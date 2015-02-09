package jp.co.sint.webshop.web.action.front.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommodityContainer;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.bean.front.catalog.DetailRecommendBaseBean;
import jp.co.sint.webshop.web.bean.front.catalog.DetailRecommendDBean;
import jp.co.sint.webshop.web.webutility.CookieContainer;

/**
 * 商品詳細のアクションクラスです
 * 
 * @author Kousen.
 */
public class DetailRecommendDSubAction extends DetailRecommendBaseAction {

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {
    Cart cart = getSessionContainer().getCart();
    String shopCode = DIContainer.getWebshopConfig().getSiteShopCode();

    DetailRecommendDBean reqBean = new DetailRecommendDBean();

    List<List<DetailRecommendBaseBean>> pageList = new ArrayList<List<DetailRecommendBaseBean>>();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    CommodityContainerCondition condition = new CommodityContainerCondition();
    condition.setSearchShopCode(shopCode);
    List<String> commodityCodeList = new ArrayList<String>();
    for (CartItem item : cart.get()) {
      commodityCodeList.add(item.getCommodityCode());
    }
    condition.setSearchCartCommodityCode(commodityCodeList.toArray(new String[commodityCodeList.size()]));
    condition.setDisplayClientType(DisplayClientType.PC.getValue());
    condition.setMaxFetchSize(10);

    SearchResult<CommodityContainer> result = service.fastFindDetailRecommendBContainer(condition);
    List<CommodityContainer> commodityList = result.getRows();

    setRecommendCommodity(pageList, service, commodityList);

    reqBean.setRecommendCount(Integer.toString(getMaxCount(commodityList)));
    reqBean.setPageList(pageList);
    reqBean.setMaxLineCount(1);
    setBean(reqBean);
  }

  /**
   * 画面表示するメッセージリストを取得します
   * 
   * @return null
   */
  public List<String> getDisplayMessageList() {
    return null;
  }

  /**
   * Cookieを取得します
   * 
   * @return null
   */
  public CookieContainer getCookieContainer() {
    return null;
  }

}
