package jp.co.sint.webshop.service.data;


public enum ImageType {
  
  /** 商品画像(PC) */
  COMMODITY_IMAGE_PC(""),
  /** 商品画像(携帯) */
  COMMODITY_IMAGE_MOBILE("_m"),
  /** 商品サムネイル画像(PC) */
  COMMODITY_THUMBNAIL_PC("_t"),
  /** 商品サムネイル画像(携帯) */
  COMMODITY_THUMBNAIL_MOBILE("_mt"),
  /** 規格画像(PC) */
  SKU_IMAGE_PC("_k"),
  /** 規格画像(携帯) */
  SKU_IMAGE_MOBILE("_mk"),
  /** ギフト画像 */
  GIFT_IMAGE("_g"),
  // 20131024 txw add start
  /** 首页TOP图片 */
  INDEX_TOP_IMAGE(""),
  /** 首页楼层图片 */
  INDEX_FLOOR_IMAGE("");
  // 20131024 txw add end
  
  
  private String suffix;
  
  private ImageType(String suffix) {
    this.suffix = suffix;
  }
  
  /**
   * suffixを取得します。
   *
   * @return suffix
   */
  public String getSuffix() {
    return suffix;
  }

}
