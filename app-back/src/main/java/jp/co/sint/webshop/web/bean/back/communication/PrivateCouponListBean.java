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
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060610:PRIVATEクーポン管理のデータモデルです。
 * 
 * @author OB.
 */
public class PrivateCouponListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue = new PagerValue();

  /** 优惠券规则编号 */
  @AlphaNum2
  @Length(6)
  @Metadata(name = "优惠券规则编号", order = 1)
  private String searchCouponCode;

  /** 优惠券规则名称 */
  @Length(40)
  @Metadata(name = "优惠券规则名称", order = 2)
  private String searchCouponName;

  // add by cs_yuli 20120515 start
  /** 优惠券规则英文名称 */
  @Length(40)
  @Metadata(name = "优惠券规则英文名称")
  private String searchCouponNameEn;

  /** 优惠券规则日文名称 */
  @Length(40)
  @Metadata(name = "优惠券规则日文名称")
  private String searchCouponNameJp;

  // add by cs_yuli 20120515 end

  /** 优惠券发行方式 */
  @Digit
  @Metadata(name = "优惠券发行方式", order = 3)
  private String searchCouponType;

  /** 发行类别 */
  @Digit
  @Metadata(name = "发行类别", order = 4)
  private String searchCampaignType;

  /** 发行开始期间(From) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "发行开始期间(From)", order = 5)
  private String searchMinIssueStartDatetimeFrom;

  /** 发行开始期间(To) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "发行开始期间(To)", order = 6)
  private String searchMinIssueStartDatetimeTo;

  /** 发行结束期间(From) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "发行结束期间(From)", order = 7)
  private String searchMinIssueEndDatetimeFrom;

  /** 发行结束期间(To) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "发行结束期间(To)", order = 8)
  private String searchMinIssueEndDatetimeTo;

  /** 利用开始期间(From) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "利用开始期间(From)", order = 9)
  private String searchMinUseStartDatetimeFrom;

  /** 利用开始期间(To) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "利用开始期间(To)", order = 10)
  private String searchMinUseStartDatetimeTo;

  /** 利用结束期间(From) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "利用结束期间(From)", order = 11)
  private String searchMinUseEndDatetimeFrom;

  /** 利用结束期间(To) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "利用结束期间(To)", order = 12)
  private String searchMinUseEndDatetimeTo;

  /** 活动状态 */
  @Digit
  @Metadata(name = "活动状态", order = 13)
  private String searchCouponActivityStatus;

  private List<PrivateCouponListBeanDetail> privateCouponList = new ArrayList<PrivateCouponListBeanDetail>();

  private List<String> checkedCouponCodeList = new ArrayList<String>();

  /** 削除ボタン表示有無 */
  private boolean deleteButtonDisplayFlg;

  /** 新規登録ボタン表示有無 */
  private boolean registerNewDisplayFlg;

  private List<CodeAttribute> couponTypes = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> campaignTypes = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> couponActivityStatusList = new ArrayList<CodeAttribute>();

  /**
   * U1060610:キャンペーン管理のサブモデルです。
   * 
   * @author OB.
   */
  public static class PrivateCouponListBeanDetail implements Serializable {

    /**
		 * 
		 */
    private static final long serialVersionUID = 1L;

    /** 优惠券规则编号 */
    private String couponCode;

    /** 优惠券规则名称 */
    private String couponName;

    /** 优惠券发行方式 */
    private String couponType;

    /** 优惠券发行类别 */
    private String campaignType;

    /** 优惠金额 */
    private String couponAmount;

    /** 优惠比例 */
    private String couponProportion;

    /** 优惠券发行开始日时 */
    private String minIssueStartDatetime;

    /** 优惠券发行结束日时 */
    private String minIssueEndDatetime;

    /** 优惠券发行最小购买金额 */
    private String minIssueOrderAmount;

    /** 优惠券利用开始日时 */
    private String minUseStartDatetime;

    /** 优惠券利用结束日时 */
    private String minUseEndDatetime;

    /** 优惠券利用最小购买金额 */
    private String minUseOrderAmount;

    private String minUseEndNum;

    private boolean afterText;

    /**
     * @return the minUseEndNum
     */
    public String getMinUseEndNum() {
      return minUseEndNum;
    }

    /**
     * @param minUseEndNum
     *          the minUseEndNum to set
     */
    public void setMinUseEndNum(String minUseEndNum) {
      this.minUseEndNum = minUseEndNum;
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
     * afterTextを取得します。
     * 
     * @return afterText
     */
    public boolean isAfterText() {
      return afterText;
    }

    /**
     * afterTextを設定します。
     * 
     * @param afterText
     *          afterText
     */
    public void setAfterText(boolean afterText) {
      this.afterText = afterText;
    }
  }

  /**
   * searchCouponCodeを取得します。
   * 
   * @return searchCouponCode
   */
  public String getSearchCouponCode() {
    return searchCouponCode;
  }

  /**
   * searchCouponCodeを設定します。
   * 
   * @param searchCouponCode
   *          searchCouponCode
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
   * searchCouponTypeを取得します。
   * 
   * @return searchCouponType
   */
  public String getSearchCouponType() {
    return searchCouponType;
  }

  /**
   * searchCouponTypeを設定します。
   * 
   * @param searchCouponType
   *          searchCouponType
   */
  public void setSearchCouponType(String searchCouponType) {
    this.searchCouponType = searchCouponType;
  }

  /**
   * searchCampaignTypeを取得します。
   * 
   * @return searchCampaignType
   */
  public String getSearchCampaignType() {
    return searchCampaignType;
  }

  /**
   * searchCampaignTypeを設定します。
   * 
   * @param searchCampaignType
   *          searchCampaignType
   */
  public void setSearchCampaignType(String searchCampaignType) {
    this.searchCampaignType = searchCampaignType;
  }

  /**
   * searchMinIssueStartDatetimeFromを取得します。
   * 
   * @return searchMinIssueStartDatetimeFrom
   */
  public String getSearchMinIssueStartDatetimeFrom() {
    return searchMinIssueStartDatetimeFrom;
  }

  /**
   * searchMinIssueStartDatetimeFromを設定します。
   * 
   * @param searchMinIssueStartDatetimeFrom
   *          searchMinIssueStartDatetimeFrom
   */
  public void setSearchMinIssueStartDatetimeFrom(String searchMinIssueStartDatetimeFrom) {
    this.searchMinIssueStartDatetimeFrom = searchMinIssueStartDatetimeFrom;
  }

  /**
   * searchMinIssueStartDatetimeToを取得します。
   * 
   * @return searchMinIssueStartDatetimeTo
   */
  public String getSearchMinIssueStartDatetimeTo() {
    return searchMinIssueStartDatetimeTo;
  }

  /**
   * searchMinIssueStartDatetimeToを設定します。
   * 
   * @param searchMinIssueStartDatetimeTo
   *          searchMinIssueStartDatetimeTo
   */
  public void setSearchMinIssueStartDatetimeTo(String searchMinIssueStartDatetimeTo) {
    this.searchMinIssueStartDatetimeTo = searchMinIssueStartDatetimeTo;
  }

  /**
   * searchMinIssueEndDatetimeFromを取得します。
   * 
   * @return searchMinIssueEndDatetimeFrom
   */
  public String getSearchMinIssueEndDatetimeFrom() {
    return searchMinIssueEndDatetimeFrom;
  }

  /**
   * searchMinIssueEndDatetimeFromを設定します。
   * 
   * @param searchMinIssueEndDatetimeFrom
   *          searchMinIssueEndDatetimeFrom
   */
  public void setSearchMinIssueEndDatetimeFrom(String searchMinIssueEndDatetimeFrom) {
    this.searchMinIssueEndDatetimeFrom = searchMinIssueEndDatetimeFrom;
  }

  /**
   * searchMinIssueEndDatetimeToを取得します。
   * 
   * @return searchMinIssueEndDatetimeTo
   */
  public String getSearchMinIssueEndDatetimeTo() {
    return searchMinIssueEndDatetimeTo;
  }

  /**
   * searchMinIssueEndDatetimeToを設定します。
   * 
   * @param searchMinIssueEndDatetimeTo
   *          searchMinIssueEndDatetimeTo
   */
  public void setSearchMinIssueEndDatetimeTo(String searchMinIssueEndDatetimeTo) {
    this.searchMinIssueEndDatetimeTo = searchMinIssueEndDatetimeTo;
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
   * searchCouponActivityStatusを取得します。
   * 
   * @return searchCouponActivityStatus
   */
  public String getSearchCouponActivityStatus() {
    return searchCouponActivityStatus;
  }

  /**
   * searchCouponActivityStatusを設定します。
   * 
   * @param searchCouponActivityStatus
   *          searchCouponActivityStatus
   */
  public void setSearchCouponActivityStatus(String searchCouponActivityStatus) {
    this.searchCouponActivityStatus = searchCouponActivityStatus;
  }

  /**
   * privateCouponListを取得します。
   * 
   * @return privateCouponList
   */
  public List<PrivateCouponListBeanDetail> getPrivateCouponList() {
    return privateCouponList;
  }

  /**
   * privateCouponListを設定します。
   * 
   * @param privateCouponList
   *          privateCouponList
   */
  public void setPrivateCouponList(List<PrivateCouponListBeanDetail> privateCouponList) {
    this.privateCouponList = privateCouponList;
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
   * couponActivityStatusListを取得します。
   * 
   * @return couponActivityStatusList
   */
  public List<CodeAttribute> getCouponActivityStatusList() {
    return couponActivityStatusList;
  }

  /**
   * couponActivityStatusListを設定します。
   * 
   * @param couponActivityStatusList
   *          couponActivityStatusList
   */
  public void setCouponActivityStatusList(List<CodeAttribute> couponActivityStatusList) {
    this.couponActivityStatusList = couponActivityStatusList;
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

    setSearchCouponCode(reqparam.get("searchCouponCode"));
    setSearchCouponNameEn(reqparam.get("searchCouponNameEn"));
    setSearchCouponNameJp(reqparam.get("searchCouponNameJp"));
    setSearchCouponName(reqparam.get("searchCouponName"));
    setSearchCouponType(reqparam.get("searchCouponType"));
    setSearchCampaignType(reqparam.get("searchCampaignType"));
    setSearchMinIssueStartDatetimeFrom(reqparam.getDateTimeString("searchMinIssueStartDatetimeFrom"));
    setSearchMinIssueStartDatetimeTo(reqparam.getDateTimeString("searchMinIssueStartDatetimeTo"));
    setSearchMinIssueEndDatetimeFrom(reqparam.getDateTimeString("searchMinIssueEndDatetimeFrom"));
    setSearchMinIssueEndDatetimeTo(reqparam.getDateTimeString("searchMinIssueEndDatetimeTo"));
    setSearchMinUseStartDatetimeFrom(reqparam.getDateTimeString("searchMinUseStartDatetimeFrom"));
    setSearchMinUseStartDatetimeTo(reqparam.getDateTimeString("searchMinUseStartDatetimeTo"));
    setSearchMinUseEndDatetimeFrom(reqparam.getDateTimeString("searchMinUseEndDatetimeFrom"));
    setSearchMinUseEndDatetimeTo(reqparam.getDateTimeString("searchMinUseEndDatetimeTo"));
    setSearchCouponActivityStatus(reqparam.get("searchCouponActivityStatus"));
    this.setCheckedCouponCodeList(Arrays.asList(reqparam.getAll("couponCode")));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1060610";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.PrivateCouponListBean.0");
  }

  /**
   * @param searchCouponNameEn
   *          the searchCouponNameEn to set
   */
  public void setSearchCouponNameEn(String searchCouponNameEn) {
    this.searchCouponNameEn = searchCouponNameEn;
  }

  /**
   * @return the searchCouponNameEn
   */
  public String getSearchCouponNameEn() {
    return searchCouponNameEn;
  }

  /**
   * @param searchCouponNameJp
   *          the searchCouponNameJp to set
   */
  public void setSearchCouponNameJp(String searchCouponNameJp) {
    this.searchCouponNameJp = searchCouponNameJp;
  }

  /**
   * @return the searchCouponNameJp
   */
  public String getSearchCouponNameJp() {
    return searchCouponNameJp;
  }
}
