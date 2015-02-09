package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.configure.TmallWarnStockSendMailConfig;
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
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.mail.MailInfo;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.data.CsvSchemaType;
import jp.co.sint.webshop.service.order.ZsIfInvImportReport;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

/**
 * 从ERP导入sku有效库存
 * 
 * @author OS011
 */
public class ZsIfInvErpImportDataSource extends
    SqlImportDataSource<ZsIfInvErpImportCsvSchema, ZsIfInvErpImportCondition> {

  private Logger logger = Logger.getLogger(this.getClass());

  private ZsIfInvImportReport zsIfInvImportReport = null;

  private PreparedStatement stockTempStatement = null;

  private PreparedStatement stockIoDeatilStatement = null;

  // 2014/06/10 库存更新对应 ob_张震 delete start
  // private PreparedStatement stockTempDelStatement = null;
  // 2014/06/10 库存更新对应 ob_张震 delete end

  WebshopConfig config = DIContainer.getWebshopConfig();

  String DETAIL_TABLE_NAME = "STOCK_TEMP";

  String IO_DETAIL_TABLE_NAME = "STOCK_IO_DETAIL";

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
  // 查询发货指示中的数量
  // String selectSql = "" +
  // " SELECT ( CASE WHEN SUM(PURCHASING_AMOUNT) IS NULL THEN 0 ELSE  SUM(PURCHASING_AMOUNT) END"
  // + " 	  + "
  // + "   (" +
  // " 	  SELECT CASE WHEN SUM(PURCHASING_AMOUNT) IS NULL THEN 0 ELSE SUM(PURCHASING_AMOUNT) END  FROM "
  // + "   TMALL_SHIPPING_HEADER SH INNER JOIN" +
  // " 	  TMALL_SHIPPING_DETAIL SD ON " + " 	  SH.SHOP_CODE=SD.SHOP_CODE"
  // + " 	  AND SH.SHIPPING_NO=SD.SHIPPING_NO" +
  // " 	  AND SH.SHIPPING_STATUS='2'" + " 	  AND SD.SKU_CODE=?" + " 	  ) "
  // + " 	  ) AS PURCHASING_AMOUNT " + " 	  FROM " +
  // " 	  SHIPPING_HEADER SH INNER JOIN" + " 	  SHIPPING_DETAIL SD ON "
  // + " 	  SH.SHOP_CODE=SD.SHOP_CODE" + " 	  AND SH.SHIPPING_NO=SD.SHIPPING_NO"
  // + " 	  AND SH.SHIPPING_STATUS='2'"
  // + " 	  AND SD.SKU_CODE=?";
  String selectSql = ""
      + " SELECT ( COALESCE(SUM(PURCHASING_AMOUNT), 0) + "
      + "           ( SELECT COALESCE(SUM(PURCHASING_AMOUNT), 0) "
      + "               FROM TMALL_SHIPPING_HEADER SH "
      + "              INNER JOIN TMALL_SHIPPING_DETAIL SD "
      + "                 ON SH.SHOP_CODE = SD.SHOP_CODE "
      + "                AND SH.SHIPPING_NO = SD.SHIPPING_NO "
      + "                AND SH.SHIPPING_STATUS = '2' "
      + "                AND SD.SKU_CODE = ? ) + "
      + "           ( SELECT COALESCE(SUM(SDC.PURCHASING_AMOUNT), 0) "
      + "               FROM TMALL_SHIPPING_HEADER SH "
      + "              INNER JOIN TMALL_SHIPPING_DETAIL SD "
      + "                 ON SH.SHOP_CODE = SD.SHOP_CODE "
      + "                AND SH.SHIPPING_NO = SD.SHIPPING_NO "
      + "                AND SH.SHIPPING_STATUS = '2' "
      + "              INNER JOIN TMALL_SHIPPING_DETAIL_COMPOSITION SDC "
      + "                 ON SH.SHOP_CODE = SDC.SHOP_CODE "
      + "                AND SD.SHIPPING_NO = SDC.SHIPPING_NO "
      + "                AND SD.SHIPPING_DETAIL_NO = SDC.SHIPPING_DETAIL_NO "
      + "                AND SD.SKU_CODE = SDC.PARENT_SKU_CODE "
      + "                AND SDC.CHILD_SKU_CODE = ? ) + "
      + "           ( SELECT COALESCE(SUM(PURCHASING_AMOUNT), 0) "
      + "               FROM JD_SHIPPING_HEADER SH "
      + "              INNER JOIN JD_SHIPPING_DETAIL SD "
      + "                 ON SH.SHOP_CODE = SD.SHOP_CODE "
      + "                AND SH.SHIPPING_NO = SD.SHIPPING_NO "
      + "                AND SH.SHIPPING_STATUS = '2' "
      + "                AND SD.SKU_CODE = ? ) +"
      + "           ( SELECT COALESCE(SUM(SDC.PURCHASING_AMOUNT), 0) "
      + "               FROM JD_SHIPPING_HEADER SH "
      + "              INNER JOIN JD_SHIPPING_DETAIL SD "
      + "                 ON SH.SHOP_CODE = SD.SHOP_CODE "
      + "                AND SH.SHIPPING_NO = SD.SHIPPING_NO "
      + "                AND SH.SHIPPING_STATUS = '2' "
      + "              INNER JOIN JD_SHIPPING_DETAIL_COMPOSITION SDC "
      + "                 ON SH.SHOP_CODE = SDC.SHOP_CODE "
      + "                AND SD.SHIPPING_NO = SDC.SHIPPING_NO "
      + "                AND SD.SHIPPING_DETAIL_NO = SDC.SHIPPING_DETAIL_NO "
      + "                AND SD.SKU_CODE = SDC.PARENT_SKU_CODE "
      + "                AND SDC.CHILD_SKU_CODE = ? )  "
      + "        ) AS PURCHASING_AMOUNT "
      + "   FROM SHIPPING_HEADER SH "
      + "  INNER JOIN SHIPPING_DETAIL SD "
      + "     ON SH.SHOP_CODE = SD.SHOP_CODE "
      + "    AND SH.SHIPPING_NO = SD.SHIPPING_NO "
      + "    AND SH.SHIPPING_STATUS = '2' "
      + "    AND SD.SKU_CODE = ? ";

  // 2014/06/10 库存更新对应 ob_张震 update end

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

  // 2014/06/10 库存更新对应 ob_张震 delete start
  // 查询在库信息
  // String selectStockTempSql =
  // "SELECT SKU_CODE FROM STOCK_TEMP WHERE SHOP_CODE = ? AND SKU_CODE=?";
  // 2014/06/10 库存更新对应 ob_张震 delete end

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
              .getString("service.data.csv.ZsIfInvImportDataSource.5"))));
    }
    // 判断商品是组合品（有原商品的）则不处理返回
    CCommodityHeaderDao cchdao = DIContainer.getDao(CCommodityHeaderDao.class);
    CCommodityHeader cch = cchdao.load("00000000", zsIfInvImportReport.getSkuCode());
    if (cch != null) {
      if (StringUtil.hasValue(cch.getOriginalCommodityCode())) {
        return summary;
      }
    }
    // 是优惠SKU,运费SKU,积分SKU时返回
    if (zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(0))
        || zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(1))
        || zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(2))
        || zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(3))
        || zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(4))
        || zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(5))
        || zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(6))
        || zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(7))
        || zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(8))) {
      return summary;
    }

    // 判断入出库数字段是否是数值
    // if (NumUtil.isNegative(zsIfInvImportReport.getStockTotal())) {
    // summary.getErrors().add(
    // new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE,
    // Messages
    // .getString("service.data.csv.ZsIfInvImportDataSource.4"))));
    // }
    // 判断入出库数字段是否是数值

    // 判断商品是否在库
    CsvSchema schema = DIContainer.getCsvSchema(CsvSchemaType.STOCK);
    Query stockCountQuery = new SimpleQuery(CsvUtil.buildCheckExistsQuery(schema), config.getSiteShopCode(),
        zsIfInvImportReport.getSkuCode());
    Long stockCount = Long.valueOf(executeScalar(stockCountQuery).toString());
    if (stockCount == 0) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
              .getString("service.data.csv.ZsIfInvImportDataSource.5")
              + zsIfInvImportReport.getSkuCode())));
    }

    for (String error : errorMessageList) {
      summary.getErrors().add(new ValidationResult(null, null, error));
    }

    if (summary.hasError()) {
      return summary;
    }
    // 未发货指示的引当取的
    // 2014/06/10 库存更新对应 ob_张震 update start
    // Query shippingGetquery = new SimpleQuery(selectSql,
    // zsIfInvImportReport.getSkuCode(), zsIfInvImportReport
    // .getSkuCode());
    Query shippingGetquery = new SimpleQuery(selectSql, zsIfInvImportReport.getSkuCode(), zsIfInvImportReport
        .getSkuCode(), zsIfInvImportReport.getSkuCode(),zsIfInvImportReport.getSkuCode(),zsIfInvImportReport.getSkuCode());
    // 2014/06/10 库存更新对应 ob_张震 update end
    ShippingDetail shippingDetail = loadAsBean(shippingGetquery, ShippingDetail.class);
    // 判断入库数量的范围合理性
    boolean isValid = true;
    String errorMessage = Message.get(CsvMessage.STOCK_AMOUNT_OVERFLOW, "(99999999L, 0L)");
    isValid &= zsIfInvImportReport.getStockTotal() + shippingDetail.getPurchasingAmount() <= 99999999L;
    // 判断入库数量的范围合理性
    if (!isValid) {
      summary.getErrors().add(new ValidationResult(null, null, errorMessage));
    }

    return summary;
  }

  /**
   * 库存导入处理
   */
  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    // 判断商品是组合品（有原商品的）则不处理返回
    CCommodityHeaderDao cchdao = DIContainer.getDao(CCommodityHeaderDao.class);
    CCommodityHeader cch = cchdao.load("00000000", zsIfInvImportReport.getSkuCode());
    if (cch != null) {
      if (StringUtil.hasValue(cch.getOriginalCommodityCode())) {
        return;
      }
    }
    // 是优惠SKU,运费SKU,积分SKU时返回
    if (zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(0))
        || zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(1))
        || zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(2))
        || zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(3))
        || zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(4))
        || zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(5))
        || zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(6))
        || zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(7))
        || zsIfInvImportReport.getSkuCode().equals(config.getIfSkuKbnList().get(8))) {
      return;
    }
    // 库位编号判断
    if (!config.getIfLocationForEc().equals(zsIfInvImportReport.getLocation())) {
      return;
    }
    logger.debug("SELECT  STOCK statement: " + selectStockSql);

    // 实例化Stock类
    Query stockGetQuery = new SimpleQuery(selectStockSql, config.getSiteShopCode(), zsIfInvImportReport.getSkuCode());

    Stock stock = loadAsBean(stockGetQuery, Stock.class);

    // 发货指示中的引当取的
    // 2014/06/10 库存更新对应 ob_张震 update start
    // Query shippingGetquery = new SimpleQuery(selectSql,
    // zsIfInvImportReport.getSkuCode(), zsIfInvImportReport
    // .getSkuCode());
    Query shippingGetquery = new SimpleQuery(selectSql, zsIfInvImportReport.getSkuCode(), zsIfInvImportReport
        .getSkuCode(), zsIfInvImportReport.getSkuCode(),zsIfInvImportReport.getSkuCode(),zsIfInvImportReport.getSkuCode());
    // 2014/06/10 库存更新对应 ob_张震 update end
    ShippingDetail shippingDetail = loadAsBean(shippingGetquery, ShippingDetail.class);

    // 总在库数公式：ERP贩卖可能数+EC发货指示数+Tmall发货指示数+JD发货指示数
    Long allStockQuantity = zsIfInvImportReport.getStockTotal() + shippingDetail.getPurchasingAmount();

    // 增量计算
    Long addStock = allStockQuantity - stock.getStockTotal();

    // 传递参数
    List<Object> stockTempParams = new ArrayList<Object>();
    // 2014/06/10 库存更新对应 ob_张震 delete start
    // 传递参数
    // List<Object> stockTempDelParams = new ArrayList<Object>();
    // 2014/06/10 库存更新对应 ob_张震 delete end
    try {
      // 2014/06/10 库存更新对应 ob_张震 delete start
      // logger.debug("DELETE  STOCK_TEMP START");
      // // 删除临时表
      // stockTempDelStatement = createPreparedStatement(deleteStockTempSql);
      // // sku_code
      // stockTempDelParams.add(zsIfInvImportReport.getSkuCode());
      // DatabaseUtil.bindParameters(stockTempDelStatement,
      // ArrayUtil.toArray(stockTempDelParams, Object.class));
      // stockTempDelStatement.executeUpdate();
      // logger.debug("DELETE  STOCK_TEMP END");
      // 2014/06/10 库存更新对应 ob_张震 delete end

      // 插入临时表
      logger.debug("INSERT STOCK_TEMP statement: " + insertStockTempSql);

      // 实际库存数为负数时
      if (zsIfInvImportReport.getStockTotal() < 0L) {
        MailInfo mailInfo = new MailInfo();
        StringBuffer sb = new StringBuffer();
        sb.append("以下是接口执行警告<BR><BR>");
        sb.append("商品 " + zsIfInvImportReport.getSkuCode() + " 库存小于零<BR><BR>");
        sb.append("导入文件来源：(ERP->FRONT,Inventory实际库存)");
        mailInfo.setText(sb.toString());
        mailInfo.setSubject("【品店】库存警告");
        mailInfo.setSendDate(DateUtil.getSysdate());

        TmallWarnStockSendMailConfig tmllMailSend = DIContainer.get(TmallWarnStockSendMailConfig.class.getSimpleName());
        mailInfo.setFromInfo(tmllMailSend.getMailFromAddr(), tmllMailSend.getMailFromName());
        String[] mailToAddrArray = tmllMailSend.getMailToAddr().split(";");
        String[] mailToNameArray = tmllMailSend.getMailToName().split(";");
        for (int i = 0; i < mailToAddrArray.length; i++) {
          if (i >= mailToNameArray.length) {
            mailInfo.addToList(mailToAddrArray[i], mailToAddrArray[i]);
          } else {
            mailInfo.addToList(mailToAddrArray[i], mailToNameArray[i]);
          }
        }
        mailInfo.setContentType(MailInfo.CONTENT_TYPE_HTML);
        MailingService svc = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
        svc.sendImmediate(mailInfo);
        return;
      }
      // 增量没有变化时
      if (addStock == 0L) {
        return;
      }

      // 2014/06/10 库存更新对应 ob_张震 add start
      // 检验库存临时表
      Query hasTempDataQuery = new SimpleQuery(hasDataStockTempSql, zsIfInvImportReport.getSkuCode());
      Long tempData = NumUtil.toLong(executeScalar(hasTempDataQuery).toString());
      if (tempData > 0L) {
        // 临时表有数据时，更新
        stockTempParams.add(addStock);
        stockTempParams.add(zsIfInvImportReport.getSkuCode());
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
        stockTempParams.add(zsIfInvImportReport.getSkuCode());
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
      // // shop_code
      // stockTempParams.add(config.getSiteShopCode());
      // // sku_code
      // stockTempParams.add(zsIfInvImportReport.getSkuCode());
      // // 总库存
      // stockTempParams.add(addStock);
      // // rowid
      // //
      // stockTempParams.add(DatabaseUtil.generateSequence(SequenceType.CUSTOMER_CODE));
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
      stockIoDeatilParams.add(zsIfInvImportReport.getSkuCode());
      // stock_io_quantity
      stockIoDeatilParams.add(addStock);
      // stock_io_type
      stockIoDeatilParams.add(StockIOType.ENTRY.getValue());
      // memo
      stockIoDeatilParams.add("(ERP-EC)实际库存导入。" + "导入库存数：" + addStock);
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
