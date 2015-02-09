package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.CampaignCommodity;
import jp.co.sint.webshop.text.Messages;

public class CampaignCommodityCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public CampaignCommodityCsvSchema() {
    getColumns().add(new CsvColumnImpl("shop_code",
        Messages.getCsvKey("service.data.csv.CampaignCommodityCsvSchema.0"), CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("campaign_code",
        Messages.getCsvKey("service.data.csv.CampaignCommodityCsvSchema.1"), CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("commodity_code",
        Messages.getCsvKey("service.data.csv.CampaignCommodityCsvSchema.2"), CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("orm_rowid",
        Messages.getCsvKey("service.data.csv.CampaignCommodityCsvSchema.3"), CsvDataType.NUMBER, false, true, false,
        "CAMPAIGN_COMMODITY_SEQ"));
    getColumns().add(new CsvColumnImpl("created_user",
        Messages.getCsvKey("service.data.csv.CampaignCommodityCsvSchema.4"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("created_datetime",
        Messages.getCsvKey("service.data.csv.CampaignCommodityCsvSchema.5"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("updated_user",
        Messages.getCsvKey("service.data.csv.CampaignCommodityCsvSchema.6"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("updated_datetime",
        Messages.getCsvKey("service.data.csv.CampaignCommodityCsvSchema.7"), CsvDataType.DATETIME));
  }

  public String getExportConfigureId() {
    return "CampaignCommodityExportDataSource";
  }

  public String getImportConfigureId() {
    return "CampaignCommodityImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(CampaignCommodity.class);
  }

}
