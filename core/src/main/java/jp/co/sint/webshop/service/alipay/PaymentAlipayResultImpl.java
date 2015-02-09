package jp.co.sint.webshop.service.alipay;

public class PaymentAlipayResultImpl implements PaymentAlipayResult {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // 结果信息
  private PaymentAlipayResultBean resultBean;

  /**
   * @return the resultBean
   */
  public PaymentAlipayResultBean getResultBean() {
    return resultBean;
  }

  /**
   * @param resultBean
   *          the resultBean to set
   */
  public void setResultBean(PaymentAlipayResultBean resultBean) {
    this.resultBean = resultBean;
  }

}
