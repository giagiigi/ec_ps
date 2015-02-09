package jp.co.sint.webshop.service.tmall;

import java.io.Serializable;

public class TmallShippingDelivery implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // 交易编号（customer_code）
  private String tid;

  // 收获人姓名（顾客姓名address_first_name）
  private String receiverName;

  // 收货人邮编（邮编postal_code）
  private String receiverZip;

  // 收货人所在省份(address1)
  private String receiverState;

  // 收货人所在市(address2)
  private String receiverCity;

  // 收货人所在区县(address3)
  private String receiverDistrict;

  // 收货人详细地址(address4)
  private String receiverAddress;

  // 收货人电话（phone_number）
  private String receiverPhone;

  // 收货人手机（phone_mobile）
  private String receiverMobile;

  // 邮费（shipping_charge）
  private String postFee;

  // 卖家发货时间（shipping_date）
  private String consignTime;

  /** 发货通知专用 */

  // 物流公司编码-示例POST
  private String companyCode;

  // 运单连接串-示例5257222
  private String outSid;

  // 正式的时候 delivery_needed(物流订单发货),测试的时候virtual_goods(虚拟物品发货)
  private String orderTypes = "virtual_goods";

  public String getOrderTypes() {
    return orderTypes;
  }

  public void setOrderTypes(String orderTypes) {
    this.orderTypes = orderTypes;
  }

  public String getCompanyCode() {
    return companyCode;
  }

  public void setCompanyCode(String companyCode) {
    this.companyCode = companyCode;
  }

  public String getOutSid() {
    return outSid;
  }

  public void setOutSid(String outSid) {
    this.outSid = outSid;
  }

  public String getTid() {
    return tid;
  }

  public void setTid(String tid) {
    this.tid = tid;
  }

  public String getReceiverName() {
    return receiverName;
  }

  public void setReceiverName(String receiverName) {
    this.receiverName = receiverName;
  }

  public String getReceiverZip() {
    return receiverZip;
  }

  public void setReceiverZip(String receiverZip) {
    this.receiverZip = receiverZip;
  }

  public String getReceiverState() {
    return receiverState;
  }

  public void setReceiverState(String receiverState) {
    this.receiverState = receiverState;
  }

  public String getReceiverCity() {
    return receiverCity;
  }

  public void setReceiverCity(String receiverCity) {
    this.receiverCity = receiverCity;
  }

  public String getReceiverDistrict() {
    return receiverDistrict;
  }

  public void setReceiverDistrict(String receiverDistrict) {
    this.receiverDistrict = receiverDistrict;
  }

  public String getReceiverAddress() {
    return receiverAddress;
  }

  public void setReceiverAddress(String receiverAddress) {
    this.receiverAddress = receiverAddress;
  }

  public String getReceiverPhone() {
    return receiverPhone;
  }

  public void setReceiverPhone(String receiverPhone) {
    this.receiverPhone = receiverPhone;
  }

  public String getReceiverMobile() {
    return receiverMobile;
  }

  public void setReceiverMobile(String receiverMobile) {
    this.receiverMobile = receiverMobile;
  }

  public String getPostFee() {
    return postFee;
  }

  public void setPostFee(String postFee) {
    this.postFee = postFee;
  }

  public String getConsignTime() {
    return consignTime;
  }

  public void setConsignTime(String consignTime) {
    this.consignTime = consignTime;
  }

}
