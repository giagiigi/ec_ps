package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SimpleQuery;
//import jp.co.sint.webshop.data.domain.PointAmplificationRate;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
//import jp.co.sint.webshop.utility.PointUtil;

public class RfAnalysisExportQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public RfAnalysisExportQuery(RfmAnalysisExportSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    // if (condition.getScale() == 100) {
    // 6
    // }

    List<Object> paramsList = new ArrayList<Object>();
    // modify by V10-CH 170 start
    // builder.append(DatabaseUtil.getSelectAllQuery(Customer.class));
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

    // trunc(rest_point/10000,(select count_number(amplification_rate) from
    // point_rule))
    // builder.append("rest_point/").append(DIContainer.getWebshopConfig().getPointMultiple()).append(",
    // ");
    // builder.append("temporary_point/").append(DIContainer.getWebshopConfig().getPointMultiple()).append(",
    // ");
//    builder.append("trim(to_char(trunc(rest_point").append(",").append(
//        PointUtil.getAcquiredPointScale()).append("),'").append(
//        PointAmplificationRate.fromValue(Integer.toString(PointUtil.getAcquiredPointScale())).getName()).append(
//        "')) AS rest_point,");
//    builder.append("trim(to_char(trunc(temporary_point").append(",")
//        .append(PointUtil.getAcquiredPointScale()).append("),'").append(
//            PointAmplificationRate.fromValue(Integer.toString(PointUtil.getAcquiredPointScale())).getName()).append(
//            "')) AS temporary_point,");
//
//    builder.append("withdrawal_request_date, ");
//    builder.append("withdrawal_date, ");
//    builder.append("orm_rowid, ");
//    builder.append("created_user, ");
//    builder.append("created_datetime, ");
//    builder.append("updated_user, ");
//    builder.append("updated_datetime ");
    // delete by lc 2012-03-12 end    
    builder.append("from customer ");
    // modify by V10-CH end
    builder.append(" WHERE CUSTOMER_CODE IN");
    builder.append(" (");
    builder.append(" SELECT");
    builder.append("   CUSTOMER_CODE");
    builder.append(" FROM");
    // Rの条件ここから

    builder.append("   (");
    builder.append("   SELECT");
    builder.append("     CUSTOMER_CODE");
    builder.append("   FROM");
    builder.append("     RFM");
    builder.append("   GROUP BY");
    builder.append("     CUSTOMER_CODE");
    builder.append("   HAVING");
    switch (condition.getRecencyRank()) {
      case RA:
        builder.append("     MAX(ORDER_DATE) >= ?");
        paramsList.add(DateUtil.truncateDate(DateUtil.addDate(DateUtil.getSysdate(), -1 * condition.getRecencyThresholdA())));
        break;
      case RB:
        builder.append("     ? > MAX(ORDER_DATE)  AND MAX(ORDER_DATE) >= ?");
        paramsList.add(DateUtil.truncateDate(DateUtil.addDate(DateUtil.getSysdate(), -1 * condition.getRecencyThresholdA())));
        paramsList.add(DateUtil.truncateDate(DateUtil.addDate(DateUtil.getSysdate(), -1 * condition.getRecencyThresholdB())));
        break;
      default:
        builder.append("     ? > MAX(ORDER_DATE)");
        paramsList.add(DateUtil.truncateDate(DateUtil.addDate(DateUtil.getSysdate(), -1 * condition.getRecencyThresholdB())));
        break;
    }
    if (DIContainer.getWebshopConfig().isPostgreSQL()) {
      builder.append("   ) AS RFM1");
    } else {
      builder.append("   ) ");
    }
    // Rの条件ここまで

    builder.append("   JOIN ");
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

    if (DIContainer.getWebshopConfig().isPostgreSQL()) {
      builder.append("   ) AS RFM2");
    } else {
      builder.append("   ) ");
    }
    // Fの条件ここまで

    builder.append("   USING (CUSTOMER_CODE)");
    builder.append(" )");

    builder.append(" ORDER BY CUSTOMER_STATUS, CUSTOMER_CODE ");

    this.setParameters(paramsList.toArray());
    this.setSqlString(builder.toString());
  }

}
