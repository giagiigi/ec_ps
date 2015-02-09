package jp.co.sint.webshop.service.tmall;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TmallTradeHeader implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // 订单交易唯一标识码（交易编号tmall_tid）
  private String tid;

  // 订单创建时间（订单时间order_datetime、创建时间created_datetime）
  private String created;

  // 买家昵称（顾客姓名last_name）
  private String buyerNick;

  // 买家电子邮件（电子邮件email）
  private String buyerEmail;

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

  // 付款时间（paymentDate）
  private String payTime;

  // 支付宝交易流水号（payment_order_id）
  private String alipayNo;

  // 收货人手机（phone_mobile）
  private String receiverMobile;

  // 实收金额（paid_price）
  private String payment;

  // 卖家实际收到的支付宝打款金额（received_payment）
  private String receivedPayment;

  // 系统优惠金额（discount_price）
  private String discountFee;

  // 交易结束时间、成功时间（tmall_end_time）
  private String endTime;

  // 买家留言（tmall_buyer_message）
  private String buyerMessage;

  // 交易状态（tmall_status）
  private String status;

  // 交易修改时间（tmall_modified_time）
  private String modified;

  // 货到付款物流状态（tmall_cod_status）
  private String codStatus;

  // 交易类型（tmall_type）
  private String type;

  // 买家使用积分（tmall_point_fee）
  private String pointFee;

  // 买家实际使用积分（tmall_real_point_fee）
  private String real_point_fee;

  // 买家获得积分
  private String buyerObtainPointFee;

  // 发票抬头（tmall_invoice_name）
  private String invoiceName;

  // 交易佣金
  private String commissionFee;

  // 卖家手动修改金额（）
  private String adjustFee;

  // 淘宝商城优惠
  private String tmall_discount_price;

  // 满就送活动优惠
  private String mjsDiscount;

  private Long orderFlg;

  private Long orderDetailNums;

  // 订单detail信息集合
  private List<TmallTradeDetail> orderDetailList = new ArrayList<TmallTradeDetail>();

  public List<TmallTradeDetail> getOrderDetailList() {
    return orderDetailList;
  }

  public void setOrderDetailList(List<TmallTradeDetail> orderDetailList) {
    this.orderDetailList = orderDetailList;
  }

  public Long getOrderDetailNums() {
    return orderDetailNums;
  }

  public void setOrderDetailNums(Long orderDetailNums) {
    this.orderDetailNums = orderDetailNums;
  }

  public Long getOrderFlg() {
    return orderFlg;
  }

  public void setOrderFlg(Long orderFlg) {
    this.orderFlg = orderFlg;
  }

  public String getTid() {
    return tid;
  }

  public void setTid(String tid) {
    this.tid = tid;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public String getBuyerNick() {
    return buyerNick;
  }

  public void setBuyerNick(String buyerNick) {
    this.buyerNick = buyerNick;
  }

  public String getBuyerEmail() {
    return buyerEmail;
  }

  public String getReceivedPayment() {
    return receivedPayment;
  }

  public void setReceivedPayment(String receivedPayment) {
    this.receivedPayment = receivedPayment;
  }

  public void setBuyerEmail(String buyerEmail) {
    this.buyerEmail = buyerEmail;
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

  public String getPayTime() {
    return payTime;
  }

  public void setPayTime(String payTime) {
    this.payTime = payTime;
  }

  public String getAlipayNo() {
    return alipayNo;
  }

  public void setAlipayNo(String alipayNo) {
    this.alipayNo = alipayNo;
  }

  public String getReceiverMobile() {
    return receiverMobile;
  }

  public void setReceiverMobile(String receiverMobile) {
    this.receiverMobile = receiverMobile;
  }

  public String getPayment() {
    return payment;
  }

  public void setPayment(String payment) {
    this.payment = payment;
  }

  public String getDiscountFee() {
    return discountFee;
  }

  public void setDiscountFee(String discountFee) {
    this.discountFee = discountFee;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getBuyerMessage() {
    return buyerMessage;
  }

  public void setBuyerMessage(String buyerMessage) {
    this.buyerMessage = buyerMessage;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getModified() {
    return modified;
  }

  public void setModified(String modified) {
    this.modified = modified;
  }

  public String getCodStatus() {
    return codStatus;
  }

  public void setCodStatus(String codStatus) {
    this.codStatus = codStatus;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getPointFee() {
    return pointFee;
  }

  public void setPointFee(String pointFee) {
    this.pointFee = pointFee;
  }

  public String getReal_point_fee() {
    return real_point_fee;
  }

  public void setReal_point_fee(String real_point_fee) {
    this.real_point_fee = real_point_fee;
  }

  public String getInvoiceName() {
    return invoiceName;
  }

  public void setInvoiceName(String invoiceName) {
    this.invoiceName = invoiceName;
  }

  public String getBuyerObtainPointFee() {
    return buyerObtainPointFee;
  }

  public void setBuyerObtainPointFee(String buyerObtainPointFee) {
    this.buyerObtainPointFee = buyerObtainPointFee;
  }

  public String getCommissionFee() {
    return commissionFee;
  }

  public void setCommissionFee(String commissionFee) {
    this.commissionFee = commissionFee;
  }

  public String getAdjustFee() {
    return adjustFee;
  }

  public void setAdjustFee(String adjustFee) {
    this.adjustFee = adjustFee;
  }

  public String getTmall_discount_price() {
    return tmall_discount_price;
  }

  public void setTmall_discount_price(String tmall_discount_price) {
    this.tmall_discount_price = tmall_discount_price;
  }

  public String getMjsDiscount() {
    return mjsDiscount;
  }

  public void setMjsDiscount(String mjsDiscount) {
    this.mjsDiscount = mjsDiscount;
  }

}
