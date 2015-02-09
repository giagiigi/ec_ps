package jp.co.sint.webshop.ext.veritrans;

import java.io.Serializable;

/**
 * ベリトランスカード決済定数クラス
 * 
 * @author System Integrator Corp.
 */
public final class VeritransCardConstantCode implements Serializable {
  
  /** uid */
  private static final long serialVersionUID = 1L;

  private VeritransCardConstantCode() {
  }
  
  /** 要求電文:カード番号 */
  public static final String REQ_CARD_NUMBER = "card-number";
  
  /** 要求電文:カード有効期限 */
  public static final String REQ_CARD_EXP = "card-exp";
  
  /** 要求電文:取引ID */
  public static final String REQ_ORDER_ID = "order-id";
 
  /** 要求電文:決済金額 */
  public static final String REQ_AMOUNT = "amount";
  
  /** 要求電文:取引タイプ */
  public static final String REQ_TXN_TYPE = "txn-type";
  
  /** 決済処理コマンド:与信処理 */
  public static final String CMD_ENTRY = "authonly";
  
  /** 決済処理コマンド:売上処理 */
  public static final String CMD_INVOICE = "postauth";
  
  /** 決済処理コマンド:取消処理 */
  public static final String CMD_CANCEL = "void";
  
  /** 決済処理コマンド:返品処理 */
  public static final String CMD_RETURN = "return";
  
  /** 決済処理コマンド:問合せ処理 */
  public static final String CMD_QUERY = "query";
  
  /** 応答電文:取引ID */
  public static final String RES_ORDER_ID = "order-id";
  
  /** 応答電文:承認番号 */
  public static final String RES_AUTH_CODE = "auth-code";
  
  /** 取引結果ステータス: カード情報不正 */
  public static final String CC_MSTATUS_FBM = "failure-bad-money";
  
  /** 取引結果ステータス: 本人認証失敗 */
  public static final String CC_MSTATUS_FIU = "failure-id-unmatched";
  
}
