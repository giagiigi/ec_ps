package jp.co.sint.webshop.service.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.SearchOrderDateType;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;

public class MyOrderListSearchQuery extends  AbstractQuery<MyOrder> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;
  
  private String[] noSelectStatus = {"-1"};// 1件も返さないように、存在しない"-1"をセット

  public MyOrderListSearchQuery() {
  }

  public MyOrderListSearchQuery(MyOrderListSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    
    
    List<Object> params = new ArrayList<Object>();

    builder.append(" SELECT OH.ORDER_NO,OH.ORDER_DATETIME AS ORDER_DATE ,OH.SHOP_CODE " +
    		" FROM ORDER_HEADER OH" +
    		" INNER JOIN SHIPPING_HEADER SH ON SH.ORDER_NO = OH.ORDER_NO " +
    		" WHERE 1=1 ");
    
    
    //店铺编号
    if (StringUtil.hasValue(condition.getShopCode())) {
      builder.append("AND OH.SHOP_CODE = ? ");
      params.add(condition.getShopCode());
    }
    
    //顾客编号
    if(StringUtil.hasValue(condition.getCustomerCode())){
      builder.append(" AND OH.CUSTOMER_CODE = ? ");
      params.add(condition.getCustomerCode());
    }
    
    //索引订单类型
    if (StringUtil.hasValue(condition.getOrderDateType()) 
        && SearchOrderDateType.RECENT.getValue().equals(condition.getOrderDateType())) {
      builder.append("AND OH.ORDER_DATETIME > (now()+'-3 month') ");
      
    }
    
    //发货状态
    
    if(condition.getShippingStatus()!=null && condition.getShippingStatus().length>0){
      String[] shippingStatus;
      if (StringUtil.hasValueAnyOf(condition.getShippingStatus())) {
        shippingStatus = ArrayUtil.immutableCopy(condition.getShippingStatus());
      } else {
        shippingStatus = this.noSelectStatus;
      }
      SqlFragment shippingFragment = SqlDialect.getDefault().createInClause("SH.SHIPPING_STATUS",
          (Object[]) shippingStatus);
      builder.append(" AND ");
      builder.append(shippingFragment.getFragment());
      params.addAll(Arrays.asList(shippingFragment.getParameters()));
    }

    builder.append(" ORDER BY  OH.ORDER_DATETIME DESC");
    
    

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

  @Override
  public Class<MyOrder> getRowType() {
    return MyOrder.class;
  }

}
