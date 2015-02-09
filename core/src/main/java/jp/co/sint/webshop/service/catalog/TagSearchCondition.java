package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.service.SearchCondition;

public class TagSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String searchTagCodeStart;

  private String searchTagCodeEnd;

  private String searchTagName;

  public String getSearchTagCodeEnd() {
    return searchTagCodeEnd;
  }

  public void setSearchTagCodeEnd(String searchTagCodeEnd) {
    this.searchTagCodeEnd = searchTagCodeEnd;
  }

  public String getSearchTagCodeStart() {
    return searchTagCodeStart;
  }

  public void setSearchTagCodeStart(String searchTagCodeStart) {
    this.searchTagCodeStart = searchTagCodeStart;
  }

  public String getSearchTagName() {
    return searchTagName;
  }

  public void setSearchTagName(String searchTagName) {
    this.searchTagName = searchTagName;
  }

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

}
