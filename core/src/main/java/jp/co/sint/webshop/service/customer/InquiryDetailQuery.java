package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.dto.InquiryDetail;
import jp.co.sint.webshop.utility.StringUtil;

public class InquiryDetailQuery extends AbstractQuery<InquiryDetail> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // 咨询情报
  public static final String LOAD_INQUIRY_HEADER_QUERY = " SELECT" +
      " IH.INQUIRY_HEADER_NO," +
      " TO_CHAR(IH.ACCEPT_DATETIME, 'YYYY/MM/DD HH24:MI:SS') AS ACCEPT_DATETIME," +
      " IH.CUSTOMER_NAME," +
      " IH.CUSTOMER_CODE," +
      " CA.MOBILE_NUMBER," +
      " IH.LARGE_CATEGORY," +
      " IH.SMALL_CATEGORY," +
      " IH.INQUIRY_WAY," +
      " IH.INQUIRY_SUBJECT," +
      " ID.INQUIRY_STATUS," +
      " IH.IB_OB_TYPE," +
      " IH.COMMODITY_CODE" +
      " FROM INQUIRY_HEADER IH" +
      " INNER JOIN (" +
      "   SELECT ID1.* " +
      "   FROM INQUIRY_DETAIL ID1" +
      "   INNER JOIN (" +
      "     SELECT INQUIRY_HEADER_NO, MAX(ACCEPT_DATETIME) AS ACCEPT_DATETIME FROM INQUIRY_DETAIL GROUP BY INQUIRY_HEADER_NO" +
      "   ) ID2 ON ID1.INQUIRY_HEADER_NO = ID2.INQUIRY_HEADER_NO AND ID1.ACCEPT_DATETIME = ID2.ACCEPT_DATETIME" +
      " ) ID ON ID.INQUIRY_HEADER_NO = IH.INQUIRY_HEADER_NO" +
      " LEFT JOIN CUSTOMER_ADDRESS CA ON CA.CUSTOMER_CODE = IH.CUSTOMER_CODE AND CA.ADDRESS_NO = " + CustomerConstant.SELF_ADDRESS_NO +
      " WHERE IH.INQUIRY_HEADER_NO = ?";
  
  // 咨询详细情报
  public static final String LOAD_INQUIRY_DETAIL_QUERY = " SELECT" +
  		" INQUIRY_HEADER_NO," +
  		" INQUIRY_DETAIL_NO," +
  		" INQUIRY_CONTENTS," +
  		" INQUIRY_STATUS," +
  		" ACCEPT_DATETIME," +
  		" PERSON_IN_CHARGE_NO," +
  		" PERSON_IN_CHARGE_NAME" +
  		" FROM INQUIRY_DETAIL";
  
  /** default constructor */
  public InquiryDetailQuery() {

  }
  
  public InquiryDetailQuery(InquirySearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    if (condition != null) { // 検索条件設定
      builder.append(LOAD_INQUIRY_DETAIL_QUERY);
      builder.append(" WHERE 1 = 1");
      
      if (StringUtil.hasValue(condition.getSearchInquiryHeaderNo())) {
        builder.append(" AND INQUIRY_HEADER_NO = ?");
        params.add(condition.getSearchInquiryHeaderNo());
      }

      setPageNumber(condition.getCurrentPage());
      setPageSize(condition.getPageSize());
      setMaxFetchSize(condition.getMaxFetchSize());
    }

    // 排序
    builder.append(" ORDER BY ACCEPT_DATETIME");
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
  }

  public Class<InquiryDetail> getRowType() {
    return InquiryDetail.class;
  }
}
