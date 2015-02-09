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
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;

/** 
 *查询定义仓库的休息日
 */
public class StockHoliday implements Serializable, WebshopEntity {
  private static final long serialVersionUID = 1L;
  /** 休息日日期 */
  @PrimaryKey(1)
  @Required
  @Metadata(name = "库存休息日", order = 1)
  private Date holidayDay;
  
  /** 行号 */
  @Required
  @Length(38)
  @Metadata(name = "行号", order = 2)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /**创建人 */
  @Required
  @Length(100)
  @Metadata(name = "创建人", order = 3)
  private String createdUser;

  /**创建日期 */
  @Required
  @Metadata(name = "创建日期", order = 4)
  private Date createdDatetime;

  /** 更新人 */
  @Required
  @Length(100)
  @Metadata(name = "更新人", order = 5)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日期", order = 6)
  private Date updatedDatetime;

  
  /**
   * @return the serialVersionUID
   */
  public static long getSerialVersionUID() {
    return serialVersionUID;
  }
  
  /**
   * @return the holidayDay
   */
  public Date getHolidayDay() {
    return holidayDay;
  }

  
  /**
   * @param holidayDay the holidayDay to set
   */
  public void setHolidayDay(Date holidayDay) {
    this.holidayDay = holidayDay;
  }

  
  /**
   * @return the ormRowid
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  
  /**
   * @param ormRowid the ormRowid to set
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
   * @param createdUser the createdUser to set
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
   * @param createdDatetime the createdDatetime to set
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
   * @param updatedUser the updatedUser to set
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
   * @param updatedDatetime the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  

}
