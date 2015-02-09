package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040130:京东库存分配。
 * 
 * @author System Integrator Corp.
 */
public class CommodityConstituteJdstockBean extends UIBackBean implements UISearchBean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue;

  private List<CommodityConstituteJdstockBeanDetail> list = new ArrayList<CommodityConstituteJdstockBeanDetail>();

  private String orgCommodityCode;

  private String orgCommodityName;

  private String orgStockTotal;

  private String orgAllocatedTotal;

  private String orgSaleQuantity;

  private String editMode;
  
  private String suitQuantity;

  public static class CommodityConstituteJdstockBeanDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String commodityCode;

    private String commodityName;

    private String combinationAmount;

    private String jdUseFlg;

    private String jdStock;

    private String beforeJdStock;

    private String jdAllocated;

    private String jdSale;

    private String scale;

    private String groupAmount;

   


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
     * @return the combinationAmount
     */
    public String getCombinationAmount() {
      return combinationAmount;
    }

    /**
     * @param combinationAmount the combinationAmount to set
     */
    public void setCombinationAmount(String combinationAmount) {
      this.combinationAmount = combinationAmount;
    }

    /**
     * @return the jdUseFlg
     */
    public String getJdUseFlg() {
      return jdUseFlg;
    }

    /**
     * @param jdUseFlg the jdUseFlg to set
     */
    public void setJdUseFlg(String jdUseFlg) {
      this.jdUseFlg = jdUseFlg;
    }

    /**
     * @return the jdStock
     */
    public String getJdStock() {
      return jdStock;
    }

    /**
     * @param jdStock the jdStock to set
     */
    public void setJdStock(String jdStock) {
      this.jdStock = jdStock;
    }



    /**
     * @return the beforeJdStock
     */
    public String getBeforeJdStock() {
      return beforeJdStock;
    }

    /**
     * @param beforeJdStock the beforeJdStock to set
     */
    public void setBeforeJdStock(String beforeJdStock) {
      this.beforeJdStock = beforeJdStock;
    }

    /**
     * @return the jdAllocated
     */
    public String getJdAllocated() {
      return jdAllocated;
    }

    /**
     * @param jdAllocated the jdAllocated to set
     */
    public void setJdAllocated(String jdAllocated) {
      this.jdAllocated = jdAllocated;
    }

    /**
     * @return the jdSale
     */
    public String getJdSale() {
      return jdSale;
    }

    /**
     * @param jdSale the jdSale to set
     */
    public void setJdSale(String jdSale) {
      this.jdSale = jdSale;
    }

    /**
     * @return the scale
     */
    public String getScale() {
      return scale;
    }

    /**
     * @param scale
     *          the scale to set
     */
    public void setScale(String scale) {
      this.scale = scale;
    }

    /**
     * @return the groupAmount
     */
    public String getGroupAmount() {
      return groupAmount;
    }

    /**
     * @param groupAmount
     *          the groupAmount to set
     */
    public void setGroupAmount(String groupAmount) {
      this.groupAmount = groupAmount;
    }

  }

  /**
   * @return the orgCommodityCode
   */
  public String getOrgCommodityCode() {
    return orgCommodityCode;
  }

  /**
   * @param orgCommodityCode
   *          the orgCommodityCode to set
   */
  public void setOrgCommodityCode(String orgCommodityCode) {
    this.orgCommodityCode = orgCommodityCode;
  }

  /**
   * @return the orgCommodityName
   */
  public String getOrgCommodityName() {
    return orgCommodityName;
  }

  /**
   * @param orgCommodityName
   *          the orgCommodityName to set
   */
  public void setOrgCommodityName(String orgCommodityName) {
    this.orgCommodityName = orgCommodityName;
  }

  /**
   * @return the orgStockTotal
   */
  public String getOrgStockTotal() {
    return orgStockTotal;
  }

  /**
   * @param orgStockTotal
   *          the orgStockTotal to set
   */
  public void setOrgStockTotal(String orgStockTotal) {
    this.orgStockTotal = orgStockTotal;
  }

  /**
   * @return the orgAllocatedTotal
   */
  public String getOrgAllocatedTotal() {
    return orgAllocatedTotal;
  }

  /**
   * @param orgAllocatedTotal
   *          the orgAllocatedTotal to set
   */
  public void setOrgAllocatedTotal(String orgAllocatedTotal) {
    this.orgAllocatedTotal = orgAllocatedTotal;
  }

  /**
   * @return the orgSaleQuantity
   */
  public String getOrgSaleQuantity() {
    return orgSaleQuantity;
  }

  /**
   * @param orgSaleQuantity
   *          the orgSaleQuantity to set
   */
  public void setOrgSaleQuantity(String orgSaleQuantity) {
    this.orgSaleQuantity = orgSaleQuantity;
  }

  /**
   * @return the editMode
   */
  public String getEditMode() {
    return editMode;
  }

  /**
   * @param editMode
   *          the editMode to set
   */
  public void setEditMode(String editMode) {
    this.editMode = editMode;
  }

  /**
   * @return the list
   */
  public List<CommodityConstituteJdstockBeanDetail> getList() {
    return list;
  }

  /**
   * @param list
   *          the list to set
   */
  public void setList(List<CommodityConstituteJdstockBeanDetail> list) {
    this.list = list;
  }

  @Override
  public void setSubJspId() {

  }

  @Override
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  @Override
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  @Override
  public void createAttributes(RequestParameter reqparam) {

  }

  @Override
  public String getModuleId() {
    return "U1040130";
  }

  @Override
  public String getModuleName() {
    return "京东库存分配";
  }

  
  /**
   * @return the suitQuantity
   */
  public String getSuitQuantity() {
    return suitQuantity;
  }

  
  /**
   * @param suitQuantity the suitQuantity to set
   */
  public void setSuitQuantity(String suitQuantity) {
    this.suitQuantity = suitQuantity;
  }

}
