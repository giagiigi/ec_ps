package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.JanCode;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040140:商品SKUのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CommoditySkuBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private CommoditySkuDetailBean edit = new CommoditySkuDetailBean();

  private List<CommoditySkuDetailBean> list = new ArrayList<CommoditySkuDetailBean>();

  private String parentCommodityCode;

  private String parentCommodityName;

  private String representSkuCode;

  private String stockManagementType;

  private String shopCode;

  private String standardDetail1Name;

  private String standardDetail2Name;

  private List<CodeAttribute> skuImageList = new ArrayList<CodeAttribute>();
  
  // 20120104 ysy add start  
  /** 是否使用 */
  private List<CodeAttribute> useFlgList = new ArrayList<CodeAttribute>();

  // 20120104 ysy add end

  /** 商品単価 */
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "商品単価")
  private String unitPricePrimaryAll;

  /** 特別価格 */
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "特別価格")
  private String discountPriceAll;

  /** 予約価格 */
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "予約価格")
  private String reservationPriceAll;

  /** 改定価格 */
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "改定価格")
  private String changeUnitPriceAll;

  /** 規格名称リスト */
  private List<CodeAttribute> commodityStandardNameList = new ArrayList<CodeAttribute>();
    
  /** 規格名称選択値 */
  private String commodityStandardNameValue;

  @Length(20)
  @Metadata(name = "規格名称1")
  private String commodityStandard1Name;

  @Length(20)
  @Metadata(name = "規格名称2")
  private String commodityStandard2Name;

  /** 退避用規格名称選択値 */
  private String escapeCommodityStandardNameValue;

  /** 退避用規格名称1 */
  private String escapeCommodityStandard1Name;

  /** 退避用規格名称2 */
  private String escapeCommodityStandard2Name;

  /** 特別価格開始日時 */
  private String discountPriceStartDatetime;

  /** 特別価格終了日時 */
  private String discountPriceEndDatetime;

  /** 予約開始日時 */
  private String reservationStartDatetime;

  /** 予約終了日時 */
  private String reservationEndDatetime;

  /** 価格改定日時 */
  private String salePriceChangeDatetime;

  private boolean priceAllTableDisplayFlg = false;

  private boolean registerRowDisplayFlg = false;

  private boolean uploadTableDisplayFlg = false;

  private boolean selectButtonDisplayFlg = false;

  private boolean updateButtonDisplayFlg = false;

  private boolean moveStockIOLinkDisplayFlg = false;

  private String displayMode = WebConstantCode.DISPLAY_READONLY;

  /**
   * U1040140:商品SKUのデータモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CommoditySkuDetailBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String shopCode;

    private String commodityCode;

    /** SKUコード */
    @Required
    @Length(24)
    @AlphaNum2
    @Metadata(name = "SKUコード")
    private String skuCode;

    /** 商品単価 */
    @Required
    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "商品単価")
    private String unitPrice;

    /** 特別価格 */
    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "特別価格")
    private String discountPrice;

    /** 予約価格 */
    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "予約価格")
    private String reservationPrice;

    /** 改定価格 */
    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "改定価格")
    private String changeUnitPrice;

    /** JANコード */
    @Length(13)
    // modify by V10-CH 170 start
    // @Pattern(value = "([0-9]{8}|[0-9]{13}|)", message =
    // "8桁または13桁の数字を入力してください")
    @JanCode
    // modify by V10-CH 170 end
    @Metadata(name = "JANコード")
    private String janCode;

    /** 規格詳細1名称 */
    @Length(20)
    @Metadata(name = "規格詳細1名称")
    private String standardDetail1Name;

    /** 規格詳細2名称 */
    @Length(20)
    @Metadata(name = "規格詳細2名称")
    private String standardDetail2Name;

    /** 表示順 */
    @Length(8)
    @Digit
    @Range(min = 0, max = 99999999)
    @Metadata(name = "表示順")
    private String displayOrder;

    /** 有効在庫 */
    private Long availableStockQuantity;

    /** 在庫数 */
    private Long stockQuantity;

    @Length(8)
    @Quantity
    // 10.1.6 10281 修正 ここから
    // @Range(min = 0, max = 99999999)
    @Range(min = 1, max = 99999999)
    // 10.1.6 10281 修正 ここまで
    @Metadata(name = "予約上限数")
    private String reservationLimit;

    @Length(8)
    @Quantity
    // 10.1.6 10281 修正 ここから
    // @Range(min = 0, max = 99999999)
    @Range(min = 1, max = 99999999)
    // 10.1.6 10281 修正 ここまで
    @Metadata(name = "注文毎予約上限数")
    private String oneshotReservationLimit;

    @Length(8)
    @Quantity
    @Range(min = 0, max = 99999999)
    @Metadata(name = "安全在庫")
    private String stockThreshold;
    
    // 20120104 ysy add start
    /** 重量  */
    @Required
    @Precision(precision = 8, scale = 3)
    @Metadata(name = "重量")
    private String weight;
    
    @Required
    private String useFlg;
       
    // 20120104 ysy add end
    
    /** PC用商品SKU画像URL */
    private String skuImagePcUrl;

    /** 携帯用商品SKU画像URL */
    private String skuImageMobileUrl;

    private boolean representFlg = false;

    private boolean displayDeleteButton = false;

    private boolean displayImageDeleteButton = false;

    private boolean diaplayMobileImageDeleteButton = false;

    private Date updateDatetime;



	/**
	 * @return the useFlg
	 */
	public String getUseFlg() {
		return useFlg;
	}

	/**
	 * @param useFlg the useFlg to set
	 */
	public void setUseFlg(String useFlg) {
		this.useFlg = useFlg;
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
     * janCodeを取得します。
     * 
     * @return janCode
     */
    public String getJanCode() {
      return janCode;
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
     * shopCodeを取得します。
     * 
     * @return shopCode
     */
    public String getShopCode() {
      return shopCode;
    }

    /**
     * commodityCodeを取得します。
     * 
     * @return the commodityCode
     */
    public String getCommodityCode() {
      return commodityCode;
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
     * standardDetail1Nameを取得します。
     * 
     * @return standardDetail1Name
     */
    public String getStandardDetail1Name() {
      return standardDetail1Name;
    }

    /**
     * standardDetail2Nameを取得します。
     * 
     * @return standardDetail2Name
     */
    public String getStandardDetail2Name() {
      return standardDetail2Name;
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
     * janCodeを設定します。
     * 
     * @param janCode
     *          janCode
     */
    public void setJanCode(String janCode) {
      this.janCode = janCode;
    }

    /**
     * reservedPriceを設定します。
     * 
     * @param reservationPrice
     *          reservationPrice
     */
    public void setReservationPrice(String reservationPrice) {
      this.reservationPrice = reservationPrice;
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
     * commodityCodeを設定します。
     * 
     * @param commodityCode
     *          commodityCode
     */
    public void setCommodityCode(String commodityCode) {
      this.commodityCode = commodityCode;
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
     * standardDetail1Nameを設定します。
     * 
     * @param standardDetail1Name
     *          standardDetail1Name
     */
    public void setStandardDetail1Name(String standardDetail1Name) {
      this.standardDetail1Name = standardDetail1Name;
    }

    /**
     * standardDetail2Nameを設定します。
     * 
     * @param standardDetail2Name
     *          standardDetail2Name
     */
    public void setStandardDetail2Name(String standardDetail2Name) {
      this.standardDetail2Name = standardDetail2Name;
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
     * skuImageUrlを取得します。
     * 
     * @return skuImagePcUrl
     */
    public String getSkuImagePcUrl() {
      return skuImagePcUrl;
    }

    /**
     * skuImageUrlを設定します。
     * 
     * @param skuImageUrl
     *          PC用商品SKU画像URL
     */
    public void setSkuImagePcUrl(String skuImageUrl) {
      this.skuImagePcUrl = skuImageUrl;
    }

    /**
     * representFlgを取得します。
     * 
     * @return representFlg
     */
    public boolean isRepresentFlg() {
      return representFlg;
    }

    /**
     * representFlgを設定します。
     * 
     * @param representFlg
     *          representFlg
     */
    public void setRepresentFlg(boolean representFlg) {
      this.representFlg = representFlg;
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
     * displayOrderを取得します。
     * 
     * @return displayOrder
     */
    public String getDisplayOrder() {
      return displayOrder;
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
     * reservationLimitを取得します。
     * 
     * @return reservationLimit
     */
    public String getReservationLimit() {
      return reservationLimit;
    }

    /**
     * reservationLimitを設定します。
     * 
     * @param reservationLimit
     *          reservationLimit
     */
    public void setReservationLimit(String reservationLimit) {
      this.reservationLimit = reservationLimit;
    }

    /**
     * oneshotReservationLimitを取得します。
     * 
     * @return oneshotReservationLimit
     */
    public String getOneshotReservationLimit() {
      return oneshotReservationLimit;
    }

    /**
     * oneshotReservationLimitを設定します。
     * 
     * @param oneshotReservationLimit
     *          oneshotReservationLimit
     */
    public void setOneshotReservationLimit(String oneshotReservationLimit) {
      this.oneshotReservationLimit = oneshotReservationLimit;
    }

    /**
     * skuImageMobileUrlを取得します。
     * 
     * @return skuImageMobileUrl
     */
    public String getSkuImageMobileUrl() {
      return skuImageMobileUrl;
    }

    /**
     * skuImageMobileUrlを設定します。
     * 
     * @param skuImageMobileUrl
     *          skuImageMobileUrl
     */
    public void setSkuImageMobileUrl(String skuImageMobileUrl) {
      this.skuImageMobileUrl = skuImageMobileUrl;
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
     * diaplayMobileImageDeleteButtonを取得します。
     * 
     * @return diaplayMobileImageDeleteButton
     */
    public boolean isDiaplayMobileImageDeleteButton() {
      return diaplayMobileImageDeleteButton;
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
     * displayImageDeleteButtonを取得します。
     * 
     * @return displayImageDeleteButton
     */
    public boolean isDisplayImageDeleteButton() {
      return displayImageDeleteButton;
    }

    /**
     * diaplayMobileImageDeleteButtonを設定します。
     * 
     * @param diaplayMobileImageDeleteButton
     *          diaplayMobileImageDeleteButton
     */
    public void setDiaplayMobileImageDeleteButton(boolean diaplayMobileImageDeleteButton) {
      this.diaplayMobileImageDeleteButton = diaplayMobileImageDeleteButton;
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
     * displayImageDeleteButtonを設定します。
     * 
     * @param displayImageDeleteButton
     *          displayImageDeleteButton
     */
    public void setDisplayImageDeleteButton(boolean displayImageDeleteButton) {
      this.displayImageDeleteButton = displayImageDeleteButton;
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
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

  }

  /**
   * escapeCommodityStandardNameValueを取得します。
   * 
   * @return escapeCommodityStandardNameValue
   */
  public String getEscapeCommodityStandardNameValue() {
    return escapeCommodityStandardNameValue;
  }

  /**
   * escapeCommodityStandardNameValueを設定します。
   * 
   * @param escapeCommodityStandardNameValue
   *          escapeCommodityStandardNameValue
   */
  public void setEscapeCommodityStandardNameValue(String escapeCommodityStandardNameValue) {
    this.escapeCommodityStandardNameValue = escapeCommodityStandardNameValue;
  }

  /**
   * escapeCommodityStandard1Nameを取得します。
   * 
   * @return escapeCommodityStandard1Name
   */
  public String getEscapeCommodityStandard1Name() {
    return escapeCommodityStandard1Name;
  }

  /**
   * escapeCommodityStandard2Nameを取得します。
   * 
   * @return escapeCommodityStandard2Name
   */
  public String getEscapeCommodityStandard2Name() {
    return escapeCommodityStandard2Name;
  }

  /**
   * escapeCommodityStandard1Nameを設定します。
   * 
   * @param escapeCommodityStandard1Name
   *          escapeCommodityStandard1Name
   */
  public void setEscapeCommodityStandard1Name(String escapeCommodityStandard1Name) {
    this.escapeCommodityStandard1Name = escapeCommodityStandard1Name;
  }

  /**
   * escapeCommodityStandard2Nameを設定します。
   * 
   * @param escapeCommodityStandard2Name
   *          escapeCommodityStandard2Name
   */
  public void setEscapeCommodityStandard2Name(String escapeCommodityStandard2Name) {
    this.escapeCommodityStandard2Name = escapeCommodityStandard2Name;
  }

  /**
   * changeUnitPriceAllを取得します。
   * 
   * @return changeUnitPriceAll
   */
  public String getChangeUnitPriceAll() {
    return changeUnitPriceAll;
  }

  /**
   * discountPriceAllを取得します。
   * 
   * @return discountPriceAll
   */
  public String getDiscountPriceAll() {
    return discountPriceAll;
  }

  /**
   * parentCommodityCodeを取得します。
   * 
   * @return parentCommodityCode
   */
  public String getParentCommodityCode() {
    return parentCommodityCode;
  }

  /**
   * parentCommodityNameを取得します。
   * 
   * @return parentCommodityName
   */
  public String getParentCommodityName() {
    return parentCommodityName;
  }

  /**
   * reservationPriceALlを取得します。
   * 
   * @return reservationPriceALl
   */
  public String getReservationPriceAll() {
    return reservationPriceAll;
  }

  /**
   * unitPricePrimaryAllを取得します。
   * 
   * @return unitPricePrimaryAll
   */
  public String getUnitPricePrimaryAll() {
    return unitPricePrimaryAll;
  }

  /**
   * changeUnitPriceAllを設定します。
   * 
   * @param changeUnitPriceAll
   *          changeUnitPriceAll
   */
  public void setChangeUnitPriceAll(String changeUnitPriceAll) {
    this.changeUnitPriceAll = changeUnitPriceAll;
  }

  /**
   * discountPriceAllを設定します。
   * 
   * @param discountPriceAll
   *          discountPriceAll
   */
  public void setDiscountPriceAll(String discountPriceAll) {
    this.discountPriceAll = discountPriceAll;
  }

  /**
   * parentCommodityCodeを設定します。
   * 
   * @param parentCommodityCode
   *          parentCommodityCode
   */
  public void setParentCommodityCode(String parentCommodityCode) {
    this.parentCommodityCode = parentCommodityCode;
  }

  /**
   * parentCommodityNameを設定します。
   * 
   * @param parentCommodityName
   *          parentCommodityName
   */
  public void setParentCommodityName(String parentCommodityName) {
    this.parentCommodityName = parentCommodityName;
  }

  /**
   * reservedPriceALlを設定します。
   * 
   * @param reservationPriceAll
   *          reservationPriceAll
   */
  public void setReservationPriceAll(String reservationPriceAll) {
    this.reservationPriceAll = reservationPriceAll;
  }

  /**
   * unitPricePrimaryAllを設定します。
   * 
   * @param unitPricePrimaryAll
   *          unitPricePrimaryAll
   */
  public void setUnitPricePrimaryAll(String unitPricePrimaryAll) {
    this.unitPricePrimaryAll = unitPricePrimaryAll;
  }

  /**
   * editを取得します。
   * 
   * @return edit
   */
  public CommoditySkuDetailBean getEdit() {
    return edit;
  }

  /**
   * listを取得します。
   * 
   * @return list
   */
  public List<CommoditySkuDetailBean> getList() {
    return list;
  }

  /**
   * editを設定します。
   * 
   * @param edit
   *          edit
   */
  public void setEdit(CommoditySkuDetailBean edit) {
    this.edit = edit;
  }

  /**
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<CommoditySkuDetailBean> list) {
    this.list = list;
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
    this.setUnitPricePrimaryAll(reqparam.get("unitPricePrimaryAll"));
    this.setReservationPriceAll(reqparam.get("reservationPriceAll"));
    this.setDiscountPriceAll(reqparam.get("discountPriceAll"));
    this.setChangeUnitPriceAll(reqparam.get("changeUnitPriceAll"));
    this.setCommodityStandardNameValue(reqparam.get("commodityStandardNameValue"));
    this.setCommodityStandard1Name(reqparam.get("commodityStandard1Name"));
    this.setCommodityStandard2Name(reqparam.get("commodityStandard2Name"));

    String[] listKey = new String[] {};
    if (StringUtil.hasValue(this.getEscapeCommodityStandardNameValue())) {
      if (this.getEscapeCommodityStandardNameValue().equals("0")) {
        listKey = new String[] {
            "skuCode", "unitPrice", "discountPrice", "reservationPrice", "changeUnitPrice", "janCode", "displayOrder",
            "reservationLimit", "oneshotReservationLimit", "stockThreshold", "weight","useFlg"
        };
      } else if (this.getEscapeCommodityStandardNameValue().equals("1")) {
        listKey = new String[] {
            "skuCode", "unitPrice", "discountPrice", "reservationPrice", "changeUnitPrice", "janCode", "standardDetail1Name",
            "displayOrder", "reservationLimit", "oneshotReservationLimit", "stockThreshold","weight","useFlg"
        };
      } else if (this.getEscapeCommodityStandardNameValue().equals("2")) {
        listKey = new String[] {
            "skuCode", "unitPrice", "discountPrice", "reservationPrice", "changeUnitPrice", "janCode", "standardDetail1Name",
            "standardDetail2Name", "displayOrder", "reservationLimit", "oneshotReservationLimit", "stockThreshold","weight","useFlg"
        };
      }
    }

    for (CommoditySkuDetailBean sku : list) {
      Map<String, String> map = reqparam.getListDataWithKey(sku.getSkuCode(), listKey);
      sku.setSkuCode(map.get("skuCode"));
      sku.setUnitPrice(map.get("unitPrice"));
      sku.setDiscountPrice(map.get("discountPrice"));
      sku.setReservationPrice(map.get("reservationPrice"));
      sku.setChangeUnitPrice(map.get("changeUnitPrice"));
      sku.setJanCode(map.get("janCode"));
      sku.setStandardDetail1Name(map.get("standardDetail1Name"));
      sku.setStandardDetail2Name(map.get("standardDetail2Name"));
      sku.setDisplayOrder(map.get("displayOrder"));
      sku.setReservationLimit(map.get("reservationLimit"));
      sku.setOneshotReservationLimit(map.get("oneshotReservationLimit"));
      sku.setStockThreshold(map.get("stockThreshold"));
      // 20120104 ysy add start
      sku.setWeight(map.get("weight"));
      sku.setUseFlg(map.get("useFlg"));
      // 20120104 ysy add end
      
    }

    edit.setSkuCode(reqparam.get("registerSkuCode"));
    edit.setUnitPrice(reqparam.get("registerUnitPrice"));
    edit.setDiscountPrice(reqparam.get("registerDiscountPrice"));
    edit.setReservationPrice(reqparam.get("registerReservationPrice"));
    edit.setChangeUnitPrice(reqparam.get("registerChangeUnitPrice"));
    edit.setJanCode(reqparam.get("registerJanCode"));
    edit.setStandardDetail1Name(reqparam.get("registerStandardDetail1Name"));
    edit.setStandardDetail2Name(reqparam.get("registerStandardDetail2Name"));
    edit.setDisplayOrder(reqparam.get("registerDisplayOrder"));
    edit.setReservationLimit(reqparam.get("registerReservationLimit"));
    edit.setOneshotReservationLimit(reqparam.get("registerOneshotReservationLimit"));
    edit.setStockThreshold(reqparam.get("registerStockThreshold"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040140";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.CommoditySkuBean.0");
  }

  /**
   * priceAllTableDisplayFlgを取得します。
   * 
   * @return priceAllTableDisplayFlg
   */
  public boolean isPriceAllTableDisplayFlg() {
    return priceAllTableDisplayFlg;
  }

  /**
   * uploadTableDisplayFlgを取得します。
   * 
   * @return uploadTableDisplayFlg
   */
  public boolean isUploadTableDisplayFlg() {
    return uploadTableDisplayFlg;
  }

  /**
   * priceAllTableDisplayFlgを設定します。
   * 
   * @param priceAllTableDisplayFlg
   *          priceAllTableDisplayFlg
   */
  public void setPriceAllTableDisplayFlg(boolean priceAllTableDisplayFlg) {
    this.priceAllTableDisplayFlg = priceAllTableDisplayFlg;
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
   *          updateButtonDisplayFlg
   */
  public void setUpdateButtonDisplayFlg(boolean updateButtonDisplayFlg) {
    this.updateButtonDisplayFlg = updateButtonDisplayFlg;
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
   * representSkuCodeを設定します。
   * 
   * @param representSkuCode
   *          representSkuCode
   */
  public void setRepresentSkuCode(String representSkuCode) {
    this.representSkuCode = representSkuCode;
  }

  /**
   * selectButtonDisplayFlgを取得します。
   * 
   * @return selectButtonDisplayFlg
   */
  public boolean isSelectButtonDisplayFlg() {
    return selectButtonDisplayFlg;
  }

  /**
   * selectButtonDisplayFlgを設定します。
   * 
   * @param selectButtonDisplayFlg
   *          selectButtonDisplayFlg
   */
  public void setSelectButtonDisplayFlg(boolean selectButtonDisplayFlg) {
    this.selectButtonDisplayFlg = selectButtonDisplayFlg;
  }

  /**
   * registerRowDisplayFlgを取得します。
   * 
   * @return registerRowDisplayFlg
   */
  public boolean isRegisterRowDisplayFlg() {
    return registerRowDisplayFlg;
  }

  /**
   * registerRowDisplayFlgを設定します。
   * 
   * @param registerRowDisplayFlg
   *          registerRowDisplayFlg
   */
  public void setRegisterRowDisplayFlg(boolean registerRowDisplayFlg) {
    this.registerRowDisplayFlg = registerRowDisplayFlg;
  }

  /**
   * discountPriceEndDatetimeを設定します。
   * 
   * @param endDatetime
   *          特別価格終了日時
   */
  public void setDiscountPriceEndDatetime(String endDatetime) {
    this.discountPriceEndDatetime = endDatetime;
  }

  /**
   * discountPriceStartDatetimeを設定します。
   * 
   * @param discountPriceStartDatetime
   *          discountPriceStartDatetime
   */
  public void setDiscountPriceStartDatetime(String discountPriceStartDatetime) {
    this.discountPriceStartDatetime = discountPriceStartDatetime;
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
   * salePriceChangeDatetimeを設定します。
   * 
   * @param salePriceChangeDatetime
   *          salePriceChangeDatetime
   */
  public void setSalePriceChangeDatetime(String salePriceChangeDatetime) {
    this.salePriceChangeDatetime = salePriceChangeDatetime;
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
   * salePriceChangeDatetimeを取得します。
   * 
   * @return salePriceChangeDatetime
   */
  public String getSalePriceChangeDatetime() {
    return salePriceChangeDatetime;
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
   * displayModeを取得します。
   * 
   * @return displayMode
   */
  public String getDisplayMode() {
    return displayMode;
  }

  /**
   * displayModeを設定します。
   * 
   * @param displayMode
   *          displayMode
   */
  public void setDisplayMode(String displayMode) {
    this.displayMode = displayMode;
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
   * standardDetail1Nameを取得します。
   * 
   * @return standardDetail1Name
   */
  public String getStandardDetail1Name() {
    return standardDetail1Name;
  }

  /**
   * standardDetail1Nameを設定します。
   * 
   * @param standardDetail1Name
   *          standardDetail1Name
   */
  public void setStandardDetail1Name(String standardDetail1Name) {
    this.standardDetail1Name = standardDetail1Name;
  }

  /**
   * standardDetail2Nameを取得します。
   * 
   * @return standardDetail2Name
   */
  public String getStandardDetail2Name() {
    return standardDetail2Name;
  }

  /**
   * standardDetail2Nameを設定します。
   * 
   * @param standardDetail2Name
   *          standardDetail2Name
   */
  public void setStandardDetail2Name(String standardDetail2Name) {
    this.standardDetail2Name = standardDetail2Name;
  }

  /**
   * moveStockIOLinkDisplayFlgを取得します。
   * 
   * @return moveStockIOLinkDisplayFlg
   */
  public boolean isMoveStockIOLinkDisplayFlg() {
    return moveStockIOLinkDisplayFlg;
  }

  /**
   * moveStockIOLinkDisplayFlgを設定します。
   * 
   * @param moveStockIOLinkDisplayFlg
   *          moveStockIOLinkDisplayFlg
   */
  public void setMoveStockIOLinkDisplayFlg(boolean moveStockIOLinkDisplayFlg) {
    this.moveStockIOLinkDisplayFlg = moveStockIOLinkDisplayFlg;
  }

  /**
   * commodityStandard1Nameを取得します。
   * 
   * @return commodityStandard1Name
   */
  public String getCommodityStandard1Name() {
    return commodityStandard1Name;
  }

  /**
   * commodityStandard2Nameを取得します。
   * 
   * @return commodityStandard2Name
   */
  public String getCommodityStandard2Name() {
    return commodityStandard2Name;
  }

  /**
   * commodityStandard1Nameを設定します。
   * 
   * @param commodityStandard1Name
   *          commodityStandard1Name
   */
  public void setCommodityStandard1Name(String commodityStandard1Name) {
    this.commodityStandard1Name = commodityStandard1Name;
  }

  /**
   * commodityStandard2Nameを設定します。
   * 
   * @param commodityStandard2Name
   *          commodityStandard2Name
   */
  public void setCommodityStandard2Name(String commodityStandard2Name) {
    this.commodityStandard2Name = commodityStandard2Name;
  }

  /**
   * commodityStandardNameListを取得します。
   * 
   * @return commodityStandardNameList
   */
  public List<CodeAttribute> getCommodityStandardNameList() {
    return commodityStandardNameList;
  }

  /**
   * commodityStandardNameValueを取得します。
   * 
   * @return commodityStandardNameValue
   */
  public String getCommodityStandardNameValue() {
    return commodityStandardNameValue;
  }

  /**
   * commodityStandardNameListを設定します。
   * 
   * @param commodityStandardNameList
   *          commodityStandardNameList
   */
  public void setCommodityStandardNameList(List<CodeAttribute> commodityStandardNameList) {
    this.commodityStandardNameList = commodityStandardNameList;
  }

  /**
   * commodityStandardNameValueを設定します。
   * 
   * @param commodityStandardNameValue
   *          commodityStandardNameValue
   */
  public void setCommodityStandardNameValue(String commodityStandardNameValue) {
    this.commodityStandardNameValue = commodityStandardNameValue;
  }

  /**
   * skuImageListを取得します。
   * 
   * @return skuImageList
   */
  public List<CodeAttribute> getSkuImageList() {
    return skuImageList;
  }

  /**
   * skuImageListを設定します。
   * 
   * @param skuImageList
   *          skuImageList
   */
  public void setSkuImageList(List<CodeAttribute> skuImageList) {
    this.skuImageList = skuImageList;
  }

/**
 * @return the useFlgList
 */
public List<CodeAttribute> getUseFlgList() {
	return useFlgList;
}

/**
 * @param useFlgList the useFlgList to set
 */
public void setUseFlgList(List<CodeAttribute> useFlgList) {
	this.useFlgList = useFlgList;
}

}
