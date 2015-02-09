//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
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
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「出荷明細(SHIPPING_REALITY_DETAIL)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class ShippingRealityDetail implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 发货明细实际编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "发货明细实际编号", order = 1)
  private Long shippingRealityDetailNo;

  /** 出荷番号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "出荷番号", order = 2)
  private String shippingNo;

  /** 受注番号 */
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "受注番号", order = 3)
  private String orderNo;

  /** ショップコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 4)
  private String shopCode;

  /** SKUコード */
  @Required
  @Length(24)
  @AlphaNum2
  @Metadata(name = "SKUコード", order = 5)
  private String skuCode;

  /** 发货数 */
  @Required
  @Length(8)
  @Metadata(name = "发货数", order = 6)
  private Long shippingAmount;

  /** 宅配便伝票番号 */
  @Length(500)
  @Metadata(name = "宅配便伝票番号", order = 7)
  private String deliverySlipNo;

  /** 出荷日 */
  @Metadata(name = "出荷日", order = 8)
  private Date shippingDate;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 9)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 10)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 11)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 12)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 13)
  private Date updatedDatetime;

  /** 处理标志 */
  @Required
  @Length(1)
  @Metadata(name = "处理标志", order = 14)
  private Long dealFlg;

  /**
   * 发货明细实际编号を取得します
   * 
   * @return 发货明细实际编号
   */
  public Long getShippingRealityDetailNo() {
    return shippingRealityDetailNo;
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
   * 发货数を取得します
   * 
   * @return 发货数
   */
  public Long getShippingAmount() {
    return shippingAmount;
  }

  /**
   * 宅配便伝票番号を取得します
   * 
   * @return 宅配便伝票番号
   */
  public String getDeliverySlipNo() {
    return deliverySlipNo;
  }

  /**
   * 出荷日を取得します
   * 
   * @return 出荷日
   */
  public Date getShippingDate() {
    return DateUtil.immutableCopy(this.shippingDate);
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
   * 发货明细实际编号を設定します
   * 
   * @param val
   *          发货明细实际编号
   */
  public void setShippingRealityDetailNo(Long shippingRealityDetailNo) {
    this.shippingRealityDetailNo = shippingRealityDetailNo;
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
   * 发货数を設定します
   * 
   * @param val
   *          发货数
   */
  public void setShippingAmount(Long shippingAmount) {
    this.shippingAmount = shippingAmount;
  }

  /**
   * 宅配便伝票番号を設定します
   * 
   * @param val
   *          宅配便伝票番号
   */
  public void setDeliverySlipNo(String deliverySlipNo) {
    this.deliverySlipNo = deliverySlipNo;
  }

  /**
   * 出荷日を設定します
   * 
   * @param val
   *          出荷日
   */
  public void setShippingDate(Date val) {
    this.shippingDate = DateUtil.immutableCopy(val);
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

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getShippingNo() {
    return shippingNo;
  }

  public void setShippingNo(String shippingNo) {
    this.shippingNo = shippingNo;
  }

  public Long getDealFlg() {
    return dealFlg;
  }

  public void setDealFlg(Long dealFlg) {
    this.dealFlg = dealFlg;
  }

}
