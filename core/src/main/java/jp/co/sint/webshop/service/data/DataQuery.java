package jp.co.sint.webshop.service.data;

public final class DataQuery {

  private DataQuery() {
  }
  
  
  public static final String GET_COMMODITY_CODE_EXISTS =
      " SELECT * "
    + "   FROM COMMODITY_HEADER "
    + "  WHERE SHOP_CODE = ? "
    + "    AND COMMODITY_CODE = ?";
    
  public static final String GET_SKU_CODE_EXISTS =
      " SELECT * "
    + "   FROM COMMODITY_DETAIL "
    + "  WHERE SHOP_CODE = ? "
    + "    AND SKU_CODE = ? ";
  
  public static final String GET_GIFT_EXISTS =
      " SELECT * "
    + "   FROM GIFT "
    + "  WHERE SHOP_CODE = ? "
    + "    AND GIFT_CODE = ? ";

}
