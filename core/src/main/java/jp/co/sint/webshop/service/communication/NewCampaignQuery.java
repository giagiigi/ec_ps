package jp.co.sint.webshop.service.communication;

public final class NewCampaignQuery {

  /**
   * default constructor
   */
  private NewCampaignQuery() {
  }

  private static final long serialVersionUID = 1L;

  private static String loadCampaignResearchQuery;

  static {
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT CM.CAMPAIGN_CODE,");
    builder.append(" CD.ATTRIBUTR_VALUE");
    builder.append("  FROM CAMPAIGN_MAIN CM");
    builder.append("  LEFT OUTER JOIN CAMPAIGN_DOINGS CD");
    builder.append("  ON CM.CAMPAIGN_CODE = CD.CAMPAIGN_CODE");
    builder.append(" WHERE CM.CAMPAIGN_CODE <> ?");
    builder.append("   AND CD.CAMPAIGN_TYPE = ?");
    builder.append(" AND (CAMPAIGN_START_DATE BETWEEN ? AND ?");
    builder.append(" OR CAMPAIGN_END_DATE BETWEEN ? AND ? ");
    builder.append(" OR (CAMPAIGN_START_DATE <= ? AND CAMPAIGN_END_DATE >= ?))");


    loadCampaignResearchQuery = builder.toString();
    
  }

  public static String getLoadCampaignResearchQuery() {
    return loadCampaignResearchQuery;
  }
  
  public Class<GiftCampaign> getRowType() {
	   
	    return GiftCampaign.class;
	  }
}
