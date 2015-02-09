package jp.co.sint.webshop.web.menu.back;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;

/**
 * メニュー制御のenumです。
 * 
 * @author System Integrator Corp.
 */
public enum ShopMenu implements BackDetailMenuBase {
  /** サイト設定 */
  SITEINFO_EDIT("U1050110", "サイト設定", "/menu/shop/siteinfo_edit"),
  /** ショップマスタ - 管理者権限 */
  SHOP_LIST_SITE("U1050210", "ショップマスタ", "/menu/shop/shop_list", Permission.SHOP_MANAGEMENT_READ_SITE),
  /** ショップマスタ - オペレータ権限 */
  SHOP_LIST_SHOP("U1050220", "ショップ登録", "/menu/shop/shop_edit", Permission.SHOP_MANAGEMENT_READ_SHOP),
  /** ショップ登録 */
  SHOP_EDIT("U1050220", "ショップ登録", "/menu/shop/shop_edit", Permission.SHOP_MANAGEMENT_UPDATE_SITE),
  /** 支払方法設定 */
  PAYMENTMETHOD_LIST("U1050510", "支払方法設定", "/menu/shop/paymentmethod_list"),
  /** 消費税マスタ */
  //TAX("U1050410", "消費税マスタ", "/menu/shop/tax"),
  /** ポイントルール */
  POINTRULE_EDIT("U1050310", "ポイントルール", "/menu/shop/pointrule_edit"),
  /** 管理ユーザマスタ */
  ACCOUNT_LIST_MANAGER("U1050910", "管理ユーザマスタ", "/menu/shop/account_list", Permission.SITE_MANAGER, Permission.SHOP_MANAGER),
  /** 管理ユーザ新規登録 */
  ACCOUNT_EDIT("U1050920", "管理ユーザ登録", "/menu/shop/account_edit", Permission.SITE_MANAGER, Permission.SHOP_MANAGER),
  /** 管理ユーザマスタ */
  ACCOUNT_LIST_OPERATOR("U1050920", "管理ユーザ登録", "/menu/shop/account_edit", Permission.SITE_OPERATOR, Permission.SHOP_OPERATOR),
  /** 配送・送料設定 */
  //DELIVERY_TYPE("U1050610", "配送・送料設定", "/menu/shop/delivery_type"),
  /** テロップ/お知らせ編集 */
  INFORMATION("U1051210", "お知らせ編集", "/menu/shop/information"),
  /** メールテンプレート */
  MAILTEMPLATE_EDIT("U1051110", "メールテンプレート", "/menu/shop/mailtemplate_edit"),
  /** カレンダーマスタ */
  CALENDAR("U1050710", "カレンダーマスタ", "/menu/shop/calendar"),
//2010/04/27 ShiKui Add Start.  
  /** 在线客服 */
  //ONLINE_SERVICE_EDIT("U1051010", "在线客服", "/menu/shop/onlineservice_edit"),
//2010/04/27 ShiKui Add End.
  
  //2010/6/10 ytw Add Start.
  //GOOGLE_ANALYTICS_SETTING("U1051020", "GoogleAnalysis设置", "/menu/shop/google_analytics_setting"),
  
  BAIDU_ANALYTICS_SETTING("U1051090", "百度分析设置", "/menu/shop/baidu_analytics_setting"),
  
  SMSTEMPLATE_EDIT("U1051030", "SMS模板", "/menu/shop/smstemplate_edit"),
//soukai delete ob 2011/12/19 ob start
  //COUPON_RULE("U1051040", "优惠券规则", "/menu/shop/coupon_rule"),

  //COUPON_ISSUE("U1051050", "优惠券发放", "/menu/shop/coupon_issue_list"),
//soukai delete ob 2011/12/19 ob end
  //ADVERT_EDIT("U1050840", "広告設定", "/menu/shop/advert_edit"),
  
  //2010/6/10 ytw Add End.
  
  // soukai add ob 2011/12/14 shb start
  DELIVERY_COMPANY("U1051410", "配送公司设定", "/menu/shop/delivery_company_list"),
  // soukai add ob 2011/12/14 shb end\
//soukai add ob 2011/12/14 ob start
  DELIVERY_COMPANY_CHARGE("U1051310", "运费设定", "/menu/shop/delivery_company_charge"); 
  // soukai add ob 2011/12/14 ob end
  private String label;

  private String url;

  private String moduleId;

  private Permission[] permissions = new Permission[0];

  private ShopMenu(String moduleId, String label, String url, Permission... permissions) {
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
  public String getLabel() {
    return StringUtil.coalesce(CodeUtil.getLabel(this), this.label);
  }

  /**
   * 画面の名称を取得します。
   * 
   * @return 各画面の名称
   */
  public String getUrl() {
    return url;
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
    return ArrayUtil.immutableCopy(this.permissions);
  }

  /**
   * ログインユーザに参照権限があるか判定します。
   * 
   * @param info
   * @return 参照権限があればtrue
   */
  public boolean hasPermission(BackLoginInfo info) {
    for (Permission p : permissions) {
      if (p.isGranted(info)) {
        return true;
      }
    }
    return false;
  }

  /**
   * タブメニューごとに表示するサブメニューの一覧を返します
   * 
   * @return サブメニューの一覧
   */
  public static ShopMenu[] getUseMenu() {
    List<ShopMenu> menus = new ArrayList<ShopMenu>();
    WebshopConfig config = DIContainer.getWebshopConfig();
    for (ShopMenu menu : values()) {
      if (config.isOne()) {
        if (menu != ShopMenu.SHOP_LIST_SITE && menu != ShopMenu.SHOP_LIST_SHOP && menu != ShopMenu.SHOP_EDIT) {
          menus.add(menu);
        }
      } else {
        menus.add(menu);
      }
    }
    return menus.toArray(new ShopMenu[menus.size()]);
  }

}
