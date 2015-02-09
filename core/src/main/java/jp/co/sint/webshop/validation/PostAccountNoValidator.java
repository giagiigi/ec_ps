package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.PostAccountNo;
import jp.co.sint.webshop.text.Messages;

/**
 * 邮局汇款客户商户号のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class PostAccountNoValidator implements Validator<PostAccountNo> {

  private static final String  REGEX_PATTERN_JBA_ACCOUNT_NO = "[0-9]{1,9}";

  public String getMessage() {
    return Messages.getString("validation.PostAccountNoValidator.0");
  }

  public boolean isValid(Object value) {
    return ValidatorUtil.patternMatches(REGEX_PATTERN_JBA_ACCOUNT_NO, value);
  }

  public void init(PostAccountNo annotation) {
  }
}
