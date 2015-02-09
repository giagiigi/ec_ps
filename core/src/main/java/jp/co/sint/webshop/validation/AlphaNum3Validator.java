package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.AlphaNum3;
import jp.co.sint.webshop.text.Messages;

/**
 * 拡張半角英数字(alphanum3)用のバリデータクラスです。
 * 
 * @author KS
 */
public class AlphaNum3Validator implements Validator<AlphaNum3> {

  private static final String REGEX_PATTERN_ALPHANUM3 = "[-A-Za-z0-9_:\\.\\+]*";

  public String getMessage() {
    return Messages.getString("validation.AlphaNum3Validator.0");
  }

  /**
   * 
   */
  public void init(AlphaNum3 annotation) {
  }

  /**
   * 値を検証します。値がnullの場合は検証対象外としてtrueを返します。
   * 
   * @param value
   *          検証対象となるオブジェクト
   * @return 値がNumberインタフェースを実装している場合または半角英数字のみで構成されている文字列の場合にtrueを返します。
   */
  public boolean isValid(Object value) {
    if (value == null || value instanceof Number) {
      return true;
    }
    return ValidatorUtil.patternMatches(REGEX_PATTERN_ALPHANUM3, value);
  }

}
