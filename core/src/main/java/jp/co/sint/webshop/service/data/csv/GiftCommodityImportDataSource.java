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
import jp.co.sint.webshop.data.dto.GiftCommodity;
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

public class GiftCommodityImportDataSource extends SqlImportDataSource<GiftCommodityCsvSchema, GiftCommodityImportCondition> {

  /** INSERT用Statement */
  private PreparedStatement insertStatement;

  /** UPDATE用Statement */
  private PreparedStatement updateStatement;

  /** 存在チェック用Statement */
  private PreparedStatement selectStatement = null;

  private GiftCommodity giftCommodity = null;

  @Override
  protected void initializeResources() {
    Logger logger = Logger.getLogger(this.getClass());
    String insertQuery = CsvUtil.buildInsertQuery(getSchema());
    String updateQuery = CsvUtil.buildUpdateQuery(getSchema());
    String selectQuery = CsvUtil.buildCheckExistsQuery(getSchema());
    logger.debug("INSERT statement: " + insertQuery);
    logger.debug("UPDATE statement: " + updateQuery);
    logger.debug("SELECT statement: " + selectQuery);

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
      giftCommodity = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), GiftCommodity.class);
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }
    giftCommodity.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    DatabaseUtil.setUserStatus(giftCommodity, getCondition().getLoginInfo());

    // 単項目バリデーションチェック
    summary.getErrors().addAll(BeanValidator.validate(giftCommodity).getErrors());
    if (summary.hasError()) {
      return summary;
    }

    // ショップコード相違チェック
    if (StringUtil.hasValue(getCondition().getShopCode())) {
      if (!giftCommodity.getShopCode().equals(getCondition().getShopCode())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.VALIDATE_SHOPCODE)));
        return summary;
      }
    }

    // ショップデータ存在チェック
    SimpleQuery shopCountQuery = new SimpleQuery("SELECT COUNT(*) FROM SHOP WHERE SHOP_CODE = ? ");
    shopCountQuery.setParameters(giftCommodity.getShopCode());
    Long shopCount = Long.valueOf(executeScalar(shopCountQuery).toString());
    if (shopCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null,
          Message.get(CsvMessage.NOT_EXIST, Messages.getString("service.data.csv.GiftCommodityImportDataSource.0"))));
    }

    // ギフトデータ存在チェック
    SimpleQuery giftCountQuery = new SimpleQuery("SELECT COUNT(*) FROM GIFT WHERE SHOP_CODE = ? AND GIFT_CODE = ? ");
    giftCountQuery.setParameters(giftCommodity.getShopCode(), giftCommodity.getGiftCode());
    Long giftCount = Long.valueOf(executeScalar(giftCountQuery).toString());
    if (giftCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null,
          Message.get(CsvMessage.NOT_EXIST, Messages.getString("service.data.csv.GiftCommodityImportDataSource.1"))));
    }

    // 商品データ存在チェック
    SimpleQuery commodityCountQuery = new SimpleQuery(
        "SELECT COUNT(*) FROM COMMODITY_HEADER WHERE SHOP_CODE = ? AND COMMODITY_CODE = ? ");
    commodityCountQuery.setParameters(giftCommodity.getShopCode(), giftCommodity.getCommodityCode());
    Long commodityCount = Long.valueOf(executeScalar(commodityCountQuery).toString());
    if (commodityCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null,
          Message.get(CsvMessage.NOT_EXIST, Messages.getString("service.data.csv.GiftCommodityImportDataSource.2"))));
    }

    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());
    try {
      PreparedStatement pstmt = null;
      List<Object> params = new ArrayList<Object>();
      if (exists(selectStatement, giftCommodity.getShopCode(), giftCommodity.getGiftCode(), giftCommodity.getCommodityCode())) {

        params.add(getCondition().getLoginInfo().getRecordingFormat());
        params.add(DateUtil.getSysdate());

        params.add(giftCommodity.getShopCode());
        params.add(giftCommodity.getGiftCode());
        params.add(giftCommodity.getCommodityCode());

        pstmt = updateStatement;
        logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
      } else {

        params.add(giftCommodity.getShopCode());
        params.add(giftCommodity.getGiftCode());
        params.add(giftCommodity.getCommodityCode());

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
