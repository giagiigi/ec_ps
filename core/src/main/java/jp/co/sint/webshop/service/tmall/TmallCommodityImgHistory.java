package jp.co.sint.webshop.service.tmall;

import java.io.Serializable;

public class TmallCommodityImgHistory implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;
  
  //图片分类ID 必须
  private Long pictureCategoryId;
  
  // 浏览上传图片的URL  必须
  private String imageUrl;
  
  //包括后缀名的图片标题  必须
  private String imageInputTitle ;
  
  //图片标题  可选
  private String title;
  
  // 返回结果的图片url
  private String returnImgUrl;

  
  /**
   * @return the pictureCategoryId
   */
  public Long getPictureCategoryId() {
    return pictureCategoryId;
  }

  
  /**
   * @param pictureCategoryId the pictureCategoryId to set
   */
  public void setPictureCategoryId(Long pictureCategoryId) {
    this.pictureCategoryId = pictureCategoryId;
  }

  
  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  
  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }


  
  /**
   * @return the imageUrl
   */
  public String getImageUrl() {
    return imageUrl;
  }


  
  /**
   * @param imageUrl the imageUrl to set
   */
  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }


  
  /**
   * @return the imageInputTitle
   */
  public String getImageInputTitle() {
    return imageInputTitle;
  }


  
  /**
   * @param imageInputTitle the imageInputTitle to set
   */
  public void setImageInputTitle(String imageInputTitle) {
    this.imageInputTitle = imageInputTitle;
  }


  
  /**
   * @return the returnImgUrl
   */
  public String getReturnImgUrl() {
    return returnImgUrl;
  }


  
  /**
   * @param returnImgUrl the returnImgUrl to set
   */
  public void setReturnImgUrl(String returnImgUrl) {
    this.returnImgUrl = returnImgUrl;
  }
  

  

}
