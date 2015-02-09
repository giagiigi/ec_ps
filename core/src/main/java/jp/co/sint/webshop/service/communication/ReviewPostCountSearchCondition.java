package jp.co.sint.webshop.service.communication;

import jp.co.sint.webshop.service.SearchCondition;

public class ReviewPostCountSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String reviewDisplayType;

  /**
   * reviewDisplayTypeを取得します。
   * 
   * @return reviewDisplayType
   */
  public String getReviewDisplayType() {
    return reviewDisplayType;
  }

  /**
   * reviewDisplayTypeを設定します。
   * 
   * @param reviewDisplayType
   *          reviewDisplayType
   */
  public void setReviewDisplayType(String reviewDisplayType) {
    this.reviewDisplayType = reviewDisplayType;
  }

  /**
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

}
