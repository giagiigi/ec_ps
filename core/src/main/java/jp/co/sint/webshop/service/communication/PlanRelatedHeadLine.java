package jp.co.sint.webshop.service.communication;

import java.io.Serializable;
import java.math.BigDecimal;

public class PlanRelatedHeadLine implements Serializable {

  /** uid */
  private static final long serialVersionUID = 1L;

  private String commodityCode;

  private String commodityName;

  private String displayOrder;

  private String discountPriceStartDatetime;
  
  private String discountPriceEndDatetime;
  
  private BigDecimal discountPrice;
  
  public String getCommodityCode() {
	return commodityCode;
  }

  public void setCommodityCode(String commodityCode) {
	this.commodityCode = commodityCode;
  }

  public String getCommodityName() {
	return commodityName;
  }

  public void setCommodityName(String commodityName) {
	this.commodityName = commodityName;
  }

  public String getDisplayOrder() {
	return displayOrder;
  }

  public void setDisplayOrder(String displayOrder) {
	this.displayOrder = displayOrder;
  }


/**
 * @return the discountPrice
 */
public BigDecimal getDiscountPrice() {
	return discountPrice;
}

/**
 * @param discountPrice the discountPrice to set
 */
public void setDiscountPrice(BigDecimal discountPrice) {
	this.discountPrice = discountPrice;
}

/**
 * @return the discountPriceStartDatetime
 */
public String getDiscountPriceStartDatetime() {
	return discountPriceStartDatetime;
}

/**
 * @param discountPriceStartDatetime the discountPriceStartDatetime to set
 */
public void setDiscountPriceStartDatetime(String discountPriceStartDatetime) {
	this.discountPriceStartDatetime = discountPriceStartDatetime;
}

/**
 * @return the discountPriceEndDatetime
 */
public String getDiscountPriceEndDatetime() {
	return discountPriceEndDatetime;
}

/**
 * @param discountPriceEndDatetime the discountPriceEndDatetime to set
 */
public void setDiscountPriceEndDatetime(String discountPriceEndDatetime) {
	this.discountPriceEndDatetime = discountPriceEndDatetime;
}


}
