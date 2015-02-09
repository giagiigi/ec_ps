package jp.co.sint.webshop.service.analysis;

import java.sql.Types;

import jp.co.sint.webshop.data.StoredProcedure;
import jp.co.sint.webshop.data.StoredProcedureParameter;

public class CommodityAccessLogDeleteProcedure implements StoredProcedure {

  private int month;

  public CommodityAccessLogDeleteProcedure() {
  }

  public CommodityAccessLogDeleteProcedure(int month) {
    this.month = month;
  }

  public boolean commitOnSuccess() {
    return false;
  }

  public StoredProcedureParameter<?>[] getParameters() {
    StoredProcedureParameter<?>[] params = new StoredProcedureParameter[2];

    StoredProcedureParameter<Integer> lMonth = StoredProcedureParameter.createInput(Integer.valueOf(getMonth()), Types.INTEGER);

    params[0] = lMonth;

    StoredProcedureParameter<Integer> lResult = StoredProcedureParameter.createOutput("r_result", Types.INTEGER);

    params[1] = lResult;

    return params;
  }

  public String getProcedureString() {
	  return "{CALL DELETE_COM_ACCESS_LOG_PROC(?,?)}";
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

}
