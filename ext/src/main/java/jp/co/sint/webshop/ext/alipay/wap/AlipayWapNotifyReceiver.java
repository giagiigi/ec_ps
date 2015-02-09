package jp.co.sint.webshop.ext.alipay.wap;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.ext.alipay.wap.util.MD5Signature;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 接收通知并处理
 */
public class AlipayWapNotifyReceiver extends HttpServlet {

  private static final long serialVersionUID = 7216412938937049671L;

  @SuppressWarnings("unchecked")
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    PartnerConfig config = DIContainer.get(PartnerConfig.class.getSimpleName());
    Logger logger = Logger.getLogger(this.getClass());

    logger.info("接收到支付宝返回通知开始!");
    // 获得通知参数
    Map map = request.getParameterMap();

    Iterator it = map.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pairs = (Map.Entry) it.next();
      logger.info("手机支付宝：" + pairs.getKey() + " = " + pairs.getValue().toString());
    }

    try {
      String sign = (String) ((Object[]) map.get("sign"))[0];
      // 获得待验签名的数据
      String verifyData = getVerifyData(map);
      logger.info("verifyData:" + verifyData);
      boolean verified = false;
      // 验证签名
      try {
        verified = MD5Signature.verify(verifyData, sign, config.getKey());
        logger.info("签名验证通过！");
      } catch (Exception e) {
        logger.info("签名验证失败！");
        e.printStackTrace();
      }

      PrintWriter out = response.getWriter();
      // 验证签名通过
      if (verified) {
        // 返回值记录串
        String notify_data = (String) ((Object[]) map.get("notify_data"))[0];

        StringReader read = new StringReader(notify_data);
        InputSource source = new InputSource(read);
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(source);
        Element root = document.getRootElement();
        // 订单交易状态
        Element elementTradeStatus = root.getChild("trade_status");
        logger.info("订单交易状态：" + elementTradeStatus.getText());
        // EC系统订单编号
        Element elementOrderNo = root.getChild("out_trade_no");
        String orderNo = elementOrderNo.getText();
        logger.info("EC系统订单编号:" + orderNo);
        if (StringUtil.isNullOrEmpty(orderNo)) {
          logger.info("支付宝返回EC订单编号为空");
          return;
        }
        // 如果支付完成
        if (elementTradeStatus.getText().equals("TRADE_FINISHED") || elementTradeStatus.getText().equals("TRADE_SUCCESS")) {
          // 根据交易状态处理业务逻辑
          OrderService service = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
          OrderContainer container = service.getOrder(orderNo);
          // 订单存在检查
          if (container == null || container.getOrderHeader() == null) {
            logger.error("订单编号在EC系统不存在:" + orderNo);
            return;
          }
          OrderHeader header = container.getOrderHeader();
          if (!PaymentStatus.PAID.longValue().equals(header.getPaymentStatus()) || header.getPaidPrice() == null) {
            // 支付宝交易流水号
            Element elementTradeNo = root.getChild("trade_no");
            String tradeNo = elementTradeNo.getText();
            logger.info("支付宝交易流水号:" + tradeNo);
            // 实际支付金额
            Element elementTotalFee = root.getChild("total_fee");
            String totalFee = elementTotalFee.getText();
            logger.info("实际支付金额：" + totalFee);

            // 订单支付状态设置：已支付
            header.setPaymentStatus(PaymentStatus.PAID.longValue());
            header.setPaymentDate(DateUtil.getSysdate());
            header.setPaymentReceiptNo(tradeNo);
            header.setPaymentMethodType(PaymentMethodType.WAP_ALIPAY.getValue());
            header.setPaidPrice(new BigDecimal(totalFee));
            // 支付信息更新
            ServiceResult svcResult = service.settlePhantomOrder(header);
            if (!svcResult.hasError()) {
              out.print("success");
              logger.info("该订单支付状态已更新：" + orderNo);
            } else {
              logger.info("该订单支付状态更新失败：" + orderNo);
            }
          } else {
            out.print("success");
            logger.info("订单已处于付款完毕状态，无需更新：" + orderNo);
          }

        } else {
          logger.info("订单在支付宝方尚未支付完成：" + orderNo);
        }
      } else {
        out.print("fail");
        logger.info("接收支付宝系统通知验证签名失败，请检查！");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    // // 获得通知签名
    // String sign = request.getParameter("sign");
    // String result = request.getParameter("result");
    // String requestToken = request.getParameter("request_token");
    // String total_fee = request.getParameter("total_fee"); // 获取总金额
    // String outTradeNo = request.getParameter("out_trade_no");
    // String tradeNo = request.getParameter("trade_no");
    // Map<String, String> resMap = new HashMap<String, String>();
    // resMap.put("result", result);
    // resMap.put("request_token", requestToken);
    // resMap.put("out_trade_no", outTradeNo);
    // resMap.put("trade_no", tradeNo);
    // String verifyData = ParameterUtil.getSignData(resMap);
    // boolean verified = false;
    // // 验签名
    // logger.info("订单编号" + outTradeNo);
    // try {
    // PrintWriter out = response.getWriter();
    // verified = MD5Signature.verify(verifyData, sign, config.getKey());
    //
    // OrderService service =
    // ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
    // OrderContainer container = service.getOrder(outTradeNo);
    // // 订单存在检查
    // if (container == null || container.getOrderHeader() == null) {
    // request.getRequestDispatcher("/app/common/index").forward(request,
    // response);
    // logger.error(Messages.getString("jp.co.sint.webshop.ext.alipay.AlipayResponseReturn.1")
    // + "OrderNo:  " + outTradeNo);
    // return;
    // }
    // OrderHeader header = container.getOrderHeader();
    //
    // if (!verified || !result.equals("success")) {
    // logger.info("该订单已支付" + outTradeNo);
    // // 已经支付的订单,返回支付完成画面.
    // if (PaymentStatus.PAID.longValue().equals(header.getPaymentStatus())) {
    // logger.info("订单已支付完成");
    // request.getRequestDispatcher("/app/order/external_payment/complete/" +
    // header.getOrderNo()).forward(request, response);
    // return;
    // }
    // System.out.println("接收支付宝系统通知验证签名失败，请检查！");
    // out.print("fail");
    // logger.info("接收支付宝系统通知验证签名失败，请检查！");
    // logger.error(Messages.getString("jp.co.sint.webshop.ext.alipay.AlipayResponseReturn.3")
    // + "OrderNo:  " + outTradeNo);
    // request.getRequestDispatcher("/app/common/index").forward(request,
    // response);
    // } else {
    //
    // if (!PaymentStatus.PAID.longValue().equals(header.getPaymentStatus())) {
    // logger.info("修改本地订单信息" + outTradeNo);
    // // 订单支付状态设置：已支付
    // header.setPaymentStatus(PaymentStatus.PAID.longValue());
    // header.setPaymentDate(DateUtil.getSysdate());
    // header.setPaymentReceiptNo(tradeNo);
    // header.setPaymentMethodType(PaymentMethodType.WAP_ALIPAY.getValue());
    // // 实收金额设置
    // header.setPaidPrice(new BigDecimal(total_fee));
    // // 支付信息更新
    // ServiceResult svcResult = service.settlePhantomOrder(header);
    // if (!svcResult.hasError()) {
    // logger.info("该订单支付状态已更新" + outTradeNo);
    // request.getRequestDispatcher("/app/order/external_payment/complete/" +
    // header.getOrderNo()).forward(request, response);
    // return;
    // } else {
    // logger.info("该订单支付状态更新失败" + outTradeNo);
    // }
    // }
    // }
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    doPost(request, response);
  }

  /**
   * 获得验签名的数据
   * 
   * @param map
   * @return
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  private String getVerifyData(Map map) {
    Logger logger = Logger.getLogger(this.getClass());
    String service = (String) ((Object[]) map.get("service"))[0];
    String v = (String) ((Object[]) map.get("v"))[0];
    String sec_id = (String) ((Object[]) map.get("sec_id"))[0];
    String notify_data = (String) ((Object[]) map.get("notify_data"))[0];
    logger.info("通知参数为：" + "service=" + service + "&v=" + v + "&sec_id=" + sec_id + "&notify_data=" + notify_data);
    return "service=" + service + "&v=" + v + "&sec_id=" + sec_id + "&notify_data=" + notify_data;
  }

}
