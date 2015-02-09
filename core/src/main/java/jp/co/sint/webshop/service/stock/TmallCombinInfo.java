package jp.co.sint.webshop.service.stock;

import java.io.Serializable;

import jp.co.sint.webshop.data.dto.TmallStockAllocation;

/**
 * TM组合库存信息で使用する商品情報を保持するクラス
 * 
 * @author System Integrator Corp.
 */
public class TmallCombinInfo implements Serializable, Cloneable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private TmallStockAllocation tmallStockAllocation = new TmallStockAllocation();

  /**
   * @return the tmallStockAllocation
   */
  public TmallStockAllocation getTmallStockAllocation() {
    return tmallStockAllocation;
  }

  /**
   * @param tmallStockAllocation
   *          the tmallStockAllocation to set
   */
  public void setTmallStockAllocation(TmallStockAllocation tmallStockAllocation) {
    this.tmallStockAllocation = tmallStockAllocation;
  }

  private Long combinationAmount;

  /**
   * @return the combinationAmount
   */
  public Long getCombinationAmount() {
    return combinationAmount;
  }

  /**
   * @param combinationAmount
   *          the combinationAmount to set
   */
  public void setCombinationAmount(Long combinationAmount) {
    this.combinationAmount = combinationAmount;
  }

}
