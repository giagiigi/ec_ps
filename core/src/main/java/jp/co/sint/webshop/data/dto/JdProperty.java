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
 * 「JD属性表(jd_Property)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author OB
 */
public class JdProperty implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "属性id", order = 1)
  private String propertyId;
  
  @PrimaryKey(2)
  @Required
  @Length(16)
  @Metadata(name = "类目id", order = 2)
  private String categoryId;

  @Required
  @Length(50)
  @Metadata(name = "属性名称", order = 3)
  private String propertyName;

  @Required
  @Length(1)
  @Metadata(name = "关键属性", order = 4)
  private Long isKey;

  @Required
  @Length(1)
  @Metadata(name = "销售属性", order = 5)
  private Long isSale;
  
  @Required
  @Length(1)
  @Metadata(name = "颜色属性", order = 6)
  private Long isColor;
  
  @Required
  @Length(1)
  @Metadata(name = "尺码属性", order = 7)
  private Long isSize;
  
  @Required
  @Length(1)
  @Metadata(name = "是否必填", order = 8)
  private Long isReq;
  
  @Required
  @Length(1)
  @Metadata(name = "是否筛选", order = 9)
  private Long isFet;
  
  @Required
  @Length(1)
  @Metadata(name = "是否导航", order = 10)
  private Long isNav;

  @Required
  @Length(1)
  @Metadata(name = "属性类型1、关键属性2、不变属性3、可变属性4、销售", order = 11)
  private Long attType;

  @Required
  @Length(1)
  @Metadata(name = "输入类型( 1、单选， 2、多选，3、输入)", order = 12)
  private Long inputType;

  @Length(16)
  @Metadata(name = "排序", order = 13)
  private Long displayOrder;
  
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 14)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 15)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 16)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 17)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 18)
  private Date updatedDatetime;

//  private List<JdPropertyValue> propertyValueContain = new ArrayList<JdPropertyValue>();

  /**
   * @return the propertyValueContain
   */
//  public List<JdPropertyValue> getPropertyValueContain() {
//    return propertyValueContain;
//  }
//
//  /**
//   * @param propertyValueContain
//   *          the propertyValueContain to set
//   */
//  public void setPropertyValueContain(List<JdPropertyValue> propertyValueContain) {
//    this.propertyValueContain = propertyValueContain;
//  }

  public boolean isSelect() {
    return this.inputType == 1 ;
  }

  public boolean isCheckbox() {
    return this.inputType == 2 ;
  }

  public boolean isInputbox() {
    return this.inputType == 3;
  }


  public boolean getIsSelect() {
    return this.isSelect();

  }

  public boolean getIsCheckbox() {
    return this.isCheckbox();
  }

  /**
   * @return the propertyId
   */
  public String getPropertyId() {
    return propertyId;
  }

  /**
   * @param propertyId
   *          the propertyId to set
   */
  public void setPropertyId(String propertyId) {
    this.propertyId = propertyId;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  /**
   * @return the propertyName
   */
  public String getPropertyName() {
    return propertyName;
  }

  /**
   * @param propertyName
   *          the propertyName to set
   */
  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  /**
   * @return the isSale
   */
  public Long getIsSale() {
    return isSale;
  }

  /**
   * @param isSale
   *          the isSale to set
   */
  public void setIsSale(Long isSale) {
    this.isSale = isSale;
  }

  public Long getIsKey() {
    return isKey;
  }

  public void setIsKey(Long isKey) {
    this.isKey = isKey;
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

  /**
   * 清除属性中属性值名称或者ID为空的元素
   */
//  public void cleanEmptyPropertyValueElement() {
//    List<JdPropertyValue> contain = new ArrayList<JdPropertyValue>();
//    for (JdPropertyValue value : contain) {
//      if ((value.getValueId() != null && !value.getValueId().equals(""))
//          && (value.getValueName() != null && !"".equals(value.getValueName()))) {
//        contain.add(value);
//      }
//    }
//    this.propertyValueContain = contain;
//  }

  /**
   * @return the isColor
   */
  public Long getIsColor() {
    return isColor;
  }

  /**
   * @param isColor the isColor to set
   */
  public void setIsColor(Long isColor) {
    this.isColor = isColor;
  }

  /**
   * @return the isSize
   */
  public Long getIsSize() {
    return isSize;
  }

  /**
   * @param isSize the isSize to set
   */
  public void setIsSize(Long isSize) {
    this.isSize = isSize;
  }

  /**
   * @return the isReq
   */
  public Long getIsReq() {
    return isReq;
  }

  /**
   * @param isReq the isReq to set
   */
  public void setIsReq(Long isReq) {
    this.isReq = isReq;
  }

  /**
   * @return the isFet
   */
  public Long getIsFet() {
    return isFet;
  }

  /**
   * @param isFet the isFet to set
   */
  public void setIsFet(Long isFet) {
    this.isFet = isFet;
  }

  /**
   * @return the isNav
   */
  public Long getIsNav() {
    return isNav;
  }

  /**
   * @param isNav the isNav to set
   */
  public void setIsNav(Long isNav) {
    this.isNav = isNav;
  }

  /**
   * @return the attType
   */
  public Long getAttType() {
    return attType;
  }

  /**
   * @param attType the attType to set
   */
  public void setAttType(Long attType) {
    this.attType = attType;
  }

  /**
   * @return the inputType
   */
  public Long getInputType() {
    return inputType;
  }

  /**
   * @param inputType the inputType to set
   */
  public void setInputType(Long inputType) {
    this.inputType = inputType;
  }

  /**
   * @return the displayOrder
   */
  public Long getDisplayOrder() {
    return displayOrder;
  }

  /**
   * @param displayOrder the displayOrder to set
   */
  public void setDisplayOrder(Long displayOrder) {
    this.displayOrder = displayOrder;
  }
}
