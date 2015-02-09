package jp.co.sint.webshop.data.csv.impl;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.text.Messages;

public class CsvColumnImpl implements CsvColumn {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  //modify by V10-CH 170 start
  // private String logicalName;
  private String logicalNameKey; 
  //modify by V10-CH 170 end

  private String physicalName;

  private CsvDataType dataType;

  private boolean excluded;

  private boolean generative;

  private boolean primaryKey;

  private String sequenceName;

  public CsvColumnImpl() {

  }

  public CsvColumnImpl(String physicalName, String logicalName, CsvDataType dataType) {
    setPhysicalName(physicalName);
    setLogicalNameKey(logicalName);
    setDataType(dataType);
  }

  public CsvColumnImpl(String physicalName, String logicalName, CsvDataType dataType, boolean excluded, boolean generative,
      boolean primaryKey, String sequenceName) {
    this(physicalName, logicalName, dataType);
    setExcluded(excluded);
    setGenerative(generative);
    setPrimaryKey(primaryKey);
    setSequenceName(sequenceName);
  }


  /**
   * logicalNameを返します。
   * 
   * @return the logicalName
   */
  public String getLogicalName() {
    return Messages.getCsvColumnName(this.getLogicalNameKey());
  }

  /**
   * logicalNameKeyを取得します。
   *
   * @return the logicalNameKey
   */
  public String getLogicalNameKey() {
    return logicalNameKey;
  }

  /**
   * logicalNameKeyを設定します。
   *
   * @param logicalNameKey 
   *          設定する logicalNameKey
   */
  public void setLogicalNameKey(String logicalNameKey) {
    this.logicalNameKey = logicalNameKey;
  }

  /**
   * physicalNameを返します。
   * 
   * @return the physicalName
   */
  public String getPhysicalName() {
    return physicalName;
  }

  /**
   * physicalNameを設定します。
   * 
   * @param physicalName
   *          設定する physicalName
   */
  public void setPhysicalName(String physicalName) {
    this.physicalName = physicalName;
  }

  /**
   * dataTypeを返します。
   * 
   * @return the dataType
   */
  public CsvDataType getDataType() {
    return dataType;
  }

  /**
   * dataTypeを設定します。
   * 
   * @param dataType
   *          設定する dataType
   */
  public void setDataType(CsvDataType dataType) {
    this.dataType = dataType;
  }

  /**
   * excludedを返します。
   * 
   * @return the excluded
   */
  public boolean isExcluded() {
    return excluded;
  }

  /**
   * excludedを設定します。
   * 
   * @param excluded
   *          設定する excluded
   */
  public void setExcluded(boolean excluded) {
    this.excluded = excluded;
  }

  /**
   * generativeを返します。
   * 
   * @return the generative
   */
  public boolean isGenerative() {
    return generative;
  }

  /**
   * generativeを設定します。
   * 
   * @param generative
   *          設定する generative
   */
  public void setGenerative(boolean generative) {
    this.generative = generative;
  }

  /**
   * sequenceNameを返します。
   * 
   * @return the sequenceName
   */
  public String getSequenceName() {
    return sequenceName;
  }

  /**
   * sequenceNameを設定します。
   * 
   * @param sequenceName
   *          設定する sequenceName
   */
  public void setSequenceName(String sequenceName) {
    this.sequenceName = sequenceName;
  }

  /**
   * primaryKeyを返します。
   * 
   * @return primaryKey
   */

  public boolean isPrimaryKey() {
    return primaryKey;
  }

  /**
   * primaryKeyを設定します。
   * 
   * @param primaryKey
   *          primaryKey
   */
  public void setPrimaryKey(boolean primaryKey) {
    this.primaryKey = primaryKey;
  }

}
