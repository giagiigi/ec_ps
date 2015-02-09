package jp.co.sint.webshop.web.action.back.communication;

import java.text.MessageFormat;

import jp.co.sint.webshop.data.dto.PropagandaActivityRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.bean.back.communication.PropagandaActivityRuleListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 宣传品活动一览删除表示处理
 * 
 * @author Kousen.
 */
public class PropagandaActivityRuleListDeleteAction extends PropagandaActivityRuleListSearchAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.PROPAGANDA_ACTIVITY_RULE_DELETE_SHOP.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // チェックボックスが選択されているか
    if (StringUtil.isNullOrEmpty(getRequestParameter().getAll("checkBox")[0])) {
      getDisplayMessage().addError(
          WebMessage.get(ActionErrorMessage.NO_CHECKED, Messages
              .getString("web.action.back.communication.PropagandaActivityRuleListDeleteAction.1")));
      return false;
    }
    return true;
  }

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
   * 开始执行动作
   * 
   * @return 返回结果
   */
  @Override
  public WebActionResult callService() {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    boolean success = false;
    PropagandaActivityRuleListBean nextBean = getBean();

    // 選択されたレビューIDを取得する
    String[] values = getRequestParameter().getAll("checkBox");

    // 削除処理実行
    for (String activityCode : values) {

      PropagandaActivityRule propagandaActivityRule = service.getPropagandaActivityRule(activityCode);
      if (propagandaActivityRule == null) {
        continue;
      }

      // 实施中的不可删除
      if (DateUtil.isPeriodDate(propagandaActivityRule.getActivityStartDatetime(), propagandaActivityRule.getActivityEndDatetime())) {
        addErrorMessage(MessageFormat.format(Messages
            .getString("web.action.back.communication.PropagandaActivityRuleListDeleteAction.2"), activityCode));
        continue;
      }

      ServiceResult serviceResult = service.deletePropagandaActivityRule(activityCode);
      if (serviceResult.hasError()) {
        addErrorMessage(MessageFormat.format(Messages
            .getString("web.action.back.communication.PropagandaActivityRuleListDeleteAction.3"), activityCode));
      }

      success = true;
    }
    if (success) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, Messages
          .getString("web.action.back.communication.PropagandaActivityRuleListDeleteAction.4")));
    }

    setRequestBean(nextBean);

    // 削除後の結果を再検索する
    return super.callService();
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.PropagandaActivityRuleListDeleteAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106151003";
  }
}
