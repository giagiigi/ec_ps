package jp.co.sint.webshop.utility;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CollectionUtil {

  /**
   * 新しいCollectionUtilを生成します。
   */
  private CollectionUtil() {
  }

  /**
   * 指定されたリストに、要素をコピーします。
   * <li>引数で受け取ったリスト(dest)をクリアします。</li>
   * <li>クリアされたリスト(dest)に、引数で受け取った要素(src)を新たに追加します。</li>
   * 
   * @param <E>
   *          リストの型
   * @param dest
   *          追加先であるリスト
   * @param src
   *          追加元である要素
   */
  public static <E>void copyAll(List<? super E> dest, List<? extends E> src) {
    dest.clear();
    dest.addAll(src);
  }

  /**
   * 指定されたSetに、要素をコピーします。
   * <li>引数で受け取ったSet(dest)をクリアします。</li>
   * <li>クリアされたSet(dest)に、引数で受け取った要素(src)を新たに追加します。</li>
   * 
   * @param <E>
   *          Setの型
   * @param dest
   *          追加先であるSet
   * @param src
   *          追加元である要素
   */
  public static <E>void copyAll(Set<? super E> dest, Set<? extends E> src) {
    dest.clear();
    dest.addAll(src);
  }

  /**
   * 指定されたMapに、要素をコピーします。
   * <li>引数で受け取ったMap(dest)をクリアします。</li>
   * <li>クリアされたMap(dest)に、引数で受け取った要素(src)を新たに追加します。</li>
   * 
   * @param <K>
   *          Mapの型(キー)
   * @param <V>
   *          Mapの型(値)
   * @param dest
   *          追加先であるMap
   * @param src
   *          追加元である要素
   */
  public static <K, V>void copyAll(Map<? super K, ? super V> dest, Map<? extends K, ? extends V> src) {
    dest.clear();
    dest.putAll(src);
  }

}
