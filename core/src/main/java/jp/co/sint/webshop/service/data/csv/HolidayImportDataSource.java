package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CommitMode;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.dto.Holiday;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class HolidayImportDataSource extends SqlImportDataSource<HolidayCsvSchema, HolidayImportCondition> {

  /** 削除対象年月 */
  private Map<String, Boolean> deleteDateSet = new HashMap<String, Boolean>();

  /** INSERT用Statement */
  private PreparedStatement insertStatement = null;

  /** DELETE用Statement */
  private PreparedStatement deleteStatement = null;

  private Holiday holiday = null;

  @Override
  public CommitMode getCommitMode() {
    return CommitMode.BULK_COMMIT;
  }

  @Override
  protected void initializeResources() {
    Logger logger = Logger.getLogger(this.getClass());
    String insertQuery = CsvUtil.buildInsertQuery(getSchema());
    String deleteQuery = "DELETE FROM HOLIDAY WHERE HOLIDAY BETWEEN " + SqlDialect.getDefault().toDatetime() + " AND "
        + SqlDialect.getDefault().toDatetime();
    logger.debug("INSERT statement: " + insertQuery);
    logger.debug("DELETE statement: " + deleteQuery);

    try {
      insertStatement = createPreparedStatement(insertQuery);
      deleteStatement = createPreparedStatement(deleteQuery);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      holiday = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), Holiday.class);
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }
    holiday.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    DatabaseUtil.setUserStatus(holiday, getCondition().getLoginInfo());

    // 単項目バリデーションチェック
    summary.getErrors().addAll(BeanValidator.validate(holiday).getErrors());
    if (summary.hasError()) {
      return summary;
    }

    // ショップコード相違チェック
    if (StringUtil.hasValue(getCondition().getShopCode())) {
      if (!holiday.getShopCode().equals(getCondition().getShopCode())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.VALIDATE_SHOPCODE)));
        return summary;
      }
    }

    // ショップ存在チェック
    SimpleQuery shopCountQuery = new SimpleQuery("SELECT COUNT(*) FROM SHOP WHERE SHOP_CODE = ? ");
    shopCountQuery.setParameters(holiday.getShopCode());
    Long shopCount = Long.valueOf(executeScalar(shopCountQuery).toString());
    if (shopCount == 0L) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
              .getString("service.data.csv.HolidayImportDataSource.0"))));
      return summary;
    }

    // 10.1.1 10006 追加 ここから
    // 各期間の日付範囲チェック
    if (!DateUtil.isCorrectAppDate(holiday.getHoliday(), false)) {
      String min = Integer.toString(DIContainer.getWebshopConfig().getApplicationMinYear());
      String max = Integer.toString(DIContainer.getWebshopConfig().getApplicationMaxYear());
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.NOT_IN_RANGE, Messages
              .getString("service.data.csv.HolidayImportDataSource.1"), min, max)));
    }
    // 10.1.1 10006 追加 ここまで

    if (summary.isValid()) {
      String year = DateUtil.getYYYY(holiday.getHoliday());
      String month = DateUtil.getMM(holiday.getHoliday());
      if (!deleteDateSet.containsKey(year + month)) {
        deleteDateSet.put(year + month, true);
      }
    }

    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());
    try {
      String targetYear = DateUtil.getYYYY(holiday.getHoliday());
      String targetMonth = DateUtil.getMM(holiday.getHoliday());

      if (deleteDateSet.get(targetYear + targetMonth)) {

        String startDate = targetYear + "/" + targetMonth + "/01";
        String endDate = targetYear + "/" + targetMonth + "/" + DateUtil.getEndDay(holiday.getHoliday());

        List<Object> deleteParams = new ArrayList<Object>();
        deleteParams.add(startDate);
        deleteParams.add(endDate);

        logger.debug("DELETE Parameters: " + Arrays.toString(ArrayUtil.toArray(deleteParams, Object.class)));
        DatabaseUtil.bindParameters(deleteStatement, ArrayUtil.toArray(deleteParams, Object.class));
        deleteStatement.execute();

        deleteDateSet.put(targetYear + targetMonth, false);
      }

      List<Object> params = new ArrayList<Object>();

      params.add(holiday.getShopCode());
      params.add(holiday.getHoliday());

      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());
      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

      DatabaseUtil.bindParameters(insertStatement, ArrayUtil.toArray(params, Object.class));

      int updCount = insertStatement.executeUpdate();
      if (updCount != 1) {
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
}
