//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// ���̃t�@�C����EDM�t�@�C�����玩����������܂��B
// ���ڕҏW���Ȃ��ŉ������B
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * �u���ߍ���HTML(EMBEDDED_HTML)�v�e�[�u����1�s���̃��R�[�h��\��DTO(Data Transfer Object)�ł��B
 *
 * @author System Integrator Corp.
 *
 */
public class IndexBatchCommodity implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "商品编号", order = 1)
  private String commodityCode;

  @Required
  @Length(50)
  @Metadata(name = "中文名", order = 2)
  private String commodityNameCn;
  
  @Length(200)
  @Metadata(name = "日文名", order = 3)
  private String commodityNameEn;
  
  @Length(200)
  @Metadata(name = "英文名", order = 4)
  private String commodityNameJp;

  @Required
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "单价", order = 5)
  private BigDecimal unitPrice;
  
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "折扣价", order = 6)
  private BigDecimal discountPrice;
  
  @Length(8)
  @Metadata(name = "内包装数", order = 7)
  private Long innerQuantity;
  
  @Required
  @Length(1)
  @Metadata(name = "商品对应滑动门种别（ 0：新品上架 1：热销商品）", order = 8)
  private Long commodityType;
  
  @Required
  @Length(1)
  @Metadata(name = "进口商品区分(1:全进口、2:海外品牌、9:普通商品)", order = 9)
  private Long importCommodityType;
  
  @Required
  @Length(1)
  @Metadata(name = "清仓商品区分(1:清仓商品、2/9:普通商品)", order = 10)
  private Long clearCommodityType;
  
  @Required
  @Length(1)
  @Metadata(name = "hot商品区分(1:是 9:不是)", order = 11)
  private Long hotCommodityType;
  
  /** ROWID */
  @Required
  @Length(38)
  @Metadata(name = "ROWID", order = 12)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 创建者 */
  @Required
  @Length(100)
  @Metadata(name = "创建者", order = 13)
  private String createdUser;

  /** 创建时间 */
  @Required
  @Metadata(name = "创建时间", order = 14)
  private Date createdDatetime;

  /** 更新者 */
  @Required
  @Length(100)
  @Metadata(name = "更新者", order = 15)
  private String updatedUser;

  /** 更新时间 */
  @Required
  @Metadata(name = "更新时间", order = 16)
  private Date updatedDatetime;


  /**
   * �f�[�^�sID��擾���܂�
   *
   * @return �f�[�^�sID
   */
  public Long getOrmRowid() {
    return this.ormRowid;
  }

  /**
   * �쐬���[�U��擾���܂�
   *
   * @return �쐬���[�U
   */
  public String getCreatedUser() {
    return this.createdUser;
  }

  /**
   * �쐬���擾���܂�
   *
   * @return �쐬��
   */
  public Date getCreatedDatetime() {
    return DateUtil.immutableCopy(this.createdDatetime);
  }

  /**
   * �X�V���[�U��擾���܂�
   *
   * @return �X�V���[�U
   */
  public String getUpdatedUser() {
    return this.updatedUser;
  }

  /**
   * �X�V���擾���܂�
   *
   * @return �X�V��
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(this.updatedDatetime);
  }

  /**
   * �f�[�^�sID��ݒ肵�܂�
   *
   * @param  val �f�[�^�sID
   */
  public void setOrmRowid(Long val) {
    this.ormRowid = val;
  }

  /**
   * �쐬���[�U��ݒ肵�܂�
   *
   * @param  val �쐬���[�U
   */
  public void setCreatedUser(String val) {
    this.createdUser = val;
  }

  /**
   * �쐬���ݒ肵�܂�
   *
   * @param  val �쐬��
   */
  public void setCreatedDatetime(Date val) {
    this.createdDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * �X�V���[�U��ݒ肵�܂�
   *
   * @param  val �X�V���[�U
   */
  public void setUpdatedUser(String val) {
    this.updatedUser = val;
  }

  /**
   * �X�V���ݒ肵�܂�
   *
   * @param  val �X�V��
   */
  public void setUpdatedDatetime(Date val) {
    this.updatedDatetime = DateUtil.immutableCopy(val);
  }

  
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
   * @return the commodityNameCn
   */
  public String getCommodityNameCn() {
    return commodityNameCn;
  }

  
  /**
   * @param commodityNameCn the commodityNameCn to set
   */
  public void setCommodityNameCn(String commodityNameCn) {
    this.commodityNameCn = commodityNameCn;
  }

  
  /**
   * @return the commodityNameEn
   */
  public String getCommodityNameEn() {
    return commodityNameEn;
  }

  
  /**
   * @param commodityNameEn the commodityNameEn to set
   */
  public void setCommodityNameEn(String commodityNameEn) {
    this.commodityNameEn = commodityNameEn;
  }

  
  /**
   * @return the commodityNameJp
   */
  public String getCommodityNameJp() {
    return commodityNameJp;
  }

  
  /**
   * @param commodityNameJp the commodityNameJp to set
   */
  public void setCommodityNameJp(String commodityNameJp) {
    this.commodityNameJp = commodityNameJp;
  }

  
  /**
   * @return the unitPrice
   */
  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  
  /**
   * @param unitPrice the unitPrice to set
   */
  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  
  /**
   * @return the discountPrice
   */
  public BigDecimal getDiscountPrice() {
    return discountPrice;
  }

  
  /**
   * @param discountPrice the discountPrice to set
   */
  public void setDiscountPrice(BigDecimal discountPrice) {
    this.discountPrice = discountPrice;
  }

  
  /**
   * @return the innerQuantity
   */
  public Long getInnerQuantity() {
    return innerQuantity;
  }

  
  /**
   * @param innerQuantity the innerQuantity to set
   */
  public void setInnerQuantity(Long innerQuantity) {
    this.innerQuantity = innerQuantity;
  }

  
  /**
   * @return the commodityType
   */
  public Long getCommodityType() {
    return commodityType;
  }

  
  /**
   * @param commodityType the commodityType to set
   */
  public void setCommodityType(Long commodityType) {
    this.commodityType = commodityType;
  }

  
  /**
   * @return the importCommodityType
   */
  public Long getImportCommodityType() {
    return importCommodityType;
  }

  
  /**
   * @param importCommodityType the importCommodityType to set
   */
  public void setImportCommodityType(Long importCommodityType) {
    this.importCommodityType = importCommodityType;
  }

  
  /**
   * @return the clearCommodityType
   */
  public Long getClearCommodityType() {
    return clearCommodityType;
  }

  
  /**
   * @param clearCommodityType the clearCommodityType to set
   */
  public void setClearCommodityType(Long clearCommodityType) {
    this.clearCommodityType = clearCommodityType;
  }

  
  /**
   * @return the hotCommodityType
   */
  public Long getHotCommodityType() {
    return hotCommodityType;
  }

  
  /**
   * @param hotCommodityType the hotCommodityType to set
   */
  public void setHotCommodityType(Long hotCommodityType) {
    this.hotCommodityType = hotCommodityType;
  }

}
