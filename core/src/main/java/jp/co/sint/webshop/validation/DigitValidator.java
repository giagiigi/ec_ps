package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.text.Messages;

/**
 * 数値用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class DigitValidator implements Validator<Digit> {

  // 10.1.1 10013 修正 ここから
  // private static final String REGEX_PATTERN_DIGIT_WITH_NEGATIVE = "^(|-)[0-9]*";
  private static final String REGEX_PATTERN_DIGIT_WITH_NEGATIVE = "^[0-9]*|-[0-9]+";
  // 10.1.1 10013 修正 ここまで

  private static final String REGEX_PATTERN_DIGIT_POSITIVE_ONLY = "^[0-9]*";

  private boolean allowNegative;

  public String getMessage() {
    //modify by V10-CH 170 start
    return Messages.getString("validation.DigitValidator.0");
    //modify by V10-CH 170 end
  }

  public void init(Digit annotation) {
    allowNegative = annotation.allowNegative();
  }

  public boolean isValid(Object value) {
    if (value == null) {
      return true;
    }
    if (value instanceof Number) {
      if (allowNegative) {
        return true;
      } else {
        return ((Number) value).longValue() >= 0;
      }
    } else {
      String pattern = REGEX_PATTERN_DIGIT_WITH_NEGATIVE;
      if (!allowNegative) {
        pattern = REGEX_PATTERN_DIGIT_POSITIVE_ONLY;
      }
      return ValidatorUtil.patternMatches(pattern, value);
    }
  }

}
