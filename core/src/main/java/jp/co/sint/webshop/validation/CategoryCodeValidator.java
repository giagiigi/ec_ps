package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.CategoryCode;
import jp.co.sint.webshop.text.Messages;

/**
 * カテゴリコード用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CategoryCodeValidator implements Validator<CategoryCode> {

  private static final String REGEX_PATTERN_CATEGORY_CODE = "[-A-Za-z0-9_\\.]*|/";

  public String getMessage() {
    //modify by V10-CH 170 start
    return Messages.getString("validation.CategoryCodeValidator.1");
    //modify by V10-CH 170 end
  }

  public boolean isValid(Object value) {
    return ValidatorUtil.patternMatches(REGEX_PATTERN_CATEGORY_CODE, value);
  }

  public void init(CategoryCode annotation) {
  }
}
