package jp.co.sint.webshop.service.customer;

import java.io.Serializable;

public class MemberSearchInfo implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String customerCode;

  private String lastName;

  private String email;

  private String phoneNumber;

  private String mobileNumber;

  private String customerStatus;

  private String sex;

  // soukai delete 2012/02/01 ob start
//  private String postalCode;
//
//  private String address1;
//
//  private String address2;
//
//  private String address3;
  // soukai delete 2012/02/01 ob end

  private String caution;

  /**
   * @return the customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * @param customerCode
   *          the customerCode to set
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName
   *          the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email
   *          the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @param phoneNumber
   *          the phoneNumber to set
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * @return the mobileNumber
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  /**
   * @param mobileNumber
   *          the mobileNumber to set
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  /**
   * @return the customerStatus
   */
  public String getCustomerStatus() {
    return customerStatus;
  }

  /**
   * @param customerStatus
   *          the customerStatus to set
   */
  public void setCustomerStatus(String customerStatus) {
    this.customerStatus = customerStatus;
  }

  /**
   * @return the sex
   */
  public String getSex() {
    return sex;
  }

  /**
   * @param sex
   *          the sex to set
   */
  public void setSex(String sex) {
    this.sex = sex;
  }

  /**
   * @return the caution
   */
  public String getCaution() {
    return caution;
  }

  /**
   * @param caution
   *          the caution to set
   */
  public void setCaution(String caution) {
    this.caution = caution;
  }

}
