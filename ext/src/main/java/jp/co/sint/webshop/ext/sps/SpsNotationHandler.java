package jp.co.sint.webshop.ext.sps;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.shop.PaymentMethodSuite;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;

import org.apache.log4j.Logger;

/**
 * @author System Integrator Corp.
 */
public class SpsNotationHandler extends HttpServlet {

  /** */
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    this.doPost(req, resp);
  }

  @SuppressWarnings("unchecked")
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    resp.setContentType("text/plain;charset=UTF-8");

    PrintWriter out = resp.getWriter();
    
    boolean resultTwo = true;
    
    try {
      Map<String, String[]> parameterMap = req.getParameterMap();

      // del start date 2011-01-27 by yyq desc：注释原有验证方法
      // SpsResponse response = SpsUtil.create(parameterMap);
      // del end date 2011-01-27 by yyq desc：注释原有验证方法

      OrderService service = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
      ShopManagementService sms = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());

      // update start date 2011-01-27 by yyq desc： 更新获得订单编号的方法
      OrderContainer container = service.getOrder(req.getParameter("out_trade_no"));
      // update end date 2011-01-27 by yyq desc： 更新获得订单编号的方法

      OrderHeader header = container.getOrderHeader();
      PaymentMethodSuite pms = sms.getPaymentMethod(header.getShopCode(), header.getPaymentMethodNo());

      // del start date 2011-01-27 by yyq desc：注释原有给key附赠的方法
      // String password = pms.getPaymentMethod().getSecretKey();
      // response.setPassword(password);
      // del end date 2011-01-27 by yyq desc：注释原有给key附赠的方法

      // add start date 2011-01-27 by yyq desc：添加需要验证的返回url
      String alipayNotifyURL = "http://notify.alipay.com/trade/notify_query.do?" + "partner="
          + pms.getPaymentMethod().getMerchantId() + "&notify_id=" + req.getParameter("notify_id");
      // add end date 2011-01-27 by yyq desc：添加需要验证的返回url

      // M17N 10361 修正 ここから（エラー時遷移URL未定）
      if (header == null) {
        resp.sendRedirect(DIContainer.getWebshopConfig().getTopPageUrl());
      }
      if (!header.getOrderStatus().equals(OrderStatus.PHANTOM_ORDER.longValue())
          && !header.getOrderStatus().equals(OrderStatus.PHANTOM_RESERVATION.longValue())) {
        resp.sendRedirect(DIContainer.getWebshopConfig().getTopPageUrl());
      }
      if (header.getPaymentStatus().equals(PaymentStatus.PAID.longValue())) {
        resp.sendRedirect(DIContainer.getWebshopConfig().getTopPageUrl());
      }
      if (header.getOrderStatus().equals(OrderStatus.CANCELLED.longValue())) {
        resp.sendRedirect(DIContainer.getWebshopConfig().getTopPageUrl());
      }
      // M17N 10361 追加 ここまで
      // M17N 10361 追加 ここまで
      // if (req.getRequestURI().equals(req.getContextPath() + "/sps/response"))
      // {

      // add start date 2011-01-27 by yyq
      // desc：验证获取支付宝ATN返回url结果，true是正确的订单信息，false 是无效的
      String responseTxt = CheckURL.check(alipayNotifyURL);
      // add end date 2011-01-27 by yyq
      // desc：验证获取支付宝ATN返回url结果，true是正确的订单信息，false 是无效的/

      // del start date 2011-01-27 by yyq desc：注释原有String 处理方法
      // String xresult = StringUtil.coalesce(response.getResResult(), "");
      // del end date 2011-01-27 by yyq desc：注释原有String 处理方法

      // add start date 2011-01-27 by yyq desc：获取支付宝ATN返回参数，进行加密签名
      Map params = new HashMap();
      for (Iterator iter = parameterMap.keySet().iterator(); iter.hasNext();) {
        String name = (String) iter.next();
        String[] values = (String[]) parameterMap.get(name);
        String valueStr = "";
        for (int i = 0; i < values.length; i++) {
          valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
        }
        params.put(name, valueStr);
      }
      String mysign = SignatureHelper_return.sign(params, pms.getPaymentMethod().getSecretKey());
      // add end date 2011-01-27 by yyq desc：获取支付宝ATN返回参数，进行加密签名

      // del start date 2011-01-27 by yyq desc：注释原有的验证支付成功
      // if (!xresult.equals("OK")) {
      // out.println("NG, received NG result");
      // } else if (!SpsUtil.validateCheckSum(response)) {
      // out.println("NG, unmatched checksum");
      // } else {
      // del end date 2011-01-27 by yyq desc：注释原有的验证支付成功

      // add start date 2011-01-27 by yyq desc：验证成功与否
      if (mysign.equals(req.getParameter("sign")) && responseTxt.equals("true")) {
        // add end date 2011-01-27 by yyq desc：验证成功与否

        header.setPaymentStatus(PaymentStatus.PAID.longValue());
        // M17N 10361 修正 ここから
        // header.setOrderStatus(OrderStatus.ORDERED.longValue());
        OrderStatus status = OrderStatus.fromValue(header.getOrderStatus());
        if (status == OrderStatus.PHANTOM_ORDER) {
          header.setOrderStatus(OrderStatus.ORDERED.longValue());

        } else if (status == OrderStatus.PHANTOM_RESERVATION) {
          header.setOrderStatus(OrderStatus.RESERVED.longValue());
        }
        // M17N 10361 修正 ここまで
        // update start date 2011-01-27 by yyq desc：付款时间及流水号
        header.setPaymentDate(DateUtil.parseString(req.getParameter("gmt_payment"), "yyyyMMddHHmmss"));
        header.setPaymentReceiptNo(req.getParameter("trade_no"));
        // update end date 2011-01-27 by yyq desc：付款时间及流水号

        ServiceResult result = service.settlePhantomOrder(header);
        if (result.hasError()) {
          out.println("NG,WS Transaction Failed.");
          resultTwo = false;
        } else {
          out.println("OK,Pay Success");
        }
      }else{
        resultTwo = false;
      }
      if (resultTwo) {
        out.println("OK success!");
        out.flush();
        out.close();
        // add start date 2011-01-27 by yyq desc：添加判断跳转
        if (req.getRequestURI().contains("/sps/complete")) {
          req.getRequestDispatcher("front/contents/top").forward(req, resp);
          return;
        }else{
          
        }
       //add end date 2011-01-27 by yyq desc：添加判断跳转
      } else {
        out.println("NG fail pay!");
        out.flush();
        out.close();
        if (req.getRequestURI().contains("/sps/complete")) {
          req.getRequestDispatcher("/front/contents/top").forward(req, resp);
          return;
        }else{
          
        }
      }
      // } else {
      // resp.reset();
      // String mode = req.getRequestURI().replaceFirst(req.getContextPath() +
      // "/sps/", "");
      // if (mode.startsWith("success")) {
      // String lang = req.getParameter("lang");
      // if (!DIContainer.getLocaleContext().isValidLanguage(lang)) {
      // lang = DIContainer.getLocaleContext().getSystemLanguageCode();
      // }
      // req.getRequestDispatcher("/" + lang +
      // "/app/order/complete").forward(req, resp);
      // } else if (mode.startsWith("cancel")) {
      // resp.sendRedirect(DIContainer.getWebshopConfig().getTopPageUrl());
      // } else if (mode.startsWith("error")) {
      // resp.sendRedirect(DIContainer.getWebshopConfig().getTopPageUrl());
      // }
      // return;
      // }
    } catch (Exception e) {
      Logger.getLogger(this.getClass()).error(e);
      out.println("NG, System Error");
    } finally {
      out.flush();
      out.close();
    }

  }
}
