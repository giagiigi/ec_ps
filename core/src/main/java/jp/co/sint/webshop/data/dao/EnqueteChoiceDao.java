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
import jp.co.sint.webshop.data.dto.EnqueteChoice;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「アンケート選択肢名(ENQUETE_CHOICE)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface EnqueteChoiceDao extends GenericDao<EnqueteChoice, Long> {

  /**
   * 指定されたorm_rowidを持つアンケート選択肢名のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するEnqueteChoiceのインスタンス
   */
  EnqueteChoice loadByRowid(Long id);

  /**
   * 主キー列の値を指定してアンケート選択肢名のインスタンスを取得します。
   *
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param enqueteChoicesNo アンケート選択肢番号
   * @return 主キー列の値に対応するEnqueteChoiceのインスタンス
   */
  EnqueteChoice load(String enqueteCode, Long enqueteQuestionNo, Long enqueteChoicesNo);

  /**
   * 主キー列の値を指定してアンケート選択肢名が既に存在するかどうかを返します。
   *
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param enqueteChoicesNo アンケート選択肢番号
   * @return 主キー列の値に対応するEnqueteChoiceの行が存在すればtrue
   */
  boolean exists(String enqueteCode, Long enqueteQuestionNo, Long enqueteChoicesNo);

  /**
   * 新規EnqueteChoiceをデータベースに追加します。
   *
   * @param obj 追加対象のEnqueteChoice
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(EnqueteChoice obj, LoginInfo loginInfo);

  /**
   * アンケート選択肢名を更新します。
   *
   * @param obj 更新対象のEnqueteChoice
   * @param loginInfo ログイン情報
   */
  void update(EnqueteChoice obj, LoginInfo loginInfo);

  /**
   * アンケート選択肢名を削除します。
   *
   * @param obj 削除対象のEnqueteChoice
   * @param loginInfo ログイン情報
   */
  void delete(EnqueteChoice obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してアンケート選択肢名を削除します。
   *
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param enqueteChoicesNo アンケート選択肢番号
   */
  void delete(String enqueteCode, Long enqueteQuestionNo, Long enqueteChoicesNo);

  /**
   * Queryオブジェクトを指定してアンケート選択肢名のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するEnqueteChoiceのリスト
   */
  List<EnqueteChoice> findByQuery(Query query);

  /**
   * SQLを指定してアンケート選択肢名のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するEnqueteChoiceのリスト
   */
  List<EnqueteChoice> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のEnqueteChoiceのリスト
   */
  List<EnqueteChoice> loadAll();

}
