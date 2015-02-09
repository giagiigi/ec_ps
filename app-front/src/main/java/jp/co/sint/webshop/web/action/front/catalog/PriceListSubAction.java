package jp.co.sint.webshop.web.action.front.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.domain.CommodityDisplayOrder;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.PriceList;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.catalog.PriceListBean;
import jp.co.sint.webshop.web.bean.front.catalog.PriceListBean.PriceDetailBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * カテゴリツリーのサブアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PriceListSubAction extends WebSubAction {

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {

    PriceListBean reqBean = (PriceListBean) getBean();
    List<PriceDetailBean> priceList = new ArrayList<PriceDetailBean>();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    // プレビューモード（管理側）だった場合、クエリ文字列に設定されているコードを取得
    if (StringUtil.hasValue(getRequestParameter().get("preview"))) {
      reqBean.setPreview(true);
      reqBean.setShopCode(getRequestParameter().get("shopCode"));
      reqBean.setCommodityCode(getRequestParameter().get("commodityCode"));
    } else {
      reqBean.setPreview(false);
      String[] urlParam = getRequestParameter().getPathArgs();
      if (urlParam.length >= 2) {
        reqBean.setShopCode(urlParam[0]);
        reqBean.setCommodityCode(urlParam[1]);
      }
    }

    // ソート済みカテゴリツリーの取得
    List<CodeAttribute> price = new ArrayList<CodeAttribute>();
    price.addAll(Arrays.asList(PriceList.values()));
    for (CodeAttribute c : price) {
      PriceDetailBean bean = new PriceDetailBean();
      String[] prices = c.getName().split(",");
      BigDecimal minLong = null;
      BigDecimal maxLong = null;
      if (prices.length == 2) {
        minLong = new BigDecimal(prices[0]);
        maxLong = new BigDecimal(prices[1]);
        bean.setPriceName(minLong + "～" + maxLong);
      } else {
        minLong = new BigDecimal(prices[0]);
        bean.setPriceName(minLong.toString());
      }
      bean.setPrice(c.getValue());
      CommodityContainerCondition condition = setSearchCondition(reqBean, minLong, maxLong);
      Object count = service.getPriceCount(condition);
      bean.setCommodityCount((Long) count);
      bean.setCategoryCode(reqBean.getCategoryCode());
      bean.setBrandCode(reqBean.getBrandCode());
      bean.setReviewScore(reqBean.getReviewScore());
      bean.setCategoryAttribute1(reqBean.getCategoryAttribute1());
      bean.setAlignmentSequence(reqBean.getAlignmentSequence());
      bean.setMode(reqBean.getMode());
      bean.setPageSize(reqBean.getPageSize());
      priceList.add(bean);
    }
    reqBean.setPriceList(priceList);
    setBean(reqBean);

  }
  
  /**
   * @param reqBean
   *          商品Bean
   * @return 商品情報
   */
  public CommodityContainerCondition setSearchCondition(PriceListBean reqBean, BigDecimal from, BigDecimal to) {
    CommodityContainerCondition condition = new CommodityContainerCondition();
    condition.setReviewScore(reqBean.getReviewScore());
//    condition.setSearchWord(reqBean.getSearchWord());
    if (NumUtil.isNull(to)) {
      condition.setSearchPriceStart(from.toString());
      condition.setSearchPriceEnd("9999999999");
    } else{
      condition.setSearchPriceStart(from.toString());
      condition.setSearchPriceEnd(to.toString());
    }
    condition.setByRepresent(true);
    condition.setSearchCategoryCode(reqBean.getCategoryCode());
    condition.setSearchBrandCode(reqBean.getBrandCode());
    condition.setSearchCategoryAttribute1(reqBean.getCategoryAttribute1());

    if (StringUtil.hasValue(reqBean.getAlignmentSequence())) {
      condition.setAlignmentSequence(reqBean.getAlignmentSequence());
    } else {
      reqBean.setAlignmentSequence(CommodityDisplayOrder.BY_POPULAR_RANKING.getValue());
      condition.setAlignmentSequence(reqBean.getAlignmentSequence());
    }

    condition.setDisplayClientType(DisplayClientType.PC.getValue());
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    return condition;
  }

  /**
   * ログイン情報を取得します
   * 
   * @return frontLoginInfo
   */
  public FrontLoginInfo getLoginInfo() {
    LoginInfo loginInfo = getSessionContainer().getLoginInfo();
    FrontLoginInfo frontLoginInfo = null;

    if (loginInfo == null) {
      frontLoginInfo = WebLoginManager.createFrontNotLoginInfo();
    } else {
      if (loginInfo instanceof FrontLoginInfo) {
        frontLoginInfo = (FrontLoginInfo) loginInfo;
      } else {
        frontLoginInfo = WebLoginManager.createFrontNotLoginInfo();
      }
    }
    return frontLoginInfo;
  }

}
