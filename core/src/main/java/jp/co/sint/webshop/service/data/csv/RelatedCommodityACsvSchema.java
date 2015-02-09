package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.RelatedCommodityA;
import jp.co.sint.webshop.text.Messages;

public class RelatedCommodityACsvSchema extends CsvSchemaImpl {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public RelatedCommodityACsvSchema() {
    getColumns().add(new CsvColumnImpl("shop_code",
        Messages.getCsvKey("service.data.csv.RelatedCommodityACsvSchema.0"),
        CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("commodity_code",
        Messages.getCsvKey("service.data.csv.RelatedCommodityACsvSchema.1"),
        CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("link_commodity_code",
        Messages.getCsvKey("service.data.csv.RelatedCommodityACsvSchema.2"),
        CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("display_order",
        Messages.getCsvKey("service.data.csv.RelatedCommodityACsvSchema.3"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("orm_rowid",
        Messages.getCsvKey("service.data.csv.RelatedCommodityACsvSchema.4"), CsvDataType.NUMBER, false, true, false,
        "RELATED_COMMODITY_A_SEQ"));
    getColumns().add(new CsvColumnImpl("created_user",
        Messages.getCsvKey("service.data.csv.RelatedCommodityACsvSchema.5"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("created_datetime",
        Messages.getCsvKey("service.data.csv.RelatedCommodityACsvSchema.6"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("updated_user",
        Messages.getCsvKey("service.data.csv.RelatedCommodityACsvSchema.7"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("updated_datetime",
        Messages.getCsvKey("service.data.csv.RelatedCommodityACsvSchema.8"), CsvDataType.DATETIME));
  }

  public String getExportConfigureId() {
    return "RelatedCommodityAExportDataSource";
  }

  public String getImportConfigureId() {
    return "RelatedCommodityAImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(RelatedCommodityA.class);
  }

}
