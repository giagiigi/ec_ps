package jp.co.sint.webshop.web.action.front.catalog;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.catalog.BrandPathBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;
import jp.co.sint.webshop.web.webutility.SessionUrl;

/**
 * パンくずリストのサブアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class BrandPathSubAction extends WebSubAction {

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前BrandPathSubAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前BrandPathSubAction:session缺失，开始记录--------------------------------------------------------------------");
    }
    BrandPathBean reqBean = (BrandPathBean) getBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    // プレビューモード（管理側）だった場合、クエリ文字列に設定されているコードを取得
    if (StringUtil.hasValue(getRequestParameter().get("preview"))) {
      reqBean.setPreview(true);
      reqBean.setShopCode(getRequestParameter().get("shopCode"));
      reqBean.setCommodityCode(getRequestParameter().get("commodityCode"));
    } else {
      reqBean.setPreview(false);
      if (StringUtil.hasValue(getRequestParameter().get("searchCategoryCode"))) {
        reqBean.setCategoryCode(getRequestParameter().get("searchCategoryCode"));
      } else if (StringUtil.hasValue(getRequestParameter().get("sessionRead")) && getRequestParameter().get("sessionRead").equals("1")) {
        SessionUrl url = getSessionContainer().getSessionUrl();
        if (url != null) {
          // 商品目录
          if (StringUtil.hasValue(url.getCategoryCode())) {
            reqBean.setCategoryCode(url.getCategoryCode());
          }
          // 品牌
          if (StringUtil.hasValue(url.getBrandCode())) {
            reqBean.setBrandCode(url.getBrandCode());
          }
        }
      } else {
        String[] urlParam = getRequestParameter().getPathArgs();
        if (urlParam.length >= 2) {
          reqBean.setShopCode(urlParam[0]);
          reqBean.setCommodityCode(urlParam[1]);
        }
      }
    }

    // ソート済みパンくずリストの取得
    reqBean.setBrandPathList(service.getBrandPath(reqBean.getCategoryCode(), reqBean.getBrandCode()));

    setBean(reqBean);
    if(sesContainer.getSession() != null){
      logger.info("当前BrandPathSubAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前BrandPathSubAction:session缺失，结束记录--------------------------------------------------------------------");
    }

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
