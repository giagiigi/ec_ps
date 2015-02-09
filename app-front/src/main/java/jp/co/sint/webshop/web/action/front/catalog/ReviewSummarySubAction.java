package jp.co.sint.webshop.web.action.front.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.domain.CommodityDisplayOrder;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.PriceList;
import jp.co.sint.webshop.data.domain.ReviewScore;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition;
import jp.co.sint.webshop.service.catalog.ReviewData;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.catalog.ReviewSummaryBean;
import jp.co.sint.webshop.web.bean.front.catalog.ReviewSummaryBean.ReviewSummaryDetailBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * カテゴリツリーのサブアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ReviewSummarySubAction extends WebSubAction {

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {

    ReviewSummaryBean reqBean = (ReviewSummaryBean) getBean();
    List<ReviewSummaryDetailBean> reviewList = new ArrayList<ReviewSummaryDetailBean>();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    // プレビューモード（管理側）だった場合、クエリ文字列に設定されているコードを取得
    if (StringUtil.hasValue(getRequestParameter().get("preview"))) {
      reqBean.setPreview(true);
      reqBean.setShopCode(getRequestParameter().get("shopCode"));
      reqBean.setCommodityCode(getRequestParameter().get("commodityCode"));
    } else {
      reqBean.setPreview(false);
      String[] urlParam = getRequestParameter().getPathArgs();
      if (urlParam.length >= 2) {
        reqBean.setShopCode(urlParam[0]);
        reqBean.setCommodityCode(urlParam[1]);
      }
    }

    CommodityContainerCondition condition = setSearchCondition(reqBean);
    // ソート済みカテゴリツリーの取得
    List<CodeAttribute> reviewScoreList = new ArrayList<CodeAttribute>();
    reviewScoreList.add(ReviewScore.FOUR_STARS);
    reviewScoreList.add(ReviewScore.THREE_STARS);
    reviewScoreList.add(ReviewScore.TWO_STARS);
    reviewScoreList.add(ReviewScore.ONE_STAR);
    List<ReviewData> list = service.getReviewSummary(condition);
    for (CodeAttribute c : reviewScoreList) {
      ReviewSummaryDetailBean bean = new ReviewSummaryDetailBean();
      bean.setReviewScore(c.getValue());
      bean.setCommodityCount(0L);
      bean.setCategoryCode(reqBean.getCategoryCode());
      bean.setBrandCode(reqBean.getBrandCode());
      bean.setPrice(reqBean.getPrice());
      bean.setCategoryAttribute1(reqBean.getCategoryAttribute1());
      bean.setAlignmentSequence(reqBean.getAlignmentSequence());
      bean.setMode(reqBean.getMode());
      bean.setPageSize(reqBean.getPageSize());
      for (ReviewData review : list) {
        if (c.getValue().equals(review.getReviewScore())) {
          bean.setCommodityCount(review.getCommodityCount());
          break;
        }
      }
      reviewList.add(bean);
    }
    
    reqBean.setReviewList(reviewList);

    setBean(reqBean);

  }
  
  /**
   * @param reqBean
   *          商品Bean
   * @return 商品情報
   */
  public CommodityContainerCondition setSearchCondition(ReviewSummaryBean reqBean) {
    CommodityContainerCondition condition = new CommodityContainerCondition();
    condition.setReviewScore(reqBean.getReviewScore());
//    condition.setSearchWord(reqBean.getSearchWord());
    if (StringUtil.hasValue(reqBean.getPrice())) {
      CodeAttribute price = PriceList.fromValue(reqBean.getPrice());
      String[] prices = price.getName().split(",");
      if (prices.length == 2) {
        condition.setSearchPriceStart(NumUtil.parse(prices[0]).abs().toString());
        condition.setSearchPriceEnd(NumUtil.parse(prices[1]).abs().toString());
      } else{
        condition.setSearchPriceStart(NumUtil.parse(prices[0]).abs().toString());
        condition.setSearchPriceEnd("9999999999");
      }
    }
    condition.setByRepresent(true);
    condition.setSearchCategoryCode(reqBean.getCategoryCode());
    condition.setSearchBrandCode(reqBean.getBrandCode());
    condition.setSearchCategoryAttribute1(reqBean.getCategoryAttribute1());

    if (StringUtil.hasValue(reqBean.getAlignmentSequence())) {
      condition.setAlignmentSequence(reqBean.getAlignmentSequence());
    } else {
      reqBean.setAlignmentSequence(CommodityDisplayOrder.BY_POPULAR_RANKING.getValue());
      condition.setAlignmentSequence(reqBean.getAlignmentSequence());
    }

    condition.setDisplayClientType(DisplayClientType.PC.getValue());
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    return condition;
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
