package jp.co.sint.webshop.service.chinapay;

public interface PaymentChinapayResult {

  boolean hasError();
  
  PaymentChinapayResultBean getResultBean();

  PaymentChinapayResultType getPaymentResultType();
}
