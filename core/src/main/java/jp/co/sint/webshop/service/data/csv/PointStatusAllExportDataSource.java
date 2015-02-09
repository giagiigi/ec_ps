package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.data.domain.PointAmplificationRate;
import jp.co.sint.webshop.service.customer.PointStatusAllQuery;
import jp.co.sint.webshop.utility.PointUtil;

public class PointStatusAllExportDataSource extends SqlExportDataSource<PointStatusAllCsvSchema, PointStatusAllExportCondition> {

  private static final String BASE_QUERY = ""
      + " SELECT "
      + "   PH.SHOP_CODE, "
      + "   S.SHOP_NAME, "
      + "   PH.CUSTOMER_CODE, "
      + "   C.LAST_NAME || C.FIRST_NAME AS CUSTOMER_NAME, "
      + "   PH.POINT_ISSUE_TYPE, "
      // modify by V10-CH start
      // + " PH.ISSUED_POINT/" +
      // DIContainer.getWebshopConfig().getPointMultiple()
      // + ", "
      + "   trim(to_char(trunc(PH.ISSUED_POINT ,"
      + PointUtil.getAcquiredPointScale()
      + " ),'"
      + PointAmplificationRate.fromValue(Integer.toString(PointUtil.getAcquiredPointScale())).getName()
      + "')) AS ISSUED_POINT,"
      // modify by V10-CH end
      + "   PH.POINT_ISSUE_STATUS, " + "   PH.ORDER_NO, " + "   O.ORDER_DATETIME, " + "   O.PAYMENT_DATE, " + "   PH.REVIEW_ID, "
      + "   PH.ENQUETE_CODE, " + "   PH.DESCRIPTION, " + "   PH.POINT_ISSUE_DATETIME " + " FROM " + "   POINT_HISTORY PH "
      + " INNER JOIN " + "   CUSTOMER C " + " ON " + "   PH.CUSTOMER_CODE = C.CUSTOMER_CODE " + " LEFT OUTER JOIN " + "   SHOP S "
      + " ON " + "   PH.SHOP_CODE = S.SHOP_CODE " + " LEFT OUTER JOIN " + "   ORDER_HEADER O " + " ON "
      + "   PH.ORDER_NO = O.ORDER_NO " + " WHERE " + "   1 = 1 ";

  @Override
  public Query getExportQuery() {
    return new PointStatusAllQuery(getCondition().getSearchCondition(), BASE_QUERY);
  }

}
