package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CategoryAttributeHeader;
import jp.co.sint.webshop.service.catalog.RelatedCategory;
import jp.co.sint.webshop.service.catalog.RelatedCategorySearchCondition;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedCategoryBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedCategoryBean.CategoryAttributeValueBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedCategoryBean.RelatedCategoryDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040220:カテゴリ－商品関連付けのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedCategorySelectAction extends RelatedCategoryBaseAction {

  private String commodityCode;

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String[] urlParameter = getRequestParameter().getPathArgs();
    if (urlParameter.length == 1) {
      commodityCode = urlParameter[0];
    } else {
      commodityCode = "";
    }

    RelatedCategoryBean reqBean = getBean();

    if (getLoginInfo().isShop() || getConfig().isOne()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    // 検索条件の設定
    RelatedCategorySearchCondition condition = setSearchCondition(reqBean);
    condition = PagerUtil.createSearchConditionFromBean(reqBean, condition);

    // カテゴリ名の取得
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    Category category = service.getCategory(reqBean.getCategoryCode());

    if (category == null) {
      setRequestBean(reqBean);
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.catalog.RelatedCategorySelectAction.0")));
      return BackActionResult.RESULT_SUCCESS;
    }

    reqBean.setCategoryName(category.getCategoryNamePc());

    // カテゴリ属性名、入力項目の設定
    setCategoryAttributeList(reqBean);

    // ヘッダーとなる情報(カテゴリ属性値以外)を取得する
    SearchResult<CategoryAttributeHeader> searchResult = service.getCategoryAttributeValueHeader(condition);
    List<CategoryAttributeHeader> categoryAttributeHeader = searchResult.getRows();

    // ページ情報を追加
    reqBean.setPagerValue(PagerUtil.createValue(searchResult));

    // 画面表示用Beanの定義
    List<RelatedCategoryDetailBean> nameList = new ArrayList<RelatedCategoryDetailBean>();

    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());

    // 取得したヘッダ情報を元に、商品コードもろもろをキーとしてカテゴリ属性値を取得し設定
    int checkedCode = 0;
    for (CategoryAttributeHeader ca : categoryAttributeHeader) {

      // ヘッダ情報を画面表示用Beanに設定
      RelatedCategoryDetailBean name = new RelatedCategoryDetailBean();
      name.setCheckedCode(String.valueOf(checkedCode));
      name.setCommodityCode(ca.getCommodityCode());
      name.setCommodityName(ca.getCommodityName());
      name.setShopCode(ca.getShopCode());
      name.setShopName(shopService.getShop(ca.getShopCode()).getShopName());

      // 選択された商品のヘッダ情報を入力項目に設定
      if (ca.getCommodityCode().equals(commodityCode)) {
        reqBean.getEdit().setShopCode(reqBean.getSearchShopCode());
        reqBean.getEdit().setCommodityCode(ca.getCommodityCode());
        reqBean.getEdit().setCommodityName(ca.getCommodityName());
        reqBean.getEdit().setShopCode(ca.getShopCode());
      }

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
          // DB内に必要なレコードが存在した場合、取得した情報をそのまま設定
          RelatedCategory relatedCategory = relatedCategoryList.get(j);
          value.setCategoryAttributeValue(relatedCategory.getCategoryAttributeValue());
        //add by cs_yuli 20120608 start
          value.setCategoryAttributeValueEn(relatedCategory.getCategoryAttributeValueEn());
          value.setCategoryAttributeValueJp(relatedCategory.getCategoryAttributeValueJp());
        //add by cs_yuli 20120608 end
          value.setUpdatedDatetime(relatedCategory.getUpdatedDatetime());

          // 選択された商品の明細情報を入力項目に設定
          if (relatedCategory.getCommodityCode().equals(commodityCode)) {
            CategoryAttributeValueBean editDetail = new CategoryAttributeValueBean();
            editDetail.setCategoryAttributeNo(NumUtil.toString(relatedCategory.getCategoryAttributeNo()));
            editDetail.setCategoryAttributeValue(relatedCategory.getCategoryAttributeValue());
            //add by cs_yuli 20120608 start
            editDetail.setCategoryAttributeValueEn(relatedCategory.getCategoryAttributeValueEn());
            editDetail.setCategoryAttributeValueJp(relatedCategory.getCategoryAttributeValueJp());
            //add by cs_yuli 20120608 end
            editDetail.setUpdatedDatetime(relatedCategory.getUpdatedDatetime());
            reqBean.getEdit().getAttributeList().set(i, editDetail);
          }
          j = j + 1;

        }
        valueList.add(value);
      }
      name.setAttributeList(valueList);
      nameList.add(name);
      checkedCode = checkedCode + 1;
    }

    // リスト用情報を画面Beanに設定
    reqBean.setList(nameList);

    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    reqBean.setShopListEdit(utilService.getShopNames(false));

    setRequestBean(reqBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    RelatedCategoryBean reqBean = (RelatedCategoryBean) getRequestBean();
    reqBean.setMode(WebConstantCode.DISPLAY_HIDDEN);

    setDisplayControl(reqBean);

    setRequestBean(reqBean);
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo()) || Permission.CATEGORY_UPDATE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    RelatedCategoryBean reqBean = getBean();
    return validateBean(reqBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.RelatedCategorySelectAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104022005";
  }

}
