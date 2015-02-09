package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.service.SearchCondition;

public class BrandSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String searchBrandCode; 

  private String searchBrandName; 
  
  private String  searchBrandNameAbbr;
  
  private String searchBrandEnglishName; 
  
  //20120515 tuxinwei add start
  private String searchBrandJapaneseName; 
  //20120515 tuxinwei add end
  
  public String getSearchBrandName() {
    return searchBrandName;
  }

  public void setSearchBrandName(String searchBrandName) {
    this.searchBrandName = searchBrandName;
  }

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @param searchBrandCode the searchBrandCode to set
   */
  public void setSearchBrandCode(String searchBrandCode) {
    this.searchBrandCode = searchBrandCode;
  }

  /**
   * @return the searchBrandCode
   */
  public String getSearchBrandCode() {
    return searchBrandCode;
  }

  /**
   * @param searchBrandNameAbbr the searchBrandNameAbbr to set
   */
  public void setSearchBrandNameAbbr(String searchBrandNameAbbr) {
    this.searchBrandNameAbbr = searchBrandNameAbbr;
  }

  /**
   * @return the searchBrandNameAbbr
   */
  public String getSearchBrandNameAbbr() {
    return searchBrandNameAbbr;
  }

  /**
   * @param searchBrandEnglishName the searchBrandEnglishName to set
   */
  public void setSearchBrandEnglishName(String searchBrandEnglishName) {
    this.searchBrandEnglishName = searchBrandEnglishName;
  }

  /**
   * @return the searchBrandEnglishName
   */
  public String getSearchBrandEnglishName() {
    return searchBrandEnglishName;
  }

  
  /**
   * @return the searchBrandJapaneseName
   */
  public String getSearchBrandJapaneseName() {
    return searchBrandJapaneseName;
  }

  
  /**
   * @param searchBrandJapaneseName the searchBrandJapaneseName to set
   */
  public void setSearchBrandJapaneseName(String searchBrandJapaneseName) {
    this.searchBrandJapaneseName = searchBrandJapaneseName;
  }
  
}
