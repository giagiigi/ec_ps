package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.UtilService;
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
public class EnqueteEditDeleteChoiceAction extends EnqueteEditBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    EnqueteEditBean enqueteBean = getBean();

    // 回答者が存在するアンケートの削除は不可
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    Long answerCount = service.getEnqueteAnswerCount(enqueteBean.getEnqueteCode());
    if (answerCount > 0) {
      addErrorMessage(WebMessage.get(CommunicationErrorMessage.ANSWERED_ENQUETE_ERROR,
          Messages.getString("web.action.back.communication.EnqueteEditDeleteChoiceAction.0"),
          Messages.getString("web.action.back.communication.EnqueteEditDeleteChoiceAction.1")));
      this.setRequestBean(enqueteBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 削除処理
    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length < 1) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.DELETE_ERROR,
          Messages.getString("web.action.back.communication.EnqueteEditDeleteChoiceAction.0")));
      this.setRequestBean(enqueteBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    ServiceResult result = service.deleteEnqueteChoice(enqueteBean.getEnqueteCode(), Long.valueOf(enqueteBean.getRegisterChoice()
        .getQuestionNo()), Long.valueOf(urlParam[0]));

    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.DELETE_ERROR,
              Messages.getString("web.action.back.communication.EnqueteEditDeleteChoiceAction.0")));
        } else if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.communication.EnqueteEditDeleteChoiceAction.0")));
        }
      }
      this.setRequestBean(enqueteBean);
      return BackActionResult.RESULT_SUCCESS;
    }
    addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
        Messages.getString("web.action.back.communication.EnqueteEditDeleteChoiceAction.0")));

    // 再表示
    EnqueteEditBean nextBean = searchEnquete(enqueteBean.getEnqueteCode(), enqueteBean.getRegisterChoice().getQuestionNo());

    // 選択肢エリアの設問ドロップダウン作成
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    nextBean.getRegisterChoice().setQuestionContents(s.getEnqueteQuestions(nextBean.getEnqueteCode()));
    nextBean.getRegisterChoice().setQuestionNo(enqueteBean.getRegisterChoice().getQuestionNo());

    nextBean.setQuestionButtonDisplay(false);
    nextBean.setQuestionsAreaDisplay(true);
    nextBean.setChoicesAreaDisplay(true);

    this.setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.EnqueteEditDeleteChoiceAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106012002";
  }

}
