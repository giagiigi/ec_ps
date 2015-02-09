//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.AllowCodFlg;
import jp.co.sint.webshop.data.domain.AllowDeliveryTimeFlg;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「区县(AREA)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author Kousen.
 */
public class Area implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 省份编号 */
  @Required
  @Length(2)
  @Metadata(name = "省份编号", order = 1)
  private String prefectureCode;

  /** 城市编号 */
  @Required
  @Length(3)
  @Metadata(name = "城市编号", order = 2)
  private String cityCode;

  /** 区县编号 */
  @PrimaryKey(1)
  @Required
  @Length(4)
  @Metadata(name = "区县编号", order = 3)
  private String areaCode;

  /** 区县名 */
  @Required
  @Length(20)
  @Metadata(name = "区县名", order = 4)
  private String areaName;

  /** 允许货到付款标志 */
  @Required
  @Length(1)
  @Domain(AllowCodFlg.class)
  @Metadata(name = "允许货到付款标志", order = 5)
  private Long allowCodFlg;

  /** 允许配送时间段标志 */
  @Required
  @Length(1)
  @Domain(AllowDeliveryTimeFlg.class)
  @Metadata(name = "允许配送时间段标志", order = 6)
  private Long allowDeliveryTimeFlg;

  /** 数据行ID */
  @Required
  @Length(38)
  @Metadata(name = "数据行ID", order = 7)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 创建者 */
  @Required
  @Length(100)
  @Metadata(name = "创建者", order = 8)
  private String createdUser;

  /** 创建日期 */
  @Required
  @Metadata(name = "创建日期", order = 9)
  private Date createdDatetime;

  /** 更新者 */
  @Required
  @Length(100)
  @Metadata(name = "更新者", order = 10)
  private String updatedUser;

  /** 更新日期 */
  @Required
  @Metadata(name = "更新日期", order = 11)
  private Date updatedDatetime;

  // 20120511 shen add start
  /** 区县英文名 */
  @Length(50)
  @Metadata(name = "区县英文名", order = 12)
  private String areaNameEn;

  /** 区县日文名 */
  @Length(50)
  @Metadata(name = "区县日文名", order = 13)
  private String areaNameJp;

  // 20120511 shen add end

  /**
   * 都道府県コードを取得します
   * 
   * @return 都道府県コード
   */
  public String getPrefectureCode() {
    return this.prefectureCode;
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
   * 都道府県コードを設定します
   * 
   * @param val
   *          都道府県コード
   */
  public void setPrefectureCode(String val) {
    this.prefectureCode = val;
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
   * @return the cityCode
   */
  public String getCityCode() {
    return cityCode;
  }

  /**
   * @param cityCode
   *          the cityCode to set
   */
  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  /**
   * @return the areaCode
   */
  public String getAreaCode() {
    return areaCode;
  }

  /**
   * @param areaCode
   *          the areaCode to set
   */
  public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
  }

  /**
   * @return the areaName
   */
  public String getAreaName() {
    return areaName;
  }

  /**
   * @param areaName
   *          the areaName to set
   */
  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  /**
   * @return the allowCodFlg
   */
  public Long getAllowCodFlg() {
    return allowCodFlg;
  }

  /**
   * @param allowCodFlg
   *          the allowCodFlg to set
   */
  public void setAllowCodFlg(Long allowCodFlg) {
    this.allowCodFlg = allowCodFlg;
  }

  /**
   * @return the allowDeliveryTimeFlg
   */
  public Long getAllowDeliveryTimeFlg() {
    return allowDeliveryTimeFlg;
  }

  /**
   * @param allowDeliveryTimeFlg
   *          the allowDeliveryTimeFlg to set
   */
  public void setAllowDeliveryTimeFlg(Long allowDeliveryTimeFlg) {
    this.allowDeliveryTimeFlg = allowDeliveryTimeFlg;
  }

  /**
   * @return the areaNameEn
   */
  public String getAreaNameEn() {
    return areaNameEn;
  }

  /**
   * @param areaNameEn
   *          the areaNameEn to set
   */
  public void setAreaNameEn(String areaNameEn) {
    this.areaNameEn = areaNameEn;
  }

  /**
   * @return the areaNameJp
   */
  public String getAreaNameJp() {
    return areaNameJp;
  }

  /**
   * @param areaNameJp
   *          the areaNameJp to set
   */
  public void setAreaNameJp(String areaNameJp) {
    this.areaNameJp = areaNameJp;
  }

}
