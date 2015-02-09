package jp.co.sint.webshop.data;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.MessageFormat;

import org.apache.log4j.Logger;

public class ReviewSummaryUpdateProcedure implements ProcedureDelegate {

  private String shopCode;

  private String commodityCode;

  private String updatedUser;

  private int result = -1;

  public ReviewSummaryUpdateProcedure() {
  }

  public ReviewSummaryUpdateProcedure(String shopCode, String commodityCode, String updatedUser) {
    setShopCode(shopCode);
    setCommodityCode(commodityCode);
    setUpdatedUser(updatedUser);
  }

  public void execute(CallableStatement statement) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());
    statement.setString(1, getShopCode());
    statement.setString(2, getCommodityCode());
    statement.setString(3, getUpdatedUser());
    logger.debug(MessageFormat.format("shop={0}, commodity={1}, user={2}", getShopCode(), getCommodityCode(), getUpdatedUser()));
    //postgres start
//    statement.registerOutParameter(4, Types.INTEGER);
//    statement.execute();
    setResult(DatabaseUtil.getResultSet(statement, 4));
    //postgres end
    if (getResult() != 0) {
      logger.info("failed");
      throw new SQLException("UPDATE_REVIEW_SUMMARY_PROC Failed");
    } else {
      logger.info("succeed");
    }
  }

  public String getStatement() {
    return "{CALL UPDATE_REVIEW_SUMMARY_PROC(?, ?, ?, ?)}";
  }

  /**
   * shopCodeを返します。
   * 
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * commodityCodeを返します。
   * 
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          設定する commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * updatedUserを返します。
   * 
   * @return the updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * updatedUserを設定します。
   * 
   * @param updatedUser
   *          設定する updatedUser
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
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

}
