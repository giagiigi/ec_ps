package jp.co.sint.webshop.mail;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;



/**
 * メール送信クラス
 * @author System Integrator Corp.
 *
 */
public interface MailSender {

  /**
   * メールを送信する。
   * パラメータmailInfoは送信するメールデータ。
   * @param mailInfo
   * @throws UnsupportedEncodingException
   * @throws MessagingException
   */
  void sendMail(MailInfo mailInfo) throws UnsupportedEncodingException, MessagingException;

}
