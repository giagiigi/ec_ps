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
import jp.co.sint.webshop.data.dto.BroadcastMailqueueHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「同報配信メールキューヘッダ(BROADCAST_MAILQUEUE_HEADER)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface BroadcastMailqueueHeaderDao extends GenericDao<BroadcastMailqueueHeader, Long> {

  /**
   * 指定されたorm_rowidを持つ同報配信メールキューヘッダのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するBroadcastMailqueueHeaderのインスタンス
   */
  BroadcastMailqueueHeader loadByRowid(Long id);

  /**
   * 主キー列の値を指定して同報配信メールキューヘッダのインスタンスを取得します。
   *
   * @param mailQueueId メールキューID
   * @return 主キー列の値に対応するBroadcastMailqueueHeaderのインスタンス
   */
  BroadcastMailqueueHeader load(Long mailQueueId);

  /**
   * 主キー列の値を指定して同報配信メールキューヘッダが既に存在するかどうかを返します。
   *
   * @param mailQueueId メールキューID
   * @return 主キー列の値に対応するBroadcastMailqueueHeaderの行が存在すればtrue
   */
  boolean exists(Long mailQueueId);

  /**
   * 新規BroadcastMailqueueHeaderをデータベースに追加します。
   *
   * @param obj 追加対象のBroadcastMailqueueHeader
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(BroadcastMailqueueHeader obj, LoginInfo loginInfo);

  /**
   * 同報配信メールキューヘッダを更新します。
   *
   * @param obj 更新対象のBroadcastMailqueueHeader
   * @param loginInfo ログイン情報
   */
  void update(BroadcastMailqueueHeader obj, LoginInfo loginInfo);

  /**
   * 同報配信メールキューヘッダを削除します。
   *
   * @param obj 削除対象のBroadcastMailqueueHeader
   * @param loginInfo ログイン情報
   */
  void delete(BroadcastMailqueueHeader obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して同報配信メールキューヘッダを削除します。
   *
   * @param mailQueueId メールキューID
   */
  void delete(Long mailQueueId);

  /**
   * Queryオブジェクトを指定して同報配信メールキューヘッダのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するBroadcastMailqueueHeaderのリスト
   */
  List<BroadcastMailqueueHeader> findByQuery(Query query);

  /**
   * SQLを指定して同報配信メールキューヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するBroadcastMailqueueHeaderのリスト
   */
  List<BroadcastMailqueueHeader> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のBroadcastMailqueueHeaderのリスト
   */
  List<BroadcastMailqueueHeader> loadAll();

}
