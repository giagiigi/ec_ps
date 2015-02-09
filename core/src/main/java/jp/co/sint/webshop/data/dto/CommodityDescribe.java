//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// ���̃t�@�C����EDM�t�@�C�����玩����������܂��B
// ���ڕҏW���Ȃ��ŉ������B
//
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
 * 「商品描述(commodity_describe)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class CommodityDescribe implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "商品コード", order = 2)
  private String commodityCode;

  /** 描述内容 */
  @Metadata(name = "描述内容CN", order = 3)
  private String decribeCn;
  
  /** 描述内容 */
  @Metadata(name = "描述内容En", order = 4)
  private String decribeEn;
  
  /** 描述内容 */
  @Metadata(name = "描述内容Jp", order = 5)
  private String decribeJp;

  /** 描述内容 */
  @Metadata(name = "描述内容Tmall", order = 6)
  private String decribeTmall;
  
  /** ROWID */
  @Required
  @Length(38)
  @Metadata(name = "ROWID", order = 7)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 创建者 */
  @Required
  @Length(100)
  @Metadata(name = "创建者", order = 8)
  private String createdUser;

  /** 创建时间 */
  @Required
  @Metadata(name = "创建时间", order = 9)
  private Date createdDatetime;

  /** 更新者 */
  @Required
  @Length(100)
  @Metadata(name = "更新者", order = 10)
  private String updatedUser;

  /** 更新时间 */
  @Required
  @Metadata(name = "更新时间", order = 11)
  private Date updatedDatetime;
  
  // 2014/04/30 京东WBS对应 ob_姚 add start
  /** 京东描述内容 */
  @Metadata(name = "京东描述内容", order = 12)
  private String decribeJd;
  // 2014/04/30 京东WBS对应 ob_姚 add end

  /**
   * �V���b�v�R�[�h��擾���܂�
   *
   * @return �V���b�v�R�[�h
   */
  public String getShopCode() {
    return this.shopCode;
  }


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
   * �V���b�v�R�[�h��ݒ肵�܂�
   *
   * @param  val �V���b�v�R�[�h
   */
  public void setShopCode(String val) {
    this.shopCode = val;
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
   * @return the decribeCn
   */
  public String getDecribeCn() {
    return decribeCn;
  }

  
  /**
   * @param decribeCn the decribeCn to set
   */
  public void setDecribeCn(String decribeCn) {
    this.decribeCn = decribeCn;
  }

  
  /**
   * @return the decribeEn
   */
  public String getDecribeEn() {
    return decribeEn;
  }

  
  /**
   * @param decribeEn the decribeEn to set
   */
  public void setDecribeEn(String decribeEn) {
    this.decribeEn = decribeEn;
  }

  
  /**
   * @return the decribeJp
   */
  public String getDecribeJp() {
    return decribeJp;
  }

  
  /**
   * @param decribeJp the decribeJp to set
   */
  public void setDecribeJp(String decribeJp) {
    this.decribeJp = decribeJp;
  }

  
  /**
   * @return the decribeTmall
   */
  public String getDecribeTmall() {
    return decribeTmall;
  }

  
  /**
   * @param decribeTmall the decribeTmall to set
   */
  public void setDecribeTmall(String decribeTmall) {
    this.decribeTmall = decribeTmall;
  }


  /**
   * @return the decribeJd
   */
  public String getDecribeJd() {
    return decribeJd;
  }


  /**
   * @param decribeJd the decribeJd to set
   */
  public void setDecribeJd(String decribeJd) {
    this.decribeJd = decribeJd;
  }

}
