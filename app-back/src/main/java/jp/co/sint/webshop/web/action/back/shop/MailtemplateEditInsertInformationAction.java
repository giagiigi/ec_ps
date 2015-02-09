package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.MailtemplateEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1051110:メールテンプレートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class MailtemplateEditInsertInformationAction extends WebBackAction<MailtemplateEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    LoginInfo login = getLoginInfo();
    return Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (StringUtil.isNullOrEmpty(getBean().getNewMailTypeName())) {
      addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR,
          Messages.getString("web.action.back.shop.MailtemplateEditInsertInformationAction.0")));
      return false;
    }
    return validateItems(getBean(), "newMailTypeName");
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    ServiceResult result = service.insertInformationMail(getBean().getNewMailTypeName());

    if (result.hasError()) {
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/shop/mailtemplate_edit/init/" + WebConstantCode.COMPLETE_INSERT);
    setRequestBean(getBean());

    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.MailtemplateEditInsertInformationAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105111003";
  }

}
