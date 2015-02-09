package jp.co.sint.webshop.data.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.co.sint.webshop.validation.RangeValidator;

/**
 * データが最小値、最大値の範囲に収まっていることを示すアノテーションです。 このアノテーションはLong型専用です
 * 
 * @author System Integrator Corp.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ValidatorClass(RangeValidator.class)
public @interface Range {

  /**
   * 最小値を返します。
   */
  long min() default 0L;

  /**
   * 最大値を返します。
   */
  long max() default Long.MAX_VALUE;

}
