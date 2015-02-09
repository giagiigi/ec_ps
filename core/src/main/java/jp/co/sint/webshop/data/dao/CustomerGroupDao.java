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
import jp.co.sint.webshop.data.dto.CustomerGroup;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「顧客グループ(CUSTOMER_GROUP)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CustomerGroupDao extends GenericDao<CustomerGroup, Long> {

  /**
   * 指定されたorm_rowidを持つ顧客グループのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCustomerGroupのインスタンス
   */
  CustomerGroup loadByRowid(Long id);

  /**
   * 主キー列の値を指定して顧客グループのインスタンスを取得します。
   *
   * @param customerGroupCode 顧客グループコード
   * @return 主キー列の値に対応するCustomerGroupのインスタンス
   */
  CustomerGroup load(String customerGroupCode);

  /**
   * 主キー列の値を指定して顧客グループが既に存在するかどうかを返します。
   *
   * @param customerGroupCode 顧客グループコード
   * @return 主キー列の値に対応するCustomerGroupの行が存在すればtrue
   */
  boolean exists(String customerGroupCode);

  /**
   * 新規CustomerGroupをデータベースに追加します。
   *
   * @param obj 追加対象のCustomerGroup
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CustomerGroup obj, LoginInfo loginInfo);

  /**
   * 顧客グループを更新します。
   *
   * @param obj 更新対象のCustomerGroup
   * @param loginInfo ログイン情報
   */
  void update(CustomerGroup obj, LoginInfo loginInfo);

  /**
   * 顧客グループを削除します。
   *
   * @param obj 削除対象のCustomerGroup
   * @param loginInfo ログイン情報
   */
  void delete(CustomerGroup obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して顧客グループを削除します。
   *
   * @param customerGroupCode 顧客グループコード
   */
  void delete(String customerGroupCode);

  /**
   * Queryオブジェクトを指定して顧客グループのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerGroupのリスト
   */
  List<CustomerGroup> findByQuery(Query query);

  /**
   * SQLを指定して顧客グループのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerGroupのリスト
   */
  List<CustomerGroup> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCustomerGroupのリスト
   */
  List<CustomerGroup> loadAll();

}
