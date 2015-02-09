package jp.co.sint.webshop.service.fastpay;

public class FastpayAlipayResultImpl implements FastpayAlipayResult {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // 结果信息
  private FastpayAlipayResultBean resultBean;

  /**
   * @return the resultBean
   */
  public FastpayAlipayResultBean getResultBean() {
    return resultBean;
  }

  /**
   * @param resultBean
   *          the resultBean to set
   */
  public void setResultBean(FastpayAlipayResultBean resultBean) {
    this.resultBean = resultBean;
  }

}
