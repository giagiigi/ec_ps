package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060410:メールマガジンマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class MailMagazineDeleteAction extends MailMagazineInitAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.MAIL_MAGAZINE_DELETE_SITE.isGranted(getLoginInfo());
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
    this.setRequestBean(getBean());
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String[] path = getRequestParameter().getPathArgs();

    CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
    ServiceResult result = svc.deleteMailMagazine(path[0]);

    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR));
        } else if (error.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.DELETE_ERROR));
        }
      }
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
        Messages.getString("web.action.back.communication.MailMagazineDeleteAction.0")));
    this.setRequestBean(getBean());
    return super.callService();
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.MailMagazineDeleteAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106041001";
  }

}
