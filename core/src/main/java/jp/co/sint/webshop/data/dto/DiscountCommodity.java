//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「限时限量折扣关联商品(DiscountCommodity)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class DiscountCommodity implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 折扣编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "折扣编号", order = 1)
  private String discountCode;

  /** 商品编号 */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品编号", order = 2)
  private String commodityCode;

  /** 折扣价格 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "折扣价格", order = 3)
  private BigDecimal discountPrice;

  /** 每个顾客最多购买数 */
  @Length(8)
  @Metadata(name = "每个顾客最多购买数", order = 4)
  private Long customerMaxTotalNum;

  /** 网站最多购买数 */
  @Length(8)
  @Metadata(name = "网站最多购买数", order = 5)
  private Long siteMaxTotalNum;

  /** 使用标志 */
  @Length(1)
  @Metadata(name = "使用标志", order = 6)
  private Long useFlg;

  /** 折扣说明(中文) */
  @Length(256)
  @Metadata(name = "折扣说明(中文)", order = 7)
  private String discountDirectionsCn;

  /** 折扣说明(中文) */
  @Length(256)
  @Metadata(name = "折扣说明(日文)", order = 8)
  private String discountDirectionsJp;

  /** 折扣说明(中文) */
  @Length(256)
  @Metadata(name = "折扣说明(英文)", order = 9)
  private String discountDirectionsEn;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 10)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 11)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 12)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 13)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 14)
  private Date updatedDatetime;
  
  @Length(5)
  @Digit
  @Quantity
  @Range(min = 0, max = 99999)
  @Metadata(name = "中文排序", order = 15)
  private Long rankCn;
  
  @Length(5)
  @Digit
  @Quantity
  @Range(min = 0, max = 99999)
  @Metadata(name = "日文排序", order = 16)
  private Long rankJp;
  
  @Length(5)
  @Digit
  @Quantity
  @Range(min = 0, max = 99999)
  @Metadata(name = "英文排序", order = 17)
  private Long rankEn;

  /**
   * @return the discountCode
   */
  public String getDiscountCode() {
    return discountCode;
  }

  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @return the discountPrice
   */
  public BigDecimal getDiscountPrice() {
    return discountPrice;
  }

  /**
   * @return the customerMaxTotalNum
   */
  public Long getCustomerMaxTotalNum() {
    return customerMaxTotalNum;
  }

  /**
   * @return the siteMaxTotalNum
   */
  public Long getSiteMaxTotalNum() {
    return siteMaxTotalNum;
  }

  /**
   * @return the ormRowid
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  /**
   * @return the createdUser
   */
  public String getCreatedUser() {
    return createdUser;
  }

  /**
   * @return the createdDatetime
   */
  public Date getCreatedDatetime() {
    return DateUtil.immutableCopy(createdDatetime);
  }

  /**
   * @return the updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * @return the updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * @return the useFlg
   */
  public Long getUseFlg() {
    return useFlg;
  }

  /**
   * @return the discountDirectionsCn
   */
  public String getDiscountDirectionsCn() {
    return discountDirectionsCn;
  }

  /**
   * @return the discountDirectionsJp
   */
  public String getDiscountDirectionsJp() {
    return discountDirectionsJp;
  }

  /**
   * @return the discountDirectionsEn
   */
  public String getDiscountDirectionsEn() {
    return discountDirectionsEn;
  }

  /**
   * @param useFlg
   *          the useFlg to set
   */
  public void setUseFlg(Long useFlg) {
    this.useFlg = useFlg;
  }

  /**
   * @param discountDirectionsCn
   *          the discountDirectionsCn to set
   */
  public void setDiscountDirectionsCn(String discountDirectionsCn) {
    this.discountDirectionsCn = discountDirectionsCn;
  }

  /**
   * @param discountDirectionsJp
   *          the discountDirectionsJp to set
   */
  public void setDiscountDirectionsJp(String discountDirectionsJp) {
    this.discountDirectionsJp = discountDirectionsJp;
  }

  /**
   * @param discountDirectionsEn
   *          the discountDirectionsEn to set
   */
  public void setDiscountDirectionsEn(String discountDirectionsEn) {
    this.discountDirectionsEn = discountDirectionsEn;
  }

  /**
   * @param discountCode
   *          the discountCode to set
   */
  public void setDiscountCode(String discountCode) {
    this.discountCode = discountCode;
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @param discountPrice
   *          the discountPrice to set
   */
  public void setDiscountPrice(BigDecimal discountPrice) {
    this.discountPrice = discountPrice;
  }

  /**
   * @param customerMaxTotalNum
   *          the customerMaxTotalNum to set
   */
  public void setCustomerMaxTotalNum(Long customerMaxTotalNum) {
    this.customerMaxTotalNum = customerMaxTotalNum;
  }

  /**
   * @param siteMaxTotalNum
   *          the siteMaxTotalNum to set
   */
  public void setSiteMaxTotalNum(Long siteMaxTotalNum) {
    this.siteMaxTotalNum = siteMaxTotalNum;
  }

  /**
   * @param ormRowid
   *          the ormRowid to set
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * @param createdUser
   *          the createdUser to set
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * @param createdDatetime
   *          the createdDatetime to set
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = DateUtil.immutableCopy(createdDatetime);
  }

  /**
   * @param updatedUser
   *          the updatedUser to set
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * @param updatedDatetime
   *          the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
  }

  
  /**
   * @return the rankCn
   */
  public Long getRankCn() {
    return rankCn;
  }

  
  /**
   * @param rankCn the rankCn to set
   */
  public void setRankCn(Long rankCn) {
    this.rankCn = rankCn;
  }

  
  /**
   * @return the rankJp
   */
  public Long getRankJp() {
    return rankJp;
  }

  
  /**
   * @param rankJp the rankJp to set
   */
  public void setRankJp(Long rankJp) {
    this.rankJp = rankJp;
  }

  
  /**
   * @return the rankEn
   */
  public Long getRankEn() {
    return rankEn;
  }

  
  /**
   * @param rankEn the rankEn to set
   */
  public void setRankEn(Long rankEn) {
    this.rankEn = rankEn;
  }

}
