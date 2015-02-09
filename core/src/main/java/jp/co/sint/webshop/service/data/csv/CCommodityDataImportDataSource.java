package jp.co.sint.webshop.service.data.csv;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.configure.WebShopSpecialConfig;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.dao.CCommodityDetailDao;
import jp.co.sint.webshop.data.dao.CCommodityHeaderDao;
import jp.co.sint.webshop.data.domain.JdUseFlg;
import jp.co.sint.webshop.data.domain.RatioType;
import jp.co.sint.webshop.data.domain.StockChangeType;
import jp.co.sint.webshop.data.domain.SyncFlagJd;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityPriceChangeHistory;
import jp.co.sint.webshop.data.dto.OriginalPlace;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.CCommodityHeaderImport;
import jp.co.sint.webshop.service.catalog.CommodityPriceChangeHistoryCondition;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.validation.ValidatorUtil;

import org.apache.log4j.Logger;

public class CCommodityDataImportDataSource extends SqlImportDataSource<CCommodityDataCsvSchema, CCommodityDataImportCondition> {

  private static final String DATAIL_TABLE_NAME = DatabaseUtil.getTableName(CCommodityDetail.class);

  // 改价审核用毛利率
  BigDecimal ecGrossProfitRate = null, ecSpecialGrossProfitRate = null, tmallGrossProfitRate = null, jdGrossProfitRate = null;
  
  private static final String HEADER_TABLE_NAME = DatabaseUtil.getTableName(CCommodityHeader.class);
  //20120207 os013 add start
  //在库
  private static final String STOCK_TABLE_NAME = DatabaseUtil.getTableName(Stock.class);
  //20120207 os013 add end

  /** 商品詳細UPDATE用Statement */
  //private PreparedStatement updateDetailStatement = null;

  /** 商品ヘッダUPDATE用Statement */
  private PreparedStatement updateHeaderStatement = null;
  //20120207 os013 add start
  
  /** 在库UPDATE用Statement */
  //private PreparedStatement updateStockStatement = null;

  /**在库SELECT用Statement */
  private PreparedStatement selectStockStatement = null;
  //201202007 os013 add end
  /**商品明细SELECT用Statement */
  private PreparedStatement selectDetailStatement = null;

  /**商品基本SELECT用Statement */
  private PreparedStatement selectHeaderStatement = null;

  private CCommodityHeaderImport header = null;

  private CCommodityDetail detail = null;
  
// 2014/06/11 库存更新对应 ob_yuan add start
  /** 库存取得用SQL */
  private String getStockSql = "SELECT STOCK_THRESHOLD FROM STOCK WHERE SHOP_CODE = ? AND SKU_CODE = ?";
  
  /** 临时库存取得用SQL */
  private String getStockTempSql = "SELECT STOCK_CHANGE_QUANTITY FROM STOCK_TEMP WHERE SHOP_CODE = ? AND SKU_CODE = ? AND STOCK_CHANGE_TYPE = " + StockChangeType.SAFE_ALLOCATED.getValue();
  
  /** 临时库存更新用Statement */
  private PreparedStatement updateStockTempStatement = null;
  
  /** 临时库存更新用SQL */
  private String updateStockTempSql = "UPDATE  STOCK_TEMP SET STOCK_CHANGE_QUANTITY = ? ,UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE SHOP_CODE = ? AND SKU_CODE = ? AND STOCK_CHANGE_TYPE = " + StockChangeType.SAFE_ALLOCATED.getValue();
  
  /** 临时库存登录用Statement */
  private PreparedStatement insertStockTempStatement = null;
  // 2014/06/11 库存更新对应 ob_yuan add end
  
  //20120207 os013 add start
  private Stock stock = null;
  
  boolean existsHeader = false;
  
  boolean existsDetail = false;
  
  boolean existsStock=false;
  
  //用于判断SKU是否同期过
  boolean existsTmallSkuId = false;
  /**在库*/
  private List<CsvColumn> stockColumns = new ArrayList<CsvColumn>();
  
  //20120207 os013 add end
  /**商品基本*/
  private List<CsvColumn> headerColumns = new ArrayList<CsvColumn>();
  
  /**商品明细*/
  private List<CsvColumn> detailColumns = new ArrayList<CsvColumn>();

  // 20130703 shen add start
  private PreparedStatement updateSyncFlag = null;
  // 20130703 shen add end
  // 2014/05/06 京东WBS对应 ob_卢 add start
  private PreparedStatement updateSyncFlagJd = null;
  // 2014/05/06 京东WBS对应 ob_卢 add end
  
  @Override
  protected void initializeResources() {
    Logger logger = Logger.getLogger(this.getClass());
    
//    //商品详细表更新Query
//    logger.debug("UPDATE statement: " + getUpdateDetailQuery());
//    //商品基本表更新Query
//    logger.debug("UPDATE statement: " + getUpdateHeaderQuery());
//    //在库表更新Query
//    logger.debug("UPDATE statement: " + getUpdateStockQuery());
    
    //在库表查询存在
    logger.debug("CHECK  statement: " + getSelectStockQuery());
    //商品详细表查询存在
    logger.debug("CHECK  statement: " + getSelectDetailQuery());
    //商品基本表查询存在
    logger.debug("CHECK  statement: " + getSelectHeaderQuery());
    

    try {
      //商品详细表查询存在
      selectDetailStatement = createPreparedStatement(getSelectDetailQuery());
      //商品基本表查询存在
      selectHeaderStatement = createPreparedStatement(getSelectHeaderQuery());
      //在库表查询存在
      selectStockStatement  = createPreparedStatement(getSelectStockQuery());
      
      // 20130703 shen add start
      updateSyncFlag = createPreparedStatement(getUpdateSyncFlagQuery());
      // 20130703 shen add end
      // 2014/05/06 京东WBS对应 ob_卢 add start
      updateSyncFlagJd = createPreparedStatement(getUpdateSyncFlagJdQuery());
      // 2014/05/06 京东WBS对应 ob_卢 add end
      
      // 2014/06/11 库存更新对应 ob_yuan add start
      /** 临时库存更新用Statement */
      updateStockTempStatement = createPreparedStatement(updateStockTempSql);
   // 2014/06/11 库存更新对应 ob_yuan add end
      
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  // 用于判断csv文件是否有代表SKU単価列
  boolean existsRepresentSkuUnitPrice = false;

  // 用于判断csv文件是否有大物フラグ列
  boolean existsBigflag = false;

  // 用于判断csv文件是否有商品名称列
  boolean existsCommodityName = false;
  boolean existsCommodityNameEn = false;
  boolean existsCommodityNameJp = false;

  // 用于判断csv文件是否有商品説明2列
  boolean existsCommodityDescription2 = false;

  // 用于判断csv文件是否有返品不可フラグ列
//  boolean existsReturnFlg = false;
  boolean existsReturnFlgCust = false;
  boolean existsReturnFlgSupp = false;
  boolean existsChangeFlgSupp = false;
  
  // 用于判断csv文件是否有品牌列
  boolean existsBrandCode = false;

  // 用于判断csv文件是否有ワーニング区分列
  boolean existsWarningFlag = false;

  // 用于判断csv文件是否有SKU名称列
  boolean existsSkuName = false;

  // 用于判断csv文件是否有定価フラグ列
  boolean existsFixedPriceFlag = false;

  // 用于判断csv文件是否有取扱いフラグ(EC)列
  boolean existsUseFlg = false;

  // 用于判断csv文件是否有取扱いフラグ(TMALL)列
  boolean existsTmallUseFlg = false;

  // 2014/04/30 京东WBS对应 ob_卢 add start
  //用于判断csv文件是否有取扱いフラグ(京东)列
  boolean existsJdUseFlg = false;
  // 2014/04/30 京东WBS对应 ob_卢 add end
  
  // 用于判断csv文件是否有商品期限管理列
  boolean existsShelfLifeFlag = false;

  //用于判断csv文件是否有EC販売フラグ列
  boolean existsSaleFlgEc = false;
  
  // 用于判断csv文件是否有取引先コード列
  boolean existsSupplierCode = false;

  // 用于判断csv文件是否有仕入担当者コード列
  boolean existsbuyerCode = false;
  
  // 用于判断csv文件是否有税率区分列
  boolean existsTaxClass = false;
  
  public boolean setSchema(List<String> csvLine) {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    for (String column : csvLine) {
        //ショップコード
      if (column.equals("shop_code")) {
        columns.add(new CsvColumnImpl("shop_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.0"), CsvDataType.STRING, false, false, true, null));
        headerColumns.add(new CsvColumnImpl("shop_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.0"), CsvDataType.STRING, false, false, true, null));
        //商品コード
      } else if (column.equals("commodity_code")) {
        columns.add(new CsvColumnImpl("commodity_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.1"), CsvDataType.STRING, false, false, true, null));
        headerColumns.add(new CsvColumnImpl("commodity_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.1"), CsvDataType.STRING, false, false, true, null));
        detailColumns.add(new CsvColumnImpl("commodity_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.1"), CsvDataType.STRING, false, false, true, null));
        stockColumns.add(new CsvColumnImpl("commodity_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.1"), CsvDataType.STRING, false, false, true, null));
        //商品名称
      } else if (column.equals("commodity_name")) {
        columns.add(new CsvColumnImpl("commodity_name", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.2"), CsvDataType.STRING, false, false, true, null));
        headerColumns.add(new CsvColumnImpl("commodity_name", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.2"), CsvDataType.STRING, false, false, true, null));
        existsCommodityName = true;
        //商品名称英字
      } else if (column.equals("commodity_name_en")) {
        columns.add(new CsvColumnImpl("commodity_name_en",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.3"), CsvDataType.STRING, false, false, true, null));
        headerColumns.add(new CsvColumnImpl("commodity_name_en",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.3"), CsvDataType.STRING, false, false, true, null));
        existsCommodityNameEn = true;
        //商品名称日文
      } else if (column.equals("commodity_name_jp")) {
        columns.add(new CsvColumnImpl("commodity_name_jp",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.86"), CsvDataType.STRING, false, false, true, null));
        headerColumns.add(new CsvColumnImpl("commodity_name_jp",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.86"), CsvDataType.STRING, false, false, true, null));
        existsCommodityNameJp = true;
        //代表SKUコード
      }else if (column.equals("represent_sku_code")) {
        columns.add(new CsvColumnImpl("represent_sku_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.4"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("represent_sku_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.4"), CsvDataType.STRING));
        //代表SKU単価
      } else if (column.equals("represent_sku_unit_price")) {
        columns.add(new CsvColumnImpl("represent_sku_unit_price", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.5"), CsvDataType.BIGDECIMAL));
        headerColumns.add(new CsvColumnImpl("represent_sku_unit_price", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.5"), CsvDataType.BIGDECIMAL));
        existsRepresentSkuUnitPrice = true;
        //商品説明1
      } else if (column.equals("commodity_description1")) {
        columns.add(new CsvColumnImpl("commodity_description1", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.6"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description1", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.6"), CsvDataType.STRING));
        //商品説明1英文
      } else if (column.equals("commodity_description1_en")) {
        columns.add(new CsvColumnImpl("commodity_description1_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.87"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description1_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.87"), CsvDataType.STRING));
        //商品説明1日文
      } else if (column.equals("commodity_description1_jp")) {
        columns.add(new CsvColumnImpl("commodity_description1_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.88"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description1_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.88"), CsvDataType.STRING));
        //商品説明2
      } else if (column.equals("commodity_description2")) {
        columns.add(new CsvColumnImpl("commodity_description2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.7"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.7"), CsvDataType.STRING));
        existsCommodityDescription2 = true;
        //商品説明2英文
      } else if (column.equals("commodity_description2_en")) {
        columns.add(new CsvColumnImpl("commodity_description2_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.89"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description2_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.89"), CsvDataType.STRING));
        //商品説明2日文
      }else if (column.equals("commodity_description2_jp")) {
        columns.add(new CsvColumnImpl("commodity_description2_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.90"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description2_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.90"), CsvDataType.STRING));
        //商品説明3
      } else if (column.equals("commodity_description3")) {
        columns.add(new CsvColumnImpl("commodity_description3", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.8"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description3", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.8"), CsvDataType.STRING));
        //商品説明3英文
      } else if (column.equals("commodity_description3_en")) {
        columns.add(new CsvColumnImpl("commodity_description3_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.91"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description3_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.91"), CsvDataType.STRING));
        //商品説明3日文
      } else if (column.equals("commodity_description3_jp")) {
        columns.add(new CsvColumnImpl("commodity_description3_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.92"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description3_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.92"), CsvDataType.STRING));
        //商品説明(一覧用）
      } else if (column.equals("commodity_description_short")) {
        columns.add(new CsvColumnImpl("commodity_description_short", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.9"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description_short", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.9"), CsvDataType.STRING));
        //商品説明(一覧用）英文
      } else if (column.equals("commodity_description_short_en")) {
        columns.add(new CsvColumnImpl("commodity_description_short_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.93"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description_short_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.93"), CsvDataType.STRING));
        //商品説明(一覧用）日文
      } else if (column.equals("commodity_description_short_jp")) {
        columns.add(new CsvColumnImpl("commodity_description_short_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.94"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_description_short_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.94"), CsvDataType.STRING));
        //商品検索ワード
      } else if (column.equals("commodity_search_words")) {
        columns.add(new CsvColumnImpl("commodity_search_words", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.10"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("commodity_search_words", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.10"),CsvDataType.STRING));
        //販売開始日時
      } else if (column.equals("sale_start_datetime")) {
        columns.add(new CsvColumnImpl("sale_start_datetime", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.11"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("sale_start_datetime", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.11"),CsvDataType.STRING));
        //販売終了日時
      } else if (column.equals("sale_end_datetime")) {
        columns.add(new CsvColumnImpl("sale_end_datetime", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.12"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("sale_end_datetime", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.12"),CsvDataType.STRING));
        //特別価格開始日時
      } else if (column.equals("discount_price_start_datetime")) {
        columns.add(new CsvColumnImpl("discount_price_start_datetime", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.13"),CsvDataType.STRING));        
        headerColumns.add(new CsvColumnImpl("discount_price_start_datetime", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.13"),CsvDataType.STRING));
        //特別価格終了日時
      } else if (column.equals("discount_price_end_datetime")) {
        columns.add(new CsvColumnImpl("discount_price_end_datetime", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.14"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("discount_price_end_datetime", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.14"),CsvDataType.STRING));
        //規格1名称ID(TMALLの属性ID）
      } else if (column.equals("standard1_id")) {
        columns.add(new CsvColumnImpl("standard1_id", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.15"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("standard1_id", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.15"),CsvDataType.STRING));
        //規格1名称
      } else if (column.equals("standard1_name")) {
        columns.add(new CsvColumnImpl("standard1_name", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.16"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("standard1_name", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.16"),CsvDataType.STRING));
        //規格1名称英文
      } else if (column.equals("standard1_name_en")) {
        columns.add(new CsvColumnImpl("standard1_name_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.95"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("standard1_name_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.95"),CsvDataType.STRING));
        //規格1名称日文
      } else if (column.equals("standard1_name_jp")) {
        columns.add(new CsvColumnImpl("standard1_name_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.96"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("standard1_name_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.96"),CsvDataType.STRING));
        //規格2名称ID(TMALLの属性ID）
      } else if (column.equals("standard2_id")) {
        columns.add(new CsvColumnImpl("standard2_id", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.17"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("standard2_id", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.17"),CsvDataType.STRING));
        //規格2名称
      } else if (column.equals("standard2_name")) {
        columns.add(new CsvColumnImpl("standard2_name", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.18"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("standard2_name", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.18"),CsvDataType.STRING));
        //規格2名称英文
      } else if (column.equals("standard2_name_en")) {
        columns.add(new CsvColumnImpl("standard2_name_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.97"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("standard2_name_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.97"),CsvDataType.STRING));
        //規格2名称日文
      } else if (column.equals("standard2_name_jp")) {
        columns.add(new CsvColumnImpl("standard2_name_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.98"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("standard2_name_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.98"),CsvDataType.STRING));
        //EC販売フラグ
      } else if (column.equals("sale_flg_ec")) {
        columns.add(new CsvColumnImpl("sale_flg_ec", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.19"),CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("sale_flg_ec", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.19"),CsvDataType.NUMBER));
        existsSaleFlgEc = true;
        //返品不可フラグ
//      } else if (column.equals("return_flg")) {
//        columns.add(new CsvColumnImpl("return_flg", 
//            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.21"),CsvDataType.NUMBER));
//        headerColumns.add(new CsvColumnImpl("return_flg", 
//            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.21"),CsvDataType.NUMBER));
//        existsReturnFlg = true;
        //供货商换货
      } else if (column.equals("return_flg_cust")) {
        columns.add(new CsvColumnImpl("return_flg_cust", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.83"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("return_flg_cust", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.83"),CsvDataType.STRING));
        existsReturnFlgCust = true;
        //供货商退货
      } else if (column.equals("return_flg_supp")) {
        columns.add(new CsvColumnImpl("return_flg_supp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.84"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("return_flg_supp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.84"),CsvDataType.STRING));
        existsReturnFlgSupp = true;
        //顾客退货
      } else if (column.equals("change_flg_supp")) {
        columns.add(new CsvColumnImpl("change_flg_supp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.85"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("change_flg_supp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.85"),CsvDataType.STRING));
        existsChangeFlgSupp = true;
        //ワーニング区分
      } else if (column.equals("warning_flag")) {
        columns.add(new CsvColumnImpl("warning_flag", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.22"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("warning_flag", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.22"),CsvDataType.STRING));
        existsWarningFlag = true;
        //リードタイム
      } else if (column.equals("lead_time")) {
        columns.add(new CsvColumnImpl("lead_time", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.23"),CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("lead_time", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.23"),CsvDataType.NUMBER));
        //セール区分
      } else if (column.equals("sale_flag")) {
        columns.add(new CsvColumnImpl("sale_flag", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.24"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("sale_flag", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.24"),CsvDataType.STRING));
        //特集区分
      } else if (column.equals("spec_flag")) {
        columns.add(new CsvColumnImpl("spec_flag", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.25"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("spec_flag", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.25"),CsvDataType.STRING));
        //ブランドコード
      } else if (column.equals("brand_code")) {
        columns.add(new CsvColumnImpl("brand_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.26"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("brand_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.26"),CsvDataType.STRING));
        existsBrandCode = true;
        //TMALL商品ID（APIの戻り値）
      } else if (column.equals("tmall_commodity_id")) {
        columns.add(new CsvColumnImpl("tmall_commodity_id", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.27"),CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("tmall_commodity_id", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.27"),CsvDataType.NUMBER));
        //TMALL代表SKU単価
      } else if (column.equals("tmall_represent_sku_price")) {
        columns.add(new CsvColumnImpl("tmall_represent_sku_price", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.28"),CsvDataType.BIGDECIMAL));
        headerColumns.add(new CsvColumnImpl("tmall_represent_sku_price", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.28"),CsvDataType.BIGDECIMAL));
        //TMALLのカテゴリID
      } else if (column.equals("tmall_category_id")) {
        columns.add(new CsvColumnImpl("tmall_category_id", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.29"),CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("tmall_category_id", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.29"),CsvDataType.NUMBER));
        //取引先コード
      } else if (column.equals("supplier_code")) {
        columns.add(new CsvColumnImpl("supplier_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.30"),CsvDataType.STRING, false, false, true, null));
        headerColumns.add(new CsvColumnImpl("supplier_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.30"),CsvDataType.STRING, false, false, true, null));
        existsSupplierCode = true;
        //仕入担当者コード
      } else if (column.equals("buyer_code")) {
        columns.add(new CsvColumnImpl("buyer_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.31"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("buyer_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.31"),CsvDataType.STRING));
        existsbuyerCode = true;
        //産地CODE
      } else if (column.equals("original_code")) {
        columns.add(new CsvColumnImpl("original_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.33"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("original_place", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.33"),CsvDataType.STRING));
      } else if (column.equals("ingredient_name1")) {
        columns.add(new CsvColumnImpl("ingredient_name1",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.34"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name1",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.34"),CsvDataType.STRING));
        //成分量1
      } else if (column.equals("ingredient_val1")) {
        columns.add(new CsvColumnImpl("ingredient_val1", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.35"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val1", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.35"),CsvDataType.STRING));
        //成分2
      } else if (column.equals("ingredient_name2")) {
        columns.add(new CsvColumnImpl("ingredient_name2",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.36"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name2",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.36"),CsvDataType.STRING));
        //成分量2
      } else if (column.equals("ingredient_val2")) {
        columns.add(new CsvColumnImpl("ingredient_val2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.37"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.37"),CsvDataType.STRING));
        //成分3
      } else if (column.equals("ingredient_name3")) {
        columns.add(new CsvColumnImpl("ingredient_name3",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.38"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name3",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.38"),CsvDataType.STRING));
        //成分量3
      } else if (column.equals("ingredient_val3")) {
        columns.add(new CsvColumnImpl("ingredient_val3", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.39"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val3", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.39"),CsvDataType.STRING));
        //成分4
      } else if (column.equals("ingredient_name4")) {
        columns.add(new CsvColumnImpl("ingredient_name4",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.40"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name4",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.40"),CsvDataType.STRING));
        //成分量4
      } else if (column.equals("ingredient_val4")) {
        columns.add(new CsvColumnImpl("ingredient_val4", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.41"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val4", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.41"),CsvDataType.STRING));
        //成分5
      } else if (column.equals("ingredient_name5")) {
        columns.add(new CsvColumnImpl("ingredient_name5",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.42"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name5",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.42"),CsvDataType.STRING));
        //成分量5
      } else if (column.equals("ingredient_val5")) {
        columns.add(new CsvColumnImpl("ingredient_val5", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.43"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val5", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.43"),CsvDataType.STRING));
        //成分6
      } else if (column.equals("ingredient_name6")) {
        columns.add(new CsvColumnImpl("ingredient_name6",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.44"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name6",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.44"),CsvDataType.STRING));
        //成分量6
      } else if (column.equals("ingredient_val6")) {
        columns.add(new CsvColumnImpl("ingredient_val6", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.45"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val6", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.45"),CsvDataType.STRING));
        //成分7
      } else if (column.equals("ingredient_name7")) {
        columns.add(new CsvColumnImpl("ingredient_name7",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.46"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name7",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.46"),CsvDataType.STRING));
        //成分量7
      } else if (column.equals("ingredient_val7")) {
        columns.add(new CsvColumnImpl("ingredient_val7", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.47"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val7", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.47"),CsvDataType.STRING));
        //成分8
      } else if (column.equals("ingredient_name8")) {
        columns.add(new CsvColumnImpl("ingredient_name8",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.48"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name8",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.48"),CsvDataType.STRING));
        //成分量8
      } else if (column.equals("ingredient_val8")) {
        columns.add(new CsvColumnImpl("ingredient_val8", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.49"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val8", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.49"),CsvDataType.STRING));
        //成分9
      } else if (column.equals("ingredient_name9")) {
        columns.add(new CsvColumnImpl("ingredient_name9",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.50"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name9",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.50"),CsvDataType.STRING));
        //成分量9
      } else if (column.equals("ingredient_val9")) {
        columns.add(new CsvColumnImpl("ingredient_val9", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.51"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val9", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.51"),CsvDataType.STRING));
        //成分10
      } else if (column.equals("ingredient_name10")) {
        columns.add(new CsvColumnImpl("ingredient_name10", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.52"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name10", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.52"),CsvDataType.STRING));
        //成分量10
      } else if (column.equals("ingredient_val10")) {
        columns.add(new CsvColumnImpl("ingredient_val10",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.53"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val10",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.53"),CsvDataType.STRING));
        //成分11
      } else if (column.equals("ingredient_name11")) {
        columns.add(new CsvColumnImpl("ingredient_name11", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.54"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name11", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.54"),CsvDataType.STRING));
        //成分量11
      } else if (column.equals("ingredient_val11")) {
        columns.add(new CsvColumnImpl("ingredient_val11",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.55"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val11",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.55"),CsvDataType.STRING));
        //成分12
      } else if (column.equals("ingredient_name12")) {
        columns.add(new CsvColumnImpl("ingredient_name12", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.56"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name12", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.56"),CsvDataType.STRING));
        //成分量12
      } else if (column.equals("ingredient_val12")) {
        columns.add(new CsvColumnImpl("ingredient_val12",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.57"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val12",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.57"),CsvDataType.STRING));
        //成分13
      } else if (column.equals("ingredient_name13")) {
        columns.add(new CsvColumnImpl("ingredient_name13", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.58"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name13", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.58"),CsvDataType.STRING));
        //成分量13
      } else if (column.equals("ingredient_val13")) {
        columns.add(new CsvColumnImpl("ingredient_val13",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.59"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val13",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.59"),CsvDataType.STRING));
        //成分14
      } else if (column.equals("ingredient_name14")) {
        columns.add(new CsvColumnImpl("ingredient_name14", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.60"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name14", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.60"),CsvDataType.STRING));
        //成分量14
      } else if (column.equals("ingredient_val14")) {
        columns.add(new CsvColumnImpl("ingredient_val14",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.61"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val14",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.61"),CsvDataType.STRING));
        //成分15
      } else if (column.equals("ingredient_name15")) {
        columns.add(new CsvColumnImpl("ingredient_name15", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.62"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_name15", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.62"),CsvDataType.STRING));
        //成分量15
      } else if (column.equals("ingredient_val15")) {
        columns.add(new CsvColumnImpl("ingredient_val15",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.63"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("ingredient_val15",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.63"),CsvDataType.STRING));
        //原材料1
      } else if (column.equals("material1")) {
        columns.add(new CsvColumnImpl("material1", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.64"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material1", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.64"),CsvDataType.STRING));
        //原材料2
      } else if (column.equals("material2")) {
        columns.add(new CsvColumnImpl("material2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.65"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.65"),CsvDataType.STRING));
        //原材料3
      } else if (column.equals("material3")) {
        columns.add(new CsvColumnImpl("material3", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.66"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material3", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.66"),CsvDataType.STRING));
        //原材料4
      } else if (column.equals("material4")) {
        columns.add(new CsvColumnImpl("material4", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.67"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material4", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.67"),CsvDataType.STRING));
        //原材料5
      } else if (column.equals("material5")) {
        columns.add(new CsvColumnImpl("material5", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.68"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material5", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.68"),CsvDataType.STRING));
        //原材料6
      } else if (column.equals("material6")) {
        columns.add(new CsvColumnImpl("material6", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.69"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material6", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.69"),CsvDataType.STRING));
        //原材料7
      } else if (column.equals("material7")) {
        columns.add(new CsvColumnImpl("material7", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.70"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material7", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.70"),CsvDataType.STRING));
        //原材料8
      } else if (column.equals("material8")) {
        columns.add(new CsvColumnImpl("material8", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.71"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material8", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.71"),CsvDataType.STRING));
        //原材料9
      } else if (column.equals("material9")) {
        columns.add(new CsvColumnImpl("material9", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.72"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material9", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.72"),CsvDataType.STRING));
        //原材料10
      } else if (column.equals("material10")) {
        columns.add(new CsvColumnImpl("material10", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.73"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material10", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.73"),CsvDataType.STRING));
        //原材料11
      } else if (column.equals("material11")) {
        columns.add(new CsvColumnImpl("material11", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.74"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material11", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.74"),CsvDataType.STRING));
        //原材料12
      } else if (column.equals("material12")) {
        columns.add(new CsvColumnImpl("material12", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.75"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material12", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.75"),CsvDataType.STRING));
        //原材料13
      } else if (column.equals("material13")) {
        columns.add(new CsvColumnImpl("material13", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.76"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material13", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.76"),CsvDataType.STRING));
        //原材料14
      } else if (column.equals("material14")) {
        columns.add(new CsvColumnImpl("material14", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.77"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material14", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.77"),CsvDataType.STRING));
        //原材料15
      } else if (column.equals("material15")) {
        columns.add(new CsvColumnImpl("material15", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.78"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("material15", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.78"),CsvDataType.STRING));
      } else if (column.equals("food_security_prd_license_no")) { //生产许可证号
        columns.add(new CsvColumnImpl("food_security_prd_license_no", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.104"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_prd_license_no", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.104"),CsvDataType.STRING));
      } else if (column.equals("food_security_design_code")) { //产品标准号
        columns.add(new CsvColumnImpl("food_security_design_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.105"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_design_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.105"),CsvDataType.STRING));
      } else if (column.equals("food_security_factory")) { //厂名
        columns.add(new CsvColumnImpl("food_security_factory", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.106"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_factory", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.106"),CsvDataType.STRING));
      } else if (column.equals("food_security_factory_site")) { //厂址
        columns.add(new CsvColumnImpl("food_security_factory_site", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.107"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_factory_site", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.107"),CsvDataType.STRING));
      } else if (column.equals("food_security_contact")) { //厂家联系方式
        columns.add(new CsvColumnImpl("food_security_contact", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.108"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_contact", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.108"),CsvDataType.STRING));
      } else if (column.equals("food_security_mix")) { //配料表
        columns.add(new CsvColumnImpl("food_security_mix", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.109"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_mix", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.109"),CsvDataType.STRING));
      } else if (column.equals("food_security_plan_storage")) { //储藏方法
        columns.add(new CsvColumnImpl("food_security_plan_storage", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.110"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_plan_storage", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.110"),CsvDataType.STRING));
      } else if (column.equals("food_security_period")) { //保质期
        columns.add(new CsvColumnImpl("food_security_period", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.111"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_period", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.111"),CsvDataType.STRING));
      } else if (column.equals("food_security_food_additive")) { //食品添加剂
        columns.add(new CsvColumnImpl("food_security_food_additive", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.112"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_food_additive", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.112"),CsvDataType.STRING));
      } else if (column.equals("food_security_supplier")) { //供货商
        columns.add(new CsvColumnImpl("food_security_supplier", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.113"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_supplier", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.113"),CsvDataType.STRING));
      } else if (column.equals("food_security_product_date_start")) { //生产开始日期
        columns.add(new CsvColumnImpl("food_security_product_date_start", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.114"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_product_date_start", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.114"),CsvDataType.STRING));
      } else if (column.equals("food_security_product_date_end")) { //生产结束日期
        columns.add(new CsvColumnImpl("food_security_product_date_end", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.115"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_product_date_end", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.115"),CsvDataType.STRING));
      } else if (column.equals("food_security_stock_date_start")) { //进货开始日期
        columns.add(new CsvColumnImpl("food_security_stock_date_start", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.116"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_stock_date_start", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.116"),CsvDataType.STRING));
      } else if (column.equals("food_security_stock_date_end")) { //进货结束日期
        columns.add(new CsvColumnImpl("food_security_stock_date_end", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.117"),CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("food_security_stock_date_end", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.117"),CsvDataType.STRING));
      } else if (column.equals("in_bound_life_days")) { //入库效期
        columns.add(new CsvColumnImpl("in_bound_life_days", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.121"),CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("in_bound_life_days", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.121"),CsvDataType.NUMBER));
      } else if (column.equals("out_bound_life_days")) { //出库效期
        columns.add(new CsvColumnImpl("out_bound_life_days", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.122"),CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("out_bound_life_days", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.122"),CsvDataType.NUMBER));
      } else if (column.equals("shelf_life_alert_days")) { //失效预警
        columns.add(new CsvColumnImpl("shelf_life_alert_days", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.123"),CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("shelf_life_alert_days", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.123"),CsvDataType.NUMBER));
      } else if (column.equals("tmall_mjs_flg")) { // tmall满就送(赠品标志)0:非赠品 1:赠品
        columns.add(new CsvColumnImpl("tmall_mjs_flg", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.124"),CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("tmall_mjs_flg", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.124"),CsvDataType.NUMBER));
      } else if (column.equals("import_commodity_type")) { // 进口商品区分(1:特别推荐、2:全进口、3:海外品品牌、4:普通国产)
        columns.add(new CsvColumnImpl("import_commodity_type", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.125"), CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("import_commodity_type", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.125"),CsvDataType.NUMBER));
      } else if (column.equals("clear_commodity_type")) { //  清仓商品区分(1:清仓商品、2:普通商品)
        columns.add(new CsvColumnImpl("clear_commodity_type", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.126"), CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("clear_commodity_type", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.126"), CsvDataType.NUMBER));
      } else if (column.equals("reserve_commodity_type1")) { // Asahi商品区分  1:是 9:默认不是
        columns.add(new CsvColumnImpl("reserve_commodity_type1", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.127"), CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("reserve_commodity_type1", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.127"), CsvDataType.NUMBER));
      }  else if (column.equals("reserve_commodity_type2")) { // "hot商品区分(只能为1:是、9:默认不是)"));
          columns.add(new CsvColumnImpl("reserve_commodity_type2", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.128"), CsvDataType.NUMBER));
              headerColumns.add(new CsvColumnImpl("reserve_commodity_type2", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.128"), CsvDataType.NUMBER));
      }  else if (column.equals("reserve_commodity_type3")) { // "商品展示区分(只能为1:是、9:默认不是)"));
          columns.add(new CsvColumnImpl("reserve_commodity_type3", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.129"), CsvDataType.NUMBER));
              headerColumns.add(new CsvColumnImpl("reserve_commodity_type3", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.129"), CsvDataType.NUMBER));
      }  else if (column.equals("new_reserve_commodity_type1")) { // "预留区分1*(只能为1:是、9:默认不是)"));
          columns.add(new CsvColumnImpl("new_reserve_commodity_type1", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.130"), CsvDataType.NUMBER));
              headerColumns.add(new CsvColumnImpl("new_reserve_commodity_type1", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.130"), CsvDataType.NUMBER));
      }  else if (column.equals("new_reserve_commodity_type2")) { //"预留区分2*(只能为1:是、9:默认不是)"));
          columns.add(new CsvColumnImpl("new_reserve_commodity_type2", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.131"), CsvDataType.NUMBER));
              headerColumns.add(new CsvColumnImpl("new_reserve_commodity_type2", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.131"), CsvDataType.NUMBER));
      }  else if (column.equals("new_reserve_commodity_type3")) { // "预留区分3*(只能为1:是、9:默认不是)"));
          columns.add(new CsvColumnImpl("new_reserve_commodity_type3", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.132"), CsvDataType.NUMBER));
              headerColumns.add(new CsvColumnImpl("new_reserve_commodity_type3", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.132"), CsvDataType.NUMBER));
      }  else if (column.equals("new_reserve_commodity_type4")) { // "预留区分4*(只能为1:是、9:默认不是)"));
          columns.add(new CsvColumnImpl("new_reserve_commodity_type4", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.133"), CsvDataType.NUMBER));
              headerColumns.add(new CsvColumnImpl("new_reserve_commodity_type4", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.133"), CsvDataType.NUMBER));
      }  else if (column.equals("new_reserve_commodity_type5")) { // "预留区分5*(只能为1:是、9:默认不是)"));
          columns.add(new CsvColumnImpl("new_reserve_commodity_type5", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.134"), CsvDataType.NUMBER));
              headerColumns.add(new CsvColumnImpl("new_reserve_commodity_type5", 
                  Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.134"), CsvDataType.NUMBER));
      }  else if (column.equals("tmall_commodity_search_words")) { // TMALL检索关键字
        columns.add(new CsvColumnImpl("tmall_commodity_search_words", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.135"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("tmall_commodity_search_words", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.135"), CsvDataType.STRING));
      }  else if (column.equals("hot_flg_en")) { // English热卖标志
        columns.add(new CsvColumnImpl("hot_flg_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.136"), CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("hot_flg_en", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.136"), CsvDataType.NUMBER));
      }  else if (column.equals("hot_flg_jp")) { // Japan热卖标志
        columns.add(new CsvColumnImpl("hot_flg_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.137"), CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("hot_flg_jp", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.137"), CsvDataType.NUMBER));
      }else if (column.equals("original_commodity_code")) { // 原商品编号
        columns.add(new CsvColumnImpl("original_commodity_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.147"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("original_commodity_code", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.147"), CsvDataType.STRING));
      }else if (column.equals("combination_amount")) { // 组合数
        columns.add(new CsvColumnImpl("combination_amount", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.148"), CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("combination_amount", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.148"), CsvDataType.NUMBER));
      } else if (column.equals("keyword_cn2")) { // 検索Keyword中文
        columns.add(new CsvColumnImpl("keyword_cn2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.149"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("keyword_cn2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.149"), CsvDataType.STRING));
      } else if (column.equals("keyword_jp2")) { // 検索Keyword日文
        columns.add(new CsvColumnImpl("keyword_jp2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.150"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("keyword_jp2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.150"), CsvDataType.STRING));
      } else if (column.equals("keyword_en2")) { // 検索Keyword英文
        columns.add(new CsvColumnImpl("keyword_en2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.151"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("keyword_en2", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.151"), CsvDataType.STRING));
      } else if (column.equals("shelf_life_flag")) {
      //商品期限管理フラグ 0管理しない/1賞味期限日/2製造日＋保管日数
        columns.add(new CsvColumnImpl("shelf_life_flag", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.79"),CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("shelf_life_flag", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.79"),CsvDataType.NUMBER));
        existsShelfLifeFlag = true;
        //保管日数
      } else if (column.equals("shelf_life_days")) {
        columns.add(new CsvColumnImpl("shelf_life_days", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.80"),CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("shelf_life_days", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.80"),CsvDataType.NUMBER));
        //大物フラグ
      } else if (column.equals("big_flag")) {
        columns.add(new CsvColumnImpl("big_flag", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.82"),CsvDataType.NUMBER));
        headerColumns.add(new CsvColumnImpl("big_flag", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.82"),CsvDataType.NUMBER));
        existsBigflag = true;
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
      // 2014/04/29 京东WBS对应 ob_卢 add end
        //SKUコード
      } else if (column.equals("sku_code")) {
        columns.add(new CsvColumnImpl("sku_code",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.0"), CsvDataType.STRING, false, false, true, null));
        detailColumns.add(new CsvColumnImpl("sku_code",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.0"), CsvDataType.STRING, false, false, true, null));
        stockColumns.add(new CsvColumnImpl("sku_code",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.0"), CsvDataType.STRING, false, false, true, null));
        //SKU名称
      } else if (column.equals("sku_name")) {
        columns.add(new CsvColumnImpl("sku_name",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.1"), CsvDataType.STRING, false, false, true, null));
        detailColumns.add(new CsvColumnImpl("sku_name",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.1"), CsvDataType.STRING, false, false, true, null));
        existsSkuName = true;
        //定価フラグ0：非定価　1：定価
      } else if (column.equals("fixed_price_flag")) {
        columns.add(new CsvColumnImpl("fixed_price_flag",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.3"), CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("fixed_price_flag",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.3"), CsvDataType.NUMBER));
        existsFixedPriceFlag = true;
        //希望小売価格
      } else if (column.equals("suggeste_price")) {
        columns.add(new CsvColumnImpl("suggeste_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.4"), CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("suggeste_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.4"), CsvDataType.BIGDECIMAL));
        //仕入価格
      } else if (column.equals("purchase_price")) {
        columns.add(new CsvColumnImpl("purchase_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.5"), CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("purchase_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.5"), CsvDataType.BIGDECIMAL));
        //EC商品単価
      } else if (column.equals("unit_price")) {
        columns.add(new CsvColumnImpl("unit_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.6"), CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("unit_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.6"), CsvDataType.BIGDECIMAL));
        //EC特別価格
      } else if (column.equals("discount_price")) {
        columns.add(new CsvColumnImpl("discount_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.7"), CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("discount_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.7"), CsvDataType.BIGDECIMAL));
        //規格1値のID(=TMALL属性値ID)
      } else if (column.equals("standard_detail1_id")) {
        columns.add(new CsvColumnImpl("standard_detail1_id",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.8"), CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("standard_detail1_id",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.8"), CsvDataType.STRING));
        //規格1値の文字列(値のIDなければ、これを利用）
      } else if (column.equals("standard_detail1_name")) {
        columns.add(new CsvColumnImpl("standard_detail1_name",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.9"), CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("standard_detail1_name",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.9"), CsvDataType.STRING));
        //規格1値のID(=TMALL属性値ID)英文
      } else if (column.equals("standard_detail1_name_en")) {
        columns.add(new CsvColumnImpl("standard_detail1_name_en",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.99"), CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("standard_detail1_name_en",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.99"), CsvDataType.STRING));
        //規格1値のID(=TMALL属性値ID)日文
      } else if (column.equals("standard_detail1_name_jp")) {
        columns.add(new CsvColumnImpl("standard_detail1_name_jp",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.100"), CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("standard_detail1_name_jp",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.100"), CsvDataType.STRING));
        //規格2値のID(=TMALL属性値ID)
      } else if (column.equals("standard_detail2_id")) {
        columns.add(new CsvColumnImpl("standard_detail2_id",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.10"),CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("standard_detail2_id",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.10"),CsvDataType.STRING));
        //規格2値の文字列(値のIDなければ、これを利用）
      } else if (column.equals("standard_detail2_name")) {
        columns.add(new CsvColumnImpl("standard_detail2_name",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.11"),CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("standard_detail2_name",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.11"),CsvDataType.STRING));
        //規格2値の文字列(値のIDなければ、これを利用）英文
      } else if (column.equals("standard_detail2_name_en")) {
        columns.add(new CsvColumnImpl("standard_detail2_name_en",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.101"),CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("standard_detail2_name_en",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.101"),CsvDataType.STRING));
        //規格2値の文字列(値のIDなければ、これを利用）日文
      } else if (column.equals("standard_detail2_name_jp")) {
        columns.add(new CsvColumnImpl("standard_detail2_name_jp",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.102"),CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("standard_detail2_name_jp",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.102"),CsvDataType.STRING));
        //商品重量(単位はKG)、未設定の場合、商品HEADの重量を利用。
      } else if (column.equals("weight")) {
        columns.add(new CsvColumnImpl("weight",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.12"),CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("weight",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.12"),CsvDataType.BIGDECIMAL));
        //容量
      } else if (column.equals("volume")) {
        columns.add(new CsvColumnImpl("volume",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.13"),CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("volume",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.13"),CsvDataType.BIGDECIMAL));
        //容量単位
      } else if (column.equals("volume_unit")) {
        columns.add(new CsvColumnImpl("volume_unit",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.14"),CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("volume_unit",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.14"),CsvDataType.STRING));
        //取扱いフラグ(EC)
      } else if (column.equals("use_flg")) {
        columns.add(new CsvColumnImpl("use_flg",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.15"),CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("use_flg",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.15"),CsvDataType.NUMBER));
        existsUseFlg = true;
        //最小仕入数
      } else if (column.equals("minimum_order")) {
        columns.add(new CsvColumnImpl("minimum_order",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.16"),CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("minimum_order",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.16"),CsvDataType.NUMBER));
        //最大仕入数
      } else if (column.equals("maximum_order")) {
        columns.add(new CsvColumnImpl("maximum_order",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.17"),CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("maximum_order",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.17"),CsvDataType.NUMBER));
        //最小単位の仕入数
      } else if (column.equals("order_multiple")) {
        columns.add(new CsvColumnImpl("order_multiple",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.18"),CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("order_multiple",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.18"),CsvDataType.NUMBER));
        //在庫警告日数
      } else if (column.equals("stock_warning")) {
        columns.add(new CsvColumnImpl("stock_warning",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.19"),CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("stock_warning",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.19"),CsvDataType.NUMBER));
        //TMALLのSKUのID
      } else if (column.equals("tmall_sku_id")) {
        columns.add(new CsvColumnImpl("tmall_sku_id",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.20"),CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("tmall_sku_id",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.20"),CsvDataType.NUMBER));
        //TMALLの商品単価
      } else if (column.equals("tmall_unit_price")) {
        columns.add(new CsvColumnImpl("tmall_unit_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.21"),CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("tmall_unit_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.21"),CsvDataType.BIGDECIMAL));
        //TMALLの特別価格
      } else if (column.equals("tmall_discount_price")) {
        columns.add(new CsvColumnImpl("tmall_discount_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.22"),CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("tmall_discount_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.22"),CsvDataType.BIGDECIMAL));
        //下限売価
      } else if (column.equals("min_price")) {
        columns.add(new CsvColumnImpl("min_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.23"),CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("min_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.23"),CsvDataType.BIGDECIMAL));
        //取扱いフラグ(TMALL)
      } else if (column.equals("tmall_use_flg")) {
        columns.add(new CsvColumnImpl("tmall_use_flg",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.31"),CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("tmall_use_flg",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.31"),CsvDataType.NUMBER));
        existsTmallUseFlg=true;
        //入数
      } else if (column.equals("inner_quantity")) {
        columns.add(new CsvColumnImpl("inner_quantity",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.27"),CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("inner_quantity",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.27"),CsvDataType.NUMBER));
        //入数単位
      } else if (column.equals("inner_quantity_unit")) {
        columns.add(new CsvColumnImpl("inner_quantity_unit",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.28"),CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("inner_quantity_unit",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.28"),CsvDataType.STRING));
        //WEB表示単価単位入数
      } else if (column.equals("inner_unit_for_price")) {
        columns.add(new CsvColumnImpl("inner_unit_for_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.29"),CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("inner_unit_for_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.29"),CsvDataType.NUMBER));
        //WEB表示単価単位容量
      } else if (column.equals("volume_unit_for_price")) {
        columns.add(new CsvColumnImpl("volume_unit_for_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.30"),CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("volume_unit_for_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.30"),CsvDataType.BIGDECIMAL));
       //税率区分
      }else if(column.equals("tax_class")){
        columns.add(new CsvColumnImpl("tax_class",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.32"),CsvDataType.STRING));
        detailColumns.add(new CsvColumnImpl("tax_class",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.32"),CsvDataType.STRING));
        existsTaxClass = true;
        //箱规
      } else if(column.equals("unit_box")){
        columns.add(new CsvColumnImpl("unit_box",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.103"), CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("unit_box",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.103"), CsvDataType.NUMBER));
      // 2014/04/29 京东WBS对应 ob_卢 add start
      } else if(column.equals("jd_use_flg")){
        columns.add(new CsvColumnImpl("jd_use_flg",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.104"), CsvDataType.NUMBER));
        detailColumns.add(new CsvColumnImpl("jd_use_flg",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.104"), CsvDataType.NUMBER));
        existsJdUseFlg=true;
      // 2014/04/29 京东WBS对应 ob_卢 add end
        //在庫数量
      } else if(column.equals("average_cost")){
        columns.add(new CsvColumnImpl("average_cost",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.105"), CsvDataType.BIGDECIMAL));
        detailColumns.add(new CsvColumnImpl("average_cost",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.105"), CsvDataType.BIGDECIMAL));
      } else if (column.equals("stock_quantity")) {
        columns.add(new CsvColumnImpl("stock_quantity",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.2"), CsvDataType.NUMBER));
        stockColumns.add(new CsvColumnImpl("stock_quantity",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.2"), CsvDataType.NUMBER));
        //引当数量
      } else if (column.equals("allocated_quantity")) {
        columns.add(new CsvColumnImpl("allocated_quantity",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.3"), CsvDataType.NUMBER));
        stockColumns.add(new CsvColumnImpl("allocated_quantity",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.3"), CsvDataType.NUMBER));
        //予約数量
      } else if (column.equals("reserved_quantity")) {
        columns.add(new CsvColumnImpl("reserved_quantity",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.4"), CsvDataType.NUMBER));
        stockColumns.add(new CsvColumnImpl("reserved_quantity",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.4"), CsvDataType.NUMBER));
        //予約上限数
      } else if (column.equals("reservation_limit")) {
        columns.add(new CsvColumnImpl("reservation_limit",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.5"), CsvDataType.NUMBER));
        stockColumns.add(new CsvColumnImpl("reservation_limit",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.5"), CsvDataType.NUMBER));
        //注文毎予約上限数
      } else if (column.equals("oneshot_reservation_limit")) {
        columns.add(new CsvColumnImpl("oneshot_reservation_limit",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.6"), CsvDataType.NUMBER));
        stockColumns.add(new CsvColumnImpl("oneshot_reservation_limit",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.6"), CsvDataType.NUMBER));
        //在庫閾値
      } else if (column.equals("stock_threshold")) {
        columns.add(new CsvColumnImpl("stock_threshold",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.7"), CsvDataType.NUMBER));
        stockColumns.add(new CsvColumnImpl("stock_threshold",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.7"), CsvDataType.NUMBER));
        //総在庫
      }else if (column.equals("stock_total")) {
        columns.add(new CsvColumnImpl("stock_total",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.8"), CsvDataType.NUMBER));
        stockColumns.add(new CsvColumnImpl("stock_total",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.8"), CsvDataType.NUMBER));
        //TMALL在庫数
      } else if (column.equals("stock_tmall")) {
        columns.add(new CsvColumnImpl("stock_tmall",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.9"), CsvDataType.NUMBER));
        stockColumns.add(new CsvColumnImpl("stock_tmall",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.9"), CsvDataType.NUMBER));
        //TMALL引当数
      } else if (column.equals("allocated_tmall")) {
        columns.add(new CsvColumnImpl("allocated_tmall",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.10"),CsvDataType.NUMBER));
        stockColumns.add(new CsvColumnImpl("allocated_tmall",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.10"),CsvDataType.NUMBER));
        //在庫リーバランスフラグ
      } else if (column.equals("share_recalc_flag")) {
        columns.add(new CsvColumnImpl("share_recalc_flag",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.11"),CsvDataType.NUMBER));
        stockColumns.add(new CsvColumnImpl("share_recalc_flag",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.11"),CsvDataType.NUMBER));
        //EC在庫割合(0-100)
      } else if (column.equals("share_ratio")) {
     // 2014/06/11 库存更新对应 ob_yuan update start
        //columns.add(new CsvColumnImpl("share_ratio",
        //    Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.12"),CsvDataType.NUMBER));
        //stockColumns.add(new CsvColumnImpl("share_ratio",
        //    Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.12"),CsvDataType.NUMBER));
        columns.add(new CsvColumnImpl("stock_ratio",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.12"),CsvDataType.STRING));
     // 2014/06/11 库存更新对应 ob_yuan update end
        //
        // 20130809 txw add start
      } else if (column.equals("title")) { // TITLE
        columns.add(new CsvColumnImpl("title", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.138"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("title", 
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.138"), CsvDataType.STRING));
      } else if (column.equals("title_en")) { // TITLE(英文)
        columns.add(new CsvColumnImpl("title_en",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.139"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("title_en",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.139"), CsvDataType.STRING));
      } else if (column.equals("title_jp")) { // TITLE(日文)
        columns.add(new CsvColumnImpl("title_jp",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.140"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("title_jp",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.140"), CsvDataType.STRING));
      } else if (column.equals("description")) { // description
        columns.add(new CsvColumnImpl("description",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.141"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("description",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.141"), CsvDataType.STRING));
      } else if (column.equals("description_en")) { // description(英文)
        columns.add(new CsvColumnImpl("description_en",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.142"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("description_en",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.142"), CsvDataType.STRING));
      } else if (column.equals("description_jp")) { // description(日文)
        columns.add(new CsvColumnImpl("description_jp",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.143"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("description_jp",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.143"), CsvDataType.STRING));
      } else if (column.equals("keyword")) { // keyword
        columns.add(new CsvColumnImpl("keyword",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.144"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("keyword",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.144"), CsvDataType.STRING));
      } else if (column.equals("keyword_en")) { // keyword(英文)
        columns.add(new CsvColumnImpl("keyword_en",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.145"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("keyword_en",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.145"), CsvDataType.STRING));
      } else if (column.equals("keyword_jp")) { // keyword(日文)
        columns.add(new CsvColumnImpl("keyword_jp",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.146"), CsvDataType.STRING));
        headerColumns.add(new CsvColumnImpl("keyword_jp",
            Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.146"), CsvDataType.STRING));
      }
      // 20130809 txw add end
    }    
    getSchema().setColumns(columns);
    try {
      //商品详细表更新Query
      //updateDetailStatement = createPreparedStatement(getUpdateDetailQuery());
      //商品基本表更新Query
      updateHeaderStatement = createPreparedStatement(getUpdateHeaderQuery());
      //在库表更新Query
      //updateStockStatement  = createPreparedStatement(getUpdateStockQuery());

    } catch (Exception e) {
      throw new DataAccessException(e);
    }
    return true;
  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();
    boolean ecGrossProfitRateLessThan0 = false, ecSpecialPriceGrossProfitRateLessThan0 = false, tmallPriceGrossProfitRateLessThan0 = false, jdPriceGrossProfitRateLessThan0 = false;
    try {
      header = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CCommodityHeaderImport.class);
      detail = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CCommodityDetail.class);
      stock  = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), Stock.class);
      //商品编号必须输入
      if(header.getCommodityCode()==null){
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM,Messages
            .getString("service.data.csv.CCommodityHeaderCsvSchema.1"))));
        return summary;
      }
      //SKU编号必须输入
      if(detail.getSkuCode()==null){
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM,Messages
            .getString("service.data.csv.CCommodityDetailCsvSchema.0"))));
        return summary;
      }
      
      //JD售价必须输入
//      if(detail.getTmallDiscountPrice()==null){
//        summary.getErrors().add(new ValidationResult(null, null, "JD售价必须输入"));
//        return summary;
//      }

      /**************************check毛利率，如果导入价格导致毛利率<0则报错（毛利率 = （售价-平均移动成本*（1+税率））/售价）**********************************/
      CatalogService service = ServiceLocator.getCatalogService(getCondition().getLoginInfo());
      BigDecimal newEcDiscountPriceReadyForCheck = detail.getDiscountPrice();
      // 后台的tmall特价就是前台的jd售价
      BigDecimal newJdPriceReadyForCheck = detail.getTmallDiscountPrice();
      BigDecimal newEcPricePriceReadyForCheck = detail.getUnitPrice();
      BigDecimal newTmallPriceReadyForCheck = detail.getTmallUnitPrice();
      SearchResult<CCommodityDetail> cCommodityDetailSearchResult = service.searchCCommodityDetail(detail.getSkuCode().replace("'", ""));
      SearchResult<CCommodityHeader> cCommodityHeaderSearchResult = service.searchCCommodityHeader(detail.getSkuCode().replace("'", ""));

      // 如果数据库中此商品有“平均计算成本”这个字段 或者 Csv导入文件中此商品含有"平均计算成本"，才check毛利率
      if (cCommodityDetailSearchResult.getRows().get(0).getAverageCost() != null || detail.getAverageCost() != null) {
        // 不是清仓商品，才check毛利率。
        if ((header.getClearCommodityType() != null && header.getClearCommodityType() != 1)
            || (header.getClearCommodityType() == null && cCommodityHeaderSearchResult.getRows().get(0).getClearCommodityType() != 1)) {
          // check Ec售价的毛利率
          // 如果有Ec特价（且在特价期间内）的话，按Ec特价算毛利率，不check Ec售价。
          if (detail.getDiscountPrice() != null
              && ((cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime() != null
              && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime() != null
              && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime().before(DateUtil.getSysdate()) 
              && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime().after(DateUtil.getSysdate()))
              || (cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime() != null
                  && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime() == null 
                  && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime().before(DateUtil.getSysdate()))
              || (cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime() == null
                  && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime() != null 
                  && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime().after(DateUtil.getSysdate())))) {
            ; // Do nothing
          } else {
            if (newEcPricePriceReadyForCheck != null) {
              ecGrossProfitRate = calculateGrossProfitRate(newEcPricePriceReadyForCheck, service, detail);
              // 毛利率小于0，报错
              if (ecGrossProfitRate.compareTo(new BigDecimal(0)) == -1) {
                ecGrossProfitRateLessThan0 = true;
              }
            }
            else if(cCommodityDetailSearchResult.getRows().get(0).getUnitPrice() != null) {
              ecGrossProfitRate = calculateGrossProfitRate(cCommodityDetailSearchResult.getRows().get(0).getUnitPrice(), service, detail);
            }
            else {
              ecGrossProfitRate = null;
            }
          }

          // check Ec特价的毛利率
          if ((cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime() != null
              && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime() != null
              && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime().before(DateUtil.getSysdate()) 
              && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime().after(DateUtil.getSysdate()))
              || (cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime() != null
                  && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime() == null 
                  && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime().before(DateUtil.getSysdate()))
              || (cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime() == null
                  && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime() != null 
                  && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime().after(DateUtil.getSysdate()))) {
            if (newEcDiscountPriceReadyForCheck != null) {
              ecSpecialGrossProfitRate = calculateGrossProfitRate(newEcDiscountPriceReadyForCheck, service, detail);
              // 毛利率小于0，报错
              if (ecSpecialGrossProfitRate.compareTo(new BigDecimal(0)) == -1) {
                ecSpecialPriceGrossProfitRateLessThan0 = true;
              }
            }
            else if(cCommodityDetailSearchResult.getRows().get(0).getDiscountPrice() != null) {
              ecSpecialGrossProfitRate = calculateGrossProfitRate(cCommodityDetailSearchResult.getRows().get(0).getDiscountPrice(), service, detail);
            }
            else {
              ecSpecialGrossProfitRate = null;
            }
          }

          // check Tmall售价的毛利率
          if (newTmallPriceReadyForCheck != null) {
            tmallGrossProfitRate = calculateGrossProfitRate(newTmallPriceReadyForCheck, service, detail);
            // 毛利率小于0，报错
            if (tmallGrossProfitRate.compareTo(new BigDecimal(0)) == -1) {
              tmallPriceGrossProfitRateLessThan0 = true;
            }
          }
          else if(cCommodityDetailSearchResult.getRows().get(0).getTmallUnitPrice() != null) {
            tmallGrossProfitRate = calculateGrossProfitRate(cCommodityDetailSearchResult.getRows().get(0).getTmallUnitPrice(), service, detail);
          }
          else {
            tmallGrossProfitRate = null;
          }

          // check JD售价的毛利率
          if (newJdPriceReadyForCheck != null) {
            jdGrossProfitRate = calculateGrossProfitRate(newJdPriceReadyForCheck, service, detail);
            // 毛利率小于0，报错
            if (jdGrossProfitRate.compareTo(new BigDecimal(0)) == -1) {
              jdPriceGrossProfitRateLessThan0 = true;
            }
          }
          else if(cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice() != null) {
            if((header.getClearCommodityType() != null && header.getClearCommodityType() != 1)
                || (header.getClearCommodityType() == null && cCommodityHeaderSearchResult.getRows().get(0).getClearCommodityType() != 1)) {
              jdGrossProfitRate = calculateGrossProfitRate(cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice(), service, detail);
            }
          }
          else {
            jdGrossProfitRate = null;
          }
        }
      }
      /*************************check毛利率，如果导入价格导致毛利率<0则报错（（售价-平均移动成本*（1+税率））/售价） end***********************/
      
      header.setCommodityCode(header.getCommodityCode().replace("'", ""));
      detail.setCommodityCode(detail.getCommodityCode().replace("'", ""));
      detail.setSkuCode(detail.getSkuCode().replace("'", ""));
      stock.setSkuCode(stock.getSkuCode().replace("'", ""));
      stock.setCommodityCode(stock.getCommodityCode().replace("'", ""));
      if(!StringUtil.isNullOrEmpty(header.getOriginalCommodityCode())){
        header.setOriginalCommodityCode(header.getOriginalCommodityCode().replace("'", ""));
      }

      if (header.getSaleStartDatetimeStr() != null && !DateUtil.isCorrect(header.getSaleStartDatetimeStr().replace("'", "").replaceAll("-", "/"))) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.DATETIMEERR, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.11"))));
        return summary;
      }
      if (header.getSaleEndDatetimeStr() != null && !DateUtil.isCorrect(header.getSaleEndDatetimeStr().replace("'", "").replaceAll("-", "/"))) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.DATETIMEERR, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.12"))));
        return summary;
      }
      if (header.getDiscountPriceStartDatetimeStr() != null && !DateUtil.isCorrect(header.getDiscountPriceStartDatetimeStr().replace("'", "").replaceAll("-", "/"))) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.DATETIMEERR, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.13"))));
        return summary;
      }
      if (header.getDiscountPriceEndDatetimeStr() != null && !DateUtil.isCorrect(header.getDiscountPriceEndDatetimeStr().replace("'", "").replaceAll("-", "/"))) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.DATETIMEERR, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.14"))));
        return summary;
      }
      //商品编号在商品基本表中是否存在
      existsHeader = exists(selectHeaderStatement, header.getCommodityCode());
      if(!existsHeader){
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.COMMODITY_CODE_NOT_EXIST,header.getCommodityCode())));
        return summary;
      } else {
        // 商品存在取出返品不可フラグ的值
        SimpleQuery returnFlgExistsQuery = new SimpleQuery("SELECT RETURN_FLG FROM C_COMMODITY_HEADER WHERE COMMODITY_CODE = ? ");
        returnFlgExistsQuery.setParameters(header.getCommodityCode());
        Object object = executeScalar(returnFlgExistsQuery);
        if (object != null) {
          header.setReturnFlg(Long.parseLong(object.toString()));
        }
      }
      //SKU编号在商品明细表中是否存在
      existsDetail = exists(selectDetailStatement, detail.getSkuCode());
      //SKU编号在在库表中是否存在
      existsStock=exists(selectStockStatement,stock.getSkuCode());
      if((!existsDetail)||(!existsStock)){
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SKU_CODE_NOT_EXIST,detail.getSkuCode())));
        return summary;
      }
      
      if(header.getInBoundLifeDays() != null){
        if(header.getInBoundLifeDays() > 9999){
          summary.getErrors().add(new ValidationResult(null, null, "入库效期值不能大于9999"));
        }
      }
      if(header.getOutBoundLifeDays() != null){
        if(header.getOutBoundLifeDays() > 9999){
          summary.getErrors().add(new ValidationResult(null, null, "出库效期值不能大于9999"));
        }
      }
      if(header.getShelfLifeAlertDays() != null){
        if(header.getShelfLifeAlertDays() > 9999){
          summary.getErrors().add(new ValidationResult(null, null, "失效预警值不能大于9999"));
        }
      }
      if(header.getTmallMjsFlg() != null){
        if(header.getTmallMjsFlg() != 0 && header.getTmallMjsFlg() != 1){
          summary.getErrors().add(new ValidationResult(null, null, "tmall满就送(赠品标志)设定错误"));
        }
      }
      if(header.getImportCommodityType() != null){
        if(header.getImportCommodityType() != 1 && header.getImportCommodityType() != 2  && header.getImportCommodityType() != 9){
          summary.getErrors().add(new ValidationResult(null, null, "进口商品区分(只能为1:全进口、2:海外品品牌、9:普通国产)"));
        }
      }
      if(header.getImportCommodityType() == null){
        header.setImportCommodityType(Long.valueOf("9"));
      }
      if(header.getClearCommodityType() != null){
        if(header.getClearCommodityType() != 1  && header.getClearCommodityType() != 9){
          summary.getErrors().add(new ValidationResult(null, null, "清仓商品区分(只能为1:清仓商品、9:普通商品)"));
        }
      }
      if(header.getClearCommodityType() == null ){
        header.setClearCommodityType(Long.valueOf("9"));
      }
      if(header.getReserveCommodityType1() != null){
        if(header.getReserveCommodityType1() != 1  && header.getReserveCommodityType1() != 9){
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
      if(header.getReserveCommodityType3() != null){
            if(header.getReserveCommodityType3() != 1 &&  header.getReserveCommodityType3() != 9){
              summary.getErrors().add(new ValidationResult(null, null, "商品展示区分(只能为1:是、9:默认不是)"));
            }
      }
      if(header.getReserveCommodityType3() == null ){
            header.setReserveCommodityType3(Long.valueOf("9"));
      }
      if(header.getNewReserveCommodityType1() != null){
          if(header.getNewReserveCommodityType1() != 1 &&  header.getNewReserveCommodityType1() != 9){
            summary.getErrors().add(new ValidationResult(null, null, "预留区分1*(只能为1:是、9:默认不是)"));
          }
      }
      if(header.getNewReserveCommodityType1() == null ){
          header.setNewReserveCommodityType1(Long.valueOf("9"));
      }
      if(header.getNewReserveCommodityType2() != null){
          if(header.getNewReserveCommodityType2() != 1 &&  header.getNewReserveCommodityType2() != 9){
            summary.getErrors().add(new ValidationResult(null, null, "预留区分2*(只能为1:是、9:默认不是)"));
          }
      }
      if(header.getNewReserveCommodityType2() == null ){
          header.setNewReserveCommodityType2(Long.valueOf("9"));
      }
      if(header.getNewReserveCommodityType3() != null){
          if(header.getNewReserveCommodityType3() != 1 &&  header.getNewReserveCommodityType3() != 9){
            summary.getErrors().add(new ValidationResult(null, null, "预留区分3*(只能为1:是、9:默认不是)"));
          }
      }
      if(header.getNewReserveCommodityType3() == null ){
          header.setNewReserveCommodityType3(Long.valueOf("9"));
      }
      if(header.getNewReserveCommodityType4() != null){
          if(header.getNewReserveCommodityType4() != 1 &&  header.getNewReserveCommodityType4() != 9){
            summary.getErrors().add(new ValidationResult(null, null, "预留区分4*(只能为1:是、9:默认不是)"));
          }
      }
      if(header.getNewReserveCommodityType4() == null ){
          header.setNewReserveCommodityType4(Long.valueOf("9"));
      }
      if(header.getNewReserveCommodityType5() != null){
          if(header.getNewReserveCommodityType5() != 1 &&  header.getNewReserveCommodityType5() != 9){
            summary.getErrors().add(new ValidationResult(null, null, "预留区分5*(只能为1:是、9:默认不是)"));
          }
      }
      if(header.getNewReserveCommodityType5() == null ){
          header.setNewReserveCommodityType5(Long.valueOf("9"));
      }    
      
      if(!StringUtil.isNullOrEmpty(header.getOriginalCode())){
          Query query = new SimpleQuery("select * from original_place where original_code=?", 
              header.getOriginalCode());
          OriginalPlace op = DatabaseUtil.loadAsBean(query, OriginalPlace.class);
          if(StringUtil.isNullOrEmpty(op)){
            summary.getErrors().add(new ValidationResult(null, null, "输入的产地CODE不存在"));
              return summary;
          }
      }  
      
      SimpleQuery query = new SimpleQuery("SELECT ORIGINAL_COMMODITY_CODE , COMBINATION_AMOUNT  FROM C_COMMODITY_HEADER WHERE COMMODITY_CODE = ? ");
      query.setParameters(header.getCommodityCode());
      CCommodityHeader commodity = loadAsBean(query,CCommodityHeader.class);
      if (StringUtil.hasValue(commodity.getOriginalCommodityCode())){//导入组合品
        if (StringUtil.hasValue(header.getOriginalCommodityCode()) && header.getCombinationAmount() != null){
          if (!header.getOriginalCommodityCode().equals(commodity.getOriginalCommodityCode().replace("'", "")) 
              || !header.getCombinationAmount().equals(commodity.getCombinationAmount())){
            summary.getErrors().add(new ValidationResult(null, null, "组合品导入时，原商品编号和组合数必须一致"));
            return summary;
          }
        } else {
          summary.getErrors().add(new ValidationResult(null, null, "组合品导入时，原商品编号和组合数不能为空"));
          return summary;
        }
      } else {//导入普通品
        if ( StringUtil.hasValue(header.getOriginalCommodityCode()) || header.getCombinationAmount() != null) {
          summary.getErrors().add(new ValidationResult(null, null, "普通商品导入时，原商品编号和组合数必须为空"));
          return summary;
        }
      }
   // 2014/06/11 库存更新对应 ob_yuan add start
      if (StringUtil.hasValue(stock.getStockRatio())) {
        if (!checkStockRatio(stock.getStockRatio())) {
          summary.getErrors().add(new ValidationResult(null, null, "库存比例导入时，必须按照【EC:TM:JD】的格式，而且合计值必须为100"));
          return summary;
        }
      }
   // 2014/06/11 库存更新对应 ob_yuan add end
    } catch (CsvImportException e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }
    
    

    // 10.1.1 10019 追加 ここから
    checkMinusNumber(summary);
    // 10.1.1 10019 追加 ここまで
    List<String> errorMessageList = new ArrayList<String>();
    
    //判断必须项是否为空
    checkExist(errorMessageList);
    
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
    // 20120319 os013 add start
    // 判断SKU是否同期过
    SimpleQuery tmallSkuIdExistsQuery = new SimpleQuery(
    "SELECT TMALL_SKU_ID FROM C_COMMODITY_DETAIL WHERE  SKU_CODE = ?");
    tmallSkuIdExistsQuery.setParameters(detail.getSkuCode());
    Object object = executeScalar(tmallSkuIdExistsQuery);
    if (object == null) {
      existsTmallSkuId = false;
    } else {
      existsTmallSkuId = true;
    }
    // 20120319 os013 add end
    
    if (existsBrandCode) {
      // 判断品牌编号是否存在
      SimpleQuery brandExistsQuery = new SimpleQuery("SELECT BRAND_CODE FROM BRAND WHERE  BRAND_CODE = ?");
      brandExistsQuery.setParameters(header.getBrandCode());
      object = executeScalar(brandExistsQuery);
      if (object == null) {
        summary.getErrors().add(new ValidationResult(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.62"), null, Message.get(CsvMessage.NOT_EXIST, header.getBrandCode())));
      }
    }
    
    //商品期限管理フラグは2の場合保管日数必須
    checkShelfLife(summary);
    
    //成对检查
    pairCheck(summary);
    
    //定価フラグcheck
    checkFixedPrice(summary);

    //最小单价check
    checkMinPrice(summary);
    
    //入り数check
    checkInner(summary);
    
    //容量check
    checkVolume(summary);
    
    //EC在庫割合check
    checkShareRatio(summary);
    
    // 如果有除了[已同期、4毛利率小于0错误]的其他错误，这时候返回。
    if (summary.hasError()) {
      return summary;
    }
    // 20120319 os013 add start
    // SKU编号已经同期过了
    if (existsTmallSkuId) {
      summary.getErrors().add(new ValidationResult(null, null, "已同期"));
    }
    
    if(ecGrossProfitRateLessThan0) {
      summary.getErrors().add(new ValidationResult(null, null, "EC售价的毛利率为:" + ecGrossProfitRate.toString() + "，小于0"));
    }
    if(ecSpecialPriceGrossProfitRateLessThan0) {
      summary.getErrors().add(new ValidationResult(null, null, "EC特价的毛利率为:" + ecSpecialGrossProfitRate.toString() + "，小于0"));
    }
    if(tmallPriceGrossProfitRateLessThan0) {
      summary.getErrors().add(new ValidationResult(null, null, "Tmall售价的毛利率为:" + tmallGrossProfitRate.toString() + "，小于0"));
    }
    if(jdPriceGrossProfitRateLessThan0) {
      summary.getErrors().add(new ValidationResult(null, null, "JD售价的毛利率为:" + jdGrossProfitRate.toString() + "，小于0"));
    }
    // 20120319 os013 add end
    // 返回已同期和毛利率错误
    
    
    // 20150120 hdh add start 特定用户才能更新商品有效期
    boolean shelfLifeUpdateFlag = false; 
    WebShopSpecialConfig speiConfig = DIContainer.getWebShopSpecialConfig();
    if(speiConfig.getShelfLifeUserList()!=null && speiConfig.getShelfLifeUserList().size()>0){
      for(String loginId:speiConfig.getShelfLifeUserList()){
        if(StringUtil.isNull(loginId)){
          continue;
        }
        if(loginId.equals(getCondition().getLoginInfo().getLoginId())){
          shelfLifeUpdateFlag = true;
          break;
        }
      }
    }
    if(!shelfLifeUpdateFlag){
      if(StringUtil.hasValue(header.getCommodityCode())){
        //查询原先的值填充
        SimpleQuery shelfLifQuery = new SimpleQuery(" SELECT * FROM C_COMMODITY_HEADER WHERE COMMODITY_CODE = ? ");
        shelfLifQuery.setParameters(header.getCommodityCode());
        CCommodityHeader oldHeader = loadAsBean(shelfLifQuery,CCommodityHeader.class);
        if(oldHeader !=null){
          header.setShelfLifeFlag(oldHeader.getShelfLifeFlag());
          header.setShelfLifeDays(oldHeader.getShelfLifeDays());
        }
      }
    }
    // 20150120 hdh add end
    
    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());


    /*********************************** 向commodity_price_change_history表插入信息 *****************************************/
    String newEcDiscountPrice = "", newJdPrice = "", newEcPrice = "", newTmallPrice = "";

    CatalogService catalogService = ServiceLocator.getCatalogService(getCondition().getLoginInfo());

    SearchResult<CCommodityDetail> cCommodityDetailSearchResult = catalogService.searchCCommodityDetail(detail.getCommodityCode());
    SearchResult<CCommodityHeader> cCommodityHeaderSearchResult = catalogService.searchCCommodityHeader(detail.getSkuCode().replace("'", ""));
    
    // 如果价格变动了,则向该表插入变动信息

    // 如果此次更新更改了Ec特价
    if (detail.getDiscountPrice() != null) {
      if (cCommodityDetailSearchResult.getRows().get(0).getDiscountPrice() != null) {
        // 如果原Ec特价不为null且新的Ec特价与表中的Ec特价不同
        if (detail.getDiscountPrice().compareTo(cCommodityDetailSearchResult.getRows().get(0).getDiscountPrice()) != 0) {
          newEcDiscountPrice = detail.getDiscountPrice().toString();
        }
      } else {
        // 如果原Ec特价为null
        newEcDiscountPrice = detail.getDiscountPrice().toString();
      }
    }
    // 如果此次更新更改了京东售价(前台的京东售价为后台的tmallDiscountPrice)
    if (detail.getTmallDiscountPrice() != null) {
      if (cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice() != null) {
        // 如果原京东售价不为null且新的京东售价与表中的京东售价不同
        if (detail.getTmallDiscountPrice().compareTo(cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice()) != 0) {
          newJdPrice = detail.getTmallDiscountPrice().toString();
        }
      } else {
        // 如果原京东售价为null
        newJdPrice = detail.getTmallDiscountPrice().toString();
      }
    }

    // 如果此次更新更改了淘宝售价
    if (detail.getTmallUnitPrice() != null) {
      if (cCommodityDetailSearchResult.getRows().get(0).getTmallUnitPrice() != null) {
        // 如果原淘宝售价不为null且新的京东售价与表中的京东售价不同
        if (detail.getTmallUnitPrice().compareTo(cCommodityDetailSearchResult.getRows().get(0).getTmallUnitPrice()) != 0) {
          newTmallPrice = detail.getTmallUnitPrice().toString();
        }
      } else {
        // 如果原淘宝售价为null
        newTmallPrice = detail.getTmallUnitPrice().toString();
      }
    }

    // 如果此次更新更改了EC售价
    if (detail.getUnitPrice() != null) {
      if (cCommodityDetailSearchResult.getRows().get(0).getUnitPrice() != null) {
        // 如果原EC售价不为null且新的京东售价与表中的京东售价不同
        if (detail.getUnitPrice().compareTo(cCommodityDetailSearchResult.getRows().get(0).getUnitPrice()) != 0) {
          newEcPrice = detail.getUnitPrice().toString();
        }
      } else {
        // 如果原EC售价为null
        newEcPrice = detail.getUnitPrice().toString();
      }
    }

    CommodityPriceChangeHistory commodityPriceChangeHistory = new CommodityPriceChangeHistory();
    commodityPriceChangeHistory = setCommodityPriceChangeHistory(commodityPriceChangeHistory, detail, newEcDiscountPrice,
        newJdPrice, newEcPrice, newTmallPrice);
    ServiceResult serviceResult = null;
    
    // 4个价格中至少1个变动了，才执行insert操作
    if (StringUtil.hasValueAnyOf(newEcDiscountPrice, newJdPrice, newEcPrice, newTmallPrice)) {
      if((header.getClearCommodityType() != null && header.getClearCommodityType() != 1)
         || (header.getClearCommodityType() == null && cCommodityHeaderSearchResult.getRows().get(0).getClearCommodityType() != 1)) {
      //  if(detail.getAverageCost() != null) {
          serviceResult = catalogService.insertCommodityPriceChangeHistory(commodityPriceChangeHistory);
      //  }
      }
    }
    if (serviceResult != null && serviceResult.hasError()) {
      throw new CsvImportException();
    }
    /********************************** 向commodity_price_change_history表插入信息 end 2014/07/22 ***************************/

    
    try {
      // 基本表更新
      int updHeaderCount = executeHeaderUpdate(existsHeader);
      if (updHeaderCount != 1) {
        throw new CsvImportException();
      }
      
      // 商品明细表更新
      int updDetailCount = executeDetailUpdate(existsDetail);
      if (updDetailCount != 1) {
        throw new CsvImportException();
      }
      
      // 在库表更新
      int updStockCount = executeStockUpdate(existsStock);
      if(updStockCount!=1){
        throw new CsvImportException();
      }
      
      // 20130703 shen add start
      // 同期标志更新
      int updateSyncFlagCount = executeUpdateSyncFlag();
      if (updateSyncFlagCount != 1) {
        throw new CsvImportException();
      }
      // 20130703 shen add end
      
      // 2014/05/06 京东WBS对应 ob_卢 add start(delete by zhangfeng on 2014/7/23)
      // 京东同期标志更新
      // 京东同期标志为不同期时，不更新；
//      if(SyncFlagJd.SYNCVISIBLE.getValue().equals(getCondition().getSyncFlagJd())){
//        // 京东同期表示为同期 而且 商品Header.JD商品ID！=null 
//        // 而且 CSV导入后商品Detail.JD使用标志为1：使用时，更新为1：待同期；反之，不更新
//        CCommodityHeaderDao cHeaderDao = DIContainer.getDao(CCommodityHeaderDao.class);
//        CCommodityDetailDao cDetailDao = DIContainer.getDao(CCommodityDetailDao.class);
//
//        CCommodityHeader cHeader = cHeaderDao.load(DIContainer.getWebshopConfig().getSiteShopCode(), header.getCommodityCode());
//        if(cHeader == null){
//          throw new CsvImportException();
//        }
//        CCommodityDetail cDetail = cDetailDao.load(DIContainer.getWebshopConfig().getSiteShopCode(), cHeader.getCommodityCode());
//        if(cDetail == null){
//          throw new CsvImportException();
//        }
//        Long jdUseFlg = JdUseFlg.DISABLED.longValue();
//        if(detail.getJdUseFlg() != null){
//          jdUseFlg = detail.getJdUseFlg();
//        }else if(cDetail.getJdUseFlg() != null){
//          jdUseFlg = cDetail.getJdUseFlg();
//        }
//        if(cHeader.getJdCommodityId() != null && JdUseFlg.ENABLED.longValue().equals(jdUseFlg)){
//          int updateSyncFlagJdCount = executeUpdateSyncFlagJd();
//          
//          if (updateSyncFlagJdCount != 1) {
//            throw new CsvImportException();
//          }
//        }
//      }
      // 2014/05/06 京东WBS对应 ob_卢 add end(delete by zhangfeng on 2014/7/23)

      int updateSyncFlagJdCount = executeUpdateSyncFlagJd();
      
      if (updateSyncFlagJdCount != 1) {
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
  
  /**
   * 商品基本表查询存在
   */
  private String getSelectHeaderQuery() {
    String selectSql = "" + " SELECT COUNT(*) FROM " + HEADER_TABLE_NAME
        // 20130617 txw update start
        + " WHERE COMMODITY_CODE = ? ";
        // 20130617 txw update end
    return selectSql;
  }
  
  
  /**
   * 商品详细表查询存在
   */
  private String getSelectDetailQuery() {
    String selectSql = "" + " SELECT COUNT(*) FROM " + DATAIL_TABLE_NAME
      // 20130617 txw update start
      + " D"
      + " INNER JOIN " + HEADER_TABLE_NAME + " H ON D.COMMODITY_CODE = H.COMMODITY_CODE "
      + " WHERE  D.SKU_CODE = ?  ";
      // 20130617 txw update end
    return selectSql;
  }
  
  
  /**
   * 在库表查询存在
   */
  private String getSelectStockQuery(){
    String selectSql = "" + " SELECT COUNT(*) FROM " + STOCK_TABLE_NAME
      // 20130617 txw update start
      + " S"
      + " INNER JOIN " + HEADER_TABLE_NAME + " H ON S.COMMODITY_CODE = H.COMMODITY_CODE "
      + " WHERE  S.SKU_CODE = ? ";
      // 20130617 txw update end
    return selectSql;
  }
 
  /**
   * 商品基本表更新Query
   */
  private String getUpdateHeaderQuery() {
    String updateSql = "" + " UPDATE " + HEADER_TABLE_NAME
        + " SET {0} SYNC_FLAG_EC = 1, SYNC_FLAG_TMALL = 1, EXPORT_FLAG_ERP = 1,EXPORT_FLAG_WMS = 1, " 
        + " UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ? ";

    StringBuilder setPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    for (int i = 0; i < headerColumns.size(); i++) {
      CsvColumn column = headerColumns.get(i);
      if (!column.getPhysicalName().equals("return_flg_cust") && 
          !column.getPhysicalName().equals("return_flg_supp") && 
          !column.getPhysicalName().equals("change_flg_supp")) {
        columnList.add(column.getPhysicalName());
      }
    }
    columnList.add("return_flg");
    for (String column : columnList) {
      setPart.append(column + " = ?, ");
    }

    return MessageFormat.format(updateSql, setPart.toString());
  }
  
  
  /**
   * 商品详细表更新Query
   */
//  private String getUpdateDetailQuery() {
//    String updateSql = "" + " UPDATE " + DATAIL_TABLE_NAME
//        + " SET {0} UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE SKU_CODE = ? ";
//
//    StringBuilder setPart = new StringBuilder();
//
//    List<String> columnList = new ArrayList<String>();
//    for (int i = 0; i < detailColumns.size(); i++) {
//      CsvColumn column = detailColumns.get(i);
//      columnList.add(column.getPhysicalName());
//    }
//
//    for (String column : columnList) {
//      setPart.append(column + " = ?, ");
//    }
//    
//    return MessageFormat.format(updateSql, setPart.toString());
//  }
  
  //2014/05/06 京东WBS对应 ob_卢 add start
  /**
   * 商品详细表更新Query jdUseFlg为空时不更新jdUseFlg
   */
  private String getUpdateDetailJdQuery() {
    String updateSql = "" + " UPDATE " + DATAIL_TABLE_NAME + " SET {0} UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE SKU_CODE = ? ";

    StringBuilder setPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    for (int i = 0; i < detailColumns.size(); i++) {
      CsvColumn column = detailColumns.get(i);
      if(column.getPhysicalName().equals("jd_use_flg")){
        if(detail.getJdUseFlg() != null){
          columnList.add(column.getPhysicalName());
        }
        continue;
      }
      columnList.add(column.getPhysicalName());
    }

    for (String column : columnList) {
      setPart.append(column + " = ?, ");
    }

    return MessageFormat.format(updateSql, setPart.toString());

  }
  // 2014/05/06 京东WBS对应 ob_卢 add end
  /**
   * 在库表更新Query
   */
  private String getUpdateStockQuery(){
    String updateSql = "" + " UPDATE " + STOCK_TABLE_NAME
    + " SET {0} UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE SKU_CODE = ? ";
    StringBuilder setPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    for (int i = 0; i < stockColumns.size(); i++) {
      CsvColumn column = stockColumns.get(i);
      if (column.getPhysicalName().equals("stock_threshold")) {
     // 2014/06/11 库存更新对应 ob_yuan delete start
        //if (!existsTmallSkuId) {
        //  columnList.add(column.getPhysicalName());
        //}
     // 2014/06/11 库存更新对应 ob_yuan delete end
      } else {
        if (!column.getPhysicalName().equals("stock_ratio")) {
          columnList.add(column.getPhysicalName());
        }
      }
    }

    for (String column : columnList) {
      setPart.append(column + " = ?, ");
    }

    return MessageFormat.format(updateSql, setPart.toString());
  }
 
  /**
   * 商品基本表更新
   */
  private int executeHeaderUpdate(boolean exists) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();

    if (exists) {
      for (int i = 0; i < headerColumns.size(); i++) {
        CsvColumn column = headerColumns.get(i);
        if (!column.getPhysicalName().equals("return_flg_cust") && 
            !column.getPhysicalName().equals("return_flg_supp") && 
            !column.getPhysicalName().equals("change_flg_supp")) {
          params.add(BeanUtil.invokeGetter(header, StringUtil.toCamelFormat(column.getPhysicalName())));
        }
      }
      params.add(header.getReturnFlg());
      
      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());


      params.add(header.getCommodityCode());

      pstmt = updateHeaderStatement;
      logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    }

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }
  
  /**
   * 商品明细表更新
   */
  private int executeDetailUpdate(boolean exists) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    if (exists) {
      for (int i = 0; i < detailColumns.size(); i++) {
        CsvColumn column = detailColumns.get(i);
        // 2014/05/06 京东WBS对应 ob_卢 add start
        if(column.getPhysicalName().equals("jd_use_flg")){
          if(detail.getJdUseFlg() == null){
            continue;
          }
        }
        // 2014/05/06 京东WBS对应 ob_卢 add end
        params.add(BeanUtil.invokeGetter(detail, StringUtil.toCamelFormat(column.getPhysicalName())));
      }

      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());

      params.add(detail.getSkuCode());
      
      // 2014/05/06 京东WBS对应 ob_卢 update start
      //pstmt = updateDetailStatement;
      pstmt = createPreparedStatement(getUpdateDetailJdQuery());
      // 2014/05/06 京东WBS对应 ob_卢 update end

      logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    }

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }
  
  /**
   * 在库表更新
   */
  private int executeStockUpdate(boolean exists) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;
    // 2014/06/11 库存更新对应 ob_yuan add start
    CsvColumn stockThresholdColumn = null;
 // 2014/06/11 库存更新对应 ob_yuan add end
    List<Object> params = new ArrayList<Object>();
    if (exists) {
      for (int i = 0; i < stockColumns.size(); i++) {
        CsvColumn column = stockColumns.get(i);
        // 2012-06-04 update start
        // if (column.getPhysicalName().equals("stock_threshold")) {
        // if(!existsTmallSkuId){
        // params.add(BeanUtil.invokeGetter(stock,
        // StringUtil.toCamelFormat(column.getPhysicalName())));
        // }
        // } else {
        // params.add(BeanUtil.invokeGetter(stock,
        // StringUtil.toCamelFormat(column.getPhysicalName())));
        // }
        // 2014/06/11 库存更新对应 ob_yuan update start
        //params.add(BeanUtil.invokeGetter(stock, StringUtil.toCamelFormat(column.getPhysicalName())));
        if (!column.getPhysicalName().equals("stock_threshold")) {
          if (!column.getPhysicalName().equals("stock_ratio")) {
            params.add(BeanUtil.invokeGetter(stock, StringUtil.toCamelFormat(column.getPhysicalName())));
          }
        } else {
          stockThresholdColumn = column;
        }
        // 2014/06/11 库存更新对应 ob_yuan update end
        // 2012-06-04 update end
      }

      params.add(getCondition().getLoginInfo().getRecordingFormat());
      params.add(DateUtil.getSysdate());
      params.add(stock.getSkuCode());

      pstmt = createPreparedStatement(getUpdateStockQuery());
      logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    } 
 // 2014/06/11 库存更新对应 ob_yuan update start
    //DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    //return pstmt.executeUpdate();
    int updateRows = 0;
    if (pstmt!=null ) {
      DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
      updateRows = pstmt.executeUpdate();
      updateRows = pstmt.executeUpdate();
      if (updateRows!=1) {
        return updateRows;
      }
    }
    //安全库存增量更新
    if(exists && stockThresholdColumn!=null) {
      //现在的安全库存取得
      Query stockQuery = new SimpleQuery(this.getStockSql,DIContainer.getWebshopConfig().getSiteShopCode(),stock.getSkuCode());
      //现在的安全库存增量取得
      Query stockTempQuery = new SimpleQuery(this.getStockTempSql,DIContainer.getWebshopConfig().getSiteShopCode(),stock.getSkuCode());
      Long stockThreshold = NumUtil.toLong(executeScalar(stockQuery).toString(),0L);
      Object addStockThresholdObj = executeScalar(stockTempQuery);
      if (stockThreshold!=stock.getStockThreshold()) {
        params.clear();
        //更新处理
        if (addStockThresholdObj!=null) {
          pstmt = this.updateStockTempStatement;
          params.add(stock.getStockThreshold()-stockThreshold);
          params.add(getCondition().getLoginInfo().getRecordingFormat());
          params.add(DateUtil.getSysdate());
          params.add(DIContainer.getWebshopConfig().getSiteShopCode());
          params.add(stock.getSkuCode());
          logger.debug("UPDATE STOCK_TEMP Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
        } else {
          //登录处理
          /** 临时库存登录用SQL */
          String insertStockTempSql = "INSERT INTO STOCK_TEMP(SHOP_CODE,SKU_CODE,ORM_ROWID,CREATED_USER,CREATED_DATETIME, " +
                    "UPDATED_USER,UPDATED_DATETIME,STOCK_CHANGE_TYPE,STOCK_CHANGE_QUANTITY) VALUES (?, ?, " +SqlDialect.getDefault().getNextvalNOprm("stock_temp_seq") +", ?, ?, ?, ?, ?, ?)";
          insertStockTempStatement = createPreparedStatement(insertStockTempSql);
          pstmt = insertStockTempStatement;
          params.add(DIContainer.getWebshopConfig().getSiteShopCode());
          params.add(stock.getSkuCode());
          params.add(getCondition().getLoginInfo().getRecordingFormat());
          params.add(DateUtil.getSysdate());
          params.add(getCondition().getLoginInfo().getRecordingFormat());
          params.add(DateUtil.getSysdate());
          params.add(StockChangeType.SAFE_ALLOCATED.longValue());
          params.add(stock.getStockThreshold()-stockThreshold);
          logger.debug("INSERT STOCK_TEMP Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
        }
        DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
        updateRows = pstmt.executeUpdate();
        if (updateRows!=1) {
          return updateRows;
        }
      }
    }
    //库存比例的更新
    if(exists && StringUtil.hasValue(stock.getStockRatio())) {
      pstmt = createPreparedStatement(getDeleteStockRatioSql());
      params.clear();
      params.add(DIContainer.getWebshopConfig().getSiteShopCode());
      params.add(stock.getCommodityCode());
      logger.debug("DELETE STOCK_RATIO Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
      DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
      updateRows = pstmt.executeUpdate();
      String[] stockRatioArray = stock.getStockRatio().split(":");
      Long ecStockRatio = NumUtil.toLong(stockRatioArray[0]);
      Long tmStockRatio = NumUtil.toLong(stockRatioArray[1]);
      Long jdStockRatio = NumUtil.toLong(stockRatioArray[2]);
      for (RatioType type : RatioType.values()) {
        params.clear();
        // ショップコード
        params.add(DIContainer.getWebshopConfig().getSiteShopCode());
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
  // 2014/06/11 库存更新对应 ob_yuan add start
  private String getInsertStockRatioSql() {
    String sql = "INSERT INTO STOCK_RATIO(shop_code, commodity_code, ratio_type, stock_ratio, " +
    "orm_rowid, created_user, created_datetime, updated_user, updated_datetime)" +
    "VALUES (?, ?, ?, ?,  "+ SqlDialect.getDefault().getNextvalNOprm("stock_ratio_seq") +", ?,?, ?, ?);";
    return sql;
  }
  private String getDeleteStockRatioSql() {
    String sql = "DELETE FROM STOCK_RATIO WHERE SHOP_CODE = ? AND COMMODITY_CODE = ?";
    return sql;
  }
  //2014/06/11 库存更新对应 ob_yuan add end
  // 10.1.1 10019 追加 ここから
  private void checkMinusNumber(ValidationSummary summary) {
    //希望售价必须输入0以上的数
    if (detail.getSuggestePrice() != null && BigDecimalUtil.isBelow(detail.getSuggestePrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages.getString("service.data.csv.CommodityDetailImportDataSource.49"))));
    }
    //EC商品单价必须输入0以上的数
    if (detail.getUnitPrice() != null && BigDecimalUtil.isBelow(detail.getUnitPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages.getString("service.data.csv.CommodityDetailImportDataSource.40"))));
    }
    //EC商品特价必须输入0以上的数
    if (detail.getDiscountPrice() != null && BigDecimalUtil.isBelow(detail.getDiscountPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages.getString("service.data.csv.CommodityDetailImportDataSource.41"))));
    }
    //Tmall商品单价必须输入0以上的数
    if (detail.getTmallUnitPrice() != null && BigDecimalUtil.isBelow(detail.getTmallUnitPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages.getString("service.data.csv.CommodityDetailImportDataSource.42"))));
    }
    //Tmall商品特价必须输入0以上的数
    if (detail.getTmallDiscountPrice() != null && BigDecimalUtil.isBelow(detail.getTmallDiscountPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages.getString("service.data.csv.CommodityDetailImportDataSource.43"))));
    }
    //最小单价必须输入0以上的数
    if (detail.getMinPrice() != null && BigDecimalUtil.isBelow(detail.getMinPrice(), BigDecimal.ZERO)) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages.getString("service.data.csv.CommodityDetailImportDataSource.50"))));
    }
  }
  // 10.1.1 10019 追加 ここまで
  
  /**
   * 商品期限管理フラグは2の場合保管日数必須
   */
  private void checkShelfLife(ValidationSummary summary) {
    if(header.getShelfLifeFlag()!=null && header.getShelfLifeFlag()==2){
      if(header.getShelfLifeDays()==null){
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CommodityHeaderImportDataSource.65"))));
      } else {
        if (header.getShelfLifeDays() < 0) {
          summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages.getString("service.data.csv.CommodityHeaderImportDataSource.67"))));
        }
      }
    }
  }
  
  /**
   * 成对检查
   */
  private void pairCheck(ValidationSummary summary){
    //成分名1，成分量1
    if((StringUtil.isNullOrEmpty(header.getIngredientName1())&&StringUtil.hasValue(header.getIngredientVal1()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal1())&&StringUtil.hasValue(header.getIngredientName1()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.34"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.35"))));
    }
    //成分名2，成分量2
    if((StringUtil.isNullOrEmpty(header.getIngredientName2())&&StringUtil.hasValue(header.getIngredientVal2()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal2())&&StringUtil.hasValue(header.getIngredientName2()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.36"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.37"))));
    }
    //成分名3，成分量3
    if((StringUtil.isNullOrEmpty(header.getIngredientName3())&&StringUtil.hasValue(header.getIngredientVal3()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal3())&&StringUtil.hasValue(header.getIngredientName3()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.38"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.39"))));
    }
    //成分名4，成分量4
    if((StringUtil.isNullOrEmpty(header.getIngredientName4())&&StringUtil.hasValue(header.getIngredientVal4()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal4())&&StringUtil.hasValue(header.getIngredientName4()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.40"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.41"))));
    }
    //成分名5，成分量5
    if((StringUtil.isNullOrEmpty(header.getIngredientName5())&&StringUtil.hasValue(header.getIngredientVal5()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal5())&&StringUtil.hasValue(header.getIngredientName5()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.42"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.43"))));
    }
    //成分名6，成分量6
    if((StringUtil.isNullOrEmpty(header.getIngredientName6())&&StringUtil.hasValue(header.getIngredientVal6()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal6())&&StringUtil.hasValue(header.getIngredientName6()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.44"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.45"))));
    }
    //成分名7，成分量7
    if((StringUtil.isNullOrEmpty(header.getIngredientName7())&&StringUtil.hasValue(header.getIngredientVal7()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal7())&&StringUtil.hasValue(header.getIngredientName7()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.46"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.47"))));
    }
    //成分名8，成分量8
    if((StringUtil.isNullOrEmpty(header.getIngredientName8())&&StringUtil.hasValue(header.getIngredientVal8()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal8())&&StringUtil.hasValue(header.getIngredientName8()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.48"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.49"))));
    }
    //成分名9，成分量9
    if((StringUtil.isNullOrEmpty(header.getIngredientName9())&&StringUtil.hasValue(header.getIngredientVal9()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal9())&&StringUtil.hasValue(header.getIngredientName9()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.50"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.51"))));
    }
    //成分名10，成分量10
    if((StringUtil.isNullOrEmpty(header.getIngredientName10())&&StringUtil.hasValue(header.getIngredientVal10()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal10())&&StringUtil.hasValue(header.getIngredientName10()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.52"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.53"))));
    }
    //成分名11，成分量11
    if((StringUtil.isNullOrEmpty(header.getIngredientName11())&&StringUtil.hasValue(header.getIngredientVal11()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal11())&&StringUtil.hasValue(header.getIngredientName11()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.54"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.55"))));
    }
    //成分名12，成分量12
    if((StringUtil.isNullOrEmpty(header.getIngredientName12())&&StringUtil.hasValue(header.getIngredientVal12()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal12())&&StringUtil.hasValue(header.getIngredientName12()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.56"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.57"))));
    }
    //成分名13，成分量13
    if((StringUtil.isNullOrEmpty(header.getIngredientName13())&&StringUtil.hasValue(header.getIngredientVal13()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal13())&&StringUtil.hasValue(header.getIngredientName13()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.58"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.59"))));
    }
    //成分名14，成分量14
    if((StringUtil.isNullOrEmpty(header.getIngredientName14())&&StringUtil.hasValue(header.getIngredientVal14()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal14())&&StringUtil.hasValue(header.getIngredientName14()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.60"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.61"))));
    }
    //成分名15，成分量15
    if((StringUtil.isNullOrEmpty(header.getIngredientName15())&&StringUtil.hasValue(header.getIngredientVal15()))
     ||(StringUtil.isNullOrEmpty(header.getIngredientVal15())&&StringUtil.hasValue(header.getIngredientName15()))){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_PAIR,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.62"),Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.63"))));
    }
  }
  
  /**
   * 定価フラグcheck
   */
  private void checkFixedPrice(ValidationSummary summary) {
    //定价标志为1
    if (detail.getFixedPriceFlag() != null && detail.getFixedPriceFlag() == 1) {
      //希望售价不能为空
      if (detail.getSuggestePrice() == null) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CommodityDetailImportDataSource.26"))));
      } else {
        //官网售价必须等于希望售价
        if (!detail.getSuggestePrice().equals(detail.getUnitPrice())) {
          summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_EQUAL, Messages.getString("service.data.csv.CommodityDetailImportDataSource.40"),Messages.getString("service.data.csv.CommodityDetailImportDataSource.49"))));
        }
        //淘宝售价必须等于希望售价
        if (!detail.getSuggestePrice().equals(detail.getTmallUnitPrice())) {
          summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_EQUAL, Messages.getString("service.data.csv.CommodityDetailImportDataSource.42"),Messages.getString("service.data.csv.CommodityDetailImportDataSource.49")))); 
        }
      }
      //EC特价不能输入
      if (detail.getDiscountPrice() != null) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NO_REQUEST_ITEM, Messages.getString("service.data.csv.CommodityDetailImportDataSource.27"))));
      }
      //Tmall特价不能输入
      if (detail.getTmallDiscountPrice() != null) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NO_REQUEST_ITEM, Messages.getString("service.data.csv.CommodityDetailImportDataSource.28"))));
      }
    }
  }
  
  /**
   * 最小单价check
   */
  private void checkMinPrice(ValidationSummary summary){
    //最小单价不为空时，所有价格都必须大于最小单价
    if (detail.getMinPrice() != null) {
      // 希望小売価格
      if (!ValidatorUtil.lessThanOrEquals(detail.getMinPrice(), detail.getSuggestePrice())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_GREATER_THAN,Messages.getString("service.data.csv.CommodityDetailImportDataSource.44"),detail.getMinPrice().toString())));
      }
      // EC商品单价
      if (!ValidatorUtil.lessThanOrEquals(detail.getMinPrice(), detail.getUnitPrice())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_GREATER_THAN,Messages.getString("service.data.csv.CommodityDetailImportDataSource.40"),detail.getMinPrice().toString())));
      }
      // EC商品特价
      if (detail.getDiscountPrice() != null && detail.getFixedPriceFlag() != 1) {
        if (!ValidatorUtil.lessThanOrEquals(detail.getMinPrice(), detail.getDiscountPrice())) {
          summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_GREATER_THAN,Messages.getString("service.data.csv.CommodityDetailImportDataSource.41"),detail.getMinPrice().toString())));
        }
      }
      // Tmall商品单价
      if (!ValidatorUtil.lessThanOrEquals(detail.getMinPrice(), detail.getTmallUnitPrice())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_GREATER_THAN,Messages.getString("service.data.csv.CommodityDetailImportDataSource.42"),detail.getMinPrice().toString())));
      }
      // Tmall商品特价
      if (detail.getTmallDiscountPrice() != null && detail.getFixedPriceFlag() != 1) {
        if (!ValidatorUtil.lessThanOrEquals(detail.getMinPrice(), detail.getTmallDiscountPrice())) {
          summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_GREATER_THAN,Messages.getString("service.data.csv.CommodityDetailImportDataSource.43"),detail.getMinPrice().toString())));
        }
      }
    }
  }
  
  /**
   * 如果入り数（inner_quantity）,入り数単位(inner_quantity_unit)
   * ,WEB表示単価単位入り数(inner_unit_for_price) 中有一个值不为空，其它两个值也不能为空
   * 且WEB表示単価単位入り数 必须小于 入り数
   */
  private void checkInner(ValidationSummary summary){
    // 入り数、入り数単位、WEB表示単価単位入り数有一个不为空
    if (StringUtil.hasValueAnyOf(detail.getInnerQuantity() == null ? null : detail.getInnerQuantity().toString(), detail.getInnerQuantityUnit(), detail.getInnerUnitForPrice() == null ? null : detail.getInnerUnitForPrice().toString())) {
      // 其他两个都不为空
      if (!StringUtil.hasValueAllOf(detail.getInnerQuantity() == null ? null : detail.getInnerQuantity().toString(), detail.getInnerQuantityUnit(), detail.getInnerUnitForPrice() == null ? null : detail.getInnerUnitForPrice().toString())) {
        summary.getErrors().add(new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityDetailImportDataSource.45")));
      } else {
       // WEB表示単価単位入り数必须小于 入り数
        if (!ValidatorUtil.lessThanOrEquals(detail.getInnerUnitForPrice(), detail.getInnerQuantity())) {
          summary.getErrors().add(new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityDetailImportDataSource.46")));
        }
      }
    }
  }
  
  /**
   * 如果容量（volume）,容量単位(volume_unit)
   * ,WEB表示単価単位容量(volume_unit_for_price) 中有一个值不为空，其它两个值也不能为空
   * 且WEB表示単価単位容量 必须小于 容量
   */
  private void checkVolume(ValidationSummary summary){
    // 容量、容量単位、WEB表示単価単位容量有一个不为空
    
    if (StringUtil.hasValueAnyOf(detail.getVolume() == null ? null : detail.getVolume().toString(), detail.getVolumeUnit(), detail.getVolumeUnitForPrice() == null ? null : detail.getVolumeUnitForPrice().toString())) {
      // 其他两个都不为空
      if (!StringUtil.hasValueAllOf(detail.getVolume() == null ? null : detail.getVolume().toString(), detail.getVolumeUnit(), detail.getVolumeUnitForPrice() == null ? null : detail.getVolumeUnitForPrice().toString())) {
        summary.getErrors().add(new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityDetailImportDataSource.47")));
      } else {
       // WEB表示単価単位容量 必须小于 容量
        if (!ValidatorUtil.lessThanOrEquals(detail.getVolumeUnitForPrice(), detail.getVolume())) {
          summary.getErrors().add(new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityDetailImportDataSource.48")));
        }
      }
    }
  }
  
  /**
   * EC在庫割合check
   */
  private void checkShareRatio(ValidationSummary summary) {
    if(stock.getShareRatio() != null && (stock.getShareRatio()>100 || stock.getShareRatio() < 0)){
      summary.getErrors().add(new ValidationResult(Messages.getString("service.data.csv.CommodityDetailImportDataSource.29"), null, Message.get(CsvMessage.OUT_OF_RANGE, "0","100")));
    }
  }
  
  private void checkDateRange(CCommodityHeaderImport bean, List<String> errorMessageList) {
    String min = Integer.toString(DIContainer.getWebshopConfig().getApplicationMinYear());
    String max = Integer.toString(DIContainer.getWebshopConfig().getApplicationMaxYear());

    if (!DateUtil.isCorrectAppDate(bean.getSaleStartDatetime(), false) && !bean.getSaleStartDatetime().equals(DateUtil.getMin())) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages.getString("service.data.csv.CommodityHeaderImportDataSource.48"), min, max));
    }
    if (!DateUtil.isCorrectAppDate(bean.getSaleEndDatetime(), false) && !bean.getSaleEndDatetime().equals(DateUtil.getMax())) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages.getString("service.data.csv.CommodityHeaderImportDataSource.49"), min, max));
    }
    if (!DateUtil.isCorrectAppDate(bean.getDiscountPriceStartDatetime(), true)) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages.getString("service.data.csv.CommodityHeaderImportDataSource.51"), min, max));
    }
    if (!DateUtil.isCorrectAppDate(bean.getDiscountPriceEndDatetime(), true)) {
      errorMessageList.add(Message.get(CsvMessage.NOT_IN_RANGE, Messages.getString("service.data.csv.CommodityHeaderImportDataSource.52"), min, max));
    }
  }
  
  private void checkTermsRules(CCommodityHeaderImport bean, List<String> errorMessageList) {
    if (bean.getSaleStartDatetime() != null && bean.getSaleEndDatetime() != null
        && bean.getSaleStartDatetime().after(bean.getSaleEndDatetime())) {
      errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.18"), bean.getCommodityCode()));
    }
    if (bean.getDiscountPriceStartDatetime() != null && bean.getDiscountPriceEndDatetime() != null
        && bean.getDiscountPriceStartDatetime().after(bean.getDiscountPriceEndDatetime())) {
      errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.19"), bean.getCommodityCode()));
    }
  }
  
  private void checkSalesTermAndDiscountRules(CCommodityHeaderImport bean, List<String> errorMessageList) {
    if (bean.getSaleStartDatetime() != null) {
      if (bean.getDiscountPriceStartDatetime() != null && bean.getSaleStartDatetime().after(bean.getDiscountPriceStartDatetime())) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.22"), bean.getCommodityCode()));
      }
      if ((!bean.getSaleStartDatetime().equals(DateUtil.getMin())) && bean.getDiscountPriceStartDatetime() == null
          && bean.getDiscountPriceEndDatetime() != null) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.23"), bean.getCommodityCode()));
      }
    }
    if (bean.getSaleEndDatetime() != null) {
      if (bean.getDiscountPriceEndDatetime() != null && bean.getDiscountPriceEndDatetime().after(bean.getSaleEndDatetime())) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.24"), bean.getCommodityCode()));
      }
      if ((!bean.getSaleEndDatetime().equals(DateUtil.getMax())) && bean.getDiscountPriceEndDatetime() == null
          && bean.getDiscountPriceStartDatetime() != null) {
        errorMessageList.add(MessageFormat.format(Messages.getString("service.data.csv.CommodityHeaderImportDataSource.25"), bean.getCommodityCode()));
      }
    }
  }
  
  /**
   * 判断必须项是否为空
   */
  private void checkExist(List<String> errorMessageList){
    //  //判断CSV文件中商品名称是否存在
    if (existsCommodityName) {
      // 商品名称必须输入
      if (StringUtil.isNullOrEmpty(header.getCommodityName())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.2")));
      }else{
        // 商品名称特殊字符转换：（单引号变换为 ‘）、（双引号变换为　“）、（百分号变换为　％）、（ 逗号变换为 　，）
        header.setCommodityName(StringUtil.parse(header.getCommodityName()));
      }
    }
    if (existsCommodityNameEn) {
      // 商品名称英文必须输入
      if (StringUtil.isNullOrEmpty(header.getCommodityNameEn())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.3")));
      }else{
        header.setCommodityNameEn(StringUtil.parse(header.getCommodityNameEn()));
      }
    }
    if (existsCommodityNameJp) {
      // 商品名称日文必须输入
      if (StringUtil.isNullOrEmpty(header.getCommodityNameJp())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.86")));
      }else{
        header.setCommodityNameJp(StringUtil.parse(header.getCommodityNameJp()));
      }
    }
    
    // 判断CSV文件中代表SKU単価列是否存在
    if (existsRepresentSkuUnitPrice) {
      // 存在则必须输入
      if (header.getRepresentSkuUnitPrice() == null) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.5")));
      }
    }
    //判断CSV文件中商品说明2是否存在
    if (existsCommodityDescription2) {
      // 存在则必须输入
      if (StringUtil.isNullOrEmpty(header.getCommodityDescription2())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.7")));
        // 商品说明2长度必须大于5
      } else if (header.getCommodityDescription2().length() < 5) {
        errorMessageList.add(Message.get(CsvMessage.COMMODITY_DESCRIPTION2_LENGTH_REQUEST_GREATER, header.getCommodityCode()));
      }
    }
    // 将返品不可フラグ的值转换成二进制
    String returnFlg = Integer.toBinaryString(header.getReturnFlg().intValue());
    // 顾客退货
    String returnFlgCust;
    // 供货商退货
    String returnFlgSupp;
    // 供货商换货
    String changeFlgSupp;
    if (returnFlg.length() < 2) {
      changeFlgSupp = "0";
      returnFlgSupp = "0";
      returnFlgCust = returnFlg;
    } else if (returnFlg.length() == 2) {
      char str[] = returnFlg.toCharArray();
      changeFlgSupp = "0";
      returnFlgSupp = "" + str[0];
      returnFlgCust = "" + str[1];
    } else {
      char str[] = returnFlg.toCharArray();
      changeFlgSupp = "" + str[0];
      returnFlgSupp = "" + str[1];
      returnFlgCust = "" + str[2];
    }
    // 判断CSV文件中供货商换货列是否存在
    if (existsReturnFlgCust) {
      // 存在必须输入
      if (StringUtil.isNullOrEmpty(header.getReturnFlgCust())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.83")));
        // 供货商换货必须为1或0
      } else if (!header.getReturnFlgCust().equals("1") && !header.getReturnFlgCust().equals("0")) {
        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.83"), "0", "1"));
      } else if (header.getReturnFlgCust().equals("1") || header.getReturnFlgCust().equals("0")) {
        returnFlgCust = header.getReturnFlgCust();
      }
    }
    // 判断CSV文件中供货商退货列是否存在
    if (existsReturnFlgSupp) {
      // 存在必须输入
      if (StringUtil.isNullOrEmpty(header.getReturnFlgSupp())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.84")));
        // 供货商退货必须为1或0
      } else if (!header.getReturnFlgSupp().equals("1") && !header.getReturnFlgSupp().equals("0")) {
        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.84"), "0", "1"));
      } else if (header.getReturnFlgSupp().equals("1") || header.getReturnFlgSupp().equals("0")) {
        returnFlgSupp = header.getReturnFlgSupp();
      }
    }
    // 判断CSV文件中顾客退货列是否存在
    if (existsChangeFlgSupp) {
      // 存在必须输入
      if (StringUtil.isNullOrEmpty(header.getChangeFlgSupp())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.85")));
        // 顾客退货必须为1或0
      } else if (!header.getChangeFlgSupp().equals("1") && !header.getChangeFlgSupp().equals("0")) {
        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.85"), "0", "1"));
      } else if (header.getChangeFlgSupp().equals("1") || header.getChangeFlgSupp().equals("0")) {
        changeFlgSupp = header.getChangeFlgSupp();
      }
    }
    // 将供货商换货、供货商退货、顾客退货拼接成一个三位的二进制数
    returnFlg = changeFlgSupp + returnFlgSupp + returnFlgCust;
    // 将二进制数转换成十进制的数赋给返品不可フラグ
    header.setReturnFlg(Integer.valueOf(returnFlg, 2).longValue());
    
//    if (existsReturnFlg) {
//      // 存在必须输入
//      if (header.getReturnFlg() == null) {
//        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.21")));
//        // 返品不可フラグ必须为1或0
//      } else if (header.getReturnFlg() != 1 && header.getReturnFlg() != 0) {
//        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE,Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.21"), "0", "1"));
//      }
//    }
    //判断CSV文件中品牌是否存在
    if (existsBrandCode) {
      // 存在必须输入
      if (StringUtil.isNullOrEmpty(header.getBrandCode())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.26")));
      }
    }
    // CSV文件存在商品期限管理列
    if (existsShelfLifeFlag) {
      if (header.getShelfLifeFlag() == null) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.79")));
      } else {
        if (header.getShelfLifeFlag() != 0 && header.getShelfLifeFlag() != 1 && header.getShelfLifeFlag() != 2) {
          errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.79"), "0", "2"));
        }
      }
    }
    // CSV文件存在大物フラグ列
    if (existsBigflag) {
      // 存在列，则不能为空
      if (header.getBigFlag() == null) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.82")));
      // 必须等于0或1
      } else if (header.getBigFlag() != 1 && header.getBigFlag() != 0) {
        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.82"), "0", "1"));
      }
    }
    if (existsWarningFlag) {
      // ワーニング区分必须为0、1或2
      if (!StringUtil.isNullOrEmpty(header.getWarningFlag())) {
        if (!header.getWarningFlag().equals("0") && !header.getWarningFlag().equals("1") && !header.getWarningFlag().equals("2")) {
          errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.22"), "0", "2"));
        }
      }
    }
    // リードタイム为0到99的数值
    if (header.getLeadTime() != null) {
      if (header.getLeadTime() < 0 || header.getLeadTime() > 99) {
        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CommodityHeaderImportDataSource.66"), "0", "99"));
      }
    }
    // CSV文件存在EC販売フラグ列
    if (existsSaleFlgEc) {
      if (header.getSaleFlgEc() == null) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.19"))); 
      } else {
        if (header.getSaleFlgEc() != 0 && header.getSaleFlgEc() != 1 && header.getSaleFlgEc() != 2) {
          errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.19"), "0", "2"));
        }
      }
    }
    // CSV文件存在取引先コード列
    if (existsSupplierCode) {
      if (StringUtil.isNullOrEmpty(header.getSupplierCode())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.30"))); 
      }
    }
    // CSV文件存在仕入担当者コード列
    if (existsbuyerCode) {
      if(StringUtil.isNullOrEmpty(header.getBuyerCode())){
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityHeaderCsvSchema.31"))); 
      }
    }
    //判断CSV文件中SKU名称是否存在
    if (existsSkuName) {
      //存在必须输入
      if (StringUtil.isNullOrEmpty(detail.getSkuName())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityDetailCsvSchema.1")));
      } else {
        // 20120305 os013 add start
        // SKU名称特殊字符转换：（单引号变换为 ‘）、（双引号变换为　“）、（百分号变换为　％）、（ 逗号变换为 　，）
        // 重新赋值
        detail.setSkuName(StringUtil.parse(detail.getSkuName()));
        // 20120305 os013 add end
        
        // SKU名称最大48或者47（第24，25字节为一个汉字时）字节
        String skuName = StringUtil.subStringByByte(detail.getSkuName(), 48);
       if (!detail.getSkuName().equals(skuName)) {
         errorMessageList.add(Messages.getString("service.data.csv.CommodityDetailImportDataSource.33"));
       }
      }
    }
    //判断CSV文件中定価フラグ是否存在
    if (existsFixedPriceFlag) {
      //存在必须输入
      if(detail.getFixedPriceFlag()==null){
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CCommodityDetailCsvSchema.3")));
      //定価フラグ必须为0或1
      } else  if(detail.getFixedPriceFlag()!=0&&detail.getFixedPriceFlag()!=1){
        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CCommodityDetailCsvSchema.3"),"0","1"));    
      }
    }
    //判断CSV文件中取扱いフラグ(EC)是否存在
    if (existsUseFlg) {
      // 存在必须输入
      if (detail.getUseFlg() == null) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CommodityDetailImportDataSource.30")));    
      }else if (detail.getUseFlg() != 0 && detail.getUseFlg() != 1) {
        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CommodityDetailImportDataSource.30"),"0","1"));    
      }
    }
    //判断CSV文件中取扱いフラグ(TMALL)是否存在
    if (existsTmallUseFlg) {
      // 存在必须输入
      if (detail.getTmallUseFlg() == null) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages.getString("service.data.csv.CommodityDetailImportDataSource.31")));    
      }else if (detail.getTmallUseFlg() != 0 && detail.getTmallUseFlg() != 1) {
        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CommodityDetailImportDataSource.31"),"0","1"));
      }
    }
    // 2014/04/30 京东WBS对应 ob_卢 add start
    //判断CSV文件中取扱いフラグ(京东)是否存在
    if (existsJdUseFlg) {
      if (detail.getJdUseFlg() != null) {
        if (!JdUseFlg.DISABLED.longValue().equals(detail.getJdUseFlg()) 
            && !JdUseFlg.ENABLED.longValue().equals(detail.getJdUseFlg())) {
          errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages.getString("service.data.csv.CommodityDetailImportDataSource.51"),"0","1"));
        }
      }
    }
    // 2014/04/30 京东WBS对应 ob_卢 add end
    // 判断税率区分存在,等于false时不存在，报错
    if(existsTaxClass){
      existsTaxClass = false;
      List<NameValue> taxClassList = DIContainer.getTaxClassValue().getTaxClass();
      for (int i = 0; i < taxClassList.size(); i++) {
        if (taxClassList.get(i).getValue().equals(detail.getTaxClass())) {
          existsTaxClass = true;
        }
      }
      if (!existsTaxClass) {
        errorMessageList.add(Message.get(CsvMessage.NOT_EXIST, Messages.getString("service.data.csv.CommodityDetailImportDataSource.32"),detail.getTaxClass()));    
      }
    }
  }
  
  // 20130703 shen add start
  private int executeUpdateSyncFlag() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;
    params.add(getCondition().getSyncFlagTmall());
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());
    params.add(header.getCommodityCode());

    pstmt = updateSyncFlag;

    logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }
  
  private String getUpdateSyncFlagQuery() {
    String updateSql = " UPDATE " + HEADER_TABLE_NAME
      + " SET SYNC_FLAG_EC = 1, SYNC_FLAG_TMALL = ?, UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ? ";
    
    return updateSql;
  }
  // 20130703 shen add end
  
  // 2014/05/06 京东WBS对应 ob_卢 add start
  private int executeUpdateSyncFlagJd() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;
    params.add(getCondition().getSyncFlagJd());
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());
    params.add(header.getCommodityCode());

    pstmt = updateSyncFlagJd;

    logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }

  
  private String getUpdateSyncFlagJdQuery() {
  String updateSql = " UPDATE " + HEADER_TABLE_NAME
    + " SET SYNC_FLAG_EC = 1, SYNC_FLAG_JD = ?, UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ? ";
    return updateSql;
  }
  // 2014/05/06 京东WBS对应 ob_卢 add end
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
  
  private CommodityPriceChangeHistory setCommodityPriceChangeHistory(CommodityPriceChangeHistory commodityPriceChangeHistory, CCommodityDetail detail, String newEcDiscountPrice, String newJdPrice, String newEcPrice, String newTmallPrice) {

    CatalogService service = ServiceLocator.getCatalogService(getCondition().getLoginInfo());
    SearchResult<CCommodityDetail> cCommodityDetailSearchResult = service.searchCCommodityDetail(detail.getCommodityCode());
    
    String ecDiscountPrice = "", ecPrice = "", tmallPrice = "", jdPrice = ""; 
    if(cCommodityDetailSearchResult.getRows().get(0).getUnitPrice() != null) {
      ecPrice = cCommodityDetailSearchResult.getRows().get(0).getUnitPrice().toString();
    }
    if(cCommodityDetailSearchResult.getRows().get(0).getDiscountPrice() != null) {
        ecDiscountPrice = cCommodityDetailSearchResult.getRows().get(0).getDiscountPrice().toString();
    }
    if(cCommodityDetailSearchResult.getRows().get(0).getTmallUnitPrice() != null) {
        tmallPrice = cCommodityDetailSearchResult.getRows().get(0).getTmallUnitPrice().toString();
    }
    // TmallDiscountPrice前台显示为jd售价
    if(cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice() != null) {
        jdPrice = cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice().toString();
    }
    commodityPriceChangeHistory.setCommodityCode(detail.getCommodityCode());
    commodityPriceChangeHistory.setSubmitTime(DateUtil.getSysdate());
    commodityPriceChangeHistory.setResponsiblePerson(getCondition().getLoginInfo().getName());
    if(StringUtil.hasValue(ecPrice)) {
      commodityPriceChangeHistory.setOldOfficialPrice(new BigDecimal(ecPrice));
    } else {
      commodityPriceChangeHistory.setOldOfficialPrice(null);
    }
    if(StringUtil.hasValue(newEcPrice)) {
      commodityPriceChangeHistory.setNewOfficialPrice(new BigDecimal(newEcPrice));
    } else {
      commodityPriceChangeHistory.setNewOfficialPrice(null);
    }
    if(StringUtil.hasValue(ecDiscountPrice)) {
      commodityPriceChangeHistory.setOldOfficialSpecialPrice(new BigDecimal(ecDiscountPrice));
    } else {
      commodityPriceChangeHistory.setOldOfficialSpecialPrice(null);
    }
    if(StringUtil.hasValue(newEcDiscountPrice)) {
      commodityPriceChangeHistory.setNewOfficialSpecialPrice(new BigDecimal(newEcDiscountPrice));
    } else {
      commodityPriceChangeHistory.setNewOfficialSpecialPrice(null);
    }
    if(StringUtil.hasValue(tmallPrice)) {
      commodityPriceChangeHistory.setOldTmallPrice(new BigDecimal(tmallPrice));
    } else {
      commodityPriceChangeHistory.setOldTmallPrice(null);
    }
    if(StringUtil.hasValue(newTmallPrice)) {
      commodityPriceChangeHistory.setNewTmallPrice(new BigDecimal(newTmallPrice));
    } else {
      commodityPriceChangeHistory.setNewTmallPrice(null);
    }
    if(StringUtil.hasValue(jdPrice)) {
      commodityPriceChangeHistory.setOldJdPrice(new BigDecimal(jdPrice));
    } else {
      commodityPriceChangeHistory.setOldJdPrice(null);
    }
    if(StringUtil.hasValue(newJdPrice)) {
      commodityPriceChangeHistory.setNewJdPrice(new BigDecimal(newJdPrice));
    } else {
      commodityPriceChangeHistory.setNewJdPrice(null);
    }
    commodityPriceChangeHistory.setOrmRowid(DatabaseUtil.generateSequence(SequenceType.COMMODITY_PRICE_CHANGE_HISTORY_SEQ));
    commodityPriceChangeHistory.setCreatedUser(getCondition().getLoginInfo().getRecordingFormat());
    commodityPriceChangeHistory.setCreatedDatetime(DateUtil.getSysdate());
    commodityPriceChangeHistory.setUpdatedUser(getCondition().getLoginInfo().getRecordingFormat());
    commodityPriceChangeHistory.setUpdatedDatetime(DateUtil.getSysdate());
    // 0为未审核，1为已审核
    commodityPriceChangeHistory.setReviewOrNotFlg(new Long(0));
    commodityPriceChangeHistory.setEcProfitMargin(ecGrossProfitRate);
    commodityPriceChangeHistory.setEcSpecialProfitMargin(ecSpecialGrossProfitRate);
    commodityPriceChangeHistory.setTmallProfitMargin(tmallGrossProfitRate);
    commodityPriceChangeHistory.setJdProfitMargin(jdGrossProfitRate);
    return commodityPriceChangeHistory;
    
  }
  
  public BigDecimal calculateGrossProfitRate(BigDecimal priceReadyForCheck, CatalogService catalogService, CCommodityDetail cCommodityDetail) {
    
    SearchResult<CCommodityDetail> cCommodityDetailSearchResult = catalogService.searchCCommodityDetail(cCommodityDetail.getSkuCode().replace("'", ""));
    BigDecimal averageCost = null;
    // 如果csv导入文件中含有“平均计算成本”，那么优先取csv导入文件的成本。
    if(detail.getAverageCost() != null) {
      averageCost = detail.getAverageCost();
    } else {
      averageCost = cCommodityDetailSearchResult.getRows().get(0).getAverageCost();
    }
    String taxClass = cCommodityDetailSearchResult.getRows().get(0).getTaxClass();
    BigDecimal taxClassB = new BigDecimal(taxClass);
    BigDecimal taxClassPercent = taxClassB.multiply(new BigDecimal(0.01));
    // 毛利率 = 售价-平均移动成本*（1+税率））/售价
    BigDecimal grossProfitRate = priceReadyForCheck.subtract(taxClassPercent.add(new BigDecimal(1)).multiply(averageCost)).divide(priceReadyForCheck, 2, RoundingMode.HALF_UP);
    return grossProfitRate;
  }
}
