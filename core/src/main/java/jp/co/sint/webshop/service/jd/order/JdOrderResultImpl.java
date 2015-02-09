package jp.co.sint.webshop.service.jd.order;

/**
 * 返回结果类
 * @author kousen
 *
 */
public class JdOrderResultImpl implements JdOrderResult {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private JdOrderResultBean resultBean;

  /**
   * @return the resultBean
   */
  public JdOrderResultBean getResultBean() {
    return resultBean;
  }

  /**
   * @param resultBean
   *          the resultBean to set
   */
  public void setResultBean(JdOrderResultBean resultBean) {
    this.resultBean = resultBean;
  }

}
