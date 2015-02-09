package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;

import org.apache.log4j.Logger;

public class ZsIfARJdErpExportDataSource extends
    SqlExportDataSource<ZsIfARJdErpExportCsvSchema, ZsIfARJdErpExportCondition> {

  WebshopConfig config = DIContainer.getWebshopConfig();

  private boolean headerOnly = false;
  
  private static String  CURRET_TIME = DateUtil.getDateTimeString();

  // 导出过程中临时设定的dataTransportStatus值
  private Long dataTransportStatus = Long.parseLong("9");

  public Long getDataTransportStatus() {
    return dataTransportStatus;
  }

  private PreparedStatement updateStatement = null;

  private Logger logger = Logger.getLogger(this.getClass());

  String updateSql = "UPDATE JD_ORDER_HEADER SET DATA_TRANSPORT_STATUS = ?, UPDATED_USER = ?, UPDATED_DATETIME = ? "
      + " WHERE PAYMENT_DATE IS NOT NULL AND (PAYMENT_METHOD_TYPE = '17') "
      + " AND JD_ORDER_STATUS = 'FINISHED_L' AND JD_END_TIME IS NOT NULL  AND DATA_TRANSPORT_STATUS = ? "
      + " AND JD_END_TIME < (TIMESTAMP '" + CURRET_TIME + "') + '-1 d' ";

  /*
   * 导出前把数据库中所有的DATA_TRANSPORT_STATUS值为0的数据的DATA_TRANSPORT_STATUS值更新成9
   */
  public void beforeExport() {

    logger.debug("UPDATE JD_ORDER_HEADER statement: " + updateSql);
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
    logger.debug("Table:JD_ORDER_HEADER UPDATE Parameters:"
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

    logger.debug("UPDATE JD_ORDER_HEADER statement: " + updateSql);
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
    logger.debug("Table:JD_ORDER_HEADER UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingParams, Object.class)));
    try {
      DatabaseUtil.bindParameters(updateStatement, ArrayUtil.toArray(updateShippingParams, Object.class));
      updateStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Query getExportQuery() {

    String sql =  "(SELECT JOH.ORDER_NO" + " ,'' AS CHECK"
        + " ,(CASE WHEN GUEST_FLG = '0' THEN substr(JOH.CUSTOMER_CODE,LENGTH(JOH.CUSTOMER_CODE)-7,8) ELSE 'GUESTJD' END) AS CUSTOMERID"
        + " ,'RMB' AS CURRENCY" + " ,'' AS CHECK_CONTROL"
        + " ,TO_CHAR(JD_END_TIME,'yy/MM/dd') AS RECEIVED_DATE,TO_CHAR(JD_END_TIME,'yy/MM/dd') AS EFFECTIVE_DATE"
        + " ,'20' AS BANK "
        + " ,'' AS REMARK" + " ,'' AS ACCOUNT " + " ,'' AS SUB_ACCOUNT" + " ,'' AS COST_CENTER" + " ,'' AS DISCOUNT_ACCOUNT"
        + " ,'' AS DISCOUNT_SUB_ACCOUNT" + " ,'' AS DISCOUNT_COST_CENTER" + " ,'1000' AS ENTITY" + " ,'' AS EXCHANGE_RATE"
        + " ,'' AS EXCHANGE_RATE2" + " ,'N' AS AUTO_APPLY" + " ,JOH.ORDER_NO AS AR_Reference"
        + " , ((CASE WHEN PAID_PRICE IS NULL THEN '0' ELSE PAID_PRICE END) "
        + "  ) AS PAID_PRICE  "
        + " , ((CASE WHEN PAID_PRICE IS NULL THEN '0' ELSE PAID_PRICE END) "
        + "  ) AS PAID_PRICE  "
        + " ,'' AS DISCOUNT"
        + " FROM JD_ORDER_HEADER JOH" 
        + " INNER JOIN JD_SHIPPING_HEADER JSH ON JSH.ORDER_NO = JOH.ORDER_NO "
        + " WHERE PAYMENT_DATE IS NOT NULL "
        + " AND (PAYMENT_METHOD_TYPE = '17') AND JD_END_TIME IS NOT NULL  "
        + " AND JD_ORDER_STATUS = 'FINISHED_L' AND " + "DATA_TRANSPORT_STATUS = " + getDataTransportStatus() 
        + " AND JD_END_TIME < (TIMESTAMP '" + CURRET_TIME + "') + '-1 d' "
        + " AND RETURN_ITEM_TYPE = 0 AND JSH.CHILD_ORDER_NO IS NULL) "
        
        + " UNION "
        + "(SELECT  JSH.CHILD_ORDER_NO,'' AS CHECK"
        + " ,(CASE WHEN GUEST_FLG = '0' THEN substr(JOH.CUSTOMER_CODE,LENGTH(JOH.CUSTOMER_CODE)-7,8) ELSE 'GUESTJD' END) AS CUSTOMERID"
        + " ,'RMB' AS CURRENCY" + " ,'' AS CHECK_CONTROL"
        + " ,TO_CHAR(JD_END_TIME,'yy/MM/dd') AS RECEIVED_DATE,TO_CHAR(JD_END_TIME,'yy/MM/dd') AS EFFECTIVE_DATE"
        + " ,'20' AS BANK "
        + " ,'' AS REMARK" + " ,'' AS ACCOUNT " + " ,'' AS SUB_ACCOUNT" + " ,'' AS COST_CENTER" + " ,'' AS DISCOUNT_ACCOUNT"
        + " ,'' AS DISCOUNT_SUB_ACCOUNT" + " ,'' AS DISCOUNT_COST_CENTER" + " ,'1000' AS ENTITY" + " ,'' AS EXCHANGE_RATE"
        + " ,'' AS EXCHANGE_RATE2" + " ,'N' AS AUTO_APPLY" + " ,JSH.CHILD_ORDER_NO AS AR_Reference"
        + " , ((CASE WHEN JSH.CHILD_PAID_PRICE IS NULL THEN '0' ELSE JSH.CHILD_PAID_PRICE END) "
        + "  ) AS PAID_PRICE  "
        + " , ((CASE WHEN JSH.CHILD_PAID_PRICE IS NULL THEN '0' ELSE JSH.CHILD_PAID_PRICE END) "
        + "  ) AS PAID_PRICE  "
        + " ,'' AS DISCOUNT"
        + " FROM JD_ORDER_HEADER JOH " 
        + " INNER JOIN JD_SHIPPING_HEADER JSH ON JSH.ORDER_NO = JOH.ORDER_NO "
        + " WHERE PAYMENT_DATE IS NOT NULL "
        + " AND (PAYMENT_METHOD_TYPE = '17') AND JD_END_TIME IS NOT NULL  "
        + " AND JD_ORDER_STATUS = 'FINISHED_L' AND " + "DATA_TRANSPORT_STATUS = " + getDataTransportStatus() 
        + " AND JD_END_TIME < (TIMESTAMP '" + CURRET_TIME + "') + '-1 d' "
        + " AND RETURN_ITEM_TYPE = 0 AND JSH.CHILD_ORDER_NO IS NOT NULL )";
      
    
    Query q = null;
    sql += " ORDER BY ORDER_NO";
    q = new SimpleQuery(sql);

    return q;
  }
  
}
