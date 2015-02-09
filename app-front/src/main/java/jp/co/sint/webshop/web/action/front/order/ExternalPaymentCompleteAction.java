package jp.co.sint.webshop.web.action.front.order;

import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.order.CompleteBean;
import jp.co.sint.webshop.web.bean.front.order.ExternalPaymentBean;

/**
 * @author System Integrator Corp.
 */
public class ExternalPaymentCompleteAction extends WebFrontAction<ExternalPaymentBean> {

  public boolean validate() {
    if (StringUtil.isNullOrEmpty(orderNo())) {
      setNextUrl("/app/common/index");
      return false;
    }

    if (getLoginInfo().isNotLogin()) {
      setNextUrl("/app/common/index");
      return false;
    }
    
    // if (!getBean().isFirstTimePay()) {
    // if (StringUtil.isNullOrEmpty(getLoginInfo().getCustomerCode())) {
    // setNextUrl("/app/order/external_payment/complete/" +
    // getBean().getOrderNo());
    // return false;
    // }
    //      
    // OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    // OrderHeader orderHeader = service.getOrderHeader(getBean().getOrderNo());
    // if (orderHeader == null ||
    // orderHeader.getPaymentStatus().equals(PaymentStatus.NOT_PAID.longValue()))
    // {
    // setNextUrl("/app/common/index");
    // return false;
    // } else if
    // (!getLoginInfo().getCustomerCode().equals(orderHeader.getCustomerCode()))
    // {
    // setNextUrl("/app/common/index");
    // return false;
    // }
    // }
    return true;
  }

  public WebActionResult callService() {

    // if (!getBean().isFirstTimePay()) {
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    OrderHeader orderHeader = service.getOrderHeader(orderNo());
    if (orderHeader == null || orderHeader.getPaymentStatus().equals(PaymentStatus.NOT_PAID.longValue())) {
      setNextUrl("/app/common/index");
      return FrontActionResult.RESULT_SUCCESS;
    }
    if (!getLoginInfo().getCustomerCode().equals(orderHeader.getCustomerCode())) {
      setNextUrl("/app/common/index");
      return FrontActionResult.RESULT_SUCCESS;
    }
    CompleteBean bean = new CompleteBean();
    bean.setOrderNo(orderHeader.getOrderNo());
    bean.setOrderDatetime(DateUtil.toDateTimeString(orderHeader.getOrderDatetime()));
    bean.setOrderUserCode(orderHeader.getCustomerCode());

    setRequestBean(bean);
    // }

    setNextUrl("/app/order/complete");

    return FrontActionResult.RESULT_SUCCESS;
  }

  private String orderNo() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 0) {
      return tmpArgs[0];
    } else {
      return "";
    }
  }
}
