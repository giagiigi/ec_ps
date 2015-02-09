package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.attribute.Url;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyEditBean.RegionBlockCharge;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;


public class FreePostageEditBean extends UIBackBean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Required
  @AlphaNum2
  @Length(16)
  @Metadata(name = "免邮促销编号", order = 1)
  private String freePostageCode;

  @Required
  @Length(50)
  @Metadata(name = "免邮促销名称", order = 2)
  private String freePostageName;

  @Required
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "免邮促销开始时间", order = 3)
  private String freeStartDate;

  @Required
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "免邮促销结束时间", order = 4)
  private String freeEndDate;

  @Digit
  @Metadata(name = "关联对象使用类型", order = 15)
  private String useType;

  @Required
  @Digit
  @Metadata(name = "会员限定", order = 15)
  private String applicableObject;

  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "重量限定", order = 8)
  private String weightLimit;

  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "最小购买金额", order = 8)
  private String minUseOrderAmount;

  /** 选中的区域显示名称转换字符串 */
  private String checkedAreaDispalyAll;

  /** 选中的区域显示名称 */
  private List<String> checkedAreaDispalyNames = new ArrayList<String>();

  /** 地域区分集合 */
  private List<RegionBlockCharge> regionBlockChargeList = new ArrayList<RegionBlockCharge>();

  /** 选中的区域列表 */
  private List<String> checkedAreaBlockIdList = new ArrayList<String>();

  private String displayMode;

  private String editMode;

  private boolean displayDeleteButton;

  private boolean displayRegisterButton;

  private boolean displayUpdateButton;

  @Metadata(name = "促销商品编号")
  private String relatedCommodityCode;

  private List<CommodityDetailBean> commodityDetailBeanList = new ArrayList<CommodityDetailBean>();

  private CommodityDetailBean commodityDetailBean = new CommodityDetailBean();

  @Metadata(name = "促销品牌编号")
  private String relatedBrandCode;

  private List<BrandDetailBean> brandDetailBeanList = new ArrayList<BrandDetailBean>();

  private BrandDetailBean brandDetailBean = new BrandDetailBean();

  @AlphaNum2
  @Length(16)
  @Metadata(name = "促销分类编号")
  private String relatedCategoryCode;

  private List<CategoryDetailBean> categoryDetailBeanList = new ArrayList<CategoryDetailBean>();

  private CategoryDetailBean categoryDetailBean = new CategoryDetailBean();

  @AlphaNum2
  @Length(20)
  @Metadata(name = "免邮代码发布")
  private String relatedIssueCode;

  private List<IssueDetailBean> issueDetailBeanList = new ArrayList<IssueDetailBean>();

  private IssueDetailBean issueDetailBean = new IssueDetailBean();

  private List<CategoryInfo> selectCategoryList = new ArrayList<CategoryInfo>();
  
  /** 目标Url */
  @Url
  @Length(100)
  @Metadata(name = "目标Url")
  private String targetUrl;

  public void setSubJspId() {

  }

  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
    setFreeStartDate(reqparam.getDateTimeString("freeStartDate"));
    setFreeEndDate(reqparam.getDateTimeString("freeEndDate"));
    this.setCheckedAreaBlockIdList(Arrays.asList(reqparam.getAll("areaId")));
  }

  public String getModuleId() {
    return "U1061310";
  }

  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.FreePostageEditBean.0");
  }

  /**
   * @return the freePostageCode
   */
  public String getFreePostageCode() {
    return freePostageCode;
  }

  /**
   * @return the freePostageName
   */
  public String getFreePostageName() {
    return freePostageName;
  }

  /**
   * @return the freeStartDate
   */
  public String getFreeStartDate() {
    return freeStartDate;
  }

  /**
   * @return the freeEndDate
   */
  public String getFreeEndDate() {
    return freeEndDate;
  }

  /**
   * @return the useType
   */
  public String getUseType() {
    return useType;
  }

  /**
   * @return the applicableObject
   */
  public String getApplicableObject() {
    return applicableObject;
  }

  /**
   * @return the weightLimit
   */
  public String getWeightLimit() {
    return weightLimit;
  }

  /**
   * @return the minUseOrderAmount
   */
  public String getMinUseOrderAmount() {
    return minUseOrderAmount;
  }

  /**
   * @return the displayMode
   */
  public String getDisplayMode() {
    return displayMode;
  }

  /**
   * @return the displayDeleteButton
   */
  public boolean isDisplayDeleteButton() {
    return displayDeleteButton;
  }

  /**
   * @return the displayRegisterButton
   */
  public boolean isDisplayRegisterButton() {
    return displayRegisterButton;
  }

  /**
   * @return the displayUpdateButton
   */
  public boolean isDisplayUpdateButton() {
    return displayUpdateButton;
  }

  /**
   * @param freePostageCode
   *          the freePostageCode to set
   */
  public void setFreePostageCode(String freePostageCode) {
    this.freePostageCode = freePostageCode;
  }

  /**
   * @param freePostageName
   *          the freePostageName to set
   */
  public void setFreePostageName(String freePostageName) {
    this.freePostageName = freePostageName;
  }

  /**
   * @param freeStartDate
   *          the freeStartDate to set
   */
  public void setFreeStartDate(String freeStartDate) {
    this.freeStartDate = freeStartDate;
  }

  /**
   * @param freeEndDate
   *          the freeEndDate to set
   */
  public void setFreeEndDate(String freeEndDate) {
    this.freeEndDate = freeEndDate;
  }

  /**
   * @param useType
   *          the useType to set
   */
  public void setUseType(String useType) {
    this.useType = useType;
  }

  /**
   * @param applicableObject
   *          the applicableObject to set
   */
  public void setApplicableObject(String applicableObject) {
    this.applicableObject = applicableObject;
  }

  /**
   * @param weightLimit
   *          the weightLimit to set
   */
  public void setWeightLimit(String weightLimit) {
    this.weightLimit = weightLimit;
  }

  /**
   * @param minUseOrderAmount
   *          the minUseOrderAmount to set
   */
  public void setMinUseOrderAmount(String minUseOrderAmount) {
    this.minUseOrderAmount = minUseOrderAmount;
  }

  /**
   * @param displayMode
   *          the displayMode to set
   */
  public void setDisplayMode(String displayMode) {
    this.displayMode = displayMode;
  }

  /**
   * @param displayDeleteButton
   *          the displayDeleteButton to set
   */
  public void setDisplayDeleteButton(boolean displayDeleteButton) {
    this.displayDeleteButton = displayDeleteButton;
  }

  /**
   * @param displayRegisterButton
   *          the displayRegisterButton to set
   */
  public void setDisplayRegisterButton(boolean displayRegisterButton) {
    this.displayRegisterButton = displayRegisterButton;
  }

  /**
   * @param displayUpdateButton
   *          the displayUpdateButton to set
   */
  public void setDisplayUpdateButton(boolean displayUpdateButton) {
    this.displayUpdateButton = displayUpdateButton;
  }

  public List<String> getCheckedAreaDispalyNames() {
    return checkedAreaDispalyNames;
  }

  public void setCheckedAreaDispalyNames(List<String> checkedAreaDispalyNames) {
    this.checkedAreaDispalyNames = checkedAreaDispalyNames;
  }

  public List<RegionBlockCharge> getRegionBlockChargeList() {
    return regionBlockChargeList;
  }

  public void setRegionBlockChargeList(List<RegionBlockCharge> regionBlockChargeList) {
    this.regionBlockChargeList = regionBlockChargeList;
  }

  public List<String> getCheckedAreaBlockIdList() {
    return checkedAreaBlockIdList;
  }

  public void setCheckedAreaBlockIdList(List<String> checkedAreaBlockIdList) {
    this.checkedAreaBlockIdList = checkedAreaBlockIdList;
  }

  public static class CommodityDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Required
    @Length(16)
    @AlphaNum2
    @Metadata(name = "促销商品编号")
    private String commodityCode;

    private String commodityName;

    /**
     * @return the commodityCode
     */
    public String getCommodityCode() {
      return commodityCode;
    }

    /**
     * @param commodityCode
     *          the commodityCode to set
     */
    public void setCommodityCode(String commodityCode) {
      this.commodityCode = commodityCode;
    }

    /**
     * @return the commodityName
     */
    public String getCommodityName() {
      return commodityName;
    }

    /**
     * @param commodityName
     *          the commodityName to set
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

  }

  public static class BrandDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Required
    @Length(16)
    @AlphaNum2
    @Metadata(name = "促销品牌编号")
    private String brandCode;

    private String brandName;

    /**
     * @return the brandCode
     */
    public String getBrandCode() {
      return brandCode;
    }

    /**
     * @return the brandName
     */
    public String getBrandName() {
      return brandName;
    }

    /**
     * @param brandCode
     *          the brandCode to set
     */
    public void setBrandCode(String brandCode) {
      this.brandCode = brandCode;
    }

    /**
     * @param brandName
     *          the brandName to set
     */
    public void setBrandName(String brandName) {
      this.brandName = brandName;
    }

  }

  public static class CategoryDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Required
    @Length(16)
    @AlphaNum2
    @Metadata(name = "促销分类编号")
    private String categoryCode;

    private String categoryName;

    /**
     * @return the categoryCode
     */
    public String getCategoryCode() {
      return categoryCode;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
      return categoryName;
    }

    /**
     * @param categoryCode
     *          the categoryCode to set
     */
    public void setCategoryCode(String categoryCode) {
      this.categoryCode = categoryCode;
    }

    /**
     * @param categoryName
     *          the categoryName to set
     */
    public void setCategoryName(String categoryName) {
      this.categoryName = categoryName;
    }

  }

  public static class IssueDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Required
    @Length(20)
    @AlphaNum2
    @Metadata(name = "促销发布编号")
    private String issueCode;
    
    @Metadata(name = "跟踪Url")
    private String trackUrl;

    /**
     * @return the issueCode
     */
    public String getIssueCode() {
      return issueCode;
    }

    /**
     * @param issueCode
     *          the issueCode to set
     */
    public void setIssueCode(String issueCode) {
      this.issueCode = issueCode;
    }

    
    /**
     * @return the trackUrl
     */
    public String getTrackUrl() {
      return trackUrl;
    }

    
    /**
     * @param trackUrl the trackUrl to set
     */
    public void setTrackUrl(String trackUrl) {
      this.trackUrl = trackUrl;
    }

  }

  /**
   * @return the commodityDetailBeanList
   */
  public List<CommodityDetailBean> getCommodityDetailBeanList() {
    return commodityDetailBeanList;
  }

  /**
   * @return the commodityDetailBean
   */
  public CommodityDetailBean getCommodityDetailBean() {
    return commodityDetailBean;
  }

  /**
   * @param commodityDetailBeanList
   *          the commodityDetailBeanList to set
   */
  public void setCommodityDetailBeanList(List<CommodityDetailBean> commodityDetailBeanList) {
    this.commodityDetailBeanList = commodityDetailBeanList;
  }

  /**
   * @param commodityDetailBean
   *          the commodityDetailBean to set
   */
  public void setCommodityDetailBean(CommodityDetailBean commodityDetailBean) {
    this.commodityDetailBean = commodityDetailBean;
  }

  /**
   * @return the editMode
   */
  public String getEditMode() {
    return editMode;
  }

  /**
   * @param editMode
   *          the editMode to set
   */
  public void setEditMode(String editMode) {
    this.editMode = editMode;
  }

  /**
   * @return the relatedCommodityCode
   */
  public String getRelatedCommodityCode() {
    return relatedCommodityCode;
  }

  /**
   * @param relatedCommodityCode
   *          the relatedCommodityCode to set
   */
  public void setRelatedCommodityCode(String relatedCommodityCode) {
    this.relatedCommodityCode = relatedCommodityCode;
  }

  /**
   * @return the relatedBrandCode
   */
  public String getRelatedBrandCode() {
    return relatedBrandCode;
  }

  /**
   * @return the brandDetailBeanList
   */
  public List<BrandDetailBean> getBrandDetailBeanList() {
    return brandDetailBeanList;
  }

  /**
   * @return the brandDetailBean
   */
  public BrandDetailBean getBrandDetailBean() {
    return brandDetailBean;
  }

  /**
   * @return the relatedCategoryCode
   */
  public String getRelatedCategoryCode() {
    return relatedCategoryCode;
  }

  /**
   * @return the categoryDetailBeanList
   */
  public List<CategoryDetailBean> getCategoryDetailBeanList() {
    return categoryDetailBeanList;
  }

  /**
   * @return the categoryDetailBean
   */
  public CategoryDetailBean getCategoryDetailBean() {
    return categoryDetailBean;
  }

  /**
   * @return the relatedIssueCode
   */
  public String getRelatedIssueCode() {
    return relatedIssueCode;
  }

  /**
   * @return the issueDetailBeanList
   */
  public List<IssueDetailBean> getIssueDetailBeanList() {
    return issueDetailBeanList;
  }

  /**
   * @return the issueDetailBean
   */
  public IssueDetailBean getIssueDetailBean() {
    return issueDetailBean;
  }

  /**
   * @param relatedBrandCode
   *          the relatedBrandCode to set
   */
  public void setRelatedBrandCode(String relatedBrandCode) {
    this.relatedBrandCode = relatedBrandCode;
  }

  /**
   * @param brandDetailBeanList
   *          the brandDetailBeanList to set
   */
  public void setBrandDetailBeanList(List<BrandDetailBean> brandDetailBeanList) {
    this.brandDetailBeanList = brandDetailBeanList;
  }

  /**
   * @param brandDetailBean
   *          the brandDetailBean to set
   */
  public void setBrandDetailBean(BrandDetailBean brandDetailBean) {
    this.brandDetailBean = brandDetailBean;
  }

  /**
   * @param relatedCategoryCode
   *          the relatedCategoryCode to set
   */
  public void setRelatedCategoryCode(String relatedCategoryCode) {
    this.relatedCategoryCode = relatedCategoryCode;
  }

  /**
   * @param categoryDetailBeanList
   *          the categoryDetailBeanList to set
   */
  public void setCategoryDetailBeanList(List<CategoryDetailBean> categoryDetailBeanList) {
    this.categoryDetailBeanList = categoryDetailBeanList;
  }

  /**
   * @param categoryDetailBean
   *          the categoryDetailBean to set
   */
  public void setCategoryDetailBean(CategoryDetailBean categoryDetailBean) {
    this.categoryDetailBean = categoryDetailBean;
  }

  /**
   * @param relatedIssueCode
   *          the relatedIssueCode to set
   */
  public void setRelatedIssueCode(String relatedIssueCode) {
    this.relatedIssueCode = relatedIssueCode;
  }

  /**
   * @param issueDetailBeanList
   *          the issueDetailBeanList to set
   */
  public void setIssueDetailBeanList(List<IssueDetailBean> issueDetailBeanList) {
    this.issueDetailBeanList = issueDetailBeanList;
  }

  /**
   * @param issueDetailBean
   *          the issueDetailBean to set
   */
  public void setIssueDetailBean(IssueDetailBean issueDetailBean) {
    this.issueDetailBean = issueDetailBean;
  }

  /**
   * @return the selectCategoryList
   */
  public List<CategoryInfo> getSelectCategoryList() {
    return selectCategoryList;
  }

  /**
   * @param selectCategoryList
   *          the selectCategoryList to set
   */
  public void setSelectCategoryList(List<CategoryInfo> selectCategoryList) {
    this.selectCategoryList = selectCategoryList;
  }
  
  /**
   * @return the targetUrl
   */
  public String getTargetUrl() {
    return targetUrl;
  }

  
  /**
   * @param targetUrl the targetUrl to set
   */
  public void setTargetUrl(String targetUrl) {
    this.targetUrl = targetUrl;
  }

  public String getCheckedAreaDispalyAll() {
    setCheckedAreaDispalyAll();
    return checkedAreaDispalyAll;
  }

  // 选中区域转换字符串
  private void setCheckedAreaDispalyAll() {
    checkedAreaDispalyAll = "";
    if (checkedAreaDispalyNames != null) {
      for (int i = 0; i < checkedAreaDispalyNames.size(); i++) {
        if (checkedAreaDispalyNames.size() - 1 > i) {
          checkedAreaDispalyAll = checkedAreaDispalyAll + checkedAreaDispalyNames.get(i) + ";";
        } else {
          checkedAreaDispalyAll = checkedAreaDispalyAll + checkedAreaDispalyNames.get(i);
        }
      }
    }
  }

}
