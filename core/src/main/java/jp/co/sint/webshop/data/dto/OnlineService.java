//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;

/** 
 * 「(OnlineService)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class OnlineService implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 在线客服编号 */
  @PrimaryKey(1)
  @Length(8)
  @Digit
  @Metadata(name = "在线客服编号", order = 1)
  private Long onlineServiceNo;

  /** 在线客服使用区分 */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "在线客服使用区分", order = 2)
  private Long enabledFlg;

  /** script内容1 */
  @Length(2000)
  @Metadata(name = "script内容1", order = 3)
  private String scriptText1;

  /** script内容2 */
  @Length(2000)
  @Metadata(name = "script内容2", order = 4)
  private String scriptText2;
 
  /** script内容3 */
  @Length(2000)
  @Metadata(name = "script内容3", order = 4)
  private String scriptText3;
  
  /** shopCode */
  @PrimaryKey
  @Metadata(name = "店铺编号", order = 5)
  private String shopCode;
  
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

  
  public Long getEnabledFlg() {
    return enabledFlg;
  }

  
  public void setEnabledFlg(Long enabledFlg) {
    this.enabledFlg = enabledFlg;
  }

  
  public Long getOnlineServiceNo() {
    return onlineServiceNo;
  }

  
  public void setOnlineServiceNo(Long onlineServiceNo) {
    this.onlineServiceNo = onlineServiceNo;
  }

  
  public Long getOrmRowid() {
    return ormRowid;
  }

  
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  
  public String getScriptText1() {
    return scriptText1;
  }

  
  public void setScriptText1(String scriptText1) {
    this.scriptText1 = scriptText1;
  }

  
  public String getScriptText2() {
    return scriptText2;
  }

  
  public void setScriptText2(String scriptText2) {
    this.scriptText2 = scriptText2;
  }

  
  
  /**
   * scriptText3を取得します。
   *
   * @return scriptText3 scriptText3
   */
  public String getScriptText3() {
    return scriptText3;
  }


  
  /**
   * scriptText3を設定します。
   *
   * @param scriptText3 
   *          scriptText3
   */
  public void setScriptText3(String scriptText3) {
    this.scriptText3 = scriptText3;
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


  
  /**
   * shopCodeを取得します。
   *
   * @return shopCode shopCode
   */
  public String getShopCode() {
    return shopCode;
  }


  
  /**
   * shopCodeを設定します。
   *
   * @param shopCode 
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }


}
