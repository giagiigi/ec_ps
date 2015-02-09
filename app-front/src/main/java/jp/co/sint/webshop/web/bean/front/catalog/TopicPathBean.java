package jp.co.sint.webshop.web.bean.front.catalog;

import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * パンくずリストのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class TopicPathBean extends UISubBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String categoryCode;

  private String commodityCode;

  private String shopCode;

  private List<CodeAttribute> topicPathList;

  private String categoryName;
  
  private String categoryLast;
  
  private boolean preview = false;

  public void createAttributes(RequestParameter reqparam) {
    categoryCode = StringUtil.coalesce(reqparam.get("searchCategoryCode"), this.getCategoryCode());
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
   * topicPathListを取得します。
   * 
   * @return topicPathList
   */
  public List<CodeAttribute> getTopicPathList() {
    return topicPathList;
  }

  /**
   * topicPathListを設定します。
   * 
   * @param topicPathList
   *          topicPathList
   */
  public void setTopicPathList(List<CodeAttribute> topicPathList) {
    this.topicPathList = topicPathList;
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
   * @return the categoryLast
   */
  public String getCategoryLast() {
    return categoryLast;
  }

  
  /**
   * @param categoryLast the categoryLast to set
   */
  public void setCategoryLast(String categoryLast) {
    this.categoryLast = categoryLast;
  }

}
