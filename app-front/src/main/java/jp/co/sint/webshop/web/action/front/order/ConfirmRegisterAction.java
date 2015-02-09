package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.domain.AdvertType;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.MobileComputerType;
import jp.co.sint.webshop.data.domain.OrderFlg;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentMethodDisplayType;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.dto.Advert;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerCardInfo;
import jp.co.sint.webshop.data.dto.CustomerCardUseInfo;
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.GiftCardReturnConfirm;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.MyCouponInfo;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.campain.CampaignInfo;
import jp.co.sint.webshop.service.campain.CampainFilter;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.service.customer.CustomerPointInfo;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.OrderMailType;
import jp.co.sint.webshop.service.order.OrderSmsType;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.PaymentErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.shop.PaymentMethodSuite;
import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.UserAgentManager;
import jp.co.sint.webshop.web.action.TransactionTokenAction;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.order.CompleteBean;
import jp.co.sint.webshop.web.bean.front.order.PaymentBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.message.front.order.PaymentErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;

import org.apache.log4j.Logger;

/**
 * U2020140:注文内容確認のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
// 10.1.4 10195 修正 ここから
// public class ConfirmRegisterAction extends ConfirmBaseAction {
public class ConfirmRegisterAction extends ConfirmBaseAction implements TransactionTokenAction {

  // 10.1.4 10195 修正 ここまで
  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = true;
    BigDecimal totalCommodityPrice = BigDecimal.ZERO;
    List<CartCommodityInfo> orderDetailList = getBean().getCashier().getShippingList().get(0).getCommodityInfoList();
    // 2012/12/03 促销对应 ob update start
    for (CartCommodityInfo od : orderDetailList) {
      totalCommodityPrice = totalCommodityPrice.add(BigDecimalUtil.multiply(od.getRetailPrice(), od.getQuantity()));
    }
    
    // 在庫・予約上限数チェック
    valid &= shippingValidation();
     
    // 折扣券的使用可能验证
    valid &= couponValidation();
    
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    for (CashierShipping shipping : getBean().getCashier().getShippingList()) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()){
        if (commodity.getIsDiscountCommodity().equals("true")){
          //历史所有客户购买总数
          Long siteTotalAmount = catalogService.getHistoryBuyAmountTotal(commodity.getSkuCode());
          if (siteTotalAmount == null){
            siteTotalAmount = 0L;
          }
          String customerCode = getLoginInfo().getCustomerCode();
          if (StringUtil.hasValue(customerCode) && !customerCode.equals("guest")) {
            DiscountCommodity dcBean = catalogService.getDiscountCommodityByCommodityCode(commodity.getSkuCode());
            //限购商品剩余可购买数量
            Long avalibleAmountTotal = dcBean.getSiteMaxTotalNum() - siteTotalAmount;
            if (avalibleAmountTotal <= 0L){
              addErrorMessage(commodity.getCommodityName() + Messages.getString("web.action.front.order.DiscountCommodity.0") );
              return false;
            }
            Long historyNum = catalogService.getHistoryBuyAmount(commodity.getSkuCode(), customerCode);
            if (historyNum == null){
              historyNum = 0L;
            }
            if (dcBean.getCustomerMaxTotalNum() > historyNum){
              Long num = dcBean.getCustomerMaxTotalNum() - historyNum;
              if (num > avalibleAmountTotal){
                num = avalibleAmountTotal;
              }
              if (commodity.getQuantity() > num ){
                addErrorMessage(commodity.getCommodityName() + Messages.getString("web.action.front.order.DiscountCommodity.1") + num + Messages.getString("web.action.front.order.DiscountCommodity.2") );
                return false;
              }
            } else {
              addErrorMessage(commodity.getCommodityName() + Messages.getString("web.action.front.order.DiscountCommodity.3"));
              return false ;
            }
          }
        }
      }
    }
    
    
    
    
    // 2012/12/03 促销对应 ob update end
    OrderHeader orderHeader = getBean().getOrder().getOrderHeader();
    if (orderHeader.getDiscountType() != null) {
      if (orderHeader.getDiscountType().equals(CouponType.CUSTOMER_GROUP.longValue())) {
        // 会员折扣
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        CustomerGroupCampaign customerGroupCampaign = customerService.getCustomerGroupCampaign(orderHeader
            .getShopCode(), orderHeader.getCustomerGroupCode());
        if (customerGroupCampaign == null) {
          addErrorMessage(Messages.getString("web.action.front.order.ConfirmRegisterAction.2"));
          valid &= false;
        } else if (BigDecimalUtil.isBelow(totalCommodityPrice, customerGroupCampaign.getMinOrderAmount())) {
          addErrorMessage(Messages.getString("web.action.front.order.ConfirmRegisterAction.3"));
          valid &= false;
        }
      } else if (orderHeader.getDiscountType().equals(CouponType.COMMON_DISTRIBUTION.longValue())) {
        // 使用公共优惠券验证
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        NewCouponRule newCouponRule = customerService.getPublicCoupon(orderHeader.getDiscountCode());
        if (newCouponRule == null) {
          addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.5"));
          return false;
        } else {
          // 优惠券使用订购金额限制
          if (BigDecimalUtil.isBelow(totalCommodityPrice, newCouponRule.getMinUseOrderAmount())) {
            addErrorMessage(Messages.getString("web.action.front.order.ConfirmRegisterAction.4")
                + newCouponRule.getMinUseOrderAmount().toString()
                + Messages.getString("web.action.front.order.ConfirmRegisterAction.4"));
            return false;
          }
          OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
          // 优惠券使用次数网站限制
          Long siteCount = orderService.countUsedCouponOrder(newCouponRule.getCouponCode(), null);
          if (siteCount >= newCouponRule.getSiteUseLimit().longValue()) {
            addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.3"));
            return false;
          }
          // 优惠券使用次数个人限制
          Long customerCount = orderService.countUsedCouponOrder(newCouponRule.getCouponCode(), orderHeader.getCustomerCode());
          if (customerCount >= newCouponRule.getPersonalUseLimit().longValue()) {
            addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.4"));
            return false;
          }

          // 2013/04/07 优惠券对应 ob add start
          // 自己发行的Public优惠券不可使用
          CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
          if (communicationService.issuedBySelf(orderHeader.getDiscountCode(), orderHeader.getCustomerCode())) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.USE_COUPON_OF_ISSUE_BY_SELF_ERROR));
            return false;
          }
        }
      } else if (orderHeader.getDiscountType().equals(CouponType.PURCHASE_DISTRIBUTION.longValue())
          || orderHeader.getDiscountType().equals(CouponType.SPECIAL_MEMBER_DISTRIBUTION.longValue())) {

        // 已选择个人优惠券信息取得
        MyCouponInfo aviableCouponSelected = getAviableCouponInfo(orderHeader.getShopCode(), orderHeader.getDiscountDetailCode());

        if (aviableCouponSelected == null || StringUtil.isNullOrEmpty(aviableCouponSelected.getCouponIssueNo())) {
          addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.2"));
          return false;
        }
        // 2013/04/16 优惠券对应 ob update end

      }
    }
    // 20120106 shen update end
    
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerCardInfo cardInfo = service.getCustomerCardInfoByCustomerCode(getLoginInfo().getCustomerCode());
    CustomerCardUseInfo cardUseInfo = service.getCustomerCardUseInfoBycustomerCode(getLoginInfo().getCustomerCode());
    GiftCardReturnConfirm confirmPrice = service.getGiftCardReturnConfirm(getLoginInfo().getCustomerCode());
    BigDecimal avalibleGiftCardAmount = cardInfo.getDenomination().add(confirmPrice.getReturnAmount()).subtract(cardUseInfo.getUseAmount());
    
    if (BigDecimalUtil.isAbove( getBean().getCashier().getGiftCardUseAmount(), avalibleGiftCardAmount)) {
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.18"));
      return false;
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
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    OrderContainer order = getBean().getOrder();
    Cashier cashier = getBean().getCashier();
    
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    if(currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())){
      order.getOrderHeader().setLanguageCode("zh-cn");
    } else if(currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())){
      order.getOrderHeader().setLanguageCode("ja-jp");
    } else if(currentLanguageCode.equals(LanguageCode.En_Us.getValue())){
      order.getOrderHeader().setLanguageCode("en-us");
    } 
    String useAgentString = getUseAgent();
    order.getOrderHeader().setUseAgent(useAgentString);
    
    UserAgentManager manager = DIContainer.getUserAgentManager();
    List<String> orderTypeList = manager.getOrderType();
    if (useAgentString.contains(orderTypeList.get(0).split(";")[0])) {
      order.getOrderHeader().setOrderClientType(orderTypeList.get(0).split(";")[1]);
      
    } else if (useAgentString.contains(orderTypeList.get(1).split(";")[0])) {
      order.getOrderHeader().setOrderClientType(orderTypeList.get(1).split(";")[1]);
      
    } else if (useAgentString.contains(orderTypeList.get(2).split(";")[0])) {
      order.getOrderHeader().setOrderClientType(orderTypeList.get(2).split(";")[1]);
      
    } else {
      order.getOrderHeader().setOrderClientType(orderTypeList.get(3).split(";")[1]);
    }
    if (getBean().getCashier().getGiftCardUseAmount() != null && BigDecimalUtil.isAbove(getBean().getCashier().getGiftCardUseAmount(), BigDecimal.ZERO)
            && BigDecimalUtil.equals(getBean().getCashier().getPaymentTotalPrice(), BigDecimal.ZERO)) {
      order.getOrderHeader().setPaymentMethodType(PaymentMethodType.NO_PAYMENT.getValue());
      order.getOrderHeader().setPaymentMethodNo(10000L);
      order.getOrderHeader().setPaymentMethodName(PaymentMethodType.NO_PAYMENT.getName());
      getBean().getCashier().getPayment().getSelectPayment().setPaymentMethodType(PaymentMethodType.NO_PAYMENT.getValue());
      
    }
    order.getOrderHeader().setGiftCardUsePrice(getBean().getCashier().getGiftCardUseAmount());
    order.getOrderHeader().setOuterCardPrice(getBean().getCashier().getOuterCardUseAmount());
    if (getBean().getCashier().getOuterCardUseAmount() != null && !BigDecimalUtil.equals(getBean().getCashier().getOuterCardUseAmount(), BigDecimal.ZERO)) {
      // 订单拦截处理
      order.getOrderHeader().setOrderFlg(OrderFlg.NOT_CHECKED.longValue());
      String message = cashier.getMessage();
      if (StringUtil.isNullOrEmpty(message)) {
        message = "";
      }
      order.getOrderHeader().setCaution("(外卡支付订单需要进行确认)" + message);
    }
    order.setOrderMailType(OrderMailType.PC);
    order.setOrderSmsType(OrderSmsType.MOBILE);
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());

    
//    List<String> interceptDeliveryCompanyList = DIContainer.getWebshopConfig().getInterceptDeliveryCompanyList();
    for (CashierShipping shipping : cashier.getShippingList()) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        CommodityHeader header = catalogService.getCommodityHeader("00000000", commodity.getCommodityCode());
        if (header != null) {
          
          //家电发票更改规格  1为需要发票
          if(order.getOrderInvoice() != null ) {
              if (header.getNewReserveCommodityType2() == 1L) {
                order.getOrderInvoice().setCommodityName(order.getOrderInvoice().getCommodityName() + "-电器");
              }
          }
          
          //特殊sku和配送公司对应
          if (header.getNewReserveCommodityType2() == 1L) {
//            if (!interceptDeliveryCompanyList.contains(cashier.getDelivery().getDeliveryCompanyCode()) 
//                || )  ) {
            if (order.getOrderHeader().getPaymentMethodType().equals(PaymentMethodType.NO_PAYMENT.getValue())) {
              order.getOrderHeader().setOrderFlg(OrderFlg.GROUPCHECKED.longValue());
              order.getOrderHeader().setCaution("(高价值商品未选择指定快递公司/全额礼品卡支付)" + cashier.getMessage());
              break;
            }
          }
        }
      }
    }

    //虚假订单关键词拦截处理
    String address = getBean().getCashier().getShippingList().get(0).getAddress().getAddress4();
    OrderService servicewords = ServiceLocator.getOrderService(getLoginInfo());
    List<String> untrueWordList = new ArrayList<String>();
    untrueWordList=servicewords.getUntrueOrderWordList();
    boolean vide =true;
    for (String list : untrueWordList) {
      int islist =address.indexOf(list);
      if(islist!=-1){
        vide=false;
        break;
      }
    }
    if(!vide){
      order.getOrderHeader().setOrderFlg(OrderFlg.NOT_CHECKED.longValue());
      String message = cashier.getMessage();
      if(StringUtil.isNullOrEmpty(message)){
        message="";
    }
      order.getOrderHeader().setCaution("【疑是虚假订单】" + order.getOrderHeader().getCaution());
  }
    
    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    PaymentMethodSuite paymentMethodSuite = shopService.getPaymentMethod(getBean().getCashier().getPaymentShopCode(), NumUtil
        .toLong(getBean().getCashier().getPayment().getPaymentMethodCode()));

    Long paymentMethodDisplayType = paymentMethodSuite.getPaymentMethod().getPaymentMethodDisplayType();

    if (paymentMethodSuite.getPaymentMethod() == null
        || (!paymentMethodDisplayType.equals(PaymentMethodDisplayType.ALL.longValue()) && !paymentMethodDisplayType
            .equals(PaymentMethodDisplayType.FRONT.longValue()))) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.NO_PAYMENT_METHOD_ERROR));
      setRequestBean(getBean());
      return FrontActionResult.RESULT_SUCCESS;
    }
    String clientType = "";
    // ClientType.OTHER 为PC，值等于1. 其他为手持设备，值等于2
    if (StringUtil.isNullOrEmpty(getClient()) || getClient().equals(ClientType.OTHER)){
    	clientType = MobileComputerType.PC.getValue();
    }else{
    	clientType = MobileComputerType.Mobile.getValue();
    }
    // 20131018 txw add start
    String mediaCode = "";
    
    CommonSessionContainer commonSession = (CommonSessionContainer) getSessionContainer();
    if (commonSession.getSession().getAttribute(WebFrameworkConstants.ATTRIBUTE_FREE_MEDIA) != null) {
      mediaCode = commonSession.getSession().getAttribute(WebFrameworkConstants.ATTRIBUTE_FREE_MEDIA).toString();
    }
    order.setMediaCode(mediaCode);
    // 20131018 txw add end
    // 20140408 txw add start desc:英文地址设置未检查并提示信息
    ShippingHeader sh = order.getShippings().get(0).getShippingHeader();
    if(sh.getAddress1().substring(0, 1).matches("[a-zA-Z]")) {
      order.getOrderHeader().setCaution(order.getOrderHeader().getCaution() + "【英文地址订单需客服确认。】");
      order.getOrderHeader().setOrderFlg(OrderFlg.NOT_CHECKED.longValue());
    }
    // 20140408 txw add end
    // upd by lc 2013-01-10
    ServiceResult result = service.createNewOrder(order, getBean().getCashier(), clientType);

    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error == CustomerServiceErrorContent.CUSTOMER_DELETED_ERROR) {
          // 顧客存在エラー
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.front.order.ConfirmRegisterAction.0")));
          addErrorMessage(WebMessage.get(OrderErrorMessage.RESTART_CASHIER));
        } else if (error == OrderServiceErrorContent.STOCK_ALLOCATE_ERROR) {
          // 在庫引当エラー
          if (order.getOrderHeader().getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
            // 10.1.6 10277 修正 ここから
            for (OrderDetail cart : order.getOrderDetails()) {
              if (!catalogService.isAvailable(cart.getShopCode(), cart.getSkuCode(), cart.getPurchasingAmount().intValue(),
                  getBean().getCashier().isReserve()).equals(CommodityAvailability.AVAILABLE)) {
                // 2012/12/12 促销对应 ob update start
                addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, getDisplayName(cart.getCommodityName(), cart.getStandardDetail1Name(), cart.getStandardDetail2Name())));
                // 2012/12/12 促销对应 ob update end
                
              } else {
                // 排他制御の衝突によるエラー (リトライは可能)
                addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_CONCURRENCY_CONTROL_ERROR));
              }
            }
            // 10.1.6 10277 修正 ここまで
          } else {
            List<OrderDetail> cartList = order.getOrderDetails();
            for (OrderDetail cart : cartList) {
              if (!catalogService.isAvailable(cart.getShopCode(), cart.getSkuCode(), cart.getPurchasingAmount().intValue(),
                  getBean().getCashier().isReserve()).equals(CommodityAvailability.AVAILABLE)) {
                // 2012/12/12 促销对应 ob update start
                addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, getDisplayName(cart.getCommodityName(), cart.getStandardDetail1Name(), cart.getStandardDetail2Name())));
                // 2012/12/12 促销对应 ob update end
                
                // 10.1.2 10110 追加 ここから
              } else {
                // 排他制御の衝突によるエラー (リトライは可能)
                addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_CONCURRENCY_CONTROL_ERROR));
                // 10.1.2 10110 追加 ここまで
              }
            }
          }
        } else if (error == OrderServiceErrorContent.CAMPAIGN_FAILED) {
          // キャンペーン削除エラー
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.front.order.ConfirmRegisterAction.1")));
          addErrorMessage(WebMessage.get(OrderErrorMessage.RESTART_CASHIER));
          getSessionContainer().getCart().clear();
        } else if (error == CustomerServiceErrorContent.POINT_OVERFLOW_ERROR) {
          // ポイントオーバーフローエラー
          Length len = BeanUtil.getAnnotation(Customer.class, "temporaryPoint", Length.class);
          String maximum = StringUtil.times("9", len.value());
          addErrorMessage(WebMessage.get(MypageErrorMessage.POINT_OVERFLOW_ERROR, maximum));
          // 10.1.4 10201 追加 ここから
        } else if (error == OrderServiceErrorContent.OVER_USABLE_POINT_ERROR) {
          CustomerService custService = ServiceLocator.getCustomerService(getLoginInfo());
          CustomerPointInfo info = custService.getCustomerPointInfo(order.getOrderHeader().getCustomerCode());
          BigDecimal restPoint = NumUtil.coalesce(info.getRestPoint(), BigDecimal.ZERO);
          // ポイント情報を更新する
          getBean().getCashier().getCustomer().setRestPoint(restPoint);
          addErrorMessage(WebMessage.get(OrderErrorMessage.OVER_USABLE_POINT));
          // 10.1.4 10201 追加 ここまで
        } else if (isPaymentError(error)) {
          // お支払い方法画面での入力パラメータエラー
          PaymentBean bean = new PaymentBean();
          bean.setCashier(getBean().getCashier());
          String errorMessage = "";

          if (error == PaymentErrorContent.INVALID_CARD_INFORMATION_ERROR) {
            errorMessage = WebMessage.get(PaymentErrorMessage.INVALID_CARD_INFORMATION_ERROR);
          } else if (error == PaymentErrorContent.INVALID_CUSTOMER_NAME_ERROR) {
            errorMessage = WebMessage.get(PaymentErrorMessage.INVALID_CUSTOMER_NAME_ERROR);
          } else if (error == PaymentErrorContent.INVALID_EMAIL_ERROR) {
            errorMessage = WebMessage.get(PaymentErrorMessage.INVALID_EMAIL_ERROR);
          } else if (error == PaymentErrorContent.INVALID_OTHER_PARAMETER_ERROR || error == PaymentErrorContent.OTHER_PAYMENT_ERROR) {
            errorMessage = WebMessage.get(PaymentErrorMessage.INVALID_OTHER_ERROR, order.getOrderHeader().getPaymentMethodName());
          } else {
            Logger logger = Logger.getLogger(this.getClass());
            logger.error(error.toString());
            return FrontActionResult.SERVICE_ERROR;
          }
          bean.getErrorMessages().add(errorMessage);
          setRequestBean(bean);
          setNextUrl("/app/order/payment");
          return FrontActionResult.RESULT_SUCCESS;
          // 20120109 shen add start
        } else if (error == CustomerServiceErrorContent.COUPON_NOT_USE_ERROR) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.IS_USED_COUPON));
          // 20120109 shen add end
        }
      }
      if (getDisplayMessage().getErrors().size() > 0) {
        setRequestBean(getBean());
        return FrontActionResult.RESULT_SUCCESS;
      }
      return FrontActionResult.SERVICE_ERROR;
    } else { //如果订单插入成功，则判断该订单是否是活动订单，如果是，则插入活动订单表
      // add by lc 2012-10-18 start
      //追加代码，判断当前订单是否为免邮费活动订单，如果是将这条数据插入活动订单表中
      CampainFilter cf = new CampainFilter();
      cf.insertCampainOrder(getFreeShippingCharge(order, getBean().getCashier()));
      // add by lc 2012-10-18 end   
    }

    // Add by V10-CH start
    String advertUrl = DIContainer.getAdvertValue().getAdvertUrl2();
    String advertUrl1 = "";
    SiteManagementService ss = ServiceLocator.getSiteManagementService(getLoginInfo());
    List<Advert> advertList = ss.getAdvertByType(AdvertType.ORDER_REGISTER_COMPLETE.getValue());
    int i = 0;
    for (OrderDetail detail : order.getOrderDetails()) {
      String orderNo = detail.getOrderNo();
      String createdUser = order.getOrderHeader().getEmail();
      String retailPrice = detail.getRetailPrice().toString();
      String purchasingAmount = detail.getPurchasingAmount().toString();
      String commodityName = detail.getCommodityName();
      if (order.getOrderDetails().size() == 1) {
        advertUrl = MessageFormat.format(DIContainer.getAdvertValue().getAdvertUrl2(), advertList.get(0).getAdvertText(), orderNo,
            createdUser, retailPrice, purchasingAmount, commodityName)
            + "\"" + ">";
        break;
      } else if (order.getOrderDetails().size() > 1) {
        if (i > 0) {
          advertUrl1 = advertUrl1
              + MessageFormat.format(DIContainer.getAdvertValue().getAdvertUrl3(), advertList.get(0).getAdvertText(), retailPrice,
                  purchasingAmount, commodityName);
        } else {
          advertUrl = MessageFormat.format(DIContainer.getAdvertValue().getAdvertUrl2(), advertList.get(0).getAdvertText(),
              orderNo, createdUser, retailPrice, purchasingAmount, commodityName);
        }
      }
      i++;
    }
    if (order.getOrderDetails().size() > 1) {
      advertUrl = advertUrl + advertUrl1 + "\"" + ">";
    }
    // Add by V10-CH end
    // 必要情報を登録完了Beanに設定
    CompleteBean bean = new CompleteBean();
    bean.setOrderNo(order.getOrderHeader().getOrderNo());
    bean.setOrderDatetime(DateUtil.toDateTimeString(order.getOrderHeader().getOrderDatetime()));
    bean.setOrderUserCode(order.getOrderHeader().getCustomerCode());
    bean.setAdvertUrl(advertUrl);
    bean.setPaymentTotalPrice(getBean().getCashier().getPaymentTotalPrice());
    setRequestBean(bean);

    // カート情報の削除
    getCart().clear();
    
    // Cashierのクリア
    getBean().getCashier().clear();

    // 购物车履历清空
    Cart cart = getCart();
    if (getLoginInfo().isLogin()) {
      cart.clearCartHistroy(getLoginInfo().getCustomerCode());
    }
    
    // 登録完了画面に遷移    String pm = order.getOrderHeader().getPaymentMethodType();
    if (pm.equals(PaymentMethodType.ALIPAY.getValue()) || pm.equals(PaymentMethodType.CHINA_UNIONPAY.getValue()) || pm.equals(PaymentMethodType.OUTER_CARD.getValue()) || pm.equals(PaymentMethodType.INNER_CARD.getValue())) {
      setNextUrl("/app/order/external_payment/init/" + order.getOrderHeader().getOrderNo());
    } else {
      setNextUrl("/app/order/complete");
    }

    return FrontActionResult.RESULT_SUCCESS;
  }

  private boolean isPaymentError(ServiceErrorContent error) {
    for (PaymentErrorContent paymentError : PaymentErrorContent.values()) {
      if (paymentError == error) {
        return true;
      }
    }
    return false;
  }

  /**
   * 根据ShippingHeaderBean查看本次发货是否是免邮费
   * @param bean
   * @return
   */
  private CampaignInfo getFreeShippingCharge(OrderContainer order, Cashier bean){
    CampaignInfo campaignI = new CampaignInfo();
    campaignI.setOrderNo(order.getOrderNo());
    campaignI.setMobileNumber(bean.getShippingList().get(0).getAddress().getMobileNumber());
    campaignI.setPhoneNumber(bean.getShippingList().get(0).getAddress().getPhoneNumber());
    campaignI.setLastName(bean.getShippingList().get(0).getAddress().getAddressLastName());
    campaignI.setAddress1(bean.getShippingList().get(0).getAddress().getAddress1());
    campaignI.setAddress2(bean.getShippingList().get(0).getAddress().getAddress2());
    campaignI.setAddress3(bean.getShippingList().get(0).getAddress().getAddress3());
    campaignI.setAddress4(bean.getShippingList().get(0).getAddress().getAddress4());
    campaignI.setPrefectureCode(bean.getShippingList().get(0).getAddress().getPrefectureCode());
    List<OrderDetail> commodityList = new ArrayList<OrderDetail>();
    for (CartCommodityInfo detail : bean.getShippingList().get(0).getCommodityInfoList()) {
      // 2012/12/07 促销对应 ob add start
      if (CommodityType.GENERALGOODS.longValue().equals(detail.getCommodityType())) {
      // 2012/12/07 促销对应 ob add end  
        OrderDetail orderD = new OrderDetail();
        orderD.setCommodityCode(detail.getCommodityCode());
        orderD.setPurchasingAmount(NumUtil.toLong(detail.getQuantity() + ""));
        commodityList.add(orderD);
      // 2012/12/07 促销对应 ob add start
      }
      // 2012/12/07 促销对应 ob add end
      
    }
    campaignI.setCommodityList(commodityList);
    
    return campaignI;
  }
  
}
