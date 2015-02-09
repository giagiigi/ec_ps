package jp.co.sint.webshop.web.action.back.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.MailType;
import jp.co.sint.webshop.data.dto.MailTemplateDetail;
import jp.co.sint.webshop.data.dto.MailTemplateHeader;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.shop.MailTemplateSuite;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.InformationSendBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.customer.CustomerErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030610:情報メール送信のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class InformationSendMailsendAction extends InformationSendBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    InformationSendBean searchBean = getBean();

    // 顧客検索条件を取得
    CustomerSearchCondition condition = new CustomerSearchCondition();
    setSearchCondition(searchBean, condition);

    MailTemplateSuite mailTemplateSuite = new MailTemplateSuite();
    MailTemplateHeader mailTemplateHeader = new MailTemplateHeader();
    MailTemplateDetail mailTemplateDetail = new MailTemplateDetail();

    mailTemplateHeader.setMailType(MailType.INFORMATION.getValue());
    mailTemplateHeader.setMailTemplateNo(Long.parseLong(searchBean.getTemplateCode()));
    mailTemplateHeader.setMailSubject(searchBean.getSubject());
    mailTemplateHeader.setMailSenderName(searchBean.getSenderName());
    mailTemplateHeader.setFromAddress(searchBean.getFromAddress());
    mailTemplateHeader.setBccAddress(searchBean.getBccAddress());
    mailTemplateHeader.setMailContentType(Long.parseLong(searchBean.getContentType()));

    List<MailTemplateDetail> mailTemplateDetailList = new ArrayList<MailTemplateDetail>();
    mailTemplateDetail.setMailText(searchBean.getMailDetailList().get(0).getMailText());
    mailTemplateDetailList.add(mailTemplateDetail);

    MailTemplateDetail signDetail = new MailTemplateDetail();
    signDetail.setMailText(searchBean.getMailDetailList().get(1).getMailText());
    mailTemplateDetailList.add(signDetail);

    mailTemplateSuite.setMailTemplateHeader(mailTemplateHeader);
    mailTemplateSuite.setMailTemplateDetail(mailTemplateDetailList);

    MailingService service = ServiceLocator.getMailingService(getLoginInfo());

    ServiceResult serviceResult = service.sendInformationMail(condition, mailTemplateSuite);
    if (serviceResult.hasError()) {
      for (ServiceErrorContent result : serviceResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          // service内部Validationエラー
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(CustomerErrorMessage.INFORMATION_MAIL_NO_DATA));
          setNextUrl(null);

          setRequestBean(searchBean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    } else {
      setNextUrl("/app/customer/information_send/init/send_mail");
    }

    setRequestBean(searchBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.InformationSendMailsendAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103061004";
  }

}
