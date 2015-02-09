/**
 * Alipay.com Inc. Copyright (c) 2005-2008 All Rights Reserved.
 */
package jp.co.sint.webshop.ext.alipay.wap;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.ext.alipay.wap.util.MD5Signature;
import jp.co.sint.webshop.ext.alipay.wap.util.ParameterUtil;
import jp.co.sint.webshop.ext.text.Messages;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;

import org.apache.log4j.Logger;

/**
 * 支付成功后支付宝同步调用url
 * 
 * @author 3y
 * @version $Id: CallBack.java, v 0.1 2011-8-25 下午05:16:26 3y Exp $
 */
public class AlipayWapCallBack extends HttpServlet {

  private static final long serialVersionUID = -2234271646410251381L;

  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    PartnerConfig config = DIContainer.get(PartnerConfig.class.getSimpleName());
    Logger logger = Logger.getLogger(this.getClass());

    // 获得通知签名
    String sign = request.getParameter("sign");
    String result = request.getParameter("result");
    String requestToken = request.getParameter("request_token");
    String outTradeNo = request.getParameter("out_trade_no");
    String tradeNo = request.getParameter("trade_no");
    //String total_fee = request.getParameter("total_fee"); // 获取总金额
    Map<String, String> resMap = new HashMap<String, String>();
    resMap.put("result", result);
    resMap.put("request_token", requestToken);
    resMap.put("out_trade_no", outTradeNo);
    resMap.put("trade_no", tradeNo);
    String verifyData = ParameterUtil.getSignData(resMap);
    boolean verified = false;
    logger.info("订单编号" + outTradeNo);
    // 使用MD5验签名
    try {
      verified = MD5Signature.verify(verifyData, sign, config.getKey());

      OrderService service = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
      OrderContainer container = service.getOrder(outTradeNo);
      // 订单存在检查
      if (container == null || container.getOrderHeader() == null) {
        request.getRequestDispatcher("/app/common/index").forward(request, response);
        logger.error(Messages.getString("jp.co.sint.webshop.ext.alipay.AlipayResponseReturn.1") + "OrderNo:  " + outTradeNo);
        return;
      }
      OrderHeader header = container.getOrderHeader();

      if (!verified || !result.equals("success")) {
        // 已经支付的订单,返回支付完成画面.
        logger.info("该订单已支付" + outTradeNo);
        if (PaymentStatus.PAID.longValue().equals(header.getPaymentStatus())) {
          request.getRequestDispatcher("/app/order/external_payment/complete/" + header.getOrderNo()).forward(request, response);
          return;
        }
        logger.error(Messages.getString("jp.co.sint.webshop.ext.alipay.AlipayResponseReturn.3") + "OrderNo:  " + outTradeNo);
        request.getRequestDispatcher("/app/common/index").forward(request, response);
      } else {
        if (!PaymentStatus.PAID.longValue().equals(header.getPaymentStatus())) {
          logger.info("修改本地订单信息" + outTradeNo);
          // 订单支付状态设置：已支付
          header.setPaymentStatus(PaymentStatus.PAID.longValue());
          header.setPaymentDate(DateUtil.getSysdate());
          header.setPaymentReceiptNo(tradeNo);
          header.setPaymentMethodType(PaymentMethodType.WAP_ALIPAY.getValue());
          // 实收金额设置
         // header.setPaidPrice(new BigDecimal(total_fee));
          // 支付信息更新
          ServiceResult svcResult = service.settlePhantomOrder(header);
          if (!svcResult.hasError()) {
            logger.info("该订单支付状态已更新" + outTradeNo);
            request.getRequestDispatcher("/app/order/external_payment/complete/" + header.getOrderNo()).forward(request, response);
            return;
          } else {
            logger.info("该订单支付状态更新失败" + outTradeNo);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
