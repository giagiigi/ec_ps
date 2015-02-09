package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyEditBean.RegionBlockCharge;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060120:アンケートマスタのデータモデルです。
 * 
 * @author OB
 */
public class PublicCouponEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @AlphaNum2
  @Required
  @Length(16)
  @Metadata(name = "优惠券规则编号", order = 1)
  private String couponId;

  @Required
  @Length(40)
  @Metadata(name = "优惠券名称", order = 1)
  private String couponName;

  @Required
  @Digit
  @Metadata(name = "优惠券类别", order = 2)
  private String couponType;

  @Required
  @Digit
  @Metadata(name = "发行方式适用对象", order = 2)
  private String issuingMode;

  @Required
  @Digit
  @Length(1)
  @Metadata(name = "优惠券发行类别", order = 3)
  private String couponIssueType;

  @Length(10)
  @Metadata(name = "发行最小购买金额", order = 4)
  private String minIssueOrderAmount;

  @Required
  @Currency
  @Length(11)
  @Metadata(name = "优惠券数值", order = 7)
  private String couponAmount;

  @Required
  @Currency
  @Length(11)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "利用最小购买金额", order = 8)
  private String minUseOrderAmount;

  @Required
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "利用期间From", order = 9)
  private String minUseStartDatetime;

  @Required
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "利用期间To", order = 10)
  private String minUseEndDatetime;

  @Required
  @Digit
  @Length(8)
  @Metadata(name = "个人最大利用回数", order = 11)
  private String personalUseLimit;

  @Required
  @Digit
  @Length(8)
  @Metadata(name = "site最大利用回数", order = 12)
  private String siteUseLimit;

  @Length(200)
  @Metadata(name = "备注", order = 13)
  private String memo;

  @Currency
  @Length(11)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "利用最大购买金额", order = 14)
  private String maxUseOrderAmount;

  @Digit
  @Metadata(name = "关联对象使用类型", order = 15)
  private String useType;

  // 优惠券类别
  private String couponTypeName;

  private Date updateDatetime;

  private String objectCommodities = "";

  private String objectBrand = "";

  private String[] objectCommoditiesDetail;

  // 金额
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "优惠金额", order = 13)
  private String money;

  // 比例
  private String ratio;

  private List<CommoditiyDetailBean> commoditiyDetailBeanList = new ArrayList<CommoditiyDetailBean>();

  private List<BrandDetailBean> brandDetailBeanList = new ArrayList<BrandDetailBean>();

  private CommoditiyDetailBean commoditiyDetailBean = new CommoditiyDetailBean();

  private BrandDetailBean brandDetailBean = new BrandDetailBean();

  /** 优惠券类别列表 */
  private List<CodeAttribute> couponIssueTypeList = new ArrayList<CodeAttribute>();

  /** 地域区分集合 */
  private List<RegionBlockCharge> regionBlockChargeList = new ArrayList<RegionBlockCharge>();

  /** 选中的区域列表 */
  private List<String> checkedAreaBlockIdList = new ArrayList<String>();

  /** 选中的区域显示名称 */
  private List<String> checkedAreaDispalyNames = new ArrayList<String>();

  /** 选中的区域显示名称转换字符串 */
  private String checkedAreaDispalyAll;

  /** 页面状态 */
  private String pageFlg = "edit";

  /** 注册按钮状态 */
  private boolean displayRegistButtonFlg;

  /** 更新按钮状态 */
  private boolean displayUpdateButtonFlg;

  private boolean displayDeleteFlg;

  private String commodityNum;

  private List<String> checkedCode = new ArrayList<String>();

  private List<String> checkedBrandCode = new ArrayList<String>();

  // 20130927 txw add start
  private List<CategoryInfo> categoryList = new ArrayList<CategoryInfo>();

  private List<CategoryDetailBean> categoryDetailList;

  private CategoryDetailBean categoryDetailBean = new CategoryDetailBean();

  private List<String> checkedCategoryCode = new ArrayList<String>();
  
  public static class CategoryDetailBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Required
    @Metadata(name = "关联分类编号")
    private String categoryCode;

    private String categoryName;
    
    private String limitedNum;
    

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

    
    /**
     * @return the limitedNum
     */
    public String getLimitedNum() {
      return limitedNum;
    }

    
    /**
     * @param limitedNum the limitedNum to set
     */
    public void setLimitedNum(String limitedNum) {
      this.limitedNum = limitedNum;
    }

  }

  // 20130927 txw add end

  public static class CommoditiyDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Required
    @Length(16)
    @AlphaNum2
    @Metadata(name = "关联商品编号")
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

    private String brandCode;

    private String brandName;
    
    private String limitedNum;  

    /**
     * @return the brandCode
     */
    public String getBrandCode() {
      return brandCode;
    }

    /**
     * @param brandCode
     *          the brandCode to set
     */
    public void setBrandCode(String brandCode) {
      this.brandCode = brandCode;
    }

    /**
     * @return the brandName
     */
    public String getBrandName() {
      return brandName;
    }

    /**
     * @param brandName
     *          the brandName to set
     */
    public void setBrandName(String brandName) {
      this.brandName = brandName;
    }

    
    /**
     * @return the limitedNum
     */
    public String getLimitedNum() {
      return limitedNum;
    }

    
    /**
     * @param limitedNum the limitedNum to set
     */
    public void setLimitedNum(String limitedNum) {
      this.limitedNum = limitedNum;
    }

  }

  public List<String> getCheckedAreaBlockIdList() {
    return checkedAreaBlockIdList;
  }

  public void setCheckedAreaBlockIdList(List<String> checkedAreaBlockIdList) {
    this.checkedAreaBlockIdList = checkedAreaBlockIdList;
  }

  /**
   * サブJSPを設定します。
   */
  public void setSubJspId() {
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {

    reqparam.copy(this);

    this.setCouponType(reqparam.get("couponType"));

    // 设置优惠券利用开始日时
    this.setMinUseStartDatetime(reqparam.getDateTimeString("minUseStartDatetime"));
    // 设置优惠券利用结束日时
    this.setMinUseEndDatetime(reqparam.getDateTimeString("minUseEndDatetime"));

    this.setCheckedAreaBlockIdList(Arrays.asList(reqparam.getAll("areaId")));

    this.setCheckedCode(Arrays.asList(reqparam.getAll("checkBox")));

    this.setCheckedBrandCode(Arrays.asList(reqparam.getAll("checkBoxBrand")));

    this.getCommoditiyDetailBean().setCommodityCode(reqparam.get("relatedComdtyCode"));

    this.getBrandDetailBean().setBrandCode(reqparam.get("brandCode"));

    // 20130927 txw add start
    this.getCategoryDetailBean().setCategoryCode(reqparam.get("categoryCode"));

    this.setCheckedCategoryCode(Arrays.asList(reqparam.getAll("checkBoxCategory")));
    // 20130927 txw add end

    // 20140604 hdh add start
    this.getBrandDetailBean().setLimitedNum(reqparam.get("newBrandNum"));

    this.getCategoryDetailBean().setLimitedNum(reqparam.get("newCategoryNum"));
    // 20140604 hdh add end
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

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1060720";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.PublicCouponEditBean.0");
  }

  /**
   * 取得优惠券规则名称
   */
  public String getCouponName() {
    return couponName;
  }

  /**
   * 设置优惠券规则名称
   */
  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  /**
   * 设置发行方式适用对象名称
   */
  public void setIssuingMode(String issuingMode) {
    this.issuingMode = issuingMode;
  }

  /**
   * 取得发行方式适用对象名称
   */
  public String getIssuingMode() {
    return issuingMode;
  }

  /**
   * 取得优惠券类别
   */
  public String getCouponType() {
    return couponType;
  }

  /**
   * 设置优惠券类别
   */
  public void setCouponType(String couponType) {
    this.couponType = couponType;
  }

  /**
   * 取得优惠券发行类别
   */
  public String getCouponIssueType() {
    return couponIssueType;
  }

  /**
   * 设置优惠券发行类别
   */
  public void setCouponIssueType(String couponIssueType) {
    this.couponIssueType = couponIssueType;
  }

  /**
   *取得优惠券发行最小购买金额
   */
  public String getMinIssueOrderAmount() {
    return minIssueOrderAmount;
  }

  /**
   * 设置优惠券发行最小购买金额
   */
  public void setMinIssueOrderAmount(String minIssueOrderAmount) {
    this.minIssueOrderAmount = minIssueOrderAmount;
  }

  /**
   * 取得优惠券数值
   */
  public String getCouponAmount() {
    return couponAmount;
  }

  /**
   * 设置优惠券数值
   */
  public void setCouponAmount(String couponAmount) {
    this.couponAmount = couponAmount;
  }

  /**
   * 取得优惠券利用最小购买金额
   */
  public String getMinUseOrderAmount() {
    return minUseOrderAmount;
  }

  /**
   * 设置优惠券利用最小购买金额
   */
  public void setMinUseOrderAmount(String minUseOrderAmount) {
    this.minUseOrderAmount = minUseOrderAmount;
  }

  /**
   * 取得优惠券利用开始日时
   */
  public String getMinUseStartDatetime() {
    return minUseStartDatetime;
  }

  /**
   *设置优惠券利用开始日时
   */
  public void setMinUseStartDatetime(String minUseStartDatetime) {
    this.minUseStartDatetime = minUseStartDatetime;
  }

  /**
   * 取得优惠券利用结束日时
   */
  public String getMinUseEndDatetime() {
    return minUseEndDatetime;
  }

  /**
   * 设置优惠券利用结束日时
   */
  public void setMinUseEndDatetime(String minUseEndDatetime) {
    this.minUseEndDatetime = minUseEndDatetime;
  }

  /**
   * 取得个人最大利用回数
   */
  public String getPersonalUseLimit() {
    return personalUseLimit;
  }

  /**
   * 设置个人最大利用回数
   */
  public void setPersonalUseLimit(String personalUseLimit) {
    this.personalUseLimit = personalUseLimit;
  }

  /**
   * 取得SITE最大利用回数
   */
  public String getSiteUseLimit() {
    return siteUseLimit;
  }

  /**
   * 设置SITE最大利用回数
   */
  public void setSiteUseLimit(String siteUseLimit) {
    this.siteUseLimit = siteUseLimit;
  }

  /**
   * 取得注册按钮状态
   */
  public boolean isDisplayRegistButtonFlg() {
    return displayRegistButtonFlg;
  }

  /**
   * 设置注册按钮状态
   */
  public void setDisplayRegistButtonFlg(boolean displayRegistButtonFlg) {
    this.displayRegistButtonFlg = displayRegistButtonFlg;
  }

  /**
   * 取得更新按钮状态
   */
  public boolean isDisplayUpdateButtonFlg() {
    return displayUpdateButtonFlg;
  }

  /**
   * 设置更新按钮状态
   */
  public void setDisplayUpdateButtonFlg(boolean displayUpdateButtonFlg) {
    this.displayUpdateButtonFlg = displayUpdateButtonFlg;
  }

  /**
   * 取得优惠券规则编号
   */
  public String getCouponId() {
    return couponId;
  }

  /**
   * 设置优惠券规则编号
   */
  public void setCouponId(String couponId) {
    this.couponId = couponId;
  }

  public List<CodeAttribute> getCouponIssueTypeList() {
    return couponIssueTypeList;
  }

  public void setCouponIssueTypeList(List<CodeAttribute> couponIssueTypeList) {
    this.couponIssueTypeList = couponIssueTypeList;
  }

  public String getMoney() {
    return money;
  }

  public void setMoney(String money) {
    this.money = money;
  }

  public String getRatio() {
    return ratio;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public void setRatio(String ratio) {
    this.ratio = ratio;
  }

  public String getCouponTypeName() {
    return couponTypeName;
  }

  public void setCouponTypeName(String couponTypeName) {
    this.couponTypeName = couponTypeName;
  }

  public String getPageFlg() {
    return pageFlg;
  }

  public void setPageFlg(String pageFlg) {
    this.pageFlg = pageFlg;
  }

  public Date getUpdateDatetime() {
    return updateDatetime;
  }

  public void setUpdateDatetime(Date updateDatetime) {
    this.updateDatetime = updateDatetime;
  }

  public List<RegionBlockCharge> getRegionBlockChargeList() {
    return regionBlockChargeList;
  }

  public void setRegionBlockChargeList(List<RegionBlockCharge> regionBlockChargeList) {
    this.regionBlockChargeList = regionBlockChargeList;
  }

  public List<String> getCheckedAreaDispalyNames() {
    return checkedAreaDispalyNames;
  }

  public void setCheckedAreaDispalyNames(List<String> checkedAreaDispalyNames) {
    this.checkedAreaDispalyNames = checkedAreaDispalyNames;
  }

  /**
   * @return the objectCommodities
   */
  public String getObjectCommodities() {
    return objectCommodities;
  }

  /**
   * @param objectCommodities
   *          the objectCommodities to set
   */
  public void setObjectCommodities(String objectCommodities) {
    this.objectCommodities = objectCommodities;
  }

  /**
   * @return the objectCommoditiesDetail
   */
  public String[] getObjectCommoditiesDetail() {
    objectCommoditiesDetail = objectCommodities.split(";");
    return objectCommoditiesDetail;
  }

  /**
   * @param objectCommoditiesDetail
   *          the objectCommoditiesDetail to set
   */
  public void setObjectCommoditiesDetail(String[] objectCommoditiesDetail) {
    this.objectCommoditiesDetail = objectCommoditiesDetail;
  }

  /**
   * @return the displayDeleteFlg
   */
  public boolean isDisplayDeleteFlg() {
    return displayDeleteFlg;
  }

  /**
   * @param displayDeleteFlg
   *          the displayDeleteFlg to set
   */
  public void setDisplayDeleteFlg(boolean displayDeleteFlg) {
    this.displayDeleteFlg = displayDeleteFlg;
  }

  /**
   * @return the commoditiyDetailBeanList
   */
  public List<CommoditiyDetailBean> getCommoditiyDetailBeanList() {
    return commoditiyDetailBeanList;
  }

  /**
   * @param commoditiyDetailBeanList
   *          the commoditiyDetailBeanList to set
   */
  public void setCommoditiyDetailBeanList(List<CommoditiyDetailBean> commoditiyDetailBeanList) {
    this.commoditiyDetailBeanList = commoditiyDetailBeanList;
  }

  /**
   * @return the commodityNum
   */
  public String getCommodityNum() {
    return commodityNum;
  }

  /**
   * @param commodityNum
   *          the commodityNum to set
   */
  public void setCommodityNum(String commodityNum) {
    this.commodityNum = commodityNum;
  }

  /**
   * @return the commoditiyDetailBean
   */
  public CommoditiyDetailBean getCommoditiyDetailBean() {
    return commoditiyDetailBean;
  }

  /**
   * @param commoditiyDetailBean
   *          the commoditiyDetailBean to set
   */
  public void setCommoditiyDetailBean(CommoditiyDetailBean commoditiyDetailBean) {
    this.commoditiyDetailBean = commoditiyDetailBean;
  }

  /**
   * @return the checkedCode
   */
  public List<String> getCheckedCode() {
    return checkedCode;
  }

  /**
   * @param checkedCode
   *          the checkedCode to set
   */
  public void setCheckedCode(List<String> checkedCode) {
    this.checkedCode = checkedCode;
  }

  /**
   * @return the maxUseOrderAmount
   */
  public String getMaxUseOrderAmount() {
    return maxUseOrderAmount;
  }

  /**
   * @param maxUseOrderAmount
   *          the maxUseOrderAmount to set
   */
  public void setMaxUseOrderAmount(String maxUseOrderAmount) {
    this.maxUseOrderAmount = maxUseOrderAmount;
  }

  /**
   * @return the useType
   */
  public String getUseType() {
    return useType;
  }

  /**
   * @param useType
   *          the useType to set
   */
  public void setUseType(String useType) {
    this.useType = useType;
  }

  /**
   * @return the objectBrand
   */
  public String getObjectBrand() {
    return objectBrand;
  }

  /**
   * @param objectBrand
   *          the objectBrand to set
   */
  public void setObjectBrand(String objectBrand) {
    this.objectBrand = objectBrand;
  }

  /**
   * @return the brandDetailBeanList
   */
  public List<BrandDetailBean> getBrandDetailBeanList() {
    return brandDetailBeanList;
  }

  /**
   * @param brandDetailBeanList
   *          the brandDetailBeanList to set
   */
  public void setBrandDetailBeanList(List<BrandDetailBean> brandDetailBeanList) {
    this.brandDetailBeanList = brandDetailBeanList;
  }

  /**
   * @return the brandDetailBean
   */
  public BrandDetailBean getBrandDetailBean() {
    return brandDetailBean;
  }

  /**
   * @param brandDetailBean
   *          the brandDetailBean to set
   */
  public void setBrandDetailBean(BrandDetailBean brandDetailBean) {
    this.brandDetailBean = brandDetailBean;
  }

  /**
   * @return the checkedBrandCode
   */
  public List<String> getCheckedBrandCode() {
    return checkedBrandCode;
  }

  /**
   * @param checkedBrandCode
   *          the checkedBrandCode to set
   */
  public void setCheckedBrandCode(List<String> checkedBrandCode) {
    this.checkedBrandCode = checkedBrandCode;
  }

  /**
   * @return the categoryList
   */
  public List<CategoryInfo> getCategoryList() {
    return categoryList;
  }

  /**
   * @return the categortDetailList
   */
  public List<CategoryDetailBean> getCategoryDetailList() {
    return categoryDetailList;
  }

  /**
   * @return the categortDetailBean
   */
  public CategoryDetailBean getCategoryDetailBean() {
    return categoryDetailBean;
  }

  /**
   * @param categoryList
   *          the categoryList to set
   */
  public void setCategoryList(List<CategoryInfo> categoryList) {
    this.categoryList = categoryList;
  }

  /**
   * @param categortDetailList
   *          the categortDetailList to set
   */
  public void setCategoryDetailList(List<CategoryDetailBean> categoryDetailList) {
    this.categoryDetailList = categoryDetailList;
  }

  /**
   * @param categortDetailBean
   *          the categortDetailBean to set
   */
  public void setCategoryDetailBean(CategoryDetailBean categoryDetailBean) {
    this.categoryDetailBean = categoryDetailBean;
  }

  /**
   * @return the checkedCategoryCode
   */
  public List<String> getCheckedCategoryCode() {
    return checkedCategoryCode;
  }

  /**
   * @param checkedCategoryCode
   *          the checkedCategoryCode to set
   */
  public void setCheckedCategoryCode(List<String> checkedCategoryCode) {
    this.checkedCategoryCode = checkedCategoryCode;
  }

}
