package jp.co.sint.webshop.service.catalog;

import java.sql.Types;

import jp.co.sint.webshop.data.StoredProcedure;
import jp.co.sint.webshop.data.StoredProcedureParameter;

public class AutoRecommendSummaryProcedure implements StoredProcedure {

  private String month;

  private String updatedUser;

  private Integer result;

  public AutoRecommendSummaryProcedure() {
  }

  public AutoRecommendSummaryProcedure(String month, String updatedUser) {
    setMonth(month);
    setUpdatedUser(updatedUser);
  }

  public boolean commitOnSuccess() {
    return true;
  }

  public StoredProcedureParameter<?>[] getParameters() {
    StoredProcedureParameter<?>[] params = new StoredProcedureParameter[3];

    StoredProcedureParameter<String> param0 = StoredProcedureParameter.createInput(getMonth(), Types.INTEGER);
    params[0] = param0;

    StoredProcedureParameter<String> param1 = StoredProcedureParameter.createInput(getUpdatedUser(), Types.VARCHAR);
    params[1] = param1;

    StoredProcedureParameter<Integer> param2 = StoredProcedureParameter.createOutput("r_result", Types.INTEGER);
    params[2] = param2;

    return params;
  }

  public String getProcedureString() {
	  return "{CALL AUTO_RECOMMEND_SUMMARY_PROC(?,?,?)}";
  }

  /**
   * resultを取得します。
   * 
   * @return result
   */
  public Integer getResult() {
    return result;
  }

  /**
   * resultを設定します。
   * 
   * @param result
   *          result
   */
  public void setResult(Integer result) {
    this.result = result;
  }

  /**
   * @return month
   */
  public String getMonth() {
    return month;
  }

  /**
   * @param month
   *          設定する month
   */
  public void setMonth(String month) {
    this.month = month;
  }

  /**
   * @return updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * @param updatedUser
   *          設定する updatedUser
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }
}
