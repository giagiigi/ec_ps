package jp.co.sint.webshop.validation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 日付・時刻書式用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class DatetimeValidator implements Validator<Datetime> {

  private String format = "";

  private String message = "";

  public DatetimeValidator() {
  }

  public DatetimeValidator(String format, String message) {
    setFormat(format);
    setMessage(message);
  }

  /**
   * formatを取得します。
   * 
   * @return format
   */
  public String getFormat() {
    return format;
  }

  /**
   * messageを取得します。
   * 
   * @return message
   */
  public String getMessage() {
    if (StringUtil.hasValue(this.message)) {
      return this.message;
    } else {
      return Messages.getString("validation.DatetimeValidator.0");
    }
  }

  /**
   * formatを設定します。
   * 
   * @param format
   *          設定する format
   */
  public void setFormat(String format) {
    this.format = format;
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

  public void init(Datetime annotation) {
    setFormat(annotation.format());
    //setMessage(annotation.message());
  }

  public boolean isValid(Object value) {

    if (value == null) {
      return true;
    }
    if (value instanceof Date) {
      return true;
    }
    if (!(value instanceof String)) {
      return false;
    }
    String strValue = (String) value;
    if (StringUtil.isNullOrEmpty(strValue)) {
      return true;
    }
    boolean result = true;
    try {
      DateFormat df = new SimpleDateFormat(this.getFormat());
      df.setLenient(false);
      df.parse(strValue);

      // SimpleDateFormatは先頭だけ合ってればOKとするらしいので
      // 入力文字列長＝書式文字列長でない場合はエラー
      // 参考
      // http://java.sun.com/j2se/1.5.0/ja/docs/ja/api/java/text/DateFormat.html#parse(java.lang.String,%20java.text.ParsePosition)
      if (strValue.length() != this.getFormat().length()) {
        result = false;
      }

    } catch (Throwable t) {
      result = false;
    }
    return result;
  }

}
