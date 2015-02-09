package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
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
public class ShopEditInitAction extends WebBackAction<ShopEditBean> {

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
    BackLoginInfo loginInfo = getLoginInfo();

    if (Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(loginInfo)) {
      return true;
    } else {
      if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(loginInfo)) {
        return true;
      }
    }

    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    BackLoginInfo loginInfo = getLoginInfo();
    if (Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(loginInfo)) {
      setNextUrl("/app/shop/shop_edit/select/" + loginInfo.getShopCode());
    }

    ShopEditBean bean = new ShopEditBean();

    bean.setUpdateProcessFlg(false);
    // modify by V10-CH 170 start 
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    bean.setCityList(s.getCityNames(bean.getPrefectureCode()));
    // modify by V10-CH 170 end
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    ShopEditBean requestBean = (ShopEditBean) getRequestBean();

    requestBean.setDisplay(WebConstantCode.DISPLAY_EDIT);
    requestBean.setDeleteButtonDisplayFlg(false);
    requestBean.setNextButtonDisplayFlg(true);
    requestBean.setRegisterButtonDisplayFlg(false);
    requestBean.setUpdateButtonDisplayFlg(false);
    requestBean.setBackButtonDisplayFlg(false);

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
    return Messages.getString("web.action.back.shop.ShopEditInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105022004";
  }

}
