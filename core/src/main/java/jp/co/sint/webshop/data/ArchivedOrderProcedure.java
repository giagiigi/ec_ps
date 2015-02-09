package jp.co.sint.webshop.data;

import java.sql.CallableStatement;
import java.sql.SQLException;

public class ArchivedOrderProcedure implements ProcedureDelegate {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  public ArchivedOrderProcedure(String orderNo) {
    setOrderNo(orderNo);
  }

  private String orderNo;

  private int result;

  /**
   * orderNoを取得します。
   * 
   * @return orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  /**
   * orderNoを設定します。
   * 
   * @param orderNo
   *          orderNo
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  /**
   * resultを返します。
   * 
   * @return the result
   */
  public int getResult() {
    return result;
  }

  /**
   * resultを設定します。
   * 
   * @param result
   *          設定する result
   */
  public void setResult(int result) {
    this.result = result;
  }

  public void execute(CallableStatement statement) throws SQLException {
    statement.setString(1, this.getOrderNo());
    //postgres start
//    statement.registerOutParameter(2, Types.INTEGER);
//    statement.execute();
//    setResult(statement.getInt(2));
    setResult(DatabaseUtil.getResultSet(statement, 2));
    //postgres end
  }

  public String getStatement() {
    return "{CALL ARCHIVED_ORDER_PROC(?,?)}";
  }

}
