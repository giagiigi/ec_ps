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


public class CustomerMessageSearchQuery extends AbstractQuery<MessageHeadLine> {

  /** uid */
  private static final long serialVersionUID = 1L;
  
  private static final String BASE_SQL
    = "SELECT "
    + "CM.ORM_ROWID, "
    + "CM.CUSTOMER_CODE, "
    + "CM.MESSAGE, "
    + "CM.CREATED_DATETIME "
    + "FROM CUSTOMER_MESSAGE CM ";
  
  private static final String SORT_CONDITION = " ORDER BY "
    +" CM.CREATED_DATETIME DESC";

  public CustomerMessageSearchQuery() {
  }

  public CustomerMessageSearchQuery(CustomerMessageSearchCondition condition) {

    StringBuilder builder = new StringBuilder(BASE_SQL);
    builder.append(" WHERE 1 = 1 ");
    List<Object> params = new ArrayList<Object>();

    // 検索条件:顾客编号
    if (StringUtil.hasValue(condition.getSearchCustomerCode())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      builder.append(" CM.CUSTOMER_CODE = ? ");
      params.add(condition.getSearchCustomerCode());
      // commented by zhangfeng 2014/4/9(for the reason of change partly match to totally match)
      //SqlFragment fragment = dialect.createLikeClause("CM.CUSTOMER_CODE", condition.getSearchCustomerCode(),
      //    LikeClauseOption.PARTIAL_MATCH);
      //builder.append(fragment.getFragment());
      //params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 検索条件:留言時間
    if (StringUtil.hasValueAnyOf(condition.getSearchMessageStartDatetimeFrom(), condition.getSearchMessageEndDatetimeTo())) {
      if (StringUtil.hasValue(condition.getSearchMessageStartDatetimeFrom())) {
        condition.setSearchMessageStartDatetimeFrom(condition.getSearchMessageStartDatetimeFrom().substring(0, 17) + "00");
        builder.append(" AND ");
        builder.append(" CM.CREATED_DATETIME >= ? ");
        params.add(DateUtil.fromString(condition.getSearchMessageStartDatetimeFrom(), true));
      }
      if (StringUtil.hasValue(condition.getSearchMessageEndDatetimeTo())) {
        condition.setSearchMessageEndDatetimeTo(condition.getSearchMessageEndDatetimeTo().substring(0, 17) + "59");
        builder.append(" AND ");
        builder.append(" CM.CREATED_DATETIME <= ? ");
        params.add(DateUtil.fromString(condition.getSearchMessageEndDatetimeTo(), true));
      }
    }

    // 並べ替え条件(按创建时间降序排序)
    builder.append(SORT_CONDITION);

    setSqlString(builder.toString());
    setParameters(params.toArray());

    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

  
  public Class<MessageHeadLine> getRowType() {
    return MessageHeadLine.class;
  }

}
