package jp.co.sint.webshop.service.customer;

import java.sql.Types;

import jp.co.sint.webshop.data.StoredProcedure;
import jp.co.sint.webshop.data.StoredProcedureParameter;
import jp.co.sint.webshop.data.domain.PointIssueStatus;
import jp.co.sint.webshop.data.domain.PointIssueType;

public class DeleteExpiredPointProcedure implements StoredProcedure {

  private String updatedUser;

  public DeleteExpiredPointProcedure() {
  }

  public DeleteExpiredPointProcedure(String updatedUser) {
    setUpdatedUser(updatedUser);
  }

  public boolean commitOnSuccess() {
    return true;
  }

  public StoredProcedureParameter<?>[] getParameters() {
    StoredProcedureParameter<?>[] params = new StoredProcedureParameter[5];

    StoredProcedureParameter<Integer> enable = StoredProcedureParameter.createInput(Integer.valueOf(PointIssueStatus.ENABLED
        .getValue()), Types.INTEGER);

    params[0] = enable;

    StoredProcedureParameter<Integer> disabled = StoredProcedureParameter.createInput(Integer.valueOf(PointIssueStatus.DISABLED
        .getValue()), Types.INTEGER);

    params[1] = disabled;

    StoredProcedureParameter<Integer> adjustment = StoredProcedureParameter.createInput(Integer.valueOf(PointIssueType.ADJUSTMENT
        .getValue()), Types.INTEGER);

    params[2] = adjustment;

    StoredProcedureParameter<String> paramUpdatedUser = StoredProcedureParameter.createInput(getUpdatedUser(), Types.VARCHAR);
    params[3] = paramUpdatedUser;

    // 10.1.1 10011 修正 ここから
    // StoredProcedureParameter<Integer> lResult = StoredProcedureParameter.createOutput("r_result_no", Types.INTEGER);
    StoredProcedureParameter<Integer> lResult = StoredProcedureParameter.createOutput("r_result", Types.INTEGER);
    // 10.1.1 10011 修正 ここまで

    params[4] = lResult;

    return params;
  }

  public String getProcedureString() {
    return "{CALL DELETE_EXPIRE_POINT_PROC(?,?,?,?,?)}";
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
