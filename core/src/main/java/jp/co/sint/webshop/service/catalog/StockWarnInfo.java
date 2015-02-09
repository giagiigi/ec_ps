package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;

public class StockWarnInfo implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String skuCode;

  private String commodityName;

  private String supplierCode;

  private Long stockNum;

  private Long stockWarning;

  public String getSkuCode() {
    return skuCode;
  }

  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  public String getCommodityName() {
    return commodityName;
  }

  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  public String getSupplierCode() {
    return supplierCode;
  }

  public void setSupplierCode(String supplierCode) {
    this.supplierCode = supplierCode;
  }

  public Long getStockNum() {
    return stockNum;
  }

  public void setStockNum(Long stockNum) {
    this.stockNum = stockNum;
  }

  public Long getStockWarning() {
    return stockWarning;
  }

  public void setStockWarning(Long stockWarning) {
    this.stockWarning = stockWarning;
  }
}
