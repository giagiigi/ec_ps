package jp.co.sint.webshop.web.action.back.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.LoginLockedFlg;
import jp.co.sint.webshop.data.domain.MailType;
import jp.co.sint.webshop.data.dto.MailTemplateDetail;
import jp.co.sint.webshop.data.dto.MailTemplateHeader;
import jp.co.sint.webshop.mail.MailTemplateTag;
import jp.co.sint.webshop.mail.MailTemplateUtil;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.service.shop.MailTemplateSuite;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.InformationSendBean;
import jp.co.sint.webshop.web.bean.back.customer.InformationSendBean.MailDetail;
import jp.co.sint.webshop.web.bean.back.customer.InformationSendBean.SmsDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030610:情報メール送信のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class InformationSendBaseAction extends WebBackAction<InformationSendBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 更新権限チェック
    return Permission.CUSTOMER_UPDATE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    InformationSendBean bean = getBean();
    boolean valid = validateBean(bean);
    for (MailDetail detail : bean.getMailDetailList()) {
      if (validateBean(detail)) {
        if (valid) {
          valid = true;
        }
      } else {
        valid = false;
        break;
      }
    }
    
    //Add bY V10-CH start
    for(SmsDetail detail : bean.getSmsDetailList()){
      if (validateBean(detail)) {
        if (valid) {
          valid = true;
        }
      } else {
        valid = false;
        break;
      }
    }
    //Add by V10-CH end
    return valid;
  }

  /**
   * 検索条件を設定します。<BR>
   * 
   * @param searchBean
   * @param condition
   */
  public void setSearchCondition(InformationSendBean searchBean, CustomerSearchCondition condition) {
    condition.setSearchCustomerFrom(searchBean.getSearchCustomerFrom());
    condition.setSearchCustomerTo(searchBean.getSearchCustomerTo());
    condition.setSearchCustomerGroupCode(searchBean.getSearchCustomerGroupCode());
    condition.setSearchTel(searchBean.getSearchTel());
    //Add by V10-CH start
    condition.setSearchMobile(searchBean.getSearchMobile());
    //Add by V10-CH end
    condition.setSearchCustomerName(searchBean.getSearchCustomerName());
    condition.setSearchCustomerNameKana(searchBean.getSearchCustomerNameKana());
    condition.setSearchEmail(searchBean.getSearchEmail());
    condition.setSearchRequestMailType(searchBean.getSearchRequestMailType());
    condition.setSearchClientMailType(searchBean.getSearchClientMailType());
    condition.setSearchCustomerStatus(CustomerStatus.MEMBER.getValue());
    condition.setSearchLoginLockedFlg(LoginLockedFlg.UNLOCKED.getValue());
    // 顧客の有無の判定を行う為、maxFetchSizeを2に設定する
    condition.setMaxFetchSize(2);
  }

  /**
   * テンプレートリストを取得します。<BR>
   * 
   * @param bean
   */
  public void getTemplateList(InformationSendBean bean) {
    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());

    List<CodeAttribute> templateList = new ArrayList<CodeAttribute>();
    templateList.add(new NameValue(Messages.getString(
      "web.action.back.customer.InformationSendBaseAction.0"), ""));
    List<CodeAttribute> informationMailListDto = shopService.getInformationMailTypeList();
    templateList.addAll(informationMailListDto);

    bean.setTemplateList(templateList);
  }

  /**
   * メールテンプレートを取得します。<BR>
   * 
   * @param bean
   */
  public void getMailTemplate(InformationSendBean bean) {
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    MailTemplateSuite suite = service.getMailTemplateConfig(getLoginInfo().getShopCode(), MailType.INFORMATION.getValue(), Long
        .parseLong(bean.getTemplateCode()));
    MailTemplateHeader header = suite.getMailTemplateHeader();
    if (header == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.customer.InformationSendBaseAction.1")));
      return;
    }
    List<MailTemplateDetail> detailList = suite.getMailTemplateDetail();
    if (detailList == null || detailList.size() <= 0) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.customer.InformationSendBaseAction.1")));
      return;
    }

    List<MailDetail> cDetailList = new ArrayList<MailDetail>();
    MailDetail detail = copyDetailToComposition(detailList.get(0));
    detail.setMailCompositionName(Messages.getString("web.action.back.customer.InformationSendBaseAction.2"));
    cDetailList.add(detail);
    cDetailList.add(copyDetailToComposition(detailList.get(1)));
    bean.setMailDetailList(cDetailList);

    bean.setSubject(header.getMailSubject());
    bean.setFromAddress(header.getFromAddress());
    bean.setBccAddress(header.getBccAddress());
    bean.setContentType(Long.toString(header.getMailContentType()));
    bean.setMailComposition(header.getMailComposition());
    bean.setMailDetailList(cDetailList);
    bean.setSenderName(header.getMailSenderName());
    bean.setUpdatedDatetime(header.getUpdatedDatetime());

  }
  //Add by V10-CH start
//  public void getSmsTemplate(InformationSendBean bean){
//    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
//    SmsTemplateSuite suite = service.getSmsTemplateConfig(SmsType.ORDER_DETAILS_PC.getValue());
//    List<SmsTemplateDetail> detailList = suite.getSmsTemplateDetail();
//    if (detailList == null || detailList.size() <= 0) {
//      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
//          Messages.getString("web.action.back.customer.InformationSendBaseAction.1")));
//      return;
//    }
//    List<SmsDetail> cDetailList = new ArrayList<SmsDetail>();
//    SmsDetail detail = copyDetailToComposition(detailList.get(0));
//    detail.setSmsCompositionName(Messages.getString("web.action.back.customer.InformationSendBaseAction.2"));
//    cDetailList.add(detail);
//    cDetailList.add(copyDetailToComposition(detailList.get(1)));
//    bean.setSmsDetailList(cDetailList);
//    bean.setSmsComposition(suite.getSmsTemplateDetail().get(0).getSmsCompositionName());
//  }
  //Add by V10-CH end
  /**
   * DTOのMailTemplateDetailをBean用のMailDetailに詰め替えて返します。<BR>
   * 
   * @param detail
   * @return c
   */
  public MailDetail copyDetailToComposition(MailTemplateDetail detail) {
    MailDetail c = new MailDetail();
    c.setBranchNo(Long.toString(detail.getMailTemplateBranchNo()));
    c.setParentBranchNo(Long.toString(detail.getParentMailTemplateBranchNo()));
    c.setMailCompositionName(detail.getMailCompositionName());
    c.setTemplateTag(detail.getSubstitutionTag());
    List<CodeAttribute> tagList = new ArrayList<CodeAttribute>();
    for (MailTemplateTag tag : MailTemplateUtil.getUsableTagList(MailType.fromValue(detail.getMailType()), detail
        .getSubstitutionTag())) {
      tagList.add(tag);
    }

    if (MailTemplateUtil.getUsableTagList(MailType.fromValue(detail.getMailType()), detail.getSubstitutionTag()).size() == 0) {
      NameValue noTag = new NameValue(Messages.getString(
        "web.action.back.customer.InformationSendBaseAction.3"), "");
      tagList.add(noTag);
    }

    c.setMailTagList(tagList);
    c.setMailTemplateDepth(detail.getMailTemplateDepth());
    c.setMailText(detail.getMailText());
    c.setUpdateDatetime(detail.getUpdatedDatetime());

    return c;
  }
  //Add by V10-CH　start
//  public SmsDetail copyDetailToComposition(SmsTemplateDetail detail){
//    SmsDetail c = new SmsDetail();
//    
//    List<CodeAttribute> tagList = new ArrayList<CodeAttribute>();
//    for (SmsTemplateTag tag : SmsTemplateUtil.getUsableTagList(SmsType.fromValue(detail.getSmsType()))) {
//      tagList.add(tag);
//    }
//    if (SmsTemplateUtil.getUsableTagList(SmsType.fromValue(detail.getSmsType())).size() == 0) {
//      NameValue noTag = new NameValue(Messages.getString(
//        "web.action.back.customer.InformationSendBaseAction.3"), "");
//      tagList.add(noTag);
//    }
//    c.setSmsText(detail.getSmsText());
//    c.setSmsUseFlg(detail.getSmsUseFlg());
//    c.setUpdateDatetime(detail.getUpdatedDatetime());
//
//    return c;
//  }
//  //Add by V10-CH end
  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public abstract WebActionResult callService();
}
