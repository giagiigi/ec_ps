package jp.co.sint.webshop.service.data.csv;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.StockManager;
import jp.co.sint.webshop.data.StockUnit;
import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
import jp.co.sint.webshop.data.domain.StockIOType;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.data.dto.StockIODetail;
import jp.co.sint.webshop.data.hibernate.StockManagerImpl;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.data.CsvSchemaType;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

public class StockIODetailImportDataSource extends SqlImportDataSource<StockIODetailCsvSchema, StockIODetailImportCondition> {

  private StockIODetail stockIODetail = null;

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      stockIODetail = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), StockIODetail.class);
      stockIODetail.setStockIODate(DateUtil.getSysdate());
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }

    List<String> fieldList = new ArrayList<String>();
    for (CsvColumn col : getSchema().getColumns()) {
      fieldList.add(StringUtil.toCamelFormat(col.getPhysicalName()));
    }

    // 単項目バリデーションチェック
    summary.getErrors()
        .addAll(BeanValidator.partialValidate(stockIODetail, ArrayUtil.toArray(fieldList, String.class)).getErrors());
    // 10.1.1 10019 追加 ここから
    if (NumUtil.isNegative(stockIODetail.getStockIOQuantity())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, 
          Messages.getString("service.data.csv.StockIODetailImportDataSource.2"))));
    }
    // 10.1.1 10019 追加 ここまで
    if (summary.hasError()) {
      return summary;
    }

    // ショップコード相違チェック
    if (StringUtil.hasValue(getCondition().getShopCode())) {
      if (!stockIODetail.getShopCode().equals(getCondition().getShopCode())) {
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.VALIDATE_SHOPCODE)));
        return summary;
      }
    }

    // ショップデータ存在チェック
    SimpleQuery shopCountQuery = new SimpleQuery("SELECT COUNT(*) FROM SHOP WHERE SHOP_CODE = ? ");
    shopCountQuery.setParameters(stockIODetail.getShopCode());
    Long shopCount = Long.valueOf(executeScalar(shopCountQuery).toString());
    if (shopCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, 
          Messages.getString("service.data.csv.StockIODetailImportDataSource.0"))));
    }

    // 在庫の存在チェック
    CsvSchema schema = DIContainer.getCsvSchema(CsvSchemaType.STOCK);
    Query stockCountQuery = new SimpleQuery(CsvUtil.buildCheckExistsQuery(schema), stockIODetail.getShopCode(), stockIODetail
        .getSkuCode());
    Long stockCount = Long.valueOf(executeScalar(stockCountQuery).toString());
    if (stockCount == 0) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, 
          Messages.getString("service.data.csv.StockIODetailImportDataSource.1"))));
    }

    Query stockGetQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(Stock.class) + " WHERE SHOP_CODE = ? AND SKU_CODE = ?",
        stockIODetail.getShopCode(), stockIODetail.getSkuCode());
    Stock stock = loadAsBean(stockGetQuery, Stock.class);
    if (stock == null) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST, 
          Messages.getString("service.data.csv.StockIODetailImportDataSource.1"))));
    } else {

      // 入出庫数の妥当性チェック
      boolean isValid = true;
      // 10.1.4 10159 修正 ここから
      // String errorMessage = "";
      String errorMessage = Message.get(CsvMessage.STOCK_AMOUNT_OVERFLOW);
      // 10.1.4 10159 修正 ここまで
      StockIOType stockIOType = StockIOType.fromValue(stockIODetail.getStockIOType());
      switch (stockIOType) {
        case ENTRY:
          isValid &= stock.getStockQuantity() + stockIODetail.getStockIOQuantity() <= 99999999L;
          // 10.1.4 10159 削除 ここから
//          errorMessage = Message.get(CsvMessage.STOCK_AMOUNT_OVERFLOW, NumUtil.toString(99999999L - stock.getStockQuantity()));
          // 10.1.4 10159 削除 ここまで
          break;
        case DELIVERY:
          // 10.1.4 10159 修正 ここから
//          Long availableQuantity = stock.getStockQuantity() - (stock.getAllocatedQuantity() + stock.getReservedQuantity());
//          isValid &= stockIODetail.getStockIOQuantity() <= availableQuantity;
//          errorMessage = Message.get(CsvMessage.STOCK_DELIVER_OVERFLOW, NumUtil.toString(availableQuantity));
          isValid &= stock.getStockQuantity() - stockIODetail.getStockIOQuantity() >= -99999999L;
          // 10.1.4 10159 修正 ここまで 
          break;
        default:
          isValid = false;
          break;
      }

      if (!isValid) {
        summary.getErrors().add(new ValidationResult(null, null, errorMessage));
      }

    }

    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {

    // 在庫の更新処理
    // 入出庫日はファイルから取り込まず、システム日付を設定
    StockManager stockManager = new StockManagerImpl(getConnection());
    StockUnit stockUnit = new StockUnit();
    stockUnit.setShopCode(stockIODetail.getShopCode());
    stockUnit.setSkuCode(stockIODetail.getSkuCode());
    stockUnit.setQuantity(stockIODetail.getStockIOQuantity().intValue());
    stockUnit.setStockIODate(DateUtil.getSysdate());
    stockUnit.setMemo(stockIODetail.getMemo());
    stockUnit.setLoginInfo(getCondition().getLoginInfo());
    StockIOType stockIOType = StockIOType.fromValue(stockIODetail.getStockIOType());

    boolean result = false;
    switch (stockIOType) {
      case ENTRY:
        result = stockManager.entry(stockUnit);
        break;
      case DELIVERY:
        result = stockManager.deliver(stockUnit);
        break;
      default:
        result = false;
        break;
    }

    if (!result) {
      throw new CsvImportException(Message.get(CsvMessage.IMPORT_STOCK_IO_FAILED));
    }
  }

}
