package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.ShopDeleteBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050240:ショップマスタ削除確認のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ShopDeleteInitAction extends WebBackAction<ShopDeleteBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;
    BackLoginInfo loginInfo = getLoginInfo();

    if (Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(loginInfo)) {
      authorization = true;
    } else {
      authorization = false;
    }

    return authorization;
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

    ShopDeleteBean bean = getBean();

    // ショップコードが存在しなかった場合エラー
    if (!StringUtil.hasValue(bean.getShopCode())) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_SHOP_CODE_ERROR));
      setNextUrl(null);
      return BackActionResult.RESULT_SUCCESS;
    }

    // コンプリートパラメータが存在した場合、完了を表示する
    String action = "";
    if (getRequestParameter().getPathArgs().length > 0) {
      // パラメータが存在した場合、[0]には"delete"又は"cancel"が入る
      action = getRequestParameter().getPathArgs()[0];
    }
    if (action.equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
          Messages.getString("web.action.back.shop.ShopDeleteInitAction.0")));
      ShopDeleteBean nextBean = new ShopDeleteBean();
      nextBean.setShopCode(bean.getShopCode());
      nextBean.setDeleteCompleteFlg(true);
      setRequestBean(nextBean);
      setNextUrl(null);
      return BackActionResult.RESULT_SUCCESS;
    } else if (action.equals(WebConstantCode.COMPLETE_CANCEL)) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.shop.ShopDeleteInitAction.1")));
      ShopDeleteBean nextBean = new ShopDeleteBean();
      nextBean.setDeleteCompleteFlg(true);
      setRequestBean(nextBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    ShopDeleteBean nextBean = new ShopDeleteBean();
    nextBean.setShopCode(bean.getShopCode());
    nextBean.setDeleteCompleteFlg(false);
    setRequestBean(nextBean);

    setNextUrl(null);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.ShopDeleteInitAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105024003";
  }

}
