package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.InquiryStatus;
import jp.co.sint.webshop.utility.DateUtil;

public class InquiryDetail implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 咨询编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "咨询编号", order = 1)
  private Long inquiryHeaderNo;

  /** 咨询明细编号 */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "咨询明细编号", order = 2)
  private Long inquiryDetailNo;

  /** 受理更新日期 */
  @Required
  @Metadata(name = "受理更新日期", order = 3)
  private Date acceptDatetime;

  /** 担当编号 */
  @Required
  @Length(20)
  @Metadata(name = "担当编号", order = 4)
  private String personInChargeNo;

  /** 担当名称 */
  @Required
  @Length(20)
  @Metadata(name = "担当名称", order = 5)
  private String personInChargeName;

  /** 咨询内容 */
  @Required
  @Length(2000)
  @Metadata(name = "咨询内容", order = 6)
  private String inquiryContents;

  /** 咨询状态 */
  @Required
  @Length(1)
  @Domain(InquiryStatus.class)
  @Metadata(name = "咨询状态", order = 7)
  private Long inquiryStatus;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 8)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 9)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 10)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 11)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 12)
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
   * @return the inquiryDetailNo
   */
  public Long getInquiryDetailNo() {
    return inquiryDetailNo;
  }

  /**
   * @param inquiryDetailNo
   *          the inquiryDetailNo to set
   */
  public void setInquiryDetailNo(Long inquiryDetailNo) {
    this.inquiryDetailNo = inquiryDetailNo;
  }

  /**
   * @return the inquiryContents
   */
  public String getInquiryContents() {
    return inquiryContents;
  }

  /**
   * @param inquiryContents
   *          the inquiryContents to set
   */
  public void setInquiryContents(String inquiryContents) {
    this.inquiryContents = inquiryContents;
  }

  /**
   * @return the inquiryStatus
   */
  public Long getInquiryStatus() {
    return inquiryStatus;
  }

  /**
   * @param inquiryStatus
   *          the inquiryStatus to set
   */
  public void setInquiryStatus(Long inquiryStatus) {
    this.inquiryStatus = inquiryStatus;
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
   * @return the personInChargeNo
   */
  public String getPersonInChargeNo() {
    return personInChargeNo;
  }

  /**
   * @param personInChargeNo
   *          the personInChargeNo to set
   */
  public void setPersonInChargeNo(String personInChargeNo) {
    this.personInChargeNo = personInChargeNo;
  }

  /**
   * @return the personInChargeName
   */
  public String getPersonInChargeName() {
    return personInChargeName;
  }

  /**
   * @param personInChargeName
   *          the personInChargeName to set
   */
  public void setPersonInChargeName(String personInChargeName) {
    this.personInChargeName = personInChargeName;
  }

}
