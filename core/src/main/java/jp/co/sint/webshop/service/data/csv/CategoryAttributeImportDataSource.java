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
import jp.co.sint.webshop.data.dto.CategoryAttribute;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class CategoryAttributeImportDataSource extends
    SqlImportDataSource<CategoryAttributeCsvSchema, CategoryAttributeImportCondition> {

  /** INSERT用Statement */
  private PreparedStatement insertStatement;

  /** UPDATE用Statement */
  private PreparedStatement updateStatement;

  /** 存在チェック用Statement */
  private PreparedStatement selectStatement = null;

  private CategoryAttribute categoryAttribute = null;

  @Override
  protected void initializeResources() {
    Logger logger = Logger.getLogger(this.getClass());
    String insertQuery = CsvUtil.buildInsertQuery(getSchema());
    String updateQuery = CsvUtil.buildUpdateQuery(getSchema());
    String selectQuery = CsvUtil.buildCheckExistsQuery(getSchema());
    logger.debug("INSERT statement: " + insertQuery);
    logger.debug("UPDATE statement: " + updateQuery);
    logger.debug("SELECT statement: " + selectQuery);

    try {
      insertStatement = createPreparedStatement(insertQuery);
      updateStatement = createPreparedStatement(updateQuery);
      selectStatement = createPreparedStatement(selectQuery);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      categoryAttribute = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CategoryAttribute.class);
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }
    categoryAttribute.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    DatabaseUtil.setUserStatus(categoryAttribute, getCondition().getLoginInfo());

    // 単項目バリデーションチェック
    summary.getErrors().addAll(BeanValidator.validate(categoryAttribute).getErrors());
    // 10.1.1 10019 追加 ここから
    if (NumUtil.isNegative(categoryAttribute.getCategoryAttributeNo())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages.getString("service.data.csv.CategoryAttributeImportDataSource.2"))));
    }
    // 10.1.1 10019 追加 ここまで
    // 10.1.3 10062 追加 ここから
    int categoryAttributeMaxValue = DIContainer.getWebshopConfig().getCategoryAttributeMaxCount();
    StringBuilder range = new StringBuilder();
    for (int i = 0; i < categoryAttributeMaxValue; i++) {
      range.append(i);
      if (i != categoryAttributeMaxValue - 1) {
        range.append(",");
      }
    }
    if (categoryAttribute.getCategoryAttributeNo() >= categoryAttributeMaxValue) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_INPUT_RANGE, Messages.getString("service.data.csv.CategoryAttributeImportDataSource.2"), range.toString())));
    }
    // 10.1.3 10062 追加 ここまで
    if (summary.hasError()) {
      return summary;
    }

    // カテゴリデータ存在チェック
    SimpleQuery categoryCountQuery = new SimpleQuery("SELECT COUNT(*) FROM CATEGORY WHERE CATEGORY_CODE = ? ");
    categoryCountQuery.setParameters(categoryAttribute.getCategoryCode());
    Long categoryCount = Long.valueOf(executeScalar(categoryCountQuery).toString());
    if (categoryCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages.getString("service.data.csv.CategoryAttributeImportDataSource.0"))));
    }

    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());
    try {
      PreparedStatement pstmt = null;
      List<Object> params = new ArrayList<Object>();
      if (exists(selectStatement, categoryAttribute.getCategoryCode(), Long.toString(categoryAttribute.getCategoryAttributeNo()))) {

        params.add(categoryAttribute.getCategoryAttributeName());
        params.add(getCondition().getLoginInfo().getRecordingFormat());
        params.add(DateUtil.getSysdate());

        params.add(categoryAttribute.getCategoryCode());
        params.add(categoryAttribute.getCategoryAttributeNo());

        pstmt = updateStatement;
        logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
      } else {

        params.add(categoryAttribute.getCategoryCode());
        params.add(categoryAttribute.getCategoryAttributeNo());
        params.add(categoryAttribute.getCategoryAttributeName());

        params.add(getCondition().getLoginInfo().getRecordingFormat());
        params.add(DateUtil.getSysdate());

        params.add(getCondition().getLoginInfo().getRecordingFormat());
        params.add(DateUtil.getSysdate());

        pstmt = insertStatement;
        logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
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
    } catch (RuntimeException e) {
      throw new CsvImportException(e);
    }
  }

  @Override
  protected void clearResources() {
    DatabaseUtil.closeResource(insertStatement);
    DatabaseUtil.closeResource(updateStatement);
    DatabaseUtil.closeResource(selectStatement);
  }
}
