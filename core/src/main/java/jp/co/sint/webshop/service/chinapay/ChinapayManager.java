package jp.co.sint.webshop.service.chinapay;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;

public class ChinapayManager {

  // 银联接口
  private PaymentChinapayProvider provider;

  // 银联接口参数
  private PaymentChinapayParameter parameter;

  public ChinapayManager() {
  }

  /**
   * 银联支付参数做成处理<BR>
   * 
   * @param container
   *          受注情報
   * @return 处理結果
   */
  public PaymentChinapayResult payment(OrderContainer container) {

    Logger logger = Logger.getLogger(this.getClass());

    logger.debug("ChinapayPayment Operation started : payment");

    // 银联接口参数初期化
    initParam(container);

    // 银联支付参数做成
    PaymentChinapayResult result = provider.payment(parameter,container.getOrderHeader().getPaymentMethodType());

    if (result.hasError()) {
      logger.error("ChinapayPayment Operation failed : payment");
    }

    logger.debug("ChinapayPayment Operation succeeded : payment");

    // 注销变量
    dispose();

    return result;
  }

  public PaymentChinapayResult find(OrderContainer container) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info("ChinapayPayment Operation started : find");
    // 银联接口参数初期化
    initParam(container);
    PaymentChinapayResult result = provider.find(parameter,container.getOrderHeader().getPaymentMethodType());

    if (result.hasError()) {
      logger.error("ChinapayPayment Operation failed : payment");
    }

    // 注销变量
    dispose();

    return result;
    
  }
  /**
   * 银联接口参数初期化
   * 
   * @param container
   */
  private void initParam(OrderContainer container) {

    // 银联接口定义
    if (provider == null) {
      provider = DIContainer.get("ChinapayCard");
    }

    // 银联接口参数生成
    if (parameter == null) {
      parameter = provider.createParameterInstance();
    }

    // 订单编号设置
    parameter.setOrderId(container.getOrderHeader().getOrderNo());
    // 20120416 wjh add start
    parameter.setTransDate(DateUtil.getDateStamp(container.getOrderHeader().getOrderDatetime()));
    // 20120416 wjh add end

    // 购买金额设置
    // 20120202 shen update start
    // parameter.setAmount(container.getTotalAmount());
    BigDecimal totalAmount = BigDecimalUtil.add(container.getTotalAmount(), container.getOrderHeader().getPaymentCommission());
    BigDecimal discountPrice = NumUtil.coalesce(container.getOrderHeader().getDiscountPrice(), BigDecimal.ZERO);
    BigDecimal giftCardUsePrice = NumUtil.coalesce(container.getOrderHeader().getGiftCardUsePrice(), BigDecimal.ZERO);
    BigDecimal outerCardUsePrice = NumUtil.coalesce(container.getOrderHeader().getOuterCardPrice(), BigDecimal.ZERO);
    totalAmount = BigDecimalUtil.subtract(totalAmount, giftCardUsePrice);
    totalAmount = BigDecimalUtil.subtract(totalAmount, discountPrice);
    totalAmount = BigDecimalUtil.add(totalAmount, outerCardUsePrice);
    parameter.setAmount(totalAmount);
    // 20120202 shen update end

  }

  /**
   * 取得 provider
   * 
   * @return the provider
   */
  public PaymentChinapayProvider getProvider() {
    return provider;
  }

  /**
   * 设定 provider
   * 
   * @param provider
   *          the provider to set
   */
  public void setProvider(PaymentChinapayProvider provider) {
    this.provider = provider;
  }

  /**
   * 取得 parameter
   * 
   * @return the parameter
   */
  public PaymentChinapayParameter getParameter() {
    return parameter;
  }

  /**
   * 设定 parameter
   * 
   * @param parameter
   *          the parameter to set
   */
  public void setParameter(PaymentChinapayParameter parameter) {
    this.parameter = parameter;
  }

  /**
   * 終了処理。<BR>
   * 支払プロバイダ・支払パラメータにnullを設定します。
   */
  private void dispose() {
    provider = null;
    parameter = null;
  }
}
