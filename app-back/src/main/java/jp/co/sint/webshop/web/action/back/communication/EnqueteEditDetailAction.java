package jp.co.sint.webshop.web.action.back.communication;

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
public class EnqueteEditDetailAction extends EnqueteEditBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // 該当アンケートが存在するかチェックする
    CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
    String[] param = getRequestParameter().getPathArgs();
    if (param.length < 1 || svc.getEnquete(param[0]) == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.communication.EnqueteEditDetailAction.0")));
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
    String[] param = getRequestParameter().getPathArgs();

    // 該当アンケートを取得
    EnqueteEditBean nextBean = searchEnquete(param[0]);

    // 設問エリアを表示、選択肢エリアを非表示
    nextBean.setQuestionsAreaDisplay(true);
    nextBean.setChoicesAreaDisplay(false);

    this.setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;

  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.EnqueteEditDetailAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106012004";
  }

}
