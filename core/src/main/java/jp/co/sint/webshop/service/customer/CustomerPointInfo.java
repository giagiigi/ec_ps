package jp.co.sint.webshop.service.customer;

import java.util.Date;

import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.utility.DateUtil;

public class CustomerPointInfo extends Customer {

  /**
   * 「顧客マスタ(CUSTOMER)」の拡張Bean
   */
  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** ポイント有効期日 */
  @Required
  private Date expiredPointDate;

  /** 電話番号 */
  private String phoneNumber;

  /** 手机番号 */
  private String mobileNumber;
  
  
  /**
   * mobileNumberを取得します。
   *
   * @return mobileNumber mobileNumber
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  
  /**
   * mobileNumberを設定します。
   *
   * @param mobileNumber 
   *          mobileNumber
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  /**
   * @return expiredPointDate
   */
  public Date getExpiredPointDate() {
    return DateUtil.immutableCopy(expiredPointDate);
  }

  /**
   * @param expiredPointDate
   *          設定する expiredPointDate
   */
  public void setExpiredPointDate(Date expiredPointDate) {
    this.expiredPointDate = DateUtil.immutableCopy(expiredPointDate);
  }

  /**
   * @return phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @param phoneNumber
   *          設定する phoneNumber
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

}
