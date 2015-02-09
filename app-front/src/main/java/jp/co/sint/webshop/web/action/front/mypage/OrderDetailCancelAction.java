package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.domain.CustomerCancelableFlg;
import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.order.OrderIdentifier;
import jp.co.sint.webshop.service.order.OrderSummary;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.OrderDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030620:注文内容のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class OrderDetailCancelAction extends WebFrontAction<OrderDetailBean> {

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
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());

    OrderDetailBean bean = getBean();

    OrderHeader orderHeader = service.getOrderHeader(bean.getOrderNo());
    OrderSummary orderSummary = service.getOrderSummary(bean.getOrderNo());

    // データ存在チェック
    if (orderHeader == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages.getString("web.action.front.mypage.OrderHistoryCancelAction.0")));

      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }

    // キャンセル済み
    if (orderHeader.getOrderStatus().equals(OrderStatus.CANCELLED.longValue())
        && orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.CANCELLED.longValue())) {
      setNextUrl("/app/mypage/order_detail/init/" + bean.getOrderNo() + "/cancel");

      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }

    // キャンセル不可チェック
    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    Shop shop = shopService.getShop(orderHeader.getShopCode());
    if (shop.getCustomerCancelableFlg().equals(CustomerCancelableFlg.DISABLED.longValue())
        || orderHeader.getDataTransportStatus().equals(Long.valueOf(DataTransportStatus.TRANSPORTED.getValue()))
        || orderHeader.getPaymentStatus().equals(PaymentStatus.PAID.longValue())
        || !orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())) {
      addErrorMessage(WebMessage.get(MypageErrorMessage.DISAPPROVE_CANCEL_ORDER));

      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }

    OrderIdentifier orderIdentifier = new OrderIdentifier();
    orderIdentifier.setOrderNo(bean.getOrderNo());
    orderIdentifier.setUpdatedDatetime(bean.getUpdatedDatetime());

    ServiceResult result = service.cancel(orderIdentifier);

    if (result.hasError()) {
      for (ServiceErrorContent content : result.getServiceErrorList()) {
        if (content.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return FrontActionResult.SERVICE_VALIDATION_ERROR;
        } else if (content.equals(OrderServiceErrorContent.ALREADY_CANCELED_ERROR)) {
          setNextUrl("/app/mypage/order_detail/init/" + bean.getOrderNo() + "/cancel");
          setRequestBean(bean);
          return FrontActionResult.RESULT_SUCCESS;
        } else if (content.equals(OrderServiceErrorContent.AUTHORIZATION_INCOMPLETE_ERROR)) {
          addErrorMessage(WebMessage.get(MypageErrorMessage.CREDIT_CANCEL_ERROR));
          setNextUrl("/app/mypage/order_detail/init/" + bean.getOrderNo());
          setRequestBean(bean);
          return FrontActionResult.RESULT_SUCCESS;
        } else {
          return FrontActionResult.SERVICE_ERROR;
        }
      }
    }

    setNextUrl("/app/mypage/order_detail/init/" + bean.getOrderNo() + "/cancel");

    return FrontActionResult.RESULT_SUCCESS;
  }

}
