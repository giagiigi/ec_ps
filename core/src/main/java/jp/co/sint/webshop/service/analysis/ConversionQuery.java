package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.UserAgent;
import jp.co.sint.webshop.utility.UserAgentManager;

/**
 * コンバージョン率を計算する為のクエリクラス
 * 
 * @author System Integrator Corp.
 */
public class ConversionQuery extends AbstractAccessLogQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** default constructor */
  public ConversionQuery() {
  }

  public ConversionQuery(CountType type, int year, int month, int day, String clientGroup) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT");
    builder.append(" " + createLabelFragment(type) + " AS LABEL,");
    builder.append(" CASE SUM(COALESCE(VISITOR_COUNT,0)) WHEN 0 THEN 0 "
        + "ELSE ROUND(SUM(COALESCE(PURCHASER_COUNT,0)) / SUM(COALESCE(VISITOR_COUNT,0)) * 100,1) END AS CONVERSION_RATE");
    builder.append(" FROM ACCESS_LOG WHERE 1 = 1");

    SqlFragment dateFragment = createDateFragment(year, month, day, type);
    if (StringUtil.hasValue(dateFragment.getFragment())) {
      builder.append(" AND " + dateFragment.getFragment());
      for (Object o : dateFragment.getParameters()) {
        params.add(o);
      }
    }

    if (StringUtil.hasValue(clientGroup)) {
      if (clientGroup.equals(UserAgentManager.OTHERS_CLIENT_GROUP_CODE)) {
        UserAgentManager manager = DIContainer.getUserAgentManager();
        List<Object> inParams = new ArrayList<Object>();
        for (UserAgent ua : manager.getUserAgentList(false)) {
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
        params.add(clientGroup);
      }
    }

    builder.append(" GROUP BY " + createLabelFragment(type));
    builder.append(" ORDER BY " + createLabelFragment(type));

    this.setSqlString(builder.toString());
    this.setParameters(params.toArray());
  }

}
