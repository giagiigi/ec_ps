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
import jp.co.sint.webshop.data.dto.MailMagazineSubscriber;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「メールマガジン購読者(MAIL_MAGAZINE_SUBSCRIBER)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface MailMagazineSubscriberDao extends GenericDao<MailMagazineSubscriber, Long> {

  /**
   * 指定されたorm_rowidを持つメールマガジン購読者のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するMailMagazineSubscriberのインスタンス
   */
  MailMagazineSubscriber loadByRowid(Long id);

  /**
   * 主キー列の値を指定してメールマガジン購読者のインスタンスを取得します。
   *
   * @param mailMagazineCode メールマガジンコード
   * @param email メールアドレス
   * @return 主キー列の値に対応するMailMagazineSubscriberのインスタンス
   */
  MailMagazineSubscriber load(String mailMagazineCode, String email);

  /**
   * 主キー列の値を指定してメールマガジン購読者が既に存在するかどうかを返します。
   *
   * @param mailMagazineCode メールマガジンコード
   * @param email メールアドレス
   * @return 主キー列の値に対応するMailMagazineSubscriberの行が存在すればtrue
   */
  boolean exists(String mailMagazineCode, String email);

  /**
   * 新規MailMagazineSubscriberをデータベースに追加します。
   *
   * @param obj 追加対象のMailMagazineSubscriber
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(MailMagazineSubscriber obj, LoginInfo loginInfo);

  /**
   * メールマガジン購読者を更新します。
   *
   * @param obj 更新対象のMailMagazineSubscriber
   * @param loginInfo ログイン情報
   */
  void update(MailMagazineSubscriber obj, LoginInfo loginInfo);

  /**
   * メールマガジン購読者を削除します。
   *
   * @param obj 削除対象のMailMagazineSubscriber
   * @param loginInfo ログイン情報
   */
  void delete(MailMagazineSubscriber obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してメールマガジン購読者を削除します。
   *
   * @param mailMagazineCode メールマガジンコード
   * @param email メールアドレス
   */
  void delete(String mailMagazineCode, String email);

  /**
   * Queryオブジェクトを指定してメールマガジン購読者のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するMailMagazineSubscriberのリスト
   */
  List<MailMagazineSubscriber> findByQuery(Query query);

  /**
   * SQLを指定してメールマガジン購読者のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するMailMagazineSubscriberのリスト
   */
  List<MailMagazineSubscriber> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のMailMagazineSubscriberのリスト
   */
  List<MailMagazineSubscriber> loadAll();

}
