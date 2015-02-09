package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.EnqueteQuestion;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060120:アンケートマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteEditSelectQuestionAction extends EnqueteEditBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    EnqueteEditBean enqueteBean = getBean();

    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length < 1) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR));
      this.setRequestBean(enqueteBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    EnqueteQuestion result = service.getEnqueteQuestion(enqueteBean.getEnqueteCode(), Long.parseLong(urlParam[0]));
    if (result == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.communication.EnqueteEditSelectQuestionAction.0")));
      this.setRequestBean(enqueteBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 再表示
    EnqueteEditBean nextBean = searchEnquete(enqueteBean.getEnqueteCode());

    // ボタン表示切替、設問エリアを表示
    nextBean.setQuestionButtonDisplay(true);
    nextBean.setQuestionsAreaDisplay(true);

    // 設問登録エリアに選択した設問を設定
    nextBean.getRegisterQuestion().setEnqueteQuestionNo(result.getEnqueteQuestionNo().toString());
    nextBean.getRegisterQuestion().setQuestionDisplayOrder(result.getDisplayOrder().toString());
    nextBean.getRegisterQuestion().setEnqueteQuestionContent(result.getEnqueteQuestionContent());
    //	add by cs_yuli 20120516 start 
    nextBean.getRegisterQuestion().setEnqueteQuestionContentEn(result.getEnqueteQuestionContentEn());
    nextBean.getRegisterQuestion().setEnqueteQuestionContentJp(result.getEnqueteQuestionContentJp());
    //	add by cs_yuli 20120516 end
    nextBean.getRegisterQuestion().setEnqueteQuestionType(result.getEnqueteQuestionType().toString());
    nextBean.getRegisterQuestion().setRequiredFlg(result.getNecessaryFlg().toString());
    nextBean.getRegisterQuestion().setUpdateDatetime(result.getUpdatedDatetime());

    this.setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.EnqueteEditSelectQuestionAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106012011";
  }

}
