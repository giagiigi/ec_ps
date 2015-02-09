package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「JD商品属性关联表(jd_Commodity_Property)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class JdCommodityProperty implements Serializable, WebshopEntity ,JsonObject   {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "商品编号", order = 1)
  private String commodityCode;
  
  @PrimaryKey(2)
  @Required
  @Length(16)
  @Metadata(name = "属性id", order = 2)
  private String propertyId;
  
  @PrimaryKey(3)
  @Required
  @Length(16)
  @Metadata(name = "属性值ID", order = 3)
  private String valueId;
  
  @Length(50)
  @Metadata(name = "属性值", order = 4)
  private String valueText;
  
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 5)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 6)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 7)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 8)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 9)
  private Date updatedDatetime;

  
  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  
  /**
   * @param commodityCode the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  
  /**
   * @return the propertyId
   */
  public String getPropertyId() {
    return propertyId;
  }

  
  /**
   * @param propertyId the propertyId to set
   */
  public void setPropertyId(String propertyId) {
    this.propertyId = propertyId;
  }

  
  /**
   * @return the valueId
   */
  public String getValueId() {
    return valueId;
  }

  
  /**
   * @param valueId the valueId to set
   */
  public void setValueId(String valueId) {
    this.valueId = valueId;
  }

  
  /**
   * @return the valueText
   */
  public String getValueText() {
    return valueText;
  }

  
  /**
   * @param valueText the valueText to set
   */
  public void setValueText(String valueText) {
    this.valueText = valueText;
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


  @Override
  public String toJsonString() {
    StringBuilder builder = new StringBuilder();
    builder.append("{");  
    builder.append(JsonUtil.getPair("commodityCode", JsonUtil.urlEncode(getCommodityCode())) + ","); 
    builder.append(JsonUtil.getPair("propertyId", JsonUtil.urlEncode(getPropertyId())) + ","); 
    builder.append(JsonUtil.getPair("valueText", getValueText()) + ","); 
    builder.append(JsonUtil.getPair("valueId", JsonUtil.urlEncode(getValueId()))) ;
    builder.append("}");
    return builder.toString();
  }

  

}
