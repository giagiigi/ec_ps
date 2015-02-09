package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class InquiryCountQuery extends AbstractQuery<InquirySearchInfo> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;
  
  /** default constructor */
  public InquiryCountQuery() {

  }
  
  public InquiryCountQuery(InquirySearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    if (condition != null) { // 検索条件設定
      builder.append(" SELECT");
      builder.append(" INQUIRY_STATUS,");
      builder.append(" COUNT(*) AS INQUIRY_STATUS_COUNT");
      builder.append(" FROM (");
      builder.append(InquirySearchQuery.LOAD_INQUIRY_QUERY);
      builder.append(" WHERE 1 = 1");
      
      // 手机号码
      if (StringUtil.hasValue(condition.getSearchMobile())) {
        builder.append(" AND CA.MOBILE_NUMBER = ?");
        params.add(condition.getSearchMobile());
      }
      
      // 会员名
      if (StringUtil.hasValue(condition.getSearchCustomerName())) {
        SqlFragment fragment = SqlDialect.getDefault().createLikeClause("CA.ADDRESS_LAST_NAME", condition.getSearchCustomerName(),
            LikeClauseOption.PARTIAL_MATCH);

        builder.append(" AND " + fragment.getFragment());
        for (Object o : fragment.getParameters()) {
          params.add(o);
        }
      }
      
      // 会员编号
      if (StringUtil.hasValue(condition.getSearchCustomerCode())) {
        builder.append(" AND IH.CUSTOMER_CODE = ?");
        params.add(condition.getSearchCustomerCode());
      }
      
      // 担当者名
      if (StringUtil.hasValue(condition.getSearchPersonInChargeName())) {
        SqlFragment fragment = SqlDialect.getDefault().createLikeClause("ID.PERSON_IN_CHARGE_NAME", condition.getSearchPersonInChargeName(),
            LikeClauseOption.PARTIAL_MATCH);

        builder.append(" AND " + fragment.getFragment());
        for (Object o : fragment.getParameters()) {
          params.add(o);
        }
      }
      
      // 担当者编号
      if (StringUtil.hasValue(condition.getSearchPersonInChargeNo())) {
        builder.append(" AND ID.PERSON_IN_CHARGE_NO = ?");
        params.add(condition.getSearchPersonInChargeNo());
      }
      
      // 受理日期
      if (StringUtil.hasValueAnyOf(condition.getSearchAcceptDateFrom(), condition.getSearchAcceptDateTo())) {
        SqlDialect dialect = SqlDialect.getDefault();
        SqlFragment fragment = dialect.createRangeClause("IH.ACCEPT_DATETIME", DateUtil.fromString(condition.getSearchAcceptDateFrom()
            + " 00:00:00", true), DateUtil.fromString(condition.getSearchAcceptDateTo() + " 23:59:59", true));
        builder.append(" AND ");
        builder.append(fragment.getFragment());
        params.addAll(Arrays.asList(fragment.getParameters()));
      }
      
      // 更新日期
      if (StringUtil.hasValueAnyOf(condition.getSearchAcceptUpdateFrom(), condition.getSearchAcceptUpdateTo())) {
        SqlDialect dialect = SqlDialect.getDefault();
        SqlFragment fragment = dialect.createRangeClause("ID.ACCEPT_DATETIME", DateUtil.fromString(condition.getSearchAcceptUpdateFrom()
            + " 00:00:00", true), DateUtil.fromString(condition.getSearchAcceptUpdateTo() + " 23:59:59", true));
        builder.append(" AND ");
        builder.append(fragment.getFragment());
        params.addAll(Arrays.asList(fragment.getParameters()));
      }
      
      // 咨询状态
      if (StringUtil.hasValue(condition.getSearchInquiryStatus())) {
        builder.append(" AND ID.INQUIRY_STATUS = ?");
        params.add(condition.getSearchInquiryStatus());
      }
      
      // 大分类
      if (StringUtil.hasValue(condition.getSearchLargeCategory())) {
        builder.append(" AND IH.LARGE_CATEGORY = ?");
        params.add(condition.getSearchLargeCategory());
      }
      
      // 关联小分类
      if (StringUtil.hasValue(condition.getSearchSmallCategory())) {
        builder.append(" AND IH.SMALL_CATEGORY = ?");
        params.add(condition.getSearchSmallCategory());
      }
      
      // 咨询途径
      if (StringUtil.hasValue(condition.getSearchInquiryWay())) {
        builder.append(" AND IH.INQUIRY_WAY = ?");
        params.add(condition.getSearchInquiryWay());
      }
      
      // IB/OB区分
      if (StringUtil.hasValue(condition.getSearchIbObType())) {
        builder.append(" AND IH.IB_OB_TYPE = ?");
        params.add(condition.getSearchIbObType());
      }
      
      builder.append(" ) MAIN");
      builder.append(" GROUP BY INQUIRY_STATUS");

      //setPageNumber(condition.getCurrentPage());
      //setPageSize(condition.getPageSize());
      setMaxFetchSize(condition.getMaxFetchSize());
    }
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
  }

  public Class<InquirySearchInfo> getRowType() {
    return InquirySearchInfo.class;
  }
}
