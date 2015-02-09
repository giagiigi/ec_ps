package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.BankCode;
import jp.co.sint.webshop.text.Messages;

/**
 * 拡張半角英数字(BankCode)用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class BankCodeValidator implements Validator<BankCode> {

  private static final String REGEX_PATTERN_BANKCODE = "[-A-Za-z0-9\\*]*";

  public String getMessage() {
    //modify by V10-CH 170 start
    return Messages.getString("validation.BankCodeValidator.0"); //$NON-NLS-1$
    //modify by V10-CH 170 end
  }

  /**
   * 
   */
  public void init(BankCode annotation) {
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
    return ValidatorUtil.patternMatches(REGEX_PATTERN_BANKCODE, value);
  }

}
