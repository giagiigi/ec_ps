package jp.co.sint.webshop.data.dto;

/**
 * 企划明细表
 * 
 * @author ob.
 * @version 1.0.0
 * 
 */
import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;

/**
 * AbstractPlanWork entity provides the base persistence definition of the PlanWork
 * @author OB
 */

public class PlanDetail implements Serializable, WebshopEntity {

  private static final long serialVersionUID = 1L;

  /** 企划编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "企划编号", order = 1)
  private String planCode;

  /** 企划明细区分 */
  @PrimaryKey(2)
  @Required
  @Length(1)
  @Digit
  @Metadata(name = "企划明细区分", order = 2)
  private Long detailType;

  /** 企划明细编号 */
  @PrimaryKey(3)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "企划明细编号", order = 3)
  private String detailCode;
  
  /** 企划明细名称 */
  @Length(40)
  @Metadata(name = "企划明细名称", order = 4)
  private String detailName;
  
  /** 企划明细名称 */
  @Length(40)
  @Metadata(name = "企划明细名称(英文)", order = 5)
  private String detailNameEn;
  
  /** 企划明细名称 */
  @Length(40)
  @Metadata(name = "企划明细名称(日文)", order = 6)
  private String detailNameJp;

  /** 企划明细URL */
  @Length(256)
  @Metadata(name = "企划明细URL", order = 7)
  private String detailUrl;
  
  /** 企划明细URL */
  @Length(256)
  @Metadata(name = "企划明细URL(英文)", order = 8)
  private String detailUrlEn;
  
  /** 企划明细URL */
  @Length(256)
  @Metadata(name = "企划明细URL(日文)", order = 9)
  private String detailUrlJp;

  /** 表示商品件数 */
  @Required
  @Length(8)
  @Digit
  @Metadata(name = "表示商品件数", order = 10)
  private Long showCommodityCount;
  
  /** 表示顺序 */
  @Length(8)
  @Digit
  @Metadata(name = "表示顺序", order = 11)
  private Long displayOrder;

  /** データ行ID */
  @Required
  @Digit
  @Metadata(name = "データ行ID", order = 12)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 13)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Datetime
  @Metadata(name = "作成日時", order = 14)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 15)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Datetime
  @Metadata(name = "更新日時", order = 16)
  private Date updatedDatetime;



  /**
   * @return 企划编号
   */
  public String getPlanCode() {
    return planCode;
  }
  /**
   * @param PlanCode 设置企划编号
   */
  public void setPlanCode(String planCode) {
    this.planCode = planCode;
  }

  public Long getDetailType() {
	return detailType;
  }
  
  public void setDetailType(Long detailType) {
	this.detailType = detailType;
  }
  
  public String getDetailCode() {
	return detailCode;
  }
  
  public void setDetailCode(String detailCode) {
	this.detailCode = detailCode;
  }
  
  public String getDetailName() {
	return detailName;
  }
  
  public void setDetailName(String detailName) {
	this.detailName = detailName;
  }
  
  public String getDetailUrl() {
	return detailUrl;
  }
  
  public void setDetailUrl(String detailUrl) {
	this.detailUrl = detailUrl;
  }
  
  public Long getShowCommodityCount() {
	return showCommodityCount;
  }
  
  public void setShowCommodityCount(Long showCommodityCount) {
	this.showCommodityCount = showCommodityCount;
  }
  
  public Long getDisplayOrder() {
	return displayOrder;
  }
  
  public void setDisplayOrder(Long displayOrder) {
	this.displayOrder = displayOrder;
  }
  /**
   * @return データ行ID
   */
  public Long getOrmRowid() {
    return ormRowid;
  }
  /**
   * @param OrmRowid 设置データ行ID
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * @return 作成ユーザ
   */
  public String getCreatedUser() {
    return createdUser;
  }
  /**
   * @param CreatedUser 设置作成ユーザ
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * @return 作成日時
   */
  public Date getCreatedDatetime() {
    return createdDatetime;
  }
  /**
   * @param CreatedDatetime 设置作成日時
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  /**
   * @return 更新ユーザ
   */
  public String getUpdatedUser() {
    return updatedUser;
  }
  /**
   * @param UpdatedUser 设置更新ユーザ
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * @return 更新日時
   */
  public Date getUpdatedDatetime() {
    return updatedDatetime;
  }
  /**
   * @param UpdatedDatetime 设置更新日時
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }
  
  /**
   * @return the detailNameEn
   */
  public String getDetailNameEn() {
    return detailNameEn;
  }
  
  /**
   * @param detailNameEn the detailNameEn to set
   */
  public void setDetailNameEn(String detailNameEn) {
    this.detailNameEn = detailNameEn;
  }
  
  /**
   * @return the detailNameJp
   */
  public String getDetailNameJp() {
    return detailNameJp;
  }
  
  /**
   * @param detailNameJp the detailNameJp to set
   */
  public void setDetailNameJp(String detailNameJp) {
    this.detailNameJp = detailNameJp;
  }
  
  /**
   * @return the detailUrlEn
   */
  public String getDetailUrlEn() {
    return detailUrlEn;
  }
  
  /**
   * @param detailUrlEn the detailUrlEn to set
   */
  public void setDetailUrlEn(String detailUrlEn) {
    this.detailUrlEn = detailUrlEn;
  }
  
  /**
   * @return the detailUrlJp
   */
  public String getDetailUrlJp() {
    return detailUrlJp;
  }
  
  /**
   * @param detailUrlJp the detailUrlJp to set
   */
  public void setDetailUrlJp(String detailUrlJp) {
    this.detailUrlJp = detailUrlJp;
  }

}