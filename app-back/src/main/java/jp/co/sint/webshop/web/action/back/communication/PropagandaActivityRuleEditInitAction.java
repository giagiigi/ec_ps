package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.domain.LanguageType;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.PropagandaActivityRuleEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * 宣传品活动初始表示处理
 * 
 * @author System Integrator Corp.
 */
public class PropagandaActivityRuleEditInitAction extends PropagandaActivityRuleEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.PROPAGANDA_ACTIVITY_RULE_READ_SHOP.isGranted(getLoginInfo());
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
    PropagandaActivityRuleEditBean bean = new PropagandaActivityRuleEditBean();

    bean.setActivityType(OrderType.EC.getValue());
    bean.setLanguageType(LanguageType.Zh_Cn.getValue());
    bean.setRegionBlockChargeList(getRegionBlockChargeOrgList());

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    BackLoginInfo login = getLoginInfo();
    PropagandaActivityRuleEditBean bean = (PropagandaActivityRuleEditBean) getRequestBean();

    // 更新権限チェック
    if (Permission.PROPAGANDA_ACTIVITY_RULE_UPDATE_SHOP.isGranted(login)) {
      bean.setDisplayRegisterButton(true);
      bean.setDisplayUpdateButton(true);
      bean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    } else {
      bean.setDisplayRegisterButton(false);
      bean.setDisplayUpdateButton(false);
      bean.setEditMode(WebConstantCode.DISPLAY_READONLY);
    }

    bean.setDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
    bean.setDisplayDeleteButton(false);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.PropagandaActivityRuleEditInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106152001";
  }

}
