package jp.co.sint.webshop.web.bean.front.catalog;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2040620:レビュー確認のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class ReviewConfirmBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String commodityCode;

  private String commodityName;

  private String commodityDescription;

  private String customerCode;

  private String commodityImageUrl;
  
  //20111219 os013 add start
  //受注履歴ID
  private String orderNo;
  //20111219 os013 add end
  
  @Required
  @Length(15)
  @Metadata(name = "ニックネーム")
  private String nickName;

  @Required
  @Length(3)
  @Metadata(name = "レビュー点数")
  private String reviewScore;

  private String sex;

  @Required
  @Length(50)
  @Metadata(name = "レビュータイトル")
  private String reviewTitle;

  @Required
  @Length(2000)
  @Metadata(name = "レビュー内容")
  private String reviewDescription;

  /** 前へ戻る, 投稿するボタン表示フラグ */
  private boolean displayButton;

  /** 商品詳細へ戻るボタン表示フラグ */
  private boolean displayBackButton;

  /** 説明文言表示フラグ */
  private boolean displayNavi;

  private boolean reviewComplateFlag;
  
  /**
   * displayNaviを返します。
   * 
   * @return the displayNavi
   */
  public boolean isDisplayNavi() {
    return displayNavi;
  }

  /**
   * displayNaviを設定します。
   * 
   * @param displayNavi
   *          設定する displayNavi
   */
  public void setDisplayNavi(boolean displayNavi) {
    this.displayNavi = displayNavi;
  }

  /**
   * displayBackButtonを返します。
   * 
   * @return the displayBackButton
   */
  public boolean isDisplayBackButton() {
    return displayBackButton;
  }

  /**
   * displayBackButtonを設定します。
   * 
   * @param displayBackButton
   *          設定する displayBackButton
   */
  public void setDisplayBackButton(boolean displayBackButton) {
    this.displayBackButton = displayBackButton;
  }

  /**
   * displayButtonを返します。
   * 
   * @return the displayButton
   */
  public boolean isDisplayButton() {
    return displayButton;
  }

  /**
   * displayButtonを設定します。
   * 
   * @param displayButton
   *          設定する displayButton
   */
  public void setDisplayButton(boolean displayButton) {
    this.displayButton = displayButton;
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
   * reviewScoreを取得します。
   * 
   * @return reviewScore
   */
  public String getReviewScore() {
    return reviewScore;
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
   * reviewScoreを設定します。
   * 
   * @param reviewScore
   *          reviewScore
   */
  public void setReviewScore(String reviewScore) {
    this.reviewScore = reviewScore;
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

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2040620";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.catalog.ReviewConfirmBean.0"); 
  }

  /**
   * commodityImageUrlを返します。
   * 
   * @return the commodityImageUrl
   */
  public String getCommodityImageUrl() {
    return commodityImageUrl;
  }

  /**
   * commodityImageUrlを設定します。
   * 
   * @param commodityImageUrl
   *          設定する commodityImageUrl
   */
  public void setCommodityImageUrl(String commodityImageUrl) {
    this.commodityImageUrl = commodityImageUrl;
  }
  //20111219 os013 add start
  /**
   *  受注履歴IDを取得します。
   * 
   * @return  受注履歴ID
   */
  public String getOrderNo() {
    return orderNo;
  }
  
  /**
   * 受注履歴IDを設定します。
   * 
   * @param 受注履歴ID
   *          受注履歴ID
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }
  //20111219 os013 add end
  
  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
    addSubJspId("/common/header");
    //addSubJspId("/catalog/sales_recommend");
  }

  
  /**
   * @return the reviewComplateFlag
   */
  public boolean isReviewComplateFlag() {
    return reviewComplateFlag;
  }

  
  /**
   * @param reviewComplateFlag the reviewComplateFlag to set
   */
  public void setReviewComplateFlag(boolean reviewComplateFlag) {
    this.reviewComplateFlag = reviewComplateFlag;
  }

  
}
