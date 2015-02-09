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
public class DescribeImgHistory implements Serializable, WebshopEntity {

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

  /** 图片名字 */
  @Length(16)
  @Metadata(name = "图片名字", order = 3)
  private String imgName;
  
  /** 天猫返回URL */
  @Length(4050)
  @Metadata(name = "天猫返回URL", order = 4)
  private String tmallReturnUrl;
  
  /** 下载图片URL */
  @Length(4050)
  @Metadata(name = "下载图片URL", order = 5)
  private String downloadImgUrl;
  
  /** 语种 */
  @Required
  @Length(10)
  @Metadata(name = "语种", order = 6)
  private String lang;

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
   * @return the imgName
   */
  public String getImgName() {
    return imgName;
  }


  
  /**
   * @param imgName the imgName to set
   */
  public void setImgName(String imgName) {
    this.imgName = imgName;
  }


  
  /**
   * @return the tmallReturnUrl
   */
  public String getTmallReturnUrl() {
    return tmallReturnUrl;
  }


  
  /**
   * @param tmallReturnUrl the tmallReturnUrl to set
   */
  public void setTmallReturnUrl(String tmallReturnUrl) {
    this.tmallReturnUrl = tmallReturnUrl;
  }


  
  /**
   * @return the downloadImgUrl
   */
  public String getDownloadImgUrl() {
    return downloadImgUrl;
  }


  
  /**
   * @param downloadImgUrl the downloadImgUrl to set
   */
  public void setDownloadImgUrl(String downloadImgUrl) {
    this.downloadImgUrl = downloadImgUrl;
  }


  
  /**
   * @return the lang
   */
  public String getLang() {
    return lang;
  }


  
  /**
   * @param lang the lang to set
   */
  public void setLang(String lang) {
    this.lang = lang;
  }

  
}
