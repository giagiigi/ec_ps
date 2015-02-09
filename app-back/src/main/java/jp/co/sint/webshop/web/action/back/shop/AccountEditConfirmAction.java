package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.MenuGroup;
import jp.co.sint.webshop.data.domain.AdministrationUserType;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.PasswordPolicy;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.AccountEditBean;
import jp.co.sint.webshop.web.bean.back.shop.AccountEditBean.AccountEditPermission;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.message.back.customer.CustomerErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050920:管理ユーザマスタ明細のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AccountEditConfirmAction extends WebBackAction<AccountEditBean> {

  @Override
  public void init() {
    if (getLoginInfo().isShop()) {
      getBean().setShopCode(getLoginInfo().getShopCode());
    }
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;
    if (getLoginInfo().isAdmin()) {
      auth = true;
    } else {
      if (getBean().getUserCode().equals(getLoginInfo().getUserCode())) {
        auth = true;
      } else {
        auth = false;
      }
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
    boolean valid = true;
    AccountEditBean bean = getBean();
    valid = validateBean(bean);

    String process = bean.getProcess();

    if (StringUtil.hasValueAnyOf(bean.getPassword(), bean.getPasswordConfirm())) {
      if (StringUtil.hasValueAllOf(bean.getPassword(), bean.getPasswordConfirm())) {
        // パスワードポリシーエラー
        PasswordPolicy policy = DIContainer.get("BackPasswordPolicy");
        if (!policy.isValidPassword(bean.getPassword())) {
          addErrorMessage(WebMessage.get(CustomerErrorMessage.PASSWORD_POLICY_ERROR));
          valid = false;
        }
        // 不一致エラー
        if (StringUtil.hasValue(bean.getPasswordConfirm()) && !bean.getPassword().equals(bean.getPasswordConfirm())) {
          addErrorMessage(WebMessage.get(ValidationMessage.NOT_SAME_PASSWORD));
          valid = false;
        }
      } else {
        if (StringUtil.isNullOrEmpty(bean.getPassword())) {
          addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR,
              Messages.getString("web.action.back.shop.AccountEditConfirmAction.0")));
          valid = false;
        }
        if (StringUtil.isNullOrEmpty(bean.getPasswordConfirm())) {
          addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR,
              Messages.getString("web.action.back.shop.AccountEditConfirmAction.1")));
          valid = false;
        }
      }
    } else if (process.equals("insert")) {
      if (StringUtil.isNullOrEmpty(bean.getPassword())) {
        addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR,
            Messages.getString("web.action.back.shop.AccountEditConfirmAction.0")));
        valid = false;
      }
      if (StringUtil.isNullOrEmpty(bean.getPasswordConfirm())) {
        addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR,
            Messages.getString("web.action.back.shop.AccountEditConfirmAction.1")));
        valid = false;
      }
    }

    if (StringUtil.hasValue(process)) {
      if (process.equals("insert") && valid) {
        SiteManagementService siteManagementService = ServiceLocator.getSiteManagementService(getLoginInfo());
        String shopCode = "";
        if (getLoginInfo().isSite()) {
          shopCode = getBean().getShopCode();
        } else {
          shopCode = getLoginInfo().getShopCode();
        }

        if (siteManagementService.getUserAccountByLoginId(shopCode, getBean().getUserLoginId()) != null) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.shop.AccountEditConfirmAction.2")));
          valid = false;
        }
      }
    } else {
      valid = false;
    }

    // 権限にオペレータを設定していて、ひとつも権限がない場合は必須入力エラーにする
    if (AdministrationUserType.OPERATOR.getValue().equals(bean.getAdministrator())) {
      boolean hasPermission = false;
      List<AccountEditPermission> permissionSet = new ArrayList<AccountEditPermission>();
      for (MenuGroup group : MenuGroup.values()) {
        permissionSet.addAll(bean.getSitePermission().getPermissionMap().get(group));
      }
      for (MenuGroup group : MenuGroup.values()) {
        permissionSet.addAll(bean.getShopPermission().getPermissionMap().get(group));
      }
      for (AccountEditPermission p : permissionSet) {
        if (StringUtil.hasValue(p.getPermissionFlg())) {
          hasPermission = true;
        }
      }
      if (!hasPermission) {
        valid = false;
        addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
            Messages.getString("web.action.back.shop.AccountEditConfirmAction.3")));
      }
    }

    return valid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    AccountEditBean bean = getBean();

    bean.setDisplayConfirmButton(false);
    bean.setAdministratorMode(WebConstantCode.DISPLAY_HIDDEN);
    bean.setUpdateMode(WebConstantCode.DISPLAY_HIDDEN);
    bean.setConfirmMode(WebConstantCode.DISPLAY_HIDDEN);

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.AccountEditConfirmAction.4");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105092002";
  }

}
