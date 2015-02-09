package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.domain.TmallSaleStatus;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040110:商品マスタのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class TmallCommodityListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue;

  private List<TmallCommodityListResult> list = new ArrayList<TmallCommodityListResult>();

  private List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> searchStockStatusList = new ArrayList<CodeAttribute>();

  private CodeAttribute[] displaySaleStatusList = TmallSaleStatus.values();

  private List<NameValue> displayStockStatusList = NameValue.asList("0:"
      + Messages.getString("web.bean.back.catalog.CommodityListBean.1") + "/1:"
      + Messages.getString("web.bean.back.catalog.CommodityListBean.2") + "/2:"
      + Messages.getString("web.bean.back.catalog.CommodityListBean.3"));

  private List<NameValue> displaySaleTypeList = NameValue.asList("0:"
      + Messages.getString("web.bean.back.catalog.CommodityListBean.4") + "/1:"
      + Messages.getString("web.bean.back.catalog.CommodityListBean.5") + "/2:"
      + Messages.getString("web.bean.back.catalog.CommodityListBean.6"));

  private List<NameValue> displayCommidityTypeList = NameValue.asList("0:" + "普通商品" + "/1:" + "礼品" + "/2:" + "宣传品" + "/3:" + "全部商品");

  private List<NameValue> displayMjsTypeList = NameValue.asList("0:" + "非赠品" + "/1:" + "赠品"+"/2:" + "全部商品");

  private List<NameValue> combinationType=NameValue.asList("0:" + "普通商品" + "/1:" + "组合商品"+"/2:" + "全部商品");
  
  @Length(16)
  @Metadata(name = "ショップコード")
  private String searchShopCode;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード(From)")
  private String searchCommodityCodeStart;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード(To)")
  private String searchCommodityCodeEnd;

  @Length(24)
  @AlphaNum2
  @Metadata(name = "SKUコード")
  private String searchSkuCode;

  @Length(50)
  @Metadata(name = "商品名")
  private String searchCommodityName;

  @Length(500)
  @Metadata(name = "検索キーワード")
  private String searchCommoditySearchWords;

  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "販売開始日時(From)")
  private String searchSaleStartDateRangeFrom;

  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "販売開始日時(To)")
  private String searchSaleStartDateRangeTo;

  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "販売終了日時(From)")
  private String searchSaleEndDateRangeFrom;

  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "販売開始日時(To)")
  private String searchSaleEndDateRangeTo;

  @Length(8)
  @Quantity
  @Range(min = 0, max = 99999999)
  @Metadata(name = "在庫数(From)")
  private String searchStockQuantityStart;

  @Length(8)
  @Quantity
  @Range(min = 0, max = 99999999)
  @Metadata(name = "在庫数(To)")
  private String searchStockQuantityEnd;

  @Metadata(name = "在庫")
  private String[] searchStockStatus;

  @Metadata(name = "販売状態")
  private String searchSaleType;

  @Metadata(name = "商品区分")
  private String searchCommidityType = "3";

  @Metadata(name = "tmall满就送区分")
  private String searchTmallMjsCommodityflg = "2";
 
  @Metadata(name = "组合商品")
  private String combination="2";

  @Metadata(name = "適用期間")
  private String[] searchSaleStatus;

  @Metadata(name = "表示クライアントPC")
  private String searchDisplayClientPc;

  @Metadata(name = "表示クライアントモバイル")
  private String searchDisplayClientMobile;

  @Metadata(name = "入荷お知らせ")
  private String searchArrivalGoods;

  @Metadata(name = "在庫閾値")
  private String searchSafetyStock;

  private String advancedSearchMode;

  private List<String> checkedCommodityList = new ArrayList<String>();

  private boolean displayShopList = false;

  private boolean displayShopNameColumn = false;

  private boolean displayCsvExportButton = false;

  private boolean displayUpdateButton = false;

  private boolean displayDeleteButton = false;
  
  /**
   * U1040110:商品マスタのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class TmallCommodityListResult implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String shopCode;

    private String shopName;

    private String commodityCode;

    private String commodityName;

    private String unitPrice;

    private String saleType;

    private String saleStatus;

    private String stockStatus;

    private String commodityImageUrl;

    private String commodityTaxType;

    private boolean relatedCommodityA = false;

    private boolean relatedCommodityB = false;

    private boolean relatedCategory = false;

    private boolean relatedTag = false;

    private boolean relatedCampaign = false;

    private boolean relatedGift = false;

    private Long commodityType;

    private Date updatedDatetime;
    
    //2014/4/28 京东WBS对应 ob_李 add start
    private boolean displayJdDescribeButton = false;
    //2014/4/28 京东WBS对应 ob_李 add start

    public Long getCommodityType() {
      return commodityType;
    }

    public void setCommodityType(Long commodityType) {
      this.commodityType = commodityType;
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
     * saleTypeを取得します。
     * 
     * @return saleType
     */
    public String getSaleType() {
      return saleType;
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
     * stockStatusを取得します。
     * 
     * @return stockStatus
     */
    public String getStockStatus() {
      return stockStatus;
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
     * saleTypeを設定します。
     * 
     * @param saleType
     *          saleType
     */
    public void setSaleType(String saleType) {
      this.saleType = saleType;
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
     * stockStatusを設定します。
     * 
     * @param stockStatus
     *          stockStatus
     */
    public void setStockStatus(String stockStatus) {
      this.stockStatus = stockStatus;
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
     * relatedCampaignを取得します。
     * 
     * @return relatedCampaign
     */
    public boolean isRelatedCampaign() {
      return relatedCampaign;
    }

    /**
     * relatedCategoryを取得します。
     * 
     * @return relatedCategory
     */
    public boolean isRelatedCategory() {
      return relatedCategory;
    }

    /**
     * relatedCommodityを取得します。
     * 
     * @return relatedCommodityA
     */
    public boolean isRelatedCommodityA() {
      return relatedCommodityA;
    }

    /**
     * relatedGiftを取得します。
     * 
     * @return relatedGift
     */
    public boolean isRelatedGift() {
      return relatedGift;
    }

    /**
     * relatedTagを取得します。
     * 
     * @return relatedTag
     */
    public boolean isRelatedTag() {
      return relatedTag;
    }

    /**
     * relatedCampaignを設定します。
     * 
     * @param relatedCampaign
     *          relatedCampaign
     */
    public void setRelatedCampaign(boolean relatedCampaign) {
      this.relatedCampaign = relatedCampaign;
    }

    /**
     * relatedCategoryを設定します。
     * 
     * @param relatedCategory
     *          relatedCategory
     */
    public void setRelatedCategory(boolean relatedCategory) {
      this.relatedCategory = relatedCategory;
    }

    /**
     * relatedCommodityを設定します。
     * 
     * @param relatedCommodity
     *          relatedCommodity
     */
    public void setRelatedCommodityA(boolean relatedCommodity) {
      this.relatedCommodityA = relatedCommodity;
    }

    /**
     * relatedGiftを設定します。
     * 
     * @param relatedGift
     *          relatedGift
     */
    public void setRelatedGift(boolean relatedGift) {
      this.relatedGift = relatedGift;
    }

    /**
     * relatedTagを設定します。
     * 
     * @param relatedTag
     *          relatedTag
     */
    public void setRelatedTag(boolean relatedTag) {
      this.relatedTag = relatedTag;
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
     *          updatedDatetime
     */
    public void setUpdatedDatetime(Date updatedDatetime) {
      this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * commodityImageUrlを取得します。
     * 
     * @return the commodityImageUrl
     */
    public String getCommodityImageUrl() {
      return commodityImageUrl;
    }

    /**
     * commodityImageUrlを設定します。
     * 
     * @param commodityImageUrl
     *          commodityImageUrl
     */
    public void setCommodityImageUrl(String commodityImageUrl) {
      this.commodityImageUrl = commodityImageUrl;
    }

    /**
     * relatedCommodityBを取得します。
     * 
     * @return relatedCommodityB
     */
    public boolean isRelatedCommodityB() {
      return relatedCommodityB;
    }

    /**
     * relatedCommodityBを設定します。
     * 
     * @param relatedCommodityB
     *          relatedCommodityB
     */
    public void setRelatedCommodityB(boolean relatedCommodityB) {
      this.relatedCommodityB = relatedCommodityB;
    }

    /**
     * commodityTaxTypeを取得します。
     * 
     * @return commodityTaxType
     */
    public String getCommodityTaxType() {
      return commodityTaxType;
    }

    /**
     * commodityTaxTypeを設定します。
     * 
     * @param commodityTaxType
     *          commodityTaxType
     */
    public void setCommodityTaxType(String commodityTaxType) {
      this.commodityTaxType = commodityTaxType;
    }

    /**
     * saleStatusを取得します。
     * 
     * @return saleStatus
     */

    public String getSaleStatus() {
      return saleStatus;
    }

    /**
     * saleStatusを設定します。
     * 
     * @param saleStatus
     *          saleStatus
     */
    public void setSaleStatus(String saleStatus) {
      this.saleStatus = saleStatus;
    }

    /**
     * @return the displayJdDescribeButton
     */
    public boolean isDisplayJdDescribeButton() {
      return displayJdDescribeButton;
    }

    /**
     * @param displayJdDescribeButton the displayJdDescribeButton to set
     */
    public void setDisplayJdDescribeButton(boolean displayJdDescribeButton) {
      this.displayJdDescribeButton = displayJdDescribeButton;
    }

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
   * searchStockStatusを取得します。
   * 
   * @return searchStockStatus
   */
  public String[] getSearchStockStatus() {
    return ArrayUtil.immutableCopy(searchStockStatus);
  }

  /**
   * searchStockStatusを設定します。
   * 
   * @param searchStockStatus
   *          searchStockStatus
   */
  public void setSearchStockStatus(String[] searchStockStatus) {
    this.searchStockStatus = ArrayUtil.immutableCopy(searchStockStatus);
  }

  /**
   * 商品一覧を取得します。
   * 
   * @return list
   */
  public List<TmallCommodityListResult> getList() {
    return list;
  }

  /**
   * 商品一覧を設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<TmallCommodityListResult> list) {
    this.list = list;
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
   * searchCommodityCodeStartを取得します。
   * 
   * @return searchCommodityCodeStart
   */
  public String getSearchCommodityCodeStart() {
    return searchCommodityCodeStart;
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
   * searchCommoditySearchWordsを取得します。
   * 
   * @return searchCommoditySearchWords
   */
  public String getSearchCommoditySearchWords() {
    return searchCommoditySearchWords;
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
   * searchSkuCodeを取得します。
   * 
   * @return searchSkuCode
   */
  public String getSearchSkuCode() {
    return searchSkuCode;
  }

  /**
   * searchStockQuantityEndを取得します。
   * 
   * @return searchStockQuantityEnd
   */
  public String getSearchStockQuantityEnd() {
    return searchStockQuantityEnd;
  }

  /**
   * searchStockQuantityStartを取得します。
   * 
   * @return searchStockQuantityStart
   */
  public String getSearchStockQuantityStart() {
    return searchStockQuantityStart;
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
   * searchCommodityCodeStartを設定します。
   * 
   * @param searchCommodityCodeStart
   *          searchCommodityCodeStart
   */
  public void setSearchCommodityCodeStart(String searchCommodityCodeStart) {
    this.searchCommodityCodeStart = searchCommodityCodeStart;
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
   * searchCommoditySearchWordsを設定します。
   * 
   * @param searchCommoditySearchWords
   *          searchCommoditySearchWords
   */
  public void setSearchCommoditySearchWords(String searchCommoditySearchWords) {
    this.searchCommoditySearchWords = searchCommoditySearchWords;
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
   * searchSkuCodeを設定します。
   * 
   * @param searchSkuCode
   *          searchSkuCode
   */
  public void setSearchSkuCode(String searchSkuCode) {
    this.searchSkuCode = searchSkuCode;
  }

  /**
   * searchSaleEndDateRangeFromを取得します。
   * 
   * @return searchSaleEndDateRangeFrom
   */
  public String getSearchSaleEndDateRangeFrom() {
    return searchSaleEndDateRangeFrom;
  }

  /**
   * searchSaleEndDateRangeToを取得します。
   * 
   * @return searchSaleEndDateRangeTo
   */
  public String getSearchSaleEndDateRangeTo() {
    return searchSaleEndDateRangeTo;
  }

  /**
   * searchSaleStartDateRangeFromを取得します。
   * 
   * @return searchSaleStartDateRangeFrom
   */
  public String getSearchSaleStartDateRangeFrom() {
    return searchSaleStartDateRangeFrom;
  }

  /**
   * searchSaleStartDateRangeToを取得します。
   * 
   * @return searchSaleStartDateRangeTo
   */
  public String getSearchSaleStartDateRangeTo() {
    return searchSaleStartDateRangeTo;
  }

  /**
   * searchSaleEndDateRangeFromを設定します。
   * 
   * @param searchSaleEndDateRangeFrom
   *          searchSaleEndDateRangeFrom
   */
  public void setSearchSaleEndDateRangeFrom(String searchSaleEndDateRangeFrom) {
    this.searchSaleEndDateRangeFrom = searchSaleEndDateRangeFrom;
  }

  /**
   * searchSaleEndDateRangeToを設定します。
   * 
   * @param searchSaleEndDateRangeTo
   *          searchSaleEndDateRangeTo
   */
  public void setSearchSaleEndDateRangeTo(String searchSaleEndDateRangeTo) {
    this.searchSaleEndDateRangeTo = searchSaleEndDateRangeTo;
  }

  /**
   * searchSaleStartDateRangeFromを設定します。
   * 
   * @param searchSaleStartDateRangeFrom
   *          searchSaleStartDateRangeFrom
   */
  public void setSearchSaleStartDateRangeFrom(String searchSaleStartDateRangeFrom) {
    this.searchSaleStartDateRangeFrom = searchSaleStartDateRangeFrom;
  }

  /**
   * searchSaleStartDateRangeToを設定します。
   * 
   * @param searchSaleStartDateRangeTo
   *          searchSaleStartDateRangeTo
   */
  public void setSearchSaleStartDateRangeTo(String searchSaleStartDateRangeTo) {
    this.searchSaleStartDateRangeTo = searchSaleStartDateRangeTo;
  }

  /**
   * searchStockQuantityEndを設定します。
   * 
   * @param searchStockQuantityEnd
   *          searchStockQuantityEnd
   */
  public void setSearchStockQuantityEnd(String searchStockQuantityEnd) {
    this.searchStockQuantityEnd = searchStockQuantityEnd;
  }

  /**
   * searchStockQuantityStartを設定します。
   * 
   * @param searchStockQuantityStart
   *          searchStockQuantityStart
   */
  public void setSearchStockQuantityStart(String searchStockQuantityStart) {
    this.searchStockQuantityStart = searchStockQuantityStart;
  }

  /**
   * displayShopListを取得します。
   * 
   * @return displayShopList
   */
  public boolean isDisplayShopList() {
    return displayShopList;
  }

  /**
   * displayShopListを設定します。
   * 
   * @param displayShopList
   *          displayShopList
   */
  public void setDisplayShopList(boolean displayShopList) {
    this.displayShopList = displayShopList;
  }

  /**
   * displayCsvExportButtonを取得します。
   * 
   * @return displayCsvExportButton
   */
  public boolean isDisplayCsvExportButton() {
    return displayCsvExportButton;
  }

  /**
   * displayCsvExportButtonを設定します。
   * 
   * @param displayCsvExportButton
   *          displayCsvExportButton
   */
  public void setDisplayCsvExportButton(boolean displayCsvExportButton) {
    this.displayCsvExportButton = displayCsvExportButton;
  }

  /**
   * searchArrivalGoodsを取得します。
   * 
   * @return searchArrivalGoods
   */
  public String getSearchArrivalGoods() {
    return searchArrivalGoods;
  }

  /**
   * searchArrivalGoodsを設定します。
   * 
   * @param searchArrivalGoods
   *          searchArrivalGoods
   */
  public void setSearchArrivalGoods(String searchArrivalGoods) {
    this.searchArrivalGoods = searchArrivalGoods;
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
   *          pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
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

    if (StringUtil.isNullOrEmpty(reqparam.get("searchDisplayClientPc"))) {
      this.setSearchDisplayClientPc("");
    }
    if (StringUtil.isNullOrEmpty(reqparam.get("searchDisplayClientMobile"))) {
      this.setSearchDisplayClientMobile("");
    }

    if (StringUtil.isNullOrEmpty(reqparam.get("searchSafetyStock"))) {
      this.setSearchSafetyStock("");
    }

    this.setSearchSaleStartDateRangeFrom(reqparam.getDateTimeString("searchSaleStartDateRangeFrom"));
    this.setSearchSaleStartDateRangeTo(reqparam.getDateTimeString("searchSaleStartDateRangeTo"));
    this.setSearchSaleEndDateRangeFrom(reqparam.getDateTimeString("searchSaleEndDateRangeFrom"));
    this.setSearchSaleEndDateRangeTo(reqparam.getDateTimeString("searchSaleEndDateRangeTo"));
    this.setSearchStockStatus(reqparam.getAll("searchStockStatus"));
    this.setSearchSaleStatus(reqparam.getAll("searchSaleStatus"));
    this.setCheckedCommodityList(Arrays.asList(reqparam.getAll("commodityCode")));
    this.setCombination(reqparam.get("combinationType"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040150";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.TmallCommodityListBean.0");
  }

  /**
   * displayShopNameColumnを取得します。
   * 
   * @return displayShopNameColumn
   */
  public boolean isDisplayShopNameColumn() {
    return displayShopNameColumn;
  }

  /**
   * displayShopNameColumnを設定します。
   * 
   * @param displayShopNameColumn
   *          displayShopNameColumn
   */
  public void setDisplayShopNameColumn(boolean displayShopNameColumn) {
    this.displayShopNameColumn = displayShopNameColumn;
  }

  /**
   * advancedSearchModeを取得します。
   * 
   * @return advancedSearchMode
   */
  public String getAdvancedSearchMode() {
    return advancedSearchMode;
  }

  /**
   * advancedSearchModeを設定します。
   * 
   * @param advancedSearchMode
   *          advancedSearchMode
   */
  public void setAdvancedSearchMode(String advancedSearchMode) {
    this.advancedSearchMode = advancedSearchMode;
  }

  /**
   * stockStatusListを取得します。
   * 
   * @return displayStockStatusList
   */
  public List<NameValue> getDisplayStockStatusList() {
    return displayStockStatusList;
  }

  /**
   * stockStatusListを設定します。
   * 
   * @param stockStatusList
   *          商品表示一覧
   */
  public void setDisplayStockStatusList(List<NameValue> stockStatusList) {
    this.displayStockStatusList = stockStatusList;
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
   * displayUpdateButtonを取得します。
   * 
   * @return displayUpdateButton
   */
  public boolean isDisplayUpdateButton() {
    return displayUpdateButton;
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
   * searchStockStatusListを取得します。
   * 
   * @return searchStockStatusList
   */
  public List<CodeAttribute> getSearchStockStatusList() {
    return searchStockStatusList;
  }

  /**
   * searchStockStatusListを設定します。
   * 
   * @param searchStockStatusList
   *          searchStockStatusList
   */
  public void setSearchStockStatusList(List<CodeAttribute> searchStockStatusList) {
    this.searchStockStatusList = searchStockStatusList;
  }

  /**
   * displaySaleStatusListを取得します。
   * 
   * @return displaySaleStatusList
   */

  public CodeAttribute[] getDisplaySaleStatusList() {
    return ArrayUtil.immutableCopy(displaySaleStatusList);
  }

  /**
   * displaySaleStatusListを設定します。
   * 
   * @param displaySaleStatusList
   *          displaySaleStatusList
   */
  public void setDisplaySaleStatusList(CodeAttribute[] displaySaleStatusList) {
    this.displaySaleStatusList = ArrayUtil.immutableCopy(displaySaleStatusList);
  }

  /**
   * displaySaleTypeListを取得します。
   * 
   * @return displaySaleTypeList
   */

  public List<NameValue> getDisplaySaleTypeList() {
    return displaySaleTypeList;
  }

  public List<NameValue> getDisplayCommidityTypeList() {
    return displayCommidityTypeList;
  }

  public void setDisplayCommidityTypeList(List<NameValue> displayCommidityTypeList) {
    this.displayCommidityTypeList = displayCommidityTypeList;
  }

  public List<NameValue> getDisplayMjsTypeList() {
    return displayMjsTypeList;
  }

  public void setDisplayMjsTypeList(List<NameValue> displayMjsTypeList) {
    this.displayMjsTypeList = displayMjsTypeList;
  }
  
  /**
   * @return the combinationType
   */
  public List<NameValue> getCombinationType() {
    return combinationType;
  }

  
  /**
   * @param combinationType the combinationType to set
   */
  public void setCombinationType(List<NameValue> combinationType) {
    this.combinationType = combinationType;
  }

  /**
   * displaySaleTypeListを設定します。
   * 
   * @param displaySaleTypeList
   *          displaySaleTypeList
   */
  public void setDisplaySaleTypeList(List<NameValue> displaySaleTypeList) {
    this.displaySaleTypeList = displaySaleTypeList;
  }

  /**
   * searchSaleStatusを取得します。
   * 
   * @return searchSaleStatus
   */

  public String[] getSearchSaleStatus() {
    return ArrayUtil.immutableCopy(searchSaleStatus);
  }

  /**
   * searchSaleStatusを設定します。
   * 
   * @param searchSaleStatus
   *          searchSaleStatus
   */
  public void setSearchSaleStatus(String[] searchSaleStatus) {
    this.searchSaleStatus = ArrayUtil.immutableCopy(searchSaleStatus);
  }

  /**
   * searchSaleTypeを取得します。
   * 
   * @return searchSaleType
   */

  public String getSearchSaleType() {
    return searchSaleType;
  }

  /**
   * searchSaleTypeを設定します。
   * 
   * @param searchSaleType
   *          searchSaleType
   */
  public void setSearchSaleType(String searchSaleType) {
    this.searchSaleType = searchSaleType;
  }

  public String getSearchCommidityType() {
    return searchCommidityType;
  }

  public void setSearchCommidityType(String searchCommidityType) {
    this.searchCommidityType = searchCommidityType;
  }

  public String getSearchTmallMjsCommodityflg() {
    return searchTmallMjsCommodityflg;
  }

  public void setSearchTmallMjsCommodityflg(String searchTmallMjsCommodityflg) {
    this.searchTmallMjsCommodityflg = searchTmallMjsCommodityflg;
  }

  /**
   * @return the combination
   */
  public String getCombination() {
    return combination;
  }

  
  /**
   * @param combination the combination to set
   */
  public void setCombination(String combination) {
    this.combination = combination;
  }

  /**
   * checkedCommodityListを取得します。
   * 
   * @return checkedCommodityList
   */

  public List<String> getCheckedCommodityList() {
    return checkedCommodityList;
  }

  /**
   * checkedCommodityListを設定します。
   * 
   * @param checkedCommodityList
   *          checkedCommodityList
   */
  public void setCheckedCommodityList(List<String> checkedCommodityList) {
    this.checkedCommodityList = checkedCommodityList;
  }

  /**
   * searchDisplayClientPcを取得します。
   * 
   * @return searchDisplayClientPc
   */
  public String getSearchDisplayClientPc() {
    return searchDisplayClientPc;
  }

  /**
   * searchDisplayClientPcを設定します。
   * 
   * @param searchDisplayClientPc
   *          searchDisplayClientPc
   */
  public void setSearchDisplayClientPc(String searchDisplayClientPc) {
    this.searchDisplayClientPc = searchDisplayClientPc;
  }

  /**
   * searchDisplayClientMobileを取得します。
   * 
   * @return searchDisplayClientMobile
   */
  public String getSearchDisplayClientMobile() {
    return searchDisplayClientMobile;
  }

  /**
   * searchDisplayClientMobileを設定します。
   * 
   * @param searchDisplayClientMobile
   *          searchDisplayClientMobile
   */
  public void setSearchDisplayClientMobile(String searchDisplayClientMobile) {
    this.searchDisplayClientMobile = searchDisplayClientMobile;
  }

  /**
   * searchSafetyStockを取得します。
   * 
   * @return searchSafetyStock
   */
  public String getSearchSafetyStock() {
    return searchSafetyStock;
  }

  /**
   * searchSafetyStockを設定します。
   * 
   * @param searchSafetyStock
   *          searchSafetyStock
   */
  public void setSearchSafetyStock(String searchSafetyStock) {
    this.searchSafetyStock = searchSafetyStock;
  }

}
