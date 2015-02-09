package jp.co.sint.webshop.web.action.front.catalog;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.dto.ReviewPost; // 10.1.4 10189 追加
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.ReviewPostAndCustHeadLine;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.catalog.DetailReviewBean;
import jp.co.sint.webshop.web.bean.front.catalog.DetailReviewBean.DetailReviewDetail;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;

/**
 * Include jsp用のActionのアクションサブクラスです
 * 
 * @author System Integrator Corp.
 */
public class DetailReviewSubAction extends WebSubAction {

  private static final int REVIEW_INIT_DISPLAY_COUNT = 5;

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前DetailReviewSubAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前DetailReviewSubAction:session缺失，开始记录--------------------------------------------------------------------");
    }

    String shopCode = "00000000";

    String commodityCode = "";

    String[] urlParam = getRequestParameter().getPathArgs();

    if (urlParam.length >= 1) {
      // shopCode = urlParam[0];
      commodityCode = urlParam[0];
    }

    DetailReviewBean reqBean = new DetailReviewBean();

    List<DetailReviewDetail> reviewList = new ArrayList<DetailReviewDetail>();
    List<DetailReviewDetail> initList = new ArrayList<DetailReviewDetail>();

    // 10.1.4 10189 削除 ここから
//    ReviewListSearchCondition condition = new ReviewListSearchCondition();
//    condition.setSearchShopCode(shopCode);
//    condition.setSearchCommodityCode(commodityCode);
//    condition.setSearchReviewDisplayType(ReviewDisplayType.DISPLAY.getValue());
    // 10.1.4 10189 削除 ここまで

  //  CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
   // List<CommodityContainer> container = service.getCommoditySkuList(shopCode, commodityCode, true, DisplayClientType.PC);

   // if (container == null) {
  //    return;
  //  }
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    // 10.1.4 10189 修正 ここから
    // SearchResult<ReviewList> resultList = communicationService.getReviewPostList(condition);
    // List<ReviewList> list = resultList.getRows();
    // reqBean.setReviewCount(Integer.toString(resultList.getRowCount()));
    List<ReviewPostAndCustHeadLine> list = communicationService.getDisplayedReviewPostAndCustList(shopCode, commodityCode);
    reqBean.setReviewCount(Integer.toString(list.size()));
    // 10.1.4 10189 修正 ここまで
    reqBean.setReviewInitDisplayCount(REVIEW_INIT_DISPLAY_COUNT);

    for (int i = 0; i < list.size(); i++) {
      DetailReviewDetail reviewDetail = new DetailReviewDetail();
      reviewDetail.setReviewTitle(list.get(i).getReviewTitle());
      reviewDetail.setNickName(list.get(i).getNickname());
      reviewDetail.setReviewDescription(list.get(i).getReviewDescription());
      reviewDetail.setReviewScore(Long.toString(list.get(i).getReviewScore()));
      reviewDetail.setSex(list.get(i).getSex());
      String contributedDate = DateUtil.toDateString(list.get(i).getReviewContributedDatetime());
      reviewDetail.setReviewContributedDatetime(contributedDate);

      if (i < reqBean.getReviewInitDisplayCount()) {
        initList.add(reviewDetail);
      } else {
        reviewList.add(reviewDetail);
      }
    }
    reqBean.setInit(initList);
    reqBean.setList(reviewList);

    setBean(reqBean);
    
    if(sesContainer.getSession() != null){
      logger.info("当前DetailReviewSubAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前DetailReviewSubAction:session缺失，结束记录--------------------------------------------------------------------");
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
