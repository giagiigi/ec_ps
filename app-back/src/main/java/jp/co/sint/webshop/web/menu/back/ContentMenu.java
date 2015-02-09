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
public enum ContentMenu implements BackDetailMenuBase {
  /** 首页TOP内容管理 */
  INDEX_TOP_IMAGE("U1100110", "首页TOP内容管理", "/menu/content/index_top_image"),
  
  /** 首页楼层内容管理 */
  INDEX_FLOOR("U1100210", "首页楼层内容管理", "/menu/content/index_floor");

  private String label;

  private String url;

  private String moduleId;

  private Permission[] permissions = new Permission[0];

  private ContentMenu(String moduleId, String label, String url, Permission... permissions) {
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
