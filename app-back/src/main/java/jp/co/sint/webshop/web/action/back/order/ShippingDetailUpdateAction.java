package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.ReturnItemType;
import jp.co.sint.webshop.data.dto.JdOrderHeader;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.ShippingDetailBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.InformationMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020420:出荷管理明細のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingDetailUpdateAction extends WebBackAction<ShippingDetailBean> {

  //ECのShipping情報の場合
  public static final String EMALL_SHIP_TYPE = "T";
  
  private static final String JD_SHIP_TYPE = "D";

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;
    BackLoginInfo login = getLoginInfo();
    if (Permission.SHIPPING_UPDATE_SITE.isGranted(login) || Permission.SHIPPING_UPDATE_SHOP.isGranted(login)) {
      auth = true;
    }
    return auth;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    ServiceResult result = null;
    ShippingDetailBean reqBean = getBean();
    
    // 受注タイプを取得する
    String shippingType = reqBean.getOrderHeader().getOrderNo().substring(0,1);
    
    // ECの場合
    if (!(EMALL_SHIP_TYPE.equals(shippingType) || JD_SHIP_TYPE.equals(shippingType))) {
      OrderService service = ServiceLocator.getOrderService(getLoginInfo());
      OrderHeader orderHeader = service.getOrderHeader(reqBean.getOrderHeader().getOrderNo());

      if (orderHeader == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.order.ShippingDetailUpdateAction.0")));
        return BackActionResult.RESULT_SUCCESS;
      }

      // 顧客退会チェック
      if (CustomerConstant.isCustomer(orderHeader.getCustomerCode())) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        if (customerService.isWithdrawed(orderHeader.getCustomerCode())) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, orderHeader.getOrderNo()));
          return BackActionResult.RESULT_SUCCESS;
        }
      }

      // データ連携済みステータスが「連携済み」の場合は変更不可
      if (orderHeader.getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.longValue())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.ORDER_DATA_TRANSPORTED_WITH_NO, reqBean.getOrderHeader().getOrderNo()));
        return BackActionResult.RESULT_SUCCESS;
      }

      // 返品済み情報は変更不可
      if (reqBean.getDeliveryHeader().getReturnItemType().equals(ReturnItemType.RETURNED.getValue())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.UPDATE_RETURNED_SHIPPING));
        return BackActionResult.RESULT_SUCCESS;
      }

      orderHeader.setOrderNo(reqBean.getOrderHeader().getOrderNo());
      orderHeader.setCaution(reqBean.getOrderHeader().getCaution());
      orderHeader.setMessage(reqBean.getOrderHeader().getMessage());
      orderHeader.setUpdatedUser(getLoginInfo().getLoginId());
      orderHeader.setUpdatedDatetime(reqBean.getOrderHeader().getOrderHeaderUpdatedDatetime());

      result = service.updateOrderRemark(orderHeader.getOrderNo(), orderHeader.getMessage(), orderHeader.getCaution(),
          orderHeader.getUpdatedDatetime());
    // TMALLの場合
    } else if(EMALL_SHIP_TYPE.equals(shippingType)) {
      OrderService service = ServiceLocator.getOrderService(getLoginInfo());
      TmallOrderHeader orderHeader = service.getTmallOrderHeader(reqBean.getOrderHeader().getOrderNo());

      if (orderHeader == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.order.ShippingDetailUpdateAction.0")));
        return BackActionResult.RESULT_SUCCESS;
      }

      // 顧客退会チェック
      if (CustomerConstant.isCustomer(orderHeader.getCustomerCode())) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        if (customerService.isWithdrawed(orderHeader.getCustomerCode())) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, orderHeader.getOrderNo()));
          return BackActionResult.RESULT_SUCCESS;
        }
      }

      // データ連携済みステータスが「連携済み」の場合は変更不可
      if (orderHeader.getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.longValue())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.ORDER_DATA_TRANSPORTED_WITH_NO, reqBean.getOrderHeader().getOrderNo()));
        return BackActionResult.RESULT_SUCCESS;
      }

      // 返品済み情報は変更不可
      if (reqBean.getDeliveryHeader().getReturnItemType().equals(ReturnItemType.RETURNED.getValue())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.UPDATE_RETURNED_SHIPPING));
        return BackActionResult.RESULT_SUCCESS;
      }

      orderHeader.setOrderNo(reqBean.getOrderHeader().getOrderNo());
      orderHeader.setCaution(reqBean.getOrderHeader().getCaution());
      orderHeader.setMessage(reqBean.getOrderHeader().getMessage());
      orderHeader.setUpdatedUser(getLoginInfo().getLoginId());
      orderHeader.setUpdatedDatetime(reqBean.getOrderHeader().getOrderHeaderUpdatedDatetime());

      result = service.updateTmallOrderRemark(orderHeader.getOrderNo(), orderHeader.getMessage(), orderHeader.getCaution(),
          orderHeader.getUpdatedDatetime());
    // JDの場合
    } else {
      OrderService service = ServiceLocator.getOrderService(getLoginInfo());
      JdOrderHeader orderHeader = service.getJdOrderHeader(reqBean.getOrderHeader().getOrderNo());
      if(orderHeader == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages.getString("web.action.back.order.ShippingDetailUpdateAction.0")));
        return BackActionResult.RESULT_SUCCESS;
      }
      // 顧客退会チェック
      if(CustomerConstant.isCustomer(orderHeader.getCustomerCode())) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        if(customerService.isWithdrawed(orderHeader.getCustomerCode())) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, orderHeader.getOrderNo()));
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      // データ連携済みステータスが「連携済み」の場合は変更不可
      if(orderHeader.getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.longValue())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.ORDER_DATA_TRANSPORTED_WITH_NO, reqBean.getOrderHeader().getOrderNo()));
        return BackActionResult.RESULT_SUCCESS;
      }

      // 返品済み情報は変更不可
      if(reqBean.getDeliveryHeader().getReturnItemType().equals(ReturnItemType.RETURNED.getValue())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.UPDATE_RETURNED_SHIPPING));
        return BackActionResult.RESULT_SUCCESS;
      }

      orderHeader.setOrderNo(reqBean.getOrderHeader().getOrderNo());
      orderHeader.setCaution(reqBean.getOrderHeader().getCaution());
      orderHeader.setMessage(reqBean.getOrderHeader().getMessage());
      orderHeader.setUpdatedUser(getLoginInfo().getLoginId());
      orderHeader.setUpdatedDatetime(reqBean.getOrderHeader().getOrderHeaderUpdatedDatetime());

      result = service.updateJdOrderRemark(orderHeader.getOrderNo(), orderHeader.getMessage(), orderHeader.getCaution(), orderHeader.getUpdatedDatetime());
    }
    
    if (result.hasError()) {
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.order.ShippingDetailUpdateAction.0")));
        } else if (errorContent.equals(OrderServiceErrorContent.FIXED_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.FIXED_DATA_WITH_NO, reqBean.getOrderHeader().getOrderNo()));
        } else if (errorContent.equals(OrderServiceErrorContent.DATA_TRANSPORT_ERROR)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.ORDER_DATA_TRANSPORTED_WITH_NO, reqBean.getOrderHeader().getOrderNo()));
        } else if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    } else {
      setNextUrl("/app/order/shipping_detail/init/"
          + reqBean.getDeliveryHeader().getShippingNo() + "/update");
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    ShippingDetailBean reqBean = getBean();
    return validateBean(reqBean.getOrderHeader());
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    ShippingDetailBean reqBean = (ShippingDetailBean) getBean();
    BackLoginInfo login = getLoginInfo();

    boolean displayFlg = false;
    if (Permission.SHIPPING_UPDATE_SITE.isGranted(login) || Permission.SHIPPING_UPDATE_SHOP.isGranted(login)) {
      displayFlg = true;
    } else {
      displayFlg = false;
    }
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    if (CustomerConstant.isCustomer(reqBean.getOrderHeader().getCustomerCode())
        && customerService.isWithdrawed(reqBean.getOrderHeader().getCustomerCode())) {
      displayFlg = false;
    }
    if (DataTransportStatus.fromValue(reqBean.getOrderHeader().getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED)) {
      addInformationMessage(WebMessage.get(InformationMessage.DATA_TRANSPORTED));
    }

    reqBean.setDisplayFlg(displayFlg);
    setRequestBean(reqBean);

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.ShippingDetailUpdateAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102042004";
  }

}
