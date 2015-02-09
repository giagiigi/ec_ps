package jp.co.sint.webshop.data;

import java.sql.Types;

public final class StoredProcedureParameter<T> {

  private T value;

  private boolean output;

  private int sqlType = Types.NULL;

  private String name;

  private StoredProcedureParameter() {

  }

  public static <T>StoredProcedureParameter<T> createInput(T value, int sqlType) {
    StoredProcedureParameter<T> t = new StoredProcedureParameter<T>();
    t.setValue(value);
    t.setSqlType(sqlType);
    t.setOutput(false);
    return t;
  }

  public static <T>StoredProcedureParameter<T> createOutput(String name, int sqlType) {
    StoredProcedureParameter<T> t = new StoredProcedureParameter<T>();
    t.setName(name);
    t.setSqlType(sqlType);
    t.setOutput(true);
    return t;
  }

  public boolean isOutput() {
    return output;
  }

  public String getName() {
    return name;
  }

  public int getSqlType() {
    return sqlType;
  }

  public T getValue() {
    return value;
  }

  public void setOutput(boolean isOutput) {
    this.output = isOutput;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSqlType(int sqlType) {
    this.sqlType = sqlType;
  }

  public void setValue(T value) {
    this.value = value;
  }

}
