package jp.co.sint.webshop.data.csv;

import java.util.List;

import jp.co.sint.webshop.service.data.CsvImportCondition;
import jp.co.sint.webshop.validation.ValidationSummary;

/**
 * CSV取込データソースです。
 * 
 * @author System Integrator Corp.
 */
public interface ImportDataSource<S extends CsvSchema, I extends CsvImportCondition<S>> extends CsvDataSource<S, I> {

  boolean setSchema(List<String> csvLine);

  void beforeImport();
  
  ValidationSummary validate(List<String> csvLine);
  
  void update(List<String> csvLine);

  CommitMode getCommitMode();

  int getMaxErrorCount();
  
  TransactionMode afterImport();

  void commit();

  void rollback();
    
  String getMessage();

}
