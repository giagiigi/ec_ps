package jp.co.sint.webshop.service.jd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.dao.JdBJCommodityDao;
import jp.co.sint.webshop.data.dao.JdGZCommodityDao;
import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.JdOrderStatus;
import jp.co.sint.webshop.data.domain.OrderDownLoadStatus;
import jp.co.sint.webshop.data.domain.OrderFlg;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignDoings;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityMaster;
import jp.co.sint.webshop.data.dto.JdBatchTime;
import jp.co.sint.webshop.data.dto.JdCouponDetail;
import jp.co.sint.webshop.data.dto.JdOrderDetail;
import jp.co.sint.webshop.data.dto.JdOrderHeader;
import jp.co.sint.webshop.data.dto.JdShippingDetail;
import jp.co.sint.webshop.data.dto.JdShippingDetailComposition;
import jp.co.sint.webshop.data.dto.JdShippingHeader;
import jp.co.sint.webshop.data.dto.JdStock;
import jp.co.sint.webshop.data.dto.JdStockAllocation;
import jp.co.sint.webshop.data.dto.JdSuitCommodity;
import jp.co.sint.webshop.data.dto.OrderInvoice;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.JdService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.campain.CampaignMain;
import jp.co.sint.webshop.service.event.StockEvent;
import jp.co.sint.webshop.service.event.StockEventType;
import jp.co.sint.webshop.service.impl.AbstractServiceImpl;
import jp.co.sint.webshop.service.jd.order.JdOrderManager;
import jp.co.sint.webshop.service.jd.order.JdOrderResult;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;

import org.apache.log4j.Logger;

import com.jd.open.api.sdk.domain.order.CouponDetail;
import com.jd.open.api.sdk.domain.order.ItemInfo;
import com.jd.open.api.sdk.domain.order.OrderSearchInfo;

public class JdOrderDownload extends AbstractServiceImpl {

  private static final long serialVersionUID = 1L;

  // 京东没有返回时间时
  private static final String NO_DATE = "0001-01-01 00:00:00";

  private static final String NO_INVOICE_INFO = "不需要开具发票";

  // 京东起始时间
  private static final String ZERO_DATE = "1970-01-01 00:00:00";

  public JdOrderDownloadErrorInfo orderDownload(String startDate, String endDate) {

    JdOrderDownloadErrorInfo errorInfo = null;

    Logger logger = Logger.getLogger(this.getClass());

    JdOrderManager orderManager = new JdOrderManager();

    // startDate = GetDateStart(startDate);
    // endDate = GetDateEnd(endDate);
    // 订单状态
    String orderState = JdOrderStatus.WAIT_SELLER_STOCK_OUT.getName() + "," + JdOrderStatus.WAIT_GOODS_RECEIVE_CONFIRM + ","
        + JdOrderStatus.FINISHED_L.getName();

    JdOrderResult orderResult = null;
    try {
      orderResult = orderManager.orderDownload(startDate, endDate, orderState);
    } catch (Exception e) {
      logger.error("订单下载失败");
      errorInfo = new JdOrderDownloadErrorInfo();
      errorInfo.setJdOrderNo("");
      errorInfo.setMessage("网络中断");
      e.printStackTrace();
      return errorInfo;
    }
    if (orderResult != null && orderResult.getResultBean().isErrorFlg()) {
      logger.error("订单下载失败");
      errorInfo = new JdOrderDownloadErrorInfo();
      errorInfo.setJdOrderNo("");
      errorInfo.setMessage("网络中断");
      return errorInfo;
    }
    List<OrderSearchInfo> result = orderResult.getResultBean().getOrderList();

    if (result != null && result.size() > 0) {

      errorInfo = dealWithOrderList(result, endDate);
    }

    return errorInfo;
  }

  // 封闭处理数据
  public JdOrderDownloadErrorInfo dealWithOrderList(List<OrderSearchInfo> result, String endDate) {

    // 地址mapping
    ChangeAdressJdToTmallUtil addressMappingUtil = new ChangeAdressJdToTmallUtil();

    Logger logger = Logger.getLogger(this.getClass());
    JdOrderDownloadErrorInfo errorInfo = null;
    JdService jdService = ServiceLocator.getJdService(this.getLoginInfo());
    

    JdBJCommodityDao bjDao = DIContainer.getDao(JdBJCommodityDao.class);
    JdGZCommodityDao gzDao = DIContainer.getDao(JdGZCommodityDao.class);
    
    CatalogService catalogService = ServiceLocator.getCatalogService(this.getLoginInfo());

    List<ValidationResult> resultList = null;

    List<JdOrderContainer> orderList = new ArrayList<JdOrderContainer>();
    for (int i = 0; i < result.size(); i++) {
//       for (int i = 2; i < 3; i++) {

      OrderSearchInfo searchInfo = result.get(i);
      
      // 多sku测试临时对应
//      if("8093804266".equals(searchInfo.getOrderId())){
//        continue;
//      }
      
      JdOrderContainer orderContainer = new JdOrderContainer();

      // // 订单编号
      // Long orderno =
      // DatabaseUtil.generateSequence(SequenceType.JD_ORDER_NO_SEQ);
      // String createOrderNo = "D" + orderno.toString();
      //      
      //      
      // // 发货编号
      // Long shippingNo =
      // DatabaseUtil.generateSequence(SequenceType.JD_SHIPPING_NO_SEQ);
      // String createShipppingNo = "D" + shippingNo.toString();
      //      

      // 商品列表
      List<JdOrderDetail> orderDetailList = new ArrayList<JdOrderDetail>();

      // 发货明细列表
      List<JdShippingDetailContainer> shippingDetailList = new ArrayList<JdShippingDetailContainer>();
      
      // 卫星仓发货明细
      List<JdShippingDetailContainer> partShippingDetailList = new ArrayList<JdShippingDetailContainer>();
      // 总重量
      BigDecimal totalWeight = BigDecimal.ZERO;
      
      JdOrderExtraInfo extraInfo = new JdOrderExtraInfo();//折单信息
      
      // 卫星仓商品价格
      BigDecimal partOrderPrice = BigDecimal.ZERO;
      
      BigDecimal totalUnitPrice = BigDecimal.ZERO;// 单品优惠价格统计
      
      BigDecimal  totalRetailPrice = BigDecimal.ZERO; //商品销售价统计

      // int itemCount = -1;

      // 发货明细中组合套装compositionNo编号
      int shippingDetailCompositionNo = -1;
      
      // 卫星仓中发货明细中组合套装compositionNo编号
//      int partShippingDetailCompositionNo = -1;
      

      // 广州收货地址标志
      boolean addressGZFlag = false;

      // 北京收货地址标志
      boolean addressBJFlag = false;

      String province = searchInfo.getConsigneeInfo().getProvince();
      if ("广东省".equals(province) ||"广东".equals(province) ) {
        addressGZFlag = true;
      } else if ("北京".equals(province) || "天津".equals(province) || "河北".equals(province)|| "河北省".equals(province)) {
        addressBJFlag = true;
      } else{
        extraInfo.setShFlag(true);
      }
      
      // 商品列表  ,分仓判断，上海仓处理
      for (ItemInfo itemInfo : searchInfo.getItemInfoList()) {
        // itemCount ++;
        BigDecimal retailPrice = BigDecimal.ZERO;// 价格(去掉单品优惠)
        
        CCommodityDetail cCmodityDetail = null;
        //京东多SKU对应
        CommodityMaster cm = catalogService.getCommodityMasterByJdCode(itemInfo.getWareId());
        if(cm!=null){  //多sku
          if(catalogService.getCommoditySkuByJdCode(cm.getCommodityCode(),itemInfo.getOuterSkuId())==null ){
            errorInfo = new JdOrderDownloadErrorInfo();
            errorInfo.setMessage("子商品SKU编号不存在 (京东商品编号:" +itemInfo.getWareId()+",SKU编号:"+itemInfo.getOuterSkuId()+")");
            errorInfo.setJdOrderNo(searchInfo.getOrderId());
            return errorInfo;
          }
          cCmodityDetail = jdService.getBySkuCode(itemInfo.getOuterSkuId());
        }else{
          cCmodityDetail = jdService.getByJdCommodityCode(itemInfo.getWareId());
        }

        if (cCmodityDetail == null) {
          errorInfo = new JdOrderDownloadErrorInfo();
          errorInfo.setMessage("京东商品编号对应的EC中商品不存在(京东商品编号:"+itemInfo.getWareId()+",EC商品编号:"+itemInfo.getProductNo()+")");
          errorInfo.setJdOrderNo(searchInfo.getOrderId());
          return errorInfo;
        }
        JdOrderDetail orderDetail = new JdOrderDetail();
        // 验证，临时设置成0
        orderDetail.setOrderNo("0");
        orderDetail.setShopCode("00000000");
        orderDetail.setSkuCode(cCmodityDetail.getSkuCode());
        orderDetail.setCommodityCode(cCmodityDetail.getCommodityCode());
        if (itemInfo.getSkuName().length() > 50) {
          orderDetail.setCommodityName(itemInfo.getSkuName().substring(0, 50));
        } else {
          orderDetail.setCommodityName(itemInfo.getSkuName());
        }

        orderDetail.setPurchasingAmount(NumUtil.toLong(itemInfo.getItemTotal()));
        orderDetail.setUnitPrice(new BigDecimal(itemInfo.getJdPrice()));
        orderDetail.setRetailPrice(new BigDecimal(itemInfo.getJdPrice()));
        orderDetail.setRetailTax(new BigDecimal("0.00"));
        orderDetail.setCommodityTaxType(0L);
        orderDetail.setCampaignDiscountRate(0L);
        orderDetail.setAppliedPointRate(0L);
        // orderDetail.setCreatedUser(DEFAULT_USER);
        // orderDetail.setCreatedDatetime(new Date());
        // orderDetail.setUpdatedUser(DEFAULT_USER);
        // orderDetail.setUpdatedDatetime(new Date());
        orderDetail.setBrandCode(null);
        orderDetail.setBrandName(null);
        orderDetail.setJdSkuCode(null);
        orderDetail.setJdCommodityCode(itemInfo.getWareId());
        setUserStatus(orderDetail);

        // 价格 减去对应单品优惠 20140523
        for (int k = 0; k < searchInfo.getCouponDetailList().size(); k++) {
          CouponDetail detail = searchInfo.getCouponDetailList().get(k);
          if (StringUtil.isNullOrEmpty(detail.getOrderId())) {
            continue;
          }
          if (itemInfo.getSkuId().equals(detail.getSkuId())) {
            totalUnitPrice = totalUnitPrice.add(new BigDecimal(detail.getCouponPrice()));
            retailPrice = new BigDecimal(itemInfo.getJdPrice()).subtract(new BigDecimal(detail.getCouponPrice())
                .divide(new BigDecimal(itemInfo.getItemTotal())));
            orderDetail.setRetailPrice(retailPrice);
            searchInfo.getCouponDetailList().remove(k);
            break;
          }
        }

        // 验证
        resultList = BeanValidator.validate(orderDetail).getErrors();
        if (resultList.size() > 0) {
          StringBuffer buffer = new StringBuffer();
          for (ValidationResult errResult : resultList) {
            logger.error(errResult.getFormedMessage());
            buffer.append(errResult.getFormedMessage());
          }
          errorInfo = new JdOrderDownloadErrorInfo();
          errorInfo.setJdOrderNo(searchInfo.getOrderId());
          errorInfo.setMessage(buffer.toString());
          return errorInfo;
        }
        orderDetailList.add(orderDetail);
        
        JdShippingDetailContainer shippingDetailContainer = new JdShippingDetailContainer();
        
        JdShippingDetail shippingDetail = new JdShippingDetail();
        // 验证，临时设置成0
        shippingDetail.setShippingNo("0");
        shippingDetail.setShippingDetailNo(0L);
        shippingDetail.setShopCode("00000000");
        shippingDetail.setSkuCode(cCmodityDetail.getSkuCode());
        if (retailPrice == BigDecimal.ZERO) {
          shippingDetail.setRetailPrice(new BigDecimal(itemInfo.getJdPrice()));
        } else {
          shippingDetail.setRetailPrice(retailPrice);
        }
        shippingDetail.setUnitPrice(new BigDecimal(itemInfo.getJdPrice()));
        shippingDetail.setPurchasingAmount(NumUtil.toLong(itemInfo.getItemTotal()));
        shippingDetail.setGiftPrice(new BigDecimal("0.00"));
        shippingDetail.setGiftTax(BigDecimal.ZERO);
        shippingDetail.setGiftTaxRate(0L);
        shippingDetail.setGiftTaxType(0L);
        // shippingDetail.setCreatedUser(DEFAULT_USER);
        // shippingDetail.setCreatedDatetime(new Date());
        // shippingDetail.setUpdatedUser(DEFAULT_USER);
        // shippingDetail.setUpdatedDatetime(new Date());
        setUserStatus(shippingDetail);

        // 验证
        resultList = BeanValidator.validate(shippingDetail).getErrors();
        if (resultList.size() > 0) {
          StringBuffer buffer = new StringBuffer();
          for (ValidationResult errResult : resultList) {
            logger.error(errResult.getFormedMessage());
            buffer.append(errResult.getFormedMessage());
          }
          errorInfo = new JdOrderDownloadErrorInfo();
          errorInfo.setJdOrderNo(searchInfo.getOrderId());
          errorInfo.setMessage(buffer.toString());
          return errorInfo;
        }

        // 组合套装
        CommodityHeader ch = jdService.getCommodityHeader(orderDetail.getShopCode(), orderDetail.getCommodityCode());
        List<JdShippingDetailComposition> compositionList = new ArrayList<JdShippingDetailComposition>();
        if (StringUtil.hasValue(ch.getOriginalCommodityCode())) {
          CCommodityDetail ccd = jdService.loadCCommodityDetail(ch.getShopCode(), ch.getOriginalCommodityCode());
          CommodityHeader cch = jdService.getCommodityHeader(ch.getShopCode(), ch.getOriginalCommodityCode());
          shippingDetailCompositionNo++;
          JdShippingDetailComposition shippingDetailComposition = new JdShippingDetailComposition();

          shippingDetailComposition.setShippingNo(shippingDetail.getShippingNo());
          shippingDetailComposition.setShippingDetailNo(shippingDetail.getShippingDetailNo());
          shippingDetailComposition.setCompositionNo(NumUtil.toLong(shippingDetailCompositionNo + ""));

          shippingDetailComposition.setShopCode(orderDetail.getShopCode());
          shippingDetailComposition.setParentCommodityCode(ch.getCommodityCode());
          shippingDetailComposition.setParentSkuCode(ch.getCommodityCode());
          shippingDetailComposition.setChildCommodityCode(ch.getOriginalCommodityCode());
          shippingDetailComposition.setChildSkuCode(ch.getOriginalCommodityCode());
          shippingDetailComposition.setCommodityName(cch.getCommodityName());
          shippingDetailComposition.setUnitPrice(BigDecimalUtil.divide(orderDetail.getUnitPrice(), ch.getCombinationAmount(), 2,
              RoundingMode.UP));
          shippingDetailComposition.setRetailPrice(BigDecimalUtil.divide(orderDetail.getRetailPrice(), ch.getCombinationAmount(),
              2, RoundingMode.UP));
          shippingDetailComposition.setRetailTax(orderDetail.getRetailTax());
          shippingDetailComposition.setCommodityTax(orderDetail.getCommodityTax());
          shippingDetailComposition.setCommodityTaxRate(orderDetail.getCommodityTaxRate());
          shippingDetailComposition.setCommodityTaxType(orderDetail.getCommodityTaxType());
          shippingDetailComposition.setCommodityWeight(ccd.getWeight());
          shippingDetailComposition.setStandardDetail1Name(ccd.getStandardDetail1Name());
          shippingDetailComposition.setStandardDetail2Name(ccd.getStandardDetail2Name());
          shippingDetailComposition
              .setPurchasingAmount(Long.valueOf(orderDetail.getPurchasingAmount() * ch.getCombinationAmount()));

          resultList = BeanValidator.validate(shippingDetailComposition).getErrors();
          if (resultList.size() > 0) {
            StringBuffer buffer = new StringBuffer();
            for (ValidationResult errResult : resultList) {
              logger.error(errResult.getFormedMessage());
              buffer.append(errResult.getFormedMessage());
            }
            errorInfo = new JdOrderDownloadErrorInfo();
            errorInfo.setJdOrderNo(searchInfo.getOrderId());
            errorInfo.setMessage(buffer.toString());
            return errorInfo;
          }
          compositionList.add(shippingDetailComposition);
          shippingDetailContainer.setCompositionList(compositionList);

        } else if ("1".equals(NumUtil.toString(ch.getSetCommodityFlg()))) { // 套装品

          List<SetCommodityComposition> setCommodityCompositions = catalogService.getSetCommodityCompositipon(orderDetail
              .getCommodityCode(), orderDetail.getShopCode());
          if (setCommodityCompositions != null) {
            for (SetCommodityComposition composition : setCommodityCompositions) {
              CCommodityDetail ccd = jdService.loadCCommodityDetail(composition.getShopCode(), composition.getChildCommodityCode());
              CommodityHeader cch = jdService.getCommodityHeader(composition.getShopCode(), composition.getChildCommodityCode());
              shippingDetailCompositionNo++;

              JdShippingDetailComposition shippingDetailComposition = new JdShippingDetailComposition();

              shippingDetailComposition.setShippingNo(shippingDetail.getShippingNo());
              shippingDetailComposition.setShippingDetailNo(shippingDetail.getShippingDetailNo());
              shippingDetailComposition.setCompositionNo(NumUtil.toLong(shippingDetailCompositionNo + ""));

              shippingDetailComposition.setShopCode(orderDetail.getShopCode());
              shippingDetailComposition.setParentCommodityCode(ch.getCommodityCode());
              shippingDetailComposition.setParentSkuCode(ch.getCommodityCode());
              shippingDetailComposition.setChildCommodityCode(ccd.getCommodityCode());
              shippingDetailComposition.setChildSkuCode(ccd.getCommodityCode());
              shippingDetailComposition.setCommodityName(cch.getCommodityName());

              if (shippingDetail.getUnitPrice() != null) {
                shippingDetailComposition.setUnitPrice(BigDecimalUtil.divide(composition.getTmallRetailPrice(), 1L, 2,
                    RoundingMode.UP)); // 商品单价
              }
              if (shippingDetail.getRetailPrice() != null) {
                if (BigDecimalUtil.equals(shippingDetail.getUnitPrice(), shippingDetail.getRetailPrice())) {
                  shippingDetailComposition.setRetailPrice(composition.getTmallRetailPrice());
                } else {
//                  BigDecimal cheapPrice = BigDecimalUtil.subtract(shippingDetail.getUnitPrice(), shippingDetail.getRetailPrice());
//                  BigDecimal everyCheapPrice = BigDecimalUtil.divide(cheapPrice, setCommodityCompositions.size());
//                  BigDecimal everyRealPrice = BigDecimalUtil.subtract(composition.getTmallRetailPrice(), everyCheapPrice);
//                  shippingDetailComposition.setRetailPrice(BigDecimalUtil.divide(everyRealPrice, 1L, 2, RoundingMode.UP)); // 销售价格
                    shippingDetailComposition.setRetailPrice(composition.getTmallRetailPrice()); // 销售价格
                }
              }
              shippingDetailComposition.setRetailTax(orderDetail.getRetailTax());
              shippingDetailComposition.setCommodityTax(orderDetail.getCommodityTax());
              shippingDetailComposition.setCommodityTaxRate(orderDetail.getCommodityTaxRate());
              shippingDetailComposition.setCommodityTaxType(orderDetail.getCommodityTaxType());
              shippingDetailComposition.setCommodityWeight(ccd.getWeight());
              shippingDetailComposition.setStandardDetail1Name(ccd.getStandardDetail1Name());
              shippingDetailComposition.setStandardDetail2Name(ccd.getStandardDetail2Name());
              shippingDetailComposition.setPurchasingAmount(orderDetail.getPurchasingAmount());

              resultList = BeanValidator.validate(shippingDetailComposition).getErrors();
              if (resultList.size() > 0) {
                StringBuffer buffer = new StringBuffer();
                for (ValidationResult errResult : resultList) {
                  logger.error(errResult.getFormedMessage());
                  buffer.append(errResult.getFormedMessage());
                }
                errorInfo = new JdOrderDownloadErrorInfo();
                errorInfo.setJdOrderNo(searchInfo.getOrderId());
                errorInfo.setMessage(buffer.toString());
                return errorInfo;
              }
              compositionList.add(shippingDetailComposition);
            }
          }
        }
        shippingDetailContainer.setCompositionList(compositionList);
        shippingDetailContainer.setShippingDetail(shippingDetail);
        // 重量累加
        totalWeight = totalWeight.add(cCmodityDetail.getWeight().multiply(new BigDecimal(itemInfo.getItemTotal())));
        totalRetailPrice = totalRetailPrice.add(shippingDetail.getRetailPrice().multiply(new BigDecimal(itemInfo.getItemTotal())));
        
        if (addressGZFlag && gzDao.load(cCmodityDetail.getCommodityCode()) != null) {
          partShippingDetailList.add(shippingDetailContainer);
          extraInfo.setGzFlag(true);
          partOrderPrice = partOrderPrice.add(shippingDetail.getRetailPrice().multiply(new BigDecimal(itemInfo.getItemTotal())));
        }
        else if( addressBJFlag && bjDao.load(cCmodityDetail.getCommodityCode()) != null) {
          partShippingDetailList.add(shippingDetailContainer);
          extraInfo.setBjFlag(true);
          partOrderPrice = partOrderPrice.add(shippingDetail.getRetailPrice().multiply(new BigDecimal(itemInfo.getItemTotal())));
        }else{
          shippingDetailList.add(shippingDetailContainer);
          extraInfo.setShFlag(true);
        }
        
      }
      JdOrderHeader orderHeader = new JdOrderHeader();

      // 验证，临时设置成0
      orderHeader.setOrderNo("0");

      orderHeader.setShopCode("00000000");
      orderHeader.setCustomerCode(searchInfo.getOrderId());
      orderHeader.setGuestFlg(1L);

      JdOrderStatus jdOrderStatus = JdOrderStatus.fromName(searchInfo.getOrderState());
      orderHeader.setOrderStatus(jdOrderStatus.longValue());
      orderHeader.setJdOrderStatus(searchInfo.getOrderState());
      orderHeader.setLastName(searchInfo.getConsigneeInfo().getFullname());
      orderHeader.setFirstName("JD");
      orderHeader.setLastNameKana("カナ");
      orderHeader.setFirstNameKana("カナ");
      orderHeader.setEmail("JD@JD.com");
      orderHeader.setPostalCode("000000");
      orderHeader.setPhoneNumber(searchInfo.getConsigneeInfo().getTelephone());
      orderHeader.setAdvanceLaterFlg(0L);
      orderHeader.setPaymentMethodNo(10017L);
      orderHeader.setPaymentMethodType(PaymentMethodType.JD_ONLINEPAYMENT.getValue());
      orderHeader.setPaymentMethodName(PaymentMethodType.JD_ONLINEPAYMENT.getName());
      orderHeader.setPaymentCommission(new BigDecimal("0.00"));
      orderHeader.setPaymentCommissionTax(new BigDecimal("0.00"));
      orderHeader.setPaymentCommissionTaxRate(0L);
      orderHeader.setPaymentCommissionTaxType(0L);
      // 京豆
      // List<CouponDetail> couponDetailList = searchInfo.getCouponDetailList();
      // Long usedPoint=0L;
      // for(CouponDetail couponDetail :couponDetailList){
      // if(JdCouponType.JD_BEAN.getValue().equals(couponDetail.getCouponType())){
      // Long tempPoint = null;
      // if(NumUtil.isNum(couponDetail.getCouponPrice())){
      // tempPoint = NumUtil.toLong((new
      // BigDecimal(couponDetail.getCouponPrice()).multiply
      // (new BigDecimal("100"))).toString());
      // }else{
      // tempPoint = 0L;
      // }
      // usedPoint = usedPoint + tempPoint;
      // }
      // }
      orderHeader.setUsedPoint(BigDecimal.ZERO);
      if (NO_DATE.equals(searchInfo.getPaymentConfirmTime()) || ZERO_DATE.equals(searchInfo.getPaymentConfirmTime())) {
        orderHeader.setPaymentDate(null);
        orderHeader.setPaymentStatus(0L);
      } else {
        orderHeader.setPaymentDate(DateUtil.fromTimestampString(searchInfo.getPaymentConfirmTime()));
        orderHeader.setPaymentStatus(1L);
      }
      orderHeader.setCustomerGroupCode("0");
      orderHeader.setDataTransportStatus(0L);// 如果更新，需要置1
      orderHeader.setPaymentLimitDate(null);
      orderHeader.setClientGroup("99");
      orderHeader.setCaution(searchInfo.getOrderRemark());
      orderHeader.setMessage("");
      orderHeader.setPaymentOrderId(null);
      orderHeader.setCvsCode(null);
      orderHeader.setPaymentReceiptNo(null);
      orderHeader.setPaymentReceiptUrl(null);
      orderHeader.setDigitalCashType(null);
      // orderHeader.setCreatedUser(DEFAULT_USER);
      // orderHeader.setCreatedDatetime(new Date());
      // orderHeader.setUpdatedUser(DEFAULT_USER);
      // orderHeader.setUpdatedDatetime(new Date());
      setUserStatus(orderHeader);
      orderHeader.setMobileNumber(searchInfo.getConsigneeInfo().getMobile());
      orderHeader.setCouponPrice(new BigDecimal("0.00"));
      // 订单总金额
      orderHeader.setPaidPrice(new BigDecimal(searchInfo.getOrderSellerPrice()).add(new BigDecimal(searchInfo.getFreightPrice())));

      // 订单拦截状态设置为1,已检查
      orderHeader.setOrderFlg(OrderFlg.CHECKED.longValue());
      
      // 发票处理
      // 增值税
      OrderInvoice orderInvoice = null;
      if (searchInfo.getVatInvoiceInfo() != null && !NO_INVOICE_INFO.equals(searchInfo.getInvoiceInfo())
          && StringUtil.hasValue(searchInfo.getVatInvoiceInfo().getTaxpayerIdent())) {
        orderHeader.setInvoiceFlg(1L);
        orderHeader.setOrderFlg(OrderFlg.NOT_CHECKED.longValue());
        orderInvoice = new OrderInvoice();
        orderInvoice.setCustomerCode(searchInfo.getOrderId());
        orderInvoice.setOrderNo(orderHeader.getOrderNo());
        orderInvoice.setCommodityName("日用品");
        orderInvoice.setInvoiceType(1L);
        orderInvoice.setBankName(searchInfo.getVatInvoiceInfo().getDepositBank());
        //银行帐号去空格  20141210 hdh update start
        if(StringUtil.hasValue(searchInfo.getVatInvoiceInfo().getBankAccount())){
          orderInvoice.setBankNo(searchInfo.getVatInvoiceInfo().getBankAccount().replaceAll(" ", ""));
        }
        // 20141210 hdh update end
        orderInvoice.setTaxpayerCode(searchInfo.getVatInvoiceInfo().getTaxpayerIdent());
        orderInvoice.setAddress(searchInfo.getVatInvoiceInfo().getRegisteredAddress());
        if (StringUtil.hasValue(searchInfo.getVatInvoiceInfo().getRegisteredPhone())) {
          orderInvoice.setTel(searchInfo.getVatInvoiceInfo().getRegisteredPhone().replace("-", ""));
        } else {
          orderInvoice.setTel(searchInfo.getVatInvoiceInfo().getRegisteredPhone());
        }
        orderInvoice.setCompanyName("增值税公司名称");
        // 验证
        resultList = BeanValidator.validate(orderInvoice).getErrors();
        if (resultList.size() > 0) {
          StringBuffer buffer = new StringBuffer();
          for (ValidationResult errResult : resultList) {
            logger.error(errResult.getFormedMessage());
            buffer.append(errResult.getFormedMessage());
          }
          errorInfo = new JdOrderDownloadErrorInfo();
          errorInfo.setJdOrderNo(orderHeader.getJdOrderNo());
          errorInfo.setMessage(buffer.toString());
          return errorInfo;
        }

      } else {
        if (StringUtil.hasValue(searchInfo.getInvoiceInfo()) && !NO_INVOICE_INFO.equals(searchInfo.getInvoiceInfo())) {
          orderHeader.setCaution(orderHeader.getCaution() + searchInfo.getInvoiceInfo());
          orderHeader.setMessage(orderHeader.getMessage() + searchInfo.getInvoiceInfo());
          orderHeader.setJdBuyerMessage(orderHeader.getJdBuyerMessage() + searchInfo.getInvoiceInfo());
          orderHeader.setInvoiceFlg(InvoiceFlg.NEED.longValue());
          orderHeader.setOrderFlg(OrderFlg.NOT_CHECKED.longValue());
        } else {
          orderHeader.setInvoiceFlg(InvoiceFlg.NO_NEED.longValue());
        }

      }

      // 除去京豆以外的全部优惠金额
      // List<CouponDetail> jdCouponDetailList =
      // searchInfo.getCouponDetailList();
      // BigDecimal discountPrice = BigDecimal.ZERO;
      // for (CouponDetail couponDetail : jdCouponDetailList) {
      // if
      // (!JdCouponType.JD_BEAN.getValue().equals(couponDetail.getCouponType()))
      // {
      // discountPrice = discountPrice.add(new
      // BigDecimal(couponDetail.getCouponPrice()));
      // }
      // }
      // orderHeader.setDiscountPrice(discountPrice);
      if (StringUtil.hasValue(searchInfo.getSellerDiscount())) {
        orderHeader.setDiscountPrice(new BigDecimal(searchInfo.getSellerDiscount()).subtract(totalUnitPrice));
      }

      orderHeader.setJdEndTime(DateUtil.fromTimestampString(searchInfo.getOrderEndTime()));
      orderHeader.setJdBuyerMessage(searchInfo.getOrderRemark());
      orderHeader.setJdOrderStatus(searchInfo.getOrderState());
      orderHeader.setJdModifiedTime(DateUtil.fromTimestampString(searchInfo.getModified()));
      orderHeader.setJdOrderNo(searchInfo.getOrderId());
      orderHeader.setOrderPayment(new BigDecimal(searchInfo.getOrderPayment()));
      orderHeader.setOrderSellerPrice(new BigDecimal(searchInfo.getOrderSellerPrice()));
      orderHeader.setJdInvoiceName(searchInfo.getInvoiceInfo());
      // 地址mapping
      orderHeader = addressMappingUtil.ChangeOrderHeaderAdress(searchInfo, orderHeader, totalWeight);

      if (!NO_DATE.equals(searchInfo.getOrderStartTime()) && !ZERO_DATE.equals(searchInfo.getOrderStartTime())) {
        orderHeader.setOrderDatetime(DateUtil.fromTimestampString(searchInfo.getOrderStartTime()));
      }

      //虚假订单关键词拦截处理 
      // 20150128 zzy add start
      String address = orderHeader.getAddress4();
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
        if(StringUtil.hasValue(orderHeader.getCaution())){
          
        }
        orderHeader.setCaution("【疑是虚假订单】" + orderHeader.getCaution());
        orderHeader.setJdBuyerMessage(orderHeader.getCaution());
      }
      // 20150128 zzy add end
      
      
      resultList = BeanValidator.validate(orderHeader).getErrors();
      if (resultList.size() > 0) {
        StringBuffer buffer = new StringBuffer();
        for (ValidationResult errResult : resultList) {
          logger.error(errResult.getFormedMessage());
          buffer.append(errResult.getFormedMessage());
        }
        errorInfo = new JdOrderDownloadErrorInfo();
        errorInfo.setJdOrderNo(orderHeader.getJdOrderNo());
        errorInfo.setMessage(buffer.toString());
        return errorInfo;
      }

      // 订单拦截
      if (StringUtil.hasValue(searchInfo.getOrderRemark())) {
        orderHeader.setOrderFlg(OrderFlg.NOT_CHECKED.longValue());
      }
      WebshopConfig config = DIContainer.getWebshopConfig();
      if ("江苏".equals(orderHeader.getAddress1()) || "浙江".equals(orderHeader.getAddress1())
          || "上海".equals(orderHeader.getAddress1())) {
        if ((orderHeader.getPaidPrice().compareTo(config.getJdPrice1()) > -1
            && orderHeader.getPaidPrice().compareTo(config.getJdPrice2()) == -1 && totalWeight.compareTo(config.getJdWeight1()) > 0)
            || (orderHeader.getPaidPrice().compareTo(config.getJdPrice2()) > -1 && totalWeight.compareTo(config.getJdWeight2()) > 0)) {
          orderHeader.setOrderFlg(OrderFlg.NOT_CHECKED.longValue());
        }
      } else {
        if (totalWeight.compareTo(config.getJdWeight3()) > 0) {
          orderHeader.setOrderFlg(OrderFlg.NOT_CHECKED.longValue());
        }
      }
      // 京东对应EC特殊商品订单拦截 start
      // boolean haveSpeCommodityFlg = false;
      // for (String commodityCode : config.getJdSpeCommodityList()) {
      // for (JdOrderDetail detail : orderDetailList) {
      // if (StringUtil.hasValue(commodityCode)) {
      // commodityCode = commodityCode.trim();
      // }
      // if (detail.getCommodityCode().equals(commodityCode)) {
      // orderHeader.setOrderFlg(0L);
      // haveSpeCommodityFlg = true;
      // if (StringUtil.hasValue(orderHeader.getCaution())) {
      // orderHeader.setCaution(orderHeader.getCaution() + "（特殊商品拦截）");
      // orderHeader.setJdBuyerMessage(orderHeader.getCaution() + "（特殊商品拦截）");
      // } else {
      // orderHeader.setCaution("（特殊商品拦截）");
      // orderHeader.setJdBuyerMessage("（特殊商品拦截）");
      // }
      // break;
      // }
      // }
      // if (haveSpeCommodityFlg) {
      // break;
      // }
      // }
      // 京东对应EC特殊商品订单拦截end

      // 分仓临时对应 0709 start JD暂不对应YYYYYYYY要对应就发出来
      // boolean allSpeCommodityFlg = true;
      // for (JdOrderDetail detail : orderDetailList) {
      // boolean allSpeCommodityFlgTemp = false;
      // for (String commodityCode : config.getJdSpeCommodityList()) {
      // if (StringUtil.hasValue(commodityCode)) {
      // commodityCode = commodityCode.trim();
      // }
      // if (detail.getCommodityCode().equals(commodityCode)) {
      // allSpeCommodityFlgTemp = true;
      // break;
      // }
      // }
      // if (!allSpeCommodityFlgTemp) {
      // allSpeCommodityFlg = false;
      // break;
      // }
      // }
      //
      // if (allSpeCommodityFlg
      // && ("广东".equals(orderHeader.getAddress1()) ||
      // "北京".equals(orderHeader.getAddress1())
      // || "天津".equals(orderHeader.getAddress1()) ||
      // "河北".equals(orderHeader.getAddress1()))) {
      // if (StringUtil.hasValue(orderHeader.getCaution())) {
      // orderHeader.setCaution(orderHeader.getCaution() + "（分仓商品拦截）");
      // orderHeader.setJdBuyerMessage(orderHeader.getCaution() + "（分仓商品拦截）");
      // } else {
      // orderHeader.setCaution("（分仓商品拦截）");
      // orderHeader.setJdBuyerMessage("（分仓商品拦截）");
      // }
      // orderHeader.setOrderFlg(0L);
      // }
      // 分仓临时对应 0709 end JD暂不对应YYYYYYYY要对应就发出来

      // 临时对应(订单金额大于1000，且订单地址内包含’ABC’字样)刷单start
      // if (StringUtil.hasValue(orderHeader.getAddress4()) &&
      // orderHeader.getAddress4().contains("ABC")
      // && orderHeader.getPaidPrice().compareTo(new BigDecimal("1000")) == 1) {
      // orderHeader.setOrderFlg(0L);
      // if (StringUtil.hasValue(orderHeader.getCaution())) {
      // orderHeader.setCaution(orderHeader.getCaution() + "（特殊订单拦截）");
      // orderHeader.setJdBuyerMessage(orderHeader.getCaution() + "（特殊订单拦截）");
      // } else {
      // orderHeader.setCaution("（特殊订单拦截）");
      // orderHeader.setJdBuyerMessage("（特殊订单拦截）");
      // }
      // }
      // 临时对应(订单金额大于1000，且订单地址内包含’ABC’字样)刷单end

      // 发货信息
      JdShippingHeader shippingHeader = new JdShippingHeader();

      // 验证，临时设置成0
      shippingHeader.setShippingNo("0");
      shippingHeader.setOrderNo(orderHeader.getOrderNo());
      shippingHeader.setShopCode("00000000");
      shippingHeader.setCustomerCode(searchInfo.getOrderId());
      shippingHeader.setAddressNo(0L);
      shippingHeader.setAddressLastName(searchInfo.getConsigneeInfo().getFullname());
      shippingHeader.setAddressFirstName("JD");
      shippingHeader.setAddressLastNameKana("カナ");
      shippingHeader.setAddressFirstNameKana("カナ");
      shippingHeader.setPostalCode("000000");
      shippingHeader.setMobileNumber(searchInfo.getConsigneeInfo().getMobile());
      shippingHeader.setPhoneNumber(searchInfo.getConsigneeInfo().getTelephone());
      shippingHeader.setDeliveryRemark("");
      shippingHeader.setAcquiredPoint(new BigDecimal("0.00"));
      shippingHeader.setShippingCharge(new BigDecimal(searchInfo.getFreightPrice()));
      shippingHeader.setShippingChargeTaxType(2L);
      shippingHeader.setShippingChargeTaxRate(0L);
      shippingHeader.setShippingChargeTax(new BigDecimal("0.00"));
      shippingHeader.setDeliveryTypeNo(0L);
      shippingHeader.setDeliveryTypeName("平邮");
      shippingHeader.setFixedSalesStatus(0L);
      // 地址mapping
      shippingHeader = addressMappingUtil.ChangeShippingHeaderAdress(searchInfo, shippingHeader, NumUtil.toString(totalWeight));

      // 发货状态
      if (JdOrderStatus.WAIT_SELLER_STOCK_OUT.getName().equals(searchInfo.getOrderState())) {
        if (NO_DATE.equals(searchInfo.getPaymentConfirmTime()) || ZERO_DATE.equals(searchInfo.getPaymentConfirmTime())) {
          shippingHeader.setShippingStatus(ShippingStatus.NOT_READY.longValue());
        } else {
          shippingHeader.setShippingStatus(ShippingStatus.READY.longValue());
        }
      } else if (JdOrderStatus.TRADE_CANCELED.getName().equals(searchInfo.getOrderState())
          && NO_DATE.equals(searchInfo.getPaymentConfirmTime())) {
        shippingHeader.setShippingStatus(ShippingStatus.CANCELLED.longValue());
      } else if (JdOrderStatus.WAIT_GOODS_RECEIVE_CONFIRM.getName().equals(searchInfo.getOrderState())
          && !NO_DATE.equals(searchInfo.getPaymentConfirmTime())) {
        shippingHeader.setShippingStatus(ShippingStatus.READY.longValue());
      } else if (JdOrderStatus.FINISHED_L.getName().equals(searchInfo.getOrderState())) {
        shippingHeader.setShippingStatus(ShippingStatus.SHIPPED.longValue());
      } else {
        errorInfo = new JdOrderDownloadErrorInfo();
        errorInfo.setMessage("京东订单状态不正确");
        errorInfo.setJdOrderNo(searchInfo.getOrderId());
        return errorInfo;
      }

      shippingHeader.setShippingDirectDate(null);//
      shippingHeader.setShippingDate(null);//
      shippingHeader.setReturnItemLossMoney(new BigDecimal("0.00"));
      shippingHeader.setReturnItemType(0L);
      // shippingHeader.setCreatedUser(DEFAULT_USER);
      // shippingHeader.setCreatedDatetime(new Date());
      // shippingHeader.setUpdatedUser(DEFAULT_USER);
      // shippingHeader.setUpdatedDatetime(new Date());
      setUserStatus(shippingHeader);
      shippingHeader.setShippingStatusWms(0L);

      // 验证
      resultList = BeanValidator.validate(shippingHeader).getErrors();
      if (resultList.size() > 0) {
        StringBuffer buffer = new StringBuffer();
        for (ValidationResult errResult : resultList) {
          logger.error(errResult.getFormedMessage());
          buffer.append(errResult.getFormedMessage());
        }
        errorInfo = new JdOrderDownloadErrorInfo();
        errorInfo.setJdOrderNo(orderHeader.getJdOrderNo());
        errorInfo.setMessage(buffer.toString());
        return errorInfo;
      }

      // 牛奶活动临时对应start
      // boolean haveSpeCommodityFlg = false;
      // String[] tempStr = {
      // "9300639500102", "4036300669452", "0000446", "6934868301174"
      // };
      //
      // for (int l = 0; l < tempStr.length; l++) {
      // for (JdOrderDetail detail : orderDetailList) {
      // if (detail.getCommodityCode().equals(tempStr[l])) {
      // haveSpeCommodityFlg = true;
      // break;
      // }
      // }
      // if (haveSpeCommodityFlg) {
      // break;
      // }
      // }
      // if (haveSpeCommodityFlg) {
      // if (!("上海".equals(orderHeader.getAddress1()) ||
      // "江苏".equals(orderHeader.getAddress1()))) {
      // shippingHeader.setDeliveryCompanyName("宅急送");
      // shippingHeader.setDeliveryCompanyNo("D002");
      // }
      // }
      // 牛奶活动临时对应end

      // 分仓临时对应 0709 start JD暂不对应YYYYYYYY要对应就发出来
      // if (allSpeCommodityFlg) {
      // if ("广东".equals(orderHeader.getAddress1())) {
      // shippingHeader.setDeliveryCompanyName("中通");
      // shippingHeader.setDeliveryCompanyNo("D010");
      // } else if ("北京".equals(orderHeader.getAddress1()) ||
      // "河北".equals(orderHeader.getAddress1())
      // || "天津".equals(orderHeader.getAddress1())) {
      // shippingHeader.setDeliveryCompanyName("圆通");
      // shippingHeader.setDeliveryCompanyNo("D011");
      // }
      // }
      // 分仓临时对应 0709 end JD暂不对应YYYYYYYY要对应就发出来

      // 优惠列表
      List<JdCouponDetail> couponDetailList = new ArrayList<JdCouponDetail>();
      for (CouponDetail detail : searchInfo.getCouponDetailList()) {
        if (StringUtil.isNullOrEmpty(detail.getOrderId())) {
          continue;
        }
        JdCouponDetail jdDetail = new JdCouponDetail();
        // Long jdDetailNo =
        // DatabaseUtil.generateSequence(SequenceType.JD_COUPON_DETAIL_NO_SEQ);
        // 验证，临时设置成0
        jdDetail.setCouponNo(0L);
        jdDetail.setCouponPrice(new BigDecimal(detail.getCouponPrice()));
        jdDetail.setCouponType(detail.getCouponType());
        jdDetail.setOrderNo(searchInfo.getOrderId());
        jdDetail.setSkuCode(detail.getSkuId());
        // jdDetail.setCreatedUser("BATCH:0:0:0");
        // jdDetail.setCreatedDatetime(new Date());
        // jdDetail.setUpdatedUser("BATCH:0:0:0");
        // jdDetail.setUpdatedDatetime(new Date());
        setUserStatus(jdDetail);

        // 验证
        resultList = BeanValidator.validate(jdDetail).getErrors();
        if (resultList.size() > 0) {
          StringBuffer buffer = new StringBuffer();
          for (ValidationResult errResult : resultList) {
            logger.error(errResult.getFormedMessage());
            buffer.append(errResult.getFormedMessage());
          }
          errorInfo = new JdOrderDownloadErrorInfo();
          errorInfo.setJdOrderNo(orderHeader.getJdOrderNo());
          errorInfo.setMessage(buffer.toString());
          return errorInfo;
        }

        couponDetailList.add(jdDetail);

      }

      orderContainer.setOrderHeader(orderHeader);
      orderContainer.setOrderDetailList(orderDetailList);
      
      if(extraInfo.isShFlag() && extraInfo.isGzFlag()){
        JdShippingHeader partShippingHeader = (JdShippingHeader)shippingHeader.clone();  //克隆对象
        partShippingHeader.setShippingCharge(BigDecimal.ZERO);
        partShippingHeader.setIsPart("GZ");
        BigDecimal partDiscountPrice = partOrderPrice.divide(totalRetailPrice, 2, RoundingMode.DOWN).
          multiply(orderHeader.getDiscountPrice());
        partShippingHeader.setDiscountPrice(partDiscountPrice);
        partShippingHeader.setDeliveryCompanyName("中通");
        partShippingHeader.setDeliveryCompanyNo("D010");
        BigDecimal partChildPaidPrice = partOrderPrice .subtract(partDiscountPrice);
        partShippingHeader.setChildPaidPrice(partChildPaidPrice);//子订单金额
        
        
        shippingHeader.setIsPart("SH");
        shippingHeader.setDiscountPrice(orderHeader.getDiscountPrice().subtract(partDiscountPrice));
        shippingHeader.setChildPaidPrice(orderHeader.getPaidPrice().subtract(partChildPaidPrice));//子订单金额
        
        orderContainer.setShippingHeader(shippingHeader);
        orderContainer.setPartShippingHeader(partShippingHeader);
        
        //组合套装中明细编号重置
        for(JdShippingDetailContainer container:shippingDetailList){
          if(container.getCompositionList()!=null && container.getCompositionList().size()>0){
            for(int j=0;j<container.getCompositionList().size();j++){
              container.getCompositionList().get(j).setCompositionNo(NumUtil.toLong(j+""));
            }
          }
        }
        orderContainer.setShippingDetailList(shippingDetailList);
        
        //组合套装中明细编号重置
        for(JdShippingDetailContainer container:partShippingDetailList){
          if(container.getCompositionList()!=null && container.getCompositionList().size()>0){
            for(int j=0;j<container.getCompositionList().size();j++){
              container.getCompositionList().get(j).setCompositionNo(NumUtil.toLong(j+""));
            }
          }
        }
        orderContainer.setPartShippingDetailList(partShippingDetailList);
        
      }else if(extraInfo.isShFlag() && extraInfo.isBjFlag()){
        JdShippingHeader partShippingHeader = (JdShippingHeader)shippingHeader.clone();  //克隆对象
        partShippingHeader.setShippingCharge(BigDecimal.ZERO);
        partShippingHeader.setIsPart("BJ");
        //订单总金额(不应取去掉优惠金额的)
        BigDecimal partDiscountPrice = partOrderPrice.divide(totalRetailPrice, 2, RoundingMode.DOWN).multiply(orderHeader.getDiscountPrice());
        partShippingHeader.setDiscountPrice(partDiscountPrice);
        partShippingHeader.setDeliveryCompanyName("圆通");
        partShippingHeader.setDeliveryCompanyNo("D011");
        BigDecimal partChildPaidPrice = partOrderPrice .subtract(partDiscountPrice);
        partShippingHeader.setChildPaidPrice(partChildPaidPrice); //子订单金额
        
        shippingHeader.setIsPart("SH");
        shippingHeader.setDiscountPrice(orderHeader.getDiscountPrice().subtract(partDiscountPrice));
        shippingHeader.setChildPaidPrice(orderHeader.getPaidPrice().subtract(partChildPaidPrice)); //子订单金额
        
        orderContainer.setShippingHeader(shippingHeader);
        orderContainer.setPartShippingHeader(partShippingHeader);
        
        
        //组合套装中明细编号重置
        for(JdShippingDetailContainer container:shippingDetailList){
          if(container.getCompositionList()!=null && container.getCompositionList().size()>0){
            for(int j=0;j<container.getCompositionList().size();j++){
              container.getCompositionList().get(j).setCompositionNo(NumUtil.toLong(j+""));
            }
          }
        }
        orderContainer.setShippingDetailList(shippingDetailList);
        
        //组合套装中明细编号重置
        for(JdShippingDetailContainer container:partShippingDetailList){
          if(container.getCompositionList()!=null && container.getCompositionList().size()>0){
            for(int j=0;j<container.getCompositionList().size();j++){
              container.getCompositionList().get(j).setCompositionNo(NumUtil.toLong(j+""));
            }
          }
        }
        orderContainer.setPartShippingDetailList(partShippingDetailList);
        
      }else if(extraInfo.isShFlag()){
        shippingHeader.setIsPart("SH");
        orderContainer.setShippingHeader(shippingHeader);
        orderContainer.setShippingDetailList(shippingDetailList);
      }else if( extraInfo.isGzFlag()){
        shippingHeader.setIsPart("GZ");
        shippingHeader.setDeliveryCompanyName("中通");
        shippingHeader.setDeliveryCompanyNo("D010");
        
        orderContainer.setShippingHeader(shippingHeader);
        orderContainer.setShippingDetailList(partShippingDetailList);
      }else if( extraInfo.isBjFlag()){
        shippingHeader.setIsPart("BJ");
        shippingHeader.setDeliveryCompanyName("圆通");
        shippingHeader.setDeliveryCompanyNo("D011");
        
        orderContainer.setShippingHeader(shippingHeader);
        orderContainer.setShippingDetailList(partShippingDetailList);
      }
      
      orderContainer.setCouponDetailList(couponDetailList);
      orderContainer.setOrderInvoice(orderInvoice);

      orderContainer.setTotalWeight(totalWeight);
      orderContainer.setExtraInfo(extraInfo);

      errorInfo = this.getGiftOrderShippingDetail(orderContainer);

      if (errorInfo != null) {
        return errorInfo;
      }

      orderList.add(orderContainer);
    }

    // 新规或更新京东订单
    for (JdOrderContainer container : orderList) {
      JdOrderHeader orderHeader = container.getOrderHeader();
      JdShippingHeader shippingHeader = container.getShippingHeader();
      // 新规更新判断
      JdOrderHeader oh = jdService.getJdOrderHeaderByJdOrderNo(orderHeader.getCustomerCode());
      if (oh != null) {
        // 更新

        // 没有付款时间,京东最新订单状态为取消而EC原始订单状态为等待出库时EC订单状态置为取消
        if (JdOrderStatus.TRADE_CANCELED.getName().equals(orderHeader.getJdOrderStatus()) && orderHeader.getPaymentDate() == null) {
          oh.setOrderStatus(JdOrderStatus.TRADE_CANCELED.longValue());
          // oh.setUpdatedDatetime(new Date());
          // oh.setUpdatedUser(DEFAULT_USER);
          oh.setJdEndTime(orderHeader.getJdEndTime());
          oh.setJdModifiedTime(orderHeader.getJdModifiedTime());
          oh.setJdOrderStatus(orderHeader.getJdOrderStatus());
          setUserStatus(oh);
          jdService.updateJdOrderHeader(oh);

        } else if (!oh.getJdOrderStatus().equals(orderHeader.getJdOrderStatus())
            || !oh.getJdModifiedTime().equals(orderHeader.getJdModifiedTime())
            || !oh.getJdEndTime().equals(orderHeader.getJdEndTime())) {
          // oh.setUpdatedDatetime(new Date());
          // oh.setUpdatedUser(DEFAULT_USER);
          setUserStatus(oh);
          oh.setJdEndTime(orderHeader.getJdEndTime());
          oh.setJdModifiedTime(orderHeader.getJdModifiedTime());
          oh.setJdOrderStatus(orderHeader.getJdOrderStatus());
          jdService.updateJdOrderHeader(oh);
        }
        List<JdShippingHeader> shs = jdService.getJdShippingHeaderByJdOrderNo(orderHeader.getCustomerCode());
        if(shs !=null && shs.size()>0){
          for(JdShippingHeader sh:shs){
            // 更新发货信息
            if (sh.getShippingStatus() < ShippingStatus.READY.longValue()) {
              if (orderHeader.getPaymentDate() != null && JdOrderStatus.WAIT_SELLER_STOCK_OUT.getName().equals(oh.getJdOrderStatus())
                  && JdOrderStatus.WAIT_SELLER_STOCK_OUT.getName().equals(orderHeader.getJdOrderStatus())) {
                sh.setShippingStatus(ShippingStatus.READY.longValue());
                // sh.setUpdatedDatetime(new Date());
                // sh.setUpdatedUser(DEFAULT_USER);
                setUserStatus(sh);
                jdService.updateJdShippingHeader(sh);
              } else if (orderHeader.getPaymentDate() != null
                  && JdOrderStatus.WAIT_SELLER_STOCK_OUT.getName().equals(oh.getJdOrderStatus())
                  && JdOrderStatus.TRADE_CANCELED.getName().equals(orderHeader.getJdOrderStatus())) {
                sh.setShippingStatus(ShippingStatus.CANCELLED.longValue());

              }
            }
          }
        }

      } else {
        // 新规

        // 订单编号
        Long orderno = DatabaseUtil.generateSequence(SequenceType.JD_ORDER_NO_SEQ);
        String createOrderNo = "D" + orderno.toString();

        // 货编号
        Long shippingNo = DatabaseUtil.generateSequence(SequenceType.JD_SHIPPING_NO_SEQ);
        String createShipppingNo = "D" + shippingNo.toString();
        orderHeader.setOrderNo(createOrderNo);
        shippingHeader.setShippingNo(createShipppingNo);
        shippingHeader.setOrderNo(createOrderNo);

        //折单处理
        if(container.getExtraInfo().isShFlag()&&(container.getExtraInfo().isGzFlag()||container.getExtraInfo().isBjFlag())){
          shippingHeader.setChildOrderNo(createOrderNo);
          
          Long partShippingNo = DatabaseUtil.generateSequence(SequenceType.JD_SHIPPING_NO_SEQ);
          String createPartShipppingNo = "D" + partShippingNo.toString();
          
          JdShippingHeader partShippingHeader = container.getPartShippingHeader();
          partShippingHeader.setShippingNo(createPartShipppingNo);
          partShippingHeader.setOrderNo(createOrderNo);
          
          //卫星仓子订单编号
          String childOrderNo = "DZ"+createOrderNo.substring(2);
          partShippingHeader.setChildOrderNo(childOrderNo);
          
          jdService.insertJdShippingHeader(partShippingHeader);
          
          //上海仓子订单编号重置
          shippingHeader.setChildOrderNo("DS" + createOrderNo.substring(2));

          // 子订单发货信息
          int itemCount = -1;
          for (JdShippingDetailContainer detailContainer : container.getPartShippingDetailList()) {
            itemCount++;
            JdShippingDetail detail = detailContainer.getShippingDetail();
            detail.setShippingNo(createPartShipppingNo);
            detail.setShippingDetailNo(NumUtil.toLong(itemCount + ""));
            jdService.insertJdShippingDetail(detail);
            if (detailContainer.getCompositionList() != null && detailContainer.getCompositionList().size() > 0) {
              for (JdShippingDetailComposition composition : detailContainer.getCompositionList()) {
                composition.setShippingNo(createPartShipppingNo);
                composition.setShippingDetailNo(NumUtil.toLong(itemCount + ""));
                jdService.insertJdShippingDetailComposition(composition);
              }
            }
          }
        }
        
        jdService.insertJdOrderHeader(orderHeader);
        jdService.insertJdShippingHeader(shippingHeader);

        for (JdOrderDetail detail : container.getOrderDetailList()) {
          detail.setOrderNo(createOrderNo);
          jdService.insertJdOrderDetail(detail);

          // 引单处理
          CommodityHeader ch = jdService.getCommodityHeader(detail.getShopCode(), detail.getCommodityCode());
          if (StringUtil.hasValue(ch.getOriginalCommodityCode())) { // 组合
            // 拆分成单品引单
            // JdStock jdStock = jdService.loadJdStock(detail.getShopCode(),
            // detail.getCommodityCode());
            // jdStock.setAllocatedQuantity(jdStock.getAllocatedQuantity() +
            // ch.getCombinationAmount() * detail.getPurchasingAmount());
            // jdService.updateJdStock(jdStock);

            // 组合品引单
            JdStockAllocation jdStockAllocation = jdService.loadJdStockAllocation(detail.getShopCode(), detail.getCommodityCode());
            jdStockAllocation.setAllocatedQuantity(jdStockAllocation.getAllocatedQuantity() + ch.getCombinationAmount()
                * detail.getPurchasingAmount());
            jdService.updateJdStockAllocation(jdStockAllocation);

          } else if ("1".equals(NumUtil.toString(ch.getSetCommodityFlg()))) { // 套装
            // 拆分成单品引单
            // List<SetCommodityComposition> setCommodityCompositions =
            // catalogService.getSetCommodityCompositipon(detail
            // .getCommodityCode(), detail.getShopCode());

            // if (setCommodityCompositions != null) {
            // for (SetCommodityComposition composition :
            // setCommodityCompositions) {
            // JdStock js = jdService.loadJdStock(composition.getShopCode(),
            // composition.getChildCommodityCode());
            // js.setAllocatedQuantity(js.getAllocatedQuantity() +
            // detail.getPurchasingAmount());
            // jdService.updateJdStock(js);
            // }
            // }
            // 套装品引单
            JdSuitCommodity jdSuitCommodity = jdService.loadJdSuitCommodity(detail.getCommodityCode());
            jdSuitCommodity.setAllocatedQuantity(jdSuitCommodity.getAllocatedQuantity() + detail.getPurchasingAmount());
            jdService.updateJdSuitCommodity(jdSuitCommodity);

          } else { // 单品
            JdStock jdStock = jdService.loadJdStock(detail.getShopCode(), detail.getCommodityCode());
            jdStock.setAllocatedQuantity(jdStock.getAllocatedQuantity() + detail.getPurchasingAmount());
            jdService.updateJdStock(jdStock);
          }

        }
        int itemCount = -1;
        for (JdShippingDetailContainer detailContainer : container.getShippingDetailList()) {
          itemCount++;
          JdShippingDetail detail = detailContainer.getShippingDetail();
          detail.setShippingNo(createShipppingNo);
          detail.setShippingDetailNo(NumUtil.toLong(itemCount + ""));
          jdService.insertJdShippingDetail(detail);
          if (detailContainer.getCompositionList() != null && detailContainer.getCompositionList().size() > 0) {
            for (JdShippingDetailComposition composition : detailContainer.getCompositionList()) {
              composition.setShippingNo(createShipppingNo);
              composition.setShippingDetailNo(NumUtil.toLong(itemCount + ""));
              jdService.insertJdShippingDetailComposition(composition);
            }
          }
        }

        for (JdCouponDetail detail : container.getCouponDetailList()) {
          // 优惠编号
          Long jdDetailNo = DatabaseUtil.generateSequence(SequenceType.JD_COUPON_DETAIL_NO_SEQ);
          detail.setCouponNo(jdDetailNo);
          jdService.insertJdCouponDetail(detail);
        }
        if (container.getOrderInvoice() != null) {
          container.getOrderInvoice().setOrderNo(createOrderNo);
          jdService.insertJdOrderInvoice(container.getOrderInvoice());
        }

        OrderService os = ServiceLocator.getOrderService(this.getLoginInfo());
        os.performStockEvent(new StockEvent(orderHeader.getOrderNo()), StockEventType.JD);

      }
    }
    // 更新Batch时间
    UpdateJdBatchTimeTb(endDate);
    return errorInfo;

  }

  // 订单下载中封装gift明细start
  public JdOrderDownloadErrorInfo getGiftOrderShippingDetail(JdOrderContainer orderContainer) throws RuntimeException {

    CatalogService service = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());

    JdOrderHeader orderHeader = orderContainer.getOrderHeader();

    List<ValidationResult> resultList = null;

    Logger logger = Logger.getLogger(this.getClass());
    JdOrderDownloadErrorInfo errorInfo = null;

    List<List<NameValue>> gCommodityList = new ArrayList<List<NameValue>>();
    // 符合条件的赠品促销活动集合
    List<CampaignMain> cmList = service.getCampaignMainByType(orderHeader.getCreatedDatetime());
    if (cmList == null) {
      return null; // 当前没有活动
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
      int totalNum = 0; // 一个订单中符合活动的商品数量总数
      if (attrValue != null && attrValue.size() > 0) {
        for (JdOrderDetail orderDetail : orderContainer.getOrderDetailList()) {

          if (attrValue.contains(orderDetail.getSkuCode())) {
            // 20140618 hdh upadate start
            // if (cmMain.getGiftAmount() != null && cmMain.getGiftAmount() <
            // orderDetail.getPurchasingAmount()) {
            // linkCommodityNums += cmMain.getGiftAmount().intValue();
            // } else {
            // linkCommodityNums += 1;
            // }
            // 20140618 hdh update end
            totalNum += orderDetail.getPurchasingAmount();

          }
        }
      }
      // 活动关联商品数量

      // 如果最小购买数为0则赠品最多只送活动中的赠品数量

      if (totalNum > 0) {
        if ("0".equals(NumUtil.toString(cmMain.getMinCommodityNum()))) {
          linkCommodityNums = cmMain.getGiftAmount().intValue();
        } else {
          linkCommodityNums = (int) ((totalNum / cmMain.getMinCommodityNum()) * cmMain.getGiftAmount());
        }
      }
      if (linkCommodityNums != 0) {
        // 获取单个活动赠品商品信息
        CampaignDoings cds = service.getCampaignDoingsList(cmMain.getCampaignCode());
        if (cds != null && StringUtil.hasValue(cds.getAttributrValue())) {
          String[] attr = cds.getAttributrValue().split(",");
          if (attr == null || attr.length == 0) {
            continue; // 没有赠品的情况，不设定赠品信息
          }
          for (int idx = 0; idx < attr.length; idx++) {
            NameValue nv = new NameValue();
            nv.setName(attr[idx]); // 赠送礼品编号
            nv.setValue(NumUtil.toString(linkCommodityNums)); // 赠送礼品数量
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

    boolean checkOrderFlg = false; // 订单检查判断
    for (List<NameValue> gcList : gCommodityList) {
      boolean stockFlg = true;
      for (NameValue nvSku : gcList) {
        // 2014/06/12 库存更新对应 ob_李 update start
        // Query query = new
        // SimpleQuery("UPDATE STOCK SET RESERVATION_LIMIT = NULL WHERE STOCK_TMALL - ALLOCATED_TMALL >= ? AND SKU_CODE = ?",
        // nvSku.getValue(),nvSku.getName());
        Query query = new SimpleQuery(
            "SELECT COUNT(*)  FROM JD_STOCK  WHERE STOCK_QUANTITY - ALLOCATED_QUANTITY >= ? AND SKU_CODE = ?", nvSku.getValue(),
            nvSku.getName());
        // 2014/06/12 库存更新对应 ob_李 update end
        int updateCnt = 0;
        Long result = ((Long) DatabaseUtil.executeScalar(query));
        if (result != null) {
          updateCnt = result.intValue();
        }

        if (updateCnt < 1) {
          stockFlg = false;
          checkOrderFlg = true; // 库存不足，订单需要做检查
          break;
        }
        // 店铺编号、商品编号
        // Stock stock = sDao.load(apiOrderHeader.getShopCode(),
        // nvSku.getName());
        // if (stock.getStockTmall() - stock.getAllocatedTmall() -
        // NumUtil.toLong(nvSku.getValue()) < 0) {
        // stockFlg = false;
        // checkOrderFlg = true; // 库存不足，订单需要做检查
        // break;
        // }
      }
      // 礼品库存充足
      if (stockFlg) {
        for (NameValue nvSku : gcList) {
          JdOrderDetail orderDetail = new JdOrderDetail();
          JdShippingDetailContainer shippingContainer = new JdShippingDetailContainer();
          JdShippingDetail shippingDetail = new JdShippingDetail();
          CommodityHeaderDao hDao = DIContainer.getDao(CommodityHeaderDao.class);
          CommodityHeader header = hDao.load(orderHeader.getShopCode(), nvSku.getName());

          // 封装订单明细start
          orderDetail.setOrderNo(orderHeader.getOrderNo());
          orderDetail.setShopCode(orderHeader.getShopCode());
          orderDetail.setSkuCode(nvSku.getName());
          orderDetail.setCommodityCode(nvSku.getName());
          orderDetail.setCommodityName(header.getCommodityName());
          orderDetail.setPurchasingAmount(NumUtil.toLong(nvSku.getValue()));
          orderDetail.setUnitPrice(new BigDecimal(0.00d));
          orderDetail.setRetailPrice(new BigDecimal(0.00d));
          orderDetail.setRetailTax(new BigDecimal(0.00d));
          orderDetail.setCommodityTaxType(0L);
          orderDetail.setAppliedPointRate(0L);
          // tod.setTmallRefundStatus("NO_REFUND");
          // orderDetail.setOrmRowid(orderDetail.getOrmRowid());
          // orderDetail.setCreatedDatetime(orderHeader.getCreatedDatetime());
          // orderDetail.setCreatedUser("BATCH:0:0:0");
          // orderDetail.setUpdatedDatetime(orderHeader.getUpdatedDatetime());
          // orderDetail.setUpdatedUser("BATCH:0:0:0");
          setUserStatus(orderDetail);

          resultList = BeanValidator.validate(orderDetail).getErrors();
          if (resultList.size() > 0) {
            StringBuffer buffer = new StringBuffer();
            for (ValidationResult errResult : resultList) {
              logger.error(errResult.getFormedMessage());
              buffer.append(errResult.getFormedMessage());
            }
            errorInfo = new JdOrderDownloadErrorInfo();
            errorInfo.setJdOrderNo(orderHeader.getJdOrderNo());
            errorInfo.setMessage(buffer.toString());
            return errorInfo;
          }

          orderContainer.getOrderDetailList().add(orderDetail);
          // 封装订单明细end
          // 封装发货明细start
          shippingDetail.setShopCode(orderHeader.getShopCode());
          shippingDetail.setSkuCode(nvSku.getName());
          shippingDetail.setUnitPrice(new BigDecimal(0.00d));
          shippingDetail.setRetailPrice(new BigDecimal(0.00d));
          shippingDetail.setDiscountAmount(new BigDecimal(0.00d));
          shippingDetail.setDiscountPrice(new BigDecimal(0.00d));
          shippingDetail.setRetailTax(new BigDecimal(0.00d));
          shippingDetail.setPurchasingAmount(NumUtil.toLong(nvSku.getValue()));
          shippingDetail.setGiftPrice(new BigDecimal(0.00d));
          shippingDetail.setGiftTaxType(0L);
          // shippingDetail.setOrmRowid(shippingDetail.getOrmRowid());
          // shippingDetail.setCreatedDatetime(orderHeader.getCreatedDatetime());
          // shippingDetail.setCreatedUser("BATCH:0:0:0");
          // shippingDetail.setUpdatedDatetime(orderHeader.getUpdatedDatetime());
          // shippingDetail.setUpdatedUser("BATCH:0:0:0");
          setUserStatus(shippingDetail);
          shippingDetail.setShippingNo("0");// 临时
          shippingDetail.setShippingDetailNo(-1L);// 临时

          resultList = BeanValidator.validate(shippingDetail).getErrors();
          if (resultList.size() > 0) {
            StringBuffer buffer = new StringBuffer();
            for (ValidationResult errResult : resultList) {
              logger.error(errResult.getFormedMessage());
              buffer.append(errResult.getFormedMessage());
            }
            errorInfo = new JdOrderDownloadErrorInfo();
            errorInfo.setJdOrderNo(orderHeader.getJdOrderNo());
            errorInfo.setMessage(buffer.toString());
            return errorInfo;
          }

          shippingContainer.setShippingDetail(shippingDetail);

          orderContainer.getShippingDetailList().add(shippingContainer);
        }
      }
    }

    // 订单设置成未检查
    if (checkOrderFlg) {
      orderHeader.setOrderFlg(OrderFlg.NOT_CHECKED.longValue());
      orderHeader.setJdBuyerMessage("警告：赠品库存不足");
      orderHeader.setCaution("警告：赠品库存不足");

    }
    return null;
  }

  public JdBatchTime GetJdBatchTime() {
    JdService jdService = ServiceLocator.getJdService(this.getLoginInfo());
    return jdService.getJdBatchTime();
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
      datetest = DateUtil.toDateTimeString(GetJdBatchTime().getFromTime());
    } else {
      datetest = start;
    }
    Date datetest2 = DateUtil.fromString(datetest, true);
    datetest = DateUtil.toDateTimeString(DateUtil.addMinute(datetest2, -15));// return

    datetest = datetest.replace("/", "-");
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
      datetest = DateUtil.getDateTimeString();// 系统时间获取:"yyyy-MM-dd hh:mm:ss"
    } else {
      datetest = end;
    }
    Date datetest2 = DateUtil.fromString(datetest, true);
    long Time1 = (datetest2.getTime() / 1000);
    datetest2.setTime(Time1 * 1000);
    datetest = DateUtil.toDateTimeString(datetest2);// return
    datetest = datetest.replaceAll("/", "-");
    return datetest;
  }

  public void UpdateJdBatchTimeTb(String from) {
    JdService jdService = ServiceLocator.getJdService(this.getLoginInfo());
    from = from.replaceAll("-", "/");
    Date fromTime = DateUtil.fromString(from, true);
    jdService.OperateJdBatchTime(fromTime, OrderDownLoadStatus.CANDOWNLOAD.longValue());
    // serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
  }
}
