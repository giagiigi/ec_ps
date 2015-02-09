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
import jp.co.sint.webshop.data.dto.Bank;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「金融機関(BANK)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface BankDao extends GenericDao<Bank, Long> {

  /**
   * 指定されたorm_rowidを持つ金融機関のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するBankのインスタンス
   */
  Bank loadByRowid(Long id);

  /**
   * 主キー列の値を指定して金融機関のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   * @param bankCode 金融機関コード
   * @param bankBranchCode 金融機関支店コード
   * @param accountNo 口座番号
   * @return 主キー列の値に対応するBankのインスタンス
   */
  Bank load(String shopCode, Long paymentMethodNo, String bankCode, String bankBranchCode, String accountNo);

  /**
   * 主キー列の値を指定して金融機関が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   * @param bankCode 金融機関コード
   * @param bankBranchCode 金融機関支店コード
   * @param accountNo 口座番号
   * @return 主キー列の値に対応するBankの行が存在すればtrue
   */
  boolean exists(String shopCode, Long paymentMethodNo, String bankCode, String bankBranchCode, String accountNo);

  /**
   * 新規Bankをデータベースに追加します。
   *
   * @param obj 追加対象のBank
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(Bank obj, LoginInfo loginInfo);

  /**
   * 金融機関を更新します。
   *
   * @param obj 更新対象のBank
   * @param loginInfo ログイン情報
   */
  void update(Bank obj, LoginInfo loginInfo);

  /**
   * 金融機関を削除します。
   *
   * @param obj 削除対象のBank
   * @param loginInfo ログイン情報
   */
  void delete(Bank obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して金融機関を削除します。
   *
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   * @param bankCode 金融機関コード
   * @param bankBranchCode 金融機関支店コード
   * @param accountNo 口座番号
   */
  void delete(String shopCode, Long paymentMethodNo, String bankCode, String bankBranchCode, String accountNo);

  /**
   * Queryオブジェクトを指定して金融機関のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するBankのリスト
   */
  List<Bank> findByQuery(Query query);

  /**
   * SQLを指定して金融機関のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するBankのリスト
   */
  List<Bank> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のBankのリスト
   */
  List<Bank> loadAll();

}
