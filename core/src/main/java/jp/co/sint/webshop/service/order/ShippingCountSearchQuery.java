package jp.co.sint.webshop.service.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;

public class ShippingCountSearchQuery extends SimpleQuery {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  public ShippingCountSearchQuery() {
  }

  public ShippingCountSearchQuery(ShippingListSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> paramsList = new ArrayList<Object>();

    builder.append("SELECT COUNT(SHIPPING_NO) FROM SHIPPING_HEADER WHERE 1 = 1");

    if (!condition.getSearchShippingStatus().isEmpty()) {
      // shipping_statusの配列
      Object[] status = condition.getSearchShippingStatus().toArray();
      SqlFragment inFragment = SqlDialect.getDefault().createInClause("SHIPPING_STATUS", status);
      builder.append(" AND " + inFragment.getFragment());
      for (Object o : inFragment.getParameters()) {
        paramsList.add(o);
      }
    }

    if (StringUtil.hasValue(condition.getSearchShopCode())) {
      builder.append(" AND SHOP_CODE = ?");
      paramsList.add(condition.getSearchShopCode());
    }

    setSqlString(builder.toString());
    setParameters(paramsList.toArray());
  }

}
