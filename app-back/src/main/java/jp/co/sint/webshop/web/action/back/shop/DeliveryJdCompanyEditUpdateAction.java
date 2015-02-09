package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryJdCompanyEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1051510:配送公司详细更新处理
 * 
 * @author cxw
 */
public class DeliveryJdCompanyEditUpdateAction extends WebBackAction<DeliveryJdCompanyEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {

    boolean authorization = false;
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo());
    } else {
      authorization = Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo());
    }
    return authorization;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */

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
    // 配送公司详细更新处理
    DeliveryJdCompanyEditBean editBean = getBean();

    // 取得选定的配送公司明细
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    DeliveryCompany company = service.getDeliveryCompany(getLoginInfo().getShopCode(), editBean.getDeliveryCompanyNo());

    company.setShopCode(getLoginInfo().getShopCode());
    company.setDeliveryCompanyNo(editBean.getDeliveryCompanyNo());
    company.setDeliveryCompanyName(editBean.getDeliveryCompanyName());
    company.setDeliveryCompanyUrl(editBean.getDeliveryCompanyUrl());
    company.setUseFlg(Long.parseLong(editBean.getDisplayFlg()));

    // 更新成功处理
    ServiceResult result = service.updateDeliveryCompany(company);
    if (result.hasError()) {
      for (ServiceErrorContent content : result.getServiceErrorList()) {
        if (content == CommonServiceErrorContent.NO_DATA_ERROR) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "配送公司"));
          setRequestBean(editBean);
          return BackActionResult.RESULT_SUCCESS;
        } else if (content == CommonServiceErrorContent.VALIDATION_ERROR) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    }

    // 更新后跳转处理
    setNextUrl("/app/shop/delivery_jd_company_edit/init/" + getBean().getDeliveryCompanyNo() + "/"
        + WebConstantCode.COMPLETE_UPDATE);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "配送公司详细更新处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105151005";
  }

}
