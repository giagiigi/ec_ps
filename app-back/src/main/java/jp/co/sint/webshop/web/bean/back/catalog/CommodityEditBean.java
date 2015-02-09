package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.JanCode;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.attribute.Url;
import jp.co.sint.webshop.data.domain.ArrivalGoodsFlg;
import jp.co.sint.webshop.data.domain.ClearCommodityType;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.ImportCommodityType;
import jp.co.sint.webshop.data.domain.NewReserveCommodityType1;
import jp.co.sint.webshop.data.domain.NewReserveCommodityType2;
import jp.co.sint.webshop.data.domain.NewReserveCommodityType3;
import jp.co.sint.webshop.data.domain.NewReserveCommodityType4;
import jp.co.sint.webshop.data.domain.NewReserveCommodityType5;
import jp.co.sint.webshop.data.domain.ReserveCommodityType1;
import jp.co.sint.webshop.data.domain.ReserveCommodityType2;
import jp.co.sint.webshop.data.domain.ReserveCommodityType3;
import jp.co.sint.webshop.data.domain.ReturnFlag;
import jp.co.sint.webshop.data.domain.SaleFlg;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.domain.WarningFlag;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040120:商品登録のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CommodityEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private CommodityEditHeaderBean header = new CommodityEditHeaderBean();

  private CommodityEditSkuBean sku = new CommodityEditSkuBean();

  private List<NameValue> displaySaleflgList = NameValue.asList("0:" + "停止销售" + "/1:" + "销售中");

  private String shopCode;

  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード")
  private String commodityCode;

  private String mode;

  private String fileUrl;

  private String commodityCodeDisplayMode;

  private String commodityEditDisplayMode;

  // 套装商品登录标记
  private boolean displaySuitCommodity = true;

  private boolean displayPreviewButton = false;

  private boolean displayMoveSkuButton = false;

  private boolean displayNextButton = false;

  private boolean displayRegisterButton = false;

  private boolean displayUpdateButton = false;

  private boolean displayCancelButton = false;

  private boolean displayImageTable = false;

  private boolean displayUploadTable = false;

  private boolean displayImageDeleteButton = false;

  private boolean displayThumbnailDeleteButton = false;

  private boolean displayMobileThumbnailDeleteButton = false;

  private boolean displayRepresentSkuImageDeleteButton = false;

  private boolean displayMobileRepresentSkuImageDeleteButton = false;

  private boolean readOnlyMode = false;

  // 20120130 ysy add start
  private ReturnFlag[] returnFlag = ReturnFlag.values();

  private WarningFlag[] warningFlag = WarningFlag.values();

  // 20120130 ysy add end

  private SaleFlg[] saleFlg = SaleFlg.values();

  private ImportCommodityType[] importCommodityType = ImportCommodityType.values();

  private ClearCommodityType[] clearCommodityType = ClearCommodityType.values();

  private ReserveCommodityType1[] reserveCommodityType1 = ReserveCommodityType1.values();

  private ReserveCommodityType2[] reserveCommodityType2 = ReserveCommodityType2.values();

  private ReserveCommodityType3[] reserveCommodityType3 = ReserveCommodityType3.values();

  private NewReserveCommodityType1[] newReserveCommodityType1 = NewReserveCommodityType1.values();

  private NewReserveCommodityType2[] newReserveCommodityType2 = NewReserveCommodityType2.values();

  private NewReserveCommodityType3[] newReserveCommodityType3 = NewReserveCommodityType3.values();

  private NewReserveCommodityType4[] newReserveCommodityType4 = NewReserveCommodityType4.values();

  private NewReserveCommodityType5[] newReserveCommodityType5 = NewReserveCommodityType5.values();

  /**
   * @return the reserveCommodityType2
   */
  public ReserveCommodityType2[] getReserveCommodityType2() {
    return reserveCommodityType2;
  }

  /**
   * @param reserveCommodityType2
   *          the reserveCommodityType2 to set
   */
  public void setReserveCommodityType2(ReserveCommodityType2[] reserveCommodityType2) {
    this.reserveCommodityType2 = reserveCommodityType2;
  }

  /**
   * @return the reserveCommodityType3
   */
  public ReserveCommodityType3[] getReserveCommodityType3() {
    return reserveCommodityType3;
  }

  /**
   * @param reserveCommodityType3
   *          the reserveCommodityType3 to set
   */
  public void setReserveCommodityType3(ReserveCommodityType3[] reserveCommodityType3) {
    this.reserveCommodityType3 = reserveCommodityType3;
  }

  /**
   * @return the newReserveCommodityType1
   */
  public NewReserveCommodityType1[] getNewReserveCommodityType1() {
    return newReserveCommodityType1;
  }

  /**
   * @param newReserveCommodityType1
   *          the newReserveCommodityType1 to set
   */
  public void setNewReserveCommodityType1(NewReserveCommodityType1[] newReserveCommodityType1) {
    this.newReserveCommodityType1 = newReserveCommodityType1;
  }

  /**
   * @return the newReserveCommodityType2
   */
  public NewReserveCommodityType2[] getNewReserveCommodityType2() {
    return newReserveCommodityType2;
  }

  /**
   * @param newReserveCommodityType2
   *          the newReserveCommodityType2 to set
   */
  public void setNewReserveCommodityType2(NewReserveCommodityType2[] newReserveCommodityType2) {
    this.newReserveCommodityType2 = newReserveCommodityType2;
  }

  /**
   * @return the newReserveCommodityType3
   */
  public NewReserveCommodityType3[] getNewReserveCommodityType3() {
    return newReserveCommodityType3;
  }

  /**
   * @param newReserveCommodityType3
   *          the newReserveCommodityType3 to set
   */
  public void setNewReserveCommodityType3(NewReserveCommodityType3[] newReserveCommodityType3) {
    this.newReserveCommodityType3 = newReserveCommodityType3;
  }

  /**
   * @return the newReserveCommodityType4
   */
  public NewReserveCommodityType4[] getNewReserveCommodityType4() {
    return newReserveCommodityType4;
  }

  /**
   * @param newReserveCommodityType4
   *          the newReserveCommodityType4 to set
   */
  public void setNewReserveCommodityType4(NewReserveCommodityType4[] newReserveCommodityType4) {
    this.newReserveCommodityType4 = newReserveCommodityType4;
  }

  /**
   * @return the newReserveCommodityType5
   */
  public NewReserveCommodityType5[] getNewReserveCommodityType5() {
    return newReserveCommodityType5;
  }

  /**
   * @param newReserveCommodityType5
   *          the newReserveCommodityType5 to set
   */
  public void setNewReserveCommodityType5(NewReserveCommodityType5[] newReserveCommodityType5) {
    this.newReserveCommodityType5 = newReserveCommodityType5;
  }

  private TaxType[] taxType = TaxType.values();

  private ArrivalGoodsFlg[] arrivalGoodsFlg = ArrivalGoodsFlg.values();

  private DisplayClientType[] displayClientType = DisplayClientType.values();

  // add by tangweihui 2012-11-16 start
  private CommodityType[] commodityTypeList = CommodityType.values();

  private SetCommodityFlg[] setCommodityFlgList = SetCommodityFlg.values();

  // add by tangweihui 2012-11-16 end
  private StockManagementType[] stockManagementType = StockManagementType.values();

  private boolean displayPreview = false;

  private String previewUrl;

  private String previewDigest;

  private List<CodeAttribute> imageList = new ArrayList<CodeAttribute>();

  /**
   * U1040120:商品登録のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CommodityEditHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Required
    @Length(1)
    @Bool
    @Metadata(name = "販売フラグ")
    private String saleFlg;

    @Required
    @Length(50)
    @Metadata(name = "商品名")
    private String commodityName;

    // 20120109 ysy add start
    @Length(200)
    @Metadata(name = "商品名(英文)")
    private String commodityNameEn;

    @Length(200)
    @Metadata(name = "商品名(日文)")
    private String commodityNameJp;

    @Length(16)
    @Metadata(name = "品牌名称")
    private String brandCode;

    private String brand;

    @Required
    @Length(1)
    @Metadata(name = "返品不可フラグ")
    private String returnFlag;

    @Length(1)
    @Metadata(name = "ワーニング区分")
    private String warningFlag;

    @Length(2)
    @Metadata(name = "リードタイム")
    private String leadTime;

    @Length(5)
    @Metadata(name = "セール区分")
    private String saleFlag;

    @Length(5)
    @Metadata(name = "特集区分")
    private String specFlag;

    // 20120109 ysy add end

    @Required
    @Length(1)
    @Metadata(name = "表示クライアント区分")
    private String displayClientType;

    // add by tangweihui 2012-11-16 start
    @Required
    @Length(1)
    @Metadata(name = "商品区分")
    private String commodityType;

    @Length(1)
    @Metadata(name = "进口商品区分")
    private String importCommodityType;

    @Length(1)
    @Metadata(name = "清仓商品区分")
    private String clearCommodityType;

    @Length(1)
    @Metadata(name = "Asahi商品区分")
    private String reserveCommodityType1;

    @Length(1)
    @Metadata(name = "hot商品区分")
    private String reserveCommodityType2;

    @Length(1)
    @Metadata(name = "include商品区分")
    private String reserveCommodityType3;

    @Length(1)
    @Metadata(name = "预留区分1*")
    private String newReserveCommodityType1;

    @Length(1)
    @Metadata(name = "预留区分2*")
    private String newReserveCommodityType2;

    @Length(1)
    @Metadata(name = "预留区分3*")
    private String newReserveCommodityType3;

    @Length(1)
    @Metadata(name = "预留区分4*")
    private String newReserveCommodityType4;

    @Length(1)
    @Metadata(name = "预留区分5*")
    private String newReserveCommodityType5;

    // add by zhangzhengtao 20130527 start
    /** 検索Keyword（中文）2 */
    @Length(30)
    @Metadata(name = " 検索Keyword（中文）2")
    private String keywordCn2;

    /** 検索Keyword（日文）2 */
    @Length(30)
    @Metadata(name = " 検索Keyword（日文）2")
    private String keywordJp2;

    /** 検索Keyword（英文）2 */
    @Length(30)
    @Metadata(name = " 検索Keyword（英文）2")
    private String keywordEn2;

    // add by zhangzhengtao 20130527 end

    @Length(1)
    @Metadata(name = "English热卖标志")
    private String hotFlgEn;

    @Length(1)
    @Metadata(name = "Japan热卖标志")
    private String hotFlgJp;

    // 20130808 txw add start
    @Length(60)
    @Metadata(name = "TITLE")
    private String title;

    @Length(60)
    @Metadata(name = "TITLE(英文)")
    private String titleEn;

    @Length(60)
    @Metadata(name = "TITLE(日文)")
    private String titleJp;

    @Length(100)
    @Metadata(name = "DESCRIPTION")
    private String description;

    @Length(100)
    @Metadata(name = "DESCRIPTION(英文)")
    private String descriptionEn;

    @Length(100)
    @Metadata(name = "DESCRIPTION(日文)")
    private String descriptionJp;

    @Length(100)
    @Metadata(name = "KEYWORD")
    private String keyword;

    @Length(100)
    @Metadata(name = "KEYWORD(英文)")
    private String keywordEn;

    @Length(100)
    @Metadata(name = "KEYWORD(日文)")
    private String keywordJp;

    // 20130808 txw add end

    /**
     * @return the reserveCommodityType2
     */
    public String getReserveCommodityType2() {
      return reserveCommodityType2;
    }

    /**
     * @return the keywordCn2
     */
    public String getKeywordCn2() {
      return keywordCn2;
    }

    /**
     * @param keywordCn2
     *          the keywordCn2 to set
     */
    public void setKeywordCn2(String keywordCn2) {
      this.keywordCn2 = keywordCn2;
    }

    /**
     * @return the keywordJp2
     */
    public String getKeywordJp2() {
      return keywordJp2;
    }

    /**
     * @param keywordJp2
     *          the keywordJp2 to set
     */
    public void setKeywordJp2(String keywordJp2) {
      this.keywordJp2 = keywordJp2;
    }

    /**
     * @return the keywordEn2
     */
    public String getKeywordEn2() {
      return keywordEn2;
    }

    /**
     * @param keywordEn2
     *          the keywordEn2 to set
     */
    public void setKeywordEn2(String keywordEn2) {
      this.keywordEn2 = keywordEn2;
    }

    /**
     * @param reserveCommodityType2
     *          the reserveCommodityType2 to set
     */
    public void setReserveCommodityType2(String reserveCommodityType2) {
      this.reserveCommodityType2 = reserveCommodityType2;
    }

    /**
     * @return the reserveCommodityType3
     */
    public String getReserveCommodityType3() {
      return reserveCommodityType3;
    }

    /**
     * @param reserveCommodityType3
     *          the reserveCommodityType3 to set
     */
    public void setReserveCommodityType3(String reserveCommodityType3) {
      this.reserveCommodityType3 = reserveCommodityType3;
    }

    /**
     * @return the newReserveCommodityType1
     */
    public String getNewReserveCommodityType1() {
      return newReserveCommodityType1;
    }

    /**
     * @param newReserveCommodityType1
     *          the newReserveCommodityType1 to set
     */
    public void setNewReserveCommodityType1(String newReserveCommodityType1) {
      this.newReserveCommodityType1 = newReserveCommodityType1;
    }

    /**
     * @return the newReserveCommodityType2
     */
    public String getNewReserveCommodityType2() {
      return newReserveCommodityType2;
    }

    /**
     * @param newReserveCommodityType2
     *          the newReserveCommodityType2 to set
     */
    public void setNewReserveCommodityType2(String newReserveCommodityType2) {
      this.newReserveCommodityType2 = newReserveCommodityType2;
    }

    /**
     * @return the newReserveCommodityType3
     */
    public String getNewReserveCommodityType3() {
      return newReserveCommodityType3;
    }

    /**
     * @param newReserveCommodityType3
     *          the newReserveCommodityType3 to set
     */
    public void setNewReserveCommodityType3(String newReserveCommodityType3) {
      this.newReserveCommodityType3 = newReserveCommodityType3;
    }

    /**
     * @return the newReserveCommodityType4
     */
    public String getNewReserveCommodityType4() {
      return newReserveCommodityType4;
    }

    /**
     * @param newReserveCommodityType4
     *          the newReserveCommodityType4 to set
     */
    public void setNewReserveCommodityType4(String newReserveCommodityType4) {
      this.newReserveCommodityType4 = newReserveCommodityType4;
    }

    /**
     * @return the newReserveCommodityType5
     */
    public String getNewReserveCommodityType5() {
      return newReserveCommodityType5;
    }

    /**
     * @param newReserveCommodityType5
     *          the newReserveCommodityType5 to set
     */
    public void setNewReserveCommodityType5(String newReserveCommodityType5) {
      this.newReserveCommodityType5 = newReserveCommodityType5;
    }

    @Required
    @Length(1)
    @Metadata(name = "套装商品区分")
    private String setCommodityFlg;

    // add by tangweihui 2012-11-16 end
    @Required
    @Length(8)
    @AlphaNum2
    @Metadata(name = "配送種別")
    private String deliveryTypeNo;

    private List<CodeAttribute> deliveyTypeName = new ArrayList<CodeAttribute>();

    @Length(4)
    @Digit
    @Range(min = 0, max = 9999)
    @Metadata(name = "おすすめ商品順位")
    private String recommendCommodityRank;

    @Required
    @Length(1)
    @Metadata(name = "入荷お知らせ申し込み")
    private String arrivalGoodsFlg;

    @Required
    @Length(1)
    @Metadata(name = "消費税区分")
    private String taxType;

    @Length(3)
    @Digit
    @Range(min = 0, max = 100)
    @Metadata(name = "商品別ポイント付与率")
    private String commodityPointRate;

    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Metadata(name = "商品別ポイント付与期間(From)")
    private String commodityPointStartDatetime;

    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Metadata(name = "商品別ポイント付与期間(To)")
    private String commodityPointEndDatetime;

    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Metadata(name = "販売期間(From)")
    private String saleStartDatetime;

    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Metadata(name = "販売期間(To)")
    private String saleEndDatetime;

    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Metadata(name = "特別価格期間(From)")
    private String discountPriceStartDatetime;

    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Metadata(name = "特別価格期間(To)")
    private String discountPriceEndDatetime;

    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Metadata(name = "予約期間(From)")
    private String reservationStartDatetime;

    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Metadata(name = "予約期間(To)")
    private String reservationEndDatetime;

    @Datetime(format = DateUtil.DEFAULT_DATE_FORMAT)
    @Metadata(name = "価格改定日")
    private String salePriceChangeDatetime;

    @Required
    @Length(1)
    @Metadata(name = "在庫管理区分")
    private String stockManagementType;

    @Length(8)
    @Digit
    @Metadata(name = "在庫状況番号")
    private String stockStatusNo;

    private List<CodeAttribute> stockStatusName = new ArrayList<CodeAttribute>();

    @Length(1000)
    @Metadata(name = "PC商品説明")
    private String commodityDescription;

    @Length(1000)
    @Metadata(name = "PC商品説明(英文)")
    private String commodityDescriptionEn;

    @Length(1000)
    @Metadata(name = "PC商品説明(日文)")
    private String commodityDescriptionJp;

    @Length(500)
    @Metadata(name = "携帯商品説明")
    private String commodityDescriptionShort;

    @Length(500)
    @Metadata(name = "携帯商品説明(英文)")
    private String commodityDescriptionShortEn;

    @Length(500)
    @Metadata(name = "携帯商品説明(日文)")
    private String commodityDescriptionShortJp;

    @Length(500)
    @Metadata(name = "商品検索ワード")
    private String commoditySearchWords;

    @Length(256)
    @Url
    @Metadata(name = "リンクURL")
    private String linkUrl;

    private Date updateDatetime;

    /**
     * @return the reserveCommodityType1
     */
    public String getReserveCommodityType1() {
      return reserveCommodityType1;
    }

    /**
     * @param reserveCommodityType1
     *          the reserveCommodityType1 to set
     */
    public void setReserveCommodityType1(String reserveCommodityType1) {
      this.reserveCommodityType1 = reserveCommodityType1;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
      return brand;
    }

    /**
     * @param brand
     *          the brand to set
     */
    public void setBrand(String brand) {
      this.brand = brand;
    }

    /**
     * @return the returnFlag
     */
    public String getReturnFlag() {
      return returnFlag;
    }

    /**
     * @param returnFlag
     *          the returnFlag to set
     */
    public void setReturnFlag(String returnFlag) {
      this.returnFlag = returnFlag;
    }

    /**
     * @return the warningFlag
     */
    public String getWarningFlag() {
      return warningFlag;
    }

    /**
     * @param warningFlag
     *          the warningFlag to set
     */
    public void setWarningFlag(String warningFlag) {
      this.warningFlag = warningFlag;
    }

    /**
     * @return the leadTime
     */
    public String getLeadTime() {
      return leadTime;
    }

    /**
     * @param leadTime
     *          the leadTime to set
     */
    public void setLeadTime(String leadTime) {
      this.leadTime = leadTime;
    }

    /**
     * @return the saleFlag
     */
    public String getSaleFlag() {
      return saleFlag;
    }

    /**
     * @param saleFlag
     *          the saleFlag to set
     */
    public void setSaleFlag(String saleFlag) {
      this.saleFlag = saleFlag;
    }

    /**
     * @return the specFlag
     */
    public String getSpecFlag() {
      return specFlag;
    }

    /**
     * @param specFlag
     *          the specFlag to set
     */
    public void setSpecFlag(String specFlag) {
      this.specFlag = specFlag;
    }

    /**
     * commodityDescriptionを取得します。
     * 
     * @return commodityDescription
     */
    public String getCommodityDescription() {
      return commodityDescription;
    }

    /**
     * @return the commodityNameEn
     */
    public String getCommodityNameEn() {
      return commodityNameEn;
    }

    /**
     * @param commodityNameEn
     *          the commodityNameEn to set
     */
    public void setCommodityNameEn(String commodityNameEn) {
      this.commodityNameEn = commodityNameEn;
    }

    /**
     * @return the brandCode
     */
    public String getBrandCode() {
      return brandCode;
    }

    /**
     * @param brandCode
     *          the brandCode to set
     */
    public void setBrandCode(String brandCode) {
      this.brandCode = brandCode;
    }

    /**
     * commodityDescriptionShortを取得します。
     * 
     * @return commodityDescriptionShort
     */
    public String getCommodityDescriptionShort() {
      return commodityDescriptionShort;
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
     * commodityPointEndDatetimeを取得します。
     * 
     * @return commodityPointEndDatetime
     */
    public String getCommodityPointEndDatetime() {
      return commodityPointEndDatetime;
    }

    /**
     * commodityPointRateを取得します。
     * 
     * @return commodityPointRate
     */
    public String getCommodityPointRate() {
      return commodityPointRate;
    }

    /**
     * commodityPointStartDatetimeを取得します。
     * 
     * @return commodityPointStartDatetime
     */
    public String getCommodityPointStartDatetime() {
      return commodityPointStartDatetime;
    }

    /**
     * commoditySearchWordsを取得します。
     * 
     * @return commoditySearchWords
     */
    public String getCommoditySearchWords() {
      return commoditySearchWords;
    }

    /**
     * deliveyTypeNoを取得します。
     * 
     * @return deliveryTypeNo
     */
    public String getDeliveryTypeNo() {
      return deliveryTypeNo;
    }

    /**
     * discountPriceEndDatetimeを取得します。
     * 
     * @return discountPriceEndDatetime
     */
    public String getDiscountPriceEndDatetime() {
      return discountPriceEndDatetime;
    }

    /**
     * discountPriceStartDatetimeを取得します。
     * 
     * @return discountPriceStartDatetime
     */
    public String getDiscountPriceStartDatetime() {
      return discountPriceStartDatetime;
    }

    /**
     * displayClientTypeを取得します。
     * 
     * @return displayClientType
     */
    public String getDisplayClientType() {
      return displayClientType;
    }

    public String getCommodityType() {
      return commodityType;
    }

    public void setCommodityType(String commodityType) {
      this.commodityType = commodityType;
    }

    public String getSetCommodityFlg() {
      return setCommodityFlg;
    }

    public void setSetCommodityFlg(String setCommodityFlg) {
      this.setCommodityFlg = setCommodityFlg;
    }

    /**
     * linkUrlを取得します。
     * 
     * @return linkUrl
     */
    public String getLinkUrl() {
      return linkUrl;
    }

    /**
     * recommendCommodityRankを取得します。
     * 
     * @return recommendCommodityRank
     */
    public String getRecommendCommodityRank() {
      return recommendCommodityRank;
    }

    /**
     * reservationEndDatetimeを取得します。
     * 
     * @return reservationEndDatetime
     */
    public String getReservationEndDatetime() {
      return reservationEndDatetime;
    }

    /**
     * reservationStartDatetimeを取得します。
     * 
     * @return reservationStartDatetime
     */
    public String getReservationStartDatetime() {
      return reservationStartDatetime;
    }

    /**
     * saleEndDatetimeを取得します。
     * 
     * @return saleEndDatetime
     */
    public String getSaleEndDatetime() {
      return saleEndDatetime;
    }

    /**
     * saleFlgを取得します。
     * 
     * @return saleFlg
     */
    public String getSaleFlg() {
      return saleFlg;
    }

    /**
     * salePriceChangeDatetimeを取得します。
     * 
     * @return salePriceChangeDatetime
     */
    public String getSalePriceChangeDatetime() {
      return salePriceChangeDatetime;
    }

    /**
     * saleStartDatetimeを取得します。
     * 
     * @return saleStartDatetime
     */
    public String getSaleStartDatetime() {
      return saleStartDatetime;
    }

    /**
     * stockManagementTypeを取得します。
     * 
     * @return stockManagementType
     */
    public String getStockManagementType() {
      return stockManagementType;
    }

    /**
     * stockStatusNoを取得します。
     * 
     * @return stockStatusNo
     */
    public String getStockStatusNo() {
      return stockStatusNo;
    }

    /**
     * commodityDescriptionを設定します。
     * 
     * @param commodityDescription
     *          commodityDescription
     */
    public void setCommodityDescription(String commodityDescription) {
      this.commodityDescription = commodityDescription;
    }

    /**
     * commodityDescriptionShortを設定します。
     * 
     * @param commodityDescriptionShort
     *          commodityDescriptionShort
     */
    public void setCommodityDescriptionShort(String commodityDescriptionShort) {
      this.commodityDescriptionShort = commodityDescriptionShort;
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
     * commodityPointEndDatetimeを設定します。
     * 
     * @param commodityPointEndDatetime
     *          commodityPointEndDatetime
     */
    public void setCommodityPointEndDatetime(String commodityPointEndDatetime) {
      this.commodityPointEndDatetime = commodityPointEndDatetime;
    }

    /**
     * commodityPointRateを設定します。
     * 
     * @param commodityPointRate
     *          commodityPointRate
     */
    public void setCommodityPointRate(String commodityPointRate) {
      this.commodityPointRate = commodityPointRate;
    }

    /**
     * commodityPointStartDatetimeを設定します。
     * 
     * @param commodityPointStartDatetime
     *          commodityPointStartDatetime
     */
    public void setCommodityPointStartDatetime(String commodityPointStartDatetime) {
      this.commodityPointStartDatetime = commodityPointStartDatetime;
    }

    /**
     * commoditySearchWordsを設定します。
     * 
     * @param commoditySearchWords
     *          commoditySearchWords
     */
    public void setCommoditySearchWords(String commoditySearchWords) {
      this.commoditySearchWords = commoditySearchWords;
    }

    /**
     * deliveyTypeNoを設定します。
     * 
     * @param deliveyTypeNo
     *          deliveyTypeNo
     */
    public void setDeliveryTypeNo(String deliveyTypeNo) {
      this.deliveryTypeNo = deliveyTypeNo;
    }

    /**
     * discountPriceEndDatetimeを設定します。
     * 
     * @param discountPriceEndDatetime
     *          discountPriceEndDatetime
     */
    public void setDiscountPriceEndDatetime(String discountPriceEndDatetime) {
      this.discountPriceEndDatetime = discountPriceEndDatetime;
    }

    /**
     * discountPriceStartDatetimeを設定します。
     * 
     * @param startDatetime
     *          startDatetime
     */
    public void setDiscountPriceStartDatetime(String startDatetime) {
      this.discountPriceStartDatetime = startDatetime;
    }

    /**
     * displayClientTypeを設定します。
     * 
     * @param displayClientType
     *          displayClientType
     */
    public void setDisplayClientType(String displayClientType) {
      this.displayClientType = displayClientType;
    }

    /**
     * linkUrlを設定します。
     * 
     * @param linkUrl
     *          linkUrl
     */
    public void setLinkUrl(String linkUrl) {
      this.linkUrl = linkUrl;
    }

    /**
     * recommendCommodityRankを設定します。
     * 
     * @param recommendCommodityRank
     *          recommendCommodityRank
     */
    public void setRecommendCommodityRank(String recommendCommodityRank) {
      this.recommendCommodityRank = recommendCommodityRank;
    }

    /**
     * reservationEndDatetimeを設定します。
     * 
     * @param reservationEndDatetime
     *          reservationEndDatetime
     */
    public void setReservationEndDatetime(String reservationEndDatetime) {
      this.reservationEndDatetime = reservationEndDatetime;
    }

    /**
     * reservationStartDatetimeを設定します。
     * 
     * @param reservationStartDatetime
     *          reservationStartDatetime
     */
    public void setReservationStartDatetime(String reservationStartDatetime) {
      this.reservationStartDatetime = reservationStartDatetime;
    }

    /**
     * saleEndDatetimeを設定します。
     * 
     * @param saleEndDatetime
     *          saleEndDatetime
     */
    public void setSaleEndDatetime(String saleEndDatetime) {
      this.saleEndDatetime = saleEndDatetime;
    }

    /**
     * saleFlgを設定します。
     * 
     * @param saleFlg
     *          saleFlg
     */
    public void setSaleFlg(String saleFlg) {
      this.saleFlg = saleFlg;
    }

    /**
     * salePriceChangeDatetimeを設定します。
     * 
     * @param salePriceChangeDatetime
     *          salePriceChangeDatetime
     */
    public void setSalePriceChangeDatetime(String salePriceChangeDatetime) {
      this.salePriceChangeDatetime = salePriceChangeDatetime;
    }

    /**
     * saleStartDatetimeを設定します。
     * 
     * @param saleStartDatetime
     *          saleStartDatetime
     */
    public void setSaleStartDatetime(String saleStartDatetime) {
      this.saleStartDatetime = saleStartDatetime;
    }

    /**
     * stockManagementTypeを設定します。
     * 
     * @param stockManagementType
     *          stockManagementType
     */
    public void setStockManagementType(String stockManagementType) {
      this.stockManagementType = stockManagementType;
    }

    /**
     * stockStatusNoを設定します。
     * 
     * @param stockStatusNo
     *          stockStatusNo
     */
    public void setStockStatusNo(String stockStatusNo) {
      this.stockStatusNo = stockStatusNo;
    }

    /**
     * deliveyTypeNameを取得します。
     * 
     * @return deliveyTypeName
     */
    public List<CodeAttribute> getDeliveyTypeName() {
      return deliveyTypeName;
    }

    /**
     * stockStatusNameを取得します。
     * 
     * @return stockStatusName
     */
    public List<CodeAttribute> getStockStatusName() {
      return stockStatusName;
    }

    /**
     * deliveyTypeNameを設定します。
     * 
     * @param deliveyTypeName
     *          deliveyTypeName
     */
    public void setDeliveyTypeName(List<CodeAttribute> deliveyTypeName) {
      this.deliveyTypeName = deliveyTypeName;
    }

    /**
     * stockStatusNameを設定します。
     * 
     * @param stockStatusName
     *          stockStatusName
     */
    public void setStockStatusName(List<CodeAttribute> stockStatusName) {
      this.stockStatusName = stockStatusName;
    }

    /**
     * taxTypeを取得します。
     * 
     * @return taxType
     */
    public String getTaxType() {
      return taxType;
    }

    /**
     * taxTypeを設定します。
     * 
     * @param taxType
     *          taxType
     */
    public void setTaxType(String taxType) {
      this.taxType = taxType;
    }

    /**
     * arrivalGoodsFlgを取得します。
     * 
     * @return arrivalGoodsFlg
     */
    public String getArrivalGoodsFlg() {
      return arrivalGoodsFlg;
    }

    /**
     * arrivalGoodsFlgを設定します。
     * 
     * @param arrivalGoodsFlg
     *          arrivalGoodsFlg
     */
    public void setArrivalGoodsFlg(String arrivalGoodsFlg) {
      this.arrivalGoodsFlg = arrivalGoodsFlg;
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
     * @param commodityNameJp
     *          the commodityNameJp to set
     */
    public void setCommodityNameJp(String commodityNameJp) {
      this.commodityNameJp = commodityNameJp;
    }

    /**
     * @return the commodityNameJp
     */
    public String getCommodityNameJp() {
      return commodityNameJp;
    }

    /**
     * @param commodityDescriptionEn
     *          the commodityDescriptionEn to set
     */
    public void setCommodityDescriptionEn(String commodityDescriptionEn) {
      this.commodityDescriptionEn = commodityDescriptionEn;
    }

    /**
     * @return the commodityDescriptionEn
     */
    public String getCommodityDescriptionEn() {
      return commodityDescriptionEn;
    }

    /**
     * @param commodityDescriptionJp
     *          the commodityDescriptionJp to set
     */
    public void setCommodityDescriptionJp(String commodityDescriptionJp) {
      this.commodityDescriptionJp = commodityDescriptionJp;
    }

    /**
     * @return the commodityDescriptionJp
     */
    public String getCommodityDescriptionJp() {
      return commodityDescriptionJp;
    }

    /**
     * @param commodityDescriptionShortEn
     *          the commodityDescriptionShortEn to set
     */
    public void setCommodityDescriptionShortEn(String commodityDescriptionShortEn) {
      this.commodityDescriptionShortEn = commodityDescriptionShortEn;
    }

    /**
     * @return the commodityDescriptionShortEn
     */
    public String getCommodityDescriptionShortEn() {
      return commodityDescriptionShortEn;
    }

    /**
     * @param commodityDescriptionShortJp
     *          the commodityDescriptionShortJp to set
     */
    public void setCommodityDescriptionShortJp(String commodityDescriptionShortJp) {
      this.commodityDescriptionShortJp = commodityDescriptionShortJp;
    }

    /**
     * @return the commodityDescriptionShortJp
     */
    public String getCommodityDescriptionShortJp() {
      return commodityDescriptionShortJp;
    }

    /**
     * @return the importCommodityType
     */
    public String getImportCommodityType() {
      return importCommodityType;
    }

    /**
     * @param importCommodityType
     *          the importCommodityType to set
     */
    public void setImportCommodityType(String importCommodityType) {
      this.importCommodityType = importCommodityType;
    }

    /**
     * @return the clearCommodityType
     */
    public String getClearCommodityType() {
      return clearCommodityType;
    }

    /**
     * @param clearCommodityType
     *          the clearCommodityType to set
     */
    public void setClearCommodityType(String clearCommodityType) {
      this.clearCommodityType = clearCommodityType;
    }

    /**
     * @return the hotFlgEn
     */
    public String getHotFlgEn() {
      return hotFlgEn;
    }

    /**
     * @param hotFlgEn
     *          the hotFlgEn to set
     */
    public void setHotFlgEn(String hotFlgEn) {
      this.hotFlgEn = hotFlgEn;
    }

    /**
     * @return the hotFlgJp
     */
    public String getHotFlgJp() {
      return hotFlgJp;
    }

    /**
     * @param hotFlgJp
     *          the hotFlgJp to set
     */
    public void setHotFlgJp(String hotFlgJp) {
      this.hotFlgJp = hotFlgJp;
    }

    /**
     * @return the title
     */
    public String getTitle() {
      return title;
    }

    /**
     * @return the titleEn
     */
    public String getTitleEn() {
      return titleEn;
    }

    /**
     * @return the titleJp
     */
    public String getTitleJp() {
      return titleJp;
    }

    /**
     * @return the description
     */
    public String getDescription() {
      return description;
    }

    /**
     * @return the descriptionEn
     */
    public String getDescriptionEn() {
      return descriptionEn;
    }

    /**
     * @return the descriptionJp
     */
    public String getDescriptionJp() {
      return descriptionJp;
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
      return keyword;
    }

    /**
     * @return the keywordEn
     */
    public String getKeywordEn() {
      return keywordEn;
    }

    /**
     * @return the keywordJp
     */
    public String getKeywordJp() {
      return keywordJp;
    }

    /**
     * @param title
     *          the title to set
     */
    public void setTitle(String title) {
      this.title = title;
    }

    /**
     * @param titleEn
     *          the titleEn to set
     */
    public void setTitleEn(String titleEn) {
      this.titleEn = titleEn;
    }

    /**
     * @param titleJp
     *          the titleJp to set
     */
    public void setTitleJp(String titleJp) {
      this.titleJp = titleJp;
    }

    /**
     * @param description
     *          the description to set
     */
    public void setDescription(String description) {
      this.description = description;
    }

    /**
     * @param descriptionEn
     *          the descriptionEn to set
     */
    public void setDescriptionEn(String descriptionEn) {
      this.descriptionEn = descriptionEn;
    }

    /**
     * @param descriptionJp
     *          the descriptionJp to set
     */
    public void setDescriptionJp(String descriptionJp) {
      this.descriptionJp = descriptionJp;
    }

    /**
     * @param keyword
     *          the keyword to set
     */
    public void setKeyword(String keyword) {
      this.keyword = keyword;
    }

    /**
     * @param keywordEn
     *          the keywordEn to set
     */
    public void setKeywordEn(String keywordEn) {
      this.keywordEn = keywordEn;
    }

    /**
     * @param keywordJp
     *          the keywordJp to set
     */
    public void setKeywordJp(String keywordJp) {
      this.keywordJp = keywordJp;
    }

  }

  /**
   * U1040120:商品登録のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CommodityEditSkuBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Length(24)
    @AlphaNum2
    @Metadata(name = "代表SKUコード")
    private String representSkuCode;

    @Length(13)
    // modity by V10-CH start
    // @Pattern(value = "([0-9]{8}|[0-9]{13}|)", message =
    // "8桁または13桁の数字を入力してください")
    @JanCode
    @Metadata(name = "JANコード")
    // modity by V10-CH end
    private String janCode;

    @Required
    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "商品単価")
    private String unitPrice = "0";

    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "特別価格")
    private String discountPrice;

    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "予約価格")
    private String reservationPrice;

    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "改定価格")
    private String changeUnitPrice;

    @Length(8)
    @Quantity
    // 10.1.6 10281 修正 ここから
    @Range(min = 1, max = 99999999)
    // 10.1.6 10281 修正 ここまで
    @Metadata(name = "予約上限数")
    private String reservationLimit;

    @Length(8)
    @Quantity
    // 10.1.6 10281 修正 ここから
    @Range(min = 1, max = 99999999)
    // 10.1.6 10281 修正 ここまで
    @Metadata(name = "注文毎予約上限数")
    private String oneshotReservationLimit;

    @Length(8)
    @Quantity
    @Range(min = 0, max = 99999999)
    @Metadata(name = "在庫閾値")
    private String stockThreshold;

    private Date updateDatetime;

    private String commodityNameEn;

    private String brandCode;

    private String brand;

    private String returnFlg;

    private String warningFlag;

    private String leadTime;

    private String saleFlag;

    private String specFlag;

    /**
     * @return the returnFlg
     */
    public String getReturnFlg() {
      return returnFlg;
    }

    /**
     * @param returnFlg
     *          the returnFlg to set
     */
    public void setReturnFlg(String returnFlg) {
      this.returnFlg = returnFlg;
    }

    /**
     * @return the warningFlag
     */
    public String getWarningFlag() {
      return warningFlag;
    }

    /**
     * @param warningFlag
     *          the warningFlag to set
     */
    public void setWarningFlag(String warningFlag) {
      this.warningFlag = warningFlag;
    }

    /**
     * @return the leadTime
     */
    public String getLeadTime() {
      return leadTime;
    }

    /**
     * @param leadTime
     *          the leadTime to set
     */
    public void setLeadTime(String leadTime) {
      this.leadTime = leadTime;
    }

    /**
     * @return the saleFlag
     */
    public String getSaleFlag() {
      return saleFlag;
    }

    /**
     * @param saleFlag
     *          the saleFlag to set
     */
    public void setSaleFlag(String saleFlag) {
      this.saleFlag = saleFlag;
    }

    /**
     * @return the specFlag
     */
    public String getSpecFlag() {
      return specFlag;
    }

    /**
     * @param specFlag
     *          the specFlag to set
     */
    public void setSpecFlag(String specFlag) {
      this.specFlag = specFlag;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
      return brand;
    }

    /**
     * @param brand
     *          the brand to set
     */
    public void setBrand(String brand) {
      this.brand = brand;
    }

    /**
     * @return the commodityNameEn
     */
    public String getCommodityNameEn() {
      return commodityNameEn;
    }

    /**
     * @param commodityNameEn
     *          the commodityNameEn to set
     */
    public void setCommodityNameEn(String commodityNameEn) {
      this.commodityNameEn = commodityNameEn;
    }

    /**
     * @return the brandCode
     */
    public String getBrandCode() {
      return brandCode;
    }

    /**
     * @param brandCode
     *          the brandCode to set
     */
    public void setBrandCode(String brandCode) {
      this.brandCode = brandCode;
    }

    /**
     * changeUnitPriceを取得します。
     * 
     * @return changeUnitPrice
     */
    public String getChangeUnitPrice() {
      return changeUnitPrice;
    }

    /**
     * discountPriceを取得します。
     * 
     * @return discountPrice
     */
    public String getDiscountPrice() {
      return discountPrice;
    }

    /**
     * representSkuCodeを取得します。
     * 
     * @return representSkuCode
     */
    public String getRepresentSkuCode() {
      return representSkuCode;
    }

    /**
     * reservationPriceを取得します。
     * 
     * @return reservationPrice
     */
    public String getReservationPrice() {
      return reservationPrice;
    }

    /**
     * unitPriceを取得します。
     * 
     * @return unitPrice
     */
    public String getUnitPrice() {
      return unitPrice;
    }

    /**
     * changeUnitPriceを設定します。
     * 
     * @param changeUnitPrice
     *          changeUnitPrice
     */
    public void setChangeUnitPrice(String changeUnitPrice) {
      this.changeUnitPrice = changeUnitPrice;
    }

    /**
     * discountPriceを設定します。
     * 
     * @param discountPrice
     *          discountPrice
     */
    public void setDiscountPrice(String discountPrice) {
      this.discountPrice = discountPrice;
    }

    /**
     * representSkuCodeを設定します。
     * 
     * @param representSkuCode
     *          representSkuCode
     */
    public void setRepresentSkuCode(String representSkuCode) {
      this.representSkuCode = representSkuCode;
    }

    /**
     * reservationPriceを設定します。
     * 
     * @param reservationPrice
     *          reservationPrice
     */
    public void setReservationPrice(String reservationPrice) {
      this.reservationPrice = reservationPrice;
    }

    /**
     * unitPriceを設定します。
     * 
     * @param unitPrice
     *          unitPrice
     */
    public void setUnitPrice(String unitPrice) {
      this.unitPrice = unitPrice;
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
     * janCodeを取得します。
     * 
     * @return the janCode
     */
    public String getJanCode() {
      return janCode;
    }

    /**
     * janCodeを設定します。
     * 
     * @param janCode
     *          janCode
     */
    public void setJanCode(String janCode) {
      this.janCode = janCode;
    }

    /**
     * reservationLimitを取得します。
     * 
     * @return the reservationLimit
     */
    public String getReservationLimit() {
      return reservationLimit;
    }

    /**
     * reservationLimitを設定します。
     * 
     * @param reservationLimit
     *          設定する reservationLimit
     */
    public void setReservationLimit(String reservationLimit) {
      this.reservationLimit = reservationLimit;
    }

    /**
     * oneshotReservationLimitを取得します。
     * 
     * @return the oneshotReservationLimit
     */
    public String getOneshotReservationLimit() {
      return oneshotReservationLimit;
    }

    /**
     * oneshotReservationLimitを設定します。
     * 
     * @param oneshotReservationLimit
     *          設定する oneshotReservationLimit
     */
    public void setOneshotReservationLimit(String oneshotReservationLimit) {
      this.oneshotReservationLimit = oneshotReservationLimit;
    }

    /**
     * stockThresholdを取得します。
     * 
     * @return stockThreshold
     */
    public String getStockThreshold() {
      return stockThreshold;
    }

    /**
     * stockThresholdを設定します。
     * 
     * @param stockThreshold
     *          stockThreshold
     */
    public void setStockThreshold(String stockThreshold) {
      this.stockThreshold = stockThreshold;
    }

  }

  /**
   * displayPreviewButtonを取得します。
   * 
   * @return displayPreviewButton
   */
  public boolean isDisplayPreviewButton() {
    return displayPreviewButton;
  }

  /**
   * displayPreviewButtonを設定します。
   * 
   * @param displayPreviewButton
   */
  public void setDisplayPreviewButton(boolean displayPreviewButton) {
    this.displayPreviewButton = displayPreviewButton;
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
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
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
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * headerを取得します。
   * 
   * @return header
   */
  public CommodityEditHeaderBean getHeader() {
    return header;
  }

  /**
   * skuを取得します。
   * 
   * @return sku
   */
  public CommodityEditSkuBean getSku() {
    return sku;
  }

  /**
   * headerを設定します。
   * 
   * @param header
   *          header
   */
  public void setHeader(CommodityEditHeaderBean header) {
    this.header = header;
  }

  /**
   * skuを設定します。
   * 
   * @param sku
   *          sku
   */
  public void setSku(CommodityEditSkuBean sku) {
    this.sku = sku;
  }

  /**
   * commodityCodeDisplayModeを取得します。
   * 
   * @return commodityCodeDisplayMode
   */
  public String getCommodityCodeDisplayMode() {
    return commodityCodeDisplayMode;
  }

  /**
   * commodityEditDisplayModeを取得します。
   * 
   * @return commodityEditDisplayMode
   */
  public String getCommodityEditDisplayMode() {
    return commodityEditDisplayMode;
  }

  /**
   * commodityCodeDisplayModeを設定します。
   * 
   * @param commodityCodeDisplayMode
   *          commodityCodeDisplayMode
   */
  public void setCommodityCodeDisplayMode(String commodityCodeDisplayMode) {
    this.commodityCodeDisplayMode = commodityCodeDisplayMode;
  }

  /**
   * commodityEditDisplayModeを設定します。
   * 
   * @param commodityEditDisplayMode
   *          commodityEditDisplayMode
   */
  public void setCommodityEditDisplayMode(String commodityEditDisplayMode) {
    this.commodityEditDisplayMode = commodityEditDisplayMode;
  }

  /**
   * displayNextButtonを取得します。
   * 
   * @return displayNextButton
   */
  public boolean isDisplayNextButton() {
    return displayNextButton;
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
   * displayUploadTableを取得します。
   * 
   * @return displayUploadTable
   */
  public boolean isDisplayUploadTable() {
    return displayUploadTable;
  }

  /**
   * displayNextButtonを設定します。
   * 
   * @param displayNextButton
   *          displayNextButton
   */
  public void setDisplayNextButton(boolean displayNextButton) {
    this.displayNextButton = displayNextButton;
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
   * displayUploadTableを設定します。
   * 
   * @param displayUploadTable
   *          displayUploadTable
   */
  public void setDisplayUploadTable(boolean displayUploadTable) {
    this.displayUploadTable = displayUploadTable;
  }

  /**
   * displayMoveSkuButtonを取得します。
   * 
   * @return displayMoveSkuButton
   */
  public boolean isDisplayMoveSkuButton() {
    return displayMoveSkuButton;
  }

  /**
   * displayMoveSkuButtonを設定します。
   * 
   * @param displayMoveSkuButton
   *          displayMoveSkuButton
   */
  public void setDisplayMoveSkuButton(boolean displayMoveSkuButton) {
    this.displayMoveSkuButton = displayMoveSkuButton;
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    this.setShopCode(reqparam.get("shopCode"));
    this.setCommodityCode(reqparam.get("commodityCode"));
    this.setDisplayPreview(false);
    reqparam.copy(header);
    header.setCommodityPointStartDatetime(reqparam.getDateTimeString("commodityPointStartDatetime"));
    header.setCommodityPointEndDatetime(reqparam.getDateTimeString("commodityPointEndDatetime"));
    header.setSaleStartDatetime(reqparam.getDateTimeString("saleStartDatetime"));
    header.setSaleEndDatetime(reqparam.getDateTimeString("saleEndDatetime"));
    header.setDiscountPriceStartDatetime(reqparam.getDateTimeString("discountPriceStartDatetime"));
    header.setDiscountPriceEndDatetime(reqparam.getDateTimeString("discountPriceEndDatetime"));
    header.setReservationStartDatetime(reqparam.getDateTimeString("reservationStartDatetime"));
    header.setReservationEndDatetime(reqparam.getDateTimeString("reservationEndDatetime"));
    header.setSalePriceChangeDatetime(reqparam.getDateString("salePriceChangeDatetime"));
    // add by twh 2012-12-4 17:16:27 start
    // 套装商品登录时必要字段的默认值
    if (!displaySuitCommodity) {
      header.setCommodityType("0");
      header.setSetCommodityFlg("1");
      header.setReturnFlag("0");
      if (commodityCodeDisplayMode.equals(WebConstantCode.DISPLAY_EDIT)) {
        sku.setUnitPrice("0");
      }
      sku.setRepresentSkuCode(reqparam.get("commodityCode"));
    } else {
      sku.setUnitPrice(reqparam.get("unitPrice"));
      sku.setRepresentSkuCode(reqparam.get("representSkuCode"));
    }
    // add by twh 2012-12-4 17:16:27 start

    sku.setDiscountPrice(reqparam.get("discountPrice"));
    sku.setReservationPrice(reqparam.get("reservationPrice"));
    sku.setChangeUnitPrice(reqparam.get("changeUnitPrice"));
    sku.setJanCode(reqparam.get("janCode"));
    sku.setReservationLimit(reqparam.get("reservationLimit"));
    sku.setOneshotReservationLimit(reqparam.get("oneshotReservationLimit"));
    sku.setStockThreshold(reqparam.get("stockThreshold"));
    // 20120109 ysy add start
    sku.setCommodityNameEn(reqparam.get("commodityNameEn"));
    sku.setBrandCode(reqparam.get("brandCode"));
    sku.setBrand(reqparam.get("brand"));

    // 20120109 ysy add end

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040120";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.CommodityEditBean.0");
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {

  }

  /**
   * arrivalGoodsFlgを取得します。
   * 
   * @return arrivalGoodsFlg
   */
  public ArrivalGoodsFlg[] getArrivalGoodsFlg() {
    return ArrayUtil.immutableCopy(arrivalGoodsFlg);
  }

  public CommodityType[] getCommodityTypeList() {
    return ArrayUtil.immutableCopy(commodityTypeList);
  }

  public void setCommodityTypeList(CommodityType[] commodityTypeList) {
    this.commodityTypeList = ArrayUtil.immutableCopy(commodityTypeList);
  }

  public SetCommodityFlg[] getSetCommodityFlgList() {
    return ArrayUtil.immutableCopy(setCommodityFlgList);
  }

  public void setSetCommodityFlgList(SetCommodityFlg[] setCommodityFlgList) {
    this.setCommodityFlgList = ArrayUtil.immutableCopy(setCommodityFlgList);
  }

  /**
   * displayClientTypeを取得します。
   * 
   * @return displayClientType
   */
  public DisplayClientType[] getDisplayClientType() {
    return ArrayUtil.immutableCopy(this.displayClientType);
  }

  /**
   * @return the returnFlag
   */
  public ReturnFlag[] getReturnFlag() {
    return returnFlag;
  }

  /**
   * @param returnFlag
   *          the returnFlag to set
   */
  public void setReturnFlag(ReturnFlag[] returnFlag) {
    this.returnFlag = returnFlag;
  }

  /**
   * @return the warningFlag
   */
  public WarningFlag[] getWarningFlag() {
    return warningFlag;
  }

  /**
   * @param warningFlag
   *          the warningFlag to set
   */
  public void setWarningFlag(WarningFlag[] warningFlag) {
    this.warningFlag = warningFlag;
  }

  /**
   * saleFlgを取得します。
   * 
   * @return saleFlg
   */
  public SaleFlg[] getSaleFlg() {
    return ArrayUtil.immutableCopy(this.saleFlg);
  }

  public ImportCommodityType[] getImportCommodityType() {
    return ArrayUtil.immutableCopy(this.importCommodityType);
  }

  public ClearCommodityType[] getClearCommodityType() {
    return ArrayUtil.immutableCopy(this.clearCommodityType);
  }

  /**
   * @return the reserveCommodityType1
   */
  public ReserveCommodityType1[] getReserveCommodityType1() {
    return reserveCommodityType1;
  }

  /**
   * @param reserveCommodityType1
   *          the reserveCommodityType1 to set
   */
  public void setReserveCommodityType1(ReserveCommodityType1[] reserveCommodityType1) {
    this.reserveCommodityType1 = reserveCommodityType1;
  }

  /**
   * stockManagementTypeを取得します。
   * 
   * @return stockManagementType
   */
  public StockManagementType[] getStockManagementType() {
    return ArrayUtil.immutableCopy(this.stockManagementType);
  }

  /**
   * taxTypeを取得します。
   * 
   * @return taxType
   */
  public TaxType[] getTaxType() {
    return ArrayUtil.immutableCopy(this.taxType);
  }

  /**
   * arrivalGoodsFlgを設定します。
   * 
   * @param arrivalGoodsFlg
   *          arrivalGoodsFlg
   */
  public void setArrivalGoodsFlg(ArrivalGoodsFlg[] arrivalGoodsFlg) {
    this.arrivalGoodsFlg = ArrayUtil.immutableCopy(arrivalGoodsFlg);
  }

  /**
   * displayClientTypeを設定します。
   * 
   * @param displayClientType
   *          displayClientType
   */
  public void setDisplayClientType(DisplayClientType[] displayClientType) {
    this.displayClientType = ArrayUtil.immutableCopy(displayClientType);
  }

  /**
   * saleFlgを設定します。
   * 
   * @param saleFlg
   *          saleFlg
   */
  public void setSaleFlg(SaleFlg[] saleFlg) {
    this.saleFlg = ArrayUtil.immutableCopy(saleFlg);
  }

  public void setImportCommodityType(ImportCommodityType[] importCommodityType) {
    this.importCommodityType = ArrayUtil.immutableCopy(importCommodityType);
  }

  public void setClearCommodityType(ClearCommodityType[] clearCommodityType) {
    this.clearCommodityType = ArrayUtil.immutableCopy(clearCommodityType);
  }

  /**
   * stockManagementTypeを設定します。
   * 
   * @param stockManagementType
   *          stockManagementType
   */
  public void setStockManagementType(StockManagementType[] stockManagementType) {
    this.stockManagementType = ArrayUtil.immutableCopy(stockManagementType);
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
   * displayCancelButtonを取得します。
   * 
   * @return displayCancelButton
   */
  public boolean isDisplayCancelButton() {
    return displayCancelButton;
  }

  /**
   * displayCancelButtonを設定します。
   * 
   * @param displayCancelButton
   *          displayCancelButton
   */
  public void setDisplayCancelButton(boolean displayCancelButton) {
    this.displayCancelButton = displayCancelButton;
  }

  /**
   * displayImageTableを取得します。
   * 
   * @return displayImageTable
   */
  public boolean isDisplayImageTable() {
    return displayImageTable;
  }

  /**
   * displayImageTableを設定します。
   * 
   * @param displayImageTable
   *          displayImageTable
   */
  public void setDisplayImageTable(boolean displayImageTable) {
    this.displayImageTable = displayImageTable;
  }

  /**
   * displayPreviewを取得します。
   * 
   * @return displayPreview
   */
  public boolean isDisplayPreview() {
    return displayPreview;
  }

  /**
   * displayPreviewを設定します。
   * 
   * @param displayPreview
   *          displayPreview
   */
  public void setDisplayPreview(boolean displayPreview) {
    this.displayPreview = displayPreview;
  }

  /**
   * readOnlyModeを取得します。
   * 
   * @return readOnlyMode
   */
  public boolean isReadOnlyMode() {
    return readOnlyMode;
  }

  /**
   * readOnlyModeを設定します。
   * 
   * @param readOnlyMode
   *          readOnlyMode
   */
  public void setReadOnlyMode(boolean readOnlyMode) {
    this.readOnlyMode = readOnlyMode;
  }

  /**
   * previewUrlを取得します。
   * 
   * @return previewUrl
   */

  public String getPreviewUrl() {
    return previewUrl;
  }

  /**
   * previewUrlを設定します。
   * 
   * @param previewUrl
   *          previewUrl
   */
  public void setPreviewUrl(String previewUrl) {
    this.previewUrl = previewUrl;
  }

  /**
   * previewDigestを取得します。
   * 
   * @return previewDigest
   */
  public String getPreviewDigest() {
    return previewDigest;
  }

  /**
   * previewDigestを設定します。
   * 
   * @param previewDigest
   *          previewDigest
   */
  public void setPreviewDigest(String previewDigest) {
    this.previewDigest = previewDigest;
  }

  /**
   * displayImageDeleteButtonを取得します。
   * 
   * @return displayImageDeleteButton
   */
  public boolean isDisplayImageDeleteButton() {
    return displayImageDeleteButton;
  }

  /**
   * displayMobileThumbnailDeleteButtonを取得します。
   * 
   * @return displayMobileThumbnailDeleteButton
   */
  public boolean isDisplayMobileThumbnailDeleteButton() {
    return displayMobileThumbnailDeleteButton;
  }

  /**
   * displayThumbnailDeleteButtonを取得します。
   * 
   * @return displayThumbnailDeleteButton
   */
  public boolean isDisplayThumbnailDeleteButton() {
    return displayThumbnailDeleteButton;
  }

  /**
   * displayImageDeleteButtonを設定します。
   * 
   * @param displayImageDeleteButton
   *          displayImageDeleteButton
   */
  public void setDisplayImageDeleteButton(boolean displayImageDeleteButton) {
    this.displayImageDeleteButton = displayImageDeleteButton;
  }

  /**
   * displayMobileThumbnailDeleteButtonを設定します。
   * 
   * @param displayMobileThumbnailDeleteButton
   *          displayMobileThumbnailDeleteButton
   */
  public void setDisplayMobileThumbnailDeleteButton(boolean displayMobileThumbnailDeleteButton) {
    this.displayMobileThumbnailDeleteButton = displayMobileThumbnailDeleteButton;
  }

  /**
   * displayThumbnailDeleteButtonを設定します。
   * 
   * @param displayThumbnailDeleteButton
   *          displayThumbnailDeleteButton
   */
  public void setDisplayThumbnailDeleteButton(boolean displayThumbnailDeleteButton) {
    this.displayThumbnailDeleteButton = displayThumbnailDeleteButton;
  }

  /**
   * displayMobileRepresentSkuImageDeleteButtonを取得します。
   * 
   * @return displayMobileRepresentSkuImageDeleteButton
   */
  public boolean isDisplayMobileRepresentSkuImageDeleteButton() {
    return displayMobileRepresentSkuImageDeleteButton;
  }

  /**
   * displayMobileRepresentSkuImageDeleteButtonを設定します。
   * 
   * @param displayMobileRepresentSkuImageDeleteButton
   *          displayMobileRepresentSkuImageDeleteButton
   */
  public void setDisplayMobileRepresentSkuImageDeleteButton(boolean displayMobileRepresentSkuImageDeleteButton) {
    this.displayMobileRepresentSkuImageDeleteButton = displayMobileRepresentSkuImageDeleteButton;
  }

  /**
   * displayRepresentSkuImageDeleteButtonを取得します。
   * 
   * @return displayRepresentSkuImageDeleteButton
   */
  public boolean isDisplayRepresentSkuImageDeleteButton() {
    return displayRepresentSkuImageDeleteButton;
  }

  /**
   * displayRepresentSkuImageDeleteButtonを設定します。
   * 
   * @param displayRepresentSkuImageDeleteButton
   *          displayRepresentSkuImageDeleteButton
   */
  public void setDisplayRepresentSkuImageDeleteButton(boolean displayRepresentSkuImageDeleteButton) {
    this.displayRepresentSkuImageDeleteButton = displayRepresentSkuImageDeleteButton;
  }

  /**
   * imageListを取得します。
   * 
   * @return imageList
   */
  public List<CodeAttribute> getImageList() {
    return imageList;
  }

  /**
   * imageListを設定します。
   * 
   * @param imageList
   *          imageList
   */
  public void setImageList(List<CodeAttribute> imageList) {
    this.imageList = imageList;
  }

  /**
   * @return the displaySuitCommodity
   */
  public boolean isDisplaySuitCommodity() {
    return displaySuitCommodity;
  }

  /**
   * @param displaySuitCommodity
   *          the displaySuitCommodity to set
   */
  public void setDisplaySuitCommodity(boolean displaySuitCommodity) {
    this.displaySuitCommodity = displaySuitCommodity;
  }

  /**
   * @return the displaySaleflgList
   */
  public List<NameValue> getDisplaySaleflgList() {
    return displaySaleflgList;
  }

  /**
   * @param displaySaleflgList
   *          the displaySaleflgList to set
   */
  public void setDisplaySaleflgList(List<NameValue> displaySaleflgList) {
    this.displaySaleflgList = displaySaleflgList;
  }

}
