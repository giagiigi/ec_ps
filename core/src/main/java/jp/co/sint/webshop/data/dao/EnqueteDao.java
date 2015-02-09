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
import jp.co.sint.webshop.data.dto.Enquete;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「アンケート(ENQUETE)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface EnqueteDao extends GenericDao<Enquete, Long> {

  /**
   * 指定されたorm_rowidを持つアンケートのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するEnqueteのインスタンス
   */
  Enquete loadByRowid(Long id);

  /**
   * 主キー列の値を指定してアンケートのインスタンスを取得します。
   *
   * @param enqueteCode アンケートコード
   * @return 主キー列の値に対応するEnqueteのインスタンス
   */
  Enquete load(String enqueteCode);

  /**
   * 主キー列の値を指定してアンケートが既に存在するかどうかを返します。
   *
   * @param enqueteCode アンケートコード
   * @return 主キー列の値に対応するEnqueteの行が存在すればtrue
   */
  boolean exists(String enqueteCode);

  /**
   * 新規Enqueteをデータベースに追加します。
   *
   * @param obj 追加対象のEnquete
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(Enquete obj, LoginInfo loginInfo);

  /**
   * アンケートを更新します。
   *
   * @param obj 更新対象のEnquete
   * @param loginInfo ログイン情報
   */
  void update(Enquete obj, LoginInfo loginInfo);

  /**
   * アンケートを削除します。
   *
   * @param obj 削除対象のEnquete
   * @param loginInfo ログイン情報
   */
  void delete(Enquete obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してアンケートを削除します。
   *
   * @param enqueteCode アンケートコード
   */
  void delete(String enqueteCode);

  /**
   * Queryオブジェクトを指定してアンケートのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するEnqueteのリスト
   */
  List<Enquete> findByQuery(Query query);

  /**
   * SQLを指定してアンケートのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するEnqueteのリスト
   */
  List<Enquete> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のEnqueteのリスト
   */
  List<Enquete> loadAll();

}
