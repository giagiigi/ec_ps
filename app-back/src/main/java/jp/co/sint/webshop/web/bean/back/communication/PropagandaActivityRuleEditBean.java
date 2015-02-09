package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyEditBean.RegionBlockCharge;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class PropagandaActivityRuleEditBean extends UIBackBean {

  /**
   * 宣传品活动Bean对象。
   */
  private static final long serialVersionUID = 1L;

  @Required
  @AlphaNum2
  @Length(16)
  @Metadata(name = "活动编号", order = 1)
  private String activityCode;

  @Required
  @Length(100)
  @Metadata(name = "活动名称", order = 2)
  private String activityName;

  @Required
  @Digit
  @Metadata(name = "活动类型", order = 3)
  private String activityType;

  @Required
  @Digit
  @Metadata(name = "语言区分", order = 4)
  private String languageType;

  @Required
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "活动开始时间", order = 5)
  private String activityStartDate;

  @Required
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "活动结束时间", order = 6)
  private String activityEndDate;

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

  @Metadata(name = "附送商品编号")
  private String relatedCommodityCode;

  @Metadata(name = "附送商品数量")
  private String relatedCommodityAmount;

  private List<CommodityDetailBean> commodityDetailBeanList = new ArrayList<CommodityDetailBean>();

  private CommodityDetailBean commodityDetailBean = new CommodityDetailBean();

  public void setSubJspId() {

  }

  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
    setActivityStartDate(reqparam.getDateTimeString("activityStartDate"));
    setActivityEndDate(reqparam.getDateTimeString("activityEndDate"));
    this.setCheckedAreaBlockIdList(Arrays.asList(reqparam.getAll("areaId")));
  }

  public String getModuleId() {
    return "U1061510";
  }

  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.PropagandaActivityRuleEditBean.0");
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
    @Metadata(name = "附送商品编号")
    private String commodityCode;

    private String commodityName;

    @Required
    @Length(8)
    @Digit
    @Range(min = 1, max = 99999999)
    @Metadata(name = "附送商品数量")
    private String commodityAmount;

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

    /**
     * @return the commodityAmount
     */
    public String getCommodityAmount() {
      return commodityAmount;
    }

    /**
     * @param commodityAmount
     *          the commodityAmount to set
     */
    public void setCommodityAmount(String commodityAmount) {
      this.commodityAmount = commodityAmount;
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
   * @return the activityCode
   */
  public String getActivityCode() {
    return activityCode;
  }

  /**
   * @return the activityName
   */
  public String getActivityName() {
    return activityName;
  }

  /**
   * @return the activityType
   */
  public String getActivityType() {
    return activityType;
  }

  /**
   * @return the languageType
   */
  public String getLanguageType() {
    return languageType;
  }

  /**
   * @return the activityStartDate
   */
  public String getActivityStartDate() {
    return activityStartDate;
  }

  /**
   * @return the activityEndDate
   */
  public String getActivityEndDate() {
    return activityEndDate;
  }

  /**
   * @param activityCode
   *          the activityCode to set
   */
  public void setActivityCode(String activityCode) {
    this.activityCode = activityCode;
  }

  /**
   * @param activityName
   *          the activityName to set
   */
  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }

  /**
   * @param activityType
   *          the activityType to set
   */
  public void setActivityType(String activityType) {
    this.activityType = activityType;
  }

  /**
   * @param languageType
   *          the languageType to set
   */
  public void setLanguageType(String languageType) {
    this.languageType = languageType;
  }

  /**
   * @param activityStartDate
   *          the activityStartDate to set
   */
  public void setActivityStartDate(String activityStartDate) {
    this.activityStartDate = activityStartDate;
  }

  /**
   * @param activityEndDate
   *          the activityEndDate to set
   */
  public void setActivityEndDate(String activityEndDate) {
    this.activityEndDate = activityEndDate;
  }

  /**
   * @return the relatedCommodityAmount
   */
  public String getRelatedCommodityAmount() {
    return relatedCommodityAmount;
  }

  /**
   * @param relatedCommodityAmount
   *          the relatedCommodityAmount to set
   */
  public void setRelatedCommodityAmount(String relatedCommodityAmount) {
    this.relatedCommodityAmount = relatedCommodityAmount;
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
