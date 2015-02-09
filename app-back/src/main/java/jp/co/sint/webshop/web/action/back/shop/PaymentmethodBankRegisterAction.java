package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.Bank;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodBankBean;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodBankBean.BankDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050530:金融機関設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PaymentmethodBankRegisterAction extends WebBackAction<PaymentmethodBankBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;

    if (getConfig().getOperatingMode().equals(OperatingMode.MALL)
        && Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (getConfig().getOperatingMode().equals(OperatingMode.MALL)
        && Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo())
        && getLoginInfo().getShopCode().equals(getConfig().getSiteShopCode())) {
      authorization = true;
    }

    if (getConfig().getOperatingMode().equals(OperatingMode.SHOP)
        && Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (getConfig().getOperatingMode().equals(OperatingMode.SHOP)
        && Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)
        && Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)
        && Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo())
        && getLoginInfo().getShopCode().equals(getConfig().getSiteShopCode())) {
      authorization = true;
    }

    return authorization;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    BankDetail bean = getBean().getRegisterBank();
    return validateBean(bean);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    PaymentmethodBankBean bean = getBean();
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    Bank registerBank = new Bank();

    registerBank.setShopCode(bean.getShopCode());
    registerBank.setPaymentMethodNo(NumUtil.toLong(bean.getPaymentMethodNo()));
    registerBank.setBankCode(bean.getRegisterBank().getBankCode());
    registerBank.setBankName(bean.getRegisterBank().getBankName());
    registerBank.setBankKana(bean.getRegisterBank().getBankKana());
    registerBank.setBankBranchCode(bean.getRegisterBank().getBankBranchCode());
    registerBank.setBankBranchName(bean.getRegisterBank().getBankBranchName());
    registerBank.setBankBranchNameKana(bean.getRegisterBank().getBankBranchNameKana());
    registerBank.setAccountType(Long.valueOf(bean.getRegisterBank().getAccountType()));
    registerBank.setAccountNo(bean.getRegisterBank().getAccountNo());
    registerBank.setAccountName(bean.getRegisterBank().getAccountName());
    registerBank.setUpdatedDatetime(bean.getRegisterBank().getUpdatedDatetime());

    ServiceResult result = null;

    if (bean.getProcessMode().equals(WebConstantCode.PROCESS_INSERT)) {
      result = service.insertBank(registerBank);
    } else {
      result = service.updateBank(registerBank);
    }

    if (result.hasError()) {
      setRequestBean(bean);
      setNextUrl(null);
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.shop.PaymentmethodBankRegisterAction.0")));
          return BackActionResult.RESULT_SUCCESS;
        }
        if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.shop.PaymentmethodBankRegisterAction.0")));
          return BackActionResult.RESULT_SUCCESS;
        }
        if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/shop/paymentmethod_bank/init/" + bean.getShopCode()
        + "/" + bean.getPaymentMethodNo() + "/register");

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.PaymentmethodBankRegisterAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105053003";
  }

}
