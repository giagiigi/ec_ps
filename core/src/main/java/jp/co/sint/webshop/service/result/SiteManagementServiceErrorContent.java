package jp.co.sint.webshop.service.result;

/**
 * ShopManagementService内で発生するエラーEnum
 * 
 * @author System Integrator Corp.
 */
public enum SiteManagementServiceErrorContent implements ServiceErrorContent {
  /** サイト未登録エラー */
  SITE_NO_DEF_FOUND_ERROR,

  /** 消費税データ非存在エラー */
  NO_TAX_DATA_ERROR,

  /** 管理ユーザ未登録エラー */
  USER_ACOUNT_NOT_FOUND_ERROR,

  /** お知らせ情報費存在エラー */
  NO_INFORMATION_DATE_ERROR;
}
