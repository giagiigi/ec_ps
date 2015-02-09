package jp.co.sint.webshop.service.data.csv;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.CCommodityDetailQuery;
import jp.co.sint.webshop.service.catalog.CommodityPriceChangeHistoryCondition;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.code.SequenceType;
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
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
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

public class CCommodityDetailImportDataSource extends
    SqlImportDataSource<CCommodityDetailCsvSchema, CCommodityDetailImportCondition> {

  private static final String DATAIL_TABLE_NAME = DatabaseUtil.getTableName(CCommodityDetail.class);

  private static final String HEADER_TABLE_NAME = DatabaseUtil.getTableName(CCommodityHeader.class);

  // private static final String HEADER_TABLE_NAME =
  // DatabaseUtil.getTableName(CCommodityHeader.class);
  // 20120207 os013 add start
  private static final String STOCK_TABLE_NAME = DatabaseUtil.getTableName(Stock.class);

  /** ヘッダUPDATE用Statement */
  private PreparedStatement updateHeaderStatementOne = null;
  private PreparedStatement updateHeaderStatementTwo = null;

  // 改价审核用毛利率
  BigDecimal ecGrossProfitRate = null, ecSpecialGrossProfitRate = null, tmallGrossProfitRate = null, jdGrossProfitRate = null;
  // 20120207 os013 add end
  /** 商品詳細INSERT用Statement */
  // private PreparedStatement insertDetailStatement = null;
  /** 商品詳細UPDATE用Statement */
  //private PreparedStatement updateDetailStatement = null;

  /** 商品ヘッダINSERT用Statement */
  // private PreparedStatement insertHeaderStatement = null;
  /** 商品ヘッダUPDATE用Statement */
  // private PreparedStatement updateHeaderStatement = null;
  // 20120207 os013 add start
  /** 在库UPDATE用Statement */
  //private PreparedStatement updateStockStatement = null;

  /** 在库SELECT用Statement */
  private PreparedStatement selectStockStatement = null;

  // 20120207 os013 add end
  /** 商品明细SELECT用Statement */
  private PreparedStatement selectDetailStatement = null;
  
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
  
  // private PreparedStatement selectHeaderStatement = null;

  private CCommodityDetail detail = null;

  // 20120206 os013 add start
  private Stock stock = null;

  private List<CsvColumn> detailColumns = new ArrayList<CsvColumn>();

  private List<CsvColumn> stockColumns = new ArrayList<CsvColumn>();

  boolean existsDetail = false;

  boolean existsStock = false;

  // 用于判断SKU是否同期过
  boolean existsTmallSkuId = false;

  // 20120206 os013 add end
  
  // 20130703 shen add start
  private PreparedStatement updateSyncFlag = null;
  // 20130703 shen add end
  
  // 2014/05/06 京东WBS对应 ob_卢 add start
  private PreparedStatement updateSyncFlagJd = null;
  // 2014/05/06 京东WBS对应 ob_卢 add end
  
  @Override
  protected void initializeResources() {
    Logger logger = Logger.getLogger(this.getClass());
    // logger.debug("INSERT statement: " + getInsertDetailQuery());
    // logger.debug("INSERT statement: " + getInsertHeaderQuery());
    // 商品明细表更新Query
    logger.debug("UPDATE statement: " + getUpdateDetailQuery());
    // logger.debug("UPDATE statement: " +
    // CatalogQuery.UPDATE_RESERVATIOIN_INFO);

    // 20120207 os013 add start
    // 在库表更新Query
    logger.debug("UPDATE statement: " + getUpdateStockQuery());
    // 商品详细表查询存在
    logger.debug("CHECK  statement: " + getSelectStockQuery());
    // 20120207 os013 add end

    // logger.debug("UPDATE statement: " + getUpdateHeaderQuery());
    // 在库表查询存在
    logger.debug("CHECK  statement: " + getSelectDetailQuery());
    // logger.debug("CHECK  statement: " + getSelectHeaderQuery());
    logger.debug("UPDATE statement: " + getUpdateHeaderQueryOne());
    logger.debug("UPDATE statement: " + getUpdateHeaderQueryTwo());
    try {
      // insertDetailStatement =
      // createPreparedStatement(getInsertDetailQuery());
      // updateDetailStatement =
      // createPreparedStatement(getUpdateDetailQuery());
      // insertHeaderStatement =
      // createPreparedStatement(getInsertHeaderQuery());
      // updateHeaderStatement =
      // createPreparedStatement(getUpdateHeaderQuery());
      // 商品详细表查询存在
      selectDetailStatement = createPreparedStatement(getSelectDetailQuery());
      updateHeaderStatementOne = createPreparedStatement(getUpdateHeaderQueryOne());
      updateHeaderStatementTwo = createPreparedStatement(getUpdateHeaderQueryTwo());
      // selectHeaderStatement =
      // createPreparedStatement(getSelectHeaderQuery());
      // 20120207 os013 add start
      // 在库表查询存在
      selectStockStatement = createPreparedStatement(getSelectStockQuery());
      // 20120207 os013 add end
      
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

  // 用于判断csv文件是否有SKU名称列
  boolean existsSkuName = false;

  // 用于判断csv文件是否有商品コード列
  boolean existsCommodityCode = false;

  // 用于判断csv文件是否有定価フラグ列
  boolean existsFixedPriceFlag = false;

  // 用于判断csv文件是否有取扱いフラグ(EC)列
  boolean existsUseFlg = false;

  // 用于判断csv文件是否有取扱いフラグ(TMALL)列
  boolean existsTmallUseFlg = false;
  
  // 2014/04/30 京东WBS对应 ob_卢 add start
  // 用于判断csv文件是否有取扱いフラグ(Jd)列
  boolean existsJdUseFlg = false;
  // 2014/04/30 京东WBS对应 ob_卢 add end

  // 用于判断csv文件是否有税率区分列
  boolean existsTaxClass = false;
  
  boolean isRepresentSku = false;

  public boolean setSchema(List<String> csvLine) {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    for (String column : csvLine) {
      // ショップコード
      if (column.equals("shop_code")) {
        detailColumns.add(new CsvColumnImpl("shop_code", Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.0"),
            CsvDataType.STRING, false, false, true, null));
        // SKUコード
      } else if (column.equals("sku_code")) {
        detailColumns.add(new CsvColumnImpl("sku_code", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.0"),
            CsvDataType.STRING, false, false, true, null));
        // SKU名称
      } else if (column.equals("sku_name")) {
        detailColumns.add(new CsvColumnImpl("sku_name", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.1"),
            CsvDataType.STRING, false, false, true, null));
        existsSkuName = true;
        // 商品コード
      } else if (column.equals("commodity_code")) {
        detailColumns.add(new CsvColumnImpl("commodity_code", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.2"),
            CsvDataType.STRING, false, false, true, null));
        existsCommodityCode = true;
        // 定価フラグ0：非定価　1：定価
      } else if (column.equals("fixed_price_flag")) {
        detailColumns.add(new CsvColumnImpl("fixed_price_flag", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.3"),
            CsvDataType.NUMBER));
        existsFixedPriceFlag = true;
        // 希望小売価格
      } else if (column.equals("suggeste_price")) {
        detailColumns.add(new CsvColumnImpl("suggeste_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.4"),
            CsvDataType.BIGDECIMAL));
        // 仕入価格
      } else if (column.equals("purchase_price")) {
        detailColumns.add(new CsvColumnImpl("purchase_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.5"),
            CsvDataType.BIGDECIMAL));
        // EC商品単価
      } else if (column.equals("unit_price")) {
        detailColumns.add(new CsvColumnImpl("unit_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.6"),
            CsvDataType.BIGDECIMAL));
        // EC特別価格
      } else if (column.equals("discount_price")) {
        detailColumns.add(new CsvColumnImpl("discount_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.7"),
            CsvDataType.BIGDECIMAL));
        // 規格1値のID(=TMALL属性値ID)
      } else if (column.equals("standard_detail1_id")) {
        detailColumns.add(new CsvColumnImpl("standard_detail1_id", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.8"), CsvDataType.STRING));
        // 規格1値の文字列(値のIDなければ、これを利用）
      } else if (column.equals("standard_detail1_name")) {
        detailColumns.add(new CsvColumnImpl("standard_detail1_name", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.9"), CsvDataType.STRING));
        // 規格1値の文字列(値のIDなければ、これを利用）(英文)
      } else if (column.equals("standard_detail1_name_en")) {
        detailColumns.add(new CsvColumnImpl("standard_detail1_name_en", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.99"), CsvDataType.STRING));
        // 規格1値の文字列(値のIDなければ、これを利用）(日文)
      } else if (column.equals("standard_detail1_name_jp")) {
        detailColumns.add(new CsvColumnImpl("standard_detail1_name_jp", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.100"), CsvDataType.STRING));
        // 規格2値のID(=TMALL属性値ID)
      } else if (column.equals("standard_detail2_id")) {
        detailColumns.add(new CsvColumnImpl("standard_detail2_id", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.10"), CsvDataType.STRING));
        // 規格2値の文字列(値のIDなければ、これを利用）
      } else if (column.equals("standard_detail2_name")) {
        detailColumns.add(new CsvColumnImpl("standard_detail2_name", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.11"), CsvDataType.STRING));
        // 規格2値の文字列(値のIDなければ、これを利用）(英文)
      } else if (column.equals("standard_detail2_name_en")) {
        detailColumns.add(new CsvColumnImpl("standard_detail2_name_en", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.101"), CsvDataType.STRING));
        // 規格2値の文字列(値のIDなければ、これを利用）(日文)
      } else if (column.equals("standard_detail2_name_jp")) {
        detailColumns.add(new CsvColumnImpl("standard_detail2_name_jp", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.102"), CsvDataType.STRING));
        // 商品重量(単位はKG)、未設定の場合、商品HEADの重量を利用。
      } else if (column.equals("weight")) {
        detailColumns.add(new CsvColumnImpl("weight", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.12"),
            CsvDataType.BIGDECIMAL));
        // 容量
      } else if (column.equals("volume")) {
        detailColumns.add(new CsvColumnImpl("volume", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.13"),
            CsvDataType.BIGDECIMAL));
        // 容量単位
      } else if (column.equals("volume_unit")) {
        detailColumns.add(new CsvColumnImpl("volume_unit", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.14"),
            CsvDataType.STRING));
        // 取扱いフラグ(EC)
      } else if (column.equals("use_flg")) {
        detailColumns.add(new CsvColumnImpl("use_flg", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.15"),
            CsvDataType.NUMBER));
        existsUseFlg = true;
        // 最小仕入数
      } else if (column.equals("minimum_order")) {
        detailColumns.add(new CsvColumnImpl("minimum_order", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.16"),
            CsvDataType.NUMBER));
        // 最大仕入数
      } else if (column.equals("maximum_order")) {
        detailColumns.add(new CsvColumnImpl("maximum_order", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.17"),
            CsvDataType.NUMBER));
        // 最小単位の仕入数
      } else if (column.equals("order_multiple")) {
        detailColumns.add(new CsvColumnImpl("order_multiple", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.18"),
            CsvDataType.NUMBER));
        // 在庫警告日数
      } else if (column.equals("stock_warning")) {
        detailColumns.add(new CsvColumnImpl("stock_warning", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.19"),
            CsvDataType.NUMBER));
        // TMALLのSKUのID
      } else if (column.equals("tmall_sku_id")) {
        detailColumns.add(new CsvColumnImpl("tmall_sku_id", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.20"),
            CsvDataType.NUMBER));
        // TMALLの商品単価
      } else if (column.equals("tmall_unit_price")) {
        detailColumns.add(new CsvColumnImpl("tmall_unit_price",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.21"), CsvDataType.BIGDECIMAL));
        // TMALLの特別価格
      } else if (column.equals("tmall_discount_price")) {
        detailColumns.add(new CsvColumnImpl("tmall_discount_price", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.22"), CsvDataType.BIGDECIMAL));
        // 下限売価
      } else if (column.equals("min_price")) {
        detailColumns.add(new CsvColumnImpl("min_price", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.23"),
            CsvDataType.BIGDECIMAL));
        // 取扱いフラグ(TMALL)
      } else if (column.equals("tmall_use_flg")) {
        detailColumns.add(new CsvColumnImpl("tmall_use_flg", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.31"),
            CsvDataType.NUMBER));
        existsTmallUseFlg = true;
        // 入数
      } else if (column.equals("inner_quantity")) {
        detailColumns.add(new CsvColumnImpl("inner_quantity", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.27"),
            CsvDataType.NUMBER));
        // 入数単位
      } else if (column.equals("inner_quantity_unit")) {
        detailColumns.add(new CsvColumnImpl("inner_quantity_unit", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.28"), CsvDataType.STRING));
        // WEB表示単価単位入数
      } else if (column.equals("inner_unit_for_price")) {
        detailColumns.add(new CsvColumnImpl("inner_unit_for_price", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.29"), CsvDataType.NUMBER));
        // WEB表示単価単位容量
      } else if (column.equals("volume_unit_for_price")) {
        detailColumns.add(new CsvColumnImpl("volume_unit_for_price", Messages
            .getCsvKey("service.data.csv.CCommodityDetailCsvSchema.30"), CsvDataType.BIGDECIMAL));
        // 税率区分
        
      } else if (column.equals("tax_class")) {
        detailColumns.add(new CsvColumnImpl("tax_class", Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.32"),
            CsvDataType.STRING));
        existsTaxClass = true;
        // 箱规
      } else if (column.equals("unit_box")){
        detailColumns.add(new CsvColumnImpl("unit_box",
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.103"), CsvDataType.NUMBER));
        // 20120206 os013 add start
        // 在庫数量
      } else if (column.equals("stock_quantity")) {
        stockColumns.add(new CsvColumnImpl("stock_quantity", Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.2"),
            CsvDataType.NUMBER));
        // 引当数量
      } else if (column.equals("allocated_quantity")) {
        stockColumns.add(new CsvColumnImpl("allocated_quantity", Messages
            .getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.3"), CsvDataType.NUMBER));
        // 予約数量
      } else if (column.equals("reserved_quantity")) {
        stockColumns.add(new CsvColumnImpl("reserved_quantity", Messages
            .getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.4"), CsvDataType.NUMBER));
        // 予約上限数
      } else if (column.equals("reservation_limit")) {
        stockColumns.add(new CsvColumnImpl("reservation_limit", Messages
            .getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.5"), CsvDataType.NUMBER));
        // 注文毎予約上限数
      } else if (column.equals("oneshot_reservation_limit")) {
        stockColumns.add(new CsvColumnImpl("oneshot_reservation_limit", Messages
            .getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.6"), CsvDataType.NUMBER));
        // 在庫閾値
      } else if (column.equals("stock_threshold")) {
        stockColumns.add(new CsvColumnImpl("stock_threshold",
            Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.7"), CsvDataType.NUMBER));
        // 総在庫
      }else if (column.equals("stock_total")) {
        stockColumns.add(new CsvColumnImpl("stock_total", Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.8"),
            CsvDataType.NUMBER));
        // TMALL在庫数
      } else if (column.equals("stock_tmall")) {
        stockColumns.add(new CsvColumnImpl("stock_tmall", Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.9"),
            CsvDataType.NUMBER));
        // TMALL引当数
      } else if (column.equals("allocated_tmall")) {
        stockColumns.add(new CsvColumnImpl("allocated_tmall", Messages
            .getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.10"), CsvDataType.NUMBER));
        // 在庫リーバランスフラグ
      } else if (column.equals("share_recalc_flag")) {
        stockColumns.add(new CsvColumnImpl("share_recalc_flag", Messages
            .getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.11"), CsvDataType.NUMBER));
        // EC在庫割合(0-100)
      } else if (column.equals("share_ratio")) {
     // 2014/06/11 库存更新对应 ob_yuan update start
        //stockColumns.add(new CsvColumnImpl("share_ratio", Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.12"),
        //    CsvDataType.NUMBER));
        stockColumns.add(new CsvColumnImpl("stock_ratio", Messages.getCsvKey("service.data.csv.CCommodityStockDataCsvSchema.12"),
                CsvDataType.STRING));
     // 2014/06/11 库存更新对应 ob_yuan update end
        //
      }
      // 2014/04/29 京东WBS对应 ob_卢 add start
      else if (column.equals("jd_use_flg")) {
        detailColumns.add(new CsvColumnImpl("jd_use_flg", 
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.104"),
            CsvDataType.NUMBER));
        existsJdUseFlg = true;
      }
      // 2014/04/29 京东WBS对应 ob_卢 add end
      // 20120206 os013 add end
      else if (column.equals("average_cost")) {
        detailColumns.add(new CsvColumnImpl("average_cost", 
            Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.105"),
            CsvDataType.BIGDECIMAL)); 
      }
    }
    // 20120206 os013 add start
    columns.addAll(detailColumns);
    columns.addAll(stockColumns);
    // 20120206 os013 add end
    getSchema().setColumns(columns);
    try {
      // 商品明细表更新Query
      //updateDetailStatement = createPreparedStatement(getUpdateDetailQuery());
      // 在库表更新Query
      //updateStockStatement = createPreparedStatement(getUpdateStockQuery());
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
    return true;
  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();
    boolean ecGrossProfitRateLessThan0 = false, ecSpecialPriceGrossProfitRateLessThan0 = false, tmallPriceGrossProfitRateLessThan0 = false, jdPriceGrossProfitRateLessThan0 = false;

    try {
      detail = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CCommodityDetail.class);
      // 20120207 os013 add start
      stock = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), Stock.class);

      // SKU编号必须输入
      if (detail.getSkuCode() == null) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.REQUEST_ITEM, Messages
                .getString("service.data.csv.CCommodityDetailCsvSchema.0"))));
        return summary;
      }
      
      //JD售价必须输入
//      if(detail.getTmallDiscountPrice()==null){
//        summary.getErrors().add(new ValidationResult(null, null, "JD售价必须输入"));
//        return summary;
//      }

      /**************************check毛利率，如果导入价格导致毛利率<0报错（毛利率 = （售价-平均移动成本*（1+税率））/售价）**********************************/

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
        if (cCommodityHeaderSearchResult.getRows().get(0).getClearCommodityType() != 1) {
          // check Ec售价的毛利率
          // 如果有Ec特价（且在特价期间内）的话，按Ec特价算毛利率，不check Ec售价。
          if (detail.getDiscountPrice() != null && 
              ((cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime() != null
                  && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime() != null
                  && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime().before(DateUtil.getSysdate()) && cCommodityHeaderSearchResult
                  .getRows().get(0).getDiscountPriceEndDatetime().after(DateUtil.getSysdate()))
              || (cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime() != null
                  && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime() == null && cCommodityHeaderSearchResult
                  .getRows().get(0).getDiscountPriceStartDatetime().before(DateUtil.getSysdate()))
              || (cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime() == null
                  && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime() != null && cCommodityHeaderSearchResult
                  .getRows().get(0).getDiscountPriceEndDatetime().after(DateUtil.getSysdate())))) {
            ; // Do nothing
          } else {
            if (newEcPricePriceReadyForCheck != null) {
              ecGrossProfitRate = calculateGrossProfitRate(newEcPricePriceReadyForCheck, service, detail);
              // 毛利率小于0，报错
              if (ecGrossProfitRate.compareTo(new BigDecimal(0)) == -1) {
                ecGrossProfitRateLessThan0 = true;
              }
            }
            // 如果新价格为null，那么用旧价格算毛利率，这时算出的毛利率不去报警。
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
            // 如果新价格为null，那么用旧价格算毛利率，这时算出的毛利率不去报警。
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
          // 如果新价格为null，那么用旧价格算毛利率，这时算出的毛利率不去报警。
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
          // 如果新价格为null，那么用旧价格算毛利率，这时算出的毛利率不去报警。
          else if(cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice() != null) {
            jdGrossProfitRate = calculateGrossProfitRate(cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice(), service, detail);
          }
          else {
            jdGrossProfitRate = null;
          }
        }
      }
      
      
      /*************************check毛利率，如果导入价格导致毛利率<0则报错（（售价-平均移动成本*（1+税率））/售价）***********************/
      
      detail.setSkuCode(detail.getSkuCode().replace("'", ""));
      stock.setSkuCode(stock.getSkuCode().replace("'", ""));
      // sku编号在商品详细中存在
      existsDetail = exists(selectDetailStatement, detail.getSkuCode());
      // sku编号在在库中存在
      existsStock = exists(selectStockStatement, detail.getSkuCode());
      if ((!existsDetail) || (!existsStock)) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SKU_CODE_NOT_EXIST, detail.getSkuCode())));
        return summary;
      }
      // 20120316 os013 add start
      // 判断SKU是否同期过
      SimpleQuery tmallSkuIdExistsQuery = new SimpleQuery("SELECT TMALL_SKU_ID FROM C_COMMODITY_DETAIL WHERE  SKU_CODE = ?");
      tmallSkuIdExistsQuery.setParameters(detail.getSkuCode());
      Object object = executeScalar(tmallSkuIdExistsQuery);
      if (object == null) {
        existsTmallSkuId = false;
      } else {
        existsTmallSkuId = true;
      }
      // 20120316 os013 add end
      
      SimpleQuery isRepresentSkuQuery = new SimpleQuery("SELECT REPRESENT_SKU_UNIT_PRICE FROM C_COMMODITY_HEADER WHERE  REPRESENT_SKU_CODE = ?");
      isRepresentSkuQuery.setParameters(detail.getSkuCode());
      Object objectSku = executeScalar(isRepresentSkuQuery);
      if (objectSku == null) {
        isRepresentSku = false;
      } else {
        isRepresentSku = true;
      }
      
      SimpleQuery commodityCodeExistsQuery = new SimpleQuery("SELECT COMMODITY_CODE FROM C_COMMODITY_DETAIL WHERE  SKU_CODE = ?");
      commodityCodeExistsQuery.setParameters(detail.getSkuCode());
      object = executeScalar(commodityCodeExistsQuery);
      if (object == null) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
                .getString("service.data.csv.CCommodityDetailCsvSchema.2"))));
      } else {
        detail.setCommodityCode(object.toString());
      }
      // 20120207 os013 add end
      // DatabaseUtil.setUserStatus(detail, getCondition().getLoginInfo());
      //
      // summary.getErrors().addAll(BeanValidator.partialValidate(detail).getErrors());
    } catch (CsvImportException e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }
    List<String> errorMessageList = new ArrayList<String>();
    // 判断必须项是否为空
    checkExist(errorMessageList);

    for (String error : errorMessageList) {
      summary.getErrors().add(new ValidationResult(null, null, error));
    }
    // 10.1.1 10019 追加 ここから
    checkMinusNumber(summary);
    // 10.1.1 10019 追加 ここまで

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
 // 2014/06/11 库存更新对应 ob_yuan add start
    if (StringUtil.hasValue(stock.getStockRatio())) {
      if (!checkStockRatio(stock.getStockRatio())) {
        summary.getErrors().add(new ValidationResult(null, null, "库存比例导入时，必须按照【EC:TM:JD】的格式，而且合计值必须为100"));
        return summary;
      }
    }
 // 2014/06/11 库存更新对应 ob_yuan add end
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
      summary.getErrors().add(new ValidationResult(null, null, "EC售价的毛利率为:" + ecGrossProfitRate.toString() + "，小于0。"));
    }
    if(ecSpecialPriceGrossProfitRateLessThan0) {
      summary.getErrors().add(new ValidationResult(null, null, "EC特价的毛利率为:" + ecSpecialGrossProfitRate.toString() + "，小于0。"));
    }
    if(tmallPriceGrossProfitRateLessThan0) {
      summary.getErrors().add(new ValidationResult(null, null, "Tmall售价的毛利率为:" + tmallGrossProfitRate.toString() + "，小于0。"));
    }
    if(jdPriceGrossProfitRateLessThan0) {
      summary.getErrors().add(new ValidationResult(null, null, "JD售价的毛利率为:" + jdGrossProfitRate.toString() + "，小于0。"));
    }
    // 20120319 os013 add end
    // 返回已同期和毛利率错误
    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());


    /*********************************** 向commodity_price_change_history表插入信息 *****************************************/
    String newEcDiscountPrice = "", newJdPrice = "", newEcPrice = "", newTmallPrice = "";

    CatalogService catalogService = ServiceLocator.getCatalogService(getCondition().getLoginInfo());

    SearchResult<CCommodityDetail> cCommodityDetailSearchResult = catalogService.searchCCommodityDetail(detail.getCommodityCode());

    SearchResult<CCommodityHeader> cCommodityHeaderSearchResult = catalogService.searchCCommodityHeader(detail.getCommodityCode());
    
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
        // 如果原淘宝售价不为null且新的淘宝售价与表中的淘宝售价不同
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
        // 如果原EC售价不为null且新的ec售价与表中的ec售价不同
        if (detail.getUnitPrice().compareTo(cCommodityDetailSearchResult.getRows().get(0).getUnitPrice()) != 0) {
          newEcPrice = detail.getUnitPrice().toString();
        }
      } else {
        // 如果原EC售价为null
        newEcPrice = detail.getUnitPrice().toString();
      }
    }

    CommodityPriceChangeHistory commodityPriceChangeHistory = new CommodityPriceChangeHistory();
    commodityPriceChangeHistory = setCommodityPriceChangeHistory(commodityPriceChangeHistory, detail,
        newEcDiscountPrice, newJdPrice, newEcPrice, newTmallPrice);

    // 4个价格中至少1个变动了才执行update操作
    ServiceResult serviceResult = null;
    if (StringUtil.hasValueAnyOf(newEcDiscountPrice, newJdPrice, newEcPrice, newTmallPrice)) {
      if(cCommodityHeaderSearchResult.getRows().get(0).getClearCommodityType() != 1) {
        //if(detail.getAverageCost() != null) {
          serviceResult = catalogService.insertCommodityPriceChangeHistory(commodityPriceChangeHistory);
        //}
      }
    }
    if (serviceResult != null && serviceResult.hasError()) {
      throw new CsvImportException();
    }
    /********************************** 向commodity_price_change_history表插入信息 end 2014/07/22 ***************************/


      try {
      // 商品明细表更新
      int updDetailCount = executeDetailUpdate(existsDetail);
      if (updDetailCount != 1) {
        throw new CsvImportException();
      }
      // 在库表更新
      int updStockCount = executeStockUpdate(existsStock);
      if (updStockCount != 1) {
        throw new CsvImportException();
      }
      // 基本表更新
      int updHeaderCount = executeUpdateHeader();
      if (updHeaderCount != 1) {
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
//        CCommodityHeader cHeader = cHeaderDao.load(DIContainer.getWebshopConfig().getSiteShopCode(), detail.getCommodityCode());
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
      // pstmt = updateDetailStatement;
      pstmt = createPreparedStatement(getUpdateDetailJdQuery());
      // 2014/05/06 京东WBS对应 ob_卢 update end
      
      logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    }

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
  }

  /**
   * 商品明细表查询Query
   */
  private String getSelectDetailQuery() {
    String selectSql = "" + " SELECT COUNT(*) FROM " + DATAIL_TABLE_NAME + " WHERE SKU_CODE = ? ";
    return selectSql;
  }

  /**
   * 商品明细表更新Query
   */
  private String getUpdateDetailQuery() {
    String updateSql = "" + " UPDATE " + DATAIL_TABLE_NAME + " SET {0} UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE SKU_CODE = ? ";

    StringBuilder setPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    for (int i = 0; i < detailColumns.size(); i++) {
      CsvColumn column = detailColumns.get(i);
      columnList.add(column.getPhysicalName());
    }

    for (String column : columnList) {
      setPart.append(column + " = ?, ");
    }

    return MessageFormat.format(updateSql, setPart.toString());

  }

  // 20120207 os013 add start
  
  // 2014/05/06 京东WBS对应 ob_卢 add start
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
   * 在库表更新
   */
  private int executeStockUpdate(boolean exists) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
 // 2014/06/11 库存更新对应 ob_yuan add start
    CsvColumn stockThresholdColumn = null;
 // 2014/06/11 库存更新对应 ob_yuan add end
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
      params.add(stock.getSkuCode());
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
        params.add(detail.getCommodityCode());
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
    String sql = "DELETE FROM STOCK_RATIO WHERE SHOP_CODE = ? AND COMMODITY_CODE = ? ";
    return sql;
  }
  //2014/06/11 库存更新对应 ob_yuan add end
  /**
   * 商品基本表更新
   */
  private int executeUpdateHeader() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;
//    if (isRepresentSku) {
//      params.add(detail.getUnitPrice());
//    }
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());

    params.add(detail.getCommodityCode());

    if (isRepresentSku) {
      pstmt = updateHeaderStatementOne;
    }else{
      pstmt = updateHeaderStatementOne;
    }

    logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();

  }

  /**
   * 基本表更新Query
   */
  private String getUpdateHeaderQueryOne() {
    String updateSql = " "+ " UPDATE " + HEADER_TABLE_NAME
      + " SET SYNC_FLAG_EC = 1, SYNC_FLAG_TMALL = 1, EXPORT_FLAG_ERP = 1,EXPORT_FLAG_WMS = 1, "
      + " UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ? ";
   
    return updateSql;
  }
  private String getUpdateHeaderQueryTwo() {
    String updateSql = " "+ " UPDATE " + HEADER_TABLE_NAME
      + " SET SYNC_FLAG_EC = 1, SYNC_FLAG_TMALL = 1, EXPORT_FLAG_ERP = 1,EXPORT_FLAG_WMS = 1,REPRESENT_SKU_UNIT_PRICE = ?, "
      + " UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ? ";
   
    return updateSql;
  }

  /**
   * 在库表更新Query
   */
  private String getUpdateStockQuery() {
    String updateSql = "" + " UPDATE " + STOCK_TABLE_NAME + " SET {0} UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE SKU_CODE = ? ";
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
     // 2014/06/11 库存更新对应 ob_yuan update start
        //columnList.add(column.getPhysicalName());
        if (!column.getPhysicalName().equals("stock_ratio")) {
          columnList.add(column.getPhysicalName());
        }
     // 2014/06/11 库存更新对应 ob_yuan update end
      }
    }

    for (String column : columnList) {
      setPart.append(column + " = ?, ");
    }

    return MessageFormat.format(updateSql, setPart.toString());
  }

  /**
   * 在库表查询Query
   */
  private String getSelectStockQuery() {
    String selectSql = "" + " SELECT COUNT(*) FROM " + STOCK_TABLE_NAME + " WHERE  SKU_CODE = ? ";
    return selectSql;
  }

  // 20120207 os013 add end

  // 10.1.1 10019 追加 ここから
  private void checkMinusNumber(ValidationSummary summary) {
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
          new ValidationResult(Messages.getString("service.data.csv.CommodityDetailImportDataSource.29"), null, Message.get(
              CsvMessage.OUT_OF_RANGE, "0", "100")));
    }
  }

  /**
   * 判断必须项是否为空
   */
  private void checkExist(List<String> errorMessageList) {
    // 判断CSV文件中SKU名称是否存在
    if (existsSkuName) {
      // 存在必须输入
      if (StringUtil.isNullOrEmpty(detail.getSkuName())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages
            .getString("service.data.csv.CCommodityDetailCsvSchema.1")));
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
    // 判断CSV文件中商品コード是否存在
    if (existsCommodityCode) {
      // 存在必须输入
      if (StringUtil.isNullOrEmpty(detail.getCommodityCode())) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages
            .getString("service.data.csv.CCommodityDetailCsvSchema.2")));
      }
    }
    // 判断CSV文件中定価フラグ是否存在
    if (existsFixedPriceFlag) {
      // 存在必须输入
      if (detail.getFixedPriceFlag() == null) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages
            .getString("service.data.csv.CCommodityDetailCsvSchema.3")));
        // 定価フラグ必须为0或1
      } else if (detail.getFixedPriceFlag() != 0 && detail.getFixedPriceFlag() != 1) {
        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages
            .getString("service.data.csv.CCommodityDetailCsvSchema.3"), "0", "1"));
      }
    }
    // 判断CSV文件中取扱いフラグ(EC)是否存在
    if (existsUseFlg) {
      // 存在必须输入
      if (detail.getUseFlg() == null) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages
            .getString("service.data.csv.CommodityDetailImportDataSource.30")));
      } else if (detail.getUseFlg() != 0 && detail.getUseFlg() != 1) {
        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages
            .getString("service.data.csv.CommodityDetailImportDataSource.30"), "0", "1"));
      }
    }
    // 判断CSV文件中取扱いフラグ(TMALL)是否存在
    if (existsTmallUseFlg) {
      // 存在必须输入
      if (detail.getTmallUseFlg() == null) {
        errorMessageList.add(Message.get(CsvMessage.REQUEST_ITEM, Messages
            .getString("service.data.csv.CommodityDetailImportDataSource.31")));
      } else if (detail.getTmallUseFlg() != 0 && detail.getTmallUseFlg() != 1) {
        errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages
            .getString("service.data.csv.CommodityDetailImportDataSource.31"), "0", "1"));
      }
    }
    // 2014/04/30 京东WBS对应 ob_卢 add start
 // 判断CSV文件中取扱いフラグ(JD)是否存在
    if (existsJdUseFlg) {
      if (detail.getJdUseFlg() != null) {
        if (!JdUseFlg.DISABLED.longValue().equals(detail.getJdUseFlg()) 
            && !JdUseFlg.ENABLED.longValue().equals(detail.getJdUseFlg())) {
          errorMessageList.add(Message.get(CsvMessage.OUT_OF_RANGE, Messages
              .getString("service.data.csv.CommodityDetailImportDataSource.51"), "0", "1"));
        }
      }
    }
    // 2014/04/30 京东WBS对应 ob_卢 add end
    // 判断税率区分存在,等于false时不存在，报错
    if (existsTaxClass) {
      existsTaxClass = false;
      List<NameValue> taxClassList = DIContainer.getTaxClassValue().getTaxClass();
      for (int i = 0; i < taxClassList.size(); i++) {
        if (taxClassList.get(i).getValue().equals(detail.getTaxClass())) {
          existsTaxClass = true;
        }
      }
      if (!existsTaxClass) {
        errorMessageList.add(Message.get(CsvMessage.NOT_EXIST, Messages
            .getString("service.data.csv.CommodityDetailImportDataSource.32"), detail.getTaxClass()));
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
    params.add(detail.getCommodityCode());

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
    params.add(detail.getCommodityCode());

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
    if (cCommodityDetailSearchResult.getRows().get(0).getUnitPrice() != null) {
        ecPrice = cCommodityDetailSearchResult.getRows().get(0).getUnitPrice().toString();
    }
    if (cCommodityDetailSearchResult.getRows().get(0).getDiscountPrice() != null) {
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
    // 如果csv导入文件中含有“平均计算成本”，那么优先取csv导入文件。
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
