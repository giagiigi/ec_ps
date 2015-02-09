package jp.co.sint.webshop.data.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.co.sint.webshop.validation.BankCodeValidator;

/**
 * データがRFC1630に定義されている"alphanum2"の範囲に含まれることを示すアノテーションです。
 * alphanum2には半角英数字とハイフン(-)、アンダースコア(_)、ピリオド(.)、プラス記号(+)が含まれます。
 * <ul>
 * <li>BankCode := alpha | digit | - | *
 * </ul>
 * RFC 1630: <a
 * href="http://www.w3.org/Addressing/rfc1630.txt">http://www.w3.org/Addressing/rfc1630.txt</a>
 * 
 * @author System Integrator Corp.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ValidatorClass(BankCodeValidator.class)
public @interface BankCode {

}
