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
public class CampaignDoing implements Serializable, WebshopEntity {

	/** Serial Version UID */
	private static final long serialVersionUID = -1;

	/** キャンペーンコード */
	@PrimaryKey(1)
	@Required
	@Length(16)
	@AlphaNum2
	@Metadata(name = "促销编号", order = 1)
	private String campaignCode;

	/** キャンペーンタイプ*/
	@Required
	@Length(1)
	@Metadata(name = "キャンペーンタイプ", order = 2)
	private Long campaignType;
	/** キャンペーン内容 */
	@Required
	@Length(500)
	@Metadata(name = "促销名称(说明)", order = 3)
	private String attributrValue;

	/** データ行ID */
	@Required
	@Length(38)
	@Metadata(name = "データ行ID", order = 4)
	private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

	/** 作成ユーザ */
	@Required
	@Length(100)
	@Metadata(name = "作成ユーザ", order = 5)
	private String createdUser;

	/** 作成日時 */
	@Required
	@Metadata(name = "作成日時", order = 6)
	private Date createdDatetime;

	/** 更新ユーザ */
	@Required
	@Length(100)
	@Metadata(name = "更新ユーザ", order = 8)
	private String updatedUser;

	/** 更新日時 */
	@Required
	@Metadata(name = "更新日時", order = 8)
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
	 * キャンペーンコードを設定します
	 * 
	 * @param val
	 *            キャンペーンコード
	 */
	public void setCampaignCode(String val) {
		this.campaignCode = val;
	}

	/**
	 * @return the campaignType
	 */
	public Long getCampaignType() {
		return campaignType;
	}

	/**
	 * @param campaignType
	 *            the campaignType to set
	 */
	public void setCampaignType(Long campaignType) {
		this.campaignType = campaignType;
	}

	
	
	

	public String getAttributrValue() {
		return attributrValue;
	}

	public void setAttributrValue(String attributrValue) {
		this.attributrValue = attributrValue;
	}

	/**
	 * 作成日時を取得します
	 * 
	 * @return 作成日時
	 */
	public Date getCreatedDatetime() {
		return DateUtil.immutableCopy(this.createdDatetime);
	}

	public Date getUpdatedDatetime() {
		return DateUtil.immutableCopy(this.updatedDatetime);
	}

	public Long getOrmRowid() {
		return ormRowid;
	}

	public void setOrmRowid(Long ormRowid) {
		this.ormRowid = ormRowid;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public void setCreatedDatetime(Date createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	public void setUpdatedDatetime(Date updatedDatetime) {
		this.updatedDatetime = updatedDatetime;
	}


}
