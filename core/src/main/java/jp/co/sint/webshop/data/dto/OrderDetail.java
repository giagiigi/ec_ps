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
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Percentage;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「受注明細(ORDER_DETAIL)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class OrderDetail implements Serializable, WebshopEntity , Cloneable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 受注番号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  //@Digit
  @Metadata(name = "受注番号", order = 1)
  private String orderNo;

  /** ショップコード */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 2)
  private String shopCode;

  /** SKUコード */
  @PrimaryKey(3)
  @Required
  @Length(24)
  @AlphaNum2
  @Metadata(name = "SKUコード", order = 3)
  private String skuCode;

  /** 商品コード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード", order = 4)
  private String commodityCode;

  /** 商品名称 */
  @Required
  @Length(200)
  @Metadata(name = "商品名称", order = 5)
  private String commodityName;

  /** 規格詳細1名称 */
  @Length(20)
  @Metadata(name = "規格詳細1名称", order = 6)
  private String standardDetail1Name;

  /** 規格詳細2名称 */
  @Length(20)
  @Metadata(name = "規格詳細2名称", order = 7)
  private String standardDetail2Name;

  /** 購入商品数 */
  @Required
  @Length(8)
  @Metadata(name = "購入商品数", order = 8)
  private Long purchasingAmount;

  /** 商品単価 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "商品単価", order = 9)
  private BigDecimal unitPrice;

  /** 販売価格 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "販売価格", order = 10)
  private BigDecimal retailPrice;

  /** 販売時消費税額 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "販売時消費税額", order = 11)
  private BigDecimal retailTax;

  /** 商品消費税率 */
  @Length(3)
  @Percentage
  @Metadata(name = "商品消費税率", order = 12)
  private Long commodityTaxRate;

  /** 商品消費税額 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "商品消費税額", order = 13)
  private BigDecimal commodityTax;

  /** 商品消費税区分 */
  @Required
  @Length(1)
  @Domain(TaxType.class)
  @Metadata(name = "商品消費税区分", order = 14)
  private Long commodityTaxType;

  /** キャンペーンコード */
  @Length(16)
  @AlphaNum2
  @Metadata(name = "キャンペーンコード", order = 15)
  private String campaignCode;

  /** キャンペーン名称 */
  @Length(50)
  @Metadata(name = "キャンペーン名称", order = 16)
  private String campaignName;

  /** キャンペーン値引率 */
  @Length(3)
  @Percentage
  @Metadata(name = "キャンペーン値引率", order = 17)
  private Long campaignDiscountRate;

  /** 適用ポイント付与率 */
  @Required
  @Length(8)
  @Metadata(name = "適用ポイント付与率", order = 18)
  private Long appliedPointRate;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 19)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 20)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 21)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 22)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 23)
  private Date updatedDatetime;

  // 20111229 shen add start
  /** 商品重量 */
  @Required
  @Precision(precision = 8, scale = 3)
  @Metadata(name = "商品重量", order = 24)
  private BigDecimal commodityWeight;

  // 20111229 shen add end

  // soukai add 2012/01/10 ob start
  @Length(16)
  @Metadata(name = "品牌编号", order = 25)
  private String brandCode;
  
  @Length(50)
  @Metadata(name = "品牌名称", order = 26)
  private String brandName;
  
  @Length(16)
  @Metadata(name = "促销企划编号", order = 27)
  private String salePlanCode;
  
  @Length(50)
  @Metadata(name = "促销企划名称", order = 28)
  private String salePlanName;
  
  @Length(16)
  @Metadata(name = "特集企划编号", order = 29)
  private String featuredPlanCode;
  
  @Length(50)
  @Metadata(name = "特集企划名称", order = 30)
  private String featuredPlanName;
  // soukai add 2012/01/10 ob end
  
  // 20120120 ysy add start
  private String discountPrice;
  
  // 20121119 促销活动 ob add start
  @Length(1)
  @Metadata(name = "商品区分", order = 31)
  private Long commodityType;
  
  @Length(1)
  @Metadata(name = "套餐区分", order = 32)
  private Long setCommodityFlg;
  // 20121119 促销活动 ob add end
  
  
  /**
 * @return the discountPrice
 */
public String getDiscountPrice() {
	return discountPrice;
}

/**
 * @param discountPrice the discountPrice to set
 */
public void setDiscountPrice(String discountPrice) {
	this.discountPrice = discountPrice;
}

/**
   * 受注番号を取得します
   * 
   * @return 受注番号
   */
  public String getOrderNo() {
    return this.orderNo;
  }

  /**
   * ショップコードを取得します
   * 
   * @return ショップコード
   */
  public String getShopCode() {
    return this.shopCode;
  }

  /**
   * SKUコードを取得します
   * 
   * @return SKUコード
   */
  public String getSkuCode() {
    return this.skuCode;
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
   * 商品名称を取得します
   * 
   * @return 商品名称
   */
  public String getCommodityName() {
    return this.commodityName;
  }

  /**
   * 規格詳細1名称を取得します
   * 
   * @return 規格詳細1名称
   */
  public String getStandardDetail1Name() {
    return this.standardDetail1Name;
  }

  /**
   * 規格詳細2名称を取得します
   * 
   * @return 規格詳細2名称
   */
  public String getStandardDetail2Name() {
    return this.standardDetail2Name;
  }

  /**
   * 購入商品数を取得します
   * 
   * @return 購入商品数
   */
  public Long getPurchasingAmount() {
    return this.purchasingAmount;
  }

  /**
   * 商品単価を取得します
   * 
   * @return 商品単価
   */
  public BigDecimal getUnitPrice() {
    return this.unitPrice;
  }

  /**
   * 販売価格を取得します
   * 
   * @return 販売価格
   */
  public BigDecimal getRetailPrice() {
    return this.retailPrice;
  }

  /**
   * 販売時消費税額を取得します
   * 
   * @return 販売時消費税額
   */
  public BigDecimal getRetailTax() {
    return this.retailTax;
  }

  /**
   * 商品消費税率を取得します
   * 
   * @return 商品消費税率
   */
  public Long getCommodityTaxRate() {
    return this.commodityTaxRate;
  }

  /**
   * 商品消費税額を取得します
   * 
   * @return 商品消費税額
   */
  public BigDecimal getCommodityTax() {
    return this.commodityTax;
  }

  /**
   * 商品消費税区分を取得します
   * 
   * @return 商品消費税区分
   */
  public Long getCommodityTaxType() {
    return this.commodityTaxType;
  }

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
   * キャンペーン値引率を取得します
   * 
   * @return キャンペーン値引率
   */
  public Long getCampaignDiscountRate() {
    return this.campaignDiscountRate;
  }

  /**
   * 適用ポイント付与率を取得します
   * 
   * @return 適用ポイント付与率
   */
  public Long getAppliedPointRate() {
    return this.appliedPointRate;
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
   * 受注番号を設定します
   * 
   * @param val
   *          受注番号
   */
  public void setOrderNo(String val) {
    this.orderNo = val;
  }

  /**
   * ショップコードを設定します
   * 
   * @param val
   *          ショップコード
   */
  public void setShopCode(String val) {
    this.shopCode = val;
  }

  /**
   * SKUコードを設定します
   * 
   * @param val
   *          SKUコード
   */
  public void setSkuCode(String val) {
    this.skuCode = val;
  }

  /**
   * 商品コードを設定します
   * 
   * @param val
   *          商品コード
   */
  public void setCommodityCode(String val) {
    this.commodityCode = val;
  }

  /**
   * 商品名称を設定します
   * 
   * @param val
   *          商品名称
   */
  public void setCommodityName(String val) {
    this.commodityName = val;
  }

  /**
   * 規格詳細1名称を設定します
   * 
   * @param val
   *          規格詳細1名称
   */
  public void setStandardDetail1Name(String val) {
    this.standardDetail1Name = val;
  }

  /**
   * 規格詳細2名称を設定します
   * 
   * @param val
   *          規格詳細2名称
   */
  public void setStandardDetail2Name(String val) {
    this.standardDetail2Name = val;
  }

  /**
   * 購入商品数を設定します
   * 
   * @param val
   *          購入商品数
   */
  public void setPurchasingAmount(Long val) {
    this.purchasingAmount = val;
  }

  /**
   * 商品単価を設定します
   * 
   * @param val
   *          商品単価
   */
  public void setUnitPrice(BigDecimal val) {
    this.unitPrice = val;
  }

  /**
   * 販売価格を設定します
   * 
   * @param val
   *          販売価格
   */
  public void setRetailPrice(BigDecimal val) {
    this.retailPrice = val;
  }

  /**
   * 販売時消費税額を設定します
   * 
   * @param val
   *          販売時消費税額
   */
  public void setRetailTax(BigDecimal val) {
    this.retailTax = val;
  }

  /**
   * 商品消費税率を設定します
   * 
   * @param val
   *          商品消費税率
   */
  public void setCommodityTaxRate(Long val) {
    this.commodityTaxRate = val;
  }

  /**
   * 商品消費税額を設定します
   * 
   * @param val
   *          商品消費税額
   */
  public void setCommodityTax(BigDecimal val) {
    this.commodityTax = val;
  }

  /**
   * 商品消費税区分を設定します
   * 
   * @param val
   *          商品消費税区分
   */
  public void setCommodityTaxType(Long val) {
    this.commodityTaxType = val;
  }

  /**
   * キャンペーンコードを設定します
   * 
   * @param val
   *          キャンペーンコード
   */
  public void setCampaignCode(String val) {
    this.campaignCode = val;
  }

  /**
   * キャンペーン名称を設定します
   * 
   * @param val
   *          キャンペーン名称
   */
  public void setCampaignName(String val) {
    this.campaignName = val;
  }

  /**
   * キャンペーン値引率を設定します
   * 
   * @param val
   *          キャンペーン値引率
   */
  public void setCampaignDiscountRate(Long val) {
    this.campaignDiscountRate = val;
  }

  /**
   * 適用ポイント付与率を設定します
   * 
   * @param val
   *          適用ポイント付与率
   */
  public void setAppliedPointRate(Long val) {
    this.appliedPointRate = val;
  }

  /**
   * データ行IDを設定します
   * 
   * @param val
   *          データ行ID
   */
  public void setOrmRowid(Long val) {
    this.ormRowid = val;
  }

  /**
   * 作成ユーザを設定します
   * 
   * @param val
   *          作成ユーザ
   */
  public void setCreatedUser(String val) {
    this.createdUser = val;
  }

  /**
   * 作成日時を設定します
   * 
   * @param val
   *          作成日時
   */
  public void setCreatedDatetime(Date val) {
    this.createdDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 更新ユーザを設定します
   * 
   * @param val
   *          更新ユーザ
   */
  public void setUpdatedUser(String val) {
    this.updatedUser = val;
  }

  /**
   * 更新日時を設定します
   * 
   * @param val
   *          更新日時
   */
  public void setUpdatedDatetime(Date val) {
    this.updatedDatetime = DateUtil.immutableCopy(val);
  }

  public BigDecimal getCommodityWeight() {
    return commodityWeight;
  }

  public void setCommodityWeight(BigDecimal commodityWeight) {
    this.commodityWeight = commodityWeight;
  }

/**
 * @return the brandCode
 */
public String getBrandCode() {
	return brandCode;
}

/**
 * @param brandCode the brandCode to set
 */
public void setBrandCode(String brandCode) {
	this.brandCode = brandCode;
}

/**
 * @return the brandName
 */
public String getBrandName() {
	return brandName;
}

/**
 * @param brandName the brandName to set
 */
public void setBrandName(String brandName) {
	this.brandName = brandName;
}

/**
 * @return the salePlanCode
 */
public String getSalePlanCode() {
	return salePlanCode;
}

/**
 * @param salePlanCode the salePlanCode to set
 */
public void setSalePlanCode(String salePlanCode) {
	this.salePlanCode = salePlanCode;
}

/**
 * @return the salePlanName
 */
public String getSalePlanName() {
	return salePlanName;
}

/**
 * @param salePlanName the salePlanName to set
 */
public void setSalePlanName(String salePlanName) {
	this.salePlanName = salePlanName;
}

/**
 * @return the featuredPlanCode
 */
public String getFeaturedPlanCode() {
	return featuredPlanCode;
}

/**
 * @param featuredPlanCode the featuredPlanCode to set
 */
public void setFeaturedPlanCode(String featuredPlanCode) {
	this.featuredPlanCode = featuredPlanCode;
}

/**
 * @return the featuredPlanName
 */
public String getFeaturedPlanName() {
	return featuredPlanName;
}

/**
 * @param featuredPlanName the featuredPlanName to set
 */
public void setFeaturedPlanName(String featuredPlanName) {
	this.featuredPlanName = featuredPlanName;
}


public Long getCommodityType() {
  return commodityType;
}


public void setCommodityType(Long commodityType) {
  this.commodityType = commodityType;
}


public Long getSetCommodityFlg() {
  return setCommodityFlg;
}


public void setSetCommodityFlg(Long setCommodityFlg) {
  this.setCommodityFlg = setCommodityFlg;
}

public Object clone() {
  try {
    return super.clone();
  } catch (CloneNotSupportedException e) {
    return null;
  }
}


}
