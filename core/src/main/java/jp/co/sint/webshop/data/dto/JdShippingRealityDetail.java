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
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;

/**
 * 「タグ(JdCouponDetail)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class JdShippingRealityDetail implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 发货明细实际编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "发货明细实际编号", order = 1)
  private String shippingRealityDetailNo;

  /** 发货编号已D开头 */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @Metadata(name = "发货编号已D开头", order = 2)
  private String shippingNo;

  /** 订单编号已D开头 */
  @Required
  @Length(16)
  @Metadata(name = " 订单编号已D开头", order = 3)
  private String orderNo;

  /** 店铺编号 */
  @Required
  @Length(16)
  @Metadata(name = " 店铺编号", order = 4)
  private String shopCode;

  /** 商品编号 */
  @Required
  @Length(24)
  @Metadata(name = " 商品编号", order = 5)
  private String skuCode;

  /** 发货数 */
  @Required
  @Length(8)
  @Metadata(name = "发货数", order = 6)
  private Long shippingAmount;

  /** 配送快递单号 */
  @Length(500)
  @Metadata(name = "配送快递单号", order = 7)
  private String deliverySlipNo;

  /** 发货日期 */
  @Metadata(name = "发货日期", order = 8)
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

  /** 处理标志：0为未处理，1为已处理 */
  @Length(1)
  @Metadata(name = "处理标志", order = 14)
  private Long dealFlg;

  /**
   * @return the shippingRealityDetailNo
   */
  public String getShippingRealityDetailNo() {
    return shippingRealityDetailNo;
  }

  /**
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @return the skuCode
   */
  public String getSkuCode() {
    return skuCode;
  }

  /**
   * @return the shippingAmount
   */
  public Long getShippingAmount() {
    return shippingAmount;
  }

  /**
   * @return the deliverySlipNo
   */
  public String getDeliverySlipNo() {
    return deliverySlipNo;
  }

  /**
   * @return the shippingDate
   */
  public Date getShippingDate() {
    return shippingDate;
  }

  /**
   * @return the ormRowid
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  /**
   * @return the createdUser
   */
  public String getCreatedUser() {
    return createdUser;
  }

  /**
   * @return the createdDatetime
   */
  public Date getCreatedDatetime() {
    return createdDatetime;
  }

  /**
   * @return the updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * @return the updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return updatedDatetime;
  }

  /**
   * @param shippingRealityDetailNo
   *          the shippingRealityDetailNo to set
   */
  public void setShippingRealityDetailNo(String shippingRealityDetailNo) {
    this.shippingRealityDetailNo = shippingRealityDetailNo;
  }

  /**
   * @param orderNo
   *          the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  /**
   * @param shopCode
   *          the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @param skuCode
   *          the skuCode to set
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  /**
   * @param shippingAmount
   *          the shippingAmount to set
   */
  public void setShippingAmount(Long shippingAmount) {
    this.shippingAmount = shippingAmount;
  }

  /**
   * @param deliverySlipNo
   *          the deliverySlipNo to set
   */
  public void setDeliverySlipNo(String deliverySlipNo) {
    this.deliverySlipNo = deliverySlipNo;
  }

  /**
   * @param shippingDate
   *          the shippingDate to set
   */
  public void setShippingDate(Date shippingDate) {
    this.shippingDate = shippingDate;
  }

  /**
   * @param ormRowid
   *          the ormRowid to set
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * @param createdUser
   *          the createdUser to set
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * @param createdDatetime
   *          the createdDatetime to set
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  /**
   * @param updatedUser
   *          the updatedUser to set
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * @param updatedDatetime
   *          the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  /**
   * @return the deal_flg
   */
  public Long getDealFlg() {
    return dealFlg;
  }

  /**
   * @param deal_flg
   *          the deal_flg to set
   */
  public void setDealFlg(Long dealFlg) {
    this.dealFlg = dealFlg;
  }

  /**
   * @return the shippingNo
   */
  public String getShippingNo() {
    return shippingNo;
  }

  /**
   * @param shippingNo
   *          the shippingNo to set
   */
  public void setShippingNo(String shippingNo) {
    this.shippingNo = shippingNo;
  }

}
