package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.PropagandaActivityRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.PropagandaActivityRuleEditBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * 宣传品活动选择表示处理
 * 
 * @author System Integrator Corp.
 */
public class PropagandaActivityRuleEditSelectAction extends PropagandaActivityRuleEditBaseAction {

  public boolean authorize() {
    return Permission.PROPAGANDA_ACTIVITY_RULE_READ_SHOP.isGranted(getLoginInfo());
  }

  public boolean validate() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    PropagandaActivityRuleEditBean bean = new PropagandaActivityRuleEditBean();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    String activityCode = "";
    if (getRequestParameter().getPathArgs().length == 1) {
      activityCode = getRequestParameter().getPathArgs()[0];
      PropagandaActivityRule propagandaActivityRule = communicationService.getPropagandaActivityRule(activityCode);

      if (propagandaActivityRule == null) {
        throw new URLNotFoundException();
      } else {
        setDtoToBean(propagandaActivityRule, bean);
      }
    } else if (getRequestParameter().getPathArgs().length == 2) {
      activityCode = getRequestParameter().getPathArgs()[1];
      PropagandaActivityRule propagandaActivityRule = communicationService.getPropagandaActivityRule(activityCode);

      if (propagandaActivityRule == null) {
        throw new URLNotFoundException();
      } else {
        setDtoToBean(propagandaActivityRule, bean);
        if (super.REGISTER.equals(getRequestParameter().getPathArgs()[0])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_SUCCESS_INFO, Messages.getString("web.action.back.communication.PropagandaActivityRuleEditSelectAction.0")));
        } else if (super.UPDATE.equals(getRequestParameter().getPathArgs()[0])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.UPDATE_SUCCESS_INFO, Messages.getString("web.action.back.communication.PropagandaActivityRuleEditSelectAction.0")));
        } else if (super.REGISTER_COMMODITY.equals(getRequestParameter().getPathArgs()[0])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_SUCCESS_INFO, Messages.getString("web.action.back.communication.PropagandaActivityRuleEditSelectAction.1")));
        } else if (super.DELETE_COMMODITY.equals(getRequestParameter().getPathArgs()[0])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.DELETE_SUCCESS_INFO, Messages.getString("web.action.back.communication.PropagandaActivityRuleEditSelectAction.1")));
        }
      }
    } else {
      throw new URLNotFoundException();
    }

    setRequestBean(bean);
    setBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  public void prerender() {
    BackLoginInfo login = getLoginInfo();
    PropagandaActivityRuleEditBean bean = (PropagandaActivityRuleEditBean) getRequestBean();

    // 更新権限チェック
    if (Permission.PROPAGANDA_ACTIVITY_RULE_UPDATE_SHOP.isGranted(login)) {
      bean.setDisplayUpdateButton(true);
      bean.setDisplayRegisterButton(true);
      bean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    } else {
      bean.setDisplayUpdateButton(false);
      bean.setDisplayRegisterButton(false);
      bean.setEditMode(WebConstantCode.DISPLAY_READONLY);
    }

    bean.setDisplayMode(WebConstantCode.DISPLAY_BLOCK);
    bean.setDisplayDeleteButton(Permission.PROPAGANDA_ACTIVITY_RULE_DELETE_SHOP.isGranted(login));
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.PropagandaActivityRuleEditSelectAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106152003";
  }

}
