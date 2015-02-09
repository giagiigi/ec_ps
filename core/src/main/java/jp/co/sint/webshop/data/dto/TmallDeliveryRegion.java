package jp.co.sint.webshop.data.dto;

/**
 * 运费表
 * 
 * @author ob.
 * @version 1.0.0
 * 
 */
import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;

/**
 * AbstractDeliveryRegionCharge entity provides the base persistence definition of the DeliveryRegionCharge
 * @author OB
 */

public class TmallDeliveryRegion implements Serializable, WebshopEntity {

  private static final long serialVersionUID = 1L;

  /** 店铺编号 */
  @Required
  @Length(16)
  @Metadata(name = "店铺编号", order = 1)
  private String shopCode;
  
  /** 配送公司编号 */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @Metadata(name = "配送公司编号", order = 2)
  private String deliveryCompanyNo;

  /** 地域编号 */
  @Required
  @Digit
  @Metadata(name = "地域编号", order = 3)
  private String prefectureCode;

  /** データ行ID */
  @Required
  @Digit
  @Metadata(name = "データ行ID", order = 14)
  private Long ormRowid=DatabaseUtil.DEFAULT_ORM_ROWID;;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 15)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Datetime
  @Metadata(name = "作成日時", order = 16)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 17)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Datetime
  @Metadata(name = "更新日時", order = 18)
  private Date updatedDatetime;



  /**
   * @return 店铺编号
   */
  public String getShopCode() {
    return shopCode;
  }
  /**
   * @param ShopCode 设置店铺编号
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  public String getDeliveryCompanyNo() {
    return deliveryCompanyNo;
  }
  
  public void setDeliveryCompanyNo(String deliveryCompanyNo) {
    this.deliveryCompanyNo = deliveryCompanyNo;
  }

  /**
   * @return データ行ID
   */
  public Long getOrmRowid() {
    return ormRowid;
  }
  /**
   * @param OrmRowid 设置データ行ID
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
   * @param CreatedUser 设置作成ユーザ
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
   * @param CreatedDatetime 设置作成日時
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
   * @param UpdatedUser 设置更新ユーザ
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
   * @param UpdatedDatetime 设置更新日時
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }
public String getPrefectureCode() {
	return prefectureCode;
}
public void setPrefectureCode(String prefectureCode) {
	this.prefectureCode = prefectureCode;
}

}