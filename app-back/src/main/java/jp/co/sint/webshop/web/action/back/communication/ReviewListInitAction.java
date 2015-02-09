package jp.co.sint.webshop.web.action.back.communication;

import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.communication.ReviewList;
import jp.co.sint.webshop.service.communication.ReviewListSearchCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.ReviewListBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1060210:レビュー管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ReviewListInitAction extends ReviewListBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.REVIEW_READ_SITE.isGranted(getLoginInfo()) || Permission.REVIEW_READ_SHOP.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
    ReviewListSearchCondition condition = new ReviewListSearchCondition();

    // 検索条件の設定:未チェックのレビューを全件取得
    condition.setSearchShopCode(this.getShopCode());
    condition.setSearchReviewDisplayType(ReviewListBean.INIT_DISPLAY_TYPE);
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // 検索処理の実行
    SearchResult<ReviewList> result = svc.getReviewPostList(condition);

    // オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.OVERFLOW);

    // nextBeanの作成
    ReviewListBean nextBean = new ReviewListBean();
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    nextBean.setShopList(utilService.getShopNamesDefaultAllShop(false, false));
    nextBean.setReviewDisplayType(condition.getSearchReviewDisplayType());

    // ページ情報を追加
    nextBean.setPagerValue(PagerUtil.createValue(result));

    // 結果一覧を作成
    List<ReviewList> reviewList = result.getRows();
    addResult(reviewList, nextBean);

    this.setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * ログインユーザによって、検索用のショップコードに設定する値を制御します。<BR>
   * <BR>
   * ＜決済モードがモール一括/ショップ個別モードの場合＞<BR>
   * ・ログインユーザがサイトの場合は、空を設定します。<BR>
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
      shopCode = "";
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
    return Messages.getString("web.action.back.communication.ReviewListInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106021002";
  }

}
