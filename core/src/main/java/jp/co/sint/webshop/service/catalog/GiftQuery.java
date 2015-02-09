package jp.co.sint.webshop.service.catalog;

public final class GiftQuery {
  
  /**
   * default constructor
   */
  private GiftQuery() {
  }
  
  public static String getGiftCountQuety() {
    final String sql = " SELECT A.SHOP_CODE "
    + "       ,A.GIFT_CODE "
    + "       ,A.GIFT_NAME "
    + "       ,A.GIFT_DESCRIPTION "
    + "       ,A.GIFT_PRICE "
    + "       ,A.DISPLAY_ORDER "
    + "       ,A.GIFT_TAX_TYPE "
    + "       ,A.DISPLAY_FLG "
    + "       ,CAST(COALESCE(CAST(B.RELATED_COUNT AS CHAR(10) )  "
    //postgreSQL start
    //+ "       ,'0') AS NUMBER) RELATED_COUNT "
    + "       ,'0') AS numeric) RELATED_COUNT " //$NON-NLS-1$
    //postgreSQL end
    + "       ,A.ORM_ROWID "
    + "       ,A.CREATED_USER "
    + "       ,A.CREATED_DATETIME "
    + "       ,A.UPDATED_USER "
    + "       ,A.UPDATED_DATETIME "
    + "   FROM GIFT A "
    + "   LEFT OUTER "
    + "   JOIN ( "
    + " SELECT SHOP_CODE "
    + "       ,GIFT_CODE "
    + "       ,COUNT(GIFT_CODE) AS RELATED_COUNT "
    + "   FROM GIFT_COMMODITY "
    + "  WHERE SHOP_CODE = ? "
    + "  GROUP BY SHOP_CODE "
    + "          ,GIFT_CODE ) B ON B.SHOP_CODE = A.SHOP_CODE "
    + "    AND B.GIFT_CODE = A.GIFT_CODE "
    + "  WHERE A.SHOP_CODE = ? "
    + "  ORDER BY A.GIFT_CODE  ";
    return sql;
  }

}
