package jp.co.sint.webshop.web.bean.front.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.AlphaNum3;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.bean.front.cart.CartBean.ShopCartBean.CommodityCartBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2020110:ショッピングカートのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CartBean extends UIFrontBean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
    addSubJspId("/common/header");
    addSubJspId("/catalog/sales_recommend");
    // 20120217 shen add start
    addSubJspId("/catalog/detail_recommend_d");
    // 20120217 shen add end
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) { // 10.1.6 10259 追加
    // ここから
    int amountUpdateCount = 0;
    int amountParameterCount = reqparam.getAll("commodityAmount").length;
    // 10.1.6 10259 追加 ここまで

    for (ShopCartBean shopBean : shopCartBean) {
      for (CommodityCartBean commodityBean : shopBean.getCommodityCartBean()) {
        for (int i = 0; i < reqparam.getAll("shippingShopCode").length; i++) {
          String strShopCode = reqparam.getAll("shippingShopCode")[i];
          String isDiscountCommodity = reqparam.getAll("isDiscountCommodity")[i];
          String strSkuCode = reqparam.getAll("skuCode")[i];
          String strGiftCode = reqparam.getAll("giftCode")[i];
          if (commodityBean.getShopCode().equals(strShopCode) && commodityBean.getSkuCode().equals(strSkuCode) 
              && commodityBean.getIsDiscountCommodity().equals(isDiscountCommodity) && commodityBean.getGiftOptionCode().equals(strGiftCode)) {
            commodityBean.setCommodityAmount(reqparam.getAll("commodityAmount")[i]);
            amountUpdateCount++; // 10.1.6 10259 追加
          }
        }
      }
      
      for (CommodityCartBean commodityBean : shopBean.getOptionalCommodityList()) {
        for (int i = 0; i < reqparam.getAll("optionalShippingShopCode").length; i++) {
          String strShopCode = reqparam.getAll("optionalShippingShopCode")[i];
          String isDiscountCommodity = reqparam.getAll("optionalIsDiscountCommodity")[i];
          String strSkuCode = reqparam.getAll("optionalSkuCode")[i];
          String strGiftCode = reqparam.getAll("optionalGiftCode")[i];
          if (commodityBean.getShopCode().equals(strShopCode) && commodityBean.getSkuCode().equals(strSkuCode) 
              && commodityBean.getIsDiscountCommodity().equals(isDiscountCommodity) && commodityBean.getGiftOptionCode().equals(strGiftCode)) {
            commodityBean.setCommodityAmount(reqparam.getAll("optionalCommodityAmount")[i]);
            amountUpdateCount++; // 10.1.6 10259 追加
          }
        }
      }
    }
    // 10.1.6 10259 追加 ここから
    setUpdateQuantityActionError(amountUpdateCount != amountParameterCount); // 数量変更アクション時エラー
    // 10.1.6 10259 追加 ここまで
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2020110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.cart.CartBean.0");
  }

  /** 各ボタン押下時に選択されたショップコード */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** 各ボタン押下時に選択されたSKUコード */
  @PrimaryKey(3)
  @Required
  // 2012/12/04 促销对应 ob update start
//  @Length(24)giftReset
//  @AlphaNum2
  @AlphaNum3
  // 2012/12/04 促销对应 ob update end
  
  @Metadata(name = "SKUコード", order = 2)
  private String skuCode;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "ギフトコード", order = 11)
  private String giftCode;
  
  // 2012/11/23 促销对应 ob add start
  @Length(16)
  @AlphaNum2
  @Metadata(name = "キャンペーンコード", order = 4)
  private String campaignCode;
  // 2012/11/23 促销对应 ob add end

  private List<ShopCartBean> shopCartBean = new ArrayList<ShopCartBean>();

  private String backUrl;

  private boolean displayNextButton;

  private boolean displayBackButton;

  /** エラーメッセージリスト */
  private List<String> errorMessageList = new ArrayList<String>();

  private boolean updateQuantityActionError; // 10.1.6 10259 追加
  
  // 2012/11/24 促销对应 ob add start
  Map<String, String> cartGiftName = new Hashtable<String, String>();
  
  Map<String, String> cartCommodityName = new Hashtable<String, String>();
  // 2012/11/24 促销对应 ob add end

  // 20111220 shen add start
  private List<CodeAttribute> prefectureList = new ArrayList<CodeAttribute>();

  // 20111220 shen add end

  /**
   * U2020110:ショッピングカートのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class ShopCartBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 決済ショップコード */
    private String shopCode;

    /** 決済ショップ名 */
    private String shopName;

    /** 配送ショップコード */
    private String deliveryShopCode;

    /** 予約用SKUコード */
    private String reserveSkuCode;

    /** 合計金額 */
    private BigDecimal shopClaimTotal;

    // 20120104 shen add start
    /** 合计重量 */
    private BigDecimal weightTotal;

    // 20120104 shen add end

    /** 商品情報 */
    private List<CommodityCartBean> commodityCartBean = new ArrayList<CommodityCartBean>();
    
    private List<CommodityCartBean> optionalCommodityListPage = new ArrayList<CommodityCartBean>();
    
    private List<CommodityCartBean> optionalCommodityList = new ArrayList<CommodityCartBean>();
    
    // 2012/11/21 促销对应 ob add start
    private List<CommodityCartBean> giftBean = new ArrayList<CommodityCartBean>();
    
    private List<CommodityCartBean> campaignInfoList = new ArrayList<CommodityCartBean>();
    
    private List<CommodityCartBean> acceptedGiftBean = new ArrayList<CommodityCartBean>();
    // 2012/11/21 促销对应 ob add end

    // 20120112 shen add start
    private String prefectureCode;

    private BigDecimal shippingCharge;

    private BigDecimal cartPriceForCharge;

    private BigDecimal cartWeightForCharge;

    private BigDecimal cartPriceForCoupon;

    private BigDecimal couponPrice;

    private Long couponProportion;

    // 20120112 shen add end

    /**
     * U2020110:ショッピングカートのサブモデルです。
     * 
     * @author System Integrator Corp.
     */
    public static class CommodityCartBean implements Serializable ,Comparable<CommodityCartBean>{

      /** serial version uid */
      private static final long serialVersionUID = 1L;

      /** 配送ショップコード */
      private String shopCode;

      private String commodityCode;

      private String skuCode;
      
      // 2012/12/04 促销对应 ob add start
      private String skuCodeOfSet;
      // 2012/12/04 促销对应 ob add end

      private String commodityName;

      private String standardDetail1Name;

      private String standardDetail2Name;

      private BigDecimal commodityUnitPrice;

      private BigDecimal commoditySalesPrice;

      private String giftOptionCode;

      private String giftName;

      private BigDecimal giftPrice;

      private BigDecimal subTotal;

      private boolean reserve;

      @Required
      @Digit
      @Range(min = 1, max = 9999)
      @Length(4)
      @Metadata(name = "数量", order = 1)
      private String commodityAmount;

      // 20111215 shen add start
      private BigDecimal discountPrice;

      private String discountRate;

      private boolean displayDiscountRate;
      
      private String originalCommodityCode;
      
      private long combinationAmount;

      // 20111215 shen add end

      // 20111230 shen add start
      private BigDecimal weight;

      // 20111230 shen add end
      
      // 20120217 shen add start
      private String stockManagementType;
      // 20120217 shen add end
      
      // 2012/11/21 促销对应 ob add start
      private String campaignCode;

      private String campaignName;
    
      private List<CompositionBean> compositionList = new ArrayList<CompositionBean>();
      
      private List<GiftBean> giftList = new ArrayList<GiftBean>();
      
      private boolean gift;
      
      private boolean setCommodity;
      // 2012/11/21 促销对应 ob add end
      
      private String isDiscountCommodity = "false";
      
      /**
       * commodityAmountを取得します。
       * 
       * @return commodityAmount
       */
      public String getCommodityAmount() {
        return commodityAmount;
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
       * commoditySalesPriceを取得します。
       * 
       * @return commoditySalesPrice
       */
      public BigDecimal getCommoditySalesPrice() {
        return commoditySalesPrice;
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
       * giftOptionCodeを取得します。
       * 
       * @return giftOptionCode
       */
      public String getGiftOptionCode() {
        return giftOptionCode;
      }

      /**
       * giftPriceを取得します。
       * 
       * @return giftPrice
       */
      public BigDecimal getGiftPrice() {
        return giftPrice;
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
       * @return the skuCodeOfSet
       */
      public String getSkuCodeOfSet() {
        return skuCodeOfSet;
      }

      /**
       * @param skuCodeOfSet the skuCodeOfSet to set
       */
      public void setSkuCodeOfSet(String skuCodeOfSet) {
        this.skuCodeOfSet = skuCodeOfSet;
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
       * commodityAmountを設定します。
       * 
       * @param commodityAmount
       *          commodityAmount
       */
      public void setCommodityAmount(String commodityAmount) {
        this.commodityAmount = commodityAmount;
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
       * commoditySalesPriceを設定します。
       * 
       * @param commoditySalesPrice
       *          commoditySalesPrice
       */
      public void setCommoditySalesPrice(BigDecimal commoditySalesPrice) {
        this.commoditySalesPrice = commoditySalesPrice;
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
       * giftOptionCodeを設定します。
       * 
       * @param giftOptionCode
       *          giftOptionCode
       */
      public void setGiftOptionCode(String giftOptionCode) {
        this.giftOptionCode = giftOptionCode;
      }

      /**
       * giftPriceを設定します。
       * 
       * @param giftPrice
       *          giftPrice
       */
      public void setGiftPrice(BigDecimal giftPrice) {
        this.giftPrice = giftPrice;
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
       * subTotalを取得します。
       * 
       * @return subTotal
       */
      public BigDecimal getSubTotal() {
        return subTotal;
      }

      /**
       * subTotalを設定します。
       * 
       * @param subTotal
       *          subTotal
       */
      public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
      }

      /**
       * commodityUnitPriceを取得します。
       * 
       * @return commodityUnitPrice
       */
      public BigDecimal getCommodityUnitPrice() {
        return commodityUnitPrice;
      }

      /**
       * commodityUnitPriceを設定します。
       * 
       * @param commodityUnitPrice
       *          commodityUnitPrice
       */
      public void setCommodityUnitPrice(BigDecimal commodityUnitPrice) {
        this.commodityUnitPrice = commodityUnitPrice;
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

      public BigDecimal getDiscountPrice() {
        return discountPrice;
      }

      public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
      }

      public String getDiscountRate() {
        return discountRate;
      }

      public void setDiscountRate(String discountRate) {
        this.discountRate = discountRate;
      }

      public boolean isDisplayDiscountRate() {
        return displayDiscountRate;
      }

      public void setDisplayDiscountRate(boolean displayDiscountRate) {
        this.displayDiscountRate = displayDiscountRate;
      }

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
       * @param stockManagementType the stockManagementType to set
       */
      public void setStockManagementType(String stockManagementType) {
        this.stockManagementType = stockManagementType;
      }
      
      /**
       * @return the campaignCode
       */
      public String getCampaignCode() {
        return campaignCode;
      }

      /**
       * @param campaignCode the campaignCode to set
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
       * @param campaignName the campaignName to set
       */
      public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
      }

      // 2012/11/21 促销对应 ob add start
      public static class CompositionBean implements Serializable {

        private static final long serialVersionUID = 1L;
        
        private String shopCode;

        private String commodityCode;

        private String skuCode;

        private String commodityName;

        private String standardDetail1Name;

        private String standardDetail2Name;

        /**
         * @return the shopCode
         */
        public String getShopCode() {
          return shopCode;
        }

        /**
         * @param shopCode the shopCode to set
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
         * @param commodityCode the commodityCode to set
         */
        public void setCommodityCode(String commodityCode) {
          this.commodityCode = commodityCode;
        }

        /**
         * @return the skuCode
         */
        public String getSkuCode() {
          return skuCode;
        }

        /**
         * @param skuCode the skuCode to set
         */
        public void setSkuCode(String skuCode) {
          this.skuCode = skuCode;
        }

        /**
         * @return the commodityName
         */
        public String getCommodityName() {
          return commodityName;
        }

        /**
         * @param commodityName the commodityName to set
         */
        public void setCommodityName(String commodityName) {
          this.commodityName = commodityName;
        }

        /**
         * @return the standardDetail1Name
         */
        public String getStandardDetail1Name() {
          return standardDetail1Name;
        }

        /**
         * @param standardDetail1Name the standardDetail1Name to set
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
         * @param standardDetail2Name the standardDetail2Name to set
         */
        public void setStandardDetail2Name(String standardDetail2Name) {
          this.standardDetail2Name = standardDetail2Name;
        }
        
      }
     
      public static class GiftBean implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        private String shopCode;

        private String giftCode;
        
        private String giftSkuCode;

        private String giftName;

        private String standardDetail1Name;

        private String standardDetail2Name;

        private BigDecimal giftSalesPrice;
        
        private String giftAmount;

        private BigDecimal weight;
        
        private BigDecimal subTotalWeight;
        
        private String campaignCode;

        private String campaignName;
        
        private BigDecimal subTotal;

        /**
         * @return the shopCode
         */
        public String getShopCode() {
          return shopCode;
        }

        /**
         * @param shopCode the shopCode to set
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
         * @param giftCode the giftCode to set
         */
        public void setGiftCode(String giftCode) {
          this.giftCode = giftCode;
        }

        /**
         * @return the giftSkuCode
         */
        public String getGiftSkuCode() {
          return giftSkuCode;
        }

        /**
         * @param giftSkuCode the giftSkuCode to set
         */
        public void setGiftSkuCode(String giftSkuCode) {
          this.giftSkuCode = giftSkuCode;
        }

        /**
         * @return the giftName
         */
        public String getGiftName() {
          return giftName;
        }

        /**
         * @param giftName the giftName to set
         */
        public void setGiftName(String giftName) {
          this.giftName = giftName;
        }

        /**
         * @return the standardDetail1Name
         */
        public String getStandardDetail1Name() {
          return standardDetail1Name;
        }

        /**
         * @param standardDetail1Name the standardDetail1Name to set
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
         * @param standardDetail2Name the standardDetail2Name to set
         */
        public void setStandardDetail2Name(String standardDetail2Name) {
          this.standardDetail2Name = standardDetail2Name;
        }

        /**
         * @return the giftSalesPrice
         */
        public BigDecimal getGiftSalesPrice() {
          return giftSalesPrice;
        }

        /**
         * @param giftSalesPrice the giftSalesPrice to set
         */
        public void setGiftSalesPrice(BigDecimal giftSalesPrice) {
          this.giftSalesPrice = giftSalesPrice;
        }

        /**
         * @return the giftAmount
         */
        public String getGiftAmount() {
          return giftAmount;
        }

        /**
         * @param giftAmount the giftAmount to set
         */
        public void setGiftAmount(String giftAmount) {
          this.giftAmount = giftAmount;
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
        // 2012/11/21 促销对应 ob add end

        /**
         * @return the subTotalWeight
         */
        public BigDecimal getSubTotalWeight() {
          return subTotalWeight;
        }

        /**
         * @param subTotalWeight the subTotalWeight to set
         */
        public void setSubTotalWeight(BigDecimal subTotalWeight) {
          this.subTotalWeight = subTotalWeight;
        }

        /**
         * @return the campaignCode
         */
        public String getCampaignCode() {
          return campaignCode;
        }

        /**
         * @param campaignCode the campaignCode to set
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
         * @param campaignName the campaignName to set
         */
        public void setCampaignName(String campaignName) {
          this.campaignName = campaignName;
        }

        /**
         * @return the subTotal
         */
        public BigDecimal getSubTotal() {
          return subTotal;
        }

        /**
         * @param subTotal the subTotal to set
         */
        public void setSubTotal(BigDecimal subTotal) {
          this.subTotal = subTotal;
        }
      }


      /**
       * @return the compositionList
       */
      public List<CompositionBean> getCompositionList() {
        return compositionList;
      }

      /**
       * @param compositionList the compositionList to set
       */
      public void setCompositionList(List<CompositionBean> compositionList) {
        this.compositionList = compositionList;
      }

      /**
       * @return the giftList
       */
      public List<GiftBean> getGiftList() {
        return giftList;
      }

      /**
       * @param giftList the giftList to set
       */
      public void setGiftList(List<GiftBean> giftList) {
        this.giftList = giftList;
      }

      /**
       * @return the gift
       */
      public boolean isGift() {
        return gift;
      }

      /**
       * @param gift the gift to set
       */
      public void setGift(boolean gift) {
        this.gift = gift;
      }

      /**
       * @return the setCommodity
       */
      public boolean isSetCommodity() {
        setCommodity = false;
        if (compositionList != null && compositionList.size() > 0) {
          setCommodity = true;
        } 
        
        return setCommodity;
      }

      /**
       * @param setCommodity the setCommodity to set
       */
      public void setSetCommodity(boolean setCommodity) {

        this.setCommodity = setCommodity;
      }

      
      /**
       * @return the originalCommodityCode
       */
      public String getOriginalCommodityCode() {
        return originalCommodityCode;
      }

      
      /**
       * @param originalCommodityCode the originalCommodityCode to set
       */
      public void setOriginalCommodityCode(String originalCommodityCode) {
        this.originalCommodityCode = originalCommodityCode;
      }

      
      /**
       * @return the combinationAmount
       */
      public long getCombinationAmount() {
        return combinationAmount;
      }

      
      /**
       * @param combinationAmount the combinationAmount to set
       */
      public void setCombinationAmount(long combinationAmount) {
        this.combinationAmount = combinationAmount;
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

      @Override
      public int compareTo(CommodityCartBean o) {
        if (this.commoditySalesPrice.compareTo(o.getCommoditySalesPrice()) > 0) {
          return -1;
        }
        return 1;
      }
    }

    /**
     * commodityCartBeanを取得します。
     * 
     * @return commodityCartBean
     */
    public List<CommodityCartBean> getCommodityCartBean() {
      return commodityCartBean;
    }

    /**
     * shopClaimTotalを取得します。
     * 
     * @return shopClaimTotal
     */
    public BigDecimal getShopClaimTotal() {
      return shopClaimTotal;
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
     * commodityCartBeanを設定します。
     * 
     * @param commodityCartBean
     *          commodityCartBean
     */
    public void setCommodityCartBean(List<CommodityCartBean> commodityCartBean) {
      this.commodityCartBean = commodityCartBean;
    }

    /**
     * @return the giftBean
     */
    public List<CommodityCartBean> getGiftBean() {
      return giftBean;
    }

    /**
     * @param giftBean the giftBean to set
     */
    public void setGiftBean(List<CommodityCartBean> giftBean) {
      this.giftBean = giftBean;
    }

    public List<CommodityCartBean> getCampaignInfoList() {
			return campaignInfoList;
		}

		public void setCampaignInfoList(List<CommodityCartBean> campaignInfoList) {
			this.campaignInfoList = campaignInfoList;
		}

		/**
     * @return the acceptedGiftBean
     */
    public List<CommodityCartBean> getAcceptedGiftBean() {
      return acceptedGiftBean;
    }

    /**
     * @param acceptedGiftBean the acceptedGiftBean to set
     */
    public void setAcceptedGiftBean(List<CommodityCartBean> acceptedGiftBean) {
      this.acceptedGiftBean = acceptedGiftBean;
    }

    /**
     * shopClaimTotalを設定します。
     * 
     * @param shopClaimTotal
     *          shopClaimTotal
     */
    public void setShopClaimTotal(BigDecimal shopClaimTotal) {
      this.shopClaimTotal = shopClaimTotal;
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
     * reserveSkuCodeを取得します。
     * 
     * @return reserveSkuCode
     */
    public String getReserveSkuCode() {
      return reserveSkuCode;
    }

    /**
     * reserveSkuCodeを設定します。
     * 
     * @param reserveSkuCode
     *          reserveSkuCode
     */
    public void setReserveSkuCode(String reserveSkuCode) {
      this.reserveSkuCode = reserveSkuCode;
    }

    /**
     * deliveryShopCodeを取得します。
     * 
     * @return deliveryShopCode
     */
    public String getDeliveryShopCode() {
      return deliveryShopCode;
    }

    /**
     * deliveryShopCodeを設定します。
     * 
     * @param deliveryShopCode
     *          deliveryShopCode
     */
    public void setDeliveryShopCode(String deliveryShopCode) {
      this.deliveryShopCode = deliveryShopCode;
    }

    public BigDecimal getWeightTotal() {
      return weightTotal;
    }

    public void setWeightTotal(BigDecimal weightTotal) {
      this.weightTotal = weightTotal;
    }

    /**
     * @return the prefectureCode
     */
    public String getPrefectureCode() {
      return prefectureCode;
    }

    /**
     * @param prefectureCode
     *          the prefectureCode to set
     */
    public void setPrefectureCode(String prefectureCode) {
      this.prefectureCode = prefectureCode;
    }

    /**
     * @return the shippingCharge
     */
    public BigDecimal getShippingCharge() {
      return shippingCharge;
    }

    /**
     * @param shippingCharge
     *          the shippingCharge to set
     */
    public void setShippingCharge(BigDecimal shippingCharge) {
      this.shippingCharge = shippingCharge;
    }

    /**
     * @return the cartPriceForCharge
     */
    public BigDecimal getCartPriceForCharge() {
      return cartPriceForCharge;
    }

    /**
     * @param cartPriceForCharge
     *          the cartPriceForCharge to set
     */
    public void setCartPriceForCharge(BigDecimal cartPriceForCharge) {
      this.cartPriceForCharge = cartPriceForCharge;
    }

    /**
     * @return the cartWeightForCharge
     */
    public BigDecimal getCartWeightForCharge() {
      return cartWeightForCharge;
    }

    /**
     * @param cartWeightForCharge
     *          the cartWeightForCharge to set
     */
    public void setCartWeightForCharge(BigDecimal cartWeightForCharge) {
      this.cartWeightForCharge = cartWeightForCharge;
    }

    /**
     * @return the cartPriceForCoupon
     */
    public BigDecimal getCartPriceForCoupon() {
      return cartPriceForCoupon;
    }

    /**
     * @param cartPriceForCoupon
     *          the cartPriceForCoupon to set
     */
    public void setCartPriceForCoupon(BigDecimal cartPriceForCoupon) {
      this.cartPriceForCoupon = cartPriceForCoupon;
    }

    /**
     * @return the couponPrice
     */
    public BigDecimal getCouponPrice() {
      return couponPrice;
    }

    /**
     * @param couponPrice
     *          the couponPrice to set
     */
    public void setCouponPrice(BigDecimal couponPrice) {
      this.couponPrice = couponPrice;
    }

    /**
     * @return the couponProportion
     */
    public Long getCouponProportion() {
      return couponProportion;
    }

    /**
     * @param couponProportion
     *          the couponProportion to set
     */
    public void setCouponProportion(Long couponProportion) {
      this.couponProportion = couponProportion;
    }

    
    /**
     * @return the optionalCommodityList
     */
    public List<CommodityCartBean> getOptionalCommodityList() {
      return optionalCommodityList;
    }

    
    /**
     * @param optionalCommodityList the optionalCommodityList to set
     */
    public void setOptionalCommodityList(List<CommodityCartBean> optionalCommodityList) {
      this.optionalCommodityList = optionalCommodityList;
    }

    
    /**
     * @return the optionalCommodityListPage
     */
    public List<CommodityCartBean> getOptionalCommodityListPage() {
      return optionalCommodityListPage;
    }

    
    /**
     * @param optionalCommodityListPage the optionalCommodityListPage to set
     */
    public void setOptionalCommodityListPage(List<CommodityCartBean> optionalCommodityListPage) {
      this.optionalCommodityListPage = optionalCommodityListPage;
    }

  }

  /**
   * shopCartBeanを取得します。
   * 
   * @return shopCartBean
   */
  public List<ShopCartBean> getShopCartBean() {
    return shopCartBean;
  }

  /**
   * shopCartBeanを設定します。
   * 
   * @param shopCartBean
   *          shopCartBean
   */
  public void setShopCartBean(List<ShopCartBean> shopCartBean) {
    this.shopCartBean = shopCartBean;
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
   * displayNextButtonを設定します。
   * 
   * @param displayNextButton
   *          displayNextButton
   */
  public void setDisplayNextButton(boolean displayNextButton) {
    this.displayNextButton = displayNextButton;
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
   * @return the campaignCode
   */
  public String getCampaignCode() {
    return campaignCode;
  }

  /**
   * @param campaignCode the campaignCode to set
   */
  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }

  /**
   * errorMessageListを取得します。
   * 
   * @return errorMessageList
   */
  public List<String> getErrorMessageList() {
    return errorMessageList;
  }

  /**
   * errorMessageListを設定します。
   * 
   * @param errorMessageList
   *          errorMessageList
   */
  public void setErrorMessageList(List<String> errorMessageList) {
    this.errorMessageList = errorMessageList;
  }

  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString("web.bean.front.cart.CartBean.1"), "/app/cart/cart"));
    return topicPath;
  }

  /**
   * displayBackButtonを返します。
   * 
   * @return the displayBackButton
   */
  public boolean isDisplayBackButton() {
    return displayBackButton;
  }

  /**
   * displayBackButtonを設定します。
   * 
   * @param displayBackButton
   *          設定する displayBackButton
   */
  public void setDisplayBackButton(boolean displayBackButton) {
    this.displayBackButton = displayBackButton;
  }

  /**
   * backUrlを返します。
   * 
   * @return the backUrl
   */
  public String getBackUrl() {
    return backUrl;
  }

  /**
   * backUrlを設定します。
   * 
   * @param backUrl
   *          設定する backUrl
   */
  public void setBackUrl(String backUrl) {
    this.backUrl = backUrl;
  }

  // 10.1.6 10259 追加 ここから
  /**
   * updateQuantityActionErrorを取得します。
   * 
   * @return updateQuantityActionError
   */
  public boolean isUpdateQuantityActionError() {
    return updateQuantityActionError;
  }

  /**
   * updateQuantityActionErrorを設定します。
   * 
   * @param updateQuantityActionError
   *          updateQuantityActionError
   */
  public void setUpdateQuantityActionError(boolean updateQuantityActionError) {
    this.updateQuantityActionError = updateQuantityActionError;
  }

  // 10.1.6 10259 追加 ここまで

  /**
   * @return the prefectureList
   */
  public List<CodeAttribute> getPrefectureList() {
    return prefectureList;
  }

  /**
   * @param prefectureList
   *          the prefectureList to set
   */
  public void setPrefectureList(List<CodeAttribute> prefectureList) {
    this.prefectureList = prefectureList;
  }

  /**
   * @return the cartGiftName
   */
  public Map<String, String> getCartGiftName() {
    return cartGiftName;
  }

  /**
   * @param cartGiftName the cartGiftName to set
   */
  public void setCartGiftName(Map<String, String> cartGiftName) {
    this.cartGiftName = cartGiftName;
  }

  /**
   * @return the cartCommodityName
   */
  public Map<String, String> getCartCommodityName() {
    return cartCommodityName;
  }

  /**
   * @param cartCommodityName the cartCommodityName to set
   */
  public void setCartCommodityName(Map<String, String> cartCommodityName) {
    this.cartCommodityName = cartCommodityName;
  }

}
