package jp.co.sint.webshop.api.jd;

import com.jd.open.api.sdk.response.AbstractResponse;

/**
 * 京东API返回实现类
 *
 * @author OB Corp.
 */
public class JdApiResultImpl implements JdApiResult {

  private static final long serialVersionUID = 1L;
  
  private static final String SUCCESS_CODE = "0";

  private JdApiResultType resultType = JdApiResultType.FAILED;
  
  private AbstractResponse resultBean;
  
  private String errorMessage;
  
  /**
   * @return the resultType
   */
  public JdApiResultType getResultType() {
    return resultType;
  }

  /**
   * @param resultType the resultType to set
   */
  public void setResultType(JdApiResultType resultType) {
    this.resultType = resultType;
  }

  /**
   * @return the errorMessage
   */
  public String getErrorMessage() {
    if (resultBean!=null) {
      return resultBean.getZhDesc();
    }
    return errorMessage;
  }

  /**
   * @return the errorCode
   */
  public String getErrorCode() {
    if (resultBean!=null) {
      return resultBean.getCode();
    }
    return "";
  }


  public boolean hasError() {
    if (resultType!=JdApiResultType.SUCCESS  || resultBean == null || !SUCCESS_CODE.equals(resultBean.getCode())) {
      return true;
    }
    return false;
  }

  /**
   * @return the resultBean
   */
  public AbstractResponse getResultBean() {
    return resultBean;
  }

  /**
   * @param resultBean the resultBean to set
   */
  public void setResultBean(AbstractResponse resultBean) {
    this.resultBean = resultBean;
  }

  /**
   * @param errorMessage the errorMessage to set
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
