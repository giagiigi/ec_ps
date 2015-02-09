package jp.co.sint.webshop.service.catalog;

import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.service.ServiceException;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.DateUtil;

public class TaxUtilImpl implements TaxUtil {

  private static final long serialVersionUID = 1L;

  private static final String CONDITION_CLAUSE = "WHERE T.APPLIED_START_DATE = "
      + "(SELECT MAX(T2.APPLIED_START_DATE) FROM TAX T2 WHERE T2.APPLIED_START_DATE < ?)";

  private static final String TAX_RATE_QUERY = "SELECT T.TAX_RATE FROM TAX T " + CONDITION_CLAUSE;

  private static final String APPLIED_DATE_QUERY = "SELECT T.APPLIED_START_DATE FROM TAX T " + CONDITION_CLAUSE;


  public Long getTaxRate() {
    return 0L;
//    return getTaxRate(new Date());
  }


  public Long getTaxRate(Date d) {
    Date appliedStartDate = d;
    Long result = null;
    try {
      if (appliedStartDate == null) {
        appliedStartDate = new Date();
      }
      Object obj = DatabaseUtil.executeScalar(new SimpleQuery(TAX_RATE_QUERY, appliedStartDate));
      result = Long.valueOf(obj.toString());
    } catch (RuntimeException e) {
      result = null;
      throw new ServiceException(Messages.getString("service.catalog.TaxUtilImpl.0"));
    }
    return result;
  }

  public Date getAppliedStartDate() {
    return getAppliedStartDate(DateUtil.getSysdate());
  }


  public Date getAppliedStartDate(Date d) {
    Date appliedStartDate = d;
    Date result = null;
    try {
      if (appliedStartDate == null) {
        appliedStartDate = new Date();
      }
      Object obj = DatabaseUtil.executeScalar(new SimpleQuery(APPLIED_DATE_QUERY, appliedStartDate));
      result = new Date(((java.sql.Date) obj).getTime());
    } catch (RuntimeException e) {
      result = null;
      throw new ServiceException(Messages.getString("service.catalog.TaxUtilImpl.1"));
    }
    return result;
  }

}
