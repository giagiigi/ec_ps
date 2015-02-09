package jp.co.sint.webshop.service.shop;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.service.SearchCondition;

public class UserAccessLogSearchCondition extends SearchCondition {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  @AlphaNum2
  @Length(20)
  @Metadata(name = "管理者ID", order = 1)
  private String loginId;

  @Length(20)
  @Metadata(name = "管理ユーザ名", order = 2)
  private String userName;

  @Datetime
  @Metadata(name = "日付_From", order = 3)
  private String datetimeFrom;

  @Datetime
  @Metadata(name = "日付_To", order = 4)
  private String datetimeTo;

  @Length(1)
  @Metadata(name = "オペレーションコード", order = 5)
  private String operationCode;

  public String getDatetimeFrom() {
    return datetimeFrom;
  }

  public void setDatetimeFrom(String datetimeFrom) {
    this.datetimeFrom = datetimeFrom;
  }

  public String getDatetimeTo() {
    return datetimeTo;
  }

  public void setDatetimeTo(String datetimeTo) {
    this.datetimeTo = datetimeTo;
  }

  /**
   * loginIdを取得します。
   * 
   * @return the loginId
   */
  public String getLoginId() {
    return loginId;
  }

  /**
   * loginIdを設定します。
   * 
   * @param loginId
   *          the loginId to set
   */
  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  /**
   * @return the operationCode
   */
  public String getOperationCode() {
    return operationCode;
  }

  /**
   * @param operationCode
   *          the operationCode to set
   */
  public void setOperationCode(String operationCode) {
    this.operationCode = operationCode;
  }

  /**
   * userNameを取得します。
   * 
   * @return the userName
   */
  public String getUserName() {
    return userName;
  }

  /**
   * userNameを設定します。
   * 
   * @param userName
   *          the userName to set
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * shopCodeを取得します。
   * 
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

}
