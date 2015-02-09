package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.data.CsvConditionImpl;
import jp.co.sint.webshop.service.order.PaymentMailDelegate;

public class PaymentInputImportCondition extends CsvConditionImpl<PaymentInputCsvSchema> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private PaymentMailDelegate delegate;

  /**
   * @return the delegate
   */
  public PaymentMailDelegate getDelegate() {
    return delegate;
  }

  /**
   * @param delegate
   *          the delegate to set
   */
  public void setDelegate(PaymentMailDelegate delegate) {
    this.delegate = delegate;
  }
}
