package jp.co.sint.webshop.data;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 在庫入出力の単位を表すオブジェクトです。ショップコード、SKUコード、入出庫数量、入出庫日で構成されます。
 * 
 * @author System Integrator Corp.
 */
public class StockUnit implements Serializable, Comparable<StockUnit> {

  /** */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String skuCode;

  private int quantity;

  private Date stockIODate;

  private String memo;

  private String shippingNo;

  private LoginInfo loginInfo;

  /**
   * @return the memo
   */
  public String getMemo() {
    return memo;
  }

  /**
   * @param memo
   *          the memo to set
   */
  public void setMemo(String memo) {
    this.memo = memo;
  }

  /**
   * shippingNoを返します。
   * 
   * @return the shippingNo
   */
  public String getShippingNo() {
    return shippingNo;
  }

  /**
   * shippingNoを設定します。
   * 
   * @param shippingNo
   *          設定する shippingNo
   */
  public void setShippingNo(String shippingNo) {
    this.shippingNo = shippingNo;
  }

  public StockUnit() {
  }

  /**
   * shopCodeを返します。
   * 
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * skuCodeを返します。
   * 
   * @return the skuCode
   */
  public String getSkuCode() {
    return skuCode;
  }

  /**
   * skuCodeを設定します。
   * 
   * @param skuCode
   *          設定する skuCode
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  /**
   * quantityを返します。
   * 
   * @return the quantity
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * quantityを設定します。
   * 
   * @param quantity
   *          設定する quantity
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * @return the stockIODate
   */
  public Date getStockIODate() {
    return DateUtil.immutableCopy(stockIODate);
  }

  /**
   * @param stockIODate
   *          the stockIODate to set
   */
  public void setStockIODate(Date stockIODate) {
    this.stockIODate = DateUtil.immutableCopy(stockIODate);
  }

  /**
   * loginInfoを返します。
   * 
   * @return the loginInfo
   */
  public LoginInfo getLoginInfo() {
    return loginInfo;
  }

  /**
   * loginInfoを設定します。
   * 
   * @param loginInfo
   *          設定する loginInfo
   */
  public void setLoginInfo(LoginInfo loginInfo) {
    this.loginInfo = loginInfo;
  }

  public int compareTo(StockUnit stockUnit) {
    int a = this.getShopCode().compareTo(stockUnit.getShopCode());
    if (a != 0) {
      return a;
    }
    return this.getSkuCode().compareTo(stockUnit.getSkuCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof StockUnit) {
      StockUnit su = (StockUnit) obj;
      return this.getShopCode().equals(su.getShopCode()) && this.getSkuCode().equals(su.getSkuCode());
    } else {
      return false;
    }
  }

  @Override
public native int hashCode();
}
