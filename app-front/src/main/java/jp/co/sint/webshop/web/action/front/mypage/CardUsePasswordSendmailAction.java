package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.domain.MailSendStatus;
import jp.co.sint.webshop.data.dto.Reminder;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.PasswordUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.CardUsePasswordBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030410:パスワード再登録URL送信のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CardUsePasswordSendmailAction extends WebFrontAction<CardUsePasswordBean> {

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
    CardUsePasswordBean bean = getBean();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    FrontLoginInfo login = getLoginInfo();
    String customerCode = login.getCustomerCode();

    Reminder reminder = new Reminder();
    reminder.setReissuanceKey(PasswordUtil.getDigest(bean.getEmail() + DateUtil.getTimeStamp()));
    reminder.setCustomerCode(customerCode);
    reminder.setReissuedDatetime(DateUtil.getSysdate());
    reminder.setMailSendStatus(Long.parseLong(MailSendStatus.NOT_SENT.getValue()));

    ServiceResult serviceResult = service.insertReminder(reminder);

    if (serviceResult.hasError()) {
      for (ServiceErrorContent result : serviceResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return FrontActionResult.SERVICE_VALIDATION_ERROR;
        } else if (result.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
              .getString("web.action.front.customer.CustomerSendpasswordSendmailAction.0")));
        } else {
          return FrontActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
      setRequestBean(bean);

      return FrontActionResult.RESULT_SUCCESS;
    }

    CustomerInfo custInfo = service.getCustomer(customerCode);

    ShopManagementService sms = ServiceLocator.getShopManagementService(getLoginInfo());
    Shop shop = sms.getShop(DIContainer.getWebshopConfig().getSiteShopCode());

    MailingService mailingService = ServiceLocator.getMailingService(getLoginInfo());
    WebshopConfig config = getConfig();
    String secureUrl = WebUtil.getSecureUrl(config.getHostName(), config.getHttpsPort());
    secureUrl += getRequestParameter().getContextPath();
    mailingService.sendPaymentPasswordSendMail(custInfo.getCustomer(), reminder, shop, secureUrl, custInfo.getCustomer().getLanguageCode());

    addInformationMessage(Messages.getString("web.action.front.order.GiftCardRule.0"));
    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }
}
