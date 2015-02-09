package jp.co.sint.webshop.data.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jp.co.sint.webshop.validation.AlphaNum3Validator;

/**
 * データがRFC1630に定義されている"alphanum3"の範囲に含まれることを示すアノテーションです。
 * alphanum3には半角英数字とハイフン(-)、アンダースコア(_)、ピリオド(.)、プラス記号(+)、コロン(:)が含まれます。
 * <ul>
 * <li>alphanum2 := alpha | digit | - | _ | . | + | :
 * </ul>
 * RFC 1630: <a
 * href="http://www.w3.org/Addressing/rfc1630.txt">http://www.w3.org/Addressing/rfc1630.txt</a>
 * 
 * @author KS.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ValidatorClass(AlphaNum3Validator.class)
public @interface AlphaNum3 {

}
