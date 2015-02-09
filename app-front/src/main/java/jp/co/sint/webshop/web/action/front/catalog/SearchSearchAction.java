package jp.co.sint.webshop.web.action.front.catalog;

import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityListBean;
import jp.co.sint.webshop.web.bean.front.catalog.SearchBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2040310:詳細検索のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class SearchSearchAction extends WebFrontAction<SearchBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    SearchBean reqBean = getBean();
    CommodityListBean nextBean = new CommodityListBean();
    nextBean.setSearchMethod(reqBean.getSearchMethod());
    nextBean.setSearchWord(reqBean.getSearchWord());
    nextBean.setSearchCommodityCode(reqBean.getSearchCommodityCode());
    nextBean.setSearchCategoryCode(reqBean.getSearchCategoryCode());
    // 一店舗版のときはショップコードを検索条件にセットしない
    if (!getConfig().isOne()) {
      nextBean.setSearchShopCode(reqBean.getSearchShopCode());
    }
    nextBean.setSearchPriceStart(reqBean.getSearchPriceStart());
    nextBean.setSearchPriceEnd(reqBean.getSearchPriceEnd());
    nextBean.setSearchCategoryAttributeList(reqBean.getCategoryAttributeList());

    setNextUrl("/app/catalog/list/init" + nextBean.toQueryString());
    setRequestBean(nextBean);

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    SearchBean bean = getBean();

    boolean result = validateBean(bean);

    if (result && StringUtil.hasValue(bean.getSearchPriceStart()) && StringUtil.hasValue(bean.getSearchPriceEnd())) {
      if (BigDecimalUtil.isAbove(NumUtil.parse(bean.getSearchPriceStart()) ,NumUtil.parse(bean.getSearchPriceEnd()))) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR,
            Messages.getString("web.action.front.catalog.SearchSearchAction.0")));
        result = false;
      }
    }

    return result;
  }
}
