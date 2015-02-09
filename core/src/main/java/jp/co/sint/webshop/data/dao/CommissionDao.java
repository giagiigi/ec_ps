//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao;

import java.math.BigDecimal;
import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.Commission;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「支払手数料(COMMISSION)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CommissionDao extends GenericDao<Commission, Long> {

  /**
   * 指定されたorm_rowidを持つ支払手数料のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCommissionのインスタンス
   */
  Commission loadByRowid(Long id);

  /**
   * 主キー列の値を指定して支払手数料のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   * @param paymentPriceThreshold 支払金額閾値
   * @return 主キー列の値に対応するCommissionのインスタンス
   */
  Commission load(String shopCode, Long paymentMethodNo, BigDecimal paymentPriceThreshold);

  /**
   * 主キー列の値を指定して支払手数料が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   * @param paymentPriceThreshold 支払金額閾値
   * @return 主キー列の値に対応するCommissionの行が存在すればtrue
   */
  boolean exists(String shopCode, Long paymentMethodNo, Long paymentPriceThreshold);

  /**
   * 新規Commissionをデータベースに追加します。
   *
   * @param obj 追加対象のCommission
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(Commission obj, LoginInfo loginInfo);

  /**
   * 支払手数料を更新します。
   *
   * @param obj 更新対象のCommission
   * @param loginInfo ログイン情報
   */
  void update(Commission obj, LoginInfo loginInfo);

  /**
   * 支払手数料を削除します。
   *
   * @param obj 削除対象のCommission
   * @param loginInfo ログイン情報
   */
  void delete(Commission obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して支払手数料を削除します。
   *
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   * @param paymentPriceThreshold 支払金額閾値
   */
  void delete(String shopCode, Long paymentMethodNo, BigDecimal paymentPriceThreshold);

  /**
   * Queryオブジェクトを指定して支払手数料のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCommissionのリスト
   */
  List<Commission> findByQuery(Query query);

  /**
   * SQLを指定して支払手数料のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCommissionのリスト
   */
  List<Commission> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCommissionのリスト
   */
  List<Commission> loadAll();

}
