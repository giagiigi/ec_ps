package jp.co.sint.webshop.web.bean.back.communication;

import java.util.Date;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060320:キャンペーンマスタのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CampaignEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @AlphaNum2
  @Required
  @Length(16)
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  @AlphaNum2
  @Required
  @Length(16)
  @Metadata(name = "キャンペーンコード", order = 2)
  private String campaignCode;

  @Required
  @Length(50)
  @Metadata(name = "キャンペーン名", order = 3)
  private String campaignName;
  @Required
  @Length(50)
  @Metadata(name = "キャンペーン名" )
  private String campaignNameEn;
  @Required
  @Length(50)
  @Metadata(name = "キャンペーン名" )
  private String campaignNameJp;
  @Required
  @Datetime
  @Metadata(name = "キャンペーン期間(From)", order = 4)
  private String campaignStartDate;

  @Required
  @Datetime
  @Metadata(name = "キャンペーン期間(To)", order = 5)
  private String campaignEndDate;

  @Required
  @Digit
  @Length(3)
  @Range(min = 0, max = 100)
  @Metadata(name = "値引率", order = 6)
  private String campaignDiscountRate;

  @Length(200)
  @Metadata(name = "備考", order = 7)
  private String memo;

  private Date updateDatetime;

  private boolean displayAssociateCommodityButton = true;

  private boolean displayFileUploadArea = true;

  private boolean registerActionFlg = true;

  private boolean previewButtonFlg;

  private String readonlyMode;

  private Boolean operatingModeIsOne = false;

  /**
   * previewButtonFlgを取得します。
   * 
   * @return previewButtonFlg
   */
  public boolean isPreviewButtonFlg() {
    return previewButtonFlg;
  }

  /**
   * previewButtonFlgを設定します。
   * 
   * @param previewButtonFlg
   *          プレビューボタンの表示フラグ
   */
  public void setPreviewButtonFlg(boolean previewButtonFlg) {
    this.previewButtonFlg = previewButtonFlg;
  }

  /**
   * readonlyModeを取得します。
   * 
   * @return readonlyMode
   */
  public String getReadonlyMode() {
    return readonlyMode;
  }

  /**
   * readonlyModeを設定します。
   * 
   * @param readonlyMode
   */
  public void setReadonlyMode(String readonlyMode) {
    this.readonlyMode = readonlyMode;
  }

  /**
   * registerActionFlgを取得します。
   * 
   * @return registerActionFlg
   */
  public boolean isRegisterActionFlg() {
    return registerActionFlg;
  }

  /**
   * registerActionFlgを設定します。
   * 
   * @param registerActionFlg
   */
  public void setRegisterActionFlg(boolean registerActionFlg) {
    this.registerActionFlg = registerActionFlg;
  }

  /**
   * displayFileUploadAreaを取得します。
   * 
   * @return displayFileUploadArea
   */
  public boolean isDisplayFileUploadArea() {
    return displayFileUploadArea;
  }

  /**
   * displayFileUploadAreaを設定します。
   * 
   * @param displayFileUploadArea
   */
  public void setDisplayFileUploadArea(boolean displayFileUploadArea) {
    this.displayFileUploadArea = displayFileUploadArea;
  }

  /**
   * campaignCodeを取得します。
   * 
   * @return campaignCode
   */
  public String getCampaignCode() {
    return campaignCode;
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
   * campaignEndDateを取得します。
   * 
   * @return campaignEndDate
   */
  public String getCampaignEndDate() {
    return campaignEndDate;
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
   * campaignStartDateを取得します。
   * 
   * @return campaignStartDate
   */
  public String getCampaignStartDate() {
    return campaignStartDate;
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
   * campaignDiscountRateを設定します。
   * 
   * @param campaignDiscountRate
   *          campaignDiscountRate
   */
  public void setCampaignDiscountRate(String campaignDiscountRate) {
    this.campaignDiscountRate = campaignDiscountRate;
  }

  /**
   * campaignEndDateを設定します。
   * 
   * @param campaignEndDate
   *          campaignEndDate
   */
  public void setCampaignEndDate(String campaignEndDate) {
    this.campaignEndDate = campaignEndDate;
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
   * campaignStartDateを設定します。
   * 
   * @param campaignStartDate
   *          campaignStartDate
   */
  public void setCampaignStartDate(String campaignStartDate) {
    this.campaignStartDate = campaignStartDate;
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
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
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

  /**
   * displayAssociateCommodityButtonを取得します。
   * 
   * @return displayAssociateCommodityButton
   */
  public boolean isDisplayAssociateCommodityButton() {
    return displayAssociateCommodityButton;
  }

  /**
   * displayAssociateCommodityButtonを設定します。
   * 
   * @param displayAssociateCommodityButton
   *          設定する displayAssociateCommodityButton
   */
  public void setDisplayAssociateCommodityButton(boolean displayAssociateCommodityButton) {
    this.displayAssociateCommodityButton = displayAssociateCommodityButton;
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

    reqparam.copy(this);

    setCampaignStartDate(reqparam.getDateString("campaignStartDate"));
    setCampaignEndDate(reqparam.getDateString("campaignEndDate"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1060320";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.CampaignEditBean.0");
  }

  /**
   * operatingModeIsOneを取得します。
   * 
   * @return operatingModeIsOne
   */
  public Boolean getOperatingModeIsOne() {
    return operatingModeIsOne;
  }

  /**
   * operatingModeIsOneを設定します。
   * 
   * @param operatingModeIsOne
   *          設定する operatingModeIsOne
   */
  public void setOperatingModeIsOne(Boolean operatingModeIsOne) {
    this.operatingModeIsOne = operatingModeIsOne;
  }

/**
 * @param campaignNameEn the campaignNameEn to set
 */
public void setCampaignNameEn(String campaignNameEn) {
	this.campaignNameEn = campaignNameEn;
}

/**
 * @return the campaignNameEn
 */
public String getCampaignNameEn() {
	return campaignNameEn;
}

/**
 * @param campaignNameJp the campaignNameJp to set
 */
public void setCampaignNameJp(String campaignNameJp) {
	this.campaignNameJp = campaignNameJp;
}

/**
 * @return the campaignNameJp
 */
public String getCampaignNameJp() {
	return campaignNameJp;
}

}
