package jp.co.sint.webshop.service.analysis;

import java.sql.Types;

import jp.co.sint.webshop.data.StoredProcedure;
import jp.co.sint.webshop.data.StoredProcedureParameter;

/**
 * 管理側ユーザアクセスログ削除ストアドプロシージャ起動用クラス。
 * 
 * @author System Integrator Corp.
 */
public class UserAccessLogDeleteProcedure implements StoredProcedure {

  /** ログの保存期間(月) */
  private int month;

  public UserAccessLogDeleteProcedure() {
  }

  public UserAccessLogDeleteProcedure(int month) {
    setMonth(month);
  }

  public boolean commitOnSuccess() {
    return false;
  }

  public StoredProcedureParameter<?>[] getParameters() {
    StoredProcedureParameter<?>[] params = new StoredProcedureParameter[2];

    StoredProcedureParameter<Integer> lMonth = StoredProcedureParameter.createInput(Integer.valueOf(getMonth()), Types.INTEGER);

    params[0] = lMonth;

    StoredProcedureParameter<Integer> lResult = StoredProcedureParameter.createOutput("LRESULT", Types.INTEGER);

    params[1] = lResult;

    return params;
  }

  public String getProcedureString() {
	  return "{CALL DELETE_USER_ACCESS_LOG_PROC(?,?)}";
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }
}
