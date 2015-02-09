package jp.co.sint.webshop.service.shop;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.Information;
import jp.co.sint.webshop.data.dto.Tax;
import jp.co.sint.webshop.data.dto.UserAccount;
import jp.co.sint.webshop.data.dto.UserPermission;

/**
 * サイト管理サービスで使用するクエリを集約したクラス
 * 
 * @author System Integrator Corp.
 */
public final class SiteManagementServiceQuery {

  /** default constructor */
  private SiteManagementServiceQuery() {
  }

  public static final String DELETE_USER_PERMISSION_LIST = "DELETE FROM USER_PERMISSION WHERE USER_CODE = ?";

  public static final String LOAD_INFORMATION_LIST = DatabaseUtil.getSelectAllQuery(Information.class)
      + " ORDER BY INFORMATION_START_DATETIME DESC";

  public static final String LOAD_TAX_LIST = DatabaseUtil.getSelectAllQuery(Tax.class) + " ORDER BY APPLIED_START_DATE ASC";

  public static final String LOAD_USER_PERMISSION_LIST = DatabaseUtil.getSelectAllQuery(UserPermission.class)
      + " WHERE USER_CODE = ?";

  public static final String LOAD_USER_ACCOUNT_BY_LOGIN_ID = DatabaseUtil.getSelectAllQuery(UserAccount.class)
      + " WHERE SHOP_CODE = ? AND USER_LOGIN_ID = ?";
}
