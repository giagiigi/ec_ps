package jp.co.sint.webshop.service.customer;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;

public class InputCouponReport {

  private static final long serialVersionUID = 1L;

  /** クーポンルール番号 */
  @Required
  @Length(8)
  @Digit
  @Metadata(name = "クーポン番号", order = 1)
  private String couponIssueNo;

  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客コード", order = 2)
  private String customerCode;

  public String getCouponIssueNo() {
    return couponIssueNo;
  }

  public void setCouponIssueNo(String couponIssueNo) {
    this.couponIssueNo = couponIssueNo;
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

}
