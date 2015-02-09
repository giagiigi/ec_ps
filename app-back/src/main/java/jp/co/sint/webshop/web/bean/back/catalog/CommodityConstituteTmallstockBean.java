package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040130:TMALL库存分配。
 * 
 * @author System Integrator Corp.
 */
public class CommodityConstituteTmallstockBean extends UIBackBean implements UISearchBean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue;

  private List<CommodityConstituteTmallstockBeanDetail> list = new ArrayList<CommodityConstituteTmallstockBeanDetail>();

  private String orgCommodityCode;

  private String orgCommodityName;

  private String orgStockTotal;

  private String orgAllocatedTotal;

  private String orgSaleQuantity;

  private String editMode;
  
  private String suitQuantity;

  public static class CommodityConstituteTmallstockBeanDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String commodityCode;

    private String commodityName;

    private String combinationAmount;

    private String tmallUseFlg;

    private String tmallStock;

    private String beforeTmallStock;

    private String tmallAllocated;

    private String tmallSale;

    private String scale;

    private String groupAmount;

    /**
     * @return the beforeTmallStock
     */
    public String getBeforeTmallStock() {
      return beforeTmallStock;
    }

    /**
     * @param beforeTmallStock
     *          the beforeTmallStock to set
     */
    public void setBeforeTmallStock(String beforeTmallStock) {
      this.beforeTmallStock = beforeTmallStock;
    }

    /**
     * @return the commodityCode
     */
    public String getCommodityCode() {
      return commodityCode;
    }

    /**
     * @param commodityCode
     *          the commodityCode to set
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
     * @param commodityName
     *          the commodityName to set
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
     * @param combinationAmount
     *          the combinationAmount to set
     */
    public void setCombinationAmount(String combinationAmount) {
      this.combinationAmount = combinationAmount;
    }

    /**
     * @return the tmallUseFlg
     */
    public String getTmallUseFlg() {
      return tmallUseFlg;
    }

    /**
     * @param tmallUseFlg
     *          the tmallUseFlg to set
     */
    public void setTmallUseFlg(String tmallUseFlg) {
      this.tmallUseFlg = tmallUseFlg;
    }

    /**
     * @return the tmallStock
     */
    public String getTmallStock() {
      return tmallStock;
    }

    /**
     * @param tmallStock
     *          the tmallStock to set
     */
    public void setTmallStock(String tmallStock) {
      this.tmallStock = tmallStock;
    }

    /**
     * @return the tmallAllocated
     */
    public String getTmallAllocated() {
      return tmallAllocated;
    }

    /**
     * @param tmallAllocated
     *          the tmallAllocated to set
     */
    public void setTmallAllocated(String tmallAllocated) {
      this.tmallAllocated = tmallAllocated;
    }

    /**
     * @return the tmallSale
     */
    public String getTmallSale() {
      return tmallSale;
    }

    /**
     * @param tmallSale
     *          the tmallSale to set
     */
    public void setTmallSale(String tmallSale) {
      this.tmallSale = tmallSale;
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
  public List<CommodityConstituteTmallstockBeanDetail> getList() {
    return list;
  }

  /**
   * @param list
   *          the list to set
   */
  public void setList(List<CommodityConstituteTmallstockBeanDetail> list) {
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
    return Messages.getString("web.bean.back.catalog.CommodityConstituteTmallstockBean.0");
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
