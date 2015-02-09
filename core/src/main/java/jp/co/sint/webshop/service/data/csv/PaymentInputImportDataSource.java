package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.domain.AdvanceLaterFlg;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.event.PaymentEvent;
import jp.co.sint.webshop.service.order.OrderSummary;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class PaymentInputImportDataSource extends SqlImportDataSource<PaymentInputCsvSchema, PaymentInputImportCondition> {

  private PreparedStatement updateOrderStatement = null;

  private PreparedStatement updateShippingStatement = null;

  private PreparedStatement updateShippingToPayStatement = null;

  private OrderHeader orderHeader = null;

  private OrderHeader orgOrderHeader = null;

  @Override
  protected void initializeResources() {
    Logger logger = Logger.getLogger(this.getClass());
    String updateOrderQuery = "UPDATE ORDER_HEADER SET PAYMENT_STATUS = ?, PAYMENT_DATE = ?, "
        + "UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE ORDER_NO = ? ";
    String updateShippingQuery = "UPDATE SHIPPING_HEADER SET SHIPPING_STATUS = ?, "
        + "UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE ORDER_NO = ? ";
    String updateShippingToPayQuery = "UPDATE SHIPPING_HEADER SET SHIPPING_STATUS = ?, "
        + "UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE ORDER_NO = ? AND SHIPPING_STATUS = ? ";

    logger.debug(updateOrderQuery);
    logger.debug(updateShippingQuery);
    logger.debug(updateShippingToPayQuery);

    try {
      updateOrderStatement = createPreparedStatement(updateOrderQuery);
      updateShippingStatement = createPreparedStatement(updateShippingQuery);
      updateShippingToPayStatement = createPreparedStatement(updateShippingToPayQuery);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }

  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      orderHeader = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), OrderHeader.class);
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }

    // 単項目バリデーションチェック
    summary.getErrors().addAll(BeanValidator.partialValidate(orderHeader, "orderNo", "paymentDate").getErrors());
    if (summary.hasError()) {
      return summary;
    }

    // 更新対象の受注データ取得
    SimpleQuery orderQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(OrderHeader.class) + " WHERE ORDER_NO = ? ");
    orderQuery.setParameters(orderHeader.getOrderNo());
    orgOrderHeader = loadAsBean(orderQuery, OrderHeader.class);

    if (orgOrderHeader == null) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, 
          Messages.getString("service.data.csv.PaymentInputImportDataSource.0"))));
      return summary;
    }
    // 決済方法のチェック(クレジット/全額ポイント支払い/支払不要は入金日変更不可)
    if (orgOrderHeader.getPaymentMethodType().equals(PaymentMethodType.CREDITCARD.getValue())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SET_PAYMENT_CREDIT)));
      return summary;
    }

    // 決済方法のチェック(クレジット/全額ポイント支払い/支払不要は入金日変更不可)
    if (orgOrderHeader.getPaymentMethodType().equals(PaymentMethodType.POINT_IN_FULL.getValue())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SET_PAYMENT_POINT_IN_FULL)));
      return summary;
    }

    // 決済方法のチェック(クレジット/全額ポイント支払い/支払不要は入金日変更不可)
    if (orgOrderHeader.getPaymentMethodType().equals(PaymentMethodType.NO_PAYMENT.getValue())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SET_PAYMENT_NO_PAYMENT)));
      return summary;
    }
    
    // M17N 10361 追加 ここから
    // 仮受注/仮予約は入金日変更不可
    OrderStatus status = OrderStatus.fromValue(orgOrderHeader.getOrderStatus());
    if (status == OrderStatus.PHANTOM_ORDER || status == OrderStatus.PHANTOM_RESERVATION) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SET_PAYMENT_DATE_PHANTOM_ORDER_WITH_NO)));
      return summary;
    }
    // M17N 10361 追加 ここまで

    // 入金日取消の場合
    if (orderHeader.getPaymentDate() == null) {
      String query = " SELECT SHIPPING_STATUS_SUMMARY FROM ORDER_SUMMARY_VIEW WHERE ORDER_NO = ? ";
      SimpleQuery orderSummaryQuery = new SimpleQuery(query);
      orderSummaryQuery.setParameters(orderHeader.getOrderNo());
      OrderSummary orderSummary = loadAsBean(orderSummaryQuery, OrderSummary.class);

      if (!orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())) {
        if (orgOrderHeader.getAdvanceLaterFlg().equals(AdvanceLaterFlg.ADVANCE.longValue())) {
          summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.DELETE_PAYMENT_IS_SHIPPED)));
        }
      }

      // 入金日設定の場合
    } else {
      if (orgOrderHeader.getOrderStatus().equals(OrderStatus.CANCELLED.longValue())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.PAYMENT_IS_CANCELLED)));
      // 10.1.1 10006 追加 ここから
      } else if (!DateUtil.isCorrectAppDate(orderHeader.getPaymentDate(), false)) {
        String min = Integer.toString(DIContainer.getWebshopConfig().getApplicationMinYear());
        String max = Integer.toString(DIContainer.getWebshopConfig().getApplicationMaxYear());
        summary.getErrors().add(new ValidationResult(null, null,
            Message.get(CsvMessage.NOT_IN_RANGE, Messages.getString("service.data.csv.PaymentInputImportDataSource.1"), min, max)));
      // 10.1.1 10006 追加 ここまで
      }
    }
    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    try {
      // 入金日取消の場合
      if (orderHeader.getPaymentDate() == null) {
        // 既に入金日が削除されている場合、何もしない
        if (orgOrderHeader.getPaymentDate() == null) {
          return;
        }
        // 受注ヘッダ更新
        List<Object> orderParams = new ArrayList<Object>();

        orderParams.add(PaymentStatus.NOT_PAID.longValue());
        orderParams.add(null);

        orderParams.add(getCondition().getLoginInfo().getRecordingFormat());
        orderParams.add(DateUtil.getSysdate());

        orderParams.add(orderHeader.getOrderNo());

        logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(orderParams, Object.class)));

        DatabaseUtil.bindParameters(updateOrderStatement, ArrayUtil.toArray(orderParams, Object.class));
        int updCount = updateOrderStatement.executeUpdate();
        if (updCount != 1) {
          throw new CsvImportException();
        }

        // 先払いの場合、受注に紐づく全出荷の出荷ステータスを未出荷に変更
        if (orgOrderHeader.getAdvanceLaterFlg().equals(AdvanceLaterFlg.ADVANCE.longValue())) {
          List<Object> shippingParams = new ArrayList<Object>();

          shippingParams.add(ShippingStatus.NOT_READY.longValue());

          shippingParams.add(getCondition().getLoginInfo().getRecordingFormat());
          shippingParams.add(DateUtil.getSysdate());

          shippingParams.add(orderHeader.getOrderNo());

          logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(shippingParams, Object.class)));

          DatabaseUtil.bindParameters(updateShippingStatement, ArrayUtil.toArray(shippingParams, Object.class));
          updateShippingStatement.executeUpdate();

        }
        // 入金日設定の場合
      } else {
        // 受注ヘッダ更新
        List<Object> orderParams = new ArrayList<Object>();

        orderParams.add(PaymentStatus.PAID.longValue());
        orderParams.add(orderHeader.getPaymentDate());

        orderParams.add(getCondition().getLoginInfo().getRecordingFormat());
        orderParams.add(DateUtil.getSysdate());

        orderParams.add(orderHeader.getOrderNo());

        logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(orderParams, Object.class)));

        DatabaseUtil.bindParameters(updateOrderStatement, ArrayUtil.toArray(orderParams, Object.class));
        int updCount = updateOrderStatement.executeUpdate();
        if (updCount != 1) {
          throw new CsvImportException();
        }

        // 先払いの場合、受注に紐づく全出荷の出荷ステータスを出荷可能に変更
        if (orgOrderHeader.getAdvanceLaterFlg().equals(AdvanceLaterFlg.ADVANCE.longValue())) {
          List<Object> shippingParams = new ArrayList<Object>();

          shippingParams.add(ShippingStatus.READY.longValue());

          shippingParams.add(getCondition().getLoginInfo().getRecordingFormat());
          shippingParams.add(DateUtil.getSysdate());

          shippingParams.add(orderHeader.getOrderNo());
          shippingParams.add(ShippingStatus.NOT_READY.longValue());

          logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(shippingParams, Object.class)));

          DatabaseUtil.bindParameters(updateShippingToPayStatement, ArrayUtil.toArray(shippingParams, Object.class));
          updateShippingToPayStatement.executeUpdate();

        }

        if (getCondition().getDelegate() != null) {
          getCondition().getDelegate().paymentDateUpdated(new PaymentEvent(orderHeader.getOrderNo(), orderHeader.getPaymentDate()));
        }
      }
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
    DatabaseUtil.closeResource(updateOrderStatement);
    DatabaseUtil.closeResource(updateShippingStatement);
    DatabaseUtil.closeResource(updateShippingToPayStatement);
  }

}
