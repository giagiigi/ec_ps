package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.ReturnItemType;
import jp.co.sint.webshop.utility.StringUtil;

public class MemberShippingHistoryQuery extends AbstractQuery<MemberShippingHistory> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // 订单发货情报
  public static final String LOAD_ORDER_QUERY = "SELECT" +
      " OH.ORDER_NO,OH.gift_card_use_price, " +
      " SH.SHIPPING_NO," +
      " TO_CHAR(OH.ORDER_DATETIME, 'yyyy/MM/dd') AS ORDER_DATETIME," +
      " TO_CHAR(OH.PAYMENT_DATE, 'yyyy/MM/dd') AS PAYMENT_DATE," +
      " OH.PAYMENT_METHOD_TYPE," +
      " OH.PAYMENT_METHOD_NAME," +
      " OH.PAYMENT_COMMISSION," +
      " (OH.TOTAL_AMOUNT - OH.DISCOUNT_PRICE) TOTAL_AMOUNT," +
      " SH.SHIPPING_STATUS," +
      " SH.DELIVERY_COMPANY_NAME," +
      " SH.DELIVERY_SLIP_NO," +
      " TO_CHAR(SH.SHIPPING_DIRECT_DATE, 'yyyy/MM/dd') AS SHIPPING_DIRECT_DATE," +
      " TO_CHAR(SH.SHIPPING_DATE, 'yyyy/MM/dd') AS SHIPPING_DATE," +
      " TO_CHAR(SH.ARRIVAL_DATE, 'yyyy/MM/dd') AS ARRIVAL_DATE," +
      " OH.ORDER_STATUS," +
      " SH.FIXED_SALES_STATUS" +
      " FROM order_summary_language_view4 OH" +
      " INNER JOIN SHIPPING_HEADER SH ON OH.ORDER_NO = SH.ORDER_NO AND SH.RETURN_ITEM_TYPE = " + ReturnItemType.ORDERED.getValue() ;
      // soukai update 2012/01/31 ob start
//      " LEFT JOIN SHIPPING_REALITY_DETAIL SR ON OH.ORDER_NO = SR.ORDER_NO";
      //" LEFT JOIN SHIPPING_REALITY_DETAIL SR ON SH.DELIVERY_SLIP_NO = SR.DELIVERY_SLIP_NO";
      // soukai update 2012/01/31 ob end

  /** default constructor */
  public MemberShippingHistoryQuery() {

  }
  
  public MemberShippingHistoryQuery(MemberSearchCondition condition, String searchQuery) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    if (condition != null) { // 検索条件設定
      builder.append(searchQuery);
      builder.append(" WHERE 1 = 1");

      // 会员编号
      if (StringUtil.hasValue(condition.getSearchCustomerCode())) {
        builder.append(" AND OH.CUSTOMER_CODE = ?");
        params.add(condition.getSearchCustomerCode());
      }
      
      // 订单区分
      if (StringUtil.hasValue(condition.getSearchOrderType())) {
        builder.append(" AND OH.ORDER_TYPE = ?");
        params.add(condition.getSearchOrderType());
      }
      
      if (condition.isSearchWithOutCancel()) {
        builder.append(" AND OH.ORDER_STATUS <> ?");
        params.add(OrderStatus.CANCELLED.getValue());
      }

      setPageNumber(condition.getCurrentPage());
      setPageSize(condition.getPageSize());
      setMaxFetchSize(condition.getMaxFetchSize());
    }

    // 排序
    builder.append(" ORDER BY OH.ORDER_DATETIME DESC, OH.ORDER_NO DESC");
    
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
  }

  public Class<MemberShippingHistory> getRowType() {
    return MemberShippingHistory.class;
  }
}
