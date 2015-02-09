package jp.co.sint.webshop.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.configure.TmallSendMailConfig;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.ActivateCouponProcedure;
import jp.co.sint.webshop.data.ActivatePointProcedure;
import jp.co.sint.webshop.data.ArchivedOrderProcedure;
import jp.co.sint.webshop.data.ConcurrencyFailureException;
import jp.co.sint.webshop.data.CouponCancelIssueProcedure;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.NewCouponIssueProcedure;
import jp.co.sint.webshop.data.NewCouponReIssueProcedure;
import jp.co.sint.webshop.data.PointInsertProcedure;
import jp.co.sint.webshop.data.PointUpdateProcedure;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.StockManager;
import jp.co.sint.webshop.data.StockUnit;
import jp.co.sint.webshop.data.TransactionManager;
import jp.co.sint.webshop.data.dao.CampaignDao;
import jp.co.sint.webshop.data.dao.CommodityDetailDao;
import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.dao.CouponIssueDao;
import jp.co.sint.webshop.data.dao.CouponRuleDao;
import jp.co.sint.webshop.data.dao.CustomerCardUseInfoDao;
import jp.co.sint.webshop.data.dao.CustomerCouponDao;
import jp.co.sint.webshop.data.dao.CustomerDao;
import jp.co.sint.webshop.data.dao.CustomerGroupCampaignDao;
import jp.co.sint.webshop.data.dao.CustomerGroupDao;
import jp.co.sint.webshop.data.dao.FriendCouponIssueHistoryDao;
import jp.co.sint.webshop.data.dao.JdOrderDetailDao;
import jp.co.sint.webshop.data.dao.JdOrderHeaderDao;
import jp.co.sint.webshop.data.dao.JdShippingDetailDao;
import jp.co.sint.webshop.data.dao.JdShippingHeaderDao;
import jp.co.sint.webshop.data.dao.JdStockAllocationDao;
import jp.co.sint.webshop.data.dao.JdSuitCommodityDao;
import jp.co.sint.webshop.data.dao.NewCouponHistoryDao;
import jp.co.sint.webshop.data.dao.NewCouponRuleDao;
import jp.co.sint.webshop.data.dao.OrderDetailDao;
import jp.co.sint.webshop.data.dao.OrderHeaderDao;
import jp.co.sint.webshop.data.dao.PaymentMethodDao;
import jp.co.sint.webshop.data.dao.PointHistoryDao;
import jp.co.sint.webshop.data.dao.PointRuleDao;
import jp.co.sint.webshop.data.dao.ReturnHeaderDao;
import jp.co.sint.webshop.data.dao.ShippingDetailCompositionDao;
import jp.co.sint.webshop.data.dao.ShippingDetailDao;
import jp.co.sint.webshop.data.dao.ShippingHeaderDao;
import jp.co.sint.webshop.data.dao.ShopDao;
import jp.co.sint.webshop.data.dao.StockDao;
import jp.co.sint.webshop.data.dao.StockHolidayDao;
import jp.co.sint.webshop.data.dao.TmallOrderDetailDao;
import jp.co.sint.webshop.data.dao.TmallOrderHeaderDao;
import jp.co.sint.webshop.data.dao.TmallShippingDetailDao;
import jp.co.sint.webshop.data.dao.TmallShippingHeaderDao;
import jp.co.sint.webshop.data.dao.TmallStockAllocationDao;
import jp.co.sint.webshop.data.dao.TmallSuitCommodityDao;
import jp.co.sint.webshop.data.dao.UntrueOrderWordDao;
import jp.co.sint.webshop.data.domain.ActivityStatus;
import jp.co.sint.webshop.data.domain.AdvanceLaterFlg;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.CouponFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.CouponUsedFlg;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.DiscountType;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.InvoiceType;
import jp.co.sint.webshop.data.domain.LoginType;
import jp.co.sint.webshop.data.domain.MailType;
import jp.co.sint.webshop.data.domain.MediaType;
import jp.co.sint.webshop.data.domain.OrderDownLoadStatus;
import jp.co.sint.webshop.data.domain.OrderFlg;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.PointIssueStatus;
import jp.co.sint.webshop.data.domain.PointIssueType;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.domain.UseStatus;
import jp.co.sint.webshop.data.dto.Area;
import jp.co.sint.webshop.data.dto.BatchTime;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignDoings;
import jp.co.sint.webshop.data.dto.City;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CouponIssue;
import jp.co.sint.webshop.data.dto.CouponRule;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerCardInfo;
import jp.co.sint.webshop.data.dto.CustomerCardUseInfo;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.data.dto.CustomerGroup;
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.data.dto.CustomerVatInvoice;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.FreePostageRule;
import jp.co.sint.webshop.data.dto.FriendCouponIssueHistory;
import jp.co.sint.webshop.data.dto.FriendCouponUseHistory;
import jp.co.sint.webshop.data.dto.JdArea;
import jp.co.sint.webshop.data.dto.JdCity;
import jp.co.sint.webshop.data.dto.JdOrderDetail;
import jp.co.sint.webshop.data.dto.JdOrderHeader;
import jp.co.sint.webshop.data.dto.JdShippingDetail;
import jp.co.sint.webshop.data.dto.JdShippingDetailComposition;
import jp.co.sint.webshop.data.dto.JdShippingHeader;
import jp.co.sint.webshop.data.dto.JdStockAllocation;
import jp.co.sint.webshop.data.dto.JdSuitCommodity;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.data.dto.NewCouponHistoryUseInfo;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.OrderCampaign;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.OrderInvoice;
import jp.co.sint.webshop.data.dto.OrderMedia;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.data.dto.PointHistory;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.data.dto.RespectiveMailqueue;
import jp.co.sint.webshop.data.dto.ReturnHeader;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingDetailComposition;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.ShippingRealityDetail;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.data.dto.SmsTemplateDetail;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.data.dto.TmallOrderDetail;
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.data.dto.TmallShippingDetail;
import jp.co.sint.webshop.data.dto.TmallShippingDetailComposition;
import jp.co.sint.webshop.data.dto.TmallShippingHeader;
import jp.co.sint.webshop.data.dto.TmallStockAllocation;
import jp.co.sint.webshop.data.dto.TmallSuitCommodity;
import jp.co.sint.webshop.data.dto.UntrueOrderWord;
import jp.co.sint.webshop.data.hibernate.TransactionManagerImpl;
import jp.co.sint.webshop.mail.MailInfo;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.MyCouponInfo;
import jp.co.sint.webshop.service.OptionalCommodity;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.StockService;
import jp.co.sint.webshop.service.alipay.AlipayManager;
import jp.co.sint.webshop.service.alipay.PaymentAlipayResult;
import jp.co.sint.webshop.service.alipay.PaymentAlipayResultBean;
import jp.co.sint.webshop.service.campain.CampaignMain;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeBase;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeCreditCard;
import jp.co.sint.webshop.service.catalog.CatalogQuery;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.chinapay.ChinapayManager;
import jp.co.sint.webshop.service.chinapay.ChinapayWapManager;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayResult;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayResultBean;
import jp.co.sint.webshop.service.communication.FreePostageListSearchCondition;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerCouponInfo;
import jp.co.sint.webshop.service.event.OrderEvent;
import jp.co.sint.webshop.service.event.OrderEventType;
import jp.co.sint.webshop.service.event.OrderListener;
import jp.co.sint.webshop.service.event.PaymentEvent;
import jp.co.sint.webshop.service.event.PaymentEventType;
import jp.co.sint.webshop.service.event.PaymentListener;
import jp.co.sint.webshop.service.event.ServiceEventHandler;
import jp.co.sint.webshop.service.event.ShippingEvent;
import jp.co.sint.webshop.service.event.ShippingEventType;
import jp.co.sint.webshop.service.event.ShippingListener;
import jp.co.sint.webshop.service.event.StockEvent;
import jp.co.sint.webshop.service.event.StockEventType;
import jp.co.sint.webshop.service.event.StockListener;
import jp.co.sint.webshop.service.jd.order.JdOrderSearchQuery;
import jp.co.sint.webshop.service.order.BookSalesContainer;
import jp.co.sint.webshop.service.order.CommodityOfCartInfo;
import jp.co.sint.webshop.service.order.DeliveryInfo;
import jp.co.sint.webshop.service.order.InputShippingReport;
import jp.co.sint.webshop.service.order.MyOrder;
import jp.co.sint.webshop.service.order.MyOrderListSearchCondition;
import jp.co.sint.webshop.service.order.MyOrderListSearchQuery;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.OrderCountSearchCondition;
import jp.co.sint.webshop.service.order.OrderCountSearchQuery;
import jp.co.sint.webshop.service.order.OrderHeadline;
import jp.co.sint.webshop.service.order.OrderIdentifier;
import jp.co.sint.webshop.service.order.OrderListSearchCondition;
import jp.co.sint.webshop.service.order.OrderListSearchQuery;
import jp.co.sint.webshop.service.order.OrderMailType;
import jp.co.sint.webshop.service.order.OrderPaymentInfo;
import jp.co.sint.webshop.service.order.OrderPaymentInfoQuery;
import jp.co.sint.webshop.service.order.OrderPropagandaCommodityInfo;
import jp.co.sint.webshop.service.order.OrderSearchQuery;
import jp.co.sint.webshop.service.order.OrderServiceQuery;
import jp.co.sint.webshop.service.order.OrderSumRetailPriceSearchQuery;
import jp.co.sint.webshop.service.order.OrderSummary;
import jp.co.sint.webshop.service.order.PaymentProviderManager;
import jp.co.sint.webshop.service.order.PaymentResult;
import jp.co.sint.webshop.service.order.ReturnInfo;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.service.order.ShippingCountSearchQuery;
import jp.co.sint.webshop.service.order.ShippingList;
import jp.co.sint.webshop.service.order.ShippingListSearchCondition;
import jp.co.sint.webshop.service.order.ShippingListSearchQuery;
import jp.co.sint.webshop.service.order.UntrueOrderWordResult;
import jp.co.sint.webshop.service.order.UntrueOrderWordSearchCondition;
import jp.co.sint.webshop.service.order.UntrueOrderWordSearchQuery;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.PaymentErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.service.sms.SmsingServiceQuery;
import jp.co.sint.webshop.service.tmall.TmallCommoditySku;
import jp.co.sint.webshop.service.tmall.TmallOrdersDownloadHandling;
import jp.co.sint.webshop.service.tmall.TmallService;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateRange;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SystemUserAgent;
import jp.co.sint.webshop.utility.UserAgent;
import jp.co.sint.webshop.utility.UserAgentManager;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.NumberLimitPolicy;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.validation.ValidatorUtil;

import org.apache.log4j.Logger;

@SuppressWarnings("unused")
public class OrderServiceImpl extends AbstractServiceImpl implements OrderService {
  
  // 2014/06/13 库存更新对应 ob_李 add start
  private List<StockListener> stockListeners;

  public void setStockListeners(List<StockListener> stockListeners) {
    this.stockListeners = stockListeners;
  }

  public void performStockEvent(StockEvent event, StockEventType type) {
    ServiceEventHandler.execute(this.stockListeners, event, type);
  }
  //2014/06/13 库存更新对应 ob_李 add end

  private List<OrderListener> orderListeners;

  private List<ShippingListener> shippingListeners;

  private List<PaymentListener> paymentListeners;

  public void setOrderListeners(List<OrderListener> orderListeners) {
    this.orderListeners = orderListeners;
  }

  public void setShippingListeners(List<ShippingListener> shippingListeners) {
    this.shippingListeners = shippingListeners;
  }

  public void setPaymentListeners(List<PaymentListener> paymentListeners) {
    this.paymentListeners = paymentListeners;
  }

  /** uid */
  private static final long serialVersionUID = 1L;

  public ServiceResult cancel(OrderIdentifier orderIdentifier) {
    Logger logger = Logger.getLogger(this.getClass());
    String orderNo = orderIdentifier.getOrderNo();
    ServiceResultImpl result = new ServiceResultImpl();
    CustomerCardUseInfoDao useInfoDao = DIContainer.getDao(CustomerCardUseInfoDao.class);
    List<CustomerCardUseInfo> useInfoList = useInfoDao.loadByOrderNo(orderNo);
    
    OrderHeaderDao dao = DIContainer.getDao(OrderHeaderDao.class);
    OrderHeader orgOrderHeader = dao.load(orderNo);

    if (orgOrderHeader == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    OrderDetailDao oDetailDao = DIContainer.getDao(OrderDetailDao.class);
    List<OrderDetail> orgOrderDetails = oDetailDao.findByQuery(OrderServiceQuery.ORDER_DETAIL_LIST_QUERY, orderNo);

    if (orgOrderDetails == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

//    // 未入金チェック
//
//    if (!orgOrderHeader.getPaymentStatus().equals(PaymentStatus.NOT_PAID.longValue())) {
//      result.addServiceError(OrderServiceErrorContent.ORDER_CANCEL_DISABLED);
//      return result;
//    }
    
    // 未出荷チェック
    OrderSummary orderSummary = getOrderSummary(orderNo);
    if (!orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())) {
      result.addServiceError(OrderServiceErrorContent.ORDER_CANCEL_ERROR);
      return result;
    }

    if(PaymentStatus.NOT_PAID.equals(orgOrderHeader.getPaymentStatus())) {
      // 支付宝
      if (orgOrderHeader.getPaymentMethodType().equals(PaymentMethodType.ALIPAY.getValue())) {
        OrderContainer orderContainer = getOrder(orderNo);
        AlipayManager paymentManager = new AlipayManager();

        PaymentAlipayResult alipayResult = paymentManager.find(orderContainer);
        // 交易成功
        if (alipayResult.getResultBean().getIsSuccess().equals("T")
            && alipayResult.getResultBean().getTradeStatus().equals("TRADE_SUCCESS")) {
          // 付款日设置
          ServiceResult setDateResult = setPaymentDate(orderIdentifier, DateUtil.parseString(alipayResult.getResultBean()
              .getGmtPayment(), DateUtil.ALIPAY_DATE_FORMAT));

          if (setDateResult.hasError()) {
            return setDateResult;
          }
          result.addServiceError(OrderServiceErrorContent.ORDER_CANCEL_DISABLED);
          return result;
        } else {
          // 交易出错或者交易不存在关闭交易
          if (!(alipayResult.getResultBean().getError() != null && alipayResult.getResultBean().getError().equals("TRADE_NOT_EXIST"))) {
            // 关闭交易
            paymentManager.close(orderContainer);
          }
        }
      } else if (orgOrderHeader.getPaymentMethodType().equals(PaymentMethodType.CHINA_UNIONPAY.getValue())) {
        OrderContainer orderContainer = getOrder(orderNo);
        ChinapayManager chinapayManager = new ChinapayManager();
        PaymentChinapayResult chinaResult = chinapayManager.find(orderContainer);
        if (chinaResult.getResultBean().getResponseCode().equals("0")) {
          if (chinaResult.getResultBean().getTransactionStatus().equals("1001")) {
            // 设定订单的付款日
            ServiceResult setDateResult = setPaymentDate(orderIdentifier, DateUtil.parseString(chinaResult.getResultBean()
                .getTransactionDate(), DateUtil.ALIPAY_DATE_FORMAT));
            if (setDateResult.hasError()) {
              return setDateResult;
            }
            result.addServiceError(OrderServiceErrorContent.ORDER_CANCEL_DISABLED);
            return result;
          } else {
            // 订单取消
          }
        } else {
          // result.addServiceError(OrderServiceErrorContent.ORDER_CANCEL_DISABLED);
          // return result;
        }
      }
    }

    List<StockUnit> stockUnitList = new ArrayList<StockUnit>();

    List<String> skuList = new ArrayList<String>();
    OrderContainer orgOrderContainer = this.getOrder(orderNo);
    for (ShippingContainer shippingContainer : orgOrderContainer.getShippings()) {
      for (ShippingDetail shippingDetail : shippingContainer.getShippingDetails()) {
        if (SetCommodityFlg.OBJECTIN.longValue().equals(shippingDetail.getSetCommodityFlg())) {
          List<ShippingDetailComposition> compositionList = shippingContainer.getShippingDetailContainerMap().get(shippingDetail);
          for (ShippingDetailComposition composition : compositionList) {
            if (skuList.contains(composition.getChildSkuCode())) {
              for (StockUnit orgStock : stockUnitList) {
                if (orgStock.getSkuCode().equals(composition.getChildSkuCode())) {
                  orgStock.setQuantity(orgStock.getQuantity() + composition.getPurchasingAmount().intValue());
                }
              }
            } else {
              skuList.add(composition.getChildSkuCode());
              StockUnit stockUnit = new StockUnit();
              stockUnit.setShopCode(shippingDetail.getShopCode());
              stockUnit.setSkuCode(composition.getChildSkuCode());
              stockUnit.setQuantity(composition.getPurchasingAmount().intValue());
              stockUnit.setLoginInfo(this.getLoginInfo());
              stockUnitList.add(stockUnit);
            }
          }
        } else {
          if (skuList.contains(shippingDetail.getSkuCode())) {
            for (StockUnit orgStock : stockUnitList) {
              if (orgStock.getSkuCode().equals(shippingDetail.getSkuCode())) {
                orgStock.setQuantity(orgStock.getQuantity() + shippingDetail.getPurchasingAmount().intValue());
              }
            }
          } else {
            skuList.add(shippingDetail.getSkuCode());
            StockUnit stockUnit = new StockUnit();
            stockUnit.setShopCode(shippingDetail.getShopCode());
            stockUnit.setSkuCode(shippingDetail.getSkuCode());
            stockUnit.setQuantity(shippingDetail.getPurchasingAmount().intValue());
            stockUnit.setLoginInfo(this.getLoginInfo());
            stockUnitList.add(stockUnit);
          }
        }
      }
    }
    Collections.sort(stockUnitList);

    TransactionManager manager = DIContainer.getTransactionManager();

    try {
      manager.begin(this.getLoginInfo());
      Long orderStatus = orgOrderHeader.getOrderStatus();

      orgOrderHeader.setOrderStatus(OrderStatus.CANCELLED.longValue());
      //orgOrderHeader.setUpdatedDatetime(orderIdentifier.getUpdatedDatetime());
      manager.update(orgOrderHeader);

      if (useInfoList != null && useInfoList.size() > 0 ) {
        for (CustomerCardUseInfo useInfo : useInfoList) {
          useInfo.setUseStatus(1L);
          manager.update(useInfo);
        }
      }
      
      List<CustomerCoupon> ccl = getOrderUsedCoupon(orderNo);
      for (CustomerCoupon cc : ccl) {
        cc.setUseDate(null);
        cc.setUseFlg(CouponUsedFlg.ENABLED.longValue());
        cc.setOrderNo("");
        manager.update(cc);
      }

      CustomerCoupon cc = getOldGetCoupon(orderNo);
      if (cc != null) {
        cc.setUseFlg(CouponUsedFlg.DISABLED.longValue());
        manager.update(cc);
      }
      // update: ポイント履歴（該当受注のポイント履歴を無効化）

      PointHistoryDao pointHistoryDao = DIContainer.getDao(PointHistoryDao.class);
      List<PointHistory> pointHistoryList = pointHistoryDao.findByQuery(OrderServiceQuery.GET_POINT_HISTORY_BY_ORDER_NO, orderNo);
      for (PointHistory pointHistory : pointHistoryList) {
        pointHistory.setPointIssueStatus(NumUtil.toLong(PointIssueStatus.DISABLED.getValue()));
        pointHistory.setDescription(Messages.getString("service.impl.OrderServiceImpl.0"));
        PointUpdateProcedure pointUpdate = new PointUpdateProcedure(pointHistory);
        manager.executeProcedure(pointUpdate);
      }

      // 使用优惠券的取消
      SimpleQuery query = new SimpleQuery(OrderServiceQuery.CANCEL_COUPON_USED, orderNo);
      manager.executeUpdate(query);
      
      // 发行优惠券取消
      CouponCancelIssueProcedure couponIssue = new CouponCancelIssueProcedure(orgOrderHeader.getOrderNo(), orgOrderHeader.getUpdatedUser(), orgOrderHeader.getCustomerCode());
      
      manager.executeProcedure(couponIssue);

      StockManager stockManager = manager.getStockManager();

      boolean successUpdateStock = false;
      if (orderStatus.equals(OrderStatus.ORDERED.longValue())) {
        successUpdateStock = stockManager.deallocate(stockUnitList);

      } else if (orderStatus.equals(OrderStatus.RESERVED.longValue())) {
        successUpdateStock = stockManager.cancelReserving(stockUnitList);
      } else if (orderStatus.equals(OrderStatus.PHANTOM_ORDER.longValue())) {
        successUpdateStock = stockManager.deallocate(stockUnitList);
      } else if (orderStatus.equals(OrderStatus.PHANTOM_RESERVATION.longValue())) {
        successUpdateStock = stockManager.cancelReserving(stockUnitList);
      } else if (orderStatus.equals(OrderStatus.CANCELLED.longValue())) {
        result.addServiceError(OrderServiceErrorContent.ALREADY_CANCELED_ERROR);
        manager.rollback();
        return result;

      } else {
        result.addServiceError(OrderServiceErrorContent.ORDER_CANCEL_ERROR);
        manager.rollback();
        return result;
      }

      if (!successUpdateStock) {
        manager.rollback();
        result.addServiceError(OrderServiceErrorContent.ORDER_CANCEL_ERROR);
        return result;
      }

      for (ShippingContainer shippingContainer : getShippingList(orderNo)) {
        shippingContainer.getShippingHeader().setShippingStatus(ShippingStatus.CANCELLED.longValue());
        manager.update(shippingContainer.getShippingHeader());
      }

      // 支払方法情報を生成

      CashierPayment cashierPayment = new CashierPayment();
      cashierPayment.setShopCode(orgOrderHeader.getShopCode());
      CashierPaymentTypeBase cashierPaymentType = new CashierPaymentTypeBase();
      cashierPaymentType.setPaymentMethodCode(Long.toString(orgOrderHeader.getPaymentMethodNo()));
      cashierPaymentType.setPaymentMethodName(orgOrderHeader.getPaymentMethodName());
      cashierPaymentType.setPaymentMethodType(orgOrderHeader.getPaymentMethodType());
      cashierPaymentType.setPaymentCommission(orgOrderHeader.getPaymentCommission());
      cashierPayment.setSelectPayment(cashierPaymentType);

      // 与信キャンセル

      result = cancelPayment(getOrder(orderNo));
      if (result.hasError()) {
        manager.rollback();
        return result;
      }
      manager.commit();

      performOrderEvent(new OrderEvent(orgOrderHeader.getOrderNo(),cashierPayment), OrderEventType.CANCELLED);

    } catch (ConcurrencyFailureException e) {
      logger.error(e);
      manager.rollback();
      throw e;
    } catch (RuntimeException e) {
      manager.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }

    return result;
  }
  
  public ServiceResult tmallCancel(OrderIdentifier orderIdentifier) {
    Logger logger = Logger.getLogger(this.getClass());
    CatalogService service = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
    StockService stockService = ServiceLocator.getStockService(ServiceLoginInfo.getInstance());
    String orderNo = orderIdentifier.getOrderNo();
    ServiceResultImpl result = new ServiceResultImpl();
    TmallOrderHeaderDao dao = DIContainer.getDao(TmallOrderHeaderDao.class);
    TmallOrderHeader orgOrderHeader = dao.load(orderNo);

    if (orgOrderHeader == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    TmallOrderDetailDao oDetailDao = DIContainer.getDao(TmallOrderDetailDao.class);
    List<TmallOrderDetail> orgOrderDetails = oDetailDao.findByQuery(OrderServiceQuery.TMALL_ORDER_DETAIL_LIST_QUERY, orderNo);

    if (orgOrderDetails == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    // 未出荷チェック
    OrderSummary orderSummary = getOrderSummary(orderNo);
    if (!orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())) {
      result.addServiceError(OrderServiceErrorContent.ORDER_CANCEL_ERROR);
      return result;
    }

    TransactionManager manager = DIContainer.getTransactionManager();

    try {
      manager.begin(this.getLoginInfo());
      
      List<StockUnit> stockUnitList = new ArrayList<StockUnit>();
      List<String> skuList = new ArrayList<String>();
      OrderContainer orgOrderContainer = this.getTmallOrder(orderNo);
      for (ShippingContainer shippingContainer : orgOrderContainer.getShippings()) {
        for (TmallShippingDetail shippingDetail : shippingContainer.getTmallShippingDetails()) {
          
          CommodityInfo commodityInfo = service.getCCommodityInfo(shippingDetail.getShopCode(), shippingDetail.getSkuCode());
          CCommodityHeader cch = commodityInfo.getCheader();
          CCommodityDetail ccd = commodityInfo.getCdetail();
          
//          List<Object> updateTSAParams = new ArrayList<Object>();
//          updateTSAParams.add(shippingDetail.getPurchasingAmount());
//          updateTSAParams.add(this.getLoginInfo().getRecordingFormat());
//          updateTSAParams.add(DateUtil.getSysdate());
//          updateTSAParams.add("00000000");
//          updateTSAParams.add(shippingDetail.getSkuCode());
          if (SetCommodityFlg.OBJECTIN.longValue().equals(cch.getSetCommodityFlg())  ) {
            TmallSuitCommodityDao tsaDao = DIContainer.getDao(TmallSuitCommodityDao.class);
            TmallSuitCommodity tsa = tsaDao.load( shippingDetail.getSkuCode());
            tsa.setAllocatedQuantity(tsa.getAllocatedQuantity() - shippingDetail.getPurchasingAmount());
            manager.update(tsa);
            //manager.executeUpdate(OrderServiceQuery.UPDATE_TMALL_SUIT_COMMODITY_SQL, updateTSAParams);
          
          } else if (StringUtil.hasValue(cch.getOriginalCommodityCode())) {  
            TmallStockAllocationDao tsaDao = DIContainer.getDao(TmallStockAllocationDao.class);
            TmallStockAllocation tsa = tsaDao.load("00000000", shippingDetail.getSkuCode());
            tsa.setAllocatedQuantity(tsa.getAllocatedQuantity() - shippingDetail.getPurchasingAmount()*cch.getCombinationAmount());
            manager.update(tsa);
            //manager.executeUpdate(OrderServiceQuery.UPDATE_TMALL_STOCK_ALLOCATION_SQL, updateTSAParams);
            
          } else {
            if (skuList.contains(shippingDetail.getSkuCode())) {
              for (StockUnit orgStock : stockUnitList) {
                if (orgStock.getSkuCode().equals(shippingDetail.getSkuCode())) {
                  orgStock.setQuantity(orgStock.getQuantity() + shippingDetail.getPurchasingAmount().intValue());
                }
              }
            } else {
              skuList.add(shippingDetail.getSkuCode());
              StockUnit stockUnit = new StockUnit();
              stockUnit.setShopCode(shippingDetail.getShopCode());
              stockUnit.setSkuCode(shippingDetail.getSkuCode());
              stockUnit.setQuantity(shippingDetail.getPurchasingAmount().intValue());
              stockUnit.setLoginInfo(this.getLoginInfo());
              stockUnit.setMemo("TM");
              stockUnitList.add(stockUnit);
            }
          }
          
          stockService.tmallStockCyncroApi(cch.getTmallCommodityId(),shippingDetail.getPurchasingAmount(),cch.getCommodityCode());
        }
      }
      Collections.sort(stockUnitList);
      // 2012/12/03 ob udpate end


      
      Long orderStatus = orgOrderHeader.getOrderStatus();

      orgOrderHeader.setOrderStatus(OrderStatus.CANCELLED.longValue());
      //orgOrderHeader.setUpdatedDatetime(orderIdentifier.getUpdatedDatetime());
      manager.update(orgOrderHeader);

      StockManager stockManager = manager.getStockManager();

      boolean successUpdateStock = false;
      if (orderStatus.equals(OrderStatus.ORDERED.longValue())) {
        successUpdateStock = stockManager.deallocate(stockUnitList);

      } else if (orderStatus.equals(OrderStatus.RESERVED.longValue())) {
        successUpdateStock = stockManager.cancelReserving(stockUnitList);
        // v10-ch-pg added end
      } else if (orderStatus.equals(OrderStatus.PHANTOM_ORDER.longValue())) {
        successUpdateStock = stockManager.deallocate(stockUnitList);
        // v10-ch-pg added end
        // M17N 10361 追加 ここから
      } else if (orderStatus.equals(OrderStatus.PHANTOM_RESERVATION.longValue())) {
        successUpdateStock = stockManager.cancelReserving(stockUnitList);
        // M17N 10361 追加 ここまで
      } else if (orderStatus.equals(OrderStatus.CANCELLED.longValue())) {
        result.addServiceError(OrderServiceErrorContent.ALREADY_CANCELED_ERROR);
        manager.rollback();
        return result;

      } else {
        result.addServiceError(OrderServiceErrorContent.ORDER_CANCEL_ERROR);
        manager.rollback();
        return result;
      }

      if (!successUpdateStock) {
        manager.rollback();
        result.addServiceError(OrderServiceErrorContent.ORDER_CANCEL_ERROR);
        return result;
      }

      for (ShippingContainer shippingContainer : getTmallShippingList(orderNo)) {
        shippingContainer.getTmallShippingHeader().setShippingStatus(ShippingStatus.CANCELLED.longValue());
        manager.update(shippingContainer.getTmallShippingHeader());
      }
      
      manager.commit();

      //performOrderEvent(new OrderEvent(orgOrderHeader.getOrderNo(),cashierPayment), OrderEventType.CANCELLED);

    } catch (ConcurrencyFailureException e) {
      logger.error(e);
      manager.rollback();
      throw e;
    } catch (RuntimeException e) {
      manager.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }

    return result;
  }
  
  
  public ServiceResult jdCancel(OrderIdentifier orderIdentifier) {
    Logger logger = Logger.getLogger(this.getClass());
    CatalogService service = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
    String orderNo = orderIdentifier.getOrderNo();
    ServiceResultImpl result = new ServiceResultImpl();
    JdOrderHeaderDao dao = DIContainer.getDao(JdOrderHeaderDao.class);
    JdOrderHeader orgOrderHeader = dao.load(orderNo);

    if (orgOrderHeader == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    JdOrderDetailDao oDetailDao = DIContainer.getDao(JdOrderDetailDao.class);
    List<JdOrderDetail> orgOrderDetails = oDetailDao.findByQuery(OrderServiceQuery.JD_ORDER_DETAIL_LIST_QUERY, orderNo);

    if (orgOrderDetails == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    // 未出荷チェック
    OrderSummary orderSummary = getOrderSummary(orderNo);
    if (!orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())) {
      result.addServiceError(OrderServiceErrorContent.ORDER_CANCEL_ERROR);
      return result;
    }

    TransactionManager manager = DIContainer.getTransactionManager();

    try {
      manager.begin(this.getLoginInfo());
    
      List<StockUnit> stockUnitList = new ArrayList<StockUnit>();
      List<String> skuList = new ArrayList<String>();
      OrderContainer orgOrderContainer = this.getJdOrder(orderNo);
      for (ShippingContainer shippingContainer : orgOrderContainer.getShippings()) {
        for (JdShippingDetail shippingDetail : shippingContainer.getJdShippingDetails()) {
          
          CommodityInfo commodityInfo = service.getCCommodityInfo(shippingDetail.getShopCode(), shippingDetail.getSkuCode());
          CCommodityHeader cch = commodityInfo.getCheader();
          CCommodityDetail ccd = commodityInfo.getCdetail();
          
          List<Object> updateTSAParams = new ArrayList<Object>();
          updateTSAParams.add(shippingDetail.getPurchasingAmount());
          updateTSAParams.add(this.getLoginInfo().getRecordingFormat());
          updateTSAParams.add(DateUtil.getSysdate());
          updateTSAParams.add("00000000");
          updateTSAParams.add(shippingDetail.getSkuCode());
          if (SetCommodityFlg.OBJECTIN.longValue().equals(cch.getSetCommodityFlg()) ) {
            JdSuitCommodityDao tsaDao = DIContainer.getDao(JdSuitCommodityDao.class);
            JdSuitCommodity tsa = tsaDao.load( shippingDetail.getSkuCode());
            tsa.setAllocatedQuantity(tsa.getAllocatedQuantity() - shippingDetail.getPurchasingAmount());
            manager.update(tsa);
            //manager.executeUpdate(OrderServiceQuery.UPDATE_TMALL_SUIT_COMMODITY_SQL, updateTSAParams);
          
          } else if (StringUtil.hasValue(cch.getOriginalCommodityCode())) {  
            JdStockAllocationDao tsaDao = DIContainer.getDao(JdStockAllocationDao.class);
            JdStockAllocation tsa = tsaDao.load("00000000", shippingDetail.getSkuCode());
            tsa.setAllocatedQuantity(tsa.getAllocatedQuantity() - shippingDetail.getPurchasingAmount()*cch.getCombinationAmount());
            manager.update(tsa);
            //manager.executeUpdate(OrderServiceQuery.UPDATE_JD_SUIT_COMMODITY_SQL, updateTSAParams);
            
          } else {
            if (skuList.contains(shippingDetail.getSkuCode())) {
              for (StockUnit orgStock : stockUnitList) {
                if (orgStock.getSkuCode().equals(shippingDetail.getSkuCode())) {
                  orgStock.setQuantity(orgStock.getQuantity() + shippingDetail.getPurchasingAmount().intValue());
                }
              }
            } else {
              skuList.add(shippingDetail.getSkuCode());
              StockUnit stockUnit = new StockUnit();
              stockUnit.setShopCode(shippingDetail.getShopCode());
              stockUnit.setSkuCode(shippingDetail.getSkuCode());
              stockUnit.setQuantity(shippingDetail.getPurchasingAmount().intValue());
              stockUnit.setLoginInfo(this.getLoginInfo());
              stockUnit.setMemo("JD");
              stockUnitList.add(stockUnit);
            }
          }
        }
      }
      Collections.sort(stockUnitList);
      // 2012/12/03 ob udpate end

      Long orderStatus = orgOrderHeader.getOrderStatus();

      orgOrderHeader.setOrderStatus(OrderStatus.CANCELLED.longValue());
      //orgOrderHeader.setUpdatedDatetime(orderIdentifier.getUpdatedDatetime());
      manager.update(orgOrderHeader);

      StockManager stockManager = manager.getStockManager();

      boolean successUpdateStock = false;
      if (orderStatus.equals(OrderStatus.ORDERED.longValue())) {
        successUpdateStock = stockManager.deallocate(stockUnitList);
      } else if (orderStatus.equals(OrderStatus.RESERVED.longValue())) {
        successUpdateStock = stockManager.cancelReserving(stockUnitList);
        // v10-ch-pg added end
      } else if (orderStatus.equals(OrderStatus.PHANTOM_ORDER.longValue())) {
        successUpdateStock = stockManager.deallocate(stockUnitList);
        // v10-ch-pg added end
        // M17N 10361 追加 ここから
      } else if (orderStatus.equals(OrderStatus.PHANTOM_RESERVATION.longValue())) {
        successUpdateStock = stockManager.cancelReserving(stockUnitList);
        // M17N 10361 追加 ここまで
      } else if (orderStatus.equals(OrderStatus.CANCELLED.longValue())) {
        result.addServiceError(OrderServiceErrorContent.ALREADY_CANCELED_ERROR);
        manager.rollback();
        return result;

      } else {
        result.addServiceError(OrderServiceErrorContent.ORDER_CANCEL_ERROR);
        manager.rollback();
        return result;
      }

      if (!successUpdateStock) {
        manager.rollback();
        result.addServiceError(OrderServiceErrorContent.ORDER_CANCEL_ERROR);
        return result;
      }

      for (ShippingContainer shippingContainer : getJdShippingList(orderNo)) {
        shippingContainer.getJdShippingHeader().setShippingStatus(ShippingStatus.CANCELLED.longValue());
        manager.update(shippingContainer.getJdShippingHeader());
      }
      
      manager.commit();

      //performOrderEvent(new OrderEvent(orgOrderHeader.getOrderNo(),cashierPayment), OrderEventType.CANCELLED);

    } catch (ConcurrencyFailureException e) {
      logger.error(e);
      manager.rollback();
      throw e;
    } catch (RuntimeException e) {
      manager.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }

    return result;
  }
  
  

  private ServiceResultImpl cancelPayment(OrderContainer order) {
    ServiceResultImpl result = new ServiceResultImpl();
    CashierPayment cashierPayment = new CashierPayment();
    OrderHeader header = order.getOrderHeader();
    CashierPaymentTypeBase type = null;
    if (header.getPaymentMethodType().equals(PaymentMethodType.CREDITCARD.getValue())) {
      type = new CashierPaymentTypeCreditCard();
    }
    if (type != null) {
      type.setPaymentMethodCode(NumUtil.toString(order.getOrderHeader().getPaymentMethodNo()));
      type.setPaymentMethodType(order.getOrderHeader().getPaymentMethodType());
      type.setPaymentMethodName(order.getOrderHeader().getPaymentMethodName());
      type.setPaymentCommission(order.getOrderHeader().getPaymentCommission());
      cashierPayment.setShopCode(order.getOrderHeader().getShopCode());
      cashierPayment.setSelectPayment(type);
      // 10.1.6 10284 修正 ここから
      // PaymentProviderManager ppMgr = new
      // PaymentProviderManager(cashierPayment, order);
      PaymentProviderManager ppMgr = new PaymentProviderManager(cashierPayment, order, getLoginInfo());
      // 10.1.6 10284 修正 ここまで
      if (!ppMgr.cancel(cashierPayment, order)) {
        result.addServiceError(OrderServiceErrorContent.AUTHORIZATION_INCOMPLETE_ERROR);
      }
    }

    return result;
  }

  public ServiceResult clearPaymentDate(OrderIdentifier orderIdentifier) {
    return clearPaymentDate(orderIdentifier, true);
  }

  public ServiceResult clearPaymentDate(String orderNo) {
    return clearPaymentDate(new OrderIdentifier(orderNo, null), false);
  }

  private ServiceResult clearPaymentDate(OrderIdentifier orderIdentifier, boolean withConcurrencyControl) {
    String orderNo = orderIdentifier.getOrderNo();
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    // validationチェック
    OrderHeader header = new OrderHeader();
    header.setOrderNo(orderNo);
    setUserStatus(header);

    List<ValidationResult> beanValidResultList = BeanValidator.partialValidate(header, "orderNo").getErrors();
    if (beanValidResultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult vr : beanValidResultList) {
        Logger logger = Logger.getLogger(this.getClass());
        logger.debug(vr.getFormedMessage());
      }
    }

    OrderHeader orgOrderHeader = getOrderHeader(orderNo);

    if (orgOrderHeader == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    // 入金ステータス確認(未入金ではないかチェック)
    if (orgOrderHeader.getPaymentStatus().equals(PaymentStatus.NOT_PAID.longValue())) {
      return serviceResult;
    }

    OrderSummary orderSummary = getOrderSummary(orderNo);
    // 出荷ステータスサマリーチェック

    if (!orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())) {
      if (orgOrderHeader.getAdvanceLaterFlg().equals(AdvanceLaterFlg.ADVANCE.longValue())) {
        serviceResult.addServiceError(OrderServiceErrorContent.PAYMENT_DATE_SET_ERROR);
        return serviceResult;
      }

    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());

      orgOrderHeader.setPaymentStatus(PaymentStatus.NOT_PAID.longValue());
      orgOrderHeader.setPaymentDate(null);
      if (withConcurrencyControl) {
        orgOrderHeader.setUpdatedDatetime(orderIdentifier.getUpdatedDatetime());
      }
      setUserStatus(orgOrderHeader);
      txMgr.update(orgOrderHeader);

      // 後先区分が先払いの場合、関連付いている全出荷ステータスを入金待ちに変更する
      if (orgOrderHeader.getAdvanceLaterFlg().equals(AdvanceLaterFlg.ADVANCE.longValue())) {
        List<ShippingContainer> shippingList = getShippingList(orderNo);
        for (ShippingContainer shippingContainer : shippingList) {
          ShippingHeader shippingHeader = shippingContainer.getShippingHeader();
          setUserStatus(shippingHeader);
          shippingHeader.setShippingStatus(ShippingStatus.NOT_READY.longValue());
          txMgr.update(shippingHeader);
        }
      }
      txMgr.commit();
      performPaymentEvent(new PaymentEvent(orgOrderHeader.getOrderNo(), orgOrderHeader.getPaymentDate()), PaymentEventType.UPDATED);
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  public ServiceResult clearShippingReport(InputShippingReport report) {
    Logger logger = Logger.getLogger(this.getClass());

    String shippingNo = report.getShippingNo();
    Date orderUpdatedDatetime = report.getOrderUpdatedDatetime();

    ServiceResultImpl serviceResult = new ServiceResultImpl();

    ShippingHeaderDao dao = DIContainer.getDao(ShippingHeaderDao.class);
    ShippingHeader shippingHeader = dao.load(shippingNo);
    if (shippingHeader == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    OrderHeader orderHeader = this.getOrderHeader(shippingHeader.getOrderNo());
    if (orderHeader == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    if (orderHeader.getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.longValue())) {
      // データ連携済みステータスが「連携済み」の場合はステータスの変更不可

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    if (shippingHeader.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue())) {
      // 売上確定ステータスが「確定済み」の場合はステータスの変更不可

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    if (!shippingHeader.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())) {
      // 出荷ステータスが「出荷済み」以外の場合はステータスの変更不可

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    // 10.1.4 10188 修正 ここから
    if (orderHeader.getPaymentMethodType().equals(PaymentMethodType.CREDITCARD.getValue())
        && orderHeader.getPaymentStatus().equals(PaymentStatus.PAID.longValue())) {
      // 「クレジットカード払い」かつ「入金済み」の場合は出荷取消不可

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }
    // 10.1.4 10188 修正 ここまで

    orderHeader.setUpdatedDatetime(orderUpdatedDatetime);
    OrderHeaderDao orderDao = DIContainer.getDao(OrderHeaderDao.class);
    orderDao.update(orderHeader, getLoginInfo());

    shippingHeader.setUpdatedDatetime(report.getUpdatedDatetime());
    shippingHeader.setUpdatedUser(report.getUpdatedUser());
    shippingHeader.setShippingStatus(ShippingStatus.IN_PROCESSING.longValue());
    shippingHeader.setDeliverySlipNo(null);
    shippingHeader.setShippingDate(null);
    shippingHeader.setArrivalDate(null);
    shippingHeader.setArrivalTimeStart(null);
    shippingHeader.setArrivalTimeEnd(null);

    ValidationSummary summary = BeanValidator.validate(shippingHeader);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    List<ShippingDetail> detailList = getShippingDetailList(shippingNo);
    List<StockUnit> stockUtilList = new ArrayList<StockUnit>();
    for (ShippingDetail detail : detailList) {
      StockUnit unit = new StockUnit();
      unit.setShopCode(detail.getShopCode());
      unit.setSkuCode(detail.getSkuCode());
      unit.setStockIODate(DateUtil.getSysdate());
      unit.setQuantity(detail.getPurchasingAmount().intValue());
      unit.setLoginInfo(this.getLoginInfo());
      stockUtilList.add(unit);
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());

      StockManager stockManager = txMgr.getStockManager();
      if (!stockManager.cancelShipping(stockUtilList)) {
        serviceResult.addServiceError(OrderServiceErrorContent.INPUT_ADEQUATE_ERROR);
        txMgr.rollback();
        return serviceResult;
      }

      txMgr.update(shippingHeader);
      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      logger.error(e);
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  public ServiceResult clearShippingDirection(InputShippingReport report) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());

    Date orderUpdatedDatetime = report.getOrderUpdatedDatetime();
    ShippingHeader shippingHeaderDto = getShippingHeader(report.getShippingNo());

    if (shippingHeaderDto == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    ValidationSummary summary = BeanValidator.validate(shippingHeaderDto);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    OrderHeader ohDto = this.getOrderHeader(shippingHeaderDto.getOrderNo());
    if (ohDto == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    if (ohDto.getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.longValue())) {
      // データ連携済みステータスが「連携済み」の場合はステータスの変更不可

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    if (shippingHeaderDto.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue())) {
      // 売上確定ステータスが「確定済み」の場合はステータスの変更不可

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    if (!shippingHeaderDto.getShippingStatus().equals(ShippingStatus.IN_PROCESSING.longValue())) {
      // 出荷ステータスが「出荷手配中」の場合以外は、ステータスの変更不可

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      txMgr.begin(this.getLoginInfo());
      ohDto.setUpdatedDatetime(orderUpdatedDatetime);
      txMgr.update(ohDto);

      // 出荷可能へ変更

      shippingHeaderDto.setShippingStatus(ShippingStatus.READY.longValue());

      txMgr.update(shippingHeaderDto);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  public ServiceResult clearShippingDirectDate(InputShippingReport report) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());

    Date orderUpdatedDatetime = report.getOrderUpdatedDatetime();
    ShippingHeader shippingHeaderDto = getShippingHeader(report.getShippingNo());

    if (shippingHeaderDto == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    ValidationSummary summary = BeanValidator.validate(shippingHeaderDto);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    OrderHeader ohDto = this.getOrderHeader(shippingHeaderDto.getOrderNo());
    if (ohDto == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    if (ohDto.getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.longValue())) {
      // データ連携済みステータスが「連携済み」の場合はステータスの変更不可

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    if (shippingHeaderDto.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue())) {
      // 売上確定ステータスが「確定済み」の場合はステータスの変更不可

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      txMgr.begin(this.getLoginInfo());
      ohDto.setUpdatedDatetime(orderUpdatedDatetime);
      txMgr.update(ohDto);

      // 出荷可能へ変更

      shippingHeaderDto.setShippingDirectDate(null);
      shippingHeaderDto.setShippingStatus(ShippingStatus.READY.longValue());

      txMgr.update(shippingHeaderDto);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }
  
  private OrderContainer createOrderByOptional(OrderContainer order ,Map<String, OptionalCommodity> optionalCommodityMap) {
//    // A件B元 orderDetail对应
//    List<OrderDetail> orderDetailList = order.getOrderDetails();
//    List<OrderDetail> newDetailList = new ArrayList<OrderDetail>();
//    List<OrderDetail> optionalDetailList = new ArrayList<OrderDetail>();
//    for (OrderDetail orderDetail : orderDetailList) {
//      for (String s : optionalCommodityMap.keySet()) {
//        if (orderDetail.getCommodityCode().equals(s.split(":")[0])) {
//          OptionalCommodity oc = optionalCommodityMap.get(s);
//          BigDecimal cheapPrice = new BigDecimal(s.split(":")[1]);
//          OrderDetail optionalDetail = (OrderDetail)orderDetail.clone();
//          optionalDetail.setRetailPrice(optionalDetail.getRetailPrice().subtract(cheapPrice));
//          optionalDetail.setPurchasingAmount(oc.getCommodityAmount());
//          optionalDetail.setCampaignCode(oc.getOptionalCode());
//          optionalDetail.setCampaignName(oc.getOptionalNameCn());
//          if (orderDetail.getPurchasingAmount().equals(oc.getCommodityAmount()) ) {
//            orderDetail.setPurchasingAmount(0L);
//          } else {
//            orderDetail.setPurchasingAmount(orderDetail.getPurchasingAmount() - oc.getCommodityAmount());
//          }
//          optionalDetailList.add(optionalDetail);
//        }
//      }
//    }
//    
//    for (OrderDetail orderDetail : orderDetailList) {
//      if (orderDetail.getPurchasingAmount() > 0L) {
//        newDetailList.add(orderDetail);
//      }
//    }
//    newDetailList.addAll(optionalDetailList);
//    order.setOrderDetails(newDetailList);
    
    // A件B元 shippingDetail对应
    List<ShippingDetail> newShippingList = new ArrayList<ShippingDetail>();
    List<ShippingDetail> optionalShippingList = new ArrayList<ShippingDetail>();
    for (ShippingContainer shipping : order.getShippings()) {
      for (ShippingDetail sd : shipping.getShippingDetails()) {
        for (String s : optionalCommodityMap.keySet()) {
          if (sd.getSkuCode().equals(s.split(":")[0])) {
            OptionalCommodity oc = optionalCommodityMap.get(s);
            BigDecimal cheapPrice = new BigDecimal(s.split(":")[1]);
            ShippingDetail optionalDetail = (ShippingDetail)sd.clone();
            optionalDetail.setRetailPrice(optionalDetail.getRetailPrice().subtract(cheapPrice));
            optionalDetail.setPurchasingAmount(oc.getCommodityAmount());
            optionalDetail.setCampaignCode(oc.getOptionalCode());
            optionalDetail.setCampaignName(oc.getOptionalNameCn());
            optionalDetail.setDiscountType(DiscountType.OPTIONAL.longValue());
            optionalDetail.setDiscountPrice(optionalDetail.getUnitPrice().subtract(optionalDetail.getRetailPrice()));
            if (sd.getPurchasingAmount().equals(oc.getCommodityAmount()) ) {
              sd.setPurchasingAmount(0L);
            } else {
              sd.setPurchasingAmount(sd.getPurchasingAmount() - oc.getCommodityAmount());
            }
            optionalShippingList.add(optionalDetail);
          }
        }
      }
      
      for (ShippingDetail sd : shipping.getShippingDetails()) {
        if (sd.getPurchasingAmount() > 0L) {
          newShippingList.add(sd);
        }
      }
      newShippingList.addAll(optionalShippingList);
      shipping.setShippingDetails(newShippingList);
      
    }
    
    
    return order;
  }

  public ServiceResult createNewOrder(OrderContainer order, Cashier cashier, String clientType) {
    ServiceResultImpl result = new ServiceResultImpl();

    // 顧客存在チェック
    CustomerDao customerDao = DIContainer.getDao(CustomerDao.class);
    String customerCode = order.getOrderHeader().getCustomerCode();
    if (!customerCode.equals(CustomerConstant.GUEST_CUSTOMER_CODE)) {
      Customer orgCustomer = customerDao.load(customerCode);
      if (orgCustomer == null || orgCustomer.getCustomerStatus().equals(CustomerStatus.RECEIVED_WITHDRAWAL_NOTICE.longValue())
          || orgCustomer.getCustomerStatus().equals(CustomerStatus.WITHDRAWED.longValue())) {
        result.addServiceError(CustomerServiceErrorContent.CUSTOMER_DELETED_ERROR);
        return result;
      }
    }
    OrderHeader oh = order.getOrderHeader();
    
    // クライアントグループ設定
    oh.setClientGroup(getClientGroup());

    // データ整合性チェック
    ShopDao shopDao = DIContainer.getDao(ShopDao.class);
    Shop shop = shopDao.load(oh.getShopCode());
    if (shop == null) {
      result.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return result;
    }
    CommodityDetailDao commodityDao = DIContainer.getDao(CommodityDetailDao.class);
    for (OrderDetail detail : order.getOrderDetails()) {
      CommodityDetail commodity = commodityDao.load(detail.getShopCode(), detail.getSkuCode());
      if (commodity == null) {
        result.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
        return result;
      }
    }

    boolean isUseablePoint = true;
    Customer customer = null;
    if (oh.getCustomerCode().equals(CustomerConstant.GUEST_CUSTOMER_CODE)) {
      // ゲスト購入の場合ポイント関連情報は使用不可とする

      isUseablePoint = false;
      // ゲストの場合、顧客コードをゲストコードに変える

      oh.setCustomerCode(CommonLogic.generateGuestCode());
    } else {
      // ゲスト以外の場合はポイントルールを参照

      PointRuleDao pointRuledao = DIContainer.getDao(PointRuleDao.class);
      List<PointRule> pointRuleList = pointRuledao.loadAll();
      PointRule pointRule = null;

      if (pointRuleList.size() > 0) {
        pointRule = pointRuleList.get(0);
        PointFunctionEnabledFlg flg = PointFunctionEnabledFlg.fromValue(pointRule.getPointFunctionEnabledFlg());
        if (flg == PointFunctionEnabledFlg.ENABLED) {
          isUseablePoint = true;
        } else {
          isUseablePoint = false;
        }
      } else {
        throw new RuntimeException(Messages.getString("service.impl.OrderServiceImpl.1"));
      }
    }

    CampaignDao dao = DIContainer.getDao(CampaignDao.class);
    for (OrderDetail od : order.getOrderDetails()) {
      if (StringUtil.hasValue(od.getCampaignCode())) {
        Campaign campaign = dao.load(od.getShopCode(), od.getCampaignCode());
        if (campaign == null) {
          result.addServiceError(OrderServiceErrorContent.CAMPAIGN_FAILED);
          return result;
        }
      }
      setUserStatus(od);
    }

    // 受注番号シーケンス取得

    if (BigDecimalUtil.isAbove(cashier.getOptionalCheapPrice(), BigDecimal.ZERO)
          && cashier.getOptionalCommodityMap() != null && cashier.getOptionalCommodityMap().values().size() > 0) {
      createOrderByOptional(order,cashier.getOptionalCommodityMap());
    }
    
    long orderNo = DatabaseUtil.generateSequence(SequenceType.ORDER_NO);

    // 20140306 txw add start
    List<OrderPropagandaCommodityInfo> propagandaCommodityList = getOrderPropagandaCommodityInfo(order.getShippings().get(0).getShippingHeader().getPrefectureCode(), customerCode);
    // 20140306 txw add end
    
    oh.setOrderNo(NumUtil.toString(orderNo));
    oh.setOrderDatetime(DateUtil.getSysdate());
    if (order.getOrderInvoice() != null) {
      oh.setInvoiceFlg(InvoiceFlg.NEED.longValue());
    } else {
      oh.setInvoiceFlg(InvoiceFlg.NO_NEED.longValue());
    }
    setUserStatus(oh);
    order.setOrderHeader(oh);

    // 受注明細に受注番号とユーザーステータスをセット
    for (OrderDetail orderDetail : order.getOrderDetails()) {
      orderDetail.setOrderNo(NumUtil.toString(orderNo));
      setUserStatus(orderDetail);
    }
    // 20140306 txw add start
    for(OrderPropagandaCommodityInfo opci: propagandaCommodityList) {
      OrderDetail od = new OrderDetail();
      od.setOrderNo(NumUtil.toString(orderNo));
      od.setShopCode(shop.getShopCode());
      od.setSkuCode(opci.getCommodityCode());
      od.setCommodityCode(opci.getCommodityCode());
      od.setCommodityName(opci.getCommodityName());
      od.setPurchasingAmount(opci.getPurchasingAmount());
      od.setUnitPrice(BigDecimal.ZERO);
      od.setRetailPrice(BigDecimal.ZERO);
      od.setRetailTax(BigDecimal.ZERO);
      od.setCommodityTaxRate(0L);
      od.setCommodityTax(BigDecimal.ZERO);
      od.setCommodityTaxType(TaxType.NO_TAX.longValue());
      od.setAppliedPointRate(0L);
      od.setCommodityWeight(opci.getCommodityWeight());
      od.setCommodityType(CommodityType.PROPAGANDA.longValue());
      od.setSetCommodityFlg(SetCommodityFlg.OBJECTOUT.longValue());
      setUserStatus(od);
      order.getOrderDetails().add(od);
    }
    // 20140306 txw add end
    
    //2012-11-27 促销对应 ob add start
    for (OrderCampaign campaign : order.getOrderCampaigns()) {
      campaign.setOrderNo(NumUtil.toString(orderNo));
      setUserStatus(campaign);
    }
    //2012-11-27 促销对应 ob add end

    // 付与ポイントを計算して設定する(新規受注時の日付で計算)
    setAcquiredPoint(order);

    // 使用ポイントの合計値と付与ポイントの合計値を取得する
    BigDecimal usePointTotal = order.getOrderHeader().getUsedPoint();
    BigDecimal acquiredPoint = BigDecimal.ZERO;
    for (ShippingContainer shipping : order.getShippings()) {
      acquiredPoint = BigDecimalUtil.add(acquiredPoint, shipping.getShippingHeader().getAcquiredPoint());
      long shippingNo = DatabaseUtil.generateSequence(SequenceType.SHIPPING_NO);
      ShippingHeader sh = shipping.getShippingHeader();
      sh.setOrderNo(NumUtil.toString(orderNo));
      sh.setShippingNo(NumUtil.toString(shippingNo));
      sh.setFixedSalesStatus(FixedSalesStatus.NOT_FIXED.longValue());
      setUserStatus(sh);
      shipping.setShippingHeader(sh);

      Long sDetailCount = 0L;
      for (ShippingDetail sd : shipping.getShippingDetails()) {
        sd.setShippingNo(NumUtil.toString(shippingNo));
        sd.setShippingDetailNo(sDetailCount++);
        if (sd.getGiftPrice() == null) {
          sd.setGiftPrice(BigDecimal.ZERO);
        }
        
        // ギフト消費税タイプが存在しない場合非課税とする
        if (sd.getGiftTaxType() == null) {
          sd.setGiftTaxType(TaxType.NO_TAX.longValue());
        }
        setUserStatus(sd);
      }
      // 20140306 txw add start
      for(OrderPropagandaCommodityInfo opci: propagandaCommodityList) {
        ShippingDetail sd = new ShippingDetail();
        sd.setShippingNo(NumUtil.toString(shippingNo));
        sd.setShippingDetailNo(sDetailCount++);
        sd.setShopCode(shop.getShopCode());
        sd.setSkuCode(opci.getCommodityCode());
        sd.setUnitPrice(BigDecimal.ZERO);
        sd.setDiscountAmount(BigDecimal.ZERO);
        sd.setRetailPrice(BigDecimal.ZERO);
        sd.setRetailTax(BigDecimal.ZERO);
        sd.setPurchasingAmount(opci.getPurchasingAmount());
        sd.setGiftPrice(BigDecimal.ZERO);
        sd.setGiftTax(BigDecimal.ZERO);
        sd.setGiftTaxType(TaxType.NO_TAX.longValue());
        sd.setCommodityType(CommodityType.PROPAGANDA.longValue());
        sd.setSetCommodityFlg(SetCommodityFlg.OBJECTOUT.longValue());
        setUserStatus(sd);
        shipping.getShippingDetails().add(sd);
      }
      // 20140306 txw add end
    }
    
    //2012-11-27 促销对应 ob add start
    for (ShippingContainer shipping : order.getShippings()){
      Long shippingDetailNo = 0L;
      Long compositionNo = 0L;
      for (ShippingDetail sd : shipping.getShippingDetails()) {
        if (!SetCommodityFlg.OBJECTIN.longValue().equals(sd.getSetCommodityFlg())) {
          sd.setShippingDetailNo(shippingDetailNo);
          shippingDetailNo++;
        }
      }
      compositionNo = shippingDetailNo;
      for (ShippingDetail sd : shipping.getShippingDetails()) {
        if (SetCommodityFlg.OBJECTIN.longValue().equals(sd.getSetCommodityFlg())) {
          sd.setShippingDetailNo(shippingDetailNo);
          shippingDetailNo++;
        }
      }
      HashMap<ShippingDetail, List<ShippingDetailComposition>> shippingDetailContainerMap = shipping.getShippingDetailContainerMap();
      for (ShippingDetail sd : shipping.getShippingDetails()) {
        if (shippingDetailContainerMap.containsKey(sd)) {
          for (ShippingDetailComposition compositionDetail : shippingDetailContainerMap.get(sd)) {
            compositionDetail.setShippingNo(sd.getShippingNo());
            compositionDetail.setShippingDetailNo(sd.getShippingDetailNo());
            compositionDetail.setCompositionNo(compositionNo++);
            setUserStatus(compositionDetail);
          }
        }
      }
    }
    //2012-11-27 促销对应 ob add end

    // 一次Validationチェック
    if (!validateOrderSet(oh, order.getOrderDetails(), order.getShippings())) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    // add by V10-CH start
    // 优惠券

    CustomerCoupon custmerCoupon = null;
    boolean isUseableCoupon = true;
    CouponRuleDao couponRuleDao = DIContainer.getDao(CouponRuleDao.class);
    List<CouponRule> couponRuleList = couponRuleDao.loadAll();
    CouponRule couponRule = null;
    if (couponRuleList.size() > 0) {
      couponRule = couponRuleList.get(0);
      CouponFunctionEnabledFlg flg = CouponFunctionEnabledFlg.fromValue(couponRule.getCouponFunctionEnabledFlg());
      if (flg == CouponFunctionEnabledFlg.ENABLED) {
        isUseableCoupon = true;
      } else {
        isUseableCoupon = false;
      }
    }
    List<CustomerCoupon> customerCouponList = new ArrayList<CustomerCoupon>();
    if (isUseableCoupon) {
      CouponIssue couponIssue = getNewCustomerCoupon(order);
      if (couponIssue != null) {
        long customerCouponId = DatabaseUtil.generateSequence(SequenceType.CUSTOMER_COUPON_ID);
        custmerCoupon = new CustomerCoupon();
        custmerCoupon.setCustomerCode(oh.getCustomerCode());
        custmerCoupon.setUseFlg(CouponUsedFlg.PHANTOM_COUPON.longValue());
        custmerCoupon.setCouponIssueNo(couponIssue.getCouponIssueNo());
        custmerCoupon.setCustomerCouponId(customerCouponId);
        custmerCoupon.setCouponPrice(couponIssue.getCouponPrice());
        custmerCoupon.setIssueDate(DateUtil.getSysdate());
        custmerCoupon.setUseCouponStartDate(couponIssue.getUseCouponStartDate());
        custmerCoupon.setUseCouponEndDate(couponIssue.getUseCouponEndDate());
        custmerCoupon.setCouponName(couponIssue.getCouponName());
        custmerCoupon.setGetCouponOrderNo(NumUtil.toString(orderNo));
        custmerCoupon.setCouponName(couponIssue.getCouponName());
        setUserStatus(custmerCoupon);
      }

      if (order.getCustomerCouponlist() != null && order.getCustomerCouponlist().size() > 0) {
        CustomerCouponDao customerCouponDao = DIContainer.getDao(CustomerCouponDao.class);
        for (CustomerCoupon customerCoupon : order.getCustomerCouponlist()) {
          CustomerCoupon cc = customerCouponDao.load(customerCoupon.getCustomerCouponId());
          if (cc != null) {
            cc.setUseFlg(CouponUsedFlg.USED.longValue());
            cc.setOrderNo(NumUtil.toString(orderNo));
            cc.setUseDate(DateUtil.getSysdate());
            customerCouponList.add(cc);
          }
        }
      }
      // add by V10-CH end
    }

    if (isUseablePoint) {
      isUseablePoint = true;
      customer = customerDao.load(oh.getCustomerCode());
      
      // 顧客の残りポイントが、今回使用ポイント合計より少ない場合エラー
      if (ValidatorUtil.lessThan(customer.getRestPoint(), usePointTotal)) {
        result.addServiceError(OrderServiceErrorContent.OVER_USABLE_POINT_ERROR);
        return result;
      }
      // 注文合計金額 < 使用ポイントの場合エラー
      if (ValidatorUtil.lessThan(order.getTotalAmount().setScale(PointUtil.getAcquiredPointScale(), RoundingMode.CEILING).add(
          new BigDecimal(1)), BigDecimalUtil.divide(usePointTotal, DIContainer.getWebshopConfig().getRmbPointRate(), PointUtil
          .getAcquiredPointScale(), RoundingMode.FLOOR))) {
        result.addServiceError(OrderServiceErrorContent.USEABLE_POINT_ERROR);
        return result;
      }
      
      // ポイントが付与できる状態の場合、仮発行ポイント、使用ポイントを設定
      BigDecimal tmpPoint = customer.getTemporaryPoint();
      if (tmpPoint == null) {
        tmpPoint = BigDecimal.ZERO;
      }
      tmpPoint = BigDecimalUtil.add(tmpPoint, acquiredPoint);
      customer.setTemporaryPoint(tmpPoint);
      BigDecimal restPoint = customer.getRestPoint();
      restPoint = BigDecimalUtil.subtract(restPoint, usePointTotal);
      customer.setRestPoint(restPoint);
    }

    order.setOrderHeader(oh);
    OrderPointInfo pointInfo = new OrderPointInfo(isUseablePoint, usePointTotal, acquiredPoint);

    // 20111223 shen add start
    // 发票验证
    if (order.getOrderInvoice() != null) {
      OrderInvoice orderInvoice = order.getOrderInvoice();
      orderInvoice.setOrderNo(oh.getOrderNo());
      setUserStatus(orderInvoice);

      ValidationSummary validationResult = BeanValidator.validate(orderInvoice);
      if (validationResult.hasError()) {
        result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        return result;
      }
      order.setOrderInvoice(orderInvoice);
    }

    // 顾客增值税发票更新
    if (order.getCustomerVatInvoice() != null) {
      CustomerVatInvoice customerVatInvoice = order.getCustomerVatInvoice();
      setUserStatus(customerVatInvoice);

      ValidationSummary validationResult = BeanValidator.validate(customerVatInvoice);
      if (validationResult.hasError()) {
        result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        return result;
      }
      order.setCustomerVatInvoice(customerVatInvoice);
    }
    // 20111223 shen add end
    
    CashierPayment cashierPayment = cashier.getPayment();
    return insertOrder(order, cashierPayment, customer, pointInfo, custmerCoupon, customerCouponList, clientType);
  }

  private String getClientGroup() {
    String clientGroup = "";
    UserAgentManager manager = DIContainer.getUserAgentManager();

    if (getLoginInfo().getLoginType().equals(LoginType.BACK)) {
      UserAgent agent = manager.identifyAgent(SystemUserAgent.BACK_ORDER.getKeyword());
      clientGroup = agent.getClientGroup();
    } else {
      clientGroup = getLoginInfo().getUserAgent().getClientGroup();
      if (getLoginInfo().getUserAgent() == null && StringUtil.isNullOrEmpty(getLoginInfo().getUserAgent().getClientGroup())) {
        UserAgent agent = manager.identifyAgent(SystemUserAgent.OTHER.getKeyword());
        clientGroup = agent.getClientGroup();
      } else {
        clientGroup = getLoginInfo().getUserAgent().getClientGroup();
      }
    }

    return clientGroup;
  }

  private static class OrderPointInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean useablePoint;

    private BigDecimal usePointTotal;

    private BigDecimal acquiredPoint;

    public OrderPointInfo(boolean useablePoint, BigDecimal usePointTotal, BigDecimal acquiredPoint) {
      this.useablePoint = useablePoint;
      this.usePointTotal = usePointTotal;
      this.acquiredPoint = acquiredPoint;
    }

    /**
     * @return the acquiredPoint
     */
    public BigDecimal getAcquiredPoint() {
      return acquiredPoint;
    }

    /**
     * @param acquiredPoint
     *          the acquiredPoint to set
     */
    public void setAcquiredPoint(BigDecimal acquiredPoint) {
      this.acquiredPoint = acquiredPoint;
    }

    /**
     * @return the isUseablePoint
     */
    public boolean isUseablePoint() {
      return useablePoint;
    }

    /**
     * @param isUseablePoint
     *          the isUseablePoint to set
     */
    public void setUseablePoint(boolean useablePoint) {
      this.useablePoint = useablePoint;
    }

    /**
     * @return the usePointTotal
     */
    public BigDecimal getUsePointTotal() {
      return usePointTotal;
    }

    /**
     * @param usePointTotal
     *          the usePointTotal to set
     */
    public void setUsePointTotal(BigDecimal usePointTotal) {
      this.usePointTotal = usePointTotal;
    }
  }

  // add by V10-CH start
  private static class OrderCouponInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<CustomerCoupon> oldUseCustomerCoupon = new ArrayList<CustomerCoupon>();

    private List<CustomerCoupon> newUseCustomerCoupon = new ArrayList<CustomerCoupon>();

    private CustomerCoupon newGetCustomerCoupon = new CustomerCoupon();

    private CustomerCoupon oldGetCustomerCoupon = new CustomerCoupon();

    private Long couponFunctionEnabledFlg;

    public Long getCouponFunctionEnabledFlg() {
      return couponFunctionEnabledFlg;
    }

    public void setCouponFunctionEnabledFlg(Long couponFunctionEnabledFlg) {
      this.couponFunctionEnabledFlg = couponFunctionEnabledFlg;
    }

    public OrderCouponInfo(List<CustomerCoupon> oldCustomerCouponList, List<CustomerCoupon> newCustomerCouponList,
        CustomerCoupon oldGetCoupon, CustomerCoupon newGetCoupon) {
      setOldUseCustomerCoupon(oldCustomerCouponList);
      setNewUseCustomerCoupon(newCustomerCouponList);
      setOldGetCustomerCoupon(oldGetCoupon);
      setNewGetCustomerCoupon(newGetCoupon);
    }

    public OrderCouponInfo() {
    }

    public CustomerCoupon getNewGetCustomerCoupon() {
      return newGetCustomerCoupon;
    }

    public void setNewGetCustomerCoupon(CustomerCoupon newGetCustomerCoupon) {
      this.newGetCustomerCoupon = newGetCustomerCoupon;
    }

    public List<CustomerCoupon> getNewUseCustomerCoupon() {
      return newUseCustomerCoupon;
    }

    public void setNewUseCustomerCoupon(List<CustomerCoupon> newUseCustomerCoupon) {
      this.newUseCustomerCoupon = newUseCustomerCoupon;
    }

    public CustomerCoupon getOldGetCustomerCoupon() {
      return oldGetCustomerCoupon;
    }

    public void setOldGetCustomerCoupon(CustomerCoupon oldGetCustomerCoupon) {
      this.oldGetCustomerCoupon = oldGetCustomerCoupon;
    }

    public List<CustomerCoupon> getOldUseCustomerCoupon() {
      return oldUseCustomerCoupon;
    }

    public void setOldUseCustomerCoupon(List<CustomerCoupon> oldUseCustomerCoupon) {
      this.oldUseCustomerCoupon = oldUseCustomerCoupon;
    }

  }

  // add by V10-CH end

  private ServiceResult insertOrder(OrderContainer order, CashierPayment cashierPayment, Customer customer,
      OrderPointInfo orderPointInfo, CustomerCoupon customerCoupon, List<CustomerCoupon> useCouponList, String clientType) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();

    OrderHeader oh = order.getOrderHeader();
    // 20111222 shen add start
    OrderInvoice orderInvoice = order.getOrderInvoice();
    CustomerVatInvoice customerVatInvoice = order.getCustomerVatInvoice();
    // 20111222 shen add end

    // 引当商品情報を作成する
    List<StockUnit> addStockUnitList = createStockUtil(order.getOrderDetails());
    //2012-11-29 促销对应 ob add start
    // 套餐商品,要管理套餐明细的库存
    List<ShippingContainer> shippingContainerList = order.getShippings();
    
    for (ShippingContainer shippingContainer : shippingContainerList) {
      HashMap<ShippingDetail, List<ShippingDetailComposition>> shippingDetailContainerMap = shippingContainer.getShippingDetailContainerMap();
      if (shippingDetailContainerMap.isEmpty()) {
        continue;
      }
      for (ShippingDetail sd : shippingContainer.getShippingDetails()) {
        if (shippingDetailContainerMap.containsKey(sd)) {
          List<ShippingDetailComposition> compositionList = shippingDetailContainerMap.get(sd);
          for (ShippingDetailComposition compositionDetail : compositionList) {
            StockUnit unit = new StockUnit();
            unit.setShopCode(compositionDetail.getShopCode());
            unit.setSkuCode(compositionDetail.getChildSkuCode());
            unit.setQuantity(compositionDetail.getPurchasingAmount().intValue());
            unit.setLoginInfo(this.getLoginInfo());
            addStockUnitList.add(unit);
          }
        }
      }
    }
      
    //库存更新列表排序
    Collections.sort(addStockUnitList);
    //2012-11-29 促销对应 ob add end

    TransactionManager txMgr = DIContainer.getTransactionManager();
    // 10.1.6 10284 修正 ここから
    PaymentProviderManager ppMgr = new PaymentProviderManager(cashierPayment, order, getLoginInfo());
    // 10.1.6 10284 修正 ここまで
    try {
      txMgr.begin(this.getLoginInfo());
      if (orderPointInfo.isUseablePoint() && ValidatorUtil.moreThan(orderPointInfo.getUsePointTotal(), BigDecimal.ZERO)) {
        // ポイント履歴INSERT
        // insert: ポイント履歴（使用ポイント）
        PointHistory useHistory = new PointHistory();
        useHistory.setPointHistoryId(DatabaseUtil.generateSequence(SequenceType.POINT_HISTORY_ID));
        useHistory.setCustomerCode(oh.getCustomerCode());
        useHistory.setPointIssueStatus(PointIssueStatus.ENABLED.longValue());
        useHistory.setPointIssueType(PointIssueType.ORDER.longValue());
        useHistory.setOrderNo(oh.getOrderNo());
        useHistory.setIssuedPoint(orderPointInfo.getUsePointTotal().negate());
        useHistory.setShopCode(oh.getShopCode());
        useHistory.setDescription(Messages.getString("service.impl.OrderServiceImpl.2"));
        useHistory.setPointUsedDatetime(DatabaseUtil.getSystemDatetime());
        useHistory.setPointIssueDatetime(DateUtil.fromString(DateUtil.getSysdateString()));
        setUserStatus(useHistory);

        PointInsertProcedure pointInsert = new PointInsertProcedure(useHistory);
        txMgr.executeProcedure(pointInsert);

        // ポイントのオーバーフロー時処理

        if (pointInsert.getResult() == CustomerConstant.POINT_OVERFLOW_CODE) {
          txMgr.rollback();
          result.addServiceError(CustomerServiceErrorContent.POINT_OVERFLOW_ERROR);
          return result;
        }
      }

      // 在庫引当
      StockManager stockManager = txMgr.getStockManager();
      OrderStatus status = OrderStatus.fromValue(oh.getOrderStatus());
      boolean successUpdateStock = false;
      if (status == OrderStatus.ORDERED) {
        successUpdateStock = stockManager.allocate(addStockUnitList);
        // v10-ch-pg added start
      } else if (status == OrderStatus.PHANTOM_ORDER) {
        successUpdateStock = stockManager.allocate(addStockUnitList);
        // v10-ch-pg added end
        // M17N 10361 追加 ここから
      } else if (status == OrderStatus.PHANTOM_RESERVATION) {
        successUpdateStock = stockManager.reserving(addStockUnitList);
        // M17N 10361 追加 ここまで
      } else if (status == OrderStatus.RESERVED) {
        successUpdateStock = stockManager.reserving(addStockUnitList);
      }
      if (!successUpdateStock) {
        result.addServiceError(OrderServiceErrorContent.STOCK_ALLOCATE_ERROR);
        txMgr.rollback();
        return result;
      }

      // 与信処理
      PaymentResult paymentResult = ppMgr.entry(cashierPayment, order);
      if (paymentResult.hasError()) {
        txMgr.rollback();
        for (PaymentErrorContent content : paymentResult.getPaymentErrorList()) {
          result.addServiceError(content);
        }
        return result;
      }

      // 与信結果を受注ヘッダに設定

      setPaymentResultStatus(order, paymentResult);

      // 最終Validationチェック
      if (!validateOrderSet(oh, order.getOrderDetails(), order.getShippings())) {
        ppMgr.cancel();
        txMgr.rollback();
        result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        return result;
      }

      // 全額ポイント払い及び支払不要の場合、入金ステータスを入金済み、出荷ステータスを出荷可能に変更
      if (cashierPayment.getSelectPayment().getPaymentMethodType().equals(PaymentMethodType.NO_PAYMENT.getValue())
          || cashierPayment.getSelectPayment().getPaymentMethodType().equals(PaymentMethodType.POINT_IN_FULL.getValue())) {
        oh.setPaymentStatus(PaymentStatus.PAID.longValue());
        oh.setPaymentDate(DateUtil.truncateDate(oh.getOrderDatetime()));
        for (ShippingContainer shipping : order.getShippings()) {
          shipping.getShippingHeader().setShippingStatus(ShippingStatus.READY.longValue());
        }
      }
      // 设置订单区分
      oh.setMobileComputerType(NumUtil.toLong(clientType));
      // insert: 受注ヘッダ
      txMgr.insert(oh);
      
      // 礼品卡支付
      if (order.getOrderHeader().getGiftCardUsePrice() != null && !BigDecimalUtil.equals(order.getOrderHeader().getGiftCardUsePrice(), BigDecimal.ZERO) ) {
        BigDecimal giftMoney = order.getOrderHeader().getGiftCardUsePrice();
        CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
        List<CustomerCardInfo> infoList = service.getCustomerCardInfoList(oh.getCustomerCode());
        for (CustomerCardInfo info : infoList) {
          if (BigDecimalUtil.equals(giftMoney, BigDecimal.ZERO)) {
            break;
          }
          CustomerCardUseInfo useInfo = new CustomerCardUseInfo();
          useInfo.setCardId(info.getCardId());
          useInfo.setOrderNo(oh.getOrderNo());
          useInfo.setUseStatus(0L);
          useInfo.setCustomerCode(oh.getCustomerCode());
          useInfo.setUseDate(DateUtil.getSysdate());
          setUserStatus(useInfo);
          if (BigDecimalUtil.isAbove(giftMoney, info.getDenomination()) ) {
            useInfo.setUseAmount(info.getDenomination());
            giftMoney = giftMoney.subtract( info.getDenomination());
          } else {
            useInfo.setUseAmount(giftMoney);
            giftMoney = BigDecimal.ZERO;
          }
          txMgr.insert(useInfo);
        }
      }
      

      // insert: 受注明細
      for (OrderDetail od : order.getOrderDetails()) {
        od.setOrderNo(oh.getOrderNo());
        txMgr.insert(od);
      }

      // 出荷ヘッダ・明細作成

      for (ShippingContainer shipping : order.getShippings()) {
        ShippingHeader sh = shipping.getShippingHeader();
        txMgr.insert(sh);
        for (ShippingDetail sd : shipping.getShippingDetails()) {
          txMgr.insert(sd);
        }
      }
      // 2012-11-27 促销对应 ob add start
      for (OrderCampaign campaign : order.getOrderCampaigns()) {
        txMgr.insert(campaign);
      }

      for (ShippingDetailComposition compositionDetail : order.getShippingDtailCompositions()) {
        txMgr.insert(compositionDetail);
      }
      // 2012-11-27 促销对应 ob add end

      // 20111222 shen add start
      // 发票更新
      if (orderInvoice != null) {
        txMgr.insert(orderInvoice);
      }

      // 顾客增值税发票更新
      if (customerVatInvoice != null) {
        // 已存在的增值税发票删除
        Query query = new SimpleQuery(OrderServiceQuery.DELETE_CUSTOMER_VAT_INVOICE_BY_CUSTOMER_CODE, customerVatInvoice
            .getCustomerCode());
        txMgr.executeUpdate(query);

        txMgr.insert(customerVatInvoice);
      }
      // 20111222 shen add end

      // 20120106 shen add start
      // 个人礼金券使用更新
      if (StringUtil.hasValue(oh.getDiscountDetailCode())) {
        NewCouponHistoryDao dao = DIContainer.getDao(NewCouponHistoryDao.class);
        NewCouponHistory newCouponHistory = dao.load(oh.getDiscountDetailCode());
        // 个人礼金券使用可否验证
        if (newCouponHistory == null || newCouponHistory.getUseStatus().equals(UseStatus.USED.longValue())) {
          ppMgr.cancel();
          txMgr.rollback();
          result.addServiceError(CustomerServiceErrorContent.COUPON_NOT_USE_ERROR);
          return result;
        }

        newCouponHistory.setUseOrderNo(oh.getOrderNo());
        newCouponHistory.setUseStatus(UseStatus.USED.longValue());

        txMgr.update(newCouponHistory);
      }
      // 20120106 shen add end
      
      // 2013/04/10 优惠券对应 ob add start
      // 朋友介绍发行的优惠券履历插入
      FriendCouponIssueHistoryDao friendCouponIssueHistoryDao = DIContainer.getDao(FriendCouponIssueHistoryDao.class);
      FriendCouponIssueHistory issueHistory = friendCouponIssueHistoryDao.load(oh.getDiscountCode());
      if (issueHistory != null && StringUtil.hasValue(issueHistory.getCouponCode())) {
        FriendCouponUseHistory useHistory = new FriendCouponUseHistory();
        CustomerDao customerDao = DIContainer.getDao(CustomerDao.class);
        Customer customerDto = customerDao.load(oh.getCustomerCode());
        
        long useHistoryId = DatabaseUtil.generateSequence(SequenceType.USE_HISTORY_ID);
        

        useHistory.setUseHistoryId(NumUtil.toString(useHistoryId));
        useHistory.setCouponCode(issueHistory.getCouponCode());
        useHistory.setFriendCouponRuleNo(issueHistory.getFriendCouponRuleNo());
        useHistory.setOrderNo(oh.getOrderNo());
        useHistory.setCustomerCode(oh.getCustomerCode());
        useHistory.setCustomerName(customerDto.getLastName());
        useHistory.setIssueDate(oh.getOrderDatetime());
        // 20140416 hdh update start
        useHistory.setCouponAmount(oh.getDiscountPrice());
        // 20140416 hdh update end
        useHistory.setCouponUseNum(issueHistory.getCouponUseNum());
        useHistory.setFormerUsePoint(issueHistory.getFormerUsePoint());
        useHistory.setPoint(issueHistory.getObtainPoint());
        
        //统计朋友优惠券使用次数
        // 20140410 hdh edit start
//        Long countUseNum=friendCouponIssueHistoryDao.countUseNum(issueHistory.getCouponCode());
//        if(countUseNum>issueHistory.getCouponUseNum()){
//          useHistory.setPoint(issueHistory.getObtainPoint());
//        }else{
//          useHistory.setPoint(issueHistory.getFormerUsePoint());
//        }
        // 20140410 hdh edit end
        
        useHistory.setPointStatus(PointIssueStatus.PROVISIONAL.longValue());
        setUserStatus(useHistory);
        
        txMgr.insert(useHistory);
        
      }
      // 2013/04/10 优惠券对应 ob add end
      // 20131018 txw add start
      if (StringUtil.hasValue(order.getMediaCode())) {
        OrderMedia orderMedia = new OrderMedia();
        CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
        FreePostageListSearchCondition condition = new FreePostageListSearchCondition();
        condition.setSearchStatus(ActivityStatus.IN_PROGRESS.getValue());
        List<FreePostageRule> resultList = service.getFreePostageList(condition).getRows();
        for (FreePostageRule rule : resultList) {
          String[] mediaCodes = new String[] {};
          boolean mediaFlg = false;
          if (StringUtil.hasValue(rule.getObjectIssueCode())) {
            mediaCodes = rule.getObjectIssueCode().split(";");
            for (String mc : mediaCodes) {
              if (mc.equals(order.getMediaCode())) {
                mediaFlg = true;
                break;
              }
            }
            if (mediaFlg) {
              orderMedia.setFreePostageCode(rule.getFreePostageCode());
              orderMedia.setFreePostageName(rule.getFreePostageName());
              orderMedia.setMediaCode(order.getMediaCode());
              orderMedia.setMediaType(MediaType.FREE_POSTAGE.longValue());
              orderMedia.setOrderNo(oh.getOrderNo());
              break;
            }
          }
        }

        if (StringUtil.hasValue(orderMedia.getOrderNo())) {
          setUserStatus(orderMedia);

          txMgr.insert(orderMedia);
        }
      }
      // 20131018 txw add end

      // soukai add 2012/02/04 ob start
      // 优惠券发行
      // 2013/04/09 优惠券对应 ob update start
//      CouponIssueProcedure couponIssue = new CouponIssueProcedure(oh.getOrderNo(), oh.getUpdatedUser());
//      txMgr.executeProcedure(couponIssue);

      Long orderCount = countUsedCouponFirstOrder(oh.getCustomerCode());
      NewCouponIssueProcedure couponIssue = new NewCouponIssueProcedure(oh.getOrderNo(), oh.getUpdatedUser(), orderCount);
      txMgr.executeProcedure(couponIssue);
      // 2013/04/09 优惠券对应 ob update end
      
      // soukai add 2012/02/04 ob end
      txMgr.commit();
      
      // 2014/06/16 库存更新对应 ob_卢 add start
      // 支付方式为【货到付款】时，触发EC引当数变动事件
      if(cashierPayment.getSelectPayment().getPaymentMethodType().equals(PaymentMethodType.CASH_ON_DELIVERY.getValue())){ 
        performStockEvent(new StockEvent(order.getOrderHeader().getOrderNo()), StockEventType.EC);
      }
      // 2014/06/16 库存更新对应 ob_卢 add end
      
      CommonLogic.verifyPointDifference(oh.getCustomerCode(), PointIssueStatus.PROVISIONAL); // 10.1.3
      // 10174
      // 追加
      // v10-ch-pg added start
      if (status == OrderStatus.PHANTOM_ORDER) {
        // 仮注文の時点ではメールを出さない
        logger.info("created a phantom order");
      } else {
        if (oh.getPaymentMethodType().equals(PaymentMethodType.CASH_ON_DELIVERY.getValue())) {
          performOrderEvent(new OrderEvent(oh.getOrderNo(), cashierPayment, order.getOrderMailType()), OrderEventType.RECEIVED);
        }
        // Add by V10-CH start
        String smsType = "";
        if (order.getOrderSmsType().name().equals("MOBILE")) {
          smsType = "06";
        } else {
          smsType = "11";
        }
        List<SmsTemplateDetail> smsTemplate = DatabaseUtil.loadAsBeanList(new SimpleQuery(SmsingServiceQuery.LOAD_SMS_USEFLG,
            new Object[] {
              smsType
            }), SmsTemplateDetail.class);
        if (smsTemplate.size() > 0) {
          if (smsTemplate.get(0).getSmsUseFlg() == 1) {
            performOrderEvent(new OrderEvent(oh.getOrderNo(), cashierPayment, order.getOrderSmsType()), OrderEventType.RECEIVED);
          }
        }
        // Add by V10-CH end
      }
      // v10-ch-pg added end
    } catch (ConcurrencyFailureException e) {
      ppMgr.cancel();
      txMgr.rollback();
      logger.error(e);
      throw e;
    } catch (RuntimeException e) {
      ppMgr.cancel();
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  private OrderContainer createOrderContainer(String orderNo, String shippingNo, String shippingDetailNo) {
    OrderContainer orderContainer = new OrderContainer();
    orderContainer.setOrderHeader(this.getOrderHeader(orderNo));
    String shopCode = null;
    String skuCode = null;

    // ShippingHeaderContainerのリストを設定する。

    List<ShippingHeader> sHeaderList = new ArrayList<ShippingHeader>();

    // shippingNoがあれば、shippingNoをキーに1件取得、なければorderNoをキーに全件取得
    if (StringUtil.hasValue(shippingNo)) {
      ShippingHeaderDao sHeaderDao = DIContainer.getDao(ShippingHeaderDao.class);
      sHeaderList.add(sHeaderDao.load(shippingNo));
    } else {
      for (ShippingContainer container : getShippingList(orderNo)) {
        sHeaderList.add(container.getShippingHeader());
      }
    }

    List<ShippingContainer> sHeaderContainerList = new ArrayList<ShippingContainer>();

    for (ShippingHeader sHeaderDto : sHeaderList) {
      ShippingContainer sHeaderContainer = new ShippingContainer();
      sHeaderContainer.setShippingHeader(sHeaderDto);

      // ShippingDetailContainerのリストを設定する。

      List<ShippingDetail> sDetailList = new ArrayList<ShippingDetail>();

      // 2012/11/19 促销活动 ob add start
      List<ShippingDetailComposition> sComostionList = new ArrayList<ShippingDetailComposition>();
      ShippingDetailCompositionDao sCompostionDao = DIContainer.getDao(ShippingDetailCompositionDao.class);
      // 2012/11/19 促销活动 ob add end

      // shippingDetailNoがあれば、shippingDetailNoをキーに1件取得、なければshippingNoをキーに全件取得
      if (StringUtil.hasValue(shippingDetailNo)) {
        ShippingDetailDao sDetailDao = DIContainer.getDao(ShippingDetailDao.class);
        ShippingDetail sDetailDto = sDetailDao.load(sHeaderDto.getShippingNo(), Long.getLong(shippingDetailNo));
        // OrderDetailの主キーとなるショップコード
        shopCode = sDetailDto.getShopCode();
        // OrderDetailの主キーとなるSKUコード

        skuCode = sDetailDto.getSkuCode();
        sDetailList.add(sDetailDao.load(sHeaderDto.getShippingNo(), Long.getLong(shippingDetailNo)));
        // 2012/11/19 促销活动 ob add start
        sComostionList.addAll(sCompostionDao.findByQuery(OrderServiceQuery.SHIPPING_COMPOSITION_DETAIL_LIST_QUERY, sHeaderDto
            .getShippingNo(), NumUtil.toLong(shippingDetailNo)));
        // 2012/11/19 促销活动 ob add end
      } else {
        sDetailList = this.getShippingDetailList(sHeaderDto.getShippingNo());
        // 2012/11/19 促销活动 ob add start
        sComostionList.addAll(sCompostionDao.findByQuery(OrderServiceQuery.SHIPPING_COMPOSITION_ALL_LIST_QUERY, sHeaderDto
            .getShippingNo()));
        // 2012/11/19 促销活动 ob add end
      }

      sHeaderContainer.setShippingDetails(sDetailList);

      // 2012/11/19 促销活动 ob add start
      sHeaderContainer.setShippingDetailCommpositionList(sComostionList);
      for (ShippingDetail shippingDetail : sDetailList) {
        if (SetCommodityFlg.OBJECTIN.longValue().equals(shippingDetail.getSetCommodityFlg())) {
          sHeaderContainer.getShippingDetailContainerMap().put(
              shippingDetail,
              sCompostionDao.findByQuery(OrderServiceQuery.SHIPPING_COMPOSITION_DETAIL_LIST_QUERY, sHeaderDto.getShippingNo(),
                  shippingDetail.getShippingDetailNo()));
        }
      }
      // 2012/11/19 促销活动 ob add end
      sHeaderContainerList.add(sHeaderContainer);
    }

    // OrderDetailContainerのリストを設定する。

    List<OrderDetail> oDetailList = new ArrayList<OrderDetail>();

    // ショップコード、SKUコードがあれば、orderNo、shopCode、skuCodeをキーに1件取得、なければOrderNoをキーに全件取得
    if (StringUtil.hasValue(shopCode) && StringUtil.hasValue(skuCode)) {
      OrderDetailDao oDetailDao = DIContainer.getDao(OrderDetailDao.class);
      oDetailList.add(oDetailDao.load(orderNo, shopCode, skuCode));
    } else {
      oDetailList = this.getOrderDetailList(orderNo);
    }

    // 2012/11/30 ob add start
    Query query = new SimpleQuery(OrderServiceQuery.ORDER_CAMAPIGN_LIST_QUERY, orderNo);
    orderContainer.setOrderCampaigns(DatabaseUtil.loadAsBeanList(query, OrderCampaign.class));
    // 2012/11/30 ob add end

    orderContainer.setOrderDetails(oDetailList);

    orderContainer.setShippings(sHeaderContainerList);
    orderContainer.setOrderInvoice(this.getOrderInvoice(orderNo));

    return orderContainer;
  }

  private OrderContainer createTmallOrderContainer(String orderNo, String shippingNo, String shippingDetailNo) {
    OrderContainer orderContainer = new OrderContainer();
    orderContainer.setTmallOrderHeader(this.getTmallOrderHeader(orderNo));
    String shopCode = null;
    String skuCode = null;

    // ShippingHeaderContainerのリストを設定する。
    List<TmallShippingHeader> sHeaderList = new ArrayList<TmallShippingHeader>();

    // shippingNoがあれば、shippingNoをキーに1件取得、なければorderNoをキーに全件取得
    if (StringUtil.hasValue(shippingNo)) {
      TmallShippingHeaderDao sHeaderDao = DIContainer.getDao(TmallShippingHeaderDao.class);
      sHeaderList.add(sHeaderDao.load(shippingNo));
    } else {
      for (ShippingContainer container : getTmallShippingList(orderNo)) {
        sHeaderList.add(container.getTmallShippingHeader());
      }
    }

    List<ShippingContainer> sHeaderContainerList = new ArrayList<ShippingContainer>();

    for (TmallShippingHeader sHeaderDto : sHeaderList) {
      ShippingContainer sHeaderContainer = new ShippingContainer();
      sHeaderContainer.setTmallShippingHeader(sHeaderDto);

      // ShippingDetailContainerのリストを設定する。

      List<TmallShippingDetail> sDetailList = new ArrayList<TmallShippingDetail>();

      // shippingDetailNoがあれば、shippingDetailNoをキーに1件取得、なければshippingNoをキーに全件取得
      if (StringUtil.hasValue(shippingDetailNo)) {
        TmallShippingDetailDao sDetailDao = DIContainer.getDao(TmallShippingDetailDao.class);
        TmallShippingDetail sDetailDto = sDetailDao.load(sHeaderDto.getShippingNo(), Long.getLong(shippingDetailNo));
        // OrderDetailの主キーとなるショップコード
        shopCode = sDetailDto.getShopCode();
        // OrderDetailの主キーとなるSKUコード

        skuCode = sDetailDto.getSkuCode();
        sDetailList.add(sDetailDao.load(sHeaderDto.getShippingNo(), Long.getLong(shippingDetailNo)));
      } else {
        sDetailList = this.getTmallShippingDetailList(sHeaderDto.getShippingNo());
      }

      sHeaderContainer.setTmallShippingDetails(sDetailList);

      sHeaderContainerList.add(sHeaderContainer);
    }

    // TmallOrderDetailContainerのリストを設定する。

    List<TmallOrderDetail> oDetailList = new ArrayList<TmallOrderDetail>();

    // ショップコード、SKUコードがあれば、orderNo、shopCode、skuCodeをキーに1件取得、なければOrderNoをキーに全件取得
    if (StringUtil.hasValue(shopCode) && StringUtil.hasValue(skuCode)) {
      TmallOrderDetailDao oDetailDao = DIContainer.getDao(TmallOrderDetailDao.class);
      oDetailList.add(oDetailDao.load(orderNo, shopCode, skuCode));
    } else {
      oDetailList = this.getTmallOrderDetailList(orderNo);
    }

    orderContainer.setTmallIOrderDetails(oDetailList);

    orderContainer.setShippings(sHeaderContainerList);

    return orderContainer;
  }

  private OrderContainer createJdOrderContainer(String orderNo, String shippingNo, String shippingDetailNo) {
    OrderContainer orderContainer = new OrderContainer();
    orderContainer.setJdOrderHeader(this.getJdOrderHeader(orderNo));
    String shopCode = null;
    String skuCode = null;

    // ShippingHeaderContainerのリストを設定する。
    List<JdShippingHeader> sHeaderList = new ArrayList<JdShippingHeader>();

    // shippingNoがあれば、shippingNoをキーに1件取得、なければorderNoをキーに全件取得
    if (StringUtil.hasValue(shippingNo)) {
      JdShippingHeaderDao sHeaderDao = DIContainer.getDao(JdShippingHeaderDao.class);
      sHeaderList.add(sHeaderDao.load(shippingNo));
    } else {
      for (ShippingContainer container : getJdShippingList(orderNo)) {
        sHeaderList.add(container.getJdShippingHeader());
      }
    }

    List<ShippingContainer> sHeaderContainerList = new ArrayList<ShippingContainer>();

    for (JdShippingHeader sHeaderDto : sHeaderList) {
      ShippingContainer sHeaderContainer = new ShippingContainer();
      sHeaderContainer.setJdShippingHeader(sHeaderDto);

      // ShippingDetailContainerのリストを設定する。

      List<JdShippingDetail> sDetailList = new ArrayList<JdShippingDetail>();

      // shippingDetailNoがあれば、shippingDetailNoをキーに1件取得、なければshippingNoをキーに全件取得
      if (StringUtil.hasValue(shippingDetailNo)) {
        JdShippingDetailDao sDetailDao = DIContainer.getDao(JdShippingDetailDao.class);
        JdShippingDetail sDetailDto = sDetailDao.load(sHeaderDto.getShippingNo(), Long.getLong(shippingDetailNo));
        // OrderDetailの主キーとなるショップコード
        shopCode = sDetailDto.getShopCode();
        // OrderDetailの主キーとなるSKUコード

        skuCode = sDetailDto.getSkuCode();
        sDetailList.add(sDetailDao.load(sHeaderDto.getShippingNo(), Long.getLong(shippingDetailNo)));
      } else {
        sDetailList = this.getJdShippingDetailList(sHeaderDto.getShippingNo());
      }

      sHeaderContainer.setJdShippingDetails(sDetailList);

      sHeaderContainerList.add(sHeaderContainer);
    }

    // JdOrderDetailContainerのリストを設定する。

    List<JdOrderDetail> oDetailList = new ArrayList<JdOrderDetail>();

    // ショップコード、SKUコードがあれば、orderNo、shopCode、skuCodeをキーに1件取得、なければOrderNoをキーに全件取得
    if (StringUtil.hasValue(shopCode) && StringUtil.hasValue(skuCode)) {
      JdOrderDetailDao oDetailDao = DIContainer.getDao(JdOrderDetailDao.class);
      oDetailList.add(oDetailDao.load(orderNo, shopCode, skuCode));
    } else {
      oDetailList = this.getJdOrderDetailList(orderNo);
    }

    orderContainer.setJdOrderDetails(oDetailList);

    orderContainer.setShippings(sHeaderContainerList);

    return orderContainer;
  }

  
  
  private OrderContainer createTmallOrderContainerByTid(String tid, String shippingNo, String shippingDetailNo) {
    OrderContainer orderContainer = new OrderContainer();
    orderContainer.setTmallOrderHeader(this.getTmallOrderHeaderByTid(tid));
    String shopCode = null;
    String skuCode = null;

    // ShippingHeaderContainerのリストを設定する。

    List<TmallShippingHeader> sHeaderList = new ArrayList<TmallShippingHeader>();

    // shippingNoがあれば、shippingNoをキーに1件取得、なければorderNoをキーに全件取得
    if (StringUtil.hasValue(shippingNo)) {
      TmallShippingHeaderDao sHeaderDao = DIContainer.getDao(TmallShippingHeaderDao.class);
      sHeaderList.add(sHeaderDao.load(shippingNo));
    } else {
      for (ShippingContainer container : getTmallShippingList(tid)) {
        sHeaderList.add(container.getTmallShippingHeader());
      }
    }

    List<ShippingContainer> sHeaderContainerList = new ArrayList<ShippingContainer>();

    for (TmallShippingHeader sHeaderDto : sHeaderList) {
      ShippingContainer sHeaderContainer = new ShippingContainer();
      sHeaderContainer.setTmallShippingHeader(sHeaderDto);

      // ShippingDetailContainerのリストを設定する。

      List<TmallShippingDetail> sDetailList = new ArrayList<TmallShippingDetail>();

      // shippingDetailNoがあれば、shippingDetailNoをキーに1件取得、なければshippingNoをキーに全件取得
      if (StringUtil.hasValue(shippingDetailNo)) {
        TmallShippingDetailDao sDetailDao = DIContainer.getDao(TmallShippingDetailDao.class);
        TmallShippingDetail sDetailDto = sDetailDao.load(sHeaderDto.getShippingNo(), Long.getLong(shippingDetailNo));
        // OrderDetailの主キーとなるショップコード
        shopCode = sDetailDto.getShopCode();
        // OrderDetailの主キーとなるSKUコード

        skuCode = sDetailDto.getSkuCode();
        sDetailList.add(sDetailDao.load(sHeaderDto.getShippingNo(), Long.getLong(shippingDetailNo)));
      } else {
        sDetailList = this.getTmallShippingDetailList(sHeaderDto.getShippingNo());
      }

      sHeaderContainer.setTmallShippingDetails(sDetailList);

      sHeaderContainerList.add(sHeaderContainer);
    }

    // TmallOrderDetailContainerのリストを設定する。

    List<TmallOrderDetail> oDetailList = new ArrayList<TmallOrderDetail>();

    // ショップコード、SKUコードがあれば、orderNo、shopCode、skuCodeをキーに1件取得、なければOrderNoをキーに全件取得
    if (StringUtil.hasValue(shopCode) && StringUtil.hasValue(skuCode)) {
      TmallOrderDetailDao oDetailDao = DIContainer.getDao(TmallOrderDetailDao.class);
      oDetailList.add(oDetailDao.load(orderContainer.getOrderHeader().getOrderNo(), shopCode, skuCode));
    } else {
      oDetailList = this.getTmallOrderDetailList(orderContainer.getOrderHeader().getOrderNo());
    }

    orderContainer.setTmallIOrderDetails(oDetailList);

    orderContainer.setShippings(sHeaderContainerList);

    return orderContainer;
  }

  private List<StockUnit> createStockUtil(List<OrderDetail> orderDetails) {
    List<StockUnit> stockUtilList = new ArrayList<StockUnit>();
    for (OrderDetail detail : orderDetails) {
      // 2012-11-28 促销对应 ob add start
      // 套餐商品,库存不处理
      if (SetCommodityFlg.OBJECTIN.longValue().equals(detail.getSetCommodityFlg())) {
        continue;
      }
      // 2012-11-28 促销对应 ob add end
      StockUnit unit = new StockUnit();
      unit.setShopCode(detail.getShopCode());
      unit.setSkuCode(detail.getSkuCode());
      unit.setQuantity(detail.getPurchasingAmount().intValue());
      unit.setLoginInfo(this.getLoginInfo());
      stockUtilList.add(unit);
    }
    return stockUtilList;
  }

  private List<StockUnit> createTStockUtil(List<TmallOrderDetail> orderDetails) {
    List<StockUnit> stockUtilList = new ArrayList<StockUnit>();
    for (TmallOrderDetail detail : orderDetails) {
      StockUnit unit = new StockUnit();
      unit.setShopCode(detail.getShopCode());
      unit.setSkuCode(detail.getSkuCode());
      unit.setQuantity(detail.getPurchasingAmount().intValue());
      unit.setLoginInfo(this.getLoginInfo());
      stockUtilList.add(unit);
    }
    return stockUtilList;
  }

  public ServiceResult deleteReturnItemData(String shippingNo) {
    ServiceResultImpl result = new ServiceResultImpl();

    if (shippingNo == null) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    ShippingContainer shipping = getShipping(shippingNo);
    ShippingHeader shippingHeader = shipping.getShippingHeader();
    if (shippingHeader.getReturnItemType() != 1L) {
      // 削除対象が返品データでない場合はエラーとする
      result.addServiceError(OrderServiceErrorContent.SHIPPING_DATA_DELETE_ERROR);
      return result;
    }

    OrderHeader orderHeader = getOrderHeader(shippingHeader.getOrderNo());
    FixedSalesStatus fixedStatus = FixedSalesStatus.fromValue(shippingHeader.getFixedSalesStatus());
    if (fixedStatus == FixedSalesStatus.FIXED) {
      // 売上確定している場合、該当返品のマイナスデータを作成して削除とする

      shippingHeader.setOriginalShippingNo(NumUtil.toLong(shippingHeader.getShippingNo()));
      shippingHeader.setShippingCharge(BigDecimalUtil.multiply(shippingHeader.getShippingCharge(), -1));
      shippingHeader.setReturnItemLossMoney(BigDecimalUtil.multiply(shippingHeader.getReturnItemLossMoney(), -1));
      shippingHeader.setReturnItemDate(DatabaseUtil.getSystemDatetime());
      shippingHeader.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
      ShippingDetail shippingDetail = null;
      if (shipping.getShippingDetails().size() > 0) {
        shippingDetail = shipping.getShippingDetails().get(0);
        shippingDetail.setPurchasingAmount(shippingDetail.getPurchasingAmount() * -1);
        shippingDetail.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
      } else {
        shippingDetail = new ShippingDetail();
      }

      result = (ServiceResultImpl) insertReturnOrder(orderHeader, shippingHeader, shippingDetail);
    } else if (fixedStatus == FixedSalesStatus.NOT_FIXED) {
      // データ未確定の場合、該当出荷情報を削除する
      TransactionManager manager = DIContainer.getTransactionManager();
      try {
        manager.begin(this.getLoginInfo());

        ArchivedOrderProcedure archivedOrder = new ArchivedOrderProcedure(orderHeader.getOrderNo());
        manager.executeProcedure(archivedOrder);

        List<ShippingDetail> shippingDetailList = shipping.getShippingDetails();
        for (ShippingDetail detail : shippingDetailList) {
          manager.delete(detail);
        }

        manager.delete(shippingHeader);
        manager.commit();
      } catch (RuntimeException e) {
        manager.rollback();
        result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
        Logger logger = Logger.getLogger(this.getClass());
        logger.error(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR.toString(), e);
      } finally {
        manager.dispose();
      }
    }

    return result;
  }

  public ServiceResult fixSale(String shippingNo) {
    ServiceResultImpl result = new ServiceResultImpl();

    ShippingHeaderDao shippingHeaderDao = DIContainer.getDao(ShippingHeaderDao.class);
    ShippingHeader header = shippingHeaderDao.load(shippingNo);

    if (header == null) {
      // 出荷情報が空か出荷済み以外ならエラーとする

      result.addServiceError(OrderServiceErrorContent.NO_SHIPPING_DATA_ERROR);
      return result;
    } else if (!header.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())) {
      result.addServiceError(OrderServiceErrorContent.NOT_SHIPPED_ERROR);
      return result;
    }
    header.setFixedSalesStatus(FixedSalesStatus.FIXED.longValue());
    setUserStatus(header);

    TransactionManager manager = DIContainer.getTransactionManager();

    try {
      manager.begin(this.getLoginInfo());
      manager.update(header);
      manager.executeProcedure(new ActivateCouponProcedure(header.getOrderNo(), this.getLoginInfo().getRecordingFormat()));
      manager.executeProcedure(new ActivatePointProcedure(header.getOrderNo(), this.getLoginInfo().getRecordingFormat()));
      manager.commit();
      CommonLogic.verifyPointDifference(header.getCustomerCode(), PointIssueStatus.ENABLED); // 10.1.3
      // 10174
      // 追加
    } catch (ConcurrencyFailureException e) {
      manager.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR.toString(), e);
    } catch (RuntimeException e) {
      manager.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR.toString(), e);
    } finally {
      manager.dispose();
    }

    return result;
  }

  /**
   * 購入金額と適用日から、該当するポイント付与率を取得する。<BR>
   * ポイントが適用されない状態（ポイントルールの適用が無効・購入金額がポイントを付与する最低購入金額以下等） の場合、<BR>
   * 付与率がマイナスに設定される。<BR>
   * 事前条件：適用日付がYYYY/MM/DDで正しいフォーマットの日付であること。<BR>
   * 時間以下(HH24:mi:ss など)は、切り捨てて処理を行う。<BR>
   * 
   * @param purchasePrice
   *          購入金額
   * @param appliedDate
   *          適用日
   * @return ポイント付与率
   */
  public Long getAppliedPointRule(BigDecimal purchasePrice, Date appliedDate) {
    PointRuleDao pointRuledao = DIContainer.getDao(PointRuleDao.class);
    List<PointRule> pointRuleList = pointRuledao.loadAll();
    PointRule pointRule = null;

    if (pointRuleList.size() > 0) {
      pointRule = pointRuledao.loadAll().get(0);
    }

    if (pointRule == null) {
      throw new RuntimeException();
    }

    // ポイントルール適用確認用フラグの生成
    boolean pointFunctionEnabledFlg = pointRule.getPointFunctionEnabledFlg().equals(PointFunctionEnabledFlg.ENABLED.longValue());
    boolean isOrverInvestPurchasePrice = BigDecimalUtil.isAboveOrEquals(purchasePrice, pointRule.getPointInvestPurchasePrice());

    // ポイントルールに関するポイント情報作成

    if (pointFunctionEnabledFlg && isOrverInvestPurchasePrice) {
      Long pointRuleRate = pointRule.getPointRate();
      // ボーナス期間の取得

      Date startDate = pointRule.getBonusPointStartDate();
      Date endDate = pointRule.getBonusPointEndDate();

      if (appliedDate == null) {
        appliedDate = DateUtil.getSysdate();
      } else {
        appliedDate = DateUtil.fromString(DateUtil.toDateString(appliedDate));
      }
      // ボーナス期間の場合、ボーナス付与率にする。

      if (startDate != null || endDate != null) {
        if (startDate == null) {
          startDate = DateUtil.getMin();
        }
        if (endDate == null) {
          endDate = DateUtil.getMax();
        }

        // ボーナス期間かボーナス日付の場合ボーナスレートを指定
        if (DateUtil.isPeriodDate(startDate, endDate, appliedDate)
            || (pointRule.getBonusPointDate().equals(NumUtil.toLong(DateUtil.getDD(appliedDate))))) {
          pointRuleRate = pointRule.getBonusPointTermRate();
        }
        // 10.1.4 10117 修正 ここから
        // }
      } else if (pointRule.getBonusPointDate().equals(NumUtil.toLong(DateUtil.getDD(appliedDate)))) {
        // 期間未指定(startDateもendDateもNULL)でも、ボーナス日ならボーナスレート適用

        pointRuleRate = pointRule.getBonusPointTermRate();
      }
      // 10.1.4 10117 修正 ここまで
      return pointRuleRate;
    }
    return -1L;
  }

  public Long getOrderCount(OrderCountSearchCondition condition) {
    return NumUtil.toLong(DatabaseUtil.executeScalar(new OrderCountSearchQuery(condition)).toString());
  }

  public OrderDetail getOrderDetail(String orderNo, String shopCode, String skuCode) {
    OrderDetailDao dao = DIContainer.getDao(OrderDetailDao.class);
    return dao.load(orderNo, shopCode, skuCode);
  }

  public List<OrderDetail> getOrderDetailList(String orderNo) {
    OrderDetailDao oDetailDao = DIContainer.getDao(OrderDetailDao.class);
    return oDetailDao.findByQuery(OrderServiceQuery.ORDER_DETAIL_LIST_QUERY, orderNo);
  }

  public List<TmallOrderDetail> getTmallOrderDetailList(String orderNo) {
    TmallOrderDetailDao oDetailDao = DIContainer.getDao(TmallOrderDetailDao.class);
    return oDetailDao.findByQuery(OrderServiceQuery.TMALL_ORDER_DETAIL_LIST_QUERY, orderNo);
  }

  public List<JdOrderDetail> getJdOrderDetailList(String orderNo) {
    Query query = new SimpleQuery(OrderServiceQuery.JD_ORDER_DETAIL_LIST_QUERY, orderNo);
    List<JdOrderDetail> jdOrderDetails =  DatabaseUtil.loadAsBeanList(query, JdOrderDetail.class);
    return jdOrderDetails;
  }
  
  public OrderHeader getOrderHeader(String orderNo) {
    OrderHeaderDao oHeaderDao = DIContainer.getDao(OrderHeaderDao.class);
    return oHeaderDao.load(orderNo);
  }

  public TmallOrderHeader getTmallOrderHeader(String orderNo) {
    TmallOrderHeaderDao oHeaderDao = DIContainer.getDao(TmallOrderHeaderDao.class);
    return oHeaderDao.load(orderNo);
  }

  public JdOrderHeader getJdOrderHeader(String orderNo) {
    JdOrderHeaderDao oHeaderDao = DIContainer.getDao(JdOrderHeaderDao.class);
    return oHeaderDao.load(orderNo);
  }
  
  // soukai add 2011/12/29 ob start
  // ＴＭＡＬＬ出荷ヘッダ情報を取得する
  public TmallShippingHeader getTmallShippingHeader(String shippingNo) {
    TmallShippingHeaderDao tmallShippingHeaderDao = DIContainer.getDao(TmallShippingHeaderDao.class);
    return tmallShippingHeaderDao.load(shippingNo);
  }
  
  // soukai add 2011/12/29 ob end

  public TmallOrderHeader getTmallOrderHeaderByTid(String tid) {
    TmallOrderHeaderDao oHeaderDao = DIContainer.getDao(TmallOrderHeaderDao.class);
    return oHeaderDao.loadByTid(tid);
  }

  public OrderHeader getOrderHeaderByPaymentOrderId(String shopCode, String paymentOrderId) {
    return DatabaseUtil.loadAsBean(
        new SimpleQuery(OrderServiceQuery.GET_ORDER_HEADER_BY_PAYMENT_ORDER_ID, shopCode, paymentOrderId), OrderHeader.class);
  }

  public List<OrderHeader> getOrderHeaderList(String shopCode) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_ORDER_HEADER_LIST_OF_SHOP_QUERY, shopCode);
    return DatabaseUtil.loadAsBeanList(query, OrderHeader.class);
  }

  public OrderContainer getOrder(String orderNo) {
    return getOrder(orderNo, null, null);
  }

  public OrderContainer getTmallOrder(String orderNo) {
    return getTmallOrder(orderNo, null, null);
  }

  public OrderContainer getJdOrder(String orderNo) {
    return getJdOrder(orderNo, null, null);
  }
  
  public OrderContainer getTmallOrderByTid(String TmallId) {
    return getTmallOrderByTid(TmallId, null, null);
  }

  public OrderSummary getOrderSummary(String orderNo) {
    Query query = new SimpleQuery("select * from order_summary_view2 where order_no = ?", orderNo);
    OrderSummary summary = DatabaseUtil.loadAsBean(query, OrderSummary.class);
    return summary;
  }

  public OrderSummary getOrderSummary(String orderNo, String orderType) {
    Query query = new SimpleQuery("select * from order_summary_view where order_no = ? and order_type = ? ", orderNo, orderType);
    OrderSummary summary = DatabaseUtil.loadAsBean(query, OrderSummary.class);
    return summary;
  }
  
  public OrderSummary getOrderSummary2(String orderNo, String orderType) {
    Query query = new SimpleQuery("select * from order_summary_view2 where order_no = ? and order_type = ? ", orderNo, orderType);
    OrderSummary summary = DatabaseUtil.loadAsBean(query, OrderSummary.class);
    return summary;
  }

  public List<OrderSummary> getOrderSummaryList(boolean loadFixedOrder) {
    Query query = null;
    String queryString = "select * from order_summary_view ";
    if (loadFixedOrder) {
      query = new SimpleQuery(queryString);
    } else {
      queryString += "where fixed_sales_status = ?";
      query = new SimpleQuery(queryString, FixedSalesStatus.NOT_FIXED.getValue());
    }

    return DatabaseUtil.loadAsBeanList(query, OrderSummary.class);
  }

  public List<ShippingHeader> getShippingHeaderListForSalsesFix() {
    Query query = null;
    String queryString = "SELECT * FROM SHIPPING_HEADER SH ";
    queryString += "WHERE SH.FIXED_SALES_STATUS = ? ";
    queryString += "AND SH.SHIPPING_STATUS = ? ";
    query = new SimpleQuery(queryString, FixedSalesStatus.NOT_FIXED.getValue(), ShippingStatus.SHIPPED.getValue());

    return DatabaseUtil.loadAsBeanList(query, ShippingHeader.class);
  }

  public ShippingHeader getShippingStatusByOrderNo(String orderNo) {
    Query query = new SimpleQuery(OrderServiceQuery.SHIPPING_STATUS_BY_ORDER_NO, orderNo);
    return DatabaseUtil.loadAsBean(query, ShippingHeader.class);
  }

  public Long getSalesAmount(OrderListSearchCondition condition) {
    return NumUtil.toLong(DatabaseUtil.executeScalar(new OrderSumRetailPriceSearchQuery(condition)).toString());
  }

  public ShippingContainer getShipping(String shippingNo) {
    ShippingContainer container = new ShippingContainer();
    container.setShippingHeader(getShippingHeader(shippingNo));
    container.setShippingDetails(getShippingDetailList(shippingNo));
    container.setTmallShippingHeader(getTmallShippingHeader(shippingNo));
    return container;
  }
  
  public ShippingContainer getJdShipping(String shippingNo) {
    ShippingContainer container = new ShippingContainer();
    container.setJdShippingHeader(getJdShippingHeader(shippingNo));
    container.setJdShippingDetails(getJdShippingDetailList(shippingNo));
    return container;
  }

  public Long getShippingCount(ShippingListSearchCondition condition) {
    return NumUtil.toLong(DatabaseUtil.executeScalar(new ShippingCountSearchQuery(condition)).toString());
  }

  public List<ShippingDetail> getShippingDetailList(String shippingNo) {
    ShippingDetailDao sDetailDao = DIContainer.getDao(ShippingDetailDao.class);
    return sDetailDao.findByQuery(OrderServiceQuery.SHIPPING_DETAIL_LIST_QUERY, shippingNo);
  }

  private List<TmallShippingDetail> getTmallShippingDetailList(String shippingNo) {
    TmallShippingDetailDao sDetailDao = DIContainer.getDao(TmallShippingDetailDao.class);
    return sDetailDao.findByQuery(OrderServiceQuery.TMALL_SHIPPING_DETAIL_LIST_QUERY, shippingNo);
  }
  
  private List<JdShippingDetail> getJdShippingDetailList(String shippingNo) {
    JdShippingDetailDao sDetailDao = DIContainer.getDao(JdShippingDetailDao.class);
    return sDetailDao.findByQuery(OrderServiceQuery.JD_SHIPPING_DETAIL_LIST_QUERY, shippingNo);
  }

  private OrderContainer getOrder(String orderNo, String shippingNo, String shippingDetailNo) {
    return createOrderContainer(orderNo, shippingNo, shippingDetailNo);
  }

  private OrderContainer getTmallOrder(String orderNo, String shippingNo, String shippingDetailNo) {
    return createTmallOrderContainer(orderNo, shippingNo, shippingDetailNo);
  }
  
  private OrderContainer getJdOrder(String orderNo, String shippingNo, String shippingDetailNo) {
    return createJdOrderContainer(orderNo, shippingNo, shippingDetailNo);
  }

  private OrderContainer getTmallOrderByTid(String orderNo, String shippingNo, String shippingDetailNo) {
    return createTmallOrderContainerByTid(orderNo, shippingNo, shippingDetailNo);
  }

  public ShippingHeader getShippingHeader(String shippingNo) {
    ShippingHeaderDao sHeaderDao = DIContainer.getDao(ShippingHeaderDao.class);
    return sHeaderDao.load(shippingNo);
  }
  
  public JdShippingHeader getJdShippingHeader(String shippingNo) {
    JdShippingHeaderDao sHeaderDao = DIContainer.getDao(JdShippingHeaderDao.class);
    return sHeaderDao.load(shippingNo);
  }

  public List<ShippingContainer> getShippingList(String orderNo) {
    List<ShippingContainer> containerList = new ArrayList<ShippingContainer>();
    ShippingHeaderDao sHeaderDao = DIContainer.getDao(ShippingHeaderDao.class);
    List<ShippingHeader> headerList = sHeaderDao.findByQuery(OrderServiceQuery.SHIPPING_HEADER_LIST_QUERY, orderNo);
    for (ShippingHeader header : headerList) {
      ShippingContainer container = new ShippingContainer();
      container.setShippingHeader(header);
      container.setShippingDetails(getShippingDetailList(header.getShippingNo()));
      containerList.add(container);
    }
    return containerList;
  }

  public List<ShippingContainer> getTmallShippingList(String Tid) {
    List<ShippingContainer> containerList = new ArrayList<ShippingContainer>();
    TmallShippingHeaderDao sHeaderDao = DIContainer.getDao(TmallShippingHeaderDao.class);
    List<TmallShippingHeader> headerList = sHeaderDao.findByQuery(OrderServiceQuery.TMALL_SHIPPING_HEADER_LIST_QUERY, Tid);
    for (TmallShippingHeader header : headerList) {
      ShippingContainer container = new ShippingContainer();
      container.setTmallShippingHeader(header);
      container.setTmallShippingDetails(getTmallShippingDetailList(header.getShippingNo()));
      containerList.add(container);
    }
    return containerList;
  }
  
  public List<ShippingContainer> getJdShippingList(String Tid) {
    List<ShippingContainer> containerList = new ArrayList<ShippingContainer>();
    JdShippingHeaderDao sHeaderDao = DIContainer.getDao(JdShippingHeaderDao.class);
    Query query = new SimpleQuery(OrderServiceQuery.JD_SHIPPING_HEADER_LIST_QUERY, Tid);
    List<JdShippingHeader> headerList =  DatabaseUtil.loadAsBeanList(query, JdShippingHeader.class);
    for (JdShippingHeader header : headerList) {
      ShippingContainer container = new ShippingContainer();
      container.setJdShippingHeader(header);
      container.setJdShippingDetails(getJdShippingDetailList(header.getShippingNo()));
      containerList.add(container);
    }
    return containerList;
  }

  public OrderContainer getOrder(String orderNo, String shippingNo) {
    return getOrder(orderNo, shippingNo, null);
  }
  
  public OrderContainer getJdOrder(String orderNo, String shippingNo) {
    return getJdOrder(orderNo, shippingNo, null);
  }

  // 2012-01-29 yyq add start
  public OrderContainer getTmallOrder(String orderNo, String shippingNo) {
    return getTmallOrder(orderNo, shippingNo, null);
  }

  // 2012-01-29 yyq add end

  public ServiceResult insertReturnOrder(OrderHeader orderHeader, ShippingHeader shippingHeader, ShippingDetail shippingDetail) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // チェック前情報の取得
    OrderHeader orderHeaderOrg = getOrderHeader(orderHeader.getOrderNo());

    TransactionManager manager = DIContainer.getTransactionManager();
    try {
      manager.begin(this.getLoginInfo());

      ArchivedOrderProcedure archivedOrder = new ArchivedOrderProcedure(orderHeader.getOrderNo());
      manager.executeProcedure(archivedOrder);

      // 受注ヘッダ情報はDBのヘッダ情報に必要な項目をコピーする
      orderHeaderOrg.setOrderNo(orderHeader.getOrderNo());
      orderHeaderOrg.setMessage(orderHeader.getMessage());
      orderHeaderOrg.setCaution(orderHeader.getCaution());
      orderHeaderOrg.setUpdatedDatetime(orderHeader.getUpdatedDatetime());

      long shippingNo = DatabaseUtil.generateSequence(SequenceType.SHIPPING_NO);

      shippingHeader.setShippingNo(NumUtil.toString(shippingNo));
      shippingHeader.setReturnItemDate(DatabaseUtil.getSystemDatetime());
      shippingHeader.setFixedSalesStatus(FixedSalesStatus.NOT_FIXED.longValue());
      setUserStatus(shippingHeader);

      shippingDetail.setShippingNo(NumUtil.toString(shippingNo));
      setUserStatus(shippingDetail);

      // validateチェック
      Logger logger = Logger.getLogger(this.getClass());
      ValidationSummary validationResult = BeanValidator.validate(orderHeaderOrg);
      if (validationResult.hasError()) {
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String s : validationResult.getErrorMessages()) {
          logger.debug(s);
        }
        manager.rollback();
        return serviceResult;
      }

      validationResult = BeanValidator.validate(shippingHeader);
      if (validationResult.hasError()) {
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String s : validationResult.getErrorMessages()) {
          logger.debug(s);
        }
        manager.rollback();
        return serviceResult;
      }

      manager.update(orderHeaderOrg);

      manager.insert(shippingHeader);

      // 出荷明細のSKUコードが未入力の場合、商品無し返品・金額調整とみなし
      // 出荷明細情報は作成しない
      if (StringUtil.hasValue(shippingDetail.getSkuCode())) {
        validationResult = BeanValidator.validate(shippingDetail);
        if (validationResult.hasError()) {
          serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
          for (String s : validationResult.getErrorMessages()) {
            logger.debug(s);
          }
          manager.rollback();
          return serviceResult;
        }
        manager.insert(shippingDetail);
      }

      manager.commit();

    } catch (ConcurrencyFailureException e) {
      manager.rollback();
      throw e;
    } catch (RuntimeException e) {
      manager.rollback();
      Logger.getLogger(this.getClass()).error(e);
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }

    return serviceResult;
  }

  public ServiceResult registerShippingReport(InputShippingReport inputShippingReport) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    ShippingHeaderDao shippingDao = DIContainer.getDao(ShippingHeaderDao.class);
    ShippingHeader updateShippingHeader = shippingDao.load(inputShippingReport.getShippingNo());
    // 10.1.4 10182 削除 ここから
    // ShippingHeader orgShippingHeader =
    // shippingDao.load(inputShippingReport.getShippingNo());
    // 10.1.4 10182 削除 ここまで

    if (updateShippingHeader == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    OrderHeaderDao orderDao = DIContainer.getDao(OrderHeaderDao.class);
    OrderHeader orderHeader = orderDao.load(updateShippingHeader.getOrderNo());

    if (orderHeader == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    if (orderHeader.getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
      serviceResult.addServiceError(OrderServiceErrorContent.SHIPPING_IS_RESERVED_ERROR);
      return serviceResult;
    }

    if (updateShippingHeader.getShippingDirectDate() == null) {
      // 出荷指示日が設定されていない場合は、出荷日と同一日付を設定

      inputShippingReport.setShippingDirectDate(inputShippingReport.getShippingDate());
    } else {
      inputShippingReport.setShippingDirectDate(updateShippingHeader.getShippingDirectDate());
    }

    updateShippingHeader.setArrivalDate(inputShippingReport.getArrivalDate());
    updateShippingHeader.setArrivalTimeEnd(inputShippingReport.getArrivalTimeEnd());
    updateShippingHeader.setArrivalTimeStart(inputShippingReport.getArrivalTimeStart());
    updateShippingHeader.setDeliverySlipNo(inputShippingReport.getDeliverySlipNo());
    updateShippingHeader.setShippingDate(inputShippingReport.getShippingDate());
    updateShippingHeader.setShippingDirectDate(inputShippingReport.getShippingDirectDate());
    updateShippingHeader.setShippingNo(inputShippingReport.getShippingNo());
    updateShippingHeader.setShippingStatus(ShippingStatus.SHIPPED.longValue());
    updateShippingHeader.setUpdatedUser(inputShippingReport.getUpdatedUser());
    updateShippingHeader.setUpdatedDatetime(inputShippingReport.getUpdatedDatetime());

    if (updateShippingHeader.getShippingDate().before(DateUtil.truncateDate(orderHeader.getOrderDatetime()))) {
      // 出荷日が受注日よりも前の日付の場合はエラー

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    if (updateShippingHeader.getShippingDate().before(updateShippingHeader.getShippingDirectDate())) {
      // 出荷日が出荷指示日よりも前の日付の場合はエラー

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    if (NumUtil.toString(updateShippingHeader.getFixedSalesStatus()).equals(FixedSalesStatus.FIXED.getValue())) {
      // 売上確定ステータスが「確定済み」の場合はステータスの変更不可

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    if (NumUtil.toString(updateShippingHeader.getShippingStatus()).equals(ShippingStatus.CANCELLED.getValue())) {
      // 出荷ステータスが「キャンセル」の場合出荷指示不可
      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    if (orderHeader.getAdvanceLaterFlg().equals(AdvanceLaterFlg.ADVANCE.longValue())) {
      if (NumUtil.toString(updateShippingHeader.getShippingStatus()).equals(ShippingStatus.READY.getValue())
          || (NumUtil.toString(updateShippingHeader.getShippingStatus()).equals(ShippingStatus.IN_PROCESSING.getValue()))
          || (NumUtil.toString(updateShippingHeader.getShippingStatus()).equals(ShippingStatus.SHIPPED.getValue()))) {
        // 先払い区分が「先払い」の場合は、出荷ステータスが「出荷可能」「出荷手配中」「出荷済み」の場合のみ出荷指示可能
        logger.debug(Messages.log("service.impl.OrderServiceImpl.3"));
      } else {
        serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
        return serviceResult;
      }
    }

    orderHeader.setUpdatedDatetime(inputShippingReport.getOrderUpdatedDatetime());

    ValidationSummary summary = BeanValidator.validate(updateShippingHeader);
    if (summary.hasError()) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : summary.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }

    List<ShippingDetail> detailList = getShippingDetailList(inputShippingReport.getShippingNo());
    List<StockUnit> stockUtilList = new ArrayList<StockUnit>();
    for (ShippingDetail detail : detailList) {
      StockUnit unit = new StockUnit();
      unit.setShopCode(detail.getShopCode());
      unit.setSkuCode(detail.getSkuCode());
      unit.setStockIODate(DateUtil.getSysdate());
      unit.setQuantity(detail.getPurchasingAmount().intValue());
      unit.setLoginInfo(getLoginInfo());
      stockUtilList.add(unit);
    }
    // 10.1.4 10182 削除 ここから
    // boolean sendShippingMailFlg =
    // isAvailableShippingMail(inputShippingReport, orgShippingHeader);
    // 10.1.4 10182 削除 ここまで

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());

      txMgr.update(orderHeader);

      StockManager stockManager = txMgr.getStockManager();
      if (!stockManager.shipping(stockUtilList)) {
        serviceResult.addServiceError(OrderServiceErrorContent.INPUT_ADEQUATE_ERROR);
        txMgr.rollback();
        return serviceResult;
      }

      txMgr.update(updateShippingHeader);
      txMgr.commit();
      // 10.1.4 10182 修正 ここから
      // performShippingEvent(new
      // ShippingEvent(updateShippingHeader.getShippingNo(),
      // sendShippingMailFlg),
      // ShippingEventType.FULFILLED);
      boolean mailSendFlg = isAvailableShippingMail(updateShippingHeader.getShippingNo());
      performShippingEvent(new ShippingEvent(updateShippingHeader.getShippingNo(), mailSendFlg, "mail"),
          ShippingEventType.FULFILLED);
      // Add by V10-CH start
      boolean smsSendFlg = isAvailableShippingSms(updateShippingHeader.getShippingNo());
      performShippingEvent(new ShippingEvent(updateShippingHeader.getShippingNo(), smsSendFlg, "sms"), ShippingEventType.FULFILLED);
      // Add by V10-CH end
      // 10.1.4 10182 修正 ここまで
    } catch (ConcurrencyFailureException e) {
      logger.error(e);
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  // 10.1.4 10182 削除 ここから
  // /**
  // * 出荷実績登録時に、メール送信可能かどうか判別するメソッド
  // */
  // private boolean isAvailableShippingMail(InputShippingReport
  // inputShippingReport, ShippingHeader shippingHeader) {
  // String shippingNo = inputShippingReport.getShippingNo();
  // if (getShippingMailCount(shippingNo).equals(0L)) {
  // return true;
  // }
  // if
  // (shippingHeader.getShippingStatus().equals(ShippingStatus.IN_PROCESSING.longValue()))
  // {
  // return true;
  // }
  // if
  // (!inputShippingReport.getShippingDate().equals(shippingHeader.getShippingDate()))
  // {
  // return true;
  // }
  //
  // return false;
  // }
  // 10.1.4 10182 削除 ここまで
  // 10.1.4 10182 追加 ここから
  private boolean isAvailableShippingMail(String shippingNo) {
    boolean result = false;
    ShippingHeaderDao dao = DIContainer.getDao(ShippingHeaderDao.class);
    ShippingHeader sh = dao.load(shippingNo);
    result = (sh != null) // 存在チェック
        && sh.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue()); // 出荷済み
    return result;
  }

  // Add by V10-CH start
  private boolean isAvailableShippingSms(String shippingNo) {
    boolean result = false;
    ShippingHeaderDao dao = DIContainer.getDao(ShippingHeaderDao.class);
    ShippingHeader sh = dao.load(shippingNo);
    result = (sh != null) // 存在チェック
        && sh.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue()); // 出荷済み
    return result;
  }

  // Add by V10-CH end

  // 10.1.4 10182 追加 ここまで

  public ServiceResult registerShippingDirection(InputShippingReport inputShippingReport) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    ShippingHeaderDao shippingDao = DIContainer.getDao(ShippingHeaderDao.class);
    ShippingHeader updateShippingHeader = shippingDao.load(inputShippingReport.getShippingNo());

    if (updateShippingHeader == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    OrderHeaderDao orderDao = DIContainer.getDao(OrderHeaderDao.class);
    OrderHeader orderHeader = orderDao.load(updateShippingHeader.getOrderNo());

    if (orderHeader == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    if (orderHeader.getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
      serviceResult.addServiceError(OrderServiceErrorContent.SHIPPING_IS_RESERVED_ERROR);
      return serviceResult;
    }

    updateShippingHeader.setUpdatedUser(inputShippingReport.getUpdatedUser());
    updateShippingHeader.setUpdatedDatetime(inputShippingReport.getUpdatedDatetime());

    if (NumUtil.toString(orderHeader.getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED.getValue())) {
      // データ連携済みステータスが「連携済み」の場合はステータスの変更不可

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    if (NumUtil.toString(updateShippingHeader.getFixedSalesStatus()).equals(FixedSalesStatus.FIXED.getValue())) {
      // 売上確定ステータスが「確定済み」の場合はステータスの変更不可

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    if (NumUtil.toString(orderHeader.getAdvanceLaterFlg()).equals(AdvanceLaterFlg.ADVANCE.getValue())) {
      if (NumUtil.toString(updateShippingHeader.getShippingStatus()).equals(ShippingStatus.READY.getValue())) {
        // 後先払い区分が「先払い」の場合は、出荷ステータスが「出荷可能」の場合のみ出荷指示可能
        logger.debug(Messages.log("service.impl.OrderServiceImpl.4"));
      } else {
        serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
        return serviceResult;
      }
    }

    if (!NumUtil.toString(updateShippingHeader.getShippingStatus()).equals(ShippingStatus.READY.getValue())) {
      // 「出荷可能」の場合のみ出荷指示可能
      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    if (updateShippingHeader.getShippingDirectDate() == null) {
      // 出荷指示日空の場合はエラー

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    orderHeader.setUpdatedDatetime(inputShippingReport.getOrderUpdatedDatetime());

    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      txMgr.begin(this.getLoginInfo());
      txMgr.update(orderHeader);

      // 出荷ステータスを「出荷手配中」に変更
      updateShippingHeader.setShippingStatus(ShippingStatus.IN_PROCESSING.longValue());

      ValidationSummary summary = BeanValidator.validate(updateShippingHeader);
      if (summary.hasError()) {
        logger = Logger.getLogger(this.getClass());
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String rs : summary.getErrorMessages()) {
          logger.debug(rs);
        }
        return serviceResult;
      }

      txMgr.update(updateShippingHeader);
      txMgr.commit();
      performShippingEvent(new ShippingEvent(updateShippingHeader.getShippingNo()), ShippingEventType.ARRANGED);
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return serviceResult;

  }

  public ServiceResult registerShippingDirectionDate(InputShippingReport inputShippingReport, boolean updateShippingStatus) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    ShippingHeaderDao shippingDao = DIContainer.getDao(ShippingHeaderDao.class);
    ShippingHeader updateShippingHeader = shippingDao.load(inputShippingReport.getShippingNo());

    if (updateShippingHeader == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    OrderHeaderDao orderDao = DIContainer.getDao(OrderHeaderDao.class);
    OrderHeader orderHeader = orderDao.load(updateShippingHeader.getOrderNo());

    if (orderHeader == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    if (orderHeader.getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
      serviceResult.addServiceError(OrderServiceErrorContent.SHIPPING_IS_RESERVED_ERROR);
      return serviceResult;
    }

    updateShippingHeader.setArrivalDate(inputShippingReport.getArrivalDate());
    updateShippingHeader.setArrivalTimeEnd(inputShippingReport.getArrivalTimeEnd());
    updateShippingHeader.setArrivalTimeStart(inputShippingReport.getArrivalTimeStart());
    updateShippingHeader.setDeliverySlipNo(inputShippingReport.getDeliverySlipNo());
    updateShippingHeader.setShippingDate(inputShippingReport.getShippingDate());
    updateShippingHeader.setShippingDirectDate(inputShippingReport.getShippingDirectDate());
    updateShippingHeader.setShippingNo(inputShippingReport.getShippingNo());
    updateShippingHeader.setUpdatedUser(inputShippingReport.getUpdatedUser());
    updateShippingHeader.setUpdatedDatetime(inputShippingReport.getUpdatedDatetime());

    if (NumUtil.toString(orderHeader.getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED.getValue())) {
      // データ連携済みステータスが「連携済み」の場合はステータスの変更不可

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    if (NumUtil.toString(updateShippingHeader.getFixedSalesStatus()).equals(FixedSalesStatus.FIXED.getValue())) {
      // 売上確定ステータスが「確定済み」の場合はステータスの変更不可

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    if (NumUtil.toString(orderHeader.getAdvanceLaterFlg()).equals(AdvanceLaterFlg.ADVANCE.getValue())) {
      if (NumUtil.toString(updateShippingHeader.getShippingStatus()).equals(ShippingStatus.READY.getValue())) {
        // 後先払い区分が「先払い」の場合は、出荷ステータスが「出荷可能」の場合のみ出荷指示可能
        logger.debug(Messages.log("service.impl.OrderServiceImpl.5"));
      } else {
        serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
        return serviceResult;
      }
    }

    if (!NumUtil.toString(updateShippingHeader.getShippingStatus()).equals(ShippingStatus.READY.getValue())) {
      // 「出荷可能」の場合のみ出荷指示可能
      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    if (updateShippingHeader.getShippingDirectDate() != null
        && updateShippingHeader.getShippingDirectDate().before(DateUtil.truncateDate(orderHeader.getOrderDatetime()))) {
      // 出荷指示日が受注日よりも前の日付の場合はエラー

      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    orderHeader.setUpdatedDatetime(inputShippingReport.getOrderUpdatedDatetime());

    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      txMgr.begin(this.getLoginInfo());
      txMgr.update(orderHeader);

      if (updateShippingStatus) {
        // 出荷ステータス更新を行う場合を「出荷手配中」に変更
        updateShippingHeader.setShippingStatus(ShippingStatus.IN_PROCESSING.longValue());
      }

      ValidationSummary summary = BeanValidator.validate(updateShippingHeader);
      if (summary.hasError()) {
        logger = Logger.getLogger(this.getClass());
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String rs : summary.getErrorMessages()) {
          logger.debug(rs);
        }
        return serviceResult;
      }

      txMgr.update(updateShippingHeader);
      txMgr.commit();
      performShippingEvent(new ShippingEvent(updateShippingHeader.getShippingNo()), ShippingEventType.ARRANGED);
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return serviceResult;

  }

  public SearchResult<OrderHeadline> searchOrderList(OrderListSearchCondition condition) {
    return DatabaseUtil.executeSearch(new OrderListSearchQuery(condition));
  }

  // soukai add 2012/01/14 ob start
  public BigDecimal getTotalOrderPrice(OrderListSearchCondition condition) {
    condition.setTotalPriceFlg(true);
    Object object = DatabaseUtil.executeScalar(new OrderListSearchQuery(condition));
    condition.setTotalPriceFlg(false);
    if (object != null) {
      return new BigDecimal(object.toString());
    } else {
      return BigDecimal.ZERO;
    }
  }

  // soukai add 2012/01/14 ob end
  /**
   * ShippingContainer(出荷ヘッダのデータセット)のリストを取得し、 獲得ポイントを設定します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <ol>
   * <li>モールのポイントルールを取得します。</li>
   * <li>モールのポイントルールの設定が、ポイントシステム使用しないの場合、獲得ポイントを設定しません。</li>
   * <li>支払金額が、ポイント付与最低金額を下回る場合、獲得ポイントを設定しません。</li>
   * <li>ポイントルールの付与率を取得します。ボーナス期間の場合は、ボーナス付与率を取得します。</li>
   * <li>使用ポイント比率を算出します。(注文の合計金額-使用ポイント)/(注文の合計金額-使用ポイント)が比率となります。</li>
   * <li>商品別にポイントルールの付与ポイントを算出します。商品別ポイント付与率が設定されている商品は、商品別付与率を優先し、設定されていない場合は、
   * ポイントルールの付与率を適用させます。</li>
   * <li>商品別に会員別ポイントの付与ポイントを算出します。</li>
   * <li>商品別の付与ポイント（ポイントルール分＋顧客グループ分）に、使用ポイント比率を掛けます。</li>
   * <li>明細ごと（商品別×数量）に算出した付与ポイントを出荷単位に集計します。</li>
   * <li>集計したポイントをOrderContainerに設定します。</li> </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>OrderContainer のgetOrderDetails()とgetShippingDetails()に受注情報が設定されていること。</dd>
   * <dd>ポイントルールのデータが1件存在すること</dd>
   * <dd>受注日が空の場合、当日日付を受注日として作成</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>OrderContainer.ShippingHeader.AcquiredPointに集計されたポイントが設定されます。</dd>
   * </dl>
   * </p>
   * 
   * @param orderContainer
   *          受注情報
   * @throws RuntimeException
   *           - ポイントルールが取得できなかった場合
   */
  public void setAcquiredPoint(OrderContainer orderContainer) {
    // ゲストの場合全付与ポイント0で設定

    String customerCode = orderContainer.getOrderHeader().getCustomerCode();
    if (customerCode.equals(CustomerConstant.GUEST_CUSTOMER_CODE) || CustomerConstant.isGuest(customerCode)) {
      for (ShippingContainer shipping : orderContainer.getShippings()) {
        shipping.getShippingHeader().setAcquiredPoint(BigDecimal.ZERO);
      }
      for (OrderDetail detail : orderContainer.getOrderDetails()) {
        detail.setAppliedPointRate(0L);
      }
      return;
    }

    // 購入金額＋使用ポイントの生成
    BigDecimal purchasePrice = BigDecimal.ZERO;
    BigDecimal usePoint = orderContainer.getOrderHeader().getUsedPoint();
    for (ShippingContainer shipping : orderContainer.getShippings()) {
      for (ShippingDetail detail : shipping.getShippingDetails()) {
        purchasePrice = purchasePrice.add(BigDecimalUtil.multiply(detail.getRetailPrice(), detail.getPurchasingAmount()));
      }
    }

    // modified by zhanghaibin start 2010-05-18
    Long pointRuleRate = getAppliedPointRule(BigDecimalUtil.subtract(purchasePrice, BigDecimalUtil.divide(usePoint, DIContainer
        .getWebshopConfig().getRmbPointRate(), PointUtil.getAcquiredPointScale(), RoundingMode.FLOOR)), orderContainer
        .getOrderHeader().getOrderDatetime());
    // modified by zhanghaibin end 2010-05-18
    // ポイントルールに関するポイント情報作成

    if (pointRuleRate >= 0L && BigDecimalUtil.isAbove(purchasePrice, BigDecimal.ZERO)) {
      // 会員別ポイント付与率を取得します。

      Long customerGroupPointRate = 0L;
      if (StringUtil.hasValue(orderContainer.getOrderHeader().getCustomerGroupCode())) {
        CustomerGroupDao custGropuDao = DIContainer.getDao(CustomerGroupDao.class);
        CustomerGroup custGroup = custGropuDao.load(orderContainer.getOrderHeader().getCustomerGroupCode());
        customerGroupPointRate = custGroup.getCustomerGroupPointRate();
      }

      // 使用ポイントを考慮したポイント比率を計算する

      // (注文の合計金額-使用ポイント) / (注文の合計金額)
      // 10,000円の注文で2000pt使用した場合、比率は0.8になり以下全ての付与ポイントが0.8倍になる。

      // 小数点以下が無限になる場合は7桁(Decimal32形式)で切り捨て

      // 商品別に付与ポイントを算出し、出荷単位で設定してきます。

      Long pointRate = pointRuleRate;
      BigDecimal acquiredPointTotal = BigDecimal.ZERO;
      for (ShippingContainer shipping : orderContainer.getShippings()) {
        // 明細単位forループ

        for (ShippingDetail shippingDetail : shipping.getShippingDetails()) {
          // ポイント付与率（商品別ポイント付与率優先 / なければポイントルールの付与率）

          Long commodityPointRate = 0L;
          BigDecimal commodityRetailPrice = BigDecimal.ZERO;
          // 出荷明細に商品単位の販売価格が存在しない為

          // 受注明細より受注時の商品ごとの販売価格及び、商品別ポイント付与率を取得する。

          for (OrderDetail orderDetail : orderContainer.getOrderDetails()) {
            if (shippingDetail.getShopCode().equals(orderDetail.getShopCode())
                && shippingDetail.getSkuCode().equals(orderDetail.getSkuCode())) {
              commodityPointRate = orderDetail.getAppliedPointRate();
              if (commodityPointRate == null) {
                orderDetail.setAppliedPointRate(pointRuleRate);
              }
              commodityRetailPrice = orderDetail.getRetailPrice();
            }
          }
          // 商品別ポイント付与率があれば、商品別ポイント付与率を設定
          if (commodityPointRate != null) {
            pointRate = commodityPointRate;
          }

          // modified by zhanghaibin start 2010-05-18
          // a.商品ごとのポイント(端数切捨て)
          BigDecimal commodityPoint = PointUtil.calculatePoint(commodityRetailPrice, pointRate);
          // b.顧客グループポイント(端数切捨て)
          BigDecimal customerPoint = PointUtil.calculatePoint(commodityRetailPrice, customerGroupPointRate);
          // modified by zhanghaibin end 2010-05-18
          // c.数量
          long quantity = NumUtil.toPrimitiveLong(shippingDetail.getPurchasingAmount(), 0L);
          // d.明細 = (a + b) × c
          BigDecimal shippingDetailPoint = BigDecimalUtil.multiply(BigDecimalUtil.add(commodityPoint, customerPoint),
              new BigDecimal(quantity));
          // e.合計 += d (明細単位の獲得ポイントを加算する)
          acquiredPointTotal = acquiredPointTotal.add(shippingDetailPoint);

        }
      }

      // 商品ごと合計獲得金額 * (お支払い合計金額 - 使用ポイント) / お支払い合計金額
      // modified by zhanghaibin start 2010-05-19
      // acquiredPointTotal =
      // priceDecimal.subtract(pointDecimal).multiply(acquiredPointTotal).divide(priceDecimal,
      // MathContext.DECIMAL32);
      BigDecimal payPrice = purchasePrice.subtract(
          usePoint.divide(DIContainer.getWebshopConfig().getRmbPointRate(), 10, RoundingMode.FLOOR)).subtract(
          orderContainer.getOrderHeader().getCouponPrice());
      if (BigDecimalUtil.isBelow(payPrice, BigDecimal.ZERO)) {
        payPrice = BigDecimal.ZERO;
      }
      acquiredPointTotal = payPrice.multiply(acquiredPointTotal).divide(purchasePrice, PointUtil.getAcquiredPointScale(),
          RoundingMode.FLOOR);
      // modified by zhanghaibin end 2010-05-19
      // 獲得ポイントを出荷ヘッダごとに按分するため商と余りを算出
      // long acquiredPointMain = acquiredPointTotal.longValue() /
      // orderContainer.getShippings().size();
      // long acquiredPointReMainder = acquiredPointTotal.longValue() %
      // orderContainer.getShippings().size();
      BigDecimal acquiredPointMain = acquiredPointTotal.divide(new BigDecimal(orderContainer.getShippings().size()), PointUtil
          .getAcquiredPointScale(), RoundingMode.FLOOR);
      BigDecimal acquiredPointReMainder = BigDecimalUtil.remainder(acquiredPointTotal, new BigDecimal(orderContainer.getShippings()
          .size()), PointUtil.getAcquiredPointScale());

      PointUtil.getCustomerPointLimit();
      for (int i = 0; i < orderContainer.getShippings().size(); i++) {
        ShippingHeader header = orderContainer.getShippings().get(i).getShippingHeader();
        if (i == 0) {
          header.setAcquiredPoint(acquiredPointMain.add(acquiredPointReMainder).setScale(PointUtil.getAcquiredPointScale(),
              RoundingMode.FLOOR));
        } else {
          header.setAcquiredPoint(acquiredPointMain);
        }
      }

    } else {
      for (OrderDetail detail : orderContainer.getOrderDetails()) {
        if (NumUtil.isNull(detail.getAppliedPointRate())) {
          detail.setAppliedPointRate(0L);
        }
      }
      // 全ての出荷ヘッダの付与ポイントに0Lを設定

      for (ShippingContainer shipping : orderContainer.getShippings()) {
        shipping.getShippingHeader().setAcquiredPoint(BigDecimal.ZERO);
      }
    }
  }

  public ServiceResult setDeliverySlipNo(String shippingNo, String deliverySlipNo) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    ShippingHeader header = new ShippingHeader();

    header.setShippingNo(shippingNo);
    header.setDeliverySlipNo(deliverySlipNo);
    setUserStatus(header);

    List<String> validationResult = BeanValidator.partialValidate(header, "shippingNo", "deliverySlipNo").getErrorMessages();
    if (validationResult.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return serviceResult;
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    txMgr.begin(this.getLoginInfo());
    try {
      txMgr.executeUpdate(OrderServiceQuery.UPDATE_SHIPPING_HEADER_DELIVERY_SLIP_NO_QUERY, header.getDeliverySlipNo(), this
          .getLoginInfo().getRecordingFormat(), header.getUpdatedDatetime(), header.getShippingNo());
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  public ServiceResult setPaymentDate(String orderNo, Date paymentDate) {
    return setPaymentDate(new OrderIdentifier(orderNo, null), paymentDate, false, null);
  }

  public ServiceResult setPaymentDate(OrderIdentifier identifier, Date paymentDate) {
    return setPaymentDate(identifier, paymentDate, true, null);
  }

  public ServiceResult setPaymentDate(String orderNo, Date paymentDate, BookSalesContainer bookSalesContainer) {
    return setPaymentDate(new OrderIdentifier(orderNo, null), paymentDate, false, bookSalesContainer);
  }

  /**
   * 入金日を設定します。
   */
  private ServiceResult setPaymentDate(OrderIdentifier orderIdentifier, Date paymentDate, boolean withConcurrencyControl,
      BookSalesContainer bookSalesContainer) {
    String orderNo = orderIdentifier.getOrderNo();
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    // validationチェック
    OrderHeader header = new OrderHeader();
    header.setOrderNo(orderNo);
    header.setPaymentDate(paymentDate);
    setUserStatus(header);

    List<ValidationResult> beanValidResultList = BeanValidator.partialValidate(header, "orderNo", "paymentDate").getErrors();
    if (beanValidResultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult vr : beanValidResultList) {
        Logger logger = Logger.getLogger(this.getClass());
        logger.debug(vr.getFormedMessage());
      }
    }

    OrderHeader orgOrderHeader = getOrderHeader(orderNo);
    if (orgOrderHeader == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }
    List<ShippingContainer> shippingList = getShippingList(orderNo);

    if (orgOrderHeader.getOrderStatus().equals(OrderStatus.CANCELLED.longValue())) {
      serviceResult.addServiceError(OrderServiceErrorContent.ORDER_CANCEL_ERROR);
      return serviceResult;
    }
    
    if (StringUtil.hasValue(orderIdentifier.getPaymentReceiptNo())) {
      orgOrderHeader.setPaymentReceiptNo(orderIdentifier.getPaymentReceiptNo());
    }
    if (orderIdentifier.getPaidPrice() != null) {
      orgOrderHeader.setPaidPrice(orderIdentifier.getPaidPrice());
    }

    orgOrderHeader.setPaymentStatus(NumUtil.toLong(PaymentStatus.PAID.getValue()));
    orgOrderHeader.setPaymentDate(paymentDate);
    if (withConcurrencyControl) {
      orgOrderHeader.setUpdatedDatetime(orderIdentifier.getUpdatedDatetime());
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());
      setUserStatus(orgOrderHeader);
      txMgr.update(orgOrderHeader);

      if (orgOrderHeader.getAdvanceLaterFlg().equals(AdvanceLaterFlg.ADVANCE.longValue())) {
        for (ShippingContainer shipping : shippingList) {
          ShippingHeader shippingHeader = shipping.getShippingHeader();
          if (shippingHeader.getShippingStatus().equals(ShippingStatus.NOT_READY.longValue())) {
            shippingHeader.setShippingStatus(ShippingStatus.READY.longValue());
            setUserStatus(shippingHeader);
            txMgr.update(shippingHeader);
          }

        }
      }
      txMgr.executeProcedure(new ActivatePointProcedure(header.getOrderNo(), this.getLoginInfo().getRecordingFormat()));
      performPaymentEvent(new PaymentEvent(orgOrderHeader.getOrderNo(), orgOrderHeader.getPaymentDate()), PaymentEventType.UPDATED);

      if (bookSalesContainer != null) {
        Logger logger = Logger.getLogger(this.getClass());
        // 10.1.6 10284 修正 ここから
        // PaymentProviderManager ppmgr = new PaymentProviderManager();
        PaymentProviderManager ppmgr = new PaymentProviderManager(getLoginInfo());
        // 10.1.6 10284 修正 ここまで
        boolean result = ppmgr.invoice(bookSalesContainer.getCashierPayment(), bookSalesContainer.getOrderContainer());
        if (result) {
          // logger.debug("受注番号" +
          // bookSalesContainer.getOrderContainer().getOrderNo() +
          // "の決済プロバイダ送信成功");
          logger.debug(MessageFormat.format(Messages.log("service.impl.OrderServiceImpl.6"), bookSalesContainer.getOrderContainer()
              .getOrderNo()));
        } else {
          // logger.error("受注番号" +
          // bookSalesContainer.getOrderContainer().getOrderNo() +
          // "の決済プロバイダ送信失敗");
          logger.debug(MessageFormat.format(Messages.log("service.impl.OrderServiceImpl.6"), bookSalesContainer.getOrderContainer()
              .getOrderNo()));
          serviceResult.addServiceError(OrderServiceErrorContent.CARD_SETTLEMENT_IMCOMPLETE_ERROR);
          txMgr.rollback();
          return serviceResult;
        }
      }

      txMgr.commit();
      CommonLogic.verifyPointDifference(orgOrderHeader.getCustomerCode(), PointIssueStatus.ENABLED); // 10.1.3
      // 10174
      // 追加
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  public SearchResult<ShippingList> searchShippingList(ShippingListSearchCondition condition) {
    ShippingListSearchQuery query = new ShippingListSearchQuery(condition);
    return DatabaseUtil.executeSearch(query);
  }

  public ServiceResult transportPayment(DateRange period) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    Query query = new SimpleQuery(OrderServiceQuery.SALES_TRANSPORT, PaymentMethodType.CREDITCARD.getValue(),
        PaymentStatus.NOT_PAID.getValue(), ShippingStatusSummary.SHIPPED_ALL.getValue(), period.getStart(), period.getEnd());
    List<OrderHeadline> headline = DatabaseUtil.loadAsBeanList(query, OrderHeadline.class);

    for (OrderHeadline oh : headline) {

      OrderContainer container = getOrder(oh.getOrderNo());
      CashierPayment cashierPayment = new CashierPayment();
      cashierPayment.setShopCode(container.getOrderHeader().getShopCode());
      CashierPaymentTypeBase payment = new CashierPaymentTypeCreditCard();
      payment.setPaymentMethodCode(NumUtil.toString(container.getOrderHeader().getPaymentMethodNo()));
      payment.setPaymentMethodName(container.getOrderHeader().getPaymentMethodName());
      payment.setPaymentMethodType(container.getOrderHeader().getPaymentMethodType());
      payment.setPaymentCommission(container.getOrderHeader().getPaymentCommission());
      cashierPayment.setSelectPayment(payment);
      cashierPayment.setShopCode(container.getOrderHeader().getShopCode());

      BookSalesContainer bookSalesContainer = new BookSalesContainer();
      bookSalesContainer.setCashierPayment(cashierPayment);
      bookSalesContainer.setOrderContainer(container);

      ServiceResult paymentResult = setPaymentDate(oh.getOrderNo(), DateUtil.getSysdate(), bookSalesContainer);
      for (ServiceErrorContent error : paymentResult.getServiceErrorList()) {
        serviceResult.addServiceError(error);
      }

    }

    return serviceResult;
  }

  private OrderHeader getAndSetOrgOrder(OrderHeader order, OrderContainer orderOrg) {
    // 旧受注情報を取得する
    OrderHeader orderHeaderOrg = orderOrg.getOrderHeader();
    OrderHeader orderHeader = order;
    // Org情報コピー

    orderHeader.setOrderStatus(orderHeaderOrg.getOrderStatus());
    orderHeader.setCustomerCode(orderHeaderOrg.getCustomerCode());
    orderHeader.setCustomerGroupCode(orderHeaderOrg.getCustomerGroupCode());
    orderHeader.setOrmRowid(orderHeaderOrg.getOrmRowid());
    orderHeader.setCreatedDatetime(orderHeaderOrg.getCreatedDatetime());
    orderHeader.setCreatedUser(orderHeaderOrg.getCreatedUser());
    orderHeader.setOrderDatetime(orderHeaderOrg.getOrderDatetime());

    return orderHeader;
  }

  public ServiceResult updateOrder(OrderContainer order, CashierPayment cashierPayment) {
    ServiceResultImpl result = new ServiceResultImpl();

    // 旧受注情報を取得し、必要な情報をコピーする
    OrderContainer orderOrg = getOrder(order.getOrderHeader().getOrderNo());
    OrderHeader orderHeader = getAndSetOrgOrder(order.getOrderHeader(), orderOrg);

    // 优惠券

    CustomerCoupon custmerCoupon = null;
    boolean isUseableCoupon = true;
    CouponRuleDao couponRuleDao = DIContainer.getDao(CouponRuleDao.class);
    List<CouponRule> couponRuleList = couponRuleDao.loadAll();
    CouponRule couponRule = null;
    Long couponflg = CouponFunctionEnabledFlg.DISABLED.longValue();
    if (couponRuleList.size() > 0) {
      couponRule = couponRuleList.get(0);
      couponflg = couponRule.getCouponFunctionEnabledFlg();
      if (couponflg.equals(CouponFunctionEnabledFlg.ENABLED.longValue())) {
        isUseableCoupon = true;
      } else {
        isUseableCoupon = false;
      }
    }
    List<CustomerCoupon> customerCouponList = new ArrayList<CustomerCoupon>();
    OrderCouponInfo orderCouponInfo = null;
    if (isUseableCoupon) {
      CouponIssue couponIssue = this.getNewCustomerCoupon(order);
      if (couponIssue != null) {
        long customerCouponId = DatabaseUtil.generateSequence(SequenceType.CUSTOMER_COUPON_ID);
        custmerCoupon = new CustomerCoupon();
        custmerCoupon.setCustomerCode(orderHeader.getCustomerCode());
        custmerCoupon.setUseFlg(CouponUsedFlg.PHANTOM_COUPON.longValue());
        custmerCoupon.setCouponIssueNo(couponIssue.getCouponIssueNo());
        custmerCoupon.setCustomerCouponId(customerCouponId);
        custmerCoupon.setCouponPrice(couponIssue.getCouponPrice());
        custmerCoupon.setIssueDate(DateUtil.getSysdate());
        custmerCoupon.setUseCouponStartDate(couponIssue.getUseCouponStartDate());
        custmerCoupon.setUseCouponEndDate(couponIssue.getUseCouponEndDate());
        custmerCoupon.setCouponName(couponIssue.getCouponName());
        custmerCoupon.setGetCouponOrderNo(orderHeader.getOrderNo());
        custmerCoupon.setCouponName(couponIssue.getCouponName());
        setUserStatus(custmerCoupon);
      }
      if (order.getCustomerCouponlist() != null && order.getCustomerCouponlist().size() > 0) {
        CustomerCouponDao customerCouponDao = DIContainer.getDao(CustomerCouponDao.class);
        for (CustomerCoupon customerCoupon : order.getCustomerCouponlist()) {
          CustomerCoupon cc = customerCouponDao.load(customerCoupon.getCustomerCouponId());
          if (cc != null) {
            cc.setUseFlg(CouponUsedFlg.USED.longValue());
            cc.setOrderNo(orderHeader.getOrderNo());
            cc.setUseDate(DateUtil.getSysdate());
            setUserStatus(cc);
            customerCouponList.add(cc);
          }
        }
      }
      List<CustomerCoupon> oldUseCouponList = getOrderUsedCoupon(orderHeader.getOrderNo());
      for (CustomerCoupon cc : oldUseCouponList) {
        cc.setUseFlg(CouponUsedFlg.ENABLED.longValue());
        cc.setOrderNo(StringUtil.EMPTY);
        cc.setUseDate(null);
        setUserStatus(cc);
      }

      CustomerCoupon getOldGetCoupon = getOldGetCoupon(orderHeader.getOrderNo());
      if (getOldGetCoupon != null) {
        getOldGetCoupon.setUseFlg(CouponUsedFlg.DISABLED.longValue());
        setUserStatus(getOldGetCoupon);
      }
      orderCouponInfo = new OrderCouponInfo(oldUseCouponList, customerCouponList, getOldGetCoupon, custmerCoupon);
      orderCouponInfo.setCouponFunctionEnabledFlg(couponflg);
    }

    // add by V10-CH end

    // 付与ポイントを計算して設定する(新規受注時の日付で計算)
    setAcquiredPoint(order);

    // 使用ポイントの合計値と付与ポイントの合計値を取得する
    BigDecimal usePointTotal = order.getOrderHeader().getUsedPoint();
    BigDecimal acquiredPoint = BigDecimal.ZERO;
    for (ShippingContainer shipping : order.getShippings()) {
      acquiredPoint = BigDecimalUtil.add(acquiredPoint, shipping.getShippingHeader().getAcquiredPoint());
    }

    // 修正前使用ポイントと付与ポイントの合計を取得する
    BigDecimal usePointTotalOrg = orderOrg.getOrderHeader().getUsedPoint();
    BigDecimal acquiredPointOrg = BigDecimal.ZERO;
    for (ShippingContainer shipping : orderOrg.getShippings()) {
      acquiredPointOrg = BigDecimalUtil.add(acquiredPointOrg, shipping.getShippingHeader().getAcquiredPoint());
    }

    setUserStatus(orderHeader);
    CampaignDao dao = DIContainer.getDao(CampaignDao.class);
    for (OrderDetail detail : order.getOrderDetails()) {
      if (StringUtil.hasValue(detail.getCampaignCode())) {
        Campaign campaign = dao.load(detail.getShopCode(), detail.getCampaignCode());
        if (campaign == null) {
          result.addServiceError(OrderServiceErrorContent.CAMPAIGN_FAILED);
          return result;
        }
      }
      setUserStatus(detail);
    }
    // 出荷番号、出荷明細番号の取得
    Map<String, Long> maxDetailNoMap = new HashMap<String, Long>();
    for (ShippingContainer shippingContainer : orderOrg.getShippings()) {
      Long detailNo = 0L;
      // 最大値の取得
      for (ShippingDetail detail : shippingContainer.getShippingDetails()) {
        if (detailNo <= NumUtil.coalesce(detail.getShippingDetailNo(), 0L)) {
          detailNo = detail.getShippingDetailNo() + 1;
        }
      }
      maxDetailNoMap.put(shippingContainer.getShippingNo(), detailNo);
    }
    for (ShippingContainer shippingContainer : order.getShippings()) {
      ShippingHeader header = shippingContainer.getShippingHeader();
      if (StringUtil.isNullOrEmpty(header.getShippingNo())) {
        Long shippingNo = DatabaseUtil.generateSequence(SequenceType.SHIPPING_NO);
        header.setShippingNo(NumUtil.toString(shippingNo));
      }
      setUserStatus(header);
      Long detailNo = NumUtil.coalesce(maxDetailNoMap.get(shippingContainer.getShippingNo()), 0L);
      for (ShippingDetail detail : shippingContainer.getShippingDetails()) {
        if (detail.getShippingDetailNo() == null) {
          detail.setShippingDetailNo(detailNo);
          setUserStatus(detail);
          detailNo++;
        }
        detail.setShippingNo(header.getShippingNo());
      }

    }

    // 2012-11-27 促销对应 ob add start
    for (ShippingContainer shipping : order.getShippings()) {
      Long shippingDetailNo = 0L;
      Long compositionNo = 0L;
      for (ShippingDetail sd : shipping.getShippingDetails()) {
        if (!SetCommodityFlg.OBJECTIN.longValue().equals(sd.getSetCommodityFlg())) {
          sd.setShippingDetailNo(shippingDetailNo);
          shippingDetailNo++;
        }
      }
      compositionNo = shippingDetailNo;
      for (ShippingDetail sd : shipping.getShippingDetails()) {
        if (SetCommodityFlg.OBJECTIN.longValue().equals(sd.getSetCommodityFlg())) {
          sd.setShippingDetailNo(shippingDetailNo);
          shippingDetailNo++;
        }
      }
      HashMap<ShippingDetail, List<ShippingDetailComposition>> shippingDetailContainerMap = shipping
          .getShippingDetailContainerMap();
      for (ShippingDetail sd : shipping.getShippingDetails()) {
        if (shippingDetailContainerMap.containsKey(sd)) {
          for (ShippingDetailComposition compositionDetail : shippingDetailContainerMap.get(sd)) {
            compositionDetail.setShippingNo(sd.getShippingNo());
            compositionDetail.setShippingDetailNo(sd.getShippingDetailNo());
            compositionDetail.setCompositionNo(compositionNo++);
            setUserStatus(compositionDetail);
          }
        }
      }
    }
    // 2012-11-27 促销对应 ob add end
    // 2012/11/30 ob add start
    for (OrderCampaign orderCapmaign : order.getOrderCampaigns()) {
      setUserStatus(orderCapmaign);
    }
    // 2012/11/30 ob add end
    // 一次Validationチェック
    if (!validateOrderSet(orderHeader, order.getOrderDetails(), order.getShippings())) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    // データ整合性チェック

    ShopDao shopDao = DIContainer.getDao(ShopDao.class);
    Shop shop = shopDao.load(orderHeader.getShopCode());
    if (shop == null) {
      result.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return result;
    }
    CommodityDetailDao commodityDao = DIContainer.getDao(CommodityDetailDao.class);
    for (OrderDetail detail : order.getOrderDetails()) {
      CommodityDetail commodity = commodityDao.load(detail.getShopCode(), detail.getSkuCode());
      if (commodity == null) {
        result.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
        return result;
      }

      if (orderHeader.getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
        CommodityHeaderDao commodityHeaderDao = DIContainer.getDao(CommodityHeaderDao.class);
        CommodityHeader commodityHeader = commodityHeaderDao.load(detail.getShopCode(), detail.getCommodityCode());
        if (!DateUtil.isPeriodDate(commodityHeader.getReservationStartDatetime(), commodityHeader.getReservationEndDatetime())) {
          result.addServiceError(OrderServiceErrorContent.NOT_RESERVATION_CHANGEABLE);
          return result;
        }
      }
    }

    boolean isUseablePoint = true;
    Customer customer = null;
    if (CustomerConstant.isGuest(orderHeader.getCustomerCode())) {
      // ゲスト購入の場合ポイント関連情報は使用不可とする

      isUseablePoint = false;
    } else {
      // ゲスト以外の場合はポイントルールを参照

      PointRule pointRule = CommonLogic.getPointRule(getLoginInfo());

      PointFunctionEnabledFlg flg = PointFunctionEnabledFlg.fromValue(pointRule.getPointFunctionEnabledFlg());
      if (flg == PointFunctionEnabledFlg.ENABLED) {
        isUseablePoint = true;
      } else {
        isUseablePoint = false;
      }
    }
    if (isUseablePoint) {
      isUseablePoint = true;
      CustomerDao customerDao = DIContainer.getDao(CustomerDao.class);
      customer = customerDao.load(orderHeader.getCustomerCode());
      // 顧客の残りポイントが、今回使用ポイント合計より少ない場合エラー

      BigDecimal rest = BigDecimalUtil.subtract(usePointTotal, usePointTotalOrg);
      if (ValidatorUtil.lessThan(customer.getRestPoint(), rest)) {
        // 10.1.4 10201 修正 ここから
        // result.addServiceError(OrderServiceErrorContent.USEABLE_POINT_ERROR);
        result.addServiceError(OrderServiceErrorContent.OVER_USABLE_POINT_ERROR);
        // 10.1.4 10201 修正 ここまで
        return result;
      }
    }

    order.setOrderHeader(orderHeader);
    OrderPointInfo pointInfo = new OrderPointInfo(isUseablePoint, usePointTotal, acquiredPoint);
    return updateOrder(order, cashierPayment, orderOrg, customer, pointInfo, orderCouponInfo);
  }

  private ServiceResult updateOrder(OrderContainer order, CashierPayment cashierPayment, OrderContainer orderOrg,
      Customer customer, OrderPointInfo pointInfo, OrderCouponInfo orderCouponInfo) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();

    OrderHeader orderHeader = order.getOrderHeader();
    // soukai add 2012/01/12 ob start
    NewCouponHistoryDao dao = DIContainer.getDao(NewCouponHistoryDao.class);
    NewCouponHistory orgCouponHistory = dao.load(orderOrg.getOrderHeader().getDiscountDetailCode());
    NewCouponHistory newCouponHistory = dao.load(order.getOrderHeader().getDiscountDetailCode());
    if (orgCouponHistory != null) {
      orgCouponHistory.setUseStatus(UseStatus.UNUSED.longValue());
      orgCouponHistory.setUseOrderNo(null);
      setUserStatus(orgCouponHistory);
    }
    if (newCouponHistory != null) {
      newCouponHistory.setUseStatus(UseStatus.USED.longValue());
      newCouponHistory.setUseOrderNo(order.getOrderNo());
      setUserStatus(newCouponHistory);
    }
    OrderInvoice orgInvoice = this.getOrderInvoice(order.getOrderNo());
    if (InvoiceFlg.NEED.longValue().equals(orderHeader.getInvoiceFlg())) {
      if (orgInvoice != null) {
        order.getOrderInvoice().setOrmRowid(orgInvoice.getOrmRowid());
        order.getOrderInvoice().setCreatedDatetime(orgInvoice.getCreatedDatetime());
        order.getOrderInvoice().setCreatedUser(orgInvoice.getCreatedUser());
        order.getOrderInvoice().setUpdatedDatetime(orgInvoice.getUpdatedDatetime());
        order.getOrderInvoice().setUpdatedUser(orgInvoice.getUpdatedUser());
      }
      setUserStatus(order.getOrderInvoice());
    }
    CustomerVatInvoice orgVatInvoice = this.getCustomerVatInvoice(order.getOrderHeader().getCustomerCode());
    if (order.getCustomerVatInvoice() != null && StringUtil.hasValue(order.getCustomerVatInvoice().getCustomerCode())) {
      if (orgVatInvoice != null) {
        order.getCustomerVatInvoice().setOrmRowid(orgVatInvoice.getOrmRowid());
        order.getCustomerVatInvoice().setCreatedDatetime(orgVatInvoice.getCreatedDatetime());
        order.getCustomerVatInvoice().setCreatedUser(orgVatInvoice.getCreatedUser());
        order.getCustomerVatInvoice().setUpdatedDatetime(orgVatInvoice.getUpdatedDatetime());
        order.getCustomerVatInvoice().setUpdatedUser(orgVatInvoice.getUpdatedUser());
      }
      setUserStatus(order.getCustomerVatInvoice());
    }
    // soukai add 2012/01/12 ob end
    TransactionManager txm = DIContainer.getTransactionManager();
    // 10.1.6 10284 修正 ここから
    // PaymentProviderManager ppMgr = new PaymentProviderManager(cashierPayment,
    // order);
    PaymentProviderManager ppMgr = new PaymentProviderManager(cashierPayment, order, getLoginInfo());
    // 10.1.6 10284 修正 ここまで
    try {
      txm.begin(this.getLoginInfo());
      ArchivedOrderProcedure archivedOrder = new ArchivedOrderProcedure(orderHeader.getOrderNo());
      txm.executeProcedure(archivedOrder);
      // add by V10-CH start
      if (orderCouponInfo != null
          && CouponFunctionEnabledFlg.ENABLED.longValue().equals(orderCouponInfo.getCouponFunctionEnabledFlg())) {
        if (orderCouponInfo.getOldGetCustomerCoupon() != null
            && orderCouponInfo.getNewGetCustomerCoupon() != null
            && !orderCouponInfo.getOldGetCustomerCoupon().getCustomerCouponId().equals(
                orderCouponInfo.getNewGetCustomerCoupon().getCustomerCouponId())) {
          ValidationSummary summary = BeanValidator.validate(orderCouponInfo.getOldGetCustomerCoupon());
          if (summary.hasError()) {
            for (String error : summary.getErrorMessages()) {
              logger.error(error);
            }
            result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
            return result;
          }
          txm.update(orderCouponInfo.getOldGetCustomerCoupon());
          summary = BeanValidator.validate(orderCouponInfo.getNewGetCustomerCoupon());
          if (summary.hasError()) {
            for (String error : summary.getErrorMessages()) {
              logger.error(error);
            }
            result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
            return result;
          }
          txm.insert(orderCouponInfo.getNewGetCustomerCoupon());
        }
        for (CustomerCoupon cc : orderCouponInfo.getOldUseCustomerCoupon()) {
          boolean exists = true;
          for (CustomerCoupon ccNew : orderCouponInfo.getNewUseCustomerCoupon()) {
            if (cc.getCustomerCouponId().equals(ccNew.getCustomerCouponId())) {
              exists = false;
              break;
            }
          }
          if (exists) {
            ValidationSummary summary = BeanValidator.validate(cc);
            if (summary.hasError()) {
              for (String error : summary.getErrorMessages()) {
                logger.error(error);
              }
              result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
              return result;
            }
            txm.update(cc);
          }
        }
        for (CustomerCoupon cc : orderCouponInfo.getNewUseCustomerCoupon()) {
          boolean exists = true;
          for (CustomerCoupon ccOld : orderCouponInfo.getOldUseCustomerCoupon()) {
            if (cc.getCustomerCouponId().equals(ccOld.getCustomerCouponId())) {
              exists = false;
              break;
            }
          }
          if (exists) {
            ValidationSummary summary = BeanValidator.validate(cc);
            if (summary.hasError()) {
              for (String error : summary.getErrorMessages()) {
                logger.error(error);
              }
              result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
              return result;
            }
            txm.update(cc);
          }
        }
      }
      // add by V10-CH end

      if (pointInfo.isUseablePoint() || ValidatorUtil.moreThan(orderOrg.getOrderHeader().getUsedPoint(), BigDecimal.ZERO)) {
        // update: ポイント履歴（修正前使用ポイントを無効化）

        PointHistoryDao pointHistoryDao = DIContainer.getDao(PointHistoryDao.class);
        List<PointHistory> pointHistoryList = pointHistoryDao.findByQuery(OrderServiceQuery.GET_POINT_HISTORY_BY_ORDER_NO,
            orderHeader.getOrderNo());
        for (PointHistory pointHistory : pointHistoryList) {
          pointHistory.setPointIssueStatus(NumUtil.toLong(PointIssueStatus.DISABLED.getValue()));
          pointHistory.setDescription(Messages.getString("service.impl.OrderServiceImpl.8"));
          PointUpdateProcedure pointUpdate = new PointUpdateProcedure(pointHistory);
          txm.executeProcedure(pointUpdate);
        }

        if (ValidatorUtil.moreThan(pointInfo.getUsePointTotal(), BigDecimal.ZERO)) {
          // insert: ポイント履歴（修正後使用ポイント）

          PointHistory useHistory = new PointHistory();
          useHistory.setPointHistoryId(DatabaseUtil.generateSequence(SequenceType.POINT_HISTORY_ID));
          useHistory.setCustomerCode(orderHeader.getCustomerCode());
          useHistory.setPointIssueStatus(PointIssueStatus.ENABLED.longValue());
          useHistory.setPointIssueType(PointIssueType.ORDER.longValue());
          useHistory.setOrderNo(orderHeader.getOrderNo());
          useHistory.setIssuedPoint(pointInfo.getUsePointTotal().negate());
          useHistory.setShopCode(orderHeader.getShopCode());
          useHistory.setPointUsedDatetime(DatabaseUtil.getSystemDatetime());
          useHistory.setPointIssueDatetime(DateUtil.fromString(DateUtil.getSysdateString()));
          setUserStatus(useHistory);
          PointInsertProcedure pointInsert = new PointInsertProcedure(useHistory);
          txm.executeProcedure(pointInsert);

          // ポイントのオーバーフロー時処理

          if (pointInsert.getResult() == CustomerConstant.POINT_OVERFLOW_CODE) {
            txm.rollback();
            result.addServiceError(CustomerServiceErrorContent.POINT_OVERFLOW_ERROR);
            return result;
          }
        }
      }

      ServiceResult stockResult = updateStock(txm, order, orderOrg);
      if (stockResult.hasError()) {
        return stockResult;
      }

      // 支払方法に応じて与信処理を行う。クレジットカードの場合、出荷ステータスを出荷可能に変更する
      PaymentResult paymentResult = ppMgr.entry(cashierPayment, order);
      if (paymentResult.hasError()) {
        txm.rollback();
        for (PaymentErrorContent content : paymentResult.getPaymentErrorList()) {
          result.addServiceError(content);
        }
        return result;
      }
      // 与信結果を受注ヘッダに設定

      setPaymentResultStatus(order, paymentResult);
      // 最終Validationチェック
      if (!validateOrderSet(orderHeader, order.getOrderDetails(), order.getShippings())) {
        ppMgr.cancel();
        txm.rollback();
        result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        return result;
      }
      // insert: 受注ヘッダ
      txm.update(orderHeader);
      // delete: 受注明細(ソートはショップコード、SKUコードの順に昇順)
      DatabaseUtil.sort(orderOrg.getOrderDetails());
      for (OrderDetail detail : orderOrg.getOrderDetails()) {
        txm.delete(detail);
      }
      // 2012/11/30 ob add start
      for (OrderCampaign orderCampaign : orderOrg.getOrderCampaigns()) {
        txm.delete(orderCampaign);
      }
      // 2012/11/30 ob add end
      for (ShippingContainer container : orderOrg.getShippings()) {
        // delete: 出荷ヘッダ
        ShippingHeader header = container.getShippingHeader();
        txm.delete(header);
        // delete: 出荷明細
        DatabaseUtil.sort(container.getShippingDetails());
        for (ShippingDetail detail : container.getShippingDetails()) {
          txm.delete(detail);
        }
        // 2012/11/30 ob add start
        for (ShippingDetailComposition composition : container.getShippingDetailCommpositionList()) {
          txm.delete(composition);
        }
        // 2012/11/30 ob add end
      }
      // insert: 受注明細
      for (OrderDetail detail : order.getOrderDetails()) {
        txm.insert(detail);
      }
      // 2012/11/30 ob add start
      for (OrderCampaign orderCampaign : order.getOrderCampaigns()) {
        txm.insert(orderCampaign);
      }
      // 2012/11/30 ob add end
      for (ShippingContainer container : order.getShippings()) {
        // insert: 出荷ヘッダ
        ShippingHeader header = container.getShippingHeader();
        txm.insert(header);
        // insert: 出荷明細
        for (ShippingDetail detail : container.getShippingDetails()) {
          txm.insert(detail);
          // 2012/11/30 ob add start
          List<ShippingDetailComposition> compositionList = container.getShippingDetailContainerMap().get(detail);
          if (compositionList != null && compositionList.size() > 0) {
            for (ShippingDetailComposition composition : compositionList) {
              txm.insert(composition);
            }
          }
          // 2012/11/30 ob add end
        }
      }
      if (pointInfo.isUseablePoint() && ValidatorUtil.moreThan(pointInfo.getAcquiredPoint(), BigDecimal.ZERO)) {
        // insert: ポイント履歴（修正後獲得ポイント）

        PointHistory history = new PointHistory();
        history.setPointHistoryId(DatabaseUtil.generateSequence(SequenceType.POINT_HISTORY_ID));
        history.setCustomerCode(orderHeader.getCustomerCode());
        history.setPointIssueStatus(PointIssueStatus.PROVISIONAL.longValue());
        history.setPointIssueType(PointIssueType.ORDER.longValue());
        history.setOrderNo(orderHeader.getOrderNo());
        history.setIssuedPoint(BigDecimalUtil.divide(pointInfo.getAcquiredPoint(), new BigDecimal(DIContainer.getWebshopConfig()
            .getPointMultiple()), PointUtil.getAcquiredPointScale(), RoundingMode.FLOOR));
        history.setShopCode(orderHeader.getShopCode());
        history.setPointIssueDatetime(DateUtil.fromString(DateUtil.getSysdateString()));
        setUserStatus(history);
        PointInsertProcedure pointInsert = new PointInsertProcedure(history);
        txm.executeProcedure(pointInsert);

        // ポイントのオーバーフロー時処理

        if (pointInsert.getResult() == CustomerConstant.POINT_OVERFLOW_CODE) {
          ppMgr.cancel();
          txm.rollback();
          result.addServiceError(CustomerServiceErrorContent.POINT_OVERFLOW_ERROR);
          return result;
        }
      }
      // soukai add 2012/01/10 ob start
      if (order.getCustomerVatInvoice() != null && StringUtil.hasValue(order.getCustomerVatInvoice().getCustomerCode())) {
        if (orgVatInvoice != null) {
          txm.update(order.getCustomerVatInvoice());
        } else {
          txm.insert(order.getCustomerVatInvoice());
        }
      }
      if (InvoiceFlg.NEED.longValue().equals(order.getOrderHeader().getInvoiceFlg())) {
        if (orgInvoice != null) {
          txm.update(order.getOrderInvoice());
        } else {
          txm.insert(order.getOrderInvoice());
        }
      } else {
        if (orgInvoice != null) {
          txm.delete(orgInvoice);
        }
      }
      if (orgCouponHistory != null && newCouponHistory != null) {
        if (orgCouponHistory.getCouponIssueNo().equals(newCouponHistory.getCouponIssueNo())) {
          txm.update(newCouponHistory);
        } else {
          txm.update(orgCouponHistory);
          txm.update(newCouponHistory);
        }
      } else {
        if (orgCouponHistory != null) {
          txm.update(orgCouponHistory);
        }
        if (newCouponHistory != null) {
          txm.update(newCouponHistory);
        }
      }
      // soukai add 2012/01/10 ob end
      // soukai add 2012/02/04 ob start
      
      // 2013/04/18 优惠券对应 ob add start
      // 朋友介绍发行的优惠券履历插入
      Query query = new SimpleQuery(OrderServiceQuery.UPDATE_FRIEND_COUPON_USE_HISTORY, PointIssueStatus.DISABLED.longValue(), orderHeader.getOrderNo());
      txm.executeUpdate(query);
      
      FriendCouponIssueHistoryDao friendCouponIssueHistoryDao = DIContainer.getDao(FriendCouponIssueHistoryDao.class);
      FriendCouponIssueHistory issueHistory = friendCouponIssueHistoryDao.load(orderHeader.getDiscountCode());
      if (issueHistory != null && StringUtil.hasValue(issueHistory.getCouponCode())) {
        FriendCouponUseHistory useHistory = new FriendCouponUseHistory();
        CustomerDao customerDao = DIContainer.getDao(CustomerDao.class);
        Customer customerDto = customerDao.load(orderHeader.getCustomerCode());
        
        long useHistoryId = DatabaseUtil.generateSequence(SequenceType.USE_HISTORY_ID);

        useHistory.setUseHistoryId(NumUtil.toString(useHistoryId));
        useHistory.setCouponCode(issueHistory.getCouponCode());
        useHistory.setFriendCouponRuleNo(issueHistory.getFriendCouponRuleNo());
        useHistory.setOrderNo(orderHeader.getOrderNo());
        useHistory.setCustomerCode(orderHeader.getCustomerCode());
        useHistory.setCustomerName(customerDto.getLastName());
        useHistory.setIssueDate(order.getOrderHeader().getOrderDatetime());
        // 20140416 hdh update start
        useHistory.setCouponAmount(order.getOrderHeader().getDiscountPrice());
        // 20140416 hdh update end
        useHistory.setPoint(issueHistory.getObtainPoint());
        useHistory.setCouponUseNum(issueHistory.getCouponUseNum());
        useHistory.setFormerUsePoint(issueHistory.getFormerUsePoint());
        //统计朋友优惠券使用次数
        // 20140410 hdh edit start
//        Long countUseNum=friendCouponIssueHistoryDao.countUseNum(issueHistory.getCouponCode());
//        if(countUseNum>issueHistory.getCouponUseNum()){
//          useHistory.setPoint(issueHistory.getObtainPoint());
//        }else{
//          useHistory.setPoint(issueHistory.getFormerUsePoint());
//        }
        // 20140410 hdh edit end
        useHistory.setPointStatus(PointIssueStatus.PROVISIONAL.longValue());
        setUserStatus(useHistory);
        
        txm.insert(useHistory);
        
      }
      // 2013/04/18 优惠券对应 ob add end
      
      // 优惠券发行
      // 2013/04/18 优惠券对应 ob update start
      // CouponReIssueProcedure couponIssue = new CouponReIssueProcedure(orderHeader.getOrderNo(), orderHeader.getUpdatedUser());
      
      Long customerCount = countUsedCouponOrder(orderHeader.getDiscountCode(), orderHeader.getCustomerCode());
      Long orderCount = countUsedCouponFirstOrder(orderHeader.getCustomerCode());
      NewCouponReIssueProcedure couponIssue = new NewCouponReIssueProcedure(orderHeader.getOrderNo(), orderHeader.getUpdatedUser(), orderCount);
      // 2013/04/18 优惠券对应 ob update end
      
      txm.executeProcedure(couponIssue);
      // soukai add 2012/02/04 ob end
      txm.commit();
      CommonLogic.verifyPointDifference(orderHeader.getCustomerCode(), PointIssueStatus.PROVISIONAL); // 10.1.3
      // 10174
      // 追加
      String orderNo = orderHeader.getOrderNo();
      performOrderEvent(new OrderEvent(orderNo, cashierPayment), OrderEventType.UPDATED);
    } catch (ConcurrencyFailureException e) {
      ppMgr.cancel();
      txm.rollback();
      logger.error(e);
      throw e;
    } catch (RuntimeException e) {
      ppMgr.cancel();
      txm.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txm.dispose();
    }
    return result;
  }

  private ServiceResult updateStock(TransactionManager txm, OrderContainer order, OrderContainer orderOrg) {
    ServiceResultImpl result = new ServiceResultImpl();

    // 引当商品情報を作成する

    List<StockUnit> addStockUnitList = createStockUtil(order.getOrderDetails());
    // 2012/11/28 ob start
    for (ShippingContainer shippingContainer : order.getShippings()) {
      for (ShippingDetail shippingDetail : shippingContainer.getShippingDetails()) {
        List<ShippingDetailComposition> compositionList = shippingContainer.getShippingDetailContainerMap().get(shippingDetail);
        if (compositionList != null && compositionList.size() > 0) {
          for (ShippingDetailComposition composition : compositionList) {
            boolean isExited = false;
            for (StockUnit unit : addStockUnitList) {
              if (unit.getSkuCode().equals(composition.getChildSkuCode())) {
                unit.setQuantity(unit.getQuantity() + shippingDetail.getPurchasingAmount().intValue());
                isExited = true;
                break;
              }
            }
            if (!isExited) {
              StockUnit addStockUnit = new StockUnit();
              addStockUnit.setShopCode(shippingDetail.getShopCode());
              addStockUnit.setSkuCode(composition.getChildSkuCode());
              addStockUnit.setQuantity(shippingDetail.getPurchasingAmount().intValue());
              addStockUnit.setLoginInfo(this.getLoginInfo());
              addStockUnitList.add(addStockUnit);
            }
          }

        }
      }
    }
    // 2012/11/28 ob end
    // 修正前引当商品情報を作成する
    List<StockUnit> cancelStockUnitList = createStockUtil(orderOrg.getOrderDetails());
    // 2012/11/28 ob start
    for (ShippingContainer shippingContainer : orderOrg.getShippings()) {
      for (ShippingDetail shippingDetail : shippingContainer.getShippingDetails()) {
        List<ShippingDetailComposition> compositionList = shippingContainer.getShippingDetailContainerMap().get(shippingDetail);
        if (compositionList != null && compositionList.size() > 0) {
          for (ShippingDetailComposition composition : compositionList) {
            boolean isExited = false;
            for (StockUnit unit : cancelStockUnitList) {
              if (unit.getSkuCode().equals(composition.getChildSkuCode())) {
                unit.setQuantity(unit.getQuantity() + shippingDetail.getPurchasingAmount().intValue());
                isExited = true;
                break;
              }
            }
            if (!isExited) {
              StockUnit cancelStockUnit = new StockUnit();
              cancelStockUnit.setShopCode(shippingDetail.getShopCode());
              cancelStockUnit.setSkuCode(composition.getChildSkuCode());
              cancelStockUnit.setQuantity(shippingDetail.getPurchasingAmount().intValue());
              cancelStockUnit.setLoginInfo(this.getLoginInfo());
              cancelStockUnitList.add(cancelStockUnit);
            }
          }

        }
      }
    }
    Collections.sort(addStockUnitList);
    Collections.sort(cancelStockUnitList);
    // 2012/11/28 ob end
    // 修正前情報の在庫引当取消処理
    StockManager stockManager = txm.getStockManager();
    OrderStatus orgStatus = OrderStatus.fromValue(orderOrg.getOrderHeader().getOrderStatus());
    // キャンセル用商品と追加用商品を同一リストにまとめる
    List<StockUnit> updateList = createUpdateSotckList(addStockUnitList, cancelStockUnitList);

    boolean successUpdateStock = false;
    if (orgStatus == OrderStatus.ORDERED) {
      for (StockUnit su : updateList) {
        if (su.getQuantity() < 0) {
          su.setQuantity(Math.abs(su.getQuantity()));
          successUpdateStock = stockManager.deallocate(su);
        } else if (su.getQuantity() > 0) {
          successUpdateStock = stockManager.allocate(su);
        } else {
          successUpdateStock = true;
        }
        if (!successUpdateStock) {
          result.addServiceError(OrderServiceErrorContent.STOCK_ALLOCATE_ERROR);
          txm.rollback();
          return result;
        }
      }
    } else if (orgStatus == OrderStatus.RESERVED) {
      for (StockUnit su : updateList) {
        if (su.getQuantity() < 0) {
          su.setQuantity(Math.abs(su.getQuantity()));
          successUpdateStock = stockManager.cancelReserving(su);
        } else if (su.getQuantity() > 0) {
          successUpdateStock = stockManager.reserving(su);
        } else {
          successUpdateStock = true;
        }
        if (!successUpdateStock) {
          result.addServiceError(OrderServiceErrorContent.STOCK_ALLOCATE_ERROR);
          txm.rollback();
          return result;
        }
      }
    }
    return result;

  }

  private List<StockUnit> createUpdateSotckList(List<StockUnit> addStockUnitList, List<StockUnit> cancelStockUnitList) {
    List<StockUnit> updateList = new ArrayList<StockUnit>();
    for (StockUnit cancel : cancelStockUnitList) {
      boolean matchCommodity = false;
      for (StockUnit add : addStockUnitList) {
        if ((cancel.getShopCode() + cancel.getSkuCode()).equals(add.getShopCode() + add.getSkuCode())) {
          // キャンセル用商品と追加用商品が一致する場合

          if (add.getQuantity() != cancel.getQuantity()) {
            StockUnit unit = new StockUnit();
            unit.setShopCode(add.getShopCode());
            unit.setSkuCode(add.getSkuCode());
            unit.setQuantity(add.getQuantity() - cancel.getQuantity());
            unit.setLoginInfo(getLoginInfo());
            updateList.add(unit);
          }
          matchCommodity = true;
        }
      }
      if (!matchCommodity) {
        StockUnit unit = new StockUnit();
        unit.setShopCode(cancel.getShopCode());
        unit.setSkuCode(cancel.getSkuCode());
        unit.setQuantity(-cancel.getQuantity());
        unit.setLoginInfo(getLoginInfo());
        updateList.add(unit);
      }
    }

    for (StockUnit add : addStockUnitList) {
      boolean matchCommodity = false;
      for (StockUnit cancel : cancelStockUnitList) {
        if ((cancel.getShopCode() + cancel.getSkuCode()).equals(add.getShopCode() + add.getSkuCode())) {
          matchCommodity = true;
        }
      }
      if (!matchCommodity) {
        StockUnit unit = new StockUnit();
        unit.setShopCode(add.getShopCode());
        unit.setSkuCode(add.getSkuCode());
        unit.setQuantity(add.getQuantity());
        unit.setLoginInfo(getLoginInfo());
        updateList.add(unit);
      }
    }

    // まとめたリストを「ショップコード/SKUコード」の昇順に並べ替える
    Collections.sort(updateList);

    return updateList;
  }

  public ServiceResult updateOrderRemark(String orderNo, String message, String caution, Date updateDatetime) {
    ServiceResultImpl result = new ServiceResultImpl();
    OrderHeader orderHeader = getOrderHeader(orderNo);

    if (orderHeader == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    orderHeader.setMessage(message);
    orderHeader.setCaution(caution);
    orderHeader.setUpdatedDatetime(updateDatetime);
    setUserStatus(orderHeader);

    // データ連携済みステータスが「連携済み」の場合は変更不可

    if (orderHeader.getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.longValue())) {
      //
      result.addServiceError(OrderServiceErrorContent.DATA_TRANSPORT_ERROR);
      return result;
    }

    ValidationSummary summary = BeanValidator.validate(orderHeader);
    if (summary.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    OrderHeaderDao dao = DIContainer.getDao(OrderHeaderDao.class);
    dao.update(orderHeader, getLoginInfo());
    return result;
  }

  public ServiceResult updateTmallOrderRemark(String orderNo, String message, String caution, Date updateDatetime) {
    ServiceResultImpl result = new ServiceResultImpl();
    TmallOrderHeader orderHeader = getTmallOrderHeader(orderNo);

    if (orderHeader == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    orderHeader.setMessage(message);
    orderHeader.setCaution(caution);
    orderHeader.setUpdatedDatetime(updateDatetime);
    setUserStatus(orderHeader);

    // データ連携済みステータスが「連携済み」の場合は変更不可

    if (orderHeader.getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.longValue())) {
      //
      result.addServiceError(OrderServiceErrorContent.DATA_TRANSPORT_ERROR);
      return result;
    }

    ValidationSummary summary = BeanValidator.validate(orderHeader);
    if (summary.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    TmallOrderHeaderDao dao = DIContainer.getDao(TmallOrderHeaderDao.class);
    dao.update(orderHeader, getLoginInfo());
    return result;
  }

  public ServiceResult updateJdOrderRemark(String orderNo, String message, String caution, Date updateDatetime) {
    ServiceResultImpl result = new ServiceResultImpl();
    JdOrderHeader orderHeader = getJdOrderHeader(orderNo);
    if(orderHeader == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }
    orderHeader.setMessage(message);
    orderHeader.setCaution(caution);
    orderHeader.setUpdatedDatetime(updateDatetime);
    setUserStatus(orderHeader);

    // データ連携済みステータスが「連携済み」の場合は変更不可
    if(orderHeader.getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.longValue())) {
      result.addServiceError(OrderServiceErrorContent.DATA_TRANSPORT_ERROR);
      return result;
    }

    ValidationSummary summary = BeanValidator.validate(orderHeader);
    if(summary.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      for(String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }
    JdOrderHeaderDao dao = DIContainer.getDao(JdOrderHeaderDao.class);
    dao.update(orderHeader, getLoginInfo());
    return result;
  }

  public ServiceResult updateOrderWithoutPayment(OrderContainer order) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    String orderNo = order.getOrderHeader().getOrderNo();
    OrderHeader orgOrderHeader = getOrderHeader(orderNo);
    if (orgOrderHeader == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }
    OrderHeader orderHeader = order.getOrderHeader();
    // 受注ヘッダ情報コピー

    orgOrderHeader.setShopCode(orderHeader.getShopCode());
    orgOrderHeader.setCustomerCode(orderHeader.getCustomerCode());
    orgOrderHeader.setLastName(orderHeader.getLastName());
    orgOrderHeader.setFirstName(orderHeader.getFirstName());
    orgOrderHeader.setLastNameKana(orderHeader.getLastNameKana());
    orgOrderHeader.setFirstNameKana(orderHeader.getFirstNameKana());
    orgOrderHeader.setEmail(orderHeader.getEmail());
    orgOrderHeader.setPostalCode(orderHeader.getPostalCode());
    orgOrderHeader.setPrefectureCode(orderHeader.getPrefectureCode());
    orgOrderHeader.setAddress1(orderHeader.getAddress1());
    orgOrderHeader.setAddress2(orderHeader.getAddress2());
    orgOrderHeader.setAddress3(orderHeader.getAddress3());
    orgOrderHeader.setAddress4(orderHeader.getAddress4());
    // add by V10-CH 170 start
    // soukai add 2012/01/08 ob start
    orgOrderHeader.setAreaCode(orderHeader.getAreaCode());
    orgOrderHeader.setOrderFlg(orderHeader.getOrderFlg());
    // soukai add 2012/01/08 ob end
    orgOrderHeader.setCityCode(orderHeader.getCityCode());
    // add by V10-CH 170 end
    orgOrderHeader.setPhoneNumber(orderHeader.getPhoneNumber());
    // Add by V10-CH start
    orgOrderHeader.setMobileNumber(orderHeader.getMobileNumber());
    // Add by V10-CH end
    orgOrderHeader.setCustomerGroupCode(orderHeader.getCustomerGroupCode());
    orgOrderHeader.setClientGroup(orderHeader.getClientGroup());
    orgOrderHeader.setCaution(orderHeader.getCaution());
    orgOrderHeader.setMessage(orderHeader.getMessage());
    orgOrderHeader.setWarningMessage(orderHeader.getWarningMessage());
    orgOrderHeader.setUpdatedDatetime(orderHeader.getUpdatedDatetime());
    // add by lc 2013-02-04 start
    if (!StringUtil.isNullOrEmpty(order.getOrderInvoice())) {
      orgOrderHeader.setInvoiceFlg(NumUtil.toLong(order.getOrderInvoice().getInvoiceFlg()));
  }
    // add by lc 2013-02-04 end    
    ValidationSummary summary = BeanValidator.validate(orderHeader);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    List<ShippingHeader> orgShippingHeaderList = new ArrayList<ShippingHeader>();
    for (ShippingContainer shipping : order.getShippings()) {
      ShippingHeader shippingHeader = shipping.getShippingHeader();
      ShippingHeader orgShippingHeader = getShippingHeader(shippingHeader.getShippingNo());
      if (orgShippingHeader == null) {
        serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
        return serviceResult;
      }
      // 売上確定時は変更不可
      if (orgShippingHeader.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue())) {
        serviceResult.addServiceError(OrderServiceErrorContent.FIXED_DATA_ERROR);
        continue;
      }

      orgShippingHeader.setShopCode(shippingHeader.getShopCode());
      orgShippingHeader.setCustomerCode(shippingHeader.getCustomerCode());
      orgShippingHeader.setAddressNo(shippingHeader.getAddressNo());
      orgShippingHeader.setAddressLastName(shippingHeader.getAddressLastName());
      orgShippingHeader.setAddressFirstName(shippingHeader.getAddressFirstName());
      orgShippingHeader.setAddressLastNameKana(shippingHeader.getAddressLastNameKana());
      orgShippingHeader.setAddressFirstNameKana(shippingHeader.getAddressFirstNameKana());
      orgShippingHeader.setPostalCode(shippingHeader.getPostalCode());
      orgShippingHeader.setAddress2(shippingHeader.getAddress2());
      orgShippingHeader.setAddress3(shippingHeader.getAddress3());
      orgShippingHeader.setAddress4(shippingHeader.getAddress4());
      orgShippingHeader.setPhoneNumber(shippingHeader.getPhoneNumber());
      // Add by V10-CH start
      orgShippingHeader.setMobileNumber(shippingHeader.getMobileNumber());
      // Add by V10-CH end
      orgShippingHeader.setDeliveryRemark(shippingHeader.getDeliveryRemark());
      orgShippingHeader.setDeliverySlipNo(shippingHeader.getDeliverySlipNo());
      orgShippingHeader.setDeliveryAppointedDate(shippingHeader.getDeliveryAppointedDate());
      orgShippingHeader.setDeliveryAppointedTimeStart(shippingHeader.getDeliveryAppointedTimeStart());
      orgShippingHeader.setDeliveryAppointedTimeEnd(shippingHeader.getDeliveryAppointedTimeEnd());
      orgShippingHeader.setShippingDirectDate(shippingHeader.getShippingDirectDate());
      orgShippingHeader.setUpdatedDatetime(shippingHeader.getUpdatedDatetime());

      summary = BeanValidator.validate(shippingHeader);
      if (summary.hasError()) {
        logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String error : summary.getErrorMessages()) {
          logger.error(error);
        }
        return serviceResult;
      }

      orgShippingHeaderList.add(orgShippingHeader);
    }

    // soukai add 2011/12/29 ob start
    // 订单发票取得
    OrderInvoice orgOrderInvoice = getOrderInvoice(orderNo);
    OrderInvoice orderInvoice = order.getOrderInvoice();
    boolean blnNullFLg = true;
    if (orgOrderInvoice == null) {
      blnNullFLg = false;
      orgOrderInvoice = new OrderInvoice();
      orgOrderInvoice.setOrderNo(orderNo);
      orgOrderInvoice.setCustomerCode(orgOrderHeader.getCustomerCode());
    }
    orgOrderInvoice.setInvoiceFlg(orderInvoice.getInvoiceFlg());
    orgOrderInvoice.setAddress(orderInvoice.getAddress());// 地址
    orgOrderInvoice.setBankName(orderInvoice.getBankName());// 银行名称
    orgOrderInvoice.setBankNo(orderInvoice.getBankNo());// 银行支行编号
    orgOrderInvoice.setCommodityName(orderInvoice.getCommodityName());// 商品规格
    orgOrderInvoice.setCompanyName(orderInvoice.getCompanyName());// 公司名称
    orgOrderInvoice.setCustomerName(orderInvoice.getCustomerName());// 顾客名称
    orgOrderInvoice.setInvoiceType(orderInvoice.getInvoiceType());// 发票类型
    orgOrderInvoice.setOrmRowid(orderInvoice.getOrmRowid());// データ行ID
    orgOrderInvoice.setTaxpayerCode(orderInvoice.getTaxpayerCode());// 纳税人识别号
    orgOrderInvoice.setTel(orderInvoice.getTel());// 电话
    orgOrderInvoice.setUpdatedDatetime(orderInvoice.getUpdatedDatetime());// 更新时间

    CustomerVatInvoice vatInvoice = getCustomerVatInvoice(order.getOrderHeader().getCustomerCode());
    boolean exitsFlg = false;
    if (vatInvoice != null) {
      exitsFlg = true;
      if (order.getCustomerVatInvoice() != null && StringUtil.hasValue(order.getCustomerVatInvoice().getCustomerCode())) {
        order.getCustomerVatInvoice().setOrmRowid(vatInvoice.getOrmRowid());
        order.getCustomerVatInvoice().setCreatedDatetime(vatInvoice.getCreatedDatetime());
        order.getCustomerVatInvoice().setCreatedUser(vatInvoice.getCreatedUser());
        order.getCustomerVatInvoice().setUpdatedDatetime(vatInvoice.getUpdatedDatetime());
        order.getCustomerVatInvoice().setUpdatedUser(vatInvoice.getUpdatedUser());
      }
    }
    summary = BeanValidator.validate(orgOrderInvoice);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    // soukai add 2011/12/29 ob end

    TransactionManager manager = DIContainer.getTransactionManager();
    try {
      manager.begin(this.getLoginInfo());

      ArchivedOrderProcedure archivedOrder = new ArchivedOrderProcedure(orderHeader.getOrderNo());
      manager.executeProcedure(archivedOrder);

      manager.update(orgOrderHeader);

      for (ShippingHeader shippingHeader : orgShippingHeaderList) {
        manager.update(shippingHeader);
      }
      // soukai add 2011/12/29 ob start
      if (InvoiceFlg.NEED.getValue().equals(orderInvoice.getInvoiceFlg())) {
        // 领取发票
        if (blnNullFLg) {
          // 更新既存订单发票情报
          manager.update(orgOrderInvoice);
        } else {
          // 插入新订单发票情报
          manager.insert(orgOrderInvoice);
        }
      } else if (InvoiceFlg.NO_NEED.getValue().equals(orderInvoice.getInvoiceFlg())) {
        // 不领取发票
        if (blnNullFLg) {
          // 删除既存订单发票情报
          manager.delete(orgOrderInvoice);
        }
      }
      if (order.getCustomerVatInvoice() != null && StringUtil.hasValue(order.getCustomerVatInvoice().getCustomerCode())) {
        setUserStatus(order.getCustomerVatInvoice());
        if (exitsFlg) {
          manager.update(order.getCustomerVatInvoice());
        } else {
          manager.insert(order.getCustomerVatInvoice());
        }
      }
      // soukai add 2011/12/29 ob end
      manager.commit();
    } catch (ConcurrencyFailureException e) {
      manager.rollback();
      throw e;
    } catch (RuntimeException e) {
      manager.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }

    return serviceResult;
  }

  // soukai add 2011/12/29 ob start
  // TMALL受注情報の更新
  public ServiceResult updateTmallOrderWithoutPayment(OrderContainer order) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    String orderNo = order.getTmallOrderHeader().getOrderNo();
    // TMALL受注ヘッダ情報を取得する
    TmallOrderHeader orgTmallOrderHeader = getTmallOrderHeader(orderNo);
    // TMALL受注ヘッダ情報がない場合
    if (orgTmallOrderHeader == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }
    TmallOrderHeader tmallOrderHeader = order.getTmallOrderHeader();
    // TMALL受注ヘッダ情報コピー
    orgTmallOrderHeader.setShopCode(tmallOrderHeader.getShopCode());
    orgTmallOrderHeader.setCustomerCode(tmallOrderHeader.getCustomerCode());
    orgTmallOrderHeader.setLastName(tmallOrderHeader.getLastName());
    orgTmallOrderHeader.setFirstName(tmallOrderHeader.getFirstName());
    orgTmallOrderHeader.setLastNameKana(tmallOrderHeader.getLastNameKana());
    orgTmallOrderHeader.setFirstNameKana(tmallOrderHeader.getFirstNameKana());
    orgTmallOrderHeader.setEmail(tmallOrderHeader.getEmail());
    orgTmallOrderHeader.setPostalCode(tmallOrderHeader.getPostalCode());
    orgTmallOrderHeader.setPrefectureCode(tmallOrderHeader.getPrefectureCode());
    orgTmallOrderHeader.setAddress1(tmallOrderHeader.getAddress1());
    orgTmallOrderHeader.setAddress2(tmallOrderHeader.getAddress2());
    orgTmallOrderHeader.setAddress3(tmallOrderHeader.getAddress3());
    orgTmallOrderHeader.setAddress4(tmallOrderHeader.getAddress4());
    orgTmallOrderHeader.setCityCode(tmallOrderHeader.getCityCode());
    orgTmallOrderHeader.setPhoneNumber(tmallOrderHeader.getPhoneNumber());
    orgTmallOrderHeader.setMobileNumber(tmallOrderHeader.getMobileNumber());
    orgTmallOrderHeader.setCustomerGroupCode(tmallOrderHeader.getCustomerGroupCode());
    orgTmallOrderHeader.setClientGroup(tmallOrderHeader.getClientGroup());
    orgTmallOrderHeader.setCaution(tmallOrderHeader.getCaution());
    orgTmallOrderHeader.setMessage(tmallOrderHeader.getMessage());
    orgTmallOrderHeader.setWarningMessage(tmallOrderHeader.getWarningMessage());
    orgTmallOrderHeader.setUpdatedDatetime(tmallOrderHeader.getUpdatedDatetime());
    orgTmallOrderHeader.setOrderFlg(tmallOrderHeader.getOrderFlg());
    orgTmallOrderHeader.setPaymentCommission(BigDecimal.ZERO);
    // add by lc 2012-04-11 start
    orgTmallOrderHeader.setInvoiceFlg(NumUtil.toLong(order.getOrderInvoice().getInvoiceFlg()));
    // add by lc 2012-04-11 end
    ValidationSummary summary = BeanValidator.validate(tmallOrderHeader);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    List<TmallShippingHeader> orgTmallShippingHeaderList = new ArrayList<TmallShippingHeader>();
    for (ShippingContainer shipping : order.getShippings()) {
      TmallShippingHeader tmallShippingHeader = shipping.getTmallShippingHeader();
      TmallShippingHeader orgTmallShippingHeader = getTmallShippingHeader(tmallShippingHeader.getShippingNo());
      // 出荷ヘッダ情報がない場合
      if (orgTmallShippingHeader == null) {
        serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
        return serviceResult;
      }
      // 売上確定時は変更不可
      if (orgTmallShippingHeader.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue())) {
        serviceResult.addServiceError(OrderServiceErrorContent.FIXED_DATA_ERROR);
        continue;
      }
      orgTmallShippingHeader.setShopCode(tmallShippingHeader.getShopCode());
      orgTmallShippingHeader.setCustomerCode(tmallShippingHeader.getCustomerCode());
      orgTmallShippingHeader.setAddressNo(tmallShippingHeader.getAddressNo());
      orgTmallShippingHeader.setAddressLastName(tmallShippingHeader.getAddressLastName());
      orgTmallShippingHeader.setAddressFirstName(tmallShippingHeader.getAddressFirstName());
      orgTmallShippingHeader.setAddressLastNameKana(tmallShippingHeader.getAddressLastNameKana());
      orgTmallShippingHeader.setAddressFirstNameKana(tmallShippingHeader.getAddressFirstNameKana());
      orgTmallShippingHeader.setPostalCode(tmallShippingHeader.getPostalCode());
      orgTmallShippingHeader.setAddress2(tmallShippingHeader.getAddress2());
      orgTmallShippingHeader.setAddress3(tmallShippingHeader.getAddress3());
      orgTmallShippingHeader.setAddress4(tmallShippingHeader.getAddress4());
      orgTmallShippingHeader.setPhoneNumber(tmallShippingHeader.getPhoneNumber());
      orgTmallShippingHeader.setMobileNumber(tmallShippingHeader.getMobileNumber());
      orgTmallShippingHeader.setDeliveryRemark(tmallShippingHeader.getDeliveryRemark());
      orgTmallShippingHeader.setDeliverySlipNo(tmallShippingHeader.getDeliverySlipNo());
      orgTmallShippingHeader.setDeliveryAppointedDate(tmallShippingHeader.getDeliveryAppointedDate());
      orgTmallShippingHeader.setDeliveryAppointedTimeStart(tmallShippingHeader.getDeliveryAppointedTimeStart());
      orgTmallShippingHeader.setDeliveryAppointedTimeEnd(tmallShippingHeader.getDeliveryAppointedTimeEnd());
      orgTmallShippingHeader.setShippingDirectDate(tmallShippingHeader.getShippingDirectDate());
      orgTmallShippingHeader.setUpdatedDatetime(tmallShippingHeader.getUpdatedDatetime());
      orgTmallShippingHeader.setDeliveryCompanyNo(tmallShippingHeader.getDeliveryCompanyNo());
      orgTmallShippingHeader.setDeliveryCompanyName(tmallShippingHeader.getDeliveryCompanyName());
      summary = BeanValidator.validate(tmallShippingHeader);
      if (summary.hasError()) {
        logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String error : summary.getErrorMessages()) {
          logger.error(error);
        }
        return serviceResult;
      }
      orgTmallShippingHeaderList.add(orgTmallShippingHeader);
    }
    // 订单发票取得
    OrderInvoice orgOrderInvoice = getOrderInvoice(orderNo);
    OrderInvoice orderInvoice = order.getOrderInvoice();
    boolean blnNullFLg = true;
    if (orgOrderInvoice == null) {
      blnNullFLg = false;
      orgOrderInvoice = new OrderInvoice();
      orgOrderInvoice.setOrderNo(orderNo);
      orgOrderInvoice.setCustomerCode(orgTmallOrderHeader.getCustomerCode());
    } else {
      orgOrderInvoice.setOrderNo(orderInvoice.getOrderNo());// 订单编号
      orgOrderInvoice.setCustomerCode(orderInvoice.getCustomerCode());// 公司code
    }
    orgOrderInvoice.setAddress(orderInvoice.getAddress());// 地址
    orgOrderInvoice.setBankName(orderInvoice.getBankName());// 银行名称
    orgOrderInvoice.setBankNo(orderInvoice.getBankNo());// 银行支行编号
    orgOrderInvoice.setCommodityName(orderInvoice.getCommodityName());// 商品规格
    orgOrderInvoice.setCompanyName(orderInvoice.getCompanyName());// 公司名称
    orgOrderInvoice.setCustomerName(orderInvoice.getCustomerName());// 顾客名称
    orgOrderInvoice.setInvoiceType(orderInvoice.getInvoiceType());// 发票类型
    orgOrderInvoice.setTaxpayerCode(orderInvoice.getTaxpayerCode());// 纳税人识别号
    orgOrderInvoice.setTel(orderInvoice.getTel());// 电话
    setUserStatus(orgOrderInvoice);
    CustomerVatInvoice vatInvoice = getCustomerVatInvoice(order.getTmallOrderHeader().getCustomerCode());
    boolean exitsFlg = false;
    if (vatInvoice != null) {
      exitsFlg = true;
      if (order.getCustomerVatInvoice() != null && StringUtil.hasValue(order.getCustomerVatInvoice().getCustomerCode())) {
        order.getCustomerVatInvoice().setOrmRowid(vatInvoice.getOrmRowid());
        order.getCustomerVatInvoice().setCreatedDatetime(vatInvoice.getCreatedDatetime());
        order.getCustomerVatInvoice().setCreatedUser(vatInvoice.getCreatedUser());
        order.getCustomerVatInvoice().setUpdatedDatetime(vatInvoice.getUpdatedDatetime());
        order.getCustomerVatInvoice().setUpdatedUser(vatInvoice.getUpdatedUser());
      }
    }
    if (StringUtil.hasValue(orderInvoice.getCustomerCode())) {
      summary = BeanValidator.validate(orderInvoice);
      if (summary.hasError()) {
        logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String error : summary.getErrorMessages()) {
          logger.error(error);
        }
        return serviceResult;
      }
    }

    TransactionManager manager = DIContainer.getTransactionManager();
    try {
      manager.begin(this.getLoginInfo());

      ArchivedOrderProcedure archivedOrder = new ArchivedOrderProcedure(tmallOrderHeader.getOrderNo());

      manager.executeProcedure(archivedOrder);

      manager.update(orgTmallOrderHeader);

      for (TmallShippingHeader tmallShippingHeader : orgTmallShippingHeaderList) {
        manager.update(tmallShippingHeader);
      }
      if (InvoiceFlg.NEED.getValue().equals(orderInvoice.getInvoiceFlg())) {
        // 领取发票
        if (blnNullFLg) {
          // 更新既存订单发票情报
          manager.update(orgOrderInvoice);
        } else {
          // 插入新订单发票情报
          manager.insert(orgOrderInvoice);
        }
      } else if (InvoiceFlg.NO_NEED.getValue().equals(orderInvoice.getInvoiceFlg())) {
        // 不领取发票
        if (blnNullFLg) {
          // 删除既存订单发票情报
          manager.delete(orgOrderInvoice);
        }
      }
      if (order.getCustomerVatInvoice() != null && StringUtil.hasValue(order.getCustomerVatInvoice().getCustomerCode())) {
        setUserStatus(order.getCustomerVatInvoice());
        if (exitsFlg) {
          manager.update(order.getCustomerVatInvoice());
        } else {
          manager.insert(order.getCustomerVatInvoice());
        }
      }
      /*
       * TmallService service = ServiceLocator.getTmallService(getLoginInfo());
       * TmallShippingDelivery tsh = new TmallShippingDelivery(); // 交易编号 更新条件
       * tsh.setTid(order.getTmallOrderHeader().getTmallTid()); for
       * (ShippingContainer shippingContainer : order.getShippings()) { // 收货人姓名
       * tsh.setReceiverName(shippingContainer.getTmallShippingHeader().
       * getAddressLastName()); // 固话
       * tsh.setReceiverPhone(shippingContainer.getTmallShippingHeader
       * ().getPhoneNumber()); // 手机
       * tsh.setReceiverMobile(shippingContainer.getTmallShippingHeader
       * ().getMobileNumber()); // 详细地址
       * tsh.setReceiverAddress(shippingContainer.
       * getTmallShippingHeader().getAddress4()); } String returnTid =
       * service.updateShippingAddrInfo(tsh); if
       * (order.getTmallOrderHeader().getTmallTid().equals(returnTid)) { // 更新成功
       * manager.commit(); } else { // 更新失败 manager.rollback();
       * serviceResult.addServiceError
       * (OrderServiceErrorContent.TMALL_ACCESS_ERROR); return serviceResult; }
       */

      // 更新成功
      manager.commit();
    } catch (ConcurrencyFailureException e) {
      manager.rollback();
      throw e;
    } catch (RuntimeException e) {
      manager.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }
    return serviceResult;
  }

  // soukai add 2011/12/29 ob end

  // Jd受注情報の更新
  public ServiceResult updateJdOrderWithoutPayment(OrderContainer order) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    String orderNo = order.getJdOrderHeader().getOrderNo();
    // Jd受注ヘッダ情報を取得する
    JdOrderHeader orgJdOrderHeader = getJdOrderHeader(orderNo);
    // Jd受注ヘッダ情報がない場合
    if (orgJdOrderHeader == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }
    JdOrderHeader jdOrderHeader = order.getJdOrderHeader();
    // Jd受注ヘッダ情報コピー
    orgJdOrderHeader.setShopCode(jdOrderHeader.getShopCode());
    orgJdOrderHeader.setCustomerCode(jdOrderHeader.getCustomerCode());
    orgJdOrderHeader.setLastName(jdOrderHeader.getLastName());
    orgJdOrderHeader.setFirstName(jdOrderHeader.getFirstName());
    orgJdOrderHeader.setLastNameKana(jdOrderHeader.getLastNameKana());
    orgJdOrderHeader.setFirstNameKana(jdOrderHeader.getFirstNameKana());
    orgJdOrderHeader.setEmail(jdOrderHeader.getEmail());
    orgJdOrderHeader.setPostalCode(jdOrderHeader.getPostalCode());
    orgJdOrderHeader.setPrefectureCode(jdOrderHeader.getPrefectureCode());
    orgJdOrderHeader.setAddress1(jdOrderHeader.getAddress1());
    orgJdOrderHeader.setAddress2(jdOrderHeader.getAddress2());
    orgJdOrderHeader.setAddress3(jdOrderHeader.getAddress3());
    orgJdOrderHeader.setAddress4(jdOrderHeader.getAddress4());
    orgJdOrderHeader.setCityCode(jdOrderHeader.getCityCode());
    orgJdOrderHeader.setPhoneNumber(jdOrderHeader.getPhoneNumber());
    orgJdOrderHeader.setMobileNumber(jdOrderHeader.getMobileNumber());
    orgJdOrderHeader.setCustomerGroupCode(jdOrderHeader.getCustomerGroupCode());
    orgJdOrderHeader.setClientGroup(jdOrderHeader.getClientGroup());
    orgJdOrderHeader.setCaution(jdOrderHeader.getCaution());
    orgJdOrderHeader.setMessage(jdOrderHeader.getMessage());
    orgJdOrderHeader.setWarningMessage(jdOrderHeader.getWarningMessage());
    orgJdOrderHeader.setUpdatedDatetime(jdOrderHeader.getUpdatedDatetime());
    orgJdOrderHeader.setOrderFlg(jdOrderHeader.getOrderFlg());
    orgJdOrderHeader.setPaymentCommission(BigDecimal.ZERO);
    // add by lc 2012-04-11 start
    orgJdOrderHeader.setInvoiceFlg(NumUtil.toLong(order.getOrderInvoice().getInvoiceFlg()));
    // add by lc 2012-04-11 end
    ValidationSummary summary = BeanValidator.validate(jdOrderHeader);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    List<JdShippingHeader> orgJdShippingHeaderList = new ArrayList<JdShippingHeader>();
    for (ShippingContainer shipping : order.getShippings()) {
      JdShippingHeader jdShippingHeader = shipping.getJdShippingHeader();
      JdShippingHeader orgJdShippingHeader = getJdShippingHeader(jdShippingHeader.getShippingNo());
      // 出荷ヘッダ情報がない場合
      if (orgJdShippingHeader == null) {
        serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
        return serviceResult;
      }
      // 売上確定時は変更不可
      if (orgJdShippingHeader.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue())) {
        serviceResult.addServiceError(OrderServiceErrorContent.FIXED_DATA_ERROR);
        continue;
      }
      orgJdShippingHeader.setShopCode(jdShippingHeader.getShopCode());
      orgJdShippingHeader.setCustomerCode(jdShippingHeader.getCustomerCode());
      orgJdShippingHeader.setAddressNo(jdShippingHeader.getAddressNo());
      orgJdShippingHeader.setAddressLastName(jdShippingHeader.getAddressLastName());
      orgJdShippingHeader.setAddressFirstName(jdShippingHeader.getAddressFirstName());
      orgJdShippingHeader.setAddressLastNameKana(jdShippingHeader.getAddressLastNameKana());
      orgJdShippingHeader.setAddressFirstNameKana(jdShippingHeader.getAddressFirstNameKana());
      orgJdShippingHeader.setPostalCode(jdShippingHeader.getPostalCode());
      orgJdShippingHeader.setAddress2(jdShippingHeader.getAddress2());
      orgJdShippingHeader.setAddress3(jdShippingHeader.getAddress3());
      orgJdShippingHeader.setAddress4(jdShippingHeader.getAddress4());
      orgJdShippingHeader.setPhoneNumber(jdShippingHeader.getPhoneNumber());
      orgJdShippingHeader.setMobileNumber(jdShippingHeader.getMobileNumber());
      orgJdShippingHeader.setDeliveryRemark(jdShippingHeader.getDeliveryRemark());
      orgJdShippingHeader.setDeliverySlipNo(jdShippingHeader.getDeliverySlipNo());
      orgJdShippingHeader.setDeliveryAppointedDate(jdShippingHeader.getDeliveryAppointedDate());
      orgJdShippingHeader.setDeliveryAppointedTimeStart(jdShippingHeader.getDeliveryAppointedTimeStart());
      orgJdShippingHeader.setDeliveryAppointedTimeEnd(jdShippingHeader.getDeliveryAppointedTimeEnd());
      orgJdShippingHeader.setShippingDirectDate(jdShippingHeader.getShippingDirectDate());
      orgJdShippingHeader.setUpdatedDatetime(jdShippingHeader.getUpdatedDatetime());
      orgJdShippingHeader.setDeliveryCompanyNo(jdShippingHeader.getDeliveryCompanyNo());
      orgJdShippingHeader.setDeliveryCompanyName(jdShippingHeader.getDeliveryCompanyName());
      summary = BeanValidator.validate(jdShippingHeader);
      if (summary.hasError()) {
        logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String error : summary.getErrorMessages()) {
          logger.error(error);
        }
        return serviceResult;
      }
      orgJdShippingHeaderList.add(orgJdShippingHeader);
    }
    // 订单发票取得
    OrderInvoice orgOrderInvoice = getOrderInvoice(orderNo);
    OrderInvoice orderInvoice = order.getOrderInvoice();
    boolean blnNullFLg = true;
    if (orgOrderInvoice == null) {
      blnNullFLg = false;
      orgOrderInvoice = new OrderInvoice();
      orgOrderInvoice.setOrderNo(orderNo);
      orgOrderInvoice.setCustomerCode(orgJdOrderHeader.getCustomerCode());
    } else {
      orgOrderInvoice.setOrderNo(orderInvoice.getOrderNo());// 订单编号
      orgOrderInvoice.setCustomerCode(orderInvoice.getCustomerCode());// 公司code
    }
    orgOrderInvoice.setAddress(orderInvoice.getAddress());// 地址
    orgOrderInvoice.setBankName(orderInvoice.getBankName());// 银行名称
    orgOrderInvoice.setBankNo(orderInvoice.getBankNo());// 银行支行编号
    orgOrderInvoice.setCommodityName(orderInvoice.getCommodityName());// 商品规格
    orgOrderInvoice.setCompanyName(orderInvoice.getCompanyName());// 公司名称
    orgOrderInvoice.setCustomerName(orderInvoice.getCustomerName());// 顾客名称
    orgOrderInvoice.setInvoiceType(orderInvoice.getInvoiceType());// 发票类型
    orgOrderInvoice.setTaxpayerCode(orderInvoice.getTaxpayerCode());// 纳税人识别号
    orgOrderInvoice.setTel(orderInvoice.getTel());// 电话
    setUserStatus(orgOrderInvoice);
    CustomerVatInvoice vatInvoice = getCustomerVatInvoice(order.getJdOrderHeader().getCustomerCode());
    boolean exitsFlg = false;
    if (vatInvoice != null) {
      exitsFlg = true;
      if (order.getCustomerVatInvoice() != null && StringUtil.hasValue(order.getCustomerVatInvoice().getCustomerCode())) {
        order.getCustomerVatInvoice().setOrmRowid(vatInvoice.getOrmRowid());
        order.getCustomerVatInvoice().setCreatedDatetime(vatInvoice.getCreatedDatetime());
        order.getCustomerVatInvoice().setCreatedUser(vatInvoice.getCreatedUser());
        order.getCustomerVatInvoice().setUpdatedDatetime(vatInvoice.getUpdatedDatetime());
        order.getCustomerVatInvoice().setUpdatedUser(vatInvoice.getUpdatedUser());
      }
    }
    if (StringUtil.hasValue(orderInvoice.getCustomerCode())) {
      summary = BeanValidator.validate(orderInvoice);
      if (summary.hasError()) {
        logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String error : summary.getErrorMessages()) {
          logger.error(error);
        }
        return serviceResult;
      }
    }

    TransactionManager manager = DIContainer.getTransactionManager();
    try {
      manager.begin(this.getLoginInfo());

      ArchivedOrderProcedure archivedOrder = new ArchivedOrderProcedure(jdOrderHeader.getOrderNo());

      manager.executeProcedure(archivedOrder);

      manager.update(orgJdOrderHeader);

      for (JdShippingHeader jdShippingHeader : orgJdShippingHeaderList) {
        manager.update(jdShippingHeader);
      }
      if (InvoiceFlg.NEED.getValue().equals(orderInvoice.getInvoiceFlg())) {
        // 领取发票
        if (blnNullFLg) {
          // 更新既存订单发票情报
          manager.update(orgOrderInvoice);
        } else {
          // 插入新订单发票情报
          manager.insert(orgOrderInvoice);
        }
      } else if (InvoiceFlg.NO_NEED.getValue().equals(orderInvoice.getInvoiceFlg())) {
        // 不领取发票
        if (blnNullFLg) {
          // 删除既存订单发票情报
          manager.delete(orgOrderInvoice);
        }
      }
      if (order.getCustomerVatInvoice() != null && StringUtil.hasValue(order.getCustomerVatInvoice().getCustomerCode())) {
        setUserStatus(order.getCustomerVatInvoice());
        if (exitsFlg) {
          manager.update(order.getCustomerVatInvoice());
        } else {
          manager.insert(order.getCustomerVatInvoice());
        }
      }

      // 更新成功
      manager.commit();
    } catch (ConcurrencyFailureException e) {
      manager.rollback();
      throw e;
    } catch (RuntimeException e) {
      manager.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }
    return serviceResult;
  }

  
  
  /**
   * 受注関連情報のValidationチェックを一括で行います。<BR>
   * 
   * @param orderHeader
   * @param orderDetails
   * @param shippings
   * @return true-validationエラーなし false-validationエラーあり
   */
  private boolean validateOrderSet(OrderHeader orderHeader, List<OrderDetail> orderDetails, List<ShippingContainer> shippings) {
    Logger logger = Logger.getLogger(this.getClass());
    boolean valid = true;
    ValidationSummary validation = BeanValidator.validate(orderHeader);
    if (validation.hasError()) {
      for (String rs : validation.getErrorMessages()) {
        logger.debug(rs);
      }
      valid = false;
    }
    for (OrderDetail orderDetail : orderDetails) {
      validation = BeanValidator.validate(orderDetail);
      if (validation.hasError()) {
        for (String rs : validation.getErrorMessages()) {
          logger.debug(rs);
        }
        valid = false;
      }
    }
    for (ShippingContainer shippingContainer : shippings) {
      ShippingHeader shippingHeader = shippingContainer.getShippingHeader();
      validation = BeanValidator.validate(shippingHeader);
      if (validation.hasError()) {
        for (String rs : validation.getErrorMessages()) {
          logger.debug(rs);
        }
        valid = false;
      }

      for (ShippingDetail shippingDetail : shippingContainer.getShippingDetails()) {
        validation = BeanValidator.validate(shippingDetail);
        if (validation.hasError()) {
          for (String rs : validation.getErrorMessages()) {
            logger.debug(rs);
          }
          valid = false;
        }
      }
      OrderContainer order = new OrderContainer();
      order.setOrderHeader(orderHeader);
      order.setOrderDetails(orderDetails);
      order.setShippings(shippings);
      NumberLimitPolicy policy = DIContainer.getNumberLimitPolicy();
      ValidationSummary summary = policy.isCorrect(order);
      if (summary.hasError()) {
        for (String error : summary.getErrorMessages()) {
          logger.debug(error);
        }
        valid = false;
      }
    }
    return valid;
  }

  /**
   * 与信結果情報を受注情報に設定します。<BR>
   * 設定する情報:
   * <ol>
   * <li>決済サービス取引ID
   * <li>コンビニコード(コンビニ決済のみ)
   * <li>承認番号
   * <li>払込URL(コンビニ決済のみ)
   * <li>電子マネー区分(電子マネー決済のみ)
   * <li>警告メッセージ
   * <li>支払期限日(コンビニ決済、電子マネー決済のみ)
   * </ol>
   * また、カード決済の場合は受注情報が含む出荷情報の出荷ステータスを"出荷可能"に設定します。<BR>
   * <BR>
   * 
   * @param order
   *          受注情報
   * @param paymentResult
   *          与信結果情報
   */
  private void setPaymentResultStatus(OrderContainer order, PaymentResult paymentResult) {
    order.getOrderHeader().setPaymentOrderId(NumUtil.toLong(paymentResult.getPaymentOrderId(), null));
    order.getOrderHeader().setCvsCode(paymentResult.getCvsCode());
    order.getOrderHeader().setPaymentReceiptNo(paymentResult.getPaymentReceiptNo());
    order.getOrderHeader().setPaymentReceiptUrl(paymentResult.getPaymentReceiptUrl());
    order.getOrderHeader().setDigitalCashType(paymentResult.getDigitalCashType());
    order.getOrderHeader().setWarningMessage(paymentResult.getMessage());
    if (paymentResult.getPaymentLimitDate() != null) {
      order.getOrderHeader().setPaymentLimitDate(DateUtil.truncateDate(paymentResult.getPaymentLimitDate()));
    }
  }

  public ServiceResult changeReservationToOrder(String orderNo) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();
    OrderContainer container = getOrder(orderNo);

    if (container.getOrderDetails().isEmpty()) {
      // logger.debug("受注番号【" + orderNo + "】は存在しないため、予約から受注への変更ができません");
      logger.debug(MessageFormat.format(Messages.log("service.impl.OrderServiceImpl.9"), orderNo));
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    // 予約データは明細が1件のみ

    TransactionManager txMgr = DIContainer.getTransactionManager();
    OrderDetail orderDetail = container.getOrderDetails().get(0);
    try {
      txMgr.begin(this.getLoginInfo());

      OrderHeader updateUser = new OrderHeader();
      setUserStatus(updateUser);

      logger.debug(Messages.log("service.impl.OrderServiceImpl.10"));
      StockManager stockMgr = txMgr.getStockManager();
      StockUnit stockUnit = new StockUnit();
      stockUnit.setShopCode(orderDetail.getShopCode());
      stockUnit.setSkuCode(orderDetail.getSkuCode());
      stockUnit.setQuantity(orderDetail.getPurchasingAmount().intValue());
      stockUnit.setLoginInfo(getLoginInfo());
      stockUnit.setStockIODate(DateUtil.getSysdate());

      boolean changeesult = stockMgr.shift(stockUnit);
      if (!changeesult) {
        result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        txMgr.rollback();
        return result;
      }

      logger.debug(Messages.log("service.impl.OrderServiceImpl.11"));
      Query orderStatusUpdateQuery = new SimpleQuery(OrderServiceQuery.UPDATE_ORDER_HEADER_ORDER_STATUS_QUERY, OrderStatus.ORDERED
          .getValue(), this.getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), orderNo, container
          .getOrderHeader().getUpdatedDatetime());

      int orderStatusResult = txMgr.executeUpdate(orderStatusUpdateQuery);
      if (orderStatusResult != 1) {
        logger.debug(Messages.log("service.impl.OrderServiceImpl.12"));
        result.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
        txMgr.rollback();
        return result;
      }
      // M17N 10361 追加 ここから
      OrderHeader header = container.getOrderHeader();
      // 仮注文場合
      if (PaymentStatus.PAID.longValue() == header.getPaymentStatus()) {
        // 发货基本信息
        List<ShippingContainer> shippingList = getShippingList(orderNo);
        for (ShippingContainer shipping : shippingList) {
          ShippingHeader shippingHeader = shipping.getShippingHeader();
          // 发货状态
          shippingHeader.setShippingStatus(ShippingStatus.READY.longValue());
          // 行号
          shippingHeader.setOrmRowid(shippingHeader.getOrmRowid());
          // 更新时间
          shippingHeader.setUpdatedDatetime(shippingHeader.getUpdatedDatetime());
          // 更新者
          shippingHeader.setUpdatedUser(shippingHeader.getUpdatedUser());
          txMgr.update(shippingHeader);

        }
      }
      // M17N 10361 追加 ここまで

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      logger.error(e);
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      logger.debug(e);
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }

    return result;
  }

  public List<OrderDetail> getResearvationOrderDetail(String shopCode, String skuCode) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_RESERVED_ORDER_QUERY, shopCode, skuCode);
    return DatabaseUtil.loadAsBeanList(query, OrderDetail.class);
  }

  public Map<String, OrderPaymentInfo> getOrderPaymentInfo(List<String> orderNoList) {
    Map<String, OrderPaymentInfo> map = new HashMap<String, OrderPaymentInfo>();

    for (String orderNo : orderNoList) {
      Query receivedQuery = new SimpleQuery(OrderPaymentInfoQuery.GET_MAIL_SENT_DATE_LIST_FROMTYPE, MailType.RECEIVED_PAYMENT
          .getValue(), orderNo);
      List<RespectiveMailqueue> receivedMailList = DatabaseUtil.loadAsBeanList(receivedQuery, RespectiveMailqueue.class);
      List<String> receivedMailSentDateList = new ArrayList<String>();
      for (RespectiveMailqueue queue : receivedMailList) {
        // 最新の物のみ表示すれば良いので、一番最後の物だけ残す。全て表示したい場合はここを修正する。

        receivedMailSentDateList.clear();
        receivedMailSentDateList.add(DateUtil.toDateTimeString(queue.getMailSentDatetime(), "yy/MM/dd"));
      }
      Query reminderQuery = new SimpleQuery(OrderPaymentInfoQuery.GET_MAIL_SENT_DATE_LIST_FROMTYPE, MailType.PAYMENT_REMINDER
          .getValue(), orderNo);
      List<RespectiveMailqueue> reminderMailList = DatabaseUtil.loadAsBeanList(reminderQuery, RespectiveMailqueue.class);
      List<String> reminderMailSentDateList = new ArrayList<String>();
      for (RespectiveMailqueue queue : reminderMailList) {
        // 最新の物のみ表示すれば良いので、一番最後の物だけ残す。全て表示したい場合はここを修正する。

        reminderMailSentDateList.clear();
        reminderMailSentDateList.add(DateUtil.toDateTimeString(queue.getMailSentDatetime(), "yy/MM/dd"));
      }
      OrderPaymentInfo orderPaymentInfo = new OrderPaymentInfo();
      orderPaymentInfo.setOrderNo(orderNo);
      orderPaymentInfo.setReceivedMailSentDateList(receivedMailSentDateList);
      orderPaymentInfo.setReminderMailSentDateList(reminderMailSentDateList);
      map.put(orderNo, orderPaymentInfo);
    }

    return map;
  }

  public Long getShippingMailCount(String shippingNo) {
    Query countQuery = new SimpleQuery(OrderServiceQuery.COUNT_SHIPPING, shippingNo);
    Long count = NumUtil.toLong(DatabaseUtil.executeScalar(countQuery).toString());
    return count;
  }

  private void performOrderEvent(OrderEvent event, OrderEventType type) {
    ServiceEventHandler.execute(this.orderListeners, event, type);
  }

  private void performShippingEvent(ShippingEvent event, ShippingEventType type) {
    ServiceEventHandler.execute(this.shippingListeners, event, type);
  }

  private void performPaymentEvent(PaymentEvent event, PaymentEventType type) {
    ServiceEventHandler.execute(this.paymentListeners, event, type);
  }

  // v10-ch-pg added start
  public ServiceResult settlePhantomOrder(OrderHeader header) {
    TransactionManager transaction = DIContainer.getTransactionManager();
    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());

    OrderContainer container = getOrder(header.getOrderNo());
    OrderHeader orgOrderHeader = container.getOrderHeader();

    // 支払方法情報を生成
    // 支付方法信息设置

    CashierPayment cashierPayment = new CashierPayment();
    cashierPayment.setShopCode(orgOrderHeader.getShopCode());
    CashierPaymentTypeBase cashierPaymentType = new CashierPaymentTypeBase();
    cashierPaymentType.setPaymentMethodCode(Long.toString(orgOrderHeader.getPaymentMethodNo()));
    cashierPaymentType.setPaymentMethodName(orgOrderHeader.getPaymentMethodName());
    cashierPaymentType.setPaymentMethodType(orgOrderHeader.getPaymentMethodType());
    cashierPaymentType.setPaymentCommission(orgOrderHeader.getPaymentCommission());
    cashierPayment.setSelectPayment(cashierPaymentType);

    try {
      transaction.begin(getLoginInfo());
      transaction.update(header);
      // M17N 10361 追加 ここから
      // 仮注文場合
      // if (OrderStatus.PHANTOM_ORDER.longValue() ==
      // orgOrderHeader.getOrderStatus()) {

      // 普通订单 && 未支付状态
      if (OrderStatus.ORDERED.longValue().equals(orgOrderHeader.getOrderStatus())
          && !PaymentStatus.PAID.longValue().equals(orgOrderHeader.getPaymentStatus())) {

        // 发货基本信息
        List<ShippingContainer> shippingList = getShippingList(header.getOrderNo());
        for (ShippingContainer shipping : shippingList) {
          ShippingHeader shippingHeader = shipping.getShippingHeader();
          // 发货状态
          shippingHeader.setShippingStatus(ShippingStatus.READY.longValue());
          // 行号
          shippingHeader.setOrmRowid(shippingHeader.getOrmRowid());
          // 更新时间
          shippingHeader.setUpdatedDatetime(shippingHeader.getUpdatedDatetime());
          // 更新者
          shippingHeader.setUpdatedUser(shippingHeader.getUpdatedUser());
          transaction.update(shippingHeader);

        }
      }
      // M17N 10361 追加 ここまで
      transaction.executeProcedure(new ActivatePointProcedure(header.getOrderNo(), this.getLoginInfo().getRecordingFormat()));
      transaction.commit();
      
      // 2014/06/16 库存更新对应 ob_卢 add start
      performStockEvent(new StockEvent(header.getOrderNo()), StockEventType.EC);
      // 2014/06/16 库存更新对应 ob_卢 add end
      
      OrderMailType omt = OrderMailType.PC;
      performOrderEvent(new OrderEvent(orgOrderHeader.getOrderNo(), cashierPayment, omt), OrderEventType.RECEIVED);
      performPaymentEvent(new PaymentEvent(header.getOrderNo(), header.getPaymentDate()), PaymentEventType.UPDATED);
    } catch (RuntimeException e) {
      logger.error(e);
      transaction.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      transaction.dispose();
    }
    return result;
  }

  public ServiceResult deletePhantomOrders(Long expireHours) {
    TransactionManager transaction = DIContainer.getTransactionManager();
    ServiceResultImpl result = new ServiceResultImpl();
    // M17N 10361 修正 ここから
    // try {
    // transaction.begin(getLoginInfo());
    // Object expireHoursObj = null;
    // if (DIContainer.getWebshopConfig().isPostgreSQL()) {
    // expireHoursObj = NumUtil.toString(expireHours);
    // } else {
    // expireHoursObj = expireHours;
    // }
    // Object[] args = {
    // getLoginInfo().getRecordingFormat(), DateUtil.getSysdate(),
    // expireHoursObj
    // };
    // String sql = "";
    // if (DIContainer.getWebshopConfig().isPostgreSQL()) {
    // sql = OrderServiceQuery.PLSQL_CANCEL_PHANTOM_ORDER_QUERY;
    // } else {
    // sql = OrderServiceQuery.CANCEL_PHANTOM_ORDER_QUERY;
    // }
    // transaction.executeUpdate(sql, args);
    // transaction.commit();
    // } catch (RuntimeException e) {
    // logger.error(e);
    // transaction.rollback();
    // result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    // } finally {
    // transaction.dispose();
    // }
    // M17N 10361 修正 ここから
    Object expireHoursObj = null;
    String sql = "";
    boolean returnB = true;
    PaymentMethodDao paymentDao = DIContainer.getDao(PaymentMethodDao.class);
    Query paymentQuery = new SimpleQuery(OrderServiceQuery.CANCEL_CUP_SPS_ALL);
    // 全てAlipayと銀聯支払方法取得
    List<PaymentMethod> payList = paymentDao.findByQuery(paymentQuery);
    try {
      transaction.begin(getLoginInfo());
      if (payList.size() > 0) {
        for (PaymentMethod paymentMethodList : payList) {
          // 支払い番号
          String paymentMethodNo = NumUtil.toString(paymentMethodList.getPaymentMethodNo());
          // 期限日数
          Long limitDays = paymentMethodList.getPaymentLimitDays() * 24;

          if (DIContainer.getWebshopConfig().isPostgreSQL()) {
            expireHoursObj = NumUtil.toString(limitDays);
            sql = OrderServiceQuery.FIND_CANCEL_PHANTOM_ORDER_QUERY_FOR_PLSQL1;
          } else {
            expireHoursObj = limitDays;
            sql = OrderServiceQuery.FIND_CANCEL_PHANTOM_ORDER_QUERY1;
          }
          // 仮注文と仮予約削除
          returnB = deleteOrderStats(result, transaction, paymentMethodNo, expireHoursObj, sql);
          if (returnB == false) {
            break;
          }
        }
      } else {
        if (DIContainer.getWebshopConfig().isPostgreSQL()) {
          expireHoursObj = NumUtil.toString(expireHours);
          sql = OrderServiceQuery.FIND_CANCEL_PHANTOM_ORDER_QUERY_FOR_PLSQL;
        } else {
          expireHoursObj = expireHours;
          sql = OrderServiceQuery.FIND_CANCEL_PHANTOM_ORDER_QUERY;
        }
        // 仮注文と仮予約削除
        returnB = deleteOrderStats(result, transaction, "", expireHoursObj, sql);
      }
      if (returnB) {
        transaction.commit();
      } else {
        transaction.rollback();
      }
    } catch (RuntimeException e) {
      transaction.rollback();
    } finally {
      transaction.dispose();
    }
    // M17N 10361 修正 ここまで
    return result;

  }

  private boolean deleteOrderStats(ServiceResultImpl result, TransactionManager transaction, String paymentMethodNo,
      Object expireHoursObj, String sql) {

    Logger logger = Logger.getLogger(this.getClass());
    List<String> orderNoList;
    // 削除対象注文データを取得する
    if (paymentMethodNo.equals("")) {
      orderNoList = DatabaseUtil.loadAsStringList(new SimpleQuery(sql, expireHoursObj));
    } else {
      orderNoList = DatabaseUtil.loadAsStringList(new SimpleQuery(sql, paymentMethodNo, expireHoursObj));
    }
    if (orderNoList != null && !orderNoList.isEmpty()) {
      OrderHeaderDao oHeaderDao = DIContainer.getDao(OrderHeaderDao.class);
      OrderDetailDao oDetailDao = DIContainer.getDao(OrderDetailDao.class);
      PointHistoryDao pointHistoryDao = DIContainer.getDao(PointHistoryDao.class);
      // 仮注文をキャンセルする
      for (String orderNo : orderNoList) {
        try {

          OrderHeader header = oHeaderDao.load(orderNo);
          OrderStatus status = OrderStatus.fromValue(header.getOrderStatus());
          // 受注状態を変更する
          transaction.executeUpdate(OrderServiceQuery.CANCEL_PHANTOM_ORDER_QUERY_BY_ORDER_NO, getLoginInfo().getRecordingFormat(),
              DateUtil.getSysdate(), orderNo);

          // 該当受注のポイント履歴を無効化
          List<PointHistory> pointHistoryList = pointHistoryDao.findByQuery(OrderServiceQuery.GET_POINT_HISTORY_BY_ORDER_NO,
              orderNo);
          for (PointHistory pointHistory : pointHistoryList) {
            pointHistory.setPointIssueStatus(NumUtil.toLong(PointIssueStatus.DISABLED.getValue()));
            pointHistory.setDescription(Messages.getString("service.impl.OrderServiceImpl.0"));
            PointUpdateProcedure pointUpdate = new PointUpdateProcedure(pointHistory);
            transaction.executeProcedure(pointUpdate);
          }
          // 在庫引き当てを取り消しする
          List<OrderDetail> orgOrderDetails = oDetailDao.findByQuery(OrderServiceQuery.ORDER_DETAIL_LIST_QUERY, orderNo);
          List<StockUnit> stockUnitList = createStockUtil(orgOrderDetails);
          StockManager stockManager = transaction.getStockManager();
          boolean successUpdateStock = false;
          if (status == OrderStatus.PHANTOM_ORDER) {
            successUpdateStock = stockManager.deallocate(stockUnitList);
          } else if (status == OrderStatus.PHANTOM_RESERVATION) {
            successUpdateStock = stockManager.cancelReserving(stockUnitList);
          }
          if (!successUpdateStock) {
            transaction.rollback();
            result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
            continue;
          }

          // 出荷データステータスを変更する
          for (ShippingContainer shippingContainer : getShippingList(orderNo)) {
            shippingContainer.getShippingHeader().setShippingStatus(ShippingStatus.CANCELLED.longValue());
            transaction.update(shippingContainer.getShippingHeader());
          }
        } catch (RuntimeException e) {
          logger.error(e);
          result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
          return false;
        } finally {
        }
      }
    }
    return true;
  }

  public CouponIssue getCoupon(String shopCode, String orderPrice) {
    CouponIssueDao couponIssueDao = DIContainer.getDao(CouponIssueDao.class);
    List<CouponIssue> list = couponIssueDao.findByQuery(OrderServiceQuery.GET_COUPON_ISSUE, shopCode, orderPrice);
    return (list != null && list.size() > 0) ? list.get(0) : null;
  }

  public List<CustomerCouponInfo> getPaymentCouponList(String customerCode) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_PAYMENT_COUPON_LIST, customerCode, CouponUsedFlg.ENABLED.longValue());
    return DatabaseUtil.loadAsBeanList(query, CustomerCouponInfo.class);
  }

  public List<CustomerCoupon> getOrderUsedCoupon(String orderNo) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_ORDER_COUPON_LIST, orderNo);
    return DatabaseUtil.loadAsBeanList(query, CustomerCoupon.class);
  }

  public List<CustomerCoupon> getOrderModifyEnableCoupon(String customerCode, String orderNo) {
    Query query = new SimpleQuery(OrderServiceQuery.ORDER_MODIFY_ENABLE_COUPON, customerCode, CouponFunctionEnabledFlg.ENABLED
        .longValue(), orderNo);
    return DatabaseUtil.loadAsBeanList(query, CustomerCoupon.class);
  }

  public CustomerCoupon getOldGetCoupon(String orderNo) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_OLD_GET_COUPON_LIST, orderNo);
    return DatabaseUtil.loadAsBean(query, CustomerCoupon.class);
  }

  private CouponIssue getNewCustomerCoupon(OrderContainer order) {
    CouponIssue cc = getCoupon(DIContainer.getWebshopConfig().getSiteShopCode(), NumUtil.toString(BigDecimalUtil.subtract(order
        .getCommodityTotalAmount(), order.getOrderHeader().getUsedPoint().divide(DIContainer.getWebshopConfig().getRmbPointRate(),
        PointUtil.getAcquiredPointScale(), RoundingMode.FLOOR))));
    return cc;
  }

  // v10-ch-pg added end

  // 201101205 yl add start
  public PaymentAlipayResultBean getAlipayBean(OrderContainer orderContainer) {
    AlipayManager paymentManager = new AlipayManager();
    PaymentAlipayResult result = paymentManager.payment(orderContainer);
    return result.getResultBean();
  }

  public PaymentChinapayResultBean getChinapayBean(OrderContainer orderContainer) {
    ChinapayManager payment = new ChinapayManager();
    return payment.payment(orderContainer).getResultBean();
  }

  public PaymentChinapayResultBean getWapChinapayBean(OrderContainer orderContainer) {
    ChinapayWapManager payment = new ChinapayWapManager();
    return payment.payment(orderContainer).getResultBean();
  }
  
  // 201101205 yl add end
  public ReturnInfo getReturnInfo(String returnNo) {
    ReturnInfo returnInfo = new ReturnInfo();
    ReturnHeaderDao dao = DIContainer.getDao(ReturnHeaderDao.class);
    ShippingDetailDao sDetailDao = (ShippingDetailDao) DIContainer.getDao(ShippingDetailDao.class);

    ReturnHeader returnHeader = dao.load(returnNo);
    if (returnHeader != null) {
      ShippingContainer shippingContainer = new ShippingContainer();
      ShippingHeader shipHeader = this.getShippingHeader(returnHeader.getShippingNo());
      shippingContainer.setShippingHeader(shipHeader);
      List<ShippingDetail> detailList = sDetailDao.findByQuery(OrderServiceQuery.SHIPPING_DETAIL_LIST_QUERY_ASC, returnHeader
          .getShippingNo());
      shippingContainer.setShippingDetails(detailList);
      returnInfo.setShippingContainer(shippingContainer);
    }
    returnInfo.setReturnheader(returnHeader);
    return returnInfo;
  }

  // Add by V10-CH start
  public OrderSummary getPayedOrderSummary(String customerCode, String dateFrom, String dateTo) {
    Query query = new SimpleQuery(
        " SELECT SUM(TOTAL_AMOUNT-COALESCE(DISCOUNT_PRICE,0)+PAYMENT_COMMISSION) TOTAL_AMOUNT ,CUSTOMER_CODE "
            + " FROM ORDER_SUMMARY_VIEW " + " WHERE CUSTOMER_CODE = ? " + " AND ORDER_STATUS <> '2' "
            + " AND SHIPPING_STATUS_SUMMARY <> '4' " + " AND RETURN_STATUS_SUMMARY = '0' "
            + " AND ORDER_DATETIME BETWEEN ? AND  ? " + " GROUP BY CUSTOMER_CODE ", customerCode, dateFrom, dateTo);
    List<OrderSummary> summaryList = DatabaseUtil.loadAsBeanList(query, OrderSummary.class);
    for (OrderSummary summary : summaryList) {
      return summary;
    }
    return null;
  }

  public List<OrderSummary> getNeedRemindSummary(String dateFrom, String dateTo) {
    Query query = new SimpleQuery(
        " SELECT SUM(TOTAL_AMOUNT-COALESCE(DISCOUNT_PRICE,0)+PAYMENT_COMMISSION) TOTAL_AMOUNT ,CUSTOMER_CODE "
            + " FROM ORDER_SUMMARY_VIEW " + " WHERE ORDER_STATUS <> '2' " + " AND SHIPPING_STATUS_SUMMARY <> '4' "
            + " AND RETURN_STATUS_SUMMARY = '0' " + " AND ORDER_DATETIME BETWEEN ? AND  ? " + " GROUP BY CUSTOMER_CODE ", dateFrom,
        dateTo);
    return DatabaseUtil.loadAsBeanList(query, OrderSummary.class);
  }

  // 2012-02-17 yyq update start
  // public List<OrderSummary> getNeedRemindSummary(String custumerCode, String
  // dateFrom, String dateTo) {
  // Query query = new SimpleQuery(
  // "select sum(total_amount) total_amount ,customer_code from order_summary_view where customer_code = ? and order_datetime between ? and ? and payment_status =1 group by customer_code",
  // custumerCode, dateFrom, dateTo);
  // return DatabaseUtil.loadAsBeanList(query, OrderSummary.class);
  // }

  public List<OrderSummary> getNeedRemindSummary(String custumerCode, String dateFrom, String dateTo) {
    Query query = new SimpleQuery(
        " SELECT SUM(TOTAL_AMOUNT-COALESCE(DISCOUNT_PRICE,0)+PAYMENT_COMMISSION) TOTAL_AMOUNT ,CUSTOMER_CODE "
            + " FROM ORDER_SUMMARY_VIEW " + " WHERE CUSTOMER_CODE = ? " + " AND ORDER_STATUS <> '2' "
            + " AND SHIPPING_STATUS_SUMMARY <> '4' " + " AND RETURN_STATUS_SUMMARY = '0' "
            + " AND ORDER_DATETIME BETWEEN ? AND  ? " + " GROUP BY CUSTOMER_CODE ", custumerCode, dateFrom, dateTo);
    return DatabaseUtil.loadAsBeanList(query, OrderSummary.class);
  }

  // 2012-02-17 yyq update end

  // Add by V10-CH end

  // 20111223 shen add start
  public CustomerVatInvoice getCustomerVatInvoice(String customerCode) {
    Query query = new SimpleQuery(OrderServiceQuery.LOAD_CUSTOMER_VAT_INVOICE_BY_CUSTOMER_CODE, customerCode);
    return DatabaseUtil.loadAsBean(query, CustomerVatInvoice.class);
  }

  // 20111223 shen add end

  /**
   * add by os012 20120106 start 淘宝已下订单处理
   */
  public ServiceResult TmallOrdersDownLoad(List<TmallOrderHeader> orderHeadDataFromAPI) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TmallSuitCommodityDao suitDao = DIContainer.getDao(TmallSuitCommodityDao.class);
    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    txMgr.begin(getLoginInfo());
 // 2014/06/12 库存更新对应 ob_李 add start
    //引当数变化订单List
    List<String> eventOrderNoList = new ArrayList<String>();
    TmallOrderHeaderDao orderDao = DIContainer.getDao(TmallOrderHeaderDao.class);
    TmallShippingHeaderDao shippingDao = DIContainer.getDao(TmallShippingHeaderDao.class);
 // 2014/06/12 库存更新对应 ob_李 add end
    try {
      // 组合商品对应 ADD 20130620 START
      CatalogService service = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
      for (TmallOrderHeader orderDataFromAPI : orderHeadDataFromAPI) {
        Long oDetailCCount = 0L;
        for (int i = 0; i < orderDataFromAPI.getOrderDetailList().size(); i++) {
          TmallOrderDetail tOrderD = orderDataFromAPI.getOrderDetailList().get(i);
          TmallShippingDetail tSdetail = orderDataFromAPI.getShippingHeader().getShippingDetailList().get(i);
          CommodityInfo commodityInfo = service.getCCommodityInfo(tSdetail.getShopCode(), tSdetail.getSkuCode());
          CCommodityHeader cch = commodityInfo.getCheader();
          CCommodityDetail ccd = commodityInfo.getCdetail();
          // 组合品对应
          if (StringUtil.hasValue(cch.getOriginalCommodityCode())) {
            TmallShippingDetailComposition tsDetailC = createTmallShDetailC(tSdetail, cch, ccd);
            tsDetailC.setCompositionNo(oDetailCCount++); //组合明细编号
            tsDetailC.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID); //行ID
            setUserStatus(tsDetailC);
            tOrderD.setTShDetailC(tsDetailC);
          } else {
            tOrderD.setTShDetailC(null);
          }
          // 套装商品对应
          if (suitDao.exists(tOrderD.getCommodityCode())) {
            List<SetCommodityComposition> compositionList = service.getSetCommodityInfo("00000000",tOrderD.getCommodityCode());
            List<TmallShippingDetailComposition> suitDetailList = new ArrayList<TmallShippingDetailComposition>();
            for (SetCommodityComposition detail : compositionList) {
              CommodityInfo commoditySuitInfo = service.getCCommodityInfo("00000000", detail.getChildCommodityCode());
              TmallShippingDetailComposition suitDetail = createSuitDetail(tSdetail, commoditySuitInfo.getCheader(), commoditySuitInfo.getCdetail() ,detail,compositionList.size());
              suitDetail.setCompositionNo(oDetailCCount++); //组合明细编号
              suitDetail.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID); //行ID
              setUserStatus(suitDetail);
              suitDetailList.add(suitDetail);
            }
            tOrderD.setSuitShDetailList(suitDetailList);
          } else {
            tOrderD.setSuitShDetailList(null);
          }
          
          
        }
        // 组合商品对应 ADD 20130620 END
        // 2014/06/12 库存更新对应 ob_李 add start
        Long orgShippingStatus = ShippingStatus.NOT_READY.longValue();//初始值设定（发货不可）
        // 取得EC当前的状态 设置为当前的状态
        TmallOrderHeader ecOrderHeader = orderDao.loadByTid(orderDataFromAPI.getTmallTid()); // 获取EC系统TmallOrderHeader
        if (ecOrderHeader!=null) {
          TmallShippingHeader ecShippingHeader = shippingDao.loadByOrderNo(ecOrderHeader.getOrderNo()); // 获取EC系统TmallShippingHeader
          if (ecShippingHeader!=null) {
            orgShippingStatus = ecShippingHeader.getShippingStatus().longValue(); // ec侧发货状态
          }
        }        
        // 2014/06/12 库存更新对应 ob_李 add end
        // 执行批次处理
        serviceResult = (ServiceResultImpl) TmallOrdersHandling(orderDataFromAPI, orderDataFromAPI.getOrderDetailList(), txMgr,true);
     // 2014/06/12 库存更新对应 ob_李 add start
        // 再次取得EC当前的发货状态
        Long newShippingStatus = ShippingStatus.NOT_READY.longValue();//初始值设定（发货不可）
        Connection connection = ((TransactionManagerImpl) txMgr).getConnection();
        Query query = new SimpleQuery(OrderSearchQuery.GET_TMALL_ORDER_HEADER,orderDataFromAPI.getTmallTid());
        List<TmallOrderHeader> orderList = DatabaseUtil.loadAsBeanList(connection, query, TmallOrderHeader.class); // 获取EC系统TmallOrderHeader
        if (orderList!=null && orderList.size()>0) {
          query = new SimpleQuery(OrderSearchQuery.GET_TMALL_SHIPPING_HEADER,orderList.get(0).getOrderNo());
          List<TmallShippingHeader> shippingList = DatabaseUtil.loadAsBeanList(connection, query, TmallShippingHeader.class);
          if (shippingList!=null && shippingList.size()>0) {
            newShippingStatus = shippingList.get(0).getShippingStatus().longValue(); // ec侧发货状态
          }
        }
        //当订单的发货状态从发货不可改为发货可能时，触发引当数变动事件
        if (ShippingStatus.NOT_READY.longValue().equals(orgShippingStatus) && 
            ShippingStatus.READY.longValue().equals(newShippingStatus)) {
          eventOrderNoList.add(orderList.get(0).getOrderNo());
        }
     // 2014/06/12 库存更新对应 ob_李 add end
        if (serviceResult.hasError()) {
          logger.debug(serviceResult);
          return serviceResult;
        }
      }
      txMgr.commit();
      // 2014/06/12 库存更新对应 ob_李 add start
      for (String orderNo : eventOrderNoList) {
          //·触发TMALL引当数变动事件
          performStockEvent(new StockEvent(orderNo), StockEventType.TM);
      }
      //2014/06/12 库存更新对应 ob_李 add end
    } catch (DataAccessException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  /***
   * add by os012 20111226 start.T-Mall订单下载批处理 <dt><b>处理概要: </b></dt> <dd>
   * 根据TmallOrderHeader、TmallOrderDetail、blExecute执行T-Mall订单下载批处理</dd>
   * <ol>
   * <li>查询订单是否EC存在订单</li>
   * <li>存在直接分析TMall是否取消</li>
   * <li>不存在时新增order再分析分析TMall是否取消</li>
   * </ol>
   * </dl> </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>提供APITmallOrderHeader和APITmallOrderDetail</dd>
   * </dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>blExecute</dd> </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>batch调用。</dd>
   * </dl>
   * </p>
   * 
   * @return
   */
  public ServiceResult TmallOrdersHandling(TmallOrderHeader apiOrderHeader, List<TmallOrderDetail> apiOrderDetailList,
      TransactionManager txMgr, boolean blExecute) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TmallOrderHeaderDao orderDao = DIContainer.getDao(TmallOrderHeaderDao.class);
    TmallShippingHeaderDao shippingDao = DIContainer.getDao(TmallShippingHeaderDao.class);
    TmallOrderDetailDao tDetailDao = DIContainer.getDao(TmallOrderDetailDao.class);
    TmallShippingHeader tShippingH = apiOrderHeader.getShippingHeader();
    try {

      if ((blExecute == false)) { // blExecute参数值为false时,表示不执行,返回
        logger.debug(Messages.log("service.impl.OrderServiceImpl.16"));
        return serviceResult;
      }
      // 查询订单是否EC存在订单，
      // 存在直接分析TMall是否取消，
      // 不存在时新增order再分析分析TMall是否取消
      String apiTmallStatus = apiOrderHeader.getTmallStatus().toUpperCase();// api订单淘宝订单状态

      Long apiOrderStatus = apiOrderHeader.getOrderStatus().longValue(); // api侧订单状态

      boolean ecOrderExists = orderDao.existsTid(apiOrderHeader.getTmallTid()); // EC系统TMALL存在订单状态

      if (ecOrderExists) { // EC系统TMALL存在订单状态处理

        TmallOrderHeader ecOrderHeader = orderDao.loadByTid(apiOrderHeader.getTmallTid()); // 获取EC系统TmallOrderHeader
        TmallShippingHeader ecShippingHeader = shippingDao.loadByOrderNo(ecOrderHeader.getOrderNo()); // 获取EC系统TmallShippingHeader

        if (resultStatusAfterDecideApi(apiOrderHeader, true) == 1) {// 如果EC端该订单已取消;API订单和EC订单的状态都为交易成功则无需做特别处理
          return serviceResult;
        } else if (resultStatusAfterDecideApi(apiOrderHeader, true) == 2) {// API端交易成功且EC端交易未成功时，只修改EC端的交易成功和最终修改时间,付款
          logger.debug(Messages.log("修改EC端订单完成状态信息"));
          Query tradeFinishQuery = new SimpleQuery(OrderSearchQuery.UPDATE_TMALL_ORDER_TARDE_FINISH, apiTmallStatus, apiOrderHeader
              .getPaidPrice(), apiOrderHeader.getTmallEndTime(), apiOrderHeader.getTmallModifiedTime(), this.getLoginInfo()
              .getRecordingFormat(), DatabaseUtil.getSystemDatetime(), ecOrderHeader.getOrderNo());
          txMgr.executeUpdate(tradeFinishQuery);
          return serviceResult;
        } else if (resultStatusAfterDecideApi(apiOrderHeader, true) == 3) { // 如果api侧交易关闭并且ec侧已付款并且没有发货，则取消EC订单、修改EC引当
          apiOrderHeader.setOrderStatus(OrderStatus.CANCELLED.longValue());
          apiOrderStatus = OrderStatus.CANCELLED.longValue();// 将api订单状态置为取消
        }
        if (apiOrderStatus.longValue() == OrderStatus.CANCELLED.longValue()) { // API拿到的订单为取消订单时
          // 如果EC系统存在订单并且 取消状态不为 取消时，需要取消EC端的订单并修改库存引当数
          // 第一步取消EC系统存在订单、取消EC系统存在发货单
          serviceResult = (ServiceResultImpl) CancelOrderAndShipping(ecOrderHeader, apiOrderHeader.getTmallStatus(), txMgr);
          if (serviceResult.hasError()) {
            return serviceResult;
          }
          // 如果货到付款支付不修改修改引当,返回
          if (PaymentMethodType.CASH_ON_DELIVERY.getValue().equals(apiOrderHeader.getPaymentMethodType())) {
            return serviceResult;
          }

          // 第二步修改引当数：引当数-EC侧此订单商品数量start
          
          // UPDATE 20130808 START
          
          // 恢复礼品引当数 add 20121023 start
          Query queryOrderDetailList = new SimpleQuery(OrderSearchQuery.TMALL_ORDER_DETAIL_LIST_BYTID_QUERY, apiOrderHeader
              .getTmallTid());
          List<TmallOrderDetail> orderDetailList = DatabaseUtil.loadAsBeanList(queryOrderDetailList, TmallOrderDetail.class);

          if (orderDetailList != null && orderDetailList.size() > 0) {
            CatalogService service = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
            for (TmallOrderDetail orderDetail : orderDetailList) {
              CommodityInfo commodityInfo = service.getCCommodityInfo(orderDetail.getShopCode(), orderDetail.getSkuCode());
              CCommodityHeader cch = commodityInfo.getCheader();
              int updateCnt = 0;
              if(cch != null && cch.getCommodityType() != null && cch.getCommodityType() == 1L){
             // 2014/06/12 库存更新对应 ob_李 update start
                //Query stockUpdateQuery3 = new SimpleQuery(OrderServiceQuery.UPDATE_STOCK_ALLOCATED_AFTER_ORDER_CANCEL, this
                //    .getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), orderDetail.getPurchasingAmount(),
                //    orderDetail.getShopCode(), orderDetail.getSkuCode());
                Query stockUpdateQuery3 = new SimpleQuery(OrderServiceQuery.UPDATE_TMALL_STOCK_AFTER_ORDER_CANCEL, this
                        .getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), orderDetail.getPurchasingAmount(),
                        orderDetail.getShopCode(), orderDetail.getSkuCode());
             // 2014/06/12 库存更新对应 ob_李 update end
                updateCnt = txMgr.executeUpdate(stockUpdateQuery3);
                if (updateCnt != 1) {
                  serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
                  txMgr.rollback();
                  return serviceResult;
                }
              }
            }
          }
          // 恢复礼品引当数 add 20121023 end
          
          if (apiOrderDetailList != null && apiOrderDetailList.size() > 0) {
            for (TmallOrderDetail orderDetail : apiOrderDetailList) {
           // UPDATE 20130808 END
              
              // 获取EC系统TMALL详细订单
              //Query queryOrderDetail = new SimpleQuery(OrderSearchQuery.TMALL_ORDER_DETAIL_QUERY, orderDetail.getSkuCode(),
              //    apiOrderHeader.getTmallTid());
              //TmallOrderDetail orderDetailEc = DatabaseUtil.loadAsBean(queryOrderDetail, TmallOrderDetail.class);
              
              // 组合商品修改 START
              int updateCnt = 0;
              if (orderDetail.getTShDetailC() != null) {
                Query stockUpdateQuery1 = new SimpleQuery(OrderServiceQuery.UPDATE_STOCK_ALLOCATED_AFTER_ORDER_CANCEL2, this
                    .getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), orderDetail.getTShDetailC().getPurchasingAmount(),
                    // 店铺编号、组合品编号、原商品编号
                    orderDetail.getShopCode(), orderDetail.getSkuCode(), orderDetail.getTShDetailC().getChildCommodityCode());
                updateCnt = txMgr.executeUpdate(stockUpdateQuery1);
                
                // 2014/06/12 库存更新对应 ob_李 delete start
//                Query stockUpdateQuery2 = new SimpleQuery(OrderServiceQuery.UPDATE_STOCK_ALLOCATED_AFTER_ORDER_CANCEL, this
//                    .getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), orderDetail.getPurchasingAmount(),
//                    // 店铺编号、原商品编号
//                    orderDetail.getShopCode(), orderDetail.getTShDetailC().getChildCommodityCode());
//                updateCnt = txMgr.executeUpdate(stockUpdateQuery2);
                // 2014/06/12 库存更新对应 ob_李 delete end
              } else if (orderDetail.getSuitShDetailList() != null && orderDetail.getSuitShDetailList().size() > 0) {
                // 2014/06/12 库存更新对应 ob_李 delete start
//                for (TmallShippingDetailComposition  suitDetail : orderDetail.getSuitShDetailList()) {
//                  Query stockUpdateQuery1 = new SimpleQuery(OrderServiceQuery.UPDATE_SUIT_STOCK_CANCEL, this
//                      .getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), orderDetail.getPurchasingAmount(),
//                      // 店铺编号、明细商品编号
//                      suitDetail.getShopCode(), suitDetail.getChildCommodityCode());
//                  updateCnt = txMgr.executeUpdate(stockUpdateQuery1);
//                }
                // 2014/06/12 库存更新对应 ob_李 delete end
                Query stockUpdateQuery2 = new SimpleQuery(OrderServiceQuery.UPDATE_TMALL_SUIT_COMMODITY_CANCEL, this
                    .getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), orderDetail.getPurchasingAmount(),
                    // 套装商品编号
                    orderDetail.getSkuCode());
                updateCnt = txMgr.executeUpdate(stockUpdateQuery2);
                
              } else {
                // 2014/06/12 库存更新对应 ob_李 update start
//                Query stockUpdateQuery3 = new SimpleQuery(OrderServiceQuery.UPDATE_STOCK_ALLOCATED_AFTER_ORDER_CANCEL, this
//                    .getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), orderDetail.getPurchasingAmount(),
//                    orderDetail.getShopCode(), orderDetail.getSkuCode());
                Query stockUpdateQuery3 = new SimpleQuery(OrderServiceQuery.UPDATE_TMALL_STOCK_AFTER_ORDER_CANCEL, this
                    .getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), orderDetail.getPurchasingAmount(),
                    orderDetail.getShopCode(), orderDetail.getSkuCode());
                // 2014/06/12 库存更新对应 ob_李 update end
                updateCnt = txMgr.executeUpdate(stockUpdateQuery3);
              }
              // 组合商品 修改 END
              
              
              if (updateCnt != 1) {
                serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
                txMgr.rollback();
                return serviceResult;
              } else {
                // 付款后取消订单（发货前申请退款）要恢复淘宝上的库存
                if (apiOrderHeader.getTmallStatus().equals("TRADE_CLOSED")) {
                  // 将引当数恢复到tmall库存数
                  try {
                    TmallService service = ServiceLocator.getTmallService(getLoginInfo());
                    if (!StringUtil.isNullOrEmpty(orderDetail.getTmallSkuCode())
                        && !StringUtil.isNullOrEmpty(orderDetail.getTmallCommodityCode())) {
                      TmallCommoditySku tcs = new TmallCommoditySku();
                      tcs.setNumiid(orderDetail.getTmallCommodityCode());
                      tcs.setSkuId(orderDetail.getTmallSkuCode());
                      tcs.setQuantity(orderDetail.getPurchasingAmount().toString());
                      tcs.setUpdateType("2");
                      service.updateSkuStock(tcs);
                      logger.info(Messages.log("Numiid:" + orderDetail.getTmallCommodityCode()+"_SkuId:" + orderDetail.getTmallSkuCode()+ "恢复淘宝库存:" + orderDetail.getPurchasingAmount().toString()));
                    } else if (!StringUtil.isNullOrEmpty(orderDetail.getTmallCommodityCode())) {
                      TmallCommoditySku tcs = new TmallCommoditySku();
                      tcs.setNumiid(orderDetail.getTmallCommodityCode());
                      tcs.setQuantity(orderDetail.getPurchasingAmount().toString());
                      tcs.setUpdateType("2");
                      service.updateSkuStock(tcs);
                      logger.info(Messages.log("Numiid:" + orderDetail.getTmallCommodityCode() + "恢复淘宝库存:" + orderDetail.getPurchasingAmount().toString()));
                    }
                   
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
              }
            }
          }
          // 第二步修改引当数：引当数-EC侧此订单商品数量end

        } else {// API拿到的订单为正常订单时
          // 判断EC存在订单和TMALL订单是否付款,都已付款返回
          if (resultStatusAfterDecideApi(apiOrderHeader, true) == 6) {
            return serviceResult;
          }
          // 判断TMALL侧订单是否付款,已付款则只修改订单付款状态和时间(并且EC侧是未付款状态)
          if (resultStatusAfterDecideApi(apiOrderHeader, true) == 7) {
            // 2012/04/12 os011 修改发货状态为可发货start
            tShippingH.setShippingStatus(ShippingStatus.READY.longValue());
            // 2012/04/12 os011 修改发货状态为可发货end
            serviceResult = (ServiceResultImpl) UpdateOrderPayAndShipping(apiOrderHeader, tShippingH, ecOrderHeader.getOrderNo(),
                txMgr, PaymentStatus.PAID.longValue());
            return serviceResult;// 返回不再修改其他数据
          }
          // 按上面逻辑：如果EC侧是未付款状态,并且货到付款支付则返回不再修改其他数据
          if (PaymentMethodType.CASH_ON_DELIVERY.getValue().equals(apiOrderHeader.getPaymentMethodType())) {
            return serviceResult;
          }
          // 按上面逻辑：如果EC侧是未付款状态，apiTMALL状态WAIT_BUYER_CONFIRM_GOODS或者TRADE_CLOSED并且有付款时间，侧不做处理
          if (resultStatusAfterDecideApi(apiOrderHeader, true) == 9) {
            return serviceResult;
          }
          // 按上面逻辑：如果EC侧和api侧是未付款状态,apiTMALL状态TRADE_NO_CREATE_PAY或者WAIT_BUYER_PAY,则修改数量，价格及引当等
          if (resultStatusAfterDecideApi(apiOrderHeader, true) == 10) {
            // 修改发货状态为可发货end
            serviceResult = (ServiceResultImpl) UpdateOrderPayAndShipping(apiOrderHeader, tShippingH, ecOrderHeader.getOrderNo(),
                txMgr, PaymentStatus.NOT_PAID.longValue());
            if (serviceResult.hasError()) {
              return serviceResult;
            }
            // 更新T-Mall详细订单商品数量、价格和更新T-Mall在库表引当数
            serviceResult = (ServiceResultImpl) updateStockAndOrder(apiOrderDetailList, apiOrderHeader.getTmallTid(), ecOrderHeader
                .getOrderNo(), true, txMgr);// true表示修改价格
            // if (serviceResult.hasError()) {
            return serviceResult;
            // }
          }
          // 按上面逻辑：如果EC侧和api侧是未付款状态,apiTMALL状态WAIT_SELLER_SEND_GOODS，则修改EC侧订单为付款，发货状态为可发货
          if (resultStatusAfterDecideApi(apiOrderHeader, true) == 11) {
            Query tradeFinishQuery = new SimpleQuery(OrderSearchQuery.UPDATE_TMALL_ORDER_TARDE_PAID,
                PaymentStatus.PAID.longValue(), apiOrderHeader.getPaymentDate(), apiOrderHeader.getTmallModifiedTime(), this
                    .getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), ecOrderHeader.getOrderNo());
            txMgr.executeUpdate(tradeFinishQuery);
            // 修改发货状态
            Query shippingUpdateQuery = new SimpleQuery(OrderSearchQuery.UPDATE_TMALL_SHIPPING_HEADER_STATUS, ShippingStatus.READY
                .getValue(), this.getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), ecOrderHeader.getOrderNo());
            txMgr.executeUpdate(shippingUpdateQuery);

            return serviceResult;
          }
        }
      } else {// 新订单
        // 添加新订单操作
        logger.info("apiOrderHeader : " + apiOrderHeader.getTmallTid());
        logger.info("apiOrderDetailList : " + apiOrderDetailList.size());

        // 订单下载中封装gift明细start
        getGiftOrderShippingDetail(apiOrderHeader, apiOrderDetailList, txMgr);
        // 订单下载中封装gift明细end
        
        // 20140326 txw add start
        // 订单下载中封装宣传品明细start
        getPropagandaOrderShippingDetail(apiOrderHeader, apiOrderDetailList);
        // 订单下载中封装宣传品明细end
        // 20140326 txw add end

        serviceResult = (ServiceResultImpl) InsertTmallOrder(apiOrderHeader, apiOrderDetailList, txMgr);
        if (serviceResult.hasError()) {
          return serviceResult;
        }

        // Tmall侧订单是否取消,Tmall侧订单没有取消的时候更新T-Mall在库表引当数
        // Tmall侧订单付款前/货到付款交易关闭:TRADE_CLOSED_BY_TAOBAO,不改变引当
        // Tmall侧订单付款后交易关闭:TRADE_CLOSED,不改变引当
        if ((apiOrderHeader.getOrderStatus().longValue() != OrderStatus.CANCELLED.longValue())
            && (!"TRADE_CLOSED_BY_TAOBAO".equals(apiTmallStatus)) && (!"TRADE_CLOSED".equals(apiTmallStatus))) {
          // 3.引当数加CVS订单商品数量
          // 更新T-Mall在库表引当数=引当数+CVS订单商品数量
          serviceResult = (ServiceResultImpl) OnlyAddStockAllocated(apiOrderDetailList, apiOrderHeader.getTmallTid(), txMgr);
          if (serviceResult.hasError()) {
            return serviceResult;
          }
        }
      }
    } catch (DataAccessException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  // 订单下载中封装gift明细start
  public void getGiftOrderShippingDetail(TmallOrderHeader apiOrderHeader, List<TmallOrderDetail> apiOrderDetailList, TransactionManager txMgr)
      throws RuntimeException {

    CatalogService service = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
    
    List<List<NameValue>> gCommodityList = new ArrayList<List<NameValue>>();
    // 符合条件的赠品促销活动集合
    List<CampaignMain> cmList = service.getCampaignMainByType(apiOrderHeader.getCreatedDatetime());
    if (cmList == null) {
      return; // 当前没有活动
    }
    
    for (CampaignMain cmMain : cmList) {
      boolean addFlg = false;
      // 礼品List
      List<NameValue> giftCommodityList = new ArrayList<NameValue>();
      // 获取单个活动关联商品信息
      CampaignCondition cc = service.getCampaignConditionByType(cmMain.getCampaignCode());
      
      List<String> attrValue = new ArrayList<String>();
      if (cc != null && StringUtil.hasValue(cc.getAttributrValue())) {
        attrValue.addAll(Arrays.asList(cc.getAttributrValue().split(","))); 
      }
      int linkCommodityNums = 0;
      int totalNum = 0; //一个订单中符合活动的商品数量总数
      if (attrValue != null && attrValue.size() > 0) {
        for (TmallOrderDetail tOrderDetail : apiOrderDetailList) {
          if (attrValue.contains(tOrderDetail.getSkuCode())) {
            // 20140806 hdh upadate start
            totalNum += tOrderDetail.getPurchasingAmount();
            // 201400806 hdh update end
            
          }
        }
      }
      // 20140806 hdh upadate start
      //如果最小购买数为0则赠品最多只送活动中的赠品数量
      if(totalNum>0){
        if("0".equals(NumUtil.toString(cmMain.getMinCommodityNum()))){
          linkCommodityNums = cmMain.getGiftAmount().intValue();
        }else{
          linkCommodityNums = (int) (( totalNum /cmMain.getMinCommodityNum())*cmMain.getGiftAmount());
        }
      }

      // 20140806 hdh upadate end
      
      // 活动关联商品数量
      if (linkCommodityNums != 0) {
        // 获取单个活动赠品商品信息
        CampaignDoings cds = service.getCampaignDoingsList(cmMain.getCampaignCode());
        if (cds != null && StringUtil.hasValue(cds.getAttributrValue())) {
          String[] attr = cds.getAttributrValue().split(",");
          if (attr == null || attr.length == 0) {
            continue; //没有赠品的情况，不设定赠品信息
          }
          for (int idx = 0; idx < attr.length; idx++) {
            NameValue nv = new NameValue();
            nv.setName(attr[idx]); //赠送礼品编号
            nv.setValue(NumUtil.toString(linkCommodityNums)); //赠送礼品数量
            giftCommodityList.add(nv);
            addFlg = true;
          }
        }
      }
      // 有赠品的情况下，追加集合
      if (addFlg) {
        gCommodityList.add(giftCommodityList);
      }
    }
    

    // 礼品库存判断
    StockDao sDao = DIContainer.getDao(StockDao.class);
    
    boolean checkOrderFlg = false; // 订单检查判断
    for (List<NameValue> gcList : gCommodityList) {
      boolean stockFlg = true;
      for (NameValue nvSku : gcList) {
        // 2014/06/12 库存更新对应 ob_李 update start
//        Query query = new SimpleQuery("UPDATE STOCK SET RESERVATION_LIMIT = NULL WHERE STOCK_TMALL - ALLOCATED_TMALL >= ? AND SKU_CODE = ?",
//            nvSku.getValue(),nvSku.getName());
        Query query = new SimpleQuery("UPDATE TMALL_STOCK SET STOCK_QUANTITY = STOCK_QUANTITY WHERE STOCK_QUANTITY - ALLOCATED_QUANTITY >= ? AND SKU_CODE = ?",
            nvSku.getValue(),nvSku.getName());
        // 2014/06/12 库存更新对应 ob_李 update end
        int updateCnt = txMgr.executeUpdate(query);
        if (updateCnt < 1) {
          stockFlg = false;
          checkOrderFlg = true; // 库存不足，订单需要做检查
          break;
        }
        // 店铺编号、商品编号
        //Stock stock = sDao.load(apiOrderHeader.getShopCode(), nvSku.getName());
        //if (stock.getStockTmall() - stock.getAllocatedTmall() - NumUtil.toLong(nvSku.getValue()) < 0) {
        //  stockFlg = false;
        //  checkOrderFlg = true; // 库存不足，订单需要做检查
        //  break;
        //}
      }
      // 礼品库存充足
      if (stockFlg) {
        for (NameValue nvSku : gcList) {
          TmallOrderDetail tod = new TmallOrderDetail();
          TmallShippingDetail tsd = new TmallShippingDetail();
          CommodityHeader ch = new CommodityHeader();
          CommodityHeaderDao hDao = DIContainer.getDao(CommodityHeaderDao.class);
          CommodityHeader header = hDao.load(apiOrderHeader.getShopCode(), nvSku.getName());

          // 封装订单明细start
          tod.setOrderNo(apiOrderHeader.getOrderNo());
          tod.setShopCode(apiOrderHeader.getShopCode());
          tod.setSkuCode(nvSku.getName());
          tod.setCommodityCode(nvSku.getName());
          tod.setCommodityName(header.getCommodityName());
          tod.setPurchasingAmount(NumUtil.toLong(nvSku.getValue()));
          tod.setUnitPrice(new BigDecimal(0.00d));
          tod.setRetailPrice(new BigDecimal(0.00d));
          tod.setRetailTax(new BigDecimal(0.00d));
          tod.setCommodityTaxType(0L);
          tod.setAppliedPointRate(0L);
          tod.setTmallRefundStatus("NO_REFUND");
          tod.setOrmRowid(tod.getOrmRowid());
          tod.setCreatedDatetime(apiOrderHeader.getCreatedDatetime());
          tod.setCreatedUser("BATCH:0:0:0");
          tod.setUpdatedDatetime(apiOrderHeader.getUpdatedDatetime());
          tod.setUpdatedUser("BATCH:0:0:0");
          apiOrderDetailList.add(tod);
          // 封装订单明细end
          // 封装发货明细start
          tsd.setShopCode(apiOrderHeader.getShopCode());
          tsd.setSkuCode(nvSku.getName());
          tsd.setUnitPrice(new BigDecimal(0.00d));
          tsd.setRetailPrice(new BigDecimal(0.00d));
          tsd.setDiscountAmount(new BigDecimal(0.00d));
          tsd.setDiscountPrice(new BigDecimal(0.00d));
          tsd.setRetailTax(new BigDecimal(0.00d));
          tsd.setPurchasingAmount(NumUtil.toLong(nvSku.getValue()));
          tsd.setGiftPrice(new BigDecimal(0.00d));
          tsd.setGiftTaxType(0L);
          tsd.setOrmRowid(tsd.getOrmRowid());
          tsd.setCreatedDatetime(apiOrderHeader.getCreatedDatetime());
          tsd.setCreatedUser("BATCH:0:0:0");
          tsd.setUpdatedDatetime(apiOrderHeader.getUpdatedDatetime());
          tsd.setUpdatedUser("BATCH:0:0:0");
          apiOrderHeader.getShippingHeader().getShippingDetailList().add(tsd);
        }
      }
    }

    // 订单设置成未检查
    if (checkOrderFlg) {
      apiOrderHeader.setOrderFlg(OrderFlg.NOT_CHECKED.longValue());
      apiOrderHeader.setTmallBuyerMessage("警告：赠品库存不足");
      apiOrderHeader.setCaution("警告：赠品库存不足");
      
    }
    
  }

  // 订单下载中封装gift明细end
  
  // 20140326 txw add start
  // 订单下载中封装宣传品明细start
  public void getPropagandaOrderShippingDetail(TmallOrderHeader apiOrderHeader, List<TmallOrderDetail> apiOrderDetailList) throws RuntimeException {
    String customerCode = apiOrderHeader.getCustomerCode();
    List<OrderPropagandaCommodityInfo> propagandaCommodityList = getTmallOrderPropagandaCommodityInfo(apiOrderHeader.getShippingHeader().getPrefectureCode(), customerCode);
      for (OrderPropagandaCommodityInfo info : propagandaCommodityList) {
        // 封装订单明细
        TmallOrderDetail tod = new TmallOrderDetail();
        tod.setOrderNo(apiOrderHeader.getOrderNo());
        tod.setShopCode(apiOrderHeader.getShopCode());
        tod.setSkuCode(info.getCommodityCode());
        tod.setCommodityCode(info.getCommodityCode());
        tod.setCommodityName(info.getCommodityName());
        tod.setPurchasingAmount(info.getPurchasingAmount());
        tod.setUnitPrice(BigDecimal.ZERO);
        tod.setRetailPrice(BigDecimal.ZERO);
        tod.setRetailTax(BigDecimal.ZERO);
        tod.setCommodityTaxType(0L);
        tod.setAppliedPointRate(0L);
        tod.setTmallRefundStatus("NO_REFUND");
        tod.setOrmRowid(tod.getOrmRowid());
        tod.setCreatedDatetime(apiOrderHeader.getCreatedDatetime());
        tod.setCreatedUser("BATCH:0:0:0");
        tod.setUpdatedDatetime(apiOrderHeader.getUpdatedDatetime());
        tod.setUpdatedUser("BATCH:0:0:0");
        apiOrderDetailList.add(tod);
        // 封装发货明细
        TmallShippingDetail tsd = new TmallShippingDetail();
        tsd.setShopCode(apiOrderHeader.getShopCode());
        tsd.setSkuCode(info.getCommodityCode());
        tsd.setUnitPrice(BigDecimal.ZERO);
        tsd.setRetailPrice(BigDecimal.ZERO);
        tsd.setDiscountAmount(BigDecimal.ZERO);
        tsd.setDiscountPrice(BigDecimal.ZERO);
        tsd.setRetailTax(BigDecimal.ZERO);
        tsd.setPurchasingAmount(info.getPurchasingAmount());
        tsd.setGiftPrice(BigDecimal.ZERO);
        tsd.setGiftTaxType(0L);
        tsd.setOrmRowid(tsd.getOrmRowid());
        tsd.setCreatedDatetime(apiOrderHeader.getCreatedDatetime());
        tsd.setCreatedUser("BATCH:0:0:0");
        tsd.setUpdatedDatetime(apiOrderHeader.getUpdatedDatetime());
        tsd.setUpdatedUser("BATCH:0:0:0");
        apiOrderHeader.getShippingHeader().getShippingDetailList().add(tsd);
      }
  }
  // 20140326 txw add end

  /***
   * 根据API和EC侧订单各种状态返回处理参数
   * 
   * @param apiOrderHeader
   * @param ecOrderExist
   * @return int
   */
  public int resultStatusAfterDecideApi(TmallOrderHeader apiOrderHeader, boolean ecOrderExist) {
    TmallOrderHeaderDao orderDao = DIContainer.getDao(TmallOrderHeaderDao.class);
    TmallShippingHeaderDao shippingDao = DIContainer.getDao(TmallShippingHeaderDao.class);
    TmallOrderDetailDao tDetailDao = DIContainer.getDao(TmallOrderDetailDao.class);
    StockDao stockDao = DIContainer.getDao(StockDao.class);
    TmallShippingHeader apiShippingH = apiOrderHeader.getShippingHeader();
    String apiTmallStatus = apiOrderHeader.getTmallStatus().toUpperCase();// api订单淘宝订单状态

    String apiTmallPayMethodType = apiOrderHeader.getPaymentMethodType();// api订单淘宝付款方式

    Long apiTamllPayStaus = apiOrderHeader.getPaymentStatus().longValue();// api订单淘宝付款状态

    Long apiOrderStatus = apiOrderHeader.getOrderStatus().longValue(); // api侧订单状态

    Long apiShippingStatus = apiOrderHeader.getShippingHeader().getShippingStatus().longValue(); // api侧发货状态
    int result = 0;
    if (ecOrderExist) {
      TmallOrderHeader ecOrderHeader = orderDao.loadByTid(apiOrderHeader.getTmallTid()); // 获取EC系统TmallOrderHeader
      TmallShippingHeader ecShippingHeader = shippingDao.loadByOrderNo(ecOrderHeader.getOrderNo()); // 获取EC系统TmallShippingHeader

      String ecTradeStatus = ecOrderHeader.getTmallStatus().toUpperCase();// EC侧订单淘宝订单状态

      String ecTmallPayMethodType = ecOrderHeader.getPaymentMethodType();// ec订单淘宝付款方式

      Long ecTamllPayStaus = ecOrderHeader.getPaymentStatus().longValue();// ec订单淘宝付款状态

      Long ecOrderStatus = ecOrderHeader.getOrderStatus().longValue(); // EC侧订单状态

      Long ecShippingStatus = ecShippingHeader.getShippingStatus().longValue(); // ec侧发货状态
      // 如果EC端该订单已取消，就不需要做特别处理，返回1
      if (ecOrderStatus.toString().equals(OrderStatus.CANCELLED.getValue())) {
        return result = 1;
      }
      // 如果API订单和EC订单的状态都为交易成功则无需做特别处理，返回1
      if ("TRADE_FINISHED".equals(apiTmallStatus) && "TRADE_FINISHED".equals(ecTradeStatus)) {
        return result = 1;
      }
      // API端交易成功且EC端交易未成功时，只修改EC端的交易成功和最终修改时间,返回2
      if ("TRADE_FINISHED".equals(apiTmallStatus) && !"TRADE_FINISHED".equals(ecTradeStatus)) {
        return result = 2;
      }
      // 如果api侧交易关闭并且ec侧已付款并且没有发货，则取消EC订单、修改EC引当，返回3
      if ("TRADE_CLOSED".equals(apiTmallStatus) && ecTamllPayStaus.toString().equals(PaymentStatus.PAID.getValue())
          && !ecShippingStatus.toString().equals(ShippingStatus.IN_PROCESSING.getValue()) && !ecShippingStatus.toString().equals(ShippingStatus.SHIPPED.getValue())) {
        return result = 3;
      }
      // ----------attention:6到11顺序不能改变---------//
      // 判断EC存在订单和TMALL订单是否付款,都已付款返回6
      if (ecTamllPayStaus.toString().equals(PaymentStatus.PAID.getValue()) && (apiTamllPayStaus.toString().equals(PaymentStatus.PAID.getValue()))) {
        return result = 6;
      }
      // 判断TMALL侧订单是否付款,已付款则只修改订单付款状态和时间(并且EC侧是未付款状态),返回7
      if (apiTamllPayStaus.toString().equals(PaymentStatus.PAID.getValue()) && !ecTamllPayStaus.equals(PaymentStatus.PAID.getValue())) {
        return result = 7;
      }
      // 如果EC侧是未付款状态,并且货到付款支付则返回不再修改其他数据,返回8
      if (PaymentMethodType.CASH_ON_DELIVERY.getValue().equals(apiTmallPayMethodType)
          && !ecTamllPayStaus.toString().equals(PaymentStatus.PAID.getValue())) {
        return result = 8;
      }
      // 如果EC侧是未付款状态，apiTMALL状态WAIT_BUYER_CONFIRM_GOODS或者TRADE_CLOSED并且有付款时间,返回9
      if (("WAIT_BUYER_CONFIRM_GOODS".equals(apiTmallStatus) || "TRADE_CLOSED".equals(apiTmallStatus))
          && (apiOrderHeader.getPaymentDate() != null) && !ecTamllPayStaus.toString().equals(PaymentStatus.PAID.getValue())) {
        return result = 9;
      }
      // 如果EC侧和api侧是未付款状态,apiTMALL状态TRADE_NO_CREATE_PAY或者WAIT_BUYER_PAY,返回10
      if (("TRADE_NO_CREATE_PAY".equals(apiTmallStatus) || "WAIT_BUYER_PAY".equals(apiTmallStatus))
          && !(ecTamllPayStaus.toString().equals(PaymentStatus.PAID.getValue()) && apiTamllPayStaus.toString().equals(PaymentStatus.PAID.getValue()))) {
        return result = 10;
      }
      // 如果EC侧和api侧是未付款状态,apiTMALL状态WAIT_SELLER_SEND_GOODS，则修改EC侧订单为付款，发货状态为可发货,返回11
      if ("WAIT_SELLER_SEND_GOODS".equals(apiTmallStatus)
          && !(ecTamllPayStaus.toString().equals(PaymentStatus.PAID.getValue()) && apiTamllPayStaus.toString().equals(PaymentStatus.PAID.getValue()))) {
        return result = 11;
      }
    }
    return result;
  }

  /**
   * 更新T-Mall详细订单商品数量和更新T-Mall在库表引当数
   * 
   * @param apiOrderDetailList
   * @param apiTmallID
   * @param ecOrderNo
   * @param txMgr
   * @param bl
   *          ：是否修改价格
   * @return ServiceResult
   */
  public ServiceResult updateStockAndOrder(List<TmallOrderDetail> apiOrderDetailList, String apiTmallID, String ecOrderNo,
      boolean bl, TransactionManager txMgr) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    StockDao stockDao = DIContainer.getDao(StockDao.class);
    try {
      // 判断商品数量变化
      for (TmallOrderDetail apiOrderDetail : apiOrderDetailList) {
        boolean blexi = stockDao.exists(apiOrderDetail.getShopCode(), apiOrderDetail.getSkuCode());
        if (blexi) {
          // 获取EC系统TMALL详细订单
          Query queryOrderDetail = new SimpleQuery(OrderSearchQuery.TMALL_ORDER_DETAIL_QUERY, apiOrderDetail.getSkuCode(),
              apiTmallID);
          TmallOrderDetail orderDetailEc = DatabaseUtil.loadAsBean(queryOrderDetail, TmallOrderDetail.class);
          if (apiOrderDetail.getPurchasingAmount() == orderDetailEc.getPurchasingAmount()
              && apiOrderDetail.getRetailPrice() == orderDetailEc.getRetailPrice()
              && apiOrderDetail.getUnitPrice() == orderDetailEc.getUnitPrice()) { // 比较价格和单价、数量没有变化、
            // 没变化不作处理
            continue;
          } else { // 商品数量有变化：
            // ※订单商品数量差额=CSV本次订单商品数量-EC上次订单商品数量
            Long increment = apiOrderDetail.getPurchasingAmount() - orderDetailEc.getPurchasingAmount();
            // 发货详细信息
            Query shippingQuery = new SimpleQuery(
                "SELECT  B.* FROM  TMALL_ORDER_DETAIL A,  TMALL_SHIPPING_DETAIL B,  TMALL_SHIPPING_HEADER C WHERE A.ORDER_NO = C.ORDER_NO AND B.SHIPPING_NO = C.SHIPPING_NO AND A.ORDER_NO=? AND B.SHOP_CODE=? AND   B.SKU_CODE  =?",
                orderDetailEc.getOrderNo(), orderDetailEc.getShopCode(), orderDetailEc.getSkuCode());
            TmallShippingDetail shippingDetailEc = DatabaseUtil.loadAsBean(shippingQuery, TmallShippingDetail.class);
            shippingDetailEc.setRetailPrice(orderDetailEc.getRetailPrice());
            shippingDetailEc.setRetailTax(orderDetailEc.getRetailTax());
            shippingDetailEc.setUnitPrice(orderDetailEc.getUnitPrice());
            shippingDetailEc.setPurchasingAmount(orderDetailEc.getPurchasingAmount());
            // 发货信息修改
            txMgr.update(shippingDetailEc);
            
            //组合商品更新 ADD START
            if (apiOrderDetail.getTShDetailC() != null) {
              Query sql = new SimpleQuery("UPDATE TMALL_SHIPPING_DETAIL_COMPOSITION SET PURCHASING_AMOUNT = ? WHERE SHIPPING_NO = ? AND SHIPPING_DETAIL_NO = ? AND PARENT_SKU_CODE = ?"
                  ,apiOrderDetail.getTShDetailC().getPurchasingAmount() ,shippingDetailEc.getShippingNo(), shippingDetailEc.getShippingDetailNo(), shippingDetailEc.getSkuCode());
              // 组合商品数量、发货编号、明细编号、组合商品编号
              // 发货组合商品信息修改
              txMgr.executeUpdate(sql);
            }
            //组合商品更新 ADD END
            
            
            //
            Query orderDetailUpdateQuery = null;
            if (bl) {
              // 1. 更新T-Mall详细订单商品数量,价格
              orderDetailUpdateQuery = new SimpleQuery(OrderSearchQuery.UPDATE_EC_ORDER_DETAIL_ORDER, this.getLoginInfo()
                  .getRecordingFormat(), DatabaseUtil.getSystemDatetime(), apiOrderDetail.getPurchasingAmount(), apiOrderDetail
                  .getRetailPrice(), apiOrderDetail.getUnitPrice(), ecOrderNo, apiOrderDetail.getShopCode(), apiOrderDetail
                  .getSkuCode());
            } else {
              // 1. 更新T-Mall详细订单商品数量
              orderDetailUpdateQuery = new SimpleQuery(OrderSearchQuery.UPDATE_TMALL_ORDER_DETAIL_ORDER, this.getLoginInfo()
                  .getRecordingFormat(), DatabaseUtil.getSystemDatetime(), apiOrderDetail.getPurchasingAmount(), ecOrderNo,
                  apiOrderDetail.getShopCode(), apiOrderDetail.getSkuCode());
            }
            txMgr.executeUpdate(orderDetailUpdateQuery);// 更新详细订单

            // 2.更新T-Mall在库表引当数
            // 2014/06/12 库存更新对应 ob_李 delete start
            // 组合商品
            if (apiOrderDetail.getTShDetailC() != null) {
              Long zongShu = apiOrderDetail.getTShDetailC().getPurchasingAmount();
              Long zuShu = apiOrderDetail.getPurchasingAmount();
              Long increment2 = (zongShu / zuShu) * increment;

              Query stockUpdateQuery1 = new SimpleQuery(OrderServiceQuery.UPDATE_STOCK_ALLOCATED_TMALL2, this.getLoginInfo()
                  .getRecordingFormat(), DatabaseUtil.getSystemDatetime(), increment2, 
                  // 店铺编号、原商品编号
                  apiOrderDetail.getShopCode(), apiOrderDetail.getSkuCode(), apiOrderDetail.getTShDetailC().getChildCommodityCode());
              txMgr.executeUpdate(stockUpdateQuery1); // 更新引当数
              
              // 2014/06/12 库存更新对应 ob_李 delete start
//              Query stockUpdateQuery2 = new SimpleQuery(OrderServiceQuery.UPDATE_STOCK_ALLOCATED_TMALL, this.getLoginInfo()
//                  .getRecordingFormat(), DatabaseUtil.getSystemDatetime(), increment2, 
//                  // 店铺编号、原商品编号
//                  apiOrderDetail.getShopCode(), apiOrderDetail.getTShDetailC().getChildCommodityCode());
//              txMgr.executeUpdate(stockUpdateQuery2); // 更新引当数
              // 2014/06/12 库存更新对应 ob_李 delete end
              
            } else {
              // 2014/06/12 库存更新对应 ob_李 update start
//              Query stockUpdateQuery3 = new SimpleQuery(OrderServiceQuery.UPDATE_STOCK_ALLOCATED_TMALL, this.getLoginInfo()
//                  .getRecordingFormat(), DatabaseUtil.getSystemDatetime(), increment, 
//                  apiOrderDetail.getShopCode(), apiOrderDetail.getSkuCode());
              Query stockUpdateQuery3 = new SimpleQuery(OrderServiceQuery.UPDATE_TMALL_STOCK, this.getLoginInfo()
                  .getRecordingFormat(), DatabaseUtil.getSystemDatetime(), increment, 
                  apiOrderDetail.getShopCode(), apiOrderDetail.getSkuCode());
              // 2014/06/12 库存更新对应 ob_李 update end
              txMgr.executeUpdate(stockUpdateQuery3); // 更新引当数
            }
            // 2014/06/12 库存更新对应 ob_李 delete end
          }
        } else {
          logger.debug(MessageFormat.format("订单交易号:{1}" + Messages.log("service.impl.OrderServiceImpl.22") + ":{0}", apiOrderDetail
              .getSkuCode(), apiTmallID));// 找不到在库表SKU
          StringUtil.sendTmallErrMail(apiTmallID, "在库表不存在此SKU编号。SKU编号：" + apiOrderDetail.getSkuCode());
          serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
          txMgr.rollback();
          return serviceResult;
        }
      }
    } catch (Exception e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  /**
   *更新T-Mall在库表引当数=引当数+CVS订单商品数量
   * 
   * @param apiOrderDetail
   * @param txMgr
   * @return ServiceResult
   */
  public ServiceResult OnlyAddStockAllocated(List<TmallOrderDetail> apiOrderDetailList, String apiTmallId, TransactionManager txMgr) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    try {
      StockDao dao = DIContainer.getDao(StockDao.class);
      for (TmallOrderDetail apiOrderDetail : apiOrderDetailList) {
        // 更新T-Mall在库表引当数=引当数+CVS订单商品数量
         boolean bl = dao.exists(apiOrderDetail.getShopCode(), apiOrderDetail.getSkuCode());
        if (bl) {
          // 组合商品判断
          Long pAmount = apiOrderDetail.getPurchasingAmount();
          if (apiOrderDetail.getTShDetailC() != null) {
            pAmount = apiOrderDetail.getTShDetailC().getPurchasingAmount();
            Query stockUpdateQuery1 = new SimpleQuery(OrderServiceQuery.UPDATE_TMALL_STOCK_ALLOCATED, this.getLoginInfo()
                .getRecordingFormat(), DatabaseUtil.getSystemDatetime(), pAmount, 
                // 店铺编号、 组合商品编号、原商品编号
                apiOrderDetail.getShopCode(), apiOrderDetail.getSkuCode(), apiOrderDetail.getTShDetailC().getChildCommodityCode());
            txMgr.executeUpdate(stockUpdateQuery1); // 更新引当数

            // 2014/06/12 库存更新对应 ob_李 delete start
//            Query stockUpdateQuery2 = new SimpleQuery(OrderServiceQuery.UPDATE_STOCK_ALLOCATED_TMALL, this.getLoginInfo()
//                .getRecordingFormat(), DatabaseUtil.getSystemDatetime(), pAmount, 
//                // 店铺编号、 原商品编号
//                apiOrderDetail.getShopCode(), apiOrderDetail.getTShDetailC().getChildCommodityCode());
//            txMgr.executeUpdate(stockUpdateQuery2); // 更新引当数
            // 2014/06/12 库存更新对应 ob_李 delete end
          } else if (apiOrderDetail.getSuitShDetailList() != null && apiOrderDetail.getSuitShDetailList().size() > 0) {
            // 2014/06/12 库存更新对应 ob_李 delete start
//            for (TmallShippingDetailComposition  suitDetail : apiOrderDetail.getSuitShDetailList()) {
//              Query stockUpdateQuery1 = new SimpleQuery(OrderServiceQuery.UPDATE_SUIT_STOCK, this
//                  .getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), pAmount,
//                  // 店铺编号、明细商品编号
//                  suitDetail.getShopCode(), suitDetail.getChildCommodityCode());
//              txMgr.executeUpdate(stockUpdateQuery1);
//            }
            // 2014/06/12 库存更新对应 ob_李 delete end
            Query stockUpdateQuery2 = new SimpleQuery(OrderServiceQuery.UPDATE_TMALL_SUIT_COMMODITY, this
                .getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), pAmount,
                //套装商品编号
                apiOrderDetail.getSkuCode());
            txMgr.executeUpdate(stockUpdateQuery2);
            
          } else {
//            Query stockUpdateQuery3 = new SimpleQuery(OrderServiceQuery.UPDATE_STOCK_ALLOCATED_TMALL, this.getLoginInfo()
//                .getRecordingFormat(), DatabaseUtil.getSystemDatetime(), pAmount, apiOrderDetail
//                .getShopCode(), apiOrderDetail.getSkuCode());
            Query stockUpdateQuery3 = new SimpleQuery(OrderServiceQuery.UPDATE_TMALL_STOCK, this.getLoginInfo()
                .getRecordingFormat(), DatabaseUtil.getSystemDatetime(), pAmount, apiOrderDetail
                .getShopCode(), apiOrderDetail.getSkuCode());
            txMgr.executeUpdate(stockUpdateQuery3); // 更新引当数
          }
        } else {
          logger.debug(MessageFormat.format("订单交易号:{1}" + Messages.log("service.impl.OrderServiceImpl.22") + ":{0}", apiOrderDetail
              .getSkuCode(), apiTmallId));// 找不到在库表SKU
          StringUtil.sendTmallErrMail(apiTmallId, "更新库存引当失败。SKU编号：" + apiOrderDetail.getSkuCode());
          serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
          txMgr.rollback();
          return serviceResult;
        }
      }
    } catch (Exception e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  /**
   * add by os012 20111230 start 新建登录淘宝订单信息
   * 
   * @param orderHeader
   * @param orderDetails
   * @return ServiceResult
   */
  public ServiceResult InsertTmallOrder(TmallOrderHeader orderHeader, List<TmallOrderDetail> orderDetails, TransactionManager txMgr) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    WebshopConfig config = DIContainer.getWebshopConfig();
    OrderInvoice apiOrderInvoice = new OrderInvoice();
    try {
      Long sDetailCount = 0L;
      Long oDetailCount = 0L;
      Long oDetailCCount = 0L;
      // 1.新建T-Mall订单
      // 获取订单号；
      // edit by os012 20120203 start
      Long orderno = DatabaseUtil.generateSequence(SequenceType.TMALL_ORDER_NO_SEQ);
      orderHeader.setOrderNo("T" + orderno.toString());
      // edit by os012 20120203 end
      setUserStatus(orderHeader);

      // 新建发货主单及发货明细
      // 获取发货号
      // edit by os012 20120203 start
      Long shipno = DatabaseUtil.generateSequence(SequenceType.TMALL_SHIPPING_NO);
      orderHeader.getShippingHeader().setShippingNo("T" + shipno.toString());
      orderHeader.getShippingHeader().setOrderNo("T" + orderno.toString());
      orderHeader.getShippingHeader().setReturnItemType(0L);
      orderHeader.getShippingHeader().setTmallShippingFlg(0L);
      orderHeader.getShippingHeader().setReturnItemLossMoney(BigDecimal.ZERO);
      // 淘宝订单取消时，发货状态改为取消
      if ("TRADE_CLOSED_BY_TAOBAO".equals(orderHeader.getTmallStatus().toUpperCase())
          || "TRADE_CLOSED".equals(orderHeader.getTmallStatus().toUpperCase())
          || (orderHeader.getOrderStatus().longValue() == OrderStatus.CANCELLED.longValue())) {
        orderHeader.setOrderStatus(OrderStatus.CANCELLED.longValue());
        orderHeader.getShippingHeader().setShippingStatus(ShippingStatus.CANCELLED.longValue());// 取消
      }
      // edit by os012 20120203 end

      setUserStatus(orderHeader.getShippingHeader());
      // 添加TmallEC侧订单
      txMgr.insert(orderHeader);
      // 添加TmallEC侧发货单
      txMgr.insert(orderHeader.getShippingHeader());

      // 添加Tmall发票抬头
      Long orderInvoiceNo = DatabaseUtil.generateSequence(SequenceType.ORDER_INVOICE_NO);
      apiOrderInvoice.setCustomerCode("GUESTTM");
      if (StringUtil.isNullOrEmpty(orderHeader.getTmallInvoiceName())) {
        apiOrderInvoice.setCustomerName(orderHeader.getShippingHeader().getAddressLastName());
      } else {
        apiOrderInvoice.setCustomerName(orderHeader.getTmallInvoiceName());
      }
      apiOrderInvoice.setOrderNo("T" + orderno.toString());
      // 20120301 yyq update start
      // apiOrderInvoice.setCommodityName(config.getTmallInvoiceCommodityName());
      List<String> list = config.getInvoiceCommodityNameList();
      apiOrderInvoice.setCommodityName(list.get(3).toString());
      // 20120301 yyq update end
      apiOrderInvoice.setInvoiceType(InvoiceType.USUAL.longValue());
      setUserStatus(apiOrderInvoice);
      // txMgr.insert(apiOrderInvoice);// 添加Tmall发票抬头
      // 2.新建淘宝详细订单和详细发货
      
      for (int i = 0; i < orderDetails.size(); i++) {
        if (orderDetails.get(i).getSkuCode().equals("")) {
          orderDetails.get(i).setSkuCode((oDetailCount++).toString());// 暂时这么处理：没有sku
        }
        // edit by os012 20120203 start
        orderDetails.get(i).setOrderNo("T" + orderno.toString());
        // edit by os012 20120203 end
        setUserStatus(orderDetails.get(i));
        orderHeader.getShippingHeader().getShippingDetailList().get(i).setShippingDetailNo(sDetailCount++);
        orderHeader.getShippingHeader().getShippingDetailList().get(i).setShippingNo("T" + shipno.toString());
        setUserStatus(orderHeader.getShippingHeader().getShippingDetailList().get(i));
        // 添加TmallEC侧详细订单
        txMgr.insert(orderDetails.get(i));
        // 添加TmallEC侧详细发货单
        // 组合商品对应 ADD 20130620 START
        if (orderDetails.get(i).getTShDetailC() != null) {
          orderDetails.get(i).getTShDetailC().setShippingNo("T" + shipno.toString());
          orderDetails.get(i).getTShDetailC().setShippingDetailNo(orderHeader.getShippingHeader().getShippingDetailList().get(i).getShippingDetailNo());
          orderDetails.get(i).getTShDetailC().setCompositionNo(oDetailCCount++);
          txMgr.insert(orderDetails.get(i).getTShDetailC());
        }
        // 组合商品对应 ADD 20130620 END
        
        // 套装商品对应
        if (orderDetails.get(i).getSuitShDetailList() != null && orderDetails.get(i).getSuitShDetailList().size() > 0) {
          for (TmallShippingDetailComposition suitDetail : orderDetails.get(i).getSuitShDetailList() ) {
            suitDetail.setShippingNo("T" + shipno.toString());
            suitDetail.setShippingDetailNo(orderHeader.getShippingHeader().getShippingDetailList().get(i).getShippingDetailNo());
            suitDetail.setCompositionNo(oDetailCCount++);
            txMgr.insert(suitDetail);
          }
        }
        txMgr.insert(orderHeader.getShippingHeader().getShippingDetailList().get(i));
      }
    } catch (DataAccessException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      StringUtil.sendTmallErrMail(orderHeader.getCustomerCode(), "淘宝下载订单插入订单表出错。");
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      StringUtil.sendTmallErrMail(orderHeader.getCustomerCode(), "淘宝下载订单插入订单表出错。");
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  private TmallShippingDetailComposition createTmallShDetailC(TmallShippingDetail tSdetail, CCommodityHeader ccHeader, CCommodityDetail ccDetail) {
    TmallShippingDetailComposition tsDetailC = new TmallShippingDetailComposition();
    tsDetailC.setShippingNo(tSdetail.getShippingNo()); // 发货编号
    tsDetailC.setShippingDetailNo(tSdetail.getShippingDetailNo()); //发货明细编号
    //tsDetailC.setCompositionNo(); //组合明细编号
    tsDetailC.setShopCode(tSdetail.getShopCode()); //店铺编号
    tsDetailC.setParentCommodityCode(tSdetail.getSkuCode()); //组合商品编号
    tsDetailC.setParentSkuCode(tSdetail.getSkuCode()); //组合商品SKU编号
    tsDetailC.setChildCommodityCode(ccHeader.getOriginalCommodityCode()); //组合明细商品编号
    tsDetailC.setChildSkuCode(ccHeader.getOriginalCommodityCode()); //组合明细商品SKU编号
    tsDetailC.setCommodityName(ccHeader.getCommodityName()); //商品名称
    tsDetailC.setStandardDetail1Name(ccDetail.getStandardDetail1Name()); //规格名称1
    tsDetailC.setStandardDetail2Name(ccDetail.getStandardDetail2Name()); //规格名称2
    if (tSdetail.getUnitPrice() != null) {
      tsDetailC.setUnitPrice(BigDecimalUtil.divide(tSdetail.getUnitPrice(), ccHeader.getCombinationAmount(), 2, RoundingMode.UP)); //商品单价
    }
    if (tSdetail.getRetailPrice() != null) {
      tsDetailC.setRetailPrice(BigDecimalUtil.divide(tSdetail.getRetailPrice(), ccHeader.getCombinationAmount(), 2, RoundingMode.UP)); //销售价格
    }
    
    tsDetailC.setPurchasingAmount(tSdetail.getPurchasingAmount() * ccHeader.getCombinationAmount()); //购买数量 = 订购数量 * 组数
    tsDetailC.setRetailTax(BigDecimal.ZERO); //销售消费税金额
    tsDetailC.setCommodityTaxRate(null); //消费税率
    tsDetailC.setCommodityTax(null); //商品消费税金额
    tsDetailC.setCommodityTaxType(0L); //消费税类型
    tsDetailC.setCommodityWeight(ccDetail.getWeight()); //商品重量
    return tsDetailC;
  }
  
  private TmallShippingDetailComposition createSuitDetail(TmallShippingDetail tSdetail, CCommodityHeader ccHeader, CCommodityDetail ccDetail,SetCommodityComposition detail,int size) {
    TmallShippingDetailComposition tsDetailC = new TmallShippingDetailComposition();
    tsDetailC.setShippingNo(tSdetail.getShippingNo()); // 发货编号
    tsDetailC.setShippingDetailNo(tSdetail.getShippingDetailNo()); //发货明细编号
    tsDetailC.setShopCode(tSdetail.getShopCode()); //店铺编号
    tsDetailC.setParentCommodityCode(tSdetail.getSkuCode()); //套装商品编号
    tsDetailC.setParentSkuCode(tSdetail.getSkuCode()); //套装商品SKU编号
    tsDetailC.setChildCommodityCode(detail.getChildCommodityCode()); //组合明细商品编号
    tsDetailC.setChildSkuCode(detail.getChildCommodityCode()); //组合明细商品SKU编号
    tsDetailC.setCommodityName(ccHeader.getCommodityName()); //商品名称
    tsDetailC.setStandardDetail1Name(ccDetail.getStandardDetail1Name()); //规格名称1
    tsDetailC.setStandardDetail2Name(ccDetail.getStandardDetail2Name()); //规格名称2
    if (tSdetail.getUnitPrice() != null) {
      tsDetailC.setUnitPrice(BigDecimalUtil.divide(detail.getTmallRetailPrice(), 1L, 2, RoundingMode.UP)); //商品单价
    }
    if (tSdetail.getRetailPrice() != null) {
      if (BigDecimalUtil.equals(tSdetail.getUnitPrice(), tSdetail.getRetailPrice())) {
        tsDetailC.setRetailPrice(detail.getTmallRetailPrice());
      } else {
//        BigDecimal cheapPrice = BigDecimalUtil.subtract(tSdetail.getUnitPrice(), tSdetail.getRetailPrice());
//        BigDecimal everyCheapPrice = BigDecimalUtil.divide(cheapPrice, size,2,RoundingMode.UP);
//        BigDecimal everyRealPrice = BigDecimalUtil.subtract(detail.getTmallRetailPrice(), everyCheapPrice);
//        tsDetailC.setRetailPrice(BigDecimalUtil.divide(everyRealPrice , 1L, 2, RoundingMode.UP)); //销售价格
        tsDetailC.setRetailPrice(detail.getTmallRetailPrice()); //销售价格
      }
    }
    
    tsDetailC.setPurchasingAmount(tSdetail.getPurchasingAmount()); //购买数量 
    tsDetailC.setRetailTax(BigDecimal.ZERO); //销售消费税金额
    tsDetailC.setCommodityTaxRate(null); //消费税率
    tsDetailC.setCommodityTax(null); //商品消费税金额
    tsDetailC.setCommodityTaxType(0L); //消费税类型
    tsDetailC.setCommodityWeight(ccDetail.getWeight()); //商品重量
    return tsDetailC;
  }
  
  
  /**
   * 取消EC系统存在订单、取消EC系统存在发货单 add by os012 20120209 start
   * 
   * @param orgOrderHeader
   * @param txMgr
   * @return
   */
  public ServiceResult CancelOrderAndShipping(TmallOrderHeader orgOrderHeader, String tmallStatus, TransactionManager txMgr) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    logger.debug(Messages.log("service.impl.OrderServiceImpl.13"));
    try {
      Query orderStatusUpdateQuery = new SimpleQuery(OrderSearchQuery.UPDATE_TMALL_ORDER_HEADER_ORDER_STATUS, OrderStatus.CANCELLED
          .getValue(), tmallStatus, this.getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), orgOrderHeader
          .getOrderNo());
      txMgr.executeUpdate(orderStatusUpdateQuery); // 取消EC订单
      Query shippingUpdateQuery = new SimpleQuery(OrderSearchQuery.UPDATE_TMALL_SHIPPING_HEADER_STATUS, ShippingStatus.CANCELLED
          .getValue(), this.getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), orgOrderHeader.getOrderNo());
      txMgr.executeUpdate(shippingUpdateQuery);// 取消EC发货单
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  /**
   * 修改订单及发货单信息 add by os012 20120209 start
   * 
   * @param orderHeader
   * @param tShippingH
   * @param OrderNo
   * @param txMgr
   * @param paymentStatus
   * @return
   */
  public ServiceResult UpdateOrderPayAndShipping(TmallOrderHeader orderHeader, TmallShippingHeader tShippingH, String OrderNo,
      TransactionManager txMgr, Long paymentStatus) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    try {
      Query paidStatusUpdateQuery = new SimpleQuery(OrderSearchQuery.UPDATE_TMALL_ORDER_HEADER_PAID_STATUS, paymentStatus,
          orderHeader.getPaymentDate(), orderHeader.getTmallCodStatus(), orderHeader.getTmallStatus(), orderHeader
              .getTmallEndTime(), orderHeader.getTmallModifiedTime(), orderHeader.getTmallRealPointFee(), orderHeader
              .getTmallInvoiceName(), orderHeader.getPaidPrice(), this.getLoginInfo().getRecordingFormat(), orderHeader
              .getAdjustFee(), orderHeader.getUsedPoint(), orderHeader.getCommissionFee(), orderHeader.getDiscountPrice(),
          orderHeader.getPointConvertPrice(), orderHeader.getTmallDiscountPrice(), orderHeader.getMjsDiscount(), DatabaseUtil
              .getSystemDatetime(), OrderNo);
      txMgr.executeUpdate(paidStatusUpdateQuery); // 修改订单
      // 修改发货单信息
      Query shippingStatusUpdateQuery = new SimpleQuery(OrderSearchQuery.UPDATE_TMALL_SHIPPING_HEADER_INFO, tShippingH
          .getAddressLastName(), tShippingH.getPostalCode(), tShippingH.getPrefectureCode(), tShippingH.getCityCode(), tShippingH
          .getAreaCode(), tShippingH.getAddress1(), tShippingH.getAddress2(), tShippingH.getAddress3(), tShippingH.getAddress4(),
          tShippingH.getAddressNo(), tShippingH.getPhoneNumber(), tShippingH.getMobileNumber(), tShippingH.getShippingStatus(),
          this.getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), tShippingH.getAddressFirstName(), tShippingH
              .getAddressLastNameKana(), tShippingH.getAddressFirstNameKana(), tShippingH.getDeliveryRemark(), tShippingH
              .getAcquiredPoint(), tShippingH.getDeliverySlipNo(), tShippingH.getShippingCharge(), tShippingH
              .getShippingChargeTaxType(), tShippingH.getShippingChargeTaxRate(), tShippingH.getShippingChargeTax(), tShippingH
              .getDeliveryTypeNo(), tShippingH.getDeliveryTypeName(), tShippingH.getDeliveryAppointedTimeStart(), tShippingH
              .getDeliveryAppointedTimeEnd(), tShippingH.getArrivalDate(), tShippingH.getArrivalTimeStart(), tShippingH
              .getArrivalTimeEnd(), tShippingH.getShippingDirectDate(), tShippingH.getShippingDate(), tShippingH
              .getOriginalShippingNo(), tShippingH.getReturnItemDate(), tShippingH.getReturnItemLossMoney(), tShippingH
              .getReturnItemType(), tShippingH.getDeliveryCompanyNo(), tShippingH.getDeliveryCompanyName(), tShippingH
              .getDeliveryAppointedDate(), OrderNo);
      txMgr.executeUpdate(shippingStatusUpdateQuery);
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  // 20111227 shen add start
  public Long countUsedCouponOrder(String couponCode, String customerCode) {
    Query query = null;
    if (StringUtil.hasValue(customerCode)) {
      query = new SimpleQuery(OrderServiceQuery.COUNT_USED_COUPON_ORDER_BY_CUSTOMER_CODE_QUERY, couponCode, customerCode,
          OrderStatus.CANCELLED.getValue());
    } else {
      query = new SimpleQuery(OrderServiceQuery.COUNT_USED_COUPON_ORDER_QUERY, couponCode, OrderStatus.CANCELLED.getValue());
    }
    return DatabaseUtil.executeScalar(query, Long.class);
  }

  // 20111227 shen add end

  // 20121102 yyq add start
  public Long countUsedCouponFirstOrder(String customerCode) {
    Query query = null;
    if (StringUtil.hasValue(customerCode)) {
      query = new SimpleQuery(OrderServiceQuery.COUNT_USED_COUPON_FIRST_ORDER_QUERY, customerCode);
    }
    return DatabaseUtil.executeScalar(query, Long.class);
  }

  // 20121102 yyq add end

  // soukai add 2011/12/28 ob start
  /**
   * 订单发票情报查询
   */
  public OrderInvoice getOrderInvoice(String orderCode) {
    Query query = new SimpleQuery(OrderServiceQuery.LOAD_ORDER_INVOICE_BY_ORDER_NO, orderCode);
    return DatabaseUtil.loadAsBean(query, OrderInvoice.class);
  }

  /**
   * 订单发票的商品规格情报查询
   */
  public List<OrderDetail> getOrderDetailCommodityList(String orderCode, String shopCode) {
    Query query = new SimpleQuery(OrderServiceQuery.ORDER_DETAIL_COMMODITY_LIST_QUERY, orderCode, shopCode);
    return DatabaseUtil.loadAsBeanList(query, OrderDetail.class);
  }
  
  /**
   * 订单详细的商品重量查询
   */
  public List<OrderDetail> getOrderDetailCommodityListWeight(String orderCode, String shopCode) {
    Query query = new SimpleQuery(OrderServiceQuery.ORDER_DETAIL_COMMODITY_LIST_WEIGHT_QUERY, orderCode, shopCode);
    return DatabaseUtil.loadAsBeanList(query, OrderDetail.class);
  }

  /**
   * 取得商品详细
   */
  public CommodityDetail getCommodityDetail(String commodityCode, String shopCode) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_COMMODITY_DETAIL, commodityCode, shopCode);
    return DatabaseUtil.loadAsBean(query, CommodityDetail.class);
  }
  
  /**
   * 获得淘宝订单发票的商品规格（订单详细的商品情报）
   * 
   * @param 订单code
   * @param 店铺code
   */
  public List<TmallOrderDetail> getTmallOrderDetailCommodityList(String orderCode, String shopCode) {
    Query query = new SimpleQuery(OrderServiceQuery.ORDER_TMALL_DETAIL_COMMODITY_LIST_QUERY, orderCode, shopCode);
    return DatabaseUtil.loadAsBeanList(query, TmallOrderDetail.class);
  }

  // soukai add 2011/12/28 ob end
  // 20111226 os013 add start
  public ServiceResult erpActualDelivery(ShippingHeader shippingHearBean, ShippingRealityDetail shippingRealityDetailBean) {
    Logger logger = Logger.getLogger(this.getClass());
    // 发货数判断
    if (shippingRealityDetailBean.getShippingAmount() > 0) {
      // 订单信息取得
      OrderHeader orderHeader = getOrderHeader(shippingHearBean.getOrderNo());
      // 判断订单存在判断
      if (orderHeader != null) {
        // 订单取消判断
        if (orderHeader.getOrderStatus() != Long.parseLong(OrderStatus.CANCELLED.getValue())) {
          CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
          ShippingHeaderDao sHeaderDao = DIContainer.getDao(ShippingHeaderDao.class);
          // 根据订单号到发货表取得发货编号
          List<ShippingHeader> headerList = sHeaderDao.findByQuery(OrderServiceQuery.SHIPPING_HEADER_LIST_QUERY, orderHeader
              .getOrderNo());
          // 发货编号
          String shippingNo = headerList.get(0).getShippingNo();
          // 取得购入商品数总数
          long purchasingAmount = getShippingDetailPurchasingAmount(shippingNo, shippingRealityDetailBean.getSkuCode());
          // 原发货总数
          long ShippingAmount = getShippingDetailListShippingAmount(shippingNo, shippingRealityDetailBean.getSkuCode());

          // 在庫信息取得
          Stock stock = service.getStock(orderHeader.getShopCode(), shippingRealityDetailBean.getSkuCode());
          // SKU存在检查
          if (stock != null) {
            // 淘宝订单号取得
            String tmallOrderNo = "";
            // tmallOrderNo = orderHeader.getTmallOrderNo();
            // 在庫设定
            Stock stockBean = new Stock();
            // ショップコード
            stockBean.setShopCode(stock.getShopCode());
            // SKUコード
            stockBean.setSkuCode(stock.getSkuCode());
            // 判断淘宝订单号是否为空
            if (StringUtil.hasValue(tmallOrderNo)) {
              // 淘宝订单号不为空
              // TMALL在庫数
              long stockTmall = stock.getStockTmall();
              // TMALL引当数
              long allocatedTmall = stock.getAllocatedTmall();
              // 发货后TMALL在庫数
              stockBean.setStockTmall(stockTmall - shippingRealityDetailBean.getShippingAmount());
              // 发货后TMALL引当数
              stockBean.setAllocatedTmall(allocatedTmall - shippingRealityDetailBean.getShippingAmount());
            } else {
              // 淘宝订单号为空
              // EC在庫数量
              long stockQuantity = stock.getStockQuantity();
              // EC引当数量
              long allocatedQuantity = stock.getAllocatedQuantity();
              // 发货后EC在庫数量
              stockBean.setStockQuantity(stockQuantity - shippingRealityDetailBean.getShippingAmount());
              // 发货后EC引当数量
              stockBean.setAllocatedQuantity(allocatedQuantity - shippingRealityDetailBean.getShippingAmount());
            }
            // 総在庫
            long stockTotal = stock.getStockTotal();
            // 发货后総在庫
            stockBean.setStockTotal(stockTotal - shippingRealityDetailBean.getShippingAmount());

            // 出荷明細List设定
            ShippingRealityDetail shippingRealityDetail = new ShippingRealityDetail();
            shippingRealityDetail.setShippingRealityDetailNo(DatabaseUtil.generateSequence(SequenceType.shipping_detail_list_no));
            // 出荷番号
            // shippingRealityDetail.setShippingNo(shippingNo);
            // ショップコード
            shippingRealityDetail.setShopCode(orderHeader.getShopCode());
            // SKUコード
            shippingRealityDetail.setSkuCode(shippingRealityDetailBean.getSkuCode());
            // 发货数
            shippingRealityDetail.setShippingAmount(shippingRealityDetailBean.getShippingAmount());
            // 宅配便伝票番号
            shippingRealityDetail.setDeliverySlipNo(shippingRealityDetailBean.getDeliverySlipNo());
            // 出荷日
            shippingRealityDetail.setShippingDate(shippingRealityDetailBean.getShippingDate());

            setUserStatus(shippingRealityDetail);

            TransactionManager txMgr = DIContainer.getTransactionManager();
            try {
              txMgr.begin(getLoginInfo());
              // 出荷明細LIST新增
              txMgr.insert(shippingRealityDetail);

              Query query = new SimpleQuery();
              // EC和淘宝订单区分
              if (StringUtil.hasValue(tmallOrderNo)) {
                query = new SimpleQuery(OrderServiceQuery.UPDATE_STOCK_T_MALL_QUERY, stockBean.getStockTmall(), stockBean
                    .getAllocatedTmall(), stockBean.getStockTotal(), stockBean.getShopCode(), stockBean.getSkuCode());
              } else {
                query = new SimpleQuery(OrderServiceQuery.UPDATE_STOCK_EC_QUERY, stockBean.getStockQuantity(), stockBean
                    .getAllocatedQuantity(), stockBean.getStockTotal(), stockBean.getShopCode(), stockBean.getSkuCode());
              }
              // 在库更新
              txMgr.executeUpdate(query);
              ShippingAmount = ShippingAmount + shippingRealityDetailBean.getShippingAmount();
              // 购入商品数总数与发货总数比较
              if (purchasingAmount == ShippingAmount) {
                // 出荷ヘッダ
                ShippingHeader shippingHeader = new ShippingHeader();
                // 出荷番号
                shippingHeader.setShippingNo(shippingNo);
                // 受注番号
                shippingHeader.setOrderNo(shippingHearBean.getOrderNo());
                // 出荷ステータス
                shippingHeader.setShippingStatus(Long.parseLong(ShippingStatusSummary.SHIPPED_ALL.getValue()));
                // 出荷日
                shippingHeader.setShippingDate(shippingHearBean.getShippingDate());
                setUserStatus(shippingHeader);
                // 出荷ヘッダ更新
                query = new SimpleQuery(OrderServiceQuery.UPDATE_SHIPPING_HEADER_QUERY, shippingHeader.getShippingStatus(),
                    shippingHeader.getShippingDate(), shippingHeader.getShippingNo(), shippingHeader.getOrderNo());
                txMgr.executeUpdate(query);
              }
            } catch (ConcurrencyFailureException e) {
              txMgr.rollback();
              throw e;
            } catch (RuntimeException e) {
              txMgr.rollback();
            } finally {
              txMgr.dispose();
            }
          } else {
            // SKU不存在不进行其他处理，写入Log
            logger.error("SKU不存在，fail:  " + shippingRealityDetailBean.getSkuCode());
            StringUtil.sendTmallErrMail(orderHeader.getCustomerCode(), "SKU不存在。SKU编号：" + shippingRealityDetailBean.getSkuCode());
            return null;
          }
        } else {
          // 订单取消不进行其他处理，写入Log
          logger.error("订单已取消，fail:  " + shippingHearBean.getOrderNo());
          return null;
        }
      }
    } else {
      // 发货数为0或null不进行其他处理，写入Log
      logger.error("发货数为，fail:  " + shippingRealityDetailBean.getShippingAmount());
      return null;
    }
    return null;
  }

  /**
   * 購入商品数总数
   */
  private Long getShippingDetailPurchasingAmount(String shippingNo, String skuCode) {
    SimpleQuery query = new SimpleQuery(OrderServiceQuery.SHIPPING_DETAIL_PURCHASING_AMOUNT_QUERY, shippingNo, skuCode);
    Long obj = DatabaseUtil.executeScalar(query, Long.class);
    return obj;
  }

  /**
   * 发货总数
   */
  private Long getShippingDetailListShippingAmount(String shippingNo, String skuCode) {
    SimpleQuery query = new SimpleQuery(OrderServiceQuery.SHIPPING_REALITY_DETAIL_SHIPPING_AMOUNT_QUERY, shippingNo, skuCode);
    Long obj = DatabaseUtil.executeScalar(query, Long.class);
    if (obj == null) {
      obj = 0L;
    }
    return obj;
  }

  // 20111226 os013 add end
  /**
   * add by os012 20120106 start 城市、省份情报查询
   */
  public City getCity(String city, String Distict) {
    Query query = new SimpleQuery(OrderServiceQuery.LOAD_CITY_BY_NAME, city, Distict);
    return DatabaseUtil.loadAsBean(query, City.class);
  }
  
  public JdCity getJdCity(String city, String Distict) {
    Query query = new SimpleQuery(OrderServiceQuery.LOAD_JD_CITY_BY_NAME, city, Distict);
    return DatabaseUtil.loadAsBean(query, JdCity.class);
  }
  
  public JdCity getSpecialJdCity(String city) {
    Query query = new SimpleQuery(OrderServiceQuery.LOAD_SPECIAL_JD_CITY_BY_NAME, city);
    return DatabaseUtil.loadAsBean(query, JdCity.class);
  }
  
  /**
   * add by os012 20120106 start 支付信息获取
   */
  public PaymentMethod GetAlipayInfo(String method, Long flg) {
    Query query = new SimpleQuery(OrderServiceQuery.PAYINFO_BY_METHOD, method, flg);
    return DatabaseUtil.loadAsBean(query, PaymentMethod.class);
  }

  /**
   * add by os012 20120106 start 支付运送公司
   */
  public DeliveryInfo GetDeliveryCopNo(Long cod, String RegionCode, String cityCode, String areaCode, String weight) {
    Query query = new SimpleQuery(OrderServiceQuery.LOAD_DELIVERY_RELATED_INFO, cod, RegionCode, cityCode, areaCode, weight, weight,weight,weight);
    return DatabaseUtil.loadAsBean(query, DeliveryInfo.class);
  }

  public DeliveryInfo GetJdDeliveryCopNo(Long cod, String RegionCode, String cityCode, String areaCode, String weight) {
    Query query = new SimpleQuery(OrderServiceQuery.loadJdDeliveryRelatedInfo(RegionCode,cityCode,areaCode), cod, weight, weight,weight,weight);
    return DatabaseUtil.loadAsBean(query, DeliveryInfo.class);
  }
  
  /**
   * add by os012 20120106 start 获取企划信息
   */
  public Plan GetEcMaster(String commodityCode, Date time) {
    Query query = new SimpleQuery(OrderServiceQuery.PLAN_NAME_BY_TYPE, commodityCode, time);
    return DatabaseUtil.loadAsBean(query, Plan.class);
  }

  /**
   * add by os012 20120109 start 获取区县信息
   * 
   * @param prefectureCode
   * @param cityCode
   * @return
   */
  public Area getArea(String prefectureCode, String cityCode, String district) {
    Query query = new SimpleQuery(OrderServiceQuery.AREA_INFO_BY_CODE, prefectureCode, cityCode, district);
    return DatabaseUtil.loadAsBean(query, Area.class);
  }
  
  public JdArea getJdArea(String prefectureCode, String cityCode, String district) {
    Query query = new SimpleQuery(OrderServiceQuery.JD_AREA_INFO_BY_CODE, prefectureCode, cityCode, district);
    return DatabaseUtil.loadAsBean(query, JdArea.class);
  }

  /**
   * add by os012 20120201 start 获取品牌信息
   * 
   * @param commodityCode
   * @return
   */
  public Brand getBrand(String commodityCode) {
    Query query = new SimpleQuery(OrderServiceQuery.BRAND_INFO_BY_CODE, commodityCode);
    return DatabaseUtil.loadAsBean(query, Brand.class);
  }

  /**
   * 查询batch信息
   */
  public BatchTime getBatchTime() {
    Query query = new SimpleQuery(OrderServiceQuery.BATCH_INFO_QUERY);
    return DatabaseUtil.loadAsBean(query, BatchTime.class);
  }

  /**
   * add by os012 20120109 start 操作batch时间信息
   * 
   * @param date
   * @return
   */
  public ServiceResult OperateBatchTime(Date date, Long status) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    txMgr.begin(getLoginInfo());
    try {
      BatchTime batchTime = getBatchTime();
      if (batchTime == null) {
        // 添加
        BatchTime batchTime1 = new BatchTime();
        batchTime1.setFromTime(date);
        batchTime1.setCreatedDatetime(DateUtil.getSysdate());
        batchTime1.setCreatedUser(this.getLoginInfo().getRecordingFormat());
        batchTime1.setUpdatedDatetime(DateUtil.getSysdate());
        batchTime1.setUpdatedUser(this.getLoginInfo().getRecordingFormat());
        batchTime1.setBatchStatus(status);
        txMgr.insert(batchTime1);
      } else {
        // 更新
        Query query = new SimpleQuery(OrderServiceQuery.BATCH_INFO_UPDATE, date, DateUtil.getSysdate(), getLoginInfo()
            .getRecordingFormat(), status);
        txMgr.executeUpdate(query);
      }
      txMgr.commit();
    } catch (DataAccessException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  /**
   * 订单下载操作 add by os012 20120109 start
   * OrderDownLoadStatus.DOWNLOADING.longValue():下载中
   * OrderDownLoadStatus.CANDOWNLOAD.longValue():可下载
   * 
   * @param startTime
   *          ：可以为空 格式：yyyy/MM/dd hh:mm:ss
   * @param endTime
   *          ：可以为空
   *@param stockAllocate
   *          :必须(是否绑定执行库存再分配)
   * @return ServiceResult
   */
  public int OrderDownLoadCommon(String startTime, String endTime, boolean stockAllocate) {

    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TmallOrdersDownloadHandling tod = new TmallOrdersDownloadHandling();
    int returnInt = 0;
    BatchTime batT = getBatchTime();
    // 判断是否存在batch记录
    if (batT != null) {
      String start = tod.GetDateStart(startTime);
      // String dateAdd1Hour = DateUtil.toDateTimeString(DateUtil.addHour(DateUtil.fromString(start, true), 2));
      Date dateAdd2Hour = DateUtil.addHour(DateUtil.fromString(start, true), 2);
      boolean flag = DateUtil.getSysdate().after(dateAdd2Hour);

      // if (batT.getBatchStatus() == OrderDownLoadStatus.DOWNLOADING.longValue() && DateUtil.isPeriodStringDateTime(start, dateAdd1Hour, DateUtil.getDateTimeString())) {
      
      if (batT.getBatchStatus().toString().equals(OrderDownLoadStatus.DOWNLOADING.longValue().toString())) {
        if(stockAllocate && flag){
          // 更新batch调用时间和状态
          ServiceResult resultStatusOperate = OperateBatchTime(batT.getFromTime(), OrderDownLoadStatus.DOWNLOADING.longValue());
          if (resultStatusOperate.hasError()) {
            logger.debug(resultStatusOperate);
            return returnInt = -1;
          }
        }else{
          logger.debug(Messages.log("service.impl.OrderServiceImpl.25"));// 订单下载中
          serviceResult.addServiceError(OrderServiceErrorContent.ORDER_DOWNLOADING);// 订单下载中
          return returnInt = OrderDownLoadStatus.DOWNLOADING.longValue().intValue();
        }
      } else {
        // 更新batch调用时间和状态
        ServiceResult resultStatusOperate = OperateBatchTime(batT.getFromTime(), OrderDownLoadStatus.DOWNLOADING.longValue());
        if (resultStatusOperate.hasError()) {
          logger.debug(resultStatusOperate);
          return returnInt = -1;
        }
      }
    } else {
      // 添加batch调用时间和状态
      ServiceResult resultStatusOperate = OperateBatchTime(DateUtil.getSysdate(), OrderDownLoadStatus.DOWNLOADING.longValue());
      if (resultStatusOperate.hasError()) {
        logger.debug(resultStatusOperate);
        return returnInt = -1;
      }
    }

    returnInt = tod.DownLoadOrdersByTime(startTime, endTime, stockAllocate);
    return returnInt;
  }

  // 20120110 wjw add start
  /**
   * 检查を設定します。
   */
  public ServiceResult setOrderFlg(String orderNo) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    // validationチェック
    OrderHeader header = new OrderHeader();
    header.setOrderNo(orderNo);
    setUserStatus(header);
    OrderHeader orgOrderHeader = getOrderHeader(orderNo);
    TmallOrderHeader tmallOrgOrderHeader = getTmallOrderHeader(orderNo);
    // EC
    if (orgOrderHeader != null) {
      orgOrderHeader.setOrderFlg(OrderFlg.CHECKED.longValue());
      TransactionManager txMgr = DIContainer.getTransactionManager();
      try {
        txMgr.begin(this.getLoginInfo());
        setUserStatus(orgOrderHeader);
        txMgr.update(orgOrderHeader);
        txMgr.commit();

      } catch (ConcurrencyFailureException e) {
        txMgr.rollback();
        throw e;
      } catch (RuntimeException e) {
        txMgr.rollback();
        serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      } finally {
        txMgr.dispose();
      }
      // TMALL
    } else if(tmallOrgOrderHeader != null){
      if (tmallOrgOrderHeader == null) {
        serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
        return serviceResult;
      }
      tmallOrgOrderHeader.setOrderFlg(OrderFlg.CHECKED.longValue());
      TransactionManager txMgr = DIContainer.getTransactionManager();
      try {
        txMgr.begin(this.getLoginInfo());
        setUserStatus(tmallOrgOrderHeader);
        txMgr.update(tmallOrgOrderHeader);
        txMgr.commit();

      } catch (ConcurrencyFailureException e) {
        txMgr.rollback();
        throw e;
      } catch (RuntimeException e) {
        txMgr.rollback();
        serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      } finally {
        txMgr.dispose();
      }
    // JD
    } else {
      JdOrderHeader jdOrgOrderHeader = getJdOrderHeader(orderNo);
      if (jdOrgOrderHeader == null) {
        serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
        return serviceResult;
      }
      jdOrgOrderHeader.setOrderFlg(OrderFlg.CHECKED.longValue());
      TransactionManager txMgr = DIContainer.getTransactionManager();
      try {
        txMgr.begin(this.getLoginInfo());
        setUserStatus(jdOrgOrderHeader);
        txMgr.update(jdOrgOrderHeader);
        txMgr.commit();

      } catch (ConcurrencyFailureException e) {
        txMgr.rollback();
        throw e;
      } catch (RuntimeException e) {
        txMgr.rollback();
        serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      } finally {
        txMgr.dispose();
      }
    }
   
    
    return serviceResult;
  }

  // 20120110 wjw add end
  // soukai add 2012/01/10 ob start
  public ServiceResult checkDiscountInfo(String customerCode, String discountType, String discountCode,
      String detailCode, BigDecimal commodityPrice, List<CommodityOfCartInfo> commodityListOfCart) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    if (CouponType.CUSTOMER_GROUP.getValue().equals(discountType)) {
      // 顾客组别优惠时，顾客组别的存在check
      CustomerGroupCampaignDao dao = DIContainer.getDao(CustomerGroupCampaignDao.class);
      List<CustomerGroupCampaign> campaign = dao.findByQuery(OrderServiceQuery.GET_CUSTOMER_GOURP_CAMPAIGN,
          discountCode);
      if (campaign == null || campaign.size() == 0) {
        serviceResult.addServiceError(OrderServiceErrorContent.DISCOUNT_INFO_NOT_EXITS_ERROR);
        return serviceResult;
      }
      if (DateUtil.getSysdate().after(campaign.get(0).getCampaignEndDatetime())
          || DateUtil.getSysdate().before(campaign.get(0).getCampaignStartDatetime())) {
        serviceResult.addServiceError(OrderServiceErrorContent.DISCOUNT_INFO_NOT_EXITS_ERROR);
        return serviceResult;
      }
      // 顾客组别优惠的最小购买金额check
      if (commodityPrice.compareTo(campaign.get(0).getMinOrderAmount()) < 0) {
        serviceResult.addServiceError(OrderServiceErrorContent.DISCOUNT_MIN_ORDER_PRICE_ERROR);
        return serviceResult;
      }
    } else if (CouponType.COMMON_DISTRIBUTION.getValue().equals(discountType)) {
      // 公共优惠券时，优惠券的存在check
      NewCouponRuleDao couponDao = DIContainer.getDao(NewCouponRuleDao.class);
      NewCouponRule coupon = couponDao.load(discountCode);
      if (coupon == null) {
        serviceResult.addServiceError(OrderServiceErrorContent.DISCOUNT_INFO_NOT_EXITS_ERROR);
        return serviceResult;
      }
      // 公共优惠券的使用回数check
      if (DateUtil.getSysdate().after(coupon.getMinUseEndDatetime())
          || DateUtil.getSysdate().before(coupon.getMinUseStartDatetime())) {
        serviceResult.addServiceError(OrderServiceErrorContent.DISCOUNT_INFO_NOT_EXITS_ERROR);
        return serviceResult;
      }
      if (NumUtil.toLong(coupon.getSiteUseLimit().toString()) > 0L) {
        Long siteUseCount = countUsedCouponOrder(coupon.getCouponCode(), null);
        if (NumUtil.toLong(coupon.getSiteUseLimit().toString()) <= siteUseCount) {
          serviceResult.addServiceError(OrderServiceErrorContent.DISCOUNT_COUNT_OVER_ERROR);
          return serviceResult;
        }
      }
      if (NumUtil.toLong(coupon.getPersonalUseLimit().toString()) > 0L) {
        Long customerUseCount = countUsedCouponOrder(coupon.getCouponCode(), customerCode);
        if (NumUtil.toLong(coupon.getPersonalUseLimit().toString()) <= customerUseCount) {
          serviceResult.addServiceError(OrderServiceErrorContent.DISCOUNT_COUNT_OVER_ERROR);
          return serviceResult;
        }
      }
      // 公共优惠券时，最小购买金额check
      if (commodityPrice.compareTo(coupon.getMinUseOrderAmount()) < 0) {
        serviceResult.addServiceError(OrderServiceErrorContent.DISCOUNT_MIN_ORDER_PRICE_ERROR);
        return serviceResult;
      }

      // 自己发行的Public优惠券不可使用
      CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
      if (communicationService.issuedBySelf(discountCode, customerCode)) {
        serviceResult.addServiceError(OrderServiceErrorContent.DISCOUNT_COUNT_ISSUE_BY_MYSELF_ERROR);
        return serviceResult;
      }
    } else {
      // 个人优惠券时,优惠券的存在check

      // 2013/04/18 优惠券对应 ob update start
//      NewCouponHistoryDao couponHistoryDao = DIContainer.getDao(NewCouponHistoryDao.class);
//      NewCouponHistory couponHistory = couponHistoryDao.load(detailCode);
//      if (couponHistory == null) {
//        serviceResult.addServiceError(OrderServiceErrorContent.DISCOUNT_INFO_NOT_EXITS_ERROR);
//        return serviceResult;
//      }
//      // 个人优惠券的使用期间check
//      if (DateUtil.getSysdate().after(couponHistory.getUseEndDatetime())
//          || DateUtil.getSysdate().before(couponHistory.getUseStartDatetime())) {
//        serviceResult.addServiceError(OrderServiceErrorContent.DISCOUNT_INFO_NOT_EXITS_ERROR);
//        return serviceResult;
//      }
//      // 个人优惠券的已使用check
//      if (UseStatus.USED.longValue().equals(couponHistory.getUseStatus())) {
//        serviceResult.addServiceError(OrderServiceErrorContent.DISCOUNT_COUPON_USED_ERROR);
//        return serviceResult;
//      }
//      // 个人优惠券的最小购买金额check
//      if (commodityPrice.compareTo(couponHistory.getMinUseOrderAmount()) < 0) {
//        serviceResult.addServiceError(OrderServiceErrorContent.DISCOUNT_MIN_ORDER_PRICE_ERROR);
//        return serviceResult;
//      }
      
      // 已选择个人优惠券信息取得
      MyCouponInfo aviableCouponSelected = getAviableCouponInfo(detailCode, customerCode, commodityListOfCart);

      if (aviableCouponSelected == null || StringUtil.isNullOrEmpty(aviableCouponSelected.getCouponIssueNo())) {
        serviceResult.addServiceError(OrderServiceErrorContent.DISCOUNT_INFO_NOT_EXITS_ERROR);
        return serviceResult;
      }
      // 2013/04/18 优惠券对应 ob update end
    }

    return serviceResult;
  }
  
  // 2013/04/18 优惠券对应 ob add start
  public MyCouponInfo getAviableCouponInfo(String couponIssueNo, String customerCode, List<CommodityOfCartInfo> commodityListOfCart) {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());

    MyCouponInfo newCouponHistory = service.getMyCoupon(customerCode, couponIssueNo);
    MyCouponInfo result = new MyCouponInfo();

    List<String> objectCommodity = new ArrayList<String>();

    BigDecimal objectTotalPrice = BigDecimal.ZERO;
    BigDecimal objectTotalPriceTemp = BigDecimal.ZERO;

    objectCommodity = getObjectCommodity(newCouponHistory, commodityListOfCart);

    // 指定商品的购买金额取得
    if (objectCommodity.size() > 0) {
      for (String commodityCode : objectCommodity) {
        for (CommodityOfCartInfo commodity : commodityListOfCart) {
          if (commodityCode.equals(commodity.getCommodityCode())) {
            objectTotalPrice = BigDecimalUtil.add(objectTotalPrice, BigDecimalUtil.multiply(commodity.getRetailPrice(),
                commodity.getPurchasingAmount()));
          }
        }
      }
    }

    objectTotalPriceTemp = objectTotalPrice;

    // 优惠前后标志
//    if (BeforeAfterDiscountType.AFTERDISCOUNT.longValue().equals(newCouponHistory.getBeforeAfterDiscountType())) {
//      BigDecimal couponAmount = BigDecimal.ZERO;
//      if (CouponIssueType.FIXED.longValue().equals(newCouponHistory.getCouponIssueType())) {
//        if (newCouponHistory.getCouponAmount() != null) {
//          couponAmount = newCouponHistory.getCouponAmount();
//        }
//      } else {
//        couponAmount = BigDecimalUtil.divide(BigDecimalUtil.multiply(objectTotalPrice, BigDecimalUtil.tempFormatLong(
//            newCouponHistory.getCouponProportion(), BigDecimal.ZERO)), 100, 2, RoundingMode.HALF_UP);
//      }
//      objectTotalPrice = BigDecimalUtil.subtract(objectTotalPrice, couponAmount);
//    }

    // 优惠券最小购买金额
    if (BigDecimalUtil.isAboveOrEquals(objectTotalPrice, newCouponHistory.getMinUseOrderAmount())) {
      newCouponHistory.setObjectTotalPrice(objectTotalPriceTemp);
      result = newCouponHistory;
    }

    return result;
  }

  /**
   * 购物车中满足优惠券使用规则（商品编号或品牌编号或商品区分品）的商品一览设定
   * 
   * @param newCouponHistory
   *          优惠券发行履历信息
   * @param objectCommodity
   *          购物车中满足优惠券使用规则的商品一览
   */
  private List<String> getObjectCommodity(MyCouponInfo newCouponHistory, List<CommodityOfCartInfo> commodityListOfCart) {
    CatalogService cs = ServiceLocator.getCatalogService(getLoginInfo());
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    List<String> commodityCodeOfCart = new ArrayList<String>();
    List<String> objectCommodity = new ArrayList<String>();

    List<NewCouponHistoryUseInfo> useCommodityList = service.getUseCommodityList(newCouponHistory.getCouponIssueNo());
    List<NewCouponHistoryUseInfo> brandList = service.getBrandCodeList(newCouponHistory.getCouponIssueNo());
    List<NewCouponHistoryUseInfo> importCommodityTypeList = service.getImportCommodityTypeList(newCouponHistory.getCouponIssueNo());
    // 20131010 txw add start
    List<NewCouponHistoryUseInfo> categoryList = service.getCategoryCodeList(newCouponHistory.getCouponIssueNo());
    // 20131010 txw add end

    List<String> useCommodityCodeList = new ArrayList<String>();
    for (NewCouponHistoryUseInfo useCommodity : useCommodityList) {
      useCommodityCodeList.add(useCommodity.getCommodityCode());
    }
    
    List<String> brandCodeList = new ArrayList<String>();
    for (NewCouponHistoryUseInfo brand : brandList) {
      brandCodeList.add(brand.getBrandCode());
    }
    
    // 20131010 txw add start
    List<String> categoryCodeList = new ArrayList<String>();
    for (NewCouponHistoryUseInfo category : categoryList) {
      categoryCodeList.add(category.getCategoryCode());
    }
    // 20131010 txw add end
    
    List<Long> importCommodityTypeCodeList = new ArrayList<Long>();
    for (NewCouponHistoryUseInfo importCommodityType : importCommodityTypeList) {
      importCommodityTypeCodeList.add(importCommodityType.getImportCommodityType());
    }
    
    for (CommodityOfCartInfo ch : commodityListOfCart) {

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

      // 20131010 txw add start
      // 使用关联信息(分类编号)
      if (categoryCodeList != null && categoryCodeList.size() > 0) {
        CommodityHeader commodityHeader = cs.getCommodityHeader(DIContainer.getWebshopConfig().getSiteShopCode(), ch.getCommodityCode());
        String categoryPath = commodityHeader.getCategoryPath();
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
          if (categoryCodeList.contains(categoryCode)) {
            existCategory = true;
            break;
          }
        }
        if (!objectCommodity.contains(ch.getCommodityCode()) && existCategory) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }
      // 20131010 txw add end

      commodityCodeOfCart.add(ch.getCommodityCode());
    }

//    if (!RelatedCommodityFlg.HAVE.longValue().equals(newCouponHistory.getRelatedCommodityFlg())) {
//      if (objectCommodity == null || objectCommodity.size() < 1) {
//        objectCommodity = commodityCodeOfCart;
//      }
//    }
    if (useCommodityCodeList.size() < 1 && brandCodeList.size() < 1 && importCommodityTypeCodeList.size() < 1) {
      objectCommodity = commodityCodeOfCart;
    }
    return objectCommodity;

  }
  // 2013/04/18 优惠券对应 ob add end

  // soukai add 2012/01/10 ob end

  // 20120130 ysy add start
  public List<CodeAttribute> getDeliveryShipNoSum(String shippingNo) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_DILIVERY_SLIP_NO_SUM, shippingNo);
    List<NameValue> result = DatabaseUtil.loadAsBeanList(query, NameValue.class);
    return new ArrayList<CodeAttribute>(result);
  }

  // 20120130 ysy add end// 20120202 ysy add start

  public List<CodeAttribute> getTmallDeliveryShipNoSum(String shippingNo) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_TMALL_DILIVERY_SLIP_NO_SUM, shippingNo);
    List<NameValue> result = DatabaseUtil.loadAsBeanList(query, NameValue.class);
    return new ArrayList<CodeAttribute>(result);
  }

  public List<CodeAttribute> getJdDeliveryShipNoSum(String shippingNo) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_JD_DILIVERY_SLIP_NO_SUM, shippingNo);
    List<NameValue> result = DatabaseUtil.loadAsBeanList(query, NameValue.class);
    return new ArrayList<CodeAttribute>(result);
  }
  
  public String getDeliveryShipNoArray(String shippingNo) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_DILIVERY_SLIP_NO_ARRAY, shippingNo);
    Object o = DatabaseUtil.executeScalar(query);
    if (o == null) {
      return null;
    } else {
      String result = o.toString();
      return result;
    }
  }

  public OrderDetail getReturnOrderDetail(String originalShippingNo, String shopCode, String skuCode) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_RETURN_ORDER_DETAIL, originalShippingNo, shopCode, skuCode);
    return DatabaseUtil.loadAsBean(query, OrderDetail.class);
  }

  // 20120202 ysy add end

  // soukai add 2012/02/04 ob start
  public List<OrderHeader> getOrderHeaderByPaymentStatus() {
    return DatabaseUtil.loadAsBeanList(new SimpleQuery(OrderServiceQuery.GET_ORDER_HEADER_BY_PAYMENT_STATUS, new Object[] {}),
        OrderHeader.class);
  }

  public ServiceResult trialsetCancel(OrderIdentifier orderIdentifier) {
    Logger logger = Logger.getLogger(this.getClass());
    String orderNo = orderIdentifier.getOrderNo();
    ServiceResultImpl result = new ServiceResultImpl();

    // 闔ｷ取OrderHeader信息
    OrderHeaderDao dao = DIContainer.getDao(OrderHeaderDao.class);
    OrderHeader orgOrderHeader = dao.load(orderNo);

    if (orgOrderHeader == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    // 闔ｷ取OrderDetail信息
    OrderDetailDao oDetailDao = (OrderDetailDao) DIContainer.getDao(OrderDetailDao.class);
    List<OrderDetail> orgOrderDetails = oDetailDao.findByQuery(OrderServiceQuery.ORDER_DETAIL_LIST_QUERY, orderNo);

    if (orgOrderDetails == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    // 未出荷チェック
    OrderSummary orderSummary = getOrderSummary(orderNo);

    if (!orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())) {
      result.addServiceError(OrderServiceErrorContent.ORDER_CANCEL_ERROR);
      return result;
    }

    List<StockUnit> stockUnitList = new ArrayList<StockUnit>();
//    for (OrderDetail od : orgOrderDetails) {
//      StockUnit stockUnit = new StockUnit();
//      stockUnit.setShopCode(od.getShopCode());
//      stockUnit.setSkuCode(od.getSkuCode());
//      stockUnit.setQuantity(od.getPurchasingAmount().intValue());
//      stockUnit.setLoginInfo(this.getLoginInfo());
//      stockUnitList.add(stockUnit);
//    }
    
    
    List<String> skuList = new ArrayList<String>();
    OrderContainer orgOrderContainer = this.getOrder(orderNo);
    for (ShippingContainer shippingContainer : orgOrderContainer.getShippings()) {
      for (ShippingDetail shippingDetail : shippingContainer.getShippingDetails()) {
        if (SetCommodityFlg.OBJECTIN.longValue().equals(shippingDetail.getSetCommodityFlg())) {
          List<ShippingDetailComposition> compositionList = shippingContainer.getShippingDetailContainerMap().get(shippingDetail);
          for (ShippingDetailComposition composition : compositionList) {
            if (skuList.contains(composition.getChildSkuCode())) {
              for (StockUnit orgStock : stockUnitList) {
                if (orgStock.getSkuCode().equals(composition.getChildSkuCode())) {
                  orgStock.setQuantity(orgStock.getQuantity() + composition.getPurchasingAmount().intValue());
                }
              }
            } else {
              skuList.add(composition.getChildSkuCode());
              StockUnit stockUnit = new StockUnit();
              stockUnit.setShopCode(shippingDetail.getShopCode());
              stockUnit.setSkuCode(composition.getChildSkuCode());
              stockUnit.setQuantity(composition.getPurchasingAmount().intValue());
              stockUnit.setLoginInfo(this.getLoginInfo());
              stockUnitList.add(stockUnit);
            }
          }
        } else {
          if (skuList.contains(shippingDetail.getSkuCode())) {
            for (StockUnit orgStock : stockUnitList) {
              if (orgStock.getSkuCode().equals(shippingDetail.getSkuCode())) {
                orgStock.setQuantity(orgStock.getQuantity() + shippingDetail.getPurchasingAmount().intValue());
              }
            }
          } else {
            skuList.add(shippingDetail.getSkuCode());
            StockUnit stockUnit = new StockUnit();
            stockUnit.setShopCode(shippingDetail.getShopCode());
            stockUnit.setSkuCode(shippingDetail.getSkuCode());
            stockUnit.setQuantity(shippingDetail.getPurchasingAmount().intValue());
            stockUnit.setLoginInfo(this.getLoginInfo());
            stockUnitList.add(stockUnit);
          }
        }
      }
    }
    Collections.sort(stockUnitList);

    TransactionManager manager = DIContainer.getTransactionManager();

    try {

      manager.begin(this.getLoginInfo());
      Long orderStatus = orgOrderHeader.getOrderStatus();

      orgOrderHeader.setOrderStatus(OrderStatus.CANCELLED.longValue());
      orgOrderHeader.setUpdatedDatetime(orderIdentifier.getUpdatedDatetime());
      manager.update(orgOrderHeader);

      CustomerCardUseInfoDao useInfoDao = DIContainer.getDao(CustomerCardUseInfoDao.class);
      List<CustomerCardUseInfo> useInfoList = useInfoDao.loadByOrderNo(orderNo);

      if (useInfoList != null && useInfoList.size() > 0 ) {
        for (CustomerCardUseInfo useInfo : useInfoList) {
          useInfo.setUseStatus(1L);
          manager.update(useInfo);
        }
      }

      List<CustomerCoupon> ccl = getOrderUsedCoupon(orderNo);
      for (CustomerCoupon cc : ccl) {
        cc.setUseDate(null);
        cc.setUseFlg(CouponUsedFlg.ENABLED.longValue());
        cc.setOrderNo("");
        manager.update(cc);
      }

      CustomerCoupon cc = getOldGetCoupon(orderNo);
      if (cc != null) {
        cc.setUseFlg(CouponUsedFlg.DISABLED.longValue());
        manager.update(cc);
      }
      // update: ポイント履歴（該当受注のポイント履歴を無効化）

      PointHistoryDao pointHistoryDao = DIContainer.getDao(PointHistoryDao.class);
      List<PointHistory> pointHistoryList = pointHistoryDao.findByQuery(OrderServiceQuery.GET_POINT_HISTORY_BY_ORDER_NO, orderNo);
      for (PointHistory pointHistory : pointHistoryList) {
        pointHistory.setPointIssueStatus(NumUtil.toLong(PointIssueStatus.DISABLED.getValue()));
        pointHistory.setDescription(Messages.getString("service.impl.OrderServiceImpl.0"));
        PointUpdateProcedure pointUpdate = new PointUpdateProcedure(pointHistory);
        manager.executeProcedure(pointUpdate);
      }

      StockManager stockManager = manager.getStockManager();

      boolean successUpdateStock = false;
      if (orderStatus.equals(OrderStatus.ORDERED.longValue())) {
        successUpdateStock = stockManager.deallocate(stockUnitList);

      } else if (orderStatus.equals(OrderStatus.RESERVED.longValue())) {
        successUpdateStock = stockManager.cancelReserving(stockUnitList);
      } else if (orderStatus.equals(OrderStatus.PHANTOM_ORDER.longValue())) {
        successUpdateStock = stockManager.deallocate(stockUnitList);
      } else if (orderStatus.equals(OrderStatus.PHANTOM_RESERVATION.longValue())) {
        successUpdateStock = stockManager.cancelReserving(stockUnitList);
      } else if (orderStatus.equals(OrderStatus.CANCELLED.longValue())) {
        result.addServiceError(OrderServiceErrorContent.ALREADY_CANCELED_ERROR);
        manager.rollback();
        return result;

      } else {
        result.addServiceError(OrderServiceErrorContent.ORDER_CANCEL_ERROR);
        manager.rollback();
        return result;
      }

      if (!successUpdateStock) {
        manager.rollback();
        result.addServiceError(OrderServiceErrorContent.ORDER_CANCEL_ERROR);
        return result;
      }

      for (ShippingContainer shippingContainer : getShippingList(orderNo)) {
        shippingContainer.getShippingHeader().setShippingStatus(ShippingStatus.CANCELLED.longValue());
        manager.update(shippingContainer.getShippingHeader());
      }

      // 支払方法情報を生成

      CashierPayment cashierPayment = new CashierPayment();
      cashierPayment.setShopCode(orgOrderHeader.getShopCode());
      CashierPaymentTypeBase cashierPaymentType = new CashierPaymentTypeBase();
      cashierPaymentType.setPaymentMethodCode(Long.toString(orgOrderHeader.getPaymentMethodNo()));
      cashierPaymentType.setPaymentMethodName(orgOrderHeader.getPaymentMethodName());
      cashierPaymentType.setPaymentMethodType(orgOrderHeader.getPaymentMethodType());
      cashierPaymentType.setPaymentCommission(orgOrderHeader.getPaymentCommission());
      cashierPayment.setSelectPayment(cashierPaymentType);

      // 与信キャンセル

      result = cancelPayment(getOrder(orderNo));
      if (result.hasError()) {
        manager.rollback();
        return result;
      }

      // soukai add 2012/02/04 ob start
      // 使用优惠券的取消
      SimpleQuery query = new SimpleQuery(OrderServiceQuery.CANCEL_COUPON_USED, orderNo);
      manager.executeUpdate(query);
      
      // 2013/04/13 优惠券对应 ob update start
      // 发行优惠券取消
      // CouponReIssueProcedure couponIssue = new CouponReIssueProcedure(orgOrderHeader.getOrderNo(), orgOrderHeader.getUpdatedUser());
      CouponCancelIssueProcedure couponIssue = new CouponCancelIssueProcedure(orgOrderHeader.getOrderNo(), orgOrderHeader.getUpdatedUser(), orgOrderHeader.getCustomerCode());
      // 2013/04/13 优惠券对应 ob update end
      
      manager.executeProcedure(couponIssue);
      // soukai add 2012/02/04 ob end
      
      manager.commit();

       performOrderEvent(new OrderEvent(orgOrderHeader.getOrderNo(),
       cashierPayment), OrderEventType.CANCELLED);

    } catch (ConcurrencyFailureException e) {
      logger.error(e);
      manager.rollback();
      throw e;
    } catch (RuntimeException e) {
      manager.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }

    return result;
  }

  // soukai add 2012/02/04 ob end

  // 20120224 yyq add start
  public List<TmallShippingHeader> getTmallShippingHeaderList() {
    TmallShippingHeaderDao sHeaderDao = DIContainer.getDao(TmallShippingHeaderDao.class);
    return sHeaderDao.findByQuery(OrderServiceQuery.TMALL_SHIPPING_HEADER_LIST_GET);
  }

  // 20120224 yyq add end
  // 20120224 os011 add start
  public PaymentMethod getAlipayPayMethodInfo() {
    // 支付宝支付用信息取得
    Query shippingGetquery = new SimpleQuery(OrderServiceQuery.GET_ALIPAY_PAYMETHOD_INFO);
    PaymentMethod PaymentMethod = DatabaseUtil.loadAsBean(shippingGetquery, PaymentMethod.class);
    return PaymentMethod;
  }

  // 20120224 os011 add end20120831

  @Override
  public DeliveryCompany GetDeliveryCompany() {
    Query query = new SimpleQuery(OrderServiceQuery.LOAD_DELIVERY_COMPANY);
    return DatabaseUtil.loadAsBean(query, DeliveryCompany.class);
  }

  // 20120628 shen add start
  public List<OrderHeader> getCustomerUsePaymentMethodList(String customerCode) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_CUSTOMER_USE_PAYMENT_METHOD_QUERY, customerCode, OrderStatus.ORDERED
        .getValue());
    return DatabaseUtil.loadAsBeanList(query, OrderHeader.class);
  }

  // 20120628 shen add end

  // 20120827 yyq add start
  public List<NewCouponRule> getCouponLimitOrderCheck(String couponCode, String mobileNumber, String addresslastName, String address) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_COUPON_LIMIT_ORDER_CHECK, couponCode, couponCode, mobileNumber,
        addresslastName, address);
    return DatabaseUtil.loadAsBeanList(query, NewCouponRule.class);
  }

  // 20120827 yyq add end

  // 20120831 yyq add start
  public List<NewCouponRule> getCouponLimitNewOrderCheck(String couponCode, String mobileNumber, String phoneNumber,
      String nameAndAddress) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_COUPON_LIMIT_NEW_ORDER_CHECK, couponCode, couponCode, mobileNumber,
        phoneNumber, nameAndAddress);
    return DatabaseUtil.loadAsBeanList(query, NewCouponRule.class);
  }

  // 20120831 yyq add end

  // 20121024 yyq add start
  @Override
  public int TmallOrderDownLoadNums(String startTime, String endTime) {

    int result = 1;
    TmallService service = ServiceLocator.getTmallService(this.getLoginInfo());
    List<String> tids = service.searchOrderDownLoadNums(startTime, endTime);
    TmallOrderHeaderDao toDao = DIContainer.getDao(TmallOrderHeaderDao.class);
    List<String> orderNums = new ArrayList<String>();

    try {
      if (tids != null && tids.size() > 0) {
        for (int i = 0; i < tids.size(); i++) {
          boolean flag = toDao.existsTid(tids.get(i));
          if (!flag) {
            orderNums.add(tids.get(i));
          }
        }
      }
      if (orderNums != null && orderNums.size() > 0) {
        MailInfo mailInfo = new MailInfo();
        StringBuffer sb = new StringBuffer();
        sb.append("以下为今日下载遗漏淘宝交易号:<BR><BR>");
        for (int i = 0; i < orderNums.size(); i++) {
          sb.append(orderNums.get(i) + "<BR>");
        }
        mailInfo.setText(sb.toString());
        mailInfo.setSubject("【品店】今日下载遗漏淘宝交易号");
        mailInfo.setSendDate(DateUtil.getSysdate());

        TmallSendMailConfig tmllMailSend = DIContainer.get(TmallSendMailConfig.class.getSimpleName());
        mailInfo.setFromInfo(tmllMailSend.getMailFromAddr(), tmllMailSend.getMailFromName());
        String[] mailToAddrArray = tmllMailSend.getMailToAddr().split(";");
        String[] mailToNameArray = tmllMailSend.getMailToName().split(";");
        for (int i = 0; i < mailToAddrArray.length; i++) {
          if (i >= mailToNameArray.length) {
            mailInfo.addToList(mailToAddrArray[i], mailToAddrArray[i]);
          } else {
            mailInfo.addToList(mailToAddrArray[i], mailToNameArray[i]);
          }
        }
        mailInfo.setContentType(MailInfo.CONTENT_TYPE_HTML);
        MailingService svc = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
        svc.sendImmediate(mailInfo);
      }
    } catch (Exception e) {
      result = 0;
      e.printStackTrace();
    }
    return result;
  }

  // 20121024 yyq add end
  // 2012/11/28 促销活动 ob add start
  public Long getCampaignDiscountUsedCount(String campaignCode, String orderNo) {
    Query query = null;
    query = new SimpleQuery(OrderServiceQuery.CAMPAIGN_DISCOUNT_USED_QUERY, campaignCode, orderNo);
    return DatabaseUtil.executeScalar(query, Long.class);
  }

  // 2012/11/28 促销活动 ob add end

  @Override
  public TmallOrderDetail getTmallOrderDetailByType(String orderNo, String skuCode) {
    SimpleQuery query = new SimpleQuery(OrderServiceQuery.GET_TMALL_ORDER_DETAIL, orderNo, skuCode);
    return DatabaseUtil.loadAsBean(query, TmallOrderDetail.class);
  }

  // 2012-12-31 add yyq start
  @Override
  public ShippingDetailComposition getShippingDetailCompositionBean(String shippingNo, String skuCode) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_SHIPPING_DETAIL_COMPOSITION, shippingNo, skuCode);
    List<ShippingDetailComposition> sdcList = DatabaseUtil.loadAsBeanList(query, ShippingDetailComposition.class);
    if (sdcList != null && sdcList.size() > 0) {
      return (ShippingDetailComposition) sdcList.get(0);
    } else {
      return null;
    }
  }
  // 2012-12-31 add yyq end
  
  // 2013-09-05 add yyq start
  @Override
  public TmallShippingDetailComposition getTmallShippingDetailCompositionBean(String shippingNo, String skuCode) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_TMALL_SHIPPING_DETAIL_COMPOSITION, shippingNo, skuCode);
    List<TmallShippingDetailComposition> sdcList = DatabaseUtil.loadAsBeanList(query, TmallShippingDetailComposition.class);
    if (sdcList != null && sdcList.size() > 0) {
      return (TmallShippingDetailComposition) sdcList.get(0);
    } else {
      return null;
    }
  }
  
  @Override
  public JdShippingDetailComposition getJdShippingDetailCompositionBean(String shippingNo, String skuCode) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_JD_SHIPPING_DETAIL_COMPOSITION, shippingNo, skuCode);
    List<JdShippingDetailComposition> sdcList = DatabaseUtil.loadAsBeanList(query, JdShippingDetailComposition.class);
    if (sdcList != null && sdcList.size() > 0) {
      return (JdShippingDetailComposition) sdcList.get(0);
    } else {
      return null;
    }
  }
  // 2013-09-05 add yyq end

  // 20140306 txw add start
  private List<OrderPropagandaCommodityInfo> getOrderPropagandaCommodityInfo(String prefectureCode, String customerCode) {
    Object[] params = {
        prefectureCode, customerCode, customerCode, customerCode
    };
    Query query = new SimpleQuery(OrderServiceQuery.GET_PROPAGANDA_ACTIVITY_COMMODITY_QUERY, params);
    return DatabaseUtil.loadAsBeanList(query, OrderPropagandaCommodityInfo.class);
  }
  
  private List<OrderPropagandaCommodityInfo> getTmallOrderPropagandaCommodityInfo(String prefectureCode, String customerCode) {
    Object[] params = {
        prefectureCode
    };
    Query query = new SimpleQuery(OrderServiceQuery.GET_TMALL_PROPAGANDA_ACTIVITY_COMMODITY_QUERY, params);
    return DatabaseUtil.loadAsBeanList(query, OrderPropagandaCommodityInfo.class);
  }
  // 20140306 txw add end
  
  //20140305 hdh add start
  @Override
  public int compareTmallOrderStatus(String startTime, String endTime) {

    int result = 1;
    TmallService service = ServiceLocator.getTmallService(this.getLoginInfo());
    List<String> orderDiffList = new ArrayList<String>();
    List<String> orderNotExistList = new ArrayList<String>();

    String sql = "SELECT TMALL_TID,TMALL_STATUS FROM TMALL_ORDER_HEADER "
        + "WHERE TMALL_STATUS <> 'TRADE_FINISHED' AND TMALL_STATUS <> 'TRADE_BUYER_SIGNED' AND TMALL_STATUS <> 'WAIT_SELLER_SEND_GOODS'"
        + "AND ORDER_STATUS <> 2 AND ((CREATED_DATETIME BETWEEN ? AND ?) OR TMALL_STATUS = 'WAIT_BUYER_PAY')";
    Query query = new SimpleQuery(sql, startTime, endTime);
    List<TmallOrderHeader> orderHeaders = DatabaseUtil.loadAsBeanList(query, TmallOrderHeader.class);

    try {
      if (orderHeaders != null && orderHeaders.size() > 0) {
        for (int i = 0; i < orderHeaders.size(); i++) {
          TmallOrderHeader orderHeader = orderHeaders.get(i);
          String orderStatus = service.searchTmallTradeGet(orderHeader.getTmallTid());
          if (StringUtil.isNullOrEmpty(orderStatus)) {
            orderNotExistList.add(orderHeader.getTmallTid());
            continue;
          }
          if (!orderStatus.equals(orderHeader.getTmallStatus())) {
            orderDiffList.add(orderHeader.getTmallTid());
          }
        }
      }
      if (orderNotExistList.size() > 0 || orderDiffList.size() > 0) {
        MailInfo mailInfo = new MailInfo();
        StringBuffer sb = new StringBuffer();
        if (orderNotExistList.size() > 0) {
          sb.append("以下为今日天猫与EC订单状态对比结果:<BR><BR>");
        }
        for (String str : orderNotExistList) {
          sb.append(str + "访问API失败，通信异常<BR>");
        }
        if (orderDiffList.size() > 0) {
          sb.append("<BR><BR>以下为订单状态不一致的交易号:<BR><BR>");
        }
        for (String str : orderDiffList) {
          sb.append(str + "<BR>");
        }
        mailInfo.setText(sb.toString());
        mailInfo.setSubject("【品店】天猫与EC订单状态不一致警告");
        mailInfo.setSendDate(DateUtil.getSysdate());

        TmallSendMailConfig tmllMailSend = DIContainer.get(TmallSendMailConfig.class.getSimpleName());
        mailInfo.setFromInfo(tmllMailSend.getMailFromAddr(), tmllMailSend.getMailFromName());
        String[] mailToAddrArray = tmllMailSend.getMailToAddr().split(";");
        String[] mailToNameArray = tmllMailSend.getMailToName().split(";");
        for (int i = 0; i < mailToAddrArray.length; i++) {
          if (i >= mailToNameArray.length) {
            mailInfo.addToList(mailToAddrArray[i], mailToAddrArray[i]);
          } else {
            mailInfo.addToList(mailToAddrArray[i], mailToNameArray[i]);
          }
        }
        mailInfo.setContentType(MailInfo.CONTENT_TYPE_HTML);
        MailingService svc = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
        svc.sendImmediate(mailInfo);
      }
    } catch (Exception e) {
      result = 0;
      e.printStackTrace();
    }
    return result;
  }

  // 20140305 hdh add end
  
  
  // 20140311 hdh add start
  public Long countCustomerGroupCampaignOrder(String campaignCode, String customerCode) {
    Query query = new SimpleQuery(OrderServiceQuery.COUNT_USED_COUPON_ORDER_BY_CUSTOMER_CODE_QUERY, campaignCode, customerCode,
          OrderStatus.CANCELLED.getValue());
    return DatabaseUtil.executeScalar(query, Long.class);
  }

  @Override
  public SearchResult<MyOrder> searchMyOrderList(MyOrderListSearchCondition condition) {
    return DatabaseUtil.executeSearch(new MyOrderListSearchQuery(condition));
  }

  @Override
  public Long getCustomerCode(String chlidorderno) {
    Query query = null;
    query = new SimpleQuery(OrderServiceQuery.GET_CHILD_ORDER_NO, chlidorderno);
    return DatabaseUtil.executeScalar(query, Long.class);
  }
  // 20140311 hdh add end
  
  // 2014-12-31 zzy add start
  @Override
  public SearchResult<UntrueOrderWordResult> getUntrueOrderWordList(UntrueOrderWordSearchCondition condition) {
    return DatabaseUtil.executeSearch(new UntrueOrderWordSearchQuery(condition));
   
  }
  // 2014-12-31 zzy add end

  @Override
  public ServiceResult insertUntrueOrderWord(UntrueOrderWord uow) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    UntrueOrderWordDao uowdao=DIContainer.getDao(UntrueOrderWordDao.class);
    uowdao.insert(uow);
    return serviceResult;
  }
  
  @Override
  public ServiceResult deleteUntrueOrderWord(String uowcode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    UntrueOrderWordDao uowdao=DIContainer.getDao(UntrueOrderWordDao.class);
    uowdao.delete(uowcode);
    return serviceResult;
  }

  @Override
  public Long getCountUntrueOrderWord(String words) {
    Query query = null;
    query = new SimpleQuery(OrderServiceQuery.GET_COUNT_UNTRUE_ORDER_WORD, words);
    return DatabaseUtil.executeScalar(query, Long.class);
  }

  @Override
  public List<String> getUntrueOrderWordList() {
    Query query = new SimpleQuery(OrderServiceQuery.GET_UNTRUE_ORDER_WORD_LIST);
    List<String> getlist = DatabaseUtil.loadAsStringList(query);
    return getlist;
  }
}
