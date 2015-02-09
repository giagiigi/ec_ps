package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.JanCode;
import jp.co.sint.webshop.text.Messages;

/**
 * JANコード用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class JanCodeValidator implements Validator<JanCode> {

  private static final String  REGEX_PATTERN_JAN_CODE = "([0-9]{8}|[0-9]{13}|)";

  public String getMessage() {
    return Messages.getString("validation.JanCodeValidator.0");
  }

  public boolean isValid(Object value) {
    return ValidatorUtil.patternMatches(REGEX_PATTERN_JAN_CODE, value);
  }

  public void init(JanCode annotation) {
  }
}
