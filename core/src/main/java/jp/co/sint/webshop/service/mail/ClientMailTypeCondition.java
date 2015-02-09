package jp.co.sint.webshop.service.mail;

import jp.co.sint.webshop.data.domain.ClientMailType;

public class ClientMailTypeCondition {

  private ClientMailType clientMailType;

  private String mailAddress;

  /**
   * @return the clientMailType
   */
  public ClientMailType getClientMailType() {
    return clientMailType;
  }

  /**
   * @param clientMailType
   *          the clientMailType to set
   */
  public void setClientMailType(ClientMailType clientMailType) {
    this.clientMailType = clientMailType;
  }

  /**
   * @return the mailAddress
   */
  public String getMailAddress() {
    return mailAddress;
  }

  /**
   * @param mailAddress
   *          the mailAddress to set
   */
  public void setMailAddress(String mailAddress) {
    this.mailAddress = mailAddress;
  }

}
