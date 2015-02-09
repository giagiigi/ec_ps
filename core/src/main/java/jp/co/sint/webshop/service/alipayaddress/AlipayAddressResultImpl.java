package jp.co.sint.webshop.service.alipayaddress;

public class AlipayAddressResultImpl implements AlipayAddressResult {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // 结果信息
  private AlipayAddressResultBean resultBean;

  /**
   * @return the resultBean
   */
  public AlipayAddressResultBean getResultBean() {
    return resultBean;
  }

  /**
   * @param resultBean
   *          the resultBean to set
   */
  public void setResultBean(AlipayAddressResultBean resultBean) {
    this.resultBean = resultBean;
  }

}
