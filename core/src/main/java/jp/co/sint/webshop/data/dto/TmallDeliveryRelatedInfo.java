package jp.co.sint.webshop.data.dto;

/**
 * 配送会社表
 * 
 * @author ob.
 * @version 1.0.0
 */
import java.io.Serializable;
import java.util.Date;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;

/**
 * AbstractDeliveryCompany entity provides the base persistence definition of
 * the DeliveryCompany
 * 
 * @author OB
 */

public class TmallDeliveryRelatedInfo implements Serializable, WebshopEntity {

  private static final long serialVersionUID = 1L;

  /** 关联编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "关联编号", order = 1)
  private String deliveryRelatedInfoNo;

  /** 店铺编号 */
  @Required
  @Length(16)
  @Metadata(name = "店铺编号", order = 2)
  private String shopCode;

  /** 配送公司编号 */
  @Required
  @Length(16)
  @Metadata(name = "配送公司编号", order = 3)
  private String deliveryCompanyNo;

  @Required
  @Length(2)
  @Metadata(name = "省/直辖市/自治区编号", order = 4)
  private String prefectureCode;

  @Required
  @Length(1)
  @Metadata(name = "COD区分", order = 5)
  private Long codType;

  @Required
  @Length(1)
  @Metadata(name = "配送希望日区分", order = 6)
  private Long deliveryDateType;

  @Required
  @Length(1)
  @Metadata(name = "配送时间段指定区分", order = 7)
  private Long deliveryAppointedTimeType;

  @Length(2)
  @Metadata(name = "配送时间段开始时间", order = 8)
  private Long deliveryAppointedStartTime;

  @Required
  @Length(2)
  @Metadata(name = "配送时间段结束时间", order = 9)
  private Long deliveryAppointedEndTime;

  /** データ行ID */
  @Required
  @Digit
  @Metadata(name = "データ行ID", order = 10)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 11)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Datetime
  @Metadata(name = "作成日時", order = 12)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 13)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Datetime
  @Metadata(name = "更新日時", order = 14)
  private Date updatedDatetime;

  @Length(10)
  @Metadata(name = "重量最小值", order = 15)
  private Long minWeight;

  @Length(10)
  @Metadata(name = "重量最大值", order = 16)
  private Long maxWeight;

  /**
   * @return 店铺编号
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param ShopCode
   *          设置店铺编号
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return 配送公司编号
   */
  public String getDeliveryCompanyNo() {
    return deliveryCompanyNo;
  }

  /**
   * @param DeliveryCompanyNo
   *          设置配送公司编号
   */
  public void setDeliveryCompanyNo(String deliveryCompanyNo) {
    this.deliveryCompanyNo = deliveryCompanyNo;
  }

  /**
   * 取得省/直辖市/自治区编号
   * 
   * @return
   */
  public String getPrefectureCode() {
    return prefectureCode;
  }

  /**
   * 设置省/直辖市/自治区编号
   * 
   * @param prefectureCode
   */
  public void setPrefectureCode(String prefectureCode) {
    this.prefectureCode = prefectureCode;
  }

  /**
   * 取得COD区分
   * 
   * @return codeType
   */
  public Long getCodType() {
    return codType;
  }

  /**
   * 设置COD区分
   * 
   * @param codeType
   *          codeType
   */
  public void setCodType(Long codType) {
    this.codType = codType;
  }

  /**
   * 取得配送希望日区分
   * 
   * @return deliveryDateType
   */
  public Long getDeliveryDateType() {
    return deliveryDateType;
  }

  /**
   * 设置配送希望日区分
   * 
   * @param deliveryDateType
   *          deliveryDateType
   */
  public void setDeliveryDateType(Long deliveryDateType) {
    this.deliveryDateType = deliveryDateType;
  }

  /**
   * @return データ行ID
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  /**
   * @param OrmRowid
   *          设置データ行ID
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * @return 作成ユーザ
   */
  public String getCreatedUser() {
    return createdUser;
  }

  /**
   * @param CreatedUser
   *          设置作成ユーザ
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * @return 作成日時
   */
  public Date getCreatedDatetime() {
    return createdDatetime;
  }

  /**
   * @param CreatedDatetime
   *          设置作成日時
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  /**
   * @return 更新ユーザ
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * @param UpdatedUser
   *          设置更新ユーザ
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * @return 更新日時
   */
  public Date getUpdatedDatetime() {
    return updatedDatetime;
  }

  /**
   * @param UpdatedDatetime
   *          设置更新日時
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  /**
   * @return the deliveryRelatedInfoNo
   */
  public String getDeliveryRelatedInfoNo() {
    return deliveryRelatedInfoNo;
  }

  /**
   * @param deliveryRelatedInfoNo
   *          the deliveryRelatedInfoNo to set
   */
  public void setDeliveryRelatedInfoNo(String deliveryRelatedInfoNo) {
    this.deliveryRelatedInfoNo = deliveryRelatedInfoNo;
  }

  /**
   * @return the deliveryAppointedTimeType
   */
  public Long getDeliveryAppointedTimeType() {
    return deliveryAppointedTimeType;
  }

  /**
   * @param deliveryAppointedTimeType
   *          the deliveryAppointedTimeType to set
   */
  public void setDeliveryAppointedTimeType(Long deliveryAppointedTimeType) {
    this.deliveryAppointedTimeType = deliveryAppointedTimeType;
  }

  /**
   * @return the deliveryAppointedStartTime
   */
  public Long getDeliveryAppointedStartTime() {
    return deliveryAppointedStartTime;
  }

  /**
   * @param deliveryAppointedStartTime
   *          the deliveryAppointedStartTime to set
   */
  public void setDeliveryAppointedStartTime(Long deliveryAppointedStartTime) {
    this.deliveryAppointedStartTime = deliveryAppointedStartTime;
  }

  /**
   * @return the deliveryAppointedEndTime
   */
  public Long getDeliveryAppointedEndTime() {
    return deliveryAppointedEndTime;
  }

  /**
   * @param deliveryAppointedEndTime
   *          the deliveryAppointedEndTime to set
   */
  public void setDeliveryAppointedEndTime(Long deliveryAppointedEndTime) {
    this.deliveryAppointedEndTime = deliveryAppointedEndTime;
  }

  public Long getMinWeight() {
    return minWeight;
  }

  public void setMinWeight(Long minWeight) {
    this.minWeight = minWeight;
  }

  public Long getMaxWeight() {
    return maxWeight;
  }

  public void setMaxWeight(Long maxWeight) {
    this.maxWeight = maxWeight;
  }

}
