package jp.co.sint.webshop.service.cart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.dao.CampaignDoingsDao;
import jp.co.sint.webshop.data.dao.DeliveryCompanyDao;
import jp.co.sint.webshop.data.dao.PaymentMethodDao;
import jp.co.sint.webshop.data.domain.AdvanceLaterType;
import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.DeliveryDateType;
import jp.co.sint.webshop.data.domain.GuestFlg;
import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.InvoiceSaveFlg;
import jp.co.sint.webshop.data.domain.InvoiceType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.data.domain.ReturnItemType;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CampaignDoings;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.CustomerVatInvoice;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.DeliveryRegion;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.DiscountHeader;
import jp.co.sint.webshop.data.dto.OrderCampaign;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.OrderInvoice;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingDetailComposition;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.CompositionItem;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.service.cart.impl.CashierImpl;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.service.shop.PaymentMethodSuite;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;

public final class CartUtil {

  private CartUtil() {

  }

  /**
   * 通常注文時のCart情報を元にCashierを生成する。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>Cart情報・顧客情報・Cartから抽出する条件を受け取りCashierを生成します。
   * <ol>
   * <li>引数で受取ったCartから、決済を行う商品を抽出します。</li>
   * <li>空のCashierを生成します。</li>
   * <li>1で取得した商品を、使用可能商品としてCashierに設定します。</li>
   * <li>引数で受け取った顧客情報をCashierに設定します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>cartはnullでないものとします。</dd>
   * <dd>shopCodeはnullでないものとします。</dd>
   * <dd>paymentCustomerはnullでないものとします。</dd>
   * <dd>決済商品は予約商品でないものとします</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param cart
   *          カート情報
   * @param shopCode
   *          決済ショップコード
   * @param paymentCustomer
   *          支払ユーザ情報
   * @return 決済情報
   * @see #createCashier(Cart cart, String shopCode, CustomerInfo
   *      paymentCustomer, Sku skuSet)
   */
  public static Cashier createCashier(Cart cart, String shopCode, CustomerInfo paymentCustomer) {
    return createCashier(cart, shopCode, paymentCustomer, new Sku("", ""));
  }

  /**
   * Cart情報を元にCashierを生成します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>Cart情報・顧客情報・Cartから抽出する条件を受け取りCashierを生成します。
   * <ol>
   * <li>引数で受取ったCartから、決済を行う商品を抽出します。</li>
   * <li>空のCashierを生成します。</li>
   * <li>1で取得した商品を、使用可能商品としてCashierに設定します。</li>
   * <li>引数で受け取った顧客情報をCashierに設定します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>cartはnullでないものとします。</dd>
   * <dd>shopCodeはnullでないものとします。</dd>
   * <dd>paymentCustomerはnullでないものとします。</dd>
   * <dd>skuSetはnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param cart
   *          カート情報
   * @param shopCode
   *          決済ショップコード
   * @param paymentCustomer
   *          支払ユーザ情報
   * @param skuSet
   *          予約商品の決済だった場合の予約コード<BR>
   *          通常注文の場合は空のStringを渡す
   * @return 決済情報
   */
  public static Cashier createCashier(Cart cart, String shopCode, CustomerInfo paymentCustomer, Sku skuSet) {
    // 使用するサービスを生成

    ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());
    CatalogService catalogService = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());

    List<CartCommodityInfo> usableCommodityInfo = new ArrayList<CartCommodityInfo>();

    // 2012/11/24 促销对应 ob add start
    List<CartCommodityInfo> otherGiftList = new ArrayList<CartCommodityInfo>();
    // 2012/11/24 促销对应 ob add end

    List<CartItem> cartItemList = new ArrayList<CartItem>();
    WebshopConfig config = DIContainer.getWebshopConfig();
    if (StringUtil.isNullOrEmpty(skuSet.getShopCode()) && StringUtil.isNullOrEmpty(skuSet.getSkuCode())) {
      // 予約以外の場合

      if (config.getOperatingMode() == OperatingMode.MALL || config.getOperatingMode() == OperatingMode.ONE) {
        // モール一括決済と一店舗版の場合、全商品を使用する

        cartItemList = cart.get();
      } else if (config.getOperatingMode() == OperatingMode.SHOP) {
        // ショップ個別決済の場合

        cartItemList = cart.get(shopCode);
      }
    } else {
      cartItemList.add(cart.getReserve(skuSet.getShopCode(), skuSet.getSkuCode()));
    }

    CashierImpl cashier = new CashierImpl(StringUtil.hasValue(skuSet.getShopCode()));
    cashier.setCustomer(paymentCustomer.getCustomer(), paymentCustomer.getAddress());

    CashierPayment payment = new CashierPayment();
    // 支払方法の設定

    payment.setShopCode(shopCode);
    cashier.setPayment(payment);
    cashier.setOpCommodityCodeList(cart.getOpCommodityCodeList());
    cashier.setOptionalCheapPrice(cart.getOptionalCheapPrice());
    cashier.setOptionalCommodityMap(cart.getOptionalCommodityMap());
    for (CartItem cartItem : cartItemList) {

      // 多重促销活动的赠品一览生成
      if (CommodityType.GIFT.longValue().equals(cartItem.getCommodityType())) {
        otherGiftList.add(cartItem.getCommodityInfo());
      }

      // 初期商品の配送先は自分に設定
      CustomerAddress address = paymentCustomer.getAddress();

      // 2012/11/26 促销对应 ob update start
      CommodityInfo commodity = new CommodityInfo();

      if (CommodityType.GENERALGOODS.longValue().equals(cartItem.getCommodityType())) {
        if (SetCommodityFlg.OBJECTIN.longValue().equals(cartItem.getCommodityInfo().getSetCommodityFlg())) {
          String[] tmp = cartItem.getCommodityInfo().getSkuCode().split(":");
          if (tmp.length > 0) {
            commodity = catalogService.getSkuInfo(cartItem.getShopCode(), tmp[0]);
          } else {
            commodity = catalogService.getSkuInfo(cartItem.getShopCode(), cartItem.getSkuCode());
          }
        } else {
          commodity = catalogService.getSkuInfo(cartItem.getShopCode(), cartItem.getSkuCode());
        }
      }

      if (CommodityType.GIFT.longValue().equals(cartItem.getCommodityType())) {
        String[] tmp = cartItem.getSkuCode().split("~");
        if (tmp.length > 1) {
          commodity = catalogService.getSkuInfo(cartItem.getShopCode(), cartItem.getSkuCode().split("~")[1]);
        } else {
          commodity = catalogService.getSkuInfo(cartItem.getShopCode(), cartItem.getSkuCode());
        }
      }

      // 2012/11/26 促销对应 ob update end

      DeliveryType deliveryType = shopService.getDeliveryType(commodity.getHeader().getShopCode(), commodity.getHeader()
          .getDeliveryTypeNo());

      
      // 使用可能な商品情報の一覧を生成
      if (CommodityType.GENERALGOODS.longValue().equals(cartItem.getCommodityType())) {
        DiscountCommodity dCommodity = catalogService.getDiscountCommodityByCommodityCode(cartItem.getCommodityCode());
        //历史所有客户购买总数
        Long siteTotalAmount = 0L;
        if (dCommodity != null){
          siteTotalAmount= catalogService.getHistoryBuyAmountTotal(cartItem.getCommodityCode());
          if (siteTotalAmount == null){
            siteTotalAmount = 0L;
          }
        }
        
        //当前登录客户历史购买数量
        Long historyNum = catalogService.getHistoryBuyAmount(cartItem.getCommodityCode(), paymentCustomer.getCustomer().getCustomerCode());
        if (historyNum == null){
          historyNum = 0L;
        }
        
        if (dCommodity != null && dCommodity.getSiteMaxTotalNum() > siteTotalAmount 
            && !paymentCustomer.getCustomer().getCustomerCode().equals("guest")){
          Long quantity = 0L;
          //限购商品剩余可购买数量
          Long avalibleAmount = dCommodity.getSiteMaxTotalNum() - siteTotalAmount;
          if(avalibleAmount < 0L){
            avalibleAmount = 0L;
          }
          BigDecimal price1 = cartItem.getRetailPrice();
          BigDecimal price2 = cartItem.getUnitPrice();
//          if(StringUtil.hasValue(cartItem.getCommodityInfo().getOriginalCommodityCode()) && cartItem.getCommodityInfo().getCombinationAmount() != null) {
//            price1 = BigDecimalUtil.divide(cartItem.getRetailPrice(),cartItem.getCommodityInfo().getCombinationAmount());
//            price2 = BigDecimalUtil.divide(cartItem.getUnitPrice(),cartItem.getCommodityInfo().getCombinationAmount());
//          } else {
//            price1 = cartItem.getRetailPrice();
//            price2 = cartItem.getUnitPrice();
//          }
          if (dCommodity.getCustomerMaxTotalNum()!=null){
            //当前客户可购买限购商品数
            quantity = dCommodity.getCustomerMaxTotalNum() - historyNum;
            if (quantity < 0L){
              quantity = 0L;
            }
            if(quantity > avalibleAmount){
              quantity = avalibleAmount;
            }
          }
          if (dCommodity.getDiscountPrice() != null){
            if(StringUtil.hasValue(cartItem.getCommodityInfo().getOriginalCommodityCode()) && cartItem.getCommodityInfo().getCombinationAmount() != null) {
              cartItem.getCommodityInfo().setRetailPrice(BigDecimalUtil.divide(dCommodity.getDiscountPrice(),cartItem.getCommodityInfo().getCombinationAmount(),2,RoundingMode.DOWN));
              cartItem.getCommodityInfo().setUnitPrice(BigDecimalUtil.divide(price2,cartItem.getCommodityInfo().getCombinationAmount(),2,RoundingMode.DOWN));
            } else {
              cartItem.getCommodityInfo().setRetailPrice(dCommodity.getDiscountPrice());
              cartItem.getCommodityInfo().setUnitPrice(price2);
            }
//            cartItem.getCommodityInfo().setUnitPrice(dCommodity.getDiscountPrice());
//            cartItem.getCommodityInfo().setRetailPrice(dCommodity.getDiscountPrice());
          }
          
          int totalNum = cartItem.getQuantity();
          if (totalNum <= quantity){
            cartItem.getCommodityInfo().setIsDiscountCommodity("true");
            usableCommodityInfo.add(cartItem.getCommodityInfo());
            cashier.addCashierItem(address, deliveryType, cartItem.getCommodityInfo());
          } else {
            if(quantity != 0L){
              cartItem.getCommodityInfo().setQuantity(Integer.parseInt(quantity.toString()));
              cartItem.getCommodityInfo().setIsDiscountCommodity("true");
              CartCommodityInfo info = createInfo(cartItem.getCommodityInfo());
              usableCommodityInfo.add(info);
              cashier.addCashierItem(address, deliveryType, info);
            }
            if(StringUtil.hasValue(cartItem.getCommodityInfo().getOriginalCommodityCode()) && cartItem.getCommodityInfo().getCombinationAmount() != null) {
              cartItem.getCommodityInfo().setRetailPrice(BigDecimalUtil.divide(price1,cartItem.getCommodityInfo().getCombinationAmount(),2,RoundingMode.DOWN));
              cartItem.getCommodityInfo().setUnitPrice(BigDecimalUtil.divide(price2,cartItem.getCommodityInfo().getCombinationAmount(),2,RoundingMode.DOWN));
            } else {
              cartItem.getCommodityInfo().setRetailPrice(price1);
              cartItem.getCommodityInfo().setUnitPrice(price2);
            }
//            cartItem.getCommodityInfo().setRetailPrice(price1);
//            cartItem.getCommodityInfo().setUnitPrice(price2);
            cartItem.getCommodityInfo().setIsDiscountCommodity("false");
            cartItem.getCommodityInfo().setQuantity(totalNum - Integer.parseInt(quantity.toString()));
            CartCommodityInfo info2 = createInfo(cartItem.getCommodityInfo());
            usableCommodityInfo.add(info2);
            cashier.addCashierItem(address, deliveryType, info2);
          } 
          cartItem.getCommodityInfo().setIsDiscountCommodity("true");
          if(StringUtil.hasValue(cartItem.getCommodityInfo().getOriginalCommodityCode()) && cartItem.getCommodityInfo().getCombinationAmount() != null) {
            cartItem.getCommodityInfo().setRetailPrice(BigDecimalUtil.divide(price1,cartItem.getCommodityInfo().getCombinationAmount(),2,RoundingMode.DOWN));
            cartItem.getCommodityInfo().setUnitPrice(BigDecimalUtil.divide(price2,cartItem.getCommodityInfo().getCombinationAmount(),2,RoundingMode.DOWN));
          } else {
            cartItem.getCommodityInfo().setRetailPrice(price1);
            cartItem.getCommodityInfo().setUnitPrice(price2);
          }
//          cartItem.getCommodityInfo().setRetailPrice(price1);
//          cartItem.getCommodityInfo().setUnitPrice(price2);
          cartItem.setQuantity(totalNum);
        } else {
          usableCommodityInfo.add(cartItem.getCommodityInfo());
          cashier.addCashierItem(address, deliveryType, cartItem.getCommodityInfo());
        }

      }

    }

    cashier.setUsableCommodity(usableCommodityInfo);

    // 2012/11/24 促销对应 ob add start
    cashier.setOtherGiftList(otherGiftList);
    // 2012/11/24 促销对应 ob add end

    // 20121224 txw add start
    cashier.setItemCount(cart.getItemCountExceptGift());

    // 20121224 txw add end
    return cashier;
  }

  /**
   * Cashierクラスから、受注データ用データセットのOrderContainerを生成します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>Cashierを受け取り、OrderContainerを返します。
   * <ol>
   * <li>OrderContainerを新しく生成します。</li>
   * <li>引数で受取った決済情報から受注ヘッダを取得し、1で生成したOrderContainerに設定します。</li>
   * <li>ShippingContainerのListを新しく生成します。</li>
   * <li>決済情報の出荷リスト1件ごとに、5～13の処理を行います。</li>
   * <li>ShippingContainerを新しく生成します。</li>
   * <li>2で値を設定したOrderContainerの受注ヘッダ情報、決済情報の支払情報・出荷情報から出荷ヘッダを取得します。</li>
   * <li>6で取得した出荷ヘッダを、5で生成したShippingContainerに設定します。</li>
   * <li>出荷明細のリストを新しく生成します。</li>
   * <li>決済情報の商品情報リスト1件ごとに、10～11の処理を行います。</li>
   * <li>出荷明細を、商品情報をもとに新しく生成します。</li>
   * <li>10で生成した出荷明細情報を、8で生成した出荷明細のリストに加えます。</li>
   * <li>5で生成したShippingContainerに8で生成した出荷明細のリストを設定します。</li>
   * <li>3で生成したShippingContainerのリストに、5で生成したShippingContainerを加えます。</li>
   * <li>1で生成したOrderContainerに14で値を設定したShippingContainerのリストを設定します。</li>
   * <li>1で生成したOrderContainerに決済情報から生成した受注明細情報を設定します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>cashierはnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param cashier
   *          決済情報
   * @return 受注データセット
   */
  public static OrderContainer createOrderContainer(Cashier cashier) {
    CatalogService catalogService = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
    
    OrderContainer orderContainer = new OrderContainer();

    orderContainer.setOrderHeader(createOrderHeader(cashier));

    List<ShippingContainer> shippingContainerList = new ArrayList<ShippingContainer>();
    // 2012-11-27 促销对应 ob add start
    List<OrderCampaign> orderCampaignList = new ArrayList<OrderCampaign>();
    // 2012-11-27 促销对应 ob add end
    List<ShippingDetailComposition> shippingDtailCompositions = new ArrayList<ShippingDetailComposition>();

    List<Sku> useSkuList = new ArrayList<Sku>();
    Map<Sku, Integer> skuMap = new HashMap<Sku, Integer>();
    Map<Sku, GiftItem> mainCommodityDetailMap = new HashMap<Sku, GiftItem>();
    List<ShippingDetail> shippingDetailList = new ArrayList<ShippingDetail>();
    for (CashierShipping cashierShipping : cashier.getShippingList()) {
      ShippingContainer shippingContainer = new ShippingContainer();
      ShippingHeader shippingHeader = createShippingHeader(orderContainer.getOrderHeader(), cashier.getPayment(), cashierShipping,
          cashier);
      shippingContainer.setShippingHeader(shippingHeader);

      HashMap<ShippingDetail, List<ShippingDetailComposition>> shippingDetailContainerMap = new HashMap<ShippingDetail, List<ShippingDetailComposition>>();
      for (CartCommodityInfo commodity : cashierShipping.getCommodityInfoList()) {
        // 2012-12-3 促销对应 ob add start
        CartCommodityInfo newCommodity = (CartCommodityInfo) commodity.clone();
        if (SetCommodityFlg.OBJECTIN.longValue().equals(commodity.getSetCommodityFlg())) {
          String[] tmp = commodity.getSkuCode().split(":");
          if (tmp.length > 0) {
            newCommodity.setSkuCode(tmp[0]);
          }
        }
        // 2012-12-3 促销对应 ob add end
        ShippingDetail shippingDetail = createShippingDetail(newCommodity,catalogService,cashier.getCustomer().getCustomerCode());
        if(StringUtil.hasValue(commodity.getOriginalCommodityCode())){
          shippingDetail.setSetCommodityFlg(Long.parseLong(SetCommodityFlg.OBJECTIN.getValue()));
        }
        shippingDetailList.add(shippingDetail);
        // 2012-11-27 促销对应 ob add start
        if (commodity.getGiftList() != null && commodity.getGiftList().size() > 0) {
          for (GiftItem gift : commodity.getGiftList()) {
            Sku sku = new Sku(gift.getShopCode(), gift.getGiftSkuCode());
            sku.setCampaignCode(gift.getCampaignCode());
            if (skuMap.containsKey(sku)) {
              Integer amount = skuMap.get(sku) + gift.getQuantity();
              skuMap.remove(sku);
              skuMap.put(sku, amount);
            } else {
              useSkuList.add(sku);
              skuMap.put(sku, gift.getQuantity());
              mainCommodityDetailMap.put(sku, gift);
            }
          }
        }

        if (CommodityType.GIFT.longValue().equals(commodity.getCommodityType())) {
          if (orderCampaignExist(orderCampaignList, commodity.getMultipleCampaignCode())) {
            continue;
          }
          // 创建促销订单信息(多重优惠促销)
          OrderCampaign orderCampaign = new OrderCampaign();
          orderCampaign.setCampaignCode(commodity.getMultipleCampaignCode());
          orderCampaign.setCampaignName(commodity.getMultipleCampaignName());
          orderCampaign.setCampaignType(CampaignMainType.MULTIPLE_GIFT.longValue());
          CampaignDoingsDao doingDao = DIContainer.getDao(CampaignDoingsDao.class);
          CampaignDoings doing = doingDao.load(commodity.getMultipleCampaignCode());
          if (doing != null && StringUtil.hasValue(doing.getAttributrValue())) {
            orderCampaign.setAttributrValue(doing.getAttributrValue());
          } else {
            orderCampaign.setAttributrValue("");
          }

          orderCampaignList.add(orderCampaign);
        } else {
          // 非赠品的要查看一下有没有折扣券
          if (StringUtil.hasValue(commodity.getCampaignCouponCode())) {
            if (orderCampaignExist(orderCampaignList, commodity.getCampaignCouponCode())) {
              continue;
            }
            // 创建促销订单信息(商品优惠券)
            OrderCampaign orderCampaign = new OrderCampaign();
            orderCampaign.setCampaignCode(commodity.getCampaignCouponCode());
            orderCampaign.setCampaignName(commodity.getCampaignCouponName());
            orderCampaign.setCampaignType(CampaignMainType.SALE_OFF.longValue());
            CampaignDoingsDao doingDao = DIContainer.getDao(CampaignDoingsDao.class);
            CampaignDoings doing = doingDao.load(commodity.getCampaignCouponCode());
            if (doing != null && StringUtil.hasValue(doing.getAttributrValue())) {
              orderCampaign.setAttributrValue(doing.getAttributrValue());
            } else {
              orderCampaign.setAttributrValue("");
            }

            orderCampaignList.add(orderCampaign);
          }

          if(StringUtil.hasValue(commodity.getOriginalCommodityCode() )){
            List<ShippingDetailComposition> compositions = new ArrayList<ShippingDetailComposition>();
            ShippingDetailComposition compositionDetail = new ShippingDetailComposition();
            compositionDetail.setShopCode(commodity.getShopCode());
            compositionDetail.setParentCommodityCode(commodity.getCommodityCode());
            compositionDetail.setParentSkuCode(commodity.getSkuCode());
            // 原商品编号、商品名称
            CommodityInfo cInfo = catalogService.getCommodityInfo(commodity.getShopCode(), commodity.getOriginalCommodityCode());
            compositionDetail.setChildCommodityCode(cInfo.getHeader().getCommodityCode());
            compositionDetail.setChildSkuCode(cInfo.getDetail().getSkuCode());
            compositionDetail.setCommodityName(cInfo.getHeader().getCommodityName());
            compositionDetail.setStandardDetail1Name(cInfo.getDetail().getStandardDetail1Name());
            compositionDetail.setStandardDetail2Name(cInfo.getDetail().getStandardDetail2Name());
            
            compositionDetail.setUnitPrice(commodity.getOriginalUnitPrice());
            BigDecimal discountPrice = BigDecimalUtil.subtract(commodity.getOriginalUnitPrice(), commodity.getOriginalRetailPrice());
            compositionDetail.setDiscountAmount(discountPrice);
            compositionDetail.setRetailPrice(commodity.getOriginalRetailPrice());
            compositionDetail.setPurchasingAmount(commodity.getQuantity()*commodity.getCombinationAmount());
            TaxUtil u = DIContainer.get("TaxUtil");
            compositionDetail.setCommodityTaxRate(u.getTaxRate());
//            compositionDetail.setRetailTax(new BigDecimal(commodity.getRetailTax()));
//            compositionDetail.setCommodityTax(commodity.getCommodityTax());
            compositionDetail.setCommodityTaxType(commodity.getCommodityTaxType());
            compositionDetail.setCommodityWeight(commodity.getWeight());
            shippingDtailCompositions.add(compositionDetail);
            compositions.add(compositionDetail);

            // 将发货明细与套餐发货明细关联
            if (!shippingDetailContainerMap.containsKey(shippingDetail)) {
              shippingDetailContainerMap.put(shippingDetail, compositions);
            }
          }
          
          // 如果是套餐商品,登录发货_套餐明细
          if (SetCommodityFlg.OBJECTIN.longValue().equals(commodity.getSetCommodityFlg())) {
            List<ShippingDetailComposition> compositions = new ArrayList<ShippingDetailComposition>();
            for (CompositionItem item : commodity.getCompositionList()) {
              ShippingDetailComposition compositionDetail = new ShippingDetailComposition();
              compositionDetail.setShopCode(commodity.getShopCode());
              compositionDetail.setParentCommodityCode(commodity.getCommodityCode());
              compositionDetail.setParentSkuCode(newCommodity.getSkuCode());
              compositionDetail.setChildCommodityCode(item.getCommodityCode());
              compositionDetail.setChildSkuCode(item.getSkuCode());
              compositionDetail.setCommodityName(item.getCommodityName());
              compositionDetail.setStandardDetail1Name(item.getStandardDetail1Name());
              compositionDetail.setStandardDetail2Name(item.getStandardDetail2Name());
              compositionDetail.setUnitPrice(item.getUnitPrice());
              compositionDetail.setPurchasingAmount(commodity.getQuantity()*(new Long("1")));
              BigDecimal discountPrice = BigDecimalUtil.subtract(item.getUnitPrice(), item.getRetailPrice());
              compositionDetail.setDiscountAmount(discountPrice);
              compositionDetail.setRetailPrice(item.getRetailPrice());

              // TaxUtil u = DIContainer.get("TaxUtil");
              // compositionDetail.setCommodityTaxRate(u.getTaxRate());
              compositionDetail.setCommodityTaxRate(item.getCommodityTaxRate());
              compositionDetail.setRetailTax(new BigDecimal(item.getRetailTax()));
              compositionDetail.setCommodityTax(item.getCommodityTax());
              compositionDetail.setCommodityTaxType(item.getCommodityTaxType());
              compositionDetail.setCommodityWeight(item.getWeight());
              shippingDtailCompositions.add(compositionDetail);
              compositions.add(compositionDetail);
            }

            // 将发货明细与套餐发货明细关联
            if (!shippingDetailContainerMap.containsKey(shippingDetail)) {
              shippingDetailContainerMap.put(shippingDetail, compositions);
            }
          }
        }

        // 2012-11-27 促销对应 ob add end
        

      }
      // 2012-11-27 促销对应 ob add start
      shippingContainer.setShippingDetailContainerMap(shippingDetailContainerMap);
      // 2012-11-27 促销对应 ob add end
      shippingContainer.setShippingDetails(shippingDetailList);
      shippingContainerList.add(shippingContainer);
    }
    for (Sku sku : useSkuList) {
      GiftItem giftItem = mainCommodityDetailMap.get(sku);
      int oldQuantity = giftItem.getQuantity();
      giftItem.setQuantity(skuMap.get(sku));
      ShippingDetail giftShippingDetail = createShippingDetail(giftItem);
      giftItem.setQuantity(oldQuantity);
      shippingDetailList.add(giftShippingDetail);
      if (orderCampaignExist(orderCampaignList, giftItem.getCampaignCode())) {
        continue;
      }
      // 创建促销订单信息(赠品促销)
      OrderCampaign orderCampaign = new OrderCampaign();
      orderCampaign.setCampaignCode(giftItem.getCampaignCode());
      orderCampaign.setCampaignName(giftItem.getCampaignName());
      orderCampaign.setCampaignType(CampaignMainType.GIFT.longValue());
      CampaignDoingsDao doingDao = DIContainer.getDao(CampaignDoingsDao.class);
      CampaignDoings doing = doingDao.load(giftItem.getCampaignCode());
      if (doing != null && StringUtil.hasValue(doing.getAttributrValue())) {
        orderCampaign.setAttributrValue(doing.getAttributrValue());
      } else {
        orderCampaign.setAttributrValue("");
      }
      orderCampaignList.add(orderCampaign);
    }
    // 2012-11-27 促销对应 ob add start
    orderContainer.setShippingDtailCompositions(shippingDtailCompositions);
    orderContainer.setOrderCampaigns(orderCampaignList);
    // 2012-11-27 促销对应 ob add end

    orderContainer.setShippings(shippingContainerList);

    orderContainer.setOrderDetails(createOrderDetailList(cashier));

    orderContainer.setCustomerCouponlist(cashier.getCustomerCouponId());

    // 20111222 shen add start
    orderContainer.setOrderInvoice(createOrderInvoice(cashier));

    orderContainer.setCustomerVatInvoice(createCustomerVatInvoice(cashier));
    // 20111222 shen add end

    // soukai add 2012/02/21 ob
    BigDecimal totalPayment = BigDecimal.ZERO;
    if (orderContainer.getOrderHeader().getPaymentCommission() != null) {
      // 指定时间段手续费
      totalPayment = totalPayment.add(orderContainer.getOrderHeader().getPaymentCommission());
    }
    totalPayment = totalPayment.add(orderContainer.getTotalAmount());
    if (orderContainer.getOrderHeader().getDiscountPrice().compareTo(totalPayment) == 0) {
      PaymentMethodDao dao = DIContainer.getDao(PaymentMethodDao.class);
      PaymentMethod noPayment = dao.load(PaymentMethodType.NO_PAYMENT.getValue());
      if (noPayment == null) {
        throw new RuntimeException();
      }
      orderContainer.getOrderHeader().setPaymentDate(DateUtil.getSysdate());
      orderContainer.getOrderHeader().setPaymentLimitDate(null);
      orderContainer.getOrderHeader().setPaymentMethodName(noPayment.getPaymentMethodName());
      orderContainer.getOrderHeader().setPaymentMethodNo(noPayment.getPaymentMethodNo());
      orderContainer.getOrderHeader().setPaymentMethodType(noPayment.getPaymentMethodType());
      orderContainer.getOrderHeader().setPaymentStatus(PaymentStatus.PAID.longValue());
      orderContainer.getOrderHeader().setAdvanceLaterFlg(noPayment.getAdvanceLaterFlg());
      for (ShippingContainer shipping : orderContainer.getShippings()) {
        shipping.getShippingHeader().setShippingStatus(ShippingStatus.READY.longValue());
      }
    }

    // soukai add 2012/02/21 ob
    return orderContainer;
  }

  /** 查看订单促销活动是否已经存在 */
  private static boolean orderCampaignExist(List<OrderCampaign> orderCampaignList, String campaignCode) {
    boolean exist = false;

    if (orderCampaignList == null || StringUtil.isNullOrEmpty(campaignCode)) {
      return true;
    }

    for (OrderCampaign campaign : orderCampaignList) {
      if (campaignCode.equals(campaign.getCampaignCode())) {
        exist = true;
        break;
      }
    }
    return exist;
  }

  /**
   * Cashierクラスから、OrderHeaderを生成します。
   * 
   * @param cashier
   *          決済情報
   * @return 受注ヘッダ
   */
  private static OrderHeader createOrderHeader(Cashier cashier) {
    OrderHeader orderHeader = new OrderHeader();

    // orderHeader.setOrderNo();
    orderHeader.setShopCode(cashier.getPaymentShopCode());
    // orderHeader.setOrderDatetime();

    // 請求情報
    orderHeader.setCustomerCode(cashier.getCustomer().getCustomerCode());
    orderHeader.setLastName(cashier.getCustomer().getLastName());
    orderHeader.setFirstName(cashier.getCustomer().getFirstName());
    orderHeader.setLastNameKana(cashier.getCustomer().getLastNameKana());
    orderHeader.setFirstNameKana(cashier.getCustomer().getFirstNameKana());
    orderHeader.setEmail(cashier.getCustomer().getEmail());
    orderHeader.setPostalCode(cashier.getSelfAddress().getPostalCode());
    orderHeader.setPrefectureCode(cashier.getSelfAddress().getPrefectureCode());
    orderHeader.setAddress1(cashier.getSelfAddress().getAddress1());
    orderHeader.setAddress2(cashier.getSelfAddress().getAddress2());
    orderHeader.setAddress3(cashier.getSelfAddress().getAddress3());
    orderHeader.setAddress4(cashier.getSelfAddress().getAddress4());
    orderHeader.setPhoneNumber(cashier.getSelfAddress().getPhoneNumber());
    // Add by V10-CH start
    orderHeader.setMobileNumber(cashier.getSelfAddress().getMobileNumber());
    // Add by V10-CH end
    orderHeader.setCustomerGroupCode(cashier.getCustomer().getCustomerGroupCode());
    orderHeader.setPrefectureCode(cashier.getSelfAddress().getPrefectureCode());
    // modify by V10-CH 170 start
    orderHeader.setCityCode(cashier.getSelfAddress().getCityCode());
    orderHeader.setCouponPrice(NumUtil.parse(cashier.getUseCoupon()));
    // modify by V10-CH 170 start

    if (CustomerConstant.GUEST_CUSTOMER_CODE.equals(cashier.getCustomer().getCustomerCode())) {
      orderHeader.setGuestFlg(GuestFlg.GUEST.longValue());
    } else {
      orderHeader.setGuestFlg(GuestFlg.MEMBER.longValue());
    }

    // 支払情報
    ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());
    String paymentMethodCode = cashier.getPayment().getSelectPayment().getPaymentMethodCode();
    PaymentMethodSuite payment = shopService.getPaymentMethod(cashier.getPaymentShopCode(), NumUtil.toLong(paymentMethodCode));
    orderHeader.setPaymentMethodNo(NumUtil.toLong(paymentMethodCode));
    orderHeader.setPaymentMethodType(payment.getPaymentMethod().getPaymentMethodType());
    // 20120627 cs_yuli edit start
    String paymentType = payment.getPaymentMethod().getPaymentMethodType();
    List<PaymentMethodType> paymentTypeList = Arrays.asList(PaymentMethodType.values());
    for (PaymentMethodType paymentMethodType : paymentTypeList) {
      if (paymentType.equals(paymentMethodType.getValue())) {
        orderHeader.setPaymentMethodName(paymentMethodType.getName());
      }
    }
    // 20120627 cs_yuli edit end
    BigDecimal commission = cashier.getPayment().getSelectPayment().getPaymentCommission();
    Long taxRate = payment.getPaymentMethod().getPaymentCommissionTaxRate();
    Long taxType = payment.getPaymentMethod().getPaymentCommissionTaxType();
    orderHeader.setPaymentCommission(commission);
    orderHeader.setPaymentCommissionTaxRate(taxRate);
    orderHeader.setPaymentCommissionTaxType(taxType);
    orderHeader.setPaymentCommissionTax(Price.getPriceTaxCharge(commission, NumUtil.toPrimitiveLong(taxRate), NumUtil
        .toString(taxType)));
    orderHeader.setAdvanceLaterFlg(payment.getPaymentMethod().getAdvanceLaterFlg());

    PaymentMethod paymentMethod = payment.getPaymentMethod();
    Long paymentLimitDays = paymentMethod.getPaymentLimitDays();
    if (paymentLimitDays != null) {
      Date limitDate = DateUtil.addDate(DateUtil.getSysdate(), paymentLimitDays.intValue());
      orderHeader.setPaymentLimitDate(DateUtil.truncateDate(limitDate));
    }

    // 支払方法区分が、支払不要、ポイント全額の場合、支払済み

    if (paymentMethod.getPaymentMethodType().equals(PaymentMethodType.NO_PAYMENT.getValue())
        || paymentMethod.getPaymentMethodType().equals(PaymentMethodType.POINT_IN_FULL.getValue())) {
      orderHeader.setPaymentStatus(PaymentStatus.PAID.longValue());
    } else {
      orderHeader.setPaymentStatus(PaymentStatus.NOT_PAID.longValue());
    }

    orderHeader.setPaymentStatus(PaymentStatus.NOT_PAID.longValue());
    orderHeader.setDataTransportStatus(DataTransportStatus.NOT_TRANSPORTED.longValue());
    if (cashier.isReserve()) {
      orderHeader.setOrderStatus(OrderStatus.RESERVED.longValue());
    } else {
      orderHeader.setOrderStatus(OrderStatus.ORDERED.longValue());
    }
    // 20120628 shen update start
    // orderHeader.setCaution(cashier.getCaution());
    // orderHeader.setMessage(cashier.getMessage());
    orderHeader.setCaution(cashier.getMessage());
    // 20120628 shen update end
    orderHeader.setUsedPoint(NumUtil.parse(cashier.getUsePoint()));

    // 20111226 shen add start
    if (BigDecimalUtil.isAbove(cashier.getDiscountPrice(), BigDecimal.ZERO)) {
      orderHeader.setDiscountType(NumUtil.toLong(cashier.getDiscount().getCouponType(), null));
      orderHeader.setDiscountMode(NumUtil.toLong(cashier.getDiscount().getDiscountMode(), null));
      orderHeader.setDiscountRate(cashier.getDiscount().getDiscountRate());
      // soukai update 2012/02/21 ob start
      if (BigDecimalUtil.isAbove(cashier.getDiscount().getDiscountPrice(), cashier.getTotalCommodityPrice())) {
        orderHeader.setDiscountPrice(cashier.getTotalCommodityPrice());
      } else {
        orderHeader.setDiscountPrice(cashier.getDiscount().getDiscountPrice());
      }
      // soukai update 2012/02/21 ob end
      orderHeader.setDiscountCode(cashier.getDiscount().getDiscountCode());
      orderHeader.setDiscountName(cashier.getDiscount().getDiscountName());
      orderHeader.setDiscountDetailCode(cashier.getDiscount().getDiscountDetailCode());
    } else {
      orderHeader.setDiscountPrice(BigDecimal.ZERO);
    }
    // 20111226 shen add end
    // 20120106 shen add start
    orderHeader.setAreaCode(cashier.getSelfAddress().getAreaCode());
    // 20120106 shen add end

    return orderHeader;
  }

  /**
   * 受注ヘッダ、キャッシャー情報(支払・出荷)から出荷ヘッダを取得します。
   * 
   * @param orderHeader
   *          受注ヘッダ
   * @param payment
   *          キャッシャー支払情報
   * @param shipping
   *          キャッシャー出荷情報
   * @return 出荷ヘッダ
   */
  private static ShippingHeader createShippingHeader(OrderHeader orderHeader, CashierPayment payment, CashierShipping shipping,
      Cashier cashier) {
    ShippingHeader shippingHeader = new ShippingHeader();

    // shippingHeader.ShippingNo();
    // shippingHeader.OrderNo();
    shippingHeader.setShopCode(shipping.getDeliveryType().getShopCode());
    shippingHeader.setCustomerCode(orderHeader.getCustomerCode());

    // CustomerCodeがnullの場合、ゲストとみなす。

    if (StringUtil.isNullOrEmpty(orderHeader.getCustomerCode())) {
      shippingHeader.setAddressNo(0L);
    } else {
      shippingHeader.setAddressNo(shipping.getAddress().getAddressNo());
    }

    CustomerAddress address = shipping.getAddress();
    shippingHeader.setAddressLastName(address.getAddressLastName());
    shippingHeader.setAddressFirstName(address.getAddressFirstName());
    shippingHeader.setAddressLastNameKana(address.getAddressLastNameKana());
    shippingHeader.setAddressFirstNameKana(address.getAddressFirstNameKana());
    shippingHeader.setPostalCode(address.getPostalCode());
    shippingHeader.setPrefectureCode(address.getPrefectureCode());
    shippingHeader.setAddress1(address.getAddress1());
    shippingHeader.setAddress2(address.getAddress2());
    shippingHeader.setAddress3(address.getAddress3());
    shippingHeader.setAddress4(address.getAddress4());
    shippingHeader.setPhoneNumber(address.getPhoneNumber());
    // Add by V10-CH start
    shippingHeader.setMobileNumber(address.getMobileNumber());
    // Add by V10-CH end
    shippingHeader.setPrefectureCode(address.getPrefectureCode());
    shippingHeader.setReturnItemLossMoney(BigDecimal.ZERO);
    shippingHeader.setDeliveryRemark(shipping.getDeliveryRemark());
    shippingHeader.setAcquiredPoint(BigDecimal.ZERO);
    // modify by V10-CH 170 start
    shippingHeader.setCityCode(address.getCityCode());
    // modify by V10-CH 170 end

    // 宅配便伝票番号は新規受注時には何も設定しない。

    // shippingHeader.setDeliverySlipNo();

    shippingHeader.setShippingCharge(shipping.getShippingCharge());
    shippingHeader.setShippingChargeTaxType(shipping.getDeliveryType().getShippingChargeTaxType());
    // 消費税取得

    TaxUtil u = DIContainer.get("TaxUtil");
    shippingHeader.setShippingChargeTaxRate(u.getTaxRate());
    shippingHeader.setShippingChargeTax(shipping.getShippingChargeTax());
    shippingHeader.setDeliveryTypeNo(shipping.getDeliveryType().getDeliveryTypeNo());
    shippingHeader.setDeliveryTypeName(shipping.getDeliveryType().getDeliveryTypeName());
    // 20120219 shen delete start
    /*
     * if (StringUtil.hasValue(shipping.getDeliveryAppointedStartTime())) {
     * shippingHeader.setDeliveryAppointedTimeStart(NumUtil.toLong(shipping.
     * getDeliveryAppointedStartTime())); } else {
     * shippingHeader.setDeliveryAppointedTimeStart(null); } if
     * (StringUtil.hasValue(shipping.getDeliveryAppointedTimeEnd())) {
     * shippingHeader.setDeliveryAppointedTimeEnd(NumUtil.toLong(shipping.
     * getDeliveryAppointedTimeEnd())); } else {
     * shippingHeader.setDeliveryAppointedTimeEnd(null); }
     */
    // 20120219 shen delete end
    // 20111229 shen update start
    // Date deliveryAppointedDate = shipping.getDeliveryAppointedDate();
    // shippingHeader.setDeliveryAppointedDate(deliveryAppointedDate);
    if (StringUtil.isNullOrEmpty(shipping.getDeliveryAppointedDate())
        || shipping.getDeliveryAppointedDate().equals(DeliveryDateType.UNUSABLE.getValue())) {
      shippingHeader.setDeliveryAppointedDate(null);
    } else {
      shippingHeader.setDeliveryAppointedDate(shipping.getDeliveryAppointedDate());
    }
    shippingHeader.setDeliveryAppointedTimeStart(NumUtil.toLong(shipping.getDeliveryAppointedStartTime(), null));
    shippingHeader.setDeliveryAppointedTimeEnd(NumUtil.toLong(shipping.getDeliveryAppointedTimeEnd(), null));

    // 配送公司设定
    UtilService utilService = ServiceLocator.getUtilService(ServiceLoginInfo.getInstance());
    // DeliveryCompany deliveryCompany =
    // utilService.getDeliveryCompany(shipping.getDeliveryType().getShopCode(),
    // address
    // .getPrefectureCode(), payment.getSelectPayment().isCashOnDelivery(),
    // shipping.getDeliveryAppointedDate(), shipping
    // .getDeliveryAppointedStartTime(), shipping.getDeliveryAppointedTimeEnd(),
    // cashier.getTotalWeight().toString());
    DeliveryCompanyDao dao = DIContainer.getDao(DeliveryCompanyDao.class);
    DeliveryCompany deliveryCompany = dao.load(cashier.getDelivery().getDeliveryCompanyCode());

    if (deliveryCompany == null || deliveryCompany.getDeliveryCompanyNo() == null) {
      deliveryCompany = utilService.getDefaultDeliveryCompany();
    }
    if (deliveryCompany != null) {
      shippingHeader.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
      shippingHeader.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());

      // 手续费设定
//      if (StringUtil.hasValueAnyOf(shipping.getDeliveryAppointedStartTime(), shipping.getDeliveryAppointedTimeEnd())) {
        ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());
        DeliveryRegion deliveryRegion = shopService.getDeliveryRegion(deliveryCompany.getShopCode(), deliveryCompany
            .getDeliveryCompanyNo(), address.getPrefectureCode());
        if (deliveryRegion != null) {
          orderHeader.setPaymentCommission(deliveryRegion.getDeliveryDatetimeCommission());
        }
        payment.getSelectPayment().setPaymentCommission(orderHeader.getPaymentCommission());
//      }
    }
    // 20111229 shen update end

    // 20111229 shen delete start
    /*
     * // 配送指定日を元に出荷指示日を設定する if (deliveryAppointedDate != null) { UtilService
     * utilService =
     * ServiceLocator.getUtilService(ServiceLoginInfo.getInstance());
     * DeliveryType deliveryType = new DeliveryType();
     * deliveryType.setShopCode(shippingHeader.getShopCode());
     * deliveryType.setDeliveryTypeNo(shippingHeader.getDeliveryTypeNo());
     * shippingHeader
     * .setShippingDirectDate(utilService.createShippingDirectDate(
     * deliveryAppointedDate, deliveryType, shippingHeader
     * .getPrefectureCode())); }
     */
    // 20111229 shen delete end
    // shippingHeader.setArrivalDate();
    // shippingHeader.setArrivalTimeStart();
    // shippingHeader.setArrivalTimeEnd();
    // 後払いの場合、出荷ステータスは出荷可能となる。
    if (orderHeader.getAdvanceLaterFlg().equals(AdvanceLaterType.LATER.longValue())) {
      shippingHeader.setShippingStatus(ShippingStatus.READY.longValue());
    } else {
      shippingHeader.setShippingStatus(ShippingStatus.NOT_READY.longValue());
    }

    // shippingHeader.setShippingDirectDate();
    // shippingHeader.setShippingDate();
    // shippingHeader.setOriginalShippingNo();
    // shippingHeader.setReturnitemDate();
    // shippingHeader.setReturnitemLossmoney();
    shippingHeader.setReturnItemType(ReturnItemType.ORDERED.longValue());
    // 20120106 shen add start
    shippingHeader.setAreaCode(address.getAreaCode());
    // 20120106 shen add end

    return shippingHeader;
  }

  private static ShippingDetail createShippingDetail(CartCommodityInfo cashierDetail,CatalogService catalogService,String cusCode) {
    ShippingDetail shippingDetail = new ShippingDetail();

    shippingDetail.setShopCode(cashierDetail.getShopCode());
    shippingDetail.setSkuCode(cashierDetail.getSkuCode());
    shippingDetail.setUnitPrice(cashierDetail.getUnitPrice());
    
    if(cashierDetail.getIsDiscountCommodity().equals("true") && !cusCode.equals("guest")){
      DiscountHeader dhBean = catalogService.getDiscountHeaderByCommodityCode(cashierDetail.getSkuCode());
      shippingDetail.setCampaignCode(dhBean.getDiscountCode());
      shippingDetail.setCampaignName(dhBean.getDiscountName());
      shippingDetail.setDiscountType(2L);
      shippingDetail.setDiscountValue(BigDecimal.ZERO);
    }
    
    // DBから商品情報を取得する
    if (cashierDetail.isDiscount()) {
      shippingDetail.setDiscountPrice(cashierDetail.getRetailPrice());
    }
    shippingDetail.setRetailPrice(cashierDetail.getRetailPrice());

    shippingDetail.setPurchasingAmount(Integer.valueOf(cashierDetail.getQuantity()).longValue());
    shippingDetail.setGiftCode(cashierDetail.getGiftCode());
    shippingDetail.setGiftName(cashierDetail.getGiftName());
    shippingDetail.setGiftPrice(cashierDetail.getGiftPrice());
    shippingDetail.setGiftTax(cashierDetail.getGiftTaxCharge());

    // 消費税取得

    TaxUtil u = DIContainer.get("TaxUtil");
    if (StringUtil.hasValue(shippingDetail.getGiftCode())) {
      shippingDetail.setGiftTaxRate(u.getTaxRate());
      shippingDetail.setGiftTax(cashierDetail.getGiftTaxCharge());
      shippingDetail.setGiftTaxType(cashierDetail.getGiftTaxType());
    }
    shippingDetail.setRetailTax(cashierDetail.getCommodityTaxCharge());
    // 2012-11-27 促销对应 ob add start
    shippingDetail.setCommodityType(cashierDetail.getCommodityType());
    shippingDetail.setSetCommodityFlg(cashierDetail.getSetCommodityFlg());

    // 如果是普通商品并且使用折扣券了,设置折扣促销活动的相关信息
    if (CommodityType.GENERALGOODS.longValue().equals(cashierDetail.getCommodityType())
        && StringUtil.hasValue(cashierDetail.getCampaignCouponCode())) {
      shippingDetail.setCampaignCode(cashierDetail.getCampaignCouponCode());
      shippingDetail.setCampaignName(cashierDetail.getCampaignCouponName());
      shippingDetail.setDiscountType(cashierDetail.getCampaignCouponType());
      shippingDetail.setDiscountValue(cashierDetail.getCampaignCouponPrice());
      shippingDetail.setRetailPrice(BigDecimalUtil.subtract(shippingDetail.getRetailPrice(), shippingDetail.getDiscountValue()));
    }

    // 如果是多重促销商品的赠品,设置促销活动编号跟名称
    if (CommodityType.GIFT.longValue().equals(cashierDetail.getCommodityType())) {
      shippingDetail.setCampaignCode(cashierDetail.getMultipleCampaignCode());
      shippingDetail.setCampaignName(cashierDetail.getMultipleCampaignName());
    }
    BigDecimal discountAmount = shippingDetail.getUnitPrice().subtract(shippingDetail.getRetailPrice());
    shippingDetail.setDiscountAmount(discountAmount);
    if (!BigDecimalUtil.equals(shippingDetail.getUnitPrice(), shippingDetail.getRetailPrice())) {
      shippingDetail.setDiscountPrice(shippingDetail.getRetailPrice());
    }
    // 2012-11-27 促销对应 ob add end

    return shippingDetail;
  }

  // 2012-11-27 促销对应 ob add start
  // 根据赠品促销活动的赠品信息,创建出荷明细
  private static ShippingDetail createShippingDetail(GiftItem giftItem) {
    ShippingDetail shippingDetail = new ShippingDetail();

    shippingDetail.setShopCode(giftItem.getShopCode());
    shippingDetail.setSkuCode(giftItem.getGiftSkuCode());
    shippingDetail.setUnitPrice(giftItem.getUnitPrice());
    shippingDetail.setRetailPrice(giftItem.getRetailPrice());
    if (giftItem.isDiscount()) {
      shippingDetail.setDiscountPrice(giftItem.getRetailPrice());
    }
    BigDecimal discountAmount = giftItem.getUnitPrice().subtract(giftItem.getRetailPrice());
    shippingDetail.setDiscountAmount(discountAmount);
    shippingDetail.setPurchasingAmount(Integer.valueOf(giftItem.getQuantity()).longValue());
    shippingDetail.setGiftCode("");
    shippingDetail.setGiftName("");
    shippingDetail.setGiftTax(BigDecimal.ZERO);

    // 消費税取得
    shippingDetail.setRetailTax(giftItem.getUnitTaxCharge());
    shippingDetail.setCommodityType(CommodityType.GIFT.longValue());
    shippingDetail.setSetCommodityFlg(SetCommodityFlg.OBJECTOUT.longValue());

    shippingDetail.setCampaignCode(giftItem.getCampaignCode());
    shippingDetail.setCampaignName(giftItem.getCampaignName());

    return shippingDetail;
  }

  // 2012-11-27 促销对应 ob add end

  /**
   * 受注明細情報生成<BR>
   * 
   * @param shippingDetailList
   * @return
   */
  private static List<OrderDetail> createOrderDetailList(Cashier cashier) {
    List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
    // soukai add 2012/01/10 ob start
    CatalogService catalogService = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
    CommunicationService commService = ServiceLocator.getCommunicationService(ServiceLoginInfo.getInstance());
    // soukai add 2012/01/10 ob end
    // 出荷明細情報を受注明細で作成しやすいようにList及びMapにつめる
    List<Sku> useSkuList = new ArrayList<Sku>();
    Map<Sku, Long> skuAmountMap = new HashMap<Sku, Long>();
    Map<Sku, CartCommodityInfo> mainShippingDetailMap = new HashMap<Sku, CartCommodityInfo>();
    for (CashierShipping shipping : cashier.getShippingList()) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        CartCommodityInfo newCommodity = (CartCommodityInfo) commodity.clone();
        if (SetCommodityFlg.OBJECTIN.longValue().equals(commodity.getSetCommodityFlg())) {
          String[] tmp = commodity.getSkuCode().split(":");
          if (tmp.length > 0) {
            newCommodity.setSkuCode(tmp[0]);
          }
        }
        Sku sku = new Sku(commodity.getShopCode(), newCommodity.getSkuCode());
        sku = getStillSetSku(useSkuList, sku);
        if (sku != null) {
          // 取得してきたSKUが既に登録されている場合
          Long amount = skuAmountMap.get(sku);
          amount += Long.valueOf(commodity.getQuantity());
          skuAmountMap.remove(sku);
          skuAmountMap.put(sku, amount);
        } else {
          sku = new Sku(commodity.getShopCode(), newCommodity.getSkuCode());
          useSkuList.add(sku);
          skuAmountMap.put(sku, Long.valueOf(commodity.getQuantity()));
          mainShippingDetailMap.put(sku, commodity);
        }
        // 2012-11-27 促销对应 ob add start
        if (commodity.getGiftList() != null && commodity.getGiftList().size() > 0) {
          for (GiftItem gift : commodity.getGiftList()) {
            Sku giftSku = new Sku(gift.getShopCode(), gift.getGiftSkuCode());
            giftSku = getStillSetSku(useSkuList, giftSku);
            if (giftSku != null) {
              // 取得してきたSKUが既に登録されている場合
              Long amount = skuAmountMap.get(giftSku);
              amount += Long.valueOf(gift.getQuantity());
              skuAmountMap.remove(giftSku);
              skuAmountMap.put(giftSku, amount);
            } else {
              giftSku = new Sku(gift.getShopCode(), gift.getGiftSkuCode());

              useSkuList.add(giftSku);
              skuAmountMap.put(giftSku, Long.valueOf(gift.getQuantity()));
              CartCommodityInfo giftCommodity = new CartCommodityInfo();
              giftCommodity.setShopCode(gift.getShopCode());
              giftCommodity.setCommodityCode(gift.getGiftCode());
              giftCommodity.setCommodityName(gift.getGiftName());
              giftCommodity.setSkuCode(gift.getGiftSkuCode());
              giftCommodity.setStandardDetail1Name(gift.getStandardDetail1Name());
              giftCommodity.setStandardDetail2Name(gift.getStandardDetail2Name());
              giftCommodity.setUnitPrice(gift.getUnitPrice());
              giftCommodity.setRetailPrice(gift.getRetailPrice());
              CatalogService catalogSvc = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
              CommodityInfo skuInfo = catalogSvc.getSkuInfo(gift.getShopCode(), gift.getGiftSkuCode());

              if (skuInfo != null && skuInfo.getHeader() != null && skuInfo.getDetail() != null) {
                Campaign campaign = catalogSvc.getAppliedCampaignInfo(skuInfo.getHeader().getShopCode(), skuInfo.getHeader()
                    .getCommodityCode());
                // 消費税の取得
                TaxUtil u = DIContainer.get("TaxUtil");
                Long taxRate = u.getTaxRate();
                Price price = new Price(skuInfo.getHeader(), skuInfo.getDetail(), campaign, taxRate);
                giftCommodity.setCommodityTaxCharge(price.retailTaxCharge());
              }
              giftCommodity.setUnitTaxCharge(gift.getUnitTaxCharge());
              giftCommodity.setCommodityTaxType(gift.getTaxType());
              giftCommodity.setUseCommodityPoint(false);
              giftCommodity.setWeight(gift.getWeight());
              giftCommodity.setCommodityType(CommodityType.GIFT.longValue());
              giftCommodity.setSetCommodityFlg(SetCommodityFlg.OBJECTOUT.longValue());

              mainShippingDetailMap.put(giftSku, giftCommodity);
            }
          }
        }
        // 2012-11-27 促销对应 ob add end
      }
    }

    for (Sku sku : useSkuList) {
      OrderDetail orderDetail = new OrderDetail();
      CartCommodityInfo commodityInfo = mainShippingDetailMap.get(sku);

      // orderDetail.setOrderNo(orderNo);
      orderDetail.setShopCode(sku.getShopCode());
      orderDetail.setSkuCode(sku.getSkuCode());

      orderDetail.setCommodityCode(commodityInfo.getCommodityCode());
      orderDetail.setCommodityName(commodityInfo.getCommodityName());
      orderDetail.setStandardDetail1Name(commodityInfo.getStandardDetail1Name());
      orderDetail.setStandardDetail2Name(commodityInfo.getStandardDetail2Name());
      orderDetail.setPurchasingAmount(skuAmountMap.get(sku));
      orderDetail.setUnitPrice(commodityInfo.getUnitPrice());
      // 2012-12-4 促销对应 ob update start
      orderDetail.setRetailPrice(commodityInfo.getRetailPrice());
      // 如果是普通商品并且使用折扣券了,设置折扣促销活动的相关信息
      if (CommodityType.GENERALGOODS.longValue().equals(commodityInfo.getCommodityType())
          && StringUtil.hasValue(commodityInfo.getCampaignCouponCode())) {
        orderDetail.setRetailPrice(BigDecimalUtil.subtract(orderDetail.getRetailPrice(), commodityInfo.getCampaignCouponPrice()));
      }
      // 2012-12-4 促销对应 ob update end

      orderDetail.setRetailTax(commodityInfo.getCommodityTaxCharge());

      // 消費税取得

      TaxUtil u = DIContainer.get("TaxUtil");

      orderDetail.setCommodityTaxRate(u.getTaxRate());
      orderDetail.setCommodityTaxType(commodityInfo.getCommodityTaxType());
      orderDetail.setCommodityTax(commodityInfo.getUnitTaxCharge());

      // キャンペーン情報の取得

      orderDetail.setCampaignCode(commodityInfo.getCampaignCode());
      orderDetail.setCampaignName(commodityInfo.getCampaignName());
      orderDetail.setCampaignDiscountRate(commodityInfo.getCampaignDiscountRate());

      if (commodityInfo.isUseCommodityPoint()) {
        orderDetail.setAppliedPointRate(commodityInfo.getCommodityPointRate());
      } else {
        orderDetail.setAppliedPointRate(null);
      }
      // 20111229 shen add start
      // 2012-11-29 促销对应 ob update start
      // orderDetail.setCommodityWeight(commodityInfo.getWeight());
      if (SetCommodityFlg.OBJECTIN.longValue().equals(commodityInfo.getSetCommodityFlg())) {
        orderDetail.setCommodityWeight(commodityInfo.getCompositionWeight());
      } else {
        orderDetail.setCommodityWeight(commodityInfo.getWeight());
      }
      // 2012-11-29 促销对应 ob update end

      // 20111229 shen add end

      // soukai add 2012/01/10 ob start
      CommodityInfo info = catalogService.getCommodityInfo(orderDetail.getShopCode(), orderDetail.getCommodityCode());
      orderDetail.setBrandCode(info.getHeader().getBrandCode());
      if (StringUtil.hasValue(orderDetail.getBrandCode())) {
        Brand brand = catalogService.getBrand(info.getHeader().getShopCode(), info.getHeader().getBrandCode());
        if (brand != null) {
          orderDetail.setBrandName(brand.getBrandName());
        }
      }
      if (StringUtil.hasValue(info.getHeader().getSaleFlag())) {
        Plan plan = commService.getPlan(PlanType.PROMOTION.getValue(), info.getHeader().getSaleFlag());
        if (plan != null) {
          orderDetail.setSalePlanCode(plan.getPlanCode());
          orderDetail.setSalePlanName(plan.getPlanName());
        }
      }
      if (StringUtil.hasValue(info.getHeader().getSpecFlag())) {
        Plan plan = commService.getPlan(PlanType.FEATURE.getValue(), info.getHeader().getSpecFlag());
        if (plan != null) {
          orderDetail.setFeaturedPlanCode(plan.getPlanCode());
          orderDetail.setFeaturedPlanName(plan.getPlanName());
        }
      }
      // soukai add 2012/01/10 ob end

      // 2012-11-27 促销对应 ob add start
      orderDetail.setCommodityType(commodityInfo.getCommodityType());
      orderDetail.setSetCommodityFlg(commodityInfo.getSetCommodityFlg());
      if (StringUtil.hasValue(commodityInfo.getOriginalCommodityCode())) {
        orderDetail.setSetCommodityFlg(SetCommodityFlg.OBJECTIN.longValue());
      }
      // 2012-11-27 促销对应 ob add end

      orderDetailList.add(orderDetail);
    }

    return orderDetailList;
  }

  private static Sku getStillSetSku(List<Sku> skuList, Sku sku) {
    for (Sku tmp : skuList) {
      if (tmp.getShopCode().equals(sku.getShopCode()) && tmp.getSkuCode().equals(sku.getSkuCode())) {
        return tmp;
      }
    }
    return null;
  }

  /**
   * 規格と予約数量受け取り注文毎予約上限数を超えているかチェックします。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>注文毎予約上限数のチェックを行います。
   * <ol>
   * <li>引数で受取ったショップコードとSKUコードの商品が予約期間中かどうかチェックします。</li>
   * <li>予約期間外の場合常にfalseを返します。</li>
   * <li>引数で受取ったショップコードとSKUコードを元に注文毎予約上限数を取得します。</li>
   * <li>引数で受取った商品予約数量と3で取得した注文毎予約上限数を比較します。</li>
   * <li>引数で受取った商品数量＞3で取得した注文毎予約上限数の場合trueを返します。</li>
   * <li>引数で受取った商品数量＜＝3で取得した注文毎予約上限数の場合falseを返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeはnullでないものとします。</dd>
   * <dd>skuCodeはnullでないものとします。</dd>
   * <dd>quantityはnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          商品のショップコード
   * @param skuCode
   *          商品の規格コード
   * @param quantity
   *          商品予約数量
   * @return true-注文毎予約上限数オーバー false-予約可能
   * @see #isOneshotReservationLimitOver(String shopCode, String skuCode, Long
   *      quantity, boolean reserve)
   */
  public static boolean isOneshotReservationLimitOver(String shopCode, String skuCode, Long quantity) {
    boolean reserve = false;
    CatalogService service = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
    CommodityInfo commodity = service.getSkuInfo(shopCode, skuCode);
    CommodityHeader header = commodity.getHeader();

    if (header.getReservationEndDatetime() != null) {
      reserve = DateUtil.isPeriodDate(header.getReservationStartDatetime(), header.getReservationEndDatetime());
    }

    return isOneshotReservationLimitOver(shopCode, skuCode, quantity, reserve);
  }

  /**
   * 注文毎予約上限数を超えていないかチェックします。<br>
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>注文毎予約上限数のチェックを行います。
   * <ol>
   * <li>引数で受取った予約期間中判定フラグがfalseの場合常にfalseを返します。</li>
   * <li>引数で受取ったショップコードとSKUコードを元に注文毎予約上限数を取得します。</li>
   * <li>引数で受取った商品予約数量と3で取得した注文毎予約上限数を比較します。</li>
   * <li>引数で受取った商品数量＞3で取得した注文毎予約上限数の場合trueを返します。</li>
   * <li>引数で受取った商品数量＜＝3で取得した注文毎予約上限数の場合falseを返します。</li></dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeはnullでないものとします。</dd>
   * <dd>skuCodeはnullでないものとします。</dd>
   * <dd>quantityはnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param quantity
   *          予約数
   * @param reserve
   *          該当のSKU商品が予約期間中かどうか 予約期間中 - true 予約期間外 - false
   * @return 予約上限数の判定結果
   */
  public static boolean isOneshotReservationLimitOver(String shopCode, String skuCode, Long quantity, boolean reserve) {
    if (reserve) {
      CatalogService service = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
      Stock stock = service.getStock(shopCode, skuCode);
      Long oneshotReservationLimit = stock.getOneshotReservationLimit();
      return (oneshotReservationLimit != null && quantity > oneshotReservationLimit);
    } else {
      return false;
    }
  }

  /**
   * カート投入可能な数量を取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>カート投入可能な数量を取得します。
   * <ol>
   * <li>該当のSKU商品が予約期間以外の場合は、有効在庫数を取得します。(
   * {@link CatalogService#getAvailableStock(String, String)}と等価)</li>
   * <li>該当のSKU商品が予約期間であり、かつ注文毎予約上限数が未設定の場合は有効在庫数を取得します。(
   * {@link CatalogService#getAvailableStock(String, String)}と等価)</li>
   * <li>該当のSKU商品が予約期間であり、かつ注文毎予約上限数が設定されている場合は、次のルールに従います。
   * <ul>
   * <li>注文毎予約上限数 > 有効在庫: 有効在庫数を取得
   * <li>注文毎予約上限数 <= 有効在庫: 注文毎予約上限数を取得
   * </ul>
   * </li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeはnullでないものとします。</dd>
   * <dd>skuCodeはnullでないものとします。</dd>
   * <dd>quantityはnullでないものとします。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param reserve
   *          該当のSKU商品が予約期間中かどうか 予約期間中 - true 予約期間外 - false
   * @return カート投入可能な数量
   */
  public static Long getAvailableQuantity(String shopCode, String skuCode, boolean reserve) {
    CatalogService service = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
    Long quantity = service.getAvailableStock(shopCode, skuCode);
    if (reserve) {
      Stock stock = service.getStock(shopCode, skuCode);
      if (stock.getOneshotReservationLimit() != null && quantity > stock.getOneshotReservationLimit()) {
        quantity = stock.getOneshotReservationLimit();
      }
    }
    return quantity;
  }

  /**
   * 指定された商品が予約商品かどうかを返します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で指定された商品が予約商品かどうかを返します。
   * <ol>
   * <li>カート内に指定されたショップコードとSKUコードを持つ受注商品があるか判定します。ある場合はfalseを返します。</li>
   * <li>カート内に指定されたショップコードとSKUコードを持つ予約商品があるか判定します。ある場合はtrueを返します。</li>
   * <li>カート内に受注商品も予約商品もない場合は指定されたショップコードとSKUコードを持つ商品が予約商品かどうかを返します。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>カート、ショップコード、SKUコードがnullでないこと。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @param cart
   *          対象のカート
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @return ショップコードとSKUコードで指定される商品が予約商品かどうか
   */
  public static boolean isReserve(Cart cart, String shopCode, String skuCode) {
    boolean hasOrderItem = cart.get(shopCode, skuCode) != null;
    boolean hasReserveItem = cart.getReserve(shopCode, skuCode) != null;

    if (!hasOrderItem && !hasReserveItem) {
      CatalogService service = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
      return service.isReserve(shopCode, skuCode);
    } else {
      return !hasOrderItem && hasReserveItem;
    }
  }

  // 20111222 shen add start
  private static OrderInvoice createOrderInvoice(Cashier cashier) {
    OrderInvoice orderInvoice = new OrderInvoice();
    CashierInvoice cashierInvoice = cashier.getInvoice();
    if (cashierInvoice.getInvoiceFlg().equals(InvoiceFlg.NEED.getValue())) {
      orderInvoice.setCustomerCode(cashier.getCustomer().getCustomerCode());
      orderInvoice.setInvoiceType(NumUtil.toLong(cashierInvoice.getInvoiceInfo().getInvoiceType(), null));
      orderInvoice.setCommodityName(cashierInvoice.getInvoiceInfo().getCommodityName());
      if (InvoiceType.USUAL.getValue().equals(cashierInvoice.getInvoiceInfo().getInvoiceType())) {
        orderInvoice.setCustomerName(cashierInvoice.getInvoiceInfo().getCustomerName());
      } else {
        orderInvoice.setCompanyName(cashierInvoice.getInvoiceInfo().getCompanyName());
        orderInvoice.setTaxpayerCode(cashierInvoice.getInvoiceInfo().getTaxpayerCode());
        orderInvoice.setAddress(cashierInvoice.getInvoiceInfo().getAddress());
        orderInvoice.setTel(cashierInvoice.getInvoiceInfo().getTel());
        orderInvoice.setBankName(cashierInvoice.getInvoiceInfo().getBankName());
        orderInvoice.setBankNo(cashierInvoice.getInvoiceInfo().getBankNo());
      }
    } else {
      orderInvoice = null;
    }

    return orderInvoice;
  }

  private static CustomerVatInvoice createCustomerVatInvoice(Cashier cashier) {
    CustomerVatInvoice customerVatInvoice = new CustomerVatInvoice();
    CashierInvoice cashierInvoice = cashier.getInvoice();
    if (cashierInvoice.getInvoiceFlg().equals(InvoiceFlg.NEED.getValue())
        && cashierInvoice.getInvoiceInfo().getInvoiceType().equals(InvoiceType.VAT.getValue())
        && StringUtil.hasValue(cashierInvoice.getInvoiceInfo().getInvoiceSaveFlg())
        && cashierInvoice.getInvoiceInfo().getInvoiceSaveFlg().equals(InvoiceSaveFlg.SAVE.getValue())) {
      customerVatInvoice.setCustomerCode(cashier.getCustomer().getCustomerCode());
      customerVatInvoice.setCompanyName(cashierInvoice.getInvoiceInfo().getCompanyName());
      customerVatInvoice.setTaxpayerCode(cashierInvoice.getInvoiceInfo().getTaxpayerCode());
      customerVatInvoice.setAddress(cashierInvoice.getInvoiceInfo().getAddress());
      customerVatInvoice.setTel(cashierInvoice.getInvoiceInfo().getTel());
      customerVatInvoice.setBankName(cashierInvoice.getInvoiceInfo().getBankName());
      customerVatInvoice.setBankNo(cashierInvoice.getInvoiceInfo().getBankNo());
    } else {
      customerVatInvoice = null;
    }

    return customerVatInvoice;
  }
  // 20111222 shen add end
  
  private static CartCommodityInfo createInfo(CartCommodityInfo info){
    CartCommodityInfo commodityInfo = new CartCommodityInfo();
    commodityInfo.setShopCode(info.getShopCode());
    commodityInfo.setCommodityCode(info.getCommodityCode());
    commodityInfo.setCommodityName(info.getCommodityName());
    commodityInfo.setSkuCode(info.getSkuCode());
    commodityInfo.setCommodityTaxType(info.getCommodityTaxType());
    commodityInfo.setCommodityTaxCharge(info.getCommodityTaxCharge());
    commodityInfo.setUnitTaxCharge(info.getUnitTaxCharge());
    if (StringUtil.hasValue(info.getOriginalCommodityCode()) && info.getCombinationAmount() != null) {
      commodityInfo.setUnitPrice(BigDecimalUtil.divide(info.getUnitPrice(), info.getCombinationAmount(),2,RoundingMode.DOWN));
      commodityInfo.setRetailPrice(BigDecimalUtil.divide(info.getRetailPrice(), info.getCombinationAmount(),2,RoundingMode.DOWN));
    } else {
      commodityInfo.setUnitPrice(info.getUnitPrice());
      commodityInfo.setRetailPrice(info.getRetailPrice());
    }
    commodityInfo.setStandardDetail1Name(info.getStandardDetail1Name());
    commodityInfo.setStandardDetail2Name(info.getStandardDetail2Name());
    commodityInfo.setGiftCode(info.getGiftCode());
    commodityInfo.setGiftName(info.getGiftName());
    commodityInfo.setGiftPrice(info.getGiftPrice());
    commodityInfo.setGiftTaxType(info.getGiftTaxType());
    commodityInfo.setGiftTaxCharge(info.getGiftTaxCharge());
    commodityInfo.setQuantity(info.getQuantity());
    commodityInfo.setCampaignCode(info.getCampaignCode());
    commodityInfo.setCampaignName(info.getCampaignName());
    commodityInfo.setCampaignNameEn(info.getCampaignNameEn());
    commodityInfo.setCampaignNameJp(info.getCampaignNameJp());
    commodityInfo.setCampaignDiscountRate(info.getCampaignDiscountRate());
    commodityInfo.setDiscount(info.isDiscount());
    commodityInfo.setUseCommodityPoint(info.isUseCommodityPoint());
    commodityInfo.setCommodityPointRate(info.getCommodityPointRate());
    commodityInfo.setCommodityPriceType(info.getCommodityPriceType());
    commodityInfo.setReturnPolicy(info.getReturnPolicy());
    commodityInfo.setWeight(info.getWeight());
    commodityInfo.setCompositionAttribute(info.getCompositionAttribute());
    commodityInfo.setStockManagementType(info.getStockManagementType());
    commodityInfo.setCompositionWeight(info.getCompositionWeight());
    commodityInfo.setSkuCodeOfSet(info.getSkuCodeOfSet());
    commodityInfo.setCommodityType(info.getCommodityType());
    commodityInfo.setSetCommodityFlg(info.getSetCommodityFlg());
    commodityInfo.setGiftList(info.getGiftList());
    commodityInfo.setCompositionList(info.getCompositionList());
    commodityInfo.setCampaignCouponType(info.getCampaignCouponType());
    commodityInfo.setCampaignCouponPrice(info.getCampaignCouponPrice());
    commodityInfo.setCampaignCouponCode(info.getCampaignCouponCode());
    commodityInfo.setCampaignCouponName(info.getCampaignCouponName());
    commodityInfo.setMultipleCampaignCode(info.getMultipleCampaignCode());
    commodityInfo.setMultipleCampaignName(info.getMultipleCampaignName());
    commodityInfo.setOriginalCommodityCode(info.getOriginalCommodityCode());
    commodityInfo.setCombinationAmount(info.getCombinationAmount());
    commodityInfo.setIsDiscountCommodity(info.getIsDiscountCommodity());
    return commodityInfo;
  } 
}
