package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.StringUtil;

public class MemberInquiryHistoryQuery extends AbstractQuery<MemberInquiryHistory> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // 咨询情报
  public static final String LOAD_INQUIRY_QUERY = "SELECT" +
  		" IH.INQUIRY_HEADER_NO," +
  		" IH.LARGE_CATEGORY," +
  		" IH.SMALL_CATEGORY," +
  		" IH.INQUIRY_WAY," +
  		" IH.INQUIRY_SUBJECT," +
  		" TO_CHAR(IH.ACCEPT_DATETIME, 'YYYY/MM/DD HH24:MI:SS') AS ACCEPT_DATE," +
  		" ID.INQUIRY_STATUS," +
  		" TO_CHAR(ID.ACCEPT_DATETIME, 'YYYY/MM/DD HH24:MI:SS') AS ACCEPT_UPDATE," +
  		" ID.PERSON_IN_CHARGE_NO," +
  		" ID.PERSON_IN_CHARGE_NAME" +
  		" FROM INQUIRY_HEADER IH" +
  		" INNER JOIN (" +
      "   SELECT ID1.* " +
      "   FROM INQUIRY_DETAIL ID1" +
      "   INNER JOIN (" +
      "     SELECT INQUIRY_HEADER_NO, MAX(ACCEPT_DATETIME) AS ACCEPT_DATETIME FROM INQUIRY_DETAIL GROUP BY INQUIRY_HEADER_NO" +
      "   ) ID2 ON ID1.INQUIRY_HEADER_NO = ID2.INQUIRY_HEADER_NO AND ID1.ACCEPT_DATETIME = ID2.ACCEPT_DATETIME" +
  		" ) ID ON ID.INQUIRY_HEADER_NO = IH.INQUIRY_HEADER_NO";

  /** default constructor */
  public MemberInquiryHistoryQuery() {

  }
  
  public MemberInquiryHistoryQuery(MemberSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    if (condition != null) { // 検索条件設定
      builder.append(LOAD_INQUIRY_QUERY);
      builder.append(" WHERE 1 = 1");

      // 会员编号
      if (StringUtil.hasValue(condition.getSearchCustomerCode())) {
        builder.append(" AND IH.CUSTOMER_CODE = ?");
        params.add(condition.getSearchCustomerCode());
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

  public Class<MemberInquiryHistory> getRowType() {
    return MemberInquiryHistory.class;
  }
}
