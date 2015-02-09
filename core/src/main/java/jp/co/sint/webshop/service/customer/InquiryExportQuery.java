package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class InquiryExportQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public InquiryExportQuery(InquirySearchCondition condition) {
    List<Object> params = new ArrayList<Object>();
    StringBuilder builder = new StringBuilder();
    builder.append(" SELECT");
    builder.append(" IH.INQUIRY_HEADER_NO,");
    builder.append(" IH.ACCEPT_DATETIME,");
    builder.append(" IH.CUSTOMER_NAME,");
    builder.append(" CA.CUSTOMER_CODE,");
    builder.append(" CA.MOBILE_NUMBER,");
    builder.append(" IH.IB_OB_TYPE,");
    builder.append(" IH.LARGE_CATEGORY,");
    builder.append(" IH.SMALL_CATEGORY,");
    builder.append(" IH.COMMODITY_CODE,");
    builder.append(" IH.INQUIRY_WAY,");
    builder.append(" IH.INQUIRY_SUBJECT,");
    builder.append(" IDL.INQUIRY_STATUS AS INQUIRY_STATUS_LAST,");
    builder.append(" ID.INQUIRY_DETAIL_NO,");
    builder.append(" ID.ACCEPT_DATETIME,");
    builder.append(" ID.PERSON_IN_CHARGE_NAME,");
    builder.append(" ID.PERSON_IN_CHARGE_NO,");
    builder.append(" ID.INQUIRY_STATUS,");
    builder.append(" ID.INQUIRY_CONTENTS,");
    builder.append(" ID.ORM_ROWID,");
    builder.append(" ID.CREATED_USER,");
    builder.append(" ID.CREATED_DATETIME,");
    builder.append(" ID.UPDATED_USER,");
    builder.append(" ID.UPDATED_DATETIME");
    builder.append(" FROM INQUIRY_HEADER IH");
    builder.append(" INNER JOIN INQUIRY_DETAIL ID ON IH.INQUIRY_HEADER_NO = ID.INQUIRY_HEADER_NO");
    builder.append(" INNER JOIN (");
    builder.append("   SELECT ID1.INQUIRY_HEADER_NO, ID1.INQUIRY_STATUS, ID1.PERSON_IN_CHARGE_NAME, ID1.PERSON_IN_CHARGE_NO, ID1.ACCEPT_DATETIME");
    builder.append("   FROM INQUIRY_DETAIL ID1");
    builder.append("   INNER JOIN (");
    builder.append("     SELECT INQUIRY_HEADER_NO, MAX(ACCEPT_DATETIME) AS ACCEPT_DATETIME FROM INQUIRY_DETAIL GROUP BY INQUIRY_HEADER_NO");
    builder.append("   ) ID2 ON ID1.INQUIRY_HEADER_NO = ID2.INQUIRY_HEADER_NO AND ID1.ACCEPT_DATETIME = ID2.ACCEPT_DATETIME");
    builder.append(" ) IDL ON IH.INQUIRY_HEADER_NO = IDL.INQUIRY_HEADER_NO");
    builder.append(" INNER JOIN CUSTOMER_ADDRESS CA ON IH.CUSTOMER_CODE = CA.CUSTOMER_CODE AND CA.ADDRESS_NO = ").append(CustomerConstant.SELF_ADDRESS_NO);
    
    if (condition != null) { // 検索条件設定
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
        SqlFragment fragment = SqlDialect.getDefault().createLikeClause("IDL.PERSON_IN_CHARGE_NAME", condition.getSearchPersonInChargeName(),
            LikeClauseOption.PARTIAL_MATCH);
        builder.append(" AND " + fragment.getFragment());
        for (Object o : fragment.getParameters()) {
          params.add(o);
        }
      }
      
      // 担当者编号
      if (StringUtil.hasValue(condition.getSearchPersonInChargeNo())) {
        builder.append(" AND IDL.PERSON_IN_CHARGE_NO = ?");
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
        SqlFragment fragment = dialect.createRangeClause("IDL.ACCEPT_DATETIME", DateUtil.fromString(condition.getSearchAcceptUpdateFrom()
            + " 00:00:00", true), DateUtil.fromString(condition.getSearchAcceptUpdateTo() + " 23:59:59", true));
        builder.append(" AND ");
        builder.append(fragment.getFragment());
        params.addAll(Arrays.asList(fragment.getParameters()));
      }
      
      // 咨询状态
      if (StringUtil.hasValue(condition.getSearchInquiryStatus())) {
        builder.append(" AND IDL.INQUIRY_STATUS = ?");
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

    }

    // 排序
    builder.append(" ORDER BY IH.INQUIRY_HEADER_NO DESC, ID.ACCEPT_DATETIME");

    setSqlString(builder.toString());
    setParameters(params.toArray());
  }

}
