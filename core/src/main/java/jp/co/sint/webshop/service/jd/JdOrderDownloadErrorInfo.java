package jp.co.sint.webshop.service.jd;

public class JdOrderDownloadErrorInfo {

  private String jdOrderNo;

  private String message;

  /**
   * @return the jdOrderNo
   */
  public String getJdOrderNo() {
    return jdOrderNo;
  }

  /**
   * @param jdOrderNo
   *          the jdOrderNo to set
   */
  public void setJdOrderNo(String jdOrderNo) {
    this.jdOrderNo = jdOrderNo;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message
   *          the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

}
