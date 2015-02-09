package jp.co.sint.webshop.service.data.csv;

import java.math.BigDecimal;
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
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class GiftImportDataSource extends SqlImportDataSource<GiftCsvSchema, GiftImportCondition> {

  /** INSERT用Statement */
  private PreparedStatement insertStatement;

  /** UPDATE用Statement */
  private PreparedStatement updateStatement;

  /** 存在チェック用Statement */
  private PreparedStatement selectStatement = null;

  private Gift gift = null;

  @Override
  protected void initializeResources() {
    String inserQuery = //CsvUtil.buildInsertQuery(getSchema());
      "INSERT INTO GIFT "
      + " (SHOP_CODE, "
       + " GIFT_CODE, "
       + " GIFT_NAME, "
       + " GIFT_DESCRIPTION, "
       + " GIFT_PRICE, "
       + " DISPLAY_ORDER, "
       + " GIFT_TAX_TYPE, "
       + " DISPLAY_FLG, "
       + " ORM_ROWID, "
       + " CREATED_USER, "
       + " CREATED_DATETIME, "
       + " UPDATED_USER, "
       + " UPDATED_DATETIME) "
       + " VALUES "
      + " (?, ?, ?, ?, ?, ?, ?, ?, NEXTVAL('GIFT_SEQ'), ?, ?, ?, ?)";
    String updateQuery = //CsvUtil.buildUpdateQuery(getSchema());
      "UPDATE GIFT "
      + " SET GIFT_NAME     = ?, "
       + " GIFT_DESCRIPTION = ?, "
       + " GIFT_PRICE       = ?, "
       + " DISPLAY_ORDER    = ?, "
       + " GIFT_TAX_TYPE    = ?, "
       + " DISPLAY_FLG      = ?, "
       + " UPDATED_USER     = ?, "
       + " UPDATED_DATETIME = ? "
       + " WHERE SHOP_CODE = ? "
       + " AND GIFT_CODE = ?";
    String selectQuery = //CsvUtil.buildCheckExistsQuery(getSchema());
      " SELECT COUNT(*) FROM GIFT WHERE SHOP_CODE= ? AND GIFT_CODE= ? ";
    Logger logger = Logger.getLogger(this.getClass());
    logger.debug("INSERT statement: " + inserQuery);
    logger.debug("UPDATE statement: " + updateQuery);
    logger.debug("CHECK  statiment: " + selectQuery);

    try {
      insertStatement = createPreparedStatement(inserQuery);
      updateStatement = createPreparedStatement(updateQuery);
      selectStatement = createPreparedStatement(selectQuery);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      gift = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), Gift.class);
      gift.setGiftTaxType(TaxType.NO_TAX.longValue());
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }
    gift.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    DatabaseUtil.setUserStatus(gift, getCondition().getLoginInfo());

    // 単項目バリデーションチェック
    summary.getErrors().addAll(BeanValidator.validate(gift).getErrors());
    // 10.1.1 10019 追加 ここから
    //modify by V10-CH 170 start
    if (gift.getGiftPrice() != null && BigDecimalUtil.isBelow(gift.getGiftPrice(), BigDecimal.ZERO)) {
    //if (NumUtil.isNegative(gift.getGiftPrice())) {
    //modify by V10-CH 170 end
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR,
          Messages.getString("service.data.csv.GiftImportDataSource.2"))));
    }
    if (NumUtil.isNegative(gift.getDisplayOrder())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR,
          Messages.getString("service.data.csv.GiftImportDataSource.3"))));
    }
    // 10.1.1 10019 追加 ここまで
    if (summary.hasError()) {
      return summary;
    }

    // ショップコード相違チェック
    if (StringUtil.hasValue(getCondition().getShopCode())) {
      if (!gift.getShopCode().equals(getCondition().getShopCode())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.VALIDATE_SHOPCODE)));
        return summary;
      }
    }

    // ショップ存在チェック
    SimpleQuery shopCountQuery = new SimpleQuery("SELECT COUNT(*) FROM SHOP WHERE SHOP_CODE = ? ");
    shopCountQuery.setParameters(gift.getShopCode());
    Long commodityCount = Long.valueOf(executeScalar(shopCountQuery).toString());
    if (commodityCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST,
          Messages.getString("service.data.csv.GiftImportDataSource.0"))));
    }

    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    try {
      PreparedStatement pstmt = null;

      List<Object> params = new ArrayList<Object>();
      if (exists(selectStatement, gift.getShopCode(), gift.getGiftCode())) {
        params.add(gift.getGiftName());
        params.add(gift.getGiftDescription());
        params.add(gift.getGiftPrice());
        params.add(gift.getDisplayOrder());
        params.add(gift.getGiftTaxType());
        params.add(gift.getDisplayFlg());

        params.add(getCondition().getLoginInfo().getRecordingFormat());
        params.add(DateUtil.getSysdate());

        params.add(gift.getShopCode());
        params.add(gift.getGiftCode());

        pstmt = updateStatement;
        logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
      } else {
        params.add(gift.getShopCode());
        params.add(gift.getGiftCode());
        params.add(gift.getGiftName());
        params.add(gift.getGiftDescription());
        params.add(gift.getGiftPrice());
        params.add(gift.getDisplayOrder());
        params.add(gift.getGiftTaxType());
        params.add(gift.getDisplayFlg());

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
    super.clearResources();
    DatabaseUtil.closeResource(insertStatement);
    DatabaseUtil.closeResource(updateStatement);
    DatabaseUtil.closeResource(selectStatement);
  }

}
