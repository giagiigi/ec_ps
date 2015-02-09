
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
 * 「发货套餐明细(SHIPPING_DETAIL_COMPOSITION)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author OB.
 *
 */
public class ShippingDetailComposition implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 发货编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "发货编号", order = 1)
  private String shippingNo;

  /** 发货明细编号 */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "发货明细编号", order = 2)
  private Long shippingDetailNo;

  /** 组合明细编号 */
  @PrimaryKey(3)
  @Required
  @Length(16)
  @Metadata(name = "组合明细编号", order = 3)
  private Long compositionNo;

  /** 店铺编号 */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "店铺编号", order = 4)
  private String shopCode;

  /** 套餐商品编号 */
  @Required
  @Length(20)
  @Metadata(name = "套餐商品编号", order = 5)
  private String parentCommodityCode;

  /** 套餐商品SKU编号 */
  @Required
  @Length(24)
  @Metadata(name = "套餐商品SKU编号", order = 6)
  private String parentSkuCode;

  /** 套餐明细商品编号 */
  @Required
  @Length(20)
  @Metadata(name = "套餐明细商品编号", order = 7)
  private String childCommodityCode;

  /** 套餐明细商品SKU编号 */
  @Required
  @Length(24)
  @Metadata(name = "套餐明细商品SKU编号", order = 8)
  private String childSkuCode;

  /** 商品名称 */
  @Length(50)
  @Metadata(name = "商品名称", order = 9)
  private String commodityName;

  /** 规格名称1 */
  @Length(20)
  @Metadata(name = "规格名称", order = 10)
  private String standardDetail1Name;

  /** 规格名称2 */
  @Length(20)
  @Metadata(name = "规格名称2", order = 11)
  private String standardDetail2Name;

  /** 商品单价 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "商品单价", order = 12)
  private BigDecimal unitPrice;

  /** 折扣价格 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "折扣价格", order = 13)
  private BigDecimal discountAmount;

  /** 销售价格 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "销售价格", order = 14)
  private BigDecimal retailPrice;

  /** 消费税 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "消费税", order = 15)
  private BigDecimal retailTax;

  /** 消费税率 */
  @Length(3)
  @Percentage
  @Metadata(name = "消费税率", order = 16)
  private Long commodityTaxRate;

  /** 商品消费税 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "商品消费税", order = 17)
  private BigDecimal commodityTax;

  /** 商品消费税区分 */
  @Required
  @Length(1)
  @Domain(TaxType.class)
  @Metadata(name = "商品消费税区分", order = 18)
  private Long commodityTaxType;
  
  /** 商品重量 */
  @Required
  @Precision(precision = 8, scale = 3)
  @Metadata(name = "商品重量", order = 19)
  private BigDecimal commodityWeight;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 20)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 21)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 22)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 23)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 24)
  private Date updatedDatetime;
  
  @Metadata(name = "组合商品总数量", order = 25)
  private Long purchasingAmount;

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
   * 構成品項番を取得します
   *
   * @return 構成品項番
   */
  public Long getCompositionNo() {
    return this.compositionNo;
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
   * 親商品コードを取得します
   *
   * @return 親商品コード
   */
  public String getParentCommodityCode() {
    return this.parentCommodityCode;
  }

  /**
   * 親SKUコードを取得します
   *
   * @return 親SKUコード
   */
  public String getParentSkuCode() {
    return this.parentSkuCode;
  }

  /**
   * 子商品コードを取得します
   *
   * @return 子商品コード
   */
  public String getChildCommodityCode() {
    return this.childCommodityCode;
  }

  /**
   * 子SKUコードを取得します
   *
   * @return 子SKUコード
   */
  public String getChildSkuCode() {
    return this.childSkuCode;
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
   * 商品単価を取得します
   *
   * @return 商品単価
   */
  public BigDecimal getUnitPrice() {
    return this.unitPrice;
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
   * 構成品項番を設定します
   *
   * @param  val 構成品項番
   */
  public void setCompositionNo(Long val) {
    this.compositionNo = val;
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
   * 親商品コードを設定します
   *
   * @param  val 親商品コード
   */
  public void setParentCommodityCode(String val) {
    this.parentCommodityCode = val;
  }

  /**
   * 親SKUコードを設定します
   *
   * @param  val 親SKUコード
   */
  public void setParentSkuCode(String val) {
    this.parentSkuCode = val;
  }

  /**
   * 子商品コードを設定します
   *
   * @param  val 子商品コード
   */
  public void setChildCommodityCode(String val) {
    this.childCommodityCode = val;
  }

  /**
   * 子SKUコードを設定します
   *
   * @param  val 子SKUコード
   */
  public void setChildSkuCode(String val) {
    this.childSkuCode = val;
  }

  /**
   * 商品名称を設定します
   *
   * @param  val 商品名称
   */
  public void setCommodityName(String val) {
    this.commodityName = val;
  }

  /**
   * 規格詳細1名称を設定します
   *
   * @param  val 規格詳細1名称
   */
  public void setStandardDetail1Name(String val) {
    this.standardDetail1Name = val;
  }

  /**
   * 規格詳細2名称を設定します
   *
   * @param  val 規格詳細2名称
   */
  public void setStandardDetail2Name(String val) {
    this.standardDetail2Name = val;
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
   * 商品消費税率を設定します
   *
   * @param  val 商品消費税率
   */
  public void setCommodityTaxRate(Long val) {
    this.commodityTaxRate = val;
  }

  /**
   * 商品消費税額を設定します
   *
   * @param  val 商品消費税額
   */
  public void setCommodityTax(BigDecimal val) {
    this.commodityTax = val;
  }

  /**
   * 商品消費税区分を設定します
   *
   * @param  val 商品消費税区分
   */
  public void setCommodityTaxType(Long val) {
    this.commodityTaxType = val;
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

  /**
   * @return the commodityWeight
   */
  public BigDecimal getCommodityWeight() {
    return commodityWeight;
  }

  /**
   * @param commodityWeight the commodityWeight to set
   */
  public void setCommodityWeight(BigDecimal commodityWeight) {
    this.commodityWeight = commodityWeight;
  }

  
  /**
   * @return the purchasingAmount
   */
  public Long getPurchasingAmount() {
    return purchasingAmount;
  }

  
  /**
   * @param purchasingAmount the purchasingAmount to set
   */
  public void setPurchasingAmount(Long purchasingAmount) {
    this.purchasingAmount = purchasingAmount;
  }
}
