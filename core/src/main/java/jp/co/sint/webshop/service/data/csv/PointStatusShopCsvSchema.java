package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;

public class PointStatusShopCsvSchema extends CsvSchemaImpl {

  public PointStatusShopCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("SHOP_CODE", "ショップコード", CsvDataType.STRING));
    columns.add(new CsvColumnImpl("SHOP_NAME", "ショップ名", CsvDataType.STRING));
    // 10.1.3 10135 修正 ここから
    // columns.add(new CsvColumnImpl("INEFFECTIVE_POINT", "無効ポイント", CsvDataType.STRING));
    // columns.add(new CsvColumnImpl("TEMPORARY_POINT", "仮発行ポイント", CsvDataType.STRING));
    // columns.add(new CsvColumnImpl("REST_POINT", "有効ポイント", CsvDataType.STRING));
    columns.add(new CsvColumnImpl("TEMPORARY_POINT", "仮発行ポイント", CsvDataType.STRING));
    columns.add(new CsvColumnImpl("REST_POINT", "有効ポイント", CsvDataType.STRING));
    columns.add(new CsvColumnImpl("INEFFECTIVE_POINT", "無効ポイント", CsvDataType.STRING));
    // 10.1.3 10135 修正 ここまで
    setColumns(columns);
  }

  /** serial verision UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "PointStatusShopExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
