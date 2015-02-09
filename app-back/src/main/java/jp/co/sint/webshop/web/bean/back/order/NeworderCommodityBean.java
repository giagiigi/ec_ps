package jp.co.sint.webshop.web.bean.back.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.CompositionItem;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1020110:新規受注(商品選択)のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCommodityBean extends UIBackBean implements UISearchBean {

  private PagerValue pagerValue = new PagerValue();

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** カテゴリコード */
  @Length(16)
  @AlphaNum2
  @Metadata(name = "カテゴリ", order = 1)
  private String searchCategoryCode;

  private String searchCategoryList;

  private List<CodeAttribute> shopList;

  /** ショップコード */
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 2)
  private String searchShopCode;

  /** SKUコード */
  @Length(24)
  @AlphaNum2
  @Metadata(name = "SKUコード", order = 3)
  private String searchSkuCode;

  /** 商品名 */
  @Length(50)
  @Metadata(name = "商品名", order = 4)
  private String searchCommodityName;

  private List<CommodityListDetailBean> commodityList = new ArrayList<CommodityListDetailBean>();

  private InputCartBean inputCartList = new InputCartBean();

  private List<CartCommodityDetailListBean> cartCommodityList = new ArrayList<CartCommodityDetailListBean>();
  
  // 10.1.2 10089 追加 ここから
  private List<CategoryInfo> categoryNodeInfoList;
  // 10.1.2 10089 追加 ここまで

  //2012/11/20 促销对应 新建订单_商品选择  ob add start
  /** 套餐bean*/
  private NeworderCommodityCompositionBean commodityCompositionBean = new NeworderCommodityCompositionBean();
  /** 赠品bean*/
  private NeworderCommodityGiftBean giftCommodityBean = new NeworderCommodityGiftBean();
  /** 套餐区分*/
  private boolean commodityComposition = false;
  /** 可领取商品显示区分*/
  private boolean giftCommodityDisplayFlg = false;
  /** 购物车other赠品list*/
  private List<CartCommodityDetailListBean> cartOtherGiftCommodityList = new ArrayList<CartCommodityDetailListBean>();

  private List<Sku> selectedCompositionList = new ArrayList<Sku>();

  /** 多关联赠品list*/
  private List<String> multipleGiftListAll = new ArrayList<String>();
  private Hashtable<String, CampaignMain> campaignHashTable = new Hashtable<String, CampaignMain>();
  //2012/11/20 促销对应 新建订单_商品选择  ob add end

  /**
   * U1020110:新規受注(商品選択)のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CommodityListDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String shopCode;

    private String shopName;

    private String skuCode;

    private String commodityName;

    private String standardName1;

    private String standardName2;

    private String commodityCode;

    private boolean discountFlg;

    private BigDecimal unitPrice;

    private BigDecimal retailPrice;

    private String discountLabel;

    private String discountPriceStartDatetime;

    private String discountPriceEndDatetime;

    private String stockManagementType;

    private Long stockQuantity;

    private String campainCode;

    private String campainName;

    private String campaignDiscountRate;

    private Long taxType;

    private Long taxRate;

    private boolean reserve;

    private boolean displayCartButton;

		/**
     * displayCartButtonを取得します。
     * 
     * @return displayCartButton
     */
    public boolean isDisplayCartButton() {
      return displayCartButton;
    }

    /**
     * displayCartButtonを設定します。
     * 
     * @param displayCartButton
     *          displayCartButton
     */
    public void setDisplayCartButton(boolean displayCartButton) {
      this.displayCartButton = displayCartButton;
    }

    /**
     * reserveを取得します。
     * 
     * @return reserve
     */
    public boolean isReserve() {
      return reserve;
    }

    /**
     * reserveを設定します。
     * 
     * @param reserve
     *          reserve
     */
    public void setReserve(boolean reserve) {
      this.reserve = reserve;
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
     * discountLabelを取得します。
     * 
     * @return discountLabel
     */
    public String getDiscountLabel() {
      return discountLabel;
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
     * skuCodeを取得します。
     * 
     * @return skuCode
     */
    public String getSkuCode() {
      return skuCode;
    }

    /**
     * standardName1を取得します。
     * 
     * @return standardName1
     */
    public String getStandardName1() {
      return standardName1;
    }

    /**
     * standardName2を取得します。
     * 
     * @return standardName2
     */
    public String getStandardName2() {
      return standardName2;
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
     * @param discountPriceStartDatetime
     *          discountPriceStartDatetime
     */
    public void setDiscountPriceStartDatetime(String discountPriceStartDatetime) {
      this.discountPriceStartDatetime = discountPriceStartDatetime;
    }

    /**
     * discountLabelを設定します。
     * 
     * @param discountLabel
     *          discountLabel
     */
    public void setDiscountLabel(String discountLabel) {
      this.discountLabel = discountLabel;
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
     * skuCodeを設定します。
     * 
     * @param skuCode
     *          skuCode
     */
    public void setSkuCode(String skuCode) {
      this.skuCode = skuCode;
    }

    /**
     * standardName1を設定します。
     * 
     * @param standardName1
     *          standardName1
     */
    public void setStandardName1(String standardName1) {
      this.standardName1 = standardName1;
    }

    /**
     * standardName2を設定します。
     * 
     * @param standardName2
     *          standardName2
     */
    public void setStandardName2(String standardName2) {
      this.standardName2 = standardName2;
    }

    /**
     * unitPriceを取得します。
     * 
     * @return unitPrice
     */
    public BigDecimal getUnitPrice() {
      return unitPrice;
    }

    /**
     * unitPriceを設定します。
     * 
     * @param unitPrice
     *          unitPrice
     */
    public void setUnitPrice(BigDecimal unitPrice) {
      this.unitPrice = unitPrice;
    }

    /**
     * retailPriceを取得します。
     * 
     * @return retailPrice
     */
    public BigDecimal getRetailPrice() {
      return retailPrice;
    }

    /**
     * retailPriceを設定します。
     * 
     * @param retailPrice
     *          retailPrice
     */
    public void setRetailPrice(BigDecimal retailPrice) {
      this.retailPrice = retailPrice;
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
     * campainCodeを取得します。
     * 
     * @return campainCode
     */
    public String getCampainCode() {
      return campainCode;
    }

    /**
     * campainCodeを設定します。
     * 
     * @param campainCode
     *          campainCode
     */
    public void setCampainCode(String campainCode) {
      this.campainCode = campainCode;
    }

    /**
     * taxRateを取得します。
     * 
     * @return taxRate
     */
    public Long getTaxRate() {
      return taxRate;
    }

    /**
     * taxRateを設定します。
     * 
     * @param taxRate
     *          taxRate
     */
    public void setTaxRate(Long taxRate) {
      this.taxRate = taxRate;
    }

    /**
     * taxTypeを取得します。
     * 
     * @return taxType
     */
    public Long getTaxType() {
      return taxType;
    }

    /**
     * taxTypeを設定します。
     * 
     * @param taxType
     *          taxType
     */
    public void setTaxType(Long taxType) {
      this.taxType = taxType;
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
     * discountFlgを取得します。
     * 
     * @return discountFlg
     */
    public boolean isDiscountFlg() {
      return discountFlg;
    }

    /**
     * discountFlgを設定します。
     * 
     * @param discountFlg
     *          discountFlg
     */
    public void setDiscountFlg(boolean discountFlg) {
      this.discountFlg = discountFlg;
    }

    /**
     * campainNameを取得します。
     * 
     * @return campainName
     */
    public String getCampainName() {
      return campainName;
    }

    /**
     * campainNameを設定します。
     * 
     * @param campainName
     *          campainName
     */
    public void setCampainName(String campainName) {
      this.campainName = campainName;
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

  }


  /**
   * U1020110:新規受注(商品選択)のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class InputCartBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** ショップコード */
    @Required
    @Length(16)
    @AlphaNum2
    @Metadata(name = "ショップコード", order = 1)
    private String inputShopCode;

    /** SKUコード */
    @Required
    @Length(24)
    @AlphaNum2
    @Metadata(name = "SKUコード", order = 2)
    private String inputSkuCode;

    /**
     * inputShopCodeを取得します。
     * 
     * @return inputShopCode
     */
    public String getInputShopCode() {
      return inputShopCode;
    }

    /**
     * inputSkuCodeを取得します。
     * 
     * @return inputSkuCode
     */
    public String getInputSkuCode() {
      return inputSkuCode;
    }

    /**
     * inputShopCodeを設定します。
     * 
     * @param inputShopCode
     *          inputShopCode
     */
    public void setInputShopCode(String inputShopCode) {
      this.inputShopCode = inputShopCode;
    }

    /**
     * inputSkuCodeを設定します。
     * 
     * @param inputSkuCode
     *          inputSkuCode
     */
    public void setInputSkuCode(String inputSkuCode) {
      this.inputSkuCode = inputSkuCode;
    }
  }

  /**
   * U1020110:新規受注(商品選択)のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CartCommodityDetailListBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String cartKey;

    private String shopCode;

    private String shopName;

    private String commodityCode;

    private String skuCode;

    private String controlSkuCode;

    private String commodityName;

    private String standard1Name;

    private String standard2Name;

    private String retailPrice;

    private String taxRate;

    private String taxType;

    private String taxPrice;

    //soukai add 2012/01/05 ob start
    private BigDecimal weight;
    
    private BigDecimal summaryWeight;
    //soukai add 2012/01/05 ob end
    @AlphaNum2
    @Metadata(name = "ギフト(税込)", order = 1)
    private String giftCode;

    private List<CodeAttribute> giftList = new ArrayList<CodeAttribute>();

    @Digit
    @Required
    @Length(4)
    @Range(min = 1, max = 9999)
    @Metadata(name = "数量", order = 2)
    private String purchasingAmount;

    private String samaryPrice;

    //2012/11/21 促销对应 新建订单_商品选择  ob add start
    private String campaignCode;

    private String campaignName;

    private List<CartGiftCommodityDetailListBean> giftCommodityList = new ArrayList<CartGiftCommodityDetailListBean>();

    private List<CompositionItem> compositionItemList = new ArrayList<CompositionItem>();

    private String commodityCompositionName;

    private Long commodityType = 0L;
    
    private Long commodityFlg = 0L;

    private String originalCommodityCode;
    
    private Long combinationAmount;
    
    private String isDiscountCommodity = "false";
    
    
    /**
     * @return the originalCommodityCode
     */
    public String getOriginalCommodityCode() {
      return originalCommodityCode;
    }

    
    /**
     * @return the combinationAmount
     */
    public Long getCombinationAmount() {
      return combinationAmount;
    }

    
    /**
     * @param originalCommodityCode the originalCommodityCode to set
     */
    public void setOriginalCommodityCode(String originalCommodityCode) {
      this.originalCommodityCode = originalCommodityCode;
    }

    
    /**
     * @param combinationAmount the combinationAmount to set
     */
    public void setCombinationAmount(Long combinationAmount) {
      this.combinationAmount = combinationAmount;
    }

    public Long getCommodityType() {
			return commodityType;
		}

		public void setCommodityType(Long commodityType) {
			this.commodityType = commodityType;
		}

		public Long getCommodityFlg() {
			return commodityFlg;
		}

		public void setCommodityFlg(Long commodityFlg) {
			this.commodityFlg = commodityFlg;
		}

		public List<CompositionItem> getCompositionItemList() {
			return compositionItemList;
		}

		public void setCompositionItemList(List<CompositionItem> compositionItemList) {
			this.compositionItemList = compositionItemList;
		}

		public String getCommodityCompositionName() {
			return commodityCompositionName;
		}

		public void setCommodityCompositionName(String commodityCompositionName) {
			this.commodityCompositionName = commodityCompositionName;
		}

		public List<CartGiftCommodityDetailListBean> getGiftCommodityList() {
			return giftCommodityList;
		}

		public void setGiftCommodityList(
				List<CartGiftCommodityDetailListBean> giftCommodityList) {
			this.giftCommodityList = giftCommodityList;
		}

    public String getCampaignCode() {
			return campaignCode;
		}

		public void setCampaignCode(String campaignCode) {
			this.campaignCode = campaignCode;
		}

		public String getCampaignName() {
			return campaignName;
		}

		public void setCampaignName(String campaignName) {
			this.campaignName = campaignName;
		}
	  //2012/11/21 促销对应 新建订单_商品选择  ob add end

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
     * purchasingAmountを取得します。
     * 
     * @return purchasingAmount
     */
    public String getPurchasingAmount() {
      return purchasingAmount;
    }

    /**
     * purchasingAmountを設定します。
     * 
     * @param purchasingAmount
     *          purchasingAmount
     */
    public void setPurchasingAmount(String purchasingAmount) {
      this.purchasingAmount = purchasingAmount;
    }

    /**
     * retailPriceを取得します。
     * 
     * @return retailPrice
     */
    public String getRetailPrice() {
      return retailPrice;
    }

    /**
     * retailPriceを設定します。
     * 
     * @param retailPrice
     *          retailPrice
     */
    public void setRetailPrice(String retailPrice) {
      this.retailPrice = retailPrice;
    }

    /**
     * samaryPriceを取得します。
     * 
     * @return samaryPrice
     */
    public String getSamaryPrice() {
      return samaryPrice;
    }

    /**
     * samaryPriceを設定します。
     * 
     * @param samaryPrice
     *          samaryPrice
     */
    public void setSamaryPrice(String samaryPrice) {
      this.samaryPrice = samaryPrice;
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
     * skuCodeを取得します。
     * 
     * @return skuCode
     */
    public String getSkuCode() {
      return skuCode;
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
     * controlSkuCodeを取得します。
     * 
     * @return controlSkuCode
     */
    public String getControlSkuCode() {
      return controlSkuCode;
    }

    /**
     * displaySkuCodeを設定します。
     * 
     * @param controlSkuCode
     *          controlSkuCode
     */
    public void setControlSkuCode(String controlSkuCode) {
      this.controlSkuCode = controlSkuCode;
    }

    /**
     * standard1Nameを取得します。
     * 
     * @return standard1Name
     */
    public String getStandard1Name() {
      return standard1Name;
    }

    /**
     * standard1Nameを設定します。
     * 
     * @param standard1Name
     *          standard1Name
     */
    public void setStandard1Name(String standard1Name) {
      this.standard1Name = standard1Name;
    }

    /**
     * standard2Nameを取得します。
     * 
     * @return standard2Name
     */
    public String getStandard2Name() {
      return standard2Name;
    }

    /**
     * standard2Nameを設定します。
     * 
     * @param standard2Name
     *          standard2Name
     */
    public void setStandard2Name(String standard2Name) {
      this.standard2Name = standard2Name;
    }

    /**
     * taxPriceを取得します。
     * 
     * @return taxPrice
     */
    public String getTaxPrice() {
      return taxPrice;
    }

    /**
     * taxPriceを設定します。
     * 
     * @param taxPrice
     *          taxPrice
     */
    public void setTaxPrice(String taxPrice) {
      this.taxPrice = taxPrice;
    }

    /**
     * taxRateを取得します。
     * 
     * @return taxRate
     */
    public String getTaxRate() {
      return taxRate;
    }

    /**
     * taxRateを設定します。
     * 
     * @param taxRate
     *          taxRate
     */
    public void setTaxRate(String taxRate) {
      this.taxRate = taxRate;
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
     * giftListを取得します。
     * 
     * @return giftList
     */
    public List<CodeAttribute> getGiftList() {
      return giftList;
    }

    /**
     * giftListを設定します。
     * 
     * @param giftList
     *          giftList
     */
    public void setGiftList(List<CodeAttribute> giftList) {
      this.giftList = giftList;
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
     * @return the cartKey
     */
    public String createKey() {
      String keyShopCode = this.getShopCode();
      String keySkuCode = this.getSkuCode();
      String keyGiftCode = this.getGiftCode();

      return createKey(keyShopCode, keySkuCode, keyGiftCode);
    }

    /**
     * @param keyShopCode
     * @param keySkuCode
     * @param keyGiftCode
     * @return the cartKey
     */
    public String createKey(String keyShopCode, String keySkuCode, String keyGiftCode) {
      if (keyShopCode == null) {
        keyShopCode = "";
      }

      if (keySkuCode == null) {
        keySkuCode = "";
      }

      if (keyGiftCode == null) {
        keyGiftCode = "";
      }

      this.setCartKey(keyShopCode + "/" + keySkuCode + "/" + keyGiftCode);
      return keyShopCode + "/" + keySkuCode + "/" + keyGiftCode;
    }

    /**
     * @return the cartKey
     */
    public String getCartKey() {
      return cartKey;
    }

    /**
     * @param cartKey
     *          the cartKey to set
     */
    public void setCartKey(String cartKey) {
      this.cartKey = cartKey;
    }

	/**
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * @return the summaryWeight
	 */
	public BigDecimal getSummaryWeight() {
		return summaryWeight;
	}

	/**
	 * @param summaryWeight the summaryWeight to set
	 */
	public void setSummaryWeight(BigDecimal summaryWeight) {
		this.summaryWeight = summaryWeight;
	}


  
  /**
   * @return the isDiscountCommodity
   */
  public String getIsDiscountCommodity() {
    return isDiscountCommodity;
  }


  
  /**
   * @param isDiscountCommodity the isDiscountCommodity to set
   */
  public void setIsDiscountCommodity(String isDiscountCommodity) {
    this.isDiscountCommodity = isDiscountCommodity;
  }
  }

  //2012/11/20 促销对应 新建订单_商品选择  ob add start

  /**
   * U1020110:新規受注(商品選択)のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CartGiftCommodityDetailListBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String cartKey;

    private String shopCode;

    private String shopName;

    private String commodityCode;

    private String skuCode;

    private String commodityName;

    private String standard1Name;

    private String standard2Name;

    private String retailPrice;

    private String taxRate;

    private String taxType;

    private String taxPrice;

    private BigDecimal weight;
    
    private BigDecimal summaryWeight;

    @AlphaNum2
    @Metadata(name = "ギフト(税込)", order = 1)
    private String giftCode;

    private List<CodeAttribute> giftList = new ArrayList<CodeAttribute>();

    @Digit
    @Required
    @Length(4)
    @Range(min = 1, max = 9999)
    @Metadata(name = "数量", order = 2)
    private String purchasingAmount;

    private String samaryPrice;

    private String campaignCode;

    private String campaignName;

    public String getCampaignCode() {
			return campaignCode;
		}

		public void setCampaignCode(String campaignCode) {
			this.campaignCode = campaignCode;
		}

		public String getCampaignName() {
			return campaignName;
		}

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
     * purchasingAmountを取得します。
     * 
     * @return purchasingAmount
     */
    public String getPurchasingAmount() {
      return purchasingAmount;
    }

    /**
     * purchasingAmountを設定します。
     * 
     * @param purchasingAmount
     *          purchasingAmount
     */
    public void setPurchasingAmount(String purchasingAmount) {
      this.purchasingAmount = purchasingAmount;
    }

    /**
     * retailPriceを取得します。
     * 
     * @return retailPrice
     */
    public String getRetailPrice() {
      return retailPrice;
    }

    /**
     * retailPriceを設定します。
     * 
     * @param retailPrice
     *          retailPrice
     */
    public void setRetailPrice(String retailPrice) {
      this.retailPrice = retailPrice;
    }

    /**
     * samaryPriceを取得します。
     * 
     * @return samaryPrice
     */
    public String getSamaryPrice() {
      return samaryPrice;
    }

    /**
     * samaryPriceを設定します。
     * 
     * @param samaryPrice
     *          samaryPrice
     */
    public void setSamaryPrice(String samaryPrice) {
      this.samaryPrice = samaryPrice;
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
     * skuCodeを取得します。
     * 
     * @return skuCode
     */
    public String getSkuCode() {
      return skuCode;
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
     * standard1Nameを取得します。
     * 
     * @return standard1Name
     */
    public String getStandard1Name() {
      return standard1Name;
    }

    /**
     * standard1Nameを設定します。
     * 
     * @param standard1Name
     *          standard1Name
     */
    public void setStandard1Name(String standard1Name) {
      this.standard1Name = standard1Name;
    }

    /**
     * standard2Nameを取得します。
     * 
     * @return standard2Name
     */
    public String getStandard2Name() {
      return standard2Name;
    }

    /**
     * standard2Nameを設定します。
     * 
     * @param standard2Name
     *          standard2Name
     */
    public void setStandard2Name(String standard2Name) {
      this.standard2Name = standard2Name;
    }

    /**
     * taxPriceを取得します。
     * 
     * @return taxPrice
     */
    public String getTaxPrice() {
      return taxPrice;
    }

    /**
     * taxPriceを設定します。
     * 
     * @param taxPrice
     *          taxPrice
     */
    public void setTaxPrice(String taxPrice) {
      this.taxPrice = taxPrice;
    }

    /**
     * taxRateを取得します。
     * 
     * @return taxRate
     */
    public String getTaxRate() {
      return taxRate;
    }

    /**
     * taxRateを設定します。
     * 
     * @param taxRate
     *          taxRate
     */
    public void setTaxRate(String taxRate) {
      this.taxRate = taxRate;
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
     * giftListを取得します。
     * 
     * @return giftList
     */
    public List<CodeAttribute> getGiftList() {
      return giftList;
    }

    /**
     * giftListを設定します。
     * 
     * @param giftList
     *          giftList
     */
    public void setGiftList(List<CodeAttribute> giftList) {
      this.giftList = giftList;
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
     * @return the cartKey
     */
    public String createKey() {
      String keyShopCode = this.getShopCode();
      String keySkuCode = this.getSkuCode();
      String keyGiftCode = this.getGiftCode();

      return createKey(keyShopCode, keySkuCode, keyGiftCode);
    }

    /**
     * @param keyShopCode
     * @param keySkuCode
     * @param keyGiftCode
     * @return the cartKey
     */
    public String createKey(String keyShopCode, String keySkuCode, String keyGiftCode) {
      if (keyShopCode == null) {
        keyShopCode = "";
      }

      if (keySkuCode == null) {
        keySkuCode = "";
      }

      if (keyGiftCode == null) {
        keyGiftCode = "";
      }

      this.setCartKey(keyShopCode + "/" + keySkuCode + "/" + keyGiftCode);
      return keyShopCode + "/" + keySkuCode + "/" + keyGiftCode;
    }

    /**
     * @return the cartKey
     */
    public String getCartKey() {
      return cartKey;
    }

    /**
     * @param cartKey
     *          the cartKey to set
     */
    public void setCartKey(String cartKey) {
      this.cartKey = cartKey;
    }

		/**
		 * @return the weight
		 */
		public BigDecimal getWeight() {
			return weight;
		}
	
		/**
		 * @param weight the weight to set
		 */
		public void setWeight(BigDecimal weight) {
			this.weight = weight;
		}
	
		/**
		 * @return the summaryWeight
		 */
		public BigDecimal getSummaryWeight() {
			return summaryWeight;
		}
	
		/**
		 * @param summaryWeight the summaryWeight to set
		 */
		public void setSummaryWeight(BigDecimal summaryWeight) {
			this.summaryWeight = summaryWeight;
		}
  }
  //2012/11/20 促销对应 新建订单_商品选择  ob add end
  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
    //2012/11/22 促销对应 新建订单_商品选择  ob add start
    reqparam.copy(commodityCompositionBean);
    reqparam.copy(giftCommodityBean);
    //2012/11/22 促销对应 新建订单_商品选择  ob add end

    this.inputCartList.setInputShopCode(reqparam.get("inputShopCode"));
    this.inputCartList.setInputSkuCode(reqparam.get("inputSkuCode"));

    for (CartCommodityDetailListBean cart : cartCommodityList) {
      cart.setGiftCode(WebUtil.escapeXml(reqparam.get("giftCode_" + cart.getShopCode() + "_" + cart.getSkuCode())));
      //2012/11/22 促销对应 新建订单_商品选择  ob update start
      cart.setPurchasingAmount(reqparam.get("purchasingAmount_" + cart.getShopCode() + "_" + cart.getControlSkuCode() + "_" + cart.getIsDiscountCommodity()));
      //2012/11/22 促销对应 新建订单_商品选择  ob update end
    }

    //2012/11/22 促销对应 新建订单_商品选择  ob add start
    if(commodityCompositionBean.getCommodityCompositionDetailBeanList() != null
    		&& commodityCompositionBean.getCommodityCompositionDetailBeanList().size() > 0){
    	for(int i = 0; i < commodityCompositionBean.getCommodityCompositionDetailBeanList().size(); i++){
    		String selectSku = reqparam.get("selectedSkuCode"+i);
    		commodityCompositionBean.getCommodityCompositionDetailBeanList().get(i).setSelectedSkuCode(selectSku);
    	}
    }
    //2012/11/22 促销对应 新建订单_商品选择  ob add end
  }

  /**
   * searchCategoryCodeを取得します。
   * 
   * @return searchCategoryCode
   */
  public String getSearchCategoryCode() {
    return searchCategoryCode;
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
   * searchCategoryCodeを設定します。
   * 
   * @param searchCategoryCode
   *          searchCategoryCode
   */
  public void setSearchCategoryCode(String searchCategoryCode) {
    this.searchCategoryCode = searchCategoryCode;
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
   * listを取得します。
   * 
   * @return list
   */
  public List<CommodityListDetailBean> getCommodityList() {
    return commodityList;
  }

  /**
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setCommodityList(List<CommodityListDetailBean> list) {
    this.commodityList = list;
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
    return "U1020110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.order.NeworderCommodityBean.0");
  }

  /**
   * cartCommodityListを取得します。
   * 
   * @return cartCommodityList
   */
  public List<CartCommodityDetailListBean> getCartCommodityList() {
    return cartCommodityList;
  }

  /**
   * cartCommodityListを設定します。
   * 
   * @param cartCommodityList
   *          cartCommodityList
   */
  public void setCartCommodityList(List<CartCommodityDetailListBean> cartCommodityList) {
    this.cartCommodityList = cartCommodityList;
  }

  // 10.1.2 10089 追加 ここから
  /**
   * categoryNodeInfoListを取得します。
   *
   * @return categoryNodeInfoList categoryNodeInfoList
   */
  public List<CategoryInfo> getCategoryNodeInfoList() {
    return categoryNodeInfoList;
  }
  
  /**
   * categoryNodeInfoListを設定します。
   *
   * @param categoryNodeInfoList 
   *          categoryNodeInfoList
   */
  public void setCategoryNodeInfoList(List<CategoryInfo> categoryNodeInfoList) {
    this.categoryNodeInfoList = categoryNodeInfoList;
  }
  // 10.1.2 10089 追加 ここまで
  
  /**
   * inputCartListを取得します。
   * 
   * @return inputCartList
   */
  public InputCartBean getInputCartList() {
    return inputCartList;
  }

  /**
   * inputCartListを設定します。
   * 
   * @param inputCartList
   *          inputCartList
   */
  public void setInputCartList(InputCartBean inputCartList) {
    this.inputCartList = inputCartList;
  }

  /**
   * searchCategoryListを取得します。
   * 
   * @return searchCategoryList
   */
  public String getSearchCategoryList() {
    return searchCategoryList;
  }

  /**
   * searchCategoryListを設定します。
   * 
   * @param searchCategoryList
   *          searchCategoryList
   */
  public void setSearchCategoryList(String searchCategoryList) {
    this.searchCategoryList = searchCategoryList;
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

  //2012/11/20 促销对应 新建订单_商品选择  ob add start
  /**
   * 取得commodityComposition。
   * 
   * @return commodityComposition
   */
  public boolean isCommodityComposition() {
    return commodityComposition;
  }

  /**
   * 设定commodityComposition。
   * 
   * @param commodityComposition
   *          commodityComposition
   */
  public void setCommodityComposition(boolean commodityComposition) {
    this.commodityComposition = commodityComposition;
  }

  /**
   * 取得commodityCompositionBean。
   * 
   * @return commodityCompositionBean
   */
	public NeworderCommodityCompositionBean getCommodityCompositionBean() {
		return commodityCompositionBean;
	}

  /**
   * 设定commodityCompositionBean。
   * 
   * @param commodityCompositionBean
   *          commodityCompositionBean
   */
	public void setCommodityCompositionBean(
			NeworderCommodityCompositionBean commodityCompositionBean) {
		this.commodityCompositionBean = commodityCompositionBean;
	}

  /**
   * 取得giftCommodityBean。
   * 
   * @return giftCommodityBean
   */
	public NeworderCommodityGiftBean getGiftCommodityBean() {
		return giftCommodityBean;
	}

  /**
   * 设定giftCommodityBean。
   * 
   * @param giftCommodityBean
   *          giftCommodityBean
   */
	public void setGiftCommodityBean(NeworderCommodityGiftBean giftCommodityBean) {
		this.giftCommodityBean = giftCommodityBean;
	}

  /**
   * 取得giftCommodityDisplayFlg。
   * 
   * @return giftCommodityDisplayFlg
   */
	public boolean isGiftCommodityDisplayFlg() {
		return giftCommodityDisplayFlg;
	}

  /**
   * 设定giftCommodityDisplayFlg。
   * 
   * @param giftCommodityDisplayFlg
   *          giftCommodityDisplayFlg
   */
	public void setGiftCommodityDisplayFlg(boolean giftCommodityDisplayFlg) {
		this.giftCommodityDisplayFlg = giftCommodityDisplayFlg;
	}

  /**
   * 取得cartOtherGiftCommodityList。
   * 
   * @return cartOtherGiftCommodityList
   */
	public List<CartCommodityDetailListBean> getCartOtherGiftCommodityList() {
		return cartOtherGiftCommodityList;
	}

  /**
   * 设定cartOtherGiftCommodityList。
   * 
   * @param cartOtherGiftCommodityList
   *          cartOtherGiftCommodityList
   */
	public void setCartOtherGiftCommodityList(List<CartCommodityDetailListBean> cartOtherGiftCommodityList) {
		this.cartOtherGiftCommodityList = cartOtherGiftCommodityList;
	}

  /**
   * selectedCompositionListを取得します。
   *
   * @return selectedCompositionList
   */
  public List<Sku> getSelectedCompositionList() {
    return selectedCompositionList;
  }

  /**
   * selectedCompositionListを設定します。
   *
   * @param selectedCompositionList
   *          selectedCompositionList
   */
  public void setSelectedCompositionList(List<Sku> selectedCompositionList) {
    this.selectedCompositionList = selectedCompositionList;
  }

  /**
   * multipleGiftListAllを取得します。
   *
   * @return multipleGiftListAll
   */
	public List<String> getMultipleGiftListAll() {
		return multipleGiftListAll;
	}

  /**
   * multipleGiftListAllを設定します。
   *
   * @param multipleGiftListAll
   *          multipleGiftListAll
   */
	public void setMultipleGiftListAll(List<String> multipleGiftListAll) {
		this.multipleGiftListAll = multipleGiftListAll;
	}

  /**
   * campaignHashTableを取得します。
   *
   * @return campaignHashTable
   */
	public Hashtable<String, CampaignMain> getCampaignHashTable() {
		return campaignHashTable;
	}

  /**
   * campaignHashTableを設定します。
   *
   * @param campaignHashTable
   *          campaignHashTable
   */
	public void setCampaignHashTable(
			Hashtable<String, CampaignMain> campaignHashTable) {
		this.campaignHashTable = campaignHashTable;
	}
  //2012/11/20 促销对应 新建订单_商品选择  ob add end
}
