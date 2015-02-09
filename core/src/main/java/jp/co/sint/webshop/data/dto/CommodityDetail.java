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
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「商品詳細(COMMODITY_DETAIL)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class CommodityDetail implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** SKUコード */
  @PrimaryKey(2)
  @Required
  @Length(24)
  @AlphaNum2
  @Metadata(name = "SKUコード", order = 2)
  private String skuCode;

  /** 商品コード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード", order = 3)
  private String commodityCode;

  /** 商品単価 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "商品単価", order = 4)
  private BigDecimal unitPrice;

  /** 特別価格 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "特別価格", order = 5)
  private BigDecimal discountPrice;

  /** 予約価格 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "予約価格", order = 6)
  private BigDecimal reservationPrice;

  /** 改定価格 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "改定価格", order = 7)
  private BigDecimal changeUnitPrice;

  /** JANコード */
  @Length(16)
  @AlphaNum2
  @Metadata(name = "JANコード", order = 8)
  private String janCode;

  /** 表示順 */
  @Length(8)
  @Metadata(name = "表示順", order = 9)
  private Long displayOrder;

  /** 規格詳細1名称 */
  @Length(20)
  @Metadata(name = "規格詳細1名称", order = 10)
  private String standardDetail1Name;

  /** 規格詳細2名称 */
  @Length(20)
  @Metadata(name = "規格詳細2名称", order = 11)
  private String standardDetail2Name;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 12)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 13)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 14)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 15)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 16)
  private Date updatedDatetime;

  /** 商品重量 */
  @Length(14)
  @Metadata(name = "商品重量", order = 17)
  private BigDecimal weight;

  /** 取扱いフラグ */
  @Length(1)
  @Metadata(name = "取扱いフラグ", order = 18)
  private Long useFlg;

  // add by cs_yuli 20120517 start
  /** 規格詳細1名称(英文) */
  @Length(20)
  @Metadata(name = "規格詳細1名称(英文)", order = 19)
  private String standardDetail1NameEn;

  /** 規格詳細1名称(日文) */
  @Length(20)
  @Metadata(name = "規格詳細1名称(日文)", order = 20)
  private String standardDetail1NameJp;

  /** 規格詳細2名称(英文) */
  @Length(20)
  @Metadata(name = "規格詳細2名称(英文)", order = 19)
  private String standardDetail2NameEn;

  /** 規格詳細2名称(日文) */
  @Length(20)
  @Metadata(name = "規格詳細2名称(日文)", order = 20)
  private String standardDetail2NameJp;

  // add by cs_yuli 20120517 end

  @Length(8)
  @Metadata(name = "入り数", order = 21)
  private Long innerQuantity;

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
   * @return the weight
   */
  public BigDecimal getWeight() {
    return weight;
  }

  /**
   * @param weight
   *          the weight to set
   */
  public void setWeight(BigDecimal weight) {
    this.weight = weight;
  }

  /**
   * @return the useFlg
   */
  public Long getUseFlg() {
    return useFlg;
  }

  /**
   * @param useFlg
   *          the useFlg to set
   */
  public void setUseFlg(Long useFlg) {
    this.useFlg = useFlg;
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
   * 予約価格を取得します
   * 
   * @return 予約価格
   */
  public BigDecimal getReservationPrice() {
    return this.reservationPrice;
  }

  /**
   * 改定価格を取得します
   * 
   * @return 改定価格
   */
  public BigDecimal getChangeUnitPrice() {
    return this.changeUnitPrice;
  }

  /**
   * JANコードを取得します
   * 
   * @return JANコード
   */
  public String getJanCode() {
    return this.janCode;
  }

  /**
   * 表示順を取得します
   * 
   * @return 表示順
   */
  public Long getDisplayOrder() {
    return this.displayOrder;
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
   * 商品単価を設定します
   * 
   * @param val
   *          商品単価
   */
  public void setUnitPrice(BigDecimal val) {
    this.unitPrice = val;
  }

  /**
   * 特別価格を設定します
   * 
   * @param val
   *          特別価格
   */
  public void setDiscountPrice(BigDecimal val) {
    this.discountPrice = val;
  }

  /**
   * 予約価格を設定します
   * 
   * @param val
   *          予約価格
   */
  public void setReservationPrice(BigDecimal val) {
    this.reservationPrice = val;
  }

  /**
   * 改定価格を設定します
   * 
   * @param val
   *          改定価格
   */
  public void setChangeUnitPrice(BigDecimal val) {
    this.changeUnitPrice = val;
  }

  /**
   * JANコードを設定します
   * 
   * @param val
   *          JANコード
   */
  public void setJanCode(String val) {
    this.janCode = val;
  }

  /**
   * 表示順を設定します
   * 
   * @param val
   *          表示順
   */
  public void setDisplayOrder(Long val) {
    this.displayOrder = val;
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

  /**
   * @param standardDetail1NameEn
   *          the standardDetail1NameEn to set
   */
  public void setStandardDetail1NameEn(String standardDetail1NameEn) {
    this.standardDetail1NameEn = standardDetail1NameEn;
  }

  /**
   * @return the standardDetail1NameEn
   */
  public String getStandardDetail1NameEn() {
    return standardDetail1NameEn;
  }

  /**
   * @param standardDetail2NameJp
   *          the standardDetail2NameJp to set
   */
  public void setStandardDetail2NameJp(String standardDetail2NameJp) {
    this.standardDetail2NameJp = standardDetail2NameJp;
  }

  /**
   * @return the standardDetail2NameJp
   */
  public String getStandardDetail2NameJp() {
    return standardDetail2NameJp;
  }

  /**
   * @param standardDetail1NameJp
   *          the standardDetail1NameJp to set
   */
  public void setStandardDetail1NameJp(String standardDetail1NameJp) {
    this.standardDetail1NameJp = standardDetail1NameJp;
  }

  /**
   * @return the standardDetail1NameJp
   */
  public String getStandardDetail1NameJp() {
    return standardDetail1NameJp;
  }

  /**
   * @param standardDetail2NameEn
   *          the standardDetail2NameEn to set
   */
  public void setStandardDetail2NameEn(String standardDetail2NameEn) {
    this.standardDetail2NameEn = standardDetail2NameEn;
  }

  /**
   * @return the standardDetail2NameEn
   */
  public String getStandardDetail2NameEn() {
    return standardDetail2NameEn;
  }

}
