package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.service.analysis.CustomerPreferenceSearchCondition.CustomerAttributeSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;

public class CustomerPreferenceSearchQuery extends AbstractQuery<CustomerPreferenceSummary> {

  private static final long serialVersionUID = 1L;

  private String[] defaultCustomerAttributeChoicesCondition = new String[] {
    "-1"
  };

  /** Default Constructor */
  public CustomerPreferenceSearchQuery() {
  }

  public CustomerPreferenceSearchQuery(CustomerPreferenceSearchCondition condition) {
    List<Object> paramsList = new ArrayList<Object>();
    StringBuilder sqlBuilder = new StringBuilder();

    sqlBuilder.append(" SELECT");
    sqlBuilder.append("   P_TABLE.SHOP_CODE,");
    sqlBuilder.append("   P_TABLE.SHOP_NAME,");
    sqlBuilder.append("   P_TABLE.COMMODITY_CODE,");
    sqlBuilder.append("   P_TABLE.COMMODITY_NAME,");
    sqlBuilder.append("   P_TABLE.TOTAL_CUSTOMER_COUNT,");
    sqlBuilder.append("   P_TABLE.TOTAL_ORDER_COUNT,");
    sqlBuilder.append("   P_TABLE.PURCHASING_AMOUNT,");
    sqlBuilder.append("   ROUND(P_TABLE.TOTAL_ORDER_COUNT / TOTAL_TABLE.TOTAL_ORDER_COUNT * 100 , 1 ) AS TOTAL_ORDER_COUNT_RATIO");
    sqlBuilder.append(" FROM");
    sqlBuilder.append(" (");
    sqlBuilder.append("   SELECT");
    sqlBuilder.append("     SHOP_CODE,");
    sqlBuilder.append("     SHOP_NAME,");
    sqlBuilder.append("     COMMODITY_CODE,");
    sqlBuilder.append("     COMMODITY_NAME,");
    sqlBuilder.append("     COUNT(CUSTOMER_CODE) AS TOTAL_CUSTOMER_COUNT,");
    sqlBuilder.append("     SUM(TOTAL_ORDER_COUNT) AS TOTAL_ORDER_COUNT,");
    sqlBuilder.append("     SUM(PURCHASING_AMOUNT) AS PURCHASING_AMOUNT");
    sqlBuilder.append("   FROM");
    sqlBuilder.append("     CUSTOMER_PREFERENCE");
    sqlBuilder.append("   WHERE");
    sqlBuilder.append("     1 = 1");

    if (StringUtil.hasValue(condition.getSexcondition())) {
      sqlBuilder.append(" AND SEX = ?");
      paramsList.add(condition.getSexcondition());
    }

    SqlFragment ageFragment = SqlDialect.getDefault().createRangeClause("AGE", condition.getSearchAgeStart(),
        condition.getSearchAgeEnd());
    if (StringUtil.hasValue(ageFragment.getFragment())) {
      sqlBuilder.append(" AND " + ageFragment.getFragment());
      for (Object o : ageFragment.getParameters()) {
        paramsList.add(o);
      }
    }

    if (StringUtil.hasValue(condition.getCustomerGroupCondition())) {
      sqlBuilder.append(" AND CUSTOMER_GROUP_CODE = ?");
      paramsList.add(condition.getCustomerGroupCondition());
    }

    Date searchStartDate = DateUtil.fromYearMonth("" + condition.getSearchYearStart(), "" + condition.getSearchMonthStart());
    Date searchEndDate = DateUtil.fromYearMonth("" + condition.getSearchYearEnd(), "" + condition.getSearchMonthEnd());

    SqlFragment yearMonthFragment = SqlDialect.getDefault().createDateRangeClause("YEAR_MONTH_OF_PURCHASE", searchStartDate,
        searchEndDate);
    if (StringUtil.hasValue(yearMonthFragment.getFragment())) {
      sqlBuilder.append(" AND " + yearMonthFragment.getFragment());
      for (Object o : yearMonthFragment.getParameters()) {
        paramsList.add(o);
      }
    }

    if (condition.getCustomerAttributeCondition().size() != 0) {
      for (CustomerAttributeSearchCondition c : condition.getCustomerAttributeCondition()) {
        sqlBuilder.append(" AND");
        sqlBuilder.append(" CUSTOMER_CODE IN");
        sqlBuilder.append("   (");
        sqlBuilder.append("   SELECT");
        sqlBuilder.append("     CUSTOMER_CODE");
        sqlBuilder.append("   FROM");
        sqlBuilder.append("     CUSTOMER_ATTRIBUTE_ANSWER");
        sqlBuilder.append("   WHERE");
        sqlBuilder.append("     CUSTOMER_ATTRIBUTE_NO = ?");
        paramsList.add(c.getCustomerAttributeNo());

        String[] answerItems = c.getAttributeAnswerItem().toArray(new String[c.getAttributeAnswerItem().size()]);
        Object[] answerParams;
        if (StringUtil.hasValueAnyOf(answerItems)) {
          answerParams = (Object[]) answerItems;
        } else {
          answerParams = (Object[]) defaultCustomerAttributeChoicesCondition;
        }

        SqlFragment answerFragment = SqlDialect.getDefault().createInClause("CUSTOMER_ATTRIBUTE_CHOICES_NO", answerParams);

        sqlBuilder.append(" AND ");
        sqlBuilder.append(answerFragment.getFragment());
        for (Object o : answerFragment.getParameters()) {
          paramsList.add(o);
        }
        sqlBuilder.append("   )");
      }
    }
    sqlBuilder.append(" GROUP BY");
    sqlBuilder.append("  SHOP_CODE,");
    sqlBuilder.append("  SHOP_NAME,");
    sqlBuilder.append("  COMMODITY_CODE,");
    sqlBuilder.append("  COMMODITY_NAME");
    sqlBuilder.append(" ) P_TABLE,");
    sqlBuilder.append(" (");
    sqlBuilder.append(" SELECT");
    sqlBuilder.append("   SUM(TOTAL_ORDER_COUNT) AS TOTAL_ORDER_COUNT");
    sqlBuilder.append(" FROM");
    sqlBuilder.append("   CUSTOMER_PREFERENCE");
    sqlBuilder.append(" ) TOTAL_TABLE");
    sqlBuilder.append(" ORDER BY");

    switch (condition.getRearrangeTypeCondition()) {
      case ORDER_BY_PURCHASING_AMOUNT:
        sqlBuilder.append("  PURCHASING_AMOUNT DESC");
        break;
      case ORDER_BY_ORDER_COUNT:
        sqlBuilder.append("  TOTAL_ORDER_COUNT DESC");
        break;
      case ORDER_BY_COMMODITY_CODE:
        sqlBuilder.append("  COMMODITY_CODE ASC");
        break;
      default:
        break;
    }

    setSqlString(sqlBuilder.toString());
    setParameters(paramsList.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
    setMaxFetchSize(condition.getMaxFetchSize());
  }

  public Class<CustomerPreferenceSummary> getRowType() {
    return CustomerPreferenceSummary.class;
  }

}
