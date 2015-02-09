package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;

import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

public class ZsIfInvImportReport implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** SKUコード */
  @Required
  @Length(24)
  @AlphaNum2
  @Metadata(name = "SKUコード", order = 1)
  private String skuCode;

  /** 入出庫数量 */
  @Length(8)
  @Quantity
  @Required
  @Metadata(name = "入庫数量", order = 2)
  private Long stockTotal;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 3)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 4)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 5)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 6)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 7)
  private Date updatedDatetime;

  // Site
  @Metadata(name = "Site", order = 8)
  private String site;

  // Location
  @Metadata(name = "Location", order = 9)
  private String location;

  // Lot Number
  @Metadata(name = "Lot Number", order = 10)
  private String lotNumber;

  // Reference Number
  @Metadata(name = "Reference Number", order = 11)
  private String referenceNumber;

  // UM
  @Metadata(name = "um", order = 12)
  private String um;

  // Effective Date
  private String effectiveDate;

  // Sales/Job
  private String salesJob;

  // Remark
  private String remark;

  // From Site
  private String fromSite;

  // From Location
  private String fromLocation;

  // From Lot Number
  private String fromLotnumber;

  // From Reference
  private String fromReference;
  
  //Conversion
  private String conversion;
  
  //Lot/Serial
  private String lotSerial;
  
  //Reference
  private String reference;
  
  //Multi Entry
  private String multiEntry;
    
  //Order
  private String order;
  
  //Line
  private String line;
  
  //Address
  private String address;
  
  //Remarks
  private String remarks;
  

  //Reason Code
  private String reasonCode;
  
  //po_number
  private String poNumber;
  
  //packing_slip
  private String packingSlip;
  
  //lot
  private String lot;
  
  //receiver
  private String receiver;
  
  
  
    
  
  
  public String getPoNumber() {
    return poNumber;
  }


  
  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }


  
  public String getPackingSlip() {
    return packingSlip;
  }


  
  public void setPackingSlip(String packingSlip) {
    this.packingSlip = packingSlip;
  }


  
  public String getLot() {
    return lot;
  }


  
  public void setLot(String lot) {
    this.lot = lot;
  }


  
  public String getReceiver() {
    return receiver;
  }


  
  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }


  public static long getSerialVersionUID() {
    return serialVersionUID;
  }


  public String getConversion() {
    return conversion;
  }

  
  public void setConversion(String conversion) {
    this.conversion = conversion;
  }

  
  

  
  public String getReference() {
    return reference;
  }

  
  public void setReference(String reference) {
    this.reference = reference;
  }

  
 
  
  public String getOrder() {
    return order;
  }

  
  public void setOrder(String order) {
    this.order = order;
  }

  
  public String getLine() {
    return line;
  }

  
  public void setLine(String line) {
    this.line = line;
  }

  
  
  
  public String getAddress() {
    return address;
  }

  
  public void setAddress(String address) {
    this.address = address;
  }

  
  public String getRemarks() {
    return remarks;
  }

  
  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  
 
  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getEffectiveDate() {
    return effectiveDate;
  }

  public void setEffectiveDate(String effectiveDate) {
    this.effectiveDate = effectiveDate;
  }

  public String getSalesJob() {
    return salesJob;
  }

  public void setSalesJob(String salesJob) {
    this.salesJob = salesJob;
  }

  public String getFromSite() {
    return fromSite;
  }

  public void setFromSite(String fromSite) {
    this.fromSite = fromSite;
  }

  public String getFromLocation() {
    return fromLocation;
  }

  public void setFromLocation(String fromLocation) {
    this.fromLocation = fromLocation;
  }

  public String getFromLotnumber() {
    return fromLotnumber;
  }

  public void setFromLotnumber(String fromLotnumber) {
    this.fromLotnumber = fromLotnumber;
  }

  public String getFromReference() {
    return fromReference;
  }

  public void setFromReference(String fromReference) {
    this.fromReference = fromReference;
  }

  public String getReferenceNumber() {
    return referenceNumber;
  }

  public void setReferenceNumber(String referenceNumber) {
    this.referenceNumber = referenceNumber;
  }

  public String getUm() {
    return um;
  }

  public void setUm(String um) {
    this.um = um;
  }

  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getLotNumber() {
    return lotNumber;
  }

  public void setLotNumber(String lotNumber) {
    this.lotNumber = lotNumber;
  }
  /**
   * SKUコードを取得します
   * 
   * @return SKUコード
   */
  public String getSkuCode() {
    return this.skuCode;
  }

  /**
   * データ行IDを取得します
   * 
   * @return データ行ID
   */
  public Long getOrmRowid() {
    return this.ormRowid;
  }

  /**
   * 作成ユーザを取得します
   * 
   * @return 作成ユーザ
   */
  public String getCreatedUser() {
    return this.createdUser;
  }

  /**
   * 作成日時を取得します
   * 
   * @return 作成日時
   */
  public Date getCreatedDatetime() {
    return DateUtil.immutableCopy(this.createdDatetime);
  }

  /**
   * 更新ユーザを取得します
   * 
   * @return 更新ユーザ
   */
  public String getUpdatedUser() {
    return this.updatedUser;
  }

  /**
   * 更新日時を取得します
   * 
   * @return 更新日時
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(this.updatedDatetime);
  }

  /**
   * SKUコードを設定します
   * 
   * @param val
   *          SKUコード
   */
  public void setSkuCode(String val) {
    this.skuCode = val;
  }

  /**
   * データ行IDを設定します
   * 
   * @param val
   *          データ行ID
   */
  public void setOrmRowid(Long val) {
    this.ormRowid = val;
  }

  /**
   * 作成ユーザを設定します
   * 
   * @param val
   *          作成ユーザ
   */
  public void setCreatedUser(String val) {
    this.createdUser = val;
  }

  /**
   * 作成日時を設定します
   * 
   * @param val
   *          作成日時
   */
  public void setCreatedDatetime(Date val) {
    this.createdDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 更新ユーザを設定します
   * 
   * @param val
   *          更新ユーザ
   */
  public void setUpdatedUser(String val) {
    this.updatedUser = val;
  }

  /**
   * 更新日時を設定します
   * 
   * @param val
   *          更新日時
   */
  public void setUpdatedDatetime(Date val) {
    this.updatedDatetime = DateUtil.immutableCopy(val);
  }

  public Long getStockTotal() {
    return stockTotal;
  }

  public void setStockTotal(Long stockTotal) {
    this.stockTotal = stockTotal;
  }
  public String getLotSerial() {
    return lotSerial;
  }


  
  public void setLotSerial(String lotSerial) {
    this.lotSerial = lotSerial;
  }


  
  public String getMultiEntry() {
    return multiEntry;
  }


  
  public void setMultiEntry(String multiEntry) {
    this.multiEntry = multiEntry;
  }


  
  public String getReasonCode() {
    return reasonCode;
  }


  
  public void setReasonCode(String reasonCode) {
    this.reasonCode = reasonCode;
  }

}
