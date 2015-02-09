package jp.co.sint.webshop.service.catalog;

import java.sql.Types;

import jp.co.sint.webshop.data.StoredProcedure;
import jp.co.sint.webshop.data.StoredProcedureParameter;

/**
 * カテゴリ集計プロシージャ起動用クラス
 * 
 * @author System Integrator Corp.
 */
public class CategorySummaryProcedure implements StoredProcedure {

  private String updatedUser;

  public CategorySummaryProcedure() {
  }

  public CategorySummaryProcedure(String updatedUser) {
    setUpdatedUser(updatedUser);
  }

  public boolean commitOnSuccess() {
    return false;
  }

  public StoredProcedureParameter<?>[] getParameters() {
    StoredProcedureParameter<?>[] params = new StoredProcedureParameter[2];

    StoredProcedureParameter<String> lUpdatedUser = StoredProcedureParameter.createInput(getUpdatedUser(), Types.VARCHAR);

    StoredProcedureParameter<Integer> lResult = StoredProcedureParameter.createOutput("r_result", Types.INTEGER);

    params[0] = lUpdatedUser;

    params[1] = lResult;

    return params;
  }

  public String getProcedureString() {
	  return "{CALL CATEGORY_SUMMARY_PROC(?,?)}";
  }

  public String getUpdatedUser() {
    return updatedUser;
  }

  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }
}
