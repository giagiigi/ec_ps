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
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.OrderHistoryBean;
import jp.co.sint.webshop.web.bean.front.mypage.OrderHistoryBean.OrdersHistoryDetail;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

import org.apache.log4j.Logger;

/**
 * U2030610:注文履歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class OrderHistoryCancelAction extends WebFrontAction<OrderHistoryBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length < 0) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
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

    OrderHistoryBean bean = getBean();

    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    OrderHeader header = new OrderHeader();

    String[] urlParam = getRequestParameter().getPathArgs();

    // 受注番号チェック
    bean.setOrderNo(urlParam[0]);
    ValidationSummary validateCustomer = BeanValidator.partialValidate(bean, "orderNo");
    if (validateCustomer.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      for (String rs : validateCustomer.getErrorMessages()) {
        logger.debug(rs);
      }
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, 
          Messages.getString("web.action.front.mypage.OrderHistoryCancelAction.0")));
      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }

    header.setOrderNo(urlParam[0]);
    header.setUpdatedDatetime(bean.getUpdatedDatetime());

    OrderHeader orderHeader = service.getOrderHeader(header.getOrderNo());
    OrderSummary orderSummary = service.getOrderSummary(header.getOrderNo());

    // データ存在チェック
    if (orderHeader == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.front.mypage.OrderHistoryCancelAction.0")));

      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }

    // キャンセル済み
    if (orderHeader.getOrderStatus().equals(OrderStatus.CANCELLED.longValue())
        && orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.CANCELLED.longValue())) {
      setNextUrl("/app/mypage/order_history/init/" + bean.getSelectOrderStatusValue() + "/canceled");

      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }

    // キャンセル不可チェック
    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    Shop shop = shopService.getShop(orderHeader.getShopCode());
    if (orderHeader.getDataTransportStatus().equals(Long.valueOf(DataTransportStatus.TRANSPORTED.getValue()))
        || orderHeader.getPaymentStatus().equals(PaymentStatus.PAID.longValue())
        || !orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())
        || shop.getCustomerCancelableFlg().equals(CustomerCancelableFlg.DISABLED.longValue())) {
      addErrorMessage(WebMessage.get(MypageErrorMessage.DISAPPROVE_CANCEL_ORDER));

      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }

    OrderIdentifier orderIdentifier = new OrderIdentifier();
    orderIdentifier.setOrderNo(header.getOrderNo());
    for (OrdersHistoryDetail orderHistoryDetail : bean.getList()) {
      if (orderHistoryDetail.getOrderNo().equals(header.getOrderNo())) {
        orderIdentifier.setUpdatedDatetime(orderHistoryDetail.getUpdatedDatetime());
      }
    }

    // データベース更新処理
    ServiceResult serviceResult = service.cancel(orderIdentifier);

    // DBエラーの有無によって次画面のURLを制御する
    if (serviceResult.hasError()) {
      for (ServiceErrorContent result : serviceResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return FrontActionResult.SERVICE_VALIDATION_ERROR;
        } else if (result.equals(OrderServiceErrorContent.ALREADY_CANCELED_ERROR)) {
          setNextUrl("/app/mypage/order_history/init/" + bean.getSelectOrderStatusValue() + "/canceled");
          setRequestBean(bean);
          return FrontActionResult.RESULT_SUCCESS;
        } else if (result.equals(OrderServiceErrorContent.AUTHORIZATION_INCOMPLETE_ERROR)) {
          addErrorMessage(WebMessage.get(MypageErrorMessage.CREDIT_CANCEL_ERROR));
          setNextUrl("/app/mypage/order_history/init/" + bean.getSelectOrderStatusValue());
          setRequestBean(bean);
          return FrontActionResult.RESULT_SUCCESS;
        } else {
          return FrontActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);

    } else {
      setNextUrl("/app/mypage/order_history/init/" + bean.getSelectOrderStatusValue() + "/canceled");
    }

    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }
}
