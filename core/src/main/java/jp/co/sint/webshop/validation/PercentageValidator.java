package jp.co.sint.webshop.validation;

import java.text.MessageFormat;

import jp.co.sint.webshop.data.attribute.Percentage;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.Range;

/**
 * パーセンテージ用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class PercentageValidator implements Validator<Percentage> {

  private Range<Integer> range = new Range<Integer>(0, 100);

  public String getMessage() {
    //modify by V10-CH 170 start
    return MessageFormat.format(Messages.getString(
    "validation.PercentageValidator.0"), range.getStart(), range.getEnd());
    //modify by V10-CH 170 end
  }

  public void init(Percentage annotation) {
    range = new Range<Integer>(annotation.min(), annotation.max());
  }

  public boolean isValid(Object value) {
    Number num = null;
    if (value == null) {
      return true;
    } else if (value instanceof Number) {
      num = (Number) value;
    } else {
      try {
        num = Integer.valueOf(value.toString());
      } catch (NumberFormatException nfEx) {
        return false;
      }
    }
    return ValidatorUtil.inRange(num.intValue(), range.getStart(), range.getEnd());
  }

}
