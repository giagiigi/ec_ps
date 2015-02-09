package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.Enquete;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1060110:アンケート管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteListDeleteAction extends EnqueteListSearchAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.ENQUETE_DELETE_SITE.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    Logger logger = Logger.getLogger(this.getClass());
    EnqueteListBean nextBean = getBean();

    // 削除処理実行
    // 選択されたアンケートコードを取得する
    String[] values = getRequestParameter().getAll("checkBox");

    ServiceResult serviceResult = null;

    // 実施中のアンケートは削除不可
    for (String enqueteCode : values) {
      Enquete enq = service.getCurrentEnquete();
      if (enq != null) {
        if (enqueteCode.equals(enq.getEnqueteCode())) {
          addErrorMessage(WebMessage.get(CommunicationErrorMessage.DELETE_ENQUETE_ERROR));
          logger.error("can't delete active enquete : enqueteCode = " + enqueteCode);
          return super.callService();
        }
      }
    }

    for (String enqueteCode : values) {
      serviceResult = service.deleteEnquete(enqueteCode);
      if (serviceResult.hasError()) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.DELETE_ERROR,
            Messages.getString("web.action.back.communication.EnqueteListDeleteAction.0") + enqueteCode));
        logger.error("failed : enqueteCode = " + enqueteCode);
      }
    }
    if (getDisplayMessage().getErrors().size() < 1) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
          Messages.getString("web.action.back.communication.EnqueteListDeleteAction.1")));
    }
    this.setRequestBean(nextBean);

    // 削除後の結果を再検索する
    return super.callService();
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
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // 選択されたアンケートコードを取得する
    String[] values = getRequestParameter().getAll("checkBox");

    // チェックボックスが選択されているか
    if (!StringUtil.hasValueAllOf(values)) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.communication.EnqueteListDeleteAction.1")));
      this.setRequestBean(getBean());
      return false;
    }
    return true;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.EnqueteListDeleteAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106011001";
  }

}
