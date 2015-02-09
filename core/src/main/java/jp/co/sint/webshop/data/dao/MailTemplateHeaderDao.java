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
import jp.co.sint.webshop.data.dto.MailTemplateHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「メールテンプレートヘッダ(MAIL_TEMPLATE_HEADER)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface MailTemplateHeaderDao extends GenericDao<MailTemplateHeader, Long> {

  /**
   * 指定されたorm_rowidを持つメールテンプレートヘッダのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するMailTemplateHeaderのインスタンス
   */
  MailTemplateHeader loadByRowid(Long id);

  /**
   * 主キー列の値を指定してメールテンプレートヘッダのインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param mailType メールタイプ
   * @param mailTemplateNo メールテンプレート番号
   * @return 主キー列の値に対応するMailTemplateHeaderのインスタンス
   */
  MailTemplateHeader load(String shopCode, String mailType, Long mailTemplateNo);

  /**
   * 主キー列の値を指定してメールテンプレートヘッダが既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param mailType メールタイプ
   * @param mailTemplateNo メールテンプレート番号
   * @return 主キー列の値に対応するMailTemplateHeaderの行が存在すればtrue
   */
  boolean exists(String shopCode, String mailType, Long mailTemplateNo);

  /**
   * 新規MailTemplateHeaderをデータベースに追加します。
   *
   * @param obj 追加対象のMailTemplateHeader
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(MailTemplateHeader obj, LoginInfo loginInfo);

  /**
   * メールテンプレートヘッダを更新します。
   *
   * @param obj 更新対象のMailTemplateHeader
   * @param loginInfo ログイン情報
   */
  void update(MailTemplateHeader obj, LoginInfo loginInfo);

  /**
   * メールテンプレートヘッダを削除します。
   *
   * @param obj 削除対象のMailTemplateHeader
   * @param loginInfo ログイン情報
   */
  void delete(MailTemplateHeader obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してメールテンプレートヘッダを削除します。
   *
   * @param shopCode ショップコード
   * @param mailType メールタイプ
   * @param mailTemplateNo メールテンプレート番号
   */
  void delete(String shopCode, String mailType, Long mailTemplateNo);

  /**
   * Queryオブジェクトを指定してメールテンプレートヘッダのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するMailTemplateHeaderのリスト
   */
  List<MailTemplateHeader> findByQuery(Query query);

  /**
   * SQLを指定してメールテンプレートヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するMailTemplateHeaderのリスト
   */
  List<MailTemplateHeader> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のMailTemplateHeaderのリスト
   */
  List<MailTemplateHeader> loadAll();

}
