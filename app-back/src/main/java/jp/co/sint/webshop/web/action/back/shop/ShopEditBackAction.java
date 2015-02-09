package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.ShopEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050220:ショップマスタ登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ShopEditBackAction extends WebBackAction<ShopEditBean> {

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authlization = false;
    BackLoginInfo loginInfo = getLoginInfo();

    if (Permission.SITE_MANAGER.isGranted(loginInfo)) {
      authlization = true;
    } else if (Permission.SHOP_MANAGER.isGranted(loginInfo)) {
      authlization = true;
    } else if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(loginInfo)
        || Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(loginInfo)) {
      authlization = true;
    } else {
      authlization = false;
    }

    return authlization;
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
    ShopEditBean requestBean = (ShopEditBean) getRequestBean();

    requestBean.setDisplay(WebConstantCode.DISPLAY_EDIT);
    requestBean.setNextButtonDisplayFlg(true);
    requestBean.setRegisterButtonDisplayFlg(false);
    requestBean.setUpdateButtonDisplayFlg(false);
    requestBean.setBackButtonDisplayFlg(false);

    if (Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(getLoginInfo())) {
      // 新規登録時は削除ボタンを表示しない
      if (!requestBean.isUpdateProcessFlg()) {
        requestBean.setDeleteButtonDisplayFlg(false);
      } else {
        requestBean.setDeleteButtonDisplayFlg(true);
      }
    }

    setRequestBean(requestBean);
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
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.ShopEditBackAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105022001";
  }

}
