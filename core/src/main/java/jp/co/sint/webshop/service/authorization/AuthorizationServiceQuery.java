package jp.co.sint.webshop.service.authorization;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.UserAccount;
import jp.co.sint.webshop.data.dto.UserPermission;

/**
 * 認証サービスで使用するクエリを集約したクラス
 * 
 * @author System Integrator Corp.
 */
public final class AuthorizationServiceQuery {

  /**
   * default constructor
   */
  private AuthorizationServiceQuery() {
  }

  public static final String LOAD_USER_ACCOUNT = DatabaseUtil.getSelectAllQuery(UserAccount.class)
      + " WHERE SHOP_CODE = ? AND USER_LOGIN_ID = ?";

  public static final String LOAD_USER_PERMISSION = DatabaseUtil.getSelectAllQuery(UserPermission.class) + " WHERE USER_CODE = ?";

  public static final String LOAD_CUSTOMER = DatabaseUtil.getSelectAllQuery(Customer.class) + " WHERE LOWER(LOGIN_ID) = ? OR LOWER(MOBILE_NUMBER) = ?";

}
