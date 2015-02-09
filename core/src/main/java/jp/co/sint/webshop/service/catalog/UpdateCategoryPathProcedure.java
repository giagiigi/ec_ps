package jp.co.sint.webshop.service.catalog;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.ProcedureDelegate;


/**
 * カテゴリパス更新プロシージャ起動用クラス
 * 
 * @author System Integrator Corp.
 */
public class UpdateCategoryPathProcedure implements ProcedureDelegate {
  
  public static final int SUCCESS = 0;
  
  public static final int OTHER_ERROR = 1;
  
  public static final int MAX_DEPTH_OVER_ERROR = 2;
  
  private String categoryCode;
  
  private int categoryMaxDepth;
  
  private String updatedUser;
  
  private int result;
  
  public UpdateCategoryPathProcedure(String categoryCode, int categoryMaxDepth, String updatedUser) {
    setCategoryCode(categoryCode);
    setCategoryMaxDepth(categoryMaxDepth);
    setUpdatedUser(updatedUser);
  }
  
  public void execute(CallableStatement statement) throws SQLException {
    statement.setString(1, getCategoryCode());
    statement.setInt(2, getCategoryMaxDepth());
    statement.setString(3, getUpdatedUser());
    //postgres start
//    statement.registerOutParameter(4, Types.INTEGER);
//    statement.execute();
//    setResult(statement.getInt(4));
    setResult(DatabaseUtil.getResultSet(statement, 4));
    //postgres end
  }

  public String getStatement() {
	  return "{CALL UPDATE_CATEGORY_PATH_PROC(?,?,?,?)}";
  }

  
  /**
   * categoryCodeを取得します。
   *
   * @return categoryCode
   */
  
  public String getCategoryCode() {
    return categoryCode;
  }

  
  /**
   * categoryMaxDepthを取得します。
   *
   * @return categoryMaxDepth
   */
  
  public int getCategoryMaxDepth() {
    return categoryMaxDepth;
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
   * updatedUserを取得します。
   *
   * @return updatedUser
   */
  
  public String getUpdatedUser() {
    return updatedUser;
  }

  
  /**
   * categoryCodeを設定します。
   *
   * @param categoryCode 
   *          categoryCode
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  
  /**
   * categoryMaxDepthを設定します。
   *
   * @param categoryMaxDepth 
   *          categoryMaxDepth
   */
  public void setCategoryMaxDepth(int categoryMaxDepth) {
    this.categoryMaxDepth = categoryMaxDepth;
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
   * updatedUserを設定します。
   *
   * @param updatedUser 
   *          updatedUser
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  //postgreSQL start
  public void execute(PreparedStatement  statement) throws SQLException {
	statement.setString(1, getCategoryCode());
	statement.setInt(2, getCategoryMaxDepth());
	statement.setString(3, getUpdatedUser());
	//statement.registerOutParameter(4, Types.INTEGER);
	statement.execute();
	//setResult(statement.getInt(4));
	ResultSet resultset = statement.getResultSet();
	resultset.next();
	setResult(resultset.getInt(1));
	resultset.close();
  }
  //postgreSQL end

}
