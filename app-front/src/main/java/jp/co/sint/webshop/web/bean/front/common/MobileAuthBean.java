package jp.co.sint.webshop.web.bean.front.common;

import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * 手机验证Bean
 * 
 * @author
 */
public class MobileAuthBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 新手机号码 */
  @Required
  @Length(11)
  @MobileNumber
  @Metadata(name = "新手机")
  private String mobileNumber;

  /** 验证码 */
  @Required
  @Length(6)
  @Digit
  @Metadata(name = "验证码")
  private String authCode;

  /** 手机验证用户FLG */
  private boolean isValidateFlg = Boolean.FALSE;

  /** 旧手机 */
  @Length(11)
  @MobileNumber
  @Metadata(name = "旧手机")
  private String oldMobileNumber;

  // 注册会员FLG
  private boolean registerCustomerFlg = Boolean.FALSE;

  /** 用户语言 */
  private String languageCode;

  private String url;

  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @param url
   *          the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * @return the languageCode
   */
  public String getLanguageCode() {
    return languageCode;
  }

  /**
   * @param languageCode
   *          the languageCode to set
   */
  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  /**
   * 取得手机号码
   * 
   * @return
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  /**
   * 设定手机号码
   * 
   * @param mobileNumber
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  /**
   * 取得验证码
   * 
   * @return
   */
  public String getAuthCode() {
    return authCode;
  }

  /**
   * 设定验证码
   * 
   * @param authCode
   */
  public void setAuthCode(String authCode) {
    this.authCode = authCode;
  }

  /**
   * 取得手机验证用户FLG
   * 
   * @return
   */
  public boolean isValidateFlg() {
    return isValidateFlg;
  }

  /**
   * 设定手机验证用户FLG
   * 
   * @param isValidateFlg
   */
  public void setValidateFlg(boolean isValidateFlg) {
    this.isValidateFlg = isValidateFlg;
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
  }

  /**
   * 取得旧手机
   * 
   * @return
   */
  public String getOldMobileNumber() {
    return oldMobileNumber;
  }

  /**
   * 设定旧手机
   * 
   * @param oldMobileNumber
   */
  public void setOldMobileNumber(String oldMobileNumber) {
    this.oldMobileNumber = oldMobileNumber;
  }

  /**
   * 取得注册会员FLG
   * 
   * @return
   */
  public boolean isRegisterCustomerFlg() {
    return registerCustomerFlg;
  }

  /**
   * 设定注册会员FLG
   * 
   * @param registerCustomerFlg
   */
  public void setRegisterCustomerFlg(boolean registerCustomerFlg) {
    this.registerCustomerFlg = registerCustomerFlg;
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.action.front.common.mobileAuthBean.0");
  }

}
