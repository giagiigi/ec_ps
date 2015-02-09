package jp.co.sint.webshop.mail.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import jp.co.sint.webshop.mail.MailInfo;
import jp.co.sint.webshop.mail.MailSender;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

/**
 * メール送信クラス
 * 
 * @author System Integrator Corp.
 */
public class MailSenderImpl implements MailSender {

  // キャラセット設定
  public static final String CHAR_SET = "UTF-8";

  private static final String CRLF = "\r\n";

  private Properties properties = new Properties();

  private Session session;

  private boolean escapeHalfWidthKana = true; // 10.1.2 10108 追加
  
  public void sendMail(MailInfo mailInfo) throws UnsupportedEncodingException, MessagingException {

    if (session == null) {
      session = Session.getDefaultInstance(getProperties(), null);
    }

    MimeMessage mimeMessage = new MimeMessage(session);

    // 送信元メールアドレスと送信者名を設定
    // 送信者名
    String fromName = mailInfo.getFromName();

    if (StringUtil.isNullOrEmpty(fromName)) {
      // 送信者名が空だったらセットしない
      mimeMessage.setFrom(new InternetAddress(mailInfo.getFromAddress()));
    } else {
      // 10.1.2 10096, 10108 修正 ここから
      // String name = changeCode(convertSjis(mailInfo.getFromName()));
      String name = changeCode(encodeHeader(escapeHalfWidthKana(mailInfo.getFromName())));
      // 10.1.2 10096, 10108 修正 ここまで
      mimeMessage.setFrom(new InternetAddress(mailInfo.getFromAddress(), name, CHAR_SET));
    }

    // 送信先メールアドレスの設定
    mimeMessage.setRecipients(Message.RecipientType.TO, createAddressArray(mailInfo.getToList()));
    // CCメールアドレスの設定
    mimeMessage.setRecipients(Message.RecipientType.CC, createAddressArray(mailInfo.getCcList()));
    // BCCメールアドレスの設定
    mimeMessage.setRecipients(Message.RecipientType.BCC, createAddressArray(mailInfo.getBccList()));
    // 送信メールのタイトルを設定
    // 10.1.2 10096, 10108 修正 ここから
    // mimeMessage.setSubject(changeCode(convertSjis(mailInfo.getSubject())), CHAR_SET);
    mimeMessage.setSubject(changeCode(encodeHeader(escapeHalfWidthKana(mailInfo.getSubject()))), CHAR_SET);
    // 10.1.2 10096, 10108 修正 ここまで

    // ボディパートの作成

    // 添付ファイルリストの作成
    List<File> fileList = mailInfo.getFileList();

    // 添付ファイルが存在する場合、ファイルの個数分ボディパートを作成
    if (fileList.size() != 0) {

      // 複数のボディを格納するマルチパートオブジェクトの作成
      Multipart multiPart = new MimeMultipart();

      // テキスト部の作成
      MimeBodyPart mimeBodyPartText = new MimeBodyPart();
      // 10.1.2 10108 修正 ここから
      // mimeBodyPartText.setContent(changeCode(convertSjis(mailInfo.getText())),
      // mailInfo.getContentType() + ";charset=" + CHAR_SET);
//    modify by V10-CH start
//      String mailText = escapeHalfWidthKana(mailInfo.getText());
//      mimeBodyPartText.setContent(changeCode(convertSjis(mailText)), mailInfo.getContentType() + ";charset=" + CHAR_SET);
      String mailText = mailInfo.getText();
//      CharacterConverter converter = DIContainer.getCharacterConverter();
//      mailText = converter.convertString(ConvertMode.UTF_8.convertFromUnicode(mailText));
      mimeBodyPartText.setContent(mailText, mailInfo.getContentType() + ";charset=" + CHAR_SET);
//    modify by V10-CH END
      // 10.1.2 10108 修正 ここまで

      // ボディパートの追加
      multiPart.addBodyPart(mimeBodyPartText);

      // ボディパートの作成
      for (File file : fileList) {
        MimeBodyPart bodyPartFile = new MimeBodyPart();
        FileDataSource fileDataSource = new FileDataSource(file);

        bodyPartFile.setDataHandler(new DataHandler(fileDataSource));
        bodyPartFile.setFileName(MimeUtility.encodeWord(fileDataSource.getName()));

        multiPart.addBodyPart(bodyPartFile);
      }

      mimeMessage.setContent(multiPart);
    } else {

      // 添付ファイルがなかった場合、マルチパートオブジェクトを使わずに本文をセット
      // 10.1.2 10108 修正 ここから
      // String text = changeCode(convertSjis(mailInfo.getText()));
//    modify by V10-CH start
      //String text = changeCode(convertSjis(escapeHalfWidthKana(mailInfo.getText())));
      String text = mailInfo.getText();
//    modify by V10-CH end
      // 10.1.2 10108 修正 ここまで
      // 参考URL→http://www.ietf.org/rfc/rfc2821.txt
      if (!text.endsWith(CRLF)) {
        text += CRLF;
      }
      mimeMessage.setText(text, CHAR_SET);
      // メールの形式を指定
      // 10.1.4 10123 修正 ここから
//      mimeMessage.setHeader("Content-Type", mailInfo.getContentType());
      mimeMessage.setHeader("Content-Type", mailInfo.getContentType() + ";charset=" + CHAR_SET);
      // 10.1.4 10123 修正 ここまで
    }

    // 送信日時を設定
    mimeMessage.setSentDate(mailInfo.getSendDate());

    // 送信
    Transport.send(mimeMessage);

  }

  // 10.1.2 10096 追加 ここから
  /**
   * <b>encodeHeader</b> メールヘッダ用にエンコードします。
   * 
   * @param lHeader
   *          ヘッダデータ
   * @return エンコード後のヘッダデータ
   * @throws なし
   */
  private static String encodeHeader(String lHeader) {
    String ret = null;
    if (lHeader != null && lHeader.length() > 0) {
      String tmp = null;
      List<String> buff = new ArrayList<String>();
      // ヘッダ文字列を13文字で分割（MIMEエンコードの分割対応）
      if (lHeader.length() > 10) {
        for (int i = 0; i < lHeader.length();) {
          int endpoint = i + 10;
          if (endpoint > lHeader.length()) {
            endpoint = lHeader.length();
          }
          buff.add(lHeader.substring(i, endpoint));
          i = endpoint;
        }
      } else {
        buff.add(lHeader);
      }
      ret = "";
      for (Iterator<String> iter = buff.iterator(); iter.hasNext();) {
        // SJIS-JIS変換
        //tmp = convertSjis(iter.next());
        tmp = iter.next();
        if (tmp != null && tmp.length() > 0 && tmp.indexOf("\u001B") != -1) {
          // Base64エンコード
          try {
            while (tmp.indexOf("\u001B") != -1) {
              int start = tmp.indexOf("\u001B");
              int end = tmp.indexOf("\u001B", start + 1) + 3;
              if (end == -1) {
                end = tmp.length();
              }
              String mimeStr = MimeUtility.encodeText(tmp.substring(start, end), "ISO-2022-JP", "B");
              tmp = tmp.substring(0, start) + mimeStr + tmp.substring(end);
            }
          } catch (Exception ex) {
            Logger.getLogger("errorlog").debug(ex);
          }
        }
        ret = ret + tmp;
      }
      ret = ret.replace("?==?", "?= =?");
    }
    return (ret);
  }
  // 10.1.2 10096 追加 ここまで

  /**
   * 与えられた文字列の特定文字をコードに変換して返す
   * 
   * @param str
   *          文字列
   * @return 文字コード変換後の文字列
   */
  private static String changeCode(String str) {
    return StringUtil.convertForEmailString(str);
  }

  /**
   * AddressInfoのリストをInternetAddressの配列に変換します。
   * 
   * @param arrayInfo
   *          アドレスと名前がセットになった値のList
   * @return アドレスと名前がセットされたInternetAddress型の配列
   * @throws UnsupportedEncodingException
   */
  // 10.1.2 10108 修正 ここから
  //private static InternetAddress[] createAddressArray(List<MailInfo.AddressInfo> arrayInfo) throws UnsupportedEncodingException {
  private InternetAddress[] createAddressArray(List<MailInfo.AddressInfo> arrayInfo) throws UnsupportedEncodingException {
  // 10.1.2 10108 修正 ここまで
    int addressCount = arrayInfo.size();
    InternetAddress[] addressInfo = new InternetAddress[addressCount];

    for (int i = 0; i < addressCount; i++) {
      String tmpAddress = arrayInfo.get(i).getAddress();
      String tmpName = arrayInfo.get(i).getName();
      // 10.1.2 10096, 10108 修正 ここから
      // tmpName = changeCode(convertSjis(tmpName));
      tmpName = changeCode(encodeHeader(escapeHalfWidthKana(tmpName)));
      // 10.1.2 10096, 10108 修正 ここまで

      addressInfo[i] = new InternetAddress(tmpAddress, tmpName, CHAR_SET);
    }

    return (addressInfo);
  }

  /**
   * SMTPサーバのIPアドレスを取得します。
   * 
   * @return smtpIpAddress
   */
  public String getSmtpIpAddress() {
    return this.getProperties().getProperty("mail.smtp.host");
  }

  /**
   * SMTPサーバのIPアドレスを設定します。
   * 
   * @param smtpIpAddress
   *          設定する smtpIpAddress
   */
  public void setSmtpIpAddress(String smtpIpAddress) {
    if (smtpIpAddress != null) {
      this.getProperties().setProperty("mail.smtp.host", smtpIpAddress);
      this.getProperties().setProperty("mail.host", smtpIpAddress);
    }
  }

  /**
   * メール送信に必要な設定を取得します。
   * 
   * @return 設定情報が含まれたPropertiesのインスタンス
   */
  public Properties getProperties() {
    return properties;
  }

  /**
   * メール送信に必要な設定を設定します
   * 
   * @param properties
   *          設定情報が含まれたPropertiesのインスタンス
   */
  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  /**
   * メール送信セッションを取得します。
   * 
   * @return メール送信セッション
   */
  public Session getSession() {
    return session;
  }

  /**
   * メールセッションを設定します。
   * 
   * @param session
   *          メール送信セッション
   */
  public void setSession(Session session) {
    this.session = session;
  }

//  private static String convertSjis(String target) {
//    // JIS ENCODING
//    // START JIS (to JIS) ESC $ B
//    // END JIS (to ASCII) ESC ( B
//    String result = null;
//    char[] sjisArray = null;
//    try {
//      sjisArray = byteToChar(target.getBytes("MS932"));
//    } catch (UnsupportedEncodingException ex) {
//      Logger.getLogger(MailSenderImpl.class).debug(ex);
//    }
//    if (sjisArray != null && sjisArray.length > 0) {
//      boolean isSjis = false;
//      List<Character> jisStrings = new ArrayList<Character>();
//      for (int i = 0; i < sjisArray.length; i++) {
//        if (i == (sjisArray.length - 1)) { // 配列最後の文字の処理
//          if (!isSjis(sjisArray[i], (char) 0, true)) { // SJISではない（SJISの場合はスキップ）
//            if (isSjis) {
//              jisStrings.add(Character.valueOf((char) 0x1b));
//              jisStrings.add(Character.valueOf((char) '('));
//              jisStrings.add(Character.valueOf((char) 'B'));
//              isSjis = false;
//            }
//            jisStrings.add(Character.valueOf(sjisArray[i]));
//          }
//        } else if (isSjis(sjisArray[i], sjisArray[i + 1], false)) { // もしSJISだったら
//          if (!isSjis) {
//            jisStrings.add(Character.valueOf((char) 0x1b));
//            jisStrings.add(Character.valueOf((char) '$'));
//            jisStrings.add(Character.valueOf((char) 'B'));
//            isSjis = true;
//          }
//          // SJIS to JIS
//          int upChar = (int) sjisArray[i];
//          int lwChar = (int) sjisArray[i + 1];
//          int upOffset = 176;
//          int lwOffset = 126;
//          int ajust = 0;
//          if (lwChar < 159) { // アジャストの変更
//            ajust = 1;
//          }
//          if (upChar < 160) { // オフセットの変更
//            upOffset = 112;
//          }
//          if (ajust == 1) { // オフセットの変更
//            if (lwChar > 127) {
//              lwOffset = 32;
//            } else {
//              lwOffset = 31;
//            }
//          }
//          int jis1 = ((upChar - upOffset) << 1) - ajust;
//          int jis2 = lwChar - lwOffset;
//          char char1 = (char) jis1;
//          char char2 = (char) jis2;
//          jisStrings.add(Character.valueOf(char1));
//          jisStrings.add(Character.valueOf(char2));
//          i++;
//        } else { // SJISではない場合
//          if (isSjis) {
//            jisStrings.add(Character.valueOf((char) 0x1b));
//            jisStrings.add(Character.valueOf((char) '('));
//            jisStrings.add(Character.valueOf((char) 'B'));
//            isSjis = false;
//          }
//          jisStrings.add(Character.valueOf(sjisArray[i]));
//        }
//      }
//      if (isSjis) {
//        jisStrings.add(Character.valueOf((char) 0x1b));
//        jisStrings.add(Character.valueOf((char) '('));
//        jisStrings.add(Character.valueOf((char) 'B'));
//      }
//      result = charListToString(jisStrings);
//    }
//    return (result);
//  }

//  /**
//   * <b>byteToChar</b> byte配列をchar配列に変換
//   * 
//   * @param lByte
//   *          元データ
//   * @return 変換後のchar配列
//   * @throws なし
//   */
//  private static char[] byteToChar(byte[] lByte) {
//    char[] result = null;
//    if (lByte != null && lByte.length > 0) {
//      result = new char[lByte.length];
//      for (int i = 0; i < lByte.length; i++) {
//        result[i] = (char) lByte[i];
//        result[i] = (char) (result[i] & 0x00ff);
//      }
//    }
//    return (result);
//  }
//
//  /**
//   * ユニコード文字列をMS932化後にJIS化します。
//   * 
//   * @param hi
//   *          上位バイト
//   * @param low
//   *          下位バイト
//   * @param only1st
//   *          true:上位バイトのみ評価、false:上位、下位バイトを評価
//   * @return true:Shift-JISコードである false:Shift-JISコードではない
//   * @throws なし
//   */
//  private static boolean isSjis(char hi, char low, boolean only1st) {
//    boolean ret = false;
//    if ((hi >= 0x81 && hi <= 0x9f) || (hi >= 0xe0 && hi <= 0xef)) {
//      if (only1st) {
//        ret = true;
//      } else if ((low >= 0x40 && low <= 0x7e) || (low >= 0x80 && low <= 0xfc)) {
//        ret = true;
//      }
//    }
//    return (ret);
//  }
//
//  /**
//   * charのリストをStringに変換します。
//   * 
//   * @param strings
//   *          元データ
//   * @return 変換後の文字データ
//   * @throws なし
//   */
//  private static String charListToString(List<Character> strings) {
//    String result = null;
//    if (strings != null && strings.size() > 0) {
//      StringBuffer buffer = new StringBuffer();
//      for (Character c : strings) {
//        buffer.append(c.charValue());
//      }
//      result = buffer.toString();
//    }
//    return (result);
//  }

  // 10.1.2 10108 追加 ここから
  private String escapeHalfWidthKana(String target) {
    if (escapeHalfWidthKana) {
      return StringUtil.toFullWidth(target);
    } else {
      return target;
    }
  }
  // 10.1.2 10108 追加 ここまで
}
