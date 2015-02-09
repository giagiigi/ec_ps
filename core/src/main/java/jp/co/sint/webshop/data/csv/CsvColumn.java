package jp.co.sint.webshop.data.csv;

import java.io.Serializable;

/**
 * CSV列定義を表すインターフェイスです。
 * 
 * @author System Integrator Corp.
 */
public interface CsvColumn extends Serializable {

  /**
   * 列の物理名を返します。
   * 
   * @return 列の物理名
   */
  String getPhysicalName();

  /**
   * 列の論理名を返します。
   * 
   * @return 列の論理名
   */
  String getLogicalName();

  /**
   * データ型を返します。
   * 
   * @return データ型
   */
  CsvDataType getDataType();

  /**
   * 自動生成される列かどうかを返します。
   * 
   * @return 自動生成される列であればtrue
   */
  boolean isGenerative();

  /**
   * 取込時に無視される列かどうかを返します。
   * 
   * @return 取込時に無視される列であればtrue
   */
  boolean isExcluded();
  
  /**
   * 主キー列かどうかを返します。
   * 
   * @return 主キー列であればtrue
   */
  boolean isPrimaryKey();

  /**
   * 自動生成に使うシーケンスの名前を返します。
   * 
   * @return シーケンスの名前
   */
  String getSequenceName();

}
