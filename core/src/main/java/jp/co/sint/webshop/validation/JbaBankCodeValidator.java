package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.JbaBankCode;
import jp.co.sint.webshop.text.Messages;

/**
 * JANコード用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class JbaBankCodeValidator implements Validator<JbaBankCode> {

  private static final String  REGEX_PATTERN_JBA_BANK_CODE = "[0-9]{4}";

  public String getMessage() {
    return Messages.getString("validation.JbaBankCodeValidator.0");
  }

  public boolean isValid(Object value) {
    return ValidatorUtil.patternMatches(REGEX_PATTERN_JBA_BANK_CODE, value);
  }

  public void init(JbaBankCode annotation) {
  }
}
