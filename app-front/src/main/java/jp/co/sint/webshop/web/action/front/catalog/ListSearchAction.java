package jp.co.sint.webshop.web.action.front.catalog;

import jp.co.sint.webshop.service.catalog.CommodityContainerCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityListBean;

/**
 * U2040410:商品一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ListSearchAction extends ListBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CommodityListBean reqBean = getBean();
    reqBean.getList().clear();

    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length >= 1) {
      reqBean.setSearchCategoryCode(urlParam[0]);
    }

    // 検索条件の設定
    CommodityContainerCondition condition = setSearchCondition(reqBean);

    // 商品一覧の取得
    setCommodityList(reqBean, condition);

    setNextUrl(null);
    setRequestBean(reqBean);

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * beanのcreateAttributeを実行します。
   * 
   * @return 実行するならtrue
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    CommodityListBean reqBean = (CommodityListBean) getRequestBean();
    setPictureMode(reqBean);
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
