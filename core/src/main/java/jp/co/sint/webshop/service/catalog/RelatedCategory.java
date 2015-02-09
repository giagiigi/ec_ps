package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.utility.DateUtil;

public class RelatedCategory implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String checkCode;

  private String categoryCode;

  private Long categoryAttributeNo;

  private String categoryAttributeName;

  private String categoryAttributeValue;
//add by cs_yuli 20120607 start
  private String categoryAttributeNameEn;

  private String categoryAttributeNameJp;
  
  private String categoryAttributeValueEn;

  private String categoryAttributeValueJp;
//add by cs_yuli 20120607 end
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  private Date updatedDatetime;

  private String commodityCode;

  private String commodityName;

  public String getCategoryAttributeName() {
    return categoryAttributeName;
  }

  public void setCategoryAttributeName(String categoryAttributeName) {
    this.categoryAttributeName = categoryAttributeName;
  }

  public String getCategoryAttributeValue() {
    return categoryAttributeValue;
  }

  public void setCategoryAttributeValue(String categoryAttributeValue) {
    this.categoryAttributeValue = categoryAttributeValue;
  }

  public String getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  public String getCheckCode() {
    return checkCode;
  }

  public void setCheckCode(String checkCode) {
    this.checkCode = checkCode;
  }

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

  public Long getOrmRowid() {
    return ormRowid;
  }

  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(updatedDatetime);
  }

  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * @param categoryAttributeNo
   *          設定する categoryAttributeNo
   */
  public void setCategoryAttributeNo(Long categoryAttributeNo) {
    this.categoryAttributeNo = categoryAttributeNo;
  }

  /**
   * @return categoryAttributeNo
   */
  public Long getCategoryAttributeNo() {
    return categoryAttributeNo;
  }

  /**
   * @param shopCode
   *          設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

/**
 * @param categoryAttributeNameEn the categoryAttributeNameEn to set
 */
public void setCategoryAttributeNameEn(String categoryAttributeNameEn) {
	this.categoryAttributeNameEn = categoryAttributeNameEn;
}

/**
 * @return the categoryAttributeNameEn
 */
public String getCategoryAttributeNameEn() {
	return categoryAttributeNameEn;
}

/**
 * @param categoryAttributeValueEn the categoryAttributeValueEn to set
 */
public void setCategoryAttributeValueEn(String categoryAttributeValueEn) {
	this.categoryAttributeValueEn = categoryAttributeValueEn;
}

/**
 * @return the categoryAttributeValueEn
 */
public String getCategoryAttributeValueEn() {
	return categoryAttributeValueEn;
}

/**
 * @param categoryAttributeNameJp the categoryAttributeNameJp to set
 */
public void setCategoryAttributeNameJp(String categoryAttributeNameJp) {
	this.categoryAttributeNameJp = categoryAttributeNameJp;
}

/**
 * @return the categoryAttributeNameJp
 */
public String getCategoryAttributeNameJp() {
	return categoryAttributeNameJp;
}

/**
 * @param categoryAttributeValueJp the categoryAttributeValueJp to set
 */
public void setCategoryAttributeValueJp(String categoryAttributeValueJp) {
	this.categoryAttributeValueJp = categoryAttributeValueJp;
}

/**
 * @return the categoryAttributeValueJp
 */
public String getCategoryAttributeValueJp() {
	return categoryAttributeValueJp;
}

}
