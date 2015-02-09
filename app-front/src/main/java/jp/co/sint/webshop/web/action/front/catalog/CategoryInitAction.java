package jp.co.sint.webshop.web.action.front.catalog;

import jp.co.sint.webshop.data.dto.Category; // 10.1.6 10262 追加
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.data.ContentsSearchCondition;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.catalog.CategoryBean;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityListBean;

/**
 * U2040210:カテゴリトップのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CategoryInitAction extends WebFrontAction<CategoryBean> {

  private String categoryCode;

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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CategoryBean reqBean = getBean();
    categoryCode = reqBean.getCategoryCode();

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    // 10.1.6 10262 修正 ここから
    // reqBean.setCategoryName(service.getCategory(categoryCode).getCategoryNamePc());
    Category category = service.getCategory(categoryCode);
    // 10.1.6 10262 修正 ここまで

    ContentsSearchCondition condition = new ContentsSearchCondition();
    condition.setContentsType(ContentsType.CONTENT_SITE_CATEGORY);
    condition.setCategoryCode(categoryCode);
    UtilService utilService=ServiceLocator.getUtilService(getLoginInfo());
    DataIOService dataIOService = ServiceLocator.getDataIOService(getLoginInfo());

    // 10.1.6 10262 修正 ここから
    // if (dataIOService.contentsExists(condition)) {
    if (category != null && dataIOService.contentsExists(condition)) {
    // 10.1.6 10262 修正 ここまで
      reqBean.setContentsUrl(dataIOService.getContentsUrl(condition));
      // 10.1.6 10262 追加 ここから
      reqBean.setCategoryName(utilService.getNameByLanguage(category.getCategoryNamePc(),category.getCategoryNamePcEn(),category.getCategoryNamePcJp()));
      // 10.1.6 10262 追加 ここまで
      // 20120305 shen add start
      reqBean.setMetaKeyword(category.getMetaKeyword());
      reqBean.setMetaDescription(category.getMetaDescription());
      // 20120305 shen add end
      setRequestBean(reqBean);
    } else {
      // カテゴリトップページが存在しない場合は、取得したカテゴリコードで絞り込んだ商品一覧を表示
      CommodityListBean nextBean = new CommodityListBean();
      nextBean.setSearchCategoryCode(categoryCode);
      setRequestBean(nextBean);
      setNextUrl("/app/catalog/list/init" + nextBean.toQueryString());
    }

    return FrontActionResult.RESULT_SUCCESS;
  }
}
