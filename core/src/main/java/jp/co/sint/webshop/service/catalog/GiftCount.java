package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

public class GiftCount implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** ギフトコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ギフトコード", order = 2)
  private String giftCode;

  /** ギフト名称 */
  @Required
  @Length(40)
  @Metadata(name = "ギフト名称", order = 3)
  private String giftName;

  /** ギフト説明 */
  @Length(200)
  @Metadata(name = "ギフト説明", order = 4)
  private String giftDescription;

  /** ギフト価格 */
  @Required
  @Length(8)
  @Currency
  @Metadata(name = "ギフト価格", order = 5)
  private BigDecimal giftPrice;

  /** 表示順 */
  @Length(4)
  @Metadata(name = "表示順", order = 6)
  private Long displayOrder;

  /** ギフト消費税区分 */
  @Required
  @Length(1)
  @Metadata(name = "ギフト消費税区分", order = 7)
  private Long giftTaxType;

  /** 表示フラグ */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "表示フラグ", order = 8)
  private Long displayFlg;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 9)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(16)
  @Metadata(name = "作成ユーザ", order = 10)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 11)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(16)
  @Metadata(name = "更新ユーザ", order = 12)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 13)
  private Date updatedDatetime;

  /** 商品の関連付き件数 */
  private Long relatedCount;

  /**
   * createdDatetimeを取得します。
   * 
   * @return createdDatetime
   */
  public Date getCreatedDatetime() {
    return DateUtil.immutableCopy(createdDatetime);
  }

  /**
   * createdUserを取得します。
   * 
   * @return createdUser
   */
  public String getCreatedUser() {
    return createdUser;
  }

  /**
   * displayFlgを取得します。
   * 
   * @return displayFlg
   */
  public Long getDisplayFlg() {
    return displayFlg;
  }

  /**
   * displayOrderを取得します。
   * 
   * @return displayOrder
   */
  public Long getDisplayOrder() {
    return displayOrder;
  }

  /**
   * giftCodeを取得します。
   * 
   * @return giftCode
   */
  public String getGiftCode() {
    return giftCode;
  }

  /**
   * giftDescriptionを取得します。
   * 
   * @return giftDescription
   */
  public String getGiftDescription() {
    return giftDescription;
  }

  /**
   * giftNameを取得します。
   * 
   * @return giftName
   */
  public String getGiftName() {
    return giftName;
  }

  /**
   * giftPriceを取得します。
   * 
   * @return giftPrice
   */
  public BigDecimal getGiftPrice() {
    return giftPrice;
  }

  /**
   * giftTaxTypeを取得します。
   * 
   * @return giftTaxType
   */
  public Long getGiftTaxType() {
    return giftTaxType;
  }

  /**
   * ormRowidを取得します。
   * 
   * @return ormRowid
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  /**
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * updatedDatetimeを取得します。
   * 
   * @return updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * updatedUserを取得します。
   * 
   * @return updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * createdDatetimeを設定します。
   * 
   * @param createdDatetime
   *          createdDatetime
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = DateUtil.immutableCopy(createdDatetime);
  }

  /**
   * createdUserを設定します。
   * 
   * @param createdUser
   *          createdUser
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * displayFlgを設定します。
   * 
   * @param displayFlg
   *          displayFlg
   */
  public void setDisplayFlg(Long displayFlg) {
    this.displayFlg = displayFlg;
  }

  /**
   * displayOrderを設定します。
   * 
   * @param displayOrder
   *          displayOrder
   */
  public void setDisplayOrder(Long displayOrder) {
    this.displayOrder = displayOrder;
  }

  /**
   * giftCodeを設定します。
   * 
   * @param giftCode
   *          giftCode
   */
  public void setGiftCode(String giftCode) {
    this.giftCode = giftCode;
  }

  /**
   * giftDescriptionを設定します。
   * 
   * @param giftDescription
   *          giftDescription
   */
  public void setGiftDescription(String giftDescription) {
    this.giftDescription = giftDescription;
  }

  /**
   * giftNameを設定します。
   * 
   * @param giftName
   *          giftName
   */
  public void setGiftName(String giftName) {
    this.giftName = giftName;
  }

  /**
   * giftPriceを設定します。
   * 
   * @param giftPrice
   *          giftPrice
   */
  public void setGiftPrice(BigDecimal giftPrice) {
    this.giftPrice = giftPrice;
  }

  /**
   * giftTaxTypeを設定します。
   * 
   * @param giftTaxType
   *          giftTaxType
   */
  public void setGiftTaxType(Long giftTaxType) {
    this.giftTaxType = giftTaxType;
  }

  /**
   * ormRowidを設定します。
   * 
   * @param ormRowid
   *          ormRowid
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * updatedDatetimeを設定します。
   * 
   * @param updatedDatetime
   *          updatedDatetime
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * updatedUserを設定します。
   * 
   * @param updatedUser
   *          updatedUser
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * relatedCountを取得します。
   * 
   * @return relatedCount
   */
  public Long getRelatedCount() {
    return relatedCount;
  }

  /**
   * relatedCountを設定します。
   * 
   * @param relatedCount
   *          relatedCount
   */
  public void setRelatedCount(Long relatedCount) {
    this.relatedCount = relatedCount;
  }
}
