package jp.co.sint.webshop.web.action.front.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.CategoryAttribute;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.catalog.SearchBean;
import jp.co.sint.webshop.web.bean.front.catalog.SearchBean.CategoryAttributeListBean;

/**
 * U2040310:詳細検索のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class SearchInitAction extends WebFrontAction<SearchBean> {
  private String categoryAttibuteName; 
  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    SearchBean reqBean = getBean();

    if (!NumUtil.isNum(reqBean.getSearchPriceStart())) {
      reqBean.setSearchPriceStart("");
    }
    if (!NumUtil.isNum(reqBean.getSearchPriceEnd())) {
      reqBean.setSearchPriceEnd("");
    }

    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    reqBean.setShopList(utilService.getShopNamesDefaultAllShop(false, true));

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    if (StringUtil.isNullOrEmpty(reqBean.getSearchCategoryCode())) {
      reqBean.setSearchCategoryCode(service.getRootCategory().getCategoryCode());
    }

    List<CategoryInfo> categoryList = service.getAllCategory();

    // カテゴリツリー構築用データの生成
    // 階層:表示順:名前:PC件数-Mobile件数
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < categoryList.size(); i++) {
      stringBuilder.append(categoryList.get(i).getCategoryCode() + ":" + categoryList.get(i).getDepth() + ":"
          + utilService.getNameByLanguage(categoryList.get(i).getCategoryNamePc(),categoryList.get(i).getCategoryNamePcEn(),categoryList.get(i).getCategoryNamePcJp()) + ":" + categoryList.get(i).getCommodityCountPc() + "/");
    }
    reqBean.setCategoryList(stringBuilder.toString());
    // 10.1.2 10089 追加 ここから
    reqBean.setCategoryNodeInfoList(categoryList);
    // 10.1.2 10089 追加 ここまで
    createCategoryAttribute(reqBean.getSearchCategoryCode(), reqBean, service);

    setNextUrl(null);
    setRequestBean(reqBean);

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * @param categoryCode
   * @param reqBean
   * @param service
   */
  private void createCategoryAttribute(String categoryCode, SearchBean reqBean, CatalogService service) {
    List<CategoryAttribute> categoryAttribute = service.getCategoryAttributeList(categoryCode);
    List<CategoryAttributeListBean> categoryAttributeListBeanList = new ArrayList<CategoryAttributeListBean>();
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    int i = 0;
    for (CategoryAttribute ca : categoryAttribute) {
      CategoryAttributeListBean categoryAttributeListBean = new CategoryAttributeListBean();
      categoryAttributeListBean.setCategoryAttributeNo(NumUtil.toString(ca.getCategoryAttributeNo()));
      categoryAttibuteName=utilService.getNameByLanguage(ca.getCategoryAttributeName(), ca.getCategoryAttributeNameEn(), ca.getCategoryAttributeNameJp());
      categoryAttributeListBean.setCategoryAttributeName(categoryAttibuteName);

      if (i < reqBean.getCategoryAttributeList().size()) {
        categoryAttributeListBean.setCategoryAttributeValue(reqBean.getCategoryAttributeList().get(i).getCategoryAttributeValue());
      }

      if (StringUtil.hasValue(categoryAttibuteName)) {
        categoryAttributeListBean.setDisplayFlg(true);
      } else {
        categoryAttributeListBean.setDisplayFlg(false);
      }

      categoryAttributeListBeanList.add(categoryAttributeListBean);
      i++;
    }
    reqBean.setCategoryAttributeListBean(categoryAttributeListBeanList);
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
}
