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
import jp.co.sint.webshop.data.dto.EnqueteQuestion;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「アンケート設問(ENQUETE_QUESTION)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface EnqueteQuestionDao extends GenericDao<EnqueteQuestion, Long> {

  /**
   * 指定されたorm_rowidを持つアンケート設問のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するEnqueteQuestionのインスタンス
   */
  EnqueteQuestion loadByRowid(Long id);

  /**
   * 主キー列の値を指定してアンケート設問のインスタンスを取得します。
   *
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @return 主キー列の値に対応するEnqueteQuestionのインスタンス
   */
  EnqueteQuestion load(String enqueteCode, Long enqueteQuestionNo);

  /**
   * 主キー列の値を指定してアンケート設問が既に存在するかどうかを返します。
   *
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @return 主キー列の値に対応するEnqueteQuestionの行が存在すればtrue
   */
  boolean exists(String enqueteCode, Long enqueteQuestionNo);

  /**
   * 新規EnqueteQuestionをデータベースに追加します。
   *
   * @param obj 追加対象のEnqueteQuestion
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(EnqueteQuestion obj, LoginInfo loginInfo);

  /**
   * アンケート設問を更新します。
   *
   * @param obj 更新対象のEnqueteQuestion
   * @param loginInfo ログイン情報
   */
  void update(EnqueteQuestion obj, LoginInfo loginInfo);

  /**
   * アンケート設問を削除します。
   *
   * @param obj 削除対象のEnqueteQuestion
   * @param loginInfo ログイン情報
   */
  void delete(EnqueteQuestion obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してアンケート設問を削除します。
   *
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   */
  void delete(String enqueteCode, Long enqueteQuestionNo);

  /**
   * Queryオブジェクトを指定してアンケート設問のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するEnqueteQuestionのリスト
   */
  List<EnqueteQuestion> findByQuery(Query query);

  /**
   * SQLを指定してアンケート設問のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するEnqueteQuestionのリスト
   */
  List<EnqueteQuestion> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のEnqueteQuestionのリスト
   */
  List<EnqueteQuestion> loadAll();

}
