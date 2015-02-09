package jp.co.sint.webshop.web.action.front.catalog;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommodityContainer;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.bean.front.catalog.DetailRecommendCBean;
import jp.co.sint.webshop.web.bean.front.catalog.DetailRecommendBaseBean;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;
import jp.co.sint.webshop.web.webutility.CookieContainer;

/**
 * お気に入り商品詳細のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class DetailRecommendCSubAction extends DetailRecommendBaseAction {

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前DetailRecommendCSubAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前DetailRecommendCSubAction:session缺失，开始记录--------------------------------------------------------------------");
    }
    String shopCode = "00000000";

    String commodityCode = "";

    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length >= 1) {
      // shopCode = urlParam[0];
      commodityCode = urlParam[0];
    }

    DetailRecommendCBean reqBean = new DetailRecommendCBean();

    List<List<DetailRecommendBaseBean>> pageList = new ArrayList<List<DetailRecommendBaseBean>>();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    CommodityContainerCondition condition = new CommodityContainerCondition();
    condition.setSearchShopCode(shopCode);
    condition.setSearchCommodityCode(commodityCode);
    // 20120116 ysy add start
    if (service.getCommodityInfo(shopCode, commodityCode) != null) {
    	condition.setSearchBrandCode(service.getCommodityInfo(shopCode, commodityCode).getHeader().getBrandCode());
    }
    // 20120116 ysy add end
    condition.setDisplayClientType(DisplayClientType.PC.getValue());
    condition.setMaxFetchSize(DIContainer.getWebshopConfig().getRecommendCommodityMaxCount());

    SearchResult<CommodityContainer> result = service.getRecommendCList(condition);
    List<CommodityContainer> commodityList = result.getRows();

    setRecommendCommodity(pageList, service, commodityList);

    reqBean.setRecommendCount(Integer.toString(getMaxCount(commodityList)));
    reqBean.setPageList(pageList);
    reqBean.setMaxLineCount(DIContainer.getWebshopConfig().getRecommendCommodityMaxLineCount());
    setBean(reqBean);
    
    if(sesContainer.getSession() != null){
      logger.info("当前DetailRecommendCSubAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前DetailRecommendCSubAction:session缺失，结束记录--------------------------------------------------------------------");
    }
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
