package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070810:売上集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class SalesAmountSearchBackAction extends SalesAmountSearchAction {

  /**
   * beanのcreateAttributeを実行します。
   * 
   * @return 実行するならtrue
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.SalesAmountSearchBackAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107081005";
  }

}
