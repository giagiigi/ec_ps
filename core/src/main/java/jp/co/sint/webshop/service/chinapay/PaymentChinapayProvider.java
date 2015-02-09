package jp.co.sint.webshop.service.chinapay;



/**
 * 银联支付接口
 * 
 * @author kousen
 */
public interface PaymentChinapayProvider {

  /**
   * 银联支付参数做成
   * 
   * @param parameter
   * @return
   */
  PaymentChinapayResult payment(PaymentChinapayParameter parameter,String methodType);

  /**
   * 参数实例化
   * 
   * @return
   */
  PaymentChinapayParameter createParameterInstance();
  
  
  PaymentChinapayResult find(PaymentChinapayParameter parameter,String Type);
  
}
