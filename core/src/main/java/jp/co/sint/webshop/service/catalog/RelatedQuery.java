package jp.co.sint.webshop.service.catalog;

public final class RelatedQuery {

  /**
   * default constructor
   */
  private RelatedQuery() {
  }
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  

  public static final String GET_APPLIED_CAMPAIGN_INFO = ""
                       + " SELECT CAM.CAMPAIGN_CODE,"
                       + "       CAM.CAMPAIGN_NAME,"
                       + "       CAM.CAMPAIGN_DISCOUNT_RATE"
                       + " FROM  CAMPAIGN_COMMODITY CC,"
                       + "       CAMPAIGN CAM "
                       + " WHERE CC.SHOP_CODE = CAM.SHOP_CODE "
                       + "   AND CC.CAMPAIGN_CODE = CAM.CAMPAIGN_CODE "
                       + "   AND CAM.CAMPAIGN_START_DATE <= ? "
                       + "   AND CAM.CAMPAIGN_END_DATE >= ? "
                       + "   AND CC.SHOP_CODE = ? "
                       + "   AND CC.COMMODITY_CODE = ? "
                       + " ORDER BY CAM.CAMPAIGN_DISCOUNT_RATE DESC, "
                       + "          CAM.CAMPAIGN_START_DATE DESC, "
                       + "          CAM.CAMPAIGN_CODE ASC";
  
  
  public static final String GET_DELETE_CAMPAIGN_COMMODITY_QUERY = ""
                      + " DELETE FROM CAMPAIGN_COMMODITY "
                      + " WHERE SHOP_CODE      = ? AND"
                      + "       CAMPAIGN_CODE  = ? AND"
                      + "       COMMODITY_CODE = ?";

  public static final String GET_DELETE_GIFT_COMMODITY_QUERY = ""
                      + " DELETE FROM GIFT_COMMODITY "
                      + " WHERE SHOP_CODE      = ? AND"
                      + "       GIFT_CODE      = ? AND"
                      + "       COMMODITY_CODE = ?";

  public static final String GET_DELETE_TAG_COMMODITY_QUERY = ""
                      + " DELETE FROM TAG_COMMODITY "
                      + " WHERE SHOP_CODE          = ? AND"
                      + "       TAG_CODE = ? AND"
                      + "       COMMODITY_CODE     = ?";
}
