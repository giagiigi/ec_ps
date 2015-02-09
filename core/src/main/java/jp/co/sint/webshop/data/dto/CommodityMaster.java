//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;

/**
 * 「キャンペーン(CAMPAIGN)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 * 
 */
public class CommodityMaster implements Serializable, WebshopEntity {

	/** Serial Version UID */
	private static final long serialVersionUID = -1;

	/** キャンペーンコード */
	@PrimaryKey(1)
	@Required
	@Length(16)
	@AlphaNum2
	@Metadata(name = "商铺编号", order = 1)
	private String shopCode;
	
	@PrimaryKey(1)
	@Required
	@Length(16)
	@Metadata(name = "主商品编号", order = 2)
	private String commodityCode;
	
	
	@Length(50)
	@Metadata(name = "主商品名称", order = 3)
	private String commodityName;

  @Required
  @Length(16)
  @Metadata(name = "JD商品编号", order = 4)
  private String jdCommodityCode;
  
  @Required
  @Length(16)
  @Metadata(name = "Tmall商品编号", order = 5)
  private String tmallCommodityCode;
	

	/** データ行ID */
	@Required
	@Length(38)
	@Metadata(name = "データ行ID", order = 6)
	private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

	/** 作成ユーザ */
	@Required
	@Length(100)
	@Metadata(name = "作成ユーザ", order = 7)
	private String createdUser;

	/** 作成日時 */
	@Required
	@Metadata(name = "作成日時", order = 8)
	private Date createdDatetime;

	/** 更新ユーザ */
	@Required
	@Length(100)
	@Metadata(name = "更新ユーザ", order = 9)
	private String updatedUser;

	/** 更新日時 */
	@Required
	@Metadata(name = "更新日時", order = 10)
	private Date updatedDatetime;

  
  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  
  /**
   * @param shopCode the shopCode to set
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
   * @param commodityCode the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  
  /**
   * @return the commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  
  /**
   * @param commodityName the commodityName to set
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  
  /**
   * @return the jdCommodityCode
   */
  public String getJdCommodityCode() {
    return jdCommodityCode;
  }

  
  /**
   * @param jdCommodityCode the jdCommodityCode to set
   */
  public void setJdCommodityCode(String jdCommodityCode) {
    this.jdCommodityCode = jdCommodityCode;
  }

  
  /**
   * @return the tmallCommodityCode
   */
  public String getTmallCommodityCode() {
    return tmallCommodityCode;
  }

  
  /**
   * @param tmallCommodityCode the tmallCommodityCode to set
   */
  public void setTmallCommodityCode(String tmallCommodityCode) {
    this.tmallCommodityCode = tmallCommodityCode;
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
	
	
	
}