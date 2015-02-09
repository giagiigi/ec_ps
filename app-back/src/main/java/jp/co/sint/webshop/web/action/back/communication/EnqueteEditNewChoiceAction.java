package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteEditBean;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteEditBean.QuestionListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060120:アンケートマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteEditNewChoiceAction extends EnqueteEditBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    EnqueteEditBean nextBean = getBean();

    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    String enqueteCode = nextBean.getEnqueteCode();
    if (service.getEnquete(enqueteCode) == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.communication.EnqueteEditNewChoiceAction.0")));
      setRequestBean(nextBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    nextBean = searchEnquete(nextBean.getEnqueteCode());

    QuestionListBean questionBean = new QuestionListBean();
    nextBean.setRegisterQuestion(questionBean);

    // 設問のドロップダウン作成
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    nextBean.getRegisterChoice().setQuestionContents(s.getEnqueteQuestions(nextBean.getEnqueteCode()));
    nextBean.getRegisterChoice().setQuestionNo("");
    nextBean.setChoicesList(null);

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
    return Messages.getString("web.action.back.communication.EnqueteEditNewChoiceAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106012006";
  }

}
