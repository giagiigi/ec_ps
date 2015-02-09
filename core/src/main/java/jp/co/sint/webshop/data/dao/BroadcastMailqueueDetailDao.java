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
import jp.co.sint.webshop.data.dto.BroadcastMailqueueDetail;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「同報配信メールキュー明細(BROADCAST_MAILQUEUE_DETAIL)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface BroadcastMailqueueDetailDao extends GenericDao<BroadcastMailqueueDetail, Long> {

  /**
   * 指定されたorm_rowidを持つ同報配信メールキュー明細のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するBroadcastMailqueueDetailのインスタンス
   */
  BroadcastMailqueueDetail loadByRowid(Long id);

  /**
   * 主キー列の値を指定して同報配信メールキュー明細のインスタンスを取得します。
   *
   * @param mailQueueId メールキューID
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するBroadcastMailqueueDetailのインスタンス
   */
  BroadcastMailqueueDetail load(Long mailQueueId, String customerCode);

  /**
   * 主キー列の値を指定して同報配信メールキュー明細が既に存在するかどうかを返します。
   *
   * @param mailQueueId メールキューID
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するBroadcastMailqueueDetailの行が存在すればtrue
   */
  boolean exists(Long mailQueueId, String customerCode);

  /**
   * 新規BroadcastMailqueueDetailをデータベースに追加します。
   *
   * @param obj 追加対象のBroadcastMailqueueDetail
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(BroadcastMailqueueDetail obj, LoginInfo loginInfo);

  /**
   * 同報配信メールキュー明細を更新します。
   *
   * @param obj 更新対象のBroadcastMailqueueDetail
   * @param loginInfo ログイン情報
   */
  void update(BroadcastMailqueueDetail obj, LoginInfo loginInfo);

  /**
   * 同報配信メールキュー明細を削除します。
   *
   * @param obj 削除対象のBroadcastMailqueueDetail
   * @param loginInfo ログイン情報
   */
  void delete(BroadcastMailqueueDetail obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して同報配信メールキュー明細を削除します。
   *
   * @param mailQueueId メールキューID
   * @param customerCode 顧客コード
   */
  void delete(Long mailQueueId, String customerCode);

  /**
   * Queryオブジェクトを指定して同報配信メールキュー明細のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するBroadcastMailqueueDetailのリスト
   */
  List<BroadcastMailqueueDetail> findByQuery(Query query);

  /**
   * SQLを指定して同報配信メールキュー明細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するBroadcastMailqueueDetailのリスト
   */
  List<BroadcastMailqueueDetail> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のBroadcastMailqueueDetailのリスト
   */
  List<BroadcastMailqueueDetail> loadAll();

}
