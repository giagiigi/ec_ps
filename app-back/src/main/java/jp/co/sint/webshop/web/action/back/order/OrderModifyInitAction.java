package jp.co.sint.webshop.web.action.back.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.DeliverySpecificationType;
import jp.co.sint.webshop.data.domain.InvoiceType;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.data.dto.CustomerVatInvoice;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.OrderInvoice;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingDetailComposition;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.campain.CampaignInfo;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.OrderSummary;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.PaymentMethodBean;
import jp.co.sint.webshop.web.bean.PaymentMethodBean.PaymentTypeBase;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.AddCommodityBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.CouponBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.InvoiceBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.OrderHeaderBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.PaymentAddress;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.PointBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.SetCommpositionInfo;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingAddress;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.TotalPrice;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean.ShippingDetailBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PaymentSupporter;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1020230:受注修正のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderModifyInitAction extends OrderModifyBaseAction {

  private OrderContainer modifyOrder = null;

  /**
   * 初期処理を実行します。
   */
  @Override
  public void init() {
    String orderNo = getPathInfo(0);
    if (StringUtil.hasValue(orderNo)) {
      OrderService service = ServiceLocator.getOrderService(getLoginInfo());
      modifyOrder = service.getOrder(orderNo);
    } else if (StringUtil.hasValue(getBean().getOrderNo())) {
      OrderService service = ServiceLocator.getOrderService(getLoginInfo());
      modifyOrder = service.getOrder(getBean().getOrderNo());
    }
    setBean(new OrderModifyBean());
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    if (modifyOrder == null) {
      return false;
    }
    OrderModifyBean bean = getBean();
    bean.setShopCode(modifyOrder.getOrderHeader().getShopCode());
    bean.setOrderNo(modifyOrder.getOrderHeader().getOrderNo());
    return super.authorize();
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = true;

    // 未入金以外は修正不可
    if (PaymentStatus.fromValue(modifyOrder.getOrderHeader().getPaymentStatus()) != PaymentStatus.NOT_PAID) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.STILL_PAID));
      valid = false;
    }
    // 出荷ステータスサマリーが未出荷以外はエラー
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    OrderSummary summary = service.getOrderSummary(modifyOrder.getOrderHeader().getOrderNo());
    if (ShippingStatusSummary.fromValue(summary.getShippingStatusSummary()) != ShippingStatusSummary.NOT_SHIPPED) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.STILL_SHIPPING_ORDER));
      valid = false;
    }

    // クレジットカードの受注修正は不可
    String paymentMethodType = modifyOrder.getOrderHeader().getPaymentMethodType();
    if (paymentMethodType.equals(PaymentMethodType.CREDITCARD.getValue())) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.MODIFY_CREDIT_ERROR));
      valid = false;
    }
    // validationエラー時の画面フラグ制御
    OrderModifyBean bean = getBean();
    if (valid) {
      bean.setDisplayMode(WebConstantCode.DISPLAY_EDIT);
      bean.setDisplayUpdateButton(true);
    } else {
      bean.setDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
      bean.setDisplayUpdateButton(false);
      bean.setOrderNo(modifyOrder.getOrderHeader().getOrderNo());
      // Validationエラーでも画面項目を表示する
      callService();
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

    // 受注顧客情報取得
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerInfo customerInfo = null;
    String customerCode = modifyOrder.getOrderHeader().getCustomerCode();
    if (CustomerConstant.isCustomer(customerCode)) {
      customerInfo = customerService.getCustomer(modifyOrder.getOrderHeader().getCustomerCode());
    } else {
      // ゲストの場合、空の顧客情報を生成する
      customerInfo = new CustomerInfo();
      Customer customer = new Customer();
      customer.setCustomerCode(customerCode);
      customerInfo.setCustomer(customer);
    }

    // 受注状況チェック
    bean.setReserveFlg(modifyOrder.getOrderHeader().getOrderStatus().equals(OrderStatus.RESERVED.longValue()));
    bean.setPhantomOrder(modifyOrder.getOrderHeader().getOrderStatus().equals(OrderStatus.PHANTOM_ORDER.longValue()));
    bean.setPhantomReservation(modifyOrder.getOrderHeader().getOrderStatus().equals(OrderStatus.PHANTOM_RESERVATION.longValue()));
    // 旧受注情報の保持
    bean.setOldOrderContainer(modifyOrder);
    // 受注ヘッダ情報生成
    copyOrderHeaderToBean(bean, modifyOrder.getOrderHeader(), customerInfo);
    // 金額情報生成
    copyPriceToBean(bean, modifyOrder.getOrderHeader().getOrderNo());
    // 出荷情報設定
    copyShippingToBean(bean, modifyOrder.getShippings());
    // 商品追加情報設定
    copyCommodityToBean(bean);
    // add by v10-ch start
    copyCouponToBean(bean, modifyOrder.getOrderHeader(), customerInfo.getCustomer());
    // add by v10-ch end
    // ポイント情報設定
    copyPointInfoToBean(bean, modifyOrder.getOrderHeader(), customerInfo.getCustomer());
    // 支払情報設定
    copyPaymentInfoToBean(bean, modifyOrder.getOrderHeader());

    bean.setOrderInvoice(getOrderInvoice(modifyOrder.getOrderHeader().getOrderNo(), modifyOrder.getOrderHeader().getCustomerCode()));
    super.initDiscount(modifyOrder.getOrderHeader(), bean.getOrgCashierDiscount());
    bean.getBeforeTotalPrice().setDiscountPrice(bean.getOrgCashierDiscount().getDiscountPrice());
    bean.getAfterTotalPrice().setDiscountPrice(bean.getOrgCashierDiscount().getDiscountPrice());
    if (bean.getOrgCashierDiscount().getDiscountPrice()!=null) {
      bean.setUseOrgDiscount("1");
    }
    if (!super.authorize()) {
      bean.setDisplayUpdateButton(false);
    }

    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    bean.getOrderHeaderEdit().getAddress().setCityList(s.getCityNames(bean.getOrderHeaderEdit().getAddress().getPrefectureCode()));
    for (ShippingHeaderBean sh : bean.getShippingHeaderList()) {
      sh.getAddress().setCityList(s.getCityNames(sh.getAddress().getPrefectureCode()));
    }

    super.setAddressList(bean);

    if (PaymentMethodType.CASH_ON_DELIVERY.getValue().equals(modifyOrder.getOrderHeader().getPaymentMethodType())) {
    	bean.setUpadatePaymentFlg(true);
    }
    //原订单商品SKU单位的引当数合计
    initOrgStockInfo(bean);
    //赠品信息的再计算
    recomputeGift(bean, true);
    boolean bool = true;
    String[] tmpArgs = getRequestParameter().getPathArgs();
    for (String string : tmpArgs) {
      if (string.equals("update")) {
        bool = false;
      }
    }
    if (bool) {
      this.recomputeShippingCharge(bean);
    }
    recomputePrice(bean);
    recomputePoint(bean);
    recomputePaymentCommission(bean);
    recomputePrice(bean);
    
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  private void initOrgStockInfo(OrderModifyBean bean) {
	  List<String> skuList = new ArrayList<String>();
	  Map<String,Long> orgStockMap = new HashMap<String,Long>();
	  for (ShippingContainer shippingContainer : bean.getOldOrderContainer().getShippings()) {
		  for (ShippingDetail detail : shippingContainer.getShippingDetails()) {
			  if (skuList.contains(detail.getSkuCode())) {
				  orgStockMap.put(detail.getSkuCode(), orgStockMap.get(detail.getSkuCode())+detail.getPurchasingAmount());
			  } else {
				  skuList.add(detail.getSkuCode());
				  orgStockMap.put(detail.getSkuCode(), detail.getPurchasingAmount());
			  }
			  if (SetCommodityFlg.OBJECTIN.longValue().equals(detail.getSetCommodityFlg())) {
				  for (ShippingDetailComposition detailComposition : shippingContainer.getShippingDetailContainerMap().get(detail)) {
						  if (skuList.contains(detailComposition.getChildSkuCode())) {
							  orgStockMap.put(detailComposition.getChildSkuCode(), orgStockMap.get(detailComposition.getChildSkuCode())+detail.getPurchasingAmount());
						  } else {
							  skuList.add(detailComposition.getChildSkuCode());
							  orgStockMap.put(detailComposition.getChildSkuCode(), detail.getPurchasingAmount());
					  }
				  }
			  }
		  }
	  }
	  bean.setOrgStockMap(orgStockMap);
  }
  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    OrderModifyBean bean = (OrderModifyBean) getRequestBean();

    bean.setOperationMode("shipping");
    bean.setDisplayMode(WebConstantCode.DISPLAY_EDIT);
    // データ連携中チェック
    DataTransportStatus transportStatus = DataTransportStatus.fromValue(modifyOrder.getOrderHeader().getDataTransportStatus());
    if (transportStatus == DataTransportStatus.TRANSPORTED) {
      // データ連携済み
      bean.setDisplayUpdateButton(false);
      addInformationMessage(WebMessage.get(OrderErrorMessage.DATA_TRANSPORTED));
    } else if (transportStatus == DataTransportStatus.NOT_TRANSPORTED) {
      bean.setDisplayUpdateButton(bean.isDisplayUpdateButton());
    } else {
      throw new RuntimeException();
    }

    String complete = getPathInfo(1);
    if (StringUtil.hasValue(complete)) {
      if (complete.equals(WebConstantCode.COMPLETE_UPDATE)) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
            .getString("web.action.back.order.OrderModifyInitAction.0")));
        addInformationMessage(WebMessage.get(CompleteMessage.SEND_MODIFY_MAIL_COMPLETE,
            bean.getOrderHeaderEdit().getCustomerCode(), bean.getOrderHeaderEdit().getAddress().getLastName(), bean
                .getOrderHeaderEdit().getAddress().getFirstName()));
      }
    }
    if (getDisplayMessage().getErrors().size() > 0) {
      getDisplayMessage().getErrors().clear();
    }

    setRequestBean(bean);
  }

  /**
   * 商品追加情報生成。<BR>
   * 出荷先が増える商品の追加は不可とする為<BR>
   * 配送先及びショップ情報は新規受注で使用されたもののみをリスト化する<BR>
   * 
   * @param bean
   *          受注Bean
   */
  public void copyCommodityToBean(OrderModifyBean bean) {
    AddCommodityBean addCommodity = new AddCommodityBean();
    // 新規受注時に使用された出荷先を一覧化する。
    List<Long> shippingAddressNo = new ArrayList<Long>();
    for (ShippingHeaderBean header : bean.getShippingHeaderList()) {
      shippingAddressNo.add(header.getShippingAddressNo());
    }

    // 配送先情報設定
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    List<CodeAttribute> shippingList = new ArrayList<CodeAttribute>();
    String customerCode = bean.getOrderHeaderEdit().getCustomerCode();
    if (CustomerConstant.isCustomer(customerCode)) {
      List<CustomerAddress> addressList = service.getCustomerAddressList(customerCode);
      if (addressList == null || addressList.size() < 1) {
        Logger logger = Logger.getLogger(this.getClass());
        logger.error("Customer addres is null. customer code = " + customerCode);
        throw new RuntimeException(Messages.getString("web.action.back.order.OrderModifyInitAction.1"));
      }
      List<Long> usingAddress = new ArrayList<Long>();
      for (CustomerAddress address : addressList) {
        Long addressNo = address.getAddressNo();
        NameValue nameValue = new NameValue(address.getAddressAlias(), NumUtil.toString(addressNo));
        shippingList.add(nameValue);
        usingAddress.add(addressNo);
      }
      for (ShippingContainer shipping : bean.getOldOrderContainer().getShippings()) {
        ShippingHeader header = shipping.getShippingHeader();
        Long addressNo = header.getAddressNo();
        // DBから取得したアドレスに存在しないアドレスが出荷情報に設定されていた場合追加する
        if (!usingAddress.contains(addressNo)) {
          NameValue nameValue = new NameValue(header.getAddressLastName() + header.getAddressFirstName(), NumUtil
              .toString(addressNo));
          shippingList.add(nameValue);
          usingAddress.add(addressNo);
        }
      }
      addCommodity.setDisplayAddressList(true);
    } else {
      // ゲストの場合、出荷情報から顧客アドレスを生成し、非表示フラグをONにする
      for (ShippingHeaderBean header : bean.getShippingHeaderList()) {
        String value = NumUtil.toString(header.getShippingAddressNo());
        String name = header.getAddress().getFirstName() + header.getAddress().getLastName();
        NameValue nameValue = new NameValue(name, value);
        shippingList.add(nameValue);
      }
      addCommodity.setDisplayAddressList(false);
    }

    // ショップ情報設定
    List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();
    // 重複防止用ショップコードリスト
    List<String> useShopCodeList = new ArrayList<String>();
    for (ShippingHeaderBean header : bean.getShippingHeaderList()) {
      String shopCode = header.getShippingShopCode();
      if (useShopCodeList.contains(shopCode)) {
        continue;
      } else {
        NameValue shop = new NameValue(header.getShippingShopName(), shopCode);
        shopList.add(shop);
        useShopCodeList.add(shopCode);
      }
    }

    addCommodity.setAddressList(shippingList);
    addCommodity.setShopList(shopList);
    bean.setAddCommodityEdit(addCommodity);
  }

  /**
   * 受注情報から合計金額を計算する
   * 
   * @param bean
   *          受注Bean
   * @param orderNo
   *          受注ナンバー
   */
  public void copyPriceToBean(OrderModifyBean bean, String orderNo) {
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    OrderSummary summary = service.getOrderSummary(orderNo);
    TotalPrice afterTotalPrice = new TotalPrice();
    afterTotalPrice.setCommodityPrice(summary.getRetailPrice());
    afterTotalPrice.setGiftPrice(summary.getGiftPrice());
    afterTotalPrice.setShippingCharge(summary.getShippingCharge());
    afterTotalPrice.setPaymentCommission(summary.getPaymentCommission());
    

    TotalPrice beforTotalPrice = new TotalPrice();

    beforTotalPrice.setCommodityPrice(summary.getRetailPrice());
    beforTotalPrice.setGiftPrice(summary.getGiftPrice());
    beforTotalPrice.setShippingCharge(summary.getShippingCharge());
    beforTotalPrice.setPaymentCommission(summary.getPaymentCommission());
    if (summary.getOuterCardUsePrice() != null && !BigDecimalUtil.equals(summary.getOuterCardUsePrice(), BigDecimal.ZERO)) {
      afterTotalPrice.setOutCardUsePrice(summary.getOuterCardUsePrice());
      beforTotalPrice.setOutCardUsePrice(summary.getOuterCardUsePrice());
    }
    
    afterTotalPrice.setAllPrice(summary.getTotalAmount().add(summary.getPaymentCommission()).add(summary.getOuterCardUsePrice()));
    beforTotalPrice.setAllPrice(summary.getTotalAmount().add(summary.getPaymentCommission()).add(summary.getOuterCardUsePrice()));
    if (summary.getPaymentMethodType().equals(PaymentMethodType.POINT_IN_FULL.getValue())) {
      afterTotalPrice.setNotPointInFull(false);
      beforTotalPrice.setNotPointInFull(false);
    } else {
      afterTotalPrice.setNotPointInFull(true);
      beforTotalPrice.setNotPointInFull(true);
    }

    bean.setAfterTotalPrice(afterTotalPrice);
    bean.setBeforeTotalPrice(beforTotalPrice);

  }

  /**
   * 受注ヘッダ情報をBeanにコピーする
   * 
   * @param bean
   * @param header
   * @param customer
   */
  public void copyOrderHeaderToBean(OrderModifyBean bean, OrderHeader header, CustomerInfo customer) {
    Customer orderCustomer = customer.getCustomer();
    bean.setOrderNo(header.getOrderNo());
    bean.setOrderDataTransportFlg(NumUtil.toString(header.getDataTransportStatus()));
    bean.setShopCode(header.getShopCode());
    bean.setUpdateDatetime(header.getUpdatedDatetime());

    OrderHeaderBean headerBean = new OrderHeaderBean();
    headerBean.setCustomerCode(header.getCustomerCode());
    headerBean.setGuestFlg(NumUtil.toString(header.getGuestFlg()));
    PaymentAddress address = new PaymentAddress();
    address.setPrefectureCode(header.getPrefectureCode());
    address.setAddress1(header.getAddress1());
    address.setAddress2(header.getAddress2());
    address.setAddress3(header.getAddress3());
    address.setAddress4(header.getAddress4());
    address.setFirstName(header.getFirstName());
    address.setFirstNameKana(header.getFirstNameKana());
    address.setLastName(header.getLastName());
    address.setLastNameKana(header.getLastNameKana());
    if (StringUtil.hasValue(header.getPhoneNumber())) {
      String[] phoneNumber = header.getPhoneNumber().split("-");
      if (phoneNumber.length == 2) {
        address.setPhoneNumber1(phoneNumber[0]);
        address.setPhoneNumber2(phoneNumber[1]);
        address.setPhoneNumber(StringUtil.joint('-', phoneNumber[0], phoneNumber[1]));
      } else if (phoneNumber.length == 3) {
        address.setPhoneNumber1(phoneNumber[0]);
        address.setPhoneNumber2(phoneNumber[1]);
        address.setPhoneNumber3(phoneNumber[2]);
        address.setPhoneNumber(StringUtil.joint('-', phoneNumber[0], phoneNumber[1], phoneNumber[2]));
      }
    } else {
      header.setPhoneNumber("");
      address.setPhoneNumber1("");
      address.setPhoneNumber2("");
      address.setPhoneNumber3("");
      address.setPhoneNumber("");
    }
    address.setMobileNumber(header.getMobileNumber());
    address.setCityCode(header.getCityCode());
    address.setAreaCode(header.getAreaCode());
    address.setPostalCode(header.getPostalCode());
    String recentEmail = "";
    String recentPhoneNumber = "";
    String recentMobileNumber = "";
    if (CustomerConstant.isCustomer(header.getCustomerCode())) {
      if (orderCustomer != null) {
        if (StringUtil.isNullOrEmpty(customer.getAddress().getPhoneNumber())) {
          customer.getAddress().setPhoneNumber("");
        }
        if (!customer.getAddress().getPhoneNumber().equals(header.getPhoneNumber())) {
          recentPhoneNumber = customer.getAddress().getPhoneNumber();
        }
        if (StringUtil.isNullOrEmpty(customer.getAddress().getMobileNumber())) {
          customer.getAddress().setMobileNumber("");
        }
        if (!customer.getAddress().getMobileNumber().equals(header.getMobileNumber())) {
          recentMobileNumber = customer.getAddress().getMobileNumber();
        }
        if (!orderCustomer.getEmail().equals(header.getEmail())) {
          recentEmail = orderCustomer.getEmail();
        }
      }
    }
    address.setRecentEmail(recentEmail);
    address.setRecentPhoneNumber(recentPhoneNumber);
    address.setRecentMobileNumber(recentMobileNumber);
    address.setCustomerEmail(header.getEmail());
    headerBean.setAddress(address);

    headerBean.setCaution(header.getCaution());
    headerBean.setMessage(header.getMessage());
    bean.setOrderStatus(OrderStatus.fromValue(header.getOrderStatus()));
    bean.setOrderHeaderEdit(headerBean);
    bean.setOrderFlg(header.getOrderFlg().toString());
    bean.setMobileComputerType(header.getMobileComputerType());
    bean.setUseAgentStr(header.getUseAgent());
    bean.setGiftCardUsePrice(header.getGiftCardUsePrice());
    bean.setOrderClientType(header.getOrderClientType());
  }

  /**
   * 出荷先情報の設定
   * 
   * @param bean
   * @param headerContainerList
   */
  public void copyShippingToBean(OrderModifyBean bean, List<ShippingContainer> headerContainerList) {
    List<ShippingHeaderBean> shippingHeaderList = new ArrayList<ShippingHeaderBean>();
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());

    boolean deletableShipping = (headerContainerList.size() > 1);
    Collections.sort(headerContainerList, new ShippingComparator());
    for (ShippingContainer container : headerContainerList) {
      ShippingHeader header = container.getShippingHeader();
      ShippingHeaderBean headerBean = new ShippingHeaderBean();
      headerBean.setModified(false);
      headerBean.setShippingNo(header.getShippingNo());
      headerBean.setShippingAddressNo(header.getAddressNo());
      headerBean.setDeletable(deletableShipping);
      ShippingAddress address = new ShippingAddress();
      address.setFirstName(header.getAddressFirstName());
      address.setFirstNameKana(header.getAddressFirstNameKana());
      address.setLastName(header.getAddressLastName());
      address.setLastNameKana(header.getAddressLastNameKana());
      address.setPostalCode(header.getPostalCode());
      address.setPrefectureCode(header.getPrefectureCode());
      address.setAddress1(header.getAddress1());
      address.setAddress2(header.getAddress2());
      address.setAddress3(header.getAddress3());
      address.setAddress4(header.getAddress4());
      if (StringUtil.hasValue(header.getPhoneNumber())) {
        String[] phoneNumber = header.getPhoneNumber().split("-");
        if (phoneNumber.length == 2) {
          address.setPhoneNumber1(phoneNumber[0]);
          address.setPhoneNumber2(phoneNumber[1]);
        } else if (phoneNumber.length == 3) {
          address.setPhoneNumber1(phoneNumber[0]);
          address.setPhoneNumber2(phoneNumber[1]);
          address.setPhoneNumber3(phoneNumber[2]);
        }
      } else {
        address.setPhoneNumber1("");
        address.setPhoneNumber2("");
        address.setPhoneNumber3("");
      }
      address.setMobileNumber(header.getMobileNumber());
      address.setCityCode(header.getCityCode());
      address.setAreaCode(header.getAreaCode());
      headerBean.setAddress(address);
      headerBean.setShippingShopCode(header.getShopCode());
      headerBean.setShippingShopName(utilService.getShopName(header.getShopCode()));
      headerBean.setShippingTypeCode(NumUtil.toString(header.getDeliveryTypeNo()));
      headerBean.setShippingTypeName(header.getDeliveryTypeName());

      DeliveryType delivery = shopService.getDeliveryType(header.getShopCode(), header.getDeliveryTypeNo());

      headerBean.setDeliveryCompanyName(header.getDeliveryCompanyName());
      headerBean.setDeliveryCompanyNo(header.getDeliveryCompanyNo());
      // 配送指定日時の設定
      DeliverySpecificationType type = DeliverySpecificationType.fromValue(delivery.getDeliverySpecificationType());
      boolean displayDeliveryAppointedDate = false;
      if (bean.isReserveFlg()) {
        displayDeliveryAppointedDate = false;
      } else {
        if (StringUtil.hasValue(header.getDeliveryAppointedDate())) {
          headerBean.setDeliveryAppointedDate(header.getDeliveryAppointedDate());
          displayDeliveryAppointedDate = true;
        } else {
          headerBean.setDeliveryAppointedDate("");
        }
        if (type != null
            && (type.equals(DeliverySpecificationType.DATE_ONLY) || type.equals(DeliverySpecificationType.DATE_AND_TIME))) {
          displayDeliveryAppointedDate = true;
        }
      }
      headerBean.setDisplayDeliveryAppointedDate(displayDeliveryAppointedDate);

      // 配送希望時間帯
      headerBean.setDeliveryAppointedStartTimeList(createTimeList());
      headerBean.setDeliveryAppointedEndTimeList(createTimeList());
      String start = NumUtil.toString(header.getDeliveryAppointedTimeStart());
      String end = NumUtil.toString(header.getDeliveryAppointedTimeEnd());
      boolean displayDeliveryAppointedTime = false;
      if (bean.isReserveFlg()) {
        displayDeliveryAppointedTime = false;
      } else {
        if (StringUtil.isNullOrEmptyAnyOf(start, end)) {
          headerBean.setDeliveryAppointedStartTime("");
          headerBean.setDeliveryAppointedEndTime("");
        } else {
          headerBean.setDeliveryAppointedStartTime(start);
          headerBean.setDeliveryAppointedEndTime(end);
          displayDeliveryAppointedTime = true;
        }
        if (type != null
            && (type.equals(DeliverySpecificationType.TIME_ONLY) || type.equals(DeliverySpecificationType.DATE_AND_TIME))) {
          displayDeliveryAppointedTime = true;
        }
      }
      headerBean.setDisplayDeliveryAppointedTime(displayDeliveryAppointedTime);

      headerBean.setShippingCharge(NumUtil.toString(header.getShippingCharge()));
      String shippingChargeTaxType = NumUtil.toString(header.getShippingChargeTaxType());
      headerBean.setShippingChargeTaxType(shippingChargeTaxType);
      headerBean.setShippingChargeTaxTypeName(TaxType.fromValue(shippingChargeTaxType).getName());
      headerBean.setDeliveryRemark(header.getDeliveryRemark());
      headerBean.setShippingDirectDate(DateUtil.toDateString(header.getShippingDirectDate())); // 10.1.6
      String deliveryTime = "";
      if (StringUtil.hasValueAllOf(headerBean.getDeliveryAppointedStartTime(), headerBean.getDeliveryAppointedEndTime())) {
        deliveryTime = headerBean.getDeliveryAppointedStartTime() + "-" + headerBean.getDeliveryAppointedEndTime();
      } else if (StringUtil.hasValue(headerBean.getDeliveryAppointedStartTime())) {
        deliveryTime = headerBean.getDeliveryAppointedStartTime() + "-";
      } else if (StringUtil.hasValue(headerBean.getDeliveryAppointedEndTime())) {
        deliveryTime = "-" + headerBean.getDeliveryAppointedEndTime();
      }
      headerBean.setDeliveryAppointedTime(deliveryTime);
      List<ShippingDetailBean> shippingDetailList = createShippingDetailBeanList(bean,container.getShippingHeader().getShippingNo(),
          container.getShippingDetails());
      headerBean.setShippingDetailList(shippingDetailList);

      shippingHeaderList.add(headerBean);
    }
    bean.setShippingHeaderList(shippingHeaderList);
  }

  /**
   * DBの出荷明細一覧から画面表示用の出荷明細情報を生成する（初期作成用）
   * 
   * @param detailList
   *          出荷明細リスト
   * @return 出荷明細情報
   */
  public List<ShippingDetailBean> createShippingDetailBeanList(OrderModifyBean bean,String shippingNo, List<ShippingDetail> detailList) {
    List<ShippingDetailBean> beanDetailList = new ArrayList<ShippingDetailBean>();
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    CatalogService categoryService = ServiceLocator.getCatalogService(getLoginInfo());
    BigDecimal totalAmount = BigDecimal.ZERO;
    List<String> commodityList = new ArrayList<String>();
    
    for (int i = 0; i < detailList.size(); i++) {
      ShippingDetail detail = detailList.get(i);
      String shopCode = detail.getShopCode();
      if (CommodityType.GIFT.longValue().equals(detail.getCommodityType()) || CommodityType.PROPAGANDA.longValue().equals(detail.getCommodityType())) {
         continue;
      }
      if (detail.getDiscountValue()!=null) {
        totalAmount = totalAmount.add((detail.getDiscountValue().add(detail.getRetailPrice())).multiply(new BigDecimal(detail.getPurchasingAmount())));
      } else {
        totalAmount = totalAmount.add((detail.getRetailPrice()).multiply(new BigDecimal(detail.getPurchasingAmount())));
      }
      ShippingDetailBean detailBean = createShippingDetailBean(shopCode, detail.getSkuCode(), detail.getGiftCode(), detail
          .getPurchasingAmount(), detail.getPurchasingAmount(),detail.getRetailPrice());
      // detailNoは全商品を通して一意になるように設定する      
      // 2012/11/20 促销活动 ob add start
      detailBean.setCampaignCode(detail.getCampaignCode());//促销活动编号
      detailBean.setCampaignName(detail.getCampaignName());//促销活动名称
      detailBean.setDiscountType(NumUtil.toString(detail.getDiscountType()));//折扣类型
      detailBean.setDiscountValue(detail.getDiscountValue());//折扣值
      //套餐明细取得
      if (SetCommodityFlg.OBJECTIN.longValue().equals(detailBean.getSetCommodityFlg())) {
        for (ShippingDetailComposition composition : getBean().getOldOrderContainer().
            getShipping(detail.getShippingNo()).getShippingDetailCommpositionList()) {
          if (detail.getShippingNo().equals(composition.getShippingNo()) 
              && detail.getShippingDetailNo().equals(composition.getShippingDetailNo())) {
        	  SetCommpositionInfo compositionInfo = new SetCommpositionInfo();
        	  compositionInfo.setSkuCode(composition.getChildSkuCode());
        	  compositionInfo.setRetailPrice(composition.getRetailPrice());
        	  composition.setOrmRowid(-1L);
        	  compositionInfo.setComposition(composition);
            detailBean.getCompositionList().add(compositionInfo);
          }
        }
      }
      commodityList.add(detailBean.getOrderDetailCommodityInfo().getCommodityCode());
      // 2012/11/20 促销活动 ob add end
      detailBean.setDetailNo(shippingNo + Integer.toString(i));
      // 旧出荷明細番号を保持する
      detailBean.setShippingDetailNo(detail.getShippingDetailNo());
      
      Long discountType = categoryService.getDiscountTypeByOrderDetail(detailBean.getSkuCode(),detailBean.getOrderDetailCommodityInfo().getOrderNo(),2L,detailBean.getOrderDetailCommodityInfo().getUnitPrice());
      if (discountType == 2L){
        detailBean.setDiscountCommodity(true);
      }
      
      beanDetailList.add(detailBean);
    }

    // 2012/11/22 促销活动 ob add start
    
    for (int i = 0; i < detailList.size(); i++) {
      ShippingDetail detail = detailList.get(i);
      if (detail.getCommodityType() == null || CommodityType.GENERALGOODS.longValue().equals(detail.getCommodityType())) {
         continue;
      }
      ShippingDetailBean detailBean = createShippingDetailBean(detail.getShopCode(), detail.getSkuCode(), detail.getGiftCode(), detail
          .getPurchasingAmount(), detail.getPurchasingAmount());
      detailBean.setCampaignCode(detail.getCampaignCode());//促销活动编号
      detailBean.setCampaignName(detail.getCampaignName());//促销活动名称
      detailBean.setDiscountType(NumUtil.toString(detail.getDiscountType()));//折扣类型
      detailBean.setDiscountValue(detail.getDiscountValue());//折扣值
      detailBean.setDetailNo(shippingNo + Integer.toString(i));
      detailBean.setShippingDetailNo(detail.getShippingDetailNo());
      beanDetailList.add(detailBean);
      CampaignInfo campaignInfo = service.getCampaignInfo(detail.getCampaignCode());
      //促销活动不存在或者是赠品促销活动时
      if (campaignInfo == null || campaignInfo.getCampaignMain() == null 
          || CampaignMainType.GIFT.longValue().equals(campaignInfo.getCampaignMain().getCampaignType())) {
        continue;
      }
      //促销条件获得
      CampaignCondition condition = campaignInfo.getConditionList().get(0);
      if (StringUtil.hasValue(condition.getAdvertCode()) && StringUtil.isNullOrEmpty(bean.getAdvertCode())) {
        bean.setAdvertCode(condition.getAdvertCode());
      }
    }
    // 2012/11/22 促销活动 ob add end
    return beanDetailList;
  }

  /**
   * ポイント関連情報を設定する
   * 
   * @param bean
   * @param header
   * @param orderCustomer
   */
  public void copyPointInfoToBean(OrderModifyBean bean, OrderHeader header, Customer orderCustomer) {
    PointBean pointInfo = bean.getPointInfo();
    if (pointInfo == null) {
      pointInfo = new PointBean();
    }
    if (CustomerConstant.isCustomer(orderCustomer.getCustomerCode())) {
      pointInfo.setRestPoint(NumUtil.toString(orderCustomer.getRestPoint()));
      BigDecimal beforeUsePoint = header.getUsedPoint();
      pointInfo.setBeforeUsePoint(beforeUsePoint);

      BigDecimal usePoint = beforeUsePoint;
      SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
      Long pointFunctionEnableFlg = service.getPointRule().getPointFunctionEnabledFlg();
      if (pointFunctionEnableFlg.equals(PointFunctionEnabledFlg.DISABLED.longValue())) {
        // ポイント使用不可となっていた場合、使用ポイントを0ptで更新
        usePoint = BigDecimal.ZERO;
      }
      pointInfo.setUsePoint(NumUtil.toString(usePoint));
    }

    bean.setPointInfo(pointInfo);
  }


  public void copyCouponToBean(OrderModifyBean bean, OrderHeader header, Customer orderCustomer) {
    // 可使用商品券一览取得    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    List<CouponBean> afterDetailList = new ArrayList<CouponBean>();
    List<CouponBean> beforDetailList = new ArrayList<CouponBean>();
    List<CustomerCoupon> oldOrderCoupon = service.getOrderUsedCoupon(header.getOrderNo());
    for (CustomerCoupon coupon : oldOrderCoupon) {
      CouponBean detail = new CouponBean();
      detail.setCustomerCouponId(NumUtil.toString(coupon.getCustomerCouponId()));
      detail.setCouponName(coupon.getCouponName());
      detail.setCouponPrice(NumUtil.toString(coupon.getCouponPrice()));
      detail.setUseEndDate(DateUtil.toDateString(coupon.getUseCouponEndDate()));
      beforDetailList.add(detail);
    }
    List<CustomerCoupon> newOrderCoupon = service.getOrderModifyEnableCoupon(orderCustomer.getCustomerCode(), header.getOrderNo());
    for (CustomerCoupon coupon : newOrderCoupon) {
      CouponBean detail = new CouponBean();
      detail.setCustomerCouponId(NumUtil.toString(coupon.getCustomerCouponId()));
      detail.setCouponName(coupon.getCouponName());
      detail.setUseEndDate(DateUtil.toDateString(coupon.getUseCouponEndDate()));
      detail.setCouponPrice(NumUtil.toString(coupon.getCouponPrice()));
      for (CustomerCoupon couponOld : oldOrderCoupon) {
        if (couponOld.getCustomerCouponId().equals(coupon.getCustomerCouponId())) {
          detail.setSelectCouponId(NumUtil.toString(couponOld.getCustomerCouponId()));
        }
      }
      afterDetailList.add(detail);
    }
    bean.getCouponInfoBean().setBeforeCouponList(beforDetailList);
    bean.getCouponInfoBean().setAfterCouponList(afterDetailList);
    bean.getCouponInfoBean().setBeforeCouponPrice(header.getCouponPrice());
    bean.getCouponInfoBean().setAfterCouponPrice(header.getCouponPrice());
  }

  /**
   * 支払情報を設定する
   * 
   * @param bean
   *          受注Bean
   * @param header
   *          受注情報
   */
  public void copyPaymentInfoToBean(OrderModifyBean bean, OrderHeader header) {
    PaymentMethodBean paymentInfo = bean.getOrderPayment();
    PaymentSupporter supporter = PaymentSupporterFactory.createPaymentSuppoerter();
    if (paymentInfo == null) {
      BigDecimal price = bean.getAfterTotalPrice().getAllPrice().subtract( NumUtil.coalesce(header.getDiscountPrice(),BigDecimal.ZERO));
      paymentInfo = supporter.createPaymentMethodBean(header.getShopCode(), price, NumUtil
          .parse(bean.getPointInfo().getUsePoint()), false, bean.getCouponInfoBean().getAfterCouponPrice(), "");
    }
    // 新規受注時の支払方法コードを設定
    String paymentMethodNo = NumUtil.toString(header.getPaymentMethodNo());
    for (PaymentTypeBase base : paymentInfo.getDisplayPaymentList()) {
      if (paymentMethodNo.equals(base.getPaymentMethodCode())) {
        paymentInfo.setPaymentMethodCode(paymentMethodNo);
      }
    }
    // 新規受注時の支払方法コードが代引きの場合、古い手数料を使用する    PaymentTypeBase select = supporter.getSelectPaymentType(paymentInfo);
    if (select != null && select.isCashOnDelivery()) {
      select.setPaymentCommission(header.getPaymentCommission());
    }
    paymentInfo.setPaymentMethodType(header.getPaymentMethodType());
    bean.setOrderPayment(paymentInfo);
  }

  /**
   * 変更する受注が自ショップの受注かどうかを返す
   * 
   * @return 自ショップフラグ
   */
  public boolean isOwnShopOrder() {
    BackLoginInfo login = getLoginInfo();
    String orderShopCode = modifyOrder.getOrderHeader().getShopCode();
    return login.getShopCode().equals(orderShopCode);
  }

  private String getPathInfo(int index) {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > index) {
      return tmpArgs[index];
    }
    return "";
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderModifyInitAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102023005";
  }

  private static class ShippingComparator implements Comparator<ShippingContainer>, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public int compare(ShippingContainer o1, ShippingContainer o2) {
      return o1.getShippingNo().compareTo(o2.getShippingNo());
    }
  }

  /**
   * 订单发票情报设定 return InvoiceBean
   */
  private InvoiceBean getOrderInvoice(String orderNo, String customerOrder) {
    InvoiceBean invoiceBean = new InvoiceBean();
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    // 订单发票情报查询
    OrderInvoice orderInvoice = orderService.getOrderInvoice(orderNo);
    invoiceBean.setInvoiceType(InvoiceType.USUAL.getValue());// 默认通常
    if (orderInvoice != null) {
      // 有订单发票时，【领取发票】设置为被选中
      if (StringUtil.isNotNull(orderInvoice.getCustomerName()) || StringUtil.isNotNull(orderInvoice.getCompanyName())) {
        invoiceBean.setInvoiceFlg("1");
      }
      invoiceBean.setInvoiceCommodityName(orderInvoice.getCommodityName());// 商品规格
      invoiceBean.setInvoiceType(NumUtil.formatNumber(orderInvoice.getInvoiceType()));// 发票类型
      if (InvoiceType.USUAL.getValue().equals(NumUtil.formatNumber(orderInvoice.getInvoiceType()))) {
        // 发票类型是通常发票
        invoiceBean.setInvoiceCustomerName(orderInvoice.getCustomerName());// 顾客名称
      } else {
        // 发票类型是增值税发票
        invoiceBean.setInvoiceCompanyName(orderInvoice.getCompanyName());// 公司名称
        invoiceBean.setInvoiceTaxpayerCode(orderInvoice.getTaxpayerCode());// 纳税人识别号
        invoiceBean.setInvoiceAddress(orderInvoice.getAddress());// 住所
        invoiceBean.setInvoiceTel(orderInvoice.getTel());// 电话号码
        invoiceBean.setInvoiceBankName(orderInvoice.getBankName());// 银行名称
        invoiceBean.setInvoiceBankNo(orderInvoice.getBankNo());// 银行支行编号
      }
    } else {
      CustomerVatInvoice customerVatInvoice = orderService.getCustomerVatInvoice(customerOrder);
      if (customerVatInvoice != null) {
        invoiceBean.setInvoiceCompanyName(customerVatInvoice.getCompanyName());
        invoiceBean.setInvoiceTaxpayerCode(customerVatInvoice.getTaxpayerCode());
        invoiceBean.setInvoiceAddress(customerVatInvoice.getAddress());
        invoiceBean.setInvoiceTel(customerVatInvoice.getTel());
        invoiceBean.setInvoiceBankName(customerVatInvoice.getBankName());
        invoiceBean.setInvoiceBankNo(customerVatInvoice.getBankNo());
      }
    }
    return invoiceBean;
  }
}
