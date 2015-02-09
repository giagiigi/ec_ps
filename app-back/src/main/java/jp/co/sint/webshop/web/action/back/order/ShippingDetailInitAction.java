package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.domain.AdvanceLaterFlg;
import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.ReturnItemType;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.dto.JdOrderDetail;
import jp.co.sint.webshop.data.dto.JdOrderHeader;
import jp.co.sint.webshop.data.dto.JdShippingDetail;
import jp.co.sint.webshop.data.dto.JdShippingHeader;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.TmallOrderDetail;
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.data.dto.TmallShippingDetail;
import jp.co.sint.webshop.data.dto.TmallShippingHeader;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.ShippingDetailBean;
import jp.co.sint.webshop.web.bean.back.order.ShippingDetailBean.DeliveryDetailBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.InformationMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020420:出荷管理明細のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingDetailInitAction extends WebBackAction<ShippingDetailBean> {
  
  //ECのShipping情報の場合
  public static final String EMALL_SHIP_TYPE = "T";
  
  private static final String JD_SHIP_TYPE = "D";
  
  private String shippingNo;

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;
    BackLoginInfo login = getLoginInfo();
    OperatingMode mode = getConfig().getOperatingMode();
    if (mode == OperatingMode.ONE) {
      auth = Permission.SHIPPING_READ_SITE.isGranted(login);
    } else {
      auth = Permission.SHIPPING_READ_SHOP.isGranted(login) || Permission.SHIPPING_READ_SITE.isGranted(login);
    }
    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    boolean result = false;

    ShippingDetailBean reqBean = getBean();
    String[] params = getRequestParameter().getPathArgs();
    shippingNo = "";

    if (params.length > 0) {
      shippingNo = params[0];
      reqBean.getDeliveryHeader().setShippingNo(shippingNo);
      result = validateItems(reqBean.getDeliveryHeader(), "shippingNo");
    } else {
      result = false;
    }

    return result;

  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    
    // 受注タイプを取得する
    String shippingType = shippingNo.substring(0,1);
    
    // ECの場合
    if (!(EMALL_SHIP_TYPE.equals(shippingType) || JD_SHIP_TYPE.equals(shippingType))) {
      ShippingDetailBean reqBean = getBean();
      OrderService orderSvc = ServiceLocator.getOrderService(getLoginInfo());

      if (getLoginInfo().isShop()) {
        ShippingHeader shippingHeader = orderSvc.getShippingHeader(shippingNo);
        if (!shippingHeader.getShopCode().equals(getLoginInfo().getShopCode())) {
          setNextUrl("/app/common/login");
          return BackActionResult.RESULT_SUCCESS;
        }
      }

      reqBean.getDeliveryHeader().setShippingNo(shippingNo);
      reqBean.setUpdateWithShipping(true);

      // 出荷ヘッダ取得
      ShippingHeader shDto = orderSvc.getShippingHeader(reqBean.getDeliveryHeader().getShippingNo());
      reqBean.getOrderHeader().setOrderNo(shDto.getOrderNo());
      // 20120130 ysy add start
      List<CodeAttribute> shipNoSum = orderSvc.getDeliveryShipNoSum(reqBean.getDeliveryHeader().getShippingNo());
      // 20120130 ysy add end    

      OrderContainer container = orderSvc
          .getOrder(reqBean.getOrderHeader().getOrderNo(), reqBean.getDeliveryHeader().getShippingNo());
      OrderHeader ohDto = container.getOrderHeader();
      // M17N 10361 追加 ここから
      OrderStatus orderStatus = OrderStatus.fromValue(ohDto.getOrderStatus());
      if (orderStatus == OrderStatus.PHANTOM_ORDER || orderStatus == OrderStatus.PHANTOM_RESERVATION) {
        throw new URLNotFoundException();
      }
      // M17N 10361 追加 ここまで

      CustomerInfo customerInfo = new CustomerInfo();
      // 会員購入の場合会員情報を取得
      if (CustomerConstant.isCustomer(ohDto.getCustomerCode())) {
        CustomerService customerSvc = ServiceLocator.getCustomerService(getLoginInfo());
        customerInfo = customerSvc.getCustomer(ohDto.getCustomerCode());
      }

      // 受注からの情報設定

      setOrderHeader(container.getOrderHeader(), customerInfo, reqBean);

      // 出荷からの情報設定

      setShipping(container.getShippings().get(0), container.getOrderDetails(), shipNoSum, reqBean);

      setRequestBean(reqBean);
     //TMALLの場合
    } else if(EMALL_SHIP_TYPE.equals(shippingType)) {
      ShippingDetailBean reqBean = getBean();
      OrderService orderSvc = ServiceLocator.getOrderService(getLoginInfo());

      if (getLoginInfo().isShop()) {
        ShippingHeader shippingHeader = orderSvc.getShippingHeader(shippingNo);
        if (!shippingHeader.getShopCode().equals(getLoginInfo().getShopCode())) {
          setNextUrl("/app/common/login");
          return BackActionResult.RESULT_SUCCESS;
        }
      }

      reqBean.getDeliveryHeader().setShippingNo(shippingNo);
      reqBean.setUpdateWithShipping(true);

      // 出荷ヘッダ取得
      TmallShippingHeader shDto = orderSvc.getTmallShippingHeader(reqBean.getDeliveryHeader().getShippingNo());
      reqBean.getOrderHeader().setOrderNo(shDto.getOrderNo());
      // 20120130 ysy add start
      List<CodeAttribute> shipNoSum = orderSvc.getTmallDeliveryShipNoSum(reqBean.getDeliveryHeader().getShippingNo());
      // 20120130 ysy add end    

      OrderContainer container = orderSvc
          .getTmallOrder(reqBean.getOrderHeader().getOrderNo(), reqBean.getDeliveryHeader().getShippingNo());
      TmallOrderHeader ohDto = container.getTmallOrderHeader();
      // M17N 10361 追加 ここから
      OrderStatus orderStatus = OrderStatus.fromValue(ohDto.getOrderStatus());
      if (orderStatus == OrderStatus.PHANTOM_ORDER || orderStatus == OrderStatus.PHANTOM_RESERVATION) {
        throw new URLNotFoundException();
      }
      // M17N 10361 追加 ここまで

      CustomerInfo customerInfo = new CustomerInfo();
      // 会員購入の場合会員情報を取得
      if (CustomerConstant.isCustomer(ohDto.getCustomerCode())) {
        CustomerService customerSvc = ServiceLocator.getCustomerService(getLoginInfo());
        customerInfo = customerSvc.getCustomer(ohDto.getCustomerCode());
      }

      // 受注からの情報設定

      setTmallOrderHeader(container.getTmallOrderHeader(), customerInfo, reqBean);

      // 出荷からの情報設定

      setTmallShipping(container.getShippings().get(0), container.getTmallIOrderDetails(), shipNoSum, reqBean);
      
      setRequestBean(reqBean);
      // JDの場合
    } else {
      ShippingDetailBean reqBean = getBean();
      OrderService orderSvc = ServiceLocator.getOrderService(getLoginInfo());

      if (getLoginInfo().isShop()) {
        ShippingHeader shippingHeader = orderSvc.getShippingHeader(shippingNo);
        if (!shippingHeader.getShopCode().equals(getLoginInfo().getShopCode())) {
          setNextUrl("/app/common/login");
          return BackActionResult.RESULT_SUCCESS;
        }
      }

      reqBean.getDeliveryHeader().setShippingNo(shippingNo);
      reqBean.setUpdateWithShipping(true);

      // 出荷ヘッダ取得
      JdShippingHeader shDto = orderSvc.getJdShippingHeader(reqBean.getDeliveryHeader().getShippingNo());
      reqBean.getOrderHeader().setOrderNo(shDto.getOrderNo());
      // 20120130 ysy add start
      List<CodeAttribute> shipNoSum = orderSvc.getJdDeliveryShipNoSum(reqBean.getDeliveryHeader().getShippingNo());
      // 20120130 ysy add end    

      OrderContainer container = orderSvc.getJdOrder(reqBean.getOrderHeader().getOrderNo(), reqBean.getDeliveryHeader().getShippingNo());
      JdOrderHeader ohDto = container.getJdOrderHeader();
      // M17N 10361 追加 ここから
      OrderStatus orderStatus = OrderStatus.fromValue(ohDto.getOrderStatus());
      if (orderStatus == OrderStatus.PHANTOM_ORDER || orderStatus == OrderStatus.PHANTOM_RESERVATION) {
        throw new URLNotFoundException();
      }
      // M17N 10361 追加 ここまで

      CustomerInfo customerInfo = new CustomerInfo();
      // 会員購入の場合会員情報を取得
      if (CustomerConstant.isCustomer(ohDto.getCustomerCode())) {
        CustomerService customerSvc = ServiceLocator.getCustomerService(getLoginInfo());
        customerInfo = customerSvc.getCustomer(ohDto.getCustomerCode());
      }

      // 受注からの情報設定

      setJdOrderHeader(container.getJdOrderHeader(), customerInfo, reqBean);

      // 出荷からの情報設定

      setJdShipping(container.getShippings().get(0), container.getJdOrderDetails(), shipNoSum, reqBean);
      
      setRequestBean(reqBean);
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  private void setOrderHeader(OrderHeader orderHeader, CustomerInfo customerInfo, ShippingDetailBean reqBean) {
    reqBean.getOrderHeader().setOrderNo(orderHeader.getOrderNo());
    reqBean.getOrderHeader().setOrderDatetime(DateUtil.toDateTimeString(orderHeader.getOrderDatetime()));
    reqBean.getOrderHeader().setMessage(orderHeader.getMessage());
    reqBean.getOrderHeader().setCaution(orderHeader.getCaution());
    reqBean.getOrderHeader().setCustomerCode(orderHeader.getCustomerCode());
    reqBean.getOrderHeader().setCustomerFirstName(orderHeader.getFirstName());
    reqBean.getOrderHeader().setCustomerLastName(orderHeader.getLastName());
    reqBean.getOrderHeader().setCustomerFirstNameKana(orderHeader.getFirstNameKana());
    reqBean.getOrderHeader().setCustomerLastNameKana(orderHeader.getLastNameKana());
    reqBean.getOrderHeader().setCustomerPhoneNumber(orderHeader.getPhoneNumber());
    //Add by V10-CH start
    reqBean.getOrderHeader().setCustomerMobileNumber(orderHeader.getMobileNumber());
    //Add by V10-CH end
    reqBean.getOrderHeader().setPaymentMethodName(orderHeader.getPaymentMethodName());
    reqBean.getOrderHeader().setOrderHeaderUpdatedDatetime(orderHeader.getUpdatedDatetime());
    reqBean.getOrderHeader().setDataTransportStatus(Long.toString(orderHeader.getDataTransportStatus()));

    for (AdvanceLaterFlg al : AdvanceLaterFlg.values()) {
      if (NumUtil.toLong(al.getValue()).equals(orderHeader.getAdvanceLaterFlg())) {
        reqBean.getOrderHeader().setAdvanceLaterName(al.getName());
      }
    }

    // 会員の場合、最新のメールアドレスを使用する
    if (CustomerConstant.isCustomer(orderHeader.getCustomerCode())) {
      reqBean.getOrderHeader().setCustomerEmail(customerInfo.getCustomer().getEmail());
    } else {
      reqBean.getOrderHeader().setCustomerEmail(orderHeader.getEmail());
    }
    reqBean.getOrderHeader().setOrderHeaderUpdatedUser(orderHeader.getUpdatedUser());
    reqBean.getOrderHeader().setOrderHeaderUpdatedDatetime(orderHeader.getUpdatedDatetime());
  }

  
  private void setTmallOrderHeader(TmallOrderHeader orderHeader, CustomerInfo customerInfo, ShippingDetailBean reqBean) {
    reqBean.getOrderHeader().setOrderNo(orderHeader.getOrderNo());
    reqBean.getOrderHeader().setOrderDatetime(DateUtil.toDateTimeString(orderHeader.getOrderDatetime()));
    reqBean.getOrderHeader().setMessage(orderHeader.getMessage());
    reqBean.getOrderHeader().setCaution(orderHeader.getCaution());
    reqBean.getOrderHeader().setCustomerCode(orderHeader.getCustomerCode());
    reqBean.getOrderHeader().setCustomerFirstName(orderHeader.getFirstName());
    reqBean.getOrderHeader().setCustomerLastName(orderHeader.getLastName());
    reqBean.getOrderHeader().setCustomerFirstNameKana(orderHeader.getFirstNameKana());
    reqBean.getOrderHeader().setCustomerLastNameKana(orderHeader.getLastNameKana());
    reqBean.getOrderHeader().setCustomerPhoneNumber(orderHeader.getPhoneNumber());
    //Add by V10-CH start
    reqBean.getOrderHeader().setCustomerMobileNumber(orderHeader.getMobileNumber());
    //Add by V10-CH end
    reqBean.getOrderHeader().setPaymentMethodName(orderHeader.getPaymentMethodName());
    reqBean.getOrderHeader().setOrderHeaderUpdatedDatetime(orderHeader.getUpdatedDatetime());
    reqBean.getOrderHeader().setDataTransportStatus(Long.toString(orderHeader.getDataTransportStatus()));

    for (AdvanceLaterFlg al : AdvanceLaterFlg.values()) {
      if (NumUtil.toLong(al.getValue()).equals(orderHeader.getAdvanceLaterFlg())) {
        reqBean.getOrderHeader().setAdvanceLaterName(al.getName());
      }
    }

    // 会員の場合、最新のメールアドレスを使用する
    if (CustomerConstant.isCustomer(orderHeader.getCustomerCode())) {
      reqBean.getOrderHeader().setCustomerEmail(customerInfo.getCustomer().getEmail());
    } else {
      reqBean.getOrderHeader().setCustomerEmail(orderHeader.getEmail());
    }
    reqBean.getOrderHeader().setOrderHeaderUpdatedUser(orderHeader.getUpdatedUser());
    reqBean.getOrderHeader().setOrderHeaderUpdatedDatetime(orderHeader.getUpdatedDatetime());
  }
  

  private void setJdOrderHeader(JdOrderHeader orderHeader, CustomerInfo customerInfo, ShippingDetailBean reqBean) {
    reqBean.getOrderHeader().setOrderNo(orderHeader.getOrderNo());
    reqBean.getOrderHeader().setOrderDatetime(DateUtil.toDateTimeString(orderHeader.getOrderDatetime()));
    reqBean.getOrderHeader().setMessage(orderHeader.getMessage());
    reqBean.getOrderHeader().setCaution(orderHeader.getCaution());
    reqBean.getOrderHeader().setCustomerCode(orderHeader.getCustomerCode());
    reqBean.getOrderHeader().setCustomerFirstName(orderHeader.getFirstName());
    reqBean.getOrderHeader().setCustomerLastName(orderHeader.getLastName());
    reqBean.getOrderHeader().setCustomerFirstNameKana(orderHeader.getFirstNameKana());
    reqBean.getOrderHeader().setCustomerLastNameKana(orderHeader.getLastNameKana());
    reqBean.getOrderHeader().setCustomerPhoneNumber(orderHeader.getPhoneNumber());
    //Add by V10-CH start
    reqBean.getOrderHeader().setCustomerMobileNumber(orderHeader.getMobileNumber());
    //Add by V10-CH end
    reqBean.getOrderHeader().setPaymentMethodName(orderHeader.getPaymentMethodName());
    reqBean.getOrderHeader().setOrderHeaderUpdatedDatetime(orderHeader.getUpdatedDatetime());
    reqBean.getOrderHeader().setDataTransportStatus(Long.toString(orderHeader.getDataTransportStatus()));

    for (AdvanceLaterFlg al : AdvanceLaterFlg.values()) {
      if (NumUtil.toLong(al.getValue()).equals(orderHeader.getAdvanceLaterFlg())) {
        reqBean.getOrderHeader().setAdvanceLaterName(al.getName());
      }
    }

    // 会員の場合、最新のメールアドレスを使用する
    if (CustomerConstant.isCustomer(orderHeader.getCustomerCode())) {
      reqBean.getOrderHeader().setCustomerEmail(customerInfo.getCustomer().getEmail());
    } else {
      reqBean.getOrderHeader().setCustomerEmail(orderHeader.getEmail());
    }
    reqBean.getOrderHeader().setOrderHeaderUpdatedUser(orderHeader.getUpdatedUser());
    reqBean.getOrderHeader().setOrderHeaderUpdatedDatetime(orderHeader.getUpdatedDatetime());
  }
  
  
  private void setShipping(ShippingContainer container, List<OrderDetail> orderDetails, List<CodeAttribute> slipNoSum, ShippingDetailBean reqBean) {
    setShippingHeader(container.getShippingHeader(), reqBean);
    setShippingDetail(container.getShippingDetails(), orderDetails, slipNoSum, reqBean);
  }

  private void setTmallShipping(ShippingContainer container, List<TmallOrderDetail> orderDetails, List<CodeAttribute> slipNoSum, ShippingDetailBean reqBean) {
    setTmallShippingHeader(container.getTmallShippingHeader(), reqBean);
    setTmallShippingDetail(container.getTmallShippingDetails(), orderDetails, slipNoSum, reqBean);
  }
  
  private void setJdShipping(ShippingContainer container, List<JdOrderDetail> orderDetails, List<CodeAttribute> slipNoSum, ShippingDetailBean reqBean) {
    setJdShippingHeader(container.getJdShippingHeader(), reqBean);
    setJdShippingDetail(container.getJdShippingDetails(), orderDetails, slipNoSum, reqBean);
  }
  
  private void setShippingHeader(ShippingHeader shippingHeader, ShippingDetailBean reqBean) {
    reqBean.getDeliveryHeader().setShippingNo(shippingHeader.getShippingNo());
    reqBean.getDeliveryHeader().setShippingDate(DateUtil.toDateString(shippingHeader.getShippingDate()));
    reqBean.getDeliveryHeader().setShippingDirectDate(DateUtil.toDateString(shippingHeader.getShippingDirectDate()));
    reqBean.getDeliveryHeader().setArrivalDate(DateUtil.toDateString(shippingHeader.getArrivalDate()));
    reqBean.getDeliveryHeader().setArrivalTimeStart(NumUtil.toString(shippingHeader.getArrivalTimeStart()));
    reqBean.getDeliveryHeader().setArrivalTimeEnd(NumUtil.toString(shippingHeader.getArrivalTimeEnd()));
    reqBean.getDeliveryHeader().setShippingHeaderUpdatedUser(shippingHeader.getUpdatedUser());
    reqBean.getDeliveryHeader().setShippingHeaderUpdatedDatetime(shippingHeader.getUpdatedDatetime());
    reqBean.getDeliveryHeader().setOriginalShippingNo(NumUtil.toString(shippingHeader.getOriginalShippingNo()));
    reqBean.getDeliveryHeader().setReturnItemDate(DateUtil.toDateString(shippingHeader.getReturnItemDate()));
    reqBean.getDeliveryHeader().setReturnItemLossMoney(NumUtil.toString(shippingHeader.getReturnItemLossMoney()));
    reqBean.getDeliveryHeader().setAddress1(shippingHeader.getAddress1());
    reqBean.getDeliveryHeader().setAddress2(shippingHeader.getAddress2());
    reqBean.getDeliveryHeader().setAddress3(shippingHeader.getAddress3());
    reqBean.getDeliveryHeader().setAddress4(shippingHeader.getAddress4());
    // 20111229 shen update start
    // reqBean.getDeliveryHeader().setDeliveryAppointedDate(DateUtil.toDateString(shippingHeader.getDeliveryAppointedDate()));
    reqBean.getDeliveryHeader().setDeliveryAppointedDate(shippingHeader.getDeliveryAppointedDate());
    // 20111229 shen update end
    reqBean.getDeliveryHeader().setDeliveryAppointedTimeEnd(NumUtil.toString(shippingHeader.getDeliveryAppointedTimeEnd()));
    reqBean.getDeliveryHeader().setDeliveryAppointedTimeStart(NumUtil.toString(shippingHeader.getDeliveryAppointedTimeStart()));
    reqBean.getDeliveryHeader().setDeliveryRemark(shippingHeader.getDeliveryRemark());
    reqBean.getDeliveryHeader().setDeliveryTel(shippingHeader.getPhoneNumber());
    //Add by V10-CH start
    reqBean.getDeliveryHeader().setDeliveryMobile(shippingHeader.getMobileNumber());
    //Add by V10-CH end
    reqBean.getDeliveryHeader().setDeliveryTypeName(shippingHeader.getDeliveryTypeName());
    reqBean.getDeliveryHeader().setDeliveryTypeNo(NumUtil.toString(shippingHeader.getDeliveryTypeNo()));
    reqBean.getDeliveryHeader().setFirstName(shippingHeader.getAddressFirstName());
    reqBean.getDeliveryHeader().setLastName(shippingHeader.getAddressLastName());
    reqBean.getDeliveryHeader().setFirstNameKana(shippingHeader.getAddressFirstNameKana());
    reqBean.getDeliveryHeader().setLastNameKana(shippingHeader.getAddressLastNameKana());
    reqBean.getDeliveryHeader().setPostalCode(shippingHeader.getPostalCode());
    reqBean.getDeliveryHeader().setPrefectureCode(shippingHeader.getPrefectureCode());
    reqBean.getDeliveryHeader().setShopCode(shippingHeader.getShopCode());
    reqBean.getDeliveryHeader().setShopName(getLoginInfo().getShopName());
    reqBean.getDeliveryHeader().setDeliveryRemark(shippingHeader.getDeliveryRemark());
    reqBean.getDeliveryHeader().setDeliverySlipNo(shippingHeader.getDeliverySlipNo());
    // 20120130 ysy add start
    reqBean.getDeliveryHeader().setDeliveryCompanyName(shippingHeader.getDeliveryCompanyName());
    reqBean.getDeliveryHeader().setDeliveryCompanyNo(shippingHeader.getDeliveryCompanyNo());
    // 20120130 ysy add end
    reqBean.getDeliveryHeader().setReturnItemType(NumUtil.toString(shippingHeader.getReturnItemType()));
    reqBean.getDeliveryHeader().setFixedSalesStatus(Long.toString(shippingHeader.getFixedSalesStatus()));

    String taxType = TaxType.INCLUDED.getValue();
    if (NumUtil.toString(shippingHeader.getReturnItemType()).equals(ReturnItemType.RETURNED.getValue())) {
      reqBean.getDeliveryHeader().setShippingStatus(ReturnItemType.RETURNED.getName());
      taxType = TaxType.NO_TAX.getValue();
    } else {
      for (ShippingStatus st : ShippingStatus.values()) {
        if (NumUtil.toString(shippingHeader.getShippingStatus()).equals(st.getValue())) {
          reqBean.getDeliveryHeader().setShippingStatus(st.getName());
        }
      }
    }
    reqBean.getDeliveryHeader().setDeliveryChargeTaxType(taxType);
    reqBean.getDeliveryHeader().setDeliveryCharge(
        NumUtil.toString(Price.getPriceIncludingTax(shippingHeader.getShippingCharge(), shippingHeader.getShippingChargeTaxRate(),
            taxType)));
  }

  private void setTmallShippingHeader(TmallShippingHeader shippingHeader, ShippingDetailBean reqBean) {
    reqBean.getDeliveryHeader().setShippingNo(shippingHeader.getShippingNo());
    reqBean.getDeliveryHeader().setShippingDate(DateUtil.toDateString(shippingHeader.getShippingDate()));
    reqBean.getDeliveryHeader().setShippingDirectDate(DateUtil.toDateString(shippingHeader.getShippingDirectDate()));
    reqBean.getDeliveryHeader().setArrivalDate(DateUtil.toDateString(shippingHeader.getArrivalDate()));
    reqBean.getDeliveryHeader().setArrivalTimeStart(NumUtil.toString(shippingHeader.getArrivalTimeStart()));
    reqBean.getDeliveryHeader().setArrivalTimeEnd(NumUtil.toString(shippingHeader.getArrivalTimeEnd()));
    reqBean.getDeliveryHeader().setShippingHeaderUpdatedUser(shippingHeader.getUpdatedUser());
    reqBean.getDeliveryHeader().setShippingHeaderUpdatedDatetime(shippingHeader.getUpdatedDatetime());
    reqBean.getDeliveryHeader().setOriginalShippingNo(shippingHeader.getOriginalShippingNo());
    reqBean.getDeliveryHeader().setReturnItemDate(DateUtil.toDateString(shippingHeader.getReturnItemDate()));
    reqBean.getDeliveryHeader().setReturnItemLossMoney(NumUtil.toString(shippingHeader.getReturnItemLossMoney()));
    reqBean.getDeliveryHeader().setAddress1(shippingHeader.getAddress1());
    reqBean.getDeliveryHeader().setAddress2(shippingHeader.getAddress2());
    reqBean.getDeliveryHeader().setAddress3(shippingHeader.getAddress3());
    reqBean.getDeliveryHeader().setAddress4(shippingHeader.getAddress4());
    // 20111229 shen update start
    // reqBean.getDeliveryHeader().setDeliveryAppointedDate(DateUtil.toDateString(shippingHeader.getDeliveryAppointedDate()));
    reqBean.getDeliveryHeader().setDeliveryAppointedDate(shippingHeader.getDeliveryAppointedDate());
    // 20111229 shen update end
    reqBean.getDeliveryHeader().setDeliveryAppointedTimeEnd(NumUtil.toString(shippingHeader.getDeliveryAppointedTimeEnd()));
    reqBean.getDeliveryHeader().setDeliveryAppointedTimeStart(NumUtil.toString(shippingHeader.getDeliveryAppointedTimeStart()));
    reqBean.getDeliveryHeader().setDeliveryRemark(shippingHeader.getDeliveryRemark());
    reqBean.getDeliveryHeader().setDeliveryTel(shippingHeader.getPhoneNumber());
    //Add by V10-CH start
    reqBean.getDeliveryHeader().setDeliveryMobile(shippingHeader.getMobileNumber());
    //Add by V10-CH end
    reqBean.getDeliveryHeader().setDeliveryTypeName(shippingHeader.getDeliveryTypeName());
    reqBean.getDeliveryHeader().setDeliveryTypeNo(NumUtil.toString(shippingHeader.getDeliveryTypeNo()));
    reqBean.getDeliveryHeader().setFirstName(shippingHeader.getAddressFirstName());
    reqBean.getDeliveryHeader().setLastName(shippingHeader.getAddressLastName());
    reqBean.getDeliveryHeader().setFirstNameKana(shippingHeader.getAddressFirstNameKana());
    reqBean.getDeliveryHeader().setLastNameKana(shippingHeader.getAddressLastNameKana());
    reqBean.getDeliveryHeader().setPostalCode(shippingHeader.getPostalCode());
    reqBean.getDeliveryHeader().setPrefectureCode(shippingHeader.getPrefectureCode());
    reqBean.getDeliveryHeader().setShopCode(shippingHeader.getShopCode());
    reqBean.getDeliveryHeader().setShopName(getLoginInfo().getShopName());
    reqBean.getDeliveryHeader().setDeliveryRemark(shippingHeader.getDeliveryRemark());
    reqBean.getDeliveryHeader().setDeliverySlipNo(shippingHeader.getDeliverySlipNo());
    // 20120130 ysy add start
    reqBean.getDeliveryHeader().setDeliveryCompanyName(shippingHeader.getDeliveryCompanyName());
    reqBean.getDeliveryHeader().setDeliveryCompanyNo(shippingHeader.getDeliveryCompanyNo());
    // 20120130 ysy add end
    reqBean.getDeliveryHeader().setReturnItemType(NumUtil.toString(shippingHeader.getReturnItemType()));
    reqBean.getDeliveryHeader().setFixedSalesStatus(Long.toString(shippingHeader.getFixedSalesStatus()));

    String taxType = TaxType.INCLUDED.getValue();
    if (NumUtil.toString(shippingHeader.getReturnItemType()).equals(ReturnItemType.RETURNED.getValue())) {
      reqBean.getDeliveryHeader().setShippingStatus(ReturnItemType.RETURNED.getName());
      taxType = TaxType.NO_TAX.getValue();
    } else {
      for (ShippingStatus st : ShippingStatus.values()) {
        if (NumUtil.toString(shippingHeader.getShippingStatus()).equals(st.getValue())) {
          reqBean.getDeliveryHeader().setShippingStatus(st.getName());
        }
      }
    }
    reqBean.getDeliveryHeader().setDeliveryChargeTaxType(taxType);
    reqBean.getDeliveryHeader().setDeliveryCharge(
        NumUtil.toString(Price.getPriceIncludingTax(shippingHeader.getShippingCharge(), shippingHeader.getShippingChargeTaxRate(),
            taxType)));
  }
  

  private void setJdShippingHeader(JdShippingHeader shippingHeader, ShippingDetailBean reqBean) {
    reqBean.getDeliveryHeader().setShippingNo(shippingHeader.getShippingNo());
    reqBean.getDeliveryHeader().setShippingDate(DateUtil.toDateString(shippingHeader.getShippingDate()));
    reqBean.getDeliveryHeader().setShippingDirectDate(DateUtil.toDateString(shippingHeader.getShippingDirectDate()));
    reqBean.getDeliveryHeader().setArrivalDate(DateUtil.toDateString(shippingHeader.getArrivalDate()));
    reqBean.getDeliveryHeader().setArrivalTimeStart(NumUtil.toString(shippingHeader.getArrivalTimeStart()));
    reqBean.getDeliveryHeader().setArrivalTimeEnd(NumUtil.toString(shippingHeader.getArrivalTimeEnd()));
    reqBean.getDeliveryHeader().setShippingHeaderUpdatedUser(shippingHeader.getUpdatedUser());
    reqBean.getDeliveryHeader().setShippingHeaderUpdatedDatetime(shippingHeader.getUpdatedDatetime());
    if(shippingHeader.getOriginalShippingNo() != null) {
      reqBean.getDeliveryHeader().setOriginalShippingNo(shippingHeader.getOriginalShippingNo().toString());
    }
    reqBean.getDeliveryHeader().setReturnItemDate(DateUtil.toDateString(shippingHeader.getReturnItemDate()));
    reqBean.getDeliveryHeader().setReturnItemLossMoney(NumUtil.toString(shippingHeader.getReturnItemLossMoney()));
    reqBean.getDeliveryHeader().setAddress1(shippingHeader.getAddress1());
    reqBean.getDeliveryHeader().setAddress2(shippingHeader.getAddress2());
    reqBean.getDeliveryHeader().setAddress3(shippingHeader.getAddress3());
    reqBean.getDeliveryHeader().setAddress4(shippingHeader.getAddress4());
    // 20111229 shen update start
    // reqBean.getDeliveryHeader().setDeliveryAppointedDate(DateUtil.toDateString(shippingHeader.getDeliveryAppointedDate()));
    reqBean.getDeliveryHeader().setDeliveryAppointedDate(shippingHeader.getDeliveryAppointedDate());
    // 20111229 shen update end
    reqBean.getDeliveryHeader().setDeliveryAppointedTimeEnd(NumUtil.toString(shippingHeader.getDeliveryAppointedTimeEnd()));
    reqBean.getDeliveryHeader().setDeliveryAppointedTimeStart(NumUtil.toString(shippingHeader.getDeliveryAppointedTimeStart()));
    reqBean.getDeliveryHeader().setDeliveryRemark(shippingHeader.getDeliveryRemark());
    reqBean.getDeliveryHeader().setDeliveryTel(shippingHeader.getPhoneNumber());
    //Add by V10-CH start
    reqBean.getDeliveryHeader().setDeliveryMobile(shippingHeader.getMobileNumber());
    //Add by V10-CH end
    reqBean.getDeliveryHeader().setDeliveryTypeName(shippingHeader.getDeliveryTypeName());
    reqBean.getDeliveryHeader().setDeliveryTypeNo(NumUtil.toString(shippingHeader.getDeliveryTypeNo()));
    reqBean.getDeliveryHeader().setFirstName(shippingHeader.getAddressFirstName());
    reqBean.getDeliveryHeader().setLastName(shippingHeader.getAddressLastName());
    reqBean.getDeliveryHeader().setFirstNameKana(shippingHeader.getAddressFirstNameKana());
    reqBean.getDeliveryHeader().setLastNameKana(shippingHeader.getAddressLastNameKana());
    reqBean.getDeliveryHeader().setPostalCode(shippingHeader.getPostalCode());
    reqBean.getDeliveryHeader().setPrefectureCode(shippingHeader.getPrefectureCode());
    reqBean.getDeliveryHeader().setShopCode(shippingHeader.getShopCode());
    reqBean.getDeliveryHeader().setShopName(getLoginInfo().getShopName());
    reqBean.getDeliveryHeader().setDeliveryRemark(shippingHeader.getDeliveryRemark());
    reqBean.getDeliveryHeader().setDeliverySlipNo(shippingHeader.getDeliverySlipNo());
    // 20120130 ysy add start
    reqBean.getDeliveryHeader().setDeliveryCompanyName(shippingHeader.getDeliveryCompanyName());
    reqBean.getDeliveryHeader().setDeliveryCompanyNo(shippingHeader.getDeliveryCompanyNo());
    // 20120130 ysy add end
    reqBean.getDeliveryHeader().setReturnItemType(NumUtil.toString(shippingHeader.getReturnItemType()));
    reqBean.getDeliveryHeader().setFixedSalesStatus(Long.toString(shippingHeader.getFixedSalesStatus()));

    String taxType = TaxType.INCLUDED.getValue();
    if (NumUtil.toString(shippingHeader.getReturnItemType()).equals(ReturnItemType.RETURNED.getValue())) {
      reqBean.getDeliveryHeader().setShippingStatus(ReturnItemType.RETURNED.getName());
      taxType = TaxType.NO_TAX.getValue();
    } else {
      for (ShippingStatus st : ShippingStatus.values()) {
        if (NumUtil.toString(shippingHeader.getShippingStatus()).equals(st.getValue())) {
          reqBean.getDeliveryHeader().setShippingStatus(st.getName());
        }
      }
    }
    reqBean.getDeliveryHeader().setDeliveryChargeTaxType(taxType);
    reqBean.getDeliveryHeader().setDeliveryCharge(
        NumUtil.toString(Price.getPriceIncludingTax(shippingHeader.getShippingCharge(), shippingHeader.getShippingChargeTaxRate(),
            taxType)));
  }
  
  private void setShippingDetail(List<ShippingDetail> shippingDetail, List<OrderDetail> orderDetails, List<CodeAttribute> shipNoSum, ShippingDetailBean reqBean) {
    List<DeliveryDetailBean> detailList = new ArrayList<DeliveryDetailBean>();

    String taxType = TaxType.INCLUDED.getValue();
    if (reqBean.getDeliveryHeader().getReturnItemType().equals(ReturnItemType.RETURNED.getValue())) {
      taxType = TaxType.NO_TAX.getValue();
    }

    for (ShippingDetail sd : shippingDetail) {
      DeliveryDetailBean detail = new DeliveryDetailBean();

      BigDecimal retailPrice = BigDecimal.ZERO;
      for (OrderDetail od : orderDetails) {
        if (sd.getShopCode().equals(od.getShopCode()) && sd.getSkuCode().equals(od.getSkuCode())) {
          detail.setComodityName(od.getCommodityName());
          detail.setStandardDetail1Name(od.getStandardDetail1Name());
          detail.setStandardDetail2Name(od.getStandardDetail2Name());
          detail.setCommodityTaxType(taxType);
          retailPrice = sd.getRetailPrice();
          detail.setRetailPrice(NumUtil.toString(retailPrice));
        }
      }
      // 20120130 ysy add start
      for (CodeAttribute nv : shipNoSum) {
    	  if (sd.getSkuCode().equals(nv.getName())) {
    		  detail.setDeliveryShipNoSum(nv.getValue());
    	  }
      }
      // 20120130 ysy add end

      detail.setShippingDetailNo(NumUtil.toString(sd.getShippingDetailNo()));
      detail.setSkuCode(sd.getSkuCode());
      detail.setRetailTaxPrice(NumUtil.toString(sd.getRetailTax()));
      detail.setPurchasingAmount(NumUtil.toString(sd.getPurchasingAmount()));
      detail.setDetailGiftCode(sd.getGiftCode());
      if (StringUtil.isNullOrEmpty(sd.getGiftName())) {
        detail.setDetailGiftName(Messages.getString("web.action.back.order.ShippingDetailInitAction.0"));
      } else {
        detail.setDetailGiftName(sd.getGiftName());
      }
      detail.setDetailGiftPrice(NumUtil.toString(sd.getGiftPrice()));
      detail.setDetailGiftTaxType(taxType);
      detail.setDetailGiftTaxRate(NumUtil.toString(sd.getGiftTaxRate()));

      BigDecimal subTotal = BigDecimal.ZERO;
      if (StringUtil.isNullOrEmpty(sd.getGiftCode())) {
        //modify by V10-CH start
        //subTotal = retailPrice * sd.getPurchasingAmount();
        subTotal = BigDecimalUtil.multiply(retailPrice, sd.getPurchasingAmount());
        //modify by V10-CH end
      } else {
        //modify by V10-CH start
//        subTotal = (retailPrice * sd.getPurchasingAmount())
//            + Price.getPriceIncludingTax(sd.getGiftPrice(), sd.getGiftTaxRate(), taxType);
        subTotal = BigDecimalUtil.multiply(retailPrice, sd.getPurchasingAmount()).add(
            Price.getPriceIncludingTax(sd.getGiftPrice(), sd.getGiftTaxRate(), taxType));
        //modify by V10-CH end
      }
      detail.setDetailSummaryPrice(NumUtil.toString(subTotal));
      detailList.add(detail);
    }

    reqBean.setDeliveryDetailList(detailList);
  }

  private void setTmallShippingDetail(List<TmallShippingDetail> shippingDetail, List<TmallOrderDetail> orderDetails, List<CodeAttribute> shipNoSum, ShippingDetailBean reqBean) {
    List<DeliveryDetailBean> detailList = new ArrayList<DeliveryDetailBean>();

    String taxType = TaxType.INCLUDED.getValue();
    if (reqBean.getDeliveryHeader().getReturnItemType().equals(ReturnItemType.RETURNED.getValue())) {
      taxType = TaxType.NO_TAX.getValue();
    }

    for (TmallShippingDetail sd : shippingDetail) {
      DeliveryDetailBean detail = new DeliveryDetailBean();

      BigDecimal retailPrice = BigDecimal.ZERO;
      for (TmallOrderDetail od : orderDetails) {
        if (sd.getShopCode().equals(od.getShopCode()) && sd.getSkuCode().equals(od.getSkuCode())) {
          detail.setComodityName(od.getCommodityName());
          detail.setStandardDetail1Name(od.getStandardDetail1Name());
          detail.setStandardDetail2Name(od.getStandardDetail2Name());
          detail.setCommodityTaxType(taxType);
          retailPrice = sd.getRetailPrice();
          detail.setRetailPrice(NumUtil.toString(retailPrice));
        }
      }
      // 20120130 ysy add start
      for (CodeAttribute nv : shipNoSum) {
        if (sd.getSkuCode().equals(nv.getName())) {
          detail.setDeliveryShipNoSum(nv.getValue());
        }
      }
      // 20120130 ysy add end

      detail.setShippingDetailNo(NumUtil.toString(sd.getShippingDetailNo()));
      detail.setSkuCode(sd.getSkuCode());
      detail.setRetailTaxPrice(NumUtil.toString(sd.getRetailTax()));
      detail.setPurchasingAmount(NumUtil.toString(sd.getPurchasingAmount()));
      detail.setDetailGiftCode(sd.getGiftCode());
      if (StringUtil.isNullOrEmpty(sd.getGiftName())) {
        detail.setDetailGiftName(Messages.getString("web.action.back.order.ShippingDetailInitAction.0"));
      } else {
        detail.setDetailGiftName(sd.getGiftName());
      }
      detail.setDetailGiftPrice(NumUtil.toString(sd.getGiftPrice()));
      detail.setDetailGiftTaxType(taxType);
      detail.setDetailGiftTaxRate(NumUtil.toString(sd.getGiftTaxRate()));

      BigDecimal subTotal = BigDecimal.ZERO;
      if (StringUtil.isNullOrEmpty(sd.getGiftCode())) {
        //modify by V10-CH start
        //subTotal = retailPrice * sd.getPurchasingAmount();
        subTotal = BigDecimalUtil.multiply(retailPrice, sd.getPurchasingAmount());
        //modify by V10-CH end
      } else {
        //modify by V10-CH start
//        subTotal = (retailPrice * sd.getPurchasingAmount())
//            + Price.getPriceIncludingTax(sd.getGiftPrice(), sd.getGiftTaxRate(), taxType);
        subTotal = BigDecimalUtil.multiply(retailPrice, sd.getPurchasingAmount()).add(
            Price.getPriceIncludingTax(sd.getGiftPrice(), sd.getGiftTaxRate(), taxType));
        //modify by V10-CH end
      }
      detail.setDetailSummaryPrice(NumUtil.toString(subTotal));
      detailList.add(detail);
    }

    reqBean.setDeliveryDetailList(detailList);
  }
  
  private void setJdShippingDetail(List<JdShippingDetail> shippingDetail, List<JdOrderDetail> orderDetails, List<CodeAttribute> shipNoSum, ShippingDetailBean reqBean) {
    List<DeliveryDetailBean> detailList = new ArrayList<DeliveryDetailBean>();

    String taxType = TaxType.INCLUDED.getValue();
    if (reqBean.getDeliveryHeader().getReturnItemType().equals(ReturnItemType.RETURNED.getValue())) {
      taxType = TaxType.NO_TAX.getValue();
    }

    for (JdShippingDetail sd : shippingDetail) {
      DeliveryDetailBean detail = new DeliveryDetailBean();

      BigDecimal retailPrice = BigDecimal.ZERO;
      for (JdOrderDetail od : orderDetails) {
        if (sd.getShopCode().equals(od.getShopCode()) && sd.getSkuCode().equals(od.getSkuCode())) {
          detail.setComodityName(od.getCommodityName());
          detail.setStandardDetail1Name(od.getStandardDetail1Name());
          detail.setStandardDetail2Name(od.getStandardDetail2Name());
          detail.setCommodityTaxType(taxType);
          retailPrice = sd.getRetailPrice();
          detail.setRetailPrice(NumUtil.toString(retailPrice));
        }
      }
      // 20120130 ysy add start
      for (CodeAttribute nv : shipNoSum) {
        if (sd.getSkuCode().equals(nv.getName())) {
          detail.setDeliveryShipNoSum(nv.getValue());
        }
      }
      // 20120130 ysy add end

      detail.setShippingDetailNo(NumUtil.toString(sd.getShippingDetailNo()));
      detail.setSkuCode(sd.getSkuCode());
      detail.setRetailTaxPrice(NumUtil.toString(sd.getRetailTax()));
      detail.setPurchasingAmount(NumUtil.toString(sd.getPurchasingAmount()));
      detail.setDetailGiftCode(sd.getGiftCode());
      if (StringUtil.isNullOrEmpty(sd.getGiftName())) {
        detail.setDetailGiftName(Messages.getString("web.action.back.order.ShippingDetailInitAction.0"));
      } else {
        detail.setDetailGiftName(sd.getGiftName());
      }
      detail.setDetailGiftPrice(NumUtil.toString(sd.getGiftPrice()));
      detail.setDetailGiftTaxType(taxType);
      detail.setDetailGiftTaxRate(NumUtil.toString(sd.getGiftTaxRate()));

      BigDecimal subTotal = BigDecimal.ZERO;
      if (StringUtil.isNullOrEmpty(sd.getGiftCode())) {
        //modify by V10-CH start
        //subTotal = retailPrice * sd.getPurchasingAmount();
        subTotal = BigDecimalUtil.multiply(retailPrice, sd.getPurchasingAmount());
        //modify by V10-CH end
      } else {
        //modify by V10-CH start
//        subTotal = (retailPrice * sd.getPurchasingAmount())
//            + Price.getPriceIncludingTax(sd.getGiftPrice(), sd.getGiftTaxRate(), taxType);
        subTotal = BigDecimalUtil.multiply(retailPrice, sd.getPurchasingAmount()).add(
            Price.getPriceIncludingTax(sd.getGiftPrice(), sd.getGiftTaxRate(), taxType));
        //modify by V10-CH end
      }
      detail.setDetailSummaryPrice(NumUtil.toString(subTotal));
      detailList.add(detail);
    }

    reqBean.setDeliveryDetailList(detailList);
  }
  
  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

    if (StringUtil.hasValue(getNextUrl())) {
      if (getNextUrl().equals("/app/common/login")) {
        return;
      }
    }

    ShippingDetailBean reqBean = (ShippingDetailBean) getBean();
    BackLoginInfo login = getLoginInfo();

    // 受注タイプを取得する
    String shippingType = shippingNo.substring(0,1);
    
    // ECの場合
    if (!(EMALL_SHIP_TYPE.equals(shippingType) || JD_SHIP_TYPE.equals(shippingType))) {
      boolean displayFlg = false;
      if (reqBean.getDeliveryHeader().getReturnItemType().equals(ReturnItemType.ORDERED.getValue())
          && (Permission.SHIPPING_UPDATE_SITE.isGranted(login) || Permission.SHIPPING_UPDATE_SHOP.isGranted(login))) {
        displayFlg = true;
      } else {
        displayFlg = false;
      }

      if (getConfig().getOperatingMode().equals(OperatingMode.MALL)) {
        reqBean.setSiteOperationFlg(true);
      } else {
        reqBean.setSiteOperationFlg(false);
      }

      CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
      boolean isWithdrawed = customerService.isWithdrawed(reqBean.getOrderHeader().getCustomerCode());

      // データ連携済み/売上確定済み/顧客退会済みの場合は変更不可

      if (reqBean.getOrderHeader().getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.getValue())
          || reqBean.getDeliveryHeader().getFixedSalesStatus().equals(FixedSalesStatus.FIXED.getValue())
          || (CustomerConstant.isCustomer(reqBean.getOrderHeader().getCustomerCode()) && isWithdrawed)) {
        displayFlg = false;
      } else {
        displayFlg &= true;
      }

      if (displayFlg) {
        reqBean.setUpdateEditMode(WebConstantCode.DISPLAY_EDIT);
      } else {
        reqBean.setUpdateEditMode(WebConstantCode.DISPLAY_READONLY);
      }

      // データ連携済みの場合はメッセージを表示する
      if (reqBean.getOrderHeader().getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.getValue())) {
        addInformationMessage(WebMessage.get(InformationMessage.DATA_TRANSPORTED));
      }

      reqBean.setDisplayFlg(displayFlg);

      OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
      ShippingHeader shippingHeader = orderService.getShippingHeader(reqBean.getDeliveryHeader().getShippingNo());

      // 出荷済みの場合、出荷指示部は指示日：テキスト表示、チェックボックス・ボタン非表示
      if (shippingHeader.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())) {
        reqBean.setDisplayDirectFlg(false);
        reqBean.setUpdateDirectEditMode(WebConstantCode.DISPLAY_READONLY);
      } else {
        reqBean.setDisplayDirectFlg(displayFlg);
        reqBean.setUpdateDirectEditMode(reqBean.getUpdateEditMode());
      }

      // キャンセル済の場合は出荷実績登録部を非表示

      if (shippingHeader.getShippingStatus().equals(ShippingStatus.CANCELLED.longValue())) {
        reqBean.setShippingReportDisplayFlg(false);
      } else {
        reqBean.setShippingReportDisplayFlg(true);
      }

      String[] param = getRequestParameter().getPathArgs();
      String completeParam = "";
      if (param.length == 2) {
        completeParam = param[1];
      }

      if (completeParam.equals(WebConstantCode.COMPLETE_UPDATE)) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
            .getString("web.action.back.order.ShippingDetailInitAction.1")));
      }

      if (completeParam.equals(WebConstantCode.COMPLETE_REGISTER)) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
            .getString("web.action.back.order.ShippingDetailInitAction.2")));
      } else if (completeParam.equals(WebConstantCode.COMPLETE_UPDATE + "_shipping_direct_date")) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
            .getString("web.action.back.order.ShippingDetailInitAction.3")));
      }
    //TMALLの場合
    } else if(EMALL_SHIP_TYPE.equals(shippingType)) {
      boolean displayFlg = false;
      if (reqBean.getDeliveryHeader().getReturnItemType().equals(ReturnItemType.ORDERED.getValue())
          && (Permission.SHIPPING_UPDATE_SITE.isGranted(login) || Permission.SHIPPING_UPDATE_SHOP.isGranted(login))) {
        displayFlg = true;
      } else {
        displayFlg = false;
      }

      if (getConfig().getOperatingMode().equals(OperatingMode.MALL)) {
        reqBean.setSiteOperationFlg(true);
      } else {
        reqBean.setSiteOperationFlg(false);
      }

      CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
      boolean isWithdrawed = customerService.isWithdrawed(reqBean.getOrderHeader().getCustomerCode());

      // データ連携済み/売上確定済み/顧客退会済みの場合は変更不可

      if (reqBean.getOrderHeader().getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.getValue())
          || reqBean.getDeliveryHeader().getFixedSalesStatus().equals(FixedSalesStatus.FIXED.getValue())
          || (CustomerConstant.isCustomer(reqBean.getOrderHeader().getCustomerCode()) && isWithdrawed)) {
        displayFlg = false;
      } else {
        displayFlg &= true;
      }

      if (displayFlg) {
        reqBean.setUpdateEditMode(WebConstantCode.DISPLAY_EDIT);
      } else {
        reqBean.setUpdateEditMode(WebConstantCode.DISPLAY_READONLY);
      }

      // データ連携済みの場合はメッセージを表示する
      if (reqBean.getOrderHeader().getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.getValue())) {
        addInformationMessage(WebMessage.get(InformationMessage.DATA_TRANSPORTED));
      }

      reqBean.setDisplayFlg(displayFlg);

      OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
      TmallShippingHeader shippingHeader = orderService.getTmallShippingHeader(reqBean.getDeliveryHeader().getShippingNo());

      // 出荷済みの場合、出荷指示部は指示日：テキスト表示、チェックボックス・ボタン非表示
      if (shippingHeader.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())) {
        reqBean.setDisplayDirectFlg(false);
        reqBean.setUpdateDirectEditMode(WebConstantCode.DISPLAY_READONLY);
      } else {
        reqBean.setDisplayDirectFlg(displayFlg);
        reqBean.setUpdateDirectEditMode(reqBean.getUpdateEditMode());
      }

      // キャンセル済の場合は出荷実績登録部を非表示

      if (shippingHeader.getShippingStatus().equals(ShippingStatus.CANCELLED.longValue())) {
        reqBean.setShippingReportDisplayFlg(false);
      } else {
        reqBean.setShippingReportDisplayFlg(true);
      }

      String[] param = getRequestParameter().getPathArgs();
      String completeParam = "";
      if (param.length == 2) {
        completeParam = param[1];
      }

      if (completeParam.equals(WebConstantCode.COMPLETE_UPDATE)) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
            .getString("web.action.back.order.ShippingDetailInitAction.1")));
      }

      if (completeParam.equals(WebConstantCode.COMPLETE_REGISTER)) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
            .getString("web.action.back.order.ShippingDetailInitAction.2")));
      } else if (completeParam.equals(WebConstantCode.COMPLETE_UPDATE + "_shipping_direct_date")) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
            .getString("web.action.back.order.ShippingDetailInitAction.3")));
      }
      //JDの場合
    } else {
      boolean displayFlg = false;
      if (reqBean.getDeliveryHeader().getReturnItemType().equals(ReturnItemType.ORDERED.getValue())
          && (Permission.SHIPPING_UPDATE_SITE.isGranted(login) || Permission.SHIPPING_UPDATE_SHOP.isGranted(login))) {
        displayFlg = true;
      } else {
        displayFlg = false;
      }

      if (getConfig().getOperatingMode().equals(OperatingMode.MALL)) {
        reqBean.setSiteOperationFlg(true);
      } else {
        reqBean.setSiteOperationFlg(false);
      }

      CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
      boolean isWithdrawed = customerService.isWithdrawed(reqBean.getOrderHeader().getCustomerCode());

      // データ連携済み/売上確定済み/顧客退会済みの場合は変更不可

      if (reqBean.getOrderHeader().getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.getValue())
          || reqBean.getDeliveryHeader().getFixedSalesStatus().equals(FixedSalesStatus.FIXED.getValue())
          || (CustomerConstant.isCustomer(reqBean.getOrderHeader().getCustomerCode()) && isWithdrawed)) {
        displayFlg = false;
      } else {
        displayFlg &= true;
      }

      if (displayFlg) {
        reqBean.setUpdateEditMode(WebConstantCode.DISPLAY_EDIT);
      } else {
        reqBean.setUpdateEditMode(WebConstantCode.DISPLAY_READONLY);
      }

      // データ連携済みの場合はメッセージを表示する
      if (reqBean.getOrderHeader().getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.getValue())) {
        addInformationMessage(WebMessage.get(InformationMessage.DATA_TRANSPORTED));
      }

      reqBean.setDisplayFlg(displayFlg);

      OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
      JdShippingHeader shippingHeader = orderService.getJdShippingHeader(reqBean.getDeliveryHeader().getShippingNo());

      // 出荷済みの場合、出荷指示部は指示日：テキスト表示、チェックボックス・ボタン非表示
      if (shippingHeader.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())) {
        reqBean.setDisplayDirectFlg(false);
        reqBean.setUpdateDirectEditMode(WebConstantCode.DISPLAY_READONLY);
      } else {
        reqBean.setDisplayDirectFlg(displayFlg);
        reqBean.setUpdateDirectEditMode(reqBean.getUpdateEditMode());
      }

      // キャンセル済の場合は出荷実績登録部を非表示

      if (shippingHeader.getShippingStatus().equals(ShippingStatus.CANCELLED.longValue())) {
        reqBean.setShippingReportDisplayFlg(false);
      } else {
        reqBean.setShippingReportDisplayFlg(true);
      }

      String[] param = getRequestParameter().getPathArgs();
      String completeParam = "";
      if (param.length == 2) {
        completeParam = param[1];
      }

      if (completeParam.equals(WebConstantCode.COMPLETE_UPDATE)) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
            .getString("web.action.back.order.ShippingDetailInitAction.1")));
      }

      if (completeParam.equals(WebConstantCode.COMPLETE_REGISTER)) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
            .getString("web.action.back.order.ShippingDetailInitAction.2")));
      } else if (completeParam.equals(WebConstantCode.COMPLETE_UPDATE + "_shipping_direct_date")) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
            .getString("web.action.back.order.ShippingDetailInitAction.3")));
      }

    }
    
    
    setRequestBean(reqBean);

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.ShippingDetailInitAction.4");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102042001";
  }

}
