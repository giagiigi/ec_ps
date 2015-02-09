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

public class InquirySearchQuery extends AbstractQuery<InquirySearchInfo> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // 咨询情报
  public static final String LOAD_INQUIRY_QUERY = " SELECT" +
  		" IH.INQUIRY_HEADER_NO," +
      " TO_CHAR(IH.ACCEPT_DATETIME, 'YYYY/MM/DD HH24:MI:SS') AS ACCEPT_DATETIME," +
      " IH.CUSTOMER_NAME," +
      " IH.CUSTOMER_CODE," +
      " CA.MOBILE_NUMBER," +
  		" IH.LARGE_CATEGORY," +
  		" IH.SMALL_CATEGORY," +
  		" IH.INQUIRY_WAY," +
  		" IH.INQUIRY_SUBJECT," +
      " ID.PERSON_IN_CHARGE_NAME," +
  		" ID.PERSON_IN_CHARGE_NO," +
      " TO_CHAR(ID.ACCEPT_DATETIME, 'YYYY/MM/DD HH24:MI:SS') AS ACCEPT_UPDATETIME," +
      " ID.INQUIRY_STATUS," +
      " IH.IB_OB_TYPE" +
  		" FROM INQUIRY_HEADER IH" +
  		" INNER JOIN (" +
      "   SELECT ID1.* " +
      "   FROM INQUIRY_DETAIL ID1" +
      "   INNER JOIN (" +
      "     SELECT INQUIRY_HEADER_NO, MAX(ACCEPT_DATETIME) AS ACCEPT_DATETIME FROM INQUIRY_DETAIL GROUP BY INQUIRY_HEADER_NO" +
      "   ) ID2 ON ID1.INQUIRY_HEADER_NO = ID2.INQUIRY_HEADER_NO AND ID1.ACCEPT_DATETIME = ID2.ACCEPT_DATETIME" +
  		" ) ID ON ID.INQUIRY_HEADER_NO = IH.INQUIRY_HEADER_NO" +
  		" LEFT JOIN CUSTOMER_ADDRESS CA ON CA.CUSTOMER_CODE = IH.CUSTOMER_CODE AND CA.ADDRESS_NO = " + CustomerConstant.SELF_ADDRESS_NO;

  public static final String DELETE_INQUIRY_QUERY = "DELETE FROM INQUIRY_DETAIL WHERE INQUIRY_HEADER_NO = ?";
  
  /** default constructor */
  public InquirySearchQuery() {

  }
  
  public InquirySearchQuery(InquirySearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    if (condition != null) { // 検索条件設定
      builder.append(LOAD_INQUIRY_QUERY);
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
      
      // 咨询主题
      if (StringUtil.hasValue(condition.getSearchInquirySubject())) {
        SqlFragment fragment = SqlDialect.getDefault().createLikeClause("IH.INQUIRY_SUBJECT", condition.getSearchInquirySubject(),
            LikeClauseOption.PARTIAL_MATCH);

        builder.append(" AND " + fragment.getFragment());
        for (Object o : fragment.getParameters()) {
          params.add(o);
        }
      }

      setPageNumber(condition.getCurrentPage());
      setPageSize(condition.getPageSize());
      setMaxFetchSize(condition.getMaxFetchSize());
    }

    // 排序
    builder.append(" ORDER BY IH.ACCEPT_DATETIME DESC");
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
  }

  public Class<InquirySearchInfo> getRowType() {
    return InquirySearchInfo.class;
  }
}
