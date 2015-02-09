package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class CCommodityHeaderExportCondition extends CsvConditionImpl<CCommodityHeaderCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private String commodityCode;

  private String skuCode;

  private List<CsvColumn> columns = new ArrayList<CsvColumn>();

  private String sqlString;
  
  private String combineType;

  private boolean headerOnly;

  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
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

  public String getSkuCode() {
    return skuCode;
  }

  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
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
