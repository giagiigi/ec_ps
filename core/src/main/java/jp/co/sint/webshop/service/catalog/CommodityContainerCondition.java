package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.PlanDetail;
import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.ArrayUtil;

public class CommodityContainerCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String searchMethod;
  
  private String searchSelected;

  private String searchShopCode;

  private String searchCategoryCode;

  private String searchCommodityCode;

  private String searchCommodityName;

  private String searchSkuCode;

  private String searchWord;

  private String reviewScore;

  private String searchPriceStart;

  private String searchPriceEnd;

  private String searchCustomerCode;

  private String searchCampaignCode;

  private String searchTagCode;

  private String alignmentSequence;

  private String searchListSort;

  // add by wjw 20120103 start
  private String searchBrandCode;

  private String searchCategoryAttribute1;

  private String searchCategoryAttribute2;

  private String searchCategoryAttribute3;

  // add by wjw 20120103 end

  private String searchSaleFlag;

  private String searchSpecFlag;

  private PlanDetail searchPlanDetail;

  private String displayClientType;

  private boolean byRepresent;

  private boolean ignoreDeliveryDisplsayType = false;

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

  private List<SearchDetailAttributeList> searchDetailAttributeList = new ArrayList<SearchDetailAttributeList>();

  // 20120217 shen add start
  private String[] searchCartCommodityCode = new String[0];

  // 20120217 shen add end
  
  // 20140902 hdh add start
  private String forHotSale;
  // 20140902 hdh add end
 

  public static class SearchDetailAttributeList implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String categoryAttributeNo;

    private String categoryAttributeValue;

    /**
     * @return categoryAttributeNo
     */
    public String getCategoryAttributeNo() {
      return categoryAttributeNo;
    }

    /**
     * @param categoryAttributeNo
     *          設定する categoryAttributeNo
     */
    public void setCategoryAttributeNo(String categoryAttributeNo) {
      this.categoryAttributeNo = categoryAttributeNo;
    }

    /**
     * @return categoryAttributeValue
     */
    public String getCategoryAttributeValue() {
      return categoryAttributeValue;
    }

    /**
     * @param categoryAttributeValue
     *          設定する categoryAttributeValue
     */
    public void setCategoryAttributeValue(String categoryAttributeValue) {
      this.categoryAttributeValue = categoryAttributeValue;
    }

  }

  /**
   * @return displayClientType
   */
  public String getDisplayClientType() {
    return displayClientType;
  }

  /**
   * @param displayClientType
   *          設定する displayClientType
   */
  public void setDisplayClientType(String displayClientType) {
    this.displayClientType = displayClientType;
  }

  /**
   * @return searchCategoryCode
   */
  public String getSearchCategoryCode() {
    return searchCategoryCode;
  }

  /**
   * @param searchCategoryCode
   *          設定する searchCategoryCode
   */
  public void setSearchCategoryCode(String searchCategoryCode) {
    this.searchCategoryCode = searchCategoryCode;
  }

  /**
   * @return reviewScore
   */
  public String getReviewScore() {
    return reviewScore;
  }

  /**
   * @return searchCommodityCode
   */
  public String getSearchCommodityCode() {
    return searchCommodityCode;
  }

  /**
   * @param searchCommodityCode
   *          設定する searchCommodityCode
   */
  public void setSearchCommodityCode(String searchCommodityCode) {
    this.searchCommodityCode = searchCommodityCode;
  }

  /**
   * @return searchCommodityName
   */
  public String getSearchCommodityName() {
    return searchCommodityName;
  }

  /**
   * @param searchCommodityName
   *          設定する searchCommodityName
   */
  public void setSearchCommodityName(String searchCommodityName) {
    this.searchCommodityName = searchCommodityName;
  }

  /**
   * @return searchCustomerCode
   */
  public String getSearchCustomerCode() {
    return searchCustomerCode;
  }

  /**
   * @param searchCustomerCode
   *          設定する searchCustomerCode
   */
  public void setSearchCustomerCode(String searchCustomerCode) {
    this.searchCustomerCode = searchCustomerCode;
  }

  /**
   * @return searchPriceEnd
   */
  public String getSearchPriceEnd() {
    return searchPriceEnd;
  }

  /**
   * @param searchPriceEnd
   *          設定する searchPriceEnd
   */
  public void setSearchPriceEnd(String searchPriceEnd) {
    this.searchPriceEnd = searchPriceEnd;
  }

  /**
   * @return searchPriceStart
   */
  public String getSearchPriceStart() {
    return searchPriceStart;
  }

  /**
   * @param searchPriceStart
   *          設定する searchPriceStart
   */
  public void setSearchPriceStart(String searchPriceStart) {
    this.searchPriceStart = searchPriceStart;
  }

  /**
   * @param reviewScore
   *          設定する reviewScore
   */
  public void setReviewScore(String reviewScore) {
    this.reviewScore = reviewScore;
  }

  /**
   * @return searchShopCode
   */
  public String getSearchShopCode() {
    return searchShopCode;
  }

  /**
   * @param searchShopCode
   *          設定する searchShopCode
   */
  public void setSearchShopCode(String searchShopCode) {
    this.searchShopCode = searchShopCode;
  }

  /**
   * @return searchWord
   */
  public String getSearchWord() {
    return searchWord;
  }

  /**
   * @param searchWord
   *          設定する searchWord
   */
  public void setSearchWord(String searchWord) {
    this.searchWord = searchWord;
  }

  /**
   * @return searchMethod
   */
  public String getSearchMethod() {
    return searchMethod;
  }

  /**
   * @param searchMethod
   *          設定する searchMethod
   */
  public void setSearchMethod(String searchMethod) {
    this.searchMethod = searchMethod;
  }

  /**
   * @param alignmentSequence
   *          設定する alignmentSequence
   */
  public void setAlignmentSequence(String alignmentSequence) {
    this.alignmentSequence = alignmentSequence;
  }

  /**
   * @return alignmentSequence
   */
  public String getAlignmentSequence() {
    return alignmentSequence;
  }

  /**
   * searchCampaignCodeを取得します。
   * 
   * @return searchCampaignCode
   */
  public String getSearchCampaignCode() {
    return searchCampaignCode;
  }

  /**
   * searchCampaignCodeを設定します。
   * 
   * @param searchCampaignCode
   *          searchCampaignCode
   */
  public void setSearchCampaignCode(String searchCampaignCode) {
    this.searchCampaignCode = searchCampaignCode;
  }

  /**
   * @return searchDetailAttributeList
   */
  public List<SearchDetailAttributeList> getSearchDetailAttributeList() {
    return searchDetailAttributeList;
  }

  /**
   * @param searchDetailAttributeList
   *          設定する searchDetailAttributeList
   */
  public void setSearchDetailAttributeList(List<SearchDetailAttributeList> searchDetailAttributeList) {
    this.searchDetailAttributeList = searchDetailAttributeList;
  }

  /**
   * @return the byRepresent
   */
  public boolean isByRepresent() {
    return byRepresent;
  }

  /**
   * @param byRepresent
   *          the byRepresent to set
   */
  public void setByRepresent(boolean byRepresent) {
    this.byRepresent = byRepresent;
  }

  /**
   * @return the searchSkuCode
   */
  public String getSearchSkuCode() {
    return searchSkuCode;
  }

  /**
   * @param searchSkuCode
   *          the searchSkuCode to set
   */
  public void setSearchSkuCode(String searchSkuCode) {
    this.searchSkuCode = searchSkuCode;
  }

  /**
   * ignoreDeliveryDisplsayTypeを返します。
   * 
   * @return the ignoreDeliveryDisplsayType
   */
  public boolean isIgnoreDeliveryDisplsayType() {
    return ignoreDeliveryDisplsayType;
  }

  /**
   * ignoreDeliveryDisplsayTypeを設定します。
   * 
   * @param ignoreDeliveryDisplsayType
   *          設定する ignoreDeliveryDisplsayType
   */
  public void setIgnoreDeliveryDisplsayType(boolean ignoreDeliveryDisplsayType) {
    this.ignoreDeliveryDisplsayType = ignoreDeliveryDisplsayType;
  }

  /**
   * searchTagCodeを取得します。
   * 
   * @return searchTagCode
   */

  public String getSearchTagCode() {
    return searchTagCode;
  }

  /**
   * searchTagCodeを設定します。
   * 
   * @param searchTagCode
   *          searchTagCode
   */
  public void setSearchTagCode(String searchTagCode) {
    this.searchTagCode = searchTagCode;
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
   * @return the searchCategoryAttribute1
   */
  public String getSearchCategoryAttribute1() {
    return searchCategoryAttribute1;
  }

  /**
   * @param searchCategoryAttribute1
   *          the searchCategoryAttribute1 to set
   */
  public void setSearchCategoryAttribute1(String searchCategoryAttribute1) {
    this.searchCategoryAttribute1 = searchCategoryAttribute1;
  }

  /**
   * @return the searchCategoryAttribute2
   */
  public String getSearchCategoryAttribute2() {
    return searchCategoryAttribute2;
  }

  /**
   * @param searchCategoryAttribute2
   *          the searchCategoryAttribute2 to set
   */
  public void setSearchCategoryAttribute2(String searchCategoryAttribute2) {
    this.searchCategoryAttribute2 = searchCategoryAttribute2;
  }

  /**
   * @return the searchCategoryAttribute3
   */
  public String getSearchCategoryAttribute3() {
    return searchCategoryAttribute3;
  }

  /**
   * @param searchCategoryAttribute3
   *          the searchCategoryAttribute3 to set
   */
  public void setSearchCategoryAttribute3(String searchCategoryAttribute3) {
    this.searchCategoryAttribute3 = searchCategoryAttribute3;
  }

  /**
   * @return the searchListSort
   */
  public String getSearchListSort() {
    return searchListSort;
  }

  /**
   * @param searchListSort
   *          the searchListSort to set
   */
  public void setSearchListSort(String searchListSort) {
    this.searchListSort = searchListSort;
  }

  /**
   * @return the searchSaleFlag
   */
  public String getSearchSaleFlag() {
    return searchSaleFlag;
  }

  /**
   * @param searchSaleFlag
   *          the searchSaleFlag to set
   */
  public void setSearchSaleFlag(String searchSaleFlag) {
    this.searchSaleFlag = searchSaleFlag;
  }

  /**
   * @return the searchSpecFlag
   */
  public String getSearchSpecFlag() {
    return searchSpecFlag;
  }

  /**
   * @param searchSpecFlag
   *          the searchSpecFlag to set
   */
  public void setSearchSpecFlag(String searchSpecFlag) {
    this.searchSpecFlag = searchSpecFlag;
  }

  /**
   * @return the searchCartCommodityCode
   */
  public String[] getSearchCartCommodityCode() {
    return ArrayUtil.immutableCopy(searchCartCommodityCode);
  }

  /**
   * @param searchCartCommodityCode
   *          the searchCartCommodityCode to set
   */
  public void setSearchCartCommodityCode(String... searchCartCommodityCode) {
    this.searchCartCommodityCode = ArrayUtil.immutableCopy(searchCartCommodityCode);
  }

  /**
   * @return the searchPlanDetail
   */
  public PlanDetail getSearchPlanDetail() {
    return searchPlanDetail;
  }

  /**
   * @param searchPlanDetail
   *          the searchPlanDetail to set
   */
  public void setSearchPlanDetail(PlanDetail searchPlanDetail) {
    this.searchPlanDetail = searchPlanDetail;
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
   * @return the forHotSale
   */
  public String getForHotSale() {
    return forHotSale;
  }

  
  /**
   * @param forHotSale the forHotSale to set
   */
  public void setForHotSale(String forHotSale) {
    this.forHotSale = forHotSale;
  }

}
