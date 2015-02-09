package jp.co.sint.webshop.web.action.back.shop;

import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.Commission;
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
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1050510:支払方法一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PaymentmethodListRegisterCommissionAction extends WebBackAction<PaymentmethodListBean> {

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
    return validateBean(getBean().getCommissionRegister());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    PaymentmethodListBean bean = getBean();

    String shopCode = "";
    if (getLoginInfo().isSite()) {
      shopCode = bean.getShopCode();
    } else {
      shopCode = getLoginInfo().getShopCode();
    }

    Commission commission = new Commission();
    commission.setShopCode(shopCode);
    commission.setPaymentMethodNo(NumUtil.toLong(bean.getPaymentMethodNo()));
    commission.setPaymentPriceThreshold(NumUtil.parse(bean.getCommissionRegister().getPriceTo()));
    commission.setPaymentCommission(NumUtil.parse(bean.getCommissionRegister().getPaymentCommission()));

    // 重複チェック
    List<Commission> commissionList = service.getCommissionList(commission.getShopCode(), commission.getPaymentMethodNo());
    for (Commission orgCommission : commissionList) {
      if (orgCommission.getPaymentPriceThreshold().equals(commission.getPaymentPriceThreshold())) {
        addErrorMessage(WebMessage.get(ShopErrorMessage.REGISTERED_COMMISSION));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }
    }

    ServiceResult result = service.insertCommission(commission);

    if (result.hasError()) {
      setRequestBean(bean);
      setNextUrl(null);
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_DEFAULT_ERROR));
        }
      }
      Logger logger = Logger.getLogger(this.getClass());
      logger.warn(result.toString());
      return BackActionResult.SERVICE_ERROR;
    } else {
      setNextUrl("/app/shop/paymentmethod_list/payment/" + shopCode + "/" + bean.getPaymentMethodNo() + "/"
          + WebConstantCode.COMPLETE_INSERT + "_commission");
      setRequestBean(bean);
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.PaymentmethodListRegisterCommissionAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105051006";
  }

}
