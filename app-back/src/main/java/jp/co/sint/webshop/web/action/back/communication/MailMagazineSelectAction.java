package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.MailMagazine;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.MailMagazineBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060410:メールマガジンマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class MailMagazineSelectAction extends WebBackAction<MailMagazineBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.MAIL_MAGAZINE_READ_SITE.isGranted(getLoginInfo());
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
    MailMagazine mailMagazine = svc.getMailMagazine(path[0]);

    // メールマガジンの存在チェック
    if (mailMagazine == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.communication.MailMagazineSelectAction.0")));
      this.setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    // nextBeanの作成
    MailMagazineBean nextBean = new MailMagazineBean();
    nextBean.setRegisteredMailMagazineCode(mailMagazine.getMailMagazineCode());
    nextBean.setRegisteredTitle(mailMagazine.getMailMagazineTitle());
    nextBean.setRegisteredDescription(mailMagazine.getMailMagazineDescription());
    nextBean.setRegisteredDisplayStatus(Long.toString(mailMagazine.getDisplayFlg()));
    nextBean.setUpdateDatetime(mailMagazine.getUpdatedDatetime());
    nextBean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
    nextBean.setList(getBean().getList());

    this.setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    // ボタン表示制御
    MailMagazineBean bean = (MailMagazineBean) getRequestBean();
    bean.setDeleteDisplayFlg(Permission.MAIL_MAGAZINE_DELETE_SITE.isGranted(getLoginInfo()));
    bean.setRegisterDisplayFlg(Permission.MAIL_MAGAZINE_UPDATE_SITE.isGranted(getLoginInfo()));
    bean.setExportDisplayFlg(Permission.MAIL_MAGAZINE_IO_SITE.isGranted(getLoginInfo()));
    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.MailMagazineSelectAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106041005";
  }

}
