package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderCustomerRegisterBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020190:新規受注（顧客登録）のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCustomerRegisterBackAction extends NeworderBaseAction<NeworderCustomerRegisterBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    setRequestBean(getBean());

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    NeworderCustomerRegisterBean bean = (NeworderCustomerRegisterBean) getRequestBean();
    bean.setDisplayNextButtonModeFlg(true);
    bean.setDisplayRegisterButtonModeFlg(false);
    bean.setDisplayBackButtonModeFlg(false);

    bean.setDisplayTextMode(WebConstantCode.DISPLAY_EDIT);

    setRequestBean(bean);
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderCustomerRegisterBackAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102019001";
  }

}
