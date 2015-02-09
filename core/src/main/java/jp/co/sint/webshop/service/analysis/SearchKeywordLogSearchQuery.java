package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;

public class SearchKeywordLogSearchQuery extends AbstractQuery<SearchKeywordLogSummary> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** default constructor */
  public SearchKeywordLogSearchQuery() {
  }

  public SearchKeywordLogSearchQuery(SearchKeywordLogSearchCondition condition) {
    StringBuilder builder = new StringBuilder();

    List<Object> paramsList = new ArrayList<Object>();

    builder.append("SELECT");
    builder.append("   SEARCH_KEY,");
    builder.append("   SEARCH_WORD,");
    builder.append("   SUM(COALESCE(SEARCH_COUNT,0)) AS SEARCH_COUNT");
    builder.append(" FROM");
    builder.append("   SEARCH_KEYWORD_LOG");
    builder.append(" WHERE");
    builder.append("   1 = 1");

    if (StringUtil.hasValue(condition.getSearchKey())) {
      builder.append(" AND");
      builder.append("   SEARCH_KEY = ?");
      paramsList.add(condition.getSearchKey());
    }

    SqlFragment dateFragment = SqlDialect.getDefault().createDateRangeClause("SEARCH_DATE", condition.getSearchStartDate(),
        condition.getSearchEndDate());

    if (StringUtil.hasValue(dateFragment.getFragment())) {
      builder.append(" AND " + dateFragment.getFragment());
      for (Object o : dateFragment.getParameters()) {
        paramsList.add(o);
      }
    }

    builder.append(" GROUP BY");
    builder.append("   SEARCH_KEY,");
    builder.append("   SEARCH_WORD");
    builder.append(" ORDER BY");
    builder.append("   SEARCH_COUNT DESC,");
    builder.append("   SEARCH_KEY");

    this.setSqlString(builder.toString());
    this.setParameters(paramsList.toArray());
    this.setPageNumber(condition.getCurrentPage());
    this.setPageSize(condition.getPageSize());
    this.setMaxFetchSize(condition.getMaxFetchSize());
    
  }

  public Class<SearchKeywordLogSummary> getRowType() {
    return SearchKeywordLogSummary.class;
  }

}
