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
import jp.co.sint.webshop.data.dto.CustomerAttribute;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「顧客属性(CUSTOMER_ATTRIBUTE)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CustomerAttributeDao extends GenericDao<CustomerAttribute, Long> {

  /**
   * 指定されたorm_rowidを持つ顧客属性のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCustomerAttributeのインスタンス
   */
  CustomerAttribute loadByRowid(Long id);

  /**
   * 主キー列の値を指定して顧客属性のインスタンスを取得します。
   *
   * @param customerAttributeNo 顧客属性番号
   * @return 主キー列の値に対応するCustomerAttributeのインスタンス
   */
  CustomerAttribute load(Long customerAttributeNo);

  /**
   * 主キー列の値を指定して顧客属性が既に存在するかどうかを返します。
   *
   * @param customerAttributeNo 顧客属性番号
   * @return 主キー列の値に対応するCustomerAttributeの行が存在すればtrue
   */
  boolean exists(Long customerAttributeNo);

  /**
   * 新規CustomerAttributeをデータベースに追加します。
   *
   * @param obj 追加対象のCustomerAttribute
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CustomerAttribute obj, LoginInfo loginInfo);

  /**
   * 顧客属性を更新します。
   *
   * @param obj 更新対象のCustomerAttribute
   * @param loginInfo ログイン情報
   */
  void update(CustomerAttribute obj, LoginInfo loginInfo);

  /**
   * 顧客属性を削除します。
   *
   * @param obj 削除対象のCustomerAttribute
   * @param loginInfo ログイン情報
   */
  void delete(CustomerAttribute obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して顧客属性を削除します。
   *
   * @param customerAttributeNo 顧客属性番号
   */
  void delete(Long customerAttributeNo);

  /**
   * Queryオブジェクトを指定して顧客属性のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerAttributeのリスト
   */
  List<CustomerAttribute> findByQuery(Query query);

  /**
   * SQLを指定して顧客属性のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerAttributeのリスト
   */
  List<CustomerAttribute> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCustomerAttributeのリスト
   */
  List<CustomerAttribute> loadAll();

}
