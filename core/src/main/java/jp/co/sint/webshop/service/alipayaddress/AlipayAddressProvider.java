package jp.co.sint.webshop.service.alipayaddress;

/**
 * 支付宝支付接口
 * 
 * @author kousen
 */
public interface AlipayAddressProvider {

  /**
   * 参数初始化
   * 
   * @return
   */
  AlipayAddressParameter createParameterInstance();

  /**
   * 支付宝支付
   * 
   * @param parameter
   * @return
   */
  AlipayAddressResult payment(AlipayAddressParameter parameter);

  /**
   * 支付宝通知
   * 
   * @param parameter
   * @return
   */
  AlipayAddressResult advice(AlipayAddressParameter parameter);

  /**
   * 支付宝查找
   * 
   * @param parameter
   * @return
   */
  AlipayAddressResult find(AlipayAddressParameter parameter);

  /**
   * 支付宝关闭
   * 
   * @param parameter
   * @return
   */
  AlipayAddressResult close(AlipayAddressParameter parameter);

}
