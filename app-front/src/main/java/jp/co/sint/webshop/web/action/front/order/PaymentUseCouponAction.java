package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

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
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.order.PaymentBean;
import jp.co.sint.webshop.web.bean.front.order.PaymentBean.CouponBean;
import jp.co.sint.webshop.web.bean.front.order.PaymentBean.PointInfoBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;


/**
 * U2020130:お支払い方法選択のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PaymentUseCouponAction extends PaymentBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // modified by zhanghaibin start 2010-05-18
    PaymentBean bean = getBean();
    boolean valid = true;  
    for (CouponBean couponBean : bean.getCouponList()) {
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
    // modified by zhanghaibin end 2010-05-18
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    PaymentBean bean = getBean();
    
    List<CustomerCoupon> ccl = new ArrayList<CustomerCoupon>();
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());    
    for (String str : bean.getUseCouponList()) {
      if (StringUtil.hasValue(str)) {
        CustomerCoupon cc = service.getCustomerCoupon(str);
        ccl.add(cc);
      }
    }
    bean.getCashier().setCustomerCouponId(ccl);
    bean.getPointInfo().setUsePoint(bean.getCashier().getUsePoint());
    
    createCashierFromDisplay();
    
    PointInfoBean pointInfo = getBean().getPointInfo();

    if (ValidatorUtil.moreThan(NumUtil.parse(bean.getCashier().getUsePoint()), pointInfo.getRestPoint())) {
      pointInfo.setUsePoint(PointUtil.getUsePointWhenUsePointAboveRestPoint(pointInfo.getRestPoint()));
    }
    BigDecimal total = BigDecimalUtil.addAll(pointInfo.getTotalCommodityPrice(), pointInfo.getTotalGiftPrice(), pointInfo
        .getTotalShippingPrice()).subtract(pointInfo.getCouponTotalPrice());
    if (BigDecimalUtil.isAbove(NumUtil.parse(pointInfo.getUsePoint()), total.multiply(
        DIContainer.getWebshopConfig().getRmbPointRate()).setScale(PointUtil.getAcquiredPointScale(), RoundingMode.FLOOR))) {
      addInformationMessage(WebMessage.get(OrderErrorMessage.POINT_IN_FULL));
      pointInfo.setUsePoint(PointUtil.getUsePointWhenUsePointAboveTotalPricePoint(total));
    }
    
    createCashierFromDisplay();

    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

}
