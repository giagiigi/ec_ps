package jp.co.sint.webshop.service.tmall;

import java.io.Serializable;

public class TmallCommodityImg implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // 商品上传的图片ID(如果是更新图片，则必传该参数)(如果是新添加则返回此字段)
  private String imgId;

  // TMALL商品ID(必传参数)
  private String tmallCommodityId;

  // 浏览上传图片的URL(必传参数)
  private String imageUrl;

  // 是否将该图片设为主图,可选值:true(代表SKU图片PC),false(默认：非主图)
  private boolean is_major;

  // 返回结果的图片url
  private String returnImgUrl;

  // 图片顺序序号
  private long position;

  public String getReturnImgUrl() {
    return returnImgUrl;
  }

  public void setReturnImgUrl(String returnImgUrl) {
    this.returnImgUrl = returnImgUrl;
  }

  public String getImgId() {
    return imgId;
  }

  public void setImgId(String imgId) {
    this.imgId = imgId;
  }

  public String getTmallCommodityId() {
    return tmallCommodityId;
  }

  public void setTmallCommodityId(String tmallCommodityId) {
    this.tmallCommodityId = tmallCommodityId;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public boolean isIs_major() {
    return is_major;
  }

  public void setIs_major(boolean is_major) {
    this.is_major = is_major;
  }

  /**
   * @return the position
   */
  public long getPosition() {
    return position;
  }

  /**
   * @param position
   *          the position to set
   */
  public void setPosition(long position) {
    this.position = position;
  }

}
