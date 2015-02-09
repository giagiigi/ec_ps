package jp.co.sint.webshop.data;

import java.sql.CallableStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class CouponCancelIssueProcedure implements ProcedureDelegate {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  public CouponCancelIssueProcedure(String orderNo,String updateUser, String customerCode) {
	  setOrderNo(orderNo);
	  setUdpateUser(updateUser);
	  setCustomerCode(customerCode);
  }

  private String orderNo;

  private String udpateUser;
  
  private String customerCode;
  
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
    statement.setString(2, customerCode);
    statement.setString(3, udpateUser);
    setResult(DatabaseUtil.getResultSet(statement, 4));

    if (getResult() == 0) {
      logger.info("result:success");
    } else {
      logger.error("result:failure");
      throw new SQLException(this.getClass().getSimpleName() + ": Failed.");
    }

  }

  public String getStatement() {
    return "{CALL cancel_issue_coupon_from_order_cancel_func(?,?,?,?)}";
  }
  
/**
 * @return the orderNo
 */
public String getOrderNo() {
	return orderNo;
}

/**
 * @param orderNo the orderNo to set
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
 * @param udpateUser the udpateUser to set
 */
public void setUdpateUser(String udpateUser) {
	this.udpateUser = udpateUser;
}

/**
 * @return the customerCode
 */
public String getCustomerCode() {
  return customerCode;
}

/**
 * @param customerCode the customerCode to set
 */
public void setCustomerCode(String customerCode) {
  this.customerCode = customerCode;
}

}
