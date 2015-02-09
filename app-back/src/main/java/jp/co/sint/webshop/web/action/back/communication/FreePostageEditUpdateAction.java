package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.FreePostageRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.FreePostageEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 免邮促销更新处理
 * 
 * @author System Integrator Corp.
 */
public class FreePostageEditUpdateAction extends FreePostageEditBaseAction {

  public boolean validate() {
    FreePostageEditBean bean = getBean();

    if (validateBean(bean) == false) {
      return false;
    }

    // 验证是否存在
    if (existFreePostage(bean.getFreePostageCode()) == false) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "免邮促销"));
      setRequestBean(getBean());
      setBean(getBean());

      return false;
    }
    // 免邮促销期间大小关系是否正确
    if (!StringUtil.isCorrectRange(bean.getFreeStartDate(), bean.getFreeEndDate())) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, "免邮促销期间"));
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
    FreePostageEditBean bean = getBean();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    FreePostageRule freePostageRule = communicationService.getFreePostageRule(bean.getFreePostageCode());
    setBeanToDto(bean, freePostageRule);
    ServiceResult result = communicationService.updateFreePostageRule(freePostageRule);

    // 更新失败
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        }
      }

      setNextUrl(null);
      addErrorMessage(WebMessage.get(ServiceErrorMessage.UPDATE_FAILED, bean.getFreePostageCode()));
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    // 更新成功
    setNextUrl("/app/communication/free_postage_edit/select/" + super.UPDATE + "/" + bean.getFreePostageCode());

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
    return Messages.getString("web.action.back.communication.FreePostageEditUpdateAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106131009";
  }

  public boolean authorize() {
    return Permission.FREE_POSTAGE_UPDATE_SHOP.isGranted(getLoginInfo());
  }

}
