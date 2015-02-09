package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.SiteManagementServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.TaxBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1050410:消費税マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TaxDeleteAction extends WebBackAction<TaxBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {

    // 権限のチェック
    BackLoginInfo loginInfo = getLoginInfo();

    return Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(loginInfo);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    TaxBean bean = getBean();

    String taxNo = bean.getTaxRegister().getTaxNo();

    // 削除可否チェック(初期データかどうかのチェックを行う)
    if (taxNo.equals("0")) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.DEFAULT_DELETE,
          Messages.getString("web.action.back.shop.TaxDeleteAction.0")));
      setRequestBean(bean);
      setNextUrl(null);
      return BackActionResult.RESULT_SUCCESS;
    }

    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
    ServiceResult result = service.deleteTax(NumUtil.toLong(taxNo));

    if (result.hasError()) {
      setRequestBean(bean);
      setNextUrl(null);
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(SiteManagementServiceErrorContent.NO_TAX_DATA_ERROR)) {
          addWarningMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR));
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      Logger logger = Logger.getLogger(this.getClass());
      logger.warn(result.toString());
      return BackActionResult.SERVICE_ERROR;
    } else {
      setNextUrl("/app/shop/tax/init/delete");
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
    return true;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.TaxDeleteAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105041001";
  }

}
