package jp.co.sint.webshop.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * ストアドプロシージャの実行結果を表現するクラスです。
 * 
 * @author System Integrator Corp.
 */
public class StoredProcedureResult implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int errorCode;

  private String errorMessage;

  private HashMap<String, Object> parameters = new HashMap<String, Object>();

  public int getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public Map<String, Object> getParameters() {
    return parameters;
  }

}
