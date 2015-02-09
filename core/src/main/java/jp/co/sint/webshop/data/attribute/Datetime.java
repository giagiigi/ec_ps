package jp.co.sint.webshop.data.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.co.sint.webshop.validation.DatetimeValidator;

/**
 * データが「日付書式」であることを示すアノテーションです。
 * 日付書式はjava.text.SimpleDateFormatと同じパターン文字列を指定できます。デフォルトの日付文字列は「yyyy/MM/dd」です。
 * 
 * @author System Integrator Corp.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ValidatorClass(DatetimeValidator.class)
public @interface Datetime {

  /**
   * 日付文字列を返します。
   * 
   * @return 日付文字列
   */
  String format() default "yyyy/MM/dd";

  //String message() default "有効な日付ではありません。";
}
