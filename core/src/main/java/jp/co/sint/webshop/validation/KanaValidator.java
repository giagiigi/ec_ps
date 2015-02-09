package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.text.Messages;

/**
 * カナ(全角カタカナ)用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class KanaValidator implements Validator<Kana> {

  private static final String REGEX_PATTERN_KANA = "[ァ-ヶ・ーヽヾ]*";

  public String getMessage() {
    //modify by V10-CH 170 start
    return Messages.getString("validation.KanaValidator.0");
    //modify by V10-CH 170 end
  }

  public void init(Kana annotation) {
  }

  public boolean isValid(Object value) {
    return ValidatorUtil.patternMatches(REGEX_PATTERN_KANA, value);
  }
}
