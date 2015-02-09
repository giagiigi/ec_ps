package jp.co.sint.webshop.data;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.dto.PointHistory;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;

public class PointUpdateProcedure implements ProcedureDelegate {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  public PointUpdateProcedure(PointHistory pointHistory) {
    setPointHistory(pointHistory);
  }

  private PointHistory pointHistory;

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

    statement.setLong(1, pointHistory.getPointHistoryId());
    statement.setString(2, pointHistory.getCustomerCode());
    statement.setLong(3, pointHistory.getPointIssueStatus());
    statement.setLong(4, pointHistory.getPointIssueType());
    statement.setString(5, pointHistory.getOrderNo());
    if (NumUtil.isNull(pointHistory.getReviewId())) {
      if (DIContainer.getWebshopConfig().isPostgreSQL()) {
        statement.setString(6, null);
      } else {
        statement.setString(6, "");
      }
    } else {
      statement.setString(6, Long.toString(pointHistory.getReviewId()));
    }
    statement.setString(7, pointHistory.getEnqueteCode());
    statement.setString(8, pointHistory.getDescription());
    //modified by zhanghaibin start 2010-05-18
    float a1 = pointHistory.getIssuedPoint().floatValue();
    float a2 = DIContainer.getWebshopConfig().getPointMultiple();
    //statement.setBigDecimal(9, pointHistory.getIssuedPoint());
    
    //int i = (int) (Math.floor(a1*ServiceLocator.getAmplificationRate())/ServiceLocator.getAmplificationRate()*a2);
    int i = (int) (Math.floor(a1)/a2);
    statement.setBigDecimal(9, BigDecimal.valueOf(i));
    //modified by zhanghaibin end   2010-05-18
    statement.setString(10, pointHistory.getShopCode());
    if (pointHistory.getPointUsedDatetime() == null) {
      if (DIContainer.getWebshopConfig().isPostgreSQL()) {
        statement.setString(11, null);
      } else {
        statement.setString(11, "");
      }
    } else {
      statement.setTimestamp(11, new java.sql.Timestamp(pointHistory.getPointUsedDatetime().getTime()));
    }
    statement.setString(12, pointHistory.getCreatedUser());  
    setResult(DatabaseUtil.getResultSet(statement, 13));

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
    return "{CALL POINT_UPDATE_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
  }

  /**
   * pointHistoryを取得します。
   * 
   * @return pointHistory
   */
  public PointHistory getPointHistory() {
    return pointHistory;
  }

  /**
   * pointHistoryを設定します。
   * 
   * @param pointHistory
   *          設定する pointHistory
   */
  public void setPointHistory(PointHistory pointHistory) {
    this.pointHistory = pointHistory;
  }
  
}
