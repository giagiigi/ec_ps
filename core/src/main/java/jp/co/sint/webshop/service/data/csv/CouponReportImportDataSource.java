package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.domain.CouponUsedFlg;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.customer.InputCouponReport;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class CouponReportImportDataSource extends SqlImportDataSource<CouponReportCsvSchema, CouponReportImportCondition> {

  private PreparedStatement updateStatement = null;

  private PreparedStatement updateOrderStatement = null;

  private InputCouponReport inputCouponReport = null;

  private Logger logger = Logger.getLogger(this.getClass());

  @Override
  protected void initializeResources() {
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
      inputCouponReport = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), InputCouponReport.class);
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }

    // 単項目バリデーションチェック
    summary.getErrors().addAll(BeanValidator.validate(inputCouponReport).getErrors());
    if (summary.hasError()) {
      return summary;
    }

    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {

    try {
      List<Object> updateOrderParams = new ArrayList<Object>();


      logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(updateOrderParams, Object.class)));

      DatabaseUtil.bindParameters(updateOrderStatement, ArrayUtil.toArray(updateOrderParams, Object.class));
      int updCount = updateOrderStatement.executeUpdate();
      if (updCount != 1) {
        throw new CsvImportException();
      }

     
     
      List<Object> updateCouponParams = new ArrayList<Object>();

      updateCouponParams.add(CouponUsedFlg.PHANTOM_COUPON.longValue());
      updateCouponParams.add(getCondition().getLoginInfo().getRecordingFormat());
      updateCouponParams.add(DateUtil.getSysdate());


      logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(updateCouponParams, Object.class)));

      DatabaseUtil.bindParameters(updateStatement, ArrayUtil.toArray(updateCouponParams, Object.class));
      int updShippingCount = updateStatement.executeUpdate();
      if (updShippingCount != 1) {
        throw new CsvImportException();
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
    DatabaseUtil.closeResource(updateStatement);
    DatabaseUtil.closeResource(updateOrderStatement);
  }

  public void commit() {
    super.commit();
  }
}
