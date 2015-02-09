package jp.co.sint.webshop.service.customer;

import java.sql.Types;

import jp.co.sint.webshop.data.StoredProcedure;
import jp.co.sint.webshop.data.StoredProcedureParameter;

public class OverdueCouponProcedure implements StoredProcedure {

  private String month;

  private String updatedUser;

  public OverdueCouponProcedure() {
  }

  public OverdueCouponProcedure(String pMonth, String updatedUser) {
    setMonth(pMonth);
    setUpdatedUser(updatedUser);
  }

  public boolean commitOnSuccess() {
    return true;
  }

  public StoredProcedureParameter<?>[] getParameters() {
    StoredProcedureParameter<?>[] params = new StoredProcedureParameter[3];

    StoredProcedureParameter<Integer> paramMonth = StoredProcedureParameter.createInput(new Integer(getMonth()).intValue(),
        Types.INTEGER);
    params[0] = paramMonth;

    StoredProcedureParameter<String> pramUpdatedUser = StoredProcedureParameter.createInput(getUpdatedUser(), Types.VARCHAR);
    params[1] = pramUpdatedUser;

    StoredProcedureParameter<Integer> lResult = StoredProcedureParameter.createOutput("r_result", Types.INTEGER);
    params[2] = lResult;

    return params;
  }

  public String getProcedureString() {
    return "{CALL OVERDUE_COUPON_PROC(?,?,?)}";
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

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }
}
