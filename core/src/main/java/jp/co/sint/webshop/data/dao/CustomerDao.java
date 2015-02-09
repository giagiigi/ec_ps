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
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「顧客(CUSTOMER)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CustomerDao extends GenericDao<Customer, Long> {

  /**
   * 指定されたorm_rowidを持つ顧客のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCustomerのインスタンス
   */
  Customer loadByRowid(Long id);

  /**
   * 主キー列の値を指定して顧客のインスタンスを取得します。
   *
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するCustomerのインスタンス
   */
  Customer load(String customerCode);
  
  Customer loadByEmail(String email);

  /**
   * 主キー列の値を指定して顧客が既に存在するかどうかを返します。
   *
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するCustomerの行が存在すればtrue
   */
  boolean exists(String customerCode);

  /**
   * 新規Customerをデータベースに追加します。
   *
   * @param obj 追加対象のCustomer
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(Customer obj, LoginInfo loginInfo);

  /**
   * 顧客を更新します。
   *
   * @param obj 更新対象のCustomer
   * @param loginInfo ログイン情報
   */
  void update(Customer obj, LoginInfo loginInfo);

  /**
   * 顧客を削除します。
   *
   * @param obj 削除対象のCustomer
   * @param loginInfo ログイン情報
   */
  void delete(Customer obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して顧客を削除します。
   *
   * @param customerCode 顧客コード
   */
  void delete(String customerCode);

  /**
   * Queryオブジェクトを指定して顧客のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerのリスト
   */
  List<Customer> findByQuery(Query query);

  /**
   * SQLを指定して顧客のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerのリスト
   */
  List<Customer> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCustomerのリスト
   */
  List<Customer> loadAll();

}
