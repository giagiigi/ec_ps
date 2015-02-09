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
public enum CustomerMenu implements BackDetailMenuBase {

  /** 顧客マスタ */
  CUSTOMER_LIST("U1030110", "顧客マスタ", "/menu/customer/customer_list"),
  /** 顧客登録 */
  CUSTOMER_EDIT("U1030120", "顧客登録", "/menu/customer/customer_edit"),
  /** ポイント利用状況 */
  POINT_STATUS("U1030310", "ポイント利用状況", "/menu/customer/point_status"),
  /** ポイント利用状況 */
  //COUPON_STATUS("U1030610", "优惠券使用状况", "/menu/customer/coupon_status"),
  /** 顧客グループマスタ */
  CUSTOMER_GROUP("U1030410", "顧客グループマスタ", "/menu/customer/customer_group");
  /** 顧客属性マスタ */
  //CUSTOMER_ATTRIBUTE("U1030510", "顧客属性マスタ", "/menu/customer/customer_attribute");

  private String label;

  private String url;

  private String moduleId;

  private Permission[] permissions = new Permission[0];

  private CustomerMenu(String moduleId, String label, String url, Permission... permissions) {
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
