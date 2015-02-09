package jp.co.sint.webshop.data.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.co.sint.webshop.validation.PrecisionValidator;

/**
 * 数値型データの精度、スケールを示すアノテーションです。
 * 
 * @author System Integrator Corp.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ValidatorClass(PrecisionValidator.class)
public @interface Precision {

  /**
   * 精度(桁数)を返します。
   * 
   * @return 桁数
   */
  int precision();

  /**
   * スケール(小数点以下の桁数)を返します。
   * 
   * @return スケール
   */
  int scale() default 0;
}
