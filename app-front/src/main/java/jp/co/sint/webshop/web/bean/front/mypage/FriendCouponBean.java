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
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2060210:レビュー一覧のデータモデルです。
 * 
 * @author OB
 */
public class FriendCouponBean extends UIFrontBean {

  private static final long serialVersionUID = 1L;

  private List<FriendCouponBeanDetail> list = new ArrayList<FriendCouponBeanDetail>();

  private List<FriendCouponBeanDetail> issuedList = new ArrayList<FriendCouponBeanDetail>();

  private List<FriendCouponBeanDetail> unissuedList = new ArrayList<FriendCouponBeanDetail>();

  // 有无优惠券权限
  private boolean issueFlg = false;

  /** 手机 */
  @Required
  @Length(11)
  @MobileNumber
  @Metadata(name = "手机")
  private String tell;

  /** 用户语言 */
  private String languageCode;

  /** 验证码 */
  @Required
  @Length(6)
  @Digit
  @Metadata(name = "验证码")
  private String authCode;

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
    
    //优惠类型
    private Long issueType;
    
    //优惠比例
    private String ratio;
    
    //最大优惠金额
    private  String maxDiscountPrice;

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
     * @param issueType the issueType to set
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
     * @param ratio the ratio to set
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
     * @param maxDiscountPrice the maxDiscountPrice to set
     */
    public void setMaxDiscountPrice(String maxDiscountPrice) {
      this.maxDiscountPrice = maxDiscountPrice;
    }

    
  }

  public List<FriendCouponBeanDetail> getList() {
    return list;
  }

  public void setList(List<FriendCouponBeanDetail> list) {
    this.list = list;
  }

  @Override
  public void createAttributes(RequestParameter reqparam) {
    this.setTell(reqparam.get("mobileNumber"));
    this.setAuthCode(reqparam.get("authCode"));
  }

  @Override
  public String getModuleId() {
    return "U2060210";
  }

  @Override
  public String getModuleName() {
    return Messages.getString("web.bean.front.mypage.FriendCouponListBean.1");
  }

  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.FriendCouponListBean.1"), "/app/mypage/mypage"));
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
   * @return the issuedList
   */
  public List<FriendCouponBeanDetail> getIssuedList() {
    return issuedList;
  }

  /**
   * @param issuedList
   *          the issuedList to set
   */
  public void setIssuedList(List<FriendCouponBeanDetail> issuedList) {
    this.issuedList = issuedList;
  }

  /**
   * @return the unissuedList
   */
  public List<FriendCouponBeanDetail> getUnissuedList() {
    return unissuedList;
  }

  /**
   * @param unissuedList
   *          the unissuedList to set
   */
  public void setUnissuedList(List<FriendCouponBeanDetail> unissuedList) {
    this.unissuedList = unissuedList;
  }
}
