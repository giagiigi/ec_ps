package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.JbaBranchCode;
import jp.co.sint.webshop.text.Messages;

/**
 * JANコード用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class JbaBranchCodeValidator implements Validator<JbaBranchCode> {

  private static final String  REGEX_PATTERN_JBA_BRANCH_CODE = "[0-9]{3}";

  public String getMessage() {
    return Messages.getString("validation.JbaBranchCodeValidator.0");
  }

  public boolean isValid(Object value) {
    return ValidatorUtil.patternMatches(REGEX_PATTERN_JBA_BRANCH_CODE, value);
  }

  public void init(JbaBranchCode annotation) {
  }
}
