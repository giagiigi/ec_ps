package jp.co.sint.webshop.data.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * データのメタ情報(データ名称、定義順序)を設定するためのアノテーションです。
 * 
 * @author System Integrator Corp.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Metadata {

  /**
   * データ名称を返します。デフォルト値は""(空文字列)です。
   * 
   * @return データ名称
   */
  String name() default "";

  /**
   * データの定義順序を返します。デフォルト値はintの最大値(Integer.MAX_VALUE)です。
   * 
   * @return データの定義順序
   */
  int order() default Integer.MAX_VALUE;
}
