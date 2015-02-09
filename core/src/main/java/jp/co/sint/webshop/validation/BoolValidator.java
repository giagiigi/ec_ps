package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.text.Messages;

/**
 * フラグ値用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 *
 */
public class BoolValidator implements Validator<Bool> {

  private static final String REGEX_PATTERN_BOOL = "[01]?";

  public String getMessage() {
    //modify by V10-CH 170 start
    return Messages.getString("validation.BoolValidator.0");
    //modify by V10-CH 170 end    
  }

  public boolean isValid(Object value) {
    if (value == null) {
      return true;
    }
    if (value instanceof Number) {
      Number numValue = (Number) value;
      return ValidatorUtil.inRange(numValue.longValue(), 0L, 1L);
    }

    return ValidatorUtil.patternMatches(REGEX_PATTERN_BOOL, value);
  }

  public void init(Bool annotation) {
  }
}
