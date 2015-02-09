package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.CommodityLayout;

public class CommodityLayoutQuery extends AbstractQuery<CommodityLayout> {

  /**
   */
  private static final long serialVersionUID = 1L;

  /**
   */
  public CommodityLayoutQuery() {

  }

  public static final String GET_COMMODITY_LAYOUT_LIST = 
    DatabaseUtil.getSelectAllQuery(CommodityLayout.class)
    + " WHERE SHOP_CODE = ? ORDER BY DISPLAY_ORDER, PARTS_CODE";

  public static final String GET_DELETE_COMMODITY_LAYOUT_SHOP_QUERY = 
    "DELETE FROM COMMODITY_LAYOUT WHERE SHOP_CODE = ?";

  public Class<CommodityLayout> getRowType() {
    return CommodityLayout.class;
  }
}
