package jp.co.sint.webshop.service.analysis;

import java.util.Date;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;

/**
 * ページビューとコンバージョン率を計算する為のクエリの基底クラス
 * 
 * @author System Integrator Corp.
 */
public abstract class AbstractAccessLogQuery extends SimpleQuery {

  private static final long serialVersionUID = 1L; // 10.1.4 K00149 追加

  /** 条件に当てはまる検索条件を作成します。 */
  public String createLabelFragment(CountType type) {
    String part = "";
    switch (type) {
      case MONTHLY:
        part = SqlDialect.getDefault().getMonthFromDate("ACCESS_DATE");
        break;
      case EVERY_DAY_OF_WEEK:
        part = SqlDialect.getDefault().getDayOfWeekFromDate("ACCESS_DATE");
        break;
      case DAILY:
        part = SqlDialect.getDefault().getDayFromDate("ACCESS_DATE");
        break;
      case HOURLY:
        part = "ACCESS_TIME";
        break;
      default:
        return "";
    }

    return part;
  }

  public SqlFragment createDateFragment(int year, int month, int day, CountType type) {
    Date startDate = null;
    Date endDate = null;
    switch (type) {
      case MONTHLY:
        startDate = DateUtil.fromString(year + "/01/01");
        endDate = DateUtil.fromString(year + "/12/31");
        break;
      case EVERY_DAY_OF_WEEK:
        // 週次・日次は同じ処理なのでfall throughで書く
      case DAILY:
        startDate = DateUtil.fromString(year + "/" + month + "/01");
        endDate = DateUtil.fromString(year + "/" + month + "/" + DateUtil.getEndDay(startDate));
        break;
      case HOURLY:
        startDate = DateUtil.fromString(year + "/" + month + "/" + day);
        endDate = startDate;
        break;
      default:
        return new SqlFragment();
    }

    return SqlDialect.getDefault().createDateRangeClause("ACCESS_DATE", startDate, endDate);
  }

}
