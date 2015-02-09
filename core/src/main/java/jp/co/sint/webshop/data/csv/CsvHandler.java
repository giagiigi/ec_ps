package jp.co.sint.webshop.data.csv;

import java.io.Reader;
import java.io.Writer;

import jp.co.sint.webshop.data.CsvResult;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvImportCondition;

/**
 * CSV入出力のインターフェイスです。
 * 
 * @author System Integrator Corp.
 */
public interface CsvHandler {

  /**
   * CSVを書き出します。
   * 
   * @param writer
   *          CSVを出力するWriter
   * @param dataSource
   *          出力元データソース
   */
  <S extends CsvSchema, E extends CsvExportCondition<S>>CsvResult exportData(Writer writer, ExportDataSource<S, E> dataSource);

  /**
   * CSVを取り込みます。
   * 
   * @param reader
   *          CSVを取込元のReader
   * @param dataSource
   *          更新先データソース
   */
  <S extends CsvSchema, I extends CsvImportCondition<S>>CsvResult importData(Reader reader, ImportDataSource<S, I> dataSource);
  // 2012-01-09 yyq add start desc:接口使用的csv导入共通
  <S extends CsvSchema, I extends CsvImportCondition<S>>CsvResult importDataIf(Reader reader, ImportDataSource<S, I> dataSource);
  // 2012-02-29 OS011 add start desc:接口使用的csv导入共通SKU重复
  <S extends CsvSchema, I extends CsvImportCondition<S>>CsvResult importDataIfSkuExist(Reader reader, ImportDataSource<S, I> dataSource);

}
