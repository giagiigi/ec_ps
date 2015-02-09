package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.SmsTemplateDetail;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.shop.SmsTemplateSuite;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.shop.SmstemplateEditBean;
import jp.co.sint.webshop.web.bean.back.shop.SmstemplateEditBean.SmsCompositionDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1051110:メールテンプレートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class SmstemplateEditRegisterAction extends SmstemplateEditPreviewAction {

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
    SmstemplateEditBean bean = getBean();

    String smsType = bean.getSmsType();
    Long templateNo = Long.parseLong(bean.getSmsType());

    SmsTemplateSuite suite = new SmsTemplateSuite();

    List<SmsTemplateDetail> detailList = new ArrayList<SmsTemplateDetail>();

    SmsCompositionDetail detail = bean.getSmsDetailList().get(0);
    SmsTemplateDetail dto = new SmsTemplateDetail();
    dto.setShopCode(getLoginInfo().getShopCode());
    dto.setSmsType(smsType);
    dto.setSmsTemplateNo(templateNo);
    dto.setSmsText(detail.getSmsText());
    dto.setSmsCompositionName(detail.getSmsCompositionName());
    dto.setUpdatedDatetime(detail.getUpdateDatetime());
    dto.setSmsUseFlg(Long.parseLong(bean.getSmsUseFlgDiv()));
    dto.setTemplateTag(detail.getTemplateTag());
    detailList.add(dto);
    suite.setSmsTemplateDetail(detailList);

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    ServiceResult result = service.updateSmsTemplateDetail(suite);

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

    setNextUrl("/app/shop/smstemplate_edit/init/" + WebConstantCode.COMPLETE_REGISTER);
    setRequestBean(getBean());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.SmstemplateEditRegisterAction.0");
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
