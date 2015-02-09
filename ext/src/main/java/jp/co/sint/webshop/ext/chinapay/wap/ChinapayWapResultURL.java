package jp.co.sint.webshop.ext.chinapay.wap;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayResultBean;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

/**
 * 银联支付画面返回
 * @author kousen
 *
 */
public class ChinapayWapResultURL extends HttpServlet {

  /** */
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    this.doPost(req, resp);
  }

  @SuppressWarnings("unchecked")
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Logger logger = Logger.getLogger(this.getClass());
    resp.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = resp.getWriter();
    boolean result = false;
    String orderNo = "";
    try {
      // 取得银联通知参数
      PaymentChinapayResultBean bean = ChinapayWapUtil.createPaymentResponse(req.getParameterMap());
      orderNo = bean.getOrderId();
      
      if (!StringUtil.isNullOrEmpty(bean) && !StringUtil.isNullOrEmpty(bean.getResult())) {
		if (bean.getResult().equals("0")) {
			result = true;
		}
      }
      // 交易状态判断
      if (result) {

        OrderService service = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
        OrderContainer container = service.getOrder(orderNo);
        OrderHeader header = container.getOrderHeader();
        if (header != null) {
          if (!PaymentStatus.PAID.longValue().equals(header.getPaymentStatus())) {
            // 订单支付状态设置：已支付
            header.setPaymentStatus(PaymentStatus.PAID.longValue());
            // 支付日设置
            header.setPaymentDate(DateUtil.parseString(DateUtil.getDateStamp(DateUtil.getSysdate()), "yyyyMMdd"));
            // 支付编号设置
            header.setPaymentReceiptNo(bean.getOrderId());
            // 实收金额设置
            // header.setPaidPrice((new BigDecimal(bean.getTransactionAmount())).divide(new BigDecimal(100)));
            // 支付信息更新
            ServiceResult svcResult = service.settlePhantomOrder(header);
            if (svcResult.hasError()) {
              result = false;
            }
          }
        } else {
          result = false;
        }
      }
        req.getRequestDispatcher("/app/order/external_payment/complete/" + orderNo).forward(req, resp);
        if (!result) {
          logger.error("ChinaPay: Update Payment Error!(OrderNo:" + orderNo + ")");
        }
        logger.info("银联画面返回：(" + orderNo + ")" + bean.getTransactionStatus());
      if (result) {
        out.println("OK");
        out.flush();
        out.close();
        logger.info("ChinaPay: Update Payment OK!(OrderNo:" + orderNo + ")" + bean.getTransactionStatus());

      } else {
        out.println("NG");
        out.flush();
        out.close();

        logger.error("ChinaPay: Update Payment Error!(OrderNo:" + orderNo + ")");
      }
    } catch (Exception ex) {
      Logger.getLogger(this.getClass()).error(ex);
      out.println("NG");
      logger.error("ChinaPay: Update Payment Error!(OrderNo:" + orderNo + ")");
    } finally {
      out.flush();
      out.close();
    }

  }
}
