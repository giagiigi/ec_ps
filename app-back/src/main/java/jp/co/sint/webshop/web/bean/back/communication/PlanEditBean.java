package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030740:企划登录/更新のデータモデルです。
 * 
 * @author OB.
 */
public class PlanEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 企划类型模式 */
  private String planTypeMode;

  /** 企划类型集合 */
  private List<NameValue> planDetailTypeList = new ArrayList<NameValue>();

  private List<String> successList = new ArrayList<String>();

  private List<String> failureList = new ArrayList<String>();

  private Date updateDatetime;

  @Required
  @AlphaNum2
  @Length(16)
  @Metadata(name = "企划编号", order = 1)
  private String planCode;

  @Required
  @Length(40)
  @Metadata(name = "企划名称", order = 2)
  private String planName;

  // add by cs_yuli 20120515 start
  /** 企划英文名称 ***/
  @Length(40)
  @Metadata(name = "企划英文名称")
  private String planNameEn;

  /** 企划日文名称 ***/
  @Length(40)
  @Metadata(name = "企划日文名称")
  private String planNameJp;

  /*** 企划英文说明 ***/
  @Length(1000)
  @Metadata(name = "企划英文说明")
  private String planDescriptionEn;

  /*** 企划日文说明 ***/
  @Length(1000)
  @Metadata(name = "企划日文说明")
  private String planDescriptionJp;

  // add by cs_yuli 20120515 end

  @Length(4)
  @Metadata(name = "企划类型", order = 3)
  private String planType;

  @Required
  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "优惠开始时间", order = 7)
  private String dateFrom;

  @Required
  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "优惠结束时间", order = 8)
  private String dateTo;

  @Length(1000)
  @Metadata(name = "企划说明", order = 10)
  private String planDescription;

  @Length(200)
  @Metadata(name = "备注", order = 10)
  private String remarks;

  // 20130703 txw add start
  @Length(60)
  @Metadata(name = "TITLE", order = 11)
  private String title;

  @Length(60)
  @Metadata(name = "TITLE(英文)", order = 12)
  private String titleEn;

  @Length(60)
  @Metadata(name = "TITLE(日文)", order = 13)
  private String titleJp;

  @Length(100)
  @Metadata(name = "DESCRIPTION", order = 14)
  private String description;

  @Length(100)
  @Metadata(name = "DESCRIPTION(英文)", order = 15)
  private String descriptionEn;

  @Length(100)
  @Metadata(name = "DESCRIPTION(日文)", order = 16)
  private String descriptionJp;

  @Length(100)
  @Metadata(name = "KEYWORD", order = 17)
  private String keyword;

  @Length(100)
  @Metadata(name = "KEYWORD(英文)", order = 18)
  private String keywordEn;

  @Length(100)
  @Metadata(name = "KEYWORD(日文)", order = 19)
  private String keywordJp;

  // 20130724 txw add start
  private String copyPlanCode;

  private boolean copyFlg;

  // 20130724 txw add end

  // 20130703 txw add end

  private String displayMode;

  private String mode;

  private Boolean updateAuthorizeFlg;

  private List<PlanEditDetailBean> detailList = new ArrayList<PlanEditDetailBean>();

  public String getPlanTypeMode() {
    return planTypeMode;
  }

  public void setPlanTypeMode(String planTypeMode) {
    this.planTypeMode = planTypeMode;
  }

  public List<NameValue> getPlanDetailTypeList() {
    return planDetailTypeList;
  }

  public void setPlanDetailTypeList(List<NameValue> planDetailTypeList) {
    this.planDetailTypeList = planDetailTypeList;
  }

  public String getPlanCode() {
    return planCode;
  }

  public void setPlanCode(String planCode) {
    this.planCode = planCode;
  }

  public String getPlanName() {
    return planName;
  }

  public void setPlanName(String planName) {
    this.planName = planName;
  }

  public String getPlanType() {
    return planType;
  }

  public void setPlanType(String planType) {
    this.planType = planType;
  }

  public String getPlanDescription() {
    return planDescription;
  }

  public void setPlanDescription(String planDescription) {
    this.planDescription = planDescription;
  }

  public String getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(String dateFrom) {
    this.dateFrom = dateFrom;
  }

  public String getDateTo() {
    return dateTo;
  }

  public void setDateTo(String dateTo) {
    this.dateTo = dateTo;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getDisplayMode() {
    return displayMode;
  }

  public void setDisplayMode(String displayMode) {
    this.displayMode = displayMode;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public Boolean getUpdateAuthorizeFlg() {
    return updateAuthorizeFlg;
  }

  public void setUpdateAuthorizeFlg(Boolean updateAuthorizeFlg) {
    this.updateAuthorizeFlg = updateAuthorizeFlg;
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

  public List<PlanEditDetailBean> getDetailList() {
    return detailList;
  }

  public void setDetailList(List<PlanEditDetailBean> detailList) {
    this.detailList = detailList;
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

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    if (WebConstantCode.DISPLAY_EDIT.equals(displayMode)) {
      setPlanCode(reqparam.get("planCode"));
      setPlanType(reqparam.get("planType"));
    }
    setPlanNameEn(reqparam.get("planNameEn"));
    setPlanNameJp(reqparam.get("planNameJp"));
    setPlanName(reqparam.get("planName"));
    setDateFrom(reqparam.getDateTimeString("dateFrom"));
    setDateTo(reqparam.getDateTimeString("dateTo"));
    setPlanDescription(reqparam.get("planDescription"));
    setPlanDescriptionEn(reqparam.get("planDescriptionEn"));
    setPlanDescriptionJp(reqparam.get("planDescriptionJp"));
    setRemarks(reqparam.get("remarks"));
    setTitle(reqparam.get("title"));
    setTitleEn(reqparam.get("titleEn"));
    setTitleJp(reqparam.get("titleJp"));
    setDescription(reqparam.get("description"));
    setDescriptionEn(reqparam.get("descriptionEn"));
    setDescriptionJp(reqparam.get("descriptionJp"));
    setKeyword(reqparam.get("keyword"));
    setKeywordEn(reqparam.get("keywordEn"));
    setKeywordJp(reqparam.get("keywordJp"));
  }

  /**
   * U1030740:企划登录/更新のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class PlanEditDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String detailCode;

    private String detailName;

    private String url;

    private String showCount;

    private String detailType;

    private String displayOrder;

    public String getDetailCode() {
      return detailCode;
    }

    public void setDetailCode(String detailCode) {
      this.detailCode = detailCode;
    }

    public String getDetailName() {
      return detailName;
    }

    public void setDetailName(String detailName) {
      this.detailName = detailName;
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

    public String getDetailType() {
      return detailType;
    }

    public void setDetailType(String detailType) {
      this.detailType = detailType;
    }

    public String getDisplayOrder() {
      return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
      this.displayOrder = displayOrder;
    }

  }

  @Override
  public String getModuleId() {
    if (PlanType.PROMOTION.getValue().equals(getPlanTypeMode())) {
      return "U1060820";
    } else if (PlanType.FEATURE.getValue().equals(getPlanTypeMode())) {
      return "U1060920";
    } else {
      return "";
    }
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
    if (PlanType.PROMOTION.getValue().equals(getPlanTypeMode())) {
      return Messages.getString("web.bean.back.communication.PlanEditBean.1");
    } else if (PlanType.FEATURE.getValue().equals(getPlanTypeMode())) {
      return Messages.getString("web.bean.back.communication.PlanEditBean.2");
    } else {
      return Messages.getString("web.bean.back.communication.PlanEditBean.0");
    }
  }

  /**
   * @param planNameEn
   *          the planNameEn to set
   */
  public void setPlanNameEn(String planNameEn) {
    this.planNameEn = planNameEn;
  }

  /**
   * @return the planNameEn
   */
  public String getPlanNameEn() {
    return planNameEn;
  }

  /**
   * @param planNameJp
   *          the planNameJp to set
   */
  public void setPlanNameJp(String planNameJp) {
    this.planNameJp = planNameJp;
  }

  /**
   * @return the planNameJp
   */
  public String getPlanNameJp() {
    return planNameJp;
  }

  /**
   * @param planDescriptionEn
   *          the planDescriptionEn to set
   */
  public void setPlanDescriptionEn(String planDescriptionEn) {
    this.planDescriptionEn = planDescriptionEn;
  }

  /**
   * @return the planDescriptionEn
   */
  public String getPlanDescriptionEn() {
    return planDescriptionEn;
  }

  /**
   * @param planDescriptionJp
   *          the planDescriptionJp to set
   */
  public void setPlanDescriptionJp(String planDescriptionJp) {
    this.planDescriptionJp = planDescriptionJp;
  }

  /**
   * @return the planDescriptionJp
   */
  public String getPlanDescriptionJp() {
    return planDescriptionJp;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @return the titleEn
   */
  public String getTitleEn() {
    return titleEn;
  }

  /**
   * @return the titleJp
   */
  public String getTitleJp() {
    return titleJp;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return the descriptionEn
   */
  public String getDescriptionEn() {
    return descriptionEn;
  }

  /**
   * @return the descriptionJp
   */
  public String getDescriptionJp() {
    return descriptionJp;
  }

  /**
   * @return the keyword
   */
  public String getKeyword() {
    return keyword;
  }

  /**
   * @return the keywordEn
   */
  public String getKeywordEn() {
    return keywordEn;
  }

  /**
   * @return the keywordJp
   */
  public String getKeywordJp() {
    return keywordJp;
  }

  /**
   * @param title
   *          the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @param titleEn
   *          the titleEn to set
   */
  public void setTitleEn(String titleEn) {
    this.titleEn = titleEn;
  }

  /**
   * @param titleJp
   *          the titleJp to set
   */
  public void setTitleJp(String titleJp) {
    this.titleJp = titleJp;
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @param descriptionEn
   *          the descriptionEn to set
   */
  public void setDescriptionEn(String descriptionEn) {
    this.descriptionEn = descriptionEn;
  }

  /**
   * @param descriptionJp
   *          the descriptionJp to set
   */
  public void setDescriptionJp(String descriptionJp) {
    this.descriptionJp = descriptionJp;
  }

  /**
   * @param keyword
   *          the keyword to set
   */
  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  /**
   * @param keywordEn
   *          the keywordEn to set
   */
  public void setKeywordEn(String keywordEn) {
    this.keywordEn = keywordEn;
  }

  /**
   * @param keywordJp
   *          the keywordJp to set
   */
  public void setKeywordJp(String keywordJp) {
    this.keywordJp = keywordJp;
  }

  /**
   * @return the copyPlanCode
   */
  public String getCopyPlanCode() {
    return copyPlanCode;
  }

  /**
   * @return the copyFlg
   */
  public boolean isCopyFlg() {
    return copyFlg;
  }

  /**
   * @param copyPlanCode
   *          the copyPlanCode to set
   */
  public void setCopyPlanCode(String copyPlanCode) {
    this.copyPlanCode = copyPlanCode;
  }

  /**
   * @param copyFlg
   *          the copyFlg to set
   */
  public void setCopyFlg(boolean copyFlg) {
    this.copyFlg = copyFlg;
  }

}
