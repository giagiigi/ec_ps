package jp.co.sint.webshop.web.bean.front.catalog;

import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2040210:カテゴリトップのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CategoryBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String contentsUrl;

  private String categoryCode;

  private String categoryName;

  // 20120305 shen add start
  private String metaKeyword;

  private String metaDescription;

  // 20120305 shen add end

  /**
   * categoryNameを取得します。
   * 
   * @return categoryName
   */
  public String getCategoryName() {
    return categoryName;
  }

  /**
   * categoryNameを設定します。
   * 
   * @param categoryName
   *          categoryName
   */
  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
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
   * contentsUrlを取得します。
   * 
   * @return contentsUrl
   */
  public String getContentsUrl() {
    return contentsUrl;
  }

  /**
   * contentsUrlを設定します。
   * 
   * @param contentsUrl
   *          contentsUrl
   */
  public void setContentsUrl(String contentsUrl) {
    this.contentsUrl = contentsUrl;
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
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    categoryCode = reqparam.get("searchCategoryCode");

  }

  /**
   * サブJSPを設定します。
   */
  public void setSubJspId() {
    super.setSubJspId();
    addSubJspId("/catalog/topic_path");
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2040210";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.catalog.CategoryBean.0");
  }

}
