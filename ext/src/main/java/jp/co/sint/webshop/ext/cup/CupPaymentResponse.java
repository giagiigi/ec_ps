package jp.co.sint.webshop.ext.cup;

/**
 * @author System Integrator Corp.
 */
public class CupPaymentResponse extends CupResponse {

  /**  */
  private static final long serialVersionUID = 1L;

  private String amount;

  private String currencyId;

  private String clock;

  /**
   * amountを返します。
   * 
   * @return amount
   */
  public String getAmount() {
    return amount;
  }

  /**
   * amountを設定します。
   * 
   * @param amount
   *          amount
   */
  public void setAmount(String amount) {
    this.amount = amount;
  }

  /**
   * currencyIdを返します。
   * 
   * @return currencyId
   */
  public String getCurrencyId() {
    return currencyId;
  }

  /**
   * currencyIdを設定します。
   * 
   * @param currencyId
   *          currencyId
   */
  public void setCurrencyId(String currencyId) {
    this.currencyId = currencyId;
  }

  /**
   * clockを返します。
   * 
   * @return clock
   */
  public String getClock() {
    return clock;
  }

  /**
   * clockを設定します。
   * 
   * @param clock
   *          clock
   */
  public void setClock(String clock) {
    this.clock = clock;
  }

}
