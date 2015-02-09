package jp.co.sint.webshop.web.action.front.catalog;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityDetailBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;

/**
 * U2040510:商品詳細のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityDetailAction extends DetailBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length >= 1) {
    } else {
      throw new URLNotFoundException();
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前CommodityDetailAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前CommodityDetailAction:session缺失，开始记录--------------------------------------------------------------------");
    }
    CommodityDetailBean reqBean = new CommodityDetailBean();
    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length >= 1) {
      reqBean.setCommodityCode(urlParam[0]);
    }
    setNextUrl("/app/catalog/detail/init/" + reqBean.getCommodityCode());
    if(sesContainer.getSession() != null){
      logger.info("当前CommodityDetailAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前CommodityDetailAction:session缺失，结束记录--------------------------------------------------------------------");
    }
    return FrontActionResult.RESULT_SUCCESS;
  }
}
