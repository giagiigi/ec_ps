package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.Point;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.PointUtil;

/**
 * ポイント用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class PointValidator implements Validator<Point> {

  private static final String REGEX_PATTERN_DIGIT_POSITIVE_ONLY = "(^[0-9]{1,8}+$)";

  private static final String REGEX_PATTERN_DIGIT_POSITIVE_ONLY1 = "(^[0-9]{1,8}+[.]{0,1}+[0-9]{1}+$)";

  private static final String REGEX_PATTERN_DIGIT_POSITIVE_ONLY2 = "(^[0-9]{1,8}+[.]{0,1}+[0-9]{1,2}+$)";

  public String getMessage() {
    if (PointUtil.getAcquiredPointScale() == 2) {
      return Messages.getString("validation.PointValidator.2");
    } else if (PointUtil.getAcquiredPointScale() == 1) {
      return Messages.getString("validation.PointValidator.1");
    } else {
      return Messages.getString("validation.PointValidator.0");
    }
  }

  public void init(Point annotation) {

  }

  public boolean isValid(Object value) {
    if (value == null) {
      return true;
    }
    if (PointUtil.getAcquiredPointScale() == 2) {
      return ValidatorUtil.patternMatches(REGEX_PATTERN_DIGIT_POSITIVE_ONLY, String.valueOf(value))
      || ValidatorUtil.patternMatches(REGEX_PATTERN_DIGIT_POSITIVE_ONLY2, String.valueOf(value));
    } else if (PointUtil.getAcquiredPointScale() == 1) {
      return ValidatorUtil.patternMatches(REGEX_PATTERN_DIGIT_POSITIVE_ONLY, String.valueOf(value))
      || ValidatorUtil.patternMatches(REGEX_PATTERN_DIGIT_POSITIVE_ONLY1, String.valueOf(value));
    } else {
      return ValidatorUtil.patternMatches(REGEX_PATTERN_DIGIT_POSITIVE_ONLY, String.valueOf(value));
    }
  }

}
