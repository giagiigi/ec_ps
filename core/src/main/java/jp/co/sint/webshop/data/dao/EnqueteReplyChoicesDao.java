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
import jp.co.sint.webshop.data.dto.EnqueteReplyChoices;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「アンケート回答選択式(ENQUETE_REPLY_CHOICES)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface EnqueteReplyChoicesDao extends GenericDao<EnqueteReplyChoices, Long> {

  /**
   * 指定されたorm_rowidを持つアンケート回答選択式のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するEnqueteReplyChoicesのインスタンス
   */
  EnqueteReplyChoices loadByRowid(Long id);

  /**
   * 主キー列の値を指定してアンケート回答選択式のインスタンスを取得します。
   *
   * @param customerCode 顧客コード
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param enqueteChoicesNo アンケート選択肢番号
   * @return 主キー列の値に対応するEnqueteReplyChoicesのインスタンス
   */
  EnqueteReplyChoices load(String customerCode, String enqueteCode, Long enqueteQuestionNo, Long enqueteChoicesNo);

  /**
   * 主キー列の値を指定してアンケート回答選択式が既に存在するかどうかを返します。
   *
   * @param customerCode 顧客コード
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param enqueteChoicesNo アンケート選択肢番号
   * @return 主キー列の値に対応するEnqueteReplyChoicesの行が存在すればtrue
   */
  boolean exists(String customerCode, String enqueteCode, Long enqueteQuestionNo, Long enqueteChoicesNo);

  /**
   * 新規EnqueteReplyChoicesをデータベースに追加します。
   *
   * @param obj 追加対象のEnqueteReplyChoices
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(EnqueteReplyChoices obj, LoginInfo loginInfo);

  /**
   * アンケート回答選択式を更新します。
   *
   * @param obj 更新対象のEnqueteReplyChoices
   * @param loginInfo ログイン情報
   */
  void update(EnqueteReplyChoices obj, LoginInfo loginInfo);

  /**
   * アンケート回答選択式を削除します。
   *
   * @param obj 削除対象のEnqueteReplyChoices
   * @param loginInfo ログイン情報
   */
  void delete(EnqueteReplyChoices obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してアンケート回答選択式を削除します。
   *
   * @param customerCode 顧客コード
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param enqueteChoicesNo アンケート選択肢番号
   */
  void delete(String customerCode, String enqueteCode, Long enqueteQuestionNo, Long enqueteChoicesNo);

  /**
   * Queryオブジェクトを指定してアンケート回答選択式のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するEnqueteReplyChoicesのリスト
   */
  List<EnqueteReplyChoices> findByQuery(Query query);

  /**
   * SQLを指定してアンケート回答選択式のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するEnqueteReplyChoicesのリスト
   */
  List<EnqueteReplyChoices> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のEnqueteReplyChoicesのリスト
   */
  List<EnqueteReplyChoices> loadAll();

}
