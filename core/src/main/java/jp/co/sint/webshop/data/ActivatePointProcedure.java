package jp.co.sint.webshop.data;

import java.sql.CallableStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.service.customer.CustomerConstant;

/**
 * カテゴリ集計プロシージャ起動用クラス
 * 
 * @author System Integrator Corp.
 */
public class ActivatePointProcedure implements ProcedureDelegate {

  private String orderNo;

  private String updatedUser;

  private int result;

  public ActivatePointProcedure() {
  }

  public ActivatePointProcedure(String orderNo, String updatedUser) {
    setOrderNo(orderNo);
    setUpdatedUser(updatedUser);
  }

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  public void execute(CallableStatement statement) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());

    statement.setString(1, getOrderNo());
    statement.setString(2, getUpdatedUser());
    //postgres start
    setResult(DatabaseUtil.getResultSet(statement, 3));
    //postgres end

    if (getResult() == 0) {
      logger.info("result:success");
    } else if (getResult() == CustomerConstant.POINT_OVERFLOW_CODE) {
      logger.error("result:PointOverflow");
    } else {
      logger.error("result:failure");
      throw new SQLException(this.getClass().getSimpleName() + ": Failed.");
    }

  }

  public String getStatement() {
    return "{CALL ACTIVATE_POINT_PROC(?,?,?)}";
  }

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
   * resultを取得します。
   * 
   * @return result
   */
  public int getResult() {
    return result;
  }

  /**
   * resultを設定します。
   * 
   * @param result
   *          result
   */
  public void setResult(int result) {
    this.result = result;
  }

  /**
   * updatedUserを取得します。
   * 
   * @return updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * updatedUserを設定します。
   * 
   * @param updatedUser
   *          updatedUser
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

}
