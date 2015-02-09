package jp.co.sint.webshop.data;

import java.sql.CallableStatement;
import java.sql.SQLException;

import jp.co.sint.webshop.utility.DIContainer;


public abstract class AbstractCallableStatement implements CallableStatement{
  public void setNumDefault(int parameterIndex, String x) throws SQLException {
    if (DIContainer.getWebshopConfig().isPostgreSQL()) {
      this.setString(parameterIndex, null);
    } else {
      this.setString(parameterIndex, x);
    }
  }
  
  public void setStringDefault(int parameterIndex, String x) throws SQLException {
    if (DIContainer.getWebshopConfig().isPostgreSQL()) {
      this.setString(parameterIndex, null);
    } else {
      this.setString(parameterIndex, x);
    }
  }
}
