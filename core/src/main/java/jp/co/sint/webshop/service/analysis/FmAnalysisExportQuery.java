package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SimpleQuery;
//import jp.co.sint.webshop.data.domain.PointAmplificationRate;
//import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
//import jp.co.sint.webshop.utility.PointUtil;

public class FmAnalysisExportQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public FmAnalysisExportQuery(RfmAnalysisExportSearchCondition condition) {
    StringBuilder builder = new StringBuilder();

    List<Object> paramsList = new ArrayList<Object>();

//  modify by V10-CH 170 start
//  builder.append(DatabaseUtil.getSelectAllQuery(Customer.class));
    builder.append("select ");
    builder.append("customer_code, ");
    // delete by lc 2012-03-12 start
//    builder.append("customer_group_code, ");
//    builder.append("last_name, ");
//    builder.append("login_id, ");
    builder.append("email ");
//    builder.append("password, ");
//    builder.append("birth_date, ");
//    builder.append("sex, ");
//    builder.append("request_mail_type, ");
//    builder.append("client_mail_type, ");
//    builder.append("caution, ");
//    builder.append("login_datetime, ");
//    builder.append("login_error_count, ");
//    builder.append("login_locked_flg, ");
//    builder.append("customer_status, ");
//    builder.append("customer_attribute_reply_date, ");
//    builder.append("latest_point_acquired_date, ");
//    //modify by V10-CH start
//    //builder.append("rest_point/").append(DIContainer.getWebshopConfig().getPointMultiple()).append(", ");
//    //builder.append("temporary_point/").append(DIContainer.getWebshopConfig().getPointMultiple()).append(", ");
//    builder.append("trim(to_char(trunc(rest_point/").append(DIContainer.getWebshopConfig().getPointMultiple()).append(",").append(
//        PointUtil.getAcquiredPointScale()).append("),'").append(
//        PointAmplificationRate.fromValue(String.valueOf(PointUtil.getAcquiredPointScale())).getName()).append(
//        "')) AS rest_point,");
//    builder.append("trim(to_char(trunc(temporary_point/").append(DIContainer.getWebshopConfig().getPointMultiple()).append(",")
//        .append(PointUtil.getAcquiredPointScale()).append("),'").append(
//            PointAmplificationRate.fromValue(String.valueOf(PointUtil.getAcquiredPointScale())).getName()).append(
//            "')) AS temporary_point,");
//    //modify by V10-CH end
//    builder.append("withdrawal_request_date, ");
//    builder.append("withdrawal_date, ");
//    builder.append("orm_rowid, ");
//    builder.append("created_user, ");
//    builder.append("created_datetime, ");
//    builder.append("updated_user, ");
//    builder.append("updated_datetime ");
    // delete by lc 2012-03-12 end
    builder.append("from customer ");
//modify by V10-CH 170 end
    builder.append(" WHERE ");
    builder.append("  CUSTOMER_CODE IN");
    builder.append(" (");
    builder.append("  SELECT");
    builder.append("    CUSTOMER_CODE");
    builder.append("  FROM");
    // Fの条件ここから
    builder.append("   (");
    builder.append("   SELECT");
    builder.append("     CUSTOMER_CODE");
    builder.append("   FROM ");
    builder.append("     RFM");
    builder.append("   WHERE");
    builder.append("     ORDER_DATE >= ?");

    paramsList.add(DateUtil.truncateDate(DateUtil.addMonth(DateUtil.getSysdate(), -1 * condition.getFrequencyPeriod())));

    builder.append("   GROUP BY");
    builder.append("     CUSTOMER_CODE");
    builder.append("   HAVING");

    switch (condition.getFrequencyRank()) {
      case FA:
        builder.append("     COUNT(ORDER_NO) >= ?");
        paramsList.add(condition.getFrequencyThresholdA());
        break;
      case FB:
        builder.append("     ? > COUNT(ORDER_NO) AND COUNT(ORDER_NO) >= ?");
        paramsList.add(condition.getFrequencyThresholdA());
        paramsList.add(condition.getFrequencyThresholdB());
        break;
      default:
        builder.append("     ? > COUNT(ORDER_NO)");
        paramsList.add(condition.getFrequencyThresholdB());
        break;
    }

    builder.append("   )  RFM1");
    // Fの条件ここまで
    builder.append("    JOIN");
    // Mの条件ここから
    builder.append("    (");
    builder.append("    SELECT");
    builder.append("      CUSTOMER_CODE");
    builder.append("    FROM");
    builder.append("      RFM");
    builder.append("    WHERE");
    builder.append("      ORDER_DATE >= ?");

    paramsList.add(DateUtil.truncateDate(DateUtil.addMonth(DateUtil.getSysdate(), -1 * condition.getMonetaryPeriod())));

    builder.append("    GROUP BY");
    builder.append("      CUSTOMER_CODE");
    builder.append("    HAVING");

    switch (condition.getMonetaryRank()) {
      case MA:
        builder.append("      SUM(TOTAL_PURCHASED_AMOUNT) >= ?");
        paramsList.add(condition.getMonetaryThresholdA());
        break;
      case MB:
        builder.append("      ? > SUM(TOTAL_PURCHASED_AMOUNT) AND SUM(TOTAL_PURCHASED_AMOUNT) >= ?");
        paramsList.add(condition.getMonetaryThresholdA());
        paramsList.add(condition.getMonetaryThresholdB());
        break;
      default:
        builder.append("      ? > SUM(TOTAL_PURCHASED_AMOUNT) ");
        paramsList.add(condition.getMonetaryThresholdB());
        break;
    }

    builder.append("    )  RFM2 ");
    // Mの条件ここまで
    builder.append("    USING ( CUSTOMER_CODE )");
    builder.append("  )");

    builder.append(" ORDER BY CUSTOMER_STATUS, CUSTOMER_CODE ");

    this.setParameters(paramsList.toArray());
    this.setSqlString(builder.toString());
  }

}
