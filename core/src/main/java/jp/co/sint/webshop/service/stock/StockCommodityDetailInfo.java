package jp.co.sint.webshop.service.stock;

import java.io.Serializable;


/**
 * JD套餐库存信息で使用する商品情報を保持するクラス
 * 
 * @author System Integrator Corp.
 */
public class StockCommodityDetailInfo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  // 京东商品ID
  private Long jdCommodityId;
  
  // TMALL商品ID
  private Long tmallCommodityId;
  
  // 有效库存
  private Long effectiveQuantity;
  
  // 商品编号
  private String commodityCode;
  
  // 组合数量
  private Long combinationAmount;

  /**
   * @return the jdCommodityId
   */
  public Long getJdCommodityId() {
    return jdCommodityId;
  }

  /**
   * @param jdCommodityId the jdCommodityId to set
   */
  public void setJdCommodityId(Long jdCommodityId) {
    this.jdCommodityId = jdCommodityId;
  }

  /**
   * @return the tmallCommodityId
   */
  public Long getTmallCommodityId() {
    return tmallCommodityId;
  }

  /**
   * @param tmallCommodityId the tmallCommodityId to set
   */
  public void setTmallCommodityId(Long tmallCommodityId) {
    this.tmallCommodityId = tmallCommodityId;
  }

  /**
   * @return the effectiveQuantity
   */
  public Long getEffectiveQuantity() {
    return effectiveQuantity;
  }

  /**
   * @param effectiveQuantity the effectiveQuantity to set
   */
  public void setEffectiveQuantity(Long effectiveQuantity) {
    this.effectiveQuantity = effectiveQuantity;
  }

  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

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

}
