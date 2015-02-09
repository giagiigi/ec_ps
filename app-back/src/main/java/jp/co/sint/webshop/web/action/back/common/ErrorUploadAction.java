package jp.co.sint.webshop.web.action.back.common;

import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1xxxxxx:エラーページのアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ErrorUploadAction extends ErrorBaseAction {

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.common.ErrorUploadAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "9999999999";
  }

}
