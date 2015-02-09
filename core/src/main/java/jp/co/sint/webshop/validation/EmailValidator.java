package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.text.Messages;

/**
 * メールアドレス用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 *
 */
public class EmailValidator implements Validator<Email> {

  private static final String REGEX_PATTERN_EMAIL = "([-A-Za-z0-9_\\.]+@[-A-Za-z0-9\\.]+)?";

  public String getMessage() {
    //modify by V10-CH 170 start
    return Messages.getString("validation.EmailValidator.0");
    //modify by V10-CH 170 end
  }

  public void init(Email annotation) {
  }

  public boolean isValid(Object value) {
    return ValidatorUtil.patternMatches(REGEX_PATTERN_EMAIL, value);
  }

}
