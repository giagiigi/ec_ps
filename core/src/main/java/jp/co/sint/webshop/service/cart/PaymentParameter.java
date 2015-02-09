package jp.co.sint.webshop.service.cart;

import java.io.Serializable;

public interface PaymentParameter extends Serializable {

  String getKey();

  String getValue();

  CashierPayment getPayment();
}
