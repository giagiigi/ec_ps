//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「キャンペーン(CAMPAIGN)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class OptionalCampaign implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** キャンペーンコード */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "キャンペーンコード", order = 2)
  private String campaignCode;

  /** キャンペーン名称 */
  @Required
  @Length(50)
  @Metadata(name = "キャンペーン名称", order = 3)
  private String campaignName;

  /** キャンペーン開始日 */
  @Required
  @Metadata(name = "キャンペーン開始日", order = 4)
  private Date campaignStartDate;

  /** キャンペーン終了日 */
  @Required
  @Metadata(name = "キャンペーン終了日", order = 5)
  private Date campaignEndDate;

  /** キャンペーン値引率 */
  @Required
  @Length(10)
  @Metadata(name = "活动商品数量", order = 6)
  private Long optionalNum;

  /** メモ */
  @Required
  @Length(200)
  @Metadata(name = "メモ", order = 7)
  private BigDecimal optionalPrice;

  @Length(1)
  @Metadata(name = "活动状态", order = 7)
  private Long campaignStatus;

  @Required
  @Length(10)
  @Metadata(name = "单个订单最大购买组数", order = 7)
  private Long orderLimitNum;

  @Length(2000)
  @Metadata(name = "关联商品", order = 7)
  private String relatedCommodity;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 8)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 9)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 10)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 11)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 12)
  private Date updatedDatetime;

  /** キャンペーン名称 */
  @Required
  @Length(50)
  @Metadata(name = "キャンペーン名称", order = 13)
  private String campaignNameEn;

  /** キャンペーン名称 */
  @Required
  @Length(50)
  @Metadata(name = "キャンペーン名称", order = 14)
  private String campaignNameJp;

  /**
   * ショップコードを取得します
   * 
   * @return ショップコード
   */
  public String getShopCode() {
    return this.shopCode;
  }

  /**
   * キャンペーンコードを取得します
   * 
   * @return キャンペーンコード
   */
  public String getCampaignCode() {
    return this.campaignCode;
  }

  /**
   * キャンペーン名称を取得します
   * 
   * @return キャンペーン名称
   */
  public String getCampaignName() {
    return this.campaignName;
  }

  /**
   * キャンペーン開始日を取得します
   * 
   * @return キャンペーン開始日
   */
  public Date getCampaignStartDate() {
    return DateUtil.immutableCopy(this.campaignStartDate);
  }

  /**
   * キャンペーン終了日を取得します
   * 
   * @return キャンペーン終了日
   */
  public Date getCampaignEndDate() {
    return DateUtil.immutableCopy(this.campaignEndDate);
  }

  /**
   * データ行IDを取得します
   * 
   * @return データ行ID
   */
  public Long getOrmRowid() {
    return this.ormRowid;
  }

  /**
   * 作成ユーザを取得します
   * 
   * @return 作成ユーザ
   */
  public String getCreatedUser() {
    return this.createdUser;
  }

  /**
   * 作成日時を取得します
   * 
   * @return 作成日時
   */
  public Date getCreatedDatetime() {
    return DateUtil.immutableCopy(this.createdDatetime);
  }

  /**
   * 更新ユーザを取得します
   * 
   * @return 更新ユーザ
   */
  public String getUpdatedUser() {
    return this.updatedUser;
  }

  /**
   * 更新日時を取得します
   * 
   * @return 更新日時
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(this.updatedDatetime);
  }

  /**
   * ショップコードを設定します
   * 
   * @param val
   *          ショップコード
   */
  public void setShopCode(String val) {
    this.shopCode = val;
  }

  /**
   * キャンペーンコードを設定します
   * 
   * @param val
   *          キャンペーンコード
   */
  public void setCampaignCode(String val) {
    this.campaignCode = val;
  }

  /**
   * キャンペーン名称を設定します
   * 
   * @param val
   *          キャンペーン名称
   */
  public void setCampaignName(String val) {
    this.campaignName = val;
  }

  /**
   * キャンペーン開始日を設定します
   * 
   * @param val
   *          キャンペーン開始日
   */
  public void setCampaignStartDate(Date val) {
    this.campaignStartDate = DateUtil.immutableCopy(val);
  }

  /**
   * キャンペーン終了日を設定します
   * 
   * @param val
   *          キャンペーン終了日
   */
  public void setCampaignEndDate(Date val) {
    this.campaignEndDate = DateUtil.immutableCopy(val);
  }

  /**
   * データ行IDを設定します
   * 
   * @param val
   *          データ行ID
   */
  public void setOrmRowid(Long val) {
    this.ormRowid = val;
  }

  /**
   * 作成ユーザを設定します
   * 
   * @param val
   *          作成ユーザ
   */
  public void setCreatedUser(String val) {
    this.createdUser = val;
  }

  /**
   * 作成日時を設定します
   * 
   * @param val
   *          作成日時
   */
  public void setCreatedDatetime(Date val) {
    this.createdDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 更新ユーザを設定します
   * 
   * @param val
   *          更新ユーザ
   */
  public void setUpdatedUser(String val) {
    this.updatedUser = val;
  }

  /**
   * 更新日時を設定します
   * 
   * @param val
   *          更新日時
   */
  public void setUpdatedDatetime(Date val) {
    this.updatedDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * @param campaignNameEn
   *          the campaignNameEn to set
   */
  public void setCampaignNameEn(String campaignNameEn) {
    this.campaignNameEn = campaignNameEn;
  }

  /**
   * @return the campaignNameEn
   */
  public String getCampaignNameEn() {
    return campaignNameEn;
  }

  /**
   * @param campaignNameJp
   *          the campaignNameJp to set
   */
  public void setCampaignNameJp(String campaignNameJp) {
    this.campaignNameJp = campaignNameJp;
  }

  /**
   * @return the campaignNameJp
   */
  public String getCampaignNameJp() {
    return campaignNameJp;
  }

  /**
   * @return the optionalNum
   */
  public Long getOptionalNum() {
    return optionalNum;
  }

  /**
   * @param optionalNum
   *          the optionalNum to set
   */
  public void setOptionalNum(Long optionalNum) {
    this.optionalNum = optionalNum;
  }

  /**
   * @return the optionalPrice
   */
  public BigDecimal getOptionalPrice() {
    return optionalPrice;
  }

  /**
   * @param optionalPrice
   *          the optionalPrice to set
   */
  public void setOptionalPrice(BigDecimal optionalPrice) {
    this.optionalPrice = optionalPrice;
  }

  /**
   * @return the campaignStatus
   */
  public Long getCampaignStatus() {
    return campaignStatus;
  }

  /**
   * @param campaignStatus
   *          the campaignStatus to set
   */
  public void setCampaignStatus(Long campaignStatus) {
    this.campaignStatus = campaignStatus;
  }

  /**
   * @return the orderLimitNum
   */
  public Long getOrderLimitNum() {
    return orderLimitNum;
  }

  /**
   * @param orderLimitNum
   *          the orderLimitNum to set
   */
  public void setOrderLimitNum(Long orderLimitNum) {
    this.orderLimitNum = orderLimitNum;
  }

  
  /**
   * @return the relatedCommodity
   */
  public String getRelatedCommodity() {
    return relatedCommodity;
  }

  
  /**
   * @param relatedCommodity the relatedCommodity to set
   */
  public void setRelatedCommodity(String relatedCommodity) {
    this.relatedCommodity = relatedCommodity;
  }

  
  
}
