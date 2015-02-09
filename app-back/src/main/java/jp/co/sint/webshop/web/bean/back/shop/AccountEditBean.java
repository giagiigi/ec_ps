package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.MenuGroup;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.AdministrationUserType;
import jp.co.sint.webshop.data.domain.LoginLockedFlg;
import jp.co.sint.webshop.data.domain.PermissionType;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.CollectionUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * 管理ユーザマスタ明細Bean<BR>
 * 各Modeについて<BR>
 * <BR>
 * updateMode:更新モード<BR>
 * 担当領域とショップ名を制御する<BR>
 * administratorMode:管理者モード<BR>
 * パスワード以外の全項目を制御する<BR>
 * confirmMode:登録確認モード<BR>
 * パスワードを制御する<BR>
 * <BR>
 * <BR>
 * U1050920:管理ユーザマスタ明細のデータモデルです。
 * 
 * @author System Integrator Corp.
 */

public class AccountEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String tableHeader;

  @Required
  @Metadata(name = "担当領域", order = 1)
  private String roleDiv;

  @Required
  private String shopCode;

  private List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();

  private String userCode;

  @Required
  @AlphaNum2
  @Length(20)
  @Metadata(name = "管理者ID", order = 2)
  private String userLoginId;

  @Required
  @Length(20)
  @Metadata(name = "管理ユーザ名", order = 3)
  private String userName;

  @Length(50)
  @Metadata(name = "パスワード", order = 4)
  private String password;

  @Length(50)
  @Metadata(name = "パスワード確認", order = 5)
  private String passwordConfirm;

  @Digit
  private String loginLock;

  @Digit
  @Length(2)
  @Range(min = 0)
  @Metadata(name = "ログインエラー回数", order = 6)
  private String loginErrorCount;

  @Required
  @Metadata(name = "管理権限", order = 7)
  private String administrator;

  @Length(200)
  @Metadata(name = "備考", order = 8)
  private String memo;

  private PermissionSet sitePermission = new PermissionSet();

  private PermissionSet shopPermission = new PermissionSet();

  private Date updateDatetime;

  private String updateMode;

  private String administratorMode;

  private String confirmMode;

  private String process;

  private boolean displayConfirmButton;

  private PermissionType[] permissionTypeList = PermissionType.values();

  private AdministrationUserType[] administrationUserTypeList = AdministrationUserType.values();

  private MenuGroup[] menuGroupList = MenuGroup.values();

  private String siteName;

  private boolean displayButton;

  private boolean siteUserFlg = false;

  /**
   * siteUserFlgを取得します。
   * 
   * @return siteUserFlg
   */
  public boolean isSiteUserFlg() {
    return siteUserFlg;
  }

  /**
   * siteUserFlgを設定します。
   * 
   * @param siteUserFlg
   *          siteUserFlg
   */
  public void setSiteUserFlg(boolean siteUserFlg) {
    this.siteUserFlg = siteUserFlg;
  }

  /**
   * displayButtonを取得します。
   * 
   * @return displayButton displayButton
   */
  public boolean isDisplayButton() {
    return displayButton;
  }

  /**
   * displayButtonを設定します。
   * 
   * @param displayButton
   *          displayButton
   */
  public void setDisplayButton(boolean displayButton) {
    this.displayButton = displayButton;
  }

  /**
   * displayConfirmButtonを取得します。
   * 
   * @return displayConfirmButton displayConfirmButton
   */
  public boolean isDisplayConfirmButton() {
    return displayConfirmButton;
  }

  /**
   * displayConfirmButtonを設定します。
   * 
   * @param displayConfirmButton
   *          displayConfirmButton
   */
  public void setDisplayConfirmButton(boolean displayConfirmButton) {
    this.displayConfirmButton = displayConfirmButton;
  }

  /**
   * confirmModeを取得します。
   * 
   * @return the confirmMode
   */
  public String getConfirmMode() {
    return confirmMode;
  }

  /**
   * confirmModeを設定します。
   * 
   * @param confirmMode
   *          confirmMode
   */
  public void setConfirmMode(String confirmMode) {
    this.confirmMode = confirmMode;
  }

  /**
   * updateModeを取得します。
   * 
   * @return updateMode
   */
  public String getUpdateMode() {
    return updateMode;
  }

  /**
   * updateModeを設定します。
   * 
   * @param updateMode
   *          updateMode
   */
  public void setUpdateMode(String updateMode) {
    this.updateMode = updateMode;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    this.setUserCode(reqparam.get("userCode"));
    this.setRoleDiv(reqparam.get("roleDiv"));
    if (getRoleDiv().equals(PermissionType.SITE.getValue())) {
      this.setShopCode(DIContainer.getWebshopConfig().getSiteShopCode());
    } else {
      this.setShopCode(reqparam.get("shopCode"));
    }
    this.setUserName(reqparam.get("userName"));
    this.setMemo(reqparam.get("memo"));
    this.setAdministrator(reqparam.get("administrator"));
    this.setPassword(reqparam.get("password"));
    this.setPasswordConfirm(reqparam.get("passwordConfirm"));
    String loginLockFlg = reqparam.get("loginLock");
    if (loginLockFlg.equals(LoginLockedFlg.LOCKED.getValue())) {
      loginLockFlg = LoginLockedFlg.LOCKED.getValue();
    } else {
      loginLockFlg = LoginLockedFlg.UNLOCKED.getValue();
    }
    String errorCount = reqparam.get("loginErrorCount");
    if (StringUtil.hasValue(errorCount)) {
      this.setLoginErrorCount(errorCount);
    } else {
      this.setLoginErrorCount("0");
    }
    this.setLoginLock(loginLockFlg);
    this.setUserLoginId(reqparam.get("userLoginId"));

    // 管理者
    if (this.getAdministrator().equals(AdministrationUserType.ADMINISTRATOR.getValue())) {
      AccountEditPermission manager = null;
      if (this.getRoleDiv().equals(PermissionType.SITE.getValue())) {
        manager = this.sitePermission.getManager();
      } else if (this.getRoleDiv().equals(PermissionType.SHOP.getValue())) {
        manager = this.shopPermission.getManager();
      } else {
        manager = new AccountEditPermission();
      }
      manager.setPermissionFlg(manager.getPermissionCode());
    } else {
      setOperatorPermissions(this.getShopPermission(), reqparam.getAll("permissionShop"));
      setOperatorPermissions(this.getSitePermission(), reqparam.getAll("permissionSite"));
    }

  }

  /**
   * premissionsに一致する権限コードがsetにあれば 権限有状態に設定する。<BR>
   * 無ければ権限無し状態に設定する。<BR>
   * 
   * @param set
   * @param permissions
   */
  private void setOperatorPermissions(PermissionSet set, String[] permissions) {
    // String[]のリスト化
    List<String> permissionList = Arrays.asList(permissions);
    for (MenuGroup group : MenuGroup.values()) {
      List<AccountEditPermission> permissionSet = set.getPermissionMap().get(group);
      for (AccountEditPermission p : permissionSet) {
        if (permissionList.contains(p.getPermissionCode())) {
          p.setPermissionFlg(p.getPermissionCode());
        } else {
          p.setPermissionFlg("");
        }
      }
    }
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1050920";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.AccountEditBean.0");
  }

  /**
   * U1050920:管理ユーザマスタ明細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class AccountEditPermission implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String permissionCode;

    private String permissionName;

    @Required
    private String permissionFlg;

    /**
     * permissionCodeを取得します。
     * 
     * @return permissionCode
     */
    public String getPermissionCode() {
      return permissionCode;
    }

    /**
     * permissionCodeを設定します。
     * 
     * @param permissionCode
     *          permissionCode
     */
    public void setPermissionCode(String permissionCode) {
      this.permissionCode = permissionCode;
    }

    /**
     * permissionFlgを取得します。
     * 
     * @return permissionFlg
     */
    public String getPermissionFlg() {
      return permissionFlg;
    }

    /**
     * permissionFlgを設定します。
     * 
     * @param permissionFlg
     *          permissionFlg
     */
    public void setPermissionFlg(String permissionFlg) {
      this.permissionFlg = permissionFlg;
    }

    /**
     * permissionNameを取得します。
     * 
     * @return permissionName
     */
    public String getPermissionName() {
      return permissionName;
    }

    /**
     * permissionNameを設定します。
     * 
     * @param permissionName
     *          permissionName
     */
    public void setPermissionName(String permissionName) {
      this.permissionName = permissionName;
    }

  }

  /**
   * U1050920:管理ユーザマスタ明細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class PermissionSet implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private AccountEditPermission manager = new AccountEditPermission();

    private HashMap<MenuGroup, List<AccountEditPermission>> permissionMap = new HashMap<MenuGroup, List<AccountEditPermission>>();

    /**
     * managerを取得します。
     * 
     * @return manager
     */
    public AccountEditPermission getManager() {
      return manager;
    }

    /**
     * managerを設定します。
     * 
     * @param manager
     *          manager
     */
    public void setManager(AccountEditPermission manager) {
      this.manager = manager;
    }

    /**
     * permissionMapを取得します。
     * 
     * @return permissionMap
     */
    public Map<MenuGroup, List<AccountEditPermission>> getPermissionMap() {
      return permissionMap;
    }

    /**
     * permissionMapを設定します。
     * 
     * @param permissionMap
     *          permissionMap
     */
    public void setPermissionMap(Map<MenuGroup, List<AccountEditPermission>> permissionMap) {
      CollectionUtil.copyAll(this.permissionMap, permissionMap);
    }

  }

  /**
   * administratorを取得します。
   * 
   * @return administrator
   */
  public String getAdministrator() {
    return administrator;
  }

  /**
   * administratorを設定します。
   * 
   * @param administrator
   *          administrator
   */
  public void setAdministrator(String administrator) {
    this.administrator = administrator;
  }

  /**
   * loginErrorCountを取得します。
   * 
   * @return loginErrorCount
   */
  public String getLoginErrorCount() {
    return loginErrorCount;
  }

  /**
   * loginErrorCountを設定します。
   * 
   * @param loginErrorCount
   *          loginErrorCount
   */
  public void setLoginErrorCount(String loginErrorCount) {
    this.loginErrorCount = loginErrorCount;
  }

  /**
   * loginLockを取得します。
   * 
   * @return loginLock
   */
  public String getLoginLock() {
    return loginLock;
  }

  /**
   * loginLockを設定します。
   * 
   * @param loginLock
   *          loginLock
   */
  public void setLoginLock(String loginLock) {
    this.loginLock = loginLock;
  }

  /**
   * memoを取得します。
   * 
   * @return memo
   */
  public String getMemo() {
    return memo;
  }

  /**
   * memoを設定します。
   * 
   * @param memo
   *          memo
   */
  public void setMemo(String memo) {
    this.memo = memo;
  }

  /**
   * passwordを取得します。
   * 
   * @return password
   */
  public String getPassword() {
    return password;
  }

  /**
   * passwordを設定します。
   * 
   * @param password
   *          password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * passwordConfirmを取得します。
   * 
   * @return passwordConfirm
   */
  public String getPasswordConfirm() {
    return passwordConfirm;
  }

  /**
   * passwordConfirmを設定します。
   * 
   * @param passwordConfirm
   *          passwordConfirm
   */
  public void setPasswordConfirm(String passwordConfirm) {
    this.passwordConfirm = passwordConfirm;
  }

  /**
   * roleDivを取得します。
   * 
   * @return roleDiv
   */
  public String getRoleDiv() {
    return roleDiv;
  }

  /**
   * roleDivを設定します。
   * 
   * @param roleDiv
   *          roleDiv
   */
  public void setRoleDiv(String roleDiv) {
    this.roleDiv = roleDiv;
  }

  /**
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * shopListを取得します。
   * 
   * @return shopList
   */
  public List<CodeAttribute> getShopList() {
    return shopList;
  }

  /**
   * shopListを設定します。
   * 
   * @param shopList
   *          shopList
   */
  public void setShopList(List<CodeAttribute> shopList) {
    this.shopList = shopList;
  }

  /**
   * shopPermissionを取得します。
   * 
   * @return shopPermission
   */
  public PermissionSet getShopPermission() {
    return shopPermission;
  }

  /**
   * shopPermissionを設定します。
   * 
   * @param shopPermission
   *          shopPermission
   */
  public void setShopPermission(PermissionSet shopPermission) {
    this.shopPermission = shopPermission;
  }

  /**
   * sitePermissionを取得します。
   * 
   * @return sitePermission
   */
  public PermissionSet getSitePermission() {
    return sitePermission;
  }

  /**
   * sitePermissionを設定します。
   * 
   * @param sitePermission
   *          sitePermission
   */
  public void setSitePermission(PermissionSet sitePermission) {
    this.sitePermission = sitePermission;
  }

  /**
   * tableHeaderを取得します。
   * 
   * @return tableHeader
   */
  public String getTableHeader() {
    return tableHeader;
  }

  /**
   * tableHeaderを設定します。
   * 
   * @param tableHeader
   *          tableHeader
   */
  public void setTableHeader(String tableHeader) {
    this.tableHeader = tableHeader;
  }

  /**
   * userCodeを取得します。
   * 
   * @return userCode
   */
  public String getUserCode() {
    return userCode;
  }

  /**
   * userCodeを設定します。
   * 
   * @param userCode
   *          userCode
   */
  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }

  /**
   * userLoginIdを取得します。
   * 
   * @return userLoginId
   */
  public String getUserLoginId() {
    return userLoginId;
  }

  /**
   * userLoginIdを設定します。
   * 
   * @param userLoginId
   *          userLoginId
   */
  public void setUserLoginId(String userLoginId) {
    this.userLoginId = userLoginId;
  }

  /**
   * userNameを取得します。
   * 
   * @return userName
   */
  public String getUserName() {
    return userName;
  }

  /**
   * userNameを設定します。
   * 
   * @param userName
   *          userName
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * updateDatetimeを取得します。
   * 
   * @return updateDatetime
   */
  public Date getUpdateDatetime() {
    return DateUtil.immutableCopy(updateDatetime);
  }

  /**
   * updateDatetimeを設定します。
   * 
   * @param updateDatetime
   *          updateDatetime
   */
  public void setUpdateDatetime(Date updateDatetime) {
    this.updateDatetime = DateUtil.immutableCopy(updateDatetime);
  }

  /**
   * administratorModeを取得します。
   * 
   * @return administratorMode
   */
  public String getAdministratorMode() {
    return administratorMode;
  }

  /**
   * administratorModeを設定します。
   * 
   * @param administratorMode
   *          administratorMode
   */
  public void setAdministratorMode(String administratorMode) {
    this.administratorMode = administratorMode;
  }

  /**
   * processを取得します。
   * 
   * @return process
   */
  public String getProcess() {
    return process;
  }

  /**
   * processを設定します。
   * 
   * @param process
   *          process
   */
  public void setProcess(String process) {
    this.process = process;
  }

  /**
   * administrationUserTypeListを取得します。
   * 
   * @return administrationUserTypeList
   */
  public AdministrationUserType[] getAdministrationUserTypeList() {
    return ArrayUtil.immutableCopy(administrationUserTypeList);
  }

  /**
   * menuGroupListを取得します。
   * 
   * @return menuGroupList
   */
  public MenuGroup[] getMenuGroupList() {
    return ArrayUtil.immutableCopy(menuGroupList);
  }

  /**
   * permissionTypeListを取得します。
   * 
   * @return permissionTypeList
   */
  public PermissionType[] getPermissionTypeList() {
    return ArrayUtil.immutableCopy(permissionTypeList);
  }

  /**
   * siteNameを取得します。
   * 
   * @return siteName
   */
  public String getSiteName() {
    return siteName;
  }

  /**
   * siteNameを設定します。
   * 
   * @param siteName
   *          siteName
   */
  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }
}
