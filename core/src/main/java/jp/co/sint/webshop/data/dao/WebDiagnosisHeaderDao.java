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
import jp.co.sint.webshop.data.dto.WebDiagnosisHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「肌肤诊断ヘッダ(WebDiagnosisHeader)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface WebDiagnosisHeaderDao extends GenericDao<WebDiagnosisHeader, Long> {

  /**
   * 指定されたorm_rowidを持つ肌肤诊断ヘッダのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するWebDiagnosisHeaderのインスタンス
   */
  WebDiagnosisHeader loadByRowid(Long id);

  /**
   * 主キー列の値を指定して肌肤诊断ヘッダのインスタンスを取得します。
   *
   * @param webDiagnosisHeaderNo 受注番号
   * @return 主キー列の値に対応するWebDiagnosisHeaderのインスタンス
   */
  WebDiagnosisHeader load(String webDiagnosisHeaderNo);

  /**
   * 主キー列の値を指定して肌肤诊断ヘッダが既に存在するかどうかを返します。
   *
   * @param webDiagnosisHeaderNo 诊断番号
   * @return 主キー列の値に対応するWebDiagnosisHeaderの行が存在すればtrue
   */
  boolean exists(String webDiagnosisHeaderNo);

  /**
   * 新規WebDiagnosisHeaderをデータベースに追加します。
   *
   * @param obj 追加対象のWebDiagnosisHeader
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(WebDiagnosisHeader obj, LoginInfo loginInfo);

  /**
   * 肌肤诊断ヘッダを更新します。
   *
   * @param obj 更新対象のWebDiagnosisHeader
   * @param loginInfo ログイン情報
   */
  void update(WebDiagnosisHeader obj, LoginInfo loginInfo);

  /**
   * 肌肤诊断ヘッダを削除します。
   *
   * @param obj 削除対象のWebDiagnosisHeader
   * @param loginInfo ログイン情報
   */
  void delete(WebDiagnosisHeader obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して受注ヘッダを削除します。
   *
   * @param webDiagnosisHeaderNo 受注番号
   */
  void delete(String webDiagnosisHeaderNo);

  /**
   * Queryオブジェクトを指定して肌肤诊断ヘッダのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するWebDiagnosisHeaderのリスト
   */
  List<WebDiagnosisHeader> findByQuery(Query query);

  /**
   * SQLを指定して肌肤诊断ヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するWebDiagnosisHeaderのリスト
   */
  List<WebDiagnosisHeader> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のWebDiagnosisHeaderのリスト
   */
  List<WebDiagnosisHeader> loadAll();
  
  /**
   * 主キー列の値を指定して肌肤诊断ヘッダが既に存在するかどうかを返します。
   *
   * @param webDiagnosisHeaderNo 诊断番号
   * @param customerCode 顾客番号
   * @return 主キー列の値に対応するWebDiagnosisHeaderの行が存在すればtrue
   */
  boolean exists(String webDiagnosisHeaderNo, String customerCode);
  

}
