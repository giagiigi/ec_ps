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
import jp.co.sint.webshop.data.dto.CampaignCommodity;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class CampaignCommodityImportDataSource extends
    SqlImportDataSource<CampaignCommodityCsvSchema, CampaignCommodityImportCondition> {

  /** INSERT用Statement */
  private PreparedStatement insertStatement;

  /** UPDATE用Statement */
  private PreparedStatement updateStatement;

  /** 存在チェック用Statement */
  private PreparedStatement selectStatement = null;

  private CampaignCommodity campaignCommodity = null;

  @Override
  protected void initializeResources() {
    Logger logger = Logger.getLogger(this.getClass());
    String insertQuery = CsvUtil.buildInsertQuery(getSchema());
    String updateQuery = CsvUtil.buildUpdateQuery(getSchema());
    String selectQuery = CsvUtil.buildCheckExistsQuery(getSchema());
    logger.debug("INSERT statement: " + insertQuery);
    logger.debug("UPDATE statement: " + updateQuery);

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
      campaignCommodity = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CampaignCommodity.class);
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }
    campaignCommodity.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    DatabaseUtil.setUserStatus(campaignCommodity, getCondition().getLoginInfo());

    // 単項目バリデーションチェック
    summary.getErrors().addAll(BeanValidator.validate(campaignCommodity).getErrors());
    if (summary.hasError()) {
      return summary;
    }

    // ショップコード相違チェック
    if (StringUtil.hasValue(getCondition().getShopCode())) {
      if (!campaignCommodity.getShopCode().equals(getCondition().getShopCode())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.VALIDATE_SHOPCODE)));
        return summary;
      }
    }

    // ショップデータ存在チェック
    SimpleQuery shopCountQuery = new SimpleQuery("SELECT COUNT(*) FROM SHOP WHERE SHOP_CODE = ? ");
    shopCountQuery.setParameters(campaignCommodity.getShopCode());
    Long shopCount = Long.valueOf(executeScalar(shopCountQuery).toString());
    if (shopCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST,
          Messages.getString("service.data.csv.CampaignCommodityImportDataSource.0"))));
    }

    // キャンペーンデータ存在チェック
    SimpleQuery campaignCountQuery =
      new SimpleQuery("SELECT COUNT(*) FROM CAMPAIGN WHERE SHOP_CODE = ? AND CAMPAIGN_CODE = ? ");
    campaignCountQuery.setParameters(campaignCommodity.getShopCode(), campaignCommodity.getCampaignCode());
    Long campaignCount = Long.valueOf(executeScalar(campaignCountQuery).toString());
    if (campaignCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST,
          Messages.getString("service.data.csv.CampaignCommodityImportDataSource.1"))));
    }

    // 商品データ存在チェック
    SimpleQuery commodityCountQuery = new SimpleQuery(
        "SELECT COUNT(*) FROM COMMODITY_HEADER WHERE SHOP_CODE = ? AND COMMODITY_CODE = ? ");
    commodityCountQuery.setParameters(campaignCommodity.getShopCode(), campaignCommodity.getCommodityCode());
    Long commodityCount = Long.valueOf(executeScalar(commodityCountQuery).toString());
    if (commodityCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST,
          Messages.getString("service.data.csv.CampaignCommodityImportDataSource.2"))));
    }

    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());
    try {
      PreparedStatement pstmt = null;
      List<Object> params = new ArrayList<Object>();
      if (exists(selectStatement, campaignCommodity.getShopCode(), campaignCommodity.getCampaignCode(), campaignCommodity
          .getCommodityCode())) {

        params.add(getCondition().getLoginInfo().getRecordingFormat());
        params.add(DateUtil.getSysdate());

        params.add(campaignCommodity.getShopCode());
        params.add(campaignCommodity.getCampaignCode());
        params.add(campaignCommodity.getCommodityCode());

        pstmt = updateStatement;
        logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
      } else {

        params.add(campaignCommodity.getShopCode());
        params.add(campaignCommodity.getCampaignCode());
        params.add(campaignCommodity.getCommodityCode());

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
