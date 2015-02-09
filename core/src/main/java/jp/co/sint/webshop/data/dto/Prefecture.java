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
import jp.co.sint.webshop.data.domain.PrefectureCode;
import jp.co.sint.webshop.data.domain.PrefectureName;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「都道府県(PREFECTURE)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class Prefecture implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 都道府県コード */
  @PrimaryKey(1)
  @Required
  @Length(2)
  @Domain(PrefectureCode.class)
  @Metadata(name = "都道府県コード", order = 1)
  private String prefectureCode;

  /** 都道府県名称 */
  @Required
  @Length(10)
  @Domain(PrefectureName.class)
  @Metadata(name = "都道府県名称", order = 2)
  private String prefectureName;

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

  // 20120511 shen add start
  /** 省份英文名 */
  @Length(50)
  @Metadata(name = "省份英文名", order = 8)
  private String prefectureNameEn;

  /** 省份日文名 */
  @Length(50)
  @Metadata(name = "省份日文名", order = 9)
  private String prefectureNameJp;

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
   * 都道府県名称を取得します
   * 
   * @return 都道府県名称
   */
  public String getPrefectureName() {
    return this.prefectureName;
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
   * 都道府県名称を設定します
   * 
   * @param val
   *          都道府県名称
   */
  public void setPrefectureName(String val) {
    this.prefectureName = val;
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
   * @return the prefectureNameEn
   */
  public String getPrefectureNameEn() {
    return prefectureNameEn;
  }

  /**
   * @param prefectureNameEn
   *          the prefectureNameEn to set
   */
  public void setPrefectureNameEn(String prefectureNameEn) {
    this.prefectureNameEn = prefectureNameEn;
  }

  /**
   * @return the prefectureNameJp
   */
  public String getPrefectureNameJp() {
    return prefectureNameJp;
  }

  /**
   * @param prefectureNameJp
   *          the prefectureNameJp to set
   */
  public void setPrefectureNameJp(String prefectureNameJp) {
    this.prefectureNameJp = prefectureNameJp;
  }

}
