package jp.co.sint.webshop.sms;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

/**
 * メール送信クラス
 * 
 * @author System Integrator Corp.
 */
public interface SmsSender {

  /**
   * メールを送信する。 パラメータsmsInfoは送信するメールデータ。
   * 
   * @param smsInfo
   * @throws UnsupportedEncodingException
   * @throws MessagingException
   */
  boolean sendSms(String[] mobiles, String msg) throws UnsupportedEncodingException, MessagingException;

}
