package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class FriendCouponRuleBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @AlphaNum2
  @Required
  @Length(8)
  @Metadata(name = "发行规则编号", order = 1)
  private String friendCouponRuleNo;

  @Required
  @Length(40)
  @Metadata(name = "发行规则中文名称", order = 2)
  private String friendCouponRuleCn;

  @Required
  @Length(40)
  @Metadata(name = "发行规则日文名称", order = 3)
  private String friendCouponRuleJp;

  @Required
  @Length(40)
  @Metadata(name = "发行规则英文名称", order = 4)
  private String friendCouponRuleEn;

  @Required
  @Length(1)
  @Metadata(name = "发行可能日期区分（0：每月 1：详细日期）", order = 5)
  private String issueDateType;

  @Length(2)
  @Metadata(name = "月份值", order = 6)
  private String issueDateNum;

  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "发行可能开始日期", order = 7)
  private String issueStartDate;

  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "发行可能结束日期", order = 8)
  private String issueEndDate;

  @Digit
  @Required
  @Length(8)
  @Metadata(name = "订购使用次数", order = 9)
  private String orderHistory;

  @Currency
  @Precision(precision = 10, scale = 2)
  @Length(10)
  @Metadata(name = "利用优惠券金额", order = 10)
  private String couponAmountNumeric;

  @Digit
  @Required
  @Length(8)
  @Metadata(name = "利用个人回数", order = 11)
  private String personalUseLimit;

  @Digit
  @Required
  @Length(8)
  @Metadata(name = "利用SITE回数", order = 12)
  private String siteUseLimit;

  @Currency
  @Precision(precision = 10, scale = 2)
  @Required
  @Length(10)
  @Metadata(name = "利用优惠券最小购买金额", order = 13)
  private String minUseOrderAmount;

  @Required
  @Length(1)
  @Metadata(name = "利用有效期类别（0：天，1：月）", order = 14)
  private String useValidType;

  @Digit
  @Required
  @Length(8)
  @Metadata(name = "利用有效期值", order = 15)
  private String useValidNum;

  @Required
  @Length(1)
  @Metadata(name = "利用限定（0:无限制 1:初次购买）", order = 16)
  private String applicableObjects;

  @Digit
  @Required
  @Length(8)
  @Metadata(name = "临界后获得积分", order = 17)
  private String obtainPoint;

  @Digit
  @Range(min = 0, max = 100)
  @Metadata(name = "比例", order = 18)
  private String ratio;
  
  @Digit
  @Required
  @Length(8)
  @Metadata(name = "发行获得积分", order = 19)
  private String issueObtainPoint;
  
  @Digit
  @Required
  @Length(8)
  @Metadata(name = "临界前获得积分", order = 20)
  private String formerUsePoint;
  

  
  @Digit
  @Required
  @Length(1)
  @Metadata(name = "优惠券种别", order = 22)
  private String couponIssueType;
  
  @Digit
  @Required
  @Length(8)
  @Metadata(name = "优惠临界人数", order = 23)
  private String couponUseNum;
  
  
  @Currency
  @Precision(precision = 10, scale = 2)
  @Length(10)
  @Metadata(name = "优惠券使用最大购买金额", order = 24)
  private String maxUseOrderAmount;
  
  @Required
  private String fixChar;

  private List<SelectFriendCouponRuleList> list = new ArrayList<SelectFriendCouponRuleList>();

  private boolean deleteButtonFlg;

  private PagerValue pagerValue = new PagerValue();

  private String displayMode;

  /**
   * U1061110:お知らせ編集のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class SelectFriendCouponRuleList implements Serializable {

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
    
    //优惠券种别
    private String coupon_issue_type;
    
    //位数
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
     * @param ratio the ratio to set
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
     * @param issueObtainPoint the issueObtainPoint to set
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
     * @param formerUsePoint the formerUsePoint to set
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
     * @param latterUsePoint the latterUsePoint to set
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
     * @param coupon_issue_type the coupon_issue_type to set
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
     * @param couponUseNum the couponUseNum to set
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
     * @param maxUseOrderAmount the maxUseOrderAmount to set
     */
    public void setMaxUseOrderAmount(String maxUseOrderAmount) {
      this.maxUseOrderAmount = maxUseOrderAmount;
    }

    
    
  }

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
   * @return the list
   */
  public List<SelectFriendCouponRuleList> getList() {
    return list;
  }

  /**
   * @param list
   *          the list to set
   */
  public void setList(List<SelectFriendCouponRuleList> list) {
    this.list = list;
  }

  /**
   * @return the pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * @param pagerValue
   *          the pagerValue to set
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
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

  
  /*
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {

  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    this.setIssueStartDate(reqparam.getDateTimeString("issueStartDate"));
    this.setIssueEndDate(reqparam.getDateTimeString("issueEndDate"));
    reqparam.copy(this);
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

  
  /**
   * @return the ratio
   */
  public String getRatio() {
    return ratio;
  }

  
  /**
   * @param ratio the ratio to set
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
   * @param issueObtainPoint the issueObtainPoint to set
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
   * @param formerUsePoint the formerUsePoint to set
   */
  public void setFormerUsePoint(String formerUsePoint) {
    this.formerUsePoint = formerUsePoint;
  }

  /**
   * @return the couponUseNum
   */
  public String getCouponUseNum() {
    return couponUseNum;
  }

  
  /**
   * @param couponUseNum the couponUseNum to set
   */
  public void setCouponUseNum(String couponUseNum) {
    this.couponUseNum = couponUseNum;
  }

  
  /**
   * @return the couponIssueType
   */
  public String getCouponIssueType() {
    return couponIssueType;
  }

  
  /**
   * @param couponIssueType the couponIssueType to set
   */
  public void setCouponIssueType(String couponIssueType) {
    this.couponIssueType = couponIssueType;
  }

  
  /**
   * @return the serialVersionUID
   */
  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  
  /**
   * @return the maxUseOrderAmount
   */
  public String getMaxUseOrderAmount() {
    return maxUseOrderAmount;
  }

  
  /**
   * @param maxUseOrderAmount the maxUseOrderAmount to set
   */
  public void setMaxUseOrderAmount(String maxUseOrderAmount) {
    this.maxUseOrderAmount = maxUseOrderAmount;
  }

  
 
}
