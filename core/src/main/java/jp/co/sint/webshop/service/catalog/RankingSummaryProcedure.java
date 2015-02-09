package jp.co.sint.webshop.service.catalog;

import java.sql.Types;

import jp.co.sint.webshop.data.StoredProcedure;
import jp.co.sint.webshop.data.StoredProcedureParameter;

public class RankingSummaryProcedure implements StoredProcedure {

  private int month;

  private String updatedUser;

  private int mode;

  private Integer result;

  public RankingSummaryProcedure() {
  }

  public RankingSummaryProcedure(int month, int mode, String updatedUser) {
    setMonth(month);
    setMode(mode);
    setUpdatedUser(updatedUser);
  }

  public boolean commitOnSuccess() {
    return true;
  }

  public StoredProcedureParameter<?>[] getParameters() {
    StoredProcedureParameter<?>[] params = new StoredProcedureParameter[4];

    StoredProcedureParameter<Integer> param0 = StoredProcedureParameter.createInput(Integer.valueOf(getMonth()), Types.INTEGER);
    params[0] = param0;

    StoredProcedureParameter<Integer> param1 = StoredProcedureParameter.createInput(Integer.valueOf(getMode()), Types.INTEGER);
    params[1] = param1;

    StoredProcedureParameter<String> param2 = StoredProcedureParameter.createInput(getUpdatedUser(), Types.VARCHAR);
    params[2] = param2;

    StoredProcedureParameter<Integer> param3 = StoredProcedureParameter.createOutput("r_result", Types.INTEGER);
    params[3] = param3;

    return params;
  }

  public String getProcedureString() {
	   return "{CALL RANKING_SUMMARY_PROC(?,?,?,?)}";
  }

  /**
   * monthを取得します。
   * 
   * @return month
   */
  public int getMonth() {
    return month;
  }

  /**
   * monthを設定します。
   * 
   * @param month
   *          month
   */
  public void setMonth(int month) {
    this.month = month;
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

  /**
   * modeを取得します。
   * 
   * @return mode
   */
  public int getMode() {
    return mode;
  }

  /**
   * modeを設定します。
   * 
   * @param mode
   *          mode
   */
  public void setMode(int mode) {
    this.mode = mode;
  }

}
