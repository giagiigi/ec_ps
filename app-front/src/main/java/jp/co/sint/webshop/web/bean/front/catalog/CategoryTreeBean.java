package jp.co.sint.webshop.web.bean.front.catalog;

import java.util.List;

import jp.co.sint.webshop.service.catalog.CategoryData;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * カテゴリツリーのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CategoryTreeBean extends UISubBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String categoryCode;

  private String commodityCode;

  private String shopCode;  

  private boolean preview = false;

  private List<CategoryData> categoryList;

  private String brandCode;

  private String reviewScore;

  private String price;
  
  private String categoryAttribute1;

  private String alignmentSequence;

  private String pageSize;

  private String mode;

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    categoryCode = StringUtil.coalesce(reqparam.get("searchCategoryCode"), this.getCategoryCode());
    brandCode = StringUtil.coalesce(reqparam.get("searchBrandCode"), this.getBrandCode());
    reviewScore = StringUtil.coalesce(reqparam.get("searchReviewScore"), this.getReviewScore());
    price = StringUtil.coalesce(reqparam.get("searchPrice"), this.getPrice());
    categoryAttribute1 = StringUtil.coalesce(reqparam.get("searchCategoryAttribute1"), this.getCategoryAttribute1());
    alignmentSequence = StringUtil.coalesce(reqparam.get("alignmentSequence"), this.getAlignmentSequence());
    mode = StringUtil.coalesce(reqparam.get("mode"), this.getMode());
    pageSize = StringUtil.coalesce(reqparam.get("pageSize"), this.getPageSize());
  }

  /**
   * categoryCodeを取得します。
   * 
   * @return categoryCode
   */
  public String getCategoryCode() {
    return categoryCode;
  }

  /**
   * categoryCodeを設定します。
   * 
   * @param categoryCode
   *          categoryCode
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  /**
   * categoryListを取得します。
   * 
   * @return categoryList
   */
  public List<CategoryData> getCategoryList() {
    return categoryList;
  }

  /**
   * categoryListを設定します。
   * 
   * @param categoryList
   *          categoryList
   */
  public void setCategoryList(List<CategoryData> categoryList) {
    this.categoryList = categoryList;
  }

  /**
   * commodityCodeを取得します。
   * 
   * @return commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  
  /**
   * previewを取得します。
   *
   * @return preview
   */
  public boolean isPreview() {
    return preview;
  }

  
  /**
   * previewを設定します。
   *
   * @param preview 
   *          preview
   */
  public void setPreview(boolean preview) {
    this.preview = preview;
  }

  
  /**
   * @return the brandCode
   */
  public String getBrandCode() {
    return brandCode;
  }

  
  /**
   * @param brandCode the brandCode to set
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
   * @param reviewScore the reviewScore to set
   */
  public void setReviewScore(String reviewScore) {
    this.reviewScore = reviewScore;
  }

  
  /**
   * @return the price
   */
  public String getPrice() {
    return price;
  }

  
  /**
   * @param price the price to set
   */
  public void setPrice(String price) {
    this.price = price;
  }

  
  /**
   * @return the categoryAttribute1
   */
  public String getCategoryAttribute1() {
    return categoryAttribute1;
  }

  
  /**
   * @param categoryAttribute1 the categoryAttribute1 to set
   */
  public void setCategoryAttribute1(String categoryAttribute1) {
    this.categoryAttribute1 = categoryAttribute1;
  }


  public String getUrlStr() {
    String url = "";
    if (StringUtil.hasValue(brandCode)) {
      url += "B" + brandCode + "/";
    }
    if (StringUtil.hasValue(reviewScore)) {
      url += "K" + reviewScore + "/";
    }
    if (StringUtil.hasValue(price)) {
      url += "D" + price + "/";
    }
    if (StringUtil.hasValue(categoryAttribute1)) {
      url += "T" + categoryAttribute1 + "/";
    }
    return url;
  }

  
  /**
   * @return the alignmentSequence
   */
  public String getAlignmentSequence() {
    return alignmentSequence;
  }

  
  /**
   * @param alignmentSequence the alignmentSequence to set
   */
  public void setAlignmentSequence(String alignmentSequence) {
    this.alignmentSequence = alignmentSequence;
  }

  
  /**
   * @return the pageSize
   */
  public String getPageSize() {
    return pageSize;
  }

  
  /**
   * @param pageSize the pageSize to set
   */
  public void setPageSize(String pageSize) {
    this.pageSize = pageSize;
  }

  
  /**
   * @return the mode
   */
  public String getMode() {
    return mode;
  }

  
  /**
   * @param mode the mode to set
   */
  public void setMode(String mode) {
    this.mode = mode;
  }
}
