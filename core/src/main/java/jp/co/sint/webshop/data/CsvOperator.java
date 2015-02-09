package jp.co.sint.webshop.data;

import java.util.List;

/**
 * CSVファイル取込の共通インタフェース
 * 
 * @author System Integrator Corp.
 * @param <T>
 *          CSVファイルに対応するBeanクラス
 */
public interface CsvOperator<T> {

  /**
   * 取り込み対象のCSVファイルに対応するBeanのクラスを取得します。
   * 
   * @return 取り込み対象のCSVファイルに対応するBeanのクラス
   */
  Class<T> getCsvBeanType();

  /**
   * 取り込み対象CSVファイルの列定義を取得します。 列名をアンダースコア区切り
   * <p>
   * このネーミングはBeanクラスのgetter/setterと対応している必要があります。
   * </p>
   * 
   * <pre>
   * 例:
   * Beanがフィールドとして&quot;shopCode&quot;, &quot;commodityCode&quot;, &quot;unitPrice&quot;の3フィールドを持つ
   * (getter/setterとしてget/setShopCode, get/setCommodityCode, get/setUnitPriceを持つ)場合、 
   * このメソッドの戻り値が{&quot;SHOP_CODE&quot;, &quot;COMMODITY_CODE&quot;, &quot;UNIT_PRICE&quot;}となるように
   * 定義します。
   * </pre>
   * 
   * @return 取り込み対象CSVファイルの列定義
   */
  String[] getColumnOrder();

  /**
   * 主キーとなるカラムの名前を返します。 列名をアンダースコア区切り
   * <p>
   * このネーミングはBeanクラスのgetter/setterと対応している必要があります。
   * </p>
   * 
   * <pre>
   * 例:
   * Beanの主キーとなるフィールドとして&quot;shopCode&quot;, &quot;commodityCode&quot;, &quot;unitPrice&quot;の3フィールドを持つ
   * (getter/setterとしてget/setShopCode, get/setCommodityCode, get/setUnitPriceを持つ)場合、 
   * このメソッドの戻り値が{&quot;SHOP_CODE&quot;, &quot;COMMODITY_CODE&quot;, &quot;UNIT_PRICE&quot;}となるように
   * 定義します。
   * </pre>
   * 
   * @return 主キーとなるカラムの名前
   */
  String[] getPrimaryKeys();

  List<String> validate(T bean);

  /**
   * 1件取り込んだときに実行されます
   * 
   * @param bean
   * @return 実行結果が成功であればtrue
   */
  boolean execute(T bean);

  /** csvヘッダ行があるかどうか返します */
  boolean getUseHeader();

  void onError(T bean);

  void init(String user);

  void dispose();

  int maxErrorCount();

}
