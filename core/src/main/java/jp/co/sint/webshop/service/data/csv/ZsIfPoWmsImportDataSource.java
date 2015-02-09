package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.dao.CCommodityHeaderDao;
import jp.co.sint.webshop.data.domain.StockIOType;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.data.CsvSchemaType;
import jp.co.sint.webshop.service.order.ZsIfInvImportReport;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

/**
 * 入荷完了导入
 * 
 * @author OS011
 */
public class ZsIfPoWmsImportDataSource extends SqlImportDataSource<ZsIfPoWmsImportCsvSchema, ZsIfPoWmsImportCondition> {

  private Logger logger = Logger.getLogger(this.getClass());

  private ZsIfInvImportReport zsIfInvImportReport = null;

  private PreparedStatement stockTempStatement = null;

  private PreparedStatement stockIoDeatilStatement = null;

  // 2014/06/10 库存更新对应 ob_张震 delete start
  // private PreparedStatement stockTempDelStatement = null;
  // 2014/06/10 库存更新对应 ob_张震 delete end

  private List<ZsIfInvImportReport> importList = new ArrayList<ZsIfInvImportReport>();

  public List<ZsIfInvImportReport> getImportList() {
    return importList;
  }

  public void setImportList(List<ZsIfInvImportReport> importList) {
    this.importList = importList;
  }

  String IO_DETAIL_TABLE_NAME = "STOCK_IO_DETAIL";

  WebshopConfig config = DIContainer.getWebshopConfig();

  String DETAIL_TABLE_NAME = "STOCK_TEMP";

  // 2014/06/10 库存更新对应 ob_张震 update start
  // 添加库存临时表
  // String insertStockTempSql =
  // "INSERT  INTO STOCK_TEMP (SHOP_CODE,SKU_CODE,ADD_STOCK_TOTAL,ORM_ROWID,CREATED_USER,CREATED_DATETIME,UPDATED_USER,UPDATED_DATETIME) VALUES (?,?,?,"
  // + SqlDialect.getDefault().getNextvalNOprm(DETAIL_TABLE_NAME + "_SEQ") +
  // ",?,?,?,?)";
  String insertStockTempSql = ""
      + " INSERT INTO STOCK_TEMP ( SHOP_CODE, "
      + "                          SKU_CODE, "
      + "                          STOCK_CHANGE_TYPE, "
      + "                          STOCK_CHANGE_QUANTITY, "
      + "                          ORM_ROWID, "
      + "                          CREATED_USER, "
      + "                          CREATED_DATETIME, "
      + "                          UPDATED_USER, "
      + "                          UPDATED_DATETIME ) "
      + "                 VALUES ( ?, ?, 0, ?, "
      + SqlDialect.getDefault().getNextvalNOprm(DETAIL_TABLE_NAME + "_SEQ") + ", " + "  ?, ?, ?, ? ) ";

  // 2014/06/10 库存更新对应 ob_张震 update end

  // 2014/06/10 库存更新对应 ob_张震 add start
  // 检验库存临时表
  String hasDataStockTempSql = " SELECT COUNT(*) FROM  STOCK_TEMP WHERE SKU_CODE = ? AND STOCK_CHANGE_TYPE = 0 ";

  // 检验库存临时表
  String updateStockTempSql = " UPDATE STOCK_TEMP SET STOCK_CHANGE_QUANTITY = STOCK_CHANGE_QUANTITY + ? WHERE SKU_CODE = ? AND STOCK_CHANGE_TYPE = 0 ";

  // 2014/06/10 库存更新对应 ob_张震 add end

  // 2014/06/10 库存更新对应 ob_张震 delete start
  // 删除库存临时表
  // String deleteStockTempSql = " DELETE FROM  STOCK_TEMP WHERE SKU_CODE=?";
  // 2014/06/10 库存更新对应 ob_张震 delete end

  // 2014/06/10 库存更新对应 ob_张震 update start
  // 查询在库信息
  // String selectStockSql =
  // "SELECT STOCK_TOTAL FROM STOCK WHERE SHOP_CODE = ? AND SKU_CODE=?";
  String selectStockSql = ""
      + " SELECT A.STOCK_QUANTITY + A.STOCK_THRESHOLD + COALESCE(GET_TMALL_STOCK(A.SKU_CODE), 0) + COALESCE(GET_JD_STOCK(A.SKU_CODE), 0) + "
      + "          ( CASE WHEN B.STOCK_CHANGE_QUANTITY IS NULL "
      + "               THEN 0 "
      + "               ELSE B.STOCK_CHANGE_QUANTITY "
      + "           END ) AS STOCK_TOTAL "
      + "   FROM STOCK A "
      + "   LEFT JOIN STOCK_TEMP B "
      + "     ON A.SKU_CODE = B.SKU_CODE "
      + "    AND B.STOCK_CHANGE_TYPE = 0 "
      + "  WHERE A.SHOP_CODE = ? "
      + "    AND A.SKU_CODE = ? ";

  // 2014/06/10 库存更新对应 ob_张震 update end

  // 查询在库信息
  String selectStockTempSql = "SELECT SKU_CODE FROM STOCK_TEMP WHERE SHOP_CODE = ? AND SKU_CODE=?";

  // 添加库存履历表
  String insertStockIoDeatilSql = "INSERT  INTO STOCK_IO_DETAIL (stock_io_id,shop_code,stock_io_date,sku_code,stock_io_quantity,stock_io_type,memo,ORM_ROWID,CREATED_USER,CREATED_DATETIME,UPDATED_USER,UPDATED_DATETIME)"
      + " VALUES (?,?,?,?,?,?,?,"
      + SqlDialect.getDefault().getNextvalNOprm(IO_DETAIL_TABLE_NAME + "_SEQ")
      + ",?,?,?,?)";

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    List<String> errorMessageList = new ArrayList<String>();

    try {
      // 导入文件数据转换成实体Bean对象
      zsIfInvImportReport = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), ZsIfInvImportReport.class);
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }
    if (zsIfInvImportReport == null) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE, Messages
              .getString("service.data.csv.ZsIfPoImportDataSource.5"))));
    }

    // 判断商品是否在库
    CsvSchema schema = DIContainer.getCsvSchema(CsvSchemaType.STOCK);
    Query stockCountQuery = new SimpleQuery(CsvUtil.buildCheckExistsQuery(schema), config.getSiteShopCode(),
        zsIfInvImportReport.getSkuCode());
    Long stockCount = Long.valueOf(executeScalar(stockCountQuery).toString());
    if (stockCount == 0) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
              .getString("service.data.csv.ZsIfPoImportDataSource.5")
              + zsIfInvImportReport.getSkuCode())));
    }

    for (String error : errorMessageList) {
      summary.getErrors().add(new ValidationResult(null, null, error));
    }

    if (summary.hasError()) {
      return summary;
    }
    // 判断入库数量的范围合理性
    boolean isValid = true;
    String errorMessage = Message.get(CsvMessage.STOCK_AMOUNT_OVERFLOW, "(99999999L, 0L)");
    isValid &= zsIfInvImportReport.getStockTotal() <= 99999999L;
    // 判断入库数量的范围合理性
    if (!isValid) {
      summary.getErrors().add(new ValidationResult(null, null, errorMessage));
    }

    List<ZsIfInvImportReport> zsIfInvImportReportList = new ArrayList<ZsIfInvImportReport>();

    int skuExist = 0;
    // 把读取的数据放到数组里
    for (ZsIfInvImportReport zsIfReportList : importList) {

      // sku相同的时候，导入数量追加
      if (zsIfInvImportReport.getSkuCode().equals(zsIfReportList.getSkuCode())) {

        // 数量追加
        zsIfReportList.setStockTotal(zsIfReportList.getStockTotal() + getStockSite(zsIfInvImportReport));
        zsIfInvImportReportList.add(zsIfReportList);
        skuExist++;
      } else {
        zsIfInvImportReportList.add(zsIfReportList);
      }
    }
    // 没有相同的SKU时，添加到数组
    if (skuExist == 0 && importList.size() > 0) {
      zsIfInvImportReport.setStockTotal(getStockSite(zsIfInvImportReport));
      zsIfInvImportReportList.add(zsIfInvImportReport);
    }

    // 初期数组没有数据时
    if (importList.size() == 0) {
      if (getStockSite(zsIfInvImportReport) != 0) {
        zsIfInvImportReport.setStockTotal(getStockSite(zsIfInvImportReport));
        zsIfInvImportReportList.add(zsIfInvImportReport);
        // 放入数组
        this.setImportList(zsIfInvImportReportList);
      }
    } else {
      // 放入数组
      this.setImportList(zsIfInvImportReportList);
    }

    return summary;
  }

  /**
   * 库存导入处理
   */
  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {

    for (ZsIfInvImportReport zsIfReportList : importList) {
      // // 是优惠SKU,运费SKU,积分SKU时返回
      // if
      // (zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(0))
      // ||
      // zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(1))
      // ||
      // zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(2))
      // ||
      // zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(3))
      // ||
      // zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(4))
      // ||
      // zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(5)))
      // {
      // return;
      // }
      // // 收货的库位判断,不是0000(良品)返回
      // if (!config.getIfLocationForEc().equals(zsIfReportList.getLocation()))
      // {
      // return;
      // }
      // SKU增量
      Long addStock = zsIfReportList.getStockTotal();
      // 增量没有变化时
      // if (addStock <= 0L) {
      // return;
      // }
      // 传递参数
      List<Object> stockTempParams = new ArrayList<Object>();
      // 2014/06/10 库存更新对应 ob_张震 delete start
      // 传递参数
      // List<Object> stockTempDelParams = new ArrayList<Object>();
      // 2014/06/10 库存更新对应 ob_张震 delete end

      try {
        // 2014/06/10 库存更新对应 ob_张震 delete start
        // logger.debug("DELETE  STOCK_TEMP START");
        // // 删掉临时表
        // stockTempDelStatement = createPreparedStatement(deleteStockTempSql);
        // // sku_code
        // stockTempDelParams.add(zsIfReportList.getSkuCode());
        // DatabaseUtil.bindParameters(stockTempDelStatement,
        // ArrayUtil.toArray(stockTempDelParams, Object.class));
        // stockTempDelStatement.executeUpdate();
        // logger.debug("DELETE  STOCK_TEMP END");
        // 2014/06/10 库存更新对应 ob_张震 delete end

        // 2014/06/10 库存更新对应 ob_张震 add start
        // 检验库存临时表
        Query hasTempDataQuery = new SimpleQuery(hasDataStockTempSql, zsIfReportList.getSkuCode());
        Long tempData = Long.valueOf(executeScalar(hasTempDataQuery).toString());
        if (tempData > 0L) {
          // 临时表有数据时，更新
          stockTempParams.add(addStock);
          stockTempParams.add(zsIfReportList.getSkuCode());
          logger.debug("Table:STOCK_TEMP UPDATE Parameters:"
              + Arrays.toString(ArrayUtil.toArray(stockTempParams, Object.class)));
          // 更新在库临时表
          stockTempStatement = createPreparedStatement(updateStockTempSql);

          // 执行更新
          DatabaseUtil.bindParameters(stockTempStatement, ArrayUtil.toArray(stockTempParams, Object.class));
          stockTempStatement.executeUpdate();
        } else {
          // 临时表没有数据时，新增
          // shop_code
          stockTempParams.add(config.getSiteShopCode());
          // sku_code
          stockTempParams.add(zsIfReportList.getSkuCode());
          // 总库存
          stockTempParams.add(addStock);
          // rowid
          // stockTempParams.add(DatabaseUtil.generateSequence(SequenceType.CUSTOMER_CODE));
          stockTempParams.add("BATCH:0:0:0");
          stockTempParams.add(DateUtil.getSysdate());
          stockTempParams.add("BATCH:0:0:0");
          stockTempParams.add(DateUtil.getSysdate());
          logger.debug("Table:STOCK_TEMP INSERT Parameters:"
              + Arrays.toString(ArrayUtil.toArray(stockTempParams, Object.class)));
          // 添加在库临时表
          stockTempStatement = createPreparedStatement(insertStockTempSql);

          // 执行更新及插入
          DatabaseUtil.bindParameters(stockTempStatement, ArrayUtil.toArray(stockTempParams, Object.class));
          stockTempStatement.executeUpdate();

        }
        // 2014/06/10 库存更新对应 ob_张震 add end

        // 2014/06/10 库存更新对应 ob_张震 delete start
        // 插入临时表
        // logger.debug("INSERT STOCK_TEMP statement: " + insertStockTempSql);
        // // shop_code
        // stockTempParams.add(config.getSiteShopCode());
        // // sku_code
        // stockTempParams.add(zsIfReportList.getSkuCode());
        // // 总库存
        // stockTempParams.add(addStock);
        // stockTempParams.add("BATCH:0:0:0");
        // stockTempParams.add(DateUtil.getSysdate());
        // stockTempParams.add("BATCH:0:0:0");
        // stockTempParams.add(DateUtil.getSysdate());
        // logger.debug("Table:STOCK_TEMP INSERT Parameters:" +
        // Arrays.toString(ArrayUtil.toArray(stockTempParams, Object.class)));
        // // 添加在库临时表
        // stockTempStatement = createPreparedStatement(insertStockTempSql);
        //
        // // 执行更新及插入
        // DatabaseUtil.bindParameters(stockTempStatement,
        // ArrayUtil.toArray(stockTempParams, Object.class));
        // stockTempStatement.executeUpdate();
        // 2014/06/10 库存更新对应 ob_张震 delete end

        // 添加库存履历表
        logger.debug("INSERT STOCK_IO_DETAIL statement: " + insertStockIoDeatilSql);
        // 传递参数
        List<Object> stockIoDeatilParams = new ArrayList<Object>();
        // stock_io_id
        stockIoDeatilParams.add(DatabaseUtil.generateSequence(SequenceType.STOCK_IO_ID));
        // shop_code
        stockIoDeatilParams.add(config.getSiteShopCode());
        // tock_io_date
        stockIoDeatilParams.add(DateUtil.fromString(DateUtil.getSysdateString()));
        // sku_code
        stockIoDeatilParams.add(zsIfReportList.getSkuCode());
        // stock_io_quantity
        stockIoDeatilParams.add(addStock);
        // stock_io_type
        stockIoDeatilParams.add(StockIOType.ENTRY.getValue());
        // memo
        stockIoDeatilParams.add("(WMS-EC)入库完成导入。" + "导入库存数：" + addStock);
        stockIoDeatilParams.add("BATCH:0:0:0");
        stockIoDeatilParams.add(DateUtil.getSysdate());
        stockIoDeatilParams.add("BATCH:0:0:0");
        stockIoDeatilParams.add(DateUtil.getSysdate());
        // 添加库存履历表
        stockIoDeatilStatement = createPreparedStatement(insertStockIoDeatilSql);
        // 执行更新及插入
        DatabaseUtil.bindParameters(stockIoDeatilStatement, ArrayUtil.toArray(stockIoDeatilParams, Object.class));
        stockIoDeatilStatement.executeUpdate();
        logger.debug("Table:STOCK_IO_DETAIL INSERT Parameters:"
            + Arrays.toString(ArrayUtil.toArray(stockIoDeatilParams, Object.class)));

      } catch (SQLException e) {
        throw new CsvImportException(e);
      } catch (CsvImportException e) {
        throw e;
      } catch (RuntimeException e) {
        throw new CsvImportException(e);
      }
    }
  }

  // 更加库位标志判断移动数量
  public long getStockSite(ZsIfInvImportReport ziir) {
    // 判断商品是组合品（有原商品的）则不处理返回
    CCommodityHeaderDao cchdao = DIContainer.getDao(CCommodityHeaderDao.class);
    CCommodityHeader cch = cchdao.load("00000000", ziir.getSkuCode());
    if (cch != null) {
      if (StringUtil.hasValue(cch.getOriginalCommodityCode())) {
        return 0;
      }
    }

    if (ziir.getSkuCode().equals(config.getIfSkuKbnList().get(0))
        || ziir.getSkuCode().equals(config.getIfSkuKbnList().get(1))
        || ziir.getSkuCode().equals(config.getIfSkuKbnList().get(2))
        || ziir.getSkuCode().equals(config.getIfSkuKbnList().get(3))
        || ziir.getSkuCode().equals(config.getIfSkuKbnList().get(4))
        || ziir.getSkuCode().equals(config.getIfSkuKbnList().get(5))
        || ziir.getSkuCode().equals(config.getIfSkuKbnList().get(6))
        || ziir.getSkuCode().equals(config.getIfSkuKbnList().get(7))
        || ziir.getSkuCode().equals(config.getIfSkuKbnList().get(8))) {
      return 0;
    }
    // 收货的库位判断,不是0000(良品)返回
    if (!config.getIfLocationForEc().equals(ziir.getLocation())) {
      return 0;
    }
    return ziir.getStockTotal();
  }

}
