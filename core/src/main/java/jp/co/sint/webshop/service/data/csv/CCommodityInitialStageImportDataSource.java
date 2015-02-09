package jp.co.sint.webshop.service.data.csv;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.dao.CategoryDao;
import jp.co.sint.webshop.data.domain.JdUseFlg;
import jp.co.sint.webshop.data.domain.RatioType;
import jp.co.sint.webshop.data.domain.SyncFlagJd;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityExt;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CategoryAttribute;
import jp.co.sint.webshop.data.dto.CategoryAttributeValue;
import jp.co.sint.webshop.data.dto.CategoryCommodity;
import jp.co.sint.webshop.data.dto.OriginalPlace;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.catalog.CCommodityHeaderImport;
import jp.co.sint.webshop.service.catalog.CatalogQuery;
import jp.co.sint.webshop.service.catalog.CategoryAttributeValueImport;
import jp.co.sint.webshop.service.catalog.CategoryCommodityImport;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.validation.ValidatorUtil;

import org.apache.log4j.Logger;

public class CCommodityInitialStageImportDataSource extends
    SqlImportDataSource<CCommodityInitialStageCsvSchema, CCommodityInitialStageImportCondition> {

  WebshopConfig config = DIContainer.getWebshopConfig();

  private static final String DATAIL_TABLE_NAME = DatabaseUtil.getTableName(CCommodityDetail.class);

  private static final String HEADER_TABLE_NAME = DatabaseUtil.getTableName(CCommodityHeader.class);

  // 20120207 os013 add start
  // 在库
  private static final String STOCK_TABLE_NAME = DatabaseUtil.getTableName(Stock.class);

  // カテゴリ陳列商品
  private static final String CATEGORY_COMMODITY_TABLE_NAME = DatabaseUtil.getTableName(CategoryCommodity.class);

  // カテゴリ属性値
  private static final String CATEGORY_ATTRIBUTE_VALUE_TABLE_NAME = DatabaseUtil.getTableName(CategoryAttributeValue.class);

  // 商品拡張情報
  private static final String EXT_TABLE_NAME = DatabaseUtil.getTableName(CCommodityExt.class);

  /** 商品詳細INSERT用Statement */
  private PreparedStatement insertDetailStatement = null;

  /** 商品ヘッダINSERT用Statement */
  private PreparedStatement insertHeaderStatement = null;

  /** 商品ヘッダUPDATE用Statement */
  private PreparedStatement updateHeaderStatement = null;

  /** 商品ヘッダINSERT用Statement */
  private PreparedStatement insertStockStatement = null;

  /** カテゴリ陳列商品INSERT用Statement */
  private PreparedStatement insertCategoryCommodityStatement = null;

  /** カテゴリ属性値INSERT用Statement */
  private PreparedStatement insertCategoryAttributeValueStatement = null;

  /** 商品拡張情報INSERT用Statement */
  private PreparedStatement insertExtStatement = null;

  /** 商品拡張情報SELECT用Statement */
  private PreparedStatement selectExtStatement = null;

  /** カテゴリ属性値SELECT用Statement */
  private PreparedStatement selectCategoryAttributeValueStatement = null;

  /** カテゴリ属性値UPDATE用Statement */
  private PreparedStatement updateCategoryAttributeValueStatement = null;

  /** カテゴリ属性値DELETE用Statement */
  private PreparedStatement deleteCategoryAttributeValueStatement = null;

  /** カテゴリ陳列商品SELECT用Statement */
  private PreparedStatement selectCategoryCommodityStatement = null;

  /** 在库SELECT用Statement */
  private PreparedStatement selectStockStatement = null;

  // 201202007 os013 add end
  /** 商品明细SELECT用Statement */
  private PreparedStatement selectDetailStatement = null;

  /** 商品基本SELECT用Statement */
  private PreparedStatement selectHeaderStatement = null;

  private CCommodityHeaderImport header = null;

  private CCommodityDetail detail = null;

  // 20120207 os013 add start
  
  private Stock stock = null;

  private CategoryCommodityImport categoryCommodity = null;

  private CategoryAttributeValueImport categoryAttributeValue = null;

  private CCommodityExt ccommodityExt = null;

  boolean existsHeader = false;

  boolean existsDetail = false;

  boolean existsStock = false;

  boolean existsCategory = false;

  boolean existsValue = false;

  boolean existsExt = false;

  /** 商品拡張情報 */
  private List<CsvColumn> extColumns = new ArrayList<CsvColumn>();

  /** カテゴリ属性値 */
  private List<CsvColumn> valueColumns = new ArrayList<CsvColumn>();

  /** カテゴリ陳列商品 */
  private List<CsvColumn> categoryCommodityColumns = new ArrayList<CsvColumn>();

  /** 在库 */
  private List<CsvColumn> stockColumns = new ArrayList<CsvColumn>();

  // 20120207 os013 add end
  /** 商品基本 */
  private List<CsvColumn> headerColumns = new ArrayList<CsvColumn>();

  /** 商品明细 */
  private List<CsvColumn> detailColumns = new ArrayList<CsvColumn>();

  // 品店カテゴリID1検索用カテゴリパス
  String categorySearchPath1 = "";

  // 品店カテゴリID2検索用カテゴリパス
  String categorySearchPath2 = "";

  // 品店カテゴリID3検索用カテゴリパス
  String categorySearchPath3 = "";

  // 品店カテゴリID4検索用カテゴリパス
  String categorySearchPath4 = "";

  // 品店カテゴリID5検索用カテゴリパス
  String categorySearchPath5 = "";

//2014/06/11 库存更新对应 ob_yuan update start
  //EC库存比例
  private Long ecStockRatio = 100L;
  //TM库存比例
  private Long tmStockRatio = 0L;
  //JD库存比例
  private Long jdStockRatio = 0L;
//2014/06/11 库存更新对应 ob_yuan update end
  @Override
  protected void initializeResources() {
    Logger logger = Logger.getLogger(this.getClass());
    // 商品明细表新建Query
    logger.debug("INSERT statement: " + getInsertDetailQuery());
    // 商品基本表新建Query
    logger.debug("INSERT statement: " + getInsertHeaderQuery());
    // 商品基本表修改Query
    logger.debug("INSERT statement: " + getUpdateHeaderQuery());
    // 在库表新建Query
    logger.debug("INSERT statement: " + getInsertStockQuery());
    // カテゴリ陳列商品表新建Query
    logger.debug("INSERT statement: " + getInsertCategoryCommodityQuery());
    // カテゴリ属性値表新建Query
    logger.debug("INSERT statement: " + getInsertCategoryAttributeValueQuery());
    // カテゴリ属性値表更新Query
    logger.debug("UPDATE statement: " + getUpdateCategoryAttributeValueQuery());
    // 商品拡張情報表新建Query
    logger.debug("INSERT statement: " + getInsertCCommodityExtQuery());

    // 在库表查询存在
    logger.debug("CHECK  statement: " + getSelectStockQuery());
    // カテゴリ陳列商品表查询存在
    logger.debug("CHECK  statement: " + getSelectCategoryCommodityQuery());
    // カテゴリ属性値表查询存在
    logger.debug("CHECK  statement: " + getSelectCategoryAttributeValueQuery());
    // 商品拡張情報表查询存在
    logger.debug("CHECK  statement: " + getSelectCCommodityExtQuery());
    // 商品详细表查询存在
    logger.debug("CHECK  statement: " + getSelectDetailQuery());
    // 商品基本表查询存在
    logger.debug("CHECK  statement: " + getSelectHeaderQuery());
    logger.debug("DELETE statement: " + getDeleteCategoryAttributeValueQuery());

    try {
      // 商品详细表查询存在
      selectDetailStatement = createPreparedStatement(getSelectDetailQuery());
      // 商品基本表查询存在
      selectHeaderStatement = createPreparedStatement(getSelectHeaderQuery());
      // 在库表查询存在
      selectStockStatement = createPreparedStatement(getSelectStockQuery());
      // 商品拡張情報表查询存在
      selectExtStatement = createPreparedStatement(getSelectCCommodityExtQuery());
      // カテゴリ陳列商品表查询存在
      selectCategoryCommodityStatement = createPreparedStatement(getSelectCategoryCommodityQuery());
      // カテゴリ属性値表查询存在
      selectCategoryAttributeValueStatement = createPreparedStatement(getSelectCategoryAttributeValueQuery());
      // カテゴリ属性値删除
      deleteCategoryAttributeValueStatement = createPreparedStatement(getDeleteCategoryAttributeValueQuery());
      
    //2014/06/11 库存更新对应 ob_yuan update start
      if (checkStockRatio(config.getStockRatio())) {
        String[] stockRatioArray = config.getStockRatio().split(":");
        //EC库存比例
        ecStockRatio = NumUtil.toLong(stockRatioArray[0]);
        //TM库存比例
        tmStockRatio = NumUtil.toLong(stockRatioArray[1]);
        //JD库存比例
        jdStockRatio = NumUtil.toLong(stockRatioArray[2]);
      }
    //2014/06/11 库存更新对应 ob_yuan update end
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public boolean setSchema(List<String> csvLine) {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    for (String column : csvLine) {
      // ショップコード
      if (column.equals("shop_code")) {
        columns.add(new CsvColumnImpl("shop_code", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.0"),
            CsvDataType.STRING, false, false, true, null));
        headerColumns.add(new CsvColumnImpl("shop_code", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.0"),
            CsvDataType.STRING, false, false, true, null));
        // 商品コード
      } else if (column.equals("commodity_code")) {
        columns.add(new CsvColumnImpl("commodity_code", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.1"),
            CsvDataType.STRING, false, false, true, null));
        headerColumns.add(new CsvColumnImpl("commodity_code", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.1"),
            CsvDataType.STRING, false, false, true, null));
        detailColumns.add(new CsvColumnImpl("commodity_code", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.1"),
            CsvDataType.STRING, false, false, true, null));
        stockColumns.add(new CsvColumnImpl("commodity_code", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.1"),
            CsvDataType.STRING, false, false, true, null));
        // 商品名称
      } else if (column.equals("commodity_name")) {
        columns.add(new CsvColumnImpl("commodity_name", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.2"),
            CsvDataType.STRING, false, false, true, null));
        headerColumns.add(new CsvColumnImpl("commodity_name", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.2"),
            CsvDataType.STRING, false, false, true, null));
        // 商品名称英字
      } else if (column.equals("commodity_name_en")) {
        columns.add(new CsvColumnImpl("commodity_name_en", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.3"),
            CsvDataType.STRING, false, false, true, null));
        headerColumns.add(new CsvColumnImpl("commodity_name_en",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.3"), CsvDataType.STRING, false, false, true, null));
        // 商品名称日文
      } else if (column.equals("commodity_name_jp")) {
        columns.add(new CsvColumnImpl("commodity_name_jp", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.86"),
            CsvDataType.STRING, false, false, true, null));
        headerColumns.add(new CsvColumnImpl("commodity_name_jp", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.86"), CsvDataType.STRING, false, false, true, null));
        // 代表SKUコード
      } else if (column.equals("represent_sku_code")) {
        columns.add(new CsvColumnImpl("represent_sku_code", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.4"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("represent_sku_code", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.4"), CsvDataType.STRING));
        // 代表SKU単価
      } else if (column.equals("represent_sku_unit_price")) {
        columns.add(new CsvColumnImpl("represent_sku_unit_price", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.5"), CsvDataType.BIGDECIMAL));
        headerColumns.add(new CsvColumnImpl("represent_sku_unit_price", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.5"), CsvDataType.BIGDECIMAL));
        // 商品説明1
      } else if (column.equals("commodity_description1")) {
        columns.add(new CsvColumnImpl("commodity_description1", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.6"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description1", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.6"), CsvDataType.STRING));
        // 商品説明1英文
      } else if (column.equals("commodity_description1_en")) {
        columns.add(new CsvColumnImpl("commodity_description1_en", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.87"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description1_en", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.87"), CsvDataType.STRING));
        // 商品説明1日文
      } else if (column.equals("commodity_description1_jp")) {
        columns.add(new CsvColumnImpl("commodity_description1_jp", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.88"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description1_jp", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.88"), CsvDataType.STRING));
        // 商品説明2
      } else if (column.equals("commodity_description2")) {
        columns.add(new CsvColumnImpl("commodity_description2", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.7"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description2", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.7"), CsvDataType.STRING));
        // 商品説明2英文
      } else if (column.equals("commodity_description2_en")) {
        columns.add(new CsvColumnImpl("commodity_description2_en", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.89"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description2_en", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.89"), CsvDataType.STRING));
        // 商品説明2日文
      } else if (column.equals("commodity_description2_jp")) {
        columns.add(new CsvColumnImpl("commodity_description2_jp", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.90"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description2_jp", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.90"), CsvDataType.STRING));
        // 商品説明3
      } else if (column.equals("commodity_description3")) {
        columns.add(new CsvColumnImpl("commodity_description3", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.8"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description3", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.8"), CsvDataType.STRING));
        // 商品説明3英文
      } else if (column.equals("commodity_description3_en")) {
        columns.add(new CsvColumnImpl("commodity_description3_en", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.91"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description3_en", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.91"), CsvDataType.STRING));
        // 商品説明3日文
      } else if (column.equals("commodity_description3_jp")) {
        columns.add(new CsvColumnImpl("commodity_description3_jp", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.92"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description3_jp", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.92"), CsvDataType.STRING));
        // 商品説明(一覧用）
      } else if (column.equals("commodity_description_short")) {
        columns.add(new CsvColumnImpl("commodity_description_short", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.9"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description_short", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.9"), CsvDataType.STRING));
        // 商品説明(一覧用）英文
      } else if (column.equals("commodity_description_short_en")) {
        columns.add(new CsvColumnImpl("commodity_description_short_en", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.93"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description_short_en", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.93"), CsvDataType.STRING));
        // 商品説明(一覧用）日文
      } else if (column.equals("commodity_description_short_jp")) {
        columns.add(new CsvColumnImpl("commodity_description_short_jp", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.94"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description_short_jp", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.94"), CsvDataType.STRING));
        // 商品検索ワード
      } else if (column.equals("commodity_search_words")) {
        columns.add(new CsvColumnImpl("commodity_search_words",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.10"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_search_words", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.10"), CsvDataType.STRING));
        // 販売開始日時
      } else if (column.equals("sale_start_datetime")) {
        columns.add(new CsvColumnImpl("sale_start_datetime", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.11"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("sale_start_datetime", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.11"), CsvDataType.STRING));
        // 販売終了日時
      } else if (column.equals("sale_end_datetime")) {
        columns.add(new CsvColumnImpl("sale_end_datetime", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.12"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("sale_end_datetime", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.12"), CsvDataType.STRING));
        // 特別価格開始日時
      } else if (column.equals("discount_price_start_datetime")) {
        columns.add(new CsvColumnImpl("discount_price_start_datetime", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.13"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("discount_price_start_datetime", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.13"), CsvDataType.STRING));
        // 特別価格終了日時
      } else if (column.equals("discount_price_end_datetime")) {
        columns.add(new CsvColumnImpl("discount_price_end_datetime", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.14"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("discount_price_end_datetime", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.14"), CsvDataType.STRING));
        // 規格1名称ID(TMALLの属性ID）
      } else if (column.equals("standard1_id")) {
        columns.add(new CsvColumnImpl("standard1_id", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.15"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("standard1_id", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.15"),
            CsvDataType.STRING));
        // 規格1名称
      } else if (column.equals("standard1_name")) {
        columns.add(new CsvColumnImpl("standard1_name", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.16"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("standard1_name", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.16"),
            CsvDataType.STRING));
        // 規格1名称英文
      } else if (column.equals("standard1_name_en")) {
        columns.add(new CsvColumnImpl("standard1_name_en", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.95"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("standard1_name_en", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.95"), CsvDataType.STRING));
        // 規格1名称日文
      } else if (column.equals("standard1_name_jp")) {
        columns.add(new CsvColumnImpl("standard1_name_jp", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.96"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("standard1_name_jp", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.96"), CsvDataType.STRING));
        // 規格2名称ID(TMALLの属性ID）
      } else if (column.equals("standard2_id")) {
        columns.add(new CsvColumnImpl("standard2_id", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.17"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("standard2_id", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.17"),
            CsvDataType.STRING));
        // 規格2名称
      } else if (column.equals("standard2_name")) {
        columns.add(new CsvColumnImpl("standard2_name", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.18"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("standard2_name", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.18"),
            CsvDataType.STRING));
        // 規格2名称英文
      } else if (column.equals("standard2_name_en")) {
        columns.add(new CsvColumnImpl("standard2_name_en", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.97"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("standard2_name_en", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.97"), CsvDataType.STRING));
        // 規格2名称日文
      } else if (column.equals("standard2_name_jp")) {
        columns.add(new CsvColumnImpl("standard2_name_jp", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.98"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("standard2_name_jp", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.98"), CsvDataType.STRING));
        // 返品不可フラグ
        // 供货商换货
      } else if (column.equals("return_flg_cust")) {
        columns.add(new CsvColumnImpl("return_flg_cust", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.83"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("return_flg_cust", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.83"),
            CsvDataType.STRING));
        // 供货商退货
      } else if (column.equals("return_flg_supp")) {
        columns.add(new CsvColumnImpl("return_flg_supp", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.84"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("return_flg_supp", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.84"),
            CsvDataType.STRING));
        // 顾客退货
      } else if (column.equals("change_flg_supp")) {
        columns.add(new CsvColumnImpl("change_flg_supp", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.85"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("change_flg_supp", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.85"),
            CsvDataType.STRING));
        // ワーニング区分
      } else if (column.equals("warning_flag")) {
        columns.add(new CsvColumnImpl("warning_flag", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.22"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("warning_flag", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.22"),
            CsvDataType.STRING));
        // リードタイム
      } else if (column.equals("lead_time")) {
        columns.add(new CsvColumnImpl("lead_time", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.23"),
            CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("lead_time", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.23"),
            CsvDataType.NUMBER));
        // セール区分
      } else if (column.equals("sale_flag")) {
        columns.add(new CsvColumnImpl("sale_flag", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.24"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("sale_flag", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.24"),
            CsvDataType.STRING));
        // 特集区分
      } else if (column.equals("spec_flag")) {
        columns.add(new CsvColumnImpl("spec_flag", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.25"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("spec_flag", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.25"),
            CsvDataType.STRING));
        // ブランドコード
      } else if (column.equals("brand_code")) {
        columns.add(new CsvColumnImpl("brand_code", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.26"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("brand_code", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.26"),
            CsvDataType.STRING));
        // TMALL代表SKU単価
      } else if (column.equals("tmall_represent_sku_price")) {
        columns.add(new CsvColumnImpl("tmall_represent_sku_price", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.28"), CsvDataType.BIGDECIMAL));
        headerColumns.add(new CsvColumnImpl("tmall_represent_sku_price", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.28"), CsvDataType.BIGDECIMAL));
        // TMALLのカテゴリID
      } else if (column.equals("tmall_category_id")) {
        columns.add(new CsvColumnImpl("tmall_category_id", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.29"),
            CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("tmall_category_id", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.29"), CsvDataType.NUMBER));
        // 取引先コード
      } else if (column.equals("supplier_code")) {
        columns.add(new CsvColumnImpl("supplier_code", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.30"),
            CsvDataType.STRING, false, false, true, null));
        headerColumns.add(new CsvColumnImpl("supplier_code", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.30"),
            CsvDataType.STRING, false, false, true, null));
        // 仕入担当者コード
      } else if (column.equals("buyer_code")) {
        columns.add(new CsvColumnImpl("buyer_code", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.31"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("buyer_code", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.31"),
            CsvDataType.STRING));
        // 産地
      } else if (column.equals("original_place")) {
        columns.add(new CsvColumnImpl("original_place", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.33"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("original_place", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.33"),
            CsvDataType.STRING));
        // 産地英文
      } else if (column.equals("original_place_en")) {
        columns.add(new CsvColumnImpl("original_place_en", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.102"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("original_place_en", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.102"), CsvDataType.STRING));
        // 産地日文
      } else if (column.equals("original_place_jp")) {
        columns.add(new CsvColumnImpl("original_place_jp", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.103"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("original_place_jp", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.103"), CsvDataType.STRING));
        // 成分1
      } else if (column.equals("ingredient_name1")) {
        columns.add(new CsvColumnImpl("ingredient_name1", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.34"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name1",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.34"), CsvDataType.STRING));
        // 成分量1
      } else if (column.equals("ingredient_val1")) {
        columns.add(new CsvColumnImpl("ingredient_val1", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.35"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val1", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.35"),
            CsvDataType.STRING));
        // 成分2
      } else if (column.equals("ingredient_name2")) {
        columns.add(new CsvColumnImpl("ingredient_name2", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.36"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name2",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.36"), CsvDataType.STRING));
        // 成分量2
      } else if (column.equals("ingredient_val2")) {
        columns.add(new CsvColumnImpl("ingredient_val2", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.37"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val2", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.37"),
            CsvDataType.STRING));
        // 成分3
      } else if (column.equals("ingredient_name3")) {
        columns.add(new CsvColumnImpl("ingredient_name3", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.38"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name3",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.38"), CsvDataType.STRING));
        // 成分量3
      } else if (column.equals("ingredient_val3")) {
        columns.add(new CsvColumnImpl("ingredient_val3", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.39"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val3", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.39"),
            CsvDataType.STRING));
        // 成分4
      } else if (column.equals("ingredient_name4")) {
        columns.add(new CsvColumnImpl("ingredient_name4", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.40"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name4",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.40"), CsvDataType.STRING));
        // 成分量4
      } else if (column.equals("ingredient_val4")) {
        columns.add(new CsvColumnImpl("ingredient_val4", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.41"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val4", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.41"),
            CsvDataType.STRING));
        // 成分5
      } else if (column.equals("ingredient_name5")) {
        columns.add(new CsvColumnImpl("ingredient_name5", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.42"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name5",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.42"), CsvDataType.STRING));
        // 成分量5
      } else if (column.equals("ingredient_val5")) {
        columns.add(new CsvColumnImpl("ingredient_val5", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.43"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val5", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.43"),
            CsvDataType.STRING));
        // 成分6
      } else if (column.equals("ingredient_name6")) {
        columns.add(new CsvColumnImpl("ingredient_name6", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.44"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name6",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.44"), CsvDataType.STRING));
        // 成分量6
      } else if (column.equals("ingredient_val6")) {
        columns.add(new CsvColumnImpl("ingredient_val6", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.45"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val6", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.45"),
            CsvDataType.STRING));
        // 成分7
      } else if (column.equals("ingredient_name7")) {
        columns.add(new CsvColumnImpl("ingredient_name7", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.46"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name7",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.46"), CsvDataType.STRING));
        // 成分量7
      } else if (column.equals("ingredient_val7")) {
        columns.add(new CsvColumnImpl("ingredient_val7", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.47"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val7", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.47"),
            CsvDataType.STRING));
        // 成分8
      } else if (column.equals("ingredient_name8")) {
        columns.add(new CsvColumnImpl("ingredient_name8", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.48"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name8",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.48"), CsvDataType.STRING));
        // 成分量8
      } else if (column.equals("ingredient_val8")) {
        columns.add(new CsvColumnImpl("ingredient_val8", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.49"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val8", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.49"),
            CsvDataType.STRING));
        // 成分9
      } else if (column.equals("ingredient_name9")) {
        columns.add(new CsvColumnImpl("ingredient_name9", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.50"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name9",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.50"), CsvDataType.STRING));
        // 成分量9
      } else if (column.equals("ingredient_val9")) {
        columns.add(new CsvColumnImpl("ingredient_val9", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.51"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val9", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.51"),
            CsvDataType.STRING));
        // 成分10
      } else if (column.equals("ingredient_name10")) {
        columns.add(new CsvColumnImpl("ingredient_name10", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.52"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name10", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.52"), CsvDataType.STRING));
        // 成分量10
      } else if (column.equals("ingredient_val10")) {
        columns.add(new CsvColumnImpl("ingredient_val10", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.53"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val10",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.53"), CsvDataType.STRING));
        // 成分11
      } else if (column.equals("ingredient_name11")) {
        columns.add(new CsvColumnImpl("ingredient_name11", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.54"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name11", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.54"), CsvDataType.STRING));
        // 成分量11
      } else if (column.equals("ingredient_val11")) {
        columns.add(new CsvColumnImpl("ingredient_val11", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.55"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val11",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.55"), CsvDataType.STRING));
        // 成分12
      } else if (column.equals("ingredient_name12")) {
        columns.add(new CsvColumnImpl("ingredient_name12", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.56"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name12", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.56"), CsvDataType.STRING));
        // 成分量12
      } else if (column.equals("ingredient_val12")) {
        columns.add(new CsvColumnImpl("ingredient_val12", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.57"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val12",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.57"), CsvDataType.STRING));
        // 成分13
      } else if (column.equals("ingredient_name13")) {
        columns.add(new CsvColumnImpl("ingredient_name13", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.58"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name13", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.58"), CsvDataType.STRING));
        // 成分量13
      } else if (column.equals("ingredient_val13")) {
        columns.add(new CsvColumnImpl("ingredient_val13", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.59"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val13",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.59"), CsvDataType.STRING));
        // 成分14
      } else if (column.equals("ingredient_name14")) {
        columns.add(new CsvColumnImpl("ingredient_name14", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.60"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name14", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.60"), CsvDataType.STRING));
        // 成分量14
      } else if (column.equals("ingredient_val14")) {
        columns.add(new CsvColumnImpl("ingredient_val14", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.61"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val14",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.61"), CsvDataType.STRING));
        // 成分15
      } else if (column.equals("ingredient_name15")) {
        columns.add(new CsvColumnImpl("ingredient_name15", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.62"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name15", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.62"), CsvDataType.STRING));
        // 成分量15
      } else if (column.equals("ingredient_val15")) {
        columns.add(new CsvColumnImpl("ingredient_val15", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.63"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val15",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.63"), CsvDataType.STRING));
        // 原材料1
      } else if (column.equals("material1")) {
        columns.add(new CsvColumnImpl("material1", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.64"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material1", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.64"),
            CsvDataType.STRING));
        // 原材料2
      } else if (column.equals("material2")) {
        columns.add(new CsvColumnImpl("material2", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.65"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material2", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.65"),
            CsvDataType.STRING));
        // 原材料3
      } else if (column.equals("material3")) {
        columns.add(new CsvColumnImpl("material3", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.66"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material3", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.66"),
            CsvDataType.STRING));
        // 原材料4
      } else if (column.equals("material4")) {
        columns.add(new CsvColumnImpl("material4", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.67"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material4", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.67"),
            CsvDataType.STRING));
        // 原材料5
      } else if (column.equals("material5")) {
        columns.add(new CsvColumnImpl("material5", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.68"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material5", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.68"),
            CsvDataType.STRING));
        // 原材料6
      } else if (column.equals("material6")) {
        columns.add(new CsvColumnImpl("material6", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.69"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material6", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.69"),
            CsvDataType.STRING));
        // 原材料7
      } else if (column.equals("material7")) {
        columns.add(new CsvColumnImpl("material7", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.70"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material7", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.70"),
            CsvDataType.STRING));
        // 原材料8
      } else if (column.equals("material8")) {
        columns.add(new CsvColumnImpl("material8", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.71"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material8", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.71"),
            CsvDataType.STRING));
        // 原材料9
      } else if (column.equals("material9")) {
        columns.add(new CsvColumnImpl("material9", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.72"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material9", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.72"),
            CsvDataType.STRING));
        // 原材料10
      } else if (column.equals("material10")) {
        columns.add(new CsvColumnImpl("material10", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.73"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material10", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.73"),
            CsvDataType.STRING));
        // 原材料11
      } else if (column.equals("material11")) {
        columns.add(new CsvColumnImpl("material11", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.74"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material11", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.74"),
            CsvDataType.STRING));
        // 原材料12
      } else if (column.equals("material12")) {
        columns.add(new CsvColumnImpl("material12", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.75"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material12", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.75"),
            CsvDataType.STRING));
        // 原材料13
      } else if (column.equals("material13")) {
        columns.add(new CsvColumnImpl("material13", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.76"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material13", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.76"),
            CsvDataType.STRING));
        // 原材料14
      } else if (column.equals("material14")) {
        columns.add(new CsvColumnImpl("material14", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.77"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material14", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.77"),
            CsvDataType.STRING));
        // 原材料15
      } else if (column.equals("material15")) {
        columns.add(new CsvColumnImpl("material15", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.78"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material15", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.78"),
            CsvDataType.STRING));
        // 商品期限管理フラグ 0管理しない/1賞味期限日/2製造日＋保管日数
      } else if (column.equals("shelf_life_flag")) {
        columns.add(new CsvColumnImpl("shelf_life_flag", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.79"),
            CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("shelf_life_flag", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.79"),
            CsvDataType.NUMBER));
        // 保管日数
      } else if (column.equals("shelf_life_days")) {
        columns.add(new CsvColumnImpl("shelf_life_days", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.80"),
            CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("shelf_life_days", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.80"),
            CsvDataType.NUMBER));
        // 入库效期
      } else if (column.equals("in_bound_life_days")) {
        columns.add(new CsvColumnImpl("in_bound_life_days", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.99"),
            CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("in_bound_life_days", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.99"), CsvDataType.NUMBER));
        // 出库效期
      } else if (column.equals("out_bound_life_days")) {
        columns.add(new CsvColumnImpl("out_bound_life_days", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.100"),
            CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("out_bound_life_days", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.100"), CsvDataType.NUMBER));
        // 失效预警
      } else if (column.equals("shelf_life_alert_days")) {
        columns.add(new CsvColumnImpl("shelf_life_alert_days",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.101"), CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("shelf_life_alert_days", Messages
            .getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.101"), CsvDataType.NUMBER));
        // 大物フラグ
      } else if (column.equals("big_flag")) {
        columns.add(new CsvColumnImpl("big_flag", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.82"),
            CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("big_flag", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.82"),
            CsvDataType.NUMBER));
        // 进口商品区分
      } else if (column.equals("import_commodity_type")) {
        columns.add(new CsvColumnImpl("import_commodity_type", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.125"),
            CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("import_commodity_type", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.125"),
            CsvDataType.NUMBER));
        // 清仓商品区分
      } else if (column.equals("clear_commodity_type")) {
        columns.add(new CsvColumnImpl("clear_commodity_type", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.126"),
            CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("clear_commodity_type", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.126"),
            CsvDataType.NUMBER));
        // Asahi商品区分
      } else if (column.equals("reserve_commodity_type1")) {
        columns.add(new CsvColumnImpl("reserve_commodity_type1", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.127"),
            CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("reserve_commodity_type1", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.127"),
            CsvDataType.NUMBER));
        // hot商品区分
      } else if (column.equals("reserve_commodity_type2")) {
        columns.add(new CsvColumnImpl("reserve_commodity_type2", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.128"),
            CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("reserve_commodity_type2", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.128"),
            CsvDataType.NUMBER));
        // 商品展示区分
      } else if (column.equals("reserve_commodity_type3")) {
        columns.add(new CsvColumnImpl("reserve_commodity_type3", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.129"),
            CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("reserve_commodity_type3", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.129"),
            CsvDataType.NUMBER));
        // 品店精选区分
      } else if (column.equals("new_reserve_commodity_type1")) {
        columns.add(new CsvColumnImpl("new_reserve_commodity_type1", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.130"),
            CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("new_reserve_commodity_type1", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.130"),
            CsvDataType.NUMBER));
        // 家电区分
      } else if (column.equals("new_reserve_commodity_type2")) {
        columns.add(new CsvColumnImpl("new_reserve_commodity_type2", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.131"),
            CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("new_reserve_commodity_type2", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.131"),
            CsvDataType.NUMBER));
      } else if (column.equals("set_commodity_flg")) {
        columns.add(new CsvColumnImpl("set_commodity_flg", "套装商品区分",
            CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("set_commodity_flg", "套装商品区分",
            CsvDataType.NUMBER));
      // 2014/04/29 京东WBS对应 ob_卢 add start
      // 京东类目ID
      } else if (column.equals("jd_category_id")) {
        columns.add(new CsvColumnImpl("jd_category_id", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.154"),
            CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("jd_category_id", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.154"),
            CsvDataType.NUMBER));
      // 广告词
      } else if (column.equals("advert_content")) {
        columns.add(new CsvColumnImpl("advert_content", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.152"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("advert_content", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.152"),
            CsvDataType.STRING));
      // 京东同期FLG
      } else if (column.equals("sync_flag_jd")) {
        columns.add(new CsvColumnImpl("sync_flag_jd", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.153"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("sync_flag_jd", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.153"),
            CsvDataType.STRING));
      // 2014/04/29 京东WBS对应 ob_卢 add end
        
        // SKUコード
      } else if (column.equals("sku_code")) {
        columns.add(new CsvColumnImpl("sku_code", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.0"),
            CsvDataType.STRING, false, false, true, null));
        detailColumns.add(new CsvColumnImpl("sku_code", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.0"),
            CsvDataType.STRING, false, false, true, null));
        stockColumns.add(new CsvColumnImpl("sku_code", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.0"),
            CsvDataType.STRING, false, false, true, null));
        // SKU名称
      } else if (column.equals("sku_name")) {
        columns.add(new CsvColumnImpl("sku_name", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.1"),
            CsvDataType.STRING, false, false, true, null));
        detailColumns.add(new CsvColumnImpl("sku_name", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.1"),
            CsvDataType.STRING, false, false, true, null));
        // 定価フラグ0：非定価　1：定価
      } else if (column.equals("fixed_price_flag")) {
        columns.add(new CsvColumnImpl("fixed_price_flag", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.3"),
            CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("fixed_price_flag", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.3"),
            CsvDataType.NUMBER));
        // 希望小売価格
      } else if (column.equals("suggeste_price")) {
        columns.add(new CsvColumnImpl("suggeste_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.4"),
            CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("suggeste_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.4"),
            CsvDataType.BIGDECIMAL));
        // 仕入価格
      } else if (column.equals("purchase_price")) {
        columns.add(new CsvColumnImpl("purchase_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.5"),
            CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("purchase_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.5"),
            CsvDataType.BIGDECIMAL));
        // EC商品単価
      } else if (column.equals("unit_price")) {
        columns.add(new CsvColumnImpl("unit_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.6"),
            CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("unit_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.6"),
            CsvDataType.BIGDECIMAL));
        // EC特別価格
      } else if (column.equals("discount_price")) {
        columns.add(new CsvColumnImpl("discount_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.7"),
            CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("discount_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.7"),
            CsvDataType.BIGDECIMAL));
        // 規格1値のID(=TMALL属性値ID)
      } else if (column.equals("standard1_value_id")) {
        columns.add(new CsvColumnImpl("standard_detail1_id", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.8"),
            CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("standard_detail1_id", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.8"), CsvDataType.STRING));
        // 規格1値の文字列(値のIDなければ、これを利用）
      } else if (column.equals("standard1_value_text")) {
        columns.add(new CsvColumnImpl("standard_detail1_name", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.9"),
            CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("standard_detail1_name", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.9"), CsvDataType.STRING));
        // 規格1値英文
      } else if (column.equals("standard1_value_text_en")) {
        columns.add(new CsvColumnImpl("standard_detail1_name_en", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.99"), CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("standard_detail1_name_en", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.99"), CsvDataType.STRING));
        // 規格1値日文
      } else if (column.equals("standard1_value_text_jp")) {
        columns.add(new CsvColumnImpl("standard_detail1_name_jp", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.100"), CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("standard_detail1_name_jp", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.100"), CsvDataType.STRING));
        // 規格2値のID(=TMALL属性値ID)
      } else if (column.equals("standard2_value_id")) {
        columns.add(new CsvColumnImpl("standard_detail2_id", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.10"),
            CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("standard_detail2_id", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.10"), CsvDataType.STRING));
        // 規格2値の文字列(値のIDなければ、これを利用）
      } else if (column.equals("standard2_value_text")) {
        columns.add(new CsvColumnImpl("standard_detail2_name", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.11"),
            CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("standard_detail2_name", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.11"), CsvDataType.STRING));
        // 規格2値英文
      } else if (column.equals("standard2_value_text_en")) {
        columns.add(new CsvColumnImpl("standard_detail2_name_en", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.101"), CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("standard_detail2_name_en", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.101"), CsvDataType.STRING));
        // 規格2値日文
      } else if (column.equals("standard2_value_text_jp")) {
        columns.add(new CsvColumnImpl("standard_detail2_name_jp", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.102"), CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("standard_detail2_name_jp", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.102"), CsvDataType.STRING));
        // 商品重量(単位はKG)、未設定の場合、商品HEADの重量を利用。
      } else if (column.equals("weight")) {
        columns.add(new CsvColumnImpl("weight", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.12"),
            CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("weight", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.12"),
            CsvDataType.BIGDECIMAL));
        // 容量
      } else if (column.equals("volume")) {
        columns.add(new CsvColumnImpl("volume", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.13"),
            CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("volume", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.13"),
            CsvDataType.BIGDECIMAL));
        // 容量単位
      } else if (column.equals("volume_unit")) {
        columns.add(new CsvColumnImpl("volume_unit", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.14"),
            CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("volume_unit", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.14"),
            CsvDataType.STRING));
        // 取扱いフラグ(EC)
      } else if (column.equals("use_flg")) {
        columns.add(new CsvColumnImpl("use_flg", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.15"),
            CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("use_flg", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.15"),
            CsvDataType.NUMBER));
        // 最小仕入数
      } else if (column.equals("minimum_order")) {
        columns.add(new CsvColumnImpl("minimum_order", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.16"),
            CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("minimum_order", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.16"),
            CsvDataType.NUMBER));
        // 最大仕入数
      } else if (column.equals("maximum_order")) {
        columns.add(new CsvColumnImpl("maximum_order", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.17"),
            CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("maximum_order", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.17"),
            CsvDataType.NUMBER));
        // 最小単位の仕入数
      } else if (column.equals("order_multiple")) {
        columns.add(new CsvColumnImpl("order_multiple", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.18"),
            CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("order_multiple", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.18"),
            CsvDataType.NUMBER));
        // 在庫警告日数
      } else if (column.equals("stock_warning")) {
        columns.add(new CsvColumnImpl("stock_warning", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.19"),
            CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("stock_warning", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.19"),
            CsvDataType.NUMBER));
        // TMALLの商品単価
      } else if (column.equals("tmall_unit_price")) {
        columns.add(new CsvColumnImpl("tmall_unit_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.21"),
            CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("tmall_unit_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.21"), CsvDataType.BIGDECIMAL));
        // TMALLの特別価格
      } else if (column.equals("tmall_discount_price")) {
        columns.add(new CsvColumnImpl("tmall_discount_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.22"),
            CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("tmall_discount_price", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.22"), CsvDataType.BIGDECIMAL));
        // 下限売価
      } else if (column.equals("min_price")) {
        columns.add(new CsvColumnImpl("min_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.23"),
            CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("min_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.23"),
            CsvDataType.BIGDECIMAL));
        // 取扱いフラグ(TMALL)
      } else if (column.equals("tmall_use_flg")) {
        columns.add(new CsvColumnImpl("tmall_use_flg", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.31"),
            CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("tmall_use_flg", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.31"),
            CsvDataType.NUMBER));
        // 入数
      } else if (column.equals("inner_quantity")) {
        columns.add(new CsvColumnImpl("inner_quantity", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.27"),
            CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("inner_quantity", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.27"),
            CsvDataType.NUMBER));
        // 入数単位
      } else if (column.equals("inner_quantity_unit")) {
        columns.add(new CsvColumnImpl("inner_quantity_unit", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.28"),
            CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("inner_quantity_unit", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.28"), CsvDataType.STRING));
        // WEB表示単価単位入数
      } else if (column.equals("inner_unit_for_price")) {
        columns.add(new CsvColumnImpl("inner_unit_for_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.29"),
            CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("inner_unit_for_price", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.29"), CsvDataType.NUMBER));
        // WEB表示単価単位容量
      } else if (column.equals("volume_unit_for_price")) {
        columns.add(new CsvColumnImpl("volume_unit_for_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.30"),
            CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("volume_unit_for_price", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.30"), CsvDataType.BIGDECIMAL));
        // 税率区分
      } else if (column.equals("tax_class")) {
        columns.add(new CsvColumnImpl("tax_class", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.32"),
            CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("tax_class", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.32"),
            CsvDataType.STRING));
        //箱规
      } else if (column.equals("unit_box")){
        columns.add(new CsvColumnImpl("unit_box", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.103"),
            CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("unit_box", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.103"),
            CsvDataType.NUMBER));
      // 2014/04/29 京东WBS对应 ob_卢 add start
      } else if (column.equals("jd_use_flg")){
        columns.add(new CsvColumnImpl("jd_use_flg", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.104"),
            CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("jd_use_flg", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.104"),
            CsvDataType.NUMBER));
      // 2014/04/29 京东WBS对应 ob_卢 add end
        // 平均计算成本
      } else if (column.equals("average_cost")) {
          columns.add(new CsvColumnImpl("average_cost", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.105"),
              CsvDataType.BIGDECIMAL));
          detailColumns.add(new CsvColumnImpl("average_cost",
              Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.105"), CsvDataType.BIGDECIMAL));
        // 在庫閾値
      }else if (column.equals("stock_threshold")) {
        columns.add(new CsvColumnImpl("stock_threshold", Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.7"),
            CsvDataType.NUMBER));
        stockColumns.add(new CsvColumnImpl("stock_threshold",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.7"), CsvDataType.NUMBER));
        // 総在庫
      } else if (column.equals("stock_total")) {
        columns.add(new CsvColumnImpl("stock_total", Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.8"),
            CsvDataType.NUMBER));
        stockColumns.add(new CsvColumnImpl("stock_total", Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.8"),
            CsvDataType.NUMBER));
        // 在庫リーバランスフラグ
      } else if (column.equals("share_recalc_flag")) {
        columns.add(new CsvColumnImpl("share_recalc_flag", Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.11"),
            CsvDataType.NUMBER));
        stockColumns.add(new CsvColumnImpl("share_recalc_flag", Messages
            .getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.11"), CsvDataType.NUMBER));
        // EC在庫割合(0-100)
      } else if (column.equals("share_ratio")) {
        columns.add(new CsvColumnImpl("share_ratio", Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.12"),
            CsvDataType.NUMBER));
        stockColumns.add(new CsvColumnImpl("share_ratio", Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.12"),
            CsvDataType.NUMBER));
        // 品店カテゴリID１
      } else if (column.equals("category1_code")) {
        columns.add(new CsvColumnImpl("category1_code", Messages.getCsvKey("service.data.csv.CategoryCommodityImportCsvSchema.4"),
            CsvDataType.STRING, false, false, true, null));
        categoryCommodityColumns.add(new CsvColumnImpl("category1_code", Messages
            .getCsvKey("service.data.csv.CategoryCommodityImportCsvSchema.4"), CsvDataType.STRING, false, false, true, null));
        // 品店カテゴリID2
      } else if (column.equals("category2_code")) {
        columns.add(new CsvColumnImpl("category2_code", Messages.getCsvKey("service.data.csv.CategoryCommodityImportCsvSchema.5"),
            CsvDataType.STRING, false, false, true, null));
        categoryCommodityColumns.add(new CsvColumnImpl("category2_code", Messages
            .getCsvKey("service.data.csv.CategoryCommodityImportCsvSchema.5"), CsvDataType.STRING, false, false, true, null));
        // 品店カテゴリID3
      } else if (column.equals("category3_code")) {
        columns.add(new CsvColumnImpl("category3_code", Messages.getCsvKey("service.data.csv.CategoryCommodityImportCsvSchema.6"),
            CsvDataType.STRING, false, false, true, null));
        categoryCommodityColumns.add(new CsvColumnImpl("category3_code", Messages
            .getCsvKey("service.data.csv.CategoryCommodityImportCsvSchema.6"), CsvDataType.STRING, false, false, true, null));
        // 品店カテゴリID4
      } else if (column.equals("category4_code")) {
        columns.add(new CsvColumnImpl("category4_code", Messages.getCsvKey("service.data.csv.CategoryCommodityImportCsvSchema.7"),
            CsvDataType.STRING, false, false, true, null));
        categoryCommodityColumns.add(new CsvColumnImpl("category4_code", Messages
            .getCsvKey("service.data.csv.CategoryCommodityImportCsvSchema.7"), CsvDataType.STRING, false, false, true, null));
        // 品店カテゴリID5
      } else if (column.equals("category5_code")) {
        columns.add(new CsvColumnImpl("category5_code", Messages.getCsvKey("service.data.csv.CategoryCommodityImportCsvSchema.8"),
            CsvDataType.STRING, false, false, true, null));
        categoryCommodityColumns.add(new CsvColumnImpl("category5_code", Messages
            .getCsvKey("service.data.csv.CategoryCommodityImportCsvSchema.8"), CsvDataType.STRING, false, false, true, null));
        // 品店カテゴリID１属性１
      } else if (column.equals("category1_attribute1")) {
        columns.add(new CsvColumnImpl("category1_attribute1", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.4"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category1_attribute1", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.4"), CsvDataType.STRING));
        // 品店カテゴリID１属性１
      } else if (column.equals("category1_attribute1_en")) {
        columns.add(new CsvColumnImpl("category1_attribute1_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.24"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category1_attribute1_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.24"), CsvDataType.STRING));
        // 品店カテゴリID１属性１
      } else if (column.equals("category1_attribute1_jp")) {
        columns.add(new CsvColumnImpl("category1_attribute1_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.39"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category1_attribute1_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.39"), CsvDataType.STRING));
        // 品店カテゴリID１属性２
      } else if (column.equals("category1_attribute2")) {
        columns.add(new CsvColumnImpl("category1_attribute2", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.5"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category1_attribute2", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.5"), CsvDataType.STRING));
        // 品店カテゴリID１属性２
      } else if (column.equals("category1_attribute2_en")) {
        columns.add(new CsvColumnImpl("category1_attribute2_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.25"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category1_attribute2_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.25"), CsvDataType.STRING));
        // 品店カテゴリID１属性２
      } else if (column.equals("category1_attribute2_jp")) {
        columns.add(new CsvColumnImpl("category1_attribute2_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.40"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category1_attribute2_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.40"), CsvDataType.STRING));
        // 品店カテゴリID１属性３
      } else if (column.equals("category1_attribute3")) {
        columns.add(new CsvColumnImpl("category1_attribute3", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.6"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category1_attribute3", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.6"), CsvDataType.STRING));
        // 品店カテゴリID１属性３
      } else if (column.equals("category1_attribute3_en")) {
        columns.add(new CsvColumnImpl("category1_attribute3_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.26"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category1_attribute3_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.26"), CsvDataType.STRING));
        // 品店カテゴリID１属性３
      } else if (column.equals("category1_attribute3_jp")) {
        columns.add(new CsvColumnImpl("category1_attribute3_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.41"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category1_attribute3_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.41"), CsvDataType.STRING));
        // 品店カテゴリID２属性１
      } else if (column.equals("category2_attribute1")) {
        columns.add(new CsvColumnImpl("category2_attribute1", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.7"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category2_attribute1", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.7"), CsvDataType.STRING));
        // 品店カテゴリID２属性１
      } else if (column.equals("category2_attribute1_en")) {
        columns.add(new CsvColumnImpl("category2_attribute1_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.27"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category2_attribute1_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.27"), CsvDataType.STRING));
        // 品店カテゴリID２属性１
      } else if (column.equals("category2_attribute1_jp")) {
        columns.add(new CsvColumnImpl("category2_attribute1_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.42"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category2_attribute1_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.42"), CsvDataType.STRING));
        // 品店カテゴリID２属性２
      } else if (column.equals("category2_attribute2")) {
        columns.add(new CsvColumnImpl("category2_attribute2", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.8"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category2_attribute2", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.8"), CsvDataType.STRING));
        // 品店カテゴリID２属性２
      } else if (column.equals("category2_attribute2_en")) {
        columns.add(new CsvColumnImpl("category2_attribute2_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.28"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category2_attribute2_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.28"), CsvDataType.STRING));
        // 品店カテゴリID２属性２
      } else if (column.equals("category2_attribute2_jp")) {
        columns.add(new CsvColumnImpl("category2_attribute2_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.43"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category2_attribute2_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.43"), CsvDataType.STRING));
        // 品店カテゴリID２属性３
      } else if (column.equals("category2_attribute3")) {
        columns.add(new CsvColumnImpl("category2_attribute3", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.9"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category2_attribute3", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.9"), CsvDataType.STRING));
        // 品店カテゴリID２属性３
      } else if (column.equals("category2_attribute3_en")) {
        columns.add(new CsvColumnImpl("category2_attribute3_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.29"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category2_attribute3_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.29"), CsvDataType.STRING));
        // 品店カテゴリID２属性３
      } else if (column.equals("category2_attribute3_jp")) {
        columns.add(new CsvColumnImpl("category2_attribute3_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.44"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category2_attribute3_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.44"), CsvDataType.STRING));
        // 品店カテゴリID３属性１
      } else if (column.equals("category3_attribute1")) {
        columns.add(new CsvColumnImpl("category3_attribute1", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.10"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category3_attribute1", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.10"), CsvDataType.STRING));
        // 品店カテゴリID３属性１
      } else if (column.equals("category3_attribute1_en")) {
        columns.add(new CsvColumnImpl("category3_attribute1_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.30"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category3_attribute1_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.30"), CsvDataType.STRING));
        // 品店カテゴリID３属性１
      } else if (column.equals("category3_attribute1_jp")) {
        columns.add(new CsvColumnImpl("category3_attribute1_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.45"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category3_attribute1_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.45"), CsvDataType.STRING));
        // 品店カテゴリID３属性２
      } else if (column.equals("category3_attribute2")) {
        columns.add(new CsvColumnImpl("category3_attribute2", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.11"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category3_attribute2", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.11"), CsvDataType.STRING));
        // 品店カテゴリID３属性２
      } else if (column.equals("category3_attribute2_en")) {
        columns.add(new CsvColumnImpl("category3_attribute2_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.31"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category3_attribute2_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.31"), CsvDataType.STRING));
        // 品店カテゴリID３属性２
      } else if (column.equals("category3_attribute2_jp")) {
        columns.add(new CsvColumnImpl("category3_attribute2_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.46"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category3_attribute2_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.46"), CsvDataType.STRING));
        // 品店カテゴリID３属性３
      } else if (column.equals("category3_attribute3")) {
        columns.add(new CsvColumnImpl("category3_attribute3", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.12"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category3_attribute3", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.12"), CsvDataType.STRING));
        // 品店カテゴリID３属性３
      } else if (column.equals("category3_attribute3_en")) {
        columns.add(new CsvColumnImpl("category3_attribute3_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.32"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category3_attribute3_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.32"), CsvDataType.STRING));
        // 品店カテゴリID３属性３
      } else if (column.equals("category3_attribute3_jp")) {
        columns.add(new CsvColumnImpl("category3_attribute3_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.47"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category3_attribute3_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.47"), CsvDataType.STRING));
        // 品店カテゴリID４属性１
      } else if (column.equals("category4_attribute1")) {
        columns.add(new CsvColumnImpl("category4_attribute1", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.13"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category4_attribute1", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.13"), CsvDataType.STRING));
        // 品店カテゴリID４属性１
      } else if (column.equals("category4_attribute1_en")) {
        columns.add(new CsvColumnImpl("category4_attribute1_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.33"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category4_attribute1_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.33"), CsvDataType.STRING));
        // 品店カテゴリID４属性１
      } else if (column.equals("category4_attribute1_jp")) {
        columns.add(new CsvColumnImpl("category4_attribute1_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.48"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category4_attribute1_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.48"), CsvDataType.STRING));
        // 品店カテゴリID４属性２
      } else if (column.equals("category4_attribute2")) {
        columns.add(new CsvColumnImpl("category4_attribute2", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.14"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category4_attribute2", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.14"), CsvDataType.STRING));
        // 品店カテゴリID４属性２
      } else if (column.equals("category4_attribute2_en")) {
        columns.add(new CsvColumnImpl("category4_attribute2_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.34"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category4_attribute2_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.34"), CsvDataType.STRING));
        // 品店カテゴリID４属性２
      } else if (column.equals("category4_attribute2_jp")) {
        columns.add(new CsvColumnImpl("category4_attribute2_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.49"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category4_attribute2_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.49"), CsvDataType.STRING));
        // 品店カテゴリID４属性３
      } else if (column.equals("category4_attribute3")) {
        columns.add(new CsvColumnImpl("category4_attribute3", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.15"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category4_attribute3", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.15"), CsvDataType.STRING));
        // 品店カテゴリID４属性３
      } else if (column.equals("category4_attribute3_en")) {
        columns.add(new CsvColumnImpl("category4_attribute3_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.35"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category4_attribute3_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.35"), CsvDataType.STRING));
        // 品店カテゴリID４属性３
      } else if (column.equals("category4_attribute3_jp")) {
        columns.add(new CsvColumnImpl("category4_attribute3_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.50"), CsvDataType.STRING));
        valueColumns.add(new CsvColumnImpl("category4_attribute3_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.50"), CsvDataType.STRING));
        // 品店カテゴリID５属性１
      } else if (column.equals("category5_attribute1")) {
        columns.add(new CsvColumnImpl("category5_attribute1", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.16"), CsvDataType.NUMBER));
        valueColumns.add(new CsvColumnImpl("category5_attribute1", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.16"), CsvDataType.NUMBER));
        // 品店カテゴリID５属性１
      } else if (column.equals("category5_attribute1_en")) {
        columns.add(new CsvColumnImpl("category5_attribute1_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.36"), CsvDataType.NUMBER));
        valueColumns.add(new CsvColumnImpl("category5_attribute1_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.36"), CsvDataType.NUMBER));
        // 品店カテゴリID５属性１
      } else if (column.equals("category5_attribute1_jp")) {
        columns.add(new CsvColumnImpl("category5_attribute1_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.51"), CsvDataType.NUMBER));
        valueColumns.add(new CsvColumnImpl("category5_attribute1_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.51"), CsvDataType.NUMBER));
        // 品店カテゴリID５属性２
      } else if (column.equals("category5_attribute2")) {
        columns.add(new CsvColumnImpl("category5_attribute2", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.17"), CsvDataType.NUMBER));
        valueColumns.add(new CsvColumnImpl("category5_attribute2", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.17"), CsvDataType.NUMBER));
        // 品店カテゴリID５属性２
      } else if (column.equals("category5_attribute2_en")) {
        columns.add(new CsvColumnImpl("category5_attribute2_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.37"), CsvDataType.NUMBER));
        valueColumns.add(new CsvColumnImpl("category5_attribute2_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.37"), CsvDataType.NUMBER));
        // 品店カテゴリID５属性２
      } else if (column.equals("category5_attribute2_jp")) {
        columns.add(new CsvColumnImpl("category5_attribute2_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.52"), CsvDataType.NUMBER));
        valueColumns.add(new CsvColumnImpl("category5_attribute2_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.52"), CsvDataType.NUMBER));
        // 品店カテゴリID５属性３
      } else if (column.equals("category5_attribute3")) {
        columns.add(new CsvColumnImpl("category5_attribute3", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.18"), CsvDataType.NUMBER));
        valueColumns.add(new CsvColumnImpl("category5_attribute3", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.18"), CsvDataType.NUMBER));
        // 品店カテゴリID５属性３
      } else if (column.equals("category5_attribute3_en")) {
        columns.add(new CsvColumnImpl("category5_attribute3_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.38"), CsvDataType.NUMBER));
        valueColumns.add(new CsvColumnImpl("category5_attribute3_en", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.38"), CsvDataType.NUMBER));
        // 品店カテゴリID５属性３
      } else if (column.equals("category5_attribute3_jp")) {
        columns.add(new CsvColumnImpl("category5_attribute3_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.53"), CsvDataType.NUMBER));
        valueColumns.add(new CsvColumnImpl("category5_attribute3_jp", Messages
            .getCsvKey("service.data.csv.CategoryAttributeValueDataCsvSchema.53"), CsvDataType.NUMBER));
        // 在庫品区分1:在庫品、2:受注発注品
      } else if (column.equals("on_stock_flag")) {
        columns.add(new CsvColumnImpl("on_stock_flag", Messages.getCsvKey("service.data.csv.CCommodityExtCsvSchema.2"),
            CsvDataType.NUMBER));
        extColumns.add(new CsvColumnImpl("on_stock_flag", Messages.getCsvKey("service.data.csv.CCommodityExtCsvSchema.2"),
            CsvDataType.NUMBER));

      } else if (column.equals("food_security_prd_license_no")) {
        columns.add(new CsvColumnImpl("food_security_prd_license_no", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.3"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_prd_license_no", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.3"), CsvDataType.STRING));
      } else if (column.equals("food_security_design_code")) {
        columns.add(new CsvColumnImpl("food_security_design_code", Messages.getCsvKey("service.data.csv.CCommodityExtCsvSchema.4"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_design_code", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.4"), CsvDataType.STRING));
      } else if (column.equals("food_security_factory")) {
        columns.add(new CsvColumnImpl("food_security_factory", Messages.getCsvKey("service.data.csv.CCommodityExtCsvSchema.5"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_factory", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.5"), CsvDataType.STRING));
      } else if (column.equals("food_security_factory_site")) {
        columns.add(new CsvColumnImpl("food_security_factory_site",
            Messages.getCsvKey("service.data.csv.CCommodityExtCsvSchema.6"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_factory_site", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.6"), CsvDataType.STRING));
      } else if (column.equals("food_security_contact")) {
        columns.add(new CsvColumnImpl("food_security_contact", Messages.getCsvKey("service.data.csv.CCommodityExtCsvSchema.7"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_contact", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.7"), CsvDataType.STRING));
      } else if (column.equals("food_security_mix")) {
        columns.add(new CsvColumnImpl("food_security_mix", Messages.getCsvKey("service.data.csv.CCommodityExtCsvSchema.8"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_mix", Messages.getCsvKey("service.data.csv.CCommodityExtCsvSchema.8"),
            CsvDataType.STRING));
      } else if (column.equals("food_security_plan_storage")) {
        columns.add(new CsvColumnImpl("food_security_plan_storage",
            Messages.getCsvKey("service.data.csv.CCommodityExtCsvSchema.9"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_plan_storage", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.9"), CsvDataType.STRING));
      } else if (column.equals("food_security_period")) {
        columns.add(new CsvColumnImpl("food_security_period", Messages.getCsvKey("service.data.csv.CCommodityExtCsvSchema.10"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_period", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.10"), CsvDataType.STRING));
      } else if (column.equals("food_security_food_additive")) {
        columns.add(new CsvColumnImpl("food_security_food_additive", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.11"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_food_additive", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.11"), CsvDataType.STRING));
      } else if (column.equals("food_security_supplier")) {
        columns.add(new CsvColumnImpl("food_security_supplier", Messages.getCsvKey("service.data.csv.CCommodityExtCsvSchema.12"),
            CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_supplier", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.12"), CsvDataType.STRING));
      } else if (column.equals("food_security_product_date_start")) {
        columns.add(new CsvColumnImpl("food_security_product_date_start", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.13"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_product_date_start", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.13"), CsvDataType.STRING));
      } else if (column.equals("food_security_product_date_end")) {
        columns.add(new CsvColumnImpl("food_security_product_date_end", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.14"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_product_date_end", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.14"), CsvDataType.STRING));
      } else if (column.equals("food_security_stock_date_start")) {
        columns.add(new CsvColumnImpl("food_security_stock_date_start", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.15"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_stock_date_start", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.15"), CsvDataType.STRING));
      } else if (column.equals("food_security_stock_date_end")) {
        columns.add(new CsvColumnImpl("food_security_stock_date_end", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.16"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_stock_date_end", Messages
            .getCsvKey("service.data.csv.CCommodityExtCsvSchema.16"), CsvDataType.STRING));
      }
    }
    getSchema().setColumns(columns);
    try {
      // 商品明细表新建Query
      insertDetailStatement = createPreparedStatement(getInsertDetailQuery());

      // 商品基本表新建Query
      insertHeaderStatement = createPreparedStatement(getInsertHeaderQuery());

      // 在库表新建Query
      insertStockStatement = createPreparedStatement(getInsertStockQuery());

      // カテゴリ陳列商品表新建Query
      insertCategoryCommodityStatement = createPreparedStatement(getInsertCategoryCommodityQuery());

      // カテゴリ属性値表新建Query
      insertCategoryAttributeValueStatement = createPreparedStatement(getInsertCategoryAttributeValueQuery());

      // 商品拡張情報表新建Query
      insertExtStatement = createPreparedStatement(getInsertCCommodityExtQuery());

      // 商品基本表更新
      updateHeaderStatement = createPreparedStatement(getUpdateHeaderQuery());

      // カテゴリ属性値表更新Query
      updateCategoryAttributeValueStatement = createPreparedStatement(getUpdateCategoryAttributeValueQuery());

    } catch (Exception e) {
      throw new DataAccessException(e);
    }
    return true;
  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      header = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CCommodityHeaderImport.class);
      detail = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CCommodityDetail.class);
      stock = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), Stock.class);
      categoryCommodity = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CategoryCommodityImport.class);
      categoryAttributeValue = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CategoryAttributeValueImport.class);
      ccommodityExt = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CCommodityExt.class);

      if (StringUtil.hasValue(header.getCommodityCode())) {
        header.setCommodityCode(header.getCommodityCode().replace("'", ""));
        categoryCommodity.setCommodityCode(categoryCommodity.getCommodityCode().replace("'", ""));
        categoryAttributeValue.setCommodityCode(categoryAttributeValue.getCommodityCode().replace("'", ""));
        ccommodityExt.setCommodityCode(ccommodityExt.getCommodityCode().replace("'", ""));
        header.setCommodityType(0L);
        header.setTmallMjsFlg(0L);
      }
      if (StringUtil.hasValue(detail.getSkuCode())) {
        detail.setSkuCode(detail.getSkuCode().replace("'", ""));
        stock.setSkuCode(stock.getSkuCode().replace("'", ""));
        if (!StringUtil.isNullOrEmpty(detail.getCommodityCode())) {
          detail.setCommodityCode(detail.getCommodityCode().replace("'", ""));
        }
        if (!StringUtil.isNullOrEmpty(stock.getCommodityCode())) {
          stock.setCommodityCode(stock.getCommodityCode().replace("'", ""));
        }
      }

      if (header.getSaleStartDatetimeStr() != null
          && !DateUtil.isCorrect(header.getSaleStartDatetimeStr().replace("'", "").replaceAll("-", "/"))) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.DATETIMEERR, Messages
                .getString("service.data.csv.CCommodityHeaderCsvSchema.11"))));
        return summary;
      }
      if (header.getSaleEndDatetimeStr() != null
          && !DateUtil.isCorrect(header.getSaleEndDatetimeStr().replace("'", "").replaceAll("-", "/"))) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.DATETIMEERR, Messages
                .getString("service.data.csv.CCommodityHeaderCsvSchema.12"))));
        return summary;
      }
      if (header.getDiscountPriceStartDatetimeStr() != null
          && !DateUtil.isCorrect(header.getDiscountPriceStartDatetimeStr().replace("'", "").replaceAll("-", "/"))) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.DATETIMEERR, Messages
                .getString("service.data.csv.CCommodityHeaderCsvSchema.13"))));
        return summary;
      }
      if (header.getDiscountPriceEndDatetimeStr() != null
          && !DateUtil.isCorrect(header.getDiscountPriceEndDatetimeStr().replace("'", "").replaceAll("-", "/"))) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.DATETIMEERR, Messages
                .getString("service.data.csv.CCommodityHeaderCsvSchema.14"))));
        return summary;
      }
      String returnFlgCust;
      String returnFlgSupp;
      String changeFlgSupp;
      // 顾客退货
      if (StringUtil.isNullOrEmpty(header.getReturnFlgCust())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CCommodityHeaderCsvSchema.83"))));
        return summary;
      } else {
        if (!header.getReturnFlgCust().equals("0") && !header.getReturnFlgCust().equals("1")) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.OUT_OF_RANGE, Messages
                  .getString("service.data.csv.CCommodityHeaderCsvSchema.83"), "0", "1")));
          return summary;
        } else {
          returnFlgCust = header.getReturnFlgCust();
        }
      }
      // 供货商退货
      if (StringUtil.isNullOrEmpty(header.getReturnFlgSupp())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CCommodityHeaderCsvSchema.84"))));
        return summary;
      } else {
        if (!header.getReturnFlgSupp().equals("0") && !header.getReturnFlgSupp().equals("1")) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.OUT_OF_RANGE, Messages
                  .getString("service.data.csv.CCommodityHeaderCsvSchema.84"), "0", "1")));
          return summary;
        } else {
          returnFlgSupp = header.getReturnFlgSupp();
        }
      }
      // 供货商换货
      if (StringUtil.isNullOrEmpty(header.getChangeFlgSupp())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CCommodityHeaderCsvSchema.85"))));
        return summary;
      } else {
        if (!header.getReturnFlgSupp().equals("0") && !header.getReturnFlgSupp().equals("1")) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.OUT_OF_RANGE, Messages
                  .getString("service.data.csv.CCommodityHeaderCsvSchema.85"), "0", "1")));
          return summary;
        } else {
          changeFlgSupp = header.getChangeFlgSupp();
        }
      }
      // 返品不可フラグ
      String returnFlg =  changeFlgSupp + returnFlgSupp + returnFlgCust;

      header.setReturnFlg(Integer.valueOf(returnFlg, 2).longValue());
      // 20120305 os013 add start
      // 商品名称特殊字符转换：（单引号变换为 ‘）、（双引号变换为　“）、（百分号变换为　％）、（ 逗号变换为 　，）
      if (!StringUtil.isNullOrEmpty(header.getCommodityName())) {
        // 重新赋值
        header.setCommodityName(StringUtil.parse(header.getCommodityName()));
      }

      // 商品英文名称特殊字符转换：（单引号变换为 ‘）、（双引号变换为　“）、（百分号变换为　％）、（ 逗号变换为 　，）
      if (!StringUtil.isNullOrEmpty(header.getCommodityNameEn())) {
        // 重新赋值
        header.setCommodityNameEn(StringUtil.parse(header.getCommodityNameEn()));
      }

      // 商品日文名称特殊字符转换：（单引号变换为 ‘）、（双引号变换为　“）、（百分号变换为　％）、（ 逗号变换为 　，）
      if (!StringUtil.isNullOrEmpty(header.getCommodityNameJp())) {
        // 重新赋值
        header.setCommodityNameJp(StringUtil.parse(header.getCommodityNameJp()));
      }

      // SKU名称特殊字符转换：（单引号变换为 ‘）、（双引号变换为　“）、（百分号变换为　％）、（ 逗号变换为 　，）
      if (!StringUtil.isNullOrEmpty(detail.getSkuName())) {
        // 重新赋值
        detail.setSkuName(StringUtil.parse(detail.getSkuName()));
      }
      // 20120305 os013 add end
      // 商品编号必须输入
      if (header.getCommodityCode() == null) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CCommodityHeaderCsvSchema.1"))));
        return summary;
      }

      // 代表SKUコード必须输入
      if (header.getRepresentSkuCode() == null) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CommodityHeaderImportDataSource.63"))));
        return summary;
      }

      // 代表SKU単価必须输入
      if (header.getRepresentSkuUnitPrice() == null) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CommodityHeaderImportDataSource.64"))));
        return summary;
      }

      // SKU编号必须输入
      if (detail.getSkuCode() == null) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CCommodityDetailCsvSchema.0"))));
        return summary;
      }
      // 商品编号在商品基本表中是否存在
      existsHeader = exists(selectHeaderStatement, header.getCommodityCode());

      // SKU编号在商品明细表中是否存在
      existsDetail = exists(selectDetailStatement, detail.getSkuCode());

      // SKU编号在在库表中是否存在
      existsStock = exists(selectStockStatement, stock.getSkuCode());

      // 商品编号在商品拡張情報表中是否存在
      existsExt = exists(selectExtStatement, ccommodityExt.getCommodityCode());
      // 存在报提示
      if (existsDetail || existsStock) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SKU_CODE_EXIST, detail.getSkuCode())));
        return summary;
      }

      // 登录商品属性验证 add by yyq start 2012-11-29
      SimpleQuery standard1IdQuery = new SimpleQuery("SELECT STANDARD1_ID FROM C_COMMODITY_HEADER WHERE COMMODITY_CODE = ? ");
      standard1IdQuery.setParameters(header.getCommodityCode());
      Object obj1St = executeScalar(standard1IdQuery);
      String standard1IdStr = "";
      if (obj1St != null) {
        standard1IdStr = executeScalar(standard1IdQuery).toString();
      }

      SimpleQuery standard2IdQuery = new SimpleQuery("SELECT STANDARD2_ID FROM C_COMMODITY_HEADER WHERE COMMODITY_CODE = ? ");
      standard2IdQuery.setParameters(header.getCommodityCode());
      Object obj2St = executeScalar(standard2IdQuery);
      String standard2IdStr = "";
      if (obj2St != null) {
        standard2IdStr = executeScalar(standard2IdQuery).toString();
      }

      if (!StringUtil.isNullOrEmpty(standard1IdStr)) {
        if (!standard1IdStr.equals(header.getStandard1Id())) {
          summary.getErrors().add(new ValidationResult(null, null, "登录的SKU属性1ID与所属商品的属性ID不匹配"));
          return summary;
        }
      }
      if (!StringUtil.isNullOrEmpty(standard2IdStr)) {
        if (!standard2IdStr.equals(header.getStandard2Id())) {
          summary.getErrors().add(new ValidationResult(null, null, "登录的SKU属性2ID与所属商品的属性ID不匹配"));
          return summary;
        }
      }
      if (!StringUtil.isNullOrEmpty(standard1IdStr) && !StringUtil.isNullOrEmpty(standard2IdStr)) {
        SimpleQuery countQuery = new SimpleQuery(
            "SELECT COUNT(*) FROM C_COMMODITY_DETAIL WHERE STANDARD_DETAIL1_ID = ? AND STANDARD_DETAIL2_ID = ? AND COMMODITY_CODE =?");
        countQuery.setParameters(detail.getStandardDetail1Id(), detail.getStandardDetail2Id(), header.getCommodityCode());
        Object obj = executeScalar(countQuery);
        if (obj != null) {
          if (!StringUtil.isNullOrEmpty(obj.toString())) {
            summary.getErrors().add(new ValidationResult(null, null, "登录sku的属性在所属商品中已经存在"));
            return summary;
          }
        }
      }
      if (!StringUtil.isNullOrEmpty(standard1IdStr) && StringUtil.isNullOrEmpty(standard2IdStr)) {
        SimpleQuery countQuery = new SimpleQuery(
            "SELECT COUNT(*) FROM C_COMMODITY_DETAIL WHERE STANDARD_DETAIL1_ID = ? AND COMMODITY_CODE =?");
        countQuery.setParameters(detail.getStandardDetail1Id(), header.getCommodityCode());
        Object obj = executeScalar(countQuery);
        if (obj != null) {
          if (!StringUtil.isNullOrEmpty(obj.toString()) && !obj.toString().equals("0")) {
            summary.getErrors().add(new ValidationResult(null, null, "登录sku的属性在所属商品中已经存在"));
            return summary;
          }
        }
      }
      if (StringUtil.isNullOrEmpty(standard1IdStr) && !StringUtil.isNullOrEmpty(standard2IdStr)) {
        SimpleQuery countQuery = new SimpleQuery(
            "SELECT COUNT(*) FROM C_COMMODITY_DETAIL WHERE  STANDARD_DETAIL2_ID = ? AND COMMODITY_CODE =?");
        countQuery.setParameters(detail.getStandardDetail2Id(), header.getCommodityCode());
        Object obj = executeScalar(countQuery);
        if (obj != null) {
          if (!StringUtil.isNullOrEmpty(obj.toString()) && !obj.toString().equals("0")) {
            summary.getErrors().add(new ValidationResult(null, null, "登录sku的属性在所属商品中已经存在"));
            return summary;
          }
        }
      }
      // 登录商品属性验证 add by yyq end 2012-11-29

      // 商品已经存在则不进行SKUコードcheck
      if (!existsHeader) {
        // 商品基本表的代表SKUコード与商品详细表的SKUコード必须一样
        if (!header.getRepresentSkuCode().equals(detail.getSkuCode())) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.REPRESENT_SKU_CODE_AND_SKU_CODE, header.getCommodityCode())));
          return summary;
        }
      }

      // 判断商品説明2长度必须大于5
      if (StringUtil.isNullOrEmpty(header.getCommodityDescription2()) || header.getCommodityDescription2().length() < 5) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.COMMODITY_DESCRIPTION2_LENGTH_REQUEST_GREATER, header
                .getCommodityCode())));
      }

      // 20120312 os013 add start
      // EC商品単価
      if (detail.getUnitPrice() == null) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CommodityDetailImportDataSource.40"))));
        return summary;
      }
      // TMALL商品単価
      if (detail.getTmallUnitPrice() == null) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CommodityDetailImportDataSource.42"))));
        return summary;
      }
      // 20120312 os013 add end
      // 商品重量判断
      if (detail.getWeight() == null || detail.getWeight().doubleValue() < 0) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CommodityDetailImportDataSource.36"))));
        return summary;
      }
      // 取扱いフラグ(EC)是否输入
      if (detail.getUseFlg() == null) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CommodityDetailImportDataSource.30"))));
        return summary;
      }
      if (detail.getUseFlg() != 0 && detail.getUseFlg() != 1) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.OUT_OF_RANGE, Messages
                .getString("service.data.csv.CommodityDetailImportDataSource.30"), "0", "1")));
        return summary;
      }

      // 取扱いフラグ(TMALL)是否输入
      if (detail.getTmallUseFlg() == null) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CommodityDetailImportDataSource.31"))));
        return summary;
      }
      if (detail.getTmallUseFlg() != 0 && detail.getTmallUseFlg() != 1) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.OUT_OF_RANGE, Messages
                .getString("service.data.csv.CommodityDetailImportDataSource.31"), "0", "1")));
        return summary;
      }
      // 2012/03/06 追加商品minimum_order 最小订货数 add by os011 start
      if (NumUtil.isNull(detail.getMinimumOrder()) || detail.getMinimumOrder() < 0L) {
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityDetailImportDataSource.37")));
        return summary;
      }
      // maximum_order 最大订货数
      if (NumUtil.isNull(detail.getMaximumOrder()) || detail.getMaximumOrder() < 0L) {
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityDetailImportDataSource.38")));
        return summary;
      }
      // order_multiple 最小订货单位数
      if (NumUtil.isNull(detail.getOrderMultiple()) || detail.getOrderMultiple() < 0L) {
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityDetailImportDataSource.39")));
        return summary;
      }
      // 2012/03/06 追加商品minimum_order 最小订货数 add by os011 end
      // 20120313 os013 add start
      // 在库警告数
      if (NumUtil.isNull(detail.getStockWarning())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CCommodityInitialStageImportDataSource.4"))));
        return summary;
      }
      // 进货负责人代码
      if (StringUtil.isNullOrEmpty(header.getBuyerCode())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CCommodityInitialStageImportDataSource.5"))));
        return summary;
      }
      // 供应商编号
      if (StringUtil.isNullOrEmpty(header.getSupplierCode())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CCommodityInitialStageImportDataSource.6"))));
        return summary;
      }
      // 20120313 os013 add end
      // 20120319 os013 add start
      // 供应商到货天数必须输入
      if (header.getLeadTime() == null) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CommodityHeaderImportDataSource.66"))));
        return summary;
      }
      if(header.getImportCommodityType() != null){
        if(header.getImportCommodityType() != 1 && header.getImportCommodityType() != 2 &&  header.getImportCommodityType() != 9){
          summary.getErrors().add(new ValidationResult(null, null, "进口商品区分(只能为1:全进口、2:海外品品牌、9:普通国产)"));
        }
      }
      if(header.getImportCommodityType() == null){
        header.setImportCommodityType(Long.valueOf("9"));
      }
      if(header.getClearCommodityType() != null){
        if(header.getClearCommodityType() != 1 &&  header.getClearCommodityType() != 9){
          summary.getErrors().add(new ValidationResult(null, null, "清仓商品区分(只能为1:清仓商品、9:普通商品)"));
        }
      }
      if(header.getClearCommodityType() == null ){
        header.setClearCommodityType(Long.valueOf("9"));
      }
      if(header.getReserveCommodityType1() != null){
        if(header.getReserveCommodityType1() != 1 &&  header.getReserveCommodityType1() != 9){
          summary.getErrors().add(new ValidationResult(null, null, "Asahi商品区分(只能为1:是、9:默认不是)"));
        }
      }
      if(header.getReserveCommodityType1() == null ){
        header.setReserveCommodityType1(Long.valueOf("9"));
      }
      if(header.getReserveCommodityType2() != null){
        if(header.getReserveCommodityType2() != 1 &&  header.getReserveCommodityType2() != 9){
          summary.getErrors().add(new ValidationResult(null, null, "hot商品区分(只能为1:是、9:默认不是)"));
        }
      }
      if(header.getReserveCommodityType2() == null ){
          header.setReserveCommodityType2(Long.valueOf("9"));
      }
      if (header.getReserveCommodityType3() != null) {
        if (header.getReserveCommodityType3() != 1 && header.getReserveCommodityType3() != 9) {
          summary.getErrors().add(new ValidationResult(null, null, "商品展示区分(只能为1:是、9:默认不是)"));
        }
      }
      if (header.getReserveCommodityType3() == null) {
        header.setReserveCommodityType3(Long.valueOf("9"));
      }
      if (header.getNewReserveCommodityType1() != null) {
        if (header.getNewReserveCommodityType1() != 1 && header.getNewReserveCommodityType1() != 9) {
          summary.getErrors().add(new ValidationResult(null, null, "品店精选区分(只能为1:是、9:默认不是)"));
        }
      }
      if (header.getNewReserveCommodityType1() == null) {
        header.setNewReserveCommodityType1(Long.valueOf("9"));
      }
      if (header.getNewReserveCommodityType2() != null) {
        if (header.getNewReserveCommodityType2() != 1 && header.getNewReserveCommodityType2() != 9) {
          summary.getErrors().add(new ValidationResult(null, null, "家电区分(只能为1:是、9:默认不是)"));
        }
      }
      if (header.getNewReserveCommodityType2() == null) {
        header.setNewReserveCommodityType2(Long.valueOf("9"));
      }
     
      
      // 警告区分必须输入,且为0或1或2
      if (StringUtil.isNullOrEmpty(header.getWarningFlag())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CommodityHeaderImportDataSource.68"))));
        return summary;
      } else {
        if (!header.getWarningFlag().equals("0") && !header.getWarningFlag().equals("1") && !header.getWarningFlag().equals("2")) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.OUT_OF_RANGE, Messages
                  .getString("service.data.csv.CommodityHeaderImportDataSource.68"), "0", "2")));
          return summary;
        }
      }
      // 20120319 os013 add end
      // 判断产地必选项是否输入
      // update by yyq 20130409 start
      if (StringUtil.isNullOrEmpty(header.getOriginalPlace())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CommodityHeaderImportDataSource.69"))));
        return summary;
      }else{
        Query query = new SimpleQuery("SELECT * FROM ORIGINAL_PLACE WHERE ORIGINAL_CODE = ?", header.getOriginalPlace());
        OriginalPlace op = DatabaseUtil.loadAsBean(query, OriginalPlace.class);
        if(op != null){
          header.setOriginalCode(header.getOriginalPlace());
          header.setOriginalPlace(op.getOriginalPlaceNameCn());
          header.setOriginalPlaceEn(op.getOriginalPlaceNameEn());
          header.setOriginalPlaceJp(op.getOriginalPlaceNameJp());
        }else{
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                  .getString("service.data.csv.CommodityHeaderImportDataSource.69"))));
          return summary;
        }
      }
      // update by yyq 20130409 end
      // del by yyq 20130409 start
      // if (header.getOriginalPlaceEn() == null) {
      // summary.getErrors().add(
      // new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM,
      // Messages
      // .getString("service.data.csv.CommodityHeaderImportDataSource.70"))));
      // return summary;
      // }
      // if (header.getOriginalPlaceJp() == null) {
      // summary.getErrors().add(
      // new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM,
      // Messages
      // .getString("service.data.csv.CommodityHeaderImportDataSource.71"))));
      // return summary;
      // }
      // del by yyq 20130409 end
      if (header.getCommodityNameEn() == null) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CommodityHeaderImportDataSource.71"))));
        return summary;
      }
      if (header.getCommodityNameJp() == null) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CommodityHeaderImportDataSource.71"))));
        return summary;
      }
      // 判断商品基本表必须项是否输入，没输入则设为默认值
      setHeader(summary);

      // 判断商品详细表必须项是否输入，没输入则设为默认值
      setDetail();

      // 判断商品拡張情報表必须项是否输入，没输入则设为默认值
      setCCommodityExt();
      DatabaseUtil.setUserStatus(ccommodityExt, getCondition().getLoginInfo());
      summary.getErrors().addAll(BeanValidator.validate(ccommodityExt).getErrors());

      // 判断在库表必须项是否输入，没输入则设为默认值
      setStock();

      // 判断品店カテゴリ是否至少输入一个
      if (categoryCommodityColumns.size() == 0) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_CODE_REQUEST_ITEM)));
        return summary;
      }
      int Count = 0;
      for (int i = 0; i < categoryCommodityColumns.size(); i++) {
        String categoryCode = BeanUtil.invokeGetter(categoryCommodity, StringUtil.toCamelFormat(categoryCommodityColumns.get(i)
            .getPhysicalName()));
        if (StringUtil.isNullOrEmpty(categoryCode)) {
          Count++;
        }

      }
      if (Count == categoryCommodityColumns.size()) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_CODE_REQUEST_ITEM)));
        return summary;
      }

      // 判断カテゴリ陳列商品表必须项是否输入，没输入则设为默认值
      setCategoryCommodity();
      // 判断カテゴリ属性値表必须项是否输入，没输入则设为默认值
      setCategoryAttributeValue();

    } catch (CsvImportException e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }

    DatabaseUtil.setUserStatus(header, getCondition().getLoginInfo());
    DatabaseUtil.setUserStatus(detail, getCondition().getLoginInfo());
    DatabaseUtil.setUserStatus(stock, getCondition().getLoginInfo());
    DatabaseUtil.setUserStatus(categoryCommodity, getCondition().getLoginInfo());
    DatabaseUtil.setUserStatus(categoryAttributeValue, getCondition().getLoginInfo());

    summary.getErrors().addAll(BeanValidator.validate(header).getErrors());
    summary.getErrors().addAll(BeanValidator.validate(detail).getErrors());
    summary.getErrors().addAll(BeanValidator.validate(stock).getErrors());

    // 判断TMALLのカテゴリID在 淘宝类目表中是否存在
    if (!existsHeader) {
      if (header.getTmallCategoryId() == null) {
        summary.getErrors().add(
            new ValidationResult(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.61"), null, Message.get(
                CsvMessage.NOT_EXIST, "")));
      } else {
        SimpleQuery CategoryCodeExistsQuery = new SimpleQuery(
            "SELECT CATEGORY_CODE FROM TMALL_CATEGORY WHERE IS_PARENT = 0 AND CATEGORY_CODE = ?");
        CategoryCodeExistsQuery.setParameters(header.getTmallCategoryId().toString());
        Object object = executeScalar(CategoryCodeExistsQuery);
        if (object == null) {
          summary.getErrors().add(
              new ValidationResult(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.61"), null, Message.get(
                  CsvMessage.NOT_EXIST, header.getTmallCategoryId().toString())));
        }
      }
    }
    // 2014/04/29 京东WBS对应 ob_卢 add start
    // 取扱いフラグ(JD)是否输入
    if (detail.getJdUseFlg() != null) {
      if (!JdUseFlg.DISABLED.longValue().equals(detail.getJdUseFlg()) 
          && !JdUseFlg.ENABLED.longValue().equals(detail.getJdUseFlg())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.OUT_OF_RANGE, Messages
                .getString("service.data.csv.CommodityDetailImportDataSource.51"), "0", "1")));
        return summary;
      }
    }
    
    // 判断JDのカテゴリID在 京东类目表中是否存在
    if (!existsHeader) {
      if (header.getJdCategoryId() != null) {
        SimpleQuery CategoryCodeExistsQuery = new SimpleQuery(
        "SELECT CATEGORY_ID FROM JD_CATEGORY WHERE IS_PARENT = 0 AND CATEGORY_ID = ?");
        CategoryCodeExistsQuery.setParameters(header.getJdCategoryId().toString());
        Object object = executeScalar(CategoryCodeExistsQuery);
        if (object == null) {
          summary.getErrors().add(
              new ValidationResult(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.72"), null, Message.get(
                  CsvMessage.NOT_EXIST, header.getJdCategoryId().toString())));
        }
      }
    }
    // 2014/04/29 京东WBS对应 ob_卢 add end
    // SKU名称最大48或者47（第24，25字节为一个汉字时）字节
    if (!StringUtil.isNullOrEmpty(detail.getSkuName())) {

      try {
        // 判断第24个字节是否为汉字
        String skuName = StringUtil.subStringByByte(detail.getSkuName(), 24);
        int skuNameSize = skuName.getBytes("GBK").length;
        // 如果是24的话，为汉字
        if (skuNameSize == 24) {
          if (detail.getSkuName().getBytes("GBK").length > 48) {
            summary.getErrors().add(
                new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityDetailImportDataSource.34")));
          }
        } else {
          if (detail.getSkuName().getBytes("GBK").length > 47) {
            summary.getErrors().add(
                new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityDetailImportDataSource.33")));
          }
        }
      } catch (UnsupportedEncodingException e) {
        // 字节转化失败
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityDetailImportDataSource.35")));
      }
    }
    // 判断品牌编号是否存在
    if (!existsHeader) {
      SimpleQuery brandExistsQuery = new SimpleQuery("SELECT BRAND_CODE FROM BRAND WHERE  BRAND_CODE = ?");
      brandExistsQuery.setParameters(header.getBrandCode());
      Object object = executeScalar(brandExistsQuery);
      if (object == null) {
        summary.getErrors().add(
            new ValidationResult(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.62"), null, Message.get(
                CsvMessage.NOT_EXIST, header.getBrandCode())));
      }
    }
    // 判断税率区分存在,等于false时不存在，报错
    boolean existsTaxClass = false;
    List<NameValue> taxClassList = DIContainer.getTaxClassValue().getTaxClass();
    for (int i = 0; i < taxClassList.size(); i++) {
      if (taxClassList.get(i).getValue().equals(detail.getTaxClass())) {
        existsTaxClass = true;
      }
    }
    if (!existsTaxClass) {
      summary.getErrors().add(
          new ValidationResult(Messages.getString("service.data.csv.CommodityDetailImportDataSource.32"), null, Message.get(
              CsvMessage.NOT_EXIST, detail.getTaxClass())));
    }

    // EC特价、TMALL特价、特价时间必须同时存在，同时不存在
    if (header.getDiscountPriceStartDatetime() != null || header.getDiscountPriceEndDatetime() != null) {
      if (detail.getDiscountPrice() == null) {
        summary.getErrors().add(
            new ValidationResult(Messages.getString("service.data.csv.CCommodityInitialStageImportDataSource.2"), null, Message
                .get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityInitialStageImportDataSource.0"))));
      }
      if (detail.getTmallDiscountPrice() == null) {
        summary.getErrors().add(
            new ValidationResult(Messages.getString("service.data.csv.CCommodityInitialStageImportDataSource.2"), null, Message
                .get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityInitialStageImportDataSource.1"))));
      }
    } else {
      if (detail.getDiscountPrice() != null) {
        summary.getErrors().add(
            new ValidationResult(Messages.getString("service.data.csv.CCommodityInitialStageImportDataSource.3"), null, Message
                .get(CsvMessage.NO_REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityInitialStageImportDataSource.0"))));
      }
      if (detail.getTmallDiscountPrice() != null) {
        summary.getErrors().add(
            new ValidationResult(Messages.getString("service.data.csv.CCommodityInitialStageImportDataSource.3"), null, Message
                .get(CsvMessage.NO_REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityInitialStageImportDataSource.1"))));
      }
    }

    // 10.1.1 10019 追加 ここから
    checkMinusNumber(summary);
    // 10.1.1 10019 追加 ここまで

    // 成对检查
    pairCheck(summary);

    List<String> errorMessageList = new ArrayList<String>();

    // 10.1.1 10006 追加 ここから
    // 各期間の日付範囲チェック
    checkDateRange(header, errorMessageList);
    // 10.1.1 10006 追加 ここまで

    // 各期間の開始/終了日時の順序チェック
    checkTermsRules(header, errorMessageList);

    checkSalesTermAndDiscountRules(header, errorMessageList);

    for (String error : errorMessageList) {
      summary.getErrors().add(new ValidationResult(null, null, error));
    }
    // 商品期限管理フラグは2の場合保管日数必須
    checkShelfLife(summary);

    // 定価フラグcheck
    checkFixedPrice(summary);

    // 最小单价check
    checkMinPrice(summary);

    // 入り数check
    checkInner(summary);

    // 容量check
    checkVolume(summary);

    // EC在庫割合check
    checkShareRatio(summary);

    if (summary.hasError()) {
      return summary;
    }

    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());
    ValidationSummary summary = new ValidationSummary();
    try {
      // 商品基本表新建
      int updHeaderCount = executeHeaderUpdate(existsHeader);
      if (updHeaderCount != 1) {
        throw new CsvImportException();
      }

      // 商品明细表新建
      int updDetailCount = executeDetailUpdate(existsDetail);
      if (updDetailCount != 1) {
        throw new CsvImportException();
      }
      // 在库表新建
      int updStockCount = executeStockUpdate(existsStock);
      if (updStockCount != 1) {
        throw new CsvImportException();
      }

      // カテゴリ陳列商品新建

      // 品店カテゴリID１新建
      if (!StringUtil.isNullOrEmpty(categoryCommodity.getCategory1Code())) {

        categoryCommodity.setCategoryCode(categoryCommodity.getCategory1Code());

        categoryAttributeValue.setCategoryCode(categoryCommodity.getCategory1Code());

        // 商品编号,品店カテゴリ编号在カテゴリ陳列商品表中是否存在
        existsCategory = existsCategory(categoryCommodity.getCategoryCode());

        // 品店カテゴリID1検索用カテゴリパス
        categoryCommodity.setCategorySearchPath(categorySearchPath1);
        // 检查カテゴリ陳列商品表字段
        summary.getErrors().addAll(BeanValidator.validate(categoryCommodity).getErrors());
        if (summary.hasError()) {
          throw new CsvImportException();
        }
        // 商品编号,品店カテゴリ编号在カテゴリ陳列商品表中存在则不做处理，不存在新建
        if (!existsCategory) {
          int updCategoryCount = executeCategoryCommodityUpdate(existsCategory);
          if (updCategoryCount != 1) {
            throw new CsvImportException();
          }
        }
        // 品店カテゴリID１属性１新建、修改、删除
        if (StringUtil.isNullOrEmpty(categoryAttributeValue.getCategory1Attribute1())) {
          // 品店カテゴリID１属性１为空，删除属性值表品店カテゴリID属性１
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(0L);
          executeDeleteCategoryAttributeValue();
        } else {
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(0L);
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValue(header.getOriginalPlace());
          // カテゴリ属性英文値
          categoryAttributeValue.setCategoryAttributeValueEn(header.getOriginalPlaceEn());
          // カテゴリ属性日文値
          categoryAttributeValue.setCategoryAttributeValueJp(header.getOriginalPlaceJp());

          // 商品编号,品店カテゴリ编号,属性编号在カテゴリ属性値表中是否存在
          existsValue = existsValue(categoryAttributeValue.getCategoryCode(), categoryAttributeValue.getCategoryAttributeNo());

          // 检查カテゴリ属性値表字段
          summary.getErrors().addAll(BeanValidator.validate(categoryAttributeValue).getErrors());
          if (summary.hasError()) {
            throw new CsvImportException();
          }
          int updValueCount = executeCategoryAttributeValueUpdate(existsValue);
          if (updValueCount != 1) {
            throw new CsvImportException();
          }
        }
        // 品店カテゴリID１属性２新建、修改、删除
        if (StringUtil.isNullOrEmpty(categoryAttributeValue.getCategory1Attribute2())) {
          // 品店カテゴリID１属性2为空，删除属性值表品店カテゴリID属性2
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(1L);
          executeDeleteCategoryAttributeValue();
        } else {
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(1L);
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValue(categoryAttributeValue.getCategory1Attribute2());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueEn(categoryAttributeValue.getCategory1Attribute2En());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueJp(categoryAttributeValue.getCategory1Attribute2Jp());

          // 商品编号,品店カテゴリ编号,属性编号在カテゴリ属性値表中是否存在
          existsValue = existsValue(categoryAttributeValue.getCategoryCode(), categoryAttributeValue.getCategoryAttributeNo());

          // 检查カテゴリ属性値表字段
          summary.getErrors().addAll(BeanValidator.validate(categoryAttributeValue).getErrors());
          if (summary.hasError()) {
            throw new CsvImportException();
          }
          int updValueCount = executeCategoryAttributeValueUpdate(existsValue);
          if (updValueCount != 1) {
            throw new CsvImportException();
          }
        }
        // 品店カテゴリID１属性３新建、修改、删除
        if (StringUtil.isNullOrEmpty(categoryAttributeValue.getCategory1Attribute3())) {
          // 品店カテゴリID１属性3为空，删除属性值表品店カテゴリID属性3
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(2L);
          executeDeleteCategoryAttributeValue();
        } else {
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(2L);
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValue(categoryAttributeValue.getCategory1Attribute3());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueEn(categoryAttributeValue.getCategory1Attribute3En());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueJp(categoryAttributeValue.getCategory1Attribute3Jp());

          // 商品编号,品店カテゴリ编号,属性编号在カテゴリ属性値表中是否存在
          existsValue = existsValue(categoryAttributeValue.getCategoryCode(), categoryAttributeValue.getCategoryAttributeNo());

          // 检查カテゴリ属性値表字段
          summary.getErrors().addAll(BeanValidator.validate(categoryAttributeValue).getErrors());
          if (summary.hasError()) {
            throw new CsvImportException();
          }
          int updValueCount = executeCategoryAttributeValueUpdate(existsValue);
          if (updValueCount != 1) {
            throw new CsvImportException();
          }
        }
      }

      // 品店カテゴリID２新建
      if (!StringUtil.isNullOrEmpty(categoryCommodity.getCategory2Code())) {

        categoryCommodity.setCategoryCode(categoryCommodity.getCategory2Code());

        categoryAttributeValue.setCategoryCode(categoryCommodity.getCategory2Code());

        // 商品编号,品店カテゴリ编号在カテゴリ陳列商品表中是否存在
        existsCategory = existsCategory(categoryCommodity.getCategoryCode());

        // 品店カテゴリID2検索用カテゴリパス
        categoryCommodity.setCategorySearchPath(categorySearchPath2);

        // 检查カテゴリ陳列商品表字段
        summary.getErrors().addAll(BeanValidator.validate(categoryCommodity).getErrors());
        if (summary.hasError()) {
          throw new CsvImportException();
        }
        // 商品编号,品店カテゴリ编号在カテゴリ陳列商品表中存在则不做处理，不存在新建
        if (!existsCategory) {
          int updCategoryCount = executeCategoryCommodityUpdate(existsCategory);
          if (updCategoryCount != 1) {
            throw new CsvImportException();
          }
        }
        // 品店カテゴリID２属性１新建、修改、删除
        if (StringUtil.isNullOrEmpty(categoryAttributeValue.getCategory2Attribute1())) {
          // 品店カテゴリID2属性１为空，删除属性值表品店カテゴリID属性１
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(0L);
          executeDeleteCategoryAttributeValue();
        } else {
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(0L);
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValue(header.getOriginalPlace());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueEn(header.getOriginalPlaceEn());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueJp(header.getOriginalPlaceJp());

          // 商品编号,品店カテゴリ编号,属性编号在カテゴリ属性値表中是否存在
          existsValue = existsValue(categoryAttributeValue.getCategoryCode(), categoryAttributeValue.getCategoryAttributeNo());

          // 检查カテゴリ属性値表字段
          summary.getErrors().addAll(BeanValidator.validate(categoryAttributeValue).getErrors());
          if (summary.hasError()) {
            throw new CsvImportException();
          }
          int updValueCount = executeCategoryAttributeValueUpdate(existsValue);
          if (updValueCount != 1) {
            throw new CsvImportException();
          }
        }
        // 品店カテゴリID２属性２新建、修改、删除
        if (StringUtil.isNullOrEmpty(categoryAttributeValue.getCategory2Attribute2())) {
          // 品店カテゴリID2属性2为空，删除属性值表品店カテゴリID属性2
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(1L);
          executeDeleteCategoryAttributeValue();
        } else {
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(1L);
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValue(categoryAttributeValue.getCategory2Attribute2());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueEn(categoryAttributeValue.getCategory2Attribute2En());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueJp(categoryAttributeValue.getCategory2Attribute2Jp());
          // 商品编号,品店カテゴリ编号,属性编号在カテゴリ属性値表中是否存在
          existsValue = existsValue(categoryAttributeValue.getCategoryCode(), categoryAttributeValue.getCategoryAttributeNo());

          // 检查カテゴリ属性値表字段
          summary.getErrors().addAll(BeanValidator.validate(categoryAttributeValue).getErrors());
          if (summary.hasError()) {
            throw new CsvImportException();
          }
          int updValueCount = executeCategoryAttributeValueUpdate(existsValue);
          if (updValueCount != 1) {
            throw new CsvImportException();
          }
        }
        // 品店カテゴリID２属性３新建、修改、删除
        if (StringUtil.isNullOrEmpty(categoryAttributeValue.getCategory2Attribute3())) {
          // 品店カテゴリID2属性3为空，删除属性值表品店カテゴリID属性3
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(2L);
          executeDeleteCategoryAttributeValue();
        } else {
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(2L);
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValue(categoryAttributeValue.getCategory2Attribute3());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueEn(categoryAttributeValue.getCategory2Attribute3En());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueJp(categoryAttributeValue.getCategory2Attribute3Jp());
          // 商品编号,品店カテゴリ编号,属性编号在カテゴリ属性値表中是否存在
          existsValue = existsValue(categoryAttributeValue.getCategoryCode(), categoryAttributeValue.getCategoryAttributeNo());

          // 检查カテゴリ属性値表字段
          summary.getErrors().addAll(BeanValidator.validate(categoryAttributeValue).getErrors());
          if (summary.hasError()) {
            throw new CsvImportException();
          }
          int updValueCount = executeCategoryAttributeValueUpdate(existsValue);
          if (updValueCount != 1) {
            throw new CsvImportException();
          }
        }
      }

      // 品店カテゴリID３新建
      if (!StringUtil.isNullOrEmpty(categoryCommodity.getCategory3Code())) {

        categoryCommodity.setCategoryCode(categoryCommodity.getCategory3Code());

        categoryAttributeValue.setCategoryCode(categoryCommodity.getCategory3Code());

        // 商品编号,品店カテゴリ编号在カテゴリ陳列商品表中是否存在
        existsCategory = existsCategory(categoryCommodity.getCategoryCode());

        // 品店カテゴリID3検索用カテゴリパス
        categoryCommodity.setCategorySearchPath(categorySearchPath3);

        // 检查カテゴリ陳列商品表字段
        summary.getErrors().addAll(BeanValidator.validate(categoryCommodity).getErrors());
        if (summary.hasError()) {
          throw new CsvImportException();
        }
        // 商品编号,品店カテゴリ编号在カテゴリ陳列商品表中存在则不做处理，不存在新建
        if (!existsCategory) {
          int updCategoryCount = executeCategoryCommodityUpdate(existsCategory);
          if (updCategoryCount != 1) {
            throw new CsvImportException();
          }
        }

        // 品店カテゴリID３属性１新建、修改、删除
        if (StringUtil.isNullOrEmpty(categoryAttributeValue.getCategory3Attribute1())) {
          // 品店カテゴリID3属性１为空，删除属性值表品店カテゴリID属性１
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(0L);
          executeDeleteCategoryAttributeValue();
        } else {
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(0L);
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValue(header.getOriginalPlace());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueEn(header.getOriginalPlaceEn());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueJp(header.getOriginalPlaceJp());
          // 商品编号,品店カテゴリ编号,属性编号在カテゴリ属性値表中是否存在
          existsValue = existsValue(categoryAttributeValue.getCategoryCode(), categoryAttributeValue.getCategoryAttributeNo());

          // 检查カテゴリ属性値表字段
          summary.getErrors().addAll(BeanValidator.validate(categoryAttributeValue).getErrors());
          if (summary.hasError()) {
            throw new CsvImportException();
          }
          int updValueCount = executeCategoryAttributeValueUpdate(existsValue);
          if (updValueCount != 1) {
            throw new CsvImportException();
          }
        }
        // 品店カテゴリID３属性２新建、修改、删除
        if (StringUtil.isNullOrEmpty(categoryAttributeValue.getCategory3Attribute2())) {
          // 品店カテゴリID3属性2为空，删除属性值表品店カテゴリID属性2
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(2L);
          executeDeleteCategoryAttributeValue();
        } else {
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(1L);
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValue(categoryAttributeValue.getCategory3Attribute2());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueEn(categoryAttributeValue.getCategory3Attribute2En());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueJp(categoryAttributeValue.getCategory3Attribute2Jp());
          // 商品编号,品店カテゴリ编号,属性编号在カテゴリ属性値表中是否存在
          existsValue = existsValue(categoryAttributeValue.getCategoryCode(), categoryAttributeValue.getCategoryAttributeNo());
          // 检查カテゴリ属性値表字段
          summary.getErrors().addAll(BeanValidator.validate(categoryAttributeValue).getErrors());
          if (summary.hasError()) {
            throw new CsvImportException();
          }

          int updValueCount = executeCategoryAttributeValueUpdate(existsValue);
          if (updValueCount != 1) {
            throw new CsvImportException();
          }
        }
        // 品店カテゴリID３属性３新建、修改、删除
        if (StringUtil.isNullOrEmpty(categoryAttributeValue.getCategory3Attribute3())) {
          // 品店カテゴリID3属性3为空，删除属性值表品店カテゴリID属性3
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(2L);
          executeDeleteCategoryAttributeValue();
        } else {
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(2L);
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValue(categoryAttributeValue.getCategory3Attribute3());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueEn(categoryAttributeValue.getCategory3Attribute3En());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueJp(categoryAttributeValue.getCategory3Attribute3Jp());
          // 商品编号,品店カテゴリ编号,属性编号在カテゴリ属性値表中是否存在
          existsValue = existsValue(categoryAttributeValue.getCategoryCode(), categoryAttributeValue.getCategoryAttributeNo());
          // 检查カテゴリ属性値表字段
          summary.getErrors().addAll(BeanValidator.validate(categoryAttributeValue).getErrors());
          if (summary.hasError()) {
            throw new CsvImportException();
          }
          int updValueCount = executeCategoryAttributeValueUpdate(existsValue);
          if (updValueCount != 1) {
            throw new CsvImportException();
          }
        }
      }

      // 品店カテゴリID４新建
      if (!StringUtil.isNullOrEmpty(categoryCommodity.getCategory4Code())) {

        categoryCommodity.setCategoryCode(categoryCommodity.getCategory4Code());

        categoryAttributeValue.setCategoryCode(categoryCommodity.getCategory4Code());

        // 商品编号,品店カテゴリ编号在カテゴリ陳列商品表中是否存在
        existsCategory = existsCategory(categoryCommodity.getCategoryCode());

        // 品店カテゴリID4検索用カテゴリパス
        categoryCommodity.setCategorySearchPath(categorySearchPath4);

        // 检查カテゴリ陳列商品表字段
        summary.getErrors().addAll(BeanValidator.validate(categoryCommodity).getErrors());
        if (summary.hasError()) {
          throw new CsvImportException();
        }
        // 商品编号,品店カテゴリ编号在カテゴリ陳列商品表中存在则不做处理，不存在新建
        if (!existsCategory) {
          int updCategoryCount = executeCategoryCommodityUpdate(existsCategory);
          if (updCategoryCount != 1) {
            throw new CsvImportException();
          }
        }
        // 品店カテゴリID４属性１新建、修改、删除
        if (StringUtil.isNullOrEmpty(categoryAttributeValue.getCategory4Attribute1())) {
          // 品店カテゴリID4属性１为空，删除属性值表品店カテゴリID属性１
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(0L);
          executeDeleteCategoryAttributeValue();
        } else {
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(0L);
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValue(header.getOriginalPlace());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueEn(header.getOriginalPlaceEn());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueJp(header.getOriginalPlaceJp());
          // 商品编号,品店カテゴリ编号,属性编号在カテゴリ属性値表中是否存在
          existsValue = existsValue(categoryAttributeValue.getCategoryCode(), categoryAttributeValue.getCategoryAttributeNo());

          // 检查カテゴリ属性値表字段
          summary.getErrors().addAll(BeanValidator.validate(categoryAttributeValue).getErrors());
          if (summary.hasError()) {
            throw new CsvImportException();
          }
          int updValueCount = executeCategoryAttributeValueUpdate(existsValue);
          if (updValueCount != 1) {
            throw new CsvImportException();
          }
        }
        // 品店カテゴリID４属性２新建、修改、删除
        if (StringUtil.isNullOrEmpty(categoryAttributeValue.getCategory4Attribute2())) {
          // 品店カテゴリID4属性2为空，删除属性值表品店カテゴリID属性2
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(1L);
          executeDeleteCategoryAttributeValue();
        } else {
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(1L);
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValue(categoryAttributeValue.getCategory4Attribute2());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueEn(categoryAttributeValue.getCategory4Attribute2En());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueJp(categoryAttributeValue.getCategory4Attribute2Jp());

          // 商品编号,品店カテゴリ编号,属性编号在カテゴリ属性値表中是否存在
          existsValue = existsValue(categoryAttributeValue.getCategoryCode(), categoryAttributeValue.getCategoryAttributeNo());

          // 检查カテゴリ属性値表字段
          summary.getErrors().addAll(BeanValidator.validate(categoryAttributeValue).getErrors());
          if (summary.hasError()) {
            throw new CsvImportException();
          }
          int updValueCount = executeCategoryAttributeValueUpdate(existsValue);
          if (updValueCount != 1) {
            throw new CsvImportException();
          }
        }
        // 品店カテゴリID４属性３新建、修改、删除
        if (StringUtil.isNullOrEmpty(categoryAttributeValue.getCategory4Attribute3())) {
          // 品店カテゴリID4属性3为空，删除属性值表品店カテゴリID属性3
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(2L);
          executeDeleteCategoryAttributeValue();
        } else {
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(2L);
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValue(categoryAttributeValue.getCategory4Attribute3());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueEn(categoryAttributeValue.getCategory4Attribute3En());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueJp(categoryAttributeValue.getCategory4Attribute3Jp());

          // 商品编号,品店カテゴリ编号,属性编号在カテゴリ属性値表中是否存在
          existsValue = existsValue(categoryAttributeValue.getCategoryCode(), categoryAttributeValue.getCategoryAttributeNo());

          // 检查カテゴリ属性値表字段
          summary.getErrors().addAll(BeanValidator.validate(categoryAttributeValue).getErrors());
          if (summary.hasError()) {
            throw new CsvImportException();
          }
          int updValueCount = executeCategoryAttributeValueUpdate(existsValue);
          if (updValueCount != 1) {
            throw new CsvImportException();
          }
        }
      }

      // 品店カテゴリID５新建
      if (!StringUtil.isNullOrEmpty(categoryCommodity.getCategory5Code())) {

        categoryCommodity.setCategoryCode(categoryCommodity.getCategory5Code());

        categoryAttributeValue.setCategoryCode(categoryCommodity.getCategory5Code());

        // 商品编号,品店カテゴリ编号在カテゴリ陳列商品表中是否存在
        existsCategory = existsCategory(categoryCommodity.getCategoryCode());

        // 品店カテゴリID5検索用カテゴリパス
        categoryCommodity.setCategorySearchPath(categorySearchPath5);

        // 检查カテゴリ陳列商品表字段
        summary.getErrors().addAll(BeanValidator.validate(categoryCommodity).getErrors());
        if (summary.hasError()) {
          throw new CsvImportException();
        }
        // 商品编号,品店カテゴリ编号在カテゴリ陳列商品表中存在则不做处理，不存在新建
        if (!existsCategory) {
          int updCategoryCount = executeCategoryCommodityUpdate(existsCategory);
          if (updCategoryCount != 1) {
            throw new CsvImportException();
          }
        }
        // 品店カテゴリID５属性１新建、修改、删除
        if (StringUtil.isNullOrEmpty(categoryAttributeValue.getCategory5Attribute1())) {
          // 品店カテゴリID5属性１为空，删除属性值表品店カテゴリID属性１
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(0L);
          executeDeleteCategoryAttributeValue();
        } else {
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(0L);
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValue(header.getOriginalPlace());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueEn(header.getOriginalPlaceEn());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueJp(header.getOriginalPlaceJp());

          // 商品编号,品店カテゴリ编号,属性编号在カテゴリ属性値表中是否存在
          existsValue = existsValue(categoryAttributeValue.getCategoryCode(), categoryAttributeValue.getCategoryAttributeNo());

          // 检查カテゴリ属性値表字段
          summary.getErrors().addAll(BeanValidator.validate(categoryAttributeValue).getErrors());
          if (summary.hasError()) {
            throw new CsvImportException();
          }
          int updValueCount = executeCategoryAttributeValueUpdate(existsValue);
          if (updValueCount != 1) {
            throw new CsvImportException();
          }
        }
        // 品店カテゴリID５属性２新建、修改、删除
        if (StringUtil.isNullOrEmpty(categoryAttributeValue.getCategory5Attribute2())) {
          // 品店カテゴリID5属性2为空，删除属性值表品店カテゴリID属性2
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(1L);
          executeDeleteCategoryAttributeValue();
        } else {
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(1L);
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValue(categoryAttributeValue.getCategory5Attribute2());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueEn(categoryAttributeValue.getCategory5Attribute2En());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueJp(categoryAttributeValue.getCategory5Attribute2Jp());
          // 商品编号,品店カテゴリ编号,属性编号在カテゴリ属性値表中是否存在
          existsValue = existsValue(categoryAttributeValue.getCategoryCode(), categoryAttributeValue.getCategoryAttributeNo());

          // 检查カテゴリ属性値表字段
          summary.getErrors().addAll(BeanValidator.validate(categoryAttributeValue).getErrors());
          if (summary.hasError()) {
            throw new CsvImportException();
          }
          int updValueCount = executeCategoryAttributeValueUpdate(existsValue);
          if (updValueCount != 1) {
            throw new CsvImportException();
          }
        }
        // 品店カテゴリID５属性３新建、修改、删除
        if (StringUtil.isNullOrEmpty(categoryAttributeValue.getCategory5Attribute3())) {
          // 品店カテゴリID5属性3为空，删除属性值表品店カテゴリID属性3
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(2L);
          executeDeleteCategoryAttributeValue();
        } else {
          // カテゴリ属性番号
          categoryAttributeValue.setCategoryAttributeNo(2L);
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValue(categoryAttributeValue.getCategory5Attribute3());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueEn(categoryAttributeValue.getCategory5Attribute3En());
          // カテゴリ属性値
          categoryAttributeValue.setCategoryAttributeValueJp(categoryAttributeValue.getCategory5Attribute3Jp());

          // 商品编号,品店カテゴリ编号,属性编号在カテゴリ属性値表中是否存在
          existsValue = existsValue(categoryAttributeValue.getCategoryCode(), categoryAttributeValue.getCategoryAttributeNo());
          // 检查カテゴリ属性値表字段
          summary.getErrors().addAll(BeanValidator.validate(categoryAttributeValue).getErrors());
          if (summary.hasError()) {
            throw new CsvImportException();
          }
          int updValueCount = executeCategoryAttributeValueUpdate(existsValue);
          if (updValueCount != 1) {
            throw new CsvImportException();
          }
        }
      }
      if (!existsExt) {
        // 商品拡張情報表新建
        int updExtCount = executeExtUpdate(existsExt);
        if (updExtCount != 1) {
          throw new CsvImportException();
        }
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

  /**
   * 商品基本表修改
   */
  private String getUpdateHeaderQuery() {

    // 2014/05/06 京东WBS对应 ob_卢 update start
//    String updateSql = "" + " UPDATE " + HEADER_TABLE_NAME
//    + " SET  SYNC_FLAG_EC = ?,SYNC_FLAG_TMALL = ? , CATEGORY_ATTRIBUTE_VALUE = ? ,"
//    + " UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ? ";
    String updateSql = "" + " UPDATE " + HEADER_TABLE_NAME
    + " SET  SYNC_FLAG_EC = ?,SYNC_FLAG_TMALL = ? , SYNC_FLAG_JD = ? , CATEGORY_ATTRIBUTE_VALUE = ? ,"
    + " UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ? ";
    // 2014/05/06 京东WBS对应 ob_卢 update endd
    return updateSql;
  }

  /**
   * 商品基本表查询存在
   */
  private String getSelectHeaderQuery() {
    String selectSql = "" + " SELECT COUNT(*) FROM " + HEADER_TABLE_NAME + " WHERE COMMODITY_CODE = ? ";
    return selectSql;
  }

  /**
   * 商品详细表查询存在
   */
  private String getSelectDetailQuery() {
    String selectSql = "" + " SELECT COUNT(*) FROM " + DATAIL_TABLE_NAME + " WHERE SKU_CODE = ? ";
    return selectSql;
  }

  /**
   * 在库表查询存在
   */
  private String getSelectStockQuery() {
    String selectSql = "" + " SELECT COUNT(*) FROM " + STOCK_TABLE_NAME + " WHERE  SKU_CODE = ? ";
    return selectSql;
  }

  /**
   * カテゴリ陳列商品表查询存在
   */
  private String getSelectCategoryCommodityQuery() {
    String selectSql = "" + " SELECT COUNT(*) FROM " + CATEGORY_COMMODITY_TABLE_NAME
        + " WHERE COMMODITY_CODE = ? AND CATEGORY_CODE = ?";
    return selectSql;
  }

  /**
   * カテゴリ属性値表查询存在
   */
  private String getSelectCategoryAttributeValueQuery() {
    String selectSql = "" + " SELECT COUNT(*) FROM " + CATEGORY_ATTRIBUTE_VALUE_TABLE_NAME
        + " WHERE  COMMODITY_CODE = ? AND CATEGORY_CODE = ? AND CATEGORY_ATTRIBUTE_NO = ? ";
    return selectSql;
  }

  /**
   * 商品拡張情報表查询存在
   */
  private String getSelectCCommodityExtQuery() {
    String selectSql = "" + " SELECT COUNT(*) FROM " + EXT_TABLE_NAME + " WHERE  COMMODITY_CODE = ? ";
    return selectSql;
  }

  /**
   * 商品基本表新建Query
   */
  private String getInsertHeaderQuery() {
    String insertSql = "" + " INSERT INTO " + HEADER_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'c_commodity_header_seq'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    // ショップコード
    columnList.add("SHOP_CODE");
    // SKUコード
    columnList.add("COMMODITY_CODE");
    // 商品コード
    columnList.add("COMMODITY_NAME");
    // 商品名称英字
    columnList.add("COMMODITY_NAME_EN");
    // 商品名称日文
    columnList.add("COMMODITY_NAME_JP");
    // 代表SKUコード
    columnList.add("REPRESENT_SKU_CODE");
    // 代表SKU単価
    columnList.add("REPRESENT_SKU_UNIT_PRICE");
    // 商品説明1
    columnList.add("COMMODITY_DESCRIPTION1");
    // 商品説明1（英文）
    columnList.add("COMMODITY_DESCRIPTION1_EN");
    // 商品説明1（日文）
    columnList.add("COMMODITY_DESCRIPTION1_JP");
    // 商品説明2
    columnList.add("COMMODITY_DESCRIPTION2");
    // 商品説明2（英文）
    columnList.add("COMMODITY_DESCRIPTION2_EN");
    // 商品説明2（日文）
    columnList.add("COMMODITY_DESCRIPTION2_JP");
    // 商品説明3
    columnList.add("COMMODITY_DESCRIPTION3");
    // 商品説明3（英文）
    columnList.add("COMMODITY_DESCRIPTION3_EN");
    // 商品説明3（日文）
    columnList.add("COMMODITY_DESCRIPTION3_JP");
    // 商品説明(一覧用）
    columnList.add("COMMODITY_DESCRIPTION_SHORT");
    // 商品説明(一覧用）（英文）
    columnList.add("COMMODITY_DESCRIPTION_SHORT_EN");
    // 商品説明(一覧用）（日文）
    columnList.add("COMMODITY_DESCRIPTION_SHORT_JP");
    // 商品検索ワード
    columnList.add("COMMODITY_SEARCH_WORDS");
    // 販売開始日時
    columnList.add("SALE_START_DATETIME");
    // 販売終了日時
    columnList.add("SALE_END_DATETIME");
    // 特別価格開始日時
    columnList.add("DISCOUNT_PRICE_START_DATETIME");
    // 特別価格終了日時
    columnList.add("DISCOUNT_PRICE_END_DATETIME");
    // 規格1名称ID(TMALLの属性ID）
    columnList.add("STANDARD1_ID");
    // 規格1名称
    columnList.add("STANDARD1_NAME");
    // 規格1名称英文
    columnList.add("STANDARD1_NAME_EN");
    // 規格1名称日文
    columnList.add("STANDARD1_NAME_JP");
    // 規格2名称ID(TMALLの属性ID）
    columnList.add("STANDARD2_ID");
    // 規格2名称
    columnList.add("STANDARD2_NAME");
    // 規格2名称英文
    columnList.add("STANDARD2_NAME_EN");
    // 規格2名称日文
    columnList.add("STANDARD2_NAME_JP");
    // EC販売フラグ
    columnList.add("SALE_FLG_EC");
    // 返品不可フラグ
    columnList.add("RETURN_FLG");
    // ワーニング区分
    columnList.add("WARNING_FLAG");
    // リードタイム
    columnList.add("LEAD_TIME");
    // セール区分
    columnList.add("SALE_FLAG");
    // 特集区分
    columnList.add("SPEC_FLAG");
    // ブランドコード
    columnList.add("BRAND_CODE");
    // TMALL商品ID（APIの戻り値）
    columnList.add("TMALL_COMMODITY_ID");
    // TMALL代表SKU単価
    columnList.add("TMALL_REPRESENT_SKU_PRICE");
    // TMALLのカテゴリID
    columnList.add("TMALL_CATEGORY_ID");
    // 取引先コード
    columnList.add("SUPPLIER_CODE");
    // 仕入担当者コード
    columnList.add("BUYER_CODE");
    // 産地
    columnList.add("ORIGINAL_PLACE");
    columnList.add("ORIGINAL_PLACE_EN");
    columnList.add("ORIGINAL_PLACE_JP");
    columnList.add("INGREDIENT_NAME1");
    columnList.add("INGREDIENT_VAL1");
    columnList.add("INGREDIENT_NAME2");
    columnList.add("INGREDIENT_VAL2");
    columnList.add("INGREDIENT_NAME3");
    columnList.add("INGREDIENT_VAL3");
    columnList.add("INGREDIENT_NAME4");
    columnList.add("INGREDIENT_VAL4");
    columnList.add("INGREDIENT_NAME5");
    columnList.add("INGREDIENT_VAL5");
    columnList.add("INGREDIENT_NAME6");
    columnList.add("INGREDIENT_VAL6");
    columnList.add("INGREDIENT_NAME7");
    columnList.add("INGREDIENT_VAL7");
    columnList.add("INGREDIENT_NAME8");
    columnList.add("INGREDIENT_VAL8");
    columnList.add("INGREDIENT_NAME9");
    columnList.add("INGREDIENT_VAL9");
    columnList.add("INGREDIENT_NAME10");
    columnList.add("INGREDIENT_VAL10");
    columnList.add("INGREDIENT_NAME11");
    columnList.add("INGREDIENT_VAL11");
    columnList.add("INGREDIENT_NAME12");
    columnList.add("INGREDIENT_VAL12");
    columnList.add("INGREDIENT_NAME13");
    columnList.add("INGREDIENT_VAL13");
    columnList.add("INGREDIENT_NAME14");
    columnList.add("INGREDIENT_VAL14");
    columnList.add("INGREDIENT_NAME15");
    columnList.add("INGREDIENT_VAL15");
    // 原材料1
    columnList.add("MATERIAL1");
    // 原材料2
    columnList.add("MATERIAL2");
    // 原材料3
    columnList.add("MATERIAL3");
    // 原材料4
    columnList.add("MATERIAL4");
    // 原材料5
    columnList.add("MATERIAL5");
    // 原材料6
    columnList.add("MATERIAL6");
    // 原材料7
    columnList.add("MATERIAL7");
    // 原材料8
    columnList.add("MATERIAL8");
    // 原材料9
    columnList.add("MATERIAL9");
    // 原材料10
    columnList.add("MATERIAL10");
    // 原材料11
    columnList.add("MATERIAL11");
    // 原材料12
    columnList.add("MATERIAL12");
    // 原材料13
    columnList.add("MATERIAL13");
    // 原材料14
    columnList.add("MATERIAL14");
    // 原材料15
    columnList.add("MATERIAL15");
    // 商品期限管理フラグ 0管理しない/1賞味期限日/2製造日＋保管日数
    columnList.add("SHELF_LIFE_FLAG");
    // 保管日数
    columnList.add("SHELF_LIFE_DAYS");
    // 入库效期
    columnList.add("IN_BOUND_LIFE_DAYS");
    // 出库效期
    columnList.add("OUT_BOUND_LIFE_DAYS");
    // 失效预警
    columnList.add("SHELF_LIFE_ALERT_DAYS");
    // 大物フラグ
    columnList.add("BIG_FLAG");
    // ECへの同期時間
    columnList.add("SYNC_TIME_EC");
    // TMALLへの同期時間
    columnList.add("SYNC_TIME_TMALL");
    // ECへの同期フラグ(0:同期不可、1同期可能、2同期済み)
    columnList.add("SYNC_FLAG_EC");
    // TMALLへの同期フラグ(0:同期不可、1同期可能、2同期済み)
    columnList.add("SYNC_FLAG_TMALL");
    // ECへの同期ユーザー
    columnList.add("SYNC_USER_EC");
    // MALLへの同期ユーザー
    columnList.add("SYNC_USER_TMALL");
    // ERP取込用データ対象フラグ(0：対象外、1：対象）
    columnList.add("EXPORT_FLAG_ERP");
    // WMS取込用データ対象フラグ(0：対象外、1：対象）
    columnList.add("EXPORT_FLAG_WMS");
    // 検索用カテゴリパス
    columnList.add("CATEGORY_SEARCH_PATH");
    // 商品の分類属性値
    columnList.add("CATEGORY_ATTRIBUTE_VALUE");

    columnList.add("food_security_prd_license_no");
    columnList.add("food_security_design_code");
    columnList.add("food_security_factory");
    columnList.add("food_security_factory_site");
    columnList.add("food_security_contact");
    columnList.add("food_security_mix");
    columnList.add("food_security_plan_storage");
    columnList.add("food_security_period");
    columnList.add("food_security_food_additive");
    columnList.add("food_security_supplier");
    columnList.add("food_security_product_date_start");
    columnList.add("food_security_product_date_end");
    columnList.add("food_security_stock_date_start");
    columnList.add("food_security_stock_date_end");
    columnList.add("commodity_type");
    columnList.add("tmall_mjs_flg");
    columnList.add("original_code");
    columnList.add("import_commodity_type");
    columnList.add("clear_commodity_type");
    columnList.add("reserve_commodity_type1");
    columnList.add("reserve_commodity_type2");
    columnList.add("reserve_commodity_type3");
    columnList.add("new_reserve_commodity_type1");
    columnList.add("new_reserve_commodity_type2");
    columnList.add("SET_COMMODITY_FLG");
    // 2014/04/29 京东WBS对应 ob_卢 add start
    columnList.add("JD_CATEGORY_ID");
    columnList.add("ADVERT_CONTENT");
    columnList.add("SYNC_FLAG_JD");
    // 2014/04/29 京东WBS对应 ob_卢 add end
    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  /**
   * 商品明细表新建Query
   */
  private String getInsertDetailQuery() {
    String insertSql = "" + " INSERT INTO " + DATAIL_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'c_commodity_detail_seq'") + ", ?, ?, ?, ?) ";
    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    // ショップコード
    columnList.add("SHOP_CODE");
    // SKUコード
    columnList.add("SKU_CODE");
    // SKU名称
    columnList.add("SKU_NAME");
    // 商品コード
    columnList.add("COMMODITY_CODE");
    // 定価フラグ0：非定価　1：定価
    columnList.add("FIXED_PRICE_FLAG");
    // 希望小売価格
    columnList.add("SUGGESTE_PRICE");
    // 仕入価格
    columnList.add("PURCHASE_PRICE");
    // EC商品単価
    columnList.add("UNIT_PRICE");
    // EC特別価格
    columnList.add("DISCOUNT_PRICE");
    // 規格1値のID(=TMALL属性値ID)
    columnList.add("STANDARD_DETAIL1_ID");
    // 規格1値の文字列(値のIDなければ、これを利用）
    columnList.add("STANDARD_DETAIL1_NAME");
    columnList.add("STANDARD_DETAIL1_NAME_EN");
    columnList.add("STANDARD_DETAIL1_NAME_JP");
    // 規格2値のID(=TMALL属性値ID)
    columnList.add("STANDARD_DETAIL2_ID");
    // 規格2値の文字列(値のIDなければ、これを利用）
    columnList.add("STANDARD_DETAIL2_NAME");
    columnList.add("STANDARD_DETAIL2_NAME_EN");
    columnList.add("STANDARD_DETAIL2_NAME_JP");
    // 商品重量(単位はKG)、未設定の場合、商品HEADの重量を利用。
    columnList.add("WEIGHT");
    // 容量
    columnList.add("VOLUME");
    // 容量単位
    columnList.add("VOLUME_UNIT");
    // 取扱いフラグ(EC)
    columnList.add("USE_FLG");
    // 最小仕入数
    columnList.add("MINIMUM_ORDER");
    // 最大仕入数
    columnList.add("MAXIMUM_ORDER");
    // 最小単位の仕入数
    columnList.add("ORDER_MULTIPLE");
    // 在庫警告日数
    columnList.add("STOCK_WARNING");
    // TMALLのSKUのID
    columnList.add("TMALL_SKU_ID");
    // TMALLの商品単価
    columnList.add("TMALL_UNIT_PRICE");
    // TMALLの特別価格
    columnList.add("TMALL_DISCOUNT_PRICE");
    // WEB表示単価単位容量
    columnList.add("VOLUME_UNIT_FOR_PRICE");
    // 縦
    columnList.add("LENGTH");
    // 横
    columnList.add("WIDTH");
    // 高さ
    columnList.add("HEIGHT");
    // 入数
    columnList.add("INNER_QUANTITY");
    // 入数単位
    columnList.add("INNER_QUANTITY_UNIT");
    // WEB表示単価単位入数
    columnList.add("INNER_UNIT_FOR_PRICE");
    // 下限売価
    columnList.add("MIN_PRICE");
    // 取扱いフラグ(TMALL)
    columnList.add("TMALL_USE_FLG");
    // 税率区分
    columnList.add("TAX_CLASS");
    // 箱规
    columnList.add("UNIT_BOX");
    // 2014/04/29 京东WBS对应 ob_卢 add start
    // JD使用标志
    columnList.add("JD_USE_FLG");
    columnList.add("AVERAGE_COST");
    // 2014/04/29 京东WBS对应 ob_卢 add end
    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  /**
   * 在库表新建Query
   */
  private String getInsertStockQuery() {
    String insertSql = "" + " INSERT INTO " + STOCK_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'stock_seq'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    // ショップコード
    columnList.add("SHOP_CODE");
    // SKUコード
    columnList.add("SKU_CODE");
    // 商品コード
    columnList.add("COMMODITY_CODE");
    // 在庫数量
    columnList.add("STOCK_QUANTITY");
    // 引当数量
    columnList.add("ALLOCATED_QUANTITY");
    // 予約数量
    columnList.add("RESERVED_QUANTITY");
    // 予約上限数
    columnList.add("RESERVATION_LIMIT");
    // 注文毎予約上限数
    columnList.add("ONESHOT_RESERVATION_LIMIT");
    // 在庫閾値
    columnList.add("STOCK_THRESHOLD");
    // TMALL引当数
    columnList.add("ALLOCATED_TMALL");
    // EC在庫割合(0-100)
    columnList.add("SHARE_RATIO");
    // 総在庫
    columnList.add("STOCK_TOTAL");
    // TMALL在庫数
    columnList.add("STOCK_TMALL");
    // 在庫リーバランスフラグ
    columnList.add("SHARE_RECALC_FLAG");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  /**
   * カテゴリ陳列商品表新建Query
   */
  private String getInsertCategoryCommodityQuery() {
    String insertSql = "" + " INSERT INTO " + CATEGORY_COMMODITY_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'category_commodity_seq'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    // ショップコード
    columnList.add("SHOP_CODE");
    // カテゴリコード
    columnList.add("CATEGORY_CODE");
    // 商品コード
    columnList.add("COMMODITY_CODE");
    // 検索用カテゴリパス
    columnList.add("CATEGORY_SEARCH_PATH");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  /**
   * カテゴリ属性値表新建Query
   */
  private String getInsertCategoryAttributeValueQuery() {
    String insertSql = "" + " INSERT INTO " + CATEGORY_ATTRIBUTE_VALUE_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'category_attribute_value_seq'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    // ショップコード
    columnList.add("SHOP_CODE");
    // カテゴリコード
    columnList.add("CATEGORY_CODE");
    // カテゴリ属性番号
    columnList.add("CATEGORY_ATTRIBUTE_NO");
    // 商品コード
    columnList.add("COMMODITY_CODE");
    // カテゴリ属性値
    columnList.add("CATEGORY_ATTRIBUTE_VALUE");
    // add by cs_yuli 20120608 start
    // 商品の分類属性英文値
    columnList.add("CATEGORY_ATTRIBUTE_VALUE_EN");
    // 商品の分類属性日文値
    columnList.add("CATEGORY_ATTRIBUTE_VALUE_JP");
    // add by cs_yuli 20120608 end
    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  /**
   * カテゴリ属性値表更新Query
   */
  private String getUpdateCategoryAttributeValueQuery() {
    String updateSql = "" + " UPDATE " + CATEGORY_ATTRIBUTE_VALUE_TABLE_NAME
        + " SET CATEGORY_ATTRIBUTE_VALUE = ? ,CATEGORY_ATTRIBUTE_VALUE_EN = ? ,CATEGORY_ATTRIBUTE_VALUE_JP = ? ,"
        + "UPDATED_USER = ?, UPDATED_DATETIME = ? "
        + " WHERE COMMODITY_CODE = ? AND CATEGORY_CODE = ? AND CATEGORY_ATTRIBUTE_NO = ?";
    return updateSql;
  }

  /**
   * カテゴリ属性値删除Query
   */
  private String getDeleteCategoryAttributeValueQuery() {
    String deleteSql = "" + "DELETE FROM " + CATEGORY_ATTRIBUTE_VALUE_TABLE_NAME
        + " WHERE COMMODITY_CODE = ? AND CATEGORY_CODE = ? AND CATEGORY_ATTRIBUTE_NO = ?";
    return deleteSql;
  }

  /**
   * 商品拡張情報表新建Query
   */
  private String getInsertCCommodityExtQuery() {
    String insertSql = "" + " INSERT INTO " + EXT_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'c_commodity_ext_seq'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    // ショップコード
    columnList.add("SHOP_CODE");
    // 商品コード
    columnList.add("COMMODITY_CODE");
    // 在庫品区分1:在庫品、2:受注発注品
    columnList.add("ON_STOCK_FLAG");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  /**
   * 商品基本表新增
   */
  private int executeHeaderUpdate(boolean exists) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();

    if (exists) {
      // ECへの同期フラグ(0:同期不可、1同期可能、2同期済み)
      // 20130703 shen update start
      // params.add(header.getSyncFlagEc());
      params.add(1L);
      // 20130703 shen update end
      // TMALLへの同期フラグ(0:同期不可、1同期可能、2同期済み)
      // 20130703 shen update start
      // params.add(header.getSyncFlagTmall());
      params.add(1L);
      // 20130703 shen update end
      // params.add(header.getCategorySearchPath());
      // 2014/05/06 京东WBS对应 ob_卢 add start
      params.add(SyncFlagJd.SYNCVISIBLE.longValue());
      // 2014/05/06 京东WBS对应 ob_卢 add end
      params.add(header.getCategoryAttributeValue());
      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      params.add(header.getCommodityCode());

      pstmt = updateHeaderStatement;
      logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    } else {
      // ショップコード
      params.add(header.getShopCode());
      // 商品コード
      params.add(header.getCommodityCode());
      // 商品名称
      params.add(header.getCommodityName());
      // 商品名称英字
      params.add(header.getCommodityNameEn());
      // 商品名称日文
      params.add(header.getCommodityNameJp());
      // 代表SKUコード
      params.add(header.getRepresentSkuCode());
      if (header.getSetCommodityFlg() != null &&  header.getSetCommodityFlg() == 1L) {
        // 代表SKU単価
        params.add(BigDecimal.ZERO);
      } else {
        // 代表SKU単価
        params.add(header.getRepresentSkuUnitPrice());
      }
      // 商品説明1
      params.add(header.getCommodityDescription1());
      // 商品説明1英文
      params.add(header.getCommodityDescription1En());
      // 商品説明1日文
      params.add(header.getCommodityDescription1Jp());
      // 商品説明2
      params.add(header.getCommodityDescription2());
      // 商品説明2英文
      params.add(header.getCommodityDescription2En());
      // 商品説明2日文
      params.add(header.getCommodityDescription2Jp());
      // 商品説明3
      params.add(header.getCommodityDescription3());
      // 商品説明3英文
      params.add(header.getCommodityDescription3En());
      // 商品説明3日文
      params.add(header.getCommodityDescription3Jp());
      // 商品説明(一覧用）
      params.add(header.getCommodityDescriptionShort());
      // 商品説明(一覧用）英文
      params.add(header.getCommodityDescriptionShortEn());
      // 商品説明(一覧用）日文
      params.add(header.getCommodityDescriptionShortJp());
      // 商品検索ワード
      params.add(header.getCommoditySearchWords());
      // 販売開始日時
      params.add(header.getSaleStartDatetime());
      // 販売終了日時
      params.add(header.getSaleEndDatetime());
      if (header.getSetCommodityFlg() != null &&  header.getSetCommodityFlg() == 1L) {
        // 特別価格開始日時
        params.add(null);
        // 特別価格終了日時
        params.add(null);
      } else {
        // 特別価格開始日時
        params.add(header.getDiscountPriceStartDatetime());
        // 特別価格終了日時
        params.add(header.getDiscountPriceEndDatetime());
      }

      
      // 規格1名称ID(TMALLの属性ID）
      params.add(header.getStandard1Id());
      // 規格1名称
      params.add(header.getStandard1Name());
      // 規格1名称英文
      params.add(header.getStandard1NameEn());
      // 規格1名称日文
      params.add(header.getStandard1NameJp());
      // 規格2名称ID(TMALLの属性ID）
      params.add(header.getStandard2Id());
      // 規格2名称
      params.add(header.getStandard2Name());
      // 規格2名称英文
      params.add(header.getStandard2NameEn());
      // 規格2名称日文
      params.add(header.getStandard2NameJp());
      // EC販売フラグ
      params.add(header.getSaleFlgEc());
      // 返品不可フラグ
      params.add(header.getReturnFlg());
      // ワーニング区分
      params.add(header.getWarningFlag());
      // リードタイム
      params.add(header.getLeadTime());
      // セール区分
      params.add(header.getSaleFlag());
      // 特集区分
      params.add(header.getSpecFlag());
      // ブランドコード
      params.add(header.getBrandCode());
      // TMALL商品ID（APIの戻り値）
      params.add(header.getTmallCommodityId());
      if (header.getSetCommodityFlg() != null &&  header.getSetCommodityFlg() == 1L) {
        // TMALL代表SKU単価
        params.add(BigDecimal.ZERO);
      } else {
        // TMALL代表SKU単価
        params.add(header.getTmallRepresentSkuPrice());
      }
      // TMALLのカテゴリID
      params.add(header.getTmallCategoryId());
      // 取引先コード
      params.add(header.getSupplierCode());
      // 仕入担当者コード
      params.add(header.getBuyerCode());
      // 産地
      params.add(header.getOriginalPlace());
      params.add(header.getOriginalPlaceEn());
      params.add(header.getOriginalPlaceJp());

      params.add(header.getIngredientName1());
      params.add(header.getIngredientVal1());
      params.add(header.getIngredientName2());
      params.add(header.getIngredientVal2());
      params.add(header.getIngredientName3());
      params.add(header.getIngredientVal3());
      params.add(header.getIngredientName4());
      params.add(header.getIngredientVal4());
      params.add(header.getIngredientName5());
      params.add(header.getIngredientVal5());
      params.add(header.getIngredientName6());
      params.add(header.getIngredientVal6());
      params.add(header.getIngredientName7());
      params.add(header.getIngredientVal7());
      params.add(header.getIngredientName8());
      params.add(header.getIngredientVal8());
      params.add(header.getIngredientName9());
      params.add(header.getIngredientVal9());
      params.add(header.getIngredientName10());
      params.add(header.getIngredientVal10());
      params.add(header.getIngredientName11());
      params.add(header.getIngredientVal11());
      params.add(header.getIngredientName12());
      params.add(header.getIngredientVal12());
      params.add(header.getIngredientName13());
      params.add(header.getIngredientVal13());
      params.add(header.getIngredientName14());
      params.add(header.getIngredientVal14());
      params.add(header.getIngredientName15());
      params.add(header.getIngredientVal15());
      params.add(header.getMaterial1());
      params.add(header.getMaterial2());
      params.add(header.getMaterial3());
      params.add(header.getMaterial4());
      params.add(header.getMaterial5());
      params.add(header.getMaterial6());
      params.add(header.getMaterial7());
      params.add(header.getMaterial8());
      params.add(header.getMaterial9());
      params.add(header.getMaterial10());
      params.add(header.getMaterial11());
      params.add(header.getMaterial12());
      params.add(header.getMaterial13());
      params.add(header.getMaterial14());
      params.add(header.getMaterial15());
      // 商品期限管理フラグ 0管理しない/1賞味期限日/2製造日＋保管日数
      params.add(header.getShelfLifeFlag());
      // 保管日数
      params.add(header.getShelfLifeDays());
      // add by cs_yuli 20120605 start
      // 入库生命天数
      params.add(header.getInBoundLifeDays());
      // 出库生命天数
      params.add(header.getOutBoundLifeDays());
      // 失效期预警
      params.add(header.getShelfLifeAlertDays());
      // add by cs_yuli 20120605 end
      // 大物フラグ
      params.add(header.getBigFlag());
      // ECへの同期時間
      params.add(header.getSyncTimeEc());
      // TMALLへの同期時間
      params.add(header.getSyncTimeTmall());
      // ECへの同期フラグ(0:同期不可、1同期可能、2同期済み)
      // 20130703 shen update start
      // params.add(header.getSyncFlagEc());
      params.add(1L);
      // 20130703 shen update end
      // TMALLへの同期フラグ(0:同期不可、1同期可能、2同期済み)
      // 20130703 shen update start
      // params.add(header.getSyncFlagTmall());
      params.add(1L);
      // 20130703 shen update end
      // ECへの同期ユーザー
      params.add(header.getSyncUserEc());
      // TMALLへの同期ユーザー
      params.add(header.getSyncUserTmall());
      // ERP取込用データ対象フラグ(0：対象外、1：対象）
      params.add(header.getExportFlagErp());
      // WMS取込用データ対象フラグ(0：対象外、1：対象）
      params.add(header.getExportFlagWms());
      // 検索用カテゴリパス
      params.add(header.getCategorySearchPath());
      // 商品の分類属性値
      params.add(header.getCategoryAttributeValue());

      params.add(header.getFoodSecurityPrdLicenseNo());
      params.add(header.getFoodSecurityDesignCode());
      params.add(header.getFoodSecurityFactory());
      params.add(header.getFoodSecurityFactorySite());
      params.add(header.getFoodSecurityContact());
      params.add(header.getFoodSecurityMix());
      params.add(header.getFoodSecurityPlanStorage());
      params.add(header.getFoodSecurityPeriod());
      params.add(header.getFoodSecurityFoodAdditive());
      params.add(header.getFoodSecuritySupplier());
      params.add(header.getFoodSecurityProductDateStart());
      params.add(header.getFoodSecurityProductDateEnd());
      params.add(header.getFoodSecurityStockDateStart());
      params.add(header.getFoodSecurityStockDateEnd());
      params.add(header.getCommodityType());
      params.add(header.getTmallMjsFlg());
      params.add(header.getOriginalCode());
      params.add(header.getImportCommodityType());
      params.add(header.getClearCommodityType());
      params.add(header.getReserveCommodityType1());
      params.add(header.getReserveCommodityType2());
      params.add(header.getReserveCommodityType3());
      params.add(header.getNewReserveCommodityType1());
      params.add(header.getNewReserveCommodityType2());
      params.add(header.getSetCommodityFlg());
      // 2014/04/29 京东WBS对应 ob_卢 add start
      params.add(header.getJdCategoryId());
      params.add(header.getAdvertContent());
      // 京东への同期フラグ(0:同期不可、1同期可能、2同期済み)
      params.add(SyncFlagJd.SYNCVISIBLE.longValue());
      // 2014/04/29 京东WBS对应 ob_卢 add end
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

  /**
   * 商品明细表新增
   */
  private int executeDetailUpdate(boolean exists) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    if (!exists) {
      // ショップコード
      params.add(detail.getShopCode());
      // SKUコード
      params.add(detail.getSkuCode());
      // SKU名称
      params.add(detail.getSkuName());
      // 商品コード
      params.add(detail.getCommodityCode());
      // 定価フラグ0：非定価　1：定価
      params.add(detail.getFixedPriceFlag());
      if (header.getSetCommodityFlg() != null &&  header.getSetCommodityFlg() == 1L) {
        // 希望小売価格
        params.add(new BigDecimal("0"));
        // 仕入価格
        params.add(new BigDecimal("1"));
        // EC商品単価
        params.add(BigDecimal.ZERO);
        // EC特別価格
        params.add(null);
      } else {
        // 希望小売価格
        params.add(detail.getSuggestePrice());
        // 仕入価格
        params.add(detail.getPurchasePrice());
        // EC商品単価
        params.add(detail.getUnitPrice());
        // EC特別価格
        params.add(detail.getDiscountPrice());
      }

      // 規格1値のID(=TMALL属性値ID)
      params.add(detail.getStandardDetail1Id());
      // 規格1値の文字列(値のIDなければ、これを利用）
      params.add(detail.getStandardDetail1Name());
      params.add(detail.getStandardDetail1NameEn());
      params.add(detail.getStandardDetail1NameJp());
      // 規格2値のID(=TMALL属性値ID)
      params.add(detail.getStandardDetail2Id());
      // 規格2値の文字列(値のIDなければ、これを利用）
      params.add(detail.getStandardDetail2Name());
      params.add(detail.getStandardDetail2NameEn());
      params.add(detail.getStandardDetail2NameJp());
      // 商品重量(単位はKG)、未設定の場合、商品HEADの重量を利用。
      params.add(detail.getWeight());
      // 容量
      params.add(detail.getVolume());
      // 容量単位
      params.add(detail.getVolumeUnit());
      // 取扱いフラグ(EC)
      params.add(detail.getUseFlg());
      // 最小仕入数
      params.add(detail.getMinimumOrder());
      // 最大仕入数
      params.add(detail.getMaximumOrder());
      // 最小単位の仕入数
      params.add(detail.getOrderMultiple());
      // 在庫警告日数
      params.add(detail.getStockWarning());
      // TMALLのSKUのID
      params.add(detail.getTmallSkuId());
      if (header.getSetCommodityFlg() != null &&  header.getSetCommodityFlg() == 1L) {
        // TMALLの商品単価
        params.add(BigDecimal.ZERO);
        // TMALLの特別価格
        params.add(null);
      } else {
        // TMALLの商品単価
        params.add(detail.getTmallUnitPrice());
        // TMALLの特別価格
        params.add(detail.getTmallDiscountPrice());
      }
      // WEB表示単価単位容量
      params.add(detail.getVolumeUnitForPrice());
      // 縦
      params.add(detail.getLength());
      // 横
      params.add(detail.getWidth());
      // 高さ
      params.add(detail.getHeight());
      // 入数
      params.add(detail.getInnerQuantity());
      // 入数単位
      params.add(detail.getInnerQuantityUnit());
      // WEB表示単価単位入数
      params.add(detail.getInnerUnitForPrice());
     
      if (header.getSetCommodityFlg() != null &&  header.getSetCommodityFlg() == 1L) {
        // 下限売価
        params.add(BigDecimal.ZERO);
      } else {
        // 下限売価
        params.add(detail.getMinPrice());
      }
      // 取扱いフラグ(TMALL)
      params.add(detail.getTmallUseFlg());
      // 税率区分
      params.add(detail.getTaxClass());
      //箱规
      params.add(detail.getUnitBox());
      
      // 2014/04/29 京东WBS对应 ob_卢 add start
      // JD使用标志
      params.add(detail.getJdUseFlg() == null ? JdUseFlg.DISABLED.longValue() : detail.getJdUseFlg());
      // 2014/04/29 京东WBS对应 ob_卢 add end
      params.add(detail.getAverageCost() == null ? null : detail.getAverageCost().toString());
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

  /**
   * 在库表新增
   */
  private int executeStockUpdate(boolean exists) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    if (!exists) {
      // ショップコード
      params.add(stock.getShopCode());
      // SKUコード
      params.add(stock.getSkuCode());
      // 商品コード
      params.add(stock.getCommodityCode());
      if (header.getSetCommodityFlg() != null && header.getSetCommodityFlg() == 1L) {
        // 在庫数量
        params.add(0L);
        // 引当数量
        params.add(0L);
        // 予約数量
        params.add(0L);
        // 予約上限数
        params.add(0L);
        // 注文毎予約上限数
        params.add(0L);
        // 安全在庫
        params.add(0L);
        // TMALL引当数
        params.add(0L);
        // EC在庫割合(0-100)
        params.add(stock.getShareRatio());
        // 総在庫
        params.add(0L);
        // TMALL在庫数
        params.add(0L);
      } else {
        // 在庫数量
        params.add(stock.getStockQuantity());
        // 引当数量
        params.add(stock.getAllocatedQuantity());
        // 予約数量
        params.add(stock.getReservedQuantity());
        // 予約上限数
        params.add(stock.getReservationLimit());
        // 注文毎予約上限数
        params.add(stock.getOneshotReservationLimit());
        // 安全在庫
        params.add(stock.getStockThreshold());
        // TMALL引当数
        params.add(stock.getAllocatedTmall());
        // EC在庫割合(0-100)
        params.add(stock.getShareRatio());
        // 総在庫
        params.add(stock.getStockTotal());
        // TMALL在庫数
        params.add(stock.getStockTmall());
      }
      
      // 在庫リーバランスフラグ
      params.add(stock.getShareRecalcFlag());

      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());
      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      pstmt = insertStockStatement;
      logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    }
    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    // 2014/06/11 库存更新对应 ob_yuan update start
    int updateRows = pstmt.executeUpdate();
    if (updateRows!=1) {
      return updateRows;
    }
    //单品时生成对应的TM单品在库信息以及JD单品在库信息以及库存比例信息
    if (header.getSetCommodityFlg() == null || header.getSetCommodityFlg() != 1L) {
      
      params.clear();
      // ショップコード
      params.add(stock.getShopCode());
      // SKUコード
      params.add(stock.getSkuCode());
      // 商品コード
      params.add(stock.getCommodityCode());
      // 在库数
      params.add(0L);
      // 引当数
      params.add(0L);
      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());
      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());
      
      //TM单品在库信息作成
      pstmt = createPreparedStatement(getInsertTmStockSql());
      logger.debug("INSERT TMALL_STOCK Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
      DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
      updateRows = pstmt.executeUpdate();
      if (updateRows!=1) {
        return updateRows;
      }
      //JD单品在库信息作成
      pstmt = createPreparedStatement(getInsertJdStockSql());
      logger.debug("INSERT JD_STOCK Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
      DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
      updateRows = pstmt.executeUpdate();
      if (updateRows!=1) {
        return updateRows;
      }
      for (RatioType type : RatioType.values()) {
        params.clear();
        // ショップコード
        params.add(stock.getShopCode());
        // 商品コード
        params.add(stock.getCommodityCode());
        // 库存比例区分
        params.add(type.longValue());
        // 库存比例
        Long stockRatio = 0L;
        if (type == RatioType.EC) {
          stockRatio = ecStockRatio;
        } else if (type == RatioType.TMALL) {
          stockRatio = tmStockRatio;
        } else {
          stockRatio = jdStockRatio;
        }
        params.add(stockRatio);
        params.add(getCondition().getLoginInfo().getRecordingFormat());
        params.add(DateUtil.getSysdate());
        params.add(getCondition().getLoginInfo().getRecordingFormat());
        params.add(DateUtil.getSysdate());
        
        //库存比例信息作成
        pstmt = createPreparedStatement(getInsertStockRatioSql());
        logger.debug("INSERT STOCK_RATIO Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
        DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
        updateRows = pstmt.executeUpdate();
        if (updateRows!=1) {
          return updateRows;
        }
      }
    }
    return updateRows;
    // 2014/06/11 库存更新对应 ob_yuan update end
    
    
  }

//2014/06/11 库存更新对应 ob_yuan update start
  private String getInsertTmStockSql() {
    String sql = "INSERT INTO TMALL_STOCK(shop_code, sku_code, commodity_code, stock_quantity, allocated_quantity, " +
            "orm_rowid, created_user, created_datetime, updated_user, updated_datetime)" +
            "VALUES (?, ?, ?, ?, ?,  "+ SqlDialect.getDefault().getNextvalNOprm("tmall_stock_seq") +", ?,?, ?, ?);";
    return sql;
  }
  private String getInsertJdStockSql() {
    String sql = "INSERT INTO JD_STOCK(shop_code, sku_code, commodity_code, stock_quantity, allocated_quantity, " +
            "orm_rowid, created_user, created_datetime, updated_user, updated_datetime)" +
            "VALUES (?, ?, ?, ?, ?,  "+ SqlDialect.getDefault().getNextvalNOprm("jd_stock_seq") +", ?,?, ?, ?);";
    return sql;
  }
  private String getInsertStockRatioSql() {
    String sql = "INSERT INTO STOCK_RATIO(shop_code, commodity_code, ratio_type, stock_ratio, " +
    "orm_rowid, created_user, created_datetime, updated_user, updated_datetime)" +
    "VALUES (?, ?, ?, ?,  "+ SqlDialect.getDefault().getNextvalNOprm("stock_ratio_seq") +", ?,?, ?, ?)";
return sql;
  }
//2014/06/11 库存更新对应 ob_yuan update end
  /**
   * カテゴリ陳列商品表新增
   */
  private int executeCategoryCommodityUpdate(boolean exists) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    if (!exists) {
      // ショップコード
      params.add(categoryCommodity.getShopCode());
      // カテゴリコード
      params.add(categoryCommodity.getCategoryCode());
      // 商品コード
      params.add(categoryCommodity.getCommodityCode());
      // 検索用カテゴリパス
      params.add(categoryCommodity.getCategorySearchPath());

      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());
      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      pstmt = insertCategoryCommodityStatement;
      logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    }
    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }

  /**
   * カテゴリ属性値表新增修改
   */
  private int executeCategoryAttributeValueUpdate(boolean exists) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    if (exists) {
      // カテゴリ属性値
      params.add(categoryAttributeValue.getCategoryAttributeValue());
      // add by cs_yuli 20120608 start
      // カテゴリ属性英文値
      params.add(categoryAttributeValue.getCategoryAttributeValueEn());
      // カテゴリ属性日文値
      params.add(categoryAttributeValue.getCategoryAttributeValueJp());
      // add by cs_yuli 20120608 end
      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      // 商品コード
      params.add(categoryAttributeValue.getCommodityCode());
      // カテゴリコード
      params.add(categoryAttributeValue.getCategoryCode());
      // カテゴリ属性番号
      params.add(categoryAttributeValue.getCategoryAttributeNo());

      pstmt = updateCategoryAttributeValueStatement;
      logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    } else {
      // ショップコード
      params.add(categoryAttributeValue.getShopCode());
      // カテゴリコード
      params.add(categoryAttributeValue.getCategoryCode());
      // カテゴリ属性番号
      params.add(categoryAttributeValue.getCategoryAttributeNo());
      // 商品コード
      params.add(categoryAttributeValue.getCommodityCode());
      // カテゴリ属性値
      params.add(categoryAttributeValue.getCategoryAttributeValue());
      // add by cs_yuli 20120608 start
      // カテゴリ属性英文値
      params.add(categoryAttributeValue.getCategoryAttributeValueEn());
      // カテゴリ属性日文値
      params.add(categoryAttributeValue.getCategoryAttributeValueJp());
      // add by cs_yuli 20120608 end

      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());
      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      pstmt = insertCategoryAttributeValueStatement;
      logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    }
    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }

  /**
   * カテゴリ属性値删除
   */
  private int executeDeleteCategoryAttributeValue() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;

    params.add(categoryAttributeValue.getCommodityCode());
    params.add(categoryAttributeValue.getCategoryCode());

    params.add(categoryAttributeValue.getCategoryAttributeNo());
    pstmt = deleteCategoryAttributeValueStatement;

    logger.debug("DELETE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }

  /**
   * 商品拡張情報表新增
   */
  private int executeExtUpdate(boolean exists) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    if (!exists) {
      // ショップコード
      params.add(ccommodityExt.getShopCode());
      // 商品コード
      params.add(ccommodityExt.getCommodityCode());
      // 在庫品区分
      params.add(ccommodityExt.getOnStockFlag());

      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());
      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      pstmt = insertExtStatement;
      logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    }
    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }

  // 10.1.1 10019 追加 ここから
  private void checkMinusNumber(ValidationSummary summary) {
    // 供应商到货天数必须输入0到99之间的数
    if (header.getLeadTime() < 0 || header.getLeadTime() > 99) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.OUT_OF_RANGE, Messages
              .getString("service.data.csv.CommodityHeaderImportDataSource.66"), "0", "99")));
    }
    // 希望售价必须输入0以上的数
    if (detail.getSuggestePrice() != null && BigDecimalUtil.isBelow(detail.getSuggestePrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.49"))));
    }
    // EC商品单价必须输入0以上的数
    if (detail.getUnitPrice() != null && BigDecimalUtil.isBelow(detail.getUnitPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.40"))));
    }
    // EC商品特价必须输入0以上的数
    if (detail.getDiscountPrice() != null && BigDecimalUtil.isBelow(detail.getDiscountPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.41"))));
    }
    // Tmall商品单价必须输入0以上的数
    if (detail.getTmallUnitPrice() != null && BigDecimalUtil.isBelow(detail.getTmallUnitPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.42"))));
    }
    // Tmall商品特价必须输入0以上的数
    if (detail.getTmallDiscountPrice() != null && BigDecimalUtil.isBelow(detail.getTmallDiscountPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.43"))));
    }
    // 最小单价必须输入0以上的数
    if (detail.getMinPrice() != null && BigDecimalUtil.isBelow(detail.getMinPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.50"))));
    }
  }

  // 10.1.1 10019 追加 ここまで
  /**
   * 各期間の日付範囲チェック
   */
  private void checkDateRange(CCommodityHeaderImport bean, List<String> errorMessageList) {
    String min = Integer.toString(DIContainer.getWebshopConfig().getApplicationMinYear());
    String max = Integer.toString(DIContainer.getWebshopConfig().getApplicationMaxYear());

    if (!DateUtil.isCorrectAppDate(bean.getSaleStartDatetime(), false) && !DateUtil.getMin().equals(bean.getSaleStartDatetime())) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages
          .getString("service.data.csv.CommodityHeaderImportDataSource.48"), min, max));
    }
    if (!DateUtil.isCorrectAppDate(bean.getSaleEndDatetime(), false) && !DateUtil.getMax().equals(bean.getSaleEndDatetime())) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages
          .getString("service.data.csv.CommodityHeaderImportDataSource.49"), min, max));
    }
    if (!DateUtil.isCorrectAppDate(bean.getDiscountPriceStartDatetime(), true)) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages
          .getString("service.data.csv.CommodityHeaderImportDataSource.51"), min, max));
    }
    if (!DateUtil.isCorrectAppDate(bean.getDiscountPriceEndDatetime(), true)) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages
          .getString("service.data.csv.CommodityHeaderImportDataSource.52"), min, max));
    }

  }

  /**
   * 各期間の開始/終了日時の順序チェック
   */
  private void checkTermsRules(CCommodityHeaderImport bean, List<String> errorMessageList) {
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
  }

  private void checkSalesTermAndDiscountRules(CCommodityHeaderImport bean, List<String> errorMessageList) {
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

  /**
   * 商品期限管理フラグは2の場合保管日数必須
   */
  private void checkShelfLife(ValidationSummary summary) {
    if (header.getShelfLifeFlag() != null && header.getShelfLifeFlag() == 2) {
      if (header.getShelfLifeDays() == null) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CommodityHeaderImportDataSource.65"))));
      } else {
        if (header.getShelfLifeDays() < 0) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages
                  .getString("service.data.csv.CommodityHeaderImportDataSource.67"))));
        }
      }
    }
  }

  /**
   * 成对检查
   */
  private void pairCheck(ValidationSummary summary) {
    // 成分名1，成分量1
    if ((StringUtil.isNullOrEmpty(header.getIngredientName1()) && StringUtil.hasValue(header.getIngredientVal1()))
        || (StringUtil.isNullOrEmpty(header.getIngredientVal1()) && StringUtil.hasValue(header.getIngredientName1()))) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR, Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.34"), Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.35"))));
    }
    // 成分名2，成分量2
    if ((StringUtil.isNullOrEmpty(header.getIngredientName2()) && StringUtil.hasValue(header.getIngredientVal2()))
        || (StringUtil.isNullOrEmpty(header.getIngredientVal2()) && StringUtil.hasValue(header.getIngredientName2()))) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR, Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.36"), Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.37"))));
    }
    // 成分名3，成分量3
    if ((StringUtil.isNullOrEmpty(header.getIngredientName3()) && StringUtil.hasValue(header.getIngredientVal3()))
        || (StringUtil.isNullOrEmpty(header.getIngredientVal3()) && StringUtil.hasValue(header.getIngredientName3()))) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR, Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.38"), Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.39"))));
    }
    // 成分名4，成分量4
    if ((StringUtil.isNullOrEmpty(header.getIngredientName4()) && StringUtil.hasValue(header.getIngredientVal4()))
        || (StringUtil.isNullOrEmpty(header.getIngredientVal4()) && StringUtil.hasValue(header.getIngredientName4()))) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR, Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.40"), Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.41"))));
    }
    // 成分名5，成分量5
    if ((StringUtil.isNullOrEmpty(header.getIngredientName5()) && StringUtil.hasValue(header.getIngredientVal5()))
        || (StringUtil.isNullOrEmpty(header.getIngredientVal5()) && StringUtil.hasValue(header.getIngredientName5()))) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR, Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.42"), Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.43"))));
    }
    // 成分名6，成分量6
    if ((StringUtil.isNullOrEmpty(header.getIngredientName6()) && StringUtil.hasValue(header.getIngredientVal6()))
        || (StringUtil.isNullOrEmpty(header.getIngredientVal6()) && StringUtil.hasValue(header.getIngredientName6()))) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR, Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.44"), Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.45"))));
    }
    // 成分名7，成分量7
    if ((StringUtil.isNullOrEmpty(header.getIngredientName7()) && StringUtil.hasValue(header.getIngredientVal7()))
        || (StringUtil.isNullOrEmpty(header.getIngredientVal7()) && StringUtil.hasValue(header.getIngredientName7()))) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR, Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.46"), Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.47"))));
    }
    // 成分名8，成分量8
    if ((StringUtil.isNullOrEmpty(header.getIngredientName8()) && StringUtil.hasValue(header.getIngredientVal8()))
        || (StringUtil.isNullOrEmpty(header.getIngredientVal8()) && StringUtil.hasValue(header.getIngredientName8()))) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR, Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.48"), Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.49"))));
    }
    // 成分名9，成分量9
    if ((StringUtil.isNullOrEmpty(header.getIngredientName9()) && StringUtil.hasValue(header.getIngredientVal9()))
        || (StringUtil.isNullOrEmpty(header.getIngredientVal9()) && StringUtil.hasValue(header.getIngredientName9()))) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR, Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.50"), Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.51"))));
    }
    // 成分名10，成分量10
    if ((StringUtil.isNullOrEmpty(header.getIngredientName10()) && StringUtil.hasValue(header.getIngredientVal10()))
        || (StringUtil.isNullOrEmpty(header.getIngredientVal10()) && StringUtil.hasValue(header.getIngredientName10()))) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR, Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.52"), Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.53"))));
    }
    // 成分名11，成分量11
    if ((StringUtil.isNullOrEmpty(header.getIngredientName11()) && StringUtil.hasValue(header.getIngredientVal11()))
        || (StringUtil.isNullOrEmpty(header.getIngredientVal11()) && StringUtil.hasValue(header.getIngredientName11()))) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR, Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.54"), Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.55"))));
    }
    // 成分名12，成分量12
    if ((StringUtil.isNullOrEmpty(header.getIngredientName12()) && StringUtil.hasValue(header.getIngredientVal12()))
        || (StringUtil.isNullOrEmpty(header.getIngredientVal12()) && StringUtil.hasValue(header.getIngredientName12()))) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR, Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.56"), Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.57"))));
    }
    // 成分名13，成分量13
    if ((StringUtil.isNullOrEmpty(header.getIngredientName13()) && StringUtil.hasValue(header.getIngredientVal13()))
        || (StringUtil.isNullOrEmpty(header.getIngredientVal13()) && StringUtil.hasValue(header.getIngredientName13()))) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR, Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.58"), Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.59"))));
    }
    // 成分名14，成分量14
    if ((StringUtil.isNullOrEmpty(header.getIngredientName14()) && StringUtil.hasValue(header.getIngredientVal14()))
        || (StringUtil.isNullOrEmpty(header.getIngredientVal14()) && StringUtil.hasValue(header.getIngredientName14()))) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR, Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.60"), Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.61"))));
    }
    // 成分名15，成分量15
    if ((StringUtil.isNullOrEmpty(header.getIngredientName15()) && StringUtil.hasValue(header.getIngredientVal15()))
        || (StringUtil.isNullOrEmpty(header.getIngredientVal15()) && StringUtil.hasValue(header.getIngredientName15()))) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR, Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.62"), Messages
              .getString("service.data.csv.CCommodityHeaderCsvSchema.63"))));
    }
  }

  /**
   * 定価フラグcheck
   */
  private void checkFixedPrice(ValidationSummary summary) {
    // 定价标志为1
    if (detail.getFixedPriceFlag() != null && detail.getFixedPriceFlag() == 1) {
      // 希望售价不能为空
      if (detail.getSuggestePrice() == null) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CommodityDetailImportDataSource.26"))));
      } else {
        // 官网售价必须等于希望售价
        if (!detail.getSuggestePrice().equals(detail.getUnitPrice())) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_EQUAL, Messages
                  .getString("service.data.csv.CommodityDetailImportDataSource.40"), Messages
                  .getString("service.data.csv.CommodityDetailImportDataSource.49"))));
        }
        // 淘宝售价必须等于希望售价
        if (!detail.getSuggestePrice().equals(detail.getTmallUnitPrice())) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_EQUAL, Messages
                  .getString("service.data.csv.CommodityDetailImportDataSource.42"), Messages
                  .getString("service.data.csv.CommodityDetailImportDataSource.49"))));
        }
      }
      // EC特价不能输入
      if (detail.getDiscountPrice() != null) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.NO_REQUEST_ITEM, Messages
                .getString("service.data.csv.CommodityDetailImportDataSource.27"))));
      }
      // Tmall特价不能输入
      if (detail.getTmallDiscountPrice() != null) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.NO_REQUEST_ITEM, Messages
                .getString("service.data.csv.CommodityDetailImportDataSource.28"))));
      }
    }
  }

  /**
   * 最小单价check
   */
  private void checkMinPrice(ValidationSummary summary) {
    // 最小单价不为空时，所有价格都必须大于最小单价
    if (detail.getMinPrice() != null) {
      // 希望小売価格
      if (!ValidatorUtil.lessThanOrEquals(detail.getMinPrice(), detail.getSuggestePrice())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_GREATER_THAN, Messages
                .getString("service.data.csv.CommodityDetailImportDataSource.44"), detail.getMinPrice().toString())));
      }
      // EC商品单价
      if (!ValidatorUtil.lessThanOrEquals(detail.getMinPrice(), detail.getUnitPrice())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_GREATER_THAN, Messages
                .getString("service.data.csv.CommodityDetailImportDataSource.40"), detail.getMinPrice().toString())));
      }
      // EC商品特价
      if (detail.getDiscountPrice() != null && detail.getFixedPriceFlag() != 1) {
        if (!ValidatorUtil.lessThanOrEquals(detail.getMinPrice(), detail.getDiscountPrice())) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_GREATER_THAN, Messages
                  .getString("service.data.csv.CommodityDetailImportDataSource.41"), detail.getMinPrice().toString())));
        }
      }
      // Tmall商品单价
      if (!ValidatorUtil.lessThanOrEquals(detail.getMinPrice(), detail.getTmallUnitPrice())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_GREATER_THAN, Messages
                .getString("service.data.csv.CommodityDetailImportDataSource.42"), detail.getMinPrice().toString())));
      }
      // Tmall商品特价
      if (detail.getTmallDiscountPrice() != null && detail.getFixedPriceFlag() != 1) {
        if (!ValidatorUtil.lessThanOrEquals(detail.getMinPrice(), detail.getTmallDiscountPrice())) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_GREATER_THAN, Messages
                  .getString("service.data.csv.CommodityDetailImportDataSource.43"), detail.getMinPrice().toString())));
        }
      }
    }
  }

  /**
   * 如果入り数（inner_quantity）,入り数単位(inner_quantity_unit)
   * ,WEB表示単価単位入り数(inner_unit_for_price) 中有一个值不为空，其它两个值也不能为空 且WEB表示単価単位入り数 必须小于
   * 入り数
   */
  private void checkInner(ValidationSummary summary) {
    // 入り数、入り数単位、WEB表示単価単位入り数有一个不为空
    if (StringUtil.hasValueAnyOf(detail.getInnerQuantity() == null ? null : detail.getInnerQuantity().toString(), detail
        .getInnerQuantityUnit(), detail.getInnerUnitForPrice() == null ? null : detail.getInnerUnitForPrice().toString())) {
      // 其他两个都不为空
      if (!StringUtil.hasValueAllOf(detail.getInnerQuantity() == null ? null : detail.getInnerQuantity().toString(), detail
          .getInnerQuantityUnit(), detail.getInnerUnitForPrice() == null ? null : detail.getInnerUnitForPrice().toString())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityDetailImportDataSource.45")));
      } else {
        // WEB表示単価単位入り数必须小于 入り数
        if (!ValidatorUtil.lessThanOrEquals(detail.getInnerUnitForPrice(), detail.getInnerQuantity())) {
          summary.getErrors().add(
              new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityDetailImportDataSource.46")));
        }
      }
    }
  }

  /**
   * 如果容量（volume）,容量単位(volume_unit) ,WEB表示単価単位容量(volume_unit_for_price)
   * 中有一个值不为空，其它两个值也不能为空 且WEB表示単価単位容量 必须小于 容量
   */
  private void checkVolume(ValidationSummary summary) {
    // 容量、容量単位、WEB表示単価単位容量有一个不为空

    if (StringUtil.hasValueAnyOf(detail.getVolume() == null ? null : detail.getVolume().toString(), detail.getVolumeUnit(), detail
        .getVolumeUnitForPrice() == null ? null : detail.getVolumeUnitForPrice().toString())) {
      // 其他两个都不为空
      if (!StringUtil.hasValueAllOf(detail.getVolume() == null ? null : detail.getVolume().toString(), detail.getVolumeUnit(),
          detail.getVolumeUnitForPrice() == null ? null : detail.getVolumeUnitForPrice().toString())) {
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityDetailImportDataSource.47")));
      } else {
        // WEB表示単価単位容量 必须小于 容量
        if (!ValidatorUtil.lessThanOrEquals(detail.getVolumeUnitForPrice(), detail.getVolume())) {
          summary.getErrors().add(
              new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityDetailImportDataSource.48")));
        }
      }
    }
  }

  /**
   * EC在庫割合check
   */
  private void checkShareRatio(ValidationSummary summary) {
    if (stock.getShareRatio() != null && (stock.getShareRatio() > 100 || stock.getShareRatio() < 0)) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.OUT_OF_RANGE, Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.29"), "0", "100")));
    }
  }

  /**
   * 判断商品基本表必须项是否输入，没输入则设为默认值
   */
  private void setHeader(ValidationSummary summary) {
    // ショップコード
    if (header.getShopCode() == null) {

      header.setShopCode(config.getSiteShopCode());
    }
    // 商品基本表検索用カテゴリパス
    String categorySearchPath = null;
    // 商品の分類属性値
    String categoryAttribute = null;
    @SuppressWarnings("unused")
    String categoryAttributeEn = null;
    @SuppressWarnings("unused")
    String categoryAttributeJp = null;
    // 判断商品是否存在，存在的话取出path值
    if (existsHeader) {
      categorySearchPath = getPath();
      categoryAttribute = getValue();
    }
    SimpleQuery pathExistsQuery = new SimpleQuery("SELECT PATH FROM CATEGORY WHERE  CATEGORY_CODE = ?");
    SimpleQuery valueExistsQuery = new SimpleQuery(
        "SELECT CATEGORY_ATTRIBUTE_NAME FROM CATEGORY_ATTRIBUTE WHERE CATEGORY_CODE= ? AND CATEGORY_ATTRIBUTE_NO = ?  ");
    @SuppressWarnings("unused")
    SimpleQuery valueExistsQueryEn = new SimpleQuery(
        "SELECT CATEGORY_ATTRIBUTE_NAME_EN FROM CATEGORY_ATTRIBUTE WHERE CATEGORY_CODE= ? AND CATEGORY_ATTRIBUTE_NO = ?  ");
    @SuppressWarnings("unused")
    SimpleQuery valueExistsQueryJp = new SimpleQuery(
        "SELECT CATEGORY_ATTRIBUTE_NAME_JP FROM CATEGORY_ATTRIBUTE WHERE CATEGORY_CODE= ? AND CATEGORY_ATTRIBUTE_NO = ?  ");
    String category1Code = categoryCommodity.getCategory1Code();
    String category2Code = categoryCommodity.getCategory2Code();
    String category3Code = categoryCommodity.getCategory3Code();
    String category4Code = categoryCommodity.getCategory4Code();
    String category5Code = categoryCommodity.getCategory5Code();
    // 品店カテゴリID1
    if (!StringUtil.isNullOrEmpty(category1Code)) {
      pathExistsQuery.setParameters(category1Code);
      Object object = executeScalar(pathExistsQuery);
      if (object == null) {
        summary.getErrors().add(
            new ValidationResult(Messages.getString("service.data.csv.CategoryCommodityImportSource.1"), null, Message.get(
                CsvMessage.NOT_EXIST, category1Code)));
      } else {
        // 品店カテゴリID1検索用カテゴリパス
        categorySearchPath1 = object.toString() + "~" + category1Code;
        // 商品编号,品店カテゴリ编号在カテゴリ陳列商品表中是否存在
        existsCategory = existsCategory(category1Code);
        if (!existsCategory) {
          if ((categorySearchPath + "#" + categorySearchPath1).length() < 256) {
            // 商品基本表検索用カテゴリパス
            if (StringUtil.isNullOrEmpty(categorySearchPath)) {
              categorySearchPath = categorySearchPath1;
            } else {
              categorySearchPath = categorySearchPath + "#" + categorySearchPath1;
            }
          }
        }
      }
      // 品店カテゴリID1属性１
      String category1Attribute1 = categoryAttributeValue.getCategory1Attribute1();
      if (!StringUtil.isNullOrEmpty(category1Attribute1)) {
        valueExistsQuery.setParameters(category1Code, 0);
        object = executeScalar(valueExistsQuery);
        if (object == null) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_ATTRIBUTE_NO_NOT_EXIST, category1Code, "0")));
        } else {
          if ((categoryAttribute + "#" + object.toString()).length() < 256) {
            if (StringUtil.isNullOrEmpty(categoryAttribute)) {
              // 商品の分類属性値
              categoryAttribute = object.toString();
            } else {
              // 商品の分類属性値
              categoryAttribute = categoryAttribute + "#" + object.toString();
            }
          }
        }
      }
      // 品店カテゴリID1属性2
      String category1Attribute2 = categoryAttributeValue.getCategory1Attribute2();
      if (!StringUtil.isNullOrEmpty(category1Attribute2)) {
        valueExistsQuery.setParameters(category1Code, 1);
        object = executeScalar(valueExistsQuery);
        if (object == null) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_ATTRIBUTE_NO_NOT_EXIST, category1Code, "1")));
        } else {
          if ((categoryAttribute + "#" + object.toString()).length() < 256) {
            // 商品の分類属性値
            if (StringUtil.isNullOrEmpty(categoryAttribute)) {
              categoryAttribute = object.toString();
            } else {
              if (StringUtil.isNullOrEmpty(category1Attribute1)) {
                categoryAttribute = categoryAttribute + "#" + object.toString();
              } else {
                categoryAttribute = categoryAttribute + "|" + object.toString();
              }
            }
          }
        }
      }
      // 品店カテゴリID1属性3
      String category1Attribute3 = categoryAttributeValue.getCategory1Attribute3();
      if (!StringUtil.isNullOrEmpty(category1Attribute3)) {
        valueExistsQuery.setParameters(category1Code, 2);
        object = executeScalar(valueExistsQuery);
        if (object == null) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_ATTRIBUTE_NO_NOT_EXIST, category1Code, "2")));
        } else {
          if ((categoryAttribute + "#" + object.toString()).length() < 256) {
            // 商品の分類属性値
            if (StringUtil.isNullOrEmpty(categoryAttribute)) {
              categoryAttribute = object.toString();
            } else {
              if (StringUtil.isNullOrEmpty(category1Attribute1) && StringUtil.isNullOrEmpty(category1Attribute2)) {
                categoryAttribute = categoryAttribute + "#" + object.toString();
              } else {
                categoryAttribute = categoryAttribute + "|" + object.toString();
              }
            }
          }
        }
      }
    }
    // 品店カテゴリID2
    if (!StringUtil.isNullOrEmpty(category2Code)) {
      pathExistsQuery.setParameters(category2Code);
      Object object = executeScalar(pathExistsQuery);
      if (object == null) {
        summary.getErrors().add(
            new ValidationResult(Messages.getString("service.data.csv.CategoryCommodityImportSource.1"), null, Message.get(
                CsvMessage.NOT_EXIST, category2Code)));
      } else {
        // 品店カテゴリID2検索用カテゴリパス
        categorySearchPath2 = object.toString() + "~" + category2Code;
        // 商品编号,品店カテゴリ编号在カテゴリ陳列商品表中是否存在
        existsCategory = existsCategory(category2Code);
        if (!existsCategory) {
          if ((categorySearchPath + "#" + categorySearchPath2).length() < 256) {
            // 商品基本表検索用カテゴリパス
            if (StringUtil.isNullOrEmpty(categorySearchPath)) {
              categorySearchPath = categorySearchPath2;
            } else {
              categorySearchPath = categorySearchPath + "#" + categorySearchPath2;
            }
          }
        }
      }
      // 品店カテゴリID2属性１
      String category2Attribute1 = categoryAttributeValue.getCategory2Attribute1();
      if (!StringUtil.isNullOrEmpty(category2Attribute1)) {
        valueExistsQuery.setParameters(category2Code, 0);
        object = executeScalar(valueExistsQuery);
        if (object == null) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_ATTRIBUTE_NO_NOT_EXIST, category2Code, "0")));
        } else {
          if ((categoryAttribute + "#" + object.toString()).length() < 256) {
            if (StringUtil.isNullOrEmpty(categoryAttribute)) {
              // 商品の分類属性値
              categoryAttribute = object.toString();
            } else {
              // 商品の分類属性値
              categoryAttribute = categoryAttribute + "#" + object.toString();
            }
          }
        }
      }
      // 品店カテゴリID2属性2
      String category2Attribute2 = categoryAttributeValue.getCategory2Attribute2();
      if (!StringUtil.isNullOrEmpty(category2Attribute2)) {
        valueExistsQuery.setParameters(category2Code, 1);
        object = executeScalar(valueExistsQuery);
        if (object == null) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_ATTRIBUTE_NO_NOT_EXIST, category2Code, "1")));
        } else {
          if ((categoryAttribute + "#" + object.toString()).length() < 256) {
            // 商品の分類属性値
            if (StringUtil.isNullOrEmpty(categoryAttribute)) {
              categoryAttribute = object.toString();
            } else {
              if (StringUtil.isNullOrEmpty(category2Attribute1)) {
                categoryAttribute = categoryAttribute + "#" + object.toString();
              } else {
                categoryAttribute = categoryAttribute + "|" + object.toString();
              }
            }
          }
        }
      }
      // 品店カテゴリID2属性3
      String category2Attribute3 = categoryAttributeValue.getCategory2Attribute3();
      if (!StringUtil.isNullOrEmpty(category2Attribute3)) {
        valueExistsQuery.setParameters(category2Code, 2);
        object = executeScalar(valueExistsQuery);
        if (object == null) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_ATTRIBUTE_NO_NOT_EXIST, category2Code, "2")));
        } else {
          if ((categoryAttribute + "#" + object.toString()).length() < 256) {
            // 商品の分類属性値
            if (StringUtil.isNullOrEmpty(categoryAttribute)) {
              categoryAttribute = object.toString();
            } else {
              if (StringUtil.isNullOrEmpty(category2Attribute1) && StringUtil.isNullOrEmpty(category2Attribute2)) {
                categoryAttribute = categoryAttribute + "#" + object.toString();
              } else {
                categoryAttribute = categoryAttribute + "|" + object.toString();
              }
            }
          }
        }
      }
    }
    // 品店カテゴリID3
    if (!StringUtil.isNullOrEmpty(category3Code)) {
      pathExistsQuery.setParameters(category3Code);
      Object object = executeScalar(pathExistsQuery);
      if (object == null) {
        summary.getErrors().add(
            new ValidationResult(Messages.getString("service.data.csv.CategoryCommodityImportSource.1"), null, Message.get(
                CsvMessage.NOT_EXIST, category3Code)));
      } else {
        // 品店カテゴリID3検索用カテゴリパス
        categorySearchPath3 = object.toString() + "~" + category3Code;
        // 商品编号,品店カテゴリ编号在カテゴリ陳列商品表中是否存在
        existsCategory = existsCategory(category3Code);
        if (!existsCategory) {
          if ((categorySearchPath + "#" + categorySearchPath3).length() < 256) {
            // 商品基本表検索用カテゴリパス
            if (StringUtil.isNullOrEmpty(categorySearchPath)) {
              categorySearchPath = categorySearchPath3;
            } else {
              categorySearchPath = categorySearchPath + "#" + categorySearchPath3;
            }
          }
        }
      }
      // 品店カテゴリID3属性１
      String category3Attribute1 = categoryAttributeValue.getCategory3Attribute1();
      if (!StringUtil.isNullOrEmpty(category3Attribute1)) {
        valueExistsQuery.setParameters(category3Code, 0);
        object = executeScalar(valueExistsQuery);
        if (object == null) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_ATTRIBUTE_NO_NOT_EXIST, category3Code, "0")));
        } else {
          if ((categoryAttribute + "#" + object.toString()).length() < 256) {
            if (StringUtil.isNullOrEmpty(categoryAttribute)) {
              // 商品の分類属性値
              categoryAttribute = object.toString();
            } else {
              // 商品の分類属性値
              categoryAttribute = categoryAttribute + "#" + object.toString();
            }
          }
        }
      }
      // 品店カテゴリID3属性2
      String category3Attribute2 = categoryAttributeValue.getCategory3Attribute2();
      if (!StringUtil.isNullOrEmpty(category3Attribute2)) {
        valueExistsQuery.setParameters(category3Code, 1);
        object = executeScalar(valueExistsQuery);
        if (object == null) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_ATTRIBUTE_NO_NOT_EXIST, category3Code, "1")));
        } else {
          if ((categoryAttribute + "#" + object.toString()).length() < 256) {
            // 商品の分類属性値
            if (StringUtil.isNullOrEmpty(categoryAttribute)) {
              categoryAttribute = object.toString();
            } else {
              if (StringUtil.isNullOrEmpty(category3Attribute1)) {
                categoryAttribute = categoryAttribute + "#" + object.toString();
              } else {
                categoryAttribute = categoryAttribute + "|" + object.toString();
              }
            }
          }
        }
      }
      // 品店カテゴリID3属性3
      String category3Attribute3 = categoryAttributeValue.getCategory3Attribute3();
      if (!StringUtil.isNullOrEmpty(category3Attribute3)) {
        valueExistsQuery.setParameters(category3Code, 2);
        object = executeScalar(valueExistsQuery);
        if (object == null) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_ATTRIBUTE_NO_NOT_EXIST, category3Code, "2")));
        } else {
          if ((categoryAttribute + "#" + object.toString()).length() < 256) {
            // 商品の分類属性値
            if (StringUtil.isNullOrEmpty(categoryAttribute)) {
              categoryAttribute = object.toString();
            } else {
              if (StringUtil.isNullOrEmpty(category3Attribute1) && StringUtil.isNullOrEmpty(category3Attribute2)) {
                categoryAttribute = categoryAttribute + "#" + object.toString();
              } else {
                categoryAttribute = categoryAttribute + "|" + object.toString();
              }
            }
          }
        }
      }
    }
    // 品店カテゴリID4
    if (!StringUtil.isNullOrEmpty(category4Code)) {
      pathExistsQuery.setParameters(category4Code);
      Object object = executeScalar(pathExistsQuery);
      if (object == null) {
        summary.getErrors().add(
            new ValidationResult(Messages.getString("service.data.csv.CategoryCommodityImportSource.1"), null, Message.get(
                CsvMessage.NOT_EXIST, category4Code)));
      } else {
        // 品店カテゴリID4検索用カテゴリパス
        categorySearchPath4 = object.toString() + "~" + category4Code;
        existsCategory = existsCategory(category4Code);
        if (!existsCategory) {
          if ((categorySearchPath + "#" + categorySearchPath4).length() < 256) {
            // 商品基本表検索用カテゴリパス
            if (StringUtil.isNullOrEmpty(categorySearchPath)) {
              categorySearchPath = categorySearchPath4;
            } else {
              categorySearchPath = categorySearchPath + "#" + categorySearchPath4;
            }
          }
        }
      }
      // 品店カテゴリID4属性１
      String category4Attribute1 = categoryAttributeValue.getCategory4Attribute1();
      if (!StringUtil.isNullOrEmpty(category4Attribute1)) {
        valueExistsQuery.setParameters(category4Code, 0);
        object = executeScalar(valueExistsQuery);
        if (object == null) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_ATTRIBUTE_NO_NOT_EXIST, category4Code, "0")));
        } else {
          if ((categoryAttribute + "#" + object.toString()).length() < 256) {
            if (StringUtil.isNullOrEmpty(categoryAttribute)) {
              // 商品の分類属性値
              categoryAttribute = object.toString();
            } else {
              // 商品の分類属性値
              categoryAttribute = categoryAttribute + "#" + object.toString();
            }
          }
        }
      }
      // 品店カテゴリID4属性2
      String category4Attribute2 = categoryAttributeValue.getCategory4Attribute2();
      if (!StringUtil.isNullOrEmpty(category4Attribute2)) {
        valueExistsQuery.setParameters(category4Code, 1);
        object = executeScalar(valueExistsQuery);
        if (object == null) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_ATTRIBUTE_NO_NOT_EXIST, category4Code, "1")));
        } else {
          if ((categoryAttribute + "#" + object.toString()).length() < 256) {
            // 商品の分類属性値
            if (StringUtil.isNullOrEmpty(categoryAttribute)) {
              categoryAttribute = object.toString();
            } else {
              if (StringUtil.isNullOrEmpty(category4Attribute1)) {
                categoryAttribute = categoryAttribute + "#" + object.toString();
              } else {
                categoryAttribute = categoryAttribute + "|" + object.toString();
              }
            }
          }
        }
      }
      // 品店カテゴリID4属性3
      String category4Attribute3 = categoryAttributeValue.getCategory4Attribute3();
      if (!StringUtil.isNullOrEmpty(category4Attribute3)) {
        valueExistsQuery.setParameters(category4Code, 2);
        object = executeScalar(valueExistsQuery);
        if (object == null) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_ATTRIBUTE_NO_NOT_EXIST, category4Code, "2")));
        } else {
          if ((categoryAttribute + "#" + object.toString()).length() < 256) {
            // 商品の分類属性値
            if (StringUtil.isNullOrEmpty(categoryAttribute)) {
              categoryAttribute = object.toString();
            } else {
              if (StringUtil.isNullOrEmpty(category4Attribute1) && StringUtil.isNullOrEmpty(category4Attribute2)) {
                categoryAttribute = categoryAttribute + "#" + object.toString();
              } else {
                categoryAttribute = categoryAttribute + "|" + object.toString();
              }
            }
          }
        }
      }
    }
    // 品店カテゴリID5
    if (!StringUtil.isNullOrEmpty(category5Code)) {
      pathExistsQuery.setParameters(category5Code);
      Object object = executeScalar(pathExistsQuery);
      if (object == null) {
        summary.getErrors().add(
            new ValidationResult(Messages.getString("service.data.csv.CategoryCommodityImportSource.1"), null, Message.get(
                CsvMessage.NOT_EXIST, category5Code)));
      } else {
        // 品店カテゴリID5検索用カテゴリパス
        categorySearchPath5 = object.toString() + "~" + category5Code;
        // 商品编号,品店カテゴリ编号在カテゴリ陳列商品表中是否存在
        existsCategory = existsCategory(category4Code);
        if (!existsCategory) {
          if ((categorySearchPath + "#" + categorySearchPath5).length() < 256) {
            // 商品基本表検索用カテゴリパス
            if (StringUtil.isNullOrEmpty(categorySearchPath)) {
              categorySearchPath = categorySearchPath5;
            } else {
              categorySearchPath = categorySearchPath + "#" + categorySearchPath5;
            }
          }
        }
      }
      // 品店カテゴリID５属性１
      String category5Attribute1 = categoryAttributeValue.getCategory5Attribute1();
      if (!StringUtil.isNullOrEmpty(category5Attribute1)) {
        valueExistsQuery.setParameters(category5Code, 0);
        object = executeScalar(valueExistsQuery);
        if (object == null) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_ATTRIBUTE_NO_NOT_EXIST, category5Code, "0")));
        } else {
          if ((categoryAttribute + "#" + object.toString()).length() < 256) {
            if (StringUtil.isNullOrEmpty(categoryAttribute)) {
              // 商品の分類属性値
              categoryAttribute = object.toString();
            } else {
              // 商品の分類属性値
              categoryAttribute = categoryAttribute + "#" + object.toString();
            }
          }
        }
      }
      // 品店カテゴリID５属性2
      String category5Attribute2 = categoryAttributeValue.getCategory5Attribute2();
      if (!StringUtil.isNullOrEmpty(category5Attribute2)) {
        valueExistsQuery.setParameters(category5Code, 1);
        object = executeScalar(valueExistsQuery);
        if (object == null) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_ATTRIBUTE_NO_NOT_EXIST, category5Code, "1")));
        } else {
          if ((categoryAttribute + "#" + object.toString()).length() < 256) {
            // 商品の分類属性値
            if (StringUtil.isNullOrEmpty(categoryAttribute)) {
              categoryAttribute = object.toString();
            } else {
              if (StringUtil.isNullOrEmpty(category5Attribute1)) {
                categoryAttribute = categoryAttribute + "#" + object.toString();
              } else {
                categoryAttribute = categoryAttribute + "|" + object.toString();
              }
            }
          }
        }
      }
      // 品店カテゴリID５属性3
      String category5Attribute3 = categoryAttributeValue.getCategory5Attribute3();
      if (!StringUtil.isNullOrEmpty(category5Attribute3)) {
        valueExistsQuery.setParameters(category5Code, 2);
        object = executeScalar(valueExistsQuery);
        if (object == null) {
          summary.getErrors().add(
              new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_ATTRIBUTE_NO_NOT_EXIST, category5Code, "2")));
        } else {
          if ((categoryAttribute + "#" + object.toString()).length() < 256) {
            // 商品の分類属性値
            if (StringUtil.isNullOrEmpty(categoryAttribute)) {
              categoryAttribute = object.toString();
            } else {
              if (StringUtil.isNullOrEmpty(category5Attribute1) && StringUtil.isNullOrEmpty(category5Attribute2)) {
                categoryAttribute = categoryAttribute + "#" + object.toString();
              } else {
                categoryAttribute = categoryAttribute + "|" + object.toString();
              }
            }
          }
        }
      }
    }

    // 商品基本表検索用カテゴリパス
    header.setCategorySearchPath(categorySearchPath);
    // 商品の分類属性値
    header.setCategoryAttributeValue(categoryAttribute);
    // // 販売開始日時
    // if (header.getSaleStartDatetime() == null) {
    // header.setSaleStartDatetime(DateUtil.getMin());
    // }
    // header.setSaleStartDatetime(DateUtil.truncateHour(header.getSaleStartDatetime()));
    //
    // // 販売終了日時
    // if (header.getSaleEndDatetime() == null) {
    // header.setSaleEndDatetime(DateUtil.getMax());
    // }
    // header.setSaleEndDatetime(DateUtil.truncateHour(header.getSaleEndDatetime()));
    //
    // // 特別価格開始日時
    // if (header.getDiscountPriceStartDatetime() != null) {
    // header.setDiscountPriceStartDatetime(DateUtil.truncateHour(header.getDiscountPriceStartDatetime()));
    // }
    //
    // // 特別価格終了日時
    // if (header.getDiscountPriceEndDatetime() != null) {
    // header.setDiscountPriceEndDatetime(DateUtil.truncateHour(DateUtil.truncateHour(header.getDiscountPriceEndDatetime())));
    // }

    // EC販売フラグ
    // header.setSaleFlgEc(0L);
    // upd by lc 2012-08-17 start
    header.setSaleFlgEc(1L);
    // upd by lc 2012-08-17 end
    // 返品不可フラグ
    if (header.getReturnFlg() == null) {
      header.setReturnFlg(0L);
    }

    // 商品期限管理フラグ 0管理しない/1賞味期限日/2製造日＋保管日数
    if (header.getShelfLifeFlag() == null) {
      header.setShelfLifeFlag(0L);
    }

    // 大物フラグ
    if (header.getBigFlag() == null) {
      header.setBigFlag(0L);
    }

    // ECへの同期フラグ(0:同期不可、1同期可能、2同期済み)
    if (header.getSyncFlagEc() == null) {
      header.setSyncFlagEc(1L);
    }

    // TMALLへの同期フラグ(0:同期不可、1同期可能、2同期済み)
    if (header.getSyncFlagTmall() == null) {
      header.setSyncFlagTmall(1L);
    }

    // ERP取込用データ対象フラグ(0：対象外、1：対象）
    if (header.getExportFlagErp() == null) {
      header.setExportFlagErp(1L);
    }

    // WMS取込用データ対象フラグ(0：対象外、1：対象）
    if (header.getExportFlagWms() == null) {
      header.setExportFlagWms(1L);
    }
  }

  /**
   * 判断商品详细表必须项是否输入，没输入则设为默认值
   */
  private void setDetail() {
    // ショップコード
    if (detail.getShopCode() == null) {
      detail.setShopCode(config.getSiteShopCode());
    }
    // 定価フラグ0：非定価　1：定価
    if (detail.getFixedPriceFlag() == null) {
      detail.setFixedPriceFlag(0L);
    }
  }

  /**
   * 判断在库表必须项是否输入，没输入则设为默认值
   */
  private void setStock() {
    // ショップコード
    if (stock.getShopCode() == null) {
      stock.setShopCode(config.getSiteShopCode());
    }
    // 引当数量
    if (stock.getAllocatedQuantity() == null) {
      stock.setAllocatedQuantity(0L);
    }
    // 予約数量
    if (stock.getReservedQuantity() == null) {
      stock.setReservedQuantity(0L);
    }
    // 安全在庫
    if (stock.getStockThreshold() == null) {
      stock.setStockThreshold(0L);
    }
    // TMALL引当数
    if (stock.getAllocatedTmall() == null) {
      stock.setAllocatedTmall(0L);
    }
    // 総在庫
    if (stock.getStockTotal() == null) {
      stock.setStockTotal(0L);
    }
    // EC在庫数量
    // EC在庫数量=総在庫-安全在庫,当小于0时EC在庫数量设为0
    if ((stock.getStockTotal() - stock.getStockThreshold()) > 0) {
      stock.setStockQuantity(stock.getStockTotal() - stock.getStockThreshold());
    } else {
      stock.setStockQuantity(0L);
    }
    // TMALL在庫数
    if (stock.getStockTmall() == null) {
      stock.setStockTmall(0L);
    }
    // 在庫リーバランスフラグ
    if (ccommodityExt.getOnStockFlag() == 1L) {
      stock.setShareRecalcFlag(1L);
      // 20120319 os013 add start
      // CSV文件EC在庫割合有值，则用CSV的值。如没有值则从配置文件中取值
      if (stock.getShareRatio() == null) {
        stock.setShareRatio(config.getShareRatioRate());
      }
      // 20120319 os013 add end
    } else if (ccommodityExt.getOnStockFlag() == 2L) {
      stock.setShareRecalcFlag(0L);
      // 非受注発注品时EC在庫割合强制为100
      stock.setShareRatio(100L);
    }
  }

  /**
   * 判断カテゴリ陳列商品表必须项是否输入，没输入则设为默认值
   */
  private void setCategoryCommodity() {
    // ショップコード
    if (categoryCommodity.getShopCode() == null) {
      categoryCommodity.setShopCode(config.getSiteShopCode());
    }
  }

  /**
   * 判断カテゴリ属性値表必须项是否输入，没输入则设为默认值
   */
  private void setCategoryAttributeValue() {
    // ショップコード
    if (categoryAttributeValue.getShopCode() == null) {
      categoryAttributeValue.setShopCode(config.getSiteShopCode());
    }
  }

  /**
   * 判断商品拡張情報表必须项是否输入，没输入则设为默认值
   */
  private void setCCommodityExt() {
    // ショップコード
    if (ccommodityExt.getShopCode() == null) {
      ccommodityExt.setShopCode(config.getSiteShopCode());
    }
  }

  private String getPath() {
    // 查找出同一商品编号所有的path值并以#号连接起
    String headerCategorySearchPath = null;
    @SuppressWarnings("unused")
    CategoryDao catDao = DIContainer.getDao(CategoryDao.class);
    Query query = new SimpleQuery(CatalogQuery.GET_CATEGORY_COMMODITY_INSERT_LIST, config.getSiteShopCode(), header
        .getCommodityCode());

    List<CategoryCommodity> categoryList = loadAsBeanList(query, CategoryCommodity.class);
    int i = 0;
    for (CategoryCommodity category : categoryList) {
      // 2011/04/10 修改patch（没有拼接第三目录） by os011 start
      // Category cat = catDao.load(category.getCategoryCode());
      // if (category == categoryList.get(0)) {
      // headerCategorySearchPath = cat.getPath();
      // } else {
      // if ((headerCategorySearchPath + "#" + cat.getPath()).length() < 256) {
      // headerCategorySearchPath = headerCategorySearchPath + "#" +
      // cat.getPath();
      // }
      // }
      if (i == 0) {
        headerCategorySearchPath = category.getCategorySearchPath();
      } else {
        headerCategorySearchPath += "~" + category.getCategorySearchPath();
      }
      i++;
      // 2011/04/10 修改patch（没有拼接第三目录） by os011 end
    }

    return headerCategorySearchPath;
  }

  private String getValue() {
    // 查找出同一商品编号所有的商品の分類属性値
    String categoryAttributeValue = null;
    Query query = new SimpleQuery(CatalogQuery.GET_CATEGORY_ATTRIBUTE_VALUE_LIST, header.getCommodityCode());
    List<CategoryAttributeValue> list = loadAsBeanList(query, CategoryAttributeValue.class);
    List<CategoryAttributeValue> valueList = new ArrayList<CategoryAttributeValue>();
    String category1Code = categoryCommodity.getCategory1Code();
    String category2Code = categoryCommodity.getCategory2Code();
    String category3Code = categoryCommodity.getCategory3Code();
    String category4Code = categoryCommodity.getCategory4Code();
    String category5Code = categoryCommodity.getCategory5Code();
    for (int i = 0; i < list.size(); i++) {
      // 除csv导入的品店カテゴリ以外商品所有的品店カテゴリ编号
      if (!(list.get(i).getCategoryCode().equals(category1Code)) && !(list.get(i).getCategoryCode().equals(category2Code))
          && !(list.get(i).getCategoryCode().equals(category3Code)) && !(list.get(i).getCategoryCode().equals(category4Code))
          && !(list.get(i).getCategoryCode().equals(category5Code))) {

        valueList.add(list.get(i));
      }
    }
    for (int i = 0; i < valueList.size(); i++) {
      query = new SimpleQuery(CatalogQuery.GET_CATEGORY_ATTRIBUTE_NAME_LIST, valueList.get(i).getCategoryCode(), valueList.get(i)
          .getCategoryAttributeNo());
      CategoryAttribute bean = loadAsBean(query, CategoryAttribute.class);

      if ((i + 1) < list.size()) {
        if (i == 0) {
          categoryAttributeValue = bean.getCategoryAttributeName();
        } else if (i != 0 && list.get(i - 1).getCategoryCode().equals(list.get(i).getCategoryCode())) {
          categoryAttributeValue = categoryAttributeValue + "|" + bean.getCategoryAttributeName();
        } else if (list.get(i).getCategoryCode().equals(list.get(i + 1).getCategoryCode())) {
          categoryAttributeValue = categoryAttributeValue + "#" + bean.getCategoryAttributeName();
        }
      } else {
        if (list.get(i - 1).getCategoryCode().equals(list.get(i).getCategoryCode())) {
          categoryAttributeValue = categoryAttributeValue + "|" + bean.getCategoryAttributeName();
        } else {
          categoryAttributeValue = categoryAttributeValue + "#" + bean.getCategoryAttributeName();
        }
      }
    }
    return categoryAttributeValue;
  }

  public boolean existsCategory(String categoryCode) {
    // 商品编号,品店カテゴリ编号在カテゴリ陳列商品表中是否存在
    boolean category = exists(selectCategoryCommodityStatement, categoryCommodity.getCommodityCode(), categoryCode);
    return category;
  }

  public boolean existsValue(String categoryCode, Long categoryAttributeNo) {
    // 商品编号,品店カテゴリ编号,属性编号在カテゴリ属性値表中是否存在
    boolean value = exists(selectCategoryAttributeValueStatement, categoryAttributeValue.getCommodityCode(), categoryCode,
        categoryAttributeNo);
    return value;
  }

//2014/06/11 库存更新对应 ob_yuan update start
  private boolean checkStockRatio(String stockRatio) {
    if (StringUtil.isNullOrEmpty(stockRatio)) {
      return false;
    } 
    String[] stockRatioArray = stockRatio.split(":");
    if (stockRatioArray==null || stockRatioArray.length!=3) {
      return false;
    }
    Long totalRatio = 0L;
    for (String ratio : stockRatioArray) {
      if(!NumUtil.isNum(ratio)) {
        return false;
      }
      Long lRatio = NumUtil.toLong(ratio);
      if (lRatio<0L || lRatio>100L) {
        return false;
      }
      totalRatio = totalRatio + lRatio;
    }
    if (totalRatio!=100L) {
      return false;
    }
    return true;
  }
//2014/06/11 库存更新对应 ob_yuan update end
}
