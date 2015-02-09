package jp.co.sint.webshop.service.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.ActivityStatus;
import jp.co.sint.webshop.data.dto.FreePostageRule;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;


public class FreePostageListSearchQuery extends AbstractQuery<FreePostageRule> {

  /** uid */
  private static final long serialVersionUID = 1L;
  
  private static final String BASE_SQL
    = "SELECT "
    + "FPR.* "
    + "FROM FREE_POSTAGE_RULE FPR ";
  
  private static final String SORT_CONDITION = " ORDER BY CASE"
    + " WHEN FPR.FREE_START_DATE <= NOW()"
    + "  AND FPR.FREE_END_DATE >= NOW() THEN "  + ActivityStatus.IN_PROGRESS.getValue()
    + " WHEN FPR.FREE_START_DATE > NOW() THEN " + ActivityStatus.NOT_PROGRESS.getValue()
    + " WHEN FPR.FREE_END_DATE < NOW() THEN  " + ActivityStatus.OUT_PROGRESS.getValue() + " END,"
    + " FPR.FREE_START_DATE DESC";

  public FreePostageListSearchQuery() {
  }

  public FreePostageListSearchQuery(FreePostageListSearchCondition condition) {

    StringBuilder builder = new StringBuilder(BASE_SQL);
    builder.append(" WHERE 1 = 1 ");
    List<Object> params = new ArrayList<Object>();

    // 検索条件:免邮促销编号
    if (StringUtil.hasValue(condition.getSearchFreePostageCode())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createLikeClause("FPR.FREE_POSTAGE_CODE", condition.getSearchFreePostageCode(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 検索条件:免邮促销名称
    if (StringUtil.hasValue(condition.getSearchFreePostageName())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createLikeClause("FPR.FREE_POSTAGE_NAME", condition.getSearchFreePostageName(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 検索条件:免邮促销時間
    if (StringUtil.hasValueAnyOf(condition.getSearchFreeStartDateFrom(), condition.getSearchFreeStartDateTo())) {
      if (StringUtil.hasValue(condition.getSearchFreeStartDateFrom())) {
        condition.setSearchFreeStartDateFrom(condition.getSearchFreeStartDateFrom().substring(0, 17) + "00");
        builder.append(" AND ");
        builder.append(" FPR.FREE_START_DATE >= ? ");
        params.add(DateUtil.fromString(condition.getSearchFreeStartDateFrom(), true));
      }
      if (StringUtil.hasValue(condition.getSearchFreeStartDateTo())) {
        condition.setSearchFreeStartDateTo(condition.getSearchFreeStartDateTo().substring(0, 17) + "59");
        builder.append(" AND ");
        builder.append(" FPR.FREE_END_DATE <= ? ");
        params.add(DateUtil.fromString(condition.getSearchFreeStartDateTo(), true));
      }
    }

    // 検索条件:折扣進行狀態
    if (StringUtil.hasValue(condition.getSearchStatus())) {
      if (condition.getSearchStatus().equals("1")) {
        builder.append(" AND ");
        builder.append(" NOW() BETWEEN FPR.FREE_START_DATE AND FPR.FREE_END_DATE ");
      } else if (condition.getSearchStatus().equals("2")) {
        builder.append(" AND ");
        builder.append(" NOW() < FPR.FREE_START_DATE ");
      } else if (condition.getSearchStatus().equals("3")) {
        builder.append(" AND ");
        builder.append(" NOW() > FPR.FREE_END_DATE ");
      }
    }
    // 並べ替え条件(実施中⇒実施前⇒実施終了, 各期間中では開始日の降順)
    builder.append(SORT_CONDITION);

    setSqlString(builder.toString());
    setParameters(params.toArray());

    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

  
  public Class<FreePostageRule> getRowType() {
    return FreePostageRule.class;
  }

}
