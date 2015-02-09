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

public class ZsIfCustErpExportDataSource extends SqlExportDataSource<ZsIfCustErpExportCsvSchema, ZsIfCustErpExportCondition> {

  WebshopConfig config = DIContainer.getWebshopConfig();

  private boolean headerOnly = false;

  // 导出过程中临时设定的exportKbn值
  private Long exportKbn = Long.parseLong("9");

  public Long getExportKbn() {
    return exportKbn;
  }

  private PreparedStatement updateStatement = null;

  private Logger logger = Logger.getLogger(this.getClass());

  String updateSql = "UPDATE CUSTOMER SET EXPORT_KBN = ?, UPDATED_USER = ?, UPDATED_DATETIME = ?  WHERE EXPORT_KBN = ? AND CUSTOMER_STATUS = 0 ";

  /*
   * 导出前把数据库中所有的EXPORT_KBN值为0的数据的EXPORT_KBN值更新成9
   */
  public void beforeExport() {

    logger.debug("UPDATE CUSTOMER statement: " + updateSql);
    try {
      updateStatement = createPreparedStatement(updateSql);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
    List<Object> updateShippingParams = new ArrayList<Object>();
    updateShippingParams.add(getExportKbn());
    updateShippingParams.add("BATCH:0:0:0");
    updateShippingParams.add(DateUtil.getSysdate());
    updateShippingParams.add(0L);
    logger.debug("Table:CUSTOMER UPDATE Parameters:" + Arrays.toString(ArrayUtil.toArray(updateShippingParams, Object.class)));
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
   * 导出成功后把数据库中所有的EXPORT_KBN值为9的数据的EXPORT_KBN值更新成1
   */
  public void afterExport() {

    logger.debug("UPDATE CUSTOMER statement: " + updateSql);
    try {
      updateStatement = createPreparedStatement(updateSql);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
    List<Object> updateShippingParams = new ArrayList<Object>();
    updateShippingParams.add(1L);
    updateShippingParams.add("BATCH:0:0:0");
    updateShippingParams.add(DateUtil.getSysdate());
    updateShippingParams.add(getExportKbn());
    logger.debug("Table:CUSTOMER UPDATE Parameters:" + Arrays.toString(ArrayUtil.toArray(updateShippingParams, Object.class)));
    try {
      DatabaseUtil.bindParameters(updateStatement, ArrayUtil.toArray(updateShippingParams, Object.class));
      updateStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Query getExportQuery() {

    String sql = "" + "SELECT substr(CUSTOMER_CODE,LENGTH(CUSTOMER_CODE)-7,8),substr(CUSTOMER_CODE,LENGTH(CUSTOMER_CODE)-7,8)" + " ,'' AS XXCM_ADDR1"
        + " ,'' AS XXCM_ADDR2" + " ,'' AS XXCM_ADDR3" + " ,'' AS XXCM_CITY" + " ,'' AS XXCM_STATE" + " ,'000000' AS XXCM_ZIP"
        + " ,'CHN' AS XXCM_CTRY" + " ,'' AS XXCM_COUNTY" + " ,'' AS XXCM_ATTN" + " ,'' AS XXCM_ATTN2" + " ,'' AS XXCM_PHONE"
        + " ,'' AS XXCM_EXT" + " ,'' AS XXCM_PHONE2" + " ,'' AS XXCM_EXT2" + " ,'' AS XXCM_FAX" + " ,'' AS XXCM_FAX2"
        + " ,'' AS XXCM_RESALE" + " ,SUBSTR(EMAIL,0,41) AS EMAIL" + " ,'' AS XXCM_CUST_STAT" + " ,'' AS XXCM_PLAN_DATE"
        + " ,'' AS XXCM_SALES_CH" + " ,'' AS XXCM_ORD_LIMIT" + " ,'' AS XXCM_DEL_AREA" + " ,'' AS XXCM_SHIPTO1"
        + " ,'' AS XXCM_SHIPTO2" + " ,'' AS XXCM_SHIPTO3" + " ,'' AS XXCM_INVOICE" + " ,'' AS XXCM_PAY_MTHD"
        + " ,'' AS XXCM_CR_TERMS" + " ,'' AS XXCM_BILL" + " ,'' AS XXCM_PST_ID" + " ,'' AS XXCM_BANK" + " ,'' AS XXCM_ACCT_TYPE"
        + " ,'' AS XXCM_EDI" + " ,'' AS XXCM_BRANCH" + " ,'' AS XXCM_BK_ACCT" + " ,'' AS XXCM_BEG_DATE" + " ,'' AS XXCM_END_DATE"
        + " ,'' AS XXCM_CAT_FLAG" + " ,'' AS XXCM_EMAIL_FLAG" + " ,'' AS XXCM_FAX_FLAG" + " ,'' AS XXCM_TEL_FLAG"
        + " ,'' AS XXCM_VISIT_FLAG" + " ,'' AS XXCM_DM_FLAG" + " ,'' AS XXCM_TYPE_IND" + " ,'' AS XXCM_COMP_SIZE"
        + " ,'' AS XXCM_OFF_SIZE" + " ,'' AS XXCM_CUST_CLASS" + " ,'EC' AS XXCM_TYPE" + " ,'' AS XXCM_EST_YR"
        + " ,'' AS XXCM_RMKS1" + " ,'' AS XXCM_RMKS2" + " ,'' AS XXCM_RMKS3" + " ,'Y' AS XXCM_TAXABLE" + " ,'CH' AS XXCM_ZONE"
        + " ,'VAT' AS XXCM_CLASS" + " ,'TAX-CH' AS XXCM_USAGE" + " ,'Y' AS XXCM_TAX_IN" + " ,'' AS XXCM_GST_ID"
        + " ,'' AS XXCM_MISC1_ID" + " ,'' AS XXCM_MISC2_ID" + " ,'' AS XXCM_MISC3_ID" + " ,'' AS XXCM_TX_IN_CITY"
        + " ,'' AS XXCM_SORT" + " ,'' AS XXCM_SLSPSN1" + " ,'' AS XXCM_MULTI" + " ,'' AS XXCM_SLSPSN2" + " ,'' AS XXCM_SLSPSN3"
        + " ,'' AS XXCM_SLSPSN4" + " ,'' AS XXCM_SHIPVIA" + " ,'' AS XXCM_PARTIAL" + " ,'' AS XXCM_FR_LIST"
        + " ,'' AS XXCM_FR_TERMS" + " ,'' AS XXCM_AR_ACCT" + " ,'' AS XXCM_AR_SUB" + " ,'' AS XXCM_AR_CC," + "'"
        + config.getIfSite()
        + "'"
        + "AS XXCM_SITE"
        + " ,'RMB' AS XXCM_CURR"
        + " ,'' AS XXCM_PR_LIST2"
        + " ,'' AS XXCM_FIX_PR"
        + " ,'' AS XXCM_PR_LIST"
        + " ,'' AS XXCM_DISC_PCT"
        + " ,'' AS XXCM_CR_LIMIT"
        + " ,'' AS XXCM_FIN"
        + " ,'' AS XXCM_DUN"
        + " ,'' AS XXCM_STMT"
        + " ,'' AS XXCM_STMT_CYC"
        + " ,'' AS XXCM_CR_REVIEW"
        + " ,'' AS XXCM_CR_UPDATE"
        + " ,'' AS XXCM_REGION"
        + " ,'' AS XXCM_HIGH_CR"
        + " ,'' AS XXCM_PAY_DATE"
        + " ,'' AS XXCM_BTB_TYPE"
        + " ,'' AS XXCM_SHIP_LT"
        + " ,'' AS XXCM_BTB_MTHD"
        + " ,'' AS XXCM_BTB_CR"
        + " FROM CUSTOMER WHERE  CUSTOMER_STATUS = 0 AND EXPORT_KBN = "
        + getExportKbn();
    Query q = null;
    sql += " ORDER BY CUSTOMER_CODE";
    q = new SimpleQuery(sql);

    return q;

  }
}
