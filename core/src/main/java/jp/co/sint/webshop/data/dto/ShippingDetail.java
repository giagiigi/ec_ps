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
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
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
 * 「出荷明細(SHIPPING_DETAIL)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class ShippingDetail implements Serializable, WebshopEntity , Cloneable{

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 出荷番号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "出荷番号", order = 1)
  private String shippingNo;

  /** 出荷明細番号 */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "出荷明細番号", order = 2)
  private Long shippingDetailNo;

  /** ショップコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 3)
  private String shopCode;

  /** SKUコード */
  @Required
  @Length(24)
  @AlphaNum2
  @Metadata(name = "SKUコード", order = 4)
  private String skuCode;

  /** 商品単価 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "商品単価", order = 5)
  private BigDecimal unitPrice;

  /** 特別価格 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "特別価格", order = 6)
  private BigDecimal discountPrice;

  /** 値引額 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "値引額", order = 7)
  private BigDecimal discountAmount;

  /** 販売価格 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "販売価格", order = 8)
  private BigDecimal retailPrice;

  /** 販売時消費税額 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "販売時消費税額", order = 9)
  private BigDecimal retailTax;

  /** 購入商品数 */
  @Required
  @Length(8)
  @Metadata(name = "購入商品数", order = 10)
  private Long purchasingAmount;

  /** ギフトコード */
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ギフトコード", order = 11)
  private String giftCode;

  /** ギフト名称 */
  @Length(40)
  @Metadata(name = "ギフト名称", order = 12)
  private String giftName;

  /** ギフト価格 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "ギフト価格", order = 13)
  private BigDecimal giftPrice;

  /** ギフト消費税率 */
  @Length(3)
  @Percentage
  @Metadata(name = "ギフト消費税率", order = 14)
  private Long giftTaxRate;

  /** ギフト消費税額 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "ギフト消費税額", order = 15)
  private BigDecimal giftTax;

  /** ギフト消費税区分 */
  @Required
  @Length(1)
  @Domain(TaxType.class)
  @Metadata(name = "ギフト消費税区分", order = 16)
  private Long giftTaxType;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 17)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 18)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 19)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 20)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 21)
  private Date updatedDatetime;
  
  //20121119 促销活动 ob add start
  @Length(1)
  @Metadata(name = "商品区分", order = 22)
  private Long commodityType;
  
  @Length(1)
  @Metadata(name = "套餐区分", order = 23)
  private Long setCommodityFlg;
  
  @Length(16)
  @AlphaNum2
  @Metadata(name = "促销活动编号", order = 24)
  private String campaignCode;
  
  @Length(50)
  @Metadata(name = "促销活动名称", order = 25)
  private String campaignName;
  
  @Length(1)
  @Metadata(name = "折扣类型", order = 26)
  private Long discountType;
  
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "折扣值", order = 27)
  private BigDecimal discountValue;
  // 20121119 促销活动 ob add end

  /**
   * 出荷番号を取得します
   *
   * @return 出荷番号
   */
  public String getShippingNo() {
    return this.shippingNo;
  }

  /**
   * 出荷明細番号を取得します
   *
   * @return 出荷明細番号
   */
  public Long getShippingDetailNo() {
    return this.shippingDetailNo;
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
   * 商品単価を取得します
   *
   * @return 商品単価
   */
  public BigDecimal getUnitPrice() {
    return this.unitPrice;
  }

  /**
   * 特別価格を取得します
   *
   * @return 特別価格
   */
  public BigDecimal getDiscountPrice() {
    return this.discountPrice;
  }

  /**
   * 値引額を取得します
   *
   * @return 値引額
   */
  public BigDecimal getDiscountAmount() {
    return this.discountAmount;
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
   * 購入商品数を取得します
   *
   * @return 購入商品数
   */
  public Long getPurchasingAmount() {
    return this.purchasingAmount;
  }

  /**
   * ギフトコードを取得します
   *
   * @return ギフトコード
   */
  public String getGiftCode() {
    return this.giftCode;
  }

  /**
   * ギフト名称を取得します
   *
   * @return ギフト名称
   */
  public String getGiftName() {
    return this.giftName;
  }

  /**
   * ギフト価格を取得します
   *
   * @return ギフト価格
   */
  public BigDecimal getGiftPrice() {
    return this.giftPrice;
  }

  /**
   * ギフト消費税率を取得します
   *
   * @return ギフト消費税率
   */
  public Long getGiftTaxRate() {
    return this.giftTaxRate;
  }

  /**
   * ギフト消費税額を取得します
   *
   * @return ギフト消費税額
   */
  public BigDecimal getGiftTax() {
    return this.giftTax;
  }

  /**
   * ギフト消費税区分を取得します
   *
   * @return ギフト消費税区分
   */
  public Long getGiftTaxType() {
    return this.giftTaxType;
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
   * 出荷番号を設定します
   *
   * @param  val 出荷番号
   */
  public void setShippingNo(String val) {
    this.shippingNo = val;
  }

  /**
   * 出荷明細番号を設定します
   *
   * @param  val 出荷明細番号
   */
  public void setShippingDetailNo(Long val) {
    this.shippingDetailNo = val;
  }

  /**
   * ショップコードを設定します
   *
   * @param  val ショップコード
   */
  public void setShopCode(String val) {
    this.shopCode = val;
  }

  /**
   * SKUコードを設定します
   *
   * @param  val SKUコード
   */
  public void setSkuCode(String val) {
    this.skuCode = val;
  }

  /**
   * 商品単価を設定します
   *
   * @param  val 商品単価
   */
  public void setUnitPrice(BigDecimal val) {
    this.unitPrice = val;
  }

  /**
   * 特別価格を設定します
   *
   * @param  val 特別価格
   */
  public void setDiscountPrice(BigDecimal val) {
    this.discountPrice = val;
  }

  /**
   * 値引額を設定します
   *
   * @param  val 値引額
   */
  public void setDiscountAmount(BigDecimal val) {
    this.discountAmount = val;
  }

  /**
   * 販売価格を設定します
   *
   * @param  val 販売価格
   */
  public void setRetailPrice(BigDecimal val) {
    this.retailPrice = val;
  }

  /**
   * 販売時消費税額を設定します
   *
   * @param  val 販売時消費税額
   */
  public void setRetailTax(BigDecimal val) {
    this.retailTax = val;
  }

  /**
   * 購入商品数を設定します
   *
   * @param  val 購入商品数
   */
  public void setPurchasingAmount(Long val) {
    this.purchasingAmount = val;
  }

  /**
   * ギフトコードを設定します
   *
   * @param  val ギフトコード
   */
  public void setGiftCode(String val) {
    this.giftCode = val;
  }

  /**
   * ギフト名称を設定します
   *
   * @param  val ギフト名称
   */
  public void setGiftName(String val) {
    this.giftName = val;
  }

  /**
   * ギフト価格を設定します
   *
   * @param  val ギフト価格
   */
  public void setGiftPrice(BigDecimal val) {
    this.giftPrice = val;
  }

  /**
   * ギフト消費税率を設定します
   *
   * @param  val ギフト消費税率
   */
  public void setGiftTaxRate(Long val) {
    this.giftTaxRate = val;
  }

  /**
   * ギフト消費税額を設定します
   *
   * @param  val ギフト消費税額
   */
  public void setGiftTax(BigDecimal val) {
    this.giftTax = val;
  }

  /**
   * ギフト消費税区分を設定します
   *
   * @param  val ギフト消費税区分
   */
  public void setGiftTaxType(Long val) {
    this.giftTaxType = val;
  }

  /**
   * データ行IDを設定します
   *
   * @param  val データ行ID
   */
  public void setOrmRowid(Long val) {
    this.ormRowid = val;
  }

  /**
   * 作成ユーザを設定します
   *
   * @param  val 作成ユーザ
   */
  public void setCreatedUser(String val) {
    this.createdUser = val;
  }

  /**
   * 作成日時を設定します
   *
   * @param  val 作成日時
   */
  public void setCreatedDatetime(Date val) {
    this.createdDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 更新ユーザを設定します
   *
   * @param  val 更新ユーザ
   */
  public void setUpdatedUser(String val) {
    this.updatedUser = val;
  }

  /**
   * 更新日時を設定します
   *
   * @param  val 更新日時
   */
  public void setUpdatedDatetime(Date val) {
    this.updatedDatetime = DateUtil.immutableCopy(val);
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

  
  public String getCampaignCode() {
    return campaignCode;
  }

  
  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }

  
  public String getCampaignName() {
    return campaignName;
  }

  
  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  
  public Long getDiscountType() {
    return discountType;
  }

  
  public void setDiscountType(Long discountType) {
    this.discountType = discountType;
  }

  
  public BigDecimal getDiscountValue() {
    return discountValue;
  }

  
  public void setDiscountValue(BigDecimal discountValue) {
    this.discountValue = discountValue;
  }

  public Object clone() {
	    try {
	      return super.clone();
	    } catch (CloneNotSupportedException e) {
	      return null;
	    }
	  }
}
