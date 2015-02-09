package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CategoryBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedCategoryBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1040210:カテゴリのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CategoryMoveAction extends WebBackAction<CategoryBean> {

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return true;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    CategoryBean reqBean = getBean();
    return validateItems(reqBean.getEdit(), "categoryCode");
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CategoryBean reqBean = getBean();

    // カテゴリ存在チェック
    CatalogService catalogSv = ServiceLocator.getCatalogService(getLoginInfo());
    if (catalogSv.getCategory(reqBean.getEdit().getCategoryCode()) == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.catalog.CategoryMoveAction.0")));
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    }

    RelatedCategoryBean relatedCategoryBean = new RelatedCategoryBean();
    relatedCategoryBean.setCategoryCode(reqBean.getEdit().getCategoryCode());

    DisplayTransition.add(getBean(), "/app/catalog/category/select/"
        + reqBean.getEdit().getCategoryCode(), getSessionContainer());

    setNextUrl("/app/catalog/related_category/init");
    setRequestBean(relatedCategoryBean);
    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CategoryMoveAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104021004";
  }

}
