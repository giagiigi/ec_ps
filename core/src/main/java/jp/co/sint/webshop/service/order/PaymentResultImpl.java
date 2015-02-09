package jp.co.sint.webshop.service.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.service.result.PaymentErrorContent;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 支払プロバイダの処理実行結果を表します。
 * 
 * @author System Integrator Corp.
 */
public class PaymentResultImpl implements PaymentResult {

  private static final long serialVersionUID = 1L;

  private PaymentResultType paymentResultType = PaymentResultType.FAILED;

  private String paymentOrderId;

  private String paymentReceiptNo;

  private String paymentReceiptUrl;

  private String message;

  private String cvsCode;

  private String digitalCashType;

  private Date paymentLimitDate;

  private List<PaymentErrorContent> paymentErrorList = new ArrayList<PaymentErrorContent>();

  /**
   * cvsCodeを取得します。
   * 
   * @return cvsCode
   */

  public String getCvsCode() {
    return cvsCode;
  }

  /**
   * digitalCashTypeを取得します。
   * 
   * @return digitalCashType
   */

  public String getDigitalCashType() {
    return digitalCashType;
  }

  /**
   * messageを取得します。
   * 
   * @return message
   */

  public String getMessage() {
    return message;
  }

  /**
   * paymentLimitDateを取得します。
   * 
   * @return paymentLimitDate
   */

  public Date getPaymentLimitDate() {
    return DateUtil.immutableCopy(paymentLimitDate);
  }

  /**
   * paymentOrderIdを取得します。
   * 
   * @return paymentOrderId
   */

  public String getPaymentOrderId() {
    return paymentOrderId;
  }

  /**
   * paymentReceiptNoを取得します。
   * 
   * @return paymentReceiptNo
   */

  public String getPaymentReceiptNo() {
    return paymentReceiptNo;
  }

  /**
   * paymentReceiptUrlを取得します。
   * 
   * @return paymentReceiptUrl
   */

  public String getPaymentReceiptUrl() {
    return paymentReceiptUrl;
  }

  /**
   * paymentResultTypeを取得します。
   * 
   * @return paymentResultType
   */

  public PaymentResultType getPaymentResultType() {
    return paymentResultType;
  }

  /**
   * cvsCodeを設定します。
   * 
   * @param cvsCode
   *          cvsCode
   */
  public void setCvsCode(String cvsCode) {
    this.cvsCode = cvsCode;
  }

  /**
   * digitalCashTypeを設定します。
   * 
   * @param digitalCashType
   *          digitalCashType
   */
  public void setDigitalCashType(String digitalCashType) {
    this.digitalCashType = digitalCashType;
  }

  /**
   * messageを設定します。
   * 
   * @param message
   *          message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * paymentErrorListを設定します。
   * 
   * @param paymentErrorList
   *          paymentErrorList
   */
  public void setPaymentErrorList(List<PaymentErrorContent> paymentErrorList) {
    this.paymentErrorList = paymentErrorList;
  }

  /**
   * paymentLimitDateを設定します。
   * 
   * @param paymentLimitDate
   *          paymentLimitDate
   */
  public void setPaymentLimitDate(Date paymentLimitDate) {
    this.paymentLimitDate = DateUtil.immutableCopy(paymentLimitDate);
  }

  /**
   * paymentOrderIdを設定します。
   * 
   * @param paymentOrderId
   *          paymentOrderId
   */
  public void setPaymentOrderId(String paymentOrderId) {
    this.paymentOrderId = paymentOrderId;
  }

  /**
   * paymentReceiptNoを設定します。
   * 
   * @param paymentReceiptNo
   *          paymentReceiptNo
   */
  public void setPaymentReceiptNo(String paymentReceiptNo) {
    this.paymentReceiptNo = paymentReceiptNo;
  }

  /**
   * paymentReceiptUrlを設定します。
   * 
   * @param paymentReceiptUrl
   *          paymentReceiptUrl
   */
  public void setPaymentReceiptUrl(String paymentReceiptUrl) {
    this.paymentReceiptUrl = paymentReceiptUrl;
  }

  public PaymentResultImpl() {
  }

  public PaymentResultImpl(PaymentResultType paymentResultType) {
    setPaymentResultType(paymentResultType);
  }

  /**
   * paymentResultTypeを設定します。
   * 
   * @param paymentResultType
   *          設定する paymentResultType
   */
  public void setPaymentResultType(PaymentResultType paymentResultType) {
    this.paymentResultType = paymentResultType;
  }

  public void addPaymentErrorList(PaymentErrorContent content) {
    paymentErrorList.add(content);
  }

  public List<PaymentErrorContent> getPaymentErrorList() {
    return paymentErrorList;
  }

  public boolean hasError() {
    return paymentResultType != PaymentResultType.COMPLETED;
  }

}
