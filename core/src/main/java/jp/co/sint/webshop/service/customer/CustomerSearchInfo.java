package jp.co.sint.webshop.service.customer;

import java.io.Serializable;

/**
 * 顧客マスタの検索結果
 * 
 * @author System Integrator Corp.
 */
public class CustomerSearchInfo implements Serializable {

  /**
   * uid
   */
  private static final long serialVersionUID = 1L;

  private String customerCode;

  private String customerGroupCode;

  private String lastName;

  private String firstName;

  private String email;

  private Long requestMailType;

  private String phoneNumber;

  //Add by V10-CH start
  private String mobileNumber;
  //Add by V10-ch end
  
  private String lastNameKana;

  private String firstNameKana;

  private String loginId;

  private String password;

  private String birthDate;

  private String sex;

  private String caution;

  private String loginDatetime;

  private String loginErrorCount;

  private String loginLockedFlg;

  private String customerStatus;

  private String customerAttributeReplyDate;

  private String latestPointAcquiredDate;

  private String restPoint;

  private String temporaryPoint;

  private String clientGroup;

  private String withdrawalRequestDate;

  private String withdrawalDate;

  private String ormRowid;

  private String createdUser;

  private String createdDatetime;

  private String updatedUser;

  private String updatedDatetime;

  private String addressLastName;

  private String addressFirstName;

  private String postalCode;

  private String address1;

  private String address2;

  private String address3;

  private String address4;
  
  //20111224 os013 add start
  private String tmallUserId;
  
  private String customerKbn;
  //20111224 os013 add end
  /**
   * address1を取得します。
   * 
   * @return address1
   */
  public String getAddress1() {
    return address1;
  }

  /**
   * address2を取得します。
   * 
   * @return address2
   */
  public String getAddress2() {
    return address2;
  }

  /**
   * address3を取得します。
   * 
   * @return address3
   */
  public String getAddress3() {
    return address3;
  }

  /**
   * address4を取得します。
   * 
   * @return address4
   */
  public String getAddress4() {
    return address4;
  }

  /**
   * addressFirstNameを取得します。
   * 
   * @return addressFirstName
   */
  public String getAddressFirstName() {
    return addressFirstName;
  }

  /**
   * addressLastNameを取得します。
   * 
   * @return addressLastName
   */
  public String getAddressLastName() {
    return addressLastName;
  }

  /**
   * address1を設定します。
   * 
   * @param address1
   *          address1
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  /**
   * address2を設定します。
   * 
   * @param address2
   *          address2
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  /**
   * address3を設定します。
   * 
   * @param address3
   *          address3
   */
  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  /**
   * address4を設定します。
   * 
   * @param address4
   *          address4
   */
  public void setAddress4(String address4) {
    this.address4 = address4;
  }

  /**
   * addressFirstNameを設定します。
   * 
   * @param addressFirstName
   *          addressFirstName
   */
  public void setAddressFirstName(String addressFirstName) {
    this.addressFirstName = addressFirstName;
  }

  /**
   * addressLastNameを設定します。
   * 
   * @param addressLastName
   *          addressLastName
   */
  public void setAddressLastName(String addressLastName) {
    this.addressLastName = addressLastName;
  }

  /**
   * postalCodeを返します。
   * 
   * @return the postalCode
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * postalCodeを設定します。
   * 
   * @param postalCode
   *          設定する postalCode
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  /**
   * birthDateを取得します。
   * 
   * @return birthDate
   */
  public String getBirthDate() {
    return birthDate;
  }

  /**
   * cautionを取得します。
   * 
   * @return caution
   */
  public String getCaution() {
    return caution;
  }

  /**
   * createdDatetimeを取得します。
   * 
   * @return createdDatetime
   */
  public String getCreatedDatetime() {
    return createdDatetime;
  }

  /**
   * createdUserを取得します。
   * 
   * @return createdUser
   */
  public String getCreatedUser() {
    return createdUser;
  }

  /**
   * customerAttributeReplyDateを取得します。
   * 
   * @return customerAttributeReplyDate
   */
  public String getCustomerAttributeReplyDate() {
    return customerAttributeReplyDate;
  }

  /**
   * customerStatusを取得します。
   * 
   * @return customerStatus
   */
  public String getCustomerStatus() {
    return customerStatus;
  }

  /**
   * firstNameKanaを取得します。
   * 
   * @return firstNameKana
   */
  public String getFirstNameKana() {
    return firstNameKana;
  }

  /**
   * lastNameKanaを取得します。
   * 
   * @return lastNameKana
   */
  public String getLastNameKana() {
    return lastNameKana;
  }

  /**
   * latestPointAcquiredDateを取得します。
   * 
   * @return latestPointAcquiredDate
   */
  public String getLatestPointAcquiredDate() {
    return latestPointAcquiredDate;
  }

  /**
   * loginDatetimeを取得します。
   * 
   * @return loginDatetime
   */
  public String getLoginDatetime() {
    return loginDatetime;
  }

  /**
   * loginErrorCountを取得します。
   * 
   * @return loginErrorCount
   */
  public String getLoginErrorCount() {
    return loginErrorCount;
  }

  /**
   * loginIdを取得します。
   * 
   * @return loginId
   */
  public String getLoginId() {
    return loginId;
  }

  /**
   * loginLockedFlgを取得します。
   * 
   * @return loginLockedFlg
   */
  public String getLoginLockedFlg() {
    return loginLockedFlg;
  }

  /**
   * ormRowidを取得します。
   * 
   * @return ormRowid
   */
  public String getOrmRowid() {
    return ormRowid;
  }

  /**
   * passwordを取得します。
   * 
   * @return password
   */
  public String getPassword() {
    return password;
  }

  /**
   * restPointを取得します。
   * 
   * @return restPoint
   */
  public String getRestPoint() {
    return restPoint;
  }

  /**
   * sexを取得します。
   * 
   * @return sex
   */
  public String getSex() {
    return sex;
  }

  /**
   * temporaryPointを取得します。
   * 
   * @return temporaryPoint
   */
  public String getTemporaryPoint() {
    return temporaryPoint;
  }

  /**
   * updatedDatetimeを取得します。
   * 
   * @return updatedDatetime
   */
  public String getUpdatedDatetime() {
    return updatedDatetime;
  }

  /**
   * updatedUserを取得します。
   * 
   * @return updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * withdrawalDateを取得します。
   * 
   * @return withdrawalDate
   */
  public String getWithdrawalDate() {
    return withdrawalDate;
  }

  /**
   * withdrawalRequestDateを取得します。
   * 
   * @return withdrawalRequestDate
   */
  public String getWithdrawalRequestDate() {
    return withdrawalRequestDate;
  }

  /**
   * birthDateを設定します。
   * 
   * @param birthDate
   *          birthDate
   */
  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  /**
   * @return clientGroup
   */
  public String getClientGroup() {
    return clientGroup;
  }

  /**
   * @param clientGroup
   *          設定する clientGroup
   */
  public void setClientGroup(String clientGroup) {
    this.clientGroup = clientGroup;
  }

  /**
   * cautionを設定します。
   * 
   * @param caution
   *          caution
   */
  public void setCaution(String caution) {
    this.caution = caution;
  }

  /**
   * createdDatetimeを設定します。
   * 
   * @param createdDatetime
   *          createdDatetime
   */
  public void setCreatedDatetime(String createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  /**
   * createdUserを設定します。
   * 
   * @param createdUser
   *          createdUser
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * customerAttributeReplyDateを設定します。
   * 
   * @param customerAttributeReplyDate
   *          customerAttributeReplyDate
   */
  public void setCustomerAttributeReplyDate(String customerAttributeReplyDate) {
    this.customerAttributeReplyDate = customerAttributeReplyDate;
  }

  /**
   * customerStatusを設定します。
   * 
   * @param customerStatus
   *          customerStatus
   */
  public void setCustomerStatus(String customerStatus) {
    this.customerStatus = customerStatus;
  }

  /**
   * firstNameKanaを設定します。
   * 
   * @param firstNameKana
   *          firstNameKana
   */
  public void setFirstNameKana(String firstNameKana) {
    this.firstNameKana = firstNameKana;
  }

  /**
   * lastNameKanaを設定します。
   * 
   * @param lastNameKana
   *          lastNameKana
   */
  public void setLastNameKana(String lastNameKana) {
    this.lastNameKana = lastNameKana;
  }

  /**
   * latestPointAcquiredDateを設定します。
   * 
   * @param latestPointAcquiredDate
   *          latestPointAcquiredDate
   */
  public void setLatestPointAcquiredDate(String latestPointAcquiredDate) {
    this.latestPointAcquiredDate = latestPointAcquiredDate;
  }

  /**
   * loginDatetimeを設定します。
   * 
   * @param loginDatetime
   *          loginDatetime
   */
  public void setLoginDatetime(String loginDatetime) {
    this.loginDatetime = loginDatetime;
  }

  /**
   * loginErrorCountを設定します。
   * 
   * @param loginErrorCount
   *          loginErrorCount
   */
  public void setLoginErrorCount(String loginErrorCount) {
    this.loginErrorCount = loginErrorCount;
  }

  /**
   * loginIdを設定します。
   * 
   * @param loginId
   *          loginId
   */
  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  /**
   * loginLockedFlgを設定します。
   * 
   * @param loginLockedFlg
   *          loginLockedFlg
   */
  public void setLoginLockedFlg(String loginLockedFlg) {
    this.loginLockedFlg = loginLockedFlg;
  }

  /**
   * ormRowidを設定します。
   * 
   * @param ormRowid
   *          ormRowid
   */
  public void setOrmRowid(String ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * passwordを設定します。
   * 
   * @param password
   *          password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * restPointを設定します。
   * 
   * @param restPoint
   *          restPoint
   */
  public void setRestPoint(String restPoint) {
    this.restPoint = restPoint;
  }

  /**
   * sexを設定します。
   * 
   * @param sex
   *          sex
   */
  public void setSex(String sex) {
    this.sex = sex;
  }

  /**
   * temporaryPointを設定します。
   * 
   * @param temporaryPoint
   *          temporaryPoint
   */
  public void setTemporaryPoint(String temporaryPoint) {
    this.temporaryPoint = temporaryPoint;
  }

  /**
   * updatedDatetimeを設定します。
   * 
   * @param updatedDatetime
   *          updatedDatetime
   */
  public void setUpdatedDatetime(String updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  /**
   * updatedUserを設定します。
   * 
   * @param updatedUser
   *          updatedUser
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * withdrawalDateを設定します。
   * 
   * @param withdrawalDate
   *          withdrawalDate
   */
  public void setWithdrawalDate(String withdrawalDate) {
    this.withdrawalDate = withdrawalDate;
  }

  /**
   * withdrawalRequestDateを設定します。
   * 
   * @param withdrawalRequestDate
   *          withdrawalRequestDate
   */
  public void setWithdrawalRequestDate(String withdrawalRequestDate) {
    this.withdrawalRequestDate = withdrawalRequestDate;
  }

  /**
   * customerCodeを取得します。
   * 
   * @return customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * customerGroupCodeを取得します。
   * 
   * @return customerGroupCode
   */
  public String getCustomerGroupCode() {
    return customerGroupCode;
  }

  /**
   * emailを取得します。
   * 
   * @return email
   */
  public String getEmail() {
    return email;
  }

  /**
   * firstNameを取得します。
   * 
   * @return firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * lastNameを取得します。
   * 
   * @return lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * phoneNumberを取得します。
   * 
   * @return phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * requestMailTypeを取得します。
   * 
   * @return requestMailType
   */
  public Long getRequestMailType() {
    return requestMailType;
  }

  /**
   * customerCodeを設定します。
   * 
   * @param customerCode
   *          customerCode
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * customerGroupCodeを設定します。
   * 
   * @param customerGroupCode
   *          customerGroupCode
   */
  public void setCustomerGroupCode(String customerGroupCode) {
    this.customerGroupCode = customerGroupCode;
  }

  /**
   * emailを設定します。
   * 
   * @param email
   *          email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * firstNameを設定します。
   * 
   * @param firstName
   *          firstName
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * lastNameを設定します。
   * 
   * @param lastName
   *          lastName
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * phoneNumberを設定します。
   * 
   * @param phoneNumber
   *          phoneNumber
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * requestMailTypeを設定します。
   * 
   * @param requestMailType
   *          requestMailType
   */
  public void setRequestMailType(Long requestMailType) {
    this.requestMailType = requestMailType;
  }

  
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

  
  public String getTmallUserId() {
    return tmallUserId;
  }

  
  public void setTmallUserId(String tmallUserId) {
    this.tmallUserId = tmallUserId;
  }

  
  public String getCustomerKbn() {
    return customerKbn;
  }

  
  public void setCustomerKbn(String customerKbn) {
    this.customerKbn = customerKbn;
  }
 
}
