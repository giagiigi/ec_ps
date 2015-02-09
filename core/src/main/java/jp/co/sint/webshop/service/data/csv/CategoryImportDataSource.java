package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.SqlUtil;
import jp.co.sint.webshop.data.csv.CommitMode;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.TransactionMode;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.catalog.CatalogQuery;
import jp.co.sint.webshop.service.catalog.UpdateCategoryPathProcedure;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class CategoryImportDataSource extends SqlImportDataSource<CategoryCsvSchema, CategoryImportCondition> {

  private Category category = null;

  private PreparedStatement insertStatement = null;

  private PreparedStatement updateStatement = null;

  private PreparedStatement selectStatement = null;

  private boolean succeedFlg = true;

  private String updateQuery = "" + " UPDATE CATEGORY " + " SET CATEGORY_NAME_PC = ? " + " , CATEGORY_NAME_MOBILE = ? "
      + " , PARENT_CATEGORY_CODE = ? " + " , DISPLAY_ORDER = ? " + " , UPDATED_USER = ? " + " , UPDATED_DATETIME = ? "
      + " WHERE CATEGORY_CODE = ? ";

  private String[] validateTarget = new String[] {
      "categoryCode", "categoryNamePc", "categoryNameMobile", "parentCategoryCode", "displayOrder"
  };

  private WebshopConfig config = DIContainer.getWebshopConfig();

  @Override
  protected void initializeResources() {
    String insertQuery = CsvUtil.buildInsertQuery(getSchema());
    String selectQuery = CsvUtil.buildCheckExistsQuery(getSchema());

    Logger logger = Logger.getLogger(this.getClass());
    logger.debug("INSERT statement: " + insertQuery);
    logger.debug("UPDATE statement: " + updateQuery);
    logger.debug("CHECK  statiment: " + selectQuery);

    try {
      insertStatement = createPreparedStatement(insertQuery);
      updateStatement = createPreparedStatement(updateQuery);
      selectStatement = createPreparedStatement(selectQuery);
    } catch (Exception e) {
      succeedFlg = false;
      throw new DataAccessException(e);
    }

  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      category = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), Category.class);
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
    }
    DatabaseUtil.setUserStatus(category, ServiceLoginInfo.getInstance());

    summary.getErrors().addAll(BeanValidator.partialValidate(category, validateTarget).getErrors());
    // 10.1.1 10019 追加 ここから
    checkMinusNumber(summary);
    if (summary.hasError()) {
      return summary;
    }
    // 10.1.1 10019 追加 ここまで

    // 自分自身を親カテゴリに指定していないかチェック
    if (StringUtil.hasValueAllOf(category.getCategoryCode(), category.getParentCategoryCode())
        && category.getCategoryCode().equals(category.getParentCategoryCode())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.CHANGE_CATEGORY_OWN)));
    }

    // ルートカテゴリの存在チェック
    Category root = loadAsBean(new SimpleQuery(CatalogQuery.GET_ROOT_CATEGORY), Category.class);
    if (root == null) {
      summary.getErrors().add(
          new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, Messages
              .getString("service.data.csv.CategoryImportDataSource.0"))));
    }

    // カテゴリ階層最大値オーバーチェック
    if (StringUtil.hasValue(category.getParentCategoryCode())) {
      Category parentCategory = loadAsBean(new SimpleQuery(SqlUtil.getSelectAllQuery(Category.class) + " WHERE CATEGORY_CODE = ? ",
          category.getParentCategoryCode()), Category.class);
      if (parentCategory != null && (parentCategory.getDepth() >= (config.getCategoryMaxDepth() - 1))) {
        summary.getErrors().add(
            new ValidationResult(null, null, Message.get(CsvMessage.CATEGORY_MAX_DEPTH_OVER, String.valueOf(config
                .getCategoryMaxDepth()))));
      }
    }

    if (summary.hasError()) {
      succeedFlg = false;
    }

    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    Category root = loadAsBean(new SimpleQuery(CatalogQuery.GET_ROOT_CATEGORY), Category.class);
    String dummyPath = root.getPath();
    Long dummyDepth = -1L;
    category.setPath(dummyPath);
    category.setDepth(dummyDepth);
    category.setCommodityCountPc(0L);
    category.setCommodityCountMobile(0L);

    try {
      PreparedStatement pstmt = null;

      List<Object> params = new ArrayList<Object>();
      if (exists(selectStatement, category.getCategoryCode())) {
        params.add(category.getCategoryNamePc());
        params.add(category.getCategoryNameMobile());
        params.add(category.getParentCategoryCode());
        params.add(category.getDisplayOrder());

        params.add(ServiceLoginInfo.getInstance().getRecordingFormat());
        params.add(DateUtil.getSysdate());

        params.add(category.getCategoryCode());

        pstmt = updateStatement;
        logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
      } else {
        params.add(category.getCategoryCode());
        params.add(category.getCategoryNamePc());
        params.add(category.getCategoryNameMobile());
        params.add(category.getParentCategoryCode());
        params.add(category.getPath());
        params.add(category.getDepth());
        params.add(category.getDisplayOrder());
        params.add(category.getCommodityCountPc());
        params.add(category.getCommodityCountMobile());

        params.add(ServiceLoginInfo.getInstance().getRecordingFormat());
        params.add(DateUtil.getSysdate());
        params.add(ServiceLoginInfo.getInstance().getRecordingFormat());
        params.add(DateUtil.getSysdate());

        pstmt = insertStatement;
        logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
      }

      DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

      int updCount = pstmt.executeUpdate();
      if (updCount != 1) {
        succeedFlg = false;
        throw new CsvImportException();
      }

      // カテゴリパス・階層の計算
      UpdateCategoryPathProcedure delegate = new UpdateCategoryPathProcedure(category.getCategoryCode(), config
          .getCategoryMaxDepth(), ServiceLoginInfo.getInstance().getRecordingFormat());
      executeProcedure(delegate);

      if (delegate.getResult() != UpdateCategoryPathProcedure.SUCCESS) {
        ValidationSummary summary = new ValidationSummary();
        summary.getErrors().add(
            new ValidationResult(null, null, MessageFormat.format(
                Messages.getString("service.data.csv.CategoryImportDataSource.1"),
                category.getCategoryCode(), category.getParentCategoryCode())));
        succeedFlg = false;
        throw new CsvImportException(summary);

      }

    } catch (SQLException e) {
      succeedFlg = false;
      throw new CsvImportException(e);
    } catch (CsvImportException e) {
      succeedFlg = false;
      throw e;
    } catch (RuntimeException e) {
      succeedFlg = false;
      throw new CsvImportException(e);
    }

  }

  @Override
  public TransactionMode afterImport() {

    if (succeedFlg) {
      // カテゴリツリー再構築
      Category root = loadAsBean(new SimpleQuery(CatalogQuery.GET_ROOT_CATEGORY), Category.class);
      UpdateCategoryPathProcedure delegate = new UpdateCategoryPathProcedure(root.getCategoryCode(), config.getCategoryMaxDepth(),
          ServiceLoginInfo.getInstance().getRecordingFormat());
      executeProcedure(delegate);

      if (delegate.getResult() != UpdateCategoryPathProcedure.SUCCESS) {
        setMessage(Message.get(CsvMessage.REBUILDE_CATEGORY_TREE_FAILED));
        return TransactionMode.ROLLBACK_FORCE;
      }
    }
    // 10.1.2 10070 修正 ここから
    // return TransactionMode.COMMIT_FORCE;
    return TransactionMode.DEPEND_ON_RESULT;
    // 10.1.2 10070 修正 ここまで
  }

  @Override
  public CommitMode getCommitMode() {
    return CommitMode.BULK_COMMIT;
  }

  // 10.1.1 10019 追加 ここから
  private void checkMinusNumber(ValidationSummary summary) {
    if (NumUtil.isNegative(category.getDepth())) {
      summary.getErrors().add(new ValidationResult(null, null,
          Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages.getString("service.data.csv.CategoryImportDataSource.3"))));
    }
    if (NumUtil.isNegative(category.getDisplayOrder())) {
      summary.getErrors().add(new ValidationResult(null, null,
          Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages.getString("service.data.csv.CategoryImportDataSource.4"))));
    }
    if (NumUtil.isNegative(category.getCommodityCountPc())) {
      summary.getErrors().add(new ValidationResult(null, null,
          Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages.getString("service.data.csv.CategoryImportDataSource.5"))));
    }
    if (NumUtil.isNegative(category.getCommodityCountMobile())) {
      summary.getErrors().add(new ValidationResult(null, null,
          Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages.getString("service.data.csv.CategoryImportDataSource.6"))));
    }
  }
  // 10.1.1 10019 追加 ここまで

}
