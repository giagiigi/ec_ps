package jp.co.sint.webshop.web.action.back.shop;

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
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodListBean;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodListBean.PaymentCommissionDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1050510:支払方法一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PaymentmethodListUpdateCommissionAction extends PaymentMethodListBaseAction {

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
    String rowId = getRequestRowid();

    PaymentmethodListBean bean = getBean();

    for (PaymentCommissionDetail commissionDetail : bean.getCommissionList()) {
      if (commissionDetail.getOrmRowid().equals(rowId)) {
        return validateBean(commissionDetail);
      }
    }
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    String rowId = getRequestRowid();

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    PaymentmethodListBean bean = getBean();

    Commission commission = new Commission();

    String shopCode = "";
    if (getLoginInfo().isSite()) {
      shopCode = bean.getShopCode();
    } else {
      shopCode = getLoginInfo().getShopCode();
    }

    for (PaymentCommissionDetail commissionDetail : bean.getCommissionList()) {
      if (commissionDetail.getOrmRowid().equals(rowId)) {
        commission.setShopCode(shopCode);
        commission.setPaymentMethodNo(NumUtil.toLong(bean.getPaymentMethodNo()));
        commission.setOrmRowid(Long.valueOf(commissionDetail.getOrmRowid()));
        commission.setPaymentPriceThreshold(NumUtil.parse(commissionDetail.getPriceTo()));
        commission.setPaymentCommission(NumUtil.parse(commissionDetail.getPaymentCommission()));
        commission.setUpdatedDatetime(commissionDetail.getUpdatedDatetime());
      }
    }

    ServiceResult result = service.updateCommission(commission);

    // 支払方法リストを再取得
    bean.setPaymentMethodList(getPaymentMethodDetailList(getLoginInfo().getShopCode()));

    if (result.hasError()) {
      setRequestBean(bean);
      setNextUrl(null);
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR));
        }
      }
      Logger logger = Logger.getLogger(this.getClass());
      logger.warn(result.toString());
      return BackActionResult.SERVICE_ERROR;
    } else {
      setNextUrl("/app/shop/paymentmethod_list/payment/" + shopCode + "/" + bean.getPaymentMethodNo() + "/"
          + WebConstantCode.COMPLETE_UPDATE + "_commission");
      setRequestBean(bean);
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * URLパラメータから行IDを取得
   * 
   * @return rowId
   */
  private String getRequestRowid() {
    String rowId = "";
    if (getRequestParameter().getPathArgs().length > 0) {
      rowId = getRequestParameter().getPathArgs()[0];
    }

    return rowId;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.PaymentmethodListUpdateCommissionAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105051007";
  }

}
