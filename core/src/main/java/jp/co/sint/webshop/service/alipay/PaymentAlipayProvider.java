package jp.co.sint.webshop.service.alipay;

/**
 * 支付宝支付接口
 * 
 * @author kousen
 */
public interface PaymentAlipayProvider {

  /**
   * 参数初始化
   * 
   * @return
   */
  PaymentAlipayParameter createParameterInstance();

  /**
   * 支付宝支付
   * 
   * @param parameter
   * @return
   */
  PaymentAlipayResult payment(PaymentAlipayParameter parameter);

  /**
   * 支付宝通知
   * 
   * @param parameter
   * @return
   */
  PaymentAlipayResult advice(PaymentAlipayParameter parameter);

  /**
   * 支付宝查找
   * 
   * @param parameter
   * @return
   */
  PaymentAlipayResult find(PaymentAlipayParameter parameter);

  /**
   * 支付宝关闭
   * 
   * @param parameter
   * @return
   */
  PaymentAlipayResult close(PaymentAlipayParameter parameter);

}
