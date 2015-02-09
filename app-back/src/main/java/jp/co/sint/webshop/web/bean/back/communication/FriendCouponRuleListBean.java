package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060310:キャンペーン管理のデータモデルです。
 * 
 * @author OB.
 */
public class FriendCouponRuleListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue = new PagerValue();

  /** soukai add 2012/01/13 ob start */
  /** 优惠券规则编号 */
  @AlphaNum2
  @Length(16)
  @Metadata(name = "优惠券规则编号", order = 1)
  private String searchCouponCode;

  /** soukai add 2012/01/13 ob end */

  /** 优惠券规则名称 */
  @Length(40)
  @Metadata(name = "优惠券规则名称", order = 2)
  private String searchCouponName;

  /** 优惠券类别 */
  @Digit
  @Metadata(name = "优惠券类别", order = 3)
  private String searchCouponType;

  /** 优惠券利用开始日时(From) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "发行开始期间(From)", order = 9)
  private String searchMinIssueStartDatetimeFrom;

  /** 优惠券利用开始日时(To) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "发行开始期间(To)", order = 10)
  private String searchMinIssueStartDatetimeTo;

  /** 优惠券利用结束日时(From) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "发行结束期间(From)", order = 11)
  private String searchMinIssueEndDatetimeFrom;

  /** 优惠券利用结束日时(To) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "发行结束期间(To)", order = 12)
  private String searchMinIssueEndDatetimeTo;

  @Digit
  @Metadata(name = "发行可能日期区分", order = 13)
  private String searchIssueDateType;

  @Metadata(name = "发行月份值", order = 14)
  private String searchIssueDateNum;

  private List<FriendCouponRuleBean> couponList = new ArrayList<FriendCouponRuleBean>();

  private List<CodeAttribute> couponActivityStatusList = new ArrayList<CodeAttribute>();

  private List<String> checkedCouponCodeList = new ArrayList<String>();

  /** 削除ボタン表示有無 */
  private boolean deleteButtonDisplayFlg;

  /** 新規登録ボタン表示有無 */
  private boolean registerNewDisplayFlg;

  private boolean linkNewDisplayFlg;

  private List<CodeAttribute> couponTypes = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> couponIssueTypes = new ArrayList<CodeAttribute>();

  private String displayMode;

  // 删除按钮
  private boolean deleteButtonFlg;

  // 搜索发行可能类型
  private List<CodeAttribute> issueDateTypes = new ArrayList<CodeAttribute>();

  /**
   * U1060310:キャンペーン管理のサブモデルです。
   * 
   * @author OB.
   */
  public static class FriendCouponRuleBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String friendCouponRuleNo;

    private String friendCouponRuleCn;

    private String friendCouponRuleJp;

    private String friendCouponRuleEn;

    private String issueDateType;

    private String issueDateNum;

    private String issueStartDate;

    private String issueEndDate;

    private String orderHistory;

    private String couponAmountNumeric;

    private String personalUseLimit;

    private String siteUseLimit;

    private String minUseOrderAmount;

    private String useValidType;

    private String useValidNum;

    private String applicableObjects;

    private String obtainPoint;

    private String fixChar;

    // 比例
    private String ratio;

    private String issueObtainPoint;

    private String formerUsePoint;

    private String latterUsePoint;

    // 优惠券种别
    private String coupon_issue_type;

    // 位数
    private String couponUseNum;

    // 优惠券使用最大购买金额
    private String maxUseOrderAmount;

    /**
     * @return the friendCouponRuleNo
     */
    public String getFriendCouponRuleNo() {
      return friendCouponRuleNo;
    }

    /**
     * @param friendCouponRuleNo
     *          the friendCouponRuleNo to set
     */
    public void setFriendCouponRuleNo(String friendCouponRuleNo) {
      this.friendCouponRuleNo = friendCouponRuleNo;
    }

    /**
     * @return the friendCouponRuleCn
     */
    public String getFriendCouponRuleCn() {
      return friendCouponRuleCn;
    }

    /**
     * @param friendCouponRuleCn
     *          the friendCouponRuleCn to set
     */
    public void setFriendCouponRuleCn(String friendCouponRuleCn) {
      this.friendCouponRuleCn = friendCouponRuleCn;
    }

    /**
     * @return the friendCouponRuleJp
     */
    public String getFriendCouponRuleJp() {
      return friendCouponRuleJp;
    }

    /**
     * @param friendCouponRuleJp
     *          the friendCouponRuleJp to set
     */
    public void setFriendCouponRuleJp(String friendCouponRuleJp) {
      this.friendCouponRuleJp = friendCouponRuleJp;
    }

    /**
     * @return the friendCouponRuleEn
     */
    public String getFriendCouponRuleEn() {
      return friendCouponRuleEn;
    }

    /**
     * @param friendCouponRuleEn
     *          the friendCouponRuleEn to set
     */
    public void setFriendCouponRuleEn(String friendCouponRuleEn) {
      this.friendCouponRuleEn = friendCouponRuleEn;
    }

    /**
     * @return the issueDateType
     */
    public String getIssueDateType() {
      return issueDateType;
    }

    /**
     * @param issueDateType
     *          the issueDateType to set
     */
    public void setIssueDateType(String issueDateType) {
      this.issueDateType = issueDateType;
    }

    /**
     * @return the issueDateNum
     */
    public String getIssueDateNum() {
      return issueDateNum;
    }

    /**
     * @param issueDateNum
     *          the issueDateNum to set
     */
    public void setIssueDateNum(String issueDateNum) {
      this.issueDateNum = issueDateNum;
    }

    /**
     * @return the issueStartDate
     */
    public String getIssueStartDate() {
      return issueStartDate;
    }

    /**
     * @param issueStartDate
     *          the issueStartDate to set
     */
    public void setIssueStartDate(String issueStartDate) {
      this.issueStartDate = issueStartDate;
    }

    /**
     * @return the issueEndDate
     */
    public String getIssueEndDate() {
      return issueEndDate;
    }

    /**
     * @param issueEndDate
     *          the issueEndDate to set
     */
    public void setIssueEndDate(String issueEndDate) {
      this.issueEndDate = issueEndDate;
    }

    /**
     * @return the orderHistory
     */
    public String getOrderHistory() {
      return orderHistory;
    }

    /**
     * @param orderHistory
     *          the orderHistory to set
     */
    public void setOrderHistory(String orderHistory) {
      this.orderHistory = orderHistory;
    }

    /**
     * @return the couponAmountNumeric
     */
    public String getCouponAmountNumeric() {
      return couponAmountNumeric;
    }

    /**
     * @param couponAmountNumeric
     *          the couponAmountNumeric to set
     */
    public void setCouponAmountNumeric(String couponAmountNumeric) {
      this.couponAmountNumeric = couponAmountNumeric;
    }

    /**
     * @return the personalUseLimit
     */
    public String getPersonalUseLimit() {
      return personalUseLimit;
    }

    /**
     * @param personalUseLimit
     *          the personalUseLimit to set
     */
    public void setPersonalUseLimit(String personalUseLimit) {
      this.personalUseLimit = personalUseLimit;
    }

    /**
     * @return the siteUseLimit
     */
    public String getSiteUseLimit() {
      return siteUseLimit;
    }

    /**
     * @param siteUseLimit
     *          the siteUseLimit to set
     */
    public void setSiteUseLimit(String siteUseLimit) {
      this.siteUseLimit = siteUseLimit;
    }

    /**
     * @return the minUseOrderAmount
     */
    public String getMinUseOrderAmount() {
      return minUseOrderAmount;
    }

    /**
     * @param minUseOrderAmount
     *          the minUseOrderAmount to set
     */
    public void setMinUseOrderAmount(String minUseOrderAmount) {
      this.minUseOrderAmount = minUseOrderAmount;
    }

    /**
     * @return the useValidType
     */
    public String getUseValidType() {
      return useValidType;
    }

    /**
     * @param useValidType
     *          the useValidType to set
     */
    public void setUseValidType(String useValidType) {
      this.useValidType = useValidType;
    }

    /**
     * @return the useValidNum
     */
    public String getUseValidNum() {
      return useValidNum;
    }

    /**
     * @param useValidNum
     *          the useValidNum to set
     */
    public void setUseValidNum(String useValidNum) {
      this.useValidNum = useValidNum;
    }

    /**
     * @return the applicableObjects
     */
    public String getApplicableObjects() {
      return applicableObjects;
    }

    /**
     * @param applicableObjects
     *          the applicableObjects to set
     */
    public void setApplicableObjects(String applicableObjects) {
      this.applicableObjects = applicableObjects;
    }

    /**
     * @return the obtainPoint
     */
    public String getObtainPoint() {
      return obtainPoint;
    }

    /**
     * @param obtainPoint
     *          the obtainPoint to set
     */
    public void setObtainPoint(String obtainPoint) {
      this.obtainPoint = obtainPoint;
    }

    /**
     * @return the fixChar
     */
    public String getFixChar() {
      return fixChar;
    }

    /**
     * @param fixChar
     *          the fixChar to set
     */
    public void setFixChar(String fixChar) {
      this.fixChar = fixChar;
    }

    /**
     * @return the ratio
     */
    public String getRatio() {
      return ratio;
    }

    /**
     * @param ratio
     *          the ratio to set
     */
    public void setRatio(String ratio) {
      this.ratio = ratio;
    }

    /**
     * @return the issueObtainPoint
     */
    public String getIssueObtainPoint() {
      return issueObtainPoint;
    }

    /**
     * @param issueObtainPoint
     *          the issueObtainPoint to set
     */
    public void setIssueObtainPoint(String issueObtainPoint) {
      this.issueObtainPoint = issueObtainPoint;
    }

    /**
     * @return the formerUsePoint
     */
    public String getFormerUsePoint() {
      return formerUsePoint;
    }

    /**
     * @param formerUsePoint
     *          the formerUsePoint to set
     */
    public void setFormerUsePoint(String formerUsePoint) {
      this.formerUsePoint = formerUsePoint;
    }

    /**
     * @return the latterUsePoint
     */
    public String getLatterUsePoint() {
      return latterUsePoint;
    }

    /**
     * @param latterUsePoint
     *          the latterUsePoint to set
     */
    public void setLatterUsePoint(String latterUsePoint) {
      this.latterUsePoint = latterUsePoint;
    }

    /**
     * @return the coupon_issue_type
     */
    public String getCoupon_issue_type() {
      return coupon_issue_type;
    }

    /**
     * @param coupon_issue_type
     *          the coupon_issue_type to set
     */
    public void setCoupon_issue_type(String coupon_issue_type) {
      this.coupon_issue_type = coupon_issue_type;
    }

    /**
     * @return the couponUseNum
     */
    public String getCouponUseNum() {
      return couponUseNum;
    }

    /**
     * @param couponUseNum
     *          the couponUseNum to set
     */
    public void setCouponUseNum(String couponUseNum) {
      this.couponUseNum = couponUseNum;
    }

    /**
     * @return the maxUseOrderAmount
     */
    public String getMaxUseOrderAmount() {
      return maxUseOrderAmount;
    }

    /**
     * @param maxUseOrderAmount
     *          the maxUseOrderAmount to set
     */
    public void setMaxUseOrderAmount(String maxUseOrderAmount) {
      this.maxUseOrderAmount = maxUseOrderAmount;
    }

  }

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
   * searchCouponNameを取得します。
   * 
   * @return searchCouponName
   */
  public String getSearchCouponName() {
    return searchCouponName;
  }

  /**
   * searchCouponNameを設定します。
   * 
   * @param searchCouponName
   *          searchCouponName
   */
  public void setSearchCouponName(String searchCouponName) {
    this.searchCouponName = searchCouponName;
  }

  /**
   * searchCouponTypeを取得します。
   * 
   * @return searchCouponType
   */
  public String getSearchCouponType() {
    return searchCouponType;
  }

  /**
   * searchCouponTypeを設定します。
   * 
   * @param searchCouponType
   *          searchCouponType
   */
  public void setSearchCouponType(String searchCouponType) {
    this.searchCouponType = searchCouponType;
  }

  /**
   * checkedCouponCodeListを取得します。
   * 
   * @return checkedCouponCodeList
   */
  public List<String> getCheckedCouponCodeList() {
    return checkedCouponCodeList;
  }

  /**
   * checkedCouponCodeListを設定します。
   * 
   * @param checkedCouponCodeList
   *          checkedCouponCodeList
   */
  public void setCheckedCouponCodeList(List<String> checkedCouponCodeList) {
    this.checkedCouponCodeList = checkedCouponCodeList;
  }

  /**
   * deleteButtonDisplayFlgを取得します。
   * 
   * @return deleteButtonDisplayFlg
   */
  public boolean getDeleteButtonDisplayFlg() {
    return deleteButtonDisplayFlg;
  }

  /**
   * deleteButtonDisplayFlgを設定します。
   * 
   * @param deleteButtonDisplayFlg
   *          deleteButtonDisplayFlg
   */
  public void setDeleteButtonDisplayFlg(boolean deleteButtonDisplayFlg) {
    this.deleteButtonDisplayFlg = deleteButtonDisplayFlg;
  }

  /**
   * registerNewDisplayFlgを取得します。
   * 
   * @return registerNewDisplayFlg
   */
  public boolean getRegisterNewDisplayFlg() {
    return registerNewDisplayFlg;
  }

  /**
   * registerNewDisplayFlgを設定します。
   * 
   * @param registerNewDisplayFlg
   *          registerNewDisplayFlg
   */
  public void setRegisterNewDisplayFlg(boolean registerNewDisplayFlg) {
    this.registerNewDisplayFlg = registerNewDisplayFlg;
  }

  /**
   * couponTypesを取得します。
   * 
   * @return couponTypes
   */
  public List<CodeAttribute> getCouponTypes() {
    return couponTypes;
  }

  /**
   * couponTypesを設定します。
   * 
   * @param couponTypes
   *          couponTypes
   */
  public void setCouponTypes(List<CodeAttribute> couponTypes) {
    this.couponTypes = couponTypes;
  }

  /**
   * couponIssueTypesを取得します。
   * 
   * @return couponIssueTypes
   */
  public List<CodeAttribute> getCouponIssueTypes() {
    return couponIssueTypes;
  }

  /**
   * couponIssueTypesを設定します。
   * 
   * @param couponIssueTypes
   *          couponIssueTypes
   */
  public void setCouponIssueTypes(List<CodeAttribute> couponIssueTypes) {
    this.couponIssueTypes = couponIssueTypes;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {

  }

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * pagerValueを設定します。
   * 
   * @param pagerValue
   *          設定する pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {

    // soukai add 2012/01/13 ob start
    setSearchCouponCode(reqparam.get("searchCouponCode"));
    // soukai add 2012/01/13 ob end
    setSearchCouponName(reqparam.get("searchCouponName"));
    setSearchCouponType(reqparam.get("searchCouponType"));
    setSearchMinIssueStartDatetimeFrom(reqparam.getDateTimeString("searchMinIssueStartDatetimeFrom"));
    setSearchMinIssueStartDatetimeTo(reqparam.getDateTimeString("searchMinIssueStartDatetimeTo"));
    setSearchMinIssueEndDatetimeFrom(reqparam.getDateTimeString("searchMinIssueEndDatetimeFrom"));
    setSearchMinIssueEndDatetimeTo(reqparam.getDateTimeString("searchMinIssueEndDatetimeTo"));
    setSearchIssueDateType(reqparam.get("searchIssueDateType"));
    setSearchIssueDateNum(reqparam.get("searchIssueDateNum"));
    this.setCheckedCouponCodeList(Arrays.asList(reqparam.getAll("couponCode")));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1061110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.FriendCouponRuleBean.0");
  }

  public boolean isLinkNewDisplayFlg() {
    return linkNewDisplayFlg;
  }

  public void setLinkNewDisplayFlg(boolean linkNewDisplayFlg) {
    this.linkNewDisplayFlg = linkNewDisplayFlg;
  }

  public List<CodeAttribute> getCouponActivityStatusList() {
    return couponActivityStatusList;
  }

  public void setCouponActivityStatusList(List<CodeAttribute> couponActivityStatusList) {
    this.couponActivityStatusList = couponActivityStatusList;
  }

  /**
   * @return the couponList
   */
  public List<FriendCouponRuleBean> getCouponList() {
    return couponList;
  }

  /**
   * @param couponList
   *          the couponList to set
   */
  public void setCouponList(List<FriendCouponRuleBean> couponList) {
    this.couponList = couponList;
  }

  /**
   * @return the displayMode
   */
  public String getDisplayMode() {
    return displayMode;
  }

  /**
   * @param displayMode
   *          the displayMode to set
   */
  public void setDisplayMode(String displayMode) {
    this.displayMode = displayMode;
  }

  /**
   * @return the deleteButtonFlg
   */
  public boolean isDeleteButtonFlg() {
    return deleteButtonFlg;
  }

  /**
   * @param deleteButtonFlg
   *          the deleteButtonFlg to set
   */
  public void setDeleteButtonFlg(boolean deleteButtonFlg) {
    this.deleteButtonFlg = deleteButtonFlg;
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

  /**
   * @return the issueDateTypes
   */
  public List<CodeAttribute> getIssueDateTypes() {
    return issueDateTypes;
  }

  /**
   * @param issueDateTypes
   *          the issueDateTypes to set
   */
  public void setIssueDateTypes(List<CodeAttribute> issueDateTypes) {
    this.issueDateTypes = issueDateTypes;
  }

}
