package jp.co.sint.webshop.data.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.co.sint.webshop.validation.LengthValidator;

/**
 * データの最大長、最大桁数を示すアノテーションです。
 * 
 * @author System Integrator Corp.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ValidatorClass(LengthValidator.class)
public @interface Length {

  /**
   * 桁数(データの最大長、最大桁数)を返します。
   * 
   * @return 桁数
   */
  int value();
}
