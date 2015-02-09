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
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

/**
 * @author System Integrator Corp.
 */
public class CommodityAccessLogSearchQuery extends AbstractQuery<CommodityAccessLogSummary> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** Default Constructor */
  public CommodityAccessLogSearchQuery() {
  }

  public CommodityAccessLogSearchQuery(CommodityAccessLogSearchCondition condition) {
    StringBuilder builder = new StringBuilder();

    builder.append("SELECT ");
    builder.append(" SHOP_CODE, SHOP_NAME, COMMODITY_CODE, COMMODITY_NAME,");
    builder.append("SUM(COALESCE(ACCESS_COUNT,0)) AS ACCESS_COUNT");
    builder.append(" FROM COMMODITY_ACCESS_LOG WHERE 1 = 1");

    List<Object> params = new ArrayList<Object>();

    SqlFragment fragment = SqlDialect.getDefault().createDateRangeClause("ACCESS_DATE", condition.getSearchStartDate(),
        condition.getSearchEndDate());

    if (StringUtil.hasValue(fragment.getFragment())) {
      builder.append(" AND " + fragment.getFragment());
      for (Object o : fragment.getParameters()) {
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
        params.add(clientGroup);
      }
    }

    String shopCode = condition.getShopCode();
    if (StringUtil.hasValue(shopCode)) {
      builder.append(" AND SHOP_CODE = ?");
      params.add(shopCode);
    }

    SqlFragment codeFragment = SqlDialect.getDefault().createRangeClause("COMMODITY_CODE", condition.getSearchCommodityCodeStart(),
        condition.getSearchCommodityCodeEnd());

    if (StringUtil.hasValue(codeFragment.getFragment())) {
      builder.append(" AND " + codeFragment.getFragment());
      for (Object o : codeFragment.getParameters()) {
        params.add(o);
      }
    }

    String commodityName = condition.getCommodityName();
    if (StringUtil.hasValue(commodityName)) {
      SqlFragment likeFragment = SqlDialect.getDefault().createLikeClause("COMMODITY_NAME", commodityName,
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + likeFragment.getFragment());
      for (Object o : likeFragment.getParameters()) {
        params.add(o);
      }
    }

    builder.append(" GROUP BY SHOP_CODE, SHOP_NAME, COMMODITY_CODE, COMMODITY_NAME");
    builder.append(" ORDER BY ACCESS_COUNT DESC");

    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
    setSqlString(builder.toString());
    setParameters(params.toArray());
    setMaxFetchSize(condition.getMaxFetchSize());
  }

  public Class<CommodityAccessLogSummary> getRowType() {
    return CommodityAccessLogSummary.class;
  }

}
