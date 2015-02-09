package jp.co.sint.webshop.service.shop;

import java.io.Serializable;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.Shop;

public final class SiteInfoQuery implements Serializable {

  /**
   * default constructor
   */
  private SiteInfoQuery() {
  }

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public static final String SITE_LOAD_QUERY = DatabaseUtil.getSelectAllQuery(Shop.class) + " WHERE SHOP_TYPE = 0";

}
