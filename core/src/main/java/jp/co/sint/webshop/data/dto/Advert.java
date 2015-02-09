package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.AdvertType;

public class Advert implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 広告番号 */
  @PrimaryKey(1)
  @Length(1)
  @Digit
  @Metadata(name = "広告番号", order = 1)
  private Long advertNo;

  /** 広告機能使用フラグ */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "広告機能使用フラグ", order = 2)
  private Long advertEnabledFlg;

  /** 広告スクリプト内容 */
  @Required
  @Length(200)
  @Metadata(name = "広告スクリプト内容", order = 4)
  private String advertText;

  /** 広告広告タイプ内容 */
  @Required
  @Length(2)
  @Domain(AdvertType.class)
  @Metadata(name = "広告タイプ", order = 2)
  private String advertType;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 13)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 14)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 15)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 16)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 17)
  private Date updatedDatetime;

  public Long getAdvertEnabledFlg() {
    return advertEnabledFlg;
  }

  public void setAdvertEnabledFlg(Long advertEnabledFlg) {
    this.advertEnabledFlg = advertEnabledFlg;
  }

  public Long getAdvertNo() {
    return advertNo;
  }

  public void setAdvertNo(Long advertNo) {
    this.advertNo = advertNo;
  }

  public String getAdvertText() {
    return advertText;
  }

  public void setAdvertText(String advertText) {
    this.advertText = advertText;
  }

  public Date getCreatedDatetime() {
    return createdDatetime;
  }

  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  public String getCreatedUser() {
    return createdUser;
  }

  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  public Long getOrmRowid() {
    return ormRowid;
  }

  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  public Date getUpdatedDatetime() {
    return updatedDatetime;
  }

  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  public String getUpdatedUser() {
    return updatedUser;
  }

  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  public String getAdvertType() {
    return advertType;
  }

  public void setAdvertType(String advertType) {
    this.advertType = advertType;
  }

}
