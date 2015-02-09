package jp.co.sint.webshop.service.shop;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 运费查询相关的信息
 * @author 
 */
public class DeliveryRegionChargeInfo implements Serializable{

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;
  
  /** 店铺编号 */
  private String shopCode;

  /** 地域编号 */
  private String prefectureCode;

  /** 配送公司编号 */
  private String deliveryCompanyNo;
  
  /** 地域名称 */
  private String prefectureName;
  
  /** 交换日 */
  private Long leadTime;

  /** 指定金额以下时运费 */
  private BigDecimal deliveryChargeSmall;

  /** 指定金额以上时运费 */
  private BigDecimal deliveryChargeBig;

  /** 重量上限 */
  private BigDecimal deliveryWeight;

  /** 订单金额 */
  private BigDecimal orderAmount;

  /** 续重 */
  private BigDecimal addWeight;

  /** 续费 */
  private BigDecimal addCharge;

  /** 免运费_订单金额 */
  private BigDecimal freeOrderAmount;

  /** 免运费_商品重量 */
  private BigDecimal freeWeight;

  private Date updatedDatetime;

  private Long ormRowid;

  private String createdUser;

  private Date createdDatetime;

  private String updatedUser;


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
public String getPrefectureName() {
	return prefectureName;
}
public void setPrefectureName(String prefectureName) {
	this.prefectureName = prefectureName;
}
public Long getOrmRowid() {
	return ormRowid;
}
public void setOrmRowid(Long ormRowid) {
	this.ormRowid = ormRowid;
}
public String getCreatedUser() {
	return createdUser;
}
public void setCreatedUser(String createdUser) {
	this.createdUser = createdUser;
}
public Date getCreatedDatetime() {
	return createdDatetime;
}
public void setCreatedDatetime(Date createdDatetime) {
	this.createdDatetime = createdDatetime;
}
public String getUpdatedUser() {
	return updatedUser;
}
public void setUpdatedUser(String updatedUser) {
	this.updatedUser = updatedUser;
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
