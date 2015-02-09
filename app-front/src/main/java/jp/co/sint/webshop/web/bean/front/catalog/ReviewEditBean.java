package jp.co.sint.webshop.web.bean.front.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.ReviewScore;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2040610:レビュー入力のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class ReviewEditBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String commodityCode;

  private String commodityName;

  private String commodityDescription;

  private String commodityImageUrl;

  private String customerCode;

  private boolean displayFlg;

  // 20111219 os013 add start
  // 受注履歴ID
  private String orderNo;

  // 20111219 os013 add end

  @Required
  @Length(15)
  @Metadata(name = "ニックネーム")
  private String nickName;

  private CodeAttribute[] reviewScoreList = ReviewScore.values();

  @Required
  @Length(3)
  @Metadata(name = "おすすめ度")
  private String reviewScoreCondition;

  private String sex;

  @Required
  @Length(50)
  @Metadata(name = "レビュータイトル")
  private String reviewTitle;

  @Required
  @Length(2000)
  @Metadata(name = "内容")
  private String reviewDescription;

  private String reviewScore;

  private String priceMode;

  private String unitPrice;

  private String discountPrice;

  private String reservationPrice;

  private String commodityTaxType;

  private String discountRate;

  private String discountPrices;

  private boolean displayDiscountRate;

  /**
   * @return the priceMode
   */
  public String getPriceMode() {
    return priceMode;
  }

  /**
   * @param priceMode
   *          the priceMode to set
   */
  public void setPriceMode(String priceMode) {
    this.priceMode = priceMode;
  }

  /**
   * @return the unitPrice
   */
  public String getUnitPrice() {
    return unitPrice;
  }

  /**
   * @param unitPrice
   *          the unitPrice to set
   */
  public void setUnitPrice(String unitPrice) {
    this.unitPrice = unitPrice;
  }

  /**
   * @return the discountPrice
   */
  public String getDiscountPrice() {
    return discountPrice;
  }

  /**
   * @param discountPrice
   *          the discountPrice to set
   */
  public void setDiscountPrice(String discountPrice) {
    this.discountPrice = discountPrice;
  }

  /**
   * @return the reservationPrice
   */
  public String getReservationPrice() {
    return reservationPrice;
  }

  /**
   * @param reservationPrice
   *          the reservationPrice to set
   */
  public void setReservationPrice(String reservationPrice) {
    this.reservationPrice = reservationPrice;
  }

  /**
   * @return the commodityTaxType
   */
  public String getCommodityTaxType() {
    return commodityTaxType;
  }

  /**
   * @param commodityTaxType
   *          the commodityTaxType to set
   */
  public void setCommodityTaxType(String commodityTaxType) {
    this.commodityTaxType = commodityTaxType;
  }

  /**
   * @return the discountRate
   */
  public String getDiscountRate() {
    return discountRate;
  }

  /**
   * @param discountRate
   *          the discountRate to set
   */
  public void setDiscountRate(String discountRate) {
    this.discountRate = discountRate;
  }

  /**
   * @return the discountPrices
   */
  public String getDiscountPrices() {
    return discountPrices;
  }

  /**
   * @param discountPrices
   *          the discountPrices to set
   */
  public void setDiscountPrices(String discountPrices) {
    this.discountPrices = discountPrices;
  }

  /**
   * @return the displayDiscountRate
   */
  public boolean isDisplayDiscountRate() {
    return displayDiscountRate;
  }

  /**
   * @param displayDiscountRate
   *          the displayDiscountRate to set
   */
  public void setDisplayDiscountRate(boolean displayDiscountRate) {
    this.displayDiscountRate = displayDiscountRate;
  }

  /**
   * @return the reviewScore
   */
  public String getReviewScore() {
    return reviewScore;
  }

  /**
   * @param reviewScore
   *          the reviewScore to set
   */
  public void setReviewScore(String reviewScore) {
    this.reviewScore = reviewScore;
  }

  /**
   * commodityCodeを取得します。
   * 
   * @return commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * commodityDescriptionを取得します。
   * 
   * @return commodityDescription
   */
  public String getCommodityDescription() {
    return commodityDescription;
  }

  /**
   * commodityNameを取得します。
   * 
   * @return commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  /**
   * customerCodeを取得します。
   * 
   * @return customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * nickNameを取得します。
   * 
   * @return nickName
   */
  public String getNickName() {
    return nickName;
  }

  /**
   * reviewDescriptionを取得します。
   * 
   * @return reviewDescription
   */
  public String getReviewDescription() {
    return reviewDescription;
  }

  /**
   * reviewScoreConditionを取得します。
   * 
   * @return reviewScoreCondition
   */
  public String getReviewScoreCondition() {
    return reviewScoreCondition;
  }

  /**
   * reviewTitleを取得します。
   * 
   * @return reviewTitle
   */
  public String getReviewTitle() {
    return reviewTitle;
  }

  /**
   * sexを取得します。
   * 
   * @return sex
   */
  public String getSex() {
    return sex;
  }

  /**
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  // 20111219 os013 add start
  /**
   * 受注履歴IDを取得します。
   * 
   * @return 受注履歴ID
   */
  public String getOrderNo() {
    return orderNo;
  }

  // 20111219 os013 add end
  /**
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * commodityDescriptionを設定します。
   * 
   * @param commodityDescription
   *          commodityDescription
   */
  public void setCommodityDescription(String commodityDescription) {
    this.commodityDescription = commodityDescription;
  }

  /**
   * commodityNameを設定します。
   * 
   * @param commodityName
   *          commodityName
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * customerCodeを設定します。
   * 
   * @param customerCode
   *          customerCode
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * nickNameを設定します。
   * 
   * @param nickName
   *          nickName
   */
  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  /**
   * reviewDescriptionを設定します。
   * 
   * @param reviewDescription
   *          reviewDescription
   */
  public void setReviewDescription(String reviewDescription) {
    this.reviewDescription = reviewDescription;
  }

  /**
   * reviewScoreConditionを設定します。
   * 
   * @param reviewScoreCondition
   *          reviewScoreCondition
   */
  public void setReviewScoreCondition(String reviewScoreCondition) {
    this.reviewScoreCondition = reviewScoreCondition;
  }

  /**
   * reviewTitleを設定します。
   * 
   * @param reviewTitle
   *          reviewTitle
   */
  public void setReviewTitle(String reviewTitle) {
    this.reviewTitle = reviewTitle;
  }

  /**
   * sexを設定します。
   * 
   * @param sex
   *          sex
   */
  public void setSex(String sex) {
    this.sex = sex;
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

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
    if(!StringUtil.isNullOrEmpty(getClient()) && !getClient().equals(ClientType.OTHER)){
      setReviewScore(reqparam.get("reviewScoreCondition"));
    }

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2040610";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.catalog.ReviewEditBean.0");
  }

  /**
   * commodityImageUrlを取得します。
   * 
   * @return commodityImageUrl
   */
  public String getCommodityImageUrl() {
    return commodityImageUrl;
  }

  /**
   * commodityImageUrlを設定します。
   * 
   * @param commodityImageUrl
   *          commodityImageUrl
   */
  public void setCommodityImageUrl(String commodityImageUrl) {
    this.commodityImageUrl = commodityImageUrl;
  }

  /**
   * displayFlgを取得します。
   * 
   * @return displayFlg
   */
  public boolean isDisplayFlg() {
    return displayFlg;
  }

  /**
   * displayFlgを設定します。
   * 
   * @param displayFlg
   *          displayFlg
   */
  public void setDisplayFlg(boolean displayFlg) {
    this.displayFlg = displayFlg;
  }

  // 20111219 os013 add start
  /**
   * 受注履歴IDを設定します。
   * 
   * @param 受注履歴ID
   *          受注履歴ID
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  // 20111219 os013 add end

  /**
   * @return the reviewScoreList
   */
  public CodeAttribute[] getReviewScoreList() {
    return reviewScoreList;
  }

  /**
   * @param reviewScoreList
   *          the reviewScoreList to set
   */
  public void setReviewScoreList(CodeAttribute[] reviewScoreList) {
    this.reviewScoreList = reviewScoreList;
  }
  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
    addSubJspId("/common/header");
    //addSubJspId("/catalog/sales_recommend");
  }
  
  
  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    if(!StringUtil.isNullOrEmpty(getClient()) && !this.getClient().equals(ClientType.OTHER)){
    topicPath.add(new NameValue(Messages.getString(
        "web.bean.front.mypage.FavoriteListBean.1"), "/app/mypage/mypage"));
    topicPath.add(new NameValue(Messages.getString(
    "web.action.front.catalog.ReviewConfirmRegisterAction.0")+Messages.getString(
    "web.action.front.catalog.ReviewConfirmRegisterAction.1"), "/app/mypage/favorite_list/init"));
    }
    return topicPath;
  }
}
