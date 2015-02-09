package jp.co.sint.webshop.service.analysis;

import java.util.Date;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.DateUtil;


public class RfAnalysisQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;
  
  private static final String RF_ANALYSIS_QUERY = "SELECT"
    + " COUNT(R_TABLE.CUSTOMER_CODE) AS CUSTOMER_COUNT,"
    + " ROUND(COUNT(R_TABLE.CUSTOMER_CODE) / TOTAL_TABLE.CUSTOMER_COUNT * 100 , 1) AS CUSTOMER_COUNT_RATIO,"
    + " ROUND(SUM(F_TABLE.ORDER_COUNT) / COUNT(R_TABLE.CUSTOMER_CODE) , 1) AS ORDER_COUNT_AVARAGE,"
    + " ROUND(SUM(F_TABLE.ORDER_COUNT) / TOTAL_TABLE.ORDER_COUNT * 100 , 1) AS ORDER_COUNT_RATIO,"
    + " ROUND(SUM(F_TABLE.PURCHASING_AMOUNT) / COUNT(R_TABLE.CUSTOMER_CODE) , 1) AS PURCHASING_AMOUNT_AVARAGE,"
    + " ROUND(SUM(F_TABLE.PURCHASING_AMOUNT) / TOTAL_TABLE.PURCHASING_AMOUNT * 100 , 1) AS PURCHASING_AMOUNT_RATIO,"
    + " R_TABLE.RANK AS RECENCY_RANK,"
    + " F_TABLE.RANK AS FREQUENCY_RANK"
    + " FROM"
    //Rの計算部分ここから
    + "("
    + "SELECT"
    + " CUSTOMER_CODE,"
    + " MAX(ORDER_DATE) AS LATEST_ORDER_DATE,"
    + " CASE"
    + "   WHEN MAX(ORDER_DATE) >= ? THEN 'A'"
    + "   WHEN ? > MAX(ORDER_DATE) AND MAX(ORDER_DATE) >= ? THEN 'B'"
    + "   ELSE 'C'"
    + " END AS RANK"
    + " FROM"
    + "   RFM"
    + " GROUP BY"
    + "   CUSTOMER_CODE"
    //Rの計算部分ここまで
    + ") R_TABLE,"
    + "("
    //Fの計算部分ここから
    + "SELECT"
    + " CUSTOMER_CODE,"
    + " COUNT(ORDER_NO) AS ORDER_COUNT,"
    + " SUM(PURCHASING_AMOUNT) AS PURCHASING_AMOUNT,"
    + " CASE"
    + "   WHEN COUNT(ORDER_NO) >= ? THEN 'A'"
    + "   WHEN ? > COUNT(ORDER_NO) AND COUNT(ORDER_NO) >= ? THEN 'B'"
    + "   ELSE 'C'"
    + " END AS RANK"
    + " FROM"
    + "   RFM"
    + " WHERE"
    + "   ORDER_DATE >= ?"
    + " GROUP BY"
    + "   CUSTOMER_CODE"
    //Fの計算部分ここまで
    + ") F_TABLE,"
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
    + " WHERE R_TABLE.CUSTOMER_CODE = F_TABLE.CUSTOMER_CODE"
    + " GROUP BY"
    + " R_TABLE.RANK,"
    + " F_TABLE.RANK,"
    + " TOTAL_TABLE.CUSTOMER_COUNT,"
    + " TOTAL_TABLE.ORDER_COUNT,"
    + " TOTAL_TABLE.PURCHASING_AMOUNT";
  
  /** default constructor */
  public RfAnalysisQuery() {
  }
  
  public RfAnalysisQuery(RfmAnalysisSearchCondition condition) {
    Date recencyThresholdDateA = 
      DateUtil.truncateDate(DateUtil.addDate(DateUtil.getSysdate(), -1 * condition.getRecencyThresholdA()));
    Date recencyThresholdDateB = 
      DateUtil.truncateDate(DateUtil.addDate(DateUtil.getSysdate(), -1 * condition.getRecencyThresholdB()));
    Date frequencyPeriodDate = DateUtil.truncateDate(DateUtil.addMonth(DateUtil.getSysdate(), -1 * condition.getFrequencyPeriod()));
  
    Object[] params = new Object[]{
        recencyThresholdDateA, recencyThresholdDateA, recencyThresholdDateB,
      condition.getFrequencyThresholdA(), condition.getFrequencyThresholdA(), condition.getFrequencyThresholdB(),
      frequencyPeriodDate
    };
    
    setSqlString(RF_ANALYSIS_QUERY);
    setParameters(params);
  }
}
