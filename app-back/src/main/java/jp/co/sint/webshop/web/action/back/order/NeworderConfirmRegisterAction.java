package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.dao.CampaignConditionDao;
import jp.co.sint.webshop.data.dao.CampaignMainDao;
import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.MobileComputerType;
import jp.co.sint.webshop.data.domain.OrderFlg;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentMethodDisplayType;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.campain.CampaignInfo;
import jp.co.sint.webshop.service.campain.CampainFilter;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.CartUtil;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.CompositionItem;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
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
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderConfirmBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderResultBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.customer.CustomerErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.message.back.order.PaymentErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020160:新規受注(確認)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderConfirmRegisterAction extends NeworderBaseAction<NeworderConfirmBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = super.authorize();
    Cashier cashier = getBean().getCashier();
    if (cashier == null) {
      auth = false;
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
    boolean valid = true;
    Cashier cashier = getBean().getCashier();
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    List<Sku> useSkuList = new ArrayList<Sku>();
    Map<Sku, Integer> skuMap = new HashMap<Sku, Integer>();
    Map<Sku, CartCommodityInfo> mainCommodityDetailMap = new HashMap<Sku, CartCommodityInfo>();
    Map<Sku, GiftItem> GiftMap = new HashMap<Sku, GiftItem>();
    Map<Sku, CompositionItem> compositionMap = new HashMap<Sku, CompositionItem>();
    for (CashierShipping cashierShipping : cashier.getShippingList()) {
      for (CartCommodityInfo commodity : cashierShipping.getCommodityInfoList()) {

        if (commodity.getIsDiscountCommodity().equals("true")){
          //历史所有客户购买总数
          Long siteTotalAmount = catalogService.getHistoryBuyAmountTotal(commodity.getSkuCode());
          if (siteTotalAmount == null){
            siteTotalAmount = 0L;
          }
          String customerCode = getBean().getCashier().getCustomer().getCustomerCode();
          if (StringUtil.hasValue(customerCode)) {
            DiscountCommodity dcBean = catalogService.getDiscountCommodityByCommodityCode(commodity.getSkuCode());
            //限购商品剩余可购买数量
            Long avalibleAmountTotal = dcBean.getSiteMaxTotalNum() - siteTotalAmount;
            if (avalibleAmountTotal <= 0L){
              addErrorMessage(commodity.getCommodityName() + "限购活动可购买数量为0。" );
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
                addErrorMessage(commodity.getCommodityName() + "限购活动最多可购买数" + num + "个。" );
                return false;
              }
            } else {
              addErrorMessage(commodity.getCommodityName() + "限购活动 已购买过个人最大数量 " + dcBean.getCustomerMaxTotalNum() + "个。");
              return false ;
            }
          }
        
        if (SetCommodityFlg.OBJECTIN.longValue().equals(commodity.getSetCommodityFlg())) {
          for (CompositionItem comItem : commodity.getCompositionList()) {
            Sku sku = new Sku(comItem.getShopCode(), comItem.getSkuCode());
            if (skuMap.containsKey(sku)) {
              Integer amount = skuMap.get(sku) + commodity.getQuantity();
              skuMap.remove(sku);
              skuMap.put(sku, amount);
            } else {
              useSkuList.add(sku);
              skuMap.put(sku, commodity.getQuantity());
              compositionMap.put(sku, comItem);
            }
          }
        } else {
          Sku sku = new Sku(commodity.getShopCode(), commodity.getSkuCode());
          if (skuMap.containsKey(sku)) {
            Integer amount = skuMap.get(sku) + commodity.getQuantity();
            skuMap.remove(sku);
            skuMap.put(sku, amount);
          } else {
            useSkuList.add(sku);
            skuMap.put(sku, commodity.getQuantity());
            mainCommodityDetailMap.put(sku, commodity);
          }
        }
        
        for (GiftItem gift : commodity.getGiftList()) {
          Sku skuGift = new Sku(gift.getShopCode(), gift.getGiftSkuCode());
          if (skuMap.containsKey(skuGift)) {
            Integer amount = skuMap.get(skuGift) + gift.getQuantity();
            skuMap.remove(skuGift);
            skuMap.put(skuGift, amount);
          } else {
            useSkuList.add(skuGift);
            skuMap.put(skuGift, gift.getQuantity());
            GiftMap.put(skuGift, gift);
          }
        }
      
      }
    }
    
    for (Sku sku : useSkuList) {
      String shopCode = sku.getShopCode();
      String skuCode = sku.getSkuCode();
      CommodityAvailability commodityAvailability = CommodityAvailability.AVAILABLE;
      if (mainCommodityDetailMap.containsKey(sku)) {
        CartCommodityInfo commodity = mainCommodityDetailMap.get(sku);

        // 如果是普通商品,check普通商品的在库;如果是赠品,check赠品的在库
        if (CommodityType.GIFT.longValue().equals(commodity.getCommodityType())) {
          commodityAvailability = catalogService.isAvailableGift(shopCode, skuCode, skuMap.get(sku));
        } else {
          commodityAvailability = catalogService.isAvailable(shopCode, skuCode, skuMap.get(sku), cashier.isReserve());
        }
      } else if (GiftMap.containsKey(sku)) {
        //如果GiftMap不为空,check giftList
        GiftItem gift = GiftMap.get(sku);
        commodityAvailability = catalogService.isAvailableGift(gift.getShopCode(), gift.getGiftSkuCode(), skuMap.get(sku));
      } else if (compositionMap.containsKey(sku)) {
        CompositionItem composition = compositionMap.get(sku);
        commodityAvailability = catalogService.isAvailable(composition.getShopCode(), composition.getSkuCode(), skuMap.get(sku), cashier.isReserve());
      }
      
      if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
        valid = false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, skuCode));
        break;
      } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
        valid = false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD, skuCode));
        break;
      } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
        valid = false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.NO_AVAILABLE_STOCK_SKU, skuCode));
        break;
      } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
        valid = false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, skuCode, NumUtil.toString(catalogService
            .getAvailableStock(shopCode, skuCode))));
        break;
      } else if (commodityAvailability.equals(CommodityAvailability.RESERVATION_LIMIT_OVER)) {
        valid = false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.RESERVATION_OVER, skuCode, NumUtil.toString(catalogService
            .getReservationAvailableStock(shopCode, skuCode))));
        break;
      } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_RESERVATION_STOCK)) {
        valid = false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_AVAILABLE, skuCode));
        break;
      } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
        valid = true;
      }
    }
  }
  //2012-11-27 促销对应 ob update end
    valid &= numberLimitValidation(getBean());
    valid &= discountCouponLimtedValidation();
    
    if (valid) {
      for (CashierShipping cashierShipping : cashier.getShippingList()) {
        for (CartCommodityInfo commodity : cashierShipping.getCommodityInfoList()) {
          Long allCount = 0L;
          Long allStock = 0L;
          Long quantity = commodity.getQuantity() + 0L;
          if (StringUtil.hasValue(commodity.getOriginalCommodityCode())) {
            allStock = catalogService.getAvailableStock(commodity.getShopCode(), commodity.getOriginalCommodityCode());
            allCount = quantity * commodity.getCombinationAmount();
          } else {
            allStock = catalogService.getAvailableStock(commodity.getShopCode(), commodity.getSkuCode());
            allCount = quantity;
          }

          for (CartCommodityInfo commodityIn : cashierShipping.getCommodityInfoList()) {
            if (!commodityIn.getCommodityCode().equals(commodity.getCommodityCode())) {
              boolean rel = false;
              if (StringUtil.hasValue(commodity.getOriginalCommodityCode())) {
                rel = (commodity.getOriginalCommodityCode().equals(commodityIn.getCommodityCode()) || (StringUtil.hasValue(commodityIn
                    .getOriginalCommodityCode()) && commodity.getOriginalCommodityCode().equals(commodityIn.getOriginalCommodityCode())));
              } else {
                rel = (StringUtil.hasValue(commodityIn.getOriginalCommodityCode()) && commodity.getCommodityCode().equals(
                    commodityIn.getOriginalCommodityCode()));
              }

              if (rel) {
                if (StringUtil.hasValue(commodityIn.getOriginalCommodityCode())) {
                  allCount = allCount + commodityIn.getQuantity() * commodityIn.getCombinationAmount();
                } else {
                  allCount = allCount + commodityIn.getQuantity();
                }
              }
            }
          }

          if (allCount > allStock) {
            addErrorMessage("可用库存不足,商品编号：" + commodity.getCommodityCode());
            valid &= false;
          }
        }
      }
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
    ServiceResult result = null;
    
    if (getBean().getCashier().getDiscount() != null && StringUtil.hasValue(getBean().getCashier().getDiscount().getDiscountCode())) {
      if (!checkDiscountInfo(getBean().getCashier())) {
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      }
    }
    
    OrderContainer order = CartUtil.createOrderContainer(getBean().getCashier());
    order.setOrderMailType(OrderMailType.PC);
    order.setOrderSmsType(OrderSmsType.MOBILE);
    order.getOrderHeader().setOrderFlg(OrderFlg.CHECKED.longValue());
    order.getOrderHeader().setUseAgent("back_order");
    order.getOrderHeader().setOrderClientType("1");
    order.getOrderHeader().setGiftCardUsePrice(BigDecimal.ZERO);
    order.getOrderHeader().setOuterCardPrice(getBean().getCashier().getOuterCardUseAmount());
    if (getBean().getCashier().getOuterCardUseAmount() != null && !BigDecimalUtil.equals(getBean().getCashier().getOuterCardUseAmount(), BigDecimal.ZERO)) {
      // 订单拦截处理
      order.getOrderHeader().setOrderFlg(OrderFlg.NOT_CHECKED.longValue());
      String message = getBean().getCashier().getMessage();
      if (StringUtil.isNullOrEmpty(message)) {
        message = "";
      }
      order.getOrderHeader().setCaution("(外卡支付订单需要进行确认)" + message);
    }
    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    PaymentMethodSuite paymentMethodSuite = shopService.getPaymentMethod(getBean().getCashier().getPaymentShopCode(), NumUtil
        .toLong(getBean().getCashier().getPayment().getPaymentMethodCode()));

    Long paymentMethodDisplayType = paymentMethodSuite.getPaymentMethod().getPaymentMethodDisplayType();

    if (paymentMethodSuite.getPaymentMethod() == null
        || (!paymentMethodDisplayType.equals(PaymentMethodDisplayType.ALL.longValue()) && !paymentMethodDisplayType
            .equals(PaymentMethodDisplayType.BACK.longValue()))) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.NO_PAYMENT_METHOD_ERROR));
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    order.getOrderHeader().setLanguageCode("zh-cn");
//    List<String> interceptDeliveryCompanyList = DIContainer.getWebshopConfig().getInterceptDeliveryCompanyList();
    for (CashierShipping shipping : getBean().getCashier().getShippingList()) {
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
//          if (header.getNewReserveCommodityType2() == 1L) {
//            if (!interceptDeliveryCompanyList.contains(getBean().getCashier().getDelivery().getDeliveryCompanyCode())) {
//              order.getOrderHeader().setOrderFlg(OrderFlg.GROUPCHECKED.longValue());
//              if (getBean().getCashier().getMessage() != null) {
//                order.getOrderHeader().setCaution("(高价值商品未选择指定快递公司)" + getBean().getCashier().getMessage());
//              } else {
//                order.getOrderHeader().setCaution("(高价值商品未选择指定快递公司)");
//              }
//              break;
//            }
//          }
          
        }
      }
    }

    result = service.createNewOrder(order, getBean().getCashier(),MobileComputerType.PC.getValue()); // PC做成订单区分

    if (result.hasError()) {
      // サービスエラーの種類に応じて画面出力等処理を変える
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error == CustomerServiceErrorContent.CUSTOMER_DELETED_ERROR) {
          // 顧客存在エラー
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.back.order.NeworderConfirmRegisterAction.0")));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        } else if (error == OrderServiceErrorContent.STOCK_ALLOCATE_ERROR) {
          // 在庫引当エラー
          if (order.getOrderHeader().getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
            for (OrderDetail cart : order.getOrderDetails()) {
              if (!catalogService.isAvailable(cart.getShopCode(), cart.getSkuCode(), cart.getPurchasingAmount().intValue(),
                  getBean().getCashier().isReserve()).equals(CommodityAvailability.AVAILABLE)) {
                addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, cart.getCommodityName()));
              } else {
                // 排他制御の衝突によるエラー (リトライは可能)
                addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_CONCURRENCY_CONTROL_ERROR));
              }
            }
          } else {
            List<OrderDetail> cartList = order.getOrderDetails();
            for (OrderDetail cart : cartList) {
              OrderStatus orderStatus = OrderStatus.fromValue(order.getOrderHeader().getOrderStatus());
              boolean isReserve = orderStatus == OrderStatus.RESERVED;
              CommodityAvailability commodityAvailability = catalogService.isAvailable(cart.getShopCode(), cart.getSkuCode(),
                  Integer.parseInt(Long.toString(cart.getPurchasingAmount())), isReserve);
              if (commodityAvailability != CommodityAvailability.AVAILABLE) {
                addErrorMessage((WebMessage.get(OrderErrorMessage.NO_AVAILABLE_STOCK_SKU, cart.getSkuCode())));
              } else {
                // 排他制御の衝突によるエラー (リトライは可能)
                addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_CONCURRENCY_CONTROL_ERROR));
              }
            }
          }
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        } else if (error == CustomerServiceErrorContent.POINT_OVERFLOW_ERROR) {
          Length len = BeanUtil.getAnnotation(Customer.class, "restPoint", Length.class);
          String maximum = StringUtil.times("9", len.value());
          addErrorMessage(WebMessage.get(CustomerErrorMessage.POINT_OVERFLOW, maximum));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        } else if (error == OrderServiceErrorContent.OVER_USABLE_POINT_ERROR) {
          CustomerService custService = ServiceLocator.getCustomerService(getLoginInfo());
          CustomerPointInfo info = custService.getCustomerPointInfo(order.getOrderHeader().getCustomerCode());
          BigDecimal restPoint = NumUtil.coalesce(info.getRestPoint(), BigDecimal.ZERO);
          // ポイント情報を更新する
          getBean().getCashier().getCustomer().setRestPoint(restPoint);
          addErrorMessage(WebMessage.get(OrderErrorMessage.OVER_USABLE_POINT, NumUtil.toString(restPoint)));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        } else if (error == OrderServiceErrorContent.CAMPAIGN_FAILED) {
          // 在庫引当エラー          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.back.order.NeworderConfirmRegisterAction.1")));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        } else if (error == PaymentErrorContent.INVALID_CARD_INFORMATION_ERROR) {
          addErrorMessage(WebMessage.get(PaymentErrorMessage.INVALID_CARD_INFORMATION_ERROR));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        } else if (error == PaymentErrorContent.INVALID_CUSTOMER_NAME_ERROR) {
          addErrorMessage(WebMessage.get(PaymentErrorMessage.INVALID_CUSTOMER_NAME_ERROR));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        } else if (error == PaymentErrorContent.INVALID_EMAIL_ERROR) {
          addErrorMessage(WebMessage.get(PaymentErrorMessage.INVALID_EMAIL_ERROR));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        } else if (error == PaymentErrorContent.INVALID_OTHER_PARAMETER_ERROR || error == PaymentErrorContent.OTHER_PAYMENT_ERROR) {
          addErrorMessage(WebMessage.get(PaymentErrorMessage.INVALID_OTHER_ERROR, order.getOrderHeader().getPaymentMethodName()));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }else { //如果订单插入成功，则判断该订单是否是活动订单，如果是，则插入活动订单表
      // add by lc 2012-10-18 start
      //追加代码，判断当前订单是否为免邮费活动订单，如果是将这条数据插入活动订单表中
      CampainFilter cf = new CampainFilter();
      cf.insertCampainOrder(getFreeShippingCharge(order, getBean().getCashier()));
      // add by lc 2012-10-18 end   
    }

    // カート情報の削除
    for (CashierShipping shipping : getBean().getCashier().getShippingList()) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        getCart().remove(commodity.getShopCode(), commodity.getSkuCode(), commodity.getGiftCode());
      }
    }
    // Cashierのクリア
    getBean().getCashier().clear();

    setNextUrl("/app/order/neworder_result/init/");
    // tmpBeanのクリア
    getSessionContainer().getTempBean();
    // 前画面情報をクリア
    getSessionContainer().clearBackTransitionInfoList();

    NeworderResultBean nextBean = new NeworderResultBean();
    nextBean.setOrderNo(order.getOrderHeader().getOrderNo());
    nextBean.setOrderDatetime(DateUtil
        .toDateTimeString(order.getOrderHeader().getOrderDatetime(), DateUtil.DEFAULT_DATETIME_FORMAT));
    nextBean.setLastName(order.getOrderHeader().getLastName());
    nextBean.setFirstName(order.getOrderHeader().getFirstName());
    nextBean.setCustomerCode(order.getOrderHeader().getCustomerCode());
    this.setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderConfirmRegisterAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102016003";
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
      //2012-12-7 促销对应 ob update start
      //免运费只考虑普通商品,赠品不考虑
      if (CommodityType.GENERALGOODS.longValue().equals(detail.getCommodityType())) {
        OrderDetail orderD = new OrderDetail();
        orderD.setCommodityCode(detail.getCommodityCode());
        orderD.setPurchasingAmount(NumUtil.toLong(detail.getQuantity()+""));
        commodityList.add(orderD);
      }
      //2012-12-7 促销对应 ob update end
    }
    campaignI.setCommodityList(commodityList);
    
    return campaignI;
  }
  //2012-12-6 促销对应 ob add start
//折扣券使用限制check
  public boolean discountCouponLimtedValidation() {
    boolean valid = true;
    Cashier cashier = getBean().getCashier();
    List<CashierShipping> shippingList = cashier.getShippingList();
    Map<String, Long> discountCouponUsedMap = new HashMap<String, Long>();
    for (CashierShipping shipping : shippingList) {
      List<CartCommodityInfo> orderCommodityList = shipping.getCommodityInfoList();
      for (CartCommodityInfo orderCommodity : orderCommodityList) {
        String campaignCode = orderCommodity.getCampaignCouponCode();
        if (discountCouponUsedMap.containsKey(campaignCode)) {
          Long count = discountCouponUsedMap.get(campaignCode) + 1;
          discountCouponUsedMap.remove(campaignCode);
          discountCouponUsedMap.put(campaignCode, count);
        } else {
          discountCouponUsedMap.put(campaignCode, 1L);
        }
      }
    }
    
    for (CashierShipping shipping : shippingList) {
      List<CartCommodityInfo> orderCommodityList = shipping.getCommodityInfoList();
      for (CartCommodityInfo orderCommodity : orderCommodityList) {
        String campaignCode = orderCommodity.getCampaignCouponCode();
        if (StringUtil.isNullOrEmpty(campaignCode)) {
          continue;
        }
        CampaignMainDao mainDao = DIContainer.getDao(CampaignMainDao.class);
        CampaignMain main = mainDao.load(campaignCode);
        //促销活动不存在
        if (null == main) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_NOT_EXIST,
              Messages.getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
          return false;
        }
        //促销活动期间不是进行中
        if (!DateUtil.isPeriodDate(main.getCampaignStartDate(), main.getCampaignEndDate())) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_NOT_EXIST,
              Messages.getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
          return false;
        }
        //促销行为类型不是商品优惠券
        if (!CampaignMainType.SALE_OFF.longValue().equals(main.getCampaignType())) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_NOT_EXIST,
              Messages.getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
          return false; 
        }
        
        CampaignConditionDao conditionDao = DIContainer.getDao(CampaignConditionDao.class);
        CampaignCondition condition = conditionDao.load(campaignCode);
        if (null == condition) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_NOT_EXIST,
              Messages.getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
          return false;
        }
        
      //判断商品在不在折扣券的使用范围内
        CommunicationService cmuSvr = ServiceLocator.getCommunicationService(getLoginInfo());
        if (!cmuSvr.isCampaignExistCommodit(orderCommodity.getShopCode(), orderCommodity.getCommodityCode(), campaignCode)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_CANNT_USED_ERROR,
              Messages.getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
          return false;
        }
        //判断折扣券的对象商品限定件数
        if (condition.getMaxCommodityNum() != null) {
          if (condition.getMaxCommodityNum() > orderCommodity.getQuantity()) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_MAX_COMMODITY_ERROR, condition.getMaxCommodityNum().toString(), campaignCode));
            return false;
          }
        }
        
        //判断折扣券的最大使用回数
        Long count = 0L;
        if (!discountCouponUsedMap.isEmpty()) {
          if (discountCouponUsedMap.containsKey(campaignCode)) {
            count = discountCouponUsedMap.get(campaignCode);
          }
        }
        
        OrderService orderSvr = ServiceLocator.getOrderService(getLoginInfo());
        count += orderSvr.getCampaignDiscountUsedCount(campaignCode, "");
        if (condition.getUseLimit() != null) {
          if (count > condition.getUseLimit()) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_MAX_USED_ERROR, campaignCode,
                Messages.getString("web.action.back.order.NeworderShippingDiscountCouponAction.0") ));
            return false;
          }
        }
      }
    }
    return valid;
  }
  //2012-12-6 促销对应 ob add end
}
