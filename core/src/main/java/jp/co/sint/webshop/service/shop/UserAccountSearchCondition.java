package jp.co.sint.webshop.service.shop;

import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.service.SearchCondition;

public class UserAccountSearchCondition extends SearchCondition {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Metadata(name = "ショップコード")
  private String shopCode;

  @Metadata(name = "管理者ID")
  private String userLoginId;

  @Metadata(name = "管理者名")
  private String userName;

  private boolean dispSuperUser = false;

  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return the userLoginId
   */
  public String getUserLoginId() {
    return userLoginId;
  }

  /**
   * @param userLoginId
   *          the userLoginId to set
   */
  public void setUserLoginId(String userLoginId) {
    this.userLoginId = userLoginId;
  }

  /**
   * @return the userName
   */
  public String getUserName() {
    return userName;
  }

  /**
   * @param userName
   *          the userName to set
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * @return the dispSuperUser
   */
  public boolean isDispSuperUser() {
    return dispSuperUser;
  }

  /**
   * @param dispSuperUser
   *          the dispSuperUser to set
   */
  public void setDispSuperUser(boolean dispSuperUser) {
    this.dispSuperUser = dispSuperUser;
  }

}
