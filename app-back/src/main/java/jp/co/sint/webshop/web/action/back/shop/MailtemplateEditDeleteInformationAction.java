package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.MailType;
import jp.co.sint.webshop.data.dto.MailTemplateDetail;
import jp.co.sint.webshop.data.dto.MailTemplateHeader;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.shop.MailTemplateSuite;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.MailtemplateEditBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1051110:メールテンプレートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class MailtemplateEditDeleteInformationAction extends WebBackAction<MailtemplateEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    LoginInfo login = getLoginInfo();
    return Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return validateItems(getBean(), "mailType");
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    MailTemplateSuite deleteSuite = new MailTemplateSuite();
    MailTemplateHeader header = new MailTemplateHeader();
    header.setShopCode(getConfig().getSiteShopCode());
    header.setMailType(MailType.INFORMATION.getValue());
    header.setMailTemplateNo(Long.parseLong(getBean().getMailType()));
    deleteSuite.setMailTemplateHeader(header);

    List<MailTemplateDetail> detailList = new ArrayList<MailTemplateDetail>();
    MailTemplateDetail mainDetail = new MailTemplateDetail();
    mainDetail.setShopCode(getConfig().getSiteShopCode());
    mainDetail.setMailType(MailType.INFORMATION.getValue());
    mainDetail.setMailTemplateNo(Long.parseLong(getBean().getMailType()));
    mainDetail.setMailTemplateBranchNo(1L);
    detailList.add(mainDetail);
    MailTemplateDetail signDetail = new MailTemplateDetail();
    signDetail.setShopCode(getConfig().getSiteShopCode());
    signDetail.setMailType(MailType.INFORMATION.getValue());
    signDetail.setMailTemplateNo(Long.parseLong(getBean().getMailType()));
    signDetail.setMailTemplateBranchNo(2L);
    detailList.add(signDetail);
    deleteSuite.setMailTemplateDetail(detailList);

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    ServiceResult result = service.deleteMailTemplate(deleteSuite);

    if (result.hasError()) {
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/shop/mailtemplate_edit/init/" + WebConstantCode.COMPLETE_DELETE);
    setRequestBean(getBean());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.MailtemplateEditDeleteInformationAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105111001";
  }

}
