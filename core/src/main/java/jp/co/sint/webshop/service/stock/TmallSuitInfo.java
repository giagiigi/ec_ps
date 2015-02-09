package jp.co.sint.webshop.service.stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.TmallStock;
import jp.co.sint.webshop.data.dto.TmallSuitCommodity;

/**
 * TM套餐库存信息で使用する商品情報を保持するクラス
 * 
 * @author System Integrator Corp.
 */
public class TmallSuitInfo implements Serializable, Cloneable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  private Long returnAmount;
  
  private TmallSuitCommodity tmallSuitCommodity = new TmallSuitCommodity();
  
  private List<TmallStock>  tmallSuitDetailList = new ArrayList<TmallStock>();
  
  /**
   * @return the returnAmount
   */
  public Long getReturnAmount() {
    return returnAmount;
  }

  /**
   * @param returnAmount the returnAmount to set
   */
  public void setReturnAmount(Long returnAmount) {
    this.returnAmount = returnAmount;
  }

  /**
   * @return the tmallSuitDetailList
   */
  public List<TmallStock> getTmallSuitDetailList() {
    return tmallSuitDetailList;
  }

  /**
   * @param tmallSuitDetailList the tmallSuitDetailList to set
   */
  public void setTmallSuitDetailList(List<TmallStock> tmallSuitDetailList) {
    this.tmallSuitDetailList = tmallSuitDetailList;
  }

  /**
   * @return the tmallSuitCommodity
   */
  public TmallSuitCommodity getTmallSuitCommodity() {
    return tmallSuitCommodity;
  }

  /**
   * @param tmallSuitCommodity the tmallSuitCommodity to set
   */
  public void setTmallSuitCommodity(TmallSuitCommodity tmallSuitCommodity) {
    this.tmallSuitCommodity = tmallSuitCommodity;
  }

}
