package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.PostalCode;
import jp.co.sint.webshop.text.Messages;

/**
 * 郵便番号用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class PostalCodeValidator implements Validator<PostalCode> {

  private static final String REGEX_PATTERN_POSTAL_CODE = "([0-9]{6})?";

  public String getMessage() {
    //modify by V10-CH 170 start
    return Messages.getString("validation.PostalCodeValidator.0");
    //modify by V10-CH 170 end
  }

  public void init(PostalCode annotation) {
  }

  public boolean isValid(Object value) {
    return ValidatorUtil.patternMatches(REGEX_PATTERN_POSTAL_CODE, value);
  }
}
