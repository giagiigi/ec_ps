//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.service.campain;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
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
	private String campaignCode;

	/** キャンペーン名称 */
	private String campaignName;

	/** キャンペーン開始日 */
	private Date campaignStartDate;

	/** キャンペーン終了日 */
	private Date campaignEndDate;

	/** キャンペーン値引率 */
	private Long campaignType;

	/** メモ */
	private String memo;

	/** データ行ID */
	private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

	/** 作成ユーザ */
	private String createdUser;

	/** 作成日時 */
	private Date createdDatetime;

	/** 更新ユーザ */
	private String updatedUser;

	/** 更新日時 */
	private Date updatedDatetime;
	
	/** 促销英文名 */
	private String campaignNameEn;
	
	/** 促销日文名 */
	private String campaignNameJp;

	/** 订购次数限制 */
  private Long orderLimit;
  
  /** 条件类型 */
  private Long campaignConditionType;
  
  /** 对象商品（包含/仅有） */
  private Long campaignConditionFlg;
  
  /** 内容 */
  private String attributrValue;
  
  /** 对象商品限定件数 */
  private Long maxCommodityNum;
  
  /** 赠品促销活动的赠品数量 */
  private Long giftAmount;
  
  /****最小购买数 ***/
  private Long minCommodityNum;
  
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

  
  /**
   * @return the campaignConditionType
   */
  public Long getCampaignConditionType() {
    return campaignConditionType;
  }

  
  /**
   * @param campaignConditionType the campaignConditionType to set
   */
  public void setCampaignConditionType(Long campaignConditionType) {
    this.campaignConditionType = campaignConditionType;
  }

  
  /**
   * @return the campaignConditionFlg
   */
  public Long getCampaignConditionFlg() {
    return campaignConditionFlg;
  }

  
  /**
   * @param campaignConditionFlg the campaignConditionFlg to set
   */
  public void setCampaignConditionFlg(Long campaignConditionFlg) {
    this.campaignConditionFlg = campaignConditionFlg;
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

  
  /**
   * @return the maxCommodityNum
   */
  public Long getMaxCommodityNum() {
    return maxCommodityNum;
  }

  
  /**
   * @param maxCommodityNum the maxCommodityNum to set
   */
  public void setMaxCommodityNum(Long maxCommodityNum) {
    this.maxCommodityNum = maxCommodityNum;
  }

  
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
