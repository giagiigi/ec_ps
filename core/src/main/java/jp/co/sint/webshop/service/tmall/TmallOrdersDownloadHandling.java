package jp.co.sint.webshop.service.tmall;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.dao.CCommodityDetailDao;
import jp.co.sint.webshop.data.domain.GuestFlg;
import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.OrderDownLoadStatus;
import jp.co.sint.webshop.data.domain.OrderFlg;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.dto.Area;
import jp.co.sint.webshop.data.dto.BatchTime;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.City;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.data.dto.TmallOrderDetail;
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.data.dto.TmallShippingDetail;
import jp.co.sint.webshop.data.dto.TmallShippingHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.impl.AbstractServiceImpl;
import jp.co.sint.webshop.service.order.DeliveryInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

/**
 * @author kousen-pa641 edit by os012 20120108 start
 */
public class TmallOrdersDownloadHandling extends AbstractServiceImpl {

  private static final long serialVersionUID = 1L;

  private List<TmallTradeHeader> extOrderHds;

  private List<TmallTradeDetail> extOrderDts;

  private List<TmallShippingDelivery> extShippHds;

  private TmallShippingDelivery extShippHd;

  private TmallTradeHeader extOrderHdr;

  private TmallTradeDetail extOrderDt;

  Logger logger = Logger.getLogger(this.getClass());

  /***
   * @param String
   *          startTime
   * @param String
   *          endTime
   * @param Booleean
   *          stockAllocate
   * @return ServiceResult serviceResult
   * @throws Exception
   *           RuntimeException
   */
  public int DownLoadOrdersByTime(String startTime, String endTime, boolean stockAllocate) throws NullPointerException {
    TmallManager tmallM = new TmallManager();
    TmallTradeInfoList orderAndShipInfoFromAPI = new TmallTradeInfoList();
    List<TmallOrderHeader> orderHeadDataFromAPI = new ArrayList<TmallOrderHeader>();
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    OrderService service = ServiceLocator.getOrderService(this.getLoginInfo());
    //CatalogService serviceCatalog = ServiceLocator.getCatalogService(this.getLoginInfo());
    int returnInt = 0;
    try {
      String start = GetDateStart(startTime);// batch前一次执行时间
      String end = GetDateEnd(endTime);// 系统现在执行时间
      // 获取API下载订单
      // 参数：start,end
      if (DateUtil.fromString(start, true).getTime() > DateUtil.fromString(end, true).getTime()) {
        serviceResult = (ServiceResultImpl) returnErrorResult("InitDateTimeUpEndTime", start, 1);// 判断时间大小
        StringUtil.sendTmallErrMail("", "淘宝订单下载时间不正确。");
        return returnInt = -1;
      }
      if ((DateUtil.fromString(end, true).getTime() / 1000 - DateUtil.fromString(start, true).getTime() / 1000 > 60 * 60 * 24)) {
        serviceResult = (ServiceResultImpl) returnErrorResult("InitDateTimeUpOneDay", start, 1);// 判断时间差(必须在一天内)
        StringUtil.sendTmallErrMail("", "淘宝订单下载时间不正确。时间差必须在一天内。");
        return returnInt = -1;
      }
      try {
        // 调用API
        orderAndShipInfoFromAPI = tmallM.searchOrderDetailList(start, end); 
      } catch (NullPointerException e) {
        serviceResult = (ServiceResultImpl) returnErrorResult("InterfaceNullPointerException", start, 1);// 接口空指针
        return returnInt = -1;
      } catch (Exception e) {
        logger.error(e.getMessage());
        serviceResult = (ServiceResultImpl) returnErrorResult("InterfaceRuntimeException", start, 1);// 接口运行时错误
        StringUtil.sendTmallErrMail("", "淘宝订单下载出现网络中断。");
        return returnInt = -1;
      }
      if (orderAndShipInfoFromAPI != null) {
        // 获取接口数据处理后订单
        logger.info("..............................封装数据开始..............................");
        orderHeadDataFromAPI = encapsulateOrdersAndShippInfo(orderAndShipInfoFromAPI);
        logger.info("..............................封装数据完成..............................");
        /**
         * 1. TMALL批处理
         */
        serviceResult = (ServiceResultImpl) service.TmallOrdersDownLoad(orderHeadDataFromAPI);
        if (serviceResult.hasError()) {
          
          // 20130427 add by yyq start 订单下载数据为空返回-3
          for (ServiceErrorContent errorContent : serviceResult.getServiceErrorList()) {
            if (errorContent.equals(OrderServiceErrorContent.ORDER_DOWN_INTERFACE_DATA_NULL )){
              logger.error("...........................下载数据为空不更新时间.............................");
              UpdateBatchTimeTb(start);// 修改batch时间和状态处理
              return returnInt = -3;
            }
          }
          // 20130427 add by yyq end 订单下载数据为空返回-3
          
          logger.error("...........................下载失败不更新时间.............................");
          UpdateBatchTimeTb(start);// 修改batch时间和状态处理
          return returnInt = -1;
        } else {
          logger.info("..............................下载成功更新时间.............................");
          UpdateBatchTimeTb(end);// 修改batch时间和状态处理
          StringUtil.sendTmallSuccMail(start, end);
        }
      } else {

        if (!StringUtil.isNullOrEmpty(tmallM.getIsNetError()) && tmallM.getIsNetError().equals("NETERROR")) {
          logger.error(".............................下载过程中网络中断............................");
          serviceResult = (ServiceResultImpl) returnErrorResult("InterfaceDataNull", start, 1);// 下载时网络连接出错
          StringUtil.sendTmallErrMail("", "淘宝订单下载出现网络中断。");
          return returnInt = -1;
        } else {
          // upd by lc 2012-08-15 start for 无订单下载时，batch时间依旧保持不变，满16小时后，发送警告邮件

          // serviceResult = (ServiceResultImpl)
          // returnErrorResult("InterfaceDataNull", end, 1);// 接口数据为空
          String dateAdd16Hour = DateUtil.toDateTimeString(DateUtil.addHour(DateUtil.fromString(start, true), 16));
          if (!DateUtil.isPeriodStringDateTime(start, dateAdd16Hour, DateUtil.getDateTimeString()) && stockAllocate) {
            serviceResult = (ServiceResultImpl) returnErrorResult("InterfaceDataNull", end, 1);
            StringUtil.sendTmallErrMail("", "已经超过16小时无订单下载");
            return returnInt = -1;
          } else {
            serviceResult = (ServiceResultImpl) returnErrorResult("InterfaceDataNull", start, 1);
          }

          // upd by lc 2012-08-15 end
        }
      }
   // 2014/06/12 库存更新对应 ob_李 add start
      /**
       * 2.根据标识是否执行再分配 true:执行 false:不执行
       *//*
      if (stockAllocate == true) {
        // 库存再分配
        serviceResult = (ServiceResultImpl) serviceCatalog.TmallStockAllocate();
        if (serviceResult.hasError()) {
          serviceResult.addServiceError(OrderServiceErrorContent.STOCK_ALLOCATE_FAILED);// 再分配失败
          logger.error("............................ 库存再分配失败 ..............................");
          return returnInt = -1;
        }
        *//**
         * 3.上传淘宝的SKu处理
         *//*
        try {
          serviceCatalog.TmallSku_Code_UP("", null, 0L, "1");
        } catch (Exception e) {
          StringUtil.sendTmallErrMail("", "上传淘宝的SKu处理失败。");
          serviceResult = (ServiceResultImpl) returnErrorResult("SKuRuntimeException", startTime, 0);// 上传淘宝的SKu失败
          logger.error("............................上传淘宝的SKu处理失败 ..............................");
          return returnInt = -1;
        }
      }*/
   // 2014/06/12 库存更新对应 ob_李 add end
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      serviceResult = (ServiceResultImpl) returnErrorResult("DataAccessException", startTime, 2);// 数据执行错误
      return returnInt = -1;
    } catch (Exception e) {
      logger.error(e.getMessage());
      serviceResult = (ServiceResultImpl) returnErrorResult("RuntimeException", startTime, 2);// 运行时错误
      StringUtil.sendTmallErrMail("", "淘宝订单下载出现网络中断。");
      return returnInt = -1;
    }
    return returnInt;
  }

  /***
   * 淘宝接口数据进行封装处理成订单及发货信息
   * 
   * @param orderContainer获取的接口数据
   * @return
   */
  public List<TmallOrderHeader> encapsulateOrdersAndShippInfo(TmallTradeInfoList orderAndShipInfoFromAPI) {
    CatalogService service = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
    TmallOrderHeader orderHeader = new TmallOrderHeader();
    TmallOrderDetail orderDetail = new TmallOrderDetail();
    TmallShippingHeader shippingHeader = new TmallShippingHeader();
    TmallShippingDetail shippingDetail = new TmallShippingDetail();
    List<TmallOrderHeader> orderHds = new ArrayList<TmallOrderHeader>();
    boolean blTmallStatus = false;
    boolean blcancel = false;
    WebshopConfig config = DIContainer.getWebshopConfig();
    // 获取的接口数据
    extOrderHds = orderAndShipInfoFromAPI.getOrderHeaderList();
    extShippHds = orderAndShipInfoFromAPI.getShippingHeaderList();

    // 获取下载的订单信息各列表；
    
    // 获取下载的发货信息各列表na
    List <String> errorTidList = new ArrayList<String>();
    for (int i = 0; i < extOrderHds.size(); i++) {
      try {
        extOrderHdr = extOrderHds.get(i);
        extShippHd = extShippHds.get(i);
        extOrderDts = extOrderHds.get(i).getOrderDetailList();
        CCommodityDetailDao detailDao = DIContainer.getDao(CCommodityDetailDao.class);
        // 获得总重量
        BigDecimal totalWeight = BigDecimal.ZERO;
        for (TmallTradeDetail todBean : extOrderDts) {
          CCommodityDetail cdBean = detailDao.load(config.getSiteShopCode(), todBean.getOuterIid());
          totalWeight = totalWeight.add(cdBean.getWeight().multiply(new BigDecimal(todBean.getNum())));
        }
        // add by lc 2012-08-15 start for 运费为零，且重量大于20KG的订单，将检查状态设置成未检查
        boolean checkFlg = false;
        if ((StringUtil.isNullOrEmpty(extShippHd.getPostFee()) || extShippHd.getPostFee().equals("0") || extShippHd.getPostFee()
            .equals("0.0"))
            && BigDecimalUtil.isAbove(totalWeight, BigDecimal.valueOf(config.getWeight()))
            && BigDecimalUtil.isAbove(new BigDecimal(extOrderHdr.getPayment()), config.getPrice())) {
          checkFlg = true;
        }
        // (拆、装)TMALL订单和发货各项信息
        orderHeader = SetOrderInfo(extOrderHdr, checkFlg);
        shippingHeader = SetShippingInfo(extShippHd, orderHeader, extOrderHdr.getBuyerObtainPointFee(), totalWeight.toString(),extOrderHdr);
        List<TmallOrderDetail> orderDets = new ArrayList<TmallOrderDetail>();
        List<TmallShippingDetail> shippDets = new ArrayList<TmallShippingDetail>();
        for (int j = 0; j < extOrderDts.size(); j++) {
          blTmallStatus = false;
          blcancel = false;
          extOrderDt = extOrderDts.get(j);
          // (拆、装)订单详细
          orderDetail = SetOrderDetailInfo(extOrderDt, orderHeader);
          // 判断淘宝所有详细订单状态 :订单详细都是SUCCESS时订单状态取消
          if ("SUCCESS".equals(orderDetail.getTmallRefundStatus())) {
            blcancel &= !blcancel;
          } else {
            blTmallStatus &= blTmallStatus;
          }
          // (拆、装)发货详细
          shippingDetail = SetShippingDetailInfo(shippingHeader, orderHeader, orderDetail);
          // 添加详细列表
          shippDets.add(shippingDetail);
          orderDets.add(orderDetail);
        }
        // 添加到DetailList列表中
        // 设置订单状态
        if (blTmallStatus && !blcancel) {
          orderHeader.setOrderStatus(OrderStatus.CANCELLED.longValue());
        } else {
          orderHeader.setOrderStatus(OrderStatus.ORDERED.longValue());
        }
        if ("TRADE_CLOSED_BY_TAOBAO".equals(orderHeader.getTmallStatus().toUpperCase())) { // 卖家或买家主动关闭交易
          orderHeader.setOrderStatus(OrderStatus.CANCELLED.longValue());
        }
        // (装)添加订单详细列表
        orderHeader.setOrderDetailList(orderDets);
        // (装)添加发货详细信息列表
        shippingHeader.setShippingDetailList(shippDets);
        // (装)添加发货信息
        orderHeader.setShippingHeader(shippingHeader);
        // (装)添加最终集合数据
        if (orderDets != null && orderDets.size() > 0 && extOrderHdr.getOrderDetailNums() == orderDets.size()) {
          orderHds.add(orderHeader);
        }
        logger.info("extOrderHdr.getOrderDetailNums() : " + extOrderHdr.getOrderDetailNums());
        logger.info("orderHeader.tmallTid : " + orderHeader.getTmallTid());
        logger.info("orderDets : " + orderDets.size());
      } catch (Exception e) {
        errorTidList.add(extOrderHdr.getTid());
        continue;
      }
    }
    if (errorTidList != null && errorTidList.size() > 0) {
      String tidStr = "";
      for (int i = 0; i < errorTidList.size(); i++) {
        tidStr += errorTidList.get(i) + "<br/>";
      }
      StringUtil.sendTmallErrMail(tidStr, "订单数据出现异常需要确认。");
    }
    
    
    return orderHds;
  }

  /**
   * @param type
   *          异常类型
   * @param time
   *          时间参数
   * @param updateType
   *          修改修改batch时间和状态处理类型
   * @return
   */
  public ServiceResult returnErrorResult(String type, String time, int updateType) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    if (type.equals("RuntimeException")) {
      logger.error("运行时错误.........................");
      logger.error(".........................................................");
      serviceResult.addServiceError(CommonServiceErrorContent.RUN_TIME_ERROR);
    }
    if (type.equals("DataAccessException")) {
      logger.error("数据执行错误.........................");
      logger.error(".........................................................");
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    if (type.equals("SKuRuntimeException")) {
      logger.error("上传淘宝的SKu处理失败.........................");
      logger.error(".........................................................");
      serviceResult.addServiceError(OrderServiceErrorContent.TAMLL_SKU_UPLOAD_FAILED);
    }
    if (type.equals("InterfaceRuntimeException")) {
      logger.error("订单下载接口报错..............................");
      logger.error(".........................................................");
      serviceResult.addServiceError(OrderServiceErrorContent.ORDER_DOWN_INTERFACE_FAILED);
    }
    if (type.equals("InterfaceNullPointerException")) {
      logger.error("订单下载接口空指针异常........................");
      logger.error(".........................................................");
      serviceResult.addServiceError(OrderServiceErrorContent.ORDER_DOWN_INTERFACE_FAILED);
    }
    if (type.equals("InitDateTimeUpOneDay")) {
      logger.error("订单下载接口时间设置不能超过一天........................");
      logger.error(".........................................................");
      serviceResult.addServiceError(OrderServiceErrorContent.ORDER_DOWN_TIME_UP_ONE_DAY);
    }
    if (type.equals("InitDateTimeUpEndTime")) {
      serviceResult.addServiceError(OrderServiceErrorContent.ORDER_DOWN_STARTTIME_UP_ENDTIME);
      logger.error("订单下载起始时间不能超过执行时间........................");
      logger.error(".........................................................");
    }
    if (type.equals("InterfaceDataNull")) {
      logger.warn("订单下载接收数据为空........................");
      logger.warn(".........................................................");
      serviceResult.addServiceError(OrderServiceErrorContent.ORDER_DOWN_INTERFACE_DATA_NULL);
    }
    if (updateType == 1) {
      UpdateBatchTimeTb(time);// 修改batch时间和状态处理：updateType=1,数据库BatchTime时间
    } else if (updateType == 2) {
      UpdateBatchTimeTb(GetDateStart(time));// 修改batch时间和状态处理：updateType=2,给定时间或者时间为空
    }
    return serviceResult;
  }

  /**
   * //设置Tmall订单表值
   * 
   * @param extOrderHdr
   * @param bool
   *          ,订单检查状态
   * @return
   */
  public TmallOrderHeader SetOrderInfo(TmallTradeHeader orderHeaderApi, boolean checkFlg) {
    WebshopConfig config = DIContainer.getWebshopConfig();
    TmallOrderHeader orderHeader = new TmallOrderHeader();

    logger.info("SetOrderInfo:" + orderHeaderApi.getTid());
    // 区域编号： 省/直辖市/自治区编号
    City city = GetCityCode(orderHeaderApi.getReceiverCity(), orderHeaderApi.getReceiverDistrict());
    // 区县信息
    Area area = GetAreaCode(city.getRegionCode(), city.getCityCode(), orderHeaderApi.getReceiverDistrict());

    // 收货地址为上海崇明县进行对应
    if (StringUtil.hasValue(orderHeaderApi.getReceiverDistrict()) && orderHeaderApi.getReceiverDistrict().equals("崇明县")) {
      orderHeader.setAddress1("上海崇明"); // 省/直辖市/自治区
      orderHeader.setAddress2("崇明县"); // 市
      orderHeader.setAddress4(orderHeaderApi.getReceiverAddress());// 详细地址
      orderHeader.setPrefectureCode("32");
      orderHeader.setCityCode("365");// 城市编码
      orderHeader.setAreaCode(area == null ? "" : area.getAreaCode());// 区县编号
    } else {
      orderHeader.setAddress1(orderHeaderApi.getReceiverState()); // 省/直辖市/自治区
      orderHeader.setAddress2(orderHeaderApi.getReceiverCity()); // 市
      orderHeader.setAddress3(orderHeaderApi.getReceiverDistrict()); // 街道
      orderHeader.setAddress4(orderHeaderApi.getReceiverAddress());// 详细地址
      orderHeader.setPrefectureCode(city.getRegionCode());
      orderHeader.setCityCode(city.getCityCode());// 城市编码
      orderHeader.setAreaCode(area == null ? "" : area.getAreaCode());// 区县编号
      orderHeader.setCaution(orderHeaderApi.getBuyerMessage());// 注意事项
      orderHeader.setTmallBuyerMessage(orderHeaderApi.getBuyerMessage());// 买家留言
    }

    // 区分货到付款和支付宝：依据货到付款物流状态和交易类型判断
    if ("COD".equals(orderHeaderApi.getType().toUpperCase())) {
      orderHeader.setAdvanceLaterFlg(1L);// 先後払フラグ
      orderHeader.setPaymentMethodType(PaymentMethodType.CASH_ON_DELIVERY.getValue());// 货到付款
      orderHeader.setPaymentMethodName("货到付款");// 支付名称
    } else {
      orderHeader.setAdvanceLaterFlg(0L);
      orderHeader.setPaymentMethodType(PaymentMethodType.ALIPAY.getValue());// 支付宝
      orderHeader.setPaymentMethodName("支付宝");// 支付名称
    }
    // 获取支付信息:依据 支付方式
    PaymentMethod pm = GetAlipayInfo(orderHeader.getPaymentMethodType(), orderHeader.getAdvanceLaterFlg());
    orderHeader.setClientGroup("99");// クライアントグループ
    orderHeader.setCouponPrice(BigDecimal.ZERO); // 电子优惠券
    orderHeader.setCreatedDatetime(DateUtil.fromTimestampString(orderHeaderApi.getCreated()));// 交易时间
    orderHeader.setCreatedUser(orderHeaderApi.getBuyerNick());
    orderHeader.setCustomerCode(orderHeaderApi.getTid());// 客户编号
    orderHeader.setCustomerGroupCode("0");// 客户组编号
    orderHeader.setCvsCode(null);
    orderHeader.setDataTransportStatus(0L);// データ連携ステータス ???
    orderHeader.setDigitalCashType(null);
    orderHeader.setDiscountCode(null);
    orderHeader.setDiscountDetailCode(null);
    orderHeader.setDiscountMode(null);
    orderHeader.setDiscountName(null);
    orderHeader.setDiscountPrice(StringUtil.isNullOrEmpty(orderHeaderApi.getDiscountFee()) ? BigDecimal.ZERO : BigDecimal
        .valueOf(Double.parseDouble(orderHeaderApi.getDiscountFee())));// 折扣金额不包含满就送
    orderHeader.setMjsDiscount(StringUtil.isNullOrEmpty(orderHeaderApi.getMjsDiscount()) ? BigDecimal.ZERO : BigDecimal
        .valueOf(Double.parseDouble(orderHeaderApi.getMjsDiscount())));// 满就送优惠金额
    orderHeader.setDiscountRate(null);
    orderHeader.setDiscountType(null);
    orderHeader.setEmail(StringUtil.isNullOrEmpty(orderHeaderApi.getBuyerEmail()) ? orderHeaderApi.getTid() + "@TMALL.COM.CN"
        : orderHeaderApi.getBuyerEmail());// email
    orderHeader.setFirstName(" ");
    orderHeader.setFirstNameKana(" ");
    orderHeader.setGuestFlg(GuestFlg.GUEST.longValue());// 客人标志
    orderHeader.setInvoiceFlg(InvoiceFlg.NO_NEED.longValue());// 需要领取发票标志
    orderHeader.setLastName(orderHeaderApi.getBuyerNick());// 昵称
    orderHeader.setLastNameKana(" ");
    orderHeader.setMessage(" ");
    orderHeader.setMobileNumber(orderHeaderApi.getReceiverMobile());// 手机号码
    orderHeader.setOrderDatetime(DateUtil.fromTimestampString(orderHeaderApi.getCreated()));// 订单时间
    // 订单标识
    // if (StringUtil.isNullOrEmpty(orderHeaderApi.getBuyerMessage())) {
    // upd by lc 2012-08-15 for 1,checkFlg为重量大于20kg，运费为零时。 2,0820为崇明岛CODE
//    if (!StringUtil.isNullOrEmpty(orderHeaderApi.getBuyerMessage()) || checkFlg
//        || BigDecimalUtil.isAboveOrEquals(NumUtil.parse(orderHeaderApi.getPayment()), new BigDecimal(10000L))
//        || (StringUtil.hasValue(orderHeaderApi.getReceiverDistrict()) && orderHeaderApi.getReceiverDistrict().equals("崇明县"))) {
      if (!StringUtil.isNullOrEmpty(orderHeaderApi.getBuyerMessage()) || checkFlg
          || BigDecimalUtil.isAboveOrEquals(NumUtil.parse(orderHeaderApi.getPayment()), new BigDecimal(10000L))) {
      orderHeader.setOrderFlg(0L);// 未检查
    } else {
      orderHeader.setOrderFlg(1L);// 已检查
    }
    if (orderHeaderApi.getOrderFlg() != null) {
      orderHeader.setOrderFlg(orderHeaderApi.getOrderFlg());
    }
    //存在团购商品 order_flg设为2
    //add by twh 2013-07-30 start
    String endDataStr = DIContainer.getWebshopConfig().getGroupBuyingEndTime();
    String startDataStr = DIContainer.getWebshopConfig().getGroupBuyingStartTime();
    Date endData = DateUtil.fromTimestampString(endDataStr);
    Date 
    startData = DateUtil.fromTimestampString(startDataStr);
    if (orderHeader.getOrderDatetime().before(endData) && orderHeader.getOrderDatetime().after(startData)){
      for (String configCode : DIContainer.getWebshopConfig().getGroupBuyingSkuList()){
        for (TmallTradeDetail orderCommodity : orderHeaderApi.getOrderDetailList()){
          if (configCode.equals(orderCommodity.getOuterIid())){
            orderHeader.setOrderFlg(2L);
          }
        }
      }
    }
    
    //临时添加北京广州仓标志  20141103 hdh 
//    boolean bjFlg = false;
//    boolean gzFlg = false;
    
    // 分仓临时对应 0709 start
    // 北京仓判断list start
    boolean allSpeCommodityFlg = true;
    for (TmallTradeDetail detail : orderHeaderApi.getOrderDetailList()) {
      boolean allSpeCommodityFlgTemp = false;
      for (String commodityCode : config.getJdSpeCommodityList()) {
        if (StringUtil.hasValue(commodityCode)) {
          commodityCode = commodityCode.trim();
        }
        if (detail.getOuterIid().equals(commodityCode)) {
          allSpeCommodityFlgTemp = true;
          break;
        }
      }
      if (!allSpeCommodityFlgTemp) {
        allSpeCommodityFlg = false;
        break;
      }
    }
    if (allSpeCommodityFlg
        && ("北京".equals(orderHeader.getAddress1()) || "天津".equals(orderHeader.getAddress1()) || "河北省".equals(orderHeader
            .getAddress1()))) {
      if (StringUtil.hasValue(orderHeader.getCaution())) {
        orderHeader.setCaution(orderHeader.getCaution() + "（分仓商品拦截）");
        orderHeader.setTmallBuyerMessage(orderHeader.getCaution());
      } else {
        orderHeader.setCaution("（分仓商品拦截）");
        orderHeader.setTmallBuyerMessage("（分仓商品拦截）");
      }
      orderHeader.setOrderFlg(0L);
      
//      bjFlg = true;
    }
    // 北京仓判断list end
    // 广州仓判断list start
    boolean allSpeCommodityFlgGz = true;
    for (TmallTradeDetail detail : orderHeaderApi.getOrderDetailList()) {
      boolean allSpeCommodityFlgTempGz = false;
      for (String commodityCode : config.getJdSpeCommodityListTwo()) {
        if (StringUtil.hasValue(commodityCode)) {
          commodityCode = commodityCode.trim();
        }
        if (detail.getOuterIid().equals(commodityCode)) {
          allSpeCommodityFlgTempGz = true;
          break;
        }
      }
      if (!allSpeCommodityFlgTempGz) {
        allSpeCommodityFlgGz = false;
        break;
      }
    }
    if (allSpeCommodityFlgGz && ("广东省".equals(orderHeader.getAddress1()))) {
      if (StringUtil.hasValue(orderHeader.getCaution())) {
        orderHeader.setCaution(orderHeader.getCaution() + "（分仓商品拦截）");
        orderHeader.setTmallBuyerMessage(orderHeader.getCaution());
      } else {
        orderHeader.setCaution("（分仓商品拦截）");
        orderHeader.setTmallBuyerMessage("（分仓商品拦截）");
      }
      orderHeader.setOrderFlg(0L);
      
//      gzFlg = true;
    }
    // 广州仓判断list end
    
    // 20141103 hdh add  start 双十一拦截
//    if(!bjFlg && !gzFlg){
//       String[] tempStrYuanTong = {"0000519", "0000520", "0000521", "0000522"};
//       
//      boolean curSpeCommodityFlg = true;
//      for (TmallTradeDetail detail : orderHeaderApi.getOrderDetailList()) {
//        boolean allSpeCommodityFlgTemp = false;
//        for (String commodityCode : tempStrYuanTong) {
//          if (StringUtil.hasValue(commodityCode)) {
//            commodityCode = commodityCode.trim();
//          }
//          if (detail.getOuterIid().equals(commodityCode)) {
//            allSpeCommodityFlgTemp = true;
//            break;
//          }
//        }
//        if (!allSpeCommodityFlgTemp) {
//          curSpeCommodityFlg = false;
//          break;
//        }
//      }
//      if(!curSpeCommodityFlg){
//        if (StringUtil.hasValue(orderHeader.getCaution())) {
//          orderHeader.setCaution(orderHeader.getCaution() + "（双11拦截）");
//          orderHeader.setTmallBuyerMessage(orderHeader.getCaution() + "（双11拦截）");
//        } else {
//          orderHeader.setCaution("（双11拦截）");
//          orderHeader.setTmallBuyerMessage("（双11拦截）");
//        }
//        orderHeader.setOrderFlg(0L);
//      }
//    }
    // 20141103 hdh add end 双十一拦截
    
    
    // 分仓临时对应 0709 end
    
    // 送货地址超过32个字符订单拦截到后台start
    // String addressUnion = "";
    // if (StringUtil.hasValue(orderHeaderApi.getReceiverDistrict())) {
    // addressUnion = orderHeaderApi.getReceiverState() +
    // orderHeaderApi.getReceiverCity() + orderHeaderApi.getReceiverDistrict()
    // + orderHeaderApi.getReceiverAddress();
    // } else {
    // addressUnion = orderHeaderApi.getReceiverState() +
    // orderHeaderApi.getReceiverCity() + orderHeaderApi.getReceiverAddress();
    // }
    if (StringUtil.hasValue(orderHeaderApi.getReceiverAddress())) {
      if (orderHeaderApi.getReceiverAddress().length() > 32) {
        orderHeader.setOrderFlg(2L);
      }
    }
    
    // 送货地址超过32个字符订单拦截到后台end  
    
    // 地址长度过长订单临时添加
    if (orderHeader.getOrderFlg() != null && orderHeader.getOrderFlg() == 2L) {
      if (orderHeader.getCaution() != null) {
        orderHeader.setCaution("（地址长度过长）" + orderHeader.getCaution());
      } else {
        orderHeader.setCaution("（地址长度过长）");
      }
      if (orderHeader.getTmallBuyerMessage() != null) {
        orderHeader.setTmallBuyerMessage("（地址长度过长）" + orderHeader.getTmallBuyerMessage());
      } else {
        orderHeader.setTmallBuyerMessage("（地址长度过长）");
      }
    }
    // 虚假订单关键词拦截处理
    String address = orderHeaderApi.getReceiverAddress();
    OrderService servicewords = ServiceLocator.getOrderService(getLoginInfo());
    List<String> untrueWordList = new ArrayList<String>();
    untrueWordList = servicewords.getUntrueOrderWordList();
    boolean vide = true;
    if (untrueWordList != null && untrueWordList.size() > 0) {
      for (String list : untrueWordList) {
        int islist = address.indexOf(list);
        if (islist != -1) {
          vide = false;
          break;
        }
      }
    }
    if (!vide) {
      orderHeader.setOrderFlg(OrderFlg.NOT_CHECKED.longValue());
      String message = orderHeader.getCaution();
      if (StringUtil.hasValue(message)) {
        orderHeader.setCaution("【疑是虚假订单】" + orderHeader.getCaution());
        orderHeader.setTmallBuyerMessage(orderHeader.getCaution());
      } else {
        orderHeader.setCaution("【疑是虚假订单】");
        orderHeader.setTmallBuyerMessage("【疑是虚假订单】");
      }
    }


    //add by twh 2013-07-30 end
    orderHeader.setOrderNo(null);
    orderHeader.setOrmRowid(orderHeader.getOrmRowid());
    orderHeader.setPaymentCommission(BigDecimal.ZERO);// 支払手数料
    orderHeader.setPaymentCommissionTax(BigDecimal.ZERO);// 支払手数料消費税額
    orderHeader.setPaymentCommissionTaxRate(0L);// 支払手数料消費税率
    orderHeader.setPaymentCommissionTaxType(0L); // 支払手数料消費税区分
    if (pm != null) {
      orderHeader.setPaymentMethodNo(pm.getPaymentMethodNo());// 支付方法编号
    } else {
      if (PaymentMethodType.CASH_ON_DELIVERY.getValue().equals(orderHeader.getPaymentMethodType())) {
        orderHeader.setPaymentMethodNo(10002L); // 支付方法编号
      } else {
        orderHeader.setPaymentMethodNo(10004L);// 支付方法编号
      }
    }
    orderHeader.setPaymentLimitDate(null); // 
    orderHeader.setPaymentOrderId(NumUtil.toLong(orderHeaderApi.getAlipayNo())); // 淘宝交易号
    orderHeader.setPaymentReceiptNo(null);
    orderHeader.setPaymentReceiptUrl(null);
    // 支付状态：付款/未付款(依据：1.等待卖家发货,即:买家已付款，2.等待买家确认收货,即:卖家已发货，3.买家已签收,货到付款专用，4.交易成功)
    if (!StringUtil.isNullOrEmpty(orderHeaderApi.getPayTime())) {
      if (orderHeaderApi.getStatus().toUpperCase().equals("WAIT_SELLER_SEND_GOODS")
          || orderHeaderApi.getStatus().toUpperCase().equals("WAIT_BUYER_CONFIRM_GOODS")
          || orderHeaderApi.getStatus().toUpperCase().equals("TRADE_BUYER_SIGNED")
          || orderHeaderApi.getStatus().toUpperCase().equals("TRADE_FINISHED")) {
        orderHeader.setPaymentDate(DateUtil.fromTimestampString(orderHeaderApi.getPayTime())); // 支付交易时间
        orderHeader.setPaymentStatus(PaymentStatus.PAID.longValue());
      } else {
        orderHeader.setPaymentStatus(PaymentStatus.NOT_PAID.longValue());
      }
    } else {
      orderHeader.setPaymentStatus(PaymentStatus.NOT_PAID.longValue());
    }
    orderHeader.setPhoneNumber(StringUtil.isNullOrEmpty(orderHeaderApi.getReceiverPhone()) ? " " : orderHeaderApi
        .getReceiverPhone());// 电话号码(为空时设置空格)
    orderHeader.setPostalCode(orderHeaderApi.getReceiverZip());// 邮编
    orderHeader.setShopCode(config.getSiteShopCode());// 商铺编号
    orderHeader.setTmallCodStatus(orderHeaderApi.getCodStatus());// 货到付款物流状态
    orderHeader.setTmallEndTime(DateUtil.fromTimestampString(orderHeaderApi.getEndTime()));// 交易结束时间
    orderHeader.setTmallInvoiceName(orderHeaderApi.getInvoiceName());// 发票抬头
    orderHeader.setTmallModifiedTime(DateUtil.fromTimestampString(orderHeaderApi.getModified()));// 交易修改时间
    orderHeader.setTmallRealPointFee(orderHeaderApi.getReal_point_fee());// 买家实际使用积分
    orderHeader.setTmallStatus(orderHeaderApi.getStatus());// 交易状态
    orderHeader.setTmallTid(orderHeaderApi.getTid());// 订单交易唯一标识码
    orderHeader.setTmallType(orderHeaderApi.getType());// 交易类型
    orderHeader.setUsedPoint(StringUtil.isNullOrEmpty(orderHeaderApi.getPointFee()) ? BigDecimal.ZERO : BigDecimal.valueOf(Double
        .parseDouble(orderHeaderApi.getPointFee())));// 买家使用积分
    orderHeader.setWarningMessage(" ");
    orderHeader.setUpdatedDatetime(DateUtil.getSysdate());
    orderHeader.setUpdatedUser(orderHeaderApi.getBuyerNick());
    orderHeader.setPaidPrice(StringUtil.isNullOrEmpty(orderHeaderApi.getReceivedPayment()) ? BigDecimal.ZERO : BigDecimal
        .valueOf(Double.parseDouble(orderHeaderApi.getReceivedPayment())));
    orderHeader.setCommissionFee(StringUtil.isNullOrEmpty(orderHeaderApi.getCommissionFee()) ? BigDecimal.ZERO : BigDecimal
        .valueOf(Double.parseDouble(orderHeaderApi.getCommissionFee()))); // 交易佣金
    orderHeader.setAdjustFee(StringUtil.isNullOrEmpty(orderHeaderApi.getAdjustFee()) ? BigDecimal.ZERO : BigDecimal.valueOf(Double
        .parseDouble(orderHeaderApi.getAdjustFee())));// 卖家手动修改金额
    // ######################积分兑换金额追加 start
    // 支付方法为支付宝时，并且已经支付时
    orderHeader.setPointConvertPrice(config.getTmallPointConvert().multiply(orderHeader.getUsedPoint()).setScale(2,
        BigDecimal.ROUND_HALF_UP));
    // 淘宝优惠
    orderHeader.setTmallDiscountPrice(StringUtil.isNullOrEmpty(orderHeaderApi.getTmall_discount_price()) ? BigDecimal.ZERO
        : BigDecimal.valueOf(Double.parseDouble(orderHeaderApi.getTmall_discount_price())));
    // ######################积分兑换金额追加 end
    return orderHeader;
  }

  /**
   * 封装Tmall详细订单表值
   * 
   * @param orderDetsAPI
   *          orderHAPI
   * @return TmallOrderDetail
   */
  public TmallOrderDetail SetOrderDetailInfo(TmallTradeDetail orderDetsAPI, TmallOrderHeader orderHAPI) {
    WebshopConfig config = DIContainer.getWebshopConfig();
    TmallOrderDetail orderDetail = new TmallOrderDetail();
    // 企划信息
    Plan tod = GetEcMaster(orderDetsAPI.getOuterIid(), DateUtil.fromTimestampString(orderDetsAPI.getCreated()));
    // 品牌信息
    Brand brand = this.GetBrand(orderDetsAPI.getOuterIid());
    if (tod != null) {
      if (tod.getPlanType() == 0L) {
        orderDetail.setSalePlanCode(tod.getPlanCode());// 销售企划编号
        orderDetail.setSalePlanName(tod.getPlanName());// 销售企划名称
      } else if (tod.getPlanType() == 1L) {
        orderDetail.setFeaturedPlanCode(tod.getPlanCode());// 特集企划编号
        orderDetail.setFeaturedPlanName(tod.getPlanName());// 特集企划名称
      }
    }
    orderDetail.setAppliedPointRate(0L);// 適用ポイント付与率
    if (brand != null) {
      orderDetail.setBrandCode(brand.getBrandCode());// 品牌编号
      orderDetail.setBrandName(brand.getBrandName());// 品牌
    }
    orderDetail.setCampaignCode(null);
    orderDetail.setCampaignDiscountRate(null);
    orderDetail.setCampaignName(null);
    orderDetail.setCommodityCode(orderDetsAPI.getOuterIid());// ec商品型号
    orderDetail.setCommodityName(orderDetsAPI.getTitle());// ec商品名称
    orderDetail.setCommodityTax(null);
    orderDetail.setCommodityTaxRate(null);
    orderDetail.setCommodityTaxType(0L);// 商品消費税区分
    orderDetail.setCreatedDatetime(DateUtil.fromTimestampString(orderDetsAPI.getCreated()));// 创建时间
    orderDetail.setCreatedUser(orderHAPI.getCreatedUser());// 创建者
    orderDetail.setOrderNo(orderHAPI.getOrderNo());// 订单号(初始系统默认值后插入操作时生成)
    orderDetail.setOrmRowid(orderDetail.getOrmRowid());
    orderDetail.setPurchasingAmount(Long.valueOf(orderDetsAPI.getNum()));// 购买数
    orderDetail.setRetailPrice(BigDecimal.valueOf(Double.parseDouble(orderDetsAPI.getPrice2())
        - Double.parseDouble(orderDetsAPI.getChildOrderPrice())));// 商品贩卖价格
    orderDetail.setRetailTax(BigDecimal.ZERO);// 消费税
    orderDetail.setShopCode(config.getSiteShopCode());// 商铺代码
    orderDetail.setSkuCode(StringUtil.isNullOrEmpty(orderDetsAPI.getOuterSkuId()) ? orderDetail.getCommodityCode() : orderDetsAPI
        .getOuterSkuId());// ecsku编号
    orderDetail.setStandardDetail1Name(orderDetsAPI.getSkuPropertiesNameOne());// 规格名称1
    orderDetail.setStandardDetail2Name(orderDetsAPI.getSkuPropertiesNameTwo());// 规格名称2
    orderDetail.setTmallCommodityCode(orderDetsAPI.getNumIid());// tmall商品型号
    orderDetail.setTmallModifiedTime(DateUtil.fromTimestampString(orderDetsAPI.getModified()));// 修改时间
    orderDetail.setTmallRefundId(orderDetsAPI.getRefundId()); // 退款ID
    orderDetail.setTmallRefundPaidPrice(orderDetsAPI.getRefundPayment() != null ? BigDecimal.valueOf(Double
        .parseDouble(orderDetsAPI.getRefundPayment())) : null);// 实际退款金额
    orderDetail.setTmallRefundStatus(orderDetsAPI.getRefundStatus());// 退货退款状态
    orderDetail.setTmallSkuCode(orderDetsAPI.getSkuId());// tmallsku编号
    orderDetail.setUnitPrice(BigDecimal.valueOf(Double.parseDouble(orderDetsAPI.getPrice1())));// 商品单价
    orderDetail.setUpdatedDatetime(DateUtil.fromTimestampString(orderDetsAPI.getModified()));// 明细修改时间
    orderDetail.setUpdatedUser(orderHAPI.getUpdatedUser());//
    
    // hdh
    orderDetail.setTmallCommodityCode(orderDetsAPI.getNumIid());
    
    return orderDetail;
  }

  /**
   * 封装Tmall发货表值
   * 
   * @param shippHdsAPI
   * @param orderHeaderAPI
   * @param 买家获得积分
   * @return TmallShippingHeader
   */
  public TmallShippingHeader SetShippingInfo(TmallShippingDelivery shippHdsAPI, TmallOrderHeader orderHeaderAPI,
      String buyerObtainPointFee, String totalWeight, TmallTradeHeader orderHeaderApi) {
    WebshopConfig config = DIContainer.getWebshopConfig();
    TmallShippingHeader shippingHeader = new TmallShippingHeader();

    // 区域编号： 省/直辖市/自治区编号
    City city = GetCityCode(shippHdsAPI.getReceiverCity(), shippHdsAPI.getReceiverDistrict());
    // 区县信息
    Area area = GetAreaCode(city.getRegionCode(), city.getCityCode(), shippHdsAPI.getReceiverDistrict());

    // 收货地址为上海崇明县进行对应
    if (StringUtil.hasValue(shippHdsAPI.getReceiverDistrict()) && shippHdsAPI.getReceiverDistrict().equals("崇明县")) {
      shippingHeader.setAddress1("上海崇明");// 省/市/自治区
      shippingHeader.setAddress2("崇明县");// 市/区
      shippingHeader.setAddress4(shippHdsAPI.getReceiverAddress());// 详细地址
      shippingHeader.setPrefectureCode("32");// 区域编号
      shippingHeader.setCityCode("365");// 城市代码
      shippingHeader.setAreaCode(area == null ? "" : area.getAreaCode());// 区县编号
    } else {
      shippingHeader.setAddress1(shippHdsAPI.getReceiverState());// 省/市/自治区
      shippingHeader.setAddress2(shippHdsAPI.getReceiverCity());// 市/区
      shippingHeader.setAddress3(shippHdsAPI.getReceiverDistrict());// 街道
      shippingHeader.setAddress4(shippHdsAPI.getReceiverAddress());// 详细地址
      shippingHeader.setPrefectureCode(city == null ? "01" : city.getRegionCode());// 区域编号
      shippingHeader.setCityCode(orderHeaderAPI.getCityCode());// 城市代码
      shippingHeader.setAreaCode(area == null ? "" : area.getAreaCode());// 区县编号
    }
    // 运送信息:CODTYPE支付宝为1L，货到付款为0L
    DeliveryInfo dri = GetDeliveryCopNo((orderHeaderAPI.getAdvanceLaterFlg() == 0L ? 1L : 0L), shippingHeader.getPrefectureCode(),
        shippingHeader.getCityCode(),shippingHeader.getAreaCode(),totalWeight.toString());
    // 设置运输公司和运输公司编码
    if (dri == null) {
      UtilService utilService = ServiceLocator.getUtilService(ServiceLoginInfo.getInstance());
      DeliveryCompany deliveryCompany = utilService.getDefaultDeliveryCompany();
      shippingHeader.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());
      shippingHeader.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
    } else {
      shippingHeader.setDeliveryCompanyName(dri.getDeliveryCompanyName());// 运输公司
      shippingHeader.setDeliveryCompanyNo(dri.getDeliveryCompanyNo());// 运输公司编码
    }

    if (StringUtil.isNullOrEmpty(buyerObtainPointFee)) {
      buyerObtainPointFee = "0";
    }
    shippingHeader.setAcquiredPoint(BigDecimal.valueOf(Double.parseDouble(buyerObtainPointFee)));// 买家获得积分
    shippingHeader.setAddressFirstName(" ");
    shippingHeader.setAddressFirstNameKana(" ");
    shippingHeader.setAddressLastName(shippHdsAPI.getReceiverName());// 昵称
    shippingHeader.setAddressLastNameKana(" ");
    shippingHeader.setAddressNo(0L);// アドレス帳番号
    shippingHeader.setArrivalDate(null);
    shippingHeader.setArrivalTimeEnd(null);
    shippingHeader.setArrivalTimeStart(null);
    shippingHeader.setCustomerCode(shippHdsAPI.getTid());// 顾客编号
    shippingHeader.setDeliveryAppointedDate(null);
    shippingHeader.setDeliveryAppointedTimeEnd(null);
    shippingHeader.setDeliveryAppointedTimeStart(null);
    shippingHeader.setDeliveryRemark(null);
    shippingHeader.setDeliverySlipNo(null);
    shippingHeader.setDeliveryTypeName("平邮");// 发货类型
    shippingHeader.setDeliveryTypeNo(0L); // 发货类型编号
    shippingHeader.setFixedSalesStatus(0L); // 销售确定状态
    shippingHeader.setMobileNumber(shippHdsAPI.getReceiverMobile());// 手机号码
    shippingHeader.setOrderNo(orderHeaderAPI.getOrderNo());// 订单号
    shippingHeader.setOriginalShippingNo(null);
    shippingHeader.setOrmRowid(shippingHeader.getOrmRowid());
    shippingHeader.setPhoneNumber(shippHdsAPI.getReceiverPhone());// 电话号码
    shippingHeader.setPostalCode(shippHdsAPI.getReceiverZip());// 邮编
    shippingHeader.setReturnItemDate(null);
    shippingHeader.setReturnItemLossMoney(BigDecimal.ZERO);
    shippingHeader.setReturnItemType(0L);
    shippingHeader.setTmallShippingFlg(0L);
    shippingHeader.setShippingCharge(StringUtil.isNullOrEmpty(shippHdsAPI.getPostFee()) ? BigDecimal.ZERO : BigDecimal
        .valueOf(Double.parseDouble(shippHdsAPI.getPostFee()))); // 邮费
    shippingHeader.setShippingChargeTax(BigDecimal.ZERO);// 送料消費税額
    shippingHeader.setShippingChargeTaxRate(0L);// 送料消費税率
    shippingHeader.setShippingChargeTaxType(2L);// 送料消費税区分
    shippingHeader.setShippingDate(DateUtil.fromTimestampString(shippHdsAPI.getConsignTime()));// 发货时间
    shippingHeader.setShippingDirectDate(null);
    shippingHeader.setShippingNo(null);
    if ((orderHeaderAPI.getPaymentDate() != null)
        || ("COD".equals(orderHeaderAPI.getTmallType().toUpperCase()) && "WAIT_SELLER_SEND_GOODS".equals(orderHeaderAPI
            .getTmallStatus().toUpperCase()))) {
      shippingHeader.setShippingStatus(ShippingStatus.READY.longValue());// 发货状态
    } else {
      shippingHeader.setShippingStatus(ShippingStatus.NOT_READY.longValue());
    }
    shippingHeader.setShopCode(config.getSiteShopCode());// 商铺编号
    shippingHeader.setCreatedDatetime(DateUtil.fromTimestampString(shippHdsAPI.getConsignTime()));// 创建时间
    shippingHeader.setCreatedUser(shippHdsAPI.getReceiverName());// 创建人
    shippingHeader.setUpdatedDatetime(DateUtil.getSysdate());
    shippingHeader.setUpdatedUser(orderHeaderAPI.getCreatedUser());
    
 // 牛奶活动临时对应start
    boolean haveSpeCommodityFlg = false;
    String[] tempStr = {
        "9300639500102", "4036300669452", "0000446", "6934868301174", "930063950004110", "6952229200379", "0000447","315525035286124"
    };

    for (int l = 0; l < tempStr.length; l++) {
      for (TmallTradeDetail orderCommodity : orderHeaderApi.getOrderDetailList()) {
        if (orderCommodity.getOuterIid().equals(tempStr[l])) {
          haveSpeCommodityFlg = true;
          break;
        }
      }
      if (haveSpeCommodityFlg) {
        break;
      }
    }
    if (haveSpeCommodityFlg) {
      if (!("上海".equals(shippingHeader.getAddress1())) && !("内蒙古自治区".equals(shippingHeader.getAddress1()))
          && !("新疆维吾尔自治区".equals(shippingHeader.getAddress1())) && !("西藏自治区".equals(shippingHeader.getAddress1()))
          && !("海南省".equals(shippingHeader.getAddress1())) && !("宁夏回族自治区".equals(shippingHeader.getAddress1()))
          && !("青海省".equals(shippingHeader.getAddress1()))) {
        if ("江苏省".equals(shippingHeader.getAddress1())) {
          shippingHeader.setDeliveryCompanyName("赛澳递");
          shippingHeader.setDeliveryCompanyNo("D005");
        } else {
          shippingHeader.setDeliveryCompanyName("百世汇通");
          shippingHeader.setDeliveryCompanyNo("D004");
        }
      }
    }
    // 牛奶活动临时对应end
    
//    // 牛奶活动临时对应TWOstart
//    boolean haveSpeCommodityFlgTwo = false;
//    String[] tempStrTwo = {
//        "693486830117412"
//    };
//
//    for (int l = 0; l < tempStrTwo.length; l++) {
//      for (TmallTradeDetail orderCommodity : orderHeaderApi.getOrderDetailList()) {
//        if (orderCommodity.getOuterIid().equals(tempStrTwo[l])) {
//          haveSpeCommodityFlgTwo = true;
//          break;
//        }
//      }
//      if (haveSpeCommodityFlgTwo) {
//        break;
//      }
//    }
//    if (haveSpeCommodityFlgTwo) {
//      if ("浙江省".equals(shippingHeader.getAddress1())) {
//        
//      }
//    }
//    // 牛奶活动临时对应TWOend
    
    
    

    
    // 2014-11-06 如果TMALL的订单中仅包括以下商品，且地址是上海以外地区的，走汇通（北京特殊走宅急送），上海走默认 start
    boolean shNotCommodityFlg = true;
    List<String> shncl = config.getShNotCommodityList();
    if (shncl != null && shncl.size() > 0) {
      for (TmallTradeDetail detail : orderHeaderApi.getOrderDetailList()) {
        boolean shNotCommodityFlgTemp = false;
        for (String commodityCode : config.getShNotCommodityList()) {
          if (StringUtil.hasValue(commodityCode)) {
            commodityCode = commodityCode.trim();
          }
          if (detail.getOuterIid().equals(commodityCode)) {
            shNotCommodityFlgTemp = true;
            break;
          }
        }
        if (!shNotCommodityFlgTemp) {
          shNotCommodityFlg = false;
          break;
        }
      }
      if (shNotCommodityFlg) {
        if (!("上海".equals(shippingHeader.getAddress1())) && !("内蒙古自治区".equals(shippingHeader.getAddress1()))
            && !("新疆维吾尔自治区".equals(shippingHeader.getAddress1())) && !("西藏自治区".equals(shippingHeader.getAddress1()))
            && !("海南省".equals(shippingHeader.getAddress1())) && !("宁夏回族自治区".equals(shippingHeader.getAddress1()))
            && !("青海省".equals(shippingHeader.getAddress1()))) {
          shippingHeader.setDeliveryCompanyName("百世汇通");
          shippingHeader.setDeliveryCompanyNo("D004");
          if ("北京".equals(shippingHeader.getAddress1())) {
            shippingHeader.setDeliveryCompanyName("宅急送");
            shippingHeader.setDeliveryCompanyNo("D002");
          }
        } 
      }
    }
    // 2014-11-06 如果TMALL的订单中仅包括以下商品，且地址是上海以外地区的，走汇通（北京特殊走宅急送），上海走默认 end
    
    // 分仓临时对应 0709 start
    // 北京仓对应start
    boolean allSpeCommodityFlg = true;
    for (TmallTradeDetail detail : orderHeaderApi.getOrderDetailList()) {
      boolean allSpeCommodityFlgTemp = false;
      for (String commodityCode : config.getJdSpeCommodityList()) {
        if (StringUtil.hasValue(commodityCode)) {
          commodityCode = commodityCode.trim();
        }
        if (detail.getOuterIid().equals(commodityCode)) {
          allSpeCommodityFlgTemp = true;
          break;
        }
      }
      if (!allSpeCommodityFlgTemp) {
        allSpeCommodityFlg = false;
        break;
      }
    }
    if (allSpeCommodityFlg) {
      if ("北京".equals(shippingHeader.getAddress1()) || "河北省".equals(shippingHeader.getAddress1())
          || "天津".equals(shippingHeader.getAddress1())) {
        shippingHeader.setDeliveryCompanyName("圆通");
        shippingHeader.setDeliveryCompanyNo("D011");
      }
    }
    // 北京仓对应end
    // 广州仓对应start
    boolean allSpeCommodityFlgGz = true;
    for (TmallTradeDetail detail : orderHeaderApi.getOrderDetailList()) {
      boolean allSpeCommodityFlgTempGz = false;
      for (String commodityCode : config.getJdSpeCommodityListTwo()) {
        if (StringUtil.hasValue(commodityCode)) {
          commodityCode = commodityCode.trim();
        }
        if (detail.getOuterIid().equals(commodityCode)) {
          allSpeCommodityFlgTempGz = true;
          break;
        }
      }
      if (!allSpeCommodityFlgTempGz) {
        allSpeCommodityFlgGz = false;
        break;
      }
    }
    if (allSpeCommodityFlgGz) {
      if ("广东省".equals(shippingHeader.getAddress1())) {
        shippingHeader.setDeliveryCompanyName("中通");
        shippingHeader.setDeliveryCompanyNo("D010");
      }
    }
    // 广州仓对应end
    // 分仓临时对应 0709 end
    
    // 2014-10-24 如果TMALL的订单中仅包括以下商品，就把快递公司改成圆通 start
    boolean shNotCommodityFlgYuanTong = true;
    String[] tempStrYuanTong = {
        "0000522"
    };
    if (tempStrYuanTong != null && tempStrYuanTong.length > 0) {
      for (TmallTradeDetail detail : orderHeaderApi.getOrderDetailList()) {
        boolean shNotCommodityFlgTemp = false;
        for (int l = 0; l < tempStrYuanTong.length; l++) {
          if (detail.getOuterIid().equals(tempStrYuanTong[l])) {
            shNotCommodityFlgTemp = true;
            break;
          }
        }
        if (!shNotCommodityFlgTemp) {
          shNotCommodityFlgYuanTong = false;
          break;
        }
      }
      if (shNotCommodityFlgYuanTong) {
          shippingHeader.setDeliveryCompanyName("圆通");
          shippingHeader.setDeliveryCompanyNo("D011");
      }
    }
 
    // 2014-10-24 如果TMALL的订单中仅包括以下商品，就把快递公司改成圆通 end

    return shippingHeader;
  }

  /**
   * 设置详细发货信息
   * 
   * @param shippHd
   * @param orderHeader
   * @param orderDetail
   * @return TmallShippingDetail
   */
  public TmallShippingDetail SetShippingDetailInfo(TmallShippingHeader shippHd, TmallOrderHeader orderHeader,
      TmallOrderDetail orderDetail) {
    WebshopConfig config = DIContainer.getWebshopConfig();
    TmallShippingDetail shippingDetail = new TmallShippingDetail();
    shippingDetail.setDiscountAmount(null);
    shippingDetail.setDiscountPrice(null);
    shippingDetail.setGiftCode(null);
    shippingDetail.setGiftName(null);
    shippingDetail.setGiftPrice(BigDecimal.ZERO);// 礼品价格
    shippingDetail.setGiftTax(null);
    shippingDetail.setGiftTaxRate(null);
    shippingDetail.setGiftTaxType(0L);// 消费税区分
    shippingDetail.setOrmRowid(shippingDetail.getOrmRowid());
    shippingDetail.setPurchasingAmount(orderDetail.getPurchasingAmount());// 销售数量
    shippingDetail.setRetailPrice(orderDetail.getRetailPrice());// 销售价格
    shippingDetail.setRetailTax(null);
    shippingDetail.setShippingDetailNo(null);
    shippingDetail.setShippingNo(shippHd.getShippingNo());// 发货编号
    shippingDetail.setShopCode(config.getSiteShopCode());// 商铺编号
    shippingDetail.setSkuCode(orderDetail.getSkuCode());// SKU编号
    shippingDetail.setUnitPrice(orderDetail.getUnitPrice());// 单价
    shippingDetail.setCreatedDatetime(shippHd.getCreatedDatetime());// 创建时间
    shippingDetail.setCreatedUser(shippHd.getCreatedUser());// 创建人
    shippingDetail.setUpdatedDatetime(shippHd.getUpdatedDatetime());
    shippingDetail.setUpdatedUser(shippHd.getUpdatedUser());
    
    // hdh start
    shippingDetail.setCommodityCode(orderDetail.getCommodityCode());
    shippingDetail.setTmallCommodityCode(orderDetail.getTmallCommodityCode());
    //之处获取tmallSKU是为了在后续程序知道是否为多SKU商品
//    shippingDetail.setTmallSkuCode(orderDetail.getTmallSkuCode());
    
    
    return shippingDetail;
  }

  /**
   * 获取开始时间点
   * 
   * @param 开始时间
   *          (可有可无)
   * @return 开始时间点(String类型)
   */
  public String GetDateStart(String start) {
    String datetest = "";
    if (StringUtil.isNullOrEmpty(start)) {
      datetest = DateUtil.toDateTimeString(GetBatchTime().getFromTime());
    } else {
      datetest = start;
    }
    Date datetest2 = DateUtil.fromString(datetest, true);
    datetest = DateUtil.toDateTimeString(datetest2);// return
    // "2012/03/09 00:46:45";
    return datetest;

  }

  /**
   * 获取系统时间点
   * 
   * @param 时间点
   *          (可有可无)
   * @return 系统时间点(String类型)
   */
  public String GetDateEnd(String end) {
    String datetest = "";
    if (StringUtil.isNullOrEmpty(end)) {
      TmallService service = ServiceLocator.getTmallService(getLoginInfo());
      String tmallTime = service.getTmallSysDateTime();// 淘宝系统时间获取:"yyyy-MM-dd hh:mm:ss"

      if (tmallTime != null) {
        datetest = tmallTime.replace("-", "/");// 转换格式为： "yyyy/MM/dd hh:mm:ss"
      } else if (StringUtil.isNullOrEmpty(tmallTime)) {
        datetest = DateUtil.toDateTimeString(DateUtil.getSysdate());// ec系统时间
      }
    } else {
      datetest = end;
    }
    Date datetest2 = DateUtil.fromString(datetest, true);
    long Time1 = (datetest2.getTime() / 1000);
    datetest2.setTime(Time1 * 1000);
    datetest = DateUtil.toDateTimeString(datetest2);// return
    // "2012/03/09 12:46:45";
    return datetest;

  }

  /**
   * 获取支付信息
   * 
   * @param 支付方式
   * @param 先后支付标识
   *          (支付宝/货到付款)
   * @return
   */
  public PaymentMethod GetAlipayInfo(String method, Long flg) {
    OrderService service = ServiceLocator.getOrderService(this.getLoginInfo());
    return service.GetAlipayInfo(method, flg);
  }

  /**
   * 获取城市编号和地域编号
   * 
   * @param cityName
   * @param districtName
   * @return 城市信息
   */
  public City GetCityCode(String cityName, String districtName) {
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    return service.getCity(cityName, districtName);
  }

  /**
   * 获取区县编号
   * 
   * @param 省
   *          、直辖市、自治区编号
   * @param 城市编号
   * @param 街道
   * @return 区县编号
   */
  public Area GetAreaCode(String prefectureCode, String cityCode, String district) {
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    return service.getArea(prefectureCode, cityCode, district);
  }

  /**
   * 获取企划信息
   * 
   * @param 商品编号
   * @param 企划时间
   * @return Plan企划信息
   */
  public Plan GetEcMaster(String commodityCode, Date time) {
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    return service.GetEcMaster(commodityCode, time);
  }

  /**
   * 获取运送信息
   * 
   * @param 支付宝为1L
   *          、货到付款为0L
   * @param 地区编号
   * @return 运送信息
   */
  public DeliveryInfo GetDeliveryCopNo(Long cod, String RegionCode,String cityCode,String areaCode, String weight) {
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    return service.GetDeliveryCopNo(cod, RegionCode, cityCode, areaCode, weight);
  }

  /**
   * 获取batch时间
   * 
   * @return BatchTime
   */
  public BatchTime GetBatchTime() {
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    return service.getBatchTime();
  }

  /**
   * 获取Brand
   * 
   * @param commodityCode
   * @return Brand
   */
  public Brand GetBrand(String commodityCode) {
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    return service.getBrand(commodityCode);
  }

  /**
   * 程序异样时再修改batch 时间处理和状态处理
   */
  public void UpdateBatchTimeTb(String from) {
    OrderService serviceOrder = ServiceLocator.getOrderService(this.getLoginInfo());
    Date fromTime = DateUtil.fromString(from, true);
    ServiceResultImpl serviceResult = (ServiceResultImpl) serviceOrder.OperateBatchTime(fromTime, OrderDownLoadStatus.CANDOWNLOAD
        .longValue());
    // serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
  }

}
