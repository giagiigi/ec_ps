package jp.co.sint.webshop.web.menu.back;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * メニュー制御のenumです。
 * 
 * @author System Integrator Corp.
 */
public enum AnalysisMenu implements BackDetailMenuBase {

  /** アクセスログ集計 */
  //ACCESS_LOG("U1070110", "アクセスログ集計", "/menu/analysis/access_log"),
  /** リファラー集計 */
  //REFERER("U1070130", "リファラー集計", "/menu/analysis/referer"),
  /** 商品別アクセスログ集計 */
  //COMMODITY_ACCESS_LOG("U1070210", "商品別アクセスログ集計", "/menu/analysis/commodity_access_log"),
  /** 顧客嗜好分析 */
  CUSTOMER_PREFERENCE("U1070310", "顧客嗜好分析", "/menu/analysis/customer_preference"),
  /** 顧客分析 */
  //CUSTOMER_STASTISTICS("U1070510", "顧客分析", "/menu/analysis/customer_statistics"),
  /** RFM分析 */
  RFM_ANALYSIS("U1070710", "RFM分析", "/menu/analysis/rfm_analysis"),
  /** ショップ別売上集計 */
  SALES_AMOUNT_SITE("U1070810", "ショップ別売上集計", "/menu/analysis/sales_amount", Permission.ANALYSIS_READ_SITE),
  /** 売上集計(ショップ) */
  SALES_AMOUNT_SHOP("U1070820", "売上集計", "/menu/analysis/sales_amount", Permission.ANALYSIS_READ_SHOP),
  /** 売上集計(一店舗) */
  SALES_AMOUNT_ONE("U1070820", "売上集計", "/menu/analysis/sales_amount_shop"),
  /** 売上集計 */
  SALES_AMOUNT_SKU("U1070830", "商品別売上集計", "/menu/analysis/sales_amount_sku"),
  /** 促销企划分析*/
  PLAN_ANALYSIS_0("U1071210", "商品折扣活动分析", "/app/analysis/plan_analysis/init/0"),
  /** 特集企划分析*/
  PLAN_ANALYSIS_1("U1071310", "特集活动分析", "/app/analysis/plan_analysis/init/1"),
  /** 顾客组别优惠分析 */
  CUSTOMER_GROUP_CAMPAIGN("U1071010", "会员折扣活动分析", "/menu/analysis/customer_group_campaign"),
  /** 顾客别优惠券分析 */
  PRIVATE_COUPON("U1071110", "会员优惠券使用状况", "/menu/analysis/private_coupon"),
  /** 公共优惠券分析 */
  NEW_PUBLIC_COUPON("U1071510", "媒体优惠代码使用状况", "/menu/analysis/new_public_coupon"),
  /** 礼品卡发行充值统计 */
  GIFT_CARD_LOG("U1071610", "礼品卡发行充值统计", "/menu/analysis/gift_card_log"),
  /** 礼品卡使用明细分析 */
  GIFT_CARD_USE_LOG("U1071710", "礼品卡使用明细分析", "/menu/analysis/gift_card_use_log");
  
  private AnalysisMenu(String moduleId, String label, String url, Permission... permissions) {
    this.moduleId = moduleId;
    this.label = label;
    this.url = url;
    this.permissions = permissions;
  }

  private String moduleId;

  private String label;

  private String url;

  private Permission[] permissions = new Permission[0];

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

  /**
   * タブメニューごとに表示するサブメニューの一覧を返します
   * 
   * @return サブメニューの一覧
   */
  public static AnalysisMenu[] getUseMenu() {
    List<AnalysisMenu> menus = new ArrayList<AnalysisMenu>();
    WebshopConfig config = DIContainer.getWebshopConfig();
    for (AnalysisMenu menu : AnalysisMenu.values()) {
      if (config.isOne()) {
        if (menu != AnalysisMenu.SALES_AMOUNT_SHOP && menu != AnalysisMenu.SALES_AMOUNT_SITE) {
          menus.add(menu);
        }
      } else {
        menus.add(menu);
      }
    }
    return menus.toArray(new AnalysisMenu[menus.size()]);
  }
}
