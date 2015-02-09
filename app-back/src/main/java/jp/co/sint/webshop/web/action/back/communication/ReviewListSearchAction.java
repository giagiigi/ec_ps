package jp.co.sint.webshop.web.action.back.communication;

import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.ReviewList;
import jp.co.sint.webshop.service.communication.ReviewListSearchCondition;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.ReviewListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1060210:レビュー管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ReviewListSearchAction extends ReviewListBaseAction {

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
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.REVIEW_READ_SHOP.isGranted(getLoginInfo()) || Permission.REVIEW_READ_SITE.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());

    // 検索条件の設定
    ReviewListBean searchBean = getBean();

    condition.setSearchShopCode(this.getShopCode());
    condition.setSearchReviewContributedDatetimeFrom(searchBean.getReviewContributedDatetimeFrom());
    condition.setSearchReviewContributedDatetimeTo(searchBean.getReviewContributedDatetimeTo());
    condition.setSearchCommodityCodeFrom(searchBean.getCommodityCodeFrom());
    condition.setSearchCommodityCodeTo(searchBean.getCommodityCodeTo());
    condition.setSearchCommodityName(searchBean.getSearchCommodityName());
    condition.setSearchReviewContent(searchBean.getReviewContents());
    condition.setSearchReviewDisplayType(searchBean.getReviewDisplayType());
    condition.setSearchListSort(searchBean.getSearchListSort());
    condition = getCondition();

    // 検索処理実行
    SearchResult<ReviewList> result = svc.getReviewPostList(condition);

    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);

    // 画面上の検索条件を保持
    ReviewListBean nextBean = searchBean;
    nextBean.getList().clear();

    // ページ情報を追加
    nextBean.setPagerValue(PagerUtil.createValue(result));

    // 結果一覧を作成
    List<ReviewList> reviewList = result.getRows();
    addResult(reviewList, nextBean);

    // ソート順切替：検索ボタン押下の場合(「""」の場合)はソート処理を行わない
    if (StringUtil.hasValue(searchBean.getSearchListSort())) {
      if (searchBean.getSearchListSort().equals("contributedDatetimeAsc")) {
        nextBean.setSortContributedDatetime("contributedDatetimeDesc");
      } else {
        nextBean.setSortContributedDatetime("contributedDatetimeAsc");
      }
      if (searchBean.getSearchListSort().equals("commodityNameAsc")) {
        nextBean.setSortCommodityName("commodityNameDesc");
      } else {
        nextBean.setSortCommodityName("commodityNameAsc");
      }
    }

    this.setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    ReviewListBean reviewBean = getBean();

    // bean(検索条件)のvalidationチェック
    if (!validateBean(reviewBean)) {
      return false;
    }

    // 日付の大小関係チェック
    condition.setSearchReviewContributedDatetimeFrom(reviewBean.getReviewContributedDatetimeFrom());
    condition.setSearchReviewContributedDatetimeTo(reviewBean.getReviewContributedDatetimeTo());
    if (!condition.isValid()) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM,
          Messages.getString("web.action.back.communication.ReviewListSearchAction.0")));
      return false;
    }

    return true;
  }

  /**
   * ログインユーザによって、検索用のショップコードに設定する値を制御します。<BR>
   * <BR>
   * ＜決済モードがモール一括/ショップ個別モードの場合＞<BR>
   * ・ログインユーザがサイトの場合は、リクエストにあるショップコードを設定します。<BR>
   * ・ログインユーザがショップの場合は、ログインセッションにあるショップコードを設定します。<BR>
   * <BR>
   * ＜決済モードが一店舗モードの場合＞<BR>
   * ・ログインセッションにあるショップコードを設定します。
   * 
   * @param
   * @return shopCode
   */
  private String getShopCode() {
    String shopCode = null;
    if (getLoginInfo().isSite() && !getConfig().isOne()) {
      shopCode = getBean().getShopCode();
    } else {
      shopCode = getLoginInfo().getShopCode();
    }
    return shopCode;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.ReviewListSearchAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106021004";
  }

}
