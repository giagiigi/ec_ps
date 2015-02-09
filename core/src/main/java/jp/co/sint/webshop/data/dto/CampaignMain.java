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
public class CampaignMain implements Serializable, WebshopEntity {

	/** Serial Version UID */
	private static final long serialVersionUID = -1;

	/** キャンペーンコード */
	@PrimaryKey(1)
	@Required
	@Length(16)
	@AlphaNum2
	@Metadata(name = "促销编号", order = 1)
	private String campaignCode;

	/** キャンペーン名称 */
	@Required
	@Length(50)
	@Metadata(name = "促销名称(说明)", order = 2)
	private String campaignName;
	
	/** キャンペーン開始日 */
	@Required
	@Metadata(name = "开始日期(年月日 时)", order = 3)
	private Date campaignStartDate;

	/** キャンペーン終了日 */
	@Required
	@Metadata(name = "结束日期(年月日 时", order = 4)
	private Date campaignEndDate;
	
	/** キャンペーン値引率 */
	@Required
	@Length(1)
	@Metadata(name = "キャンペーン値引率", order = 5)
	private Long campaignType;
	
	/** メモ */
	@Length(256)
	@Metadata(name = "备注", order = 6)
	private String memo;
	
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
	
	/** 促销英文名 */
	@Length(100)
	@Metadata(name = "促销英文名", order = 12)
	private String campaignNameEn;
	
	/** 促销日文名 */
	@Length(100)
	@Metadata(name = "促销日文名", order = 13)
	private String campaignNameJp;
    
	// 2012/11/16 促销对应 ob add start
	/** 订购次数限制 */
	@Required
	@Length(8)
	@Metadata(name = "订购次数限制", order = 14)
	private Long orderLimit;
	// 2012/11/16 促销对应 ob add end

	@Length(8)
	@Metadata(name = "赠品促销活动的赠品数量", order = 15)
	private Long giftAmount;
	
	//20140724 hdh add start
	@Length(8)
	@Metadata(name = "最小购买商品数", order = 16)
	private Long minCommodityNum;
	
	//20140724 hdh add end
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
	 * メモを取得します
	 * 
	 * @return メモ
	 */
	public String getMemo() {
		return this.memo;
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
	 * キャンペーン開始日を設定します
	 * 
	 * @param val
	 *            キャンペーン開始日
	 */
	public void setCampaignStartDate(Date val) {
		this.campaignStartDate = DateUtil.immutableCopy(val);
	}

	/**
	 * キャンペーン終了日を設定します
	 * 
	 * @param val
	 *            キャンペーン終了日
	 */
	public void setCampaignEndDate(Date val) {
		this.campaignEndDate = DateUtil.immutableCopy(val);
	}

	/**
	 * メモを設定します
	 * 
	 * @param val
	 *            メモ
	 */
	public void setMemo(String val) {
		this.memo = val;
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
	 * @param campaignNameEn
	 *            the campaignNameEn to set
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
	 *            the campaignNameJp to set
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

  // 2012/11/16 促销对应 ob add start 
  /**
   * @return the orderLimit
   */
  public Long getOrderLimit() {
    return orderLimit;
  }

  
  /**
   * @param orderLimit the orderLimit to set
   */
  public void setOrderLimit(Long orderLimit) {
    this.orderLimit = orderLimit;
  }

  // 2012/11/16 促销对应 ob add end

  
  /**
   * @return the giftAmount
   */
  public Long getGiftAmount() {
    return giftAmount;
  }

  
  /**
   * @param giftAmount the giftAmount to set
   */
  public void setGiftAmount(Long giftAmount) {
    this.giftAmount = giftAmount;
  }

  
  /**
   * @return the minCommodityNum
   */
  public Long getMinCommodityNum() {
    return minCommodityNum;
  }

  
  /**
   * @param minCommodityNum the minCommodityNum to set
   */
  public void setMinCommodityNum(Long minCommodityNum) {
    this.minCommodityNum = minCommodityNum;
  }

  

}
