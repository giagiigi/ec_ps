package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.analysis.CommodityAccessLogSearchCondition;
import jp.co.sint.webshop.service.analysis.CommodityAccessLogSummary;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.analysis.CommodityAccessLogBean;
import jp.co.sint.webshop.web.bean.back.analysis.CommodityAccessLogBean.CommodityAccessLogBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.analysis.AnalysisErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1070210:商品別アクセスログ集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CommodityAccessLogSearchAction extends CommodityAccessLogBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    // 分析参照_ショップの権限を持つユーザは一店舗モードでないときのみ参照可能
    // 分析参照_サイトの権限を持つユーザは常に参照可能
    return (Permission.ANALYSIS_READ_SHOP.isGranted(login) && !getConfig().isOne())
        || Permission.ANALYSIS_READ_SITE.isGranted(login);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CommodityAccessLogBean bean = getBean();
    // 検索条件の設定
    CommodityAccessLogSearchCondition condition = new CommodityAccessLogSearchCondition();

    // ショップ管理者は自分のショップ以外を検索不可
    if (getLoginInfo().isShop()) {
      if (!bean.getShopCodeCondition().equals(getLoginInfo().getShopCode())) {
        addErrorMessage(WebMessage.get(AnalysisErrorMessage.PERMISSION_ERROR));
        return BackActionResult.RESULT_SUCCESS;
      }
    }

    condition.setSearchStartDate(DateUtil.fromString(bean.getSearchStartDate()));
    condition.setSearchEndDate(DateUtil.fromString(bean.getSearchEndDate()));
    condition.setClientGroup(bean.getClientGroupCondition());
    condition.setShopCode(bean.getShopCodeCondition());
    condition.setSearchCommodityCodeStart(bean.getSearchCommodityCodeStart());
    condition.setSearchCommodityCodeEnd(bean.getSearchCommodityCodeEnd());
    condition.setCommodityName(bean.getCommodityName());

    PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // 検索結果をbeanに格納
    SearchResult<CommodityAccessLogSummary> result = ServiceLocator.getAnalysisService(getLoginInfo()).getCommodityAccessLog(
        condition);

    bean.setClientGroupList(createClientGroupList());

    bean.setShopCodeList(createShopList());
    bean.setPagerValue(PagerUtil.createValue(result));

    if (result.getRowCount() == 0) {
      this.addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
      bean.setSearchResultList(new ArrayList<CommodityAccessLogBeanDetail>());
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }
    if (result.isOverflow()) {
      this.addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW, NumUtil.formatNumber(""
          + result.getRowCount()), "" + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
    }
    long scale = NumUtil.toLong(bean.getScaleCondition());

    List<CommodityAccessLogBeanDetail> resultList = new ArrayList<CommodityAccessLogBeanDetail>();
    for (CommodityAccessLogSummary r : result.getRows()) {
      CommodityAccessLogBeanDetail detail = new CommodityAccessLogBeanDetail();
      detail.setShopName(r.getShopName());
      detail.setCommodityName(r.getCommodityName());
      detail.setCommodityAccessLogCount(NumUtil.toString(r.getAccessCount()));

      long accessCount = r.getAccessCount();

      // グラフ表示スケールが0以下の場合はグラフを表示しない
      if (scale <= 0) {
        detail.setGraphCount("0");
      } else {
        detail.setGraphCount(NumUtil.toString((accessCount / scale)));
        detail.setFraction(accessCount % scale != 0);
      }

      resultList.add(detail);
    }
    bean.setSearchResultList(resultList);

    // 一店舗版のときあるいはショップ管理者のときは自ショップのみを表示する
    if (getLoginInfo().isShop() || getConfig().isOne()) {
      bean.setShopCodeCondition(getLoginInfo().getShopCode());
      bean.setShopCodeDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
    } else {
      bean.setShopCodeDisplayMode(WebConstantCode.DISPLAY_EDIT);
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
    CommodityAccessLogBean searchBean = getBean();
    boolean result = false;

    if (validateBean(searchBean)) {
      result = true;
      // 検索期間が正しいか判定
      if (StringUtil.isCorrectRange(searchBean.getSearchStartDate(), searchBean.getSearchEndDate())) {
        result &= true;
      } else {
        result &= false;
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
      }

      // 検索商品コード開始，終了共に値があるとき，
      // 範囲が正しいか判定
      if (StringUtil.hasValue(searchBean.getSearchCommodityCodeStart())
          && StringUtil.hasValue(searchBean.getSearchCommodityCodeEnd())) {
        if (StringUtil.isCorrectRange(searchBean.getSearchCommodityCodeStart(), searchBean.getSearchCommodityCodeEnd())) {
          result &= true;
        } else {
          result &= false;
          addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR,
              Messages.getString("web.action.back.analysis.CommodityAccessLogSearchAction.0")));
        }
      }
    }
    return result;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.CommodityAccessLogSearchAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107021003";
  }

}
