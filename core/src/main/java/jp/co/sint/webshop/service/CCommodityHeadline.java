package jp.co.sint.webshop.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CCommodityHeadline implements Serializable {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String commodityCode;

  private String commodityName;

  private String commodityNameEn;

  private String representSkuCode;

  private BigDecimal representSkuUnitPrice;

  private String commodityDescription1;

  private String commodityDescription2;

  private String commodityDescription3;

  private String commodityDescriptionShort;

  private String commoditySearchWords;

  private Date saleStartDatetime;

  private Date saleEndDatetime;

  private Date discountPriceStartDatetime;

  private Date discountPriceEndDatetime;

  private String standard1Id;

  private String standard1Name;

  private String standard2Id;

  private String standard2Name;

  private Long saleFlgEc;

  private Long saleFlgTmall;

  private Long returnFlg;

  private String warningFlag;

  private Long leadTime;

  private String saleFlag;

  private String specFlag;

  private String brandCode;

  private Long tmallCommodityId;

  private Long tmallRepresentSkuPrice;

  private Long tmallCategoryId;

  private String supplierCode;

  private String buyerCode;

  private String taxCode;

  private String originalPlace;

  private String ingredientName1;

  private String ingredientVal1;

  private String ingredientName2;

  private String ingredientVal2;

  private String ingredientName3;

  private String ingredientVal3;

  private String ingredientName4;

  private String ingredientVal4;

  private String ingredientName5;

  private String ingredientVal5;

  private String ingredientName6;

  private String ingredientVal6;

  private String ingredientName7;

  private String ingredientVal7;

  private String ingredientName8;

  private String ingredientVal8;

  private String ingredientName9;

  private String ingredientVal9;

  private String ingredientName10;

  private String ingredientVal10;

  private String ingredientName11;

  private String ingredientVal11;

  private String ingredientName12;

  private String ingredientVal12;

  private String ingredientName13;

  private String ingredientVal13;

  private String ingredientName14;

  private String ingredientVal14;

  private String ingredientName15;

  private String ingredientVal15;

  private String material1;

  private String material2;

  private String material3;

  private String material4;

  private String material5;

  private String material6;

  private String material7;

  private String material8;

  private String material9;

  private String material10;

  private String material11;

  private String material12;

  private String material13;

  private String material14;

  private String material15;

  private Long shelfLifeFlag;

  private Long shelfLifeDays;

  private Long quantityPerUnit;

  private Long bigFlag;

  private String skuCode;

  private String skuName;

  private Long fixedpriceflag;

  private Long suggestePrice;

  private BigDecimal purchasePrice;

  private BigDecimal unitPrice;

  private BigDecimal discountPrice;

  private String standard1ValueId;

  private String standard1ValueText;

  private String standard2ValueId;

  private String standard2ValueText;

  private BigDecimal weight;

  private Long volume;

  private String volumeUnit;

  private Long useFlg;

  private Long minimumOrder;

  private Long maximumOrder;

  private Long orderMultiple;

  private Long stockWarning;
  
  private Long tmallSkuId;
  
  private BigDecimal tmallUnitPrice;
  
  private BigDecimal tmallDiscountPrice;

  
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
   * @return the commodityNameEn
   */
  public String getCommodityNameEn() {
    return commodityNameEn;
  }

  
  /**
   * @param commodityNameEn the commodityNameEn to set
   */
  public void setCommodityNameEn(String commodityNameEn) {
    this.commodityNameEn = commodityNameEn;
  }

  
  /**
   * @return the representSkuCode
   */
  public String getRepresentSkuCode() {
    return representSkuCode;
  }

  
  /**
   * @param representSkuCode the representSkuCode to set
   */
  public void setRepresentSkuCode(String representSkuCode) {
    this.representSkuCode = representSkuCode;
  }

  
  /**
   * @return the representSkuUnitPrice
   */
  public BigDecimal getRepresentSkuUnitPrice() {
    return representSkuUnitPrice;
  }

  
  /**
   * @param representSkuUnitPrice the representSkuUnitPrice to set
   */
  public void setRepresentSkuUnitPrice(BigDecimal representSkuUnitPrice) {
    this.representSkuUnitPrice = representSkuUnitPrice;
  }

  
  /**
   * @return the commoditySearchWords
   */
  public String getCommoditySearchWords() {
    return commoditySearchWords;
  }

  
  /**
   * @param commoditySearchWords the commoditySearchWords to set
   */
  public void setCommoditySearchWords(String commoditySearchWords) {
    this.commoditySearchWords = commoditySearchWords;
  }

  
  /**
   * @return the saleStartDatetime
   */
  public Date getSaleStartDatetime() {
    return saleStartDatetime;
  }

  
  /**
   * @param saleStartDatetime the saleStartDatetime to set
   */
  public void setSaleStartDatetime(Date saleStartDatetime) {
    this.saleStartDatetime = saleStartDatetime;
  }

  
  /**
   * @return the saleEndDatetime
   */
  public Date getSaleEndDatetime() {
    return saleEndDatetime;
  }

  
  /**
   * @param saleEndDatetime the saleEndDatetime to set
   */
  public void setSaleEndDatetime(Date saleEndDatetime) {
    this.saleEndDatetime = saleEndDatetime;
  }

  
  /**
   * @return the discountPriceStartDatetime
   */
  public Date getDiscountPriceStartDatetime() {
    return discountPriceStartDatetime;
  }

  
  /**
   * @param discountPriceStartDatetime the discountPriceStartDatetime to set
   */
  public void setDiscountPriceStartDatetime(Date discountPriceStartDatetime) {
    this.discountPriceStartDatetime = discountPriceStartDatetime;
  }

  
  /**
   * @return the discountPriceEndDatetime
   */
  public Date getDiscountPriceEndDatetime() {
    return discountPriceEndDatetime;
  }

  
  /**
   * @param discountPriceEndDatetime the discountPriceEndDatetime to set
   */
  public void setDiscountPriceEndDatetime(Date discountPriceEndDatetime) {
    this.discountPriceEndDatetime = discountPriceEndDatetime;
  }

  
  /**
   * @return the standard1Id
   */
  public String getStandard1Id() {
    return standard1Id;
  }

  
  /**
   * @param standard1Id the standard1Id to set
   */
  public void setStandard1Id(String standard1Id) {
    this.standard1Id = standard1Id;
  }

  
  /**
   * @return the standard1Name
   */
  public String getStandard1Name() {
    return standard1Name;
  }

  
  /**
   * @param standard1Name the standard1Name to set
   */
  public void setStandard1Name(String standard1Name) {
    this.standard1Name = standard1Name;
  }

  
  /**
   * @return the standard2Id
   */
  public String getStandard2Id() {
    return standard2Id;
  }

  
  /**
   * @param standard2Id the standard2Id to set
   */
  public void setStandard2Id(String standard2Id) {
    this.standard2Id = standard2Id;
  }

  
  /**
   * @return the standard2Name
   */
  public String getStandard2Name() {
    return standard2Name;
  }

  
  /**
   * @param standard2Name the standard2Name to set
   */
  public void setStandard2Name(String standard2Name) {
    this.standard2Name = standard2Name;
  }

  
  /**
   * @return the returnFlg
   */
  public Long getReturnFlg() {
    return returnFlg;
  }

  
  /**
   * @param returnFlg the returnFlg to set
   */
  public void setReturnFlg(Long returnFlg) {
    this.returnFlg = returnFlg;
  }

  
  /**
   * @return the warningFlag
   */
  public String getWarningFlag() {
    return warningFlag;
  }

  
  /**
   * @param warningFlag the warningFlag to set
   */
  public void setWarningFlag(String warningFlag) {
    this.warningFlag = warningFlag;
  }

  
  /**
   * @return the leadTime
   */
  public Long getLeadTime() {
    return leadTime;
  }

  
  /**
   * @param leadTime the leadTime to set
   */
  public void setLeadTime(Long leadTime) {
    this.leadTime = leadTime;
  }

  
  /**
   * @return the saleFlag
   */
  public String getSaleFlag() {
    return saleFlag;
  }

  
  /**
   * @param saleFlag the saleFlag to set
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
   * @param specFlag the specFlag to set
   */
  public void setSpecFlag(String specFlag) {
    this.specFlag = specFlag;
  }

  
  /**
   * @return the brandCode
   */
  public String getBrandCode() {
    return brandCode;
  }

  
  /**
   * @param brandCode the brandCode to set
   */
  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  
  /**
   * @return the buyerCode
   */
  public String getBuyerCode() {
    return buyerCode;
  }

  
  /**
   * @param buyerCode the buyerCode to set
   */
  public void setBuyerCode(String buyerCode) {
    this.buyerCode = buyerCode;
  }

  
  /**
   * @return the taxCode
   */
  public String getTaxCode() {
    return taxCode;
  }

  
  /**
   * @param taxCode the taxCode to set
   */
  public void setTaxCode(String taxCode) {
    this.taxCode = taxCode;
  }

  
  /**
   * @return the supplierCode
   */
  public String getSupplierCode() {
    return supplierCode;
  }

  
  /**
   * @param supplierCode the supplierCode to set
   */
  public void setSupplierCode(String supplierCode) {
    this.supplierCode = supplierCode;
  }

  
  /**
   * @return the originalPlace
   */
  public String getOriginalPlace() {
    return originalPlace;
  }

  
  /**
   * @param originalPlace the originalPlace to set
   */
  public void setOriginalPlace(String originalPlace) {
    this.originalPlace = originalPlace;
  }

  
  /**
   * @return the bigFlag
   */
  public Long getBigFlag() {
    return bigFlag;
  }

  
  /**
   * @param bigFlag the bigFlag to set
   */
  public void setBigFlag(Long bigFlag) {
    this.bigFlag = bigFlag;
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
   * @return the skuName
   */
  public String getSkuName() {
    return skuName;
  }

  
  /**
   * @param skuName the skuName to set
   */
  public void setSkuName(String skuName) {
    this.skuName = skuName;
  }

  
  /**
   * @return the purchasePrice
   */
  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }

  
  /**
   * @param purchasePrice the purchasePrice to set
   */
  public void setPurchasePrice(BigDecimal purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  
  /**
   * @return the unitPrice
   */
  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  
  /**
   * @param unitPrice the unitPrice to set
   */
  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  
  /**
   * @return the disCountPrice
   */
  public BigDecimal getDiscountPrice() {
    return discountPrice;
  }

  
  /**
   * @param disCountPrice the disCountPrice to set
   */
  public void setDiscountPrice(BigDecimal discountPrice) {
    this.discountPrice = discountPrice;
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
   * @return the useFlg
   */
  public Long getUseFlg() {
    return useFlg;
  }

  
  /**
   * @param useFlg the useFlg to set
   */
  public void setUseFlg(Long useFlg) {
    this.useFlg = useFlg;
  }

  
  /**
   * @return the minimumOrder
   */
  public Long getMinimumOrder() {
    return minimumOrder;
  }

  
  /**
   * @param minimumOrder the minimumOrder to set
   */
  public void setMinimumOrder(Long minimumOrder) {
    this.minimumOrder = minimumOrder;
  }

  
  /**
   * @return the maximumOrder
   */
  public Long getMaximumOrder() {
    return maximumOrder;
  }

  
  /**
   * @param maximumOrder the maximumOrder to set
   */
  public void setMaximumOrder(Long maximumOrder) {
    this.maximumOrder = maximumOrder;
  }

  
  /**
   * @return the orderMultiple
   */
  public Long getOrderMultiple() {
    return orderMultiple;
  }

  
  /**
   * @param orderMultiple the orderMultiple to set
   */
  public void setOrderMultiple(Long orderMultiple) {
    this.orderMultiple = orderMultiple;
  }

  
  /**
   * @return the stockWarning
   */
  public Long getStockWarning() {
    return stockWarning;
  }

  
  /**
   * @param stockWarning the stockWarning to set
   */
  public void setStockWarning(Long stockWarning) {
    this.stockWarning = stockWarning;
  }


  
  /**
   * @return the commodityDescription1
   */
  public String getCommodityDescription1() {
    return commodityDescription1;
  }


  
  /**
   * @param commodityDescription1 the commodityDescription1 to set
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
   * @param commodityDescription2 the commodityDescription2 to set
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
   * @param commodityDescription3 the commodityDescription3 to set
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
   * @param commodityDescriptionShort the commodityDescriptionShort to set
   */
  public void setCommodityDescriptionShort(String commodityDescriptionShort) {
    this.commodityDescriptionShort = commodityDescriptionShort;
  }


  
  /**
   * @return the saleFlgEc
   */
  public Long getSaleFlgEc() {
    return saleFlgEc;
  }


  
  /**
   * @param saleFlgEc the saleFlgEc to set
   */
  public void setSaleFlgEc(Long saleFlgEc) {
    this.saleFlgEc = saleFlgEc;
  }


  
  /**
   * @return the saleFlgTmall
   */
  public Long getSaleFlgTmall() {
    return saleFlgTmall;
  }


  
  /**
   * @param saleFlgTmall the saleFlgTmall to set
   */
  public void setSaleFlgTmall(Long saleFlgTmall) {
    this.saleFlgTmall = saleFlgTmall;
  }


  
  /**
   * @return the tmallCommodityId
   */
  public Long getTmallCommodityId() {
    return tmallCommodityId;
  }


  
  /**
   * @param tmallCommodityId the tmallCommodityId to set
   */
  public void setTmallCommodityId(Long tmallCommodityId) {
    this.tmallCommodityId = tmallCommodityId;
  }


  
  /**
   * @return the tmallRepresentSkuPrice
   */
  public Long getTmallRepresentSkuPrice() {
    return tmallRepresentSkuPrice;
  }


  
  /**
   * @param tmallRepresentSkuPrice the tmallRepresentSkuPrice to set
   */
  public void setTmallRepresentSkuPrice(Long tmallRepresentSkuPrice) {
    this.tmallRepresentSkuPrice = tmallRepresentSkuPrice;
  }


  
  /**
   * @return the tmallCategoryId
   */
  public Long getTmallCategoryId() {
    return tmallCategoryId;
  }


  
  /**
   * @param tmallCategoryId the tmallCategoryId to set
   */
  public void setTmallCategoryId(Long tmallCategoryId) {
    this.tmallCategoryId = tmallCategoryId;
  }


  
  /**
   * @return the ingredientName1
   */
  public String getIngredientName1() {
    return ingredientName1;
  }


  
  /**
   * @param ingredientName1 the ingredientName1 to set
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
   * @param ingredientVal1 the ingredientVal1 to set
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
   * @param ingredientName2 the ingredientName2 to set
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
   * @param ingredientVal2 the ingredientVal2 to set
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
   * @param ingredientName3 the ingredientName3 to set
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
   * @param ingredientVal3 the ingredientVal3 to set
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
   * @param ingredientName4 the ingredientName4 to set
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
   * @param ingredientVal4 the ingredientVal4 to set
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
   * @param ingredientName5 the ingredientName5 to set
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
   * @param ingredientVal5 the ingredientVal5 to set
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
   * @param ingredientName6 the ingredientName6 to set
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
   * @param ingredientVal6 the ingredientVal6 to set
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
   * @param ingredientName7 the ingredientName7 to set
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
   * @param ingredientVal7 the ingredientVal7 to set
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
   * @param ingredientName8 the ingredientName8 to set
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
   * @param ingredientVal8 the ingredientVal8 to set
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
   * @param ingredientName9 the ingredientName9 to set
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
   * @param ingredientVal9 the ingredientVal9 to set
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
   * @param ingredientName10 the ingredientName10 to set
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
   * @param ingredientVal10 the ingredientVal10 to set
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
   * @param ingredientName11 the ingredientName11 to set
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
   * @param ingredientVal11 the ingredientVal11 to set
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
   * @param ingredientName12 the ingredientName12 to set
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
   * @param ingredientVal12 the ingredientVal12 to set
   */
  public void setIngredientVal12(String ingredientVal12) {
    this.ingredientVal12 = ingredientVal12;
  }


  
  /**
   * @return the ingredientName13
   */
  public String getIngredientName13() {
    return ingredientName13;
  }


  
  /**
   * @param ingredientName13 the ingredientName13 to set
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
   * @param ingredientVal13 the ingredientVal13 to set
   */
  public void setIngredientVal13(String ingredientVal13) {
    this.ingredientVal13 = ingredientVal13;
  }


  
  /**
   * @return the ingredientName14
   */
  public String getIngredientName14() {
    return ingredientName14;
  }


  
  /**
   * @param ingredientName14 the ingredientName14 to set
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
   * @param ingredientVal14 the ingredientVal14 to set
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
   * @param ingredientName15 the ingredientName15 to set
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
   * @param ingredientVal15 the ingredientVal15 to set
   */
  public void setIngredientVal15(String ingredientVal15) {
    this.ingredientVal15 = ingredientVal15;
  }


  
  /**
   * @return the material1
   */
  public String getMaterial1() {
    return material1;
  }


  
  /**
   * @param material1 the material1 to set
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
   * @param material2 the material2 to set
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
   * @param material3 the material3 to set
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
   * @param material4 the material4 to set
   */
  public void setMaterial4(String material4) {
    this.material4 = material4;
  }


  
  /**
   * @return the material5
   */
  public String getMaterial5() {
    return material5;
  }


  
  /**
   * @param material5 the material5 to set
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
   * @param material6 the material6 to set
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
   * @param material7 the material7 to set
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
   * @param material8 the material8 to set
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
   * @param material9 the material9 to set
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
   * @param material10 the material10 to set
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
   * @param material11 the material11 to set
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
   * @param material12 the material12 to set
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
   * @param material13 the material13 to set
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
   * @param material14 the material14 to set
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
   * @param material15 the material15 to set
   */
  public void setMaterial15(String material15) {
    this.material15 = material15;
  }


  
  /**
   * @return the shelfLifeFlag
   */
  public Long getShelfLifeFlag() {
    return shelfLifeFlag;
  }


  
  /**
   * @param shelfLifeFlag the shelfLifeFlag to set
   */
  public void setShelfLifeFlag(Long shelfLifeFlag) {
    this.shelfLifeFlag = shelfLifeFlag;
  }


  
  /**
   * @return the shelfLifeDays
   */
  public Long getShelfLifeDays() {
    return shelfLifeDays;
  }


  
  /**
   * @param shelfLifeDays the shelfLifeDays to set
   */
  public void setShelfLifeDays(Long shelfLifeDays) {
    this.shelfLifeDays = shelfLifeDays;
  }


  
  /**
   * @return the quantityPerUnit
   */
  public Long getQuantityPerUnit() {
    return quantityPerUnit;
  }


  
  /**
   * @param quantityPerUnit the quantityPerUnit to set
   */
  public void setQuantityPerUnit(Long quantityPerUnit) {
    this.quantityPerUnit = quantityPerUnit;
  }


  
  /**
   * @return the fixedpriceflag
   */
  public Long getFixedpriceflag() {
    return fixedpriceflag;
  }


  
  /**
   * @param fixedpriceflag the fixedpriceflag to set
   */
  public void setFixedpriceflag(Long fixedpriceflag) {
    this.fixedpriceflag = fixedpriceflag;
  }


  
  /**
   * @return the suggestePrice
   */
  public Long getSuggestePrice() {
    return suggestePrice;
  }


  
  /**
   * @param suggestePrice the suggestePrice to set
   */
  public void setSuggestePrice(Long suggestePrice) {
    this.suggestePrice = suggestePrice;
  }


  
  /**
   * @return the standard1ValueId
   */
  public String getStandard1ValueId() {
    return standard1ValueId;
  }


  
  /**
   * @param standard1ValueId the standard1ValueId to set
   */
  public void setStandard1ValueId(String standard1ValueId) {
    this.standard1ValueId = standard1ValueId;
  }


  
  /**
   * @return the standard1ValueText
   */
  public String getStandard1ValueText() {
    return standard1ValueText;
  }


  
  /**
   * @param standard1ValueText the standard1ValueText to set
   */
  public void setStandard1ValueText(String standard1ValueText) {
    this.standard1ValueText = standard1ValueText;
  }


  
  /**
   * @return the standard2ValueId
   */
  public String getStandard2ValueId() {
    return standard2ValueId;
  }


  
  /**
   * @param standard2ValueId the standard2ValueId to set
   */
  public void setStandard2ValueId(String standard2ValueId) {
    this.standard2ValueId = standard2ValueId;
  }


  
  /**
   * @return the standard2ValueText
   */
  public String getStandard2ValueText() {
    return standard2ValueText;
  }


  
  /**
   * @param standard2ValueText the standard2ValueText to set
   */
  public void setStandard2ValueText(String standard2ValueText) {
    this.standard2ValueText = standard2ValueText;
  }


  
  /**
   * @return the volume
   */
  public Long getVolume() {
    return volume;
  }


  
  /**
   * @param volume the volume to set
   */
  public void setVolume(Long volume) {
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
   * @return the tmallUnitPrice
   */
  public BigDecimal getTmallUnitPrice() {
    return tmallUnitPrice;
  }


  
  /**
   * @param tmallUnitPrice the tmallUnitPrice to set
   */
  public void setTmallUnitPrice(BigDecimal tmallUnitPrice) {
    this.tmallUnitPrice = tmallUnitPrice;
  }


  
  /**
   * @return the tmallDiscountPrice
   */
  public BigDecimal getTmallDiscountPrice() {
    return tmallDiscountPrice;
  }


  
  /**
   * @param tmallDiscountPrice the tmallDiscountPrice to set
   */
  public void setTmallDiscountPrice(BigDecimal tmallDiscountPrice) {
    this.tmallDiscountPrice = tmallDiscountPrice;
  }
}
