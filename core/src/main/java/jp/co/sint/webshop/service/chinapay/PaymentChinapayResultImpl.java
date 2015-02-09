package jp.co.sint.webshop.service.chinapay;


public class PaymentChinapayResultImpl implements PaymentChinapayResult {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 1L;

  // 结果信息
  private PaymentChinapayResultBean resultBean;

  // 银联接口处理结果
  private PaymentChinapayResultType paymentResultType = PaymentChinapayResultType.FAILED;

  @Override
  public boolean hasError() {
    return paymentResultType != PaymentChinapayResultType.COMPLETED;
  }

  /**
   * 取得 resultBean
   * 
   * @return the resultBean
   */
  public PaymentChinapayResultBean getResultBean() {
    return resultBean;
  }

  /**
   * 设定 resultBean
   * 
   * @param resultBean
   *          the resultBean to set
   */
  public void setResultBean(PaymentChinapayResultBean resultBean) {
    this.resultBean = resultBean;
  }

  /**
   * 取得 paymentResultType
   * 
   * @return the paymentResultType
   */
  public PaymentChinapayResultType getPaymentResultType() {
    return paymentResultType;
  }

  /**
   * 设定 paymentResultType
   * 
   * @param paymentResultType
   *          the paymentResultType to set
   */
  public void setPaymentResultType(PaymentChinapayResultType paymentResultType) {
    this.paymentResultType = paymentResultType;
  }

}
