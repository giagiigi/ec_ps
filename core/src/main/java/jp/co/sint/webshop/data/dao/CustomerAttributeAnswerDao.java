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
import jp.co.sint.webshop.data.dto.CustomerAttributeAnswer;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「顧客属性回答(CUSTOMER_ATTRIBUTE_ANSWER)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CustomerAttributeAnswerDao extends GenericDao<CustomerAttributeAnswer, Long> {

  /**
   * 指定されたorm_rowidを持つ顧客属性回答のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCustomerAttributeAnswerのインスタンス
   */
  CustomerAttributeAnswer loadByRowid(Long id);

  /**
   * 主キー列の値を指定して顧客属性回答のインスタンスを取得します。
   *
   * @param customerAttributeNo 顧客属性番号
   * @param customerAttributeChoicesNo 顧客属性選択肢番号
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するCustomerAttributeAnswerのインスタンス
   */
  CustomerAttributeAnswer load(Long customerAttributeNo, Long customerAttributeChoicesNo, String customerCode);

  /**
   * 主キー列の値を指定して顧客属性回答が既に存在するかどうかを返します。
   *
   * @param customerAttributeNo 顧客属性番号
   * @param customerAttributeChoicesNo 顧客属性選択肢番号
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するCustomerAttributeAnswerの行が存在すればtrue
   */
  boolean exists(Long customerAttributeNo, Long customerAttributeChoicesNo, String customerCode);

  /**
   * 新規CustomerAttributeAnswerをデータベースに追加します。
   *
   * @param obj 追加対象のCustomerAttributeAnswer
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CustomerAttributeAnswer obj, LoginInfo loginInfo);

  /**
   * 顧客属性回答を更新します。
   *
   * @param obj 更新対象のCustomerAttributeAnswer
   * @param loginInfo ログイン情報
   */
  void update(CustomerAttributeAnswer obj, LoginInfo loginInfo);

  /**
   * 顧客属性回答を削除します。
   *
   * @param obj 削除対象のCustomerAttributeAnswer
   * @param loginInfo ログイン情報
   */
  void delete(CustomerAttributeAnswer obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して顧客属性回答を削除します。
   *
   * @param customerAttributeNo 顧客属性番号
   * @param customerAttributeChoicesNo 顧客属性選択肢番号
   * @param customerCode 顧客コード
   */
  void delete(Long customerAttributeNo, Long customerAttributeChoicesNo, String customerCode);

  /**
   * Queryオブジェクトを指定して顧客属性回答のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerAttributeAnswerのリスト
   */
  List<CustomerAttributeAnswer> findByQuery(Query query);

  /**
   * SQLを指定して顧客属性回答のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerAttributeAnswerのリスト
   */
  List<CustomerAttributeAnswer> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCustomerAttributeAnswerのリスト
   */
  List<CustomerAttributeAnswer> loadAll();

}
