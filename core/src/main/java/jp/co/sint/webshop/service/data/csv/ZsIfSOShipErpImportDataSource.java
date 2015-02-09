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
import jp.co.sint.webshop.data.dao.OrderDetailDao;
import jp.co.sint.webshop.data.dao.TmallOrderDetailDao;
import jp.co.sint.webshop.data.domain.AdvanceLaterFlg;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.ShippingRealityDetail;
import jp.co.sint.webshop.data.dto.TmallOrderDetail;
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.data.dto.TmallShippingDetail;
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

public class ZsIfSOShipErpImportDataSource extends SqlImportDataSource<ZsIfSOShipErpImportCsvSchema, ZsIfSOShipErpImportCondition> {

  WebshopConfig config = DIContainer.getWebshopConfig();

  // 订单区分
  private String lineType = "";

  // 订单编号
  private String lineOrderNo = "";

  // 退货区分
  private boolean returnType = false;

  // 订单是否已取消过
  private boolean cancelFlg = false;

  // 发货Header
  private ShippingHeader shippingHeader = null;

  // 订单Header
  private OrderHeader orderHeader = null;

  // 发货Detail
  private ShippingDetail shippingDetail = null;

  // 发货Tmall_Header
  private TmallShippingHeader tmallShippingHeader = null;

  // 订单Tmall_Header
  private TmallOrderHeader tmallOrderHeader = null;

  // 发货Tmall_Detail
  private TmallShippingDetail tmallShippingDetail = null;

  private PreparedStatement insertShippingRealityDetailStatement = null;

  private PreparedStatement insertTmallShippingRealityDetailStatement = null;

  private PreparedStatement insertShippingHeaderStatement = null;

  private PreparedStatement insertShippingDetailStatement = null;

  private PreparedStatement updateECOrderStatusStatement = null;

  private PreparedStatement updateTmallOrderStatusStatement = null;

  private PreparedStatement updateECShipStatusStatement = null;

  private PreparedStatement updateTmallShipStatusStatement = null;

  private PreparedStatement updateECStockStatement = null;

  private PreparedStatement updateTmallStockStatement = null;

  // EC发货实际明细表
  private static final String SHIPPING_REALITY_DETAIL = DatabaseUtil.getTableName(ShippingRealityDetail.class);

  // Tmall发货实际明细表
  private static final String TMALL_SHIPPING_REALITY_DETAIL = DatabaseUtil.getTableName(TmallShippingRealityDetail.class);

  // EC发货Header表
  private static final String SHIPPING_HEADER = DatabaseUtil.getTableName(ShippingHeader.class);

  // EC发货Detail表
  private static final String SHIPPING_DETAIL = DatabaseUtil.getTableName(ShippingDetail.class);

  // Tmall发货Header表
  private static final String TMALL_SHIPPING_HEADER = DatabaseUtil.getTableName(TmallShippingHeader.class);

  // Tmall发货Detail表
  private static final String TMALL_SHIPPING_DETAIL = DatabaseUtil.getTableName(TmallShippingDetail.class);

  // EC 更新订单状态为取消
  private String updateECOrderStatusSql = "UPDATE ORDER_HEADER SET ORDER_STATUS = ?, UPDATED_USER = ? , UPDATED_DATETIME = ?"
      + " WHERE ORDER_NO = ? ";

  // Tmall 更新订单状态为取消
  private String updateTmallOrderStatusSql = "UPDATE TMALL_ORDER_HEADER SET ORDER_STATUS = ?, UPDATED_USER = ? , UPDATED_DATETIME = ?"
      + " WHERE ORDER_NO = ? ";

  // EC 更新发货状态为取消
  private String updateECShipStatusSql = "UPDATE SHIPPING_HEADER SET SHIPPING_STATUS = ?, UPDATED_USER = ? , UPDATED_DATETIME = ?"
      + " WHERE SHIPPING_NO = ? ";

  // Tmall 更新发货状态为取消
  private String updateTmallShipStatusSql = "UPDATE TMALL_SHIPPING_HEADER SET SHIPPING_STATUS = ?, UPDATED_USER = ? , UPDATED_DATETIME = ?"
      + " WHERE SHIPPING_NO = ? ";

  // EC 更新库存引当
  private String updateECStockSql = "UPDATE STOCK SET ALLOCATED_QUANTITY = ALLOCATED_QUANTITY - ?, UPDATED_USER = ? , "
      + " UPDATED_DATETIME = ?  WHERE SKU_CODE = ? ";

  // Tmall 更新库存引当
  private String updateTmallStockSql = "UPDATE STOCK SET ALLOCATED_TMALL = ALLOCATED_TMALL - ?, UPDATED_USER = ? , "
      + " UPDATED_DATETIME = ?  WHERE SKU_CODE = ? ";

  // 发货实际数据接收类
  private ZsIfSOShipImportReport zsIfSOShipImportReport = null;

  private Logger logger = Logger.getLogger(this.getClass());

  @Override
  protected void initializeResources() {
    logger.debug("INSERT SHIPPING_REALITY_DETAIL : " + getInsertShippingRealityDetailSql());
    logger.debug("INSERT TMALL_SHIPPING_REALITY_DETAIL : " + getInsertTmallShippingRealityDetailSql());
    try {
      insertShippingRealityDetailStatement = createPreparedStatement(getInsertShippingRealityDetailSql());
      insertTmallShippingRealityDetailStatement = createPreparedStatement(getInsertTmallShippingRealityDetailSql());
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
            || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(6))) {
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

      // 根据订单编号给发货Detail实体类赋值
      SimpleQuery shipDetailQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(ShippingDetail.class)
          + " WHERE SHIPPING_NO = ? AND SKU_CODE = ?");
      shipDetailQuery.setParameters(shippingHeader.getShippingNo(), zsIfSOShipImportReport.getSkuCode());
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
      SimpleQuery existImportQuery = new SimpleQuery(
          "SELECT COUNT(*) FROM SHIPPING_REALITY_DETAIL WHERE SHIPPING_AMOUNT = 0 AND ORDER_NO = ? AND SKU_CODE = ?");
      existImportQuery.setParameters(orderHeader.getOrderNo(), zsIfSOShipImportReport.getSkuCode());
      Long existNums = Long.valueOf(executeScalar(existImportQuery).toString());
      if (existNums != 0L) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.20"), "SKU"));
      }

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
      if (orderHeader.getOrderStatus() == OrderStatus.CANCELLED.longValue()) {
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
        // 查询该sku的发货数量信息
        
        // 组合商品、套装商品  UPDATE START
        //SimpleQuery shipNumQuery = new SimpleQuery(
        //    "select sd.purchasing_amount from shipping_header sh inner join shipping_detail sd on sh.shipping_no = sd.shipping_no "
        //        + "where (sh.RETURN_ITEM_TYPE <> 1 OR sh.RETURN_ITEM_TYPE IS NULL) and sh.shipping_status = 3 and order_no = ? and sd.sku_code = ?  ");

        String numSql = "SELECT SUM(CASE WHEN SDC.CHILD_SKU_CODE IS NOT NULL THEN SDC.PURCHASING_AMOUNT ELSE SD.PURCHASING_AMOUNT END) "
          + " FROM SHIPPING_HEADER SH INNER JOIN SHIPPING_DETAIL SD ON SH.SHIPPING_NO = SD.SHIPPING_NO " 
          + " LEFT JOIN SHIPPING_DETAIL_COMPOSITION SDC ON SD.SHIPPING_NO = SDC.SHIPPING_NO AND SD.SHIPPING_DETAIL_NO = SDC.SHIPPING_DETAIL_NO "
          + " WHERE (SH.RETURN_ITEM_TYPE <> 1 OR SH.RETURN_ITEM_TYPE IS NULL) AND SH.SHIPPING_STATUS = 3 AND ORDER_NO = ? AND "
          + " (CASE WHEN SDC.CHILD_SKU_CODE IS NOT NULL THEN SDC.CHILD_SKU_CODE = ? ELSE SD.SKU_CODE = ? END)";
        SimpleQuery shipNumQuery = new SimpleQuery(numSql);
        shipNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode(), zsIfSOShipImportReport.getSkuCode());
        // 组合商品、套装商品  UPDATE END
        
        Object objOne = executeScalar(shipNumQuery);
        Long shipNum = 0L;
        if (objOne != null) {
          shipNum = Long.valueOf(objOne.toString());
        } else {
          errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.11"),
              "SHIPPING_HEADER"));
        }
        SimpleQuery returnNumQuery = new SimpleQuery(
            "select sum(sd.purchasing_amount) from shipping_header sh inner join shipping_detail sd on sh.shipping_no = sd.shipping_no "
                + "where sh.return_item_type = 1 and sh.shipping_status = 3 and order_no = ? and sd.sku_code = ?  ");
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
          // 查询该sku的发货数量信息
          
          // 套装、组合商品对应  START
          //SimpleQuery shipNumQuery = new SimpleQuery(
          //    "select sd.purchasing_amount from shipping_header sh inner join shipping_detail sd on sh.shipping_no = sd.shipping_no "
          //        + "where (sh.RETURN_ITEM_TYPE <> 1 OR sh.RETURN_ITEM_TYPE IS NULL) and sh.shipping_status = 2 and order_no = ? and sd.sku_code = ?  ");
          
          String numSql = "SELECT SUM(CASE WHEN SD.SET_COMMODITY_FLG = '1' THEN SDC.PURCHASING_AMOUNT ELSE SD.PURCHASING_AMOUNT END) "
            + " FROM SHIPPING_HEADER SH INNER JOIN SHIPPING_DETAIL SD ON SH.SHIPPING_NO = SD.SHIPPING_NO " 
            + " LEFT JOIN SHIPPING_DETAIL_COMPOSITION SDC ON SD.SHIPPING_NO = SDC.SHIPPING_NO AND SD.SHIPPING_DETAIL_NO = SDC.SHIPPING_DETAIL_NO "
            + " WHERE (SH.RETURN_ITEM_TYPE <> 1 OR SH.RETURN_ITEM_TYPE IS NULL) AND SH.SHIPPING_STATUS = 2 AND ORDER_NO = ? AND "
            + " (CASE WHEN SD.SET_COMMODITY_FLG = '1' THEN SDC.CHILD_SKU_CODE = ? ELSE SD.SKU_CODE = ? END)";
          SimpleQuery shipNumQuery = new SimpleQuery(numSql);
          // shipNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode());
          shipNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode(), zsIfSOShipImportReport.getSkuCode());
          // 套装、组合商品对应  END
          
          Object objOne = executeScalar(shipNumQuery);
          Long shipNum = 0L;
          if (objOne != null) {
            shipNum = Long.valueOf(objOne.toString());
          } else {
            errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.17"),
                lineOrderNo));
          }
          // 查询发货实际明细表中的数量和
          SimpleQuery srNumQuery = new SimpleQuery(
              "select sum(shipping_amount) from shipping_reality_detail where shipping_amount > 0 and order_no = ? and sku_code = ? ");
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
    } else {
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

      // 根据订单编号给发货Detail实体类赋值
      SimpleQuery shipDetailQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(TmallShippingDetail.class)
          + " WHERE SHIPPING_NO = ? AND SKU_CODE = ?");
      shipDetailQuery.setParameters(tmallShippingHeader.getShippingNo(), zsIfSOShipImportReport.getSkuCode());
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
      SimpleQuery existImportQuery = new SimpleQuery(
          "SELECT COUNT(*) FROM TMALL_SHIPPING_REALITY_DETAIL WHERE SHIPPING_AMOUNT = 0 AND ORDER_NO = ? AND SKU_CODE = ?");
      existImportQuery.setParameters(tmallOrderHeader.getOrderNo(), zsIfSOShipImportReport.getSkuCode());
      Long existNums = Long.valueOf(executeScalar(existImportQuery).toString());
      if (existNums != 0L) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.ZsIfSOShipErpImportDataSource.20"), "SKU"));
      }

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
        
        // 组合商品、套装商品  UPDATE START
        // 查询该sku的发货数量信息
        //SimpleQuery shipNumQuery = new SimpleQuery(
        //    "select sd.purchasing_amount from tmall_shipping_header sh inner join tmall_shipping_detail sd on sh.shipping_no = sd.shipping_no "
        //        + "where (sh.RETURN_ITEM_TYPE <> 1 OR sh.RETURN_ITEM_TYPE IS NULL) and sh.shipping_status = 3 and order_no = ? and sd.sku_code = ?  ");

        String numSql = "SELECT SUM(CASE WHEN SDC.CHILD_SKU_CODE IS NOT NULL THEN SDC.PURCHASING_AMOUNT ELSE SD.PURCHASING_AMOUNT END) "
          + " FROM TMALL_SHIPPING_HEADER SH INNER JOIN TMALL_SHIPPING_DETAIL SD ON SH.SHIPPING_NO = SD.SHIPPING_NO " 
          + " LEFT JOIN TMALL_SHIPPING_DETAIL_COMPOSITION SDC ON SD.SHIPPING_NO = SDC.SHIPPING_NO AND SD.SHIPPING_DETAIL_NO = SDC.SHIPPING_DETAIL_NO "
          + " WHERE (SH.RETURN_ITEM_TYPE <> 1 OR SH.RETURN_ITEM_TYPE IS NULL) AND SH.SHIPPING_STATUS = 3 AND ORDER_NO = ? AND "
          + " (CASE WHEN SDC.CHILD_SKU_CODE IS NOT NULL THEN SDC.CHILD_SKU_CODE = ? ELSE SD.SKU_CODE = ? END)";
        SimpleQuery shipNumQuery = new SimpleQuery(numSql);
        shipNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode(), zsIfSOShipImportReport.getSkuCode());
        // 组合商品、套装商品  UPDATE END
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
          // 查询该sku的发货数量信息

          // 组合商品、套装商品  UPDATE START
          //SimpleQuery shipNumQuery = new SimpleQuery(
          //    "select sd.purchasing_amount from tmall_shipping_header sh inner join tmall_shipping_detail sd on sh.shipping_no = sd.shipping_no "
          //        + "where (sh.RETURN_ITEM_TYPE <> 1 OR sh.RETURN_ITEM_TYPE IS NULL) and sh.shipping_status = 2 and order_no = ? and sd.sku_code = ?  ");
          //shipNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode());
          
          String numSql = "SELECT SUM(CASE WHEN SDC.CHILD_SKU_CODE IS NOT NULL THEN SDC.PURCHASING_AMOUNT ELSE SD.PURCHASING_AMOUNT END) "
            + " FROM TMALL_SHIPPING_HEADER SH INNER JOIN TMALL_SHIPPING_DETAIL SD ON SH.SHIPPING_NO = SD.SHIPPING_NO " 
            + " LEFT JOIN TMALL_SHIPPING_DETAIL_COMPOSITION SDC ON SD.SHIPPING_NO = SDC.SHIPPING_NO AND SD.SHIPPING_DETAIL_NO = SDC.SHIPPING_DETAIL_NO "
            + " WHERE (SH.RETURN_ITEM_TYPE <> 1 OR SH.RETURN_ITEM_TYPE IS NULL) AND SH.SHIPPING_STATUS = 2 AND ORDER_NO = ? AND "
            + " (CASE WHEN SDC.CHILD_SKU_CODE IS NOT NULL THEN SDC.CHILD_SKU_CODE = ? ELSE SD.SKU_CODE = ? END)";
          SimpleQuery shipNumQuery = new SimpleQuery(numSql);
          shipNumQuery.setParameters(lineOrderNo, zsIfSOShipImportReport.getSkuCode(), zsIfSOShipImportReport.getSkuCode());
          // 组合商品、套装商品  UPDATE END
          
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
              "select sum(shipping_amount) from tmall_shipping_reality_detail where shipping_amount > 0 and order_no = ? and sku_code = ? ");
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
          || zsIfSOShipImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(6))) {
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
            int updateCount = executeUpdateOrderStatus(orderHeader.getOrderNo(), "EC");
            if (updateCount != 1) {
              throw new CsvImportException();
            }
            int updateNum = executeUpdateShippingStatus(shippingHeader.getShippingNo(), "EC");
            if (updateNum != 1) {
              throw new CsvImportException();
            }
            int celNum = executeUpdateStockCancel(orderHeader.getOrderNo(), "EC");
            if (celNum != 0) {
              throw new CsvImportException();
            }
          }
        }
      } else {
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
      }
      // 如果是退货
      if (returnType) {
        Long shippingNo = 0L;
        if (lineType.equals("EC")) {
          shippingNo = DatabaseUtil.generateSequence(SequenceType.SHIPPING_NO);
        } else {
          shippingNo = DatabaseUtil.generateSequence(SequenceType.TMALL_SHIPPING_NO);
        }
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

  // 通过判断EC/TMALL插入退货信息到发货Header表
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
    } else {
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
    }

    logger.debug("INSERT SHIPPING_HEADER|TMALL_SHIPPING_HEADER : " + getInsertShippingHeaderSql());
    insertShippingHeaderStatement = createPreparedStatement(getInsertShippingHeaderSql());
    pstmt = insertShippingHeaderStatement;

    if (lineType.equals("EC")) {
      logger.debug("Table:SHIPPING_HEADER INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    } else {
      logger.debug("Table:TMALL_SHIPPING_HEADER INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
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
      params.add("BATCH:0:0:0");
      params.add(DateUtil.getSysdate());
      params.add("BATCH:0:0:0");
      params.add(DateUtil.getSysdate());
    } else {
      params.add("T" + shippingNo);
      params.add(config.getSiteShopCode());
      params.add(zsIfSOShipImportReport.getSkuCode());
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
      params.add("BATCH:0:0:0");
      params.add(DateUtil.getSysdate());
      params.add("BATCH:0:0:0");
      params.add(DateUtil.getSysdate());
    }

    logger.debug("INSERT SHIPPING_DETAIL|TMALL_SHIPPING_DETAIL : " + getInsertShippingDetailSql());
    insertShippingDetailStatement = createPreparedStatement(getInsertShippingDetailSql());
    pstmt = insertShippingDetailStatement;

    if (lineType.equals("EC")) {
      logger.debug("Table:SHIPPING_DETAIL INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    } else {
      logger.debug("Table:TMALL_SHIPPING_DETAIL INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
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

  // 通过判断EC/TMALL生成退货SHIPPING_HEADER sql
  private String getInsertShippingHeaderSql() {
    String insertSql = "";
    if (lineType.equals("EC")) {
      insertSql = " INSERT INTO " + SHIPPING_HEADER
          + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
          + SqlDialect.getDefault().getNextvalNOprm("'shipping_header_seq'") + ", ?, ?, ?, ?) ";
    } else {
      insertSql = " INSERT INTO " + TMALL_SHIPPING_HEADER
          + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME , TMALL_SHIPPING_FLG) " + " VALUES "
          + " ({1} " + SqlDialect.getDefault().getNextvalNOprm("'tmall_shipping_header_seq'") + ", ?, ?, ?, ?,?) ";
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
    } else {
      insertSql = " INSERT INTO " + TMALL_SHIPPING_DETAIL
          + " ({0} SHIPPING_DETAIL_NO,ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES "
          + " ({1} " + "'0'," + SqlDialect.getDefault().getNextvalNOprm("'tmall_shipping_detail_seq'") + ", ?, ?, ?, ?) ";
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
    } else {
      logger.debug("UPDATE TMALL_ORDER_HEADER: " + updateTmallOrderStatusSql);
      updateTmallOrderStatusStatement = createPreparedStatement(updateTmallOrderStatusSql);
      pstmt = updateTmallOrderStatusStatement;
      logger.debug("Table:TMALL_ORDER_HEADER UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
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
    } else {
      logger.debug("UPDATE TMALL_SHIPPING_HEADER: " + updateTmallShipStatusSql);
      updateTmallShipStatusStatement = createPreparedStatement(updateTmallShipStatusSql);
      pstmt = updateTmallShipStatusStatement;
      logger.debug("Table:TMALL_SHIPPING_HEADER UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    }

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }

  // 取消引当数
  public int executeUpdateStockCancel(String orderNo, String orderType) throws SQLException {
    int error = 0;

    if (orderType.equals("EC")) {
      OrderDetailDao oDetailDao = DIContainer.getDao(OrderDetailDao.class);
      List<OrderDetail> orgOrderDetails = oDetailDao.findByQuery(OrderServiceQuery.ORDER_DETAIL_LIST_QUERY, orderHeader
          .getOrderNo());
      for (OrderDetail od : orgOrderDetails) {
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
      }
    } else {
      TmallOrderDetailDao oDetailDao = DIContainer.getDao(TmallOrderDetailDao.class);
      List<TmallOrderDetail> orgOrderDetails = oDetailDao.findByQuery(OrderServiceQuery.TMALL_ORDER_DETAIL_LIST_QUERY,
          tmallOrderHeader.getOrderNo());
      for (TmallOrderDetail od : orgOrderDetails) {
        PreparedStatement pstmt = null;
        List<Object> params = new ArrayList<Object>();

        params.add(od.getPurchasingAmount().intValue());
        params.add("BATCH:0:0:0");
        params.add(DateUtil.getSysdate());
        params.add(od.getSkuCode());

        logger.debug("UPDATE STOCK: " + updateTmallStockSql);
        updateTmallStockStatement = createPreparedStatement(updateTmallStockSql);
        pstmt = updateTmallStockStatement;
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
            } else if (!StringUtil.isNullOrEmpty(od.getTmallCommodityCode())) {
              TmallCommoditySku tcs = new TmallCommoditySku();
              tcs.setNumiid(od.getTmallCommodityCode());
              tcs.setQuantity(od.getPurchasingAmount().toString());
              tcs.setUpdateType("2");
              service.updateSkuStock(tcs);
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
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
}
