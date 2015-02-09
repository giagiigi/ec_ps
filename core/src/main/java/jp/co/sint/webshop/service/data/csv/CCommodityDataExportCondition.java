package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class CCommodityDataExportCondition extends CsvConditionImpl<CCommodityDataCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private String skuCode;

  private String commodityCode;

  private List<CsvColumn> columns = new ArrayList<CsvColumn>();

  private String sqlString;
  
  private String combineType;

  private boolean headerOnly;

  /**
   * @return the skuCode
   */
  public String getSkuCode() {
    return skuCode;
  }

  /**
   * @param skuCode
   *          the skuCode to set
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  public String getCommodityCode() {
    return commodityCode;
  }

  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @return the columns
   */
  public List<CsvColumn> getColumns() {
    return columns;
  }

  /**
   * @param columns
   *          the columns to set
   */
  public void setColumns(List<CsvColumn> columns) {
    this.columns = columns;
  }

  /**
   * @return the sqlString
   */
  public String getSqlString() {
    return sqlString;
  }

  /**
   * @param sqlString
   *          the sqlString to set
   */
  public void setSqlString(String sqlString) {
    this.sqlString = sqlString;
  }

  /**
   * @return the headerOnly
   */
  public boolean isHeaderOnly() {
    return headerOnly;
  }

  /**
   * @param headerOnly
   *          the headerOnly to set
   */
  public void setHeaderOnly(boolean headerOnly) {
    this.headerOnly = headerOnly;
  }

  
  /**
   * @return the combineType
   */
  public String getCombineType() {
    return combineType;
  }

  
  /**
   * @param combineType the combineType to set
   */
  public void setCombineType(String combineType) {
    this.combineType = combineType;
  }

}
