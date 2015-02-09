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
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.ReturnItemType;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.order.OrderServiceQuery;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class DeliverySlipNoImportDataSource extends SqlImportDataSource<DeliverySlipNoCsvSchema, DeliverySlipNoImportCondition> {

  /** UPDATE用Statement */
  private PreparedStatement updateStatement = null;

  /** 存在チェック用Statement */
  private PreparedStatement selectStatement = null;

  private ShippingHeader shippingHeader = null;

  @Override
  protected void initializeResources() {
    Logger logger = Logger.getLogger(this.getClass());
    String updateQuery = OrderServiceQuery.UPDATE_SHIPPING_HEADER_DELIVERY_SLIP_NO_QUERY;
    String selectQuery = CsvUtil.buildCheckExistsQuery(getSchema());
    logger.debug("UPDATE statement: " + updateQuery);

    try {
      updateStatement = createPreparedStatement(updateQuery);
      selectStatement = createPreparedStatement(selectQuery);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      shippingHeader = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), ShippingHeader.class);
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }
    shippingHeader.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    DatabaseUtil.setUserStatus(shippingHeader, getCondition().getLoginInfo());

    // 単項目バリデーションチェック
    summary.getErrors().addAll(BeanValidator.partialValidate(shippingHeader, "shippingNo", "deliverySlipNo").getErrors());

    // 宅配便伝票番号nullチェック
    if (shippingHeader.getDeliverySlipNo() == null) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.DELIVERY_SLIP_NO_REQUIRED)));
    }

    if (summary.hasError()) {
      return summary;
    }

    // 出荷情報存在チェック
    SimpleQuery shippingCountQuery = new SimpleQuery("SELECT COUNT(*) FROM SHIPPING_HEADER WHERE SHIPPING_NO = ? ");
    shippingCountQuery.setParameters(shippingHeader.getShippingNo());
    Long shippingCount = Long.valueOf(executeScalar(shippingCountQuery).toString());
    if (shippingCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST,
          Messages.getString("service.data.csv.DeliverySlipNoImportDataSource.0"))));
      return summary;
    }

    SimpleQuery shippingQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(ShippingHeader.class) + " WHERE SHIPPING_NO = ? ");
    shippingQuery.setParameters(shippingHeader.getShippingNo());
    ShippingHeader orgShippingHeader = loadAsBean(shippingQuery, ShippingHeader.class);

    // キャンセル・返品チェック
    if (orgShippingHeader.getShippingStatus().equals(ShippingStatus.CANCELLED.longValue())
        || orgShippingHeader.getReturnItemType().equals(ReturnItemType.RETURNED.longValue())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.CANCELL_OR_RETURN)));
    }

    // 売上確定チェック
    if (orgShippingHeader.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.FIXED_DATA)));
    }

    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());
    try {
      PreparedStatement pstmt = null;
      List<Object> params = new ArrayList<Object>();
      if (exists(selectStatement, shippingHeader.getShippingNo())) {

        params.add(shippingHeader.getDeliverySlipNo());
        params.add(getCondition().getLoginInfo().getRecordingFormat());
        params.add(DateUtil.getSysdate());

        params.add(shippingHeader.getShippingNo());

        pstmt = updateStatement;
        logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
      } else {
        logger.debug(MessageFormat.format(Messages.log("service.data.csv.DeliverySlipNoImportDataSource.1"),
            shippingHeader.getShippingNo(),
            Message.get(CsvMessage.NOT_EXIST,
                Messages.log("service.data.csv.DeliverySlipNoImportDataSource.2"))));
        throw new CsvImportException(Messages.getString("service.data.csv.DeliverySlipNoImportDataSource.3"));
      }

      DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

      int updCount = pstmt.executeUpdate();
      if (updCount != 1) {
        throw new CsvImportException();
      }
    } catch (SQLException e) {
      throw new CsvImportException(e);
    } catch (CsvImportException e) {
      throw e;
    } catch (Exception e) {
      throw new CsvImportException(e);
    }

  }

}
