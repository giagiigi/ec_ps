package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040510:入出庫管理のデータモデルです。
 *
 * @author System Integrator Corp.
 */
public class StockIOBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private StockIODetailBean edit = new StockIODetailBean();

  private List<StockIODetailBean> list = new ArrayList<StockIODetailBean>();

  /** 検索用ショップコード */
  private String searchShopCode;

  @Metadata(name = "SKUコード")
  @Length(24)
  @Required
  @AlphaNum2
  private String searchSkuCode;

  @Metadata(name = "入出庫区分")
  private List<String> searchStockIOType = new ArrayList<String>();

  /** 検索用入出庫登録日開始 */
  private String searchStockIODateStart;

  /** 旧検索用入出庫登録日開始 */
  private String oldSearchStockIODateStart;

  /** 検索用入出庫登録日終了 */
  private String searchStockIODateEnd;

  /** 旧検索用入出庫登録日終了 */
  private String oldSearchStockIODateEnd;

  private String commodityName;

  private String stockManagementType;

  private String stockManagenentTypeName;

  /** 在庫数 */
  private Long stockQuantity;

  /** 引当数 */
  private Long allocatedQuantity;

  /** 予約数 */
  private Long reservedQuantity;

  /** 有効在庫数 */
  private Long availableStockQuantity;

  /** 予約上限数 */
  private Long reservationLimit;

  /** 注文毎予約上限数 */
  private Long oneshotReservationLimit;

  /** 安全在庫 */
  private Long stockThreshold;

  /** 取込ファイル */
  private String fileUrl;

  /** 登録テーブル表示フラグ */
  private boolean registerTableDisplayFlg = false;

  /** 検索テーブル表示フラグ */
  private boolean searchTableDisplayFlg = false;

  /** CSV一括取込テーブル表示フラグ */
  private boolean uploadTableDisplayFlg = false;

  /** ショップリスト */
  private List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> stockOperationList;

  private List<NameValue> csvHeaderType = NameValue.asList("true:"
      + Messages.getString("web.bean.back.catalog.StockIOBean.1")
      + "/false:"
      + Messages.getString("web.bean.back.catalog.StockIOBean.2"));

  /** ページャ */
  private PagerValue pagerValue;

  /**
   * U1040510:入出庫管理のサブモデルです。
   *
   * @author System Integrator Corp.
   */
  public static class StockIODetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 入出庫日 */
    @Datetime(format = "yyyy/MM/dd")
    @Metadata(name = "入出庫日")
    private String stockIODate;

    /** SKUコード */
    @Required
    @Length(24)
    @AlphaNum2
    @Metadata(name = "SKUコード")
    private String skuCode;

    /** 商品名 */
    private String commodityName;

    /** 入出庫区分 */
    @Required
    @Length(1)
    @Metadata(name = "入出庫区分")
    private String stockIOType;

    /** 入出庫事由 */
    @Length(200)
    @Metadata(name = "入出庫事由")
    private String memo;

    /** 入出庫数量 */
    @Required
    @Length(8)
    @Quantity
    @Metadata(name = "入出庫数")
    private String stockIOQuantity;

    /**
     * commodityNameを取得します。
     *
     * @return commodityName
     */
    public String getCommodityName() {
      return commodityName;
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
     * skuCodeを取得します。
     *
     * @return skuCode
     */
    public String getSkuCode() {
      return skuCode;
    }

    /**
     * stockIODateを取得します。
     *
     * @return stockIODate
     */
    public String getStockIODate() {
      return stockIODate;
    }

    /**
     * stockIOQuantityを取得します。
     *
     * @return stockIOQuantity
     */
    public String getStockIOQuantity() {
      return stockIOQuantity;
    }

    /**
     * stockIOTypeを取得します。
     *
     * @return stockIOType
     */
    public String getStockIOType() {
      return stockIOType;
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
     * memoを設定します。
     *
     * @param memo
     *          memo
     */
    public void setMemo(String memo) {
      this.memo = memo;
    }

    /**
     * skuCodeを設定します。
     *
     * @param skuCode
     *          skuCode
     */
    public void setSkuCode(String skuCode) {
      this.skuCode = skuCode;
    }

    /**
     * stockIODateを設定します。
     *
     * @param stockIODate
     *          stockIODate
     */
    public void setStockIODate(String stockIODate) {
      this.stockIODate = stockIODate;
    }

    /**
     * stockIOQuantityを設定します。
     *
     * @param stockIOQuantity
     *          stockIOQuantity
     */
    public void setStockIOQuantity(String stockIOQuantity) {
      this.stockIOQuantity = stockIOQuantity;
    }

    /**
     * stockIOTypeを設定します。
     *
     * @param stockIOType
     *          stockIOType
     */
    public void setStockIOType(String stockIOType) {
      this.stockIOType = stockIOType;
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
   * searchStockIODateEndを取得します。
   *
   * @return searchStockIODateEnd
   */
  public String getSearchStockIODateEnd() {
    return searchStockIODateEnd;
  }

  /**
   * searchStockIODateStartを取得します。
   *
   * @return searchStockIODateStart
   */
  public String getSearchStockIODateStart() {
    return searchStockIODateStart;
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
   * searchStockIODateEndを設定します。
   *
   * @param searchStockIODateEnd
   *          searchStockIODateEnd
   */
  public void setSearchStockIODateEnd(String searchStockIODateEnd) {
    this.searchStockIODateEnd = searchStockIODateEnd;
  }

  /**
   * searchStockIODateStartを設定します。
   *
   * @param searchStockIODateStart
   *          searchStockIODateStart
   */
  public void setSearchStockIODateStart(String searchStockIODateStart) {
    this.searchStockIODateStart = searchStockIODateStart;
  }

  /**
   * StockIODetailBeanを取得します。
   *
   * @return StockIODetailBean
   */
  public StockIODetailBean getEdit() {
    return edit;
  }

  /**
   * Listを取得します。
   *
   * @return List
   */
  public List<StockIODetailBean> getList() {
    return list;
  }

  /**
   * StockShipEntryDetailBeanを設定します。
   *
   * @param edit
   *          入出庫情報
   */
  public void setEdit(StockIODetailBean edit) {
    this.edit = edit;
  }

  /**
   * Listを設定します。
   *
   * @param list
   *          入出庫情報一覧
   */
  public void setList(List<StockIODetailBean> list) {
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
   * @return pagerValue pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
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
    commodityName = "";
    searchShopCode = reqparam.get("searchShopCode");
    searchSkuCode = reqparam.get("searchSkuCode");
    searchStockIODateStart = reqparam.getDateString("searchStockIODateStart");
    searchStockIODateEnd = reqparam.getDateString("searchStockIODateEnd");
    setSearchStockIOType(Arrays.asList(reqparam.getAll("searchStockIOType")));

    edit.setSkuCode(searchSkuCode);
    edit.setStockIOType(reqparam.get("stockIOType"));
    edit.setStockIOQuantity(reqparam.get("stockIOQuantity"));
    edit.setMemo(reqparam.get("memo"));
    list = new ArrayList<StockIODetailBean>();
    pagerValue = null;

  }

  /**
   * モジュールIDを取得します。
   *
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040510";
  }

  /**
   * モジュール名を取得します。
   *
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.StockIOBean.0");
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
   * searchSkuCodeを取得します。
   *
   * @return searchSkuCode
   */
  public String getSearchSkuCode() {
    return searchSkuCode;
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
   * @param registerTableDisplayFlg
   *          registerTableDisplayFlg
   */
  public void setRegisterTableDisplayFlg(boolean registerTableDisplayFlg) {
    this.registerTableDisplayFlg = registerTableDisplayFlg;
  }

  /**
   * searchTableDisplayFlgを取得します。
   *
   * @return searchTableDisplayFlg searchTableDisplayFlg
   */
  public boolean isSearchTableDisplayFlg() {
    return searchTableDisplayFlg;
  }

  /**
   * searchTableDisplayFlgを設定します。
   *
   * @param searchTableDisplayFlg
   *          searchTableDisplayFlg
   */
  public void setSearchTableDisplayFlg(boolean searchTableDisplayFlg) {
    this.searchTableDisplayFlg = searchTableDisplayFlg;
  }

  /**
   * searchStockIOTypeを設定します。
   *
   * @param searchStockIOType
   *          searchStockIOType
   */
  public void setSearchStockIOType(List<String> searchStockIOType) {
    this.searchStockIOType = searchStockIOType;
  }

  /**
   * searchStockIOTypeを取得します。
   *
   * @return searchStockIOType
   */
  public List<String> getSearchStockIOType() {
    return searchStockIOType;
  }

  /**
   * uploadTableDisplayFlgを取得します。
   *
   * @return the uploadTableDisplayFlg
   */
  public boolean isUploadTableDisplayFlg() {
    return uploadTableDisplayFlg;
  }

  /**
   * uploadTableDisplayFlgを設定します。
   *
   * @param uploadTableDisplayFlg
   *          uploadTableDisplayFlg
   */
  public void setUploadTableDisplayFlg(boolean uploadTableDisplayFlg) {
    this.uploadTableDisplayFlg = uploadTableDisplayFlg;
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
   * allocatedQuantityを取得します。
   *
   * @return allocatedQuantity
   */
  public Long getAllocatedQuantity() {
    return allocatedQuantity;
  }

  /**
   * allocatedQuantityを設定します。
   *
   * @param allocatedQuantity
   *          allocatedQuantity
   */
  public void setAllocatedQuantity(Long allocatedQuantity) {
    this.allocatedQuantity = allocatedQuantity;
  }

  /**
   * availableStockQuantityを取得します。
   *
   * @return availableStockQuantity
   */
  public Long getAvailableStockQuantity() {
    return availableStockQuantity;
  }

  /**
   * availableStockQuantityを設定します。
   *
   * @param availableStockQuantity
   *          availableStockQuantity
   */
  public void setAvailableStockQuantity(Long availableStockQuantity) {
    this.availableStockQuantity = availableStockQuantity;
  }

  /**
   * oneshotReservationLimitを取得します。
   *
   * @return oneshotReservationLimit
   */
  public Long getOneshotReservationLimit() {
    return oneshotReservationLimit;
  }

  /**
   * oneshotReservationLimitを設定します。
   *
   * @param oneshotReservationLimit
   *          oneshotReservationLimit
   */
  public void setOneshotReservationLimit(Long oneshotReservationLimit) {
    this.oneshotReservationLimit = oneshotReservationLimit;
  }

  /**
   * reservationLimitを取得します。
   *
   * @return reservationLimit
   */
  public Long getReservationLimit() {
    return reservationLimit;
  }

  /**
   * reservationLimitを設定します。
   *
   * @param reservationLimit
   *          reservationLimit
   */
  public void setReservationLimit(Long reservationLimit) {
    this.reservationLimit = reservationLimit;
  }

  /**
   * reservedQuantityを取得します。
   *
   * @return reservedQuantity
   */
  public Long getReservedQuantity() {
    return reservedQuantity;
  }

  /**
   * reservedQuantityを設定します。
   *
   * @param reservedQuantity
   *          reservedQuantity
   */
  public void setReservedQuantity(Long reservedQuantity) {
    this.reservedQuantity = reservedQuantity;
  }

  /**
   * stockQuantityを取得します。
   *
   * @return stockQuantity
   */
  public Long getStockQuantity() {
    return stockQuantity;
  }

  /**
   * stockQuantityを設定します。
   *
   * @param stockQuantity
   *          stockQuantity
   */
  public void setStockQuantity(Long stockQuantity) {
    this.stockQuantity = stockQuantity;
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
   * stockOperationListを取得します。
   *
   * @return stockOperationList
   */
  public List<CodeAttribute> getStockOperationList() {
    return stockOperationList;
  }

  /**
   * stockOperationListを設定します。
   *
   * @param stockOperationList
   *          stockOperationList
   */
  public void setStockOperationList(List<CodeAttribute> stockOperationList) {
    this.stockOperationList = stockOperationList;
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
   * stockManagementTypeを設定します。
   *
   * @param stockManagementType
   *          stockManagementType
   */
  public void setStockManagementType(String stockManagementType) {
    this.stockManagementType = stockManagementType;
  }

  /**
   * stockManagenentTypeNameを取得します。
   *
   * @return stockManagenentTypeName
   */

  public String getStockManagenentTypeName() {
    return stockManagenentTypeName;
  }

  /**
   * stockManagenentTypeNameを設定します。
   *
   * @param stockManagenentTypeName
   *          stockManagenentTypeName
   */
  public void setStockManagenentTypeName(String stockManagenentTypeName) {
    this.stockManagenentTypeName = stockManagenentTypeName;
  }

  /**
   * stockThresholdを取得します。
   *
   * @return stockThreshold
   */
  public Long getStockThreshold() {
    return stockThreshold;
  }

  /**
   * stockThresholdを設定します。
   *
   * @param stockThreshold
   *          stockThreshold
   */
  public void setStockThreshold(Long stockThreshold) {
    this.stockThreshold = stockThreshold;
  }

  /**
   * oldSearchStockIODateStartを取得します。
   *
   * @return oldSearchStockIODateStart
   */
  public String getOldSearchStockIODateStart() {
    return oldSearchStockIODateStart;
  }

  /**
   * oldSearchStockIODateStartを設定します。
   *
   * @param oldSearchStockIODateStart
   *          oldSearchStockIODateStart
   */
  public void setOldSearchStockIODateStart(String oldSearchStockIODateStart) {
    this.oldSearchStockIODateStart = oldSearchStockIODateStart;
  }

  /**
   * oldSearchStockIODateEndを取得します。
   *
   * @return oldSearchStockIODateEnd
   */
  public String getOldSearchStockIODateEnd() {
    return oldSearchStockIODateEnd;
  }

  /**
   * oldSearchStockIODateEndを設定します。
   *
   * @param oldSearchStockIODateEnd
   *          oldSearchStockIODateEnd
   */
  public void setOldSearchStockIODateEnd(String oldSearchStockIODateEnd) {
    this.oldSearchStockIODateEnd = oldSearchStockIODateEnd;
  }
}
