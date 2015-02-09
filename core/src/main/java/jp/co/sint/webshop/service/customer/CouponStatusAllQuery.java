package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

public class CouponStatusAllQuery extends AbstractQuery<CustomerCoupon> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private int pageSize = 10;

  private int pageNumber = 1;

  // ポイント利用状況
  public static final String LOAD_COUPON_STATUS_QUERY = "SELECT * FROM CUSTOMER_COUPON CC "
    + " WHERE 1 = 1 ";


  public CouponStatusAllQuery() {

  }

  public CouponStatusAllQuery(CouponStatusListSearchCondition condition, String query) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    final String sql = query;

    if (condition != null) { // 検索条件設定
      
      if (StringUtil.hasValue(condition.getSearchShopCode())) {
        builder.append(" AND CC.SHOP_CODE = ?");
        params.add(condition.getSearchShopCode());
      }

      if (StringUtil.hasValue(condition.getSearchCustomerCode())) {
        builder.append(" AND CC.CUSTOMER_CODE = ?");
        params.add(condition.getSearchCustomerCode());
      }

      if (StringUtil.hasValue(condition.getSearchCouponStatus())) {
        builder.append(" AND CC.USE_FLG = ?");
        params.add(condition.getSearchCouponStatus());
      }

      builder.append(" ORDER BY CC.CUSTOMER_COUPON_ID DESC");

      this.pageNumber = condition.getCurrentPage();
      this.pageSize = condition.getPageSize();
    }

    setSqlString(sql + builder.toString());
    setParameters(params.toArray());

    Logger logger = Logger.getLogger(this.getClass());
    logger.info("sql:" + sql + builder.toString());

  }

  public Class<CustomerCoupon> getRowType() {
    return CustomerCoupon.class;
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
