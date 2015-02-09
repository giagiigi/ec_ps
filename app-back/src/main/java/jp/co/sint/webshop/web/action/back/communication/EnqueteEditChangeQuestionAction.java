package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060120:アンケートマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteEditChangeQuestionAction extends EnqueteEditBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    EnqueteEditBean enqueteBean = getBean();

    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length < 1) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.communication.EnqueteEditChangeQuestionAction.0")));
      this.setRequestBean(enqueteBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 設問・選択肢エリア表示
    EnqueteEditBean nextBean = searchEnquete(enqueteBean.getEnqueteCode(), urlParam[0]);

    // 選択肢エリアの設問ドロップダウン作成
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    nextBean.getRegisterChoice().setQuestionContents(s.getEnqueteQuestions(nextBean.getEnqueteCode()));
    nextBean.getRegisterChoice().setQuestionNo(urlParam[0]);

    // 設問エリアを表示、選択肢エリアを非表示
    nextBean.setQuestionButtonDisplay(false);
    nextBean.setQuestionsAreaDisplay(true);
    nextBean.setChoiceButtonDisplay(false);
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
    return Messages.getString("web.action.back.communication.EnqueteEditChangeQuestionAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106012001";
  }

}
