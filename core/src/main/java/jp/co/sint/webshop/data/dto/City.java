//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「地域(REGION)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class City implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 国コード */
  @PrimaryKey(1)
  @Required
  @Length(2)
  @Metadata(name = "国コード", order = 1)
  private String countryCode;

  /** 地域コード */
  @PrimaryKey(2)
  @Required
  @Length(2)
  @Metadata(name = "地域コード", order = 2)
  private String regionCode;

  /** 地域コード */
  @PrimaryKey(3)
  @Required
  @Length(3)
  @Metadata(name = "城市コード", order = 3)
  private String cityCode;

  /** 地域名称 */
  @Required
  @Length(50)
  @Metadata(name = "城市名称", order = 4)
  private String cityName;

  /** 表示順 */
  @Length(8)
  @Metadata(name = "表示順", order = 5)
  private Long displayOrder;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 6)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 7)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 8)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 9)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 10)
  private Date updatedDatetime;

  // 20120511 shen add start
  /** 城市英文名 */
  @Length(50)
  @Metadata(name = "城市英文名", order = 11)
  private String cityNameEn;

  /** 城市日文名 */
  @Length(50)
  @Metadata(name = "城市日文名", order = 12)
  private String cityNameJp;

  // 20120511 shen add end

  /**
   * 国コードを取得します
   * 
   * @return 国コード
   */
  public String getCountryCode() {
    return this.countryCode;
  }

  /**
   * 地域コードを取得します
   * 
   * @return 地域コード
   */
  public String getRegionCode() {
    return this.regionCode;
  }

  /**
   * 表示順を取得します
   * 
   * @return 表示順
   */
  public Long getDisplayOrder() {
    return this.displayOrder;
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
   * 国コードを設定します
   * 
   * @param val
   *          国コード
   */
  public void setCountryCode(String val) {
    this.countryCode = val;
  }

  /**
   * 地域コードを設定します
   * 
   * @param val
   *          地域コード
   */
  public void setRegionCode(String val) {
    this.regionCode = val;
  }

  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  /**
   * 表示順を設定します
   * 
   * @param val
   *          表示順
   */
  public void setDisplayOrder(Long val) {
    this.displayOrder = val;
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
   * @return the cityNameEn
   */
  public String getCityNameEn() {
    return cityNameEn;
  }

  /**
   * @param cityNameEn
   *          the cityNameEn to set
   */
  public void setCityNameEn(String cityNameEn) {
    this.cityNameEn = cityNameEn;
  }

  /**
   * @return the cityNameJp
   */
  public String getCityNameJp() {
    return cityNameJp;
  }

  /**
   * @param cityNameJp
   *          the cityNameJp to set
   */
  public void setCityNameJp(String cityNameJp) {
    this.cityNameJp = cityNameJp;
  }

}
