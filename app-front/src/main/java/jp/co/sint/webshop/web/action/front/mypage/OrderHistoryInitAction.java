package jp.co.sint.webshop.web.action.front.mypage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.CustomerCancelableFlg;
import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.domain.ReturnStatusSummary;
import jp.co.sint.webshop.data.domain.SearchOrderDateType;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.communication.ReviewPostCustomerCountSearchCondition;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.OrderHeadline;
import jp.co.sint.webshop.service.order.OrderListSearchCondition;
import jp.co.sint.webshop.service.order.OrderSummary;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.OrderHistoryBean;
import jp.co.sint.webshop.web.bean.front.mypage.OrderHistoryBean.CommodityAndReviewBean;
import jp.co.sint.webshop.web.bean.front.mypage.OrderHistoryBean.OrdersHistoryCommodityDetail;
import jp.co.sint.webshop.web.bean.front.mypage.OrderHistoryBean.OrdersHistoryDetail;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.CompleteMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U2030610:ご注文履歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class OrderHistoryInitAction extends WebFrontAction<OrderHistoryBean> {

  private OrderListSearchCondition condition;

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new OrderListSearchCondition();
  }

  protected OrderListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected OrderListSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    OrderHistoryBean bean = getBean();
    if (bean == null) {
      bean = new OrderHistoryBean();
    }

    // 検索条件作成
    // セッションから顧客コードを取得
    String customerCode = "";
    FrontLoginInfo login = getLoginInfo();
    customerCode = login.getCustomerCode();

    // 顧客存在チェック
    CustomerService customerSv = ServiceLocator.getCustomerService(getLoginInfo());
    if (customerSv.isNotFound(customerCode) || customerSv.isInactive(customerCode)) {
      setNextUrl("/app/common/index");

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }
    condition.setCustomerCode(customerCode);
    condition.setCustomer(true);
    condition.setSearchListSort(OrderHistoryBean.ORDER_DATETIME_DESC);
    condition = getCondition();
    
    //默认近期订单(6个月内)
    String searchDateType = getRequestParameter().get("searchOrderDateType");
    if(StringUtil.isNullOrEmpty(searchDateType) || SearchOrderDateType.RECENT.getValue().equals(searchDateType)){
      condition.setOrderDatetimeStart(DateUtil.toDateString(DateUtil.addMonth(DateUtil.getSysdate(), -6)));
      bean.setSearchOrderDateType(SearchOrderDateType.RECENT.getValue());
    }else{
      bean.setSearchOrderDateType(SearchOrderDateType.ALL.getValue());
    }

    if (!StringUtil.isNullOrEmpty(getClient()) && !getClient().equals(ClientType.OTHER)) {
      WebshopConfig config = DIContainer.getWebshopConfig();
      condition.setPageSize(config.getMobilePageSize());
    }
    // 受注ステータス
    String[] param = getRequestParameter().getPathArgs();
    String searchType = "all";
    if (param.length > 0) {
      searchType = param[0];
    }
    bean.setSelectOrderStatusValue(searchType);

    // 検索条件生成
    createSearchCondition(searchType);
    if (searchType.equals("0")) {
      condition.setSearchListSort("orderNo0");
    } else if (searchType.equals("1")) {
      condition.setSearchListSort("orderNo1");
    }
    // 検索実行
    OrderService orderSv = ServiceLocator.getOrderService(getLoginInfo());
    SearchResult<OrderHeadline> result = orderSv.searchOrderList(condition);

    bean.getList().clear();

    // リスト生成
    bean.setPagerValue(PagerUtil.createValue(result));
    List<OrderHeadline> orderList = result.getRows();
    List<OrdersHistoryDetail> detailList = bean.getList();
    // 20111220 os013 add start
    CommunicationService cs = ServiceLocator.getCommunicationService(getLoginInfo());
    // 20111220 os013 add end
    for (OrderHeadline b : orderList) {
      OrdersHistoryDetail row = new OrdersHistoryDetail();
      row.setOrderNo(b.getOrderNo());
      row.setShopCode(b.getShopCode());
      row.setOrderDatetime(b.getOrderDatetime());
      // 10.1.3 10126 修正 ここから
      // row.setTotalPrice(NumUtil.toString(NumUtil.toLong(b.getTotalAmount())));
      // add by V10-CH start
      // row.setTotalPrice(NumUtil.toString(NumUtil.toLong(b.getTotalAmount()) +
      // b.getPaymentCommission()));
      BigDecimal outPrice = BigDecimal.ZERO;
      if (b.getOuterCardUsePrice() != null ) {
        outPrice = b.getOuterCardUsePrice();
      }
      row.setTotalPrice(NumUtil.toString(BigDecimalUtil.add(NumUtil.parse(b.getTotalAmount()), b.getPaymentCommission().add(outPrice))));
      // add by lc 2012-02-01 start
      String totalOrderPrice = row.getTotalPrice();
      if (StringUtil.hasValue(b.getDiscountPrice())) {
        totalOrderPrice = (new BigDecimal(totalOrderPrice).subtract(new BigDecimal(b.getDiscountPrice()))).toString();
      }
      row.setTotalPrice(totalOrderPrice);
      // add by lc 2012-02-01 end

      // add by V10-CH end
      // 10.1.3 10126 修正 ここまで
      row.setUpdatedDatetime(b.getUpdatedDatetime());

      List<OrderDetail> orderDetailList = orderSv.getOrderDetailList(b.getOrderNo());
      List<OrdersHistoryCommodityDetail> commodityList = new ArrayList<OrdersHistoryCommodityDetail>();
      // 20111220 os013 add start
      OrderSummary orderSummary = orderSv.getOrderSummary(b.getOrderNo());
      List<CommodityAndReviewBean> coList = new ArrayList<CommodityAndReviewBean>();
      // 20111220 os013 add end
      // 20130801 txw update start
      OrderContainer container = orderSv.getOrder(b.getOrderNo());
      String shippingNo = container.getShippings().get(0).getShippingHeader().getShippingNo();
      List<ShippingDetail> orderShippingList = orderSv.getShippingDetailList(shippingNo);
      for (OrderDetail dl : orderDetailList) {
        for (ShippingDetail sd : orderShippingList) {
          if (dl.getSkuCode().equals(sd.getSkuCode()) && !sd.getCommodityType().equals(CommodityType.PROPAGANDA.longValue())) {
            OrdersHistoryCommodityDetail commodity = new OrdersHistoryCommodityDetail();
            // 20111220 os013 add start
            // 将商品code、名字、是否可以评论存入CommodityAndReviewBean
            CommodityAndReviewBean commodityAndReviewBean = new CommodityAndReviewBean();
            commodityAndReviewBean.setCommodityCode(dl.getCommodityCode());
            commodityAndReviewBean.setCommodityName(dl.getCommodityName());
            // 20120113 ysy add start
            // 读取sku编号
            commodityAndReviewBean.setSkuCode(dl.getSkuCode());
            // 20120113 ysy add end
            commodityAndReviewBean.setDisplayReviewButtonFlg(false);
            // 判断评论的标准:订单发货状态为已发货
            if (CommodityType.GENERALGOODS.longValue().equals(dl.getCommodityType())
                && ShippingStatus.SHIPPED.getValue().equals(b.getShippingStatusSummary())) {

              ReviewPostCustomerCountSearchCondition reviewPostCustomerCountCondition = new ReviewPostCustomerCountSearchCondition();
              reviewPostCustomerCountCondition.setShopCode(b.getShopCode());
              reviewPostCustomerCountCondition.setCommodityCode(dl.getCommodityCode());
              reviewPostCustomerCountCondition.setCustomerCode(getLoginInfo().getCustomerCode());
              // 20111220 os013 add start
              reviewPostCustomerCountCondition.setOrderNo(b.getOrderNo());
              // 20111220 0s013 add end
              Long count = cs.getReviewPostCount(reviewPostCustomerCountCondition);
              // 判断每条订单的每个商品我评论了几次
              if (count.intValue() < 1) {
                commodityAndReviewBean.setDisplayReviewButtonFlg(true);
              }
            }
            // 20111220 os013 add end
            commodity.setCommodityName(StringUtil.getHeadline(dl.getCommodityName(), 20));
            // add by lc 2012-02-01 start
            commodity.setCommodityCode(dl.getCommodityCode());
            commodity.setShopCode(dl.getShopCode());
            // add by lc 2012-02-01 end

            // add by yyq 2012-02-19 start
            commodity.setDisplayReviewButtonFlg(commodityAndReviewBean.isDisplayReviewButtonFlg());
            commodity.setCommodityAmount(NumUtil.toString(sd.getPurchasingAmount()));
            commodity.setSkuCode(dl.getSkuCode());
            // add by yyq 2012-02-19 end

            commodity.setStandardDetail1Name(dl.getStandardDetail1Name());
            commodity.setStandardDetail2Name(dl.getStandardDetail2Name());

            // add by yyq 20120219 start
            // 2012/12/19 促销对应 ob update start
            // CatalogService service =
            // ServiceLocator.getCatalogService(getLoginInfo());
            // CommodityAvailability commodityAvailability =
            // service.isAvailable(dl.getShopCode(), dl.getSkuCode(), 1, false);
            // if
            // (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
            // commodity.setSale(true);
            // commodity.setDisplayCartButton(true);
            // } else {
            // commodity.setSale(false);
            // commodity.setDisplayCartButton(false);
            // }
            commodity.setCommoditySet(displaySetButton(dl.getShopCode(), dl.getCommodityCode(), dl.getSetCommodityFlg()));
            commodity.setGiftFlg(CommodityType.GIFT.longValue().equals(dl.getCommodityType()));
            String diplayCommodityName = getDisplayName(dl.getCommodityName(), dl.getStandardDetail1Name(), dl
                .getStandardDetail2Name());
            commodity.setDisplayCartButton(checkAvailable(dl.getShopCode(), dl.getCommodityCode(), dl.getSkuCode(), dl
                .getCommodityType(), dl.getSetCommodityFlg(), 1L, diplayCommodityName, false));
            // 2012/12/19 促销对应 ob update end

            // add by yyq 20120219 end

            commodity.setSalePrice(NumUtil.toString(sd.getRetailPrice()));

            commodityList.add(commodity);
            // 20111220 os013 add start
            coList.add(commodityAndReviewBean);
            // 20111220 os013 add end
          }
        }
      }
      // 20111220 os013 add start
      row.setCommodityAndReviewBeanList(coList);
      // 20111220 os013 add end
      row.setCommodityList(commodityList);
      // M17N 10361 修正 ここから
      if (b.getOrderStatus().equals(OrderStatus.RESERVED.getValue())
          || b.getOrderStatus().equals(OrderStatus.PHANTOM_RESERVATION.getValue())) {
        row.setReserve(true);
      } else {
        row.setReserve(false);
      }
      // M17N 10361 修正 ここまで
      if (b.getReturnStatusSummary().equals(ReturnStatusSummary.NOT_RETURNED.getValue())) {
        row.setHasReturn(false);
      } else {
        row.setHasReturn(true);
      }
      row.setShippingStatus(setShippingStatus(b.getShippingStatusSummary(), orderSv.getShippingList(b.getOrderNo())));
      if (StringUtil.hasValue(b.getPaymentDate())) {
        row.setPaymentDate(b.getPaymentDate());
      }

      // add by lc 2012-02-01 start
      // 支払方法名称
      row.setPaymentMethodName(b.getPaymentMethodName());
      // add by lc 2012-02-01 end

      // キャンセルボタンの表示
      OrderHeader orderHeader = orderSv.getOrderHeader(b.getOrderNo());
      if (orderHeader.getPaymentStatus().equals(PaymentStatus.NOT_PAID.longValue())
          && orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())
          && DataTransportStatus.fromValue(orderHeader.getDataTransportStatus()).equals(DataTransportStatus.NOT_TRANSPORTED)) {
        ShopManagementService shopSv = ServiceLocator.getShopManagementService(getLoginInfo());
        Shop shop = shopSv.getShop(orderHeader.getShopCode());
        if (shop.getCustomerCancelableFlg().equals(CustomerCancelableFlg.ENABLED.longValue())) {
          row.setCancelStatus(true);
        }
      }
      if (orderSummary.getOrderStatus() != Long.parseLong(OrderStatus.CANCELLED.getValue())
          && orderSummary.getPaymentStatus() == Long.parseLong(PaymentStatus.NOT_PAID.getValue())
          && (orderSummary.getPaymentMethodType().equals(PaymentMethodType.ALIPAY.getValue()) 
              || orderSummary.getPaymentMethodType().equals(PaymentMethodType.CHINA_UNIONPAY.getValue())
              || orderSummary.getPaymentMethodType().equals(PaymentMethodType.OUTER_CARD.getValue())
              || orderSummary.getPaymentMethodType().equals(PaymentMethodType.INNER_CARD.getValue()))) {
        row.setPaymentFlg(true);
      } else {
        row.setPaymentFlg(false);
      }
      row.setPaymentFormObject(getPaymentFormObject(container));
      if (PaymentMethodType.ALIPAY.getValue().equals(container.getOrderHeader().getPaymentMethodType())) {
        row.setDisplayAlipayInfo(true);
      } else if (PaymentMethodType.CHINA_UNIONPAY.getValue().equals(container.getOrderHeader().getPaymentMethodType())
          || PaymentMethodType.OUTER_CARD.getValue().equals(container.getOrderHeader().getPaymentMethodType())
          || PaymentMethodType.INNER_CARD.getValue().equals(container.getOrderHeader().getPaymentMethodType())) {
        row.setDisplayChinapayInfo(true);
      }
      detailList.add(row);
    }

    bean.setList(detailList);

    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

  private void createSearchCondition(String searchType) {
    if (StringUtil.isNullOrEmpty(searchType) || searchType.equals("all") || searchType.equals("0") || searchType.equals("1")) {
      // 全て
      String[] values = new String[OrderStatus.values().length];
      int i = 0;
      for (OrderStatus status : OrderStatus.values()) {
        values[i] = status.getValue();
        i++;
      }
      condition.setOrderStatus(values);
      // 出荷ステータス:未出荷/一部出荷/全出荷
      condition.setShippingStatusSummary(ShippingStatusSummary.SHIPPED_ALL.getValue(), ShippingStatusSummary.IN_PROCESSING
          .getValue(), ShippingStatusSummary.PARTIAL_SHIPPED.getValue(), ShippingStatusSummary.NOT_SHIPPED.getValue());
      // 返品状況:返品なし/一部返品/全返品
      condition.setReturnStatusSummary(new String[] {
          ReturnStatusSummary.NOT_RETURNED.getValue(), ReturnStatusSummary.PARTIAL_RETURNED.getValue(),
          ReturnStatusSummary.RETURNED_ALL.getValue()
      });
      condition.setPaymentStatus("");
      condition.setSearchDataTransportFlg(true);
      condition.setSearchFixedSalesDataFlg(true);
    } else if (searchType.equals("reserve")) {
      // 予約と仮予約場合
      // M17N 10361 修正 ここから
      String[] orderList = {
          OrderStatus.RESERVED.getValue(), OrderStatus.PHANTOM_RESERVATION.getValue()
      };
      condition.setOrderStatus(orderList);
      // M17N 10361 修正 ここまで
      // 出荷ステータス:未出荷
      condition.setShippingStatusSummary(ShippingStatusSummary.NOT_SHIPPED.getValue());
      // 返品状況:返品なし
      condition.setReturnStatusSummary(new String[] {
        ReturnStatusSummary.NOT_RETURNED.getValue()
      });
      condition.setPaymentStatus("");
      condition.setSearchDataTransportFlg(true);
      condition.setSearchFixedSalesDataFlg(true);
    } else if (searchType.equals("cancelable")) {
      // キャンセル可能な受注
      // 受注ステータス：受注/予約、入金ステータス：未払い、出荷ステータス：未出荷 返品ステータス：返品なし
      String[] values = new String[2];
      values[0] = OrderStatus.ORDERED.getValue();
      values[1] = OrderStatus.RESERVED.getValue();
      condition.setOrderStatus(values);
      condition.setPaymentStatus(PaymentStatus.NOT_PAID.getValue());
      condition.setReturnStatusSummary(new String[] {
        ReturnStatusSummary.NOT_RETURNED.getValue()
      });
      condition.setSearchDataTransportFlg(false);
      condition.setSearchFixedSalesDataFlg(false);
      condition.setShippingStatusSummary(ShippingStatusSummary.NOT_SHIPPED.getValue());
      condition.setCustomerCancelableFlg(true);
    } else {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }

  }

  /**
   * キャンセル完了表示
   */
  @Override
  public void prerender() {
    String[] args = getRequestParameter().getPathArgs();
    if (args.length > 1 && StringUtil.hasValue(args[1])) {
      if (args[1].equals("canceled")) {
        addInformationMessage(WebMessage.get(CompleteMessage.CANCEL_COMPLETE, Messages
            .getString("web.action.front.mypage.OrderHistoryInitAction.0")));
      } else {
        throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
      }
    }
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
   * 出荷状況を設定します。<br>
   * 
   * @param shippingStatusSummary
   *          出荷状況サマリー
   * @param shippingList
   *          出荷リスト
   * @return
   */
  private String setShippingStatus(String shippingStatusSummary, List<ShippingContainer> shippingList) {
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

  // 20110217 yyq add start
  private Object getPaymentFormObject(OrderContainer container) {
    Object result = null;
    OrderHeader oh = container.getOrderHeader();
    PaymentMethodType pmt = PaymentMethodType.fromValue(oh.getPaymentMethodType());
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    if (pmt == PaymentMethodType.ALIPAY) {
      result = service.getAlipayBean(container);

    } else if (pmt == PaymentMethodType.CHINA_UNIONPAY || pmt == PaymentMethodType.OUTER_CARD || pmt == PaymentMethodType.INNER_CARD) {
      result = service.getChinapayBean(container);
    }
    return result;
  }
  // 20110217 yyq add end
}
