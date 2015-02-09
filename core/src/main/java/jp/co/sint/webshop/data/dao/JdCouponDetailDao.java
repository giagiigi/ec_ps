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
import jp.co.sint.webshop.data.dto.JdCouponDetail;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「受注明細(ORDER_DETAIL)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface JdCouponDetailDao extends GenericDao<JdCouponDetail, Long> {

  /**
   * 指定されたorm_rowidを持つ受注明細のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するOrderDetailのインスタンス
   */
  JdCouponDetail loadByRowid(Long id);

  /**
   * 主キー列の値を指定して受注明細のインスタンスを取得します。
   *
   * @param orderNo 受注番号
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するOrderDetailのインスタンス
   */
  JdCouponDetail load(String orderNo, Long couponNo);

  /**
   * 主キー列の値を指定して受注明細が既に存在するかどうかを返します。
   *
   * @param orderNo 受注番号
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するOrderDetailの行が存在すればtrue
   */
  boolean exists(String orderNo, Long couponNo);

  /**
   * 新規OrderDetailをデータベースに追加します。
   *
   * @param obj 追加対象のOrderDetail
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(JdCouponDetail obj, LoginInfo loginInfo);

  /**
   * 受注明細を更新します。
   *
   * @param obj 更新対象のOrderDetail
   * @param loginInfo ログイン情報
   */
  void update(JdCouponDetail obj, LoginInfo loginInfo);

  /**
   * 受注明細を削除します。
   *
   * @param obj 削除対象のOrderDetail
   * @param loginInfo ログイン情報
   */
  void delete(JdCouponDetail obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して受注明細を削除します。
   *
   * @param orderNo 受注番号
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   */
  void delete(String orderNo, Long couponNo);

  /**
   * Queryオブジェクトを指定して受注明細のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するOrderDetailのリスト
   */
  List<JdCouponDetail> findByQuery(Query query);

  /**
   * SQLを指定して受注明細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するOrderDetailのリスト
   */
  List<JdCouponDetail> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のOrderDetailのリスト
   */
  List<JdCouponDetail> loadAll();

}
