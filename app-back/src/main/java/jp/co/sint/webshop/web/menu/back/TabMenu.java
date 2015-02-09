package jp.co.sint.webshop.web.menu.back;

import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * タブメニュー制御のenumです。
 * 
 * @author System Integrator Corp.
 */
public enum TabMenu {
  /** 「受注管理」を表す値です。 */
  ORDER("受注管理", OrderMenu.values(), "order"),

  /** 「顧客管理」を表す値です。 */
  CUSTOMER("顧客管理", CustomerMenu.values(), "customer"),

  /** 「商品管理」を表す値です。 */
  CATALOG("商品管理", CatalogMenu.values(), "catalog"),
  
  // 20131023 txw add start
  /** 「内容管理」を表す値です。 */
  CONTENT("内容管理", ContentMenu.values(), "content"),
  // 20131023 txw add end

  /** 「ショップ管理」を表す値です。 */
  SHOP("ショップ管理", ShopMenu.getUseMenu(), "shop"),

  /** 「コミュニケーション」を表す値です。 */
  COMMUNICATION("コミュニケーション", CommunicationMenu.values(), "communication"),

  /** 「分析」を表す値です。 */
  ANALYSIS("分析", AnalysisMenu.getUseMenu(), "analysis"),

  /** 「データ入出力」を表す値です。 */
  //DATA_IO("データ入出力", DataIOMenu.values(), "data"),

  /** 「客服会员咨询待机管理」を表す値です。 */
  /*
   * add by yl 20121208 start
   */
  SERVICE("客服会员咨询待机管理",ServiceMenu.values(),"service");
  
  private String label;

  private BackDetailMenuBase[] menus;

  private String subsystemName;

  private TabMenu(String label, BackDetailMenuBase[] menus, String subsystemName) {
    this.label = label;
    this.menus = menus;
    this.subsystemName = subsystemName;
  }

  /**
   * メニュー情報を取得します。
   * 
   * @return メニュー情報
   */
  public BackDetailMenuBase[] getMenus() {
    return ArrayUtil.immutableCopy(menus);
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
   * サブシステムの名称を取得します。
   * 
   * @return 各サブシステムの名称
   */
  public String getSubsystemName() {
    return subsystemName;
  }

}
