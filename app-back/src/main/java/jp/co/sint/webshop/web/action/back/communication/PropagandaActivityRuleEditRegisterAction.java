package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.PropagandaActivityRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.PropagandaActivityRuleEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 宣传品活动新建登录处理
 * 
 * @author System Integrator Corp.
 */
public class PropagandaActivityRuleEditRegisterAction extends PropagandaActivityRuleEditBaseAction {
  
  public boolean authorize() {
    return Permission.PROPAGANDA_ACTIVITY_RULE_UPDATE_SHOP.isGranted(getLoginInfo());
  }

  public boolean validate() {
    PropagandaActivityRuleEditBean bean = getBean();

    if (validateBean(bean) == false) {
      return false;
    }

    // 验证是否存在
    if (existActivity(bean.getActivityCode())) {

      addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_DEFAULT_ERROR));
      setRequestBean(getBean());
      setBean(getBean());

      return false;
    }
    
    // 活动期间大小关系是否正确
    if (!StringUtil.isCorrectRange(bean.getActivityStartDate(), bean.getActivityEndDate())) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, Messages.getString("web.action.back.communication.PropagandaActivityRuleEditRegisterAction.0")));
      return false;
    }
    
    // 同类型同语言同期间只能登录一个活动
    if(existCommon(bean)) {
      addErrorMessage(Messages.getString("web.action.back.communication.PropagandaActivityRuleEditRegisterAction.2"));
      return false;
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    PropagandaActivityRuleEditBean bean = getBean();
    PropagandaActivityRule propagandaActivityRule = new PropagandaActivityRule();
    setBeanToDto(bean, propagandaActivityRule);
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    ServiceResult serviceResult = communicationService.insertPropagandaActivityRule(propagandaActivityRule);

    // 登录失败
    if (serviceResult.hasError()) {
      setNextUrl(null);
      for (ServiceErrorContent error : serviceResult.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        }
      }
      addErrorMessage(WebMessage.get(DataIOErrorMessage.CONTENTS_REGISTER_FAILED, bean.getActivityCode()));
      setRequestBean(getBean());
      setBean(getBean());
      return BackActionResult.SERVICE_ERROR;
    }

    // 添加成功
    setNextUrl("/app/communication/propaganda_activity_rule_edit/select/" + super.REGISTER + "/" + bean.getActivityCode());
    setRequestBean(getBean());
    setBean(getBean());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.PropagandaActivityRuleEditRegisterAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106152002";
  }

}
