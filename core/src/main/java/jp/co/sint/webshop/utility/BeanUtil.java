package jp.co.sint.webshop.utility;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Bean操作用のユーティリティクラスです。
 * 
 * @author System Integrator Corp.
 */
public final class BeanUtil {

  /**
   * コンストラクタ
   */
  private BeanUtil() {

  }

  /**
   * 引数で受け取ったオブジェクトの中から、一番最初にnullでなかったものを返します。<br>
   * 引数で受け取るオブジェクトの個数に制限はありません。
   * 
   * @param <T>
   *          オブジェクトの型
   * @param values
   *          nullかどうかを判断するオブジェクト
   * @return 引数で受け取ったオブジェクトのうち、一番最初にnullでなかったものを返します。<br>
   *         全てのオブジェクトがnullだった場合、nullを返します。
   */
  public static <T>T coalesce(T... values) {
    if (values != null) {
      for (T v : values) {
        if (v != null) {
          return v;
        }
      }
    }
    return null;
  }

  public static boolean isNull(Object obj) {
    return obj == null;
  }
  
  public static boolean areNullAllOf(Object...objects) {
    if (objects == null) {
      return true;
    }
    boolean result = true;
    for (Object obj : objects) {
      result &= obj == null;
    }
    return result;
  }
  public static boolean areNullAnyOf(Object...objects) {
    if (objects == null) {
      return true;
    }
    boolean result = false;
    for (Object obj : objects) {
      result |= obj == null;
    }
    return result;
  }
  
  /**
   * 引数で受け取った半角英数文字列の一文字目を大文字に変換し、冒頭に"get"を付けたものを返します。<br>
   * e.g. getGetterMethodName("fieldName");→getFieldName<br>
   * <br>
   * 引数で受け取ったフィールド名の一文字目は英字であるものとします。<br>
   * 英字以外の文字だった場合、"get"つなぎでそのままの文字列が返ります。
   * 
   * @param fieldName
   *          フィールド名
   * @return getterメソッド名<br>
   *         引数で受け取ったフィールド名に対応するgetterメソッド名を返します。
   */
  public static String getGetterMethodName(String fieldName) {
    return "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
  }

  /**
   * 引数で受け取った半角英数文字列の一文字目を大文字に変換し、冒頭に"set"を付けたものを返します。<br>
   * e.g. getSetterMethodName("fieldName");→setFieldName<br>
   * <br>
   * 引数で受け取ったフィールド名の一文字目は英字であるものとします。<br>
   * 英字以外の文字だった場合、"set"つなぎでそのままの文字列が返ります。
   * 
   * @param fieldName
   *          フィールド名
   * @return setterメソッド名<br>
   *         引数で受け取ったフィールド名に対応するsetterメソッド名を返します。
   */
  public static String getSetterMethodName(String fieldName) {
    return "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
  }

  /**
   * 引数で受け取ったBeanのgetterメソッドを呼び出し、フィールドの値を取得します。<br>
   * ただし、以下のいずれかに該当する場合、RuntimeExceptionが発生します。
   * <ol>
   * <li>Beanに存在しないgetterメソッド(フィールド名)を指定した場合</li>
   * <li>privateなgetterメソッドにアクセスしようとした場合</li>
   * <li>指定したgetterメソッドが例外を投げる場合</li>
   * </ol>
   * 
   * @param <T>
   *          取得する値の型
   * @param bean
   *          getterメソッドを呼び出すBean
   * @param fieldName
   *          フィールド名
   * @return フィールドに設定されている値<br>
   *         引数で受け取ったBeanが持つフィールドのうち、引数で受け取ったフィールドに設定されている値を返します。
   */
  @SuppressWarnings("unchecked")
  public static <T>T invokeGetter(Object bean, String fieldName) {
    T result = null;
    Logger logger = Logger.getLogger(BeanUtil.class);
    try {
      Method getter = bean.getClass().getMethod(getGetterMethodName(fieldName));
      result = (T) getter.invoke(bean);
    } catch (NoSuchMethodException e) {
      logger.trace(e);
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      logger.trace(e);
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      logger.trace(e);
      throw new RuntimeException(e);
    }
    return result;
  }

  /**
   * 引数で受け取ったBeanのsetterメソッドを呼び出し、引数で受け取ったフィールドに値を設定します。<br>
   * ただし、以下のいずれかに該当する場合、RuntimeExceptionが発生します。
   * <ol>
   * <li>Beanに存在しないsetterメソッド(フィールド名)を指定した場合</li>
   * <li>privateなsetterメソッドにアクセスしようとした場合</li>
   * <li>指定したsetterメソッドが例外を投げる場合</li>
   * </ol>
   * 
   * @param <T>
   *          設定する値の型
   * @param bean
   *          setterメソッドを呼び出すBean
   * @param fieldName
   *          フィールド名
   * @param value
   *          フィールドに設定する値
   */
  public static <T>void invokeSetter(Object bean, String fieldName, T value) {
    Logger logger = Logger.getLogger(BeanUtil.class);
    try {
      Method setter = bean.getClass().getMethod(getSetterMethodName(fieldName), value.getClass());
      setter.invoke(bean, value);
    } catch (NoSuchMethodException e) {
      logger.trace(e);
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      logger.trace(e);
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      logger.trace(e);
      throw new RuntimeException(e);
    }
  }

  public static <A extends Annotation>A getAnnotation(Class<?> beanType, String fieldName, Class<A> annotationType) {
    Logger logger = Logger.getLogger(BeanUtil.class);
    A result = null;
    try {
      result = beanType.getDeclaredField(fieldName).getAnnotation(annotationType);
    } catch (RuntimeException e) {
      logger.warn(e);
    } catch (Exception e) {
      logger.warn(e);
    }
    return result;
  }

  public static <T>T findFirst(Collection<T> collection, BeanCriteria<T> condition) {
    if (collection == null) {
      return null;
    }
    for (T t : collection) {
      if (condition.match(t)) {
        return t;
      }
    }
    return null;
  }

  public static <T>Collection<T> findAll(Collection<T> collection, BeanCriteria<T> condition) {
    if (collection == null) {
      return null;
    }
    List<T> result = new ArrayList<T>();
    for (T t : collection) {
      if (condition.match(t)) {
        result.add(t);
      }
    }
    return result;
  }

  public static <T>void forEach(Collection<T> collection, BeanDelegate<T> proc) {
    for (T t : collection) {
      proc.execute(t);
    }
  }
  
  public static int generateInstantHashCode(Object...objects) {
    //適当にハッシュコードを生成します
    int hash = 0;
    if (objects != null) {
      for (Object obj : objects) {
        if (obj != null) {
          hash += obj.hashCode();
        }
      }
    }
    return hash;
  }
}
