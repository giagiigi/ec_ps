package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;

import jp.co.sint.webshop.data.attribute.Metadata;

public class NewPublicCouponDetailsCondition implements Serializable {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** 検索订单日期 */
  @Metadata(name = "検索订单日期")
  private String searchOrderDate;

  /** 検索优惠代码 */
  @Metadata(name = "検索优惠代码")
  private String searchDiscountCode;

  /** 订单来源 */
  @Metadata(name = "订单来源")
  private String searchMobileComputerType;
  
  @Metadata(name = "制作订单设备类型")
  private String orderClientType;

  /** 集計タイプ */
  @Metadata(name = "集計タイプ", order = 6)
  private CountType type;

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
   * @return the searchOrderDate
   */
  public String getSearchOrderDate() {
    return searchOrderDate;
  }

  /**
   * @param searchOrderDate
   *          the searchOrderDate to set
   */
  public void setSearchOrderDate(String searchOrderDate) {
    this.searchOrderDate = searchOrderDate;
  }

  /**
   * @return the searchDiscountCode
   */
  public String getSearchDiscountCode() {
    return searchDiscountCode;
  }

  /**
   * @param searchDiscountCode
   *          the searchDiscountCode to set
   */
  public void setSearchDiscountCode(String searchDiscountCode) {
    this.searchDiscountCode = searchDiscountCode;
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
