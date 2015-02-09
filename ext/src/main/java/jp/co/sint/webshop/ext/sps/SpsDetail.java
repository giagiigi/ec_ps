package jp.co.sint.webshop.ext.sps;

import java.io.Serializable;

/**
 * @author System Integrator Corp.
 */
public class SpsDetail implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 明細行番号 */
  private String dtlRowno = "";

  /** 明細商品ID */
  private String dtlItemId = "";

  /** 明細商品名称 */
  private String dtlItemName = "";

  /** 明細数量 */
  private String dtlItemCount = "";

  /** 明細税額 */
  private String dtlTax = "";

  /** 明細金額(税込) */
  private String dtlAmount = "";

  /** 明細自由欄1 */
  private String dtlFree1 = "";

  /** 明細自由欄2 */
  private String dtlFree2 = "";

  /** 明細自由欄3 */
  private String dtlFree3 = "";

  /**
   * dtlRownoを返します。
   * 
   * @return dtlRowno
   */
  public String getDtlRowno() {
    return dtlRowno;
  }

  /**
   * dtlRownoを設定します。
   * 
   * @param dtlRowno
   *          dtlRowno
   */
  public void setDtlRowno(String dtlRowno) {
    this.dtlRowno = dtlRowno;
  }

  /**
   * dtlItemIdを返します。
   * 
   * @return dtlItemId
   */
  public String getDtlItemId() {
    return dtlItemId;
  }

  /**
   * dtlItemIdを設定します。
   * 
   * @param dtlItemId
   *          dtlItemId
   */
  public void setDtlItemId(String dtlItemId) {
    this.dtlItemId = dtlItemId;
  }

  /**
   * dtlItemNameを返します。
   * 
   * @return dtlItemName
   */
  public String getDtlItemName() {
    return dtlItemName;
  }

  /**
   * dtlItemNameを設定します。
   * 
   * @param dtlItemName
   *          dtlItemName
   */
  public void setDtlItemName(String dtlItemName) {
    this.dtlItemName = dtlItemName;
  }

  /**
   * dtlItemCountを返します。
   * 
   * @return dtlItemCount
   */
  public String getDtlItemCount() {
    return dtlItemCount;
  }

  /**
   * dtlItemCountを設定します。
   * 
   * @param dtlItemCount
   *          dtlItemCount
   */
  public void setDtlItemCount(String dtlItemCount) {
    this.dtlItemCount = dtlItemCount;
  }

  /**
   * dtlTaxを返します。
   * 
   * @return dtlTax
   */
  public String getDtlTax() {
    return dtlTax;
  }

  /**
   * dtlTaxを設定します。
   * 
   * @param dtlTax
   *          dtlTax
   */
  public void setDtlTax(String dtlTax) {
    this.dtlTax = dtlTax;
  }

  /**
   * dtlAmountを返します。
   * 
   * @return dtlAmount
   */
  public String getDtlAmount() {
    return dtlAmount;
  }

  /**
   * dtlAmountを設定します。
   * 
   * @param dtlAmount
   *          dtlAmount
   */
  public void setDtlAmount(String dtlAmount) {
    this.dtlAmount = dtlAmount;
  }

  /**
   * dtlFree1を返します。
   * 
   * @return dtlFree1
   */
  public String getDtlFree1() {
    return dtlFree1;
  }

  /**
   * dtlFree1を設定します。
   * 
   * @param dtlFree1
   *          dtlFree1
   */
  public void setDtlFree1(String dtlFree1) {
    this.dtlFree1 = dtlFree1;
  }

  /**
   * dtlFree2を返します。
   * 
   * @return dtlFree2
   */
  public String getDtlFree2() {
    return dtlFree2;
  }

  /**
   * dtlFree2を設定します。
   * 
   * @param dtlFree2
   *          dtlFree2
   */
  public void setDtlFree2(String dtlFree2) {
    this.dtlFree2 = dtlFree2;
  }

  /**
   * dtlFree3を返します。
   * 
   * @return dtlFree3
   */
  public String getDtlFree3() {
    return dtlFree3;
  }

  /**
   * dtlFree3を設定します。
   * 
   * @param dtlFree3
   *          dtlFree3
   */
  public void setDtlFree3(String dtlFree3) {
    this.dtlFree3 = dtlFree3;
  }

}
