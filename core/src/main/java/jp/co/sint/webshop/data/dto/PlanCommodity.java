package jp.co.sint.webshop.data.dto;

/**
 * 企划关联商品表
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
 * AbstractPlanCommodity entity provides the base persistence definition of the PlanCommodity
 * @author OB
 */

public class PlanCommodity implements Serializable, WebshopEntity {

  private static final long serialVersionUID = 1L;

  /** 企划编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
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

  /** 商品编号 */
  @PrimaryKey(4)
  @Required
  @Length(24)
  @AlphaNum2
  @Metadata(name = "商品编号", order = 4)
  private String commodityCode;

  /** 表示顺序 */
  @Length(8)
  @Digit
  @Metadata(name = "表示顺序", order = 7)
  private Long displayOrder;

  /** データ行ID */
  @Required
  @Digit
  @Metadata(name = "データ行ID", order = 6)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 7)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Datetime
  @Metadata(name = "作成日時", order = 8)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 9)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Datetime
  @Metadata(name = "更新日時", order = 10)
  private Date updatedDatetime;

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
  public String getCommodityCode() {
	return commodityCode;
  }
  public void setCommodityCode(String commodityCode) {
	this.commodityCode = commodityCode;
  }
  public Long getDisplayOrder() {
	return displayOrder;
  }
  public void setDisplayOrder(Long displayOrder) {
	this.displayOrder = displayOrder;
  }
  
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

}