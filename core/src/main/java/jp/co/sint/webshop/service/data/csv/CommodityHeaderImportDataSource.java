package jp.co.sint.webshop.service.data.csv;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.dao.CommodityDetailDao;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.catalog.CatalogQuery;
import jp.co.sint.webshop.service.catalog.CommodityDeleteQuery;
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

public class CommodityHeaderImportDataSource extends SqlImportDataSource<CommodityHeaderCsvSchema, CommodityHeaderImportCondition> {

  private static final String HEADER_TABLE_NAME = DatabaseUtil.getTableName(CommodityHeader.class);

  private static final String DETAIL_TABLE_NAME = DatabaseUtil.getTableName(CommodityDetail.class);

  private String insertDetailQuery = "" + " INSERT " + " INTO COMMODITY_DETAIL ( " + "   SHOP_CODE " + "   , SKU_CODE "
      + "   , COMMODITY_CODE " + "   , UNIT_PRICE " + "   , DISCOUNT_PRICE " + "   , RESERVATION_PRICE "
      + "   , CHANGE_UNIT_PRICE " + "   , JAN_CODE " + "   , DISPLAY_ORDER " + "   , STANDARD_DETAIL1_NAME "
      + "   , STANDARD_DETAIL2_NAME " + "   , ORM_ROWID " + "   , CREATED_USER " + "   , CREATED_DATETIME " + "   , UPDATED_USER "
      + "   , UPDATED_DATETIME " + " ) VALUES ( " + "   ? " + "   , ? " + "   , ? " + "   , ? " + "   , ? "
      + "   , ? "
      + "   , ? "
      // postgreSQL start
      // + " , ? " + " , ? " + " , ? " + " , ? " + " , " + DETAIL_TABLE_NAME +
      // "_SEQ.NEXTVAL" + " , ? " + " , ? "
      + "   , ? " + "   , ? " + "   , ? " + "   , ? " + "   , "
      + SqlDialect.getDefault().getNextvalNOprm(DETAIL_TABLE_NAME + "_SEQ") + "   , ? " + "   , ? "
      // postgreSQL end
      + "   , ? " + "   , ? " + " ) ";

  private String updateDetailQuery = "" + " UPDATE COMMODITY_DETAIL " + " SET COMMODITY_CODE = ? " + " , UNIT_PRICE = ? "
      + " , DISCOUNT_PRICE = ? " + " , RESERVATION_PRICE = ? " + " , CHANGE_UNIT_PRICE = ? " + " , JAN_CODE = ? "
      + " , DISPLAY_ORDER = ? " + " , UPDATED_USER = ? " + " , UPDATED_DATETIME = ? " + " WHERE SHOP_CODE = ? "
      + " AND SKU_CODE = ? ";

  private String updatePriceQuery = "" + "UPDATE COMMODITY_DETAIL " + " SET DISCOUNT_PRICE = ?, " + "     RESERVATION_PRICE = ?, "
      + "     CHANGE_UNIT_PRICE = ?, " + "     UPDATED_USER = ?, " + "     UPDATED_DATETIME = ? "
      + " WHERE SHOP_CODE = ? AND SKU_CODE = ? ";

  private String deleteCommodityDetailQuery = " DELETE FROM COMMODITY_DETAIL "
      + " WHERE SHOP_CODE = ? AND COMMODITY_CODE = ? AND SKU_CODE <> ?";

  private String deleteStockQuery = " DELETE FROM STOCK WHERE (SHOP_CODE, SKU_CODE) "
      + " IN (SELECT CD.SHOP_CODE, CD.SKU_CODE FROM COMMODITY_DETAIL CD"
      + "      INNER JOIN STOCK ST ON CD.SHOP_CODE = ST.SHOP_CODE AND CD.SKU_CODE = ST.SKU_CODE"
      + "      WHERE CD.SHOP_CODE = ? AND CD.COMMODITY_CODE = ?) AND SKU_CODE <> ?";

  private String deleteStockIoDetailQuery = " DELETE FROM STOCK_IO_DETAIL WHERE (SHOP_CODE, SKU_CODE) "
      + " IN (SELECT CD.SHOP_CODE, CD.SKU_CODE FROM COMMODITY_DETAIL CD"
      + "      INNER JOIN STOCK_IO_DETAIL ST ON CD.SHOP_CODE = ST.SHOP_CODE AND CD.SKU_CODE = ST.SKU_CODE"
      + "      WHERE CD.SHOP_CODE = ? AND CD.COMMODITY_CODE = ?) AND SKU_CODE <> ?";

  private String updateStandardNameQuery = " UPDATE COMMODITY_DETAIL SET"
      + " STANDARD_DETAIL1_NAME = '', STANDARD_DETAIL2_NAME = '', UPDATED_USER = ?, UPDATED_DATETIME = ?"
      + " WHERE SHOP_CODE = ? AND COMMODITY_CODE = ?";

  private String updateStandardName2Query = " UPDATE COMMODITY_DETAIL SET" + " STANDARD_DETAIL2_NAME = '',"
      + " UPDATED_USER = ?, UPDATED_DATETIME = ?"
      + " WHERE SHOP_CODE = ? AND COMMODITY_CODE = ? AND STANDARD_DETAIL2_NAME IS NOT NULL";

  /** ヘッダINSERT用Statement */
  private PreparedStatement insertHeaderStatement = null;

  /** ヘッダUPDATE用Statement */
  private PreparedStatement updateHeaderStatement = null;

  /** ヘッダ存在チェック用Statement */
  private PreparedStatement selectHeaderStatement = null;

  /** 詳細INSERT用Statement */
  private PreparedStatement insertDetailStatement = null;

  /** 詳細UPDATE用Statement */
  private PreparedStatement updateDetailStatement = null;

  /** 詳細INSERT用Statement */
  private PreparedStatement selectDetailStatement = null;

  /** 在庫INSERT用Statement */
  private PreparedStatement insertStockStatement = null;

  /** 在庫UPDATE用Statement */
  private PreparedStatement updateStockStatement = null;

  /** 商品詳細価格UPDATE用Statement */
  private PreparedStatement updatePriceStatement = null;

  /** 商品詳細DELETE用Statement */
  private PreparedStatement deleteCommodityDetailStatement = null;

  /** 在庫DELETE用Statement */
  private PreparedStatement deleteStockStatement = null;

  /** 在庫入出庫DELETE用Statement */
  private PreparedStatement deleteStockIoDetailStatement = null;

  /** 商品詳細(規格詳細名称1,2)UPDATE用Statement */
  private PreparedStatement updateStandardNameStatement = null;

  /** 商品詳細(規格詳細名称2)UPDATE用Statement */
  private PreparedStatement updateStandardName2Statement = null;

  /** 商品ヘッダ */
  private CommodityHeader header = null;

  /** 代表SKU */
  private CommodityDetail representSku = null;

  /** 在庫 */
  private Stock stock = null;

  private WebshopConfig config = null;

  @Override
  protected void initializeResources() {
    config = DIContainer.getWebshopConfig();
    String selectHeaderQuery = CsvUtil.buildCheckExistsQuery(getSchema());
    Logger logger = Logger.getLogger(this.getClass());
    logger.debug("INSERT statement: " + getInsertHeaderQuery());
    logger.debug("INSERT statement: " + insertDetailQuery);
    logger.debug("UPDATE statement: " + getUpdateHeaderQuery());
    logger.debug("UPDATE statement: " + updateDetailQuery);
    logger.debug("CHECK  statement: " + selectHeaderQuery);
    logger.debug("PRICE  statement: " + updatePriceQuery);
    logger.debug("DELETE  statement: " + deleteCommodityDetailQuery);
    logger.debug("STANDARD_NAME  statement: " + updateStandardName2Query);

    CsvSchema detailSchema = DIContainer.getCsvSchema(CsvSchemaType.COMMODITY_DETAIL);
    CsvSchema stockSchema = DIContainer.getCsvSchema(CsvSchemaType.STOCK);

    try {
      insertHeaderStatement = createPreparedStatement(getInsertHeaderQuery());
      updateHeaderStatement = createPreparedStatement(getUpdateHeaderQuery());
      selectHeaderStatement = createPreparedStatement(selectHeaderQuery);

      insertDetailStatement = createPreparedStatement(insertDetailQuery);
      updateDetailStatement = createPreparedStatement(updateDetailQuery);
      selectDetailStatement = createPreparedStatement(CsvUtil.buildCheckExistsQuery(detailSchema));

      insertStockStatement = createPreparedStatement(CsvUtil.buildInsertQuery(stockSchema));
      updateStockStatement = createPreparedStatement(CatalogQuery.UPDATE_RESERVATIOIN_INFO);

      updatePriceStatement = createPreparedStatement(updatePriceQuery);
      deleteCommodityDetailStatement = createPreparedStatement(deleteCommodityDetailQuery);
      deleteStockStatement = createPreparedStatement(deleteStockQuery);
      deleteStockIoDetailStatement = createPreparedStatement(deleteStockIoDetailQuery);
      updateStandardNameStatement = createPreparedStatement(updateStandardNameQuery);
      updateStandardName2Statement = createPreparedStatement(updateStandardName2Query);

    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      header = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CommodityHeader.class);
      // add by V10-CH 170 start
      header.setCommodityTaxType(TaxType.NO_TAX.longValue());
      // add by V10-CH 170 start
      representSku = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CommodityDetail.class);
      stock = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), Stock.class);

      String shadowSearchWords = StringUtil.coalesce(header.getCommoditySearchWords(), "") + header.getCommodityCode()
          + header.getCommodityName();
      header.setShadowSearchWords(StringUtil.toSearchKeywords(shadowSearchWords));
      // 販売開始/終了日時が未設定の場合はデフォルト値を設定
      // 販売開始日時のデフォルト値:システム最小日付

      // 販売終了日時のデフォルト値:システム最大日付

      // 10.1.1 10006 修正 ここから
      // if (header.getSaleStartDatetime() == null ||
      // header.getSaleStartDatetime().before(DateUtil.getMin())) {
      if (header.getSaleStartDatetime() == null) {
        // 10.1.1 10006 修正 ここまで
        header.setSaleStartDatetime(DateUtil.getMin());
      }
      // 10.1.1 10006 修正 ここから
      // if (header.getSaleEndDatetime() == null ||
      // header.getSaleEndDatetime().after(DateUtil.getMax())) {
      if (header.getSaleEndDatetime() == null) {
        // 10.1.1 10006 修正 ここまで
        header.setSaleEndDatetime(DateUtil.getMax());
      }

      // 予約開始/終了日時が未設定の場合はシステム最小日付を両方に設定

      // 予約開始のみが未設定の場合は、開始日時にシステム最小日付を設定
      if (header.getReservationStartDatetime() == null && header.getReservationEndDatetime() == null) {
        header.setReservationStartDatetime(DateUtil.getMin());
        header.setReservationEndDatetime(DateUtil.getMin());
      } else if (header.getReservationStartDatetime() == null) {
        header.setReservationStartDatetime(DateUtil.getMin());
      }

      // 10.1.3 10057 追加 ここから
      // 商品別ポイント付与期間、販売期間、特別価格期間、予約期間、価格改定日時の日付設定レベルを画面側と合わせる。

      header.setCommodityPointStartDatetime(DateUtil.truncateHour(header.getCommodityPointStartDatetime()));
      header.setCommodityPointEndDatetime(DateUtil.truncateHour(header.getCommodityPointEndDatetime()));
      header.setSaleStartDatetime(DateUtil.truncateHour(header.getSaleStartDatetime()));
      header.setSaleEndDatetime(DateUtil.truncateHour(header.getSaleEndDatetime()));
      header.setDiscountPriceStartDatetime(DateUtil.truncateHour(header.getDiscountPriceStartDatetime()));
      header.setDiscountPriceEndDatetime(DateUtil.truncateHour(DateUtil.truncateHour(header.getDiscountPriceEndDatetime())));
      header.setReservationStartDatetime(DateUtil.truncateHour(header.getReservationStartDatetime()));
      header.setReservationEndDatetime(DateUtil.truncateHour(header.getReservationEndDatetime()));
      header.setChangeUnitPriceDatetime(DateUtil.truncateDate(header.getChangeUnitPriceDatetime()));
      // 10.1.3 10057 追加 ここまで

      representSku.setSkuCode(header.getRepresentSkuCode());
      stock.setSkuCode(header.getRepresentSkuCode());
      stock.setStockQuantity(0L);
      stock.setAllocatedQuantity(0L);
      stock.setReservedQuantity(0L);
      if (NumUtil.isNull(stock.getStockThreshold())) {
        stock.setStockThreshold(0L);
      }

    } catch (CsvImportException e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }
    DatabaseUtil.setUserStatus(header, getCondition().getLoginInfo());
    DatabaseUtil.setUserStatus(representSku, getCondition().getLoginInfo());

    summary.getErrors().addAll(BeanValidator.partialValidate(header, getHeaderValidationFields()).getErrors());
    summary.getErrors().addAll(BeanValidator.validate(representSku).getErrors());
    // 10.1.1 10019 追加 ここから
    checkMinusNumber(summary);
    // 10.1.1 10019 追加 ここまで
    // 10.1.6 10281 追加 ここから
    RangeValidator rangeValidator = new RangeValidator(1L, 99999999L);
    if (!rangeValidator.isValid(stock.getReservationLimit())) {
      summary.getErrors().add(
          new ValidationResult(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.45"), null, rangeValidator
              .getMessage()));
    }
    if (!rangeValidator.isValid(stock.getOneshotReservationLimit())) {
      summary.getErrors().add(
          new ValidationResult(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.46"), null, rangeValidator
              .getMessage()));
    }
    // 10.1.6 10281 追加 ここまで

    if (summary.hasError()) {
      return summary;
    }

    // ショップコード相違チェック

    if (StringUtil.hasValue(getCondition().getShopCode())) {
      if (!header.getShopCode().equals(getCondition().getShopCode())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.VALIDATE_SHOPCODE)));
        return summary;
      }
    }

    // ショップデータ存在チェック

    SimpleQuery shopCountQuery = new SimpleQuery("SELECT COUNT(*) FROM SHOP WHERE SHOP_CODE = ? ");
    shopCountQuery.setParameters(header.getShopCode());
    Long shopCount = Long.valueOf(executeScalar(shopCountQuery).toString());
    if (shopCount == 0L) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
              .getString("service.data.csv.CommodityHeaderImportDataSource.0"))));
    }

    // 代表SKUに指定したコードが、別の商品コードに紐付くSKUとして登録されていないかをチェック

    SimpleQuery skuExistsQuery = new SimpleQuery(
        "SELECT COMMODITY_CODE FROM COMMODITY_DETAIL WHERE SHOP_CODE = ? AND SKU_CODE = ? ");
    skuExistsQuery.setParameters(header.getShopCode(), header.getRepresentSkuCode());

    Object object = executeScalar(skuExistsQuery);
    if (object != null) {
      String commodityCode = object.toString();
      if (!commodityCode.equals(header.getCommodityCode())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityHeaderImportDataSource.1")));
      }
    }

    List<String> errorMessageList = new ArrayList<String>();

    // 規格名称の妥当性チェック

    if (StringUtil.isNullOrEmpty(header.getCommodityStandard1Name()) && StringUtil.hasValue(header.getCommodityStandard2Name())) {
      errorMessageList.add(Message.get(CsvMessage.FAULT_STANDARD_NAME, Messages
          .getString("service.data.csv.CommodityHeaderImportDataSource.2")));
    }

    // 10.1.6 10258 追加 ここから
    // 規格名称の重複チェック

    if (StringUtil.hasValue(header.getCommodityStandard1Name()) && StringUtil.hasValue(header.getCommodityStandard2Name())) {
      if (header.getCommodityStandard1Name().equals(header.getCommodityStandard2Name())) {
        errorMessageList.add(Message.get(CsvMessage.OVERLAPPED_VALUES, Messages
            .getString("service.data.csv.CommodityHeaderImportDataSource.58"), Messages
            .getString("service.data.csv.CommodityHeaderImportDataSource.59")));
      }
    }
    // 10.1.6 10258 追加 ここまで

    // 在庫状況の妥当性チェック

    checkStockStatusRules(header, errorMessageList);

    // 配送種別の存在チェック
    checkDeliveryTypeRules(header, errorMessageList);

    // 10.1.1 10006 追加 ここから
    // 各期間の日付範囲チェック
    checkDateRange(header, errorMessageList);
    // 10.1.1 10006 追加 ここまで

    // 各期間の開始/終了日時の順序チェック

    checkTermsRules(header, errorMessageList);

    // 販売期間、特別価格期間に関する順序チェック

    checkSalesTermAndDiscountRules(header, errorMessageList);

    // 販売期間、予約期間に関する順序チェック

    checkSalesTermAndReservationRules(header, errorMessageList);

    // 販売期間、価格改定日の順序チェック

    checkSalesTermAndOrderRules(header, errorMessageList);

    // 商品別ポイント付与に関するチェック

    checkCommodityPointRules(header, errorMessageList);

    // 商品詳細のチェック

    checkDetailRules(header, representSku, errorMessageList);

    // 予約上限数、注文毎予約上限数のチェック
    checkStockRules(stock, errorMessageList);

    // 在庫閾値のチェック
    checkStockThresholdRules(stock, errorMessageList);

    // おすすめ順のチェック
    checkRecommendCommodityRankRules(header, errorMessageList);

    for (String error : errorMessageList) {
      summary.getErrors().add(new ValidationResult(null, null, error));
    }

    return summary;
  }

  private void checkStockThresholdRules(Stock bean, List<String> errorMessageList) {
    if (bean.getStockThreshold() != null && (bean.getStockThreshold() < 0 || 99999999 < bean.getStockThreshold())) {
      errorMessageList.add(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.3"));
    }
  }

  private void checkStockRules(Stock bean, List<String> errorMessageList) {
    if (bean.getReservationLimit() != null && bean.getOneshotReservationLimit() != null
        && ValidatorUtil.lessThan(bean.getReservationLimit(), bean.getOneshotReservationLimit())) {
      errorMessageList.add(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.4"));
    }
  }

  private void checkRecommendCommodityRankRules(CommodityHeader bean, List<String> errorMessageList) {
    // DB定義と入力ルールが異なることにより、BeanValidatorでのチェックができないため、個別のチェックを実施している。

    if (bean.getRecommendCommodityRank() != null
        && (9999 < bean.getRecommendCommodityRank() || bean.getRecommendCommodityRank() < 0)) {
      errorMessageList.add(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.5"));
    }
  }

  private void checkDetailRules(CommodityHeader bean, CommodityDetail detail, List<String> errorMessageList) {
    if ((bean.getDiscountPriceStartDatetime() != null || bean.getDiscountPriceEndDatetime() != null)
        && detail.getDiscountPrice() == null) {
      errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.6"), detail
          .getCommodityCode()));
    }

    if (bean.getDiscountPriceStartDatetime() == null && bean.getDiscountPriceEndDatetime() == null
        && detail.getDiscountPrice() != null) {
      errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.7"), detail
          .getCommodityCode()));
    }

    if (((bean.getReservationStartDatetime() != null || bean.getReservationEndDatetime() != null) && detail.getReservationPrice() == null)
        && !(bean.getReservationStartDatetime().equals(DateUtil.getMin()) && bean.getReservationEndDatetime().equals(
            DateUtil.getMin()))) {
      errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.8"), detail
          .getCommodityCode()));
    }

    if (detail.getReservationPrice() != null
        && ((bean.getReservationStartDatetime() == null && bean.getReservationEndDatetime() == null) || (bean
            .getReservationStartDatetime().equals(DateUtil.getMin()) && bean.getReservationEndDatetime().equals(DateUtil.getMin())))) {
      errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.9"), detail
          .getCommodityCode()));
    }

    if (bean.getChangeUnitPriceDatetime() != null && detail.getChangeUnitPrice() == null) {
      errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.10"), detail
          .getCommodityCode()));
    }

    if (bean.getChangeUnitPriceDatetime() == null && detail.getChangeUnitPrice() != null) {
      errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.11"), detail
          .getCommodityCode()));
    }

  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    try {

      boolean existsHeader = exists(selectHeaderStatement, header.getShopCode(), header.getCommodityCode());
      int updHeaderCount = executeUpdateHeader(existsHeader);
      if (updHeaderCount != 1) {
        throw new CsvImportException();
      }

      boolean existsRepresent = exists(selectDetailStatement, representSku.getShopCode(), representSku.getSkuCode());
      int updDetailCount = executeUpdateDetail(existsRepresent);
      if (updDetailCount != 1) {
        throw new CsvImportException();
      }

      // 更新の場合

      if (existsHeader) {
        // 該当商品の各価格を更新

        executeUpdatePrice();
      }

      int updStockCount = executeUpdateStock(existsRepresent);
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

  private void checkStockStatusRules(CommodityHeader bean, List<String> errorMessageList) {
    if (StockManagementType.WITH_STATUS == StockManagementType.fromValue(bean.getStockManagementType())) {
      if (bean.getStockStatusNo() == null) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.12"), bean
            .getCommodityCode()));
      } else {
        Query stockStatusCountQuery = new SimpleQuery(
            "SELECT COUNT(*) FROM STOCK_STATUS WHERE SHOP_CODE = ? AND STOCK_STATUS_NO = ?", bean.getShopCode(), bean
                .getStockStatusNo());
        Long stockStatusCount = Long.valueOf(executeScalar(stockStatusCountQuery).toString());
        if (stockStatusCount == 0) {
          errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.14"), bean
              .getCommodityCode(), bean.getStockStatusNo()));
        }
      }
    } else {
      if (bean.getStockStatusNo() != null) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.15"), bean
            .getCommodityCode()));
      }
    }
  }

  private void checkDeliveryTypeRules(CommodityHeader bean, List<String> errorMessageList) {
    Query deliveryTypeCountQuery = new SimpleQuery(
        "SELECT COUNT(*) FROM DELIVERY_TYPE WHERE SHOP_CODE = ? AND DELIVERY_TYPE_NO = ?", bean.getShopCode(), bean
            .getDeliveryTypeNo());
    Long deliveryTypeCount = Long.valueOf(executeScalar(deliveryTypeCountQuery).toString());
    if (deliveryTypeCount == 0) {
      errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.17"), bean
          .getCommodityCode(), bean.getDeliveryTypeNo()));
    }
  }

  // 10.1.1 10006 追加 ここから
  private void checkDateRange(CommodityHeader bean, List<String> errorMessageList) {
    String min = Integer.toString(DIContainer.getWebshopConfig().getApplicationMinYear());
    String max = Integer.toString(DIContainer.getWebshopConfig().getApplicationMaxYear());

    if (!DateUtil.isCorrectAppDate(bean.getSaleStartDatetime(), false) && !bean.getSaleStartDatetime().equals(DateUtil.getMin())) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages
          .getString("service.data.csv.CommodityHeaderImportDataSource.48"), min, max));
    }
    if (!DateUtil.isCorrectAppDate(bean.getSaleEndDatetime(), false) && !bean.getSaleEndDatetime().equals(DateUtil.getMax())) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages
          .getString("service.data.csv.CommodityHeaderImportDataSource.49"), min, max));
    }
    if (!DateUtil.isCorrectAppDate(bean.getChangeUnitPriceDatetime(), true)) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages
          .getString("service.data.csv.CommodityHeaderImportDataSource.50"), min, max));
    }
    if (!DateUtil.isCorrectAppDate(bean.getDiscountPriceStartDatetime(), true)) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages
          .getString("service.data.csv.CommodityHeaderImportDataSource.51"), min, max));
    }
    if (!DateUtil.isCorrectAppDate(bean.getDiscountPriceEndDatetime(), true)) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages
          .getString("service.data.csv.CommodityHeaderImportDataSource.52"), min, max));
    }
    if (!DateUtil.isCorrectAppDate(bean.getReservationStartDatetime(), false)
        && !bean.getReservationStartDatetime().equals(DateUtil.getMin())) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages
          .getString("service.data.csv.CommodityHeaderImportDataSource.53"), min, max));
    }
    if (!DateUtil.isCorrectAppDate(bean.getReservationEndDatetime(), false)
        && !bean.getReservationEndDatetime().equals(DateUtil.getMin())) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages
          .getString("service.data.csv.CommodityHeaderImportDataSource.54"), min, max));
    }
    if (!DateUtil.isCorrectAppDate(bean.getCommodityPointStartDatetime(), true)) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages
          .getString("service.data.csv.CommodityHeaderImportDataSource.55"), min, max));
    }
    if (!DateUtil.isCorrectAppDate(bean.getCommodityPointEndDatetime(), true)) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages
          .getString("service.data.csv.CommodityHeaderImportDataSource.56"), min, max));
    }
  }

  // 10.1.1 10006 追加 ここまで

  private void checkTermsRules(CommodityHeader bean, List<String> errorMessageList) {
    if (bean.getSaleStartDatetime() != null && bean.getSaleEndDatetime() != null
        && bean.getSaleStartDatetime().after(bean.getSaleEndDatetime())) {
      errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.18"), bean
          .getCommodityCode()));
    }
    if (bean.getDiscountPriceStartDatetime() != null && bean.getDiscountPriceEndDatetime() != null
        && bean.getDiscountPriceStartDatetime().after(bean.getDiscountPriceEndDatetime())) {
      errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.19"), bean
          .getCommodityCode()));
    }
    if (bean.getReservationStartDatetime() != null && bean.getReservationEndDatetime() != null
        && bean.getReservationStartDatetime().after(bean.getReservationEndDatetime())) {
      errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.20"), bean
          .getCommodityCode()));
    }
    if (bean.getCommodityPointStartDatetime() != null && bean.getCommodityPointEndDatetime() != null
        && bean.getCommodityPointStartDatetime().after(bean.getCommodityPointEndDatetime())) {
      errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.21"), bean
          .getCommodityCode()));
    }
  }

  private void checkSalesTermAndDiscountRules(CommodityHeader bean, List<String> errorMessageList) {
    if (bean.getSaleStartDatetime() != null) {
      if (bean.getDiscountPriceStartDatetime() != null && bean.getSaleStartDatetime().after(bean.getDiscountPriceStartDatetime())) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.22"), bean
            .getCommodityCode()));
      }
      if ((!bean.getSaleStartDatetime().equals(DateUtil.getMin())) && bean.getDiscountPriceStartDatetime() == null
          && bean.getDiscountPriceEndDatetime() != null) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.23"), bean
            .getCommodityCode()));
      }
    }
    if (bean.getSaleEndDatetime() != null) {
      if (bean.getDiscountPriceEndDatetime() != null && bean.getDiscountPriceEndDatetime().after(bean.getSaleEndDatetime())) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.24"), bean
            .getCommodityCode()));
      }
      if ((!bean.getSaleEndDatetime().equals(DateUtil.getMax())) && bean.getDiscountPriceEndDatetime() == null
          && bean.getDiscountPriceStartDatetime() != null) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.25"), bean
            .getCommodityCode()));
      }
    }
  }

  private void checkSalesTermAndReservationRules(CommodityHeader bean, List<String> errorMessageList) {
    if (bean.getReservationStartDatetime() != null) {
      if (bean.getReservationEndDatetime() == null) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.26"), bean
            .getCommodityCode()));
      }
      if (bean.getSaleStartDatetime() == null) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.27"), bean
            .getCommodityCode()));
      }
      if (bean.getReservationEndDatetime() != null && bean.getSaleStartDatetime() != null
          && bean.getReservationEndDatetime().after(bean.getSaleStartDatetime())) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.28"), bean
            .getCommodityCode()));
      }
    } else if (bean.getReservationEndDatetime() != null) {
      if (bean.getSaleStartDatetime() == null) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.29"), bean
            .getCommodityCode()));
      }
      if (bean.getReservationEndDatetime() != null && bean.getSaleStartDatetime() != null
          && bean.getReservationEndDatetime().after(bean.getSaleStartDatetime())) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.30"), bean
            .getCommodityCode()));
      }
    }
  }

  private void checkSalesTermAndOrderRules(CommodityHeader bean, List<String> errorMessageList) {
    if (bean.getChangeUnitPriceDatetime() != null) {
      if (bean.getSaleStartDatetime() != null && bean.getSaleEndDatetime() != null) {

        if (bean.getChangeUnitPriceDatetime().before(bean.getSaleStartDatetime())
            || bean.getChangeUnitPriceDatetime().after(bean.getSaleEndDatetime())) {
          errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.31"), bean
              .getCommodityCode()));
        }

      } else if (bean.getSaleStartDatetime() != null) {
        if (bean.getChangeUnitPriceDatetime().before(bean.getSaleStartDatetime())) {
          errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.32"), bean
              .getCommodityCode()));
        }

      } else if (bean.getSaleEndDatetime() != null) {
        if (bean.getChangeUnitPriceDatetime().after(bean.getSaleEndDatetime())) {
          errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.33"), bean
              .getCommodityCode()));
        }
      }
    }
  }

  private void checkCommodityPointRules(CommodityHeader bean, List<String> errorMessageList) {
    if (StringUtil.isNullOrEmpty(getCondition().getShopCode()) || config.getOperatingMode().equals(OperatingMode.ONE)) {
      // 商品別ポイント付与に関するチェック

      if ((bean.getCommodityPointStartDatetime() != null || bean.getCommodityPointEndDatetime() != null)
          && bean.getCommodityPointRate() == null) {
        errorMessageList.add(MessageFormat.format(
            Messages.getString("service.data.csv.CommodityHeaderImportDataSource.34"), bean.getCommodityCode())); //$NON-NLS-1$
      }
      if ((bean.getCommodityPointStartDatetime() != null || bean.getCommodityPointEndDatetime() != null)
          && bean.getCommodityPointRate() != null && (bean.getCommodityPointRate() < 0 || 101 <= bean.getCommodityPointRate())) {
        errorMessageList.add(MessageFormat.format(
            Messages.getString("service.data.csv.CommodityHeaderImportDataSource.35"), bean.getCommodityCode())); //$NON-NLS-1$
      }
      if (bean.getCommodityPointStartDatetime() == null && bean.getCommodityPointEndDatetime() == null
          && bean.getCommodityPointRate() != null) {
        errorMessageList.add(MessageFormat.format(
            Messages.getString("service.data.csv.CommodityHeaderImportDataSource.36"), bean.getCommodityCode())); //$NON-NLS-1$
      }
    }

  }

  private void executeUpdatePrice() throws SQLException {

    Logger logger = Logger.getLogger(this.getClass());

    // 代表SKUが存在しない場合は処理しない
    if (representSku == null) {
      return;
    }

    SimpleQuery query = new SimpleQuery(DatabaseUtil.getSelectAllQuery(CommodityDetail.class)
        + " WHERE SHOP_CODE = ? AND COMMODITY_CODE = ? AND SKU_CODE <> ? ");
    query.setParameters(header.getShopCode(), header.getCommodityCode(), header.getRepresentSkuCode());
    List<CommodityDetail> details = loadAsBeanList(query, CommodityDetail.class);
    for (CommodityDetail detail : details) {

      // 特別価格の設定

      if (NumUtil.isNull(representSku.getDiscountPrice())) {
        detail.setDiscountPrice(null);
      } else {
        detail.setDiscountPrice(NumUtil.coalesce(detail.getDiscountPrice(), representSku.getDiscountPrice()));
      }

      // 予約価格の設定

      if (NumUtil.isNull(representSku.getReservationPrice())) {
        detail.setReservationPrice(null);
      } else {
        detail.setReservationPrice(NumUtil.coalesce(detail.getReservationPrice(), representSku.getReservationPrice()));
      }

      // 改定価格の設定

      if (NumUtil.isNull(representSku.getChangeUnitPrice())) {
        detail.setChangeUnitPrice(null);
      } else {
        detail.setChangeUnitPrice(NumUtil.coalesce(detail.getChangeUnitPrice(), representSku.getChangeUnitPrice()));
      }

      List<Object> priceParams = new ArrayList<Object>();
      priceParams.add(detail.getDiscountPrice());
      priceParams.add(detail.getReservationPrice());
      priceParams.add(detail.getChangeUnitPrice());

      priceParams.add(getCondition().getLoginInfo().getRecordingFormat());
      priceParams.add(DateUtil.getSysdate());

      priceParams.add(detail.getShopCode());
      priceParams.add(detail.getSkuCode());
      DatabaseUtil.bindParameters(updatePriceStatement, ArrayUtil.toArray(priceParams, Object.class));

      logger.debug("PRICE  Parameters: " + Arrays.toString(ArrayUtil.toArray(priceParams, Object.class)));

      int priceUpdCount = updatePriceStatement.executeUpdate();
      if (priceUpdCount < 1) {
        throw new CsvImportException();
      }
    }
  }

  private int executeUpdateHeader(boolean exists) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;

    if (exists) {
      params.add(header.getCommodityName());
      params.add(header.getRepresentSkuCode());
      params.add(representSku.getUnitPrice());
      params.add(header.getStockStatusNo());
      params.add(header.getStockManagementType());
      params.add(header.getCommodityTaxType());
      params.add(header.getCommodityDescriptionPc());
      params.add(header.getCommodityDescriptionMobile());
      params.add(header.getCommoditySearchWords());
      params.add(header.getShadowSearchWords());
      params.add(header.getSaleStartDatetime());
      params.add(header.getSaleEndDatetime());
      params.add(header.getChangeUnitPriceDatetime());
      params.add(header.getDiscountPriceStartDatetime());
      params.add(header.getDiscountPriceEndDatetime());
      params.add(header.getReservationStartDatetime());
      params.add(header.getReservationEndDatetime());
      params.add(header.getDeliveryTypeNo());
      params.add(header.getLinkUrl());

      if (NumUtil.isNull(header.getRecommendCommodityRank())) {
        params.add(99999999L);
      } else {
        params.add(header.getRecommendCommodityRank());
      }

      params.add(header.getCommodityStandard1Name());
      params.add(header.getCommodityStandard2Name());
      if (StringUtil.isNullOrEmpty(getCondition().getShopCode()) || config.getOperatingMode().equals(OperatingMode.ONE)) {
        params.add(header.getCommodityPointRate());
        params.add(header.getCommodityPointStartDatetime());
        params.add(header.getCommodityPointEndDatetime());
      }
      params.add(header.getSaleFlg());
      params.add(header.getDisplayClientType());
      params.add(header.getArrivalGoodsFlg());

      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      params.add(header.getShopCode());
      params.add(header.getCommodityCode());

      pstmt = updateHeaderStatement;
      logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    } else {
      params.add(header.getShopCode());
      params.add(header.getCommodityCode());
      params.add(header.getCommodityName());
      params.add(header.getRepresentSkuCode());
      params.add(representSku.getUnitPrice());
      params.add(header.getStockStatusNo());
      params.add(header.getStockManagementType());
      params.add(header.getCommodityTaxType());
      params.add(header.getCommodityDescriptionPc());
      params.add(header.getCommodityDescriptionMobile());
      params.add(header.getCommoditySearchWords());
      params.add(header.getShadowSearchWords());
      params.add(header.getSaleStartDatetime());
      params.add(header.getSaleEndDatetime());
      params.add(header.getChangeUnitPriceDatetime());
      params.add(header.getDiscountPriceStartDatetime());
      params.add(header.getDiscountPriceEndDatetime());
      params.add(header.getReservationStartDatetime());
      params.add(header.getReservationEndDatetime());
      params.add(header.getDeliveryTypeNo());
      params.add(header.getLinkUrl());

      if (NumUtil.isNull(header.getRecommendCommodityRank())) {
        params.add(99999999L);
      } else {
        params.add(header.getRecommendCommodityRank());
      }

      if (NumUtil.isNull(header.getCommodityPopularRank())) {
        params.add(99999999L);
      } else {
        params.add(header.getCommodityPopularRank());
      }
      params.add(header.getCommodityStandard1Name());
      params.add(header.getCommodityStandard2Name());
      if (StringUtil.isNullOrEmpty(getCondition().getShopCode()) || config.getOperatingMode().equals(OperatingMode.ONE)) {
        params.add(header.getCommodityPointRate());
        params.add(header.getCommodityPointStartDatetime());
        params.add(header.getCommodityPointEndDatetime());
      }
      params.add(header.getSaleFlg());
      params.add(header.getDisplayClientType());
      params.add(header.getArrivalGoodsFlg());

      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());
      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      pstmt = insertHeaderStatement;
      logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    }

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();

  }

  private int executeUpdateDetail(boolean exists) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();

    if (StringUtil.isNullOrEmpty(header.getCommodityStandard1Name())
        && StringUtil.isNullOrEmpty(header.getCommodityStandard2Name())) {
      executeDeleteStock();
      executeDeleteStockIoDetail();
      executeDeleteDetail();
      executeUpdateStandardName(true);
    } else if (StringUtil.isNullOrEmpty(header.getCommodityStandard2Name())) {
      executeUpdateStandardName(false);
    }

    if (exists) {
      // 10.1.6 10264
      CommodityDetailDao dao = DIContainer.getDao(CommodityDetailDao.class);
      CommodityDetail commodityDetail = dao.load(representSku.getShopCode(), representSku.getSkuCode());
      // 10.1.6 10264
      params.add(representSku.getCommodityCode());
      params.add(representSku.getUnitPrice());
      params.add(representSku.getDiscountPrice());
      params.add(representSku.getReservationPrice());
      params.add(representSku.getChangeUnitPrice());
      params.add(representSku.getJanCode());
      // 10.1.6 10264
      // params.add(representSku.getDisplayOrder());
      params.add(commodityDetail.getDisplayOrder());
      // 10.1.6 10264
      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      params.add(representSku.getShopCode());
      params.add(representSku.getSkuCode());

      pstmt = updateDetailStatement;
      logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    } else {
      params.add(representSku.getShopCode());
      params.add(representSku.getSkuCode());
      params.add(representSku.getCommodityCode());
      params.add(representSku.getUnitPrice());
      params.add(representSku.getDiscountPrice());
      params.add(representSku.getReservationPrice());
      params.add(representSku.getChangeUnitPrice());
      params.add(representSku.getJanCode());
      params.add(representSku.getDisplayOrder());
      params.add(representSku.getStandardDetail1Name());
      params.add(representSku.getStandardDetail2Name());

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

  private int executeUpdateStock(boolean exists) throws SQLException {
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

  private int executeDeleteDetail() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    SimpleQuery getDetailsQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(CommodityDetail.class)
        + " WHERE SHOP_CODE = ? AND COMMODITY_CODE = ? AND SKU_CODE <> ? ");
    getDetailsQuery.setParameters(header.getShopCode(), header.getCommodityCode(), header.getRepresentSkuCode());
    List<CommodityDetail> details = loadAsBeanList(getDetailsQuery, CommodityDetail.class);
    for (CommodityDetail detail : details) {
      Query checkDeletetableQuery = new SimpleQuery(CommodityDeleteQuery.getNotFixedSaleCommodityCountQuery(),
          detail.getShopCode(), detail.getSkuCode());
      Long count = Long.valueOf(executeScalar(checkDeletetableQuery).toString());
      if (count > 0) {
        ValidationSummary summary = new ValidationSummary();
        ValidationResult result = new ValidationResult(null, null, Message.get(CsvMessage.DELETE_COMMODITY_ERROR, header
            .getCommodityCode()));
        summary.getErrors().add(result);
        throw new CsvImportException(summary);
      }
    }

    List<Object> deleteParams = new ArrayList<Object>();
    deleteParams.add(representSku.getShopCode());
    deleteParams.add(representSku.getCommodityCode());
    deleteParams.add(header.getRepresentSkuCode());

    pstmt = deleteCommodityDetailStatement;
    logger.debug("DELETE Parameters: " + Arrays.toString(ArrayUtil.toArray(deleteParams, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(deleteParams, Object.class));

    return pstmt.executeUpdate();
  }

  private int executeDeleteStock() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;
    List<Object> deleteParams = new ArrayList<Object>();
    deleteParams.add(representSku.getShopCode());
    deleteParams.add(representSku.getCommodityCode());
    deleteParams.add(header.getRepresentSkuCode());

    pstmt = deleteStockStatement;
    logger.debug("DELETE Parameters: " + Arrays.toString(ArrayUtil.toArray(deleteParams, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(deleteParams, Object.class));

    return pstmt.executeUpdate();
  }

  private int executeDeleteStockIoDetail() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;
    List<Object> deleteParams = new ArrayList<Object>();
    deleteParams.add(representSku.getShopCode());
    deleteParams.add(representSku.getCommodityCode());
    deleteParams.add(header.getRepresentSkuCode());

    pstmt = deleteStockIoDetailStatement;
    logger.debug("DELETE Parameters: " + Arrays.toString(ArrayUtil.toArray(deleteParams, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(deleteParams, Object.class));

    return pstmt.executeUpdate();
  }

  private int executeUpdateStandardName(boolean isUpdateAllStandardName) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());

    params.add(representSku.getShopCode());
    params.add(representSku.getCommodityCode());

    if (isUpdateAllStandardName) {
      pstmt = updateStandardNameStatement;
    } else {
      pstmt = updateStandardName2Statement;
    }
    logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }

  private String getInsertHeaderQuery() {
    String insertSql = "" + " INSERT INTO " + HEADER_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'commodity_header_seq'") + ", ?, ?, ?, ?) ";
    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("SHOP_CODE");
    columnList.add("COMMODITY_CODE");
    columnList.add("COMMODITY_NAME");
    columnList.add("REPRESENT_SKU_CODE");
    columnList.add("REPRESENT_SKU_UNIT_PRICE");
    columnList.add("STOCK_STATUS_NO");
    columnList.add("STOCK_MANAGEMENT_TYPE");
    columnList.add("COMMODITY_TAX_TYPE");
    columnList.add("COMMODITY_DESCRIPTION_PC");
    columnList.add("COMMODITY_DESCRIPTION_MOBILE");
    columnList.add("COMMODITY_SEARCH_WORDS");
    columnList.add("SHADOW_SEARCH_WORDS");
    columnList.add("SALE_START_DATETIME");
    columnList.add("SALE_END_DATETIME");
    columnList.add("CHANGE_UNIT_PRICE_DATETIME");
    columnList.add("DISCOUNT_PRICE_START_DATETIME");
    columnList.add("DISCOUNT_PRICE_END_DATETIME");
    columnList.add("RESERVATION_START_DATETIME");
    columnList.add("RESERVATION_END_DATETIME");
    columnList.add("DELIVERY_TYPE_NO");
    columnList.add("LINK_URL");
    columnList.add("RECOMMEND_COMMODITY_RANK");
    columnList.add("COMMODITY_POPULAR_RANK");
    columnList.add("COMMODITY_STANDARD1_NAME");
    columnList.add("COMMODITY_STANDARD2_NAME");
    if (StringUtil.isNullOrEmpty(getCondition().getShopCode()) || config.getOperatingMode().equals(OperatingMode.ONE)) {
      columnList.add("COMMODITY_POINT_RATE");
      columnList.add("COMMODITY_POINT_START_DATETIME");
      columnList.add("COMMODITY_POINT_END_DATETIME");
    }
    columnList.add("SALE_FLG");
    columnList.add("DISPLAY_CLIENT_TYPE");
    columnList.add("ARRIVAL_GOODS_FLG");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }

    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  private String getUpdateHeaderQuery() {
    String updateSql = "" + " UPDATE " + HEADER_TABLE_NAME
        + " SET {0} UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE SHOP_CODE = ? AND COMMODITY_CODE = ? ";

    StringBuilder setPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("COMMODITY_NAME");
    columnList.add("REPRESENT_SKU_CODE");
    columnList.add("REPRESENT_SKU_UNIT_PRICE");
    columnList.add("STOCK_STATUS_NO");
    columnList.add("STOCK_MANAGEMENT_TYPE");
    columnList.add("COMMODITY_TAX_TYPE");
    columnList.add("COMMODITY_DESCRIPTION_PC");
    columnList.add("COMMODITY_DESCRIPTION_MOBILE");
    columnList.add("COMMODITY_SEARCH_WORDS");
    columnList.add("SHADOW_SEARCH_WORDS");
    columnList.add("SALE_START_DATETIME");
    columnList.add("SALE_END_DATETIME");
    columnList.add("CHANGE_UNIT_PRICE_DATETIME");
    columnList.add("DISCOUNT_PRICE_START_DATETIME");
    columnList.add("DISCOUNT_PRICE_END_DATETIME");
    columnList.add("RESERVATION_START_DATETIME");
    columnList.add("RESERVATION_END_DATETIME");
    columnList.add("DELIVERY_TYPE_NO");
    columnList.add("LINK_URL");
    columnList.add("RECOMMEND_COMMODITY_RANK");
    columnList.add("COMMODITY_STANDARD1_NAME");
    columnList.add("COMMODITY_STANDARD2_NAME");
    if (StringUtil.isNullOrEmpty(getCondition().getShopCode()) || config.getOperatingMode().equals(OperatingMode.ONE)) {
      columnList.add("COMMODITY_POINT_RATE");
      columnList.add("COMMODITY_POINT_START_DATETIME");
      columnList.add("COMMODITY_POINT_END_DATETIME");
    }
    columnList.add("SALE_FLG");
    columnList.add("DISPLAY_CLIENT_TYPE");
    columnList.add("ARRIVAL_GOODS_FLG");

    for (String column : columnList) {
      setPart.append(column + " = ?, ");
    }

    return MessageFormat.format(updateSql, setPart.toString());

  }

  private String[] getHeaderValidationFields() {
    List<String> fieldList = new ArrayList<String>();
    fieldList.add("shopCode");
    fieldList.add("commodityCode");
    fieldList.add("commodityName");
    fieldList.add("representSkuCode");
    fieldList.add("stockStatusNo");
    fieldList.add("stockManagementType");
    fieldList.add("commodityDescriptionPc");
    fieldList.add("commodityDescriptionMobile");
    fieldList.add("commoditySearchWords");
    fieldList.add("saleStartDatetime");
    fieldList.add("saleEndDatetime");
    fieldList.add("changeUnitPriceDatetime");
    fieldList.add("discountPriceStartDatetime");
    fieldList.add("discountPriceEndDatetime");
    fieldList.add("reservationStartDatetime");
    fieldList.add("reservationEndDatetime");
    fieldList.add("deliveryTypeNo");
    fieldList.add("linkUrl");
    fieldList.add("commodityStandard1Name");
    fieldList.add("commodityStandard2Name");
    if (StringUtil.isNullOrEmpty(getCondition().getShopCode()) || config.getOperatingMode().equals(OperatingMode.ONE)) {
      fieldList.add("commodityPointRate");
      fieldList.add("commodityPointStartDatetime");
      fieldList.add("commodityPointEndDatetime");
    }
    fieldList.add("saleFlg");
    fieldList.add("displayClientType");
    fieldList.add("arrivalGoodsFlg");

    return ArrayUtil.toArray(fieldList, String.class);
  }

  // 10.1.1 10019 追加 ここから
  private void checkMinusNumber(ValidationSummary summary) {
    if (NumUtil.isNegative(header.getStockStatusNo())) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityHeaderImportDataSource.38"))));
    }
    if (NumUtil.isNegative(header.getDeliveryTypeNo())) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityHeaderImportDataSource.39"))));
    }
    if (NumUtil.isNegative(header.getRecommendCommodityRank())) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityHeaderImportDataSource.40"))));
    }
    if (representSku.getUnitPrice() != null && BigDecimalUtil.isBelow(representSku.getUnitPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityHeaderImportDataSource.41"))));
    }
    if (representSku.getDiscountPrice() != null && BigDecimalUtil.isBelow(representSku.getDiscountPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityHeaderImportDataSource.42"))));
    }
    if (representSku.getReservationPrice() != null && BigDecimalUtil.isBelow(representSku.getReservationPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityHeaderImportDataSource.43"))));
    }
    if (representSku.getChangeUnitPrice() != null && BigDecimalUtil.isBelow(representSku.getChangeUnitPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityHeaderImportDataSource.44"))));
    }
    // 10.1.6 10281 削除 ここから
    // if (NumUtil.isNegative(stock.getReservationLimit())) {
    // summary.getErrors().add(
    // new ValidationResult(null, null,
    // Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
    // .getString("service.data.csv.CommodityHeaderImportDataSource.45"))));
    // }
    // if (NumUtil.isNegative(stock.getOneshotReservationLimit())) {
    // summary.getErrors().add(
    // new ValidationResult(null, null,
    // Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
    // .getString("service.data.csv.CommodityHeaderImportDataSource.46"))));
    // }
    // 10.1.6 10281 削除 ここまで
    if (NumUtil.isNegative(stock.getStockThreshold())) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityHeaderImportDataSource.47"))));
    }
  }
  // 10.1.1 10019 追加 ここまで

}
