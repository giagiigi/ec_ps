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
public enum ServiceMenu implements BackDetailMenuBase {
  /** 电话中心待机管理 */
  MEMBER_INFO("U1090110", "客服中心", "/menu/service/member_info"),
  /** 礼品卡退款确认 */
  GIFT_CARD_RETURN_CONFIRM("U1090120", "礼品卡退款确认", "/menu/service/gift_card_return_confirm"),
  /** 咨询投诉管理 */
  INQUIRY_LIST("U1090130", "咨询投诉管理", "/menu/service/inquiry_list");

  private String label;

  private String url;

  private String moduleId;

  private Permission[] permissions = new Permission[0];

  private ServiceMenu(String moduleId, String label, String url, Permission... permissions) {
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
