//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.attribute.Url;

/**
 * 「商品ヘッダ(COMMODITY_HEADER)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class ImageUploadHistory implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** 代表SKUコード */
  @PrimaryKey(2)
  @Required
  @Length(24)
  @AlphaNum2
  @Metadata(name = "代表SKUコード", order = 2)
  private String skuCode;

  /** 商品コード */
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード", order = 3)
  private String commodityCode;

  /** 淘宝商城图片url */
  @Length(256)
  @Url
  @Metadata(name = "淘宝商城图片url", order = 4)
  private String tmallImgUrl;

  /** 淘宝商城图片id */
  @Length(256)
  @Metadata(name = "淘宝商城图片id", order = 5)
  private String tmallImgId;

  /** 本地图片处理状态 */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "本地图片处理状态", order = 6)
  private Long localOperFlg = 0L;

  /** 淘宝上传状态ID */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "淘宝上传状态", order = 7)
  private Long tmallUploadFlg = 0L;

  /** 官网1上传状态 */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "官网1上传状态", order = 8)
  private Long Ec1UploadFlg = 0L;

  /** 官网2上传状态 */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "官网2上传状态", order = 9)
  private Long Ec2UploadFlg = 0L;

  /** 图片上传时间 */
  @Metadata(name = "图片上传时间", order = 10)
  private Date uploadDatetime;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 11)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 12)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 13)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 14)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 15)
  private Date updatedDatetime;

  // 20130905 txw add start
  /** 上传图片名称 */
  @Length(50)
  @Metadata(name = "上传图片名称", order = 16)
  private String uploadCommodityImg;

  // 20130905 txw add end
  
  // 2014/04/30 京东WBS对应 ob_姚 add start
  /** JD上传状态 */
  @Length(1)
  @Bool
  @Metadata(name = "JD上传状态", order = 17)
  private Long jdUploadFlg = 0L;
  
  /** JD用属性值ID（0-5；0：主图） */
  @Length(16)
  @Metadata(name = "JD用属性值ID（0-5；0：主图）", order = 18)
  private String jdAttId;
  
  /** JD用图片URL */
  @Length(256)
  @Url
  @Metadata(name = "JD用图片URL", order =19)
  private String jdImgUrl;

  /** JD用图片ID */
  @Length(256)
  @Metadata(name = "JD用图片ID", order = 20)
  private String jdImageId;
  
  /** JD图片上传时间 */
  @Metadata(name = "JD上传时间", order = 21)
  private Date jdUploadTime;
  // 2014/04/30 京东WBS对应 ob_姚 add end

  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @return the uploadCommodityImg
   */
  public String getUploadCommodityImg() {
    return uploadCommodityImg;
  }

  /**
   * @param uploadCommodityImg
   *          the uploadCommodityImg to set
   */
  public void setUploadCommodityImg(String uploadCommodityImg) {
    this.uploadCommodityImg = uploadCommodityImg;
  }

  /**
   * @param shopCode
   *          the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return the skuCode
   */
  public String getSkuCode() {
    return skuCode;
  }

  /**
   * @param skuCode
   *          the skuCode to set
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @return the tmallImgUrl
   */
  public String getTmallImgUrl() {
    return tmallImgUrl;
  }

  /**
   * @param tmallImgUrl
   *          the tmallImgUrl to set
   */
  public void setTmallImgUrl(String tmallImgUrl) {
    this.tmallImgUrl = tmallImgUrl;
  }

  /**
   * @return the tmallImgId
   */
  public String getTmallImgId() {
    return tmallImgId;
  }

  /**
   * @param tmallImgId
   *          the tmallImgId to set
   */
  public void setTmallImgId(String tmallImgId) {
    this.tmallImgId = tmallImgId;
  }

  /**
   * @return the localOperFlg
   */
  public Long getLocalOperFlg() {
    return localOperFlg;
  }

  /**
   * @param localOperFlg
   *          the localOperFlg to set
   */
  public void setLocalOperFlg(Long localOperFlg) {
    this.localOperFlg = localOperFlg;
  }

  /**
   * @return the tmallUploadFlg
   */
  public Long getTmallUploadFlg() {
    return tmallUploadFlg;
  }

  /**
   * @param tmallUploadFlg
   *          the tmallUploadFlg to set
   */
  public void setTmallUploadFlg(Long tmallUploadFlg) {
    this.tmallUploadFlg = tmallUploadFlg;
  }

  /**
   * @return the ec1UploadFlg
   */
  public Long getEc1UploadFlg() {
    return Ec1UploadFlg;
  }

  /**
   * @param ec1UploadFlg
   *          the ec1UploadFlg to set
   */
  public void setEc1UploadFlg(Long ec1UploadFlg) {
    Ec1UploadFlg = ec1UploadFlg;
  }

  /**
   * @return the ec2UploadFlg
   */
  public Long getEc2UploadFlg() {
    return Ec2UploadFlg;
  }

  /**
   * @param ec2UploadFlg
   *          the ec2UploadFlg to set
   */
  public void setEc2UploadFlg(Long ec2UploadFlg) {
    Ec2UploadFlg = ec2UploadFlg;
  }

  /**
   * @return the uploadDatetime
   */
  public Date getUploadDatetime() {
    return uploadDatetime;
  }

  /**
   * @param uploadDatetime
   *          the uploadDatetime to set
   */
  public void setUploadDatetime(Date uploadDatetime) {
    this.uploadDatetime = uploadDatetime;
  }

  /**
   * @return the ormRowid
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  /**
   * @param ormRowid
   *          the ormRowid to set
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * @return the createdUser
   */
  public String getCreatedUser() {
    return createdUser;
  }

  /**
   * @param createdUser
   *          the createdUser to set
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * @return the createdDatetime
   */
  public Date getCreatedDatetime() {
    return createdDatetime;
  }

  /**
   * @param createdDatetime
   *          the createdDatetime to set
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  /**
   * @return the updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * @param updatedUser
   *          the updatedUser to set
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * @return the updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return updatedDatetime;
  }

  /**
   * @param updatedDatetime
   *          the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  /**
   * @return the jdUploadFlg
   */
  public Long getJdUploadFlg() {
    return jdUploadFlg;
  }

  /**
   * @param jdUploadFlg the jdUploadFlg to set
   */
  public void setJdUploadFlg(Long jdUploadFlg) {
    this.jdUploadFlg = jdUploadFlg;
  }

  /**
   * @return the jdAttId
   */
  public String getJdAttId() {
    return jdAttId;
  }

  /**
   * @param jdAttId the jdAttId to set
   */
  public void setJdAttId(String jdAttId) {
    this.jdAttId = jdAttId;
  }

  /**
   * @return the jdImgUrl
   */
  public String getJdImgUrl() {
    return jdImgUrl;
  }

  /**
   * @param jdImgUrl the jdImgUrl to set
   */
  public void setJdImgUrl(String jdImgUrl) {
    this.jdImgUrl = jdImgUrl;
  }

  /**
   * @return the jdImageId
   */
  public String getJdImageId() {
    return jdImageId;
  }

  /**
   * @param jdImageId the jdImageId to set
   */
  public void setJdImageId(String jdImageId) {
    this.jdImageId = jdImageId;
  }

  /**
   * @return the jdUploadTime
   */
  public Date getJdUploadTime() {
    return jdUploadTime;
  }

  /**
   * @param jdUploadTime the jdUploadTime to set
   */
  public void setJdUploadTime(Date jdUploadTime) {
    this.jdUploadTime = jdUploadTime;
  }
}
