package jp.co.sint.webshop.web.bean.front.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.service.CommodityHeadline;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition.SearchDetailAttributeList;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * カテゴリツリーのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class NewSalesRecommendBean extends UISubBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<DetailBean> salesChartsList;

  private int maxLineCount;

  // 品店精选
  private List<CommodityHeadline> pageList = new ArrayList<CommodityHeadline>();

  // 热销商品
  private List<CommodityHeadline> pageHotList = new ArrayList<CommodityHeadline>();
  
  
  private List<SearchDetailAttributeList> searchCategoryAttributeList = new ArrayList<SearchDetailAttributeList>();
  
  
  private String searchSelected;

  private boolean selectedFlg;

  // used for detect if a customer already left 5 or more message.
  private boolean fiveMessageFlag;

  private String searchMethod;

  private String searchWord;

  private String encoded_searchWord;

  private String searchCommodityCode;

  private String searchPriceStart;

  private String searchPriceEnd;

  private String searchCategoryCode;

  private String searchShopCode;

  private String searchCampaignCode;

  private String searchTagCode;

  private String searchReviewScore;

  private String alignmentSequence;

  private PagerValue pagerValue;

  private String searchBrandCode;

  private String searchAttribute1;

  private String searchAttribute2;

  private String searchAttribute3;

  private String searchPrice;

  private boolean sessionRead;

  private String metaKeyword;

  private String metaDescription;

  private String priceStart;

  private String priceEnd;

  private String categoryName;

  private boolean categoryFlag;

  private boolean brandFlag;

  private String url;

  // 20121213 txw add start
  private boolean displayCategoryFlg;

  // 20121213 txw add end

  private String selectCommodityCode;

  private String importCommodityType;

  private String clearCommodityType;

  private String reserveCommodityType1;

  private String reserveCommodityType2;

  private String reserveCommodityType3;

  private String newReserveCommodityType1;

  private String newReserveCommodityType2;

  private String newReserveCommodityType3;

  private String newReserveCommodityType4;

  private String newReserveCommodityType5;

  // 20130809 txw add start
  private String title;

  private String description;

  private String keyword;

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
     * @param categoryCode
     *          the categoryCode to set
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
     * @param categoryName
     *          the categoryName to set
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
     * @param commodityCode1
     *          the commodityCode1 to set
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
     * @param commodityName1
     *          the commodityName1 to set
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
     * @param commodityCode2
     *          the commodityCode2 to set
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
     * @param commodityName2
     *          the commodityName2 to set
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
     * @param commodityCode3
     *          the commodityCode3 to set
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
     * @param commodityName3
     *          the commodityName3 to set
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
    this.setSessionRead(false);
    if (StringUtil.hasValue(reqparam.get("sessionRead")) && reqparam.get("sessionRead").equals("1")) {
      this.setSessionRead(true);
    }
    // 检索按钮查找
    searchCategoryCode = reqparam.get("searchCategoryCode"); // 商品目录
    searchWord = reqparam.get("searchWord");
    encoded_searchWord = reqparam.get("searchWord");
    searchTagCode = reqparam.get("searchTagCode");
    importCommodityType = reqparam.get("importCommodityType");
    clearCommodityType = reqparam.get("clearCommodityType");
    reserveCommodityType1 = reqparam.get("reserveCommodityType1");
    reserveCommodityType2 = reqparam.get("reserveCommodityType2");
    reserveCommodityType3 = reqparam.get("reserveCommodityType3");
    newReserveCommodityType1 = reqparam.get("newReserveCommodityType1");
    newReserveCommodityType2 = reqparam.get("newReserveCommodityType2");
    newReserveCommodityType3 = reqparam.get("newReserveCommodityType3");
    newReserveCommodityType4 = reqparam.get("newReserveCommodityType4");
    newReserveCommodityType5 = reqparam.get("newReserveCommodityType5");

    // this.setSearchCategoryCode("");
    this.setSelectedFlg(false);
    this.setSearchBrandCode("");
    this.setSearchSelected("");
    this.setSearchReviewScore("");
    this.setSearchPriceStart("");
    this.setSearchPriceEnd("");
    this.setSearchPrice("");
    this.setSearchAttribute1("");
    this.setSearchAttribute2("");
    this.setSearchAttribute3("");
    this.setPriceStart("");
    this.setPriceEnd("");
  }


  /**
   * @return the salesChartsList
   */
  public List<DetailBean> getSalesChartsList() {
    return salesChartsList;
  }

  /**
   * @param salesChartsList
   *          the salesChartsList to set
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
   * @param maxLineCount
   *          the maxLineCount to set
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
   * @param pageList
   *          the pageList to set
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
   * @param pageHotList
   *          the pageHotList to set
   */
  public void setPageHotList(List<CommodityHeadline> pageHotList) {
    this.pageHotList = pageHotList;
  }

  /**
   * @return the selectedFlg
   */
  public boolean isSelectedFlg() {
    return selectedFlg;
  }

  /**
   * @param selectedFlg
   *          the selectedFlg to set
   */
  public void setSelectedFlg(boolean selectedFlg) {
    this.selectedFlg = selectedFlg;
  }

  /**
   * @return the fiveMessageFlag
   */
  public boolean isFiveMessageFlag() {
    return fiveMessageFlag;
  }

  /**
   * @param fiveMessageFlag
   *          the fiveMessageFlag to set
   */
  public void setFiveMessageFlag(boolean fiveMessageFlag) {
    this.fiveMessageFlag = fiveMessageFlag;
  }

  /**
   * @return the searchMethod
   */
  public String getSearchMethod() {
    return searchMethod;
  }

  /**
   * @param searchMethod
   *          the searchMethod to set
   */
  public void setSearchMethod(String searchMethod) {
    this.searchMethod = searchMethod;
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
   * @return the encoded_searchWord
   */
  public String getEncoded_searchWord() {
    return encoded_searchWord;
  }

  /**
   * @param encoded_searchWord
   *          the encoded_searchWord to set
   */
  public void setEncoded_searchWord(String encoded_searchWord) {
    this.encoded_searchWord = encoded_searchWord;
  }

  /**
   * @return the searchCommodityCode
   */
  public String getSearchCommodityCode() {
    return searchCommodityCode;
  }

  /**
   * @param searchCommodityCode
   *          the searchCommodityCode to set
   */
  public void setSearchCommodityCode(String searchCommodityCode) {
    this.searchCommodityCode = searchCommodityCode;
  }

  /**
   * @return the searchPriceStart
   */
  public String getSearchPriceStart() {
    return searchPriceStart;
  }

  /**
   * @param searchPriceStart
   *          the searchPriceStart to set
   */
  public void setSearchPriceStart(String searchPriceStart) {
    this.searchPriceStart = searchPriceStart;
  }

  /**
   * @return the searchPriceEnd
   */
  public String getSearchPriceEnd() {
    return searchPriceEnd;
  }

  /**
   * @param searchPriceEnd
   *          the searchPriceEnd to set
   */
  public void setSearchPriceEnd(String searchPriceEnd) {
    this.searchPriceEnd = searchPriceEnd;
  }

  /**
   * @return the searchCategoryCode
   */
  public String getSearchCategoryCode() {
    return searchCategoryCode;
  }

  /**
   * @param searchCategoryCode
   *          the searchCategoryCode to set
   */
  public void setSearchCategoryCode(String searchCategoryCode) {
    this.searchCategoryCode = searchCategoryCode;
  }

  /**
   * @return the searchShopCode
   */
  public String getSearchShopCode() {
    return searchShopCode;
  }

  /**
   * @param searchShopCode
   *          the searchShopCode to set
   */
  public void setSearchShopCode(String searchShopCode) {
    this.searchShopCode = searchShopCode;
  }

  /**
   * @return the searchCampaignCode
   */
  public String getSearchCampaignCode() {
    return searchCampaignCode;
  }

  /**
   * @param searchCampaignCode
   *          the searchCampaignCode to set
   */
  public void setSearchCampaignCode(String searchCampaignCode) {
    this.searchCampaignCode = searchCampaignCode;
  }

  /**
   * @return the searchTagCode
   */
  public String getSearchTagCode() {
    return searchTagCode;
  }

  /**
   * @param searchTagCode
   *          the searchTagCode to set
   */
  public void setSearchTagCode(String searchTagCode) {
    this.searchTagCode = searchTagCode;
  }

  /**
   * @return the searchReviewScore
   */
  public String getSearchReviewScore() {
    return searchReviewScore;
  }

  /**
   * @param searchReviewScore
   *          the searchReviewScore to set
   */
  public void setSearchReviewScore(String searchReviewScore) {
    this.searchReviewScore = searchReviewScore;
  }

  /**
   * @return the alignmentSequence
   */
  public String getAlignmentSequence() {
    return alignmentSequence;
  }

  /**
   * @param alignmentSequence
   *          the alignmentSequence to set
   */
  public void setAlignmentSequence(String alignmentSequence) {
    this.alignmentSequence = alignmentSequence;
  }

  /**
   * @return the pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * @param pagerValue
   *          the pagerValue to set
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * @return the searchBrandCode
   */
  public String getSearchBrandCode() {
    return searchBrandCode;
  }

  /**
   * @param searchBrandCode
   *          the searchBrandCode to set
   */
  public void setSearchBrandCode(String searchBrandCode) {
    this.searchBrandCode = searchBrandCode;
  }

  /**
   * @return the searchAttribute1
   */
  public String getSearchAttribute1() {
    return searchAttribute1;
  }

  /**
   * @param searchAttribute1
   *          the searchAttribute1 to set
   */
  public void setSearchAttribute1(String searchAttribute1) {
    this.searchAttribute1 = searchAttribute1;
  }

  /**
   * @return the searchAttribute2
   */
  public String getSearchAttribute2() {
    return searchAttribute2;
  }

  /**
   * @param searchAttribute2
   *          the searchAttribute2 to set
   */
  public void setSearchAttribute2(String searchAttribute2) {
    this.searchAttribute2 = searchAttribute2;
  }

  /**
   * @return the searchAttribute3
   */
  public String getSearchAttribute3() {
    return searchAttribute3;
  }

  /**
   * @param searchAttribute3
   *          the searchAttribute3 to set
   */
  public void setSearchAttribute3(String searchAttribute3) {
    this.searchAttribute3 = searchAttribute3;
  }

  /**
   * @return the searchPrice
   */
  public String getSearchPrice() {
    return searchPrice;
  }

  /**
   * @param searchPrice
   *          the searchPrice to set
   */
  public void setSearchPrice(String searchPrice) {
    this.searchPrice = searchPrice;
  }

  /**
   * @return the sessionRead
   */
  public boolean isSessionRead() {
    return sessionRead;
  }

  /**
   * @param sessionRead
   *          the sessionRead to set
   */
  public void setSessionRead(boolean sessionRead) {
    this.sessionRead = sessionRead;
  }

  /**
   * @return the metaKeyword
   */
  public String getMetaKeyword() {
    return metaKeyword;
  }

  /**
   * @param metaKeyword
   *          the metaKeyword to set
   */
  public void setMetaKeyword(String metaKeyword) {
    this.metaKeyword = metaKeyword;
  }

  /**
   * @return the metaDescription
   */
  public String getMetaDescription() {
    return metaDescription;
  }

  /**
   * @param metaDescription
   *          the metaDescription to set
   */
  public void setMetaDescription(String metaDescription) {
    this.metaDescription = metaDescription;
  }

  /**
   * @return the priceStart
   */
  public String getPriceStart() {
    return priceStart;
  }

  /**
   * @param priceStart
   *          the priceStart to set
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
   * @param priceEnd
   *          the priceEnd to set
   */
  public void setPriceEnd(String priceEnd) {
    this.priceEnd = priceEnd;
  }

  /**
   * @return the categoryName
   */
  public String getCategoryName() {
    return categoryName;
  }

  /**
   * @param categoryName
   *          the categoryName to set
   */
  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  /**
   * @return the categoryFlag
   */
  public boolean isCategoryFlag() {
    return categoryFlag;
  }

  /**
   * @param categoryFlag
   *          the categoryFlag to set
   */
  public void setCategoryFlag(boolean categoryFlag) {
    this.categoryFlag = categoryFlag;
  }

  /**
   * @return the brandFlag
   */
  public boolean isBrandFlag() {
    return brandFlag;
  }

  /**
   * @param brandFlag
   *          the brandFlag to set
   */
  public void setBrandFlag(boolean brandFlag) {
    this.brandFlag = brandFlag;
  }

  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @param url
   *          the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * @return the displayCategoryFlg
   */
  public boolean isDisplayCategoryFlg() {
    return displayCategoryFlg;
  }

  /**
   * @param displayCategoryFlg
   *          the displayCategoryFlg to set
   */
  public void setDisplayCategoryFlg(boolean displayCategoryFlg) {
    this.displayCategoryFlg = displayCategoryFlg;
  }

  /**
   * @return the selectCommodityCode
   */
  public String getSelectCommodityCode() {
    return selectCommodityCode;
  }

  /**
   * @param selectCommodityCode
   *          the selectCommodityCode to set
   */
  public void setSelectCommodityCode(String selectCommodityCode) {
    this.selectCommodityCode = selectCommodityCode;
  }

  /**
   * @return the importCommodityType
   */
  public String getImportCommodityType() {
    return importCommodityType;
  }

  /**
   * @param importCommodityType
   *          the importCommodityType to set
   */
  public void setImportCommodityType(String importCommodityType) {
    this.importCommodityType = importCommodityType;
  }

  /**
   * @return the clearCommodityType
   */
  public String getClearCommodityType() {
    return clearCommodityType;
  }

  /**
   * @param clearCommodityType
   *          the clearCommodityType to set
   */
  public void setClearCommodityType(String clearCommodityType) {
    this.clearCommodityType = clearCommodityType;
  }

  /**
   * @return the reserveCommodityType1
   */
  public String getReserveCommodityType1() {
    return reserveCommodityType1;
  }

  /**
   * @param reserveCommodityType1
   *          the reserveCommodityType1 to set
   */
  public void setReserveCommodityType1(String reserveCommodityType1) {
    this.reserveCommodityType1 = reserveCommodityType1;
  }

  /**
   * @return the reserveCommodityType2
   */
  public String getReserveCommodityType2() {
    return reserveCommodityType2;
  }

  /**
   * @param reserveCommodityType2
   *          the reserveCommodityType2 to set
   */
  public void setReserveCommodityType2(String reserveCommodityType2) {
    this.reserveCommodityType2 = reserveCommodityType2;
  }

  /**
   * @return the reserveCommodityType3
   */
  public String getReserveCommodityType3() {
    return reserveCommodityType3;
  }

  /**
   * @param reserveCommodityType3
   *          the reserveCommodityType3 to set
   */
  public void setReserveCommodityType3(String reserveCommodityType3) {
    this.reserveCommodityType3 = reserveCommodityType3;
  }

  /**
   * @return the newReserveCommodityType1
   */
  public String getNewReserveCommodityType1() {
    return newReserveCommodityType1;
  }

  /**
   * @param newReserveCommodityType1
   *          the newReserveCommodityType1 to set
   */
  public void setNewReserveCommodityType1(String newReserveCommodityType1) {
    this.newReserveCommodityType1 = newReserveCommodityType1;
  }

  /**
   * @return the newReserveCommodityType2
   */
  public String getNewReserveCommodityType2() {
    return newReserveCommodityType2;
  }

  /**
   * @param newReserveCommodityType2
   *          the newReserveCommodityType2 to set
   */
  public void setNewReserveCommodityType2(String newReserveCommodityType2) {
    this.newReserveCommodityType2 = newReserveCommodityType2;
  }

  /**
   * @return the newReserveCommodityType3
   */
  public String getNewReserveCommodityType3() {
    return newReserveCommodityType3;
  }

  /**
   * @param newReserveCommodityType3
   *          the newReserveCommodityType3 to set
   */
  public void setNewReserveCommodityType3(String newReserveCommodityType3) {
    this.newReserveCommodityType3 = newReserveCommodityType3;
  }

  /**
   * @return the newReserveCommodityType4
   */
  public String getNewReserveCommodityType4() {
    return newReserveCommodityType4;
  }

  /**
   * @param newReserveCommodityType4
   *          the newReserveCommodityType4 to set
   */
  public void setNewReserveCommodityType4(String newReserveCommodityType4) {
    this.newReserveCommodityType4 = newReserveCommodityType4;
  }

  /**
   * @return the newReserveCommodityType5
   */
  public String getNewReserveCommodityType5() {
    return newReserveCommodityType5;
  }

  /**
   * @param newReserveCommodityType5
   *          the newReserveCommodityType5 to set
   */
  public void setNewReserveCommodityType5(String newReserveCommodityType5) {
    this.newReserveCommodityType5 = newReserveCommodityType5;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title
   *          the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the keyword
   */
  public String getKeyword() {
    return keyword;
  }

  /**
   * @param keyword
   *          the keyword to set
   */
  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  
  /**
   * @return the searchSelected
   */
  public String getSearchSelected() {
    return searchSelected;
  }

  
  /**
   * @param searchSelected the searchSelected to set
   */
  public void setSearchSelected(String searchSelected) {
    this.searchSelected = searchSelected;
  }

  
  /**
   * @return the searchCategoryAttributeList
   */
  public List<SearchDetailAttributeList> getSearchCategoryAttributeList() {
    return searchCategoryAttributeList;
  }

  
  /**
   * @param searchCategoryAttributeList the searchCategoryAttributeList to set
   */
  public void setSearchCategoryAttributeList(List<SearchDetailAttributeList> searchCategoryAttributeList) {
    this.searchCategoryAttributeList = searchCategoryAttributeList;
  }

}
