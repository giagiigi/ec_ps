package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;

public class CommodityMasterResult implements Serializable {

  /**
   * TM/JD 多商品关联 辅助类
   */
  private static final long serialVersionUID = 1L;
  private String commodityCode;     
  private String commodityName;   
  private String jdCommodityCode;  
  private String tmallCommodityCode;
  
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
   * @return the commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }
  
  /**
   * @param commodityName the commodityName to set
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }
  
  /**
   * @return the jdCommodityCode
   */
  public String getJdCommodityCode() {
    return jdCommodityCode;
  }
  
  /**
   * @param jdCommodityCode the jdCommodityCode to set
   */
  public void setJdCommodityCode(String jdCommodityCode) {
    this.jdCommodityCode = jdCommodityCode;
  }
  
  /**
   * @return the tmallCommodityCode
   */
  public String getTmallCommodityCode() {
    return tmallCommodityCode;
  }
  
  /**
   * @param tmallCommodityCode the tmallCommodityCode to set
   */
  public void setTmallCommodityCode(String tmallCommodityCode) {
    this.tmallCommodityCode = tmallCommodityCode;
  }
  
}
