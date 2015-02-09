package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class MemberSearchQuery extends AbstractQuery<MemberSearchInfo> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // 会员情报
  public static final String LOAD_CUSTOMER_QUERY = "SELECT DISTINCT C.CUSTOMER_CODE," + " C.LAST_NAME,"
      + " C.EMAIL," + " C.CUSTOMER_STATUS," + " C.SEX," + " C.CAUTION"
      + " FROM CUSTOMER C";

  /** default constructor */
  public MemberSearchQuery() {

  }

  public MemberSearchQuery(MemberSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    if (condition != null) { // 検索条件設定
      builder.append(LOAD_CUSTOMER_QUERY);
      // soukai add 2012/01/31 ob start
      if (StringUtil.hasValue(condition.getSearchOrderNo())) {
        builder.append(" INNER JOIN ORDER_HEADER AS OH ON OH.ORDER_NO = ? AND OH.CUSTOMER_CODE = C.CUSTOMER_CODE");
        params.add(condition.getSearchOrderNo());
      }

      // 手机号码 || 固定电话
      if (StringUtil.hasValue(condition.getSearchMobile())
          || StringUtil.hasValue(condition.getSearchTel())) {
        builder.append(" INNER JOIN CUSTOMER_ADDRESS CA ON C.CUSTOMER_CODE = CA.CUSTOMER_CODE ");

        if (StringUtil.hasValue(condition.getSearchMobile())) {
          builder.append(" AND CA.MOBILE_NUMBER = ?");
          params.add(condition.getSearchMobile());
        }

        if (StringUtil.hasValue(condition.getSearchTel())) {
          SqlFragment fragment = SqlDialect.getDefault().createLikeClause(
              "REPLACE(CA.PHONE_NUMBER,'-','')", condition.getSearchTel(),
              LikeClauseOption.PARTIAL_MATCH);

          builder.append(" AND " + fragment.getFragment());
          for (Object o : fragment.getParameters()) {
            params.add(o);
          }
        }
      }
      
      // 运单号
      if (StringUtil.hasValue(condition.getSearchDeliverySlipNo())) {
        builder.append(" INNER JOIN SHIPPING_HEADER SH ON C.CUSTOMER_CODE = SH.CUSTOMER_CODE ");
        SqlFragment fragment = SqlDialect
            .getDefault()
            .createLikeClause(
                " AND SH.DELIVERY_SLIP_NO = (SELECT DELIVERY_SLIP_NO FROM SHIPPING_REALITY_DETAIL WHERE DELIVERY_SLIP_NO ",
                condition.getSearchDeliverySlipNo(), LikeClauseOption.PARTIAL_MATCH);

        builder.append("" + fragment.getFragment());
        builder.append(")");
        for (Object o : fragment.getParameters()) {
          params.add(o);
        }
      }
     // soukai add 2012/01/31 ob end

      builder.append(" WHERE 1 = 1");

      // 邮件地址
      if (StringUtil.hasValue(condition.getSearchEmail())) {
        SqlFragment fragment = SqlDialect.getDefault().createLikeClause("C.EMAIL",
            condition.getSearchEmail(), LikeClauseOption.PARTIAL_MATCH);

        builder.append(" AND " + fragment.getFragment());
        for (Object o : fragment.getParameters()) {
          params.add(o);
        }
      }
      
      if (StringUtil.hasValue(condition.getSearchCustomerCode())) {
          builder.append(" AND C.CUSTOMER_CODE = ?");
          params.add(condition.getSearchCustomerCode());
        }
      
      if (StringUtil.hasValue(condition.getSearchCustomerName())) {
          builder.append(" AND C.LAST_NAME = ?");
          params.add(condition.getSearchCustomerName());
        }

      // soukai add 2012/01/31 ob start
      
      // soukai add 2012/01/31 ob end
      // 会员状态
      // builder.append(" AND C.CUSTOMER_STATUS <> " +
      // CustomerStatus.WITHDRAWED.getValue());

      setPageNumber(condition.getCurrentPage());
      setPageSize(condition.getPageSize());
      setMaxFetchSize(condition.getMaxFetchSize());
    }

    // 排序
    builder.append(" ORDER BY C.CUSTOMER_CODE DESC");

    setSqlString(builder.toString());
    setParameters(params.toArray());
  }

  public Class<MemberSearchInfo> getRowType() {
    return MemberSearchInfo.class;
  }
}
