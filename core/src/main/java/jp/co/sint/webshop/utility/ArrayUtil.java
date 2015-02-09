package jp.co.sint.webshop.utility;

import java.lang.reflect.Array;
import java.util.List;

import jp.co.sint.webshop.validation.ValidatorUtil;

/**
 * 配列操作用のユーティリティクラスです。
 * 
 * @author System Integrator Corp.
 */
public final class ArrayUtil {

  private ArrayUtil() {
  }

  /**
   * 配列をコピーします。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>配列の型情報から新たな配列のインスタンスを生成し、要素をコピーします。 コピー方式は「浅いコピー(shallow copy)」です。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>ありません。</dd>
   * </dl>
   * </p>
   * 
   * @param <T>
   *          配列の型
   * @param src
   *          コピー元となる配列
   * @return コピーされた配列のインスタンス。配列の引数がnullの場合はnullを返します。
   */
  @SuppressWarnings("unchecked")
  public static <T>T[] immutableCopy(T[] src) {
    T[] dest = null;
    if (src != null) {
      Class<T> arrayType = (Class<T>) src.getClass().getComponentType();
      dest = (T[]) Array.newInstance(arrayType, src.length);
      System.arraycopy(src, 0, dest, 0, src.length);
    }
    return dest;
  }

  /**
   * 配列を作成します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引数で受け取ったリスト、クラス情報から、新たな配列のインスタンスを生成します。</dd>
   * <ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param <T>
   *          リスト、クラスの型
   * @param src
   *          リスト
   * @param type
   *          クラス
   * @return リストの要素を含む配列
   */
  @SuppressWarnings("unchecked")
  public static <T>T[] toArray(List<T> src, Class<T> type) {
    return src.toArray((T[]) Array.newInstance(type, src.size()));
  }


  public static <T> boolean areEquals(T[] array1, T[] array2) {
    boolean result = true;
    if (array1 == null && array2 == null) {
      return result;
    } else if (array1 == null || array2 == null) {
      return false;
    } else if (array1.length != array2.length) {
      return false;
    } else {
      for (int i = 0; i < array1.length; i++) {
        result &= ValidatorUtil.areEqualOrNull(array1[i], array2[i]);
        if (!result) {
          break;
        }
      }
      return result;
    }
  }
}
