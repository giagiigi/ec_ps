package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class CampaignResearchCsvSchema extends CsvSchemaImpl {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public CampaignResearchCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("COMMODITY_CODE",
        Messages.getCsvKey("service.data.csv.CampaignResearchCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COMMODITY_NAME",
        Messages.getCsvKey("service.data.csv.CampaignResearchCsvSchema.1"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COMMODITY_SALES_AMOUNT",
        Messages.getCsvKey("service.data.csv.CampaignResearchCsvSchema.2"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("COMMODITY_ORDER_AMOUNT",
        Messages.getCsvKey("service.data.csv.CampaignResearchCsvSchema.3"), CsvDataType.NUMBER));
    setColumns(columns);
  }

  public String getExportConfigureId() {
    return "CampaignResearchExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
