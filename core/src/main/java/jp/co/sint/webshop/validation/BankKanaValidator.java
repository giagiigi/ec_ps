package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.BankKana;
import jp.co.sint.webshop.text.Messages;

/**
 * 銀行口座名に利用できるカナ文字(全角カタカナ＋括弧など)用のバリデータクラスです。 利用できる文字は、
 * <ul>
 * <li>全角カタカナ</li>
 * <li>全角濁点、半濁点</li>
 * <li>全角英字</li>
 * <li>全角数字</li>
 * <li>全角括弧</li>
 * <li>全角コンマ、ピリオド</li>
 * </ul>
 * です。
 * 
 * @author System Integrator Corp.
 */
public class BankKanaValidator implements Validator<BankKana> {

  private static final String REGEX_PATTERN_BANK_KANA = "[０-９ァ-ロワヲンヴＡ-Ｚ゛゜￥「」，．（）ー‐―／　]*";

  public String getMessage() {
    //modify by V10-CH 170 start
    return Messages.getString("validation.BankKanaValidator.1");
    //modify by V10-CH 170 end
  }

  public void init(BankKana annotation) {
  }

  public boolean isValid(Object value) {
    return ValidatorUtil.patternMatches(REGEX_PATTERN_BANK_KANA, value);
  }
}
