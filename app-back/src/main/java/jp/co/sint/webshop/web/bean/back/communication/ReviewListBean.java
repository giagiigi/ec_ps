package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060210:レビュー管理のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class ReviewListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<ReviewListBeanDetail> list = new ArrayList<ReviewListBeanDetail>();

  private List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();

  /** 表示状態の初期値 0:未チェック */
  public static final String INIT_DISPLAY_TYPE = "0";

  /** ショップコード */
  @AlphaNum2
  @Metadata(name = "ショップコード")
  private String shopCode;

  @Datetime
  @Metadata(name = "投稿日(From)")
  private String reviewContributedDatetimeFrom;

  @Datetime
  @Metadata(name = "投稿日(To)")
  private String reviewContributedDatetimeTo;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード(From)")
  private String commodityCodeFrom;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード(To)")
  private String commodityCodeTo;

  @Length(50)
  @Metadata(name = "商品名")
  private String searchCommodityName;

  @Length(50)
  @Metadata(name = "タイトル・内容")
  private String reviewContents;

  private String reviewDisplayType;

  private List<NameValue> reviewDisplayTypeList = NameValue.asList("0:"
      + Messages.getString("web.bean.back.communication.ReviewListBean.1")
      + "/1:"
      + Messages.getString("web.bean.back.communication.ReviewListBean.2")
      + "/2:"
      + Messages.getString("web.bean.back.communication.ReviewListBean.3")
      + "/3:"
      + Messages.getString("web.bean.back.communication.ReviewListBean.4"));

  private boolean updateButtonDisplayFlg;

  private boolean deleteButtonDisplayFlg;

  private boolean checkBoxDisplayFlg;

  private String searchListSort;

  private String sortContributedDatetime = "contributedDatetimeAsc";

  private String sortCommodityName = "commodityNameAsc";

  private PagerValue pagerValue;

  /**
   * U1060210:レビュー管理のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class ReviewListBeanDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String customerCode;

    private String reviewId;

    private String reviewContributedDatetime;

    private String nickName;

    private String commodityName;

    private String reviewScore;

    private String reviewTitle;

    private String reviewDescription;

    private String rearReviewDescription;

    private String reviewDisplayType;

    private Date updatedDatetime;

    private boolean customerLinkDisplay;

    /**
     * customerLinkDisplayを取得します。
     * 
     * @return customerLinkDisplay
     */
    public boolean isCustomerLinkDisplay() {
      return customerLinkDisplay;
    }

    /**
     * customerLinkDisplayを設定します。
     * 
     * @param customerLinkDisplay
     */
    public void setCustomerLinkDisplay(boolean customerLinkDisplay) {
      this.customerLinkDisplay = customerLinkDisplay;
    }

    /**
     * rearReviewDescriptionを取得します。
     * 
     * @return rearReviewDescription
     */
    public String getRearReviewDescription() {
      return rearReviewDescription;
    }

    /**
     * rearReviewDescriptionを設定します。
     * 
     * @param rearReviewDescription
     */
    public void setRearReviewDescription(String rearReviewDescription) {
      this.rearReviewDescription = rearReviewDescription;
    }

    /**
     * updatedDatetimeを取得します。
     * 
     * @return updatedDatetime
     */
    public Date getUpdatedDatetime() {
      return DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * updatedDatetimeを設定します。
     * 
     * @param updatedDatetime
     */
    public void setUpdatedDatetime(Date updatedDatetime) {
      this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * customerCodeを取得します。
     * 
     * @return customerCode
     */
    public String getCustomerCode() {
      return customerCode;
    }

    /**
     * customerCodeを設定します。
     * 
     * @param customerCode
     */
    public void setCustomerCode(String customerCode) {
      this.customerCode = customerCode;
    }

    /**
     * commodityNameを取得します。
     * 
     * @return commodityName
     */
    public String getCommodityName() {
      return commodityName;
    }

    /**
     * nickNameを取得します。
     * 
     * @return nickName
     */
    public String getNickName() {
      return nickName;
    }

    /**
     * reviewContributedDatetimeを取得します。
     * 
     * @return reviewContributedDatetime
     */
    public String getReviewContributedDatetime() {
      return reviewContributedDatetime;
    }

    /**
     * reviewDescriptionを取得します。
     * 
     * @return reviewDescription
     */
    public String getReviewDescription() {
      return reviewDescription;
    }

    /**
     * reviewDisplayTypeを取得します。
     * 
     * @return reviewDisplayType
     */
    public String getReviewDisplayType() {
      return reviewDisplayType;
    }

    /**
     * reviewIdを取得します。
     * 
     * @return reviewId
     */
    public String getReviewId() {
      return reviewId;
    }

    /**
     * reviewScoreを取得します。
     * 
     * @return reviewScore
     */
    public String getReviewScore() {
      return reviewScore;
    }

    /**
     * reviewTitleを取得します。
     * 
     * @return reviewTitle
     */
    public String getReviewTitle() {
      return reviewTitle;
    }

    /**
     * commodityNameを設定します。
     * 
     * @param commodityName
     *          commodityName
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    /**
     * nickNameを設定します。
     * 
     * @param nickName
     *          nickName
     */
    public void setNickName(String nickName) {
      this.nickName = nickName;
    }

    /**
     * reviewContributedDatetimeを設定します。
     * 
     * @param reviewContributedDatetime
     *          reviewContributedDatetime
     */
    public void setReviewContributedDatetime(String reviewContributedDatetime) {
      this.reviewContributedDatetime = reviewContributedDatetime;
    }

    /**
     * reviewDescriptionを設定します。
     * 
     * @param reviewDescription
     *          reviewDescription
     */
    public void setReviewDescription(String reviewDescription) {
      this.reviewDescription = reviewDescription;
    }

    /**
     * reviewDisplayTypeを設定します。
     * 
     * @param reviewDisplayType
     *          reviewDisplayType
     */
    public void setReviewDisplayType(String reviewDisplayType) {
      this.reviewDisplayType = reviewDisplayType;
    }

    /**
     * reviewIdを設定します。
     * 
     * @param reviewId
     *          reviewId
     */
    public void setReviewId(String reviewId) {
      this.reviewId = reviewId;
    }

    /**
     * reviewScoreを設定します。
     * 
     * @param reviewScore
     *          reviewScore
     */
    public void setReviewScore(String reviewScore) {
      this.reviewScore = reviewScore;
    }

    /**
     * reviewTitleを設定します。
     * 
     * @param reviewTitle
     *          reviewTitle
     */
    public void setReviewTitle(String reviewTitle) {
      this.reviewTitle = reviewTitle;
    }

  }

  /**
   * sortCommodityNameを取得します。
   * 
   * @return sortCommodityName
   */
  public String getSortCommodityName() {
    return sortCommodityName;
  }

  /**
   * sortCommodityNameを設定します。
   * 
   * @param sortCommodityName
   */
  public void setSortCommodityName(String sortCommodityName) {
    this.sortCommodityName = sortCommodityName;
  }

  /**
   * sortContributedDatetimeを取得します。
   * 
   * @return sortContributedDatetime
   */
  public String getSortContributedDatetime() {
    return sortContributedDatetime;
  }

  /**
   * sortContributedDatetimeを設定します。
   * 
   * @param sortContributedDatetime
   */
  public void setSortContributedDatetime(String sortContributedDatetime) {
    this.sortContributedDatetime = sortContributedDatetime;
  }

  /**
   * searchListSortを取得します。
   * 
   * @return searchListSort
   */
  public String getSearchListSort() {
    return searchListSort;
  }

  /**
   * searchListSortを設定します。
   * 
   * @param searchListSort
   */
  public void setSearchListSort(String searchListSort) {
    this.searchListSort = searchListSort;
  }

  /**
   * checkBoxDisplayFlgを取得します。
   * 
   * @return checkBoxDisplayFlg
   */
  public boolean isCheckBoxDisplayFlg() {
    return checkBoxDisplayFlg;
  }

  /**
   * checkBoxDisplayFlgを設定します。
   * 
   * @param checkBoxDisplayFlg
   */
  public void setCheckBoxDisplayFlg(boolean checkBoxDisplayFlg) {
    this.checkBoxDisplayFlg = checkBoxDisplayFlg;
  }

  /**
   * deleteButtonDisplayFlgを取得します。
   * 
   * @return deleteButtonDisplayFlg
   */
  public boolean isDeleteButtonDisplayFlg() {
    return deleteButtonDisplayFlg;
  }

  /**
   * deleteButtonDisplayFlgを設定します。
   * 
   * @param deleteButtonDisplayFlg
   */
  public void setDeleteButtonDisplayFlg(boolean deleteButtonDisplayFlg) {
    this.deleteButtonDisplayFlg = deleteButtonDisplayFlg;
  }

  /**
   * updateButtonDisplayFlgを取得します。
   * 
   * @return updateButtonDisplayFlg
   */
  public boolean isUpdateButtonDisplayFlg() {
    return updateButtonDisplayFlg;
  }

  /**
   * updateButtonDisplayFlgを設定します。
   * 
   * @param updateButtonDisplayFlg
   */
  public void setUpdateButtonDisplayFlg(boolean updateButtonDisplayFlg) {
    this.updateButtonDisplayFlg = updateButtonDisplayFlg;
  }

  /**
   * commodityCodeFromを取得します。
   * 
   * @return commodityCodeFrom
   */
  public String getCommodityCodeFrom() {
    return commodityCodeFrom;
  }

  /**
   * commodityCodeToを取得します。
   * 
   * @return commodityCodeTo
   */
  public String getCommodityCodeTo() {
    return commodityCodeTo;
  }

  /**
   * listを取得します。
   * 
   * @return list
   */
  public List<ReviewListBeanDetail> getList() {
    return list;
  }

  /**
   * reviewContributedDatetimeFromを取得します。
   * 
   * @return reviewContributedDatetimeFrom
   */
  public String getReviewContributedDatetimeFrom() {
    return reviewContributedDatetimeFrom;
  }

  /**
   * reviewContributedDatetimeToを取得します。
   * 
   * @return reviewContributedDatetimeTo
   */
  public String getReviewContributedDatetimeTo() {
    return reviewContributedDatetimeTo;
  }

  /**
   * reviewDisplayTypeを取得します。
   * 
   * @return reviewDisplayType
   */
  public String getReviewDisplayType() {
    return reviewDisplayType;
  }

  /**
   * searchCommodityNameを取得します。
   * 
   * @return searchCommodityName
   */
  public String getSearchCommodityName() {
    return searchCommodityName;
  }

  /**
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * commodityCodeFromを設定します。
   * 
   * @param commodityCodeFrom
   *          commodityCodeFrom
   */
  public void setCommodityCodeFrom(String commodityCodeFrom) {
    this.commodityCodeFrom = commodityCodeFrom;
  }

  /**
   * commodityCodeToを設定します。
   * 
   * @param commodityCodeTo
   *          commodityCodeTo
   */
  public void setCommodityCodeTo(String commodityCodeTo) {
    this.commodityCodeTo = commodityCodeTo;
  }

  /**
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<ReviewListBeanDetail> list) {
    this.list = list;
  }

  /**
   * reviewContributedDatetimeFromを設定します。
   * 
   * @param reviewContributedDatetimeFrom
   *          reviewContributedDatetimeFrom
   */
  public void setReviewContributedDatetimeFrom(String reviewContributedDatetimeFrom) {
    this.reviewContributedDatetimeFrom = reviewContributedDatetimeFrom;
  }

  /**
   * reviewContributedDatetimeToを設定します。
   * 
   * @param reviewContributedDatetimeTo
   *          reviewContributedDatetimeTo
   */
  public void setReviewContributedDatetimeTo(String reviewContributedDatetimeTo) {
    this.reviewContributedDatetimeTo = reviewContributedDatetimeTo;
  }

  /**
   * reviewDisplayTypeを設定します。
   * 
   * @param reviewDisplayType
   *          reviewDisplayType
   */
  public void setReviewDisplayType(String reviewDisplayType) {
    this.reviewDisplayType = reviewDisplayType;
  }

  /**
   * searchCommodityNameを設定します。
   * 
   * @param searchCommodityName
   *          searchCommodityName
   */
  public void setSearchCommodityName(String searchCommodityName) {
    this.searchCommodityName = searchCommodityName;
  }

  /**
   * shopNameを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * reviewContentsを取得します。
   * 
   * @return reviewContents
   */
  public String getReviewContents() {
    return reviewContents;
  }

  /**
   * reviewContentsを設定します。
   * 
   * @param reviewContents
   *          reviewContents
   */
  public void setReviewContents(String reviewContents) {
    this.reviewContents = reviewContents;
  }

  /**
   * shopListを取得します。
   * 
   * @return shopList
   */
  public List<CodeAttribute> getShopList() {
    return shopList;
  }

  /**
   * shopListを設定します。
   * 
   * @param shopList
   *          shopList
   */
  public void setShopList(List<CodeAttribute> shopList) {
    this.shopList = shopList;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    this.setShopCode(reqparam.get("shopCode"));
    this.setReviewContributedDatetimeFrom(reqparam.getDateString("reviewContributedDatetimeFrom"));
    this.setReviewContributedDatetimeTo(reqparam.getDateString("reviewContributedDatetimeTo"));
    this.setCommodityCodeFrom(reqparam.get("commodityCodeFrom"));
    this.setCommodityCodeTo(reqparam.get("commodityCodeTo"));
    this.setSearchCommodityName(reqparam.get("commodityName"));
    this.setReviewContents(reqparam.get("reviewContents"));
    this.setReviewDisplayType(reqparam.get("searchReviewDisplayType"));
    this.setSearchListSort(reqparam.get("searchListSort"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1060210";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.ReviewListBean.0");
  }

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * pagerValueを設定します。
   * 
   * @param pagerValue
   *          pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * reviewDisplayTypeListを返します。
   * 
   * @return the reviewDisplayTypeList
   */
  public List<NameValue> getReviewDisplayTypeList() {
    return reviewDisplayTypeList;
  }

  /**
   * reviewDisplayTypeListを設定します。
   * 
   * @param reviewDisplayTypeList
   *          設定する reviewDisplayTypeList
   */
  public void setReviewDisplayTypeList(List<NameValue> reviewDisplayTypeList) {
    this.reviewDisplayTypeList = reviewDisplayTypeList;
  }

}
