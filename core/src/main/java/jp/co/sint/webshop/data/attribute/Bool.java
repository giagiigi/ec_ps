package jp.co.sint.webshop.data.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.co.sint.webshop.validation.BoolValidator;

/**
 * データが「フラグ値」であることを示すアノテーションです。
 * 
 * @author System Integrator Corp.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ValidatorClass(BoolValidator.class)
public @interface Bool {

}
