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
 * 「JD类目(JD_Category)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author OB
 */
public class JdCategory implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 类目编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "类目编号", order = 1)
  private String categoryId;

  /** 类目名称 */
  @Required
  @Length(50)
  @Metadata(name = "类目名称", order = 2)
  private String categoryName;

  @Length(16)
  @Metadata(name = "父类编号", order = 3)
  private String parentId;

  /** 是否是父类目ID */
  @Required
  @Length(1)
  @Metadata(name = "是否是父类目ID", order = 4)
  private Long isParent;

  /** 等级 */
  @Required
  @Length(1)
  @Metadata(name = "等级", order = 5)
  private Long level;
  
  /** 表示顺序 */
  @Length(16)
  @Metadata(name = "表示顺序", order = 6)
  private Long displayOrder;
  
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 7)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 8)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 9)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 10)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 11)
  private Date updatedDatetime;

  /**
   * @param isParent
   *          the isParent to set
   */
  public void setIsParent(Long isParent) {
    this.isParent = isParent;
  }


  public boolean isParentBoolean() {
    return this.isParent == 1;
  }

  /**
   * @return the categoryName
   */
  public String getCategoryName() {
    return categoryName;
  }

  /**
   * @param categoryName
   *          the categoryName to set
   */
  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
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

  public Long getIsParent() {
    return isParent;
  }

  /**
   * @return the categoryId
   */
  public String getCategoryId() {
    return categoryId;
  }

  /**
   * @param categoryId the categoryId to set
   */
  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  /**
   * @return the parentId
   */
  public String getParentId() {
    return parentId;
  }

  /**
   * @param parentId the parentId to set
   */
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  /**
   * @return the level
   */
  public Long getLevel() {
    return level;
  }

  /**
   * @param level the level to set
   */
  public void setLevel(Long level) {
    this.level = level;
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
