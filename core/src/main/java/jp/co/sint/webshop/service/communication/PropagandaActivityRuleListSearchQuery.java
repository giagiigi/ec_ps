package jp.co.sint.webshop.service.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.ActivityStatus;
import jp.co.sint.webshop.data.dto.PropagandaActivityRule;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;


public class PropagandaActivityRuleListSearchQuery extends AbstractQuery<PropagandaActivityRule> {

  /** uid */
  private static final long serialVersionUID = 1L;
  
  private static final String BASE_SQL
    = "SELECT "
    + "PAR.* "
    + "FROM PROPAGANDA_ACTIVITY_RULE PAR ";
  
  private static final String SORT_CONDITION = " ORDER BY CASE"
    + " WHEN PAR.ACTIVITY_START_DATETIME <= NOW()"
    + "  AND PAR.ACTIVITY_END_DATETIME >= NOW() THEN "  + ActivityStatus.IN_PROGRESS.getValue()
    + " WHEN PAR.ACTIVITY_START_DATETIME > NOW() THEN " + ActivityStatus.NOT_PROGRESS.getValue()
    + " WHEN PAR.ACTIVITY_END_DATETIME < NOW() THEN  " + ActivityStatus.OUT_PROGRESS.getValue() + " END,"
    + " PAR.ACTIVITY_START_DATETIME DESC";

  public PropagandaActivityRuleListSearchQuery() {
  }

  public PropagandaActivityRuleListSearchQuery(PropagandaActivityRuleListSearchCondition condition) {

    StringBuilder builder = new StringBuilder(BASE_SQL);
    builder.append(" WHERE 1 = 1 ");
    List<Object> params = new ArrayList<Object>();

    // 検索条件:活动编号
    if (StringUtil.hasValue(condition.getSearchActivityCode())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createLikeClause("PAR.ACTIVITY_CODE", condition.getSearchActivityCode(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 検索条件:活动名称
    if (StringUtil.hasValue(condition.getSearchActivityName())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createLikeClause("PAR.ACTIVITY_NAME", condition.getSearchActivityName(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    
    // 検索条件:活动类型
    if (StringUtil.hasValue(condition.getSearchActivityType())) {
      builder.append(" AND PAR.ORDER_TYPE = ? ");
      params.add(condition.getSearchActivityType());
    }

    // 検索条件:活动期间
    if (StringUtil.hasValueAnyOf(condition.getSearchActivityStartDateFrom(), condition.getSearchActivityStartDateTo())) {
      if (StringUtil.hasValue(condition.getSearchActivityStartDateFrom())) {
        condition.setSearchActivityStartDateFrom(condition.getSearchActivityStartDateFrom().substring(0, 17) + "00");
        builder.append(" AND ");
        builder.append(" PAR.ACTIVITY_START_DATETIME >= ? ");
        params.add(DateUtil.fromString(condition.getSearchActivityStartDateFrom(), true));
      }
      if (StringUtil.hasValue(condition.getSearchActivityStartDateTo())) {
        condition.setSearchActivityStartDateTo(condition.getSearchActivityStartDateTo().substring(0, 17) + "59");
        builder.append(" AND ");
        builder.append(" PAR.ACTIVITY_END_DATETIME <= ? ");
        params.add(DateUtil.fromString(condition.getSearchActivityStartDateTo(), true));
      }
    }

    // 検索条件:活动狀態
    if (StringUtil.hasValue(condition.getSearchStatus())) {
      if (condition.getSearchStatus().equals("1")) {
        builder.append(" AND ");
        builder.append(" NOW() BETWEEN PAR.ACTIVITY_START_DATETIME AND PAR.ACTIVITY_END_DATETIME ");
      } else if (condition.getSearchStatus().equals("2")) {
        builder.append(" AND ");
        builder.append(" NOW() < PAR.ACTIVITY_START_DATETIME ");
      } else if (condition.getSearchStatus().equals("3")) {
        builder.append(" AND ");
        builder.append(" NOW() > PAR.ACTIVITY_END_DATETIME ");
      }
    }
    // 並べ替え条件(実施中⇒実施前⇒実施終了, 各期間中では開始日の降順)
    builder.append(SORT_CONDITION);

    setSqlString(builder.toString());
    setParameters(params.toArray());

    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

  
  public Class<PropagandaActivityRule> getRowType() {
    return PropagandaActivityRule.class;
  }

}
