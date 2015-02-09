package jp.co.sint.webshop.validation;

import java.math.BigDecimal;
import java.text.MessageFormat;

import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 数値データの精度を規定するバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class PrecisionValidator implements Validator<Precision> {

  private int precision;

  private int scale;

  public PrecisionValidator() {
  }

  public PrecisionValidator(int precision, int scale) {
    setPrecision(precision);
    setScale(scale);
  }

  public int getPrecision() {
    return precision;
  }

  public void setPrecision(int precision) {
    this.precision = precision;
  }

  public int getScale() {
    return scale;
  }

  public void setScale(int scale) {
    this.scale = scale;
  }

  public void init(Precision annotation) {
    setPrecision(annotation.precision());
    setScale(annotation.scale());
  }

  private boolean enabled() {
    return (getPrecision() > 0) && (getScale() >= 0) && (getPrecision() - getScale() > 0);
  }

  private boolean disabled() {
    return !enabled();
  }

  public boolean isValid(Object value) {

    if (disabled()) {
      String errorMessage = MessageFormat.format("invalid arguments: precision={0}, scale={1}", //$NON-NLS-1$
          getPrecision(), getScale());
      throw new IllegalArgumentException(errorMessage);
    }

    if (value == null) {
      return true;
    }
    // M17N-0001 追加 ここから
    if (StringUtil.isNullOrEmpty(value.toString())) {
      return true;
    }
    // M17N-0001 追加 ここまで
    BigDecimal bdValue = null;
    boolean result = false;
    try {
      if (value instanceof BigDecimal) {
        bdValue = (BigDecimal) value;
      } else {
        bdValue = new BigDecimal(value.toString()); // double, floatはNG?
      }
      result = bdValue.precision() <= getPrecision() && bdValue.scale() <= getScale()
          // M17N-0001 追加 ここから
          && bdValue.precision() - bdValue.scale() <= getPrecision() - getScale();
          // M17N-0001 追加 ここまで
    } catch (RuntimeException e) {
      result = false;
    }
    return result;
  }

  public String getMessage() {
    return MessageFormat.format(Messages.getString("validation.PrecisionValidator.0"), precision - scale, scale); //$NON-NLS-1$
  }

}
