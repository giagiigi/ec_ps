package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.dto.Tax;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.TaxBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1050410:消費税マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TaxInsertAction extends WebBackAction<TaxBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {

    // 権限のチェック
    BackLoginInfo loginInfo = getLoginInfo();
    return Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(loginInfo);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    TaxBean bean = getBean();

    // beanから値を取得
    Tax tax = new Tax();
    tax.setAppliedStartDate(DateUtil.fromString(bean.getTaxRegister().getAppliedStartDate()));
    tax.setTaxRate(Long.parseLong(bean.getTaxRegister().getTaxRate()));

    // 登録処理
    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
    ServiceResult result = service.insertTax(tax);

    // 登録処理の成功チェック
    if (result.hasError()) {
      setRequestBean(bean);
      setNextUrl(null);
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.shop.TaxInsertAction.0")));
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;

    } else {
      setNextUrl("/app/shop/tax/init/insert");
    }
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    TaxBean bean = getBean();
    return validateBean(bean.getTaxRegister());
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.TaxInsertAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105041003";
  }

}
