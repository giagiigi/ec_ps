package jp.co.sint.webshop.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.utility.BeanUtil;

/**
 * 入力値検証用のユーティリティです。このクラスをインスタンス化することはできません。
 * 
 * @author System Integrator Corp.
 */
public final class ValidatorUtil {

  private ValidatorUtil() {
  }

  /**
   * 値がnullかどうかを返します。
   * 
   * @param value
   *          チェックする値
   * @return 値がnullならtrue
   */
  public static boolean isNull(Object value) {
    return value == null;
  }

  /**
   * 文字列がnullまたは空文字列かどうかを返します。
   * 
   * @param value
   *          チェックする値
   * @return 値がnull、もしくは長さゼロの文字列ならtrue
   */
  public static boolean isNullOrEmpty(String value) {
    return value == null || value.length() == 0;
  }

  public static boolean patternMatches(String pattern, Object value) {

    if (value == null) {
      return true;
    }

    if (!(value instanceof String)) {
      return false;
    }

    return Pattern.matches(pattern, (String) value);
  }

  /**
   * 値が特定の範囲に入っているかどうかを返します。 範囲には値の両端(最大値、最小値)を含みます。
   * 
   * @param <T>
   *          比較可能な型(Comparable<T>)
   * @param value
   *          チェックする値
   * @param min
   *          最小値
   * @param max
   *          最大値
   * @return 値が最小値～最大値の間に入っていればtrue
   */
  public static <T extends Comparable<T>>boolean inRange(T value, T min, T max) {
    return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
  }

  /**
   * 値が比較対象より大きいかどうかを返します。
   * 
   * @param <T>
   *          比較可能な型(Comparable<T>)
   * @param value
   *          チェックする値
   * @param target
   *          比較対象
   * @return 値が比較対象より大きければtrue
   */
  public static <T extends Comparable<T>>boolean moreThan(T value, T target) {
    return value.compareTo(target) > 0;
  }

  /**
   * 値が比較対象と等しい、または比較対象より大きいかどうかを返します。
   * 
   * @param <T>
   *          比較可能な型(Comparable<T>)
   * @param value
   *          チェックする値
   * @param target
   *          比較対象
   * @return 値が比較対象と等しい、または比較対象より大きければtrue
   */
  public static <T extends Comparable<T>>boolean moreThanOrEquals(T value, T target) {
    return value.compareTo(target) >= 0;
  }

  /**
   * 値が比較対象より小さいかどうかを返します。
   * 
   * @param <T>
   *          比較可能な型(Comparable<T>)
   * @param value
   *          チェックする値
   * @param target
   *          比較対象
   * @return 値が比較対象より小さければtrue
   */
  public static <T extends Comparable<T>>boolean lessThan(T value, T target) {
    return value.compareTo(target) < 0;
  }

  /**
   * 値が比較対象と等しい、または対象より小さいかどうかを返します。
   * 
   * @param <T>
   *          比較可能な型(Comparable<T>)
   * @param value
   *          チェックする値
   * @param target
   *          比較対象
   * @return 値が比較対象と等しい、または比較対象より小さければtrue
   */
  public static <T extends Comparable<T>>boolean lessThanOrEquals(T value, T target) {
    return value.compareTo(target) <= 0;
  }

  /**
   * 文字列が最大長に収まっているかどうかを返します。
   * 
   * @param value
   *          チェックする値
   * @param maxLength
   *          最大長
   * @return 文字列の長さが最大長以下であればtrue
   */
  public static boolean withinLength(String value, int maxLength) {
    return value.length() <= maxLength;
  }

  /**
   * 文字列が正規表現にマッチしているかどうかを返します。
   * 
   * @param value
   *          チェック対象文字列
   * @param pattern
   *          正規表現のパターン文字列
   * @return マッチしていればtrue
   */
  public static boolean matchesWith(String value, String pattern) {
    return Pattern.matches(pattern, value);
  }

  /**
   * 期間を表す2つの値が正しい順序になっているかどうかを返します。 このメソッドはlessThanOrEquals()と同じ実装です。
   * 
   * @param <T>
   *          比較可能な型(Comparable<T>)
   * @param from
   *          期間開始
   * @param to
   *          期間終了
   * @return from ≦ toであればtrue
   */
  public static <T extends Comparable<T>>boolean isCorrectOrder(T from, T to) {
    return lessThanOrEquals(from, to);
  }

  /**
   * リストをValidatorによって検証し、すべての検証結果の積集合を返します。
   * 
   * @param <A>
   *          Validatorのアノテーションタイプ
   * @param <E>
   *          値の型
   * @param validator
   *          値を検証するValidatorオブジェクト
   * @param valueList
   *          値のリスト
   * @return 各要素の検証結果がすべてtrueのときのみtrueを返す。それ以外はfalse。
   */
  public static <A extends Annotation, E extends Object>boolean areValidAllOf(Validator<A> validator, List<E> valueList) {
    boolean result = true;
    for (Object value : valueList) {
      result &= validator.isValid(value);
    }
    return result;
  }

  /**
   * リストをValidatorによって検証し、すべての検証結果の和集合を返します。
   * 
   * @param <A>
   *          Validatorのアノテーションタイプ
   * @param <E>
   *          値の型
   * @param validator
   *          値を検証するValidatorオブジェクト
   * @param valueList
   *          値のリスト
   * @return 各要素の検証結果が1つでもtrueであればtrue。すべてfalseのときはfalseを返す。
   */
  public static <A extends Annotation, E extends Object>boolean areValidAnyOf(Validator<A> validator, List<E> valueList) {
    boolean result = false;
    for (Object value : valueList) {
      result |= validator.isValid(value);
    }
    return result;
  }

  /**
   * 2つのオブジェクトを比較し、値が等しいかどうかを調べます。
   * 
   * @param e1
   *          比較対象となるオブジェクト(1)
   * @param e2
   *          比較対象となるオブジェクト(2)
   * @return e1.equals(e2)結果がtrue、あるいはt1,
   *         e2がいずれもnullの場合はtrueを返します。そうでなければfalseを返します。
   */
  public static boolean areEqualOrNull(Object e1, Object e2) {
    if (e1 == null && e2 == null) {
      return true;
    } else if (e1 != null && e2 != null) {
      return e1.equals(e2);
    } else {
      return false;
    }
  }

  /**
   * 2つのオブジェクトを比較し、内容が等しいかどうかを調べます。
   * 
   * @param <E>
   *          WebshopEntityインタフェースを継承する型
   * @param e1
   *          比較対象となるオブジェクト(1)
   * @param e2
   *          比較対象となるオブジェクト(2)
   * @return <E>型に含まれるすべてのフィールドに対してe1, e2の値を比較した結果が すべてtrueの場合、あるいはe1,
   *         e2ともにnullの場合はtrueを返します。
   */
  public static <E extends WebshopEntity>boolean areEqualOrNull(E e1, E e2) {
    Logger logger = Logger.getLogger(ValidatorUtil.class);
    boolean result = false;
    if (e1 == null && e2 == null) {
      result = true;
    } else if (e1 == null || e2 == null) {
      result = false;
    } else {
      result = true;
      for (Field f : e1.getClass().getDeclaredFields()) {
        if (f.getName().equals("serialVersionUID")) {
          continue;
        }
        Object v1 = BeanUtil.invokeGetter(e1, f.getName());
        Object v2 = BeanUtil.invokeGetter(e2, f.getName());
        boolean r = ValidatorUtil.areEqualOrNull(v1, v2);
        result &= r;
        Object[] arguments = new Object[] {
            f.getName(), v1, v2, result
        };
        logger.trace(MessageFormat.format("{0}:{1}/{2} - {3}", arguments));
      }
    }
    return result;
  }
}
