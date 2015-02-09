package jp.co.sint.webshop.service.communication;

public final class CampaignQuery {

  /**
   * default constructor
   */
  private CampaignQuery() {
  }

  private static final long serialVersionUID = 1L;

  private static String loadCampaignResearchQuery;

  static {
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT CC.COMMODITY_CODE,");
    builder.append("       CH.COMMODITY_NAME,");
    builder.append("       SUM(CASE WHEN SH.FIXED_SALES_STATUS = 1 THEN SD.RETAIL_PRICE * SD.PURCHASING_AMOUNT");
    builder.append("                ELSE 0 END) AS COMMODITY_SALES_AMOUNT,");
    builder.append("       COUNT(DISTINCT CASE WHEN SH.FIXED_SALES_STATUS = 1 THEN OD.ORDER_NO");
    builder.append("                           ELSE NULL END) AS COMMODITY_ORDER_AMOUNT");
    builder.append("  FROM CAMPAIGN_COMMODITY CC");
    builder.append(" INNER JOIN COMMODITY_HEADER CH");
    builder.append("    ON CC.SHOP_CODE = CH.SHOP_CODE");
    builder.append("   AND CC.COMMODITY_CODE = CH.COMMODITY_CODE");
    builder.append("  LEFT OUTER JOIN ORDER_DETAIL OD");
    builder.append("    ON CC.SHOP_CODE = OD.SHOP_CODE");
    builder.append("   AND CC.COMMODITY_CODE = OD.COMMODITY_CODE");
    builder.append("   AND CC.CAMPAIGN_CODE = OD.CAMPAIGN_CODE");
    builder.append("  LEFT OUTER JOIN SHIPPING_HEADER SH");
    builder.append("    ON OD.ORDER_NO = SH.ORDER_NO");
    builder.append("  LEFT OUTER JOIN SHIPPING_DETAIL SD");
    builder.append("    ON SH.SHIPPING_NO = SD.SHIPPING_NO");
    builder.append("   AND OD.SHOP_CODE = SD.SHOP_CODE");
    builder.append("   AND OD.SKU_CODE = SD.SKU_CODE");
    builder.append(" WHERE CC.SHOP_CODE = ?");
    builder.append("   AND CC.CAMPAIGN_CODE = ?");
    builder.append(" GROUP BY CC.COMMODITY_CODE, CH.COMMODITY_NAME");
    builder.append(" ORDER BY COMMODITY_SALES_AMOUNT DESC,");
    builder.append("          COMMODITY_ORDER_AMOUNT DESC,");
    builder.append("          CC.COMMODITY_CODE ASC");

    loadCampaignResearchQuery = builder.toString();
  }

  public static String getLoadCampaignResearchQuery() {
    return loadCampaignResearchQuery;
  }
}
