package jp.co.sint.webshop.service.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.StringUtil;

public class ReviewPostCountSearchQuery extends AbstractQuery<String> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  public ReviewPostCountSearchQuery() {
  }

  public ReviewPostCountSearchQuery(ReviewPostCountSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT COUNT(REVIEW_ID) AS COUNT_RESULT FROM REVIEW_POST ");
    builder.append("WHERE 1=1 ");

    if (StringUtil.hasValue(condition.getReviewDisplayType())) {
      builder.append("AND REVIEW_DISPLAY_TYPE = ?");
      params.add(condition.getReviewDisplayType());
    }

    if (StringUtil.hasValue(condition.getShopCode())) {
      builder.append("AND SHOP_CODE = ? ");
      params.add(condition.getShopCode());
    }

    setSqlString(builder.toString());
    setParameters(params.toArray());
  }

  public Class<String> getRowType() {
    return String.class;
  }
}
