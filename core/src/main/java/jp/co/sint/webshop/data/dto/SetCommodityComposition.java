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
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「套餐商品明细(SET_COMMODITY_COMPOSITION)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class SetCommodityComposition implements Serializable, WebshopEntity {

	/** Serial Version UID */
	private static final long serialVersionUID = -1;

	/** 店铺番号（FK） */
	@PrimaryKey(1)
	@Required
	@Length(16)
	@Metadata(name = "店铺番号（FK）", order = 1)
	private String shopCode;

	/** 商品编号 */
	@PrimaryKey(2)
	@Required
	@Length(16)
	@Metadata(name = "商品编号", order = 2)
	private String commodityCode;

	/** 子商品编号 */
	@PrimaryKey(3)
	@Required
	@Length(16)
	@Metadata(name = "子商品编号", order = 3)
	private String childCommodityCode;
	
	/** 销售价格 */
	@Required
	@Precision(precision = 10, scale = 2)
	@Currency
	@Metadata(name = "销售价格", order = 4)
	private BigDecimal retailPrice;

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
  
  /** TMALL销售价格 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "TMALL销售价格", order = 13)
  private BigDecimal tmallRetailPrice;
	
	/**
	 * ショップコードを取得します
	 * 
	 * @return ショップコード
	 */
	public String getShopCode() {
		return this.shopCode;
	}

	/**
	 * 商品コードを取得します
	 * 
	 * @return 商品コード
	 */
	public String getCommodityCode() {
		return this.commodityCode;
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
	 * ショップコードを設定します
	 * 
	 * @param val
	 *            ショップコード
	 */
	public void setShopCode(String val) {
		this.shopCode = val;
	}

	/**
	 * 商品コードを設定します
	 * 
	 * @param val
	 *            商品コード
	 */
	public void setCommodityCode(String val) {
		this.commodityCode = val;
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
	 * childCommodityCodeを取得します
	 * 
	 * @return childCommodityCode
	 */
	public String getChildCommodityCode() {
		return childCommodityCode;
	}

	/**
	 * childCommodityCodeを設定します
	 * 
	 * @param val
	 *            childCommodityCode
	 */
	public void setChildCommodityCode(String childCommodityCode) {
		this.childCommodityCode = childCommodityCode;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}


  
  /**
   * @return the tmallRetailPrice
   */
  public BigDecimal getTmallRetailPrice() {
    return tmallRetailPrice;
  }

  
  /**
   * @param tmallRetailPrice the tmallRetailPrice to set
   */
  public void setTmallRetailPrice(BigDecimal tmallRetailPrice) {
    this.tmallRetailPrice = tmallRetailPrice;
  }
}
