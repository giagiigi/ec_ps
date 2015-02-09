package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.StockManager;
import jp.co.sint.webshop.data.StockUnit;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.domain.AdvanceLaterFlg;
import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.ReturnItemType;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.hibernate.StockManagerImpl;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.event.ShippingEvent;
import jp.co.sint.webshop.service.order.InputShippingReport;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class ShippingReportImportDataSource extends SqlImportDataSource<ShippingReportCsvSchema, ShippingReportImportCondition> {

  private PreparedStatement updateStatement = null;

  private PreparedStatement updateOrderStatement = null;

  private InputShippingReport inputShippingReport = null;

  private Logger logger = Logger.getLogger(this.getClass()); // 10.1.3 10106 追加

  @Override
  protected void initializeResources() {
    // Logger logger = Logger.getLogger(this.getClass()); // 10.1.4 K00168 削除
    String updateOrderQuery = "UPDATE ORDER_HEADER SET UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE ORDER_NO = ? ";
    String updateQuery = "UPDATE SHIPPING_HEADER SET SHIPPING_STATUS = ?, SHIPPING_DIRECT_DATE = ?, SHIPPING_DATE = ?,"
        + " ARRIVAL_DATE = ?, ARRIVAL_TIME_START = ?, ARRIVAL_TIME_END = ?, DELIVERY_SLIP_NO = ?, UPDATED_USER = ? "
        + " , UPDATED_DATETIME = ? WHERE SHIPPING_NO = ? ";
    logger.debug(updateOrderQuery);
    logger.debug(updateQuery);

    try {
      updateOrderStatement = createPreparedStatement(updateOrderQuery);
      updateStatement = createPreparedStatement(updateQuery);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }

  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      inputShippingReport = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), InputShippingReport.class);
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }

    // 単項目バリデーションチェック
    summary.getErrors().addAll(BeanValidator.validate(inputShippingReport).getErrors());
    if (summary.hasError()) {
      return summary;
    }

    // 出荷情報存在チェック
    SimpleQuery shippingCountQuery = new SimpleQuery("SELECT COUNT(*) FROM SHIPPING_HEADER WHERE SHIPPING_NO = ? ");
    shippingCountQuery.setParameters(inputShippingReport.getShippingNo());
    Long shippingCount = Long.valueOf(executeScalar(shippingCountQuery).toString());
    if (shippingCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST,
          Messages.getString("service.data.csv.ShippingReportImportDataSource.0"))));
      return summary;
    }

    SimpleQuery shippingQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(ShippingHeader.class) + " WHERE SHIPPING_NO = ? ");
    shippingQuery.setParameters(inputShippingReport.getShippingNo());
    ShippingHeader shippingHeader = loadAsBean(shippingQuery, ShippingHeader.class);

    // ショップコード相違チェック
    if (StringUtil.hasValue(getCondition().getShopCode())) {
      if (!shippingHeader.getShopCode().equals(getCondition().getShopCode())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.VALIDATE_SHOPCODE)));
        return summary;
      }
    }

    // ショップデータ存在チェック
    SimpleQuery shopCountQuery = new SimpleQuery("SELECT COUNT(*) FROM SHOP WHERE SHOP_CODE = ? ");
    shopCountQuery.setParameters(shippingHeader.getShopCode());
    Long shopCount = Long.valueOf(executeScalar(shopCountQuery).toString());
    if (shopCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST,
          Messages.getString("service.data.csv.ShippingReportImportDataSource.1"))));
    }

    // 受注情報存在チェック
    SimpleQuery orderCountQuery = new SimpleQuery("SELECT COUNT(*) FROM ORDER_HEADER WHERE ORDER_NO = ? ");
    orderCountQuery.setParameters(shippingHeader.getOrderNo());
    Long orderCount = Long.valueOf(executeScalar(orderCountQuery).toString());
    if (orderCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST,
          Messages.getString("service.data.csv.ShippingReportImportDataSource.2"))));
      return summary;
    }

    SimpleQuery orderQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(OrderHeader.class) + " WHERE ORDER_NO = ? ");
    orderQuery.setParameters(shippingHeader.getOrderNo());
    OrderHeader orderHeader = loadAsBean(orderQuery, OrderHeader.class);

    // 受注情報予約チェック(予約の場合は出荷不可)
    if (orderHeader.getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPING_IS_RESERVED)));
    }
    
    // M17N 10361 追加 ここから
    if (orderHeader.getOrderStatus().equals(OrderStatus.PHANTOM_ORDER.longValue())
        || orderHeader.getOrderStatus().equals(OrderStatus.PHANTOM_RESERVATION.longValue())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPING_IS_PHANTOM, shippingHeader.getShippingNo())));
    }
    // M17N 10361 追加 ここまで

    // 売上確定ステータスが「確定済み」の場合はステータスの変更不可
    if (shippingHeader.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.FIXED_DATA)));
    }

    // 返品ステータスが「返品」の場合はステータスの変更不可
    if (shippingHeader.getReturnItemType().equals(ReturnItemType.RETURNED.longValue())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPING_IS_RETURNED)));
    }
    // 10.1.4 10183 追加 ここから
    // 基幹連携していない場合は、「未出荷(入金待ちor出荷可能)」→「出荷済み」への移行不可
    if (orderHeader.getDataTransportStatus().equals(DataTransportStatus.NOT_TRANSPORTED.longValue())
        && (shippingHeader.getShippingStatus().equals(ShippingStatus.NOT_READY.longValue())
            || shippingHeader.getShippingStatus().equals(ShippingStatus.READY.longValue()))) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPING_IS_NOT_IN_PROCESSING)));
    }
    // 10.1.4 10183 追加 ここまで
    // 出荷日が受注日よりも前の日付の場合はエラー
    if (inputShippingReport.getShippingDate().before(DateUtil.truncateDate(orderHeader.getOrderDatetime()))) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPINGDATE_BEFORE_ORDERDATE)));
    }

    // 出荷日が出荷指示日よりも前の日付の場合はエラー
    if (inputShippingReport.getShippingDirectDate() != null
        && inputShippingReport.getShippingDate().before(inputShippingReport.getShippingDirectDate())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPINGDATE_BEFORE_DIRECTDATE)));
    }

    // 到着予定日が出荷日よりも前の日付の場合はエラー
    if (inputShippingReport.getArrivalDate() != null) {
      if (inputShippingReport.getArrivalDate().before(inputShippingReport.getShippingDate())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.ARRIVALDATE_BEFORE_SHIPPINGDATE)));
      }
    }

    // 出荷指示日が受注日よりも前の日付の場合はエラー
    if (inputShippingReport.getShippingDirectDate() != null
        && inputShippingReport.getShippingDirectDate().before(DateUtil.truncateDate(orderHeader.getOrderDatetime()))) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPING_DIRECT_BEFORE_ORDERDATE)));
    }

    // 10.1.1 10006 追加 ここから
    // 日付範囲チェック
    String min = Integer.toString(DIContainer.getWebshopConfig().getApplicationMinYear());
    String max = Integer.toString(DIContainer.getWebshopConfig().getApplicationMaxYear());
    if (!DateUtil.isCorrectAppDate(inputShippingReport.getShippingDirectDate(), true)) {
      summary.getErrors().add(new ValidationResult(null, null,
          Message.get(CsvMessage.NOT_IN_RANGE, Messages.getString("service.data.csv.ShippingReportImportDataSource.4"), min, max)));
    }
    if (!DateUtil.isCorrectAppDate(inputShippingReport.getShippingDate(), false)) {
      summary.getErrors().add(new ValidationResult(null, null,
          Message.get(CsvMessage.NOT_IN_RANGE, Messages.getString("service.data.csv.ShippingReportImportDataSource.5"), min, max)));
    }
    if (!DateUtil.isCorrectAppDate(inputShippingReport.getArrivalDate(), true)) {
      summary.getErrors().add(new ValidationResult(null, null,
          Message.get(CsvMessage.NOT_IN_RANGE, Messages.getString("service.data.csv.ShippingReportImportDataSource.6"), min, max)));
    }
    // 10.1.1 10006 追加 ここまで

    // 出荷指示日存在チェック(存在しなかった場合、出荷日を設定する)
    if (inputShippingReport.getShippingDirectDate() == null) {
      inputShippingReport.setShippingDirectDate(inputShippingReport.getShippingDate());
    }

    // 到着予定時間がどちらか一方のみ入力されていないかチェック
    if (inputShippingReport.getArrivalTimeStart() != null || inputShippingReport.getArrivalTimeEnd() != null) {
      if (inputShippingReport.getArrivalTimeStart() == null || inputShippingReport.getArrivalTimeEnd() == null) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.ARRIVAL_TIME_BOTH_REQUIRED)));
      }
    }

    // 到着予定時間の開始終了が入れ替わっていないかチェック
    if (inputShippingReport.getArrivalTimeStart() != null && inputShippingReport.getArrivalTimeEnd() != null) {
      if (NumUtil.toPrimitiveLong(inputShippingReport.getArrivalTimeStart()) > NumUtil.toPrimitiveLong(inputShippingReport
          .getArrivalTimeEnd())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.ARRIVAL_TIME_COMPARISON_ERROR)));
      }
    }

    // 受注キャンセルチェック
    if (orderHeader.getOrderStatus().equals(OrderStatus.CANCELLED.longValue())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SHIPPING_IS_CANCELLED)));
    }

    // 先払い区分が「先払い」の場合は、出荷ステータスが入金街の場合のみ出荷指示不可
    if (orderHeader.getAdvanceLaterFlg().equals(AdvanceLaterFlg.ADVANCE.longValue())) {
      if (shippingHeader.getShippingStatus().equals(ShippingStatus.NOT_READY.longValue())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NO_PAY_AND_PAYMENT_ADVANCE)));
      }
    }

    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    // Logger logger = Logger.getLogger(this.getClass()); // 10.1.4 K00168 削除

    try {
      List<Object> updateOrderParams = new ArrayList<Object>();

      // 受注ヘッダ更新者・更新日の更新
      updateOrderParams.add(getCondition().getLoginInfo().getRecordingFormat());
      updateOrderParams.add(DateUtil.getSysdate());

      SimpleQuery shippingQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(ShippingHeader.class) + " WHERE SHIPPING_NO = ? ");
      shippingQuery.setParameters(inputShippingReport.getShippingNo());
      ShippingHeader shippingHeader = loadAsBean(shippingQuery, ShippingHeader.class);

      updateOrderParams.add(shippingHeader.getOrderNo());

      logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(updateOrderParams, Object.class)));

      DatabaseUtil.bindParameters(updateOrderStatement, ArrayUtil.toArray(updateOrderParams, Object.class));
      int updCount = updateOrderStatement.executeUpdate();
      if (updCount != 1) {
        throw new CsvImportException();
      }

      // 在庫更新
      SimpleQuery shippingDetailQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(ShippingDetail.class)
          + " WHERE SHIPPING_NO = ? ");
      shippingDetailQuery.setParameters(inputShippingReport.getShippingNo());
      List<ShippingDetail> shippingDetailList = loadAsBeanList(shippingDetailQuery, ShippingDetail.class);
      List<StockUnit> stockUtilList = new ArrayList<StockUnit>();
      for (ShippingDetail detail : shippingDetailList) {
        StockUnit unit = new StockUnit();
        unit.setShopCode(detail.getShopCode());
        unit.setSkuCode(detail.getSkuCode());
        unit.setStockIODate(DateUtil.getSysdate());
        unit.setQuantity(detail.getPurchasingAmount().intValue());
        unit.setLoginInfo(getCondition().getLoginInfo());
        stockUtilList.add(unit);
      }

      // 10.1.4 10183 修正 ここから
//      StockManager stockManager = new StockManagerImpl(getConnection());
//      if (!stockManager.shipping(stockUtilList)) {
//        throw new CsvImportException("在庫引き当て処理が失敗しました。");
//      }
      if (ShippingStatus.IN_PROCESSING.longValue().equals(shippingHeader.getShippingStatus())
          || ShippingStatus.READY.longValue().equals(shippingHeader.getShippingStatus())) {
        StockManager stockManager = new StockManagerImpl(getConnection());
        if (!stockManager.shipping(stockUtilList)) {
          throw new CsvImportException(Messages.getString("service.data.csv.ShippingReportImportDataSource.3"));
        }
        logger.info(Messages.getString("service.data.csv.ShippingReportImportDataSource.7"));
      } else {
        logger.info(MessageFormat.format(Messages.getString("service.data.csv.ShippingReportImportDataSource.8"),
            shippingHeader.getShippingNo(), shippingHeader.getShippingStatus()));
      }
      // 10.1.4 10183 修正 ここまで

      // 出荷ヘッダ更新
      List<Object> updateShippingParams = new ArrayList<Object>();

      updateShippingParams.add(ShippingStatus.SHIPPED.longValue());
      updateShippingParams.add(inputShippingReport.getShippingDirectDate());
      updateShippingParams.add(inputShippingReport.getShippingDate());
      updateShippingParams.add(inputShippingReport.getArrivalDate());
      updateShippingParams.add(inputShippingReport.getArrivalTimeStart());
      updateShippingParams.add(inputShippingReport.getArrivalTimeEnd());
      updateShippingParams.add(inputShippingReport.getDeliverySlipNo());
      updateShippingParams.add(getCondition().getLoginInfo().getRecordingFormat());
      updateShippingParams.add(DateUtil.getSysdate());

      updateShippingParams.add(inputShippingReport.getShippingNo());

      logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(updateShippingParams, Object.class)));

      DatabaseUtil.bindParameters(updateStatement, ArrayUtil.toArray(updateShippingParams, Object.class));
      int updShippingCount = updateStatement.executeUpdate();
      if (updShippingCount != 1) {
        throw new CsvImportException();
      }
      // 10.1.3 10106 削除 ここから
      // if (getCondition().getDelegate() != null) {
      //   getCondition().getDelegate().shippingUpdated(new ShippingEvent(inputShippingReport.getShippingNo()));
      // }
      // 10.1.3 10106 削除 ここまで

    } catch (SQLException e) {
      throw new CsvImportException(e);
    } catch (CsvImportException e) {
      throw e;
    } catch (RuntimeException e) {
      throw new CsvImportException(e);
    }
  }

  @Override
  protected void clearResources() {
    DatabaseUtil.closeResource(updateStatement);
    DatabaseUtil.closeResource(updateOrderStatement);
  }

  // 10.1.3 10106 追加 ここから
  public void commit() {
    super.commit();
    // コミット成功後、出荷報告メールを送信する
    if (getCondition().getDelegate() != null) {
      try {
        getCondition().getDelegate().shippingUpdated(new ShippingEvent(inputShippingReport.getShippingNo()),"");
      } catch (Exception e) {
        logger.error(MessageFormat.format(Messages.getString("service.data.csv.ShippingReportImportDataSource.9"),
            inputShippingReport.getShippingNo()));
        logger.error(e);
      }
    }
  }
  // 10.1.3 10106 追加 ここまで
}
