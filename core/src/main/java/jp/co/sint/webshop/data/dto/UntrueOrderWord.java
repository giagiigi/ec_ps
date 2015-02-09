
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
 *虚假订单关键词
 */
public class UntrueOrderWord implements Serializable, WebshopEntity {
  private static final long serialVersionUID = 1L;
  /** 关键词编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "虚假订单关键词编号", order = 1)
  private String orderWordCode;
  
  /**关键词名称 */
  @Required
  @Length(100)
  @Metadata(name = "虚假订单关键词名称", order = 2)
  private String orderWordName;
  
  /** 行号 */
  @Required
  @Length(38)
  @Metadata(name = "行号", order = 3)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /**创建人 */
  @Required
  @Length(100)
  @Metadata(name = "创建人", order = 4)
  private String createdUser;

  /**创建日期 */
  @Required
  @Metadata(name = "创建日期", order = 5)
  private Date createdDatetime;

  /** 更新人 */
  @Required
  @Length(100)
  @Metadata(name = "更新人", order = 6)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日期", order = 7)
  private Date updatedDatetime;

  
  /**
   * @return the serialVersionUID
   */
  public static long getSerialVersionUID() {
    return serialVersionUID;
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



  
  /**
   * @return the orderWordCode
   */
  public String getOrderWordCode() {
    return orderWordCode;
  }



  
  /**
   * @param orderWordCode the orderWordCode to set
   */
  public void setOrderWordCode(String orderWordCode) {
    this.orderWordCode = orderWordCode;
  }



  
  /**
   * @return the orderWordName
   */
  public String getOrderWordName() {
    return orderWordName;
  }



  
  /**
   * @param orderWordName the orderWordName to set
   */
  public void setOrderWordName(String orderWordName) {
    this.orderWordName = orderWordName;
  }

}
