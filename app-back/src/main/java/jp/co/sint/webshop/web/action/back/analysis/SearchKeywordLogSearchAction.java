package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.AnalysisService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.analysis.SearchKeywordLogSearchCondition;
import jp.co.sint.webshop.service.analysis.SearchKeywordLogSummary;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.analysis.SearchKeywordLogBean;
import jp.co.sint.webshop.web.bean.back.analysis.SearchKeywordLogBean.SearchKeywordLogBeanDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1070910:検索キーワード集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class SearchKeywordLogSearchAction extends SearchKeywordLogBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // モードに関係なく分析参照_サイトの権限を持つユーザのみ表示可能
    return Permission.ANALYSIS_READ_SITE.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    SearchKeywordLogBean bean = getBean();

    AnalysisService service = ServiceLocator.getAnalysisService(getLoginInfo());

    SearchKeywordLogSearchCondition condition = new SearchKeywordLogSearchCondition();

    condition.setSearchKey(bean.getSearchKeyCondition());
    condition.setSearchStartDate(DateUtil.fromString(bean.getSearchStartDate()));
    condition.setSearchEndDate(DateUtil.fromString(bean.getSearchEndDate()));

    PagerUtil.createSearchCondition(getRequestParameter(), condition);

    SearchResult<SearchKeywordLogSummary> searchResult = service.getSearchKeywordLog(condition);

    bean.setSearchKeyList(createSearchKeyList());
    bean.setExportAuthority(hasExportAuthority());

    bean.setPagerValue(PagerUtil.createValue(searchResult));

    if (searchResult.getRowCount() == 0) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
      // 検索結果が0件のときは結果をクリア
      bean.setSearchResult(new ArrayList<SearchKeywordLogBeanDetail>());
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    } else if (searchResult.isOverflow()) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW, NumUtil.formatNumber(""
          + searchResult.getRowCount()), "" + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
    }

    List<SearchKeywordLogBeanDetail> resultList = new ArrayList<SearchKeywordLogBeanDetail>();

    long graphScale = NumUtil.toLong(bean.getScaleCondition());

    for (SearchKeywordLogSummary r : searchResult.getRows()) {
      SearchKeywordLogBeanDetail detail = new SearchKeywordLogBeanDetail();

      detail.setSearchKey(r.getSearchKey());
      detail.setSearchWord(r.getSearchWord());
      detail.setSearchCount(NumUtil.toString(r.getSearchCount()));

      long graphCount;
      if (graphScale <= 0L) {
        graphCount = 0L;
      } else {
        graphCount = r.getSearchCount() / graphScale;
        detail.setFraction(r.getSearchCount() % graphScale != 0);
      }

      detail.setGraphCount(NumUtil.toString(graphCount));

      resultList.add(detail);
    }
    bean.setSearchResult(resultList);

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
    boolean result = false;

    SearchKeywordLogBean bean = getBean();
    if (validateBean(bean)) {
      result = true;
      if (StringUtil.hasValue(bean.getSearchStartDate()) && StringUtil.hasValue(bean.getSearchEndDate())) {
        if (StringUtil.isCorrectRange(bean.getSearchStartDate(), bean.getSearchEndDate())) {
          result &= true;
        } else {
          addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
          result &= false;
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
    return Messages.getString("web.action.back.analysis.SearchKeywordLogSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107091003";
  }

}
