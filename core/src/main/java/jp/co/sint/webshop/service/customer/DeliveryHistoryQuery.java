package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.ReturnItemType; // 10.1.7 10323 追加
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

public class DeliveryHistoryQuery extends AbstractQuery<DeliveryHistoryInfo> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private int pageSize = 10;

  private int pageNumber = 1;

  public static final String LOAD_DELIVERY_HISTORY_QUERY 
    = "SELECT TO_CHAR(SH.SHIPPING_DATE, 'YYYY/MM/DD HH24:MI:SS') SHIPPING_DATE , "
    + "SH.SHIPPING_NO, SH.ORDER_NO, TO_CHAR(OH.ORDER_DATETIME, 'YYYY/MM/DD HH24:MI:SS') ORDER_DATETIME, "
    // 10.1.5 10209
  //  // 10.1.3 10126 
  //  // + " (SS.SHIPPING_CHARGE + SS.RETAIL_PRICE + SS.GIFT_PRICE) AS TOTAL_PRICE, RETURN_ITEM_TYPE "
  //  + " (SS.SHIPPING_CHARGE + SS.RETAIL_PRICE + SS.GIFT_PRICE) AS TOTAL_PRICE, OH.PAYMENT_COMMISSION, RETURN_ITEM_TYPE  "
  //  // 10.1.3 10126 
  //  + "FROM SHIPPING_HEADER SH INNER JOIN ORDER_HEADER OH ON SH.ORDER_NO = OH.ORDER_NO "
  //  + "INNER JOIN SHIPPING_SUMMARY_VIEW SS ON SS.SHIPPING_NO = SH.SHIPPING_NO "
    // 10.1.7 10313 修正 ここから
    // + " OH.TOTAL_AMOUNT AS TOTAL_PRICE, OH.PAYMENT_COMMISSION, RETURN_ITEM_TYPE  "
    + " (SSV.SHIPPING_CHARGE + SSV.RETAIL_PRICE + SSV.GIFT_PRICE) AS TOTAL_PRICE, "
    + " OH.PAYMENT_COMMISSION, SH.RETURN_ITEM_TYPE  "
    // 10.1.7 10313 修正 ここまで
    + " FROM SHIPPING_HEADER SH INNER JOIN ORDER_SUMMARY_VIEW OH ON SH.ORDER_NO = OH.ORDER_NO "
    // 10.1.5 10209 
    // 10.1.7 10313 追加 ここから
    + " INNER JOIN SHIPPING_SUMMARY_VIEW SSV ON SSV.SHIPPING_NO = SH.SHIPPING_NO "
    // 10.1.7 10313 追加 ここまで
    // 10.1.7 10323 修正 ここから
    // + " WHERE OH.ORDER_STATUS != " + OrderStatus.CANCELLED.getValue();
    + " WHERE OH.ORDER_STATUS != " + OrderStatus.CANCELLED.getValue()
    + " AND SH.RETURN_ITEM_TYPE = " + ReturnItemType.ORDERED.getValue();
    // 10.1.7 10323 修正 ここまで
  
  public DeliveryHistoryQuery() {

  }

  public DeliveryHistoryQuery(DeliveryHistorySearchCondition condition, String query) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    final String sql = query;

    if (condition != null) { // 検索条件設定

      if (StringUtil.hasValue(condition.getSearchCustomerCode())) {
        builder.append(" AND SH.CUSTOMER_CODE = ?");
        params.add(condition.getSearchCustomerCode());
      }

      if (StringUtil.hasValue(condition.getSearchAddressNo())) {
        builder.append(" AND SH.ADDRESS_NO = ?");
        params.add(condition.getSearchAddressNo());
      }

      builder.append(" ORDER BY SHIPPING_NO DESC");

      this.pageNumber = condition.getCurrentPage();
      this.pageSize = condition.getPageSize();
    }

    setSqlString(sql + builder.toString());
    setParameters(params.toArray());

    Logger logger = Logger.getLogger(this.getClass());
    logger.info("sql:" + sql + builder.toString());

  }

  public Class<DeliveryHistoryInfo> getRowType() {
    return DeliveryHistoryInfo.class;
  }

  /**
   * pageNumberを取得します。
   * 
   * @return pageNumber
   */
  public int getPageNumber() {
    return pageNumber;
  }

  /**
   * pageSizeを取得します。
   * 
   * @return pageSize
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * pageNumberを設定します。
   * 
   * @param pageNumber
   *          pageNumber
   */
  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  /**
   * pageSizeを設定します。
   * 
   * @param pageSize
   *          pageSize
   */
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

}
