package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.data.dto.StockIODetail;

public class StockIO extends StockIODetail {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 商品コード */
  private String commodityCode;

  /** 商品名 */
  private String commodityName;

  /** 規格詳細1名称 */
  private String standardDetail1Name;

  /** 規格詳細2名称 */
  private String standardDetail2Name;

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
   * @return the commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  /**
   * @param commodityName
   *          the commodityName to set
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * @return the standardDetail1Name
   */
  public String getStandardDetail1Name() {
    return standardDetail1Name;
  }

  /**
   * @param standardDetail1Name
   *          the standardDetail1Name to set
   */
  public void setStandardDetail1Name(String standardDetail1Name) {
    this.standardDetail1Name = standardDetail1Name;
  }

  /**
   * @return the standardDetail2Name
   */
  public String getStandardDetail2Name() {
    return standardDetail2Name;
  }

  /**
   * @param standardDetail2Name
   *          the standardDetail2Name to set
   */
  public void setStandardDetail2Name(String standardDetail2Name) {
    this.standardDetail2Name = standardDetail2Name;
  }

}
