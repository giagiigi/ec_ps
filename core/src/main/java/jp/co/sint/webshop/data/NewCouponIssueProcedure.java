package jp.co.sint.webshop.data;

import java.sql.CallableStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class NewCouponIssueProcedure implements ProcedureDelegate {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  public NewCouponIssueProcedure(String orderNo, String updateUser, Long orderCount) {
    setOrderNo(orderNo);
    setUdpateUser(updateUser);
    setOrderCount(orderCount);
  }

  private String orderNo;

  private String udpateUser;

  private Long orderCount;

  private int result;

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
    Logger logger = Logger.getLogger(this.getClass());
    statement.setString(1, orderNo);
    statement.setString(2, udpateUser);
    statement.setLong(3, orderCount);

    setResult(DatabaseUtil.getResultSet(statement, 4));

    if (getResult() == 0) {
      logger.info("result:success");
    } else {
      logger.error("result:failure");
      throw new SQLException(this.getClass().getSimpleName() + ": Failed.");
    }

  }

  public String getStatement() {
    return "{CALL CREATE_NEW_COUPON_FROM_ORDER_NEW_FUNC(?,?,?,?)}";
  }

  /**
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  /**
   * @param orderNo
   *          the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  /**
   * @return the udpateUser
   */
  public String getUdpateUser() {
    return udpateUser;
  }

  /**
   * @param udpateUser
   *          the udpateUser to set
   */
  public void setUdpateUser(String udpateUser) {
    this.udpateUser = udpateUser;
  }

  /**
   * @return the orderCount
   */
  public Long getOrderCount() {
    return orderCount;
  }

  /**
   * @param orderCount
   *          the orderCount to set
   */
  public void setOrderCount(Long orderCount) {
    this.orderCount = orderCount;
  }

}
