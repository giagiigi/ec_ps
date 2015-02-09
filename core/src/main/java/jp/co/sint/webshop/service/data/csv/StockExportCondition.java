package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class StockExportCondition extends CsvConditionImpl<StockCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }
}
