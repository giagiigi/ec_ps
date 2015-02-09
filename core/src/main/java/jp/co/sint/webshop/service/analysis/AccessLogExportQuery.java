package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateRange;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;

public class AccessLogExportQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  // 1日の時間数
  private static final int MAX_HOURS = 24;

  private String createLabel(String columnName, CountType type) {
    String label = columnName;
    switch (type) {
      case DAILY:
        label = SqlDialect.getDefault().getDayFromDate(columnName);
        break;
      case EVERY_DAY_OF_WEEK:
        label = SqlDialect.getDefault().getDayOfWeekFromDate(columnName);
        break;
      case MONTHLY:
        label = SqlDialect.getDefault().getMonthFromDate(columnName);
        break;
      default:
        break;
    }

    return label;
  }

  // カレンダーの作成
  private Query createCalendar(Date start, Date end) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    if (DIContainer.getWebshopConfig().isPostgreSQL()) {
      builder.append("SELECT * FROM get_all_date(?, ?) CAL_DATE(CAL_DATE date)");
      params.add(start);
      params.add(end);
    } else {
      builder.append(" SELECT ");
      builder.append("   ? + LEVEL - 1 AS CAL_DATE ");
      builder.append(" FROM ");
      builder.append("   DUAL ");
      builder.append(" CONNECT BY ");
      builder.append("   ? + LEVEL - 1 <= ? ");
      params.add(start);
      params.add(start);
      params.add(end);
    }

    return new SimpleQuery(builder.toString(), params.toArray());
  }

  private Query createHourlyQuery(DateRange range, String clientGroup) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append(" SELECT ");
    builder.append("   CALENDAR.CAL_HOUR AS LABEL, ");
    builder.append("   SUM(COALESCE(ACC.PAGE_VIEW_COUNT,0)) AS PAGE_VIEW_COUNT, ");
    builder.append("   CASE ");
    builder.append("     WHEN SUM(COALESCE(ACC.VISITOR_COUNT,0)) = 0 THEN 0 ");
    builder.append("     ELSE ROUND(SUM(COALESCE(ACC.PURCHASER_COUNT,0)) * 100 / SUM(COALESCE(ACC.VISITOR_COUNT,0)),1) ");
    builder.append("   END AS CONVERSION_RATE ");
    builder.append(" FROM ");
    builder.append("   ( ");
    if (DIContainer.getWebshopConfig().isPostgreSQL()) {
      builder.append("   SELECT ");
      builder.append("    * FROM get_all_hour(?) CAL_HOUR (CAL_HOUR numeric)");
    } else {
      builder.append("   SELECT ");
      builder.append("     LEVEL - 1 AS CAL_HOUR ");
      builder.append("   FROM ");
      builder.append("     DUAL ");
      builder.append("   CONNECT BY ");
      builder.append("     LEVEL - 1 < ? ");
    }    
    params.add(MAX_HOURS);
    builder.append("   ) CALENDAR ");
    builder.append(" LEFT OUTER JOIN ");
    builder.append("   ( ");
    builder.append("   SELECT ");
    builder.append("     ACCESS_TIME, ");
    builder.append("     PAGE_VIEW_COUNT, ");
    builder.append("     PURCHASER_COUNT, ");
    builder.append("     VISITOR_COUNT ");
    builder.append("   FROM ");
    builder.append("     ACCESS_LOG ");
    builder.append("   WHERE ");
    builder.append("     1 = 1 ");
    SqlFragment fragment = SqlDialect.getDefault().createDateRangeClause("ACCESS_DATE", range.getStart(), range.getEnd());
    if (StringUtil.hasValue(fragment.getFragment())) {
      builder.append(" AND " + fragment.getFragment());
      for (Object o : fragment.getParameters()) {
        params.add(o);
      }
    }
    if (StringUtil.hasValue(clientGroup)) {
      builder.append(" AND ");
      builder.append("   CLIENT_GROUP = ? ");
      params.add(clientGroup);
    }
    builder.append("   ) ACC ");
    builder.append(" ON ");
    builder.append("   CALENDAR.CAL_HOUR = ACC.ACCESS_TIME ");
    builder.append(" GROUP BY ");
    builder.append("   CALENDAR.CAL_HOUR ");
    builder.append(" ORDER BY ");
    builder.append("   CALENDAR.CAL_HOUR");

    return new SimpleQuery(builder.toString(), params.toArray());
  }

  public AccessLogExportQuery(DateRange range, String clientGroup, CountType type) {
    if (type.equals(CountType.HOURLY)) {
      Query q = createHourlyQuery(range, clientGroup);
      setSqlString(q.getSqlString());
      setParameters(q.getParameters());
    } else {
      List<Object> params = new ArrayList<Object>();
      StringBuilder builder = new StringBuilder();
      builder.append(" SELECT ");
      builder.append("   " + createLabel("CALENDAR.CAL_DATE", type) + " AS LABEL, ");
      builder.append("   SUM(COALESCE(ACC.PAGE_VIEW_COUNT,0)) AS PAGE_VIEW_COUNT, ");
      builder.append("   CASE ");
      builder.append("     WHEN SUM(COALESCE(ACC.VISITOR_COUNT,0)) = 0 THEN 0 ");
      builder.append("     ELSE ROUND(SUM(COALESCE(ACC.PURCHASER_COUNT,0)) * 100 / SUM(COALESCE(ACC.VISITOR_COUNT,0)),1) ");
      builder.append("   END AS CONVERSION_RATE ");
      builder.append(" FROM ");
      builder.append(" ( ");
      Query calendarQuery = createCalendar(range.getStart(), range.getEnd());
      for (Object o : calendarQuery.getParameters()) {
        params.add(o);
      }
      builder.append("   " + calendarQuery.getSqlString());
      builder.append(" ) CALENDAR ");
      builder.append(" LEFT OUTER JOIN ");
      builder.append(" ( ");
      builder.append(" SELECT ");
      builder.append("   ACCESS_DATE, ");
      builder.append("   PAGE_VIEW_COUNT, ");
      builder.append("   PURCHASER_COUNT, ");
      builder.append("   VISITOR_COUNT ");
      builder.append(" FROM ");
      builder.append("   ACCESS_LOG ");
      builder.append(" WHERE ");
      builder.append("   1 = 1 ");
      SqlFragment fragment = SqlDialect.getDefault().createDateRangeClause("ACCESS_DATE", range.getStart(), range.getEnd());
      if (StringUtil.hasValue(fragment.getFragment())) {
        builder.append(" AND " + fragment.getFragment());
        for (Object o : fragment.getParameters()) {
          params.add(o);
        }
      }
      if (StringUtil.hasValue(clientGroup)) {
        builder.append(" AND ");
        builder.append("   CLIENT_GROUP = ? ");
        params.add(clientGroup);
      }
      builder.append(" ) ACC ");
      builder.append(" ON ");
      builder.append("   CALENDAR.CAL_DATE = ACC.ACCESS_DATE ");
      builder.append(" GROUP BY ");
      builder.append("   " + createLabel("CALENDAR.CAL_DATE", type));
      builder.append(" ORDER BY ");
      builder.append("   " + createLabel("CALENDAR.CAL_DATE", type));

      setSqlString(builder.toString());
      setParameters(params.toArray());
    }
  }
}
