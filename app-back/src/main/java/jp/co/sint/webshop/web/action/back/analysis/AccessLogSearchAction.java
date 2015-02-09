package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.domain.DayOfWeek;
import jp.co.sint.webshop.service.AnalysisService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.analysis.AccessLogData;
import jp.co.sint.webshop.service.analysis.Conversion;
import jp.co.sint.webshop.service.analysis.CountType;
import jp.co.sint.webshop.service.analysis.PageView;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.UserAgent;
import jp.co.sint.webshop.utility.UserAgentManager;
import jp.co.sint.webshop.validation.DatetimeValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.AccessLogBean;
import jp.co.sint.webshop.web.bean.back.analysis.AccessLogBean.AccessLogBeanDetail;
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
public class AccessLogSearchAction extends WebBackAction<AccessLogBean> {

  private static final int MAXIMUM_MONTH = 12;

  private static final int MAXIMUM_TIME = 24;

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // モードに関係なく分析参照_サイトの権限を持つユーザのみがアクセス可能
    return Permission.ANALYSIS_READ_SITE.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    AccessLogBean bean = getBean();

    int year;
    int month;
    int day;
    if (StringUtil.hasValue(bean.getSearchYear())) {
      year = Integer.parseInt(bean.getSearchYear());
    } else {
      year = 1;
    }
    if (StringUtil.hasValue(bean.getSearchMonth())) {
      month = Integer.parseInt(bean.getSearchMonth());
    } else {
      month = 1;
    }
    if (StringUtil.hasValue(bean.getSearchDay())) {
      day = Integer.parseInt(bean.getSearchDay());
    } else {
      day = 1;
    }

    UserAgentManager manager = DIContainer.getUserAgentManager();
    List<UserAgent> agentsList = new ArrayList<UserAgent>();
    if (StringUtil.hasValue(bean.getClientGroupCondition())) {
      agentsList.add(manager.fromClientGroup(bean.getClientGroupCondition()));
    } else {
      agentsList.addAll(manager.getUserAgentList());
    }

    CountType type;
    List<String> labelsList = new ArrayList<String>();

    if (bean.getDisplayMode().equals("month")) {
      type = CountType.MONTHLY;
      for (int i = 1; i <= MAXIMUM_MONTH; i++) {
        labelsList.add(StringUtil.addZero("" + i, 2));
      }
    } else if (bean.getDisplayMode().equals("week")) {
      type = CountType.EVERY_DAY_OF_WEEK;
      for (DayOfWeek d : DayOfWeek.values()) {
        labelsList.add(d.getValue());
      }
    } else if (bean.getDisplayMode().equals("day")) {
      type = CountType.DAILY;
      Date date = DateUtil.fromYearMonth(bean.getSearchYear(), bean.getSearchMonth());

      if (date == null) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.SEARCHCONDITION_ERROR));

        return BackActionResult.RESULT_SUCCESS;
      }

      for (int i = 1; i <= Integer.parseInt(DateUtil.getEndDay(date)); i++) {
        labelsList.add(StringUtil.addZero("" + i, 2));
      }
    } else if (bean.getDisplayMode().equals("time")) {
      type = CountType.HOURLY;
      for (int i = 0; i < MAXIMUM_TIME; i++) {
        labelsList.add("" + i);
      }
    } else {
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(WebMessage.get(AnalysisErrorMessage.DISPLAY_MODE_ERROR));
      return BackActionResult.RESULT_SUCCESS;
    }

    AnalysisService service = ServiceLocator.getAnalysisService(getLoginInfo());

    AccessLogData data = service.getAccessLog(type, year, month, day, bean.getClientGroupCondition());

    bean.setExportAuthority(Permission.ANALYSIS_DATA_SITE.isGranted(getLoginInfo()));

    List<AccessLogBeanDetail> detailList = new ArrayList<AccessLogBeanDetail>();
    // 棒グラフ表示データ作成用
    Map<String, String> accessLogGraphMap = new HashMap<String, String>();
    StringBuilder conversionGraphBuilder = new StringBuilder();
    StringBuilder columnBuilder = new StringBuilder();
    String delimiter = "";
    conversionGraphBuilder.append(Messages.getString("web.action.back.analysis.AccessLogSearchAction.0"));

    for (String l : labelsList) {
      AccessLogBeanDetail detail = new AccessLogBeanDetail();

      Conversion conversion = data.getConversion(type, l);
      detail.setConversionRate(conversion.getConversionRate());
      detail.setLabel(conversion.getLabel());

      Long pageViewCount = 0L;
      for (UserAgent ua : agentsList) {
        PageView view = data.getPageView(type, l, ua.getClientGroup());
        pageViewCount += view.getAccessCount();

        String agentName = ua.getAgentName();
        if (accessLogGraphMap.containsKey(agentName)) {
          accessLogGraphMap.put(agentName, accessLogGraphMap.get(agentName) + ":" + view.getAccessCount());
        } else {
          accessLogGraphMap.put(agentName, NumUtil.toString(view.getAccessCount()));
        }
      }
      detail.setPageViewCount(NumUtil.toString(pageViewCount));

      columnBuilder.append(delimiter + conversion.getLabel());
      delimiter = ",";
      conversionGraphBuilder.append(":" + conversion.getConversionRate());
      detailList.add(detail);
    }
    bean.setSearchResult(detailList);

    delimiter = "";
    StringBuilder accessLogGraphBuilder = new StringBuilder();

    for (UserAgent ua : agentsList) {
      accessLogGraphBuilder.append(delimiter + ua.getAgentName() + ":" + accessLogGraphMap.get(ua.getAgentName()));
      delimiter = ",";
    }

    bean.setColumnData(columnBuilder.toString());
    bean.setConevrsionBarGraphData(conversionGraphBuilder.toString());
    bean.setAccessLogBarGraphData(accessLogGraphBuilder.toString());
    bean.setSearchResultDisplay(true);

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
          Messages.getString("web.action.back.analysis.AccessLogSearchAction.1"));

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
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.AccessLogSearchAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107011004";
  }

}
