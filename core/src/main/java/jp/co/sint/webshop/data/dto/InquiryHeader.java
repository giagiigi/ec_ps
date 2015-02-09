package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.IbObType;
import jp.co.sint.webshop.data.domain.InquiryWay;
import jp.co.sint.webshop.utility.DateUtil;

public class InquiryHeader implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 咨询编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "咨询编号", order = 1)
  private Long inquiryHeaderNo;

  /** 顾客编号 */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顾客编号", order = 2)
  private String customerCode;

  /** 顾客名称 */
  @Required
  @Length(20)
  @Metadata(name = "顾客名称", order = 3)
  private String customerName;

  /** 受理日期 */
  @Required
  @Metadata(name = "受理日期", order = 4)
  private Date acceptDatetime;

  /** 大分类 */
  @Required
  @Length(50)
  @Metadata(name = "大分类 ", order = 5)
  private String largeCategory;

  /** 小分类 */
  @Required
  @Length(50)
  @Metadata(name = "小分类", order = 6)
  private String smallCategory;

  /** 咨询途径 */
  @Required
  @Length(2)
  @Domain(InquiryWay.class)
  @Metadata(name = "咨询途径", order = 7)
  private Long inquiryWay;

  /** 咨询主题 */
  @Required
  @Length(200)
  @Metadata(name = "咨询主题", order = 8)
  private String inquirySubject;

  /** IB/OB区分 */
  @Required
  @Length(1)
  @Domain(IbObType.class)
  @Metadata(name = "IB/OB区分", order = 9)
  private Long ibObType;

  /** 商品编号 */
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品编号", order = 10)
  private String commodityCode;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 11)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 12)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 13)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 14)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 15)
  private Date updatedDatetime;

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

  /**
   * @return the inquiryHeaderNo
   */
  public Long getInquiryHeaderNo() {
    return inquiryHeaderNo;
  }

  /**
   * @param inquiryHeaderNo
   *          the inquiryHeaderNo to set
   */
  public void setInquiryHeaderNo(Long inquiryHeaderNo) {
    this.inquiryHeaderNo = inquiryHeaderNo;
  }

  /**
   * @return the customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * @param customerCode
   *          the customerCode to set
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * @return the customerName
   */
  public String getCustomerName() {
    return customerName;
  }

  /**
   * @param customerName
   *          the customerName to set
   */
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  /**
   * @return the largeCategory
   */
  public String getLargeCategory() {
    return largeCategory;
  }

  /**
   * @param largeCategory
   *          the largeCategory to set
   */
  public void setLargeCategory(String largeCategory) {
    this.largeCategory = largeCategory;
  }

  /**
   * @return the smallCategory
   */
  public String getSmallCategory() {
    return smallCategory;
  }

  /**
   * @param smallCategory
   *          the smallCategory to set
   */
  public void setSmallCategory(String smallCategory) {
    this.smallCategory = smallCategory;
  }

  /**
   * @return the inquiryWay
   */
  public Long getInquiryWay() {
    return inquiryWay;
  }

  /**
   * @param inquiryWay
   *          the inquiryWay to set
   */
  public void setInquiryWay(Long inquiryWay) {
    this.inquiryWay = inquiryWay;
  }

  /**
   * @return the inquirySubject
   */
  public String getInquirySubject() {
    return inquirySubject;
  }

  /**
   * @param inquirySubject
   *          the inquirySubject to set
   */
  public void setInquirySubject(String inquirySubject) {
    this.inquirySubject = inquirySubject;
  }

  /**
   * @return the acceptDatetime
   */
  public Date getAcceptDatetime() {
    return acceptDatetime;
  }

  /**
   * @param acceptDatetime
   *          the acceptDatetime to set
   */
  public void setAcceptDatetime(Date acceptDatetime) {
    this.acceptDatetime = acceptDatetime;
  }

  /**
   * @return the ibObType
   */
  public Long getIbObType() {
    return ibObType;
  }

  /**
   * @param ibObType
   *          the ibObType to set
   */
  public void setIbObType(Long ibObType) {
    this.ibObType = ibObType;
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

}
