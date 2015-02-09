package jp.co.sint.webshop.service.communication;

import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.StringUtil;

public class ReviewListSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** ショップコード */
  private String searchShopCode;

  /** 投稿日FROM */
  private String searchReviewContributedDatetimeFrom;

  /** 投稿日TO */
  private String searchReviewContributedDatetimeTo;

  /** 商品コードFROM */
  private String searchCommodityCodeFrom;

  /** 商品コードTO */
  private String searchCommodityCodeTo;

  /** 商品名 */
  private String searchCommodityName;

  /** タイトル・内容 */
  private String searchReviewContent;

  /** 表示状態 */
  private String searchReviewDisplayType;

  /** 商品コード */
  private String searchCommodityCode;

  /** 顧客コード */
  private String searchCustomerCode;
  
  //20111219 os013 add start
  /** 受注履歴ID */
  private String searchOrderNo;
  //20111219 os013 add end
  
  /** ソート順 */
  private String searchListSort;

  private String searchDisplayCount;
  
  // 20111219 os013 add start
  /** 评论星数 */
  private String[] searchReviewScoreSummary = new String[0];

  // 20111219 os013 add end
  /**
   * searchListSortを取得します。
   * 
   * @return searchListSort
   */
  public String getSearchListSort() {
    return searchListSort;
  }

  /**
   * searchListSortを設定します。
   * 
   * @param searchListSort
   */
  public void setSearchListSort(String searchListSort) {
    this.searchListSort = searchListSort;
  }

  /**
   * searchShopCodeを取得します。
   * 
   * @return searchShopCode
   */
  public String getSearchShopCode() {
    return searchShopCode;
  }

  /**
   * searchShopCodeを設定します。
   * 
   * @param searchShopCode
   */
  public void setSearchShopCode(String searchShopCode) {
    this.searchShopCode = searchShopCode;
  }

  /**
   * searchCommodityCodeFromを取得します。
   * 
   * @return searchCommodityCodeFrom
   */
  public String getSearchCommodityCodeFrom() {
    return searchCommodityCodeFrom;
  }

  /**
   * searchCommodityCodeFromを設定します。
   * 
   * @param searchCommodityCodeFrom
   *          searchCommodityCodeFrom
   */
  public void setSearchCommodityCodeFrom(String searchCommodityCodeFrom) {
    this.searchCommodityCodeFrom = searchCommodityCodeFrom;
  }

  /**
   * searchCommodityCodeToを取得します。
   * 
   * @return searchCommodityCodeTo
   */
  public String getSearchCommodityCodeTo() {
    return searchCommodityCodeTo;
  }

  /**
   * searchCommodityCodeToを設定します。
   * 
   * @param searchCommodityCodeTo
   *          searchCommodityCodeTo
   */
  public void setSearchCommodityCodeTo(String searchCommodityCodeTo) {
    this.searchCommodityCodeTo = searchCommodityCodeTo;
  }

  /**
   * searchCommodityNameを取得します。
   * 
   * @return searchCommodityName
   */
  public String getSearchCommodityName() {
    return searchCommodityName;
  }

  /**
   * searchCommodityNameを設定します。
   * 
   * @param searchCommodityName
   *          searchCommodityName
   */
  public void setSearchCommodityName(String searchCommodityName) {
    this.searchCommodityName = searchCommodityName;
  }

  /**
   * searchReviewContentを取得します。
   * 
   * @return searchReviewContent
   */
  public String getSearchReviewContent() {
    return searchReviewContent;
  }

  /**
   * searchReviewContentを設定します。
   * 
   * @param searchReviewContent
   *          searchReviewContent
   */
  public void setSearchReviewContent(String searchReviewContent) {
    this.searchReviewContent = searchReviewContent;
  }

  /**
   * searchReviewContributedDatetimeFromを取得します。
   * 
   * @return searchReviewContributedDatetimeFrom
   */
  public String getSearchReviewContributedDatetimeFrom() {
    return searchReviewContributedDatetimeFrom;
  }

  /**
   * searchReviewContributedDatetimeFromを設定します。
   * 
   * @param searchReviewContributedDatetimeFrom
   *          searchReviewContributedDatetimeFrom
   */
  public void setSearchReviewContributedDatetimeFrom(String searchReviewContributedDatetimeFrom) {
    this.searchReviewContributedDatetimeFrom = searchReviewContributedDatetimeFrom;
  }

  /**
   * searchReviewContributedDatetimeToを取得します。
   * 
   * @return searchReviewContributedDatetimeTo
   */
  public String getSearchReviewContributedDatetimeTo() {
    return searchReviewContributedDatetimeTo;
  }

  /**
   * searchReviewContributedDatetimeToを設定します。
   * 
   * @param searchReviewContributedDatetimeTo
   *          searchReviewContributedDatetimeTo
   */
  public void setSearchReviewContributedDatetimeTo(String searchReviewContributedDatetimeTo) {
    this.searchReviewContributedDatetimeTo = searchReviewContributedDatetimeTo;
  }

  /**
   * searchReviewDisplayTypeを取得します。
   * 
   * @return searchReviewDisplayType
   */
  public String getSearchReviewDisplayType() {
    return searchReviewDisplayType;
  }

  /**
   * searchReviewDisplayTypeを設定します。
   * 
   * @param searchReviewDisplayType
   *          searchReviewDisplayType
   */
  public void setSearchReviewDisplayType(String searchReviewDisplayType) {
    this.searchReviewDisplayType = searchReviewDisplayType;
  }

  /**
   * searchCommodityCodeを取得します。
   * 
   * @return searchCommodityCode
   */
  public String getSearchCommodityCode() {
    return searchCommodityCode;
  }

  /**
   * searchCustomerCodeを取得します。
   * 
   * @return searchCustomerCode
   */
  public String getSearchCustomerCode() {
    return searchCustomerCode;
  }

  /**
   * searchCommodityCodeを設定します。
   * 
   * @param searchCommodityCode
   *          searchCommodityCode
   */
  public void setSearchCommodityCode(String searchCommodityCode) {
    this.searchCommodityCode = searchCommodityCode;
  }

  /**
   * searchCustomerCodeを設定します。
   * 
   * @param searchCustomerCode
   *          searchCustomerCode
   */
  public void setSearchCustomerCode(String searchCustomerCode) {
    this.searchCustomerCode = searchCustomerCode;
  }

  /**
   * @return the searchReviewScoreSummary
   */
  public String[] getSearchReviewScoreSummary() {
    return searchReviewScoreSummary;
  }

  /**
   * @param searchReviewScoreSummary
   *          the searchReviewScoreSummary to set
   */
  public void setSearchReviewScoreSummary(String[] searchReviewScoreSummary) {
    this.searchReviewScoreSummary = searchReviewScoreSummary;
  }

  public boolean isValid() {

    boolean result = true;

    String startDateFrom = getSearchReviewContributedDatetimeFrom();
    String startDateTo = getSearchReviewContributedDatetimeTo();

    if (StringUtil.hasValueAllOf(startDateFrom, startDateTo)) {
      result &= StringUtil.isCorrectRange(startDateFrom, startDateTo);
    }
    return result;

  }
  //20111219 os013 add start
  /**
   * 受注履歴IDを設定します。
   * 
   * @param 受注履歴ID
   *          受注履歴ID
   */
  public void setSearchOrderNo(String searchOrderNo) {
    this.searchOrderNo = searchOrderNo;
  }

  /**
   *  受注履歴IDを取得します。
   * 
   * @return  受注履歴ID
   */
  public String getSearchOrderNo() {
    return searchOrderNo;
  }
  //20111219 os013 add end

  
  /**
   * @return the searchDisplayCount
   */
  public String getSearchDisplayCount() {
    return searchDisplayCount;
  }

  
  /**
   * @param searchDisplayCount the searchDisplayCount to set
   */
  public void setSearchDisplayCount(String searchDisplayCount) {
    this.searchDisplayCount = searchDisplayCount;
  }
}
