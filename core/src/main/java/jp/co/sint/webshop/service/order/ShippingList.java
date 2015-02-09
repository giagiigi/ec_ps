package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.utility.DateUtil;

public class ShippingList implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String shippingCheckbox;

  private String shippingNo;

  private String orderNo;

  private String message;

  private String caution;

  private String deliveryRemark;

  private String addressLastName;

  private String addressFirstName;

  private String customerLastName;

  private String customerFirstName;

  private String deliveryTypeNo;

  private String deliveryTypeName;

  private String deliverySlipNo;

  private String shippingStatus;

  private Date orderDatetime;

  private Date shippingDate;

  private Date shippingDirectDate;

  // 20120116 ysy update start
  private String deliveryAppointedDate;
  
  private String deliveryCompanyName;
  
  private String deliveryCompanyNo;
  // 20120118 ysy add
  private String mobileNumber;

  private String phoneNumber;

  private String lastName;
  
  private String firstName;
  
  private String dataTransportStatus;
  
  private String fixedSalesStatus;
  
  private String orderStatus;
  
  private String shopCode;
  
  private String ecTmallFlg;
  
  // 20120116 ysy update end

  private Date arrivalDate;

  private String arrivalTimeStart;

  private String arrivalTimeEnd;

  private Long returnItemType;

  private Date updatedDatetime;

  private String deliverySlipNo1;
  
  private String tmallTid;
  
  private String orderFlg;

/**
 * @return the deliverySlipNo1
 */
public String getDeliverySlipNo1() {
	return deliverySlipNo1;
}

/**
 * @param deliverySlipNo1 the deliverySlipNo1 to set
 */
public void setDeliverySlipNo1(String deliverySlipNo1) {
	this.deliverySlipNo1 = deliverySlipNo1;
}

/**
 * @return the ecTmallFlg
 */
public String getEcTmallFlg() {
	return ecTmallFlg;
}

/**
 * @param ecTmallFlg the ecTmallFlg to set
 */
public void setEcTmallFlg(String ecTmallFlg) {
	this.ecTmallFlg = ecTmallFlg;
}

/**
 * @return the shopCode
 */
public String getShopCode() {
	return shopCode;
}

/**
 * @param shopCode the shopCode to set
 */
public void setShopCode(String shopCode) {
	this.shopCode = shopCode;
}

/**
 * @return the mobileNumber
 */
public String getMobileNumber() {
	return mobileNumber;
}

/**
 * @param mobileNumber the mobileNumber to set
 */
public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
}

/**
 * @return the phoneNumber
 */
public String getPhoneNumber() {
	return phoneNumber;
}

/**
 * @param phoneNumber the phoneNumber to set
 */
public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}

/**
 * @return the lastName
 */
public String getLastName() {
	return lastName;
}

/**
 * @param lastName the lastName to set
 */
public void setLastName(String lastName) {
	this.lastName = lastName;
}

/**
 * @return the firstName
 */
public String getFirstName() {
	return firstName;
}

/**
 * @param firstName the firstName to set
 */
public void setFirstName(String firstName) {
	this.firstName = firstName;
}

/**
 * @return the dataTransportStatus
 */
public String getDataTransportStatus() {
	return dataTransportStatus;
}

/**
 * @param dataTransportStatus the dataTransportStatus to set
 */
public void setDataTransportStatus(String dataTransportStatus) {
	this.dataTransportStatus = dataTransportStatus;
}

/**
 * @return the fixedSalesStatus
 */
public String getFixedSalesStatus() {
	return fixedSalesStatus;
}

/**
 * @param fixedSalesStatus the fixedSalesStatus to set
 */
public void setFixedSalesStatus(String fixedSalesStatus) {
	this.fixedSalesStatus = fixedSalesStatus;
}

/**
 * @return the orderStatus
 */
public String getOrderStatus() {
	return orderStatus;
}

/**
 * @param orderStatus the orderStatus to set
 */
public void setOrderStatus(String orderStatus) {
	this.orderStatus = orderStatus;
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

/**
 * @return the deliveryCompanyName
 */
public String getDeliveryCompanyName() {
	return deliveryCompanyName;
}

/**
 * @param deliveryCompanyName the deliveryCompanyName to set
 */
public void setDeliveryCompanyName(String deliveryCompanyName) {
	this.deliveryCompanyName = deliveryCompanyName;
}

public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(updatedDatetime);
  }

  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * @return the addressFirstName
   */
  public String getAddressFirstName() {
    return addressFirstName;
  }

  /**
   * @param addressFirstName
   *          the addressFirstName to set
   */
  public void setAddressFirstName(String addressFirstName) {
    this.addressFirstName = addressFirstName;
  }

  /**
   * @return the addressLastName
   */
  public String getAddressLastName() {
    return addressLastName;
  }

  /**
   * @param addressLastName
   *          the addressLastName to set
   */
  public void setAddressLastName(String addressLastName) {
    this.addressLastName = addressLastName;
  }

  /**
   * @return the arrivalDate
   */
  public Date getArrivalDate() {
    return DateUtil.immutableCopy(arrivalDate);
  }

  /**
   * @param arrivalDate
   *          the arrivalDate to set
   */
  public void setArrivalDate(Date arrivalDate) {
    this.arrivalDate = DateUtil.immutableCopy(arrivalDate);
  }

  /**
   * @return the arrivalTimeEnd
   */
  public String getArrivalTimeEnd() {
    return arrivalTimeEnd;
  }

  /**
   * @param arrivalTimeEnd
   *          the arrivalTimeEnd to set
   */
  public void setArrivalTimeEnd(String arrivalTimeEnd) {
    this.arrivalTimeEnd = arrivalTimeEnd;
  }

  /**
   * @return the arrivalTimeStart
   */
  public String getArrivalTimeStart() {
    return arrivalTimeStart;
  }

  /**
   * @param arrivalTimeStart
   *          the arrivalTimeStart to set
   */
  public void setArrivalTimeStart(String arrivalTimeStart) {
    this.arrivalTimeStart = arrivalTimeStart;
  }

  /**
   * @return the caution
   */
  public String getCaution() {
    return caution;
  }

  /**
   * @param caution
   *          the caution to set
   */
  public void setCaution(String caution) {
    this.caution = caution;
  }

  /**
   * @return the customerFirstName
   */
  public String getCustomerFirstName() {
    return customerFirstName;
  }

  /**
   * @param customerFirstName
   *          the customerFirstName to set
   */
  public void setCustomerFirstName(String customerFirstName) {
    this.customerFirstName = customerFirstName;
  }

  /**
   * @return the customerLastName
   */
  public String getCustomerLastName() {
    return customerLastName;
  }

  /**
   * @param customerLastName
   *          the customerLastName to set
   */
  public void setCustomerLastName(String customerLastName) {
    this.customerLastName = customerLastName;
  }

  /**
   * @return the deliverySlipNo
   */
  public String getDeliverySlipNo() {
    return deliverySlipNo;
  }

  /**
   * @param deliverySlipNo
   *          the deliverySlipNo to set
   */
  public void setDeliverySlipNo(String deliverySlipNo) {
    this.deliverySlipNo = deliverySlipNo;
  }

  /**
   * @return the deliveryTypeName
   */
  public String getDeliveryTypeName() {
    return deliveryTypeName;
  }

  /**
   * @param deliveryTypeName
   *          the deliveryTypeName to set
   */
  public void setDeliveryTypeName(String deliveryTypeName) {
    this.deliveryTypeName = deliveryTypeName;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message
   *          the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return the orderDatetime
   */
  public Date getOrderDatetime() {
    return DateUtil.immutableCopy(orderDatetime);
  }

  /**
   * @param orderDatetime
   *          the orderDatetime to set
   */
  public void setOrderDatetime(Date orderDatetime) {
    this.orderDatetime = DateUtil.immutableCopy(orderDatetime);
  }

  /**
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  /**
   * @param orderNo
   *          the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  /**
   * @return the returnItemType
   */
  public Long getReturnItemType() {
    return returnItemType;
  }

  /**
   * @param returnItemType
   *          the returnItemType to set
   */
  public void setReturnItemType(Long returnItemType) {
    this.returnItemType = returnItemType;
  }

  /**
   * @return the shippingCheckbox
   */
  public String getShippingCheckbox() {
    return shippingCheckbox;
  }

  /**
   * @param shippingCheckbox
   *          the shippingCheckbox to set
   */
  public void setShippingCheckbox(String shippingCheckbox) {
    this.shippingCheckbox = shippingCheckbox;
  }

  /**
   * @return the shippingDate
   */
  public Date getShippingDate() {
    return DateUtil.immutableCopy(shippingDate);
  }

  /**
   * @param shippingDate
   *          the shippingDate to set
   */
  public void setShippingDate(Date shippingDate) {
    this.shippingDate = DateUtil.immutableCopy(shippingDate);
  }

  /**
   * @return the shippingDirectDate
   */
  public Date getShippingDirectDate() {
    return DateUtil.immutableCopy(shippingDirectDate);
  }

  /**
   * @param shippingDirectDate
   *          the shippingDirectDate to set
   */
  public void setShippingDirectDate(Date shippingDirectDate) {
    this.shippingDirectDate = DateUtil.immutableCopy(shippingDirectDate);
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

  /**
   * @return the shippingStatus
   */
  public String getShippingStatus() {
    return shippingStatus;
  }

  /**
   * @param shippingStatus
   *          the shippingStatus to set
   */
  public void setShippingStatus(String shippingStatus) {
    this.shippingStatus = shippingStatus;
  }

  /**
   * @return the deliveryRemark
   */
  public String getDeliveryRemark() {
    return deliveryRemark;
  }

  /**
   * @param deliveryRemark
   *          the deliveryRemark to set
   */
  public void setDeliveryRemark(String deliveryRemark) {
    this.deliveryRemark = deliveryRemark;
  }

  public String getDeliveryTypeNo() {
    return deliveryTypeNo;
  }

  public void setDeliveryTypeNo(String deliveryTypeNo) {
    this.deliveryTypeNo = deliveryTypeNo;
  }

/**
 * @return the deliveryAppointedDate
 */
public String getDeliveryAppointedDate() {
	return deliveryAppointedDate;
}

/**
 * @param deliveryAppointedDate the deliveryAppointedDate to set
 */
public void setDeliveryAppointedDate(String deliveryAppointedDate) {
	this.deliveryAppointedDate = deliveryAppointedDate;
}


/**
 * @return the tmallTid
 */
public String getTmallTid() {
  return tmallTid;
}


/**
 * @param tmallTid the tmallTid to set
 */
public void setTmallTid(String tmallTid) {
  this.tmallTid = tmallTid;
}


/**
 * @return the orderFlg
 */
public String getOrderFlg() {
  return orderFlg;
}


/**
 * @param orderFlg the orderFlg to set
 */
public void setOrderFlg(String orderFlg) {
  this.orderFlg = orderFlg;
}

}
