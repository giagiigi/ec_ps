package jp.co.sint.webshop.service.analysis;

import java.util.Date;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.DateUtil;


public class RmAnalysisQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;
  
  private static final String RM_ANALYSIS_QUERY = "SELECT"
    + "  COUNT(R_TABLE.CUSTOMER_CODE) AS CUSTOMER_COUNT,"
    + "  ROUND(COUNT(R_TABLE.CUSTOMER_CODE) / TOTAL_TABLE.CUSTOMER_COUNT * 100 , 1) AS CUSTOMER_COUNT_RATIO,"
    + "  SUM(M_TABLE.TOTAL_PURCHASED_AMOUNT) AS TOTAL_PURCHASED_AMOUNT,"
    + "  ROUND(SUM(M_TABLE.TOTAL_PURCHASED_AMOUNT) / TOTAL_TABLE.SUM_OF_PRICE * 100 , 1) AS TOTAL_PURCHASED_AMOUNT_RATIO,"
    + "  R_TABLE.RANK AS RECENCY_RANK,"
    + "  M_TABLE.RANK AS MONETARY_RANK"
    + " FROM"
    + " ("
    //R計算部分ここから
    + "  SELECT "
    + "    CUSTOMER_CODE,"
    + "    MAX(ORDER_DATE) AS LATEST_ORDER_DATE,"
    + "    CASE"
    + "      WHEN MAX(ORDER_DATE) >= ? THEN 'A'"
    + "      WHEN ? > MAX(ORDER_DATE) AND MAX(ORDER_DATE) >= ? THEN 'B'"
    + "      ELSE 'C'"
    + "    END AS RANK"
    + "  FROM"
    + "    RFM"
    + "  GROUP BY"
    + "    CUSTOMER_CODE"
    //Rの計算部分ここまで
    + "  ) R_TABLE,"
    + "("
    //Mの計算部分ここから
    + " SELECT"
    + "  CUSTOMER_CODE,"
    + "   SUM(TOTAL_PURCHASED_AMOUNT) AS TOTAL_PURCHASED_AMOUNT,"
    + "   SUM(PURCHASING_AMOUNT) AS PURCHASING_AMOUNT,"
    + "   COUNT(ORDER_NO) AS ORDER_COUNT,"
    + "   CASE"
    + "     WHEN SUM(TOTAL_PURCHASED_AMOUNT) >= ? THEN 'A'"
    + "     WHEN ? > SUM(TOTAL_PURCHASED_AMOUNT) AND SUM(TOTAL_PURCHASED_AMOUNT) >= ? THEN 'B'"
    + "     ELSE 'C'"
    + "   END AS RANK"
    + " FROM"
    + "   RFM"
    + " WHERE"
    + "   ORDER_DATE >= ?"
    + " GROUP BY"
    + "   CUSTOMER_CODE" 
    //Mの計算部分ここまで
    + " ) M_TABLE,"
    + " ("
    //割合計算用ここから
    + " SELECT"
    + "   CASE"
    + "     WHEN COUNT(CUSTOMER_CODE) = 0 THEN 1"
    + "     ELSE COUNT(CUSTOMER_CODE)"
    + "   END AS CUSTOMER_COUNT,"
    + "   CASE"
    + "     WHEN COUNT(ORDER_NO) = 0 THEN 1"
    + "     ELSE COUNT(ORDER_NO) "
    + "   END AS ORDER_COUNT,"
    + "   CASE"
    + "     WHEN SUM(PURCHASING_AMOUNT) = 0 THEN 1"
    + "     ELSE SUM(PURCHASING_AMOUNT)"
    + "   END AS PURCHASING_AMOUNT,"
    + "   CASE "
    + "     WHEN SUM(TOTAL_PURCHASED_AMOUNT) = 0 THEN 1"
    + "     ELSE SUM(TOTAL_PURCHASED_AMOUNT)"
    + "   END AS SUM_OF_PRICE"
    + " FROM RFM"
    //割合計算用ここまで
    + " ) TOTAL_TABLE"
    + " WHERE "
    + "   R_TABLE.CUSTOMER_CODE = M_TABLE.CUSTOMER_CODE"
    + " GROUP BY"
    + "   R_TABLE.RANK,"
    + "   M_TABLE.RANK,"
    + "   TOTAL_TABLE.CUSTOMER_COUNT,"
    + "   TOTAL_TABLE.SUM_OF_PRICE ";
  
  /** default constructor */
  public RmAnalysisQuery() {
  }
  
  public RmAnalysisQuery(RfmAnalysisSearchCondition condition) {
    Date recencyThresholdDateA = 
      DateUtil.truncateDate(DateUtil.addDate(DateUtil.getSysdate(), -1 * condition.getRecencyThresholdA()));
    Date recencyThresholdDateB = 
      DateUtil.truncateDate(DateUtil.addDate(DateUtil.getSysdate(), -1 * condition.getRecencyThresholdB()));
    Date monetaryPeriodDate = DateUtil.truncateDate(DateUtil.addMonth(DateUtil.getSysdate(), -1 * condition.getMonetaryPeriod()));
    
    Object[] params = new Object[] {
        recencyThresholdDateA, recencyThresholdDateA, recencyThresholdDateB,
      condition.getMonetaryThresholdA(), condition.getMonetaryThresholdA(), condition.getMonetaryThresholdB(),
      monetaryPeriodDate
    };
    
    setSqlString(RM_ANALYSIS_QUERY);
    setParameters(params);
  }

}
