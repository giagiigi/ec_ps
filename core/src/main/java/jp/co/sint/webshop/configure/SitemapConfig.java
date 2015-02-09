package jp.co.sint.webshop.configure;

import java.io.Serializable;

public class SitemapConfig implements Serializable {

  /**
   * 网站地图参数配置
   */
  private static final long serialVersionUID = 1L;

  private String xmlnsValue;

  private String xmlnsImageValue;

  private String xmlnsVideoValue;

  private String xmlDir;

  private String xmlFileName1;

  private String xmlFileName2;

  private String xmlFileName3;

  private boolean discount = false;

  private boolean plan = false;

  private boolean category = false;

  private boolean brand = false;

  private boolean commodity = false;

  private String indexPriority;

  private String categoryPriority;

  private String brandPriority;

  private String commodityPriority;

  private String categoryChangefreq;

  private String brandChangefreq;

  private String commodityChangefreq;

  private String indexUrl;

  private String featuredUrl;

  private String categoryUrl;

  private String brandUrl;

  private String commodityUrl;

  private String xmlCode = "UTF-8";

  /**
   * @return the xmlnsValue
   */
  public String getXmlnsValue() {
    return xmlnsValue;
  }

  /**
   * @return the xmlnsImageValue
   */
  public String getXmlnsImageValue() {
    return xmlnsImageValue;
  }

  /**
   * @return the xmlnsVideoValue
   */
  public String getXmlnsVideoValue() {
    return xmlnsVideoValue;
  }

  /**
   * @return the xmlDir
   */
  public String getXmlDir() {
    return xmlDir;
  }

  /**
   * @return the xmlFileName1
   */
  public String getXmlFileName1() {
    return xmlFileName1;
  }

  /**
   * @return the xmlFileName2
   */
  public String getXmlFileName2() {
    return xmlFileName2;
  }

  /**
   * @return the xmlFileName3
   */
  public String getXmlFileName3() {
    return xmlFileName3;
  }

  /**
   * @return the discount
   */
  public boolean isDiscount() {
    return discount;
  }

  /**
   * @return the plan
   */
  public boolean isPlan() {
    return plan;
  }

  /**
   * @return the category
   */
  public boolean isCategory() {
    return category;
  }

  /**
   * @return the brand
   */
  public boolean isBrand() {
    return brand;
  }

  /**
   * @return the commodity
   */
  public boolean isCommodity() {
    return commodity;
  }

  /**
   * @return the indexPriority
   */
  public String getIndexPriority() {
    return indexPriority;
  }

  /**
   * @return the categoryPriority
   */
  public String getCategoryPriority() {
    return categoryPriority;
  }

  /**
   * @return the brandPriority
   */
  public String getBrandPriority() {
    return brandPriority;
  }

  /**
   * @return the commodityPriority
   */
  public String getCommodityPriority() {
    return commodityPriority;
  }

  /**
   * @return the categoryChangefreq
   */
  public String getCategoryChangefreq() {
    return categoryChangefreq;
  }

  /**
   * @return the brandChangefreq
   */
  public String getBrandChangefreq() {
    return brandChangefreq;
  }

  /**
   * @return the commodityChangefreq
   */
  public String getCommodityChangefreq() {
    return commodityChangefreq;
  }

  /**
   * @return the indexUrl
   */
  public String getIndexUrl() {
    return indexUrl;
  }

  /**
   * @return the featuredUrl
   */
  public String getFeaturedUrl() {
    return featuredUrl;
  }

  /**
   * @return the categoryUrl
   */
  public String getCategoryUrl() {
    return categoryUrl;
  }

  /**
   * @return the brandUrl
   */
  public String getBrandUrl() {
    return brandUrl;
  }

  /**
   * @return the commodityUrl
   */
  public String getCommodityUrl() {
    return commodityUrl;
  }

  /**
   * @param xmlnsValue
   *          the xmlnsValue to set
   */
  public void setXmlnsValue(String xmlnsValue) {
    this.xmlnsValue = xmlnsValue;
  }

  /**
   * @param xmlnsImageValue
   *          the xmlnsImageValue to set
   */
  public void setXmlnsImageValue(String xmlnsImageValue) {
    this.xmlnsImageValue = xmlnsImageValue;
  }

  /**
   * @param xmlnsVideoValue
   *          the xmlnsVideoValue to set
   */
  public void setXmlnsVideoValue(String xmlnsVideoValue) {
    this.xmlnsVideoValue = xmlnsVideoValue;
  }

  /**
   * @param xmlDir
   *          the xmlDir to set
   */
  public void setXmlDir(String xmlDir) {
    this.xmlDir = xmlDir;
  }

  /**
   * @param xmlFileName1
   *          the xmlFileName1 to set
   */
  public void setXmlFileName1(String xmlFileName1) {
    this.xmlFileName1 = xmlFileName1;
  }

  /**
   * @param xmlFileName2
   *          the xmlFileName2 to set
   */
  public void setXmlFileName2(String xmlFileName2) {
    this.xmlFileName2 = xmlFileName2;
  }

  /**
   * @param xmlFileName3
   *          the xmlFileName3 to set
   */
  public void setXmlFileName3(String xmlFileName3) {
    this.xmlFileName3 = xmlFileName3;
  }

  /**
   * @param discount
   *          the discount to set
   */
  public void setDiscount(boolean discount) {
    this.discount = discount;
  }

  /**
   * @param plan
   *          the plan to set
   */
  public void setPlan(boolean plan) {
    this.plan = plan;
  }

  /**
   * @param category
   *          the category to set
   */
  public void setCategory(boolean category) {
    this.category = category;
  }

  /**
   * @param brand
   *          the brand to set
   */
  public void setBrand(boolean brand) {
    this.brand = brand;
  }

  /**
   * @param commodity
   *          the commodity to set
   */
  public void setCommodity(boolean commodity) {
    this.commodity = commodity;
  }

  /**
   * @param indexPriority
   *          the indexPriority to set
   */
  public void setIndexPriority(String indexPriority) {
    this.indexPriority = indexPriority;
  }

  /**
   * @param categoryPriority
   *          the categoryPriority to set
   */
  public void setCategoryPriority(String categoryPriority) {
    this.categoryPriority = categoryPriority;
  }

  /**
   * @param brandPriority
   *          the brandPriority to set
   */
  public void setBrandPriority(String brandPriority) {
    this.brandPriority = brandPriority;
  }

  /**
   * @param commodityPriority
   *          the commodityPriority to set
   */
  public void setCommodityPriority(String commodityPriority) {
    this.commodityPriority = commodityPriority;
  }

  /**
   * @param categoryChangefreq
   *          the categoryChangefreq to set
   */
  public void setCategoryChangefreq(String categoryChangefreq) {
    this.categoryChangefreq = categoryChangefreq;
  }

  /**
   * @param brandChangefreq
   *          the brandChangefreq to set
   */
  public void setBrandChangefreq(String brandChangefreq) {
    this.brandChangefreq = brandChangefreq;
  }

  /**
   * @param commodityChangefreq
   *          the commodityChangefreq to set
   */
  public void setCommodityChangefreq(String commodityChangefreq) {
    this.commodityChangefreq = commodityChangefreq;
  }

  /**
   * @param indexUrl
   *          the indexUrl to set
   */
  public void setIndexUrl(String indexUrl) {
    this.indexUrl = indexUrl;
  }

  /**
   * @param featuredUrl
   *          the featuredUrl to set
   */
  public void setFeaturedUrl(String featuredUrl) {
    this.featuredUrl = featuredUrl;
  }

  /**
   * @param categoryUrl
   *          the categoryUrl to set
   */
  public void setCategoryUrl(String categoryUrl) {
    this.categoryUrl = categoryUrl;
  }

  /**
   * @param brandUrl
   *          the brandUrl to set
   */
  public void setBrandUrl(String brandUrl) {
    this.brandUrl = brandUrl;
  }

  /**
   * @param commodityUrl
   *          the commodityUrl to set
   */
  public void setCommodityUrl(String commodityUrl) {
    this.commodityUrl = commodityUrl;
  }

  
  /**
   * @return the xmlCode
   */
  public String getXmlCode() {
    return xmlCode;
  }

  
  /**
   * @param xmlCode the xmlCode to set
   */
  public void setXmlCode(String xmlCode) {
    this.xmlCode = xmlCode;
  }


}
