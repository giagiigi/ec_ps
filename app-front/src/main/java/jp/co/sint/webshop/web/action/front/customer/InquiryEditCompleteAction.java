package jp.co.sint.webshop.web.action.front.customer;

import java.io.PrintWriter;
import java.io.StringWriter;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.mail.MailInfo;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.ExPrintWriter;
import jp.co.sint.webshop.utility.InquiryConfig;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.TransactionTokenAction;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.customer.InquiryEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2060410:お問い合わせ入力のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
// 10.1.4 10195 修正 ここから
// public class InquiryEditCompleteAction extends WebFrontAction<InquiryEditBean> {
public class InquiryEditCompleteAction extends WebFrontAction<InquiryEditBean> implements TransactionTokenAction {
// 10.1.4 10195 修正 ここまで

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

    InquiryEditBean bean = getBean();

    // メール送信情報作成
    MailInfo info = new MailInfo();

    String subject = bean.getCustomerInquirySubjectList().get(Integer.parseInt(bean.getCustomerInquirySubject())).getName();
    info.setSubject(subject);
    info.setFromInfo(bean.getEmail());
    info.setSendDate(DateUtil.getSysdate());

    info.setContentType(MailInfo.CONTENT_TYPE_HTML);

    // 『ショップ個別モードで「注文・予約について」の問い合わせ』、または
    // 『モール一括、またはショップ個別モードで「商品について」の問い合わせ』の場合は、メールの送信先をショップ管理者とする
    boolean mallMode = getConfig().getOperatingMode().equals(OperatingMode.MALL);
    boolean shopMode = getConfig().getOperatingMode().equals(OperatingMode.SHOP);
    if ((shopMode && bean.isOrderInquiryMode()) || ((mallMode || shopMode) && bean.isCommodityInquiryMode())) {
      ShopManagementService shopSvc = ServiceLocator.getShopManagementService(getLoginInfo());
      Shop shop = shopSvc.getShop(bean.getShopCode());
      info.addToList(shop.getEmail());
    } else {
      // 設定ファイルから送信先メールアドレスを取得
      UtilService service = ServiceLocator.getUtilService(getLoginInfo());
      InquiryConfig inquiry = service.getInquiryConfig();
      info.addToList(inquiry.getToAddress());
      info.addCcList(inquiry.getCcAddress());
      info.addBccList(inquiry.getBccAddress());
    }

    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);

    if (StringUtil.hasValue(bean.getCustomerCode())) {
      out.println(Messages.getString(
      "web.action.front.customer.InquiryEditCompleteAction.0") + bean.getCustomerCode());
    }
    out.println(Messages.getString("web.action.front.customer.InquiryEditCompleteAction.1") + bean.getCustomerName());
    if (bean.isCommodityInquiryMode()) {
      if (mallMode || shopMode) {
        out.println(Messages.getString("web.action.front.customer.InquiryEditCompleteAction.2") + bean.getShopCode());
        out.println(Messages.getString("web.action.front.customer.InquiryEditCompleteAction.3") + bean.getShopName());

      }
      out.println(Messages.getString("web.action.front.customer.InquiryEditCompleteAction.4") + bean.getCommodityCode());
      out.println(Messages.getString("web.action.front.customer.InquiryEditCompleteAction.5") + bean.getCommodityName());
    } else if (bean.isOrderInquiryMode()) {
      if (shopMode) {
        out.println(Messages.getString("web.action.front.customer.InquiryEditCompleteAction.2") + bean.getShopCode());
        out.println(Messages.getString("web.action.front.customer.InquiryEditCompleteAction.3") + bean.getShopName());
      }
      out.println(Messages.getString("web.action.front.customer.InquiryEditCompleteAction.6") + bean.getOrderNo());
    }
    out.println(bean.getInquiryDetail());
    info.setText(s.toString());

    // メール送信処理
    MailingService svc = ServiceLocator.getMailingService(getLoginInfo());
    ServiceResult result = svc.sendImmediate(info);

    if (result.hasError()) {
      addErrorMessage(WebMessage.get(MypageErrorMessage.INQUIRY_ERROR));
      return FrontActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/customer/inquiry_edit/init/complete");

    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }
}
