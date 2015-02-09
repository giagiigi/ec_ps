package jp.co.sint.webshop.service.analysis;

import java.util.Date;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.DateUtil;

public class FmAnalysisQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;
  
  private static final String FM_ANALYSIS_QUERY = "SELECT"
    + "   COUNT(F_TABLE.CUSTOMER_CODE) AS CUSTOMER_COUNT,"
    + "   ROUND(COUNT(F_TABLE.CUSTOMER_CODE) / TOTAL_TABLE.CUSTOMER_COUNT * 100 , 1) AS CUSTOMER_COUNT_RATIO,"
    // 10.1.4 10119 修正 ここから
    // + "   ROUND(COUNT(F_TABLE.ORDER_COUNT) / COUNT(F_TABLE.CUSTOMER_CODE) , 1) AS ORDER_COUNT_AVARAGE,"
    + "   ROUND(SUM(F_TABLE.ORDER_COUNT) / COUNT(F_TABLE.CUSTOMER_CODE) , 1) AS ORDER_COUNT_AVARAGE,"
    // 10.1.4 10119 修正 ここまで
    + "   ROUND(COUNT(F_TABLE.ORDER_COUNT) / TOTAL_TABLE.ORDER_COUNT * 100 , 1) AS ORDER_COUNT_RATIO,"
    + "   ROUND(SUM(F_TABLE.PURCHASING_AMOUNT) / COUNT(F_TABLE.CUSTOMER_CODE) , 1) AS PURCHASING_AMOUNT_AVARAGE,"
    + "   ROUND(SUM(F_TABLE.PURCHASING_AMOUNT) / TOTAL_TABLE.PURCHASING_AMOUNT * 100 , 1) AS PURCHASING_AMOUNT_RATIO,"
    + "   SUM(M_TABLE.TOTAL_PURCHASED_AMOUNT) AS TOTAL_PURCHASED_AMOUNT,"
    + "   ROUND(SUM(M_TABLE.TOTAL_PURCHASED_AMOUNT) / TOTAL_TABLE.SUM_OF_PRICE * 100 , 1) AS TOTAL_PURCHASED_AMOUNT_RATIO,"
    + "   F_TABLE.RANK AS FREQUENCY_RANK,"
    + "   M_TABLE.RANK AS MONETARY_RANK"
    + " FROM"
    + "   ("
    //Fの計算部分ここから
    + "   SELECT"
    + "     CUSTOMER_CODE,"
    + "     COUNT(ORDER_NO) AS ORDER_COUNT,"
    + "     SUM(PURCHASING_AMOUNT) AS PURCHASING_AMOUNT,"
    + "     CASE"
    + "       WHEN COUNT(ORDER_NO) >= ? THEN 'A'"
    + "       WHEN ? > COUNT(ORDER_NO) AND COUNT(ORDER_NO) >= ? THEN 'B'"
    + "       ELSE 'C'"
    + "     END AS RANK"
    + "   FROM"
    + "     RFM"
    + "   WHERE"
    + "     ORDER_DATE >= ?"
    + "   GROUP BY"
    + "     CUSTOMER_CODE"
    //Fの計算部分ここまで
    + "   ) F_TABLE,"
    + "   ("
    //Mの計算部分ここから
    + "   SELECT"
    + "     CUSTOMER_CODE,"
    + "     SUM(TOTAL_PURCHASED_AMOUNT) AS TOTAL_PURCHASED_AMOUNT,"
    + "     COUNT(ORDER_NO) AS ORDER_COUNT,"
    + "     CASE"
    + "       WHEN SUM(TOTAL_PURCHASED_AMOUNT) >= ? THEN 'A'"
    + "       WHEN ? > SUM(TOTAL_PURCHASED_AMOUNT) AND SUM(TOTAL_PURCHASED_AMOUNT) >= ? THEN 'B'"
    + "       ELSE 'C'"
    + "     END AS RANK"
    + "   FROM"
    + "     RFM"
    + "   WHERE"
    + "     ORDER_DATE >= ?"
    + "   GROUP BY"
    + "     CUSTOMER_CODE"
    //Mの計算部分ここまで
    + "   ) M_TABLE,"
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
    + " WHERE F_TABLE.CUSTOMER_CODE = M_TABLE.CUSTOMER_CODE"
    + " GROUP BY"
    + "   F_TABLE.RANK,"
    + "   M_TABLE.RANK,"
    + "   TOTAL_TABLE.CUSTOMER_COUNT,"
    + "   TOTAL_TABLE.ORDER_COUNT,"
    + "   TOTAL_TABLE.PURCHASING_AMOUNT,"
    + "   TOTAL_TABLE.SUM_OF_PRICE";

  /** default constructor */
  public FmAnalysisQuery() {
  }
  
  public FmAnalysisQuery(RfmAnalysisSearchCondition condition) {
    Date frequencyPeriodDate = DateUtil.truncateDate(DateUtil.addMonth(DateUtil.getSysdate(), -1 * condition.getFrequencyPeriod()));
    Date monetaryPeriodDate = DateUtil.truncateDate(DateUtil.addMonth(DateUtil.getSysdate(), -1 * condition.getMonetaryPeriod()));
    
    Object[] params = new Object[] {
        condition.getFrequencyThresholdA(), condition.getFrequencyThresholdA(), condition.getFrequencyThresholdB(),
        frequencyPeriodDate,
        condition.getMonetaryThresholdA(), condition.getMonetaryThresholdA(), condition.getMonetaryThresholdB(),
        monetaryPeriodDate
    };
    
    setSqlString(FM_ANALYSIS_QUERY);
    setParameters(params);
  }

}
