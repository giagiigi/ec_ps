package jp.co.sint.webshop.service.analysis;

import java.util.Date;

import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.DateUtil;

public class CommodityAccessLogSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** ショップコード */
  private String shopCode;

  /** 集計開始日 */
  @Required
  @Metadata(name = "検索期間(開始日)", order = 1)
  private Date searchStartDate;

  /** 集計終了日 */
  @Required
  @Metadata(name = "検索期間(終了日)", order = 2)
  private Date searchEndDate;

  /** クライアントグループ */
  private String clientGroup;

  /** 検索開始商品コード */
  private String searchCommodityCodeStart;

  /** 検索終了商品コード */
  private String searchCommodityCodeEnd;

  /** 商品名 */
  private String commodityName;

  /**
   * commodityNameを取得します。
   * 
   * @return commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  /**
   * searchCommodityCodeEndを取得します。
   * 
   * @return searchCommodityCodeEnd
   */
  public String getSearchCommodityCodeEnd() {
    return searchCommodityCodeEnd;
  }

  /**
   * searchCommodityCodeStartを取得します。
   * 
   * @return searchCommodityCodeStart
   */
  public String getSearchCommodityCodeStart() {
    return searchCommodityCodeStart;
  }

  /**
   * searchEndDateを取得します。
   * 
   * @return searchEndDate
   */
  public Date getSearchEndDate() {
    return DateUtil.immutableCopy(searchEndDate);
  }

  /**
   * searchStartDateを取得します。
   * 
   * @return searchStartDate
   */
  public Date getSearchStartDate() {
    return DateUtil.immutableCopy(searchStartDate);
  }

  /**
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * commodityNameを設定します。
   * 
   * @param commodityName
   *          commodityName
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * searchCommodityCodeEndを設定します。
   * 
   * @param searchCommodityCodeEnd
   *          searchCommodityCodeEnd
   */
  public void setSearchCommodityCodeEnd(String searchCommodityCodeEnd) {
    this.searchCommodityCodeEnd = searchCommodityCodeEnd;
  }

  /**
   * searchCommodityCodeStartを設定します。
   * 
   * @param searchCommodityCodeStart
   *          searchCommodityCodeStart
   */
  public void setSearchCommodityCodeStart(String searchCommodityCodeStart) {
    this.searchCommodityCodeStart = searchCommodityCodeStart;
  }

  /**
   * searchEndDateを設定します。
   * 
   * @param searchEndDate
   *          searchEndDate
   */
  public void setSearchEndDate(Date searchEndDate) {
    this.searchEndDate = DateUtil.immutableCopy(searchEndDate);
  }

  /**
   * searchStartDateを設定します。
   * 
   * @param searchStartDate
   *          searchStartDate
   */
  public void setSearchStartDate(Date searchStartDate) {
    this.searchStartDate = DateUtil.immutableCopy(searchStartDate);
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * clientGroupを取得します。
   * 
   * @return clientGroup
   */
  public String getClientGroup() {
    return clientGroup;
  }

  /**
   * clientGroupを設定します。
   * 
   * @param clientGroup
   *          clientGroup
   */
  public void setClientGroup(String clientGroup) {
    this.clientGroup = clientGroup;
  }
}
