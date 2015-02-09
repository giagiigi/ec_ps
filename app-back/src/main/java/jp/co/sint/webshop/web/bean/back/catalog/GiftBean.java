package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040310:ギフトのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class GiftBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private GiftDetailBean edit = new GiftDetailBean();

  private List<GiftDetailBean> list = new ArrayList<GiftDetailBean>();

  private String fileUrl;

  private String mode;

  private List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();

  @Required
  @Metadata(name = "ショップ")
  private String searchShopCode;

  private boolean displaySearchButton = false;

  private boolean displayEditTable = false;

  private boolean displayUploadTable = false;

  private boolean displaySelectButton = false;

  private boolean displayRegisterButton = false;

  private boolean displayUpdateButton = false;

  private String giftCodeDisplayMode = WebConstantCode.DISPLAY_HIDDEN;

  private TaxType[] taxType = TaxType.values();

  private List<CodeAttribute> giftImageList = new ArrayList<CodeAttribute>();

  /**
   * U1040310:ギフトのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class GiftDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** ショップコード */
    @Length(16)
    @AlphaNum2
    @Metadata(name = "ショップコード", order = 1)
    private String shopCode;

    /** ギフトコード */
    @Required
    @Length(16)
    @AlphaNum2
    @Metadata(name = "ギフトコード", order = 2)
    private String giftCode;

    /** ギフト名 */
    @Required
    @Length(40)
    @Metadata(name = "ギフト名", order = 3)
    private String giftName;

    /** 説明 */
    @Length(200)
    @Metadata(name = "説明", order = 4)
    private String giftDescription;

    /** 価格 */
    @Required
    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "価格", order = 5)
    private String giftPrice;

    /** 表示順 */
    @Length(8)
    @Digit
    @Range(min = 0, max = 99999999)
    @Metadata(name = "表示順", order = 6)
    private String displayOrder;

    /** 消費税区分 */
    @Required
    @Length(1)
    @Metadata(name = "消費税区分", order = 7)
    private String giftTaxType;

    /** 表示区分 */
    @Required
    @Length(1)
    @Bool
    @Metadata(name = "表示区分", order = 8)
    private String displayFlg;

    private String giftImageUrl;

    private Date updateDatetime;

    private boolean displayDeleteButton = false;

    /**
     * giftImageUrlを取得します。
     * 
     * @return giftImageUrl
     */
    public String getGiftImageUrl() {
      return giftImageUrl;
    }

    /**
     * giftImageUrlを設定します。
     * 
     * @param giftImageUrl
     *          giftImageUrl
     */
    public void setGiftImageUrl(String giftImageUrl) {
      this.giftImageUrl = giftImageUrl;
    }

    /**
     * displayFlgを取得します。
     * 
     * @return displayFlg
     */
    public String getDisplayFlg() {
      return displayFlg;
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
     * giftCodeを取得します。
     * 
     * @return giftCode
     */
    public String getGiftCode() {
      return giftCode;
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
     * giftTaxTypeを取得します。
     * 
     * @return giftTaxType
     */
    public String getGiftTaxType() {
      return giftTaxType;
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
     * displayFlgを設定します。
     * 
     * @param displayFlg
     *          displayFlg
     */
    public void setDisplayFlg(String displayFlg) {
      this.displayFlg = displayFlg;
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
     * giftCodeを設定します。
     * 
     * @param giftCode
     *          giftCode
     */
    public void setGiftCode(String giftCode) {
      this.giftCode = giftCode;
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
     * giftTaxTypeを設定します。
     * 
     * @param giftTaxType
     *          giftTaxType
     */
    public void setGiftTaxType(String giftTaxType) {
      this.giftTaxType = giftTaxType;
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
     *          updateDatetime
     */
    public void setUpdateDatetime(Date updateDatetime) {
      this.updateDatetime = DateUtil.immutableCopy(updateDatetime);
    }

    /**
     * displayDeleteButtonを取得します。
     * 
     * @return displayDeleteButton
     */
    public boolean isDisplayDeleteButton() {
      return displayDeleteButton;
    }

    /**
     * displayDeleteButtonを設定します。
     * 
     * @param displayDeleteButton
     *          displayDeleteButton
     */
    public void setDisplayDeleteButton(boolean displayDeleteButton) {
      this.displayDeleteButton = displayDeleteButton;
    }

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
   * modeを取得します。
   * 
   * @return mode
   */
  public String getMode() {
    return mode;
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
   * modeを設定します。
   * 
   * @param mode
   *          mode
   */
  public void setMode(String mode) {
    this.mode = mode;
  }

  /**
   * CategoryDetailBeanを取得します。
   * 
   * @return CategoryDetailBean
   */
  public GiftDetailBean getEdit() {
    return edit;
  }

  /**
   * ギフト一覧を取得します。
   * 
   * @return List
   */
  public List<GiftDetailBean> getList() {
    return list;
  }

  /**
   * GiftDetailBeanを設定します。
   * 
   * @param edit
   *          ギフト情報
   */
  public void setEdit(GiftDetailBean edit) {
    this.edit = edit;
  }

  /**
   * ギフト一覧を設定します。
   * 
   * @param list
   *          ギフト一覧
   */
  public void setList(List<GiftDetailBean> list) {
    this.list = list;
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
   * shopListを取得します。
   * 
   * @return shopList
   */
  public List<CodeAttribute> getShopList() {
    return shopList;
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
   * shopListを設定します。
   * 
   * @param shopList
   *          shopList
   */
  public void setShopList(List<CodeAttribute> shopList) {
    this.shopList = shopList;
  }

  /**
   * displayEditTableを取得します。
   * 
   * @return displayEditTable
   */
  public boolean isDisplayEditTable() {
    return displayEditTable;
  }

  /**
   * displaySearchButtonを取得します。
   * 
   * @return displaySearchButton
   */
  public boolean isDisplaySearchButton() {
    return displaySearchButton;
  }

  /**
   * displaySelectButtonを取得します。
   * 
   * @return displaySelectButton
   */
  public boolean isDisplaySelectButton() {
    return displaySelectButton;
  }

  /**
   * displayEditTableを設定します。
   * 
   * @param displayEditTable
   *          displayEditTable
   */
  public void setDisplayEditTable(boolean displayEditTable) {
    this.displayEditTable = displayEditTable;
  }

  /**
   * displaySearchButtonを設定します。
   * 
   * @param displaySearchButton
   *          displaySearchButton
   */
  public void setDisplaySearchButton(boolean displaySearchButton) {
    this.displaySearchButton = displaySearchButton;
  }

  /**
   * displaySelectButtonを設定します。
   * 
   * @param displaySelectButton
   *          displaySelectButton
   */
  public void setDisplaySelectButton(boolean displaySelectButton) {
    this.displaySelectButton = displaySelectButton;
  }

  /**
   * displayRegisterButtonを取得します。
   * 
   * @return displayRegisterButton
   */
  public boolean isDisplayRegisterButton() {
    return displayRegisterButton;
  }

  /**
   * displayUpdateButtonを取得します。
   * 
   * @return displayUpdateButton
   */
  public boolean isDisplayUpdateButton() {
    return displayUpdateButton;
  }

  /**
   * displayRegisterButtonを設定します。
   * 
   * @param displayRegisterButton
   *          displayRegisterButton
   */
  public void setDisplayRegisterButton(boolean displayRegisterButton) {
    this.displayRegisterButton = displayRegisterButton;
  }

  /**
   * displayUpdateButtonを設定します。
   * 
   * @param displayUpdateButton
   *          displayUpdateButton
   */
  public void setDisplayUpdateButton(boolean displayUpdateButton) {
    this.displayUpdateButton = displayUpdateButton;
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
    this.setSearchShopCode(reqparam.get("searchShopCode"));
    reqparam.copy(edit);
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040310";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.GiftBean.0");
  }

  /**
   * giftCodeDisplayModeを取得します。
   * 
   * @return giftCodeDisplayMode
   */
  public String getGiftCodeDisplayMode() {
    return giftCodeDisplayMode;
  }

  /**
   * giftCodeDisplayModeを設定します。
   * 
   * @param giftCodeDisplayMode
   *          giftCodeDisplayMode
   */
  public void setGiftCodeDisplayMode(String giftCodeDisplayMode) {
    this.giftCodeDisplayMode = giftCodeDisplayMode;
  }

  /**
   * displayUploadTableを取得します。
   * 
   * @return displayUploadTable displayUploadTable
   */
  public boolean isDisplayUploadTable() {
    return displayUploadTable;
  }

  /**
   * displayUploadTableを設定します。
   * 
   * @param displayUploadTable
   *          displayUploadTable
   */
  public void setDisplayUploadTable(boolean displayUploadTable) {
    this.displayUploadTable = displayUploadTable;
  }

  /**
   * taxTypeを取得します。
   * 
   * @return the taxType
   */
  public TaxType[] getTaxType() {
    return ArrayUtil.immutableCopy(taxType);
  }

  /**
   * taxTypeを設定します。
   * 
   * @param taxType
   *          taxType
   */
  public void setTaxType(TaxType[] taxType) {
    this.taxType = ArrayUtil.immutableCopy(taxType);
  }

  /**
   * giftImageListを取得します。
   * 
   * @return giftImageList
   */
  public List<CodeAttribute> getGiftImageList() {
    return giftImageList;
  }

  /**
   * giftImageListを設定します。
   * 
   * @param giftImageList
   *          giftImageList
   */
  public void setGiftImageList(List<CodeAttribute> giftImageList) {
    this.giftImageList = giftImageList;
  }

}
