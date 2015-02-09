package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.service.shop.UserAccountSearchCondition;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1050910:管理ユーザマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AccountListSearchAction extends AccountListBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return validateBean(getBean().getSearchCondition());
  }

  /**
   * 検索条件を取得します。
   */
  @Override
  public UserAccountSearchCondition getSearchCondition() {
    UserAccountSearchCondition condition = new UserAccountSearchCondition();
    condition.setDispSuperUser(getLoginInfo().getUserCode().equals(DIContainer.getWebshopConfig().getSiteUserCode()));
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.AccountListSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105091004";
  }

}
