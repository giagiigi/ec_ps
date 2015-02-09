package jp.co.sint.webshop.configure;

import java.io.Serializable;

/**
 * SI Web Shopping 10システム全体の設定情報です。
 * 起動時にapplicationContext-configure.xmlの内容を読み込んで初期化します。
 * 
 * @author System Integrator Corp.
 */
public class JdStockSendMailConfig implements Serializable {

  /** uid */
  private static final long serialVersionUID = 1L;
  
  private  String mailFromAddr ="";

  private  String mailFromName="";

  private  String mailToAddr="";

  private  String mailToName=""; 

  
  public  String getMailFromAddr() {
    return mailFromAddr;
  }

  
  public  void setMailFromAddr(String mailFromAddr) {
    this.mailFromAddr = mailFromAddr;
  }

  
  public  String getMailFromName() {
    return mailFromName;
  }

  
  public  void setMailFromName(String mailFromName) {
    this.mailFromName = mailFromName;
  }

  
  public  String getMailToAddr() {
    return mailToAddr;
  }

  
  public  void setMailToAddr(String mailToAddr) {
    this.mailToAddr = mailToAddr;
  }

  
  public  String getMailToName() {
    return mailToName;
  }

  
  public  void setMailToName(String mailToName) {
    this.mailToName = mailToName;
  }

}
