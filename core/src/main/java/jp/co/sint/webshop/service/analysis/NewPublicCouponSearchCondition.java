package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;

public class NewPublicCouponSearchCondition implements Serializable {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** 検索年 */
  @Required
  @Metadata(name = "検索年")
  private String searchStartYear;

  /** 検索年 */
  @Required
  @Metadata(name = "検索年")
  private String searchEndYear;

  /** 検索月 */
  @Metadata(name = "検索月")
  private String searchStartMonth;

  /** 検索月 */
  @Metadata(name = "検索月")
  private String searchEndMonth;

  /** 検索時間 */
  @Required
  @Metadata(name = "検索時間")
  private String searchStartDate;

  /** 検索時間 */
  @Required
  @Metadata(name = "検索時間")
  private String searchEndDate;

  /** ショップコード */
  @Required
  @Length(16)
  @Metadata(name = "ショップコード")
  private String shopCode;

  /** 集計タイプ */
  @Required
  @Metadata(name = "集計タイプ", order = 6)
  private CountType type;

  /** 订单来源 */
  @Metadata(name = "订单来源")
  private String searchMobileComputerType;
  
  @Metadata(name = "制作订单设备类型")
  private String orderClientType;

  public String getSearchMobileComputerType() {
    return searchMobileComputerType;
  }

  public void setSearchMobileComputerType(String searchMobileComputerType) {
    this.searchMobileComputerType = searchMobileComputerType;
  }

  /**
   * typeを返します。
   * 
   * @return the type
   */
  public CountType getType() {
    return type;
  }

  /**
   * typeを設定します。
   * 
   * @param type
   *          設定する type
   */
  public void setType(CountType type) {
    this.type = type;
  }

  /**
   * shopCodeを返します。
   * 
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return the searchStartYear
   */
  public String getSearchStartYear() {
    return searchStartYear;
  }

  /**
   * @param searchStartYear
   *          the searchStartYear to set
   */
  public void setSearchStartYear(String searchStartYear) {
    this.searchStartYear = searchStartYear;
  }

  /**
   * @return the searchEndYear
   */
  public String getSearchEndYear() {
    return searchEndYear;
  }

  /**
   * @param searchEndYear
   *          the searchEndYear to set
   */
  public void setSearchEndYear(String searchEndYear) {
    this.searchEndYear = searchEndYear;
  }

  /**
   * @return the searchStartMonth
   */
  public String getSearchStartMonth() {
    return searchStartMonth;
  }

  /**
   * @param searchStartMonth
   *          the searchStartMonth to set
   */
  public void setSearchStartMonth(String searchStartMonth) {
    this.searchStartMonth = searchStartMonth;
  }

  /**
   * @return the searchEndMonth
   */
  public String getSearchEndMonth() {
    return searchEndMonth;
  }

  /**
   * @param searchEndMonth
   *          the searchEndMonth to set
   */
  public void setSearchEndMonth(String searchEndMonth) {
    this.searchEndMonth = searchEndMonth;
  }

  /**
   * @return the searchStartDate
   */
  public String getSearchStartDate() {
    return searchStartDate;
  }

  /**
   * @param searchStartDate
   *          the searchStartDate to set
   */
  public void setSearchStartDate(String searchStartDate) {
    this.searchStartDate = searchStartDate;
  }

  /**
   * @return the searchEndDate
   */
  public String getSearchEndDate() {
    return searchEndDate;
  }

  /**
   * @param searchEndDate
   *          the searchEndDate to set
   */
  public void setSearchEndDate(String searchEndDate) {
    this.searchEndDate = searchEndDate;
  }

  
  /**
   * @return the orderClientType
   */
  public String getOrderClientType() {
    return orderClientType;
  }

  
  /**
   * @param orderClientType the orderClientType to set
   */
  public void setOrderClientType(String orderClientType) {
    this.orderClientType = orderClientType;
  }

}
