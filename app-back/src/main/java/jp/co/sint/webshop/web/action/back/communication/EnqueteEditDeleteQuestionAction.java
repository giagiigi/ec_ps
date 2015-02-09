package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060120:アンケートマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteEditDeleteQuestionAction extends EnqueteEditBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    EnqueteEditBean enqueteBean = (EnqueteEditBean) getBean();

    // 回答者が存在するアンケートの削除は不可
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    Long answerCount = service.getEnqueteAnswerCount(enqueteBean.getEnqueteCode());
    if (answerCount > 0) {
      addErrorMessage(WebMessage.get(CommunicationErrorMessage.ANSWERED_ENQUETE_ERROR,
          Messages.getString("web.action.back.communication.EnqueteEditDeleteQuestionAction.0"),
          Messages.getString("web.action.back.communication.EnqueteEditDeleteQuestionAction.1")));
      this.setRequestBean(enqueteBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 削除処理
    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length < 1) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.DELETE_ERROR,
          Messages.getString("web.action.back.communication.EnqueteEditDeleteQuestionAction.0")));
      return BackActionResult.RESULT_SUCCESS;
    }

    ServiceResult result = service.deleteEnqueteQuestion(enqueteBean.getEnqueteCode(), Long.parseLong(urlParam[0]));
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)
            || error.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.DELETE_ERROR,
              Messages.getString("web.action.back.communication.EnqueteEditDeleteQuestionAction.0")));
        } else if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.communication.EnqueteEditDeleteQuestionAction.0")));
        }
      }
      this.setRequestBean(enqueteBean);
      return BackActionResult.RESULT_SUCCESS;
    } else {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
          Messages.getString("web.action.back.communication.EnqueteEditDeleteQuestionAction.0")));
    }

    // 再表示
    EnqueteEditBean nextBean = searchEnquete(enqueteBean.getEnqueteCode());

    // 設問エリアを表示
    nextBean.setQuestionsAreaDisplay(true);

    this.setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.EnqueteEditDeleteQuestionAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106012003";
  }

}
