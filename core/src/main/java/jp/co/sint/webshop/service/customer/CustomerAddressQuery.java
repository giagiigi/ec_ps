package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.CustomerAddress;

import org.apache.log4j.Logger;

public class CustomerAddressQuery extends AbstractQuery<CustomerAddress> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private int pageSize = 10;

  private int pageNumber = 1;

  public static final String LOAD_ALL_QUERY = DatabaseUtil.getSelectAllQuery(CustomerAddress.class)
      + " WHERE CUSTOMER_CODE = ? ORDER BY ADDRESS_NO";

  public CustomerAddressQuery() {

  }

  public CustomerAddressQuery(CustomerSearchCondition condition) {

    List<Object> params = new ArrayList<Object>();

    params.add(condition.getCustomerCode());

    setSqlString(LOAD_ALL_QUERY);
    setParameters(params.toArray());

    this.pageNumber = condition.getCurrentPage();
    this.pageSize = condition.getPageSize();

    Logger logger = Logger.getLogger(this.getClass());
    logger.info("sql:" + LOAD_ALL_QUERY);

  }

  public Class<CustomerAddress> getRowType() {
    return CustomerAddress.class;
  }

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

}
