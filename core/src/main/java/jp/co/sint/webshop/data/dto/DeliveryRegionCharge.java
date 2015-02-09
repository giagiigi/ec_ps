package jp.co.sint.webshop.data.dto;

/**
 * 运费表
 * 
 * @author ob.
 * @version 1.0.0
 * 
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;

import jp.co.sint.webshop.data.attribute.Required;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;

/**
 * AbstractDeliveryRegionCharge entity provides the base persistence definition of the DeliveryRegionCharge
 * @author OB
 */

public class DeliveryRegionCharge implements Serializable, WebshopEntity {

  private static final long serialVersionUID = 1L;

  /** 店铺编号 */
  @Required
  @Length(16)
  @Metadata(name = "店铺编号", order = 1)
  private String shopCode;

  /** 地域编号 */
  @Required
  @Digit
  @Metadata(name = "地域编号", order = 3)
  private String prefectureCode;

  /** 交换日 */
  @Required
  @Length(2)
  @Digit
  @Metadata(name = "交换日", order = 4)
  private Long leadTime;

  /** 指定金额以下时运费 */
  @Required
  @Currency
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "指定金额以下时运费", order = 6)
  private BigDecimal deliveryChargeSmall;

  /** 指定金额以上时运费 */
  @Required
  @Currency
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "指定金额以上时运费", order = 7)
  private BigDecimal deliveryChargeBig;

  /** 重量上限 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "重量上限", order = 8)
  private BigDecimal deliveryWeight;

  /** 订单金额 */
  @Required
  @Currency
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "订单金额", order = 9)
  private BigDecimal orderAmount;

  /** 续重 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "续重", order = 10)
  private BigDecimal addWeight;

  /** 续费 */
  @Required
  @Currency
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "续费", order = 11)
  private BigDecimal addCharge;

  /** 免运费_订单金额 */
  @Required
  @Currency
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "免运费_订单金额", order = 12)
  private BigDecimal freeOrderAmount;

  /** 免运费_商品重量 */
  @Required
  @Currency
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "免运费_商品重量", order = 13)
  private BigDecimal freeWeight;

  /** データ行ID */
  @Required
  @Digit
  @Metadata(name = "データ行ID", order = 14)
  private Long ormRowid=DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 15)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Datetime
  @Metadata(name = "作成日時", order = 16)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 17)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Datetime
  @Metadata(name = "更新日時", order = 18)
  private Date updatedDatetime;

  /** 配送公司编号 */
  @Required
  @Length(16)
  @Metadata(name = "配送公司编号", order = 19)
  private String deliveryCompanyNo;

  /**
   * @return 店铺编号
   */
  public String getShopCode() {
    return shopCode;
  }
  /**
   * @param ShopCode 设置店铺编号
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }
  
  /**
   * @return 交换日
   */
  public Long getLeadTime() {
    return leadTime;
  }
  /**
   * @param LeadTime 设置交换日
   */
  public void setLeadTime(Long leadTime) {
    this.leadTime = leadTime;
  }

  /**
   * @return 指定金额以下时运费
   */
  public BigDecimal getDeliveryChargeSmall() {
    return deliveryChargeSmall;
  }
  /**
   * @param DeliveryChargeSmall 设置指定金额以下时运费
   */
  public void setDeliveryChargeSmall(BigDecimal deliveryChargeSmall) {
    this.deliveryChargeSmall = deliveryChargeSmall;
  }

  /**
   * @return 指定金额以上时运费
   */
  public BigDecimal getDeliveryChargeBig() {
    return deliveryChargeBig;
  }
  /**
   * @param DeliveryChargeBig 设置指定金额以上时运费
   */
  public void setDeliveryChargeBig(BigDecimal deliveryChargeBig) {
    this.deliveryChargeBig = deliveryChargeBig;
  }

  /**
   * @return 重量上限
   */
  public BigDecimal getDeliveryWeight() {
    return deliveryWeight;
  }
  /**
   * @param DeliveryWeight 设置重量上限
   */
  public void setDeliveryWeight(BigDecimal deliveryWeight) {
    this.deliveryWeight = deliveryWeight;
  }

  /**
   * @return 订单金额
   */
  public BigDecimal getOrderAmount() {
    return orderAmount;
  }
  /**
   * @param OrderAmount 设置订单金额
   */
  public void setOrderAmount(BigDecimal orderAmount) {
    this.orderAmount = orderAmount;
  }

  /**
   * @return 续重
   */
  public BigDecimal getAddWeight() {
    return addWeight;
  }
  /**
   * @param AddWeight 设置续重
   */
  public void setAddWeight(BigDecimal addWeight) {
    this.addWeight = addWeight;
  }

  /**
   * @return 续费
   */
  public BigDecimal getAddCharge() {
    return addCharge;
  }
  /**
   * @param AddCharge 设置续费
   */
  public void setAddCharge(BigDecimal addCharge) {
    this.addCharge = addCharge;
  }

  /**
   * @return 免运费_订单金额
   */
  public BigDecimal getFreeOrderAmount() {
    return freeOrderAmount;
  }
  /**
   * @param FreeOrderAmount 设置免运费_订单金额
   */
  public void setFreeOrderAmount(BigDecimal freeOrderAmount) {
    this.freeOrderAmount = freeOrderAmount;
  }

  /**
   * @return 免运费_商品重量
   */
  public BigDecimal getFreeWeight() {
    return freeWeight;
  }
  /**
   * @param FreeWeight 设置免运费_商品重量
   */
  public void setFreeWeight(BigDecimal freeWeight) {
    this.freeWeight = freeWeight;
  }

  /**
   * @return データ行ID
   */
  public Long getOrmRowid() {
    return ormRowid;
  }
  /**
   * @param OrmRowid 设置データ行ID
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * @return 作成ユーザ
   */
  public String getCreatedUser() {
    return createdUser;
  }
  /**
   * @param CreatedUser 设置作成ユーザ
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * @return 作成日時
   */
  public Date getCreatedDatetime() {
    return createdDatetime;
  }
  /**
   * @param CreatedDatetime 设置作成日時
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  /**
   * @return 更新ユーザ
   */
  public String getUpdatedUser() {
    return updatedUser;
  }
  /**
   * @param UpdatedUser 设置更新ユーザ
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * @return 更新日時
   */
  public Date getUpdatedDatetime() {
    return updatedDatetime;
  }
  /**
   * @param UpdatedDatetime 设置更新日時
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }
public String getPrefectureCode() {
	return prefectureCode;
}
public void setPrefectureCode(String prefectureCode) {
	this.prefectureCode = prefectureCode;
}

/**
 * @return the deliveryCompanyNo
 */
public String getDeliveryCompanyNo() {
  return deliveryCompanyNo;
}

/**
 * @param deliveryCompanyNo the deliveryCompanyNo to set
 */
public void setDeliveryCompanyNo(String deliveryCompanyNo) {
  this.deliveryCompanyNo = deliveryCompanyNo;
}

}