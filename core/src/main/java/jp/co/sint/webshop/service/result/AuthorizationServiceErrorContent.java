package jp.co.sint.webshop.service.result;

/**
 * AuthorizationService内で発生するエラーEnum
 * 
 */
public enum AuthorizationServiceErrorContent implements ServiceErrorContent {
  /** 管理ユーザ登録なしエラー*/
  USER_ACCOUNT_NOT_FOUND,
  /** 管理ユーザパスワード不一致エラー*/
  USER_ACCOUNT_PASSWORD_UNMATCH,  
  /** 管理ユーザロックエラー*/
  USER_ACCOUNT_LOCK,
  /** 管理ユーザ権限エラー*/
  USER_PERMISSION_NOT_FOUND,
  /** 顧客登録なしエラー*/
  CUSTOMER_NOT_FOUND,
  /** 顧客パスワード不一致エラー*/
  CUSTOMER_PASSWORD_UNMATCH,  
  /** 顧客ロックエラー*/
  CUSTOMER_LOCK,
  //20111226 os013 add start
  /** 支付宝登录会员判断*/
  CUSTOMER_KBN;
  //20111226 os013 add end
}
