package jp.co.sint.webshop.web.bean.front.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.service.catalog.CategoryData;
import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class LeftMenuBean extends UISubBean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String selected;

  private String categoryCode;

  private String brandCode;

  private String reviewScore;

  private String priceType;

  private String priceStart;

  private String priceEnd;

  private String attribute1;

  private String attribute2;

  private String attribute3;

  private String searchWord;

  private List<CategoryData> categoryList;
  
  private List<DetailBean> brandList = new ArrayList<DetailBean>();
  
  private List<DetailBean> reviewList = new ArrayList<DetailBean>();
  
  private List<DetailBean> priceList = new ArrayList<DetailBean>();
  
  private List<DetailBean> attributeList1 = new ArrayList<DetailBean>();
  
  private List<DetailBean> attributeList2 = new ArrayList<DetailBean>();
  
  private List<DetailBean> attributeList3 = new ArrayList<DetailBean>();
  
  private boolean brandFlag = false;
  
  private boolean attributeFlag = false;
  
  private boolean attribute1Flag = false;
  
  private boolean attribute2Flag = false;
  
  private boolean attribute3Flag = false;
  
  private boolean flag = false;

  public static class DetailBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String detailCode;

    private String detailName;

    private Long commodityCount;
    
    private Long commodityPopularRank;

    /**
     * @return the detailCode
     */
    public String getDetailCode() {
      return detailCode;
    }

    /**
     * @param detailCode
     *          the detailCode to set
     */
    public void setDetailCode(String detailCode) {
      this.detailCode = detailCode;
    }

    /**
     * @return the detailName
     */
    public String getDetailName() {
      return detailName;
    }

    /**
     * @param detailName
     *          the detailName to set
     */
    public void setDetailName(String detailName) {
      this.detailName = detailName;
    }

    /**
     * @return the commodityCount
     */
    public Long getCommodityCount() {
      return commodityCount;
    }

    /**
     * @param commodityCount
     *          the commodityCount to set
     */
    public void setCommodityCount(Long commodityCount) {
      this.commodityCount = commodityCount;
    }

    
    /**
     * @return the commodityPopularRank
     */
    public Long getCommodityPopularRank() {
      return commodityPopularRank;
    }

    
    /**
     * @param commodityPopularRank the commodityPopularRank to set
     */
    public void setCommodityPopularRank(Long commodityPopularRank) {
      this.commodityPopularRank = commodityPopularRank;
    }

  }

  @Override
  public void createAttributes(RequestParameter reqparam) {
  }

  /**
   * @return the categoryCode
   */
  public String getCategoryCode() {
    return categoryCode;
  }

  /**
   * @param categoryCode
   *          the categoryCode to set
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  /**
   * @return the brandCode
   */
  public String getBrandCode() {
    return brandCode;
  }

  /**
   * @param brandCode
   *          the brandCode to set
   */
  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  /**
   * @return the reviewScore
   */
  public String getReviewScore() {
    return reviewScore;
  }

  /**
   * @param reviewScore
   *          the reviewScore to set
   */
  public void setReviewScore(String reviewScore) {
    this.reviewScore = reviewScore;
  }

  /**
   * @return the priceType
   */
  public String getPriceType() {
    return priceType;
  }

  /**
   * @param priceType
   *          the priceType to set
   */
  public void setPriceType(String priceType) {
    this.priceType = priceType;
  }

  /**
   * @return the attribute1
   */
  public String getAttribute1() {
    return attribute1;
  }

  /**
   * @param attribute1
   *          the attribute1 to set
   */
  public void setAttribute1(String attribute1) {
    this.attribute1 = attribute1;
  }

  /**
   * @return the attribute2
   */
  public String getAttribute2() {
    return attribute2;
  }

  /**
   * @param attribute2
   *          the attribute2 to set
   */
  public void setAttribute2(String attribute2) {
    this.attribute2 = attribute2;
  }

  /**
   * @return the attribute3
   */
  public String getAttribute3() {
    return attribute3;
  }

  /**
   * @param attribute3
   *          the attribute3 to set
   */
  public void setAttribute3(String attribute3) {
    this.attribute3 = attribute3;
  }

  /**
   * @return the searchWord
   */
  public String getSearchWord() {
    return searchWord;
  }

  /**
   * @param searchWord
   *          the searchWord to set
   */
  public void setSearchWord(String searchWord) {
    this.searchWord = searchWord;
  }

  
  /**
   * @return the brandList
   */
  public List<DetailBean> getBrandList() {
    return brandList;
  }

  
  /**
   * @param brandList the brandList to set
   */
  public void setBrandList(List<DetailBean> brandList) {
    this.brandList = brandList;
  }

  
  /**
   * @return the reviewList
   */
  public List<DetailBean> getReviewList() {
    return reviewList;
  }

  
  /**
   * @param reviewList the reviewList to set
   */
  public void setReviewList(List<DetailBean> reviewList) {
    this.reviewList = reviewList;
  }

  
  /**
   * @return the priceList
   */
  public List<DetailBean> getPriceList() {
    return priceList;
  }

  
  /**
   * @param priceList the priceList to set
   */
  public void setPriceList(List<DetailBean> priceList) {
    this.priceList = priceList;
  }

  
  /**
   * @return the categoryList
   */
  public List<CategoryData> getCategoryList() {
    return categoryList;
  }

  
  /**
   * @param categoryList the categoryList to set
   */
  public void setCategoryList(List<CategoryData> categoryList) {
    this.categoryList = categoryList;
  }

  
  /**
   * @return the attributeList1
   */
  public List<DetailBean> getAttributeList1() {
    return attributeList1;
  }

  
  /**
   * @param attributeList1 the attributeList1 to set
   */
  public void setAttributeList1(List<DetailBean> attributeList1) {
    this.attributeList1 = attributeList1;
  }

  
  /**
   * @return the attributeList2
   */
  public List<DetailBean> getAttributeList2() {
    return attributeList2;
  }

  
  /**
   * @param attributeList2 the attributeList2 to set
   */
  public void setAttributeList2(List<DetailBean> attributeList2) {
    this.attributeList2 = attributeList2;
  }

  
  /**
   * @return the attributeList3
   */
  public List<DetailBean> getAttributeList3() {
    return attributeList3;
  }

  
  /**
   * @param attributeList3 the attributeList3 to set
   */
  public void setAttributeList3(List<DetailBean> attributeList3) {
    this.attributeList3 = attributeList3;
  }

  
  /**
   * @return the attributeFlag
   */
  public boolean isAttributeFlag() {
    return attributeFlag;
  }

  
  /**
   * @param attributeFlag the attributeFlag to set
   */
  public void setAttributeFlag(boolean attributeFlag) {
    this.attributeFlag = attributeFlag;
  }

  
  /**
   * @return the attribute1Flag
   */
  public boolean isAttribute1Flag() {
    return attribute1Flag;
  }

  
  /**
   * @param attribute1Flag the attribute1Flag to set
   */
  public void setAttribute1Flag(boolean attribute1Flag) {
    this.attribute1Flag = attribute1Flag;
  }

  
  /**
   * @return the attribute2Flag
   */
  public boolean isAttribute2Flag() {
    return attribute2Flag;
  }

  
  /**
   * @param attribute2Flag the attribute2Flag to set
   */
  public void setAttribute2Flag(boolean attribute2Flag) {
    this.attribute2Flag = attribute2Flag;
  }

  
  /**
   * @return the attribute3Flag
   */
  public boolean isAttribute3Flag() {
    return attribute3Flag;
  }

  
  /**
   * @param attribute3Flag the attribute3Flag to set
   */
  public void setAttribute3Flag(boolean attribute3Flag) {
    this.attribute3Flag = attribute3Flag;
  }

  
  /**
   * @return the brandFlag
   */
  public boolean isBrandFlag() {
    return brandFlag;
  }

  
  /**
   * @param brandFlag the brandFlag to set
   */
  public void setBrandFlag(boolean brandFlag) {
    this.brandFlag = brandFlag;
  }

  
  /**
   * @return the flag
   */
  public boolean isFlag() {
    return flag;
  }

  
  /**
   * @param flag the flag to set
   */
  public void setFlag(boolean flag) {
    this.flag = flag;
  }

  
  /**
   * @return the priceStart
   */
  public String getPriceStart() {
    return priceStart;
  }

  
  /**
   * @param priceStart the priceStart to set
   */
  public void setPriceStart(String priceStart) {
    this.priceStart = priceStart;
  }

  
  /**
   * @return the priceEnd
   */
  public String getPriceEnd() {
    return priceEnd;
  }

  
  /**
   * @param priceEnd the priceEnd to set
   */
  public void setPriceEnd(String priceEnd) {
    this.priceEnd = priceEnd;
  }

  
  /**
   * @return the selected
   */
  public String getSelected() {
    return selected;
  }

  
  /**
   * @param selected the selected to set
   */
  public void setSelected(String selected) {
    this.selected = selected;
  }

}
