package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

public class InputShippingReport implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** 出荷番号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "出荷番号", order = 1)
  private String shippingNo;

  /** 出荷日 */
  @Required
  @Datetime
  @Metadata(name = "出荷日", order = 2)
  private Date shippingDate;

  /** 出荷指示日 */
  @Datetime
  @Metadata(name = "出荷指示日", order = 3)
  private Date shippingDirectDate;

  /** 到着予定日 */
  @Datetime
  @Metadata(name = "到着予定日", order = 4)
  private Date arrivalDate;

  /** 配送指定時間開始 */
  @Length(2)
  @Digit
  @Range(min = 0, max = 23)
  @Metadata(name = "配送指定時間開始", order = 5)
  private Long arrivalTimeStart;

  /** 配送指定時間終了 */
  @Length(2)
  @Digit
  @Range(min = 0, max = 23)
  @Metadata(name = "配送指定時間終了", order = 6)
  private Long arrivalTimeEnd;

  /** 宅配便伝票番号 */
  @Length(30)
  @Digit
  @Metadata(name = "宅配便伝票番号", order = 7)
  private String deliverySlipNo;

  /** 更新ユーザ */
  @Length(16)
  @Metadata(name = "更新ユーザ", order = 8)
  private String updatedUser;

  /** 更新日時 */
  @Metadata(name = "更新日時", order = 9)
  private Date updatedDatetime;

  private Date orderUpdatedDatetime;

  /**
   * @return the orderUpdatedDatetime
   */
  public Date getOrderUpdatedDatetime() {
    return DateUtil.immutableCopy(orderUpdatedDatetime);
  }

  /**
   * @param orderUpdatedDatetime
   *          the orderUpdatedDatetime to set
   */
  public void setOrderUpdatedDatetime(Date orderUpdatedDatetime) {
    this.orderUpdatedDatetime = DateUtil.immutableCopy(orderUpdatedDatetime);
  }

  /**
   * @return the updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * @param updatedDatetime
   *          the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * @return the serialVersionUID
   */
  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  /**
   * @return the arrivalDate
   */
  public Date getArrivalDate() {
    return DateUtil.immutableCopy(arrivalDate);
  }

  /**
   * @param arrivalDate
   *          the arrivalDate to set
   */
  public void setArrivalDate(Date arrivalDate) {
    this.arrivalDate = DateUtil.immutableCopy(arrivalDate);
  }

  /**
   * @return the arrivalTimeEnd
   */
  public Long getArrivalTimeEnd() {
    return arrivalTimeEnd;
  }

  /**
   * @param arrivalTimeEnd
   *          the arrivalTimeEnd to set
   */
  public void setArrivalTimeEnd(Long arrivalTimeEnd) {
    this.arrivalTimeEnd = arrivalTimeEnd;
  }

  /**
   * @return the arrivalTimeStart
   */
  public Long getArrivalTimeStart() {
    return arrivalTimeStart;
  }

  /**
   * @param arrivalTimeStart
   *          the arrivalTimeStart to set
   */
  public void setArrivalTimeStart(Long arrivalTimeStart) {
    this.arrivalTimeStart = arrivalTimeStart;
  }

  /**
   * @return the deliverySlipNo
   */
  public String getDeliverySlipNo() {
    return deliverySlipNo;
  }

  /**
   * @param deliverySlipNo
   *          the deliverySlipNo to set
   */
  public void setDeliverySlipNo(String deliverySlipNo) {
    this.deliverySlipNo = deliverySlipNo;
  }

  /**
   * @return the shippingDate
   */
  public Date getShippingDate() {
    return DateUtil.immutableCopy(shippingDate);
  }

  /**
   * @param shippingDate
   *          the shippingDate to set
   */
  public void setShippingDate(Date shippingDate) {
    this.shippingDate = DateUtil.immutableCopy(shippingDate);
  }

  /**
   * @return the shippingDirectDate
   */
  public Date getShippingDirectDate() {
    return DateUtil.immutableCopy(shippingDirectDate);
  }

  /**
   * @param shippingDirectDate
   *          the shippingDirectDate to set
   */
  public void setShippingDirectDate(Date shippingDirectDate) {
    this.shippingDirectDate = DateUtil.immutableCopy(shippingDirectDate);
  }

  /**
   * @return the shippingNo
   */
  public String getShippingNo() {
    return shippingNo;
  }

  /**
   * @param shippingNo
   *          the shippingNo to set
   */
  public void setShippingNo(String shippingNo) {
    this.shippingNo = shippingNo;
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

}
