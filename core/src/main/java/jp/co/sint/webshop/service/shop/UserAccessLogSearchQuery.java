package jp.co.sint.webshop.service.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.SearchQuery;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.dto.UserAccessLog;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class UserAccessLogSearchQuery extends SimpleQuery implements SearchQuery<UserAccessLog> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private int pageSize = 10;

  private int pageNumber = 1;

  public UserAccessLogSearchQuery() {
    super();
  }

  public UserAccessLogSearchQuery(UserAccessLogSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    String userAccount = "";
    if (StringUtil.hasValue(condition.getLoginId())) {
      userAccount = ", USER_ACCOUNT A";
    }

    builder.append("SELECT L.USER_ACCESS_LOG_ID, L.USER_CODE, L.SHOP_CODE, L.USER_NAME,"
        + " L.OPERATION_CODE, L.ACCESS_DATETIME, L.IP_ADDRESS, L.ORM_ROWID,"
        + " L.CREATED_USER, L.CREATED_DATETIME, L.UPDATED_USER, L.UPDATED_DATETIME FROM USER_ACCESS_LOG L" + userAccount
        + " WHERE 1 = 1");
    if (StringUtil.hasValue(condition.getShopCode())) {
      builder.append(" AND L.SHOP_CODE = ? ");
      params.add(condition.getShopCode());
    }
    if (StringUtil.hasValue(condition.getLoginId())) {
      SqlFragment fragment = SqlDialect.getDefault().createLikeClause("A.USER_LOGIN_ID", condition.getLoginId(),
          LikeClauseOption.STARTS_WITH);
      builder.append(" AND ");
      builder.append("L.USER_CODE = A.USER_CODE");
      builder.append(" AND " + fragment.getFragment());
      for (Object o : fragment.getParameters()) {
        params.add(o);
      }
    }
    if (StringUtil.hasValue(condition.getUserName())) {
      SqlFragment fragment = SqlDialect.getDefault().createLikeClause("L.USER_NAME", condition.getUserName(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + fragment.getFragment());
      for (Object o : fragment.getParameters()) {
        params.add(o);
      }
    }

    if (StringUtil.hasValue(condition.getUserName())) {
      builder.append(" AND ");
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(dialect.getLikeClausePattern("L.USER_NAME"));
      params.add("%" + dialect.escape(condition.getUserName()) + "%");
    }

    if (StringUtil.hasValueAnyOf(condition.getDatetimeFrom(), condition.getDatetimeTo())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createDateRangeClause("L.ACCESS_DATETIME", DateUtil.fromString(condition.getDatetimeFrom(),
          true), DateUtil.fromString(condition.getDatetimeTo(), true));

      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    if (StringUtil.hasValue(condition.getOperationCode())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("L.OPERATION_CODE", condition.getOperationCode(),
          LikeClauseOption.STARTS_WITH);

      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    builder.append(" ORDER BY L.ACCESS_DATETIME DESC, L.USER_CODE ");

    this.setSqlString(builder.toString());
    this.setParameters(params.toArray());

    this.pageNumber = condition.getCurrentPage();
    this.pageSize = condition.getPageSize();
  }

  public int getMaxFetchSize() {
    return 1000;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public int getPageSize() {
    return pageSize;
  }

  public Class<UserAccessLog> getRowType() {
    return UserAccessLog.class;
  }
}
