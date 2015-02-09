package jp.co.sint.webshop.service.catalog;


public class ArrivalGoodsExportQuery extends ArrivalGoodsSearchQuery {

  private static final long serialVersionUID = 1L;

  private static final String LOAD_ARRIVAL_GOODS_CSV = " SELECT a.SHOP_CODE,  "
    + " b.SKU_CODE, "
    + " a.COMMODITY_NAME || " 
    + " CASE "
    + "  WHEN b.STANDARD_DETAIL1_NAME IS NOT NULL AND b.STANDARD_DETAIL2_NAME IS NOT NULL "
    + "  THEN '(' || b.STANDARD_DETAIL1_NAME || ':' || b.STANDARD_DETAIL2_NAME || ')' "
    + "  WHEN b.STANDARD_DETAIL1_NAME IS NOT NULL "
    + "  THEN '(' || b.STANDARD_DETAIL1_NAME || ')' "
    + "  WHEN b.STANDARD_DETAIL2_NAME IS NOT NULL "
    + "  THEN '(' || b.STANDARD_DETAIL2_NAME || ')' "
    + " END, "
    + " c.EMAIL  "
    + " FROM COMMODITY_HEADER a INNER JOIN  "
    + "  (SELECT SHOP_CODE,  "
    + "   SKU_CODE,  "
    + "   STANDARD_DETAIL1_NAME,  "
    + "   STANDARD_DETAIL2_NAME,  "
    + "   COMMODITY_CODE  "
    + "   FROM COMMODITY_DETAIL) b "
    + " ON  b.SHOP_CODE      = a.SHOP_CODE "
    + " AND b.COMMODITY_CODE = a.COMMODITY_CODE INNER JOIN  "
    + "  (SELECT SHOP_CODE, "
    + "   SKU_CODE, "
    + "   EMAIL, "
    + "   COUNT(EMAIL) SUBSCRIPTION_COUNT "
    + "   FROM ARRIVAL_GOODS "
    + "   GROUP BY SHOP_CODE, SKU_CODE, EMAIL) c "
    + " ON  c.SHOP_CODE = a.SHOP_CODE "
    + " AND c.SKU_CODE  = b.SKU_CODE ";

  public ArrivalGoodsExportQuery(ArrivalGoodsSearchCondition condition) {
    createArrivalGoodsSearchQuery(condition, LOAD_ARRIVAL_GOODS_CSV);
  }
  
}
