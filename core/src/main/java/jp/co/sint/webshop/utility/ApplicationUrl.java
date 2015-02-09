package jp.co.sint.webshop.utility;

import java.io.Serializable;

public class ApplicationUrl implements Serializable {

  private static final long serialVersionUID = 1L;

  private String hostName = "";

  private String fileName = "";

  private int httpPort = 80;

  private int httpsPort = 443;

  public ApplicationUrl() {
  }

  public ApplicationUrl(ApplicationUrl prototype) {
    setHostName(prototype.getHostName());
    setFileName(prototype.getFileName());
    setHttpPort(prototype.getHttpPort());
    setHttpsPort(prototype.getHttpsPort());
  }

  public ApplicationUrl(String hostName, String fileName) {
    setHostName(hostName);
    setFileName(fileName);
  }

  public ApplicationUrl(String hostName, String fileName, int httpPort, int httpsPort) {
    this(hostName, fileName);
    setHttpPort(httpPort);
    setHttpsPort(httpsPort);
  }

  /**
   * hostNameを返します。
   * 
   * @return the hostName
   */
  public String getHostName() {
    return hostName;
  }

  /**
   * hostNameを設定します。
   * 
   * @param hostName
   *          設定する hostName
   */
  public void setHostName(String hostName) {
    this.hostName = StringUtil.coalesce(hostName, "");
  }

  /**
   * fileNameを返します。
   * 
   * @return the fileName
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * fileNameを設定します。
   * 
   * @param fileName
   *          設定する fileName
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   * httpPortを返します。
   * 
   * @return the httpPort
   */
  public int getHttpPort() {
    return httpPort;
  }

  /**
   * httpPortを設定します。
   * 
   * @param httpPort
   *          設定する httpPort
   */
  public void setHttpPort(int httpPort) {
    this.httpPort = httpPort;
  }

  /**
   * httpsPortを返します。
   * 
   * @return the httpsPort
   */
  public int getHttpsPort() {
    return httpsPort;
  }

  /**
   * httpsPortを設定します。
   * 
   * @param httpsPort
   *          設定する httpsPort
   */
  public void setHttpsPort(int httpsPort) {
    this.httpsPort = httpsPort;
  }

  public String getNormalUrl() {
    return WebUtil.getNormalUrl(getHostName(), getHttpPort(), getFileName());
  }

  public String getSecureUrl() {
    return WebUtil.getSecureUrl(getHostName(), getHttpsPort(), getFileName());
  }

  public String getConditionalUrl(boolean isSecure) {
    if (isSecure) {
      return getSecureUrl();
    } else {
      return getNormalUrl();
    }
  }

}
