package jp.co.sint.webshop.service.jd.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.StringUtil;

public class JdCsvOrderSearchQuery extends AbstractQuery<JdOrderlLists> {

  private static final long serialVersionUID = 1L;

  public static final String JD_ORDER_LIST_GET
  =" SELECT DISTINCT JSH.CUSTOMER_CODE,JSH.ORDER_NO,CHILD_ORDER_NO,JSD.SKU_CODE,CH.COMMODITY_NAME AS COMMODITYNAME,JSD.PURCHASING_AMOUNT,"
      +"JSD.UNIT_PRICE,(JSD.PURCHASING_AMOUNT*JSD.UNIT_PRICE) AS ROWPRICE,JSH.CHILD_PAID_PRICE,JSH.DELIVERY_COMPANY_NAME,"
      +" JSH.IS_PART,JSH.ADDRESS1,JSH.ADDRESS2,JSH.ADDRESS3,JSH.ADDRESS4"
      +" FROM JD_SHIPPING_HEADER JSH,JD_SHIPPING_DETAIL JSD,JD_ORDER_HEADER JDH,COMMODITY_HEADER CH"
      +" WHERE JSH.SHIPPING_NO=JSD.SHIPPING_NO AND JDH.ORDER_NO=JSH.ORDER_NO and CH.COMMODITY_CODE = JSD.SKU_CODE" 
      +" AND (JSH.SHIPPING_STATUS='2' OR JSH.SHIPPING_STATUS='3' OR JSH.SHIPPING_STATUS='1') AND JSH.RETURN_ITEM_TYPE=0 AND CHILD_ORDER_NO IS NOT NULL ";
  private static final String SORT_CONDITION = "";
  public JdCsvOrderSearchQuery() {
  }
  public JdCsvOrderSearchQuery(JdOrderSearchCondition condition) {
    StringBuilder builder = new StringBuilder(JD_ORDER_LIST_GET);
    List<Object> params = new ArrayList<Object>();
    //检索条件留言时间
    if (StringUtil.hasValueAnyOf(condition.getSearchFromPaymentDatetime(), condition.getSearchToPaymentDatetime())) {
      if (StringUtil.hasValue(condition.getSearchFromPaymentDatetime())) {
        condition.setSearchFromPaymentDatetime(condition.getSearchFromPaymentDatetime());
        builder.append(" AND ");
        builder.append(" JSH.SHIPPING_DATE >= ? ");
        params.add(condition.getSearchFromPaymentDatetime());
      }
      if (StringUtil.hasValue(condition.getSearchToPaymentDatetime())) {
        condition.setSearchToPaymentDatetime(condition.getSearchToPaymentDatetime());
        builder.append(" AND ");
        builder.append(" JSH.SHIPPING_DATE <= ? ");
        params.add(condition.getSearchToPaymentDatetime());
      }
    }
    if (StringUtil.hasValueAnyOf(condition.getOrderFromPaymentDatetime(), condition.getOrderToPaymentDatetime())) {
      if (StringUtil.hasValue(condition.getOrderFromPaymentDatetime())) {
        // condition.setOrderFromPaymentDatetime(condition.getOrderFromPaymentDatetime()+
        // " 00:00:00");
        builder.append(" AND ");
        builder.append(" jdh.order_datetime >= ?");
        params.add(condition.getOrderFromPaymentDatetime() + " 00:00:00");
      }
      if (StringUtil.hasValue(condition.getOrderToPaymentDatetime())) {
        // condition.setOrderToPaymentDatetime(condition.getOrderToPaymentDatetime()+
        // " 23:59:59");
        builder.append(" AND ");
        builder.append(" jdh.order_datetime <= ? ");
        params.add(condition.getOrderToPaymentDatetime() + " 23:59:59");
      }
    }
   
    //按照发货日降序
    builder.append(SORT_CONDITION);
    setSqlString(builder.toString());
    setParameters(params.toArray());

    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize()); 
  }

  public Class<JdOrderlLists> getRowType() {
    return JdOrderlLists.class;

  }
}
