package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.data.dto.Tag;

public class RelatedTag extends Tag {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  private String checkCode;

  private String commodityCode;

  /**
   * @return commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode
   *          設定する commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @return checkCode
   */
  public String getCheckCode() {
    return checkCode;
  }

  /**
   * @param checkCode
   *          設定する checkCode
   */
  public void setCheckCode(String checkCode) {
    this.checkCode = checkCode;
  }

}
