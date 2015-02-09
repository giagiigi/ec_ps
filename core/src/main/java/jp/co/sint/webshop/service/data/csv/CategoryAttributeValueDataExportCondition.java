package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class CategoryAttributeValueDataExportCondition extends CsvConditionImpl<CategoryAttributeValueDataCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private String commodityCode;

  private String skuCode;

  private List<CsvColumn> columns = new ArrayList<CsvColumn>();

  private String sqlString;

  private boolean headerOnly;

  public String getCommodityCode() {
    return commodityCode;
  }

  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  public List<CsvColumn> getColumns() {
    return columns;
  }

  public void setColumns(List<CsvColumn> columns) {
    this.columns = columns;
  }

  public String getSqlString() {
    return sqlString;
  }

  public void setSqlString(String sqlString) {
    this.sqlString = sqlString;
  }

  public boolean isHeaderOnly() {
    return headerOnly;
  }

  public void setHeaderOnly(boolean headerOnly) {
    this.headerOnly = headerOnly;
  }

  public String getSkuCode() {
    return skuCode;
  }

  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

}
