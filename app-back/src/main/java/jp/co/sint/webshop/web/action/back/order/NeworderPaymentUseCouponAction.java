package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.CouponFunctionEnabledFlg;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderPaymentBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderPaymentBean.CouponBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderPaymentBean.UsePointBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020150:新規受注(決済方法指定)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderPaymentUseCouponAction extends NeworderPaymentBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    NeworderPaymentBean bean = getBean();
    for (CouponBean couponBean : bean.getCouponList()) {
      String selectID = "";
      for (String str : bean.getUseCouponList()) {
        if (couponBean.getCustomerCouponId().equals(str)) {
          selectID = str;
        }
      }
      if (StringUtil.isNullOrEmpty(selectID)) {
        couponBean.setSelectCouponId("");
      } else {
        couponBean.setSelectCouponId(selectID);
      }
    }

    SiteManagementService siteService = ServiceLocator.getSiteManagementService(getLoginInfo());
    Long maxCoupon = siteService.getCouponRule().getCouponAmount();
    if (getBean().getUseCouponList().size() > maxCoupon) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.MAX_USEABLE_COUPON_COUNT, String.valueOf(maxCoupon)));
      return false;
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
    NeworderPaymentBean bean = createPaymentBean();

    // add by V10-CH start
    // SiteManagementService siteManagementService =
    // ServiceLocator.getSiteManagementService(ServiceLoginInfo.getInstance());
    // Long couponEnabledFlg =
    // siteManagementService.getCouponRule().getCouponFunctionEnabledFlg();
    //    
    // if (couponEnabledFlg == 1) {
    // OrderService orderService =
    // ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
    // List<CustomerCouponInfo> couponInfo =
    // orderService.getPaymentCouponList(cashier.getCustomer().getCustomerCode());
    // List<CouponBean> cbl = new ArrayList<CouponBean>();
    // for (CustomerCouponInfo cci : couponInfo) {
    // CouponBean cb = new CouponBean();
    // cb.setCouponName(cci.getCouponNameFront());
    // cb.setCouponPrice(cci.getCouponPrice());
    // cb.setUseEndDate(cci.getUseCouponEndDate());
    // cb.setCustomerCouponId(NumUtil.toString(cci.getCustomerCouponId()));
    // if (cashier.getCustomerCouponId() != null) {
    // for (CustomerCoupon customerCouponId : cashier.getCustomerCouponId()) {
    // if (StringUtil.hasValueAllOf(NumUtil.toString(cci.getCustomerCouponId()),
    // NumUtil.toString(customerCouponId.getCustomerCouponId()))
    // &&
    // NumUtil.toString(cci.getCustomerCouponId()).equals(NumUtil.toString(customerCouponId.getCustomerCouponId())))
    // {
    // cb.setSelectCouponId(NumUtil.toString(customerCouponId.getCustomerCouponId()));
    // }
    // }
    // }
    // cbl.add(cb);
    // }
    // bean.setCouponList(cbl);
    // }
    bean.setCouponFunctionEnabledFlg(CouponFunctionEnabledFlg.ENABLED.getValue());
    List<CustomerCoupon> ccl = new ArrayList<CustomerCoupon>();
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    for (String str : bean.getUseCouponList()) {
      if (StringUtil.hasValue(str)) {
        CustomerCoupon cc = service.getCustomerCoupon(str);
        ccl.add(cc);
      }
    }
    bean.getCashier().setCustomerCouponId(ccl);
    // add by V10-CH end

    // 支払方法関連情報の取得
    createCashierFromDisplay();

    BigDecimal totalPaymentPrice = BigDecimalUtil.subtract(bean.getTotalPrice(), NumUtil.parse(bean.getCouponTotalPrice()));
    UsePointBean pointInfo = bean.getUsePointBean();
    // modify V10-CN start
    if (ValidatorUtil.moreThan(NumUtil.parse(bean.getCashier().getUsePoint()), pointInfo.getRestPoint())) {
      pointInfo.setUsePoint(PointUtil.getUsePointWhenUsePointAboveRestPoint(pointInfo.getRestPoint()));
    }
    BigDecimal rmbPointRate = DIContainer.getWebshopConfig().getRmbPointRate();
    if (BigDecimalUtil.isAbove(NumUtil.parse(pointInfo.getUsePoint()), totalPaymentPrice.multiply(rmbPointRate).setScale(
        PointUtil.getAcquiredPointScale(), RoundingMode.CEILING))) {
      addInformationMessage(WebMessage.get(OrderErrorMessage.POINT_IN_FULL));
      pointInfo.setUsePoint(PointUtil.getUsePointWhenUsePointAboveTotalPricePoint(totalPaymentPrice));
    }
    // modify V10-CN end
    createCashierFromDisplay();

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderPaymentInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102015002";
  }

}
