package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.dto.JdOrderHeader;
import jp.co.sint.webshop.data.dto.JdShippingHeader;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.data.dto.TmallShippingHeader;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.order.OrderIdentifier;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderDetailBean;
import jp.co.sint.webshop.web.bean.back.order.OrderDetailBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020220:受注管理明細のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderDetailCancelOrderAction extends OrderDetailBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;

    if (getConfig().getOperatingMode().equals(OperatingMode.MALL)) {
      authorization = Permission.ORDER_MODIFY_SITE.isGranted(getLoginInfo());
    } else if (getConfig().getOperatingMode().equals(OperatingMode.SHOP)) {
      authorization = Permission.ORDER_MODIFY_SHOP.isGranted(getLoginInfo())
          || Permission.ORDER_MODIFY_SITE.isGranted(getLoginInfo());
    } else if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = Permission.ORDER_MODIFY_SITE.isGranted(getLoginInfo());
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
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    OrderDetailBean bean = getBean();

    String orderType = bean.getOrderNo().substring(0, 1);
    
    if ((!"T".equals(orderType)) && (!"D".equals(orderType))) {
      
      OrderHeader orderHeader = service.getOrderHeader(bean.getOrderNo());
      // データ存在チェック
      if (orderHeader == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
            .getString("web.action.back.order.OrderDetailCancelOrderAction.0")));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }
      

      if(OrderStatus.CANCELLED.equals(orderHeader.getOrderStatus())) {
        addErrorMessage("该订单已取消");
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }

      // 顧客退会チェック
      if (CustomerConstant.isCustomer(orderHeader.getCustomerCode())) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        if (customerService.isWithdrawed(orderHeader.getCustomerCode())) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, orderHeader.getOrderNo()));
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }

      // データ連携済チェック
      if (DataTransportStatus.fromValue(orderHeader.getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED)) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.DATA_TRANSPORTED));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }

      for (ShippingHeaderBean shippingHeader : bean.getShippingList()) {
        ShippingHeader shippingHeaderInfo = service.getShippingHeader(shippingHeader.getShippingNo());
        if (shippingHeaderInfo.getShippingStatus().equals(ShippingStatus.IN_PROCESSING.longValue())
            || shippingHeaderInfo.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())) {
          addErrorMessage("该订单目前已发货，无法取消");
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }

      OrderIdentifier orderIdentifier = new OrderIdentifier();
      orderIdentifier.setOrderNo(bean.getOrderNo());
      //orderIdentifier.setUpdatedDatetime(bean.getOrderHeaderEdit().getUpdateDatetime());

      ServiceResult result = service.cancel(orderIdentifier);

      if (result.hasError()) {
        for (ServiceErrorContent content : result.getServiceErrorList()) {
          if (content == CommonServiceErrorContent.VALIDATION_ERROR) {
            return BackActionResult.SERVICE_VALIDATION_ERROR;
          } else if (content == OrderServiceErrorContent.AUTHORIZATION_INCOMPLETE_ERROR) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.CREDIT_CANCEL_ERROR));
            setRequestBean(bean);
            return BackActionResult.RESULT_SUCCESS;
            // soukai add 2012/02/04 ob start
          } else if (content == OrderServiceErrorContent.ORDER_CANCEL_DISABLED) {
            setNextUrl("/app/order/order_detail/init/" + bean.getOrderNo() + "/cancel_error");
            setRequestBean(bean);
            return BackActionResult.RESULT_SUCCESS;
            // soukai add 2012/02/04 ob end
          } else {
            return BackActionResult.SERVICE_ERROR;
          }
        }
      }
      
    } else if("T".equals(orderType)){
      
      TmallOrderHeader orderHeader = service.getTmallOrderHeader(bean.getOrderNo());

      // データ存在チェック
      if (orderHeader == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
            .getString("web.action.back.order.OrderDetailCancelOrderAction.0")));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }

      if(OrderStatus.CANCELLED.equals(orderHeader.getOrderStatus())) {
        addErrorMessage("该订单已取消");
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }

      // データ連携済チェック
      if (DataTransportStatus.fromValue(orderHeader.getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED)) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.DATA_TRANSPORTED));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }

      for (ShippingHeaderBean shippingHeader : bean.getShippingList()) {
        TmallShippingHeader shippingHeaderInfo = service.getTmallShippingHeader(shippingHeader.getShippingNo());
        if (shippingHeaderInfo.getShippingStatus().equals(ShippingStatus.IN_PROCESSING.longValue())
            || shippingHeaderInfo.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())) {
          addErrorMessage("该订单目前已发货，无法取消");
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }

      OrderIdentifier orderIdentifier = new OrderIdentifier();
      orderIdentifier.setOrderNo(bean.getOrderNo());
      //orderIdentifier.setUpdatedDatetime(bean.getOrderHeaderEdit().getUpdateDatetime());

      ServiceResult result = service.tmallCancel(orderIdentifier);

      if (result.hasError()) {
        for (ServiceErrorContent content : result.getServiceErrorList()) {
          if (content == CommonServiceErrorContent.VALIDATION_ERROR) {
            return BackActionResult.SERVICE_VALIDATION_ERROR;
          } else if (content == OrderServiceErrorContent.AUTHORIZATION_INCOMPLETE_ERROR) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.CREDIT_CANCEL_ERROR));
            setRequestBean(bean);
            return BackActionResult.RESULT_SUCCESS;
            // soukai add 2012/02/04 ob start
          } else if (content == OrderServiceErrorContent.ORDER_CANCEL_DISABLED) {
            setNextUrl("/app/order/order_detail/init/" + bean.getOrderNo() + "/cancel_error");
            setRequestBean(bean);
            return BackActionResult.RESULT_SUCCESS;
            // soukai add 2012/02/04 ob end
          } else {
            return BackActionResult.SERVICE_ERROR;
          }
        }
      }
      
    } else {
      JdOrderHeader orderHeader = service.getJdOrderHeader(bean.getOrderNo());

      // データ存在チェック
      if (orderHeader == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
            .getString("web.action.back.order.OrderDetailCancelOrderAction.0")));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }

      if(OrderStatus.CANCELLED.equals(orderHeader.getOrderStatus())) {
        addErrorMessage("该订单已取消");
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }

      // データ連携済チェック
      if (DataTransportStatus.fromValue(orderHeader.getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED)) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.DATA_TRANSPORTED));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }

      for (ShippingHeaderBean shippingHeader : bean.getShippingList()) {
        JdShippingHeader shippingHeaderInfo = service.getJdShippingHeader(shippingHeader.getShippingNo());
        if (shippingHeaderInfo.getShippingStatus().equals(ShippingStatus.IN_PROCESSING.longValue())
            || shippingHeaderInfo.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())) {
          addErrorMessage("该订单目前已发货，无法取消");
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }

      OrderIdentifier orderIdentifier = new OrderIdentifier();
      orderIdentifier.setOrderNo(bean.getOrderNo());
      //orderIdentifier.setUpdatedDatetime(bean.getOrderHeaderEdit().getUpdateDatetime());

      ServiceResult result = service.jdCancel(orderIdentifier);

      if (result.hasError()) {
        for (ServiceErrorContent content : result.getServiceErrorList()) {
          if (content == CommonServiceErrorContent.VALIDATION_ERROR) {
            return BackActionResult.SERVICE_VALIDATION_ERROR;
          } else if (content == OrderServiceErrorContent.AUTHORIZATION_INCOMPLETE_ERROR) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.CREDIT_CANCEL_ERROR));
            setRequestBean(bean);
            return BackActionResult.RESULT_SUCCESS;
            // soukai add 2012/02/04 ob start
          } else if (content == OrderServiceErrorContent.ORDER_CANCEL_DISABLED) {
            setNextUrl("/app/order/order_detail/init/" + bean.getOrderNo() + "/cancel_error");
            setRequestBean(bean);
            return BackActionResult.RESULT_SUCCESS;
            // soukai add 2012/02/04 ob end
          } else {
            return BackActionResult.SERVICE_ERROR;
          }
        }
      }
    }

    setNextUrl("/app/order/order_detail/init/" + bean.getOrderNo() + "/" + WebConstantCode.COMPLETE_CANCEL);

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderDetailCancelOrderAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102022001";
  }

}
