package jp.co.sint.webshop.service.sms;

import jp.co.sint.webshop.data.domain.ClientSmsType;

public class ClientSmsTypeCondition {

  private ClientSmsType clientSmsType;

  private String smsAddress;

  /**
   * @return the clientSmsType
   */
  public ClientSmsType getClientSmsType() {
    return clientSmsType;
  }

  /**
   * @param clientSmsType
   *          the clientSmsType to set
   */
  public void setClientSmsType(ClientSmsType clientSmsType) {
    this.clientSmsType = clientSmsType;
  }

  /**
   * @return the smsAddress
   */
  public String getSmsAddress() {
    return smsAddress;
  }

  /**
   * @param smsAddress
   *          the smsAddress to set
   */
  public void setSmsAddress(String smsAddress) {
    this.smsAddress = smsAddress;
  }

}
