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
import jp.co.sint.webshop.data.dto.CustomerAttributeChoice;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「顧客属性選択肢名(CUSTOMER_ATTRIBUTE_CHOICE)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CustomerAttributeChoiceDao extends GenericDao<CustomerAttributeChoice, Long> {

  /**
   * 指定されたorm_rowidを持つ顧客属性選択肢名のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCustomerAttributeChoiceのインスタンス
   */
  CustomerAttributeChoice loadByRowid(Long id);

  /**
   * 主キー列の値を指定して顧客属性選択肢名のインスタンスを取得します。
   *
   * @param customerAttributeNo 顧客属性番号
   * @param customerAttributeChoicesNo 顧客属性選択肢番号
   * @return 主キー列の値に対応するCustomerAttributeChoiceのインスタンス
   */
  CustomerAttributeChoice load(Long customerAttributeNo, Long customerAttributeChoicesNo);

  /**
   * 主キー列の値を指定して顧客属性選択肢名が既に存在するかどうかを返します。
   *
   * @param customerAttributeNo 顧客属性番号
   * @param customerAttributeChoicesNo 顧客属性選択肢番号
   * @return 主キー列の値に対応するCustomerAttributeChoiceの行が存在すればtrue
   */
  boolean exists(Long customerAttributeNo, Long customerAttributeChoicesNo);

  /**
   * 新規CustomerAttributeChoiceをデータベースに追加します。
   *
   * @param obj 追加対象のCustomerAttributeChoice
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CustomerAttributeChoice obj, LoginInfo loginInfo);

  /**
   * 顧客属性選択肢名を更新します。
   *
   * @param obj 更新対象のCustomerAttributeChoice
   * @param loginInfo ログイン情報
   */
  void update(CustomerAttributeChoice obj, LoginInfo loginInfo);

  /**
   * 顧客属性選択肢名を削除します。
   *
   * @param obj 削除対象のCustomerAttributeChoice
   * @param loginInfo ログイン情報
   */
  void delete(CustomerAttributeChoice obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して顧客属性選択肢名を削除します。
   *
   * @param customerAttributeNo 顧客属性番号
   * @param customerAttributeChoicesNo 顧客属性選択肢番号
   */
  void delete(Long customerAttributeNo, Long customerAttributeChoicesNo);

  /**
   * Queryオブジェクトを指定して顧客属性選択肢名のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerAttributeChoiceのリスト
   */
  List<CustomerAttributeChoice> findByQuery(Query query);

  /**
   * SQLを指定して顧客属性選択肢名のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerAttributeChoiceのリスト
   */
  List<CustomerAttributeChoice> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCustomerAttributeChoiceのリスト
   */
  List<CustomerAttributeChoice> loadAll();

}
