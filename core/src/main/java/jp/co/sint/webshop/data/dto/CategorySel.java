package jp.co.sint.webshop.data.dto;

import java.io.Serializable;

public class CategorySel implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  private String categoryCode;

  private String commodityCode;

  private String keywordJp1;

  private String keywordJp2;

  private String keywordCn1;

  private String keywordCn2;

  private String keywordEn1;

  private String keywordEn2;

  /**
   * @return the categoryCode
   */
  public String getCategoryCode() {
    return categoryCode;
  }

  /**
   * @param categoryCode
   *          the categoryCode to set
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  /**
   * @return the commodiyCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodiyCode
   *          the commodiyCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @return the keywordJp1
   */
  public String getKeywordJp1() {
    return keywordJp1;
  }

  /**
   * @param keywordJp1
   *          the keywordJp1 to set
   */
  public void setKeywordJp1(String keywordJp1) {
    this.keywordJp1 = keywordJp1;
  }

  /**
   * @return the keywordJp2
   */
  public String getKeywordJp2() {
    return keywordJp2;
  }

  /**
   * @param keywordJp2
   *          the keywordJp2 to set
   */
  public void setKeywordJp2(String keywordJp2) {
    this.keywordJp2 = keywordJp2;
  }

  /**
   * @return the keywordCn1
   */
  public String getKeywordCn1() {
    return keywordCn1;
  }

  /**
   * @param keywordCn1
   *          the keywordCn1 to set
   */
  public void setKeywordCn1(String keywordCn1) {
    this.keywordCn1 = keywordCn1;
  }

  /**
   * @return the keywordCn2
   */
  public String getKeywordCn2() {
    return keywordCn2;
  }

  /**
   * @param keywordCn2
   *          the keywordCn2 to set
   */
  public void setKeywordCn2(String keywordCn2) {
    this.keywordCn2 = keywordCn2;
  }

  /**
   * @return the keywordEn1
   */
  public String getKeywordEn1() {
    return keywordEn1;
  }

  /**
   * @param keywordEn1
   *          the keywordEn1 to set
   */
  public void setKeywordEn1(String keywordEn1) {
    this.keywordEn1 = keywordEn1;
  }

  /**
   * @return the keywordEn2
   */
  public String getKeywordEn2() {
    return keywordEn2;
  }

  /**
   * @param keywordEn2
   *          the keywordEn2 to set
   */
  public void setKeywordEn2(String keywordEn2) {
    this.keywordEn2 = keywordEn2;
  }
}
