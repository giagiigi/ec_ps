package jp.co.sint.webshop.web.action.front.catalog;

import jp.co.sint.webshop.data.domain.CommodityDisplayOrder;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityListBean;
import jp.co.sint.webshop.web.webutility.PagerValue;

/**
 * U2040410:商品一覧のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class SaleHotInitAction extends ListBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CommodityListBean reqBean = new CommodityListBean();
    reqBean.getList().clear();

    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length >= 1) {
      reqBean.setSearchCategoryCode(urlParam[0]);
    }
    
    // 検索条件の設定
    reqBean.setAlignmentSequence(CommodityDisplayOrder.BY_PRICE_ASCENDING.getValue());
    PagerValue value = new PagerValue();
    value.setPageSize(10);
    reqBean.setPagerValue(value);
    CommodityContainerCondition condition = setSearchCondition(reqBean);

    // 商品一覧の取得
    setCommodityList(reqBean, condition);

    reqBean.setMetaKeyword("");
    reqBean.setMetaDescription("");
    reqBean.setCategoryName("");
    reqBean.setCategoryFlag(false);
    reqBean.setBrandFlag(false);
    reqBean.setSearchWord(WebUtil.escapeXml(reqBean.getSearchWord()));
    setNextUrl(null);
    setRequestBean(reqBean);

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    CommodityListBean reqBean = (CommodityListBean) getRequestBean();
    setPictureMode(reqBean);
  }
  
}
