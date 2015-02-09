package jp.co.sint.webshop.service.data;

import jp.co.sint.webshop.data.csv.CsvFilter;
import jp.co.sint.webshop.data.csv.CsvSchema;

public interface CsvImportCondition<S extends CsvSchema> extends CsvCondition<S> {

  CsvFilter getFilter();

}
