package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.text.Messages;

/**
 * 数量用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class QuantityValidator implements Validator<Quantity> {

  // 10.1.2 10076 修正 ここから
  // private static final String REGEX_PATTERN_DIGIT = "^(|-)[0-9]*";
  private static final String REGEX_PATTERN_DIGIT = "^[0-9]*|-[0-9]+";
  // 10.1.2 10076 修正 ここまで

  public String getMessage() {
    //modify by V10-CH 170 start
    return Messages.getString("validation.QuantityValidator.0");
    //modify by V10-CH 170 end
  }

  public boolean isValid(Object value) {
    if (value == null || value instanceof Number) {
      return true;
    }
    return ValidatorUtil.patternMatches(REGEX_PATTERN_DIGIT, value);
  }

  public void init(Quantity annotation) {
  }

}
