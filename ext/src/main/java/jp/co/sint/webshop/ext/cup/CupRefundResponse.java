package jp.co.sint.webshop.ext.cup;

/**
 * @author System Integrator Corp.
 */
public class CupRefundResponse extends CupResponse {

  /**  */
  private static final long serialVersionUID = 1L;

  private String responseCode;

  private String processDate;

  private String sendTime;

  private String refundAmount;

  private String status;


  /**
   * responseCodeを返します。
   * 
   * @return responseCode
   */
  public String getResponseCode() {
    return responseCode;
  }

  /**
   * responseCodeを設定します。
   * 
   * @param responseCode
   *          responseCode
   */
  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }

  /**
   * processDateを返します。
   * 
   * @return processDate
   */
  public String getProcessDate() {
    return processDate;
  }

  /**
   * processDateを設定します。
   * 
   * @param processDate
   *          processDate
   */
  public void setProcessDate(String processDate) {
    this.processDate = processDate;
  }

  /**
   * sendTimeを返します。
   * 
   * @return sendTime
   */
  public String getSendTime() {
    return sendTime;
  }

  /**
   * sendTimeを設定します。
   * 
   * @param sendTime
   *          sendTime
   */
  public void setSendTime(String sendTime) {
    this.sendTime = sendTime;
  }

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

  /**
   * statusを返します。
   * 
   * @return status
   */
  public String getStatus() {
    return status;
  }

  /**
   * statusを設定します。
   * 
   * @param status
   *          status
   */
  public void setStatus(String status) {
    this.status = status;
  }

}
