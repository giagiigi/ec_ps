package jp.co.sint.webshop.service.data.csv;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.catalog.CatalogQuery;
import jp.co.sint.webshop.service.data.CsvSchemaType;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.RangeValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.validation.ValidatorUtil;

import org.apache.log4j.Logger;

public class CommodityDetailImportDataSource extends SqlImportDataSource<CommodityDetailCsvSchema, CommodityDetailImportCondition> {

  private static final String DATAIL_TABLE_NAME = DatabaseUtil.getTableName(CommodityDetail.class);

  private static final String HEADER_TABLE_NAME = DatabaseUtil.getTableName(CommodityHeader.class);

  /** 商品詳細INSERT用Statement */
  private PreparedStatement insertDetailStatement = null;

  /** 在庫INSERT用Statement */
  private PreparedStatement insertStockStatement = null;

  /** 商品詳細UPDATE用Statement */
  private PreparedStatement updateDetailStatement = null;

  /** 在庫UPDATE用Statement */
  private PreparedStatement updateStockStatement = null;

  /** 商品ヘッダUPDATE用Statement */
  private PreparedStatement updateHeaderStatement = null;

  private PreparedStatement selectStatement = null;

  private CommodityDetail detail = null;

  private Stock stock = null;

  @Override
  protected void initializeResources() {
    String insertStockQuery = CsvUtil.buildInsertQuery(DIContainer.getCsvSchema(CsvSchemaType.STOCK));
    String selectQuery = CsvUtil.buildCheckExistsQuery(getSchema());
    Logger logger = Logger.getLogger(this.getClass());
    logger.debug("INSERT statement: " + getInsertDetailQuery());
    logger.debug("INSERT statement: " + insertStockQuery);
    logger.debug("UPDATE statement: " + getUpdateDetailQuery());
    logger.debug("UPDATE statement: " + CatalogQuery.UPDATE_RESERVATIOIN_INFO);
    logger.debug("UPDATE statement: " + getUpdateHeaderQuery());
    logger.debug("CHECK  statement: " + selectQuery);

    try {
      insertDetailStatement = createPreparedStatement(getInsertDetailQuery());
      insertStockStatement = createPreparedStatement(insertStockQuery);
      updateDetailStatement = createPreparedStatement(getUpdateDetailQuery());
      updateStockStatement = createPreparedStatement(CatalogQuery.UPDATE_RESERVATIOIN_INFO);
      updateHeaderStatement = createPreparedStatement(getUpdateHeaderQuery());
      selectStatement = createPreparedStatement(selectQuery);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      detail = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CommodityDetail.class);
      stock = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), Stock.class);
      stock.setStockQuantity(0L);
      stock.setAllocatedQuantity(0L);
      stock.setReservedQuantity(0L);
      stock.setStockQuantity(0L);
      if (NumUtil.isNull(stock.getStockThreshold())) {
        stock.setStockThreshold(0L);
      }

    } catch (CsvImportException e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }
    DatabaseUtil.setUserStatus(detail, getCondition().getLoginInfo());

    summary.getErrors().addAll(BeanValidator.validate(detail).getErrors());
    // 10.1.1 10019 追加 ここから
    checkMinusNumber(summary);
    // 10.1.1 10019 追加 ここまで
    // 10.1.6 10281 追加 ここから
    RangeValidator rangeValidator = new RangeValidator(1L, 99999999L);
    if (!rangeValidator.isValid(stock.getReservationLimit())) {
      summary.getErrors().add(
          new ValidationResult(Messages.getString("service.data.csv.CommodityDetailImportDataSource.20"), null, rangeValidator
              .getMessage()));
    }
    if (!rangeValidator.isValid(stock.getOneshotReservationLimit())) {
      summary.getErrors().add(
          new ValidationResult(Messages.getString("service.data.csv.CommodityDetailImportDataSource.22"), null, rangeValidator
              .getMessage()));
    }
    // 10.1.6 10281 追加 ここまで

    if (summary.hasError()) {
      return summary;
    }

    // ショップコード相違チェック

    if (StringUtil.hasValue(getCondition().getShopCode())) {
      if (!detail.getShopCode().equals(getCondition().getShopCode())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.VALIDATE_SHOPCODE)));
        return summary;
      }
    }

    // ショップデータ存在チェック

    SimpleQuery shopCountQuery = new SimpleQuery("SELECT COUNT(*) FROM SHOP WHERE SHOP_CODE = ? ");
    shopCountQuery.setParameters(detail.getShopCode());
    Long shopCount = Long.valueOf(executeScalar(shopCountQuery).toString());
    if (shopCount == 0L) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.0"))));
    }

    // 代表SKUに指定したコードが、別の商品コードに紐付くSKUとして登録されていないかをチェック

    SimpleQuery skuExistsQuery = new SimpleQuery(
        "SELECT COMMODITY_CODE FROM COMMODITY_DETAIL WHERE SHOP_CODE = ? AND SKU_CODE = ? ");
    skuExistsQuery.setParameters(detail.getShopCode(), detail.getSkuCode());

    Object object = executeScalar(skuExistsQuery);
    if (object != null) {
      String commodityCode = object.toString();
      if (!commodityCode.equals(detail.getCommodityCode())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityDetailImportDataSource.1")));
      }
    }

    Query commodityGetQuery = new SimpleQuery("SELECT * FROM COMMODITY_HEADER WHERE SHOP_CODE = ? AND COMMODITY_CODE = ?", detail
        .getShopCode(), detail.getCommodityCode());
    CommodityHeader header = loadAsBean(commodityGetQuery, CommodityHeader.class);
    if (header == null) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.2"))));
      return summary;
    }
    if ((header.getDiscountPriceStartDatetime() != null || header.getDiscountPriceEndDatetime() != null)
        && detail.getDiscountPrice() == null) {
      summary.getErrors().add(
          new ValidationResult(null, null, MessageFormat.format(Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.3"), detail.getCommodityCode())));
    }
    if (header.getDiscountPriceStartDatetime() == null && header.getDiscountPriceEndDatetime() == null
        && detail.getDiscountPrice() != null) {
      summary.getErrors().add(
          new ValidationResult(null, null, MessageFormat.format(Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.4"), detail.getCommodityCode())));
    }
    if (((header.getReservationStartDatetime() != null || header.getReservationEndDatetime() != null) && detail
        .getReservationPrice() == null)
        && !(header.getReservationStartDatetime().equals(DateUtil.getMin()) && header.getReservationEndDatetime().equals(
            DateUtil.getMin()))) {
      summary.getErrors().add(
          new ValidationResult(null, null, MessageFormat.format(Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.5"), detail.getCommodityCode())));
    }
    if (detail.getReservationPrice() != null
        && ((header.getReservationStartDatetime() == null && header.getReservationEndDatetime() == null) || (header
            .getReservationStartDatetime().equals(DateUtil.getMin()) && header.getReservationEndDatetime()
            .equals(DateUtil.getMin())))) {
      summary.getErrors().add(
          new ValidationResult(null, null, MessageFormat.format(Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.6"), detail.getCommodityCode())));
    }
    if (header.getChangeUnitPriceDatetime() != null && detail.getChangeUnitPrice() == null) {
      summary.getErrors().add(
          new ValidationResult(null, null, MessageFormat.format(Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.7"), detail.getCommodityCode())));
    }
    if (header.getChangeUnitPriceDatetime() == null && detail.getChangeUnitPrice() != null) {
      summary.getErrors().add(
          new ValidationResult(null, null, MessageFormat.format(Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.8"), detail.getCommodityCode())));
    }
    if (stock.getReservationLimit() != null && stock.getOneshotReservationLimit() != null
        && ValidatorUtil.lessThan(stock.getReservationLimit(), stock.getOneshotReservationLimit())) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityDetailImportDataSource.9")));
    }
    if (stock.getStockThreshold() != null && (stock.getStockThreshold() < 0 || 99999999 < stock.getStockThreshold())) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityDetailImportDataSource.10")));
    }
    boolean exists = exists(selectStatement, detail.getShopCode(), detail.getSkuCode());
    if (!exists) {
      if (StringUtil.isNullOrEmpty(header.getCommodityStandard1Name())
          && StringUtil.isNullOrEmpty(header.getCommodityStandard2Name())) {
        summary.getErrors().add(
            new ValidationResult(null, null, MessageFormat.format(Messages
                .getString("service.data.csv.CommodityDetailImportDataSource.11"), detail.getSkuCode())));
      }
    }
    if (StringUtil.isNullOrEmpty(header.getCommodityStandard1Name())
        && StringUtil.isNullOrEmpty(header.getCommodityStandard2Name())) {
      if (StringUtil.hasValue(detail.getStandardDetail1Name()) || StringUtil.hasValue(detail.getStandardDetail2Name())) {
        summary.getErrors().add(
            new ValidationResult(null, null, MessageFormat.format(Messages
                .getString("service.data.csv.CommodityDetailImportDataSource.12"), detail.getSkuCode())));
      }
    }
    if (StringUtil.hasValue(header.getCommodityStandard1Name()) && StringUtil.isNullOrEmpty(detail.getStandardDetail1Name())) {
      summary.getErrors().add(
          new ValidationResult(null, null, MessageFormat.format(Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.13"), detail.getSkuCode(), header
              .getCommodityStandard1Name())));
    }
    if (StringUtil.hasValue(header.getCommodityStandard2Name()) && StringUtil.isNullOrEmpty(detail.getStandardDetail2Name())) {
      summary.getErrors().add(
          new ValidationResult(null, null, MessageFormat.format(Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.14"), detail.getSkuCode(), header
              .getCommodityStandard2Name())));
    }
    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    try {

      boolean exists = exists(selectStatement, detail.getShopCode(), detail.getSkuCode());

      int updDetailCount = executeDetailUpdate(exists);
      if (updDetailCount != 1) {
        throw new CsvImportException();
      }

      // 更新したSKUが、代表SKUだった場合、商品ヘッダの代表SKU商品単価を更新

      if (isRepresentSku()) {
        int updHeaderCount = executeHeaderUpdate();
        if (updHeaderCount != 1) {
          throw new CsvImportException();
        }
      }

      int updStockCount = executeStockUpdate(exists);
      if (updStockCount != 1) {
        throw new CsvImportException();
      }

    } catch (SQLException e) {
      logger.debug(e.getMessage());
      throw new CsvImportException(e);
    } catch (CsvImportException e) {
      logger.debug(e.getMessage());
      throw e;
    } catch (RuntimeException e) {
      logger.debug(e.getMessage());
      throw new CsvImportException(e);
    }

  }

  private boolean isRepresentSku() {
    SimpleQuery checkRepresentSkuQuery = new SimpleQuery(
        "SELECT COMMODITY_CODE FROM COMMODITY_HEADER WHERE SHOP_CODE = ? AND COMMODITY_CODE = ? AND REPRESENT_SKU_CODE = ? ");
    checkRepresentSkuQuery.setParameters(detail.getShopCode(), detail.getCommodityCode(), detail.getSkuCode());
    Object commodityCode = executeScalar(checkRepresentSkuQuery);
    if (commodityCode != null) {
      return true;
    }
    return false;
  }

  private int executeHeaderUpdate() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();

    params.add(detail.getUnitPrice());

    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());

    params.add(detail.getShopCode());
    params.add(detail.getCommodityCode());

    pstmt = updateHeaderStatement;
    logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }

  private int executeStockUpdate(boolean exists) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    if (exists) {
      params.add(stock.getOneshotReservationLimit());
      params.add(stock.getReservationLimit());
      params.add(stock.getStockThreshold());

      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      params.add(stock.getShopCode());
      params.add(stock.getSkuCode());

      pstmt = updateStockStatement;
      logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    } else {
      params.add(stock.getShopCode());
      params.add(stock.getSkuCode());
      params.add(stock.getCommodityCode());
      params.add(stock.getStockQuantity());
      params.add(stock.getAllocatedQuantity());
      params.add(stock.getReservedQuantity());
      params.add(stock.getReservationLimit());
      params.add(stock.getOneshotReservationLimit());
      params.add(stock.getStockThreshold());

      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());
      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      pstmt = insertStockStatement;
      logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    }

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }

  private int executeDetailUpdate(boolean exists) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    if (exists) {
      params.add(detail.getCommodityCode());
      params.add(detail.getUnitPrice());
      params.add(detail.getDiscountPrice());
      params.add(detail.getReservationPrice());
      params.add(detail.getChangeUnitPrice());
      params.add(detail.getJanCode());
      params.add(detail.getDisplayOrder());
      params.add(detail.getStandardDetail1Name());
      params.add(detail.getStandardDetail2Name());

      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      params.add(detail.getShopCode());
      params.add(detail.getSkuCode());

      pstmt = updateDetailStatement;
      logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    } else {

      params.add(detail.getShopCode());
      params.add(detail.getSkuCode());
      params.add(detail.getCommodityCode());
      params.add(detail.getUnitPrice());
      params.add(detail.getDiscountPrice());
      params.add(detail.getReservationPrice());
      params.add(detail.getChangeUnitPrice());
      params.add(detail.getJanCode());
      params.add(detail.getDisplayOrder());
      params.add(detail.getStandardDetail1Name());
      params.add(detail.getStandardDetail2Name());

      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());
      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      pstmt = insertDetailStatement;
      logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    }

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }

  private String getInsertDetailQuery() {
    String insertSql = "" + " INSERT INTO " + DATAIL_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        // postgreSQL start
        // + DATAIL_TABLE_NAME + "_SEQ.NEXTVAL, ?, ?, ?, ?) ";
        + SqlDialect.getDefault().getNextvalNOprm(DATAIL_TABLE_NAME + "_SEQ") + ", ?, ?, ?, ?) ";
    // postgreSQL end
    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("SHOP_CODE");
    columnList.add("SKU_CODE");
    columnList.add("COMMODITY_CODE");
    columnList.add("UNIT_PRICE");
    columnList.add("DISCOUNT_PRICE");
    columnList.add("RESERVATION_PRICE");
    columnList.add("CHANGE_UNIT_PRICE");
    columnList.add("JAN_CODE");
    columnList.add("DISPLAY_ORDER");
    columnList.add("STANDARD_DETAIL1_NAME");
    columnList.add("STANDARD_DETAIL2_NAME");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  private String getUpdateDetailQuery() {
    String updateSql = "" + " UPDATE " + DATAIL_TABLE_NAME
        + " SET {0} UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE SHOP_CODE = ? AND SKU_CODE = ? ";

    StringBuilder setPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("COMMODITY_CODE");
    columnList.add("UNIT_PRICE");
    columnList.add("DISCOUNT_PRICE");
    columnList.add("RESERVATION_PRICE");
    columnList.add("CHANGE_UNIT_PRICE");
    columnList.add("JAN_CODE");
    columnList.add("DISPLAY_ORDER");
    columnList.add("STANDARD_DETAIL1_NAME");
    columnList.add("STANDARD_DETAIL2_NAME");

    for (String column : columnList) {
      setPart.append(column + " = ?, ");
    }

    return MessageFormat.format(updateSql, setPart.toString());

  }

  private String getUpdateHeaderQuery() {
    String updateSql = "" + " UPDATE " + HEADER_TABLE_NAME
        + " SET {0} UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE SHOP_CODE = ? AND COMMODITY_CODE = ? ";

    StringBuilder setPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("REPRESENT_SKU_UNIT_PRICE");

    for (String column : columnList) {
      setPart.append(column + " = ?, ");
    }

    return MessageFormat.format(updateSql, setPart.toString());
  }

  // 10.1.1 10019 追加 ここから
  private void checkMinusNumber(ValidationSummary summary) {
    if (detail.getUnitPrice() != null && BigDecimalUtil.isBelow(detail.getUnitPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.15"))));
    }
    if (detail.getDiscountPrice() != null && BigDecimalUtil.isBelow(detail.getDiscountPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.16"))));
    }
    if (detail.getReservationPrice() != null && BigDecimalUtil.isBelow(detail.getReservationPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.17"))));
    }
    if (detail.getChangeUnitPrice() != null && BigDecimalUtil.isBelow(detail.getChangeUnitPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.18"))));
    }
    if (NumUtil.isNegative(detail.getDisplayOrder())) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.19"))));
    }
    // 10.1.6 10281 削除 ここから
    // if (NumUtil.isNegative(stock.getReservationLimit())) {
    // summary.getErrors().add(
    // new ValidationResult(null, null,
    // Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
    // .getString("service.data.csv.CommodityDetailImportDataSource.20"))));
    // }
    // if (NumUtil.isNegative(stock.getOneshotReservationLimit())) {
    // summary.getErrors().add(
    // new ValidationResult(null, null,
    // Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
    // .getString("service.data.csv.CommodityDetailImportDataSource.21"))));
    //    }
    // 10.1.6 10281 削除 ここまで
    if (NumUtil.isNegative(stock.getStockThreshold())) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.22"))));
    }
  }
  // 10.1.1 10019 追加 ここまで

}
