package jp.co.sint.webshop.service.stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.JdStock;
import jp.co.sint.webshop.data.dto.JdSuitCommodity;

/**
 * JD套餐库存信息で使用する商品情報を保持するクラス
 * 
 * @author System Integrator Corp.
 */
public class JdSuitInfo implements Serializable, Cloneable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private JdSuitCommodity jdSuitCommodity = new JdSuitCommodity();

  private Long returnAmount;
  
  private List<JdStock>  jdSuitDetailList = new ArrayList<JdStock>();
  
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
   * @return the jdSuitDetailList
   */
  public List<JdStock> getJdSuitDetailList() {
    return jdSuitDetailList;
  }

  /**
   * @param jdSuitDetailList the jdSuitDetailList to set
   */
  public void setJdSuitDetailList(List<JdStock> jdSuitDetailList) {
    this.jdSuitDetailList = jdSuitDetailList;
  }

  /**
   * @return the tmallSuitCommodity
   */
  public JdSuitCommodity getJdSuitCommodity() {
    return jdSuitCommodity;
  }

  /**
   * @param tmallSuitCommodity the tmallSuitCommodity to set
   */
  public void setJdSuitCommodity(JdSuitCommodity jdSuitCommodity) {
    this.jdSuitCommodity = jdSuitCommodity;
  }

}
