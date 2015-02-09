package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.domain.ShelfLifeFlag;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityProductionDate;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.catalog.InputCommodityProductionDate;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

/**
 * 商品生产日期
 * 
 * @author ks
 */
public class CommodityProductionDateImportDataSource extends
    SqlImportDataSource<CommodityProductionDateImportCsvSchema, CommodityProductionDateImportCondition> {

  private PreparedStatement insertCommodityProductionDateStatement = null;

  private PreparedStatement updateCommodityProductionDateStatement = null;

  private InputCommodityProductionDate inputCommodityProductionDate = null;

  private Logger logger = Logger.getLogger(this.getClass());

  @Override
  protected void initializeResources() {
    String insertIntegratedMemberQuery = "INSERT INTO COMMODITY_PRODUCTION_DATE("
      + " SKU_CODE, EARLIST_DATE, ORM_ROWID,"
      + " CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME)" + " VALUES (?, ?, "
      + SqlDialect.getDefault().getNextvalNOprm("COMMODITY_PRODUCTION_DATE_SEQ") + ", ?, ?, ?, ?)";

    String updateIntegratedMemberQuery = "UPDATE COMMODITY_PRODUCTION_DATE"
        + " SET EARLIST_DATE=?,UPDATED_USER=?, UPDATED_DATETIME=?" + " WHERE SKU_CODE=?";

    logger.debug("INSERT COMMODITY_PRODUCTION_DATE statement: " + insertIntegratedMemberQuery);
    logger.debug("UPDATE COMMODITY_PRODUCTION_DATE statement: " + updateIntegratedMemberQuery);

    try {
      insertCommodityProductionDateStatement = createPreparedStatement(insertIntegratedMemberQuery);
      updateCommodityProductionDateStatement = createPreparedStatement(updateIntegratedMemberQuery);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }

  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      // 导入文件数据转换成实体Bean对象
      inputCommodityProductionDate = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), InputCommodityProductionDate.class);

    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }

    // 単項目バリデーションチェック
    summary.getErrors().addAll(BeanValidator.validate(inputCommodityProductionDate).getErrors());
    if (summary.hasError()) {
      return summary;
    }

    return summary;
  }

  public void executeUpdate(List<String> csvLine) throws SQLException {
    WebshopConfig config = DIContainer.getWebshopConfig();
    String shopCode = config.getSiteShopCode();
    String chSql = "SELECT * FROM COMMODITY_HEADER WHERE SHOP_CODE = '" + shopCode + "' AND COMMODITY_CODE = '" + inputCommodityProductionDate.getSkuCode() + "'"
      + " AND SHELF_LIFE_FLAG = " + ShelfLifeFlag.BYENDTIMEORCREATETIME.getValue(); 
    CommodityHeader commodityHeader = loadAsBean(new SimpleQuery(chSql), CommodityHeader.class);
    
    if(commodityHeader != null) {
      String cpdSql = "SELECT * FROM COMMODITY_PRODUCTION_DATE WHERE SKU_CODE = '" + inputCommodityProductionDate.getSkuCode() + "'";
      CommodityProductionDate commodityProductionDate = loadAsBean(new SimpleQuery(cpdSql), CommodityProductionDate.class);

      if (commodityProductionDate == null) {
        addCommodityProductionDate();
      } else {
        updateCommodityProductionDate();
      }
    }
  }

  private void addCommodityProductionDate() {
    try {
      List<Object> insertCPDParams = new ArrayList<Object>();
      insertCPDParams.add(inputCommodityProductionDate.getSkuCode());
      insertCPDParams.add(inputCommodityProductionDate.getEarlistDate());
      insertCPDParams.add(getCondition().getLoginInfo().getRecordingFormat());
      insertCPDParams.add(DateUtil.getSysdate());
      insertCPDParams.add(getCondition().getLoginInfo().getRecordingFormat());
      insertCPDParams.add(DateUtil.getSysdate());
      logger.debug("Table:COMMODITY_PRODUCTION_DATE INSERT Parameters:"
          + Arrays.toString(ArrayUtil.toArray(insertCPDParams, Object.class)));
      DatabaseUtil.bindParameters(insertCommodityProductionDateStatement, ArrayUtil.toArray(insertCPDParams, Object.class));
      int istMemberCount = insertCommodityProductionDateStatement.executeUpdate();

      if (istMemberCount != 1) {
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

  private void updateCommodityProductionDate() {
    try {
      List<Object> updateCPDParams = new ArrayList<Object>();
      updateCPDParams.add(inputCommodityProductionDate.getEarlistDate());
      updateCPDParams.add(getCondition().getLoginInfo().getRecordingFormat());
      updateCPDParams.add(DateUtil.getSysdate());
      updateCPDParams.add(inputCommodityProductionDate.getSkuCode());

      logger.debug("Table:COMMODITY_PRODUCTION_DATE UPDATE Parameters:"
          + Arrays.toString(ArrayUtil.toArray(updateCPDParams, Object.class)));

      DatabaseUtil.bindParameters(updateCommodityProductionDateStatement, ArrayUtil.toArray(updateCPDParams, Object.class));
      int updMemberCount = updateCommodityProductionDateStatement.executeUpdate();

      if (updMemberCount != 1) {
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

  protected void clearResources() {
    DatabaseUtil.closeResource(insertCommodityProductionDateStatement);
    DatabaseUtil.closeResource(updateCommodityProductionDateStatement);
  }

}
