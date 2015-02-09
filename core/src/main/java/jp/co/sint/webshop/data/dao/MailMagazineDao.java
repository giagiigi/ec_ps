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
import jp.co.sint.webshop.data.dto.MailMagazine;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「メールマガジン(MAIL_MAGAZINE)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface MailMagazineDao extends GenericDao<MailMagazine, Long> {

  /**
   * 指定されたorm_rowidを持つメールマガジンのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するMailMagazineのインスタンス
   */
  MailMagazine loadByRowid(Long id);

  /**
   * 主キー列の値を指定してメールマガジンのインスタンスを取得します。
   *
   * @param mailMagazineCode メールマガジンコード
   * @return 主キー列の値に対応するMailMagazineのインスタンス
   */
  MailMagazine load(String mailMagazineCode);

  /**
   * 主キー列の値を指定してメールマガジンが既に存在するかどうかを返します。
   *
   * @param mailMagazineCode メールマガジンコード
   * @return 主キー列の値に対応するMailMagazineの行が存在すればtrue
   */
  boolean exists(String mailMagazineCode);

  /**
   * 新規MailMagazineをデータベースに追加します。
   *
   * @param obj 追加対象のMailMagazine
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(MailMagazine obj, LoginInfo loginInfo);

  /**
   * メールマガジンを更新します。
   *
   * @param obj 更新対象のMailMagazine
   * @param loginInfo ログイン情報
   */
  void update(MailMagazine obj, LoginInfo loginInfo);

  /**
   * メールマガジンを削除します。
   *
   * @param obj 削除対象のMailMagazine
   * @param loginInfo ログイン情報
   */
  void delete(MailMagazine obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してメールマガジンを削除します。
   *
   * @param mailMagazineCode メールマガジンコード
   */
  void delete(String mailMagazineCode);

  /**
   * Queryオブジェクトを指定してメールマガジンのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するMailMagazineのリスト
   */
  List<MailMagazine> findByQuery(Query query);

  /**
   * SQLを指定してメールマガジンのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するMailMagazineのリスト
   */
  List<MailMagazine> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のMailMagazineのリスト
   */
  List<MailMagazine> loadAll();

}
