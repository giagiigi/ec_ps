package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.catalog.CategoryAttributeHeader;
import jp.co.sint.webshop.service.catalog.RelatedCategory;
import jp.co.sint.webshop.service.catalog.RelatedCategorySearchCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedCategoryBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedCategoryBean.CategoryAttributeValueBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedCategoryBean.RelatedCategoryDetailBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040220:カテゴリ－商品関連付けのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedCategorySearchAction extends RelatedCategoryBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.CATEGORY_READ.isGranted(getLoginInfo()) || Permission.COMMODITY_READ.isGranted(getLoginInfo());
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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    RelatedCategoryBean reqBean = getBean();

    reqBean.getList().clear();

    BackLoginInfo login = getLoginInfo();

    if (login.isShop() || getConfig().isOne()) {
      reqBean.setSearchShopCode(login.getShopCode());
    }

    // カテゴリ名の取得
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    Category category = service.getCategory(reqBean.getCategoryCode());
    reqBean.setCategoryName(category.getCategoryNamePc());

    // カテゴリ属性名、入力項目の設定
    setCategoryAttributeList(reqBean);

    // 検索条件の設定
    RelatedCategorySearchCondition condition = setSearchCondition(reqBean);
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // 検索条件を元に一覧の値を取得する
    SearchResult<CategoryAttributeHeader> searchResult = service.getCategoryAttributeValueHeader(condition);
    List<CategoryAttributeHeader> categoryAttributeHeader = searchResult.getRows();

    // ページ情報を追加
    reqBean.setPagerValue(PagerUtil.createValue(searchResult));

    // 画面表示用Beanの定義
    List<RelatedCategoryDetailBean> nameList = new ArrayList<RelatedCategoryDetailBean>();

    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());

    // 取得したヘッダ情報を元に、カテゴリ属性値を取得して設定
    int checkedCode = 0;
    for (CategoryAttributeHeader ca : categoryAttributeHeader) {

      // ヘッダ情報を画面表示用Beanに設定
      RelatedCategoryDetailBean name = new RelatedCategoryDetailBean();
      name.setCheckedCode(String.valueOf(checkedCode));
      name.setCommodityCode(ca.getCommodityCode());
      name.setCommodityName(ca.getCommodityName());
      name.setShopCode(ca.getShopCode());
      name.setShopName(shopService.getShop(ca.getShopCode()).getShopName());

      // 商品コードに関連付いているカテゴリ属性値を取得
      List<RelatedCategory> relatedCategoryList = service.getCategoryAttributeValueList(ca.getShopCode(),
          reqBean.getCategoryCode(), ca.getCommodityCode());
      List<CategoryAttributeValueBean> valueList = new ArrayList<CategoryAttributeValueBean>();

      // 取得したカテゴリ属性値を画面表示用Beanに設定
      int j = 0;
      for (int i = 0; i < reqBean.getAttributeList().size(); i++) {
        CategoryAttributeValueBean value = new CategoryAttributeValueBean();
        value.setCategoryAttributeNo(String.valueOf(i));
        if (j < relatedCategoryList.size() && relatedCategoryList.get(j).getCategoryAttributeNo().equals(Long.valueOf(i))) {
          RelatedCategory relatedCategory = relatedCategoryList.get(j);
          value.setCategoryAttributeValue(relatedCategory.getCategoryAttributeValue());
          value.setCategoryAttributeValueEn(relatedCategory.getCategoryAttributeValueEn());
          value.setCategoryAttributeValueJp(relatedCategory.getCategoryAttributeValueJp());
          value.setUpdatedDatetime(relatedCategory.getUpdatedDatetime());
          j = j + 1;
        }
        valueList.add(value);
      }

      name.setAttributeList(valueList);
      nameList.add(name);
      checkedCode = checkedCode + 1;
    }

    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(searchResult, SearchWarningType.BOTH);

    if (!nameList.isEmpty()) {
      // リスト用情報を画面Beanに設定
      reqBean.setList(nameList);
    }

    setRequestBean(reqBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    RelatedCategoryBean reqBean = (RelatedCategoryBean) getRequestBean();
    reqBean.setMode(WebConstantCode.DISPLAY_EDIT);

    setDisplayControl(reqBean);

    setRequestBean(reqBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.RelatedCategorySearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104022004";
  }

}
