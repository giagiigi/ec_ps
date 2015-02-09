package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040160:関連付けのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class RelatedBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 画面モード */
  @Required
  private String pictureMode;

  /** 有効コード */
  @Required
  private String effectualCode;

  /** 検索用ショップコード */
  private String searchShopCode;

  /** 画面名 */
  private String pictureName;

  /** ショップ名 */
  private String shopName;

  /** 検索用商品コード開始 */
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード(From)")
  private String searchCommodityCodeStart;

  /** 検索用商品コード終了 */
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード(To)")
  private String searchCommodityCodeEnd;

  /** 検索用商品名 */
  @Length(50)
  @Metadata(name = "商品名")
  private String searchCommodityName;

  /** 商品コード */
  private String commodityCode;

  /** 商品名 */
  private String commodityName;

  /** タグコード */
  private String tagCode;

  /** タグ名 */
  private String tagName;

  /** ギフトコード */
  private String giftCode;

  /** ギフト名 */
  private String giftName;

  /** ギフト区分 */
  private String giftType;

  /** ギフト説明 */
  private String giftDescription;

  /** ギフト価格 */
  private String giftPrice;

  /** ギフト消費税区分 */
  private String giftTaxType;

  /** キャンペーンコード */
  private String campaignCode;

  /** キャンペーン名 */
  private String campaignName;

  /** キャンペーン期間 */
  private String campaignDate;

  /** メモ */
  private String memo;

  /** 削除モード */
  private boolean interactiveDeleteFlg;

  /** ファイルパス */
  private String fileUrl;

  /** 登録テーブル表示フラグ */
  private boolean registerTableDisplayFlg;

  /** 更新テーブル表示フラグ */
  private boolean updateTableDisplayFlg;

  /** 削除テーブル表示フラグ */
  private boolean deleteTableDisplayFlg;

  /** 取込テーブル表示フラグ */
  private boolean importTableDisplayFlg;

  /** ページャ */
  private PagerValue pagerValue;

  /** 登録用レコード */
  private RelatedDetailBean edit = new RelatedDetailBean();

  /** 一覧用リスト */
  private List<RelatedDetailBean> list = new ArrayList<RelatedDetailBean>();

  /** 編集モード */
  private String editMode;
  
  private List<CodeAttribute> langCodes = new ArrayList<CodeAttribute>();

  private List<NameValue> csvHeaderType = NameValue.asList("true:"
      + Messages.getString("web.bean.back.catalog.RelatedBean.0")
      + "/false:"
      + Messages.getString("web.bean.back.catalog.RelatedBean.1"));
  
  private List<String> checkedCodeList = new ArrayList<String>();

  /**
   * U1040160:関連付けのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class RelatedDetailBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Length(16)
    @AlphaNum2
    @Metadata(name = "ショップコード")
    private String shopCode;

    @Required
    @Length(16)
    @AlphaNum2
    @Metadata(name = "商品コード")
    private String commodityCode;

    @Length(8)
    @Digit
    @Metadata(name = "表示順")
    private String displayOrder;
    
    @Length(4)
    @AlphaNum2
    @Digit
    @Metadata(name = "标签中文商品显示顺序")
    private String sortNum;
    
    @Length(4)
    @AlphaNum2
    @Digit
    @Metadata(name = "标签日文商品显示顺序")
    private String sortNumJp;
    
    @Length(4)
    @AlphaNum2
    @Digit
    @Metadata(name = "标签英文商品显示顺序")
    private String sortNumEn;

    private String langTab;
    
    private List<String> lang = new ArrayList<String>();

    /** 商品名 */
    private String commodityName;

    /** 適用キャンペーン */
    private String appliedCampaignName;

    /** 更新日付 */
    private Date updatedDatetime;

    /** 相互関連付け */
    private String interaction;

    /**
     * appliedCampaignNameを取得します。
     * 
     * @return appliedCampaignName
     */
    public String getAppliedCampaignName() {
      return appliedCampaignName;
    }

    /**
     * appliedCampaignNameを設定します。
     * 
     * @param appliedCampaignName
     *          appliedCampaignName
     */
    public void setAppliedCampaignName(String appliedCampaignName) {
      this.appliedCampaignName = appliedCampaignName;
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
     * commodityCodeを設定します。
     * 
     * @param commodityCode
     *          commodityCode
     */
    public void setCommodityCode(String commodityCode) {
      this.commodityCode = commodityCode;
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
     * commodityNameを設定します。
     * 
     * @param commodityName
     *          commodityName
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
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
     * shopCodeを設定します。
     * 
     * @param shopCode
     *          shopCode
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    /**
     * displayOrderを設定します。
     * 
     * @param displayOrder
     *          displayOrder
     */
    public void setDisplayOrder(String displayOrder) {
      this.displayOrder = displayOrder;
    }

    /**
     * displayOrderを取得します。
     * 
     * @return displayOrder
     */
    public String getDisplayOrder() {
      return displayOrder;
    }

    /**
     * interactionを取得します。
     * 
     * @return interaction
     */
    public String getInteraction() {
      return interaction;
    }

    /**
     * interactionを設定します。
     * 
     * @param interaction
     *          interaction
     */
    public void setInteraction(String interaction) {
      this.interaction = interaction;
    }

    /**
     * updatedDatetimeを設定します。
     * 
     * @param updatedDatetime
     *          updatedDatetime
     */
    public void setUpdatedDatetime(Date updatedDatetime) {
      this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
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
     * @return the lang
     */
    public List<String> getLang() {
      return lang;
    }

    
    /**
     * @param lang the lang to set
     */
    public void setLang(List<String> lang) {
      this.lang = lang;
    }




    
    /**
     * @return the langTab
     */
    public String getLangTab() {
      return langTab;
    }

    
    /**
     * @param langTab the langTab to set
     */
    public void setLangTab(String langTab) {
      this.langTab = langTab;
    }

    
    /**
     * @return the sortNum
     */
    public String getSortNum() {
      return sortNum;
    }

    
    /**
     * @param sortNum the sortNum to set
     */
    public void setSortNum(String sortNum) {
      this.sortNum = sortNum;
    }

    
    /**
     * @return the sortNumJp
     */
    public String getSortNumJp() {
      return sortNumJp;
    }

    
    /**
     * @param sortNumJp the sortNumJp to set
     */
    public void setSortNumJp(String sortNumJp) {
      this.sortNumJp = sortNumJp;
    }

    
    /**
     * @return the sortNumEn
     */
    public String getSortNumEn() {
      return sortNumEn;
    }

    
    /**
     * @param sortNumEn the sortNumEn to set
     */
    public void setSortNumEn(String sortNumEn) {
      this.sortNumEn = sortNumEn;
    }

    

  }

  /**
   * editModeを取得します。
   * 
   * @return editMode
   */
  public String getEditMode() {
    return editMode;
  }

  /**
   * editModeを設定します。
   * 
   * @param editMode
   *          editMode
   */
  public void setEditMode(String editMode) {
    this.editMode = editMode;
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
   * pictureModeを取得します。
   * 
   * @return pictureMode
   */
  public String getPictureMode() {
    return pictureMode;
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

  /**
   * campaignCodeを取得します。
   * 
   * @return campaignCode
   */
  public String getCampaignCode() {
    return campaignCode;
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
   * campaignDateを取得します。
   * 
   * @return campaignDate
   */
  public String getCampaignDate() {
    return campaignDate;
  }

  /**
   * campaignDateを設定します。
   * 
   * @param campaignDate
   *          campaignDate
   */
  public void setCampaignDate(String campaignDate) {
    this.campaignDate = campaignDate;
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
   * campaignNameを設定します。
   * 
   * @param campaignName
   *          campaignName
   */
  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
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
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
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
   * commodityNameを設定します。
   * 
   * @param commodityName
   *          commodityName
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * editを取得します。
   * 
   * @return edit
   */
  public RelatedDetailBean getEdit() {
    return edit;
  }

  /**
   * editを設定します。
   * 
   * @param edit
   *          edit
   */
  public void setEdit(RelatedDetailBean edit) {
    this.edit = edit;
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
   * giftCodeを設定します。
   * 
   * @param giftCode
   *          giftCode
   */
  public void setGiftCode(String giftCode) {
    this.giftCode = giftCode;
  }

  /**
   * giftDescriptionを取得します。
   * 
   * @return giftDescription
   */
  public String getGiftDescription() {
    return giftDescription;
  }

  /**
   * giftDescriptionを設定します。
   * 
   * @param giftDescription
   *          giftDescription
   */
  public void setGiftDescription(String giftDescription) {
    this.giftDescription = giftDescription;
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
   * giftNameを設定します。
   * 
   * @param giftName
   *          giftName
   */
  public void setGiftName(String giftName) {
    this.giftName = giftName;
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
   * giftPriceを設定します。
   * 
   * @param giftPrice
   *          giftPrice
   */
  public void setGiftPrice(String giftPrice) {
    this.giftPrice = giftPrice;
  }

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
   * giftTypeを取得します。
   * 
   * @return giftType
   */
  public String getGiftType() {
    return giftType;
  }

  /**
   * giftTypeを設定します。
   * 
   * @param giftType
   *          giftType
   */
  public void setGiftType(String giftType) {
    this.giftType = giftType;
  }

  /**
   * listを取得します。
   * 
   * @return list
   */
  public List<RelatedDetailBean> getList() {
    return list;
  }

  /**
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<RelatedDetailBean> list) {
    this.list = list;
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
   * memoを設定します。
   * 
   * @param memo
   *          memo
   */
  public void setMemo(String memo) {
    this.memo = memo;
  }

  /**
   * searchCommodityCodeEndを取得します。
   * 
   * @return searchCommodityCodeEnd
   */
  public String getSearchCommodityCodeEnd() {
    return searchCommodityCodeEnd;
  }

  /**
   * searchCommodityCodeEndを設定します。
   * 
   * @param searchCommodityCodeEnd
   *          searchCommodityCodeEnd
   */
  public void setSearchCommodityCodeEnd(String searchCommodityCodeEnd) {
    this.searchCommodityCodeEnd = searchCommodityCodeEnd;
  }

  /**
   * searchCommodityCodeStartを取得します。
   * 
   * @return searchCommodityCodeStart
   */
  public String getSearchCommodityCodeStart() {
    return searchCommodityCodeStart;
  }

  /**
   * searchCommodityCodeStartを設定します。
   * 
   * @param searchCommodityCodeStart
   *          searchCommodityCodeStart
   */
  public void setSearchCommodityCodeStart(String searchCommodityCodeStart) {
    this.searchCommodityCodeStart = searchCommodityCodeStart;
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
   * searchCommodityNameを設定します。
   * 
   * @param searchCommodityName
   *          searchCommodityName
   */
  public void setSearchCommodityName(String searchCommodityName) {
    this.searchCommodityName = searchCommodityName;
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
   * tagCodeを設定します。
   * 
   * @param tagCode
   *          tagCode
   */
  public void setTagCode(String tagCode) {
    this.tagCode = tagCode;
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
   * tagNameを設定します。
   * 
   * @param tagName
   *          tagName
   */
  public void setTagName(String tagName) {
    this.tagName = tagName;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040160";
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
   * effectualCodeを設定します。
   * 
   * @param effectualCode
   *          effectualCode
   */
  public void setEffectualCode(String effectualCode) {
    this.effectualCode = effectualCode;
  }

  /**
   * effectualCodeを取得します。
   * 
   * @return effectualCode
   */
  public String getEffectualCode() {
    return effectualCode;
  }

  /**
   * deleteTableDisplayFlgを取得します。
   * 
   * @return deleteTableDisplayFlg deleteTableDisplayFlg
   */
  public boolean isDeleteTableDisplayFlg() {
    return deleteTableDisplayFlg;
  }

  /**
   * deleteTableDisplayFlgを設定します。
   * 
   * @param deleteTableDisplayFlg
   *          deleteTableDisplayFlg
   */
  public void setDeleteTableDisplayFlg(boolean deleteTableDisplayFlg) {
    this.deleteTableDisplayFlg = deleteTableDisplayFlg;
  }

  /**
   * importTableDisplayFlgを取得します。
   * 
   * @return importTableDisplayFlg
   */
  public boolean isImportTableDisplayFlg() {
    return importTableDisplayFlg;
  }

  /**
   * importTableDisplayFlgを設定します。
   * 
   * @param importTableDisplayFlg
   *          importTableDisplayFlg
   */
  public void setImportTableDisplayFlg(boolean importTableDisplayFlg) {
    this.importTableDisplayFlg = importTableDisplayFlg;
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
   * registerTableDisplayFlgを設定します。
   * 
   * @param registerTableDiaplayFlg
   *          登録テーブル表示フラグ
   */
  public void setRegisterTableDisplayFlg(boolean registerTableDiaplayFlg) {
    this.registerTableDisplayFlg = registerTableDiaplayFlg;
  }

  /**
   * fileUrlを設定します。
   * 
   * @param fileUrl
   *          fileUrl
   */
  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  /**
   * fileUrlを取得します。
   * 
   * @return fileUrl
   */
  public String getFileUrl() {
    return fileUrl;
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
   * interactiveDeleteFlgを取得します。
   * 
   * @return interactiveDeleteFlg
   */
  public boolean isInteractiveDeleteFlg() {
    return interactiveDeleteFlg;
  }

  /**
   * interactiveDeleteFlgを設定します。
   * 
   * @param interactiveDeleteFlg
   *          interactiveDeleteFlg
   */
  public void setInteractiveDeleteFlg(boolean interactiveDeleteFlg) {
    this.interactiveDeleteFlg = interactiveDeleteFlg;
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    searchCommodityCodeStart = reqparam.get("searchCommodityCodeStart");
    searchCommodityCodeEnd = reqparam.get("searchCommodityCodeEnd");
    searchCommodityName = reqparam.get("searchCommodityName");
    checkedCodeList = Arrays.asList(reqparam.getAll("checkBox"));
    fileUrl = reqparam.get("fileUrl");
    edit.setCommodityCode(reqparam.get("commodityCode"));
    edit.setDisplayOrder(reqparam.get("displayOrder"));
    if(StringUtil.hasValue(reqparam.get("sortNum"))){
      edit.setSortNum(reqparam.get("sortNum"));
    }
    if(StringUtil.hasValue(reqparam.get("sortNumJp"))){
      edit.setSortNumJp(reqparam.get("sortNumJp"));
    }
    if(StringUtil.hasValue(reqparam.get("sortNumEn"))){
      edit.setSortNumEn(reqparam.get("sortNumEn"));
    }
    edit.setLang(Arrays.asList(reqparam.getAll("langType")));
  }

  /**
   * updateButtonDisplayFlgを取得します。
   * 
   * @return the updateTableDisplayFlg
   */
  public boolean isUpdateTableDisplayFlg() {
    return updateTableDisplayFlg;
  }

  /**
   * updateButtonDisplayFlgを設定します。
   * 
   * @param updateButtonDisplayFlg
   *          更新テーブル表示フラグ
   */
  public void setUpdateTableDisplayFlg(boolean updateButtonDisplayFlg) {
    this.updateTableDisplayFlg = updateButtonDisplayFlg;
  }

  /**
   * csvHeaderTypeを取得します。
   * 
   * @return csvHeaderType
   */
  public List<NameValue> getCsvHeaderType() {
    return csvHeaderType;
  }

  /**
   * csvHeaderTypeを設定します。
   * 
   * @param csvHeaderType
   *          csvHeaderType
   */
  public void setCsvHeaderType(List<NameValue> csvHeaderType) {
    this.csvHeaderType = csvHeaderType;
  }

  
  /**
   * checkedCodeListを取得します。
   *
   * @return checkedCodeList
   */
  public List<String> getCheckedCodeList() {
    return checkedCodeList;
  }

  
  /**
   * checkedCodeListを設定します。
   *
   * @param checkedCodeList 
   *          checkedCodeList
   */
  public void setCheckedCodeList(List<String> checkedCodeList) {
    this.checkedCodeList = checkedCodeList;
  }

  
  /**
   * @return the langCodes
   */
  public List<CodeAttribute> getLangCodes() {
    return langCodes;
  }

  
  /**
   * @param langCodes the langCodes to set
   */
  public void setLangCodes(List<CodeAttribute> langCodes) {
    this.langCodes = langCodes;
  }

}
