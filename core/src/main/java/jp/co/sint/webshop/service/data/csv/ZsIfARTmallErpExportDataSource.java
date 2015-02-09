package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;

public class ZsIfARTmallErpExportDataSource extends
    SqlExportDataSource<ZsIfARTmallErpExportCsvSchema, ZsIfARTmallErpExportCondition> {

  WebshopConfig config = DIContainer.getWebshopConfig();

  private boolean headerOnly = false;

  // 导出过程中临时设定的dataTransportStatus值
  private Long dataTransportStatus = Long.parseLong("9");

  public Long getDataTransportStatus() {
    return dataTransportStatus;
  }

  private PreparedStatement updateStatement = null;

  private Logger logger = Logger.getLogger(this.getClass());

  String updateSql = "UPDATE TMALL_ORDER_HEADER SET DATA_TRANSPORT_STATUS = ?, UPDATED_USER = ?, UPDATED_DATETIME = ? "
      + " WHERE PAYMENT_DATE IS NOT NULL AND (PAYMENT_METHOD_TYPE = '11' OR PAYMENT_METHOD_TYPE = '12') "
      + " AND TMALL_STATUS = 'TRADE_FINISHED' AND TMALL_END_TIME IS NOT NULL  AND DATA_TRANSPORT_STATUS = ? ";

  /*
   * 导出前把数据库中所有的DATA_TRANSPORT_STATUS值为0的数据的DATA_TRANSPORT_STATUS值更新成9
   */
  public void beforeExport() {

    logger.debug("UPDATE TMALL_ORDER_HEADER statement: " + updateSql);
    try {
      updateStatement = createPreparedStatement(updateSql);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
    List<Object> updateShippingParams = new ArrayList<Object>();
    updateShippingParams.add(getDataTransportStatus());
    updateShippingParams.add("BATCH:0:0:0");
    updateShippingParams.add(DateUtil.getSysdate());
    updateShippingParams.add(0L);
    logger.debug("Table:TMALL_ORDER_HEADER UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingParams, Object.class)));
    try {
      DatabaseUtil.bindParameters(updateStatement, ArrayUtil.toArray(updateShippingParams, Object.class));
      int rows = updateStatement.executeUpdate();
      // 判断是不是无数据，只有标题行
      if (rows <= 0) {
        headerOnly = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // 判断是不是无数据，只有标题行实现方法
  public boolean headerOnly() {
    return headerOnly;
  }

  /*
   * 导出成功后把数据库中所有的DATA_TRANSPORT_STATUS值为9的数据的DATA_TRANSPORT_STATUS值更新成1
   */
  public void afterExport() {

    logger.debug("UPDATE TMALL_ORDER_HEADER statement: " + updateSql);
    try {
      updateStatement = createPreparedStatement(updateSql);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
    List<Object> updateShippingParams = new ArrayList<Object>();
    updateShippingParams.add(1L);
    updateShippingParams.add("BATCH:0:0:0");
    updateShippingParams.add(DateUtil.getSysdate());
    updateShippingParams.add(getDataTransportStatus());
    logger.debug("Table:TMALL_ORDER_HEADER UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingParams, Object.class)));
    try {
      DatabaseUtil.bindParameters(updateStatement, ArrayUtil.toArray(updateShippingParams, Object.class));
      updateStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Query getExportQuery() {

    String sql = "" + "(SELECT ORDER_NO" + " ,'' AS CHECK"
        + " ,(CASE WHEN GUEST_FLG = '0' THEN substr(CUSTOMER_CODE,LENGTH(CUSTOMER_CODE)-7,8) ELSE 'GUESTTM' END) AS CUSTOMERID"
        + " ,'RMB' AS CURRENCY" + " ,'' AS CHECK_CONTROL"
        + " ,TO_CHAR(TMALL_END_TIME,'yy/MM/dd') AS RECEIVED_DATE,TO_CHAR(TMALL_END_TIME,'yy/MM/dd') AS EFFECTIVE_DATE"
        + " ,(CASE WHEN PAYMENT_METHOD_TYPE = '11' THEN '14' WHEN PAYMENT_METHOD_TYPE = '12' THEN '11' END) AS BANK "
        + " ,'' AS REMARK" + " ,'' AS ACCOUNT " + " ,'' AS SUB_ACCOUNT" + " ,'' AS COST_CENTER" + " ,'' AS DISCOUNT_ACCOUNT"
        + " ,'' AS DISCOUNT_SUB_ACCOUNT" + " ,'' AS DISCOUNT_COST_CENTER" + " ,'1000' AS ENTITY" + " ,'' AS EXCHANGE_RATE"
        + " ,'' AS EXCHANGE_RATE2" + " ,'N' AS AUTO_APPLY" + " ,ORDER_NO AS AR_Reference"
        + " , ((CASE WHEN PAID_PRICE IS NULL THEN '0' ELSE PAID_PRICE END) "
        + "  -(CASE WHEN point_convert_price IS NULL THEN '0' ELSE point_convert_price END) "
        + "  -(CASE WHEN tmall_discount_price IS NULL THEN '0' ELSE tmall_discount_price END)) AS PAID_PRICE  "
        + " , ((CASE WHEN PAID_PRICE IS NULL THEN '0' ELSE PAID_PRICE END) "
        + "  -(CASE WHEN point_convert_price IS NULL THEN '0' ELSE point_convert_price END) "
        + "  -(CASE WHEN tmall_discount_price IS NULL THEN '0' ELSE tmall_discount_price END)) AS PAID_PRICE  "
        + " ,'' AS DISCOUNT"
        + " FROM TMALL_ORDER_HEADER" + " WHERE PAYMENT_DATE IS NOT NULL AND "
        + "(PAYMENT_METHOD_TYPE = '11' OR PAYMENT_METHOD_TYPE = '12') AND TMALL_END_TIME IS NOT NULL  "
        + " AND TMALL_STATUS = 'TRADE_FINISHED' AND " + "DATA_TRANSPORT_STATUS = " + getDataTransportStatus() 
        + ") UNION ALL  "
        + "(SELECT REPLACE(ORDER_NO,'T','S') " + " ,'' AS CHECK"
        + " ,'TMALL' AS CUSTOMERID"
        + " ,'RMB' AS CURRENCY" + " ,'' AS CHECK_CONTROL"
        + " ,TO_CHAR(TMALL_END_TIME,'yy/MM/dd') AS RECEIVED_DATE,TO_CHAR(TMALL_END_TIME,'yy/MM/dd') AS EFFECTIVE_DATE"
        + " ,(CASE WHEN PAYMENT_METHOD_TYPE = '11' THEN '14' WHEN PAYMENT_METHOD_TYPE = '12' THEN '11' END) AS BANK "
        + " ,'' AS REMARK" + " ,'' AS ACCOUNT " + " ,'' AS SUB_ACCOUNT" + " ,'' AS COST_CENTER" + " ,'' AS DISCOUNT_ACCOUNT"
        + " ,'' AS DISCOUNT_SUB_ACCOUNT" + " ,'' AS DISCOUNT_COST_CENTER" + " ,'1000' AS ENTITY" + " ,'' AS EXCHANGE_RATE"
        + " ,'' AS EXCHANGE_RATE2" + " ,'N' AS AUTO_APPLY" + " ,REPLACE(ORDER_NO,'T','S') AS AR_Reference"
        + " ,POINT_CONVERT_PRICE AS PAID_PRICE  "
        + " ,POINT_CONVERT_PRICE AS PAID_PRICE  "
        + " ,'' AS DISCOUNT"
        + " FROM TMALL_ORDER_HEADER" + " WHERE PAYMENT_DATE IS NOT NULL AND "
        + "(PAYMENT_METHOD_TYPE = '11' OR PAYMENT_METHOD_TYPE = '12') AND TMALL_END_TIME IS NOT NULL "
        + " AND POINT_CONVERT_PRICE IS NOT NULL AND POINT_CONVERT_PRICE > 0 "
        + " AND TMALL_STATUS = 'TRADE_FINISHED' AND " + "DATA_TRANSPORT_STATUS = " + getDataTransportStatus()+")" ;
    
    Query q = null;
    sql += " ORDER BY ORDER_NO";
    q = new SimpleQuery(sql);

    return q;
  }
}
