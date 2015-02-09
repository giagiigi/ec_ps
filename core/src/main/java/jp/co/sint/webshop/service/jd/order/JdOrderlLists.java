package jp.co.sint.webshop.service.jd.order;

import java.io.Serializable;

public class JdOrderlLists implements Serializable {

  /**
   * 京东拆分订单辅助类
   */
  private static final long serialVersionUID = 1L;
  private String childOrderNo;        //子编号
  private String customerCode;        //京东订单交易号
  private String skuCode;             //商品编号
  private String commodityname;       //商品名
  private String purchasingAmount;    //商品单价
  private String unitPrice;           //商品金额
  private String rowprice;            //行金额
  private String childPaidPrice;      //订单金额(子)
  private String deliveryCompanyName; //配送公司名
  private String isPart;              //卫星仓标志
  private String address1;            //收获地址1
  private String address2;            //收获地址2
  private String address3;            //收获地址3
  private String address4;            //收获地址4
  private String orderPayment;        //母订单金额
  private String orderNo;             //母订单编号
  private String shippingStatus;      //订单发货状态

  /**
   * @return the orderPayment
   */
  public String getOrderPayment() {
    return orderPayment;
  }
  
  /**
   * @param orderPayment the orderPayment to set
   */
  public void setOrderPayment(String orderPayment) {
    this.orderPayment = orderPayment;
  }
  
  /**
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }
  
  /**
   * @param orderNo the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
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
  
  /**
   * @return the shippingStatus
   */
  public String getShippingStatus() {
    return shippingStatus;
  }
  
  /**
   * @param shippingStatus the shippingStatus to set
   */
  public void setShippingStatus(String shippingStatus) {
    this.shippingStatus = shippingStatus;
  }

  
  /**
   * @return the childPaidPrice
   */
  public String getChildPaidPrice() {
    return childPaidPrice;
  }

  
  /**
   * @param childPaidPrice the childPaidPrice to set
   */
  public void setChildPaidPrice(String childPaidPrice) {
    this.childPaidPrice = childPaidPrice;
  }

  /**
   * @return the purchasingAmount
   */
  public String getPurchasingAmount() {
    return purchasingAmount;
  }

  
  /**
   * @param purchasingAmount the purchasingAmount to set
   */
  public void setPurchasingAmount(String purchasingAmount) {
    this.purchasingAmount = purchasingAmount;
  }

  
  /**
   * @return the unitPrice
   */
  public String getUnitPrice() {
    return unitPrice;
  }

  
  /**
   * @param unitPrice the unitPrice to set
   */
  public void setUnitPrice(String unitPrice) {
    this.unitPrice = unitPrice;
  }

  
  /**
   * @return the rowprice
   */
  public String getRowprice() {
    return rowprice;
  }

  
  /**
   * @param rowprice the rowprice to set
   */
  public void setRowprice(String rowprice) {
    this.rowprice = rowprice;
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
   * @return the address1
   */
  public String getAddress1() {
    return address1;
  }

  
  /**
   * @param address1 the address1 to set
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  
  /**
   * @return the address2
   */
  public String getAddress2() {
    return address2;
  }

  
  /**
   * @param address2 the address2 to set
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  
  /**
   * @return the address3
   */
  public String getAddress3() {
    return address3;
  }

  
  /**
   * @param address3 the address3 to set
   */
  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  
  /**
   * @return the address4
   */
  public String getAddress4() {
    return address4;
  }

  
  /**
   * @param address4 the address4 to set
   */
  public void setAddress4(String address4) {
    this.address4 = address4;
  }

  
  /**
   * @return the commodityname
   */
  public String getCommodityname() {
    return commodityname;
  }

  
  /**
   * @param commodityname the commodityname to set
   */
  public void setCommodityname(String commodityname) {
    this.commodityname = commodityname;
  }

  
  /**
   * @return the customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  
  /**
   * @param customerCode the customerCode to set
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  
  /**
   * @return the skuCode
   */
  public String getSkuCode() {
    return skuCode;
  }

  
  /**
   * @param skuCode the skuCode to set
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  }
