package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.EnqueteChoice;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
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
public class EnqueteEditSelectChoiceAction extends EnqueteEditBaseAction {

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

    EnqueteChoice result = service.getEnqueteChoice(enqueteBean.getEnqueteCode(), Long.parseLong(enqueteBean.getRegisterChoice()
        .getQuestionNo()), Long.parseLong(urlParam[0]));
    if (result == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.communication.EnqueteEditSelectChoiceAction.0")));
      this.setRequestBean(enqueteBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 再表示
    EnqueteEditBean nextBean = searchEnquete(enqueteBean.getEnqueteCode(), enqueteBean.getRegisterChoice().getQuestionNo());

    // 選択肢エリアの設問ドロップダウン作成
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    nextBean.getRegisterChoice().setQuestionContents(s.getEnqueteQuestions(nextBean.getEnqueteCode()));
    nextBean.getRegisterChoice().setQuestionNo(enqueteBean.getRegisterChoice().getQuestionNo());

    // 設問エリアを表示、選択肢エリアを非表示
    nextBean.setQuestionButtonDisplay(false);
    nextBean.setQuestionsAreaDisplay(true);
    nextBean.setChoiceButtonDisplay(true);
    nextBean.setChoicesAreaDisplay(true);

    // 選択肢登録エリアに選択した選択肢を設定
    nextBean.getRegisterChoice().setEnqueteChoicesNo(result.getEnqueteChoicesNo().toString());
    nextBean.getRegisterChoice().setChoicesDisplayOrder(result.getDisplayOrder().toString());
    nextBean.getRegisterChoice().setEnqueteChoices(result.getEnqueteChoices());
    //	add by cs_yuli 20120516 start
    nextBean.getRegisterChoice().setEnqueteChoicesEn(result.getEnqueteChoicesEn());
    nextBean.getRegisterChoice().setEnqueteChoicesJp(result.getEnqueteChoicesJp()); 
    //	add by cs_yuli 20120516 end   
    nextBean.getRegisterChoice().setUpdateDatetime(result.getUpdatedDatetime());

    this.setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.EnqueteEditSelectChoiceAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106012010";
  }

}
