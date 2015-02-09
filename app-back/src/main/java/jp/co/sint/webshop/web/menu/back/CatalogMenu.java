package jp.co.sint.webshop.web.menu.back;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * メニュー制御のenumです。
 * 
 * @author System Integrator Corp.
 */
public enum CatalogMenu implements BackDetailMenuBase {
  /** 商品マスタ */
  COMMODITY_LIST("U1040110", "商品マスタ", "/menu/catalog/commodity_list"),
  /** 商品登録 */
  //COMMODITY_EDIT("U1040120", "商品登録", "/menu/catalog/commodity_edit"),
  /** カテゴリマスタ */
  CATEGORY("U1040210", "カテゴリマスタ", "/menu/catalog/category"),
  /** ギフトマスタ */
  //GIFT("U1040310", "ギフトマスタ", "/menu/catalog/gift"),
  /** タグマスタ */
  TAG("U1040410", "タグマスタ", "/menu/catalog/tag"),
  /** 入出庫管理 */
  //STOCK_IO("U1040510", "入出庫管理", "/menu/catalog/stock_io"),
  /** 在庫状況設定 */
  //STOCK_STATUS("U1040610", "在庫状況設定", "/menu/catalog/stock_status"),
  /** 入荷お知らせ */
  //ARRIVAL_GOODS("U1040710", "入荷お知らせ", "/menu/catalog/arrival_goods"),
  /** 商品詳細レイアウト */
  //COMMODITY_COMMON("U1040810", "商品詳細レイアウト", "/menu/catalog/commodity_common"),
  /**品牌管理 */
  BRAND_LIST("U1040910", "品牌管理", "/menu/catalog/brand_list"),
  //add by os014 2011-12-20 begin
  /** TMALL商品管理 */
  TMALL_COMMODITY_LIST("U1040150", "TMALL商品管理", "/menu/catalog/tmall_commodity_list"),
  //add by os014 2011-12-20 end
  //add by os014 2011-12-21 begin
  /** 商品登録 */
  //TMALL_COMMODITY_EDIT("U1040220", "商品编辑", "/menu/catalog/tmall_commodity_edit"),
  //add by os014 2011-12-21 end
//add by os014 2011-12-21 begin
  /** 商品登録 */
  COMMODITY_SYNCHRO("U1040230", "商品同期化", "/menu/catalog/commodity_cynchro"),
  //add by os014 2011-12-21 end
  //add by ysy 20111227 start
  /**商品CSV导出 */
  COMMODITY_CSV_EXPORT("U1800001", "商品CSV导出", "/menu/catalog/commodity_csv_export"),
  /**商品CSV导入 */
  COMMODITY_CSV_IMPORT("U1800002", "商品CSV导入", "/menu/catalog/commodity_csv_import"),
  /**库存管理 */
  STOCK_LIST("U1800003", "库存管理", "/menu/catalog/stock_list"),
  // add by ysy 20111227 end
  // add by ob 20120210 start
  /**商品图片管理*/
  COMMODITY_IMG_UPLOAD_HISTORY("U1041010", "商品图片管理", "/menu/catalog/commodity_image"),
  // add by ob 20120210 end
  //add by zzt 2013-6-6 15:38:01 start
  COMMODITY_CONSTITUTE("U1040130", "组合商品登录", "/menu/catalog/commodity_constitute"),
  //add by zzt 2013-6-6 15:38:14 end
  COMMODITY_HOT_SALE("U1040140", "热销商品登录", "/menu/catalog/commodity_hot_sale"),
  
  COMMODITY_CHOSEN_SORT("U1040160", "品店精选排序", "/menu/catalog/commodity_chose"),
  
  COMMODITY_PRICE_CHANGE_REVIEW("U1040170", "商品改价审核", "/menu/catalog/commodity_price_change_review"),
  //zzy 2015-01-09 add start
  COMMODITY_MASTER_LIST("U1040180","TM/JD多SKU商品关联","/menu/catalog/commodity_master_list");
  //zzy 2015-01-09 add end
  private String label;

  private String url;

  private String moduleId;

  private Permission[] permissions = new Permission[0];

  private CatalogMenu(String moduleId, String label, String url, Permission... permissions) {
    this.moduleId = moduleId;
    this.label = label;
    this.url = url;
    this.permissions = permissions;
  }

  /**
   * 画面のURLを取得します。
   * 
   * @return 各画面のURL
   */
  public String getUrl() {
    return url;
  }

  /**
   * 画面の名称を取得します。
   * 
   * @return 各画面の名称
   */
  public String getLabel() {
    return StringUtil.coalesce(CodeUtil.getLabel(this), this.label);
  }

  /**
   * 画面のモジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return this.moduleId;
  }

  /**
   * 画面の権限情報を取得します。
   * 
   * @return 各画面の権限情報
   */
  public Permission[] getPermissions() {
    return ArrayUtil.immutableCopy(permissions);
  }

}
