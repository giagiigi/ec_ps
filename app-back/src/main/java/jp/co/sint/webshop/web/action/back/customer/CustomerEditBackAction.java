package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.CustomerEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030120:顧客登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerEditBackAction extends CustomerEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 権限チェック(参照：更新)
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_UPDATE.isGranted(login);
  }

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
   * 表示ボタン・入力テキストモード設定
   */
  @Override
  public void prerender() {
    CustomerEditBean nextBean = (CustomerEditBean) getRequestBean();

    // 更新権限チェック
    nextBean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    nextBean.setNextButtonDisplayFlg(true);

    // 初期表示時は常に登録/更新/戻るボタンを非表示にセット
    nextBean.setRegisterButtonDisplayFlg(false);
    nextBean.setUpdateButtonDisplayFlg(false);
    nextBean.setBackButtonDisplayFlg(false);

    // 削除権限チェック
    if (Permission.CUSTOMER_DELETE.isGranted(getLoginInfo())) {
      if (StringUtil.hasValue(nextBean.getCustomerCode())) {
        nextBean.setWithdrawalDisplayFlg(true);
      } else {
        nextBean.setWithdrawalDisplayFlg(false);
      }
    }
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
    return Messages.getString("web.action.back.customer.CustomerEditBackAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103012001";
  }

}
