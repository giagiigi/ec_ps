package jp.co.sint.webshop.validation;

import java.math.BigDecimal;
import java.text.MessageFormat;

import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 数値の範囲チェック用Validatorです。
 * 
 * @author System Integrator Corp.
 */
public class RangeValidator implements Validator<Range> {

  private Long min;

  private Long max;

  public RangeValidator() {

  }

  public RangeValidator(Long min, Long max) {
    setMin(min);
    setMax(max);
  }

  public Long getMax() {
    return max;
  }

  public Long getMin() {
    return min;
  }

  public void setMax(Long max) {
    this.max = max;
  }

  public void setMin(Long min) {
    this.min = min;
  }

  public boolean isValid(Object value) {
    if (value == null) {
      return true;
    }
    if (value instanceof Long) {
      return ValidatorUtil.inRange((Long) value, min, max);
    } else {
      if (value instanceof String) {
        // 10.1.2 10075 修正 ここから
        // return ValidatorUtil.inRange(NumUtil.toLong((String) value), min, max);
        String strValue = (String) value;
        if (StringUtil.isNullOrEmpty(strValue)) {
          return true;
        }
        //return NumUtil.isNum(strValue) && ValidatorUtil.inRange(NumUtil.toLong(strValue), min, max);
        return NumUtil.isDecimal(strValue) && ValidatorUtil.inRange(new BigDecimal(strValue).longValue(), min, max);
        
        // 10.1.2 10075 修正 ここまで
      }
      return false;
    }
  }

  public void init(Range annotation) {
    setMax(annotation.max());
    setMin(annotation.min());
  }

  public String getMessage() {
    // 10.1.2 10075 修正 ここから
    // if (min != 0L && max != Long.MAX_VALUE) {
    if (min.longValue() != Long.MIN_VALUE && max.longValue() != Long.MAX_VALUE) {
      // 10.1.2 10075 修正 ここまで
      return MessageFormat.format(Messages.getString("validation.RangeValidator.0"), min, max);
      // 10.1.2 10075 追加 ここから
    } else if (min.longValue() == Long.MIN_VALUE && max.longValue() == Long.MAX_VALUE) {
      return Messages.getString("validation.RangeValidator.4");
      // 10.1.2 10075 追加 ここまで
    } else if (max.longValue() == Long.MAX_VALUE) {
      return MessageFormat.format(Messages.getString("validation.RangeValidator.1"), min);  //$NON-NLS-1$
      // 10.1.2 10075 修正 ここから
      // } else if (min == 0L) {
    } else if (min.longValue() == Long.MIN_VALUE) {
      // 10.1.2 10075 修正 ここまで
      return MessageFormat.format(Messages.getString("validation.RangeValidator.2"), max);  //$NON-NLS-1$
    } else {
      return MessageFormat.format(Messages.getString("validation.RangeValidator.3"), min, max);  //$NON-NLS-1$
    }
  }

}
