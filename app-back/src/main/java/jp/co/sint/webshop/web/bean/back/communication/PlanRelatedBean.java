package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.attribute.Url;
import jp.co.sint.webshop.data.domain.PlanDetailType;
import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class PlanRelatedBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<String> successList = new ArrayList<String>();

  private List<String> failureList = new ArrayList<String>();

  private String mode;

  private String planCode;

  private Date updateDatetime;

  /** 企划类型模式 */
  private String planTypeMode;

  /** 关联明细类型 */
  private String detailType;

  private List<CodeAttribute> brandList = new ArrayList<CodeAttribute>();

  private List<String> commodityCodeList = new ArrayList<String>();

  private List<CategoryInfo> categoryList;

  private String categoryName;

  private String categoryPath;

  private Boolean updateAuthorizeFlg;

  @Required
  @Metadata(name = "商品编号")
  private String commodityCode;

  @Length(8)
  @Digit
  @Metadata(name = "优先顺序")
  private String commodityDisplayOrder;

  @Required
  @Metadata(name = "品牌")
  private String brandCode;

  private String brandName;

  @Required
  @Metadata(name = "商品分类")
  private String categoryCode;

  @Required
  @Length(16)
  @Metadata(name = "编号")
  private String code;

  @Required
  @Length(40)
  @Metadata(name = "名称")
  private String name;

  @Required
  @Length(40)
  @Metadata(name = "名称(英文)")
  private String nameEn;

  @Required
  @Length(40)
  @Metadata(name = "名称(日文)")
  private String nameJp;

  @Url
  @Length(256)
  @Metadata(name = "企划明细URL")
  private String url;

  @Url
  @Length(256)
  @Metadata(name = "企划明细URL(英文)")
  private String urlEn;

  @Url
  @Length(256)
  @Metadata(name = "企划明细URL(日文)")
  private String urlJp;

  @Required
  @Length(8)
  @Digit
  @Metadata(name = "表示商品件数")
  private String showCount;

  @Length(8)
  @Digit
  @Metadata(name = "优先顺序")
  private String displayOrder;

  private Boolean isUpdateFlg = false;

  private String displayMode;

  // 20130812 txw update start
  private String sortNumMode;

  // 20130812 txw update end

  private List<RelatedCommodityBean> list = new ArrayList<RelatedCommodityBean>();

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPlanCode() {
    return planCode;
  }

  public void setPlanCode(String planCode) {
    this.planCode = planCode;
  }

  public String getDetailType() {
    return detailType;
  }

  public void setDetailType(String detailType) {
    this.detailType = detailType;
  }

  public String getPlanTypeMode() {
    return planTypeMode;
  }

  public void setPlanTypeMode(String planTypeMode) {
    this.planTypeMode = planTypeMode;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getShowCount() {
    return showCount;
  }

  public void setShowCount(String showCount) {
    this.showCount = showCount;
  }

  public String getDisplayOrder() {
    return displayOrder;
  }

  public void setDisplayOrder(String displayOrder) {
    this.displayOrder = displayOrder;
  }

  public List<CodeAttribute> getBrandList() {
    return brandList;
  }

  public void setBrandList(List<CodeAttribute> brandList) {
    this.brandList = brandList;
  }

  public List<String> getCommodityCodeList() {
    return commodityCodeList;
  }

  public void setCommodityCodeList(List<String> commodityCodeList) {
    this.commodityCodeList = commodityCodeList;
  }

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

  public String getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
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

  public List<CategoryInfo> getCategoryList() {
    return categoryList;
  }

  public void setCategoryList(List<CategoryInfo> categoryList) {
    this.categoryList = categoryList;
  }

  public List<RelatedCommodityBean> getList() {
    return list;
  }

  public void setList(List<RelatedCommodityBean> list) {
    this.list = list;
  }

  public Boolean getIsUpdateFlg() {
    return isUpdateFlg;
  }

  public void setIsUpdateFlg(Boolean isUpdateFlg) {
    this.isUpdateFlg = isUpdateFlg;
  }

  public String getDisplayMode() {
    return displayMode;
  }

  public void setDisplayMode(String displayMode) {
    this.displayMode = displayMode;
  }

  /**
   * updateDatetimeを取得します。
   * 
   * @return updateDatetime
   */
  public Date getUpdateDatetime() {
    return DateUtil.immutableCopy(updateDatetime);
  }

  /**
   * updateDatetimeを設定します。
   * 
   * @param updateDatetime
   *          設定する updateDatetime
   */
  public void setUpdateDatetime(Date updateDatetime) {
    this.updateDatetime = DateUtil.immutableCopy(updateDatetime);
  }

  public String getCommodityCode() {
    return commodityCode;
  }

  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  public String getCommodityDisplayOrder() {
    return commodityDisplayOrder;
  }

  public void setCommodityDisplayOrder(String commodityDisplayOrder) {
    this.commodityDisplayOrder = commodityDisplayOrder;
  }

  public List<String> getSuccessList() {
    return successList;
  }

  public void setSuccessList(List<String> successList) {
    this.successList = successList;
  }

  public List<String> getFailureList() {
    return failureList;
  }

  public void setFailureList(List<String> failureList) {
    this.failureList = failureList;
  }

  public Boolean getUpdateAuthorizeFlg() {
    return updateAuthorizeFlg;
  }

  public void setUpdateAuthorizeFlg(Boolean updateAuthorizeFlg) {
    this.updateAuthorizeFlg = updateAuthorizeFlg;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  /**
   * @return the sortNumMode
   */
  public String getSortNumMode() {
    return sortNumMode;
  }

  /**
   * @param sortNumMode
   *          the sortNumMode to set
   */
  public void setSortNumMode(String sortNumMode) {
    this.sortNumMode = sortNumMode;
  }

  public static class RelatedCommodityBean implements Serializable {

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

  @Override
  public String getModuleId() {
    return "U1060820";
  }

  @Override
  public void setSubJspId() {
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    if (PlanType.FEATURE.getValue().equals(getPlanTypeMode())) {
      if (PlanDetailType.BRAND.getValue().equals(getDetailType())) {
        return Messages.getString("web.bean.back.communication.PlanRelatedBean.0");
      } else if (PlanDetailType.CATEGORY.getValue().equals(getDetailType())) {
        return Messages.getString("web.bean.back.communication.PlanRelatedBean.1");
      } else if (PlanDetailType.FREE.getValue().equals(getDetailType())) {
        return Messages.getString("web.bean.back.communication.PlanRelatedBean.2");
      }
    } else {
      if (PlanDetailType.BRAND.getValue().equals(getDetailType())) {
        return Messages.getString("web.bean.back.communication.PlanRelatedBean.0");
      } else if (PlanDetailType.CATEGORY.getValue().equals(getDetailType())) {
        return Messages.getString("web.bean.back.communication.PlanRelatedBean.1");
      } else if (PlanDetailType.FREE.getValue().equals(getDetailType())) {
        return Messages.getString("web.bean.back.communication.PlanRelatedBean.2");
      }
    }
    return "";
  }

  @Override
  public void createAttributes(RequestParameter reqparam) {
    if (!isUpdateFlg) {
      if (PlanDetailType.BRAND.getValue().equals(getDetailType())) {
        setBrandCode(reqparam.get("brandCode"));
      } else if (PlanDetailType.CATEGORY.getValue().equals(getDetailType())) {
        setCategoryCode(reqparam.get("categoryCode"));
      } else {
        setCode(reqparam.get("code"));
        setName(reqparam.get("name"));
        setNameJp(reqparam.get("nameJp"));
        setNameEn(reqparam.get("nameEn"));
        setUrl(reqparam.get("url"));
        setUrlJp(reqparam.get("urlJp"));
        setUrlEn(reqparam.get("urlEn"));
      }
    } else {
      setCommodityCode(reqparam.get("commodityCode").trim());
      setCommodityDisplayOrder(reqparam.get("commodityDisplayOrder"));
    }
    if (PlanDetailType.FREE.getValue().equals(getDetailType())) {
      setName(reqparam.get("name"));
      setNameJp(reqparam.get("nameJp"));
      setNameEn(reqparam.get("nameEn"));
      setUrl(reqparam.get("url"));
      setUrlJp(reqparam.get("urlJp"));
      setUrlEn(reqparam.get("urlEn"));
    }
    setShowCount(reqparam.get("showCount"));
    setDisplayOrder(reqparam.get("displayOrder"));
  }

  /**
   * @return the nameEn
   */
  public String getNameEn() {
    return nameEn;
  }

  /**
   * @param nameEn
   *          the nameEn to set
   */
  public void setNameEn(String nameEn) {
    this.nameEn = nameEn;
  }

  /**
   * @return the nameJp
   */
  public String getNameJp() {
    return nameJp;
  }

  /**
   * @param nameJp
   *          the nameJp to set
   */
  public void setNameJp(String nameJp) {
    this.nameJp = nameJp;
  }

  /**
   * @return the urlEn
   */
  public String getUrlEn() {
    return urlEn;
  }

  /**
   * @param urlEn
   *          the urlEn to set
   */
  public void setUrlEn(String urlEn) {
    this.urlEn = urlEn;
  }

  /**
   * @return the urlJp
   */
  public String getUrlJp() {
    return urlJp;
  }

  /**
   * @param urlJp
   *          the urlJp to set
   */
  public void setUrlJp(String urlJp) {
    this.urlJp = urlJp;
  }

}
