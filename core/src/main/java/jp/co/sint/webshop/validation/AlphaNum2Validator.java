package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.text.Messages;

/**
 * 拡張半角英数字(alphanum2)用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class AlphaNum2Validator implements Validator<AlphaNum2> {

  private static final String REGEX_PATTERN_ALPHANUM2 = "[-A-Za-z0-9_\\.\\+]*";

  public String getMessage() {
    //modify by V10-CH 170 start
    return Messages.getString("validation.AlphaNum2Validator.0"); //$NON-NLS-1$
    //modify by V10-CH 170 end
  }

  /**
   * 
   */
  public void init(AlphaNum2 annotation) {
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
    return ValidatorUtil.patternMatches(REGEX_PATTERN_ALPHANUM2, value);
  }

}
