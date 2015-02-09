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
//05-20 delete start
//import jp.co.sint.webshop.data.dto.Bank;
//05-20 delete end
// 05-20 Add start
import jp.co.sint.webshop.data.dto.PostPayment;
//05-20 Add end
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「郵便支払い(POST_PAYMENT)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
//05-20 modify start 
  public interface PostPaymentDao extends GenericDao<PostPayment, Long> {
//05-20 modify end 
  /**
   * 指定されたorm_rowidを持つ金融機関のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するPaymentのインスタンス
   */
  PostPayment loadByRowid(Long id);

  /**
   * 主キー列の値を指定して金融機関のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号

   * @param accountNo 口座番号
   * @return 主キー列の値に対応するPostPaymentのインスタンス
   */
  PostPayment load(String shopCode, Long paymentMethodNo, String postAccountNo);

  /**
   * 主キー列の値を指定して金融機関が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   * @param accountNo 口座番号
   * @return 主キー列の値に対応するPostPaymentの行が存在すればtrue
   */
  boolean exists(String shopCode, Long paymentMethodNo, String postAccountNo);

  /**
   * 新規PostPaymentをデータベースに追加します。
   *
   * @param obj 追加対象のPostPayment
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(PostPayment obj, LoginInfo loginInfo);

  /**
   * 金融機関を更新します。
   *
   * @param obj 更新対象のPostPayment
   * @param loginInfo ログイン情報
   */
  void update(PostPayment obj, LoginInfo loginInfo);

  /**
   * 金融機関を削除します。
   *
   * @param obj 削除対象のPostPayment
   * @param loginInfo ログイン情報
   */
  void delete(PostPayment obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して金融機関を削除します。
   *
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   * @param accountNo 口座番号
   */
  void delete(String shopCode, Long paymentMethodNo, String postAccountNo);

  /**
   * Queryオブジェクトを指定して金融機関のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するPostPaymentのリスト
   */
  //List<Bank> findByQuery(Query query);
  List<PostPayment> findByQuery(Query query);
  

  /**
   * SQLを指定して金融機関のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するPostPaymentのリスト
   */
  //List<Bank> findByQuery(String sqlString, Object... params);
  List<PostPayment> findByQuery(String sqlString, Object... params);
  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のPostPaymentのリスト
   */
  List<PostPayment> loadAll();

}
