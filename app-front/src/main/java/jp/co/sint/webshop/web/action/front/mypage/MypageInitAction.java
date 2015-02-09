package jp.co.sint.webshop.web.action.front.mypage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.ReturnStatusSummary;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommodityContainer;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.service.communication.MyCouponHistorySearchCondition;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.order.OrderHeadline;
import jp.co.sint.webshop.service.order.OrderListSearchCondition;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.MypageBean;
import jp.co.sint.webshop.web.bean.front.mypage.OrderHistoryBean;
import jp.co.sint.webshop.web.bean.front.mypage.MypageBean.MypageDetailBean;
import jp.co.sint.webshop.web.bean.front.mypage.MypageBean.RecommendDetail;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U2030110:マイページのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class MypageInitAction extends WebFrontAction<MypageBean> {

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
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CustomerService customerSv = ServiceLocator.getCustomerService(getLoginInfo());
    CommunicationService cs = ServiceLocator.getCommunicationService(getLoginInfo());
    
    FrontLoginInfo login = getLoginInfo();
    String customerCode = login.getCustomerCode();


    // 客户信息取得，判断客户信息当前是否正常      START
    if (customerSv.isNotFound(customerCode) || customerSv.isInactive(customerCode)) {
      setNextUrl("/app/common/index");
      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }
    
    // 当为支付宝登录会员时，顾客注册
//    if (customer.getCustomerKbn() != null && customer.getCustomerKbn() == 1) {
//      setNextUrl("/app/customer/customer_edit1/init");
//      return FrontActionResult.RESULT_SUCCESS;
//    }
    // 客户信息取得，判断客户信息当前是否正常      END
    
    MypageBean bean = new MypageBean();
    
    CustomerService customerSev = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerInfo info = customerSev.getCustomer(customerCode);
    Customer customer = info.getCustomer();    
    bean.setSex(NumUtil.toString(customer.getSex()));
    
    
    MyCouponHistorySearchCondition condition = new MyCouponHistorySearchCondition();
    condition.setCustomerCode(login.getCustomerCode());
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    Long couponNum = cs.getAvaliableCouponCount(customerCode);
    if(couponNum == null ) {
      couponNum=0L;
    }
    bean.setCouponNum(couponNum.toString());
    
    Long avaliableGiftCardNum = customerSev.getAvaliableGiftCardCount(customerCode);
    if(avaliableGiftCardNum == null ) {
      avaliableGiftCardNum=0L;
    }
    bean.setAvaliableGiftCardNum(avaliableGiftCardNum.toString()); 
    
    
    Long goodPoint = cs.getFriendCouponUseHistoryAllPoint(getLoginInfo().getCustomerCode());
    if(goodPoint == null ) {
      goodPoint=0L;
    }
    bean.setGoodPoint(goodPoint.toString());
    
    // 近期订单取得
    OrderListSearchCondition orderCondition = new OrderListSearchCondition();
    orderCondition.setCustomerCode(customerCode);
    orderCondition.setPaymentStatus("");
    orderCondition.setSearchFixedSalesDataFlg(true);
    orderCondition.setSearchDataTransportFlg(true);
    orderCondition.setSearchListSort(OrderHistoryBean.ORDER_DATETIME_DESC);
    orderCondition.setCustomer(true);
    // 受注ステータスを全て指定
    String[] values = new String[OrderStatus.values().length];
    int j = 0;
    for (OrderStatus status : OrderStatus.values()) {
      values[j] = status.getValue();
      j++;
    }
    orderCondition.setOrderStatus(values);
    // 出荷ステータス:未出荷/一部出荷/全出荷
    orderCondition.setShippingStatusSummary(ShippingStatusSummary.SHIPPED_ALL.getValue(), ShippingStatusSummary.IN_PROCESSING
        .getValue(), ShippingStatusSummary.PARTIAL_SHIPPED.getValue(), ShippingStatusSummary.NOT_SHIPPED.getValue());
    // 返品状況:返品なし/一部返品/全返品
    orderCondition.setReturnStatusSummary(new String[] {
        ReturnStatusSummary.NOT_RETURNED.getValue(), 
        ReturnStatusSummary.PARTIAL_RETURNED.getValue(),
        ReturnStatusSummary.RETURNED_ALL.getValue()
    });

    // 過去半年分のみを指定
    orderCondition.setOrderDatetimeStart(DateUtil.toDateString(DateUtil.addMonth(DateUtil.getSysdate(), -6)));
    orderCondition.setLimitNum(5L);
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    SearchResult<OrderHeadline> result = orderService.searchOrderList(orderCondition);
    List<OrderHeadline> orderList = result.getRows();
    
    List<MypageDetailBean> list = new ArrayList<MypageDetailBean>();
    if (orderList.size() > 0) {
      for (int i = 0; i < 5; i++) {
        if (i >= orderList.size()) {
          break;
        }
        MypageDetailBean detail = new MypageDetailBean();
        detail.setOrderDatetime(orderList.get(i).getOrderDatetime());
        detail.setOrderNo(orderList.get(i).getOrderNo());
        String totalOrderPrice = NumUtil.toString(BigDecimalUtil.add(NumUtil.parse(orderList.get(i).getTotalAmount()), orderList
            .get(i).getPaymentCommission()));
        if (StringUtil.hasValue(orderList.get(i).getDiscountPrice())) {
          totalOrderPrice = (new BigDecimal(totalOrderPrice).subtract(new BigDecimal(orderList.get(i).getDiscountPrice())))
              .toString();
        }
        detail.setTotalAmount(totalOrderPrice);
        detail.setAddressLastName(orderList.get(i).getAddressLastName());
        detail.setShippingStatus(getShippingStatus(orderList.get(i).getShippingStatusSummary(), orderService
            .getShippingList(orderList.get(i).getOrderNo())));

        list.add(detail);
      }
    }
    bean.setList(list);
    
//    // 推荐商品
//    CommodityContainerCondition recommendCondition = new CommodityContainerCondition();
//    recommendCondition.setSearchCustomerCode(customerCode);
//    recommendCondition.setDisplayClientType(DisplayClientType.PC.getValue());
//    recommendCondition.setByRepresent(true);
//    recommendCondition.setMaxFetchSize(3);
//    recommendCondition = PagerUtil.createSearchCondition(getRequestParameter(), recommendCondition);
//
//    // 検索結果の取得
//    SearchResult<CommodityContainer> recommendResult = service.getRecommendedCommodities(recommendCondition);
//    List<CommodityContainer> commodityList = recommendResult.getRows();
//    TaxUtil tu = DIContainer.get("TaxUtil");
//    Long taxRate = tu.getTaxRate();
//    List<RecommendDetail> rdList = new ArrayList<RecommendDetail>();
//    for (CommodityContainer cc : commodityList) {
//
//      RecommendDetail commodity = new RecommendDetail();
//      Campaign campaign = service.getAppliedCampaignInfo(cc.getCommodityHeader().getShopCode(), cc.getCommodityHeader()
//          .getCommodityCode());
//      Price price = new Price(cc.getCommodityHeader(), cc.getCommodityDetail(), campaign, taxRate);
//
//      String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
//      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
//        commodity.setCommodityName(StringUtil.getHeadline(cc.getCommodityHeader().getCommodityName(), 20));
//        commodity.setCommodityDescription(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionPc()));
//        commodity.setCommodityDescriptionShort(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionMobile()));
//
//      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
//        commodity.setCommodityName(StringUtil.getHeadline(cc.getCommodityHeader().getCommodityNameEn(), 20));
//        commodity.setCommodityDescription(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionPcEn()));
//        commodity.setCommodityDescriptionShort(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionMobileEn()));
//
//      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
//        commodity.setCommodityName(StringUtil.getHeadline(cc.getCommodityHeader().getCommodityNameJp(), 20));
//        commodity.setCommodityDescription(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionPcJp()));
//        commodity.setCommodityDescriptionShort(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionMobileJp()));
//
//      }
//      commodity.setCommodityCode(cc.getCommodityHeader().getCommodityCode());
//      commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice()));
//      commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice()));
//      commodity.setReservedPrice(NumUtil.toString(price.getReservationPrice()));
//      commodity.setCommodityTaxType(NumUtil.toString(cc.getCommodityHeader().getCommodityTaxType()));
//      commodity.setCommodityPointRate(NumUtil.toString(cc.getCommodityHeader().getCommodityPointRate()));
//      commodity.setReviewScore(NumUtil.toString(cc.getReviewSummary().getReviewScore()));
//      commodity.setStockQuantity(NumUtil.toString(cc.getContainerAddInfo().getAvailableStockQuantity()));
//      commodity.setCampaignPeriod(price.isCampaign());
//      commodity.setPointPeriod(price.isPoint());
//      commodity.setDiscountPeriod(price.isDiscount());
//      commodity.setShopCode(cc.getCommodityHeader().getShopCode());
//      commodity.setShopName(cc.getShop().getShopName());
//      if (cc.getCommodityHeader().getImportCommodityType() != null) {
//        commodity.setImportCommodityType(cc.getCommodityHeader().getImportCommodityType());
//      }
//      if (cc.getCommodityHeader().getClearCommodityType() != null) {
//        commodity.setClearCommodityType(cc.getCommodityHeader().getClearCommodityType());
//      }
//      if (cc.getCommodityHeader().getReserveCommodityType1() != null) {
//        commodity.setReserveCommodityType1(cc.getCommodityHeader().getReserveCommodityType1());
//      }
//      if (cc.getCommodityHeader().getReserveCommodityType2() != null) {
//        commodity.setReserveCommodityType2(cc.getCommodityHeader().getReserveCommodityType2());
//      }
//      if (cc.getCommodityHeader().getReserveCommodityType3() != null) {
//        commodity.setReserveCommodityType3(cc.getCommodityHeader().getReserveCommodityType3());
//      }
//      if (cc.getCommodityHeader().getNewReserveCommodityType1() != null) {
//        commodity.setNewReserveCommodityType1(cc.getCommodityHeader().getNewReserveCommodityType1());
//      }
//      if (cc.getCommodityHeader().getNewReserveCommodityType2() != null) {
//        commodity.setNewReserveCommodityType2(cc.getCommodityHeader().getNewReserveCommodityType2());
//      }
//      if (cc.getCommodityHeader().getNewReserveCommodityType3() != null) {
//        commodity.setNewReserveCommodityType3(cc.getCommodityHeader().getNewReserveCommodityType3());
//      }
//      if (cc.getCommodityHeader().getNewReserveCommodityType4() != null) {
//        commodity.setNewReserveCommodityType4(cc.getCommodityHeader().getNewReserveCommodityType4());
//      }
//      if (cc.getCommodityHeader().getNewReserveCommodityType5() != null) {
//        commodity.setNewReserveCommodityType5(cc.getCommodityHeader().getNewReserveCommodityType5());
//      }
//      if (cc.getCommodityDetail().getInnerQuantity() != null) {
//        commodity.setInnerQuantity(cc.getCommodityDetail().getInnerQuantity());
//      }
//
//      if (price.getCampaignInfo() == null) {
//        commodity.setCampaignDiscountRate("");
//      } else {
//        commodity.setCampaignCode(price.getCampaignInfo().getCampaignCode());
//        commodity.setCampaignName(utilService.getNameByLanguage(price.getCampaignInfo().getCampaignName(), price.getCampaignInfo()
//            .getCampaignNameEn(), price.getCampaignInfo().getCampaignNameJp()));
//        commodity.setCampaignDiscountRate(NumUtil.toString(price.getCampaignInfo().getCampaignDiscountRate()));
//      }
//
//      if (price.isSale()) {
//        commodity.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
//      } else if (price.isDiscount()) {
//        commodity.setPriceMode(CommodityPriceType.DISCOUNT_PRICE.getValue());
//      } else if (price.isReserved()) {
//        commodity.setPriceMode(CommodityPriceType.RESERVATION_PRICE.getValue());
//      } else {
//        commodity.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
//      }
//
//      commodity.setStockStatus("");
//
//      rdList.add(commodity);
//    }
//    bean.setRdList(rdList);
    
    
    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();
    CommodityContainerCondition favCondition = new CommodityContainerCondition();
    favCondition.setSearchCustomerCode(customerCode);
    favCondition = PagerUtil.createSearchCondition(getRequestParameter(), favCondition);
    favCondition.setMaxFetchSize(3);
    if(!StringUtil.isNullOrEmpty(getClient()) && !getClient().equals(ClientType.OTHER)){
      WebshopConfig config = DIContainer.getWebshopConfig();
      condition.setPageSize(config.getMobilePageSize());
    }
    // 検索結果の取得

    SearchResult<CommodityContainer> favResult = service.getFavoriteCommodities(favCondition);
    List<CommodityContainer> favCommodityList = favResult.getRows();
    
    List<RecommendDetail> favList = new ArrayList<RecommendDetail>();
    for (CommodityContainer cc : favCommodityList) {

      RecommendDetail commodity = new RecommendDetail();
      Campaign campaign = service.getAppliedCampaignInfo(cc.getCommodityHeader().getShopCode(), cc.getCommodityHeader()
          .getCommodityCode());
      Price price = new Price(cc.getCommodityHeader(), cc.getCommodityDetail(), campaign, taxRate);

      String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
        commodity.setCommodityName(StringUtil.getHeadline(cc.getCommodityHeader().getCommodityName(), 20));
        commodity.setCommodityDescription(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionPc()));
        commodity.setCommodityDescriptionShort(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionMobile()));

      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
        commodity.setCommodityName(StringUtil.getHeadline(cc.getCommodityHeader().getCommodityNameEn(), 20));
        commodity.setCommodityDescription(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionPcEn()));
        commodity.setCommodityDescriptionShort(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionMobileEn()));

      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
        commodity.setCommodityName(StringUtil.getHeadline(cc.getCommodityHeader().getCommodityNameJp(), 20));
        commodity.setCommodityDescription(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionPcJp()));
        commodity.setCommodityDescriptionShort(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionMobileJp()));

      }
      commodity.setCommodityCode(cc.getCommodityHeader().getCommodityCode());
      commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice()));
      commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice()));
      commodity.setReservedPrice(NumUtil.toString(price.getReservationPrice()));
      commodity.setCommodityTaxType(NumUtil.toString(cc.getCommodityHeader().getCommodityTaxType()));
      commodity.setCommodityPointRate(NumUtil.toString(cc.getCommodityHeader().getCommodityPointRate()));
      commodity.setReviewScore(NumUtil.toString(cc.getReviewSummary().getReviewScore()));
      commodity.setStockQuantity(NumUtil.toString(cc.getContainerAddInfo().getAvailableStockQuantity()));
      commodity.setCampaignPeriod(price.isCampaign());
      commodity.setPointPeriod(price.isPoint());
      commodity.setDiscountPeriod(price.isDiscount());
      commodity.setShopCode(cc.getCommodityHeader().getShopCode());
      commodity.setShopName(cc.getShop().getShopName());
      if (cc.getCommodityHeader().getImportCommodityType() != null) {
        commodity.setImportCommodityType(cc.getCommodityHeader().getImportCommodityType());
      }
      if (cc.getCommodityHeader().getClearCommodityType() != null) {
        commodity.setClearCommodityType(cc.getCommodityHeader().getClearCommodityType());
      }
      if (cc.getCommodityHeader().getReserveCommodityType1() != null) {
        commodity.setReserveCommodityType1(cc.getCommodityHeader().getReserveCommodityType1());
      }
      if (cc.getCommodityHeader().getReserveCommodityType2() != null) {
        commodity.setReserveCommodityType2(cc.getCommodityHeader().getReserveCommodityType2());
      }
      if (cc.getCommodityHeader().getReserveCommodityType3() != null) {
        commodity.setReserveCommodityType3(cc.getCommodityHeader().getReserveCommodityType3());
      }
      if (cc.getCommodityHeader().getNewReserveCommodityType1() != null) {
        commodity.setNewReserveCommodityType1(cc.getCommodityHeader().getNewReserveCommodityType1());
      }
      if (cc.getCommodityHeader().getNewReserveCommodityType2() != null) {
        commodity.setNewReserveCommodityType2(cc.getCommodityHeader().getNewReserveCommodityType2());
      }
      if (cc.getCommodityHeader().getNewReserveCommodityType3() != null) {
        commodity.setNewReserveCommodityType3(cc.getCommodityHeader().getNewReserveCommodityType3());
      }
      if (cc.getCommodityHeader().getNewReserveCommodityType4() != null) {
        commodity.setNewReserveCommodityType4(cc.getCommodityHeader().getNewReserveCommodityType4());
      }
      if (cc.getCommodityHeader().getNewReserveCommodityType5() != null) {
        commodity.setNewReserveCommodityType5(cc.getCommodityHeader().getNewReserveCommodityType5());
      }
      if (cc.getCommodityDetail().getInnerQuantity() != null) {
        commodity.setInnerQuantity(cc.getCommodityDetail().getInnerQuantity());
      }

      if (price.getCampaignInfo() == null) {
        commodity.setCampaignDiscountRate("");
      } else {
        commodity.setCampaignCode(price.getCampaignInfo().getCampaignCode());
        commodity.setCampaignName(utilService.getNameByLanguage(price.getCampaignInfo().getCampaignName(), price.getCampaignInfo()
            .getCampaignNameEn(), price.getCampaignInfo().getCampaignNameJp()));
        commodity.setCampaignDiscountRate(NumUtil.toString(price.getCampaignInfo().getCampaignDiscountRate()));
      }

      if (price.isSale()) {
        commodity.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
      } else if (price.isDiscount()) {
        commodity.setPriceMode(CommodityPriceType.DISCOUNT_PRICE.getValue());
      } else if (price.isReserved()) {
        commodity.setPriceMode(CommodityPriceType.RESERVATION_PRICE.getValue());
      } else {
        commodity.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
      }

      commodity.setStockStatus("");

      favList.add(commodity);
    }
    bean.setFavList(favList);
    
    
    
    
    
    
    
//    
//    bean.setCustomerCode(customerCode);
//    bean.setLastName(customer.getLastName());
//    bean.setFirstName(customer.getFirstName());
//    bean.setEmail(customer.getEmail());
//    bean.setRestPoint(NumUtil.toString(customer.getRestPoint()));
//
//    SiteManagementService sms = ServiceLocator.getSiteManagementService(getLoginInfo());
//    PointRule pr = sms.getPointRule();
//    String pointPeriod = pr.getPointPeriod() + "";
//    bean.setPointPeriod(pointPeriod);
//
//
//    CustomerGroupCount customerGroupCount = service.getCustomerGroup(customer.getCustomerGroupCode());
//    
//    bean.setCustomerGroupName(utilService.getNameByLanguage(customerGroupCount.getCustomerGroupName(), customerGroupCount
//        .getCustomerGroupNameEn(), customerGroupCount.getCustomerGroupNameJp()));
//    SiteManagementService shopSv = ServiceLocator.getSiteManagementService(getLoginInfo());
//    bean.setShopCode(shopSv.getSite().getShopCode());
//
//    // 受注履歴取得
//    OrderListSearchCondition orderCondition = new OrderListSearchCondition();
//    orderCondition.setCustomerCode(customerCode);
//    orderCondition.setPaymentStatus("");
//    orderCondition.setSearchFixedSalesDataFlg(true);
//    orderCondition.setSearchDataTransportFlg(true);
//    orderCondition.setSearchListSort(OrderHistoryBean.ORDER_DATETIME_DESC);
//    orderCondition.setCustomer(true);
//    // 受注ステータスを全て指定
//    String[] values = new String[OrderStatus.values().length];
//    int j = 0;
//    for (OrderStatus status : OrderStatus.values()) {
//      values[j] = status.getValue();
//      j++;
//    }
//    orderCondition.setOrderStatus(values);
//    // 出荷ステータス:未出荷/一部出荷/全出荷
//    orderCondition.setShippingStatusSummary(ShippingStatusSummary.SHIPPED_ALL.getValue(), ShippingStatusSummary.IN_PROCESSING
//        .getValue(), ShippingStatusSummary.PARTIAL_SHIPPED.getValue(), ShippingStatusSummary.NOT_SHIPPED.getValue());
//    // 返品状況:返品なし/一部返品/全返品
//    orderCondition.setReturnStatusSummary(new String[] {
//        ReturnStatusSummary.NOT_RETURNED.getValue(), ReturnStatusSummary.PARTIAL_RETURNED.getValue(),
//        ReturnStatusSummary.RETURNED_ALL.getValue()
//    });
//
//    // 過去半年分のみを指定
//    orderCondition.setOrderDatetimeStart(DateUtil.toDateString(DateUtil.addMonth(DateUtil.getSysdate(), -6)));
//
//    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
//    SearchResult<OrderHeadline> result = orderService.searchOrderList(orderCondition);
//    List<OrderHeadline> orderList = result.getRows();
//
//    WebshopConfig config = DIContainer.getWebshopConfig();
//    String customerGroupName = "";
//    Long[] datas = new Long[] {
//        0L, 0L, 0L
//    };
//    Long[] datasLv = new Long[] {
//        0L, 0L, 0L
//    };
//    for (String data : config.getCustomerGroupType()) {
//      String[] arr = data.split(",");
//      if (arr.length > 0) {
//        datasLv[NumUtil.parse(arr[0]).intValue()] = NumUtil.parseLong(NumUtil.parse(arr[0]));
//        datas[NumUtil.parse(arr[0]).intValue()] = NumUtil.parseLong(NumUtil.parse(arr[1]));
//      }
//    }
//
//    // 级别统计期间
//    Date date = DateUtil.getSysdate();
//    date = DateUtil.addMonth(date, -(config.getCustomerLvMonth() - 1));
//    String dateFrom = DateUtil.toDateTimeString(date, "yyyy/MM") + "/01";
//    date = DateUtil.getSysdate();
//    date = DateUtil.addMonth(date, 1);
//    date = DateUtil.fromString(DateUtil.toDateTimeString(date, "yyyy/MM") + "/01");
//    date = DateUtil.addDate(date, -1);
//    String dateTo = DateUtil.toDateString(date);
//
//    CustomerGroupDao customerGroupDao = DIContainer.getDao(CustomerGroupDao.class);
//    List<OrderSummary> summaryList = orderService.getNeedRemindSummary(customerCode, dateFrom, dateTo);
//
//    // 2012-02-17 yyq update start
//    long totalAmount = 0L;
//    Date dateMes = DateUtil.getSysdate();
//    dateMes = DateUtil.addMonth(dateMes, 3);
//    String monthStr = "";
//    if (locale.toString().equals("en_US")) {
//      monthStr = StringUtil.getLocalEnMonth(dateMes.getMonth());
//    } else {
//      monthStr = dateMes.getMonth() + "";
//    }
//    for (OrderSummary summary : summaryList) {
//      totalAmount = summary.getTotalAmount().longValue();
//    }
//
//    // 提示即将升级为金卡会员信息
//    if (totalAmount < datas[2] && totalAmount >= datas[1]) {
//      CustomerGroup customerMess = customerGroupDao.load(datasLv[1].toString());
//      CustomerGroup customerGroup = customerGroupDao.load(datasLv[2].toString());
//      if (customerMess != null && customerGroup != null) {
//        String customerGroupNameMess = utilService.getNameByLanguage(customerMess.getCustomerGroupName(), customerMess
//            .getCustomerGroupNameEn(), customerMess.getCustomerGroupNameJp());
//        customerGroupName = utilService.getNameByLanguage(customerGroup.getCustomerGroupName(), customerGroup
//            .getCustomerGroupNameEn(), customerGroup.getCustomerGroupNameJp());
//        Long resultLong = datas[2] - totalAmount;
//        bean.setCustomerGroupMessages(WebMessage.get(MypageDisplayMessage.CUSTOMER_GROUP_UPGRADES_REMIND_TWO, monthStr,
//            customerGroupNameMess, resultLong.toString(), customerGroupName));
//      } else {
//        bean.setCustomerGroupMessages(Messages.getString("web.action.front.mypage.MypageInitAction.0"));
//      }
//    }
//    
//    // 提示即将升级为银卡会员信息
//    if (totalAmount < datas[1]) {
//      CustomerGroup customerGroup = customerGroupDao.load(datasLv[1].toString());
//      if (customerGroup != null) {
//        customerGroupName = utilService.getNameByLanguage(customerGroup.getCustomerGroupName(), customerGroup
//            .getCustomerGroupNameEn(), customerGroup.getCustomerGroupNameJp());
//        Long resultLong = datas[1] - totalAmount;
//        bean.setCustomerGroupMessages(WebMessage.get(MypageDisplayMessage.CUSTOMER_GROUP_UPGRADES_REMIND_ONE,
//            resultLong.toString(), monthStr, customerGroupName));
//      } else {
//        bean.setCustomerGroupMessages(Messages.getString("web.action.front.mypage.MypageInitAction.0"));
//      }
//    }
//    // 已经成为金卡会员的提示信息
//    if (totalAmount >= datas[2]) {
//      CustomerGroup customerGroup = customerGroupDao.load(datasLv[2].toString());
//      if (customerGroup != null) {
//        customerGroupName = utilService.getNameByLanguage(customerGroup.getCustomerGroupName(), customerGroup
//            .getCustomerGroupNameEn(), customerGroup.getCustomerGroupNameJp());
//        bean.setCustomerGroupMessages(WebMessage.get(MypageDisplayMessage.CUSTOMER_GROUP_UPGRADES_REMIND_THREE, monthStr,
//            customerGroupName));
//      } else {
//        bean.setCustomerGroupMessages(Messages.getString("web.action.front.mypage.MypageInitAction.0"));
//      }
//    }
//
//    List<MypageDetailBean> list = new ArrayList<MypageDetailBean>();
//    if (orderList.size() > 0) {
//      for (int i = 0; i < 5; i++) {
//        if (i >= orderList.size()) {
//          break;
//        }
//        MypageDetailBean detail = new MypageDetailBean();
//        detail.setUpdatedDatetime(orderList.get(i).getUpdatedDatetime());
//        detail.setOrderDatetime(orderList.get(i).getOrderDatetime());
//        detail.setPaymentDate(orderList.get(i).getPaymentDate());
//        detail.setPaymentMethodName(orderList.get(i).getPaymentMethodName());
//        detail.setOrderNo(orderList.get(i).getOrderNo());
//        detail.setTotalAmount(NumUtil.toString(BigDecimalUtil.add(NumUtil.parse(orderList.get(i).getTotalAmount()), orderList
//            .get(i).getPaymentCommission())));
//        String totalOrderPrice = detail.getTotalAmount();
//        if (StringUtil.hasValue(orderList.get(i).getDiscountPrice())) {
//          totalOrderPrice = (new BigDecimal(totalOrderPrice).subtract(new BigDecimal(orderList.get(i).getDiscountPrice())))
//              .toString();
//        }
//        detail.setTotalAmount(totalOrderPrice);
//        List<CommodityAndReviewBean> coList = new ArrayList<CommodityAndReviewBean>();
//        List<String> commodityName = new ArrayList<String>();
//        List<String> shopCode = new ArrayList<String>();
//        List<String> commodityCode = new ArrayList<String>();
//        List<String> standardDetail1Name = new ArrayList<String>();
//        List<String> standardDetail2Name = new ArrayList<String>();
//        List<String> salePrice = new ArrayList<String>();
//
//        OrderSummary orderSummary = orderService.getOrderSummary(orderList.get(i).getOrderNo());
//        // M17N 10361 追加 ここから
//        // 未取消 && 未支付 && alipay とnetbank
//        if (orderSummary.getOrderStatus() != Long.parseLong(OrderStatus.CANCELLED.getValue())
//            && orderSummary.getPaymentStatus() == Long.parseLong(PaymentStatus.NOT_PAID.getValue())
//            && (orderSummary.getPaymentMethodType().equals(PaymentMethodType.ALIPAY.getValue()) 
//                || orderSummary.getPaymentMethodType().equals(PaymentMethodType.CHINA_UNIONPAY.getValue())
//                || orderSummary.getPaymentMethodType().equals(PaymentMethodType.OUTER_CARD.getValue())
//                || orderSummary.getPaymentMethodType().equals(PaymentMethodType.INNER_CARD.getValue()))) {
//          detail.setPaymentFlg(true);
//        } else {
//          detail.setPaymentFlg(false);
//        }
//        // 2012-02-17 yyq add start
//        OrderContainer container = orderService.getOrder(detail.getOrderNo());
//        detail.setPaymentFormObject(getPaymentFormObject(container));
//        if (PaymentMethodType.ALIPAY.getValue().equals(container.getOrderHeader().getPaymentMethodType())) {
//          detail.setDisplayAlipayInfo(true);
//        } else if (PaymentMethodType.CHINA_UNIONPAY.getValue().equals(container.getOrderHeader().getPaymentMethodType()) 
//            || PaymentMethodType.OUTER_CARD.getValue().equals(container.getOrderHeader().getPaymentMethodType())
//            || PaymentMethodType.INNER_CARD.getValue().equals(container.getOrderHeader().getPaymentMethodType())) {
//          detail.setDisplayChinapayInfo(true);
//        }
//        // 2012-02-17 yyq add end
//
//        List<OrderDetail> orderDetailList = orderService.getOrderDetailList(orderList.get(i).getOrderNo());
//        // 20130801 txw update start
//        String shippingNo = container.getShippings().get(0).getShippingHeader().getShippingNo();
//        List<ShippingDetail> orderShippingList = orderService.getShippingDetailList(shippingNo);
//        for (OrderDetail dl : orderDetailList) {
//          for (ShippingDetail sd : orderShippingList) {
//            if (dl.getSkuCode().equals(sd.getSkuCode()) && !sd.getCommodityType().equals(CommodityType.PROPAGANDA.longValue())) {
//              // 20111216 lirong add start
//              // 将商品code、名字、是否可以评论存入CommodityAndReviewBean
//              CommodityAndReviewBean commodityAndReviewBean = new CommodityAndReviewBean();
//              commodityAndReviewBean.setCommodityCode(dl.getCommodityCode());
//              commodityAndReviewBean.setSkuCode(dl.getSkuCode());
//              commodityAndReviewBean.setCommodityName(StringUtil.getHeadline(dl.getCommodityName(), 20));
//              commodityAndReviewBean.setDisplayReviewButtonFlg(false);
//              commodityAndReviewBean.setShopCode(dl.getShopCode());
//              commodityAndReviewBean.setPurchasingAmount(sd.getPurchasingAmount());
//              commodityAndReviewBean.setStandardDetail1Name(dl.getStandardDetail1Name());
//              commodityAndReviewBean.setStandardDetail2Name(dl.getStandardDetail2Name());
//              // 判断评论是否显示,判断标准订单的发货状态为发货完毕
//              if (CommodityType.GENERALGOODS.longValue().equals(dl.getCommodityType())
//                  && ShippingStatus.SHIPPED.getValue().equals(orderList.get(i).getShippingStatusSummary())) {
//
//                ReviewPostCustomerCountSearchCondition reviewPostCustomerCountCondition = new ReviewPostCustomerCountSearchCondition();
//                reviewPostCustomerCountCondition.setShopCode(bean.getShopCode());
//                reviewPostCustomerCountCondition.setCommodityCode(dl.getCommodityCode());
//                reviewPostCustomerCountCondition.setCustomerCode(getLoginInfo().getCustomerCode());
//                // 20111220 os013 add start
//                reviewPostCustomerCountCondition.setOrderNo(orderList.get(i).getOrderNo());
//                // 20111220 0s013 add end
//                Long count = cs.getReviewPostCount(reviewPostCustomerCountCondition);
//                // 判断每条订单的每个商品我评论了几次
//                if (count.intValue() < 1) {
//                  commodityAndReviewBean.setDisplayReviewButtonFlg(true);
//                }
//              }
//              // 20111216 lirong add end
//              commodityName.add(dl.getCommodityName());
//              // add by lc 2012-01-31 start
//              shopCode.add(dl.getShopCode());
//              commodityCode.add(dl.getCommodityCode());
//              // add by lc 2012-01-31 end
//              standardDetail1Name.add(dl.getStandardDetail1Name());
//              standardDetail2Name.add(dl.getStandardDetail2Name());
//
//              commodityAndReviewBean.setCommoditySet(displaySetButton(dl.getShopCode(), dl.getCommodityCode(), dl
//                  .getSetCommodityFlg()));
//              commodityAndReviewBean.setGiftFlg(CommodityType.GIFT.longValue().equals(dl.getCommodityType()));
//              String diplayCommodityName = getDisplayName(dl.getCommodityName(), dl.getStandardDetail1Name(), dl
//                  .getStandardDetail2Name());
//              commodityAndReviewBean.setDisplayCartButton(checkAvailable(dl.getShopCode(), dl.getCommodityCode(), dl.getSkuCode(),
//                  dl.getCommodityType(), dl.getSetCommodityFlg(), 1L, diplayCommodityName, false));
//              // 2012/12/19 促销对应 ob update end
//              // add by yyq 20120219 end
//
//              salePrice.add(NumUtil.toString(sd.getRetailPrice()));
//              // 20111216 lirong add start
//              coList.add(commodityAndReviewBean);
//              // 20111216 lirong add end
//            }
//          }
//        }
//        // 20130801 txw update end
//        // 20111216 lirong add start
//        detail.setCommodityAndReviewBeanList(coList);
//        // 20111216 lirong add end
//        detail.setCommodityName(commodityName);
//        // add by lc 2012-01-31 start
//        detail.setShopCode(shopCode);
//        detail.setCommodityCode(commodityCode);
//        // add by lc 2012-01-31 end
//        detail.setStandardDetail1Name(standardDetail1Name);
//        detail.setStandardDetail2Name(standardDetail2Name);
//        detail.setSalePrice(salePrice);
//        detail.setShippingStatus(getShippingStatus(orderList.get(i).getShippingStatusSummary(), orderService
//            .getShippingList(orderList.get(i).getOrderNo())));
//        // 运单号
//        detail.setSlipNo(orderList.get(i).getDeliverySlipNo());
//
//        // M17N 10361 修正 ここから
//        if (orderList.get(i).getOrderStatus().equals(OrderStatus.RESERVED.getValue())
//            || orderList.get(i).getOrderStatus().equals(OrderStatus.PHANTOM_RESERVATION.getValue())) {
//          detail.setReserve(true);
//        } else {
//          detail.setReserve(false);
//        }
//        // M17N 10361 修正 ここまで
//        if (orderList.get(i).getReturnStatusSummary().equals(ReturnStatusSummary.NOT_RETURNED.getValue())) {
//          detail.setHasReturn(false);
//        } else {
//          detail.setHasReturn(true);
//        }
//
//        // キャンセルボタンの表示
//        OrderHeader orderHeader = orderService.getOrderHeader(orderList.get(i).getOrderNo());
//        if (orderHeader.getPaymentStatus().equals(PaymentStatus.NOT_PAID.longValue())
//            && orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())
//            && DataTransportStatus.fromValue(orderHeader.getDataTransportStatus()).equals(DataTransportStatus.NOT_TRANSPORTED)) {
//          Shop shop = shopSv.getSite();
//          if (shop.getCustomerCancelableFlg().equals(CustomerCancelableFlg.ENABLED.longValue())) {
//            detail.setCancelStatus(true);
//          }
//        }
//        // M17N 10361 追加 ここまで
//        list.add(detail);
//      }
//    }
//    bean.setList(list);
//    bean.setListSize(Integer.valueOf(list.size()).longValue());
//
//    // 推荐商品
//    // add by lc 2012-01-31 start
//    CommodityContainerCondition recommendCondition = new CommodityContainerCondition();
//    recommendCondition.setSearchCustomerCode(customerCode);
//    recommendCondition.setDisplayClientType(DisplayClientType.PC.getValue());
//    recommendCondition.setByRepresent(true);
//    recommendCondition.setMaxFetchSize(5);
//    recommendCondition = PagerUtil.createSearchCondition(getRequestParameter(), recommendCondition);
//
//    // 検索結果の取得
//    SearchResult<CommodityContainer> recommendResult = service.getRecommendedCommodities(recommendCondition);
//    List<CommodityContainer> commodityList = recommendResult.getRows();
//    TaxUtil tu = DIContainer.get("TaxUtil");
//    Long taxRate = tu.getTaxRate();
//    List<RecommendDetail> rdList = new ArrayList<RecommendDetail>();
//    for (CommodityContainer cc : commodityList) {
//
//      RecommendDetail commodity = new RecommendDetail();
//      Campaign campaign = service.getAppliedCampaignInfo(cc.getCommodityHeader().getShopCode(), cc.getCommodityHeader()
//          .getCommodityCode());
//      Price price = new Price(cc.getCommodityHeader(), cc.getCommodityDetail(), campaign, taxRate);
//
//      // add by cs_yuli 20120514 start
//      String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
//      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
//        commodity.setCommodityName(StringUtil.getHeadline(cc.getCommodityHeader().getCommodityName(), 20));
//        commodity.setCommodityDescription(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionPc()));
//        commodity.setCommodityDescriptionShort(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionMobile()));
//
//      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
//        commodity.setCommodityName(StringUtil.getHeadline(cc.getCommodityHeader().getCommodityNameEn(), 20));
//        commodity.setCommodityDescription(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionPcEn()));
//        commodity.setCommodityDescriptionShort(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionMobileEn()));
//
//      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
//        commodity.setCommodityName(StringUtil.getHeadline(cc.getCommodityHeader().getCommodityNameJp(), 20));
//        commodity.setCommodityDescription(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionPcJp()));
//        commodity.setCommodityDescriptionShort(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionMobileJp()));
//
//      }
//      // add by cs_yuli 20120514 end
//      commodity.setCommodityCode(cc.getCommodityHeader().getCommodityCode());
//      commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice()));
//      commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice()));
//      commodity.setReservedPrice(NumUtil.toString(price.getReservationPrice()));
//      commodity.setCommodityTaxType(NumUtil.toString(cc.getCommodityHeader().getCommodityTaxType()));
//      commodity.setCommodityPointRate(NumUtil.toString(cc.getCommodityHeader().getCommodityPointRate()));
//      commodity.setReviewScore(NumUtil.toString(cc.getReviewSummary().getReviewScore()));
//      commodity.setStockQuantity(NumUtil.toString(cc.getContainerAddInfo().getAvailableStockQuantity()));
//      commodity.setCampaignPeriod(price.isCampaign());
//      commodity.setPointPeriod(price.isPoint());
//      commodity.setDiscountPeriod(price.isDiscount());
//      commodity.setShopCode(cc.getCommodityHeader().getShopCode());
//      commodity.setShopName(cc.getShop().getShopName());
//      // add by yyq start 20130415
//      if (cc.getCommodityHeader().getImportCommodityType() != null) {
//        commodity.setImportCommodityType(cc.getCommodityHeader().getImportCommodityType());
//      }
//      if (cc.getCommodityHeader().getClearCommodityType() != null) {
//        commodity.setClearCommodityType(cc.getCommodityHeader().getClearCommodityType());
//      }
//      if (cc.getCommodityHeader().getReserveCommodityType1() != null) {
//        commodity.setReserveCommodityType1(cc.getCommodityHeader().getReserveCommodityType1());
//      }
//      if (cc.getCommodityHeader().getReserveCommodityType2() != null) {
//        commodity.setReserveCommodityType2(cc.getCommodityHeader().getReserveCommodityType2());
//      }
//      if (cc.getCommodityHeader().getReserveCommodityType3() != null) {
//        commodity.setReserveCommodityType3(cc.getCommodityHeader().getReserveCommodityType3());
//      }
//      if (cc.getCommodityHeader().getNewReserveCommodityType1() != null) {
//        commodity.setNewReserveCommodityType1(cc.getCommodityHeader().getNewReserveCommodityType1());
//      }
//      if (cc.getCommodityHeader().getNewReserveCommodityType2() != null) {
//        commodity.setNewReserveCommodityType2(cc.getCommodityHeader().getNewReserveCommodityType2());
//      }
//      if (cc.getCommodityHeader().getNewReserveCommodityType3() != null) {
//        commodity.setNewReserveCommodityType3(cc.getCommodityHeader().getNewReserveCommodityType3());
//      }
//      if (cc.getCommodityHeader().getNewReserveCommodityType4() != null) {
//        commodity.setNewReserveCommodityType4(cc.getCommodityHeader().getNewReserveCommodityType4());
//      }
//      if (cc.getCommodityHeader().getNewReserveCommodityType5() != null) {
//        commodity.setNewReserveCommodityType5(cc.getCommodityHeader().getNewReserveCommodityType5());
//      }
//      if (cc.getCommodityDetail().getInnerQuantity() != null) {
//        commodity.setInnerQuantity(cc.getCommodityDetail().getInnerQuantity());
//      }
//      // add by yyq end 20130415
//
//      if (price.getCampaignInfo() == null) {
//        commodity.setCampaignDiscountRate("");
//      } else {
//        commodity.setCampaignCode(price.getCampaignInfo().getCampaignCode());
//        commodity.setCampaignName(utilService.getNameByLanguage(price.getCampaignInfo().getCampaignName(), price.getCampaignInfo()
//            .getCampaignNameEn(), price.getCampaignInfo().getCampaignNameJp()));
//        commodity.setCampaignDiscountRate(NumUtil.toString(price.getCampaignInfo().getCampaignDiscountRate()));
//      }
//
//      if (price.isSale()) {
//        commodity.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
//      } else if (price.isDiscount()) {
//        commodity.setPriceMode(CommodityPriceType.DISCOUNT_PRICE.getValue());
//      } else if (price.isReserved()) {
//        commodity.setPriceMode(CommodityPriceType.RESERVATION_PRICE.getValue());
//      } else {
//        commodity.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
//      }
//
//      commodity.setStockStatus("");
//
//      rdList.add(commodity);
//    }
//    bean.setRdList(rdList);
//
//    // 优惠券
//    List<NewCouponHistory> nchList = customerSv.getCouponHistoryValidList(customerCode);
//    List<CouponHistoryListDetail> couponHistoryList = new ArrayList<CouponHistoryListDetail>();
//    List<CouponHistoryListDetail> couponFutureList = new ArrayList<CouponHistoryListDetail>();
//    String couponName = "";
//    for (NewCouponHistory cc : nchList) {
//      CouponHistoryListDetail couponHistoryListDetail = new CouponHistoryListDetail();
//      // 优惠券规则名称
//      couponName = utilService.getNameByLanguage(cc.getCouponName(), cc.getCouponNameEn(), cc.getCouponNameJp());
//      // 优惠券规则名称
//      couponHistoryListDetail.setCouponName(couponName);
//      // 优惠券发行类别
//      couponHistoryListDetail.setCouponIssueType(Long.toString(cc.getCouponIssueType()));
//      // 优惠券比例
//      couponHistoryListDetail.setCouponProportion(cc.getCouponProportion());
//      // 优惠金额
//      couponHistoryListDetail.setCouponAmount(NumUtil.toString(cc.getCouponAmount()));
//      // 优惠券利用开始日时
//      couponHistoryListDetail.setUseStartDatetime(DateUtil.toDateAndHourString(cc.getUseStartDatetime()));
//      // 优惠券利用结束日时
//      couponHistoryListDetail.setUseEndDatetime(DateUtil.toDateAndHourString(cc.getUseEndDatetime()));
//
//      if (DateUtil.isPeriodDate(cc.getUseStartDatetime(), cc.getUseEndDatetime())) {
//        couponHistoryList.add(couponHistoryListDetail);
//      } else {
//        couponFutureList.add(couponHistoryListDetail);
//      }
//    }
//    bean.setChldListSize(NumUtil.toLong(Integer.toString(couponHistoryList.size())));
//    bean.setChldList(couponHistoryList);
//    bean.setChldFutureList(couponFutureList);
    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }


  /**
   * 出荷状況を設定します。<br>
   * 
   * @param shippingStatusSummary
   *          出荷状況サマリー
   * @param shippingList
   *          出荷リスト
   * @return
   */
  private String getShippingStatus(String shippingStatusSummary, List<ShippingContainer> shippingList) {
    String resultShippingStatus = "";

    if (shippingStatusSummary.equals(ShippingStatusSummary.NOT_SHIPPED.getValue())
        || shippingStatusSummary.equals(ShippingStatusSummary.SHIPPED_ALL.getValue())
        || shippingStatusSummary.equals(ShippingStatusSummary.CANCELLED.getValue())) {
      if (shippingStatusSummary.equals(ShippingStatusSummary.NOT_SHIPPED.getValue())) {
        resultShippingStatus = ShippingStatusSummary.fromValue(ShippingStatusSummary.NOT_SHIPPED.getValue()).getName();
      } else if (shippingStatusSummary.equals(ShippingStatusSummary.SHIPPED_ALL.getValue())) {
        resultShippingStatus = ShippingStatus.fromValue(ShippingStatus.SHIPPED.getValue()).getName();
      } else if (shippingStatusSummary.equals(ShippingStatusSummary.CANCELLED.getValue())) {
        resultShippingStatus = ShippingStatus.fromValue(ShippingStatus.CANCELLED.getValue()).getName();
      }
      return resultShippingStatus;
    }
    String getShippingStatus = "";
    int shippingListSize = shippingList.size();
    int inProcessingCount = 0;
    int shippedCount = 0;
    for (ShippingContainer list : shippingList) {
      getShippingStatus = NumUtil.toString(list.getShippingHeader().getShippingStatus());
      if (list.getShippingHeader().getShippingStatus().equals(ShippingStatus.IN_PROCESSING.longValue())) {
        inProcessingCount++;
      } else if (list.getShippingHeader().getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())) {
        shippedCount++;
      }
    }
    if (shippingListSize > 1) {
      if ((shippingListSize > shippedCount) && shippedCount > 0) {
        resultShippingStatus = ShippingStatusSummary.fromValue(ShippingStatusSummary.PARTIAL_SHIPPED.getValue()).getName();
      } else if (inProcessingCount > 0) {
        resultShippingStatus = ShippingStatus.fromValue(ShippingStatus.IN_PROCESSING.getValue()).getName();
      }
    } else {
      if (getShippingStatus.equals(ShippingStatus.IN_PROCESSING.getValue())) {
        resultShippingStatus = ShippingStatus.fromValue(ShippingStatus.IN_PROCESSING.getValue()).getName();
      }
    }

    return resultShippingStatus;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * キャンセル完了表示
   */
  @Override
  public void prerender() {
    String[] args = getRequestParameter().getPathArgs();
    if (args.length > 0 && StringUtil.hasValue(args[0])) {
      if (args[0].equals("disabled")) {
        addErrorMessage(WebMessage.get(MypageErrorMessage.DISAPPROVE_CANCEL_ORDER));
      }
    }
  }

}
