package jp.co.sint.webshop.ext.veritrans;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeCvs;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.PaymentParameter;
import Jp.BuySmart.CVS.GWLib.Transaction;

public class VeritransCvsParameter extends PaymentParameter {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** コンビニコード */
  private VeritransCvsCode cvsCode;

  /** 姓 */
  private String lastName;

  /** 名 */
  private String firstName;

  /** カナ(姓) */
  private String lastNameKana;

  /** カナ(名) */
  private String firstNameKana;

  /** 電話番号 */
  private String phoneNumber;
  
  /** 注意事項 */
  private String message;
  
  private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd");

  
  public Map<String, String> getArgs(Operation oper) {
    Map<String, String> map = null;
    switch (oper) {
      case ENTRY:
        map = getEntryRequestParameter();
        break;
      case QUERY:
        map = getQueryRequestParameter();
        break;
      default:
        break;
    }
    return map;
  }

  /**
   * 決済要求電文用パラメータを返します。<BR>
   * 
   * @return params
   */
  private Map<String, String> getEntryRequestParameter() {
    HashMap<String, String> params = new HashMap<String, String>();
    params.put(Transaction.REQ_ORDER_ID, this.getOrderId());
    params.put(Transaction.REQ_CVS_TYPE, this.getCvsCode().getValue());
    params.put(Transaction.REQ_NAME1, this.getLastName());
    params.put(Transaction.REQ_NAME2, this.getFirstName());
    params.put(Transaction.REQ_KANA, this.getLastNameKana() + this.getFirstNameKana());
    params.put(Transaction.REQ_PAY_LIMIT, FORMATTER.format(this.getPaymentLimitDate()));
    params.put(Transaction.REQ_TEL_NO, this.getPhoneNumber());
    params.put(Transaction.REQ_AMOUNT, String.valueOf(this.getAmount()));
    params.put(Transaction.REQ_COMMAND, Operation.ENTRY.getCommand());

    return params;
  }

  /**
   * 問合せ要求電文用パラメータを返します。<BR>
   * 
   * @return
   */
  private Map<String, String> getQueryRequestParameter() {
    HashMap<String, String> params = new HashMap<String, String>();
    params.put(Transaction.REQ_ORDER_ID, this.getOrderId());
    params.put(Transaction.REQ_COMMAND, Operation.QUERY.getCommand());

    return params;
  }

  /**
   * firstNameを取得します。
   * 
   * @return firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * firstNameKanaを取得します。
   * 
   * @return firstNameKana
   */
  public String getFirstNameKana() {
    return firstNameKana;
  }

  /**
   * lastNameを取得します。
   * 
   * @return lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * lastNameKanaを取得します。
   * 
   * @return lastNameKana
   */
  public String getLastNameKana() {
    return lastNameKana;
  }

  /**
   * messageを取得します。
   * 
   * @return message
   */
  public String getMessage() {
    return message;
  }

  /**
   * phoneNumberを取得します。
   * 
   * @return phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * firstNameを設定します。
   * 
   * @param firstName
   *          firstName
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * firstNameKanaを設定します。
   * 
   * @param firstNameKana
   *          firstNameKana
   */
  public void setFirstNameKana(String firstNameKana) {
    this.firstNameKana = firstNameKana;
  }

  /**
   * lastNameを設定します。
   * 
   * @param lastName
   *          lastName
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * lastNameKanaを設定します。
   * 
   * @param lastNameKana
   *          lastNameKana
   */
  public void setLastNameKana(String lastNameKana) {
    this.lastNameKana = lastNameKana;
  }

  /**
   * messageを設定します。
   * 
   * @param message
   *          message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * phoneNumberを設定します。
   * 
   * @param phoneNumber
   *          phoneNumber
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public static enum Operation {

    /** */
    ENTRY(Transaction.CMD_ENTRY),
    /** */
    QUERY("query");

    private String command;

    private Operation(String command) {
      setCommand(command);
    }

    public String getCommand() {
      return command;
    }

    public void setCommand(String command) {
      this.command = command;
    }
  }

  
  /**
   * cvsCodeを取得します。
   * 
   * @return cvsCode
   */
  public VeritransCvsCode getCvsCode() {
    return cvsCode;
  }

  /**
   * cvsCodeを設定します。
   * 
   * @param cvsCode
   *          cvsCode
   */
  public void setCvsCode(VeritransCvsCode cvsCode) {
    this.cvsCode = cvsCode;
  }

  @Override
  public void setCashierPayment(CashierPayment cashier) {
    CashierPaymentTypeCvs cvs = (CashierPaymentTypeCvs) cashier.getSelectPayment();
        
    this.setFirstName(cvs.getFirstName());
    this.setLastName(cvs.getLastName());
    this.setFirstNameKana(cvs.getFirstNameKana());
    this.setLastNameKana(cvs.getLastNameKana());
    this.setCvsCode(VeritransCvsCode.fromValue(cvs.getConvenienceCode()));
 
  }
  
  @Override
  public void setOrderContainer(OrderContainer orderContainer) {
    this.setPhoneNumber(orderContainer.getOrderHeader().getPhoneNumber());
  }

}
