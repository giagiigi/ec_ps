package jp.co.sint.webshop.data.csv;

import java.util.List;

import jp.co.sint.webshop.service.data.CsvExportCondition;

/**
 * CSV出力データソースです。
 * 
 * @author System Integrator Corp.
 */
public interface ExportDataSource<S extends CsvSchema, E extends CsvExportCondition<S>> extends CsvDataSource<S, E> {

  void beforeExport();
  
  boolean headerOnly();

  CsvData getData();

  void onFetchData(List<String> csvLine);

  void afterExport();

  // 2012-01-06 yyq add start desc:事务提交和回滚
  void commit();

  void rollback();
  // 2012-01-06 yyq add end desc:事务提交和回滚
}
