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
import jp.co.sint.webshop.data.dto.MailTemplateDetail;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「メールテンプレート明細(MAIL_TEMPLATE_DETAIL)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface MailTemplateDetailDao extends GenericDao<MailTemplateDetail, Long> {

  /**
   * 指定されたorm_rowidを持つメールテンプレート明細のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するMailTemplateDetailのインスタンス
   */
  MailTemplateDetail loadByRowid(Long id);

  /**
   * 主キー列の値を指定してメールテンプレート明細のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param mailType メールタイプ
   * @param mailTemplateNo メールテンプレート番号
   * @param mailTemplateBranchNo メールテンプレート枝番
   * @return 主キー列の値に対応するMailTemplateDetailのインスタンス
   */
  MailTemplateDetail load(String shopCode, String mailType, Long mailTemplateNo, Long mailTemplateBranchNo);

  /**
   * 主キー列の値を指定してメールテンプレート明細が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param mailType メールタイプ
   * @param mailTemplateNo メールテンプレート番号
   * @param mailTemplateBranchNo メールテンプレート枝番
   * @return 主キー列の値に対応するMailTemplateDetailの行が存在すればtrue
   */
  boolean exists(String shopCode, String mailType, Long mailTemplateNo, Long mailTemplateBranchNo);

  /**
   * 新規MailTemplateDetailをデータベースに追加します。
   *
   * @param obj 追加対象のMailTemplateDetail
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(MailTemplateDetail obj, LoginInfo loginInfo);

  /**
   * メールテンプレート明細を更新します。
   *
   * @param obj 更新対象のMailTemplateDetail
   * @param loginInfo ログイン情報
   */
  void update(MailTemplateDetail obj, LoginInfo loginInfo);

  /**
   * メールテンプレート明細を削除します。
   *
   * @param obj 削除対象のMailTemplateDetail
   * @param loginInfo ログイン情報
   */
  void delete(MailTemplateDetail obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してメールテンプレート明細を削除します。
   *
   * @param shopCode ショップコード
   * @param mailType メールタイプ
   * @param mailTemplateNo メールテンプレート番号
   * @param mailTemplateBranchNo メールテンプレート枝番
   */
  void delete(String shopCode, String mailType, Long mailTemplateNo, Long mailTemplateBranchNo);

  /**
   * Queryオブジェクトを指定してメールテンプレート明細のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するMailTemplateDetailのリスト
   */
  List<MailTemplateDetail> findByQuery(Query query);

  /**
   * SQLを指定してメールテンプレート明細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するMailTemplateDetailのリスト
   */
  List<MailTemplateDetail> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のMailTemplateDetailのリスト
   */
  List<MailTemplateDetail> loadAll();

}
