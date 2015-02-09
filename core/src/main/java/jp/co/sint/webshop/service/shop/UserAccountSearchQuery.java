package jp.co.sint.webshop.service.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SearchQuery;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.dto.UserAccount;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class UserAccountSearchQuery extends SimpleQuery implements SearchQuery<UserAccount> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private int pageSize = 10;

  private int pageNumber = 1;

  public UserAccountSearchQuery() {
    super();
  }

  public UserAccountSearchQuery(UserAccountSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append(DatabaseUtil.getSelectAllQuery(UserAccount.class) + " WHERE 1 = 1 ");
    if (StringUtil.hasValue(condition.getShopCode())) {
      builder.append("AND SHOP_CODE = ? ");
      params.add(condition.getShopCode());
    }

    if (StringUtil.hasValue(condition.getUserLoginId())) {
      SqlFragment fragment = SqlDialect.getDefault().createLikeClause("USER_LOGIN_ID", condition.getUserLoginId(),
          LikeClauseOption.STARTS_WITH);
      builder.append("AND " + fragment.getFragment());
      for (Object o : fragment.getParameters()) {
        params.add(o);
      }
    }

    if (StringUtil.hasValue(condition.getUserName())) {
      SqlFragment fragment = SqlDialect.getDefault().createLikeClause("USER_NAME", condition.getUserName(),
          LikeClauseOption.PARTIAL_MATCH);

      builder.append("AND " + fragment.getFragment());
      for (Object o : fragment.getParameters()) {
        params.add(o);
      }
    }

    if (!condition.isDispSuperUser()) {
      builder.append("AND USER_CODE != ?");
      params.add(DIContainer.getWebshopConfig().getSiteUserCode());
    }

    builder.append(" ORDER BY SHOP_CODE, USER_LOGIN_ID ");

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

  public Class<UserAccount> getRowType() {
    return UserAccount.class;
  }

}
