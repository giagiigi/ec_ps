package jp.co.sint.webshop.service.communication;

import java.sql.Types;

import jp.co.sint.webshop.data.StoredProcedure;
import jp.co.sint.webshop.data.StoredProcedureParameter;

public class ReviewSummaryProcedure implements StoredProcedure {

  private String updatedUser;

  private Integer result;

  public ReviewSummaryProcedure() {
  }

  public ReviewSummaryProcedure(String updatedUser) {
    setUpdatedUser(updatedUser);
  }

  public boolean commitOnSuccess() {
    return true;
  }

  public StoredProcedureParameter<?>[] getParameters() {
    StoredProcedureParameter<?>[] params = new StoredProcedureParameter[2];

    StoredProcedureParameter<String> param0 = StoredProcedureParameter.createInput(getUpdatedUser(), Types.VARCHAR);
    params[0] = param0;

    StoredProcedureParameter<Integer> param1 = StoredProcedureParameter.createOutput("r_result", Types.INTEGER);
    params[1] = param1;

    return params;
  }

  public String getProcedureString() {
	  return "{CALL REVIEW_SUMMARY_PROC(?,?)}";
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

}
