package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
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
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060620: PRIVATEクーポンマスタのデータモデルです。
 * 
 * @author OB.
 */
public class PrivateCouponEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 优惠券规则编号 */
  @Required
  @AlphaNum2
  @Metadata(name = "优惠券规则编号", order = 1)
  private String couponCode;

  /** 优惠券规则名称 */
  @Required
  @Length(40)
  @Metadata(name = "优惠券名称", order = 2)
  private String couponName;

  // add by cs_yuli 20120515 start
  /** 优惠券规则英文名称 */
  @Required
  @Length(40)
  @Metadata(name = "优惠券规则英文名称")
  private String couponNameEn;

  /** 优惠券规则日文名称 */
  @Required
  @Length(40)
  @Metadata(name = "优惠券规则日文名称")
  private String couponNameJp;

  // add by cs_yuli 20120515 end
  /** 优惠券发行方式（0:购买发行；1:特别会员发行；2:公共发行） */
  @Required
  @Metadata(name = "优惠券发行方式", order = 3)
  private String couponType;

  /** 优惠券发行类别（0:比例；1:固定金额） */
  @Required
  @Digit
  @Metadata(name = "发行类别", order = 4)
  private String campaignType;

  /** 优惠券金额 */
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "优惠券金额 ", order = 5)
  private String couponAmount;

  /** 优惠券比例 */
  @Length(3)
  @Digit
  @Range(min = 0, max = 100)
  @Metadata(name = "优惠券比例", order = 6)
  private String couponProportion;

  /** 发行期间From */
  @Required
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "发行期间From", order = 7)
  private String minIssueStartDatetime;

  /** 发行期间To */
  @Required
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "发行期间To", order = 8)
  private String minIssueEndDatetime;

  /** 优惠券发行最小购买金额 */
  @Required
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "发行最小购买金额 ", order = 9)
  private String minIssueOrderAmount;

  /** 利用期间From */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "利用期间From", order = 10)
  private String minUseStartDatetime;

  /** 利用期间To */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "利用期间To", order = 11)
  private String minUseEndDatetime;

  /** 优惠券利用最小购买金额 */
  @Required
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "利用最小购买金额 ", order = 12)
  private String minUseOrderAmount;

  /** 发行理由 */
  @Length(200)
  @Metadata(name = "发行理由", order = 13)
  private String issueReason;

  /** 备注 */
  @Length(200)
  @Metadata(name = "备注", order = 14)
  private String memo;

  // 2013/04/01 优惠券对应 ob add start
  // /** 优惠券发行方式适用类别（0:无限制；1:仅限初次购买使用） */
  // @Required
  // @Digit
  // @Metadata(name = "优惠券发行方式适用类别", order = 15)
  // private Long applicableObjects = 0L;

  /** 指定商品发行优惠券（1:有；0:无） */
  @Required
  @Digit
  @Metadata(name = "指定商品发行优惠券", order = 15)
  private String relatedCommodityFlg;

  /** 适用对象（0:无限制 1:初次购买） */
  @Required
  @Digit
  @Metadata(name = "适用对象", order = 16)
  private String applicableObject;

  /** 优惠券利用最大购买金额 */
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "利用最大购买金额 ", order = 17)
  private String maxUseOrderAmount;

  /** 优惠券发行金额类别（0:折扣前金额 1:折扣后金额） */
  @Required
  @Digit
  @Metadata(name = "优惠券发行金额类别", order = 18)
  private String beforeAfterDiscountType;

  /** 个人最大利用回数 */
  @Required
  @Length(8)
  @Digit
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "个人最大发行回数 ", order = 19)
  private String personalUseLimit;

  /** site最大利用回数 */
  @Required
  @Length(8)
  @Digit
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "site最大发行回数 ", order = 20)
  private String siteUseLimit;

  @Required
  @Length(2)
  @Digit
  @Metadata(name = "优惠券利用开始日 ", order = 21)
  private String minUseStartNum;

  @Required
  @Length(8)
  @Digit
  @Metadata(name = "优惠券使用期限 ", order = 22)
  private String minUseEndNum;

  /** 关联对象使用类型（0：可使用 1：不可使用） */
  @Digit
  @Metadata(name = "关联对象使用类型", order = 23)
  private String useType;

  @Metadata(name = "商品分类")
  private String categoryCode;

  @Metadata(name = "商品编号")
  private String lssueCommodityCode;

  private String useCommodityCode;

  @Metadata(name = "品牌")
  private String brandCodeAll;

  private String brandCode;

  private String categoryName;

  private String categoryPath;

  @Digit
  @Length(8)
  @Metadata(name = "积分数换取优惠券")
  private String exchangePointAmount;

  // 使用关联商品区分
  private List<String> importCommodityType = new ArrayList<String>();

  // 添加品牌List
  private List<BrandBean> brandList = new ArrayList<BrandBean>();

  // 所有品牌List
  private List<CodeAttribute> brandListAll = new ArrayList<CodeAttribute>();

  // 删除品牌List
  private List<CodeAttribute> brandListDelete = new ArrayList<CodeAttribute>();

  // 20130929 txw add start
  // 添加分类List
  private List<CategoryBean> categoryList = new ArrayList<CategoryBean>();

  @Metadata(name = "关联商品分类")
  private String relateCategoryCode;

  private List<CategoryInfo> selectCategoryList = new ArrayList<CategoryInfo>();

  // 所有分类List
  private List<CodeAttribute> categoryListAll = new ArrayList<CodeAttribute>();

  // 20130929 txw add end

  // 20131009 txw add start
  // 发行关联品牌明细
  private List<LssueBrandBean> lssueBrandList = new ArrayList<LssueBrandBean>();

  @Metadata(name = "发行品牌编号")
  private String lssueBrandCode;

  // 添加分类List
  private List<IssueCategoryBean> lssueCategoryList = new ArrayList<IssueCategoryBean>();

  @Metadata(name = "发行分类编号")
  private String lssueCategoryCode;

  // 20131009 txw add end

  // 删除关联商品List
  private List<CodeAttribute> lssueListDelete = new ArrayList<CodeAttribute>();

  // 删除使用商品List
  private List<CodeAttribute> useListDelete = new ArrayList<CodeAttribute>();

  // 使用关联商品区分List
  private List<CodeAttribute> importCommodityTypeList = new ArrayList<CodeAttribute>();

  // 发行关联商品明细
  private List<LssueCommodityBean> lssueList = new ArrayList<LssueCommodityBean>();

  // 使用关联商品明细
  private List<LssueCommodityBean> useList = new ArrayList<LssueCommodityBean>();

  // 2013/04/01 优惠券对应 ob add end
  /** 更新ボタン表示有無 */
  private boolean updateAuthorizeFlag = false;

  /** 新規登録ボタン表示有無 */
  private boolean registerNewDisplayFlag = false;

  /** 优惠券类别Flag */
  private int couponTypeFlag = 0;

  /*** 画面项目编辑状态 */
  private String couponDisplayMode = WebConstantCode.DISPLAY_EDIT;

  private List<CodeAttribute> campaignTypes = new ArrayList<CodeAttribute>();

  private List<NameValue> csvHeaderType = NameValue.asList("false:" + Messages.getString("web.bean.back.catalog.CategoryBean.2"));

  // 2013/04/01 优惠券对应 ob add start
  private List<CodeAttribute> relatedCommodityFlgs = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> applicableObjects = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> useFlagObjects = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> beforeAfterDiscountTypes = new ArrayList<CodeAttribute>();

  // 2013/04/01 优惠券对应 ob add end

  private boolean csvImportFlag = false;

  private Date updateDatetime;

  public List<CodeAttribute> getBrandListDelete() {
    return brandListDelete;
  }

  public void setBrandListDelete(List<CodeAttribute> brandListDelete) {
    this.brandListDelete = brandListDelete;
  }

  /**
   * couponCodeを取得します。
   * 
   * @return couponCode
   */
  public String getCouponCode() {
    return couponCode;
  }

  /**
   * couponCodeを設定します。
   * 
   * @param couponCode
   *          couponCode
   */
  public void setCouponCode(String couponCode) {
    this.couponCode = couponCode;
  }

  /**
   * couponNameを取得します。
   * 
   * @return couponName
   */
  public String getCouponName() {
    return couponName;
  }

  /**
   * couponNameを設定します。
   * 
   * @param couponName
   *          couponName
   */
  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  /**
   * couponTypeを取得します。
   * 
   * @return couponType
   */
  public String getCouponType() {
    return couponType;
  }

  /**
   * couponTypeを設定します。
   * 
   * @param couponType
   *          couponType
   */
  public void setCouponType(String couponType) {
    this.couponType = couponType;
  }

  /**
   * campaignTypeを取得します。
   * 
   * @return campaignType
   */
  public String getCampaignType() {
    return campaignType;
  }

  /**
   * campaignTypeを設定します。
   * 
   * @param campaignType
   *          campaignType
   */
  public void setCampaignType(String campaignType) {
    this.campaignType = campaignType;
  }

  /**
   * minIssueOrderAmountを取得します。
   * 
   * @return minIssueOrderAmount
   */
  public String getMinIssueOrderAmount() {
    return minIssueOrderAmount;
  }

  /**
   * minIssueOrderAmountを設定します。
   * 
   * @param minIssueOrderAmount
   *          minIssueOrderAmount
   */
  public void setMinIssueOrderAmount(String minIssueOrderAmount) {
    this.minIssueOrderAmount = minIssueOrderAmount;
  }

  /**
   * minIssueStartDatetimeを取得します。
   * 
   * @return minIssueStartDatetime
   */
  public String getMinIssueStartDatetime() {
    return minIssueStartDatetime;
  }

  /**
   * minIssueStartDatetimeを設定します。
   * 
   * @param minIssueStartDatetime
   *          minIssueStartDatetime
   */
  public void setMinIssueStartDatetime(String minIssueStartDatetime) {
    this.minIssueStartDatetime = minIssueStartDatetime;
  }

  /**
   * minIssueEndDatetimeを取得します。
   * 
   * @return minIssueEndDatetime
   */
  public String getMinIssueEndDatetime() {
    return minIssueEndDatetime;
  }

  /**
   * minIssueEndDatetimeを設定します。
   * 
   * @param minIssueEndDatetime
   *          minIssueEndDatetime
   */
  public void setMinIssueEndDatetime(String minIssueEndDatetime) {
    this.minIssueEndDatetime = minIssueEndDatetime;
  }

  /**
   * couponAmountを取得します。
   * 
   * @return couponAmount
   */
  public String getCouponAmount() {
    return couponAmount;
  }

  /**
   * couponAmountを設定します。
   * 
   * @param couponAmount
   *          couponAmount
   */
  public void setCouponAmount(String couponAmount) {
    this.couponAmount = couponAmount;
  }

  /**
   * couponProportionを取得します。
   * 
   * @return couponProportion
   */
  public String getCouponProportion() {
    return couponProportion;
  }

  /**
   * couponProportionを設定します。
   * 
   * @param couponProportion
   *          couponProportion
   */
  public void setCouponProportion(String couponProportion) {
    this.couponProportion = couponProportion;
  }

  /**
   * minUseOrderAmountを取得します。
   * 
   * @return minUseOrderAmount
   */
  public String getMinUseOrderAmount() {
    return minUseOrderAmount;
  }

  /**
   * minUseOrderAmountを設定します。
   * 
   * @param minUseOrderAmount
   *          minUseOrderAmount
   */
  public void setMinUseOrderAmount(String minUseOrderAmount) {
    this.minUseOrderAmount = minUseOrderAmount;
  }

  /**
   * issueReasonを取得します。
   * 
   * @return issueReason
   */
  public String getIssueReason() {
    return issueReason;
  }

  /**
   * issueReasonを設定します。
   * 
   * @param issueReason
   *          issueReason
   */
  public void setIssueReason(String issueReason) {
    this.issueReason = issueReason;
  }

  /**
   * memoを取得します。
   * 
   * @return memo
   */
  public String getMemo() {
    return memo;
  }

  /**
   * memoを設定します。
   * 
   * @param memo
   *          memo
   */
  public void setMemo(String memo) {
    this.memo = memo;
  }

  /**
   * minUseStartDatetimeを取得します。
   * 
   * @return minUseStartDatetime
   */
  public String getMinUseStartDatetime() {
    return minUseStartDatetime;
  }

  /**
   * minUseStartDatetimeを設定します。
   * 
   * @param minUseStartDatetime
   *          minUseStartDatetime
   */
  public void setMinUseStartDatetime(String minUseStartDatetime) {
    this.minUseStartDatetime = minUseStartDatetime;
  }

  /**
   * minUseEndDatetimeを取得します。
   * 
   * @return minUseEndDatetime
   */
  public String getMinUseEndDatetime() {
    return minUseEndDatetime;
  }

  /**
   * minUseEndDatetimeを設定します。
   * 
   * @param minUseEndDatetime
   *          minUseEndDatetime
   */
  public void setMinUseEndDatetime(String minUseEndDatetime) {
    this.minUseEndDatetime = minUseEndDatetime;
  }

  /**
   * updateAuthorizeFlagを取得します。
   * 
   * @return updateAuthorizeFlag
   */
  public boolean isUpdateAuthorizeFlag() {
    return updateAuthorizeFlag;
  }

  /**
   * updateAuthorizeFlagを設定します。
   * 
   * @param updateAuthorizeFlag
   *          updateAuthorizeFlag
   */
  public void setUpdateAuthorizeFlag(boolean updateAuthorizeFlag) {
    this.updateAuthorizeFlag = updateAuthorizeFlag;
  }

  /**
   * registerNewDisplayFlagを取得します。
   * 
   * @return registerNewDisplayFlag
   */
  public boolean isRegisterNewDisplayFlag() {
    return registerNewDisplayFlag;
  }

  /**
   * registerNewDisplayFlagを設定します。
   * 
   * @param registerNewDisplayFlag
   *          registerNewDisplayFlag
   */
  public void setRegisterNewDisplayFlag(boolean registerNewDisplayFlag) {
    this.registerNewDisplayFlag = registerNewDisplayFlag;
  }

  /**
   * couponTypeFlagを取得します。
   * 
   * @return couponTypeFlag
   */
  public int getCouponTypeFlag() {
    return couponTypeFlag;
  }

  /**
   * couponTypeFlagを設定します。
   * 
   * @param couponTypeFlag
   *          couponTypeFlag
   */
  public void setCouponTypeFlag(int couponTypeFlag) {
    this.couponTypeFlag = couponTypeFlag;
  }

  /**
   * couponDisplayModeを取得します。
   * 
   * @return couponDisplayMode
   */
  public String getCouponDisplayMode() {
    return couponDisplayMode;
  }

  /**
   * couponDisplayModeを設定します。
   * 
   * @param couponDisplayMode
   *          couponDisplayMode
   */
  public void setCouponDisplayMode(String couponDisplayMode) {
    this.couponDisplayMode = couponDisplayMode;
  }

  /**
   * campaignTypesを取得します。
   * 
   * @return campaignTypes
   */
  public List<CodeAttribute> getCampaignTypes() {
    return campaignTypes;
  }

  /**
   * campaignTypesを設定します。
   * 
   * @param campaignTypes
   *          campaignTypes
   */
  public void setCampaignTypes(List<CodeAttribute> campaignTypes) {
    this.campaignTypes = campaignTypes;
  }

  /**
   * csvHeaderTypeを取得します。
   * 
   * @return csvHeaderType
   */
  public List<NameValue> getCsvHeaderType() {
    return csvHeaderType;
  }

  /**
   * csvHeaderTypeを設定します。
   * 
   * @param csvHeaderType
   *          csvHeaderType
   */
  public void setCsvHeaderType(List<NameValue> csvHeaderType) {
    this.csvHeaderType = csvHeaderType;
  }

  /**
   * csvImportFlagを取得します。
   * 
   * @return csvImportFlag
   */
  public boolean isCsvImportFlag() {
    return csvImportFlag;
  }

  /**
   * csvImportFlagを設定します。
   * 
   * @param csvImportFlag
   *          csvImportFlag
   */
  public void setCsvImportFlag(boolean csvImportFlag) {
    this.csvImportFlag = csvImportFlag;
  }

  /**
   * updateDatetimeを取得します。
   * 
   * @return updateDatetime
   */
  public Date getUpdateDatetime() {
    return updateDatetime;
  }

  /**
   * updateDatetimeを設定します。
   * 
   * @param updateDatetime
   *          updateDatetime
   */
  public void setUpdateDatetime(Date updateDatetime) {
    this.updateDatetime = updateDatetime;
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    // 2013/04/01 优惠券对应 ob add start
    reqparam.copy(this);
    this.getBrandListDelete().clear();
    String[] array = reqparam.getAll("brandCode");
    if (array != null && array.length > 0) {
      for (int i = 0; i < array.length; i++) {
        if (!StringUtil.isNullOrEmpty(array[i])) {
          this.getBrandListDelete().add(new NameValue("", array[i]));
        }
      }
    }
    // 2013/04/01 优惠券对应 ob add end
    setCouponCode(reqparam.get("couponCode"));
    setCouponName(reqparam.get("couponName"));
    setCouponNameEn(reqparam.get("couponNameEn"));
    setCouponNameJp(reqparam.get("couponNameJp"));
    setCouponType(reqparam.get("couponType"));
    setCouponAmount(reqparam.get("couponAmount"));
    setCouponProportion(reqparam.get("couponProportion"));
    setCampaignType(reqparam.get("campaignType"));
    setMinIssueStartDatetime(reqparam.getDateTimeString("minIssueStartDatetime"));
    setMinIssueEndDatetime(reqparam.getDateTimeString("minIssueEndDatetime"));
    setMinIssueOrderAmount(reqparam.get("minIssueOrderAmount"));
    setMinUseStartDatetime(reqparam.getDateTimeString("minUseStartDatetime"));
    setMinUseEndDatetime(reqparam.getDateTimeString("minUseEndDatetime"));
    setMinUseOrderAmount(reqparam.get("minUseOrderAmount"));
    setIssueReason(reqparam.get("issueReason"));
    setMemo(reqparam.get("memo"));
    // 2013/04/01 优惠券对应 ob add start
    setRelatedCommodityFlg(reqparam.get("relatedCommodityFlg"));
    setApplicableObject(reqparam.get("applicableObject"));
    setMaxUseOrderAmount(reqparam.get("maxUseOrderAmount"));
    setBeforeAfterDiscountType(reqparam.get("beforeAfterDiscountType"));
    setPersonalUseLimit(reqparam.get("personalUseLimit"));
    setSiteUseLimit(reqparam.get("siteUseLimit"));
    setLssueCommodityCode(reqparam.get("lssueCommodityCode").trim());
    setUseCommodityCode(reqparam.get("useCommodityCode").trim());
    setUseCommodityCode(reqparam.get("useCommodityCode").trim());
    setBrandCodeAll(reqparam.get("brandCodeAll"));
    setBrandCode(reqparam.get("brandCode"));
    setMinUseStartNum(reqparam.get("minUseStartNum"));
    setMinUseEndNum(reqparam.get("minUseEndNum"));
    // 2013/04/01 优惠券对应 ob add end
    // 优惠券兑换积分数
    setExchangePointAmount(reqparam.get("exchangePointAmount"));
    // 20130929 txw add start
    setRelateCategoryCode(reqparam.get("relateCategoryCode"));

    setLssueBrandCode(reqparam.get("lssueBrandCode").trim());

    setLssueCategoryCode(reqparam.get("lssueCategoryCode"));
    // 20130929 txw add end
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1060620";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.PrivateCouponEditBean.0");
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {

  }

  /**
   * @param couponNameEn
   *          the couponNameEn to set
   */
  public void setCouponNameEn(String couponNameEn) {
    this.couponNameEn = couponNameEn;
  }

  /**
   * @return the couponNameEn
   */
  public String getCouponNameEn() {
    return couponNameEn;
  }

  /**
   * @param couponNameJp
   *          the couponNameJp to set
   */
  public void setCouponNameJp(String couponNameJp) {
    this.couponNameJp = couponNameJp;
  }

  /**
   * @return the couponNameJp
   */
  public String getCouponNameJp() {
    return couponNameJp;
  }

  public String getRelatedCommodityFlg() {
    return relatedCommodityFlg;
  }

  public void setRelatedCommodityFlg(String relatedCommodityFlg) {
    this.relatedCommodityFlg = relatedCommodityFlg;
  }

  public String getApplicableObject() {
    return applicableObject;
  }

  public void setApplicableObject(String applicableObject) {
    this.applicableObject = applicableObject;
  }

  public String getMaxUseOrderAmount() {
    return maxUseOrderAmount;
  }

  public void setMaxUseOrderAmount(String maxUseOrderAmount) {
    this.maxUseOrderAmount = maxUseOrderAmount;
  }

  public String getBeforeAfterDiscountType() {
    return beforeAfterDiscountType;
  }

  public void setBeforeAfterDiscountType(String beforeAfterDiscountType) {
    this.beforeAfterDiscountType = beforeAfterDiscountType;
  }

  public String getPersonalUseLimit() {
    return personalUseLimit;
  }

  public void setPersonalUseLimit(String personalUseLimit) {
    this.personalUseLimit = personalUseLimit;
  }

  public String getSiteUseLimit() {
    return siteUseLimit;
  }

  public void setSiteUseLimit(String siteUseLimit) {
    this.siteUseLimit = siteUseLimit;
  }

  public List<CodeAttribute> getRelatedCommodityFlgs() {
    return relatedCommodityFlgs;
  }

  public void setRelatedCommodityFlgs(List<CodeAttribute> relatedCommodityFlgs) {
    this.relatedCommodityFlgs = relatedCommodityFlgs;
  }

  public List<CodeAttribute> getApplicableObjects() {
    return applicableObjects;
  }

  public void setApplicableObjects(List<CodeAttribute> applicableObjects) {
    this.applicableObjects = applicableObjects;
  }

  public List<CodeAttribute> getBeforeAfterDiscountTypes() {
    return beforeAfterDiscountTypes;
  }

  public void setBeforeAfterDiscountTypes(List<CodeAttribute> beforeAfterDiscountTypes) {
    this.beforeAfterDiscountTypes = beforeAfterDiscountTypes;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getCategoryPath() {
    return categoryPath;
  }

  public void setCategoryPath(String categoryPath) {
    this.categoryPath = categoryPath;
  }

  public String getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  public String getBrandCodeAll() {
    return brandCodeAll;
  }

  public void setBrandCodeAll(String brandCodeAll) {
    this.brandCodeAll = brandCodeAll;
  }

  public List<BrandBean> getBrandList() {
    return brandList;
  }

  public void setBrandList(List<BrandBean> brandList) {
    this.brandList = brandList;
  }

  public String getLssueCommodityCode() {
    return lssueCommodityCode;
  }

  public void setLssueCommodityCode(String lssueCommodityCode) {
    this.lssueCommodityCode = lssueCommodityCode;
  }

  public String getUseCommodityCode() {
    return useCommodityCode;
  }

  public void setUseCommodityCode(String useCommodityCode) {
    this.useCommodityCode = useCommodityCode;
  }

  public List<CodeAttribute> getBrandListAll() {
    return brandListAll;
  }

  public void setBrandListAll(List<CodeAttribute> brandListAll) {
    this.brandListAll = brandListAll;
  }

  public String getBrandCode() {
    return brandCode;
  }

  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  public List<String> getImportCommodityType() {
    return importCommodityType;
  }

  public void setImportCommodityType(List<String> importCommodityType) {
    this.importCommodityType = importCommodityType;
  }

  public List<CodeAttribute> getImportCommodityTypeList() {
    return importCommodityTypeList;
  }

  public void setImportCommodityTypeList(List<CodeAttribute> importCommodityTypeList) {
    this.importCommodityTypeList = importCommodityTypeList;
  }

  public static class LssueCommodityBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String commodityCode;

    private String commodityName;

    private String displayOrder;

    private BigDecimal discountPrice;

    private String discountPriceStartDatetime;

    private String discountPriceEndDatetime;

    private boolean dateOut = true;

    public String getCommodityCode() {
      return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
      this.commodityCode = commodityCode;
    }

    public String getCommodityName() {
      return commodityName;
    }

    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    public String getDisplayOrder() {
      return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
      this.displayOrder = displayOrder;
    }

    /**
     * @return the discountPrice
     */
    public BigDecimal getDiscountPrice() {
      return discountPrice;
    }

    /**
     * @param discountPrice
     *          the discountPrice to set
     */
    public void setDiscountPrice(BigDecimal discountPrice) {
      this.discountPrice = discountPrice;
    }

    /**
     * @return the discountPriceStartDatetime
     */
    public String getDiscountPriceStartDatetime() {
      return discountPriceStartDatetime;
    }

    /**
     * @param discountPriceStartDatetime
     *          the discountPriceStartDatetime to set
     */
    public void setDiscountPriceStartDatetime(String discountPriceStartDatetime) {
      this.discountPriceStartDatetime = discountPriceStartDatetime;
    }

    /**
     * @return the discountPriceEndDatetime
     */
    public String getDiscountPriceEndDatetime() {
      return discountPriceEndDatetime;
    }

    /**
     * @param discountPriceEndDatetime
     *          the discountPriceEndDatetime to set
     */
    public void setDiscountPriceEndDatetime(String discountPriceEndDatetime) {
      this.discountPriceEndDatetime = discountPriceEndDatetime;
    }

    /**
     * @return the dateOut
     */
    public boolean isDateOut() {
      return dateOut;
    }

    /**
     * @param dateOut
     *          the dateOut to set
     */
    public void setDateOut(boolean dateOut) {
      this.dateOut = dateOut;
    }
  }

  public static class BrandBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String brandCode;

    private String brandName;

    public String getBrandCode() {
      return brandCode;
    }

    public void setBrandCode(String brandCode) {
      this.brandCode = brandCode;
    }

    public String getBrandName() {
      return brandName;
    }

    public void setBrandName(String brandName) {
      this.brandName = brandName;
    }

  }

  public List<LssueCommodityBean> getLssueList() {
    return lssueList;
  }

  public void setLssueList(List<LssueCommodityBean> lssueList) {
    this.lssueList = lssueList;
  }

  public List<CodeAttribute> getLssueListDelete() {
    return lssueListDelete;
  }

  public void setLssueListDelete(List<CodeAttribute> lssueListDelete) {
    this.lssueListDelete = lssueListDelete;
  }

  public List<CodeAttribute> getUseListDelete() {
    return useListDelete;
  }

  public void setUseListDelete(List<CodeAttribute> useListDelete) {
    this.useListDelete = useListDelete;
  }

  public List<LssueCommodityBean> getUseList() {
    return useList;
  }

  public void setUseList(List<LssueCommodityBean> useList) {
    this.useList = useList;
  }

  public String getMinUseStartNum() {
    return minUseStartNum;
  }

  public void setMinUseStartNum(String minUseStartNum) {
    this.minUseStartNum = minUseStartNum;
  }

  public String getMinUseEndNum() {
    return minUseEndNum;
  }

  public void setMinUseEndNum(String minUseEndNum) {
    this.minUseEndNum = minUseEndNum;
  }

  /**
   * @return the exchangePointAmount
   */
  public String getExchangePointAmount() {
    return exchangePointAmount;
  }

  /**
   * @param exchangePointAmount
   *          the exchangePointAmount to set
   */
  public void setExchangePointAmount(String exchangePointAmount) {
    this.exchangePointAmount = exchangePointAmount;
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
   * @return the useFlagObjects
   */
  public List<CodeAttribute> getUseFlagObjects() {
    return useFlagObjects;
  }

  /**
   * @param useFlagObjects
   *          the useFlagObjects to set
   */
  public void setUseFlagObjects(List<CodeAttribute> useFlagObjects) {
    this.useFlagObjects = useFlagObjects;
  }

  // 20130929 txw add start
  public static class CategoryBean implements Serializable {

    private static final long serialVersionUID = 1L;

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

  /**
   * @return the categoryList
   */
  public List<CategoryBean> getCategoryList() {
    return categoryList;
  }

  /**
   * @param categoryList
   *          the categoryList to set
   */
  public void setCategoryList(List<CategoryBean> categoryList) {
    this.categoryList = categoryList;
  }

  /**
   * @return the relateCategoryCode
   */
  public String getRelateCategoryCode() {
    return relateCategoryCode;
  }

  /**
   * @param relateCategoryCode
   *          the relateCategoryCode to set
   */
  public void setRelateCategoryCode(String relateCategoryCode) {
    this.relateCategoryCode = relateCategoryCode;
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
   * @return the categoryListAll
   */
  public List<CodeAttribute> getCategoryListAll() {
    return categoryListAll;
  }

  /**
   * @param categoryListAll
   *          the categoryListAll to set
   */
  public void setCategoryListAll(List<CodeAttribute> categoryListAll) {
    this.categoryListAll = categoryListAll;
  }

  public static class LssueBrandBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String brandCode;

    private String brandName;

    public String getBrandCode() {
      return brandCode;
    }

    public void setBrandCode(String brandCode) {
      this.brandCode = brandCode;
    }

    public String getBrandName() {
      return brandName;
    }

    public void setBrandName(String brandName) {
      this.brandName = brandName;
    }

  }

  /**
   * @return the lssueBrandList
   */
  public List<LssueBrandBean> getLssueBrandList() {
    return lssueBrandList;
  }

  /**
   * @param lssueBrandList
   *          the lssueBrandList to set
   */
  public void setLssueBrandList(List<LssueBrandBean> lssueBrandList) {
    this.lssueBrandList = lssueBrandList;
  }

  /**
   * @return the lssueBrandCode
   */
  public String getLssueBrandCode() {
    return lssueBrandCode;
  }

  /**
   * @param lssueBrandCode
   *          the lssueBrandCode to set
   */
  public void setLssueBrandCode(String lssueBrandCode) {
    this.lssueBrandCode = lssueBrandCode;
  }

  /**
   * @return the lssueCategoryList
   */
  public List<IssueCategoryBean> getLssueCategoryList() {
    return lssueCategoryList;
  }

  /**
   * @return the lssueCategoryCode
   */
  public String getLssueCategoryCode() {
    return lssueCategoryCode;
  }

  /**
   * @param lssueCategoryList
   *          the lssueCategoryList to set
   */
  public void setLssueCategoryList(List<IssueCategoryBean> lssueCategoryList) {
    this.lssueCategoryList = lssueCategoryList;
  }

  /**
   * @param lssueCategoryCode
   *          the lssueCategoryCode to set
   */
  public void setLssueCategoryCode(String lssueCategoryCode) {
    this.lssueCategoryCode = lssueCategoryCode;
  }

  public static class IssueCategoryBean implements Serializable {

    private static final long serialVersionUID = 1L;

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

  // 20130929 txw add end

}
