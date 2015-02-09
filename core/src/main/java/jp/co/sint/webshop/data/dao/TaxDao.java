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
import jp.co.sint.webshop.data.dto.Tax;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「消費税(TAX)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface TaxDao extends GenericDao<Tax, Long> {

  /**
   * 指定されたorm_rowidを持つ消費税のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するTaxのインスタンス
   */
  Tax loadByRowid(Long id);

  /**
   * 主キー列の値を指定して消費税のインスタンスを取得します。
   *
   * @param taxNo 消費税番号
   * @return 主キー列の値に対応するTaxのインスタンス
   */
  Tax load(Long taxNo);

  /**
   * 主キー列の値を指定して消費税が既に存在するかどうかを返します。
   *
   * @param taxNo 消費税番号
   * @return 主キー列の値に対応するTaxの行が存在すればtrue
   */
  boolean exists(Long taxNo);

  /**
   * 新規Taxをデータベースに追加します。
   *
   * @param obj 追加対象のTax
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(Tax obj, LoginInfo loginInfo);

  /**
   * 消費税を更新します。
   *
   * @param obj 更新対象のTax
   * @param loginInfo ログイン情報
   */
  void update(Tax obj, LoginInfo loginInfo);

  /**
   * 消費税を削除します。
   *
   * @param obj 削除対象のTax
   * @param loginInfo ログイン情報
   */
  void delete(Tax obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して消費税を削除します。
   *
   * @param taxNo 消費税番号
   */
  void delete(Long taxNo);

  /**
   * Queryオブジェクトを指定して消費税のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するTaxのリスト
   */
  List<Tax> findByQuery(Query query);

  /**
   * SQLを指定して消費税のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するTaxのリスト
   */
  List<Tax> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のTaxのリスト
   */
  List<Tax> loadAll();

}
