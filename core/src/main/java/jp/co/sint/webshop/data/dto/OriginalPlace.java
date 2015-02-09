package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;

public class OriginalPlace implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 产地编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "产地编号", order = 1)
  private String originalCode;

  /** 产地中文名称 */
  @Required
  @Length(100)
  @Metadata(name = "产地中文名称", order = 2)
  private String originalPlaceNameCn;

  /** 产地英文名称 */
  @Required
  @Length(100)
  @Metadata(name = "产地英文名称", order = 3)
  private String originalPlaceNameEn;

  /** 产地日文名称 */
  @Required
  @Length(100)
  @Metadata(name = "产地日文名称", order = 4)
  private String originalPlaceNameJp;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 5)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 6)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 7)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 8)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 9)
  private Date updatedDatetime;

  /**
   * @return the originalCode
   */
  public String getOriginalCode() {
    return originalCode;
  }

  /**
   * @param originalCode
   *          the originalCode to set
   */
  public void setOriginalCode(String originalCode) {
    this.originalCode = originalCode;
  }

  /**
   * @return the originalPlaceNameCn
   */
  public String getOriginalPlaceNameCn() {
    return originalPlaceNameCn;
  }

  /**
   * @param originalPlaceNameCn
   *          the originalPlaceNameCn to set
   */
  public void setOriginalPlaceNameCn(String originalPlaceNameCn) {
    this.originalPlaceNameCn = originalPlaceNameCn;
  }

  /**
   * @return the originalPlaceNameEn
   */
  public String getOriginalPlaceNameEn() {
    return originalPlaceNameEn;
  }

  /**
   * @param originalPlaceNameEn
   *          the originalPlaceNameEn to set
   */
  public void setOriginalPlaceNameEn(String originalPlaceNameEn) {
    this.originalPlaceNameEn = originalPlaceNameEn;
  }

  /**
   * @return the originalPlaceNameJp
   */
  public String getOriginalPlaceNameJp() {
    return originalPlaceNameJp;
  }

  /**
   * @param originalPlaceNameJp
   *          the originalPlaceNameJp to set
   */
  public void setOriginalPlaceNameJp(String originalPlaceNameJp) {
    this.originalPlaceNameJp = originalPlaceNameJp;
  }

  /**
   * @return the ormRowid
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  /**
   * @param ormRowid
   *          the ormRowid to set
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * @return the createdUser
   */
  public String getCreatedUser() {
    return createdUser;
  }

  /**
   * @param createdUser
   *          the createdUser to set
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * @return the createdDatetime
   */
  public Date getCreatedDatetime() {
    return createdDatetime;
  }

  /**
   * @param createdDatetime
   *          the createdDatetime to set
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  /**
   * @return the updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * @param updatedUser
   *          the updatedUser to set
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * @return the updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return updatedDatetime;
  }

  /**
   * @param updatedDatetime
   *          the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

}
