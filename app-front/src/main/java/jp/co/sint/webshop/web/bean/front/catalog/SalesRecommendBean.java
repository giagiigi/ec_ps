package jp.co.sint.webshop.web.bean.front.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.service.CommodityHeadline;
import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * カテゴリツリーのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class SalesRecommendBean extends UISubBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<DetailBean> salesChartsList;
  
  private int maxLineCount;

  // 品店精选
  private List<CommodityHeadline> pageList = new ArrayList<CommodityHeadline>();
  
  // 热销商品
  private List<CommodityHeadline> pageHotList = new ArrayList<CommodityHeadline>();
  
  public static class DetailBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String categoryCode;

    private String categoryName;

    private String commodityCode1;

    private String commodityName1;

    private String commodityCode2;

    private String commodityName2;

    private String commodityCode3;

    private String commodityName3;

    
    /**
     * @return the categoryCode
     */
    public String getCategoryCode() {
      return categoryCode;
    }

    
    /**
     * @param categoryCode the categoryCode to set
     */
    public void setCategoryCode(String categoryCode) {
      this.categoryCode = categoryCode;
    }

    
    /**
     * @return the categoryName
     */
    public String getCategoryName() {
      return categoryName;
    }

    
    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
      this.categoryName = categoryName;
    }

    
    /**
     * @return the commodityCode1
     */
    public String getCommodityCode1() {
      return commodityCode1;
    }

    
    /**
     * @param commodityCode1 the commodityCode1 to set
     */
    public void setCommodityCode1(String commodityCode1) {
      this.commodityCode1 = commodityCode1;
    }

    
    /**
     * @return the commodityName1
     */
    public String getCommodityName1() {
      return commodityName1;
    }

    
    /**
     * @param commodityName1 the commodityName1 to set
     */
    public void setCommodityName1(String commodityName1) {
      this.commodityName1 = commodityName1;
    }

    
    /**
     * @return the commodityCode2
     */
    public String getCommodityCode2() {
      return commodityCode2;
    }

    
    /**
     * @param commodityCode2 the commodityCode2 to set
     */
    public void setCommodityCode2(String commodityCode2) {
      this.commodityCode2 = commodityCode2;
    }

    
    /**
     * @return the commodityName2
     */
    public String getCommodityName2() {
      return commodityName2;
    }

    
    /**
     * @param commodityName2 the commodityName2 to set
     */
    public void setCommodityName2(String commodityName2) {
      this.commodityName2 = commodityName2;
    }

    
    /**
     * @return the commodityCode3
     */
    public String getCommodityCode3() {
      return commodityCode3;
    }

    
    /**
     * @param commodityCode3 the commodityCode3 to set
     */
    public void setCommodityCode3(String commodityCode3) {
      this.commodityCode3 = commodityCode3;
    }

    
    /**
     * @return the commodityName3
     */
    public String getCommodityName3() {
      return commodityName3;
    }

    
    /**
     * @param commodityName3 the commodityName3 to set
     */
    public void setCommodityName3(String commodityName3) {
      this.commodityName3 = commodityName3;
    }
    
    
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    
  }

  
  /**
   * @return the salesChartsList
   */
  public List<DetailBean> getSalesChartsList() {
    return salesChartsList;
  }

  
  /**
   * @param salesChartsList the salesChartsList to set
   */
  public void setSalesChartsList(List<DetailBean> salesChartsList) {
    this.salesChartsList = salesChartsList;
  }


  
  /**
   * @return the maxLineCount
   */
  public int getMaxLineCount() {
    return maxLineCount;
  }


  
  /**
   * @param maxLineCount the maxLineCount to set
   */
  public void setMaxLineCount(int maxLineCount) {
    this.maxLineCount = maxLineCount;
  }


  
  /**
   * @return the pageList
   */
  public List<CommodityHeadline> getPageList() {
    return pageList;
  }


  
  /**
   * @param pageList the pageList to set
   */
  public void setPageList(List<CommodityHeadline> pageList) {
    this.pageList = pageList;
  }


  
  /**
   * @return the pageHotList
   */
  public List<CommodityHeadline> getPageHotList() {
    return pageHotList;
  }


  
  /**
   * @param pageHotList the pageHotList to set
   */
  public void setPageHotList(List<CommodityHeadline> pageHotList) {
    this.pageHotList = pageHotList;
  }
  
  
}
