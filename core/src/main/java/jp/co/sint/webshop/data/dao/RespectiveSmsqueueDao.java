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
import jp.co.sint.webshop.data.dto.RespectiveSmsqueue;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「個別配信メールキュー(RESPECTIVE_SMSQUEUE)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface RespectiveSmsqueueDao extends GenericDao<RespectiveSmsqueue, Long> {

  /**
   * 指定されたorm_rowidを持つ個別配信メールキューのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するRespectiveSmsqueueのインスタンス
   */
  RespectiveSmsqueue loadByRowid(Long id);

  /**
   * 主キー列の値を指定して個別配信メールキューのインスタンスを取得します。
   *
   * @param smsQueueId メールキューID
   * @return 主キー列の値に対応するRespectiveSmsqueueのインスタンス
   */
  RespectiveSmsqueue load(Long smsQueueId);

  /**
   * 主キー列の値を指定して個別配信メールキューが既に存在するかどうかを返します。
   *
   * @param smsQueueId メールキューID
   * @return 主キー列の値に対応するRespectiveSmsqueueの行が存在すればtrue
   */
  boolean exists(Long smsQueueId);

  /**
   * 新規RespectiveSmsqueueをデータベースに追加します。
   *
   * @param obj 追加対象のRespectiveSmsqueue
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(RespectiveSmsqueue obj, LoginInfo loginInfo);

  /**
   * 個別配信メールキューを更新します。
   *
   * @param obj 更新対象のRespectiveSmsqueue
   * @param loginInfo ログイン情報
   */
  void update(RespectiveSmsqueue obj, LoginInfo loginInfo);


  /**
   * Queryオブジェクトを指定して個別配信メールキューのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRespectiveSmsqueueのリスト
   */
  List<RespectiveSmsqueue> findByQuery(Query query);

  /**
   * SQLを指定して個別配信メールキューのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRespectiveSmsqueueのリスト
   */
  List<RespectiveSmsqueue> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のRespectiveSmsqueueのリスト
   */
  List<RespectiveSmsqueue> loadAll();

}
