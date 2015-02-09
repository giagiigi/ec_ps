package jp.co.sint.webshop.ext.cup;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.ext.sps.SpsNotationHandler;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.utility.DateUtil;

import org.apache.log4j.Logger;

/**
 * @author System Integrator Corp.
 */
public class CupNotationHandler extends SpsNotationHandler {

  /** */
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    this.doPost(req, resp);
  }

  @SuppressWarnings("unchecked")
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//    Logger logger = Logger.getLogger(this.getClass());
    Logger.getLogger(this.getClass());
    resp.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = resp.getWriter();
    boolean result = true;
    // update start date 2011-01-27 by yyq desc：注释原有判断跳转
    // if (req.getRequestURI().contains("/cup/complete")) {
    // String lang = req.getParameter("lang");
    // if (!DIContainer.getLocaleContext().isValidLanguage(lang)) {
    // lang = DIContainer.getLocaleContext().getSystemLanguageCode();
    // }
    // req.getRequestDispatcher("/" + lang + "/app/order/complete").forward(req,
    // resp);
    // return;
    // } else {
    // logger.debug("unknown response: " + req.getRequestURI());
    // }
    // update end date 2011-01-27 by yyq desc：注释原有判断跳转
    try {
      CupPaymentResponse response = CupUtil.createPaymentResponse(req.getParameterMap());
      result = CupUtil.verifyCupPaymentResponse(response);

      // add start date 2011-01-27 by yyq desc：添加验证参数
      if (result && response.getTransactionStatus().equals("1001")) {
        // add end date 2011-01-27 by yyq desc：添加验证参数
        OrderService service = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
        String orderNo = response.getPrivate1(); // private1にWSの注文番号を保持する
        OrderContainer container = service.getOrder(orderNo);
        OrderHeader header = container.getOrderHeader();
        if (header != null) {
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
          header.setPaymentDate(DateUtil.parseString(response.getTransactionDate(), "yyyyMMdd"));
          header.setPaymentReceiptNo(response.getOrderId());

          ServiceResult svcResult = service.settlePhantomOrder(header);
          if (svcResult.hasError()) {
            result = false;
          }
        } else {
          result = false;
        }
      }

      if (result) {
        out.println("OK success!");
        out.flush();
        out.close();
        // add start date 2011-01-27 by yyq desc：添加判断跳转
        if (req.getRequestURI().contains("/cup/complete")) {
          req.getRequestDispatcher("front/contents/top").forward(req, resp);
          return;
        }else{
          
        }
       //add end date 2011-01-27 by yyq desc：添加判断跳转
      } else {
        out.println("NG fail pay!");
        out.flush();
        out.close();
        if (req.getRequestURI().contains("/cup/complete")) {
          req.getRequestDispatcher("front/contents/top").forward(req, resp);
          return;
        }else{
          
        }
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass()).error(ex);
      out.println("NG");
    } finally {
      out.flush();
      out.close();
    }

  }
}
