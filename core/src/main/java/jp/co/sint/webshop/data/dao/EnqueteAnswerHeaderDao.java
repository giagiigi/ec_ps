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
import jp.co.sint.webshop.data.dto.EnqueteAnswerHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「アンケート回答ヘッダ(ENQUETE_ANSWER_HEADER)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface EnqueteAnswerHeaderDao extends GenericDao<EnqueteAnswerHeader, Long> {

  /**
   * 指定されたorm_rowidを持つアンケート回答ヘッダのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するEnqueteAnswerHeaderのインスタンス
   */
  EnqueteAnswerHeader loadByRowid(Long id);

  /**
   * 主キー列の値を指定してアンケート回答ヘッダのインスタンスを取得します。
   *
   * @param customerCode 顧客コード
   * @param enqueteCode アンケートコード
   * @return 主キー列の値に対応するEnqueteAnswerHeaderのインスタンス
   */
  EnqueteAnswerHeader load(String customerCode, String enqueteCode);

  /**
   * 主キー列の値を指定してアンケート回答ヘッダが既に存在するかどうかを返します。
   *
   * @param customerCode 顧客コード
   * @param enqueteCode アンケートコード
   * @return 主キー列の値に対応するEnqueteAnswerHeaderの行が存在すればtrue
   */
  boolean exists(String customerCode, String enqueteCode);

  /**
   * 新規EnqueteAnswerHeaderをデータベースに追加します。
   *
   * @param obj 追加対象のEnqueteAnswerHeader
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(EnqueteAnswerHeader obj, LoginInfo loginInfo);

  /**
   * アンケート回答ヘッダを更新します。
   *
   * @param obj 更新対象のEnqueteAnswerHeader
   * @param loginInfo ログイン情報
   */
  void update(EnqueteAnswerHeader obj, LoginInfo loginInfo);

  /**
   * アンケート回答ヘッダを削除します。
   *
   * @param obj 削除対象のEnqueteAnswerHeader
   * @param loginInfo ログイン情報
   */
  void delete(EnqueteAnswerHeader obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してアンケート回答ヘッダを削除します。
   *
   * @param customerCode 顧客コード
   * @param enqueteCode アンケートコード
   */
  void delete(String customerCode, String enqueteCode);

  /**
   * Queryオブジェクトを指定してアンケート回答ヘッダのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するEnqueteAnswerHeaderのリスト
   */
  List<EnqueteAnswerHeader> findByQuery(Query query);

  /**
   * SQLを指定してアンケート回答ヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するEnqueteAnswerHeaderのリスト
   */
  List<EnqueteAnswerHeader> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のEnqueteAnswerHeaderのリスト
   */
  List<EnqueteAnswerHeader> loadAll();

}
