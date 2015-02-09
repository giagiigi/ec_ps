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
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「配送種別(DELIVERY_TYPE)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface DeliveryTypeDao extends GenericDao<DeliveryType, Long> {

  /**
   * 指定されたorm_rowidを持つ配送種別のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するDeliveryTypeのインスタンス
   */
  DeliveryType loadByRowid(Long id);

  /**
   * 主キー列の値を指定して配送種別のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   * @return 主キー列の値に対応するDeliveryTypeのインスタンス
   */
  DeliveryType load(String shopCode, Long deliveryTypeNo);

  /**
   * 主キー列の値を指定して配送種別が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   * @return 主キー列の値に対応するDeliveryTypeの行が存在すればtrue
   */
  boolean exists(String shopCode, Long deliveryTypeNo);

  /**
   * 新規DeliveryTypeをデータベースに追加します。
   *
   * @param obj 追加対象のDeliveryType
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(DeliveryType obj, LoginInfo loginInfo);

  /**
   * 配送種別を更新します。
   *
   * @param obj 更新対象のDeliveryType
   * @param loginInfo ログイン情報
   */
  void update(DeliveryType obj, LoginInfo loginInfo);

  /**
   * 配送種別を削除します。
   *
   * @param obj 削除対象のDeliveryType
   * @param loginInfo ログイン情報
   */
  void delete(DeliveryType obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して配送種別を削除します。
   *
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   */
  void delete(String shopCode, Long deliveryTypeNo);

  /**
   * Queryオブジェクトを指定して配送種別のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するDeliveryTypeのリスト
   */
  List<DeliveryType> findByQuery(Query query);

  /**
   * SQLを指定して配送種別のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するDeliveryTypeのリスト
   */
  List<DeliveryType> findByQuery(String sqlString, Object... params);

}
