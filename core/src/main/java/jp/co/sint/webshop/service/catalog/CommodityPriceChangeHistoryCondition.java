package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.service.SearchCondition;


public class CommodityPriceChangeHistoryCondition  extends SearchCondition {

  private static final long serialVersionUID = 1L;
  
  private String commodityCode;
  
  private String submitTime;
  
  private String responsiblePerson;
  
  private String oldOfficialPrice;
  
  private String newOfficialPrice;
  
  private String oldOfficialSpecialPrice;
  
  private String newOfficialSpecialPrice;
  
  private String oldTmallPrice;
  
  private String newTmallPrice;
  
  private String oldJdPrice;
  
  private String newJdPrice;
  
  private String ormRowid;
  
  private String createdUser;
  
  private String createdDatetime;
  
  private String updatedUser;
  
  private String updatedDatetime;
  
  private String reviewOrNotFlg;

  
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
   * @return the submitTime
   */
  public String getSubmitTime() {
    return submitTime;
  }

  
  /**
   * @param submitTime the submitTime to set
   */
  public void setSubmitTime(String submitTime) {
    this.submitTime = submitTime;
  }

  
  /**
   * @return the responsiblePerson
   */
  public String getResponsiblePerson() {
    return responsiblePerson;
  }

  
  /**
   * @param responsiblePerson the responsiblePerson to set
   */
  public void setResponsiblePerson(String responsiblePerson) {
    this.responsiblePerson = responsiblePerson;
  }

  
  /**
   * @return the oldOfficialPrice
   */
  public String getOldOfficialPrice() {
    return oldOfficialPrice;
  }

  
  /**
   * @param oldOfficialPrice the oldOfficialPrice to set
   */
  public void setOldOfficialPrice(String oldOfficialPrice) {
    this.oldOfficialPrice = oldOfficialPrice;
  }

  
  /**
   * @return the newOfficialPrice
   */
  public String getNewOfficialPrice() {
    return newOfficialPrice;
  }

  
  /**
   * @param newOfficialPrice the newOfficialPrice to set
   */
  public void setNewOfficialPrice(String newOfficialPrice) {
    this.newOfficialPrice = newOfficialPrice;
  }

  
  /**
   * @return the oldOfficialSpecialPrice
   */
  public String getOldOfficialSpecialPrice() {
    return oldOfficialSpecialPrice;
  }

  
  /**
   * @param oldOfficialSpecialPrice the oldOfficialSpecialPrice to set
   */
  public void setOldOfficialSpecialPrice(String oldOfficialSpecialPrice) {
    this.oldOfficialSpecialPrice = oldOfficialSpecialPrice;
  }

  
  /**
   * @return the newOfficialSpecialPrice
   */
  public String getNewOfficialSpecialPrice() {
    return newOfficialSpecialPrice;
  }

  
  /**
   * @param newOfficialSpecialPrice the newOfficialSpecialPrice to set
   */
  public void setNewOfficialSpecialPrice(String newOfficialSpecialPrice) {
    this.newOfficialSpecialPrice = newOfficialSpecialPrice;
  }

  
  /**
   * @return the oldTmallPrice
   */
  public String getOldTmallPrice() {
    return oldTmallPrice;
  }

  
  /**
   * @param oldTmallPrice the oldTmallPrice to set
   */
  public void setOldTmallPrice(String oldTmallPrice) {
    this.oldTmallPrice = oldTmallPrice;
  }

  
  /**
   * @return the newTmallPrice
   */
  public String getNewTmallPrice() {
    return newTmallPrice;
  }

  
  /**
   * @param newTmallPrice the newTmallPrice to set
   */
  public void setNewTmallPrice(String newTmallPrice) {
    this.newTmallPrice = newTmallPrice;
  }

  
  /**
   * @return the oldJdPrice
   */
  public String getOldJdPrice() {
    return oldJdPrice;
  }

  
  /**
   * @param oldJdPrice the oldJdPrice to set
   */
  public void setOldJdPrice(String oldJdPrice) {
    this.oldJdPrice = oldJdPrice;
  }

  
  /**
   * @return the newJdPrice
   */
  public String getNewJdPrice() {
    return newJdPrice;
  }

  
  /**
   * @param newJdPrice the newJdPrice to set
   */
  public void setNewJdPrice(String newJdPrice) {
    this.newJdPrice = newJdPrice;
  }

  
  /**
   * @return the ormRowid
   */
  public String getOrmRowid() {
    return ormRowid;
  }

  
  /**
   * @param ormRowid the ormRowid to set
   */
  public void setOrmRowid(String ormRowid) {
    this.ormRowid = ormRowid;
  }

  
  /**
   * @return the createdUser
   */
  public String getCreatedUser() {
    return createdUser;
  }

  
  /**
   * @param createdUser the createdUser to set
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  
  /**
   * @return the createdDatetime
   */
  public String getCreatedDatetime() {
    return createdDatetime;
  }

  
  /**
   * @param createdDatetime the createdDatetime to set
   */
  public void setCreatedDatetime(String createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  
  /**
   * @return the updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  
  /**
   * @param updatedUser the updatedUser to set
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  
  /**
   * @return the updatedDatetime
   */
  public String getUpdatedDatetime() {
    return updatedDatetime;
  }

  
  /**
   * @param updatedDatetime the updatedDatetime to set
   */
  public void setUpdatedDatetime(String updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  
  /**
   * @return the reviewOrNotFlg
   */
  public String getReviewOrNotFlg() {
    return reviewOrNotFlg;
  }

  
  /**
   * @param reviewOrNotFlg the reviewOrNotFlg to set
   */
  public void setReviewOrNotFlg(String reviewOrNotFlg) {
    this.reviewOrNotFlg = reviewOrNotFlg;
  }

  
  /**
   * @return the serialVersionUID
   */
  public static long getSerialVersionUID() {
    return serialVersionUID;
  }
  
}
