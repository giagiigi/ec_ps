package jp.co.sint.webshop.service.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.StringUtil;

public class ReviewPostCustomerCountSearchQuery extends AbstractQuery<String> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  public ReviewPostCustomerCountSearchQuery() {
  }

  public ReviewPostCustomerCountSearchQuery(ReviewPostCustomerCountSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT COUNT(REVIEW_ID) AS COUNT_RESULT FROM REVIEW_POST ");
    builder.append("WHERE 1=1 ");

    if (StringUtil.hasValue(condition.getCommodityCode())) {
      builder.append("AND COMMODITY_CODE = ?");
      params.add(condition.getCommodityCode());
    }
    if (StringUtil.hasValue(condition.getOrderNo())) {
      builder.append("AND ORDER_NO = ?");
      params.add(condition.getOrderNo());
    }
    if (StringUtil.hasValue(condition.getShopCode())) {
      builder.append("AND SHOP_CODE = ? ");
      params.add(condition.getShopCode());
    }
    if (StringUtil.hasValue(condition.getCustomerCode())) {
      builder.append("AND CUSTOMER_CODE = ? ");
      params.add(condition.getCustomerCode());
    }

    setSqlString(builder.toString());
    setParameters(params.toArray());
  }

  public Class<String> getRowType() {
    return String.class;
  }
}
