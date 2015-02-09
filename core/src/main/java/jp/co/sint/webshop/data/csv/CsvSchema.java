package jp.co.sint.webshop.data.csv;

import java.io.Serializable;
import java.util.List;

import jp.co.sint.webshop.validation.ValidationSummary;

/**
 * CSVのファイル定義を表すインターフェイスです。
 * 
 * @author System Integrator Corp.
 */
public interface CsvSchema extends Serializable {

  /**
   * CSVの列定義リストを返します。
   * 
   * @return CSV列定義リスト
   */
  List<CsvColumn> getColumns();

  /**
   * ファイル名を返します。
   * 
   * @return ファイル名
   */
  String getFileName();

  /**
   * CsvのIDを返します。
   * 
   * @return CsvのID
   */
  String getCsvId();

  /**
   * インポート先のテーブル名を返します。
   * 
   * @return テーブル名
   */
  String getTargetTableName();

  String getImportConfigureId();

  String getExportConfigureId();

  ValidationSummary checkColumns(List<String> csvLine);

}
