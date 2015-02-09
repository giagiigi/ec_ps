package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.MailMagazine;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.MailMagazineBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060410:メールマガジンマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class MailMagazineRegisterAction extends MailMagazineInitAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.MAIL_MAGAZINE_UPDATE_SITE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return validateBean(getBean());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    MailMagazineBean bean = getBean();

    MailMagazine mailMagazine = new MailMagazine();
    mailMagazine.setMailMagazineCode(bean.getRegisteredMailMagazineCode());
    mailMagazine.setMailMagazineTitle(bean.getRegisteredTitle());
    mailMagazine.setMailMagazineDescription(bean.getRegisteredDescription());
    mailMagazine.setDisplayFlg(Long.parseLong((bean.getRegisteredDisplayStatus())));

    CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
    ServiceResult result = svc.insertMailMagazine(mailMagazine);

    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (error.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.communication.MailMagazineRegisterAction.0")));
        }
      }
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
        Messages.getString("web.action.back.communication.MailMagazineRegisterAction.0")));
    this.setRequestBean(bean);
    return super.callService();
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.MailMagazineRegisterAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106041004";
  }

}
