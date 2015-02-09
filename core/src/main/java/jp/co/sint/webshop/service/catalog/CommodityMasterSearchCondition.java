package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.service.SearchCondition;
public class CommodityMasterSearchCondition extends SearchCondition {

  /**
   * TM/JD 多商品关联 检索条件
   * */
  private static final long serialVersionUID = 1L;

  private String commodityCode; //主商品编号
  private String commodityName; //主商品名称
  
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
 
}
