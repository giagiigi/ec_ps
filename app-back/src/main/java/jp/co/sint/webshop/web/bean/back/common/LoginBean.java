package jp.co.sint.webshop.web.bean.back.common;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1010110:ログインのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class LoginBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** ショップコード */
  @Length(16)
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** ログインID */
  @Required
  @Length(20)
  @Metadata(name = "管理者ID", order = 2)
  private String loginId;

  /** パスワード */
  @Required
  @Length(50)
  @Metadata(name = "パスワード", order = 3)
  private String password;

  /** 運用モード true:ショップコード入力あり false:ショップコード入力なし */
  private Boolean operatingModeIsMall = false;

  /**
   * loginIdを取得します。
   * 
   * @return loginId
   */
  public String getLoginId() {
    return loginId;
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
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * loginIdを設定します。
   * 
   * @param inputLoginId
   *          ログインID
   */
  public void setLoginId(String inputLoginId) {
    this.loginId = inputLoginId;
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
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * operatingModeIsMallを取得します。
   * 
   * @return operatingModeIsMall
   */
  public Boolean getOperatingModeIsMall() {
    return operatingModeIsMall;
  }

  /**
   * operatingModeIsMallを設定します。
   * 
   * @param operatingModeIsMall
   *          operatingModeIsMall
   */
  public void setOperatingModeIsMall(Boolean operatingModeIsMall) {
    this.operatingModeIsMall = operatingModeIsMall;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    this.setShopCode(reqparam.get("shopCode"));
    this.setLoginId(reqparam.get("loginId"));
    this.setPassword(reqparam.get("password"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1010110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.common.LoginBean.0");
  }

}
