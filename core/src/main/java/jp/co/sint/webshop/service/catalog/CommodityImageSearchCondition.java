package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.DateUtil;

public class CommodityImageSearchCondition extends SearchCondition {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "同期開始日時(From)")
  private String searchUploadTime;
  
  private String[] skuList;
  
  private String shopCode;

  /**
   * @return the searchUploadTime
   */
  public String getSearchUploadTime() {
    return searchUploadTime;
  }

  /**
   * @param searchUploadTime the searchUploadTime to set
   */
  public void setSearchUploadTime(String searchUploadTime) {
    this.searchUploadTime = searchUploadTime;
  }

  /**
   * @return the skuList
   */
  public String[] getSkuList() {
    return skuList;
  }

  /**
   * @param skuList the skuList to set
   */
  public void setSkuList(String[] skuList) {
    this.skuList = skuList;
  }

  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }
}
