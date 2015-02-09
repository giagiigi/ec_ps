package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040170:一括関連付けのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class RelatedListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  private List<RelatedTagListBean> relatedTagList = new ArrayList<RelatedTagListBean>();

  private List<RelatedGiftListBean> relatedGiftList = new ArrayList<RelatedGiftListBean>();

  private List<RelatedCampaignListBean> relatedCampaignList = new ArrayList<RelatedCampaignListBean>();

  /** 画面モード */
  @Required
  private String pictureMode;

  /** 検索用ショップコード */
  @Required
  private String searchShopCode;

  /** 商品コード */
  @Required
  private String commodityCode;

  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  private String searchStartDateTime;

  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  private String searchEndDateTime;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "検索コード(From)")
  private String searchEffectualCodeStart;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "検索コード(To)")
  private String searchEffectualCodeEnd;

  @Length(50)
  @Metadata(name = "検索名")
  private String searchEffectualName;

  private String pictureName;

  private boolean registerTableDisplayFlg;

  private String shopName;

  private String commodityName;

  private PagerValue pagerValue;

  /**
   * U1040170:一括関連付けのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class RelatedTagListBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String checkCode;

    private String shopCode;

    private String tagCode;

    private String tagName;

    /**
     * shopCodeを取得します。
     * 
     * @return shopCode
     */
    public String getShopCode() {
      return shopCode;
    }

    /**
     * tagCodeを取得します。
     * 
     * @return tagCode
     */
    public String getTagCode() {
      return tagCode;
    }

    /**
     * tagNameを取得します。
     * 
     * @return tagName
     */
    public String getTagName() {
      return tagName;
    }

    /**
     * shopCodeを設定します。
     * 
     * @param shopCode
     *          shopCode
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    /**
     * tagCodeを設定します。
     * 
     * @param tagCode
     *          tagCode
     */
    public void setTagCode(String tagCode) {
      this.tagCode = tagCode;
    }

    /**
     * tagNameを設定します。
     * 
     * @param tagName
     *          tagName
     */
    public void setTagName(String tagName) {
      this.tagName = tagName;
    }

    /**
     * checkCodeを設定します。
     * 
     * @param checkCode
     *          checkCode
     */
    public void setCheckCode(String checkCode) {
      this.checkCode = checkCode;
    }

    /**
     * checkCodeを取得します。
     * 
     * @return checkCode
     */
    public String getCheckCode() {
      return checkCode;
    }

  }

  /**
   * U1040170:一括関連付けのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class RelatedGiftListBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String check;

    private String checkCode;

    private String shopCode;

    private String giftCode;

    private String giftName;

    private String giftPrice;

    private String giftTaxType;

    /**
     * giftTaxTypeを取得します。
     * 
     * @return giftTaxType
     */
    public String getGiftTaxType() {
      return giftTaxType;
    }

    /**
     * giftTaxTypeを設定します。
     * 
     * @param giftTaxType
     *          giftTaxType
     */
    public void setGiftTaxType(String giftTaxType) {
      this.giftTaxType = giftTaxType;
    }

    /**
     * checkを取得します。
     * 
     * @return check
     */
    public String getCheck() {
      return check;
    }

    /**
     * giftCodeを取得します。
     * 
     * @return giftCode
     */
    public String getGiftCode() {
      return giftCode;
    }

    /**
     * giftNameを取得します。
     * 
     * @return giftName
     */
    public String getGiftName() {
      return giftName;
    }

    /**
     * giftPriceを取得します。
     * 
     * @return giftPrice
     */
    public String getGiftPrice() {
      return giftPrice;
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
     * checkを設定します。
     * 
     * @param check
     *          check
     */
    public void setCheck(String check) {
      this.check = check;
    }

    /**
     * giftCodeを設定します。
     * 
     * @param giftCode
     *          giftCode
     */
    public void setGiftCode(String giftCode) {
      this.giftCode = giftCode;
    }

    /**
     * giftNameを設定します。
     * 
     * @param giftName
     *          giftName
     */
    public void setGiftName(String giftName) {
      this.giftName = giftName;
    }

    /**
     * giftPriceを設定します。
     * 
     * @param giftPrice
     *          giftPrice
     */
    public void setGiftPrice(String giftPrice) {
      this.giftPrice = giftPrice;
    }

    /**
     * shopCodeを設定します。
     * 
     * @param shopCode
     *          shopCode
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    /**
     * checkCodeを設定します。
     * 
     * @param checkCode
     *          checkCode
     */
    public void setCheckCode(String checkCode) {
      this.checkCode = checkCode;
    }

    /**
     * checkCodeを取得します。
     * 
     * @return checkCode
     */
    public String getCheckCode() {
      return checkCode;
    }

  }

  /**
   * U1040170:一括関連付けのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class RelatedCampaignListBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String check;

    private String checkCode;

    private String shopCode;

    private String campaignCode;

    private String campaignName;

    private String campaignStartDateTime;

    private String campaignEndDateTime;

    private String campaignDiscountRate;

    /**
     * campaignCodeを取得します。
     * 
     * @return campaignCode
     */
    public String getCampaignCode() {
      return campaignCode;
    }

    /**
     * campaignEndDateTimeを取得します。
     * 
     * @return campaignEndDateTime
     */
    public String getCampaignEndDateTime() {
      return campaignEndDateTime;
    }

    /**
     * campaignNameを取得します。
     * 
     * @return campaignName
     */
    public String getCampaignName() {
      return campaignName;
    }

    /**
     * campaignStartDateTimeを取得します。
     * 
     * @return campaignStartDateTime
     */
    public String getCampaignStartDateTime() {
      return campaignStartDateTime;
    }

    /**
     * checkを取得します。
     * 
     * @return check
     */
    public String getCheck() {
      return check;
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
     * campaignCodeを設定します。
     * 
     * @param campaignCode
     *          campaignCode
     */
    public void setCampaignCode(String campaignCode) {
      this.campaignCode = campaignCode;
    }

    /**
     * campaignEndDateTimeを設定します。
     * 
     * @param campaignEndDateTime
     *          campaignEndDateTime
     */
    public void setCampaignEndDateTime(String campaignEndDateTime) {
      this.campaignEndDateTime = campaignEndDateTime;
    }

    /**
     * campaignNameを設定します。
     * 
     * @param campaignName
     *          campaignName
     */
    public void setCampaignName(String campaignName) {
      this.campaignName = campaignName;
    }

    /**
     * campaignStartDateTimeを設定します。
     * 
     * @param campaignStartDateTime
     *          campaignStartDateTime
     */
    public void setCampaignStartDateTime(String campaignStartDateTime) {
      this.campaignStartDateTime = campaignStartDateTime;
    }

    /**
     * checkを設定します。
     * 
     * @param check
     *          check
     */
    public void setCheck(String check) {
      this.check = check;
    }

    /**
     * shopCodeを設定します。
     * 
     * @param shopCode
     *          shopCode
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    /**
     * campaignDiscountRateを取得します。
     * 
     * @return campaignDiscountRate
     */
    public String getCampaignDiscountRate() {
      return campaignDiscountRate;
    }

    /**
     * campaignDiscountRateを設定します。
     * 
     * @param campaignDiscountRate
     *          campaignDiscountRate
     */
    public void setCampaignDiscountRate(String campaignDiscountRate) {
      this.campaignDiscountRate = campaignDiscountRate;
    }

    /**
     * checkCodeを設定します。
     * 
     * @param checkCode
     *          checkCode
     */
    public void setCheckCode(String checkCode) {
      this.checkCode = checkCode;
    }

    /**
     * checkCodeを取得します。
     * 
     * @return checkCode
     */
    public String getCheckCode() {
      return checkCode;
    }

  }

  /**
   * commodityCodeを取得します。
   * 
   * @return commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
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
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
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
   * relatedTagListを取得します。
   * 
   * @return relatedTagList
   */
  public List<RelatedTagListBean> getRelatedTagList() {
    return relatedTagList;
  }

  /**
   * relatedTagListを設定します。
   * 
   * @param relatedTagList
   *          relatedTagList
   */
  public void setRelatedTagList(List<RelatedTagListBean> relatedTagList) {
    this.relatedTagList = relatedTagList;
  }

  /**
   * relatedGiftListを取得します。
   * 
   * @return relatedGiftList
   */
  public List<RelatedGiftListBean> getRelatedGiftList() {
    return relatedGiftList;
  }

  /**
   * relatedGiftListを設定します。
   * 
   * @param relatedGiftList
   *          relatedGiftList
   */
  public void setRelatedGiftList(List<RelatedGiftListBean> relatedGiftList) {
    this.relatedGiftList = relatedGiftList;
  }

  /**
   * relatedCampaignListを取得します。
   * 
   * @return relatedCampaignList
   */
  public List<RelatedCampaignListBean> getRelatedCampaignList() {
    return relatedCampaignList;
  }

  /**
   * relatedCampaignListを設定します。
   * 
   * @param relatedCampaignList
   *          relatedCampaignList
   */
  public void setRelatedCampaignList(List<RelatedCampaignListBean> relatedCampaignList) {
    this.relatedCampaignList = relatedCampaignList;
  }

  /**
   * searchShopCodeを設定します。
   * 
   * @param searchShopCode
   *          searchShopCode
   */
  public void setSearchShopCode(String searchShopCode) {
    this.searchShopCode = searchShopCode;
  }

  /**
   * searchShopCodeを取得します。
   * 
   * @return searchShopCode
   */
  public String getSearchShopCode() {
    return searchShopCode;
  }

  /**
   * pictureModeを設定します。
   * 
   * @param pictureMode
   *          pictureMode
   */
  public void setPictureMode(String pictureMode) {
    this.pictureMode = pictureMode;
  }

  /**
   * pictureModeを取得します。
   * 
   * @return pictureMode
   */
  public String getPictureMode() {
    return pictureMode;
  }

  /**
   * registerTableDisplayFlgを設定します。
   * 
   * @param registerTableDisplayFlg
   *          registerTableDisplayFlg
   */
  public void setRegisterTableDisplayFlg(boolean registerTableDisplayFlg) {
    this.registerTableDisplayFlg = registerTableDisplayFlg;
  }

  /**
   * registerTableDisplayFlgを取得します。
   * 
   * @return registerTableDisplayFlg
   */
  public boolean isRegisterTableDisplayFlg() {
    return registerTableDisplayFlg;
  }

  /**
   * searchEndDateTimeを取得します。
   * 
   * @return searchEndDateTime
   */
  public String getSearchEndDateTime() {
    return searchEndDateTime;
  }

  /**
   * searchEndDateTimeを設定します。
   * 
   * @param searchEndDateTime
   *          searchEndDateTime
   */
  public void setSearchEndDateTime(String searchEndDateTime) {
    this.searchEndDateTime = searchEndDateTime;
  }

  /**
   * searchStartDateTimeを取得します。
   * 
   * @return searchStartDateTime
   */
  public String getSearchStartDateTime() {
    return searchStartDateTime;
  }

  /**
   * searchStartDateTimeを設定します。
   * 
   * @param searchStartDateTime
   *          searchStartDateTime
   */
  public void setSearchStartDateTime(String searchStartDateTime) {
    this.searchStartDateTime = searchStartDateTime;
  }

  /**
   * searchEffectualCodeStartを設定します。
   * 
   * @param searchEffectualCodeStart
   *          searchEffectualCodeStart
   */
  public void setSearchEffectualCodeStart(String searchEffectualCodeStart) {
    this.searchEffectualCodeStart = searchEffectualCodeStart;
  }

  /**
   * searchEffectualCodeStartを取得します。
   * 
   * @return searchEffectualCodeStart
   */
  public String getSearchEffectualCodeStart() {
    return searchEffectualCodeStart;
  }

  /**
   * searchEffectualCodeEndを設定します。
   * 
   * @param searchEffectualCodeEnd
   *          searchEffectualCodeEnd
   */
  public void setSearchEffectualCodeEnd(String searchEffectualCodeEnd) {
    this.searchEffectualCodeEnd = searchEffectualCodeEnd;
  }

  /**
   * searchEffectualCodeEndを取得します。
   * 
   * @return searchEffectualCodeEnd
   */
  public String getSearchEffectualCodeEnd() {
    return searchEffectualCodeEnd;
  }

  /**
   * searchEffectualNameを設定します。
   * 
   * @param searchEffectualName
   *          searchEffectualName
   */
  public void setSearchEffectualName(String searchEffectualName) {
    this.searchEffectualName = searchEffectualName;
  }

  /**
   * searchEffectualNameを取得します。
   * 
   * @return searchEffectualName
   */
  public String getSearchEffectualName() {
    return searchEffectualName;
  }

  /**
   * pictureNameを設定します。
   * 
   * @param pictureName
   *          pictureName
   */
  public void setPictureName(String pictureName) {
    this.pictureName = pictureName;
  }

  /**
   * pictureNameを取得します。
   * 
   * @return pictureName
   */
  public String getPictureName() {
    return pictureName;
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
    searchEffectualCodeStart = reqparam.get("searchEffectualCodeStart");
    searchEffectualCodeEnd = reqparam.get("searchEffectualCodeEnd");
    searchEffectualName = reqparam.get("searchEffectualName");
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040170";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return getPictureName();
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
   * pagerValueを取得します。
   * 
   * @return pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * shopNameを取得します。
   * 
   * @return shopName
   */
  public String getShopName() {
    return shopName;
  }

  /**
   * shopNameを設定します。
   * 
   * @param shopName
   *          shopName
   */
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

}
