package jp.co.sint.webshop.data.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.co.sint.webshop.validation.PatternValidator;

/**
 * データが、正規表現の示すパターンに従っていることを示すアノテーションです。
 * 
 * @author System Integrator Corp.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ValidatorClass(PatternValidator.class)
public @interface Pattern {

  /**
   * 正規表現のパターン文字列を返します。
   * 
   * @return 正規表現のパターン文字列
   */
  String value();

  String message() default "書式エラーです。";
}
