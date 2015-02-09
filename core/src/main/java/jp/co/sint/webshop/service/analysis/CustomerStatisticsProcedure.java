package jp.co.sint.webshop.service.analysis;

import java.sql.Types;

import jp.co.sint.webshop.data.StoredProcedure;
import jp.co.sint.webshop.data.StoredProcedureParameter;

public class CustomerStatisticsProcedure implements StoredProcedure {

  public CustomerStatisticsProcedure() {
  }

  public boolean commitOnSuccess() {
    return false;
  }

  public StoredProcedureParameter<?>[] getParameters() {
    StoredProcedureParameter<?>[] params = new StoredProcedureParameter[1];

    StoredProcedureParameter<Integer> lResult = StoredProcedureParameter.createOutput("r_result", Types.INTEGER);

    params[0] = lResult;

    return params;
  }

  public String getProcedureString() {
	  return "{CALL CUSTOMER_STATISTICS_PROC(?)}";
  }

}
