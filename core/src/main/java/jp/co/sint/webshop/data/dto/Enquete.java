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
import java.math.RoundingMode;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Point;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.PointUtil;

/**
 * 「アンケート(ENQUETE)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 * 
 */
public class Enquete implements Serializable, WebshopEntity {

	/** Serial Version UID */
	private static final long serialVersionUID = -1;

	/** アンケートコード */
	@PrimaryKey(1)
	@Required
	@Length(16)
	@AlphaNum2
	@Metadata(name = "アンケートコード", order = 1)
	private String enqueteCode;

	/** アンケート名称 */
	@Required
	@Length(40)
	@Metadata(name = "アンケート名称", order = 2)
	private String enqueteName;

	/** アンケート開始日 */
	@Metadata(name = "アンケート開始日", order = 3)
	private Date enqueteStartDate;

	/** アンケート終了日 */
	@Metadata(name = "アンケート終了日", order = 4)
	private Date enqueteEndDate;

	/** アンケート付与ポイント数 */
	// @Precision(precision = 10, scale = 2)
	@Point
	@Metadata(name = "アンケート付与ポイント数", order = 5)
	private BigDecimal enqueteInvestPoint;

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
	// add by cs_yuli 20120516 start
	/******* 问卷调查名称(英文) ********/
	@Required
	@Length(40)
	@Metadata(name = "问卷调查名称(英文)", order = 11)
	private String enqueteNameEn;
	/****** 问卷调查名称(日文) ********/
	@Required
	@Length(40)
	@Metadata(name = "问卷调查名称(日文)", order = 12)
	private String enqueteNameJp;

	// add by cs_yuli 20120516 end
	/**
	 * アンケートコードを取得します
	 * 
	 * @return アンケートコード
	 */
	public String getEnqueteCode() {
		return this.enqueteCode;
	}

	/**
	 * アンケート名称を取得します
	 * 
	 * @return アンケート名称
	 */
	public String getEnqueteName() {
		return this.enqueteName;
	}

	/**
	 * アンケート開始日を取得します
	 * 
	 * @return アンケート開始日
	 */
	public Date getEnqueteStartDate() {
		return DateUtil.immutableCopy(this.enqueteStartDate);
	}

	/**
	 * アンケート終了日を取得します
	 * 
	 * @return アンケート終了日
	 */
	public Date getEnqueteEndDate() {
		return DateUtil.immutableCopy(this.enqueteEndDate);
	}

	/**
	 * アンケート付与ポイント数を取得します
	 * 
	 * @return アンケート付与ポイント数
	 */
	public BigDecimal getEnqueteInvestPoint() {
		return this.enqueteInvestPoint;
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
	 * アンケートコードを設定します
	 * 
	 * @param val
	 *            アンケートコード
	 */
	public void setEnqueteCode(String val) {
		this.enqueteCode = val;
	}

	/**
	 * アンケート名称を設定します
	 * 
	 * @param val
	 *            アンケート名称
	 */
	public void setEnqueteName(String val) {
		this.enqueteName = val;
	}

	/**
	 * アンケート開始日を設定します
	 * 
	 * @param val
	 *            アンケート開始日
	 */
	public void setEnqueteStartDate(Date val) {
		this.enqueteStartDate = DateUtil.immutableCopy(val);
	}

	/**
	 * アンケート終了日を設定します
	 * 
	 * @param val
	 *            アンケート終了日
	 */
	public void setEnqueteEndDate(Date val) {
		this.enqueteEndDate = DateUtil.immutableCopy(val);
	}

	/**
	 * アンケート付与ポイント数を設定します
	 * 
	 * @param val
	 *            アンケート付与ポイント数
	 */
	public void setEnqueteInvestPoint(BigDecimal val) {
		this.enqueteInvestPoint = val.setScale(PointUtil
				.getAcquiredPointScale(), RoundingMode.FLOOR);
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
	 * @param enqueteNameEn
	 *            the enqueteNameEn to set
	 */
	public void setEnqueteNameEn(String enqueteNameEn) {
		this.enqueteNameEn = enqueteNameEn;
	}

	/**
	 * @return the enqueteNameEn
	 */
	public String getEnqueteNameEn() {
		return enqueteNameEn;
	}

	/**
	 * @param enqueteNameJp
	 *            the enqueteNameJp to set
	 */
	public void setEnqueteNameJp(String enqueteNameJp) {
		this.enqueteNameJp = enqueteNameJp;
	}

	/**
	 * @return the enqueteNameJp
	 */
	public String getEnqueteNameJp() {
		return enqueteNameJp;
	}

}
