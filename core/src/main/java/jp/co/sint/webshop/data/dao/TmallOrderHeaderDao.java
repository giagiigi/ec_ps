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
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「受注ヘッダ(ORDER_HEADER)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface TmallOrderHeaderDao extends GenericDao<TmallOrderHeader, Long> {

  /**
   * 指定されたorm_rowidを持つ受注ヘッダのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するOrderHeaderのインスタンス
   */
  TmallOrderHeader loadByRowid(Long id);

  /**
   * 主キー列の値を指定して受注ヘッダのインスタンスを取得します。
   *
   * @param orderNo 受注番号
   * @return 主キー列の値に対応するOrderHeaderのインスタンス
   */
  TmallOrderHeader load(String orderNo);
  /**
   * 主キー列の値を指定して受注ヘッダのインスタンスを取得します。
   *
   * @param TmallId 受注番号
   * @return 主キー列の値に対応するTmallOrderHeaderのインスタンス
   */
  TmallOrderHeader loadByTid(String tid);
  /**
   * 主キー列の値を指定して受注ヘッダが既に存在するかどうかを返します。
   *
   * @param orderNo 受注番号
   * @return 主キー列の値に対応するOrderHeaderの行が存在すればtrue
   */
  boolean exists(String orderNo);
  /**
   * 主キー列の値を指定して受注ヘッダが既に存在するかどうかを返します。
   *
   * @param TmallId 受注番号
   * @return 主キー列の値に対応するTmallOrderHeaderの行が存在すればtrue
   */
  boolean existsTid(String tid) ;
  /**
   * 新規OrderHeaderをデータベースに追加します。
   *
   * @param obj 追加対象のOrderHeader
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(TmallOrderHeader obj, LoginInfo loginInfo);

  /**
   * 受注ヘッダを更新します。
   *
   * @param obj 更新対象のOrderHeader
   * @param loginInfo ログイン情報
   */
  void update(TmallOrderHeader obj, LoginInfo loginInfo);

  /**
   * 受注ヘッダを削除します。
   *
   * @param obj 削除対象のOrderHeader
   * @param loginInfo ログイン情報
   */
  void delete(TmallOrderHeader obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して受注ヘッダを削除します。
   *
   * @param orderNo 受注番号
   */
  void delete(String orderNo);

  /**
   * Queryオブジェクトを指定して受注ヘッダのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するOrderHeaderのリスト
   */
  List<TmallOrderHeader> findByQuery(Query query);

  /**
   * SQLを指定して受注ヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するOrderHeaderのリスト
   */
  List<TmallOrderHeader> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のOrderHeaderのリスト
   */
  List<TmallOrderHeader> loadAll();

}
