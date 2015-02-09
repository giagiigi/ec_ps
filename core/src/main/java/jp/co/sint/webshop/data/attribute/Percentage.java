package jp.co.sint.webshop.data.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.co.sint.webshop.validation.PercentageValidator;

/**
 * データが「パーセンテージ」であることを示すアノテーションです。
 * 
 * @author System Integrator Corp.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ValidatorClass(PercentageValidator.class)
public @interface Percentage {

  /**
   * 最小値を返します。
   * 
   * @return 最小値
   */
  int min() default 0;

  /**
   * 最大値を返します。
   * 
   * @return 最大値
   */
  int max() default 100;
}
