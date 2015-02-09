package jp.co.sint.webshop.data.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.co.sint.webshop.validation.Validator;

/**
 * このパッケージ(jp.co.sint.webshop.data.attribute)で定義されたデータ属性と、
 * 検証オブジェクトを関連付けるためのアノテーションです。
 * 
 * @author System Integrator Corp.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ValidatorClass {

  /**
   * データ属性に関連付けられたValidatorオブジェクトを返します。
   * @return データ属性に関連付けられたValidatorオブジェクト
   */
  Class<? extends Validator<?>> value();
}
