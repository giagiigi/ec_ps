package jp.co.sint.webshop.web.bean.front.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2050410:サイトマップのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class SitemapBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CategoryInformation> categoryInfoList = new ArrayList<CategoryInformation>();

  private List<ShopInfo> shopInfoList = new ArrayList<ShopInfo>();

  private boolean complianceFlg;
  
  // 20120723 ysy add strat
  private String filePath;
  
  private String PageTopic;
  // 20120723 ysy add end
  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
    super.setSubJspId();
    addSubJspId("/catalog/campaign_info");
  }

  /**
   * U2050410:サイトマップのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CategoryInformation implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private UrlSet firstCategoryInfo = new UrlSet();

    private String categoryCode;

    private List<UrlSet> secondCategoryInfoList = new ArrayList<UrlSet>();

    /**
     * categoryCodeを取得します。
     * 
     * @return categoryCode
     */
    public String getCategoryCode() {
      return categoryCode;
    }

    /**
     * firstCategoryInfoを取得します。
     * 
     * @return firstCategoryInfo
     */
    public UrlSet getFirstCategoryInfo() {
      return firstCategoryInfo;
    }

    /**
     * secondCategoryInfoListを取得します。
     * 
     * @return secondCategoryInfoList
     */
    public List<UrlSet> getSecondCategoryInfoList() {
      return secondCategoryInfoList;
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
     * firstCategoryInfoを設定します。
     * 
     * @param firstCategoryInfo
     *          firstCategoryInfo
     */
    public void setFirstCategoryInfo(UrlSet firstCategoryInfo) {
      this.firstCategoryInfo = firstCategoryInfo;
    }

    /**
     * secondCategoryInfoListを設定します。
     * 
     * @param secondCategoryInfoList
     *          secondCategoryInfoList
     */
    public void setSecondCategoryInfoList(List<UrlSet> secondCategoryInfoList) {
      this.secondCategoryInfoList = secondCategoryInfoList;
    }
  }

  /**
   * U2050410:サイトマップのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class ShopInfo implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private UrlSet shopInfo = new UrlSet();

    private UrlSet shopCompliance = new UrlSet();

    private String logoUrl;

    private String shopCode;

    private String shopName;

    /**
     * shopComplianceを取得します。
     * 
     * @return shopCompliance
     */
    public UrlSet getShopCompliance() {
      return shopCompliance;
    }

    /**
     * shopComplianceを設定します。
     * 
     * @param shopCompliance
     *          shopCompliance
     */
    public void setShopCompliance(UrlSet shopCompliance) {
      this.shopCompliance = shopCompliance;
    }

    /**
     * logoUrlを取得します。
     * 
     * @return logoUrl
     */
    public String getLogoUrl() {
      return logoUrl;
    }

    /**
     * shopInfoを取得します。
     * 
     * @return shopInfo
     */
    public UrlSet getShopInfo() {
      return shopInfo;
    }

    /**
     * logoUrlを設定します。
     * 
     * @param logoUrl
     *          logoUrl
     */
    public void setLogoUrl(String logoUrl) {
      this.logoUrl = logoUrl;
    }

    /**
     * shopInfoを設定します。
     * 
     * @param shopInfo
     *          shopInfo
     */
    public void setShopInfo(UrlSet shopInfo) {
      this.shopInfo = shopInfo;
    }

    public String getShopCode() {
      return shopCode;
    }

    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    public String getShopName() {
      return shopName;
    }

    public void setShopName(String shopName) {
      this.shopName = shopName;
    }

  }

  /**
   * U2050410:サイトマップのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class UrlSet implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String name;

    private String url;

    /**
     * nameを取得します。
     * 
     * @return name
     */
    public String getName() {
      return name;
    }

    /**
     * urlを取得します。
     * 
     * @return url
     */
    public String getUrl() {
      return url;
    }

    /**
     * nameを設定します。
     * 
     * @param name
     *          name
     */
    public void setName(String name) {
      this.name = name;
    }

    /**
     * urlを設定します。
     * 
     * @param url
     *          url
     */
    public void setUrl(String url) {
      this.url = url;
    }

  }

  /**
   * categoryInfoListを取得します。
   * 
   * @return categoryInfoList
   */
  public List<CategoryInformation> getCategoryInfoList() {
    return categoryInfoList;
  }

  /**
   * shopInfoListを取得します。
   * 
   * @return shopInfoList
   */
  public List<ShopInfo> getShopInfoList() {
    return shopInfoList;
  }

  /**
   * categoryInfoListを設定します。
   * 
   * @param categoryInfoList
   *          categoryInfoList
   */
  public void setCategoryInfoList(List<CategoryInformation> categoryInfoList) {
    this.categoryInfoList = categoryInfoList;
  }

  /**
   * shopInfoListを設定します。
   * 
   * @param shopInfoList
   *          shopInfoList
   */
  public void setShopInfoList(List<ShopInfo> shopInfoList) {
    this.shopInfoList = shopInfoList;
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
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2050410";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.info.SitemapBean.0");
  }

  /**
   * complianceFlgを取得します。
   * 
   * @return complianceFlg
   */
  public boolean isComplianceFlg() {
    return complianceFlg;
  }

  /**
   * complianceFlgを設定します。
   * 
   * @param complianceFlg
   *          complianceFlg
   */
  public void setComplianceFlg(boolean complianceFlg) {
    this.complianceFlg = complianceFlg;
  }
  
  /**
   * @return the filePath
   */
  public String getFilePath() {
    return filePath;
  }

  
  /**
   * @param filePath the filePath to set
   */
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }
  
  /**
   * @return the pageTopic
   */
  public String getPageTopic() {
    return PageTopic;
  }

  
  /**
   * @param pageTopic the pageTopic to set
   */
  public void setPageTopic(String pageTopic) {
    PageTopic = pageTopic;
  }
  
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString("web.bean.front.info.SitemapBean.0"), "/siteinfo/guide"));
    if (! StringUtil.isNullOrEmpty(this.PageTopic)) {
      topicPath.add(new NameValue(this.PageTopic, ""));
    }
    return topicPath;
  }
}
