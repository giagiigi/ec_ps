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
import jp.co.sint.webshop.data.dto.DeliveryAppointedTime;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「配送指定時間(DELIVERY_APPOINTED_TIME)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface DeliveryAppointedTimeDao extends GenericDao<DeliveryAppointedTime, Long> {

  /**
   * 指定されたorm_rowidを持つ配送指定時間のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するDeliveryAppointedTimeのインスタンス
   */
  DeliveryAppointedTime loadByRowid(Long id);

  /**
   * 主キー列の値を指定して配送指定時間のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   * @param deliveryAppointedTimeCode 配送指定時間コード
   * @return 主キー列の値に対応するDeliveryAppointedTimeのインスタンス
   */
  DeliveryAppointedTime load(String shopCode, Long deliveryTypeNo, String deliveryAppointedTimeCode);

  /**
   * 主キー列の値を指定して配送指定時間が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   * @param deliveryAppointedTimeCode 配送指定時間コード
   * @return 主キー列の値に対応するDeliveryAppointedTimeの行が存在すればtrue
   */
  boolean exists(String shopCode, Long deliveryTypeNo, String deliveryAppointedTimeCode);

  /**
   * 新規DeliveryAppointedTimeをデータベースに追加します。
   *
   * @param obj 追加対象のDeliveryAppointedTime
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(DeliveryAppointedTime obj, LoginInfo loginInfo);

  /**
   * 配送指定時間を更新します。
   *
   * @param obj 更新対象のDeliveryAppointedTime
   * @param loginInfo ログイン情報
   */
  void update(DeliveryAppointedTime obj, LoginInfo loginInfo);

  /**
   * 配送指定時間を削除します。
   *
   * @param obj 削除対象のDeliveryAppointedTime
   * @param loginInfo ログイン情報
   */
  void delete(DeliveryAppointedTime obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して配送指定時間を削除します。
   *
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   * @param deliveryAppointedTimeCode 配送指定時間コード
   */
  void delete(String shopCode, Long deliveryTypeNo, String deliveryAppointedTimeCode);

  /**
   * Queryオブジェクトを指定して配送指定時間のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するDeliveryAppointedTimeのリスト
   */
  List<DeliveryAppointedTime> findByQuery(Query query);

  /**
   * SQLを指定して配送指定時間のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するDeliveryAppointedTimeのリスト
   */
  List<DeliveryAppointedTime> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のDeliveryAppointedTimeのリスト
   */
  List<DeliveryAppointedTime> loadAll();

}
