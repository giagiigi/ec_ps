package jp.co.sint.webshop.service.shop;

public final class CouponQuery {

  private static final long serialVersionUID = 1L;

  private CouponQuery() {
  }
  public static String loadCouponResearchQuery;
  
  public static String loadCouponResearchDateQuery;
  

  static {
    StringBuilder builder = new StringBuilder();
    builder.append("select CH.COMMODITY_CODE, ");
    builder.append(" CH.COMMODITY_NAME, ");
    builder.append(" SUM(CASE WHEN SH.FIXED_SALES_STATUS = 1 THEN SD.RETAIL_PRICE * SD.PURCHASING_AMOUNT ");
    builder.append(" ELSE 0 END) AS COMMODITY_SALES_AMOUNT, ");
    builder.append(" COUNT(DISTINCT CASE WHEN SH.FIXED_SALES_STATUS = 1 THEN OD.ORDER_NO ");
    builder.append(" ELSE NULL END) AS COMMODITY_ORDER_AMOUNT ");
    builder.append(" from customer_coupon cc ");
    builder.append(" INNER JOIN ORDER_DETAIL OD ");
    builder.append(" ON CC.ORDER_NO = OD.ORDER_NO ");
    builder.append(" INNER JOIN SHIPPING_HEADER SH ");
    builder.append(" ON OD.ORDER_NO = SH.ORDER_NO AND SH.FIXED_SALES_STATUS = 1 ");
    builder.append(" LEFT OUTER JOIN SHIPPING_DETAIL SD ");
    builder.append(" ON SH.SHIPPING_NO = SD.SHIPPING_NO ");
    builder.append(" AND OD.SHOP_CODE = SD.SHOP_CODE ");
    builder.append(" AND OD.SKU_CODE = SD.SKU_CODE ");
    builder.append(" INNER JOIN COMMODITY_DETAIL CD ");
    builder.append(" ON sd.SKU_CODE = CD.SKU_CODE ");
    builder.append(" INNER JOIN COMMODITY_HEADER CH ");
    builder.append(" ON CH.COMMODITY_CODE = CD.COMMODITY_CODE ");
    builder.append(" where cc.coupon_issue_no = ? and ch.shop_code = ? ");
    builder.append(" GROUP BY CH.COMMODITY_CODE, CH.COMMODITY_NAME");
    loadCouponResearchQuery = builder.toString();
  }
  
  static {
    StringBuilder builder = new StringBuilder();
    builder.append(" SELECT  SUM(coupon_price) AS ISSUE_TOTAL_PRICE, ");
    builder.append(" SUM(CASE WHEN use_flg = ? AND SH.FIXED_SALES_STATUS = 1 THEN coupon_price ");
    builder.append(" ELSE 0 END) AS USED_TOTAL_PRICE, ");
    builder.append(" SUM(CASE WHEN use_flg = ? THEN coupon_price ");
    builder.append(" ELSE 0 END) AS ENABLE_TOTAL_PRICE, ");
    builder.append(" SUM(CASE WHEN use_flg = ? THEN coupon_price ");
    builder.append(" ELSE 0 END) AS DISABLE_TOTAL_PRICE, ");
    builder.append(" SUM(CASE WHEN use_flg = ? THEN coupon_price ");
    builder.append(" ELSE 0 END) AS OVERDUE_TOTAL_PRICE, ");
    builder.append(" SUM(CASE WHEN use_flg = ? THEN coupon_price ");
    builder.append(" ELSE 0 END) AS PHANTOM_TOTAL_PRICE ");
    builder.append(" FROM customer_coupon CI ");
    builder.append(" LEFT JOIN SHIPPING_HEADER SH ");
    builder.append(" ON SH.ORDER_NO = CI.ORDER_NO ");
    builder.append(" where CI.coupon_issue_no = ? ");
    loadCouponResearchDateQuery = builder.toString();
  }
  
  public static String getLoadCouponResearchQuery() {
    return loadCouponResearchQuery;
  }
  

  public static String getloadCouponResearchDateQuery() {
    return loadCouponResearchDateQuery;
  }
}
