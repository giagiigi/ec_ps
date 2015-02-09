package jp.co.sint.webshop.service.analysis;

import java.util.Date;

import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.DateUtil;

public class SalesAmountBySkuSearchCondition extends SearchCondition {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  @Datetime
  @Metadata(name = "検索期間(開始日)", order = 1)
  private Date searchStartDate;

  @Datetime
  @Metadata(name = "検索期間(終了日)", order = 2)
  private Date searchEndDate;

  @Length(16)
  @Metadata(name = "ショップコード", order = 3)
  private String shopCode;

  @Length(16)
  @Metadata(name = "商品コード(開始)", order = 4)
  private String commodityCodeStart;

  @Length(16)
  @Metadata(name = "商品コード(終了)", order = 5)
  private String commodityCodeEnd;

  @Length(100)
  @Metadata(name = "規格別商品名称", order = 6)
  private String commoditySkuName;

  /**
   * searchStartDateを返します。
   * 
   * @return the searchStartDate
   */
  public Date getSearchStartDate() {
    return DateUtil.immutableCopy(searchStartDate);
  }

  /**
   * searchEndDateを返します。
   * 
   * @return the searchEndDate
   */
  public Date getSearchEndDate() {
    return DateUtil.immutableCopy(searchEndDate);
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
   * commodityCodeStartを返します。
   * 
   * @return the commodityCodeStart
   */
  public String getCommodityCodeStart() {
    return commodityCodeStart;
  }

  /**
   * commodityCodeEndを返します。
   * 
   * @return the commodityCodeEnd
   */
  public String getCommodityCodeEnd() {
    return commodityCodeEnd;
  }

  /**
   * commoditySkuNameを返します。
   * 
   * @return the commoditySkuName
   */
  public String getCommoditySkuName() {
    return commoditySkuName;
  }

  /**
   * searchStartDateを設定します。
   * 
   * @param searchStartDate
   *          設定する searchStartDate
   */
  public void setSearchStartDate(Date searchStartDate) {
    this.searchStartDate = DateUtil.immutableCopy(searchStartDate);
  }

  /**
   * searchEndDateを設定します。
   * 
   * @param searchEndDate
   *          設定する searchEndDate
   */
  public void setSearchEndDate(Date searchEndDate) {
    this.searchEndDate = DateUtil.immutableCopy(searchEndDate);
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
   * commodityCodeStartを設定します。
   * 
   * @param commodityCodeStart
   *          設定する commodityCodeStart
   */
  public void setCommodityCodeStart(String commodityCodeStart) {
    this.commodityCodeStart = commodityCodeStart;
  }

  /**
   * commodityCodeEndを設定します。
   * 
   * @param commodityCodeEnd
   *          設定する commodityCodeEnd
   */
  public void setCommodityCodeEnd(String commodityCodeEnd) {
    this.commodityCodeEnd = commodityCodeEnd;
  }

  /**
   * commoditySkuNameを設定します。
   * 
   * @param commoditySkuName
   *          設定する commoditySkuName
   */
  public void setCommoditySkuName(String commoditySkuName) {
    this.commoditySkuName = commoditySkuName;
  }

}
