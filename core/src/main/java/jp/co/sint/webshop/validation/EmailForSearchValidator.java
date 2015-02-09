package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.EmailForSearch;
import jp.co.sint.webshop.text.Messages;

/**
 * JANコード用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class EmailForSearchValidator implements Validator<EmailForSearch> {

  private static final String  REGEX_PATTERN_EMAIL_FOR_SEARCH = "[-A-Za-z0-9_\\.@]*"; //$NON-NLS-1$

  public String getMessage() {
    return Messages.getString("validation.EmailForSearchValidator.0"); //$NON-NLS-1$
  }

  public boolean isValid(Object value) {
    return ValidatorUtil.patternMatches(REGEX_PATTERN_EMAIL_FOR_SEARCH, value);
  }

  public void init(EmailForSearch annotation) {
  }
}
