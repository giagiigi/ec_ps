package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 携帯番号用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class MobileNumberValidator implements Validator<MobileNumber> {

  private static final String REGEX_PATTERN_MOBILE_NUMBER = "([0-9]{11})?";

  public String getMessage() {
    return Messages.getString("validation.MobileNumberValidator.0");
  }

  public void init(MobileNumber annotation) {
  }

  public boolean isValid(Object value) {
    if (StringUtil.isNullOrEmpty(String.valueOf(value))) {
      return true;
    }
    return ValidatorUtil.patternMatches(REGEX_PATTERN_MOBILE_NUMBER, value);
  }
}
