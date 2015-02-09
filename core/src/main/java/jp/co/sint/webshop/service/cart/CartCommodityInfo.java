package jp.co.sint.webshop.service.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * カートで使用する商品情報を保持するクラス
 * 
 * @author System Integrator Corp.
 */
public class CartCommodityInfo implements Serializable, Cloneable ,Comparable<CartCommodityInfo> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String commodityCode;

  private String commodityName;

  private String skuCode;

  private Long commodityTaxType;

  private BigDecimal commodityTaxCharge;

  private BigDecimal unitPrice;

  private BigDecimal unitTaxCharge;

  private BigDecimal retailPrice;

  private String standardDetail1Name;

  private String standardDetail2Name;

  private String giftCode;

  private String giftName;

  private BigDecimal giftPrice;

  private Long giftTaxType;

  private BigDecimal giftTaxCharge;

  private int quantity;

  private String campaignCode;

  private String campaignName;

  private String campaignNameEn;

  private String campaignNameJp;

  private Long campaignDiscountRate;

  private boolean discount;

  private boolean useCommodityPoint;

  private Long commodityPointRate;

  private CommodityPriceType commodityPriceType;

  private String returnPolicy; // 10.1.4 K00171 追加

  // 20111230 shen add start
  // 套餐商品时：套餐明细的总重量
  private BigDecimal weight;

  private String compositionAttribute;

  // 20111230 shen add end

  // 20120206 shen add start
  private String stockManagementType;

  // 20120206 shen add end

  // 2012/11/21 促销对应 ob add start
  // 套餐自身的重量
  private BigDecimal compositionWeight;

  private String skuCodeOfSet;

  // 商品区分
  private Long commodityType;

  // セット商品フラグ
  private Long setCommodityFlg;

  private List<GiftItem> giftList = new ArrayList<GiftItem>();

  private List<CompositionItem> compositionList = new ArrayList<CompositionItem>();

  // 折扣券的折扣类型
  private Long campaignCouponType;

  // 使用折扣券后的优惠金额
  private BigDecimal campaignCouponPrice = BigDecimal.ZERO;

  // 折扣券编号
  private String campaignCouponCode;

  // 折扣券活动名称
  private String campaignCouponName;

  private String multipleCampaignCode;

  private String multipleCampaignName;

  // 2012/11/21 促销对应 ob add end

  private String originalCommodityCode;

  private Long combinationAmount;

  private String isDiscountCommodity = "false";

  private BigDecimal discountTimePrice;

  /**
   * @return the commodityPointRate
   */
  public Long getCommodityPointRate() {
    return commodityPointRate;
  }

  /**
   * @return the compositionAttribute
   */
  public String getCompositionAttribute() {
    return compositionAttribute;
  }

  /**
   * @param compositionAttribute
   *          the compositionAttribute to set
   */
  public void setCompositionAttribute(String compositionAttribute) {
    this.compositionAttribute = compositionAttribute;
  }

  public Long getCampaignCouponType() {
    return campaignCouponType;
  }

  public void setCampaignCouponType(Long campaignCouponType) {
    this.campaignCouponType = campaignCouponType;
  }

  public String getMultipleCampaignCode() {
    return multipleCampaignCode;
  }

  public void setMultipleCampaignCode(String multipleCampaignCode) {
    this.multipleCampaignCode = multipleCampaignCode;
  }

  public String getMultipleCampaignName() {
    return multipleCampaignName;
  }

  public void setMultipleCampaignName(String multipleCampaignName) {
    this.multipleCampaignName = multipleCampaignName;
  }

  /**
   * @param commodityPointRate
   *          the commodityPointRate to set
   */
  public void setCommodityPointRate(Long commodityPointRate) {
    this.commodityPointRate = commodityPointRate;
  }

  /**
   * @return the discount
   */
  public boolean isDiscount() {
    return discount;
  }

  /**
   * @param discount
   *          the discount to set
   */
  public void setDiscount(boolean discount) {
    this.discount = discount;
  }

  /**
   * @return the useCommodityPoint
   */
  public boolean isUseCommodityPoint() {
    return useCommodityPoint;
  }

  /**
   * @param useCommodityPoint
   *          the useCommodityPoint to set
   */
  public void setUseCommodityPoint(boolean useCommodityPoint) {
    this.useCommodityPoint = useCommodityPoint;
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
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * commodityNameを取得します。
   * 
   * @return the commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  /**
   * commodityNameを設定します。
   * 
   * @param commodityName
   *          the commodityName to set
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * commodityTaxChargeを取得します。
   * 
   * @return the commodityTaxCharge
   */
  public BigDecimal getCommodityTaxCharge() {
    return commodityTaxCharge;
  }

  /**
   * commodityTaxChargeを設定します。
   * 
   * @param commodityTaxCharge
   *          the commodityTaxCharge to set
   */
  public void setCommodityTaxCharge(BigDecimal commodityTaxCharge) {
    this.commodityTaxCharge = commodityTaxCharge;
  }

  /**
   * commodityTaxTypeを取得します。
   * 
   * @return the commodityTaxType
   */
  public Long getCommodityTaxType() {
    return commodityTaxType;
  }

  /**
   * commodityTaxTypeを設定します。
   * 
   * @param commodityTaxType
   *          the commodityTaxType to set
   */
  public void setCommodityTaxType(Long commodityTaxType) {
    this.commodityTaxType = commodityTaxType;
  }

  /**
   * giftCodeを取得します。
   * 
   * @return the giftCode
   */
  public String getGiftCode() {
    return giftCode;
  }

  /**
   * giftCodeを設定します。
   * 
   * @param giftCode
   *          the giftCode to set
   */
  public void setGiftCode(String giftCode) {
    this.giftCode = giftCode;
  }

  /**
   * giftNameを取得します。
   * 
   * @return the giftName
   */
  public String getGiftName() {
    return giftName;
  }

  /**
   * giftNameを設定します。
   * 
   * @param giftName
   *          the giftName to set
   */
  public void setGiftName(String giftName) {
    this.giftName = giftName;
  }

  /**
   * giftPriceを取得します。
   * 
   * @return the giftPrice
   */
  public BigDecimal getGiftPrice() {
    return giftPrice;
  }

  /**
   * giftPriceを設定します。
   * 
   * @param giftPrice
   *          the giftPrice to set
   */
  public void setGiftPrice(BigDecimal giftPrice) {
    this.giftPrice = giftPrice;
  }

  /**
   * giftTaxChargeを取得します。
   * 
   * @return the giftTaxCharge
   */
  public BigDecimal getGiftTaxCharge() {
    return giftTaxCharge;
  }

  /**
   * giftTaxChargeを設定します。
   * 
   * @param giftTaxCharge
   *          the giftTaxCharge to set
   */
  public void setGiftTaxCharge(BigDecimal giftTaxCharge) {
    this.giftTaxCharge = giftTaxCharge;
  }

  /**
   * giftTaxTypeを取得します。
   * 
   * @return the giftTaxType
   */
  public Long getGiftTaxType() {
    return giftTaxType;
  }

  /**
   * giftTaxTypeを設定します。
   * 
   * @param giftTaxType
   *          the giftTaxType to set
   */
  public void setGiftTaxType(Long giftTaxType) {
    this.giftTaxType = giftTaxType;
  }

  /**
   * quantityを取得します。
   * 
   * @return the quantity
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * quantityを設定します。
   * 
   * @param quantity
   *          the quantity to set
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * retailPriceを取得します。
   * 
   * @return the retailPrice
   */
  public BigDecimal getRetailPrice() {
    if (StringUtil.hasValue(this.originalCommodityCode) && this.combinationAmount != null) {
      return retailPrice.multiply(new BigDecimal(this.combinationAmount));
    } else {
      return retailPrice;
    }
  }

  /**
   * retailPriceを設定します。
   * 
   * @param retailPrice
   *          the retailPrice to set
   */
  public void setRetailPrice(BigDecimal retailPrice) {
    this.retailPrice = retailPrice;
  }

  /**
   * skuCodeを取得します。
   * 
   * @return the skuCode
   */
  public String getSkuCode() {
    return skuCode;
  }

  /**
   * skuCodeを設定します。
   * 
   * @param skuCode
   *          the skuCode to set
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  /**
   * @return the skuCodeOfSet
   */
  public String getSkuCodeOfSet() {
    return skuCodeOfSet;
  }

  /**
   * @param skuCodeOfSet
   *          the skuCodeOfSet to set
   */
  public void setSkuCodeOfSet(String skuCodeOfSet) {
    this.skuCodeOfSet = skuCodeOfSet;
  }

  /**
   * standardDetail1Nameを取得します。
   * 
   * @return the standardDetail1Name
   */
  public String getStandardDetail1Name() {
    return standardDetail1Name;
  }

  /**
   * standardDetail1Nameを設定します。
   * 
   * @param standardDetail1Name
   *          the standardDetail1Name to set
   */
  public void setStandardDetail1Name(String standardDetail1Name) {
    this.standardDetail1Name = standardDetail1Name;
  }

  /**
   * standardDetail2Nameを取得します。
   * 
   * @return the standardDetail2Name
   */
  public String getStandardDetail2Name() {
    return standardDetail2Name;
  }

  /**
   * standardDetail2Nameを設定します。
   * 
   * @param standardDetail2Name
   *          the standardDetail2Name to set
   */
  public void setStandardDetail2Name(String standardDetail2Name) {
    this.standardDetail2Name = standardDetail2Name;
  }

  /**
   * unitPriceを取得します。
   * 
   * @return the unitPrice
   */
  public BigDecimal getUnitPrice() {
    if (StringUtil.hasValue(this.originalCommodityCode) && this.combinationAmount != null) {
      return unitPrice.multiply(new BigDecimal(this.combinationAmount));
    } else {
      return unitPrice;
    }

  }

  public BigDecimal getOriginalUnitPrice() {
    return this.unitPrice;
  }

  public BigDecimal getOriginalRetailPrice() {
    return this.retailPrice;
  }

  /**
   * unitPriceを設定します。
   * 
   * @param unitPrice
   *          the unitPrice to set
   */
  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return the campaignCode
   */
  public String getCampaignCode() {
    return campaignCode;
  }

  /**
   * @param campaignCode
   *          the campaignCode to set
   */
  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }

  /**
   * @return the campaignDiscountRate
   */
  public Long getCampaignDiscountRate() {
    return campaignDiscountRate;
  }

  /**
   * @param campaignDiscountRate
   *          the campaignDiscountRate to set
   */
  public void setCampaignDiscountRate(Long campaignDiscountRate) {
    this.campaignDiscountRate = campaignDiscountRate;
  }

  /**
   * @return the campaignName
   */
  public String getCampaignName() {
    return campaignName;
  }

  /**
   * @param campaignName
   *          the campaignName to set
   */
  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  /**
   * commodityPriceTypeを取得します。
   * 
   * @return commodityPriceType
   */
  public CommodityPriceType getCommodityPriceType() {
    return commodityPriceType;
  }

  /**
   * commodityPriceTypeを設定します。
   * 
   * @param commodityPriceType
   *          commodityPriceType
   */
  public void setCommodityPriceType(CommodityPriceType commodityPriceType) {
    this.commodityPriceType = commodityPriceType;
  }

  /**
   * @return the compositionWeight
   */
  public BigDecimal getCompositionWeight() {
    return compositionWeight;
  }

  /**
   * @param compositionWeight
   *          the compositionWeight to set
   */
  public void setCompositionWeight(BigDecimal compositionWeight) {
    this.compositionWeight = compositionWeight;
  }

  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      return null;
    }
  }

  /**
   * @return the unitTaxCharge
   */
  public BigDecimal getUnitTaxCharge() {
    return unitTaxCharge;
  }

  /**
   * @param unitTaxCharge
   *          the unitTaxCharge to set
   */
  public void setUnitTaxCharge(BigDecimal unitTaxCharge) {
    this.unitTaxCharge = unitTaxCharge;
  }

  // 10.1.4 K00171 追加 ここから
  public boolean isWithReturnPolicy() {
    return StringUtil.hasValue(getReturnPolicy());
  }

  public String getReturnPolicy() {
    return returnPolicy;
  }

  public void setReturnPolicy(String returnPolicy) {
    this.returnPolicy = returnPolicy;
  }

  // 10.1.4 K00171 追加 ここまで

  public BigDecimal getWeight() {
    return weight;
  }

  public void setWeight(BigDecimal weight) {
    this.weight = weight;
  }

  /**
   * @return the stockManagementType
   */
  public String getStockManagementType() {
    return stockManagementType;
  }

  /**
   * @param stockManagementType
   *          the stockManagementType to set
   */
  public void setStockManagementType(String stockManagementType) {
    this.stockManagementType = stockManagementType;
  }

  /**
   * @param campaignNameEn
   *          the campaignNameEn to set
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
   * @param campaignNameJp
   *          the campaignNameJp to set
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

  /**
   * @return the commodityType
   */
  public Long getCommodityType() {
    return commodityType;
  }

  /**
   * @param commodityType
   *          the commodityType to set
   */
  public void setCommodityType(Long commodityType) {
    this.commodityType = commodityType;
  }

  /**
   * @return the setCommodityFlg
   */
  public Long getSetCommodityFlg() {
    return setCommodityFlg;
  }

  /**
   * @param setCommodityFlg
   *          the setCommodityFlg to set
   */
  public void setSetCommodityFlg(Long setCommodityFlg) {
    this.setCommodityFlg = setCommodityFlg;
  }

  /**
   * @return the giftList
   */
  public List<GiftItem> getGiftList() {
    return giftList;
  }

  /**
   * @param giftList
   *          the giftList to set
   */
  public void setGiftList(List<GiftItem> giftList) {
    this.giftList = giftList;
  }

  /**
   * @return the compositionList
   */
  public List<CompositionItem> getCompositionList() {
    return compositionList;
  }

  /**
   * @param compositionList
   *          the compositionList to set
   */
  public void setCompositionList(List<CompositionItem> compositionList) {
    this.compositionList = compositionList;
  }

  /**
   * @return the campaignCouponPrice
   */
  public BigDecimal getCampaignCouponPrice() {
    return campaignCouponPrice;
  }

  /**
   * @param campaignCouponPrice
   *          the campaignCouponPrice to set
   */
  public void setCampaignCouponPrice(BigDecimal campaignCouponPrice) {
    this.campaignCouponPrice = campaignCouponPrice;
  }

  /**
   * @return the campaignCouponCode
   */
  public String getCampaignCouponCode() {
    return campaignCouponCode;
  }

  /**
   * @param campaignCouponCode
   *          the campaignCouponCode to set
   */
  public void setCampaignCouponCode(String campaignCouponCode) {
    this.campaignCouponCode = campaignCouponCode;
  }

  /**
   * @return the campaignCouponName
   */
  public String getCampaignCouponName() {
    return campaignCouponName;
  }

  /**
   * @param campaignCouponName
   *          the campaignCouponName to set
   */
  public void setCampaignCouponName(String campaignCouponName) {
    this.campaignCouponName = campaignCouponName;
  }

  // 2012/11/21 促销对应 ob add start
  public static class GiftItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String shopCode;

    private String giftCode;

    private String giftName;

    private String giftSkuCode;

    private Long taxType;

    private BigDecimal unitPrice;

    private BigDecimal unitTaxCharge;

    private BigDecimal retailPrice;

    private String standardDetail1Name;

    private String standardDetail2Name;

    private int quantity;

    private String campaignCode;

    private String campaignName;

    private boolean discount;

    private BigDecimal weight;

    /**
     * @return the shopCode
     */
    public String getShopCode() {
      return shopCode;
    }

    /**
     * @param shopCode
     *          the shopCode to set
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    /**
     * @return the giftCode
     */
    public String getGiftCode() {
      return giftCode;
    }

    /**
     * @param giftCode
     *          the giftCode to set
     */
    public void setGiftCode(String giftCode) {
      this.giftCode = giftCode;
    }

    /**
     * @return the giftName
     */
    public String getGiftName() {
      return giftName;
    }

    /**
     * @param giftName
     *          the giftName to set
     */
    public void setGiftName(String giftName) {
      this.giftName = giftName;
    }

    /**
     * @return the giftSkuCode
     */
    public String getGiftSkuCode() {
      return giftSkuCode;
    }

    /**
     * @param giftSkuCode
     *          the giftSkuCode to set
     */
    public void setGiftSkuCode(String giftSkuCode) {
      this.giftSkuCode = giftSkuCode;
    }

    /**
     * @return the taxType
     */
    public Long getTaxType() {
      return taxType;
    }

    /**
     * @param taxType
     *          the taxType to set
     */
    public void setTaxType(Long taxType) {
      this.taxType = taxType;
    }

    /**
     * @return the unitPrice
     */
    public BigDecimal getUnitPrice() {
      return unitPrice;
    }

    /**
     * @param unitPrice
     *          the unitPrice to set
     */
    public void setUnitPrice(BigDecimal unitPrice) {
      this.unitPrice = unitPrice;
    }

    /**
     * @return the unitTaxCharge
     */
    public BigDecimal getUnitTaxCharge() {
      return unitTaxCharge;
    }

    /**
     * @param unitTaxCharge
     *          the unitTaxCharge to set
     */
    public void setUnitTaxCharge(BigDecimal unitTaxCharge) {
      this.unitTaxCharge = unitTaxCharge;
    }

    /**
     * @return the retailPrice
     */
    public BigDecimal getRetailPrice() {
      return retailPrice;
    }

    /**
     * @param retailPrice
     *          the retailPrice to set
     */
    public void setRetailPrice(BigDecimal retailPrice) {
      this.retailPrice = retailPrice;
    }

    /**
     * @return the standardDetail1Name
     */
    public String getStandardDetail1Name() {
      return standardDetail1Name;
    }

    /**
     * @param standardDetail1Name
     *          the standardDetail1Name to set
     */
    public void setStandardDetail1Name(String standardDetail1Name) {
      this.standardDetail1Name = standardDetail1Name;
    }

    /**
     * @return the standardDetail2Name
     */
    public String getStandardDetail2Name() {
      return standardDetail2Name;
    }

    /**
     * @param standardDetail2Name
     *          the standardDetail2Name to set
     */
    public void setStandardDetail2Name(String standardDetail2Name) {
      this.standardDetail2Name = standardDetail2Name;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
      return quantity;
    }

    /**
     * @param quantity
     *          the quantity to set
     */
    public void setQuantity(int quantity) {
      this.quantity = quantity;
    }

    /**
     * @return the campaignCode
     */
    public String getCampaignCode() {
      return campaignCode;
    }

    /**
     * @param campaignCode
     *          the campaignCode to set
     */
    public void setCampaignCode(String campaignCode) {
      this.campaignCode = campaignCode;
    }

    /**
     * @return the campaignName
     */
    public String getCampaignName() {
      return campaignName;
    }

    /**
     * @param campaignName
     *          the campaignName to set
     */
    public void setCampaignName(String campaignName) {
      this.campaignName = campaignName;
    }

    /**
     * @return the discount
     */
    public boolean isDiscount() {
      return discount;
    }

    /**
     * @param discount
     *          the discount to set
     */
    public void setDiscount(boolean discount) {
      this.discount = discount;
    }

    /**
     * @return the weight
     */
    public BigDecimal getWeight() {
      return weight;
    }

    /**
     * @param weight
     *          the weight to set
     */
    public void setWeight(BigDecimal weight) {
      this.weight = weight;
    }
  }

  public static class CompositionItem implements Serializable {

    /** serial version UID */
    private static final long serialVersionUID = 1L;

    private String shopCode;

    private String commodityCode;

    private String commodityName;

    private String skuCode;

    private BigDecimal unitPrice;

    private Long commodityTaxType;

    private String standardDetail1Name;

    private String standardDetail2Name;

    private boolean representFlg;

    private BigDecimal retailPrice;

    private Long retailTax;

    private Long commodityTaxRate;

    private BigDecimal commodityTax;

    private BigDecimal weight;

    /**
     * @return the unitPrice
     */
    public BigDecimal getUnitPrice() {
      return unitPrice;
    }

    /**
     * @param unitPrice
     *          the unitPrice to set
     */
    public void setUnitPrice(BigDecimal unitPrice) {
      this.unitPrice = unitPrice;
    }

    /**
     * @return the retailPrice
     */
    public BigDecimal getRetailPrice() {
      return retailPrice;
    }

    /**
     * @param retailPrice
     *          the retailPrice to set
     */
    public void setRetailPrice(BigDecimal retailPrice) {
      this.retailPrice = retailPrice;
    }

    /**
     * @return the weight
     */
    public BigDecimal getWeight() {
      return weight;
    }

    /**
     * @param weight
     *          the weight to set
     */
    public void setWeight(BigDecimal weight) {
      this.weight = weight;
    }

    /**
     * @return the shopCode
     */
    public String getShopCode() {
      return shopCode;
    }

    /**
     * @param shopCode
     *          the shopCode to set
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
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
     * @return the commodityName
     */
    public String getCommodityName() {
      return commodityName;
    }

    /**
     * @param commodityName
     *          the commodityName to set
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    /**
     * @return the skuCode
     */
    public String getSkuCode() {
      return skuCode;
    }

    /**
     * @param skuCode
     *          the skuCode to set
     */
    public void setSkuCode(String skuCode) {
      this.skuCode = skuCode;
    }

    /**
     * @return the commodityTaxType
     */
    public Long getCommodityTaxType() {
      return commodityTaxType;
    }

    /**
     * @param commodityTaxType
     *          the commodityTaxType to set
     */
    public void setCommodityTaxType(Long commodityTaxType) {
      this.commodityTaxType = commodityTaxType;
    }

    /**
     * @return the standardDetail1Name
     */
    public String getStandardDetail1Name() {
      return standardDetail1Name;
    }

    /**
     * @param standardDetail1Name
     *          the standardDetail1Name to set
     */
    public void setStandardDetail1Name(String standardDetail1Name) {
      this.standardDetail1Name = standardDetail1Name;
    }

    /**
     * @return the standardDetail2Name
     */
    public String getStandardDetail2Name() {
      return standardDetail2Name;
    }

    /**
     * @param standardDetail2Name
     *          the standardDetail2Name to set
     */
    public void setStandardDetail2Name(String standardDetail2Name) {
      this.standardDetail2Name = standardDetail2Name;
    }

    /**
     * @return the representFlg
     */
    public boolean isRepresentFlg() {
      return representFlg;
    }

    /**
     * @param representFlg
     *          the representFlg to set
     */
    public void setRepresentFlg(boolean representFlg) {
      this.representFlg = representFlg;
    }

    /**
     * @return the retailTax
     */
    public Long getRetailTax() {
      return retailTax;
    }

    /**
     * @param retailTax
     *          the retailTax to set
     */
    public void setRetailTax(Long retailTax) {
      this.retailTax = retailTax;
    }

    /**
     * @return the commodityTaxRate
     */
    public Long getCommodityTaxRate() {
      return commodityTaxRate;
    }

    /**
     * @param commodityTaxRate
     *          the commodityTaxRate to set
     */
    public void setCommodityTaxRate(Long commodityTaxRate) {
      this.commodityTaxRate = commodityTaxRate;
    }

    /**
     * @return the commodityTax
     */
    public BigDecimal getCommodityTax() {
      return commodityTax;
    }

    /**
     * @param commodityTax
     *          the commodityTax to set
     */
    public void setCommodityTax(BigDecimal commodityTax) {
      this.commodityTax = commodityTax;
    }

  }

  // 2012/11/21 促销对应 ob add end

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
   * @return the isDiscountCommodity
   */
  public String getIsDiscountCommodity() {
    return isDiscountCommodity;
  }

  /**
   * @param isDiscountCommodity
   *          the isDiscountCommodity to set
   */
  public void setIsDiscountCommodity(String isDiscountCommodity) {
    this.isDiscountCommodity = isDiscountCommodity;
  }

  /**
   * @param combinationAmount
   *          the combinationAmount to set
   */
  public void setCombinationAmount(Long combinationAmount) {
    this.combinationAmount = combinationAmount;
  }

  /**
   * @return the combinationAmount
   */
  public Long getCombinationAmount() {
    return combinationAmount;
  }

  /**
   * @return the discountTimePrice
   */
  public BigDecimal getDiscountTimePrice() {
    return discountTimePrice;
  }

  /**
   * @param discountTimePrice
   *          the discountTimePrice to set
   */
  public void setDiscountTimePrice(BigDecimal discountTimePrice) {
    this.discountTimePrice = discountTimePrice;
  }

  @Override
  public int compareTo(CartCommodityInfo o) {
    if (this.retailPrice.compareTo(o.getRetailPrice()) > 0) {
      return -1;
    }
    return 1;
  }

}
