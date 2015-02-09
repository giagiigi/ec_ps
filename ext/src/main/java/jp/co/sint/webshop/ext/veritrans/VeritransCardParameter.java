package jp.co.sint.webshop.ext.veritrans;

import java.util.HashMap;
import java.util.Map;

import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeCreditCard;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.PaymentParameter;

public class VeritransCardParameter extends PaymentParameter {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** クレジットカード番号 */
  private String creditCardNo;

  /** カード有効期限 */
  private String cardExpired;

  public Map<String, String> getArgs(Operation oper) {
    Map<String, String> map = null;
    switch (oper) {
      case AUTHONLY:
        map = getAuthOnlyParameter();
        break;
      case POSTAUTH:
        map = getPostAuthParamter();
        break;
      case CANCEL_AUTHONLY:
        map = getCancelAuthOnlyParameter();
        break;
      case CANCEL_POSTAUTH:
        map = getCancelPostAuthParameter();
        break;
      case RETURN:
        map = getReturnParameter();
        break;
      case QUERY:
        map = getQueryParameter();
        break;
      default:
        break;
    }
    return map;
  }

  /**
   * 与信処理電文用パラメータを返します。
   * 
   * @return params
   */
  private Map<String, String> getAuthOnlyParameter() {
    HashMap<String, String> params = new HashMap<String, String>();

    params.put(VeritransCardConstantCode.REQ_CARD_NUMBER, this.getCreditCardNo());
    params.put(VeritransCardConstantCode.REQ_CARD_EXP, this.getCardExpired());
    params.put(VeritransCardConstantCode.REQ_ORDER_ID, this.getOrderId());
    params.put(VeritransCardConstantCode.REQ_AMOUNT, String.valueOf(this.getAmount()));

    return params;
  }

  /**
   * 売上処理電文用パラメータを返します。
   * 
   * @return params
   */
  private Map<String, String> getPostAuthParamter() {
    HashMap<String, String> params = new HashMap<String, String>();

    params.put(VeritransCardConstantCode.REQ_ORDER_ID, this.getOrderId());
    params.put(VeritransCardConstantCode.REQ_AMOUNT, String.valueOf(this.getAmount()));

    return params;
  }

  /**
   * 与信取消処理電文用パラメータを返します。<BR>
   * 
   * @return params
   */
  private Map<String, String> getCancelAuthOnlyParameter() {
    HashMap<String, String> params = new HashMap<String, String>();

    params.put(VeritransCardConstantCode.REQ_ORDER_ID, this.getOrderId());
    params.put(VeritransCardConstantCode.REQ_TXN_TYPE, VeritransCardConstantCode.CMD_ENTRY);

    return params;
  }

  /**
   * 売上取消処理電文用パラメータを返します。<BR>
   * 
   * @return params
   */
  private Map<String, String> getCancelPostAuthParameter() {
    HashMap<String, String> params = new HashMap<String, String>();

    params.put(VeritransCardConstantCode.REQ_ORDER_ID, this.getOrderId());
    params.put(VeritransCardConstantCode.REQ_TXN_TYPE, VeritransCardConstantCode.CMD_INVOICE);

    return params;
  }

  /**
   * 返品処理電文用パラメータを返します。
   * 
   * @return params
   */
  private Map<String, String> getReturnParameter() {
    HashMap<String, String> params = new HashMap<String, String>();

    params.put(VeritransCardConstantCode.REQ_ORDER_ID, this.getOrderId());
    params.put(VeritransCardConstantCode.REQ_AMOUNT, String.valueOf(this.getAmount()));
    params.put(VeritransCardConstantCode.REQ_TXN_TYPE, VeritransCardConstantCode.CMD_INVOICE);

    return params;
  }

  /**
   * 問合せ処理電文用パラメータを返します。<BR>
   * WSV10では未使用。現在は決済取引IDを指定して取引情報を取得します。
   * 
   * @return params
   */
  private Map<String, String> getQueryParameter() {
    HashMap<String, String> params = new HashMap<String, String>();

    params.put(VeritransCardConstantCode.RES_ORDER_ID, this.getOrderId());

    return params;
  }

  /**
   * cardExpiredを取得します。
   * 
   * @return cardExpired
   */
  public String getCardExpired() {
    return cardExpired;
  }

  /**
   * creditCardNoを取得します。
   * 
   * @return creditCardNo
   */
  public String getCreditCardNo() {
    return creditCardNo;
  }

  /**
   * cardExpiredを設定します。
   * 
   * @param cardExpired
   *          cardExpired
   */
  public void setCardExpired(String cardExpired) {
    this.cardExpired = cardExpired;
  }

  /**
   * creditCardNoを設定します。
   * 
   * @param creditCardNo
   *          creditCardNo
   */
  public void setCreditCardNo(String creditCardNo) {
    this.creditCardNo = creditCardNo;
  }

  /** ベリトランスカード決済の取引種別 */
  public static enum Operation {

    /** 与信処理 */
    AUTHONLY("authonly"),
    /** 売上処理 */
    POSTAUTH("postauth"),
    /** 与信取消処理 */
    CANCEL_AUTHONLY("void"),
    /** 売上取消処理 */
    CANCEL_POSTAUTH("void"),
    /** 返品処理 */
    RETURN("return"),
    /** 問合せ処理 */
    QUERY("query");

    private String command;

    private Operation(String command) {
      this.command = command;
    }

    public String getCommand() {
      return command;
    }
  }

  @Override
  public void setCashierPayment(CashierPayment cashier) {
    CashierPaymentTypeCreditCard card = (CashierPaymentTypeCreditCard) cashier.getSelectPayment();

    this.setCreditCardNo(card.getCardNo());
    this.setCardExpired(card.getCardExpirationMonth() + "/" + card.getCardExpirationYear());

  }
  
  @Override
  public void setOrderContainer(OrderContainer orderContainer) {
    // カード決済は処理なし
  }

}
