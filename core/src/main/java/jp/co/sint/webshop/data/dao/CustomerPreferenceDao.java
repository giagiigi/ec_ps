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
import jp.co.sint.webshop.data.dto.CustomerPreference;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「顧客嗜好(CUSTOMER_PREFERENCE)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CustomerPreferenceDao extends GenericDao<CustomerPreference, Long> {

  /**
   * 指定されたorm_rowidを持つ顧客嗜好のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCustomerPreferenceのインスタンス
   */
  CustomerPreference loadByRowid(Long id);

  /**
   * 主キー列の値を指定して顧客嗜好のインスタンスを取得します。
   *
   * @param customerPreferenceId 顧客嗜好ID
   * @return 主キー列の値に対応するCustomerPreferenceのインスタンス
   */
  CustomerPreference load(Long customerPreferenceId);

  /**
   * 主キー列の値を指定して顧客嗜好が既に存在するかどうかを返します。
   *
   * @param customerPreferenceId 顧客嗜好ID
   * @return 主キー列の値に対応するCustomerPreferenceの行が存在すればtrue
   */
  boolean exists(Long customerPreferenceId);

  /**
   * 新規CustomerPreferenceをデータベースに追加します。
   *
   * @param obj 追加対象のCustomerPreference
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CustomerPreference obj, LoginInfo loginInfo);

  /**
   * 顧客嗜好を更新します。
   *
   * @param obj 更新対象のCustomerPreference
   * @param loginInfo ログイン情報
   */
  void update(CustomerPreference obj, LoginInfo loginInfo);

  /**
   * 顧客嗜好を削除します。
   *
   * @param obj 削除対象のCustomerPreference
   * @param loginInfo ログイン情報
   */
  void delete(CustomerPreference obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して顧客嗜好を削除します。
   *
   * @param customerPreferenceId 顧客嗜好ID
   */
  void delete(Long customerPreferenceId);

  /**
   * Queryオブジェクトを指定して顧客嗜好のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerPreferenceのリスト
   */
  List<CustomerPreference> findByQuery(Query query);

  /**
   * SQLを指定して顧客嗜好のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerPreferenceのリスト
   */
  List<CustomerPreference> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCustomerPreferenceのリスト
   */
  List<CustomerPreference> loadAll();

}
