package jp.co.sint.webshop.service.analysis;

import java.sql.Types;
import java.util.Date;

import jp.co.sint.webshop.data.StoredProcedure;
import jp.co.sint.webshop.data.StoredProcedureParameter;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * ショップ別売上集計データ作成ストアドプロシージャ起動クラス
 * 
 * @author System Integrator Corp.
 */
public class SalesAmountByShopGenerateProcedure implements StoredProcedure {

  /** 集計開始日 */
  private Date startDate;

  /** 集計終了日 */
  private Date endDate;

  public boolean commitOnSuccess() {
    return false;
  }

  /** default constructor */
  public SalesAmountByShopGenerateProcedure() {
  }

  public SalesAmountByShopGenerateProcedure(Date start, Date end) {
    setStartDate(start);
    setEndDate(end);
  }

  public StoredProcedureParameter<?>[] getParameters() {
    StoredProcedureParameter<?>[] params = new StoredProcedureParameter[3];

    StoredProcedureParameter<Date> param0 = StoredProcedureParameter.createInput(getStartDate(), Types.DATE);
    params[0] = param0;

    StoredProcedureParameter<Date> param1 = StoredProcedureParameter.createInput(getEndDate(), Types.DATE);
    params[1] = param1;

    StoredProcedureParameter<Integer> param2 = StoredProcedureParameter.createOutput("r_result", Types.INTEGER);
    params[2] = param2;

    return params;
  }

  public String getProcedureString() {
	  return "{CALL CREATE_SALES_AMOUNT_SHOP_PROC(?,?,?)}";
  }

  /**
   * startDateを返します。
   * 
   * @return the startDate
   */
  public Date getStartDate() {
    return DateUtil.immutableCopy(startDate);
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
   * startDateを設定します。
   * 
   * @param startDate
   *          設定する startDate
   */
  public void setStartDate(Date startDate) {
    this.startDate = DateUtil.immutableCopy(startDate);
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
