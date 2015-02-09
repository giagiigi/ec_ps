package jp.co.sint.webshop.data.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.co.sint.webshop.validation.BankKanaValidator;

/**
 * 銀行口座名に利用できるカナ文字(全角カタカナ＋括弧など)を示すアノテーションです。
 * 
 * @author System Integrator Corp.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ValidatorClass(BankKanaValidator.class)
public @interface BankKana {

}
