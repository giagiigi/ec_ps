package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.domain.MobileComputerType;
import jp.co.sint.webshop.utility.StringUtil;

public class NewPublicCouponQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** default constructor */
  public NewPublicCouponQuery() {
  }


  public NewPublicCouponQuery(NewPublicCouponSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();
    
    String startYear = condition.getSearchStartYear();
    String endYear = condition.getSearchEndYear();
    String startMonth = condition.getSearchStartMonth();
    String endMonth = condition.getSearchEndMonth();
    String startDate = condition.getSearchStartDate();
    String endDate = condition.getSearchEndDate();
    String DATE_PATTERN = "";
    String DATE_PATTERN_HEADER = "";
    String START_DATE = "";
    String END_DATE = "";
    
    switch (condition.getType()) {
      case MONTHLY:
        DATE_PATTERN_HEADER = "YYYY-MM";
        DATE_PATTERN = "YYYY/MM";
        START_DATE = startYear + "/" + startMonth ;
        END_DATE = endYear + "/" + endMonth ;
        break;
      case DAILY:
        DATE_PATTERN_HEADER = "YYYY-MM-DD";
        DATE_PATTERN = "YYYY/MM/DD";
        START_DATE = startDate;
        END_DATE = endDate;
        break;
      default:
        break;
    }

    //Query calendarQuery = createDateTable(condition.getType(), startDate, endDate);

    builder.append(" SELECT TO_CHAR(OH.ORDER_DATETIME, '"+DATE_PATTERN_HEADER+"') AS ORDER_DATE,");
    builder.append(" OH.DISCOUNT_CODE,");
    builder.append(" COUNT(0) AS TOTAL_CALL,");
    builder.append(" SUM(COALESCE(OH_RETURN_COUNT_FUNC(OH.ORDER_NO), 0::NUMERIC)) AS RETURN_CALL,");
    builder.append(" SUM(COALESCE(OH_LANG_COUNT_FUNC(OH.ORDER_NO,'zh-cn'), 0::NUMERIC)) AS ZH_CALL,");
    builder.append(" SUM(COALESCE(OH_LANG_COUNT_FUNC(OH.ORDER_NO,'ja-jp'), 0::NUMERIC)) AS JP_CALL,");
    builder.append(" SUM(COALESCE(OH_LANG_COUNT_FUNC(OH.ORDER_NO,'en-us'), 0::NUMERIC)) AS EN_CALL,");
    builder.append(" SUM(OH.DISCOUNT_PRICE) AS DISCOUNT_PRICE,");
    builder.append(" (SUM(COALESCE(OH_RETAIL_PRICE_FUNC(OH.ORDER_NO), 0::NUMERIC)) - SUM(OH.DISCOUNT_PRICE)) AS PAID_PRICE,");
    builder.append(" SUM(SH.SHIPPING_CHARGE) AS SHIPPING_CHARGE");
    builder.append(" FROM ORDER_HEADER OH ");
    builder.append(" INNER JOIN SHIPPING_HEADER SH ");
    builder.append(" ON OH.ORDER_NO = SH.ORDER_NO ");
    builder.append(" INNER JOIN NEW_COUPON_RULE NCR ");
    builder.append(" ON OH.DISCOUNT_CODE = NCR.COUPON_CODE ");
    
    builder.append(" WHERE TO_CHAR(OH.ORDER_DATETIME, '"+DATE_PATTERN+"') >= '"+START_DATE+"' AND TO_CHAR(OH.ORDER_DATETIME, '"+DATE_PATTERN+"') <= '"+END_DATE+"'");
    builder.append(" AND SH.RETURN_ITEM_TYPE = 0 ");
    builder.append(" AND (OH.ORDER_STATUS = '1' OR SH.SHIPPING_DIRECT_DATE IS NOT NULL) ");
    builder.append(" AND DISCOUNT_CODE IS NOT NULL ");
    // 判断是PC来源还是手机来源
    if (!StringUtil.isNullOrEmpty(condition.getSearchMobileComputerType())) {
      if(condition.getSearchMobileComputerType().equals(MobileComputerType.Mobile.getValue())){
        builder.append(" AND OH.MOBILE_COMPUTER_TYPE = ? ");
        params.add(condition.getSearchMobileComputerType());
      }
      if(condition.getSearchMobileComputerType().equals(MobileComputerType.PC.getValue())){
        builder.append(" AND (OH.MOBILE_COMPUTER_TYPE <> ? OR OH.MOBILE_COMPUTER_TYPE IS NULL) ");
        params.add(MobileComputerType.Mobile.getValue());
      }
    }
    if (StringUtil.hasValue(condition.getOrderClientType()) ) {
      builder.append(" AND OH.ORDER_CLIENT_TYPE = ? ");
      params.add(condition.getOrderClientType());
    }
    
    builder.append(" GROUP BY ORDER_DATE,DISCOUNT_CODE");
    builder.append(" ORDER BY ORDER_DATE ASC");

    setSqlString(builder.toString());
    setParameters(params.toArray());

  }
}
