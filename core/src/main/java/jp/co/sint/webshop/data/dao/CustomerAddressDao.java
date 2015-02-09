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
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「顧客アドレス帳(CUSTOMER_ADDRESS)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CustomerAddressDao extends GenericDao<CustomerAddress, Long> {

  /**
   * 指定されたorm_rowidを持つ顧客アドレス帳のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCustomerAddressのインスタンス
   */
  CustomerAddress loadByRowid(Long id);

  /**
   * 主キー列の値を指定して顧客アドレス帳のインスタンスを取得します。
   *
   * @param customerCode 顧客コード
   * @param addressNo アドレス帳番号
   * @return 主キー列の値に対応するCustomerAddressのインスタンス
   */
  CustomerAddress load(String customerCode, Long addressNo);

  /**
   * 主キー列の値を指定して顧客アドレス帳が既に存在するかどうかを返します。
   *
   * @param customerCode 顧客コード
   * @param addressNo アドレス帳番号
   * @return 主キー列の値に対応するCustomerAddressの行が存在すればtrue
   */
  boolean exists(String customerCode, Long addressNo);

  /**
   * 新規CustomerAddressをデータベースに追加します。
   *
   * @param obj 追加対象のCustomerAddress
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CustomerAddress obj, LoginInfo loginInfo);

  /**
   * 顧客アドレス帳を更新します。
   *
   * @param obj 更新対象のCustomerAddress
   * @param loginInfo ログイン情報
   */
  void update(CustomerAddress obj, LoginInfo loginInfo);

  /**
   * 顧客アドレス帳を削除します。
   *
   * @param obj 削除対象のCustomerAddress
   * @param loginInfo ログイン情報
   */
  void delete(CustomerAddress obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して顧客アドレス帳を削除します。
   *
   * @param customerCode 顧客コード
   * @param addressNo アドレス帳番号
   */
  void delete(String customerCode, Long addressNo);

  /**
   * Queryオブジェクトを指定して顧客アドレス帳のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerAddressのリスト
   */
  List<CustomerAddress> findByQuery(Query query);

  /**
   * SQLを指定して顧客アドレス帳のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerAddressのリスト
   */
  List<CustomerAddress> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCustomerAddressのリスト
   */
  List<CustomerAddress> loadAll();

}
