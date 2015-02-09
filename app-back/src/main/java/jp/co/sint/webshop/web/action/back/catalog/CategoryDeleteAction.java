package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.CatalogQuery;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CategoryBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1040210:カテゴリのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CategoryDeleteAction extends WebBackAction<CategoryBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    Logger logger = Logger.getLogger(this.getClass());
    CategoryBean reqBean = getBean();

    // 削除対象カテゴリコードの存在チェック
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    Category category = service.getCategory(reqBean.getEdit().getCategoryCode());

    if (category == null) {
      setRequestBean(reqBean);
      setNextUrl("/app/catalog/category/init/delete");
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // ルートカテゴリを削除しようとした場合
    Category rootCategory = service.getRootCategory();
    if (reqBean.getEdit().getCategoryCode().equals(rootCategory.getCategoryCode())) {
      logger.error(CatalogErrorMessage.ROOT_CATEGORY_DELETE_ERROR);
      addWarningMessage(WebMessage.get(CatalogErrorMessage.ROOT_CATEGORY_DELETE_ERROR));
      setNextUrl(null);
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 削除対象カテゴリの特定
    List<Category> deleteCategoryList = service.getCategoryListFromPath(reqBean.getEdit().getCategoryCode(), category.getPath()
        + CatalogQuery.CATEGORY_DELIMITER + reqBean.getEdit().getCategoryCode());

    List<String> categoryCodeList = new ArrayList<String>();

    for (Category ct : deleteCategoryList) {
      categoryCodeList.add(ct.getCategoryCode());
    }

    // 削除サービスの実行
    ServiceResult result = service.deleteCategory(categoryCodeList);

    if (result.hasError()) {
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.catalog.CategoryDeleteAction.0")));
        } else if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.catalog.CategoryDeleteAction.0")));
        } else if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    } else {
      setNextUrl("/app/catalog/category/init/delete");
    }
    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return login.isSite() && Permission.CATEGORY_DELETE.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    CategoryBean reqBean = (CategoryBean) getBean();
    return validateItems(reqBean.getEdit(), "categoryCode");
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CategoryDeleteAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104021001";
  }

}
