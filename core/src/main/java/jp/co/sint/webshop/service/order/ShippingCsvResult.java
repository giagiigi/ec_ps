package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.service.result.ServiceErrorContent;

/**
 * 出荷CSV取込処理の結果を表すクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingCsvResult implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<ShippingCsvError> errorList = new ArrayList<ShippingCsvError>();;

  /**
   * @return the errorList
   */
  public List<ShippingCsvError> getErrorList() {
    return errorList;
  }

  public void addErrorList(int lineNo, String shippingNo, boolean error, ServiceErrorContent errorContent) {
    errorList.add(new ShippingCsvError(lineNo, shippingNo, error, errorContent));
  }

  public boolean hasError() {
    return errorList.size() != 0;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public static class ShippingCsvError implements Serializable {

    private static final long serialVersionUID = 1L;

    private int lineNo;

    private String shippingNo;

    private boolean error;

    private ServiceErrorContent errorContent;

    public ShippingCsvError() {

    }

    public ShippingCsvError(int lineNo, String shippingNo, boolean error, ServiceErrorContent errorContent) {
      this.lineNo = lineNo;
      this.shippingNo = shippingNo;
      this.error = error;
      this.errorContent = errorContent;
    }

    /**
     * @return the error
     */
    public boolean isError() {
      return error;
    }

    /**
     * @param error
     *          the error to set
     */
    public void setError(boolean error) {
      this.error = error;
    }

    /**
     * @return the errorContent
     */
    public ServiceErrorContent getErrorContent() {
      return errorContent;
    }

    /**
     * @param errorContent
     *          the errorContent to set
     */
    public void setErrorContent(ServiceErrorContent errorContent) {
      this.errorContent = errorContent;
    }

    /**
     * @return the lineNo
     */
    public int getLineNo() {
      return lineNo;
    }

    /**
     * @param lineNo
     *          the lineNo to set
     */
    public void setLineNo(int lineNo) {
      this.lineNo = lineNo;
    }

    /**
     * @return the shippingNo
     */
    public String getShippingNo() {
      return shippingNo;
    }

    /**
     * @param shippingNo
     *          the shippingNo to set
     */
    public void setShippingNo(String shippingNo) {
      this.shippingNo = shippingNo;
    }

  }

}
