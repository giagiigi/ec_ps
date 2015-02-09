package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;

/**
 * @author System Integrator Corp.
 */
public class BrandData implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;
  
  private String shopCode;

  /** 品牌编号 */
  private String brandCode;

  /** 品牌名称 */
  private String brandName;
  /** 品牌名称 */
  private String brandEnglishName;  
  /** 品牌名称 */
  private String brandJapaneseName;
  /** 品牌ABBR名 */ 
  private String brandNameAbbr;

  /** 品牌说明 */ 
  private String brandDescription;
  /** 品牌说明 */ 
  private String brandDescriptionEn;
  /** 品牌说明 */ 
  private String brandDescriptionJp;
  
  private Long commodityCount;
  
  private Long commodityPopularRank;

  
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
   * @return the brandName
   */
  public String getBrandName() {
    return brandName;
  }

  
  /**
   * @param brandName the brandName to set
   */
  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  
  /**
   * @return the brandNameAbbr
   */
  public String getBrandNameAbbr() {
    return brandNameAbbr;
  }

  
  /**
   * @param brandNameAbbr the brandNameAbbr to set
   */
  public void setBrandNameAbbr(String brandNameAbbr) {
    this.brandNameAbbr = brandNameAbbr;
  }

  
  /**
   * @return the brandDescription
   */
  public String getBrandDescription() {
    return brandDescription;
  }

  
  /**
   * @param brandDescription the brandDescription to set
   */
  public void setBrandDescription(String brandDescription) {
    this.brandDescription = brandDescription;
  }

  
  /**
   * @return the commodityCount
   */
  public Long getCommodityCount() {
    return commodityCount;
  }

  
  /**
   * @param commodityCount the commodityCount to set
   */
  public void setCommodityCount(Long commodityCount) {
    this.commodityCount = commodityCount;
  }


  
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
   * @return the commodityPopularRank
   */
  public Long getCommodityPopularRank() {
    return commodityPopularRank;
  }


  
  /**
   * @param commodityPopularRank the commodityPopularRank to set
   */
  public void setCommodityPopularRank(Long commodityPopularRank) {
    this.commodityPopularRank = commodityPopularRank;
  }


/**
 * @param brandDescriptionEn the brandDescriptionEn to set
 */
public void setBrandDescriptionEn(String brandDescriptionEn) {
	this.brandDescriptionEn = brandDescriptionEn;
}


/**
 * @return the brandDescriptionEn
 */
public String getBrandDescriptionEn() {
	return brandDescriptionEn;
}


/**
 * @param brandDescriptionJp the brandDescriptionJp to set
 */
public void setBrandDescriptionJp(String brandDescriptionJp) {
	this.brandDescriptionJp = brandDescriptionJp;
}


/**
 * @return the brandDescriptionJp
 */
public String getBrandDescriptionJp() {
	return brandDescriptionJp;
}


 


/**
 * @param brandEnglishName the brandEnglishName to set
 */
public void setBrandEnglishName(String brandEnglishName) {
	this.brandEnglishName = brandEnglishName;
}


/**
 * @return the brandEnglishName
 */
public String getBrandEnglishName() {
	return brandEnglishName;
}


/**
 * @param brandJapaneseName the brandJapaneseName to set
 */
public void setBrandJapaneseName(String brandJapaneseName) {
	this.brandJapaneseName = brandJapaneseName;
}


/**
 * @return the brandJapaneseName
 */
public String getBrandJapaneseName() {
	return brandJapaneseName;
}

}
