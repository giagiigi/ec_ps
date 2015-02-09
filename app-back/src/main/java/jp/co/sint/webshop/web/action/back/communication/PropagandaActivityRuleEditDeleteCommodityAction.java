package jp.co.sint.webshop.web.action.back.communication;

import java.text.MessageFormat;

import jp.co.sint.webshop.data.dto.PropagandaActivityCommodity;
import jp.co.sint.webshop.data.dto.PropagandaActivityRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.PropagandaActivityRuleEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 宣传品活动附送商品删除处理
 * 
 * @author Kousen.
 */
public class PropagandaActivityRuleEditDeleteCommodityAction extends PropagandaActivityRuleEditBaseAction {

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
    if (StringUtil.isNullOrEmpty(getRequestParameter().getAll("checkBoxCommodity")[0])) {
      getDisplayMessage().addError(WebMessage.get(ActionErrorMessage.NO_CHECKED, Messages.getString("web.action.back.communication.PropagandaActivityRuleEditDeleteCommodityAction.0")));
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
    PropagandaActivityRuleEditBean bean = getBean();
    PropagandaActivityRule propagandaActivityRule = service.getPropagandaActivityRule(bean.getActivityCode());

    if (propagandaActivityRule == null) {
      addErrorMessage(Messages.getString("web.action.back.communication.PropagandaActivityRuleEditDeleteCommodityAction.1"));
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    } else {
      String[] values = getRequestParameter().getAll("checkBoxCommodity");

      // 削除処理実行
      for (String commodityCode : values) {

        PropagandaActivityCommodity propagandaActivityCommodity = service.getPropagandaActivityCommodity(bean.getActivityCode(),
            commodityCode);
        if (propagandaActivityCommodity == null) {
          continue;
        }

        ServiceResult serviceResult = service.deletePropagandaActivityCommodity(bean.getActivityCode(), commodityCode);
        if (serviceResult.hasError()) {
          addErrorMessage(MessageFormat.format(Messages.getString("web.action.back.communication.PropagandaActivityRuleEditDeleteCommodityAction.2"), commodityCode));
          setNextUrl(null);
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }

      setNextUrl("/app/communication/propaganda_activity_rule_edit/select/" + super.DELETE_COMMODITY + "/" + bean.getActivityCode());
    }

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.PropagandaActivityRuleEditDeleteCommodityAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106152006";
  }
}
