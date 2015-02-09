package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.Enquete;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1060110:アンケート管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteListMoveAction extends WebBackAction<EnqueteListBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String[] urlParam = getRequestParameter().getPathArgs();
    EnqueteListBean reqBean = getBean();

    if (urlParam[0].equals("edit") || urlParam[0].equals("result")) {

      if (urlParam.length < 2) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
        setRequestBean(reqBean);
        return BackActionResult.RESULT_SUCCESS;
      }

      String enqueteCode = urlParam[1];
      CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
      Enquete enquete = service.getEnquete(enqueteCode);

      if (enquete == null) {
        setRequestBean(reqBean);
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.communication.EnqueteListMoveAction.0")));
        return BackActionResult.RESULT_SUCCESS;
      }

      if (urlParam[0].equals("edit")) {
        setNextUrl("/app/communication/enquete_edit/detail/" + enqueteCode);
      } else if (urlParam[0].equals("result")) {
        setNextUrl("/app/communication/enquete_summary/init/" + enqueteCode);
      }

    } else if (urlParam[0].equals("new")) {
      setNextUrl("/app/communication/enquete_edit/init");
    } else {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 前画面情報設定
    DisplayTransition.add(getBean(), "/app/communication/enquete_list/search_back", getSessionContainer());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return true;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length > 0) {
      return true;
    }
    addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    return false;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.EnqueteListMoveAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106011003";
  }

}
