package jp.co.sint.webshop.data.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.co.sint.webshop.validation.DomainValidator;


/**
 * 各種ステータスやフラグ等など、コード値が特定の定義域によって規定されていることを示すアノテーションです。
 * 
 * @author System Integrator Corp.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ValidatorClass(DomainValidator.class)
public @interface Domain {

  /**
   * 定義域を表す列挙体(Enum)を返します。
   * 
   * 指定されるクラスは「public static boolean isValid(String)」というメソッドシグネチャを持つ必要があります。
   * 
   * @return 定義域を表す列挙体(Enum)の型を表すclassオブジェクト
   */
  Class<?> value();

}
