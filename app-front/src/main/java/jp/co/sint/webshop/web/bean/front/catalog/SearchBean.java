package jp.co.sint.webshop.web.bean.front.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition.SearchDetailAttributeList;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2040310:詳細検索のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class SearchBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<NameValue> searchMethodList = NameValue.asList("0:" + Messages.getString("web.bean.front.catalog.SearchBean.1") + "/1:" + Messages.getString("web.bean.front.catalog.SearchBean.2"));

  private List<SearchDetailAttributeList> categoryAttributeList = new ArrayList<SearchDetailAttributeList>();

  private List<CategoryAttributeListBean> categoryAttributeListBean = new ArrayList<CategoryAttributeListBean>();

  @Metadata(name = "AND/OR種別")
  private String searchMethod;

  @Metadata(name = "検索キーワード")
  private String searchWord;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード")
  private String searchCommodityCode;

  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "下限価格")
  private String searchPriceStart;

  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "上限価格")
  private String searchPriceEnd;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "カテゴリコード")
  private String searchCategoryCode;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード")
  private String searchShopCode;

  private String categoryList;

  private List<CodeAttribute> shopList;
  
  // 10.1.2 10089 追加 ここから
  private List<CategoryInfo> categoryNodeInfoList;
  // 10.1.2 10089 追加 ここまで

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    searchMethod = reqparam.get("searchMethod");
    searchWord = reqparam.get("searchWord");
    searchCommodityCode = reqparam.get("searchCommodityCode");
    searchCategoryCode = reqparam.get("searchCategoryCode");
    searchShopCode = reqparam.get("searchShopCode");
    searchPriceStart = reqparam.get("searchPriceStart");
    searchPriceEnd = reqparam.get("searchPriceEnd");

    String[] categoryAttributeNo = reqparam.getAll("categoryAttributeNo");
    String[] categoryAttributeValue = reqparam.getAll("categoryAttributeValue");
    categoryAttributeList = new ArrayList<SearchDetailAttributeList>();

    for (int i = 0; i < categoryAttributeNo.length; i++) {
      SearchDetailAttributeList attributeList = new SearchDetailAttributeList();
      attributeList.setCategoryAttributeNo(categoryAttributeNo[i]);
      attributeList.setCategoryAttributeValue(categoryAttributeValue[i]);
      categoryAttributeList.add(attributeList);
      // 10.1.6 10261 追加 ここから
      if (i < categoryAttributeListBean.size()) {
        categoryAttributeListBean.get(i).setCategoryAttributeValue(categoryAttributeValue[i]);
      }
      // 10.1.6 10261 追加 ここまで
    }
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2040310";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.catalog.SearchBean.0");
  }

  /**
   * U2040310:詳細検索のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CategoryAttributeListBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String categoryAttributeNo;

    private String categoryAttributeName;

    @Length(30)
    private String categoryAttributeValue;

    private boolean displayFlg;

    /**
     * categoryAttributeNameを取得します。
     * 
     * @return categoryAttributeName
     */
    public String getCategoryAttributeName() {
      return categoryAttributeName;
    }

    /**
     * categoryAttributeNameを設定します。
     * 
     * @param categoryAttributeName
     *          categoryAttributeName
     */
    public void setCategoryAttributeName(String categoryAttributeName) {
      this.categoryAttributeName = categoryAttributeName;
    }

    /**
     * categoryAttributeNoを取得します。
     * 
     * @return categoryAttributeNo
     */
    public String getCategoryAttributeNo() {
      return categoryAttributeNo;
    }

    /**
     * categoryAttributeNoを設定します。
     * 
     * @param categoryAttributeNo
     *          categoryAttributeNo
     */
    public void setCategoryAttributeNo(String categoryAttributeNo) {
      this.categoryAttributeNo = categoryAttributeNo;
    }

    /**
     * categoryAttributeValueを取得します。
     * 
     * @return categoryAttributeValue
     */
    public String getCategoryAttributeValue() {
      return categoryAttributeValue;
    }

    /**
     * categoryAttributeValueを設定します。
     * 
     * @param categoryAttributeValue
     *          categoryAttributeValue
     */
    public void setCategoryAttributeValue(String categoryAttributeValue) {
      this.categoryAttributeValue = categoryAttributeValue;
    }

    /**
     * displayFlgを取得します。
     * 
     * @return displayFlg
     */
    public boolean isDisplayFlg() {
      return displayFlg;
    }

    /**
     * displayFlgを設定します。
     * 
     * @param displayFlg
     *          displayFlg
     */
    public void setDisplayFlg(boolean displayFlg) {
      this.displayFlg = displayFlg;
    }

  }

  /**
   * categoryListを取得します。
   * 
   * @return categoryList
   */
  public String getCategoryList() {
    return categoryList;
  }

  /**
   * categoryListを設定します。
   * 
   * @param categoryList
   *          categoryList
   */
  public void setCategoryList(String categoryList) {
    this.categoryList = categoryList;
  }

  /**
   * searchCategoryCodeを取得します。
   * 
   * @return searchCategoryCode
   */
  public String getSearchCategoryCode() {
    return searchCategoryCode;
  }

  /**
   * searchCategoryCodeを設定します。
   * 
   * @param searchCategoryCode
   *          searchCategoryCode
   */
  public void setSearchCategoryCode(String searchCategoryCode) {
    this.searchCategoryCode = searchCategoryCode;
  }

  /**
   * searchCommodityCodeを取得します。
   * 
   * @return searchCommodityCode
   */
  public String getSearchCommodityCode() {
    return searchCommodityCode;
  }

  /**
   * searchCommodityCodeを設定します。
   * 
   * @param searchCommodityCode
   *          searchCommodityCode
   */
  public void setSearchCommodityCode(String searchCommodityCode) {
    this.searchCommodityCode = searchCommodityCode;
  }

  /**
   * searchPriceEndを取得します。
   * 
   * @return searchPriceEnd
   */
  public String getSearchPriceEnd() {
    return searchPriceEnd;
  }

  /**
   * searchPriceEndを設定します。
   * 
   * @param searchPriceEnd
   *          searchPriceEnd
   */
  public void setSearchPriceEnd(String searchPriceEnd) {
    this.searchPriceEnd = searchPriceEnd;
  }

  /**
   * searchPriceStartを取得します。
   * 
   * @return searchPriceStart
   */
  public String getSearchPriceStart() {
    return searchPriceStart;
  }

  /**
   * searchPriceStartを設定します。
   * 
   * @param searchPriceStart
   *          searchPriceStart
   */
  public void setSearchPriceStart(String searchPriceStart) {
    this.searchPriceStart = searchPriceStart;
  }

  /**
   * searchShopCodeを取得します。
   * 
   * @return searchShopCode
   */
  public String getSearchShopCode() {
    return searchShopCode;
  }

  /**
   * searchShopCodeを設定します。
   * 
   * @param searchShopCode
   *          searchShopCode
   */
  public void setSearchShopCode(String searchShopCode) {
    this.searchShopCode = searchShopCode;
  }

  /**
   * searchWordを取得します。
   * 
   * @return searchWord
   */
  public String getSearchWord() {
    return searchWord;
  }

  /**
   * searchWordを設定します。
   * 
   * @param searchWord
   *          searchWord
   */
  public void setSearchWord(String searchWord) {
    this.searchWord = searchWord;
  }

  /**
   * shopListを取得します。
   * 
   * @return shopList
   */
  public List<CodeAttribute> getShopList() {
    return shopList;
  }

  /**
   * shopListを設定します。
   * 
   * @param shopList
   *          shopList
   */
  public void setShopList(List<CodeAttribute> shopList) {
    this.shopList = shopList;
  }

  // 10.1.2 10089 追加 ここから
  /**
   * categoryNodeInfoListを取得します。
   *
   * @return categoryNodeInfoList categoryNodeInfoList
   */
  public List<CategoryInfo> getCategoryNodeInfoList() {
    return categoryNodeInfoList;
  }
  
  /**
   * categoryNodeInfoListを設定します。
   *
   * @param categoryNodeInfoList 
   *          categoryNodeInfoList
   */
  public void setCategoryNodeInfoList(List<CategoryInfo> categoryNodeInfoList) {
    this.categoryNodeInfoList = categoryNodeInfoList;
  }
  // 10.1.2 10089 追加 ここまで

  /**
   * searchMethodを取得します。
   * 
   * @return searchMethod
   */
  public String getSearchMethod() {
    return searchMethod;
  }

  /**
   * searchMethodを設定します。
   * 
   * @param searchMethod
   *          searchMethod
   */
  public void setSearchMethod(String searchMethod) {
    this.searchMethod = searchMethod;
  }

  /**
   * searchMethodListを取得します。
   * 
   * @return searchMethodList
   */
  public List<NameValue> getSearchMethodList() {
    return searchMethodList;
  }

  /**
   * searchMethodListを設定します。
   * 
   * @param searchMethodList
   *          searchMethodList
   */
  public void setSearchMethodList(List<NameValue> searchMethodList) {
    this.searchMethodList = searchMethodList;
  }

  /**
   * categoryAttributeListを取得します。
   * 
   * @return categoryAttributeList
   */
  public List<SearchDetailAttributeList> getCategoryAttributeList() {
    return categoryAttributeList;
  }

  /**
   * categoryAttributeListを設定します。
   * 
   * @param categoryAttributeList
   *          categoryAttributeList
   */
  public void setCategoryAttributeList(List<SearchDetailAttributeList> categoryAttributeList) {
    this.categoryAttributeList = categoryAttributeList;
  }

  /**
   * categoryAttributeListBeanを取得します。
   * 
   * @return categoryAttributeListBean
   */
  public List<CategoryAttributeListBean> getCategoryAttributeListBean() {
    return categoryAttributeListBean;
  }

  /**
   * categoryAttributeListBeanを設定します。
   * 
   * @param categoryAttributeListBean
   *          categoryAttributeListBean
   */
  public void setCategoryAttributeListBean(List<CategoryAttributeListBean> categoryAttributeListBean) {
    this.categoryAttributeListBean = categoryAttributeListBean;
  }

}
