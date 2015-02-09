package jp.co.sint.webshop.configure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MobileDomain implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<String> mobileDomain = new ArrayList<String>();

  /**
   * mobileDomainを取得します。
   * 
   * @return mobileDomain
   */
  public List<String> getMobileDomain() {
    return mobileDomain;
  }

  /**
   * mobileDomainを設定します。
   * 
   * @param mobileDomain
   *          設定する mobileDomain
   */
  public void setMobileDomain(List<String> mobileDomain) {
    this.mobileDomain = mobileDomain;
  }

  /**
   * 指定されたメールアドレスからPCメールか携帯メールかを判断します。
   * 
   * @param mailAddress
   *          メールアドレス
   * @return 0：PC、1：携帯
   */
  public int getClientMailType(String mailAddress) {
    int mailType = 0;
    boolean result = false;

    for (String regex : mobileDomain) {
      regex = ".*@" + regex;
      result |= Pattern.matches(regex, mailAddress);
    }
    if (result) {
      mailType = 1;
    }
    return mailType;
  }
  //Add by V10-CH start
  public int getClientSmsType(String smsAddress){
    int smsType = 0;
    boolean result = false;
     
    for(String regex : mobileDomain ){
      regex = ".*@" + regex;
      result |= Pattern.matches(regex, smsAddress);
    }
    if(result){
      smsType = 1;
    }
    return smsType;
  }
  //Add by V10-CH end
  
}
