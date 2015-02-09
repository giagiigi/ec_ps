package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.DeliveryDateType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.campain.CampaignInfo;
import jp.co.sint.webshop.service.campain.CampainFilter;
import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.service.customer.CustomerPointInfo;
import jp.co.sint.webshop.service.order.CommodityOfCartInfo;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.PaymentErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.PointBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean.ShippingDetailBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.customer.CustomerErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.message.back.order.PaymentErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020230:受注修正のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderModifyRegisterAction extends OrderModifyBaseAction {

  /**
   * beanのcreateAttributeを実行します。
   * 
   * @return 実行するならtrue
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    String shopCode = getBean().getShopCode();
    if (StringUtil.isNullOrEmpty(shopCode)) {
      return false;
    }
    BackLoginInfo login = getLoginInfo();
    WebshopConfig config = getConfig();
    boolean auth = false;
    if (config.getOperatingMode() == OperatingMode.MALL) {
      // モール版 許可（サイト管理者）
      if (Permission.ORDER_MODIFY_SITE.isGranted(login)) {
        auth = true;
      }
    } else if (config.getOperatingMode() == OperatingMode.SHOP) {
      // ショップ版 許可（ショップ管理者自ショップ受注）
      if (Permission.ORDER_MODIFY_SHOP.isGranted(login)) {
        auth = shopCode.equals(login.getShopCode());
      } else if (Permission.ORDER_MODIFY_SITE.isGranted(login)) {
        auth = true;
      }
    } else if (config.getOperatingMode() == OperatingMode.ONE) {
      // 一店舗版 許可（サイト管理者）
      auth = Permission.ORDER_MODIFY_SITE.isGranted(login);
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
    OrderModifyBean bean = getBean();
    boolean valid = true;
    // データ連携チェック
    String transportStatus = getBean().getOrderDataTransportFlg();
    if (StringUtil.isNullOrEmpty(transportStatus)) {
      throw new RuntimeException();
    } else if (transportStatus.equals(DataTransportStatus.TRANSPORTED.getValue())) {
      // データ連携済み
      addErrorMessage(WebMessage.get(OrderErrorMessage.DATA_TRANSPORTED));
      valid = false;
    }

    if (!isChangeAbleOrder()) {
      return false;
    }

    int shippingCount = 0;
    valid &= validateBean(bean.getOrderHeaderEdit());
    List<Sku> checkSkuList = new ArrayList<Sku>();
    for (ShippingHeaderBean header : bean.getShippingHeaderList()) {
      // 商品の存在しない出荷先は無効とする。（明細なし出荷情報は登録しない）
      valid &= validateBean(header);
      for (ShippingDetailBean detail : header.getShippingDetailList()) {
        if (validateBean(detail)) {
          // 在庫チェック
          String skuCode = detail.getSkuCode();
          String shopCode = header.getShippingShopCode();

          // 同一の規格は登録しない
          boolean add = true;
          for (Sku sku : checkSkuList) {
            if (sku.getShopCode().equals(shopCode) && sku.getSkuCode().equals(skuCode)) {
              add = false;
            }
          }
          if (add) {
            checkSkuList.add(new Sku(shopCode, skuCode));
          }
        } else {
          valid = false;
        }
      }
      shippingCount++;
    }
   
    // 購入予定の全SKUを在庫チェックする
    valid &= validationShippingDetailStock(getBean(),true);
    // 有効な（商品が存在する）出荷先が無ければエラーとする
    if (shippingCount <= 0) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.NO_SHIPPING));
      valid = false;
    }
    PointBean pointInfo = getBean().getPointInfo();

    valid &= validateBean(pointInfo);
    List<String> errors = PaymentSupporterFactory.createPaymentSuppoerter().validatePayment(getBean().getOrderPayment());
    for (String error : errors) {
      addErrorMessage(error);
      valid = false;
    }

    // 利用ポイントが使用可能ポイントより多い場合エラー
    if (pointInfo.isShort()) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.OVER_USABLE_POINT));
      valid = false;
    }

    // 商品の在庫数チェック

    for (ShippingHeaderBean shippingHeader: getBean().getShippingHeaderList()) {
    	for (ShippingDetailBean detailBean : shippingHeader.getShippingDetailList()) {
    		if (CommodityType.GENERALGOODS.longValue().equals(detailBean.getCommodityType())
    				&& StringUtil.hasValue(detailBean.getCampaignName()) && !detailBean.getDiscountType().equals("2")) {
    			valid &= this.checkCampaignDiscount(getBean(), detailBean.getSkuCode(), false);
    		}
    	}
    }
    if (valid) {
      valid = validationCombineStock();
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
    
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    ServiceResult result =null;
    
     if (bean.getNewCashierDiscount()!=null && StringUtil.hasValue(bean.getNewCashierDiscount().getDiscountCode())){
      
      CommodityHeaderDao chDao = DIContainer.getDao(CommodityHeaderDao.class);
      List<CommodityOfCartInfo> commodityListOfCart = new ArrayList<CommodityOfCartInfo>();
      
      for (ShippingHeaderBean shipping : getBean().getShippingHeaderList()) {
        for (ShippingDetailBean detail : shipping.getShippingDetailList()) {
          CommodityOfCartInfo commodityOfCart = new CommodityOfCartInfo();
          OrderDetail orderDetailCommodityInfo = detail.getOrderDetailCommodityInfo();
          commodityOfCart.setCommodityCode(orderDetailCommodityInfo.getCommodityCode());
          commodityOfCart.setCommodityName(orderDetailCommodityInfo.getCommodityName());
          commodityOfCart.setRetailPrice(orderDetailCommodityInfo.getRetailPrice());
          commodityOfCart.setPurchasingAmount(orderDetailCommodityInfo.getPurchasingAmount());
          commodityOfCart.setBrandCode(orderDetailCommodityInfo.getBrandCode());
          CommodityHeader ch = chDao.load(orderDetailCommodityInfo.getShopCode(), orderDetailCommodityInfo.getCommodityCode());
          commodityOfCart.setImportCommodityType(ch.getImportCommodityType());
          commodityListOfCart.add(commodityOfCart);
        }
      }
      
      result = orderService.checkDiscountInfo(bean.getOrderHeaderEdit().getCustomerCode(),
          bean.getNewCashierDiscount().getCouponType(),bean.getNewCashierDiscount().getDiscountCode(),
          bean.getNewCashierDiscount().getDiscountDetailCode(),bean.getAfterTotalPrice().getCommodityPrice(), commodityListOfCart);
    }
    
    if (result !=null && result.hasError()) {
    	for (ServiceErrorContent error : result.getServiceErrorList()) {
    		if (error == OrderServiceErrorContent.DISCOUNT_INFO_NOT_EXITS_ERROR) {
    			addErrorMessage("选择的优惠已被删除或者利用期间外。");
  	          	setRequestBean(bean);
  	          	return BackActionResult.RESULT_SUCCESS;
    		}
    		if (error == OrderServiceErrorContent.DISCOUNT_COUNT_OVER_ERROR) {
    			addErrorMessage("选择的优惠已超过最大利用可能回数。");
  	          	setRequestBean(bean);
  	          	return BackActionResult.RESULT_SUCCESS;
    		}
    		if (error == OrderServiceErrorContent.DISCOUNT_COUPON_USED_ERROR) {
    			addErrorMessage("选择的优惠券已使用。");
  	          	setRequestBean(bean);
  	          	return BackActionResult.RESULT_SUCCESS;
    		}
    		if (error == OrderServiceErrorContent.DISCOUNT_MIN_ORDER_PRICE_ERROR) {
    			addErrorMessage("本次商品购买金额小于使用优惠的最小购买金额。");
  	          	setRequestBean(bean);
  	          	return BackActionResult.RESULT_SUCCESS;
    		}
    	}
    }
    OrderContainer container = createOrderContainer(bean);
    container = this.recomputeOrderContainer(container);
    String deliveryAppointedDate = container.getShippings().get(0).getShippingHeader().getDeliveryAppointedDate();
    if (StringUtil.hasValue(deliveryAppointedDate) && deliveryAppointedDate.equals(DeliveryDateType.UNUSABLE.getValue())) {
      container.getShippings().get(0).getShippingHeader().setDeliveryAppointedDate(null);
    }

    // 金額上限チェック
    if (!numberLimitValidation(container)) {
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    if (hasDeffOrderContainer(container)) {
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    CashierPayment payment = PaymentSupporterFactory.createPaymentSuppoerter().createCashierPayment(bean.getOrderPayment());

    // 受注修正サービス呼出処理
    orderService = ServiceLocator.getOrderService(getLoginInfo());
    container.getOrderHeader().setLanguageCode("zh-cn");
    result = orderService.updateOrder(container, payment);
    if (result.hasError()) {
    	container = this.recomputeOrderContainer(container);
    }
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo()); // 10.1.6 10277 追加
    
    if (result.hasError()) {
      // サービスエラーの種類に応じて画面出力等処理を変える      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error == OrderServiceErrorContent.STOCK_ALLOCATE_ERROR) {
          // 在庫引当エラー          if (container.getOrderHeader().getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
            for (OrderDetail cart : container.getOrderDetails()) {
              if (!catalogService.isAvailable(cart.getShopCode(), cart.getSkuCode(), cart.getPurchasingAmount().intValue(),
                  true).equals(CommodityAvailability.AVAILABLE)) {
                addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, cart.getCommodityName()));
              } else {
                // 排他制御の衝突によるエラー (リトライは可能)
                addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_CONCURRENCY_CONTROL_ERROR));
              }
            }
          } else {
            List<OrderDetail> cartList = container.getOrderDetails();
            for (OrderDetail cart : cartList) {
              if (!catalogService.isAvailable(cart.getShopCode(), cart.getSkuCode(),
                  Integer.parseInt(Long.toString(cart.getPurchasingAmount())), false).equals(CommodityAvailability.AVAILABLE)) {
                addErrorMessage((WebMessage.get(OrderErrorMessage.NO_AVAILABLE_STOCK_SKU, cart.getSkuCode())));
              } else {
                // 排他制御の衝突によるエラー (リトライは可能)
                addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_CONCURRENCY_CONTROL_ERROR));
              }
            }
          }
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        } else if (error == OrderServiceErrorContent.CAMPAIGN_FAILED) {
          // 在庫引当エラー
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.back.order.OrderModifyRegisterAction.1")));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        } else if (error == CustomerServiceErrorContent.POINT_OVERFLOW_ERROR) {
          Length len = BeanUtil.getAnnotation(Customer.class, "restPoint", Length.class);
          String maximum = StringUtil.times("9", len.value());
          addErrorMessage(WebMessage.get(CustomerErrorMessage.POINT_OVERFLOW, maximum));
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
          addErrorMessage(WebMessage
              .get(PaymentErrorMessage.INVALID_OTHER_ERROR, container.getOrderHeader().getPaymentMethodName()));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        } else if (error == OrderServiceErrorContent.NOT_RESERVATION_CHANGEABLE) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_RESERVATION_CHANGE));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        } else if (error == OrderServiceErrorContent.OVER_USABLE_POINT_ERROR) {
          CustomerService custService = ServiceLocator.getCustomerService(getLoginInfo());
          CustomerPointInfo info = custService.getCustomerPointInfo(container.getOrderHeader().getCustomerCode());
          BigDecimal restPoint = NumUtil.coalesce(info.getRestPoint(), BigDecimal.ZERO);

          getBean().getPointInfo().setRestPoint(NumUtil.toString(restPoint));
          addErrorMessage(WebMessage.get(OrderErrorMessage.OVER_USABLE_POINT));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }else { //如果订单插入成功，则判断该订单是否是活动订单，如果是，则插入活动订单表
      //追加代码，判断当前订单是否为免邮费活动订单，如果是将这条数据插入活动订单表中
      CampainFilter cf = new CampainFilter();
      cf.insertCampainOrder(getFreeShippingCharge(container));
    }

    setNextUrl("/app/order/order_modify/init/" + container.getOrderHeader().getOrderNo() + "/" + WebConstantCode.COMPLETE_UPDATE);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * @param order
   * @return
   */
  private boolean hasDeffOrderContainer(OrderContainer order) {
    OrderModifyBean bean = getBean();
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    BigDecimal grandTotal = BigDecimal.ZERO;
    for (ShippingContainer header : order.getShippings()) {
      int itemCount = 0;
      BigDecimal subTotal = BigDecimal.ZERO;
      for (ShippingDetail detail : header.getShippingDetails()) {
        OrderDetail orgOrderDetail = bean.getOldOrderContainer().getOrderDetail(new Sku(detail.getShopCode(), detail.getSkuCode()));
        CommodityInfo commodity = catalogService.getSkuInfo(detail.getShopCode(), detail.getSkuCode());

        if (orgOrderDetail == null) {
          BigDecimal retailPrice = detail.getRetailPrice();
          if (detail.getDiscountValue()!=null){
            retailPrice = retailPrice.add(detail.getDiscountValue());
          }
        }
        Gift gift = catalogService.getGift(detail.getShopCode(), detail.getGiftCode());
        ShippingDetail detailGiftInfo = getShippingDetailFromGiftCode(header.getShippingNo(), detail.getShippingDetailNo(), detail
            .getGiftCode());
        if (detailGiftInfo != null) {
          // 新規受注に存在したギフトの場合、明細と金額が異なればエラー
          if (!detailGiftInfo.getGiftPrice().equals(detail.getGiftPrice())) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.CHANGE_MASTER_GIFT));
            return true;
          }
          // 最新のギフトが使用不可状態の場合数量チェックも行う
          if ((gift == null || isNotAvailableGift(commodity.getHeader(), gift.getGiftCode()))
              && detail.getPurchasingAmount() > detailGiftInfo.getPurchasingAmount()) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.CHANGE_MASTER_GIFT));
            return true;
          }
        } else if (StringUtil.hasValue(detail.getGiftCode())) {
          // 新しく追加したギフトの場合最新の情報を取得
          if (StringUtil.hasValue(detail.getGiftCode())) {
            if (gift == null) {
              addErrorMessage(WebMessage.get(OrderErrorMessage.CHANGE_MASTER_GIFT));
              return true;
            }
            // ギフト税込み価格を生成
            TaxUtil taxUtil = DIContainer.get("TaxUtil");
            BigDecimal giftPrice = Price.getPriceIncludingTax(gift.getGiftPrice(), taxUtil.getTaxRate(), NumUtil.toString(gift
                .getGiftTaxType()));
            if (!giftPrice.equals(detail.getGiftPrice())) {
              addErrorMessage(WebMessage.get(OrderErrorMessage.CHANGE_MASTER_GIFT));
              return true;
            }
            // 最新のギフトが使用不可状態の場合数量チェックも行う
            if (isNotAvailableGift(commodity.getHeader(), gift.getGiftCode())) {
              addErrorMessage(WebMessage.get(OrderErrorMessage.CHANGE_MASTER_GIFT));
              return true;
            }
          }
        }
        itemCount += detail.getPurchasingAmount();
        subTotal = subTotal.add(BigDecimalUtil.multiply(detail.getRetailPrice().add(detail.getGiftPrice()),
            detail.getPurchasingAmount()));
      }
      grandTotal = grandTotal.add(subTotal.add(header.getShippingHeader().getShippingCharge()));
    }
    // 支払手数料チェック
    if (!order.getOrderHeader().getPaymentMethodNo().equals(bean.getOldOrderContainer().getOrderHeader().getPaymentMethodNo())
        || !order.getOrderHeader().getUsedPoint().equals(bean.getOldOrderContainer().getOrderHeader().getUsedPoint())) {
    }
    return false;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderModifyRegisterAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102023009";
  }

  /**
   * 根据ShippingHeaderBean查看本次发货是否是免邮费
   * @param bean
   * @return
   */
  private CampaignInfo getFreeShippingCharge(OrderContainer bean){
    CampaignInfo campaignI = new CampaignInfo();
    
    campaignI.setOrderNo(bean.getOrderNo());
    campaignI.setMobileNumber(bean.getShippings().get(0).getShippingHeader().getMobileNumber());
    campaignI.setPhoneNumber(bean.getShippings().get(0).getShippingHeader().getPhoneNumber());
    campaignI.setLastName(bean.getShippings().get(0).getShippingHeader().getAddressLastName());
    campaignI.setAddress1(bean.getShippings().get(0).getShippingHeader().getAddress1());
    campaignI.setAddress2(bean.getShippings().get(0).getShippingHeader().getAddress2());
    campaignI.setAddress3(bean.getShippings().get(0).getShippingHeader().getAddress3());
    campaignI.setAddress4(bean.getShippings().get(0).getShippingHeader().getAddress4());
    campaignI.setPrefectureCode(bean.getShippings().get(0).getShippingHeader().getPrefectureCode());
    List<OrderDetail> commodityList = new ArrayList<OrderDetail>();
    
    for (OrderDetail detail : bean.getOrderDetails()) {
      if (CommodityType.GIFT.longValue().equals(detail.getCommodityType())) {
    	  continue;
      }
      OrderDetail orderD = new OrderDetail();
      orderD.setCommodityCode(detail.getCommodityCode());
      orderD.setPurchasingAmount(detail.getPurchasingAmount());
      commodityList.add(orderD);
    }
    campaignI.setCommodityList(commodityList);
    
    return campaignI;
  }
}
