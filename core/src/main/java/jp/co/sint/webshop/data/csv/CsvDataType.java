package jp.co.sint.webshop.data.csv;

import java.sql.Timestamp;
import java.util.Date;

import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidationResult;

/**
 * Csvのデータ型を定義する列挙体です。
 * 
 * @author System Integrator Corp.
 */
public enum CsvDataType {

  /** 文字列型を現します。 */
  STRING,

  /** 数値型を現します。 */
  NUMBER,

  /** BIGDECIMAL型を現します。 */
  BIGDECIMAL,

  /** 日付型(yyyy/MM/dd)を現します。 */
  DATE(DateUtil.DEFAULT_DATE_FORMAT),
  
  /** 日付型(yy/MM/dd)を現します。 */
  SHORT_DATE(DateUtil.DEFAULT_SHORT_DATE_FORMAT),

  /** 日付時刻型(yyyy/MM/dd HH:mm:ss)を現します。 */
  DATETIME(DateUtil.DEFAULT_DATETIME_FORMAT);

  private String format;

  private CsvDataType() {
    this.format = null;
  }

  private CsvDataType(String format) {
    this.format = format;
  }

  public String getFormat() {
    return this.format;
  }

  public String format(Object value) {
    String result = StringUtil.EMPTY;
    if (value == null) {
      result = StringUtil.EMPTY;
    } else if (value instanceof Timestamp) {
      Timestamp timestamp = (Timestamp) value;
      Date date = new Date(timestamp.getTime());
      if (StringUtil.hasValue(getFormat())) {
        result = DateUtil.toDateTimeString(date, getFormat());
      } else {
        result = DateUtil.toDateTimeString(date);
      }
    } else {
      result = value.toString();
    }
    return result;
  }

  public ValidationResult validate(String value) {
    ValidationResult result = new ValidationResult(true);
    if (StringUtil.hasValue(value)) {
      switch (this) {
        case NUMBER:
          if (!NumUtil.isNum(value)) {
            result = new ValidationResult(null, null, Message.get(CsvMessage.DIGIT));
          }
          break;
        case BIGDECIMAL:
          if (!NumUtil.isDecimal(value)) {
            result = new ValidationResult(null, null, Message.get(CsvMessage.DIGIT));
          }
          break;
        case DATE:
          if (DateUtil.parseString(value, DateUtil.DEFAULT_DATE_FORMAT) == null) {
            result = new ValidationResult(null, null, Message.get(CsvMessage.DATE));
          }
          break;
        case SHORT_DATE:
          if (DateUtil.parseString(20+value, DateUtil.DEFAULT_DATE_FORMAT) == null) {
            result = new ValidationResult(null, null, Message.get(CsvMessage.DATE));
          }
          break;
        case DATETIME:
          if (DateUtil.parseString(value, DateUtil.DEFAULT_DATETIME_FORMAT) == null) {
            result = new ValidationResult(null, null, Message.get(CsvMessage.DATETIME));
          }
          break;
        default:
          break;
      }
    }

    return result;
  }

  public Object parse(String value) {
    Object result = null;
    if (StringUtil.hasValue(value)) {
      switch (this) {
        case STRING:
          result = value;
          break;
        case NUMBER:
          result = NumUtil.toLong(value, null);
          break;
        case BIGDECIMAL:
          result = NumUtil.parse(value);
          break;
        case SHORT_DATE:
          result = DateUtil.parseString(20+value, DateUtil.DEFAULT_DATE_FORMAT);
          break;
        case DATE:
        case DATETIME:
          result = DateUtil.parseString(value, getFormat());
          break;
        default:
          break;
      }

      if (result == null) {
        throw new IllegalArgumentException();
      }
    }

    return result;
  }
}
