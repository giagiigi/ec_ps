package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.MenuGroup;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.AdministrationUserType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.PermissionType;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.data.dto.UserAccount;
import jp.co.sint.webshop.data.dto.UserPermission;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.DigitValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.AccountEditBean;
import jp.co.sint.webshop.web.bean.back.shop.AccountEditBean.AccountEditPermission;
import jp.co.sint.webshop.web.bean.back.shop.AccountEditBean.PermissionSet;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050920:管理ユーザマスタ明細のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AccountEditInitAction extends WebBackAction<AccountEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;

    if (getLoginInfo().isAdmin()) {
      if (getLoginInfo().isShop()) {
        String userShopCode = getUpdateUserShopCode();
        if (userShopCode.length() == 0) {
          auth = true;
        } else if (userShopCode.equals(getLoginInfo().getShopCode())) {
          auth = true;
        } else {
          auth = false;
        }
      } else {
        auth = getLoginInfo().isSite();
      }
    } else {
      // オペレータの場合自動で自ユーザ情報を表示
      auth = true;
    }
    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String updateCode = getUpdateUserCode();
    if (StringUtil.hasValue(updateCode)) {
      DigitValidator v = new DigitValidator();
      if (v.isValid(updateCode)) {
        return true;
      } else {
        addErrorMessage(WebMessage.get(ShopErrorMessage.CODE_FAILED,
            Messages.getString("web.action.back.shop.AccountEditInitAction.0")));
        return false;
      }
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    AccountEditBean bean = new AccountEditBean();
    bean.setDisplayButton(true);

    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    if (getLoginInfo().isSite() && getLoginInfo().isAdmin()) {
      bean.setShopList(ServiceLocator.getUtilService(getLoginInfo()).getShopNames(false, false));
      bean.setSiteName(ServiceLocator.getSiteManagementService(getLoginInfo()).getSite().getShopName());
    } else {
      List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();
      String shopCode = getLoginInfo().getShopCode();
      Shop userShop = shopService.getShop(shopCode);
      shopList.add(new NameValue(userShop.getShopName(), shopCode));
    }

    List<String> userPermissionCodeList = new ArrayList<String>();

    // 新規登録の場合
    if (getLoginInfo().isAdmin() && getUpdateUserCode().length() <= 0) {
      if (getLoginInfo().isSite()) {
        bean.setRoleDiv(PermissionType.SITE.getValue());
        bean.setShopCode(getConfig().getSiteShopCode());
      } else {
        bean.setShopCode(getLoginInfo().getShopCode());
        bean.setRoleDiv(PermissionType.SHOP.getValue());
      }
      bean.setAdministrator(AdministrationUserType.OPERATOR.getValue());
      bean.setUpdateMode(WebConstantCode.DISPLAY_EDIT);
      bean.setConfirmMode(WebConstantCode.DISPLAY_EDIT);
      bean.setAdministratorMode(WebConstantCode.DISPLAY_EDIT);
      bean.setProcess("insert");
      bean.setTableHeader(Messages.getString("web.action.back.shop.AccountEditInitAction.1"));
    } else {
      // 更新時の情報取得処理
      String userCode = "";
      if (getLoginInfo().isAdmin()) {
        userCode = getUpdateUserCode();
      } else {
        userCode = getLoginInfo().getUserCode();
      }

      SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
      UserAccount account = service.getUserAccount(Long.parseLong(userCode));

      if (account == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.shop.AccountEditInitAction.2")));
        return BackActionResult.RESULT_SUCCESS;
      }

      bean.setShopCode(account.getShopCode());
      bean.setUserName(account.getUserName());
      bean.setUserCode(Long.toString(account.getUserCode()));
      bean.setUserLoginId(account.getUserLoginId());
      bean.setLoginLock(Long.toString(account.getLoginLockedFlg()));
      bean.setLoginErrorCount(Long.toString(account.getLoginErrorCount()));
      bean.setMemo(account.getMemo());
      bean.setUpdateDatetime(account.getUpdatedDatetime());

      List<UserPermission> userPermission = service.getUserPermissionList(account.getUserCode());
      for (UserPermission p : userPermission) {

        if (bean.getRoleDiv() == null) {
          Permission enumPermission = getPermissionByCode(p.getPermissionCode());
          if (getConfig().isOne()) {
            bean.setRoleDiv(PermissionType.SITE.getValue());
          } else if (enumPermission != null) {
            bean.setRoleDiv(enumPermission.getPermissionType().getValue());
          } else {
            bean.setRoleDiv(null);
          }
        }

        if (p.getPermissionCode().equals(Permission.SITE_MANAGER.getValue())) {
          bean.setAdministrator(AdministrationUserType.ADMINISTRATOR.getValue());
          break;
        } else if (p.getPermissionCode().equals(Permission.SHOP_MANAGER.getValue())) {
          bean.setAdministrator(AdministrationUserType.ADMINISTRATOR.getValue());
          break;
        } else {
          userPermissionCodeList.add(p.getPermissionCode());
          bean.setAdministrator(AdministrationUserType.OPERATOR.getValue());
        }
      }
      bean.setUpdateMode(WebConstantCode.DISPLAY_HIDDEN);
      bean.setConfirmMode(WebConstantCode.DISPLAY_EDIT);
      bean.setProcess("update");
      bean.setTableHeader(Messages.getString("web.action.back.shop.AccountEditInitAction.3"));
      String siteUserCode = DIContainer.getWebshopConfig().getSiteUserCode();
      if (getUpdateUserCode().equals(siteUserCode)) {
        // 真管理ユーザの場合
        bean.setAdministratorMode(WebConstantCode.DISPLAY_HIDDEN);
        bean.setSiteUserFlg(true);
        if (getLoginInfo().getUserCode().equals(siteUserCode)) {
          // ログイン者が新管理ユーザであればボタン表示
          bean.setDisplayButton(true);
        } else {
          // ログイン者が新管理ユーザでなければボタン非表示
          bean.setDisplayButton(false);
        }
      } else if (getLoginInfo().isAdmin()) {
        bean.setAdministratorMode(WebConstantCode.DISPLAY_EDIT);
        bean.setDisplayButton(true);
      } else {
        bean.setAdministratorMode(WebConstantCode.DISPLAY_HIDDEN);
        bean.setDisplayButton(true);
      }
    }
    bean.setDisplayConfirmButton(true);
    createPermissionList(bean, userPermissionCodeList);

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Permissionの列挙から画面表示用の権限情報を生成する
   * 
   * @param bean
   * @param userPermissionCodeList
   *          ユーザ権限のコード一覧
   */
  private void createPermissionList(AccountEditBean bean, List<String> userPermissionCodeList) {
    PermissionSet shopPermission = new PermissionSet();
    PermissionSet sitePermission = new PermissionSet();
    for (MenuGroup g : MenuGroup.values()) {
      shopPermission.getPermissionMap().put(g, new ArrayList<AccountEditPermission>());
      sitePermission.getPermissionMap().put(g, new ArrayList<AccountEditPermission>());
    }

    Permission[] permissionList = Permission.getPermissionSet(getConfig().getOperatingMode());
    for (Permission p : permissionList) {
      PermissionSet tempSet = null;

      if (getConfig().getOperatingMode() == OperatingMode.ONE) {
        // 一店舗版は全てサイトとして登録
        tempSet = sitePermission;
      } else if (p.getPermissionType() == PermissionType.SITE) {
        tempSet = sitePermission;
      } else if (p.getPermissionType() == PermissionType.SHOP) {
        tempSet = shopPermission;
      } else {
        tempSet = new PermissionSet();
      }

      AccountEditPermission userPermission = new AccountEditPermission();
      userPermission.setPermissionCode(p.getCode());
      userPermission.setPermissionName(p.getName());
      if (userPermissionCodeList.contains(p.getCode())) {
        userPermission.setPermissionFlg(p.getCode());
      } else {
        userPermission.setPermissionFlg("");
      }
      if (p.getMenuGroup() == null) {
        tempSet.setManager(userPermission);
      } else {
        tempSet.getPermissionMap().get(p.getMenuGroup()).add(userPermission);
      }
    }
    bean.setShopPermission(shopPermission);
    bean.setSitePermission(sitePermission);
  }

  /**
   * 権限コードをが一致するPermissionを返します。
   * 
   * @return 権限
   */
  private Permission getPermissionByCode(String code) {
    for (Permission p : Permission.values()) {
      if (p.getCode().equals(code)) {
        return p;
      }
    }
    return null;
  }

  private String getUpdateUserCode() {
    return getPathInfo(0);

  }

  private String getUpdateUserShopCode() {
    String shopCode = "";
    String updateUserCode = getUpdateUserCode();
    DigitValidator v = new DigitValidator();
    if (StringUtil.hasValue(updateUserCode) && v.isValid(updateUserCode)) {
      SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
      UserAccount account = service.getUserAccount(Long.parseLong(updateUserCode));
      if (account == null) {
        shopCode = "";
      } else {
        shopCode = account.getShopCode();
      }
    } else {
      shopCode = "";
    }
    return shopCode;
  }

  private String getPathInfo(int index) {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > index) {
      return tmpArgs[index];
    }
    return "";
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    if (getPathInfo(1).equals(WebConstantCode.COMPLETE_INSERT)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
          Messages.getString("web.action.back.shop.AccountEditInitAction.0")));
    }

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.AccountEditInitAction.4");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105092003";
  }

}
