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
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「支払方法(PAYMENT_METHOD)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface PaymentMethodDao extends GenericDao<PaymentMethod, Long> {

  /**
   * 指定されたorm_rowidを持つ支払方法のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するPaymentMethodのインスタンス
   */
  PaymentMethod loadByRowid(Long id);

  /**
   * 主キー列の値を指定して支払方法のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   * @return 主キー列の値に対応するPaymentMethodのインスタンス
   */
  PaymentMethod load(String shopCode, Long paymentMethodNo);
  // 20111206 lirong add start
  /**
   * 支払区分の値を指定して支払方法のインスタンスを取得します。
   *
   * @param paymentMethodType 支払区分番号
   * @return 支払区分の値に対応するPaymentMethodのインスタンス
   */
  PaymentMethod load(String paymentMethodType);
//20111206 lirong add end
  /**
   * 主キー列の値を指定して支払方法が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   * @return 主キー列の値に対応するPaymentMethodの行が存在すればtrue
   */
  boolean exists(String shopCode, Long paymentMethodNo);

  /**
   * 新規PaymentMethodをデータベースに追加します。
   *
   * @param obj 追加対象のPaymentMethod
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(PaymentMethod obj, LoginInfo loginInfo);

  /**
   * 支払方法を更新します。
   *
   * @param obj 更新対象のPaymentMethod
   * @param loginInfo ログイン情報
   */
  void update(PaymentMethod obj, LoginInfo loginInfo);

  /**
   * 支払方法を削除します。
   *
   * @param obj 削除対象のPaymentMethod
   * @param loginInfo ログイン情報
   */
  void delete(PaymentMethod obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して支払方法を削除します。
   *
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   */
  void delete(String shopCode, Long paymentMethodNo);

  /**
   * Queryオブジェクトを指定して支払方法のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するPaymentMethodのリスト
   */
  List<PaymentMethod> findByQuery(Query query);

  /**
   * SQLを指定して支払方法のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するPaymentMethodのリスト
   */
  List<PaymentMethod> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のPaymentMethodのリスト
   */
  List<PaymentMethod> loadAll();

}
