package jp.co.sint.webshop.utility;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Phone;

/**
 * データのユーティリティクラスです。
 * 
 * @author System Integrator Corp.
 */
public final class DataConverter {

  /** private constructor */
  private DataConverter() {

  }

  /**
   * 受取ったオブジェクトの編場変数を、そのメンバ変数のアノテーションから自動で変換します。<br>
   * 変換を行うアノテーションは以下の通りです。
   * <ol>
   * <li>Kana</li>
   * <li>Digit</li>
   * <li>Phone</li>
   * <li>Currency</li>
   * </ol>
   * 
   * @param object
   *          変換を行いたいBean
   */
  public static void adjustInput(Object object) {
    if (object == null) {
      return;
    }

    Field[] fields = object.getClass().getDeclaredFields();

    try {
      for (Field field : fields) {

        if (field.getAnnotations().length == 0) {
          continue;
        }

        Annotation[] annotations = field.getAnnotations();

        for (Annotation annotation : annotations) {

          String getMethodName = getGetterMethodName(field.getName());
          String setMethodName = getSetterMethodName(field.getName());
          Method getMethod = object.getClass().getMethod(getMethodName);
          Method setMethod = object.getClass().getMethod(setMethodName, String.class);

          if (annotation instanceof Kana) {
            setMethod.invoke(object, StringUtil.toKatakana((String) getMethod.invoke(object)));
          } else if (annotation instanceof Digit || annotation instanceof Phone || annotation instanceof Currency) {
            setMethod.invoke(object, StringUtil.toHalfWidth((String) getMethod.invoke(object)));
          }
        }
      }
    } catch (RuntimeException e) {
      throw new RuntimeException(e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * 引数で受取ったメンバ変数の一文字目を大文字にし、冒頭に"get"を付けたものを返します。<br>
   * ex) getGetterMethodName("fieldName");→getFieldName<br>
   * <br>
   * 引数で受取ったフィールド名の一文字目は英字であるものとします。<br>
   * 英字以外の文字だった場合、"get"つなぎでそのままの文字列が返ります。
   * 
   * @param f
   *          メンバ変数名
   * @return メソッド名
   */
  private static String getGetterMethodName(String f) {
    return "get" + f.substring(0, 1).toUpperCase(Locale.JAPAN) + f.substring(1);
  }

  /**
   * 引数で受取ったメンバ変数の一文字目を大文字にし、頭に"set"をつけた物を返します。<br>
   * ex) getSetterMethodName("fieldName");→setFieldName<br>
   * <br>
   * 引数で受取ったフィールド名の一文字目は英字であるものとします。<br>
   * 英字以外の文字だった場合、"set"つなぎでそのままの文字列が返ります。
   * 
   * @param f
   *          メンバ変数名
   * @return メソッド名
   */
  private static String getSetterMethodName(String f) {
    return "set" + f.substring(0, 1).toUpperCase(Locale.JAPAN) + f.substring(1);
  }
}
