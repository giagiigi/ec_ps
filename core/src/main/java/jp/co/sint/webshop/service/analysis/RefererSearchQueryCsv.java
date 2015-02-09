package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;

public class RefererSearchQueryCsv implements Query {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 集計結果取得用SQL */
  private String sql;

  /** 集計結果取得用オブジェクト */
  private Object[] param;

  public RefererSearchQueryCsv() {

  }

  public RefererSearchQueryCsv(RefererSearchCondition condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("select referer_url, sum(referer_count) as referer_count from referer where 1=1 ");

    SqlFragment dateFragment = SqlDialect.getDefault().createRangeClause("ACCESS_DATE", condition.getSearchStartDate(),
        condition.getSearchEndDate());

    if (StringUtil.hasValue(dateFragment.getFragment())) {
      builder.append(" AND " + dateFragment.getFragment());
      for (Object o : dateFragment.getParameters()) {
        params.add(o);
      }
    }

    if (StringUtil.hasValue(condition.getClientGroup())) {
      builder.append(" AND CLIENT_GROUP = ?");
      params.add(condition.getClientGroup());
    }

    builder.append("group by referer_url order by referer_count desc");

    sql = builder.toString();
    param = params.toArray();

  }

  public Object[] getParameters() {
    return ArrayUtil.immutableCopy(param);
  }

  public String getSqlString() {
    return sql;
  }

}
