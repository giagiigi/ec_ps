package jp.co.sint.webshop.service.shop;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.domain.DefaultFlg;
import jp.co.sint.webshop.data.dto.RegionBlockLocation;

/**
 * ショップ管理サービスで使用するクエリを集約したクラス
 * 
 * @author System Integrator Corp.
 */
public final class ShopManagementServiceQuery {

  /** default constructor */
  private ShopManagementServiceQuery() {
  }

  public static final String LOAD_REGION_BLOCK_LOCATION = DatabaseUtil.getSelectAllQuery(RegionBlockLocation.class)
      + " WHERE SHOP_CODE = ? AND PREFECTURE_CODE = ?";
  
  public static final String UPDATE_DEFAULT_DELIVERY_COMPANY = "UPDATE DELIVERY_COMPANY SET DEFAULT_FLG =" + DefaultFlg.DEFAULT.getValue() 
  + ",UPDATED_USER = ? ,UPDATED_DATETIME = ?"
  + " WHERE DELIVERY_COMPANY_NO = ? ";
  
  public static final String UPDATE_DEFAULT_DELIVERY_COMPANY2 = "UPDATE DELIVERY_COMPANY SET DEFAULT_FLG =" + DefaultFlg.NOT_DEFAULT.getValue() 
  + ",UPDATED_USER = ? ,UPDATED_DATETIME = ?"
  + " WHERE DELIVERY_COMPANY_NO <> ? ";
}
