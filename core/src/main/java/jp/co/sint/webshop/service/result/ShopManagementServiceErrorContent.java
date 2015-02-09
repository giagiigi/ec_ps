package jp.co.sint.webshop.service.result;

/**
 * ShopManagementService内で発生するエラーEnum
 * 
 * @author System Integrator Corp.
 */
public enum ShopManagementServiceErrorContent implements ServiceErrorContent {
  /** ショップ未登録エラー */
  SHOP_NO_DEF_FOUND_ERROR,

  /** ショップ登録済みエラー */
  SHOP_REGISTERED_ERROR,

  /** 削除対象配送種別使用中エラー */
  DELIVERY_TYPE_STILL_USE_ERROR,

  /** 削除対象配送種別削除不可エラー */
  USED_DELIVERY_TYPE,
  /** 金融機関全件削除エラー */
  DELETE_ALL_BANK,
  /** 邮局機関全件削除エラー */
  DELETE_ALL_POST,
  //soukai add 2011/12/19 ob start
  DEFAULT_DELETE_ERROR,
  //soukai add 2011/12/19 ob end
  /** 支払方法削除不可エラー削除エラー */
  NOT_DELETE_PAYMENT;
  
  
  
  
}
