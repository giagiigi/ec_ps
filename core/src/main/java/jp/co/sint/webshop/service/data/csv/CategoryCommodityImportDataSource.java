package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CommitMode;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.dao.CategoryDao;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.data.dto.CategoryCommodity;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class CategoryCommodityImportDataSource extends
    SqlImportDataSource<CategoryCommodityCsvSchema, CategoryCommodityImportCondition> {

  private PreparedStatement insertStatement = null;

  private PreparedStatement selectStatement;

  private CategoryCommodity categoryCommodity = null;

  @Override
  protected void initializeResources() {

    String insertQuery = CsvUtil.buildInsertQuery(getSchema());
    String selectQuery = CsvUtil.buildCheckExistsQuery(getSchema());
    Logger logger = Logger.getLogger(this.getClass());
    logger.debug("INSERT statement: " + insertQuery);

    try {
      // カテゴリ陳列商品は主キー列,データ行ID,作成ユーザ,作成日時,更新ユーザ,更新日時のみのため、インサートのみ
      insertStatement = createPreparedStatement(insertQuery);
      selectStatement = createPreparedStatement(selectQuery);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      categoryCommodity = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CategoryCommodity.class);
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }
    // カテゴリ検索パスは導出項目の為、Validation時にダミーの値をセット
    categoryCommodity.setCategorySearchPath("/");
    categoryCommodity.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    DatabaseUtil.setUserStatus(categoryCommodity, getCondition().getLoginInfo());

    // バリデーションチェック
    summary.getErrors().addAll(BeanValidator.validate(categoryCommodity).getErrors());
    if (summary.hasError()) {
      return summary;
    }

    // カテゴリ存在チェック
    SimpleQuery categoryCountQuery = new SimpleQuery("SELECT COUNT(*) FROM CATEGORY WHERE CATEGORY_CODE = ?");
    categoryCountQuery.setParameters(categoryCommodity.getCategoryCode());
    Long categoryCount = Long.valueOf(executeScalar(categoryCountQuery).toString());
    if (categoryCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null,
          Message.get(CsvMessage.NOT_EXIST,
              Messages.getString("service.data.csv.CategoryCommodityImportDataSource.0"))));
    }

    // ショップコード相違チェック
    if (StringUtil.hasValue(getCondition().getShopCode())) {
      if (!categoryCommodity.getShopCode().equals(getCondition().getShopCode())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.VALIDATE_SHOPCODE)));
        return summary;
      }
    }

    // ショップデータ存在チェック
    SimpleQuery shopCountQuery = new SimpleQuery("SELECT COUNT(*) FROM SHOP WHERE SHOP_CODE = ? ");
    shopCountQuery.setParameters(categoryCommodity.getShopCode());
    Long shopCount = Long.valueOf(executeScalar(shopCountQuery).toString());
    if (shopCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null,
          Message.get(CsvMessage.NOT_EXIST,
              Messages.getString("service.data.csv.CategoryCommodityImportDataSource.1"))));
    }

    // 商品存在チェック
    SimpleQuery commodityCountQuery = new SimpleQuery(
        "SELECT COUNT(*) FROM COMMODITY_HEADER WHERE SHOP_CODE = ? AND COMMODITY_CODE = ?");
    commodityCountQuery.setParameters(categoryCommodity.getShopCode(), categoryCommodity.getCommodityCode());
    Long commodityCount = Long.valueOf(executeScalar(commodityCountQuery).toString());
    if (commodityCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null,
          Message.get(CsvMessage.NOT_EXIST,
              Messages.getString("service.data.csv.CategoryCommodityImportDataSource.2"))));
    }

    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {

    Logger logger = Logger.getLogger(this.getClass());

    try {

      PreparedStatement stmt = null;

      List<Object> params = new ArrayList<Object>();
      if (!exists(selectStatement, categoryCommodity.getShopCode(), categoryCommodity.getCategoryCode(), categoryCommodity
          .getCommodityCode())) {
        params.add(categoryCommodity.getShopCode());
        params.add(categoryCommodity.getCategoryCode());
        params.add(categoryCommodity.getCommodityCode());

        CategoryDao dao = DIContainer.getDao(CategoryDao.class);
        Category category = dao.load(categoryCommodity.getCategoryCode());

        params.add(category.getPath() + "~" + categoryCommodity.getCategoryCode());

        // 作成ユーザ, 作成日時, 更新ユーザ, 更新日時はDTOから取得しない
        params.add(getCondition().getLoginInfo().getRecordingFormat());
        params.add(DateUtil.getSysdate());
        params.add(getCondition().getLoginInfo().getRecordingFormat());
        params.add(DateUtil.getSysdate());

        logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
        DatabaseUtil.bindParameters(insertStatement, ArrayUtil.toArray(params, Object.class));

        stmt = insertStatement;
        int updCount = stmt.executeUpdate();
        if (updCount != 1) {
          throw new CsvImportException();
        }
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
  public CommitMode getCommitMode() {
    return CommitMode.FOR_EACH;
  }

  @Override
  protected void clearResources() {
    DatabaseUtil.closeResource(insertStatement);
    DatabaseUtil.closeResource(selectStatement);
  }

}
