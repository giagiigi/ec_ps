package jp.co.sint.webshop.web.action.back.shop;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.Commission;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodListBean;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodListBean.PaymentCommissionDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1050510:支払方法一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PaymentmethodListDeleteCommissionAction extends WebBackAction<PaymentmethodListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;
    if (getConfig().getOperatingMode().equals(OperatingMode.MALL)
        && Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (getConfig().getOperatingMode().equals(OperatingMode.MALL)
        && Permission.SHOP_MANAGEMENT_DELETE_SHOP.isGranted(getLoginInfo())
        && getLoginInfo().getShopCode().equals(getConfig().getSiteShopCode())) {
      authorization = true;
    }
    if (getConfig().getOperatingMode().equals(OperatingMode.SHOP)
        && Permission.SHOP_MANAGEMENT_DELETE_SHOP.isGranted(getLoginInfo())) {
      authorization = true;
    }
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)
        && Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)
        && Permission.SHOP_MANAGEMENT_DELETE_SHOP.isGranted(getLoginInfo())
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
    return true;
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

    //String length = WebUtil.buildMaxlength(PaymentCommissionDetail.class, "priceTo", null);

    // 購入金額(TO)の最大値を取得
//    String priceTo = "";
//    for (Long i = 0L; i < NumUtil.toLong(length); i++) {
//      priceTo += "9";
//    }
    Precision precision = BeanUtil.getAnnotation(PaymentCommissionDetail.class, "priceTo", Precision.class);
    BigDecimal maximum = NumUtil.getActualMaximum(precision.precision(), precision.scale());
    //commission.setPaymentPriceThreshold(maximum);

    String shopCode = "";
    if (getLoginInfo().isSite()) {
      shopCode = bean.getShopCode();
    } else {
      shopCode = getLoginInfo().getShopCode();
    }

    for (PaymentCommissionDetail commissionDetail : bean.getCommissionList()) {

      if (commissionDetail.getOrmRowid().equals(rowId)) {
        if (commissionDetail.getPriceTo().equals(maximum.toString())) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.DELETE_MAX_COMMISSION_ERROR));
          return BackActionResult.RESULT_SUCCESS;
        }
        commission.setShopCode(shopCode);
        commission.setPaymentMethodNo(NumUtil.toLong(bean.getPaymentMethodNo()));
        commission.setOrmRowid(Long.valueOf(commissionDetail.getOrmRowid()));
        commission.setPaymentPriceThreshold(NumUtil.parse(commissionDetail.getPriceTo()));
      }
    }

    ServiceResult result = service.deleteCommission(commission.getShopCode(), commission.getPaymentMethodNo(), commission
        .getPaymentPriceThreshold());

    if (result.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.warn(result.toString());
      return BackActionResult.SERVICE_ERROR;
    } else {
      setNextUrl("/app/shop/paymentmethod_list/payment/" + shopCode + "/" + bean.getPaymentMethodNo() + "/"
          + WebConstantCode.COMPLETE_DELETE + "_commission");
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
    return Messages.getString("web.action.back.shop.PaymentmethodListDeleteCommissionAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105051001";
  }

}
