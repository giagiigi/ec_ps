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
import jp.co.sint.webshop.data.dto.CategoryAttributeValue;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
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

public class CategoryAttributeValueImportDataSource extends
    SqlImportDataSource<CategoryAttributeValueCsvSchema, CategoryAttributeValueImportCondition> {

  /** INSERT用Statement */
  private PreparedStatement insertStatement;

  /** UPDATE用Statement */
  private PreparedStatement updateStatement;

  /** 存在チェック用Statement */
  private PreparedStatement selectStatement = null;

  private CategoryAttributeValue categoryAttributeValue = null;

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
      categoryAttributeValue = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CategoryAttributeValue.class);
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }
    categoryAttributeValue.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    DatabaseUtil.setUserStatus(categoryAttributeValue, getCondition().getLoginInfo());

    // 単項目バリデーションチェック
    summary.getErrors().addAll(BeanValidator.validate(categoryAttributeValue).getErrors());
    // 10.1.1 10019 追加 ここから
    if (NumUtil.isNegative(categoryAttributeValue.getCategoryAttributeNo())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
          .getString("service.data.csv.CategoryAttributeValueImportDataSource.4"))));
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
    if (categoryAttributeValue.getCategoryAttributeNo() >= categoryAttributeMaxValue) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_INPUT_RANGE, Messages
          .getString("service.data.csv.CategoryAttributeValueImportDataSource.4"), range.toString())));
    }
    // 10.1.3 10062 追加 ここまで
    if (summary.hasError()) {
      return summary;
    }

    // カテゴリデータ存在チェック
    SimpleQuery categoryCountQuery = new SimpleQuery("SELECT COUNT(*) FROM CATEGORY WHERE CATEGORY_CODE = ? ");
    categoryCountQuery.setParameters(categoryAttributeValue.getCategoryCode());
    Long categoryCount = Long.valueOf(executeScalar(categoryCountQuery).toString());
    if (categoryCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
          .getString("service.data.csv.CategoryAttributeValueImportDataSource.0"))));
    }

    // カテゴリ属性名称データ存在チェック
    SimpleQuery categoryAttributeCountQuery = new SimpleQuery(
        "SELECT COUNT(*) FROM CATEGORY_ATTRIBUTE WHERE CATEGORY_CODE = ? AND CATEGORY_ATTRIBUTE_NO = ? ");
    categoryAttributeCountQuery.setParameters(categoryAttributeValue.getCategoryCode(), categoryAttributeValue
        .getCategoryAttributeNo());
    Long categoryAttributeCount = Long.valueOf(executeScalar(categoryAttributeCountQuery).toString());
    if (categoryAttributeCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
          .getString("service.data.csv.CategoryAttributeValueImportDataSource.1"))));
    }

    // ショップコード相違チェック
    if (StringUtil.hasValue(getCondition().getShopCode())) {
      if (!categoryAttributeValue.getShopCode().equals(getCondition().getShopCode())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.VALIDATE_SHOPCODE)));
        return summary;
      }
    }

    // ショップデータ存在チェック
    SimpleQuery shopCountQuery = new SimpleQuery("SELECT COUNT(*) FROM SHOP WHERE SHOP_CODE = ? ");
    shopCountQuery.setParameters(categoryAttributeValue.getShopCode());
    Long shopCount = Long.valueOf(executeScalar(shopCountQuery).toString());
    if (shopCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
          .getString("service.data.csv.CategoryAttributeValueImportDataSource.2"))));
    }

    // 商品データ存在チェック
    SimpleQuery commodityCountQuery = new SimpleQuery(
        "SELECT COUNT(*) FROM COMMODITY_HEADER WHERE SHOP_CODE = ? AND COMMODITY_CODE = ? ");
    commodityCountQuery.setParameters(categoryAttributeValue.getShopCode(), categoryAttributeValue.getCommodityCode());
    Long commodityCount = Long.valueOf(executeScalar(commodityCountQuery).toString());
    if (commodityCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
          .getString("service.data.csv.CategoryAttributeValueImportDataSource.3"))));
    }

    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());
    try {
      PreparedStatement pstmt = null;
      List<Object> params = new ArrayList<Object>();
      if (exists(selectStatement, categoryAttributeValue.getShopCode(), categoryAttributeValue.getCategoryCode(), Long
          .toString(categoryAttributeValue.getCategoryAttributeNo()), categoryAttributeValue.getCommodityCode())) {

        params.add(categoryAttributeValue.getCategoryAttributeValue());
        params.add(getCondition().getLoginInfo().getRecordingFormat());
        params.add(DateUtil.getSysdate());

        params.add(categoryAttributeValue.getShopCode());
        params.add(categoryAttributeValue.getCategoryCode());
        params.add(categoryAttributeValue.getCategoryAttributeNo());
        params.add(categoryAttributeValue.getCommodityCode());

        pstmt = updateStatement;
        logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
      } else {

        params.add(categoryAttributeValue.getShopCode());
        params.add(categoryAttributeValue.getCategoryCode());
        params.add(categoryAttributeValue.getCategoryAttributeNo());
        params.add(categoryAttributeValue.getCommodityCode());
        params.add(categoryAttributeValue.getCategoryAttributeValue());

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
