package jp.co.sint.webshop.ext.alipay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.ext.text.Messages;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.shop.PaymentMethodSuite;
import jp.co.sint.webshop.utility.DateUtil;

public class AlipayResponseReturn extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.doPost(request, response);
  }

  @SuppressWarnings("unchecked")
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Logger logger = Logger.getLogger(this.getClass());
    response.setContentType("text/plain;charset=UTF-8");
    // 获取支付宝的通知返回参数
    String trade_no = request.getParameter("trade_no"); // 支付宝交易号
    String order_no = request.getParameter("out_trade_no"); // 获取订单号
    String total_fee = request.getParameter("total_fee"); // 获取总金额
    String trade_status = request.getParameter("trade_status"); // 交易状态
    String sign = request.getParameter("sign");
    OrderService service = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
    OrderContainer container = service.getOrder(order_no);
    // 订单存在检查
    logger.info(Messages.getString("jp.co.sint.webshop.ext.alipay.AlipayResponseReturn.0") + trade_status);
    if (container == null || container.getOrderHeader() == null) {
      request.getRequestDispatcher("/app/common/index").forward(request, response);
      logger.error(Messages.getString("jp.co.sint.webshop.ext.alipay.AlipayResponseReturn.1")+"OrderNo:  " + order_no);
      return;
    }
    OrderHeader header = container.getOrderHeader();
    PaymentMethodSuite method = getPaymentMethodSuite(header.getShopCode(), header.getPaymentMethodNo());
    String key = "";
    String partnerID = "";

    // 不是支付宝支付的订单，返回。
    if (method == null || method.getPaymentMethod() == null
        || !PaymentMethodType.ALIPAY.getValue().equals(method.getPaymentMethod().getPaymentMethodType())) {
      request.getRequestDispatcher("/app/common/index").forward(request, response);
      logger.error(Messages.getString("jp.co.sint.webshop.ext.alipay.AlipayResponseReturn.2")+"OrderNo:  " + order_no);
      return;
    }
    key = method.getPaymentMethod().getSecretKey();
    partnerID = method.getPaymentMethod().getMerchantId();

    Map<String, String> params = new HashMap<String, String>();
    Map requestParams = request.getParameterMap();
    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
      String name = (String) iter.next();
      String[] values = (String[]) requestParams.get(name);
      String valueStr = "";
      for (int i = 0; i < values.length; i++) {
        valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
      }
      // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
      valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
      params.put(name, valueStr);
    }
    String mysign = AlipayUtil.GetMysign(params, key);

    String responseTxt = Verify(request.getParameter("notify_id"), partnerID);
    if (trade_status.equals("TRADE_SUCCESS") && mysign.equals(sign) && responseTxt.equals("true")) {
      if (!PaymentStatus.PAID.longValue().equals(header.getPaymentStatus())) {
        // 订单支付状态设置：已支付
        header.setPaymentStatus(PaymentStatus.PAID.longValue());
        if (params.get("gmt_payment") != null) {
          header.setPaymentDate(DateUtil.parseString(params.get("gmt_payment"), DateUtil.ALIPAY_DATE_FORMAT));
        } else {
          header.setPaymentDate(DateUtil.getSysdate());
        }
        header.setPaymentReceiptNo(trade_no);
        // 实收金额设置
        header.setPaidPrice(new BigDecimal(total_fee));
        // 支付信息更新
        ServiceResult svcResult = service.settlePhantomOrder(header);
        if (svcResult.hasError()) {
        } else {
          // 20110509 shiseidou update start
          // request.getRequestDispatcher("/app/order/complete").forward(request,
          // response);
          request.getRequestDispatcher("/app/order/external_payment/complete/" + header.getOrderNo()).forward(request, response);
          // 20110509 shiseidou update end
          return;
        }
      }
    }
    // 已经支付的订单,返回支付完成画面.
    if (PaymentStatus.PAID.longValue().equals(header.getPaymentStatus())) {
      request.getRequestDispatcher("/app/order/external_payment/complete/" + header.getOrderNo()).forward(request, response);
      return;
    }
    logger.error(Messages.getString("jp.co.sint.webshop.ext.alipay.AlipayResponseReturn.3")+"OrderNo:  " + order_no);
    request.getRequestDispatcher("/app/common/index").forward(request, response);
  }

  private PaymentMethodSuite getPaymentMethodSuite(String shopCode, Long paymentMethodNo) {
    ShopManagementService service = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());
    PaymentMethodSuite method = service.getPaymentMethod(shopCode, paymentMethodNo);

    return method;
  }

  /**
   * *功能：获取远程服务器ATN结果,验证返回URL
   * 
   * @param notify_id
   *          通知校验ID
   * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
   *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
   */
  public static String Verify(String notify_id, String partnerID) {
    // 获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求
    String veryfy_url = "";
    veryfy_url = "https://mapi.alipay.com/gateway.do?service=notify_verify";
    veryfy_url = veryfy_url + "&partner=" + partnerID + "&notify_id=" + notify_id;
    String responseTxt = CheckUrl(veryfy_url);
    return responseTxt;
  }

  /**
   * *功能：获取远程服务器ATN结果
   * 
   * @param urlvalue
   *          指定URL路径地址
   * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
   *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
   */
  public static String CheckUrl(String urlvalue) {
    String inputLine = "";

    try {
      URL url = new URL(urlvalue);
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
      inputLine = in.readLine().toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return inputLine;
  }
}
