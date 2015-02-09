package jp.co.sint.webshop.service.communication;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.utility.DateUtil;

public class MessageHeadLine implements Serializable {

  /** uid */
  private static final long serialVersionUID = 1L;

  private String customerCode;
  
  private String message;
  
  private Date createdDatetime;
  
  private long ormRowid;

  /**
   * @return the customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * @return the createdDatetime
   */
  public Date getCreatedDatetime() {
    return DateUtil.immutableCopy(createdDatetime);
  }

  
  
  /**
   * @return the ormRowid
   */
  public long getOrmRowid() {
    return ormRowid;
  }

  
  /**
   * @param ormRowid the ormRowid to set
   */
  public void setOrmRowid(long ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  
  /**
   * @param message the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @param customerCode
   *          the customerCode to set
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * @param createdDatetime
   *          the createdDatetime to set
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = DateUtil.immutableCopy(createdDatetime);
  }

}
