package jp.co.sint.webshop.web.action.back.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.dao.DeliveryCompanyDao;
import jp.co.sint.webshop.data.dao.PaymentMethodDao;
import jp.co.sint.webshop.data.domain.AdvanceLaterType;
import jp.co.sint.webshop.data.domain.CampaignConditionFlg;
import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.DiscountType;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.InvoiceSaveFlg;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.OrderFlg;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignDoings;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.data.dto.CustomerVatInvoice;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.DeliveryRegion;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.data.dto.NewCouponHistoryUseInfo;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.OrderCampaign;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.OrderInvoice;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingDetailComposition;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.MyCouponInfo;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.campain.CampaignInfo;
import jp.co.sint.webshop.service.campain.CampainFilter;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.CartUtil;
import jp.co.sint.webshop.service.cart.CashierDiscount;
import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.service.shop.PaymentMethodSuite;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.PaymentMethodBean;
import jp.co.sint.webshop.web.bean.PaymentMethodBean.PaymentTypeBase;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.DeliveryCompanyBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.GiftAttribute;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.OrderHeaderBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.PointBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.SetCommpositionInfo;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingSku;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.TotalPrice;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean.GiftInfo;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean.ShippingDetailBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PaymentSupporter;

import org.apache.log4j.Logger;

/**
 * U1020230:受注修正の基底クラスです。
 * 
 * @author System Integrator Corp.
 */
public abstract class OrderModifyBaseAction extends WebBackAction<OrderModifyBean> {

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
      if (Permission.ORDER_MODIFY_SITE.isGranted(login)) {
        auth = true;
      }
    } else if (config.getOperatingMode() == OperatingMode.SHOP) {
      if (Permission.ORDER_MODIFY_SHOP.isGranted(login)) {
        auth = shopCode.equals(login.getShopCode());
      } else if (Permission.ORDER_MODIFY_SITE.isGranted(login)) {
        auth = true;
      }
    } else if (config.getOperatingMode() == OperatingMode.ONE) {
      auth = Permission.ORDER_MODIFY_SITE.isGranted(login);
    }
    return auth;
  }

  public boolean numberLimitValidation(OrderContainer order) {
    ValidationSummary summary = DIContainer.getNumberLimitPolicy().isCorrect(order);
    if (summary.hasError()) {
      for (String error : summary.getErrorMessages()) {
        addErrorMessage(error);
      }
      return false;
    } else {
      return true;
    }
  }

  public void createOutCardPrice() {
    OrderModifyBean bean = getBean();
    BigDecimal outerCardRate = DIContainer.getWebshopConfig().getOuterCardRate();
    if (!BigDecimalUtil.equals(bean.getAfterTotalPrice().getOutCardUsePrice(), BigDecimal.ZERO)) {
        BigDecimal disPrice = BigDecimal.ZERO;
        if (BigDecimalUtil.isBelow(bean.getAfterTotalPrice().getDiscountPrice(), bean.getAfterTotalPrice().getCommodityPrice())) {
          disPrice = bean.getAfterTotalPrice().getDiscountPrice();
        } else {
          disPrice = bean.getAfterTotalPrice().getCommodityPrice();
        }
        BigDecimal avaPrice = bean.getAfterTotalPrice().getAllPrice().subtract(disPrice).subtract(bean.getAfterTotalPrice().getOutCardUsePrice());
        BigDecimal outPrice = avaPrice.divide(outerCardRate,5);
        outPrice = BigDecimalUtil.subtract(outPrice, avaPrice);
        BigDecimal allPrice = bean.getAfterTotalPrice().getAllPrice().add(outPrice).subtract(bean.getAfterTotalPrice().getOutCardUsePrice());
        bean.getAfterTotalPrice().setOutCardUsePrice(outPrice);
        bean.getAfterTotalPrice().setAllPrice(allPrice);
    }
  }
  
  public boolean validateBean(Object objBean, String additionName) {
    Logger logger = Logger.getLogger(this.getClass());
    List<ValidationResult> resultList = BeanValidator.validate(objBean).getErrors();
    if (resultList.size() > 0) {
      for (ValidationResult result : resultList) {
        addErrorMessage(additionName + result.getFormedMessage());
        logger.debug(result.getFormedMessage());
      }
      return false;
    }
    return true;
  }

  /**
   * 商品追加情報を元に画面表示用出荷明細情報（商品情報）を生成する
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param giftCode
   *          ギフトコード
   * @param addQuantity
   *          追加数量
   * @param orgQuantity
   *          登録済み数量
   * @return 商品情報
   */
  public ShippingDetailBean createShippingDetailBean(String shopCode, String skuCode, String giftCode, Long addQuantity,
      Long orgQuantity, BigDecimal price) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodity = service.getSkuInfo(shopCode, skuCode);

    if (commodity == null) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.error("Commodity is null. ShopCode = " + shopCode + " SKUCode = " + skuCode);
      throw new RuntimeException(Messages.getString("web.action.back.order.OrderModifyBaseAction.0"));
    }

    OrderDetail newOrderDetail;

    String commodityCode = commodity.getHeader().getCommodityCode();
    List<CodeAttribute> skuList = new ArrayList<CodeAttribute>();
    HashMap<String, ShippingSku> shippingDetailSkuMap = new HashMap<String, ShippingSku>();
    boolean reserve = false;
    // 商品明細にて別同一商品の別SKUを表示する為、SKU一覧を取得し情報設定

    // 但し予約の場合はSKU変更不可
    if (getBean().isReserveFlg()) {
      skuList.add(getSkuNameValue(shopCode, skuCode, true));
      String commodityName = StringUtil.getHeadline(commodity.getHeader().getCommodityName(), 15);
      commodityName += createStandardDetailName(commodity.getDetail().getStandardDetail1Name(), commodity.getDetail()
          .getStandardDetail2Name());
      ShippingSku shippingSku = new ShippingSku(skuCode, commodityName, commodity.getDetail().getUnitPrice());
      shippingDetailSkuMap.put(skuCode, shippingSku);
      reserve = true;
    } else {
      skuList.add(getSkuNameValue(shopCode, skuCode, true));
      String commodityName = StringUtil.getHeadline(commodity.getHeader().getCommodityName(), 15);
      commodityName += createStandardDetailName(commodity.getDetail().getStandardDetail1Name(), commodity.getDetail()
          .getStandardDetail2Name());
      ShippingSku shippingSku = new ShippingSku(skuCode, commodityName, commodity.getDetail().getUnitPrice());
      shippingDetailSkuMap.put(skuCode, shippingSku);
      reserve = false;
    }

    // 配送商品情報にOrderContainerを設定（登録時に使用する）

    OrderDetail oldOrderDetail = getBean().getOldOrderContainer().getOrderDetail(new Sku(shopCode, skuCode));
    if (oldOrderDetail != null) {

      newOrderDetail = oldOrderDetail;
    } else {
      CommodityInfo skuInfo = service.getSkuInfo(shopCode, skuCode);
      newOrderDetail = createOrderDetailCommodityInfo(skuInfo);
    }
    OrderDetail detail = createOrderDetail(newOrderDetail, price);
    ShippingDetailBean detailBean = new ShippingDetailBean(getMaxDetailNo());
    detailBean.setShippingDetailNo(null);
    detailBean.setShippingDetailGiftList(createGiftList(shopCode, commodityCode, giftCode));
    detailBean.setShippingDetailSkuList(skuList);
    detailBean.setShippingDetailSkuMap(shippingDetailSkuMap);
    detailBean.setShippingDetailCommodityAmount(NumUtil.toString(addQuantity));
    detailBean.setOrgShippingDetailCommodityAmount(orgQuantity);
    detailBean.setGiftCode(giftCode);
    detailBean.setOrgGiftCode(StringUtil.coalesce(giftCode, ""));
    detailBean.setSkuCode(skuCode);
    detailBean.setOrderDetailCommodityInfo(detail);
    detailBean.setReserve(reserve);
    detailBean.setWeigth(detail.getCommodityWeight());
    detail.setPurchasingAmount(addQuantity);
    detailBean.setCommodityType(detail.getCommodityType());
    detailBean.setSetCommodityFlg(detail.getSetCommodityFlg());

    return detailBean;
  }

  private OrderDetail createOrderDetail(OrderDetail order, BigDecimal price) {
    OrderDetail detail = new OrderDetail();
    detail.setOrderNo(order.getOrderNo());
    detail.setShopCode(order.getShopCode());
    detail.setSkuCode(order.getSkuCode());
    detail.setCommodityCode(order.getCommodityCode());
    detail.setCommodityName(order.getCommodityName());
    detail.setStandardDetail1Name(order.getStandardDetail1Name());
    detail.setStandardDetail2Name(order.getStandardDetail2Name());
    detail.setPurchasingAmount(order.getPurchasingAmount());
    detail.setUnitPrice(price);
    detail.setRetailPrice(price);
    detail.setRetailTax(order.getRetailTax());
    detail.setCommodityTaxRate(order.getCommodityTaxRate());
    detail.setCommodityTax(order.getCommodityTax());
    detail.setCommodityTaxType(order.getCommodityTaxType());
    detail.setCampaignCode(order.getCampaignCode());
    detail.setCampaignName(order.getCampaignName());
    detail.setCampaignDiscountRate(order.getCampaignDiscountRate());
    detail.setAppliedPointRate(order.getAppliedPointRate());
    detail.setCommodityWeight(order.getCommodityWeight());
    detail.setSalePlanCode(order.getSalePlanCode());
    detail.setSalePlanName(order.getSalePlanName());
    detail.setFeaturedPlanCode(order.getFeaturedPlanCode());
    detail.setFeaturedPlanName(order.getFeaturedPlanName());
    detail.setBrandCode(order.getBrandCode());
    detail.setBrandName(order.getBrandName());
    detail.setOrmRowid(order.getOrmRowid());
    detail.setCreatedUser(order.getCreatedUser());
    detail.setCreatedDatetime(order.getCreatedDatetime());
    detail.setUpdatedUser(order.getUpdatedUser());
    detail.setUpdatedDatetime(order.getUpdatedDatetime());
    detail.setCommodityType(order.getCommodityType());
    detail.setSetCommodityFlg(order.getSetCommodityFlg());
    return detail;
  }

  public ShippingDetailBean createShippingDetailBean(String shopCode, String skuCode, String giftCode, Long addQuantity,
      Long orgQuantity) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodity = service.getSkuInfo(shopCode, skuCode);

    if (commodity == null) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.error("Commodity is null. ShopCode = " + shopCode + " SKUCode = " + skuCode);
      throw new RuntimeException(Messages.getString("web.action.back.order.OrderModifyBaseAction.0"));
    }

    OrderDetail newOrderDetail;

    String commodityCode = commodity.getHeader().getCommodityCode();
    List<CodeAttribute> skuList = new ArrayList<CodeAttribute>();
    HashMap<String, ShippingSku> shippingDetailSkuMap = new HashMap<String, ShippingSku>();
    boolean reserve = false;

    // 商品明細にて別同一商品の別SKUを表示する為、SKU一覧を取得し情報設定
    // 但し予約の場合はSKU変更不可
    if (getBean().isReserveFlg()) {
      skuList.add(getSkuNameValue(shopCode, skuCode, true));
      String commodityName = StringUtil.getHeadline(commodity.getHeader().getCommodityName(), 15);
      commodityName += createStandardDetailName(commodity.getDetail().getStandardDetail1Name(), commodity.getDetail()
          .getStandardDetail2Name());
      ShippingSku shippingSku = new ShippingSku(skuCode, commodityName, commodity.getDetail().getUnitPrice());
      shippingDetailSkuMap.put(skuCode, shippingSku);
      reserve = true;
    } else {
      skuList.add(getSkuNameValue(shopCode, skuCode, true));
      String commodityName = StringUtil.getHeadline(commodity.getHeader().getCommodityName(), 15);
      commodityName += createStandardDetailName(commodity.getDetail().getStandardDetail1Name(), commodity.getDetail()
          .getStandardDetail2Name());
      ShippingSku shippingSku = new ShippingSku(skuCode, commodityName, commodity.getDetail().getUnitPrice());
      shippingDetailSkuMap.put(skuCode, shippingSku);
      reserve = false;
    }

    // 配送商品情報にOrderContainerを設定（登録時に使用する）

    OrderDetail oldOrderDetail = getBean().getOldOrderContainer().getOrderDetail(new Sku(shopCode, skuCode));
    if (oldOrderDetail != null) {
      newOrderDetail = oldOrderDetail;
    } else {
      CommodityInfo skuInfo = service.getSkuInfo(shopCode, skuCode);
      newOrderDetail = createOrderDetailCommodityInfo(skuInfo);
    }
    ShippingDetailBean detailBean = new ShippingDetailBean(getMaxDetailNo());
    detailBean.setShippingDetailNo(null);
    detailBean.setShippingDetailGiftList(createGiftList(shopCode, commodityCode, giftCode));
    detailBean.setShippingDetailSkuList(skuList);
    detailBean.setShippingDetailSkuMap(shippingDetailSkuMap);
    detailBean.setShippingDetailCommodityAmount(NumUtil.toString(addQuantity));
    detailBean.setOrgShippingDetailCommodityAmount(orgQuantity);
    detailBean.setGiftCode(giftCode);
    detailBean.setOrgGiftCode(StringUtil.coalesce(giftCode, ""));
    detailBean.setSkuCode(skuCode);
    detailBean.setOrderDetailCommodityInfo(newOrderDetail);
    detailBean.setReserve(reserve);
    detailBean.setWeigth(newOrderDetail.getCommodityWeight());
    newOrderDetail.setPurchasingAmount(addQuantity);
    detailBean.setCommodityType(newOrderDetail.getCommodityType());
    detailBean.setSetCommodityFlg(newOrderDetail.getSetCommodityFlg());

    return detailBean;
  }

  private NameValue getSkuNameValue(String shopCode, String skuCode, boolean isOnlyOne) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();

    String displaySkuCode = "";

    if (!isOnlyOne) {
      displaySkuCode = skuCode + Messages.getString("web.action.back.order.OrderModifyBaseAction.1");
    }

    OrderDetail oldOrderDetail = getBean().getOldOrderContainer().getOrderDetail(new Sku(shopCode, skuCode));
    String name = "";
    if (oldOrderDetail != null) {
      name = displaySkuCode + StringUtil.getHeadline(oldOrderDetail.getCommodityName(), 15)
          + createStandardDetailName(oldOrderDetail.getStandardDetail1Name(), oldOrderDetail.getStandardDetail2Name());
      if (!isOnlyOne) {
        name += Messages.getString("web.action.back.order.OrderModifyBaseAction.1")
            + Price.getFormatPrice(oldOrderDetail.getRetailPrice());
      }
    } else {
      CommodityInfo skuDetail = service.getSkuInfo(shopCode, skuCode);
      CommodityHeader header = skuDetail.getHeader();
      Campaign campaign = service.getAppliedCampaignInfo(header.getShopCode(), header.getCommodityCode());
      Price price = new Price(skuDetail.getHeader(), skuDetail.getDetail(), campaign, taxRate);
      name = displaySkuCode + StringUtil.getHeadline(skuDetail.getHeader().getCommodityName(), 15);
      name += createStandardDetailName(skuDetail.getDetail().getStandardDetail1Name(), skuDetail.getDetail()
          .getStandardDetail2Name());
      if (!isOnlyOne) {
        name += Messages.getString("web.action.back.order.OrderModifyBaseAction.1") + Price.getFormatPrice(price.getRetailPrice());
      }
    }
    return new NameValue(name, skuCode);
  }

  private NameValue getSkuNameValueNoPrice(String shopCode, String skuCode, boolean isOnlyOne) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    String displaySkuCode = "";
    if (!isOnlyOne) {
      displaySkuCode = skuCode + Messages.getString("web.action.back.order.OrderModifyBaseAction.1");
    }

    OrderDetail oldOrderDetail = getBean().getOldOrderContainer().getOrderDetail(new Sku(shopCode, skuCode));
    String name = "";
    if (oldOrderDetail != null) {
      name = displaySkuCode + StringUtil.getHeadline(oldOrderDetail.getCommodityName(), 15)
          + createStandardDetailName(oldOrderDetail.getStandardDetail1Name(), oldOrderDetail.getStandardDetail2Name());

    } else {
      CommodityInfo skuDetail = service.getSkuInfo(shopCode, skuCode);
      name = displaySkuCode + StringUtil.getHeadline(skuDetail.getHeader().getCommodityName(), 15);
      name += createStandardDetailName(skuDetail.getDetail().getStandardDetail1Name(), skuDetail.getDetail()
          .getStandardDetail2Name());

    }
    return new NameValue(name, skuCode);
  }

  /**
   * 最新のDBの情報のみを使用してOrderDetailを生成する。<br />
   * 但し、商品以外の情報は空になる
   * 
   * @param commodity
   * @return OrderDetail
   */
  public OrderDetail createOrderDetailCommodityInfo(CommodityInfo commodity) {
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    OrderDetail orderDetail = new OrderDetail();
    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();

    String shopCode = commodity.getHeader().getShopCode();
    String commodityCode = commodity.getHeader().getCommodityCode();
    Campaign campaign = catalogService.getAppliedCampaignInfo(shopCode, commodityCode);
    Price price = new Price(commodity.getHeader(), commodity.getDetail(), campaign, taxRate);
    CommodityHeader header = commodity.getHeader();
    CommodityDetail detail = commodity.getDetail();

    orderDetail.setOrderNo("");
    orderDetail.setShopCode(header.getShopCode());
    orderDetail.setSkuCode(detail.getSkuCode());
    orderDetail.setCommodityCode(header.getCommodityCode());
    orderDetail.setCommodityName(commodity.getHeader().getCommodityName());
    orderDetail.setStandardDetail1Name(commodity.getDetail().getStandardDetail1Name());
    orderDetail.setStandardDetail2Name(commodity.getDetail().getStandardDetail2Name());
    orderDetail.setUnitPrice(price.getUnitPrice());
    orderDetail.setRetailPrice(price.getRetailPrice());
    if (SetCommodityFlg.OBJECTIN.longValue().equals(commodity.getHeader().getSetCommodityFlg())) {
      orderDetail.setRetailPrice(price.getUnitPrice());
    }
    orderDetail.setRetailTax(price.retailTaxCharge());
    orderDetail.setCommodityTaxRate(price.getTaxRate());
    orderDetail.setCommodityTaxType(commodity.getHeader().getCommodityTaxType());
    orderDetail.setCommodityTax(price.getUnitTaxCharge());

    orderDetail.setCommodityWeight(commodity.getDetail().getWeight());
    orderDetail.setBrandCode(commodity.getHeader().getBrandCode());
    if (StringUtil.hasValue(orderDetail.getBrandCode())) {
      Brand brand = catalogService.getBrand(commodity.getHeader().getShopCode(), commodity.getHeader().getBrandCode());
      if (brand != null) {
        orderDetail.setBrandName(brand.getBrandName());
      }
    }
    CommunicationService commService = ServiceLocator.getCommunicationService(getLoginInfo());
    if (StringUtil.hasValue(commodity.getHeader().getSaleFlag())) {
      Plan plan = commService.getPlan(PlanType.PROMOTION.getValue(), commodity.getHeader().getSaleFlag());
      if (plan != null) {
        orderDetail.setSalePlanCode(plan.getPlanCode());
        orderDetail.setSalePlanName(plan.getPlanName());
      }
    }
    if (StringUtil.hasValue(commodity.getHeader().getSpecFlag())) {
      Plan plan = commService.getPlan(PlanType.FEATURE.getValue(), commodity.getHeader().getSpecFlag());
      if (plan != null) {
        orderDetail.setFeaturedPlanCode(plan.getPlanCode());
        orderDetail.setFeaturedPlanName(plan.getPlanName());
      }
    }
    if (!price.isDiscount()) {
      // 特価期間中でなければ、キャンペーンを取得する
      campaign = catalogService.getAppliedCampaignBySku(header.getShopCode(), detail.getSkuCode());
    }
    if (campaign != null) {
      orderDetail.setCampaignCode(campaign.getCampaignCode());
      orderDetail.setCampaignName(campaign.getCampaignName());
      orderDetail.setCampaignDiscountRate(campaign.getCampaignDiscountRate());
    }

    CommodityHeader cHeader = commodity.getHeader();
    if ((cHeader.getCommodityPointStartDatetime() != null && cHeader.getCommodityPointEndDatetime() != null)
        && DateUtil.isPeriodDate(cHeader.getCommodityPointStartDatetime(), cHeader.getCommodityPointEndDatetime())) {
      orderDetail.setAppliedPointRate(commodity.getHeader().getCommodityPointRate());
    } else {
      orderDetail.setAppliedPointRate(null);
    }
    // 商品区分
    orderDetail.setCommodityType(commodity.getHeader().getCommodityType());
    // 套餐商品区分
    orderDetail.setSetCommodityFlg(commodity.getHeader().getSetCommodityFlg());
    return orderDetail;
  }

  /**
   * 商品情報連番の最大値＋１を取得します。
   * 
   * @return 商品情報連番の最大値＋１
   */
  private String getMaxDetailNo() {
    Long maxNo = 0L;
    for (ShippingHeaderBean header : getBean().getShippingHeaderList()) {
      for (ShippingDetailBean detail : header.getShippingDetailList()) {
        Long detailNo = NumUtil.toLong(detail.getDetailNo());
        if (maxNo <= detailNo) {
          maxNo = detailNo + 1;
        }
      }
    }
    return NumUtil.toString(maxNo);
  }

  /**
   * 送料を更新する
   * 
   * @param bean
   */
  public void recomputeShippingCharge(OrderModifyBean omBean) {
    ShippingHeaderBean bean = omBean.getShippingHeaderList().get(0);
    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    DeliveryType deliveryType = shopService.getDeliveryType(bean.getShippingShopCode(), NumUtil.toLong(bean.getShippingTypeCode()));
    recomputeShippingCharge(deliveryType, bean);
    BigDecimal totalOrderPrice = BigDecimal.ZERO;
    BigDecimal totalWeight = BigDecimal.ZERO;
    for (ShippingDetailBean detail : bean.getShippingDetailList()) {
      if (CommodityType.GENERALGOODS.longValue().equals(detail.getCommodityType())) {
        // 商品合计金额的计算
        if (StringUtil.isNullOrEmpty(detail.getGiftCode()) || detail.getShippingDetailGiftList().size() <= 1) {
          totalOrderPrice = totalOrderPrice.add(detail.getOrderDetailCommodityInfo().getRetailPrice().multiply(
              NumUtil.parse(detail.getShippingDetailCommodityAmount())));
        } else {
          for (GiftAttribute giftAttribute : detail.getShippingDetailGiftList()) {
            if (giftAttribute.getValue().equals(detail.getGiftCode())) {
              totalOrderPrice = totalOrderPrice.add((detail.getOrderDetailCommodityInfo().getRetailPrice().add(giftAttribute
                  .getGiftPrice())).multiply(NumUtil.parse(detail.getShippingDetailCommodityAmount())));
            }
          }
        }
        // 商品合计重量的计算
        if (SetCommodityFlg.OBJECTIN.longValue().equals(detail.getSetCommodityFlg())) {
          BigDecimal setCommodityWeight = BigDecimal.ZERO;
          for (SetCommpositionInfo compositionInfo : detail.getCompositionList()) {
            setCommodityWeight = setCommodityWeight.add(compositionInfo.getComposition().getCommodityWeight());
          }
          totalWeight = totalWeight.add(setCommodityWeight.multiply(NumUtil.parse(detail.getShippingDetailCommodityAmount())));
        } else {
          totalWeight = totalWeight.add(detail.getWeigth().multiply(NumUtil.parse(detail.getShippingDetailCommodityAmount())));
        }
      }
    }
    totalWeight = totalWeight.add(this.getGiftCommodityWeight(getBean()));
    if (!isFreeShippingCharge(omBean)) {
      bean.setShippingCharge(shopService.getShippingCharge(bean.getAddress().getPrefectureCode(), totalOrderPrice, totalWeight,
          bean.getDeliveryCompanyNo()).toString());
    } else {
      bean.setShippingCharge("0");
    }
  }

  /**
   * 根据ShippingHeaderBean查看本次发货是否是免邮费
   * 
   * @param bean
   * @return
   */
  private boolean isFreeShippingCharge(OrderModifyBean omBean) {
    ShippingHeaderBean bean = omBean.getShippingHeaderList().get(0);
    boolean result = false;
    CampainFilter cf = new CampainFilter();
    CampaignInfo campaignI = new CampaignInfo();
    campaignI.setMobileNumber(bean.getAddress().getMobileNumber());
    campaignI.setPhoneNumber(bean.getAddress().getPhoneNumber());
    campaignI.setLastName(bean.getAddress().getLastName());
    campaignI.setAddress1(bean.getAddress().getAddress1());
    campaignI.setAddress2(bean.getAddress().getAddress2());
    campaignI.setAddress3(bean.getAddress().getAddress3());
    campaignI.setAddress4(bean.getAddress().getAddress4());
    if (!StringUtil.isNullOrEmpty(omBean.getOrderHeaderEdit().getCustomerCode())) {
      campaignI.setCustomerCode(omBean.getOrderHeaderEdit().getCustomerCode());
    }
    campaignI.setPrefectureCode(bean.getAddress().getPrefectureCode());
    List<OrderDetail> commodityList = new ArrayList<OrderDetail>();

    for (ShippingDetailBean detail : bean.getShippingDetailList()) {
      if (CommodityType.GIFT.longValue().equals(detail.getCommodityType())) {
        continue;
      }
      OrderDetail orderD = new OrderDetail();
      orderD.setCommodityCode(detail.getOrderDetailCommodityInfo().getCommodityCode());
      orderD.setPurchasingAmount(NumUtil.toLong(detail.getShippingDetailCommodityAmount()));
      commodityList.add(orderD);
    }
    campaignI.setCommodityList(commodityList);
    campaignI.setOrderDateTime(getBean().getOldOrderContainer().getOrderHeader().getOrderDatetime());
    result = cf.isFreeShippingCharge(campaignI);
    return result;
  }

  /**
   * 送料を更新する
   * 
   * @param deliveryType
   * @param bean
   */
  public void recomputeShippingCharge(DeliveryType deliveryType, ShippingHeaderBean bean) {

    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    int itemCount = 0;
    BigDecimal subTotal = BigDecimal.ZERO;
    for (ShippingDetailBean detail : bean.getShippingDetailList()) {
      itemCount += NumUtil.toLong(detail.getShippingDetailCommodityAmount());

      if (StringUtil.isNullOrEmpty(detail.getGiftCode()) || detail.getShippingDetailGiftList().size() <= 1) {
        subTotal = subTotal.add(detail.getOrderDetailCommodityInfo().getRetailPrice().multiply(
            NumUtil.parse(detail.getShippingDetailCommodityAmount())));
      } else {
        for (GiftAttribute giftAttribute : detail.getShippingDetailGiftList()) {
          if (giftAttribute.getValue().equals(detail.getGiftCode())) {
            // ギフトを選択した場合
            subTotal = subTotal.add((detail.getOrderDetailCommodityInfo().getRetailPrice().add(giftAttribute.getGiftPrice()))
                .multiply(NumUtil.parse(detail.getShippingDetailCommodityAmount())));
          }
        }
      }
    }

    // 配送種別による送料計算
    BigDecimal charge = shopService.calculateShippingCharge(deliveryType.getShopCode(), deliveryType.getDeliveryTypeNo(), bean
        .getAddress().getPrefectureCode(), itemCount, subTotal);

    bean.setShippingCharge(NumUtil.toString(charge));
    String shippingChargeTaxType = NumUtil.toString(deliveryType.getShippingChargeTaxType());
    bean.setShippingChargeTaxType(shippingChargeTaxType);
    bean.setShippingChargeTaxTypeName(TaxType.fromValue(shippingChargeTaxType).getName());
    bean.setModified(true);
  }

  /**
   * 規格詳細名称を生成する<BR>
   * 規格詳細１名称と規格詳細２名称両方存在する場合<BR>
   * (規格詳細１名称/規格詳細２名称)<BR>
   * 規格詳細１名称のみ存在する場合<BR>
   * (規格詳細１名称)<BR>
   * それ以外の場合空文字を返す
   * 
   * @param detail1Name
   * @param detail2Name
   * @return 規格詳細名称
   */
  private String createStandardDetailName(String detail1Name, String detail2Name) {
    String detail = "";
    if (StringUtil.hasValue(detail1Name) && StringUtil.hasValue(detail2Name)) {
      detail = "(" + detail1Name + "/" + detail2Name + ")";
    } else if (StringUtil.hasValue(detail1Name) && StringUtil.isNullOrEmpty(detail2Name)) {
      detail = "(" + detail1Name + ")";
    } else {
      detail = "";
    }
    return detail;
  }

  /**
   * 変更後合計金額情報を更新する
   * 
   * @param bean
   */
  public WebActionResult recomputePrice(OrderModifyBean bean) {
    BigDecimal giftPriceTotal = BigDecimal.ZERO;
    BigDecimal shippingChargeTotal = BigDecimal.ZERO;
    BigDecimal commodityPriceTotal = BigDecimal.ZERO;

    for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
      shippingChargeTotal = shippingChargeTotal.add(NumUtil.parse(headerBean.getShippingCharge()));
      for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
        if (CommodityType.GIFT.longValue().equals(detailBean.getCommodityType())) {
          continue;
        }
        BigDecimal commodityPrice = detailBean.getOrderDetailCommodityInfo().getRetailPrice();
        commodityPriceTotal = commodityPriceTotal.add(commodityPrice.multiply(NumUtil.parse(detailBean
            .getShippingDetailCommodityAmount())));
        BigDecimal giftPrice = getGiftPrice(detailBean, headerBean.getShippingShopCode());
        giftPriceTotal = giftPriceTotal.add(giftPrice.multiply(NumUtil.parse(detailBean.getShippingDetailCommodityAmount())));
      }
    }

    TotalPrice afterTotalPrice = bean.getAfterTotalPrice();
    afterTotalPrice.setShippingCharge(shippingChargeTotal);
    afterTotalPrice.setCommodityPrice(commodityPriceTotal);
    afterTotalPrice.setGiftPrice(giftPriceTotal);
    afterTotalPrice.setAllPrice(BigDecimalUtil.addAll(shippingChargeTotal, commodityPriceTotal, giftPriceTotal));
    afterTotalPrice.setGrandAllPrice(afterTotalPrice.getAllPrice());
    afterTotalPrice.setAllPrice(afterTotalPrice.getAllPrice().add(afterTotalPrice.getPaymentCommission()).add(afterTotalPrice.getOutCardUsePrice()));
    if ("1".equals(bean.getUseOrgDiscount())) {
      CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
      NewCouponRule newCouponRule = customerService.getPublicCoupon(bean.getOrgCashierDiscount().getDiscountCode());
      if (newCouponRule == null) {
        afterTotalPrice.setDiscountPrice(BigDecimal.ZERO);
        bean.setAfterTotalPrice(afterTotalPrice);
        return BackActionResult.RESULT_SUCCESS;
      }
      if (bean.getOrgCashierDiscount().getDiscountRate() != null && bean.getOrgCashierDiscount().getDiscountRate() > 0L) {

        BigDecimal discountPrice;
        if (StringUtil.isNullOrEmpty(newCouponRule.getObjectCommodities())) {
          if (newCouponRule.getMaxUseOrderAmount() != null) {
            if (commodityPriceTotal.compareTo(newCouponRule.getMaxUseOrderAmount()) > 0) {
              discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(newCouponRule.getMaxUseOrderAmount(), newCouponRule
                  .getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
            } else {
              discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(commodityPriceTotal, newCouponRule
                  .getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
            }
          } else {
            discountPrice = BigDecimalUtil.divide(
                BigDecimalUtil.multiply(commodityPriceTotal, newCouponRule.getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
          }
        } else {
          // 取得优惠券包含对象商品list
          String[] commodityList = newCouponRule.getObjectCommodities().split(";");
          BigDecimal result = BigDecimal.ZERO;
          for (String commodityCode : commodityList) {
            for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
              for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
                if (commodityCode.equals(detailBean.getSkuCode())) {
                  result = result.add(BigDecimalUtil.multiply(detailBean.getOrderDetailCommodityInfo().getRetailPrice(), Long
                      .valueOf(detailBean.getShippingDetailCommodityAmount())));
                }
              }
            }
          }
          BigDecimal commodityTotalPrice = result;
          if (newCouponRule.getMaxUseOrderAmount() == null) {
            discountPrice = BigDecimalUtil.divide(
                BigDecimalUtil.multiply(commodityTotalPrice, newCouponRule.getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
          } else {
            if (commodityTotalPrice.compareTo(newCouponRule.getMaxUseOrderAmount()) > 0) {
              discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(newCouponRule.getMaxUseOrderAmount(), newCouponRule
                  .getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
            } else {
              discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(commodityTotalPrice, newCouponRule
                  .getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
            }
          }
        }
        afterTotalPrice.setDiscountPrice(discountPrice);
      }

    } else if (StringUtil.isNullOrEmpty(bean.getNewCashierDiscount().getDiscountCode())) {
      afterTotalPrice.setDiscountPrice(BigDecimal.ZERO);
    }
    bean.setAfterTotalPrice(afterTotalPrice);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 出荷商品情報をマージする
   * 
   * @param detailList
   * @return List<ShippingDetailBean>
   */
  public List<ShippingDetailBean> margeShippingDetail(List<ShippingDetailBean> detailList) {
    Map<String, ShippingDetailBean> detailMap = new LinkedHashMap<String, ShippingDetailBean>();

    for (ShippingDetailBean detail : detailList) {
      String skuCode = detail.getSkuCode();
      if (SetCommodityFlg.OBJECTIN.longValue().equals(detail.getSetCommodityFlg())) {
        skuCode = detail.getSetCommoditySkuCode();
      }
      if (CommodityType.GIFT.longValue().equals(detail.getCommodityType())) {
        skuCode = skuCode + "_" + detail.getCampaignCode();
      }
      if (detailMap.containsKey(detail.getSkuCode())) {
        ShippingDetailBean copyDetail = detailMap.get(skuCode);
        // 重複する商品があった場合数量を加算
        String shippingDetailCommodityAmount = NumUtil.summary(copyDetail.getShippingDetailCommodityAmount(), detail
            .getShippingDetailCommodityAmount());
        copyDetail.setShippingDetailCommodityAmount(shippingDetailCommodityAmount);
      } else {
        detailMap.put(skuCode, detail);
      }
    }

    List<ShippingDetailBean> shippingDetailList = new ArrayList<ShippingDetailBean>();
    for (Entry<String, ShippingDetailBean> set : detailMap.entrySet()) {
      shippingDetailList.add(set.getValue());
    }
    return shippingDetailList;
  }

  /**
   * ギフトの金額を取得する。
   * 
   * @param shopCode
   * @param giftCode
   * @return ギフト金額
   */
  private BigDecimal getGiftPrice(ShippingDetailBean detail, String shopCode) {
    BigDecimal price = BigDecimal.ZERO;
    if (StringUtil.isNullOrEmpty(detail.getGiftCode())) {
      return price;
    } else {
      GiftAttribute giftAttribute = getGiftAttribute(detail, detail.getGiftCode());
      if (giftAttribute != null) {
        price = giftAttribute.getGiftPrice();
      } else {
        CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
        Gift gift = service.getGift(shopCode, detail.getGiftCode());
        TaxUtil taxUtil = DIContainer.get("TaxUtil");
        if (gift != null) {
          price = Price.getPriceIncludingTax(gift.getGiftPrice(), taxUtil.getTaxRate(), NumUtil.toString(gift.getGiftTaxType()));
        }
      }
    }
    return price;
  }

  /**
   * 新規受注時の情報から合致する出荷明細を1件取得する 存在しない場合はnullを返す<br/>
   * 使用目的は旧ギフト情報の取得に限る
   * 
   * @param shopCode
   * @param giftCode
   * @return ShippingDetail
   */
  public ShippingDetail getShippingDetailFromGiftCode(String shopCode, String giftCode) {
    if (StringUtil.isNullOrEmpty(giftCode)) {
      return null;
    }
    for (ShippingContainer shipping : getBean().getOldOrderContainer().getShippings()) {
      if (shipping.getShippingHeader().getShopCode().equals(shopCode)) {
        for (ShippingDetail detail : shipping.getShippingDetails()) {
          if (giftCode.equals(detail.getGiftCode())) {
            return detail;
          }
        }
      }
    }
    return null;
  }

  public ShippingDetail getShippingDetailFromGiftCode(String shippingNo, Long shippingDetailNo, String giftCode) {
    if (StringUtil.isNullOrEmpty(giftCode)) {
      return null;
    }
    for (ShippingContainer shipping : getBean().getOldOrderContainer().getShippings()) {
      if (shippingNo != null && shippingNo.equals(shipping.getShippingNo())) {
        for (ShippingDetail detail : shipping.getShippingDetails()) {
          if (shippingDetailNo != null && shippingDetailNo.equals(detail.getShippingDetailNo())
              && giftCode.equals(detail.getGiftCode())) {
            return detail;
          }
        }
      }
    }
    return null;
  }

  /**
   * ポイント使用再計算を行う。<BR>
   * 使用ポイントが全額ポイント支払に必要なポイントより多い場合<BR>
   * 支払に必要なポイント値に下方修正する。<BR>
   * 
   * @param bean
   */
  public void recomputePoint(OrderModifyBean bean) {
    PointBean pointInfo = bean.getPointInfo();
    if (pointInfo == null) {
      pointInfo = new PointBean();
    }

    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
    BigDecimal allPoint = null;
    if (BigDecimalUtil.isAboveOrEquals(bean.getAfterTotalPrice().getCommodityPrice(), service.getCouponRule()
        .getCouponInvestPurchasePrice())) {
      allPoint = BigDecimalUtil.subtract(bean.getAfterTotalPrice().getGrandAllPrice(), bean.getCouponInfoBean()
          .getAfterCouponPrice());
    } else {
      allPoint = bean.getAfterTotalPrice().getGrandAllPrice();
      bean.getCouponInfoBean().clearAfterCouponSelect();
      bean.getCouponInfoBean().setAfterCouponPrice(BigDecimal.ZERO);
    }

    BigDecimal usePoint = NumUtil.parse(pointInfo.getUsePoint());
    if (ValidatorUtil.moreThan(NumUtil.parse(pointInfo.getUsePoint()), NumUtil.parse(pointInfo.getRestPoint()))) {
      pointInfo.setUsePoint(PointUtil.getUsePointWhenUsePointAboveRestPoint(NumUtil.parse(pointInfo.getRestPoint())));
    }
    if (ValidatorUtil.moreThanOrEquals(usePoint, allPoint.multiply(DIContainer.getWebshopConfig().getRmbPointRate()).setScale(
        PointUtil.getAcquiredPointScale(), RoundingMode.CEILING))) {
      pointInfo.setUsePoint(PointUtil.getUsePointWhenUsePointAboveTotalPricePoint(allPoint));
      pointInfo.setTotalPrice(PointUtil.getTotalPyamentPrice(allPoint, NumUtil.parse(pointInfo.getUsePoint())));
    } else {
      pointInfo.setTotalPrice(PointUtil.getTotalPyamentPrice(allPoint, NumUtil.parse(pointInfo.getUsePoint())));
    }

    bean.setPointInfo(pointInfo);
    BigDecimal allPointPayment = BigDecimalUtil.multiply(allPoint, DIContainer.getWebshopConfig().getRmbPointRate()).setScale(
        PointUtil.getAcquiredPointScale(), RoundingMode.CEILING);
    if (BigDecimalUtil.isAbove(allPointPayment, BigDecimal.ZERO)) {
      bean.getAfterTotalPrice().setAllPointPaymentPoint(allPointPayment);
    } else {
      bean.getAfterTotalPrice().setAllPointPaymentPoint(BigDecimal.ZERO);
    }
  }

  public void recomputeCoupon(OrderModifyBean bean) {
    BigDecimal afertCouponPrice = BigDecimal.ZERO;
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    if (bean.getUseCouponList() != null && bean.getUseCouponList().size() > 0) {
      for (String str : bean.getUseCouponList()) {
        if (StringUtil.hasValue(str)) {
          CustomerCoupon cc = service.getCustomerCoupon(str);
          afertCouponPrice = BigDecimalUtil.add(afertCouponPrice, cc.getCouponPrice());
        }
      }
    }
    bean.getCouponInfoBean().setAfterCouponPrice(afertCouponPrice);
  }

  public void recomputePaymentCommission(OrderModifyBean bean) {
    PointBean pointInfo = bean.getPointInfo();
    if (pointInfo == null) {
      pointInfo = new PointBean();
    }
    // 支払手数料再計算
    PaymentSupporter supporter = PaymentSupporterFactory.createPaymentSuppoerter();
    BigDecimal totalPrice = pointInfo.getTotalPrice();

    supporter.updatePaymentCommission(bean.getOrderPayment(), totalPrice);

    // 支払方法一覧の更新
    CashierPayment payment = supporter.createCashierPayment(bean.getOrderPayment());
    PaymentMethodBean newPayment = supporter.createPaymentMethodBean(payment, pointInfo.getTotalPrice(), NumUtil.parse(pointInfo
        .getUsePoint()), false, bean.getAfterTotalPrice().getDiscountPrice(), "");
    for (int i = 0; i < newPayment.getDisplayPaymentList().size(); i++) {
      PaymentTypeBase paymentBase = newPayment.getDisplayPaymentList().get(i);
      // 支払方法リストのパラメータのリカバリ処理
      if (paymentBase.isCreditcard()) {
        for (PaymentTypeBase oldPayment : bean.getOrderPayment().getDisplayPaymentList()) {
          if (oldPayment.isCreditcard()) {
            newPayment.getDisplayPaymentList().set(i, oldPayment);
          }
        }
      } else if (paymentBase.isCvsPayment()) {
        for (PaymentTypeBase oldPayment : bean.getOrderPayment().getDisplayPaymentList()) {
          if (oldPayment.isCvsPayment()) {
            newPayment.getDisplayPaymentList().set(i, oldPayment);
          }
        }
      } else if (paymentBase.isDigitalCash()) {
        for (PaymentTypeBase oldPayment : bean.getOrderPayment().getDisplayPaymentList()) {
          if (oldPayment.isDigitalCash()) {
            newPayment.getDisplayPaymentList().set(i, oldPayment);
          }
        }
      }
    }

    // 全額ポイント払いの場合の制御
    if (BigDecimalUtil.equals(totalPrice, BigDecimal.ZERO)) {
      for (PaymentTypeBase paymentBase : newPayment.getDisplayPaymentList()) {
        if (paymentBase.isPointInFull()) {
          newPayment.setPaymentMethodCode(paymentBase.getPaymentMethodCode());
        }
        // 支払方法リストのパラメータのリカバリ処理
        if (paymentBase.isCreditcard()) {
          for (PaymentTypeBase oldPayment : bean.getOrderPayment().getDisplayPaymentList()) {
            if (oldPayment.isCreditcard()) {
              paymentBase = oldPayment;
            }
          }
        } else if (paymentBase.isCvsPayment()) {
          for (PaymentTypeBase oldPayment : bean.getOrderPayment().getDisplayPaymentList()) {
            if (oldPayment.isCvsPayment()) {
              paymentBase = oldPayment;
            }
          }
        } else if (paymentBase.isDigitalCash()) {
          for (PaymentTypeBase oldPayment : bean.getOrderPayment().getDisplayPaymentList()) {
            if (oldPayment.isDigitalCash()) {
              paymentBase = oldPayment;
            }
          }
        }
      }
    }
    bean.setOrderPayment(newPayment);

    TotalPrice afterTotalPrice = bean.getAfterTotalPrice();
    for (PaymentTypeBase displayPayment : bean.getOrderPayment().getDisplayPaymentList()) {
      if (bean.getOrderPayment().getPaymentMethodCode().equals(displayPayment.getPaymentMethodCode())) {
        if (displayPayment.getPaymentMethodType().equals(PaymentMethodType.POINT_IN_FULL.getValue())) {
          afterTotalPrice.setNotPointInFull(false);
          addWarningMessage(WebMessage.get(OrderErrorMessage.POINT_IN_FULL));
        } else {
          afterTotalPrice.setNotPointInFull(true);
        }
        bean.setAfterTotalPrice(afterTotalPrice);
      }
    }
    // 配送公司设定
    BigDecimal deliveryTimeCommssion = BigDecimal.ZERO;
    UtilService utilService = ServiceLocator.getUtilService(ServiceLoginInfo.getInstance());
    for (ShippingHeaderBean header : bean.getShippingHeaderList()) {
      boolean codType = false;
      for (PaymentTypeBase paymentType : bean.getOrderPayment().getDisplayPaymentList()) {
        if (paymentType.isCashOnDelivery()
            && paymentType.getPaymentMethodCode().equals(bean.getOrderPayment().getPaymentMethodCode())) {
          codType = true;
          break;
        }
      }
      // 获得总重量
      BigDecimal totalWeight = BigDecimal.ZERO;
      for (ShippingDetailBean detail : header.getShippingDetailList()) {
        if (CommodityType.GENERALGOODS.longValue().equals(detail.getCommodityType())) {
          if (SetCommodityFlg.OBJECTIN.longValue().equals(detail.getSetCommodityFlg())) {
            BigDecimal setCommodityWeight = BigDecimal.ZERO;
            for (SetCommpositionInfo compositionInfo : detail.getCompositionList()) {
              setCommodityWeight = setCommodityWeight.add(compositionInfo.getComposition().getCommodityWeight());
            }
            totalWeight = totalWeight.add(setCommodityWeight.multiply(NumUtil.parse(detail.getShippingDetailCommodityAmount())));
          } else {
            totalWeight = totalWeight.add(detail.getWeigth().multiply(NumUtil.parse(detail.getShippingDetailCommodityAmount())));
          }
        }
      }
      totalWeight = totalWeight.add(this.getGiftCommodityWeight(getBean()));
      List<DeliveryCompany> deliveryCompanyList = new ArrayList<DeliveryCompany>();
      DeliveryCompany deliveryCompany = null;

      // 根据支付方式取得全部配送公司
      deliveryCompanyList = utilService.getDeliveryCompanys(header.getShippingShopCode(), header.getAddress().getPrefectureCode(),
          header.getAddress().getCityCode(), header.getAddress().getAreaCode(), codType, totalWeight.toString());

      List<DeliveryCompany> lsDcb = new ArrayList<DeliveryCompany>();
      DeliveryCompanyBean cacheDCBean = new DeliveryCompanyBean();
      cacheDCBean.setDeliveryCompanyNo("D000");
      for (DeliveryCompany dc : deliveryCompanyList) {
        if (!cacheDCBean.getDeliveryCompanyNo().equals(dc.getDeliveryCompanyNo())) {
          DeliveryCompany dcb = new DeliveryCompany();
          dcb.setDeliveryCompanyNo(dc.getDeliveryCompanyNo());
          dcb.setDeliveryCompanyName(dc.getDeliveryCompanyName());

          lsDcb.add(dcb);
        }
        cacheDCBean.setDeliveryCompanyNo(dc.getDeliveryCompanyNo());
      }

      if (lsDcb.size() > 0) {
        if (lsDcb.size() == 1) {// 如果只发现一条配送公司，那么选择该条配送公司进行运费计算
          deliveryCompany = lsDcb.get(0);
        } else {// 如果有两条以上配送公司，那么选画面单选按钮选中的配送公司
          if (!StringUtil.isNullOrEmpty(header.getDeliveryCompanyNo())) {
            DeliveryCompanyDao dao = DIContainer.getDao(DeliveryCompanyDao.class);
            deliveryCompany = dao.load(header.getDeliveryCompanyNo());
          } else {// 如果画面配送公司单选按钮未选择，那么用默认D000编号查询运费今昔
            deliveryCompany = new DeliveryCompany();
            deliveryCompany.setDeliveryCompanyNo("D000");
          }
        }
      } else {
        deliveryCompany = utilService.getDefaultDeliveryCompany();
      }
      if (deliveryCompany != null) {
        // 手续费设定
        ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());
        DeliveryRegion deliveryRegion = shopService.getDeliveryRegion(deliveryCompany.getShopCode(), deliveryCompany
            .getDeliveryCompanyNo(), header.getAddress().getPrefectureCode());
        if (deliveryRegion != null) {
          deliveryTimeCommssion = deliveryRegion.getDeliveryDatetimeCommission();
        }
      }
    }
    bean.getAfterTotalPrice().setPaymentCommission(deliveryTimeCommssion);
    // soukai add 2012/01/10 ob end
  }

  /**
   * SKUごとに画面の数量からオリジナルの数量を引いたものを返す。
   * 
   * @param shopCode
   *          商品のショップコード
   * @param skuCode
   *          SKUコード
   * @return SKUごとの画面の数量とオリジナルの数量の差分<br>
   *         新規に追加の場合は、0を返す。
   */
  public Long getCurrentCommodityTotalAmount(String shopCode, String skuCode) {
    // 新受注のSKU単位の商品合計数量

    Long newCommodityAmount = 0L;
    for (ShippingHeaderBean header : getBean().getShippingHeaderList()) {
      if (header.getShippingShopCode().equals(shopCode)) {
        for (ShippingDetailBean detail : header.getShippingDetailList()) {
          if (detail.getSkuCode().equals(skuCode)) {
            // 画面で購入予定の商品数を加算
            newCommodityAmount += NumUtil.toLong(detail.getShippingDetailCommodityAmount());
          }
        }
      }
    }
    return newCommodityAmount;
  }

  public Long getOldCommodityTotalAmount(String shopCode, String skuCode) {
    Long amount = 0L;
    for (ShippingContainer container : getBean().getOldOrderContainer().getShippings()) {
      if (container.getShippingHeader().getShopCode().equals(shopCode)) {
        for (ShippingDetail detail : container.getShippingDetails()) {
          if (detail.getSkuCode().equals(skuCode)) {
            amount += detail.getPurchasingAmount();
          }
        }
      }
    }
    return amount;
  }

  /**
   * 予約商品の在庫・注文毎予約上限数と、予約以外の商品の在庫チェックを行います。
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param totalOldAmount
   *          数量(前回)
   * @param totalCurrentAmount
   *          数量(現在)
   * @return 入力値にエラーがなければtrueを返します。
   */
  public boolean validationStock(String shopCode, String skuCode, Long totalOldAmount, Long totalCurrentAmount) {
    boolean valid = true;

    boolean isReserve = getBean().isReserveFlg();
    Long totalDiffAmount = totalCurrentAmount - totalOldAmount;

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    CommodityAvailability commodityAvailability = service.isAvailable(shopCode, skuCode, totalDiffAmount.intValue(), isReserve);
    if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
      if (totalOldAmount == 0L) {
        valid &= false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, skuCode));
      } else if (totalCurrentAmount > 0L && totalCurrentAmount > totalOldAmount) {
        valid &= false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST_ADDITION, skuCode));
      }
    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
      if (totalOldAmount == 0L) {
        valid &= false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD, skuCode));
      } else if (totalCurrentAmount > 0L && totalCurrentAmount > totalOldAmount) {
        valid &= false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST_ADDITION, skuCode));
      }
    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
      if (totalOldAmount == 0L || totalCurrentAmount > totalOldAmount) {
        valid &= false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.NO_AVAILABLE_STOCK_SKU, skuCode));
      }
    } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
      valid &= false;
      addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE, skuCode, NumUtil.toString(CartUtil.getAvailableQuantity(
          shopCode, skuCode, isReserve)
          + totalOldAmount)));
    } else if (commodityAvailability.equals(CommodityAvailability.RESERVATION_LIMIT_OVER)
        || commodityAvailability.equals(CommodityAvailability.OUT_OF_RESERVATION_STOCK)) {
      // 予約上限数エラー or 注文毎予約上限数エラー

      if (totalOldAmount == 0L || totalCurrentAmount > totalOldAmount) {
        // 旧予約に存在しないか、旧予約より多い数を予約使用とした場合エラー
        // 注文毎予約上限数のチェック

        Long commodityAmount = 0L;
        for (ShippingHeaderBean header : getBean().getShippingHeaderList()) {
          for (ShippingDetailBean detail : header.getShippingDetailList()) {
            if (detail.getSkuCode().equals(skuCode)) {
              commodityAmount += NumUtil.toLong(detail.getShippingDetailCommodityAmount());
            }
          }
        }
        Stock stock = service.getStock(shopCode, skuCode);
        Long oneshotReservationLimit = stock.getOneshotReservationLimit();
        Long reservationAvailable = service.getReservationAvailableStock(shopCode, skuCode);

        if (oneshotReservationLimit != null) {
          if (totalOldAmount > oneshotReservationLimit) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.RESERVATION_OVER, skuCode, NumUtil.toString(totalOldAmount)));
            valid &= false;
          } else if (oneshotReservationLimit >= reservationAvailable) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.RESERVATION_OVER, skuCode, NumUtil.toString(oneshotReservationLimit)));
            valid &= false;
          }
        } else {
          addErrorMessage(WebMessage.get(OrderErrorMessage.RESERVATION_OVER, skuCode, NumUtil.toString(reservationAvailable
              + totalOldAmount)));
          valid &= false;
        }
      }
    } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
      if (getBean().isReserveFlg() && (totalOldAmount == 0L || totalCurrentAmount > totalOldAmount)) {
        // 注文毎予約上限数は別チェックをする(上記のチェックは差分でしかチェックしていないため上限数チェックができない）

        Stock stock = service.getStock(shopCode, skuCode);
        Long oneshotReservationLimit = stock.getOneshotReservationLimit();
        if (oneshotReservationLimit != null && totalCurrentAmount > oneshotReservationLimit) {
          if (totalOldAmount > oneshotReservationLimit) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.RESERVATION_OVER, skuCode, NumUtil.toString(totalOldAmount)));
            valid &= false;
          } else {
            addErrorMessage(WebMessage.get(OrderErrorMessage.RESERVATION_OVER, skuCode, NumUtil.toString(oneshotReservationLimit)));
          }
        } else {
          valid &= true;
        }
      } else {
        valid &= true;
      }
    }

    return valid;
  }

  public boolean validationShippingDetailStock(OrderModifyBean bean, boolean checkStock) {
    boolean valid = true;
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    List<String> skuList = new ArrayList<String>();
    Map<String, Long> stockMap = new HashMap<String, Long>();
    for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
      for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
        // 普通商品的在库验证
        if (CommodityType.GENERALGOODS.longValue().equals(detailBean.getCommodityType())) {
          if (!SetCommodityFlg.OBJECTIN.longValue().equals(detailBean.getSetCommodityFlg())) {
            Long oldAmount = bean.getOrgStockMap().get(detailBean.getSkuCode());
            if (oldAmount == null) {
              oldAmount = 0L;
            }
            if (skuList.contains(detailBean.getSkuCode())) {
              stockMap.put(detailBean.getSkuCode(), NumUtil.toLong(detailBean.getShippingDetailCommodityAmount())
                  + stockMap.get(detailBean.getSkuCode()));
            } else {
              skuList.add(detailBean.getSkuCode());
              stockMap.put(detailBean.getSkuCode(), NumUtil.toLong(detailBean.getShippingDetailCommodityAmount()));
            }
            valid = valid
                & validationStock(bean.getShopCode(), detailBean.getSkuCode(), oldAmount, NumUtil.toLong(detailBean
                    .getShippingDetailCommodityAmount()));
          } else {
            for (SetCommpositionInfo info : detailBean.getCompositionList()) {
              if (skuList.contains(info.getSkuCode())) {
                stockMap.put(info.getSkuCode(), NumUtil.toLong(detailBean.getShippingDetailCommodityAmount())
                    + stockMap.get(info.getSkuCode()));
              } else {
                skuList.add(info.getSkuCode());
                stockMap.put(info.getSkuCode(), NumUtil.toLong(detailBean.getShippingDetailCommodityAmount()));
              }
            }
            // 套餐商品的在库验证
            valid = valid & validationSetStock(detailBean);
          }

        }

      }
    }
    if (valid && checkStock) {
      for (String skuCode : skuList) {
        Long stockAmount = service.getAvailableStock(bean.getShopCode(), skuCode);
        if (stockAmount == -1L) {
          continue;
        } else {
          if (getBean().getOrgStockMap().get(skuCode) != null) {
            stockAmount = stockAmount + getBean().getOrgStockMap().get(skuCode);
          }
          if (stockAmount < stockMap.get(skuCode)) {
            valid &= false;
            addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE, skuCode, NumUtil.toString(stockAmount)));
          }
        }
      }

    }
    return valid;
  }

  private boolean validationSetStock(ShippingDetailBean detailBean) {
    boolean valid = true;
    String shopCode = getBean().getShopCode();
    boolean orgFlg = false;
    boolean isReserve = getBean().isReserveFlg();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    for (OrderDetail detail : getBean().getOldOrderContainer().getOrderDetails()) {
      if (detail.getSkuCode().equals(detailBean.getSkuCode())) {
        orgFlg = true;
        break;
      }
    }
    // 套餐明细商品的件数小于2件时，不可购买
    if (detailBean.getCompositionList() == null) {
      valid &= false;
      addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, detailBean.getSkuCode()));
      return valid;
    }

    // 套餐商品本身贩卖可能验证
    CommodityAvailability commodityAvailability = service.isAvailable(shopCode, detailBean.getSkuCode(), 1, isReserve, true);
    if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
      if (!orgFlg) {
        valid &= false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, detailBean.getSkuCode()));
      }
    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
      if (!orgFlg) {
        valid &= false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD, detailBean.getSkuCode()));
      }
    }
    for (SetCommpositionInfo compositionInfo : detailBean.getCompositionList()) {
      commodityAvailability = service.isAvailable(shopCode, compositionInfo.getSkuCode(), 1, isReserve);
      if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
        if (!orgFlg) {
          valid &= false;
          addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, detailBean.getSkuCode()));
        }
      } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
        if (!orgFlg) {
          valid &= false;
          addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD, detailBean.getSkuCode()));
        }
      }
    }
    if (valid) {
      Long amount = NumUtil.toLong(detailBean.getShippingDetailCommodityAmount());
      Long mixAmount = -1L;

      for (SetCommpositionInfo setDetail : detailBean.getCompositionList()) {
        Long stockAmount = service.getAvailableStock(shopCode, setDetail.getSkuCode());
        if (stockAmount == -1L) {
          continue;
        }
        if (getBean().getOrgStockMap().get(setDetail.getSkuCode()) != null) {
          stockAmount = stockAmount + getBean().getOrgStockMap().get(setDetail.getSkuCode());
        }
        if (mixAmount == -1L) {
          mixAmount = stockAmount;
        } else if (stockAmount < mixAmount) {
          mixAmount = stockAmount;
        }
      }
      if (mixAmount < amount && mixAmount != -1L) {
        valid &= false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE, detailBean.getSkuCode(), NumUtil.toString(mixAmount)));
      }
    }
    return valid;
  }

  /**
   * セット品在庫チェックを行います。
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param totalOldAmount
   *          数量(前回)
   * @param totalCurrentAmount
   *          数量(現在)
   * @return 入力値にエラーがなければtrueを返します。
   */
  public boolean validationSetStock(OrderModifyBean bean, String skuCode, boolean addFlg) {
    boolean valid = true;
    String shopCode = bean.getShopCode();
    boolean orgFlg = false;
    boolean isReserve = getBean().isReserveFlg();

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    ShippingDetailBean detailBean = initSetShippingDetailBean(bean, skuCode);

    if (addFlg) {
      detailBean = bean.getSetCommodityInfo();
    }

    for (OrderDetail detail : bean.getOldOrderContainer().getOrderDetails()) {
      if (detail.getSkuCode().equals(skuCode)) {
        orgFlg = true;
        break;
      }
    }
    // 套餐明细商品的件数小于2件时，不可购买
    if (detailBean.getCompositionList() == null || detailBean.getCompositionList().size() < 2) {
      valid &= false;
      addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, skuCode));
      return valid;
    }

    // 套餐商品本身贩卖可能验证
    CommodityAvailability commodityAvailability = service.isAvailable(shopCode, skuCode, 1, isReserve, true);
    if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
      if (!orgFlg) {
        valid &= false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, skuCode));
        return valid;
      }
    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
      if (!orgFlg) {
        valid &= false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD, skuCode));
        return valid;
      }
    }
    for (SetCommpositionInfo compositionInfo : detailBean.getCompositionList()) {
      commodityAvailability = service.isAvailable(shopCode, compositionInfo.getSkuCode(), 1, isReserve);
      if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
        if (!orgFlg) {
          valid &= false;
          addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, skuCode));
          return valid;
        }
      } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
        if (!orgFlg) {
          valid &= false;
          addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD, skuCode));
          return valid;
        }
      }
    }
    if (addFlg && valid) {
      Long amount = NumUtil.toLong(detailBean.getShippingDetailCommodityAmount());
      Long mixAmount = -1L;
      for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
        for (ShippingDetailBean shippingDetailBean : headerBean.getShippingDetailList()) {
          if (detailBean.getSkuCode().equals(shippingDetailBean.getSkuCode())
              && detailBean.getCompositionList().size() == shippingDetailBean.getCompositionList().size()) {
            List<String> list1 = new ArrayList<String>();
            List<String> list2 = new ArrayList<String>();
            for (SetCommpositionInfo info : detailBean.getCompositionList()) {
              list1.add(info.getSkuCode());
            }
            for (SetCommpositionInfo info : shippingDetailBean.getCompositionList()) {
              list2.add(info.getSkuCode());
            }
            if (list1.containsAll(list2)) {
              amount = amount + NumUtil.toLong(shippingDetailBean.getShippingDetailCommodityAmount());
              break;
            }
          }
        }
      }
      for (SetCommpositionInfo setDetail : detailBean.getCompositionList()) {
        Long stockAmount = service.getAvailableStock(bean.getShopCode(), setDetail.getSkuCode());
        if (stockAmount == -1L) {
          continue;
        }
        if (bean.getOrgStockMap().get(setDetail.getSkuCode()) != null) {
          stockAmount = stockAmount + bean.getOrgStockMap().get(setDetail.getSkuCode());
        }
        if (mixAmount == -1L) {
          mixAmount = stockAmount;
        } else if (stockAmount < mixAmount) {
          mixAmount = stockAmount;
        }
      }
      if (mixAmount < amount && mixAmount != -1L) {
        valid &= false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE, skuCode, NumUtil.toString(mixAmount)));
      } else {
        boolean isSame = false;
        for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
          for (ShippingDetailBean shippingDetailBean : headerBean.getShippingDetailList()) {
            if (detailBean.getSkuCode().equals(shippingDetailBean.getSkuCode())
                && detailBean.getCompositionList().size() == shippingDetailBean.getCompositionList().size()) {
              List<String> list1 = new ArrayList<String>();
              List<String> list2 = new ArrayList<String>();
              for (SetCommpositionInfo info : detailBean.getCompositionList()) {
                list1.add(info.getSkuCode());
              }
              for (SetCommpositionInfo info : shippingDetailBean.getCompositionList()) {
                list2.add(info.getSkuCode());
              }
              if (list1.containsAll(list2)) {
                shippingDetailBean.setShippingDetailCommodityAmount(NumUtil.toString(amount));
                isSame = true;
                detailBean = null;
                break;
              }
            }
          }
        }
        if (!isSame) {
          for (SetCommpositionInfo info : detailBean.getCompositionList()) {
            for (CommodityDetail commodityDetail : info.getCommodityDetailList()) {
              if (commodityDetail.getSkuCode().equals(info.getSkuCode())) {
                info.getComposition().setUnitPrice(commodityDetail.getUnitPrice());
                info.getComposition().setChildSkuCode(commodityDetail.getSkuCode());
                info.getComposition().setStandardDetail1Name(commodityDetail.getStandardDetail1Name());
                info.getComposition().setStandardDetail2Name(commodityDetail.getStandardDetail2Name());
                info.getComposition().setCommodityWeight(commodityDetail.getWeight());
              }
            }
          }
          bean.getShippingHeaderList().get(0).getShippingDetailList().add(detailBean);
          detailBean = null;
        }
      }
    }
    if (valid) {
      bean.setSetCommodityInfo(detailBean);
    }
    return valid;
  }

  public ShippingDetailBean initSetShippingDetailBean(OrderModifyBean bean, String skuCode) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodityInfo = service.getSkuInfo(bean.getShopCode(), skuCode);
    ShippingDetailBean detailBean = null;
    detailBean = createShippingDetailBean(bean.getShopCode(), skuCode, null, NumUtil.toLong(bean.getAddCommodityEdit()
        .getAddAmount()), 0L);
    for (ShippingContainer shippingContainer : bean.getOldOrderContainer().getShippings()) {
      for (ShippingDetail detail : shippingContainer.getShippingDetails()) {
        if (skuCode.equals(detail.getSkuCode())) {
          for (ShippingDetailComposition detailComposition : shippingContainer.getShippingDetailContainerMap().get(detail)) {
            SetCommpositionInfo compositionInfo = new SetCommpositionInfo();
            compositionInfo.setCommodityCode(detailComposition.getChildCommodityCode());
            compositionInfo.setRetailPrice(detailComposition.getRetailPrice());
            detailBean.getCompositionList().add(compositionInfo);
          }
          break;
        }
      }
    }
    if (detailBean.getCompositionList().size() <= 0) {
      List<SetCommodityComposition> commodityList = service.getSetCommodityCompositipon(commodityInfo.getHeader()
          .getCommodityCode(), bean.getShopCode());
      for (SetCommodityComposition composition : commodityList) {
        SetCommpositionInfo compositionInfo = new SetCommpositionInfo();
        compositionInfo.setCommodityCode(composition.getChildCommodityCode());
        compositionInfo.setRetailPrice(composition.getRetailPrice());
        detailBean.getCompositionList().add(compositionInfo);
      }
    }
    for (SetCommpositionInfo compositionInfo : detailBean.getCompositionList()) {
      initSetCompositionInfo(compositionInfo, commodityInfo.getHeader().getShopCode(), commodityInfo.getHeader().getCommodityCode());
    }
    return detailBean;
  }

  private void initSetCompositionInfo(SetCommpositionInfo info, String shopCode, String commodityCode) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodityInfo = service.getCommodityInfo(shopCode, commodityCode);
    CommodityInfo childCommodityInfo = service.getCommodityInfo(shopCode, info.getCommodityCode());
    List<CommodityDetail> detailList = service.getCommoditySku(shopCode, info.getCommodityCode());
    info.setSkuCode(childCommodityInfo.getHeader().getRepresentSkuCode());
    info.setRepresentSkuCode(childCommodityInfo.getHeader().getRepresentSkuCode());
    info.setCommodityDetailList(detailList);
    List<CodeAttribute> skuList = new ArrayList<CodeAttribute>();
    for (CommodityDetail detail : detailList) {
      skuList.add(getSkuNameValueNoPrice(detail.getShopCode(), detail.getSkuCode(), detailList.size() == 1));
    }
    info.setShippingDetailSkuList(skuList);
    ShippingDetailComposition detailComposition = new ShippingDetailComposition();
    detailComposition.setShopCode(shopCode);
    detailComposition.setParentCommodityCode(commodityCode);
    detailComposition.setParentSkuCode(commodityInfo.getHeader().getRepresentSkuCode());
    detailComposition.setChildCommodityCode(info.getCommodityCode());
    detailComposition.setChildSkuCode(childCommodityInfo.getHeader().getRepresentSkuCode());
    detailComposition.setCommodityName(childCommodityInfo.getHeader().getCommodityName());
    detailComposition.setUnitPrice(childCommodityInfo.getHeader().getRepresentSkuUnitPrice());
    detailComposition.setCommodityTaxType(childCommodityInfo.getHeader().getCommodityTaxType());
    info.setComposition(detailComposition);
  }

  public List<CodeAttribute> createTimeList() {
    List<CodeAttribute> values = new ArrayList<CodeAttribute>();
    values.add(new NameValue("--", ""));
    for (int i = 0; i <= 23; i++) {
      String year = Integer.toString(i);
      String dispYear = year;
      if (year.length() == 1) {
        dispYear = "0" + year;
      }
      values.add(new NameValue(dispYear, year));
    }
    return values;
  }

  public boolean isNotAvailableGift(String shopCode, String skuCode, String giftCode) {
    // 最新の使用可能なギフトを全て取得し、合致するギフトがあればＯＫ
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodity = catalogService.getSkuInfo(shopCode, skuCode);
    return isNotAvailableGift(commodity.getHeader(), giftCode);
  }

  public boolean isNotAvailableGift(CommodityHeader commodity, String giftCode) {
    // 最新の使用可能なギフトを全て取得し、合致するギフトがあればＯＫ
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());

    boolean notUsableGift = true;
    List<Gift> giftList = catalogService.getAvailableGiftList(commodity.getShopCode(), commodity.getCommodityCode());
    for (Gift usableGift : giftList) {
      if (usableGift.getGiftCode().equals(giftCode)) {
        notUsableGift = false;
      }
    }
    return notUsableGift;
  }

  /**
   * ポイントのドロップダウンの価格を旧受注に置き換える
   * 
   * @param shopCode
   * @param commodityCode
   * @param usedGiftCode
   */
  public List<GiftAttribute> createGiftList(String shopCode, String commodityCode, String usedGiftCode) {
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    TaxUtil u = DIContainer.get("TaxUtil");
    Long taxRate = u.getTaxRate();

    List<GiftAttribute> giftList = new ArrayList<GiftAttribute>();
    giftList.add(new GiftAttribute(Messages.getString("web.action.back.order.OrderModifyBaseAction.2"), ""));
    // 旧受注で使用されていたギフトの追加

    if (StringUtil.hasValue(usedGiftCode)) {
      ShippingDetail usedGift = getShippingDetailFromGiftCode(shopCode, usedGiftCode);
      GiftAttribute giftAttribute = new GiftAttribute();

      giftAttribute.setName(usedGift.getGiftName() + "(" + Price.getFormatPrice(usedGift.getGiftPrice()) + ")");
      giftAttribute.setValue(usedGift.getGiftCode());
      giftAttribute.setGiftName(usedGift.getGiftName());
      giftAttribute.setGiftPrice(usedGift.getGiftPrice());
      giftAttribute.setGiftTaxType(usedGift.getGiftTaxType());
      giftAttribute.setGiftTaxRate(usedGift.getGiftTaxRate());
      giftAttribute.setGiftTax(usedGift.getGiftTax());
      // 最新情報で使用不可なギフトは更新不可

      giftAttribute.setUpdatableGift(false);
      giftList.add(giftAttribute);
    }

    for (Gift gift : catalogService.getAvailableGiftList(shopCode, commodityCode)) {
      GiftAttribute giftAttribute = new GiftAttribute();
      // 旧出荷のギフト情報取得

      ShippingDetail detail = getShippingDetailFromGiftCode(shopCode, gift.getGiftCode());

      String orgGiftCode = "";
      if (detail != null) {
        orgGiftCode = detail.getGiftCode();
      }
      if (StringUtil.isNullOrEmpty(orgGiftCode)) {
        // 旧受注で設定されてないギフト情報は最新のものをそのまま使用

        BigDecimal giftPrice = Price.getPriceIncludingTax(gift.getGiftPrice(), taxRate, String.valueOf(gift.getGiftTaxType()));
        String name = gift.getGiftName() + "(" + Price.getFormatPrice(giftPrice) + ")";

        giftAttribute.setName(name);
        giftAttribute.setValue(gift.getGiftCode());
        giftAttribute.setGiftName(gift.getGiftName());
        giftAttribute.setGiftPrice(giftPrice);
        giftAttribute.setGiftTaxType(gift.getGiftTaxType());
        giftAttribute.setGiftTaxRate(taxRate);
        giftAttribute.setGiftTax(Price.getPriceTaxCharge(gift.getGiftPrice(), taxRate, NumUtil.toString(gift.getGiftTaxType())));
        // 最新情報で使用可能なギフトは更新可能

        giftAttribute.setUpdatableGift(true);
      } else if (StringUtil.hasValue(usedGiftCode) && orgGiftCode.equals(usedGiftCode)) {
        // 最新情報で使用可能なギフトは更新可能に更新する
        giftAttribute = giftList.get(1);
        giftList.remove(giftAttribute);
        giftAttribute.setUpdatableGift(true);
      } else {
        // 旧受注で設定されているギフト情報はその当時のものを使用
        giftAttribute.setName(detail.getGiftName() + "(" + Price.getFormatPrice(detail.getGiftPrice()) + ")");
        giftAttribute.setValue(detail.getGiftCode());
        giftAttribute.setGiftName(detail.getGiftName());
        giftAttribute.setGiftPrice(detail.getGiftPrice());
        giftAttribute.setGiftTaxType(detail.getGiftTaxType());
        giftAttribute.setGiftTaxRate(detail.getGiftTaxRate());
        giftAttribute.setGiftTax(detail.getGiftTax());
        // 最新情報で使用可能なギフトは更新可能
        giftAttribute.setUpdatableGift(true);
      }
      giftList.add(giftAttribute);
    }
    return giftList;
  }

  public GiftAttribute getGiftAttribute(ShippingDetailBean detailBean, String giftCode) {
    for (GiftAttribute gift : detailBean.getShippingDetailGiftList()) {
      if (gift.getValue().equals(StringUtil.coalesce(giftCode, ""))) {
        return gift;
      }
    }
    return null;
  }

  public OrderContainer createOrderContainer(OrderModifyBean bean) {
    OrderContainer container = new OrderContainer();

    List<CustomerCoupon> ccl = new ArrayList<CustomerCoupon>();
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    CommunicationService commService = ServiceLocator.getCommunicationService(getLoginInfo());
    for (String str : bean.getUseCouponList()) {
      if (StringUtil.hasValue(str)) {
        CustomerCoupon cc = service.getCustomerCoupon(str);
        ccl.add(cc);
      }
    }
    container.setCustomerCouponlist(ccl);

    // 受注ヘッダ情報コピー
    container.setOrderHeader(createOrderHeader(bean));

    List<ShippingContainer> shippingList = new ArrayList<ShippingContainer>();
    for (ShippingHeaderBean header : bean.getShippingHeaderList()) {
      ShippingContainer shipping = new ShippingContainer();
      // 出荷ヘッダ情報コピー
      shipping.setShippingHeader(createShippingHeader(container, header));

      List<ShippingDetail> shippingDetailList = new ArrayList<ShippingDetail>();
      for (ShippingDetailBean detail : header.getShippingDetailList()) {
        // 出荷明細（商品）情報コピー
        shippingDetailList.add(createShippingDetail(header.getShippingShopCode(), detail));
        if (SetCommodityFlg.OBJECTIN.longValue().equals(detail.getSetCommodityFlg())) {
          shipping.getShippingDetailContainerMap().put(shippingDetailList.get(shippingDetailList.size() - 1),
              createShippingDetailComposition(detail));
        }
        for (GiftInfo giftInfo : detail.getGiftCommodityList()) {
          shippingDetailList.add(giftInfo.getShippingDetail());
        }
      }
      List<String> skuList = new ArrayList<String>();
      Map<String, ShippingDetail> skuMap = new HashMap<String, ShippingDetail>();
      List<ShippingDetail> removeList = new ArrayList<ShippingDetail>();
      for (ShippingDetail shippingDetail : shippingDetailList) {
        if (CommodityType.GIFT.longValue().equals(shippingDetail.getCommodityType())) {
          String keyWord = shippingDetail.getSkuCode();
          if (StringUtil.hasValue(shippingDetail.getCampaignCode())) {
            keyWord = keyWord + "_" + shippingDetail.getCampaignCode();
          }
          if (skuList.contains(keyWord)) {
            ShippingDetail oldShippingDetail = skuMap.get(keyWord);
            oldShippingDetail.setPurchasingAmount(oldShippingDetail.getPurchasingAmount() + shippingDetail.getPurchasingAmount());
            skuMap.put(keyWord, oldShippingDetail);
          } else {
            skuList.add(keyWord);
            skuMap.put(keyWord, (ShippingDetail) shippingDetail.clone());
          }
          removeList.add(shippingDetail);
        }
      }
      shippingDetailList.removeAll(removeList);
      for (String keyWord : skuList) {
        shippingDetailList.add(skuMap.get(keyWord));
      }
      shipping.setShippingDetails(shippingDetailList);

      shippingList.add(shipping);
    }

    container.setShippings(shippingList);

    // 受注明細情報コピー（出荷明細情報より生成）

    container.setOrderDetails(createOrderDetailList(container));

    if (InvoiceFlg.NEED.getValue().equals(bean.getOrderInvoice().getInvoiceFlg())) {
      OrderInvoice invoiceBean = new OrderInvoice();
      invoiceBean.setOrderNo(bean.getOrderNo());
      invoiceBean.setCustomerCode(bean.getOrderHeaderEdit().getCustomerCode());
      CustomerVatInvoice vatInvoice = new CustomerVatInvoice();
      setupOrderInvoiceInfo(invoiceBean, vatInvoice);
      container.setOrderInvoice(invoiceBean);
      container.setCustomerVatInvoice(vatInvoice);
    }


    BigDecimal totalPayment = BigDecimal.ZERO;
    if (container.getOrderHeader().getPaymentCommission() != null) {
      // 指定时间段手续费
      totalPayment = totalPayment.add(container.getOrderHeader().getPaymentCommission());
    }
    totalPayment = totalPayment.add(container.getTotalAmount());
    if (container.getOrderHeader().getDiscountPrice() == null) {
      container.getOrderHeader().setDiscountPrice(BigDecimal.ZERO);
    }
    if (container.getOrderHeader().getDiscountPrice().compareTo(totalPayment) == 0) {
      PaymentMethodDao dao = DIContainer.getDao(PaymentMethodDao.class);
      PaymentMethod noPayment = dao.load(PaymentMethodType.NO_PAYMENT.getValue());
      if (noPayment == null) {
        throw new RuntimeException();
      }
      container.getOrderHeader().setPaymentDate(DateUtil.getSysdate());
      container.getOrderHeader().setPaymentLimitDate(null);
      container.getOrderHeader().setPaymentMethodName(noPayment.getPaymentMethodName());
      container.getOrderHeader().setPaymentMethodNo(noPayment.getPaymentMethodNo());
      container.getOrderHeader().setPaymentMethodType(noPayment.getPaymentMethodType());
      container.getOrderHeader().setPaymentStatus(PaymentStatus.PAID.longValue());
      container.getOrderHeader().setAdvanceLaterFlg(noPayment.getAdvanceLaterFlg());
      for (ShippingContainer shipping : container.getShippings()) {
        shipping.getShippingHeader().setShippingStatus(ShippingStatus.READY.longValue());
      }
    }


    List<OrderCampaign> campaignList = new ArrayList<OrderCampaign>();
    List<String> codeList = new ArrayList<String>();
    for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
      for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
        if (StringUtil.hasValue(detailBean.getCampaignCode())) {
          if (!codeList.contains(detailBean.getCampaignCode())) {
            CampaignInfo info = commService.getCampaignInfo(detailBean.getCampaignCode());
            if (info != null && info.getCampaignMain() != null) {
              OrderCampaign orderCampaign = new OrderCampaign();
              orderCampaign.setOrderNo(bean.getOrderNo());
              orderCampaign.setCampaignCode(info.getCampaignMain().getCampaignCode());
              orderCampaign.setCampaignName(info.getCampaignMain().getCampaignName());
              orderCampaign.setCampaignType(info.getCampaignMain().getCampaignType());
              orderCampaign.setAttributrValue(info.getCampaignDoings().getAttributrValue());
              codeList.add(info.getCampaignMain().getCampaignCode());
              campaignList.add(orderCampaign);
            }
          }
        }
        if (detailBean.getGiftCommodityList() != null && detailBean.getGiftCommodityList().size() > 0) {
          for (GiftInfo giftInfo : detailBean.getGiftCommodityList()) {
            if (!codeList.contains(giftInfo.getShippingDetail().getCampaignCode())) {
              CampaignInfo info = commService.getCampaignInfo(giftInfo.getShippingDetail().getCampaignCode());
              if (info != null && info.getCampaignMain() != null) {
                OrderCampaign orderCampaign = new OrderCampaign();
                orderCampaign.setOrderNo(bean.getOrderNo());
                orderCampaign.setCampaignCode(info.getCampaignMain().getCampaignCode());
                orderCampaign.setCampaignName(info.getCampaignMain().getCampaignName());
                orderCampaign.setCampaignType(info.getCampaignMain().getCampaignType());
                orderCampaign.setAttributrValue(info.getCampaignDoings().getAttributrValue());
                codeList.add(info.getCampaignMain().getCampaignCode());
                campaignList.add(orderCampaign);
              }
            }
          }
        }
      }
    }
    container.setOrderCampaigns(campaignList);
    return container;
  }

  /**
   * 受注ヘッダ情報生成
   * 
   * @param orderHeader
   * @param bean
   */
  private OrderHeader createOrderHeader(OrderModifyBean bean) {
    OrderHeader orderHeader = new OrderHeader();

    orderHeader.setOrderNo(bean.getOrderNo());
    orderHeader.setShopCode(bean.getShopCode());
    // 最初に受注した日を設定する為、Actionでは変更しない

    OrderHeaderBean orderBean = bean.getOrderHeaderEdit();
    orderHeader.setGuestFlg(NumUtil.toLong(bean.getOrderHeaderEdit().getGuestFlg()));
    orderHeader.setLastName(orderBean.getAddress().getLastName());
    orderHeader.setFirstName(orderBean.getAddress().getFirstName());
    orderHeader.setLastNameKana(orderBean.getAddress().getLastNameKana());
    orderHeader.setFirstNameKana(orderBean.getAddress().getFirstNameKana());
    orderHeader.setEmail(orderBean.getAddress().getCustomerEmail());
    orderHeader.setPostalCode(orderBean.getAddress().getPostalCode());
    orderHeader.setCouponPrice(bean.getCouponInfoBean().getAfterCouponPrice());
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    orderHeader.setPrefectureCode(orderBean.getAddress().getPrefectureCode());
    orderHeader.setAddress1(s.getPrefectureName(orderBean.getAddress().getPrefectureCode()));
    orderHeader.setAddress2(s.getCityName(orderBean.getAddress().getPrefectureCode(), orderBean.getAddress().getCityCode()));
    orderHeader.setCityCode(orderBean.getAddress().getCityCode());
    orderHeader.setAreaCode(orderBean.getAddress().getAreaCode());
    orderHeader.setAddress3(s.getAreaName(orderBean.getAddress().getAreaCode()));
    orderHeader.setAddress4(orderBean.getAddress().getAddress4());
    if (StringUtil.hasValueAllOf(orderBean.getAddress().getPhoneNumber1(), orderBean.getAddress().getPhoneNumber2(), orderBean
        .getAddress().getPhoneNumber3())) {
      orderHeader.setPhoneNumber(StringUtil.joint('-', orderBean.getAddress().getPhoneNumber1(), orderBean.getAddress()
          .getPhoneNumber2(), orderBean.getAddress().getPhoneNumber3()));
    } else if (StringUtil.hasValueAllOf(orderBean.getAddress().getPhoneNumber1(), orderBean.getAddress().getPhoneNumber2())) {
      orderHeader.setPhoneNumber(StringUtil.joint('-', orderBean.getAddress().getPhoneNumber1(), orderBean.getAddress()
          .getPhoneNumber2()));
    } else {
      orderHeader.setPhoneNumber("");
    }

    orderHeader.setMobileNumber(orderBean.getAddress().getMobileNumber());
    PaymentMethodBean paymentBean = bean.getOrderPayment();
    PaymentTypeBase selectPayment = PaymentSupporterFactory.createPaymentSuppoerter().getSelectPaymentType(paymentBean);
    // 支払方法関連情報取得
    String paymentMethodCode = paymentBean.getPaymentMethodCode();
    orderHeader.setPaymentMethodNo(NumUtil.toLong(paymentMethodCode));

    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    PaymentMethodSuite payment = shopService.getPaymentMethod(bean.getShopCode(), orderHeader.getPaymentMethodNo());
    if (payment != null && payment.getPaymentMethod() != null) {
      orderHeader.setPaymentMethodName(payment.getPaymentMethod().getPaymentMethodName());
      orderHeader.setPaymentMethodType(payment.getPaymentMethod().getPaymentMethodType());
      BigDecimal commission = selectPayment.getPaymentCommission();
      Long taxRate = payment.getPaymentMethod().getPaymentCommissionTaxRate();
      if (taxRate == null) {
        taxRate = 0L;
      }
      Long taxType = payment.getPaymentMethod().getPaymentCommissionTaxType();
      orderHeader.setPaymentCommission(bean.getAfterTotalPrice().getPaymentCommission());
      orderHeader.setPaymentCommissionTaxRate(taxRate);
      orderHeader.setPaymentCommissionTaxType(taxType);
      orderHeader.setPaymentCommissionTax(Price.getPriceTaxCharge(commission, taxRate, NumUtil.toString(taxType)));
      orderHeader.setAdvanceLaterFlg(payment.getPaymentMethod().getAdvanceLaterFlg());
      Long paymentLimitDays = payment.getPaymentMethod().getPaymentLimitDays();
      if (paymentLimitDays != null) {
        Date limitDate = DateUtil.addDate(DateUtil.getSysdate(), paymentLimitDays.intValue());
        orderHeader.setPaymentLimitDate(DateUtil.truncateDate(limitDate));
      }

      // 支払方法区分が、支払不要、ポイント全額の場合、支払済み

      if (payment.getPaymentMethod().getPaymentMethodType().equals(PaymentMethodType.NO_PAYMENT.getValue())
          || payment.getPaymentMethod().getPaymentMethodType().equals(PaymentMethodType.POINT_IN_FULL.getValue())) {
        orderHeader.setPaymentStatus(PaymentStatus.PAID.longValue());
        orderHeader.setPaymentDate(DateUtil.fromString(DateUtil.getSysdateString()));
      } else {
        orderHeader.setPaymentStatus(PaymentStatus.NOT_PAID.longValue());
      }
    } else {
      orderHeader.setPaymentStatus(PaymentStatus.NOT_PAID.longValue());
    }
    orderHeader.setClientGroup(bean.getOldOrderContainer().getOrderHeader().getClientGroup());

    orderHeader.setDataTransportStatus(NumUtil.toLong(bean.getOrderDataTransportFlg()));
    orderHeader.setCaution(bean.getOrderHeaderEdit().getCaution());
    orderHeader.setMessage(bean.getOrderHeaderEdit().getMessage());
    orderHeader.setUpdatedDatetime(bean.getUpdateDatetime());

    orderHeader.setUsedPoint(NumUtil.parse(bean.getPointInfo().getUsePoint()));

    if (InvoiceFlg.NEED.getValue().equals(bean.getOrderInvoice().getInvoiceFlg())) {
      orderHeader.setInvoiceFlg(InvoiceFlg.NEED.longValue());
    } else {
      orderHeader.setInvoiceFlg(InvoiceFlg.NO_NEED.longValue());
    }
    if ("1".equals(bean.getUseOrgDiscount())) {
      orderHeader.setDiscountCode(bean.getOrgCashierDiscount().getDiscountCode());
      orderHeader.setDiscountDetailCode(bean.getOrgCashierDiscount().getDiscountDetailCode());
      orderHeader.setDiscountMode(NumUtil.toLong(bean.getOrgCashierDiscount().getDiscountMode()));
      orderHeader.setDiscountType(NumUtil.toLong(bean.getOrgCashierDiscount().getCouponType()));
      orderHeader.setDiscountName(bean.getOrgCashierDiscount().getDiscountName());
      orderHeader.setDiscountRate(bean.getOrgCashierDiscount().getDiscountRate());
      if (bean.getOrgCashierDiscount().getDiscountRate() != null && bean.getOrgCashierDiscount().getDiscountRate() > 0L) {
        orderHeader.setDiscountPrice(bean.getAfterTotalPrice().getDiscountPrice());
      } else {
        if (bean.getAfterTotalPrice().getCommodityPrice().compareTo(bean.getOrgCashierDiscount().getDiscountPrice()) < 0) {
          orderHeader.setDiscountPrice(bean.getAfterTotalPrice().getCommodityPrice());
        } else {
          orderHeader.setDiscountPrice(bean.getOrgCashierDiscount().getDiscountPrice());
        }
      }
    } else if (bean.getNewCashierDiscount() != null && StringUtil.hasValue(bean.getNewCashierDiscount().getDiscountCode())) {
      orderHeader.setDiscountCode(bean.getNewCashierDiscount().getDiscountCode());
      orderHeader.setDiscountDetailCode(bean.getNewCashierDiscount().getDiscountDetailCode());
      orderHeader.setDiscountMode(NumUtil.toLong(bean.getNewCashierDiscount().getDiscountMode()));
      orderHeader.setDiscountType(NumUtil.toLong(bean.getNewCashierDiscount().getCouponType()));
      orderHeader.setDiscountName(bean.getNewCashierDiscount().getDiscountName());
      orderHeader.setDiscountRate(bean.getNewCashierDiscount().getDiscountRate());
      if (bean.getAfterTotalPrice().getCommodityPrice().compareTo(bean.getNewCashierDiscount().getDiscountPrice()) < 0) {
        orderHeader.setDiscountPrice(bean.getAfterTotalPrice().getCommodityPrice());
      } else {
        orderHeader.setDiscountPrice(bean.getNewCashierDiscount().getDiscountPrice());
      }
    }

    if (!"1".equals(bean.getUseOrgDiscount())) {
      if (StringUtil.hasValue(bean.getDiscountPrice())) {
        if (bean.getAfterTotalPrice().getCommodityPrice().compareTo(new BigDecimal(bean.getDiscountPrice())) < 0) {
          orderHeader.setDiscountPrice(bean.getAfterTotalPrice().getCommodityPrice());
        } else {
          orderHeader.setDiscountPrice(new BigDecimal(bean.getDiscountPrice()));
        }
      }
    }
    // 2013/04/18 优惠券对应 ob update end
    orderHeader.setOrderFlg(OrderFlg.fromValue(bean.getOrderFlg()).longValue());
    orderHeader.setMobileComputerType(bean.getMobileComputerType());
    orderHeader.setUseAgent(bean.getUseAgentStr());
    orderHeader.setGiftCardUsePrice(bean.getGiftCardUsePrice());
    orderHeader.setOuterCardPrice(bean.getAfterTotalPrice().getOutCardUsePrice());
    orderHeader.setOrderClientType(bean.getOrderClientType());
    return orderHeader;
  }

  /**
   * 出荷ヘッダ情報生成
   * 
   * @param shippingHeader
   * @param bean
   * @param header
   */
  private ShippingHeader createShippingHeader(OrderContainer container, ShippingHeaderBean header) {
    // 共通情報の取得
    TaxUtil taxUtil = DIContainer.get("TaxUtil");
    Long currentTaxRate = taxUtil.getTaxRate();

    ShippingHeader shippingHeader = new ShippingHeader();

    OrderHeaderBean orderBean = getBean().getOrderHeaderEdit();

    shippingHeader.setOrderNo(getBean().getOrderNo());
    shippingHeader.setShippingNo(header.getShippingNo());
    shippingHeader.setShopCode(header.getShippingShopCode());
    shippingHeader.setCustomerCode(orderBean.getCustomerCode());
    shippingHeader.setAddressNo(header.getShippingAddressNo());
    shippingHeader.setAddressLastName(header.getAddress().getLastName());
    shippingHeader.setAddressFirstName(header.getAddress().getFirstName());
    shippingHeader.setAddressLastNameKana(header.getAddress().getLastNameKana());
    shippingHeader.setAddressFirstNameKana(header.getAddress().getFirstNameKana());
    shippingHeader.setPostalCode(header.getAddress().getPostalCode());
    shippingHeader.setPrefectureCode(header.getAddress().getPrefectureCode());
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    shippingHeader.setAddress1(s.getPrefectureName(header.getAddress().getPrefectureCode()));
    shippingHeader.setAddress2(s.getCityName(header.getAddress().getPrefectureCode(), header.getAddress().getCityCode()));
    shippingHeader.setCityCode(header.getAddress().getCityCode());
    shippingHeader.setAddress3(s.getAreaName(header.getAddress().getAreaCode()));
    shippingHeader.setAreaCode(header.getAddress().getAreaCode());
    shippingHeader.setAddress4(header.getAddress().getAddress4());
    if (StringUtil.hasValueAllOf(header.getAddress().getPhoneNumber1(), header.getAddress().getPhoneNumber2(), header.getAddress()
        .getPhoneNumber3())) {
      shippingHeader.setPhoneNumber(StringUtil.joint('-', header.getAddress().getPhoneNumber1(), header.getAddress()
          .getPhoneNumber2(), header.getAddress().getPhoneNumber3()));
    } else if (StringUtil.hasValueAllOf(header.getAddress().getPhoneNumber1(), header.getAddress().getPhoneNumber2())) {
      shippingHeader.setPhoneNumber(StringUtil.joint('-', header.getAddress().getPhoneNumber1(), header.getAddress()
          .getPhoneNumber2()));
    } else {
      shippingHeader.setPhoneNumber("");
    }
    if (StringUtil.hasValueAnyOf(header.getAddress().getMobileNumber())) {
      shippingHeader.setMobileNumber(header.getAddress().getMobileNumber());
    }
    shippingHeader.setDeliveryRemark(header.getDeliveryRemark());

    BigDecimal shippingCharge = NumUtil.parse(header.getShippingCharge());
    String shippingChargeTaxType = header.getShippingChargeTaxType();
    shippingHeader.setShippingCharge(shippingCharge);
    shippingHeader.setShippingChargeTaxType(NumUtil.toLong(shippingChargeTaxType));
    shippingHeader.setShippingChargeTaxRate(currentTaxRate);
    shippingHeader.setShippingChargeTax(Price.getPriceTaxChargeInclueding(shippingCharge, currentTaxRate, shippingChargeTaxType));
    shippingHeader.setDeliveryTypeNo(NumUtil.toLong(header.getShippingTypeCode()));
    shippingHeader.setDeliveryTypeName(header.getShippingTypeName());
    String ymd = header.getDeliveryAppointedDate();
    shippingHeader.setDeliveryAppointedDate(ymd);
    String startTime = header.getDeliveryAppointedStartTime();
    String endTime = header.getDeliveryAppointedEndTime();
    if (StringUtil.hasValue(startTime)) {
      shippingHeader.setDeliveryAppointedTimeStart(NumUtil.toLong(startTime));
    }
    if (StringUtil.hasValue(endTime)) {
      shippingHeader.setDeliveryAppointedTimeEnd(NumUtil.toLong(endTime));
    }

    shippingHeader.setShippingDirectDate(null);

    // 後先区分で出荷ステータスを変える(後払いなら出荷可能・先払いなら入金待ち)
    Long advanceLaterFlg = container.getOrderHeader().getAdvanceLaterFlg();
    AdvanceLaterType advance = AdvanceLaterType.fromValue(NumUtil.toString(advanceLaterFlg));
    Long status = 0L;
    if (advance == AdvanceLaterType.ADVANCE) {
      status = ShippingStatus.NOT_READY.longValue();
    } else if (advance == AdvanceLaterType.LATER) {
      status = ShippingStatus.READY.longValue();
    }
    shippingHeader.setShippingStatus(status);
    shippingHeader.setReturnItemLossMoney(BigDecimal.ZERO);
    shippingHeader.setReturnItemType(0L);
    shippingHeader.setFixedSalesStatus(FixedSalesStatus.NOT_FIXED.longValue());

    boolean codType = false;
    // 配送公司设定
    for (PaymentTypeBase paymentType : getBean().getOrderPayment().getDisplayPaymentList()) {
      if (paymentType.isCashOnDelivery()
          && paymentType.getPaymentMethodCode().equals(getBean().getOrderPayment().getPaymentMethodCode())) {
        codType = true;
        break;
      }
    }

    // 获得总重量
    BigDecimal totalWeight = BigDecimal.ZERO;
    for (ShippingDetailBean detail : header.getShippingDetailList()) {
      if (CommodityType.GENERALGOODS.longValue().equals(detail.getCommodityType())) {
        if (SetCommodityFlg.OBJECTIN.longValue().equals(detail.getSetCommodityFlg())) {
          BigDecimal setCommodityWeight = BigDecimal.ZERO;
          for (SetCommpositionInfo compositionInfo : detail.getCompositionList()) {
            setCommodityWeight = setCommodityWeight.add(compositionInfo.getComposition().getCommodityWeight());
          }
          totalWeight = totalWeight.add(setCommodityWeight.multiply(NumUtil.parse(detail.getShippingDetailCommodityAmount())));
        } else {
          totalWeight = totalWeight.add(detail.getWeigth().multiply(NumUtil.parse(detail.getShippingDetailCommodityAmount())));
        }
      }
    }
    totalWeight = totalWeight.add(this.getGiftCommodityWeight(getBean()));
    List<DeliveryCompany> deliveryCompanyList = new ArrayList<DeliveryCompany>();
    DeliveryCompany deliveryCompany = null;
    UtilService utilService = ServiceLocator.getUtilService(ServiceLoginInfo.getInstance());
    // 根据支付方式取得全部配送公司
    deliveryCompanyList = utilService.getDeliveryCompanys(shippingHeader.getShopCode(), shippingHeader.getPrefectureCode(),
        shippingHeader.getCityCode(), shippingHeader.getAreaCode(), codType, totalWeight.toString());

    if (deliveryCompanyList.size() > 0) {
      if (deliveryCompanyList.size() == 1) {// 如果只发现一条配送公司，那么选择该条配送公司进行运费计算
        deliveryCompany = deliveryCompanyList.get(0);
      } else {// 如果有两条以上配送公司，那么选画面单选按钮选中的配送公司
        if (!StringUtil.isNullOrEmpty(getBean().getShippingHeaderList().get(0).getDeliveryCompanyNo())) {
          DeliveryCompanyDao dao = DIContainer.getDao(DeliveryCompanyDao.class);
          deliveryCompany = dao.load(getBean().getShippingHeaderList().get(0).getDeliveryCompanyNo());
        } else {// 如果画面配送公司单选按钮未选择，那么用默认D000编号查询运费今昔
          deliveryCompany = new DeliveryCompany();
          deliveryCompany.setDeliveryCompanyNo("D000");
        }
      }
    } else {
      deliveryCompany = utilService.getDefaultDeliveryCompany();
    }

    if (deliveryCompany == null || deliveryCompany.getDeliveryCompanyNo() == null) {
      deliveryCompany = utilService.getDefaultDeliveryCompany();
    }
    if (deliveryCompany != null) {
      shippingHeader.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
      shippingHeader.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());
    }
    return shippingHeader;
  }

  /**
   * ショップコード＋ShippingDetailBeanから出荷明細情報を1件生成する
   * 
   * @param shopCode
   * @param detail
   * @return 出荷明細情報
   */
  private ShippingDetail createShippingDetail(String shopCode, ShippingDetailBean detail) {
    // 共通情報の取得
    TaxUtil taxUtil = DIContainer.get("TaxUtil");
    Long currentTaxRate = taxUtil.getTaxRate();

    ShippingDetail shippingDetail = new ShippingDetail();

    shippingDetail.setShippingDetailNo(detail.getShippingDetailNo());
    shippingDetail.setShopCode(shopCode);
    shippingDetail.setSkuCode(detail.getSkuCode());

    OrderDetail order = detail.getOrderDetailCommodityInfo();

    // 旧受注時のキャンペーンコードが存在すればそれを適用させる
    String campaignCode = order.getCampaignCode();
    shippingDetail.setUnitPrice(order.getUnitPrice());
    // 旧受注時の販売価格が存在すればそれを適用する
    shippingDetail.setRetailPrice(order.getRetailPrice());
    shippingDetail.setRetailTax(Price.getPriceTaxCharge(order.getRetailPrice(), currentTaxRate, TaxType.INCLUDED.getValue()));
    shippingDetail.setPurchasingAmount(NumUtil.toLong(detail.getShippingDetailCommodityAmount()));
    BigDecimal discountAmount = shippingDetail.getUnitPrice().subtract(shippingDetail.getRetailPrice());
    // 予約・キャンペーン以外で割引があった場合は特別価格とする
    if (!BigDecimalUtil.equals(discountAmount, BigDecimal.ZERO)) {
      shippingDetail.setDiscountAmount(discountAmount);
      if (!detail.isReserve() && campaignCode == null) {
        shippingDetail.setDiscountPrice(shippingDetail.getRetailPrice());
      } else {
        shippingDetail.setDiscountPrice(null);
      }
    } else {
      shippingDetail.setDiscountAmount(BigDecimal.ZERO);
    }

    // 旧受注時に存在したギフトは旧受注時の情報を使用して明細情報を生成する
    if (StringUtil.isNullOrEmpty(detail.getGiftCode())) {
      shippingDetail.setGiftPrice(BigDecimal.ZERO);
      shippingDetail.setGiftTax(BigDecimal.ZERO);
      shippingDetail.setGiftTaxType(TaxType.NO_TAX.longValue());
    } else {
      GiftAttribute gift = getGiftAttribute(detail, detail.getGiftCode());
      if (gift != null) {
        shippingDetail.setGiftCode(gift.getValue());
        shippingDetail.setGiftName(gift.getGiftName());
        shippingDetail.setGiftPrice(gift.getGiftPrice());
        shippingDetail.setGiftTaxRate(gift.getGiftTaxRate());
        shippingDetail.setGiftTaxType(gift.getGiftTaxType());
        shippingDetail.setGiftTax(gift.getGiftTax());
      } else {
        shippingDetail.setGiftPrice(BigDecimal.ZERO);
        shippingDetail.setGiftTaxType(TaxType.NO_TAX.longValue());
        shippingDetail.setGiftTax(BigDecimal.ZERO);
      }
    }

    if (StringUtil.hasValue(detail.getCampaignName())) {
      shippingDetail.setCampaignCode(detail.getCampaignCode());// 促销活动编号
      shippingDetail.setCampaignName(detail.getCampaignName());// 促销活动名称
    }
    shippingDetail.setCommodityType(detail.getCommodityType());// 商品区分
    shippingDetail.setSetCommodityFlg(detail.getSetCommodityFlg());// 套餐商品区分
    if (StringUtil.hasValue(detail.getDiscountType())) {
      shippingDetail.setDiscountType(NumUtil.toLong(detail.getDiscountType()));// 折扣类型
    }
    shippingDetail.setDiscountValue(detail.getDiscountValue());// 折扣值
    return shippingDetail;
  }

  /**
   * ショップコード＋ShippingDetailBeanから出荷明細情報を1件生成する
   * 
   * @param shopCode
   * @param detail
   * @return 出荷明細情報
   */
  private List<ShippingDetailComposition> createShippingDetailComposition(ShippingDetailBean detail) {
    // 共通情報の取得
    TaxUtil taxUtil = DIContainer.get("TaxUtil");
    Long currentTaxRate = taxUtil.getTaxRate();

    List<ShippingDetailComposition> list = new ArrayList<ShippingDetailComposition>();
    for (SetCommpositionInfo composition : detail.getCompositionList()) {
      composition.getComposition().setRetailPrice(composition.getRetailPrice());
      list.add(composition.getComposition());
    }

    for (ShippingDetailComposition shippingComposition : list) {
      shippingComposition.setRetailTax(BigDecimal.ZERO);
      shippingComposition.setDiscountAmount(shippingComposition.getUnitPrice().subtract(shippingComposition.getRetailPrice()));
      shippingComposition.setCommodityTaxRate(currentTaxRate);
      // shippingComposition.setCommodityTaxType(TaxType.NO_TAX.longValue());
      shippingComposition.setCommodityTax(BigDecimal.ZERO);
    }

    return list;
  }

  /**
   * 受注明細情報生成<BR>
   * 
   * @param shippingDetailList
   * @return 受注明細のリスト
   */
  private List<OrderDetail> createOrderDetailList(OrderContainer container) {
    String orderNo = container.getOrderHeader().getOrderNo();
    List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    // 全出荷明細をショップ＋SKUごとにソートする。

    List<ShippingDetail> shippingDetailList = sortShippingDetailList(container);
    // ソートした出荷明細のリストでループする

    String oldShopCode = "";
    String oldSkuCode = "";
    for (ShippingDetail detail : shippingDetailList) {
      if (oldShopCode.equals(detail.getShopCode()) && oldSkuCode.equals(detail.getSkuCode())) {
        // 前のショップコード＋SKUと同じものであれば数量のみ追加

        addAmountToOrder(orderDetailList, detail.getShopCode(), detail.getSkuCode(), detail.getPurchasingAmount());
      } else {
        if (CommodityType.GIFT.longValue().equals(detail.getCommodityType())) {
          CommodityInfo skuInfo = catalogService.getSkuInfo(detail.getShopCode(), detail.getSkuCode());
          OrderDetail orderDetail = createOrderDetailCommodityInfo(skuInfo);
          orderDetail.setOrderNo(orderNo);
          orderDetail.setPurchasingAmount(detail.getPurchasingAmount());
          orderDetail.setSkuCode(detail.getSkuCode());
          orderDetail.setCommodityType(detail.getCommodityType());
          orderDetail.setSetCommodityFlg(detail.getSetCommodityFlg());
          orderDetail.setOrmRowid(-1L);
          orderDetailList.add(orderDetail);
        } else {
          ShippingDetailBean detailBean = getPrecentShippingDetailBean(detail.getShopCode(), detail.getSkuCode());

          // 前のショップコード＋SKUと違うものであればOrderDetailをNewする
          OrderDetail orderDetail = detailBean.getOrderDetailCommodityInfo();
          orderDetail.setOrderNo(orderNo);

          orderDetail.setPurchasingAmount(NumUtil.toLong(detailBean.getShippingDetailCommodityAmount()));
          orderDetail.setSkuCode(detailBean.getSkuCode());
          orderDetail.setCommodityType(detailBean.getCommodityType());
          orderDetail.setSetCommodityFlg(detailBean.getSetCommodityFlg());
          orderDetail.setOrmRowid(-1L);

          orderDetailList.add(orderDetail);
        }
      }
      oldShopCode = detail.getShopCode();
      oldSkuCode = detail.getSkuCode();
    }

    return orderDetailList;
  }

  private ShippingDetailBean getPrecentShippingDetailBean(String shopCode, String skuCode) {
    for (ShippingHeaderBean header : getBean().getShippingHeaderList()) {
      if (header.getShippingShopCode().equals(shopCode)) {
        for (ShippingDetailBean detail : header.getShippingDetailList()) {
          if (detail.getSkuCode().equals(skuCode)) {
            return detail;
          }
        }
      }
    }
    return null;
  }

  /**
   * 受注情報から出荷明細の一覧をショップコードとSKUコードの昇順でソートして返す。
   * 
   * @param container
   * @return 出荷明細のリスト
   */
  private List<ShippingDetail> sortShippingDetailList(OrderContainer container) {
    List<ShippingDetail> shippingDetailList = new ArrayList<ShippingDetail>();

    for (ShippingContainer shipping : container.getShippings()) {
      for (ShippingDetail detail : shipping.getShippingDetails()) {
        shippingDetailList.add(detail);
      }
    }

    ShippingDetail[] details = shippingDetailList.toArray(new ShippingDetail[shippingDetailList.size()]);
    Arrays.sort(details, new ShippingDetailComparator());
    shippingDetailList = new ArrayList<ShippingDetail>();
    for (ShippingDetail shipping : details) {
      shippingDetailList.add(shipping);
    }

    return shippingDetailList;
  }

  /**
   * 受注明細の一覧からショップコードとSKUコードが一致するものを選択し、購入商品数を加算します。
   * 
   * @param detailList
   * @param shopCode
   * @param skuCode
   * @param amount
   */
  private void addAmountToOrder(List<OrderDetail> detailList, String shopCode, String skuCode, Long amount) {
    for (OrderDetail detail : detailList) {
      if (detail.getShopCode().equals(shopCode) && detail.getSkuCode().equals(skuCode)) {
        detail.setPurchasingAmount(amount + detail.getPurchasingAmount());
      }
    }
  }

  public boolean isChangeAbleOrder() {
    // 予約期間が終わっている場合は受注修正不可
    if (getBean().isReserveFlg()) {
      CommodityInfo commodityInfo = null;
      CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
      OrderService service = ServiceLocator.getOrderService(getLoginInfo());
      List<ShippingContainer> shippingList = service.getShippingList(getBean().getOrderNo());

      for (ShippingContainer sc : shippingList) {
        for (ShippingDetail sd : sc.getShippingDetails()) {
          commodityInfo = catalogService.getSkuInfo(sd.getShopCode(), sd.getSkuCode());
        }
      }

      if (commodityInfo == null
          || !DateUtil.isPeriodDate(commodityInfo.getHeader().getReservationStartDatetime(), commodityInfo.getHeader()
              .getReservationEndDatetime())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_RESERVATION_CHANGE));
        return false;
      }
    }
    return true;

  }

  private static class ShippingDetailComparator implements Comparator<ShippingDetail>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 出荷明細のソート用に比較を行います。
     * 
     * @param o1
     * @param o2
     * @return 処理結果
     */
    public int compare(ShippingDetail o1, ShippingDetail o2) {
      if (o1.getShopCode().equals(o2.getShopCode())) {
        return o1.getSkuCode().compareTo(o2.getSkuCode());
      } else {
        return o1.getShopCode().compareTo(o2.getShopCode());
      }
    }
  }

  public void setAddressList(OrderModifyBean bean) {
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    bean.setAddressScript(s.createAddressScript());
    bean.getOrderHeaderEdit().getAddress().setAddressPrefectureList(s.createPrefectureList());
    bean.getOrderHeaderEdit().getAddress().setAddressCityList(
        s.createCityList(bean.getOrderHeaderEdit().getAddress().getPrefectureCode()));
    bean.getOrderHeaderEdit().getAddress().setAddressAreaList(
        s.createAreaList(bean.getOrderHeaderEdit().getAddress().getPrefectureCode(), bean.getOrderHeaderEdit().getAddress()
            .getCityCode()));
    for (ShippingHeaderBean shipping : bean.getShippingHeaderList()) {
      shipping.getAddress().setAddressPrefectureList(s.createPrefectureList());
      shipping.getAddress().setAddressCityList(s.createCityList(shipping.getAddress().getPrefectureCode()));
      shipping.getAddress().setAddressAreaList(
          s.createAreaList(shipping.getAddress().getPrefectureCode(), shipping.getAddress().getCityCode()));
    }
  }

  public void setDeliveryDatetimeInfo(OrderModifyBean bean) {
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    for (ShippingHeaderBean header : bean.getShippingHeaderList()) {
      if (StringUtil.isNull(getBean().getOrderPayment().getPaymentMethodCode())) {
        header.setDeliveryAppointedDateList(null);
        header.setDeliveryAppointedTimeList(null);
      } else {
        boolean codType = false;
        for (PaymentTypeBase paymentType : bean.getOrderPayment().getDisplayPaymentList()) {
          if (paymentType.isCashOnDelivery()
              && paymentType.getPaymentMethodCode().equals(bean.getOrderPayment().getPaymentMethodCode())) {
            codType = true;
            break;
          }
        }
        List<CartCommodityInfo> commodityList = new ArrayList<CartCommodityInfo>();
        // 获得总重量
        BigDecimal totalWeight = BigDecimal.ZERO;
        for (ShippingDetailBean detail : header.getShippingDetailList()) {
          CartCommodityInfo cartCommodityInfo = new CartCommodityInfo();
          cartCommodityInfo.setShopCode(detail.getOrderDetailCommodityInfo().getShopCode());
          cartCommodityInfo.setCommodityCode(detail.getOrderDetailCommodityInfo().getCommodityCode());
          commodityList.add(cartCommodityInfo);
          if (CommodityType.GENERALGOODS.longValue().equals(detail.getCommodityType())) {
            if (SetCommodityFlg.OBJECTIN.longValue().equals(detail.getSetCommodityFlg())) {
              BigDecimal setCommodityWeight = BigDecimal.ZERO;
              for (SetCommpositionInfo compositionInfo : detail.getCompositionList()) {
                setCommodityWeight = setCommodityWeight.add(compositionInfo.getComposition().getCommodityWeight());
              }
              totalWeight = totalWeight.add(setCommodityWeight.multiply(NumUtil.parse(detail.getShippingDetailCommodityAmount())));
            } else {
              totalWeight = totalWeight.add(detail.getWeigth().multiply(NumUtil.parse(detail.getShippingDetailCommodityAmount())));
            }
          }
        }
        totalWeight = totalWeight.add(this.getGiftCommodityWeight(bean));
        String dcCode = "";
        if (bean.getShippingHeaderList().get(0).getDeliveryCompanyNo() == null) {
          // 获得配送公司
          List<DeliveryCompany> dc = utilService.getDeliveryCompanys(header.getShippingShopCode(), header.getAddress()
              .getPrefectureCode(), header.getAddress().getCityCode(), header.getAddress().getAreaCode(), codType, totalWeight
              .toString());
          // 2013/04/21 优惠券对应 ob update end

          List<DeliveryCompanyBean> lsDcb = new ArrayList<DeliveryCompanyBean>();
          DeliveryCompanyBean cacheDCBean = new DeliveryCompanyBean();
          cacheDCBean.setDeliveryCompanyNo("D000");
          for (DeliveryCompany deliveryCompany : dc) {
            if (!cacheDCBean.getDeliveryCompanyNo().equals(deliveryCompany.getDeliveryCompanyNo())) {
              DeliveryCompanyBean dcb = new DeliveryCompanyBean();
              dcb.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
              dcb.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());

              lsDcb.add(dcb);
            }
            cacheDCBean.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
          }

          if (lsDcb.size() == 1) {
            dcCode = dc.get(0).getDeliveryCompanyNo();
          }
        } else {
          dcCode = bean.getShippingHeaderList().get(0).getDeliveryCompanyNo();
        }
        List<CodeAttribute> deliveryDateList = utilService.getDeliveryDateList(header.getShippingShopCode(), header.getAddress()
            .getPrefectureCode(), codType, commodityList, totalWeight.toString(), dcCode);
        header.setDeliveryAppointedDateList(deliveryDateList);
        if (deliveryDateList.size() > 0) {
          String deliveryAppointedDate = header.getDeliveryAppointedDate();
          boolean flg = false;
          for (CodeAttribute info : deliveryDateList) {
            if (info.getValue().equals(deliveryAppointedDate)) {
              flg = true;
            }
          }
          if (!flg || StringUtil.isNullOrEmpty(deliveryAppointedDate)) {
            deliveryAppointedDate = deliveryDateList.get(0).getValue();
          }
          List<CodeAttribute> deliveryTimeZoneList = utilService.getDeliveryTimeList(header.getShippingShopCode(), header
              .getAddress().getPrefectureCode(), codType, deliveryAppointedDate, totalWeight.toString(), dcCode);
          header.setDeliveryAppointedTimeList(deliveryTimeZoneList);
        }
      }

    }
  }

  // 初始化优惠信息
  public void initDiscount(OrderHeader header, CashierDiscount discount) {
    if (header.getDiscountType() != null) {
      discount.setDiscountPrice(header.getDiscountPrice());
      discount.setDiscountName(header.getDiscountName());
      discount.setDiscountCode(header.getDiscountCode());
      discount.setDiscountDetailCode(header.getDiscountDetailCode());
      discount.setDiscountRate(header.getDiscountRate());
      discount.setDiscountMode(header.getDiscountMode().toString());
      if (CouponType.CUSTOMER_GROUP.longValue().equals(header.getDiscountType())) {
        discount.setDiscountType(DiscountType.CUSTOMER.getValue());
      } else {
        discount.setDiscountType(DiscountType.COUPON.getValue());
      }
      discount.setCouponType(header.getDiscountType().toString());
    } else {
      discount.setDiscountPrice(header.getDiscountPrice());
    }
  }

  // 优惠种别取得
  public List<CodeAttribute> createDiscountTypeList() {
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerInfo customerInfo = customerService.getCustomer(getBean().getOrderHeaderEdit().getCustomerCode());
    if (customerInfo == null || customerInfo.getCustomer() == null) {
      return new ArrayList<CodeAttribute>();
    }
    List<CodeAttribute> discountTypeList = new ArrayList<CodeAttribute>();
    CustomerGroupCampaign customerGroupCampaign = getCustomerGroupCampaign();
    if (customerGroupCampaign != null) {
      discountTypeList.add(new NameValue(DiscountType.CUSTOMER.getName(), DiscountType.CUSTOMER.getValue()));
    }
    discountTypeList.add(new NameValue(DiscountType.COUPON.getName(), DiscountType.COUPON.getValue()));
    return discountTypeList;
  }

  // 顾客种别优惠获得
  public CustomerGroupCampaign getCustomerGroupCampaign() {
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    String shopCode = getBean().getShopCode();
    CustomerInfo customerInfo = customerService.getCustomer(getBean().getOrderHeaderEdit().getCustomerCode());
    if (customerInfo == null || customerInfo.getCustomer() == null) {
      return null;
    }
    CustomerGroupCampaign customerGroupCampaign = customerService.getCustomerGroupCampaign(shopCode, customerInfo.getCustomer()
        .getCustomerGroupCode());
    if (customerGroupCampaign != null) {
      if (BigDecimalUtil.isBelow(getBean().getAfterTotalPrice().getCommodityPrice(), customerGroupCampaign.getMinOrderAmount())) {
        customerGroupCampaign = null;
      }
    }
    return customerGroupCampaign;
  }

  // 个人优惠券信息获得
  public List<CodeAttribute> createPersonalCouponList() {
    List<CodeAttribute> personalCouponList = new ArrayList<CodeAttribute>();
    personalCouponList.add(new NameValue("请选择", ""));
    // 2013/04/18 优惠券对应 ob update start
    String shopCode = getBean().getShopCode();
    List<MyCouponInfo> newCouponHistoryList = getAviableCouponList(shopCode);

    for (MyCouponInfo newCouponHistory : newCouponHistoryList) {
      personalCouponList.add(new NameValue(newCouponHistory.getCouponIssueNo(), newCouponHistory.getCouponIssueNo()));
    }
    // 2013/04/18 优惠券对应 ob update end

    return personalCouponList;
  }

  // 2013/04/18 优惠券对应 ob add start
  /**
   * 可以使用的优惠券一览取得
   * 
   * @param shopCode
   * @return 可以使用的优惠券一览
   */
  public List<MyCouponInfo> getAviableCouponList(String shopCode) {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());

    List<MyCouponInfo> newCouponHistoryList = service.getMyCoupon(getBean().getOrderHeaderEdit().getCustomerCode());
    List<MyCouponInfo> result = new ArrayList<MyCouponInfo>();

    List<String> objectCommodity = new ArrayList<String>();

    for (MyCouponInfo newCouponHistory : newCouponHistoryList) {

      BigDecimal objectTotalPrice = BigDecimal.ZERO;
      BigDecimal objectTotalPriceTemp = BigDecimal.ZERO;

      objectCommodity = getObjectCommodity(shopCode, newCouponHistory.getCouponIssueNo());

      // 指定商品的购买金额取得
      if (objectCommodity.size() > 0) {
        for (String commodityCode : objectCommodity) {
          for (ShippingHeaderBean shipping : getBean().getShippingHeaderList()) {
            for (ShippingDetailBean detail : shipping.getShippingDetailList()) {
              OrderDetail orderDetailCommodityInfo = detail.getOrderDetailCommodityInfo();
              if (commodityCode.equals(orderDetailCommodityInfo.getCommodityCode())) {
                objectTotalPrice = BigDecimalUtil.add(objectTotalPrice, BigDecimalUtil.multiply(orderDetailCommodityInfo
                    .getRetailPrice(), orderDetailCommodityInfo.getPurchasingAmount()));
              }
            }
          }
        }
      } else {
        continue;
      }
      objectTotalPriceTemp = objectTotalPrice;

      // 优惠券利用最小购买金额
      if (BigDecimalUtil.isAboveOrEquals(objectTotalPrice, newCouponHistory.getMinUseOrderAmount())) {
        newCouponHistory.setObjectTotalPrice(objectTotalPriceTemp);
        result.add(newCouponHistory);
      }
    }

    return result;
  }

  /**
   * 购物车中满足优惠券使用规则（商品编号或品牌编号或商品区分品）的商品一览设定
   * 
   * @param shopCode
   * @param newCouponHistory
   *          优惠券发行履历信息
   * @param objectCommodity
   *          购物车中满足优惠券使用规则的商品一览
   */
  private List<String> getObjectCommodity(String shopCode, String couponIssueNo) {
    List<CommodityHeader> commodityListOfCart = new ArrayList<CommodityHeader>();
    CatalogService cs = ServiceLocator.getCatalogService(getLoginInfo());
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    List<String> commodityCodeOfCart = new ArrayList<String>();
    List<String> objectCommodity = new ArrayList<String>();

    for (ShippingHeaderBean shipping : getBean().getShippingHeaderList()) {
      for (ShippingDetailBean detail : shipping.getShippingDetailList()) {
        OrderDetail orderDetailCommodityInfo = detail.getOrderDetailCommodityInfo();
        commodityListOfCart.add(cs.getCommodityHeader(shopCode, orderDetailCommodityInfo.getCommodityCode()));
      }
    }

    List<NewCouponHistoryUseInfo> useCommodityList = service.getUseCommodityList(couponIssueNo);
    List<NewCouponHistoryUseInfo> brandList = service.getBrandCodeList(couponIssueNo);
    List<NewCouponHistoryUseInfo> importCommodityTypeList = service.getImportCommodityTypeList(couponIssueNo);
    // 20131010 txw add start
    List<NewCouponHistoryUseInfo> categoryList = service.getCategoryCodeList(couponIssueNo);
    // 20131010 txw add end

    List<String> useCommodityCodeList = new ArrayList<String>();
    for (NewCouponHistoryUseInfo useCommodity : useCommodityList) {
      useCommodityCodeList.add(useCommodity.getCommodityCode());
    }

    List<String> brandCodeList = new ArrayList<String>();
    for (NewCouponHistoryUseInfo brand : brandList) {
      brandCodeList.add(brand.getBrandCode());
    }

    List<Long> importCommodityTypeCodeList = new ArrayList<Long>();
    for (NewCouponHistoryUseInfo importCommodityType : importCommodityTypeList) {
      importCommodityTypeCodeList.add(importCommodityType.getImportCommodityType());
    }

    List<String> caregoryCodeList = new ArrayList<String>();
    for (NewCouponHistoryUseInfo category : categoryList) {
      caregoryCodeList.add(category.getCategoryCode());
    }

    for (CommodityHeader ch : commodityListOfCart) {

      // 使用关联信息(商品编号)
      if (useCommodityCodeList != null && useCommodityCodeList.size() > 0 && useCommodityCodeList.contains(ch.getCommodityCode())) {
        if (!objectCommodity.contains(ch.getCommodityCode())) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }

      // 使用关联信息(品牌编号)
      if (brandCodeList != null && brandCodeList.size() > 0 && brandCodeList.contains(ch.getBrandCode())) {
        if (!objectCommodity.contains(ch.getCommodityCode())) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }

      // 使用关联信息(商品区分品)
      if (importCommodityTypeCodeList != null && importCommodityTypeCodeList.size() > 0
          && importCommodityTypeCodeList.contains(ch.getImportCommodityType())) {
        if (!objectCommodity.contains(ch.getCommodityCode())) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }

      // 使用关联信息(分类编号)
      if (caregoryCodeList != null && caregoryCodeList.size() > 0) {
        String categoryPath = ch.getCategoryPath();
        categoryPath = categoryPath.replaceAll("/", "");
        categoryPath = categoryPath.replaceAll("#", "");
        String[] categorys = categoryPath.split("~");
        boolean existCategory = false;
        if (StringUtil.hasValue(categoryPath)) {
          existCategory = false;
        } else {
          existCategory = true;
        }
        for (String categoryCode : categorys) {
          if (caregoryCodeList.contains(categoryCode)) {
            existCategory = true;
            break;
          }
        }
        if (!objectCommodity.contains(ch.getCommodityCode()) && existCategory) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }

      commodityCodeOfCart.add(ch.getCommodityCode());
    }

    if (useCommodityCodeList.size() < 1 && brandCodeList.size() < 1 && importCommodityTypeCodeList.size() < 1) {
      objectCommodity = commodityCodeOfCart;
    }
    return objectCommodity;
  }

  // 2013/04/18 优惠券对应 ob add end
  public NewCouponHistory getPersonalCoupon(String customerCode, String personalCouponCode, BigDecimal totalCommodityPrice) {
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    NewCouponHistory newCouponHistory = customerService.getUnusedPersonalCoupon(customerCode, personalCouponCode);
    if (newCouponHistory != null) {
      if (BigDecimalUtil.isBelow(totalCommodityPrice, newCouponHistory.getMinUseOrderAmount())) {
        newCouponHistory = null;
      }
    }

    return newCouponHistory;
  }

  public NewCouponRule getPublicCoupon(String publicCouponCode, BigDecimal totalCommodityPrice) {
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    NewCouponRule newCouponRule = customerService.getPublicCoupon(publicCouponCode);
    if (newCouponRule != null) {
      if (BigDecimalUtil.isBelow(totalCommodityPrice, newCouponRule.getMinUseOrderAmount())) {
        newCouponRule = null;
      }
    }

    return newCouponRule;
  }

  private void setupOrderInvoiceInfo(OrderInvoice orderInvoiceInfo, CustomerVatInvoice customerVatInvoice) {
    orderInvoiceInfo.setCommodityName(getBean().getOrderInvoice().getInvoiceCommodityName());// 商品规格
    orderInvoiceInfo.setInvoiceType(NumUtil.toLong(getBean().getOrderInvoice().getInvoiceType()));// 发票类型
    orderInvoiceInfo.setCustomerName(getBean().getOrderInvoice().getInvoiceCustomerName());// 顾客姓名
    orderInvoiceInfo.setCompanyName(getBean().getOrderInvoice().getInvoiceCompanyName());// 公司名称
    orderInvoiceInfo.setTaxpayerCode(getBean().getOrderInvoice().getInvoiceTaxpayerCode());// 纳税人识别号
    orderInvoiceInfo.setAddress(getBean().getOrderInvoice().getInvoiceAddress());// 地址
    orderInvoiceInfo.setTel(getBean().getOrderInvoice().getInvoiceTel());// 电话
    orderInvoiceInfo.setBankName(getBean().getOrderInvoice().getInvoiceBankName());// 银行名称
    orderInvoiceInfo.setBankNo(getBean().getOrderInvoice().getInvoiceBankNo());// 银行支行编号
    orderInvoiceInfo.setInvoiceFlg(getBean().getOrderInvoice().getInvoiceFlg());// 是否领取发票
    if (InvoiceSaveFlg.SAVE.getValue().equals(getBean().getOrderInvoice().getInvoiceSaveFlg())) {
      customerVatInvoice.setCompanyName(getBean().getOrderInvoice().getInvoiceCompanyName());// 公司名称
      customerVatInvoice.setTaxpayerCode(getBean().getOrderInvoice().getInvoiceTaxpayerCode());// 纳税人识别号
      customerVatInvoice.setAddress(getBean().getOrderInvoice().getInvoiceAddress());// 地址
      customerVatInvoice.setTel(getBean().getOrderInvoice().getInvoiceTel());// 电话
      customerVatInvoice.setBankName(getBean().getOrderInvoice().getInvoiceBankName());// 银行名称
      customerVatInvoice.setBankNo(getBean().getOrderInvoice().getInvoiceBankNo());// 银行支行编号
      customerVatInvoice.setCustomerCode(getBean().getOrderHeaderEdit().getCustomerCode());
    } else {
      customerVatInvoice = null;
    }
  }


  public void createDeliveryToBean(OrderModifyBean bean) {
    UtilService utilService = ServiceLocator.getUtilService(ServiceLoginInfo.getInstance());
    String shopCode = bean.getShopCode();
    String prefectureCode = bean.getShippingHeaderList().get(0).getAddress().getPrefectureCode();
    // 2013/04/21 优惠券对应 ob add start
    String cityCode = bean.getShippingHeaderList().get(0).getAddress().getCityCode();
    String areaCode = bean.getShippingHeaderList().get(0).getAddress().getAreaCode();
    // 2013/04/21 优惠券对应 ob add end
    // 获得支付类型
    String paymentMethodType = "";
    for (PaymentTypeBase ptb : bean.getOrderPayment().getDisplayPaymentList()) {
      if (ptb.getPaymentMethodCode().equals(bean.getOrderPayment().getPaymentMethodCode())) {
        paymentMethodType = ptb.getPaymentMethodType();
        break;
      }
    }
    // 是否货到付款
    Boolean codFlg = paymentMethodType.equals(PaymentMethodType.CASH_ON_DELIVERY.getValue()) ? true : false;
    // 订单重量计算
    BigDecimal subTotalWeight = BigDecimal.ZERO;
    for (ShippingDetailBean commodity : bean.getShippingHeaderList().get(0).getShippingDetailList()) {
      if (NumUtil.isNum(commodity.getShippingDetailCommodityAmount())) {
        if (CommodityType.GENERALGOODS.longValue().equals(commodity.getCommodityType())) {
          if (SetCommodityFlg.OBJECTIN.longValue().equals(commodity.getSetCommodityFlg())) {
            for (SetCommpositionInfo commoposition : commodity.getCompositionList()) {
              subTotalWeight = subTotalWeight.add(commoposition.getComposition().getCommodityWeight().multiply(
                  NumUtil.parse(commodity.getShippingDetailCommodityAmount())));
            }
          } else {
            subTotalWeight = subTotalWeight.add(commodity.getOrderDetailCommodityInfo().getCommodityWeight().multiply(
                NumUtil.parse(commodity.getShippingDetailCommodityAmount())));
          }
        }
      }
    }
    subTotalWeight = subTotalWeight.add(this.getGiftCommodityWeight(bean));
    // 获得配送公司
    // 2013/04/21 优惠券对应 ob update start
    List<DeliveryCompany> dc = utilService.getDeliveryCompanys(shopCode, prefectureCode, cityCode, areaCode, codFlg, subTotalWeight
        .toString());
    // 2013/04/21 优惠券对应 ob update end

    // 封装List<DeliveryCompanyBean>
    List<DeliveryCompanyBean> lsDcb = new ArrayList<DeliveryCompanyBean>();
    DeliveryCompanyBean cacheDCBean = new DeliveryCompanyBean();
    cacheDCBean.setDeliveryCompanyNo("D000");
    for (DeliveryCompany deliveryCompany : dc) {
      if (!cacheDCBean.getDeliveryCompanyNo().equals(deliveryCompany.getDeliveryCompanyNo())) {
        DeliveryCompanyBean dcb = new DeliveryCompanyBean();
        dcb.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
        dcb.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());
        lsDcb.add(dcb);
      }
      cacheDCBean.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
    }
    // 设置Bean可以指定的配送公司列表
    bean.setDeliveryCompanyList(lsDcb);
  }

  // 2012/11/22 促销活动 ob add start
  public void recomputeGift(OrderModifyBean bean, boolean checkStock) {
    // 普通商品的赠品优惠活动的再计算
    this.recomputeCommodityInfo(bean);
    // 购物车中的原多重优惠活动赠品的再计算
    this.recomputeCartGfitInfo(bean);
    // 可领取商品再计算
    this.recomputeFreeGiftInfo(bean);
    // 赠品的在库验证
    if (checkStock) {
      this.checkGiftStock(bean);
    }
    // 验证折扣券是否可用
    checkCampaignEnable(bean);
  }

  public void checkCampaignEnable(OrderModifyBean bean) {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
      for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
        detailBean.setDisplayCampaignFlg(false);
        if (CommodityType.GENERALGOODS.longValue().equals(detailBean.getCommodityType())
            && !SetCommodityFlg.OBJECTIN.longValue().equals(detailBean.getSetCommodityFlg())) {
          detailBean.setDisplayCampaignFlg(service.getEnableCampaignFlg(
              detailBean.getOrderDetailCommodityInfo().getCommodityCode(), bean.getOldOrderContainer().getOrderHeader()
                  .getOrderDatetime()));
        }
      }
    }
  }

  private BigDecimal getGiftCommodityWeight(OrderModifyBean bean) {
    BigDecimal totalCommodityWeight = BigDecimal.ZERO;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    List<String> giftList = new ArrayList<String>();
    Map<String, Long> giftMap = new HashMap<String, Long>();
    Map<String, BigDecimal> weightMap = new HashMap<String, BigDecimal>();
    // 所有赠品SKU的数量合计
    for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
      for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
        if (CommodityType.GIFT.longValue().equals(detailBean.getCommodityType())) {
          // 多重优惠活动赠品
          if (giftList.contains(detailBean.getSkuCode())) {
            giftMap.put(detailBean.getSkuCode(), giftMap.get(detailBean.getSkuCode())
                + NumUtil.toLong(detailBean.getShippingDetailCommodityAmount()));
          } else {
            giftList.add(detailBean.getSkuCode());
            giftMap.put(detailBean.getSkuCode(), NumUtil.toLong(detailBean.getShippingDetailCommodityAmount()));
            weightMap.put(detailBean.getSkuCode(), detailBean.getWeigth());
          }
        } else {
          // 赠品促销活动的赠品
          for (GiftInfo giftInfo : detailBean.getGiftCommodityList()) {
            if (giftList.contains(giftInfo.getShippingDetail().getSkuCode())) {
              giftMap.put(giftInfo.getShippingDetail().getSkuCode(), giftMap.get(giftInfo.getShippingDetail().getSkuCode())
                  + giftInfo.getShippingDetail().getPurchasingAmount());
            } else {
              giftList.add(giftInfo.getShippingDetail().getSkuCode());
              giftMap.put(giftInfo.getShippingDetail().getSkuCode(), giftInfo.getShippingDetail().getPurchasingAmount());
              weightMap.put(giftInfo.getShippingDetail().getSkuCode(), giftInfo.getWeight());
            }
          }
        }
      }
    }
    // 在库验证
    for (String skuCode : giftList) {
      Long stockQuantity = catalogService.getAvailableStock(bean.getShopCode(), skuCode);
      if (stockQuantity == -1L) {
        stockQuantity = 99999999L;
      } else {
        // 原引当数加算
        if (bean.getOrgStockMap().get(skuCode) != null) {
          stockQuantity = stockQuantity + bean.getOrgStockMap().get(skuCode);
        }
      }
      Long giftStock = giftMap.get(skuCode);
      if (stockQuantity < giftStock) {
        totalCommodityWeight = totalCommodityWeight.add((new BigDecimal(stockQuantity).multiply(weightMap.get(skuCode))));
      } else {
        totalCommodityWeight = totalCommodityWeight.add((new BigDecimal(giftStock).multiply(weightMap.get(skuCode))));
      }

    }
    return totalCommodityWeight;
  }

  // 赠品的在库验证
  private void checkGiftStock(OrderModifyBean bean) {
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    List<String> giftList = new ArrayList<String>();
    Map<String, Long> giftMap = new HashMap<String, Long>();
    // 所有赠品SKU的数量合计
    for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
      for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
        if (CommodityType.GIFT.longValue().equals(detailBean.getCommodityType())) {
          // 多重优惠活动赠品
          if (giftList.contains(detailBean.getSkuCode())) {
            giftMap.put(detailBean.getSkuCode(), giftMap.get(detailBean.getSkuCode())
                + NumUtil.toLong(detailBean.getShippingDetailCommodityAmount()));
          } else {
            giftList.add(detailBean.getSkuCode());
            giftMap.put(detailBean.getSkuCode(), NumUtil.toLong(detailBean.getShippingDetailCommodityAmount()));
          }
        } else {
          // 赠品促销活动的赠品
          for (GiftInfo giftInfo : detailBean.getGiftCommodityList()) {
            if (giftList.contains(giftInfo.getShippingDetail().getSkuCode())) {
              giftMap.put(giftInfo.getShippingDetail().getSkuCode(), giftMap.get(giftInfo.getShippingDetail().getSkuCode())
                  + giftInfo.getShippingDetail().getPurchasingAmount());
            } else {
              giftList.add(giftInfo.getShippingDetail().getSkuCode());
              giftMap.put(giftInfo.getShippingDetail().getSkuCode(), giftInfo.getShippingDetail().getPurchasingAmount());
            }
          }
        }
      }
    }
    // 在库验证
    for (String skuCode : giftList) {
      Long stockQuantity = catalogService.getAvailableStock(bean.getShopCode(), skuCode);
      CommodityInfo commodityInfo = catalogService.getSkuInfo(bean.getShopCode(), skuCode);
      if (stockQuantity == -1L) {
        continue;
      }
      // 原引当数加算
      if (getBean().getOrgStockMap().get(skuCode) != null) {
        stockQuantity = stockQuantity + getBean().getOrgStockMap().get(skuCode);
      }
      Long giftStock = giftMap.get(skuCode);
      if (stockQuantity == 0L) {
        this.addWarningMessage("赠品（" + commodityInfo.getHeader().getCommodityName() + "）已无库存，无法赠送。");
        for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
          for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
            if (detailBean.getSkuCode().equals(skuCode)) {
              detailBean.setShippingDetailCommodityAmount("0");
            } else if (CommodityType.GENERALGOODS.longValue().equals(detailBean.getCommodityType())) {
              for (GiftInfo giftInfo : detailBean.getGiftCommodityList()) {
                if (giftInfo.getShippingDetail().getSkuCode().equals(skuCode)) {
                  giftInfo.getShippingDetail().setPurchasingAmount(0L);
                }
              }
            }
          }
        }
      } else if (stockQuantity < giftStock) {
        this.addWarningMessage("赠品（" + commodityInfo.getHeader().getCommodityName() + "）应赠送" + giftStock.toString()
            + "个，因库存不足，实际赠送" + stockQuantity.toString() + "个。");
        for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
          for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
            if (detailBean.getSkuCode().equals(skuCode)) {
              if (stockQuantity <= 0L) {
                detailBean.setShippingDetailCommodityAmount("0");
              } else if (stockQuantity < NumUtil.parseLong(detailBean.getShippingDetailCommodityAmount())) {
                detailBean.setShippingDetailCommodityAmount(stockQuantity.toString());
                stockQuantity = 0L;
              } else {
                stockQuantity = stockQuantity - NumUtil.parseLong(detailBean.getShippingDetailCommodityAmount());
              }

            } else if (CommodityType.GENERALGOODS.longValue().equals(detailBean.getCommodityType())) {
              for (GiftInfo giftInfo : detailBean.getGiftCommodityList()) {
                if (giftInfo.getShippingDetail().getSkuCode().equals(skuCode)) {
                  if (stockQuantity <= 0L) {
                    giftInfo.getShippingDetail().setPurchasingAmount(0L);
                  } else if (stockQuantity < giftInfo.getShippingDetail().getPurchasingAmount()) {
                    giftInfo.getShippingDetail().setPurchasingAmount(stockQuantity);
                    stockQuantity = 0L;
                  } else {
                    stockQuantity = stockQuantity - giftInfo.getShippingDetail().getPurchasingAmount();
                  }
                }
              }
            }
          }
        }
      }

    }
    for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
      List<ShippingDetailBean> removeList = new ArrayList<ShippingDetailBean>();
      for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
        if (CommodityType.GIFT.longValue().equals(detailBean.getCommodityType())) {
          if (detailBean.getShippingDetailCommodityAmount().equals("0")) {
            removeList.add(detailBean);
          }

        } else if (CommodityType.GENERALGOODS.longValue().equals(detailBean.getCommodityType())) {
          List<GiftInfo> removeGiftList = new ArrayList<GiftInfo>();
          for (GiftInfo giftInfo : detailBean.getGiftCommodityList()) {
            if (giftInfo.getShippingDetail().getPurchasingAmount().equals(0L)) {
              removeGiftList.add(giftInfo);
            }
          }
          detailBean.getGiftCommodityList().removeAll(removeGiftList);
        }
      }
      headerBean.getShippingDetailList().removeAll(removeList);
    }
  }

  // 可领取商品再计算
  private void recomputeFreeGiftInfo(OrderModifyBean bean) {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    BigDecimal totalAmount = getCommodityTotalAmount(bean);
    List<String> shippingCommodityList = getCommodityList(bean);
    // 所有使用的多重促销活动的商品初期化
    bean.getGiftList().clear();
    // 所有适用的多重优惠活动取得
    List<CampaignInfo> campaignList = service.getMultipleGiftCampaignInfo(bean.getOldOrderContainer().getOrderHeader()
        .getOrderDatetime(), totalAmount, bean.getAdvertCode());
    for (CampaignInfo campaignInfo : campaignList) {
      // 促销条件获得
      CampaignCondition condition = campaignInfo.getConditionList().get(0);
      List<String> conditionCommodityList = Arrays.asList(condition.getAttributrValue().trim().split(","));
      if (StringUtil.hasValue(condition.getAttributrValue().trim())) {
        // 关联商品验证
        // 仅有
        if (CampaignConditionFlg.CAMPAIGN_CONDITION_ONLY.longValue().equals(condition.getCampaignConditionFlg())) {
          if (!conditionCommodityList.containsAll(shippingCommodityList)) {
            continue;
          }
        } else {
          // 包含
          boolean isExited = false;
          for (String commodityCode : shippingCommodityList) {
            if (conditionCommodityList.contains(commodityCode)) {
              isExited = true;
              break;
            }
          }
          if (!isExited) {
            continue;
          }
        }
      }
      // 促销活动满足条件时
      CampaignDoings campaignDoing = campaignInfo.getCampaignDoings();
      if (StringUtil.hasValue(campaignDoing.getAttributrValue().trim())) {
        List<String> giftCommodityList = Arrays.asList(campaignDoing.getAttributrValue().trim().split(","));
        // 可领取商品的list做成
        for (String commodityCode : giftCommodityList) {
          CommodityInfo commodityInfo = catalogService.getCommodityInfo(bean.getShopCode(), commodityCode);
          if (checkGiftStock(bean, commodityInfo.getHeader().getRepresentSkuCode(), campaignInfo.getCampaignMain()
              .getCampaignCode())) {
            ShippingDetailBean detailBean = createShippingDetailBean(bean.getShopCode(), commodityInfo.getHeader()
                .getRepresentSkuCode(), null, 1L, 0L);
            detailBean.setCampaignCode(campaignInfo.getCampaignMain().getCampaignCode());
            detailBean.setCampaignName(campaignInfo.getCampaignMain().getCampaignName());
            bean.getGiftList().add(detailBean);
          }
        }
      }
    }

  }

  // 购物车中的原多重优惠活动赠品的再计算
  private void recomputeCartGfitInfo(OrderModifyBean bean) {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    BigDecimal totalAmount = getCommodityTotalAmount(bean);
    List<String> shippingCommodityList = getCommodityList(bean);
    List<ShippingDetailBean> removeList = new ArrayList<ShippingDetailBean>();
    List<ShippingDetailBean> giftList = new ArrayList<ShippingDetailBean>();
    // 赠品促销活动的最新化
    for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
      // 订单中多重促销活动是否符合条件再次验证
      for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
        if (CommodityType.GIFT.longValue().equals(detailBean.getCommodityType())) {
          giftList.add(detailBean);
          CampaignInfo campaignInfo = service.getCampaignInfo(detailBean.getCampaignCode());
          // 促销活动不存在或者是赠品促销活动时
          if (campaignInfo == null || campaignInfo.getCampaignMain() == null
              || CampaignMainType.GIFT.longValue().equals(campaignInfo.getCampaignMain().getCampaignType())) {
            removeList.add(detailBean);
            continue;
          }
          // 商品信息不存在时
          CommodityInfo commodityInfo = catalogService.getSkuInfo(bean.getShopCode(), detailBean.getSkuCode());
          if (commodityInfo == null || commodityInfo.getHeader() == null) {
            removeList.add(detailBean);
            continue;
          }
          // 促销条件获得
          CampaignCondition condition = campaignInfo.getConditionList().get(0);
          if (StringUtil.hasValueAllOf(bean.getAdvertCode(), condition.getAdvertCode())) {
            if (!bean.getAdvertCode().equals(condition.getAdvertCode())) {
              removeList.add(detailBean);
              continue;
            }
          } else if (StringUtil.hasValue(condition.getAdvertCode())) {
            removeList.add(detailBean);
            continue;
          }
          // 促销条件获得
          if (condition.getOrderAmount() != null && condition.getOrderAmount().compareTo(totalAmount) > 0) {
            removeList.add(detailBean);
            continue;
          }
          if (!DateUtil.isPeriodDate(campaignInfo.getCampaignMain().getCampaignStartDate(), campaignInfo.getCampaignMain()
              .getCampaignEndDate(), bean.getOldOrderContainer().getOrderHeader().getOrderDatetime())) {
            removeList.add(detailBean);
            continue;
          }
          if (StringUtil.hasValue(condition.getAttributrValue().trim())) {
            List<String> conditionCommodityList = Arrays.asList(condition.getAttributrValue().trim().split(","));
            if (CampaignConditionFlg.CAMPAIGN_CONDITION_ONLY.longValue().equals(condition.getCampaignConditionFlg())) {
              if (!conditionCommodityList.containsAll(shippingCommodityList)) {
                removeList.add(detailBean);
                continue;
              }
            } else {
              boolean isExited = false;
              for (String commodityCode : shippingCommodityList) {
                if (conditionCommodityList.contains(commodityCode)) {
                  isExited = true;
                  break;
                }
              }
              if (!isExited) {
                removeList.add(detailBean);
                continue;
              }
            }
          }
        }
      }
      // 删除不再满足多重优惠条件的赠品
      headerBean.getShippingDetailList().removeAll(giftList);
      giftList.removeAll(removeList);
      headerBean.getShippingDetailList().addAll(giftList);
    }
  }

  // 普通商品的赠品促销活动的再计算
  private void recomputeCommodityInfo(OrderModifyBean bean) {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    // 赠品促销活动的最新化
    for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
      // 普通商品赠品信息的获得
      for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
        // 普通商品时设定对应的赠品信息
        if (detailBean.getCommodityType() == null || CommodityType.GENERALGOODS.longValue().equals(detailBean.getCommodityType())) {
          // 赠品信息List初期化
          detailBean.getGiftCommodityList().clear();
          // 活动期间内的促销活动信息取得
          List<CampaignInfo> campaignList = service.getCampaignGiftInfoList(bean.getOldOrderContainer().getOrderHeader()
              .getOrderDatetime(), detailBean.getOrderDetailCommodityInfo().getCommodityCode());
          if (campaignList != null && campaignList.size() > 0) {
            for (CampaignInfo campaignInfo : campaignList) {
              // 活动的赠品信息获得
              if (campaignInfo.getCampaignDoings() != null
                  && StringUtil.hasValue(campaignInfo.getCampaignDoings().getAttributrValue().trim())) {
                List<String> commodityList = Arrays.asList(campaignInfo.getCampaignDoings().getAttributrValue().trim().split(","));
                for (String commodityCode : commodityList) {
                  CommodityInfo commodityInfo = catalogService.getCommodityInfo(bean.getShopCode(), commodityCode);
                  if (commodityInfo != null && commodityInfo.getHeader() != null) {
                    // 赠品信息做成
                    ShippingDetail detail = new ShippingDetail();
                    detail.setShopCode(bean.getShopCode());
                    detail.setSkuCode(commodityInfo.getHeader().getRepresentSkuCode());
                    detail.setUnitPrice(commodityInfo.getDetail().getUnitPrice());
                    detail.setDiscountAmount(BigDecimal.ZERO);
                    detail.setRetailPrice(commodityInfo.getDetail().getUnitPrice());
                    detail.setRetailTax(BigDecimal.ZERO);
                    detail.setGiftCode(null);
                    detail.setGiftName(null);
                    detail.setGiftPrice(BigDecimal.ZERO);
                    detail.setGiftTax(BigDecimal.ZERO);
                    detail.setGiftTaxType(TaxType.NO_TAX.longValue());
                    detail.setCommodityType(commodityInfo.getHeader().getCommodityType());
                    detail.setSetCommodityFlg(commodityInfo.getHeader().getSetCommodityFlg());
                    detail.setCampaignCode(campaignInfo.getCampaignMain().getCampaignCode());
                    detail.setCampaignName(campaignInfo.getCampaignMain().getCampaignName());
                    if (NumUtil.isNum(detailBean.getShippingDetailCommodityAmount())) {
                      detail.setPurchasingAmount(NumUtil.toLong(detailBean.getShippingDetailCommodityAmount()));
                    }
                    GiftInfo giftInfo = new GiftInfo();
                    giftInfo.setShippingDetail(detail);
                    giftInfo.setCommodityName(commodityInfo.getHeader().getCommodityName());
                    giftInfo.setStandardName1(commodityInfo.getDetail().getStandardDetail1Name());
                    giftInfo.setStandardName2(commodityInfo.getDetail().getStandardDetail2Name());
                    giftInfo.setWeight(commodityInfo.getDetail().getWeight());
                    detailBean.getGiftCommodityList().add(giftInfo);
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  // 购买商品合计金额的取得（使用折扣券前的金额)
  private BigDecimal getCommodityTotalAmount(OrderModifyBean bean) {
    BigDecimal totalAmount = BigDecimal.ZERO;
    for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
      for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
        if (detailBean.getCommodityType() == null || CommodityType.GENERALGOODS.longValue().equals(detailBean.getCommodityType())) {
          if (NumUtil.isNum(detailBean.getShippingDetailCommodityAmount())) {
            // 商品的合计金额取得（使用优惠券前）
            if (detailBean.getDiscountValue() != null) {
              totalAmount = totalAmount.add((detailBean.getDiscountValue()).add(
                  detailBean.getOrderDetailCommodityInfo().getRetailPrice()).multiply(
                  NumUtil.parse(detailBean.getShippingDetailCommodityAmount())));
            } else {
              totalAmount = totalAmount.add((detailBean.getOrderDetailCommodityInfo().getRetailPrice()).multiply(NumUtil
                  .parse(detailBean.getShippingDetailCommodityAmount())));
            }
          }
        }
      }
    }
    return totalAmount;
  }

  // 所有普通商品的list取得
  private List<String> getCommodityList(OrderModifyBean bean) {
    List<String> shippingCommodityList = new ArrayList<String>();
    for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
      for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
        if (detailBean.getCommodityType() == null || CommodityType.GENERALGOODS.longValue().equals(detailBean.getCommodityType())) {
          shippingCommodityList.add(detailBean.getOrderDetailCommodityInfo().getCommodityCode());
        }
      }
    }
    return shippingCommodityList;
  }

  // 验证多重优惠活动的商品是否可以领取
  private boolean checkGiftStock(OrderModifyBean bean, String skuCode, String campaignCode) {
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    Long stockQuantity = catalogService.getAvailableStock(bean.getShopCode(), skuCode);
    // 减去购物车中该赠品的数量
    for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
      for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
        if (skuCode.equals(detailBean.getSkuCode())) {
          if (campaignCode.equals(detailBean.getCampaignCode())) {
            return false;
          }
        }
      }
    }
    if (stockQuantity == -1L) {
      return true;
    } else if (stockQuantity == 0L) {
      return false;
    }
    // 减去购物车中该赠品的数量
    for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
      for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
        if (skuCode.equals(detailBean.getSkuCode())) {
          stockQuantity = stockQuantity - 1L;
        } else {
          for (GiftInfo giftInfo : detailBean.getGiftCommodityList()) {
            if (giftInfo.getShippingDetail().getSkuCode().equals(skuCode)) {
              stockQuantity = stockQuantity - giftInfo.getShippingDetail().getPurchasingAmount();
            }
          }
        }
      }
    }
    // 加上原订单中该赠品的数量
    if (bean.getOrgStockMap().get(skuCode) != null) {
      stockQuantity = stockQuantity + bean.getOrgStockMap().get(skuCode);
    }
    if (stockQuantity <= 0) {
      return false;
    }
    return true;
  }

  public boolean checkCampaignDiscount(OrderModifyBean bean, String skuCode, boolean initFlg) {
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    ShippingDetailBean orgDetailBean = null;
    for (ShippingDetailBean detailBean : bean.getShippingHeaderList().get(0).getShippingDetailList()) {
      if (detailBean.getSkuCode().equals(skuCode)) {
        orgDetailBean = detailBean;
        break;
      }
    }
    CampaignInfo campaignInfo = communicationService.getCampaignInfo(orgDetailBean.getCampaignCode());
    if (campaignInfo == null || campaignInfo.getCampaignMain() == null) {
      addErrorMessage("您输入的折扣券【" + orgDetailBean.getCampaignCode() + "】不存在或者已过期。");
      return false;
    } else if (!DateUtil.isPeriodDate(campaignInfo.getCampaignMain().getCampaignStartDate(), campaignInfo.getCampaignMain()
        .getCampaignEndDate(), bean.getOldOrderContainer().getOrderHeader().getOrderDatetime())) {
      addErrorMessage("您输入的折扣券【" + orgDetailBean.getCampaignCode() + "】不存在或者已过期。");
      return false;
    } else if (!CampaignMainType.SALE_OFF.longValue().equals(campaignInfo.getCampaignMain().getCampaignType())) {
      addErrorMessage("您输入的折扣券【" + orgDetailBean.getCampaignCode() + "】不存在或者已过期。");
      return false;
    } else {
      CampaignCondition condition = campaignInfo.getConditionList().get(0);
      CampaignDoings doings = campaignInfo.getCampaignDoings();
      List<String> commodityList = Arrays.asList(condition.getAttributrValue().trim().split(","));
      if (commodityList.contains(orgDetailBean.getOrderDetailCommodityInfo().getCommodityCode())) {
        if (condition.getUseLimit() != null) {
          Long usedCount = orderService.getCampaignDiscountUsedCount(orgDetailBean.getCampaignCode(), bean.getOrderNo());
          for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
            for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
              if (!skuCode.equals(detailBean.getSkuCode()) && StringUtil.hasValue(detailBean.getCampaignName())
                  && orgDetailBean.getCampaignCode().equals(detailBean.getCampaignCode())) {
                usedCount = usedCount + 1;
              }
            }
          }
          if (usedCount >= condition.getUseLimit()) {
            addErrorMessage("您输入的折扣券【" + orgDetailBean.getCampaignCode() + "】已超过最大使用回数。");
            return false;
          }
        }

        if (condition.getMaxCommodityNum() != null && NumUtil.isNum(orgDetailBean.getShippingDetailCommodityAmount())
            && condition.getMaxCommodityNum() > NumUtil.toLong(orgDetailBean.getShippingDetailCommodityAmount())) {
          addErrorMessage("您输入的折扣券【" + orgDetailBean.getCampaignCode() + "】适用商品的最小购物数为" + condition.getMaxCommodityNum().toString()
              + "个。");
          return false;
        } else {
          // 折扣值得计算
          if (initFlg) {
            orgDetailBean.setCampaignCode(campaignInfo.getCampaignMain().getCampaignCode());
            orgDetailBean.setCampaignName(campaignInfo.getCampaignMain().getCampaignName());
            orgDetailBean.setDiscountType(NumUtil.toString(condition.getDiscountType()));
            if (CouponIssueType.PROPORTION.longValue().equals(condition.getDiscountType())) {
              BigDecimal discountValue = BigDecimalUtil.divide(BigDecimalUtil.multiply(orgDetailBean.getOrderDetailCommodityInfo()
                  .getRetailPrice(), new BigDecimal(doings.getAttributrValue())), 100, 2, RoundingMode.HALF_UP);
              if (orgDetailBean.getOrderDetailCommodityInfo().getRetailPrice().compareTo(discountValue) < 0) {
                orgDetailBean.setDiscountValue(orgDetailBean.getOrderDetailCommodityInfo().getRetailPrice());
                orgDetailBean.getOrderDetailCommodityInfo().setRetailPrice(BigDecimal.ZERO);
              } else {
                orgDetailBean.setDiscountValue(discountValue);
                orgDetailBean.getOrderDetailCommodityInfo().setRetailPrice(
                    orgDetailBean.getOrderDetailCommodityInfo().getRetailPrice().subtract(discountValue));
              }
            } else if (CouponIssueType.FIXED.longValue().equals(condition.getDiscountType())) {
              BigDecimal discountValue = new BigDecimal(doings.getAttributrValue());
              if (orgDetailBean.getOrderDetailCommodityInfo().getRetailPrice().compareTo(discountValue) < 0) {
                orgDetailBean.setDiscountValue(orgDetailBean.getOrderDetailCommodityInfo().getRetailPrice());
                orgDetailBean.getOrderDetailCommodityInfo().setRetailPrice(BigDecimal.ZERO);
              } else {
                orgDetailBean.setDiscountValue(discountValue);
                orgDetailBean.getOrderDetailCommodityInfo().setRetailPrice(
                    orgDetailBean.getOrderDetailCommodityInfo().getRetailPrice().subtract(discountValue));
              }
            }
          }

        }
      } else {
        addErrorMessage("您输入的折扣券【" + orgDetailBean.getCampaignCode() + "】不存在或者已过期。");
        return false;
      }
    }
    return true;
  }

  // 无库存的赠品信息再删除
  public OrderContainer recomputeOrderContainer(OrderContainer order) {
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    Map<String, Long> stockMap = new HashMap<String, Long>();
    List<String> skuList = new ArrayList<String>();
    for (OrderDetail orderDetail : order.getOrderDetails()) {
      if (CommodityType.GIFT.longValue().equals(orderDetail.getCommodityType())) {
        Long stockQuantity = catalogService.getAvailableStock(orderDetail.getShopCode(), orderDetail.getSkuCode());
        if (stockQuantity == -1L) {
          continue;
        }
        if (getBean().getOrgStockMap().get(orderDetail.getSkuCode()) != null) {
          stockQuantity = stockQuantity + getBean().getOrgStockMap().get(orderDetail.getSkuCode());
        }
        if (stockQuantity == 0L) {
          stockMap.put(orderDetail.getSkuCode(), 0L);
          skuList.add(orderDetail.getSkuCode());
        } else if (stockQuantity < orderDetail.getPurchasingAmount()) {
          stockMap.put(orderDetail.getSkuCode(), stockQuantity);
          orderDetail.setPurchasingAmount(stockQuantity);
          skuList.add(orderDetail.getSkuCode());
        }
      }
    }
    for (String skuCode : skuList) {
      if (stockMap.get(skuCode) == 0L) {
        for (OrderDetail orderDetail : order.getOrderDetails()) {
          if (orderDetail.getSkuCode().equals(skuCode)) {
            order.getOrderDetails().remove(orderDetail);
            break;
          }
        }
        for (ShippingContainer shippingContainer : order.getShippings()) {
          List<ShippingDetail> removeList = new ArrayList<ShippingDetail>();
          for (ShippingDetail shippingDetail : shippingContainer.getShippingDetails()) {
            if (shippingDetail.getSkuCode().equals(skuCode)) {
              removeList.add(shippingDetail);
            }
          }
          shippingContainer.getShippingDetails().removeAll(removeList);
        }
      } else {
        for (ShippingContainer shippingContainer : order.getShippings()) {
          List<ShippingDetail> removeList = new ArrayList<ShippingDetail>();
          for (ShippingDetail shippingDetail : shippingContainer.getShippingDetails()) {
            if (shippingDetail.getSkuCode().equals(skuCode)) {
              if (stockMap.get(skuCode) > shippingDetail.getPurchasingAmount()) {
                stockMap.put(skuCode, stockMap.get(skuCode) - shippingDetail.getPurchasingAmount());
              } else {
                if (stockMap.get(skuCode) == 0L) {
                  removeList.add(shippingDetail);
                } else {
                  shippingDetail.setPurchasingAmount(stockMap.get(skuCode));
                  stockMap.put(skuCode, 0L);
                }
              }
            }
          }
          shippingContainer.getShippingDetails().removeAll(removeList);
        }
      }
    }
    List<OrderCampaign> campaignList = new ArrayList<OrderCampaign>();
    for (OrderCampaign orderCampaign : order.getOrderCampaigns()) {
      boolean isRemove = true;
      for (ShippingContainer shippingContainer : order.getShippings()) {
        for (ShippingDetail shippingDetail : shippingContainer.getShippingDetails()) {
          if (orderCampaign.getCampaignCode().equals(shippingDetail.getCampaignCode())) {
            isRemove = false;
            break;
          }
        }
      }
      if (isRemove) {
        campaignList.add(orderCampaign);
      }
    }
    order.getOrderCampaigns().removeAll(campaignList);
    return order;
  }


  public BigDecimal getTotalCouponCommodityPrice(String CouponCommodity) {

    if (StringUtil.isNullOrEmpty(CouponCommodity)) {
      return getBean().getAfterTotalPrice().getCommodityPrice();
    }

    // 取得优惠券包含对象商品list
    String[] commodityList = CouponCommodity.split(";");
    BigDecimal result = BigDecimal.ZERO;
    for (String commodityCode : commodityList) {
      for (ShippingHeaderBean headerBean : getBean().getShippingHeaderList()) {
        for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
          if (commodityCode.equals(detailBean.getSkuCode())) {
            result = result.add(BigDecimalUtil.multiply(detailBean.getOrderDetailCommodityInfo().getRetailPrice(), Long
                .valueOf(detailBean.getShippingDetailCommodityAmount())));
          }
        }
      }
    }

    return result;
  }

  public boolean validationCombineStock() {
    boolean validate = true;
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    if (getBean().getShippingHeaderList().size() > 0) {
      for (ShippingHeaderBean shbOut : getBean().getShippingHeaderList()) {
        for (ShippingDetailBean sdbOut : shbOut.getShippingDetailList()) {
          Long allCount = 0L;
          Long allStock = 0L;
          CommodityHeader chOut = service.getCommodityHeader(shbOut.getShippingShopCode(), sdbOut.getSkuCode());
          if (StringUtil.hasValue(chOut.getOriginalCommodityCode()) && chOut.getCombinationAmount() != null) {
            allStock = service.getAvailableStock(shbOut.getShippingShopCode(), chOut.getOriginalCommodityCode());
            allCount = (NumUtil.toLong(sdbOut.getShippingDetailCommodityAmount()) - getOldCommodityTotalAmount(shbOut
                .getShippingShopCode(), sdbOut.getSkuCode()))
                * chOut.getCombinationAmount();
          } else {
            allStock = service.getAvailableStock(shbOut.getShippingShopCode(), sdbOut.getSkuCode());
            allCount = NumUtil.toLong(sdbOut.getShippingDetailCommodityAmount())
                - getOldCommodityTotalAmount(shbOut.getShippingShopCode(), sdbOut.getSkuCode());
          }

          for (ShippingHeaderBean shbIn : getBean().getShippingHeaderList()) {
            if (shbIn.getShippingShopCode().equals(shbOut.getShippingShopCode())) {
              for (ShippingDetailBean sdbIn : shbIn.getShippingDetailList()) {
                if (!sdbIn.getSkuCode().equals(chOut.getCommodityCode())) {
                  CommodityHeader chIn = service.getCommodityHeader(shbIn.getShippingShopCode(), sdbIn.getSkuCode());
                  boolean rel = false;
                  if (StringUtil.hasValue(chIn.getOriginalCommodityCode())) {
                    rel = (chIn.getOriginalCommodityCode().equals(chIn.getCommodityCode()) || (StringUtil.hasValue(chIn
                        .getOriginalCommodityCode()) && chIn.getOriginalCommodityCode().equals(chIn.getOriginalCommodityCode())));
                  } else {
                    rel = (StringUtil.hasValue(chIn.getOriginalCommodityCode()) && chIn.getCommodityCode().equals(
                        chIn.getOriginalCommodityCode()));
                  }

                  if (rel) {
                    if (StringUtil.hasValue(chIn.getOriginalCommodityCode()) && chIn.getCombinationAmount() != null) {
                      allCount = allCount
                          + (NumUtil.toLong(sdbIn.getShippingDetailCommodityAmount()) - getOldCommodityTotalAmount(shbIn
                              .getShippingShopCode(), sdbIn.getSkuCode())) * chIn.getCombinationAmount();
                    } else {
                      allCount = allCount + NumUtil.toLong(sdbIn.getShippingDetailCommodityAmount())
                          - getOldCommodityTotalAmount(shbIn.getShippingShopCode(), sdbIn.getSkuCode());
                    }
                  }
                }
              }
            }
          }

          if (allCount > allStock) {
            addErrorMessage("可用库存不足,商品编号：" + chOut.getCommodityCode());
            validate = false;
          }
        }
      }
    }

    return validate;
  }

}
