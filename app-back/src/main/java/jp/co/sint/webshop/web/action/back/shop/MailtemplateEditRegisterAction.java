package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.MailType;
import jp.co.sint.webshop.data.dto.MailTemplateDetail;
import jp.co.sint.webshop.data.dto.MailTemplateHeader;
import jp.co.sint.webshop.mail.MailComposition;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.shop.MailTemplateSuite;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.shop.MailtemplateEditBean;
import jp.co.sint.webshop.web.bean.back.shop.MailtemplateEditBean.MailCompositionDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1051110:メールテンプレートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class MailtemplateEditRegisterAction extends MailtemplateEditPreviewAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    LoginInfo login = getLoginInfo();
    return (Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(login) || Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(login));
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    MailtemplateEditBean bean = getBean();

    String mailType = "";
    Long templateNo;
    if (isInformationMail()) {
      mailType = MailType.INFORMATION.getValue();
      templateNo = Long.parseLong(bean.getMailType());
    } else {
      mailType = bean.getMailType();
      templateNo = 0L;
    }

    MailTemplateSuite suite = new MailTemplateSuite();

    MailTemplateHeader header = new MailTemplateHeader();
    header.setShopCode(getLoginInfo().getShopCode());
    header.setMailType(mailType);
    header.setMailTemplateNo(templateNo);
    header.setMailSubject(bean.getSubject());
    header.setMailComposition(bean.getMailComposition());
    header.setUpdatedDatetime(bean.getUpdateDatetime());
    header.setMailSenderName(bean.getSenderName());
    header.setFromAddress(bean.getFromAddress());
    header.setBccAddress(bean.getBccAddress());
    header.setMailContentType(NumUtil.toLong(bean.getContentType()));
    suite.setMailTemplateHeader(header);

    List<MailTemplateDetail> detailList = new ArrayList<MailTemplateDetail>();
    for (MailCompositionDetail detail : bean.getMailDetailList()) {
      MailTemplateDetail dto = new MailTemplateDetail();
      dto.setShopCode(getLoginInfo().getShopCode());
      dto.setMailType(mailType);
      dto.setMailTemplateNo(templateNo);
      dto.setMailTemplateBranchNo(Long.parseLong(detail.getBranchNo()));
      dto.setParentMailTemplateBranchNo(Long.parseLong(detail.getParentBranchNo()));
      dto.setMailTemplateDepth(detail.getMailTemplateDepth());
      dto.setMailText(detail.getMailText());
      // 10.1.4 10142 修正 ここから
      // dto.setMailCompositionName(detail.getMailCompositionName());
      // 情報メール、かつメール本文の場合は情報メール名称を設定する
      if (isInformationMail() && detail.getTemplateTag().equals(MailComposition.INFORMATION_MAIL_MAIN.getSubstitutionTag())) {
        dto.setMailCompositionName(bean.getNewMailTypeName());
      } else {
        dto.setMailCompositionName(detail.getMailCompositionName());
      }
      // 10.1.4 10142 修正 ここまで
      dto.setSubstitutionTag(detail.getTemplateTag());
      dto.setUpdatedDatetime(detail.getUpdateDatetime());

      detailList.add(dto);
    }
    suite.setMailTemplateDetail(detailList);

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    ServiceResult result = service.updateMailTemplateDetail(suite);

    if (result.hasError()) {
      for (ServiceErrorContent content : result.getServiceErrorList()) {
        if (content == CommonServiceErrorContent.NO_DATA_ERROR) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/shop/mailtemplate_edit/init/" + WebConstantCode.COMPLETE_REGISTER);
    setRequestBean(getBean());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 選択されたメールタイプが情報メールかどうか判定する
   * 
   * @return true-情報メール false-その他
   */
  private boolean isInformationMail() {
    return getBean().getTemplateDiv().equals("1");
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.MailtemplateEditRegisterAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105111005";
  }

}
