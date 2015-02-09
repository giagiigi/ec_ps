package jp.co.sint.webshop.service.customer;

import java.sql.Types;

import jp.co.sint.webshop.data.StoredProcedure;
import jp.co.sint.webshop.data.StoredProcedureParameter;

public class CustomerRecommendSummaryProcedure implements StoredProcedure {

  private String month;

  private String updatedUser;

  public CustomerRecommendSummaryProcedure() {
  }

  public CustomerRecommendSummaryProcedure(String month, String updatedUser) {
    setMonth(month);
    setUpdatedUser(updatedUser);
  }

  public boolean commitOnSuccess() {
    return true;
  }

  public StoredProcedureParameter<?>[] getParameters() {
    StoredProcedureParameter<?>[] params = new StoredProcedureParameter[3];

    StoredProcedureParameter<Integer> paramMonth = StoredProcedureParameter.createInput(Integer.valueOf(getMonth()), Types.INTEGER);
    params[0] = paramMonth;

    StoredProcedureParameter<String> pramUpdatedUser = StoredProcedureParameter.createInput(getUpdatedUser(), Types.VARCHAR);
    params[1] = pramUpdatedUser;

    StoredProcedureParameter<Integer> lResult = StoredProcedureParameter.createOutput("r_result", Types.INTEGER);
    params[2] = lResult;

    return params;
  }

  public String getProcedureString() {
    return "{CALL CUSTOMER_RECOMMEND_PROC(?,?,?)}";
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
