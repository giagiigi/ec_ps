package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.JbaAccountNo;
import jp.co.sint.webshop.text.Messages;

/**
 * 口座番号用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class JbaAccountNoValidator implements Validator<JbaAccountNo> {

  private static final String  REGEX_PATTERN_JBA_ACCOUNT_NO = "[0-9]{1,7}";

  public String getMessage() {
    return Messages.getString("validation.JbaAccountNoValidator.0");
  }

  public boolean isValid(Object value) {
    return ValidatorUtil.patternMatches(REGEX_PATTERN_JBA_ACCOUNT_NO, value);
  }

  public void init(JbaAccountNo annotation) {
  }
}
