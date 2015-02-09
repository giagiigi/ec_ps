package jp.co.sint.webshop.service.communication;

import jp.co.sint.webshop.service.SearchCondition;

public class FriendCouponRuleCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 优惠券规则编号 */
  private String searchCouponCode;

  /** 优惠券规则名称 */
  private String searchCouponName;

  // add by cs_yuli 20120515 start
  /** 优惠券规则英文名称 */
  private String searchCouponNameEn;

  /** 优惠券规则日文名称 */
  private String searchCouponNameJp;

  /** 优惠券类别 */
  private String searchCouponType;

  /** 优惠券发行类别 */
  private String searchCampaignType;

  /** 优惠券发行开始日时(From) */
  private String searchMinIssueStartDatetimeFrom;

  /** 优惠券发行开始日时(To) */
  private String searchMinIssueStartDatetimeTo;

  /** 优惠券发行结束日时(From) */
  private String searchMinIssueEndDatetimeFrom;

  /** 优惠券发行结束日时(To) */
  private String searchMinIssueEndDatetimeTo;

  /** 优惠券利用开始日时(From) */
  private String searchMinUseStartDatetimeFrom;

  /** 优惠券利用开始日时(To) */
  private String searchMinUseStartDatetimeTo;

  /** 优惠券利用结束日时(From) */
  private String searchMinUseEndDatetimeFrom;

  /** 优惠券利用结束日时(To) */
  private String searchMinUseEndDatetimeTo;

  /*** 发行可能日期区分 **/
  private String searchIssueDateType;

  /** 发行月份值 */
  private String searchIssueDateNum;

  /**
   * @return the searchCouponCode
   */
  public String getSearchCouponCode() {
    return searchCouponCode;
  }

  /**
   * @param searchCouponCode
   *          the searchCouponCode to set
   */
  public void setSearchCouponCode(String searchCouponCode) {
    this.searchCouponCode = searchCouponCode;
  }

  /**
   * @return the searchCouponName
   */
  public String getSearchCouponName() {
    return searchCouponName;
  }

  /**
   * @param searchCouponName
   *          the searchCouponName to set
   */
  public void setSearchCouponName(String searchCouponName) {
    this.searchCouponName = searchCouponName;
  }

  /**
   * @return the searchCouponNameEn
   */
  public String getSearchCouponNameEn() {
    return searchCouponNameEn;
  }

  /**
   * @param searchCouponNameEn
   *          the searchCouponNameEn to set
   */
  public void setSearchCouponNameEn(String searchCouponNameEn) {
    this.searchCouponNameEn = searchCouponNameEn;
  }

  /**
   * @return the searchCouponNameJp
   */
  public String getSearchCouponNameJp() {
    return searchCouponNameJp;
  }

  /**
   * @param searchCouponNameJp
   *          the searchCouponNameJp to set
   */
  public void setSearchCouponNameJp(String searchCouponNameJp) {
    this.searchCouponNameJp = searchCouponNameJp;
  }

  /**
   * @return the searchCouponType
   */
  public String getSearchCouponType() {
    return searchCouponType;
  }

  /**
   * @param searchCouponType
   *          the searchCouponType to set
   */
  public void setSearchCouponType(String searchCouponType) {
    this.searchCouponType = searchCouponType;
  }

  /**
   * @return the searchCampaignType
   */
  public String getSearchCampaignType() {
    return searchCampaignType;
  }

  /**
   * @param searchCampaignType
   *          the searchCampaignType to set
   */
  public void setSearchCampaignType(String searchCampaignType) {
    this.searchCampaignType = searchCampaignType;
  }

  /**
   * @return the searchMinIssueStartDatetimeFrom
   */
  public String getSearchMinIssueStartDatetimeFrom() {
    return searchMinIssueStartDatetimeFrom;
  }

  /**
   * @param searchMinIssueStartDatetimeFrom
   *          the searchMinIssueStartDatetimeFrom to set
   */
  public void setSearchMinIssueStartDatetimeFrom(String searchMinIssueStartDatetimeFrom) {
    this.searchMinIssueStartDatetimeFrom = searchMinIssueStartDatetimeFrom;
  }

  /**
   * @return the searchMinIssueStartDatetimeTo
   */
  public String getSearchMinIssueStartDatetimeTo() {
    return searchMinIssueStartDatetimeTo;
  }

  /**
   * @param searchMinIssueStartDatetimeTo
   *          the searchMinIssueStartDatetimeTo to set
   */
  public void setSearchMinIssueStartDatetimeTo(String searchMinIssueStartDatetimeTo) {
    this.searchMinIssueStartDatetimeTo = searchMinIssueStartDatetimeTo;
  }

  /**
   * @return the searchMinIssueEndDatetimeFrom
   */
  public String getSearchMinIssueEndDatetimeFrom() {
    return searchMinIssueEndDatetimeFrom;
  }

  /**
   * @param searchMinIssueEndDatetimeFrom
   *          the searchMinIssueEndDatetimeFrom to set
   */
  public void setSearchMinIssueEndDatetimeFrom(String searchMinIssueEndDatetimeFrom) {
    this.searchMinIssueEndDatetimeFrom = searchMinIssueEndDatetimeFrom;
  }

  /**
   * @return the searchMinIssueEndDatetimeTo
   */
  public String getSearchMinIssueEndDatetimeTo() {
    return searchMinIssueEndDatetimeTo;
  }

  /**
   * @param searchMinIssueEndDatetimeTo
   *          the searchMinIssueEndDatetimeTo to set
   */
  public void setSearchMinIssueEndDatetimeTo(String searchMinIssueEndDatetimeTo) {
    this.searchMinIssueEndDatetimeTo = searchMinIssueEndDatetimeTo;
  }

  /**
   * @return the searchMinUseStartDatetimeFrom
   */
  public String getSearchMinUseStartDatetimeFrom() {
    return searchMinUseStartDatetimeFrom;
  }

  /**
   * @param searchMinUseStartDatetimeFrom
   *          the searchMinUseStartDatetimeFrom to set
   */
  public void setSearchMinUseStartDatetimeFrom(String searchMinUseStartDatetimeFrom) {
    this.searchMinUseStartDatetimeFrom = searchMinUseStartDatetimeFrom;
  }

  /**
   * @return the searchMinUseStartDatetimeTo
   */
  public String getSearchMinUseStartDatetimeTo() {
    return searchMinUseStartDatetimeTo;
  }

  /**
   * @param searchMinUseStartDatetimeTo
   *          the searchMinUseStartDatetimeTo to set
   */
  public void setSearchMinUseStartDatetimeTo(String searchMinUseStartDatetimeTo) {
    this.searchMinUseStartDatetimeTo = searchMinUseStartDatetimeTo;
  }

  /**
   * @return the searchMinUseEndDatetimeFrom
   */
  public String getSearchMinUseEndDatetimeFrom() {
    return searchMinUseEndDatetimeFrom;
  }

  /**
   * @param searchMinUseEndDatetimeFrom
   *          the searchMinUseEndDatetimeFrom to set
   */
  public void setSearchMinUseEndDatetimeFrom(String searchMinUseEndDatetimeFrom) {
    this.searchMinUseEndDatetimeFrom = searchMinUseEndDatetimeFrom;
  }

  /**
   * @return the searchMinUseEndDatetimeTo
   */
  public String getSearchMinUseEndDatetimeTo() {
    return searchMinUseEndDatetimeTo;
  }

  /**
   * @param searchMinUseEndDatetimeTo
   *          the searchMinUseEndDatetimeTo to set
   */
  public void setSearchMinUseEndDatetimeTo(String searchMinUseEndDatetimeTo) {
    this.searchMinUseEndDatetimeTo = searchMinUseEndDatetimeTo;
  }

  /**
   * @return the searchIssueDateType
   */
  public String getSearchIssueDateType() {
    return searchIssueDateType;
  }

  /**
   * @param searchIssueDateType
   *          the searchIssueDateType to set
   */
  public void setSearchIssueDateType(String searchIssueDateType) {
    this.searchIssueDateType = searchIssueDateType;
  }

  /**
   * @return the searchIssueDateNum
   */
  public String getSearchIssueDateNum() {
    return searchIssueDateNum;
  }

  /**
   * @param searchIssueDateNum
   *          the searchIssueDateNum to set
   */
  public void setSearchIssueDateNum(String searchIssueDateNum) {
    this.searchIssueDateNum = searchIssueDateNum;
  }

}
