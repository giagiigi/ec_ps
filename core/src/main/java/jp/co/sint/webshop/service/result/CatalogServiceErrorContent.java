package jp.co.sint.webshop.service.result;

/**
 * CatalogService内で発生するエラーEnum
 * 
 * @author System Integrator Corp.
 */
public enum CatalogServiceErrorContent implements ServiceErrorContent {

  /** 商品削除エラー */
  DELETE_COMMODITY_ERROR,

  /** カテゴリ集計実行エラー */
  CATEGORY_SUMMARY_EXECUTE_ERROR,

  /** カテゴリパス不正エラー */
  UPDATE_CATEGORY_PATH_ERROR,

  /** カテゴリ階層最大値オーバーエラー */
  CATEGORY_MAX_DEPTH_OVER_ERROR,

  /** ワークテーブル初期化エラー */
  WORKTABLE_INITIALIZE_ERROR,

  /** 規格名称設定エラー */
  STANDARD_NAME_SET_ERROR,
  //20120107 os013 add start
  /**T-Mall的引当数大于库存数*/
  STOCK_ALLOCATEDTMALL_STOCKTMALL_ERROR,
  /** 安全库存数量+EC引当数+Tmall引当数大于总库存数 */
  STOCK_STOCKTHRESHOLD_ALLOCATEDQUANTITY_ALLOCATEDTMALL_ERROR,
  /**库存在分配未成功*/
  STOCK_THE_ALLOCATION_WAS_NOT_SUCCESSFUL_ERROR,
  // 2014/05/02 京东WBS对应 ob_姚 add start
  /**商家类目获取失败*/
  GET_JD_CATEGORY_ERROR,
  /**商家类目属性获取失败*/
  GET_JD_PROPERTY_ERROR,
  /**商家类目属性值获取失败*/
  GET_JD_PROPERTY_VALUE_ERROR,
  /**商家已授权品牌查询失败*/
  GET_VENDER_BRAND_ERROR,
  /**参数错误*/
  PARAM_ERROR,
  /**京东商品图片上传失败*/
  JD_IMAGE_UPLOAD_ERROR,
  // 2014/05/02 京东WBS对应 ob_姚 add end
  /**淘宝上传未成功*/
  STOCK_TMALL_UP_ERROR;
  //20120107 os013 add end
}
