package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.analysis.SearchKeywordLogSearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.SearchKeywordLogExportCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.analysis.SearchKeywordLogBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070910:検索キーワード集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class SearchKeywordLogExportAction extends SearchKeywordLogBaseAction implements WebExportAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    // モードに関係なく分析データ入出力_サイトの権限を持つユーザのみアクセス可能
    return Permission.ANALYSIS_DATA_SITE.isGranted(login);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    SearchKeywordLogBean bean = getBean();

    SearchKeywordLogSearchCondition searchCondition = new SearchKeywordLogSearchCondition();

    searchCondition.setSearchKey(bean.getSearchKeyCondition());
    searchCondition.setSearchStartDate(DateUtil.fromString(bean.getSearchStartDate()));
    searchCondition.setSearchEndDate(DateUtil.fromString(bean.getSearchEndDate()));

    SearchKeywordLogExportCondition condition = CsvExportType.EXPORT_CSV_SEARCH_KEYWORD_LOG.createConditionInstance();
    condition.setSearchCondition(searchCondition);
    this.exportCondition = condition;

    setNextUrl("/download");
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

  private CsvExportCondition<? extends CsvSchema> exportCondition;

  public CsvExportCondition<? extends CsvSchema> getExportCondition() {
    return this.exportCondition;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.SearchKeywordLogExportAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107091001";
  }

}
