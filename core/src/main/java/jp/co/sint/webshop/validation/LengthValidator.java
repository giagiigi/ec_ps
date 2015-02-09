package jp.co.sint.webshop.validation;

import java.text.MessageFormat;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.text.Messages;

/**
 * 文字列長を規定するバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class LengthValidator implements Validator<Length> {

  private int length;

  public LengthValidator() {
  }

  public LengthValidator(int length) {
    setLength(length);
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public void init(Length annotation) {
    setLength(annotation.value());
  }

  public boolean isValid(Object value) {
    if (value == null) {
      return true;
    }
    if (value instanceof Number) {
      Number numValue = (Number) value;
      int len = Long.toString(Math.abs(numValue.longValue()), 10).length();
      return len <= length;
    }

    if (!(value instanceof String)) {
      return false;
    }
    return ((String) value).length() <= length;
  }

  public String getMessage() {
    //modify by V10-CH 170 start
    return MessageFormat.format(Messages.getString("validation.LengthValidator.0"), length);
    //modify by V10-CH 170 end
  }

}
