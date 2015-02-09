package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.attribute.Required;

public class ZsIfSOShipImportReport implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 订单编号 */
  @Required
  @Length(16)
  @Metadata(name = "订单编号", order = 1)
  private String orderNo;

  /** 出荷日 */
  @Required
  @Datetime
  @Metadata(name = "出荷日", order = 2)
  private Date shippingDate;

  // line
  @Metadata(name = "明细行号", order = 3)
  private String lineNo;

  /** SKUコード */
  @Required
  @Length(24)
  @AlphaNum2
  @Metadata(name = "SKUコード", order = 4)
  private String skuCode;

  // cancel B/O
  @Metadata(name = "取消标志", order = 5)
  private String cancelFlg;

  /** 发货数 */
  @Required
  @Length(8)
  @Quantity
  @Metadata(name = "发货数", order = 6)
  private Long shippingAmount;

  // Site
  @Metadata(name = "Site", order = 7)
  private String site;

  // Location
  @Metadata(name = "Location", order = 8)
  private String location;

  // lot Number
  @Metadata(name = "lot_number", order = 9)
  private String lotNumber;

  // reference
  @Metadata(name = "reference", order = 10)
  private String reference;

  /** 退货时使用的订单编号 */
  @Length(16)
  @Metadata(name = "退货时使用的订单编号", order = 11)
  private String cancelOrderNo;

  // shipment_status
  @Metadata(name = "shipment_status", order = 12)
  private String shipmentStatus;

  /** 运单号 */
  @Length(30)
  @Metadata(name = "运单号 ", order = 13)
  private String deliverySlipNo;

  /** 处理标志 */
  @Length(1)
  @Quantity
  @Metadata(name = "处理标志", order = 14)
  private Long dealflg = 0L;

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public Date getShippingDate() {
    return shippingDate;
  }

  public void setShippingDate(Date shippingDate) {
    this.shippingDate = shippingDate;
  }

  public String getLineNo() {
    return lineNo;
  }

  public void setLineNo(String lineNo) {
    this.lineNo = lineNo;
  }

  public String getSkuCode() {
    return skuCode;
  }

  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  public String getCancelFlg() {
    return cancelFlg;
  }

  public void setCancelFlg(String cancelFlg) {
    this.cancelFlg = cancelFlg;
  }

  public Long getShippingAmount() {
    return shippingAmount;
  }

  public void setShippingAmount(Long shippingAmount) {
    this.shippingAmount = shippingAmount;
  }

  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getLotNumber() {
    return lotNumber;
  }

  public void setLotNumber(String lotNumber) {
    this.lotNumber = lotNumber;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public String getCancelOrderNo() {
    return cancelOrderNo;
  }

  public void setCancelOrderNo(String cancelOrderNo) {
    this.cancelOrderNo = cancelOrderNo;
  }

  public String getShipmentStatus() {
    return shipmentStatus;
  }

  public void setShipmentStatus(String shipmentStatus) {
    this.shipmentStatus = shipmentStatus;
  }

  public String getDeliverySlipNo() {
    return deliverySlipNo;
  }

  public void setDeliverySlipNo(String deliverySlipNo) {
    this.deliverySlipNo = deliverySlipNo;
  }

  public Long getDealflg() {
    return dealflg;
  }

  public void setDealflg(Long dealflg) {
    this.dealflg = dealflg;
  }

}
