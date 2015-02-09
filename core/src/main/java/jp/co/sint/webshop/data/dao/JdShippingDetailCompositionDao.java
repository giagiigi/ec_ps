//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.JdShippingDetailComposition;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「出荷明細(TMALL_SHIPPING_DETAIL)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface JdShippingDetailCompositionDao extends GenericDao<JdShippingDetailComposition, Long> {

  /**
   * 指定されたorm_rowidを持つ出荷明細のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するTmallShippingDetailのインスタンス
   */
  JdShippingDetailComposition loadByRowid(Long id);

  /**
   * 主キー列の値を指定して出荷明細のインスタンスを取得します。
   *
   * @param shippingNo 出荷番号
   * @param TmallShippingDetailNo 出荷明細番号
   * @return 主キー列の値に対応するTmallShippingDetailのインスタンス
   */
  JdShippingDetailComposition load(String shippingNo, Long jdShippingDetailNo, Long compositionNo);

  /**
   * 主キー列の値を指定して出荷明細が既に存在するかどうかを返します。
   *
   * @param shippingNo 出荷番号
   * @param TmallShippingDetailNo 出荷明細番号
   * @return 主キー列の値に対応するTmallShippingDetailの行が存在すればtrue
   */
  boolean exists(String shippingNo, Long jdShippingDetailNo, Long compositionNo);

  /**
   * 新規TmallShippingDetailをデータベースに追加します。
   *
   * @param obj 追加対象のTmallShippingDetail
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(JdShippingDetailComposition obj, LoginInfo loginInfo);

  /**
   * 出荷明細を更新します。
   *
   * @param obj 更新対象のTmallShippingDetail
   * @param loginInfo ログイン情報
   */
  void update(JdShippingDetailComposition obj, LoginInfo loginInfo);

  /**
   * 出荷明細を削除します。
   *
   * @param obj 削除対象のTmallShippingDetail
   * @param loginInfo ログイン情報
   */
  void delete(JdShippingDetailComposition obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して出荷明細を削除します。
   *
   * @param shippingNo 出荷番号
   * @param TmallShippingDetailNo 出荷明細番号
   */
  void delete(String shippingNo, Long jdShippingDetailNo,Long compositionNo);

  /**
   * Queryオブジェクトを指定して出荷明細のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するTmallShippingDetailのリスト
   */
  List<JdShippingDetailComposition> findByQuery(Query query);

  /**
   * SQLを指定して出荷明細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するTmallShippingDetailのリスト
   */
  List<JdShippingDetailComposition> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のTmallShippingDetailのリスト
   */
  List<JdShippingDetailComposition> loadAll();

}
