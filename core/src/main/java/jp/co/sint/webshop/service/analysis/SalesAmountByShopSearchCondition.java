package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;

public class SalesAmountByShopSearchCondition implements Serializable {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** 検索年 */
  @Required
  @Metadata(name = "検索年")
  private int searchYear;

  /** 検索月 */
  @Metadata(name = "検索月")
  private int searchMonth;

  /** ショップコード */
  @Required
  @Length(16)
  @Metadata(name = "ショップコード")
  private String shopCode;

  /** クライアントグループ */
  @Required
  @Length(2)
  @Metadata(name = "クライアントグループ")
  private String clientGroup;

  /** 支払方法番号 */
  @Length(8)
  @Metadata(name = "支払い方法番号")
  private Long paymentMethodNo;

  /** 支払区分 */
  @Length(2)
  @Metadata(name = "支払い方法区分")
  private String paymentMethodType;

  /** 集計タイプ */
  @Required
  @Metadata(name = "集計タイプ", order = 6)
  private CountType type;

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
   * searchYearを返します。
   * 
   * @return the searchYear
   */
  public int getSearchYear() {
    return searchYear;
  }

  /**
   * searchMonthを返します。
   * 
   * @return the searchMonth
   */
  public int getSearchMonth() {
    return searchMonth;
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
   * clientGroupを返します。
   * 
   * @return the clientGroup
   */
  public String getClientGroup() {
    return clientGroup;
  }

  /**
   * paymentMethodNoを返します。
   * 
   * @return the paymentMethodNo
   */
  public Long getPaymentMethodNo() {
    return paymentMethodNo;
  }

  /**
   * searchYearを設定します。
   * 
   * @param searchYear
   *          設定する searchYear
   */
  public void setSearchYear(int searchYear) {
    this.searchYear = searchYear;
  }

  /**
   * searchMonthを設定します。
   * 
   * @param searchMonth
   *          設定する searchMonth
   */
  public void setSearchMonth(int searchMonth) {
    this.searchMonth = searchMonth;
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
   * clientGroupを設定します。
   * 
   * @param clientGroup
   *          設定する clientGroup
   */
  public void setClientGroup(String clientGroup) {
    this.clientGroup = clientGroup;
  }

  /**
   * paymentMethodNoを設定します。
   * 
   * @param paymentMethodNo
   *          設定する paymentMethodNo
   */
  public void setPaymentMethodNo(Long paymentMethodNo) {
    this.paymentMethodNo = paymentMethodNo;
  }

  public String getPaymentMethodType() {
    return paymentMethodType;
  }

  public void setPaymentMethodType(String paymentMethodType) {
    this.paymentMethodType = paymentMethodType;
  }

}
