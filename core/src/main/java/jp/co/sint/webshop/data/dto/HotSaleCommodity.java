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
 * �u���ߍ���HTML(EMBEDDED_HTML)�v�e�[�u����1�s���̃��R�[�h��\��DTO(Data Transfer Object)�ł��B
 *
 * @author System Integrator Corp.
 *
 */
public class HotSaleCommodity implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "商品编号", order = 1)
  private String commodityCode;

  @Required
  @Length(5)
  @Metadata(name = "中文名", order = 2)
  private String commodityName;
  
  @Required
  @Length(50)
  @Metadata(name = "语言区分", order = 3)
  private String languageCode;
  
  @Required
  @Length(5)
  @Metadata(name = "排序", order = 4)
  private Long sortRank;
  
  /** ROWID */
  @Required
  @Length(38)
  @Metadata(name = "ROWID", order = 5)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 创建者 */
  @Required
  @Length(100)
  @Metadata(name = "创建者", order = 6)
  private String createdUser;

  /** 创建时间 */
  @Required
  @Metadata(name = "创建时间", order = 7)
  private Date createdDatetime;

  /** 更新者 */
  @Required
  @Length(100)
  @Metadata(name = "更新者", order = 8)
  private String updatedUser;

  /** 更新时间 */
  @Required
  @Metadata(name = "更新时间", order = 9)
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
   * @return the commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  
  /**
   * @param commodityName the commodityName to set
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  
  /**
   * @return the languageCode
   */
  public String getLanguageCode() {
    return languageCode;
  }

  
  /**
   * @param languageCode the languageCode to set
   */
  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  
  /**
   * @return the sortRank
   */
  public Long getSortRank() {
    return sortRank;
  }

  
  /**
   * @param sortRank the sortRank to set
   */
  public void setSortRank(Long sortRank) {
    this.sortRank = sortRank;
  }


}
