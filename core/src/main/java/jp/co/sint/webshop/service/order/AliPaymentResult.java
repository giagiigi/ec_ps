package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.util.Date;

public class AliPaymentResult implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String is_success;

  private String sign;

  private String sign_type;

  private String error;

  private String buyer_email;

  private String buyer_id;

  private String discount;

  private String flag_trade_locked;

  private Date gmt_create;

  private Date gmt_last_modified_time;

  private Date gmt_payment;

  private String is_total_fee_adjust;

  private String out_trade_no;

  private String payment_type;

  private String seller_email;

  private String seller_id;

  private String total_fee;

  private String trade_no;

  private String trade_status;

  private String use_coupon;

  private String subject;

  private String body;

  private String price;

  private String quantity;

  private String logistics_fee;

  private String coupon_discount;

  private String refund_status;

  private String logistics_status;

  private String gmt_send_goods;

  private String gmt_refund;

  private String gmt_close;

  private String gmt_logistics_modify;

  private String additional_trade_status;

  private String time_out_type;

  private Date time_out;

  private String refund_fee;

  private String refund_flow_type;

  private String refund_id;

  private String refund_cash_fee;

  private String refund_coupon_fee;

  private String refund_agent_pay_fee;

  private String coupon_used_fee;

  private String to_buyer_fee;

  private String to_seller_fee;

  private String fund_bill_list;

  /**
   * @return the is_success
   */
  public String getIs_success() {
    return is_success;
  }

  /**
   * @param is_success
   *          the is_success to set
   */
  public void setIs_success(String is_success) {
    this.is_success = is_success;
  }

  /**
   * @return the sign
   */
  public String getSign() {
    return sign;
  }

  /**
   * @param sign
   *          the sign to set
   */
  public void setSign(String sign) {
    this.sign = sign;
  }

  /**
   * @return the sign_type
   */
  public String getSign_type() {
    return sign_type;
  }

  /**
   * @param sign_type
   *          the sign_type to set
   */
  public void setSign_type(String sign_type) {
    this.sign_type = sign_type;
  }

  /**
   * @return the error
   */
  public String getError() {
    return error;
  }

  /**
   * @param error
   *          the error to set
   */
  public void setError(String error) {
    this.error = error;
  }

  /**
   * @return the buyer_email
   */
  public String getBuyer_email() {
    return buyer_email;
  }

  /**
   * @param buyer_email
   *          the buyer_email to set
   */
  public void setBuyer_email(String buyer_email) {
    this.buyer_email = buyer_email;
  }

  /**
   * @return the buyer_id
   */
  public String getBuyer_id() {
    return buyer_id;
  }

  /**
   * @param buyer_id
   *          the buyer_id to set
   */
  public void setBuyer_id(String buyer_id) {
    this.buyer_id = buyer_id;
  }

  /**
   * @return the discount
   */
  public String getDiscount() {
    return discount;
  }

  /**
   * @param discount
   *          the discount to set
   */
  public void setDiscount(String discount) {
    this.discount = discount;
  }

  /**
   * @return the flag_trade_locked
   */
  public String getFlag_trade_locked() {
    return flag_trade_locked;
  }

  /**
   * @param flag_trade_locked
   *          the flag_trade_locked to set
   */
  public void setFlag_trade_locked(String flag_trade_locked) {
    this.flag_trade_locked = flag_trade_locked;
  }

  /**
   * @return the gmt_create
   */
  public Date getGmt_create() {
    return gmt_create;
  }

  /**
   * @param gmt_create
   *          the gmt_create to set
   */
  public void setGmt_create(Date gmt_create) {
    this.gmt_create = gmt_create;
  }

  /**
   * @return the gmt_last_modified_time
   */
  public Date getGmt_last_modified_time() {
    return gmt_last_modified_time;
  }

  /**
   * @param gmt_last_modified_time
   *          the gmt_last_modified_time to set
   */
  public void setGmt_last_modified_time(Date gmt_last_modified_time) {
    this.gmt_last_modified_time = gmt_last_modified_time;
  }

  /**
   * @return the gmt_payment
   */
  public Date getGmt_payment() {
    return gmt_payment;
  }

  /**
   * @param gmt_payment
   *          the gmt_payment to set
   */
  public void setGmt_payment(Date gmt_payment) {
    this.gmt_payment = gmt_payment;
  }

  /**
   * @return the is_total_fee_adjust
   */
  public String getIs_total_fee_adjust() {
    return is_total_fee_adjust;
  }

  /**
   * @param is_total_fee_adjust
   *          the is_total_fee_adjust to set
   */
  public void setIs_total_fee_adjust(String is_total_fee_adjust) {
    this.is_total_fee_adjust = is_total_fee_adjust;
  }

  /**
   * @return the out_trade_no
   */
  public String getOut_trade_no() {
    return out_trade_no;
  }

  /**
   * @param out_trade_no
   *          the out_trade_no to set
   */
  public void setOut_trade_no(String out_trade_no) {
    this.out_trade_no = out_trade_no;
  }

  /**
   * @return the payment_type
   */
  public String getPayment_type() {
    return payment_type;
  }

  /**
   * @param payment_type
   *          the payment_type to set
   */
  public void setPayment_type(String payment_type) {
    this.payment_type = payment_type;
  }

  /**
   * @return the seller_email
   */
  public String getSeller_email() {
    return seller_email;
  }

  /**
   * @param seller_email
   *          the seller_email to set
   */
  public void setSeller_email(String seller_email) {
    this.seller_email = seller_email;
  }

  /**
   * @return the seller_id
   */
  public String getSeller_id() {
    return seller_id;
  }

  /**
   * @param seller_id
   *          the seller_id to set
   */
  public void setSeller_id(String seller_id) {
    this.seller_id = seller_id;
  }

  /**
   * @return the total_fee
   */
  public String getTotal_fee() {
    return total_fee;
  }

  /**
   * @param total_fee
   *          the total_fee to set
   */
  public void setTotal_fee(String total_fee) {
    this.total_fee = total_fee;
  }

  /**
   * @return the trade_no
   */
  public String getTrade_no() {
    return trade_no;
  }

  /**
   * @param trade_no
   *          the trade_no to set
   */
  public void setTrade_no(String trade_no) {
    this.trade_no = trade_no;
  }

  /**
   * @return the trade_status
   */
  public String getTrade_status() {
    return trade_status;
  }

  /**
   * @param trade_status
   *          the trade_status to set
   */
  public void setTrade_status(String trade_status) {
    this.trade_status = trade_status;
  }

  /**
   * @return the use_coupon
   */
  public String getUse_coupon() {
    return use_coupon;
  }

  /**
   * @param use_coupon
   *          the use_coupon to set
   */
  public void setUse_coupon(String use_coupon) {
    this.use_coupon = use_coupon;
  }

  /**
   * @return the subject
   */
  public String getSubject() {
    return subject;
  }

  /**
   * @param subject
   *          the subject to set
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * @return the body
   */
  public String getBody() {
    return body;
  }

  /**
   * @param body
   *          the body to set
   */
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * @return the price
   */
  public String getPrice() {
    return price;
  }

  /**
   * @param price
   *          the price to set
   */
  public void setPrice(String price) {
    this.price = price;
  }

  /**
   * @return the quantity
   */
  public String getQuantity() {
    return quantity;
  }

  /**
   * @param quantity
   *          the quantity to set
   */
  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  /**
   * @return the logistics_fee
   */
  public String getLogistics_fee() {
    return logistics_fee;
  }

  /**
   * @param logistics_fee
   *          the logistics_fee to set
   */
  public void setLogistics_fee(String logistics_fee) {
    this.logistics_fee = logistics_fee;
  }

  /**
   * @return the coupon_discount
   */
  public String getCoupon_discount() {
    return coupon_discount;
  }

  /**
   * @param coupon_discount
   *          the coupon_discount to set
   */
  public void setCoupon_discount(String coupon_discount) {
    this.coupon_discount = coupon_discount;
  }

  /**
   * @return the refund_status
   */
  public String getRefund_status() {
    return refund_status;
  }

  /**
   * @param refund_status
   *          the refund_status to set
   */
  public void setRefund_status(String refund_status) {
    this.refund_status = refund_status;
  }

  /**
   * @return the logistics_status
   */
  public String getLogistics_status() {
    return logistics_status;
  }

  /**
   * @param logistics_status
   *          the logistics_status to set
   */
  public void setLogistics_status(String logistics_status) {
    this.logistics_status = logistics_status;
  }

  /**
   * @return the gmt_send_goods
   */
  public String getGmt_send_goods() {
    return gmt_send_goods;
  }

  /**
   * @param gmt_send_goods
   *          the gmt_send_goods to set
   */
  public void setGmt_send_goods(String gmt_send_goods) {
    this.gmt_send_goods = gmt_send_goods;
  }

  /**
   * @return the gmt_refund
   */
  public String getGmt_refund() {
    return gmt_refund;
  }

  /**
   * @param gmt_refund
   *          the gmt_refund to set
   */
  public void setGmt_refund(String gmt_refund) {
    this.gmt_refund = gmt_refund;
  }

  /**
   * @return the gmt_close
   */
  public String getGmt_close() {
    return gmt_close;
  }

  /**
   * @param gmt_close
   *          the gmt_close to set
   */
  public void setGmt_close(String gmt_close) {
    this.gmt_close = gmt_close;
  }

  /**
   * @return the gmt_logistics_modify
   */
  public String getGmt_logistics_modify() {
    return gmt_logistics_modify;
  }

  /**
   * @param gmt_logistics_modify
   *          the gmt_logistics_modify to set
   */
  public void setGmt_logistics_modify(String gmt_logistics_modify) {
    this.gmt_logistics_modify = gmt_logistics_modify;
  }

  /**
   * @return the additional_trade_status
   */
  public String getAdditional_trade_status() {
    return additional_trade_status;
  }

  /**
   * @param additional_trade_status
   *          the additional_trade_status to set
   */
  public void setAdditional_trade_status(String additional_trade_status) {
    this.additional_trade_status = additional_trade_status;
  }

  /**
   * @return the time_out_type
   */
  public String getTime_out_type() {
    return time_out_type;
  }

  /**
   * @param time_out_type
   *          the time_out_type to set
   */
  public void setTime_out_type(String time_out_type) {
    this.time_out_type = time_out_type;
  }

  /**
   * @return the time_out
   */
  public Date getTime_out() {
    return time_out;
  }

  /**
   * @param time_out
   *          the time_out to set
   */
  public void setTime_out(Date time_out) {
    this.time_out = time_out;
  }

  /**
   * @return the refund_fee
   */
  public String getRefund_fee() {
    return refund_fee;
  }

  /**
   * @param refund_fee
   *          the refund_fee to set
   */
  public void setRefund_fee(String refund_fee) {
    this.refund_fee = refund_fee;
  }

  /**
   * @return the refund_flow_type
   */
  public String getRefund_flow_type() {
    return refund_flow_type;
  }

  /**
   * @param refund_flow_type
   *          the refund_flow_type to set
   */
  public void setRefund_flow_type(String refund_flow_type) {
    this.refund_flow_type = refund_flow_type;
  }

  /**
   * @return the refund_id
   */
  public String getRefund_id() {
    return refund_id;
  }

  /**
   * @param refund_id
   *          the refund_id to set
   */
  public void setRefund_id(String refund_id) {
    this.refund_id = refund_id;
  }

  /**
   * @return the refund_cash_fee
   */
  public String getRefund_cash_fee() {
    return refund_cash_fee;
  }

  /**
   * @param refund_cash_fee
   *          the refund_cash_fee to set
   */
  public void setRefund_cash_fee(String refund_cash_fee) {
    this.refund_cash_fee = refund_cash_fee;
  }

  /**
   * @return the refund_coupon_fee
   */
  public String getRefund_coupon_fee() {
    return refund_coupon_fee;
  }

  /**
   * @param refund_coupon_fee
   *          the refund_coupon_fee to set
   */
  public void setRefund_coupon_fee(String refund_coupon_fee) {
    this.refund_coupon_fee = refund_coupon_fee;
  }

  /**
   * @return the refund_agent_pay_fee
   */
  public String getRefund_agent_pay_fee() {
    return refund_agent_pay_fee;
  }

  /**
   * @param refund_agent_pay_fee
   *          the refund_agent_pay_fee to set
   */
  public void setRefund_agent_pay_fee(String refund_agent_pay_fee) {
    this.refund_agent_pay_fee = refund_agent_pay_fee;
  }

  /**
   * @return the coupon_used_fee
   */
  public String getCoupon_used_fee() {
    return coupon_used_fee;
  }

  /**
   * @param coupon_used_fee
   *          the coupon_used_fee to set
   */
  public void setCoupon_used_fee(String coupon_used_fee) {
    this.coupon_used_fee = coupon_used_fee;
  }

  /**
   * @return the to_buyer_fee
   */
  public String getTo_buyer_fee() {
    return to_buyer_fee;
  }

  /**
   * @param to_buyer_fee
   *          the to_buyer_fee to set
   */
  public void setTo_buyer_fee(String to_buyer_fee) {
    this.to_buyer_fee = to_buyer_fee;
  }

  /**
   * @return the to_seller_fee
   */
  public String getTo_seller_fee() {
    return to_seller_fee;
  }

  /**
   * @param to_seller_fee
   *          the to_seller_fee to set
   */
  public void setTo_seller_fee(String to_seller_fee) {
    this.to_seller_fee = to_seller_fee;
  }

  /**
   * @return the fund_bill_list
   */
  public String getFund_bill_list() {
    return fund_bill_list;
  }

  /**
   * @param fund_bill_list
   *          the fund_bill_list to set
   */
  public void setFund_bill_list(String fund_bill_list) {
    this.fund_bill_list = fund_bill_list;
  }

  /**
   * @return the serialVersionUID
   */
  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

}
