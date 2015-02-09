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
public enum OrderMenu implements BackDetailMenuBase {
  /** 受注入金管理 */
  ORDER_LIST("U1020210", "受注入金管理", "/menu/order/order_list"),
  /** 新規受注登録 */
  NEWORDER_COMMODITY("U1020110", "新規受注登録", "/menu/order/neworder_commodity"),
  /** 出荷管理 */
  SHIPPING_LIST("U1020410", "出荷管理", "/menu/order/shipping_list"),
  /** 受注入金管理 */
  ORDER_CONFIRM_LIST("U1020510", "受注確認管理", "/menu/order/order_confirm_list"),
  /**Jd拆分订单管理*/
  JD_ORDER_LIST("U1020610","JD订单拆分管理","/menu/order/jd_order_list"),
  // zzy 2014/12/31 start 
  UNTRUE_ORDER_WORD("U1020710","虚假订单关键词管理","/menu/order/untrue_order_word");
  //zzy 2014/12/31 end 
  
  private String label;

  private String url;

  private String moduleId;

  private Permission[] permissions = new Permission[0];

  private OrderMenu(String moduleId, String label, String url, Permission... permissions) {
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
