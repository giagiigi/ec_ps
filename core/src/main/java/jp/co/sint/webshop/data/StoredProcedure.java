package jp.co.sint.webshop.data;


public interface StoredProcedure {

  String getProcedureString();

  StoredProcedureParameter<?>[] getParameters();
  
  boolean commitOnSuccess();
}
