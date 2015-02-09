package jp.co.sint.webshop.web.action.back.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.PointIssueStatus;
import jp.co.sint.webshop.data.domain.PointIssueType;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.PointStatusAllSearchInfo;
import jp.co.sint.webshop.service.customer.PointStatusListSearchCondition;
import jp.co.sint.webshop.service.customer.PointStatusShopSearchInfo;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.PointStatusBean;
import jp.co.sint.webshop.web.bean.back.customer.PointStatusBean.PointStatusDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1030310:ポイント利用状況のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PointStatusSearchAction extends WebBackAction<PointStatusBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_POINT_READ.isGranted(login);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());
    PointStatusListSearchCondition condition = new PointStatusListSearchCondition();

    // 検索条件をセット
    PointStatusBean bean = getBean();
    condition.setSearchShopCode(bean.getSearchShopCode());
    condition.setSearchIssueType(bean.getSearchIssueType());
    condition.setSearchPointIssueStatus(bean.getSearchPointIssueStatus());
    condition.setSearchPointIssueStartDate(bean.getSearchPointIssueStartDate());
    condition.setSearchPointIssueEndDate(bean.getSearchPointIssueEndDate());
    condition.setSearchSummaryCondition(bean.getSearchSummaryCondition());

    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // 検索処理実行
    // 集計対象が全明細の場合
    if (bean.getSearchSummaryCondition().equals(PointStatusBean.SUMMARY_TYPE_ALL)) {
      SearchResult<PointStatusAllSearchInfo> result = cs.findPointStatusInfo(condition);

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

      List<PointStatusAllSearchInfo> allList = result.getRows();

      // 一覧情報設定
      List<PointStatusDetail> list = new ArrayList<PointStatusDetail>();
      bean.setAllList(null);
      for (PointStatusAllSearchInfo pl : allList) {
        PointStatusDetail detail = new PointStatusDetail();
        detail.setShopCode(pl.getShopCode());
        detail.setShopName(pl.getShopName());
        detail.setCustomerCode(pl.getCustomerCode());
        detail.setCustomerLastName(pl.getLastName());
        detail.setCustomerFirstName(pl.getFirstName());
        detail.setIssueTypeName(PointIssueType.fromValue(pl.getPointIssueType()).getName());
        detail.setIssuedPoint(pl.getIssuedPoint());
        detail.setIssuedStatusName(PointIssueStatus.fromValue(pl.getPointIssueStatus()).getName());
        detail.setOrderNo(pl.getOrderNo());
        detail.setOrderDate(pl.getOrderDatetime());
        detail.setPaymentDate(pl.getPaymentDate());
        detail.setPointIssueDatetime(pl.getPointIssueDatetime());

        list.add(detail);
        bean.setAllList(list);
      }
      bean.setDisplayTarget("all");
      // 集計対象がショップ別の場合
    } else if (bean.getSearchSummaryCondition().equals(PointStatusBean.SUMMARY_TYPE_SHOP)) {
      SearchResult<PointStatusShopSearchInfo> result = cs.findPointStatusShopInfo(condition);

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

      List<PointStatusShopSearchInfo> shopList = result.getRows();

      // 一覧情報設定
      List<PointStatusDetail> list = new ArrayList<PointStatusDetail>();
      bean.setShopList(null);
      for (PointStatusShopSearchInfo pl : shopList) {
        PointStatusDetail detail = new PointStatusDetail();
        detail.setShopCode(pl.getShopCode());
        detail.setShopName(pl.getShopName());
        detail.setIneffectivePoint(pl.getIneffectivePoint());
        detail.setTemporaryPoint(pl.getTemporaryPoint());
        detail.setEffectivePoint(pl.getRestPoint());

        list.add(detail);
        bean.setShopList(list);
      }
      bean.setDisplayTarget("shop");
    }

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    boolean result = true;
    PointStatusBean bean = getBean();

    // bean(検索条件)のvalidationチェック
    result = validateBean(bean);
    if (!result) {
      return result;
    }

    // 日付の大小関係チェック
    PointStatusListSearchCondition validatedCondition = new PointStatusListSearchCondition();
    validatedCondition.setSearchPointIssueStartDate(bean.getSearchPointIssueStartDate());
    validatedCondition.setSearchPointIssueEndDate(bean.getSearchPointIssueEndDate());

    if (!validatedCondition.isValid()) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.SEARCHCONDITION_ERROR));
      result = false;
    }

    return result;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.PointStatusSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103031003";
  }

}
