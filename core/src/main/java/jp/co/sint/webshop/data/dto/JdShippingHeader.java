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
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;

/**
 * 「タグ(JdCouponDetail)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class JdShippingHeader implements Serializable, WebshopEntity,Cloneable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 发货编号D字母开头 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "发货编号D字母开头", order = 1)
  private String shippingNo;

  /** 订单编号 */
  @Required
  @Length(16)
  @Metadata(name = "订单编号", order = 2)
  private String orderNo;

  /** 店铺编号 */
  @Required
  @Length(16)
  @Metadata(name = "店铺编号", order = 3)
  private String shopCode;

  /** 京东订单号 */
  @Length(16)
  @Metadata(name = "京东订单号", order = 4)
  private String customerCode;

  /** address_no */
  @Length(8)
  @Metadata(name = "addressNo", order = 5)
  private Long addressNo;

  /** 收货人姓名 */
  @Required
  @Length(20)
  @Metadata(name = "收货人姓名", order = 6)
  private String addressLastName;

  /** address_first_name */
  @Required
  @Length(20)
  @Metadata(name = "addressFirstName", order = 7)
  private String addressFirstName;

  /** address_last_name_kana */
  @Required
  @Length(40)
  @Metadata(name = "addressLastNameKana", order = 8)
  private String addressLastNameKana;

  /** address_first_name_kana */
  @Required
  @Length(40)
  @Metadata(name = "addressFirstNameKana", order = 9)
  private String addressFirstNameKana;

  /** 邮政编码 */
  @Required
  @Length(7)
  @Metadata(name = "邮政编码", order = 10)
  private String postalCode;

  /** 省份编号 */
  @Required
  @Length(2)
  @Metadata(name = "省份编号", order = 11)
  private String prefectureCode;

  /** 收货人省份编号 */
  @Required
  @Length(50)
  @Metadata(name = "收货人省份编号", order = 12)
  private String address1;

  /** 收货人城市名 */
  @Required
  @Length(50)
  @Metadata(name = "收货人城市名", order = 13)
  private String address2;

  /** 收货人区县名 */
  @Length(50)
  @Metadata(name = "收货人区县名", order = 14)
  private String address3;

  /** 收货人街道地址 */
  @Required
  @Length(100)
  @Metadata(name = "收货人街道地址", order = 15)
  private String address4;

  /** 固定电话 */
  @Length(24)
  @Metadata(name = "固定电话", order = 16)
  private String phoneNumber;

  /** 手机 */
  @Length(11)
  @Metadata(name = "手机", order = 17)
  private String mobileNumber;

  /** delivery_remark */
  @Length(500)
  @Metadata(name = "deliveryRemark", order = 18)
  private String deliveryRemark;

  /** acquired_point */
  @Precision(precision = 12, scale = 2)
  @Metadata(name = "acquiredPoint", order = 19)
  private BigDecimal acquiredPoint;

  /** 快递单号（WMS提供） */
  @Length(500)
  @Metadata(name = "快递单号（WMS提供）", order = 20)
  private String deliverySlipNo;

  /** 运费 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "运费", order = 21)
  private BigDecimal shippingCharge;

  /** shipping_charge_tax_type */
  @Required
  @Length(1)
  @Metadata(name = "shippingChargeTaxType", order = 22)
  private Long shippingChargeTaxType;

  /** shipping_charge_tax_rate */
  @Length(3)
  @Metadata(name = "shippingChargeTaxRate", order = 23)
  private Long shippingChargeTaxRate;

  /** shipping_charge_tax */
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "shippingChargeTax", order = 24)
  private BigDecimal shippingChargeTax;

  /** delivery_type_no */
  @Required
  @Length(8)
  @Metadata(name = "deliveryTypeNo", order = 25)
  private Long deliveryTypeNo;

  /** delivery_type_name */
  @Length(40)
  @Metadata(name = "deliveryTypeName", order = 26)
  private String deliveryTypeName;

  /** delivery_appointed_date */
  @Length(10)
  @Metadata(name = "deliveryAppointedDate", order = 27)
  private String deliveryAppointedDate;

  /** delivery_appointed_time_start */
  @Length(2)
  @Metadata(name = "deliveryAppointedTimeStart", order = 28)
  private Long deliveryAppointedTimeStart;

  /** delivery_appointed_time_end */
  @Length(2)
  @Metadata(name = "deliveryAppointedTimeEnd", order = 29)
  private Long deliveryAppointedTimeEnd;

  /** arrival_date */
  @Metadata(name = "arrivalDate", order = 30)
  private Date arrivalDate;

  /** arrival_time_start */
  @Length(2)
  @Metadata(name = "arrivalTimeStart", order = 31)
  private Long arrivalTimeStart;

  /** arrival_time_end */
  @Length(2)
  @Metadata(name = "arrivalTimeEnd", order = 32)
  private Long arrivalTimeEnd;

  /** fixed_sales_status */
  @Required
  @Length(1)
  @Metadata(name = "fixedSalesStatus", order = 33)
  private Long fixedSalesStatus;

  /** 根据API返回值，判断发货状态WMS 0未发货 1可以发货 2发货中 3发货完毕 */
  @Required
  @Length(1)
  @Metadata(name = "根据API返回值，判断发货状态WMS 0未发货 1可以发货 2发货中 3发货完毕", order = 34)
  private Long shippingStatus;

  /** 发货指示日 */
  @Metadata(name = "发货指示日", order = 35)
  private Date shippingDirectDate;

  /** 发货完毕日 */
  @Metadata(name = "发货完毕日", order = 36)
  private Date shippingDate;

  /** original_shipping_no */
  @Length(16)
  @Metadata(name = "originalShippingNo", order = 37)
  private String originalShippingNo;

  /** return_item_date */
  @Metadata(name = "returnItemDate", order = 38)
  private Date returnItemDate;

  /** return_item_loss_money */
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "returnItemLossMoney", order = 39)
  private BigDecimal returnItemLossMoney;

  /** return_item_type */
  @Length(1)
  @Metadata(name = "returnItemType", order = 40)
  private Long returnItemType;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 41)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 42)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 43)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 44)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 45)
  private Date updatedDatetime;

  /** 城市编号 */
  @Required
  @Length(10)
  @Metadata(name = "城市编号", order = 46)
  private String cityCode;

  /** 配送公司编号 */
  @Required
  @Length(16)
  @Metadata(name = "配送公司编号", order = 47)
  private String deliveryCompanyNo;

  /** 配送公司名称 */
  @Length(50)
  @Metadata(name = "配送公司名称", order = 48)
  private String deliveryCompanyName;

  /** 区县编号 */
  @Length(10)
  @Metadata(name = "区县编号", order = 49)
  private String areaCode;

  /** 京东api发货成功标志 */
  @Length(1)
  @Metadata(name = "京东api发货成功标志", order = 50)
  private Long jdShippingFlg;

  /** 根据API返回值，判断发货状态ERP 0未发货 1可以发货 2发货中 */
  @Length(1)
  @Metadata(name = "根据API返回值，判断发货状态ERP 0未发货 1可以发货 2发货中", order = 51)
  private Long shippingStatusWms;

  /** jd订单拆分，分仓区分*/
  @Length(4)
  @Metadata(name = "分仓区分", order = 52)
  private String isPart;
  
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "折扣金额", order = 53)
  private BigDecimal discountPrice;
  
  /** 子订单订单号*/
  @Length(16)
  @Metadata(name = "自订单订单号", order = 54)
  private String childOrderNo;
  
  /** 子订单订单号*/
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "子订单金额", order = 55)
  private BigDecimal childPaidPrice;
  
  
  /**
   * @return the shippingNo
   */
  public String getShippingNo() {
    return shippingNo;
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
   * @return the customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * @return the addressNo
   */
  public Long getAddressNo() {
    return addressNo;
  }

  /**
   * @return the addressLastName
   */
  public String getAddressLastName() {
    return addressLastName;
  }

  /**
   * @return the addressFirstName
   */
  public String getAddressFirstName() {
    return addressFirstName;
  }

  /**
   * @return the addressLastNameKana
   */
  public String getAddressLastNameKana() {
    return addressLastNameKana;
  }

  /**
   * @return the postalCode
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * @return the prefectureCode
   */
  public String getPrefectureCode() {
    return prefectureCode;
  }

  /**
   * @return the address1
   */
  public String getAddress1() {
    return address1;
  }

  /**
   * @return the address2
   */
  public String getAddress2() {
    return address2;
  }

  /**
   * @return the address3
   */
  public String getAddress3() {
    return address3;
  }

  /**
   * @return the address4
   */
  public String getAddress4() {
    return address4;
  }

  /**
   * @return the phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @return the mobileNumber
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  /**
   * @return the deliveryRemark
   */
  public String getDeliveryRemark() {
    return deliveryRemark;
  }

  /**
   * @return the acquiredPoint
   */
  public BigDecimal getAcquiredPoint() {
    return acquiredPoint;
  }

  /**
   * @return the deliverySlipNo
   */
  public String getDeliverySlipNo() {
    return deliverySlipNo;
  }

  /**
   * @return the shippingCharge
   */
  public BigDecimal getShippingCharge() {
    return shippingCharge;
  }

  /**
   * @return the shippingChargeTaxType
   */
  public Long getShippingChargeTaxType() {
    return shippingChargeTaxType;
  }

  /**
   * @return the shippingChargeTaxRate
   */
  public Long getShippingChargeTaxRate() {
    return shippingChargeTaxRate;
  }

  /**
   * @return the shippingChargeTax
   */
  public BigDecimal getShippingChargeTax() {
    return shippingChargeTax;
  }

  /**
   * @return the deliveryTypeNo
   */
  public Long getDeliveryTypeNo() {
    return deliveryTypeNo;
  }

  /**
   * @return the deliveryTypeName
   */
  public String getDeliveryTypeName() {
    return deliveryTypeName;
  }

  /**
   * @return the deliveryAppointedDate
   */
  public String getDeliveryAppointedDate() {
    return deliveryAppointedDate;
  }

  /**
   * @return the deliveryAppointedTimeStart
   */
  public Long getDeliveryAppointedTimeStart() {
    return deliveryAppointedTimeStart;
  }

  /**
   * @return the deliveryAppointedTimeEnd
   */
  public Long getDeliveryAppointedTimeEnd() {
    return deliveryAppointedTimeEnd;
  }

  /**
   * @return the arrivalDate
   */
  public Date getArrivalDate() {
    return arrivalDate;
  }

  /**
   * @return the arrivalTimeStart
   */
  public Long getArrivalTimeStart() {
    return arrivalTimeStart;
  }

  /**
   * @return the arrivalTimeEnd
   */
  public Long getArrivalTimeEnd() {
    return arrivalTimeEnd;
  }

  /**
   * @return the fixedSalesStatus
   */
  public Long getFixedSalesStatus() {
    return fixedSalesStatus;
  }

  /**
   * @return the shippingStatus
   */
  public Long getShippingStatus() {
    return shippingStatus;
  }

  /**
   * @return the shippingDirectDate
   */
  public Date getShippingDirectDate() {
    return shippingDirectDate;
  }

  /**
   * @return the shippingDate
   */
  public Date getShippingDate() {
    return shippingDate;
  }

  /**
   * @return the returnItemDate
   */
  public Date getReturnItemDate() {
    return returnItemDate;
  }

  /**
   * @return the returnItemLossMoney
   */
  public BigDecimal getReturnItemLossMoney() {
    return returnItemLossMoney;
  }

  /**
   * @return the returnItemType
   */
  public Long getReturnItemType() {
    return returnItemType;
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
   * @return the cityCode
   */
  public String getCityCode() {
    return cityCode;
  }

  /**
   * @return the deliveryCompanyNo
   */
  public String getDeliveryCompanyNo() {
    return deliveryCompanyNo;
  }

  /**
   * @return the deliveryCompanyName
   */
  public String getDeliveryCompanyName() {
    return deliveryCompanyName;
  }

  /**
   * @return the areaCode
   */
  public String getAreaCode() {
    return areaCode;
  }

  /**
   * @return the jdShippingFlg
   */
  public Long getJdShippingFlg() {
    return jdShippingFlg;
  }

  /**
   * @return the shippingStatusWms
   */
  public Long getShippingStatusWms() {
    return shippingStatusWms;
  }

  /**
   * @param shippingNo
   *          the shippingNo to set
   */
  public void setShippingNo(String shippingNo) {
    this.shippingNo = shippingNo;
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
   * @param customerCode
   *          the customerCode to set
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * @param addressNo
   *          the addressNo to set
   */
  public void setAddressNo(Long addressNo) {
    this.addressNo = addressNo;
  }

  /**
   * @param addressLastName
   *          the addressLastName to set
   */
  public void setAddressLastName(String addressLastName) {
    this.addressLastName = addressLastName;
  }

  /**
   * @param addressFirstName
   *          the addressFirstName to set
   */
  public void setAddressFirstName(String addressFirstName) {
    this.addressFirstName = addressFirstName;
  }

  /**
   * @param addressLastNameKana
   *          the addressLastNameKana to set
   */
  public void setAddressLastNameKana(String addressLastNameKana) {
    this.addressLastNameKana = addressLastNameKana;
  }

  /**
   * @param postalCode
   *          the postalCode to set
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  /**
   * @param prefectureCode
   *          the prefectureCode to set
   */
  public void setPrefectureCode(String prefectureCode) {
    this.prefectureCode = prefectureCode;
  }

  /**
   * @param address1
   *          the address1 to set
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  /**
   * @param address2
   *          the address2 to set
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  /**
   * @param address3
   *          the address3 to set
   */
  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  /**
   * @param address4
   *          the address4 to set
   */
  public void setAddress4(String address4) {
    this.address4 = address4;
  }

  /**
   * @param phoneNumber
   *          the phoneNumber to set
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * @param mobileNumber
   *          the mobileNumber to set
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  /**
   * @param deliveryRemark
   *          the deliveryRemark to set
   */
  public void setDeliveryRemark(String deliveryRemark) {
    this.deliveryRemark = deliveryRemark;
  }

  /**
   * @param acquiredPoint
   *          the acquiredPoint to set
   */
  public void setAcquiredPoint(BigDecimal acquiredPoint) {
    this.acquiredPoint = acquiredPoint;
  }

  /**
   * @param deliverySlipNo
   *          the deliverySlipNo to set
   */
  public void setDeliverySlipNo(String deliverySlipNo) {
    this.deliverySlipNo = deliverySlipNo;
  }

  /**
   * @param shippingCharge
   *          the shippingCharge to set
   */
  public void setShippingCharge(BigDecimal shippingCharge) {
    this.shippingCharge = shippingCharge;
  }

  /**
   * @param shippingChargeTaxType
   *          the shippingChargeTaxType to set
   */
  public void setShippingChargeTaxType(Long shippingChargeTaxType) {
    this.shippingChargeTaxType = shippingChargeTaxType;
  }

  /**
   * @param shippingChargeTaxRate
   *          the shippingChargeTaxRate to set
   */
  public void setShippingChargeTaxRate(Long shippingChargeTaxRate) {
    this.shippingChargeTaxRate = shippingChargeTaxRate;
  }

  /**
   * @param shippingChargeTax
   *          the shippingChargeTax to set
   */
  public void setShippingChargeTax(BigDecimal shippingChargeTax) {
    this.shippingChargeTax = shippingChargeTax;
  }

  /**
   * @param deliveryTypeNo
   *          the deliveryTypeNo to set
   */
  public void setDeliveryTypeNo(Long deliveryTypeNo) {
    this.deliveryTypeNo = deliveryTypeNo;
  }

  /**
   * @param deliveryTypeName
   *          the deliveryTypeName to set
   */
  public void setDeliveryTypeName(String deliveryTypeName) {
    this.deliveryTypeName = deliveryTypeName;
  }

  /**
   * @param deliveryAppointedDate
   *          the deliveryAppointedDate to set
   */
  public void setDeliveryAppointedDate(String deliveryAppointedDate) {
    this.deliveryAppointedDate = deliveryAppointedDate;
  }

  /**
   * @param deliveryAppointedTimeStart
   *          the deliveryAppointedTimeStart to set
   */
  public void setDeliveryAppointedTimeStart(Long deliveryAppointedTimeStart) {
    this.deliveryAppointedTimeStart = deliveryAppointedTimeStart;
  }

  /**
   * @param deliveryAppointedTimeEnd
   *          the deliveryAppointedTimeEnd to set
   */
  public void setDeliveryAppointedTimeEnd(Long deliveryAppointedTimeEnd) {
    this.deliveryAppointedTimeEnd = deliveryAppointedTimeEnd;
  }

  /**
   * @param arrivalDate
   *          the arrivalDate to set
   */
  public void setArrivalDate(Date arrivalDate) {
    this.arrivalDate = arrivalDate;
  }

  /**
   * @param arrivalTimeStart
   *          the arrivalTimeStart to set
   */
  public void setArrivalTimeStart(Long arrivalTimeStart) {
    this.arrivalTimeStart = arrivalTimeStart;
  }

  /**
   * @param arrivalTimeEnd
   *          the arrivalTimeEnd to set
   */
  public void setArrivalTimeEnd(Long arrivalTimeEnd) {
    this.arrivalTimeEnd = arrivalTimeEnd;
  }

  /**
   * @param fixedSalesStatus
   *          the fixedSalesStatus to set
   */
  public void setFixedSalesStatus(Long fixedSalesStatus) {
    this.fixedSalesStatus = fixedSalesStatus;
  }

  /**
   * @param shippingStatus
   *          the shippingStatus to set
   */
  public void setShippingStatus(Long shippingStatus) {
    this.shippingStatus = shippingStatus;
  }

  /**
   * @param shippingDirectDate
   *          the shippingDirectDate to set
   */
  public void setShippingDirectDate(Date shippingDirectDate) {
    this.shippingDirectDate = shippingDirectDate;
  }

  /**
   * @param shippingDate
   *          the shippingDate to set
   */
  public void setShippingDate(Date shippingDate) {
    this.shippingDate = shippingDate;
  }

  /**
   * @param returnItemDate
   *          the returnItemDate to set
   */
  public void setReturnItemDate(Date returnItemDate) {
    this.returnItemDate = returnItemDate;
  }

  /**
   * @param returnItemLossMoney
   *          the returnItemLossMoney to set
   */
  public void setReturnItemLossMoney(BigDecimal returnItemLossMoney) {
    this.returnItemLossMoney = returnItemLossMoney;
  }

  /**
   * @param returnItemType
   *          the returnItemType to set
   */
  public void setReturnItemType(Long returnItemType) {
    this.returnItemType = returnItemType;
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
   * @param cityCode
   *          the cityCode to set
   */
  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  /**
   * @param deliveryCompanyNo
   *          the deliveryCompanyNo to set
   */
  public void setDeliveryCompanyNo(String deliveryCompanyNo) {
    this.deliveryCompanyNo = deliveryCompanyNo;
  }

  /**
   * @param deliveryCompanyName
   *          the deliveryCompanyName to set
   */
  public void setDeliveryCompanyName(String deliveryCompanyName) {
    this.deliveryCompanyName = deliveryCompanyName;
  }

  /**
   * @param areaCode
   *          the areaCode to set
   */
  public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
  }

  /**
   * @param jdShippingFlg
   *          the jdShippingFlg to set
   */
  public void setJdShippingFlg(Long jdShippingFlg) {
    this.jdShippingFlg = jdShippingFlg;
  }

  /**
   * @param shippingStatusWms
   *          the shippingStatusWms to set
   */
  public void setShippingStatusWms(Long shippingStatusWms) {
    this.shippingStatusWms = shippingStatusWms;
  }

  /**
   * @return the addressFirstNameKana
   */
  public String getAddressFirstNameKana() {
    return addressFirstNameKana;
  }

  /**
   * @param addressFirstNameKana
   *          the addressFirstNameKana to set
   */
  public void setAddressFirstNameKana(String addressFirstNameKana) {
    this.addressFirstNameKana = addressFirstNameKana;
  }

  /**
   * @return the originalShippingNo
   */
  public String getOriginalShippingNo() {
    return originalShippingNo;
  }

  /**
   * @param originalShippingNo
   *          the originalShippingNo to set
   */
  public void setOriginalShippingNo(String originalShippingNo) {
    this.originalShippingNo = originalShippingNo;
  }

  
  /**
   * @return the isPart
   */
  public String getIsPart() {
    return isPart;
  }

  
  /**
   * @param isPart the isPart to set
   */
  public void setIsPart(String isPart) {
    this.isPart = isPart;
  }

  
  /**
   * @return the discountPrice
   */
  public BigDecimal getDiscountPrice() {
    return discountPrice;
  }

  
  /**
   * @param discountPrice the discountPrice to set
   */
  public void setDiscountPrice(BigDecimal discountPrice) {
    this.discountPrice = discountPrice;
  }

  
  /**
   * @return the childOrderNo
   */
  public String getChildOrderNo() {
    return childOrderNo;
  }

  
  /**
   * @param childOrderNo the childOrderNo to set
   */
  public void setChildOrderNo(String childOrderNo) {
    this.childOrderNo = childOrderNo;
  }
  
  public Object clone() {
    JdShippingHeader header = null;
    try {
      header = (JdShippingHeader) super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return header;
  }

  
  /**
   * @return the childPaidPrice
   */
  public BigDecimal getChildPaidPrice() {
    return childPaidPrice;
  }

  
  /**
   * @param childPaidPrice the childPaidPrice to set
   */
  public void setChildPaidPrice(BigDecimal childPaidPrice) {
    this.childPaidPrice = childPaidPrice;
  }     
  
}
