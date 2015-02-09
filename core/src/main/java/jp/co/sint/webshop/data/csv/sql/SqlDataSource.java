package jp.co.sint.webshop.data.csv.sql;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataSource;
import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.data.CsvCondition;
import jp.co.sint.webshop.utility.DIContainer;

public class SqlDataSource<S extends CsvSchema, C extends CsvCondition<S>> implements CsvDataSource<S, C> {

  private Connection connection;

  private S schema;

  private C condition;

  public final void init() {
    try {
      DataSource ds = DIContainer.getWebshopDataSource();
      connection = ds.getConnection();
      connection.setAutoCommit(false);
    } catch (SQLException e) {
      dispose();
      throw new DataAccessException(e);
    }
    this.initializeResources();
  }

  public final void dispose() {
    this.clearResources();
    DatabaseUtil.closeResource(connection);
  }

  protected void initializeResources() {
  }

  protected Connection getConnection() {
    return this.connection;
  }

  protected void clearResources() {

  }

  public S getSchema() {
    return this.schema;
  }

  public C getCondition() {
    return this.condition;
  }

  public void setCondition(C condition) {
    this.condition = condition;
  }

  public void setSchema(S schema) {
    this.schema = schema;
  }

}
