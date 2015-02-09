package jp.co.sint.webshop.service.data.csv;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.domain.BigGoodsFlag;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.ReturnFlg;
import jp.co.sint.webshop.data.domain.SaleFlg;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.domain.ShelfLifeFlag;
import jp.co.sint.webshop.data.domain.UsingFlg;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityExt;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.JdStock;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.data.dto.StockRatio;
import jp.co.sint.webshop.data.dto.TmallStock;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

public class CommodityGiftImportDataSource extends SqlImportDataSource<CommodityGiftCsvSchema, CommodityGiftImportCondition> {

  // 声明CCommodityHeader、CCommodityDetail、Stock表
  private static final String HEADER_TABLE_NAME = DatabaseUtil.getTableName(CCommodityHeader.class);

  private static final String DETAIL_TABLE_NAME = DatabaseUtil.getTableName(CCommodityDetail.class);

  private static final String STOCK_RATIO_TABLE_NAME = DatabaseUtil.getTableName(StockRatio.class);
  
  private static final String STOCK_TABLE_NAME = DatabaseUtil.getTableName(Stock.class);

  private static final String JD_STOCK_TABLE_NAME = DatabaseUtil.getTableName(JdStock.class);
  
  private static final String TMALL_STOCK_TABLE_NAME = DatabaseUtil.getTableName(TmallStock.class);
  
  private static final String EXT_TABLE_NAME = DatabaseUtil.getTableName(CCommodityExt.class);

  private PreparedStatement insertHeaderStatement = null;

  private PreparedStatement insertDetailStatement = null;

  private PreparedStatement insertStockRatioStatement = null;
  
  private PreparedStatement insertStockStatement = null;

  private PreparedStatement insertJdStockStatement = null;
 
  private PreparedStatement insertTmallStockStatement = null;
  
  private PreparedStatement insertExtStatement = null;

  private Long ecStockRatio = 0L;
  
  /** 商品ヘッダ */
  private CCommodityHeader cheader = null;

  /** 代表SKU */
  private CCommodityDetail csku = null;

  /** 在庫 */
  private Stock stock = null;
  
  private JdStock jdStock = null;
  
  private TmallStock tmallStock = null;
  
  private StockRatio stockRatio = null;

  private WebshopConfig config = null;

  @Override
  protected void initializeResources() {
    config = DIContainer.getWebshopConfig();

    Logger logger = Logger.getLogger(this.getClass());

    logger.debug("INSERT statement: " + getInsertHeaderQuery());
    logger.debug("INSERT statement: " + getInsertDetailQuery());
    logger.debug("INSERT statement: " + getInsertStockRatioQuery());
    logger.debug("INSERT statement: " + getInsertStockQuery());
    logger.debug("INSERT statement: " + getInsertJdStockQuery());
    logger.debug("INSERT statement: " + getInsertTmallStockQuery());
    logger.debug("INSERT statement: " + getInsertCCommodityExtQuery());

    try {
      insertHeaderStatement = createPreparedStatement(getInsertHeaderQuery());
      insertDetailStatement = createPreparedStatement(getInsertDetailQuery());
      insertStockRatioStatement = createPreparedStatement(getInsertStockRatioQuery());
      insertStockStatement = createPreparedStatement(getInsertStockQuery());
      insertJdStockStatement = createPreparedStatement(getInsertJdStockQuery());
      insertTmallStockStatement = createPreparedStatement(getInsertTmallStockQuery());

      insertExtStatement = createPreparedStatement(getInsertCCommodityExtQuery());
      
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  @Override
  public ValidationSummary validate(List<String> csvLine) {

    ValidationSummary summary = new ValidationSummary();

    try {
      if (!StringUtil.isNullOrEmpty(config.getStockRatio())) {
        String[] stockRatioArray = config.getStockRatio().split(":");
        //EC库存比例
        ecStockRatio = NumUtil.toLong(stockRatioArray[0]);
      }
      
      cheader = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CCommodityHeader.class);
      csku = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CCommodityDetail.class);
      stock = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), Stock.class);
      jdStock = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), JdStock.class);
      tmallStock = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), TmallStock.class);
      stockRatio = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), StockRatio.class);
      
      
      // 商品主表必须字段赋值
      cheader.setRepresentSkuUnitPrice(new BigDecimal(0));
      cheader.setSaleStartDatetime(DateUtil.getMin());
      cheader.setSaleEndDatetime(DateUtil.getMax());
      cheader.setSaleFlgEc(SaleFlg.FOR_SALE.longValue());
      cheader.setReturnFlg(ReturnFlg.CANNOT.longValue());
      // cheader.setShelfLifeFlag(ShelfLifeFlag.NOT.longValue());
      // cheader.setShelfLifeDays(0L);
      cheader.setBigFlag(BigGoodsFlag.Big.longValue());
      cheader.setSyncFlagEc(1L);
      // 20130703 shen update start
      // cheader.setSyncFlagTmall(2L);
      cheader.setSyncFlagTmall(0L);
      // 20130703 shen update end
      cheader.setExportFlagErp(1L);
      cheader.setExportFlagWms(1L);
      // 20140228 txw update start
      // cheader.setCommodityType(CommodityType.GIFT.longValue());
      cheader.setSetCommodityFlg(SetCommodityFlg.OBJECTOUT.longValue());
      // 20140228 txw update start
      
      // 商品明细表必须字段赋值
      csku.setFixedPriceFlag(0L);
      csku.setPurchasePrice(new BigDecimal(0));
      csku.setUnitPrice(new BigDecimal(0));
      csku.setUseFlg(UsingFlg.VISIBLE.longValue());
      csku.setTmallUseFlg(UsingFlg.HIDDEN.longValue());
      csku.setTaxClass("0");

      // 库存表必须字段赋值
      stock.setStockQuantity(0L);
      stock.setAllocatedQuantity(0L);
      stock.setReservedQuantity(0L);
      stock.setStockThreshold(0L);
      stock.setStockTmall(0L);
      stock.setStockTotal(0L);
      stock.setAllocatedTmall(0L);
      stock.setShareRecalcFlag(1L);
      stock.setShareRatio(ecStockRatio);

      // JD库存表必须字段赋值
      jdStock.setStockQuantity(0L);
      jdStock.setAllocatedQuantity(0L);
      
      // TMALL库存表必须字段赋值
      tmallStock.setStockQuantity(0L);
      tmallStock.setAllocatedQuantity(0L);
      
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }

    // 店铺code和商品code和skucode验证赋值
    if (!StringUtil.isNullOrEmpty(cheader.getCommodityCode())) {
      if (cheader.getCommodityCode().length() > 16) {
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.23")));
        return summary;
      }
      cheader.setShopCode(config.getSiteShopCode());
      csku.setShopCode(config.getSiteShopCode());
      stock.setShopCode(config.getSiteShopCode());
      jdStock.setShopCode(config.getSiteShopCode());
      tmallStock.setShopCode(config.getSiteShopCode());
      stockRatio.setShopCode(config.getSiteShopCode());
      cheader.setRepresentSkuCode(cheader.getCommodityCode());
      csku.setCommodityCode(cheader.getCommodityCode());
      csku.setSkuCode(cheader.getCommodityCode());
      stock.setCommodityCode(cheader.getCommodityCode());
      stock.setSkuCode(cheader.getCommodityCode());
      jdStock.setCommodityCode(cheader.getCommodityCode());
      jdStock.setSkuCode(cheader.getCommodityCode());
      tmallStock.setCommodityCode(cheader.getCommodityCode());
      tmallStock.setSkuCode(cheader.getCommodityCode());
      stockRatio.setCommodityCode(cheader.getCommodityCode());
    } else {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
              .getString("service.data.csv.CommodityGiftImportDataSource.0"))));
      return summary;
    }

    // 商品中文名验证赋值
    if (!StringUtil.isNullOrEmpty(cheader.getCommodityName())) {
      if (cheader.getCommodityName().length() > 50) {
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.24")));
        return summary;
      }
      csku.setSkuName(cheader.getCommodityName());
    } else {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
              .getString("service.data.csv.CommodityGiftImportDataSource.1"))));
      return summary;
    }

    // 商品英文名验证
    if (StringUtil.isNullOrEmpty(cheader.getCommodityNameEn())) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
              .getString("service.data.csv.CommodityGiftImportDataSource.2"))));
      return summary;
    }
    if (cheader.getCommodityNameEn().length() > 200) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.25")));
      return summary;
    }

    // 商品日文名验证
    if (StringUtil.isNullOrEmpty(cheader.getCommodityNameJp())) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
              .getString("service.data.csv.CommodityGiftImportDataSource.3"))));
      return summary;
    }
    if (cheader.getCommodityNameJp().length() > 200) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.26")));
      return summary;
    }

    // 供应商编号验证
    if (StringUtil.isNullOrEmpty(cheader.getSupplierCode())) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
              .getString("service.data.csv.CommodityGiftImportDataSource.4"))));
      return summary;
    }
    if (cheader.getSupplierCode().length() > 8) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.27")));
      return summary;
    }

    // 采购员编号验证
    if (StringUtil.isNullOrEmpty(cheader.getBuyerCode())) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
              .getString("service.data.csv.CommodityGiftImportDataSource.5"))));
      return summary;
    }
    if (cheader.getBuyerCode().length() > 8) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.28")));
      return summary;
    }

    // 入库效期长度验证
    if (cheader.getInBoundLifeDays() != null && cheader.getInBoundLifeDays() > 9999L) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.6")));
      return summary;
    }

    // 出库效期长度验证
    if (cheader.getOutBoundLifeDays() != null && cheader.getOutBoundLifeDays() > 9999L) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.7")));
      return summary;
    }

    // 失效预警长度验证
    if (cheader.getShelfLifeAlertDays() != null && cheader.getShelfLifeAlertDays() > 9999L) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.8")));
      return summary;
    }

    // 商品重量长度验证
    if (csku.getWeight() != null && csku.getWeight().floatValue() > 99999999f) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.9")));
      return summary;
    }

    // 库存警告数非空和长度验证
    if (csku.getStockWarning() == null) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.10")));
      return summary;
    } else {
      if (csku.getStockWarning() > 99999999L) {
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.11")));
        return summary;
      }
    }

    // 采购提前期非空和长度验证
    if (cheader.getLeadTime() == null) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.12")));
      return summary;
    } else {
      if (cheader.getLeadTime() > 99L) {
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.13")));
        return summary;
      }
    }

    // 商品期限管理フラグ 0管理しない/1賞味期限日/2製造日＋保管日数
    if (NumUtil.isNull(cheader.getShelfLifeFlag())) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.29")));
      return summary;
    } else {
      if (cheader.getShelfLifeFlag() != 0L && cheader.getShelfLifeFlag() != 1L && cheader.getShelfLifeFlag() != 2L) {
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.30")));
        return summary;
      }
    }
    // 保管日数
    if (NumUtil.isNull(cheader.getShelfLifeDays())) {
      cheader.setShelfLifeDays(0L);
    } else {
      if (cheader.getShelfLifeDays() > 999999L) {
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.31")));
        return summary;
      }
    }

    // 最小采购数非空和长度验证
    if (csku.getMinimumOrder() == null) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.14")));
      return summary;
    } else {
      if (csku.getMinimumOrder() > 99999999L) {
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.15")));
        return summary;
      }
    }

    // 最大采购数非空和长度验证
    if (csku.getMaximumOrder() == null) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.16")));
      return summary;
    } else {
      if (csku.getMaximumOrder() > 99999999L) {
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.17")));
        return summary;
      }
    }

    // 最小采购数单位非空和长度验证
    if (csku.getOrderMultiple() == null) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.18")));
      return summary;
    } else {
      if (csku.getOrderMultiple() > 99999999L) {
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.19")));
        return summary;
      }
    }
    
    // 20140228 txw add start
    // 商品类型验证
    if (cheader.getCommodityType() == null) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.32")));
      return summary;
    } else {
      if (!(cheader.getCommodityType().equals(CommodityType.GIFT.longValue()) || cheader.getCommodityType().equals(CommodityType.PROPAGANDA.longValue()))) {
        summary.getErrors().add(
            new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.33")));
        return summary;
      }
    }
    // 20140228 txw add end

    // 判断商品在商品主表是否已存在
    SimpleQuery cHeaderCountQuery = new SimpleQuery("SELECT COUNT(*) FROM C_COMMODITY_HEADER WHERE COMMODITY_CODE = ? ");
    cHeaderCountQuery.setParameters(cheader.getCommodityCode());
    Long cHeaderCount = Long.valueOf(executeScalar(cHeaderCountQuery).toString());
    if (cHeaderCount > 0L) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.20")));
      return summary;
    }

    // 判断商品在商品明细表是否已存在
    SimpleQuery cDetailCountQuery = new SimpleQuery("SELECT COUNT(*) FROM C_COMMODITY_DETAIL WHERE COMMODITY_CODE = ? ");
    cDetailCountQuery.setParameters(cheader.getCommodityCode());
    Long cDetailCount = Long.valueOf(executeScalar(cDetailCountQuery).toString());
    if (cDetailCount > 0L) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.21")));
      return summary;
    }

    // 判断商品在库存是否已存在
    SimpleQuery stockCountQuery = new SimpleQuery("SELECT COUNT(*) FROM STOCK WHERE COMMODITY_CODE = ? ");
    stockCountQuery.setParameters(cheader.getCommodityCode());
    Long stockCount = Long.valueOf(executeScalar(stockCountQuery).toString());
    if (stockCount > 0L) {
      summary.getErrors().add(
          new ValidationResult(null, null, Messages.getString("service.data.csv.CommodityGiftImportDataSource.22")));
      return summary;
    }

    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    try {
      int updHeaderCount = executeUpdateHeader();
      if (updHeaderCount != 1) {
        throw new CsvImportException();
      }

      int updDetailCount = executeUpdateDetail();
      if (updDetailCount != 1) {
        throw new CsvImportException();
      }
      
      int updStockCount = executeStockDetail();
      if (updStockCount != 1) {
        throw new CsvImportException();
      }

      int updJdStockCount = executeJdStockDetail();
      if (updJdStockCount != 1) {
        throw new CsvImportException();
      }
      
      int updTmallStockCount = executeTmallStockDetail();
      if (updTmallStockCount != 1) {
        throw new CsvImportException();
      }
      
      int updExtCount = executeExtUpdate();
      if (updExtCount != 1) {
        throw new CsvImportException();
      }
      
      // 插入3条数据到stock_ratio表，分别为EC，Tmall，Jd的库存比例
      int ecStockRatio = 0, tmallStockRatio = 0, jdStockRatio = 0;
      if (!StringUtil.isNullOrEmpty(config.getStockRatio())) {
        String[] stockRatioArray = config.getStockRatio().split(":");
        //EC库存比例, TMALL库存比例, JD库存比例
        ecStockRatio = Integer.parseInt(stockRatioArray[0]);
        tmallStockRatio = Integer.parseInt(stockRatioArray[1]);
        jdStockRatio = Integer.parseInt(stockRatioArray[2]);
      }
      
      int updStockRatioCount = executeStockRatio(0, ecStockRatio);
      if (updStockRatioCount != 1) {
        throw new CsvImportException();
      }
      
      int updStockRatioCount2 = executeStockRatio(1, tmallStockRatio);
      if (updStockRatioCount2 != 1) {
        throw new CsvImportException();
      }
      
      int updStockRatioCount3 = executeStockRatio(2, jdStockRatio);
      if (updStockRatioCount3 != 1) {
        throw new CsvImportException();
      }

    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw new CsvImportException(e);
    } catch (CsvImportException e) {
      logger.error(e.getMessage());
      throw e;
    } catch (RuntimeException e) {
      logger.error(e.getMessage());
      throw new CsvImportException(e);
    }
  }

  // 插入商品主表的参数赋值
  private int executeUpdateHeader() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;

    params.add(cheader.getShopCode());
    params.add(cheader.getCommodityCode());
    params.add(cheader.getCommodityName());
    params.add(cheader.getCommodityNameEn());
    params.add(cheader.getCommodityNameJp());
    params.add(cheader.getRepresentSkuCode());
    params.add(cheader.getRepresentSkuUnitPrice());
    params.add(cheader.getSaleStartDatetime());
    params.add(cheader.getSaleEndDatetime());
    params.add(cheader.getSaleFlgEc());
    params.add(cheader.getReturnFlg());
    params.add(cheader.getLeadTime());
    params.add(cheader.getSupplierCode());
    params.add(cheader.getBuyerCode());
    params.add(cheader.getShelfLifeFlag());
    params.add(cheader.getShelfLifeDays());
    params.add(cheader.getBigFlag());
    params.add(cheader.getInBoundLifeDays());
    params.add(cheader.getOutBoundLifeDays());
    params.add(cheader.getShelfLifeAlertDays());
    params.add(cheader.getSyncFlagEc());
    params.add(cheader.getSyncFlagTmall());
    params.add(cheader.getExportFlagErp());
    params.add(cheader.getExportFlagWms());
    params.add(cheader.getCommodityType());
    // 20140228 txw add start
    params.add(cheader.getSetCommodityFlg());
    // 20140228 txw add end
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());

    pstmt = insertHeaderStatement;
    logger.debug("Table:C_COMMODITY_HEADER INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }

  // 插入商品主表的SQL
  private String getInsertHeaderQuery() {
    String insertSql = "" + " INSERT INTO " + HEADER_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'C_COMMODITY_HEADER_SEQ'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("SHOP_CODE");
    columnList.add("COMMODITY_CODE");
    columnList.add("COMMODITY_NAME");
    columnList.add("COMMODITY_NAME_EN");
    columnList.add("COMMODITY_NAME_JP");
    columnList.add("REPRESENT_SKU_CODE");
    columnList.add("REPRESENT_SKU_UNIT_PRICE");
    columnList.add("SALE_START_DATETIME");
    columnList.add("SALE_END_DATETIME");
    columnList.add("SALE_FLG_EC");
    columnList.add("RETURN_FLG");
    columnList.add("LEAD_TIME");
    columnList.add("SUPPLIER_CODE");
    columnList.add("BUYER_CODE");
    columnList.add("SHELF_LIFE_FLAG");
    columnList.add("SHELF_LIFE_DAYS");
    columnList.add("BIG_FLAG");
    columnList.add("IN_BOUND_LIFE_DAYS");
    columnList.add("OUT_BOUND_LIFE_DAYS");
    columnList.add("SHELF_LIFE_ALERT_DAYS");
    columnList.add("SYNC_FLAG_EC");
    columnList.add("SYNC_FLAG_TMALL");
    columnList.add("EXPORT_FLAG_ERP");
    columnList.add("EXPORT_FLAG_WMS");
    columnList.add("COMMODITY_TYPE");
    // 20140228 txw add start
    columnList.add("SET_COMMODITY_FLG");
    // 20140228 txw add end

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  // 插入商品明细表的参数赋值
  private int executeUpdateDetail() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;

    params.add(csku.getShopCode());
    params.add(csku.getSkuCode());
    params.add(csku.getSkuName());
    params.add(csku.getCommodityCode());
    params.add(csku.getFixedPriceFlag());
    params.add(csku.getPurchasePrice());
    params.add(csku.getUnitPrice());
    params.add(csku.getWeight());
    params.add(csku.getUseFlg());
    params.add(csku.getTmallUseFlg());
    params.add(csku.getMinimumOrder());
    params.add(csku.getMaximumOrder());
    params.add(csku.getOrderMultiple());
    params.add(csku.getStockWarning());
    params.add(csku.getTaxClass());
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());

    pstmt = insertDetailStatement;
    logger.debug("Table:C_COMMODITY_DETAIL INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }

  // 插入商品明细表的SQL
  private String getInsertDetailQuery() {
    String insertSql = "" + " INSERT INTO " + DETAIL_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'C_COMMODITY_DETAIL_SEQ'") + ", ?, ?, ?, ?) ";
    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("SHOP_CODE");
    columnList.add("SKU_CODE");
    columnList.add("SKU_NAME");
    columnList.add("COMMODITY_CODE");
    columnList.add("FIXED_PRICE_FLAG");
    columnList.add("PURCHASE_PRICE");
    columnList.add("UNIT_PRICE");
    columnList.add("WEIGHT");
    columnList.add("USE_FLG");
    columnList.add("TMALL_USE_FLG");
    columnList.add("MINIMUM_ORDER");
    columnList.add("MAXIMUM_ORDER");
    columnList.add("ORDER_MULTIPLE");
    columnList.add("STOCK_WARNING");
    columnList.add("TAX_CLASS");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  // 插入库存比例表的参数赋值
  private int executeStockRatio(int ratioType, int stockRatioFromConfig) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;

    params.add(stockRatio.getShopCode());
    params.add(stockRatio.getCommodityCode());
    params.add(ratioType);
    params.add(stockRatioFromConfig);
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());

    pstmt = insertStockRatioStatement;
    logger.debug("Table:STOCK_RATIO INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }
  
  // 插入商品明细表的参数赋值
  private int executeStockDetail() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;

    params.add(stock.getShopCode());
    params.add(stock.getSkuCode());
    params.add(stock.getCommodityCode());
    params.add(stock.getStockQuantity());
    params.add(stock.getAllocatedQuantity());
    params.add(stock.getReservedQuantity());
    params.add(stock.getStockThreshold());
    params.add(stock.getAllocatedTmall());
    params.add(config.getShareRatioRate());
    params.add(stock.getStockTotal());
    params.add(stock.getStockTmall());
    params.add(stock.getShareRecalcFlag());
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());

    pstmt = insertStockStatement;
    logger.debug("Table:STOCK INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }

  // 插入商品明细表的参数赋值
  private int executeJdStockDetail() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;

    params.add(jdStock.getShopCode());
    params.add(jdStock.getSkuCode());
    params.add(jdStock.getCommodityCode());
    params.add(jdStock.getStockQuantity());
    params.add(jdStock.getAllocatedQuantity());
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());

    pstmt = insertJdStockStatement;
    logger.debug("Table:JD_STOCK INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }
  
  // 插入商品明细表的参数赋值
  private int executeTmallStockDetail() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    List<Object> params = new ArrayList<Object>();

    PreparedStatement pstmt = null;

    params.add(tmallStock.getShopCode());
    params.add(tmallStock.getSkuCode());
    params.add(tmallStock.getCommodityCode());
    params.add(tmallStock.getStockQuantity());
    params.add(tmallStock.getAllocatedQuantity());
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());

    pstmt = insertTmallStockStatement;
    logger.debug("Table:TMALL_STOCK INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));

    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));
    return pstmt.executeUpdate();
  }
  
  // 插入库存比例表SQL
  private String getInsertStockRatioQuery() {
    String insertSql = "" + " INSERT INTO " + STOCK_RATIO_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'STOCK_RATIO_SEQ'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("SHOP_CODE");
    columnList.add("COMMODITY_CODE");
    columnList.add("RATIO_TYPE");
    columnList.add("STOCK_RATIO");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }
  
  // 插入库存表SQL
  private String getInsertStockQuery() {
    String insertSql = "" + " INSERT INTO " + STOCK_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'STOCK_SEQ'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("SHOP_CODE");
    columnList.add("SKU_CODE");
    columnList.add("COMMODITY_CODE");
    columnList.add("STOCK_QUANTITY");
    columnList.add("ALLOCATED_QUANTITY");
    columnList.add("RESERVED_QUANTITY");
    columnList.add("STOCK_THRESHOLD");
    columnList.add("ALLOCATED_TMALL");
    columnList.add("SHARE_RATIO");
    columnList.add("STOCK_TOTAL");
    columnList.add("STOCK_TMALL");
    columnList.add("SHARE_RECALC_FLAG");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }
  
  // 插入JD库存表SQL
  private String getInsertJdStockQuery() {
    String insertSql = "" + " INSERT INTO " + JD_STOCK_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'JD_STOCK_SEQ'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("SHOP_CODE");
    columnList.add("SKU_CODE");
    columnList.add("COMMODITY_CODE");
    columnList.add("STOCK_QUANTITY");
    columnList.add("ALLOCATED_QUANTITY");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }

  // 插入TMALL库存表SQL
  private String getInsertTmallStockQuery() {
    String insertSql = "" + " INSERT INTO " + TMALL_STOCK_TABLE_NAME
        + " ({0} ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME ) " + " VALUES " + " ({1} "
        + SqlDialect.getDefault().getNextvalNOprm("'TMALL_STOCK_SEQ'") + ", ?, ?, ?, ?) ";

    StringBuilder columnNamePart = new StringBuilder();
    StringBuilder valuesPart = new StringBuilder();

    List<String> columnList = new ArrayList<String>();
    columnList.add("SHOP_CODE");
    columnList.add("SKU_CODE");
    columnList.add("COMMODITY_CODE");
    columnList.add("STOCK_QUANTITY");
    columnList.add("ALLOCATED_QUANTITY");

    for (String column : columnList) {
      columnNamePart.append(column + ", ");
      valuesPart.append("?, ");
    }
    return MessageFormat.format(insertSql, columnNamePart.toString(), valuesPart.toString());
  }
  
  
  private int executeExtUpdate() throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement pstmt = null;

    List<Object> params = new ArrayList<Object>();
    // ショップコード
    params.add(cheader.getShopCode());
    // 商品コード
    params.add(cheader.getCommodityCode());
    // 在庫品区分
    params.add(1L);

    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());
    params.add(getCondition().getLoginInfo().getRecordingFormat());
    params.add(DateUtil.getSysdate());

    pstmt = insertExtStatement;
    logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
    DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

    return pstmt.executeUpdate();
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

}
