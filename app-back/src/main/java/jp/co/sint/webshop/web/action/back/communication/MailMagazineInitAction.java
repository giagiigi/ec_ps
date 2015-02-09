package jp.co.sint.webshop.web.action.back.communication;

import java.util.List;

import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.MailMagazineHeadLine;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.MailMagazineBean;
import jp.co.sint.webshop.web.bean.back.communication.MailMagazineBean.MailMagazineBeanList;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060410:メールマガジンマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class MailMagazineInitAction extends WebBackAction<MailMagazineBean> {

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
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
    List<MailMagazineHeadLine> list = svc.getMailMagazineHeaderList();

    // nextBeanの作成
    MailMagazineBean nextBean = new MailMagazineBean();
    nextBean.setDisplayMode(WebConstantCode.DISPLAY_EDIT);

    for (MailMagazineHeadLine mh : list) {
      MailMagazineBeanList detail = new MailMagazineBeanList();
      detail.setMailMagazineCode(mh.getMailMagazineCode());
      detail.setTitle(mh.getMailMagazineTitle());
      detail.setDescription(mh.getMailMagazineDescription());
      detail.setDisplayStatus(this.getStatus(mh.getDisplayFlg()));
      detail.setSubscriberAmount(mh.getSubscriberAmount());
      nextBean.getList().add(detail);
    }

    this.setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 表示状態を取得します
   * 
   * @param displayFlg
   * @return 表示状態（表示/非表示）
   */
  public String getStatus(Long displayFlg) {
    String status = "";
    if (displayFlg.equals(0L)) {
      status = Messages.getString("web.action.back.communication.MailMagazineInitAction.1");
    } else {
      status = Messages.getString("web.action.back.communication.MailMagazineInitAction.2");
    }
    return status;
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
    return Messages.getString("web.action.back.communication.MailMagazineInitAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106041003";
  }

}
