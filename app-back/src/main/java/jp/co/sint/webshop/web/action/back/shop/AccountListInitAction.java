package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.service.shop.UserAccountSearchCondition;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050910:管理ユーザマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AccountListInitAction extends AccountListBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return true;
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

  private String getCompleteParam() {
    String[] pathInfoParams = getRequestParameter().getPathArgs();
    if (pathInfoParams.length > 0) {
      return pathInfoParams[0];
    }
    return "";
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    if (getCompleteParam().equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
          Messages.getString("web.action.back.shop.AccountListInitAction.0")));
    }
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return (getCompleteParam().length() <= 0);
  }

  /**
   * 検索条件を取得します。
   */
  @Override
  public UserAccountSearchCondition getSearchCondition() {
    UserAccountSearchCondition condition = new UserAccountSearchCondition();
    condition.setDispSuperUser(getLoginInfo().getUserCode().equals(DIContainer.getWebshopConfig().getSiteUserCode()));
    return PagerUtil.createSearchConditionFromBean(getBean(), condition);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.AccountListInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105091002";
  }

}
