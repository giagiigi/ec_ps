//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
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
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Phone;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.ReturnMethodType;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「退换货header」テーブルを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class ReturnHeader implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 退货编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "退货编号", order = 1)
  private String returnNo;
  
  /** 订单编号 */
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "订单编号", order = 2)
  private String orderNo;
  
  /** 出荷编号 */
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "出荷编号", order = 3)
  private String shippingNo;
  
  /** 交换订单编号 */
  @Length(16)
  @Digit
  @Metadata(name = "交换订单编号", order = 4)
  private String exchangeOrderNo;
  
  /** 类型 */
  @Required
  @Length(1)
  @Metadata(name = "类型", order = 5)
  private Long returnType;
  
  /** 状态 */
  @Required
  @Length(1)
  @Metadata(name = "状态", order = 6)
  private Long returnStatus;
  
  /** 取消flg */
  @Required
  @Length(1)
  @Metadata(name = "取消flg", order = 7)
  private Long cancelFlg;
  
  /** 受理日期 */
  @Required
  @Datetime
  @Metadata(name = "受理日期", order = 8)
  private Date acceptedDate;
  
  /** 退款签收人 */
  @Length(20)
  @Metadata(name = "退款签收人", order = 9)
  private String returnInCharge;
  
  
  /** メールアドレス */
  @Length(256)
  @Email
  @Metadata(name = "メールアドレス", order = 10)
  private String email;

  /** 電話番号 */
  @Length(20)
  @Phone
  @Metadata(name = "電話番号", order = 11)
  private String phoneNumber;
  
  /** 手机番号 */
  @Length(11)
  @MobileNumber
  @Metadata(name = "手机番号", order = 12)
  private String mobileNumber;
  
  /** 支払方法区分 */
  @Length(2)
  @Domain(PaymentMethodType.class)
  @Metadata(name = "支払方法区分", order = 13)
  private String paymentMethodType;
  
  /** 支払方法名称 */
  @Length(25)
  @Metadata(name = "支払方法名称", order = 14)
  private String paymentMethodName;
  
  /** 退款方法区分 */
  @Length(2)
  @Domain(ReturnMethodType.class)
  @Metadata(name = "退款方法区分", order = 15)
  private String returnMethodType;
  
  /** 退款方法名称 */
  @Length(25)
  @Metadata(name = "退款方法名称", order = 16)
  private String returnMethodName;
  
  
  /** 持卡人 */
  @Length(20)
  @Metadata(name = "持卡人", order = 17)
  private String cardHolder;
  
  /** 退款银行 */
  @Length(30)
  @Metadata(name = "退款银行", order = 18)
  private String returnBank;
  
  /** 银行支行  */
  @Length(30)
  @Metadata(name = "银行支行", order = 19)
  private String returnChildBank;
  
  /** 账户1  */
  @Length(4)
  @Metadata(name = "账户1", order = 20)
  private String returnBankCardNo1;
  
  /** 账户2  */
  @Length(4)
  @Metadata(name = "账户2", order = 21)
  private String returnBankCardNo2;
  
  /** 账户3  */
  @Length(4)
  @Metadata(name = "账户3", order = 22)
  private String returnBankCardNo3;
  
  /** 账户4  */
  @Length(4)
  @Metadata(name = "账户4", order = 23)
  private String returnBankCardNo4;
  
  /** 账户5  */
  @Length(4)
  @Metadata(name = "账户5", order = 24)
  private String returnBankCardNo5;
  
  
  /** 退货日期  */
  @Datetime
  @Metadata(name = "退货日期", order = 25)
  private Date returnDate;
  
  /** 发票有无 */
  @Length(1)
  @Metadata(name = "发票有无", order = 26)
  private Long invoiceFlg;
  
  /** 退款确认日期 */
  @Datetime
  @Metadata(name = "退款确认日期", order = 27)
  private Date returnVerityDate;
  
  /** 退款完成日期 */
  @Datetime
  @Metadata(name = "退款完成日期", order = 28)
  private Date returnCompleteDate;
  
  /** 实际退款金额 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "实际退款金额", order = 29)
  private BigDecimal actualReturnMoney;
  
  /** 退款联络日期 */
  @Datetime
  @Metadata(name = "退款联络日期", order = 30)
  private Date returnContactDate;
  
 
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 31)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 32)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 33)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 34)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 35)
  private Date updatedDatetime;
  
 
  
  
  /**
   * returnNoを取得します
   *
   * @return returnNo
   */
  public String getReturnNo() {
    return returnNo;
  }


  /**
   * returnNoを設定します
   *
   * @param returnNo
   */
  public void setReturnNo(String returnNo) {
    this.returnNo = returnNo;
  }


  /**
   * orderNoを取得します
   *
   * @return orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }


  /**
   * orderNoを設定します
   *
   * @param orderNo
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }


  /**
   * shippingNoを取得します
   *
   * @return shippingNo
   */
  public String getShippingNo() {
    return shippingNo;
  }


  /**
   * shippingNoを設定します
   *
   * @param shippingNo
   */
  public void setShippingNo(String shippingNo) {
    this.shippingNo = shippingNo;
  }


  
  /**
   * exchangeOrderNoを取得します
   *
   * @return exchangeOrderNo
   */
  public String getExchangeOrderNo() {
    return exchangeOrderNo;
  }



  /**
   * exchangeOrderNoを設定します
   *
   * @param exchangeOrderNo
   */
  public void setExchangeOrderNo(String exchangeOrderNo) {
    this.exchangeOrderNo = exchangeOrderNo;
  }


  /**
   * returnTypeを取得します
   *
   * @return returnType
   */
  public Long getReturnType() {
    return returnType;
  }


  /**
   * returnTypeを設定します
   *
   * @param returnType
   */
  public void setReturnType(Long returnType) {
    this.returnType = returnType;
  }


  /**
   * returnStatusを取得します
   *
   * @return returnStatus
   */
  public Long getReturnStatus() {
    return returnStatus;
  }


  /**
   * returnStatusを設定します
   *
   * @param returnStatus
   */
  public void setReturnStatus(Long returnStatus) {
    this.returnStatus = returnStatus;
  }


  /**
   * cancelFlgを取得します
   *
   * @return cancelFlg
   */
  public Long getCancelFlg() {
    return cancelFlg;
  }


  /**
   * cancelFlgを設定します
   *
   * @param cancelFlg
   */
  public void setCancelFlg(Long cancelFlg) {
    this.cancelFlg = cancelFlg;
  }


  /**
   * acceptedDateを取得します
   *
   * @return acceptedDate
   */
  public Date getAcceptedDate() {
    return acceptedDate;
  }


  /**
   * acceptedDateを設定します
   *
   * @param acceptedDate
   */
  public void setAcceptedDate(Date acceptedDate) {
    this.acceptedDate = acceptedDate;
  }


  /**
   * returnInChargeを取得します
   *
   * @return returnInCharge
   */
  public String getReturnInCharge() {
    return returnInCharge;
  }


  /**
   * 更新日時を設定します
   *
   * @param returnContactDate
   */
  public void setReturnInCharge(String returnInCharge) {
    this.returnInCharge = returnInCharge;
  }


  /**
   * returnContactDateを取得します
   *
   * @return returnContactDate
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }


  /**
   * 更新日時を設定します
   *
   * @param returnContactDate
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }


  /**
   * returnContactDateを取得します
   *
   * @return returnContactDate
   */
  public String getMobileNumber() {
    return mobileNumber;
  }


  /**
   * returnInChargeを設定します
   *
   * @param returnInCharge
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }


  /**
   * paymentMethodTypeを取得します
   *
   * @return paymentMethodType
   */
  public String getPaymentMethodType() {
    return paymentMethodType;
  }


  /**
   * paymentMethodTypeを設定します
   *
   * @param paymentMethodType
   */
  public void setPaymentMethodType(String paymentMethodType) {
    this.paymentMethodType = paymentMethodType;
  }


  /**
   * paymentMethodNameを取得します
   *
   * @return paymentMethodName
   */
  public String getPaymentMethodName() {
    return paymentMethodName;
  }


  /**
   * paymentMethodNameを設定します
   *
   * @param paymentMethodName
   */
  public void setPaymentMethodName(String paymentMethodName) {
    this.paymentMethodName = paymentMethodName;
  }


  /**
   * returnMethodTypeを取得します
   *
   * @return returnMethodType
   */
  public String getReturnMethodType() {
    return returnMethodType;
  }


  /**
   *returnMethodTypeを設定します
   *
   * @param returnMethodType
   */
  public void setReturnMethodType(String returnMethodType) {
    this.returnMethodType = returnMethodType;
  }


  /**
   * returnMethodNameを取得します
   *
   * @return returnMethodName
   */
  public String getReturnMethodName() {
    return returnMethodName;
  }

  /**
   * returnMethodNameを設定します
   *
   * @param returnMethodName
   */
  public void setReturnMethodName(String returnMethodName) {
    this.returnMethodName = returnMethodName;
  }


  /**
   * cardHolderを取得します
   *
   * @return cardHolder
   */
  public String getCardHolder() {
    return cardHolder;
  }


  /**
   * cardHolderを設定します
   *
   * @param cardHolder
   */
  public void setCardHolder(String cardHolder) {
    this.cardHolder = cardHolder;
  }


  /**
   * returnBankを取得します
   *
   * @return returnBank
   */
  public String getReturnBank() {
    return returnBank;
  }


  /**
   * returnBankを設定します
   *
   * @param returnBank
   */
  public void setReturnBank(String returnBank) {
    this.returnBank = returnBank;
  }


  /**
   * returnChildBankを取得します
   *
   * @return returnChildBank
   */
  public String getReturnChildBank() {
    return returnChildBank;
  }


  /**
   * returnChildBankを設定します
   *
   * @param returnChildBank
   */
  public void setReturnChildBank(String returnChildBank) {
    this.returnChildBank = returnChildBank;
  }


  /**
   * returnBankCardNo1を取得します
   *
   * @return returnBankCardNo1
   */
  public String getReturnBankCardNo1() {
    return returnBankCardNo1;
  }


  /**
   * returnBankCardNo1を設定します
   *
   * @param returnBankCardNo1
   */
  public void setReturnBankCardNo1(String returnBankCardNo1) {
    this.returnBankCardNo1 = returnBankCardNo1;
  }


  /**
   * returnBankCardNo2を取得します
   *
   * @return returnBankCardNo2
   */
  public String getReturnBankCardNo2() {
    return returnBankCardNo2;
  }


  /**
   * returnBankCardNo2を設定します
   *
   * @param returnBankCardNo2
   */
  public void setReturnBankCardNo2(String returnBankCardNo2) {
    this.returnBankCardNo2 = returnBankCardNo2;
  }


  /**
   * returnBankCardNo3を取得します
   *
   * @return returnBankCardNo3
   */
  public String getReturnBankCardNo3() {
    return returnBankCardNo3;
  }


  /**
   * returnBankCardNo3を設定します
   *
   * @param returnBankCardNo3
   */
  public void setReturnBankCardNo3(String returnBankCardNo3) {
    this.returnBankCardNo3 = returnBankCardNo3;
  }


  /**
   * returnBankCardNo4を取得します
   *
   * @return returnBankCardNo4
   */
  public String getReturnBankCardNo4() {
    return returnBankCardNo4;
  }


  /**
   * returnBankCardNo4を設定します
   *
   * @param returnBankCardNo4
   */
  public void setReturnBankCardNo4(String returnBankCardNo4) {
    this.returnBankCardNo4 = returnBankCardNo4;
  }


  /**
   * returnBankCardNo5を取得します
   *
   * @return returnBankCardNo5
   */
  public String getReturnBankCardNo5() {
    return returnBankCardNo5;
  }


  /**
   * returnBankCardNo5を設定します
   *
   * @param returnBankCardNo5
   */
  public void setReturnBankCardNo5(String returnBankCardNo5) {
    this.returnBankCardNo5 = returnBankCardNo5;
  }


  /**
   * returnDateを取得します
   *
   * @return returnDate
   */
  public Date getReturnDate() {
    return returnDate;
  }


  /**
   * 更新日時を設定します
   *
   * @param returnContactDate
   */
  public void setReturnDate(Date returnDate) {
    this.returnDate = returnDate;
  }


  /**
   * returnContactDateを取得します
   *
   * @return returnContactDate
   */
  public Long getInvoiceFlg() {
    return invoiceFlg;
  }


  /**
   * returnDateを設定します
   *
   * @param returnDate
   */
  public void setInvoiceFlg(Long invoiceFlg) {
    this.invoiceFlg = invoiceFlg;
  }

  /**
   * returnVerityDateを取得します
   *
   * @return returnVerityDate
   */
  public Date getReturnVerityDate() {
    return returnVerityDate;
  }


  /**
   * returnVerityDateを設定します
   *
   * @param returnVerityDate
   */
  public void setReturnVerityDate(Date returnVerityDate) {
    this.returnVerityDate = returnVerityDate;
  }


  /**
   * returnCompleteDateを取得します
   *
   * @return returnCompleteDate
   */
  public Date getReturnCompleteDate() {
    return returnCompleteDate;
  }


  /**
   * returnCompleteDateを設定します
   *
   * @param returnCompleteDate
   */
  public void setReturnCompleteDate(Date returnCompleteDate) {
    this.returnCompleteDate = returnCompleteDate;
  }


  /**
   * actualReturnMoneyを取得します
   *
   * @return actualReturnMoney
   */
  public BigDecimal getActualReturnMoney() {
    return actualReturnMoney;
  }


  /**
   * actualReturnMoneyを設定します
   *
   * @param actualReturnMoney
   */
  public void setActualReturnMoney(BigDecimal actualReturnMoney) {
    this.actualReturnMoney = actualReturnMoney;
  }


  /**
   * returnContactDateを取得します
   *
   * @return returnContactDate
   */
  public Date getReturnContactDate() {
    return returnContactDate;
  }


  /**
   * returnContactDateを設定します
   *
   * @param returnContactDate
   */
  public void setReturnContactDate(Date returnContactDate) {
    this.returnContactDate = returnContactDate;
  }


    /**
   * メールアドレスを取得します
   *
   * @return メールアドレス
   */
  public String getEmail() {
    return this.email;
  }

  
  /**
   * データ行IDを取得します
   *
   * @return データ行ID
   */
  public Long getOrmRowid() {
    return this.ormRowid;
  }

  /**
   * 作成ユーザを取得します
   *
   * @return 作成ユーザ
   */
  public String getCreatedUser() {
    return this.createdUser;
  }

  /**
   * 作成日時を取得します
   *
   * @return 作成日時
   */
  public Date getCreatedDatetime() {
    return DateUtil.immutableCopy(this.createdDatetime);
  }

  /**
   * 更新ユーザを取得します
   *
   * @return 更新ユーザ
   */
  public String getUpdatedUser() {
    return this.updatedUser;
  }

  /**
   * 更新日時を取得します
   *
   * @return 更新日時
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(this.updatedDatetime);
  }

  
  /**
   * メールアドレスを設定します
   *
   * @param  val メールアドレス
   */
  public void setEmail(String val) {
    this.email = val;
  }

 
  /**
   * データ行IDを設定します
   *
   * @param  val データ行ID
   */
  public void setOrmRowid(Long val) {
    this.ormRowid = val;
  }

  /**
   * 作成ユーザを設定します
   *
   * @param  val 作成ユーザ
   */
  public void setCreatedUser(String val) {
    this.createdUser = val;
  }

  /**
   * 作成日時を設定します
   *
   * @param  val 作成日時
   */
  public void setCreatedDatetime(Date val) {
    this.createdDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 更新ユーザを設定します
   *
   * @param  val 更新ユーザ
   */
  public void setUpdatedUser(String val) {
    this.updatedUser = val;
  }

  /**
   * 更新日時を設定します
   *
   * @param  val 更新日時
   */
  public void setUpdatedDatetime(Date val) {
    this.updatedDatetime = DateUtil.immutableCopy(val);
  }

}
