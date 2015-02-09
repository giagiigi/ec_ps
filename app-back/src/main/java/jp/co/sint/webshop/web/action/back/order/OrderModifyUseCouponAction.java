package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.CouponBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020230:受注修正のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderModifyUseCouponAction extends OrderModifyBaseAction {

  @Override
  public boolean validate() {
    boolean valid = true;
    OrderModifyBean bean = getBean();
    for (CouponBean couponBean : bean.getCouponInfoBean().getAfterCouponList()) {
      String selectID = "";
      for (String str : bean.getUseCouponList()) {
        if(couponBean.getCustomerCouponId().equals(str)) {
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
      valid = false;
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
    
    OrderModifyBean bean = getBean();
    List<CustomerCoupon> ccl = new ArrayList<CustomerCoupon>();
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());  
    BigDecimal afertCouponPrice = BigDecimal.ZERO;
    for (String str : bean.getUseCouponList()) {
      if (StringUtil.hasValue(str)) {
        CustomerCoupon cc = service.getCustomerCoupon(str);
        ccl.add(cc);
        afertCouponPrice = BigDecimalUtil.add(afertCouponPrice, cc.getCouponPrice());
      }
    }
    bean.getCouponInfoBean().setAfterCouponPrice(afertCouponPrice);
    bean.setCustomerCoupon(ccl);
    
    recomputePoint(getBean());
    recomputePaymentCommission(getBean());
    recomputePrice(getBean());

    numberLimitValidation(createOrderContainer(getBean()));

    setRequestBean(getBean());
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderModifyComputePointAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102023011";
  }

}
