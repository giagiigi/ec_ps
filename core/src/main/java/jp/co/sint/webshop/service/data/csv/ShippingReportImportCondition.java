package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.data.CsvConditionImpl;
import jp.co.sint.webshop.service.order.ShippingMailDelegate;

public class ShippingReportImportCondition extends CsvConditionImpl<ShippingReportCsvSchema> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private ShippingMailDelegate delegate = new ShippingMailDelegate();

  /**
   * @return the delegate
   */
  public ShippingMailDelegate getDelegate() {
    return delegate;
  }

  /**
   * @param delegate
   *          the delegate to set
   */
  public void setDelegate(ShippingMailDelegate delegate) {
    this.delegate = delegate;
  }

}
