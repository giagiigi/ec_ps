package jp.co.sint.webshop.validation;

import java.io.UnsupportedEncodingException;

import jp.co.sint.webshop.data.attribute.Fullwidth;
import jp.co.sint.webshop.text.Messages;

public class FullwidthValidator implements Validator<Fullwidth> {

  public String getMessage() {
    //modify by V10-CH 170 start
    return Messages.getString("validation.FullwidthValidator.0");
    //modify by V10-CH 170 end
  }

  public void init(Fullwidth annotation) {
  }

  public boolean isValid(Object value) {
    if (value == null) {
      return true;
    }

    if (!(value instanceof String)) {
      return false;
    }
    String str = (String) value;
    boolean result = false;
    try {
      result = (str.length() * 2 == str.getBytes("Windows-31J").length);
    } catch (UnsupportedEncodingException e) {
      result = false;
    } catch (RuntimeException e) {
      result = false;
    }
    return result;

  }

}
