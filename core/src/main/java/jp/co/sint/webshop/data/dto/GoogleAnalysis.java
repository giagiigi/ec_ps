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
public class GoogleAnalysis implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Google Analytics编号 */
  @PrimaryKey(1)
  @Length(1)
  @Digit
  @Metadata(name = "Google Analytics编号", order = 1)
  private Long googleAnalysisNo;

  /** Google Analytics使用区分 */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "Google Analytics使用区分", order = 2)
  private Long enabledFlg;

  /** script内容 */
  @Length(2000)
  @Metadata(name = "script内容", order = 3)
  private String scriptText;

 
  
  /** 数据行ID */
  @Required
  @Length(38)
  @Metadata(name = "数据行ID", order = 4)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 创建用户 */
  @Required
  @Length(100)
  @Metadata(name = "创建用户", order = 5)
  private String createdUser;

  /** 创建时间 */
  @Required
  @Metadata(name = "创建时间", order = 6)
  private Date createdDatetime;

  /** 更新用户 */
  @Required
  @Length(100)
  @Metadata(name = "更新用户", order = 7)
  private String updatedUser;

  /** 更新时间 */
  @Required
  @Metadata(name = "更新时间", order = 8)
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

  
  public Long getgoogleAnalysisNo() {
    return googleAnalysisNo;
  }

  
  public void setgoogleAnalysisNo(Long googleAnalysisNo) {
    this.googleAnalysisNo = googleAnalysisNo;
  }

  
  public Long getOrmRowid() {
    return ormRowid;
  }

  
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  



  
  public String getScriptText() {
    return scriptText;
  }


  
  public void setScriptText(String scriptText) {
    this.scriptText = scriptText;
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


  
  public Long getGoogleAnalysisNo() {
    return googleAnalysisNo;
  }


  
  public void setGoogleAnalysisNo(Long googleAnalysisNo) {
    this.googleAnalysisNo = googleAnalysisNo;
  }


}
