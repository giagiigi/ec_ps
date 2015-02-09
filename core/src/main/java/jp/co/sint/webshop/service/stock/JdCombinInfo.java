package jp.co.sint.webshop.service.stock;

import java.io.Serializable;

import jp.co.sint.webshop.data.dto.JdStockAllocation;

/**
 * JD组合库存信息で使用する商品情報を保持するクラス
 * 
 * @author System Integrator Corp.
 */
public class JdCombinInfo implements Serializable, Cloneable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private JdStockAllocation jdStockAllocation = new JdStockAllocation();
  
  private Long combinationAmount;


  /**
   * @return the combinationAmount
   */
  public Long getCombinationAmount() {
    return combinationAmount;
  }

  /**
   * @param combinationAmount the combinationAmount to set
   */
  public void setCombinationAmount(Long combinationAmount) {
    this.combinationAmount = combinationAmount;
  }

  /**
   * @return the jdStockAllocation
   */
  public JdStockAllocation getJdStockAllocation() {
    return jdStockAllocation;
  }

  /**
   * @param jdStockAllocation the jdStockAllocation to set
   */
  public void setJdStockAllocation(JdStockAllocation jdStockAllocation) {
    this.jdStockAllocation = jdStockAllocation;
  }


}
