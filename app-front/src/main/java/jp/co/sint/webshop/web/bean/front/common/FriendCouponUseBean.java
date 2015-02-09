package jp.co.sint.webshop.web.bean.front.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2060210:レビュー一覧のデータモデルです。
 * 
 * @author OB
 */
public class FriendCouponUseBean extends UIFrontBean {

  private static final long serialVersionUID = 1L;

  // 顾客名
  private String customerName;

  // 优惠券金额
  private BigDecimal couponAmount;

  // 优惠券编号
  private String couponCode;

  // 优惠券使用最小购买金额
  private BigDecimal minUseOrderAmount;

  // 优惠券使用开始日时
  private String minUseStartDatetime;

  // 优惠券使用结束日时
  private String minUseEndDatetime;
  
  //优惠券名称
  private String friendCouponRule;
  
  //优惠券名称(英文)
  private String friendCouponRuleEn;
  
  //优惠券名称(日语)
  private String friendCouponRuleJp;
  
  /**利用限定（0:无限制 1:初次购买）*/
  private String applicableObjects;
  
  private boolean isError = false;
  
  
  // 优惠券比例
  private String couponProportion;
  
  // 优惠券类型
  private String couponIssueType;
  
  
  // 最大优惠金额
  private String maxDiscountPrice;

  @Override
  public void createAttributes(RequestParameter reqparam) {

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
  public BigDecimal getCouponAmount() {
    return couponAmount;
  }

  /**
   * @param couponAmount
   *          the couponAmount to set
   */
  public void setCouponAmount(BigDecimal couponAmount) {
    this.couponAmount = couponAmount;
  }

  /**
   * @return the minUseOrderAmount
   */
  public BigDecimal getMinUseOrderAmount() {
    return minUseOrderAmount;
  }

  /**
   * @param minUseOrderAmount
   *          the minUseOrderAmount to set
   */
  public void setMinUseOrderAmount(BigDecimal minUseOrderAmount) {
    this.minUseOrderAmount = minUseOrderAmount;
  }

  /**
   * @return the minUseStartDatetime
   */
  public String getMinUseStartDatetime() {
    return minUseStartDatetime;
  }

  /**
   * @param minUseStartDatetime
   *          the minUseStartDatetime to set
   */
  public void setMinUseStartDatetime(String minUseStartDatetime) {
    this.minUseStartDatetime = minUseStartDatetime;
  }

  /**
   * @return the minUseEndDatetime
   */
  public String getMinUseEndDatetime() {
    return minUseEndDatetime;
  }

  /**
   * @param minUseEndDatetime
   *          the minUseEndDatetime to set
   */
  public void setMinUseEndDatetime(String minUseEndDatetime) {
    this.minUseEndDatetime = minUseEndDatetime;
  }

  
  /**
   * @return the friendCouponRule
   */
  public String getFriendCouponRule() {
    return friendCouponRule;
  }

  
  /**
   * @param friendCouponRule the friendCouponRule to set
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
   * @param friendCouponRuleEn the friendCouponRuleEn to set
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
   * @param friendCouponRuleJp the friendCouponRuleJp to set
   */
  public void setFriendCouponRuleJp(String friendCouponRuleJp) {
    this.friendCouponRuleJp = friendCouponRuleJp;
  }

  /**
   * @return the isError
   */
  public boolean isError() {
    return isError;
  }

  /**
   * @param isError the isError to set
   */
  public void setError(boolean isError) {
    this.isError = isError;
  }

  /**
   * 取得显示图片内容格式
   * @return
   */
  public String getDisplayImgSize() {
    if(CampaignType.PROPORTION.getValue().equals(couponIssueType)){
      if(couponProportion != null ){
        return "proportion_"+couponProportion;
      }
    }else{
      if(couponAmount != null ){
        return couponAmount.intValue()+"";
      }
    }
    return "";
  }
  
  /**
   * 取得利用限定（0:无限制 1:初次购买）
   * @return
   */
  public String getApplicableObjects() {
    return applicableObjects;
  }

  /**
   * 设定利用限定（0:无限制 1:初次购买）
   * @param applicableObjects
   */
  public void setApplicableObjects(String applicableObjects) {
    this.applicableObjects = applicableObjects;
  }
  
  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
    addSubJspId("/common/header");
    //addSubJspId("/catalog/" + DisplayPartsCode.CART.getName());
    // addSubJspId("/catalog/" + DisplayPartsCode.RECOMMEND_A.getName());
    //addSubJspId("/catalog/" + DisplayPartsCode.RECOMMEND_C.getName());
    //addSubJspId("/catalog/" + DisplayPartsCode.RECOMMEND_B.getName());
    //addSubJspId("/catalog/" + DisplayPartsCode.REVIEWS.getName());
    // addSubJspId("/catalog/category_tree");
    //addSubJspId("/catalog/topic_path");
    // addSubJspId("/catalog/campaign_info");
    //addSubJspId("/catalog/sales_recommend");
    addSubJspId("/catalog/sales_star");
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
