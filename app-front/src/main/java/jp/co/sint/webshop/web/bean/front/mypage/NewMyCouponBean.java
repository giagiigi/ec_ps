package jp.co.sint.webshop.web.bean.front.mypage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * 优惠券信息
 * 
 * @author System Integrator Corp.
 */
public class NewMyCouponBean extends UIFrontBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<MyCouponHistoryDetail> couponIssueList = new ArrayList<MyCouponHistoryDetail>();

  /** 是否存在查询信息 */
  private boolean hasValue = Boolean.TRUE;

  private List<FriendCouponBeanDetail> friendList = new ArrayList<FriendCouponBeanDetail>();

  private List<FriendCouponBeanDetail> issuedFriendList = new ArrayList<FriendCouponBeanDetail>();

  private List<FriendCouponBeanDetail> unissuedFriendList = new ArrayList<FriendCouponBeanDetail>();

  // 有无优惠券权限
  private boolean issueFlg = false;

  /** 手机 */
  @Length(11)
  @MobileNumber
  @Metadata(name = "手机")
  private String tell;

  /** 用户语言 */
  private String languageCode;

  /** 验证码 */
  @Length(6)
  @Digit
  @Metadata(name = "验证码")
  private String authCode;

  private PagerValue pagerValue;

  
  private String mobileNumber;
  /** 有效积分 */
  private Long goodPoint;

  private List<PrivateCouponExchangeBeanDetail> privateList = new ArrayList<PrivateCouponExchangeBeanDetail>();

  private List<PrivateCouponUseDetail> usePrivateList = new ArrayList<PrivateCouponUseDetail>();

  private List<FriendIssueHistoryDetail> issueFriendList = new ArrayList<FriendIssueHistoryDetail>();
  
  //20140923 页面显示选项卡
  private String tabIndex;

  /**
   * クーポン詳細情報
   * 
   * @author System Integrator Corp.
   */
  public static class MyCouponHistoryDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** クーポン名称 */
    private String couponName;

    /** クーポン名称(英文) */
    private String couponNameEn;

    /** 优惠券名称(日文) */
    private String couponNameJp;

    /** 优惠金額 */
    private String couponAmount;

    /** 使用起始金額 */
    private String couponBeginAmount;

    /** クーポン利用开始日时 */
    private String useStartDatetime;

    /** クーポン利用结束日时 */
    private String useEndDatetime;

    /** 使用状態 */
    private String useStatus;

    /** 固定金额优惠FLG */
    private boolean fixedFlg = Boolean.FALSE;

    // 优惠券编号
    private String couponCode;

    /**
     * 取得クーポン名称
     * 
     * @return
     */
    public String getCouponName() {
      return couponName;
    }

    /**
     * 设定クーポン名称
     * 
     * @param couponName
     */
    public void setCouponName(String couponName) {
      this.couponName = couponName;
    }

    /**
     * 取得优惠金额
     * 
     * @return
     */
    public String getCouponAmount() {
      return couponAmount;
    }

    /**
     * 設定优惠金额
     * 
     * @param couponAmount
     */
    public void setCouponAmount(String couponAmount) {
      this.couponAmount = couponAmount;
    }

    /**
     * 取得使用起始金额
     * 
     * @return
     */
    public String getCouponBeginAmount() {
      return couponBeginAmount;
    }

    /**
     * 設定使用起始金额
     * 
     * @param couponBeginAmount
     */
    public void setCouponBeginAmount(String couponBeginAmount) {
      this.couponBeginAmount = couponBeginAmount;
    }

    /**
     * 取得クーポン利用开始日时
     * 
     * @return
     */
    public String getUseStartDatetime() {
      return useStartDatetime;
    }

    /**
     * 設定クーポン利用开始日时
     * 
     * @param useStartDatetime
     */
    public void setUseStartDatetime(String useStartDatetime) {
      this.useStartDatetime = useStartDatetime;
    }

    /**
     * 取得クーポン利用结束日时
     * 
     * @return
     */
    public String getUseEndDatetime() {
      return useEndDatetime;
    }

    /**
     * 設定クーポン利用结束日时
     * 
     * @param useEndDatetime
     */
    public void setUseEndDatetime(String useEndDatetime) {
      this.useEndDatetime = useEndDatetime;
    }

    /**
     * 取得使用状態
     * 
     * @return
     */
    public String getUseStatus() {
      return useStatus;
    }

    /**
     * 設定使用状態
     * 
     * @param useStatus
     */
    public void setUseStatus(String useStatus) {
      this.useStatus = useStatus;
    }

    /**
     * 取得クーポン名称(英文)
     * 
     * @return
     */
    public String getCouponNameEn() {
      return couponNameEn;
    }

    /**
     * 設定クーポン名称(英文)
     * 
     * @param couponNameEn
     */
    public void setCouponNameEn(String couponNameEn) {
      this.couponNameEn = couponNameEn;
    }

    /**
     * 取得クーポン名称(日文)
     * 
     * @return
     */
    public String getCouponNameJp() {
      return couponNameJp;
    }

    /**
     * 設定クーポン名称(日文)
     * 
     * @param couponNameJp
     */
    public void setCouponNameJp(String couponNameJp) {
      this.couponNameJp = couponNameJp;
    }

    /**
     * 固定金额优惠FLG
     * 
     * @return
     */
    public boolean isFixedFlg() {
      return fixedFlg;
    }

    /**
     * 固定金额优惠FLG
     * 
     * @param fixedFlg
     */
    public void setFixedFlg(boolean fixedFlg) {
      this.fixedFlg = fixedFlg;
    }

    /**
     * @return the couponCode
     */
    public String getCouponCode() {
      return couponCode;
    }

    /**
     * @param couponCode
     *          the couponCode to set
     */
    public void setCouponCode(String couponCode) {
      this.couponCode = couponCode;
    }

  }

  public static class FriendCouponBeanDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** 优惠金额 */
    private String couponAmount;

    /** 优惠券规则编号 */
    private String couponCode;

    private String friendCouponRuleNo;

    /** 优惠券规则编号 */
    private String friendCouponRule;

    /** 优惠券规则编号 */
    private String friendCouponRuleEn;

    /** 优惠券规则编号 */
    private String friendCouponRuleJp;

    /** 利用优惠券最小购买金额 */
    private String minUseOrderAmount;

    // 2013/04/12 优惠券对应 ob add start
    /** 利用优惠券最小购买金额 */
    private String applicableObjects;

    // 2013/04/12 优惠券对应 ob add end

    /** 优惠券利用开始日时 */
    private String issueStartDate;

    /** 优惠券利用结束日时 */
    private String issueEndDate;

    /** 发行状态 */
    private String issueState;

    /** 发行状态 */
    private String url;

    /** 利用有效期类别 */
    private Long useValidType;

    /** 利用有效期值 */
    private Long useValidNum;

    // 是否可发行
    private boolean ableFlg = false;

    // 是否已经发行
    private boolean issueFlg = false;

    // 是否过期
    private boolean urlDate = false;

    // 优惠类型
    private Long issueType;

    // 优惠比例
    private String ratio;

    // 最大优惠金额
    private String maxDiscountPrice;

    /**
     * @return the couponAmount
     */
    public String getCouponAmount() {
      return couponAmount;
    }

    /**
     * @param couponAmount
     *          the couponAmount to set
     */
    public void setCouponAmount(String couponAmount) {
      this.couponAmount = couponAmount;
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
     * @return the issueFlg
     */
    public boolean isIssueFlg() {
      return issueFlg;
    }

    /**
     * @param issueFlg
     *          the issueFlg to set
     */
    public void setIssueFlg(boolean issueFlg) {
      this.issueFlg = issueFlg;
    }

    /**
     * @return the issueState
     */
    public String getIssueState() {
      return issueState;
    }

    /**
     * @param issueState
     *          the issueState to set
     */
    public void setIssueState(String issueState) {
      this.issueState = issueState;
    }

    /**
     * @return the url
     */
    public String getUrl() {
      return url;
    }

    /**
     * @param url
     *          the url to set
     */
    public void setUrl(String url) {
      this.url = url;
    }

    /**
     * @return the friendCouponRule
     */
    public String getFriendCouponRule() {
      return friendCouponRule;
    }

    /**
     * @param friendCouponRule
     *          the friendCouponRule to set
     */
    public void setFriendCouponRule(String friendCouponRule) {
      this.friendCouponRule = friendCouponRule;
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
     * @return the couponCode
     */
    public String getCouponCode() {
      return couponCode;
    }

    /**
     * @param couponCode
     *          the couponCode to set
     */
    public void setCouponCode(String couponCode) {
      this.couponCode = couponCode;
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
     * @return the applicableObjectsName
     */
    public String getApplicableObjects() {
      return applicableObjects;
    }

    /**
     * @param applicableObjectsName
     *          the applicableObjectsName to set
     */
    public void setApplicableObjects(String applicableObjects) {
      this.applicableObjects = applicableObjects;
    }

    /**
     * @return the urlDate
     */
    public boolean isUrlDate() {
      return urlDate;
    }

    /**
     * @param urlDate
     *          the urlDate to set
     */
    public void setUrlDate(boolean urlDate) {
      this.urlDate = urlDate;
    }

    public String getDisplayImgSize() {

      if (StringUtil.hasValue(getCouponAmount())) {
        return NumUtil.toString(new BigDecimal(getCouponAmount()).setScale(0, BigDecimal.ROUND_HALF_UP));
      }
      return "0";
    }

    /**
     * @return the useValidType
     */
    public Long getUseValidType() {
      return useValidType;
    }

    /**
     * @param useValidType
     *          the useValidType to set
     */
    public void setUseValidType(Long useValidType) {
      this.useValidType = useValidType;
    }

    /**
     * @return the useValidNum
     */
    public Long getUseValidNum() {
      return useValidNum;
    }

    /**
     * @param useValidNum
     *          the useValidNum to set
     */
    public void setUseValidNum(Long useValidNum) {
      this.useValidNum = useValidNum;
    }

    /**
     * @return the ableFlg
     */
    public boolean isAbleFlg() {
      return ableFlg;
    }

    /**
     * @param ableFlg
     *          the ableFlg to set
     */
    public void setAbleFlg(boolean ableFlg) {
      this.ableFlg = ableFlg;
    }

    /**
     * @return the issueType
     */
    public Long getIssueType() {
      return issueType;
    }

    /**
     * @param issueType
     *          the issueType to set
     */
    public void setIssueType(Long issueType) {
      this.issueType = issueType;
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
     * @return the maxDiscountPrice
     */
    public String getMaxDiscountPrice() {
      return maxDiscountPrice;
    }

    /**
     * @param maxDiscountPrice
     *          the maxDiscountPrice to set
     */
    public void setMaxDiscountPrice(String maxDiscountPrice) {
      this.maxDiscountPrice = maxDiscountPrice;
    }

  }

  public static class PrivateCouponUseDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    // 优惠金额
    private String couponAmount;

    // 优惠券利用最小购买金额
    private String minUseOrderAmount;

    // 有效期限(开始)
    private String minUseStartDatetime;

    // 有效期限(结束)
    private String minUseEndDatetime;

    // 优惠劵规则代码
    private String couponCode;

    // 优惠劵规则名称
    private String couponName;

    // Link表示制御Flag
    private Boolean displayLink = false;

    // 兑换所需点数
    private String exchangePointAmount;

    // 优惠券利用结束日期（金额、特别会员使用）
    private String minUseEndNum;

    //发行类型,比例，
    private String issueType;

    //优惠比例
    private String couponProportion;
    
    public String getCouponAmount() {
      return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
      this.couponAmount = couponAmount;
    }

    public String getMinUseOrderAmount() {
      return minUseOrderAmount;
    }

    public void setMinUseOrderAmount(String minUseOrderAmount) {
      this.minUseOrderAmount = minUseOrderAmount;
    }

    public String getMinUseStartDatetime() {
      return minUseStartDatetime;
    }

    public void setMinUseStartDatetime(String minUseStartDatetime) {
      this.minUseStartDatetime = minUseStartDatetime;
    }

    public String getMinUseEndDatetime() {
      return minUseEndDatetime;
    }

    public void setMinUseEndDatetime(String minUseEndDatetime) {
      this.minUseEndDatetime = minUseEndDatetime;
    }

    public String getCouponCode() {
      return couponCode;
    }

    public void setCouponCode(String couponCode) {
      this.couponCode = couponCode;
    }

    public String getCouponName() {
      return couponName;
    }

    public void setCouponName(String couponName) {
      this.couponName = couponName;
    }

    public Boolean getDisplayLink() {
      return displayLink;
    }

    public void setDisplayLink(Boolean displayLink) {
      this.displayLink = displayLink;
    }

    public String getExchangePointAmount() {
      return exchangePointAmount;
    }

    public void setExchangePointAmount(String exchangePointAmount) {
      this.exchangePointAmount = exchangePointAmount;
    }

    public String getMinUseEndNum() {
      return minUseEndNum;
    }

    public void setMinUseEndNum(String minUseEndNum) {
      this.minUseEndNum = minUseEndNum;
    }

    
    /**
     * @return the issueType
     */
    public String getIssueType() {
      return issueType;
    }

    
    /**
     * @param issueType the issueType to set
     */
    public void setIssueType(String issueType) {
      this.issueType = issueType;
    }

    
    /**
     * @return the couponProportion
     */
    public String getCouponProportion() {
      return couponProportion;
    }

    
    /**
     * @param couponProportion the couponProportion to set
     */
    public void setCouponProportion(String couponProportion) {
      this.couponProportion = couponProportion;
    }
    
    
  }

  public static class PrivateCouponExchangeBeanDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    // 顾客名称
    private String customerName;

    // 发行日期
    private String couponIssueDate;

    // 优惠劵编号
    private String couponCode;

    // 优惠劵金额
    private String couponAmount;

    // 所得积分
    private Long allPoint;

    // 利用回数
    private Long couponNum;

    // 订单号
    private String orderNo;

    /**
     * @return the customerName
     */
    public String getCustomerName() {
      return customerName;
    }

    /**
     * @param customerName
     *          the customerName to set
     */
    public void setCustomerName(String customerName) {
      this.customerName = customerName;
    }

    /**
     * @return the couponIssueDate
     */
    public String getCouponIssueDate() {
      return couponIssueDate;
    }

    /**
     * @param couponIssueDate
     *          the couponIssueDate to set
     */
    public void setCouponIssueDate(String couponIssueDate) {
      this.couponIssueDate = couponIssueDate;
    }

    /**
     * @return the couponCode
     */
    public String getCouponCode() {
      return couponCode;
    }

    /**
     * @param couponCode
     *          the couponCode to set
     */
    public void setCouponCode(String couponCode) {
      this.couponCode = couponCode;
    }

    /**
     * @return the couponAmount
     */
    public String getCouponAmount() {
      return couponAmount;
    }

    /**
     * @param couponAmount
     *          the couponAmount to set
     */
    public void setCouponAmount(String couponAmount) {
      this.couponAmount = couponAmount;
    }

    /**
     * @return the allPoint
     */
    public Long getAllPoint() {
      return allPoint;
    }

    /**
     * @param allPoint
     *          the allPoint to set
     */
    public void setAllPoint(Long allPoint) {
      this.allPoint = allPoint;
    }

    public Long getCouponNum() {
      return couponNum;
    }

    public void setCouponNum(Long couponNum) {
      this.couponNum = couponNum;
    }

    /**
     * @return the orderNo
     */
    public String getOrderNo() {
      return orderNo;
    }

    /**
     * @param orderNo
     *          the orderNo to set
     */
    public void setOrderNo(String orderNo) {
      this.orderNo = orderNo;
    }

  }

  public static class FriendIssueHistoryDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    // 发行日期
    private String issueDate;

    // 优惠券编号
    private String couponCode;

    // 优惠券类型
    private String couponIssueType;

    // 优惠金额
    private String couponAmount;

    // 优惠比例

    private String couponProportion;

    // 发行积分
    private String issueObtainPoint;

    /**
     * @return the issueDate
     */
    public String getIssueDate() {
      return issueDate;
    }

    /**
     * @param issueDate
     *          the issueDate to set
     */
    public void setIssueDate(String issueDate) {
      this.issueDate = issueDate;
    }

    /**
     * @return the couponCode
     */
    public String getCouponCode() {
      return couponCode;
    }

    /**
     * @param couponCode
     *          the couponCode to set
     */
    public void setCouponCode(String couponCode) {
      this.couponCode = couponCode;
    }

    /**
     * @return the couponIssueType
     */
    public String getCouponIssueType() {
      return couponIssueType;
    }

    /**
     * @param couponIssueType
     *          the couponIssueType to set
     */
    public void setCouponIssueType(String couponIssueType) {
      this.couponIssueType = couponIssueType;
    }

    /**
     * @return the couponAmount
     */
    public String getCouponAmount() {
      return couponAmount;
    }

    /**
     * @param couponAmount
     *          the couponAmount to set
     */
    public void setCouponAmount(String couponAmount) {
      this.couponAmount = couponAmount;
    }

    /**
     * @return the couponProportion
     */
    public String getCouponProportion() {
      return couponProportion;
    }

    /**
     * @param couponProportion
     *          the couponProportion to set
     */
    public void setCouponProportion(String couponProportion) {
      this.couponProportion = couponProportion;
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

  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    this.setTell(reqparam.get("mobileNumber"));
    this.setAuthCode(reqparam.get("authCode"));
  }

  public List<MyCouponHistoryDetail> getCouponIssueList() {
    return couponIssueList;
  }

  public void setCouponIssueList(List<MyCouponHistoryDetail> couponIssueList) {
    this.couponIssueList = couponIssueList;
  }

  /**
   * 是否存在查询信息取得
   * 
   * @return
   */
  public boolean isHasValue() {
    return hasValue;
  }

  /**
   * 是否存在查询信息设定
   * 
   * @param hasValue
   */
  public void setHasValue(boolean hasValue) {
    this.hasValue = hasValue;
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "";
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
   *          pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.mypage.NewMyCouponsBean.0");
  }

  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString(
    "web.bean.front.mypage.NewCouponListBean.1"), "/app/mypage/mypage"));
    topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.NewCouponListBean.0"),
        "/app/mypage/my_coupon_history/init"));
    return topicPath;
  }

  /**
   * @return the issueFlg
   */
  public boolean isIssueFlg() {
    return issueFlg;
  }

  /**
   * @param issueFlg
   *          the issueFlg to set
   */
  public void setIssueFlg(boolean issueFlg) {
    this.issueFlg = issueFlg;
  }

  /**
   * @return the tell
   */
  public String getTell() {
    return tell;
  }

  /**
   * @param tell
   *          the tell to set
   */
  public void setTell(String tell) {
    this.tell = tell;
  }

  /**
   * @return the languageCode
   */
  public String getLanguageCode() {
    return languageCode;
  }

  /**
   * @param languageCode
   *          the languageCode to set
   */
  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  /**
   * @return the authCode
   */
  public String getAuthCode() {
    return authCode;
  }

  /**
   * @param authCode
   *          the authCode to set
   */
  public void setAuthCode(String authCode) {
    this.authCode = authCode;
  }

  /**
   * @return the friendList
   */
  public List<FriendCouponBeanDetail> getFriendList() {
    return friendList;
  }

  /**
   * @param friendList
   *          the friendList to set
   */
  public void setFriendList(List<FriendCouponBeanDetail> friendList) {
    this.friendList = friendList;
  }

  /**
   * @return the issuedFriendList
   */
  public List<FriendCouponBeanDetail> getIssuedFriendList() {
    return issuedFriendList;
  }

  /**
   * @param issuedFriendList
   *          the issuedFriendList to set
   */
  public void setIssuedFriendList(List<FriendCouponBeanDetail> issuedFriendList) {
    this.issuedFriendList = issuedFriendList;
  }

  /**
   * @return the unissuedFriendList
   */
  public List<FriendCouponBeanDetail> getUnissuedFriendList() {
    return unissuedFriendList;
  }

  /**
   * @param unissuedFriendList
   *          the unissuedFriendList to set
   */
  public void setUnissuedFriendList(List<FriendCouponBeanDetail> unissuedFriendList) {
    this.unissuedFriendList = unissuedFriendList;
  }

  /**
   * @return the goodPoint
   */
  public Long getGoodPoint() {
    return goodPoint;
  }

  /**
   * @param goodPoint
   *          the goodPoint to set
   */
  public void setGoodPoint(Long goodPoint) {
    this.goodPoint = goodPoint;
  }

  /**
   * @return the usePrivateList
   */
  public List<PrivateCouponUseDetail> getUsePrivateList() {
    return usePrivateList;
  }

  /**
   * @param usePrivateList
   *          the usePrivateList to set
   */
  public void setUsePrivateList(List<PrivateCouponUseDetail> usePrivateList) {
    this.usePrivateList = usePrivateList;
  }

  /**
   * @return the issueFriendList
   */
  public List<FriendIssueHistoryDetail> getIssueFriendList() {
    return issueFriendList;
  }

  /**
   * @param issueFriendList
   *          the issueFriendList to set
   */
  public void setIssueFriendList(List<FriendIssueHistoryDetail> issueFriendList) {
    this.issueFriendList = issueFriendList;
  }

  /**
   * @return the privateList
   */
  public List<PrivateCouponExchangeBeanDetail> getPrivateList() {
    return privateList;
  }

  /**
   * @param privateList
   *          the privateList to set
   */
  public void setPrivateList(List<PrivateCouponExchangeBeanDetail> privateList) {
    this.privateList = privateList;
  }

  
  /**
   * @return the tabIndex
   */
  public String getTabIndex() {
    return tabIndex;
  }

  
  /**
   * @param tabIndex the tabIndex to set
   */
  public void setTabIndex(String tabIndex) {
    this.tabIndex = tabIndex;
  }

  
  /**
   * @return the mobileNumber
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  
  /**
   * @param mobileNumber the mobileNumber to set
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }
  
  @Override
  public void setSubJspId() {
    addSubJspId("/common/header");
    addSubJspId("/catalog/topic_path");
  }

}
