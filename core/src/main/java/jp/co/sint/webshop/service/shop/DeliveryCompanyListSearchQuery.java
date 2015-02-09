package jp.co.sint.webshop.service.shop;

import java.util.ArrayList;
import java.util.List;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.StringUtil;

public class DeliveryCompanyListSearchQuery extends SimpleQuery {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  public DeliveryCompanyListSearchQuery() {
  }

  public DeliveryCompanyListSearchQuery(DeliveryCompanyListSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT DELIVERY_COMPANY_NO,DELIVERY_COMPANY_NAME,DELIVERY_SPECIFICATION_TYPE,DELIVERY_PAYMENT_ADVANCE_FLG,DELIVERY_PAYMENT_LATER_FLG,DELIVERY_DATETIME_COMMISSION");
    builder.append(" FROM DELIVERY_COMPANY ");
    builder.append("WHERE 1=1 ");
    
    if (StringUtil.hasValue(condition.getDeliveryCompanyNo())) {
      builder.append("AND DELIVERY_COMPANY_NO = ? ");
      params.add(condition.getDeliveryCompanyNo());
    }
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
  }

}
