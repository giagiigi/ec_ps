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
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.Sex;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「顧客嗜好(CUSTOMER_PREFERENCE)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class CustomerPreference implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 顧客嗜好ID */
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "顧客嗜好ID", order = 1)
  private Long customerPreferenceId;

  /** ショップコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 2)
  private String shopCode;

  /** 商品コード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード", order = 3)
  private String commodityCode;

  /** 顧客コード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客コード", order = 4)
  private String customerCode;

  /** 購入年月 */
  @Required
  @Metadata(name = "購入年月", order = 5)
  private Date yearMonthOfPurchase;

  /** 顧客グループコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客グループコード", order = 6)
  private String customerGroupCode;

  /** ショップ名称 */
  @Required
  @Length(30)
  @Metadata(name = "ショップ名称", order = 7)
  private String shopName;

  /** 商品名称 */
  @Required
  @Length(200)
  @Metadata(name = "商品名称", order = 8)
  private String commodityName;

  /** 年齢 */
  @Required
  @Length(3)
  @Metadata(name = "年齢", order = 9)
  private Long age;

  /** 性別 */
  @Required
  @Length(1)
  @Domain(Sex.class)
  @Metadata(name = "性別", order = 10)
  private Long sex;

  /** 注文件数累計 */
  @Required
  @Length(8)
  @Metadata(name = "注文件数累計", order = 11)
  private Long totalOrderCount;

  /** 購入商品数 */
  @Required
  @Length(8)
  @Metadata(name = "購入商品数", order = 12)
  private Long purchasingAmount;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 13)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 14)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 15)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 16)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 17)
  private Date updatedDatetime;

  /**
   * 顧客嗜好IDを取得します
   *
   * @return 顧客嗜好ID
   */
  public Long getCustomerPreferenceId() {
    return this.customerPreferenceId;
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
   * 商品コードを取得します
   *
   * @return 商品コード
   */
  public String getCommodityCode() {
    return this.commodityCode;
  }

  /**
   * 顧客コードを取得します
   *
   * @return 顧客コード
   */
  public String getCustomerCode() {
    return this.customerCode;
  }

  /**
   * 購入年月を取得します
   *
   * @return 購入年月
   */
  public Date getYearMonthOfPurchase() {
    return DateUtil.immutableCopy(this.yearMonthOfPurchase);
  }

  /**
   * 顧客グループコードを取得します
   *
   * @return 顧客グループコード
   */
  public String getCustomerGroupCode() {
    return this.customerGroupCode;
  }

  /**
   * ショップ名称を取得します
   *
   * @return ショップ名称
   */
  public String getShopName() {
    return this.shopName;
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
   * 年齢を取得します
   *
   * @return 年齢
   */
  public Long getAge() {
    return this.age;
  }

  /**
   * 性別を取得します
   *
   * @return 性別
   */
  public Long getSex() {
    return this.sex;
  }

  /**
   * 注文件数累計を取得します
   *
   * @return 注文件数累計
   */
  public Long getTotalOrderCount() {
    return this.totalOrderCount;
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
   * 顧客嗜好IDを設定します
   *
   * @param  val 顧客嗜好ID
   */
  public void setCustomerPreferenceId(Long val) {
    this.customerPreferenceId = val;
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
   * 商品コードを設定します
   *
   * @param  val 商品コード
   */
  public void setCommodityCode(String val) {
    this.commodityCode = val;
  }

  /**
   * 顧客コードを設定します
   *
   * @param  val 顧客コード
   */
  public void setCustomerCode(String val) {
    this.customerCode = val;
  }

  /**
   * 購入年月を設定します
   *
   * @param  val 購入年月
   */
  public void setYearMonthOfPurchase(Date val) {
    this.yearMonthOfPurchase = DateUtil.immutableCopy(val);
  }

  /**
   * 顧客グループコードを設定します
   *
   * @param  val 顧客グループコード
   */
  public void setCustomerGroupCode(String val) {
    this.customerGroupCode = val;
  }

  /**
   * ショップ名称を設定します
   *
   * @param  val ショップ名称
   */
  public void setShopName(String val) {
    this.shopName = val;
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
   * 年齢を設定します
   *
   * @param  val 年齢
   */
  public void setAge(Long val) {
    this.age = val;
  }

  /**
   * 性別を設定します
   *
   * @param  val 性別
   */
  public void setSex(Long val) {
    this.sex = val;
  }

  /**
   * 注文件数累計を設定します
   *
   * @param  val 注文件数累計
   */
  public void setTotalOrderCount(Long val) {
    this.totalOrderCount = val;
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

}
