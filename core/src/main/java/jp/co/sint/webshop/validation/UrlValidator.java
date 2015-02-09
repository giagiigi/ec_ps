package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.Url;
import jp.co.sint.webshop.text.Messages;

/**
 * URL用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class UrlValidator implements Validator<Url> {

  private static final String REGEX_PATTERN_URL = "(^(https?)(://[-_.!~*'()a-zA-Z0-9;/?:@&=+$,%#]+)$)?";

  public String getMessage() {
    //modify by V10-CH 170 start
    return Messages.getString("validation.UrlValidator.0"); //$NON-NLS-1$
    //modify by V10-CH 170 end
  }

  public void init(Url annotation) {
  }

  public boolean isValid(Object value) {
    return ValidatorUtil.patternMatches(REGEX_PATTERN_URL, value);
  }
}
