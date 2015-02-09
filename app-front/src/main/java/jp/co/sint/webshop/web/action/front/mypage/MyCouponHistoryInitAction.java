package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.domain.CouponStatus;
import jp.co.sint.webshop.data.domain.CouponUse;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.MyCouponHistorySearchCondition;
import jp.co.sint.webshop.service.communication.NewCouponHistoryInfo;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.MyCouponHistoryBean;
import jp.co.sint.webshop.web.bean.front.mypage.MyCouponHistoryBean.MyCouponHistoryDetail;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageDisplayMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * クーポン履歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class MyCouponHistoryInitAction extends WebFrontAction<MyCouponHistoryBean> {

  private static final String PROPORTION_FLG = "%";

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    MyCouponHistoryBean bean = getBean();
    if (bean == null) {
      bean = new MyCouponHistoryBean();
    }
    // ロケールの言語を取得する
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();

    // ログイン情報
    FrontLoginInfo login = getLoginInfo();

    // 顧客サービス
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    MyCouponHistorySearchCondition condition = new MyCouponHistorySearchCondition();

    // 顧客番号
    condition.setCustomerCode(login.getCustomerCode());
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // クーポンルール情報を取得する
    SearchResult<NewCouponHistoryInfo> result = service.getCustomerNewCouponList(condition);

    // 画面情報を取得する
    bean.getCouponIssueList().clear();
    MyCouponHistoryDetail couponDetail = null;
    for (NewCouponHistoryInfo coupon : result.getRows()) {
      couponDetail = new MyCouponHistoryDetail();
      // 优惠券名称
      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
        couponDetail.setCouponName(coupon.getCouponName());

        // 优惠券名称(日文)
      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
        couponDetail.setCouponName(coupon.getCouponNameJp());

        // 优惠券名称(英文)
      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
        couponDetail.setCouponName(coupon.getCouponNameEn());
      }
      // 优惠金額
      if (CouponIssueType.FIXED.getValue().equals(coupon.getCouponIssueType())) {
        couponDetail.setFixedFlg(Boolean.TRUE);
        couponDetail.setCouponAmount(coupon.getCouponAmount());
      } else {
        if (StringUtil.hasValue(coupon.getCouponProportion())) {
          couponDetail.setCouponAmount(coupon.getCouponProportion() + PROPORTION_FLG);
        } else {
          couponDetail.setCouponAmount(StringUtil.EMPTY);
        }
      }
      // 优惠券使用最小购买金額
      couponDetail.setCouponBeginAmount(coupon.getMinUseOrderAmount());
      // 利用开始日时
      couponDetail.setUseStartDatetime(DateUtil.toDateString(coupon.getUseStartDatetime()));
      // 利用结束日时
      couponDetail.setUseEndDatetime(DateUtil.toDateString(coupon.getUseEndDatetime()));

      if (CouponStatus.USED.getValue().equals(coupon.getCouponStatus())) {
        if (coupon.getUseStatus().equals(CouponUse.UNUSED.getValue())
            && StringUtil.isCorrectRange(DateUtil.toDateTimeString(coupon.getUseEndDatetime()), DateUtil.toDateTimeString(DateUtil
                .getSysdate()))) {
          couponDetail.setUseStatus(CouponUse.OVERDATE.getName());
        } else {
          couponDetail.setUseStatus(CouponUse.fromValue(coupon.getUseStatus()).getName());
        }
      } else {
        couponDetail.setUseStatus(CouponStatus.fromValue(coupon.getCouponStatus()).getName());
      }
      bean.getCouponIssueList().add(couponDetail);
    }
    // ページ情報の追加
    bean.setPagerValue(PagerUtil.createValue(result));

    bean.setHasValue(true);
    if (bean.getCouponIssueList().isEmpty()) {
      bean.setHasValue(Boolean.FALSE);
    }
    // 検索結果が最大フェッチ件数を設定する
    if (result.isOverflow()) {
      this.addWarningMessage(WebMessage.get(MypageDisplayMessage.SEARCH_RESULT_OVERFLOW, NumUtil.formatNumber(""
          + result.getRowCount()), "" + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
    }
    setRequestBean(bean);
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
