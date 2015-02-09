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
public enum DataIOMenu implements BackDetailMenuBase {
  /** データ一括出力 */
  DATA_EXPORT("U1080110", "データ一括出力", "/menu/data/data_export"),
  /** データ一括取込 */
  DATA_IMPORT("U1080210", "データ一括取込", "/menu/data/data_import"),
  /** ファイルアップロード */
  FILE_UPLOAD("U1080310", "ファイルアップロード", "/menu/data/file_upload"),
  /** ファイル削除 */
  FILE_DELETE("U1080410", "ファイル削除", "/menu/data/file_delete");

  private String label;

  private String url;

  private String moduleId;

  private Permission[] permissions = new Permission[0];

  private DataIOMenu(String moduleId, String label, String url, Permission... permissions) {
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
