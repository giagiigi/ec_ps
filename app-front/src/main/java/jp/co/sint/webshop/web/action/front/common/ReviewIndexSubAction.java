package jp.co.sint.webshop.web.action.front.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.SearchResult; 
import jp.co.sint.webshop.data.domain.ReviewDisplayType;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.communication.ReviewList;
import jp.co.sint.webshop.service.communication.ReviewListSearchCondition; 
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.common.ReviewIndexBean;
import jp.co.sint.webshop.web.bean.front.common.ReviewIndexBean.ReviewListBeanDetail;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;

/**
 * インフォメーションのサブアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ReviewIndexSubAction extends WebSubAction {

  private ReviewListSearchCondition condition;
  private final static String DISPLAY_COUNT="5";
  private final static int DESCRIPTION_COUNT=30; 
  private String commodityName="";
  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new ReviewListSearchCondition();
    condition.setSearchDisplayCount(DISPLAY_COUNT);
    condition.setSearchReviewDisplayType(ReviewDisplayType.DISPLAY.getValue());
  }
  
  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前ReviewIndexSubAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前ReviewIndexSubAction:session缺失，开始记录--------------------------------------------------------------------");
    }
    CommunicationService cs = ServiceLocator.getCommunicationService(getLoginInfo()); 
    SearchResult<ReviewList> reviewList = cs.getReviewPostList(condition);
    ReviewIndexBean reviewListBean = new ReviewIndexBean();
    List<ReviewListBeanDetail> list = new ArrayList<ReviewListBeanDetail>();
    
    for (ReviewList item : reviewList.getRows()) {
      ReviewListBeanDetail detail = new ReviewListBeanDetail();
      detail.setJanCode(item.getJanCode());
      detail.setCommodityCode(item.getCommodityCode());      
      if (new Long(0).equals(item.getSign())) {
        detail.setDisplayLink(false);
      } else {
        detail.setDisplayLink(true);
      }
      //add by cs_yuli 20120514 start 
      UtilService utilService=ServiceLocator.getUtilService(getLoginInfo());
  	  commodityName = utilService.getNameByLanguage(item.getCommodityName(),item.getCommodityNameEn(),item.getCommodityNameJp());
  	  detail.setCommodityName(commodityName); 
      //add by cs_yuli 20120514 end     
      detail.setNickName(item.getNickname());
      detail.setShopCode(item.getShopCode());
      detail.setReviewDisplayType(item.getReviewDisplayType());
      
      detail.setReviewContributedDatetime(DateUtil.getYYYY(item.getReviewContributedDatetime())
          + "-" +DateUtil.getMM(item.getReviewContributedDatetime()) + "-" + DateUtil.getDD(item.getReviewContributedDatetime()));
      detail.setReviewTitle(item.getReviewTitle());
      if(item.getReviewDescription().length() > DESCRIPTION_COUNT)
      {
        detail.setReviewDescription(item.getReviewDescription().substring(0,DESCRIPTION_COUNT) + "...");
      } else {
        detail.setReviewDescription(item.getReviewDescription());
      }
      detail.setReviewScore(NumUtil.toString(item.getReviewScore()));
      detail.setDiscountMode(item.getDiscountMode());
      list.add(detail);
    }
    // リスト生成
    reviewListBean.setList(list);

    setBean(reviewListBean);
    
    if(sesContainer.getSession() != null){
      logger.info("当前ReviewIndexSubAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前ReviewIndexSubAction:session缺失，结束记录--------------------------------------------------------------------");
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
