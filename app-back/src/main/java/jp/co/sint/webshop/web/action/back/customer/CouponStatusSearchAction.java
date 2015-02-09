package jp.co.sint.webshop.web.action.back.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CouponUsedFlg;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CouponStatusAllInfo;
import jp.co.sint.webshop.service.customer.CouponStatusSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.CouponStatusBean;
import jp.co.sint.webshop.web.bean.back.customer.CouponStatusBean.CouponStatusDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1030999:商品券使用状况查询Action
 * 
 * @author swj
 */
public class CouponStatusSearchAction extends WebBackAction<CouponStatusBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_COUPON_READ.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    boolean result = true;
    CouponStatusBean bean = getBean();

    // bean(検索条件)のvalidationチェック
    result = validateBean(bean);
    if (!result) {
      return result;
    }

    // 日付の大小関係チェック

    CouponStatusSearchCondition validatedCondition = new CouponStatusSearchCondition();
    validatedCondition.setSearchIssueFromDate(bean.getSearchIssueFromDate());
    validatedCondition.setSearchIssueToDate(bean.getSearchIssueToDate());

    if (!validatedCondition.isValid()) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
      result = false;
    }

    return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());
    CouponStatusSearchCondition condition = new CouponStatusSearchCondition();

    CouponStatusBean bean = getBean();
    condition.setSearchIssueFromDate(bean.getSearchIssueFromDate());
    condition.setSearchIssueToDate(bean.getSearchIssueToDate());
    condition.setSearchCouponStatus(bean.getSearchCouponStatus());
    condition.setSearchSummaryCondition(bean.getSearchSummaryCondition());

    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    SearchResult<CouponStatusAllInfo> result = cs.findCouponStatusDetailInfo(condition);

    // 検索結果0件チェック

    if (result.getRowCount() == 0) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
    }
    // オーバーフローチェック

    if (result.isOverflow()) {
      this.addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW, NumUtil.formatNumber(""
          + result.getRowCount()), "" + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
    }

    // ページ情報を追加
    bean.setPagerValue(PagerUtil.createValue(result));

    List<CouponStatusAllInfo> detailList = result.getRows();
    // 一覧情報設定

    List<CouponStatusDetail> list = new ArrayList<CouponStatusDetail>();
    bean.setDetailList(null);
    for (CouponStatusAllInfo dl : detailList) {
      CouponStatusDetail detail = new CouponStatusDetail();
      detail.setCustomerCouponId(dl.getCustomerCouponId());
      detail.setCouponIssueNo(dl.getCouponIssueNo());
      detail.setCouponName(dl.getCouponName());
      detail.setCouponPrice(dl.getCouponPrice());
      detail.setIssueDate(DateUtil.toDateString(dl.getIssueDate()));
      detail.setUseFlg(CouponUsedFlg.fromValue(dl.getUseFlg()).getName());
      detail.setCustomerCode(dl.getCustomerCode());
      detail.setOrderNo(dl.getOrderNo());
      detail.setUseDate(DateUtil.toDateString(dl.getUseDate()));
      detail.setCustomerName(dl.getCustomerName());
      // Add by V10-CH start
      detail.setUseCouponEndDate(DateUtil.toDateString(dl.getUseCouponEndDate()));
      // Add by V10-CH end
      list.add(detail);
    }
    bean.setDetailList(list);
    bean.setDisplayTarget(true);
    // 集計対象がショップ別の場合

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.CouponStatusSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103099902";
  }
}
