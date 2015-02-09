package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.text.Messages;

/**
 * 必須チェック用のValidatorです。
 * 
 * @author System Integrator Corp.
 */
public class RequiredValidator implements Validator<Required> {

  public void init(Required annotation) {
  }

  public boolean isValid(Object value) {
    if (value == null) {
      return false;
    } else if (value instanceof String) {
      return ((String) value).length() > 0;
    } else {
      return true;
    }
  }

  public String getMessage() {
    //modify by V10-CH 170 start
    return Messages.getString("validation.RequiredValidator.0");
    //modify by V10-CH 170 end
  }

}
