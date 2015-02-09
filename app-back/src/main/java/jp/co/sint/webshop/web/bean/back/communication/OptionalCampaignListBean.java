package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060310:キャンペーン管理のデータモデルです。
 * 
 * @author OB.
 */
public class OptionalCampaignListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue = new PagerValue();

  /** soukai add 2012/01/13 ob start */
  /** 优惠券规则编号 */
  @AlphaNum2
  @Length(16)
  @Metadata(name = "活动编号", order = 1)
  private String searchCouponCode;

  /** soukai add 2012/01/13 ob end */

  /** 优惠券规则名称 */
  @Length(40)
  @Metadata(name = "活动名称", order = 2)
  private String searchCouponName;

  /** 优惠券利用开始日时(From) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "利用开始期间(From)", order = 9)
  private String searchMinUseStartDatetimeFrom;

  /** 优惠券利用开始日时(To) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "利用开始期间(To)", order = 10)
  private String searchMinUseStartDatetimeTo;

  /** 优惠券利用结束日时(From) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "利用结束期间(From)", order = 11)
  private String searchMinUseEndDatetimeFrom;

  /** 优惠券利用结束日时(To) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "利用结束期间(To)", order = 12)
  private String searchMinUseEndDatetimeTo;

  /** 发行状态 */
  @Digit
  @Metadata(name = "发行状态", order = 13)
  private String searchCouponActivityStatus;

  private List<OptionalCampaignListBeanDetail> optionalCampaingList = new ArrayList<OptionalCampaignListBeanDetail>();

  private List<CodeAttribute> couponActivityStatusList = new ArrayList<CodeAttribute>();

  private List<String> checkedCouponCodeList = new ArrayList<String>();

  /** 削除ボタン表示有無 */
  private boolean deleteButtonDisplayFlg;

  /** 新規登録ボタン表示有無 */
  private boolean registerNewDisplayFlg;

  private boolean linkNewDisplayFlg;

  private List<CodeAttribute> couponTypes = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> couponIssueTypes = new ArrayList<CodeAttribute>();

  /**
   * U1060310:キャンペーン管理のサブモデルです。
   * 
   * @author OB.
   */
  public static class OptionalCampaignListBeanDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 优惠券规则编号 */
    private String campaignCode;

    /** 优惠券规则名称 */
    private String campaignName;

    /** 优惠券利用开始日时 */
    private String startDatetime;

    /** 优惠券利用结束日时 */
    private String endDatetime;

    private String optionalNum;

    private String optionalPrice;

    private String orderLimitNum;

    /**
     * @return the campaignCode
     */
    public String getCampaignCode() {
      return campaignCode;
    }

    /**
     * @param campaignCode
     *          the campaignCode to set
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
     * @param campaignName
     *          the campaignName to set
     */
    public void setCampaignName(String campaignName) {
      this.campaignName = campaignName;
    }

    /**
     * @return the startDatetime
     */
    public String getStartDatetime() {
      return startDatetime;
    }

    /**
     * @param startDatetime
     *          the startDatetime to set
     */
    public void setStartDatetime(String startDatetime) {
      this.startDatetime = startDatetime;
    }

    /**
     * @return the endDatetime
     */
    public String getEndDatetime() {
      return endDatetime;
    }

    /**
     * @param endDatetime
     *          the endDatetime to set
     */
    public void setEndDatetime(String endDatetime) {
      this.endDatetime = endDatetime;
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
     * @return the orderLimitNum
     */
    public String getOrderLimitNum() {
      return orderLimitNum;
    }

    /**
     * @param orderLimitNum
     *          the orderLimitNum to set
     */
    public void setOrderLimitNum(String orderLimitNum) {
      this.orderLimitNum = orderLimitNum;
    }

    
    /**
     * @return the optionalNum
     */
    public String getOptionalNum() {
      return optionalNum;
    }

    
    /**
     * @param optionalNum the optionalNum to set
     */
    public void setOptionalNum(String optionalNum) {
      this.optionalNum = optionalNum;
    }

  }

  /**
   * @return the searchCouponCode
   */
  public String getSearchCouponCode() {
    return searchCouponCode;
  }

  /**
   * @param searchCouponCode
   *          the searchCouponCode to set
   */
  public void setSearchCouponCode(String searchCouponCode) {
    this.searchCouponCode = searchCouponCode;
  }

  /**
   * searchCouponNameを取得します。
   * 
   * @return searchCouponName
   */
  public String getSearchCouponName() {
    return searchCouponName;
  }

  /**
   * searchCouponNameを設定します。
   * 
   * @param searchCouponName
   *          searchCouponName
   */
  public void setSearchCouponName(String searchCouponName) {
    this.searchCouponName = searchCouponName;
  }

  /**
   * searchMinUseStartDatetimeFromを取得します。
   * 
   * @return searchMinUseStartDatetimeFrom
   */
  public String getSearchMinUseStartDatetimeFrom() {
    return searchMinUseStartDatetimeFrom;
  }

  /**
   * searchMinUseStartDatetimeFromを設定します。
   * 
   * @param searchMinUseStartDatetimeFrom
   *          searchMinUseStartDatetimeFrom
   */
  public void setSearchMinUseStartDatetimeFrom(String searchMinUseStartDatetimeFrom) {
    this.searchMinUseStartDatetimeFrom = searchMinUseStartDatetimeFrom;
  }

  /**
   * searchMinUseStartDatetimeToを取得します。
   * 
   * @return searchMinUseStartDatetimeTo
   */
  public String getSearchMinUseStartDatetimeTo() {
    return searchMinUseStartDatetimeTo;
  }

  /**
   * searchMinUseStartDatetimeToを設定します。
   * 
   * @param searchMinUseStartDatetimeTo
   *          searchMinUseStartDatetimeTo
   */
  public void setSearchMinUseStartDatetimeTo(String searchMinUseStartDatetimeTo) {
    this.searchMinUseStartDatetimeTo = searchMinUseStartDatetimeTo;
  }

  /**
   * searchMinUseEndDatetimeFromを取得します。
   * 
   * @return searchMinUseEndDatetimeFrom
   */
  public String getSearchMinUseEndDatetimeFrom() {
    return searchMinUseEndDatetimeFrom;
  }

  /**
   * searchMinUseEndDatetimeFromを設定します。
   * 
   * @param searchMinUseEndDatetimeFrom
   *          searchMinUseEndDatetimeFrom
   */
  public void setSearchMinUseEndDatetimeFrom(String searchMinUseEndDatetimeFrom) {
    this.searchMinUseEndDatetimeFrom = searchMinUseEndDatetimeFrom;
  }

  /**
   * searchMinUseEndDatetimeToを取得します。
   * 
   * @return searchMinUseEndDatetimeTo
   */
  public String getSearchMinUseEndDatetimeTo() {
    return searchMinUseEndDatetimeTo;
  }

  /**
   * searchMinUseEndDatetimeToを設定します。
   * 
   * @param searchMinUseEndDatetimeTo
   *          searchMinUseEndDatetimeTo
   */
  public void setSearchMinUseEndDatetimeTo(String searchMinUseEndDatetimeTo) {
    this.searchMinUseEndDatetimeTo = searchMinUseEndDatetimeTo;
  }

  /**
   * checkedCouponCodeListを取得します。
   * 
   * @return checkedCouponCodeList
   */
  public List<String> getCheckedCouponCodeList() {
    return checkedCouponCodeList;
  }

  /**
   * checkedCouponCodeListを設定します。
   * 
   * @param checkedCouponCodeList
   *          checkedCouponCodeList
   */
  public void setCheckedCouponCodeList(List<String> checkedCouponCodeList) {
    this.checkedCouponCodeList = checkedCouponCodeList;
  }

  /**
   * deleteButtonDisplayFlgを取得します。
   * 
   * @return deleteButtonDisplayFlg
   */
  public boolean getDeleteButtonDisplayFlg() {
    return deleteButtonDisplayFlg;
  }

  /**
   * deleteButtonDisplayFlgを設定します。
   * 
   * @param deleteButtonDisplayFlg
   *          deleteButtonDisplayFlg
   */
  public void setDeleteButtonDisplayFlg(boolean deleteButtonDisplayFlg) {
    this.deleteButtonDisplayFlg = deleteButtonDisplayFlg;
  }

  /**
   * registerNewDisplayFlgを取得します。
   * 
   * @return registerNewDisplayFlg
   */
  public boolean getRegisterNewDisplayFlg() {
    return registerNewDisplayFlg;
  }

  /**
   * registerNewDisplayFlgを設定します。
   * 
   * @param registerNewDisplayFlg
   *          registerNewDisplayFlg
   */
  public void setRegisterNewDisplayFlg(boolean registerNewDisplayFlg) {
    this.registerNewDisplayFlg = registerNewDisplayFlg;
  }

  /**
   * couponTypesを取得します。
   * 
   * @return couponTypes
   */
  public List<CodeAttribute> getCouponTypes() {
    return couponTypes;
  }

  /**
   * couponTypesを設定します。
   * 
   * @param couponTypes
   *          couponTypes
   */
  public void setCouponTypes(List<CodeAttribute> couponTypes) {
    this.couponTypes = couponTypes;
  }

  /**
   * couponIssueTypesを取得します。
   * 
   * @return couponIssueTypes
   */
  public List<CodeAttribute> getCouponIssueTypes() {
    return couponIssueTypes;
  }

  /**
   * couponIssueTypesを設定します。
   * 
   * @param couponIssueTypes
   *          couponIssueTypes
   */
  public void setCouponIssueTypes(List<CodeAttribute> couponIssueTypes) {
    this.couponIssueTypes = couponIssueTypes;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {

  }

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * pagerValueを設定します。
   * 
   * @param pagerValue
   *          設定する pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {

    // soukai add 2012/01/13 ob start
    setSearchCouponCode(reqparam.get("searchCouponCode"));
    // soukai add 2012/01/13 ob end
    setSearchCouponName(reqparam.get("searchCouponName"));
    setSearchMinUseStartDatetimeFrom(reqparam.getDateTimeString("searchMinUseStartDatetimeFrom"));
    setSearchMinUseStartDatetimeTo(reqparam.getDateTimeString("searchMinUseStartDatetimeTo"));
    setSearchMinUseEndDatetimeFrom(reqparam.getDateTimeString("searchMinUseEndDatetimeFrom"));
    setSearchMinUseEndDatetimeTo(reqparam.getDateTimeString("searchMinUseEndDatetimeTo"));
    this.setCheckedCouponCodeList(Arrays.asList(reqparam.getAll("couponCode")));
    this.setSearchCouponActivityStatus(reqparam.get("searchCouponActivityStatus"));
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
    // return
    // Messages.getString("web.bean.back.communication.PublicCouponListBean.0");
    return "购买多件优惠活动";
  }

  public boolean isLinkNewDisplayFlg() {
    return linkNewDisplayFlg;
  }

  public void setLinkNewDisplayFlg(boolean linkNewDisplayFlg) {
    this.linkNewDisplayFlg = linkNewDisplayFlg;
  }

  public String getSearchCouponActivityStatus() {
    return searchCouponActivityStatus;
  }

  public void setSearchCouponActivityStatus(String searchCouponActivityStatus) {
    this.searchCouponActivityStatus = searchCouponActivityStatus;
  }

  public List<CodeAttribute> getCouponActivityStatusList() {
    return couponActivityStatusList;
  }

  public void setCouponActivityStatusList(List<CodeAttribute> couponActivityStatusList) {
    this.couponActivityStatusList = couponActivityStatusList;
  }

  /**
   * @return the optionalCampaingList
   */
  public List<OptionalCampaignListBeanDetail> getOptionalCampaingList() {
    return optionalCampaingList;
  }

  /**
   * @param optionalCampaingList
   *          the optionalCampaingList to set
   */
  public void setOptionalCampaingList(List<OptionalCampaignListBeanDetail> optionalCampaingList) {
    this.optionalCampaingList = optionalCampaingList;
  }
}
