package jp.co.sint.webshop.service.result;

/**
 * サービス内で発生したエラーの種別を判断するEnum
 * 
 * @author System Integrator Corp.
 */
public enum CommonServiceErrorContent implements ServiceErrorContent {
  /** 該当データ未存在エラー*/
  NO_DATA_ERROR,
  /** データ重複エラー*/
  DUPLICATED_REGISTER_ERROR,
  /** DB接続エラー */
  DB_CONNECT_ERROR,
  /** Validationエラー */
  VALIDATION_ERROR,
  /** 入力値妥当性エラー */
  INPUT_ADEQUATE_ERROR,
  /** 运行时错误 */
  RUN_TIME_ERROR,
  /** DBオブジェクト実行エラー */
  DB_OBJECT_EXECUTE_ERROR,
  // 2014/06/06 库存更新对应 ob_李 add start
  /** TMAPI连协错误 */
  TM_API_ERROR,
  /** JDAPI连协错误 */
  JD_API_ERROR,
  /** 商品库存信息错误 */
  STOCK_ERROR,
  /** 商品库存再计算处理错误 */
  STOCK_RECALCULATION_ERROR;
  // 2014/06/06 库存更新对应 ob_李 add end

}
