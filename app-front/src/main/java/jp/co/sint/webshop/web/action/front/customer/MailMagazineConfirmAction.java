package jp.co.sint.webshop.web.action.front.customer;

import java.text.MessageFormat;
import java.util.List;

import jp.co.sint.webshop.data.dto.MailMagazine;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.mail.MailInfo;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.MailMagazineConfig;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.CompleteMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2060420:メールマガジン登録のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class MailMagazineConfirmAction extends MailMagazineBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return super.validate() && validateBean(getBean());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String email = getBean().getEmail();

    // 登録済みメールマガジン一覧の取得
    CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
    List<MailMagazine> mailMagazineList = svc.getSubscribersMailMagazineList(email);

    // メール送信
    MailingService mailSvc = ServiceLocator.getMailingService(getLoginInfo());
    MailInfo info = setMailInfo(email, mailMagazineList);
    ServiceResult mailResult = mailSvc.sendImmediate(info);

    // エラー処理
    if (mailResult.hasError()) {
      addErrorMessage(WebMessage.get(MypageErrorMessage.MAILMAGAZINE_SENDMAIL_ERROR, Messages
          .getString("web.action.front.customer.MailMagazineConfirmAction.0")));
      setRequestBean(getBean());
      return FrontActionResult.RESULT_SUCCESS;
    }

    // 正常終了メッセージ
    addInformationMessage(WebMessage.get(CompleteMessage.SENDMAIL_COMPLETE, Messages
        .getString("web.action.front.customer.MailMagazineConfirmAction.0")));

    setRequestBean(getBean());
    return FrontActionResult.RESULT_SUCCESS;

  }

  // メール送信情報作成
  private MailInfo setMailInfo(String email, List<MailMagazine> mailMagazineList) {
    UtilService service = ServiceLocator.getUtilService(getLoginInfo());
    MailMagazineConfig config = service.getMailMagazineConfig();

    MailInfo info = new MailInfo();
    info.setSubject(config.getConfirmMailMagazineTitle());
    info.setFromInfo(config.getFromInfo());
    info.setSendDate(DateUtil.getSysdate());
    info.setContentType(MailInfo.CONTENT_TYPE_HTML);
    info.addToList(email);

    // 本文作成
    String text = "";

    // サイト情報取得
    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    Shop shop = shopService.getShop(DIContainer.getWebshopConfig().getSiteShopCode());
    String siteName = shop.getShopName();

    if (mailMagazineList.size() > 0) {
      String list = "";
      for (MailMagazine m : mailMagazineList) {
        list += "・『" + m.getMailMagazineTitle() + "』\n";
      }
      text = MessageFormat.format(config.getConfirmMailMagazineText(), email, siteName, list);
    } else {
      text = MessageFormat.format(config.getNoExistMailMagazineText(), email, siteName);
    }
    // 署名作成
    String[] signInfo = {
        siteName, shop.getPostalCode(),
        shop.getAddress1() + shop.getAddress2() + shop.getAddress3() + StringUtil.coalesce(shop.getAddress4(), ""),
        shop.getPhoneNumber(),shop.getMobileNumber(), shop.getEmail()
    };
    text += MessageFormat.format(config.getSign(), (Object[]) signInfo);
    info.setText(text);
    return info;
  }
}
