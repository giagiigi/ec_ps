package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.MenuGroup;
import jp.co.sint.webshop.data.dto.UserAccount;
import jp.co.sint.webshop.data.dto.UserPermission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.DigitValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.shop.AccountEditBean;
import jp.co.sint.webshop.web.bean.back.shop.AccountEditBean.AccountEditPermission;
import jp.co.sint.webshop.web.bean.back.shop.AccountEditBean.PermissionSet;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050920:管理ユーザマスタ明細のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class AccountEditRegisterBaseAction extends AccountEditConfirmAction {

  protected static final int REGISTER_TYPE_INSERT = 1;

  protected static final int REGISTER_TYPE_UPDATE = 2;

  protected WebActionResult baseCallService(int type) {
    AccountEditBean bean = getBean();
    UserAccount account = copyUserData(bean);

    List<UserPermission> permissionList = new ArrayList<UserPermission>();
    PermissionSet sitePermission = bean.getSitePermission();
    PermissionSet shopPermission = bean.getShopPermission();

    createOperatorPermission(permissionList, sitePermission);
    createOperatorPermission(permissionList, shopPermission);

    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());

    ServiceResult result = null;
    if (type == REGISTER_TYPE_INSERT) {
      result = service.insertUserAccount(account, permissionList);
    } else if (type == REGISTER_TYPE_UPDATE) {
      result = service.updateUserAccount(account, permissionList);
    } else {
      return BackActionResult.SERVICE_ERROR;
    }
    if (result.hasError()) {
      for (ServiceErrorContent content : result.getServiceErrorList()) {
        if (content == CommonServiceErrorContent.NO_DATA_ERROR) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }

    // ユーザコード取得
    SiteManagementService siteManagementService = ServiceLocator.getSiteManagementService(getLoginInfo());
    UserAccount registerUser = siteManagementService.getUserAccountByLoginId(account.getShopCode(), account.getUserLoginId());

    if (registerUser == null) {
      return BackActionResult.SERVICE_ERROR;
    } else {
      setNextUrl("/app/shop/account_edit/init/" + registerUser.getUserCode() + "/" + WebConstantCode.COMPLETE_INSERT);
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  private void createOperatorPermission(List<UserPermission> permissionList, PermissionSet permissionSet) {
    if (StringUtil.hasValue(permissionSet.getManager().getPermissionFlg())) {
      UserPermission p = new UserPermission();
      p.setPermissionCode(permissionSet.getManager().getPermissionCode());
      permissionList.add(p);
    }

    for (MenuGroup g : MenuGroup.values()) {
      List<AccountEditPermission> shopOperatorPermission = permissionSet.getPermissionMap().get(g);
      for (AccountEditPermission p : shopOperatorPermission) {
        if (StringUtil.hasValue(p.getPermissionFlg())) {
          UserPermission dtoP = new UserPermission();
          dtoP.setPermissionCode(p.getPermissionCode());
          permissionList.add(dtoP);
        }
      }
    }

  }

  private UserAccount copyUserData(AccountEditBean bean) {
    UserAccount account = new UserAccount();
    DigitValidator v = new DigitValidator();
    String userCode = bean.getUserCode();
    if (StringUtil.hasValue(userCode) && v.isValid(userCode)) {
      account.setUserCode(Long.parseLong(userCode));
    }

    String shopCode = "";
    if (getLoginInfo().isSite()) {
      shopCode = bean.getShopCode();
    } else {
      shopCode = getLoginInfo().getShopCode();
    }
    account.setShopCode(shopCode);
    account.setUserLoginId(bean.getUserLoginId());
    account.setPassword(bean.getPassword());
    account.setUserName(bean.getUserName());
    account.setLoginErrorCount(Long.parseLong(bean.getLoginErrorCount()));
    account.setLoginLockedFlg(Long.parseLong(bean.getLoginLock()));
    account.setMemo(bean.getMemo());
    account.setUpdatedDatetime(bean.getUpdateDatetime());

    return account;
  }
}
