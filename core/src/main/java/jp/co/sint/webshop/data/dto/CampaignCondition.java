//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
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
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「キャンペーン(CampaignCondition)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 * 
 */
public class CampaignCondition implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 促销编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "促销编号", order = 1)
  private String campaignCode;

  /** 条件类型 */
  @Required
  @Length(1)
  @Metadata(name = "条件类型", order = 2)
  private Long campaignConditionType;
  
  /** 对象商品 */
  @Required
  @Length(1)
  @Metadata(name = "对象商品", order = 3)
  private Long campaignConditionFlg;

  /** 内容 */
  @Required
  @Metadata(name = "内容", order = 4)
  private String attributrValue;
  
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 5)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 6)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 7)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 8)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 9)
  private Date updatedDatetime;
  
  /** 对象商品限定件数 */
  @Length(8)
  @Metadata(name = "对象商品限定件数", order = 10)
  private Long maxCommodityNum;
  
  /** 注文金额 */
  @Length(10)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "注文金额", order = 11)
  private BigDecimal orderAmount;
  
  /** 广告媒体编号 */
  @Length(16)
  @Metadata(name = "广告媒体编号", order = 12)
  private String advertCode;

  /** 折扣区分 */
  @Length(8)
  @Metadata(name = "折扣区分", order = 13)
  private Long discountType;
   
  /**使用限制次数*/
  @Digit
  @Range(min=1,max=99999999)
  @Length(8)
  @Metadata(name = "使用限制次数", order = 14)
  private Long useLimit;
  
  /**
   * キャンペーンコードを取得します
   * 
   * @return キャンペーンコード
   */
  public String getCampaignCode() {
    return this.campaignCode;
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

  public Long getCampaignConditionType() {
    return campaignConditionType;
  }


  public void setCampaignConditionType(Long campaignConditionType) {
    this.campaignConditionType = campaignConditionType;
  }


  public Long getCampaignConditionFlg() {
    return campaignConditionFlg;
  }


  public void setCampaignConditionFlg(Long campaignConditionFlg) {
    this.campaignConditionFlg = campaignConditionFlg;
  }


  public String getAttributrValue() {
    return attributrValue;
  }


  public void setAttributrValue(String attributrValue) {
    this.attributrValue = attributrValue;
  }


  public Long getMaxCommodityNum() {
    return maxCommodityNum;
  }


  public void setMaxCommodityNum(Long maxCommodityNum) {
    this.maxCommodityNum = maxCommodityNum;
  }


  public BigDecimal getOrderAmount() {
    return orderAmount;
  }


  public void setOrderAmount(BigDecimal orderAmount) {
    this.orderAmount = orderAmount;
  }


  public String getAdvertCode() {
    return advertCode;
  }


  public void setAdvertCode(String advertCode) {
    this.advertCode = advertCode;
  }


  public Long getDiscountType() {
    return discountType;
  }


  public void setDiscountType(Long discountType) {
    this.discountType = discountType;
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
   * キャンペーンコードを設定します
   * 
   * @param val
   *            キャンペーンコード
   */
  public void setCampaignCode(String val) {
    this.campaignCode = val;
  }

  /**
   * データ行IDを設定します
   * 
   * @param val
   *            データ行ID
   */
  public void setOrmRowid(Long val) {
    this.ormRowid = val;
  }

  /**
   * 作成ユーザを設定します
   * 
   * @param val
   *            作成ユーザ
   */
  public void setCreatedUser(String val) {
    this.createdUser = val;
  }

  /**
   * 作成日時を設定します
   * 
   * @param val
   *            作成日時
   */
  public void setCreatedDatetime(Date val) {
    this.createdDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 更新ユーザを設定します
   * 
   * @param val
   *            更新ユーザ
   */
  public void setUpdatedUser(String val) {
    this.updatedUser = val;
  }

  /**
   * 更新日時を設定します
   * 
   * @param val
   *            更新日時
   */
  public void setUpdatedDatetime(Date val) {
    this.updatedDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 
   * @return 使用限制次数
   */
  public Long getUseLimit() {
    return useLimit;
  }

  /**
   * 
   * @param useLimit 使用限制次数
   */
  public void setUseLimit(Long useLimit) {
    this.useLimit = useLimit;
  }

  

}
