package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.UserAgent;
import jp.co.sint.webshop.utility.UserAgentManager;

public class RefererSearchQuery extends AbstractQuery<RefererSummary> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  public RefererSearchQuery() {

  }

  public RefererSearchQuery(RefererSearchCondition condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT REFERER_URL, SUM(REFERER_COUNT) AS REFERER_COUNT FROM REFERER WHERE 1=1 ");

    SqlFragment dateFragment = SqlDialect.getDefault().createRangeClause("ACCESS_DATE", condition.getSearchStartDate(),
        condition.getSearchEndDate());

    if (StringUtil.hasValue(dateFragment.getFragment())) {
      builder.append(" AND " + dateFragment.getFragment());
      for (Object o : dateFragment.getParameters()) {
        params.add(o);
      }
    }

    String clientGroup = condition.getClientGroup();
    if (StringUtil.hasValue(clientGroup)) {
      if (clientGroup.equals(UserAgentManager.OTHERS_CLIENT_GROUP_CODE)) {
        List<Object> inParams = new ArrayList<Object>();
        UserAgentManager manager = DIContainer.getUserAgentManager();
        for (UserAgent ua : manager.getUserAgentList(false)) {
          // 定義外のクライアントグループは全てその他
          if (!ua.getClientGroup().equals(UserAgentManager.OTHERS_CLIENT_GROUP_CODE)) {
            inParams.add(ua.getClientGroup());
          }
        }
        SqlFragment inFragment = SqlDialect.getDefault().createInClause("CLIENT_GROUP", inParams.toArray());
        builder.append(" AND NOT " + inFragment.getFragment());
        for (Object o : inFragment.getParameters()) {
          params.add(o);
        }
      } else {
        builder.append(" AND CLIENT_GROUP = ?");
        params.add(condition.getClientGroup());
      }
    }

    builder.append(" GROUP BY REFERER_URL ORDER BY REFERER_COUNT DESC");

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
    setMaxFetchSize(condition.getMaxFetchSize());

  }

  public Class<RefererSummary> getRowType() {
    return RefererSummary.class;
  }

}
