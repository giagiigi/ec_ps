package jp.co.sint.webshop.web.action.front.common;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.dto.DiscountHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.data.ContentsSearchCondition;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.common.IndexBean;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;

/**
 * U2040110:トップページのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class IndexInitAction extends WebFrontAction<IndexBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    
    Logger logger = Logger.getLogger(this.getClass());
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前IndexInitAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前IndexInitAction:session缺失，开始记录--------------------------------------------------------------------");
    }

    IndexBean bean = new IndexBean();
    DiscountHeader dh = catalogService.getDiscountHeaderLeast();
    if (dh != null) {
      String startMonth = DateUtil.getMM(dh.getDiscountStartDatetime());
      String startDay =  DateUtil.getDD(dh.getDiscountStartDatetime());
      String smin = DateUtil.getHHmmss(dh.getDiscountStartDatetime());
      String startMin = smin.substring(0,smin.length()-3);
      
      String endMonth = DateUtil.getMM(dh.getDiscountEndDatetime());
      String endDay =  DateUtil.getDD(dh.getDiscountEndDatetime());
      String min = DateUtil.getHHmmss(dh.getDiscountEndDatetime());
      String endMin = min.substring(0,min.length()-3);
      
      bean.setStartDate(startMonth+"/"+startDay+" "+startMin);
      bean.setEndDate(endMonth+"/"+endDay+" "+endMin);
    }
    setRequestBean(bean);
    //add by os011 2011/12/20  start 
    String path = "/";
    //判断用户id的cookie是否存在
    //登录状态，没有cookie的时候，创建cookie
    if (this.getLoginInfo()!=null&&this.getLoginInfo().isLogin()&&this.getCookieContainer().getCookie(DIContainer.getWebshopConfig().getHostName())!=null)
    {    	
  	  int alivePeriod = 60 * 60 * 24 *30;
      getCookieContainer().addSecureCookies(DIContainer.getWebshopConfig().getHostName(), this.getLoginInfo().getName(), path, alivePeriod);
    }
  //add by os011 2011/12/20  start 
    ContentsSearchCondition condition = new ContentsSearchCondition();
    condition.setContentsType(ContentsType.CONTENT_SITE_TOPPAGE);
  
  
    DataIOService service = ServiceLocator.getDataIOService(ServiceLoginInfo.getInstance());

    boolean contentsExists = service.contentsExists(condition);

    if (contentsExists) {
      //setNextUrl("/contents/top/");
    } else {
      setNextUrl(null);
    }
    
    if(sesContainer.getSession() != null){
      logger.info("当前IndexInitAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前IndexInitAction:session缺失，结束记录--------------------------------------------------------------------");
    }

    return FrontActionResult.RESULT_SUCCESS;
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

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

  }
}
