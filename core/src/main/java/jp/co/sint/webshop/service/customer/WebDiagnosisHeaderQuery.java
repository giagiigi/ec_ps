package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.dto.WebDiagnosisHeader;

public final class WebDiagnosisHeaderQuery extends AbstractQuery<WebDiagnosisHeader>{
  
  
  public WebDiagnosisHeaderQuery(CustomerSearchCondition condition) {
    List<Object> params = new ArrayList<Object>();

    params.add(condition.getCustomerCode());

    setSqlString(LOAD_WEBDIAGNOSISHEADER_LIST);
    setParameters(params.toArray());

    this.pageNumber = condition.getCurrentPage();
    this.pageSize = condition.getPageSize();

    Logger logger = Logger.getLogger(this.getClass());
    logger.info("sql:" + LOAD_WEBDIAGNOSISHEADER_LIST);
  }

  private static final long serialVersionUID = 1L;
  
  private int pageSize = 10;

  private int pageNumber = 1;

  public static final String LOAD_WEBDIAGNOSISHEADER_LIST =
   "SELECT W.WEB_DIAGNOSIS_HEADER_NO,"
        + " W.CUSTOMER_CODE,CUSTOMER_NAME," 
        + " W.WEB_DIAGNOSIS_DATE,"
        + " W.WEB_DIAGNOSIS_SUBJECT,"
        + " W.WEB_DIAGNOSIS_RESULT_TITLE,"
        + " W.WEB_DIAGNOSIS_RESULT_COMMENT,"
        + " W.ORM_ROWID," 
        + " W.CREATED_USER,"
        + " W.CREATED_DATETIME," 
        + " W.UPDATED_USER,"
        + " W.UPDATED_DATETIME"
        + " FROM WEB_DIAGNOSIS_HEADER AS W"
        + " INNER JOIN CUSTOMER  AS C"
        + " ON C.CUSTOMER_CODE = W.CUSTOMER_CODE"
        + " WHERE W.CUSTOMER_CODE = ?"
        + " ORDER BY W.WEB_DIAGNOSIS_DATE DESC";
  
   // 获得当天的第一次诊断数据
   public static final String SELECT_TODAY_MIN_WEB_DIAGNOSIS_HEADER_NO = "SELECT MIN(WEB_DIAGNOSIS_HEADER_NO) FROM WEB_DIAGNOSIS_HEADER WHERE CUSTOMER_CODE = ? AND " +
     "TO_CHAR(WEB_DIAGNOSIS_DATE, 'YYYYMMDD') = TO_CHAR(NOW(), 'YYYYMMDD') AND WEB_DIAGNOSIS_CODE = ?";

   // 获得第一次诊断数据
   public static final String SELECT_MIN_WEB_DIAGNOSIS_HEADER_NO = "SELECT MIN(WEB_DIAGNOSIS_HEADER_NO) FROM WEB_DIAGNOSIS_HEADER WHERE CUSTOMER_CODE = ?  AND WEB_DIAGNOSIS_CODE = ?";

   // 获得顾客当天的保存诊断次数
   public static final String SELECT_TODAY_DATA = "SELECT COUNT(WEB_DIAGNOSIS_HEADER_NO) FROM WEB_DIAGNOSIS_HEADER WHERE CUSTOMER_CODE = ? AND " +
   		"TO_CHAR(WEB_DIAGNOSIS_DATE, 'YYYYMMDD') = TO_CHAR(NOW(), 'YYYYMMDD') AND WEB_DIAGNOSIS_CODE = ?";
   
   // 获得顾客所有的诊断次数
   public static final String SELECT_ALL_DATA = "SELECT COUNT(WEB_DIAGNOSIS_HEADER_NO) FROM WEB_DIAGNOSIS_HEADER WHERE CUSTOMER_CODE = ?  AND WEB_DIAGNOSIS_CODE = ? ";
   
   public static final String SELECT_OLD_DATA = "SELECT * FROM WEB_DIAGNOSIS_HEADER" +
   		" WHERE CUSTOMER_CODE = ? AND " +
   		" WEB_DIAGNOSIS_CODE = ? AND " +
   		" WEB_DIAGNOSIS_DATE < ?" +
   		" ORDER BY WEB_DIAGNOSIS_DATE DESC";
   
   
   public static final String DELETE_WEB_DIAGNOSIS_HEADER = "DELETE FROM WEB_DIAGNOSIS_HEADER WHERE WEB_DIAGNOSIS_HEADER_NO = ?";
   public static final String DELETE_WEB_DIAGNOSIS_DETAIL = "DELETE FROM WEB_DIAGNOSIS_DETAIL WHERE WEB_DIAGNOSIS_HEADER_NO = ?";
   public static final String DELETE_WEB_DIAGNOSIS_COMMODITY = "DELETE FROM WEB_DIAGNOSIS_COMMODITY WHERE WEB_DIAGNOSIS_HEADER_NO = ?";
    /**
     * pageNumberを取得します。
     * 
     * @return pageNumber
     */
    public int getPageNumber() {
      return pageNumber;
    }

    /**
     * pageSizeを取得します。
     * 
     * @return pageSize
     */
    public int getPageSize() {
      return pageSize;
    }

    /**
     * pageNumberを設定します。
     * 
     * @param pageNumber
     *          pageNumber
     */
    public void setPageNumber(int pageNumber) {
      this.pageNumber = pageNumber;
    }

    /**
     * pageSizeを設定します。
     * 
     * @param pageSize
     *          pageSize
     */
    public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
    }

    @Override
    public Class<WebDiagnosisHeader> getRowType() {
      return WebDiagnosisHeader.class;
    }

}