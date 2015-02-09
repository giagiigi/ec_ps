package jp.co.sint.webshop.service.customer;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.CartHistory;

public final class CartHistoryQuery {

  /**
   * default constructor
   */
  private CartHistoryQuery() {
  }
  
  public static final String SELECT_CART_HISTORY_BY_CUSTOMER_CODE_QUERY = DatabaseUtil.getSelectAllQuery(CartHistory.class) + 
      " WHERE CUSTOMER_CODE = ?";

  public static final String DELETE_CART_HISTORY_QUERY = "DELETE FROM CART_HISTORY WHERE CUSTOMER_CODE = ?";
}
