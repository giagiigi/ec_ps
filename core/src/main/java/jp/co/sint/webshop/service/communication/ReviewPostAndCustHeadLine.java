package jp.co.sint.webshop.service.communication;

import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.ReviewDisplayType;

public class ReviewPostAndCustHeadLine {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** レビューID */
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "レビューID", order = 1)
  private Long reviewId;

  /** ショップコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 2)
  private String shopCode;

  /** 商品コード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード", order = 3)
  private String commodityCode;

  /** 顧客コード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客コード", order = 4)
  private String customerCode;

  /** レビュータイトル */
  @Required
  @Length(50)
  @Metadata(name = "レビュータイトル", order = 5)
  private String reviewTitle;

  /** ニックネーム */
  @Required
  @Length(15)
  @Metadata(name = "ニックネーム", order = 6)
  private String nickname;

  /** レビュー投稿日時 */
  @Required
  @Metadata(name = "レビュー投稿日時", order = 7)
  private Date reviewContributedDatetime;

  /** レビュー内容 */
  @Required
  @Length(2000)
  @Metadata(name = "レビュー内容", order = 8)
  private String reviewDescription;

  /** レビュー点数 */
  @Required
  @Length(3)
  @Metadata(name = "レビュー点数", order = 9)
  private Long reviewScore;

  /** 商品レビュー表示区分 */
  @Required
  @Length(1)
  @Domain(ReviewDisplayType.class)
  @Metadata(name = "商品レビュー表示区分", order = 10)
  private Long reviewDisplayType;

  /** レビューポイント割当ステータス */
  @Length(1)
  @Bool
  @Metadata(name = "レビューポイント割当ステータス", order = 11)
  private Long reviewPointAllocatedStatus;

  // 20111219 os013 add start
  /** 受注履歴ID */
  @Length(16)
  @Metadata(name = "受注履歴ID", order = 12)
  private String orderNo;

  // 20111219 os013 add end

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 13)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 14)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 15)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 16)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 17)
  private Date updatedDatetime;

  private Long sex;

  /**
   * @return the reviewId
   */
  public Long getReviewId() {
    return reviewId;
  }

  /**
   * @param reviewId
   *          the reviewId to set
   */
  public void setReviewId(Long reviewId) {
    this.reviewId = reviewId;
  }

  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @return the customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * @param customerCode
   *          the customerCode to set
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * @return the reviewTitle
   */
  public String getReviewTitle() {
    return reviewTitle;
  }

  /**
   * @param reviewTitle
   *          the reviewTitle to set
   */
  public void setReviewTitle(String reviewTitle) {
    this.reviewTitle = reviewTitle;
  }

  /**
   * @return the nickname
   */
  public String getNickname() {
    return nickname;
  }

  /**
   * @param nickname
   *          the nickname to set
   */
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  /**
   * @return the reviewContributedDatetime
   */
  public Date getReviewContributedDatetime() {
    return reviewContributedDatetime;
  }

  /**
   * @param reviewContributedDatetime
   *          the reviewContributedDatetime to set
   */
  public void setReviewContributedDatetime(Date reviewContributedDatetime) {
    this.reviewContributedDatetime = reviewContributedDatetime;
  }

  /**
   * @return the reviewDescription
   */
  public String getReviewDescription() {
    return reviewDescription;
  }

  /**
   * @param reviewDescription
   *          the reviewDescription to set
   */
  public void setReviewDescription(String reviewDescription) {
    this.reviewDescription = reviewDescription;
  }

  /**
   * @return the reviewScore
   */
  public Long getReviewScore() {
    return reviewScore;
  }

  /**
   * @param reviewScore
   *          the reviewScore to set
   */
  public void setReviewScore(Long reviewScore) {
    this.reviewScore = reviewScore;
  }

  /**
   * @return the reviewDisplayType
   */
  public Long getReviewDisplayType() {
    return reviewDisplayType;
  }

  /**
   * @param reviewDisplayType
   *          the reviewDisplayType to set
   */
  public void setReviewDisplayType(Long reviewDisplayType) {
    this.reviewDisplayType = reviewDisplayType;
  }

  /**
   * @return the reviewPointAllocatedStatus
   */
  public Long getReviewPointAllocatedStatus() {
    return reviewPointAllocatedStatus;
  }

  /**
   * @param reviewPointAllocatedStatus
   *          the reviewPointAllocatedStatus to set
   */
  public void setReviewPointAllocatedStatus(Long reviewPointAllocatedStatus) {
    this.reviewPointAllocatedStatus = reviewPointAllocatedStatus;
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

  /**
   * @return the ormRowid
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  /**
   * @param ormRowid
   *          the ormRowid to set
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * @return the createdUser
   */
  public String getCreatedUser() {
    return createdUser;
  }

  /**
   * @param createdUser
   *          the createdUser to set
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * @return the createdDatetime
   */
  public Date getCreatedDatetime() {
    return createdDatetime;
  }

  /**
   * @param createdDatetime
   *          the createdDatetime to set
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  /**
   * @return the updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * @param updatedUser
   *          the updatedUser to set
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * @return the updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return updatedDatetime;
  }

  /**
   * @param updatedDatetime
   *          the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  /**
   * @return the sex
   */
  public Long getSex() {
    return sex;
  }

  /**
   * @param sex
   *          the sex to set
   */
  public void setSex(Long sex) {
    this.sex = sex;
  }

}
