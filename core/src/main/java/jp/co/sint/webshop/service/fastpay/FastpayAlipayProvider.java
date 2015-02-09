package jp.co.sint.webshop.service.fastpay;

/**
 * 支付宝支付接口
 * 
 * @author kousen
 */
public interface FastpayAlipayProvider {

  /**
   * 参数初始化
   * 
   * @return
   */
  FastpayAlipayParameter createParameterInstance();

  /**
   * 支付宝支付
   * 
   * @param parameter
   * @return
   */
  FastpayAlipayResult payment(FastpayAlipayParameter parameter);

  /**
   * 支付宝通知
   * 
   * @param parameter
   * @return
   */
  FastpayAlipayResult advice(FastpayAlipayParameter parameter);

  /**
   * 支付宝查找
   * 
   * @param parameter
   * @return
   */
  FastpayAlipayResult find(FastpayAlipayParameter parameter);

  /**
   * 支付宝关闭
   * 
   * @param parameter
   * @return
   */
  FastpayAlipayResult close(FastpayAlipayParameter parameter);

}
