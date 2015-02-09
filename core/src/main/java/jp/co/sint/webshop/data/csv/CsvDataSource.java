package jp.co.sint.webshop.data.csv;

import jp.co.sint.webshop.data.Disposable;
import jp.co.sint.webshop.service.data.CsvCondition;

/**
 * CSV入出力に使用するデータソースの共通既定インターフェイスです。
 * 
 * @author System Integrator Corp.
 */
public interface CsvDataSource<S extends CsvSchema, C extends CsvCondition<S>> extends Disposable {

  void init();

  void setSchema(S schema);
  
  S getSchema();

  void setCondition(C condition);
  
  C getCondition();
  
}
