package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.EnqueteChoice;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060120:アンケートマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteEditRegisterChoiceAction extends EnqueteEditBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (StringUtil.isNullOrEmpty(getBean().getRegisterChoice().getQuestionNo())) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.communication.EnqueteEditRegisterChoiceAction.0")));
      return false;
    }
    return validateBean(getBean().getRegisterChoice());
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
    EnqueteChoice choice = new EnqueteChoice();
    choice.setEnqueteCode(enqueteBean.getEnqueteCode());
    choice.setEnqueteQuestionNo(Long.parseLong(enqueteBean.getRegisterChoice().getQuestionNo()));
    choice.setDisplayOrder(Long.parseLong(enqueteBean.getRegisterChoice().getChoicesDisplayOrder()));
    choice.setEnqueteChoices(enqueteBean.getRegisterChoice().getEnqueteChoices());
    //	add by cs_yuli 20120516 start
    choice.setEnqueteChoicesEn(enqueteBean.getRegisterChoice().getEnqueteChoicesEn());
    choice.setEnqueteChoicesJp(enqueteBean.getRegisterChoice().getEnqueteChoicesJp()); 
    //	add by cs_yuli 20120516 end    
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    ServiceResult result = service.insertEnqueteChoice(choice);

    if (result.hasError()) {
      for (ServiceErrorContent c : result.getServiceErrorList()) {
        if (c.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.communication.EnqueteEditRegisterChoiceAction.1")));
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }
    addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
        Messages.getString("web.action.back.communication.EnqueteEditRegisterChoiceAction.2")));

    // 設問・選択肢エリア再表示
    EnqueteEditBean nextBean = searchEnquete(enqueteBean.getEnqueteCode(), enqueteBean.getRegisterChoice().getQuestionNo());

    // 選択肢エリアの設問ドロップダウン作成
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    nextBean.getRegisterChoice().setQuestionContents(s.getEnqueteQuestions(nextBean.getEnqueteCode()));
    nextBean.getRegisterChoice().setQuestionNo(enqueteBean.getRegisterChoice().getQuestionNo());

    // 設問エリアを表示、選択肢エリアを非表示
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
    return Messages.getString("web.action.back.communication.EnqueteEditRegisterChoiceAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106012008";
  }

}
