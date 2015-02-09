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
 * 「商品明細(c_commodity_detail)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class CCommodityDetail implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  @PrimaryKey(1)
  @Required
  @Length(24)
  @Metadata(name = "SKUコード", order = 2)
  private String skuCode;

  @Required
  @Length(50)
  @Metadata(name = "SKU名称", order = 3)
  private String skuName;

  @Required
  @Length(16)
  @Metadata(name = "商品コード", order = 4)
  private String commodityCode;

  @Required
  @Metadata(name = "定価フラグ0：非定価", order = 5)
  private Long fixedPriceFlag;

  @Length(10)
  @Metadata(name = "希望小売価格", order = 6)
  private BigDecimal suggestePrice;

  @Required
  @Length(13)
  @Metadata(name = "仕入価格", order = 7)
  private BigDecimal purchasePrice;

  /** ビジター数 */
  @Length(13)
  @Metadata(name = "EC商品単価", order = 8)
  private BigDecimal unitPrice;

  /** 購入者数 */
  @Length(13)
  @Metadata(name = "EC特別価格", order = 9)
  private BigDecimal discountPrice;

  @Length(20)
  @Metadata(name = "規格1値のID(=TMALL属性値ID)", order = 10)
  private String standardDetail1Id;

  @Length(20)
  @Metadata(name = "規格1値の文字列(値のIDなければ、これを利用)", order = 11)
  private String standardDetail1Name;

  @Length(20)
  @Metadata(name = "規格2値のID(=TMALL属性値ID)", order = 12)
  private String standardDetail2Id;

  @Length(20)
  @Metadata(name = "規格2値の文字列(値のIDなければ、これを利用)", order = 13)
  private String standardDetail2Name;

  @Length(12)
  @Metadata(name = "商品重量(単位はKG)、未設定の場合、商品HEADの重量を利用。", order = 14)
  private BigDecimal weight;

  @Length(8)
  @Metadata(name = "容量", order = 15)
  private BigDecimal volume;

  @Length(10)
  @Metadata(name = "容量単位", order = 16)
  private String volumeUnit;

  @Length(1)
  @Metadata(name = "取扱いフラグ", order = 17)
  private Long useFlg;

  @Length(8)
  @Metadata(name = "最小仕入数", order = 18)
  private Long minimumOrder;

  @Length(8)
  @Metadata(name = "最大仕入数", order = 19)
  private Long maximumOrder;

  @Length(8)
  @Metadata(name = "最小単位の仕入数", order = 20)
  private Long orderMultiple;

  @Length(8)
  @Metadata(name = "在庫警告日数", order = 21)
  private Long stockWarning;

  @Length(16)
  @Metadata(name = "TMALLのSKUのID", order = 22)
  private Long tmallSkuId;

  @Length(13)
  @Metadata(name = "TMALLの商品単価", order = 23)
  private BigDecimal tmallUnitPrice;

  @Length(13)
  @Metadata(name = "TMALLの特別価格", order = 24)
  private BigDecimal tmallDiscountPrice;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 25)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 26)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 27)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 28)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 29)
  private Date updatedDatetime;

  @Length(12)
  @Metadata(name = "下限売価", order = 30)
  private BigDecimal minPrice;

  @Length(12)
  @Metadata(name = "縦(単位はCM)", order = 31)
  private BigDecimal length;

  @Length(12)
  @Metadata(name = "横(単位はCM)", order = 32)
  private BigDecimal width;

  @Length(12)
  @Metadata(name = "高さ(単位はCM)", order = 33)
  private BigDecimal height;

  @Length(12)
  @Metadata(name = "WEB表示単価単位容量", order = 34)
  private BigDecimal volumeUnitForPrice;

  @Length(8)
  @Metadata(name = "入り数", order = 35)
  private Long innerQuantity;

  @Length(10)
  @Metadata(name = "WEB表示単価単位入り数", order = 36)
  private Long innerUnitForPrice;

  @Length(10)
  @Metadata(name = "取扱いフラグ(TMALL)", order = 37)
  private Long tmallUseFlg;

  @Length(10)
  @Metadata(name = "入り数単位", order = 38)
  private String innerQuantityUnit;

  @Required
  @Length(4)
  @Metadata(name = "税率区分", order = 39)
  private String taxClass;

  @Length(20)
  @Metadata(name = "規格1値の文字列(値のIDなければ、これを利用)英文", order = 40)
  private String standardDetail1NameEn;

  @Length(20)
  @Metadata(name = "規格1値の文字列(値のIDなければ、これを利用)日文", order = 41)
  private String standardDetail1NameJp;

  @Length(20)
  @Metadata(name = "規格1値の文字列(値のIDなければ、これを利用)英文", order = 42)
  private String standardDetail2NameEn;

  @Length(20)
  @Metadata(name = "規格1値の文字列(値のIDなければ、これを利用)日文", order = 43)
  private String standardDetail2NameJp;
  
  @Length(8)
  @Metadata(name = "乡规", order = 44)
  private Long unitBox;
  
  @Length(1)
  @Metadata(name = "JD使用标志", order = 45)
  private Long jdUseFlg;

  @Length(13)
  @Metadata(name = "平均计算成本", order = 46)
  private BigDecimal averageCost;


  public String getStandardDetail1NameEn() {
    return standardDetail1NameEn;
  }

  public void setStandardDetail1NameEn(String standardDetail1NameEn) {
    this.standardDetail1NameEn = standardDetail1NameEn;
  }

  public String getStandardDetail1NameJp() {
    return standardDetail1NameJp;
  }

  public void setStandardDetail1NameJp(String standardDetail1NameJp) {
    this.standardDetail1NameJp = standardDetail1NameJp;
  }

  public String getStandardDetail2NameEn() {
    return standardDetail2NameEn;
  }

  public void setStandardDetail2NameEn(String standardDetail2NameEn) {
    this.standardDetail2NameEn = standardDetail2NameEn;
  }

  public String getStandardDetail2NameJp() {
    return standardDetail2NameJp;
  }

  public void setStandardDetail2NameJp(String standardDetail2NameJp) {
    this.standardDetail2NameJp = standardDetail2NameJp;
  }

  /**
   * @return the minPrice
   */
  public BigDecimal getMinPrice() {
    return minPrice;
  }

  /**
   * @param minPrice
   *          the minPrice to set
   */
  public void setMinPrice(BigDecimal minPrice) {
    this.minPrice = minPrice;
  }

  /**
   * @return the length
   */
  public BigDecimal getLength() {
    return length;
  }

  /**
   * @return the taxClass
   */
  public String getTaxClass() {
    return taxClass;
  }

  /**
   * @param taxClass
   *          the taxClass to set
   */
  public void setTaxClass(String taxClass) {
    this.taxClass = taxClass;
  }

  /**
   * @param length
   *          the length to set
   */
  public void setLength(BigDecimal length) {
    this.length = length;
  }

  /**
   * @return the width
   */
  public BigDecimal getWidth() {
    return width;
  }

  /**
   * @param width
   *          the width to set
   */
  public void setWidth(BigDecimal width) {
    this.width = width;
  }

  /**
   * @return the height
   */
  public BigDecimal getHeight() {
    return height;
  }

  /**
   * @param height
   *          the height to set
   */
  public void setHeight(BigDecimal height) {
    this.height = height;
  }

  /**
   * @return the volumeUnitForPrice
   */
  public BigDecimal getVolumeUnitForPrice() {
    return volumeUnitForPrice;
  }

  /**
   * @param volumeUnitForPrice
   *          the volumeUnitForPrice to set
   */
  public void setVolumeUnitForPrice(BigDecimal volumeUnitForPrice) {
    this.volumeUnitForPrice = volumeUnitForPrice;
  }

  /**
   * @return the innerQuantity
   */
  public Long getInnerQuantity() {
    return innerQuantity;
  }

  /**
   * @param innerQuantity
   *          the innerQuantity to set
   */
  public void setInnerQuantity(Long innerQuantity) {
    this.innerQuantity = innerQuantity;
  }

  /**
   * @return the innerUnitForPrice
   */
  public Long getInnerUnitForPrice() {
    return innerUnitForPrice;
  }

  /**
   * @param innerUnitForPrice
   *          the innerUnitForPrice to set
   */
  public void setInnerUnitForPrice(Long innerUnitForPrice) {
    this.innerUnitForPrice = innerUnitForPrice;
  }

  /**
   * @return the tmallUseFlg
   */
  public Long getTmallUseFlg() {
    return tmallUseFlg;
  }

  /**
   * @param tmallUseFlg
   *          the tmallUseFlg to set
   */
  public void setTmallUseFlg(Long tmallUseFlg) {
    this.tmallUseFlg = tmallUseFlg;
  }

  /**
   * @return the innerQuantityUnit
   */
  public String getInnerQuantityUnit() {
    return innerQuantityUnit;
  }

  /**
   * @param innerQuantityUnit
   *          the innerQuantityUnit to set
   */
  public void setInnerQuantityUnit(String innerQuantityUnit) {
    this.innerQuantityUnit = innerQuantityUnit;
  }

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  public String getSkuCode() {
    return skuCode;
  }

  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  public String getSkuName() {
    return skuName;
  }

  public void setSkuName(String skuName) {
    this.skuName = skuName;
  }

  public String getCommodityCode() {
    return commodityCode;
  }

  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @return the fixedPriceFlag
   */
  public Long getFixedPriceFlag() {
    return fixedPriceFlag;
  }

  /**
   * @param fixedPriceFlag
   *          the fixedPriceFlag to set
   */
  public void setFixedPriceFlag(Long fixedPriceFlag) {
    this.fixedPriceFlag = fixedPriceFlag;
  }

  /**
   * @return the suggestePrice
   */
  public BigDecimal getSuggestePrice() {
    return suggestePrice;
  }

  /**
   * @param suggestePrice
   *          the suggestePrice to set
   */
  public void setSuggestePrice(BigDecimal suggestePrice) {
    this.suggestePrice = suggestePrice;
  }

  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }

  public void setPurchasePrice(BigDecimal purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public BigDecimal getDiscountPrice() {
    return discountPrice;
  }

  public void setDiscountPrice(BigDecimal discountPrice) {
    this.discountPrice = discountPrice;
  }

  public BigDecimal getWeight() {
    return weight;
  }

  public void setWeight(BigDecimal weight) {
    this.weight = weight;
  }

  public Long getUseFlg() {
    return useFlg;
  }

  public void setUseFlg(Long useFlg) {
    this.useFlg = useFlg;
  }

  public Long getMinimumOrder() {
    return minimumOrder;
  }

  /**
   * @return the volume
   */
  public BigDecimal getVolume() {
    return volume;
  }

  /**
   * @param volume
   *          the volume to set
   */
  public void setVolume(BigDecimal volume) {
    this.volume = volume;
  }

  /**
   * @return the volumeUnit
   */
  public String getVolumeUnit() {
    return volumeUnit;
  }

  /**
   * @param volumeUnit
   *          the volumeUnit to set
   */
  public void setVolumeUnit(String volumeUnit) {
    this.volumeUnit = volumeUnit;
  }

  public void setMinimumOrder(Long minimumOrder) {
    this.minimumOrder = minimumOrder;
  }

  public Long getMaximumOrder() {
    return maximumOrder;
  }

  public void setMaximumOrder(Long maximumOrder) {
    this.maximumOrder = maximumOrder;
  }

  public Long getOrderMultiple() {
    return orderMultiple;
  }

  public void setOrderMultiple(Long orderMultiple) {
    this.orderMultiple = orderMultiple;
  }

  public Long getStockWarning() {
    return stockWarning;
  }

  public void setStockWarning(Long stockWarning) {
    this.stockWarning = stockWarning;
  }

  public Long getTmallSkuId() {
    return tmallSkuId;
  }

  public void setTmallSkuId(Long tmallSkuId) {
    this.tmallSkuId = tmallSkuId;
  }

  public BigDecimal getTmallUnitPrice() {
    return tmallUnitPrice;
  }

  public void setTmallUnitPrice(BigDecimal tmallUnitPrice) {
    this.tmallUnitPrice = tmallUnitPrice;
  }

  public BigDecimal getTmallDiscountPrice() {
    return tmallDiscountPrice;
  }

  public void setTmallDiscountPrice(BigDecimal tmallDiscountPrice) {
    this.tmallDiscountPrice = tmallDiscountPrice;
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

  public String getStandardDetail1Id() {
    return standardDetail1Id;
  }

  public void setStandardDetail1Id(String standardDetail1Id) {
    this.standardDetail1Id = standardDetail1Id;
  }

  public String getStandardDetail1Name() {
    return standardDetail1Name;
  }

  public void setStandardDetail1Name(String standardDetail1Name) {
    this.standardDetail1Name = standardDetail1Name;
  }

  public String getStandardDetail2Id() {
    return standardDetail2Id;
  }

  public void setStandardDetail2Id(String standardDetail2Id) {
    this.standardDetail2Id = standardDetail2Id;
  }

  public String getStandardDetail2Name() {
    return standardDetail2Name;
  }

  public void setStandardDetail2Name(String standardDetail2Name) {
    this.standardDetail2Name = standardDetail2Name;
  }

  
  /**
   * @return the unitBox
   */
  public Long getUnitBox() {
    return unitBox;
  }

  
  /**
   * @param unitBox the unitBox to set
   */
  public void setUnitBox(Long unitBox) {
    this.unitBox = unitBox;
  }

  /**
   * @return the jdUseFlg
   */
  public Long getJdUseFlg() {
    return jdUseFlg;
  }

  /**
   * @param jdUseFlg the jdUseFlg to set
   */
  public void setJdUseFlg(Long jdUseFlg) {
    this.jdUseFlg = jdUseFlg;
  }

  
  /**
   * @return the averageCost
   */
  public BigDecimal getAverageCost() {
    return averageCost;
  }

  
  /**
   * @param averageCost the averageCost to set
   */
  public void setAverageCost(BigDecimal averageCost) {
    this.averageCost = averageCost;
  }

  
}
