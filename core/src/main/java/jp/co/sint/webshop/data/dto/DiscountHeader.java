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
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「限时限量折扣(DiscountHeader)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 * 
 */
public class DiscountHeader implements Serializable, WebshopEntity {

	/** Serial Version UID */
	private static final long serialVersionUID = -1;

	/** 折扣编号 */
	@PrimaryKey(1)
	@Required
	@Length(16)
	@AlphaNum2
	@Metadata(name = "折扣编号", order = 1)
	private String discountCode;

	/** 折扣名称 */
	@Required
	@Length(50)
	@Metadata(name = "折扣名称", order = 2)
	private String discountName;
	
	/** 折扣开始日 */
	@Required
	@Metadata(name = "开始日期(年月日 时)", order = 3)
	private Date discountStartDatetime;

	/** 折扣结束日 */
	@Required
	@Metadata(name = "结束日期(年月日 时)", order = 4)
	private Date discountEndDatetime;
	
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

  
  @Length(8)
  @Metadata(name = "商品种类数量", order = 10)
  private Long commodityTypeNum;
  
  
  @Length(1)
  @Metadata(name="SO发送类型", order=11)
  private Long soCouponFlg;	
	
  /**
   * @return the discountCode
   */
  public String getDiscountCode() {
    return discountCode;
  }

  
  /**
   * @return the discountName
   */
  public String getDiscountName() {
    return discountName;
  }

  
  /**
   * @return the discountStartDatetime
   */
  public Date getDiscountStartDatetime() {
    return DateUtil.immutableCopy(this.discountStartDatetime);
  }

  
  /**
   * @return the discountEndDatetime
   */
  public Date getDiscountEndDatetime() {
    return DateUtil.immutableCopy(this.discountEndDatetime);
  }

  
  /**
   * @return the ormRowid
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  
  /**
   * @return the createdUser
   */
  public String getCreatedUser() {
    return createdUser;
  }

  
  /**
   * @return the createdDatetime
   */
  public Date getCreatedDatetime() {
    return DateUtil.immutableCopy(this.createdDatetime);
  }

  
  /**
   * @return the updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  
  /**
   * @return the updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(this.updatedDatetime);
  }

  
  /**
   * @param discountCode the discountCode to set
   */
  public void setDiscountCode(String discountCode) {
    this.discountCode = discountCode;
  }

  
  /**
   * @param discountName the discountName to set
   */
  public void setDiscountName(String discountName) {
    this.discountName = discountName;
  }

  
  /**
   * @param discountStartDatetime the discountStartDatetime to set
   */
  public void setDiscountStartDatetime(Date discountStartDatetime) {
    this.discountStartDatetime = DateUtil.immutableCopy(discountStartDatetime);
  }

  
  /**
   * @param discountEndDatetime the discountEndDatetime to set
   */
  public void setDiscountEndDatetime(Date discountEndDatetime) {
    this.discountEndDatetime = DateUtil.immutableCopy(discountEndDatetime);
  }

  
  /**
   * @param ormRowid the ormRowid to set
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  
  /**
   * @param createdUser the createdUser to set
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  
  /**
   * @param createdDatetime the createdDatetime to set
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = DateUtil.immutableCopy(createdDatetime);
  }

  
  /**
   * @param updatedUser the updatedUser to set
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  
  /**
   * @param updatedDatetime the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
  }


  
  /**
   * @return the commodityTypeNum
   */
  public Long getCommodityTypeNum() {
    return commodityTypeNum;
  }


  
  /**
   * @param commodityTypeNum the commodityTypeNum to set
   */
  public void setCommodityTypeNum(Long commodityTypeNum) {
    this.commodityTypeNum = commodityTypeNum;
  }


  public void setSoCouponFlg(Long soCouponFlg) {
    this.soCouponFlg = soCouponFlg;
  }


  public Long getSoCouponFlg() {
    return soCouponFlg;
  }


}
