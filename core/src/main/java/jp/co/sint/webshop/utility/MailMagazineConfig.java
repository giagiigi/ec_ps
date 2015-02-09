package jp.co.sint.webshop.utility;

import java.io.Serializable;

/**
 * メールマガジン登録メール情報
 * 
 * @author System Integrator Corp.
 */
public class MailMagazineConfig implements Serializable {

  /**
   * serial version UID
   */
  private static final long serialVersionUID = 1L;

  /** 送信者メールアドレス */
  private String fromInfo;

  /** メールマガジン配信登録用メールタイトル */
  private String registerMailMagazineTitle;

  /** メールマガジン配信登録用メールタイトル */
  private String cancelMailMagazineTitle;

  /** 配信中メールマガジン確認用メールタイトル */
  private String confirmMailMagazineTitle;

  /** メールマガジン配信登録用メール本文 */
  private String registerMailMagazineText;

  /** メールマガジン配信停止用メール本文 */
  private String cancelMailMagazineText;

  /** 配信中メールマガジン確認用メール本文 */
  private String confirmMailMagazineText;

  /** 配信中メールマガジン未存在用メール本文 */
  private String noExistMailMagazineText;

  /** 署名 */
  private String sign;

  /**
   * noExistMailMagazineTextを取得します。
   * 
   * @return noExistMailMagazineText
   */
  public String getNoExistMailMagazineText() {
    return StringUtil.coalesce(CodeUtil.getEntry("noExistMailMagazineText"), noExistMailMagazineText);
  }

  /**
   * noExistMailMagazineTextを設定します。
   * 
   * @param noExistMailMagazineText
   *          noExistMailMagazineText
   */
  public void setNoExistMailMagazineText(String noExistMailMagazineText) {
    this.noExistMailMagazineText = noExistMailMagazineText;
  }

  /**
   * cancelMailMagazineTextを取得します。
   * 
   * @return cancelMailMagazineText
   */
  public String getCancelMailMagazineText() {
    return StringUtil.coalesce(CodeUtil.getEntry("cancelMailMagazineText"), cancelMailMagazineText);
  }

  /**
   * cancelMailMagazineTextを設定します。
   * 
   * @param cancelMailMagazineText
   *          cancelMailMagazineText
   */
  public void setCancelMailMagazineText(String cancelMailMagazineText) {
    this.cancelMailMagazineText = cancelMailMagazineText;
  }

  /**
   * cancelMailMagazineTitleを取得します。
   * 
   * @return cancelMailMagazineTitle
   */
  public String getCancelMailMagazineTitle() {
    return StringUtil.coalesce(CodeUtil.getEntry("cancelMailMagazineTitle"), cancelMailMagazineTitle);
  }

  /**
   * cancelMailMagazineTitleを設定します。
   * 
   * @param cancelMailMagazineTitle
   *          cancelMailMagazineTitle
   */
  public void setCancelMailMagazineTitle(String cancelMailMagazineTitle) {
    this.cancelMailMagazineTitle = cancelMailMagazineTitle;
  }

  /**
   * registerMailMagazineTextを取得します。
   * 
   * @return registerMailMagazineText
   */
  public String getRegisterMailMagazineText() {
    return StringUtil.coalesce(CodeUtil.getEntry("registerMailMagazineText"), registerMailMagazineText);
  }

  /**
   * registerMailMagazineTextを設定します。
   * 
   * @param registerMailMagazineText
   *          registerMailMagazineText
   */
  public void setRegisterMailMagazineText(String registerMailMagazineText) {
    this.registerMailMagazineText = registerMailMagazineText;
  }

  /**
   * registerMailMagazineTitleを取得します。
   * 
   * @return registerMailMagazineTitle
   */
  public String getRegisterMailMagazineTitle() {
    return StringUtil.coalesce(CodeUtil.getEntry("registerMailMagazineTitle"), registerMailMagazineTitle);
  }

  /**
   * registerMailMagazineTitleを設定します。
   * 
   * @param registerMailMagazineTitle
   *          registerMailMagazineTitle
   */
  public void setRegisterMailMagazineTitle(String registerMailMagazineTitle) {
    this.registerMailMagazineTitle = registerMailMagazineTitle;
  }

  /**
   * confirmMailMagazineTitleを取得します。
   * 
   * @return confirmMailMagazineTitle
   */
  public String getConfirmMailMagazineTitle() {
    return StringUtil.coalesce(CodeUtil.getEntry("confirmMailMagazineTitle"), confirmMailMagazineTitle);
  }

  /**
   * confirmMailMagazineTitleを設定します。
   * 
   * @param confirmMailMagazineTitle
   *          confirmMailMagazineTitle
   */
  public void setConfirmMailMagazineTitle(String confirmMailMagazineTitle) {
    this.confirmMailMagazineTitle = confirmMailMagazineTitle;
  }

  /**
   * fromInfoを取得します。
   * 
   * @return fromInfo
   */
  public String getFromInfo() {
    return fromInfo;
  }

  /**
   * fromInfoを設定します。
   * 
   * @param fromInfo
   *          fromInfo
   */
  public void setFromInfo(String fromInfo) {
    this.fromInfo = fromInfo;
  }

  /**
   * signを取得します。
   * 
   * @return sign
   */
  public String getSign() {
    return StringUtil.coalesce(CodeUtil.getEntry("sign"), sign);
  }

  /**
   * signを設定します。
   * 
   * @param sign
   *          sign
   */
  public void setSign(String sign) {
    this.sign = sign;
  }

  /**
   * confirmMailMagazineTextを取得します。
   * 
   * @return confirmMailMagazineText
   */
  public String getConfirmMailMagazineText() {
    return StringUtil.coalesce(CodeUtil.getEntry("confirmMailMagazineText"), confirmMailMagazineText);
  }

  /**
   * confirmMailMagazineTextを設定します。
   * 
   * @param confirmMailMagazineText
   *          confirmMailMagazineText
   */
  public void setConfirmMailMagazineText(String confirmMailMagazineText) {
    this.confirmMailMagazineText = confirmMailMagazineText;
  }

}
