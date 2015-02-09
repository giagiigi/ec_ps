package jp.co.sint.webshop.service.communication;

import jp.co.sint.webshop.service.SearchCondition;

public class GiftCardDetailListSearchCondition extends SearchCondition {

  /** uid */
  private static final long serialVersionUID = 1L;

  /** 礼品卡编号 */
  private String searchGiftCardCode;

  /** 礼品卡名称 */
  private String searchGiftCardName;

  /** 礼品卡发行开始日时(From) */
  private String searchMinIssueStartDatetimeFrom;

  /** 礼品卡发行开始日时(To) */
  private String searchMinIssueStartDatetimeTo;

  /**
   * searchMinIssueStartDatetimeFromを取得します。
   * 
   * @return searchMinIssueStartDatetimeFrom
   */
  public String getSearchMinIssueStartDatetimeFrom() {
    return searchMinIssueStartDatetimeFrom;
  }

  /**
   * searchMinIssueStartDatetimeFromを設定します。
   * 
   * @param searchMinIssueStartDatetimeFrom
   *          searchMinIssueStartDatetimeFrom
   */
  public void setSearchMinIssueStartDatetimeFrom(String searchMinIssueStartDatetimeFrom) {
    this.searchMinIssueStartDatetimeFrom = searchMinIssueStartDatetimeFrom;
  }

  /**
   * searchMinIssueStartDatetimeToを取得します。
   * 
   * @return searchMinIssueStartDatetimeTo
   */
  public String getSearchMinIssueStartDatetimeTo() {
    return searchMinIssueStartDatetimeTo;
  }

  /**
   * searchMinIssueStartDatetimeToを設定します。
   * 
   * @param searchMinIssueStartDatetimeTo
   *          searchMinIssueStartDatetimeTo
   */
  public void setSearchMinIssueStartDatetimeTo(String searchMinIssueStartDatetimeTo) {
    this.searchMinIssueStartDatetimeTo = searchMinIssueStartDatetimeTo;
  }

  /**
   * @return the searchGiftCardCode
   */
  public String getSearchGiftCardCode() {
    return searchGiftCardCode;
  }

  /**
   * @param searchGiftCardCode
   *          the searchGiftCardCode to set
   */
  public void setSearchGiftCardCode(String searchGiftCardCode) {
    this.searchGiftCardCode = searchGiftCardCode;
  }

  /**
   * @return the searchGiftCardName
   */
  public String getSearchGiftCardName() {
    return searchGiftCardName;
  }

  /**
   * @param searchGiftCardName
   *          the searchGiftCardName to set
   */
  public void setSearchGiftCardName(String searchGiftCardName) {
    this.searchGiftCardName = searchGiftCardName;
  }
}
