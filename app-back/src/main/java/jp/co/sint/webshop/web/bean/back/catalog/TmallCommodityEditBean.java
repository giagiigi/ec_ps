package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.CategoryCode;
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
import jp.co.sint.webshop.data.domain.BigGoodsFlag;
import jp.co.sint.webshop.data.domain.ClearCommodityType;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.DisplayClientTypeNew;
import jp.co.sint.webshop.data.domain.ImportCommodityType;
import jp.co.sint.webshop.data.domain.NewReserveCommodityType1;
import jp.co.sint.webshop.data.domain.NewReserveCommodityType2;
import jp.co.sint.webshop.data.domain.NewReserveCommodityType3;
import jp.co.sint.webshop.data.domain.NewReserveCommodityType4;
import jp.co.sint.webshop.data.domain.NewReserveCommodityType5;
import jp.co.sint.webshop.data.domain.ReserveCommodityType1;
import jp.co.sint.webshop.data.domain.ReserveCommodityType2;
import jp.co.sint.webshop.data.domain.ReserveCommodityType3;
import jp.co.sint.webshop.data.domain.ReturnFlg;
import jp.co.sint.webshop.data.domain.SaleFlg;
import jp.co.sint.webshop.data.domain.ShelfLifeFlag;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.domain.TmallMjsType;
import jp.co.sint.webshop.data.domain.TmallWarningFlag;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.DataContainer;
import jp.co.sint.webshop.data.dto.TmallCategory;
import jp.co.sint.webshop.data.dto.TmallCommodityProperty;
import jp.co.sint.webshop.service.CategoryViewUtil.PropertyKeys;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.back.catalog.TmallCommodityEditBaseAction;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040120:商品登録のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
/**
 * @author kousen
 */
public class TmallCommodityEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<TmallCategory> listCategorys = new ArrayList<TmallCategory>();

  private TmallCommodityEditHeaderBean header = new TmallCommodityEditHeaderBean();

  private Brand commodityBrand = new Brand();

  private List<CodeAttribute> orignalPlaceList = new ArrayList<CodeAttribute>();

  private boolean pagePd = true;

  /**
   * @return the pagePd
   */
  public boolean isPagePd() {
    return pagePd;
  }

  /**
   * @param pagePd
   *          the pagePd to set
   */
  public void setPagePd(boolean pagePd) {
    this.pagePd = pagePd;
  }

  /**
   * @return the commodityBrand
   */
  public Brand getCommodityBrand() {
    return commodityBrand;
  }

  /**
   * @return the orignalPlaceList
   */
  public List<CodeAttribute> getOrignalPlaceList() {
    return orignalPlaceList;
  }

  /**
   * @param orignalPlaceList
   *          the orignalPlaceList to set
   */
  public void setOrignalPlaceList(List<CodeAttribute> orignalPlaceList) {
    this.orignalPlaceList = orignalPlaceList;
  }

  /**
   * @param commodityBrand
   *          the commodityBrand to set
   */
  public void setCommodityBrand(Brand commodityBrand) {
    this.commodityBrand = commodityBrand;
  }

  private String oldCommodityCode;

  /**
   * @return the oldCommodityCode
   */
  public String getOldCommodityCode() {
    return oldCommodityCode;
  }

  /**
   * @param oldCommodityCode
   *          the oldCommodityCode to set
   */
  public void setOldCommodityCode(String oldCommodityCode) {
    this.oldCommodityCode = oldCommodityCode;
  }

  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "EC特別価格")
  private String commodityDiscountPrice;

  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "tmall特別価格")
  private String commodityTmallDiscountPrice;

  private PropertyKeys propertyKeys;

  /** 企划类型集合 */
  private List<NameValue> planDetailTypeList = new ArrayList<NameValue>();

  /** 特集类型集合 */
  private List<NameValue> featuredPlanDetailTypes = new ArrayList<NameValue>();

  private TmallCommodityEditSkuBean sku = new TmallCommodityEditSkuBean();

  private List<TmallCommodityEditSkuBean> list = new ArrayList<TmallCommodityEditSkuBean>();

  private List<TmallCategory> allCategoryContainer = new ArrayList<TmallCategory>();

  private List<TmallPropertyValueBean> propertyBeanList = new ArrayList<TmallPropertyValueBean>();

  private List<TmallCommodityProperty> properties;

  private String commodityBrandName;

  private String commodityPropertyJson;

  /**
   * @return the commodityPropertyJson
   */
  public String getCommodityPropertyJson() {
    return commodityPropertyJson;
  }

  /**
   * @param commodityPropertyJson
   *          the commodityPropertyJson to set
   */
  public void setCommodityPropertyJson(String commodityPropertyJson) {
    this.commodityPropertyJson = commodityPropertyJson;
  }

  @Length(16)
  @CategoryCode
  private String categoryCode;

  private String showMode = "edit";

  /**
   * @return the showMode
   */
  public String getShowMode() {
    return showMode;
  }

  /**
   * @param showMode
   *          the showMode to set
   */
  public void setShowMode(String showMode) {
    this.showMode = showMode;
  }

  /**
   * @return the commodityDiscountPrice
   */
  public String getCommodityDiscountPrice() {
    return commodityDiscountPrice;
  }

  /**
   * @param commodityDiscountPrice
   *          the commodityDiscountPrice to set
   */
  public void setCommodityDiscountPrice(String commodityDiscountPrice) {
    this.commodityDiscountPrice = commodityDiscountPrice;
  }

  /**
   * @return the commodityTmallDiscountPrice
   */
  public String getCommodityTmallDiscountPrice() {
    return commodityTmallDiscountPrice;
  }

  /**
   * @param commodityTmallDiscountPrice
   *          the commodityTmallDiscountPrice to set
   */
  public void setCommodityTmallDiscountPrice(String commodityTmallDiscountPrice) {
    this.commodityTmallDiscountPrice = commodityTmallDiscountPrice;
  }

  private String categoryName;

  /**
   * @return the commodityBrandName
   */
  public String getCommodityBrandName() {
    return commodityBrandName;
  }

  /**
   * @return the featuredPlanDetailTypes
   */
  public List<NameValue> getFeaturedPlanDetailTypes() {
    return featuredPlanDetailTypes;
  }

  /**
   * @param featuredPlanDetailTypes
   *          the featuredPlanDetailTypes to set
   */
  public void setFeaturedPlanDetailTypes(List<NameValue> featuredPlanDetailTypes) {
    this.featuredPlanDetailTypes = featuredPlanDetailTypes;
  }

  /**
   * @param commodityBrandName
   *          the commodityBrandName to set
   */
  public void setCommodityBrandName(String commodityBrandName) {
    this.commodityBrandName = commodityBrandName;
  }

  /**
   * @return the planDetailTypeList
   */
  public List<NameValue> getPlanDetailTypeList() {
    return planDetailTypeList;
  }

  /**
   * @param planDetailTypeList
   *          the planDetailTypeList to set
   */
  public void setPlanDetailTypeList(List<NameValue> planDetailTypeList) {
    this.planDetailTypeList = planDetailTypeList;
  }

  /**
   * @return the propertyBeanList
   */
  public List<TmallPropertyValueBean> getPropertyBeanList() {
    return propertyBeanList;
  }

  /**
   * @param propertyBeanList
   *          the propertyBeanList to set
   */
  public void setPropertyBeanList(List<TmallPropertyValueBean> propertyBeanList) {
    this.propertyBeanList = propertyBeanList;
  }

  /**
   * @return the propertyKeys
   */
  public PropertyKeys getPropertyKeys() {
    return propertyKeys;
  }

  /**
   * @param propertyKeys
   *          the propertyKeys to set
   */
  public void setPropertyKeys(PropertyKeys propertyKeys) {
    this.propertyKeys = propertyKeys;
  }

  private List<CodeAttribute> warningFlags = new ArrayList<CodeAttribute>();

  /**
   * @return the warningFlags
   */
  public List<CodeAttribute> getWarningFlags() {
    return warningFlags;
  }

  public void setWarningFlags(List<CodeAttribute> list) {
    this.warningFlags = list;
  }

  private String propertyHtml;

  /**
   * @return the propertyHtml
   */
  public String getPropertyHtml() {
    return propertyHtml;
  }

  /**
   * @param propertyHtml
   *          the propertyHtml to set
   */
  public void setPropertyHtml(String propertyHtml) {
    this.propertyHtml = propertyHtml;
  }

  /**
   * @return the properties
   */
  public List<TmallCommodityProperty> getProperties() {
    return properties;
  }

  /**
   * @param properties
   *          the properties to set
   */
  public void setProperties(List<TmallCommodityProperty> properties) {
    this.properties = properties;
  }

  /**
   * @return the allCategoryContainer
   */
  public List<TmallCategory> getAllCategoryContainer() {
    return allCategoryContainer;
  }

  /**
   * @param allCategoryContainer
   *          the allCategoryContainer to set
   */
  public void setAllCategoryContainer(List<TmallCategory> allCategoryContainer) {
    this.allCategoryContainer = allCategoryContainer;
  }

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

  private boolean displayfanhuiButton = false;

  /**
   * @return the displayfanhuiButton
   */
  public boolean isDisplayfanhuiButton() {
    return displayfanhuiButton;
  }

  /**
   * @param displayfanhuiButton
   *          the displayfanhuiButton to set
   */
  public void setDisplayfanhuiButton(boolean displayfanhuiButton) {
    this.displayfanhuiButton = displayfanhuiButton;
  }

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

  private TmallMjsType[] tmallMjsType = TmallMjsType.values();

  private BigGoodsFlag[] bigFlag = BigGoodsFlag.values();

  private ReturnFlg[] ReturnFlag = ReturnFlg.values();

  private ShelfLifeFlag[] shelfLifeFlags = ShelfLifeFlag.values();

  private TmallWarningFlag[] tmallWarningFlag = TmallWarningFlag.values();

  /**
   * @param newReserveCommodityType1
   *          the newReserveCommodityType1 to set
   */
  public void setNewReserveCommodityType1(NewReserveCommodityType1[] newReserveCommodityType1) {
    this.newReserveCommodityType1 = newReserveCommodityType1;
  }

  /**
   * @param newReserveCommodityType2
   *          the newReserveCommodityType2 to set
   */
  public void setNewReserveCommodityType2(NewReserveCommodityType2[] newReserveCommodityType2) {
    this.newReserveCommodityType2 = newReserveCommodityType2;
  }

  /**
   * @param newReserveCommodityType3
   *          the newReserveCommodityType3 to set
   */
  public void setNewReserveCommodityType3(NewReserveCommodityType3[] newReserveCommodityType3) {
    this.newReserveCommodityType3 = newReserveCommodityType3;
  }

  /**
   * @param newReserveCommodityType4
   *          the newReserveCommodityType4 to set
   */
  public void setNewReserveCommodityType4(NewReserveCommodityType4[] newReserveCommodityType4) {
    this.newReserveCommodityType4 = newReserveCommodityType4;
  }

  /**
   * @param newReserveCommodityType5
   *          the newReserveCommodityType5 to set
   */
  public void setNewReserveCommodityType5(NewReserveCommodityType5[] newReserveCommodityType5) {
    this.newReserveCommodityType5 = newReserveCommodityType5;
  }

  /**
   * @return the tmallWarningFlag
   */
  public TmallWarningFlag[] getTmallWarningFlag() {
    return tmallWarningFlag;
  }

  /**
   * @param tmallWarningFlag
   *          the tmallWarningFlag to set
   */
  public void setTmallWarningFlag(TmallWarningFlag[] tmallWarningFlag) {
    this.tmallWarningFlag = tmallWarningFlag;
  }

  /**
   * @return the shelfLifeFlags
   */
  public ShelfLifeFlag[] getShelfLifeFlags() {
    return shelfLifeFlags;
  }

  /**
   * @param shelfLifeFlags
   *          the shelfLifeFlags to set
   */
  public void setShelfLifeFlags(ShelfLifeFlag[] shelfLifeFlags) {
    this.shelfLifeFlags = shelfLifeFlags;
  }

  /**
   * @return the bigFlag
   */
  public BigGoodsFlag[] getBigFlag() {
    return bigFlag;
  }

  /**
   * @return the returnFlag
   */
  public ReturnFlg[] getReturnFlag() {
    return ReturnFlag;
  }

  /**
   * @param returnFlag
   *          the returnFlag to set
   */
  public void setReturnFlag(ReturnFlg[] returnFlag) {
    ReturnFlag = returnFlag;
  }

  /**
   * @param bigFlag
   *          the bigFlag to set
   */
  public void setBigFlag(BigGoodsFlag[] bigFlag) {
    this.bigFlag = bigFlag;
  }

  private DisplayClientTypeNew[] displayClientTypes = DisplayClientTypeNew.values();

  /**
   * @return the displayClientTypes
   */
  public DisplayClientTypeNew[] getDisplayClientTypes() {
    return displayClientTypes;
  }

  /**
   * @param displayClientTypes
   *          the displayClientTypes to set
   */
  public void setDisplayClientTypes(DisplayClientTypeNew[] displayClientTypes) {
    this.displayClientTypes = displayClientTypes;
  }

  private TaxType[] taxType = TaxType.values();

  private ArrivalGoodsFlg[] arrivalGoodsFlg = ArrivalGoodsFlg.values();

  private DisplayClientType[] displayClientType = DisplayClientType.values();

  private StockManagementType[] stockManagementType = StockManagementType.values();

  private boolean displayPreview = false;

  private String previewUrl;

  private String previewDigest;

  private List<CodeAttribute> imageList = new ArrayList<CodeAttribute>();
  
  //商品有效期只读
  private boolean showShelfLifeReadOnly;

  /**
   * U1040120:商品登録のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class TmallCommodityEditHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

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

    @Length(24)
    @Metadata(name = "代表SKUコード")
    private String representSkuCode;

    /** TMALL检索关键字 */
    @Length(500)
    @Metadata(name = "TMALL检索关键字", order = 24)
    private String tmallCommoditySearchWords;

    /**
     * @return the tmallCommoditySearchWords
     */
    public String getTmallCommoditySearchWords() {
      return tmallCommoditySearchWords;
    }

    /**
     * @param tmallCommoditySearchWords
     *          the tmallCommoditySearchWords to set
     */
    public void setTmallCommoditySearchWords(String tmallCommoditySearchWords) {
      this.tmallCommoditySearchWords = tmallCommoditySearchWords;
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

    @Length(6)
    @Metadata(name = "保质期天数")
    private String shelfLifeDays;

    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Metadata(name = "tmall同期时间")
    private String syncTmallTime;

    @Length(1)
    @Metadata(name = "同期标志")
    private String syncFlagEc;

    @Required
    @Length(1)
    @Metadata(name = "保质期管理")
    private String shelfLifeFlag;

    // @Length(8)
    // @Metadata(name = "组合内单品数")
    // private String quantityPerUnit;

    @Length(20)
    @Metadata(name = "成分1")
    private String ingredientName1;

    @Length(10)
    @Metadata(name = "成分量1")
    private String ingredientVal1;

    @Length(20)
    @Metadata(name = "成分2")
    private String ingredientName2;

    @Length(10)
    @Metadata(name = "成分量2")
    private String ingredientVal2;

    @Length(20)
    @Metadata(name = "成分3")
    private String ingredientName3;

    @Length(10)
    @Metadata(name = "成分量3")
    private String ingredientVal3;

    @Length(20)
    @Metadata(name = "成分4")
    private String ingredientName4;

    @Length(10)
    @Metadata(name = "成分量4")
    private String ingredientVal4;

    @Length(20)
    @Metadata(name = "成分5")
    private String ingredientName5;

    @Length(10)
    @Metadata(name = "成分量5")
    private String ingredientVal5;

    @Length(20)
    @Metadata(name = "成分6")
    private String ingredientName6;

    @Length(10)
    @Metadata(name = "成分量6")
    private String ingredientVal6;

    @Length(20)
    @Metadata(name = "成分7")
    private String ingredientName7;

    @Length(10)
    @Metadata(name = "成分量7")
    private String ingredientVal7;

    @Length(20)
    @Metadata(name = "成分8")
    private String ingredientName8;

    @Length(10)
    @Metadata(name = "成分量8")
    private String ingredientVal8;

    @Length(20)
    @Metadata(name = "成分9")
    private String ingredientName9;

    @Length(10)
    @Metadata(name = "成分量9")
    private String ingredientVal9;

    @Length(20)
    @Metadata(name = "成分10")
    private String ingredientName10;

    @Length(10)
    @Metadata(name = "成分量10")
    private String ingredientVal10;

    @Length(20)
    @Metadata(name = "成分11")
    private String ingredientName11;

    @Length(10)
    @Metadata(name = "成分量11")
    private String ingredientVal11;

    @Length(20)
    @Metadata(name = "成分12")
    private String ingredientName12;

    @Length(10)
    @Metadata(name = "成分量12")
    private String ingredientVal12;

    @Length(20)
    @Metadata(name = "成分14")
    private String ingredientName14;

    @Length(10)
    @Metadata(name = "成分量14")
    private String ingredientVal14;

    @Length(20)
    @Metadata(name = "成分15")
    private String ingredientName15;

    @Length(10)
    @Metadata(name = "成分量15")
    private String ingredientVal15;

    @Length(20)
    @Metadata(name = "成分13")
    private String ingredientName13;

    @Length(10)
    @Metadata(name = "成分量13")
    private String ingredientVal13;

    @Length(20)
    @Metadata(name = "原材料1")
    private String material1;

    @Length(20)
    @Metadata(name = "原材料2")
    private String material2;

    @Length(20)
    @Metadata(name = "原材料3")
    private String material3;

    @Length(20)
    @Metadata(name = "原材料4")
    private String material4;

    @Length(20)
    @Metadata(name = "原材料5")
    private String material5;

    @Length(20)
    @Metadata(name = "原材料6")
    private String material6;

    @Length(20)
    @Metadata(name = "原材料7")
    private String material7;

    @Length(20)
    @Metadata(name = "原材料8")
    private String material8;

    @Length(20)
    @Metadata(name = "原材料9")
    private String material9;

    @Length(20)
    @Metadata(name = "原材料10")
    private String material10;

    @Length(20)
    @Metadata(name = "原材料11")
    private String material11;

    @Length(20)
    @Metadata(name = "原材料12")
    private String material12;

    @Length(20)
    @Metadata(name = "原材料13")
    private String material13;

    @Length(20)
    @Metadata(name = "原材料14")
    private String material14;

    @Length(20)
    @Metadata(name = "原材料15")
    private String material15;

    @Length(1)
    @Metadata(name = "进口商品区分")
    private String importCommodityTypeEc;

    @Length(1)
    @Metadata(name = "清仓商品区分")
    private String clearCommodityTypeEc;

    @Length(1)
    @Metadata(name = "Asahi商品区分")
    private String reserveCommodityType1;

    @Length(1)
    @Metadata(name = "hot商品区分")
    private String reserveCommodityType2;

    @Length(1)
    @Metadata(name = "商品展示区分")
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

    @Length(16)
    @Metadata(name = "产地Code")
    private String originalCode;

    /** 原商品编号 */
    @Length(16)
    @Metadata(name = "原商品编号")
    private String originalCommodityCode;

    /** 组合数量 */
    @Length(8)
    @Digit
    @Metadata(name = "组合数量")
    private String combinationAmount;

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
    
    private boolean suitFlg = false;

    /**
     * @return the originalCommodityCode
     */
    public String getOriginalCommodityCode() {
      return originalCommodityCode;
    }

    /**
     * @param originalCommodityCode
     *          the originalCommodityCode to set
     */
    public void setOriginalCommodityCode(String originalCommodityCode) {
      this.originalCommodityCode = originalCommodityCode;
    }

    /**
     * @return the combinationAmount
     */
    public String getCombinationAmount() {
      return combinationAmount;
    }

    /**
     * @param combinationAmount
     *          the combinationAmount to set
     */
    public void setCombinationAmount(String combinationAmount) {
      this.combinationAmount = combinationAmount;
    }

    /**
     * @return the originalCode
     */
    public String getOriginalCode() {
      return originalCode;
    }

    /**
     * @param originalCode
     *          the originalCode to set
     */
    public void setOriginalCode(String originalCode) {
      this.originalCode = originalCode;
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

    @Required
    @Length(1)
    @Metadata(name = "销售标志")
    private String saleFlgEc;

    @Length(1)
    @Metadata(name = "tmall满就送")
    private String tmallMjsFlg;

    @Required
    @Length(1)
    @Metadata(name = "供应商是否可换货标志")
    private String shopChangeFlag;

    @Required
    @Length(1)
    @Metadata(name = "供应商是否可退货标志")
    private String shopReturnFlag;

    @Required
    @Length(1)
    @Metadata(name = "顾客是否可退货标志")
    private String custReturnFlag;

    // 20120322 os013 add start
    @Length(50)
    @Metadata(name = "代表SKU单价")
    private BigDecimal representSkuUnitPrice;

    // 20120322 os013 add end
    // add by cs_yuli 20120605 start
    @Length(4)
    @Metadata(name = "入库生命天数")
    private Long inBoundLifeDays;

    @Length(4)
    @Metadata(name = "出库生命天数")
    private Long outBoundLifeDays;

    @Length(4)
    @Metadata(name = "失效期预警")
    private Long shelfLifeAlertDays;

    @Length(1)
    @Metadata(name = "ERP标志")
    private Long exportFlagErp;

    @Length(1)
    @Metadata(name = "WMS标志")
    private Long exportFlagWms;

    // add by cs_yuli 20120605 end

    /*
     * 食品安全新增商品字段
     */
    // 生产许可证号
    @Length(16)
    @Metadata(name = "生产许可证号", order = 112)
    private String foodSecurityPrdLicenseNo;

    // 产品标准号
    @Length(25)
    @Metadata(name = "产品标准号", order = 113)
    private String foodSecurityDesignCode;

    // 厂名
    @Length(50)
    @Metadata(name = "厂名", order = 114)
    private String foodSecurityFactory;

    // 厂址
    @Length(50)
    @Metadata(name = "厂址", order = 115)
    private String foodSecurityFactorySite;

    // 厂家联系方式
    @Length(25)
    @Metadata(name = "厂家联系方式", order = 115)
    private String foodSecurityContact;

    // 配料表
    @Length(100)
    @Metadata(name = "配料表", order = 116)
    private String foodSecurityMix;

    // 储藏方法
    @Length(25)
    @Metadata(name = "储藏方法", order = 117)
    private String foodSecurityPlanStorage;

    // 保质期
    @Length(15)
    @Metadata(name = "保质期", order = 118)
    private String foodSecurityPeriod;

    // 食品添加剂
    @Length(50)
    @Metadata(name = "食品添加剂", order = 119)
    private String foodSecurityFoodAdditive;

    // 供货商
    @Length(25)
    @Metadata(name = "供货商", order = 120)
    private String foodSecuritySupplier;

    // 生产开始日期,格式必须为yyyy-MM-dd
    @Datetime
    @Metadata(name = "生产开始日期", order = 121)
    private String foodSecurityProductDateStart;

    // 生产结束日期,格式必须为yyyy-MM-dd
    @Datetime
    @Metadata(name = "生产结束日期", order = 122)
    private String foodSecurityProductDateEnd;

    // 进货开始日期，要在生产日期之后，格式必须为yyyy-MM-dd
    @Datetime
    @Metadata(name = "进货开始日期", order = 123)
    private String foodSecurityStockDateStart;

    // 进货结束日期，要在生产日期之后，格式必须为yyyy-MM-dd
    @Datetime
    @Metadata(name = "进货结束日期", order = 124)
    private String foodSecurityStockDateEnd;

    @Length(50)
    @Metadata(name = "产地英文", order = 125)
    private String originalPlaceEn;

    @Length(50)
    @Metadata(name = "产地日文", order = 126)
    private String originalPlaceJp;

    @Length(1)
    @Metadata(name = "商品区分")
    private Long commodityType;

    public Long getCommodityType() {
      return commodityType;
    }

    public void setCommodityType(Long commodityType) {
      this.commodityType = commodityType;
    }

    public String getOriginalPlaceEn() {
      return originalPlaceEn;
    }

    public void setOriginalPlaceEn(String originalPlaceEn) {
      this.originalPlaceEn = originalPlaceEn;
    }

    public String getOriginalPlaceJp() {
      return originalPlaceJp;
    }

    public void setOriginalPlaceJp(String originalPlaceJp) {
      this.originalPlaceJp = originalPlaceJp;
    }

    /**
     * @return the shopChangeFlag
     */
    public String getShopChangeFlag() {
      return shopChangeFlag;
    }

    /**
     * @param shopChangeFlag
     *          the shopChangeFlag to set
     */
    public void setShopChangeFlag(String shopChangeFlag) {
      this.shopChangeFlag = shopChangeFlag;
    }

    /**
     * @return the shopReturnFlag
     */
    public String getShopReturnFlag() {
      return shopReturnFlag;
    }

    /**
     * @param shopReturnFlag
     *          the shopReturnFlag to set
     */
    public void setShopReturnFlag(String shopReturnFlag) {
      this.shopReturnFlag = shopReturnFlag;
    }

    /**
     * @return the custReturnFlag
     */
    public String getCustReturnFlag() {
      return custReturnFlag;
    }

    /**
     * @param custReturnFlag
     *          the custReturnFlag to set
     */
    public void setCustReturnFlag(String custReturnFlag) {
      this.custReturnFlag = custReturnFlag;
    }

    /**
     * @return the saleFlgEc
     */
    public String getSaleFlgEc() {
      return saleFlgEc;
    }

    /**
     * @param saleFlgEc
     *          the saleFlgEc to set
     */
    public void setSaleFlgEc(String saleFlgEc) {
      this.saleFlgEc = saleFlgEc;
    }

    public String getTmallMjsFlg() {
      return tmallMjsFlg;
    }

    public void setTmallMjsFlg(String tmallMjsFlg) {
      this.tmallMjsFlg = tmallMjsFlg;
    }

    /**
     * @return the material1
     */
    public String getMaterial1() {
      return material1;
    }

    /**
     * @param material1
     *          the material1 to set
     */
    public void setMaterial1(String material1) {
      this.material1 = material1;
    }

    /**
     * @return the material2
     */
    public String getMaterial2() {
      return material2;
    }

    /**
     * @param material2
     *          the material2 to set
     */
    public void setMaterial2(String material2) {
      this.material2 = material2;
    }

    /**
     * @return the material3
     */
    public String getMaterial3() {
      return material3;
    }

    /**
     * @param material3
     *          the material3 to set
     */
    public void setMaterial3(String material3) {
      this.material3 = material3;
    }

    /**
     * @return the material4
     */
    public String getMaterial4() {
      return material4;
    }

    /**
     * @param material4
     *          the material4 to set
     */
    public void setMaterial4(String material4) {
      this.material4 = material4;
    }

    /**
     * @return the syncTmallTime
     */
    public String getSyncTmallTime() {
      return syncTmallTime;
    }

    /**
     * @param syncTmallTime
     *          the syncTmallTime to set
     */
    public void setSyncTmallTime(String syncTmallTime) {
      this.syncTmallTime = syncTmallTime;
    }

    /**
     * @return the syncFlagEc
     */
    public String getSyncFlagEc() {
      return syncFlagEc;
    }

    /**
     * @param syncFlagEc
     *          the syncFlagEc to set
     */
    public void setSyncFlagEc(String syncFlagEc) {
      this.syncFlagEc = syncFlagEc;
    }

    /**
     * @return the material5
     */
    public String getMaterial5() {
      return material5;
    }

    /**
     * @param material5
     *          the material5 to set
     */
    public void setMaterial5(String material5) {
      this.material5 = material5;
    }

    /**
     * @return the material6
     */
    public String getMaterial6() {
      return material6;
    }

    /**
     * @param material6
     *          the material6 to set
     */
    public void setMaterial6(String material6) {
      this.material6 = material6;
    }

    /**
     * @return the material7
     */
    public String getMaterial7() {
      return material7;
    }

    /**
     * @param material7
     *          the material7 to set
     */
    public void setMaterial7(String material7) {
      this.material7 = material7;
    }

    /**
     * @return the material8
     */
    public String getMaterial8() {
      return material8;
    }

    /**
     * @param material8
     *          the material8 to set
     */
    public void setMaterial8(String material8) {
      this.material8 = material8;
    }

    /**
     * @return the material9
     */
    public String getMaterial9() {
      return material9;
    }

    /**
     * @param material9
     *          the material9 to set
     */
    public void setMaterial9(String material9) {
      this.material9 = material9;
    }

    /**
     * @return the material10
     */
    public String getMaterial10() {
      return material10;
    }

    /**
     * @param material10
     *          the material10 to set
     */
    public void setMaterial10(String material10) {
      this.material10 = material10;
    }

    /**
     * @return the material11
     */
    public String getMaterial11() {
      return material11;
    }

    /**
     * @param material11
     *          the material11 to set
     */
    public void setMaterial11(String material11) {
      this.material11 = material11;
    }

    /**
     * @return the material12
     */
    public String getMaterial12() {
      return material12;
    }

    /**
     * @param material12
     *          the material12 to set
     */
    public void setMaterial12(String material12) {
      this.material12 = material12;
    }

    /**
     * @return the material13
     */
    public String getMaterial13() {
      return material13;
    }

    /**
     * @param material13
     *          the material13 to set
     */
    public void setMaterial13(String material13) {
      this.material13 = material13;
    }

    /**
     * @return the material14
     */
    public String getMaterial14() {
      return material14;
    }

    /**
     * @param material14
     *          the material14 to set
     */
    public void setMaterial14(String material14) {
      this.material14 = material14;
    }

    /**
     * @return the material15
     */
    public String getMaterial15() {
      return material15;
    }

    /**
     * @param material15
     *          the material15 to set
     */
    public void setMaterial15(String material15) {
      this.material15 = material15;
    }

    /**
     * @return the ingredientName1
     */
    public String getIngredientName1() {
      return ingredientName1;
    }

    /**
     * @param ingredientName1
     *          the ingredientName1 to set
     */
    public void setIngredientName1(String ingredientName1) {
      this.ingredientName1 = ingredientName1;
    }

    /**
     * @return the ingredientVal1
     */
    public String getIngredientVal1() {
      return ingredientVal1;
    }

    /**
     * @param ingredientVal1
     *          the ingredientVal1 to set
     */
    public void setIngredientVal1(String ingredientVal1) {
      this.ingredientVal1 = ingredientVal1;
    }

    /**
     * @return the ingredientName2
     */
    public String getIngredientName2() {
      return ingredientName2;
    }

    /**
     * @param ingredientName2
     *          the ingredientName2 to set
     */
    public void setIngredientName2(String ingredientName2) {
      this.ingredientName2 = ingredientName2;
    }

    /**
     * @return the ingredientVal2
     */
    public String getIngredientVal2() {
      return ingredientVal2;
    }

    /**
     * @param ingredientVal2
     *          the ingredientVal2 to set
     */
    public void setIngredientVal2(String ingredientVal2) {
      this.ingredientVal2 = ingredientVal2;
    }

    /**
     * @return the ingredientName3
     */
    public String getIngredientName3() {
      return ingredientName3;
    }

    /**
     * @param ingredientName3
     *          the ingredientName3 to set
     */
    public void setIngredientName3(String ingredientName3) {
      this.ingredientName3 = ingredientName3;
    }

    /**
     * @return the ingredientVal3
     */
    public String getIngredientVal3() {
      return ingredientVal3;
    }

    /**
     * @param ingredientVal3
     *          the ingredientVal3 to set
     */
    public void setIngredientVal3(String ingredientVal3) {
      this.ingredientVal3 = ingredientVal3;
    }

    /**
     * @return the ingredientName4
     */
    public String getIngredientName4() {
      return ingredientName4;
    }

    /**
     * @param ingredientName4
     *          the ingredientName4 to set
     */
    public void setIngredientName4(String ingredientName4) {
      this.ingredientName4 = ingredientName4;
    }

    /**
     * @return the ingredientVal4
     */
    public String getIngredientVal4() {
      return ingredientVal4;
    }

    /**
     * @param ingredientVal4
     *          the ingredientVal4 to set
     */
    public void setIngredientVal4(String ingredientVal4) {
      this.ingredientVal4 = ingredientVal4;
    }

    /**
     * @return the ingredientName5
     */
    public String getIngredientName5() {
      return ingredientName5;
    }

    /**
     * @param ingredientName5
     *          the ingredientName5 to set
     */
    public void setIngredientName5(String ingredientName5) {
      this.ingredientName5 = ingredientName5;
    }

    /**
     * @return the ingredientVal5
     */
    public String getIngredientVal5() {
      return ingredientVal5;
    }

    /**
     * @param ingredientVal5
     *          the ingredientVal5 to set
     */
    public void setIngredientVal5(String ingredientVal5) {
      this.ingredientVal5 = ingredientVal5;
    }

    /**
     * @return the ingredientName6
     */
    public String getIngredientName6() {
      return ingredientName6;
    }

    /**
     * @param ingredientName6
     *          the ingredientName6 to set
     */
    public void setIngredientName6(String ingredientName6) {
      this.ingredientName6 = ingredientName6;
    }

    /**
     * @return the ingredientVal6
     */
    public String getIngredientVal6() {
      return ingredientVal6;
    }

    /**
     * @param ingredientVal6
     *          the ingredientVal6 to set
     */
    public void setIngredientVal6(String ingredientVal6) {
      this.ingredientVal6 = ingredientVal6;
    }

    /**
     * @return the ingredientName7
     */
    public String getIngredientName7() {
      return ingredientName7;
    }

    /**
     * @param ingredientName7
     *          the ingredientName7 to set
     */
    public void setIngredientName7(String ingredientName7) {
      this.ingredientName7 = ingredientName7;
    }

    /**
     * @return the ingredientVal7
     */
    public String getIngredientVal7() {
      return ingredientVal7;
    }

    /**
     * @param ingredientVal7
     *          the ingredientVal7 to set
     */
    public void setIngredientVal7(String ingredientVal7) {
      this.ingredientVal7 = ingredientVal7;
    }

    /**
     * @return the ingredientName8
     */
    public String getIngredientName8() {
      return ingredientName8;
    }

    /**
     * @param ingredientName8
     *          the ingredientName8 to set
     */
    public void setIngredientName8(String ingredientName8) {
      this.ingredientName8 = ingredientName8;
    }

    /**
     * @return the ingredientVal8
     */
    public String getIngredientVal8() {
      return ingredientVal8;
    }

    /**
     * @param ingredientVal8
     *          the ingredientVal8 to set
     */
    public void setIngredientVal8(String ingredientVal8) {
      this.ingredientVal8 = ingredientVal8;
    }

    /**
     * @return the ingredientName9
     */
    public String getIngredientName9() {
      return ingredientName9;
    }

    /**
     * @param ingredientName9
     *          the ingredientName9 to set
     */
    public void setIngredientName9(String ingredientName9) {
      this.ingredientName9 = ingredientName9;
    }

    /**
     * @return the ingredientVal9
     */
    public String getIngredientVal9() {
      return ingredientVal9;
    }

    /**
     * @param ingredientVal9
     *          the ingredientVal9 to set
     */
    public void setIngredientVal9(String ingredientVal9) {
      this.ingredientVal9 = ingredientVal9;
    }

    /**
     * @return the ingredientName10
     */
    public String getIngredientName10() {
      return ingredientName10;
    }

    /**
     * @param ingredientName10
     *          the ingredientName10 to set
     */
    public void setIngredientName10(String ingredientName10) {
      this.ingredientName10 = ingredientName10;
    }

    /**
     * @return the ingredientVal10
     */
    public String getIngredientVal10() {
      return ingredientVal10;
    }

    /**
     * @param ingredientVal10
     *          the ingredientVal10 to set
     */
    public void setIngredientVal10(String ingredientVal10) {
      this.ingredientVal10 = ingredientVal10;
    }

    /**
     * @return the ingredientName11
     */
    public String getIngredientName11() {
      return ingredientName11;
    }

    /**
     * @param ingredientName11
     *          the ingredientName11 to set
     */
    public void setIngredientName11(String ingredientName11) {
      this.ingredientName11 = ingredientName11;
    }

    /**
     * @return the ingredientVal11
     */
    public String getIngredientVal11() {
      return ingredientVal11;
    }

    /**
     * @param ingredientVal11
     *          the ingredientVal11 to set
     */
    public void setIngredientVal11(String ingredientVal11) {
      this.ingredientVal11 = ingredientVal11;
    }

    /**
     * @return the ingredientName12
     */
    public String getIngredientName12() {
      return ingredientName12;
    }

    /**
     * @param ingredientName12
     *          the ingredientName12 to set
     */
    public void setIngredientName12(String ingredientName12) {
      this.ingredientName12 = ingredientName12;
    }

    /**
     * @return the ingredientVal12
     */
    public String getIngredientVal12() {
      return ingredientVal12;
    }

    /**
     * @param ingredientVal12
     *          the ingredientVal12 to set
     */
    public void setIngredientVal12(String ingredientVal12) {
      this.ingredientVal12 = ingredientVal12;
    }

    /**
     * @return the ingredientName14
     */
    public String getIngredientName14() {
      return ingredientName14;
    }

    /**
     * @param ingredientName14
     *          the ingredientName14 to set
     */
    public void setIngredientName14(String ingredientName14) {
      this.ingredientName14 = ingredientName14;
    }

    /**
     * @return the ingredientVal14
     */
    public String getIngredientVal14() {
      return ingredientVal14;
    }

    /**
     * @param ingredientVal14
     *          the ingredientVal14 to set
     */
    public void setIngredientVal14(String ingredientVal14) {
      this.ingredientVal14 = ingredientVal14;
    }

    /**
     * @return the ingredientName15
     */
    public String getIngredientName15() {
      return ingredientName15;
    }

    /**
     * @param ingredientName15
     *          the ingredientName15 to set
     */
    public void setIngredientName15(String ingredientName15) {
      this.ingredientName15 = ingredientName15;
    }

    /**
     * @return the ingredientVal15
     */
    public String getIngredientVal15() {
      return ingredientVal15;
    }

    /**
     * @param ingredientVal15
     *          the ingredientVal15 to set
     */
    public void setIngredientVal15(String ingredientVal15) {
      this.ingredientVal15 = ingredientVal15;
    }

    /**
     * @return the ingredientName13
     */
    public String getIngredientName13() {
      return ingredientName13;
    }

    /**
     * @param ingredientName13
     *          the ingredientName13 to set
     */
    public void setIngredientName13(String ingredientName13) {
      this.ingredientName13 = ingredientName13;
    }

    /**
     * @return the ingredientVal13
     */
    public String getIngredientVal13() {
      return ingredientVal13;
    }

    /**
     * @param ingredientVal13
     *          the ingredientVal13 to set
     */
    public void setIngredientVal13(String ingredientVal13) {
      this.ingredientVal13 = ingredientVal13;
    }

    /**
     * @return the representSkuCode
     */
    public String getRepresentSkuCode() {
      return representSkuCode;
    }

    /**
     * @return the shelfLifeDays
     */
    public String getShelfLifeDays() {
      return shelfLifeDays;
    }

    /**
     * @param shelfLifeDays
     *          the shelfLifeDays to set
     */
    public void setShelfLifeDays(String shelfLifeDays) {
      this.shelfLifeDays = shelfLifeDays;
    }

    /**
     * @return the shelfLifeFlag
     */
    public String getShelfLifeFlag() {
      return shelfLifeFlag;
    }

    /**
     * @param shelfLifeFlag
     *          the shelfLifeFlag to set
     */
    public void setShelfLifeFlag(String shelfLifeFlag) {
      this.shelfLifeFlag = shelfLifeFlag;
    }

    // /**
    // * @return the quantityPerUnit
    // */
    // public String getQuantityPerUnit() {
    // return quantityPerUnit;
    // }
    //
    //
    //
    //    
    // /**
    // * @param quantityPerUnit the quantityPerUnit to set
    // */
    // public void setQuantityPerUnit(String quantityPerUnit) {
    // this.quantityPerUnit = quantityPerUnit;
    // }

    /**
     * @param representSkuCode
     *          the representSkuCode to set
     */
    public void setRepresentSkuCode(String representSkuCode) {
      this.representSkuCode = representSkuCode;
    }

    @Required
    @Length(16)
    @Metadata(name = "商品编号")
    private String commodityCode;

    @Length(20)
    @Metadata(name = "产地")
    private String originalPlace;

    @Length(16)
    @Metadata(name = "淘宝商城类目编号")
    private Long tcategoryId;

    @Required
    @Length(16)
    @Metadata(name = "品牌")
    private String brandCode;

    @Required
    @Length(1)
    @Metadata(name = "大型货物标志")
    private String bigFlag;

    /**
     * @return the bigFlag
     */
    public String getBigFlag() {
      return bigFlag;
    }

    /**
     * @param bigFlag
     *          the bigFlag to set
     */
    public void setBigFlag(String bigFlag) {
      this.bigFlag = bigFlag;
    }

    @Length(5)
    @Metadata(name = "销售区分")
    private String saleFlag;

    @Length(5)
    @Metadata(name = "特集区分")
    private String specFlag;

    @Required
    @Length(2)
    @Range(min = 0, max = 99)
    @Metadata(name = "最短发货周期")
    private Long leadTime;

    @Length(1)
    @Metadata(name = "警告区分")
    private String warningFlag;

    @Required
    @Length(50)
    @Metadata(name = "商品名")
    private String commodityName;

    @Length(1)
    @Metadata(name = "是否可退货")
    private String returnFlg;

    // @Length(8)
    // @AlphaNum2
    // @Metadata(name = "配送種別")
    // private String deliveryTypeNo;
    //
    // private List<CodeAttribute> deliveyTypeName = new
    // ArrayList<CodeAttribute>();
    //
    // @Length(4)
    // @Digit
    // @Range(min = 0, max = 9999)
    // @Metadata(name = "おすすめ商品順位")
    // private String recommendCommodityRank;
    //
    //    
    // @Length(1)
    // @Metadata(name = "入荷お知らせ申し込み")
    // private String arrivalGoodsFlg;

    // @Length(1)
    // @Metadata(name = "消費税区分")
    // private String taxType;

    // @Length(3)
    // @Digit
    // @Range(min = 0, max = 100)
    // @Metadata(name = "商品別ポイント付与率")
    // private String commodityPointRate;
    //
    // @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    // @Metadata(name = "商品別ポイント付与期間(From)")
    // private String commodityPointStartDatetime;
    //
    // @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    // @Metadata(name = "商品別ポイント付与期間(To)")
    // private String commodityPointEndDatetime;

    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Metadata(name = "销售期间(From)")
    private String saleStartDatetime;

    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Metadata(name = "销售期间(To)")
    private String saleEndDatetime;

    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Metadata(name = "特別价格期間(From)")
    private String discountPriceStartDatetime;

    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Metadata(name = "特別价格期間(To)")
    private String discountPriceEndDatetime;

    // @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    // @Metadata(name = "予約期間(From)")
    // private String reservationStartDatetime;
    //
    // @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    // @Metadata(name = "予約期間(To)")
    // private String reservationEndDatetime;
    //
    // @Datetime(format = DateUtil.DEFAULT_DATE_FORMAT)
    // @Metadata(name = "価格改定日")
    // private String salePriceChangeDatetime;

    @Length(20)
    @Metadata(name = "規格1名称")
    private String standard1Name;

    @Length(20)
    @Metadata(name = "規格2名称")
    private String standard2Name;

    @Length(20)
    @Metadata(name = "規格1名称ID(TMALLの属性ID）", order = 14)
    private String standard1Id;

    @Length(20)
    @Metadata(name = "規格2名称ID(TMALLの属性ID）", order = 16)
    private String standard2Id;

    @Length(1)
    @Metadata(name = "在庫管理区分")
    private String stockManagementType;

    @Length(5)
    @Metadata(name = "特集区分")
    private String spec_flag;

    @Length(8)
    @Digit
    @Metadata(name = "在庫状況番号")
    private String stockStatusNo;

    private List<CodeAttribute> stockStatusName = new ArrayList<CodeAttribute>();

    //2014/4/28 京东WBS对应 ob_李 add start
    @Length(45)
    @Metadata(name = "广告词")
    private String advertContent;
    //2014/4/28 京东WBS对应 ob_李 add end
    
    @Length(1000)
    @Metadata(name = "商品説明1")
    private String commodityDescription1;

    @Length(1000)
    @Metadata(name = "商品説明1 英字")
    private String commodityDescription1En;

    @Length(1000)
    @Metadata(name = "商品説明1 日字")
    private String commodityDescription1Jp;

    @Required
    @Length(2000)
    @Metadata(name = "商品説明2")
    private String commodityDescription2;

    // @Required
    @Length(2000)
    @Metadata(name = "商品説明2 英字")
    private String commodityDescription2En;

    // @Required
    @Length(2000)
    @Metadata(name = "商品説明2 日字")
    private String commodityDescription2Jp;

    @Length(1000)
    @Metadata(name = "商品説明3")
    private String commodityDescription3;

    @Length(1000)
    @Metadata(name = "商品説明3 英字")
    private String commodityDescription3En;

    @Length(1000)
    @Metadata(name = "商品説明3 日字")
    private String commodityDescription3Jp;

    @Length(500)
    @Metadata(name = "商品说明一览用")
    private String commodityDescriptionShort;

    @Length(500)
    @Metadata(name = "商品说明一览用 英字")
    private String commodityDescriptionShortEn;

    @Length(500)
    @Metadata(name = "商品说明一览用 日字")
    private String commodityDescriptionShortJp;

    @Length(500)
    @Metadata(name = "检索文字")
    private String commoditySearchWords;

    @Length(256)
    @Url
    @Metadata(name = "リンクURL")
    private String linkUrl;

    private Date updateDatetime;

    @Required
    @Length(200)
    @Metadata(name = "商品英文名")
    private String commodityNameEn;

    @Required
    @Length(200)
    @Metadata(name = "商品日文名")
    private String commodityNameJp;

    @Required
    @Length(8)
    @Metadata(name = "采购负责人代码", order = 26)
    private String buyerCode;

    @Required
    @Length(8)
    @Metadata(name = "供应商编号", order = 31)
    private String supplierCode;

    private TmallCategory category = new TmallCategory();

    /**
     * @return the standard1Id
     */
    public String getStandard1Id() {
      return standard1Id;
    }

    /**
     * @param standard1Id
     *          the standard1Id to set
     */
    public void setStandard1Id(String standard1Id) {
      this.standard1Id = standard1Id;
    }

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
     * @return the standard2Id
     */
    public String getStandard2Id() {
      return standard2Id;
    }

    /**
     * @param standard2Id
     *          the standard2Id to set
     */
    public void setStandard2Id(String standard2Id) {
      this.standard2Id = standard2Id;
    }

    /**
     * @return the category
     */
    public TmallCategory getCategory() {
      return category;
    }

    /**
     * @param category
     *          the category to set
     */
    public void setCategory(TmallCategory category) {
      this.category = category;
    }

    /**
     * @return the buyerCode
     */
    public String getBuyerCode() {
      return buyerCode;
    }

    /**
     * @return the tcategoryId
     */
    public Long getTcategoryId() {
      return tcategoryId;
    }

    /**
     * @param tcategoryId
     *          the tcategoryId to set
     */
    public void setTcategoryId(Long tcategoryId) {
      this.tcategoryId = tcategoryId;
    }

    /**
     * @param buyerCode
     *          the buyerCode to set
     */
    public void setBuyerCode(String buyerCode) {
      this.buyerCode = buyerCode;
    }

    /**
     * @return the supplierCode
     */
    public String getSupplierCode() {
      return supplierCode;
    }

    /**
     * @param supplierCode
     *          the supplierCode to set
     */
    public void setSupplierCode(String supplierCode) {
      this.supplierCode = supplierCode;
    }

    /**
     * @return the commodityDescription1
     */
    public String getCommodityDescription1() {
      return commodityDescription1;
    }

    /**
     * @param commodityDescription1
     *          the commodityDescription1 to set
     */
    public void setCommodityDescription1(String commodityDescription1) {
      this.commodityDescription1 = commodityDescription1;
    }

    /**
     * @return the commodityDescription2
     */
    public String getCommodityDescription2() {
      return commodityDescription2;
    }

    /**
     * @param commodityDescription2
     *          the commodityDescription2 to set
     */
    public void setCommodityDescription2(String commodityDescription2) {
      this.commodityDescription2 = commodityDescription2;
    }

    /**
     * @return the commodityDescription3
     */
    public String getCommodityDescription3() {
      return commodityDescription3;
    }

    /**
     * @param commodityDescription3
     *          the commodityDescription3 to set
     */
    public void setCommodityDescription3(String commodityDescription3) {
      this.commodityDescription3 = commodityDescription3;
    }

    /**
     * @return the commodityDescriptionShort
     */
    public String getCommodityDescriptionShort() {
      return commodityDescriptionShort;
    }

    /**
     * @param commodityDescriptionShort
     *          the commodityDescriptionShort to set
     */
    public void setCommodityDescriptionShort(String commodityDescriptionShort) {
      this.commodityDescriptionShort = commodityDescriptionShort;
    }

    /**
     * commodityNameを取得します。
     * 
     * @return commodityName
     */
    public String getCommodityName() {
      return commodityName;
    }

    // /**
    // * commodityPointEndDatetimeを取得します。
    //
    // *
    // * @return commodityPointEndDatetime
    // */
    // public String getCommodityPointEndDatetime() {
    // return commodityPointEndDatetime;
    // }
    //
    // /**
    // * commodityPointRateを取得します。
    //
    // *
    // * @return commodityPointRate
    // */
    // public String getCommodityPointRate() {
    // return commodityPointRate;
    // }
    //
    // /**
    // * commodityPointStartDatetimeを取得します。
    //
    // *
    // * @return commodityPointStartDatetime
    // */
    // public String getCommodityPointStartDatetime() {
    // return commodityPointStartDatetime;
    // }

    /**
     * commoditySearchWordsを取得します。
     * 
     * @return commoditySearchWords
     */
    public String getCommoditySearchWords() {
      return commoditySearchWords;
    }

    /**
     * deliveyTypeNoを取得します。 // * // * @return deliveryTypeNo //
     */
    // public String getDeliveryTypeNo() {
    // return deliveryTypeNo;
    // }
    /**
     * @return the originalPlace
     */
    public String getOriginalPlace() {
      return originalPlace;
    }

    /**
     * @param originalPlace
     *          the originalPlace to set
     */
    public void setOriginalPlace(String originalPlace) {
      this.originalPlace = originalPlace;
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
     * linkUrlを取得します。
     * 
     * @return linkUrl
     */
    public String getLinkUrl() {
      return linkUrl;
    }

    /**
     * recommendCommodityRankを取得します。 // * // * @return recommendCommodityRank //
     */
    // public String getRecommendCommodityRank() {
    // return recommendCommodityRank;
    // }
    //
    // /**
    // * reservationEndDatetimeを取得します。
    //
    // *
    // * @return reservationEndDatetime
    // */
    // public String getReservationEndDatetime() {
    // return reservationEndDatetime;
    // }
    /**
     * reservationStartDatetimeを取得します。
     * 
     * @return reservationStartDatetime
     */
    // public String getReservationStartDatetime() {
    // return reservationStartDatetime;
    // }
    /**
     * saleEndDatetimeを取得します。
     * 
     * @return saleEndDatetime
     */
    public String getSaleEndDatetime() {
      return saleEndDatetime;
    }

    /**
     * salePriceChangeDatetimeを取得します。
     * 
     * @return salePriceChangeDatetime
     */
    // public String getSalePriceChangeDatetime() {
    // return salePriceChangeDatetime;
    // }
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
     * commodityNameを設定します。
     * 
     * @param commodityName
     *          commodityName
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    /**
     * commodityPointEndDatetimeを設定します。 // * // * @param
     * commodityPointEndDatetime // * commodityPointEndDatetime //
     */
    // public void setCommodityPointEndDatetime(String
    // commodityPointEndDatetime) {
    // this.commodityPointEndDatetime = commodityPointEndDatetime;
    // }
    //
    // /**
    // * commodityPointRateを設定します。
    //
    // *
    // * @param commodityPointRate
    // * commodityPointRate
    // */
    // public void setCommodityPointRate(String commodityPointRate) {
    // this.commodityPointRate = commodityPointRate;
    // }
    // /**
    // * commodityPointStartDatetimeを設定します。
    //
    // *
    // * @param commodityPointStartDatetime
    // * commodityPointStartDatetime
    // */
    // public void setCommodityPointStartDatetime(String
    // commodityPointStartDatetime) {
    // this.commodityPointStartDatetime = commodityPointStartDatetime;
    // }
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
     * deliveyTypeNoを設定します。 // * // * @param deliveyTypeNo // * deliveyTypeNo //
     */
    // public void setDeliveryTypeNo(String deliveyTypeNo) {
    // this.deliveryTypeNo = deliveyTypeNo;
    // }
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
     * linkUrlを設定します。
     * 
     * @param linkUrl
     *          linkUrl
     */
    public void setLinkUrl(String linkUrl) {
      this.linkUrl = linkUrl;
    }

    /**
     * recommendCommodityRankを設定します。 // * // * @param recommendCommodityRank //
     * * recommendCommodityRank //
     */
    // public void setRecommendCommodityRank(String recommendCommodityRank)
    // {
    // this.recommendCommodityRank = recommendCommodityRank;
    // }
    //
    // /**
    // * reservationEndDatetimeを設定します。
    //
    // *
    // * @param reservationEndDatetime
    // * reservationEndDatetime
    // */
    // public void setReservationEndDatetime(String reservationEndDatetime)
    // {
    // this.reservationEndDatetime = reservationEndDatetime;
    // }
    //
    // /**
    // * reservationStartDatetimeを設定します。
    //
    // *
    // * @param reservationStartDatetime
    // * reservationStartDatetime
    // */
    // public void setReservationStartDatetime(String
    // reservationStartDatetime)
    // {
    // this.reservationStartDatetime = reservationStartDatetime;
    // }
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
     * salePriceChangeDatetimeを設定します。 // * // * @param salePriceChangeDatetime
     * // * salePriceChangeDatetime //
     */
    // public void setSalePriceChangeDatetime(String
    // salePriceChangeDatetime) {
    // this.salePriceChangeDatetime = salePriceChangeDatetime;
    // }
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
     * deliveyTypeNameを取得します。 // * // * @return deliveyTypeName //
     */
    // public List<CodeAttribute> getDeliveyTypeName() {
    // return deliveyTypeName;
    // }
    /**
     * stockStatusNameを取得します。
     * 
     * @return stockStatusName
     */
    public List<CodeAttribute> getStockStatusName() {
      return stockStatusName;
    }

    /**
     * deliveyTypeNameを設定します。 // * // * @param deliveyTypeName // *
     * deliveyTypeName //
     */
    // public void setDeliveyTypeName(List<CodeAttribute> deliveyTypeName) {
    // this.deliveyTypeName = deliveyTypeName;
    // }
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
     * taxTypeを取得します。 // * // * @return taxType //
     */
    // public String getTaxType() {
    // return taxType;
    // }
    //
    // /**
    // * taxTypeを設定します。
    //
    // *
    // * @param taxType
    // * taxType
    // */
    // public void setTaxType(String taxType) {
    // this.taxType = taxType;
    // }
    // /**
    // * arrivalGoodsFlgを取得します。
    //
    // *
    // * @return arrivalGoodsFlg
    // */
    // public String getArrivalGoodsFlg() {
    // return arrivalGoodsFlg;
    // }
    //
    // /**
    // * arrivalGoodsFlgを設定します。
    //
    // *
    // * @param arrivalGoodsFlg
    // * arrivalGoodsFlg
    // */
    // public void setArrivalGoodsFlg(String arrivalGoodsFlg) {
    // this.arrivalGoodsFlg = arrivalGoodsFlg;
    // }
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
     * @param commodityNameEn
     *          the commodityNameEn to set
     */
    public void setCommodityNameEn(String commodityNameEn) {
      this.commodityNameEn = commodityNameEn;
    }

    /**
     * @return the commodityNameEn
     */
    public String getCommodityNameEn() {
      return commodityNameEn;
    }

    /**
     * @param saleFlag
     *          the saleFlag to set
     */
    public void setSaleFlag(String saleFlag) {
      this.saleFlag = saleFlag;
    }

    /**
     * @return the saleFlag
     */
    public String getSaleFlag() {
      return saleFlag;
    }

    /**
     * @param spec_flag
     *          the spec_flag to set
     */
    public void setSpec_flag(String spec_flag) {
      this.spec_flag = spec_flag;
    }

    /**
     * @return the spec_flag
     */
    public String getSpec_flag() {
      return spec_flag;
    }

    /**
     * @param warningFlag
     *          the warningFlag to set
     */
    public void setWarningFlag(String warningFlag) {
      this.warningFlag = warningFlag;
    }

    /**
     * @return the warningFlag
     */
    public String getWarningFlag() {
      return warningFlag;
    }

    /**
     * @param specFlag
     *          the specFlag to set
     */
    public void setSpecFlag(String specFlag) {
      this.specFlag = specFlag;
    }

    /**
     * @return the specFlag
     */
    public String getSpecFlag() {
      return specFlag;
    }

    /**
     * @param leadTime
     *          the leadTime to set
     */
    public void setLeadTime(Long leadTime) {
      this.leadTime = leadTime;
    }

    /**
     * @return the leadTime
     */
    public Long getLeadTime() {
      return leadTime;
    }

    /**
     * @param brandCode
     *          the brandCode to set
     */
    public void setBrandCode(String brandCode) {
      this.brandCode = brandCode;
    }

    /**
     * @return the brandCode
     */
    public String getBrandCode() {
      return brandCode;
    }

    /**
     * @param standard1Name
     *          the standard1Name to set
     */
    public void setStandard1Name(String standard1Name) {
      this.standard1Name = standard1Name;
    }

    /**
     * @return the standard1Name
     */
    public String getStandard1Name() {
      return standard1Name;
    }

    /**
     * @param standard2Name
     *          the standard2Name to set
     */
    public void setStandard2Name(String standard2Name) {
      this.standard2Name = standard2Name;
    }

    /**
     * @return the standard2Name
     */
    public String getStandard2Name() {
      return standard2Name;
    }

    /**
     * @return the representSkuUnitPrice
     */
    public BigDecimal getRepresentSkuUnitPrice() {
      return representSkuUnitPrice;
    }

    /**
     * @param representSkuUnitPrice
     *          the representSkuUnitPrice to set
     */
    public void setRepresentSkuUnitPrice(BigDecimal representSkuUnitPrice) {
      this.representSkuUnitPrice = representSkuUnitPrice;
    }

    /**
     * @return the commodityDescription1En
     */
    public String getCommodityDescription1En() {
      return commodityDescription1En;
    }

    /**
     * @param commodityDescription1En
     *          the commodityDescription1En to set
     */
    public void setCommodityDescription1En(String commodityDescription1En) {
      this.commodityDescription1En = commodityDescription1En;
    }

    /**
     * @return the commodityDescription1Jp
     */
    public String getCommodityDescription1Jp() {
      return commodityDescription1Jp;
    }

    /**
     * @param commodityDescription1Jp
     *          the commodityDescription1Jp to set
     */
    public void setCommodityDescription1Jp(String commodityDescription1Jp) {
      this.commodityDescription1Jp = commodityDescription1Jp;
    }

    /**
     * @return the commodityDescription2En
     */
    public String getCommodityDescription2En() {
      return commodityDescription2En;
    }

    /**
     * @param commodityDescription2En
     *          the commodityDescription2En to set
     */
    public void setCommodityDescription2En(String commodityDescription2En) {
      this.commodityDescription2En = commodityDescription2En;
    }

    /**
     * @return the commodityDescription2Jp
     */
    public String getCommodityDescription2Jp() {
      return commodityDescription2Jp;
    }

    /**
     * @param commodityDescription2Jp
     *          the commodityDescription2Jp to set
     */
    public void setCommodityDescription2Jp(String commodityDescription2Jp) {
      this.commodityDescription2Jp = commodityDescription2Jp;
    }

    /**
     * @return the commodityDescription3En
     */
    public String getCommodityDescription3En() {
      return commodityDescription3En;
    }

    /**
     * @param commodityDescription3En
     *          the commodityDescription3En to set
     */
    public void setCommodityDescription3En(String commodityDescription3En) {
      this.commodityDescription3En = commodityDescription3En;
    }

    /**
     * @return the commodityDescription3Jp
     */
    public String getCommodityDescription3Jp() {
      return commodityDescription3Jp;
    }

    /**
     * @param commodityDescription3Jp
     *          the commodityDescription3Jp to set
     */
    public void setCommodityDescription3Jp(String commodityDescription3Jp) {
      this.commodityDescription3Jp = commodityDescription3Jp;
    }

    /**
     * @return the commodityDescriptionShortEn
     */
    public String getCommodityDescriptionShortEn() {
      return commodityDescriptionShortEn;
    }

    /**
     * @param commodityDescriptionShortEn
     *          the commodityDescriptionShortEn to set
     */
    public void setCommodityDescriptionShortEn(String commodityDescriptionShortEn) {
      this.commodityDescriptionShortEn = commodityDescriptionShortEn;
    }

    /**
     * @return the commodityDescriptionShortJp
     */
    public String getCommodityDescriptionShortJp() {
      return commodityDescriptionShortJp;
    }

    /**
     * @param commodityDescriptionShortJp
     *          the commodityDescriptionShortJp to set
     */
    public void setCommodityDescriptionShortJp(String commodityDescriptionShortJp) {
      this.commodityDescriptionShortJp = commodityDescriptionShortJp;
    }

    /**
     * @return the commodityNameJp
     */
    public String getCommodityNameJp() {
      return commodityNameJp;
    }

    /**
     * @param commodityNameJp
     *          the commodityNameJp to set
     */
    public void setCommodityNameJp(String commodityNameJp) {
      this.commodityNameJp = commodityNameJp;
    }

    /**
     * @param inBoundLifeDays
     *          the inBoundLifeDays to set
     */
    public void setInBoundLifeDays(Long inBoundLifeDays) {
      this.inBoundLifeDays = inBoundLifeDays;
    }

    /**
     * @return the inBoundLifeDays
     */
    public Long getInBoundLifeDays() {
      return inBoundLifeDays;
    }

    /**
     * @param outBoundLifeDays
     *          the outBoundLifeDays to set
     */
    public void setOutBoundLifeDays(Long outBoundLifeDays) {
      this.outBoundLifeDays = outBoundLifeDays;
    }

    /**
     * @return the outBoundLifeDays
     */
    public Long getOutBoundLifeDays() {
      return outBoundLifeDays;
    }

    /**
     * @param shelfLifeAlertDays
     *          the shelfLifeAlertDays to set
     */
    public void setShelfLifeAlertDays(Long shelfLifeAlertDays) {
      this.shelfLifeAlertDays = shelfLifeAlertDays;
    }

    /**
     * @return the shelfLifeAlertDays
     */
    public Long getShelfLifeAlertDays() {
      return shelfLifeAlertDays;
    }

    /**
     * @return the exportFlagErp
     */
    public Long getExportFlagErp() {
      return exportFlagErp;
    }

    /**
     * @param exportFlagErp
     *          the exportFlagErp to set
     */
    public void setExportFlagErp(Long exportFlagErp) {
      this.exportFlagErp = exportFlagErp;
    }

    /**
     * @return the exportFlagWms
     */
    public Long getExportFlagWms() {
      return exportFlagWms;
    }

    /**
     * @param exportFlagWms
     *          the exportFlagWms to set
     */
    public void setExportFlagWms(Long exportFlagWms) {
      this.exportFlagWms = exportFlagWms;
    }

    /**
     * @return the foodSecurityPrdLicenseNo
     */
    public String getFoodSecurityPrdLicenseNo() {
      return foodSecurityPrdLicenseNo;
    }

    /**
     * @param foodSecurityPrdLicenseNo
     *          the foodSecurityPrdLicenseNo to set
     */
    public void setFoodSecurityPrdLicenseNo(String foodSecurityPrdLicenseNo) {
      this.foodSecurityPrdLicenseNo = foodSecurityPrdLicenseNo;
    }

    /**
     * @return the foodSecurityDesignCode
     */
    public String getFoodSecurityDesignCode() {
      return foodSecurityDesignCode;
    }

    /**
     * @param foodSecurityDesignCode
     *          the foodSecurityDesignCode to set
     */
    public void setFoodSecurityDesignCode(String foodSecurityDesignCode) {
      this.foodSecurityDesignCode = foodSecurityDesignCode;
    }

    /**
     * @return the foodSecurityFactory
     */
    public String getFoodSecurityFactory() {
      return foodSecurityFactory;
    }

    /**
     * @param foodSecurityFactory
     *          the foodSecurityFactory to set
     */
    public void setFoodSecurityFactory(String foodSecurityFactory) {
      this.foodSecurityFactory = foodSecurityFactory;
    }

    /**
     * @return the foodSecurityFactorySite
     */
    public String getFoodSecurityFactorySite() {
      return foodSecurityFactorySite;
    }

    /**
     * @param foodSecurityFactorySite
     *          the foodSecurityFactorySite to set
     */
    public void setFoodSecurityFactorySite(String foodSecurityFactorySite) {
      this.foodSecurityFactorySite = foodSecurityFactorySite;
    }

    /**
     * @return the foodSecurityContact
     */
    public String getFoodSecurityContact() {
      return foodSecurityContact;
    }

    /**
     * @param foodSecurityContact
     *          the foodSecurityContact to set
     */
    public void setFoodSecurityContact(String foodSecurityContact) {
      this.foodSecurityContact = foodSecurityContact;
    }

    /**
     * @return the foodSecurityMix
     */
    public String getFoodSecurityMix() {
      return foodSecurityMix;
    }

    /**
     * @param foodSecurityMix
     *          the foodSecurityMix to set
     */
    public void setFoodSecurityMix(String foodSecurityMix) {
      this.foodSecurityMix = foodSecurityMix;
    }

    /**
     * @return the foodSecurityPlanStorage
     */
    public String getFoodSecurityPlanStorage() {
      return foodSecurityPlanStorage;
    }

    /**
     * @param foodSecurityPlanStorage
     *          the foodSecurityPlanStorage to set
     */
    public void setFoodSecurityPlanStorage(String foodSecurityPlanStorage) {
      this.foodSecurityPlanStorage = foodSecurityPlanStorage;
    }

    /**
     * @return the foodSecurityPeriod
     */
    public String getFoodSecurityPeriod() {
      return foodSecurityPeriod;
    }

    /**
     * @param foodSecurityPeriod
     *          the foodSecurityPeriod to set
     */
    public void setFoodSecurityPeriod(String foodSecurityPeriod) {
      this.foodSecurityPeriod = foodSecurityPeriod;
    }

    /**
     * @return the foodSecurityFoodAdditive
     */
    public String getFoodSecurityFoodAdditive() {
      return foodSecurityFoodAdditive;
    }

    /**
     * @param foodSecurityFoodAdditive
     *          the foodSecurityFoodAdditive to set
     */
    public void setFoodSecurityFoodAdditive(String foodSecurityFoodAdditive) {
      this.foodSecurityFoodAdditive = foodSecurityFoodAdditive;
    }

    /**
     * @return the foodSecuritySupplier
     */
    public String getFoodSecuritySupplier() {
      return foodSecuritySupplier;
    }

    /**
     * @param foodSecuritySupplier
     *          the foodSecuritySupplier to set
     */
    public void setFoodSecuritySupplier(String foodSecuritySupplier) {
      this.foodSecuritySupplier = foodSecuritySupplier;
    }

    /**
     * @return the foodSecurityProductDateStart
     */
    public String getFoodSecurityProductDateStart() {
      return foodSecurityProductDateStart;
    }

    /**
     * @param foodSecurityProductDateStart
     *          the foodSecurityProductDateStart to set
     */
    public void setFoodSecurityProductDateStart(String foodSecurityProductDateStart) {
      this.foodSecurityProductDateStart = foodSecurityProductDateStart;
    }

    /**
     * @return the foodSecurityProductDateEnd
     */
    public String getFoodSecurityProductDateEnd() {
      return foodSecurityProductDateEnd;
    }

    /**
     * @param foodSecurityProductDateEnd
     *          the foodSecurityProductDateEnd to set
     */
    public void setFoodSecurityProductDateEnd(String foodSecurityProductDateEnd) {
      this.foodSecurityProductDateEnd = foodSecurityProductDateEnd;
    }

    /**
     * @return the foodSecurityStockDateStart
     */
    public String getFoodSecurityStockDateStart() {
      return foodSecurityStockDateStart;
    }

    /**
     * @param foodSecurityStockDateStart
     *          the foodSecurityStockDateStart to set
     */
    public void setFoodSecurityStockDateStart(String foodSecurityStockDateStart) {
      this.foodSecurityStockDateStart = foodSecurityStockDateStart;
    }

    /**
     * @return the foodSecurityStockDateEnd
     */
    public String getFoodSecurityStockDateEnd() {
      return foodSecurityStockDateEnd;
    }

    /**
     * @param foodSecurityStockDateEnd
     *          the foodSecurityStockDateEnd to set
     */
    public void setFoodSecurityStockDateEnd(String foodSecurityStockDateEnd) {
      this.foodSecurityStockDateEnd = foodSecurityStockDateEnd;
    }

    /**
     * @return the importCommodityTypeEc
     */
    public String getImportCommodityTypeEc() {
      return importCommodityTypeEc;
    }

    /**
     * @param importCommodityTypeEc
     *          the importCommodityTypeEc to set
     */
    public void setImportCommodityTypeEc(String importCommodityTypeEc) {
      this.importCommodityTypeEc = importCommodityTypeEc;
    }

    /**
     * @return the clearCommodityTypeEc
     */
    public String getClearCommodityTypeEc() {
      return clearCommodityTypeEc;
    }

    /**
     * @param clearCommodityTypeEc
     *          the clearCommodityTypeEc to set
     */
    public void setClearCommodityTypeEc(String clearCommodityTypeEc) {
      this.clearCommodityTypeEc = clearCommodityTypeEc;
    }

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
     * @return the reserveCommodityType2
     */
    public String getReserveCommodityType2() {
      return reserveCommodityType2;
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
     * @return the suitFlg
     */
    public boolean isSuitFlg() {
      return suitFlg;
    }

    
    /**
     * @param suitFlg the suitFlg to set
     */
    public void setSuitFlg(boolean suitFlg) {
      this.suitFlg = suitFlg;
    }

    /**
     * @return the advertContent
     */
    public String getAdvertContent() {
      return advertContent;
    }

    /**
     * @param advertContent the advertContent to set
     */
    public void setAdvertContent(String advertContent) {
      this.advertContent = advertContent;
    }

  }

  /**
   * U1040120:商品登録のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class TmallCommodityEditSkuBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Length(20)
    @Metadata(name = "規格1値のID(=TMALL属性値ID)", order = 8)
    private String standardDetail1Id;

    @Length(20)
    @Metadata(name = "規格2値のID(=TMALL属性値ID)", order = 10)
    private String standardDetail2Id;

    @Length(24)
    @AlphaNum2
    @Metadata(name = "代表SKUコード")
    private String representSkuCode;

    @Length(50)
    @Metadata(name = "SKU名称")
    private String skuName;

    @Length(13)
    @JanCode
    @Metadata(name = "JANコード")
    private String janCode;

    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "Ec商品単価")
    private String unitPrice;

    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "Ec特別価格")
    private String discountPrice;

    @Length(20)
    @Metadata(name = "規格1値の文字列(値のIDなければ、これを利用）")
    private String standardDetail1Name;

    @Length(20)
    @Metadata(name = "規格2値の文字列(値のIDなければ、これを利用）")
    private String standardDetail2Name;

    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = " 仕入価格")
    private String purchasePrice;

    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "tmall商品単価")
    private String tmallUnitPrice;

    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "tmall特別価格")
    private String tmallDiscountPrice;

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
    @Range(min = 1, max = 99999999)
    @Metadata(name = "予約上限数")
    private String reservationLimit;

    @Length(8)
    @Quantity
    @Range(min = 1, max = 99999999)
    @Metadata(name = "注文毎予約上限数")
    private String oneshotReservationLimit;

    @Length(8)
    @Quantity
    @Range(min = 0, max = 99999999)
    @Metadata(name = "在庫閾値")
    private String stockThreshold;

    @Length(8)
    @Currency
    @Metadata(name = "重量")
    private BigDecimal weight;

    private Date updateDatetime;

    @Length(8)
    @Metadata(name = "最小仕入数", order = 18)
    private Long minimumOrder;

    @Length(8)
    @Metadata(name = "最大仕入数", order = 19)
    private Long maximumOrder;

    @Length(8)
    @Metadata(name = "最小単位の仕入数", order = 20)
    private Long orderMultiple;

    @Length(8)
    @Metadata(name = "在庫警告日数", order = 21)
    private Long stockWarning;

    public Long getStockWarning() {
      return stockWarning;
    }

    public void setStockWarning(Long stockWarning) {
      this.stockWarning = stockWarning;
    }

    public Long getMinimumOrder() {
      return minimumOrder;
    }

    public void setMinimumOrder(Long minimumOrder) {
      this.minimumOrder = minimumOrder;
    }

    public Long getMaximumOrder() {
      return maximumOrder;
    }

    public void setMaximumOrder(Long maximumOrder) {
      this.maximumOrder = maximumOrder;
    }

    public Long getOrderMultiple() {
      return orderMultiple;
    }

    public void setOrderMultiple(Long orderMultiple) {
      this.orderMultiple = orderMultiple;
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
     * @return the standardDetail1Id
     */
    public String getStandardDetail1Id() {
      return standardDetail1Id;
    }

    /**
     * @param standardDetail1Id
     *          the standardDetail1Id to set
     */
    public void setStandardDetail1Id(String standardDetail1Id) {
      this.standardDetail1Id = standardDetail1Id;
    }

    /**
     * @return the standardDetail2Id
     */
    public String getStandardDetail2Id() {
      return standardDetail2Id;
    }

    /**
     * @param standardDetail2Id
     *          the standardDetail2Id to set
     */
    public void setStandardDetail2Id(String standardDetail2Id) {
      this.standardDetail2Id = standardDetail2Id;
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

    /**
     * @param tmallUnitPrice
     *          the tmallUnitPrice to set
     */
    public void setTmallUnitPrice(String tmallUnitPrice) {
      this.tmallUnitPrice = tmallUnitPrice;
    }

    /**
     * @return the tmallUnitPrice
     */
    public String getTmallUnitPrice() {
      return tmallUnitPrice;
    }

    /**
     * @param tmallDiscountPrice
     *          the tmallDiscountPrice to set
     */
    public void setTmallDiscountPrice(String tmallDiscountPrice) {
      this.tmallDiscountPrice = tmallDiscountPrice;
    }

    /**
     * @return the tmallDiscountPrice
     */
    public String getTmallDiscountPrice() {
      return tmallDiscountPrice;
    }

    /**
     * @param purchasePrice
     *          the purchasePrice to set
     */
    public void setPurchasePrice(String purchasePrice) {
      this.purchasePrice = purchasePrice;
    }

    /**
     * @return the purchasePrice
     */
    public String getPurchasePrice() {
      return purchasePrice;
    }

    /**
     * @param skuName
     *          the skuName to set
     */
    public void setSkuName(String skuName) {
      this.skuName = skuName;
    }

    /**
     * @return the skuName
     */
    public String getSkuName() {
      return skuName;
    }

    /**
     * @param standardDetail1Name
     *          the standardDetail1Name to set
     */
    public void setStandardDetail1Name(String standardDetail1Name) {
      this.standardDetail1Name = standardDetail1Name;
    }

    /**
     * @return the standardDetail1Name
     */
    public String getStandardDetail1Name() {
      return standardDetail1Name;
    }

    /**
     * @param standardDetail2Name
     *          the standardDetail2Name to set
     */
    public void setStandardDetail2Name(String standardDetail2Name) {
      this.standardDetail2Name = standardDetail2Name;
    }

    /**
     * @return the standardDetail2Name
     */
    public String getStandardDetail2Name() {
      return standardDetail2Name;
    }

    /**
     * @param weight
     *          the weight to set
     */
    public void setWeight(BigDecimal weight) {
      this.weight = weight;
    }

    /**
     * @return the weight
     */
    public BigDecimal getWeight() {
      return weight;
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
  public TmallCommodityEditHeaderBean getHeader() {
    return header;
  }

  /**
   * skuを取得します。
   * 
   * @return sku
   */
  public TmallCommodityEditSkuBean getSku() {
    return sku;
  }

  /**
   * headerを設定します。
   * 
   * @param header
   *          header
   */
  public void setHeader(TmallCommodityEditHeaderBean header) {
    this.header = header;
  }

  /**
   * skuを設定します。
   * 
   * @param sku
   *          sku
   */
  public void setSku(TmallCommodityEditSkuBean sku) {
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

  // 品牌属性
  private List<TmallPropertyBean> commodityPropertysList = new ArrayList<TmallPropertyBean>();

  // 商品关键属性
  private List<TmallPropertyBean> commodityStandardPopList = new ArrayList<TmallPropertyBean>();

  /**
   * @return the commodityStandardPopList
   */
  public List<TmallPropertyBean> getCommodityStandardPopList() {
    return commodityStandardPopList;
  }

  /**
   * @param commodityStandardPopList
   *          the commodityStandardPopList to set
   */
  public void setCommodityStandardPopList(List<TmallPropertyBean> commodityStandardPopList) {
    this.commodityStandardPopList = commodityStandardPopList;
  }

  /**
   * @return the commodityPropertysList
   */
  public List<TmallPropertyBean> getCommodityPropertysList() {
    return commodityPropertysList;
  }

  /**
   * @param commodityPropertysList
   *          the commodityPropertysList to set
   */
  public void setCommodityPropertysList(List<TmallPropertyBean> commodityPropertysList) {
    this.commodityPropertysList = commodityPropertysList;
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    this.setCommodityCode(reqparam.get("commodityCode"));
    // this.setCommodityDiscountPrice(reqparam.get("commodityDiscountPrice"));
    // this.setCommodityTmallDiscountPrice(reqparam.get("commodityTmallDiscountPrice"));
    if (StringUtil.hasValue(reqparam.get("commodityDiscountPrice"))) {
      this.setCommodityDiscountPrice(reqparam.get("commodityDiscountPrice"));
    } else {
      this.setCommodityDiscountPrice(this.sku.getDiscountPrice());
    }
    if (StringUtil.hasValue(reqparam.get("commodityTmallDiscountPrice"))) {
      this.setCommodityTmallDiscountPrice(reqparam.get("commodityTmallDiscountPrice"));
    } else {
      this.setCommodityTmallDiscountPrice(this.sku.getTmallDiscountPrice());
    }
    this.setCommodityBrandName(reqparam.get("commodityBrandName"));
    this.setCategoryName(reqparam.get("categoryName"));
    this.setCategoryCode(reqparam.get("tcategoryId"));
    this.setDisplayPreview(false);
    this.setPropertyHtml(reqparam.get("propertyHtml"));
    // 用于存放所有页面属性 的控件名称
    List<String> propertyIds = new ArrayList<String>();
    header.setTcategoryId(NumUtil.toLong(reqparam.get("tcategoryId")));
    reqparam.copy(header);
    if (StringUtil.hasValue(reqparam.get("brandCode"))) {
      header.setBrandCode(reqparam.get("brandCode"));
    } else {
      header.setBrandCode(this.header.getBrandCode());
    }
    /**
     * 处理属性页面元素
     */
    String brandCode = header.getBrandCode();
    // 用于存放品牌属性
    List<TmallPropertyBean> ps = new ArrayList<TmallPropertyBean>();
    // 用于存放关键属性
    List<TmallPropertyBean> standardPop = new ArrayList<TmallPropertyBean>();
    if (StringUtil.hasValue(brandCode) && "edit".equals(this.getShowMode())) {
      Map<String, String[]> param = reqparam.getRequestMap();
      Set<String> keys = param.keySet();
      Iterator<String> iterator = keys.iterator();
      while (iterator.hasNext()) {
        String name = iterator.next();
        // 获取品牌属性的控件名称
        if ((name.indexOf(brandCode) >= 0 || name.indexOf(String.valueOf(header.getTcategoryId())) >= 0)
            && name.indexOf("_id") >= 0) {
          // 获取属性Id
          propertyIds.add(name);
        }
      }
      for (int i = 0; i < propertyIds.size(); i++) {
        // 得到属性对应的界面控件名称
        String propertyIdName = propertyIds.get(i);
        // 得到名称的前缀 格式 :brandcode_propertyId
        String namePrefix = propertyIdName.substring(0, propertyIdName.lastIndexOf("_"));
        // 获取属性id
        String propertyId = param.get(propertyIdName)[0];
        // 获取属性名称
        String propertyName = param.get(namePrefix + "_name")[0];
        // 获取属性值ID
        String valueId[] = param.get(namePrefix + "_valueId");

        // 手动输入的值 名称
        String manualValues[] = param.get(namePrefix + "_manual_value");
        TmallPropertyBean property = new TmallPropertyBean();
        if (header.getTcategoryId() != null) {
          property.setCategoryId(NumUtil.toString(header.getTcategoryId()));
        }
        property.setPropertyId(propertyId);
        property.setPropertyName(propertyName);
        property.setCommodityCode(this.getCommodityCode());
        List<TmallPropertyValueBean> values = new ArrayList<TmallPropertyValueBean>();
        if (valueId != null && valueId.length > 0) {
          if (manualValues != null && manualValues.length > 0) {// 手动输入
            for (int j = 0; j < valueId.length; j++) {
              if ("0".equals(valueId[j])) {
                TmallPropertyValueBean p = new TmallPropertyValueBean();
                p.setPropertyId(propertyId);
                p.setValueId("0");
                p.setValueName(manualValues[0]);
                values.add(p);
                break;
              }
            }
          } else { // 不是手动输入
            for (int j = 0; j < valueId.length; j++) {
              TmallPropertyValueBean p = new TmallPropertyValueBean();
              p.setPropertyId(propertyId);
              p.setValueId(valueId[j]);
              values.add(p);
            }
          }

        } else {
          // 如果页面没有选择或者输入属性值 设置 一个空的对象
          // 在action中validat时会处理该空对象
          TmallPropertyValueBean p = new TmallPropertyValueBean();
          values.add(p);
        }
        property.setValues(values);
        // 存放到品牌属性集合
        if (propertyIdName.indexOf(brandCode) >= 0) {
          ps.add(property);
        }
        // 存放到关键属性集合
        if (propertyIdName.indexOf(String.valueOf(header.getTcategoryId())) >= 0) {
          standardPop.add(property);
        }
      }
      // 设置品牌属性；
      this.commodityPropertysList = ps;
      // 设置商品关键属性；
      this.commodityStandardPopList = standardPop;
      // 将商品属性转换成json字符串保存到Bean中
      List<TmallCommodityProperty> pss = TmallCommodityEditBaseAction.copyTmallCommodityProperty(this.commodityPropertysList,
          this.commodityStandardPopList);
      DataContainer<TmallCommodityProperty> datas = new DataContainer<TmallCommodityProperty>();
      datas.setElements(pss);
      this.setCommodityPropertyJson(datas.toJsonString());
    }
    header.setCommodityCode(reqparam.get("commodityCode"));
    header.setCombinationAmount(reqparam.get("combinationAmount"));
    header.setTmallCommoditySearchWords(reqparam.get("tmallCommoditySearchWords"));
    header.setCommodityName(StringUtil.parse(reqparam.get("commodityName")));
    header.setCommodityNameEn(StringUtil.parse(reqparam.get("commodityNameEn")));
    header.setCommoditySearchWords(reqparam.get("commoditySearchWords"));
    header.setDiscountPriceEndDatetime(reqparam.getDateTimeString("discountPriceEndDatetime"));
    header.setDiscountPriceStartDatetime(reqparam.getDateTimeString("discountPriceStartDatetime"));
    header.setSaleStartDatetime(reqparam.getDateTimeString("saleStartDatetime"));
    header.setSaleEndDatetime(reqparam.getDateTimeString("saleEndDatetime"));
    header.setSpecFlag(reqparam.get("specFlag"));
    header.setLeadTime(StringUtil.isNullOrEmpty(reqparam.get("leadTime")) ? 1 : NumUtil.toLong(reqparam.get("leadTime")));
    header.setStandard1Name(reqparam.get("standard1Name"));
    header.setStandard2Name(reqparam.get("standard2Name"));
    header.setWarningFlag(reqparam.get("warningFlag"));
    header.setShelfLifeDays(reqparam.get("shelfLifeDays"));
    if (StringUtil.hasValue(reqparam.get("shelfLifeFlag"))) {
      header.setShelfLifeFlag(reqparam.get("shelfLifeFlag"));
    } else {
      header.setShelfLifeFlag(this.header.getShelfLifeFlag());
    }

    // modified by cs_yuli 20120607 start
    header.setInBoundLifeDays(StringUtil.isNullOrEmpty(reqparam.get("inBoundLifeDays")) ? null : NumUtil.toLong(reqparam
        .get("inBoundLifeDays")));
    header.setOutBoundLifeDays(StringUtil.isNullOrEmpty(reqparam.get("outBoundLifeDays")) ? null : NumUtil.toLong(reqparam
        .get("outBoundLifeDays")));
    header.setShelfLifeAlertDays(StringUtil.isNullOrEmpty(reqparam.get("shelfLifeAlertDays")) ? null : NumUtil.toLong(reqparam
        .get("shelfLifeAlertDays")));
    // modified by cs_yuli 20120607 end
    // header.setQuantityPerUnit(reqparam.get("quantityPerUnit"));
    //2014/4/28 京东WBS对应 ob_李 add start
    header.setAdvertContent(reqparam.get("advertContent"));
    //2014/4/28 京东WBS对应 ob_李 add end
    header.setCommodityDescription1(reqparam.get("commodityDescription1"));
    if (StringUtil.hasValue(reqparam.get("commodityDescription2"))) {
      header.setCommodityDescription2(reqparam.get("commodityDescription2"));
    } else {
      header.setCommodityDescription2(this.header.getCommodityDescription2());
    }
    header.setCommodityDescription3(reqparam.get("commodityDescription3"));
    header.setCommodityDescriptionShort(reqparam.get("commodityDescriptionShort"));
    if (StringUtil.hasValue(reqparam.get("shopChangeFlag"))) {
      header.setShopChangeFlag(reqparam.get("shopChangeFlag"));
    } else {
      header.setShopChangeFlag(this.header.getShopChangeFlag());
    }
    if (StringUtil.hasValue(reqparam.get("shopReturnFlag"))) {
      header.setShopReturnFlag(reqparam.get("shopReturnFlag"));
    } else {
      header.setShopReturnFlag(this.header.getShopReturnFlag());
    }
    if (StringUtil.hasValue(reqparam.get("custReturnFlag"))) {
      header.setCustReturnFlag(reqparam.get("custReturnFlag"));
    } else {
      header.setCustReturnFlag(this.header.getCustReturnFlag());
    }
    header.setKeywordCn2(reqparam.get("keywordCn2"));
    header.setKeywordEn2(reqparam.get("keywordEn2"));
    header.setKeywordJp2(reqparam.get("keywordJp2"));
    header.setHotFlgEn(reqparam.get("hotFlgEn"));
    header.setHotFlgJp(reqparam.get("hotFlgJp"));
    header.setFoodSecurityProductDateStart(reqparam.getDateString("foodSecurityProductDateStart"));
    header.setFoodSecurityProductDateEnd(reqparam.getDateString("foodSecurityProductDateEnd"));
    header.setFoodSecurityStockDateStart(reqparam.getDateString("foodSecurityStockDateStart"));
    header.setFoodSecurityStockDateEnd(reqparam.getDateString("foodSecurityStockDateEnd"));
    header.setTitle(reqparam.get("title"));
    header.setTitleEn(reqparam.get("titleEn"));
    header.setTitleJp(reqparam.get("titleJp"));
    header.setDescription(reqparam.get("description"));
    header.setDescriptionEn(reqparam.get("descriptionEn"));
    header.setDescriptionJp(reqparam.get("descriptionJp"));
    header.setKeyword(reqparam.get("keyword"));
    header.setKeywordEn(reqparam.get("keywordEn"));
    header.setKeywordJp(reqparam.get("keywordJp"));
    sku.setMinimumOrder(StringUtil.isNullOrEmpty(reqparam.get("minimumOrder")) ? null : NumUtil
        .toLong(reqparam.get("minimumOrder")));
    sku.setMaximumOrder(StringUtil.isNullOrEmpty(reqparam.get("maximumOrder")) ? null : NumUtil
        .toLong(reqparam.get("maximumOrder")));
    sku.setOrderMultiple(StringUtil.isNullOrEmpty(reqparam.get("orderMultiple")) ? null : NumUtil.toLong(reqparam
        .get("orderMultiple")));
    sku.setStockWarning(StringUtil.isNullOrEmpty(reqparam.get("stockWarning")) ? null : NumUtil
        .toLong(reqparam.get("stockWarning")));
    sku.setUnitPrice(StringUtil.isNullOrEmpty(reqparam.get("UnitPrices")) ? null : reqparam.get("UnitPrices"));
    sku.setDiscountPrice(StringUtil.isNullOrEmpty(reqparam.get("DiscountPrices")) ? null : reqparam.get("DiscountPrices"));
    sku.setTmallUnitPrice(StringUtil.isNullOrEmpty(reqparam.get("TmallUnitPrices")) ? null : reqparam.get("TmallUnitPrices"));
    sku.setTmallDiscountPrice(StringUtil.isNullOrEmpty(reqparam.get("TmallDiscountPrices")) ? null : reqparam
        .get("TmallDiscountPrices"));

    if (NumUtil.isDecimal(reqparam.get("weight"))) {
      sku.setWeight(StringUtil.isNullOrEmpty(reqparam.get("weight")) ? null : new BigDecimal(reqparam.get("weight")));
    } else {
      sku.setWeight(new BigDecimal(0));
    }
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040220";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.TmallCommodityEditBean.0");
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

  /**
   * displayClientTypeを取得します。
   * 
   * @return displayClientType
   */
  public DisplayClientType[] getDisplayClientType() {
    return ArrayUtil.immutableCopy(this.displayClientType);
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

  public ReserveCommodityType1[] getReserveCommodityType1() {
    return ArrayUtil.immutableCopy(this.reserveCommodityType1);
  }

  public ReserveCommodityType2[] getReserveCommodityType2() {
    return ArrayUtil.immutableCopy(this.reserveCommodityType2);
  }

  public ReserveCommodityType3[] getReserveCommodityType3() {
    return ArrayUtil.immutableCopy(this.reserveCommodityType3);
  }

  public NewReserveCommodityType1[] getNewReserveCommodityType1() {
    return ArrayUtil.immutableCopy(this.newReserveCommodityType1);
  }

  public NewReserveCommodityType2[] getNewReserveCommodityType2() {
    return ArrayUtil.immutableCopy(this.newReserveCommodityType2);
  }

  public NewReserveCommodityType3[] getNewReserveCommodityType3() {
    return ArrayUtil.immutableCopy(this.newReserveCommodityType3);
  }

  public NewReserveCommodityType4[] getNewReserveCommodityType4() {
    return ArrayUtil.immutableCopy(this.newReserveCommodityType4);
  }

  public NewReserveCommodityType5[] getNewReserveCommodityType5() {
    return ArrayUtil.immutableCopy(this.newReserveCommodityType5);
  }

  public TmallMjsType[] getTmallMjsType() {
    return ArrayUtil.immutableCopy(this.tmallMjsType);
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

  public void setReserveCommodityType1(ReserveCommodityType1[] reserveCommodityType1) {
    this.reserveCommodityType1 = ArrayUtil.immutableCopy(reserveCommodityType1);
  }

  public void setReserveCommodityType2(ReserveCommodityType2[] reserveCommodityType2) {
    this.reserveCommodityType2 = ArrayUtil.immutableCopy(reserveCommodityType2);
  }

  public void setReserveCommodityType3(ReserveCommodityType3[] reserveCommodityType3) {
    this.reserveCommodityType3 = ArrayUtil.immutableCopy(reserveCommodityType3);
  }

  public void setTmallMjsType(TmallMjsType[] tmallMjsType) {
    this.tmallMjsType = ArrayUtil.immutableCopy(tmallMjsType);
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
   * @param list
   *          the list to set
   */
  public void setList(List<TmallCommodityEditSkuBean> list) {
    this.list = list;
  }

  /**
   * @return the list
   */
  public List<TmallCommodityEditSkuBean> getList() {
    return list;
  }

  /**
   * @return the listCategorys
   */
  public List<TmallCategory> getListCategorys() {
    return listCategorys;
  }

  /**
   * @param listCategorys
   *          the listCategorys to set
   */
  public void setListCategorys(List<TmallCategory> listCategorys) {
    this.listCategorys = listCategorys;
  }

  /**
   * @param categoryCode
   *          the categoryCode to set
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  /**
   * @return the categoryCode
   */
  public String getCategoryCode() {
    return categoryCode;
  }

  /**
   * @param categoryName
   *          the categoryName to set
   */
  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  /**
   * @return the categoryName
   */
  public String getCategoryName() {
    return categoryName;
  }

  public static class TmallPropertyValueBean {

    @Required
    @Length(16)
    @Metadata(name = "属性值id", order = 1)
    private String valueId;

    @Length(50)
    @Metadata(name = "属性值名称", order = 2)
    private String valueName;

    @Required
    @Length(16)
    @Metadata(name = "所属属性id", order = 3)
    private String propertyId;

    /**
     * @return the valueId
     */
    public String getValueId() {
      return valueId;
    }

    /**
     * @param valueId
     *          the valueId to set
     */
    public void setValueId(String valueId) {
      this.valueId = valueId;
    }

    /**
     * @return the valueName
     */
    public String getValueName() {
      return valueName;
    }

    /**
     * @param valueName
     *          the valueName to set
     */
    public void setValueName(String valueName) {
      this.valueName = valueName;
    }

    /**
     * @return the propertyId
     */
    public String getPropertyId() {
      return propertyId;
    }

    /**
     * @param propertyId
     *          the propertyId to set
     */
    public void setPropertyId(String propertyId) {
      this.propertyId = propertyId;
    }

  }

  public static class TmallPropertyBean {

    @Required
    @Length(16)
    @Metadata(name = "所属类目ID", order = 6)
    private String categoryId;

    @Required
    @Length(16)
    @Metadata(name = "属性id", order = 1)
    private String propertyId;

    @Required
    @Length(50)
    @Metadata(name = "属性名称", order = 2)
    private String propertyName;

    @Metadata(name = "商品编码", order = 2)
    private String commodityCode;

    List<TmallPropertyValueBean> values = new ArrayList<TmallPropertyValueBean>();

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
     * @return the values
     */
    public List<TmallPropertyValueBean> getValues() {
      return values;
    }

    /**
     * @param values
     *          the values to set
     */
    public void setValues(List<TmallPropertyValueBean> values) {
      this.values = values;
    }

    /**
     * @return the categoryId
     */
    public String getCategoryId() {
      return categoryId;
    }

    /**
     * @param categoryId
     *          the categoryId to set
     */
    public void setCategoryId(String categoryId) {
      this.categoryId = categoryId;
    }

    /**
     * @return the propertyId
     */
    public String getPropertyId() {
      return propertyId;
    }

    /**
     * @param propertyId
     *          the propertyId to set
     */
    public void setPropertyId(String propertyId) {
      this.propertyId = propertyId;
    }

    /**
     * @return the propertyName
     */
    public String getPropertyName() {
      return propertyName;
    }

    /**
     * @param propertyName
     *          the propertyName to set
     */
    public void setPropertyName(String propertyName) {
      this.propertyName = propertyName;
    }
  }

  
  /**
   * @return the showShelfLifeReadOnly
   */
  public boolean isShowShelfLifeReadOnly() {
    return showShelfLifeReadOnly;
  }

  
  /**
   * @param showShelfLifeReadOnly the showShelfLifeReadOnly to set
   */
  public void setShowShelfLifeReadOnly(boolean showShelfLifeReadOnly) {
    this.showShelfLifeReadOnly = showShelfLifeReadOnly;
  }

}
