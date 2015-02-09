package jp.co.sint.webshop.web.action.front.catalog;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.catalog.ParamPathBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;
import jp.co.sint.webshop.web.webutility.SessionUrl;

/**
 * パンくずリストのサブアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ParamPathSubAction extends WebSubAction {

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {
    
    String HALF_SPACE = " ";
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前ParamPathSubAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前ParamPathSubAction:session缺失，开始记录--------------------------------------------------------------------");
    }
//    ParamPathBean reqBean = (ParamPathBean) getBean();
    ParamPathBean reqBean = new ParamPathBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    SessionUrl url = getSessionContainer().getSessionUrl();
    
    if (url != null) {
      // 商品目录
      if (StringUtil.hasValue(getRequestParameter().get("searchCategoryCode"))) {
        reqBean.setCategoryCode(getRequestParameter().get("searchCategoryCode"));
        reqBean.setSearchWord(getRequestParameter().get("searchWord"));
      } else if (StringUtil.hasValue(getRequestParameter().get("searchTagCode"))) {
        reqBean.setCategoryCode(getRequestParameter().get("searchCategoryCode"));
        reqBean.setSearchWord(getRequestParameter().get("searchWord"));
      } else {
        reqBean.setCategoryCode(url.getCategoryCode());
        // 品牌编号
        reqBean.setBrandCode(url.getBrandCode());
        // 属性1
        reqBean.setAttribute1(url.getAttribute1());
        // 属性2
        reqBean.setAttribute2(url.getAttribute2());
        // 属性3
        reqBean.setAttribute3(url.getAttribute3());
        // 价格区域
        reqBean.setPriceType(url.getPriceType());
        reqBean.setPriceStart(url.getPriceStart());
        reqBean.setPriceEnd(url.getPriceEnd());
        // 评论区域
        reqBean.setReviewScore(url.getReviewScore());
        // 关键字
        reqBean.setSearchWord(url.getSearchWord());
      }
    } else {
      reqBean.setCategoryCode(getRequestParameter().get("searchCategoryCode"));
      reqBean.setSearchWord(getRequestParameter().get("searchWord"));
    }


    String str = reqBean.getSearchWord();
    str = str.replace("/", HALF_SPACE);
    str = str.replace("\\", HALF_SPACE);
    reqBean.setSearchWord(str);
    // ソート済みパンくずリストの取得
    reqBean.setParamPathList(service.getParamPath(reqBean.getCategoryCode(), reqBean.getBrandCode(), reqBean.getReviewScore()
        , reqBean.getPriceType(), reqBean.getPriceStart(), reqBean.getPriceEnd(), reqBean.getAttribute1(), reqBean.getAttribute2(), reqBean.getAttribute3(), reqBean.getSearchWord()));
    String paramPath = "";
    for (CodeAttribute c : reqBean.getParamPathList()) {
      if (StringUtil.hasValue(paramPath)) {
        paramPath += " ";
      }
      paramPath += c.getName();
    }
    reqBean.setParamPath(paramPath);
    setBean(reqBean);

    if(sesContainer.getSession() != null){
      logger.info("当前ParamPathSubAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前ParamPathSubAction:session缺失，结束记录--------------------------------------------------------------------");
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
