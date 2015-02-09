package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.analysis.CountType;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.AccessLogExportCondition;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateRange;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.UserAgent;
import jp.co.sint.webshop.utility.UserAgentManager;
import jp.co.sint.webshop.validation.DatetimeValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.AccessLogBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.analysis.AnalysisErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1070110:アクセスログ集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class AccessLogExportAction extends WebBackAction<AccessLogBean> implements WebExportAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 分析データ出力_サイトの権限を持つユーザのみがCSV出力可能
    return Permission.ANALYSIS_DATA_SITE.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    AccessLogBean bean = getBean();

    UserAgentManager manager = DIContainer.getUserAgentManager();
    List<UserAgent> agentsList = new ArrayList<UserAgent>();
    if (StringUtil.hasValue(bean.getClientGroupCondition())) {
      agentsList.add(manager.fromClientGroup(bean.getClientGroupCondition()));
    } else {
      agentsList.addAll(manager.getUserAgentList());
    }

    CountType type;

    Date start = null;
    Date end = null;

    if (bean.getDisplayMode().equals("month")) {
      type = CountType.MONTHLY;
      start = DateUtil.fromYear(bean.getSearchYear());
      int year = NumUtil.toLong(bean.getSearchYear()).intValue();
      end = DateUtil.addDate(DateUtil.fromYear("" + (year + 1)), -1);
    } else if (bean.getDisplayMode().equals("week")) {
      type = CountType.EVERY_DAY_OF_WEEK;
      start = DateUtil.fromYearMonth(bean.getSearchYear(), bean.getSearchMonth());
      String endDate = DateUtil.getEndDay(start);
      int daysToEndDay = NumUtil.toLong(endDate).intValue() - 1;
      end = DateUtil.addDate(start, daysToEndDay);
    } else if (bean.getDisplayMode().equals("day")) {
      type = CountType.DAILY;
      start = DateUtil.fromYearMonth(bean.getSearchYear(), bean.getSearchMonth());
      String endDate = DateUtil.getEndDay(start);
      int daysToEndDay = NumUtil.toLong(endDate).intValue() - 1;
      end = DateUtil.addDate(start, daysToEndDay);
    } else if (bean.getDisplayMode().equals("time")) {
      type = CountType.HOURLY;
      start = DateUtil.fromString(bean.getSearchYear()
          + "/" + bean.getSearchMonth() + "/" + bean.getSearchDay());
      end = start;
    } else {
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(WebMessage.get(AnalysisErrorMessage.DISPLAY_MODE_ERROR));
      return BackActionResult.RESULT_SUCCESS;
    }

    if (start == null || end == null) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.SEARCHCONDITION_ERROR));
      return BackActionResult.RESULT_SUCCESS;
    }

    AccessLogExportCondition condition = CsvExportType.EXPORT_CSV_ACCESS_LOG.createConditionInstance();
    condition.setClientGroup(bean.getClientGroupCondition());
    condition.setType(type);
    condition.setRange(new DateRange(start, end));
    this.exportCondition = condition;

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }
  
  private CsvExportCondition<? extends CsvSchema> exportCondition;
  
  public CsvExportCondition<? extends CsvSchema> getExportCondition() {
    return exportCondition;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    AccessLogBean bean = getBean();
    boolean result = validateBean(bean);

    if (result) {
      // 表示モードが正しいかどうか
      String dispMode = bean.getDisplayMode();

      // 日付書式の確認，月，日がないものについてはそれぞれ"01"を補って判定する
      String dateString = "";

      if (dispMode.equals("time")) {
        dateString = bean.getSearchYear() + "/" + bean.getSearchMonth() + "/" + bean.getSearchDay();
      } else if (dispMode.equals("day")) {
        dateString = bean.getSearchYear() + "/" + bean.getSearchMonth() + "/" + "01";
      } else if (dispMode.equals("week")) {
        dateString = bean.getSearchYear() + "/" + bean.getSearchMonth() + "/" + "01";
      } else if (dispMode.equals("month")) {
        dateString = bean.getSearchYear() + "/" + "01" + "/" + "01";
      } else {
        addErrorMessage(WebMessage.get(AnalysisErrorMessage.DISPLAY_MODE_ERROR));
        return false;
      }

      DatetimeValidator datetimeValidator = new DatetimeValidator("yyyy/MM/dd",
          Messages.getString("web.action.back.analysis.AccessLogExportAction.0"));

      if (datetimeValidator.isValid(dateString)) {
        result &= true;
      } else {
        result &= false;
        addErrorMessage(datetimeValidator.getMessage());
      }
    }

    return result;
  }

  /**
   * 遷移先の画面のURLを取得します。
   * 
   * @return 遷移先の画面のURL
   */
  public String getNextUrl() {
    return "/download";
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.AccessLogExportAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107011001";
  }

}
