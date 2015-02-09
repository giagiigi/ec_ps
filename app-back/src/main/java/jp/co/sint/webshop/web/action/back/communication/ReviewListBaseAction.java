package jp.co.sint.webshop.web.action.back.communication;

import java.util.List;

import jp.co.sint.webshop.data.domain.ReviewDisplayType;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.ReviewList;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.ReviewListBean;
import jp.co.sint.webshop.web.bean.back.communication.ReviewListBean.ReviewListBeanDetail;

/**
 * U1060210:レビュー管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class ReviewListBaseAction extends WebBackAction<ReviewListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public abstract boolean authorize();

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    // ボタン表示制御
    ReviewListBean bean = (ReviewListBean) getRequestBean();
    bean.setDeleteButtonDisplayFlg(Permission.REVIEW_DELETE_SITE.isGranted(getLoginInfo())
        || Permission.REVIEW_DELETE_SHOP.isGranted(getLoginInfo()));
    bean.setUpdateButtonDisplayFlg(Permission.REVIEW_UPDATE_SITE.isGranted(getLoginInfo())
        || Permission.REVIEW_UPDATE_SHOP.isGranted(getLoginInfo()));
    bean.setCheckBoxDisplayFlg(Permission.REVIEW_UPDATE_SITE.isGranted(getLoginInfo())
        || Permission.REVIEW_UPDATE_SHOP.isGranted(getLoginInfo()) || Permission.REVIEW_DELETE_SITE.isGranted(getLoginInfo())
        || Permission.REVIEW_DELETE_SHOP.isGranted(getLoginInfo()));
    setRequestBean(bean);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public abstract WebActionResult callService();

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
   * レビューの検索結果一覧を作成します。<BR>
   * 
   * @param reviewList
   * @param nextBean
   */
  public void addResult(List<ReviewList> reviewList, ReviewListBean nextBean) {
    for (ReviewList reviewData : reviewList) {
      ReviewListBeanDetail detail = new ReviewListBeanDetail();

      String customerCode = reviewData.getCustomerCode();
      detail.setCustomerCode(customerCode);
      detail.setReviewId(reviewData.getReviewId().toString());
      detail.setReviewDisplayType(this.getType(reviewData.getReviewDisplayType()));
      detail.setReviewContributedDatetime(DateUtil.toDateTimeString(reviewData.getReviewContributedDatetime()));
      detail.setNickName(reviewData.getNickname());
      detail.setCommodityName(reviewData.getCommodityName());
      detail.setReviewScore(Long.toString(reviewData.getReviewScore()));
      detail.setReviewTitle(reviewData.getReviewTitle());
      if (reviewData.getReviewDescription().length() >= 60) {
        detail.setReviewDescription(reviewData.getReviewDescription().substring(0, 60));
        detail.setRearReviewDescription(reviewData.getReviewDescription());
      } else {
        detail.setReviewDescription(reviewData.getReviewDescription());
      }
      CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
      boolean customerLinkDisplay = !service.isWithdrawed(customerCode) && !service.isNotFound(customerCode)
          && (Permission.CUSTOMER_READ.isGranted(getLoginInfo()) || Permission.CUSTOMER_READ_SHOP.isGranted(getLoginInfo()));
      detail.setCustomerLinkDisplay(customerLinkDisplay);
      detail.setUpdatedDatetime(reviewData.getUpdatedDatetime());
      nextBean.getList().add(detail);
    }
  }

  /**
   * 表示状態の設定を行います。<BR>
   * （0:未チェック、1:表示、2:非表示）
   * 
   * @param displayType
   *          表示状態値
   * @return 表示状態名称
   */
  public String getType(long displayType) {
    String result = null;
    if (displayType == 0) {
      result = ReviewDisplayType.UNCHECKED.getName();
    } else if (displayType == 1) {
      result = ReviewDisplayType.DISPLAY.getName();
    } else if (displayType == 2) {
      result = ReviewDisplayType.HIDDEN.getName();
    }
    return result;
  }

}
