package jp.co.sint.webshop.web.action.front.catalog;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.catalog.ShopListBean;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;

/**
 * ショップリスト情報のサブアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ShopListSubAction extends WebSubAction {

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前ShopListSubAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前ShopListSubAction:session缺失，开始记录--------------------------------------------------------------------");
    }
    ShopListBean reqBean = (ShopListBean) getBean();
    UtilService service = ServiceLocator.getUtilService(ServiceLoginInfo.getInstance());
    reqBean.setShopList(service.getShopNamesDefaultAllShop(false, true));
    setBean(reqBean);
    if(sesContainer.getSession() != null){
      logger.info("当前ShopListSubAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前ShopListSubAction:session缺失，结束记录--------------------------------------------------------------------");
    }
  }
}
