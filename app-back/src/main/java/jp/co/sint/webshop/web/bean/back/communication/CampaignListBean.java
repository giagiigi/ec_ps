package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060310:キャンペーン管理のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CampaignListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue = new PagerValue();

  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String searchShopCode;

  @AlphaNum2
  @Length(16)
  @Metadata(name = "キャンペーンコード", order = 2)
  private String searchCampaignCode;

  @Length(50)
  @Metadata(name = "キャンペーン名", order = 3)
  private String searchCampaignName;

  @Datetime
  @Metadata(name = "開始日(From)", order = 4)
  private String searchCampaignStartDateFrom;

  @Datetime
  @Metadata(name = "開始日(To)", order = 5)
  private String searchCampaignStartDateTo;

  @Datetime
  @Metadata(name = "終了日(From)", order = 6)
  private String searchCampaignEndDateFrom;

  @Datetime
  @Metadata(name = "終了日(To)", order = 7)
  private String searchCampaignEndDateTo;

  private List<CodeAttribute> shopNames = new ArrayList<CodeAttribute>();

  private List<CampaignListBeanDetail> campaignList = new ArrayList<CampaignListBeanDetail>();

  /** 更新ボタン表示有無 */
  private boolean updateAuthorizeFlg;

  /** 削除ボタン表示有無 */
  private boolean deleteAuthorizeFlg;

  /** 新規登録ボタン表示有無 */
  private boolean registerNewDisplayFlg;

  /**
   * U1060310:キャンペーン管理のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CampaignListBeanDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String shopCode;

    private String shopName;

    private String campaignCode;

    private String campaignName;

    private String campaignNameEn;

    private String campaignNameJp;

    private String campaignStartDate;

    private String campaignEndDate;

    private String campaignDiscountRate;

    private boolean analysisButtonFlg;

    private boolean previewButtonFlg;

    /**
     * analysisButtonFlgを取得します。
     * 
     * @return analysisButtonFlg
     */
    public boolean isAnalysisButtonFlg() {
      return analysisButtonFlg;
    }

    /**
     * analysisButtonFlgを設定します。
     * 
     * @param analysisButtonFlg
     */
    public void setAnalysisButtonFlg(boolean analysisButtonFlg) {
      this.analysisButtonFlg = analysisButtonFlg;
    }

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
     */
    public void setPreviewButtonFlg(boolean previewButtonFlg) {
      this.previewButtonFlg = previewButtonFlg;
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
     * shopCodeを取得します。
     * 
     * @return shopCode
     */
    public String getShopCode() {
      return shopCode;
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
     * shopCodeを設定します。
     * 
     * @param shopCode
     *          shopCode
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
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

  /**
   * registerNewDisplayFlgを取得します。
   * 
   * @return registerNewDisplayFlg
   */
  public boolean isRegisterNewDisplayFlg() {
    return registerNewDisplayFlg;
  }

  /**
   * registerNewDisplayFlgを設定します。
   * 
   * @param registerNewDisplayFlg
   */
  public void setRegisterNewDisplayFlg(boolean registerNewDisplayFlg) {
    this.registerNewDisplayFlg = registerNewDisplayFlg;
  }

  /**
   * deleteAuthorizeFlgを取得します。
   * 
   * @return deleteAuthorizeFlg
   */
  public boolean isDeleteAuthorizeFlg() {
    return deleteAuthorizeFlg;
  }

  /**
   * deleteAuthorizeFlgを設定します。
   * 
   * @param deleteAuthorizeFlg
   */
  public void setDeleteAuthorizeFlg(boolean deleteAuthorizeFlg) {
    this.deleteAuthorizeFlg = deleteAuthorizeFlg;
  }

  /**
   * updateAuthorizeFlgを取得します。
   * 
   * @return updateAuthorizeFlg
   */
  public boolean isUpdateAuthorizeFlg() {
    return updateAuthorizeFlg;
  }

  /**
   * updateAuthorizeFlgを設定します。
   * 
   * @param updateAuthorizeFlg
   */
  public void setUpdateAuthorizeFlg(boolean updateAuthorizeFlg) {
    this.updateAuthorizeFlg = updateAuthorizeFlg;
  }

  /**
   * campaignListを取得します。
   * 
   * @return campaignList
   */
  public List<CampaignListBeanDetail> getCampaignList() {
    return campaignList;
  }

  /**
   * searchCampaignCodeを取得します。
   * 
   * @return searchCampaignCode
   */
  public String getSearchCampaignCode() {
    return searchCampaignCode;
  }

  /**
   * searchCampaignEndDateFromを取得します。
   * 
   * @return searchCampaignEndDateFrom
   */
  public String getSearchCampaignEndDateFrom() {
    return searchCampaignEndDateFrom;
  }

  /**
   * searchCampaignEndDateToを取得します。
   * 
   * @return searchCampaignEndDateTo
   */
  public String getSearchCampaignEndDateTo() {
    return searchCampaignEndDateTo;
  }

  /**
   * searchCampaignNameを取得します。
   * 
   * @return searchCampaignName
   */
  public String getSearchCampaignName() {
    return searchCampaignName;
  }

  /**
   * searchCampaignStartDateFromを取得します。
   * 
   * @return searchCampaignStartDateFrom
   */
  public String getSearchCampaignStartDateFrom() {
    return searchCampaignStartDateFrom;
  }

  /**
   * searchCampaignStartDateToを取得します。
   * 
   * @return searchCampaignStartDateTo
   */
  public String getSearchCampaignStartDateTo() {
    return searchCampaignStartDateTo;
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
   * campaignListを設定します。
   * 
   * @param campaignList
   *          campaignList
   */
  public void setCampaignList(List<CampaignListBeanDetail> campaignList) {
    this.campaignList = campaignList;
  }

  /**
   * searchCampaignCodeを設定します。
   * 
   * @param searchCampaignCode
   *          searchCampaignCode
   */
  public void setSearchCampaignCode(String searchCampaignCode) {
    this.searchCampaignCode = searchCampaignCode;
  }

  /**
   * searchCampaignEndDateFromを設定します。
   * 
   * @param searchCampaignEndDateFrom
   *          searchCampaignEndDateFrom
   */
  public void setSearchCampaignEndDateFrom(String searchCampaignEndDateFrom) {
    this.searchCampaignEndDateFrom = searchCampaignEndDateFrom;
  }

  /**
   * searchCampaignEndDateToを設定します。
   * 
   * @param searchCampaignEndDateTo
   *          searchCampaignEndDateTo
   */
  public void setSearchCampaignEndDateTo(String searchCampaignEndDateTo) {
    this.searchCampaignEndDateTo = searchCampaignEndDateTo;
  }

  /**
   * searchCampaignNameを設定します。
   * 
   * @param searchCampaignName
   *          searchCampaignName
   */
  public void setSearchCampaignName(String searchCampaignName) {
    this.searchCampaignName = searchCampaignName;
  }

  /**
   * searchCampaignStartDateFromを設定します。
   * 
   * @param searchCampaignStartDateFrom
   *          searchCampaignStartDateFrom
   */
  public void setSearchCampaignStartDateFrom(String searchCampaignStartDateFrom) {
    this.searchCampaignStartDateFrom = searchCampaignStartDateFrom;
  }

  /**
   * searchCampaignStartDateToを設定します。
   * 
   * @param searchCampaignStartDateTo
   *          searchCampaignStartDateTo
   */
  public void setSearchCampaignStartDateTo(String searchCampaignStartDateTo) {
    this.searchCampaignStartDateTo = searchCampaignStartDateTo;
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
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {

  }

  /**
   * shopNamesを取得します。
   * 
   * @return shopNames
   */
  public List<CodeAttribute> getShopNames() {
    return shopNames;
  }

  /**
   * shopNamesを設定します。
   * 
   * @param shopNames
   *          設定する shopNames
   */
  public void setShopNames(List<CodeAttribute> shopNames) {
    this.shopNames = shopNames;
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

    setSearchShopCode(reqparam.get("searchShopCode"));
    setSearchCampaignCode(reqparam.get("searchCampaignCode"));
    setSearchCampaignName(reqparam.get("searchCampaignName"));
    setSearchCampaignStartDateFrom(reqparam.getDateString("searchCampaignStartDateFrom"));
    setSearchCampaignStartDateTo(reqparam.getDateString("searchCampaignStartDateTo"));
    setSearchCampaignEndDateFrom(reqparam.getDateString("searchCampaignEndDateFrom"));
    setSearchCampaignEndDateTo(reqparam.getDateString("searchCampaignEndDateTo"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1060310";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.CampaignListBean.0");
  }
}
