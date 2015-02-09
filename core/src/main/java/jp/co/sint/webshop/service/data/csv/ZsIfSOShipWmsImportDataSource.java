package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.dao.CustomerCardUseInfoDao;
import jp.co.sint.webshop.data.dao.FriendCouponUseHistoryDao;
import jp.co.sint.webshop.data.dao.JdOrderDetailDao;
import jp.co.sint.webshop.data.dao.JdShippingDetailDao;
import jp.co.sint.webshop.data.dao.JdShippingHeaderDao;
import jp.co.sint.webshop.data.dao.JdSuitCommodityDao;
import jp.co.sint.webshop.data.dao.NewCouponHistoryDao;
import jp.co.sint.webshop.data.dao.OrderDetailDao;
import jp.co.sint.webshop.data.dao.ShippingDetailCompositionDao;
import jp.co.sint.webshop.data.dao.TmallOrderDetailDao;
import jp.co.sint.webshop.data.dao.TmallSuitCommodityDao;
import jp.co.sint.webshop.data.domain.AdvanceLaterFlg;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.dto.CustomerCardUseInfo;
import jp.co.sint.webshop.data.dto.FriendCouponUseHistory;
import jp.co.sint.webshop.data.dto.JdOrderDetail;
import jp.co.sint.webshop.data.dto.JdOrderHeader;
import jp.co.sint.webshop.data.dto.JdShippingDetail;
import jp.co.sint.webshop.data.dto.JdShippingDetailComposition;
import jp.co.sint.webshop.data.dto.JdShippingHeader;
import jp.co.sint.webshop.data.dto.JdShippingRealityDetail;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingDetailComposition;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.ShippingRealityDetail;
import jp.co.sint.webshop.data.dto.TmallOrderDetail;
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.data.dto.TmallShippingDetail;
import jp.co.sint.webshop.data.dto.TmallShippingDetailComposition;
import jp.co.sint.webshop.data.dto.TmallShippingHeader;
import jp.co.sint.webshop.data.dto.TmallShippingRealityDetail;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.order.OrderServiceQuery;
import jp.co.sint.webshop.service.order.ZsIfSOShipImportReport;
import jp.co.sint.webshop.service.tmall.TmallCommoditySku;
import jp.co.sint.webshop.service.tmall.TmallService;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

public class ZsIfSOShipWmsImportDataSource extends SqlImportDataSource<ZsIfSOShipWmsImportCsvSchema, ZsIfSOShipWmsImportCondition> {

  WebshopConfig config = DIContainer.getWebshopConfig();

  // 订单区分
  private String lineType = "";

  // 订单编号
  private String lineOrderNo = "";

  // 退货区分
  private boolean returnType = false;

  // 订单是否已取消过
  private boolean cancelFlg = false;

  // 套装区分
  private boolean compositionFlg = false;
  
  // JD拆单区分
  private boolean jdCharOrderFlg = false;

  // 发货Header
  private ShippingHeader shippingHeader = null;

  // 订单Header
  private OrderHeader orderHeader = null;

  // 发货Detail
  private ShippingDetail shippingDetail = null;

  // 发货Detail
  private ShippingDetailComposition shippingDetailComposition = null;

  // 发货Tmall_Header
  private TmallShippingHeader tmallShippingHeader = null;

  // 订单Tmall_Header
  private TmallOrderHeader tmallOrderHeader = null;

  // 发货Tmall_Detail
  private TmallShippingDetail tmallShippingDetail = null;

  // 发货Jd_Header
  private JdShippingHeader jdShippingHeader = null;

  // 订单Jd_Header
  private JdOrderHeader jdOrderHeader = null;

  // 发货Jd_Detail
  private JdShippingDetail jdShippingDetail = null;

  // 发货商品组数
  private TmallShippingDetailComposition tmallShippingDetailComposition = null;

  private JdShippingDetailComposition jdShippingDetailComposition = null;

  private PreparedStatement insertShippingRealityDetailStatement = null;

  private PreparedStatement insertTmallShippingRealityDetailStatement = null;

  private PreparedStatement insertJdShippingRealityDetailStatement = null;

  private PreparedStatement insertShippingHeaderStatement = null;

  private PreparedStatement insertShippingDetailStatement = null;

  private PreparedStatement updateECOrderStatusStatement = null;

  private PreparedStatement updateTmallOrderStatusStatement = null;

  private PreparedStatement updateJdOrderStatusStatement = null;

  private PreparedStatement updateECShipStatusStatement = null;

  private PreparedStatement updateTmallShipStatusStatement = null;

  private PreparedStatement updateJdShipStatusStatement = null;
  
  private PreparedStatement updateJdCharShipStatusStatement = null;

  private PreparedStatement updateECStockStatement = null;

  private PreparedStatement updateTmallStockStatement = null;

  private PreparedStatement updateJdStockStatement = null;

  private PreparedStatement updateTmallStockAllocationStatement = null;

  private PreparedStatement updateJdStockAllocationStatement = null;

  private PreparedStatement updateTmallStockSuitStatement = null;

  private PreparedStatement updateJdStockSuitStatement = null;

  private PreparedStatement updateCustomerCardUseInfoStatement = null;

  private PreparedStatement updateNewCouponHistoryStatement = null;

  private PreparedStatement updateFriendCouponUseHistoryStatement = null;

  // EC发货实际明细表
  private static final String SHIPPING_REALITY_DETAIL = DatabaseUtil.getTableName(ShippingRealityDetail.class);

  // Tmall发货实际明细表
  private static final String TMALL_SHIPPING_REALITY_DETAIL = DatabaseUtil.getTableName(TmallShippingRealityDetail.class);

  // JD发货实际明细表
  private static final String JD_SHIPPING_REALITY_DETAIL = DatabaseUtil.getTableName(JdShippingRealityDetail.class);

  // EC发货Header表
  private static final String SHIPPING_HEADER = DatabaseUtil.getTableName(ShippingHeader.class);

  // EC发货Detail表
  private static final String SHIPPING_DETAIL = DatabaseUtil.getTableName(ShippingDetail.class);

  // Tmall发货Header表
  private static final String TMALL_SHIPPING_HEADER = DatabaseUtil.getTableName(TmallShippingHeader.class);

  // Tmall发货Detail表
  private static final String TMALL_SHIPPING_DETAIL = DatabaseUtil.getTableName(TmallShippingDetail.class);

  // JD发货Header表
  private static final String JD_SHIPPING_HEADER = DatabaseUtil.getTableName(JdShippingHeader.class);

  // JD发货Detail表
  private static final String JD_SHIPPING_DETAIL = DatabaseUtil.getTableName(JdShippingDetail.class);

  // EC 更新订单状态为取消
  private String updateECOrderStatusSql = "UPDATE ORDER_HEADER SET ORDER_STATUS = ?, UPDATED_USER = ? , UPDATED_DATETIME = ?"
      + " WHERE ORDER_NO = ? ";

  // Tmall 更新订单状态为取消
  private String updateTmallOrderStatusSql = "UPDATE TMALL_ORDER_HEADER SET ORDER_STATUS = ?, UPDATED_USER = ? , UPDATED_DATETIME = ?"
      + " WHERE ORDER_NO = ? ";

  // JD 更新订单状态为取消
  private String updateJdOrderStatusSql = "UPDATE JD_ORDER_HEADER SET ORDER_STATUS = ?, UPDATED_USER = ? , UPDATED_DATETIME = ?"
      + " WHERE ORDER_NO = ? ";

  // EC 更新发货状态为取消
  private String updateECShipStatusSql = "UPDATE SHIPPING_HEADER SET SHIPPING_STATUS = ?, UPDATED_USER = ? , UPDATED_DATETIME = ?"
      + " WHERE SHIPPING_NO = ? ";

  // Tmall 更新发货状态为取消
  private String updateTmallShipStatusSql = "UPDATE TMALL_SHIPPING_HEADER SET SHIPPING_STATUS = ?, UPDATED_USER = ? , UPDATED_DATETIME = ?"
      + " WHERE SHIPPING_NO = ? ";

  // JD 更新发货状态为取消
  private String updateJdShipStatusSql = "UPDATE JD_SHIPPING_HEADER SET SHIPPING_STATUS = ?, UPDATED_USER = ? , UPDATED_DATETIME = ?"
      + " WHERE SHIPPING_NO = ? ";
  
  // 更新JD拆单订单发货状态为发货完毕
  private String updateJdCharShipStatusSql = "UPDATE JD_SHIPPING_HEADER SET SHIPPING_STATUS = ?,SHIPPING_DATE = ?, UPDATED_USER = ? , UPDATED_DATETIME = ?, DELIVERY_SLIP_NO = ?"
      + " WHERE ORDER_NO = ? ";

  // EC 更新库存引当
  private String updateECStockSql = "UPDATE STOCK SET ALLOCATED_QUANTITY = ALLOCATED_QUANTITY - ?, UPDATED_USER = ? , "
      + " UPDATED_DATETIME = ?  WHERE SKU_CODE = ? ";

  // Tmall 更新库存引当
  private String updateTmallStockSql = "UPDATE TMALL_STOCK SET ALLOCATED_QUANTITY = ALLOCATED_QUANTITY - ?, UPDATED_USER = ? , "
      + " UPDATED_DATETIME = ?  WHERE SKU_CODE = ? ";

  // JD 更新库存引当
  private String updateJdStockSql = "UPDATE JD_STOCK SET ALLOCATED_QUANTITY = ALLOCATED_QUANTITY - ?, UPDATED_USER = ? , "
      + " UPDATED_DATETIME = ?  WHERE SKU_CODE = ? ";
  
  // JD 拆单子订单减单品库存
  private String updateJdCharStockSql = "UPDATE JD_STOCK SET ALLOCATED_QUANTITY = ALLOCATED_QUANTITY - ?,STOCK_QUANTITY = STOCK_QUANTITY - ?, UPDATED_USER = ? , "
      + " UPDATED_DATETIME = ?  WHERE SKU_CODE = ? ";

  // 组合品引当恢复
  private String updateTmallStockAllocationSql = "UPDATE TMALL_STOCK_ALLOCATION SET ALLOCATED_QUANTITY = ALLOCATED_QUANTITY - ?, UPDATED_USER = ? , "
      + " UPDATED_DATETIME = ?  WHERE SKU_CODE = ? AND ORIGINAL_COMMODITY_CODE = ? ";

  // 组合品引当恢复
  private String updateJdStockAllocationSql = "UPDATE JD_STOCK_ALLOCATION SET ALLOCATED_QUANTITY = ALLOCATED_QUANTITY - ?, UPDATED_USER = ? , "
      + " UPDATED_DATETIME = ?  WHERE SKU_CODE = ? AND ORIGINAL_COMMODITY_CODE = ? ";
  
  // JD 拆单子订单减组合品库存
  private String updateJdCharStockAllocationSql = "UPDATE JD_STOCK_ALLOCATION SET ALLOCATED_QUANTITY = ALLOCATED_QUANTITY - ?,STOCK_QUANTITY = STOCK_QUANTITY - ?, UPDATED_USER = ? , "
      + " UPDATED_DATETIME = ?  WHERE SKU_CODE = ? AND ORIGINAL_COMMODITY_CODE = ? ";

  // 套装品引当恢复
  private String updateTmallStockSuitSql = "UPDATE TMALL_SUIT_COMMODITY SET ALLOCATED_QUANTITY = ALLOCATED_QUANTITY - ?, UPDATED_USER = ? , "
      + " UPDATED_DATETIME = ?  WHERE COMMODITY_CODE = ? ";

  // JD 套装品引当恢复
  private String updateJdStockSuitSql = "UPDATE JD_SUIT_COMMODITY SET ALLOCATED_QUANTITY = ALLOCATED_QUANTITY - ?, UPDATED_USER = ? , "
      + " UPDATED_DATETIME = ?  WHERE COMMODITY_CODE = ? ";
  
  // JD 拆单子订单减套装品库存
  private String updateJdCharStockSuitSql = "UPDATE JD_SUIT_COMMODITY SET ALLOCATED_QUANTITY = ALLOCATED_QUANTITY - ?, STOCK_QUANTITY = STOCK_QUANTITY - ?, UPDATED_USER = ? , "
      + " UPDATED_DATETIME = ?  WHERE COMMODITY_CODE = ? ";

  // 订单发货前取消时礼品卡使用金额取消（恢复礼品卡金额）:EC 专用
  private String updateCustomerCardUseInfoSql = "UPDATE CUSTOMER_CARD_USE_INFO SET USE_STATUS = ?,  UPDATED_USER = ?, UPDATED_DATETIME = ? "
      + " WHERE CUSTOMER_CODE = ? AND ORDER_NO = ? AND CARD_ID = ? ";

  // 如果发行过优惠券,则将原发行优惠券取消 (COUPON_STATUS = 2 表示优惠券状态为取消) EC 专用
  private String updateNewCouponHistorySql = "UPDATE NEW_COUPON_HISTORY SET COUPON_STATUS = 2,UPDATED_USER = ?,UPDATED_DATETIME = ? WHERE ISSUE_ORDER_NO = ? AND COUPON_ISSUE_NO = ? ";

  // 如果使用过朋友介绍的优惠券时,则将朋友介绍优惠券状态设为【 POINT_STATUS 2：无效】EC 专用
  private String updateFriendCouponUseHistorySql = "UPDATE FRIEND_COUPON_USE_HISTORY SET POINT_STATUS = 2, UPDATED_USER = ?,UPDATED_DATETIME = ? WHERE ORDER_NO = ? AND CUSTOMER_CODE = ? ";

  // 发货实际数据接收类
  private ZsIfSOShipImportReport zsIfSOShipImportReport = null;

  private Logger logger = Logger.getLogger(this.getClass());

  @Override
  protected void initializeResources() {
    logger.debug("INSERT SHIPPING_REALITY_DETAIL : " + getInsertShippingRealityDetailSql());
    logger.debug("INSERT TMALL_SHIPPING_REALITY_DETAIL : " + getInsertTmallShippingRealityDetailSql());
    logger.debug("INSERT JD_SHIPPING_REALITY_DETAIL : " + getInsertJdShippingRealityDetailSql());
    try {
      insertShippingRealityDetailStatement = createPreparedStatement(getInsertShippingRealityDetailSql());
      insertTmallShippingRealityDetailStatement = createPreparedStatement(getInsertTmallShippingRealityDetailSql());
      insertJdShippingRealityDetailStatement = createPreparedStatement(getInsertJdShippingRealityDetailSql());
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  @Override
  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      cancelFlg = false;
      returnType = false;

      // 导入文件数据转换成实体Bean对象
      zsIfSOShipImportReport = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), ZsIfSOShipImportReport.class);

      if (zsIfSOShipImportReport.getSkuCode() != null) {
        // 是优惠SKU,运费SKU,积分SKU时返回
        if (zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(0))
            || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(1))
            || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(2))
            || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(3))
            || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(4))
            || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(5))
            || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(6))
            || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(7))
            || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(8))) {
          return summary;
        }
      }
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }
    List<String> errorMessageList = new ArrayList<String>();
    // 验证字段长度
    checkItemLength(zsIfSOShipImportReport, errorMessageList);
    // 验证发货数是否为空
    if (zsIfSOShipImportReport.getShippingAmount() == null) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
              .getString("service.data.csv.ZsIfSOShipErpImportDataSource.6"))));
      return summary;
    }
    // 验证发货日是否为空
    if (zsIfSOShipImportReport.getShippingDate() == null) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
              .getString("service.data.csv.ZsIfSOShipErpImportDataSource.7"))));
      return summary;
    }

    // 验证信息
    if (zsIfSOShipImportReport.getShippingAmount() < 0) {
      lineOrderNo = zsIfSOShipImportReport.getCancelOrderNo();
      if (StringUtil.isNullOrEmpty(lineOrderNo)) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
                .getString("service.data.csv.ZsIfSOShipErpImportDataSource.13"))));
        return summary;
      }
    } else {
      lineOrderNo = zsIfSOShipImportReport.getOrderNo();
      if (StringUtil.isNullOrEmpty(lineOrderNo)) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
                .getString("service.data.csv.ZsIfSOShipErpImportDataSource.14"))));
        return summary;
      }
    }
    // 确认是EC订单OR淘宝订单
    lineType = getTmallOrEcOrder(lineOrderNo);

    if (lineType.equals("EC")) {
      // 根据订单编号给发货Header实体类赋值
      SimpleQuery shippingQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(ShippingHeader.class)
          + " WHERE (RETURN_ITEM_TYPE <> 1 OR RETURN_ITEM_TYPE IS NULL) AND ORDER_NO = ? ");
      shippingQuery.setParameters(lineOrderNo);
      try {
        shippingHeader = loadAsBean(shippingQuery, ShippingHeader.class);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (shippingHeader == null || StringUtil.isNullOrEmpty(shippingHeader.getShippingNo())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
                .getString("service.data.csv.ZsIfSOShipErpImportDataSource.16"))));
        return summary;
      }

      // 判断导入明细中的sku是否是套装商品、组合商品
      SimpleQuery shipDetailCompositionQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(ShippingDetailComposition.class)
          + " WHERE SHIPPING_NO = ? AND CHILD_SKU_CODE = ?");
      shipDetailCompositionQuery.setParameters(shippingHeader.getShippingNo(), zsIfSOShipImportReport.getSkuCode());
      try {
        shippingDetailComposition = loadAsBean(shipDetailCompositionQuery, ShippingDetailComposition.class);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (shippingDetailComposition != null && StringUtil.hasValue(shippingDetailComposition.getShippingNo())) {
        compositionFlg = true;
      } else {
        compositionFlg = false;
      }

      // 根据订单编号给发货Detail实体类赋值
      SimpleQuery shipDetailQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(ShippingDetail.class)
          + " WHERE SHIPPING_NO = ? AND SKU_CODE = ?");
      if (compositionFlg) {
        shipDetailQuery.setParameters(shippingHeader.getShippingNo(), shippingDetailComposition.getParentSkuCode());
      } else {
        shipDetailQuery.setParameters(shippingHeader.getShippingNo(), zsIfSOShipImportReport.getSkuCode());
      }
      try {
        shippingDetail = loadAsBean(shipDetailQuery, ShippingDetail.class);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (shippingDetail == null || StringUtil.isNullOrEmpty(shippingDetail.getShippingNo())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
                .getString("service.data.csv.ZsIfSOShipErpImportDataSource.16"))));
        return summary;
      }

      // 20121227 yyq update start
      // 判断导入明细中的sku是否是套装商品
      // SimpleQuery shipDetailCompositionQuery = new
      // SimpleQuery(DatabaseUtil.getSelectAllQuery(ShippingDetailComposition.class)
      // + " WHERE SHIPPING_NO = ? AND CHILD_SKU_CODE = ?");
      // shipDetailCompositionQuery.setParameters(shippingHeader.getShippingNo(),
      // zsIfSOShipImportReport.getSkuCode());
      // try {
      // shippingDetailComposition = loadAsBean(shipDetailCompositionQuery,
      // ShippingDetailComposition.class);
      // } catch (Exception e) {
      // e.printStackTrace();
      // }
      // if (shippingDetailComposition == null ||
      // StringUtil.isNullOrEmpty(shippingDetailComposition.getShippingNo())) {
      // // 根据订单编号给发货Detail实体类赋值
      // SimpleQuery shipDetailQuery = new
      // SimpleQuery(DatabaseUtil.getSelectAllQuery(ShippingDetail.class)
      // + " WHERE SHIPPING_NO = ? AND SKU_CODE = ?");
      // shipDetailQuery.setParameters(shippingHeader.getShippingNo(),
      // zsIfSOShipImportReport.getSkuCode());
      // try {
      // shippingDetail = loadAsBean(shipDetailQuery, ShippingDetail.class);
      // } catch (Exception e) {
      // e.printStackTrace();
      // }
      // if (shippingDetail == null ||
      // StringUtil.isNullOrEmpty(shippingDetail.getShippingNo())) {
      // summary.getErrors().add(
      // new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST,
      // Messages
      // .getString("service.data.csv.ZsIfSOShipErpImportDataSource.16"))));
      // return summary;
      // }
      // } else {
      // compositionFlg = true;
      // }
      // 20121227 yyq update end

      // 根据订单编号，判断是否存在该订单
      SimpleQuery orderCountQuery = new SimpleQuery("SELECT COUNT(*) FROM ORDER_HEADER WHERE ORDER_NO = ? ");
      orderCountQuery.setParameters(shippingHeader.getOrderNo());
      Long orderCount = Long.valueOf(executeScalar(orderCountQuery).toString());
      if (orderCount == 0L) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
                .getString("service.data.csv.ZsIfSOShipErpImportDataSource.8"))));
        return summary;
      }
      // 根据订单号给订单Header实体类赋值
      SimpleQuery orderQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(OrderHeader.class) + " WHERE ORDER_NO = ? ");
      orderQuery.setParameters(shippingHeader.getOrderNo());
      orderHeader = loadAsBean(orderQuery, OrderHeader.class);

      // 如果订单发货完毕，则不能取消
      if (shippingHeader.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())
          && zsIfSOShipImportReport.getShippingAmount() == 0) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.18"),
            "ORDER_STATUS"));
      }

      // 导入取消数据已经存在，则报错
      // SimpleQuery existImportQuery = new SimpleQuery(
      // "SELECT COUNT(*) FROM SHIPPING_REALITY_DETAIL WHERE SHIPPING_AMOUNT = 0 AND ORDER_NO = ? AND SKU_CODE = ?");
      // existImportQuery.setParameters(orderHeader.getOrderNo(),
      // zsIfSOShipImportReport.getSkuCode());
      // Long existNums =
      // Long.valueOf(executeScalar(existImportQuery).toString());
      // if (existNums != 0L) {
      // //errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.20"),
      // "SKU"));
      // }

      // if(shippingHeader.getShippingStatus() ==
      // ShippingStatus.CANCELLED.longValue()){}

      // 如果之前导入过部分sku并保存成功了，订单再取消则报错
      SimpleQuery isImportQuery = new SimpleQuery(
          "SELECT COUNT(*) FROM SHIPPING_REALITY_DETAIL WHERE SHIPPING_AMOUNT > 0 AND ORDER_NO = ? ");
      isImportQuery.setParameters(orderHeader.getOrderNo());
      Long existCount = Long.valueOf(executeScalar(isImportQuery).toString());
      if (existCount != 0L && zsIfSOShipImportReport.getShippingAmount() == 0) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.19"),
            "ORDER_STATUS"));
      }

      // 如果订单已经取消，那么取消标志则为true
      SimpleQuery isCelQuery = new SimpleQuery(
          "SELECT COUNT(*) FROM SHIPPING_REALITY_DETAIL WHERE SHIPPING_AMOUNT = 0 AND ORDER_NO = ? ");
      isCelQuery.setParameters(orderHeader.getOrderNo());
      Long existCelCount = Long.valueOf(executeScalar(isCelQuery).toString());
      if (existCelCount != 0L && zsIfSOShipImportReport.getShippingAmount() == 0) {
        cancelFlg = true;
      }
      if (orderHeader.getOrderStatus() == 2) {
        cancelFlg = true;
      }

      // 如果是预约订单、假预约、假订单均报错
      if (orderHeader.getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPING_IS_RESERVED)));
      }
      if (orderHeader.getOrderStatus().equals(OrderStatus.PHANTOM_ORDER.longValue())
          || orderHeader.getOrderStatus().equals(OrderStatus.PHANTOM_RESERVATION.longValue())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.SHIPPING_IS_PHANTOM, shippingHeader.getShippingNo())));
      }
      // 如果发货状态是0或者1时，则报错
      if ((shippingHeader.getShippingStatus().equals(ShippingStatus.NOT_READY.longValue()) || shippingHeader.getShippingStatus()
          .equals(ShippingStatus.READY.longValue()))) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPING_IS_NOT_IN_PROCESSING)));
      }
      // 发货日如果在订单日之前则报错
      if (zsIfSOShipImportReport.getShippingDate() != null) {
        if (zsIfSOShipImportReport.getShippingDate().before(DateUtil.truncateDate(orderHeader.getOrderDatetime()))) {
          summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPINGDATE_BEFORE_ORDERDATE)));
        }
      }
      // 设置日期最大值最小值范围，判断发货日是否为空且是否在范围内
      String min = Integer.toString(DIContainer.getWebshopConfig().getApplicationMinYear());
      String max = Integer.toString(2099);
      if (!DateUtil.isCorrectAppDate(zsIfSOShipImportReport.getShippingDate(), false)) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.NOT_IN_RANGE, Messages
                .getString("service.data.csv.ZsIfSOShipErpImportDataSource.9"), min, max)));
      }

      // 如果已经支付且发货状态是未支付则报错
      if (orderHeader.getAdvanceLaterFlg().equals(AdvanceLaterFlg.ADVANCE.longValue())) {
        if (shippingHeader.getShippingStatus().equals(ShippingStatus.NOT_READY.longValue())) {
          summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NO_PAY_AND_PAYMENT_ADVANCE)));
        }
      }
      // 确定销售状态，如是已完成则报错
      if (shippingHeader.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.FIXED_DATA)));
      }

      // 业务验证
      if (zsIfSOShipImportReport.getShippingAmount() < 0) {
        lineOrderNo = zsIfSOShipImportReport.getCancelOrderNo();
        returnType = true;
        // 退货时查询的发货状态应为发货完毕
        if (!(shippingHeader.getShippingStatus().toString()).equals(ShippingStatus.SHIPPED.getValue())) {
          errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.10"),
              "SHIPPING_STATUS"));
        }

        // 组合商品、套装商品 UPDATE START
        // 查询该sku的发货数量信息
        // SimpleQuery shipNumQuery = new SimpleQuery(
        // "select sd.purchasing_amount from shipping_header sh inner join shipping_detail sd on sh.shipping_no = sd.shipping_no "
        // +
        // "where (sh.RETURN_ITEM_TYPE <> 1 OR sh.RETURN_ITEM_TYPE IS NULL) and sh.shipping_status = 3 and order_no = ? and sd.sku_code = ?  ");
        // shipNumQuery.setParameters(lineOrderNo,
        // zsIfSOShipImportReport.getSkuCode());
        // Object objOne = executeScalar(shipNumQuery);
        // Long shipNum = 0L;
        // if (objOne != null) {
        // shipNum = Long.valueOf(objOne.toString());
        // }
        //
        // SimpleQuery shipNumCompositionQuery = new SimpleQuery(
        // "select sum(sd.purchasing_amount) from shipping_header sh  "
        // +
        // " inner join shipping_detail sd on sh.shipping_no = sd.shipping_no  "
        // +
        // " inner join shipping_detail_composition sdc on sh.shipping_no = sdc.shipping_no and sdc.shipping_detail_no = sd.shipping_detail_no "
        // +
        // " where (sh.RETURN_ITEM_TYPE <> 1 OR sh.RETURN_ITEM_TYPE IS NULL) and sh.shipping_status = 3 and order_no = ? and sdc.child_sku_code = ? ");
        // shipNumCompositionQuery.setParameters(lineOrderNo,
        // zsIfSOShipImportReport.getSkuCode());
        // Object objThree = executeScalar(shipNumCompositionQuery);
        // if (objThree != null) {
        // shipNum = shipNum + Long.valueOf(objThree.toString());
        // }

        String numSql = "SELECT SUM(CASE WHEN SDC.CHILD_SKU_CODE IS NOT NULL THEN SDC.PURCHASING_AMOUNT ELSE SD.PURCHASING_AMOUNT END) "
            + " FROM SHIPPING_HEADER SH INNER JOIN SHIPPING_DETAIL SD ON SH.SHIPPING_NO = SD.SHIPPING_NO "
            + " LEFT JOIN SHIPPING_DETAIL_COMPOSITION SDC ON SD.SHIPPING_NO = SDC.SHIPPING_NO AND SD.SHIPPING_DETAIL_NO = SDC.SHIPPING_DETAIL_NO "
            + " WHERE (SH.RETURN_ITEM_TYPE <> 1 OR SH.RETURN_ITEM_TYPE IS NULL) AND SH.SHIPPING_STATUS = 3 AND ORDER_NO = ? AND "
            + " (CASE WHEN SDC.CHILD_SKU_CODE IS NOT NULL THEN SDC.CHILD_SKU_CODE = ? ELSE SD.SKU_CODE = ? END)";
        SimpleQuery shipNumQuery = new SimpleQuery(numSql);
        shipNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode(), zsIfSOShipImportReport.getSkuCode());
        Object objOne = executeScalar(shipNumQuery);
        Long shipNum = 0L;
        if (objOne != null) {
          shipNum = Long.valueOf(objOne.toString());
        }
        if (shipNum == 0) {
          errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.11"),
              lineOrderNo));
        }
        // 组合商品、套装商品 UPDATE END

        SimpleQuery returnNumQuery = new SimpleQuery(
            "SELECT SUM(SD.PURCHASING_AMOUNT) FROM SHIPPING_HEADER SH INNER JOIN SHIPPING_DETAIL SD ON SH.SHIPPING_NO = SD.SHIPPING_NO "
                + "WHERE SH.RETURN_ITEM_TYPE = 1 AND SH.SHIPPING_STATUS = 3 AND ORDER_NO = ? AND SD.SKU_CODE = ?  ");
        returnNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode());
        Object objTwo = executeScalar(returnNumQuery);
        Long returnNum = 0L;
        if (objTwo != null) {
          returnNum = Long.valueOf(objTwo.toString());
        }
        long totalNum = shipNum + returnNum + zsIfSOShipImportReport.getShippingAmount();
        if (totalNum < 0) {
          errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.12"),
              "SHIPPING_AMOUNT"));
        }

      } else {
        if (!cancelFlg) {
          lineOrderNo = zsIfSOShipImportReport.getOrderNo();
          returnType = false;
          // 套装、组合商品对应 START
          // 查询该sku的发货数量信息在发货明细中
          // SimpleQuery shipNumQuery = new SimpleQuery(
          // "select sd.purchasing_amount from shipping_header sh inner join shipping_detail sd on sh.shipping_no = sd.shipping_no "
          // +
          // "where (sh.RETURN_ITEM_TYPE <> 1 OR sh.RETURN_ITEM_TYPE IS NULL) and sh.shipping_status = 2 and order_no = ? and sd.sku_code = ?  ");
          // shipNumQuery.setParameters(lineOrderNo,
          // zsIfSOShipImportReport.getSkuCode());
          // Object objOne = executeScalar(shipNumQuery);
          // Long shipNum = 0L;
          // if (objOne != null) {
          // shipNum = Long.valueOf(objOne.toString());
          // }
          //
          // SimpleQuery shipNumCompositionQuery = new SimpleQuery(
          // "select sum(sd.purchasing_amount) from shipping_header sh  "
          // +
          // " inner join shipping_detail sd on sh.shipping_no = sd.shipping_no  "
          // +
          // " inner join shipping_detail_composition sdc on sh.shipping_no = sdc.shipping_no and sdc.shipping_detail_no = sd.shipping_detail_no "
          // +
          // " where (sh.RETURN_ITEM_TYPE <> 1 OR sh.RETURN_ITEM_TYPE IS NULL) and sh.shipping_status = 2 and order_no = ? and sdc.child_sku_code = ? ");
          // shipNumCompositionQuery.setParameters(lineOrderNo,
          // zsIfSOShipImportReport.getSkuCode());
          // Object objThree = executeScalar(shipNumCompositionQuery);
          // if (objThree != null) {
          // shipNum = shipNum + Long.valueOf(objThree.toString());
          // }

          String numSql = "SELECT SUM(CASE WHEN SD.SET_COMMODITY_FLG = '1' THEN SDC.PURCHASING_AMOUNT ELSE SD.PURCHASING_AMOUNT END) "
              + " FROM SHIPPING_HEADER SH INNER JOIN SHIPPING_DETAIL SD ON SH.SHIPPING_NO = SD.SHIPPING_NO "
              + " LEFT JOIN SHIPPING_DETAIL_COMPOSITION SDC ON SD.SHIPPING_NO = SDC.SHIPPING_NO AND SD.SHIPPING_DETAIL_NO = SDC.SHIPPING_DETAIL_NO "
              + " WHERE (SH.RETURN_ITEM_TYPE <> 1 OR SH.RETURN_ITEM_TYPE IS NULL) AND SH.SHIPPING_STATUS = 2 AND ORDER_NO = ? AND "
              + " (CASE WHEN SD.SET_COMMODITY_FLG = '1' THEN SDC.CHILD_SKU_CODE = ? ELSE SD.SKU_CODE = ? END)";
          SimpleQuery shipNumQuery = new SimpleQuery(numSql);
          shipNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode(), zsIfSOShipImportReport.getSkuCode());
          Object objOne = executeScalar(shipNumQuery);
          Long shipNum = 0L;
          if (objOne != null) {
            shipNum = Long.valueOf(objOne.toString());
          } else {
            errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.17"),
                lineOrderNo));
          }
          // 套装、组合商品对应 END

          if (shipNum == 0) {
            errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.17"),
                lineOrderNo));
          }

          // 查询发货实际明细表中的数量和
          SimpleQuery srNumQuery = new SimpleQuery(
              "SELECT SUM(SHIPPING_AMOUNT) FROM SHIPPING_REALITY_DETAIL WHERE SHIPPING_AMOUNT > 0 AND ORDER_NO = ? AND SKU_CODE = ? ");
          srNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode());
          Object objTwo = executeScalar(srNumQuery);
          Long srNum = 0L;
          if (objTwo != null) {
            srNum = Long.valueOf(objTwo.toString());
          }
          // 计算sku的历史合计
          Long totalNum = srNum + zsIfSOShipImportReport.getShippingAmount();
          // 如果大于发货数据 则报错
          if (totalNum > shipNum) {
            errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.15"),
                "SHIPPING_AMOUNT"));
          }
        }
      }
    } else if (lineType.equals("TMALL")) {
      // 根据订单编号给发货Header实体类赋值
      SimpleQuery shippingQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(TmallShippingHeader.class)
          + " WHERE (RETURN_ITEM_TYPE <> 1 OR RETURN_ITEM_TYPE IS NULL) AND ORDER_NO = ? ");
      shippingQuery.setParameters(lineOrderNo);
      try {
        tmallShippingHeader = loadAsBean(shippingQuery, TmallShippingHeader.class);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (tmallShippingHeader == null || StringUtil.isNullOrEmpty(tmallShippingHeader.getShippingNo())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
                .getString("service.data.csv.ZsIfSOShipErpImportDataSource.16"))));
        return summary;
      }

      // 判断导入明细中的sku是否是套装商品、组合商品
      SimpleQuery tmallshipDetailCompositionQuery = new SimpleQuery(DatabaseUtil
          .getSelectAllQuery(TmallShippingDetailComposition.class)
          + " WHERE SHIPPING_NO = ? AND CHILD_SKU_CODE = ?");
      tmallshipDetailCompositionQuery.setParameters(tmallShippingHeader.getShippingNo(), zsIfSOShipImportReport.getSkuCode());
      try {
        tmallShippingDetailComposition = loadAsBean(tmallshipDetailCompositionQuery, TmallShippingDetailComposition.class);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (tmallShippingDetailComposition != null && StringUtil.hasValue(tmallShippingDetailComposition.getShippingNo())) {
        compositionFlg = true;
      } else {
        compositionFlg = false;
      }

      // 根据订单编号给发货Detail实体类赋值
      SimpleQuery shipDetailQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(TmallShippingDetail.class)
          + " WHERE SHIPPING_NO = ? AND SKU_CODE = ?");
      if (compositionFlg) {
        shipDetailQuery.setParameters(tmallShippingHeader.getShippingNo(), tmallShippingDetailComposition.getParentSkuCode());
      } else {
        shipDetailQuery.setParameters(tmallShippingHeader.getShippingNo(), zsIfSOShipImportReport.getSkuCode());
      }
      try {
        tmallShippingDetail = loadAsBean(shipDetailQuery, TmallShippingDetail.class);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (tmallShippingDetail == null || StringUtil.isNullOrEmpty(tmallShippingDetail.getShippingNo())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
                .getString("service.data.csv.ZsIfSOShipErpImportDataSource.16"))));
        return summary;
      }

      // 根据订单编号，判断是否存在该订单
      SimpleQuery orderCountQuery = new SimpleQuery("SELECT COUNT(*) FROM TMALL_ORDER_HEADER WHERE ORDER_NO = ? ");
      orderCountQuery.setParameters(tmallShippingHeader.getOrderNo());
      Long orderCount = Long.valueOf(executeScalar(orderCountQuery).toString());
      if (orderCount == 0L) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
                .getString("service.data.csv.ZsIfSOShipErpImportDataSource.8"))));
        return summary;
      }
      // 根据订单号给订单Header实体类赋值
      SimpleQuery orderQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(TmallOrderHeader.class) + " WHERE ORDER_NO = ? ");
      orderQuery.setParameters(tmallShippingHeader.getOrderNo());
      tmallOrderHeader = loadAsBean(orderQuery, TmallOrderHeader.class);

      // 导入取消数据已经存在，则报错
      // SimpleQuery existImportQuery = new SimpleQuery(
      // "SELECT COUNT(*) FROM TMALL_SHIPPING_REALITY_DETAIL WHERE SHIPPING_AMOUNT = 0 AND ORDER_NO = ? AND SKU_CODE = ?");
      // existImportQuery.setParameters(tmallOrderHeader.getOrderNo(),
      // zsIfSOShipImportReport.getSkuCode());
      // Long existNums =
      // Long.valueOf(executeScalar(existImportQuery).toString());
      // if (existNums != 0L) {
      // errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.20"),
      // "SKU"));
      // }

      // 如果订单发货完毕，则不能取消
      if (tmallShippingHeader.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())
          && zsIfSOShipImportReport.getShippingAmount() == 0) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.18"),
            "ORDER_STATUS"));
      }

      // 如果之前导入过部分sku并保存成功了，订单再取消则报错
      SimpleQuery isImportQuery = new SimpleQuery(
          "SELECT COUNT(*) FROM TMALL_SHIPPING_REALITY_DETAIL WHERE SHIPPING_AMOUNT > 0 AND ORDER_NO = ? ");
      isImportQuery.setParameters(tmallOrderHeader.getOrderNo());
      Long existCount = Long.valueOf(executeScalar(isImportQuery).toString());
      if (existCount != 0L && zsIfSOShipImportReport.getShippingAmount() == 0) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.19"),
            "ORDER_STATUS"));
      }

      // 如果订单已经取消，那么取消标志则为true
      SimpleQuery isCelQuery = new SimpleQuery(
          "SELECT COUNT(*) FROM TMALL_SHIPPING_REALITY_DETAIL WHERE SHIPPING_AMOUNT = 0 AND ORDER_NO = ? ");
      isCelQuery.setParameters(tmallOrderHeader.getOrderNo());
      Long existCelCount = Long.valueOf(executeScalar(isCelQuery).toString());
      if (existCelCount != 0L && zsIfSOShipImportReport.getShippingAmount() == 0) {
        cancelFlg = true;
      }
      if (tmallOrderHeader.getOrderStatus() == OrderStatus.CANCELLED.longValue()) {
        cancelFlg = true;
      }

      // 如果是预约订单、假预约、假订单均报错
      if (tmallOrderHeader.getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPING_IS_RESERVED)));
      }
      if (tmallOrderHeader.getOrderStatus().equals(OrderStatus.PHANTOM_ORDER.longValue())
          || tmallOrderHeader.getOrderStatus().equals(OrderStatus.PHANTOM_RESERVATION.longValue())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.SHIPPING_IS_PHANTOM, shippingHeader.getShippingNo())));
      }

      // 如果发货状态是0或者1时，则报错
      if ((tmallShippingHeader.getShippingStatus().equals(ShippingStatus.NOT_READY.longValue()) || tmallShippingHeader
          .getShippingStatus().equals(ShippingStatus.READY.longValue()))) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPING_IS_NOT_IN_PROCESSING)));
      }

      // 发货日如果在订单日之前则报错
      if (zsIfSOShipImportReport.getShippingDate() != null) {
        if (zsIfSOShipImportReport.getShippingDate().before(DateUtil.truncateDate(tmallOrderHeader.getOrderDatetime()))) {
          summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPINGDATE_BEFORE_ORDERDATE)));
        }
      }
      // 设置日期最大值最小值范围，判断发货日是否为空且是否在范围内
      String min = Integer.toString(DIContainer.getWebshopConfig().getApplicationMinYear());
      String max = Integer.toString(2099);
      if (!DateUtil.isCorrectAppDate(zsIfSOShipImportReport.getShippingDate(), false)) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.NOT_IN_RANGE, Messages
                .getString("service.data.csv.ZsIfSOShipErpImportDataSource.9"), min, max)));
      }
      // 如果已经支付且发货状态是未支付则报错
      if (tmallOrderHeader.getAdvanceLaterFlg().equals(AdvanceLaterFlg.ADVANCE.longValue())) {
        if (tmallShippingHeader.getShippingStatus().equals(ShippingStatus.NOT_READY.longValue())) {
          summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NO_PAY_AND_PAYMENT_ADVANCE)));
        }
      }
      // 确定销售状态，如是已完成则报错
      if (tmallShippingHeader.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.FIXED_DATA)));
      }

      // 业务验证
      if (zsIfSOShipImportReport.getShippingAmount() < 0) {
        lineOrderNo = zsIfSOShipImportReport.getCancelOrderNo();
        returnType = true;
        // 退货时查询的发货状态应为发货完毕
        if (!(tmallShippingHeader.getShippingStatus().toString()).equals(ShippingStatus.SHIPPED.getValue())) {
          errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.10"),
              "SHIPPING_STATUS"));
        }

        // 组合商品、套装商品 UPDATE START
        // 查询该sku的发货数量信息
        // SimpleQuery shipNumQuery = new SimpleQuery(
        // "select sd.purchasing_amount from tmall_shipping_header sh inner join tmall_shipping_detail sd on sh.shipping_no = sd.shipping_no "
        // +
        // "where (sh.RETURN_ITEM_TYPE <> 1 OR sh.RETURN_ITEM_TYPE IS NULL) and sh.shipping_status = 3 and order_no = ? and sd.sku_code = ?  ");
        // shipNumQuery.setParameters(lineOrderNo,
        // zsIfSOShipImportReport.getSkuCode());

        String numSql = "SELECT SUM(CASE WHEN SDC.CHILD_SKU_CODE IS NOT NULL THEN SDC.PURCHASING_AMOUNT ELSE SD.PURCHASING_AMOUNT END) "
            + " FROM TMALL_SHIPPING_HEADER SH INNER JOIN TMALL_SHIPPING_DETAIL SD ON SH.SHIPPING_NO = SD.SHIPPING_NO "
            + " LEFT JOIN TMALL_SHIPPING_DETAIL_COMPOSITION SDC ON SD.SHIPPING_NO = SDC.SHIPPING_NO AND SD.SHIPPING_DETAIL_NO = SDC.SHIPPING_DETAIL_NO "
            + " WHERE (SH.RETURN_ITEM_TYPE <> 1 OR SH.RETURN_ITEM_TYPE IS NULL) AND SH.SHIPPING_STATUS = 3 AND ORDER_NO = ? AND "
            + " (CASE WHEN SDC.CHILD_SKU_CODE IS NOT NULL THEN SDC.CHILD_SKU_CODE = ? ELSE SD.SKU_CODE = ? END)";
        SimpleQuery shipNumQuery = new SimpleQuery(numSql);
        shipNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode(), zsIfSOShipImportReport.getSkuCode());
        // 组合商品、套装商品 UPDATE END

        Object objOne = executeScalar(shipNumQuery);
        Long shipNum = 0L;
        if (objOne != null) {
          shipNum = Long.valueOf(objOne.toString());
        } else {
          errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.11"),
              "SHIPPING_HEADER"));
        }
        SimpleQuery returnNumQuery = new SimpleQuery(
            "SELECT SUM(SD.PURCHASING_AMOUNT) FROM TMALL_SHIPPING_HEADER SH INNER JOIN TMALL_SHIPPING_DETAIL SD ON SH.SHIPPING_NO = SD.SHIPPING_NO "
                + "WHERE SH.RETURN_ITEM_TYPE = 1 AND SH.SHIPPING_STATUS = 3 AND ORDER_NO = ? AND SD.SKU_CODE = ?");
        returnNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode());
        Object objTwo = executeScalar(returnNumQuery);
        Long returnNum = 0L;
        if (objTwo != null) {
          returnNum = Long.valueOf(objTwo.toString());
        }
        long totalNum = shipNum + returnNum + zsIfSOShipImportReport.getShippingAmount();
        if (totalNum < 0) {
          errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.12"),
              "SHIPPING_AMOUNT"));
        }
      } else {
        if (!cancelFlg) {
          lineOrderNo = zsIfSOShipImportReport.getOrderNo();
          returnType = false;

          // 组合商品、套装商品 UPDATE START
          // 查询该sku的发货数量信息
          // SimpleQuery shipNumQuery = new SimpleQuery(
          // "select sd.purchasing_amount from tmall_shipping_header sh inner join tmall_shipping_detail sd on sh.shipping_no = sd.shipping_no "
          // +
          // "where (sh.RETURN_ITEM_TYPE <> 1 OR sh.RETURN_ITEM_TYPE IS NULL) and sh.shipping_status = 2 and order_no = ? and sd.sku_code = ?  ");
          // shipNumQuery.setParameters(lineOrderNo,
          // zsIfSOShipImportReport.getSkuCode());

          String numSql = "SELECT SUM(CASE WHEN SDC.CHILD_SKU_CODE IS NOT NULL THEN SDC.PURCHASING_AMOUNT ELSE SD.PURCHASING_AMOUNT END) "
              + " FROM TMALL_SHIPPING_HEADER SH INNER JOIN TMALL_SHIPPING_DETAIL SD ON SH.SHIPPING_NO = SD.SHIPPING_NO "
              + " LEFT JOIN TMALL_SHIPPING_DETAIL_COMPOSITION SDC ON SD.SHIPPING_NO = SDC.SHIPPING_NO AND SD.SHIPPING_DETAIL_NO = SDC.SHIPPING_DETAIL_NO "
              + " WHERE (SH.RETURN_ITEM_TYPE <> 1 OR SH.RETURN_ITEM_TYPE IS NULL) AND SH.SHIPPING_STATUS = 2 AND ORDER_NO = ? AND "
              + " (CASE WHEN SDC.CHILD_SKU_CODE IS NOT NULL THEN SDC.CHILD_SKU_CODE = ? ELSE SD.SKU_CODE = ? END)";
          SimpleQuery shipNumQuery = new SimpleQuery(numSql);
          shipNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode(), zsIfSOShipImportReport.getSkuCode());
          // 组合商品、套装商品 UPDATE END

          Object objOne = executeScalar(shipNumQuery);
          Long shipNum = 0L;
          if (objOne != null) {
            shipNum = Long.valueOf(objOne.toString());
          } else {
            errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.17"),
                "SKU_CODE"));
          }
          // 查询发货实际明细表中的数量和
          SimpleQuery srNumQuery = new SimpleQuery(
              "SELECT SUM(SHIPPING_AMOUNT) FROM TMALL_SHIPPING_REALITY_DETAIL WHERE SHIPPING_AMOUNT > 0 AND ORDER_NO = ? AND SKU_CODE = ?");
          srNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode());
          Object objTwo = executeScalar(srNumQuery);
          Long srNum = 0L;
          if (objTwo != null) {
            srNum = Long.valueOf(objTwo.toString());
          }
          // 计算sku的历史合计
          Long totalNum = srNum + zsIfSOShipImportReport.getShippingAmount();
          // 如果大于发货数据 则报错
          if (totalNum > shipNum) {
            errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.15"),
                "SHIPPING_AMOUNT"));
          }
        }
      }
      // 订单为JD时
    } else {
      
      // 是否为拆单订单判断
      SimpleQuery jdCheckQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(JdShippingHeader.class)
          + " WHERE (RETURN_ITEM_TYPE <> 1 OR RETURN_ITEM_TYPE IS NULL) AND CHILD_ORDER_NO = ? ");
      jdCheckQuery.setParameters(lineOrderNo);
      try {
        jdShippingHeader = loadAsBean(jdCheckQuery, JdShippingHeader.class);
      } catch (Exception e) {
        e.printStackTrace();
      }
      
      // 是拆单订单
      if (jdShippingHeader != null && StringUtil.hasValue(jdShippingHeader.getShippingNo())) {
        // 拆单flg设定为true
        jdCharOrderFlg = true;
        // 如果订单已经发货完成或者取消则不能更新发货状态
        if (jdShippingHeader.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())
            || jdShippingHeader.getShippingStatus().equals(ShippingStatus.CANCELLED.longValue())) {
          summary.getErrors().add(new ValidationResult(null, null, jdShippingHeader.getOrderNo() + ":已发货或者取消无需更新发货状态"));
          return summary;
        }
        // 如果发货状态是0或者1时，则报错
        if ((jdShippingHeader.getShippingStatus().equals(ShippingStatus.NOT_READY.longValue()) || jdShippingHeader
            .getShippingStatus().equals(ShippingStatus.READY.longValue()))) {
          summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPING_IS_NOT_IN_PROCESSING)));
        }
      } else {
        // 非拆单订单
        // 拆单flg设定为false
        jdCharOrderFlg = false;
        
        // 根据订单编号给发货Header实体类赋值
        SimpleQuery shippingQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(JdShippingHeader.class)
            + " WHERE (RETURN_ITEM_TYPE <> 1 OR RETURN_ITEM_TYPE IS NULL) AND ORDER_NO = ? ");
        shippingQuery.setParameters(lineOrderNo);
        try {
          jdShippingHeader = loadAsBean(shippingQuery, JdShippingHeader.class);
        } catch (Exception e) {
          e.printStackTrace();
        }
        if (jdShippingHeader == null || StringUtil.isNullOrEmpty(jdShippingHeader.getShippingNo())) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
                  .getString("service.data.csv.ZsIfSOShipErpImportDataSource.16"))));
          return summary;
        }

        // 判断导入明细中的sku是否是套装商品、组合商品
        SimpleQuery jdshipDetailCompositionQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(JdShippingDetailComposition.class)
            + " WHERE SHIPPING_NO = ? AND CHILD_SKU_CODE = ?");
        jdshipDetailCompositionQuery.setParameters(jdShippingHeader.getShippingNo(), zsIfSOShipImportReport.getSkuCode());
        try {
          jdShippingDetailComposition = loadAsBean(jdshipDetailCompositionQuery, JdShippingDetailComposition.class);
        } catch (Exception e) {
          e.printStackTrace();
        }
        if (jdShippingDetailComposition != null && StringUtil.hasValue(jdShippingDetailComposition.getShippingNo())) {
          compositionFlg = true;
        } else {
          compositionFlg = false;
        }

        // 根据订单编号给发货Detail实体类赋值
        SimpleQuery shipDetailQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(JdShippingDetail.class)
            + " WHERE SHIPPING_NO = ? AND SKU_CODE = ?");
        if (compositionFlg) {
          shipDetailQuery.setParameters(jdShippingHeader.getShippingNo(), jdShippingDetailComposition.getParentSkuCode());
        } else {
          shipDetailQuery.setParameters(jdShippingHeader.getShippingNo(), zsIfSOShipImportReport.getSkuCode());
        }
        try {
          jdShippingDetail = loadAsBean(shipDetailQuery, JdShippingDetail.class);
        } catch (Exception e) {
          e.printStackTrace();
        }
        if (jdShippingDetail == null || StringUtil.isNullOrEmpty(jdShippingDetail.getShippingNo())) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
                  .getString("service.data.csv.ZsIfSOShipErpImportDataSource.16"))));
          return summary;
        }

        // 根据订单编号，判断是否存在该订单
        SimpleQuery orderCountQuery = new SimpleQuery("SELECT COUNT(*) FROM JD_ORDER_HEADER WHERE ORDER_NO = ? ");
        orderCountQuery.setParameters(jdShippingHeader.getOrderNo());
        Long orderCount = Long.valueOf(executeScalar(orderCountQuery).toString());
        if (orderCount == 0L) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
                  .getString("service.data.csv.ZsIfSOShipErpImportDataSource.8"))));
          return summary;
        }
        // 根据订单号给订单Header实体类赋值
        SimpleQuery orderQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(JdOrderHeader.class) + " WHERE ORDER_NO = ? ");
        orderQuery.setParameters(jdShippingHeader.getOrderNo());
        jdOrderHeader = loadAsBean(orderQuery, JdOrderHeader.class);

        // 导入取消数据已经存在，则报错
        // SimpleQuery existImportQuery = new SimpleQuery(
        // "SELECT COUNT(*) FROM TMALL_SHIPPING_REALITY_DETAIL WHERE SHIPPING_AMOUNT = 0 AND ORDER_NO = ? AND SKU_CODE = ?");
        // existImportQuery.setParameters(tmallOrderHeader.getOrderNo(),
        // zsIfSOShipImportReport.getSkuCode());
        // Long existNums =
        // Long.valueOf(executeScalar(existImportQuery).toString());
        // if (existNums != 0L) {
        // errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.20"),
        // "SKU"));
        // }

        // 如果订单发货完毕，则不能取消
        if (jdShippingHeader.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())
            && zsIfSOShipImportReport.getShippingAmount() == 0) {
          errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.18"),
              "ORDER_STATUS"));
        }

        // 如果之前导入过部分sku并保存成功了，订单再取消则报错
        SimpleQuery isImportQuery = new SimpleQuery(
            "SELECT COUNT(*) FROM JD_SHIPPING_REALITY_DETAIL WHERE SHIPPING_AMOUNT > 0 AND ORDER_NO = ? ");
        isImportQuery.setParameters(jdOrderHeader.getOrderNo());
        Long existCount = Long.valueOf(executeScalar(isImportQuery).toString());
        if (existCount != 0L && zsIfSOShipImportReport.getShippingAmount() == 0) {
          errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.19"),
              "ORDER_STATUS"));
        }

        // 如果订单已经取消，那么取消标志则为true
        SimpleQuery isCelQuery = new SimpleQuery(
            "SELECT COUNT(*) FROM JD_SHIPPING_REALITY_DETAIL WHERE SHIPPING_AMOUNT = 0 AND ORDER_NO = ? ");
        isCelQuery.setParameters(jdOrderHeader.getOrderNo());
        Long existCelCount = Long.valueOf(executeScalar(isCelQuery).toString());
        if (existCelCount != 0L && zsIfSOShipImportReport.getShippingAmount() == 0) {
          cancelFlg = true;
        }
        if (jdOrderHeader.getOrderStatus() == OrderStatus.CANCELLED.longValue()) {
          cancelFlg = true;
        }

        // 如果是预约订单、假预约、假订单均报错
        if (jdOrderHeader.getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
          summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPING_IS_RESERVED)));
        }
        if (jdOrderHeader.getOrderStatus().equals(OrderStatus.PHANTOM_ORDER.longValue())
            || jdOrderHeader.getOrderStatus().equals(OrderStatus.PHANTOM_RESERVATION.longValue())) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.SHIPPING_IS_PHANTOM, jdShippingHeader.getShippingNo())));
        }

        // 如果发货状态是0或者1时，则报错
        if ((jdShippingHeader.getShippingStatus().equals(ShippingStatus.NOT_READY.longValue()) || jdShippingHeader
            .getShippingStatus().equals(ShippingStatus.READY.longValue()))) {
          summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPING_IS_NOT_IN_PROCESSING)));
        }

        // 发货日如果在订单日之前则报错
        if (zsIfSOShipImportReport.getShippingDate() != null) {
          if (zsIfSOShipImportReport.getShippingDate().before(DateUtil.truncateDate(jdOrderHeader.getOrderDatetime()))) {
            summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPINGDATE_BEFORE_ORDERDATE)));
          }
        }
        // 设置日期最大值最小值范围，判断发货日是否为空且是否在范围内
        String min = Integer.toString(DIContainer.getWebshopConfig().getApplicationMinYear());
        String max = Integer.toString(2099);
        if (!DateUtil.isCorrectAppDate(zsIfSOShipImportReport.getShippingDate(), false)) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.NOT_IN_RANGE, Messages
                  .getString("service.data.csv.ZsIfSOShipErpImportDataSource.9"), min, max)));
        }
        // 如果已经支付且发货状态是未支付则报错
        if (jdOrderHeader.getAdvanceLaterFlg().equals(AdvanceLaterFlg.ADVANCE.longValue())) {
          if (jdShippingHeader.getShippingStatus().equals(ShippingStatus.NOT_READY.longValue())) {
            summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NO_PAY_AND_PAYMENT_ADVANCE)));
          }
        }
        // 确定销售状态，如是已完成则报错
        if (jdShippingHeader.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue())) {
          summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.FIXED_DATA)));
        }

        // 业务验证
        if (zsIfSOShipImportReport.getShippingAmount() < 0) {
          lineOrderNo = zsIfSOShipImportReport.getCancelOrderNo();
          returnType = true;
          // 退货时查询的发货状态应为发货完毕
          if (!(jdShippingHeader.getShippingStatus().toString()).equals(ShippingStatus.SHIPPED.getValue())) {
            errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.10"),
                "SHIPPING_STATUS"));
          }

          // 组合商品、套装商品 UPDATE START
          // 查询该sku的发货数量信息
          // SimpleQuery shipNumQuery = new SimpleQuery(
          // "select sd.purchasing_amount from jd_shipping_header sh inner join jd_shipping_detail sd on sh.shipping_no = sd.shipping_no "
          // +
          // "where (sh.RETURN_ITEM_TYPE <> 1 OR sh.RETURN_ITEM_TYPE IS NULL) and sh.shipping_status = 3 and order_no = ? and sd.sku_code = ?  ");
          // shipNumQuery.setParameters(lineOrderNo,
          // zsIfSOShipImportReport.getSkuCode());

          String numSql = "SELECT SUM(CASE WHEN SDC.CHILD_SKU_CODE IS NOT NULL THEN SDC.PURCHASING_AMOUNT ELSE SD.PURCHASING_AMOUNT END) "
              + " FROM JD_SHIPPING_HEADER SH INNER JOIN JD_SHIPPING_DETAIL SD ON SH.SHIPPING_NO = SD.SHIPPING_NO "
              + " LEFT JOIN JD_SHIPPING_DETAIL_COMPOSITION SDC ON SD.SHIPPING_NO = SDC.SHIPPING_NO AND SD.SHIPPING_DETAIL_NO = SDC.SHIPPING_DETAIL_NO "
              + " WHERE (SH.RETURN_ITEM_TYPE <> 1 OR SH.RETURN_ITEM_TYPE IS NULL) AND SH.SHIPPING_STATUS = 3 AND ORDER_NO = ? AND "
              + " (CASE WHEN SDC.CHILD_SKU_CODE IS NOT NULL THEN SDC.CHILD_SKU_CODE = ? ELSE SD.SKU_CODE = ? END)";
          SimpleQuery shipNumQuery = new SimpleQuery(numSql);
          shipNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode(), zsIfSOShipImportReport.getSkuCode());
          // 组合商品、套装商品 UPDATE END

          Object objOne = executeScalar(shipNumQuery);
          Long shipNum = 0L;
          if (objOne != null) {
            shipNum = Long.valueOf(objOne.toString());
          } else {
            errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.11"),
                "SHIPPING_HEADER"));
          }
          SimpleQuery returnNumQuery = new SimpleQuery(
              "SELECT SUM(SD.PURCHASING_AMOUNT) FROM JD_SHIPPING_HEADER SH INNER JOIN JD_SHIPPING_DETAIL SD ON SH.SHIPPING_NO = SD.SHIPPING_NO "
                  + "WHERE SH.RETURN_ITEM_TYPE = 1 AND SH.SHIPPING_STATUS = 3 AND ORDER_NO = ? AND SD.SKU_CODE = ?");
          returnNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode());
          Object objTwo = executeScalar(returnNumQuery);
          Long returnNum = 0L;
          if (objTwo != null) {
            returnNum = Long.valueOf(objTwo.toString());
          }
          long totalNum = shipNum + returnNum + zsIfSOShipImportReport.getShippingAmount();
          if (totalNum < 0) {
            errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.12"),
                "SHIPPING_AMOUNT"));
          }
        } else {
          if (!cancelFlg) {
            lineOrderNo = zsIfSOShipImportReport.getOrderNo();
            returnType = false;

            // 组合商品、套装商品 UPDATE START
            // 查询该sku的发货数量信息
            // SimpleQuery shipNumQuery = new SimpleQuery(
            // "select sd.purchasing_amount from jd_shipping_header sh inner join jd_shipping_detail sd on sh.shipping_no = sd.shipping_no "
            // +
            // "where (sh.RETURN_ITEM_TYPE <> 1 OR sh.RETURN_ITEM_TYPE IS NULL) and sh.shipping_status = 2 and order_no = ? and sd.sku_code = ?  ");
            // shipNumQuery.setParameters(lineOrderNo,
            // zsIfSOShipImportReport.getSkuCode());

            String numSql = "SELECT SUM(CASE WHEN SDC.CHILD_SKU_CODE IS NOT NULL THEN SDC.PURCHASING_AMOUNT ELSE SD.PURCHASING_AMOUNT END) "
                + " FROM JD_SHIPPING_HEADER SH INNER JOIN JD_SHIPPING_DETAIL SD ON SH.SHIPPING_NO = SD.SHIPPING_NO "
                + " LEFT JOIN JD_SHIPPING_DETAIL_COMPOSITION SDC ON SD.SHIPPING_NO = SDC.SHIPPING_NO AND SD.SHIPPING_DETAIL_NO = SDC.SHIPPING_DETAIL_NO "
                + " WHERE (SH.RETURN_ITEM_TYPE <> 1 OR SH.RETURN_ITEM_TYPE IS NULL) AND SH.SHIPPING_STATUS = 2 AND ORDER_NO = ? AND "
                + " (CASE WHEN SDC.CHILD_SKU_CODE IS NOT NULL THEN SDC.CHILD_SKU_CODE = ? ELSE SD.SKU_CODE = ? END)";
            SimpleQuery shipNumQuery = new SimpleQuery(numSql);
            shipNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode(), zsIfSOShipImportReport.getSkuCode());
            // 组合商品、套装商品 UPDATE END

            Object objOne = executeScalar(shipNumQuery);
            Long shipNum = 0L;
            if (objOne != null) {
              shipNum = Long.valueOf(objOne.toString());
            } else {
              errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.17"),
                  "SKU_CODE"));
            }
            // 查询发货实际明细表中的数量和
            SimpleQuery srNumQuery = new SimpleQuery(
                "SELECT SUM(SHIPPING_AMOUNT) FROM JD_SHIPPING_REALITY_DETAIL WHERE SHIPPING_AMOUNT > 0 AND ORDER_NO = ? AND SKU_CODE = ?");
            srNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode());
            Object objTwo = executeScalar(srNumQuery);
            Long srNum = 0L;
            if (objTwo != null) {
              srNum = Long.valueOf(objTwo.toString());
            }
            // 计算sku的历史合计
            Long totalNum = srNum + zsIfSOShipImportReport.getShippingAmount();
            // 如果大于发货数据 则报错
            if (totalNum > shipNum) {
              errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.15"),
                  "SHIPPING_AMOUNT"));
            }
          }
        }
      }
    }
    for (String error : errorMessageList) {
      summary.getErrors().add(new ValidationResult(null, null, error));
    }
    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());
    try {
      // 是优惠SKU,运费SKU,积分SKU时返回
      if (zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(0))
          || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(1))
          || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(2))
          || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(3))
          || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(4))
          || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(5))
          || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(6))
          || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(7))
          || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(8))) {
        return;
      }
      // if-EC订单 else-淘宝订单
      if (zsIfSOShipImportReport.getShippingAmount() == 0L && !cancelFlg) {
        zsIfSOShipImportReport.setDealflg(1L);
      }
      if (lineType.equals("EC")) {
        if (!returnType) {
          int insertCount = executeInsertShippingRealityDetail();
          if (insertCount != 1) {
            throw new CsvImportException();
          }
          // 如果导入数量为0 取消订单
          if (zsIfSOShipImportReport.getShippingAmount() == 0L && !cancelFlg) {
            // 订单状态
            int updateCount = executeUpdateOrderStatus(orderHeader.getOrderNo(), "EC");
            if (updateCount != 1) {
              throw new CsvImportException();
            }
            // 发货状态
            int updateNum = executeUpdateShippingStatus(shippingHeader.getShippingNo(), "EC");
            if (updateNum != 1) {
              throw new CsvImportException();
            }
            // 恢复引当
            int celNum = executeUpdateStockCancel(orderHeader.getOrderNo(), "EC");
            if (celNum != 0) {
              throw new CsvImportException();
            }
            // 礼品卡使用取消
            // int giftNum = executeUpdateGiftCancel(orderHeader.getOrderNo());
            // if (giftNum != 0) {
            // throw new CsvImportException();
            // }
            // 购买发行优惠券取消
            int couponNum = executeUpdateNewCouponCancel(orderHeader.getOrderNo());
            if (couponNum != 0) {
              throw new CsvImportException();
            }
            // 朋友介绍优惠券状态设为【2：无效】
            int friendCouponNum = executeUpdateFriendCouponUseCancel(orderHeader.getOrderNo(), orderHeader.getCustomerCode());
            if (friendCouponNum != 0) {
              throw new CsvImportException();
            }
          }
        }
      } else if (lineType.equals("TMALL")) {
        if (!returnType) {
          int insertCount = executeInsertTmallShippingRealityDetail();
          if (insertCount != 1) {
            throw new CsvImportException();
          }
          // 如果导入数量为0 取消订单
          if (zsIfSOShipImportReport.getShippingAmount() == 0L && !cancelFlg) {
            int updateCount = executeUpdateOrderStatus(tmallOrderHeader.getOrderNo(), "TMALL");
            if (updateCount != 1) {
              throw new CsvImportException();
            }
            int updateNum = executeUpdateShippingStatus(tmallShippingHeader.getShippingNo(), "TMALL");
            if (updateNum != 1) {
              throw new CsvImportException();
            }
            int celNum = executeUpdateStockCancel(tmallOrderHeader.getOrderNo(), "TMALL");
            if (celNum != 0) {
              throw new CsvImportException();
            }
          }
        }
      } else {
        // 非JD拆单
        if (!jdCharOrderFlg) {
          if (!returnType) {
            int insertCount = executeInsertJdShippingRealityDetail();
            if (insertCount != 1) {
              throw new CsvImportException();
            }
            // 如果导入数量为0 取消订单
            if (zsIfSOShipImportReport.getShippingAmount() == 0L && !cancelFlg) {
              int updateCount = executeUpdateOrderStatus(jdOrderHeader.getOrderNo(), "JD");
              if (updateCount != 1) {
                throw new CsvImportException();
              }
              int updateNum = executeUpdateShippingStatus(jdShippingHeader.getShippingNo(), "JD");
              if (updateNum != 1) {
                throw new CsvImportException();
              }
              int celNum = executeUpdateStockCancel(jdOrderHeader.getOrderNo(), "JD");
              if (celNum != 0) {
                throw new CsvImportException();
              }
            }
          }
        } else {
          // JD拆单
          // 更新2条子订单的发货状态为发货完毕
          int updateRes = executeUpdateJdCharShippingStatus(jdShippingHeader.getOrderNo());
          if (updateRes <= 1) {
            throw new CsvImportException();
          } else {
            // 减掉JD订单的库存数和引当数
            int updateStock = jdCharStockDeal(jdShippingHeader.getOrderNo());
            if (updateStock != 0) {
              throw new CsvImportException();
            }
          }
        }
      }
      // 如果是退货
      if (returnType) {
        Long shippingNo = 0L;
        if (lineType.equals("EC")) {
          shippingNo = DatabaseUtil.generateSequence(SequenceType.SHIPPING_NO);
        } else if (lineType.equals("TMALL")) {
          shippingNo = DatabaseUtil.generateSequence(SequenceType.TMALL_SHIPPING_NO);
        } else {
          // 非JD拆单
          if (!jdCharOrderFlg) {
            shippingNo = DatabaseUtil.generateSequence(SequenceType.JD_SHIPPING_NO_SEQ);
          }
        }
        // 非JD拆单
        if (!jdCharOrderFlg) {
          int insertHeader = executeInsertShippingHeader(shippingNo.toString());
          if (insertHeader != 1) {
            throw new CsvImportException();
          } else {
            int insertDetail = executeInsertShippingDetail(shippingNo.toString());
            if (insertDetail != 1) {
              throw new CsvImportException();
            }
          }
        }
      }
    } catch (SQLException e) {
      logger.debug(e.getMessage());
      throw new CsvImportException(e);
    } catch (CsvImportException e) {
      logger.debug(e.getMessage());
      throw e;
    } catch (RuntimeException e) {
      throw new CsvImportException(e);
    }
  }

  // 插入EC发货实际明细表
  public int executeInsertShippingRealityDetail() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();

    params.add(shippingHeader.getShippingNo());
    params.add(zsIfSOShipImportReport.getOrderNo());
    params.add(config.getSiteShopCode());
    params.add(zsIfSOShipImportReport.getSkuCode());
    params.add(zsIfSOShipImportReport.getShippingAmount());
    params.add(zsIfSOShipImportReport.getDeliverySlipNo());
    params.add(zsIfSOShipImportReport.getShippingDate());
    params.add(zsIfSOShipImportReport.getDealflg());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());

    pstmt = insertShippingRealityDetailStatement;

    logger.debug("Table:SHIPPING_REALITY_DETAIL INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }

  // 插入Tmall发货实际明细表
  public int executeInsertTmallShippingRealityDetail() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();

    params.add(tmallShippingHeader.getShippingNo());
    params.add(zsIfSOShipImportReport.getOrderNo());
    params.add(config.getSiteShopCode());
    params.add(zsIfSOShipImportReport.getSkuCode());
    params.add(zsIfSOShipImportReport.getShippingAmount());
    params.add(zsIfSOShipImportReport.getDeliverySlipNo());
    params.add(zsIfSOShipImportReport.getShippingDate());
    params.add(zsIfSOShipImportReport.getDealflg());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());

    pstmt = insertTmallShippingRealityDetailStatement;

    logger.debug("Table:TMALL_SHIPPING_REALITY_DETAIL INSERT Parameters: "
        + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }

  // 插入Jd发货实际明细表
  public int executeInsertJdShippingRealityDetail() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();

    params.add(jdShippingHeader.getShippingNo());
    params.add(zsIfSOShipImportReport.getOrderNo());
    params.add(config.getSiteShopCode());
    params.add(zsIfSOShipImportReport.getSkuCode());
    params.add(zsIfSOShipImportReport.getShippingAmount());
    params.add(zsIfSOShipImportReport.getDeliverySlipNo());
    params.add(zsIfSOShipImportReport.getShippingDate());
    params.add(zsIfSOShipImportReport.getDealflg());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());

    pstmt = insertJdShippingRealityDetailStatement;

    logger.debug("Table:JD_SHIPPING_REALITY_DETAIL INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }

  // 通过判断EC/TMALL/JD插入退货信息到发货Header表
  public int executeInsertShippingHeader(String shippingNo) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    if (lineType.equals("EC")) {
      params.add(shippingNo);
      params.add(shippingHeader.getOrderNo());
      params.add(config.getSiteShopCode());
      params.add(shippingHeader.getCustomerCode());
      params.add(shippingHeader.getAddressNo());
      params.add(shippingHeader.getAddressLastName());
      params.add(shippingHeader.getAddressFirstName());
      params.add(shippingHeader.getAddressLastNameKana());
      params.add(shippingHeader.getAddressFirstNameKana());
      params.add(shippingHeader.getPostalCode());
      params.add(shippingHeader.getPrefectureCode());
      params.add(shippingHeader.getAddress1());
      params.add(shippingHeader.getAddress2());
      params.add(shippingHeader.getAddress3());
      params.add(shippingHeader.getAddress4());
      params.add(shippingHeader.getPhoneNumber());
      params.add(shippingHeader.getMobileNumber());
      params.add(shippingHeader.getDeliveryRemark());
      params.add(shippingHeader.getAcquiredPoint());
      params.add(shippingHeader.getDeliverySlipNo());
      params.add(shippingHeader.getShippingCharge());
      params.add(shippingHeader.getShippingChargeTaxType());
      params.add(shippingHeader.getShippingChargeTaxRate());
      params.add(shippingHeader.getShippingChargeTax());
      params.add(shippingHeader.getDeliveryTypeNo());
      params.add(shippingHeader.getDeliveryTypeName());
      params.add(shippingHeader.getDeliveryAppointedTimeStart());
      params.add(shippingHeader.getDeliveryAppointedTimeEnd());
      params.add(shippingHeader.getArrivalDate());
      params.add(shippingHeader.getArrivalTimeStart());
      params.add(shippingHeader.getArrivalTimeEnd());
      params.add(FixedSalesStatus.NOT_FIXED.longValue());
      params.add(shippingHeader.getShippingStatus());
      params.add(shippingHeader.getShippingDirectDate());
      params.add(shippingHeader.getShippingDate());
      params.add(shippingHeader.getShippingNo());
      params.add(DatabaseUtil.getSystemDatetime());
      params.add(shippingHeader.getReturnItemLossMoney());
      params.add(1L);
      params.add(shippingHeader.getCityCode());
      params.add(shippingHeader.getDeliveryCompanyNo());
      params.add(shippingHeader.getDeliveryCompanyName());
      params.add(shippingHeader.getDeliveryAppointedDate());
      params.add(shippingHeader.getAreaCode());
      params.add("BATCH:0:0:0");
      params.add(DateUtil.getSysdate());
      params.add("BATCH:0:0:0");
      params.add(DateUtil.getSysdate());
    } else if (lineType.equals("TMALL")) {
      params.add("T" + shippingNo);
      params.add(tmallShippingHeader.getOrderNo());
      params.add(config.getSiteShopCode());
      params.add(tmallShippingHeader.getCustomerCode());
      params.add(tmallShippingHeader.getAddressNo());
      params.add(tmallShippingHeader.getAddressLastName());
      params.add(tmallShippingHeader.getAddressFirstName());
      params.add(tmallShippingHeader.getAddressLastNameKana());
      params.add(tmallShippingHeader.getAddressFirstNameKana());
      params.add(tmallShippingHeader.getPostalCode());
      params.add(tmallShippingHeader.getPrefectureCode());
      params.add(tmallShippingHeader.getAddress1());
      params.add(tmallShippingHeader.getAddress2());
      params.add(tmallShippingHeader.getAddress3());
      params.add(tmallShippingHeader.getAddress4());
      params.add(tmallShippingHeader.getPhoneNumber());
      params.add(tmallShippingHeader.getMobileNumber());
      params.add(tmallShippingHeader.getDeliveryRemark());
      params.add(tmallShippingHeader.getAcquiredPoint());
      params.add(tmallShippingHeader.getDeliverySlipNo());
      params.add(tmallShippingHeader.getShippingCharge());
      params.add(tmallShippingHeader.getShippingChargeTaxType());
      params.add(tmallShippingHeader.getShippingChargeTaxRate());
      params.add(tmallShippingHeader.getShippingChargeTax());
      params.add(tmallShippingHeader.getDeliveryTypeNo());
      params.add(tmallShippingHeader.getDeliveryTypeName());
      params.add(tmallShippingHeader.getDeliveryAppointedTimeStart());
      params.add(tmallShippingHeader.getDeliveryAppointedTimeEnd());
      params.add(tmallShippingHeader.getArrivalDate());
      params.add(tmallShippingHeader.getArrivalTimeStart());
      params.add(tmallShippingHeader.getArrivalTimeEnd());
      params.add(FixedSalesStatus.NOT_FIXED.longValue());
      params.add(tmallShippingHeader.getShippingStatus());
      params.add(tmallShippingHeader.getShippingDirectDate());
      params.add(tmallShippingHeader.getShippingDate());
      params.add(tmallShippingHeader.getShippingNo());
      params.add(DatabaseUtil.getSystemDatetime());
      params.add(tmallShippingHeader.getReturnItemLossMoney());
      params.add(1L);
      params.add(tmallShippingHeader.getCityCode());
      params.add(tmallShippingHeader.getDeliveryCompanyNo());
      params.add(tmallShippingHeader.getDeliveryCompanyName());
      params.add(tmallShippingHeader.getDeliveryAppointedDate());
      params.add(tmallShippingHeader.getAreaCode());
      params.add("BATCH:0:0:0");
      params.add(DateUtil.getSysdate());
      params.add("BATCH:0:0:0");
      params.add(DateUtil.getSysdate());
      params.add(tmallShippingHeader.getTmallShippingFlg());
    } else {
      params.add("D" + shippingNo);
      params.add(jdShippingHeader.getOrderNo());
      params.add(config.getSiteShopCode());
      params.add(jdShippingHeader.getCustomerCode());
      params.add(jdShippingHeader.getAddressNo());
      params.add(jdShippingHeader.getAddressLastName());
      params.add(jdShippingHeader.getAddressFirstName());
      params.add(jdShippingHeader.getAddressLastNameKana());
      params.add(jdShippingHeader.getAddressFirstNameKana());
      params.add(jdShippingHeader.getPostalCode());
      params.add(jdShippingHeader.getPrefectureCode());
      params.add(jdShippingHeader.getAddress1());
      params.add(jdShippingHeader.getAddress2());
      params.add(jdShippingHeader.getAddress3());
      params.add(jdShippingHeader.getAddress4());
      params.add(jdShippingHeader.getPhoneNumber());
      params.add(jdShippingHeader.getMobileNumber());
      params.add(jdShippingHeader.getDeliveryRemark());
      params.add(jdShippingHeader.getAcquiredPoint());
      params.add(jdShippingHeader.getDeliverySlipNo());
      params.add(jdShippingHeader.getShippingCharge());
      params.add(jdShippingHeader.getShippingChargeTaxType());
      params.add(jdShippingHeader.getShippingChargeTaxRate());
      params.add(jdShippingHeader.getShippingChargeTax());
      params.add(jdShippingHeader.getDeliveryTypeNo());
      params.add(jdShippingHeader.getDeliveryTypeName());
      params.add(jdShippingHeader.getDeliveryAppointedTimeStart());
      params.add(jdShippingHeader.getDeliveryAppointedTimeEnd());
      params.add(jdShippingHeader.getArrivalDate());
      params.add(jdShippingHeader.getArrivalTimeStart());
      params.add(jdShippingHeader.getArrivalTimeEnd());
      params.add(FixedSalesStatus.NOT_FIXED.longValue());
      params.add(jdShippingHeader.getShippingStatus());
      params.add(jdShippingHeader.getShippingDirectDate());
      params.add(jdShippingHeader.getShippingDate());
      params.add(jdShippingHeader.getShippingNo());
      params.add(DatabaseUtil.getSystemDatetime());
      params.add(jdShippingHeader.getReturnItemLossMoney());
      params.add(1L);
      params.add(jdShippingHeader.getCityCode());
      params.add(jdShippingHeader.getDeliveryCompanyNo());
      params.add(jdShippingHeader.getDeliveryCompanyName());
      params.add(jdShippingHeader.getDeliveryAppointedDate());
      params.add(jdShippingHeader.getAreaCode());
      params.add("BATCH:0:0:0");
      params.add(DateUtil.getSysdate());
      params.add("BATCH:0:0:0");
      params.add(DateUtil.getSysdate());
      params.add(jdShippingHeader.getJdShippingFlg());
    }

    logger.debug("INSERT SHIPPING_HEADER|TMALL_SHIPPING_HEADER|JD_SHIPPING_HEADER : " + getInsertShippingHeaderSql());
    insertShippingHeaderStatement = createPreparedStatement(getInsertShippingHeaderSql());
    pstmt = insertShippingHeaderStatement;

    if (lineType.equals("EC")) {
      logger.debug("Table:SHIPPING_HEADER INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    } else if (lineType.equals("TMALL")) {
      logger.debug("Table:TMALL_SHIPPING_HEADER INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    } else {
      logger.debug("Table:JD_SHIPPING_HEADER INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    }

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }

  // 通过判断EC/TMALL插入退货信息到发货Detail表
  public int executeInsertShippingDetail(String shippingNo) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    if (lineType.equals("EC")) {
      params.add(shippingNo);
      params.add(config.getSiteShopCode());
      params.add(zsIfSOShipImportReport.getSkuCode());
      if (!compositionFlg) {
        params.add(shippingDetail.getUnitPrice());
        params.add(shippingDetail.getDiscountPrice());
        params.add(shippingDetail.getDiscountAmount());
        params.add(shippingDetail.getRetailPrice());
        params.add(shippingDetail.getRetailTax());
        params.add(zsIfSOShipImportReport.getShippingAmount());
        params.add(shippingDetail.getGiftCode());
        params.add(shippingDetail.getGiftName());
        params.add(shippingDetail.getGiftPrice());
        params.add(shippingDetail.getGiftTaxRate());
        params.add(shippingDetail.getGiftTax());
        params.add(shippingDetail.getGiftTaxType());
      } else {
        params.add(shippingDetailComposition.getUnitPrice());
        params.add(0.0);
        params.add(shippingDetailComposition.getDiscountAmount());
        params.add(shippingDetailComposition.getRetailPrice());
        params.add(shippingDetailComposition.getRetailTax());
        params.add(zsIfSOShipImportReport.getShippingAmount());
        params.add("");
        params.add("");
        params.add(0.0);
        params.add(0.0);
        params.add(0.0);
        params.add(0.0);
      }
      params.add(shippingDetail.getCommodityType());
      params.add("BATCH:0:0:0");
      params.add(DateUtil.getSysdate());
      params.add("BATCH:0:0:0");
      params.add(DateUtil.getSysdate());
    } else if (lineType.equals("TMALL")) {
      params.add("T" + shippingNo);
      params.add(config.getSiteShopCode());
      params.add(zsIfSOShipImportReport.getSkuCode());

      if (!compositionFlg) {
        params.add(tmallShippingDetail.getUnitPrice());
        params.add(tmallShippingDetail.getDiscountPrice());
        params.add(tmallShippingDetail.getDiscountAmount());
        params.add(tmallShippingDetail.getRetailPrice());
        params.add(tmallShippingDetail.getRetailTax());
        params.add(zsIfSOShipImportReport.getShippingAmount());
        params.add(tmallShippingDetail.getGiftCode());
        params.add(tmallShippingDetail.getGiftName());
        params.add(tmallShippingDetail.getGiftPrice());
        params.add(tmallShippingDetail.getGiftTaxRate());
        params.add(tmallShippingDetail.getGiftTax());
        params.add(tmallShippingDetail.getGiftTaxType());
      } else {
        params.add(tmallShippingDetailComposition.getUnitPrice());
        params.add(0.0);
        params.add(tmallShippingDetailComposition.getDiscountAmount());
        params.add(tmallShippingDetailComposition.getRetailPrice());
        params.add(tmallShippingDetailComposition.getRetailTax());
        params.add(zsIfSOShipImportReport.getShippingAmount());
        params.add("");
        params.add("");
        params.add(0.0);
        params.add(0.0);
        params.add(0.0);
        params.add(0.0);
      }
      // params.add(tmallShippingDetail.getUnitPrice());
      // params.add(tmallShippingDetail.getDiscountPrice());
      // params.add(tmallShippingDetail.getDiscountAmount());
      // params.add(tmallShippingDetail.getRetailPrice());
      // params.add(tmallShippingDetail.getRetailTax());
      // params.add(zsIfSOShipImportReport.getShippingAmount());
      // params.add(tmallShippingDetail.getGiftCode());
      // params.add(tmallShippingDetail.getGiftName());
      // params.add(tmallShippingDetail.getGiftPrice());
      // params.add(tmallShippingDetail.getGiftTaxRate());
      // params.add(tmallShippingDetail.getGiftTax());
      // params.add(tmallShippingDetail.getGiftTaxType());
      params.add("BATCH:0:0:0");
      params.add(DateUtil.getSysdate());
      params.add("BATCH:0:0:0");
      params.add(DateUtil.getSysdate());
    } else {
      params.add("D" + shippingNo);
      params.add(config.getSiteShopCode());
      params.add(zsIfSOShipImportReport.getSkuCode());

      if (!compositionFlg) {
        params.add(jdShippingDetail.getUnitPrice());
        params.add(jdShippingDetail.getDiscountPrice());
        params.add(jdShippingDetail.getDiscountAmount());
        params.add(jdShippingDetail.getRetailPrice());
        params.add(jdShippingDetail.getRetailTax());
        params.add(zsIfSOShipImportReport.getShippingAmount());
        params.add(jdShippingDetail.getGiftCode());
        params.add(jdShippingDetail.getGiftName());
        params.add(jdShippingDetail.getGiftPrice());
        params.add(jdShippingDetail.getGiftTaxRate());
        params.add(jdShippingDetail.getGiftTax());
        params.add(jdShippingDetail.getGiftTaxType());
      } else {
        params.add(jdShippingDetailComposition.getUnitPrice());
        params.add(0.0);
        params.add(jdShippingDetailComposition.getDiscountAmount());
        params.add(jdShippingDetailComposition.getRetailPrice());
        params.add(jdShippingDetailComposition.getRetailTax());
        params.add(zsIfSOShipImportReport.getShippingAmount());
        params.add("");
        params.add("");
        params.add(0.0);
        params.add(0.0);
        params.add(0.0);
        params.add(0.0);
      }
      params.add("BATCH:0:0:0");
      params.add(DateUtil.getSysdate());
      params.add("BATCH:0:0:0");
      params.add(DateUtil.getSysdate());
    }

    logger.debug("INSERT SHIPPING_DETAIL|TMALL_SHIPPING_DETAIL|JD_SHIPPING_DETAIL : " + getInsertShippingDetailSql());
    insertShippingDetailStatement = createPreparedStatement(getInsertShippingDetailSql());
    pstmt = insertShippingDetailStatement;

    if (lineType.equals("EC")) {
      logger.debug("Table:SHIPPING_DETAIL INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    } else if (lineType.equals("TMALL")) {
      logger.debug("Table:TMALL_SHIPPING_DETAIL INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    } else {
      logger.debug("Table:JD_SHIPPING_DETAIL INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    }

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }

  // 插入到SHIPPING_REALITY_DETAIL sql生成
  private String getInsertShippingRealityDetailSql() {
    String insertSql = "" + " INSERT INTO " + SHIPPING_REALITY_DETAIL
        + " ({0} SHIPPING_REALITY_DETAIL_NO,ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) "
        + " VALUES " + " ({1} " + SqlDialect.getDefault().getNextvalNOprm("'SHIPPING_REALITY_DETAIL_NO_SEQ'") + ","
        + SqlDialect.getDefault().getNextvalNOprm("'SHIPPING_REALITY_DETAIL_SEQ'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("SHIPPING_NO");
    columnList.add("ORDER_NO");
    columnList.add("SHOP_CODE");
    columnList.add("SKU_CODE");
    columnList.add("SHIPPING_AMOUNT");
    columnList.add("DELIVERY_SLIP_NO");
    columnList.add("SHIPPING_DATE");
    columnList.add("DEAL_FLG");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  // 插入到TMALL_SHIPPING_REALITY_DETAIL sql生成
  private String getInsertTmallShippingRealityDetailSql() {
    String insertSql = "" + " INSERT INTO " + TMALL_SHIPPING_REALITY_DETAIL
        + " ({0} SHIPPING_REALITY_DETAIL_NO,ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) "
        + " VALUES " + " ({1} " + SqlDialect.getDefault().getNextvalNOprm("'TMALL_SHIPPING_REALITY_DETAIL_NO_SEQ'") + ","
        + SqlDialect.getDefault().getNextvalNOprm("'TMALL_SHIPPING_REALITY_DETAIL_SEQ'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("SHIPPING_NO");
    columnList.add("ORDER_NO");
    columnList.add("SHOP_CODE");
    columnList.add("SKU_CODE");
    columnList.add("SHIPPING_AMOUNT");
    columnList.add("DELIVERY_SLIP_NO");
    columnList.add("SHIPPING_DATE");
    columnList.add("DEAL_FLG");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  // 插入到JD_SHIPPING_REALITY_DETAIL sql生成
  private String getInsertJdShippingRealityDetailSql() {
    String insertSql = "" + " INSERT INTO " + JD_SHIPPING_REALITY_DETAIL
        + " ({0} SHIPPING_REALITY_DETAIL_NO,ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) "
        + " VALUES " + " ({1} " + SqlDialect.getDefault().getNextvalNOprm("'JD_SHIPPING_REALITY_DETAIL_NO_SEQ'") + ","
        + SqlDialect.getDefault().getNextvalNOprm("'JD_SHIPPING_REALITY_DETAIL_SEQ'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("SHIPPING_NO");
    columnList.add("ORDER_NO");
    columnList.add("SHOP_CODE");
    columnList.add("SKU_CODE");
    columnList.add("SHIPPING_AMOUNT");
    columnList.add("DELIVERY_SLIP_NO");
    columnList.add("SHIPPING_DATE");
    columnList.add("DEAL_FLG");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  // 通过判断EC/TMALL生成退货SHIPPING_HEADER sql
  private String getInsertShippingHeaderSql() {
    String insertSql = "";
    if (lineType.equals("EC")) {
      insertSql = " INSERT INTO " + SHIPPING_HEADER
          + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
          + SqlDialect.getDefault().getNextvalNOprm("'shipping_header_seq'") + ", ?, ?, ?, ?) ";
    } else if (lineType.equals("TMALL")) {
      insertSql = " INSERT INTO " + TMALL_SHIPPING_HEADER
          + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME , TMALL_SHIPPING_FLG) " + " VALUES "
          + " ({1} " + SqlDialect.getDefault().getNextvalNOprm("'tmall_shipping_header_seq'") + ", ?, ?, ?, ?,?) ";
    } else {
      insertSql = " INSERT INTO " + JD_SHIPPING_HEADER
          + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME , JD_SHIPPING_FLG) " + " VALUES "
          + " ({1} " + SqlDialect.getDefault().getNextvalNOprm("'jd_shipping_header_seq'") + ", ?, ?, ?, ?,?) ";
    }

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();
    List<String> columnList = new ArrayList<String>();
    columnList.add("SHIPPING_NO");
    columnList.add("ORDER_NO");
    columnList.add("SHOP_CODE");
    columnList.add("CUSTOMER_CODE");
    columnList.add("ADDRESS_NO");
    columnList.add("ADDRESS_LAST_NAME");
    columnList.add("ADDRESS_FIRST_NAME");
    columnList.add("ADDRESS_LAST_NAME_KANA");
    columnList.add("ADDRESS_FIRST_NAME_KANA");
    columnList.add("POSTAL_CODE");
    columnList.add("PREFECTURE_CODE");
    columnList.add("ADDRESS1");
    columnList.add("ADDRESS2");
    columnList.add("ADDRESS3");
    columnList.add("ADDRESS4");
    columnList.add("PHONE_NUMBER");
    columnList.add("MOBILE_NUMBER");
    columnList.add("DELIVERY_REMARK");
    columnList.add("ACQUIRED_POINT");
    columnList.add("DELIVERY_SLIP_NO");
    columnList.add("SHIPPING_CHARGE");
    columnList.add("SHIPPING_CHARGE_TAX_TYPE");
    columnList.add("SHIPPING_CHARGE_TAX_RATE");
    columnList.add("SHIPPING_CHARGE_TAX");
    columnList.add("DELIVERY_TYPE_NO");
    columnList.add("DELIVERY_TYPE_NAME");
    columnList.add("DELIVERY_APPOINTED_TIME_START");
    columnList.add("DELIVERY_APPOINTED_TIME_END");
    columnList.add("ARRIVAL_DATE");
    columnList.add("ARRIVAL_TIME_START");
    columnList.add("ARRIVAL_TIME_END");
    columnList.add("FIXED_SALES_STATUS");
    columnList.add("SHIPPING_STATUS");
    columnList.add("SHIPPING_DIRECT_DATE");
    columnList.add("SHIPPING_DATE");
    columnList.add("ORIGINAL_SHIPPING_NO");
    columnList.add("RETURN_ITEM_DATE");
    columnList.add("RETURN_ITEM_LOSS_MONEY");
    columnList.add("RETURN_ITEM_TYPE");
    columnList.add("CITY_CODE");
    columnList.add("DELIVERY_COMPANY_NO");
    columnList.add("DELIVERY_COMPANY_NAME");
    columnList.add("DELIVERY_APPOINTED_DATE");
    columnList.add("AREA_CODE");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  // 通过判断EC/TMALL生成退货SHIPPING_DETAIL sql
  private String getInsertShippingDetailSql() {
    String insertSql = "";
    if (lineType.equals("EC")) {
      insertSql = " INSERT INTO " + SHIPPING_DETAIL
          + " ({0} SHIPPING_DETAIL_NO,ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES "
          + " ({1} " + "'0'," + SqlDialect.getDefault().getNextvalNOprm("'shipping_detail_seq'") + ", ?, ?, ?, ?) ";
    } else if (lineType.equals("TMALL")) {
      insertSql = " INSERT INTO " + TMALL_SHIPPING_DETAIL
          + " ({0} SHIPPING_DETAIL_NO,ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES "
          + " ({1} " + "'0'," + SqlDialect.getDefault().getNextvalNOprm("'tmall_shipping_detail_seq'") + ", ?, ?, ?, ?) ";
    } else {
      insertSql = " INSERT INTO " + JD_SHIPPING_DETAIL
          + " ({0} SHIPPING_DETAIL_NO,ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES "
          + " ({1} " + "'0'," + SqlDialect.getDefault().getNextvalNOprm("'jd_shipping_detail_seq'") + ", ?, ?, ?, ?) ";
    }

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();
    List<String> columnList = new ArrayList<String>();
    columnList.add("SHIPPING_NO");
    columnList.add("SHOP_CODE");
    columnList.add("SKU_CODE");
    columnList.add("UNIT_PRICE");
    columnList.add("DISCOUNT_PRICE");
    columnList.add("DISCOUNT_AMOUNT");
    columnList.add("RETAIL_PRICE");
    columnList.add("RETAIL_TAX");
    columnList.add("PURCHASING_AMOUNT");
    columnList.add("GIFT_CODE");
    columnList.add("GIFT_NAME");
    columnList.add("GIFT_PRICE");
    columnList.add("GIFT_TAX_RATE");
    columnList.add("GIFT_TAX");
    columnList.add("GIFT_TAX_TYPE");
    if (lineType.equals("EC")) {
      columnList.add("COMMODITY_TYPE");
    }

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  // 更新订单状态为取消
  public int executeUpdateOrderStatus(String orderNo, String orderType) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    params.add(OrderStatus.CANCELLED.longValue());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());
    params.add(orderNo);

    if (orderType.equals("EC")) {
      logger.debug("UPDATE ORDER_HEADER: " + updateECOrderStatusSql);
      updateECOrderStatusStatement = createPreparedStatement(updateECOrderStatusSql);
      pstmt = updateECOrderStatusStatement;
      logger.debug("Table:ORDER_HEADER UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    } else if (orderType.equals("TMALL")) {
      logger.debug("UPDATE TMALL_ORDER_HEADER: " + updateTmallOrderStatusSql);
      updateTmallOrderStatusStatement = createPreparedStatement(updateTmallOrderStatusSql);
      pstmt = updateTmallOrderStatusStatement;
      logger.debug("Table:TMALL_ORDER_HEADER UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    } else {
      logger.debug("UPDATE JD_ORDER_HEADER: " + updateJdOrderStatusSql);
      updateJdOrderStatusStatement = createPreparedStatement(updateJdOrderStatusSql);
      pstmt = updateJdOrderStatusStatement;
      logger.debug("Table:JD_ORDER_HEADER UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    }

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }

  // 更新发货状态为取消
  public int executeUpdateShippingStatus(String shipNo, String orderType) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    params.add(ShippingStatus.CANCELLED.longValue());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());
    params.add(shipNo);

    if (orderType.equals("EC")) {
      logger.debug("UPDATE SHIPPING_HEADER: " + updateECShipStatusSql);
      updateECShipStatusStatement = createPreparedStatement(updateECShipStatusSql);
      pstmt = updateECShipStatusStatement;
      logger.debug("Table:SHIPPING_HEADER UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    } else if (orderType.equals("TMALL")) {
      logger.debug("UPDATE TMALL_SHIPPING_HEADER: " + updateTmallShipStatusSql);
      updateTmallShipStatusStatement = createPreparedStatement(updateTmallShipStatusSql);
      pstmt = updateTmallShipStatusStatement;
      logger.debug("Table:TMALL_SHIPPING_HEADER UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    } else {
      logger.debug("UPDATE JD_SHIPPING_HEADER: " + updateJdShipStatusSql);
      updateJdShipStatusStatement = createPreparedStatement(updateJdShipStatusSql);
      pstmt = updateJdShipStatusStatement;
      logger.debug("Table:JD_SHIPPING_HEADER UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    }

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }
  
  // 取消引当数
  public int executeUpdateStockCancel(String orderNo, String orderType) throws SQLException {
    int error = 0;
    // EC订单处理
    if (orderType.equals("EC")) {
      OrderDetailDao oDetailDao = DIContainer.getDao(OrderDetailDao.class);
      List<OrderDetail> orgOrderDetails = oDetailDao.findByQuery(OrderServiceQuery.ORDER_DETAIL_LIST_QUERY, orderHeader
          .getOrderNo());
      for (OrderDetail od : orgOrderDetails) {

        if (od.getSetCommodityFlg() == null || od.getSetCommodityFlg() != 1) {
          // 普通商品
          PreparedStatement pstmt = null;
          List<Object> params = new ArrayList<Object>();

          params.add(od.getPurchasingAmount().intValue());
          params.add("BATCH:0:0:0");
          params.add(DateUtil.getSysdate());
          params.add(od.getSkuCode());

          logger.debug("UPDATE STOCK: " + updateECStockSql);
          updateECStockStatement = createPreparedStatement(updateECStockSql);
          pstmt = updateECStockStatement;
          logger.debug("Table:STOCK UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
          DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
          int reCount = pstmt.executeUpdate();
          if (reCount != 1) {
            error++;
          }
        } else {
          // 套装品
          ShippingDetailCompositionDao shippingDetailCompositionDao = DIContainer.getDao(ShippingDetailCompositionDao.class);
          List<ShippingDetailComposition> sdcList = shippingDetailCompositionDao.findByQuery(
              "SELECT * FROM SHIPPING_DETAIL_COMPOSITION WHERE SHIPPING_NO=? AND  PARENT_COMMODITY_CODE =?", shippingHeader
                  .getShippingNo(), od.getSkuCode());
          for (ShippingDetailComposition sdc : sdcList) {
            PreparedStatement pstmt = null;
            List<Object> params = new ArrayList<Object>();
            // 组合品数量
            // params.add(od.getPurchasingAmount().intValue());
            params.add(sdc.getPurchasingAmount().intValue());
            // 组合品数量
            params.add("BATCH:0:0:0");
            params.add(DateUtil.getSysdate());
            params.add(sdc.getChildSkuCode());

            logger.debug("UPDATE STOCK: " + updateECStockSql);
            updateECStockStatement = createPreparedStatement(updateECStockSql);
            pstmt = updateECStockStatement;
            logger.debug("Table:STOCK UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
            DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
            int reCount = pstmt.executeUpdate();
            if (reCount != 1) {
              error++;
            }
          }
        }
      }
    } else if (orderType.equals("TMALL")) {
      // Tmall订单处理
      // CatalogService catalogservice =
      // ServiceLocator.getCatalogService(getCondition().getLoginInfo());
      TmallOrderDetailDao oDetailDao = DIContainer.getDao(TmallOrderDetailDao.class);
      List<TmallOrderDetail> orgOrderDetails = oDetailDao.findByQuery(OrderServiceQuery.TMALL_ORDER_DETAIL_LIST_QUERY,
          tmallOrderHeader.getOrderNo());
      TmallSuitCommodityDao suitDao = DIContainer.getDao(TmallSuitCommodityDao.class);
      for (TmallOrderDetail od : orgOrderDetails) {
        SimpleQuery sqlQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(TmallShippingDetailComposition.class)
            + " WHERE SHIPPING_NO = ? AND PARENT_COMMODITY_CODE = ?", tmallShippingHeader.getShippingNo(), od.getCommodityCode());
        TmallShippingDetailComposition sdcBean = loadAsBean(sqlQuery, TmallShippingDetailComposition.class);

        // 是否有套装或组合明细
        if (sdcBean != null && sdcBean.getPurchasingAmount() != null) {
          // 是否有套装
          if (suitDao.exists(sdcBean.getParentCommodityCode())) {
            // List<SetCommodityComposition> compositionList =
            // catalogservice.getSetCommodityInfo("00000000", sdcBean
            // .getParentCommodityCode());
            // for (SetCommodityComposition detail : compositionList) {
            // // STOCK 库存
            // List<Object> params = new ArrayList<Object>();
            // params.add(sdcBean.getPurchasingAmount().intValue());
            // params.add("BATCH:0:0:0");
            // params.add(DateUtil.getSysdate());
            // params.add(detail.getChildCommodityCode());
            // logger.debug("UPDATE STOCK: " + updateTmallStockSql);
            // updateTmallStockStatement =
            // createPreparedStatement(updateTmallStockSql);
            // PreparedStatement pstmt = updateTmallStockStatement;
            // logger.debug("Table:STOCK UPDATE Parameters: " +
            // Arrays.toString(ArrayUtil.toArray(params, Object.class)));
            // DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params,
            // Object.class));
            // int reCount = pstmt.executeUpdate();
            // if (reCount != 1) {
            // error++;
            // }
            // }
            // TMALL_SUIT_COMMODITY 套装商品库存比例表 START
            List<Object> params2 = new ArrayList<Object>();
            params2.add(sdcBean.getPurchasingAmount().intValue());
            params2.add("BATCH:0:0:0");
            params2.add(DateUtil.getSysdate());
            params2.add(sdcBean.getParentCommodityCode());
            logger.debug("UPDATE TMALL_SUIT_COMMODITY: " + updateTmallStockSuitSql);
            updateTmallStockSuitStatement = createPreparedStatement(updateTmallStockSuitSql);
            PreparedStatement pstmt2 = updateTmallStockSuitStatement;
            logger.debug("Table:TMALL_SUIT_COMMODITY UPDATE Parameters: "
                + Arrays.toString(ArrayUtil.toArray(params2, Object.class)));
            DatabaseUtil.bindParameters(pstmt2, ArrayUtil.toArray(params2, Object.class));
            int reCount2 = pstmt2.executeUpdate();
            if (reCount2 != 1) {
              error++;
            }
            // TMALL_SUIT_COMMODITY 套装商品库存比例表 END
            // 将引当数恢复到tmall库存数(套装商品)
            try {
              TmallService service = ServiceLocator.getTmallService(getCondition().getLoginInfo());
              if (!StringUtil.isNullOrEmpty(od.getTmallSkuCode()) && !StringUtil.isNullOrEmpty(od.getTmallCommodityCode())) {
                TmallCommoditySku tcs = new TmallCommoditySku();
                tcs.setNumiid(od.getTmallCommodityCode());
                tcs.setSkuId(od.getTmallSkuCode());
                tcs.setQuantity(od.getPurchasingAmount().toString());
                tcs.setUpdateType("2");
                service.updateSkuStock(tcs);
                logger.info(Messages.log("Numiid:" + od.getTmallCommodityCode() + "_SkuId:" + od.getTmallSkuCode() + "恢复淘宝库存:"
                    + od.getPurchasingAmount().toString()));
              } else if (!StringUtil.isNullOrEmpty(od.getTmallCommodityCode())) {
                TmallCommoditySku tcs = new TmallCommoditySku();
                tcs.setNumiid(od.getTmallCommodityCode());
                tcs.setQuantity(od.getPurchasingAmount().toString());
                tcs.setUpdateType("2");
                service.updateSkuStock(tcs);
                logger.info(Messages.log("Numiid:" + od.getTmallCommodityCode() + "恢复淘宝库存:" + od.getPurchasingAmount().toString()));
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          } else {
            // 组合品处理
            // STOCK 库存
            // List<Object> params = new ArrayList<Object>();
            // params.add(sdcBean.getPurchasingAmount().intValue());
            // params.add("BATCH:0:0:0");
            // params.add(DateUtil.getSysdate());
            // params.add(sdcBean.getChildCommodityCode());
            // logger.debug("UPDATE STOCK: " + updateTmallStockSql);
            // updateTmallStockStatement =
            // createPreparedStatement(updateTmallStockSql);
            // PreparedStatement pstmt = updateTmallStockStatement;
            // logger.debug("Table:STOCK UPDATE Parameters: " +
            // Arrays.toString(ArrayUtil.toArray(params, Object.class)));
            // DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params,
            // Object.class));
            // int reCount = pstmt.executeUpdate();
            // if (reCount != 1) {
            // error++;
            // } else {
            // TMALL_STOCK_ALLOCATION 组合商品库存比例表 START
            List<Object> params2 = new ArrayList<Object>();
            params2.add(sdcBean.getPurchasingAmount().intValue());
            params2.add("BATCH:0:0:0");
            params2.add(DateUtil.getSysdate());
            params2.add(od.getSkuCode());
            params2.add(sdcBean.getChildCommodityCode());
            logger.debug("UPDATE TMALL_STOCK_ALLOCATION: " + updateTmallStockAllocationSql);
            updateTmallStockAllocationStatement = createPreparedStatement(updateTmallStockAllocationSql);
            PreparedStatement pstmt2 = updateTmallStockAllocationStatement;
            logger.debug("Table:TMALL_STOCK_ALLOCATION UPDATE Parameters: "
                + Arrays.toString(ArrayUtil.toArray(params2, Object.class)));
            DatabaseUtil.bindParameters(pstmt2, ArrayUtil.toArray(params2, Object.class));
            pstmt2.executeUpdate();
            // TMALL_STOCK_ALLOCATION 组合商品库存比例表 END
            // 将引当数恢复到tmall库存数(组合商品)
            try {
              TmallService service = ServiceLocator.getTmallService(getCondition().getLoginInfo());
              if (!StringUtil.isNullOrEmpty(od.getTmallSkuCode()) && !StringUtil.isNullOrEmpty(od.getTmallCommodityCode())) {
                TmallCommoditySku tcs = new TmallCommoditySku();
                tcs.setNumiid(od.getTmallCommodityCode());
                tcs.setSkuId(od.getTmallSkuCode());
                tcs.setQuantity(od.getPurchasingAmount().toString());
                tcs.setUpdateType("2");
                service.updateSkuStock(tcs);
                logger.info(Messages.log("Numiid:" + od.getTmallCommodityCode() + "_SkuId:" + od.getTmallSkuCode() + "恢复淘宝库存:"
                    + od.getPurchasingAmount().toString()));
              } else if (!StringUtil.isNullOrEmpty(od.getTmallCommodityCode())) {
                TmallCommoditySku tcs = new TmallCommoditySku();
                tcs.setNumiid(od.getTmallCommodityCode());
                tcs.setQuantity(od.getPurchasingAmount().toString());
                tcs.setUpdateType("2");
                service.updateSkuStock(tcs);
                logger.info(Messages.log("Numiid:" + od.getTmallCommodityCode() + "恢复淘宝库存:" + od.getPurchasingAmount().toString()));
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
            // }
          }
        } else {
          List<Object> params = new ArrayList<Object>();
          params.add(od.getPurchasingAmount().intValue());
          params.add("BATCH:0:0:0");
          params.add(DateUtil.getSysdate());
          params.add(od.getSkuCode());
          logger.debug("UPDATE STOCK: " + updateTmallStockSql);
          updateTmallStockStatement = createPreparedStatement(updateTmallStockSql);
          PreparedStatement pstmt = updateTmallStockStatement;
          logger.debug("Table:STOCK UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
          DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
          int reCount = pstmt.executeUpdate();
          if (reCount != 1) {
            error++;
          } else {
            // 将引当数恢复到tmall库存数
            try {
              TmallService service = ServiceLocator.getTmallService(getCondition().getLoginInfo());
              if (!StringUtil.isNullOrEmpty(od.getTmallSkuCode()) && !StringUtil.isNullOrEmpty(od.getTmallCommodityCode())) {
                TmallCommoditySku tcs = new TmallCommoditySku();
                tcs.setNumiid(od.getTmallCommodityCode());
                tcs.setSkuId(od.getTmallSkuCode());
                tcs.setQuantity(od.getPurchasingAmount().toString());
                tcs.setUpdateType("2");
                service.updateSkuStock(tcs);
                logger.info(Messages.log("Numiid:" + od.getTmallCommodityCode() + "_SkuId:" + od.getTmallSkuCode() + "恢复淘宝库存:"
                    + od.getPurchasingAmount().toString()));
              } else if (!StringUtil.isNullOrEmpty(od.getTmallCommodityCode())) {
                TmallCommoditySku tcs = new TmallCommoditySku();
                tcs.setNumiid(od.getTmallCommodityCode());
                tcs.setQuantity(od.getPurchasingAmount().toString());
                tcs.setUpdateType("2");
                service.updateSkuStock(tcs);
                logger.info(Messages.log("Numiid:" + od.getTmallCommodityCode() + "恢复淘宝库存:" + od.getPurchasingAmount().toString()));
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }
    } else {
      // 处理京东
      // CatalogService catalogservice =
      // ServiceLocator.getCatalogService(getCondition().getLoginInfo());
      JdOrderDetailDao oDetailDao = DIContainer.getDao(JdOrderDetailDao.class);
      List<JdOrderDetail> orgOrderDetails = oDetailDao.findByQuery(OrderServiceQuery.JD_ORDER_DETAIL_LIST_QUERY, jdOrderHeader
          .getOrderNo());
      JdSuitCommodityDao suitDao = DIContainer.getDao(JdSuitCommodityDao.class);
      for (JdOrderDetail od : orgOrderDetails) {
        SimpleQuery sqlQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(JdShippingDetailComposition.class)
            + " WHERE SHIPPING_NO = ? AND PARENT_COMMODITY_CODE = ?", jdShippingHeader.getShippingNo(), od.getCommodityCode());
        JdShippingDetailComposition sdcBean = loadAsBean(sqlQuery, JdShippingDetailComposition.class);

        // 是否有套装或组合明细
        if (sdcBean != null && sdcBean.getPurchasingAmount() != null) {
          // 是否有套装
          if (suitDao.exists(sdcBean.getParentCommodityCode())) {
            // List<SetCommodityComposition> compositionList =
            // catalogservice.getSetCommodityInfo("00000000", sdcBean
            // .getParentCommodityCode());
            // for (SetCommodityComposition detail : compositionList) {
            // // STOCK 库存
            // List<Object> params = new ArrayList<Object>();
            // params.add(sdcBean.getPurchasingAmount().intValue());
            // params.add("BATCH:0:0:0");
            // params.add(DateUtil.getSysdate());
            // params.add(detail.getChildCommodityCode());
            // logger.debug("UPDATE STOCK: " + updateJdStockSql);
            // updateJdStockStatement =
            // createPreparedStatement(updateJdStockSql);
            // PreparedStatement pstmt = updateJdStockStatement;
            // logger.debug("Table:STOCK UPDATE Parameters: " +
            // Arrays.toString(ArrayUtil.toArray(params, Object.class)));
            // DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params,
            // Object.class));
            // int reCount = pstmt.executeUpdate();
            // if (reCount != 1) {
            // error++;
            // }
            // }
            // TMALL_SUIT_COMMODITY 套装商品库存比例表 START
            List<Object> params2 = new ArrayList<Object>();
            params2.add(sdcBean.getPurchasingAmount().intValue());
            params2.add("BATCH:0:0:0");
            params2.add(DateUtil.getSysdate());
            params2.add(sdcBean.getParentCommodityCode());
            logger.debug("UPDATE JD_SUIT_COMMODITY: " + updateJdStockSuitSql);
            updateJdStockSuitStatement = createPreparedStatement(updateJdStockSuitSql);
            PreparedStatement pstmt2 = updateJdStockSuitStatement;
            logger.debug("Table:JD_SUIT_COMMODITY UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params2, Object.class)));
            DatabaseUtil.bindParameters(pstmt2, ArrayUtil.toArray(params2, Object.class));
            int reCount2 = pstmt2.executeUpdate();
            if (reCount2 != 1) {
              error++;
            }
            // TMALL_SUIT_COMMODITY 套装商品库存比例表 END
            // 将引当数恢复到tmall库存数(套装商品)
            // try {
            // TmallService service =
            // ServiceLocator.getJdService(getCondition().getLoginInfo());
            // if (!StringUtil.isNullOrEmpty(od.getTmallSkuCode()) &&
            // !StringUtil.isNullOrEmpty(od.getTmallCommodityCode())) {
            // TmallCommoditySku tcs = new TmallCommoditySku();
            // tcs.setNumiid(od.getTmallCommodityCode());
            // tcs.setSkuId(od.getTmallSkuCode());
            // tcs.setQuantity(od.getPurchasingAmount().toString());
            // tcs.setUpdateType("2");
            // service.updateSkuStock(tcs);
            // logger.info(Messages.log("Numiid:" + od.getTmallCommodityCode() +
            // "_SkuId:" + od.getTmallSkuCode() + "恢复淘宝库存:"
            // + od.getPurchasingAmount().toString()));
            // } else if (!StringUtil.isNullOrEmpty(od.getTmallCommodityCode()))
            // {
            // TmallCommoditySku tcs = new TmallCommoditySku();
            // tcs.setNumiid(od.getTmallCommodityCode());
            // tcs.setQuantity(od.getPurchasingAmount().toString());
            // tcs.setUpdateType("2");
            // service.updateSkuStock(tcs);
            // logger.info(Messages.log("Numiid:" + od.getTmallCommodityCode() +
            // "恢复淘宝库存:" + od.getPurchasingAmount().toString()));
            // }
            // } catch (Exception e) {
            // e.printStackTrace();
            // }
          } else {
            // 组合品处理
            // STOCK 库存
            // List<Object> params = new ArrayList<Object>();
            // params.add(sdcBean.getPurchasingAmount().intValue());
            // params.add("BATCH:0:0:0");
            // params.add(DateUtil.getSysdate());
            // params.add(sdcBean.getChildCommodityCode());
            // logger.debug("UPDATE STOCK: " + updateJdStockSql);
            // updateJdStockStatement =
            // createPreparedStatement(updateJdStockSql);
            // PreparedStatement pstmt = updateJdStockStatement;
            // logger.debug("Table:STOCK UPDATE Parameters: " +
            // Arrays.toString(ArrayUtil.toArray(params, Object.class)));
            // DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params,
            // Object.class));
            // int reCount = pstmt.executeUpdate();
            // if (reCount != 1) {
            // error++;
            // } else {
            // TMALL_STOCK_ALLOCATION 组合商品库存比例表 START
            List<Object> params2 = new ArrayList<Object>();
            params2.add(sdcBean.getPurchasingAmount().intValue());
            params2.add("BATCH:0:0:0");
            params2.add(DateUtil.getSysdate());
            params2.add(od.getSkuCode());
            params2.add(sdcBean.getChildCommodityCode());
            logger.debug("UPDATE JD_STOCK_ALLOCATION: " + updateJdStockAllocationSql);
            updateJdStockAllocationStatement = createPreparedStatement(updateJdStockAllocationSql);
            PreparedStatement pstmt2 = updateJdStockAllocationStatement;
            logger.debug("Table:JD_STOCK_ALLOCATION UPDATE Parameters: "
                + Arrays.toString(ArrayUtil.toArray(params2, Object.class)));
            DatabaseUtil.bindParameters(pstmt2, ArrayUtil.toArray(params2, Object.class));
            pstmt2.executeUpdate();
            // TMALL_STOCK_ALLOCATION 组合商品库存比例表 END
            // 将引当数恢复到tmall库存数(组合商品)
            // try {
            // TmallService service =
            // ServiceLocator.getTmallService(getCondition().getLoginInfo());
            // if (!StringUtil.isNullOrEmpty(od.getTmallSkuCode()) &&
            // !StringUtil.isNullOrEmpty(od.getTmallCommodityCode())) {
            // TmallCommoditySku tcs = new TmallCommoditySku();
            // tcs.setNumiid(od.getTmallCommodityCode());
            // tcs.setSkuId(od.getTmallSkuCode());
            // tcs.setQuantity(od.getPurchasingAmount().toString());
            // tcs.setUpdateType("2");
            // service.updateSkuStock(tcs);
            // logger.info(Messages.log("Numiid:" + od.getTmallCommodityCode()
            // + "_SkuId:" + od.getTmallSkuCode() + "恢复淘宝库存:"
            // + od.getPurchasingAmount().toString()));
            // } else if
            // (!StringUtil.isNullOrEmpty(od.getTmallCommodityCode())) {
            // TmallCommoditySku tcs = new TmallCommoditySku();
            // tcs.setNumiid(od.getTmallCommodityCode());
            // tcs.setQuantity(od.getPurchasingAmount().toString());
            // tcs.setUpdateType("2");
            // service.updateSkuStock(tcs);
            // logger.info(Messages.log("Numiid:" + od.getTmallCommodityCode()
            // + "恢复淘宝库存:" + od.getPurchasingAmount().toString()));
            // }
            // } catch (Exception e) {
            // e.printStackTrace();
            // }
            // }
          }
        } else {
          List<Object> params = new ArrayList<Object>();
          params.add(od.getPurchasingAmount().intValue());
          params.add("BATCH:0:0:0");
          params.add(DateUtil.getSysdate());
          params.add(od.getSkuCode());
          logger.debug("UPDATE STOCK: " + updateJdStockSql);
          updateJdStockStatement = createPreparedStatement(updateJdStockSql);
          PreparedStatement pstmt = updateJdStockStatement;
          logger.debug("Table:STOCK UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
          DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
          int reCount = pstmt.executeUpdate();
          if (reCount != 1) {
            error++;
          } else {
            // 将引当数恢复到tmall库存数
            // try {
            // TmallService service =
            // ServiceLocator.getTmallService(getCondition().getLoginInfo());
            // if (!StringUtil.isNullOrEmpty(od.getTmallSkuCode()) &&
            // !StringUtil.isNullOrEmpty(od.getTmallCommodityCode())) {
            // TmallCommoditySku tcs = new TmallCommoditySku();
            // tcs.setNumiid(od.getTmallCommodityCode());
            // tcs.setSkuId(od.getTmallSkuCode());
            // tcs.setQuantity(od.getPurchasingAmount().toString());
            // tcs.setUpdateType("2");
            // service.updateSkuStock(tcs);
            // logger.info(Messages.log("Numiid:" + od.getTmallCommodityCode() +
            // "_SkuId:" + od.getTmallSkuCode() + "恢复淘宝库存:"
            // + od.getPurchasingAmount().toString()));
            // } else if (!StringUtil.isNullOrEmpty(od.getTmallCommodityCode()))
            // {
            // TmallCommoditySku tcs = new TmallCommoditySku();
            // tcs.setNumiid(od.getTmallCommodityCode());
            // tcs.setQuantity(od.getPurchasingAmount().toString());
            // tcs.setUpdateType("2");
            // service.updateSkuStock(tcs);
            // logger.info(Messages.log("Numiid:" + od.getTmallCommodityCode() +
            // "恢复淘宝库存:" + od.getPurchasingAmount().toString()));
            // }
            // } catch (Exception e) {
            // e.printStackTrace();
            // }
          }
        }
      }
    }
    return error;
  }

  // 订单发货前取消时礼品卡使用金额取消（恢复礼品卡金额）
  public int executeUpdateGiftCancel(String orderNo) throws SQLException {
    int error = 0;

    CustomerCardUseInfoDao useInfoDao = DIContainer.getDao(CustomerCardUseInfoDao.class);
    List<CustomerCardUseInfo> useInfoList = useInfoDao.loadByOrderNo(orderNo);

    if (useInfoList != null && useInfoList.size() > 0) {
      for (CustomerCardUseInfo ccui : useInfoList) {
        PreparedStatement pstmt = null;
        List<Object> params = new ArrayList<Object>();

        params.add(1L);
        params.add("BATCH:0:0:0");
        params.add(DateUtil.getSysdate());
        params.add(ccui.getCustomerCode());
        params.add(ccui.getOrderNo());
        params.add(ccui.getCardId());
        logger.debug("UPDATE CUSTOMER_CARD_USE_INFO: " + updateCustomerCardUseInfoSql);
        updateCustomerCardUseInfoStatement = createPreparedStatement(updateCustomerCardUseInfoSql);
        pstmt = updateCustomerCardUseInfoStatement;
        logger.debug("Table:CUSTOMER_CARD_USE_INFO UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
        DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
        int reCount = pstmt.executeUpdate();
        if (reCount != 1) {
          error++;
        }
      }
    }
    return error;
  }

  // 购买发行优惠券取消
  public int executeUpdateNewCouponCancel(String orderNo) throws SQLException {
    int error = 0;

    NewCouponHistoryDao nCouDao = DIContainer.getDao(NewCouponHistoryDao.class);
    List<NewCouponHistory> nCouList = nCouDao.findByQuery("SELECT * FROM NEW_COUPON_HISTORY WHERE ISSUE_ORDER_NO = ?", orderNo);

    if (nCouList != null && nCouList.size() > 0) {
      for (NewCouponHistory nCou : nCouList) {
        PreparedStatement pstmt = null;
        List<Object> params = new ArrayList<Object>();

        params.add("BATCH:0:0:0");
        params.add(DateUtil.getSysdate());
        params.add(nCou.getIssueOrderNo());
        params.add(nCou.getCouponIssueNo());
        logger.debug("UPDATE NEW_COUPON_HISTORY: " + updateNewCouponHistorySql);
        updateNewCouponHistoryStatement = createPreparedStatement(updateNewCouponHistorySql);
        pstmt = updateNewCouponHistoryStatement;
        logger.debug("Table:NEW_COUPON_HISTORY UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
        DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
        int reCount = pstmt.executeUpdate();
        if (reCount != 1) {
          error++;
        }
      }
    }
    return error;
  }

  // 朋友介绍优惠券状态设为【2：无效】
  public int executeUpdateFriendCouponUseCancel(String orderNo, String customerCode) throws SQLException {
    int error = 0;

    FriendCouponUseHistoryDao useFriendCouDao = DIContainer.getDao(FriendCouponUseHistoryDao.class);
    List<FriendCouponUseHistory> useFriendCouList = useFriendCouDao.findByQuery(
        "SELECT * FROM FRIEND_COUPON_USE_HISTORY WHERE ORDER_NO = ? AND CUSTOMER_CODE = ? ", orderNo, customerCode);

    if (useFriendCouList != null && useFriendCouList.size() > 0) {
      for (FriendCouponUseHistory nFriCou : useFriendCouList) {
        PreparedStatement pstmt = null;
        List<Object> params = new ArrayList<Object>();

        params.add("BATCH:0:0:0");
        params.add(DateUtil.getSysdate());
        params.add(nFriCou.getOrderNo());
        params.add(nFriCou.getCustomerCode());
        logger.debug("UPDATE FRIEND_COUPON_USE_HISTORY: " + updateFriendCouponUseHistorySql);
        updateFriendCouponUseHistoryStatement = createPreparedStatement(updateFriendCouponUseHistorySql);
        pstmt = updateFriendCouponUseHistoryStatement;
        logger.debug("Table:FRIEND_COUPON_USE_HISTORY UPDATE Parameters: "
            + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
        DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
        int reCount = pstmt.executeUpdate();
        if (reCount != 1) {
          error++;
        }
      }
    }
    return error;
  }

  // 判断改行数据时EC订单还是Tmall订单
  private String getTmallOrEcOrder(String orderNo) {
    String orderTitle = orderNo.substring(0, 1);
    if (orderTitle.equals("T")) {
      return "TMALL";
    } else if (orderTitle.equals("D")) {
      return "JD";
    } else {
      return "EC";
    }
  }

  // 将负数转正数
  // private long getNum(long num) {
  // String temp = num + "";
  // String numStr = temp.substring(1);
  // return Long.parseLong(numStr);
  // }

  private void checkItemLength(ZsIfSOShipImportReport bean, List<String> errorMessageList) {
    if (bean.getOrderNo() != null) {
      if (bean.getOrderNo().length() > 8) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.1"),
            "ORDER_NO"));
      }
    }
    if (bean.getCancelOrderNo() != null) {
      if (bean.getCancelOrderNo().length() > 8) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.2"),
            "ORDER_NO"));
      }
    }
    if (bean.getSkuCode() != null) {
      if (bean.getSkuCode().length() > 24) {
        errorMessageList
            .add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.3"), "SKU_NO"));
      }
    }
    if (bean.getShippingAmount() != null) {
      if (bean.getShippingAmount().toString().length() > 8) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.4"),
            "SHIPPING_AMOUNT"));
      }
    }
    if (bean.getDeliverySlipNo() != null) {
      if (bean.getDeliverySlipNo().length() > 500) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.5"),
            "DELIVERY_SLIP_NO"));
      }
    }
  }
  
  // 更新JD拆单订单发货状态为发货完毕
  public int executeUpdateJdCharShippingStatus(String orderNo) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    params.add(ShippingStatus.SHIPPED.longValue());
    params.add(zsIfSOShipImportReport.getShippingDate());
    params.add("BATCH:0:0:0");
    params.add(DateUtil.getSysdate());
    params.add(zsIfSOShipImportReport.getDeliverySlipNo());
    params.add(orderNo);

    logger.debug("UPDATE JD_SHIPPING_HEADER: " + updateJdCharShipStatusSql);
    updateJdCharShipStatusStatement = createPreparedStatement(updateJdCharShipStatusSql);
    pstmt = updateJdCharShipStatusStatement;
    logger.debug("Table:JD_SHIPPING_HEADER UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }
  
  // 减掉JD拆单订单的库存数和引当数
  public int jdCharStockDeal(String orderNo) throws SQLException {
    int error = 0;

    JdShippingHeaderDao jdShippingHeaderDao = DIContainer.getDao(JdShippingHeaderDao.class);
    List<JdShippingHeader> jdShippingHeaderList = jdShippingHeaderDao.findByQuery(
        "SELECT * FROM JD_SHIPPING_HEADER WHERE ORDER_NO = ? AND RETURN_ITEM_TYPE = 0", orderNo);

    for (JdShippingHeader jdl : jdShippingHeaderList) {

      JdShippingDetailDao jdShippingDetailDao = DIContainer.getDao(JdShippingDetailDao.class);
      List<JdShippingDetail> jdShippingDetailList = jdShippingDetailDao.findByQuery(
          "SELECT * FROM JD_SHIPPING_DETAIL WHERE SHIPPING_NO = ?", jdl.getShippingNo());

      JdSuitCommodityDao suitDao = DIContainer.getDao(JdSuitCommodityDao.class);
      for (JdShippingDetail jsd : jdShippingDetailList) {
        SimpleQuery sqlQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(JdShippingDetailComposition.class)
            + " WHERE SHIPPING_NO = ? AND PARENT_COMMODITY_CODE = ?", jsd.getShippingNo(), jsd.getSkuCode());
        JdShippingDetailComposition sdcBean = loadAsBean(sqlQuery, JdShippingDetailComposition.class);

        // 是否有套装或组合明细
        if (sdcBean != null && sdcBean.getPurchasingAmount() != null) {
          if (suitDao.exists(sdcBean.getParentCommodityCode())) {
            // 套装
            List<Object> params2 = new ArrayList<Object>();
            params2.add(sdcBean.getPurchasingAmount().intValue());
            params2.add(sdcBean.getPurchasingAmount().intValue());
            params2.add("BATCH:0:0:0");
            params2.add(DateUtil.getSysdate());
            params2.add(sdcBean.getParentCommodityCode());
            logger.debug("UPDATE JD_SUIT_COMMODITY: " + updateJdCharStockSuitSql);
            updateJdStockSuitStatement = createPreparedStatement(updateJdCharStockSuitSql);
            PreparedStatement pstmt2 = updateJdStockSuitStatement;
            logger.debug("Table:JD_SUIT_COMMODITY UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params2, Object.class)));
            DatabaseUtil.bindParameters(pstmt2, ArrayUtil.toArray(params2, Object.class));
            int res = pstmt2.executeUpdate();
            if (res != 1) {
              error++;
            }
          } else {
            // 组合
            List<Object> params2 = new ArrayList<Object>();
            params2.add(sdcBean.getPurchasingAmount().intValue());
            params2.add(sdcBean.getPurchasingAmount().intValue());
            params2.add("BATCH:0:0:0");
            params2.add(DateUtil.getSysdate());
            params2.add(jsd.getSkuCode());
            params2.add(sdcBean.getChildCommodityCode());
            logger.debug("UPDATE JD_STOCK_ALLOCATION: " + updateJdCharStockAllocationSql);
            updateJdStockAllocationStatement = createPreparedStatement(updateJdCharStockAllocationSql);
            PreparedStatement pstmt2 = updateJdStockAllocationStatement;
            logger.debug("Table:JD_STOCK_ALLOCATION UPDATE Parameters: "
                + Arrays.toString(ArrayUtil.toArray(params2, Object.class)));
            DatabaseUtil.bindParameters(pstmt2, ArrayUtil.toArray(params2, Object.class));
            int res = pstmt2.executeUpdate();
            if (res != 1) {
              error++;
            }
          }
        } else {
          // 单品
          List<Object> params = new ArrayList<Object>();
          params.add(jsd.getPurchasingAmount().intValue());
          params.add(jsd.getPurchasingAmount().intValue());
          params.add("BATCH:0:0:0");
          params.add(DateUtil.getSysdate());
          params.add(jsd.getSkuCode());
          logger.debug("UPDATE STOCK: " + updateJdCharStockSql);
          updateJdStockStatement = createPreparedStatement(updateJdCharStockSql);
          PreparedStatement pstmt = updateJdStockStatement;
          logger.debug("Table:STOCK UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
          DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
          int res = pstmt.executeUpdate();
          if (res != 1) {
            error++;
          }
        }
      }
    }
    return error;
  }
  
}
