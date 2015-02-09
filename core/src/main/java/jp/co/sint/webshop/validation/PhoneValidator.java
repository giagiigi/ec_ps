package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.Phone;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 電話番号用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class PhoneValidator implements Validator<Phone> {

  private static final String REGEX_PATTERN_PHONE = "((^[0-9]{2,6}+-[0-9]{6,10}+-{0,1}[0-9]{0,9}+$)|(^[0-9]+$))?";
  
  public String getMessage() {
    //modify by V10-CH 170 start
    return Messages.getString("validation.PhoneValidator.0");
    //modify by V10-CH 170 end
  }

  public void init(Phone annotation) {
  }

  public boolean isValid(Object value) {
    if (value != null && StringUtil.hasValue(String.valueOf(value).replaceAll("-", ""))) {
      return ValidatorUtil.patternMatches(REGEX_PATTERN_PHONE, value);
    } else {
      return true;
    }
  }
}
