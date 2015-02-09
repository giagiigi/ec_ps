package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.Holiday;
import jp.co.sint.webshop.text.Messages;

public class HolidayCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public HolidayCsvSchema() {
    getColumns().add(new CsvColumnImpl("holiday_id",
        Messages.getCsvKey("service.data.csv.HolidayCsvSchema.0"),
        CsvDataType.NUMBER, false, true, true, "HOLIDAY_ID_SEQ"));
    getColumns().add(new CsvColumnImpl("shop_code",
        Messages.getCsvKey("service.data.csv.HolidayCsvSchema.1"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("holiday",
        Messages.getCsvKey("service.data.csv.HolidayCsvSchema.2"),
        CsvDataType.DATE));
    getColumns().add(new CsvColumnImpl("orm_rowid",
        Messages.getCsvKey("service.data.csv.HolidayCsvSchema.3"),
        CsvDataType.NUMBER, false, true, false, "HOLIDAY_SEQ"));
    getColumns().add(new CsvColumnImpl("created_user",
        Messages.getCsvKey("service.data.csv.HolidayCsvSchema.4"),
        CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("created_datetime",
        Messages.getCsvKey("service.data.csv.HolidayCsvSchema.5"),
        CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("updated_user",
        Messages.getCsvKey("service.data.csv.HolidayCsvSchema.6"),
        CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("updated_datetime",
        Messages.getCsvKey("service.data.csv.HolidayCsvSchema.7"),
        CsvDataType.DATETIME));
  }

  public String getExportConfigureId() {
    return "HolidayExportDataSource";
  }

  public String getImportConfigureId() {
    return "HolidayImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(Holiday.class);
  }

}
