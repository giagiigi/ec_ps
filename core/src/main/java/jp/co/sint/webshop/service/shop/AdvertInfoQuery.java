package jp.co.sint.webshop.service.shop;

import java.io.Serializable;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.Advert;


public class AdvertInfoQuery implements Serializable {

  /**
   * default constructor
   */
  private AdvertInfoQuery() {
  }

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public static final String ADVERT_LOAD_QUERY = DatabaseUtil.getSelectAllQuery(Advert.class)
  + " where advert_no = ? ";

  public static final String LOAD_ENABLED_ADVERT_QUERY; 

  public static final String LOAD_ADVERT_QUERY; 
  
  static {
    StringBuilder builder = new StringBuilder();
    builder.append(" SELECT * ");
    builder.append(" FROM advert ");
    builder.append(" where advert_enabled_flg = 1 ");
    builder.append(" and advert_type = ? ");
    LOAD_ENABLED_ADVERT_QUERY = builder.toString();
  }
  
  static {
    StringBuilder builder = new StringBuilder();
    builder.append(" SELECT * ");
    builder.append(" FROM advert ");
    builder.append(" where ");
    builder.append(" advert_type = ? ");
    LOAD_ADVERT_QUERY = builder.toString();
  }
}
