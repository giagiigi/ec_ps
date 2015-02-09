package jp.co.sint.webshop.service.tmall;

import java.io.Serializable;

public class TmallTradeDetail implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // tmall商品型号（tmall_commodity_code）
  private String numIid;

  // tmallsku编号（tmall_sku_code）
  private String skuId;

  // ec商品型号（commodity_code）
  private String outerIid;

  // ecsku编号（sku_code）
  private String outerSkuId;

  // 商品标题（commodity_name）
  private String title;

  // 规格名称1（standard_detail1_name）
  private String skuPropertiesNameOne;

  // 规格名称2（standard_detail2_name）
  private String skuPropertiesNameTwo;

  // 商品购买数量（purchasing_amount）
  private String num;

  // 商品单价（unit_price）
  private String price1;

  // 商品贩卖价格（retail_price）
  private String price2;

  // 明细创建时间（created_datetime）
  private String created;

  // 明细修改时间（tmall_modified_time）
  private String modified;

  // 退货退款状态
  private String refundStatus;

  // 实际退款金额
  private String refundPayment;

  // 退款ID
  private String refundId;

  // 子订单折扣金额（限时折扣对应）<商品单位>
  private String childOrderPrice;

  public String getChildOrderPrice() {
    return childOrderPrice;
  }

  public void setChildOrderPrice(String childOrderPrice) {
    this.childOrderPrice = childOrderPrice;
  }

  public String getRefundStatus() {
    return refundStatus;
  }

  public void setRefundStatus(String refundStatus) {
    this.refundStatus = refundStatus;
  }

  public String getRefundPayment() {
    return refundPayment;
  }

  public void setRefundPayment(String refundPayment) {
    this.refundPayment = refundPayment;
  }

  public String getRefundId() {
    return refundId;
  }

  public void setRefundId(String refundId) {
    this.refundId = refundId;
  }

  public String getNumIid() {
    return numIid;
  }

  public void setNumIid(String numIid) {
    this.numIid = numIid;
  }

  public String getSkuId() {
    return skuId;
  }

  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }

  public String getOuterIid() {
    return outerIid;
  }

  public void setOuterIid(String outerIid) {
    this.outerIid = outerIid;
  }

  public String getOuterSkuId() {
    return outerSkuId;
  }

  public void setOuterSkuId(String outerSkuId) {
    this.outerSkuId = outerSkuId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSkuPropertiesNameOne() {
    return skuPropertiesNameOne;
  }

  public void setSkuPropertiesNameOne(String skuPropertiesNameOne) {
    this.skuPropertiesNameOne = skuPropertiesNameOne;
  }

  public String getSkuPropertiesNameTwo() {
    return skuPropertiesNameTwo;
  }

  public void setSkuPropertiesNameTwo(String skuPropertiesNameTwo) {
    this.skuPropertiesNameTwo = skuPropertiesNameTwo;
  }

  public String getNum() {
    return num;
  }

  public void setNum(String num) {
    this.num = num;
  }

  public String getPrice1() {
    return price1;
  }

  public void setPrice1(String price1) {
    this.price1 = price1;
  }

  public String getPrice2() {
    return price2;
  }

  public void setPrice2(String price2) {
    this.price2 = price2;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public String getModified() {
    return modified;
  }

  public void setModified(String modified) {
    this.modified = modified;
  }

}
