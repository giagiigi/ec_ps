package jp.co.sint.webshop.service.analysis;

import java.sql.Types;
import java.util.Date;

import jp.co.sint.webshop.data.StoredProcedure;
import jp.co.sint.webshop.data.StoredProcedureParameter;
import jp.co.sint.webshop.utility.DateUtil;

public class RfmGenerateProcedure implements StoredProcedure {

  private Date startDate;

  private Date endDate;

  private Integer result;

  public RfmGenerateProcedure() {
  }

  public RfmGenerateProcedure(Date startDate, Date endDate) {
    setStartDate(startDate);
    setEndDate(endDate);
  }

  public boolean commitOnSuccess() {
    return false;
  }

  public StoredProcedureParameter<?>[] getParameters() {
    StoredProcedureParameter<?>[] params = new StoredProcedureParameter[3];

    StoredProcedureParameter<Date> param1 = StoredProcedureParameter.createInput(getStartDate(), Types.TIMESTAMP);
    params[0] = param1;

    StoredProcedureParameter<Date> param2 = StoredProcedureParameter.createInput(getEndDate(), Types.TIMESTAMP);
    params[1] = param2;

    StoredProcedureParameter<Integer> param3 = StoredProcedureParameter.createOutput("r_result", Types.INTEGER);
    params[2] = param3;

    return params;
  }

  public String getProcedureString() {
	  return "{CALL CREATE_RFM_PROC(?,?,?)}";
  }

  /**
   * orderDateを取得します。
   * 
   * @return orderDate
   */
  public Date getStartDate() {
    return DateUtil.immutableCopy(startDate);
  }

  /**
   * orderDateを設定します。
   * 
   * @param startDate
   *          
   */
  public void setStartDate(Date startDate) {
    this.startDate = DateUtil.immutableCopy(startDate);
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
   * endDateを返します。
   * 
   * @return the endDate
   */
  public Date getEndDate() {
    return DateUtil.immutableCopy(endDate);
  }

  /**
   * endDateを設定します。
   * 
   * @param endDate
   *          設定する endDate
   */
  public void setEndDate(Date endDate) {
    this.endDate = DateUtil.immutableCopy(endDate);
  }

}
