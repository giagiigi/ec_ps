package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CategoryBean;
import jp.co.sint.webshop.web.bean.back.catalog.CategoryBean.CategoryAttributeBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040210:カテゴリのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CategoryDeleteAttributeAction extends WebBackAction<CategoryBean> {

  private String categoryAttributeNo;

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CategoryBean reqBean = getBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    ServiceResult result = service.deleteCategoryAttributeValue(reqBean.getEdit().getCategoryCode(), NumUtil
        .toLong(categoryAttributeNo));

    if (result.hasError()) {
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.catalog.CategoryDeleteAttributeAction.0")));
        } else if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.catalog.CategoryDeleteAttributeAction.0")));
        } else if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    } else {
      setNextUrl("/app/catalog/category/select/" + reqBean.getEdit().getCategoryCode()
          + "/delete");
    }
    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = false;

    // Bean内のカテゴリコードのValidationチェック
    CategoryBean reqBean = getBean();
    result = validateItems(reqBean.getEdit(), "categoryCode");

    // URLパラメータのカテゴリ属性のValidationチェック
    CategoryAttributeBean validateBean = new CategoryAttributeBean();
    String[] param = getRequestParameter().getPathArgs();
    if (param.length > 0 && result) {
      categoryAttributeNo = param[0];
      validateBean.setCategoryAttributeNo(categoryAttributeNo);
      result = validateItems(validateBean, "categoryAttributeNo");
    } else {
      result = false;
    }

    return result;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().isSite() && Permission.CATEGORY_DELETE.isGranted(getLoginInfo());
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CategoryDeleteAttributeAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104021002";
  }

}
