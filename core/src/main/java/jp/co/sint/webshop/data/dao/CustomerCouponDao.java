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
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「ポイントルール(Coupon_RULE)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CustomerCouponDao extends GenericDao<CustomerCoupon, Long> {

  /**
   * 指定されたorm_rowidを持つポイントルールのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCustomerCouponのインスタンス
   */
  CustomerCoupon loadByRowid(Long id);

  /**
   * 主キー列の値を指定してポイントルールのインスタンスを取得します。
   *
   * @param pointRuleNo ポイントルール番号
   * @return 主キー列の値に対応するCustomerCouponのインスタンス
   */
  CustomerCoupon load(Long customerCouponId);

  /**
   * 主キー列の値を指定してポイントルールが既に存在するかどうかを返します。
   *
   * @param pointRuleNo ポイントルール番号
   * @return 主キー列の値に対応するCustomerCouponの行が存在すればtrue
   */
  boolean exists(Long customerCouponId);

  /**
   * 新規CustomerCouponをデータベースに追加します。
   *
   * @param obj 追加対象のCustomerCoupon
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CustomerCoupon obj, LoginInfo loginInfo);

  /**
   * ポイントルールを更新します。
   *
   * @param obj 更新対象のCustomerCoupon
   * @param loginInfo ログイン情報
   */
  void update(CustomerCoupon obj, LoginInfo loginInfo);

  /**
   * ポイントルールを削除します。
   *
   * @param obj 削除対象のCustomerCoupon
   * @param loginInfo ログイン情報
   */
  void delete(CustomerCoupon obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してポイントルールを削除します。
   *
   * @param pointRuleNo ポイントルール番号
   */
  void delete(Long customerCouponId);

  /**
   * Queryオブジェクトを指定してポイントルールのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerCouponのリスト
   */
  List<CustomerCoupon> findByQuery(Query query);

  /**
   * SQLを指定してポイントルールのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerCouponのリスト
   */
  List<CustomerCoupon> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCustomerCouponのリスト
   */
  List<CustomerCoupon> loadAll();

}
