package jp.co.sint.webshop.service.communication;

import jp.co.sint.webshop.service.SearchCondition;

public class GiftCardRuleListSearchCondition extends SearchCondition {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String searchCardCode;

  private String searchCardName;

  private String cardHistoryNo;

  private String updateUser;

  /**
   * @return the searchCardCode
   */
  public String getSearchCardCode() {
    return searchCardCode;
  }

  /**
   * @param searchCardCode
   *          the searchCardCode to set
   */
  public void setSearchCardCode(String searchCardCode) {
    this.searchCardCode = searchCardCode;
  }

  /**
   * @return the searchCardName
   */
  public String getSearchCardName() {
    return searchCardName;
  }

  /**
   * @param searchCardName
   *          the searchCardName to set
   */
  public void setSearchCardName(String searchCardName) {
    this.searchCardName = searchCardName;
  }

  /**
   * @return the cardHistoryNo
   */
  public String getCardHistoryNo() {
    return cardHistoryNo;
  }

  /**
   * @param cardHistoryNo
   *          the cardHistoryNo to set
   */
  public void setCardHistoryNo(String cardHistoryNo) {
    this.cardHistoryNo = cardHistoryNo;
  }

  /**
   * @return the updateUser
   */
  public String getUpdateUser() {
    return updateUser;
  }

  /**
   * @param updateUser
   *          the updateUser to set
   */
  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser;
  }

}
