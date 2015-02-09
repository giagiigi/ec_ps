package jp.co.sint.webshop.service.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;

public class EnqueteSearchQuery extends AbstractQuery<EnqueteList> {

  private static final long serialVersionUID = 1L;

  public EnqueteSearchQuery() {

  }

  public EnqueteSearchQuery(EnqueteListSearchCondition condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT E.ENQUETE_CODE, E.ENQUETE_NAME, E.ENQUETE_START_DATE, E.ENQUETE_END_DATE,"
        + " COUNT(A.CUSTOMER_CODE) AS ENQUETE_ANSWER_COUNT FROM ENQUETE E"
        + " LEFT OUTER JOIN ENQUETE_ANSWER_HEADER A ON E.ENQUETE_CODE = A.ENQUETE_CODE WHERE 1 = 1");

    // アンケートコード
    SqlFragment fragment = SqlDialect.getDefault().createRangeClause("E.ENQUETE_CODE", condition.getSearchEnqueteCodeFrom(),
        condition.getSearchEnqueteCodeTo());
    if (StringUtil.hasValue(fragment.getFragment())) {
      builder.append(" AND " + fragment.getFragment());
      for (Object o : fragment.getParameters()) {
        params.add(o);
      }
    }

    // アンケート名
    if (StringUtil.hasValue(condition.getSearchEnqueteName())) {
      builder.append(" AND ");
      builder.append(SqlDialect.getDefault().getLikeClausePattern("E.ENQUETE_NAME"));
      String enqueteName = SqlDialect.getDefault().escape(condition.getSearchEnqueteName());
      enqueteName = "%" + enqueteName + "%";
      params.add(enqueteName);
    }

    // アンケート開始日
    if (DateUtil.isCorrect(condition.getSearchEnqueteStartDateFrom())
        && DateUtil.isCorrect(condition.getSearchEnqueteStartDateTo())) {
      builder.append(" AND E.ENQUETE_START_DATE BETWEEN ? AND ?");
      params.add(condition.getSearchEnqueteStartDateFrom());
      params.add(condition.getSearchEnqueteStartDateTo());
    } else if (DateUtil.isCorrect(condition.getSearchEnqueteStartDateFrom())) {
      builder.append(" AND E.ENQUETE_START_DATE >= ?");
      params.add(condition.getSearchEnqueteStartDateFrom());
    } else if (DateUtil.isCorrect(condition.getSearchEnqueteStartDateTo())) {
      builder.append(" AND E.ENQUETE_START_DATE <= ?");
      params.add(condition.getSearchEnqueteStartDateTo());
    }

    // アンケート終了日
    if (DateUtil.isCorrect(condition.getSearchEnqueteEndDateFrom()) && DateUtil.isCorrect(condition.getSearchEnqueteEndDateTo())) {
      builder.append(" AND E.ENQUETE_END_DATE BETWEEN ? AND ?");
      params.add(condition.getSearchEnqueteEndDateFrom());
      params.add(condition.getSearchEnqueteEndDateTo());
    } else if (DateUtil.isCorrect(condition.getSearchEnqueteEndDateFrom())) {
      builder.append(" AND E.ENQUETE_END_DATE >= ?");
      params.add(condition.getSearchEnqueteEndDateFrom());
    } else if (DateUtil.isCorrect(condition.getSearchEnqueteEndDateTo())) {
      builder.append(" AND E.ENQUETE_END_DATE <= ?");
      params.add(condition.getSearchEnqueteEndDateTo());
    }

    // 並べ替え条件（アンケート実施中＞実施前＞終了の順＋開始日順）
    builder.append(" GROUP BY E.ENQUETE_CODE, E.ENQUETE_NAME, E.ENQUETE_START_DATE, E.ENQUETE_END_DATE");
    builder.append(" ORDER BY CASE WHEN ? BETWEEN ENQUETE_START_DATE AND ENQUETE_END_DATE THEN 0 ");
    params.add(DateUtil.truncateDate(DateUtil.getSysdate()));
    builder.append(" WHEN ? < ENQUETE_START_DATE THEN 1 ELSE 2 END, ENQUETE_START_DATE DESC");
    params.add(DateUtil.truncateDate(DateUtil.getSysdate()));

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());

  }

  public Class<EnqueteList> getRowType() {
    return EnqueteList.class;
  }

}
