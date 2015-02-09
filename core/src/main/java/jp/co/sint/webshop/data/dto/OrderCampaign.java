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
 * 「キャンペーン(CAMPAIGN)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 * 
 */
public class OrderCampaign implements Serializable, WebshopEntity {

	/** Serial Version UID */
	private static final long serialVersionUID = -1;

	@PrimaryKey(2)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "订单编号", order = 1)
  private String orderNo;
	
	/** キャンペーンコード */
	@PrimaryKey(2)
	@Required
	@Length(16)
	@AlphaNum2
	@Metadata(name = "促销编号", order = 2)
	private String campaignCode;

	/** キャンペーン名称 */
	@Required
	@Length(50)
	@Metadata(name = "促销名称(说明)", order = 3)
	private String campaignName;

	/** キャンペーン値引率 */
	@Required
	@Length(1)
	@Metadata(name = "行为类型", order = 4)
	private Long campaignType;

	/** キャンペーン名称 */
  @Required
  @Metadata(name = "促销名称(说明)", order = 5)
  private String attributrValue;

	/** データ行ID */
	@Required
	@Length(38)
	@Metadata(name = "データ行ID", order = 7)
	private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

	/** 作成ユーザ */
	@Required
	@Length(100)
	@Metadata(name = "作成ユーザ", order = 8)
	private String createdUser;

	/** 作成日時 */
	@Required
	@Metadata(name = "作成日時", order = 9)
	private Date createdDatetime;

	/** 更新ユーザ */
	@Required
	@Length(100)
	@Metadata(name = "更新ユーザ", order = 10)
	private String updatedUser;

	/** 更新日時 */
	@Required
	@Metadata(name = "更新日時", order = 11)
	private Date updatedDatetime;
  
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
	 * キャンペーンコードを設定します
	 * 
	 * @param val
	 *            キャンペーンコード
	 */
	public void setCampaignCode(String val) {
		this.campaignCode = val;
	}

	/**
	 * キャンペーン名称を設定します
	 * 
	 * @param val
	 *            キャンペーン名称
	 */
	public void setCampaignName(String val) {
		this.campaignName = val;
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
   * @return the campaignType
   */
  public Long getCampaignType() {
    return campaignType;
  }
  
  /**
   * @param campaignType the campaignType to set
   */
  public void setCampaignType(Long campaignType) {
    this.campaignType = campaignType;
  }

  
  /**
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  
  /**
   * @param orderNo the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  
  /**
   * @return the attributrValue
   */
  public String getAttributrValue() {
    return attributrValue;
  }

  
  /**
   * @param attributrValue the attributrValue to set
   */
  public void setAttributrValue(String attributrValue) {
    this.attributrValue = attributrValue;
  }

}
