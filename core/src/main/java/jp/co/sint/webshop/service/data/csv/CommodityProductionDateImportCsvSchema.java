package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;


/**
 * 商品生产日期
 * 
 * @author ks
 *
 */
public class CommodityProductionDateImportCsvSchema extends CsvSchemaImpl {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public CommodityProductionDateImportCsvSchema() {
    getColumns().add(new CsvColumnImpl("sku_code",
        Messages.getCsvKey("service.data.csv.CommodityProductionDateImportCsvSchema.0"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("earlist_date",
        Messages.getCsvKey("service.data.csv.CommodityProductionDateImportCsvSchema.1"), CsvDataType.DATE)); 
  }
  
  public String getExportConfigureId() {
    return "";
  }

  public String getImportConfigureId() {
    return "CommodityProductionDateImportDataSource";
  }

  public String getTargetTableName() {
    return "";
  }

}

