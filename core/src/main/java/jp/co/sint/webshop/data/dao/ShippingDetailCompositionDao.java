package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.ShippingDetailComposition;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「出荷明細構成品(SHIPPING_DETAIL_COMPOSITION)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author OB.
 *
 */
public interface ShippingDetailCompositionDao extends GenericDao<ShippingDetailComposition, Long> {

  /**
   * 指定されたorm_rowidを持つ出荷明細構成品のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するShippingDetailCompositionのインスタンス
   */
  ShippingDetailComposition loadByRowid(Long id);

  /**
   * 主キー列の値を指定して出荷明細構成品のインスタンスを取得します。
   *
   * @param shippingNo 出荷番号
   * @param shippingDetailNo 出荷明細番号
   * @param compositionNo 構成品項番
   * @return 主キー列の値に対応するShippingDetailCompositionのインスタンス
   */
  ShippingDetailComposition load(String shippingNo, Long shippingDetailNo, Long compositionNo);

  /**
   * 主キー列の値を指定して出荷明細構成品が既に存在するかどうかを返します。
   *
   * @param shippingNo 出荷番号
   * @param shippingDetailNo 出荷明細番号
   * @param compositionNo 構成品項番
   * @return 主キー列の値に対応するShippingDetailCompositionの行が存在すればtrue
   */
  boolean exists(String shippingNo, Long shippingDetailNo, Long compositionNo);

  /**
   * 新規ShippingDetailCompositionをデータベースに追加します。
   *
   * @param obj 追加対象のShippingDetailComposition
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(ShippingDetailComposition obj, LoginInfo loginInfo);

  /**
   * 出荷明細構成品を更新します。
   *
   * @param obj 更新対象のShippingDetailComposition
   * @param loginInfo ログイン情報
   */
  void update(ShippingDetailComposition obj, LoginInfo loginInfo);

  /**
   * 出荷明細構成品を削除します。
   *
   * @param obj 削除対象のShippingDetailComposition
   * @param loginInfo ログイン情報
   */
  void delete(ShippingDetailComposition obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して出荷明細構成品を削除します。
   *
   * @param shippingNo 出荷番号
   * @param shippingDetailNo 出荷明細番号
   * @param compositionNo 構成品項番
   */
  void delete(String shippingNo, Long shippingDetailNo, Long compositionNo);

  /**
   * Queryオブジェクトを指定して出荷明細構成品のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するShippingDetailCompositionのリスト
   */
  List<ShippingDetailComposition> findByQuery(Query query);

  /**
   * SQLを指定して出荷明細構成品のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するShippingDetailCompositionのリスト
   */
  List<ShippingDetailComposition> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のShippingDetailCompositionのリスト
   */
  List<ShippingDetailComposition> loadAll();

}
