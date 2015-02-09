package jp.co.sint.webshop.service.jd.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.StringUtil;

public class JdOrderSearchQuery extends AbstractQuery<JdOrderlLists> {

  private static final long serialVersionUID = 1L;
  
  public static final String JD_ORDER_LIST_GET
    ="SELECT JSH.CUSTOMER_CODE,JOH.ORDER_PAYMENT,JOH.ORDER_NO,JSH.CHILD_PAID_PRICE,CHILD_ORDER_NO,JSH.SHIPPING_STATUS"
    +" FROM JD_ORDER_HEADER JOH,JD_SHIPPING_HEADER JSH,JD_ORDER_HEADER JDH"
    +" WHERE JOH.ORDER_NO=JSH.ORDER_NO  AND JDH.ORDER_NO=JSH.ORDER_NO AND JSH.RETURN_ITEM_TYPE=0 AND JOH.ORDER_NO IN (SELECT DISTINCT ORDER_NO FROM JD_SHIPPING_HEADER"
    +" WHERE (SHIPPING_STATUS='2' OR SHIPPING_STATUS='3' OR SHIPPING_STATUS='1') AND RETURN_ITEM_TYPE=0 AND CHILD_ORDER_NO IS NOT NULL)";
    
  
  private static final String SORT_CONDITION = " ORDER BY "
    +" JSH.SHIPPING_DATE DESC";
  public JdOrderSearchQuery() {
  }
  
  public JdOrderSearchQuery(JdOrderSearchCondition condition) {
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
        // condition.setOrderFromPaymentDatetime(condition.getOrderFromPaymentDatetime());
        builder.append(" AND ");
        builder.append(" JDH.ORDER_DATETIME >= ?");
        params.add(condition.getOrderFromPaymentDatetime() + " 00:00:00");
      }
      if (StringUtil.hasValue(condition.getOrderToPaymentDatetime())) {
        // condition.setOrderToPaymentDatetime(condition.getOrderToPaymentDatetime()
        // + " 23:59:59");
        builder.append(" AND ");
        builder.append(" JDH.ORDER_DATETIME <= ? ");
        params.add(condition.getOrderToPaymentDatetime() + " 23:59:59");
      }
    }
       //按照发货日降序排列
    builder.append(SORT_CONDITION);
    //school
    setSqlString(builder.toString());
    setParameters(params.toArray());

    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }
  
  public Class<JdOrderlLists> getRowType() {
    return JdOrderlLists.class;
  }
}
