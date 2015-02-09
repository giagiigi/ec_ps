package jp.co.sint.webshop.service.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.ActivityStatus;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;


public class DiscountListSearchQuery extends AbstractQuery<DiscountHeadLine> {

  /** uid */
  private static final long serialVersionUID = 1L;
  
  private static final String BASE_SQL
    = "SELECT "
    + "DH.DISCOUNT_CODE, "
    + "DH.DISCOUNT_NAME, "
    + "DH.DISCOUNT_START_DATETIME, "
    + "DH.DISCOUNT_END_DATETIME, "
    + "(SELECT COUNT(*) FROM DISCOUNT_COMMODITY DC WHERE DH.DISCOUNT_CODE = DC.DISCOUNT_CODE) AS COMMODITY_COUNT "
    + "FROM DISCOUNT_HEADER DH ";
  
  private static final String SORT_CONDITION = " ORDER BY CASE"
    + " WHEN DH.DISCOUNT_START_DATETIME <= NOW()"
    + "  AND DH.DISCOUNT_END_DATETIME >= NOW() THEN "  + ActivityStatus.IN_PROGRESS.getValue()
    + " WHEN DH.DISCOUNT_START_DATETIME > NOW() THEN " + ActivityStatus.NOT_PROGRESS.getValue()
    + " WHEN DH.DISCOUNT_END_DATETIME < NOW() THEN  " + ActivityStatus.OUT_PROGRESS.getValue() + " END,"
    + " DH.DISCOUNT_START_DATETIME DESC";

  public DiscountListSearchQuery() {
  }

  public DiscountListSearchQuery(DiscountListSearchCondition condition) {

    StringBuilder builder = new StringBuilder(BASE_SQL);
    builder.append(" WHERE 1 = 1 ");
    List<Object> params = new ArrayList<Object>();

    // 検索条件:折扣编号
    if (StringUtil.hasValue(condition.getSearchDiscountCode())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createLikeClause("DH.DISCOUNT_CODE", condition.getSearchDiscountCode(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 検索条件:折扣名称
    if (StringUtil.hasValue(condition.getSearchDiscountName())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createLikeClause("DH.DISCOUNT_NAME", condition.getSearchDiscountName(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 検索条件:折扣時間
    if (StringUtil.hasValueAnyOf(condition.getSearchDiscountStartDatetimeFrom(), condition.getSearchDiscountEndDatetimeTo())) {
      if (StringUtil.hasValue(condition.getSearchDiscountStartDatetimeFrom())) {
        condition.setSearchDiscountStartDatetimeFrom(condition.getSearchDiscountStartDatetimeFrom().substring(0, 17) + "00");
        builder.append(" AND ");
        builder.append(" DH.DISCOUNT_START_DATETIME >= ? ");
        params.add(DateUtil.fromString(condition.getSearchDiscountStartDatetimeFrom(), true));
      }
      if (StringUtil.hasValue(condition.getSearchDiscountEndDatetimeTo())) {
        condition.setSearchDiscountEndDatetimeTo(condition.getSearchDiscountEndDatetimeTo().substring(0, 17) + "59");
        builder.append(" AND ");
        builder.append(" DH.DISCOUNT_END_DATETIME <= ? ");
        params.add(DateUtil.fromString(condition.getSearchDiscountEndDatetimeTo(), true));
      }
    }

    // 検索条件:折扣進行狀態
    if (StringUtil.hasValue(condition.getSearchDiscountStatus())) {
      if (condition.getSearchDiscountStatus().equals("1")) {
        builder.append(" AND ");
        builder.append(" NOW() between DH.DISCOUNT_START_DATETIME AND DH.DISCOUNT_END_DATETIME ");
      } else if (condition.getSearchDiscountStatus().equals("2")) {
        builder.append(" AND ");
        builder.append(" NOW() < DH.DISCOUNT_START_DATETIME ");
      } else if (condition.getSearchDiscountStatus().equals("3")) {
        builder.append(" AND ");
        builder.append(" NOW() > DH.DISCOUNT_END_DATETIME ");
      }
    }
    // 並べ替え条件(実施中⇒実施前⇒実施終了, 各期間中では開始日の降順)
    builder.append(SORT_CONDITION);

    setSqlString(builder.toString());
    setParameters(params.toArray());

    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

  
  public Class<DiscountHeadLine> getRowType() {
    return DiscountHeadLine.class;
  }

}
