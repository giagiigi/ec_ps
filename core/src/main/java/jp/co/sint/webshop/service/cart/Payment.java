package jp.co.sint.webshop.service.cart;

import java.io.Serializable;
import java.util.Set;

public interface Payment extends Serializable {
  
  String getPaymentShopCode();

  String getPaymentMethodCode();

  String getPaymentMethodName();

  String getPaymentMethodType();

  Set<PaymentParameter> getParameters();

  Long getCommission();

  Long getTaxCharge();

  String getTaxType();
}
