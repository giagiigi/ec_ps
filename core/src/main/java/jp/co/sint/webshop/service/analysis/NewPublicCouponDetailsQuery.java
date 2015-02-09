package jp.co.sint.webshop.service.analysis;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.domain.MobileComputerType;
import jp.co.sint.webshop.utility.StringUtil;

public class NewPublicCouponDetailsQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** default constructor */
  public NewPublicCouponDetailsQuery() {
  }

  public NewPublicCouponDetailsQuery(NewPublicCouponDetailsCondition condition) {
    StringBuilder builder = new StringBuilder();

    String orderDate = condition.getSearchOrderDate();
    String discountCode = condition.getSearchDiscountCode();

    String DATE_PATTERN = "";

    switch (condition.getType()) {
      case MONTHLY:
        DATE_PATTERN = "YYYY-MM";
        break;
      case DAILY:
        DATE_PATTERN = "YYYY-MM-DD";
        break;
      default:
        break;
    }

    builder.append(" SELECT ");
    builder.append(" OH.CUSTOMER_CODE,");
    builder.append(" OH.DISCOUNT_CODE, ");
    builder.append(" OH.ORDER_NO, ");
    builder.append(" CASE WHEN OH.MOBILE_COMPUTER_TYPE = 2 THEN 'EC[Mobile]' WHEN OH.MOBILE_COMPUTER_TYPE = 1 THEN 'EC[PC]' " +
    		           " WHEN OH.MOBILE_COMPUTER_TYPE IS NULL THEN 'EC[PC]' ELSE 'EC[PC]' END AS MOBILE_COMPUTER_TYPE, ");
    builder.append(" COALESCE(OH_RETAIL_PRICE_FUNC(OH.ORDER_NO), 0::NUMERIC) AS PAID_PRICE, ");
    builder.append(" OH.DISCOUNT_PRICE,");
    builder.append(" SH.SHIPPING_CHARGE, ");
    builder
        .append(" COALESCE(OH_RETAIL_PRICE_FUNC(OH.ORDER_NO), 0::NUMERIC) + SH.SHIPPING_CHARGE -OH.DISCOUNT_PRICE AS PAYMENT_PRICE,");
    builder.append(" OH.LANGUAGE_CODE,");
    builder.append(" SH.ADDRESS1");
    builder.append(" ,OH.USE_AGENT,OH.ORDER_CLIENT_TYPE");
    builder.append(" FROM ORDER_HEADER OH");
    builder.append(" INNER JOIN SHIPPING_HEADER SH ON OH.ORDER_NO = SH.ORDER_NO");
    builder.append(" WHERE TO_CHAR(OH.ORDER_DATETIME, '" + DATE_PATTERN + "') = '" + orderDate + "'");
    builder.append(" AND SH.RETURN_ITEM_TYPE = 0 ");
    builder.append(" AND (OH.ORDER_STATUS = '1' OR SH.SHIPPING_DIRECT_DATE IS NOT NULL) ");
    builder.append(" AND OH.DISCOUNT_CODE = '" + discountCode + "'");
    // 判断是PC来源还是手机来源
    if (!StringUtil.isNullOrEmpty(condition.getSearchMobileComputerType())) {
      if(condition.getSearchMobileComputerType().equals(MobileComputerType.Mobile.getValue())){
        builder.append(" AND OH.MOBILE_COMPUTER_TYPE =  "+condition.getSearchMobileComputerType()+"  ");
      }
      if(condition.getSearchMobileComputerType().equals(MobileComputerType.PC.getValue())){
        builder.append(" AND (OH.MOBILE_COMPUTER_TYPE <> "+MobileComputerType.Mobile.getValue()+" OR OH.MOBILE_COMPUTER_TYPE IS NULL) ");
      }
    }
    
    if (StringUtil.hasValue(condition.getOrderClientType()) ) {
      builder.append(" AND OH.ORDER_CLIENT_TYPE =  " +condition.getOrderClientType()+"  ");
    }

    setSqlString(builder.toString());
  }
}
