package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060120:アンケートマスタのデータモデルです。
 * 
 * @author OB
 */
public class FriendCouponRuleEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Required
  @AlphaNum2
  @Length(8)
  @Metadata(name = "发行规则编号", order = 1)
  private String friendCouponRuleNo;

  @Required
  @Length(40)
  @Metadata(name = "发行规则中文名称", order = 2)
  private String friendCouponRuleCn;

  @Required
  @Length(40)
  @Metadata(name = "发行规则日文名称", order = 3)
  private String friendCouponRuleJp;

  @Required
  @Length(40)
  @Metadata(name = "发行规则英文名称", order = 4)
  private String friendCouponRuleEn;

  @Required
  @Length(1)
  @Metadata(name = "发行可能日期区分（0：每月 1：详细日期）", order = 5)
  private String issueDateType;

  @Length(2)
  @Metadata(name = "月份值", order = 6)
  private String issueDateNum;

  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "发行可能开始日期", order = 7)
  private String issueStartDate;

  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "发行可能结束日期", order = 8)
  private String issueEndDate;

  @Digit
  @Required
  @Length(8)
  @Metadata(name = "订购使用次数", order = 9)
  private String orderHistory;

  @Currency
  @Precision(precision = 10, scale = 2)
  @Length(10)
  @Metadata(name = "利用优惠券金额", order = 10)
  private String couponAmountNumeric;

  @Digit
  @Required
  @Length(8)
  @Metadata(name = "利用个人回数", order = 11)
  private String personalUseLimit;

  @Digit
  @Required
  @Length(8)
  @Metadata(name = "利用SITE回数", order = 12)
  private String siteUseLimit;

  @Currency
  @Precision(precision = 10, scale = 2)
  @Required
  @Length(10)
  @Metadata(name = "利用优惠券最小购买金额", order = 13)
  private String minUseOrderAmount;

  @Required
  @Length(1)
  @Metadata(name = "利用有效期类别（0：天，1：月）", order = 14)
  private String useValidType;

  @Digit
  @Required
  @Length(8)
  @Metadata(name = "利用有效期值", order = 15)
  private String useValidNum;

  @Required
  @Length(1)
  @Metadata(name = "利用限定（0:无限制 1:初次购买）", order = 16)
  private String applicableObjects;

  @Digit
  @Required
  @Length(8)
  @Metadata(name = "临界后获得积分", order = 17)
  private String obtainPoint;

  @Digit
  @Range(min = 0, max = 100)
  @Metadata(name = "比例", order = 18)
  private String ratio;

  @Digit
  @Required
  @Length(8)
  @Metadata(name = "发行获得积分", order = 19)
  private String issueObtainPoint;

  @Digit
  @Required
  @Length(8)
  @Metadata(name = "临界前获得积分", order = 20)
  private String formerUsePoint;

  @Digit
  @Required
  @Length(1)
  @Metadata(name = "优惠券种别", order = 22)
  private String couponIssueType;

  @Digit
  @Required
  @Length(8)
  @Metadata(name = "优惠临界人数", order = 23)
  private String couponUseNum;

  @Currency
  @Precision(precision = 10, scale = 2)
  @Length(10)
  @Metadata(name = "优惠券使用最大购买金额", order = 24)
  private String maxUseOrderAmount;

  @Required
  private String fixChar;
  
  @Required
  @Digit
  @Metadata(name = "关联对象使用类型", order = 25)
  private String useType;

  private boolean deleteButtonFlg;

  private PagerValue pagerValue = new PagerValue();

  private String displayMode;

  private String objectCommodities = "";

  private String objectBrand = "";

  private String[] objectCommoditiesDetail;

  private List<CommoditiyDetailBean> commoditiyDetailBeanList = new ArrayList<CommoditiyDetailBean>();

  private List<BrandDetailBean> brandDetailBeanList = new ArrayList<BrandDetailBean>();

  private CommoditiyDetailBean commoditiyDetailBean = new CommoditiyDetailBean();

  private BrandDetailBean brandDetailBean = new BrandDetailBean();

  /** 优惠券类别列表 */
  private List<CodeAttribute> couponIssueTypeList = new ArrayList<CodeAttribute>();

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
     * @param limitedNum
     *          the limitedNum to set
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
     * @param limitedNum
     *          the limitedNum to set
     */
    public void setLimitedNum(String limitedNum) {
      this.limitedNum = limitedNum;
    }

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

    // this.setCouponType(reqparam.get("couponType"));

    // 设置优惠券利用开始日时
    this.setIssueStartDate(reqparam.getDateTimeString("issueStartDate"));

    // 设置优惠券利用结束日时
    this.setIssueEndDate(reqparam.getDateTimeString("issueEndDate"));

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

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1061110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.FriendCouponRuleBean.0");
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

  public List<CodeAttribute> getCouponIssueTypeList() {
    return couponIssueTypeList;
  }

  public void setCouponIssueTypeList(List<CodeAttribute> couponIssueTypeList) {
    this.couponIssueTypeList = couponIssueTypeList;
  }

  public String getRatio() {
    return ratio;
  }

  public String getPageFlg() {
    return pageFlg;
  }

  public void setPageFlg(String pageFlg) {
    this.pageFlg = pageFlg;
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

  /**
   * @return the issueDateType
   */
  public String getIssueDateType() {
    return issueDateType;
  }

  /**
   * @param issueDateType
   *          the issueDateType to set
   */
  public void setIssueDateType(String issueDateType) {
    this.issueDateType = issueDateType;
  }

  /**
   * @return the issueDateNum
   */
  public String getIssueDateNum() {
    return issueDateNum;
  }

  /**
   * @param issueDateNum
   *          the issueDateNum to set
   */
  public void setIssueDateNum(String issueDateNum) {
    this.issueDateNum = issueDateNum;
  }

  /**
   * @return the issueStartDate
   */
  public String getIssueStartDate() {
    return issueStartDate;
  }

  /**
   * @param issueStartDate
   *          the issueStartDate to set
   */
  public void setIssueStartDate(String issueStartDate) {
    this.issueStartDate = issueStartDate;
  }

  /**
   * @return the issueEndDate
   */
  public String getIssueEndDate() {
    return issueEndDate;
  }

  /**
   * @param issueEndDate
   *          the issueEndDate to set
   */
  public void setIssueEndDate(String issueEndDate) {
    this.issueEndDate = issueEndDate;
  }

  /**
   * @return the orderHistory
   */
  public String getOrderHistory() {
    return orderHistory;
  }

  /**
   * @param orderHistory
   *          the orderHistory to set
   */
  public void setOrderHistory(String orderHistory) {
    this.orderHistory = orderHistory;
  }

  /**
   * @return the couponAmountNumeric
   */
  public String getCouponAmountNumeric() {
    return couponAmountNumeric;
  }

  /**
   * @param couponAmountNumeric
   *          the couponAmountNumeric to set
   */
  public void setCouponAmountNumeric(String couponAmountNumeric) {
    this.couponAmountNumeric = couponAmountNumeric;
  }

  /**
   * @return the useValidType
   */
  public String getUseValidType() {
    return useValidType;
  }

  /**
   * @param useValidType
   *          the useValidType to set
   */
  public void setUseValidType(String useValidType) {
    this.useValidType = useValidType;
  }

  /**
   * @return the useValidNum
   */
  public String getUseValidNum() {
    return useValidNum;
  }

  /**
   * @param useValidNum
   *          the useValidNum to set
   */
  public void setUseValidNum(String useValidNum) {
    this.useValidNum = useValidNum;
  }

  /**
   * @return the applicableObjects
   */
  public String getApplicableObjects() {
    return applicableObjects;
  }

  /**
   * @param applicableObjects
   *          the applicableObjects to set
   */
  public void setApplicableObjects(String applicableObjects) {
    this.applicableObjects = applicableObjects;
  }

  /**
   * @return the obtainPoint
   */
  public String getObtainPoint() {
    return obtainPoint;
  }

  /**
   * @param obtainPoint
   *          the obtainPoint to set
   */
  public void setObtainPoint(String obtainPoint) {
    this.obtainPoint = obtainPoint;
  }

  /**
   * @return the issueObtainPoint
   */
  public String getIssueObtainPoint() {
    return issueObtainPoint;
  }

  /**
   * @param issueObtainPoint
   *          the issueObtainPoint to set
   */
  public void setIssueObtainPoint(String issueObtainPoint) {
    this.issueObtainPoint = issueObtainPoint;
  }

  /**
   * @return the formerUsePoint
   */
  public String getFormerUsePoint() {
    return formerUsePoint;
  }

  /**
   * @param formerUsePoint
   *          the formerUsePoint to set
   */
  public void setFormerUsePoint(String formerUsePoint) {
    this.formerUsePoint = formerUsePoint;
  }

  /**
   * @return the couponUseNum
   */
  public String getCouponUseNum() {
    return couponUseNum;
  }

  /**
   * @param couponUseNum
   *          the couponUseNum to set
   */
  public void setCouponUseNum(String couponUseNum) {
    this.couponUseNum = couponUseNum;
  }

  /**
   * @return the fixChar
   */
  public String getFixChar() {
    return fixChar;
  }

  /**
   * @param fixChar
   *          the fixChar to set
   */
  public void setFixChar(String fixChar) {
    this.fixChar = fixChar;
  }

  /**
   * @return the deleteButtonFlg
   */
  public boolean isDeleteButtonFlg() {
    return deleteButtonFlg;
  }

  /**
   * @param deleteButtonFlg
   *          the deleteButtonFlg to set
   */
  public void setDeleteButtonFlg(boolean deleteButtonFlg) {
    this.deleteButtonFlg = deleteButtonFlg;
  }

  /**
   * @return the pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * @param pagerValue
   *          the pagerValue to set
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * @return the displayMode
   */
  public String getDisplayMode() {
    return displayMode;
  }

  /**
   * @param displayMode
   *          the displayMode to set
   */
  public void setDisplayMode(String displayMode) {
    this.displayMode = displayMode;
  }

  /**
   * @param ratio
   *          the ratio to set
   */
  public void setRatio(String ratio) {
    this.ratio = ratio;
  }

  /**
   * @return the friendCouponRuleNo
   */
  public String getFriendCouponRuleNo() {
    return friendCouponRuleNo;
  }

  /**
   * @param friendCouponRuleNo
   *          the friendCouponRuleNo to set
   */
  public void setFriendCouponRuleNo(String friendCouponRuleNo) {
    this.friendCouponRuleNo = friendCouponRuleNo;
  }

  /**
   * @return the friendCouponRuleCn
   */
  public String getFriendCouponRuleCn() {
    return friendCouponRuleCn;
  }

  /**
   * @param friendCouponRuleCn
   *          the friendCouponRuleCn to set
   */
  public void setFriendCouponRuleCn(String friendCouponRuleCn) {
    this.friendCouponRuleCn = friendCouponRuleCn;
  }

  /**
   * @return the friendCouponRuleJp
   */
  public String getFriendCouponRuleJp() {
    return friendCouponRuleJp;
  }

  /**
   * @param friendCouponRuleJp
   *          the friendCouponRuleJp to set
   */
  public void setFriendCouponRuleJp(String friendCouponRuleJp) {
    this.friendCouponRuleJp = friendCouponRuleJp;
  }

  /**
   * @return the friendCouponRuleEn
   */
  public String getFriendCouponRuleEn() {
    return friendCouponRuleEn;
  }

  /**
   * @param friendCouponRuleEn
   *          the friendCouponRuleEn to set
   */
  public void setFriendCouponRuleEn(String friendCouponRuleEn) {
    this.friendCouponRuleEn = friendCouponRuleEn;
  }

  
  /**
   * @return the useType
   */
  public String getUseType() {
    return useType;
  }

  
  /**
   * @param useType the useType to set
   */
  public void setUseType(String useType) {
    this.useType = useType;
  }

}
