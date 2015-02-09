package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.EnqueteQuestion;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060120:アンケートマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteEditRegisterQuestionAction extends EnqueteEditBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return validateBean(getBean().getRegisterQuestion());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // 入力項目取得
    EnqueteEditBean enqueteBean = getBean();

    // 登録処理
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    EnqueteQuestion question = new EnqueteQuestion();
    question.setEnqueteCode(enqueteBean.getEnqueteCode());
    question.setDisplayOrder(Long.parseLong(enqueteBean.getRegisterQuestion().getQuestionDisplayOrder()));
    question.setEnqueteQuestionContent(enqueteBean.getRegisterQuestion().getEnqueteQuestionContent());
    // add by cs_yuli 20120516 start
    question.setEnqueteQuestionContentEn(enqueteBean.getRegisterQuestion().getEnqueteQuestionContentEn());
    question.setEnqueteQuestionContentJp(enqueteBean.getRegisterQuestion().getEnqueteQuestionContentJp());
    // add by cs_yuli 20120516 end
    question.setEnqueteQuestionType(Long.parseLong(enqueteBean.getRegisterQuestion().getEnqueteQuestionType()));
    question.setNecessaryFlg(Long.parseLong(enqueteBean.getRegisterQuestion().getRequiredFlg()));
    ServiceResult result = service.insertEnqueteQuestion(question);

    if (result.hasError()) {
      for (ServiceErrorContent s : result.getServiceErrorList()) {
        if (s.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.communication.EnqueteEditRegisterQuestionAction.0")));
          setRequestBean(enqueteBean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }
    addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
        Messages.getString("web.action.back.communication.EnqueteEditRegisterQuestionAction.1")));

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
    return Messages.getString("web.action.back.communication.EnqueteEditRegisterQuestionAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106012009";
  }

}
