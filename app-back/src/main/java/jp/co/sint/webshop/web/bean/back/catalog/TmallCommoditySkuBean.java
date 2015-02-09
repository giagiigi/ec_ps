package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.JanCode;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.SaleFlg;
import jp.co.sint.webshop.data.domain.UsingFlg;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
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
public class TmallCommoditySkuBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private CCommoditySkuDetailBean edit = new CCommoditySkuDetailBean();

  private CCommodityHeader header = new CCommodityHeader();
  
  private List<CCommoditySkuDetailBean> list = new ArrayList<CCommoditySkuDetailBean>();

  private SaleFlg[] saleFlag = SaleFlg.values();
  
  private List<CCommodityDetail> skuList = new ArrayList<CCommodityDetail>();
  
  private CCommoditySkuDetailBean representSku = new CCommoditySkuDetailBean();
  
  private List<CodeAttribute> standards = new ArrayList<CodeAttribute>();
  
  private UsingFlg[] usingFlg = UsingFlg.values();
  
  
  private boolean displayUpdateButton = false;
  
  private boolean displayDeleteButton = false;
  
  private String commodityEditDisplayMode = "";
  
  /**
   * 税率区分List
   */
  private List<NameValue> taxClassList = new ArrayList<NameValue>();
  
  /**
   * @return the taxClassList
   */
  public List<NameValue> getTaxClassList() {
    return taxClassList;
  }

  
  /**
   * @param taxClassList the taxClassList to set
   */
  public void setTaxClassList(List<NameValue> taxClassList) {
    this.taxClassList = taxClassList;
  }

  /**
   * @return the commodityEditDisplayMode
   */
  public String getCommodityEditDisplayMode() {
    return commodityEditDisplayMode;
  }

  /**
   * @param commodityEditDisplayMode the commodityEditDisplayMode to set
   */
  public void setCommodityEditDisplayMode(String commodityEditDisplayMode) {
    this.commodityEditDisplayMode = commodityEditDisplayMode;
  }

  /**
   * @return the usingFlg
   */
  public UsingFlg[] getUsingFlg() {
    return usingFlg;
  }

  
  /**
   * @param usingFlg the usingFlg to set
   */
  public void setUsingFlg(UsingFlg[] usingFlg) {
    this.usingFlg = usingFlg;
  }

  /**
   * @return the standards
   */
  public List<CodeAttribute> getStandards() {
    return standards;
  }
  
  /**
   * @param standards the standards to set
   */
  public void setStandards(List<CodeAttribute> standards) {
    this.standards = standards;
  }

  /**
   * @return the displayUpdateButton
   */
  public boolean isDisplayUpdateButton() {
    return displayUpdateButton;
  }

  /**
   * @param displayUpdateButton the displayUpdateButton to set
   */
  public void setDisplayUpdateButton(boolean displayUpdateButton) {
    this.displayUpdateButton = displayUpdateButton;
  }

  /**
   * @return the displayDeleteButton
   */
  public boolean isDisplayDeleteButton() {
    return displayDeleteButton;
  }
  
  /**
   * @param displayDeleteButton the displayDeleteButton to set
   */
  public void setDisplayDeleteButton(boolean displayDeleteButton) {
    this.displayDeleteButton = displayDeleteButton;
  }

  /**
   * @return the representSku
   */
  public CCommoditySkuDetailBean getRepresentSku() {
    return representSku;
  }
  
  /**
   * @param representSku the representSku to set
   */
  public void setRepresentSku(CCommoditySkuDetailBean representSku) {
    this.representSku = representSku;
  }

  private String parentCommodityCode;

  private String parentCommodityName;

  private String representSkuCode;

  private String stockManagementType;

  private String shopCode;

  private String standardDetail1Name;

  //add by os014 2012-01-31 begin
  private String commodityStandardDetail1Id;
  
  private String commodityStandardDetail2Id;
  
  /**
   * 用于界面展示sku属性1值
   */
  private List<CodeAttribute> standard1ValueList = new ArrayList<CodeAttribute>();
  /**
   * 用于界面展示sku属性2值
   */
  private List<CodeAttribute> standard2ValueList = new ArrayList<CodeAttribute>();
  
  private String standardDetail2Name;
  //add by os014 2012-01-31 end
  private List<CodeAttribute> skuImageList = new ArrayList<CodeAttribute>();

  
  /**
   * @return the standard1ValueList
   */
  public List<CodeAttribute> getStandard1ValueList() {
    return standard1ValueList;
  }


  
  /**
   * @param standard1ValueList the standard1ValueList to set
   */
  public void setStandard1ValueList(List<CodeAttribute> standard1ValueList) {
    this.standard1ValueList = standard1ValueList;
  }


  
  /**
   * @return the standard2ValueList
   */
  public List<CodeAttribute> getStandard2ValueList() {
    return standard2ValueList;
  }


  
  /**
   * @param standard2ValueList the standard2ValueList to set
   */
  public void setStandard2ValueList(List<CodeAttribute> standard2ValueList) {
    this.standard2ValueList = standard2ValueList;
  }


  


  
  
  /**
   * @return the commodityStandardDetail1Id
   */
  public String getCommodityStandardDetail1Id() {
    return commodityStandardDetail1Id;
  }


  
  /**
   * @param commodityStandardDetail1Id the commodityStandardDetail1Id to set
   */
  public void setCommodityStandardDetail1Id(String commodityStandardDetail1Id) {
    this.commodityStandardDetail1Id = commodityStandardDetail1Id;
  }


  
  /**
   * @return the commodityStandardDetail2Id
   */
  public String getCommodityStandardDetail2Id() {
    return commodityStandardDetail2Id;
  }


  
  /**
   * @param commodityStandardDetail2Id the commodityStandardDetail2Id to set
   */
  public void setCommodityStandardDetail2Id(String commodityStandardDetail2Id) {
    this.commodityStandardDetail2Id = commodityStandardDetail2Id;
  }



  /**
   * @return the saleFlag
   */
  public SaleFlg[] getSaleFlag() {
    return saleFlag;
  }



  
  /**
   * @param saleFlag the saleFlag to set
   */
  public void setSaleFlag(SaleFlg[] saleFlag) {
    this.saleFlag = saleFlag;
  }



  /**
   * @return the header
   */
  public CCommodityHeader getHeader() {
    return header;
  }


  
  /**
   * @param header the header to set
   */
  public void setHeader(CCommodityHeader header) {
    this.header = header;
  }


  /**
   * @return the skuList
   */
  public List<CCommodityDetail> getSkuList() {
    return skuList;
  }

  
  /**
   * @param skuList the skuList to set
   */
  public void setSkuList(List<CCommodityDetail> skuList) {
    this.skuList = skuList;
  }

  /**采购价格*/
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "采购价格")
  private String purchasePriceAll;
  
  /**希望价格*/
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "希望价格")
  private String suggestePriceAll;
  
  /** 销售价格 */
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "销售价格")
  private String unitPriceAll;

  /** 特別価格 */
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "特別価格")
  private String discountPriceAll;

  
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

  private boolean standard1DisplayFlag = true;
  
  private boolean standard2DisplayFlag = true;

  /**
   * @return the standard1DisplayFlag
   */
  public boolean isStandard1DisplayFlag() {
    return standard1DisplayFlag;
  }
  
  /**
   * @param standard1DisplayFlag the standard1DisplayFlag to set
   */
  public void setStandard1DisplayFlag(boolean standard1DisplayFlag) {
    this.standard1DisplayFlag = standard1DisplayFlag;
  }


  
  /**
   * @return the standard2DisplayFlag
   */
  public boolean isStandard2DisplayFlag() {
    return standard2DisplayFlag;
  }


  
  /**
   * @param standard2DisplayFlag the standard2DisplayFlag to set
   */
  public void setStandard2DisplayFlag(boolean standard2DisplayFlag) {
    this.standard2DisplayFlag = standard2DisplayFlag;
  }


  /**
   * @return the purchasePriceAll
   */
  public String getPurchasePriceAll() {
    return purchasePriceAll;
  }
  
  /**
   * @param purchasePriceAll the purchasePriceAll to set
   */
  public void setPurchasePriceAll(String purchasePriceAll) {
    this.purchasePriceAll = purchasePriceAll;
  }
  
  /**
   * @return the suggestePriceAll
   */
  public String getSuggestePriceAll() {
    return suggestePriceAll;
  }

  
  /**
   * @param suggestePriceAll the suggestePriceAll to set
   */
  public void setSuggestePriceAll(String suggestePriceAll) {
    this.suggestePriceAll = suggestePriceAll;
  }

  /**
   * @return the unitPriceAll
   */
  public String getUnitPriceAll() {
    return unitPriceAll;
  }

  
  /**
   * @param unitPriceAll the unitPriceAll to set
   */
  public void setUnitPriceAll(String unitPriceAll) {
    this.unitPriceAll = unitPriceAll;
  }

  /**
   * U1040140:商品SKUのデータモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CCommoditySkuDetailBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String shopCode;

    private String commodityCode;

    @Length(18)
    @AlphaNum2
    @Metadata(name = "代表SKU编号")
    private String representSkuCode;

    @Required
    @Length(50) 
    @Metadata(name = "SKU名称")
    private String   skuName;
    
    @Length(18) 
    @Metadata(name = "SKU编号")
    private String skuCode;     
    
    @Length(8) 
    @Metadata(name = "表示順")
    private String displayOrder;
    
    @Length(13) 
    @JanCode
    @Metadata(name = "JANコード") 
    private String janCode;

    private Long tmallSkuId;
    
    
    @Length(20) 
    @Metadata(name = "规格值1")
    private String   standardDetail1Name;
    
    @Length(20)  
    @Metadata(name = "规格值2")
    private String   standardDetail2Name;
    
    @Length(20) 
    @Metadata(name = "规格值1编号")
    private String   standardDetail1Id;
    
    @Length(20)  
    @Metadata(name = "规格值2编号")
    private String   standardDetail2Id;
    
    @Required
    @Length(9) 
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 6, scale = 2)
    @Metadata(name = "采购价格")
    private String purchasePrice;

    @Length(1)
    @Metadata(name = "定价销售标志", order = 5)
    private String fixedPriceFlag;
    
    @Length(9) 
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 6, scale = 2)
    @Metadata(name = "希望售价")
    private String suggestePrice;
    
    @Length(9)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 6, scale = 2)
    @Metadata(name = "最低售价", order = 30)
    private String minPrice;
    
    @Required
    @Length(9) 
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "官网售价")
    private String unitPrice;    

    @Length(9)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "官网特价")
    private String discountPrice;
    
    @Required
    @Length(9) 
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "淘宝售价")
    private String tmallUnitPrice;  
    
    @Length(9)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "淘宝特价")
    private String tmallDiscountPrice;
    
    @Required
    @Length(8)
    @Quantity
    @Range(min = 0, max = 99999999)
    @Metadata(name = "最小采购数")
    private String minimumOrder; 
    
    @Required
    @Length(8)
    @Quantity
    @Range(min = 0, max = 99999999)
    @Metadata(name = "最大采购数")
    private String maximumOrder; 
    
    @Required
    @Length(8)
    @Quantity
    @Range(min = 0, max = 99999999)
    @Metadata(name = "采购单位数量")
    private String orderMultiple; 
    
    @Length(8)
    @Quantity
    @Range(min = 0, max = 99999999)
    @Metadata(name = "安全库存")
    private String stockThreshold; 
    
    @Required
    @Length(5)
    @Quantity
    @Range(min = 0, max = 99999)
    @Metadata(name = "库存警告数")
    private String stockWarning;     
    
//    @Length(1)
//    @Metadata(name = "销售标志")
//    private String saleFlg; 

    @Length(9) 
    @Required
    @Range(min = 0, max = 99999)
    @Precision(precision = 8, scale = 3)
    @Metadata(name = "重量")
    private String weight;   

    @Length(8)
    @Quantity
    @Range(min = 1, max = 99999999)
    @Metadata(name = "内包装数", order = 35)
    private String innerQuantity;
    
    @Length(10)
    @Metadata(name = "内包装单位", order = 38)
    private String innerQuantityUnit;
    
    @Length(8)
    @Quantity
    @Range(min = 1, max = 99999999)
    @Metadata(name = "均价计算单位(内包装)", order = 36)
    private String innerUnitForPrice;
    
    @Length(9)
    @Currency
    @Precision(precision = 8, scale = 3)
    @Metadata(name = "容量", order = 15)
    private String volume;

    @Length(10)
    @Metadata(name = "容量单位", order = 16)
    private String volumeUnit;
    
    @Length(9)
    @Currency
    @Precision(precision = 8, scale = 3)
    @Metadata(name = "均价计算单位(容量)", order = 34)
    private String volumeUnitForPrice;
    
    @Required
    @Length(2)
    @Metadata(name = "税率区分", order = 35)
    private String taxClass;    
    
    @Length(12)
    @Metadata(name = "长", order = 31)
    private String length;
    
    @Length(12)
    @Metadata(name = "宽", order = 32)
    private String width;
    
    @Length(12)
    @Metadata(name = "高", order = 33)
    private String height;
    
    
    
    @Required
    @Length(10)
    @Metadata(name = "淘宝使用标志", order = 37)
    private String tmallUseFlg;
    
    @Required
    @Length(1)
    @Metadata(name = "官网使用标志", order = 17)
    private String useFlg;
    
    @Length(8)
    @Metadata(name = "箱规", order = 39)
    private String unitBox;
    //2014/4/28 京东WBS对应 ob_李 add start
    @Required
    @Length(1)
    @Metadata(name = "京东使用标志", order = 40)
    private String jdUseFlg;
    //2014/4/28 京东WBS对应 ob_李 add start
    
    @Length(9)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "平均计算成本", order = 41)
    private String averageCost;
    /**
     * @return the minPrice
     */
    public String getMinPrice() {
      return minPrice;
    }
    
    
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
     * @return the tmallUseFlg
     */
    public String getTmallUseFlg() {
      return tmallUseFlg;
    }



    /**
     * @param minPrice the minPrice to set
     */
    public void setMinPrice(String minPrice) {
      this.minPrice = minPrice;
    }

    /**
     * @return the fixedPriceFlag
     */
    public String getFixedPriceFlag() {
      return fixedPriceFlag;
    }
    
    /**
     * @param fixedPriceFlag the fixedPriceFlag to set
     */
    public void setFixedPriceFlag(String fixedPriceFlag) {
      this.fixedPriceFlag = fixedPriceFlag;
    }

    /**
     * @return the taxClass
     */
    public String getTaxClass() {
      return taxClass;
    }
    
    /**
     * @param taxClass the taxClass to set
     */
    public void setTaxClass(String taxClass) {
      this.taxClass = taxClass;
    }
    
    /**
     * @return the volumeUnitForPrice
     */
    public String getVolumeUnitForPrice() {
      return volumeUnitForPrice;
    }

    
    /**
     * @param volumeUnitForPrice the volumeUnitForPrice to set
     */
    public void setVolumeUnitForPrice(String volumeUnitForPrice) {
      this.volumeUnitForPrice = volumeUnitForPrice;
    }

    
    /**
     * @return the innerQuantity
     */
    public String getInnerQuantity() {
      return innerQuantity;
    }

    
    /**
     * @param innerQuantity the innerQuantity to set
     */
    public void setInnerQuantity(String innerQuantity) {
      this.innerQuantity = innerQuantity;
    }

    
    /**
     * @return the innerUnitForPrice
     */
    public String getInnerUnitForPrice() {
      return innerUnitForPrice;
    }

    
    /**
     * @param innerUnitForPrice the innerUnitForPrice to set
     */
    public void setInnerUnitForPrice(String innerUnitForPrice) {
      this.innerUnitForPrice = innerUnitForPrice;
    }

    
    

    
    /**
     * @param tmallUseFlg the tmallUseFlg to set
     */
    public void setTmallUseFlg(String tmallUseFlg) {
      this.tmallUseFlg = tmallUseFlg;
    }

    
    /**
     * @return the innerQuantityUnit
     */
    public String getInnerQuantityUnit() {
      return innerQuantityUnit;
    }

    
    /**
     * @param innerQuantityUnit the innerQuantityUnit to set
     */
    public void setInnerQuantityUnit(String innerQuantityUnit) {
      this.innerQuantityUnit = innerQuantityUnit;
    }

    /**
     * @return the tmallSkuId
     */
    public Long getTmallSkuId() {
      return tmallSkuId;
    }
    
    /**
     * @param tmallSkuId the tmallSkuId to set
     */
    public void setTmallSkuId(Long tmallSkuId) {
      this.tmallSkuId = tmallSkuId;
    }


    /**
     * @return the volume
     */
    public String getVolume() {
      return volume;
    }

    
    /**
     * @param volume the volume to set
     */
    public void setVolume(String volume) {
      this.volume = volume;
    }

    /**
     * @return the volumeUnit
     */
    public String getVolumeUnit() {
      return volumeUnit;
    }

    /**
     * @param volumeUnit the volumeUnit to set
     */
    public void setVolumeUnit(String volumeUnit) {
      this.volumeUnit = volumeUnit;
    }

    /**
     * @return the suggestePrice
     */
    public String getSuggestePrice() {
      return suggestePrice;
    }
    
    /**
     * @param suggestePrice the suggestePrice to set
     */
    public void setSuggestePrice(String suggestePrice) {
      this.suggestePrice = suggestePrice;
    }




    /**
     * @return the minimumOrder
     */
    public String getMinimumOrder() {
      return minimumOrder;
    }



    
    /**
     * @param minimumOrder the minimumOrder to set
     */
    public void setMinimumOrder(String minimumOrder) {
      this.minimumOrder = minimumOrder;
    }

    
    

    
    /**
     * @return the standardDetail1Id
     */
    public String getStandardDetail1Id() {
      return standardDetail1Id;
    }


    
    /**
     * @param standardDetail1Id the standardDetail1Id to set
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
     * @param standardDetail2Id the standardDetail2Id to set
     */
    public void setStandardDetail2Id(String standardDetail2Id) {
      this.standardDetail2Id = standardDetail2Id;
    }


    /**
     * @return the maximumOrder
     */
    public String getMaximumOrder() {
      return maximumOrder;
    }



    
    /**
     * @param maximumOrder the maximumOrder to set
     */
    public void setMaximumOrder(String maximumOrder) {
      this.maximumOrder = maximumOrder;
    }



    
    /**
     * @return the orderMultiple
     */
    public String getOrderMultiple() {
      return orderMultiple;
    }



    
    /**
     * @param orderMultiple the orderMultiple to set
     */
    public void setOrderMultiple(String orderMultiple) {
      this.orderMultiple = orderMultiple;
    }



//    /**
//     * @return the saleFlg
//     */
//    public String getSaleFlg() {
//      return saleFlg;
//    }
//
//
//    
//    /**
//     * @param saleFlg the saleFlg to set
//     */
//    public void setSaleFlg(String saleFlg) {
//      this.saleFlg = saleFlg;
//    }


    /**
     * @return the stockWarning
     */
    public String getStockWarning() {
      return stockWarning;
    }

    
    /**
     * @param stockWarning the stockWarning to set
     */
    public void setStockWarning(String stockWarning) {
      this.stockWarning = stockWarning;
    }

    private Date updateDatetime;



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
     * @param representSkuCode the representSkuCode to set
     */
    public void setRepresentSkuCode(String representSkuCode) {
      this.representSkuCode = representSkuCode;
    }

    /**
     * @return the representSkuCode
     */
    public String getRepresentSkuCode() {
      return representSkuCode;
    }

    /**
     * @param skuName the skuName to set
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
     * @param purchasePrice the purchasePrice to set
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
     * @param tmallDiscountPrice the tmallDiscountPrice to set
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
     * @param tmallUnitPrice the tmallUnitPrice to set
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
     * @param skuCode the skuCode to set
     */
    public void setSkuCode(String skuCode) {
      this.skuCode = skuCode;
    }

    /**
     * @return the skuCode
     */
    public String getSkuCode() {
      return skuCode;
    }

    /**
     * @param displayOrder the displayOrder to set
     */
    public void setDisplayOrder(String displayOrder) {
      this.displayOrder = displayOrder;
    }

    /**
     * @return the displayOrder
     */
    public String getDisplayOrder() {
      return displayOrder;
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


    
    /**
     * @return the length
     */
    public String getLength() {
      return length;
    }


    
    /**
     * @param length the length to set
     */
    public void setLength(String length) {
      this.length = length;
    }


    
    /**
     * @return the width
     */
    public String getWidth() {
      return width;
    }


    
    /**
     * @param width the width to set
     */
    public void setWidth(String width) {
      this.width = width;
    }


    
    /**
     * @return the height
     */
    public String getHeight() {
      return height;
    }


    
    /**
     * @param height the height to set
     */
    public void setHeight(String height) {
      this.height = height;
    }


    
    /**
     * @return the unitBox
     */
    public String getUnitBox() {
      return unitBox;
    }


    
    /**
     * @param unitBox the unitBox to set
     */
    public void setUnitBox(String unitBox) {
      this.unitBox = unitBox;
    }


    /**
     * @return the jdUseFlg
     */
    public String getJdUseFlg() {
      return jdUseFlg;
    }


    /**
     * @param jdUseFlg the jdUseFlg to set
     */
    public void setJdUseFlg(String jdUseFlg) {
      this.jdUseFlg = jdUseFlg;
    }


    
    /**
     * @return the averagePrice
     */
    public String getAverageCost() {
      return averageCost;
    }


    
    /**
     * @param averagePrice the averagePrice to set
     */
    public void setAverageCost(String averageCost) {
      this.averageCost = averageCost;
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
   * editを取得します。
   * 
   * @return edit
   */
  public CCommoditySkuDetailBean getEdit() {
    return edit;
  }

  /**
   * listを取得します。
   * 
   * @return list
   */
  public List<CCommoditySkuDetailBean> getList() {
    return list;
  }

  /**
   * editを設定します。
   * 
   * @param edit
   *          edit
   */
  public void setEdit(CCommoditySkuDetailBean edit) {
    this.edit = edit;
  }

  /**
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<CCommoditySkuDetailBean> list) {
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
    this.setDiscountPriceAll(reqparam.get("discountPriceAll"));
    this.setCommodityStandardNameValue(reqparam.get("commodityStandardNameValue"));
    this.setCommodityStandard1Name(reqparam.get("commodityStandard1Name"));
    this.setCommodityStandard2Name(reqparam.get("commodityStandard2Name"));
    this.setUnitPriceAll(reqparam.get("unitPriceAll"));
    this.setSuggestePriceAll(reqparam.get("suggestePriceAll"));
    this.setPurchasePriceAll(reqparam.get("purchasePriceAll"));
    //add by os014 2012-01-31 begin
    this.setCommodityStandardDetail1Id(reqparam.get("commodityStandardDetail1Id"));
    this.setStandardDetail1Name(reqparam.get("standardDetail1Name"));
    this.setCommodityStandardDetail2Id(reqparam.get("commodityStandardDetail2Id"));
    this.setStandardDetail2Name(reqparam.get("standardDetail2Name"));
    //add by os014 2012-01-31 end
    
    String[] listKey = new String[] {};
    if (StringUtil.hasValue(this.getEscapeCommodityStandardNameValue())) {
      if (this.getEscapeCommodityStandardNameValue().equals("0")) {
        listKey = new String[] {
            "skuCode", "unitPrice", "discountPrice", "reservationPrice", "changeUnitPrice", "janCode", "displayOrder",
            "reservationLimit", "oneshotReservationLimit", "stockThreshold"
        };
      } else if (this.getEscapeCommodityStandardNameValue().equals("1")) {
        listKey = new String[] {
            "skuCode", "unitPrice", "discountPrice", "reservationPrice", "changeUnitPrice", "janCode", "standardDetail1Name",
            "displayOrder", "reservationLimit", "oneshotReservationLimit", "stockThreshold"
        };
      } else if (this.getEscapeCommodityStandardNameValue().equals("2")) {
        listKey = new String[] {
            "skuCode", "unitPrice", "discountPrice", "reservationPrice", "changeUnitPrice", "janCode", "standardDetail1Name",
            "standardDetail2Name", "displayOrder", "reservationLimit", "oneshotReservationLimit", "stockThreshold"
        };
      } 
    }else {
      
      if(StringUtil.isNullOrEmpty(reqparam.get("commodityStandardDetail1Id")) && StringUtil.isNullOrEmpty(reqparam.get("commodityStandardDetail2Id"))){
        listKey = new String[] {
            "skuCode","skuName","purchasePrice","suggestePrice",
            "unitPrice","tmallUnitPrice","discountPrice","tmallDiscountPrice","minimumOrder","maximumOrder",
            "orderMultiple","stockWarning","volume","volumeUnit","weight","minPrice","fixedPriceFlag",
            "innerQuantity","innerQuantityUnit","innerUnitForPrice","volumeUnitForPrice","unitBox","tmallUseFlg","useFlg","taxClass"
            //2014/4/28 京东WBS对应 ob_李 add start
            ,"jdUseFlg"
            //2014/4/28 京东WBS对应 ob_李 add end
        };
      }
      if(StringUtil.isNullOrEmpty(reqparam.get("commodityStandardDetail1Id")) && !StringUtil.isNullOrEmpty(reqparam.get("commodityStandardDetail2Id"))){
        listKey = new String[] {
            "skuCode","skuName","standardDetail2Id","purchasePrice","suggestePrice",
            "unitPrice","tmallUnitPrice","discountPrice","tmallDiscountPrice","minimumOrder","maximumOrder",
            "orderMultiple","stockWarning","volume","volumeUnit","weight","minPrice","fixedPriceFlag",
            "innerQuantity","innerQuantityUnit","innerUnitForPrice","volumeUnitForPrice","unitBox","tmallUseFlg","useFlg","taxClass"
            //2014/4/28 京东WBS对应 ob_李 add start
            ,"jdUseFlg"
            //2014/4/28 京东WBS对应 ob_李 add end
        };
      }
      if(!StringUtil.isNullOrEmpty(reqparam.get("commodityStandardDetail1Id")) && StringUtil.isNullOrEmpty(reqparam.get("commodityStandardDetail2Id"))){
        listKey = new String[] {
            "skuCode","skuName","standardDetail1Id","purchasePrice","suggestePrice",
            "unitPrice","tmallUnitPrice","discountPrice","tmallDiscountPrice","minimumOrder","maximumOrder",
            "orderMultiple","stockWarning","volume","volumeUnit","weight","minPrice","fixedPriceFlag",
            "innerQuantity","innerQuantityUnit","innerUnitForPrice","volumeUnitForPrice","unitBox","tmallUseFlg","useFlg","taxClass"
            //2014/4/28 京东WBS对应 ob_李 add start
            ,"jdUseFlg"
            //2014/4/28 京东WBS对应 ob_李 add end
        };
      }
      if(!StringUtil.isNullOrEmpty(reqparam.get("commodityStandardDetail1Id")) && !StringUtil.isNullOrEmpty(reqparam.get("commodityStandardDetail2Id"))){
        listKey = new String[] {
            "skuCode","skuName","standardDetail1Id","standardDetail2Id","purchasePrice","suggestePrice",
            "unitPrice","tmallUnitPrice","discountPrice","tmallDiscountPrice","minimumOrder","maximumOrder",
            "orderMultiple","stockWarning","volume","volumeUnit","weight","minPrice","fixedPriceFlag",
            "innerQuantity","innerQuantityUnit","innerUnitForPrice","volumeUnitForPrice","unitBox","tmallUseFlg","useFlg","taxClass"
            //2014/4/28 京东WBS对应 ob_李 add start
            ,"jdUseFlg"
            //2014/4/28 京东WBS对应 ob_李 add end
        };
      }
    }
    
    for (CCommoditySkuDetailBean sku : list) {
      Map<String, String> map = reqparam.getListDataWithKey(sku.getSkuCode(), listKey);
      sku.setSkuCode(map.get("skuCode")); 
      sku.setSkuName(StringUtil.parse(map.get("skuName")));
      sku.setUnitPrice(map.get("unitPrice"));      
      sku.setDiscountPrice(map.get("discountPrice"));
      sku.setJanCode(map.get("janCode"));      
      sku.setVolume(map.get("volume"));
      sku.setVolumeUnit(map.get("volumeUnit"));      
      sku.setWeight(map.get("weight"));
      sku.setPurchasePrice(map.get("purchasePrice"));      
      sku.setSuggestePrice(map.get("suggestePrice"));
      sku.setTmallDiscountPrice(map.get("tmallDiscountPrice"));      
      sku.setTmallUnitPrice(map.get("tmallUnitPrice"));
      sku.setMinimumOrder(map.get("minimumOrder"));      
      sku.setMaximumOrder(map.get("maximumOrder"));
      sku.setOrderMultiple(map.get("orderMultiple"));      
      sku.setStockWarning(map.get("stockWarning"));
      sku.setStandardDetail1Name(map.get("standardDetail1Name"));      
      sku.setStandardDetail1Id(map.get("standardDetail1Id"));
      sku.setStandardDetail2Name(map.get("standardDetail2Name"));      
      sku.setStandardDetail2Id(map.get("standardDetail2Id"));
      sku.setDisplayOrder(map.get("displayOrder"));      
      sku.setStockThreshold(map.get("stockThreshold"));
      sku.setMinPrice(map.get("minPrice"));
      sku.setFixedPriceFlag(map.get("fixedPriceFlag"));
      sku.setInnerQuantity(map.get("innerQuantity"));
      sku.setInnerQuantityUnit(map.get("innerQuantityUnit"));
      sku.setInnerUnitForPrice(map.get("innerUnitForPrice"));
      sku.setVolumeUnitForPrice(map.get("volumeUnitForPrice"));
      sku.setUnitBox(map.get("unitBox"));
      sku.setTmallUseFlg(map.get("tmallUseFlg"));
      sku.setUseFlg(map.get("useFlg"));
      //2014/4/28 京东WBS对应 ob_李 add start
      sku.setJdUseFlg(map.get("jdUseFlg"));
      sku.setAverageCost(reqparam.get("averageCost"));
      //2014/4/28 京东WBS对应 ob_李 add start
      sku.setTaxClass(map.get("taxClass"));
    }

    edit.setSkuCode(reqparam.get("registerSkuCode"));
    edit.setUnitPrice(reqparam.get("registerUnitPrice"));
    edit.setDiscountPrice(reqparam.get("registerDiscountPrice"));
    edit.setJanCode(reqparam.get("registerJanCode"));
    edit.setStandardDetail1Name(reqparam.get("registerStandardDetail1Name"));
    edit.setStandardDetail2Name(reqparam.get("registerStandardDetail2Name"));
    edit.setDisplayOrder(reqparam.get("registerDisplayOrder"));
    edit.setStockThreshold(reqparam.get("registerStockThreshold"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040240";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.TmallCommoditySkuBean.0");
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

}
