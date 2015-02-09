package jp.co.sint.webshop.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class PasswordPolicy implements Serializable {

  private static final long serialVersionUID = 1L;

  private int minLength;

  private String message;

  private List<String> patterns = new ArrayList<String>();

  private String messageKey;

  /**
   * messageKeyを取得します。
   * 
   * @return messageKey messageKey
   */
  public String getMessageKey() {
    return messageKey;
  }

  /**
   * messageKeyを設定します。
   * 
   * @param messageKey
   *          messageKey
   */
  public void setMessageKey(String messageKey) {
    this.messageKey = messageKey;
  }

  /**
   * minLengthを返します。
   * 
   * @return the minLength
   */
  public int getMinLength() {
    return minLength;
  }

  /**
   * minLengthを設定します。
   * 
   * @param minLength
   *          設定する minLength
   */
  public void setMinLength(int minLength) {
    this.minLength = minLength;
  }

  /**
   * messageを返します。
   * 
   * @return the message
   */
  public String getMessage() {
    return StringUtil.coalesce(CodeUtil.getEntry(getMessageKey()), message);
  }

  /**
   * messageを設定します。
   * 
   * @param message
   *          設定する message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * patternsを返します。
   * 
   * @return the patterns
   */
  public List<String> getPatterns() {
    return patterns;
  }

  /**
   * patternsを設定します。
   * 
   * @param patterns
   *          設定する patterns
   */
  public void setPatterns(List<String> patterns) {
    this.patterns = patterns;
  }

  /**
   * 指定された文字列がパスワードポリシーに準拠しているかどうかを返します。<br>
   * ・パスワード入力最低必要桁数以上であること。<br>
   * ・半角の英数字と、「-」「_」「.」で構成されていること。
   * 
   * @param input
   *          入力文字列
   * @return パスワードポリシーに準拠している場合、trueを返します。<br>
   *         パスワードポリシーに準拠していない場合、falseを返します。
   */
  public boolean isValidPassword(String input) {
    boolean result = true;
    Logger logger = Logger.getLogger(this.getClass());
    result &= input.length() >= getMinLength();
    for (String regex : patterns) {
      result &= Pattern.matches(regex, input);
      logger.debug(result);
    }
    return result;
  }
}
