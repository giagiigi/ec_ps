package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.dto.CategoryAttribute;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.RelatedCategorySearchCondition;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedCategoryBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedCategoryBean.CategoryAttributeBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedCategoryBean.CategoryAttributeValueBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedCategoryBean.RelatedCategoryDetailBean;

/**
 * U1040220:カテゴリ－商品関連付けのアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class RelatedCategoryBaseAction extends WebBackAction<RelatedCategoryBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public abstract WebActionResult callService();

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public abstract boolean authorize();

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
   * @param reqBean
   *          カテゴリ関連付けBean
   * @return カテゴリ情報
   */
  public RelatedCategorySearchCondition setSearchCondition(RelatedCategoryBean reqBean) {
    RelatedCategorySearchCondition condition = new RelatedCategorySearchCondition();
    condition.setCategoryCode(reqBean.getCategoryCode());
    condition.setSearchCommodityCodeStart(reqBean.getSearchCommodityCodeStart());
    condition.setSearchCommodityCodeEnd(reqBean.getSearchCommodityCodeEnd());
    condition.setSearchCommodityName(reqBean.getSearchCommodityName());
    condition.setShopCode(reqBean.getSearchShopCode());
    return condition;
  }

  /**
   * @param reqBean
   *          カテゴリ関連付けBean
   */
  public void setCategoryAttributeList(RelatedCategoryBean reqBean) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    List<CategoryAttribute> categoryAttributeList = service.getCategoryAttributeList(reqBean.getCategoryCode());

    // 入力項目欄の情報を設定
    List<CategoryAttributeBean> attributeList = new ArrayList<CategoryAttributeBean>();
    RelatedCategoryDetailBean editHeader = new RelatedCategoryDetailBean();
    editHeader.setShopCode(reqBean.getSearchShopCode());

    WebshopConfig config = DIContainer.getWebshopConfig();
    int h = 0;
    for (int i = 0; i < config.getCategoryAttributeMaxCount(); i++) {
      String attributeNo = String.valueOf(i);
      CategoryAttributeBean attributeBean = new CategoryAttributeBean();
      CategoryAttributeValueBean valueBean = new CategoryAttributeValueBean();
      attributeBean.setCategoryAttributeNo(attributeNo);
      valueBean.setCategoryAttributeNo(attributeNo);

      if (h < categoryAttributeList.size() && categoryAttributeList.get(h).getCategoryAttributeNo().equals(Long.valueOf(i))) {
        CategoryAttribute attribute = categoryAttributeList.get(h);
        attributeBean.setCategoryAttributeName(attribute.getCategoryAttributeName());
        //add by cs_yuli 20120607 start 
        attributeBean.setCategoryAttributeNameEn(attribute.getCategoryAttributeNameEn());
        attributeBean.setCategoryAttributeNameJp(attribute.getCategoryAttributeNameJp());
        //add by cs_yuli 20120607 end
        h = h + 1;
      }
      attributeList.add(attributeBean);
      editHeader.getAttributeList().add(valueBean);
    }

    // 入力項目欄の情報を画面Beanに設定
    reqBean.setEdit(editHeader);

    // 属性名を画面Beanに設定
    reqBean.setAttributeList(attributeList);

  }

  /**
   * @param reqBean
   *          カテゴリ関連付けBean
   */
  public void setDisplayControl(RelatedCategoryBean reqBean) {
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    reqBean.setShopList(utilService.getShopNamesDefaultAllShop(false, false));
    reqBean.setShopListEdit(utilService.getShopNames(true));

    if (Permission.CATEGORY_UPDATE.isGranted(getLoginInfo()) || Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      reqBean.setRegisterTableDisplayFlg(true);
      reqBean.setDeletePartsDisplayFlg(true);

      if (reqBean.getAttributeList().size() > 0) {
        boolean selectDisplayFlg = false;
        for (CategoryAttributeBean categoryAttribute : reqBean.getAttributeList()) {
          selectDisplayFlg |= categoryAttribute.getCategoryAttributeName() != null;
        }
        reqBean.setSelectDisplayFlg(selectDisplayFlg);
      }
    }

    if (getLoginInfo().isSite()) {
      reqBean.setShopListDisplayFlg(true);
    }

  }

}
