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
import jp.co.sint.webshop.data.dto.EnqueteReplyInput;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「アンケート回答入力式(ENQUETE_REPLY_INPUT)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface EnqueteReplyInputDao extends GenericDao<EnqueteReplyInput, Long> {

  /**
   * 指定されたorm_rowidを持つアンケート回答入力式のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するEnqueteReplyInputのインスタンス
   */
  EnqueteReplyInput loadByRowid(Long id);

  /**
   * 主キー列の値を指定してアンケート回答入力式のインスタンスを取得します。
   *
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するEnqueteReplyInputのインスタンス
   */
  EnqueteReplyInput load(String enqueteCode, Long enqueteQuestionNo, String customerCode);

  /**
   * 主キー列の値を指定してアンケート回答入力式が既に存在するかどうかを返します。
   *
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するEnqueteReplyInputの行が存在すればtrue
   */
  boolean exists(String enqueteCode, Long enqueteQuestionNo, String customerCode);

  /**
   * 新規EnqueteReplyInputをデータベースに追加します。
   *
   * @param obj 追加対象のEnqueteReplyInput
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(EnqueteReplyInput obj, LoginInfo loginInfo);

  /**
   * アンケート回答入力式を更新します。
   *
   * @param obj 更新対象のEnqueteReplyInput
   * @param loginInfo ログイン情報
   */
  void update(EnqueteReplyInput obj, LoginInfo loginInfo);

  /**
   * アンケート回答入力式を削除します。
   *
   * @param obj 削除対象のEnqueteReplyInput
   * @param loginInfo ログイン情報
   */
  void delete(EnqueteReplyInput obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してアンケート回答入力式を削除します。
   *
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param customerCode 顧客コード
   */
  void delete(String enqueteCode, Long enqueteQuestionNo, String customerCode);

  /**
   * Queryオブジェクトを指定してアンケート回答入力式のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するEnqueteReplyInputのリスト
   */
  List<EnqueteReplyInput> findByQuery(Query query);

  /**
   * SQLを指定してアンケート回答入力式のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するEnqueteReplyInputのリスト
   */
  List<EnqueteReplyInput> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のEnqueteReplyInputのリスト
   */
  List<EnqueteReplyInput> loadAll();

}
