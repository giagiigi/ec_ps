package jp.co.sint.webshop.ext.cup;

/**
 * @author System Integrator Corp.
 */
public class CupResponse extends CupData {

  /**  */
  private static final long serialVersionUID = 1L;

  private String transactionStatus;

  /**
   * transactionStatusを返します。
   * 
   * @return transactionStatus
   */
  public String getTransactionStatus() {
    return transactionStatus;
  }

  /**
   * transactionStatusを設定します。
   * 
   * @param transactionStatus
   *          transactionStatus
   */
  public void setTransactionStatus(String transactionStatus) {
    this.transactionStatus = transactionStatus;
  }

}
