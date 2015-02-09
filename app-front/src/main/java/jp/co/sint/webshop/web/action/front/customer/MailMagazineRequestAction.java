package jp.co.sint.webshop.web.action.front.customer;

import java.text.MessageFormat;
import java.util.Date;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.mail.MailInfo;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.MailMagazineConfig;
import jp.co.sint.webshop.utility.PasswordUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.customer.MailMagazineBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageDisplayMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2060420:メールマガジン登録のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class MailMagazineRequestAction extends MailMagazineBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = super.validate();
    MailMagazineBean bean = getBean();
    result &= validateBean(bean);

    // チェックボックスが選択されているか
    String[] values = getRequestParameter().getAll("checkBox");
    if (!StringUtil.hasValueAnyOf(values)) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED, Messages
          .getString("web.action.front.customer.MailMagazineRequestAction.0")));
      result &= false;
    }

    // URLパラメータのチェック
    if (getRequestParameter().getPathArgs().length == 0) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      result &= false;
    }
    return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String[] values = getRequestParameter().getAll("checkBox"); // メルマガコード取得
    String email = getBean().getEmail(); // メルアド取得
    Date requestedDate = DateUtil.getSysdate(); // 登録依頼受付日時取得

    // 暗号化対象文字列生成
    String mailMagazineCodeList = "";
    String delimiter = "";
    for (int i = 0; i < values.length; i++) {
      mailMagazineCodeList += delimiter + values[i];
      delimiter = ",";
    }
    String target = mailMagazineCodeList + ";" + email + ";" + DateUtil.toDateTimeString(requestedDate);

    // 暗号化
    String encrypt = PasswordUtil.encrypt(target);

    // 登録Action呼び出しURLに生成した暗号を加える
    String actionName = getRequestParameter().getPathArgs()[0];
    WebshopConfig config = getConfig();
    String url = WebUtil.getSecureUrl(config.getHostName(), config.getHttpsPort());
    url += getRequestParameter().getContextPath();
    url += "/app/customer/mail_magazine/" + actionName + "/" + encrypt;

    // メール送信
    MailingService svc = ServiceLocator.getMailingService(getLoginInfo());
    MailInfo info = setMailInfo(email, url, actionName);
    ServiceResult result = svc.sendImmediate(info);

    String message = "";
    if (actionName.equals("register")) {
      message = Messages.getString("web.action.front.customer.MailMagazineRequestAction.1");
    } else if (actionName.equals("cancel")) {
      message = Messages.getString("web.action.front.customer.MailMagazineRequestAction.2");
    }

    // エラー処理
    if (result.hasError()) {
      addErrorMessage(WebMessage.get(MypageErrorMessage.MAILMAGAZINE_SENDMAIL_ERROR, message));
      setRequestBean(getBean());
      return FrontActionResult.RESULT_SUCCESS;
    }

    String[] messages = {
        message, message
    };
    addInformationMessage(WebMessage.get(MypageDisplayMessage.MAILMAGAZINE_SENDMAIL_COMPLETE, messages));
    setRequestBean(getBean());
    return FrontActionResult.RESULT_SUCCESS;

  }

  // メール送信情報作成
  private MailInfo setMailInfo(String email, String url, String actionName) {
    UtilService service = ServiceLocator.getUtilService(getLoginInfo());
    MailMagazineConfig config = service.getMailMagazineConfig();

    MailInfo info = new MailInfo();
    info.setFromInfo(config.getFromInfo());
    info.setSendDate(DateUtil.getSysdate());
    info.setContentType(MailInfo.CONTENT_TYPE_HTML);
    info.addToList(email);

    // 件名、本文作成
    String subject = "";
    String text = "";

    // サイト情報取得
    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    Shop shop = shopService.getShop(DIContainer.getWebshopConfig().getSiteShopCode());
    String siteName = shop.getShopName();

    if (actionName.equals("register")) {
      subject = config.getRegisterMailMagazineTitle();
      text = MessageFormat.format(config.getRegisterMailMagazineText(), email, siteName, url);
    } else if (actionName.equals("cancel")) {
      subject = config.getCancelMailMagazineTitle();
      text = MessageFormat.format(config.getCancelMailMagazineText(), email, siteName, url);
    }

    // 署名作成
    String[] signInfo = {
        siteName, shop.getPostalCode(),
        shop.getAddress1() + shop.getAddress2() + shop.getAddress3() + StringUtil.coalesce(shop.getAddress4(), ""),
        shop.getPhoneNumber(),shop.getMobileNumber(), shop.getEmail()
    };
    text += MessageFormat.format(config.getSign(), (Object[]) signInfo);

    info.setSubject(subject);
    info.setText(text);

    return info;
  }

}
