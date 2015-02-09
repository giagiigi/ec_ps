package jp.co.sint.webshop.ext.cup;

/**
 * @author System Integrator Corp.
 */
public class CupRefundRequest extends CupRequest {

  /**  */
  private static final long serialVersionUID = 1L;

  private String refundAmount;

  /**
   * refundAmountを返します。
   * 
   * @return refundAmount
   */
  public String getRefundAmount() {
    return refundAmount;
  }

  /**
   * refundAmountを設定します。
   * 
   * @param refundAmount
   *          refundAmount
   */
  public void setRefundAmount(String refundAmount) {
    this.refundAmount = refundAmount;
  }

}
