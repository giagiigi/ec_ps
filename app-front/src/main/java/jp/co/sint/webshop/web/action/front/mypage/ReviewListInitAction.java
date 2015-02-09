package jp.co.sint.webshop.web.action.front.mypage;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.ReviewScore;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.communication.ReviewList;
import jp.co.sint.webshop.service.communication.ReviewListSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil; 
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.ReviewListBean;
import jp.co.sint.webshop.web.bean.front.mypage.ReviewListBean.ReviewListBeanDetail;
import jp.co.sint.webshop.web.webutility.PagerUtil;
/**
 * U2030610:レビュー一覧のアクションクラスです
 * 
 * @author System OB.
 */
public class ReviewListInitAction extends WebFrontAction<ReviewListBean> {

  
  
  private ReviewListSearchCondition condition;

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new ReviewListSearchCondition();
  }

  protected ReviewListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected ReviewListSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CommunicationService cs = ServiceLocator.getCommunicationService(getLoginInfo()); 
    condition.setSearchCustomerCode(getLoginInfo().getCustomerCode());
    // 20110808 shiseidou add start
    List<String> searchReviewScoreList = new ArrayList<String>();
    searchReviewScoreList.add(ReviewScore.ONE_STAR.getValue());
    searchReviewScoreList.add(ReviewScore.TWO_STARS.getValue());
    searchReviewScoreList.add(ReviewScore.THREE_STARS.getValue());
    searchReviewScoreList.add(ReviewScore.FOUR_STARS.getValue());
    searchReviewScoreList.add(ReviewScore.FIVE_STARS.getValue());
    String[] searchReviewScoreSummary = searchReviewScoreList.toArray(
        new String[searchReviewScoreList.size()]);
    condition.setSearchReviewScoreSummary(searchReviewScoreSummary);
    // 20110808 shiseidou add end
    condition = getCondition();
//    condition.setPageSize(1000);
    SearchResult<ReviewList> reviewList = cs.getReviewPostList(condition);
    
    ReviewListBean reviewListBean = new ReviewListBean();
    List<ReviewListBeanDetail> list = new ArrayList<ReviewListBeanDetail>();
    
    for (ReviewList item : reviewList.getRows()) {
      ReviewListBeanDetail detail = new ReviewListBeanDetail();
      // 20110504 shiseidou add start
      detail.setJanCode(item.getJanCode());
      detail.setCommodityCode(item.getCommodityCode());      
      if (new Long(0).equals(item.getSign())) {
        detail.setDisplayLink(false);
      } else {
        detail.setDisplayLink(true);
      }
      // 20110504 shiseidou add end
      // 20120201 ysy add start
      detail.setShopCode(item.getShopCode());
      detail.setReviewDisplayType(item.getReviewDisplayType());
      // 20120201 ysy add end
      //add by cs_yuli 20120514 start 
      UtilService utilService=ServiceLocator.getUtilService(getLoginInfo());   
      detail.setCommodityName(utilService.getNameByLanguage(item.getCommodityName(),item.getCommodityNameEn(),item.getCommodityNameJp()));
      //add by cs_yuli 20120514 end
      detail.setReviewContributedDatetime(DateUtil.getYYYY(item.getReviewContributedDatetime())
          + "-" +DateUtil.getMM(item.getReviewContributedDatetime()) + "-" + DateUtil.getDD(item.getReviewContributedDatetime()));
      detail.setReviewTitle(item.getReviewTitle());
      detail.setReviewDescription(item.getReviewDescription());
      detail.setReviewScore(NumUtil.toString(item.getReviewScore()));
      detail.setDiscountMode(item.getDiscountMode());
      list.add(detail);
    }
    reviewListBean.setList(list);
    // リスト生成
    reviewListBean.setPagerValue(PagerUtil.createValue(reviewList));
    setRequestBean(reviewListBean);
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * キャンセル完了表示
   */
  @Override
  public void prerender() {
    
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

}
