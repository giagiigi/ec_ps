package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060120:アンケートマスタのデータモデルです。
 * 
 * @author OB
 */
public class OptionalCampaignEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @AlphaNum2
  @Required
  @Length(16)
  @Metadata(name = "任意活动编号", order = 1)
  private String campaignCode;

  @Required
  @Length(40)
  @Metadata(name = "任意活动名称", order = 1)
  private String campaignName;

  @Required
  @Digit
  @Range(min = 1, max = 999999999)
  @Metadata(name = "活动商品数量", order = 2)
  private String optionalNum;


  @Required
  @Currency
  @Length(11)
  @Precision(precision = 10, scale = 2)
  @Range(min = 0, max = 999999999)
  @Metadata(name = "任意活动价格", order = 8)
  private String optionalPrice;

  @Required
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "利用期间From", order = 9)
  private String campaignStartDate;

  @Required
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "利用期间To", order = 10)
  private String campaignEndDate;

  private Date updateDatetime;

  private String objectCommodities = "";


  private String[] objectCommoditiesDetail;

  private List<CommoditiyDetailBean> commoditiyDetailBeanList = new ArrayList<CommoditiyDetailBean>();
  
  private CommoditiyDetailBean commoditiyDetailBean = new CommoditiyDetailBean();


  /** 页面状态 */
  private String pageFlg = "edit";

  /** 注册按钮状态 */
  private boolean displayRegistButtonFlg;

  /** 更新按钮状态 */
  private boolean displayUpdateButtonFlg;

  private boolean displayDeleteFlg;

  private String commodityNum;
  
  // 促销英文名
  @Required
  @Length(100)
  @Metadata(name = "活动名称(英文)")
  private String campaignNameEn;

  // 促销日文名
  @Required
  @Length(100)
  @Metadata(name = "活动名称(日文)")
  private String campaignNameJp;
    
  
  // 区分更新或增加
  private String displayMode;
  
  // 登录按钮
  private boolean displayLoginButtonFlg;
  
  // 只读权限控制
  private boolean displayReadFlg;
  
  //关联商品显示与否
  private boolean disRelatedButtonFlg;
  
  @Required
  @Digit
  @Range(min = 1, max = 999999999)
  @Metadata(name = "单个最大组数", order = 11)
  private String orderLimitNum;

  //选中的商品编号
  private List<String> checkedCode = new ArrayList<String>();

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
    
    this.getCommoditiyDetailBean().setCommodityCode(reqparam.get("relatedComdtyCode"));
    
    this.setCampaignStartDate(reqparam.getDateTimeString("campaignStartDate"));
    this.setCampaignEndDate(reqparam.getDateTimeString("campaignEndDate"));
    
    this.setCheckedCode(Arrays.asList(reqparam.getAll("checkBox")));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1061610";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return "购买多件优惠活动";
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
   * @return the optionalNum
   */
  public String getOptionalNum() {
    return optionalNum;
  }

  /**
   * @param optionalNum
   *          the optionalNum to set
   */
  public void setOptionalNum(String optionalNum) {
    this.optionalNum = optionalNum;
  }

  /**
   * @return the optionalPrice
   */
  public String getOptionalPrice() {
    return optionalPrice;
  }

  /**
   * @param optionalPrice
   *          the optionalPrice to set
   */
  public void setOptionalPrice(String optionalPrice) {
    this.optionalPrice = optionalPrice;
  }

  
  /**
   * @return the displayMode
   */
  public String getDisplayMode() {
    return displayMode;
  }

  
  /**
   * @param displayMode the displayMode to set
   */
  public void setDisplayMode(String displayMode) {
    this.displayMode = displayMode;
  }

  
  /**
   * @return the displayLoginButtonFlg
   */
  public boolean isDisplayLoginButtonFlg() {
    return displayLoginButtonFlg;
  }

  
  /**
   * @param displayLoginButtonFlg the displayLoginButtonFlg to set
   */
  public void setDisplayLoginButtonFlg(boolean displayLoginButtonFlg) {
    this.displayLoginButtonFlg = displayLoginButtonFlg;
  }

  
  /**
   * @return the displayReadFlg
   */
  public boolean isDisplayReadFlg() {
    return displayReadFlg;
  }

  
  /**
   * @param displayReadFlg the displayReadFlg to set
   */
  public void setDisplayReadFlg(boolean displayReadFlg) {
    this.displayReadFlg = displayReadFlg;
  }

  
  /**
   * @return the campaignCode
   */
  public String getCampaignCode() {
    return campaignCode;
  }

  
  /**
   * @param campaignCode the campaignCode to set
   */
  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }

  
  /**
   * @return the campaignName
   */
  public String getCampaignName() {
    return campaignName;
  }

  
  /**
   * @param campaignName the campaignName to set
   */
  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  
  /**
   * @return the campaignNameEn
   */
  public String getCampaignNameEn() {
    return campaignNameEn;
  }

  
  /**
   * @param campaignNameEn the campaignNameEn to set
   */
  public void setCampaignNameEn(String campaignNameEn) {
    this.campaignNameEn = campaignNameEn;
  }

  
  /**
   * @return the campaignNameJp
   */
  public String getCampaignNameJp() {
    return campaignNameJp;
  }

  
  /**
   * @param campaignNameJp the campaignNameJp to set
   */
  public void setCampaignNameJp(String campaignNameJp) {
    this.campaignNameJp = campaignNameJp;
  }

  
  /**
   * @return the campaignStartDate
   */
  public String getCampaignStartDate() {
    return campaignStartDate;
  }

  
  /**
   * @param campaignStartDate the campaignStartDate to set
   */
  public void setCampaignStartDate(String campaignStartDate) {
    this.campaignStartDate = campaignStartDate;
  }

  
  /**
   * @return the campaignEndDate
   */
  public String getCampaignEndDate() {
    return campaignEndDate;
  }

  
  /**
   * @param campaignEndDate the campaignEndDate to set
   */
  public void setCampaignEndDate(String campaignEndDate) {
    this.campaignEndDate = campaignEndDate;
  }

  
  /**
   * @return the disRelatedButtonFlg
   */
  public boolean isDisRelatedButtonFlg() {
    return disRelatedButtonFlg;
  }

  
  /**
   * @param disRelatedButtonFlg the disRelatedButtonFlg to set
   */
  public void setDisRelatedButtonFlg(boolean disRelatedButtonFlg) {
    this.disRelatedButtonFlg = disRelatedButtonFlg;
  }

  
  /**
   * @return the orderLimitNum
   */
  public String getOrderLimitNum() {
    return orderLimitNum;
  }

  
  /**
   * @param orderLimitNum the orderLimitNum to set
   */
  public void setOrderLimitNum(String orderLimitNum) {
    this.orderLimitNum = orderLimitNum;
  }

  
  /**
   * @return the checkedCode
   */
  public List<String> getCheckedCode() {
    return checkedCode;
  }

  
  /**
   * @param checkedCode the checkedCode to set
   */
  public void setCheckedCode(List<String> checkedCode) {
    this.checkedCode = checkedCode;
  }

}
