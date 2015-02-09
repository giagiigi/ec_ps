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
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「商品詳細(COMMODITY_DETAIL)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class CommodityPriceChangeHistory implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 商品コード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード", order = 1)
  private String commodityCode;

  /** 提交日時 */
  @PrimaryKey(2)
  @Required
  @Metadata(name = "提交日時", order = 2)
  private Date submitTime;

  /** 提交人 */
  @Length(20)
  @Metadata(name = "提交人", order = 3)
  private String responsiblePerson;

  
  /** 改前官网原价 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "改前官网原价", order = 4)
  private BigDecimal oldOfficialPrice;


  /** 改后官网原价 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "改后官网原价", order = 5)
  private BigDecimal newOfficialPrice;

  
  /** 改前官网特价 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "改前官网特价", order = 6)
  private BigDecimal oldOfficialSpecialPrice;

  
  /** 改后官网特价 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "改后官网特价", order = 7)
  private BigDecimal newOfficialSpecialPrice;

  
  /** 改前淘宝售价 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "改前淘宝售价", order = 8)
  private BigDecimal oldTmallPrice;

  
  /** 改后淘宝售价 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "改后淘宝售价", order = 9)
  private BigDecimal newTmallPrice;

  
  /** 改前京东售价 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "改前京东售价", order = 10)
  private BigDecimal oldJdPrice;

  
  /** 改后京东售价 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "改后京东售价", order = 11)
  private BigDecimal newJdPrice;

 
  /** データ行ID */
  @Length(38)
  @Metadata(name = "データ行ID", order = 12)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 13)
  private String createdUser;

  /** 作成日時 */
  @Metadata(name = "作成日時", order = 14)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 15)
  private String updatedUser;

  /** 更新日時 */
  @Metadata(name = "更新日時", order = 16)
  private Date updatedDatetime;


  /** 审核flg 0是未审核，1是已审核 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "审核flg", order = 11)
  private Long reviewOrNotFlg;

  /** EC售价毛利率 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "EC售价毛利率", order = 11)
  private BigDecimal ecProfitMargin;

  /** EC特价毛利率 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "EC特价毛利率", order = 11)
  private BigDecimal ecSpecialProfitMargin;

  /** TMALL售价毛利率 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "TMALL售价毛利率", order = 11)
  private BigDecimal tmallProfitMargin;

  /** JD售价毛利率 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "JD售价毛利率", order = 11)
  private BigDecimal jdProfitMargin;

  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  
  /**
   * @param commodityCode the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  
  /**
   * @return the submitTime
   */
  public Date getSubmitTime() {
    return submitTime;
  }

  
  /**
   * @param submitTime the submitTime to set
   */
  public void setSubmitTime(Date submitTime) {
    this.submitTime = submitTime;
  }

  
  /**
   * @return the responsiblePerson
   */
  public String getResponsiblePerson() {
    return responsiblePerson;
  }

  
  /**
   * @param responsiblePerson the responsiblePerson to set
   */
  public void setResponsiblePerson(String responsiblePerson) {
    this.responsiblePerson = responsiblePerson;
  }

  
  /**
   * @return the oldOfficialPrice
   */
  public BigDecimal getOldOfficialPrice() {
    return oldOfficialPrice;
  }

  
  /**
   * @param oldOfficialPrice the oldOfficialPrice to set
   */
  public void setOldOfficialPrice(BigDecimal oldOfficialPrice) {
    this.oldOfficialPrice = oldOfficialPrice;
  }

  
  /**
   * @return the newOfficialPrice
   */
  public BigDecimal getNewOfficialPrice() {
    return newOfficialPrice;
  }

  
  /**
   * @param newOfficialPrice the newOfficialPrice to set
   */
  public void setNewOfficialPrice(BigDecimal newOfficialPrice) {
    this.newOfficialPrice = newOfficialPrice;
  }

  
  /**
   * @return the oldOfficialSpecialPrice
   */
  public BigDecimal getOldOfficialSpecialPrice() {
    return oldOfficialSpecialPrice;
  }

  
  /**
   * @param oldOfficialSpecialPrice the oldOfficialSpecialPrice to set
   */
  public void setOldOfficialSpecialPrice(BigDecimal oldOfficialSpecialPrice) {
    this.oldOfficialSpecialPrice = oldOfficialSpecialPrice;
  }

  
  /**
   * @return the newOfficialSpecialPrice
   */
  public BigDecimal getNewOfficialSpecialPrice() {
    return newOfficialSpecialPrice;
  }

  
  /**
   * @param newOfficialSpecialPrice the newOfficialSpecialPrice to set
   */
  public void setNewOfficialSpecialPrice(BigDecimal newOfficialSpecialPrice) {
    this.newOfficialSpecialPrice = newOfficialSpecialPrice;
  }

  
  /**
   * @return the oldTmallPrice
   */
  public BigDecimal getOldTmallPrice() {
    return oldTmallPrice;
  }

  
  /**
   * @param oldTmallPrice the oldTmallPrice to set
   */
  public void setOldTmallPrice(BigDecimal oldTmallPrice) {
    this.oldTmallPrice = oldTmallPrice;
  }

  
  /**
   * @return the newTmallPrice
   */
  public BigDecimal getNewTmallPrice() {
    return newTmallPrice;
  }

  
  /**
   * @param newTmallPrice the newTmallPrice to set
   */
  public void setNewTmallPrice(BigDecimal newTmallPrice) {
    this.newTmallPrice = newTmallPrice;
  }

  
  /**
   * @return the oldJdPrice
   */
  public BigDecimal getOldJdPrice() {
    return oldJdPrice;
  }

  
  /**
   * @param oldJdPrice the oldJdPrice to set
   */
  public void setOldJdPrice(BigDecimal oldJdPrice) {
    this.oldJdPrice = oldJdPrice;
  }

  
  /**
   * @return the newJdPrice
   */
  public BigDecimal getNewJdPrice() {
    return newJdPrice;
  }

  
  /**
   * @param newJdPrice the newJdPrice to set
   */
  public void setNewJdPrice(BigDecimal newJdPrice) {
    this.newJdPrice = newJdPrice;
  }

  
  /**
   * @return the ormRowid
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  
  /**
   * @param ormRowid the ormRowid to set
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
   * @param createdUser the createdUser to set
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
   * @param createdDatetime the createdDatetime to set
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
   * @param updatedUser the updatedUser to set
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
   * @param updatedDatetime the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }


  
  /**
   * @return the reviewOrNotFlg
   */
  public Long getReviewOrNotFlg() {
    return reviewOrNotFlg;
  }


  
  /**
   * @param reviewOrNotFlg the reviewOrNotFlg to set
   */
  public void setReviewOrNotFlg(Long reviewOrNotFlg) {
    this.reviewOrNotFlg = reviewOrNotFlg;
  }


  
  /**
   * @return the ecProfitMargin
   */
  public BigDecimal getEcProfitMargin() {
    return ecProfitMargin;
  }


  
  /**
   * @param ecProfitMargin the ecProfitMargin to set
   */
  public void setEcProfitMargin(BigDecimal ecProfitMargin) {
    this.ecProfitMargin = ecProfitMargin;
  }


  
  /**
   * @return the ecSpecialProfitMargin
   */
  public BigDecimal getEcSpecialProfitMargin() {
    return ecSpecialProfitMargin;
  }


  
  /**
   * @param ecSpecialProfitMargin the ecSpecialProfitMargin to set
   */
  public void setEcSpecialProfitMargin(BigDecimal ecSpecialProfitMargin) {
    this.ecSpecialProfitMargin = ecSpecialProfitMargin;
  }


  
  /**
   * @return the tmallProfitMargin
   */
  public BigDecimal getTmallProfitMargin() {
    return tmallProfitMargin;
  }


  
  /**
   * @param tmallProfitMargin the tmallProfitMargin to set
   */
  public void setTmallProfitMargin(BigDecimal tmallProfitMargin) {
    this.tmallProfitMargin = tmallProfitMargin;
  }


  
  /**
   * @return the jdProfitMargin
   */
  public BigDecimal getJdProfitMargin() {
    return jdProfitMargin;
  }


  
  /**
   * @param jdProfitMargin the jdProfitMargin to set
   */
  public void setJdProfitMargin(BigDecimal jdProfitMargin) {
    this.jdProfitMargin = jdProfitMargin;
  }


}
